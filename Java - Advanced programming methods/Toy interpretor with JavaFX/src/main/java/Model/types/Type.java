package Model.types;

import Model.values.Value;

public interface Type {
    Type deepCopy();
    Value defaultValue();
}
