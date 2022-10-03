package gov.sir.core.negocio.modelo.constantes;

/**
 * Clase para el manejo de las constantes necesarias para el 
 * avance de turnos. 
 * @author dlopez
 * 
 */
public class CAvanzarTurno {


	/**
	* Constante para avanzar turno normal. 
	*/
	public final static int  AVANZAR_NORMAL = 0;
	
	/**
	* Constante para avanzar turno push. 
	*/
	public final static int  AVANZAR_PUSH = 1;
	
	
	/**
	* Constante para avanzar turno pop. 
	*/
	public final static int  AVANZAR_POP = 2;
	
	
	/**
	* Constante para avanzar turno y delegar. 
	*/
	public final static int  AVANZAR_ENTREGA = 3;
	
	
	/**
	* Constante para avanzar turno a un usuario cualquiera, quitando marca 
	* de usuario destino.
	*/
	public final static int  AVANZAR_CUALQUIERA = 4;
	
	
	/**
	* Constante para avanzar turno a un usuario cualquiera, quitando 
	* las marcas del último avanzar push.
	*/
	public final static int  AVANZAR_ELIMINANDO_PUSH = 5;
	
	
	/**
	* Constante para avanzar turno a un usuario específico y su estación, 
	* estableciendo como modo de bloqueo Delegar Usuario.
	*/
	public final static int  AVANZAR_USUARIO = 6;
	
	
	/**
	* Constante para recuperar lista de validaciones de certificados masivos.
	*/
	public final static String LISTA_VALIDACIONES_MASIVOS = "LISTA_VALIDACIONES_MASIVOS";
}
