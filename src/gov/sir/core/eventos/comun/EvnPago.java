package gov.sir.core.eventos.comun;

import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.Hashtable;
import java.util.List;

/**
 * Evento que se encarga de hacer la validacion y el proceso
 * del pago
 * @author eacosta,jfrias,mmunoz
 */
public class EvnPago extends EvnSIR {
    /**
     * Solicitud para validar el pago realizado a una solicitud
     */
    public static final String VALIDAR = "VALIDAR";

    /**
     * Solicitud de Procesar una solicitud
     */
    public static final String PROCESAR = "PROCESAR";

    /**
     * Solicitud de Procesar una solicitud
     */
    public static final String VALIDAR_PROCESAR = "VALIDAR_PROCESAR";
    
    
	public static final String VERIFICAR_APLICACION = "VERIFICAR_APLICACION";
	
	public static final String BUSCAR_APLICACION = "BUSCAR_APLICACION";
	
	public String matricualSinMigrar;
        
        /** @author : HGOMEZ
        *** @change : Se crea la variable tipoCertificado para identificar el tipo de
        *** certificado y poder presentarlo en el imprimible del Recibo de Solicitud.
        *** Caso Mantis : 11598
        */
        public String tipoCertificado;
        
        public boolean isCertificadoEspecial;
        
        public boolean isCertificadoTramite;
        
        public boolean isCertificadoActuacion;
	
	public boolean asociarMatriculaSinMigrar;
	
	public boolean  turnoNacional;

    /**
     * Pago realizado por el ciudadano
     */
    private Pago pago;


	/**
	 *
	 */
	private Turno turno;
	private Solicitud solicitud;
        private List turnoTramite;


    /** Proceso en el cual esta el usuario*/
    private Proceso proceso;

    /** Estacion en el cual esta el usuario*/
    private Estacion estacion;

    /** Identificador unico de usuario*/
    private String UID;

    /** Identificador del Circulo Registral*/
    private String idCirculo;

    /** Circulo Registral*/
    private Circulo circulo;

    /** Impresora seleccionada */
    private String impresora;

	/** Impresora seleccionada */
	private String sessionId;

    /** Variable para guardar el usuario del sistema*/
    private gov.sir.core.negocio.modelo.Usuario usuarioSIR;

    /** Variable para guardar el rol con el que esta trabajando el usuario*/
    private Rol rol;
    
    /** Variable para almacenar el banco de destino vinculado al documento pago*/
    private String banco;
    
    /** Variable para almacenar el canal (Tarjeta, Aprobacion, Consignacion)*/
    private String canal;
    
    /** Variable pára almacenar el numero del canal*/
    private String noCanal;

    /** Variable para guardar las validaciones que se realizaron a las matriculas cuando se realiza
     * una solicitud masiva de certificados*/
    private Hashtable validacionesMasivos;

    /** Indica si el pago realizado es de registro */
    private boolean esPagoRegistro = false;

    /** Indica si el pago realizado es de fotocopias */
    private boolean esPagoFotocopias = false;
    
    /** Indica si el pago realizado es de fotocopias */
    private boolean esPagoCorreccion= false;
    
    /** Indica si el pago realizado es de mayor valor en certificados */
    private boolean esPagoCertificadoMayorValor = false;

    /** Indica si el pago realizado es de consulta simple */
    private boolean asignarEstacion = false;
    
    private AplicacionPago aplicacionPago;

    /**
    * Lista que almacena las notas informativas declaradas antes de la
    * creación del turno.
    */
    private List listaNotasSinTurno;
    
    private boolean omitirRecibo;
    
    private boolean nuevaAplicacion;
    
    /**
     * Variables que se usan para pagar certificados asociados en turnos ya radicados
     */
    /** Indica si es un pago de ceritificado Asociado a un turno ya radicado **/
    private boolean pagoCertificadosAsociadosTurno;
    
    /** Lista de las liquidaciones de los certificados asociados que se desean agregar al turno ya radicado **/
    private List liquidacionesCertificadosAsociados;
    
    private List notasInformativas;
    
    
    private List matriculasSinMigrar;
    
    private boolean tieneMatriculasSinMigrar;
    
    private double valorCertificadosAsociados = 0;
    
    private boolean turnoCertificadosInternet;
    
    private boolean imprimirNotaCertMasivoExento = false;

    /**
     * Constructor por paramentros.
     * @param usuario org.auriga.core.modelo.transferObjects.Usuario
     * @param pago Pago
     * @param proceso Proceso
     * @param estacion Estacion
     * @param tipoEvento String
     * @param usuarioSIR
     */
    public EvnPago(Usuario usuario, Pago pago, Proceso proceso,
        Estacion estacion, String tipoEvento,
        gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
        super(usuario, tipoEvento);
        this.pago = pago;
        this.proceso = proceso;
        this.estacion = estacion;
        this.usuarioSIR = usuarioSIR;
    }

    /**
    * Asocia al evento la lista de notas informativas.
    * @param notasInformativas Lista que va a ser asociada con el atributo listaNotasSinTurno.
    * <p>Contiene las notas informativas definidas antes de la creación de un turno.
    */
    public void setListaNotasSinTurno(List notasInformativas) {
        this.listaNotasSinTurno = notasInformativas;
    }

    /**
    * Retorna la lista de notas informativas asociadas con el evento.
    * @return Lista con las notas informativas
    */
    public List getListaNotasSinTurno() {
        return this.listaNotasSinTurno;
    }

    /**
     * Obtener el atributo circulo
     *
     * @return Retorna el atributo circulo.
     */
    public Circulo getCirculo() {
        return circulo;
    }

    /**
     * Actualizar el valor del atributo circulo
     * @param circulo El nuevo valor del atributo circulo.
     */
    public void setCirculo(Circulo circulo) {
        this.circulo = circulo;
    }

    /**
     * Obtener el atributo esPagoRegistro
     *
     * @return Retorna el atributo esPagoRegistro.
     */
    public boolean isEsPagoRegistro() {
        return esPagoRegistro;
    }

    /**
     * Obtener el atributo esPagoConsultaSimple
     *
     * @return Retorna el atributo esPagoConsultaSimple.
     */
    public boolean isAsignarEstacion() {
        return asignarEstacion;
    }

    public boolean isCertificadoEspecial() {
        return isCertificadoEspecial;
    }

    public void setCertificadoEspecial(boolean isCertificadoEspecial) {
        this.isCertificadoEspecial = isCertificadoEspecial;
    }

    public boolean isCertificadoTramite() {
        return isCertificadoTramite;
    }

    public void setCertificadoTramite(boolean isCertificadoTramite) {
        this.isCertificadoTramite = isCertificadoTramite;
    }

    /**
     * Actualizar el valor del atributo esPagoRegistro
     * @param esPagoRegistro El nuevo valor del atributo esPagoRegistro.
     */
    public void setEsPagoRegistro(boolean esPagoRegistro) {
        this.esPagoRegistro = esPagoRegistro;
    }

    /**
     * Actualizar el valor del atributo esPagoConsultaSimple
     * @param esPagoRegistro El nuevo valor del atributo esPagoConsultaSimple.
     */
    public void setAsignarEstacion(boolean asignarEstacion) {
        this.asignarEstacion = asignarEstacion;
    }

    /**
     * Obtener el atributo estacion
     *
     * @return Retorna el atributo estacion.
     */
    public Estacion getEstacion() {
        return estacion;
    }

    /**
     * Actualizar el valor del atributo estacion
     * @param estacion El nuevo valor del atributo estacion.
     */
    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    /**
     * Obtener el atributo idCirculo
     *
     * @return Retorna el atributo idCirculo.
     */
    public String getIdCirculo() {
        return idCirculo;
    }

    /**
     * Actualizar el valor del atributo idCirculo
     * @param idCirculo El nuevo valor del atributo idCirculo.
     */
    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    /**
     * Obtener el atributo impresora
     *
     * @return Retorna el atributo impresora.
     */
    public String getImpresora() {
        return impresora;
    }

    /**
     * Actualizar el valor del atributo impresora
     * @param impresora El nuevo valor del atributo impresora.
     */
    public void setImpresora(String impresora) {
        this.impresora = impresora;
    }

    /**
     * Obtener el atributo pago
     *
     * @return Retorna el atributo pago.
     */
    public Pago getPago() {
        return pago;
    }

    /**
     * Actualizar el valor del atributo pago
     * @param pago El nuevo valor del atributo pago.
     */
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    /**
     * Obtener el atributo proceso
     *
     * @return Retorna el atributo proceso.
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * Actualizar el valor del atributo proceso
     * @param proceso El nuevo valor del atributo proceso.
     */
    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    /**
     * Obtener el atributo rol
     *
     * @return Retorna el atributo rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Actualizar el valor del atributo rol
     * @param rol El nuevo valor del atributo rol.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public String getBanco(){
        return banco;
    }
    
    public void setBanco(String banco){
        this.banco = banco;
    }
    
    public String getCanal(){
        return canal;
    }
    
    public void setCanal(String canal){
        this.canal = canal;
    }
    
    public String getNoCanal(){
        return noCanal;
    }
    
    public void setNoCanal(String noCanal){
        this.noCanal = noCanal;
    }

    /**
     * Obtener el atributo uID
     *
     * @return Retorna el atributo uID.
     */
    public String getUID() {
        return UID;
    }

    /**
     * Actualizar el valor del atributo uID
     * @param uid El nuevo valor del atributo uID.
     */
    public void setUID(String uid) {
        UID = uid;
    }

    /**
     * Obtener el atributo usuarioSIR
     *
     * @return Retorna el atributo usuarioSIR.
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
        return usuarioSIR;
    }

    /**
     * Actualizar el valor del atributo usuarioSIR
     * @param usuarioSIR El nuevo valor del atributo usuarioSIR.
     */
    public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
        this.usuarioSIR = usuarioSIR;
    }
    
	/**
	 * @param usuario
	 * @param aplicacionPago
	 */
	public EvnPago(Usuario usuario, AplicacionPago aplicacionPago) {
		super(usuario,EvnPago.VERIFICAR_APLICACION);
		this.aplicacionPago = aplicacionPago;
	}

	

    /**
     * Obtener el atributo validacionesMasivos
     *
     * @return Retorna el atributo validacionesMasivos.
     */
    public Hashtable getValidacionesMasivos() {
        return validacionesMasivos;
    }

  public boolean isEsPagoFotocopias() {
    return esPagoFotocopias;
  }

  public Fase getFase() {
    return fase;
  }

  public String getRespuestaWF() {
    return respuestaWF;
  }

	public String getSessionId() {
		return sessionId;
	}

	public Turno getTurno() {
		return turno;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	protected Fase fase;
  protected String respuestaWF = "";



  /**
     * Actualizar el valor del atributo validacionesMasivos
     * @param validacionesMasivos El nuevo valor del atributo validacionesMasivos.
     */
    public void setValidacionesMasivos(Hashtable validacionesMasivos) {
        this.validacionesMasivos = validacionesMasivos;
    }

  public void setEsPagoFotocopias(boolean esPagoFotocopias) {
    this.esPagoFotocopias = esPagoFotocopias;
  }

  public void setFase(Fase fase) {
    this.fase = fase;
  }

  public void setRespuestaWF(String respuestaWF) {
    this.respuestaWF = respuestaWF;
  }

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

    public boolean isEsPagoCorreccion() {
        return esPagoCorreccion;
    }

    public void setEsPagoCorreccion(boolean esPagoCorreccion) {
        this.esPagoCorreccion = esPagoCorreccion;
    }
	/**
	 * @return
	 */
	public AplicacionPago getAplicacionPago() {
		return aplicacionPago;
	}


	/**
	 * @return
	 */
	public boolean isOmitirRecibo() {
		return omitirRecibo;
	}

	/**
	 * @param b
	 */
	public void setOmitirRecibo(boolean b) {
		omitirRecibo = b;
	}

	public boolean isNuevaAplicacion() {
		return nuevaAplicacion;
	}

	public void setNuevaAplicacion(boolean nuevaAplicacion) {
		this.nuevaAplicacion = nuevaAplicacion;
	}

	public void setEsPagoCertificadoMayorValor(boolean b) {
		this.esPagoCertificadoMayorValor = b;
	}

	public boolean isEsPagoCertificadoMayorValor() {
		return esPagoCertificadoMayorValor;
	}

	public boolean isPagoCertificadosAsociadosTurno() {
		return pagoCertificadosAsociadosTurno;
	}

	public void setPagoCertificadosAsociadosTurno(
			boolean pagoCertificadosAsociadosTurno) {
		this.pagoCertificadosAsociadosTurno = pagoCertificadosAsociadosTurno;
	}

	public List getLiquidacionesCertificadosAsociados() {
		return liquidacionesCertificadosAsociados;
	}

	public void setLiquidacionesCertificadosAsociados(
			List liquidacionesCertificadosAsociados) {
		this.liquidacionesCertificadosAsociados = liquidacionesCertificadosAsociados;
	}

	public List getNotasInformativas() {
		return notasInformativas;
	}

	public void setNotasInformativas(List notasInformativas) {
		this.notasInformativas = notasInformativas;
	}

	public boolean isAsociarMatriculaSinMigrar() {
		return asociarMatriculaSinMigrar;
	}

	public void setAsociarMatriculaSinMigrar(boolean asociarMatriculaSinMigrar) {
		this.asociarMatriculaSinMigrar = asociarMatriculaSinMigrar;
	}

	public String getMatricualSinMigrar() {
		return matricualSinMigrar;
	}

	public void setMatricualSinMigrar(String matricualSinMigrar) {
		this.matricualSinMigrar = matricualSinMigrar;
	}

	public List getMatriculasSinMigrar() {
		return matriculasSinMigrar;
	}

	public void setMatriculasSinMigrar(List matriculasSinMigrar) {
		this.matriculasSinMigrar = matriculasSinMigrar;
	}

	public boolean isTieneMatriculasSinMigrar() {
		return tieneMatriculasSinMigrar;
	}

	public void setTieneMatriculasSinMigrar(boolean tieneMatriculasSinMigrar) {
		this.tieneMatriculasSinMigrar = tieneMatriculasSinMigrar;
	}

	public boolean isTurnoNacional() {
		return turnoNacional;
	}

	public void setTurnoNacional(boolean turnoNacional) {
		this.turnoNacional = turnoNacional;
	}

	public double getValorCertificadosAsociados() {
		return valorCertificadosAsociados;
	}

	public void setValorCertificadosAsociados(double valorCertificadosAsociados) {
		this.valorCertificadosAsociados = valorCertificadosAsociados;
	}

	public boolean isTurnoCertificadosInternet() {
		return turnoCertificadosInternet;
	}

	public void setTurnoCertificadosInternet(boolean turnoCertificadosInternet) {
		this.turnoCertificadosInternet = turnoCertificadosInternet;
	}

	public boolean isImprimirNotaCertMasivoExento() {
		return imprimirNotaCertMasivoExento;
	}

	public void setImprimirNotaCertMasivoExento(
			boolean imprimirNotaCertMasivoExento) {
		this.imprimirNotaCertMasivoExento = imprimirNotaCertMasivoExento;
	}

        /** @author : HGOMEZ
        *** @change : Métodos para obtener y setear el valor de la variable
        *** tipoCertificado .
        *** Caso Mantis : 11598
        */

    public String getTipoCertificado() {
        return tipoCertificado;
    }

    public void setTipoCertificado(String tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public List getTurnoTramite() {
        return turnoTramite;
    }

    public void setTurnoTramite(List turnoTramite) {
        this.turnoTramite = turnoTramite;
    }
    
    public boolean isCertificadoActuacion(){
        return isCertificadoActuacion;
    }
    
    public void setCertificadoActuacion(boolean isCertificadoActuacion){
        this.isCertificadoActuacion = isCertificadoActuacion;
    }
}
