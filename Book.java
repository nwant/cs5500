import java.io.Serializable;

public class Book extends LibraryInventoryItem {
    private static final long serialVersionUID = 66L;
    private String ISBN;
    private String title;
    private String author;

    public Book(String ISBN, String title, String author) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return ISBN;
    }

    public void copyAdded() {
        unitAdded();
    }

    @Override
    public String toString() {
        return String.format("\"%s\" by %s", title, author);
    }
}