package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.expr.Exp;
import Model.state.PrgState;
import Model.types.IntType;
import Model.types.RefType;
import Model.types.StringType;
import Model.types.Type;
import Model.values.IntValue;
import Model.values.StringValue;
import Model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    Exp expr;
    String var_name;

    public ReadFile(Exp expr, String var_name) {
        this.expr = expr;
        this.var_name = var_name;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException{
        try{
            Type expressionType = expr.typecheck(typeEnv);
            Type variableType = typeEnv.lookup(var_name);
            if (expressionType.equals(new StringType())) {
                if (variableType.equals(new IntType())) {
                    return typeEnv;
                } else {
                    throw new StmtException("Variable not of type int");
                }
            } else {
                throw new StmtException("Expression not of type string");
            }
        }
        catch (ExprException e){
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IDictionary<String, Value> sym = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Value> heap = state.getHeap();

        if (!sym.is_defined(var_name)){
            throw new StmtException("READ: Variable already exists");
        } else {
            Value v = sym.lookup(var_name);
            if (v.getType().equals(new IntType())){
                Value val = expr.eval(sym,heap);
                if (val.getType().equals(new StringType())){
                    StringValue str = (StringValue) val;
                    if (fileTable.is_defined(str)) {
                        BufferedReader br = fileTable.lookup(str);
                        try {
                            String read = br.readLine();
                            IntValue intv;
                            if (read == null)
                                intv = new IntValue();
                            else
                                intv = new IntValue(Integer.parseInt(read));
                            sym.update_pair(var_name, intv);
                        } catch (IOException e) {
                            throw new StmtException("READ: Error in opening file!");
                        }
                    }
                } else {
                    throw new StmtException("READ: Not a string");
                }
            } else {
                throw new StmtException("READ: Not an int");
            }
        }
        //return state;
        return null;
    }

    @Override
    public String toString() {
        return "Readfile("+ expr.toString()+","+var_name.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(expr.deepCopy(), new String(var_name));
    }
}
