package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.domain.Game;
import lt.andriaus.hangman.domain.GameException;
import lt.andriaus.hangman.gateway.api.Database;
import lt.andriaus.hangman.gateway.implementation.inmemory.InMemoryDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class WithDatabaseGameManagerIntegrationTest {
    private static GameManager gameManager;


    @BeforeAll
    static void setup() {
        Database<String> wordDB = new InMemoryDatabase<>();
        wordDB.save("Adele");
        Database<Game> gameDB = new InMemoryDatabase<>();
        gameManager = new WithDatabaseGameManager(wordDB, gameDB);
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
        assertThat(newGame.map(Game::getWord)).hasValue("ADELE");
    }

    @Test
    void shouldGetEmptyOptionalGame() {
        Optional<Game> emptyGame = gameManager.loadGame(-1);
        assertThat(emptyGame).isEmpty();
    }

    @Test
    void shouldFailAtGuessing() {
        Optional<Game> gameAfterGuess = gameManager.guessLetter(-1, 'C');
        assertThat(gameAfterGuess).isEmpty();
    }

    @Test
    void shouldAddLettersAfterGuessing() {
        int newGameId = gameManager.createGame();
        gameManager.guessLetter(newGameId, 'E');
        Optional<Game> gameAfterGuesses = gameManager.guessLetter(newGameId, 'B');
        assertThat(gameAfterGuesses.map(Game::getGuessedLetters).map(Set::size)).hasValue(2);
    }


    @Test
    void shouldThrowWrongSymbolException() {
        int newGameId = gameManager.createGame();
        assertThatThrownBy(() -> gameManager
                .guessLetter(newGameId, '5'))
                .isInstanceOf(GameException.class)
                .hasMessageContaining("Symbol [5] is not alphabetic");
    }

    @Test
    void shouldThrowWhenGameIsLost() {
        int newGameId = gameManager.createGame();
        List.of('b', 'c', 'f', 'g', 'i', 'j', 'k', 'm', 'n', 'p')
                .forEach(guess -> gameManager.guessLetter(newGameId, guess));
        assertThatThrownBy(() -> gameManager
                .guessLetter(newGameId, 'R'))
                .isInstanceOf(GameException.class)
                .hasMessageContaining("Game is already over");
    }

    @Test
    void shouldThrowWhenGameIsWon() {
        int newGameId = gameManager.createGame();
        List.of('a', 'd', 'e', 'l')
                .forEach(guess -> gameManager.guessLetter(newGameId, guess));
        assertThatThrownBy(() -> gameManager
                .guessLetter(newGameId, 'Z'))
                .isInstanceOf(GameException.class)
                .hasMessageContaining("Game is already over");
    }

}