import java.util.Scanner;

public class Launcher{
 static	final String adminName = "navaneeth";
 static	final String adminPass = "password";
 static	final String userName  = "ajay";
 static	final String userPass  = "abcdef";

	public static void main(String[] args) {
		System.out.println("Welcome to The Library");
		System.out.println("Please enter your credentials:");
		System.out.println("Username: ");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		System.out.println("Password: ");
		String pass = sc.nextLine();

		User person = login(name, pass);
	}

	static User login(String name, String pass) {
			if(name.equals(adminName) && pass.equals(adminPass)) {
			System.out.println(" Hello admin ");
			return new User(name, pass, true);
		}
		else if(name.equals(userName) && pass.equals(userPass)) {
			System.out.println(" Hello user ");
			return new User(name, pass, false);
		}
		else {
			System.out.println("Invalid credentials ");
			return null;
		}
	}
}