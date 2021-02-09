package lt.andriaus.hangman.util;

import lt.andriaus.hangman.domain.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JSONGameTest {
    JSONGame jsonGame;
    @Mock
    Game game;
    @BeforeEach
    void setup(){
        when(game.getWord()).thenReturn("MAMAS");
    }
    @Test
    void shouldBeBLank() {
        when(game.getGuessedLetters()).thenReturn(Set.of());
        jsonGame = new JSONGame(game);
        assertThat(jsonGame.getWord()).isEqualTo("_____");
    }

    @Test
    void shouldOpenSingle() {
        when(game.getGuessedLetters()).thenReturn(Set.of('S'));
        jsonGame = new JSONGame(game);
        assertThat(jsonGame.getWord()).isEqualTo("____S");
    }

    @Test
    void shouldOpenAll() {
        when(game.getGuessedLetters()).thenReturn(Set.of('M', 'A', 'S'));
        jsonGame = new JSONGame(game);
        assertThat(jsonGame.getWord()).isEqualTo("MAMAS");
    }

}