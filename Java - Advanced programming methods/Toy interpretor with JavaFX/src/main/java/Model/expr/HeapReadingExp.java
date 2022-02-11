package Model.expr;

import Exceptions.ExprException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.RefType;
import Model.types.Type;
import Model.values.RefValue;
import Model.values.Value;

public class HeapReadingExp implements Exp{
    Exp e;

    public HeapReadingExp(Exp e) {
        this.e = e;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value val = e.eval(tbl, heap);
        if (!(val instanceof RefValue)) {
            throw new ExprException("Expression couldn't be evaluated to a RefValue");
        } else {
            RefValue v = (RefValue) val;
            if (heap.is_defined(v.getAddr())) {
                return heap.get(v.getAddr());
            } else {
                throw new ExprException("Address not existing in heap");
            }
        }
    }

    @Override
    public String toString() {
        return "rH(" + e + ")";
    }

    @Override
    public Exp deepCopy() {
        return new HeapReadingExp(e.deepCopy());
    }

    public Type typecheck(IDictionary<String,Type> typeEnv) throws ExprException{
        Type typ=e.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        }
        else
            throw new ExprException("the rH argument is not a Ref Type");
    }
}
