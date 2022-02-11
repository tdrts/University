package Model.adt;

import Exceptions.AdtException;

import java.util.HashMap;

public interface IHeap<V> {
    int allocate(V value);
    V get(int addr);
    void update(int addr, V value);
    V deallocate(int addr);
    HashMap<Integer, V> getContent();
    boolean is_defined(int addr);
    void set(HashMap<Integer, V> new_dict);
}
