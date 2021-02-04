package lt.andriaus.hangman.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GameIntegrationTest {
    private final String word = "ABC";
    private final Set<Character> guessedLettersList = "ABDEFGHIJK".chars()
            .mapToObj(e -> (char) e).collect(Collectors.toSet());
    private final Game game = Game.Builder.fromWord(word).withLetters(guessedLettersList).build();

    @Test
    void shouldAddLetter() {
        Game ongoingGame = game.guessLetter('L');
        assertThat(ongoingGame.getGuessedLetters().contains('L')).isTrue();
    }

    @Test
    void shouldBeOngoing() {
        Game ongoingGame = game.guessLetter('L').guessLetter('A').guessLetter('l');
        assertThat(ongoingGame.getGameStatus()).isEqualTo(Game.GameStatus.ONGOING);
    }

    @Test
    void shouldBeVictory() {
        Game finishedGame = game.guessLetter('L').guessLetter('C');
        assertThat(finishedGame.getGameStatus()).isEqualTo(Game.GameStatus.VICTORY);
    }

    @Test
    void shouldBeLoss() {
        Game lostGame = game.guessLetter('L').guessLetter('m');
        assertThat(lostGame.getGameStatus()).isEqualTo(Game.GameStatus.LOSS);
    }

}
