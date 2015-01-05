public class Monitor implements Runnable {

  private HyperLinksList hyperLinksList;
  private static final int MAXLINKS = 500;

  public Monitor(HyperLinksList hyperLinksList) {
    this.hyperLinksList = hyperLinksList;
  }

  public void run() {
    while (true) {
      synchronized(hyperLinksList) {
        if (hyperLinksList.size() > MAXLINKS) {
          //System.out.println("Too many items in the queue: " + hyperLinksList.size() + "!");
        } else if (hyperLinksList.size() == 0) {
          //System.out.println("Queue empty!");
          if(CrawlHyperlinksThreaded.noOfLinks == MAXLINKS) {
        	  System.out.println("finished crawling, now return");
        	  WikiCrawlerUI.textArea.append("\nfinished crawling, now return");
        	  return;
          }
        }
      }
      try {
        Thread.sleep(500);
      } catch (Exception ex) {
      }
    }
  }

}