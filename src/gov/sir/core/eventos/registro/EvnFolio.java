package gov.sir.core.eventos.registro;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.comun.EvnSIR;

import gov.sir.core.negocio.modelo.WebSegregacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Turno;

/**
 * Evento utilizado para el envio de solicitudes con respecto a folios, 
 * a la capa de negocio
 * 
 */
public class EvnFolio extends EvnSIR {

	/**
    * Folio que se quiere hacer persistente.  
    */
	private Folio folio;
	
	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
	private List anotaciones;
	
    private WebSegregacion webSegregacion;
    
    /** Constante que indica que se desea consultar los detalles de un folio */
    public static final String CONSULTA = "CONSULTA";
    
	/** Constante que indica que se desea consultar los detalles de un folio */
	public static final String CONSULTA_DIGITACION_MASIVA = "CONSULTA_DIGITACION_MASIVA";    
    
	/** Constante que indica que se desea consultar los detalles de un folio por ID y usuario*/
	public static final String CONSULTA_ID_USER = "CONSULTA_ID_USER";    
    
    /** Constante que indica que se desea obtener una lista de folios que cumple
     * un criterio */
    public static final String BUSCAR = "BUSCAR";
    
    /** Constante que indica que se desea crear un folio */
    public static final String CREAR = "CREAR";

	/** Constante que indica que se desea grabar en temporal un folio */
	public static final String GRABAR_TEMPORAL = "GRABAR_TEMPORAL";
    
    /** Constante que indica que se desea crear un folio desenglobándolo desde
     * otro */
    public static final String DESENGLOBAR = "DESENGLOBAR";
    
    /** Constante que indica que se desea crear un folio englobando una lista de
     * folios existentes */
    public static final String ENGLOBAR = "ENGLOBAR";
    
    /**
     * Constante que indica que se desea consultar una lista de folios
     */
    public static final String CONSULTAR_LISTA="CONSULTAR_LISTA";
	
	/**
	 * Constante que indica que se desea grabar una edicion
	 */
	public static final String GRABAR_EDICION="GRABAR_EDICION";
    
    //Constantes utilizadas para generar respuestas sobre el resultado de actividades. 
    public static final String CREACION_FOLIO_CREADO = "CREADO";
	public static final String CREACION_FOLIO_EXISTE = "EXISTE";
	public static final String REVISION_INIC_CONFIRMAR = "CONFIRMAR";
	public static final String REVISION_INIC_NEGAR = "NEGAR";
	public static final String REVISION_CREADO = "CREADO";
	public static final String REVISION_RECHAZADO = "RECHAZADO";
	
	
	
	private Turno turno;
	
	private Fase fase;
	
	private String respuestaWF;
	
	private List listaFolios;
	
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	
    /**
     * Usado para consulta, búsqueda y creación
     * CONSULTA
     * BUSCAR
     * CREAR
     * @param usuario que genera el evento.
     * @param tipoEvento
     * @param folio que quierehacerse persistente. 
     * @see <{EvnSIR}>
     */
    public EvnFolio(Usuario usuario, String tipoEvento, Folio folio) {
		super(usuario, tipoEvento);
        this.folio = folio;
    }


	public EvnFolio(Usuario usuario, String tipoEvento, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.folio = folio;
		this.usuarioSIR = usuarioSIR;
	}
	
	public EvnFolio(Usuario usuario , Folio folio, List anotaciones, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, GRABAR_EDICION);
		this.folio=folio;
		this.anotaciones=anotaciones;
		this.usuarioNeg=usuarioNeg;
	}
    
	public EvnFolio(Usuario usuario, Turno turno, String tipoEvento, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.folio = folio;
		this.usuarioSIR = usuarioSIR;
	}    
    
    public EvnFolio(Usuario usuario, List folios){
    	super(usuario, CONSULTAR_LISTA);
    	this.listaFolios=folios;
    }
    
    
	/**
	 * @param usuarioAuriga
	 * @param string
	 * @param turno
	 * @param fase
	 * @param string2
	 */
	public EvnFolio(Usuario usuario, String tipoEvento, Turno turno, Fase fase, String respuestaWF) {
		super(usuario,tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respuestaWF;
	}
	

	/**
	 * @param usuarioAuriga
	 * @param string
	 * @param turno
	 * @param fase
	 * @param string2
	 * @param folio
	 * @param usuario
	 */
	public EvnFolio(Usuario usuarioAuriga, String string, Turno turno, Fase fase, String string2, Folio folio, gov.sir.core.negocio.modelo.Usuario usuario) {
		super(usuarioAuriga,string);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = string2;
		this.folio = folio;
		this.usuarioSIR = usuario;
	}


	
    
    /**
     * Usado para Segregacion
     * @param usuario
     * @param tipoEvento
     * @param webSegregacion
     */
    public EvnFolio(Usuario usuario, String tipoEvento, WebSegregacion webSegregacion) {
        super(usuario, tipoEvento);
        this.webSegregacion = webSegregacion;
    }
    
   
    /**
     * Getter for property webSegregacion.
     * @return Value of property webSegregacion.
     */
    public WebSegregacion getWebSegregacion() {
        return this.webSegregacion;
    }
    
    /**
     * Getter for property folio.
     * @return Value of property folio.
     */
    public Folio getFolio() {
        return this.folio;
    }


    
	/**
	 * Getter for property fase.
	 * @return
	 */
	public Fase getFase() {
		return fase;
	}

	/**
	 *  Getter for property respuestaWF.
	 *  * @return
	 */
	public String getRespuestaWF() {
		return respuestaWF;
	}

	/**
	 * Getter for property Turno.
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * @return
	 */
	public List getListaFolios() {
		return listaFolios;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}
	
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}
	
	/**
	 * @return
	 */
	public List getAnotaciones() {
		return anotaciones;
	}
	

	/**
	 * @param usuario
	 */
	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioSIR = usuario;
	}

}

