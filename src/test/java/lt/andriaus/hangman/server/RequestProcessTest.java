package lt.andriaus.hangman.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import lt.andriaus.hangman.util.RequestStub;
import org.junit.jupiter.api.Test;
import spark.Request;

import java.util.Map;

import static lt.andriaus.hangman.server.RequestProcess.getIdFromQueryAndBody;
import static lt.andriaus.hangman.util.GuessGameRequestBody.toJson;
import static org.assertj.core.api.Assertions.assertThat;

class RequestProcessTest {
/*
    @Test
    void process() {
    }

    @Test
    void resultNotFound() {
    }
*/

    @Test
    void shouldGetIdFromQuery() {
        Request request = new RequestStub(Map.of("id", "10"));
        assertThat(getIdFromQueryAndBody(request)).isEqualTo("10");
    }

    @Test
    void shouldGetIdFromBody() throws JsonProcessingException {
        Request request = new RequestStub(toJson(Map.of("id", 10)));
        assertThat(getIdFromQueryAndBody(request)).isEqualTo("10");
    }

    @Test
    void shouldNotGetId() throws JsonProcessingException {
        Request requestBody = new RequestStub(toJson(Map.of("a", 10)));
        Request requestQuery = new RequestStub(Map.of());
        assertThat(getIdFromQueryAndBody(requestBody)).isEqualTo("");
        assertThat(getIdFromQueryAndBody(requestQuery)).isEqualTo("");
    }

}