package Model.adt;

import Exceptions.AdtException;

import java.security.Key;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class AdtDictionary<K,V> implements IDictionary<K,V> {
    private Map<K,V> dict;

    public AdtDictionary(){
        this.dict = new HashMap<>();
    }

    @Override
    public void add_pair(K key, V value) throws AdtException {
        if (dict.containsKey(key)){
            throw new AdtException("Key already exists");
        }
        dict.put(key,value);
    }

    @Override
    public Map<K, V> getContent() {
        return dict;
    }

    @Override
    public void remove(K key) throws AdtException {
        if (!dict.containsKey(key)){
            throw new AdtException("Key doesn't exists");
        }
        dict.remove(key);
    }

    @Override
    public void update_pair(K key, V value) throws AdtException {
//        if (!dict.containsKey(key)){
//            throw new AdtException("Key doesn't exists");
//        }
        dict.put(key, value);
    }

    @Override
    public boolean is_defined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public V lookup(K key) {
        return dict.get(key);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (K e:dict.keySet()){
            if (e!= null){
                sb.append(e.toString()).append(" : ").append(dict.get(e).toString()).append('\n');
            }
        }
        return sb.toString();
    }

    public AdtDictionary<K, V> createCopy(){
        AdtDictionary<K, V> copy = new AdtDictionary<>();
        for (var key: dict.keySet())
        {
            copy.dict.put(key,dict.get(key));
        }
        return copy;
    }
}
