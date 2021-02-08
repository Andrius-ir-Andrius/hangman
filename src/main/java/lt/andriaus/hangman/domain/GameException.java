package lt.andriaus.hangman.domain;

public class GameException extends RuntimeException {

    private GameException(String message) {
        super(message);
    }

    public static GameException gameIsAlreadyOverException() {
        return new GameException("GameIsAlreadyOverException");
    }

    public static GameException symbolIsNotAlphabeticException() {
        return new GameException("SymbolIsNotAlphabeticException");
    }

}