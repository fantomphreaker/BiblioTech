import java.util.HashMap;
import java.util.Map;

public class Library{
	
	CustomHashMap bookList = new CustomHashMap();//stores book and no.of copies
	HashMap<Book, Integer> lendList = new HashMap<Book, Integer>();
	int noOfBooksLended;
	//int noOfCopiesAvailable;

	/*int getNoOfCopiesAvailable(Book book){
		noOfCopiesAvailable = bookList.get(book) - lendList.get(book);
			if(noOfCopiesAvailable > 0) {

				return noOfCopiesAvailable;
			}
			return 0;
	}*/

	void lendBooks(int num){
		noOfBooksLended = num + noOfBooksLended;
	}
	int getNoOfBooksLended(){

		return noOfBooksLended;
	}
	void addBook(Book book, int bookCopies){
				bookList.put(book, bookCopies);
				DBHelper helper = new DBHelper();
				helper.open();
				helper.addBook(book);
				helper.close();
	}

}