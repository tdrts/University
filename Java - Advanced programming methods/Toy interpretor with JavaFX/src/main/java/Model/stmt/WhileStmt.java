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
import Model.values.Value;

public class WhileStmt implements IStmt{
    Exp e;
    IStmt stmt;

    public WhileStmt(Exp e, IStmt st) {
        this.e = e;
        this.stmt = st;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException{
        try{
            Type typexp = e.typecheck(typeEnv);
            if (typexp.equals(new BoolType())) {
                stmt.typecheck(typeEnv.createCopy());
                return typeEnv;
            } else
                throw new StmtException("The condition of while has not the type bool");
        }
        catch (ExprException e){
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IStack<IStmt> stk = state.getExeStack();
        IDictionary<String, Value> table = state.getSymTable();
        IHeap<Value> heap = state.getHeap();

        Value v = e.eval(table,heap);
        if (v.getType() instanceof BoolType){
            BoolValue boolVal = (BoolValue) v;
            if (boolVal.getVal()) {
                stk.push(this.deepCopy());
                stk.push(stmt);
            }

        } else {
            throw new StmtException("Value not boolean");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(e.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "(while (" + e + ") " + stmt + ")";
    }
}
