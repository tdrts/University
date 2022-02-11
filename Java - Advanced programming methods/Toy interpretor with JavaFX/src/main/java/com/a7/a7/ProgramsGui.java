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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ProgramsGui {
    @FXML
    private Button buttonExecute;

    @FXML
    private ListView<String> listPrograms;

    private List<IStmt> stmtList = new LinkedList<>();

    @FXML
    private void executePressed(ActionEvent actionEvent) throws IOException, StmtException {
        int index = listPrograms.getSelectionModel().getSelectedIndex();

        IStmt statement = stmtList.get(index);
        try{
            statement.typecheck(new AdtDictionary<String, Type>());
        } catch (StmtException e){
            raiseAlert(e.getMessage());
            return;
        }


        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("gui.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        GUIController home_controller = fxmlLoader.getController();
        home_controller.setProgram(statement, index);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Home");
        stage.show();
    }

    private void raiseAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    private void initialize(){
        try{
            listPrograms.setItems(this.populateListPrograms());
            listPrograms.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            listPrograms.getSelectionModel().selectIndices(0);
            listPrograms.getFocusModel().focus(0);
        }
        catch (StmtException e){
            System.out.println(e.getMessage());
        }
    }


    public ObservableList<String> populateListPrograms() throws StmtException {
        IStmt example_1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));


        IStmt example_2 = new CompStmt(
                new VarDeclStmt("a", new IntType()), new CompStmt(
                new VarDeclStmt("b", new IntType()), new CompStmt(
                new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),
                        new ArithExp(
                                new ValueExp(new IntValue(3)),
                                new ValueExp(new IntValue(5)), '*'),
                        '+')), new CompStmt(
                new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), '+')),
                new PrintStmt(new VarExp("b")))
        )));

        IStmt example_3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                VarExp("v"))))));

        IStmt example_4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                        new CompStmt(new ReadFile(new VarExp("varf"),"varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                new CompStmt(new ReadFile(new VarExp("varf"),"varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));


        IStmt example_5 = new CompStmt(new VarDeclStmt("nr2",new IntType()), new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new IfStmt(new RelationalExp(new VarExp("v"), new VarExp("nr2"),"<"),
                        new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("nr2"))))));

        IStmt example_6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewHeapStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));

        IStmt example_7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewHeapStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(new HeapReadingExp(new HeapReadingExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)),
                                                        '+')))))));

        IStmt example_8 = new CompStmt(
                new VarDeclStmt(
                        "v",
                        new IntType()
                ),
                new CompStmt(
                        new AssignStmt(
                                "v",
                                new ValueExp(
                                        new IntValue(4)
                                )
                        ),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExp(
                                                new VarExp("v"),
                                                new ValueExp(
                                                        new IntValue(0)
                                                ),
                                                ">"),
                                        new CompStmt(
                                                new PrintStmt(
                                                        new VarExp("v")
                                                ),
                                                new AssignStmt(
                                                        "v",
                                                        new ArithExp(
                                                                new VarExp("v"),
                                                                new ValueExp(
                                                                        new IntValue(1)
                                                                ),
                                                                '-'
                                                        )
                                                )
                                        )
                                ),
                                new PrintStmt(
                                        new VarExp("v")
                                )
                        )
                )
        );

        IStmt example_9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewHeapStmt("a", new VarExp("v")),
                                        new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new HeapReadingExp(new HeapReadingExp(new VarExp("a"))))))
                        )));

        IStmt example_10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewHeapStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new HeapReadingExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReadingExp(new VarExp("a")))))))));



        stmtList.add(example_1);
        stmtList.add(example_2);
        stmtList.add(example_3);
        stmtList.add(example_4);
        stmtList.add(example_5);
        stmtList.add(example_6);
        stmtList.add(example_7);
        stmtList.add(example_8);
        stmtList.add(example_9);
        stmtList.add(example_10);

        ObservableList<String> programs = FXCollections.observableArrayList(example_1.toString(),example_2.toString(),example_3.toString(),example_4.toString(),example_5.toString(),example_6.toString(),example_7.toString(),example_8.toString(),example_9.toString(),example_10.toString());
        return programs;
    }
}
