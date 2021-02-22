package lt.andriaus.hangman.util;

public class ExceptionResponseJSON {
    private final String message;

    public ExceptionResponseJSON(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
