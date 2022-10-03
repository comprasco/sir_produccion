package gov.sir.core.eventos.comun;

import java.util.Hashtable;

import org.auriga.smart.eventos.SoporteEventoRespuesta;

/**
 * Evento base de respuesta de la capa de negocio.
 * Los eventos de respuesta son responsables de enviar a la capa
 * externa (web, rmi, otros...) el resultado de una operacion o
 * transaccion ocurrida en la capa de negocio. Por definicion
 * son objetos inmutables.
 * @author Daniel Salas
 * @author Diego Cantor
 */
public class EvnSIRRespuesta extends SoporteEventoRespuesta {

	private String tipoEvento;

	/**
	 * Tabla de hashing para guardar parametros opcionales del
	 * evento de respuesta.
	 */
	private Hashtable parametros = null;

	/**
	     * Constructor utilizado para enviar un objeto generico como respuesta
	     * @param payload un objeto generico que sera enviado como respuesta
	     */
	public EvnSIRRespuesta(Object payload) {
		super(payload);
	}

	/**
	 * Constructor utilizado cuando es necesario indicar a la
	 * capa externa el tipo de evento de respuesta. Esta
	 * información puede ayudar a la capa externa a procesar
	 * mas eficientemente la informacion recibida en lugar
	 * de hacer validaciones complicadas. Por ejemplo, el tipo
	 * de evento puede decirle a la capa web que hacer con
	 * la informacion recibida.
	 * @param payload objeto de respuesta
	 * @param tipoEvento el tipo de evento de respuesta
	 */
	public EvnSIRRespuesta(Object payload, String tipoEvento) {
		this(payload);
		this.tipoEvento = tipoEvento;
	}

	/**
	 * Devuelve el tipo de evento de respuesta
	 * @return el tipo de evento de respuesta o null si no existe un tipo para este evento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * @return
	 */
	public Hashtable getParametros() {
		return parametros;
	}

	/**
	 * @param hashtable
	 */
	public void setParametros(Hashtable hashtable) {
		parametros = hashtable;
	} 

	/**
	 * @param string
	 */
	public void setTipoEvento(String string) {
		tipoEvento = string;
	}

}
