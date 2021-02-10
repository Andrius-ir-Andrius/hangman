package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.gateway.api.Database;
import lt.andriaus.hangman.domain.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicGameManagerTest {
    BasicGameManager basicGameManager;
    @Mock
    Game game;
    @Mock
    Database<String> wordDB;
    @Mock
    Database<Game> gameDB;


    @BeforeEach
    void setup() {
        basicGameManager = new BasicGameManager(wordDB, gameDB);
    }

    @Test
    void shouldNotCreateGame() {
        when(wordDB.loadRandom()).thenReturn(Optional.empty());
        assertThatThrownBy(() -> basicGameManager.createGame())
                .isInstanceOf(GameManagerException.class)
                .hasMessageContaining("Failed to create Game");
    }

    @Test
    void shouldCreateGame() {
        String word = "HELLO";
        when(wordDB.loadRandom()).thenReturn(Optional.of(word));
        int newGameId = basicGameManager.createGame();
        assertThat(newGameId).isEqualTo(0);
    }

    @Test
    void shouldLoadGame() {
        when(game.getWord()).thenReturn("HELLO");
        when(gameDB.loadOne(0)).thenReturn(Optional.of(game));
        Optional<Game> existingGame = basicGameManager.loadGame(0);
        assertThat(existingGame).isPresent();
        assertThat(existingGame.map(Game::getWord)).hasValue("HELLO");
    }

    @Test
    void shouldNotLoadGame() {
        Optional<Game> notExistingGame = basicGameManager.loadGame(1);
        assertThat(notExistingGame).isEmpty();
    }
}