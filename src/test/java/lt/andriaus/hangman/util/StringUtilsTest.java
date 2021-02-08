package lt.andriaus.hangman.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {
    private static String wordString;

    @BeforeAll
    static void setup() {
        wordString = "hello";
    }

    @Test
    void shouldReturnSet() {
        Set<Character> wordSet = Utils.StringToCharSet(wordString);

        assertThat(wordSet.contains('h')).isTrue();
        assertThat(wordSet.size()).isEqualTo(wordString.length() - 1);
    }

    @Test
    void shouldReturnCharList() {
        List<Character> wordList = Utils.StringToCharList(wordString);

        assertThat(wordList.get(0)).isEqualTo(wordString.charAt(0));
        assertThat(wordList.get(1)).isEqualTo(wordString.charAt(1));
        assertThat(wordList.size()).isEqualTo(wordString.length());
    }

    @Test
    void shouldReturnString() {
        Set<Character> wordSet = Utils.StringToCharSet(wordString);
        String setString = Utils.CharSetToString(wordSet);
        for(int i = 0; i < setString.length(); i++){
            assertThat(wordSet.contains(setString.charAt(i))).isTrue();
        }
    }
}
