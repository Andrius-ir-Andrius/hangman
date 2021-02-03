package lt.andriaus.hangman.useCases;

public interface Database<E> {
    public E loadOne(int id);

    public int save(E element);

    public int save(E element, int id);
}
