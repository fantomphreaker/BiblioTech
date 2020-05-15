public class Book {

    private String bookName;
    private String isbn;
    private String authorName;
    private int bookCopies;

    Book(String bName, String ISBN, String authorName, int bookCopies) {
        bookName = bName;
        isbn = ISBN;
        this.authorName = authorName;
        this.bookCopies = bookCopies;
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

    int getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(int bookCopies) {
        this.bookCopies = bookCopies;
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