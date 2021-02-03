package lt.andriaus.hangman.database;

import java.util.Map;

public interface Database<E> {
    E loadOne(int id);

    Map<Integer, E> loadAll();

    int save(E element);

    int save(E element, int id);
}
