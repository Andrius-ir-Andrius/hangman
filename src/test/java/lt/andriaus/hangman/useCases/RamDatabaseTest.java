package lt.andriaus.hangman.useCases;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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

}