package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.state.PrgState;
import Model.types.Type;

public class NopStmt implements IStmt {
    public NopStmt(){

    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws StmtException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
