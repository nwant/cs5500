public class Command {
    private CommandCode.Action commandToExecute;
    private LibraryMember user;

    private Command(CommandCode commandCode, LibraryMember user) throws InvalidCommandCode {
        if (commandCode.asAction() == CommandCode.Action.NULL) {
            throw new InvalidCommandCode("A valid command code was not provided.");
        }
        this.user = user;
        this.commandToExecute = commandCode.asAction();
    }

    private Command(LibraryMember user, CommandCode commandCode) throws InvalidCommandCode, InsufficientPermissions {
        this(commandCode, user);
        if (commandCode.getPermissionLevel() != CommandCode.PermissionLevel.BASIC) {
            throw new InsufficientPermissions();
        }
    }

    private Command(MemberOfStaff user, CommandCode commandCode) throws InvalidCommandCode, InsufficientPermissions {
        this(commandCode, user);
        if (commandCode.getPermissionLevel() == CommandCode.PermissionLevel.ADMIN) {
            throw new InsufficientPermissions();
        }
    }

    private Command(Librarian user, CommandCode commandCode) throws InvalidCommandCode {
        this(commandCode, user);
    }

    public static void executeCommandAsBasicUser(CommandCode commandCode, LibraryMember basicUser)
            throws InvalidCommandCode, InsufficientPermissions {
        new Command(basicUser, commandCode).execute();
    }

    public static void executeCommandAsNonAdminStaffMember(CommandCode commandCode, MemberOfStaff staffMemberUser)
            throws InvalidCommandCode, InsufficientPermissions {
        new Command(staffMemberUser, commandCode).execute();
    }

    public static void executeCommandAsAdmin(CommandCode commandCode, Librarian adminUser) throws InvalidCommandCode {
        new Command(adminUser, commandCode).execute();
    }

    private void execute() {
        try {
            if (commandToExecute == CommandCode.Action.BOOK_BORROW) {
                executeBookBorrowCommand();
            } else if (commandToExecute == CommandCode.Action.BOOK_RETURN) {
                executeBookReturnCommand();
            } else if (commandToExecute == CommandCode.Action.JOURNAL_BORROW) {
                executeJournalBorrowCommand();
            } else if (commandToExecute == CommandCode.Action.JOURNAL_RETURN) {
                executeJournalReturnCommand();
            } else if (commandToExecute == CommandCode.Action.ADD_ITEM_TO_CATALOG) {
                executeAddItemToCatalogCommand();
            } else if (commandToExecute == CommandCode.Action.SHUTDOWN_SYSTEM) {
                LibrarySystem.shutdownRequestOccurred();
            }
        } catch (Exception e) {
            Terminal.printErrorMessage(e.getMessage());
        }
    }

    private void executeBookBorrowCommand() {
        String ISBN = Terminal.haveUserSelectIdFromCollection(Catalog.getAvailableCopies(), "Select book to borrow:");
        Copy copyToBorrow = Catalog.findAvailableCopy(ISBN);
        if (user.borrow(copyToBorrow)) {
            Terminal.printInfoMessage(String.format("You borrowed %s.", copyToBorrow.toString()));
        } else {
            Terminal.printErrorMessage("Unable to borrow book.");
        }
    }

    private void executeBookReturnCommand() {
        if (user.getBorrowedCopies().isEmpty())
        {
            Terminal.printInfoMessage("You have no book copies checked out.");
            return;
        }
        String ISBN = Terminal.haveUserSelectIdFromCollection(user.getBorrowedCopies(), "Select book to return:");
        Copy copyToReturn = (Copy) user.findBorrowedById(ISBN);
        user.returnToLibrary(copyToReturn);
    }

    private void executeJournalBorrowCommand() {
        String ISSN = Terminal.haveUserSelectIdFromCollection(Catalog.getAvailableIssues(),
                "Select journal to borrow:");
        Issue issueToBorrow = Catalog.findAvaiableIssue(ISSN);
        ((MemberOfStaff) user).borrow(issueToBorrow);
    }

    private void executeJournalReturnCommand() {
        if (((MemberOfStaff)user).getBorrowedIssues().isEmpty())
        {
            Terminal.printInfoMessage("You have no journal issues checked out.");
            return;
        }

        String ISSN = Terminal.haveUserSelectIdFromCollection(((MemberOfStaff) user).getBorrowedIssues(),
                "Select journal to return:");
        Issue issueToReturn = (Issue) user.findBorrowedById(ISSN);
        ((MemberOfStaff) user).returnToLibrary(issueToReturn);
    }

    private void executeAddItemToCatalogCommand() {
        LibraryInventoryItem itemToAdd = Terminal.getNewItemInformationFromUser();
        if (itemToAdd instanceof Book) {
            Copy newBookCopy = new Copy((Book) itemToAdd);
            ((Librarian) user).addToCatalog(newBookCopy);
        } else if (itemToAdd instanceof Journal) {
            Issue newJournalIssue = new Issue((Journal) itemToAdd);
            ((Librarian) user).addToCatalog(newJournalIssue);
        }
    }
}