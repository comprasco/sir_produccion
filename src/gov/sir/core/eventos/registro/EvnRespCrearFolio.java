package gov.sir.core.eventos.registro;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author ppabon
 * Clase que envia el nuevo folio y turno a la capa web, cuando se ha creado un nuevo folio
 */
public class EvnRespCrearFolio extends EvnSIRRespuesta {
    
	public static final String CREACION_FOLIO="CREACION_FOLIO";    
	public static final String ELIMINAR_FOLIO_TEMPORAL="ELIMINAR_FOLIO_TEMPORAL";	
	/**Constante que indica realizar las vaslidaciones del ciudadano de una anotacion*/
	public static final String VALIDAR_ANOTACION_CIUDADANO="VALIDAR_ANOTACION_CIUDADANO";	
	
    private Turno turno;
    
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
    
    
	public EvnRespCrearFolio(Folio folio, String tipoEvento, Turno turno) {
		super(folio, tipoEvento);
		this.turno = turno;
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

}
