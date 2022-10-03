/*
 * Created on 10-dic-2004
*/
package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * @author jfrias
*/
public class EvnRespTurnoManualRegistro extends EvnSIRRespuesta {

        // identificadores de evento para edicion de turno vinculado ----------------------
        public static final String REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK
          = "REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK";
        public static final String REGISTRO_VINCULARTURNO_DELITEM__EVENTRESP_OK
          = "REGISTRO_VINCULARTURNO_DELITEM__EVENTRESP_OK";
        // ----------------------------------------------------------------------------------




	/** Constante para acción Agregar Acto */
	public static final String AGREGAR_ACTO="AGREGAR_ACTO";

    /** Constante para acción buscar solicitud */
    public static final String BUSCAR_SOLICITUD = "BUSCAR_SOLICITUD";

	/** Constante para acción buscar solicitud para edición*/
	public static final String BUSCAR_SOLICITUD_EDICION = "BUSCAR_SOLICITUD_EDICION";

    /** Constante para acción incrementar secuencia del recibo */
    public static final String INCREMENTAR_SECUENCIAL_RECIBO = "INCREMENTAR_SECUENCIAL_RECIBO";

    /** Constante para acción de creación de turno */
    public static final String CREAR_TURNO = "CREAR_TURNO";

    /** Constante para acción validar solicitud */
    public static final String VALIDAR_SOLICITUD = "VALIDAR_SOLICITUD";

    private long valorSecuencial;

    protected gov.sir.core.negocio.modelo.SolicitudVinculada solicitudVinculada;
    
	/**
	 * Determina si el usuario que hace la liquidación es cajero, para evitar hacer otro paso,
	 * entonces el mismo usuario registra el pago.
	 */
	private Boolean esCajeroRegistro;
    


	/**
	 * @param payload
	 */
	public EvnRespTurnoManualRegistro(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespTurnoManualRegistro(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

    /**
     * @param valorSecuencial
     */
    public void setValorSecuencial(long valorSecuencial) {
        this.valorSecuencial= valorSecuencial;
    }

    public void setSolicitudVinculada( gov.sir.core.negocio.modelo.SolicitudVinculada solicitudVinculada ) {
        this.solicitudVinculada = solicitudVinculada;
    }

    public gov.sir.core.negocio.modelo.SolicitudVinculada getSolicitudVinculada() {
        return solicitudVinculada;
    }

    /**
     * @return valorSecuencial
     */
    public long getValorSecuencial() {
        return this.valorSecuencial;

    }

	/**
	 * @return
	 */
	public Boolean getEsCajeroRegistro() {
		return esCajeroRegistro;
	}

	/**
	 * @param boolean1
	 */
	public void setEsCajeroRegistro(Boolean boolean1) {
		esCajeroRegistro = boolean1;
	}

}
