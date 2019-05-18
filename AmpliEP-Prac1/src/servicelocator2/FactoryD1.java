package servicelocator2;

import Exceptions.LocatorError;
import Implementations.ImplementationD1;
import Interfaces.InterfaceD;

public class FactoryD1 implements Factory<InterfaceD> {
    public InterfaceD create (ServiceLocator sl) throws LocatorError {
        int d = (int) sl.getObject(InterfaceD.class);
        return new ImplementationD1(d);
    }
}
