package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.adt.IList;
import Model.adt.IStack;
import Model.expr.Exp;
import Model.state.PrgState;
import Model.types.Type;
import Model.values.Value;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IStack<IStmt> stk = state.getExeStack();
        IList<Value> out = state.getOut();
        IHeap<Value> heap = state.getHeap();

        out.add_element(exp.eval(state.getSymTable(),heap));
        //System.out.println("print teeest "+out.toString());
        state.setExeStack(stk);
        state.setOut(out);
        return null;
        //return state;
    }

    @Override
    public String toString() {
        return "print(" +exp.toString()+")";
    }

    public IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException{
        try{
            exp.typecheck(typeEnv);
            return typeEnv;
        }
        catch (ExprException e){
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
}
