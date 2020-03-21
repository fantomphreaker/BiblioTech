import java.util.HashMap;
import java.util.Map;

public class Library{
	
	CustomHashMap bookList = new CustomHashMap();//stores book and no.of copies
	/* to-do 1. addBook function.
			 2. removeBook function.
			 3. viewBook function.
			 4. borrowBook function. */
	void addBook(Book book, int bookCopies){
				bookList.put(book, bookCopies);
	}

}