package lt.andriaus.hangman.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.andriaus.hangman.domain.Game;
import lt.andriaus.hangman.usecase.GameManager;
import spark.Request;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Action {

    private final GameManager gameManager;

    public Action(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public int createGame() {
        return gameManager.createGame();
    }

    public Optional<Game> loadGame(Request request) {
        return gameManager.loadGame(parseInt(request.queryParams("id")));
    }

    public Optional<Game> guessLetter(Request request) {
        RequestBody body;
        try {
            body = new ObjectMapper().readValue(request.body(), RequestBody.class);
        } catch (JsonProcessingException e) {
            if (e.getMessage().contains("`int` from String ")) {
                Matcher matcher = Pattern.compile("from String \"(.+?)\":").matcher(e.getMessage());
                if(matcher.find())
                    throw ActionException.integerWasExpected(matcher.group(1));
            }
            throw new RuntimeException(e.getMessage());
        }
        String letters = body.getLetters();
        int id = body.getId();
        if (letters.length() != 1)
            throw ActionException.characterWasExpected(letters);
        char character = letters.charAt(0);
        return gameManager.guessLetter(id, character);
    }

    private static class RequestBody {
        private final int id;
        private final String letters;

        public RequestBody(@JsonProperty("id") int id, @JsonProperty("letter") String letters) {
            this.id = id;
            this.letters = letters;
        }

        public int getId() {
            return id;
        }

        public String getLetters() {
            return letters;
        }
    }
}
