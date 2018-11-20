import java.lang.Exception;

public class InvalidCommandCode extends Exception {
    public InvalidCommandCode(String message) {
        super(message);
    }
}