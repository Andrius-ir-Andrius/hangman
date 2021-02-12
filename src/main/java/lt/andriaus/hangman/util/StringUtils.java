package lt.andriaus.hangman.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StringUtils {

    public static Set<Character> stringToCharSet(String inputString) {
        return inputString.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toSet());
    }

    public static List<Character> stringToCharList(String inputString) {
        return inputString.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toList());
    }

    public static String charSetToString(Set<Character> inputSet) {
        return inputSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
