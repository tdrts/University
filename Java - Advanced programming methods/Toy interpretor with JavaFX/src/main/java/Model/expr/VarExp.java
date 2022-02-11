package Model.expr;

import Exceptions.ExprException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.Type;
import Model.values.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String s) {
        id = s;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        return tbl.lookup(id);
    }

    public Type typecheck(IDictionary<String,Type> typeEnv) throws ExprException{
        return typeEnv.lookup(id); }

    @Override
    public Exp deepCopy() {
        return new VarExp(new String(id));
    }

    @Override
    public String toString() {
        return id;
    }
}
