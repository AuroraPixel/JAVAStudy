package ArrayList;

import java.util.Arrays;
import java.util.Collection;

public class MyArrayList<E> {
    /**
     * 不可序列化元素对象
     */
    transient Object[] elementData;

    /**
     * 初始化空对象
     */
    private static final Object[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};

    private static final Object[] DEFAULT_EMPTY_ELEMENT_DATA = {};

    /**
     * 初始化容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * int最大-8
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 集合长度/下一个元素的指针
     */
    private int size;

    /**
     * 初始化
     */
    public MyArrayList() {
        this.elementData = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }

    /**
     * 初始化
     *
     * @param initialCapacity
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = DEFAULT_EMPTY_ELEMENT_DATA;
        } else {
            throw new RuntimeException("集合初始化大小不能为负数!");
        }
    }

    public Object[] getElementData(){
        return this.elementData;
    }

    /**
     * 添加参数
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        //集合容量扩容
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    /**
     * 添加另一个集合的方法
     * @param c
     * @return
     */
    public boolean addAll(Collection<? extends E> c){
        //原始集合变为数组
        Object[] a = c.toArray();
        //获取原始集合的长度
        int alength = a.length;
        ensureCapacityInternal(size+alength);
        //元素copy
        System.arraycopy(a,0,elementData,size,alength);
        size+=alength;
        return alength!=0;
    }

    /**
     * 集合容量扩容
     *
     * @param minCapacity
     */
    private void ensureCapacityInternal(int minCapacity) {
        //比较指针后移大小与容量大小
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    /**
     * 集合容量扩容
     *
     * @param minCapacity
     */
    private void ensureExplicitCapacity(int minCapacity) {
        if (minCapacity - elementData.length > 0)
            //集合容量
            grow(minCapacity);
    }

    /**
     * 扩容方法
     *
     * @param minCapacity
     */
    private void grow(int minCapacity) {
        //获取当前长度
        int oldLength = elementData.length;
        //扩展为当前容量的1.5倍
        int newLength = oldLength + (oldLength >> 1);
        //判断预期扩展容量与当前1.5容量大小取其中最大的
        if(newLength-minCapacity<0){
            newLength = minCapacity;
        }
        //与最大容量比较
        if(newLength-MAX_ARRAY_SIZE>0){
            newLength = hugeCapacity(minCapacity);
        }
        //集合扩容
        elementData = Arrays.copyOf(elementData,newLength);
    }

    /**
     * 判断是否给予初始化容量
     * @param elementData
     * @param minCapacity
     * @return
     */
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        //比较是否为空参传入初始化对象
        if (elementData == DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
            //如果为空参初始化的空对象，比较size与默认长度(默认长度为10)
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        //返回指定size
        return minCapacity;
    }

    /**
     * 最大容量比较
     * @param minCapacity
     * @return
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }
}
