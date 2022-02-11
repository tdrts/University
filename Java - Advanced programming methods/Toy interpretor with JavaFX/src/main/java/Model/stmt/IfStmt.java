package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.expr.Exp;
import Model.state.PrgState;
import Model.types.BoolType;
import Model.types.Type;
import Model.values.BoolValue;
import Model.values.IntValue;
import Model.values.Value;

public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IStack<IStmt> stk = state.getExeStack();
        IHeap<Value> heap = state.getHeap();

        Value condition = exp.eval(state.getSymTable(),heap);

        if (!condition.getType().equals(new BoolType())) {
            throw new StmtException("Condition not bool");
        }

        if (condition.equals(new BoolValue(true))) {
            stk.push(thenS.deepCopy());
        } else {
            stk.push(elseS.deepCopy());
        }
        state.setExeStack(stk);

        //return state;
        return null;
    }

    @Override
    public String toString() {
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString() +")ELSE("+elseS.toString()+"))";
    }

    public IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException{
        try{
            Type typexp = exp.typecheck(typeEnv);
            if (typexp.equals(new BoolType())) {
                thenS.typecheck(typeEnv.createCopy());
                elseS.typecheck(typeEnv.createCopy());
                return typeEnv;
            } else
                throw new StmtException("The condition of IF has not the type bool");
        }
        catch (ExprException e){
            throw new StmtException(e.getMessage());
        }
     }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(),elseS.deepCopy());
    }
}
