package Model.stmt;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IStack;
import Model.state.PrgState;
import Model.types.*;
import Model.values.BoolValue;
import Model.values.IntValue;
import Model.values.StringValue;
import Model.values.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }

    @Override
    public PrgState execute(PrgState state) throws AdtException, ExprException, StmtException {
        IStack<IStmt> stk = state.getExeStack();
        IDictionary<String, Value> table = state.getSymTable();

        if (!table.is_defined(name)) {
            Value defaultValue = typ.defaultValue();
            table.add_pair(name, defaultValue);

//            if (typ.equals(new IntType())){
//                table.add_pair(name, new IntType().defaultValue());
//            }
//            else if (typ.equals(new BoolType())) {
//                table.add_pair(name, new BoolType().defaultValue());
//            }
//            else if (typ.equals(new StringType())) {
//                table.add_pair(name, new StringType().defaultValue());
//            }
//            else if (typ instanceof RefType) {
//                table.add_pair(name, new RefType().defaultValue());
//            }
        } else {
                throw new StmtException("Type is not existing");
        }

        state.setSymTable(table);
        state.setExeStack(stk);
        //return state;
        return null;
    }

    @Override
    public String toString() {
        return typ + " " + name;
    }

    public IDictionary<String,Type> typecheck(IDictionary<String,Type> typeEnv) throws StmtException {
        try{
            typeEnv.add_pair(name, typ);
            return typeEnv;
        }
        catch (AdtException e){
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(new String(name), typ.deepCopy());
    }
}
