import java.io.Serializable;

public abstract class BorrowableItem implements Serializable {
    private static final long serialVersionUID = 44L;
    private LibraryInventoryItem item;
    private Boolean isBorrowed = false;

    public BorrowableItem(LibraryInventoryItem item) {
        this.item = item;
        this.item.unitAdded();
    }

    public Boolean borrow() {
        if (!isBorrowed && item.borrowed()) {
            isBorrowed = true;
            return true;
        } else {
            return false;
        }
    }

    public Boolean returnToLibrary() {
        if (isBorrowed && item.returned()) {
            isBorrowed = false;
            return true;
        } else {
            return false;
        }
    }

    public String getItemId() {
        return item.getId();
    }

    public Boolean isBorrowed() {
        return isBorrowed;
    }

    public String getItemAsString() {
        return item.toString();
    }
}