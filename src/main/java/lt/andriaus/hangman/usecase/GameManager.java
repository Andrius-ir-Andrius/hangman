package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.domain.Game;

import java.util.Optional;

public interface GameManager {
    Optional<Integer> createGame();

    Optional<Game> loadGame(int id);

    Optional<Game> guessLetter(int id, char letter);
}
