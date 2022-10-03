package gov.sir.core.negocio.modelo.util;

import org.apache.log4j.Logger;

/**
 * @author Pablo Quintana
 * Clase que facilita el uso del log mediante patr�n singleton
 */
public class Log {
	
	private static Log log;
	private static Logger logger;
	
	/**
	 * 
	 *
	 */
	private Log() {
		logger=Logger.getLogger("SIR");
	}

	public static Log getInstance(){
		if(log==null)
			log = new Log();
		return log;	
	}
	
	/**
	 * 
	 * @param instancia
	 * @param objeto
	 * @param throwable
	 * Se utiliza para escribir mensajes de depuraci�n
	 */
	public void debug(final Object instancia, final Object objeto, final Throwable throwable){
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin ="[" + instancia + "] " + strObject;
		logger.debug((Object)messageFin, throwable);
	}
	
	/**
	 * @param instancia
	 * @param objeto
	 * Se utiliza para escribir mensajes de depuraci�n
	 */
	public void debug(final Object instancia, final Object objeto){
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin = "[" + instancia+"] " + strObject;
		logger.debug((Object)messageFin);
	}
	/**
	 * se utiliza para mensajes similares al modo "verbose" en otras aplicaciones
	 * @param instancia
	 * @param objeto
	 */
	public void info(final Object instancia, final Object objeto){
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin= "[" + instancia + "] " + strObject;
		logger.info((Object)messageFin);
	}
	/**
	 * se utiliza para mensajes similares al modo "verbose" en otras aplicaciones
	 * @param instancia
	 * @param objeto
	 * @param throwable
	 */
	public void info(Object instancia, final Object objeto, final Throwable throwable){
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin= "[" + instancia + "] " + strObject;
		logger.info((Object)messageFin,throwable);
	}
	/**
	 * se utiliza en mensajes de error de la aplicaci�n que se desea guardar, 
	 * estos eventos afectan al programa pero lo dejan seguir funcionando,
	 * @param instancia
	 * @param objeto
	 */
	public void error(final Object instancia, final Object objeto){
		final Throwable throwable= new Throwable();
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin= "[" + instancia + "] " + strObject;
		logger.error((Object)messageFin,throwable);
	}
	/**
	 * se utiliza en mensajes de error de la aplicaci�n que se desea guardar, 
	 * estos eventos afectan al programa pero lo dejan seguir funcionando,
	 * @param instancia
	 * @param objeto
	 * @param throwable
	 */
	public void error(final Object instancia, final Object objeto, final Throwable throwable){
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin= "[" + instancia + "] " + strObject;
		logger.error((Object)messageFin,throwable);
	}
	/**
	 * se utiliza para mensajes cr�ticos del sistema, generalmente despu�s de guardar el 
	 * mensaje el programa abortar�
	 * @param instancia
	 * @param objeto
	 */
	public void fatal(final Object instancia, final Object objeto){
		final Throwable throwable= new Throwable();
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin= "[" + instancia + "] " + strObject;
		logger.fatal((Object)messageFin,throwable);
	}
	/**
	 * se utiliza para mensajes cr�ticos del sistema, generalmente despu�s de guardar el mensaje el 
	 * programa abortar�
	 * @param instancia
	 * @param objeto
	 * @param throwable
	 */
	public void fatal(final Object instancia, final Object objeto, final Throwable throwable){
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin= "[" + instancia + "] " + strObject;
		logger.fatal((Object)messageFin,throwable);
	}
	/**
	 * se utiliza para mensajes de alerta sobre eventos que se desea mantener constancia, pero que no 
	 * afectan al correcto funcionamiento del programa.
	 * @param instancia
	 * @param objeto
	 */
	public void warn(final Object instancia, final Object objeto){
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin= "[" + instancia + "] " + strObject;
		logger.warn((Object)messageFin);
	}
	/**
	 * se utiliza para mensajes de alerta sobre eventos que se desea mantener constancia, pero que no 
	 * afectan al correcto funcionamiento del programa.
	 * @param instancia
	 * @param objeto
	 * @param throwable
	 */
	public void warn(final Object instancia, final Object objeto, final Throwable throwable){
		String strObject="";
		if(objeto!=null)
			strObject=objeto.toString();
		String messageFin= "[" + instancia + "] " + strObject;
		logger.warn((Object)messageFin,throwable);
	}
	

}

