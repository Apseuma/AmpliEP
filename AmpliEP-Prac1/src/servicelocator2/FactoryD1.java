package servicelocator2;

import Exceptions.LocatorError;
import Implementations.ImplementationD1;
import Interfaces.InterfaceD;

public class FactoryD1 implements Factory<InterfaceD> {
    public InterfaceD create (ServiceLocator sl) throws LocatorError {
        int d = sl.getObject(Integer.class);
        return new ImplementationD1(d);
    }
}
