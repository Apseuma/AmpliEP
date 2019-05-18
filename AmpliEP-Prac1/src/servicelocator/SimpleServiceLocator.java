package servicelocator;

import Exceptions.LocatorError;

import java.util.HashMap;
import java.util.Map;


public class SimpleServiceLocator implements ServiceLocator {

    private Map<String,Object> constants;
    private Map<String,Factory> services;

    public SimpleServiceLocator() {
        constants = new HashMap<String,Object>();
        services = new HashMap<String, Factory>();
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
        if (constants.containsKey(name)) {
            return constants.get(name);

        }else if (services.containsKey(name)){
            return services.get(name).create(this);

        } else {
            throw new LocatorError("This name doesn't exist.");
        }
    }
}

//segur que aquesta classe va en aquest lloc del codi??? nose

//lo que si casi segur es que té un diccionaris (clau-valor)
// on tot lo que se li registra (set) es guarda