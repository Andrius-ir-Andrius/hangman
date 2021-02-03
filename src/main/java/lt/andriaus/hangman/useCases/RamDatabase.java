package lt.andriaus.hangman.useCases;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.unmodifiableMap;

public class RamDatabase<E> implements Database<E> {
    private Map<Integer, E> gameList;
    private AtomicInteger atomicId;

    public RamDatabase() {
        this.gameList = new HashMap<>();
        atomicId = new AtomicInteger(0);
    }

    @Override
    public int save(E element) {
        int id = atomicId.getAndIncrement();
        this.gameList.put(id, element);
        return id;
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
