package Model.adt;

import Exceptions.AdtException;

import java.util.ArrayList;
import java.util.List;

public class AdtList<T> implements IList<T>{
    List<T> list;

    public AdtList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void remove_element(T v) throws AdtException {
        try {
            list.remove(v);
        } catch (Exception e){
            throw new AdtException("Element " + v +" does not exist");
        }
    }

    @Override
    public int get_size() {
        return list.size();
    }

    @Override
    public T get_element_by_position(int i) throws AdtException {
        if (i >= this.get_size()){
            throw new AdtException("Index out of bounds");
        }
        return list.get(i);
    }

    @Override
    public String toString() {;
        StringBuilder out = new StringBuilder();
        for (T v: list){
            out.append(v.toString());
            out.append("\n");
        }
        return out.toString();
    }

    @Override
    public List<T> getAll() {
        return list;
    }

    @Override
    public void add_element(T v) {
        list.add(v);
    }
}
