import java.util.ArrayList;
/**
 * This class can try out URL connections
 */
public class URLTester {
    public static void main(String[] args) {
        URLGetter url = new URLGetter("http://nytimes.com");
        url.printStatusCode();
        ArrayList<String> page = url.getContents();
        for (String line : page) {
            System.out.println(line);
        }
        System.out.println(url.getRedirectURL());
    }
}
