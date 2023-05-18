package create_patterns.factoryPattern.abstractFactory.service.impl.Android;

import create_patterns.factoryPattern.abstractFactory.service.UIController;

public class AndroidUIControllerImpl implements UIController {
    public void display() {
        System.out.println("安卓显示器");
    }
}
