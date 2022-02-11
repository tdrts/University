package Model.expr;

import Exceptions.ExprException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.Type;
import Model.values.BoolValue;
import Model.values.Value;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    int op;

    public LogicExp(Exp deepCopy, Exp deepCopy1, int op1) {
        e1 = deepCopy;
        e2 = deepCopy1;
        op = op1;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value v1, v2;
        
        v1 = e1.eval(tbl, heap);

        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new BoolType())) {

                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;

                boolean a = i1.getVal();
                boolean b = i2.getVal();

                if (op == 1) {
                    return new BoolValue(a && b);
                }
                else if (op == 2) {
                    return new BoolValue(a || b);
                }
            }
            else {
                throw new ExprException("Second operand is not a boolean");
            }
        }
        else {
            throw new ExprException("First operand is not a boolean");
        }

        return new BoolValue(false);
    }

    public Type typecheck(IDictionary<String,Type> typeEnv) throws ExprException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new ExprException("second operand is not bool");
        } else
            throw new ExprException("first operand is not bool");
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }
}
