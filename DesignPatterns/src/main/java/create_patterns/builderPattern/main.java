package create_patterns.builderPattern;

public class main {
    public static void main(String[] args) {
        createBuilderPattern();
    }

    public static void createBuilderPattern(){
        builderPattern builderPattern = create_patterns.builderPattern.builderPattern.builder().ageBuild(22);
        System.out.println(builderPattern);
        System.out.println(builderPattern.hashCode());
        create_patterns.builderPattern.builderPattern builderPattern1 = builderPattern.nameBuild("aaa");
        System.out.println(builderPattern1);
        System.out.println(builderPattern.hashCode());
        System.out.println(builderPattern1.build());
        System.out.println(builderPattern1.build().hashCode());
    }
}
