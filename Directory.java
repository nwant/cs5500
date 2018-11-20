import java.io.Serializable;
import java.util.Map;

import java.util.HashMap;
import java.io.StringWriter;

public class Directory implements Serializable {
    private static final long serialVersionUID = 22L;
    private Map<String, LibraryMember> nonStaffMembers = new HashMap<>();
    private Map<String, MemberOfStaff> standardStaffMembers = new HashMap<>();
    private Map<String, Librarian> librarians = new HashMap<>();
    private static int lastMemberIdGenerated = 99; // non staff id >= 100
    private static int lastStandardStaffIdGenerated = 9; // standard staff id >= 10, < 100
    private static int lastLibrarianIdGenerated = 0; // librarian id between 1 and 9

    public Boolean containsId(String id) {
        return nonStaffMembers.containsKey(id) || standardStaffMembers.containsKey(id) || librarians.containsKey(id);
    }

    public LibraryMember lookup(String id) {
        if (nonStaffMembers.containsKey(id)) {
            return nonStaffMembers.get(id);
        } else if (standardStaffMembers.containsKey(id)) {
            return standardStaffMembers.get(id);
        } else if (librarians.containsKey(id)) {
            return librarians.get(id);
        } else {
            return null;
        }
    }

    public void addNonStaffMember(String name) {
        LibraryMember newNonStaffMember = new LibraryMember(generateNewMemberId(), name);
        nonStaffMembers.put(newNonStaffMember.getId(), newNonStaffMember);
    }

    public void addStandardStaffMember(String name) {
        MemberOfStaff newStandardStaffMember = new MemberOfStaff(generateNewStaffId(), name);
        standardStaffMembers.put(newStandardStaffMember.getId(), newStandardStaffMember);
    }

    public void addLibrarian(String name) {
        Librarian newLibrarian = new Librarian(generateNewLibrarianId(), name);
        librarians.put(newLibrarian.getId(), newLibrarian);
    }

    private static String generateNewMemberId() {
        lastMemberIdGenerated++;
        return String.valueOf(lastMemberIdGenerated);
    }

    private static String generateNewStaffId() {
        lastStandardStaffIdGenerated++;
        return String.valueOf(lastStandardStaffIdGenerated);
    }

    private static String generateNewLibrarianId() {
        lastLibrarianIdGenerated++;
        return String.valueOf(lastLibrarianIdGenerated);
    }

    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        stringWriter.write("Non Staff Members:\n");
        for (LibraryMember nonStaffMember : nonStaffMembers.values()) {
            stringWriter.write(nonStaffMember.toString() + "\n");
        }
        stringWriter.write("\nStaff Members:\n");
        for (MemberOfStaff memberOfStaff : standardStaffMembers.values()) {
            stringWriter.write(memberOfStaff.toString() + "\n");
        }
        for (Librarian librarian : librarians.values()) {
            stringWriter.write(librarian.toString() + " [Librarian]\n");
        }
        return stringWriter.toString();
    }
}