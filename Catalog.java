import java.io.Serializable;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Catalog implements Serializable {
    private static Map<String, List<Copy>> copies = new HashMap<>();
    private static Map<String, List<Issue>> issues = new HashMap<>();
    private static final long serialVersionUID = 11L;

    public void addCopies(List<Copy> newCopies) {
        for (Copy newCopy : newCopies) {
            addCopy(newCopy);
        }
    }

    public void addCopy(Copy newCopy) {
        if (isNewBookId(newCopy.getItemId())) {
            registerBookId(newCopy.getItemId());
        }
        addCopyToCollection(newCopy);
    }

    private Boolean isNewBookId(String bookId) {
        return !copies.containsKey(bookId);
    }

    private void registerBookId(String bookId) {
        copies.put(bookId, new ArrayList<>());
    }

    private void addCopyToCollection(Copy copy) {
        copies.get(copy.getItemId()).add(copy);
    }

    public void addIssues(List<Issue> newIssues) {
        for (Issue newIssue : newIssues) {
            addIssue(newIssue);
        }
    }

    public void addIssue(Issue newIssue) {
        if (isNewJournalId(newIssue.getItemId())) {
            registerJournalId(newIssue.getItemId());
        }
        addIssueToCollection(newIssue);
    }

    public static List<Copy> getAvailableCopies() {
        Map<String, Copy> availableCopies = new HashMap<>();
        for (String isbn : copies.keySet()) {
            for (Copy copy : copies.get(isbn)) {
                if (!copy.isBorrowed()) {
                    availableCopies.put(copy.getItemId(), copy);
                }
            }
        }
        return new ArrayList<>(availableCopies.values());
    }

    public static List<Issue> getAvailableIssues() {
        Map<String, Issue> availableIssues = new HashMap<>();
        for (String issn : issues.keySet()) {
            for (Issue issue : issues.get(issn)) {
                if (!issue.isBorrowed()) {
                    availableIssues.put(issue.getItemId(), issue);
                }
            }
        }
        return new ArrayList<Issue>(availableIssues.values());
    }

    public static Copy findAvailableCopy(String ISBN) {
        for (Copy copy : copies.get(ISBN)) {
            if (!copy.isBorrowed()) {
                return copy;
            }
        }
        return null;
    }

    public static Issue findAvaiableIssue(String ISSN) {
        return issues.get(ISSN).get(0);
    }

    private Boolean isNewJournalId(String journalId) {
        return !issues.containsKey(journalId);
    }

    private void registerJournalId(String journalId) {
        issues.put(journalId, new ArrayList<>());
    }

    private void addIssueToCollection(Issue issue) {
        issues.get(issue.getItemId()).add(issue);
    }
}