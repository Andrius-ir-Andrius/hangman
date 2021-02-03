package lt.andriaus.hangman.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

        Game guessedLetter = game.guessLetter('D').guessLetter('5');
        List<Character> result = guessedLetter.getLetters();
        assertThat(result).contains('D');
        assertThat(result).doesNotContain('5');
    }

    @Test
    void shouldNotBeModified() {
        String word = "ABC";
        Game game = Game.Builder.fromWord(word).build();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> game.getLetters().add('a'));
    }

}
