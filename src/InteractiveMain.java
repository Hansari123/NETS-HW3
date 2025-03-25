import org.jsoup.nodes.Document;

import java.util.Map;
import java.util.Scanner;

public class InteractiveMain {
    public static void main(String[] args) {
        // Create a Scanner to enable interactivity
        Scanner scanner = new Scanner(System.in);
        Document homepage = WebParser.fetchPage("https://en.wikipedia.org/wiki/Summer_Olympic_Games");
        //System.out.println(homepage);
        if (homepage != null) {
            // CASING!!!!
            WebParser.questionOne("C", homepage);
        }

        /*
        System.out.println("Welcome to the Wikipedia Web Scraper!");
        System.out.println("We have scraped Summer Olympics data. You can:");
        System.out.println("1. List all past and present Olympic sports that start with some letter");
        System.out.println("2. List all countries (full name, not country code) that have participated in the Olympics, but are\n" +
                "now considered “obsolete”?");
        System.out.println("3. List all countries that have won at least some number of silver medals in some year.");
        System.out.println("4. List all countries that had podium sweeps in some year.");
        System.out.println("5. Find how many total medals some country has won in a sport.");
        System.out.println("6. Find how many governing bodies of the past or present sports from the Summer Olympics are\n" +
                "headquartered in some country.");
        System.out.println("7. Among all Summer Olympics hosted in some country since some date, find how many countries the\n" +
                "torch relay that covered the longest total distance passed through");
        System.out.println("8. Wild card – come up with an interesting question. List the question and find the answer to it");
        System.out.println("Choose a question by typing 1-8 in the console!");
        while (true) {
            System.out.print("Question number (or 'exit'): ");
            // Take in user input on the next line
            String input = scanner.nextLine();
            // Now you can match on or use the user input with the 'input' variable
            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            } else if (input.equals("1")) {
                System.out.println(" ");
            } else if (input.equals("2")) {
                System.out.println(" ");
            } else if (input.equals("3")) {
                System.out.println(" ");
            } else if (input.equals("4")) {
                System.out.println(" ");
            } else if (input.equals("5")) {
                System.out.println(" ");
            } else if (input.equals("6")) {
                System.out.println(" ");
            } else if (input.equals("7")) {
                System.out.println(" ");
            } else if (input.equals("8")) {
                System.out.println(" ");
            }
        }
        scanner.close();

         */
    }
}
