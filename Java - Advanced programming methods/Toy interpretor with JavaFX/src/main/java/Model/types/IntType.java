package Model.types;

import Model.values.IntValue;
import Model.values.Value;

public class IntType implements Type {
    public boolean equals(Object another) {
        if (another instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    public String toString() {
        return "int";
    }
}
