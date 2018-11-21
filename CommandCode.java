import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class CommandCode {
  private Action action;
  private static final Set<Character> validCommandCodes = new HashSet<>(
      Arrays.asList('1', '2', '3', '4', 'q', '+', '*', 'c', 'b'));

  public enum Action {
    BOOK_BORROW, BOOK_RETURN, JOURNAL_BORROW, JOURNAL_RETURN, ADD_ITEM_TO_CATALOG, SHUTDOWN_SYSTEM, LOGOUT,
    PRINT_CATALOG, PRINT_BORROWED_ITEMS, NULL
  }

  public enum PermissionLevel {
    BASIC, STAFF, ADMIN
  }

  public CommandCode() {
    this(Action.NULL);
  }

  public CommandCode(char commandCode) throws InvalidCommandCode {
    if (isValid(commandCode)) {
      this.action = getAction(commandCode);
    } else {
      throw new InvalidCommandCode(String.format("'%s' is an unrecognized command."));
    }
  }

  public CommandCode(Action action) {
    this.action = action;
  }

  public Boolean isValid(char commandCode) {
    return validCommandCodes.contains(commandCode);
  }

  public Action asAction() {
    return action;
  }

  private Action getAction(char commandCode) {
    switch (commandCode) {
    case 'b':
      return Action.PRINT_BORROWED_ITEMS;
    case 'c':
      return Action.PRINT_CATALOG;
    case '1':
      return Action.BOOK_BORROW;
    case '2':
      return Action.BOOK_RETURN;
    case '3':
      return Action.JOURNAL_BORROW;
    case '4':
      return Action.JOURNAL_RETURN;
    case 'q':
      return Action.SHUTDOWN_SYSTEM;
    case '+':
      return Action.ADD_ITEM_TO_CATALOG;
    case '*':
      return Action.LOGOUT;
    default:
      return Action.NULL;
    }
  }

  public PermissionLevel getPermissionLevel() {
    switch (asAction()) {
    case ADD_ITEM_TO_CATALOG:
      return PermissionLevel.ADMIN;
    case SHUTDOWN_SYSTEM:
    case JOURNAL_BORROW:
    case JOURNAL_RETURN:
      return PermissionLevel.STAFF;
    default:
      return PermissionLevel.BASIC;
    }
  }
}