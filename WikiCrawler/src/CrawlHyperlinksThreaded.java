import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;

public class CrawlHyperlinksThreaded implements Runnable{
	public static Set<String> mySet = new HashSet<String>();
	private HyperLinksList hyperLinksList;
	private static StringBuilder nextHyperLink = new StringBuilder("");
	public static int count = 1;
	public static int noOfLinks = 0;
	public static final int MAXLINKS = 500;
	public static int wordCount = 0;
	public String searchWord;
	
	
	public CrawlHyperlinksThreaded(HyperLinksList queue, String word) {
		this.searchWord = word;
		this.hyperLinksList = queue;
	}

	
	public void run() {
		while(noOfLinks <= MAXLINKS) {
			if(hyperLinksList.size() > 0) {
				synchronized(hyperLinksList) {
					nextHyperLink = hyperLinksList.retrieve();
					WikiCrawlerUI.textArea.append(nextHyperLink.toString());
					WikiCrawlerUI.textArea.append("\n");
					hyperLinksList.notifyAll();
				}
				crawlThisLink(nextHyperLink.toString());
			} else {
				synchronized(hyperLinksList) {
					try {
						if(noOfLinks == MAXLINKS) {
							System.out.println("returning from thread id:"+Thread.currentThread().getId());
							WikiCrawlerUI.textArea.append("\nreturning from thread id:"+Thread.currentThread().getId());
							return;
						} else {
							hyperLinksList.wait(500);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private static boolean isValidURL(String url) {
        UrlValidator urlValidator = new UrlValidator();
        urlValidator.isValid(url);
        ReadPageAndFindHyperlinks web = new ReadPageAndFindHyperlinks();
        if(web.findHyperlinks(url).equals("Exception"))
            return false;
        else
            return true;
    }
	
	public static String extractText(String url) throws IOException {
	    String textOnly = Jsoup.parse(url).text();
	    return textOnly;
	  }
	   
	public static int wordCount(String s){
	    if (s == null)
	    	return 0;
	    else
	        return s.trim().split("\\s+").length;
	}
	
	private int countWords(String page) {
		int countWordsTemp = 0;
		String words;
		try {
			words = extractText(page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			words = "";
			e.printStackTrace();
		}
	    String[] allwords = words.split(" ");
	    for(int i=0; i<allwords.length;i++) {
	        if(allwords[i].equals(searchWord)) {
	        	countWordsTemp++;
	        }   
	    }
	    return countWordsTemp;
	}
	

	
	private void crawlWebPage(String url) {
		System.out.println("No of links crawled is "+noOfLinks+" with thread "+Thread.currentThread().getId()+" and the URL being crawled is:"+url);
		WikiCrawlerUI.textArea.append("\nNo of links crawled is "+noOfLinks+" with thread "+Thread.currentThread().getId()+" and the URL being crawled is:"+url);
        System.out.println("");
        ReadWebPage web2= new ReadWebPage();
        String page = web2.getURL(url);
        synchronized(this) {
        	wordCount = wordCount + countWords(page);
        }
        Pattern pattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
        Matcher matcher = pattern.matcher(page);
        while (matcher.find()) {
        	String match = matcher.group(); 
        	Pattern pattern1 = Pattern.compile("android-app|action=edit|.png|.ico|.php|creativecommons.org|wikimedia|wikidata|mediawiki|quot|.rss|.svg|.jpg");
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
        		synchronized(this) {
        			if(count < MAXLINKS) {
        				//count++;
        				if(mySet.contains(sb.toString())) {
        					System.out.println("Already crawled link "+sb);
        					WikiCrawlerUI.textArea.append("\nAlready crawled link "+sb);
        					
        				} else {
        					count++;
        					mySet.add(sb.toString());
        					hyperLinksList.append(sb);
        				}
        			}
        		}
        		//System.out.println(sb.toString());
        		//sb1.append(sb);
        		//if(str.length()>15)
        		//System.out.println(str.substring(6,15));
        	}
        }
    }
	
	private void crawlThisLink(String link) {
		if(link.equals("")) {
			//Do nothing
		} else {
			if(isValidURL(link)) {
				synchronized(this) {
					if(noOfLinks>=MAXLINKS) {
						System.out.println("Maximum number of links crawled. Exiting");
						WikiCrawlerUI.textArea.append("\nMaximum number of links crawled. Exiting");
						return;
					} else {
						noOfLinks++;
					}
				}
				crawlWebPage(link);
			}
		}
	}
}
