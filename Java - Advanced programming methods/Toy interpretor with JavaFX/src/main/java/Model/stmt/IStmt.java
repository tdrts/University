package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.state.PrgState;
import Model.types.Type;

public interface IStmt {
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException;
    IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException;

    IStmt deepCopy();
}
