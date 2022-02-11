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

public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return id+"="+ exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IDictionary<String,Value> tbl = state.getSymTable();
        IHeap<Value> heap = state.getHeap();

        Value val = exp.eval(tbl,heap);
        if (tbl.is_defined(id)){
            Type typ = tbl.lookup(id).getType();
            if (val.getType().equals(typ)){
                tbl.update_pair(id, val);
            } else {
                throw new StmtException("Type mismatch");
            }
        } else {
            throw new StmtException("Variable not defined");
        }

        state.setSymTable(tbl);
        //return state;
        return null;
    }

    public IDictionary<String,Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException {
        try{
            Type typevar = typeEnv.lookup(id);
            Type typexp = exp.typecheck(typeEnv);
            if (typevar.equals(typexp))
                return typeEnv;
            else
                throw new StmtException("Assignment: right hand side and left hand side have different types ");
        }
        catch (ExprException ex) {
            throw new StmtException(ex.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(new String(id), exp.deepCopy());
    }
}
