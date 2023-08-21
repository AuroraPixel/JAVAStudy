package Link;

public class NodeList {
    //初始化头节点
    private Node<?> header = new Node<>(null,-999);

    //添加子节点
    public void add(Node<?> node){
        Node<?> temp = header;
        while (true){
            if(temp.next==null){
                break;
            }
            if(temp.next.index>= node.index){
                break;
            }
            temp=temp.next;
        }
        Node<?> next = temp.next;
        temp.next=node;
        node.next=next;
    }

    public void update(Node<?> node){
        Node<?> temp = header;
        while (true){
            if(temp.next==null){
                throw new RuntimeException("该节点不存在!");
            }
            if(temp.next.index== node.index){
                break;
            }
            temp=temp.next;
        }
        temp.next.setItem(node.getItem());
    }

    public void showList(){
        Node<?> itemNode = header;
        while (itemNode.next!=null){
            itemNode=itemNode.next;
            System.out.println(itemNode.getItem().toString());
        }
    }

    public void remove(int index){
        Node<?> temp = header;
        while(true){
            if(temp.next==null){
                throw new RuntimeException("该节点不存在!");
            }
            if(temp.next.index==index){
                break;
            }
            temp=temp.next;
        }
        temp.next=temp.next.next;
    }

    public Node<?> getNode(){
        return this.header;
    }

    public void setNode(Node node){
        this.header=node;
    }

}

