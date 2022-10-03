package gov.sir.core.eventos.registro;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

import java.util.List;

/**
 * Esta clase encapsula todo el conocimiento generado como respuesta 
 * a una solicitud de tipo creación de Folio. 
 * @author dlopez
 */
public class EvnRespFolio extends EvnSIRRespuesta {
	
	//SIR-41(R1)
	
	/**
	 * Esta constante indica que la respuesta es a una solicitud de creación
	 * de folios. 
	 */
	public static final String CREAR = "CREAR";
	
	/**
	 * Esta constante indica que la respuesta es a una solicitud de consulta
	 * de folios. 
	 */
	public static final String CONSULTAR = "CONSULTAR";
	
	/**
	 * Esta constante indica que la respuesta es a una solicitud de consulta
	 * de lista folios. 
	 */
	public static final String CONSULTAR_LISTA = "CONSULTAR_LISTA";
	
	public static final String FOLIO_TEMPORAL = "FOLIO_TEMPORAL";

	public static final String ELIMINAR_DIRECCION = "ELIMINAR_DIRECCION";

	public static final String AGREGAR_DIRECCION = "AGREGAR_DIRECCION";
	
	public static final String ACTUALIZAR_FOLIO_COMPLEMENTACION = "ACTUALIZAR_FOLIO_COMPLEMENTACION";
	
	private List foliosDerivados;
	private List foliosPadre;
	private List foliosHijo;
	private List gravamenes;
	private List medidasCautelares;
	private List salvedadesAnotaciones;
	private List cancelaciones;
	private long numeroAnotaciones;
	private boolean esMayorExtension; 
	
	/**
	 * Folio que fue creado y hecho persistente. 
	 */
	private Folio folio;
	
	private List folios;
	
	private Turno turno;
	
	private List aTemporales;
	
	private List dirTemporales = null;
	
	
	/**
	 * Método constructor de un evento de respuesta para la creación de folios. 
	 * @param folioCreado El folio que fue hecho persistente.
	 * @param tipoRespuesta La respuesta a la solicitud de creación de folio. 
	 */
	public EvnRespFolio(Folio folioCreado, String tipoRespuesta) 
	{
		super(folioCreado, tipoRespuesta);
		this.folio = folioCreado;
	}
	
	public EvnRespFolio(List folios, String tipoRespuesta){
		super(folios,tipoRespuesta);
		this.folios=folios;
	}
	
	public EvnRespFolio(Folio folio,List aTemporales) {
		super(folio, EvnRespCalificacion.FOLIO_TEMPORAL);
		this.folio = folio;
		this.aTemporales=aTemporales;
	}

	/**
	 * Retorna el folio asociado a un Evento de Respuesta de creación de folios. 
	 * @return El folio sobre el que se ha hecho la persistencia. 
	 */
	public Folio getFolio() 
	{
		return folio;
	}
	/**
	 * @return
	 */
	public List getFolios() {
		return folios;
	}

	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
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
	public List getListATemp() {
		return aTemporales;
	}
	
	
	/**
	 * @return
	 */
	public List getFoliosHijo() {
		return foliosHijo;
	}
	
	/**
	 * @return
	 */
	public List getFoliosPadre() {
		return foliosPadre;
	}
	
	public boolean getMayorExtensionFolio() {
		return esMayorExtension;
	}
	
	/**
	 * @return
	 */
	public List getGravamenes() {
		return gravamenes;
	}
	
	/**
	 * @return
	 */
	public List getMedidasCautelares() {
		return medidasCautelares;
	}
	
	/**
	 * @return
	 */
	public List getSalvedadesAnotaciones() {
		return salvedadesAnotaciones;
	}
	
	/**
	 * @return
	 */
	public List getCancelaciones() {
		return cancelaciones;
	}
	
	/**
	 * @return
	 */
	public long getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	public List getDirTemporales() {
		return dirTemporales;
	}

	public void setDirTemporales(List dirTemporales) {
		this.dirTemporales = dirTemporales;
	}
}
