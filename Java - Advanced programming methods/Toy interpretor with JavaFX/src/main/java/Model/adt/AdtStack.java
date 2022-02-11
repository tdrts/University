package Model.adt;

import Exceptions.AdtException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AdtStack <T> implements IStack<T> {
    Stack<T> stack;

    public AdtStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws AdtException{
        if (stack.isEmpty()){
            throw new AdtException("Stack is empty");
        }
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T v: (stack)){
            sb.append(v.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<T> getContent() {
        return new ArrayList<>(stack);
    }

}
