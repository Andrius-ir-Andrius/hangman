package lt.andriaus.hangman.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GameTest {

    @Test
    void shouldCreateGameWithWord() {
        String word = "ABC";
        Game game = Game.Builder.fromWord(word).build();

        String result = game.getWord();

        assertThat(result).isEqualTo(word);
    }

    @Test
    void shouldAddLetterOnly() {
        String word = "ABC";
        Game game = Game.Builder.fromWord(word).build();

        List<Character> guessedLetters = game.guessLetter('D').guessLetter('5').getGuessedLetters();
        assertThat(guessedLetters).contains('D');
        assertThat(guessedLetters).doesNotContain('5');
    }

    @Test
    void shouldNotBeModified() {
        String word = "ABC";
        Game game = Game.Builder.fromWord(word).build();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> game.getGuessedLetters().add('a'));
    }

    @Test
    void shouldUseUpperCaseOnly() {
        String word = "aBc";
        Game game = Game.Builder.fromWord(word).build();

        Game guessedLetter = game.guessLetter('D').guessLetter('c');
        List<Character> result = guessedLetter.getGuessedLetters();

        assertThat(guessedLetter.getWord()).isEqualTo("ABC");
        assertThat(result).contains('D');
        assertThat(result).contains('C');
    }

    @Test
    void shouldBeOngoing() {
        String word = "ABC";
        Game game = Game.Builder.fromWord(word).build();
        assertThat(game.getGameStatus()).isEqualTo(Game.GameStatus.ONGOING);
    }

    @Test
    void shouldBeVictory() {
        String word = "ABC";
        List<Character> charList = word.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());
        Game game = Game.Builder.fromWord(word).withLetters(charList).build();
        assertThat(game.getGameStatus()).isEqualTo(Game.GameStatus.VICTORY);
    }

    @Test
    void shouldBeLoss() {
        String word = "ABC";
        List<Character> charList = "ADEFGHIJKLM".chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());
        Game game = Game.Builder.fromWord(word).withLetters(charList).build();
        assertThat(game.getGameStatus()).isEqualTo(Game.GameStatus.LOSS);
    }

}
