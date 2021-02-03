package lt.andriaus.hangman.database;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.unmodifiableMap;

public class RamDatabase<E> implements Database<E> {
    private final Map<Integer, E> gamesById;
    private AtomicInteger atomicId;

    public RamDatabase() {
        this.gamesById = new HashMap<>();
        atomicId = new AtomicInteger();
    }

    @Override
    public int save(E element) {
        int id = atomicId.getAndIncrement();
        return save(element, id);
    }

    @Override
    public int save(E element, int id) {
        this.gamesById.put(id, element);
        return id;
    }

    @Override
    public E loadOne(int id) {
        return this.gamesById.get(id);
    }

    @Override
    public Map<Integer, E> loadAll() {
        return unmodifiableMap(this.gamesById);
    }
}
