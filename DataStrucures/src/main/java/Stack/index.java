package Stack;

import com.sun.xml.internal.ws.encoding.MtomCodec;

public class index {
    public static void main(String[] args) {
//        QueueStack<String> queueStack = new QueueStack<>();
//        queueStack.push("a");
//        queueStack.push("b");
//        System.out.println(queueStack.pop());
//        System.out.println(queueStack.empty());
//        System.out.println('1'-48);

        String expression = "7*2*2-5+1-5+3-4";
        Calculator calculator = new Calculator(expression);
        System.out.println(calculator.run());
    }
}
