package Controller;

import Exceptions.*;
import Model.adt.*;
import Model.state.PrgState;
import Model.stmt.IStmt;
import Model.values.RefValue;
import Model.values.StringValue;
import Model.values.Value;
import Repo.IRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepo repo;
    ExecutorService executor;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    public void startExecutor() {
        executor = Executors.newFixedThreadPool(2);
    }

    public void stopExecutor() {
        executor.shutdownNow();
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList=removeCompletedPrg(repo.getPrgList());

        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepoException e) {
                e.printStackTrace();
            }
        });
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(
                        future -> {
                            try {
                                return future.get();
                            }
                            catch (ExecutionException | InterruptedException ex) {
                                return null;
                            }
                        }
                )
                .filter(p -> p!=null)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepoException e) {
                e.printStackTrace();
            }
        });
        repo.setPrgList(prgList);
    }

    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){
            garbageCollectAllPrograms(prgList);
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public IStack<IStmt> getExecutionStack(int process) {
        List<PrgState> states = repo
                .getPrgList()
                .stream()
                .filter((el) -> el.getId() == process)
                .collect(Collectors.toList());
        if (states.size() == 0) {
            return new AdtStack<>();
        } else {
            return states.get(0).getExeStack();
        }
    }

    public IDictionary<String, Value> getSymbolTable(int process) {
        List<PrgState> states = repo.getPrgList().stream().filter((el) -> el.getId() == process).collect(Collectors.toList());
        if (states.size() == 0) {
            return new AdtDictionary<>();
        } else {
            return states.get(0).getSymTable();
        }
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        if (repo.getPrgList().size() == 0) {
            return new AdtDictionary<>();
        }
        return repo.getPrgList().get(0).getFileTable();
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(elem->symTableAddr.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void garbageCollectAllPrograms(List<PrgState> prgList) {
        prgList.forEach(
                p -> {
                    p.getHeap().set(
                            (HashMap<Integer, Value>) this.safeGarbageCollector(
                                    this.getAddrFromSymTable(p.getSymTable().getContent().values()),
                                    this.getAddrFromHeap(p.getHeap().getContent().values()),
                                    p.getHeap().getContent()
                            )
                    );
                }
        );
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap
                .entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()) )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues
                .stream()
                .filter(v -> v instanceof RefValue)
                .map(v-> { RefValue v1 = (RefValue) v; return v1.getAddr(); })
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues
                .stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> { RefValue v1 = (RefValue) v; return v1.getAddr(); })
                .collect(Collectors.toList());
    }

    public List<PrgState> getPrgList() {
        return repo.getPrgList();
    }

    public IList<Value> getOutput() {
            if (repo.getPrgList().size() == 0) {
                return new AdtList<>();
            }
            return repo.getPrgList().get(0).getOut();
    }

    public IHeap<Value> getHeapTable() {
        if (repo.getPrgList().size() == 0) {
            return new AdtHeap<>();
        }
        return repo.getPrgList().get(0).getHeap();
    }

}
