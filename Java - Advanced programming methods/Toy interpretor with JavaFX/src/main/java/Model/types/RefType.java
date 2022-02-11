package Model.types;

import Model.values.RefValue;
import Model.values.Value;

import java.util.Objects;

public class RefType implements Type {
    Type inner;

    public RefType() {
        inner = new IntType();
    }

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    public boolean equals(Object another) {

        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
            //return inner.equals(another.getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }
}
