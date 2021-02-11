package lt.andriaus.hangman.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
class StringUtilsTest {
    private String wordString;

    @BeforeAll
    void setup() {
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
        Set<Character> inputSet = Set.of('a', 'b', 'c');
        String receivedString = StringUtils.charSetToString(inputSet);
        assertThat(receivedString).contains("a");
        assertThat(receivedString).contains("b");
        assertThat(receivedString).contains("c");
        assertThat(receivedString.length()).isEqualTo(3);
    }

    @Test
    void shouldFindRegexGroups() {
        String regexPattern = "(([\\w\\s']+) ((\\d+)\\@(\\d+)))";
        String stringToLookAt = "hello it's me 45@321";
        List<String> groups = StringUtils.getRegexGroups(regexPattern, stringToLookAt);

        assertThat(groups.size()).isEqualTo(6);
        assertThat(groups.get(0)).isEqualTo(stringToLookAt);
        assertThat(groups.get(1)).isEqualTo(stringToLookAt);
        assertThat(groups.get(2)).isEqualTo("hello it's me");
        assertThat(groups.get(3)).isEqualTo("45@321");
        assertThat(groups.get(4)).isEqualTo("45");
        assertThat(groups.get(5)).isEqualTo("321");
    }
}
