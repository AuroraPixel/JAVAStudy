package Link;

import java.util.ArrayList;
import java.util.List;

public class Josephu {
    static class Node {
        private int val;
        private Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    private static Node getNode() {
        Node newNode = new Node(-1);
        Node currency = newNode;
        for (int i = 1; i <= 6; i++) {
            currency.next = new Node(i);
            currency = currency.next;
        }
        currency.next = newNode.next;
        return newNode.next;
    }

    public static void main(String[] args) {
        Node node = getNode();
        Object[] josephu = getJosephu(node, 2);
        for (Object o : josephu) {
            System.out.println(o);
        }
    }

    public static Object[] getJosephu(Node node, int i) {
        List<Integer> result = new ArrayList<>();
        //获取尾节点
        Node paper = node;
        while (paper.next != node) {
            paper = paper.next;
        }
        //当前的节点
        Node local = node;
        int a = 1;
        while (true) {
            if (a == i) {
                result.add(local.val);
                //paper后移a-1
                for (int j = 0; j < a - 1; j++) {
                    paper = paper.next;
                }
                a = 0;
                paper.next = local.next;
            }
            local = local.next;
            a++;
            if (paper.next == paper) {
                result.add(paper.val);
                break;
            }
        }
        return result.toArray();
    }
}
