package ArrayList;

import java.util.Iterator;

public class index {
    public static void main(String[] args) {
        MyArrayList<String> stringMyArrayList = new MyArrayList();
        for (int i = 0; i < 20; i++) {
            stringMyArrayList.add(i+"");
        }
        Iterator<String> iterator = stringMyArrayList.getIterator();
        while (iterator.hasNext()){
            stringMyArrayList.add(2+"");
            System.out.println(iterator.next());
        }
    }
}