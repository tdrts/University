package Model.expr;

import Exceptions.ExprException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.Type;
import Model.values.BoolValue;
import Model.values.IntValue;
import Model.values.Value;

public class RelationalExp implements Exp{
    Exp e1;
    Exp e2;
    String sign;

    public RelationalExp(Exp e1, Exp e2, String s) {
        this.e1 = e1;
        this.e2 = e2;
        this.sign = s;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value eval1 = e1.eval(tbl, heap);
        Value eval2 = e2.eval(tbl, heap);
        if (eval1.getType().equals(new IntType())){
            if (eval2.getType().equals(new IntType())){
                IntValue int1 = (IntValue) eval1;
                int n1 = int1.getVal();

                IntValue int2 = (IntValue) eval2;
                int n2 = int2.getVal();

                if (sign.equals("<")) return new BoolValue(n1 < n2);
                if (sign.equals("<=")) return new BoolValue(n1 <= n2);
                if (sign.equals("==")) return new BoolValue(n1 == n2);
                if (sign.equals("!=")) return new BoolValue(n1 != n2);
                if (sign.equals(">")) return new BoolValue(n1 > n2);
                if (sign.equals(">=")) return new BoolValue(n1 >= n2);
                else
                    throw new ExprException("sign does not exist!");

            } else {
                throw new ExprException("Second sign not integer");
            }
        } else {
            throw new ExprException("First sign not integer");
        }
    }

    public Type typecheck(IDictionary<String,Type> typeEnv) throws ExprException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new ExprException("second operand is not int");
        } else
            throw new ExprException("first operand is not int");
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(e1.deepCopy(), e2.deepCopy(), new String(sign));
    }

    @Override
    public String toString() {
        return e1.toString() + " " + sign + " " + e2.toString();
    }
}
