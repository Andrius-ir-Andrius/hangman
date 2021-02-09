package lt.andriaus.hangman.util;

import java.util.Map;

public class RequestStub extends spark.Request {
    private String body;
    private Map<String, String> query;

    public RequestStub(String body) {
        this.body = body;
    }

    public RequestStub(Map<String, String> query) {
        this.query = query;
    }

    public String body() {
        return body;
    }

    public String queryParams(String query) {
        return this.query.get(query);
    }
}
