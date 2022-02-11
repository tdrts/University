//package View;
//import Controller.Controller;
//import Exceptions.RepoException;
//import Exceptions.ServiceException;
//import Model.adt.*;
//import Model.expr.*;
//import Model.state.PrgState;
//import Model.stmt.*;
//import Model.types.BoolType;
//import Model.types.IntType;
//import Model.types.StringType;
//import Model.values.BoolValue;
//import Model.values.IntValue;
//import Model.values.StringValue;
//import Model.values.Value;
//import Repo.IRepo;
//import Repo.Repo;
//import Test.*;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Scanner;
//
//import java.io.BufferedReader;
//
//public class Main {
//
//    public static void main(String[] args) throws ServiceException, IOException, RepoException {
////        Scanner readFile = new Scanner(System.in);
////        System.out.print("File for Repo 1:");
////        String file1 = readFile.nextLine();
////
////        System.out.print("File for Repo 2:");
////        String file2 = readFile.nextLine();
////
////        System.out.print("File for Repo 3:");
////        String file3 = readFile.nextLine();
////
////        System.out.print("File for Repo 4:");
////        String file4 = readFile.nextLine();
//
//        IStack<IStmt> stack1 = new AdtStack<>();
//        IStack<IStmt> stack2 = new AdtStack<>();
//        IStack<IStmt> stack3 = new AdtStack<>();
//        IStack<IStmt> stack4 = new AdtStack<>();
//        IStack<IStmt> stack5 = new AdtStack<>();
//
//        IStmt example_1 = new CompStmt(new VarDeclStmt("v",new IntType()),
//                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
//
//
//        IStmt example_2 = new CompStmt(
//                new VarDeclStmt("a", new IntType()), new CompStmt(
//                new VarDeclStmt("b", new IntType()), new CompStmt(
//                new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),
//                        new ArithExp(
//                                new ValueExp(new IntValue(3)),
//                                new ValueExp(new IntValue(5)), '*'),
//                        '+')), new CompStmt(
//                new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), '+')),
//                new PrintStmt(new VarExp("b")))
//        )));
//
//        IStmt example_3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                        new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
//                                VarExp("v"))))));
//
//        IStmt example_4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
//                new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
//                        new CompStmt(new ReadFile(new VarExp("varf"),"varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
//                                new CompStmt(new ReadFile(new VarExp("varf"),"varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
//
//
//        IStmt example_5 = new CompStmt(new VarDeclStmt("nr2",new IntType()), new CompStmt(new VarDeclStmt("v",new IntType()),
//                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new IfStmt(new RelationalExp(new VarExp("v"), new VarExp("nr2"),"<"),
//                        new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("nr2"))))));
//
//        PrgState prg1 = new PrgState(stack1, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_1);
//        //IRepo repo1 = new Repo(prg1, file1);
//        IRepo repo1 = new Repo(prg1, "log1.txt");
//        Controller ctr1 = new Controller(repo1);
//        repo1.addState(prg1);
//
//        PrgState prg2 = new PrgState(stack2, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_2);
//        //IRepo repo2 = new Repo(prg2, file2);
//        IRepo repo2 = new Repo(prg2, "log2.txt");
//        Controller ctr2 = new Controller(repo2);
//        repo2.addState(prg2);
//
//        PrgState prg3 = new PrgState(stack3, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_3);
//        //IRepo repo3 = new Repo(prg3,file3);
//        IRepo repo3 = new Repo(prg3, "log3.txt");
//        Controller ctr3 = new Controller(repo3);
//        repo3.addState(prg3);
//
//        PrgState prg4 = new PrgState(stack4, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_4, new AdtDictionary<StringValue, BufferedReader>());
//        //IRepo repo4 = new Repo(prg4,file4);
//        IRepo repo4 = new Repo(prg4, "log4.txt");
//        Controller ctr4 = new Controller(repo4);
//        repo4.addState(prg4);
//
//        PrgState prg5 = new PrgState(stack5, new AdtDictionary<String, Value>(),  new AdtList<Value>(), example_5, new AdtDictionary<StringValue, BufferedReader>());
//        IRepo repo5 = new Repo(prg5, "log5.txt");
//        Controller ctr5 = new Controller(repo5);
//        repo5.addState(prg5);
//
//
//        while (true){
//
//            Scanner keyboard = new Scanner(System.in);
//            System.out.print("Chose between example 1 or 2 or 3 or 4 or 5 or 0 to close: ");
//            int myint = keyboard.nextInt();
//            switch (myint){
//                case(1):
//                    System.out.println("\t\t\t\t\t\t\t\tEXAMPLE 1");
//                    ctr1.allStep();
//                    break;
//                case(2):
//                    System.out.println("\t\t\t\t\t\t\t\tEXAMPLE 2");
//                    ctr2.allStep();
//                    break;
//                case(3):
//                    System.out.println("\t\t\t\t\t\t\t\tEXAMPLE 3");
//                    ctr3.allStep();
//                    break;
//                case(4):
//                    System.out.println("\t\t\t\t\t\t\t\tEXAMPLE 4");
//                    ctr4.allStep();
//                    break;
//                case(5):
//                    System.out.println("\t\t\t\t\t\t\t\tEXAMPLE 5");
//                    ctr5.allStep();
//                    break;
//                case(0):
//                    System.exit(0);
//
//            }
//        }
//    }
//}
