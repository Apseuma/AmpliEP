package servicelocator;

import Exceptions.LocatorErrorException;
import Implementations.ImplementationD1;
import Interfaces.InterfaceD;

public class FactoryD1 implements Factory {
    public InterfaceD create (ServiceLocator sl) throws LocatorErrorException {
        try	{
            int d = (int) sl.getObject("d");
            return new ImplementationD1(d);
        }	catch (ClassCastException ex)	{
            throw new LocatorErrorException("DDDDDDDDDD");
        }
    }
}
