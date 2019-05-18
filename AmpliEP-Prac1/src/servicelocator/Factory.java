package servicelocator;

import Exceptions.LocatorError;

public interface Factory {
    Object create (ServiceLocator sl) throws LocatorError;
}
