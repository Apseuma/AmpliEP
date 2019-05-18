package servicelocator2;

import Exceptions.LocatorError;

public interface Factory<T> {
    T create (ServiceLocator sl) throws LocatorError;
}

