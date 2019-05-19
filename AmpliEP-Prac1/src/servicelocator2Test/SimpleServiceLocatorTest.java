package servicelocator2Test;

import Exceptions.LocatorError;
import Interfaces.InterfaceB;
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
            ssl.setService(factB2.getClass(), factB2);  //ja hi ha un FactoryB1
        });

        ssl.setService(factD.getClass(), factD);
    }


    @Test
    void setConstantTest() throws LocatorError {
        String str = "holaaa";
        int integ = 55;
        int integ2 = 1899;

        ssl.setConstant(String.class, str);
        ssl.setConstant(Integer.class, integ);

        assertThrows(LocatorError.class, () -> {
            ssl.setConstant(Integer.class, integ2);
        });

        ssl.setConstant(FactoryA1.class,new FactoryA1());

        assertThrows(LocatorError.class, () -> {
            ssl.setConstant(FactoryA1.class,new FactoryA1());
        });
    }


    @Test
    void getInexistentObject() throws LocatorError {
        assertThrows(LocatorError.class, () -> {
            ssl.getObject(String.class);
        });

        Factory factA = new FactoryA1();
        assertThrows(LocatorError.class, () -> {
            ssl.getObject(factA.getClass());    //no s'ha fet setService
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
        InterfaceC interfC2 = (InterfaceC) ssl.getObject(factC.getClass());
        assertNotSame(interfC1, interfC2);
    }

    @Test
    void getObjectConstantTest() throws LocatorError {
        ssl.setConstant(String.class,"La factoria C em necessita!");
        ssl.setConstant(Integer.class,44);
        ssl.setConstant(Double.class,4.33);
        ssl.setConstant(FactoryA1.class,new FactoryA1());

        assertThrows(LocatorError.class, () -> {
            ssl.getObject(FactoryC1.class);
        });

        ssl.getObject(String.class);
        ssl.getObject(Integer.class);
        ssl.getObject(Double.class);
        ssl.getObject(FactoryA1.class);
    }

    @Test
    void getObjectInterfaceDTest() throws LocatorError {
        Factory factD = new FactoryD1();
        ssl.setService(factD.getClass(),factD);

        assertThrows(LocatorError.class, () -> {
            ssl.getObject(factD.getClass()); //pq necessita un Integer.class->*número*
        });

        ssl.setConstant(Integer.class, 34);
        ssl.getObject(factD.getClass()); //OK: ara ja té el Integer.class->*número*
    }

    @Test
    void getObjectInterfaceCTest() throws LocatorError {
        Factory factC = new FactoryC1();
        ssl.setService(factC.getClass(),factC);

        assertThrows(LocatorError.class, () -> {
            ssl.getObject(factC.getClass()); //pq necessita un String.class->*string*
        });

        ssl.setConstant(String.class, "ho necessita InterfaceC");
        ssl.getObject(factC.getClass()); //OK: ara ja té el String.class->*string*
    }

    @Test
    void getObjectInterfaceBTest() throws LocatorError {
        Factory factB = new FactoryB1();
        ssl.setService(factB.getClass(),factB);

        assertThrows(LocatorError.class, () -> {
            ssl.getObject(factB.getClass()); //pq necessita un InterfaceD.class->*interfaceD*
        });

        ssl.setConstant(Integer.class, 455);

        Factory factD = new FactoryD1();
        ssl.setService(factD.getClass(),factD);
        InterfaceD interfD = (InterfaceD) ssl.getObject(factD.getClass());

        ssl.setConstant(InterfaceD.class,interfD); //ara ja té el InterfaceD.class->*interfaceD*

        ssl.getObject(factB.getClass());    //OK
        ssl.getObject(FactoryB1.class);     //aquesta i l'anteior són el mateix
    }


    @Test
    void getObjectInterfaceATest() throws LocatorError {
        Factory factA = new FactoryA1();
        ssl.setService(factA.getClass(),factA);

        assertThrows(LocatorError.class, () -> {
            ssl.getObject(factA.getClass()); //pq necessita un InterfaceB.class->*interfaceB*
                                            //            i un InterfaceC.class->*interfaceC*

        });

        Factory factC = new FactoryC1();
        ssl.setService(factC.getClass(),factC);
        ssl.setConstant(String.class,"necessari per fer InterfaceC");

        InterfaceC interfC = (InterfaceC) ssl.getObject(factC.getClass());
        ssl.setConstant(InterfaceC.class,interfC);  //ara ja té el InterfaceC.class->*interfaceC*

        Factory factD = new FactoryD1();
        ssl.setService(factD.getClass(),factD);
        ssl.setConstant(Integer.class, 455);

        InterfaceD interfD = (InterfaceD) ssl.getObject(factD.getClass());
        ssl.setConstant(InterfaceD.class,interfD);

        Factory factB = new FactoryB1();
        ssl.setService(factB.getClass(),factB);
        InterfaceB interfB = (InterfaceB) ssl.getObject(factB.getClass());
        ssl.setConstant(InterfaceB.class,interfB);  //ara ja té el InterfaceB.class->*interfaceB*

        ssl.getObject(factA.getClass()); //OK: perquè ja té un InterfaceB.class->*interfaceB*
        //                     i un InterfaceC.class->*interfaceC*
    }
}