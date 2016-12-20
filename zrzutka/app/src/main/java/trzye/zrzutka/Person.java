package trzye.zrzutka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;

/**
 * Created by trzye on 14.12.2016.
 */

final public class Person extends Observable implements Serializable{
    final private String name;
    private Integer age;

    final public String getName(){
        return name;
    }

    final public void setAge(Integer age){
        if(age >= 0) {
            this.age = age;
            notifyObservers();
        }
    }
    final public Integer getAge(){
        return age;
    }

    public Person(String name, Integer age){
        this.name = name;
        this.age = age;
        System.out.println("I'm " + name);
    }

    public Person(Person other){
        this(other.name, other.age);
        System.out.println("I'm copy of" + other.name);
    }

    @Override
    final public String toString() {
        return "name: " + name + " age: " + age;
    }

    public static void main(){
        ArrayList<String> elements = new ArrayList<>();
        IntegerComparator
    }

    class IntegerComparator implements Comparator<Integer>{

        @Override
        public int compare(Integer e1, Integer e2) {
            return e1.compareTo(e2);
        }
    }
}
