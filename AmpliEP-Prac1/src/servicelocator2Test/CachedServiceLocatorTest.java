package servicelocator2Test;

import Exceptions.LocatorError;
import Interfaces.InterfaceC;
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
            csl.setService(factB2.getClass(), factB2);
        });

        csl.setService(factD.getClass(), factD);
    }


    @Test
    void setConstantTest() throws LocatorError {
        String str = "holaaa";
        int integ = 55;
        int integ2 = 1899;
        Factory factC = new FactoryC1();


        csl.setConstant(String.class, str);
        csl.setConstant(Integer.class, integ);

        assertThrows(LocatorError.class, () -> {
            csl.setConstant(Integer.class, integ2);
        });

        //ssl.setConstant(factC.getClass(),factC); pq no??

    }


    @Test
    void getInexistentObject() throws LocatorError {
        assertThrows(LocatorError.class, () -> {
            csl.getObject(String.class);
        });

        Factory factA = new FactoryA1();
        assertThrows(LocatorError.class, () -> {
            csl.getObject(factA.getClass());
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
        InterfaceC interfC2 = (InterfaceC) csl.getObject(FactoryC1.class);  //es la mateixa class
        assertSame(interfC1, interfC2);

    }
}
