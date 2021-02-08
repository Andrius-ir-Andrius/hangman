package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.database.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class RamDatabaseTest {
    private final List<String> DATA = Arrays.asList("ABC", "DEF", "GHI");
    Database<String> database;

    @BeforeEach
    void prepare() {
        database = new RamDatabase<>();
    }

    @Test
    void shouldAddNew() {
        int one = database.save(DATA.get(0));
        Optional<String> recordValue = database.loadOne(one);
        assertThat(recordValue.isPresent()).isTrue();
        assertThat(recordValue.get()).isEqualTo(DATA.get(0));
    }

    @Test
    void shouldUpdate() {
        int originalId = database.save(DATA.get(0));
        int updatedId = database.save(DATA.get(2), originalId);
        Optional<String> updatedValue = database.loadOne(updatedId);
        assertThat(originalId).isEqualTo(updatedId);
        assertThat(updatedValue.isPresent()).isTrue();
        assertThat(updatedValue.get()).isEqualTo(DATA.get(2));
    }

    @Test
    void shouldNotFind() {
        assertThat(database.loadOne(-1).isEmpty()).isTrue();
    }

    @Test
    void shouldRetrieveAll() {
        DATA.forEach(database::save);
        assertThat(database.loadAll().values().containsAll(DATA)).isTrue();
    }

    @Test
    void shouldNotModify() {
        DATA.forEach(database::save);
        Map<Integer, String> result = database.loadAll();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> result.put(0, DATA.get(1)));
    }

    @Test
    void shouldNotRetrieveRandom(){
        Optional<String> wordFromDB = database.loadRandom();
        assertThat(wordFromDB).isEmpty();
    }

    @Test
    void shouldRetrieveRandom(){
        String word = "test";
        database.save("test");
        Optional<String> wordFromDB = database.loadRandom();
        assertThat(wordFromDB).isNotEmpty();
        assertThat(wordFromDB.get()).isEqualTo(word);
    }

}
