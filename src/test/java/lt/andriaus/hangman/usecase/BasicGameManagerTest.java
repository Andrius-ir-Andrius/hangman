package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.domain.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicGameManagerTest {
    BasicGameManager basicGameManager;
    @Mock
    Game game;
    @Mock
    RamDatabase<String> wordDB;
    @Mock
    RamDatabase<Game> gameDB;


    @BeforeEach
    void setup(){
        basicGameManager = new BasicGameManager(wordDB, gameDB);
    }

    @Test
    void shouldNotCreateGame() {
        when(wordDB.loadRandom()).thenReturn(Optional.empty());
        assertThatThrownBy(() ->basicGameManager.createGame())
                .isInstanceOf(GameManagerException.class)
                .hasMessageContaining("FailedToCreateGameException");
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
        assertThat(existingGame.isPresent()).isTrue();
        assertThat(existingGame.get().getWord()).isEqualTo("HELLO");
    }
    @Test
    void shouldNotLoadGame() {
        when(gameDB.loadOne(1)).thenReturn(Optional.empty());
        Optional<Game> notExistingGame = basicGameManager.loadGame(1);
        assertThat(notExistingGame.isEmpty()).isTrue();
    }
}