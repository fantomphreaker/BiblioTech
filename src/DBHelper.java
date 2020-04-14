import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public boolean addBook(Book book){
        boolean isBookAdded = true;
        try{
            String queryString = "INSERT INTO library (isbn_number, book_name, author_name, copies) VALUES (\""+ book.getISBN()+ "\",\""+ book.getBookName() + "\",\"" + book.getAuthorName()+"\",1);";
            System.out.println(queryString);
            String isbn = book.getISBN();
            String bname = book.getBookName();
            String authname = book.getAuthorName();
            statement.executeUpdate(queryString);
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            isBookAdded = false;
        }

        return isBookAdded;
    }

    public ArrayList<Book> getAllBooks(){
        ArrayList<Book> books = new ArrayList<>();

        return  books;
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
