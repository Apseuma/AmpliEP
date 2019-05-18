package servicelocator;

import Exceptions.LocatorErrorException;

public interface Factory {
    Object create (ServiceLocator sl) throws LocatorErrorException;
}
