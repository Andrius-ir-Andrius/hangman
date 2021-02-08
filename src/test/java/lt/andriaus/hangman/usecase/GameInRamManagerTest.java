package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.domain.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameInRamManagerTest {
    GameInRamManager gameInRamManager;
    @Mock
    Game game;
    @Mock
    RamDatabase<String> wordDB;
    @Mock
    RamDatabase<Game> gameDB;


    @BeforeEach
    void setup(){
        gameInRamManager = new GameInRamManager(wordDB, gameDB);
    }

    @Test
    void shouldCreateGame() {
        Map<Integer, String> wordMap = new HashMap<>();
        wordMap.put(0, "HELLO");
        when(wordDB.loadAll()).thenReturn(wordMap);
        when(gameDB.save(any())).thenReturn(0);
        int newGameId = gameInRamManager.createGame();
        assertThat(newGameId).isEqualTo(0);
    }

    @Test
    void shouldLoadGame() {
        when(game.getWord()).thenReturn("HELLO");
        when(gameDB.loadOne(0)).thenReturn(Optional.of(game));
        Optional<Game> existingGame = gameInRamManager.loadGame(0);
        assertThat(existingGame.isPresent()).isTrue();
        assertThat(existingGame.get().getWord()).isEqualTo("HELLO");
    }
    @Test
    void shouldNotLoadGame() {
        when(gameDB.loadOne(1)).thenReturn(Optional.empty());
        Optional<Game> notExistingGame = gameInRamManager.loadGame(1);
        assertThat(notExistingGame.isEmpty()).isTrue();
    }
}