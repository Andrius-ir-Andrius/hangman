package lt.andriaus.hangman.useCases;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class RamDatabase<E> implements Database<E> {
    private Map<Integer, E> gameList;
    private int id;

    public RamDatabase() {
        this.gameList = new HashMap<>();
        id = 0;
    }

    @Override
    public int save(E element) {
        this.gameList.put(this.id++, element);
        return id - 1;
    }

    @Override
    public int save(E element, int id) {
        this.gameList.put(id, element);
        return id;
    }

    @Override
    public E loadOne(int id) {
        return this.gameList.get(id);
    }

    @Override
    public Map<Integer, E> loadAll() {
        return unmodifiableMap(this.gameList);
    }
}
