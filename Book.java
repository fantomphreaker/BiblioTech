public class Book{
	
	private String bookName;
	private String isbn;
	private String authorName;

		Book(String bname, String isBn, String authname){
			bookName = bname;
			isbn     = isBn;
			authorName = authname;
		}

		String getBookName(){
			return bookName;
		}

		String getAuthorName(){
			return authorName;
		}

		String getISBN(){
			return isbn;
		}

		void setBookName(String bname){
			bookName = bname;

		}
		void setAuthorName(String aName){

			authorName = aName;
		}
		void setISBN(String iSBN){

			isbn = iSBN;
	    }
	   /* void displayBook(){
	    	System.out.println(bookName+" -> "+authorName); ---> testcode ajay
	    } 
	    */
}