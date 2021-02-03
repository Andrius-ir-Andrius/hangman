package lt.andriaus.hangman.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class Game {
    private final String word;
    private final List<Character> letters;

    private Game(String word, List<Character> letters) {
        this.word = word;
        this.letters = letters;
    }

    public String getWord() {
        return word;
    }

    public Game guessLetter(char letter) {
        List<Character> list = new ArrayList<>(letters);
        if (Character.isAlphabetic(letter))
            list.add(letter);

        return Builder.from(this)
                .withLetters(list)
                .build();
    }

    public List<Character> getLetters() {
        return this.letters;
    }


    public static class Builder {
        private final String word;
        private List<Character> letters;

        public Builder(String word, List<Character> letters) {
            this.word = word;
            this.letters = letters;
        }

        public static Builder from(Game game) {
            return new Builder(game.word, game.letters);
        }

        public static Builder fromWord(String word) {
            return new Builder(word, emptyList());
        }

        public Builder withLetters(List<Character> letters) {
            this.letters = unmodifiableList(letters);
            return new Builder(word, this.letters);
        }

        public Game build() {
            return new Game(word, letters);
        }
    }

}
