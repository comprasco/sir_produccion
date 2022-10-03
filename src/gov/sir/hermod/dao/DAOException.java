/*
 * DAOException.java
 * Clase para el manejo de las excepciones que se presentan en los DAO.
 * Created on 9 de agosto de 2004, 16:15
 */

package gov.sir.hermod.dao;

import gov.sir.hermod.HermodException;

/**
 * Clase para el manejo de las excepciones que se presentan en los DAO.
 * @author  mrios, dlopez
 */
public class DAOException extends HermodException {
    
    /**
    * Crea una nueva instancia de <code>DAOException</code> sin ningun mensaje
    * detallado
    */
    public DAOException() {
        super();
    }

    
    /**
     * Crea una nueva instancia de <code>DAOxception</code> con el mensaje detallado
     * @param msg el mensaje detallado
     */
    public DAOException(String msg) {
        super(msg);
    }
    
    /**
     * Crea una nueva instancia de <code>DAOException</code> con el mensaje detallado,
     * y el throwable correspondiente a la excepcion.
     * @param msg el mensaje detallado
     * @param tr el throwable de la excepcion
     */
    public DAOException(String msg, Throwable tr) {
        super(msg, tr);
    }
    
    
    /**
    * Excepci�n generada cuando no se puede obtener la instancia del DAO. 
    */
    public static String DAO_NO_OBTENIDO = "No se pudo obtener el DAO solicitado";
    
    
	/**
	* Excepci�n generada cuando no se puede obtener la instancia correcta del DAO. 
	*/
    public static String DAO_INCORRECTO = "El DAO solicitado no corresponde a la implementacion";
    
    
	/**
	* Excepci�n generada cuando no se puede realizar la validaci�n de un pago.
	*/
    public static String VALIDACION_PAGO_INCORRECTA = "Validacion incorrecta del pago";
    
    
	/**
	* Excepci�n generada cuando no se puede procesar un pago.
	*/    
    public static String PAGO_INCORRECTO = "No se pudo procesar el pago";        
    
    
    
    /**
    * Excepci�n generada cuando el turno recibido como par�metro es nulo.
    */
    public static String TURNO_NULO = "El turno recibido como par�metro es nulo";

    
   /**
   * Excepci�n generada cuando no se encuentra un pago asociado con una solicitud. 
   */
   public static String PAGO_NO_ASOCIADO_SOLICITUD = "No se encontr� el pago asociado con la solicitud.";
   
    
   /**
   * Excepci�n generada cuando no se encuentra una solicitud persistente con el identificador dado. 
   */
   public static String SOLICITUD_PERSISTENTE_NO_ENCONTRADA = "No se encontr� una solicitud persistente con el identificador dado";
    
    
}