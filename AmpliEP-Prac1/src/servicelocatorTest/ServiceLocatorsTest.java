/*package servicelocatorTest;

import Exceptions.LocatorError;
import Interfaces.InterfaceC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceLocatorsTest {
    class SimpleServiceLocatorTest{
        private ServiceLocators[2] ssl;

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
        void getObjectComplexTest() throws LocatorError {

            ssl.setConstant("constD", 34);
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
            ssl.setConstant("constD", 34);
            ssl.setConstant("constC", "valor de InterfaceC");

            ssl.setService("factD",new FactoryD1());
            Object interfD = ssl.getObject("factD");
            ssl.setConstant("D",interfD);

            ssl.setService("factC",new FactoryC1());
            Object interfC = ssl.getObject("factC");
            ssl.setConstant("C",interfC);

            ssl.setService("factB",new FactoryB1());
            Object interfB = ssl.getObject("factB");
            ssl.setConstant("B",interfB);

            ssl.setService("factA",new FactoryA1());

        }
*/