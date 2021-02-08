package lt.andriaus.hangman.domain;

public class GameException extends RuntimeException {

    private GameException(String message) {
        super(message);
    }

    public static GameException gameIsAlreadyOverException() {
        return new GameException("GameIsAlreadyOverException");
    }

    public static GameException symbolIsNotAlphabeticException(char letter) {
        return new GameException(String.format("SymbolIsNotAlphabeticException; letter=%s", letter));
    }

}