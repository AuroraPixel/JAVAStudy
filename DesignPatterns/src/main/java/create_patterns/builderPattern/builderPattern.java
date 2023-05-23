package create_patterns.builderPattern;

public class builderPattern {
    private Integer age;

    private String name;

    private builderPattern(Integer age,String name){
        this.age = age;
        this.name = name;
    }

    private builderPattern(){

    }

    public static builderPattern builder(){
        builderPattern builderPattern = new builderPattern();
        return builderPattern;
    }

    public  builderPattern ageBuild(Integer age){
        this.age = age;
        return this;
    }

    public  builderPattern nameBuild(String name){
        this.name = name;
        return this;
    }

    public builderPattern build(){
        return new builderPattern(this.age,this.name);
    }



    @Override
    public String toString() {
        return "builderPattern{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
