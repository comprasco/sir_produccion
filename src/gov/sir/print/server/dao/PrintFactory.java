/*
 * Created on 27-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.print.server.dao;


import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.print.server.PrintJobsProperties;

/**
 * Esta clase implementa el patrón de fábrica abstracta. Una
 * fábrica concreta permite obtener objetos que proveen los servicios
 * de registro
 *
 * Dentro de la arquitectura de SIR, este servicio permite administrar,
 * consultar y actualizar de manera segura sesiones de impresión
 *
 * @author Ing. Fabián Ceballos Acosta
 */
public abstract class PrintFactory {
    
    /**
    * Obtiene la implementación de la fábrica
    * @return la fábrica concreta
    */
    public static PrintFactory getFactory() throws FactoryException {
        PrintFactory factory = null;
        PrintJobsProperties prop = PrintJobsProperties.getInstancia();
        String clase = prop.getProperty(PrintJobsProperties.PRINT_FACTORY);
        
        try {
            factory = (PrintFactory) Class.forName(clase).newInstance();
            Log.getInstance().info(PrintFactory.class,"Instanciando clase: "+clase);
            return factory;
            
        } catch (Exception e) {
        	Log.getInstance().info(PrintFactory.class,"Error cargando la clase " + clase + ":" +
                e.getMessage());
			throw new FactoryException("No se pudo cargar la fábrica "+clase);
        }
    }

    /**
    * Devuelve un <code>ImpresionDAO</code>
    * @return un objeto que implementa la interfaz <code>ImpresionDAO</code>
    */
    public abstract ImpresionDAO getImpresionDAO();


	
}
