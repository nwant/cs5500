import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class LibrarySystem {
    private static LibrarySystem instance;
    private final String CATALOG_SYSTEM_FILE_LOCATION = "sys/catalog.ser";
    private final String DIRECTORY_SYSTEM_FILE_LOCATION = "sys/directory.ser";

    private File catalogSystemFile = new File(CATALOG_SYSTEM_FILE_LOCATION);
    private File directorySystemFile = new File(DIRECTORY_SYSTEM_FILE_LOCATION);
    private Catalog catalogService;
    private Directory directoryService;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        instance = new LibrarySystem();
        instance.startup();
    }

    public static void shutdownRequestOccurred() throws IOException {
        instance.shutdown();
    }

    public static Boolean directoryContainsId(String memberOrStaffId) {
        return instance.directoryService.containsId(memberOrStaffId);
    }

    public static LibraryMember directoryLookup(String memberOrStaffId) {
        return instance.directoryService.lookup(memberOrStaffId);
    }

    public static void addToCatalog(Copy newCopy) {
        instance.catalogService.addCopy(newCopy);
    }

    public static void addToCatalog(Issue newIssue) {
        instance.catalogService.addIssue(newIssue);
    }

    private void startup() throws IOException, ClassNotFoundException {
        startServices();
        Terminal.initiateLoginPrompt();
    }

    private void startServices() throws IOException, ClassNotFoundException {
        if (foundSystemServiceFiles()) {
            reloadSystemServices();
        } else {
            initializeSystemServices();
        }
    }

    private Boolean foundSystemServiceFiles() {
        return catalogSystemFile.exists() && directorySystemFile.exists();
    }

    private void reloadSystemServices() throws IOException, ClassNotFoundException {
        loadCatalog();
        loadDirectory();
    }

    private void initializeSystemServices() {
        createCatalog();
        createDirectory();
    }

    private void loadCatalog() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(catalogSystemFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        catalogService = (Catalog) ois.readObject();
        ois.close();
        fis.close();
    }

    private void loadDirectory() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(directorySystemFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        directoryService = (Directory) ois.readObject();
        ois.close();
        fis.close();
    }

    private void createCatalog() {
        catalogService = new Catalog();
        catalogService.addCopies(getDefaultCopiesOfBooks());
        catalogService.addIssues(getDefaultIssuesOfBooks());
    }

    private List<Copy> getDefaultCopiesOfBooks() {
        List<Copy> bookCopies = new ArrayList<Copy>();
        bookCopies.addAll(createBookCopies(new Book("9780441172719", "Dune", "Frank Herbert"), 2));
        bookCopies
                .addAll(createBookCopies(new Book("9780547928210", "The Fellowship of the Ring", "J.R.R. Tolkien"), 3));
        bookCopies.addAll(
                createBookCopies(new Book("9780345391803", "Hitchhicker's Guide to the Galaxy", "Douglas Adams"), 2));
        bookCopies.addAll(createBookCopies(new Book("9780441007462", "Neuromancer", "William Gibson"), 2));
        bookCopies.addAll(createBookCopies(new Book("9781876963460", "The Stars My Destination", "Alfred Bester"), 2));
        bookCopies.addAll(createBookCopies(new Book("9781535001885", "The War of the Worlds", "H.G. Wells"), 2));
        bookCopies.addAll(createBookCopies(new Book("9780553293357", "Foundation", "Isaac Asimov"), 2));
        bookCopies.addAll(createBookCopies(new Book("9780060850524", "Brave New World", "Aldous Huxley"), 2));
        bookCopies.addAll(createBookCopies(new Book("9780060850524", "The Martian Cronicles", "Ray Bradbury"), 2));
        bookCopies.addAll(
                createBookCopies(new Book("9780441790340", "Stranger in a Strange Land", "Robert A. Heinlein"), 2));
        return bookCopies;
    }

    private List<Copy> createBookCopies(Book book, int numberOfCopies) {
        List<Copy> bookCopies = new ArrayList<Copy>();
        for (int i = 0; i < numberOfCopies; i++) {
            bookCopies.add(new Copy(book));
        }
        return bookCopies;
    }

    private List<Issue> getDefaultIssuesOfBooks() {
        List<Issue> issues = new ArrayList<Issue>();
        issues.addAll(createJournalIssues(new Journal("2468-0672", "HardwareX", "Oxford", "2017"), 4));
        issues.addAll(createJournalIssues(new Journal("2468-1113", "Computational Toxicology", "Elsevier", "2017"), 4));
        issues.addAll(createJournalIssues(new Journal("2352-9520", "OpenNano", "Elsevier", "2016"), 4));
        issues.addAll(createJournalIssues(new Journal("2352-7285", "Development Engineering", "Elsevier", "2016"), 4));
        issues.addAll(createJournalIssues(new Journal("2352-3093", "REACH", "Elsevier", "2016"), 4));
        return issues;
    }

    private List<Issue> createJournalIssues(Journal journal, int numberOfIssues) {
        List<Issue> journalIssues = new ArrayList<Issue>();
        for (int i = 0; i < numberOfIssues; i++) {
            journalIssues.add(new Issue(journal));
        }
        return journalIssues;
    }

    private void createDirectory() {
        directoryService = new Directory();
        createNonStaffMembers();
        createStandardStaffMembers();
        createLibrarians();
    }

    private void createNonStaffMembers() {
        directoryService.addNonStaffMember("George Washington");
        directoryService.addNonStaffMember("John Adams");
        directoryService.addNonStaffMember("Thomas Jefferson");
        directoryService.addNonStaffMember("James Madison");
        directoryService.addNonStaffMember("James Monroe");
        directoryService.addNonStaffMember("John Quincy Adams");
        directoryService.addNonStaffMember("Andrew Jackson");
        directoryService.addNonStaffMember("Martin Van Buren");
        directoryService.addNonStaffMember("William Henry Harrison");
        directoryService.addNonStaffMember("John Tyler");
    }

    private void createStandardStaffMembers() {
        directoryService.addStandardStaffMember("Niels Bohr");
        directoryService.addStandardStaffMember("Max Planck");
    }

    private void createLibrarians() {
        directoryService.addLibrarian("Benjamin Franklin");
    }

    public void shutdown() throws IOException {
        serializeCatalog();
        serializeDirectory();
    }

    private void serializeCatalog() throws IOException {
        FileOutputStream fos = new FileOutputStream(catalogSystemFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(catalogService);
        oos.close();
        fos.close();
    }

    private void serializeDirectory() throws IOException {
        FileOutputStream fos = new FileOutputStream(directorySystemFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(directoryService);
        oos.close();
        fos.close();
    }
}