package lt.andriaus.hangman.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.andriaus.hangman.util.ExceptionResponseJSON;
import lt.andriaus.hangman.util.GuessGameRequestBody;
import spark.Request;
import spark.Response;

import java.util.Optional;
import java.util.function.Supplier;

public class RequestProcess {
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
            return InternalServerError(res, e.getMessage());
        }
    }

    public static String InternalServerError(Response res, String message) {
        res.status(500);
        return convertToJson(new ExceptionResponseJSON(message));
    }

    public static String resultNotFound(Request req, Response res) {
        res.status(404);
        return convertToJson(
                new ExceptionResponseJSON("Result not found for request: id=" + getIdFromQueryAndBody(req))
        );
    }

    private static String getIdFromBody(Request req) {
        try {
            return new ObjectMapper().readValue(req.body(), GuessGameRequestBody.class).getId() + "";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String getIdFromQueryAndBody(Request req) {
        String idFromQuery = req.queryParams("id");
        if (null != idFromQuery)
            return req.queryParams("id");
        return getIdFromBody(req);
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
