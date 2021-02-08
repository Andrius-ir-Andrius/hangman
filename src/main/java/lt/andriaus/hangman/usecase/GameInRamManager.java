package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.database.Database;
import lt.andriaus.hangman.domain.Game;

import java.util.*;

public class GameInRamManager implements GameManager{
    private final Database<String> wordDB;
    private final Database<Game> gameDB;
    public GameInRamManager(Database<String> wordDB, Database<Game> gameDB){
        this.wordDB = wordDB;
        this.gameDB = gameDB;
    }
    public int createGame(){
        Map<Integer, String> loadedWordMap = wordDB.loadAll();
        List<String> allWords = new ArrayList<>(loadedWordMap.values());
        if(allWords.size() == 0){
            return -1;
        }
        String randomWord = allWords.get(new Random().nextInt(allWords.size()));
        Game newGame = Game.Builder.fromWord(randomWord).build();
        return gameDB.save(newGame);
    }
    public Optional<Game> loadGame(int id){
        return gameDB.loadOne(id);
    }
    public Optional<Game> guessLetter(int id, char letter){
        Optional<Game> loadedGame = loadGame(id);
        if(loadedGame.isEmpty())
            return loadedGame;
        Game gameAfterGuess = loadedGame.get().guessLetter(letter);
        gameDB.save(gameAfterGuess, id);
        return Optional.of(gameAfterGuess);
    }
}
