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




	/** Constante para acci�n Agregar Acto */
	public static final String AGREGAR_ACTO="AGREGAR_ACTO";

    /** Constante para acci�n buscar solicitud */
    public static final String BUSCAR_SOLICITUD = "BUSCAR_SOLICITUD";

	/** Constante para acci�n buscar solicitud para edici�n*/
	public static final String BUSCAR_SOLICITUD_EDICION = "BUSCAR_SOLICITUD_EDICION";

    /** Constante para acci�n incrementar secuencia del recibo */
    public static final String INCREMENTAR_SECUENCIAL_RECIBO = "INCREMENTAR_SECUENCIAL_RECIBO";

    /** Constante para acci�n de creaci�n de turno */
    public static final String CREAR_TURNO = "CREAR_TURNO";

    /** Constante para acci�n validar solicitud */
    public static final String VALIDAR_SOLICITUD = "VALIDAR_SOLICITUD";

    private long valorSecuencial;

    protected gov.sir.core.negocio.modelo.SolicitudVinculada solicitudVinculada;
    
	/**
	 * Determina si el usuario que hace la liquidaci�n es cajero, para evitar hacer otro paso,
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
