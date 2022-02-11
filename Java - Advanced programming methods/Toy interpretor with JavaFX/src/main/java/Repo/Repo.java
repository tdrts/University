package Repo;

import Exceptions.RepoException;
import Model.state.PrgState;
import Model.stmt.IStmt;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Repo implements IRepo{
    private List<PrgState> states;
    private String logFilePath;

    public Repo(PrgState prgState, String logFilePath){
        states = new LinkedList<>();
        this.logFilePath = logFilePath;
    }

    //private IStmt originalProgram;

    public Repo(PrgState prgState){
        //originalProgram = prgState.getOriginalProgram();
        states = new LinkedList<>();
    }

//    @Override
//    public PrgState getCrtPrg() {
//        PrgState state = states.get(0);
//        states.remove(0);
//        return state;
//    }

    @Override
    public void printPrgState(PrgState prgState) {
        System.out.println(prgState);
    }

    @Override
    public void addState(PrgState state) {
        states.add(state);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws RepoException {
        File myFile = new File(this.logFilePath);
        try {
            PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(myFile, true)));
            logFile.write(state.toString());
            logFile.close();
        } catch (IOException e) {
            throw new RepoException(e.getMessage());
            //e.printStackTrace();
        }
    }

    @Override
    public void setPrgList(List<PrgState> new_states) {
        this.states = new_states;
    }

    @Override
    public List<PrgState> getPrgList() {
        return states;
    }

//    @Override
//    public IStmt getOriginalProgram() {
//        return originalProgram;
//    }
}
