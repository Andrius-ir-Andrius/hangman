package lt.andriaus.hangman.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class Game {
    private final String word;
    private final List<Character> guessedLetters;

    public enum GameStatus {
        ONGOING, VICTORY, LOSS;
    }

    private Game(String word, List<Character> letters) {
        this.word = word;
        this.guessedLetters = letters;
    }

    public String getWord() {
        return word;
    }

    public Game guessLetter(Character letter) {
        List<Character> list = new ArrayList<>(guessedLetters);
        if (Character.isAlphabetic(letter))
            list.add(letter.toString().toUpperCase().toCharArray()[0]);

        return Builder.fromGame(this)
                .withLetters(list)
                .build();

    }

    public List<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public GameStatus getGameStatus() {
        List<Character> wordChars = word.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());

        List<Character> incorrectlyGuessedLetters = guessedLetters.stream()
                .filter(character -> !wordChars.contains((Character) character)).collect(Collectors.toList());

        //if the amount of incorrectly guessed letters is higher than allowed
        if (incorrectlyGuessedLetters.size() >= 10)
            return GameStatus.LOSS;

        //if the game is already guessed correctly
        if (guessedLetters.containsAll(wordChars))
            return GameStatus.VICTORY;

        return GameStatus.ONGOING;
    }


    public static class Builder {
        private final String word;
        private final List<Character> letters;

        public Builder(String word, List<Character> letters) {
            this.word = word.toUpperCase();
            this.letters = letters;
        }

        public static Builder fromGame(Game game) {
            return new Builder(game.word, game.guessedLetters);
        }

        public static Builder fromWord(String word) {
            return new Builder(word, emptyList());
        }

        public Builder withLetters(List<Character> letters) {
            return new Builder(word, unmodifiableList(letters));
        }

        public Game build() {
            return new Game(word, letters);
        }
    }

}
