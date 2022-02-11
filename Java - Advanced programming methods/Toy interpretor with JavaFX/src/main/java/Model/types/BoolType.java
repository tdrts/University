package Model.types;

import Model.values.BoolValue;
import Model.values.Value;

public class BoolType implements Type{
    public boolean equals(Object another) {
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }

    public String toString() {
        return "bool";
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
