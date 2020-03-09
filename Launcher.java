import java.util.Scanner;
import java.util.ArrayList;

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
        
        User currentUser = login(name, pass);
		if(currentUser.getIsAdmin() == true){
			displayAdminMenu(currentUser);
		}
		else{
			displayUserMenu(currentUser);
		}
       
	    
	}

	static User login(String name, String pass) {
			if(name.equals(adminName) && pass.equals(adminPass)) {
			System.out.println(" Hello Administrator "+ name);
			return new User(name, pass, true);
		}
		else if(name.equals(userName) && pass.equals(userPass)) {
			System.out.println(" Hello user "+ name);
			return new User(name, pass, false);
		}
		else {
			System.out.println("Invalid credentials ");
			return null;
		}
	}
    
    
   
   static void displayAdminMenu(User currentUser) {
		int choice = 0;
		
 	    Scanner sc = new Scanner(System.in);
		
		System.out.println(" 1. Add Book ");
		System.out.println(" 2. Remove Book ");
		System.out.println(" 3. View Books ");
		System.out.println(" 4. Exit ");
		System.out.println("Enter the choice: ");
		choice = sc.nextInt();
		sc.nextLine();

		if(choice == 1){
			System.out.println("Enter the book name: ");
			String bookName = sc.nextLine();
			System.out.println("Enter the authorName: ");
			String authorName = sc.nextLine();
			System.out.println("Enter the ISBN :");
			String isbn = sc.nextLine();
			System.out.println("Enter the number of books:");
			int numbook = sc.nextInt();
			
			currentUser.getBookList().add(new Book(isbn, bookName, authorName, numbook));
			System.out.println("Book Added Successfully! ");
			displayAdminMenu(currentUser);//code to add book to arraylist goes here

         }
		else if(choice == 2){
			//code to remove book from arraylist
		}
		else if(choice == 3){
			//code to view books
			for(int i = 0 ; i < currentUser.getBookList().size(); i++) {

				Book b = currentUser.getBookList().get(i);
				System.out.println(i+1+".");
				b.display();


			}
		displayAdminMenu(currentUser);
		}
		else if(choice == 4){
			System.out.println("Exiting...");
			main(null);

			
		}
		else{
			System.out.println("Invalid choice ");
			displayAdminMenu(currentUser);
		}


	}

	static void displayUserMenu(User currentUser){
		int choice = 0;
		Scanner sc = new Scanner(System.in);

		System.out.println("1. Borrow Book ");
		System.out.println("2. Return Book ");
		System.out.println("3. View Books");
		if(choice == 1)
		{
			//code to borrow book
		}
		else if(choice == 2){
			//code to return book
		}
		else if(choice == 3){
			for(int i = 0 ; i < currentUser.getBookList().size(); i++) {

				Book b = currentUser.getBookList().get(i);
				System.out.println(i+1+".");
				b.display();
			}
			displayUserMenu(currentUser);
//code to view books
		}
		else{

		   System.out.println("Invalid Choice");
		   displayUserMenu(currentUser);
		}
	}
}