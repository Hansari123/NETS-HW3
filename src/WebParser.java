import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebParser {
    private HashMap<String, String> locationToMayor;

    /**
     * This is our WebParser constructor. It initializes our
     * locationToMayor instance variable. We add the city as
     * a key and the value as the mayor. This way we are able
     * to store all the cities and their corresponding mayors
     * in a compact fashion.
     */
    public WebParser() {
        locationToMayor = new HashMap<>();
    }

    /**
     * This method takes in a URL and fetches the corresponding page
     * if it exists and outputs it as a document.
     *
     * @param url The String url of the page we are interested in
     * @return The document version of the provided String url
     */
    public static Document fetchPage(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("Failed to fetch page: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method finds all the past and present Olympic sports that
     * start with some specified letter. We search for a table whose
     * name is "Sport". Then, we store the names of all the sports we
     * found by iterating over the table. We output only those that
     * match the specified letter.
     *
     * @param letter The letter for which we want to find Olympic sports
     * @param doc The document we will be searching over to find the sports
     */
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

    /**
     * This method finds the list of all countries that have participated in the Olympics,
     * but are now considered "obsolete". We search for a link called "List of participating
     * nations at the Summer Olympic Games". We go to the page of that link, and we search for
     * a table called "A". Then, we store all the "obsolete" nations based on the color of the
     * table entry. We output all the obsolete nations found
     *
     * @param doc The document we will be searching over to find the obsolete nations
     */
    public static void questionTwo(Document doc) {
        String correctPage = "";
        Set<String> countries = new TreeSet<>();
        Elements links = doc.select(".div-col");
        if (links != null) {
            for (Element l : links) {
                if (l.selectFirst("li") != null) {
                    if (l.selectFirst("li").selectFirst("a") != null) {
                        if (l.selectFirst("li").selectFirst("a").text().equals("List of participating nations at the Summer Olympic Games")) {
                            correctPage = l.selectFirst("li").selectFirst("a").attr("abs:href");
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

    /**
     * This method finds the list of countries that have won some number
     * of silver medals in a given year. We first look for a table called
     * "Olympiad" and we go to the link relating to the user's preferred year.
     * From there, we find a table called Rank and store all the countries
     * who have >= "atLeast" silver medals. We output all of these countries.
     *
     * @param atLeast The minimum number of silver medals we are looking for
     * @param year The year for which we are inspecting the silver medals
     * @param doc The document we will begin at for our search for the countries of interest
     */
    public static void questionThree(int atLeast, int year, Document doc) {
        String correctPage = getYearPage(searchTableGetRows("Olympiad", doc), year, doc);
        Set<String> countries = new TreeSet<>();
        if (!correctPage.isEmpty()) {
            Document newDoc = fetchPage(correctPage);
            if (newDoc != null) {
                Elements rows2 = searchTableGetRows("Rank", newDoc);
                for (Element row : rows2) {
                    if (row.select("td").size() >= 3) {
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

    /**
     * This method finds the list of countries that had podium sweeps in a given year.
     * We go to the year of interest page found in the "Olympiad" table. From there,
     * we find the "Date" table and output the countries found.
     *
     * @param year The year for which we want to find podium sweeps
     * @param doc The document we will begin at to find the podium sweeps
     */
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

    /**
     * This method finds the total number of medals some country won in
     * some sport. We will first look for the "All-time" medals link.
     * From there, we will go to the team table and go to the link for
     * the country of interest. We go to the sport tables and find the
     * sport of interest. From there, we output the total.
     *
     * @param country The country whose total medals we are finding
     * @param sport The sport whose medals we are interested in
     * @param doc The document we will begin at to find the total medals
     */
    public static void questionFive(String country, String sport, Document doc) {
        String total = "None";
        String correctPage = "";
        Elements links = doc.select("a");
        if (links != null) {
            for (Element link : links) {
                if (link.text().equals("All-time Olympic Games medal table")) {
                    correctPage = link.attr("abs:href");
                }
            }
        }
        System.out.println(correctPage);
        if (!correctPage.isEmpty()) {
            Document newDoc = fetchPage(correctPage);
            if (newDoc != null) {
                Elements rows = searchTableGetRows("Team", newDoc);
                for (Element row : rows) {
                    if (row.selectFirst("td") != null) {
                        if (row.selectFirst("td").selectFirst("a") != null) {
                            if (row.selectFirst("td").selectFirst("a").text().equals(country)) {
                                Element link = row.selectFirst("td").selectFirst("a");
                                correctPage = link.attr("abs:href");
                                newDoc = fetchPage(correctPage);
                            }
                        }
                    }
                }
            }
            if (newDoc != null) {
                Elements rows2 = searchTableGetRows("Sport", newDoc);
                for (Element row : rows2) {
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
        System.out.println(total);
    }

    /**
     * This method finds the number of governing bodies of the past or present sports
     * from the Summer Olympics which are located in a specific country. We will
     * iterate over all the rows of the sports table, go to the link, find the link
     * called "governing bodies" and find headquarters and count if our country of
     * interest shows up. We return the total count.
     *
     * @param country The country we want to inspect as a headquarter
     * @param doc The document at which we begin our search
     */
    public static void questionSix(String country, Document doc) {
        Set<String> governingBodies = new TreeSet<>();
        int total = 0;
        Elements rows = searchTableGetRows("Sport", doc);
        for (Element row : rows) {
            if (row.selectFirst("td") != null) {
                if (row.selectFirst("td").selectFirst("a") != null) {
                    String correctPage = row.selectFirst("td").selectFirst("a").attr("abs:href");
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
                                                        newDoc = fetchPage(r2.selectFirst("td").selectFirst("a").attr("abs:href"));
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

    /**
     * We want to find among all Summer Olympics hosted in some country since some year,
     * the number of countries the longest distance torch relay passed through. We will go
     * to the torch relays page, iterate over all the distances corresponding
     * to the specified country, and returning the count of countries for the largest
     * distance relay.
     *
     * @param country The country whose torch relay we are interested in
     * @param year The year we want to use as our starting point
     * @param doc The document we will begin our search with
     */
    public static void questionSeven(String country, int year, Document doc) {
        String correctPage = "";
        Elements links = doc.select("a");
        if (links != null) {
            for (Element link : links) {
                if (link.text().equals("Torch relays")) {
                    correctPage = link.attr("abs:href");
                }
            }
        }
        boolean countryFound = false;
        boolean entered = false;
        String newPage = "";
        int currentYear = year;
        int longestDistance = 0;
        int finalCount = 0;
        if (!correctPage.isEmpty()) {
            Document newDoc = fetchPage(correctPage);
            if (newDoc != null) {
                Elements rows = searchTableGetRows("Site of the Olympic Games", newDoc);
                for (Element row : rows) {
                    if (row.selectFirst("td") != null) {
                        if (row.selectFirst("td").selectFirst("a") != null) {
                            if (row.selectFirst("td").selectFirst("a").attr("title").contains(country)) {
                                String pattern = ".*(\\d{4}).*";
                                Pattern p = Pattern.compile(pattern);
                                Matcher m = p.matcher(row.selectFirst("td").select("a").get(row.selectFirst("td").select("a").size() - 1).text());
                                if (m.find()) {
                                    if (Integer.parseInt(m.group(1)) >= year) {
                                        if (row.select("td").get(2) != null) {
                                            if (country.equals("France") || (!row.select("td").get(2).text().equals("-") && !row.select("td").get(2).text().equals("") && Integer.parseInt(removeCommas(row.select("td").get(2).text())) > longestDistance)) {
                                                currentYear = Integer.parseInt(m.group(1));
                                                countryFound = true;
                                                if (!country.equals("France")) {
                                                    longestDistance = Integer.parseInt(removeCommas(row.select("td").get(2).text()));
                                                }
                                                if (row.select("td").get(4).selectFirst("a") != null) {
                                                    if (row.select("td").get(4).selectFirst("a").text().equals(currentYear + " Summer Olympics torch relay")) {
                                                        newPage = row.select("td").get(4).selectFirst("a").attr("abs:href");
                                                        entered = false;
                                                    } else {
                                                        entered = true;
                                                        newPage = "";
                                                        String parse = row.select("td").get(4).text();
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
            if (!countryFound) {
                System.out.println("Invalid Host Country (potentially for the provided year)!");
                return;
            }
            if (!entered) {
                finalCount = 1;
                if (!newPage.isEmpty()) {
                    Document d = fetchPage(newPage);
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
            System.out.println(finalCount);
        }
    }

    /**
     * This method finds the city and current Mayor of the city in which the Summer Olympics of some year took place.
     * We go to the year page, we find the host city, we follow the link, we search for mayor, and return the
     * name and city.
     *
     * @param year The year whose Olympics we are interested in
     * @param doc The document we will begin our search at
     */
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
                                                    correctPage = r.selectFirst("a").attr("abs:href");
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
                                    if (e.selectFirst("a").text().contains("Mayor") || e.selectFirst("a").text().contains("Lord mayor")) {
                                        if (e.select("a").size() > 1) {
                                            if (e.select("a").get(1).text().length() > 3) {
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
                                                    if (e.selectFirst("a").text().length() > 3) {
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

    /**
     * Looks for the table with a name equal to "tableName".
     * Returns the rows of the found table or empty if not found.
     *
     * @param tableName The name we are searching for
     * @param doc The document over which we are looking for the table
     * @return returns the rows of the found table (empty if not found)
     */
    private static Elements searchTableGetRows(String tableName, Document doc) {
        Elements table = doc.select("table");
        for (Element t : table) {
            if (t != null) {
                if (t.selectFirst("th") != null) {
                    if (t.selectFirst("th").text().equals(tableName)) {
                        return t.select("tr");
                    }
                }
            }
        }
        return new Elements();
    }

    /**
     * This method takes in a string and removes all the commas
     * from the string and returns the comma-less string.
     *
     * @param distance the String from which to remove commas
     * @return returns the String with commas removed
     */
    public static String removeCommas(String distance) {
        String fixed = "";
        for (int i = 0; i < distance.length(); i++) {
            if (distance.charAt(i) != ',') {
                fixed = fixed + distance.charAt(i);
            }
        }
        return fixed;
    }

    /**
     * Looks for the table with a name containing the substring "name".
     * Returns the rows of the found table or empty if not found.
     *
     * @param name The name we are searching for
     * @param doc The document over which we are looking for the table
     * @return returns the rows of the found table (empty if not found)
     */
    private static Elements searchTableContains(String name, Document doc) {
        Elements table = doc.select("table");
        for (Element t : table) {
            if (t != null) {
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

    /**
     * This method serves as a helper method. It will iterate over the rows
     * of some table and find if the year matches the specified year. If so,
     * it gets the associated link and returns it. If the years are 1916, 1940,
     * or 1944, it just prints out that the games were cancelled in those years.
     *
     * @param rows The rows of the table we are inspecting
     * @param year The year we are interested
     * @param doc The document we will be searching over
     * @return returns a String representation of the correct page
     */
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
                            correctPage = link.attr("abs:href");
                        }
                    }
                }
            } else {
                if (row.selectFirst("th") != null) {
                    if (row.selectFirst("th").text().substring(0,4).equals("" + year)) {
                        if (row.selectFirst("a") != null) {
                            correctPage = row.selectFirst("a").attr("abs:href");
                        }
                    }
                }
            }
        }
        return correctPage;
    }
}