import java.util.Scanner;


public class DriverMain {
	private static final int THREADS = 50;

	public static void mainThread(String url,String countWord) {
		long start = System.currentTimeMillis();
		HyperLinksList hyperLinksList = new HyperLinksList();
		
		Scanner sc = new Scanner(System.in);
        //System.out.println("Enter URL:");
        //String url = sc.next();
        //WikiCrawlerUI UI = new WikiCrawlerUI();
        //String url = UI.textField.getText();
        //UI.textField.setText(url);
        hyperLinksList.append(new StringBuilder(url));
       // System.out.println("Enter the keyword:");
        String word = countWord;
        //sc.close();
        sc.close();
        
        Thread monitor = new Thread(new Monitor(hyperLinksList));
		monitor.setDaemon(true);
	    monitor.start();
        
        Thread[] crawlers = new Thread[THREADS];
       // System.out.println("Creating Threads");	
		for (int i = 0; i < crawlers.length; i++) {
			crawlers[i] = new Thread(new CrawlHyperlinksThreaded(hyperLinksList, word));
		//	 System.out.println("Creating Threads");	
		    crawlers[i].start();
		}
		
		for (int i = 0; i < crawlers.length; i++) {
		    try {
		      crawlers[i].join();
		    } catch (Exception ex) {
		    	//do nothing
		    }
		}
		long end = System.currentTimeMillis();
		System.out.println("total time : "+((end-start)/1000)+" seconds");
		WikiCrawlerUI.textArea.append("\ntotal time : "+((end-start)/1000)+" seconds");
		System.out.println("total occurences for word: "+word+" are: "+CrawlHyperlinksThreaded.wordCount);
		WikiCrawlerUI.textField_2.setText(Integer.toString(CrawlHyperlinksThreaded.wordCount));
	}

}
