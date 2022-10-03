package gov.sir.core.eventos.antiguosistema;

import java.util.List;
import java.util.Vector;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author jfrias
 *
 */
public class EvnRespAntiguoSistema extends EvnSIRRespuesta {
    
    public static final String ASOCIAR_MATRICULAS="ASOCIAR_MATRICULAS";
    public static final String ELIMINAR_MATRICULAS="ELIMINAR_MATRICULAS";
    public static final String CONSULTAR_MATRICULA="CONSULTAR_MATRICULA";
    public static final String CONSULTA_FOLIO = "CONSULTA_FOLIO"; 
    public static final String AVANZAR_CORRECCIONES = "AVANZAR_CORRECCIONES_OK";
    
    public static final String VOLVER_AGREGAR_CIUDADANOS ="VOLVER_AGREGAR_CIUDADANOS";
    
    public static final String CONSULTAR_ANOTACION_POR_ORDEN = "CONSULTAR_ANOTACION_POR_ORDEN";
    private String anho;
    private Turno turno;
    
    private List listaCompletaCiudadanos;
    
    private Vector anotaciones;
    
    /** @param payload */
    public EvnRespAntiguoSistema(Object payload) {
        super(payload);
        // TODO Auto-generated constructor stub
    }

    /** @param payload
     /** @param tipoEvento */
    public EvnRespAntiguoSistema(Object payload, String tipoEvento) {
        super(payload, tipoEvento);
        // TODO Auto-generated constructor stub
    }
    
    
	public EvnRespAntiguoSistema(Folio folio, String tipoEvento, String anho) {
		super(folio, tipoEvento);
		this.anho = anho;
	}

	/**
	 * @return
	 */
	public String getAnho() {
		return anho;
	}

	public Turno getTurno() {
		return turno;
	}
	
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public List getListaCompletaCiudadanos() {
		return listaCompletaCiudadanos;
	}

	public void setListaCompletaCiudadanos(List listaCompletaCiudadanos) {
		this.listaCompletaCiudadanos = listaCompletaCiudadanos;
	}

	public Vector getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(Vector anotaciones) {
		this.anotaciones = anotaciones;
	}

}
