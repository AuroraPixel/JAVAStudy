package create_patterns.factoryPattern.simpleFactoryPattern.factory;

import create_patterns.factoryPattern.simpleFactoryPattern.pojo.Shape;
import create_patterns.factoryPattern.simpleFactoryPattern.pojo.shape.CircleShape;
import create_patterns.factoryPattern.simpleFactoryPattern.pojo.shape.ReactShape;

public class ShapeFactory {
    private final String Tag = "ShapeFactory";

    public Shape getShape(String type){
        Shape shape = null;
        if(type.equalsIgnoreCase("circle")){
            shape = new CircleShape();
        }
        if(type.equalsIgnoreCase("React")){
            shape = new ReactShape();
        }

        return shape;
    }
}
