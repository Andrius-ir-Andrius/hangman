package lt.andriaus.hangman.database;

import lt.andriaus.hangman.database.Database;
import lt.andriaus.hangman.database.RamDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RamDatabaseTest {
    private final List<String> data = Arrays.asList("ABC", "DEF", "GHI");
    Database<String> database;

    @BeforeEach
    void prepare() {
        database = new RamDatabase<>();
    }

    @Test
    void shouldAddNew() {
        int one = database.save(data.get(0));
        assertThat(database.loadOne(one)).isEqualTo(data.get(0));
    }

    @Test
    void shouldUpdate() {
        int originalId = database.save(data.get(0));
        int updatedId = database.save(data.get(2), originalId);
        String updatedValue = database.loadOne(updatedId);
        assertThat(originalId).isEqualTo(updatedId);
        assertThat(updatedValue).isEqualTo(data.get(2));
    }

    @Test
    void shouldNotFind() {
        assertThat(database.loadOne(-1)).isEqualTo(null);
    }

    @Test
    void shouldRetrieveAll() {
        data.forEach(database::save);
        assertThat(database.loadAll().values().containsAll(data)).isTrue();
    }

    @Test
    void shouldNotModify() {
        data.forEach(database::save);
        Map<Integer, String> result = database.loadAll();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> result.put(0, data.get(1)));
    }

}