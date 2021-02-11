package lt.andriaus.hangman.server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class ServerTest {
    @BeforeAll
    static void setup() {
        Server.main(null);
    }

    HttpURLConnection createConnection(String url, String method) throws IOException {
        HttpURLConnection urlConn = (HttpURLConnection) new URL(url).openConnection();
        urlConn.setRequestMethod(method);
        urlConn.setDoOutput(true);
        return urlConn;
    }

    void setRequestBody(HttpURLConnection urlConn, String requestBody) throws IOException {
        OutputStream os = urlConn.getOutputStream();
        os.write(requestBody.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    String getContentsFromConnectionStream(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        StringBuilder respDataBuf = new StringBuilder();
        respDataBuf.setLength(0);
        int b;
        while ((b = inputStream.read()) != -1) {
            respDataBuf.append((char) b);
        }
        return respDataBuf.toString();
    }

    String getUrlContents(String url, String method, String requestBody) throws IOException {
        HttpURLConnection connection = createConnection(url, method);
        if (!requestBody.equals(""))
            setRequestBody(connection, requestBody);
        return getContentsFromConnectionStream(connection);
    }

    @Test
    void aa() throws IOException {
        URL url = new URL("http://localhost:4567");
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        assertThat(urlConn.getResponseCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @Test
    void shouldCreateGame() throws IOException {
        URL url = new URL("http://localhost:4567/game");
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestMethod("POST");
        assertThat(urlConn.getResponseCode()).isEqualTo(HttpURLConnection.HTTP_CREATED);
    }

    @Test
    void shouldLoadGameAfterCreate() throws IOException {
        String id = getUrlContents("http://localhost:4567/game", "POST", "");
        String loadedGame = getUrlContents("http://localhost:4567/game?id=" + id, "GET", "");

        assertThat(loadedGame).contains("\"guessedLetters\":[]");
    }

    @Test
    void shouldNotLoadGame() {
        assertThatThrownBy(() -> getUrlContents("http://localhost:4567/game?id=-1", "GET", ""))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void shouldGuessLetterAfterCreate() throws IOException {
        String id = getUrlContents("http://localhost:4567/game", "POST", "");
        String payload = String.format("{\"id\":\"%s\", \"letter\":\"%s\"}", id, "a");
        String loadedGame = getUrlContents("http://localhost:4567/game", "PUT", payload);

        assertThat(loadedGame).contains("\"guessedLetters\":[\"A\"]");
    }

    @Test
    void shouldNotGuessNonAlphabeticSymbol() throws IOException {
        String id = getUrlContents("http://localhost:4567/game", "POST", "");
        String payload = String.format("{\"id\":\"%s\", \"letter\":\"%s\"}", id, "5");
        assertThatThrownBy(() -> getUrlContents(
                "http://localhost:4567/game",
                "PUT",
                payload)
        )
                .isInstanceOf(IOException.class).hasMessageContaining("500 for URL");
    }


    @Test
    void shouldNotGuessInNonExistingGame() {
        String payload = String.format("{\"id\":\"%s\", \"letter\":\"%s\"}", "-1", "a");
        assertThatThrownBy(() -> getUrlContents(
                "http://localhost:4567/game",
                "PUT",
                payload)
        )
                .isInstanceOf(IOException.class);
    }
}
