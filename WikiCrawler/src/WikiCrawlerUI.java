import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollBar;


public class WikiCrawlerUI {

	private JFrame frame;
	public JTextField textField ;
	public static JTextArea textArea = new JTextArea();
	private static int final_count;
	private static int totalPages = 0;
	private static HashSet links = new HashSet();
	private JScrollPane scrollPane;
	private JLabel lblNewLabel_1;
	private static JTextField textField_1;
	public static JTextField textField_2;
	private static StringBuilder linkslist = new StringBuilder();
	private Thread t;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WikiCrawlerUI window = new WikiCrawlerUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WikiCrawlerUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 661, 454);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\t\t\t\t\t\t\t\t\tWiki Crawler");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(93, 0, 424, 45);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 76, 438, 45);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Search\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String Url = textField.getText();
				//Url = textField.getText();
				//CrawlHyperlinks crawl = new CrawlHyperlinks();
				//WikiCrawlerUI crawl = new WikiCrawlerUI();
				t = new Thread(new Runnable(){
					@Override
					public void run(){
						//while(!t.interrupted())
						String countWord = textField_1.getText();
							DriverMain.mainThread(Url,countWord);	
						try {
		                    Thread.sleep(1000);
		                } catch (InterruptedException e) {
		                    return;
		                }
					}
				});
				t.start();
				//crawlPage(Url);	
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton.setBounds(478, 75, 108, 45);
		frame.getContentPane().add(btnNewButton);
		//scroll = new JScrollPane (textArea, 
			//	   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scroll.setBounds(0, 334, 605, -334);
		//scroll = new JScrollPane(textArea);
		//scroll.setBounds(0, 4, 13, -4);
		//scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//frame.getContentPane().add(scroll);
		//frame.getContentPane().add(textArea);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 215, 412, 178);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);
		//textArea = new JTextArea();
		//frame.getContentPane().add(textArea);
		//textArea.setEditable(false);
		//textArea.setLineWrap(true);
		
		lblNewLabel_1 = new JLabel("Enter Word to Count");
		lblNewLabel_1.setBounds(10, 146, 180, 32);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(287, 146, 161, 32);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Word Count\r\n");
		lblNewLabel_2.setBounds(478, 226, 108, 32);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(478, 280, 108, 32);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		//frame.getContentPane().add(new JScrollPane(textArea));
	}
	private static void crawlPage(String url) {
		CrawlHyperlinks crawl = new CrawlHyperlinks();
		int wordCount = 0;
    	if(crawl.isValidURL(url)) {
    		if(links.contains(url)) {
    			System.out.println("already parsed URL:"+url);
    			//textArea.append("\nalready parsed URL:"+url+"\n");
    			linkslist.append("\nalready parsed URL:"+url+"\n");
    			System.out.println("");
    			//textArea.append("\n");
    			return;
    		}
    		else
    			links.add(url);
    		System.out.println("Crawling URL:"+url);
    		//textArea.append("\nCrawling Url:"+url+"\n");
    		linkslist.append("\nCrawling Url:"+url+"\n");
    		System.out.println("");
    		//textArea.append("\n");
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
            		String countWord = textField_1.getText();
    				ReadWord word = new ReadWord();
    				try {
    					wordCount = word.countWord(url, countWord);
    					final_count+=wordCount;
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            		//textArea.append(sb.toString());
    				linkslist.append(sb.toString());
            		//textField_2.setText(Integer.toString(final_count));
            		//sb1.append(sb);
            		//if(str.length()>15)
            		//System.out.println(str.substring(6,15));
    				totalPages++;
    				if(totalPages < 500) {
                		crawlPage(sb.toString());
                	} else {
                		System.out.println("Done crawling 500 pages");
                		//textArea.append("\nDone crawling 500 pages\n");
                		linkslist.append("\nDone crawling 500 pages\n");
                		return;
                	}

            	}
    		}
        } else {
        	System.out.println("Not a valid URL:"+url);
        	System.out.println("");
        }
        System.out.println(totalPages);
    }
}
