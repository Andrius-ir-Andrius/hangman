package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.database.Database;
import lt.andriaus.hangman.domain.Game;
import lt.andriaus.hangman.domain.GameException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class GameInRamManagerIntegrationTest {
    private static Database<Game> gameDB;
    private static Database<String> wordDB;
    private static GameManager gameManager;


    @BeforeAll
    static void setup(){
        wordDB = new RamDatabase<>();
        wordDB.save("Hello");
        wordDB.save("Adele");
        gameDB = new RamDatabase<>();
        gameManager = new GameInRamManager(wordDB, gameDB);
    }

    @Test
    void shouldCreateGame() {
        int newGameId = gameManager.createGame();
        assertThat(newGameId).isGreaterThan(-1);
    }

    @Test
    void shouldCreateAndLoadGame() {
        int newGameId = gameManager.createGame();
        Optional<Game> newGame = gameManager.loadGame(newGameId);
        assertThat(newGame.isPresent()).isTrue();
        assertThat(Arrays.asList(new String[]{"HELLO", "ADELE"}).contains(newGame.get().getWord())).isTrue();
    }

    @Test
    void shouldGetEmptyGame(){
        Optional<Game> emptyGame = gameManager.loadGame(-1);
        assertThat(emptyGame.isEmpty()).isTrue();
    }

    @Test
    void shouldFailAtGuessing(){
        Optional<Game> gameAfterGuess = gameManager.guessLetter(-1, 'C');
        assertThat(gameAfterGuess.isEmpty()).isTrue();
    }

    @Test
    void shouldAddLettersAfterGuessing(){
        int newGameId = gameManager.createGame();
        Optional<Game> gameAfterGuesses = gameManager.guessLetter(newGameId, 'E');
        gameAfterGuesses = gameManager.guessLetter(newGameId, 'B');
        assertThat(gameAfterGuesses.isPresent()).isTrue();
        assertThat(gameAfterGuesses.get().getGuessedLetters().size()).isEqualTo(2);
    }


    @Test
    void shouldThrowWrongSymbolException(){
        int newGameId = gameManager.createGame();
        assertThatThrownBy(() -> gameManager
                .guessLetter(newGameId, '5'))
                .isInstanceOf(GameException.class)
                .hasMessageContaining("SymbolIsNotAlphabeticException");
    }

    @Test
    void shouldThrowWhenGameIsLost(){
        int newGameId = gameManager.createGame();
        gameManager.guessLetter(newGameId, 'B');
        gameManager.guessLetter(newGameId, 'C');
        gameManager.guessLetter(newGameId, 'F');
        gameManager.guessLetter(newGameId, 'G');
        gameManager.guessLetter(newGameId, 'I');
        gameManager.guessLetter(newGameId, 'J');
        gameManager.guessLetter(newGameId, 'K');
        gameManager.guessLetter(newGameId, 'M');
        gameManager.guessLetter(newGameId, 'N');
        gameManager.guessLetter(newGameId, 'P');
        assertThatThrownBy(() -> gameManager
                .guessLetter(newGameId, 'R'))
                .isInstanceOf(GameException.class)
                .hasMessageContaining("GameIsAlreadyOverException");
    }
    @Test
    void shouldThrowWhenGameIsWon(){
        int newGameId = gameManager.createGame();
        Character[] nameLetters;
        Game newGame = gameManager.loadGame(newGameId).get();
        if(newGame.getWord().equals("ADELE")){
            nameLetters = new Character[]{'a', 'd', 'e', 'l'};
        }else{
            nameLetters = new Character[]{'h', 'e', 'l', 'o'};
        }
        gameManager.guessLetter(newGameId, nameLetters[0]);
        gameManager.guessLetter(newGameId, nameLetters[1]);
        gameManager.guessLetter(newGameId, nameLetters[2]);
        gameManager.guessLetter(newGameId, nameLetters[3]);
        assertThatThrownBy(() -> gameManager
                .guessLetter(newGameId, 'Z'))
                .isInstanceOf(GameException.class)
                .hasMessageContaining("GameIsAlreadyOverException");
    }

}