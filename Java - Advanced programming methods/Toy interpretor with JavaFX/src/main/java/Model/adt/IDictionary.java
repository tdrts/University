package Model.adt;

import Exceptions.AdtException;

import java.util.Map;

public interface IDictionary<K,V> {
    void add_pair(K key, V value) throws AdtException;
    void remove(K key) throws AdtException;
    void update_pair(K key, V value) throws AdtException;

    boolean is_defined(K key);
    V lookup(K key);
    public Map<K, V> getContent();
    public IDictionary<K, V> createCopy();
}
