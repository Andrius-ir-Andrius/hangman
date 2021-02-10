package lt.andriaus.hangman.server;

public class ActionException extends RuntimeException{
    private ActionException(String message){
        super(message);
    }
    public static ActionException integerWasExpected(String value){
        return new ActionException(String.format("Integer was expected, [%s] was given", value));
    }
    public static ActionException characterWasExpected(String value){
        return new ActionException(String.format("A single character was expected, [%s] was given", value));
    }
}
