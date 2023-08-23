package Link;

import java.sql.SQLOutput;
import java.util.Stack;

public class index {
    public static void main(String[] args) {
        long a = 10000;
        a=a*100000000000L;
        while(true){
            //取余数
            int yu=(int)(a%10);
            System.out.println(yu);
            a = a/10;
            if(a==0){
                break;
            }

        }
    }

    public static void countNodeLength(NodeList nodeList) {
        Node<?> header = nodeList.getNode();
        int length = 0;
        while (header.next != null) {
            length++;
            header = header.next;
        }
        System.out.println(length);
    }

    public static Node getEndIndex(NodeList nodeList, int index) {
        //先遍历获取长度
        int nodeLength = getNodeLength(nodeList);
        if (index > nodeLength) {
            throw new RuntimeException("长度超出链表长度");
        }
        Node<?> header = nodeList.getNode();
        index = nodeLength - index + 1;
        while (header.next != null) {
            header = header.next;
            index--;
            if (index == 0) {
                break;
            }
        }
        return header;

    }

    private static int getNodeLength(NodeList nodeList) {
        Node<?> header = nodeList.getNode();
        int length = 0;
        while (header.next != null) {
            length++;
            header = header.next;
        }
        return length;
    }

    private static void reverseNode(NodeList nodeList) {
        Node<?> header = nodeList.getNode();
        if (header.next == null || header.next.next == null) {
            return;
        }
        //当前的节点
        Node<?> cur = header.next;
        //下一个节点
        Node<?> next = null;
        //反转接受
        Node<?> reverse = new Node<>(null, -999);
        while (cur != null) {
            next = cur.next;
            cur.next = reverse.next;
            reverse.next = cur;
            cur = next;
        }
        header.next = reverse.next;
    }
}
