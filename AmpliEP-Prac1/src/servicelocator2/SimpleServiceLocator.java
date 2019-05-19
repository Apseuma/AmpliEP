package servicelocator2;

import Exceptions.LocatorError;

import java.util.HashMap;
import java.util.Map;

public class SimpleServiceLocator implements ServiceLocator {
    private Map<Class<?>,Object> services;
    private Map<Class<?>,Object> constants;

    public SimpleServiceLocator(){
        services = new HashMap<Class<?>, Object>();
        constants = new HashMap<Class<?>, Object>();
    }

    @Override
    public <T> void setService(Class<T> klass, Factory<T> factory) throws LocatorError {
        if (services.containsKey(klass) || constants.containsKey(klass)){
            throw new LocatorError("This name is already used, use another one.");
        } else{
            services.put(klass, factory);
        }
    }

    @Override
    public <T> void setConstant(Class<T> klass, T value) throws LocatorError {
        if (services.containsKey(klass) || constants.containsKey(klass)){
            throw new LocatorError("This name is already used, use another one.");
        } else{
            constants.put(klass, value);
        }
    }

    @Override
    public <T> T getObject(Class<T> klass) throws LocatorError {
        if (constants.containsKey(klass)) {
            return (T) constants.get(klass);
        }else if (services.containsKey(klass)){
            return ((Factory<T>) services.get(klass)).create(this);
        } else {
            throw new LocatorError("This name doesn't exist.");
        }
    }
}
