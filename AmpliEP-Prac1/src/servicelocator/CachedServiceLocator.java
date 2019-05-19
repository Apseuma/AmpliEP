package servicelocator;

import Exceptions.LocatorError;

import java.util.HashMap;
import java.util.Map;

public class CachedServiceLocator implements ServiceLocator {
    private Map<String,Object> constants;
    private Map<String,Factory> services;
    private Map<String,Object> created;

    public CachedServiceLocator() {
        constants = new HashMap<String,Object>();
        services = new HashMap<String, Factory>();
        created = new HashMap<String,Object>();
    }

    public void setService(String name, Factory factory) throws LocatorError {
        if (services.containsKey(name) || constants.containsKey(name)){
            throw new LocatorError("This name is already used, use another one.");
        } else{
            services.put(name, factory);
        }
    }

    public void setConstant(String name, Object value) throws LocatorError {
        if (services.containsKey(name) || constants.containsKey(name)){
            throw new LocatorError("This name is already used, use another one.");
        } else{
            constants.put(name, value);
        }
    }

    public Object getObject(String name) throws LocatorError {
        if (created.containsKey(name)){
            return created.get(name);
        }
         else if (constants.containsKey(name)) {
            return constants.get(name);
        }
         else if (services.containsKey(name)){
            Object serv = services.get(name).create(this);
            created.put(name,serv);
            return serv;
        }
         else {
            throw new LocatorError("This name doesn't exist.");
        }
    }
}