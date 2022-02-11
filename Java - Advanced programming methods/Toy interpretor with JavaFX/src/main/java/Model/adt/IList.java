package Model.adt;

import Exceptions.AdtException;

import java.util.List;

public interface IList<T> {
    List<T> getAll();
    void add_element(T v);
    void remove_element(T v) throws AdtException;
    String toString();
    int get_size();
    T get_element_by_position(int i) throws AdtException;
}
