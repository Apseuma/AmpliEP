package servicelocator;

import Exceptions.LocatorError;
import Implementations.ImplementationB1;
import Interfaces.InterfaceB;
import Interfaces.InterfaceD;

public class FactoryB1 implements Factory {
    public InterfaceB create (ServiceLocator sl) throws LocatorError {
        try	{
            InterfaceD d = (InterfaceD)	sl.getObject("D");
            return new ImplementationB1(d);
        } catch (ClassCastException ex)	{
            throw new LocatorError("BBBBB");
        }
    }
}
