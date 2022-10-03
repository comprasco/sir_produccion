package gov.sir.core.eventos.comun;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.Circulo;

/**
 * @author mmunoz
 */
public class EvnLocacion extends EvnSIR{

	/**Esta constante tiene el tipo de evento que carga las locaciones */
	public static final String CARGAR_LOCACION = "CARGAR_LOCACION";
	
	/**Circulo de las locaciones que se quieren cargar*/
	private Circulo circulo; 

	/**
	 * Constructor del evento con parametros.
	 * @param usuario org.auriga.core.modelo.transferObjects.Usuario
	 * @param circulo gov.sir.core.negocio.modelo.Circulo
	 */
	public EvnLocacion(Usuario usuario, Circulo circulo) {
		super(usuario,EvnLocacion.CARGAR_LOCACION);
		this.circulo = circulo;
	}

	/**
	 * Retorna el circulo que viaja en el evento
	 * @return gov.sir.core.negocio.modelo.Circulo
	 */
	public Circulo getCirculo() {
		return circulo;
	}

}
