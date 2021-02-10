package lt.andriaus.hangman.gateway.implementation.inmemory;

import lt.andriaus.hangman.gateway.api.Database;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.unmodifiableMap;

public class InMemoryDatabase<E> implements Database<E> {
    private final Map<Integer, E> elementsById;
    private final AtomicInteger atomicId;

    public InMemoryDatabase() {
        this.elementsById = new HashMap<>();
        atomicId = new AtomicInteger();
    }

    @Override
    public int save(E element) {
        int id = atomicId.getAndIncrement();
        return save(element, id);
    }

    @Override
    public int save(E element, int id) {
        this.elementsById.put(id, element);
        return id;
    }

    @Override
    public Optional<E> loadRandom() {
        if (atomicId.get() == 0)
            return Optional.empty();
        int randomId = new Random().nextInt(elementsById.size());
        return Optional.of(elementsById.get(randomId));
    }

    @Override
    public Optional<E> loadOne(int id) {
        return Optional.ofNullable(this.elementsById.get(id));
    }

    @Override
    public Map<Integer, E> loadAll() {
        return unmodifiableMap(this.elementsById);
    }
}
