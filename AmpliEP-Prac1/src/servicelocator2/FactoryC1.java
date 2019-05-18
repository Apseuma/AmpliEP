package servicelocator2;

import Exceptions.LocatorError;
import Implementations.ImplementationC1;
import Interfaces.InterfaceC;

public class FactoryC1 implements Factory<InterfaceC>{
    public InterfaceC create (ServiceLocator sl) throws LocatorError {
        String c = sl.getObject(String.class);
        return new ImplementationC1(c);
    }
}
