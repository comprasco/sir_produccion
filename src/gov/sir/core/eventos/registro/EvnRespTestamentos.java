package gov.sir.core.eventos.registro;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * relacionadas con la inscripci�n de testamentos.
 * @author ppabon
 */
public class EvnRespTestamentos extends EvnSIRRespuesta {

	// SIR 57 R
	/** Constante que identifica la acci�n de incribir el testamento*/
	public static final String REGISTRAR = "REGISTRAR";
	
	/** Constante que identifica la acci�n de devolver el  testamento*/
	public static final String DEVOLVER = "DEVOLVER";
	
	
	/** Constante que identifica la acci�n devolver el testamento a correcci�n de encabezado*/
	public static final String CORRECCION_ENCABEZADO="CORRECCION_ENCABEZADO";
	
	/** Constante para identificar la accion devolver a confrontacion**/
	public static final String DEVOLVER_A_CONFRONTACION="DEVOLVER_A_CONFRONTACION";

	private int idImprimible = 0;

	/**
	 * @param payload
	 */
	public EvnRespTestamentos(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespTestamentos(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespTestamentos(Object payload, String tipoEvento, int idImprimible) {
		super(payload, tipoEvento);
		this.idImprimible = idImprimible;
	}


	/**
	 * @return
	 */
	public int getIdImprimible() {
		return idImprimible;
	}

}
