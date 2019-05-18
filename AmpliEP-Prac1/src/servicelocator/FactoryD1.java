package servicelocator;

import Exceptions.LocatorError;
import Implementations.ImplementationD1;
import Interfaces.InterfaceD;

public class FactoryD1 implements Factory {
    public InterfaceD create (ServiceLocator sl) throws LocatorError {
        try	{
            int d = (int) sl.getObject("D");
            return new ImplementationD1(d);
        } catch (ClassCastException ex) {
            throw new LocatorError("DDDDDDDDDD");
        }
    }
}
