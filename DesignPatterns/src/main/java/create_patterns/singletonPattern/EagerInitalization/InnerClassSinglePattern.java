package create_patterns.singletonPattern.EagerInitalization;

public class InnerClassSinglePattern {

    private InnerClassSinglePattern(){
        System.out.println("构造器:InnerClassSinglePattern:被创建了");
    }


    private static class InnerClassInstance{
        private static final InnerClassSinglePattern innerClassSinglePattern = new InnerClassSinglePattern();
    }

    public static InnerClassSinglePattern getInstance(){
        return InnerClassInstance.innerClassSinglePattern;
    }
}
