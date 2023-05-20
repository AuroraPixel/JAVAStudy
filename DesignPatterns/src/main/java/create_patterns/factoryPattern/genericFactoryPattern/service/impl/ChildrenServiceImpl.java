package create_patterns.factoryPattern.genericFactoryPattern.service.impl;

import create_patterns.factoryPattern.genericFactoryPattern.service.PersonService;

public class ChildrenServiceImpl implements PersonService {
    @Override
    public void action() {
        System.out.println("学习!");
    }
}
