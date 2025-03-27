import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.*;

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
        Elements rows = searchTableGetRows("Sport", doc);
        for (Element row : rows) {
            if (row.selectFirst("td") != null) {
                if (row.selectFirst("td").selectFirst("a") != null) {
                    names.add(row.selectFirst("td").selectFirst("a").text());
                }
            }
        }
        boolean entered = false;
        for (String sport : names) {
            if (sport.substring(0, 1).equals(letter)) {
                System.out.println(sport);
                entered = true;
            }
        }
        if (!entered) {
            System.out.println("None");
        }
    }

    public static void questionTwo(Document doc) {
        // BASE URL???????????
        Set<String> countryCodes = new TreeSet<>();
        Elements links = doc.select(".div-col");
        String correctPage = "https://en.wikipedia.org" + links.get(1).select("a").attr("href");
        Document newDoc = fetchPage(correctPage);
        if (newDoc != null) {
            if (newDoc.select("ul").size() >= 16) {
                Element link = newDoc.select("ul").get(16);
                System.out.println(link);
                for (Element l : link) {
                    System.out.println(l);
                }
            }
        }
    }

    public static void questionThree(int atLeast, int year, Document doc) {
        String correctPage = getYearPage(searchTableGetRows("Olympiad", doc), year, doc);
        Set<String> countries = new TreeSet<>();
        if (!correctPage.isEmpty()) {
            Document newDoc = fetchPage(correctPage);
            if (newDoc != null) {
                Elements rows2 = searchTableGetRows("Rank", newDoc);
                for (Element row : rows2) {
                    // Check if first "td" tag within the row is not null
                    if (row.select("td").size() >= 3) {
                        // Check if we are inspecting the correct year/row
                        if (Integer.parseInt(row.select("td").get(2).text()) >= atLeast) {
                            if (row.selectFirst("th") != null) {
                                if (row.selectFirst("th").selectFirst("a") != null) {
                                    String country = row.selectFirst("th").selectFirst("a").text();
                                    countries.add(country);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (countries.isEmpty()) {
            System.out.println("None");
        } else {
            for (String country : countries) {
                System.out.println(country);
            }
        }
    }

    // CHECK 1904!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ASK ERROR HANDLING!!!!!!!!!!!!!!!!!! ASK IF THESE ARE ALL PODIUM SWEEPS!
    public static void questionFour(int year, Document doc) {
        String correctPage = getYearPage(searchTableGetRows("Olympiad", doc), year, doc);
        Set<String> countries = new TreeSet<>();
        Document newDoc = fetchPage(correctPage);
        if (newDoc != null) {
            Elements rows = searchTableGetRows("Date", newDoc);
            for (Element row: rows) {
                if (row.select("td").size() >= 3) {
                    if (row.select("td").get(3).selectFirst("a") != null) {
                        countries.add(row.select("td").get(3).selectFirst("a").text());
                    }
                }
            }
        }
        if (countries.isEmpty()) {
            System.out.println("None");
        } else {
            for (String country : countries) {
                System.out.println(country);
            }
        }
    }

    // HOW TO HANDLE THOSE WITH 0 !!!!!!!!!!!!!!!!!
    public static void questionFive(String country, String sport, Document doc) {
        String total = "None";
        // ASK THIS!!!!!!!!!!!!
        Elements links = doc.select(".hatnote.navigation-not-searchable");
        String correctPage = "https://en.wikipedia.org" + links.get(2).select("a").attr("href");
        Document newDoc = fetchPage(correctPage);
        if (newDoc != null) {
            Elements rows = searchTableGetRows("Team", newDoc);
            for (Element row : rows) {
                // Check if first "td" tag within the row is not null
                if (row.selectFirst("td") != null) {
                    // Check if first "a" instance is not null
                    if (row.selectFirst("td").selectFirst("a") != null) {
                        if (row.selectFirst("td").selectFirst("a").text().equals(country)) {
                            Element link = row.selectFirst("td").selectFirst("a");
                            correctPage = "https://en.wikipedia.org" + link.attr("href");
                            newDoc = fetchPage(correctPage);
                        }
                    }
                }
            }
        }
        if (newDoc != null) {
            Elements rows2 = searchTableGetRows("Sport", newDoc);
            for (Element row : rows2) {
                // Check if first "td" tag within the row is not null
                if (row.selectFirst("a") != null) {
                    if (row.selectFirst("a").text().equals(sport)) {
                        if (row.select("td").size() >= 4) {
                            total = row.select("td").get(3).text();
                        }
                    }
                }
            }
        }
        System.out.println(total);
    }

    // SHOULD I GET UNIQUE?
    // A LOT OF THESE DONT HAVE GOVERNING BODIES???? // CAN I DO CONTAINS??????? // first table???? HOW TO GET THE ONE WITH THE GOVERNING BODY? // PERFORMANCE????
    public static void questionSix(String country, Document doc) {
        Set<String> governingBodies = new TreeSet<>();
        int total = 0;
        Elements rows = searchTableGetRows("Sport", doc);
        for (Element row : rows) {
            if (row.selectFirst("td") != null) {
                if (row.selectFirst("td").selectFirst("a") != null) {
                    String correctPage = "https://en.wikipedia.org" + row.selectFirst("td").selectFirst("a").attr("href");
                    Document newDoc = fetchPage(correctPage);
                    if (newDoc != null) {
                        Elements table1 = newDoc.select("table");
                        for (Element t1 : table1) {
                            if (t1 != null) {
                                if (t1.select("tr") != null) {
                                    Elements rows2 = t1.select("tr");
                                    for (Element r2 : rows2) {
                                        if (r2.selectFirst("th") != null) {
                                            if (r2.selectFirst("th").text().equals("Governing body")) {
                                                if (r2.selectFirst("td") != null) {
                                                    if (r2.selectFirst("td").selectFirst("a") != null) {
                                                        if (governingBodies.contains(r2.selectFirst("td").selectFirst("a").text())) {
                                                            continue;
                                                        }
                                                        governingBodies.add(r2.selectFirst("td").selectFirst("a").text());
                                                        newDoc = fetchPage("https://en.wikipedia.org" + r2.selectFirst("td").selectFirst("a").attr("href"));
                                                        if (newDoc != null) {
                                                            Elements table = newDoc.select("table");
                                                            for (Element t : table) {
                                                                if (t != null) {
                                                                    if (t.select("tr") != null) {
                                                                        Elements rows3 = t.select("tr");
                                                                        for (Element r3 : rows3) {
                                                                            if (r3.selectFirst("th") != null) {
                                                                                if (r3.selectFirst("th").text().equals("Headquarters")) {
                                                                                    if (r3.selectFirst("td") != null) {
                                                                                        if (r3.selectFirst("td").text().contains(country)) {
                                                                                            total++;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(total);
    }

    private static void questionSeven(String country, String year, Document doc) {

    }

    private static Elements searchTableGetRows(String tableName, Document doc) {
        Elements table = doc.select("table");
        // iterating over all tables
        for (Element t : table) {
            //if table is null go next
            if (t != null) {
                // if header of table is null go next (we want the name of the first part of the header)
                if (t.selectFirst("th") != null) {
                    // If first part of the header is Sport, then we found the correct table
                    if (t.selectFirst("th").text().equals(tableName)) {
                        // We get all the rows within that table
                        return t.select("tr");
                    }
                }
            }
        }
        return new Elements();
    }

    private static Elements searchTableContains(String tableName, Document doc) {
        Elements table = doc.select("table");
        // iterating over all tables
        for (Element t : table) {
            //if table is null go next
            if (t != null) {
                // if header of table is null go next (we want the name of the first part of the header)
                if (t.selectFirst("th") != null) {
                    // If first part of the header is Sport, then we found the correct table
                    if (t.selectFirst("th").text().contains(tableName)) {
                        // We get all the rows within that table
                        return t.select("tr");
                    }
                }
            }
        }
        return new Elements();
    }

    public static String getYearPage(Elements rows, int year, Document doc) {
        String correctPage = "";
        for (Element row : rows) {
            if (row.selectFirst("td") != null) {
                if (row.selectFirst("td").text().substring(0,4).equals("" + year)) {
                    if (row.select("td").size() >= 2) {
                        if (row.select("td").get(1).selectFirst("a") != null) {
                            Element link = row.select("td").get(1).selectFirst("a");
                            correctPage = "https://en.wikipedia.org" + link.attr("href");   //ASK!!!!!!!!!!!!!!!
                        }
                    }
                }
            }
        }
        return correctPage;
    }
}