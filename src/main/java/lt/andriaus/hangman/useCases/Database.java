package lt.andriaus.hangman.useCases;

import java.util.Map;

public interface Database<E> {
    public E loadOne(int id);

    public Map<Integer, E> loadAll();

    public int save(E element);

    public int save(E element, int id);
}
