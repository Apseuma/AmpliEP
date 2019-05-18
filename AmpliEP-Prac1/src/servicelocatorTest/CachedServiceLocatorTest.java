package servicelocatorTest;

import Exceptions.LocatorError;
import Interfaces.InterfaceC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator.*;

import static org.junit.jupiter.api.Assertions.*;

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
        assertSame(interfC1, interfC2);     //UNICA LÍNEA QUE CANVIA!!!
    }


    @Test
    void getObjectComplexTest() throws LocatorError {

        csl.setConstant("constD", 34);
        csl.setConstant("constC", "valor de InterfaceC");
        csl.setService("B", new FactoryB1());
        csl.setService("A", new FactoryA1());

        assertSame(34, csl.getObject("constD")); //OK, pq es un int
        assertSame("valor de InterfaceC", csl.getObject("constC")); //OK, pq es una string

        assertThrows(LocatorError.class, () -> {
            csl.getObject("B"); //pq necessita un "D"->InterfaceD
        });

        assertThrows(LocatorError.class, () -> {
            csl.getObject("A"); //pq necessita un "B"->InterfaceB
        });
    }


    @Test
    void getObjectComplexTest2() throws LocatorError {
        csl.setConstant("constD", 34);
        csl.setConstant("constC", "valor de InterfaceC");

        csl.setService("factD",new FactoryD1());
        Object interfD = csl.getObject("factD");
        csl.setConstant("D",interfD);

        csl.setService("factC",new FactoryC1());
        Object interfC = csl.getObject("factC");
        csl.setConstant("C",interfC);

        csl.setService("factB",new FactoryB1());
        Object interfB = csl.getObject("factB");
        csl.setConstant("B",interfB);

        csl.setService("factA",new FactoryA1());

        /*ara, tenim:
            SIMPLE SERVICE LOCATOR:
            "factA"->FactoryA1();
            "factB"->FactoryB1();
            "factC"->FactoryC1();
            "factD"->FactoryD1();

            és a dir, disposem dels 4 tipus d'Interfícies si fem un getObject.
         */
    }

}