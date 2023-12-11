package helpers;

import org.aeonbits.owner.ConfigFactory;

/**
 * @author Евгения Лаптева
 * С помощью этого класса мы можем пользоваться информацией из файла test.properties
 */
public class Properties {

    public static TestProperties testProperties = ConfigFactory.create(TestProperties.class);
}
