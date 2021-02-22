package lt.andriaus.hangman.server;

import lt.andriaus.hangman.domain.Game;
import lt.andriaus.hangman.gateway.api.Database;
import lt.andriaus.hangman.gateway.implementation.inmemory.InMemoryDatabase;
import lt.andriaus.hangman.usecase.GameManager;
import lt.andriaus.hangman.usecase.WithDatabaseGameManager;
import lt.andriaus.hangman.util.GuessGameResponse;

import static lt.andriaus.hangman.server.RequestProcess.process;
import static spark.Spark.*;

public class Server {
    static private Action action;
    final private static String GAME_URL = "/game";

    public static void main(String[] args) {
        initialiseConfig();

        options("/*", (req, res) -> {
            String accessControlRequestHeaders = req
                    .headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers",
                        accessControlRequestHeaders);
            }

            String accessControlRequestMethod = req
                    .headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods",
                        accessControlRequestMethod);
            }

            return "OK";
        });


        before((req, res) -> res.header("Access-Control-Allow-Origin", "*"));
        before((req, res) -> res.header("Access-Control-Allow-Methods", "PUT, POST, GET, DELETE, OPTIONS"));


        post(GAME_URL, (req, res) -> process(req, res, () -> {
            res.status(201);
            return action.createGame();
        }));

        get(GAME_URL, (req, res) -> process(req, res, () ->
                action.loadGame(req).map(GuessGameResponse::new)
        ));

        put(GAME_URL, (req, res) -> process(req, res, () ->
                action.guessLetter(req).map(GuessGameResponse::new)
        ));
    }

    private static void initialiseConfig() {
        Database<String> wordDB = new InMemoryDatabase<>();
        Database<Game> gameDB = new InMemoryDatabase<>();
        GameManager gameManager = new WithDatabaseGameManager(wordDB, gameDB);
        wordDB.save("best");
        wordDB.save("test");
        action = new Action(gameManager);
    }

}
