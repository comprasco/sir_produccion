/*
 * Created on Apr 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.sir.core.is21;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author gardila
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProxyDirectMethodExecution implements InvocationHandler, IProxyObject {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(this.concreteImplementation, args);
        } 
        catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    public void setConcreteObject(Object concreteImplementation) {
        this.concreteImplementation = concreteImplementation;
    }
    
    private Object concreteImplementation = null;
}
