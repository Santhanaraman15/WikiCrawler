import java.io.*;
import java.util.Scanner;
import java.util.regex.*;
import java.lang.StringBuilder;
public class ReadPageAndFindHyperlinks {
    public String findHyperlinks(String url){
        int count = 0;
        StringBuilder sb1 = new StringBuilder();
        ReadWebPage web2= new ReadWebPage();
        String page = web2.getURL(url);
        Pattern pattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
        Matcher matcher = pattern.matcher(page);
        while (matcher.find()) {
            String match = matcher.group(); 
            Pattern pattern1 = Pattern.compile("android-app|action=edit|.png|.ico|.php|creativecommons.org|wikimedia|wikidata|mediawiki");
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
                if(str.substring(1,6).equals("/wiki"))
                    sb.append("http://www.en.wikipedia.org");
                sb.append(str.substring(1,str.length()-1));
                if((sb.substring(0, 2)).equals("//")||(sb.charAt(0)=='#'))
                    continue;
                //System.out.println(sb.toString());
                sb1.append(sb);
                //if(str.length()>15)
                //System.out.println(str.substring(6,15));
                count ++;
            }
        }
        //System.out.println(count);
        return sb1.toString();
  
    }
    public static void main(String[] arguments) {
        ReadPageAndFindHyperlinks findlinks = new ReadPageAndFindHyperlinks();
        String url;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter URL:");
        url = sc.next();
        String links = findlinks.findHyperlinks(url);
        /*int count = 0;
        String url;
        ReadWebPage web2= new ReadWebPage();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter URL:");
        url = sc.next();
        String page = web2.getURL(url);
        Pattern pattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
        Matcher matcher = pattern.matcher(page);
        while (matcher.find()) {
            String match = matcher.group(); 
            Pattern pattern1 = Pattern.compile("android-app|action=edit|.png|.ico|.php|creativecommons.org|wikimedia|wikidata|mediawiki");
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
                if(str.substring(1,6).equals("/wiki"))
                    sb.append("http://www.en.wikipedia.org");
                sb.append(str.substring(1,str.length()-1));
                if((sb.substring(0, 2)).equals("//")||(sb.charAt(0)=='#'))
                    continue;
                System.out.println(sb.toString());
                //if(str.length()>15)
                //System.out.println(str.substring(6,15));
                count ++;
            }
        }
        System.out.println(count);*/
         
    }
 
    private static String loadPage(String name) {
        StringBuffer output = new StringBuffer();
        try {
            FileReader file = new FileReader(name);
            BufferedReader buff = new BufferedReader(file);
            boolean eof = false;
            while (!eof) {
                String line = buff.readLine();
                if (line == null)
                    eof = true;
                else
                    output.append(line + "\n");
            }
            buff.close();
        } catch (IOException e) {
            System.out.println("Error -- " + e.toString());
        }
        return output.toString();
    }
}