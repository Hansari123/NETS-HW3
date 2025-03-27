import org.jsoup.nodes.Document;

import java.util.Map;
import java.util.Scanner;

public class InteractiveMain {
    public static void main(String[] args) {
        // Create a Scanner to enable interactivity
        Scanner scanner = new Scanner(System.in);
        Document homepage = WebParser.fetchPage("https://en.wikipedia.org/wiki/Summer_Olympic_Games");
        //System.out.println(homepage);
        if (homepage == null) {
            System.out.println("Failed to retrieve webpage");
            return;
        }

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
        System.out.println();
        System.out.println("Choose a question by typing 1-8 in the console!");
        while (true) {
            System.out.println("Question number (or 'exit'): ");
            // Take in user input on the next line
            String input = scanner.nextLine();
            // Now you can match on or use the user input with the 'input' variable
            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            } else if (input.equals("1")) {
                System.out.println("You picked: List all past and present Olympic sports that start with some letter.");
                System.out.println("Which letter would you like to inspect?");
                input = scanner.nextLine();
                while (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                    System.out.println("Which letter would you like to inspect?");
                    input = scanner.nextLine();
                }
                System.out.println("Getting all Olympic sports that start with the letter " + input.toUpperCase());
                WebParser.questionOne(input.toUpperCase(), homepage);
            } else if (input.equals("2")) {
                WebParser.questionTwo(homepage);
            } else if (input.equals("3")) {
                System.out.println("You picked: List all countries that have won at least some number of silver medals in some year.");
                boolean correctNumMedals = false;
                while (!correctNumMedals) {
                    System.out.println("What number of silver medals would you like to inspect?");
                    input = scanner.nextLine();
                    for (int i = 0; i < input.length(); i++) {
                        if (!Character.isDigit(input.charAt(i))) {
                            break;
                        }
                    }
                    correctNumMedals = true;
                }
                int numMedals = Integer.parseInt(input);
                boolean goodYear = false;
                int year = 0;
                while (!goodYear) {
                    System.out.println("Which year would you like to inspect?");
                    input = scanner.nextLine();
                    int numDigits  = 0;
                    if (input.length() == 4) {
                        for (int i = 0; i < 4; i++) {
                            if (Character.isDigit(input.charAt(i))) {
                                numDigits++;
                            }
                        }
                        if (numDigits == 4) {
                            year = Integer.parseInt(input);
                        }

                        if (year >= 1896 && year % 4 == 0 && year <= 2024) {
                            goodYear = true;
                        }
                    }
                }
                System.out.println();
                System.out.println("List of all countries that have won at least " + numMedals + " silver medals in " + year);
                WebParser.questionThree(numMedals, year, homepage);
            } else if (input.equals("4")) {
                System.out.println("You picked: List all countries that had podium sweeps in some year.");
                boolean goodYear = false;
                int year = 0;
                while (!goodYear) {
                    System.out.println("Which year would you like to inspect?");
                    input = scanner.nextLine();
                    int numDigits  = 0;
                    if (input.length() == 4) {
                        for (int i = 0; i < 4; i++) {
                            if (Character.isDigit(input.charAt(i))) {
                                numDigits++;
                            }
                        }
                        if (numDigits == 4) {
                            year = Integer.parseInt(input);
                        }

                        if (year >= 1896 && year % 4 == 0 && year <= 2024) {
                            goodYear = true;
                        }
                    }
                }
                System.out.println();
                System.out.println("List of all countries that had podium sweeps in " + year);
                WebParser.questionFour(year, homepage);
            } else if (input.equals("5")) {
                // ASSUMPTION: NO BAD INPUTS / CASING!
                System.out.println("You picked: Find how many total medals some country has won in a sport.");
                System.out.println("Pick a country.");
                String country = scanner.nextLine();
                System.out.println("Pick a sport");
                String sport = scanner.nextLine();
                System.out.println();
                System.out.println("Find how many total medals " + country + " won in " + sport);
                WebParser.questionFive(country, sport, homepage);
            } else if (input.equals("6")) {
                System.out.println("You picked: Find how many governing bodies of the past or present sports from the Summer Olympics are headquartered in some country.");
                System.out.println("Pick a country.");
                String country = scanner.nextLine();
                System.out.println("Find how many governing bodies of the past or present sports from the Summer Olympics are headquartered in " + country);
                WebParser.questionSix(country, homepage);
            } else if (input.equals("7")) {
                System.out.println(" ");
            } else if (input.equals("8")) {
                System.out.println(" ");
            }
        }
        scanner.close();
    }
}
