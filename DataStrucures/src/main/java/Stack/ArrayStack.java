package Stack;

public class ArrayStack<E> {
    /**
     * 栈栈元素
     */
    transient Object[] elementData;

    /**
     * 栈顶的指针
     */
    private int top = -1;

    private final int size;

    public ArrayStack(int index){
        if(index<=0){
            throw new RuntimeException("当前栈初始化长度不能为负数");
        }
        this.size = index;
        elementData = new Object[size];
    }

    public boolean push(E element){
        int expectIndex = top+2;
        if(size<expectIndex){
            throw new RuntimeException("队列已满");
        }
        elementData[++top]=element;
        return true;
    }

    public E pop(){
        if(top==-1){
            throw new RuntimeException("当前栈为空!");
        }
        return (E) elementData[top--];
    }
}
