package servicelocatorTest;

import Exceptions.LocatorError;
import Interfaces.InterfaceB;
import Interfaces.InterfaceD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicelocator.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SimpleServiceLocatorTest {
    private SimpleServiceLocator ssl;

    @BeforeEach
    void setUp() {
        ssl = new SimpleServiceLocator();
    }

    @Test
    void setServiceTest() throws LocatorError {
        ssl.setService("service1", new FactoryB1());

        //both factories with the same key -> LocatorError
        assertThrows(LocatorError.class, () -> {
            ssl.setService("service1", new FactoryC1());
        });

        //same factories with different keys
        ssl.setService("service2", new FactoryB1());

    }


    @Test
    void setConstantTest() throws LocatorError {
        ssl.setConstant("constant1", 34);
        ssl.setConstant("constant2", "hello");

        //two constants with the same key -> LocatorError
        assertThrows(LocatorError.class, () -> {
            ssl.setConstant("constant1", "hey, I'm a constant");
        });

        //same constants with different keys ->OK
        ssl.setConstant("constant3", 34);

        //explicar??????? factory es object so...
        ssl.setConstant("factory", new FactoryB1());

    }

    @Test
    void setServiceAndConstantTest() throws LocatorError {
        ssl.setConstant("A", 34);

        //two constants/services with the same key -> LocatorError
        assertThrows(LocatorError.class, () -> {
            ssl.setService("A", new FactoryD1());
        });


        ssl.setService("B", new FactoryC1());

        //two constants/services with the same key -> LocatorError
        assertThrows(LocatorError.class, () -> {
            ssl.setConstant("B", 89);
        });


    }


    @Test
    void getObjectTest() throws LocatorError {

        ssl.setConstant("D", 34);
        ssl.setConstant("C", "valor de InterfaceC");
        ssl.setService("B", new FactoryB1());
        ssl.setService("A", new FactoryA1());

        assertSame(34, ssl.getObject("D")); //OK, pq es un int
        assertSame("valor de InterfaceC", ssl.getObject("C")); //OK, pq es una string

        assertThrows(LocatorError.class, () -> {
            ssl.getObject("B"); //pq D es una constant, no una interface
        });

        assertThrows(LocatorError.class, () -> {
            ssl.getObject("A"); //pq necessita B, que llença excepció
        });


        //GETOBJECT DE SERVICES
        ssl.setService("factD", new FactoryD1());
        InterfaceD interfD1 = (InterfaceD) ssl.getObject("factD");
        InterfaceD interfD2 = (InterfaceD) ssl.getObject("factD");
        assertNotSame(interfD1, interfD2);  //pq getobject crea nous objectes si son via setService

        //GETOBJECT DE CONSTANTS
        Object valD1 = ssl.getObject("D");
        Object valD2 = ssl.getObject("D");
        assertSame(valD1, valD2); //pq getobject retorna directe si es via setConstant


        //EXCEPCIÓ
        assertThrows(LocatorError.class, () -> {
            ssl.getObject("inventat");
        });


        //FALTA LO QUE HI HA APUNTAT DE VIASERVCEICE I TAL

    }


    @Test
    void getObjectInterfConstantTest() throws LocatorError {

        ssl.setService("servei", new FactoryD1());
        InterfaceD interfD1 = (InterfaceD) ssl.getObject("servei");
        InterfaceD interfD2 = (InterfaceD) ssl.getObject("servei");
        assertNotSame(interfD1, interfD2);  //pq getobject crea nous objectes si son via setService

        ssl.setConstant("constant", new FactoryD1());
        Factory constFactory = (FactoryD1) ssl.getObject("constant");
        Factory constFactory2 = (FactoryD1) ssl.getObject("constant");
        assertSame(constFactory, constFactory2);  //pq getobject crea nous objectes si son via setService
    }

}