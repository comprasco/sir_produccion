package gov.sir.core.eventos.registro;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * @author pmunoz
 */
public class EvnRespMesaControl extends EvnSIRRespuesta {

	public static final String MESA_CONTROL = "MESA_CONTROL";
	public static final String CONSULTAR_TURNOS = "CONSULTAR_TURNOS";
	public LinkedList folios = null;
	public static final String ENVIAR_RESPUESTA = "ENVIAR_RESPUESTA";
    public static final String IMPRIMIR_CERTIFICADO = "IMPRIMIR_CERTIFICADO";
    public static final String CAMBIAR_MATRICULA = "CAMBIAR_MATRICULA";
    public static final String VALIDAR_CERTIFICADOS = "VALIDAR_CERTIFICADOS";
	/**
	 * Indicador accion cambiar consultar las matrículas válidas para el cambio de matrículas
	 */
    public static final String CARGAR_MATRICULAS_VALIDAS_CAMBIO = "CARGAR_MATRICULAS_VALIDAS_CAMBIO";

	/**
	* Constante que identifica que se desea consultar los turnos que pertenecen a una relación
	*/
	public static final String CONSULTAR_RELACION = "CONSULTAR_RELACION";
    
	private List validos;
	
	private Map turnosCertificadosValidos;	
	
	

	/**
	 * @param object
	 */
	public EvnRespMesaControl(LinkedList foliosAsociados) {
		super(foliosAsociados, EvnRespMesaControl.MESA_CONTROL);
		this.folios = foliosAsociados;
	}
	
	/**
	 * @param object, String
	 */
	public EvnRespMesaControl(Object payload, String tipoRespuesta) {
		super(payload, tipoRespuesta);
		
	}

	/**
	 * @param valido
	 */
	public void setValidos(List valido) {
		this.validos= valido;	
	}

	/**
	 * @return Returns the validos.
	 */
	public List getValidos() {
		return validos;
	}

	public Map getTurnosCertificadosValidos() {
		return turnosCertificadosValidos;
	}

	public void setTurnosCertificadosValidos(Map turnosCertificadosValidos) {
		this.turnosCertificadosValidos = turnosCertificadosValidos;
	}
}
