package Collection;

import java.util.ArrayList;
import java.util.Collection;

public class MainRun {
    public static void main(String[] args) {
        //创建Collection集合
        Collection collection = new ArrayList<>();
        //collection的基本方法
        //1.添加元素 boolean add(E e);
        collection.add("aa");
        collection.add("bb");
        collection.add("cc");
        System.out.println(collection);
        //2.删除元素 boolean remove(Object o);
        // 注：Collection集合中的remove方法通过元素而不是索引
        collection.remove("aa");
        //3.清空集合 void clear();
        //collection.clear();
        //4.判断集合是否为空 boolean isEmpty();
        System.out.println(collection.isEmpty());
        //5.获取集合的长度 int size();
        System.out.println(collection.size());
        //6.判断集合中是否包含某个元素 boolean contains(Object o);
        //注：Collection集合中的contains方法是通过对象的equals方法来判断是否包含某个元素,如果是自定义对象，需要重写equals方法
        System.out.println(collection.contains("bb"));
        //7.将集合转换为数组 Object[] toArray();
        Object[] objects = collection.toArray();
        //8.将集合转换为数组 T[] toArray(T[] a);
        //9.将集合中的元素添加到另一个集合中 boolean addAll(Collection<? extends E> c);
        Collection collection1 = new ArrayList<>();
        collection1.add("dd");
        collection1.add("ee");
        collection.addAll(collection1);
        System.out.println(collection);
        //10.判断集合中是否包含另一个集合中的所有元素 boolean containsAll(Collection<?> c);
        System.out.println(collection.containsAll(collection1));
        //11.删除集合中与另一个集合中相同的元素 boolean removeAll(Collection<?> c);
        //12.保留集合中与另一个集合中相同的元素 boolean retainAll(Collection<?> c);
    }
}

