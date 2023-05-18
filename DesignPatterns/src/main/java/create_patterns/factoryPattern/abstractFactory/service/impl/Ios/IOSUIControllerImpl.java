package create_patterns.factoryPattern.abstractFactory.service.impl.Ios;

import create_patterns.factoryPattern.abstractFactory.service.UIController;

public class IOSUIControllerImpl implements UIController {
    public void display() {
        System.out.println("IOS显示器");
    }
}
