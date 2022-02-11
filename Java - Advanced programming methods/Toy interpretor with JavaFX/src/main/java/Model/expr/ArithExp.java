package Model.expr;

import Exceptions.ExprException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.IntType;
import Model.types.Type;
import Model.values.IntValue;
import Model.values.Value;

public class ArithExp implements Exp{
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(Exp deepCopy, Exp deepCopy1, char op) {
        this.e1 = deepCopy;
        this.e2 = deepCopy1;
        if(op == '+')
            this.op = 1;
        if(op == '-')
            this.op = 2;
        if(op == '*')
            this.op = 3;
        if(op == '/')
            this.op = 4;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);

        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl,heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1 = i1.getVal();
                int n2 = i2.getVal();
                switch (op) {
                    case 1:
                        return new IntValue(n1 + n2);
                    case 2:
                        return new IntValue(n1 - n2);
                    case 3:
                        return new IntValue(n1 * n2);
                    case 4:
                        if (n2 == 0) {
                            throw new ExprException("Division by zero");
                        }
                        else {
                            return new IntValue(n1 / n2);
                        }
                    default:
                        throw new ExprException("Not existing operation");
                }
            }
            else {
                throw new ExprException("Second operand is not an integer");
            }
        }
        else {
            throw new ExprException("First operand is not an integer");
        }

    }

    @Override
    public String toString() {
        switch (op) {
            case 1:
                return e1.toString() + "+" + e2.toString();
            case 2:
                return e1.toString() + "-" + e2.toString();
            case 3:
                return e1.toString() + "*" + e2.toString();
            case 4:
                return e1.toString() + '/' + e2.toString();
            default:
                return "";
        }
    }

    @Override
    public Exp deepCopy() {
        switch (op) {
            case 1:
                return new ArithExp(e1.deepCopy(), e2.deepCopy(), '+');
            case 2:
                return new ArithExp(e1.deepCopy(), e2.deepCopy(), '-');
            case 3:
                return new ArithExp(e1.deepCopy(), e2.deepCopy(), '*');
            case 4:
                return new ArithExp(e1.deepCopy(), e2.deepCopy(), '/');
            default:
                return new ArithExp(e1.deepCopy(), e2.deepCopy(), '+');
        }
    }

    public Type typecheck(IDictionary<String,Type> typeEnv) throws ExprException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new ExprException("second operand is not an integer");
        } else
            throw new ExprException("first operand is not an integer");
    }
}
