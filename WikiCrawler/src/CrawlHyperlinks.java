import java.util.Scanner;
import java.util.regex.*;
import java.util.*;

import org.apache.commons.validator.routines.UrlValidator;
public class CrawlHyperlinks {
	private static int totalPages = 0;
	private static HashSet links = new HashSet();
	
    public static void main(String[] arguments) {
        String url;
        //ReadWebPage web2= new ReadWebPage();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter URL:");
        url = sc.next();
        sc.close();
        crawlPage(url);
    }
    
    static boolean isValidURL(String url) {
    	UrlValidator urlValidator = new UrlValidator();
    	urlValidator.isValid(url);
    	ReadPageAndFindHyperlinks web = new ReadPageAndFindHyperlinks();
    	if(web.findHyperlinks(url).equals("Exception"))
    		return false;
    	else
    		return true;
    }
    
    static void crawlPage(String url) {
    	if(isValidURL(url)) {
    		if(links.contains(url)) {
    			System.out.println("already parsed URL:"+url);
    			System.out.println("");
    			return;
    		}
    		else
    			links.add(url);
    		System.out.println("Crawling URL:"+url);
    		System.out.println("");
    		ReadWebPage web2= new ReadWebPage();
    		String page = web2.getURL(url);
    		Pattern pattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
    		Matcher matcher = pattern.matcher(page);
    		while (matcher.find()) {
    			String match = matcher.group(); 
            	Pattern pattern1 = Pattern.compile("android-app|action=edit|.png|.ico|.php|creativecommons.org|wikimedia|wikidata|mediawiki|quot|.rss|.svg");
            	Matcher matcher1 = pattern1.matcher(match);
            	if(matcher1.find())
            		continue;
            	if(match.length()>9){
            		StringBuilder sb = new StringBuilder();
            		String str =  match.substring(6, match.length());
            		if(str.length()<17)
            			continue;
            		if(str.substring(6, 15).equals("wikipedia")||(str.substring(7, 16).equals("wikipedia"))){
            			if(!str.substring(3, 4).equals("en"))
            			continue;
            		}
            		if(str.substring(1,6).equals("/wiki")||str.charAt(1)=='/')
            			sb.append("http://www.en.wikipedia.org");
            		sb.append(str.substring(1,str.length()-1));
            		if((sb.substring(0, 2)).equals("//")||(sb.charAt(0)=='#'))
            			continue;
            		System.out.println(sb.toString());
            		//sb1.append(sb);
            		//if(str.length()>15)
            		//System.out.println(str.substring(6,15));
    				totalPages++;
    				if(totalPages < 500) {
                		crawlPage(sb.toString());
                	} else {
                		System.out.println("Done crawling 500 pages");
                		return;
                	}

            	}
    		}
        } else {
        	System.out.println("Not a valid URL:"+url);
        	System.out.println("");
        }
        //System.out.println(totalPages);
    }
}