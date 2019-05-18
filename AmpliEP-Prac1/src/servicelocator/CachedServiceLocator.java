package servicelocator;

import Exceptions.LocatorError;

import java.util.HashMap;
import java.util.Map;

public class CachedServiceLocator implements ServiceLocator {

    private Map<String,Object> services;

    public CachedServiceLocator() {
        services = new HashMap<String, Object>();
    }

    public void setService(String name, Factory factory) throws LocatorError {
        if (services.containsKey(name)){
            throw new LocatorError("This name is already used, use another one.");
        } else{
            services.put(name, factory);
        }
    }

    public void setConstant(String name, Object value) throws LocatorError {
        if (services.containsKey(name)){
            throw new LocatorError("This name is already used, use another one.");
        } else{
            services.put(name, value);
        }
    }

    public Object getObject(String name) throws LocatorError {
        if (services.containsKey(name)){
            return services.get(name);
        } else{
            throw new LocatorError("This name doesn't exist.");
        }
    }
}

//segur que esta classe va en aquest lloc del codi???