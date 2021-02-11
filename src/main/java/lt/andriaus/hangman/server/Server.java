package lt.andriaus.hangman.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.andriaus.hangman.domain.Game;
import lt.andriaus.hangman.gateway.api.Database;
import lt.andriaus.hangman.gateway.implementation.inmemory.InMemoryDatabase;
import lt.andriaus.hangman.usecase.GameManager;
import lt.andriaus.hangman.usecase.WithDatabaseGameManager;
import lt.andriaus.hangman.util.JSONGame;
import spark.Request;

import java.util.Optional;
import java.util.function.Supplier;

import static spark.Spark.*;

public class Server {
    static private Action action;

    public static void main(String[] args) {
        init();
        String websiteUrl = "";
        String corsPattern = String.format("https?:\\/\\/:(localhost|%s).+", websiteUrl);
        before((req, res) -> res.header("Access-Control-Allow-Origin", corsPattern));
        post("/game", (req, res) -> process(req, res, () -> {
            res.status(201);
            return action.createGame();
        }));
        get("/game", (req, res) -> process(req, res, () -> action.loadGame(req).map(JSONGame::new)));
        put("/game", (req, res) -> process(req, res, () ->
                action.guessLetter(req).map(JSONGame::new))
        );
    }

    static void init() {
        Database<String> wordDB = new InMemoryDatabase<>();
        Database<Game> gameDB = new InMemoryDatabase<>();
        GameManager gameManager = new WithDatabaseGameManager(wordDB, gameDB);
        wordDB.save("best");
        wordDB.save("test");
        action = new Action(gameManager);
    }


    static <T> String process(
            spark.Request req,
            spark.Response res,
            Supplier<Optional<T>> resultProducer) {
        try {
            Optional<T> resultOptional = resultProducer.get();
            if (resultOptional.isPresent())
                return resultOptional.map(Server::convertToJson).orElseThrow();
            else
                return resultNotFound(req, res);
        } catch (Exception e) {
            res.status(500);
            return e.getMessage();
        }
    }

    private static String resultNotFound(Request req, spark.Response res) {
        res.status(404);
        return "Result not found for request: id=" + getIdFromQueryAndBody(req);
    }

    private static String getIdFromQueryAndBody(Request req) {
        if (req.queryParams("id").length() > 0)
            return req.queryParams("id");
        try {
            return new ObjectMapper().readValue(req.body(), Action.RequestBody.class).getId() + "";
        } catch (Exception e) {
            return "";
        }
    }

    private static <T> String convertToJson(T result) {
        try {
            return new ObjectMapper().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RestServerException("Failed to convert result to json", e);
        }
    }

    private static class RestServerException extends RuntimeException {
        public RestServerException(String message, Throwable e) {
            super(message, e);
        }
    }
}
