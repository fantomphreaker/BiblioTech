import org.sqlite.core.DB;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Launcher {


    static User currentUser = null;

    static ArrayList<Book> allBooks = null;

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
        DBHelper helper = new DBHelper();
        helper.open();
        currentUser = helper.getUserDetails(inputUsername, inputPassword);
        helper.close();
        if (currentUser == null) {
            System.out.println("Invalid Credentials! ");
        }


    }

    static boolean displayMenu(boolean isAdmin) {
        int choice = 0;
        boolean isExit = false;
        if (isAdmin) {
            System.out.println(
                    "Menu:\n" +
                            "1.Add Book\n" +   //done with sql
                            "2.Remove Book\n" + //do this last
                            "3.View Book\n" +  //done with sql
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
                if (bCopies < 0) {
                    System.out.println("Invalid choice entry. Please try again.\n");

                } else {
                    System.out.println("Press a y/n to confirm/cancel: ");
                    String userConfirmation = sc.nextLine();


                    if (userConfirmation.equals("y") || userConfirmation.equals("Y")) {
                        Book book = new Book(bName, bISBN, authorName, bCopies);
                        //book.displayBook();//temp testcode
                        DBHelper helper = new DBHelper();
                        helper.open();
                        helper.addBook(book);
                        helper.close();
                        System.out.println(bCopies + " book(s) of ISBN: " + bISBN + " added to the library!\n");
                    } else {
                        System.out.println("Cancelling... ");
                    }
                }

            } else if (choice == 3) {
                DBHelper helper = new DBHelper();
                int i = 1;
                helper.open();
                allBooks = helper.getAllBooks();
                helper.close();
                if(allBooks.isEmpty()){
                    System.out.println(" No Books Found! ");
                }
                for (Book book : allBooks) {
                    System.out.println(i + ". " + book.getBookName() + " by " + book.getAuthorName() + " | ISBN: " + book.getISBN() + " | No. of available copies: " + book.getBookCopies());
                    i++;
                }

            } else if (choice == 4) {
                isExit = true;
            } else if (choice == 2) {
                DBHelper helper = new DBHelper();
                System.out.println("Enter the name of the book to be removed. ");
                String bName = sc.nextLine();
                helper.open();
                helper.removeBooks(bName);
                helper.close();

            }//Admin menu ends here

        } else {
            System.out.println("\nMenu:\n1.Borrow Book\n2.View Book\n3.Borrowed Books List\n4.Exit\nPlease enter your choice(1/2/3/4) ");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == 2) {
                System.out.println("Press y/n to confirm ");
                String yesorno = sc.nextLine();


                if (yesorno.equals("y") || yesorno.equals("Y")) {
                    DBHelper helper = new DBHelper();
                    int i = 1;
                    helper.open();
                    allBooks = helper.getAllBooks();
                    helper.close();
                    for (Book book : allBooks) {
                        System.out.println(i + ". " + book.getBookName() + " by " + book.getAuthorName() + " | ISBN: " + book.getISBN() + " | No. of available copies: " + book.getBookCopies());
                        i++;
                    }//code to view books from db
                } else {
                    System.out.println("Exiting...\n");
                }


            } else if (choice == 1) {

                System.out.println("Enter the name of the book you want to borrow ");
                String bName = sc.nextLine();
                DBHelper helper = new DBHelper();
                helper.open();
                ArrayList<Book> lendList = helper.borrowBook(bName);
                helper.close();

                if (lendList.size() == 0) {
                    System.out.println("Book not available!");
                } else if (lendList.size() > 1) {
                    System.out.println("Enter your choice: ");
                    int select = sc.nextInt();

                    if (select <= lendList.size()) {

                        Book book = lendList.get(select - 1);
                        helper.open();
                        boolean isBorrowed = helper.addBorrowedBooks(book);
                        helper.close();
                        if (isBorrowed) {
                            System.out.println("Book borrowed successfully!");
                        } else {
                            System.out.println("Borrowing failed!");
                        }

                    } else {
                        System.out.println("Invalid choice!");
                    }
                } else if (lendList.size() == 1) {
                    Book book = lendList.get(0);
                    helper.open();
                    if (helper.addBorrowedBooks(book)) {
                        System.out.println("Book borrowed successfully!");
                    }
                    helper.close();
                }


            } else if (choice == 3) {


            } else if (choice == 4) {
                isExit = true;
            }

        }
        return isExit;
    }

}

