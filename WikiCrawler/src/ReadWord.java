import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;

public class ReadWord {

  private static int count;

public static String extractText(String url) throws IOException {
    String textOnly = Jsoup.parse(url).text();
    return textOnly;
  }
  
  public static int wordCount(String s){
	    if (s == null)
	       return 0;
	    return s.trim().split("\\s+").length;
	}
  public int countWord(String url,String word) throws IOException{
	  
	  ReadWebPage web2= new ReadWebPage();
	  String page = web2.getURL(url);
	  String words = ReadWord.extractText(page);
	  String[] allwords = words.split(" ");
	  for(int i=0; i<allwords.length;i++)
	    {
	    	if(allwords[i].equals(word))
	    	{
				count++;
	    	}	
	    }
	  return count;
  }

  public final static void main(String[] args) throws Exception{
	String url;  
	ReadWebPage web2= new ReadWebPage();
  	Scanner sc = new Scanner(System.in);
  	System.out.println("Enter URL:");
  	url = sc.next();
  	System.out.println("Enter word to search:");
  	String specificwordcount=sc.next();
    String page = web2.getURL(url);
    String words = ReadWord.extractText(page);
    String[] allwords = words.split(" ");
    count = 0;
    /*for(int i=0; i<allwords.length;i++)
    {
    	System.out.println(allwords[i]);
    }*/
    for(int i=0; i<allwords.length;i++)
    {
    	if(allwords[i].equals(specificwordcount))
    	{
			count++;
    	}	
    }
    System.out.println("Total number of words in the link:"+ReadWord.wordCount(words));
    System.out.println("Number of times "+ specificwordcount +" repeated:"+count);
  }
}