package lt.andriaus.hangman.database;

import java.util.Map;
import java.util.Optional;

public interface Database<E> {
    Optional<E> loadOne(int id);

    Map<Integer, E> loadAll();

    int save(E element);

    int save(E element, int id);
}
