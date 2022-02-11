package View;

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

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) throws StmtException {
        IStack<IStmt> stack1 = new AdtStack<>();
        IStack<IStmt> stack2 = new AdtStack<>();
        IStack<IStmt> stack3 = new AdtStack<>();
        IStack<IStmt> stack4 = new AdtStack<>();
        IStack<IStmt> stack5 = new AdtStack<>();
        IStack<IStmt> stack6 = new AdtStack<>();
        IStack<IStmt> stack7 = new AdtStack<>();
        IStack<IStmt> stack8 = new AdtStack<>();
        IStack<IStmt> stack9 = new AdtStack<>();
        IStack<IStmt> stack10 = new AdtStack<>();

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


        example_1.typecheck(new AdtDictionary<String, Type>());
        PrgState prg1 = new PrgState(stack1, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_1,new AdtDictionary<StringValue, BufferedReader>(), new AdtHeap<Value>());
        //IRepo repo1 = new Repo(prg1, file1);
        IRepo repo1 = new Repo(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);
        repo1.addState(prg1);

        example_2.typecheck(new AdtDictionary<String, Type>());
        PrgState prg2 = new PrgState(stack2, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_2,new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        //IRepo repo2 = new Repo(prg2, file2);
        IRepo repo2 = new Repo(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);
        repo2.addState(prg2);

        example_3.typecheck(new AdtDictionary<String, Type>());
        PrgState prg3 = new PrgState(stack3, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_3,new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        //IRepo repo3 = new Repo(prg3,file3);
        IRepo repo3 = new Repo(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);
        repo3.addState(prg3);

        example_4.typecheck(new AdtDictionary<String, Type>());
        PrgState prg4 = new PrgState(stack4, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_4, new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        //IRepo repo4 = new Repo(prg4,file4);
        IRepo repo4 = new Repo(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);
        repo4.addState(prg4);

        example_5.typecheck(new AdtDictionary<String, Type>());
        PrgState prg5 = new PrgState(stack5, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_5, new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        IRepo repo5 = new Repo(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);
        repo5.addState(prg5);

        example_6.typecheck(new AdtDictionary<String, Type>());
        PrgState prg6 = new PrgState(stack6, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_6, new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        IRepo repo6 = new Repo(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);
        repo6.addState(prg6);

        example_7.typecheck(new AdtDictionary<String, Type>());
        PrgState prg7 = new PrgState(stack7, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_7, new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        IRepo repo7 = new Repo(prg7, "log7.txt");
        Controller ctr7 = new Controller(repo7);
        repo7.addState(prg7);

        example_8.typecheck(new AdtDictionary<String, Type>());
        PrgState prg8 = new PrgState(stack8, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_8, new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        IRepo repo8 = new Repo(prg8, "log8.txt");
        Controller ctr8 = new Controller(repo8);
        repo8.addState(prg8);

        example_9.typecheck(new AdtDictionary<String, Type>());
        PrgState prg9 = new PrgState(stack9, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_9, new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        IRepo repo9 = new Repo(prg9, "log9.txt");
        Controller ctr9 = new Controller(repo9);
        repo9.addState(prg9);

        example_10.typecheck(new AdtDictionary<String, Type>());
        PrgState prg10 = new PrgState(stack10, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_10, new AdtDictionary<StringValue, BufferedReader>(),new AdtHeap<Value>());
        IRepo repo10 = new Repo(prg10, "log10.txt");
        Controller ctr10 = new Controller(repo10);
        repo10.addState(prg10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",example_1.toString(),ctr1));
        menu.addCommand(new RunExample("2",example_2.toString(),ctr2));
        menu.addCommand(new RunExample("3",example_3.toString(),ctr3));
        menu.addCommand(new RunExample("4",example_4.toString(),ctr4));
        menu.addCommand(new RunExample("5",example_5.toString(),ctr5));
        menu.addCommand(new RunExample("6",example_6.toString(),ctr6));
        menu.addCommand(new RunExample("7",example_7.toString(),ctr7));
        menu.addCommand(new RunExample("8",example_8.toString(),ctr8));
        menu.addCommand(new RunExample("9",example_9.toString(),ctr9));
        menu.addCommand(new RunExample("10",example_10.toString(),ctr10));
        menu.show();
    }
}
