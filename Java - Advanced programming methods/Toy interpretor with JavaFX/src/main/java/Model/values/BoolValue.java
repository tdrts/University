package Model.values;

import Model.types.BoolType;
import Model.types.Type;

public class BoolValue implements Value{
    Boolean val;

    public BoolValue(Boolean b){
        val = b;
    }

    public BoolValue(){
        val = false;
    }

    @Override
    public Type getType(){
        return new BoolType();
    }

    public Boolean getVal(){
        return val;
    }

    @Override
    public String toString() {
        return Boolean.toString(val);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoolValue))
            return false;
        BoolValue b = (BoolValue) o;
        return b.val == val;
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }
}
