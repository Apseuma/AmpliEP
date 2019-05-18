package servicelocator2;

import Exceptions.LocatorError;
import Implementations.ImplementationB1;
import Interfaces.InterfaceB;
import Interfaces.InterfaceD;

public class FactoryB1 implements Factory<InterfaceB>{
    public InterfaceB create (ServiceLocator sl) throws LocatorError {
        InterfaceD d = sl.getObject(InterfaceD.class);
        return new ImplementationB1(d);
    }
}