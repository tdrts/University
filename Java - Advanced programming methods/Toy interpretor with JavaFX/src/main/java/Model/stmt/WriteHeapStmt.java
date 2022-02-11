package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.expr.Exp;
import Model.state.PrgState;
import Model.types.RefType;
import Model.types.Type;
import Model.values.RefValue;
import Model.values.Value;

public class WriteHeapStmt implements IStmt{
    String var_name;
    Exp e;

    public WriteHeapStmt(String var_name, Exp e) {
        this.var_name = var_name;
        this.e = e;
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IDictionary<String, Value> sym = state.getSymTable();
        IHeap<Value> heap = state.getHeap();

        if (!sym.is_defined(var_name)){
            throw new StmtException("Variable is not defined");
        } else {
            if (!(sym.lookup(var_name).getType() instanceof RefType)){
                throw new StmtException("Type is not RefType");
            } else {
                RefValue v = (RefValue) sym.lookup(var_name);
                if (heap.is_defined(v.getAddr())){
                    Value val = e.eval(sym, heap);
                    if (sym.lookup(var_name).getType().equals(new RefType(val.getType()))){
                        int adr = v.getAddr();
                        heap.update(adr, val);
                    } else {
                        throw new StmtException("Pointing variable has a a type different to the evaluated expression.");
                    }
                } else {
                    throw new StmtException("Address not in heap");
                }
            }
        }
        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException{
        try{
            Type typevar = typeEnv.lookup(var_name);
            Type typexp = e.typecheck(typeEnv);
            if (typevar.equals(new RefType(typexp)))
                return typeEnv;
            else
                throw new StmtException("write heap stmt: right hand side and left hand side have different types");
        }
        catch (ExprException e){
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "wH(" + var_name + "," + e + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(var_name, e.deepCopy());
    }
}
