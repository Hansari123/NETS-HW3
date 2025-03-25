import org.jsoup.nodes.Document;
import java.util.Map;
public class ParserMain {
    public static void main(String[] args) {
        Document homepage = WebParser.fetchPage("https://en.wikipedia.org/wiki/Summer_Olympic_Games");
        System.out.println(homepage);
    }
}
