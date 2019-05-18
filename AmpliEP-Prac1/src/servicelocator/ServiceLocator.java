package servicelocator;

import Exceptions.LocatorErrorException;

public interface ServiceLocator {
    void setService(String name, Factory factory) throws LocatorErrorException;
    void setConstant(String name, Object value) throws LocatorErrorException;
    Object getObject(String name) throws LocatorErrorException;

}
