package servicelocatorTest;

import Exceptions.LocatorError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator.Factory;
import servicelocator.FactoryB1;
import servicelocator.SimpleServiceLocator;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SimpleServiceLocatorTest {
    private SimpleServiceLocator ssl;

    @BeforeEach
    void setUp() {
        ssl = new SimpleServiceLocator();
    }

    @Test
    void setServiceTest1() throws LocatorError {
        //ssl.setService("service1", new FactoryB1());
        assertDoesNotThrow(ssl.setService("service1", new FactoryB1()));
    }
}