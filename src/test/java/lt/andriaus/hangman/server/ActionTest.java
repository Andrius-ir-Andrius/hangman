package lt.andriaus.hangman.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import lt.andriaus.hangman.domain.Game;
import lt.andriaus.hangman.domain.GameException;
import lt.andriaus.hangman.usecase.GameManager;
import lt.andriaus.hangman.util.RequestStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;

import java.util.Map;
import java.util.Optional;

import static lt.andriaus.hangman.util.GuessGameRequestBody.toJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActionTest {
    private final Optional<Game> optionalGame = Optional.of(Game.Builder.fromWord("TEST").build());
    @Mock
    GameManager gameManager;
    @InjectMocks
    Action action;

    @BeforeEach
    void setup() {
        action = new Action(gameManager);
    }

    @Test
    void shouldCreateGame() {

        when(gameManager.createGame()).thenReturn(Optional.of(0));
        Optional<Integer> id = action.createGame();
        assertThat(id).hasValue(0);

    }

    @Test
    void shouldLoadGame() {

        when(gameManager.createGame()).thenReturn(Optional.of(0));
        when(gameManager.loadGame(0)).thenReturn(optionalGame);
        Optional<Integer> id = action.createGame();
        Request request = new RequestStub(Map.of("id", id.get() + ""));
        Optional<Game> game = action.loadGame(request);
        assertThat(game).isPresent();

    }

    @Test
    void shouldNotFindWrongIdGame() {
        when(gameManager.loadGame(-1)).thenReturn(Optional.empty());
        Request wrongIdRequest = new RequestStub(Map.of("id", "-1"));
        Optional<Game> wrongIdGame = action.loadGame(wrongIdRequest);
        assertThat(wrongIdGame).isEmpty();
    }

    @Test
    void shouldNotFindAlphabeticIdGame() {
        Request notNumericRequest = new RequestStub(Map.of("id", "a"));
        assertThatThrownBy(() -> action.loadGame(notNumericRequest))
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    void shouldGuessLetter() throws JsonProcessingException {
        when(gameManager.createGame()).thenReturn(Optional.of(0));
        when(gameManager.guessLetter(0, 'a')).thenReturn(optionalGame);
        Optional<Integer> id = action.createGame();
        Request request = new RequestStub(
                toJson(Map.of("id", id.get(), "letter", 'a'))
        );
        Optional<Game> game = action.guessLetter(request);
        assertThat(game).isPresent();
    }

    @Test
    void shouldNotGuessLetterInEmptyGame() throws JsonProcessingException {
        when(gameManager.guessLetter(-1, 'a')).thenReturn(Optional.empty());

        Request wrongIdRequest = new RequestStub(
                toJson(Map.of("id", -1, "letter", "a"))
        );

        Optional<Game> wrongIdGame = action.guessLetter(wrongIdRequest);

        assertThat(wrongIdGame).isEmpty();

    }

    @Test
    void shouldNotGuessNonAlphabeticLetter() throws JsonProcessingException {
        when(gameManager.guessLetter(0, '5')).thenThrow(
                GameException.symbolIsNotAlphabeticException('5')
        );

        Request wrongLetterRequest = new RequestStub(
                toJson(Map.of("id", 0, "letter", "5"))
        );

        assertThatThrownBy(() -> action.guessLetter(wrongLetterRequest))
                .isInstanceOf(GameException.class)
                .hasMessage("Symbol [5] is not alphabetic");
    }

    @Test
    void shouldNotGuessStringLetter() throws JsonProcessingException {
        Request stringLetterRequest = new RequestStub(
                toJson(Map.of("id", -1, "letter", "labas"))
        );

        assertThatThrownBy(() -> action.guessLetter(stringLetterRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Wrong request body");
    }

    @Test
    void shouldNotGuessLetterWithAlphabeticGameId() throws JsonProcessingException {
        Request stringIdRequest = new RequestStub(
                toJson(Map.of("id", "a", "letter", "a"))
        );

        assertThatThrownBy(() -> action.guessLetter(stringIdRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Wrong request body");
    }
}