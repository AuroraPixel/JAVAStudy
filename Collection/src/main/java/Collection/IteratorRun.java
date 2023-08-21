package Collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class IteratorRun {
    public static void main(String[] args) {
        List<String> collection = new ArrayList<String>();
        collection.add("aa");
        collection.add("bb");
        collection.add("cc");
        collection.add("dd");
        collection.add("gg");
        //获取迭代器
        Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()){

            String next = iterator.next();
            if(next.equals("bb")){
                collection.remove("bb");
            }
        }

//        List<String> list = new ArrayList<String>();
//        list.add("bb");
//        list.add("aa");
//        list.add("cc");
//        Iterator<String> lists = list.iterator();
//        while (lists.hasNext()){
//            String next = lists.next();
//            if(next.equals("bb")){
//                list.remove("bb");
//            }
//        }

        //总结：
        //1.Collection获取迭代器：collection.iterator() 方法获取
        //2.迭代器的三个方法：hasNext() next() remove()
        //2.1：hasNext() 判断下一个是否有元素：有true 无false
        //2.2: next() 获取元素,并且指针移动到下一个元素的位置
        //2.3: remove() 通过这个方法可以移动collection中当前通过迭代器next获取的元素
        //3.注意:
        //3.1.当hasNext()为false的时候：使用next()的时候会出现 NoSuchElementException
        //3.2.当remove()时：iterator为初始状态的时候,会出现IllegalStateException
        //3.3.当iterator在遍历的时候，使用集合中的修改方法会出现 ConcurrentModificationException
    }
}
