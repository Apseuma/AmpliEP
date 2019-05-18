package servicelocator;

import Exceptions.LocatorErrorException;

import java.util.HashMap;
import java.util.Map;


public class SimpleServiceLocator implements ServiceLocator {

    private Map<String,Object> services;

    public SimpleServiceLocator() {
        services = new HashMap<String, Object>();
    }

    void setService(String name, Factory factory) throws LocatorErrorException {
        if (services.containsKey(name)){
            throw LocatorErrorException; //??
        } else{
            services.put(name, factory);
            //algo mes?
        }

    }

    void setConstant(String name, Object value) throws LocatorErrorException{

    }

    Object getObject(String name) throws LocatorErrorException{


    }


}

//segur que aquesta classe va en aquest lloc del codi??? nose

//lo que si casi segur es que té un diccionaris (clau-valor)
// on tot lo que se li registra (set) es guarda