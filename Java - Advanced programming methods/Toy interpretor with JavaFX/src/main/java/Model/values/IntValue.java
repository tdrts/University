package Model.values;

import Model.types.IntType;
import Model.types.Type;

public class IntValue implements Value{
    int val;


    public IntValue(int v) {
        val = v;
    }

    public IntValue() {
        val = 0;
    }

    public int getVal() {
        return val;
    }

    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public Type getType() {
        return new IntType();
    }


    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntValue))
            return false;
        IntValue t = (IntValue) o;
        return t.val == val;
    }
}
