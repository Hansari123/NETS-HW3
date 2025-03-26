import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class WebParser {
    /*
     * Fetches and returns the Document for a given URL
     */
    public static Document fetchPage(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("Failed to fetch page: " + e.getMessage());
            return null;
        }
    }

    public static void questionOne(String letter, Document doc) {
        Set<String> names = new TreeSet<>();
        // selecting all tables
        Elements table = doc.select("table");
        // iterating over all tables
        for (Element t : table) {
            //if table is null go next
            if (t != null) {
                // if header of table is null go next (we want the name of the first part of the header)
                if (t.selectFirst("th") != null) {
                    // If first part of the header is Sport, then we found the correct table
                    if (t.selectFirst("th").text().equals("Sport")) {
                        // We get all the rows within that table
                        Elements rows = t.select("tr");
                        // We now iterate over the rows of the table
                        for (Element row : rows) {
                            // Check if first "td" tag within the row is not null
                            if (row.selectFirst("td") != null) {
                                // Check if first "a" instance is not null
                                if (row.selectFirst("td").selectFirst("a") != null) {
                                    // We get the text in the row which represents the sport name
                                    String sportName = row.selectFirst("td").selectFirst("a").text();
                                    names.add(sportName);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (String sport : names) {
            if (sport.substring(0, 1).equals(letter)) {
                System.out.println(sport);
            }
        }
    }

    public static void questionThree(int atLeast, int year, Document doc) {



    }






}
