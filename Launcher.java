import java.util.Scanner;
import java.util.HashMap;

public class Launcher{

	static final String USERNAME_ADMIN = "navaneeth";
	static final String PASSWORD_ADMIN = "abcdef";
	static final String NAME_ADMIN	   = "Navaneeth Gopal";
	static final String USERNAME_NOTADMIN = "ajay";
	static final String PASSWORD_NOTADMIN = "1234";
	static final String NAME_NOTADMIN	  = "Ajay Narayanan";
	static 	User currentUser = null;
	static 	Library lib = null;
	static	Book book = null;

	 static Scanner sc = new Scanner(System.in); 
	
	public static void main(String[] args){
 
		System.out.println("Welcome to the library! ");
		System.out.println("Please enter your credentials: ");
			
			System.out.println("username : ");
				String username_in = sc.nextLine();
			System.out.println("password : "); 
			    String userpass_in = sc.nextLine();
					
					login(username_in, userpass_in);

			   /* while(currentUser == null){
			  //  	login();
			    //}

			   / while(!displayMenu()) Ajay code. */ 
		  


	}

static void login(String username_in, String userpass_in){
			    if(username_in.equals(USERNAME_ADMIN) && userpass_in.equals(PASSWORD_ADMIN)){

			    	System.out.println("Welcome administrator "+NAME_ADMIN);
			    	currentUser = new User(username_in, userpass_in, true );
			    	displayMenu(true);

			    }else if(username_in.equals(USERNAME_NOTADMIN) && userpass_in.equals(PASSWORD_NOTADMIN)){

			    	System.out.println("Welcome "+NAME_NOTADMIN);
			    	currentUser = new User(username_in, userpass_in, false );
			    	displayMenu(false);
			    }else{
			    	System.out.println("Invalid credentials ");
			    }
			}

	static void displayMenu(boolean trueorfalse){
		int choice = 0;
		
		if(trueorfalse == true){
			System.out.println("Menu:\n1.Add Book\n2.Remove Book\n3.View Book\nPlease enter your choice(1/2/3)");
			choice = sc.nextInt();
			sc.nextLine();
				if(choice == 1){
					System.out.println("Enter the name of the book: ");
							String bname = sc.nextLine();
					System.out.println("Enter the Author name:");
						String authname = sc.nextLine();
					System.out.println("Enter the ISBN:");
						String bisbn  = sc.nextLine();
					System.out.println("Enter the number of copies:");
						int bcopies = sc.nextInt();
						sc.nextLine();
					System.out.println("Press a y/n to confirm/cancel: ");
						String yesorno = sc.nextLine();
						
						if(yesorno.equals("y") || yesorno.equals("Y")){
							System.out.println("bname = "+bname);
							book = new Book(bname, bisbn, authname);
							//book.displayBook();//temp testcode
							lib  = new Library();
							lib.addBook(book, bcopies);
							System.out.println(bcopies+" book(s) of ISBN: "+bisbn+" added to the library!\n");
							displayMenu(true);

						}else{
							System.out.println("Cancelling... ");
							displayMenu(true);
						}
				}else if(choice == 3){
					System.out.println("Press y/n to confirm ");
						
						String yesorno = sc.nextLine();
						if(yesorno.equals("y") || yesorno.equals("Y")){
							System.out.println(lib.bookList.toString());	//code to view books from HashMap
						}else{
							System.out.println("Exiting...");
							displayMenu(true);
						}

				}
			//add method calls in this line
		}else if(trueorfalse == false){
			System.out.println("Menu:\n 1.Borrow Book\n2.View Book\nPlease enter your choice(1/2/3) ");
			choice = sc.nextInt();
			//add method calls for non admin here
		}



	}
}

