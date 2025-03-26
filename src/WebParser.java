import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
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
        String correctPage = "";
        Elements table = doc.select("table");
        // iterating over all tables
        for (Element t : table) {
            //if table is null go next
            if (t != null) {
                // if header of table is null go next (we want the name of the first part of the header)
                if (t.selectFirst("th") != null) {
                    // If first part of the header is Olympiad, then we found the correct table
                    if (t.selectFirst("th").text().equals("Olympiad")) {
                        // We get all the rows within that table
                        Elements rows = t.select("tr");
                        // We now iterate over the rows of the table
                        for (Element row : rows) {
                            // Check if first "td" tag within the row is not null
                            if (row.selectFirst("td") != null) {
                                // Check if we are inspecting the correct year/row
                                if (row.selectFirst("td").text().substring(0, 4).equals("" + year)) {
                                    // We get the link to the year's page
                                    if (row.select("td").size() >= 2) {
                                        if (row.select("td").get(1).selectFirst("a") != null) {
                                            Element link = row.select("td").get(1).selectFirst("a");
                                            correctPage = "https://en.wikipedia.org" + link.attr("href");   //ASK!!!!!!!!!!!!!!!
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Set<String> countries = new TreeSet<>();
        Document newDoc = fetchPage(correctPage);
        if (newDoc != null) {
            Elements table2 = newDoc.select("table");
            // iterating over all tables
            for (Element t : table2) {
                //if table is null go next
                if (t != null) {
                    // if header of table is null go next (we want the name of the first part of the header)
                    if (t.selectFirst("th") != null) {
                        // If first part of the header is Rank, then we found the correct table
                        if (t.selectFirst("th").text().equals("Rank")) {
                            // We get all the rows within that table
                            Elements rows = t.select("tr");
                            // We now iterate over the rows of the table
                            for (Element row : rows) {
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
                }
            }
        }
        for (String country : countries) {
            System.out.println(country);
        }
    }


    public static void questionFour (int year, Document doc) {
        String correctPage = "";
        Elements table = doc.select("table");
        // iterating over all tables
        for (Element t : table) {
            //if table is null go next
            if (t != null) {
                // if header of table is null go next (we want the name of the first part of the header)
                if (t.selectFirst("th") != null) {
                    // If first part of the header is Olympiad, then we found the correct table
                    if (t.selectFirst("th").text().equals("Olympiad")) {
                        // We get all the rows within that table
                        Elements rows = t.select("tr");
                        // We now iterate over the rows of the table
                        for (Element row : rows) {
                            // Check if first "td" tag within the row is not null
                            if (row.selectFirst("td") != null) {
                                // Check if we are inspecting the correct year/row
                                if (row.selectFirst("td").text().substring(0, 4).equals("" + year)) {
                                    // We get the link to the year's page
                                    if (row.select("td").size() >= 2) {
                                        if (row.select("td").get(1).selectFirst("a") != null) {
                                            Element link = row.select("td").get(1).selectFirst("a");
                                            correctPage = "https://en.wikipedia.org" + link.attr("href");   //ASK!!!!!!!!!!!!!!!
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // CHECK 1904!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ASK ERROR HANDLING!!!!!!!!!!!!!!!!!! ASK IF THESE ARE ALL PODIUM SWEEPS!

        Set<String> countries = new TreeSet<>();
        Document newDoc = fetchPage(correctPage);
        if (newDoc != null) {
            Elements table2 = newDoc.select("table");
            // iterating over all tables
            for (Element t : table2) {
                //if table is null go next
                if (t != null) {
                    if (t.selectFirst("th") != null) {
                        if (t.selectFirst("th").text().equals("Date")) {
                            Elements rows = t.select("tr");
                            for (Element row : rows) {
                                if (row.select("td").size() >= 3) {
                                    if (row.select("td").get(3).selectFirst("a") != null) {
                                        countries.add(row.select("td").get(3).selectFirst("a").text());
                                    }
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

    // HOW TO HANDLE THOSE WITH 0 !!!!!!!!!!!!!!!!!
    public static void questionFive(String country, String sport, Document doc) {
        String total = "None";
        // ASK THIS!!!!!!!!!!!!
        Elements links = doc.select(".hatnote.navigation-not-searchable");
        String correctPage = "https://en.wikipedia.org" + links.get(2).select("a").attr("href");
        Document newDoc = fetchPage(correctPage);
        if (newDoc != null) {
            Elements table = newDoc.select("table");
            // iterating over all tables
            for (Element t : table) {
                //if table is null go next
                if (t != null) {
                    // if header of table is null go next (we want the name of the first part of the header)
                    if (t.selectFirst("th") != null) {
                        // If first part of the header is Sport, then we found the correct table
                        if (t.selectFirst("th").text().equals("Team")) {
                            // We get all the rows within that table
                            Elements rows = t.select("tr");
                            // We now iterate over the rows of the table
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
                    }
                }
            }
        }


        if (newDoc != null) {
            Elements table = newDoc.select("table");
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
                                if (row.selectFirst("a") != null) {
                                    if (row.selectFirst("a").text().equals(sport)) {
                                        if (row.select("td").size() >= 4) {
                                            total = row.select("td").get(3).text();
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





}