/*
 * Created on 27-sep-2004
*/
package gov.sir.core.eventos.registro;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * @author jfrias,dsalas
 */
public class EvnRespConfrontacion extends EvnSIRRespuesta {
	public static final String TURNO="TURNO";

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespConfrontacion(Object payload) {
		super(payload, TURNO);
	}

}
