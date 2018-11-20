public class Librarian extends MemberOfStaff {
    private static final long serialVersionUID = 99L;

    public Librarian(String staffId, String name) {
        super(staffId, name);
    }

    public void addToCatalog(Copy newCopy) {
        LibrarySystem.addToCatalog(newCopy);
    }

    public void addToCatalog(Issue newIssue) {
        LibrarySystem.addToCatalog(newIssue);
    }

    @Override
    public Boolean hasAdminPrivileges() {
        return true;
    }
}