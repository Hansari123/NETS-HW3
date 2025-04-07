import org.jsoup.nodes.Document;
import java.util.Scanner;

public class InteractiveMain {
    /**
     * This is the interactive main method. We will prompt users to pick a question.
     * Users can enter their inputs to the questions, and we will provide an answer
     * based on the information we scraped from the Wikipedia Summer Olympics page.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Document homepage = WebParser.fetchPage("https://en.wikipedia.org/wiki/Summer_Olympic_Games");
        if (homepage == null) {
            System.out.println("Failed to retrieve webpage");
            return;
        }
        printQuestions();
        while (true) {
            System.out.println("Question number (or 'exit'): ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            } else if (input.equals("1")) {
                handleQuestionOne(scanner, homepage);
            } else if (input.equals("2")) {
                handleQuestionTwo(homepage);
            } else if (input.equals("3")) {
                handleQuestionThree(scanner, homepage);
            } else if (input.equals("4")) {
                handleQuestionFour(scanner, homepage);
            } else if (input.equals("5")) {
                handleQuestionFive(scanner, homepage);
            } else if (input.equals("6")) {
                handleQuestionSix(scanner, homepage);
            } else if (input.equals("7")) {
                handleQuestionSeven(scanner, homepage);
            } else if (input.equals("8")) {
                handleQuestionEight(scanner, homepage);
            }
        }
        scanner.close();
    }

    /**
     * This method prints out the list of 8 questions that we will answer for the user.
     * This is its own method to avoid clutter in the main method above.
     */
    public static void printQuestions() {
        System.out.println("Welcome to the Wikipedia Web Scraper!");
        System.out.println("We have scraped Summer Olympics data. You can:");
        System.out.println("1. List all past and present Olympic sports that start with some letter");
        System.out.println("2. List all countries that have participated in the Olympics, but are now considered “obsolete”.");
        System.out.println("3. List all countries that have won at least some number of silver medals in some year.");
        System.out.println("4. List all countries that had podium sweeps in some year.");
        System.out.println("5. Find how many total medals some country has won in a sport.");
        System.out.println("6. Find how many governing bodies of the past or present sports from the Summer Olympics are\n" +
                "headquartered in some country.");
        System.out.println("7. Among all Summer Olympics hosted in some country since some date, find how many countries the\n" +
                "torch relay that covered the longest total distance passed through");
        System.out.println("8. Wild card: Find the city and current Mayor of the city in which the Summer Olympics of some year took place.");
        System.out.println();
        System.out.println("Choose a question by typing 1-8 in the console!");
    }

    /**
     * This method handles all the print statements, user prompting, and answer printing
     * related to question 1. We prompt the user to enter a letter. From there, we call
     * our WebParser class to solve the question based on the user's input.
     *
     * @param scanner The scanner object we are using to interact with the terminal/user
     * @param homepage The document we are parsing
     */
    public static void handleQuestionOne(Scanner scanner, Document homepage) {
        System.out.println("You picked: List all past and present Olympic sports that start with some letter.");
        System.out.println("Which letter would you like to inspect?");
        String input = scanner.nextLine();
        while (input.length() != 1) {
            System.out.println("Which letter would you like to inspect?");
            input = scanner.nextLine();
        }
        System.out.println("Getting all Olympic sports that start with the letter " + input.toUpperCase());
        WebParser.questionOne(input.toUpperCase(), homepage);
    }

    /**
     * This method handles all the print statements and answer printing related to question 2.
     * There is no need for us to prompt users in this case since we are printing a list.
     *
     * @param homepage The document we are parsing
     */
    public static void handleQuestionTwo(Document homepage) {
        System.out.println("You picked: List all countries that have participated in the Olympics, but are now considered “obsolete” ");
        System.out.println("Obsolete countries:");
        WebParser.questionTwo(homepage);
    }

    /**
     * This method handles all the print statements, user prompting, and answer printing
     * related to question 3. We first check if a valid number of silver medals is entered
     * by ensuring that it is a number. From there, we check if a valid year has been entered.
     * If both entries are valid, we call our WebParser class to solve the question based on
     * the user's input.
     *
     * @param scanner The scanner object we are using to interact with the terminal/user
     * @param homepage The document we are parsing
     */
    public static void handleQuestionThree(Scanner scanner, Document homepage) {
        System.out.println("You picked: List all countries that have won at least some number of silver medals in some year.");
        boolean correctNumMedals = false;
        String input = "";
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
        int year = handleYear(scanner);
        System.out.println();
        System.out.println("Listing of all countries that have won at least " + numMedals + " silver medals in " + year);
        WebParser.questionThree(numMedals, year, homepage);
    }

    /**
     * This method handles all the print statements, user prompting, and answer printing
     * related to question 4. We first check if a valid year has been entered. If so, we
     * call our WebParser class to solve the question based on the user's input.
     *
     * @param scanner The scanner object we are using to interact with the terminal/user
     * @param homepage The document we are parsing
     */
    public static void handleQuestionFour(Scanner scanner, Document homepage) {
        System.out.println("You picked: List all countries that had podium sweeps in some year.");
        int year = handleYear(scanner);
        System.out.println();
        System.out.println("Listing of all countries that had podium sweeps in " + year);
        WebParser.questionFour(year, homepage);
    }

    /**
     * This method handles all the print statements, user prompting, and answer printing
     * related to question 5. We prompt the user for a country and a sport, and we call our
     * WebParser class to solve the question based on the user's input.
     *
     * @param scanner The scanner object we are using to interact with the terminal/user
     * @param homepage The document we are parsing
     */
    public static void handleQuestionFive(Scanner scanner, Document homepage) {
        System.out.println("You picked: Find how many total medals some country has won in a sport.");
        System.out.println("Pick a country.");
        String country = scanner.nextLine();
        System.out.println("Pick a sport");
        String sport = scanner.nextLine();
        System.out.println();
        System.out.println("Finding how many total medals " + country + " won in " + sport);
        WebParser.questionFive(country, sport, homepage);
    }

    /**
     * This method handles all the print statements, user prompting, and answer printing
     * related to question 6. We prompt the user for a country, and we call our WebParser
     * class to solve the question based on the user's input.
     *
     * @param scanner The scanner object we are using to interact with the terminal/user
     * @param homepage The document we are parsing
     */
    public static void handleQuestionSix(Scanner scanner, Document homepage) {
        System.out.println("You picked: Find how many governing bodies of past or present sports from the Summer Olympics are headquartered in some country.");
        System.out.println("Pick a country.");
        String country = scanner.nextLine();
        System.out.println("Finding how many governing bodies of past or present sports from the Summer Olympics are headquartered in " + country + "...");
        WebParser.questionSix(country, homepage);
    }

    /**
     * This method handles all the print statements, user prompting, and answer printing
     * related to question 7. We prompt the user for a country and a year. We make sure that
     * the year is valid. And we call our WebParser class to solve the question based on the user's input.
     *
     * @param scanner The scanner object we are using to interact with the terminal/user
     * @param homepage The document we are parsing
     */
    public static void handleQuestionSeven(Scanner scanner, Document homepage) {
        System.out.println("You picked: Among all Summer Olympics hosted in some country since some date, find how many countries the torch relay that covered the longest total distance passed through.");
        System.out.println("Pick a country.");
        String country = scanner.nextLine();
        int year = handleYear(scanner);
        System.out.println();
        System.out.println("Among all Summer Olympics hosted in " + country + " since " + year + " finding how many countries the torch relay that covered the longest total distance passed through...");
        WebParser.questionSeven(country, year, homepage);
    }

    /**
     * This method handles all the print statements, user prompting, and answer printing
     * related to question 8. We prompt the user for a year, and make sure that the year is valid.
     * We create a WebParser object because question 8 works with instance variables.We call our WebParser
     * class to solve the question based on the user's input.
     *
     * @param scanner The scanner object we are using to interact with the terminal/user
     * @param homepage The document we are parsing
     */
    public static void handleQuestionEight(Scanner scanner, Document homepage) {
        System.out.println("You picked: Wild card: Find the city and current Mayor of the city in which the Summer Olympics of some year took place.");
        int year = handleYear(scanner);
        System.out.println();
        System.out.println("Finding the city and current Mayor of the city in which the Summer Olympics of " + year + " took place");
        WebParser w = new WebParser();
        w.questionEight(year, homepage);
    }

    /**
     * This method prompts the user to enter a year until a year between 1896 and
     * 2024 has been entered. The year also has to be a year in which the Olympics
     * was held. Once all the conditions are met, we return the valid year that the
     * user wants to inspect.
     *
     * @param scanner The scanner object we are using to interact with the terminal/user
     * @return returns the valid year that the user selected to inspect
     */
    public static int handleYear(Scanner scanner) {
        boolean goodYear = false;
        int year = 0;
        String input = "";
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
        return year;
    }
}
