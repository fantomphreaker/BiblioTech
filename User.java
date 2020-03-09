import java.util.ArrayList;

public class User {
	private String userName;
	private String password;
	private boolean isAdmin;
	private ArrayList<Book> bookList = new ArrayList<Book>();

String getUserName(String username) {
	return username;

}

String getUserPass(String userpass) {
	return userpass;
}

boolean getIsAdmin() {
	return isAdmin;
}	
 
ArrayList<Book> getBookList(){
	return bookList;
}	
	User (String name, String pass, boolean isadmin) {
		userName = name;
		password = pass;
		isAdmin	 = isadmin;
	}
	
	

}