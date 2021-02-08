package lt.andriaus.hangman.usecase;

import lt.andriaus.hangman.database.Database;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.unmodifiableMap;

public class RamDatabase<E> implements Database<E> {
    private final Map<Integer, E> gamesById;
    private final AtomicInteger atomicId;

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
    public Optional<E> loadOne(int id) {
        return Optional.ofNullable(this.gamesById.get(id));
    }

    @Override
    public Map<Integer, E> loadAll() {
        return unmodifiableMap(this.gamesById);
    }
}
