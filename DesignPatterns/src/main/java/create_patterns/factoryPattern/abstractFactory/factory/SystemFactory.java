package create_patterns.factoryPattern.abstractFactory.factory;

import create_patterns.factoryPattern.abstractFactory.service.OperationController;
import create_patterns.factoryPattern.abstractFactory.service.UIController;

public interface SystemFactory {
    OperationController createOperationController();
    UIController createUIController();
}
