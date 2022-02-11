package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.expr.Exp;
import Model.state.PrgState;
import Model.types.StringType;
import Model.types.Type;
import Model.values.StringValue;
import Model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    Exp expr;

    public CloseRFile(Exp expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IDictionary<String, Value> sym = state.getSymTable();
        IHeap<Value> heap = state.getHeap();

        Value value = expr.eval(sym, heap);
        if (value.getType().equals(new StringType())) {
            StringValue str = (StringValue) value;
            if (fileTable.is_defined(str)) {
                BufferedReader br = fileTable.lookup(str);
                try {
                    br.close();
                    fileTable.remove(str);
                } catch (IOException ioe) {
                    throw new StmtException("CLOSE: cannot close file!");
                }
            } else throw new StmtException("CLOSE: File doesn't exist");
        }
        else {
            throw new StmtException("CLOSE: Should be String");
        }
        //return state;
        return null;
    }

    @Override
    public String toString() {
        return "CloseRFile(" + expr.toString() + ")";
    }


    public IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException {
        try{
            Type type = expr.typecheck(typeEnv);
            if (type.equals(new StringType())) {
                return typeEnv;
            } else {
                throw new StmtException("Expression not of type string");
            }
        }
        catch (ExprException e){
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(expr.deepCopy());
    }
}
