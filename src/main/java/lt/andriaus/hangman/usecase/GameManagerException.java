package lt.andriaus.hangman.usecase;

public class GameManagerException extends RuntimeException {

    private GameManagerException(String message) {
        super(message);
    }

    public static GameManagerException failedToCreateGameException() {
        return new GameManagerException("Failed to create Game");
    }

}