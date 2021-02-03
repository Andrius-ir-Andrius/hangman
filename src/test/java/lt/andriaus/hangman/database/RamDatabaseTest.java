package lt.andriaus.hangman.database;

import lt.andriaus.hangman.database.Database;
import lt.andriaus.hangman.database.RamDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RamDatabaseTest {
    List<String> data = Arrays.asList("ABC", "DEF", "GHI");

    @Test
    void shouldAddNew() {
        Database<String> database = new RamDatabase<>();
        int one = database.save(data.get(0));
        int two = database.save(data.get(1));
        assertThat(database.loadOne(one)).isEqualTo(data.get(0));
        assertThat(database.loadOne(two)).isEqualTo(data.get(1));
    }

    @Test
    void shouldUpdate() {
        Database<String> database = new RamDatabase<>();
        int one = database.save(data.get(0));
        int two = database.save(data.get(1));
        int three = database.save(data.get(2), one);
        assertThat(three).isEqualTo(one);
        assertThat(database.loadOne(one)).isEqualTo(data.get(2));
        assertThat(database.loadOne(two)).isEqualTo(data.get(1));
    }

    @Test
    void shouldNotFind() {
        Database<String> database = new RamDatabase<>();
        database.save(data.get(0));
        database.save(data.get(1));
        assertThat(database.loadOne(-1)).isEqualTo(null);
    }

    @Test
    void shouldRetrieveAll() {
        Database<String> database = new RamDatabase<>();
        data.forEach(database::save);
        List<String> result = new ArrayList<>(database.loadAll().values());
        assertThat(result.containsAll(data)).isTrue();
    }

    @Test
    void shouldNotModify() {
        Database<String> database = new RamDatabase<>();
        data.forEach(database::save);
        Map<Integer, String> result = database.loadAll();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> result.put(0, data.get(1)));
    }

}