package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.domain.Game;
import lt.andriaus.hangman.gateway.api.Database;

import java.util.Optional;

public class WithDatabaseGameManager implements GameManager {
    private final Database<String> wordDB;
    private final Database<Game> gameDB;

    public WithDatabaseGameManager(Database<String> wordDB, Database<Game> gameDB) {
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
