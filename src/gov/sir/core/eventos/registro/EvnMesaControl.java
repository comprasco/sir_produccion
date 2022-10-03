package gov.sir.core.eventos.registro;

import java.util.LinkedList;
import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author pmunoz, ddiaz
 *
 */
public class EvnMesaControl extends EvnSIR {

	/**
	 * Indicador accion de avanzar
	 */
	public static final String AVANZAR="AVANZAR";
	
	/**
	 * Indicador accion de mesa control
	 */
	public static final String MESA_CONTROL = "MESA_CONTROL";
	
	/**
	 * Indicador accion de consultar turnos
	 */
	public static final String CONSULTAR_TURNOS = "CONSULTAR_TURNOS";
	
	/**
	 * Indicador accion de enviar respuesta
	 */
	public static final String ENVIAR_RESPUESTA = "ENVIAR_RESPUESTA";
	
	/**
	 * Indicador accion de devolver revision
	 */
	public static final String DEVOLVER_REVISION = "DEVOLVER_REVISION";
	
	/**
	 * Indicador accion imprimir certificado
	 */
	public static final String IMPRIMIR_CERTIFICADO = "IMPRIMIR_CERTIFICADO";	
	
	/**
	 * Indicador accion imprimir certificado por relación
	 */
	public static final String IMPRIMIR_CERTIFICADO_RELACION = "IMPRIMIR_CERTIFICADO_RELACION";
	
	/**
	 * Indicador accion avanzar mesa II
	 */
	public static final String AVANZAR_MESA_II = "AVANZAR_MESA_II";
	
	/**
	 * Indicador accion negar mesa II
	 */
	public static final String NEGAR_MESA_II = "NEGAR_MESA_II";
	
	/**
	 * Indicador accion cambiar Matricula
	 */
	public static final String CAMBIAR_MATRICULA = "CAMBIAR_MATRICULA";
	
	/**
	 * Indicador accion cambiar consultar las matrículas válidas para el cambio de matrículas
	 */
	public static final String CARGAR_MATRICULAS_VALIDAS_CAMBIO = "CARGAR_MATRICULAS_VALIDAS_CAMBIO";
	
	/**
	 * Indicador accion cambiar Matricula por relación
	 */
	public static final String CAMBIAR_MATRICULA_RELACION = "CAMBIAR_MATRICULA_RELACION";
	
	/**
	 * Indicador accion de consultar los datos asociados a una relación.
	 */
	public static final String CONSULTAR_RELACION = "CONSULTAR_RELACION";	
	
	/**
	 * Indicador accion cambiar validar certificados
	 */
	public static final String VALIDAR_CERTIFICADOS = "VALIDAR_CERTIFICADOS";

	
	/**
	 * Esta constante define la respuesta que se le da al workflow
	 */
	public static final String IMPRESION_CONFIRMAR = "CONFIRMAR";
	
	/** Variable que identifica que retornan un turno a testamento para corrección
	 *Modifica Pablo Quintana Junio 19 2008*/
	public static final String DEVOLVER_TESTAMENTO="DEVOLVER_TESTAMENTO";
	
	private LinkedList foliosDerivados;
	private String turnoDesde;
	private String turnoHasta;
	private String respuesta;
	private String numeroRelacion;
	private List solCerts;
	private Turno turno;
	private Circulo circulo;
	private Turno turnoCertificado;
	private Turno turnoRegistro;
    
	/**Proceso para poder listar los turno*/
    private Proceso proceso;
	/** Identificador del Relación*/
	private String idRelacion;

    private Fase fase;

    private SolicitudCertificado solC;

    private SolicitudFolio solF;

    private Estacion estacion;

    private String pathFirmas;

	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
	
	private List allSolicitudCertificados;

	

	
    

    

    

    
    
    
	

	
	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnMesaControl(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}
	
	/**
	 * Usado para hacer segregaciones masivas
	 * @param usuario
	 * @param tipoEvento
	 * @param listaFolios LinkedList de los folios relacionados en certificados masivos
	 */
	public EvnMesaControl(Usuario usuario, String tipoEvento, LinkedList listaFolios) {
		super(usuario, MESA_CONTROL);
		this.foliosDerivados = listaFolios;
	}
	
	/**
	 * @return foliosDerivados
	 */
	public LinkedList getListaFolios(){
		return foliosDerivados;
	}

	/**
	 * Inicializa el id del turno desde el cual se efecutara la consulta
	 * @param nturnoDesde 
	 */
	public void setTurnoDesde(String nturnoDesde) {
		this.turnoDesde= nturnoDesde;
	}
	
	/**
	 * Obtiene el id del turno desde el que se hara la consulta
	 * @return turnoDesde 
	 */
	public String getTurnoDesde() {
		return this.turnoDesde;
	}
	
	/**
	 * Inicializa el id del turno hasta el cual se hara la consulta
	 * @param nturnoHasta
	 */
	public void setTurnoHasta(String nturnoHasta) {
		this.turnoHasta= nturnoHasta;
	}
	
	/**
	 * Obtiene el id del turno hasta el que se hara la consulta
	 * @return turnoDesde
	 */
	public String getTurnoHasta() {
		return this.turnoHasta;
	}

	/**
	 * Inicializa la respuesta del evento
	 * @param nRespuesta
	 */
	public void setRespuesta(String nRespuesta) {
		this.respuesta= nRespuesta;
	}
	
	/**
	 * Obtiene la respuesta del evento
	 * @return respuesta
	 */
	public String getRespuesta() {
		return this.respuesta;
	}

	/**
	 * Inicializa la lista de solcitudes de certificado
	 * @param nSolCerts
	 */
	public void setSolicitudesCertificado(List nSolCerts) {
		this.solCerts=nSolCerts;	
	}
	
	/**
	 * Obtiene la lista de solicitudes de certificado
	 * @return solCert
	 */
	public List getSolicitudesCertificado() {
		return this.solCerts;	
	}

    /**
     * Inicializa el turno sobre el que se va trabajar en la accion de negocio
     * @param turno
     */
    public void setTurno(Turno turno) {
        this.turno= turno;
        
    }
    
    /**
     * Obtiene  elturno sobre el que se va trabajar en la accion de negocio
     * @return turno
     */
    public Turno getTurno() {
        return this.turno;
        
    }

    /**
     * @param fase
     */
    public void setFase(Fase fase) {
        this.fase=fase;
    }
	
    /**
     * @return fase
     */
    public Fase getFase() {
        return this.fase;
    }

    /**
     * @param solC
     */
    public void setSolicitudCertificado(SolicitudCertificado solC) {
        this.solC = solC ; 
    }
    
    /**
     * @return solC
     */
    public SolicitudCertificado getSolicitudCertificado() {
        return this.solC;
    }

    /**
     * @param solF
     */
    public void setSolFolio(SolicitudFolio solF) {
        this.solF=solF;
    }
    
    /**
     * @return solF
     */
    public SolicitudFolio getSolFolio() {
        return this.solF;
    }

    /**
     * @param estacion
     */
    public void setEstacion(Estacion estacion) {
       this.estacion= estacion;   
    }
    
    /**
     * @return estacion
     */
    public Estacion getEstacion() {
       return this.estacion;   
    }

    /**
     * @param pathFirmas
     */
    public void setPathFirmas(String pathFirmas) {
        this.pathFirmas= pathFirmas;    
    }
    
    /**
     * @return pathFirmas
     */
    public String getPathFirmas() {
        return this.pathFirmas;    
    }

	/**
	 * @param usuarioNeg
	 */
	public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		this.usuarioNeg=usuarioNeg;
		
	}
    
    
	/**
	 * @return Returns the usuarioNeg.
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}
	/**
	 * @return Returns the solCerts.
	 */
	public List getSolCerts() {
		return solCerts;
	}
	/**
	 * @param solCerts The solCerts to set.
	 */
	public void setSolCerts(List solCerts) {
		this.solCerts = solCerts;
	}

	public List getAllSolicitudCertificados() {
		return allSolicitudCertificados;
	}

	public void setAllSolicitudCertificados(List allSolicitudCertificados) {
		this.allSolicitudCertificados = allSolicitudCertificados;
	}

	public String getNumeroRelacion() {
		return numeroRelacion;
	}

	public void setNumeroRelacion(String numeroRelacion) {
		this.numeroRelacion = numeroRelacion;
	}

	public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public Turno getTurnoCertificado() {
		return turnoCertificado;
	}

	public void setTurnoCertificado(Turno turnoCertificado) {
		this.turnoCertificado = turnoCertificado;
	}

	public Turno getTurnoRegistro() {
		return turnoRegistro;
	}

	public void setTurnoRegistro(Turno turnoRegistro) {
		this.turnoRegistro = turnoRegistro;
	}

	public String getIdRelacion() {
		return idRelacion;
	}

	public void setIdRelacion(String idRelacion) {
		this.idRelacion = idRelacion;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}
}
