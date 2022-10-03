package gov.sir.core.eventos.registro;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.WebSegEng;

/**
 * Evento utilizado para el envio de solicitudes con respecto a folios, 
 * a la capa de negocio
 * @author ppabon
 */
public class EvnRespEnglobe extends EvnSIRRespuesta {

	/** Constante para llamar a la acción que guarda una anotación en el objeto WebEnglobe*/
	public final static String CONSULTA_FOLIO = "CONSULTA_FOLIO";	
	
	/** Constante para llamar a la acción que guarda una anotación en el objeto WebEnglobe*/
	public final static String CONSULTA_ANOTACIONES_TEMPORALES = "CONSULTA_ANOTACIONES_TEMPORALES";			
	
	/** Constante para llamar a la acción que guarda una anotación en el objeto WebEnglobe*/
	public final static String ENGLOBAR_FOLIOS = "ENGLOBAR_FOLIOS";			
	
	/** Constante para llamar a la acción que guarda el objeto WebEnglobe*/
	public final static String GUARDAR_WEB_ENGLOBE = "GUARDAR_WEB_ENGLOBE";		
	
	public static final String CONSULTAR_ULTIMOS_PROPIETARIOS ="CONSULTAR_ULTIMOS_PROPIETARIOS";
	
	public static final String CONSULTAR_PROPIETARIOS_FOLIO ="CONSULTAR_PROPIETARIOS_FOLIO";	
	
	public static final String VOLVER_AGREGAR_CIUDADANOS ="VOLVER_AGREGAR_CIUDADANOS";
	
	
	/**
	 * Identifica los folios que foeron creados en el proceso de segregación en registro de documentos.
	 */
	private List foliosDerivados;
	
	/**
	 * Identifica el folio que se desea enviar a la capa web
	 */
	private Folio folio;
	
	/**
	 * Identifica el folio que se desea enviar a la capa web
	 */
	private Turno turno;	
	
	/**
	 * Identifica el objeto con los datos del englobe
	 */
	private WebSegEng webSegEng;	

	/**
	 * Identifica el objeto con la oficina origen
	 */		
	private OficinaOrigen oficinaOrigen;
		
	private List propietariosFolios;
	
	private List listaCompletaCiudadanos;

	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespEnglobe(String orden, String tipoEvento) {
		super(orden, tipoEvento);
	}    

	
	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespEnglobe(Folio folio, String tipoEvento) {
		super(folio, tipoEvento);
	}    	
	

	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespEnglobe(WebSegEng webSegEng, String tipoEvento) {
		super(webSegEng, tipoEvento);
		this.webSegEng = webSegEng;
	}    		
	
	

	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespEnglobe(Folio folio, String tipoEvento, Turno turno) {
		super(folio, tipoEvento);
		this.turno = turno;
	}    	


	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespEnglobe(List foliosDerivados, String tipoEvento) {
		super(foliosDerivados, tipoEvento);
		this.foliosDerivados = foliosDerivados;
	}    

	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespEnglobe(List foliosDerivados, String tipoEvento, Folio folio ) {
		super(foliosDerivados, tipoEvento);
		this.foliosDerivados = foliosDerivados;
		this.folio = folio;
	}    
	
	public EvnRespEnglobe(String mensaje) {
		super(mensaje);
	}


	/**
	 * @return
	 */
	public List getFoliosDerivados() {
		return foliosDerivados;
	}

	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}



	/**
	 * @return
	 */
	public WebSegEng getWebSegEng() {
		return webSegEng;
	}

	/**
	 * @return
	 */
	public OficinaOrigen getOficinaOrigen() {
		return oficinaOrigen;
	}

	/**
	 * @param origen
	 */
	public void setOficinaOrigen(OficinaOrigen origen) {
		oficinaOrigen = origen;
	}


	public List getPropietariosFolios() {
		return propietariosFolios;
	}


	public void setPropietariosFolios(List propietariosFolios) {
		this.propietariosFolios = propietariosFolios;
	}


	public List getListaCompletaCiudadanos() {
		return listaCompletaCiudadanos;
	}


	public void setListaCompletaCiudadanos(List listaCompletaCiudadanos) {
		this.listaCompletaCiudadanos = listaCompletaCiudadanos;
	}

}
