package Model.adt;
import Exceptions.AdtException;

import java.util.List;

public interface IStack<T> {
    T pop() throws AdtException;
    void push(T v);
    boolean isEmpty();
    List<T> getContent();
    //String getAll();
}
