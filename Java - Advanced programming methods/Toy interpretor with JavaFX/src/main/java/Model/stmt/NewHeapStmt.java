package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.expr.Exp;
import Model.state.PrgState;
import Model.types.RefType;
import Model.types.Type;
import Model.values.RefValue;
import Model.values.Value;

public class NewHeapStmt implements IStmt{
    String var_name;
    Exp e;

    public NewHeapStmt(String var_name, Exp e) {
        this.var_name = var_name;
        this.e = e;
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IDictionary<String, Value> sym = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        IStack<IStmt> stk = state.getExeStack();

        if (!sym.is_defined(var_name)){
            throw new StmtException("Variable not defined");
        } else {
            if (!(sym.lookup(var_name).getType() instanceof RefType)){
                throw new StmtException("Variable not of type Ref");
            } else{
                Value v = e.eval(sym, heap);
                Value table_v = sym.lookup(var_name);
//                System.out.println("v.gettype "+v.getType());
//                System.out.println("table_v.gettype "+((((RefType)(table_v.getType())).getInner())));

                if (v.getType().equals(((RefType)(table_v.getType())).getInner())){
                    int addr = heap.allocate(v);
                    sym.update_pair(var_name, new RefValue(addr, v.getType()));
                } else {
                    throw new StmtException("Value type not matching");
                }
            }
        }

        state.setExeStack(stk);
        state.setSymTable(sym);
        state.setHeap(heap);
        return null;
    }

    public IDictionary<String,Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException{
        try{
            Type typevar = typeEnv.lookup(var_name);
            Type typexp = e.typecheck(typeEnv);
            if (typevar.equals(new RefType(typexp)))
                return typeEnv;
            else
                throw new StmtException("NEW stmt: right hand side and left hand side have different types");
        }
        catch (ExprException e){
            throw new StmtException(e.getMessage());
        }
    }


    @Override
    public IStmt deepCopy() {
        return new NewHeapStmt(var_name, e.deepCopy());
    }

    @Override
    public String toString(){
        return "new(" + var_name + ", " + e + ")";
    }
}
