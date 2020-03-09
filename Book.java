public class Book{
	private String isBn;
	private String bookName;
	private String authorName;
	private int bookCount;

   
   public Book(String iSBN, String name, String authname, int bcount ){
    	isBn = iSBN;
    	bookName = name;
    	authorName = authname;
    	bookCount = bcount; 
    }

    void display(){
       System.out.println("Book name: "+bookName);
       System.out.println("By Author: "+authorName);
       System.out.println("ISBN : "+isBn);

    	
    }

}