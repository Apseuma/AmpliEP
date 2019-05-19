package servicelocatorTest;

import Exceptions.LocatorError;
import Interfaces.InterfaceC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleServiceLocatorTest{
    private SimpleServiceLocator ssl;

    @BeforeEach
    void setUp() {
        ssl = new SimpleServiceLocator();
    }

    @Test
    void setServiceTest() throws LocatorError {
        ssl.setService("service1", new FactoryB1());
        assertThrows(LocatorError.class, () -> {
            ssl.setService("service1", new FactoryC1());
        });

        ssl.setService("service2", new FactoryB1());
    }


    @Test
    void setConstantTest() throws LocatorError {
        ssl.setConstant("constant1", 34);
        ssl.setConstant("constant2", "hello");

        assertThrows(LocatorError.class, () -> {
            ssl.setConstant("constant1", "hey, I'm a constant");
        });

        ssl.setConstant("constant3", 34);

        ssl.setConstant("factory", new FactoryB1());   //ok
    }

    @Test
    void setExistentServiceAndConstantTest() throws LocatorError {
        ssl.setConstant("A", 34);
        assertThrows(LocatorError.class, () -> {
            ssl.setService("A", new FactoryD1());
        });

        ssl.setService("B", new FactoryC1());
        assertThrows(LocatorError.class, () -> {
            ssl.setConstant("B", 89);
        });
    }

    @Test
    void getInexistentObject() throws LocatorError {
        assertThrows(LocatorError.class, () -> {
            ssl.getObject("inventat");
        });
    }

    @Test
    void getSameOrNotObject() throws LocatorError {
        ssl.setConstant("constC", "string");

        Object const1 = ssl.getObject("constC");
        Object const2 = ssl.getObject("constC");
        assertSame(const1, const2);

        ssl.setService("serv1", new FactoryC1());
        InterfaceC interfC1 = (InterfaceC) ssl.getObject("serv1");
        InterfaceC interfC2 = (InterfaceC) ssl.getObject("serv1");
        assertNotSame(interfC1, interfC2);
    }



    @Test
    void getObjectInterfaceDTest() throws LocatorError{
        ssl.setService("interfD",new FactoryD1());

        assertThrows(LocatorError.class, () -> {
            ssl.getObject("interfD"); //pq necessita un "constD"->*int*
        });

        ssl.setConstant("constD",155);
        ssl.getObject("interfD"); //OK: perquè ja té un "constD"->*int*

    }


    @Test
    void getObjectInterfaceCTest() throws LocatorError{
        ssl.setService("interfC",new FactoryC1());

        assertThrows(LocatorError.class, () -> {
            ssl.getObject("interfC"); //pq necessita un "constC"->*string*
        });

        ssl.setConstant("constC","hello!!");
        ssl.getObject("interfC"); //OK: perquè ja té un "constC"->*string*

    }

    @Test
    void getObjectInterfaceBTest() throws LocatorError{
        ssl.setService("interfB",new FactoryB1());

        assertThrows(LocatorError.class, () -> {
            ssl.getObject("interfB"); //pq necessita un "D"->*interfaceD*
        });

        ssl.setConstant("constD",10 );
        ssl.setService("interfD",new FactoryD1());
        Object interfD = ssl.getObject("interfD");

        ssl.setConstant("D",interfD); //ara ja té un "D"->*interfaceD*

        ssl.getObject("interfB"); //OK

    }

    @Test
    void getObjectInterfaceATest() throws LocatorError{
        ssl.setService("interfA",new FactoryA1());

        assertThrows(LocatorError.class, () -> {
            ssl.getObject("interfA"); //pq necessita un "B"->*interfaceB*
                                            //           i un "C"->*interfaceC*
        });

        ssl.setConstant("constD",10 );
        ssl.setService("interfD",new FactoryD1());
        Object interfD = ssl.getObject("interfD");
        ssl.setConstant("D",interfD);

        ssl.setService("interfB",new FactoryB1());
        Object interfB = ssl.getObject("interfB");
        ssl.setConstant("B",interfB); //ara ja té un "B"->*interfaceB*


        ssl.setConstant("constC","heyy" );
        ssl.setService("interfC",new FactoryC1());
        Object interfC = ssl.getObject("interfC");
        ssl.setConstant("C",interfC); //ara ja té un "C"->*interfaceC*


        ssl.getObject("interfA"); // OK

    }

}