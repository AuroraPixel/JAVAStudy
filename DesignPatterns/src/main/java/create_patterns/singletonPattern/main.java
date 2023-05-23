package create_patterns.singletonPattern;

import create_patterns.singletonPattern.EagerInitalization.InnerClassSinglePattern;
import create_patterns.singletonPattern.EagerInitalization.LazySinglePattern;
import create_patterns.singletonPattern.EagerInitalization.SinglePattern;
import create_patterns.singletonPattern.EagerInitalization.normalInstance;

public class main {
    public static void main(String[] args) {
//        //普通模式
//        createNormalInstance();
//        //饿汉式
//        createEagerSinglePattern();
//        //懒汉式
//        createLazySinglePattern();
//        //多线程下的懒汉式
//        manyThreadCreateLazySinglePattern();
//        //线程安全的懒汉式(volatile+synchronize)
//        createSynchronizeLazySinglePattern();
        //匿名内部类
        createInnerClassSinglePattern();
    }

    //匿名内部类
    private static void createInnerClassSinglePattern(){
        for (int i = 0; i < 20; i++) {
            InnerClassSinglePattern instance = InnerClassSinglePattern.getInstance();
            System.out.println(instance);
        }
    }

    //懒汉式(线程不安全的)
    private static void createLazySinglePattern() {
        System.out.println("懒汉式");
        LazySinglePattern instance = LazySinglePattern.getInstance();
        System.out.println(instance);
        LazySinglePattern instance1 = LazySinglePattern.getInstance();
        System.out.println(instance1);
    }

    //懒汉式(线程不安全的)
    private static void createSynchronizeLazySinglePattern() {
        System.out.println("synchronize懒汉式");
        LazySinglePattern instance = LazySinglePattern.getSynchronized();
        System.out.println(instance);
        LazySinglePattern instance1 = LazySinglePattern.getSynchronized();
        System.out.println(instance1);
    }

    private static void manyThreadCreateLazySinglePattern(){
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                LazySinglePattern instance = LazySinglePattern.getInstance();
                System.out.println(instance);
            }).start();
        }
    }

    //普通模式
    private static void createNormalInstance(){
        System.out.println("普通模式");
        normalInstance normalInstance = new normalInstance();
        normalInstance normalInstance1 = new normalInstance();
        System.out.println(normalInstance);
        System.out.println(normalInstance1);
    }

    //饿汉式
    private static void createEagerSinglePattern() {
        System.out.println("饿汉式");
        SinglePattern instance = SinglePattern.getInstance();
        System.out.println(instance);
        SinglePattern instance1 = SinglePattern.getInstance();
        System.out.println(instance1);
    }
}
