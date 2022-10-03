/*
 * LiquidadorException.java
 * Created on 6 de agosto de 2004, 17:44
 * Clase que se encarga del manejo de las excepciones ocurridas durante
 * el proceso de liquidaci�n. 
 */

package gov.sir.hermod.pagos;

import gov.sir.hermod.HermodException;

/**
 * Clase que se encarga del manejo de las excepciones ocurridas durante
 * el proceso de liquidaci�n. 
 * @author  dlopez, mrios
 */
public class LiquidarException extends HermodException {
    
    
    /**
    * Crea una nueva instancia de la clase LiquidarException
    */
    public LiquidarException() {
        super();
    }
    
    
	/**
	* Crea una nueva instancia de la clase LiquidarException.
	* @param msg El mensaje asociado con la excepci�n. 
	*/
    public LiquidarException(String msg) {
        super(msg);
    }
    
    
	/**
	* Crea una nueva instancia de la clase LiquidarException.
	* @param msg El mensaje asociado con la excepci�n. 
	* @param tr El Throwable asociado con la excepci�n. 
	*/
    public LiquidarException(String msg, Throwable tr) {
        super(msg, tr);
    }
    
    /**
    * Constante que se utiliza para informar que no fue posible la realizaci�n
    * de la liquidaci�n. 
    */
    public static String LIQUIDACION_NO_REALIZADA = "No fue posible realizar la liquidacion";
}