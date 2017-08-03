
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
public class WebCrawler extends Thread {

    public static Queue<String> yahooQueue = new LinkedList<>();
    public static Set<String> yahooMarked = new HashSet<>();
    public static Queue<String> bingQueue = new LinkedList<>();
    public static Set<String> bingMarked = new HashSet<>();

    public static Queue<String> queue = new LinkedList<>();
    public static Set<String> marked = new HashSet<>();
    public static String regex = "(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp4|zip|gz))$");
    public static String yahooLink = "https://sg.search.yahoo.com/search;?p=";
    public static String bingLink = "https://www.bing.com/search?q=";

    public static void main(String args[]) {
        String getQuery = JOptionPane.showInputDialog(null, "Enter query to search for", "Crawler", JOptionPane.INFORMATION_MESSAGE);
        StringBuilder yahooSb = PageRead.readPage(yahooLink + getQuery.replace(" ", "+"));
        StringBuilder bingSb = PageRead.readPage(bingLink + getQuery.replace(" ", "+"));
        System.out.println("Crawling URL: " + yahooLink + getQuery.replace(" ", "+"));
        System.out.println("Yahoo html content : --------------------------------------------------------------\n" + yahooSb + "\n----------------------------------------------------------------------------");
        System.out.println("Crawling URL: " + bingLink + getQuery.replace(" ", "+"));
        System.out.println("Bing html content : --------------------------------------------------------------\n" + bingSb + "\n----------------------------------------------------------------------------");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(yahooSb.toString());

        System.out.println("------------------------Looking for match in :" + yahooLink + getQuery.replace(" ", "+"));
        while (matcher.find()) {

            String w = matcher.group();

            if (!yahooMarked.contains(w) && Filter.filterMiscLinks(w)) {
                yahooMarked.add(w);
                System.out.println("Found Match : " + w);
                yahooQueue.add(w);
            }
            if (yahooMarked.size() >= 2) {
                break;
            }
        }

        matcher = pattern.matcher(bingSb.toString());

        System.out.println("------------------------Looking for match in " + bingLink + getQuery.replace(" ", "+"));
        while (matcher.find()) {
            String w = matcher.group();
            if (!bingMarked.contains(w) && Filter.filterMiscLinks(w)) {
                bingMarked.add(w);
                System.out.println("Found Match : " + w);
                bingQueue.add(w);
            }
            if (bingMarked.size() >= 2) {
                break;
            }
        }

        Thread thread1 = new Thread() {
            public void run() {
                for (String yahooLink : yahooQueue) {
                    StringBuilder firstContent = PageRead.readPage(yahooLink);
                    Matcher matcher = pattern.matcher(firstContent.toString());

                    System.out.println("------------------------Threading :" + yahooLink);
                    while (matcher.find()) {
                        String w = matcher.group();

                        if (!marked.contains(w) && Filter.filterMiscLinks(w)) {
                            marked.add(w);
                            System.out.println("Found Match : " + w);
                            queue.add(w);
                        }
                        if (marked.size() >= 10) {
                            return;
                        }
                    }

                }
            }
        };
        
        Thread thread2 = new Thread() {
            public void run() {
                for (String bingLink : bingQueue) {
                    StringBuilder secondContent = PageRead.readPage(bingLink);
                    Matcher matcher = pattern.matcher(secondContent.toString());

                    System.out.println("Threading :" + bingLink);
                    while (matcher.find()) {
                        String w = matcher.group();

                        if (!marked.contains(w) && Filter.filterMiscLinks(w)) {
                            marked.add(w);
                            System.out.println("Found Match : " + w);
                            queue.add(w);
                        }
                        if (marked.size() >= 10) {
                            return;
                        }
                    }

                }
            }
        };
        

    }
}
