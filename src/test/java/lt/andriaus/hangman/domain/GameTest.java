package lt.andriaus.hangman.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameTest {
    private final String word = "ABC";
    private final Game game = Game.Builder.fromWord(word).build();

    @Test
    void shouldCreateGameWithWord() {
        String result = game.getWord();
        assertThat(result).isEqualTo(word);
    }

    @Test
    void shouldAddLetterOnly() {
        Game spiedGame = spy(game);
        when(spiedGame.getGameStatus()).thenReturn(Game.GameStatus.ONGOING);

        Game firstGuess = spiedGame.guessLetter('D');

        assertThatThrownBy(() -> spiedGame.guessLetter('5'))
                .isInstanceOf(GameException.class)
                .hasMessageContaining(GameException.Exceptions.SymbolIsNotAlphabeticException.toString());
        assertThat(firstGuess.getGuessedLetters().contains('D')).isTrue();
    }

    @Test
    void shouldNotBeModified() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> game.getGuessedLetters().add('a'));
    }

    @Test
    void shouldUseUpperCaseOnly() {
        Game gameWithWrongWord = spy(Game.Builder.fromWord("aBc").build());
        when(gameWithWrongWord.getGameStatus()).thenReturn(Game.GameStatus.ONGOING);

        Game gameWithGuessedLetters = gameWithWrongWord.guessLetter('c');
        Set<Character> result = gameWithGuessedLetters.getGuessedLetters();

        assertThat(gameWithGuessedLetters.getWord()).isEqualTo("ABC");
        assertThat(result).contains('C');
    }

    @Test
    void shouldBeOngoing() {
        assertThat(game.getGameStatus()).isEqualTo(Game.GameStatus.ONGOING);
    }

    @Test
    void shouldBeVictory() {
        Set<Character> wordCharSet = word.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toSet());
        Game finishedGame = Game.Builder.fromWord(word).withLetters(wordCharSet).build();
        assertThat(finishedGame.getGameStatus()).isEqualTo(Game.GameStatus.VICTORY);
    }

    @Test
    void shouldBeLoss() {
        Set<Character> wordCharSet = "ADEFGHIJKLM".chars()
                .mapToObj(e -> (char) e).collect(Collectors.toSet());
        Game lostGame = Game.Builder.fromWord(word).withLetters(wordCharSet).build();
        assertThat(lostGame.getGameStatus()).isEqualTo(Game.GameStatus.LOST);
    }

}
