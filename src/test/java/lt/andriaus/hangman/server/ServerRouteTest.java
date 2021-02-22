package lt.andriaus.hangman.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.andriaus.hangman.util.GuessGameRequestBody;
import lt.andriaus.hangman.util.GuessGameResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ServerRouteTest {

    private final String URL = "http://localhost:4567/game";
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    void setup() {
        Server.main(null);
    }

    @Test
    void shouldNotFindNonExistingRoute() throws UnirestException {
        HttpResponse<String> response = Unirest.get(URL + "a")
                .asString();
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    void shouldCreateGame() throws UnirestException {
        HttpResponse<String> response = Unirest.post(URL)
                .asString();
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getBody()).containsOnlyDigits();
        assertThat(parseInt(response.getBody())).isGreaterThan(-1);
    }

    @Test
    void shouldLoadGameAfterCreate() throws UnirestException, JsonProcessingException {
        int id = parseInt(Unirest.post(URL)
                .asString().getBody());
        HttpResponse<JsonNode> response = Unirest.get(URL)
                .queryString("id", id)
                .asJson();
        GuessGameResponseBody responseBody = mapper.readValue(
                response.getBody().toString(), GuessGameResponseBody.class
        );
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(responseBody.getGuessedLetters().isEmpty()).isTrue();
    }

    @Test
    void shouldNotLoadNotExistingGame() throws UnirestException {
        HttpResponse<String> response = Unirest.get(URL)
                .queryString("id", -1)
                .asString();
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    void shouldGuessLetterAfterCreate() throws UnirestException, JsonProcessingException {
        int id = parseInt(Unirest.post(URL)
                .asString().getBody());
        HttpResponse<JsonNode> response = Unirest.put(URL)
                .body(GuessGameRequestBody.toJson(Map.of("id", id, "letter", 'a')))
                .asJson();
        GuessGameResponseBody responseBody = mapper.readValue(
                response.getBody().toString(), GuessGameResponseBody.class
        );
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(responseBody.getGuessedLetters().size()).isEqualTo(1);
        assertThat(responseBody.getGuessedLetters().contains("A")).isTrue();

    }

    @Test
    void shouldNotGuessNonAlphabeticSymbol() throws UnirestException, JsonProcessingException {
        int id = parseInt(Unirest.post(URL)
                .asString().getBody());
        HttpResponse<String> response = Unirest.put(URL)
                .body(GuessGameRequestBody.toJson(Map.of("id", id, "letter", '5')))
                .asString();
        assertThat(response.getStatus()).isEqualTo(500);
    }

    @Test
    void shouldNotGuessInNonExistingGame() throws UnirestException, JsonProcessingException {
        HttpResponse<String> response = Unirest.put(URL)
                .body(GuessGameRequestBody.toJson(Map.of("id", -1, "letter", 'a')))
                .asString();
        assertThat(response.getStatus()).isEqualTo(404);
    }
}
