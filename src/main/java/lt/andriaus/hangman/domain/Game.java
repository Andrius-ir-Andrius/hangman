package lt.andriaus.hangman.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.*;

public class Game {
    private final String word;
    private final Set<Character> guessedLetters;

    public enum GameStatus {
        ONGOING, VICTORY, LOSS;
    }

    private Game(String word, Set<Character> letters) {
        this.word = word;
        this.guessedLetters = letters;
    }

    public String getWord() {
        return word;
    }

    public Game guessLetter(Character letter) {
        if (getGameStatus() != GameStatus.ONGOING)
            return this;
        Set<Character> list = new HashSet<>(guessedLetters);
        if (Character.isAlphabetic(letter))
            list.add(letter.toString().toUpperCase().toCharArray()[0]);

        return Builder.fromGame(this)
                .withLetters(list)
                .build();

    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public GameStatus getGameStatus() {
        List<Character> wordChars = word.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());

        Set<Character> incorrectlyGuessedLetters = guessedLetters.stream()
                .filter(character -> !wordChars.contains((Character) character)).collect(Collectors.toSet());


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
        private final Set<Character> letters;

        public Builder(String word, Set<Character> letters) {
            this.word = word.toUpperCase();
            this.letters = letters;
        }

        public static Builder fromGame(Game game) {
            return new Builder(game.word, game.guessedLetters);
        }

        public static Builder fromWord(String word) {
            return new Builder(word, emptySet());
        }

        public Builder withLetters(Set<Character> letters) {
            return new Builder(word, unmodifiableSet(letters));
        }

        public Game build() {
            return new Game(word, letters);
        }
    }

}
