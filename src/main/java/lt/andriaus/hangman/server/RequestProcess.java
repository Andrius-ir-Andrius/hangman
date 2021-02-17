package lt.andriaus.hangman.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.andriaus.hangman.util.RequestBody;
import spark.Request;
import spark.Response;

import java.util.Optional;
import java.util.function.Supplier;

public class RequestProcess
{
    public static <T> String process(
            Request req,
            Response res,
            Supplier<Optional<T>> resultProducer) {
        try {
            Optional<T> resultOptional = resultProducer.get();
            if (resultOptional.isPresent())
                return resultOptional.map(RequestProcess::convertToJson).orElseThrow();
            else
                return resultNotFound(req, res);
        } catch (Exception e) {
            res.status(500);
            return e.getMessage();
        }
    }

    public static String resultNotFound(Request req, Response res) {
        res.status(404);
        return "Result not found for request: id=" + getIdFromQueryAndBody(req);
    }

    public static String getIdFromQueryAndBody(Request req) {
        String idFromQuery = req.queryParams("id");
        if (null != idFromQuery)
            return req.queryParams("id");
        try {
            return new ObjectMapper().readValue(req.body(), RequestBody.class).getId() + "";
        } catch (Exception e) {
            return "";
        }
    }

    private static <T> String convertToJson(T result) {
        try {
            return new ObjectMapper().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RestServerException("Failed to convert result to json", e);
        }
    }

    private static class RestServerException extends RuntimeException {
        public RestServerException(String message, Throwable e) {
            super(message, e);
        }
    }
}
