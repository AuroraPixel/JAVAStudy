package create_patterns.singletonPattern.EagerInitalization;

public class SinglePattern {
    private static SinglePattern singlePattern = new SinglePattern();

    private SinglePattern(){
        System.out.println("singlePattern被创建了!");
    }

    public static SinglePattern getInstance(){
        return  singlePattern;
    }
}
