package servicelocator;

import Exceptions.LocatorErrorException;
import Implementations.ImplementationA1;
import Interfaces.InterfaceA;
import Interfaces.InterfaceB;
import Interfaces.InterfaceC;

class	FactoryA1	implements	Factory	{
    public InterfaceA create (ServiceLocator	sl) throws LocatorErrorException {
        try	{
            InterfaceB b = (InterfaceB)	sl.getObject("B");
            InterfaceC c = (InterfaceC)	sl.getObject("C");
            return	new ImplementationA1(b,	c);
        }	catch (ClassCastException ex)	{
            throw new LocatorErrorException("AAAAAAAAAAAAAA");
        }
    }
}