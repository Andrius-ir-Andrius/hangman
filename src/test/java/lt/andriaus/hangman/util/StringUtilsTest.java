package lt.andriaus.hangman.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {
    private static String wordString;

    @BeforeAll
    static void setup() {
        wordString = "hello";
    }

    @Test
    void shouldReturnSet() {
        Set<Character> wordSet = Utils.stringToCharSet(wordString);

        assertThat(wordSet.contains('h')).isTrue();
        assertThat(wordSet.size()).isEqualTo(wordString.length() - 1);
    }

    @Test
    void shouldReturnCharList() {
        List<Character> wordList = Utils.stringToCharList(wordString);

        assertThat(wordList.get(0)).isEqualTo(wordString.charAt(0));
        assertThat(wordList.get(1)).isEqualTo(wordString.charAt(1));
        assertThat(wordList).hasSize(wordString.length());
    }
}
