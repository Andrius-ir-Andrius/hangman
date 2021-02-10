package lt.andriaus.hangman.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StringUtils {

    static public Set<Character> stringToCharSet(String inputString) {
        return inputString.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toSet());
    }

    static public List<Character> stringToCharList(String inputString) {
        return inputString.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toList());
    }
}
