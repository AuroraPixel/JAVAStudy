package Queue.QueueByArray;

import java.lang.Object;

public class Queue {
    //队列最大数量
    private int maxSize = 0;

    //队列添加的最后一个元素的位置
    private int rear = -1;

    //队列的获取元素的第一个位置
    private int front = -1;

    //元素存放容器
    private Object[] itemQueue;

    public Queue(int maxSize){
        if(maxSize==0){
            throw new RuntimeException("队列初始化不能为0");
        }
        this.maxSize = maxSize;
        itemQueue = new Object[maxSize];
    }

    /**
     * 添加元素
     * @param item
     */
    public void add( Object item){
        //检测元素是否已满
        checkIsFull();
        //添加元素
        rear++;
        itemQueue[rear]=item;
    }

    /**
     * 获取元素指针下移动
     * @return
     */
    public Object next(){
        //判断是否有效下一个元素
        if(!hasNext()){
            throw new RuntimeException("队列下一个元素为空");
        }
        //头指针往后移动
        front++;
        //判断指针是否超过rear
        if(front>rear){
            throw new RuntimeException("指针超出索引!");
        }
        return itemQueue[front];
    }

    /**
     * 判断下一个元素是否为空
     * @return
     */
    public boolean hasNext() {
        int localFront = front;
        if(isEmpty()){
            return false;
        }
        localFront++;
        if(itemQueue[localFront]==null){
            return false;
        }else{
            return true;
        }
    }

    public void remove(){
        if(rear!=front){
            System.arraycopy(itemQueue,front+1,itemQueue,front,rear-front);
        }
        itemQueue[rear] = null;
        front--;
        rear--;
    }


    /**
     * 检测元素是否已满
     */
    private void checkIsFull() {
        if(rear==maxSize-1){
            throw new RuntimeException("队列已满");
        }
    }

    public Object[] getItemQueue (){
        return itemQueue;
    }

    public boolean isEmpty(){
        return rear ==front;
    }

    public int size(){
        return rear+1;
    }

}
