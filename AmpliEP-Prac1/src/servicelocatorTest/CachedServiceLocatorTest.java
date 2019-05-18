package servicelocatorTest;

import Exceptions.LocatorError;
import Interfaces.InterfaceC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator.CachedServiceLocator;
import servicelocator.FactoryC1;

import static org.junit.jupiter.api.Assertions.*;

class CachedServiceLocatorTest extends ServiceLocatorTest{
    private CachedServiceLocator csl;
    @BeforeEach
    void setUp() {
        csl = new CachedServiceLocator();
    }

    @Test
    void setService() {
    }

    @Test
    void setConstant() {
    }

    @Test
    void getObject() throws LocatorError {
        csl.setConstant("C","string");
        csl.setConstant("D",55);


        Object const1 = csl.getObject("C");
        Object const2 = csl.getObject("C");


        csl.setService("serv1",new FactoryC1());
        InterfaceC interfC1= (InterfaceC) csl.getObject("serv1");
        InterfaceC interfC2= (InterfaceC) csl.getObject("serv1");
        assertSame(interfC1,interfC2);



    }
}