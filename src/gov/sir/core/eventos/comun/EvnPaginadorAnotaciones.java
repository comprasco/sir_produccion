package gov.sir.core.eventos.comun;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.FolioPk;

/**
 * Este evento es responsable de enviar la información necesaria para construir el listado de las anotaciones  
 * en los diferentes procesos por medio del paginador de anotaciones. 
 * @author ppabon
 */
public class EvnPaginadorAnotaciones extends EvnSIR {

	private FolioPk folioID;
	private String criterio;
	private String valorCriterio;
	/**
	 * @author Cesar Ramírez
	 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
	 * Variable que guarda el rol actual al consultar las anotaciones.
	 **/
	private String rolID;
	private int inicio;
	private int cantidad;
	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
	private boolean consulta = false;
	private String nombrePaginador;
	private String nombreResultado;
	private int paginaInicial=0;
	private boolean consultarAnotacionesDefinitivas= false; 
	
	private boolean getAnotacionesConDeltadas = false;
	private boolean consultarAnotacionesFolioTemporal= false;

    /**
     * Modificado por OSBERT LINERO - Iridium Telecomunicaciones e Informáica.
     * Cambio realizado para resolver incidencia Mantis 0002399 - Requerimiento 117
     * Se crean 2 atributos para guardar el número de anotación desde la que se desea visualizar
     * y el número total de anotaciones respectivamente, y los metodos de acceso a dichos atributos.
     */
    private Integer anotcionesDesdeV = 0;
    private Integer numAnotcionesTotalV = 0;
	
	public static final String CONSULTAR_ANOTACIONES = "CONSULTAR_ANOTACIONES";	
	public static final String CONSULTAR_ANOTACIONES_FOLIO = "CONSULTAR_ANOTACIONES_FOLIO";	
	public static final String CARGAR_PAGINA = "CARGAR_PAGINA";

	/**
	 * @param usuario
	 */
	public EvnPaginadorAnotaciones(Usuario usuario) {
		super(usuario);
	}

	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnPaginadorAnotaciones(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	/**
	 * @param usuario
	 * @param folio
	 * @param criterio
	 * @param valorCriterio
	 * @param inicio
	 * @param cantidad
	 * @param tipoEvento
	 */
	public EvnPaginadorAnotaciones(Usuario usuario, FolioPk folioID, String criterio, String valorCriterio, int inicio, int cantidad, String tipoEvento, boolean consultarAnotacionesDefinitivas) {
		super(usuario, tipoEvento);
		this.folioID = folioID;
		this.criterio = criterio;
		this.valorCriterio = valorCriterio;
		this.inicio = inicio;
		this.cantidad = cantidad;
		this.consultarAnotacionesDefinitivas = consultarAnotacionesDefinitivas; 
	}
	
	/**
	 * Evento cuando se quiere obtener la lsita de anotaciones del folio, en 2 listas una de temporales y otra de definitivas.
	 * Se obtendra toda la lista de las temporales y de las definitivas solo se obtendra el segmento de la lista segun los
	 * parametros inicio y cantidad.
	 * @param usuario
	 * @param folio
	 * @param inicio
	 * @param cantidad
	 * @param tipoEvento
	 * @param usuarioNeg
	 */
	public EvnPaginadorAnotaciones(Usuario usuario, FolioPk folioID, int inicio, int cantidad, String tipoEvento, gov.sir.core.negocio.modelo.Usuario usuarioNeg, boolean consultarAnotacionesDefinitivas) {
		super(usuario, tipoEvento);
		this.folioID = folioID;
		this.inicio = inicio;
		this.cantidad = cantidad;
		this.usuarioNeg= usuarioNeg;
		this.consultarAnotacionesDefinitivas = consultarAnotacionesDefinitivas;		
	}

	/**
	 * @author Cesar Ramírez
	 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
	 * Constructor que añade el id del rol para trabajarlo posteriormente.
	 **/
	public EvnPaginadorAnotaciones(Usuario usuario, FolioPk folioID, int inicio, int cantidad, String tipoEvento, gov.sir.core.negocio.modelo.Usuario usuarioNeg, boolean consultarAnotacionesDefinitivas, String rolID) {
		super(usuario, tipoEvento);
		this.folioID = folioID;
		this.inicio = inicio;
		this.cantidad = cantidad;
		this.usuarioNeg= usuarioNeg;
		this.consultarAnotacionesDefinitivas = consultarAnotacionesDefinitivas;
		this.rolID = rolID;
	}
	
	/**
	 * @return
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @return
	 */
	public String getCriterio() {
		return criterio;
	}

	/**
	 * @return
	 */
	public FolioPk getFolio() {
		return folioID;
	}

	/**
	 * @return
	 */
	public int getInicio() {
		return inicio;
	}

	/**
	 * @return
	 */
	public void setConsulta(boolean nConsulta) {
		consulta= nConsulta;
	}

	/**
	 * @return
	 */
	public boolean getConsulta() {
		return consulta;
	}

	/**
	 * @return
	 */
	public String getValorCriterio() {
		return valorCriterio;
	}
	
	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}
	
	/**
	 * @return
	 */
	public void setNombreResultado(String valor) {
		nombreResultado=valor;
	}
	
	/**
	 * @return
	 */
	public String getNombreResultado() {
		return nombreResultado;
	}
	
	/**
	 * @return
	 */
	public void setNombrePaginador(String valor) {
		nombrePaginador=valor;
	}

	/**
	 * @return
	 */
	public String getNombrePaginador() {
		return nombrePaginador;
	}
	
	/**
	 * @return
	 */
	public void setPaginaInicial(int valor) {
		paginaInicial=valor;
	}

	/**
	 * @return
	 */
	public int getPaginaInicial() {
		return paginaInicial;
	}
	
	

	/**
	 * @return
	 */
	public boolean isConsultarAnotacionesDefinitivas() {
		return consultarAnotacionesDefinitivas;
	}

	/**
	 * @param b
	 */
	public void setConsultarAnotacionesDefinitivas(boolean b) {
		consultarAnotacionesDefinitivas = b;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioNeg = usuario;
	}

	public boolean isGetAnotacionesConDeltadas() {
		return getAnotacionesConDeltadas;
	}

	public void setGetAnotacionesConDeltadas(boolean getAnotacionesConDeltadas) {
		this.getAnotacionesConDeltadas = getAnotacionesConDeltadas;
	}

	public void setConsultarAnotacionesFolioTemporal(boolean consultarAnotacionesFolioTemporal) {
		this.consultarAnotacionesFolioTemporal = consultarAnotacionesFolioTemporal;
	}

	public boolean isConsultarAnotacionesFolioTemporal() {
		return consultarAnotacionesFolioTemporal;
	}

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e Informáica.
     * Cambio realizado para resolver incidencia Mantis 0002399 - Requerimiento 117
     * @return the anotcionesDesdeV
     */
    public Integer getAnotcionesDesdeV() {
        return anotcionesDesdeV;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e Informáica.
     * Cambio realizado para resolver incidencia Mantis 0002399 - Requerimiento 117
     * @param anotcionesDesdeV the anotcionesDesdeV to set
     */
    public void setAnotcionesDesdeV(Integer anotcionesDesdeV) {
        this.anotcionesDesdeV = anotcionesDesdeV;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e Informáica.
     * Cambio realizado para resolver incidencia Mantis 0002399 - Requerimiento 117
     * @return the numAnotcionesTotalV
     */
    public Integer getNumAnotcionesTotalV() {
        return numAnotcionesTotalV;
    }

    /**
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e Informáica.
     * Cambio realizado para resolver incidencia Mantis 0002399 - Requerimiento 117
     * @param numAnotcionesTotalV the numAnotcionesTotalV to set
     */
    public void setNumAnotcionesTotalV(Integer numAnotcionesTotalV) {
        this.numAnotcionesTotalV = numAnotcionesTotalV;
    }

	/**
	 * @author Cesar Ramírez
	 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
	 * Getter y Setter rolID
	 **/
	public String getRolID() {
		return rolID;
	}

	public void setRolID(String rolID) {
		this.rolID = rolID;
	}
}
