package servicelocator;

import Exceptions.LocatorError;
import Implementations.ImplementationC1;
import Interfaces.InterfaceC;

public class FactoryC1 implements Factory {
    public InterfaceC create (ServiceLocator sl) throws LocatorError {
        try	{
            String c = (String) sl.getObject("constC");
            return new ImplementationC1(c);
        } catch (ClassCastException ex)	{
            throw new LocatorError("CCCCCC");
        }
    }
}
