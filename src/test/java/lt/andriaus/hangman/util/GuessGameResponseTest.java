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
class GuessGameResponseTest {
    GuessGameResponse guessGameResponse;
    @Mock
    Game game;

    @BeforeEach
    void setup() {
        when(game.getWord()).thenReturn("MAMAS");
    }

    @Test
    void shouldBeBLank() {
        when(game.getGuessedLetters()).thenReturn(Set.of());
        guessGameResponse = new GuessGameResponse(game);
        assertThat(guessGameResponse.getWord()).isEqualTo("_____");
    }

    @Test
    void shouldOpenSingle() {
        when(game.getGuessedLetters()).thenReturn(Set.of('S'));
        guessGameResponse = new GuessGameResponse(game);
        assertThat(guessGameResponse.getWord()).isEqualTo("____S");
    }

    @Test
    void shouldOpenAll() {
        when(game.getGuessedLetters()).thenReturn(Set.of('M', 'A', 'S'));
        guessGameResponse = new GuessGameResponse(game);
        assertThat(guessGameResponse.getWord()).isEqualTo("MAMAS");
    }

}