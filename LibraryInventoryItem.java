import java.io.Serializable;

public abstract class LibraryInventoryItem implements Serializable {
    private static final long serialVersionUID = 55L;
    private int numberOfUnits = 0;
    private int unitsBorrowed = 0;

    public abstract String getId();

    public Boolean borrowed() {
        if (numberOfUnits > unitsBorrowed) {
            unitsBorrowed++;
            return true;
        } else {
            return false;
        }
    }

    public Boolean returned() {
        if (unitsBorrowed > 0 && numberOfUnits > unitsBorrowed) {
            unitsBorrowed--;
            return true;
        } else {
            return false;
        }
    }

    protected void unitAdded() {
        numberOfUnits++;
    }
}