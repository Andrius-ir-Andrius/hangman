package lt.andriaus.hangman.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    static public String charSetToString(Set<Character> inputSet) {
        return inputSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }

    static public List<String> getRegexGroups(String regexPattern, String string) {
        List<String> answer = new ArrayList<>();
        Matcher matcher = Pattern.compile(regexPattern).matcher(string);
        while (matcher.find())
            for (int i = 0; i <= matcher.groupCount(); i++)
                answer.add(matcher.group(i));
        return answer;
    }
}
