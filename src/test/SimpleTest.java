package test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author aqian
 * @description
 * @date 2023/4/7 16:22:01
 */
public class SimpleTest {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        ArrayList<Object> objects = new ArrayList<>(list);
        System.out.println(objects);
    }
}
