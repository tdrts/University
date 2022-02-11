package com.a7.a7;

import Controller.Controller;
import Exceptions.StmtException;
import Model.adt.*;
import Model.expr.*;
import Model.state.PrgState;
import Model.stmt.*;
import Model.types.*;
import Model.values.BoolValue;
import Model.values.IntValue;
import Model.values.StringValue;
import Model.values.Value;
import Repo.IRepo;
import Repo.Repo;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class GUIController {

    @FXML
    private Button buttonOneStep;

    @FXML
    private Text textPrgStates;

    @FXML
    private ListView<String> listOut;

    @FXML
    private ListView<String> listFiles;

    @FXML
    private ListView<String> listPrgId;

    @FXML
    private ListView<String> listStack;

    @FXML
    private TableView<Pair<Integer, Value>> tableHeap;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> heapAddress;

    @FXML
    private TableColumn<Pair<Integer, Value>, Value> heapValue;

    @FXML
    private TableView<Pair<String, Value>> listSymTable;

    @FXML
    private TableColumn<Pair<String, Value>, String> symTblName;

    @FXML
    private TableColumn<Pair<String, Value>, Value> symTblValue;

    private Controller ctrl;

    List<PrgState> programStateList;

    int currentState = 1;

    @FXML
    private void initialize(){
        heapAddress.setCellValueFactory(new PairKeyFactory<>());
        heapValue.setCellValueFactory(new PairValueFactory<>());
        symTblName.setCellValueFactory(new PairKeyFactory<>());
        symTblValue.setCellValueFactory(new PairValueFactory<>());
        listPrgId.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            try {
                currentState = Integer.parseInt(t1);
            } catch (Exception ignored) {
                return;
            }
            if (currentState == 0) {
                return;
            }
            populateSymbolTable();
            populateExecutionList();
        });
    }

    @FXML
    public void onRunOneStepPressed(ActionEvent actionEvent) {
        oneStep();

        setAllFields();
    }

    private void setAllFields() {
        setNumberOfStates();
        populateHeapTable();
        populateSymbolTable();
        populateProgramList();
        populateFileList();
        populateOutputList();
        populateExecutionList();
    }

    private void populateHeapTable() {
        List<Pair<Integer, Value>> list = new LinkedList<>();
        ctrl.getHeapTable().getContent().forEach((key, value) -> list.add(new Pair<>(key, value)));
        tableHeap.setItems(FXCollections.observableList(list));
    }

    private void populateFileList() {
        List<String> list = new LinkedList<>();
        ctrl.getFileTable().getContent().forEach((key, value) -> list.add(key.getVal()));
        listFiles.setItems(FXCollections.observableList(list));
    }

    private void populateOutputList() {
        List<String> list = ctrl
                .getOutput()
                .getAll()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        listOut.setItems(FXCollections.observableList(list));
    }

    private void populateSymbolTable() {
        List<Pair<String, Value>> list = new LinkedList<>();
        ctrl.getSymbolTable(currentState)
                .getContent()
                .forEach((key, value) -> list.add(new Pair<>(key, value)));
        listSymTable.setItems(FXCollections.observableList(list));
    }

    private void populateProgramList() {
        List<String> list = new LinkedList<>();
        ctrl.getPrgList().forEach((el) -> list.add(el.getId().toString()));
        listPrgId.setItems(FXCollections.observableList(list));
    }

    private void populateExecutionList() {
        List<String> list = ctrl
                .getExecutionStack(currentState)
                .getContent()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.toList());
        Collections.reverse(list);
        listStack.setItems(FXCollections.observableList(list));
    }

    private void setNumberOfStates() {
        int numberOfStates = ctrl.getPrgList().size();
        if (numberOfStates == 0) {
            textPrgStates.setText("There are currently no program states");
        } else if (numberOfStates == 1) {
            textPrgStates.setText("There is currently 1 program state");
        } else {
            textPrgStates.setText("There are currently " + numberOfStates + " program states");
        }
    }

    private void oneStep() {
        if(ctrl.getPrgList().size()==0){
            raiseAlert("Finished");
            return;
        }

        try {
            ctrl.garbageCollectAllPrograms(programStateList);
            ctrl.oneStepForAllPrg(programStateList);
            programStateList = ctrl.getPrgList();
            if (programStateList.size() == 0) {

                ctrl.stopExecutor();
                //ctrl.setFinalStateList(programStateList);
            }
        } catch (InterruptedException e) {
            raiseAlert("Interpreter: " + e.getMessage());
        } catch (Exception e) {
            raiseAlert(e.getMessage());
        }
    }

    private void raiseAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }



    public void setProgram(IStmt statement, int index) {
        String file = "Log" + index + ".txt";
        IStack<IStmt> stack = new AdtStack<>();
        //stack.push(new NopStatement());
        PrgState state = new PrgState(stack, new AdtDictionary<String, Value>(),  new AdtList<Value>(), statement, new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        IRepo repository = new Repo(state, file);
        repository.addState(state);
        ctrl = new Controller(repository);
        ctrl.startExecutor();
        programStateList = ctrl.getPrgList();
    }

}

class PairKeyFactory<T, E> implements Callback<TableColumn.CellDataFeatures<Pair<T, E>, T>, ObservableValue<T>> {
    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<Pair<T, E>, T> data) {
        return new ReadOnlyObjectWrapper<>(data.getValue().getKey());
    }
}

class PairValueFactory<T, E> implements Callback<TableColumn.CellDataFeatures<Pair<T, E>, E>, ObservableValue<E>> {
    @Override
    public ObservableValue<E> call(TableColumn.CellDataFeatures<Pair<T, E>, E> data) {
        E value = data.getValue().getValue();
        if (value instanceof ObservableValue) {
            return (ObservableValue) value;
        } else {
            return new ReadOnlyObjectWrapper<>(value);
        }
    }
}