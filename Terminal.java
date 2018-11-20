import java.io.*;
import java.util.Scanner;
import java.lang.Exception;
import java.util.List;

public class Terminal {
    private Scanner input = new Scanner(System.in);
    private int itemSelectionNumber;
    private CommandCode enteredCommandCode;

    public static void initiateLoginPrompt() 
    {
        Terminal session = new Terminal();
        while (LoginSession.noneActive()) {
            session.promptUserForId();
        }
    }

    private void promptUserForId() {
        try {
            LoginSession.startSessionByUserId(getNextIdEntered());
        } catch (LoginFailed e) {
            printError(e.getMessage());
        }
    }

    private String getNextIdEntered() {
        printInfo("Please enter your member or staff id:");
        int userId = getIntFromUser();
        return String.valueOf(userId);
    }

    private int getIntFromUser() {
        while (!input.hasNextInt()) {
            input.next();
        }
        return input.nextInt();
    }

    private void printError(String errorMessage) {
        System.err.println(errorMessage);
    }

    private void printInfo(String message) {
        System.out.println(message);
    }

    public static void printGreeting(String userName) {
        new Terminal().printInfo(String.format("Welcome, %s!\n", userName));
    }

    public static void printInfoMessage(String infoMessage) {
        new Terminal().printInfo(infoMessage);
    }

    public static void printErrorMessage(String errorMessage) {
        new Terminal().printError(errorMessage);
    }

    public static CommandCode getCommandCodeFromBasicUser() {
        Terminal session = new Terminal();
        session.promptBasicUserForCommandCode();
        session.getCommandCodeFromUser();
        return session.enteredCommandCode;
    }

    public static CommandCode getCommandCodeFromNonAdminStaffMember() {
        Terminal session = new Terminal();
        session.promptNonAdminStaffMemberForCommandCode();
        session.getCommandCodeFromUser();
        return session.enteredCommandCode;
    }

    public static CommandCode getCommandCodeFromAdmin() {
        Terminal session = new Terminal();
        session.promptAdminForCommandCode();
        session.getCommandCodeFromUser();
        return session.enteredCommandCode;
    }

    private void promptAdminForCommandCode() {
        presentBasicCommandOptions();
        presentStaffOptions();
        presentCatalogUpdateOptions();
    }

    private void promptNonAdminStaffMemberForCommandCode() {
        presentBasicCommandOptions();
        presentStaffOptions();
    }

    private void promptBasicUserForCommandCode() {
        presentBasicCommandOptions();
    }

    private void presentBasicCommandOptions() {
        printInfo("Select from the following options:\n");
        printInfo("Press '*' to logout");
        printInfo("Press '1' to borrow a book");
        printInfo("Press '2' to return a book");
    }

    private void getCommandCodeFromUser() {
        while (input.hasNext()) {
            try {
                char codeEntered = input.next().charAt(0);
                enteredCommandCode = new CommandCode(codeEntered);
                break;
            } catch (InvalidCommandCode e) {
                printError(e.getMessage());
            }
        }
    }

    private void presentStaffOptions() {
        printInfo("Press '3' to check out a journal");
        printInfo("Press '4' to borrow a journal");
        printInfo("Press 'q' to shutdown the system");
    }

    private void presentCatalogUpdateOptions() {
        printInfo("Press '+' to add an item to the catalog");
    }

    public static String haveUserSelectIdFromCollection(List<? extends BorrowableItem> items, String messagePrompt) {
        Terminal session = new Terminal();
        session.printInfo(messagePrompt);
        session.listOutItemsAndGetItemSelectionFromUser(items);
        return items.get(session.itemSelectionNumber - 1).getItemId();
    }

    private void listOutItemsAndGetItemSelectionFromUser(List<? extends BorrowableItem> items) {
        listOutItems(items);
        while (true) {
            itemSelectionNumber = getIntFromUser();
            if (itemSelectionNumber < 1 || itemSelectionNumber > items.size()) {
                printError("Invalid selection. Please try again.");
            } else {
                break;
            }
        }
    }

    private void listOutItems(List<? extends BorrowableItem> items) {
        for (int i = 0; i < items.size(); i++) {
            printInfo(String.format("%d: %s", i + 1, items.get(i).toString()));
        }
    }

    public static LibraryInventoryItem getNewItemInformationFromUser() {
        Terminal session = new Terminal();
        int selection;
        do {
            session.printInfo("Press '1' to add a book copy or '2' to add a journal issue:");
            selection = session.getIntFromUser();
            if (selection != 1 && selection != 2) {
                session.printError("Invalid input.");
            } else {
                break;
            }
        } while (true);

        return (selection == 1) ? session.getBookInformationFromUser() : session.getJournalInformationFromUser();
    }

    private Book getBookInformationFromUser() {
        printInfo("Enter ISBN:");
        String isbn = getStringFromUser();
        printInfo("Enter Title:");
        String title = getStringFromUser();
        printInfo("Enter Author:");
        String author = getStringFromUser();
        return new Book(isbn, title, author);
    }

    private Journal getJournalInformationFromUser() {
        printInfo("Enter ISSN:");
        String issn = getStringFromUser();
        printInfo("Enter Title:");
        String title = getStringFromUser();
        printInfo("Enter Publisher:");
        String publisher = getStringFromUser();
        printInfo("Enter Publication Year:");
        String publicationYear = String.valueOf(getIntFromUser());
        return new Journal(issn, title, publisher, publicationYear);
    }

    private String getStringFromUser() {
        return input.nextLine();
    }
}