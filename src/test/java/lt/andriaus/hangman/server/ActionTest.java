package lt.andriaus.hangman.server;

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
        assertThat(id).isPresent().hasValue(0);

    }

    @Test
    void shouldLoadGame() {

        when(gameManager.createGame()).thenReturn(Optional.of(0));
        when(gameManager.loadGame(0)).thenReturn(optionalGame);
        Optional<Integer> id = action.createGame();
        Request request = new RequestStub(Map.of("id", id.orElse(-1) + ""));
        Optional<Game> game = action.loadGame(request);
        assertThat(game).isPresent();

    }

    @Test
    void shouldNotFindGame() {
        when(gameManager.loadGame(-1)).thenReturn(Optional.empty());
        Request wrongIdRequest = new RequestStub(Map.of("id", "-1"));
        Request notNumericRequest = new RequestStub(Map.of("id", "a"));
        Optional<Game> wrongIdGame = action.loadGame(wrongIdRequest);
        assertThat(wrongIdGame).isEmpty();
        assertThatThrownBy(() -> action.loadGame(notNumericRequest))
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    void shouldGuessLetter() {
        when(gameManager.createGame()).thenReturn(Optional.of(0));
        when(gameManager.guessLetter(0, 'a')).thenReturn(optionalGame);
        Optional<Integer> id = action.createGame();
        Request request = new RequestStub(
                String.format(
                        "{\"id\":\"%s\", \"letter\":\"%s\"}",
                        id.orElse(-1),
                        "a")
        );
        Optional<Game> game = action.guessLetter(request);
        assertThat(game).isPresent();
    }

    @Test
    void shouldNotGuessLetter(){
        when(gameManager.guessLetter(-1, 'a')).thenReturn(Optional.empty());
        when(gameManager.guessLetter(0, '5')).thenThrow(GameException.symbolIsNotAlphabeticException('5'));

        String template = "{\"id\":\"%s\", \"letter\":\"%s\"}";

        Request wrongIdRequest = new RequestStub(String.format(template, -1, "a"));
        Request wrongLetterRequest = new RequestStub(String.format(template, 0, "5"));
        Request stringLetterRequest = new RequestStub(String.format(template, -1, "labs"));
        Request stringIdRequest = new RequestStub(String.format(template, "a", "l"));

        Optional<Game> wrongIdGame = action.guessLetter(wrongIdRequest);

        assertThat(wrongIdGame).isEmpty();

        assertThatThrownBy(() -> action.guessLetter(wrongLetterRequest))
                .isInstanceOf(GameException.class)
                .hasMessage("Symbol [5] is not alphabetic");

        assertThatThrownBy(() -> action.guessLetter(stringLetterRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("A single character was expected, [labs] was given");

        assertThatThrownBy(() -> action.guessLetter(stringIdRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Integer was expected, [a] was given");
    }
}