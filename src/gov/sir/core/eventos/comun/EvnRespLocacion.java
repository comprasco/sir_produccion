package gov.sir.core.eventos.comun;

import java.util.List;

import gov.sir.core.negocio.modelo.Circulo;

/**
 * @author mmunoz
 */
public class EvnRespLocacion extends EvnSIRRespuesta{

	/**Lista donde se guardan los departamentos del circulo*/
	private List departamentos;
	
	/**Se guarda el circulo de donde se pidieron los departamentos*/
	private Circulo circulo;

	/**
	 * Indica que este evento de respuesta esta devolviendo una lista de departamentos.
	 * Es necesario entonces hacer el cast correspondiente cuando se llame al metodo
	 * <CODE>getDepartamentos()</CODE>
	 */    
	public static final String LISTA_DEPTOS = "LISTA_DEPTOS";

	/**
	 * Constructor del evento con parametros.
	 * @param departamentos List
	 * @param circulo gov.sir.core.negocio.modelo.Circulo
	 */
	public EvnRespLocacion(List departamentos,Circulo circulo) {
		super(departamentos,EvnRespLocacion.LISTA_DEPTOS);
		this.departamentos = departamentos;
		this.circulo = circulo;
	}
	
	/**
	 * Metodo que retorna la lista de los departamentos del circulo
	 * seleccionado
	 * @return List
	 */
	public List getDepartamentos() {
		return departamentos;
	}
	
	

	/**
	 * Metodo que retorna el circulo del cual se saco la lista de lista
	 * de los departamentos
	 * @return Circulo gov.sir.core.negocio.modelo.Circulo
	 */
	public Circulo getCirculo() {
		return circulo;
	}

}
