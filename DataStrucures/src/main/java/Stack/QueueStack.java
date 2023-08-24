package Stack;

import java.util.LinkedList;
import java.util.Queue;

public class QueueStack<E> {
    private Queue<E> pre;

    private Queue<E> after;

    public QueueStack(){
        pre = new LinkedList<>();
        after = new LinkedList<>();
    }

    public void push(E element){
        pre.offer(element);
    }

    public E pop(){
        E result = null;
        while(!pre.isEmpty()){
            result = pre.poll();
            if(!pre.isEmpty()){
                after.offer(result);
            }
        }
        Queue<E> item = pre;
        pre=after;
        after=item;
        return result;
    }

    public E top(){
        E result = null;
        while(!pre.isEmpty()){
            result = pre.poll();
            after.offer(result);
        }
        Queue<E> item = pre;
        pre=after;
        after=item;
        return result;
    }

    public boolean empty(){
        return pre.isEmpty();
    }
}
