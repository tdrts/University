package Model.adt;

import Exceptions.AdtException;

import java.util.HashMap;

public class AdtHeap<V> implements IHeap<V>{
    private HashMap<Integer,V> dict;
    private int next_free;

    public AdtHeap(){
        this.dict = new HashMap<Integer, V>();
        this.next_free = 0;
    }

    private void increment_next_free_address(){
        next_free++;
    }

    @Override
    public int allocate(V value) {
        increment_next_free_address();
        dict.put(getNext_free(), value);
        return getNext_free();
    }

    @Override
    public V get(int addr) {
        return dict.get(addr);
    }

    public void update(int addr, V value) {
        dict.put(addr,value);
    }

    @Override
    public V deallocate(int addr) {
        return dict.remove(addr);
    }

    @Override
    public HashMap<Integer, V> getContent() {
        return dict;
    }

    @Override
    public boolean is_defined(int addr) {
        return dict.containsKey(addr);
    }

    @Override
    public void set(HashMap<Integer, V> new_dict) {
        this.dict = new_dict;
    }

    public int getNext_free() {
        return next_free;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (var e:dict.keySet()){
            if (e!= null){
                sb.append(e.toString()).append(" -> ").append(dict.get(e).toString()).append('\n');
            }
        }
        return sb.toString();
    }
}
