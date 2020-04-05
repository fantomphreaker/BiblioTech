public class Book {

    private String bookName;
    private String isbn;
    private String authorName;

    Book(String bName, String ISBN, String authorName) {
        bookName = bName;
        isbn = ISBN;
        this.authorName = authorName;
    }

    String getBookName() {
        return bookName;
    }

    String getAuthorName() {
        return authorName;
    }

    String getISBN() {
        return isbn;
    }

    void setBookName(String bName) {
        bookName = bName;

    }

    void setAuthorName(String aName) {

        authorName = aName;
    }

    void setISBN(String ISBN) {

        isbn = ISBN;
    }

}