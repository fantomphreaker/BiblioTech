import java.sql.*;
import java.util.*;

public class DBHelper {

    Connection connection = null;
    Statement statement = null;
    Scanner sc = new Scanner(System.in);

    public void open() {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/library.db");
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            close();
        }
    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.

            System.err.println(e.getMessage());
        }

    }

    public User getUserDetails(String username, String password) {

        try {
            String queryString = "SELECT * FROM users WHERE username =\"" + username + "\" AND password = \"" + password + "\";";
            ResultSet rs = statement.executeQuery(queryString);
            if (rs.next()) {
                if (rs.getInt("is_admin") == 1) {
                    return new User(rs.getString("username"), rs.getString("password"), rs.getString("first_name") + " " + rs.getString("last_name"), true);
                } else {
                    return new User(rs.getString("username"), rs.getString("password"), rs.getString("first_name") + " " + rs.getString("last_name"), false);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return null;
    }

    public ArrayList<Book> borrowBook(String bookName) {
        ArrayList<Book> lendList = new ArrayList<Book>();

        try {
            String queryString = "SELECT * FROM library WHERE book_name LIKE \"%" + bookName + "%\";";
            ResultSet rs = statement.executeQuery(queryString);
            while (rs.next()) {
                Book book = new Book(rs.getString("book_name"), rs.getString("isbn_number"), rs.getString("author_name"), rs.getInt("copies"));
                lendList.add(book);
            }
            if (lendList.isEmpty()) {
                System.out.println("Book not available");
                return null;
            } else if (lendList.size() == 1) {
                return lendList;
            } else {
                System.out.println(lendList.size() + " results found!");
                int i = 1;
                for (Book book : lendList) {
                    System.out.println(i + ". " + book.getBookName() + " by " + book.getAuthorName() + "| ISBN: " + book.getISBN() + " | No. of copies: " + book.getBookCopies());
                    i++;
                }


            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lendList;
    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> bookLists = new ArrayList<Book>();

        try {

            String queryString = "SELECT * FROM library;";
            ResultSet rs = statement.executeQuery(queryString);
            while (rs.next()) {
                Book book = new Book(rs.getString("book_name"), rs.getString("isbn_number"), rs.getString("author_name"), rs.getInt("copies"));
                bookLists.add(book);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return bookLists;
    }

    public boolean borrowedBooksList(User currentUser) { // test commit
        boolean isListEmpty = true;

        try {
            String queryString = "SELECT book_name, isbn_number FROM lendlist WHERE username = \"" + currentUser.getUsername() + "\";";
            ResultSet rs = statement.executeQuery(queryString);
            int i = 1;
            while (rs.next()) {
                System.out.println(i + ". " + rs.getString("book_name") + "|ISBN: " + rs.getString("isbn_number"));
                i++;
            }
            isListEmpty = rs.next();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return isListEmpty;

    }

    public boolean addBook(Book book) {
        boolean isBookAdded = true;
        String bookNameFromDb = null;


        int prevCopies = 0;
        try {
            boolean isISBNsame = false;
            String queryString2 = "SELECT * FROM library WHERE isbn_number = \"" + book.getISBN() + "\";";
            ResultSet rs = statement.executeQuery(queryString2);
            while (rs.next()) {
                prevCopies = rs.getInt("copies");
                isISBNsame = true;
                bookNameFromDb = rs.getString("book_name");
            }

            if (!isISBNsame) {
                String queryString = "INSERT INTO library (isbn_number, book_name, author_name, copies) VALUES (\"" + book.getISBN() + "\",\"" + book.getBookName() + "\",\"" + book.getAuthorName() + "\"," + book.getBookCopies() + ");";
//                System.out.println(queryString);
                statement.executeUpdate(queryString);
                isBookAdded = true;

            } else if (isISBNsame && !(bookNameFromDb.equals(book.getBookName()))) {
                System.out.println("Mismatch of book names with the same ISBN found! \nAdding book failed!\n");
                isBookAdded = false;
            } else {
                prevCopies = book.getBookCopies() + prevCopies;
                String query = "UPDATE library SET copies = " + prevCopies + " WHERE isbn_number =\"" + book.getISBN() + "\";";
                statement.executeUpdate(query);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            isBookAdded = false;
        }

        return isBookAdded;
    }

    public boolean addBorrowedBooks(Book book, User currentUser) {
        boolean isBookBorrowed = true;
        try {
            String queryString = "INSERT INTO lendlist (book_name, isbn_number, username) VALUES (\"" + book.getBookName() + "\",\"" + book.getISBN() + "\",\"" + currentUser.getUsername() + "\");";
            // System.out.println(queryString);
            statement.executeUpdate(queryString);
        } catch (SQLException ex) {
            System.out.println(" Can't borrow multiple books of same ISBN number ");
            isBookBorrowed = false;
        }
        return isBookBorrowed;
    }

    public boolean removeBooks(String bookName) {
        //boolean isRemoved = true;
        try {

            int i = 0;
            String queryString = "SELECT * FROM library WHERE book_name LIKE \"%" + bookName + "%\";";
            ResultSet rs = statement.executeQuery(queryString);
            ArrayList<Book> removeList = new ArrayList<Book>();
            while (rs.next()) {
                Book book = new Book(rs.getString("book_name"), rs.getString("isbn_number"), rs.getString("author_name"), rs.getInt("copies"));
                removeList.add(book);
                ++i;
            }
            if (i == 1) {
                System.out.println("Book name : " + removeList.get(0).getBookName() + "\n ISBN : " + removeList.get(0).getISBN() + "\n Copies : " + removeList.get(0).getBookCopies());
                System.out.println("Enter the number of copies to be removed : ");
                int cpy = sc.nextInt();
                if(cpy < 0){ return false;}
                if (removeList.get(0).getBookCopies() == cpy) {
                    queryString = "DELETE FROM library WHERE isbn_number = \"" + removeList.get(0).getISBN() + "\";";
                    statement.executeUpdate(queryString);
//                    System.out.println("Book Removed!");
                    return true;

                } else if (removeList.get(0).getBookCopies() > cpy) {
                    cpy = removeList.get(0).getBookCopies() - cpy;
                    queryString = "UPDATE library SET copies = " + cpy + " WHERE isbn_number = \"" + removeList.get(0).getISBN() + "\";";
                    System.out.println(queryString);//test
                    statement.executeUpdate(queryString);
                    return true;

                } else {
//                    System.out.println("Operation failed! ");
                    return false;
                }
            } else {
                System.out.println(i + " Result(s) found !");
                int j = 1;
                for (Book book : removeList) {
                    System.out.println(j + ". " + book.getBookName() + " by " + book.getAuthorName() + " |ISBN : " + book.getISBN() + " |Copies: " + book.getBookCopies());
                    j++;
                }
                System.out.println("Enter your choice");
                int choice = sc.nextInt();
                if(choice <= 0){ return false;}
                if (choice <= removeList.size()) {
                    System.out.println("Enter the number of copies.");
                    int copies = sc.nextInt();
                    if (copies <= 0) {
                        System.out.println("Number of copies can't be zero or negative.\n");
                        return false;
                    }
                    if (removeList.get(choice - 1).getBookCopies() > copies) {
                        queryString = "UPDATE library SET copies = " + (removeList.get(choice - 1).getBookCopies() - copies) + " WHERE isbn_number = \"" + removeList.get(choice - 1).getISBN() + "\";";
                        statement.executeUpdate(queryString);
                        return true;
                    } else if (removeList.get(choice - 1).getBookCopies() == copies) {
                        queryString = "DELETE FROM library WHERE isbn_number = \"" + removeList.get(choice - 1).getISBN() + "\";";
                        statement.executeUpdate(queryString);
                        return true;
                    }

                }
            }
        } catch (SQLException ex) {
            System.out.println("Cannot remove book(s)!");
            return false;
        }
        return true;
    }


    public static void main(String[] args) { //test code

        Connection connection = null;


        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:db/library.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
//            statement.executeUpdate("CREATE TABLE library (isbn TEXT PRIMARY KEY, book_name TEXT NOT NULL, author_name TEXT NOT NULL, copies INTEGER DEFAULT 0)");
            statement.executeUpdate("INSERT INTO library (isbn_number, book_name, author_name, copies) VALUES (\"1222-1222-1111-0000\",\"Harry Potter\",\"JKR\",12)");
            ResultSet rs = statement.executeQuery("SELECT * FROM library");
            while (rs.next()) {
                System.out.println("Name : " + rs.getString("book_name"));
            }


        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println("### " + e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.

                System.err.println(e.getMessage());
            }
        }
    }
}
