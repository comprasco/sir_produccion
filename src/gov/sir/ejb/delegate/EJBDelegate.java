/*
 * Created on 03-oct-2005
 */
package gov.sir.ejb.delegate;

import gov.sir.core.is21.DynamicProxy;
import gov.sir.core.is21.DynamicProxyException;
import java.util.Hashtable;
import org.auriga.core.modelo.Servicio;
import org.auriga.smart.mapas.InformacionServicio;
import org.auriga.smart.servicio.ServiceLocatorDelegate;
import org.auriga.smart.servicio.ServiceLocatorException;


/**
 * @author Ing. Germán Villalba F.
 */
public class EJBDelegate implements ServiceLocatorDelegate {
    
    private static final String INTERFACE_HOME 		= "home";
    private static final String JNDI_NAME 			= "jndi";
    private static final String BUSSINESS_INTERFACE = "interface";
    private static final String INTERFACE_REMOTE 	= "remote";

    /** 
     * @see org.auriga.smart.servicio.ServiceLocatorDelegate#getService(org.auriga.smart.mapas.InformacionServicio)
     */
    public Object getService(InformacionServicio info) throws ServiceLocatorException {
        DynamicProxy dynProxy = DynamicProxy.getInstance();
        Servicio servicio = null;
        
        ClassLoader classLoader = EJBDelegate.class.getClassLoader();
        
		String strHome 			= info.getDelegate().getParametro(INTERFACE_HOME);
		String jndiName 		= info.getDelegate().getParametro(JNDI_NAME);
		String strInterface 	= info.getDelegate().getParametro(BUSSINESS_INTERFACE);
		String strEjbInterface 	= info.getDelegate().getParametro(INTERFACE_REMOTE);
		
		Hashtable props = new Hashtable();
				
		try {
		    servicio = (Servicio) dynProxy.buildService(classLoader,props,strHome,jndiName,strInterface,strEjbInterface);
		} catch (DynamicProxyException e1) {
			e1.printStackTrace();
			throw new ServiceLocatorException("ERROR OBTENIENDO EL SERVICIO EJB");
		}
        
        
        return servicio;
    }
    
 


}
