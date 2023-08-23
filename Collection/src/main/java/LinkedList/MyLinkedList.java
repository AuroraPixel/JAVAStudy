package LinkedList;

import java.util.LinkedList;

public class MyLinkedList<E> {
    /**
     * 内部链表节点
     * @param <E>
     */
    private static class Node<E>{
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> pre,E element,Node<E> next){
            this.item=element;
            this.next=next;
            this.prev=pre;
        }
    }

    /**
     * 初始化大小
     */
    transient int size = 0;

    /**
     * 头节点
     */
    transient Node<E> first;

    /**
     * 尾节点
     */
    transient Node<E> last;

    public MyLinkedList(){}

    public boolean add(E element){
        linkLast(element);
        return true;
    }

    public E remove(int index){
        checkElementIndex(index);
        return unlink(node(index));
    }

    /**
     * 根据头尾查找要删除的节点
     * @param index
     * @return
     */
    private Node<E> node(int index) {
        //遍历查找节点
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    /**
     * 删除链表节点
     * @param node
     * @return
     */
    private E unlink(Node<E> node) {
        //当前节点的元素
        final E item = node.item;
        //前一个节点
        final Node<E> prev = node.prev;
        //后一个节点
        final Node<E> next = node.next;
        //如果前一个节点为空头节点尾下一个节点
        if(prev==null){
            first = next;
        }else{
            prev.next = next;
            node.prev = null;
        }

        if(next==null){
            last = prev;
        }else{
            next.prev = prev;
            node.next = null;
        }

        return item;
    }

    private void checkElementIndex(int index) {
        boolean flag = index >= 0 && index < size;
        if(!flag){
            throw new RuntimeException("索引越界!");
        }
    }

    /**
     * 添加节点
     * @param element
     */
    private void linkLast(E element) {
        //当前尾节点—>新变量
        final Node<E> l = last;
        //创建新节点
        final Node<E> newNode = new Node<E>(l,element,null);
        //last重新指向
        last = newNode;
        //判断头节点是否为空
        if(l==null){
            first = newNode;
        }else{
            l.next=newNode;
        }
        size++;

    }
}
