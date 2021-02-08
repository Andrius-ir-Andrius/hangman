package lt.andriaus.hangman.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

    static public Set<Character> StringToCharSet(String inputString) {
        return inputString.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toSet());
    }

    static public List<Character> StringToCharList(String inputString) {
        return inputString.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toList());
    }

    static public String CharSetToString(Set<Character> inputSet) {
        List<String> stringList = inputSet.stream().map(String::valueOf).collect(Collectors.toList());
        return String.join("", stringList);
    }
}
