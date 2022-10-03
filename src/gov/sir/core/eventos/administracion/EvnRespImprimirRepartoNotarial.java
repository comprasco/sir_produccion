package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Circulo;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * con el traslado de turnos entre usuarios de un mismo círculo
 * @author jmendez
 */
public class EvnRespImprimirRepartoNotarial extends EvnSIRRespuesta {

	/**Esta constante se utiliza  para identificar el evento de impresión satisfactoria de 
	 * un folio */
	public static final String IMPRESION_CARATULA_OK = "IMPRESION_CARATULA_OK";

	/**Esta constante se utiliza  para identificar el evento de impresión satisfactoria de 
		 * un CERTIFICADO */
	public static final String IMPRESION_ACTA_OK = "IMPRESION_ACTA_OK";
	
	/** Constante que identifica la acción de reimprimir un  RECIBO  */
	public static final String REIMPRIMIR_RECIBO = "REIMPRIMIR_RECIBO";

	/** Constante que identifica la acción de reimprimir un  RECIBO  */
	public static final String REIMPRIMIR_CONSULTA_OK = "REIMPRIMIR_CONSULTA_OK";
	
	public static final String OBTENER_IMPRESORAS_CIRCULO = "OBTENER_IMPRESORAS_CIRCULO";
    
    public static final String OBTENER_ULTIMO_TURNO_IMPRESO_OK = "OBTENER_ULTIMO_TURNO_IMPRESO_OK";
    
	public static final String DETERMINAR_ESTACION = "DETERMINAR_ESTACION";    
	
	public static final String ULTIMO_TURNO_PROCESO = "ULTIMO_TURNO_PROCESO";

	/** Circulo asociado */
    private Circulo cir;
    private String turno;
    private List turnosAsociados;
    
    /**
    * Lista de notas informativas asociadas con la reimpresión de certificados.
    */
    private List listaNotasReimpresion;
    
    /**Lista con las posibles esación para reimprimir*/
    List estaciones = null;
	
	/**
	 * @param payload
	 */
	public EvnRespImprimirRepartoNotarial(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespImprimirRepartoNotarial(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}
	
	public Circulo getCirculo() {
		return cir;
	}

	public void setCirculo(Circulo cir) {
		this.cir = cir;
	}

	/**
	 * @return
	 */
	public List getListaNotasReimpresion() {
		return listaNotasReimpresion;
	}

	/**
	 * @param list
	 */
	public void setListaNotasReimpresion(List list) {
		listaNotasReimpresion = list;
	}

	/**
	 * @return
	 */
	public List getEstaciones() {
		return estaciones;
	}

	/**
	 * @param list
	 */
	public void setEstaciones(List list) {
		estaciones = list;
	}

	/**
	 * @return Returns the turno.
	 */
	public String getTurno() {
		return turno;
	}
	/**
	 * @param turno The turno to set.
	 */
	public void setTurno(String turno) {
		this.turno = turno;
	}
	/**
	 * @return Returns the turnosAsociados.
	 */
	public List getTurnosAsociados() {
		return turnosAsociados;
	}
	/**
	 * @param turnosAsociados The turnosAsociados to set.
	 */
	public void setTurnosAsociados(List turnosAsociados) {
		this.turnosAsociados = turnosAsociados;
	}
}
