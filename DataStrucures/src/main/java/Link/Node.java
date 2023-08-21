package Link;

public class Node<T> {
    private T item;
    public Node<?> next;

    public int index=0;

    public Node(T item,int index){
        this.item=item;
        this.index=index;
    }

    public T getItem(){
        return item;
    }

    public void setItem(Object item){
        this.item= (T) item;
    }
}
