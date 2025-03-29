import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.lang.constant.DynamicCallSiteDesc;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebParser {
    private HashMap<String, String> locationToMayor;
    String currentCity;

    public WebParser() {
        locationToMayor = new HashMap<>();
        currentCity = "";
    }

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

    //ASSUMPTION: SUMMER OLYMPICS

    // 1916!!!!!!!!!!!!!!!

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

    // ASK ABOUT COLOR!
    public static void questionTwo(Document doc) {
        String correctPage = "";
        Set<String> countries = new TreeSet<>();
        Elements links = doc.select(".div-col");
        if (links != null) {
            for (Element l : links) {
                if (l.selectFirst("li") != null) {
                    if (l.selectFirst("li").selectFirst("a") != null) {
                        if (l.selectFirst("li").selectFirst("a").text().equals("List of participating nations at the Summer Olympic Games")) {
                            correctPage = "https://en.wikipedia.org" + l.selectFirst("li").selectFirst("a").attr("href");
                        }
                    }
                }
            }
        }
        Document newDoc = fetchPage(correctPage);
        if (newDoc != null) {
            Elements rows = searchTableGetRows("A", newDoc);
            for (Element r : rows) {
                if (r.selectFirst("td") != null) {
                    if (r.selectFirst("td").attr("bgcolor").equals("#e0e0e0")) {
                        if (r.selectFirst("a") != null) {
                            countries.add(r.selectFirst("a").text());
                        }
                    }
                }
            }
        }

        for (String c : countries) {
            System.out.println(c);
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

    // CHECK 1916!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ASK ERROR HANDLING!!!!!!!!!!!!!!!!!! ASK IF THESE ARE ALL PODIUM SWEEPS!
    public static void questionFour(int year, Document doc) {
        String correctPage = getYearPage(searchTableGetRows("Olympiad", doc), year, doc);
        if (correctPage.isEmpty()) {
            System.out.println("None");
        } else {
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

    public static void questionSix(String country, Document doc) {
        Set<String> governingBodies = new TreeSet<>();
        // ASSUMPTION: VALID COUNTRY GIVEN
        // UNITED KINGDOM!!!!!!!!!!
        // HEADQUARTER THEN RIGHT AFTER IS THE COUNTRY
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
                                                                                        if (country.equals("United States")) {
                                                                                            if (r3.selectFirst("td").text().contains(country) || r3.selectFirst("td").text().contains("U.S.")) {
                                                                                                total++;
                                                                                            }
                                                                                        } else if (r3.selectFirst("td").text().contains(country)) {
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

    public static void questionSevenEh(String country, int year, Document doc) {
        String correctPage = "";
        Elements rows = searchTableGetRows("Olympiad", doc);
        for (Element row : rows) {
            if (row.selectFirst("td") != null) {
                // ASSUMING THIS WILL ALWAYS BE AN INTEGER
                if (Integer.parseInt(row.selectFirst("td").text().substring(0,4)) >= year) {
                    if (row.select("td").size() >= 3) {
                        if (row.select("td").get(2).selectFirst("a") != null) {
                            if (row.select("td").get(2).selectFirst("a").attr("title") != null) {
                                if (row.select("td").get(2).selectFirst("a").attr("title").equals(country)) {
                                    if (row.select("td").get(1).selectFirst("a") != null) {
                                        Element link = row.select("td").get(1).selectFirst("a");
                                        correctPage = "https://en.wikipedia.org" + link.attr("href");
                                        System.out.println(correctPage);
                                    } else {
                                        if (row.selectFirst("th") != null) {
                                            if (Integer.parseInt(row.selectFirst("th").text().substring(0,4)) >= year) {
                                                if (row.selectFirst("a") != null) {
                                                    correctPage = "https://en.wikipedia.org" + row.selectFirst("a").attr("href");
                                                    System.out.println(correctPage);
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
            if (!correctPage.isEmpty()) {
                Document newDoc = fetchPage(correctPage);
                if (newDoc != null) {

                }

            }





        }



    }


    public static void questionSeven(String country, int year, Document doc) {
        // GET THE YEARS, GET THE LIST OF TORCH RELAYS, CHECK THE YEARS
        boolean countryFound = false;
        Set<String> countries = new TreeSet<>();
        Set<String> correctYears = new TreeSet<>();
        Elements rows = searchTableGetRows("Olympiad", doc);
        for (Element row : rows) {
            if (row.selectFirst("td") != null) {
                // ASSUMING THIS WILL ALWAYS BE AN INTEGER
                if (Integer.parseInt(row.selectFirst("td").text().substring(0,4)) >= year && Integer.parseInt(row.selectFirst("td").text().substring(0,4)) <= 2024) {
                    if (row.select("td").size() >= 3) {
                        if (row.select("td").get(2).selectFirst("a") != null) {
                            if (row.select("td").get(2).selectFirst("a").attr("title") != null) {
                                if (row.select("td").get(2).selectFirst("a").attr("title").contains(country)) {
                                    correctYears.add(row.selectFirst("td").text().substring(0,4));
                                    countryFound = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!countryFound) {
            System.out.println("Invalid Host Country (potentially for the provided year)!");
        } else {
            int count = 0;
            for (String y : correctYears) {
                if (Integer.parseInt(y) < 1936) {
                    count++;
                }
            }
            if (count == correctYears.size()) {
                System.out.println("No torch relay info available at this host country. 1936 started the torch relays");
                return;
            }
            int finalCount = 0;
            boolean entered = false;
            int longestDistance = 0;
            String page = "";
            String listPage = getListOfTorchRelaysPage(doc);
            if (!listPage.isEmpty()) {
                Document newDoc = fetchPage(listPage);
                if (newDoc != null) {
                    Elements rows2 = searchTableGetRows("Site of the Olympic Games", newDoc);
                    for (Element r2 : rows2) {
                        //ASSUME ALL CLICKABLE
                        if (r2.selectFirst("td") != null) {
                            if (r2.selectFirst("td").selectFirst("a") != null) {
                                String pattern = ".*(\\d{4}).*";
                                Pattern p = Pattern.compile(pattern);
                                Matcher m = p.matcher(r2.selectFirst("td").select("a").get(r2.selectFirst("td").select("a").size() - 1).text());
                                if (m.find()) {
                                    for (String s : correctYears) {
                                        if (m.group(1).equals(s)) {
                                            if (r2.select("td").get(2) != null) {
                                                if (!r2.select("td").get(2).text().equals("-") && Integer.parseInt(removeCommas(r2.select("td").get(2).text())) > longestDistance) {
                                                    longestDistance = Integer.parseInt(removeCommas(r2.select("td").get(2).text()));
                                                    year = Integer.parseInt(s);
                                                    if (r2.select("td").get(4).selectFirst("a") != null) {
                                                        if (r2.select("td").get(4).selectFirst("a").text().equals(year + " Summer Olympics torch relay")) {
                                                            page = "https://en.wikipedia.org" + r2.select("td").get(4).selectFirst("a").attr("href");
                                                            entered = false;
                                                        } else {
                                                            entered = true;
                                                            page = "";
                                                            String parse = r2.select("td").get(4).text();
                                                            for (int i = 0; i < parse.length(); i++) {
                                                                if (parse.charAt(i) == '(') {
                                                                    if (i < parse.length() - 3) {
                                                                        if (parse.charAt(i + 1) == 'b' && parse.charAt(i + 2) == 'y') {
                                                                            continue;
                                                                        } else {
                                                                            finalCount++;
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

            if (!entered) {
                finalCount = 1;
                if (!page.isEmpty()) {
                    Document d = fetchPage(page);
                    if (d != null) {
                        Elements rows4 = searchTableContains("Countries visited", d);
                        for (Element r4: rows4) {
                            if (r4.selectFirst("th") != null) {
                                if (r4.selectFirst("th").text().equals("Countries visited")) {
                                    if (r4.selectFirst("td") != null) {
                                        for (int i = 0; i < r4.selectFirst("td").text().length(); i++) {
                                            if (r4.selectFirst("td").text().charAt(i) == ',') {
                                                finalCount++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(finalCount); //IRELAND PART OF THE UK???????????????????????
        }
    }

    public static String removeCommas(String distance) {
        String fixed = "";
        for (int i = 0; i < distance.length(); i++) {
            if (distance.charAt(i) != ',') {
                fixed = fixed + distance.charAt(i);
            }

        }
        return fixed;
    }

    public static String getListOfTorchRelaysPage(Document doc) {
        String correctPage = getYearPage(searchTableGetRows("Olympiad", doc), 1936, doc); //HARDCODE??????????????
        if (!correctPage.isEmpty()) {
            Document newDoc = fetchPage(correctPage);
            Elements words = newDoc.select("p");
            for (Element p : words) {
                if (p.selectFirst("a") != null) {
                    if (p.selectFirst("a").text().equals("first of its kind")) { //HARDCODE??????????????????
                        return "https://en.wikipedia.org" + p.selectFirst("a").attr("href");
                    }
                }
            }
        }
        return "";
    }

    public void questionEight(int year, Document doc) {
        String city = "";
        String mayor = "";
        boolean foundCity = false;
        String correctPage = getYearPage(searchTableGetRows("Olympiad", doc), year, doc);
        if (!correctPage.isEmpty()) {
            Document newDoc = fetchPage(correctPage);
            if (newDoc != null) {
                Elements table = newDoc.select("table");
                for (Element t : table) {
                    if (t != null && !foundCity) {
                        if (t.select("tr") != null) {
                            Elements rows = t.select("tr");
                            for (Element r : rows) {
                                if (r.selectFirst("th") != null) {
                                    if (r.selectFirst("th").text().equals("Location")) {
                                        if (r.selectFirst("td") != null ) {
                                            if (!r.selectFirst("td").text().equals("")) {
                                                city = r.selectFirst("td").text().split(",")[0].trim();
                                                foundCity = true;
                                            }
                                            if (r.selectFirst("a") != null) {
                                                if (r.selectFirst("a").text().equals(city)) {
                                                    correctPage = "https://en.wikipedia.org" + r.selectFirst("a").attr("href");
                                                }
                                                foundCity = true;
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
        if (!correctPage.isEmpty()) {
            Document finalDoc = fetchPage(correctPage);
            boolean updated = false;
            if (finalDoc != null) {
                Elements tables = finalDoc.select("table");
                for (Element t : tables) {
                    if (t != null) {
                        if (t.select("tr") != null) {
                            Elements rs = t.select("tr");
                            for (Element e : rs) {
                                if (e.selectFirst("a") != null && !updated) {
                                    if (e.selectFirst("a").text().contains("Mayor") || e.selectFirst("a").text().contains("Lord mayor")) { // HARDCODE????
                                        if (e.select("a").size() > 1) {
                                            if (e.select("a").get(1).text().length() > 3) { //HARDCODE!!!!!!!!!!!!!!!!!!!!!
                                                mayor = e.select("a").get(1).text();
                                                if (!locationToMayor.containsKey(city)) {
                                                    locationToMayor.put(city, mayor);
                                                }
                                                updated = true;
                                            }
                                        }
                                    } else if (!updated) {
                                        if (e.selectFirst("th") != null) {
                                            if (e.selectFirst("th").text().contains("Mayor") || e.selectFirst("a").text().contains("Lord mayor")) {
                                                if (e.selectFirst("a") != null) {
                                                    if (e.selectFirst("a").text().length() > 3) { //HARDCODE!!!!!!!!!!!!!!!!!!!!!
                                                        mayor = e.selectFirst("a").text();
                                                        if (!locationToMayor.containsKey(city)) {
                                                            locationToMayor.put(city, mayor);
                                                        }
                                                        updated = true;
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
        if (!city.isEmpty()) {
            if (locationToMayor.get(city) == null) {
                System.out.println("The city was " + city + " but we were unable to find information on the mayor!");
            } else {
                System.out.println("The city was " + city + " and the current mayor is " + locationToMayor.get(city));
            }
        }
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

    private static Elements searchTableContains(String name, Document doc) {
        Elements table = doc.select("table");
        // iterating over all tables
        for (Element t : table) {
            //if table is null go next
            if (t != null) {
                // if header of table is null go next (we want the name of the first part of the header)
                if (t.select("th") != null) {
                    for (Element x : t.select("th")) {
                        if (x.text().equals(name)) {
                            return t.select("tr");
                        }
                    }
                }
            }
        }
        return new Elements();
    }

    private static Elements searchTableContainsTable(String name, Document doc) {
        Elements table = doc.select("table");
        // iterating over all tables
        for (Element t : table) {
            //if table is null go next
            if (t != null) {
                // if header of table is null go next (we want the name of the first part of the header)
                if (t.select("th") != null) {
                    for (Element x : t.select("th")) {
                        if (x.text().equals(name)) {
                            if (t.select("a") != null) {
                                return t.select("a");
                            }
                        }
                    }
                }
            }
        }
        return new Elements();
    }

    public static String getYearPage(Elements rows, int year, Document doc) {
        if (year == 1916 || year == 1940 || year == 1944) {
            System.out.println("Summer Olympic Games were canceled in " + year);
            return "";
        }
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
            } else {
                if (row.selectFirst("th") != null) {
                    if (row.selectFirst("th").text().substring(0,4).equals("" + year)) {
                        if (row.selectFirst("a") != null) {
                            correctPage = "https://en.wikipedia.org" + row.selectFirst("a").attr("href");
                        }
                    }
                }
            }
        }
        return correctPage;
    }
}