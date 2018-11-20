import java.util.ArrayList;
import java.util.List;

public class MemberOfStaff extends LibraryMember {
    private static final long serialVersionUID = 99L;
    private static final int MAX_ITEMS_OUT = 12;

    public MemberOfStaff(String staffId, String name) {
        super(staffId, name);
    }

    public Boolean borrow(Issue issue) {
        return borrowItem(issue);
    }

    public Boolean returnToLibrary(Issue issue) {
        return returnItemToLibrary(issue);
    }

    public List<Issue> getBorrowedIssues() {
        List<Issue> borrowedIssues = new ArrayList<>();
        for (BorrowableItem borrowedItem : borrowedItems) {
            if (borrowedItem instanceof Issue) {
                borrowedIssues.add((Issue) borrowedItem);
            }
        }
        return borrowedIssues;
    }

    @Override
    public Boolean isStaffMember() {
        return true;
    }

    @Override
    protected int getBorrowLimit() {
        return MAX_ITEMS_OUT;
    }
}