public class Copy extends BorrowableItem {
    private static final long serialVersionUID = 77L;

    public Copy(Book book) {
        super(book);
    }

    @Override
    public String toString() {
        return getItemAsString();
    }
}