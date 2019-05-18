package servicelocator;

import Exceptions.LocatorErrorException;
import Implementations.ImplementationC1;
import Interfaces.InterfaceC;

public class FactoryC1 implements Factory {
    public InterfaceC create (ServiceLocator sl) throws LocatorErrorException {
        try	{
            String c = (String) sl.getObject("C");
            return new ImplementationC1(c);
        }	catch (ClassCastException ex)	{
            throw new LocatorErrorException("CCCCCC");
        }
    }
}
