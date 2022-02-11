package Model.expr;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.Type;
import Model.values.Value;

public interface Exp {
    Value eval(IDictionary<String,Value> tbl, IHeap<Value> heap) throws ExprException;
    Type typecheck(IDictionary<String, Type> typeEnv) throws ExprException;

    Exp deepCopy();
}
