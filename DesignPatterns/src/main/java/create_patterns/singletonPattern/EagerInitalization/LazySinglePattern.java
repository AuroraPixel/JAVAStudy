package create_patterns.singletonPattern.EagerInitalization;

public class LazySinglePattern {
    private static volatile   LazySinglePattern lazySinglePattern = null;

    private LazySinglePattern(){
        System.out.println("构造器:LazySinglePattern:被创建了");
    }

    //线程不安全的创建方式
    public static LazySinglePattern getInstance(){
        if(lazySinglePattern==null){
            System.out.println("LazySinglePattern:为空先创建!");
            lazySinglePattern = new LazySinglePattern();
            System.out.println(lazySinglePattern);
        }
        return lazySinglePattern;
    }

    //加synchronized锁
    public static  synchronized LazySinglePattern getSynchronized(){
        if(lazySinglePattern==null){
            lazySinglePattern = new LazySinglePattern();
            System.out.println(lazySinglePattern);
        }
        return lazySinglePattern;
    }
}
