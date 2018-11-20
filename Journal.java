import java.io.Serializable;

public class Journal extends LibraryInventoryItem {
    private static final long serialVersionUID = 66L;
    private String ISSN;
    private String title;
    private String publisher;
    private String publicationYear;

    public Journal(String ISSN, String title, String publisher, String publicationYear) {
        this.ISSN = ISSN;
        this.title = title;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
    }

    public String getId() {
        return ISSN;
    }

    public void issueAdded() {
        unitAdded();
    }

    @Override
    public String toString() {
        return String.format("\"%s\" Published By %s in %s", title, publisher, publicationYear);
    }
}