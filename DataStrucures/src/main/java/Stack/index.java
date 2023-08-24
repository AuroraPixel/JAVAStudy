package Stack;

import com.sun.xml.internal.ws.encoding.MtomCodec;

public class index {
    public static void main(String[] args) {
        QueueStack<String> queueStack = new QueueStack<>();
        queueStack.push("a");
        queueStack.push("b");
        System.out.println(queueStack.pop());
        System.out.println(queueStack.empty());

    }
}
