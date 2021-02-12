package lt.andriaus.hangman.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class RequestBody {
    private final int id;
    private final char letter;

    public RequestBody(@JsonProperty("id") int id, @JsonProperty("letter") char letter) {
        this.id = id;
        this.letter = letter;
    }

    public int getId() {
        return id;
    }

    public char getLetter() {
        return letter;
    }

    public static String toJson(Map<String, Object> payload) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(payload);
    }
}