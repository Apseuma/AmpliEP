package servicelocator2Test;

import Exceptions.LocatorError;
import Interfaces.InterfaceC;
import Interfaces.InterfaceD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator2.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleServiceLocatorTest{
    private SimpleServiceLocator ssl;

    @BeforeEach
    void setUp() {
        ssl = new SimpleServiceLocator();
    }

    @Test
    void setServiceTest() throws LocatorError {
        Factory factB = new FactoryB1();
        Factory factB2 = new FactoryB1();
        Factory factD = new FactoryD1();

        ssl.setService(factB.getClass(), factB);
        assertThrows(LocatorError.class, () -> {
            ssl.setService(factB2.getClass(), factB2);
        });

        ssl.setService(factD.getClass(), factD);
    }


    @Test
    void setConstantTest() throws LocatorError {
        String str = "holaaa";
        int integ = 55;
        int integ2 = 1899;
        Factory factC = new FactoryC1();


        ssl.setConstant(String.class, str);
        ssl.setConstant(Integer.class, integ);

        assertThrows(LocatorError.class, () -> {
            ssl.setConstant(Integer.class, integ2);
        });

        //ssl.setConstant(factC.getClass(),factC); pq no??

    }


    @Test
    void getInexistentObject() throws LocatorError {
        assertThrows(LocatorError.class, () -> {
            ssl.getObject(String.class);
        });

        Factory factA = new FactoryA1();
        assertThrows(LocatorError.class, () -> {
            ssl.getObject(factA.getClass());
        });

    }

    @Test
    void getSameOrNotObject() throws LocatorError {
        double real = 3.456;

        ssl.setConstant(Double.class, real);

        Object const1 = ssl.getObject(Double.class);
        Object const2 = ssl.getObject(Double.class);
        assertSame(const1, const2);


        Factory factC = new FactoryC1();

        ssl.setConstant(String.class,"La factoria C em necessita!");
        ssl.setService(factC.getClass(), factC);
        InterfaceC interfC1 = (InterfaceC) ssl.getObject(factC.getClass());
        InterfaceC interfC2 = (InterfaceC) ssl.getObject(FactoryC1.class);  //es la mateixa class
        assertNotSame(interfC1, interfC2);
    }


    /*@Test
    void getObjectComplexTest() throws LocatorError {

        ssl.setConstant(, 34);
        ssl.setConstant("constC", "valor de InterfaceC");
        ssl.setService("B", new FactoryB1());
        ssl.setService("A", new FactoryA1());

        assertSame(34, ssl.getObject("constD")); //OK, pq es un int
        assertSame("valor de InterfaceC", ssl.getObject("constC")); //OK, pq es una string

        assertThrows(LocatorError.class, () -> {
            ssl.getObject("B"); //pq necessita un "D"->InterfaceD
        });

        assertThrows(LocatorError.class, () -> {
            ssl.getObject("A"); //pq necessita un "B"->InterfaceB
        });
    }


    @Test
    void getObjectComplexTest2() throws LocatorError {
        ssl.setConstant(Integer.class, 44);
        ssl.setConstant(String.class, "necessari per fer InterfaceC");

        Factory factD = new FactoryD1();
        ssl.setService(factD.getClass(), factD);

        Object interfD = ssl.getObject(FactoryD1.class);
        ssl.setConstant(interfD.getClass(), interfD);
    }/*
        ssl.setService("factC",new FactoryC1());
        Object interfC = ssl.getObject("factC");
        ssl.setConstant("C",interfC);

        ssl.setService("factB",new FactoryB1());
        Object interfB = ssl.getObject("factB");
        ssl.setConstant("B",interfB);

        ssl.setService("factA",new FactoryA1());

        /*ara, tenim:
            SIMPLE SERVICE LOCATOR:
            "factA"->FactoryA1();
            "factB"->FactoryB1();
            "factC"->FactoryC1();
            "factD"->FactoryD1();

            és a dir, disposem dels 4 tipus d'Interfícies si fem un getObject.

    }
*/
}