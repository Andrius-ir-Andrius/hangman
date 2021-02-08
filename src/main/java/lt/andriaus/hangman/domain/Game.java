package lt.andriaus.hangman.domain;

import lt.andriaus.hangman.util.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.*;

public class Game {
    private final String word;
    private final Set<Character> guessedLetters;
    private static final int MAX_ALLOWED_LETTERS = 10;

    public enum GameStatus {
        ONGOING, VICTORY, LOST;
    }

    private Game(String word, Set<Character> guessedLetters) {
        this.word = word;
        this.guessedLetters = guessedLetters;
    }

    public String getWord() {
        return word;
    }

    public Game guessLetter(Character letter) {
        if (getGameStatus() != GameStatus.ONGOING)
            throw GameException.GameIsAlreadyOverException();

        if (!Character.isAlphabetic(letter))
            throw GameException.SymbolIsNotAlphabeticException();

        Set<Character> newSet = new HashSet<>(guessedLetters);
        newSet.add(Character.toUpperCase(letter));
        return Builder.fromGame(this).withLetters(newSet).build();
    }

    public String getMaskedWord() {
        return Utils.StringToCharList(word).stream().map(character -> {
            if(guessedLetters.contains(character))
                return character.toString();
            else
                return "_";
        }).collect(Collectors.joining());
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public GameStatus getGameStatus() {
        List<Character> wordChars = Utils.StringToCharList(word);

        Set<Character> incorrectlyGuessedLetters = guessedLetters
                .stream()
                .filter(character -> !wordChars.contains(character))
                .collect(Collectors.toSet());


        if (incorrectlyGuessedLetters.size() >= MAX_ALLOWED_LETTERS)
            return GameStatus.LOST;

        if (guessedLetters.containsAll(wordChars))
            return GameStatus.VICTORY;

        return GameStatus.ONGOING;
    }

    public static class Builder {
        private final String word;
        private final Set<Character> guessedLetters;

        public Builder(String word, Set<Character> guessedLetters) {
            this.word = word.toUpperCase();
            this.guessedLetters = guessedLetters;
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
            return new Game(word, guessedLetters);
        }
    }

}
