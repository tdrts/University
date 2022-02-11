package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.expr.Exp;
import Model.state.PrgState;
import Model.types.StringType;
import Model.types.Type;
import Model.values.StringValue;
import Model.values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt{
    Exp expr;

    public OpenRFile(Exp e) {
        this.expr = e;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException{
        try{
            Type typexp = expr.typecheck(typeEnv);
            if (typexp.equals(new StringType()))
                return typeEnv;
            else
                throw new StmtException("should be evaluated as string");
        }
        catch (ExprException e){
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        //IStack<IStmt> stk = state.getExeStack();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IDictionary<String, Value> sym = state.getSymTable();
        IHeap<Value> heap = state.getHeap();

        Value val = expr.eval(sym,heap);
        if (val.getType().equals(new StringType())){
            StringValue str = (StringValue) val;
            if (fileTable.is_defined(str)){
                throw new StmtException("OPEN: File already defined");
            } else {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(str.getVal()));
                    fileTable.update_pair(str, br);
                } catch (IOException e) {
                    throw new StmtException("OPEN: Error in opening file!");
                }
            }
        } else {
            throw new StmtException("OPEN: Type not string");
        }
        //return state;
        return null;
    }

    @Override
    public String toString() {
        return "OpenRFile("+expr.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(expr.deepCopy());
    }
}
