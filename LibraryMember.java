import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class LibraryMember implements Serializable {
    private String id;
    private String name;
    private static final int MAX_BOOKS_OUT = 6;
    protected List<BorrowableItem> borrowedItems = new ArrayList<>();
    private static final long serialVersionUID = 33L;

    public LibraryMember(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Boolean hasAdminPrivileges() {
        return false;
    }

    public Boolean isStaffMember() {
        return false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Copy> getBorrowedCopies() {
        List<Copy> borrowedCopies = new ArrayList<>();
        for (BorrowableItem borrowedItem : borrowedItems) {
            if (borrowedItem instanceof Copy) {
                borrowedCopies.add((Copy) borrowedItem);
            }
        }
        return borrowedCopies;
    }

    public BorrowableItem findBorrowedById(String id) {
        for (BorrowableItem borrowedItem : borrowedItems) {
            if (borrowedItem.getItemId() == id) {
                return borrowedItem;
            }
        }
        // TODO: throw error?
        return null;
    }

    public Boolean borrow(Copy copy) {
        return borrowItem(copy);
    }

    protected Boolean borrowItem(BorrowableItem item) {
        if (okToBorrow() && item.borrow()) {
            borrowedItems.add(item);
            return true;
        } else {
            return false;
        }
    }

    protected Boolean okToBorrow() {
        return borrowedItems.size() < getBorrowLimit();
    }

    protected int getBorrowLimit() {
        return MAX_BOOKS_OUT;
    }

    public Boolean returnToLibrary(Copy copy) {
        return returnItemToLibrary(copy);
    }

    protected Boolean returnItemToLibrary(BorrowableItem item) {
        if (amBorrowing(item) && item.returnToLibrary()) {
            removeFromBorrowedItems(item);
            return true;
        } else {
            return false;
        }
    }

    private Boolean amBorrowing(BorrowableItem item) {
        for (BorrowableItem borrowedItem : borrowedItems) {
            if (borrowedItem.getItemId().equals(item.getItemId())) {
                return true;
            }
        }
        return false;
    }

    private void removeFromBorrowedItems(BorrowableItem item) {
        BorrowableItem itemToRemove = item;
        for (BorrowableItem borrowedItem : borrowedItems) {
            if (item.getItemId() == borrowedItem.getItemId()) {
                itemToRemove = borrowedItem;
                break;
            }
        }
        borrowedItems.remove(itemToRemove);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, id);
    }
}