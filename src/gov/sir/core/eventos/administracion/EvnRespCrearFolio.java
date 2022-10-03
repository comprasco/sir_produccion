package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Anotacion;

/**
 * @author ppabon
 * Clase que envia el nuevo folio y turno a la capa web, cuando se ha creado un nuevo folio
 */
public class EvnRespCrearFolio extends EvnSIRRespuesta {
    
	public static final String CREACION_FOLIO="CREACION_FOLIO";
	public static final String VALIDAR_MATRICULA_CREACION_FOLIO_OK = "VALIDAR_MATRICULA_CREACION_FOLIO_OK";
	public static final String AGREGAR_ANOTACION_OK = "AGREGAR_ANOTACION_OK";
		public static final String VALIDAR_MATRICULA_CREACION_FOLIO_UPDATING_OK 
	 = "VALIDAR_MATRICULA_CREACION_FOLIO_UPDATING_OK";
	public static final String CANCELAR_ANOTACIONES_OK = "CANCELAR_ANOTACIONES_OK";
	public static final String AGREGAR_SEGREGACION_ENGLOBE_OK = "AGREGAR_SEGREGACION_ENGLOBE_OK";
	
	public static final String AGREGAR_DERIVACION_OK = "AGREGAR_DERIVACION_OK";
	public static final String ELIMINAR_DERIVACION_OK = "ELIMINAR_DERIVACION_OK";
	
	public static final String AGREGAR_ENGLOBE_OK = "AGREGAR_ENGLOBE_OK";
	public static final String ELIMINAR_ENGLOBE_OK = "ELIMINAR_ENGLOBE_OK";
	
	public static final String VOLVER_AGREGAR_CIUDADANOS ="VOLVER_AGREGAR_CIUDADANOS";
	
	
	private List foliosHijo;
	private List foliosPadre;
	private List gravamenes;
	private List medidasCautelares;
	private List salvedadesAnotaciones;
	private List falsaTradicion;
	private List anotacionesInvalidas;
	private List cancelaciones;
	private long numeroAnotaciones;
	private boolean esMayorExtension;
	private List aTemporales;
	private String linderoTemporal;
	private long anotacionesNextOrden;
	
	private List listaCompletaCiudadanos;
	
    
	/**Lista de anotaciones agregadas de un folio. Por cada una que se agregue, se muestra
	 * el listado de anotaciones en la parte superior de la patanlla de creacion de anotaciones*/
	private List anotacionesAgregadas = null;

	/**Anotacion que se va a agregar*/
	private Anotacion anotacion = null;
	
	/**Orden de la anotacion*/
	private String nextOrden;	
	
    public Anotacion getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(Anotacion anotacion) {
		this.anotacion = anotacion;
	}

	public List getAnotacionesAgregadas() {
		return anotacionesAgregadas;
	}

	public void setAnotacionesAgregadas(List anotacionesAgregadas) {
		this.anotacionesAgregadas = anotacionesAgregadas;
	}

	public String getNextOrden() {
		return nextOrden;
	}

	public void setNextOrden(String nextOrden) {
		this.nextOrden = nextOrden;
	}

	/** @param payload */
    public EvnRespCrearFolio(Object payload) {
        super(payload);
    }

    /** @param payload
     /** @param tipoEvento */
    public EvnRespCrearFolio(Object payload, String tipoEvento) {
        super(payload, tipoEvento);
    }

	public List getAnotacionesInvalidas() {
		return anotacionesInvalidas;
	}

	public void setAnotacionesInvalidas(List anotacionesInvalidas) {
		this.anotacionesInvalidas = anotacionesInvalidas;
	}

	public List getATemporales() {
		return aTemporales;
	}

	public void setATemporales(List temporales) {
		aTemporales = temporales;
	}

	public List getCancelaciones() {
		return cancelaciones;
	}

	public void setCancelaciones(List cancelaciones) {
		this.cancelaciones = cancelaciones;
	}

	public boolean isEsMayorExtension() {
		return esMayorExtension;
	}

	public void setEsMayorExtension(boolean esMayorExtension) {
		this.esMayorExtension = esMayorExtension;
	}

	public List getFalsaTradicion() {
		return falsaTradicion;
	}

	public void setFalsaTradicion(List falsaTradicion) {
		this.falsaTradicion = falsaTradicion;
	}

	public List getFoliosHijo() {
		return foliosHijo;
	}

	public void setFoliosHijo(List foliosHijo) {
		this.foliosHijo = foliosHijo;
	}

	public List getFoliosPadre() {
		return foliosPadre;
	}

	public void setFoliosPadre(List foliosPadre) {
		this.foliosPadre = foliosPadre;
	}

	public List getGravamenes() {
		return gravamenes;
	}

	public void setGravamenes(List gravamenes) {
		this.gravamenes = gravamenes;
	}

	public String getLinderoTemporal() {
		return linderoTemporal;
	}

	public void setLinderoTemporal(String linderoTemporal) {
		this.linderoTemporal = linderoTemporal;
	}

	public List getMedidasCautelares() {
		return medidasCautelares;
	}

	public void setMedidasCautelares(List medidasCautelares) {
		this.medidasCautelares = medidasCautelares;
	}

	public long getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	public void setNumeroAnotaciones(long numeroAnotaciones) {
		this.numeroAnotaciones = numeroAnotaciones;
	}

	public List getSalvedadesAnotaciones() {
		return salvedadesAnotaciones;
	}

	public void setSalvedadesAnotaciones(List salvedadesAnotaciones) {
		this.salvedadesAnotaciones = salvedadesAnotaciones;
	}

	public long getAnotacionesNextOrden() {
		return anotacionesNextOrden;
	}

	public void setAnotacionesNextOrden(long anotacionesNextOrden) {
		this.anotacionesNextOrden = anotacionesNextOrden;
	}

	public List getListaCompletaCiudadanos() {
		return listaCompletaCiudadanos;
	}

	public void setListaCompletaCiudadanos(List listaCompletaCiudadanos) {
		this.listaCompletaCiudadanos = listaCompletaCiudadanos;
	}
    
}
