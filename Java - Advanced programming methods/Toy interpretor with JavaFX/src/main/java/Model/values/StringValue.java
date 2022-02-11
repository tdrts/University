package Model.values;

import Model.types.StringType;
import Model.types.Type;

public class StringValue implements Value{
    private String s;

    public StringValue(String s) {
        this.s = s;
    }

    public StringValue() {
        this.s = "";
    }

    public String getVal() {
        return s;
    }

    @Override
    public String toString(){
        return s;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringValue))
            return false;
        StringValue t = (StringValue) o;
        return s.equals(t.s);
    }
}
