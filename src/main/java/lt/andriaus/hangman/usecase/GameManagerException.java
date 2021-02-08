package lt.andriaus.hangman.usecase;

public class GameManagerException extends RuntimeException {

    private GameManagerException(String message) {
        super(message);
    }

    public static GameManagerException FailedToCreateGameException() {
        return new GameManagerException("FailedToCreateGameException");
    }

}