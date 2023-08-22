package Link;

public class DoublyLink {
    static class Node {
        private int val;
        private Node pre;
        private Node next;

        public Node(int val){
            this.val=val;
        }

        public Node(){

        }
    }

    static class NodeList{

        private Node header = new Node(-999);

        public  void add(Node node){
            Node itemHeader = header;
            while(true){
                if(itemHeader.next==null){
                    itemHeader.next=node;
                    node.pre=itemHeader;
                    break;
                }
                itemHeader = itemHeader.next;
            }
        }

        public Node getHeader(){
            return header;
        }
    }

    public static void main(String[] args) {
        NodeList nodeList = new NodeList();
        for (int i = 0; i < 5; i++) {
            Node node = new Node(i);
            nodeList.add(node);
        }
        System.out.println(nodeList.header);
    }
}
