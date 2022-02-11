package Model.values;

import Model.types.Type;

public interface Value {
    Type getType();
    Value deepCopy();
    //Value equals();
}
