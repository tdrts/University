package Model.state;

import Exceptions.AdtException;
import Exceptions.ExprException;
import Exceptions.StmtException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.adt.IList;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Model.values.StringValue;
import Model.values.Value;

import java.io.BufferedReader;

public class PrgState {
    IStack<IStmt> exeStack;
    IDictionary<String, Value> symTable;
    IList<Value> out;
    IDictionary<StringValue, BufferedReader> fileTable;
    IHeap<Value> heap;
    private int id;
    private static int static_id = 0;
    IStmt originalProgram; //optional field, but good to have


    public Integer getId() {
        return id;
    }

    public PrgState(IStack<IStmt> stk, IDictionary<String,Value> symtbl, IList<Value> ot, IStmt prg, IDictionary<StringValue, BufferedReader> ft, IHeap<Value> h){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        originalProgram=prg.deepCopy(); //recreate the entire original prg
        fileTable = ft;
        heap = h;
        this.id = getNewPrgStateID();
        stk.push(prg);
    }

    public static synchronized int getNewPrgStateID() {
        ++static_id;
        return static_id;
    }


    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public PrgState(IStack<IStmt> stk, IDictionary<String,Value> symtbl, IList<Value> ot){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
    }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws AdtException, StmtException, ExprException {
        if (exeStack.isEmpty()) {
            throw new AdtException("Stack is empty");
        }
        IStmt currentStmt = exeStack.pop();
        return currentStmt.execute(this);
    }

    public IHeap<Value> getHeap() {
        return heap;
    }

    public void setHeap(IHeap<Value> heap) {
        this.heap = heap;
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public IDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(IDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public IList<Value> getOut() {
        return out;
    }

    public void setOut(IList<Value> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //sb.append("\tProgram\n");
        sb.append("\nID: ").append(id).append("\n");
        sb.append("Exe Stack\n");
        sb.append(this.getExeStack().toString());
        sb.append("Sym Table\n");
        sb.append(this.getSymTable().toString());
        sb.append("Heap\n");
        sb.append(this.getHeap().toString());
        sb.append("Out\n");
        sb.append(this.getOut().toString());
        sb.append("Files\n");
        sb.append(this.getFileTable().toString());
        sb.append("\n");

        return sb.toString();
    }
}
