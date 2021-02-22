package lt.andriaus.hangman.util;

import lt.andriaus.hangman.domain.Game;

import java.util.Set;


/**
 * encapsulates Game and is used for turning into json
 */
public class GuessGameResponseJSON {
    private final Game game;

    public GuessGameResponseJSON(Game game) {
        this.game = game;
    }

    public Set<Character> getGuessedLetters() {
        return game.getGuessedLetters();
    }

    public String getWord() {
        String word = game.getWord();
        Set<Character> letters = game.getGuessedLetters();
        return word.replaceAll(String.format("[^\\s%s]", StringUtils.charSetToString(letters)), "_");
    }
}
