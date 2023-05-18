package create_patterns.factoryPattern.simpleFactoryPattern.pojo.shape;

import create_patterns.factoryPattern.simpleFactoryPattern.pojo.Shape;

public class CircleShape implements Shape {
    public final String NAME = "圆形";
    private final Integer  TYPE = 1;

    public void draw() {
        System.out.println("画"+NAME);
    }
}
