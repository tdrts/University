package Model.expr;

import Exceptions.ExprException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.Type;
import Model.values.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value deepCopy) {
        e = deepCopy;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        return e;
    }

    public Type typecheck(IDictionary<String,Type> typeEnv) throws ExprException{
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }
}
