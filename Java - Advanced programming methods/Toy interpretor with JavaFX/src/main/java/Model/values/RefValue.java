package Model.values;

import Model.types.RefType;
import Model.types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(this.address,this.locationType);
    }

    @Override
    public String toString() {
        return "(" + address + "," + locationType + ")";
    }

}
