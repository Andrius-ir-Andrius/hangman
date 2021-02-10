package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.domain.Game;
import lt.andriaus.hangman.gateway.api.Database;
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
class WithDatabaseGameManagerTest {
    WithDatabaseGameManager withDatabaseGameManager;
    @Mock
    Game game;
    @Mock
    Database<String> wordDB;
    @Mock
    Database<Game> gameDB;


    @BeforeEach
    void setup() {
        withDatabaseGameManager = new WithDatabaseGameManager(wordDB, gameDB);
    }

    @Test
    void shouldNotCreateGame() {
        when(wordDB.loadRandom()).thenReturn(Optional.empty());
        assertThatThrownBy(() -> withDatabaseGameManager.createGame())
                .isInstanceOf(GameManagerException.class)
                .hasMessageContaining("Failed to create Game");
    }

    @Test
    void shouldCreateGame() {
        String word = "HELLO";
        when(wordDB.loadRandom()).thenReturn(Optional.of(word));
        int newGameId = withDatabaseGameManager.createGame();
        assertThat(newGameId).isEqualTo(0);
    }

    @Test
    void shouldLoadGame() {
        when(game.getWord()).thenReturn("HELLO");
        when(gameDB.loadOne(0)).thenReturn(Optional.of(game));
        Optional<Game> existingGame = withDatabaseGameManager.loadGame(0);
        assertThat(existingGame.map(Game::getWord)).hasValue("HELLO");
    }

    @Test
    void shouldNotLoadGame() {
        Optional<Game> notExistingGame = withDatabaseGameManager.loadGame(1);
        assertThat(notExistingGame).isEmpty();
    }
}