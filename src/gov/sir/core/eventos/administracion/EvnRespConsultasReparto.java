package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * @author mmunoz
 */
public class EvnRespConsultasReparto extends EvnSIRRespuesta {

	/** Constante que identifica la minuta*/	
	public static final String MINUTA_REPARTO = "MINUTA_REPARTO";


	/** Constante que identifica la Hashtable que tiene como llave el # del turno y como objeto la minuta*/
	public static final String TABLA_REPARTO_FECHA = "LISTA_REPARTO_FECHA";
	
	/** Constante que identifica la Hashtable que tiene como llave el # del turno y como objeto la minuta*/
	public static final String TABLA_REPARTO_OTORGANTE = "TABLA_REPARTO_OTORGANTE";
	
	/** Constante que identifica la Hashtable que tiene como llave el # del turno y como objeto la minuta*/
	public static final String TABLA_MINUTAS_PENDIENTES_REPARTO = "TABLA_MINUTAS_PENDIENTES_REPARTO";
	
	public static final String MINUTA_EDITADA = "MINUTA_EDITADA";
	
	public static final String MINUTA_IMPRESA= "MINUTA_IMPRESA";
	
	private List circulosNotariales;
	
	private List turnosRestitucion;
	
	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespConsultasReparto(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return
	 */
	public List getCirculosNotariales() {
		return circulosNotariales;
	}

	/**
	 * @param list
	 */
	public void setCirculosNotariales(List list) {
		circulosNotariales = list;
	}

	public List getTurnosRestitucion() {
		return turnosRestitucion;
	}

	public void setTurnosRestitucion(List turnosRestitucion) {
		this.turnosRestitucion = turnosRestitucion;
	}

}
