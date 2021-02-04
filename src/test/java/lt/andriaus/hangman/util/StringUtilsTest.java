package lt.andriaus.hangman.util;

import lt.andriaus.hangman.database.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

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
    void shouldReturnList() {
        List<Character> wordList = Utils.StringToCharList(wordString);

        assertThat(wordList.get(0)).isEqualTo(wordList.get(0));
        assertThat(wordList.get(1)).isEqualTo(wordList.get(1));
        assertThat(wordList.size()).isEqualTo(wordString.length());
    }
}