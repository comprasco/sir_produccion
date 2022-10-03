/*
 * Created on 29-sep-2004
*/
package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;

import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;


/**
 * @author jfrias
*/
public class EvnTurnoManualRegistro extends EvnSIR {

    // identificadores de evento para edicion de turno vinculado ----------------------
    public static final String REGISTRO_VINCULARTURNO_ADDITEM_EVENT
      = "REGISTRO_VINCULARTURNO_ADDITEM_EVENT";
    public static final String REGISTRO_VINCULARTURNO_DELITEM_EVENT
      = "REGISTRO_VINCULARTURNO_DELITEM_EVENT";
    // ----------------------------------------------------------------------------------



    /**
     *
     */
    public static final String LIQUIDAR = "LIQUIDAR";

	/**
	 *
	 */
	public static final String EDITAR_LIQUIDACION = "EDITAR_LIQUIDACION";
    /**
     *
     */
    public static final String VALIDAR = "VALIDAR";
    /**
     *
     */
    public static final String VALIDAR_MATRICULA = "VALIDAR_MATRICULA";
    /**
     *
     */
    public static final String VALIDAR_MATRICULA_ASOCIADA = "VALIDAR_MATRICULA_ASOCIADA";
    /**
     *
     */
    public static final String VALIDAR_TURNO = "VALIDAR_TURNO";
    /**
     *
     */
    public static final String AGREGAR_ACTO = "AGREGAR_ACTO";

    /**
     *
     */
    public static final String BUSCAR_SOLICITUD = "BUSCAR_SOLICITUD";

	/**
	 *
	 */
	public static final String BUSCAR_SOLICITUD_EDICION = "BUSCAR_SOLICITUD_EDICION";

    /**
     *
     */
    public static final String INCREMENTAR_SECUENCIAL_RECIBO = "INCREMENTAR_SECUENCIAL_RECIBO";

    /**
     *
     */
    public static final String VALIDAR_SOLICITUD = "VALIDAR_SOLICITUD";

    /**
     *
     */
    public static final String CREAR_TURNO = "CREAR_TURNO";

    /** Solicitud del Registro actual */
    private SolicitudRegistro solicitud;

    /** Turno anterior de esta solicitud de registro */
    private Turno turnoAnterior;

    /**
     * Solicitud vinculada a esta solicitud de registro
     * */
    protected gov.sir.core.negocio.modelo.SolicitudVinculada solicitudVinculada;




    /** Matrícula */
    private String matricula;

    /** Acto actual */
    private Acto acto;

	/**
	 * UID de session del usuario
	 */
	private String UID = "";

    /**
     * Usuario SIR
     */
    private gov.sir.core.negocio.modelo.Usuario usuarioSIR;

    /**
     * Liquidacion que se genera
     */
    private Liquidacion liquidacion;

    /** Proceso asociado */
    private Proceso proceso;

    /** Estación del registro */
    private Estacion estacion;

    /** Se debe habilitar el pago */
    private boolean habilitarPago;

    /** Identificador de la solicitud*/
    private String idSolicitud;

    /** Turno actual */
    private Turno turno;

    /** Fase de la solicitud */
    private Fase fase;

    /** Respuesta del work flow */
    private String respuestaWF;

    /** Usuario */
    private gov.sir.core.negocio.modelo.Usuario usuarioNec;

    /** Rol */
    private Object rol;

    /** Motivo */
    private String motivo;
    private String fechaImpresion;
    private Circulo circulo;
    
	private boolean imprimirConstancia = true;





    /**
     * @param usuario
     */
    public EvnTurnoManualRegistro(Usuario usuario) {
        super(usuario);
    }

    /**
     * @param usuario
     * @param matricula
     */
    public EvnTurnoManualRegistro(Usuario usuario, String matricula) {
        super(usuario, VALIDAR_MATRICULA);
        this.matricula = matricula;
    }

    /**
     * @param usuario
     * @param solicitud
     */
    public EvnTurnoManualRegistro(Usuario usuario, SolicitudRegistro solicitud) {
        super(usuario, VALIDAR);
        this.solicitud = solicitud;
		if(solicitud != null)
			this.proceso = solicitud.getProceso();
    }

    /**
     * @param usuario
     * @param turnoAnterior
     */
    public EvnTurnoManualRegistro(Usuario usuario, Turno turnoAnterior) {
        super(usuario, VALIDAR_TURNO);
        this.turnoAnterior = turnoAnterior;
    }

    /**
     * @param usuario
     * @param acto
     */
    public EvnTurnoManualRegistro(Usuario usuario, Acto acto) {
        super(usuario, AGREGAR_ACTO);
        this.acto = acto;
    }

    /**
	 * Constructor con parametros de EvnLiquidacionRegistro, este constructor es utilizado cuando se quiere
	 * avanzar un serial de recibo
	 * @param usuarioAuriga org.auriga.core.modelo.transferObjects.Usuario
	 * @param tipoEvento String con el tipo de evento que se quiere crear.
	 * @param turno gov.sir.core.negocio.modelo.Turno
	 * @param fase gov.sir.core.negocio.modelo.Fase
	 * @param respuestaWF String con la respuesta que se le va a dar al WorkFlow
	 * @param usuario gov.sir.core.negocio.modelo.Usuario usuarioNec
	 * @param estacion org.auriga.core.modelo.transferObjects.Estacion
	 */
	public EvnTurnoManualRegistro(Usuario usuarioAuriga, String tipoEvento, Turno turno, Fase fase, Estacion estacion, String respuestaWF, gov.sir.core.negocio.modelo.Usuario usuario) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respuestaWF;
		this.usuarioNec = usuario;
		this.estacion = estacion;
		this.rol = null;
	}

    /**
     * @param usuario
     * @param liquidacion
     * @param proceso
     * @param estacion
     * @param habilitarPago
     * @param usuarioNeg
     */
    public EvnTurnoManualRegistro(Usuario usuario,
        LiquidacionTurnoRegistro liquidacion, Proceso proceso,
        Estacion estacion, boolean habilitarPago,
        gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
        super(usuario, LIQUIDAR);
        this.liquidacion = liquidacion;
        this.proceso = proceso;
        this.estacion = estacion;
        this.habilitarPago = habilitarPago;
        this.usuarioSIR = usuarioNeg;
    }

	/**
	 * @param usuario
	 * @param liquidacion
	 * @param proceso
	 * @param estacion
	 * @param habilitarPago
	 * @param usuarioNeg
	 * @param tipoevento
	 */
	public EvnTurnoManualRegistro(Usuario usuario,
		LiquidacionTurnoRegistro liquidacion, Proceso proceso,
		Estacion estacion, boolean habilitarPago,
		gov.sir.core.negocio.modelo.Usuario usuarioNeg, String tipoAccion) {
		super(usuario, tipoAccion);
		this.liquidacion = liquidacion;
		this.proceso = proceso;
		this.estacion = estacion;
		this.habilitarPago = habilitarPago;
		this.usuarioSIR = usuarioNeg;
	}

    /**
    * @return  obtiene la solicitud
    */
    public SolicitudRegistro getSolicitud() {
        return solicitud;
    }

    /**
     * @return obtiene el turno anterior
     */
    public Turno getTurnoAnterior() {
        return turnoAnterior;
    }

    /**
     * @return obtiene la matrícula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @return obtiene el acto
     */
    public Acto getActo() {
        return acto;
    }

    /**
     * @param usuario
     */
    public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuario) {
        usuarioSIR = usuario;
    }

    /**
     * @return obtiene el usuario SIR
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
        return usuarioSIR;
    }

    /**
     * @return obtiene la liquidación
     */
    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    /**
     * @param estacion
     */
    public void setEstacion(Estacion estacion) {
        this.estacion= estacion;
    }

    /**
     * @return obtiene la estación
     */
    public Estacion getEstacion() {
        return estacion;
    }

    /**
     * @return obtiene el proceso
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * @return  se debe habilitar el pago
     */
    public boolean isHabilitarPago() {
        return habilitarPago;
    }

	/**
	 * @return Returns the uID.
	 */
	public String getUID() {
		return UID;
	}

	/**
	 * @param uid The uID to set.
	 */
	public void setUID(String uid) {
		UID = uid;
	}

    /**
     * @param idSolicitud
     */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud=idSolicitud;
    }

    /**
     * @return idSolicitud
     */
    public String getIdSolicitud() {
        return this.idSolicitud;
    }

    /**
     * @param motivo
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return motivo
     */
    public String getMotivo() {
        return this.motivo;
    }

    /**
     * @param turno
     */
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    /**
     * @return turno
     */
    public Turno getTurno() {
        return this.turno;
    }

    /**
     * @param fase
     */
    public void setFase(Fase fase) {
        this.fase = fase;
    }

    /**
     * @return fase
     */
    public Fase getFase() {
        return this.fase;
    }

    /**
     * @param respuestaWF
     */
    public void setRespuestaWF(String respuestaWF) {
        this.respuestaWF = respuestaWF;
    }

    /**
     * @return respuestaWF
     */
    public String getRespuestaWF() {
        return this.respuestaWF;
    }

    /**
     * @param usuarioNec
     */
    public void setUsuarioNec(gov.sir.core.negocio.modelo.Usuario usuarioNec) {
        this.usuarioNec = usuarioNec;
    }

    /**
     * @return usuarioNec
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioNec() {
        return this.usuarioNec;
    }

    /**
     * @param sol
     */
    public void setSolicitudRegistro(SolicitudRegistro sol) {
        this.solicitud = sol;
    }

    /**
     * @return sol
     */
    public SolicitudRegistro getSolicitudRegistro() {
        return this.solicitud;
    }

    /**
     * @param fechaImpresion
     */
    public void setFechaImpresion(String fechaImpresion) {

        this.fechaImpresion=fechaImpresion;
    }

    /**
     * @return fechaImpresion
     */
    public String getFechaImpresion() {

        return this.fechaImpresion;
    }

    /**
     * @param circulo
     */
    public void setCirculo(Circulo circulo) {
        this.circulo= circulo;
    }


    public void setSolicitudVinculada( gov.sir.core.negocio.modelo.SolicitudVinculada solicitudVinculada ) {
        this.solicitudVinculada = solicitudVinculada;
    }

    public gov.sir.core.negocio.modelo.SolicitudVinculada getSolicitudVinculada() {
        return solicitudVinculada;
    }
	
	private String anio;
	private String idTurno;
	
	public void setAnio(String sAnio) {
		anio = sAnio;
	}

	public String getAnio() {
		return anio;
	}
	
	public String getIdCirculo() {
		return circulo.getIdCirculo();
	}
	
	public void setIdTurno(String turno) {
		idTurno = turno;
	}
	
	public String getIdTurno() {
		return idTurno;
	}

    /**
     * @return circulo
     */
    public Circulo getCirculo() {
        return this.circulo;
    }


	/**
	 * @return
	 */
	public boolean isImprimirConstancia() {
		return imprimirConstancia;
	}

	/**
	 * @param b
	 */
	public void setImprimirConstancia(boolean b) {
		imprimirConstancia = b;
	}

}
