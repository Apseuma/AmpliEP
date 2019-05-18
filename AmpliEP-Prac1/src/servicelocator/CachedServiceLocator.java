package servicelocator;

import Exceptions.LocatorErrorException;

import java.util.HashMap;
import java.util.Map;

public class CachedServiceLocator implements ServiceLocator {

    private Map<String,Object> services;

    public CachedServiceLocator() {
        services = new HashMap<String, Object>();
    }

    public void setService(String name, Factory factory) throws LocatorErrorException {
        if (services.containsKey(name)){
            throw new LocatorErrorException("This name is already used, use another one.");
        } else{
            services.put(name, factory);
        }
    }

    public void setConstant(String name, Object value) throws LocatorErrorException{
        if (services.containsKey(name)){
            throw new LocatorErrorException("This name is already used, use another one.");
        } else{
            services.put(name, value);
        }
    }

    public Object getObject(String name) throws LocatorErrorException{
        if (services.containsKey(name)){
            return services.get(name);
        } else{
            throw new LocatorErrorException("This name doesn't exist.");
        }
    }
}

//segur que esta classe va en aquest lloc del codi???