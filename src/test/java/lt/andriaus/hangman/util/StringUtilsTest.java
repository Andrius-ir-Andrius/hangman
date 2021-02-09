package lt.andriaus.hangman.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StringUtilsTest {
    private static String wordString;

    @BeforeAll
    static void setup() {
        wordString = "hello";
    }

    @Test
    void shouldReturnSet() {
        Set<Character> wordSet = StringUtils.stringToCharSet(wordString);

        assertThat(wordSet.contains('h')).isTrue();
        assertThat(wordSet.size()).isEqualTo(wordString.length() - 1);
    }

    @Test
    void shouldReturnCharList() {
        List<Character> wordList = StringUtils.stringToCharList(wordString);

        assertThat(wordList.get(0)).isEqualTo(wordString.charAt(0));
        assertThat(wordList.get(1)).isEqualTo(wordString.charAt(1));
        assertThat(wordList.size()).isEqualTo(wordString.length());
    }

    @Test
    void shouldReturnString() {
        Set<Character> wordSet = new HashSet<>(List.of('a', 'b', 'c', 'd'));
        String setString = StringUtils.CharSetToString(wordSet);
        for(int i = 0; i < setString.length(); i++){
            assertThat(wordSet.contains(setString.charAt(i))).isTrue();
        }
        assertThat(setString.length()).isEqualTo(wordSet.size());
    }
}
