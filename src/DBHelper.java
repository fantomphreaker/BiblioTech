import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {

    Connection connection = null;
    Statement statement = null;

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

    public User getUserDetails(String username, String password){

        try{
            String queryString = "SELECT * FROM users WHERE username =\""+username+"\" AND password = \""+password+"\";";
            ResultSet rs = statement.executeQuery(queryString);
            if(rs.next()) {
                if (rs.getInt("is_admin") == 1){
                    return new User(rs.getString("username"), rs.getString("password"), rs.getString("first_name") + " " + rs.getString("last_name"), true);
                }else{
                    return new User(rs.getString("username"), rs.getString("password"), rs.getString("first_name") + " " + rs.getString("last_name"), false);
                }
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());

        }
        return null;
    }

    public ArrayList<Book> borrowBook(String bookName){
        ArrayList<Book> lendList = new ArrayList<Book>();

        try{
            String queryString = "SELECT * FROM library WHERE \"book_name\"= \""+bookName+"\";";
            ResultSet rs= statement.executeQuery(queryString);
            while(rs.next()){
                Book book = new Book(rs.getString("book_name"), rs.getString("isbn_number"), rs.getString("author_name"), rs.getInt("copies"));
                lendList.add(book);
            }
            if(lendList.isEmpty()){
                System.out.println("Book not available");
                return null;
            }else if(lendList.size() == 1){
                return lendList;
            }else{
                System.out.println(lendList.size()+" results found!");
                int i = 1;
                for(Book book : lendList){
                    System.out.println(i+". "+book.getBookName()+" by "+book.getAuthorName()+"| ISBN: "+book.getISBN()+" | No. of copies: "+book.getBookCopies());
                    i++;
                }


            }

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return lendList;
    }

    public ArrayList<Book> getAllBooks(){
        ArrayList<Book> bookLists = new ArrayList<Book>();

        try{

            String queryString = "SELECT * FROM library;";
            ResultSet rs = statement.executeQuery(queryString);
            while(rs.next()){
               Book book = new Book(rs.getString("book_name"), rs.getString("isbn_number"), rs.getString("author_name"), rs.getInt("copies"));
               bookLists.add(book);
            }

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return bookLists;
    }

    public boolean addBook(Book book){
        boolean isBookAdded = true;

        int prevCopies = 0;
        try{
            boolean isISBNsame = false;
            String queryString2 = "SELECT * FROM library WHERE isbn_number = \""+book.getISBN()+"\";";
            ResultSet rs = statement.executeQuery(queryString2);
            while(rs.next()){
                prevCopies = rs.getInt("copies");
                isISBNsame = true;
                }

            if(!isISBNsame){
            String queryString = "INSERT INTO library (isbn_number, book_name, author_name, copies) VALUES (\""+ book.getISBN()+ "\",\""+ book.getBookName() + "\",\"" + book.getAuthorName()+"\","+book.getBookCopies()+");";
            System.out.println(queryString);
            statement.executeUpdate(queryString);
            isBookAdded = true;

            }else{
              prevCopies = book.getBookCopies() + prevCopies;
              String query = "UPDATE library SET copies = "+prevCopies+" WHERE isbn_number =\""+book.getISBN()+"\";";
              statement.executeUpdate(query);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            isBookAdded = false;
        }

        return isBookAdded;
    }

    public boolean addBorrowedBooks(Book book){
        boolean isBookBorrowed = true;
        try{
            String queryString = "INSERT INTO lendlist (book_name, isbn_number) VALUES (\""+book.getBookName()+"\",\""+book.getISBN()+"\");";
           // System.out.println(queryString);
            statement.executeUpdate(queryString);
        }catch(SQLException ex){
            System.out.println(" Can't borrow multiple books of same ISBN number ");
            isBookBorrowed = false;
        }
        return isBookBorrowed;
    }

   /* public boolean userTableCreator() {
        Connection con = null;

        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getTables(null, null, "users",
                new String[]{"TABLE"});
        if (!res.next()) {
            System.out.println(
                    "   "//+res.getString("TABLE_CAT")
                         //   + ", "+res.getString("TABLE_SCHEM")
                            + ", "+res.getString("users");
                         //   + ", "+res.getString("TABLE_TYPE")
                         //   + ", "+res.getString("REMARKS"));


            try {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:db/library.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.
                statement.executeUpdate("CREATE TABLE users (username TEXT PRIMARY KEY, password TEXT NOT NULL, first_name TEXT NOT NULL, last_name TEXT NOT NULL, is_admin BIT NOT NULL)");
                // statement.executeUpdate("INSERT INTO library (isbn_number, book_name, author_name, copies) VALUES (\"1222-1222-1111-0000\",\"Harry Potter\",\"JKR\",12)");
                ResultSet rs = statement.executeQuery("SELECT * FROM users");
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
    } */

//    public ArrayList<Book> getAllBooks(){
//        ArrayList<Book> books = new ArrayList<>();
//
//        return  books;
//    }

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
