package lt.andriaus.hangman.util;

import spark.Request;

import java.util.Map;

public class RequestStub extends Request {
    private final String body;
    private final Map<String, String> query;

    private RequestStub(String body, Map<String, String> query) {
        this.body = body;
        this.query = query;
    }

    public RequestStub(String body) {
        this(body, null);
    }

    public RequestStub(Map<String, String> query) {
        this(null, query);
    }

    public String body() {
        return body;
    }

    public String queryParams(String query) {
        try {
            return this.query.get(query);
        } catch (Exception e) {
            return null;
        }
    }
}
