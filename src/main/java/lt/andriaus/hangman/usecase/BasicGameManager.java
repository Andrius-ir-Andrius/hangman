package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.database.Database;
import lt.andriaus.hangman.domain.Game;

import java.util.*;

public class BasicGameManager implements GameManager {
    private final Database<String> wordDB;
    private final Database<Game> gameDB;

    public BasicGameManager(Database<String> wordDB, Database<Game> gameDB) {
        this.wordDB = wordDB;
        this.gameDB = gameDB;
    }

    public Optional<Integer> createGame() {
        return Optional.of(
                wordDB.loadRandom()
                .map(word -> Game.Builder.fromWord(word).build())
                .map(gameDB::save)
                .orElseThrow(GameManagerException::failedToCreateGameException)
        );
    }

    public Optional<Game> loadGame(int id) {
        return gameDB.loadOne(id);
    }

    public Optional<Game> guessLetter(int id, char letter) {
        Optional<Game> game = loadGame(id).map(g -> g.guessLetter(letter));
        game.ifPresent(obj -> gameDB.save(obj, id));
        return game;
    }
}
