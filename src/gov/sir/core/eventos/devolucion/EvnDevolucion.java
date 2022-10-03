package gov.sir.core.eventos.devolucion;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.Date;
import java.util.List;

/**
 * @author mmunoz, jvelez
 */
public class EvnDevolucion extends EvnSIR {

    private Liquidacion liquidacion;
    private Liquidacion liquidacionMayorValor;
    
    private NotificacionNota notify;

    private String chequeBanco;
    private String chequeNumero;
    private double chequeValor;
    private Date chequeFecha;

    // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
    // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
    private String notaDebitoBanco;
    private String notaDebitoNumero;
    private double notaDebitoValor;
    private Date notaDebitoFecha;
    
    private String formaPago;

    private DocumentoPago docPago;
    
    private double devolucionImpuestos;
    
    
    private double devolucionDerechos;
    
    private double devolucionCertificados;

    private Date fechaRecurso;
    
    private Date fechaEjecutoria;
    
    private String numeroOrden;
    private Date fechaOrden;
    
    private String transferenciaNumero;
    private String transferenciaTitular;
    private double transferenciaValor;
    private String transferenciaBanco;
    
    private String numeroResolucion;
    
    private List notasInformativas;
    
    private List resoluciones;
    
    private Oficio oficioResolucion;
    
    private double valorSaldoFavor;


	/**
	 * Constante que indica que el turno entra a la fase en la que se espera por la interposición
	 * de recursos por parte del ciudadano.
	 */
	public static final String ESPERAR_INTERPONER_RECURSOS = "ESPERAR_INTERPONER_RECURSOS";
	
        public static final String AGREGAR_RESOLUCION_RECURSOS_NOT = "AGREGAR_RESOLUCION_RECURSOS_NOT";
        
	/**
	 * Constante que indica que se va a realizar la devolución de dineros al ciudadano, y que no
	 * se van a interponer recursos.
	 */
	public static final String DEVOLVER_SIN_RECURSOS ="DEVOLVER_SIN_RECURSOS";
	
	
	/**
	 * Constante que indica que no se va a realizar devolución de dineros al ciudadano, y que no
	 * se van a interponer recursos.
	 */
	public static final String FINALIZAR_SIN_RECURSOS ="FINALIZAR_SIN_RECURSOS";
	
	/**
	 * Constante que indica que el usuario decide interponer recursos.
	 */
	public static final String CONFIRMAR_RECURSOS ="CONFIRMAR_RECURSOS";
	
        public static final String ELIMINAR_RESOLUCIONES_NOT = "ELIMINAR_RESOLUCIONES_NOT";
	
	/**
	 * Constante que indica que el usuario decide no interponer recursos.
	 */
	public static final String NEGAR_RECURSOS ="NEGAR_RECURSOS";
        
        public static final String DEVOLVER_CALIFICACION = "DEVOLVER_CALIFICACION";
	
	public static final String NEGAR_RESOLUCION ="NEGAR_RESOLUCION";
        
        public static final String EDITAR_RECURSO_NOTA = "EDITAR_RECURSO_NOTA";
        
        public static final String NOTIFICAR_NOTA_DEVOLUTIVA = "NOTIFICAR_NOTA_DEVOLUTIVA";
	
	/**
	 * Constante que indica la confirmación de la disponibilidad del dinero para
	 * realizar la Devolución. 
	 */
	public static final String CONFIRMAR_DINERO ="CONFIRMAR_DINERO";
	
	/**
	* Constante que indica que se regresa un turno de la fase de resolución a la
	* fase de análisis
	*/
	public static final String DEVOLVER_RESOLUCION_ANALISIS = "DEVOLVER_RESOLUCION_ANALISIS";
	

	/**
	* Constante que indica que debe realizarse la modificación del valor de la devolución
	* una vez se ha pasado por la fase de recursos.
	*/
	public static final String MODIFICAR_VALOR_DEVOLUCION = "MODIFICAR_VALOR_DEVOLUCION";
        
        public static final String AVANZAR_NOTA_DEV_NOTIFICADA = "AVANZAR_NOTA_DEV_NOTIFICADA";
        
        public static final String AVANZAR_TURNO = "AVANZAR_TURNO";
        
        public static final String ACEPTAR_INTERPOSICION_RECURSOS_NOT = "ACEPTAR_INTERPOSICION_RECURSOS_NOT";

    /**Esta constante define la respuesta que se le da al workflow que se
     * guarda en el evento*/
    private String respuestaWF = "";

    /**Esta constante define el turno con el cual se esta trabajado*/
    private Turno turno = null;

    /**Esta constante define la fase en la cual se esta trabajando*/
    private Fase fase = null;

    /**Variable donde se guarda la estacion para realizar la impresion */
    private Estacion estacion = null;

    /**Usuario SIR que esta efectuando todas las operaciones */
    private gov.sir.core.negocio.modelo.Usuario usuarioNec;

    /**En esta variable se guarda la informacion de la resolucion */
    private String resolucion;
    
	/**
	* Variable se guarda la informacion del tipo de recurso que se interpone
	*/
	private String tipoRecurso;
	
	/**
	* Variable se guarda la informacion del usuario que interpone el recurso
	*/
	private String usuarioRecurso;
	
	/**
	* Variable se guarda la informacion de la descripción del recurso que se interpone
	*/
	private String descripcionRecurso;

    /**
     * En esta variable se guarda la información del efectivo asociado
     * con la devolución.
     */
	private double efectivoValor;

    private String idWfTurnoAnterior;

    private Rol rol;


    private String UID;
    
    private String interponeRecursos;
    
    private int posicionRecurso;
    
    private String datoAmpliacionRecurso;
    
    private gov.sir.core.negocio.modelo.Usuario usuarioSir;
    private gov.sir.core.negocio.modelo.Usuario usuarioNeg;


    /**Esta constante guarda el valor de la accion de avanzar el turno */
    public static final String AVANZAR = "AVANZAR";

    /**Esta constante guarda la respuesta del workflow confirmar*/
    public static final String CONFIRMAR = "CONFIRMAR";

    /**Esta constante guarda la respuesta del workflow negar*/
    public static final String NEGAR = "NEGAR";

    /**Esta constante guarda la respuesta del workflow default*/
    public static final String DEFAULT = "DEFAULT";

    public static final String REPOSICION_RECURSO = "REPOSICION";

    public static final String APELACION_RECURSO = "APELACION";

    public static final String NO_RECURSO = "NO_RECURSO";


    public static final String ANALISIS_ACEPTAR = "ANALISIS_ACEPTAR";

    public static final String ANALISIS_NEGAR = "ANALISIS_NEGAR";

    public static final String NOTIFICAR_ACEPTAR = "NOTIFICAR_ACEPTAR";

    public static final String RESOLUCION_ACEPTAR = "RESOLUCION_ACEPTAR";


    public static final String REPOSICION_ACEPTAR = "REPOSICION_ACEPTAR";

    public static final String APELACION_ACEPTAR = "APELACION_ACEPTAR";

    public static final String PAGO_CONFIRMAR = "PAGO_CONFIRMAR";

    public static final String AGREGAR_TURNO_ANT = "AGREGAR_TURNO_ANT";
    public static final String AGREGAR_CONSIGNACION_CHEQUE = "AGREGAR_CONSIGNACION_CHEQUE";
    public static final String IMPRIMIR_BORRADOR = "IMPRIMIR_BORRADOR";
    public static final String RESOLUCION_FIRMAR_ACEPTAR ="RESOLUCION_FIRMAR_ACEPTAR";
    public static final String CAMBIAR_TURNO_DEVOLUCION="CAMBIAR_TURNO_DEVOLUCION";
    public static final String AGREGAR_RESOLUCION = "AGREGAR_RESOLUCION";
    public static final String ELIMINAR_RESOLUCIONES = "ELIMINAR_RESOLUCIONES";
            
            
     //RESPUESTAS WORKFLOW FASE DE NOTIFICAR CIUDADANO
     /**
      * Respuesta hacia el Workflow. 
      * Avanza hacia la fase en la que se confirma si el ciudadano interpone o no
      * recursos.
      */
     public static final String CON_RECURSO = "CON RECURSO"; 
     
     /**
     * Respuesta hacia el Workflow. 
     * Avanza hacia la fase en la que se espera la confirmación de dineros
     * por parte de SNR.
     */
     public static final String SIN_RECURSO_DEVOLVER = "SIN RECURSO DEVOLVER";

     
     /**
     * Respuesta que finaliza un turno, cuando no hay devolución ni interposición
     * de recursos.
     */
     public static final String SIR_RECURSO_FINALIZAR = "SIN RECURSO FINALIZAR";


	/**
	 * Constante que identifica la acción que permite agregar recursos a un turno de devolución.
	 */
	public static final String AGREGAR_RECURSO = "AGREGAR_RECURSO";
	
	public static final String EDITAR_RECURSO = "EDITAR_RECURSO";
	
	
	/**
	* Constante que indica que se han aceptado los recursos interpuestos por el ciudadano
	*/
	public static final String ACEPTAR_INTERPOSICION_RECURSOS = "ACEPTAR_INTERPOSICION_RECURSOS";
	
	
	/**
	* Constante que indica que se han negado los recursos interpuestos por el ciudadano
	*/
	public static final String NEGAR_INTERPOSICION_RECURSOS = "NEGAR_INTERPOSICION_RECURSOS";

	
	public static final String DEVOLVER_RECURSOS = "DEVOLVER_RECURSOS";
	
	public static final String DEVOLVER_RESOLUCION = "DEVOLVER_RESOLUCION";
        
        public static final String ESPERANDO_JUZGADO = "ESPERANDO_JUZGADO";
        
        public static final String AGREGAR_RECURSO_NOTA_DEVOLUTIVA = "AGREGAR_RECURSO_NOTA_DEVOLUTIVA";

        /**
         * yeferson
         */
        
        private double valorDevolucionConservaDoc;
        
        
    /**
     * Constructor por defecto del evento.
     * @param usuarioAuriga org.auriga.core.modelo.transferObjects.Usuario
     * @param tipoEvento String
     * @param turno gov.sir.core.negocio.modelo.Turno
     * @param fase gov.sir.core.negocio.modelo.Fase
     * @param estacion org.auriga.core.modelo.transferObjects.Estacion
     * @param respuestaWF String
     * @param usuario gov.sir.core.negocio.modelo.Usuario
     */
    public EvnDevolucion(Usuario usuarioAuriga, String tipoEvento, Turno turno,
                         Fase fase, Estacion estacion, String respuestaWF,
                         gov.sir.core.negocio.modelo.Usuario usuario) {
        super(usuarioAuriga, tipoEvento);
        this.turno = turno;
        this.fase = fase;
        this.respuestaWF = respuestaWF;
        this.usuarioNec = usuario;
        this.estacion = estacion;
        this.valorDevolucionConservaDoc = 0;
    }
    
    public EvnDevolucion(Usuario usuarioAuriga, List resoluciones,String tipoEvento) {
		super(usuarioAuriga, tipoEvento);
		this.resoluciones = resoluciones;
                this.valorDevolucionConservaDoc = 0;
    }
    
    public EvnDevolucion(Usuario usuarioAuriga, String tipoEvento) {
		super(usuarioAuriga, tipoEvento);
    }
    
    
    public gov.sir.core.negocio.modelo.Usuario getUsuarioSir(){
        return usuarioSir;
    }
    
    public void setUsuarioSir(gov.sir.core.negocio.modelo.Usuario usuarioSir){
        this.usuarioSir = usuarioSir;
    }
    
    public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg(){
        return usuarioNeg;
    }
    
    public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuarioNeg){
        this.usuarioNeg = usuarioNeg;
    }
    /**
     * Retorna la fase que viaja en el evento
     * @return Fase
     */
    public Fase getFase() {
        return fase;
    }
    
    public void setFase(Fase fase){
        this.fase = fase; 
    }

    /**
     * Retorna el turno que viaja en el evento
     * @return Turno
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * Retorna la respuesta que se le quiere dar al workflow
     * @return String
     */
    public String getRespuestaWF() {
        return respuestaWF;
    }

    /**
     * Retorna la estacion que se encuentra en el evento
     * @return Estacion
     */
    public Estacion getEstacion() {
        return estacion;
    }

    /**
     * Retorna el usuario SIR que se encuentra en el evento
     * @return gov.sir.core.negocio.modelo.Usuario
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioNec() {
        return usuarioNec;
    }

    /**
     * Retorna la resulacion que se quiere relacionar con la devolucion.
     * @return String
     */
    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public void setRespuestaWF(String respuestaWF) {
        this.respuestaWF = respuestaWF;
    }


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getIdWfTurnoAnterior() {
        return idWfTurnoAnterior;
    }

    public String getChequeNumero() {
        return chequeNumero;
    }

    public String getChequeBanco() {
        return chequeBanco;
    }

    public double getChequeValor() {
        return chequeValor;
    }
    
    public Date getChequeFecha() {
        return chequeFecha;
    }
    
    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
    public DocumentoPago getDocPago() {
        return docPago;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }
    
    public Date getFechaRecurso() {
        return fechaRecurso;
    }
    
    public Date getFechaEjecutoria() {
        return fechaEjecutoria;
    }
    
    public String getNumeroOrden() {
        return numeroOrden;
    }
    
    public Date getFechaOrden() {
        return fechaOrden;
    }
    
    public String getTransferenciaNumero() {
        return transferenciaNumero;
    }
    
    public String getTransferenciaTitular() {
        return transferenciaTitular;
    }

    public String getTransferenciaBanco() {
        return transferenciaBanco;
    }

    public double getTransferenciaValor() {
        return transferenciaValor;
    }

    public void setIdWfTurnoAnterior(String idWfTurnoAnterior) {
        this.idWfTurnoAnterior = idWfTurnoAnterior;
    }

    public void setChequeNumero(String chequeNumero) {
        this.chequeNumero = chequeNumero;
    }

    public void setChequeBanco(String chequeBanco) {
        this.chequeBanco = chequeBanco;
    }

    public void setChequeValor(double chequeValor) {
        this.chequeValor = chequeValor;
    }
    
    public void setChequeFecha(Date chequeFecha) {
        this.chequeFecha = chequeFecha;
    }
    
    public void setDocPago(DocumentoPago docPago) {
        this.docPago = docPago;
    }


    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }
    
    public void setFechaRecurso(Date fechaRecurso) {
        this.fechaRecurso = fechaRecurso;
    }
    
    public void setFechaEjecutoria(Date fechaEjecutoria) {
        this.fechaEjecutoria = fechaEjecutoria;
    }
    
    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }
    
    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }
    
    public void setTransferenciaNumero(String transferenciaNumero) {
        this.transferenciaNumero = transferenciaNumero;
    }

    public void setTransferenciaBanco(String transferenciaBanco) {
        this.transferenciaBanco = transferenciaBanco;
    }

    public void setTransferenciaValor(double transferenciaValor) {
        this.transferenciaValor = transferenciaValor;
    }
    
    public void setTransferenciaTitular(String transferenciaTitular) {
        this.transferenciaTitular = transferenciaTitular;
    }

	/**
	 * @return
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * @param rol
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @return
	 */
	public double getEfectivoValor() {
		return efectivoValor;
	}

  public Liquidacion getLiquidacion() {
    return liquidacion;
  }

  /**
	 * @param d
	 */
	public void setEfectivoValor(double d) {
		efectivoValor = d;
	}

  public void setLiquidacion(Liquidacion liquidacion) {
    this.liquidacion = liquidacion;
  }

	/**
	 * @return
	 */
	public String getDescripcionRecurso() {
		return descripcionRecurso;
	}

	/**
	 * @return
	 */
	public String getTipoRecurso() {
		return tipoRecurso;
	}

	/**
	 * @return
	 */
	public String getUsuarioRecurso() {
		return usuarioRecurso;
	}

	/**
	 * @param string
	 */
	public void setDescripcionRecurso(String string) {
		descripcionRecurso = string;
	}

	/**
	 * @param string
	 */
	public void setTipoRecurso(String string) {
		tipoRecurso = string;
	}

	/**
	 * @param string
	 */
	public void setUsuarioRecurso(String string) {
		usuarioRecurso = string;
	}

	/**
	 * @return
	 */
	public double getDevolucionCertificados() {
		return devolucionCertificados;
	}

	/**
	 * @return
	 */
	public double getDevolucionDerechos() {
		return devolucionDerechos;
	}

	/**
	 * @return
	 */
	public double getDevolucionImpuestos() {
		return devolucionImpuestos;
	}

	/**
	 * @param d
	 */
	public void setDevolucionCertificados(double d) {
		devolucionCertificados = d;
	}

	/**
	 * @param d
	 */
	public void setDevolucionDerechos(double d) {
		devolucionDerechos = d;
	}

	/**
	 * @param d
	 */
	public void setDevolucionImpuestos(double d) {
		devolucionImpuestos = d;
	}

	public List getNotasInformativas() {
		return notasInformativas;
	}

	public void setNotasInformativas(List notasInformativas) {
		this.notasInformativas = notasInformativas;
	}
	
	public List getResoluciones() {
		return resoluciones;
	}

	public void setResoluciones(List resoluciones) {
		this.resoluciones = resoluciones;
	}
	
	private boolean turnoDevolucionModificado;

	public boolean isTurnoDevolucionModificado() {
		return turnoDevolucionModificado;
	}

	public void setTurnoDevolucionModificado(boolean turnoDevolucionModificado) {
		this.turnoDevolucionModificado = turnoDevolucionModificado;
	}

	public Oficio getOficioResolucion() {
		return oficioResolucion;
	}

	public void setOficioResolucion(Oficio oficioResolucion) {
		this.oficioResolucion = oficioResolucion;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Liquidacion getLiquidacionMayorValor() {
		return liquidacionMayorValor;
	}

	public void setLiquidacionMayorValor(Liquidacion liquidacionMayorValor) {
		this.liquidacionMayorValor = liquidacionMayorValor;
	}

	public double getValorSaldoFavor() {
		return valorSaldoFavor;
	}

	public void setValorSaldoFavor(double valorSaldoFavor) {
		this.valorSaldoFavor = valorSaldoFavor;
	}

	public String getInterponeRecursos() {
		return interponeRecursos;
	}

	public void setInterponeRecursos(String interponeRecursos) {
		this.interponeRecursos = interponeRecursos;
	}

	public int getPosicionRecurso() {
		return posicionRecurso;
	}

	public void setPosicionRecurso(int posicionRecurso) {
		this.posicionRecurso = posicionRecurso;
	}

	public String getDatoAmpliacionRecurso() {
		return datoAmpliacionRecurso;
	}

	public void setDatoAmpliacionRecurso(String datoAmpliacionRecurso) {
		this.datoAmpliacionRecurso = datoAmpliacionRecurso;
	}

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
     * Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
     * @return the notaDebitoBanco
     */
    public String getNotaDebitoBanco() {
        return notaDebitoBanco;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
     * Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
     * @param notaDebitoBanco the notaDebitoBanco to set
     */
    public void setNotaDebitoBanco(String notaDebitoBanco) {
        this.notaDebitoBanco = notaDebitoBanco;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
     * Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
     * @return the notaDebitoNumero
     */
    public String getNotaDebitoNumero() {
        return notaDebitoNumero;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
     * Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
     * @param notaDebitoNumero the notaDebitoNumero to set
     */
    public void setNotaDebitoNumero(String notaDebitoNumero) {
        this.notaDebitoNumero = notaDebitoNumero;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
     * Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
     * @return the notaDebitoValor
     */
    public double getNotaDebitoValor() {
        return notaDebitoValor;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
     * Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
     * @param notaDebitoValor the notaDebitoValor to set
     */
    public void setNotaDebitoValor(double notaDebitoValor) {
        this.notaDebitoValor = notaDebitoValor;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
     * Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
     * @return the notaDebitoFecha
     */
    public Date getNotaDebitoFecha() {
        return notaDebitoFecha;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
     * Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
     * @param notaDebitoFecha the notaDebitoFecha to set
     */
    public void setNotaDebitoFecha(Date notaDebitoFecha) {
        this.notaDebitoFecha = notaDebitoFecha;
    }

    /**
     * yeferson
     */
    
    /**
     * @return the valorDevolucionConservaDoc
     */
    public double getValorDevolucionConservaDoc() {
        return valorDevolucionConservaDoc;
    }

    /**
     * @param valorDevolucionConservaDoc the valorDevolucionConservaDoc to set
     */
    public void setValorDevolucionConservaDoc(double valorDevolucionConservaDoc) {
        this.valorDevolucionConservaDoc = valorDevolucionConservaDoc;
    } 
	
    //fin

    public NotificacionNota getNotify() {
        return notify;
    }

    public void setNotify(NotificacionNota notify) {
        this.notify = notify;
    }
    
}
