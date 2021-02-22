package lt.andriaus.hangman.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GuessGameResponseBody {
    private final String word;
    private final List<String> guessedLetters;

    public GuessGameResponseBody(
            @JsonProperty("word") String word,
            @JsonProperty("guessedLetters") List<String> guessedLetters
    ) {
        this.word = word;
        this.guessedLetters = guessedLetters;
    }

    public List<String> getGuessedLetters() {
        return guessedLetters;
    }

    public String getWord() {
        return word;
    }
}
