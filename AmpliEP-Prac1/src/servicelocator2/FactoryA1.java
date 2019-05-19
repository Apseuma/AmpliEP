package servicelocator2;

import Exceptions.LocatorError;
import Implementations.ImplementationA1;
import Interfaces.InterfaceA;
import Interfaces.InterfaceB;
import Interfaces.InterfaceC;

public class FactoryA1	implements	Factory<InterfaceA>	{
    public InterfaceA create (ServiceLocator sl) throws LocatorError {
        InterfaceB b = sl.getObject(InterfaceB.class);
        InterfaceC c = sl.getObject(InterfaceC.class);
        return new ImplementationA1(b, c);
    }
}
