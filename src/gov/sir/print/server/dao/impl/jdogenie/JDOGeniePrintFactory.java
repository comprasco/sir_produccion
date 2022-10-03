/*
 * Created on 15-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.print.server.dao.impl.jdogenie;


import gov.sir.print.server.dao.ImpresionDAO;
import gov.sir.print.server.dao.PrintFactory;


/**
 * Implementación de JDOGenie para PrintFactory
 * @author fceballos
 */
public class JDOGeniePrintFactory extends PrintFactory {
    /**
       * Devuelve un <code>ImpresionDAO</code>
       * @return un objeto que implementa la interfaz <code>ImpresionDAO</code>
       */
    public ImpresionDAO getImpresionDAO() {
        return new JDOGenieImpresionDAO();
    }

	  
}
