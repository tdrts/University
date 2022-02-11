package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IStack;
import Model.state.PrgState;
import Model.types.Type;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        state.setExeStack(stk);
        //return state;
        return null;
    }

    @Override
    public String toString() {
        return "(" + first + ";" + second + ")";
    }

    public IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException{
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
