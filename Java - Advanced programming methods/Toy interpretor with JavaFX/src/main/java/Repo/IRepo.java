package Repo;

import Exceptions.RepoException;
import Model.state.PrgState;
import Model.stmt.IStmt;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    public List<PrgState> getPrgList();

    //PrgState getCrtPrg();


    //IStmt getOriginalProgram();
    void printPrgState(PrgState prgState);

    void setPrgList(List<PrgState> new_states);

    void addState(PrgState state);

    void logPrgStateExec(PrgState state) throws RepoException;
}
