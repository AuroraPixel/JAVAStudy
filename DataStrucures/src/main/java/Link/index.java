package Link;

public class index {
    public static void main(String[] args) {
        NodeList nodeList = new NodeList();
        Node<String> node = new Node<>(1+"",1);
        nodeList.add(node);
        Node<String> node1 = new Node<>(2+"",2);
        nodeList.add(node1);
        Node<String> node2 = new Node<>(5+"",5);
        nodeList.add(node2);
        Node<String> node3 = new Node<>(4+"",4);
        nodeList.add(node3);
        Node<String> node4 = new Node<>(7+"",7);
        nodeList.add(node4);
        Node<String> node5 = new Node<>(110+"",2);
        nodeList.update(node5);
        nodeList.remove(7);
        System.out.println("-------------------");
        countNodeLength(nodeList);
        System.out.println("-------------------");
        nodeList.showList();
        System.out.println("-------------------");
        System.out.println(getEndIndex(nodeList, 2).getItem());
        System.out.println("-------------------");
        reverseNode(nodeList);
        nodeList.showList();

    }

    public static void countNodeLength(NodeList nodeList){
        Node<?> header = nodeList.getNode();
        int length = 0;
        while (header.next != null) {
            length++;
            header = header.next;
        }
        System.out.println(length);
    }

    public static Node getEndIndex(NodeList nodeList,int index){
        //先遍历获取长度
        int nodeLength = getNodeLength(nodeList);
        if(index>nodeLength){
            throw new RuntimeException("长度超出链表长度");
        }
        Node<?> header = nodeList.getNode();
        index = nodeLength-index+1;
        while (header.next!=null){
            header= header.next;
            index--;
            if(index==0){
                break;
            }
        }
        return header;

    }

    private static int getNodeLength(NodeList nodeList) {
        Node<?> header = nodeList.getNode();
        int length = 0;
        while(header.next!=null){
            length++;
            header = header.next;
        }
        return length;
    }

    private static void reverseNode(NodeList nodeList){
        Node<?> header = nodeList.getNode();
        if(header.next==null||header.next.next==null){
            return;
        }
        Node<?> cur = header.next;
        Node<?> next = null;
        Node<?> newHeader = new Node<>(null,-999);
        while (cur!=null){
            next=cur.next;
            cur.next=newHeader.next;
            cur=next;
        }
        header.next=newHeader.next;
        nodeList.setNode(header);
    }
}
