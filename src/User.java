public class User{
	
	private String username;
	private String password;
	private String name;
	private boolean isAdmin;

	User(String uName, String pWord, boolean isAdmin){
		username = uName;
		password = pWord;
		this.isAdmin = isAdmin;

	}

	boolean isAdmin(){
		return isAdmin;
	}



}