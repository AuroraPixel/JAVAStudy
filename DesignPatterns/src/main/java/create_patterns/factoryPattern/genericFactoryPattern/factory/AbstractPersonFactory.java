package create_patterns.factoryPattern.genericFactoryPattern.factory;

import create_patterns.factoryPattern.genericFactoryPattern.service.PersonService;

public interface AbstractPersonFactory {
      <T extends PersonService> PersonService createPerson (Class<T> cls);
}
