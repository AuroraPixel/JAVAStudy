package Queue.QueueByArray;

import java.io.PrintStream;
import java.util.Arrays;

public class index {
    public static void main(String[] args) {
        int size = 5;
        CircleQueue queue = new CircleQueue(size);
        for (int i = 0; i < size; i++) {
            queue.add(i + "");
        }


        System.out.println(queue.getItemQueue());

        System.out.println("------------------");
        System.out.println(queue.next());
        System.out.println(queue.next());
        queue.add("5");
        queue.add("6");
        System.out.println("------------------");
        System.out.println(queue.getItemQueue());

        System.out.println("------------------");
        while (queue.hasNext()) {
            System.out.println(queue.next());
        }

    }
}
