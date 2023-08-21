package Queue.QueueByArray;

public class CircleQueue {
    //队列最大数量
    private int maxSize=0;

    //队列添加的最后一个元素的位置
    private int rear = 0;

    //队列的获取元素的第一个位置
    private int front = 0;

    //元素存放容器
    private Object[] itemQueue;

    public CircleQueue(int maxSize){
        if(maxSize==0){
            throw new RuntimeException("队列初始化不能为0");
        }
        this.maxSize = maxSize+1;
        itemQueue = new Object[this.maxSize];
    }

    /**
     * 添加元素
     * @param item
     */
    public void add( Object item){
        //检测元素是否已满
        if(checkIsFull()){
            throw new RuntimeException("队列已满");
        }
        //添加元素
        rear++;
        if(rear==maxSize){
            rear=rear%maxSize;
        }
        itemQueue[rear]=item;
    }

    /**
     * 获取元素指针下移动
     * @return
     */
    public Object next(){
        //判断是否有效下一个元素
        if(!hasNext()){
            throw new RuntimeException("队列为空");
        }
        //头指针往后移动
        front++;
        //判断指针是否超过rear
        if(front==maxSize){
            front = front%maxSize;
        }
        return itemQueue[front];
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
    private boolean checkIsFull() {
        if((rear+1)%maxSize==front){
            return true;
        }
        return false;
    }

    public String getItemQueue (){
        StringBuffer result = new StringBuffer("[");
        for (int i= front+1;i<=front+size();i++){
            result.append(itemQueue[i%maxSize]);
            if(i==front+size()){
                result.append("]");
            }else{
                result.append(",");
            }
        }
        return result.toString();
    }

    /**
     * 判断下一个元素是否为空
     * @return
     */
    public boolean hasNext(){
        return rear !=front;
    }

    public int size(){
        return (rear+maxSize-front)%maxSize;
    }

}
