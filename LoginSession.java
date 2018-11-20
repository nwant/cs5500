public class LoginSession {
    private static LoginSession currentSession;
    private LibraryMember user;
    private CommandCode nextCommandRequest = new CommandCode();

    public static void startSessionByUserId(String userId) throws LoginFailed {
        currentSession = new LoginSession();
        currentSession.user = LibrarySystem.directoryLookup(userId);
        if (currentSession.user == null) {
            throw new LoginFailed("Invalid Id");
        }
        Terminal.printGreeting(currentSession.user.getName());
        currentSession.executeInputtedCommand();
    }

    public static Boolean noneActive() {
        return currentSession == null;
    }

    public static String getNameOfLoggedInUser() {

        return currentSession.user.getName();
    }

    private void executeInputtedCommand() {
        while (nextCommandRequest.asAction() != CommandCode.Action.LOGOUT) {
            try {
                requestNextCommandFromUser();
                executeRequestedCommand();
            } catch (InvalidCommandCode e) {
                Terminal.printErrorMessage("Invalid Command");
            } catch (InsufficientPermissions e) {
                Terminal.printErrorMessage("You do not have sufficient permissions to perform this action");
            }
        }
    }

    private void requestNextCommandFromUser() {
        if (user.hasAdminPrivileges()) {
            nextCommandRequest = Terminal.getCommandCodeFromAdmin();
        } else if (user.isStaffMember()) {
            nextCommandRequest = Terminal.getCommandCodeFromNonAdminStaffMember();
        } else {
            nextCommandRequest = Terminal.getCommandCodeFromBasicUser();
        }
    }

    private void executeRequestedCommand() throws InvalidCommandCode, InsufficientPermissions {
        if (user.hasAdminPrivileges()) {
            Command.executeCommandAsAdmin(nextCommandRequest, (Librarian) user);
        } else if (user.isStaffMember()) {
            Command.executeCommandAsNonAdminStaffMember(nextCommandRequest, (MemberOfStaff) user);
        } else {
            Command.executeCommandAsBasicUser(nextCommandRequest, user);
        }
    }
}