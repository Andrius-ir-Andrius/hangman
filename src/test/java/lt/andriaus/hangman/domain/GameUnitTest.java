package lt.andriaus.hangman.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameUnitTest {
    private final String word = "ABC";
    private final Game game = Game.Builder.fromWord(word).build();
    private final Game spiedGame = spy(game);

    @BeforeEach
    void prepare() {
        when(spiedGame.getGameStatus()).thenReturn(Game.GameStatus.ONGOING);
    }

    @Test
    void shouldCreateGameWithWord() {
        String result = game.getWord();
        assertThat(result).isEqualTo(word);
    }

    @Test
    void shouldAddLetterOnly() {
        Set<Character> guessedLetters = spiedGame.guessLetter('D').guessLetter('5').getGuessedLetters();
        assertThat(guessedLetters).contains('D');
        assertThat(guessedLetters).doesNotContain('5');
    }

    @Test
    void shouldNotBeModified() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> game.getGuessedLetters().add('a'));
    }

    @Test
    void shouldUseUpperCaseOnly() {
        Game gameWithWrongWord = spy(Game.Builder.fromWord("aBc").build());
        when(gameWithWrongWord.getGameStatus()).thenReturn(Game.GameStatus.ONGOING);

        Game guessedLetter = gameWithWrongWord.guessLetter('D').guessLetter('c');
        Set<Character> result = guessedLetter.getGuessedLetters();

        assertThat(guessedLetter.getWord()).isEqualTo("ABC");
        assertThat(result).contains('D');
        assertThat(result).contains('C');
    }

    @Test
    void shouldBeOngoing() {
        assertThat(game.getGameStatus()).isEqualTo(Game.GameStatus.ONGOING);
    }

    @Test
    void shouldBeVictory() {
        Set<Character> charList = word.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toSet());
        Game finishedGame = Game.Builder.fromWord(word).withLetters(charList).build();
        assertThat(finishedGame.getGameStatus()).isEqualTo(Game.GameStatus.VICTORY);
    }

    @Test
    void shouldBeLoss() {
        Set<Character> charList = "ADEFGHIJKLM".chars()
                .mapToObj(e -> (char) e).collect(Collectors.toSet());
        Game lostGame = Game.Builder.fromWord(word).withLetters(charList).build();
        assertThat(lostGame.getGameStatus()).isEqualTo(Game.GameStatus.LOSS);
    }

}
