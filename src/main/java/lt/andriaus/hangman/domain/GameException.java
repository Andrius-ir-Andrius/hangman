package lt.andriaus.hangman.domain;

public class GameException extends RuntimeException {

    private GameException(String message) {
        super(message);
    }

    public static GameException gameIsAlreadyOverException() {
        return new GameException("Game is already over");
    }

    public static GameException symbolIsNotAlphabeticException(char letter) {
        return new GameException(String.format("Symbol [%s] is not alphabetic", letter));
    }

}