package create_patterns.factoryPattern.abstractFactory.factory.Impl;

import create_patterns.factoryPattern.abstractFactory.factory.SystemFactory;
import create_patterns.factoryPattern.abstractFactory.service.OperationController;
import create_patterns.factoryPattern.abstractFactory.service.UIController;
import create_patterns.factoryPattern.abstractFactory.service.impl.Android.AndroidOperationControllerImpl;
import create_patterns.factoryPattern.abstractFactory.service.impl.Android.AndroidUIControllerImpl;

public class AndroidFactoryImpl implements SystemFactory {
    public OperationController createOperationController() {
        return new AndroidOperationControllerImpl();
    }

    public UIController createUIController() {
        return new AndroidUIControllerImpl();
    }
}
