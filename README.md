# WikiCrawler

The Wikicrawler crawls through wikipedia and gives out the number of times a word has been repeated in all the pages. 
The program crawls only English wikipedia pages.
The program works concurrently with 50 threads and crawls upto 500 pages. 
The unique words are stored using a ConcurrentHashMap Data Structure.
The program works similar to producer-consumer problem storing the words from the wikipedia page in a queue and the ConcurrentHashMap is updated for everyword in the queue 
