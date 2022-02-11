package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.*;
import Model.state.PrgState;
import Model.types.BoolType;
import Model.types.Type;
import Model.values.StringValue;
import Model.values.Value;

import java.io.BufferedReader;

public class ForkStmt implements IStmt{

    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "fork() { " + this.stmt.toString() + " }";
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IStack<IStmt> exeStack = new AdtStack<>();

        IDictionary<String, Value> symTable = state.getSymTable().createCopy();

        IList<Value> out = state.getOut();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Value> heap = state.getHeap();

        return new PrgState(exeStack, symTable, out,this.stmt, fileTable, heap);
    }

    public IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException{
        stmt.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }
}
