package Test;
import Exceptions.AdtException;
import Model.adt.*;

public class TestAdt {
    AdtStack<Integer> st;
    AdtStack<Boolean> st2;

    AdtDictionary<Integer, Integer> dic;

    public TestAdt() {
        this.st = new AdtStack<>();
        this.st2 = new AdtStack<>();
        this.dic = new AdtDictionary<>();
    }

    public void testDict(){
        try{
            dic.add_pair(1,2);
            dic.add_pair(2,1);
        } catch (AdtException e){
            System.out.println(e.getMessage());
        }
        System.out.println(dic.toString());

        try{
            dic.remove(1);
            dic.update_pair(2,3);
            dic.remove(1);
            dic.remove(2);
            dic.remove(3);
        } catch (AdtException e){
            System.out.println(e.getMessage());
        }
        System.out.println(dic.toString());

        try{
            dic.remove(3);
        } catch (AdtException e){
            System.out.println(e.getMessage());
        }

    }

    public void testStack(){
        st2.push(true);
        st2.push(true);
        st2.push(false);
        //System.out.println(st2.getAll());

        try {
            System.out.println(st2.pop());
            System.out.println(st2.pop());
            System.out.println("Empty?" + st2.isEmpty());
            System.out.println(st2.pop());
            System.out.println("Empty?" +st2.isEmpty());
            System.out.println(st2.pop());
        }
        catch (AdtException e){
            System.out.println(e.getMessage());
        }



    }
}
