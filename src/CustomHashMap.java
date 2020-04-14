import java.util.HashMap;

class CustomHashMap extends HashMap<Book, Integer>{

@Override
public String toString(){

	String returnValue = "";
	for(Book b : keySet()){
		returnValue += (b.getBookName()+" : "+get(b)+" copies"+"\n");
         
       }
     return returnValue;
}

}