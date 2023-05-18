package create_patterns.factoryPattern.genericFactoryPattern.factory.impl;

import create_patterns.factoryPattern.genericFactoryPattern.factory.AbstractPersonFactory;
import create_patterns.factoryPattern.genericFactoryPattern.service.PersonService;

public class PersonFactoryImpl implements AbstractPersonFactory {

    @Override
    public <T extends PersonService> PersonService createPerson(Class<T> cls) {
        PersonService personService = null;
        try {
            personService = cls.getDeclaredConstructor().newInstance();
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return personService;
    }
}
