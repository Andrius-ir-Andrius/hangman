package lt.andriaus.hangman.domain;

public class GameException extends RuntimeException {

    private GameException(String message) {
        super(message);
    }

    public static GameException GameIsAlreadyOverException() {
        return new GameException("GameIsAlreadyOverException");
    }

    public static GameException SymbolIsNotAlphabeticException() {
        return new GameException("SymbolIsNotAlphabeticException");
    }

}