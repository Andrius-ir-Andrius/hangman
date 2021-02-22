package lt.andriaus.hangman.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.andriaus.hangman.domain.Game;
import lt.andriaus.hangman.usecase.GameManager;
import lt.andriaus.hangman.util.GuessGameRequestBody;
import spark.Request;

import java.util.Optional;

import static java.lang.Integer.parseInt;

public class Action {

    private final GameManager gameManager;
    private final ObjectMapper objectMapper;

    public Action(GameManager gameManager) {
        this.gameManager = gameManager;
        objectMapper = new ObjectMapper();
    }

    public Optional<Integer> createGame() {
        return gameManager.createGame();
    }

    public Optional<Game> loadGame(Request request) {
        return gameManager.loadGame(parseInt(request.queryParams("id")));
    }

    public Optional<Game> guessLetter(Request request) {
        GuessGameRequestBody body;
        try {
            body = objectMapper.readValue(request.body(), GuessGameRequestBody.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Wrong request body");
        }
        char character = body.getLetter();
        int id = body.getId();
        return gameManager.guessLetter(id, character);
    }
}
