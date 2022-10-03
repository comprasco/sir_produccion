package gov.sir.core.eventos.registro;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.WebSegEng;

/**
 * Evento utilizado para el envio de solicitudes con respecto a folios, 
 * a la capa de negocio
 * @author ppabon
 */
public class EvnRespSegregacion extends EvnSIRRespuesta {

	/** Constante para llamar a la acción que guarda una anotación en el objeto WebSegregacion*/
	public final static String CONSULTA_FOLIO = "CONSULTA_FOLIO";	
	
	/** Constante para llamar a la acción que guarda el objeto websegregación*/
	public final static String GUARDAR_WEB_SEGREGACION = "GUARDAR_WEB_SEGREGACION";		
	
	/** Constante para llamar a la acción que guarda una anotación en el objeto WebSegregacion*/
	public final static String CONSULTA_ANOTACIONES_TEMPORALES = "CONSULTA_ANOTACIONES_TEMPORALES";			
	
	/** Constante para llamar a la acción que muestra la confirmación de lo que sucedió en el proceso de segregación*/
	public final static String SEGREGACION_FOLIO = "SEGREGACION_FOLIO";
	
	/** Constante para llamar a la acción que consulta el nuevo folios segregado*/
	public final static String CONSULTA_NUEVO_FOLIO = "CONSULTA_NUEVO_FOLIO";	
	
	public static final String CONSULTAR_ULTIMOS_PROPIETARIOS ="CONSULTAR_ULTIMOS_PROPIETARIOS";
	
	public static final String CONSULTAR_PROPIETARIOS_FOLIO ="CONSULTAR_PROPIETARIOS_FOLIO";	
	
	public static final String VOLVER_AGREGAR_CIUDADANOS ="VOLVER_AGREGAR_CIUDADANOS";
	
	
	/**
	 * Identifica los folios que foeron creados en el proceso de segregación en registro de documentos.
	 */
	private List foliosDerivados;
	
	/**
	 * Identifica el Turno. Es usado para devolver el turno con los nuevos SolicitudFolios asociados.
	 */
	private Turno turno;
	
	/**
	 * Identifica el objeto con toda la información referente a la segregación.
	 */	
	private WebSegEng webSegEng;
	
	private List propietariosFolios;
	
	private List listaCompletaCiudadanos;

	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespSegregacion(String orden, String tipoEvento) {
		super(orden, tipoEvento);
	}    
	
	
	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespSegregacion(Folio folio, WebSegEng webSegEng , String tipoEvento) {
		super(folio, tipoEvento);
		this.webSegEng = webSegEng;
	}    	
	
	/**
	 * Crea el evento de respuesta con el tipo de objeto WebSegEng
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespSegregacion( WebSegEng webSegEng , String tipoEvento) {
		super(webSegEng, tipoEvento);
		this.webSegEng = webSegEng;
	}    		

	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespSegregacion(List foliosDerivados, WebSegEng webSegEng , String tipoEvento) {
		super(foliosDerivados, tipoEvento);
		this.foliosDerivados = foliosDerivados;
		this.webSegEng = webSegEng;
	}    



	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespSegregacion(List foliosDerivados, String tipoEvento, Turno turno) {
		super(foliosDerivados, tipoEvento);
		this.foliosDerivados = foliosDerivados;
		this.turno = turno;
	}    
	
	public EvnRespSegregacion(String mensaje) {
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
	 * @param eng
	 */
	public void setWebSegEng(WebSegEng eng) {
		webSegEng = eng;
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
