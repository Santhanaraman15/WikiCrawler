import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
   
public class ReadWebPage {
    public String getURL(String s){
        URL url;
        String urlcontent = new String(); 
        try {
            // get URL content
            url = new URL(s);
            URLConnection conn = url.openConnection();
   
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
   
            String inputLine;
            StringBuilder sb = new StringBuilder();
            //save to this filename
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
                sb.append(System.getProperty("line.separator"));
            }
            urlcontent = sb.toString();
            br.close();
   
            //System.out.println("Done");
        } catch (MalformedURLException e) {
            System.out.println("Enter proper URL");
            return "Exception";
        } catch (IOException e) {
            //e.printStackTrace();
            return "Exception";
        }
        return urlcontent;
    }
    public static void main(String[] args) {
        String url,urlcontent;
        ReadWebPage web1 = new ReadWebPage();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter URL:");
        url = sc.next();
        sc.close();
        urlcontent = web1.getURL(url);
        System.out.println(urlcontent);
    }
}