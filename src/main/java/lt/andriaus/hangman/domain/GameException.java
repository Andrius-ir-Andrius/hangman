package lt.andriaus.hangman.domain;

class GameException extends RuntimeException {
    enum Exceptions {
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