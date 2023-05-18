package create_patterns.factoryPattern.simpleFactoryPattern.pojo.shape;

import create_patterns.factoryPattern.simpleFactoryPattern.pojo.Shape;

public class ReactShape implements Shape {
    public final String NAME = "方形";
    private final Integer  TYPE = 2;

    public void draw() {
        System.out.println("画"+NAME);
    }
}
