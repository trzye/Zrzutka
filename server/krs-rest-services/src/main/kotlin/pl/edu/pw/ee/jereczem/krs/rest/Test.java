package pl.edu.pw.ee.jereczem.krs.rest;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by trzye on 15.12.2016.
 */
public class Test {
    public static void main(){
        ArrayList<Integer> integers = new ArrayList<>();

        integers.sort(new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        integers.sort((o1,o2) -> o1.compareTo(o2));
    }
}
