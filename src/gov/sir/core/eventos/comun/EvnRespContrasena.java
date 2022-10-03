package gov.sir.core.eventos.comun;

import java.util.Hashtable;

public class EvnRespContrasena extends EvnSIRRespuesta{

	/**Esta constante se utiliza  para identificar el evento de listado de los objeto oplookup code asociados a un oplookup type */
	public static final String LISTADO_LOOKUP_CODES_MESA_AYUDA_OK = "LISTADO_LOOKUP_CODES_MESA_AYUDA_OK";
	
	private Hashtable ht;
	
	/**
	 * @param payload
	 */
	public EvnRespContrasena(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespContrasena(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}
	
	public Hashtable getHt() {
		return ht;
	}

	public void setHt(Hashtable ht) {
		this.ht = ht;
	}
}
