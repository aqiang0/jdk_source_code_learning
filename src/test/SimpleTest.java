package test;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author aqian
 * @description
 * @date 2023/4/7 16:22:01
 */
public class SimpleTest {
    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        System.out.println(list);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("a","aaa");
        System.out.println(map);
    }
}
