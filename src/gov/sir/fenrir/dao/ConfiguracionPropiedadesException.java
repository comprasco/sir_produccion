
package gov.sir.fenrir.dao;

/**
 * Excepción utilizada para notificar el error en la configuración de los parámetros del servicio
  * @author jfrias
 */
public class ConfiguracionPropiedadesException extends Exception {

	public static final String CONFIGURACION_INVALIDA = "No existe definición completa de llaves en el archivo .properties";

	/**
	 * 
	 */
	public ConfiguracionPropiedadesException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ConfiguracionPropiedadesException(String arg0) {
		super(arg0);
	}
	
	/**
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public ConfiguracionPropiedadesException(String arg0, Throwable arg1) {
		   super(arg0, arg1);
	   }

}
