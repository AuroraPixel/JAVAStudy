package create_patterns.factoryPattern;

import create_patterns.factoryPattern.abstractFactory.factory.Impl.AndroidFactoryImpl;
import create_patterns.factoryPattern.abstractFactory.factory.Impl.IOSFactoryImpl;
import create_patterns.factoryPattern.abstractFactory.factory.SystemFactory;
import create_patterns.factoryPattern.abstractFactory.service.OperationController;
import create_patterns.factoryPattern.abstractFactory.service.UIController;
import create_patterns.factoryPattern.factoryPattern.ImageFactory.JpgReaderFactory;
import create_patterns.factoryPattern.factoryPattern.Reader;
import create_patterns.factoryPattern.factoryPattern.ReaderFactory;
import create_patterns.factoryPattern.simpleFactoryPattern.factory.ShapeFactory;
import create_patterns.factoryPattern.simpleFactoryPattern.pojo.Shape;

public class main {
    public static void main(String[] args) {
        //简单工厂模式
        simpleFactory();
        //工厂方法模式
        Factory();
        //抽象工厂模式
        AbstractFactory();
    }

    //简单工厂类模式
    private static void simpleFactory() {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle = shapeFactory.getShape("circle");
        circle.draw();
        Shape react = shapeFactory.getShape("react");
        react.draw();
    }

    //工厂方法模式
    private static  void Factory(){
        ReaderFactory readerFactory = new JpgReaderFactory();
        Reader reader = readerFactory.getReader();
        reader.read();
    }

    private static void AbstractFactory(){
        //创建安卓工厂
        SystemFactory androidFactory = new AndroidFactoryImpl();

        OperationController androidOperationController = androidFactory.createOperationController();
        UIController androidUIController = androidFactory.createUIController();
        androidOperationController.Controller();
        androidUIController.display();

        //创建IOS工厂
        SystemFactory iosFactory = new IOSFactoryImpl();
        UIController iosUI = iosFactory.createUIController();
        OperationController iosOperation = iosFactory.createOperationController();
        iosUI.display();
        iosOperation.Controller();

    }
}
