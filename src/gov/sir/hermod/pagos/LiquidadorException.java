/*
 * LiquidadorException.java
 * Clase para el manejo de las excepciones que ocurren durante el proceso
 * de liquidación.
 * Created on 6 de agosto de 2004, 17:44
 */

package gov.sir.hermod.pagos;

import gov.sir.hermod.HermodException;

/**
 * Clase para el manejo de las excepcions que ocurren durante el proceso de
 * Liquidación. 
 * @author  mrios, dlopez
 */
public class LiquidadorException extends HermodException {
	
	/**
	* Mensaje asociado a la excepción que ocurre cuando no es posible obtener
	* el liquidador.  
	*/
	public static String LIQUIDADOR_NO_OBTENIDO = "No fue posible obtener el liquidador";
    
    
	/**
	 * Mensaje asociado a la excepción que ocurre cuando no es posible obtener
	 * el valor liquidado.  
	 */
	public static String VALOR_NO_OBTENIDO = "No fue posible obtener el valor";
    
    
    /**
    *  Crea una nueva instancia de la clase LiquidadorException
    */
    public LiquidadorException() {
        super();
    }
    
    
	/**
    *  Crea una nueva instancia de la clase LiquidadorException 
    *  con un mensaje asociado.
    *  @param msg El mensaje asociado a la excepción. 
	*/
    public LiquidadorException(String msg) {
        super(msg);
    }
    
    
	/**
	*  Crea una nueva instancia de la clase LiquidadorException 
	*  con un mensaje y un throwable asociado.
	*  @param msg El mensaje asociado a la excepción. 
	*  @param tr El throwable asociado a la excepción. 
	*/
    public LiquidadorException(String msg, Throwable tr) {
        super(msg, tr);
    }


}