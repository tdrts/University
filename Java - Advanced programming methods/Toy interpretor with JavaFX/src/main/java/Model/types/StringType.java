package Model.types;

import Model.values.StringValue;
import Model.values.Value;

public class StringType implements Type{
    @Override
    public Type deepCopy() {
        return new StringType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
