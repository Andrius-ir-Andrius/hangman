package lt.andriaus.hangman.domain;

public class GameException extends RuntimeException {
    public enum Exceptions {
        GameIsAlreadyOverException,
        SymbolIsNotAlphabeticException
    }

    private GameException(String message) {
        super(message);
    }

    public static GameException GameIsAlreadyOverException() {
        return new GameException(Exceptions.GameIsAlreadyOverException.toString());
    }

    public static GameException SymbolIsNotAlphabeticException() {
        return new GameException(Exceptions.SymbolIsNotAlphabeticException.toString());
    }

}