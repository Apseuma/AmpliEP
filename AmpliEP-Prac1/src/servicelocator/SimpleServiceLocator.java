package servicelocator;

import Exceptions.LocatorErrorException;

import java.util.HashMap;
import java.util.Map;


public class SimpleServiceLocator implements ServiceLocator {

    private Map<String,Object> services;

    public SimpleServiceLocator() {
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
            Object obj = services.get(name);
            if(obj instanceof Factory){
                return ((Factory) obj).create(this);    //Ja sabem que es tracta d'un objecte de tipus factory però ho hem de castejar per a tractar-ho com a tal
            }else{
                return obj;
            }
        } else{
            throw new LocatorErrorException("This name doesn't exist.");
        }
    }
}

//segur que aquesta classe va en aquest lloc del codi??? nose

//lo que si casi segur es que té un diccionaris (clau-valor)
// on tot lo que se li registra (set) es guarda