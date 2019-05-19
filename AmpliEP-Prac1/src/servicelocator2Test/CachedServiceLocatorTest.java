package servicelocator2Test;

import Exceptions.LocatorError;
import Interfaces.InterfaceB;
import Interfaces.InterfaceC;
import Interfaces.InterfaceD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator2.CachedServiceLocator;
import servicelocator2.*;

import static org.junit.jupiter.api.Assertions.*;

public class CachedServiceLocatorTest {
    private CachedServiceLocator csl;

    @BeforeEach
    void setUp() {
        csl = new CachedServiceLocator();
    }

    @Test
    void setServiceTest() throws LocatorError {
        Factory factB = new FactoryB1();
        Factory factB2 = new FactoryB1();
        Factory factD = new FactoryD1();

        csl.setService(factB.getClass(), factB);

        assertThrows(LocatorError.class, () -> {
            csl.setService(factB2.getClass(), factB2);  //ja hi ha un FactoryB1
        });

        csl.setService(factD.getClass(), factD);
    }


    @Test
    void setConstantTest() throws LocatorError {
        String str = "holaaa";
        int integ = 55;
        int integ2 = 1899;

        csl.setConstant(String.class, str);
        csl.setConstant(Integer.class, integ);

        assertThrows(LocatorError.class, () -> {
            csl.setConstant(Integer.class, integ2);
        });

        csl.setConstant(FactoryA1.class,new FactoryA1());

        assertThrows(LocatorError.class, () -> {
            csl.setConstant(FactoryA1.class,new FactoryA1());
        });

    }


    @Test
    void getInexistentObject() throws LocatorError {
        assertThrows(LocatorError.class, () -> {
            csl.getObject(String.class);
        });

        Factory factA = new FactoryA1();
        assertThrows(LocatorError.class, () -> {
            csl.getObject(factA.getClass());    //no s'ha fet setService
        });

    }

    @Test
    void getSameOrNotObject() throws LocatorError {
        double real = 3.456;

        csl.setConstant(Double.class, real);

        Object const1 = csl.getObject(Double.class);
        Object const2 = csl.getObject(Double.class);
        assertSame(const1, const2);


        Factory factC = new FactoryC1();

        csl.setConstant(String.class,"La factoria C em necessita!");
        csl.setService(factC.getClass(), factC);
        InterfaceC interfC1 = (InterfaceC) csl.getObject(factC.getClass());
        InterfaceC interfC2 = (InterfaceC) csl.getObject(factC.getClass());
        assertSame(interfC1, interfC2);
    }

    @Test
    void getObjectConstantTest() throws LocatorError {
        csl.setConstant(String.class,"La factoria C em necessita!");
        csl.setConstant(Integer.class,44);
        csl.setConstant(Double.class,4.33);
        csl.setConstant(FactoryA1.class,new FactoryA1());

        assertThrows(LocatorError.class, () -> {
            csl.getObject(FactoryC1.class);
        });

        csl.getObject(String.class);
        csl.getObject(Integer.class);
        csl.getObject(Double.class);
        csl.getObject(FactoryA1.class);
    }

    @Test
    void getObjectInterfaceDTest() throws LocatorError {
        Factory factD = new FactoryD1();
        csl.setService(factD.getClass(),factD);

        assertThrows(LocatorError.class, () -> {
            csl.getObject(factD.getClass()); //pq necessita un Integer.class->*número*
        });

        csl.setConstant(Integer.class, 34);
        csl.getObject(factD.getClass()); //OK: ara ja té el Integer.class->*número*
    }

    @Test
    void getObjectInterfaceCTest() throws LocatorError {
        Factory factC = new FactoryC1();
        csl.setService(factC.getClass(),factC);

        assertThrows(LocatorError.class, () -> {
            csl.getObject(factC.getClass()); //pq necessita un String.class->*string*
        });

        csl.setConstant(String.class, "ho necessita InterfaceC");
        csl.getObject(factC.getClass()); //OK: ara ja té el String.class->*string*
    }

    @Test
    void getObjectInterfaceBTest() throws LocatorError {
        Factory factB = new FactoryB1();
        csl.setService(factB.getClass(),factB);

        assertThrows(LocatorError.class, () -> {
            csl.getObject(factB.getClass()); //pq necessita un InterfaceD.class->*interfaceD*
        });

        csl.setConstant(Integer.class, 455);

        Factory factD = new FactoryD1();
        csl.setService(factD.getClass(),factD);
        InterfaceD interfD = (InterfaceD) csl.getObject(factD.getClass());

        csl.setConstant(InterfaceD.class,interfD); //ara ja té el InterfaceD.class->*interfaceD*

        csl.getObject(factB.getClass());    //OK
        csl.getObject(FactoryB1.class);     //aquesta i l'anteior són el mateix
    }


    @Test
    void getObjectInterfaceATest() throws LocatorError {
        Factory factA = new FactoryA1();
        csl.setService(factA.getClass(),factA);

        assertThrows(LocatorError.class, () -> {
            csl.getObject(factA.getClass()); //pq necessita un InterfaceB.class->*interfaceB*
            //            i un InterfaceC.class->*interfaceC*

        });


        Factory factC = new FactoryC1();
        csl.setService(factC.getClass(),factC);
        csl.setConstant(String.class,"necessari per fer InterfaceC");

        InterfaceC interfC = (InterfaceC) csl.getObject(factC.getClass());
        csl.setConstant(InterfaceC.class,interfC);  //ara ja té el InterfaceC.class->*interfaceC*


        Factory factD = new FactoryD1();
        csl.setService(factD.getClass(),factD);
        csl.setConstant(Integer.class, 455);

        InterfaceD interfD = (InterfaceD) csl.getObject(factD.getClass());
        csl.setConstant(InterfaceD.class,interfD);

        Factory factB = new FactoryB1();
        csl.setService(factB.getClass(),factB);
        InterfaceB interfB = (InterfaceB) csl.getObject(factB.getClass());
        csl.setConstant(InterfaceB.class,interfB);  //ara ja té el InterfaceB.class->*interfaceB*


        csl.getObject(factA.getClass()); //OK: perquè ja té un InterfaceB.class->*interfaceB*
        //                     i un InterfaceC.class->*interfaceC*

    }


}
