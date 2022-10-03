package gov.sir.core.eventos.registro;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.Turno;

/**
 * Evento utilizado para el envio de solicitudes con respecto a folios, 
 * a la capa de negocio
 * @author ppabon
 */
public class EvnRespDigitacionMasiva extends EvnSIRRespuesta {

	/**
	 * Constante que identifica que se desea bloquear los folios mientras se realiza el proceso de corrección
	 */
	public final static String TOMAR_FOLIO = "TOMAR_FOLIO";	
	
	/** 
	 * Constante para indicar que se desea realizar la consulta a un folio para cargar la información de un folio
	 * */
	public static final String CONSULTAR_FOLIO_DIGITACION_MASIVA = "CONSULTAR_FOLIO_DIGITACION_MASIVA";
	
	/** 
	 * Constante para indicar que se desea realizar una actualización a los folios indicados
	 * */
	public static final String EDITAR_FOLIO_DIGITACION_MASIVA = "EDITAR_FOLIO_DIGITACION_MASIVA";
	
	/** 
	 * Constante para indicar que se desea construir la complementación a partir de las anotaciones de un folio
	 * */
	public static final String CONSTRUIR_COMPLEMENTACION = "CONSTRUIR_COMPLEMENTACION";

	/**
	 * Constante para indicar que se quieren ver los detalles del folio
	 */
	public static final String VER_DETALLES_FOLIO = "VER_DETALLES_FOLIO";	
	
	
	/**
	 * Identifica el folio que se desea enviar a la capa web
	 */
	private Folio folio;
	
	/**
	 * Identifica el folio que se desea enviar a la capa web
	 */
	private Turno turno;	
	
	
	/**
	* Identifica el folio que se desea enviar a la capa web
	 */
	private LlaveBloqueo llaveBloqueo;
	
	/**
	* Identifica la lista de validaciones.
	*/
	private List validaciones;
	
	/**
	* Identifica la complementacion creada a partir de varias anotaciontes.
	*/
	private String complementacion;

	private long numeroAnotaciones;

	private List foliosPadre;

	private List foliosHijos;
        
        private List historialAreas;

	private String linderoTemporal;

	private boolean mayorExtensionFolio;

	private List cancelaciones;

	private List salvedadesAnotaciones;

	private List anotacionesInvalidas;

	private List falsaTradicion;

	private List medidasCautelares;

	private List gravamenes;

	private List anotacionesPatrimonioFamiliar;

	private List anotacionesAfectacionVivienda;
	
	private List dirTemporales = null;
			
		
	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespDigitacionMasiva(String tipoEvento,  LlaveBloqueo llavebloqueo, Turno turno) {
		super(tipoEvento, tipoEvento);
		this.turno = turno;
		this.llaveBloqueo = llavebloqueo;
	}    
	
	/**
	 * Crea el evento únicamente con el tipo de evento
	 * @param tipoEvento
	 */
	public EvnRespDigitacionMasiva(String tipoEvento) {
		super(tipoEvento, tipoEvento);
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
	public LlaveBloqueo getLlaveBloqueo() {
		return llaveBloqueo;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @return
	 */
	public List getValidaciones() {
		return validaciones;
	}

	/**
	 * @param list
	 */
	public void setValidaciones(List list) {
		validaciones = list;
	}

	/**
	 * @return
	 */
	public String getComplementacion() {
		return complementacion;
	}

        public List getHistorialAreas() {
            return historialAreas;
        }

        public void setHistorialAreas(List historialAreas) {
            this.historialAreas = historialAreas;
        }

	/**
	 * @param string
	 */
	public void setComplementacion(String string) {
		complementacion = string;
	}

	public long getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	public void setNumeroAnotaciones(long numeroAnotaciones) {
		this.numeroAnotaciones = numeroAnotaciones;
	}

	public void setFoliosPadre(List foliosPadre) {
		this.foliosPadre = foliosPadre;
	}

	public void setFoliosHijos(List foliosHijos) {
		this.foliosHijos = foliosHijos;
	}

	public void setGravamentes(List gravamenes) {
		this.gravamenes = gravamenes;
	}

	public void setMedidasCautelares(List medidasCautelares) {
		this.medidasCautelares = medidasCautelares;
	}

	public void setFalsaTradicion(List falsaTradicion) {
		this.falsaTradicion = falsaTradicion;
	}

	public void setAnotacionesInvalidas(List anotacionesInvalidas) {
		this.anotacionesInvalidas = anotacionesInvalidas;
	}

	public void setSalvedadesAnotaciones(List salvedadesAnotaciones) {
		this.salvedadesAnotaciones = salvedadesAnotaciones;
	}

	public void setCancelaciones(List cancelaciones) {
		this.cancelaciones = cancelaciones;
	}

	public void setMayorExtensionFolio(boolean mayorExtensionFolio) {
		this.mayorExtensionFolio = mayorExtensionFolio;
	}

	public void setLinderoTemporal(String linderoTemporal) {
		this.linderoTemporal = linderoTemporal;
	}

	public List getGravamenes() {
		return gravamenes;
	}

	public void setGravamenes(List gravamenes) {
		this.gravamenes = gravamenes;
	}

	public List getCancelaciones() {
		return cancelaciones;
	}

	public boolean isMayorExtensionFolio() {
		return mayorExtensionFolio;
	}

	public List getFoliosHijos() {
		return foliosHijos;
	}

	public List getFoliosPadre() {
		return foliosPadre;
	}

	public String getLinderoTemporal() {
		return linderoTemporal;
	}

	public List getMedidasCautelares() {
		return medidasCautelares;
	}

	public List getAnotacionesInvalidas() {
		return anotacionesInvalidas;
	}

	public List getFalsaTradicion() {
		return falsaTradicion;
	}

	public List getSalvedadesAnotaciones() {
		return salvedadesAnotaciones;
	}

	public void setLlaveBloqueo(LlaveBloqueo llaveBloqueo) {
		this.llaveBloqueo = llaveBloqueo;
	}

	public void setAnotacionesPatrimonioFamiliar(List anotacionesPatrimonioFamiliar) {
		this.anotacionesPatrimonioFamiliar = anotacionesPatrimonioFamiliar;
	}

	public void setAnotacionesAfectacionVivienda(List anotacionesAfectacionVivienda) {
		this.anotacionesAfectacionVivienda = anotacionesAfectacionVivienda;
	}

	public List getAnotacionesAfectacionVivienda() {
		return anotacionesAfectacionVivienda;
	}

	public List getAnotacionesPatrimonioFamiliar() {
		return anotacionesPatrimonioFamiliar;
	}

	public void setDirTemporales(List dirTemporales) {
		this.dirTemporales = dirTemporales;
	}
	
	public List getDirTemporales(){
		return dirTemporales;
	}
	
	private Folio folioDef;


	public Folio getFolioDef() {
		return folioDef;
	}

	public void setFolioDef(Folio folioDef) {
		this.folioDef = folioDef;
	}
	

}
