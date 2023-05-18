package create_patterns.factoryPattern.abstractFactory.factory.Impl;

import create_patterns.factoryPattern.abstractFactory.factory.SystemFactory;
import create_patterns.factoryPattern.abstractFactory.service.OperationController;
import create_patterns.factoryPattern.abstractFactory.service.UIController;
import create_patterns.factoryPattern.abstractFactory.service.impl.Ios.IOSOperationControllerImpl;
import create_patterns.factoryPattern.abstractFactory.service.impl.Ios.IOSUIControllerImpl;

public class IOSFactoryImpl implements SystemFactory {
    public OperationController createOperationController() {
        return new IOSOperationControllerImpl();
    }

    public UIController createUIController() {
        return new IOSUIControllerImpl();
    }
}
