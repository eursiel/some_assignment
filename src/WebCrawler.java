
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kangj
 */
public class WebCrawler {
    
    public static Queue<String> queue = new LinkedList<>();
    public static Set<String> marked = new HashSet<>();
    public static String regex = "http[s]*://(\\w+\\.)*(\\w+)";
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp4|zip|gz))$");
    public static String yahooLink = "https://sg.search.yahoo.com/search;?p=";
    public static String bingLink = "https://www.bing.com/search?q=";
    
    public static void main(String args[])
    {
        String getQuery = JOptionPane.showInputDialog(null,"Enter query to search for","Crawler",JOptionPane.INFORMATION_MESSAGE);
        StringBuilder yahooSb = PageRead.readPage(yahooLink+getQuery.replace(" ", "+"));
        StringBuilder bingSb = PageRead.readPage(bingLink+getQuery.replace(" ", "+"));
        System.out.println("Crawling URL: " + yahooLink+getQuery.replace(" ", "+"));
        System.out.println("Yahoo html content : --------------------------------------------------------------\n"+yahooSb+"\n----------------------------------------------------------------------------");
        System.out.println("Crawling URL: " + bingLink+getQuery.replace(" ", "+"));
        System.out.println("Bing html content : --------------------------------------------------------------\n"+bingSb+"\n----------------------------------------------------------------------------");
        
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(yahooSb.toString());
        
        
        System.out.println("------------------------Looking for match in :" + yahooLink+getQuery.replace(" ", "+"));
        //Yahoo : Fifth match
        while(matcher.find())
        {
             String w = matcher.group();
                
                if(!marked.contains(w) ){
                    System.out.println("Found Match : " + w);
                }
        }
        
         matcher = pattern.matcher(bingSb.toString());
        
        System.out.println("------------------------Looking for match in " + bingLink+getQuery.replace(" ", "+"));
        while(matcher.find())
        {
             String w = matcher.group();
                
                if(!marked.contains(w)){
                    System.out.println("Found Match : " + w);
                }
        }
    }
}
