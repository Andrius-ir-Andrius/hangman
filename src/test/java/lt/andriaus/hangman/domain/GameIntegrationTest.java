package lt.andriaus.hangman.domain;

import lt.andriaus.hangman.util.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class GameIntegrationTest {
    private static String word;
    private static Set<Character> guessedLettersSet;
    private static Game game;

    @BeforeAll
    static void setUp() {
        word = "ABC";
        guessedLettersSet = Utils.StringToCharSet("ABDEFGHIJK");
        game = Game.Builder.fromWord(word).withLetters(guessedLettersSet).build();
    }

    @Test
    void shouldAddLetter() {
        Game ongoingGame = game.guessLetter('L');
        assertThat(ongoingGame.getGuessedLetters().contains('L')).isTrue();
    }

    @Test
    void shouldBeOngoing() {
        Game ongoingGame = game
                .guessLetter('L')
                .guessLetter('A')
                .guessLetter('l');
        assertThat(ongoingGame.getGameStatus()).isEqualTo(Game.GameStatus.ONGOING);
    }

    @Test
    void shouldBeVictory() {
        Game wonGame = game
                .guessLetter('L')
                .guessLetter('C');
        assertThat(wonGame.getGameStatus()).isEqualTo(Game.GameStatus.VICTORY);
    }

    @Test
    void shouldBeLoss() {
        Game lostGame = game
                .guessLetter('L')
                .guessLetter('m');
        assertThat(lostGame.getGameStatus()).isEqualTo(Game.GameStatus.LOST);
    }

    @Test
    void shouldThrowExceptionWhenGameIsOver() {
        Game lostGame = game
                .guessLetter('L')
                .guessLetter('m');

        Game wonGame = game
                .guessLetter('L')
                .guessLetter('C');

        assertThatThrownBy(() -> lostGame.guessLetter('z'))
                .isInstanceOf(GameException.class)
                .hasMessageContaining(GameException.Exceptions.GameIsAlreadyOverException.toString());
        assertThatThrownBy(() -> wonGame.guessLetter('z'))
                .isInstanceOf(GameException.class)
                .hasMessageContaining(GameException.Exceptions.GameIsAlreadyOverException.toString());
    }
}
