
package gov.sir.fenrir.dao;

/**
 * Excepción arrojada cuando ocurren errores de conexión en el servicio
 * @author jfrias
 *
 */
public class ErrorConexionException extends Exception {

	/**
	 * Identifica cuando la excepción ocurre por la no existencia del pool de conexiones
	 */
	public static final String POOL_NO_DISPONIBLE = "No hay pool de conexiones disponible";
	
	/**
	 * Identifica cuando la excepción ocurre por la no disponibilidad de conexiones
	 */
	public static final String CONEXION_NO_DISPONIBLE = "No hay conexión disponible";

	/**
	 * 
	 */
	public ErrorConexionException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ErrorConexionException(String arg0) {
		super(arg0);
	}
	
	/**
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public ErrorConexionException(String arg0, Throwable arg1) {
		   super(arg0, arg1);
	   }
	
	

}
