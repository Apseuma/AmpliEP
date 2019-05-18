package servicelocatorTest;

import Exceptions.LocatorError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceLocatorTest {
    private ServiceLocator ssl;
    private ServiceLocator csl;

    @BeforeEach
    void setUp() {
        ssl = new SimpleServiceLocator();
        csl = new CachedServiceLocator();

    }

    @ParameterizedTest
    void setServiceTest() throws LocatorError {
        ssl.setService("service1", new FactoryB1());

        //both factories with the same key -> LocatorError
        assertThrows(LocatorError.class, () -> {
            ssl.setService("service1", new FactoryC1());
        });

        //same factories with different keys
        ssl.setService("service2", new FactoryB1());

    }


    @Test
    void setConstantTest() throws LocatorError {
        ssl.setConstant("constant1", 34);
        ssl.setConstant("constant2", "hello");

        //two constants with the same key -> LocatorError
        assertThrows(LocatorError.class, () -> {
            ssl.setConstant("constant1", "hey, I'm a constant");
        });

        //same constants with different keys ->OK
        ssl.setConstant("constant3", 34);

        //explicar??????? factory es object so...
        ssl.setConstant("factory", new FactoryB1());

    }

    @Test
    void setServiceAndConstantTest() throws LocatorError {
        ssl.setConstant("A", 34);

        //two constants/services with the same key -> LocatorError
        assertThrows(LocatorError.class, () -> {
            ssl.setService("A", new FactoryD1());
        });


        ssl.setService("B", new FactoryC1());

        //two constants/services with the same key -> LocatorError
        assertThrows(LocatorError.class, () -> {
            ssl.setConstant("B", 89);
        });


    }
