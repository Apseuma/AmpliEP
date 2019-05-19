package servicelocatorTest;

import Exceptions.LocatorError;
import Interfaces.InterfaceC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator.*;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
class CachedServiceLocatorTest{
    private CachedServiceLocator csl;

    @BeforeEach
    void setUp() {
        csl = new CachedServiceLocator();
    }

    @Test
    void setServiceTest() throws LocatorError {
        csl.setService("service1", new FactoryB1());

        assertThrows(LocatorError.class, () -> {
            csl.setService("service1", new FactoryC1());
        });

        csl.setService("service2", new FactoryB1());
    }

    @Test
    void setConstantTest() throws LocatorError {
        csl.setConstant("constant1", 34);
        csl.setConstant("constant2", "hello");

        assertThrows(LocatorError.class, () -> {
            csl.setConstant("constant1", "hey, I'm a constant");
        });

        csl.setConstant("constant3", 34);
        csl.setConstant("factory", new FactoryB1());   //ok
    }

    @Test
    void setExistentServiceAndConstantTest() throws LocatorError {
        csl.setConstant("A", 34);
        assertThrows(LocatorError.class, () -> {
            csl.setService("A", new FactoryD1());
        });

        csl.setService("B", new FactoryC1());
        assertThrows(LocatorError.class, () -> {
            csl.setConstant("B", 89);
        });
    }

    @Test
    void getInexistentObject() throws LocatorError {
        assertThrows(LocatorError.class, () -> {
            csl.getObject("inventat");
        });
    }

    @Test
    void getSameOrNotObject() throws LocatorError {
        csl.setConstant("constC", "string");

        Object const1 = csl.getObject("constC");
        Object const2 = csl.getObject("constC");
        assertSame(const1, const2);

        csl.setService("serv1", new FactoryC1());
        InterfaceC interfC1 = (InterfaceC) csl.getObject("serv1");
        InterfaceC interfC2 = (InterfaceC) csl.getObject("serv1");
        assertSame(interfC1, interfC2);
    }

    @Test
    void getObjectInterfaceDTest() throws LocatorError{
        csl.setService("interfD",new FactoryD1());

        assertThrows(LocatorError.class, () -> {
            csl.getObject("interfD"); //pq necessita un "constD"->*int*
        });

        csl.setConstant("constD",155);
        csl.getObject("interfD"); //OK: perquè ja té un "constD"->*int*
    }

    @Test
    void getObjectInterfaceCTest() throws LocatorError{
        csl.setService("interfC",new FactoryC1());

        assertThrows(LocatorError.class, () -> {
            csl.getObject("interfC"); //pq necessita un "constC"->*string*
        });

        csl.setConstant("constC","hello!!");
        csl.getObject("interfC"); //OK: perquè ja té un "constC"->*string*
    }

    @Test
    void getObjectInterfaceBTest() throws LocatorError{
        csl.setService("interfB",new FactoryB1());

        assertThrows(LocatorError.class, () -> {
            csl.getObject("interfB"); //pq necessita un "D"->*interfaceD*
        });

        csl.setConstant("constD",10 );
        csl.setService("interfD",new FactoryD1());
        Object interfD = csl.getObject("interfD");

        csl.setConstant("D",interfD); //ara ja té un "D"->*interfaceD*
        csl.getObject("interfB"); //OK
    }

    @Test
    void getObjectInterfaceATest() throws LocatorError{
        csl.setService("interfA",new FactoryA1());

        assertThrows(LocatorError.class, () -> {
            csl.getObject("interfA"); //pq necessita un "B"->*interfaceB*
            //                               i un "C"->*interfaceC*
        });

        csl.setConstant("constD",10 );
        csl.setService("interfD",new FactoryD1());
        Object interfD = csl.getObject("interfD");
        csl.setConstant("D",interfD);

        csl.setService("interfB",new FactoryB1());
        Object interfB = csl.getObject("interfB");
        csl.setConstant("B",interfB); //ara ja té un "B"->*interfaceB*

        csl.setConstant("constC","heyy" );
        csl.setService("interfC",new FactoryC1());
        Object interfC = csl.getObject("interfC");
        csl.setConstant("C",interfC); //ara ja té un "C"->*interfaceC*

        csl.getObject("interfA"); // OK
    }
}