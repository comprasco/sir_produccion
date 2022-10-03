package gov.sir.core.eventos.registro;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;

/**
 * @author mmunoz, jfrias
 */
public class EvnRespRevision extends EvnSIRRespuesta {

	public static final String SEGREGACION_MASIVA = "SEGREGACION_MASIVA";
	public static final String FOLIO_TEMPORAL = "FOLIO_TEMPORAL";
	private List foliosDerivados;
	private List foliosPadre;
	private List foliosHijo;
	private List gravamenes;
	private List medidasCautelares;
	private long numeroAnotaciones;
	private Folio folio;
	private boolean esMayorExtension; 
	private boolean validoAprobarCalificacion = false;
	private List falsaTradicion;
	private List anotacionesInvalidas;		

	/**
	 * @param object
	 */
	public EvnRespRevision(List foliosDerivados) {
		super(foliosDerivados, EvnRespCalificacion.SEGREGACION_MASIVA);
		this.foliosDerivados = foliosDerivados;
	}

	public EvnRespRevision(Folio folio) {
		super(folio, EvnRespCalificacion.FOLIO_TEMPORAL);
		this.folio = folio;
	}

	public EvnRespRevision(boolean valida) {
		super(EvnRevision.VALIDAR_APROBAR_CALIFICACION);
		this.setValidoAprobarCalificacion(valida);
	}

	public EvnRespRevision(Folio folio, List foliosPadre, List foliosHijo, List gravamenes, List medidasCautelares,  List falsaTradicion, List anotacionesInvalidas, long numeroAnotaciones, boolean esMayorExtension) {
		this(folio);
		this.foliosHijo = foliosHijo;
		this.foliosPadre = foliosPadre;
		this.gravamenes = gravamenes;
		this.medidasCautelares = medidasCautelares;
		this.numeroAnotaciones = numeroAnotaciones;
		this.falsaTradicion = falsaTradicion;
		this.anotacionesInvalidas = anotacionesInvalidas;
		this.esMayorExtension = esMayorExtension;
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

	public boolean getMayorExtensionFolio() {
		return esMayorExtension;
	}

	/**
	 * @return
	 */
	public long getNumeroAnotaciones() {
		return numeroAnotaciones;
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
	public boolean isValidoAprobarCalificacion() {
		return validoAprobarCalificacion;
	}

	/**
	 * @param b
	 */
	public void setValidoAprobarCalificacion(boolean b) {
		validoAprobarCalificacion = b;
	}

	/**
	 * @return
	 */
	public List getAnotacionesInvalidas() {
		return anotacionesInvalidas;
	}

	/**
	 * @return
	 */
	public List getFalsaTradicion() {
		return falsaTradicion;
	}

}
