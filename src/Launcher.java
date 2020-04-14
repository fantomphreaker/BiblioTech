import java.util.Scanner;

public class Launcher{

	static final String USERNAME_ADMIN = "n";
	static final String PASSWORD_ADMIN = "a";
	static final String NAME_ADMIN	   = "Navaneeth Gopal";
	static final String USERNAME_NOTADMIN = "a";
	static final String PASSWORD_NOTADMIN = "1";
	static final String NAME_NOTADMIN	  = "Ajay Narayanan";
	static 	User currentUser = null;
	static 	Library lib = new Library();

	 static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Welcome to the library! ");
		System.out.println("Please enter your credentials: ");


		boolean isExitProgram = false;

		while (!isExitProgram) {
			currentUser = null;
			while (currentUser == null) {
				System.out.println("username : ");
				String inputUsername = sc.nextLine();
				System.out.println("password : ");
				String inputPassword = sc.nextLine();
				login(inputUsername, inputPassword);
			}

			boolean isExitMenu = false;
			while (!isExitMenu) {
				isExitMenu = displayMenu(currentUser.isAdmin());

				/*if(isExitMenu == false){
					isExitProgram = false;
					isExitMenu = true;
				}*/
			}

			System.out.println("Exit Library ? (y/n)");
			String userConfirmation = sc.nextLine();
			if (userConfirmation.equals("y") || userConfirmation.equals("Y")) {
				isExitProgram = true;
			}
		}

	}

	static void login(String inputUsername, String inputPassword) {
		currentUser = null;
		if (inputUsername.equals(USERNAME_ADMIN) && inputPassword.equals(PASSWORD_ADMIN)) {

			System.out.println("Welcome administrator " + NAME_ADMIN);
			currentUser = new User(inputUsername, inputPassword, true);

		} else if (inputUsername.equals(USERNAME_NOTADMIN) && inputPassword.equals(PASSWORD_NOTADMIN)) {

			System.out.println("Welcome " + NAME_NOTADMIN);
			currentUser = new User(inputUsername, inputPassword, false);
		} else {
			System.out.println("Invalid credentials ");
		}
	}

	static boolean displayMenu(boolean isAdmin) {
		int choice = 0;
		boolean isExit = false;
		if (isAdmin) {
			System.out.println(
					"Menu:\n" +
							"1.Add Book\n" +
							"2.Remove Book\n" +
							"3.View Book\n" +
							"4.Exit\n" +
							"Please enter your choice: "
			);
			choice = sc.nextInt();
			sc.nextLine();
			if (choice == 1) {
				System.out.println("Enter the name of the book: ");
				String bName = sc.nextLine();
				System.out.println("Enter the Author name:");
				String authorName = sc.nextLine();
				System.out.println("Enter the ISBN:");
				String bISBN = sc.nextLine();
				System.out.println("Enter the number of copies:");
				int bCopies = sc.nextInt();
				sc.nextLine();
				System.out.println("Press a y/n to confirm/cancel: ");
				String userConfirmation = sc.nextLine();

				if (userConfirmation.equals("y") || userConfirmation.equals("Y")) {
					Book book = new Book(bName, bISBN, authorName);
					//book.displayBook();//temp testcode

					lib.addBook(book, bCopies);
					System.out.println(bCopies + " book(s) of ISBN: " + bISBN + " added to the library!\n");
				} else {
					System.out.println("Cancelling... ");
				}

			} else if (choice == 3) {
				System.out.println("\nTotal number of books in the library : " + lib.bookList.keySet().size());
				//System.out.println("size = "+ lib.bookList.size()); //testcode ajay
				System.out.println(lib.bookList.toString());    //code to view books from HashMap

			} else if (choice == 4) {
					isExit = true;
			}

		} else {
			System.out.println("\nMenu:\n1.Borrow Book\n2.View Book\n3.Borrowed Books List\n4.Exit\nPlease enter your choice(1/2/3/4) ");
			choice = sc.nextInt();
			sc.nextLine();
			if (choice == 2) {
				System.out.println("Press y/n to confirm ");
				String yesorno = sc.nextLine();
				sc.nextLine();

				if (yesorno.equals("y") || yesorno.equals("Y")) {
					System.out.println("\nTotal number of books in the library : " + lib.bookList.keySet().size());
					System.out.println(lib.bookList.toString());
				} else {
					System.out.println("Exiting...\n");
				}


			} else if (choice == 1) {
				int flag = -1;
				boolean canBorrow = true;
				System.out.println("Enter the name of the book you want to borrow: ");

				String borrowBookName = sc.nextLine();
				sc.nextLine();

				for (Book book : lib.bookList.keySet()) {

					if (book.getBookName().equals(borrowBookName)) {
						for (Book b : lib.lendList.keySet()) {

							if (b.getISBN().equals(book.getISBN())) {
								System.out.println("You can't borrow more than one book having the same ISBN!\n");
								canBorrow = false;
								break;
							}

						}


					}


					if (book.getBookName().equals(borrowBookName) && canBorrow) {
						//System.out.println(lib.getNoOfCopiesAvailable(book)+" Copies available. Do you want to borrow?(y/n)\n");
						//String yesorno = sc.nextLine();
						//if(yesorno.equals("y") || yesorno.equals("Y")) {
						//System.out.println("Testcode"+ lib.getNoOfCopiesAvailable(book));

						lib.lendList.put(book, 1);
						flag = 1;
						break;
						//	}

					}
				}
				if (flag != -1) {

					lib.lendBooks(flag);
					System.out.println(flag + " Book(s) borrowed from the Library! \n");


				} else {

					System.out.println("Incorrect Book Name \n");


				}

			} else if (choice == 3) {

				System.out.println("Press y/n to confirm\n");
				String yesoorno = sc.nextLine();

				if (yesoorno.equals("y") || yesoorno.equals("Y")) {

					System.out.println("No of books borrowed: " + lib.getNoOfBooksLended());
					for (Book b : lib.lendList.keySet()) {

						System.out.println("Book name: " + b.getBookName() + " by " + b.getAuthorName() + " of ISBN " + b.getISBN());

					}

				} else {

					System.out.println("Exiting...");

				}


			} else if (choice == 4) {
				isExit = true;
			}

		}
		return isExit;
	}

}

