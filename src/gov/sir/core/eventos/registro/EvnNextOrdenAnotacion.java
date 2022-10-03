package gov.sir.core.eventos.registro;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.eventos.comun.EvnSIR;

/**
 * Evento para obtener el siguiente codigo de orden para la anotacion
 * @author dsalas
 */
public class EvnNextOrdenAnotacion extends EvnSIR {

	/**
	 * Constante que indica que se va a averiguar el codigo de orden de la siguiente anotacion a 
	 * insertar
	 * */
	public static final String NEXT_ORDEN_ANOTACION = "NEXT_ORDEN_ANOTACION";

	/**
	* Folio del que se va a obtener el siguiente orden de anotación  
	*/
	private Folio folio;
	
	/**
	 * @param usuario
	 * @param turno
	 * @param tipoEvento
	 * @param folio
	 * @param usuarioSIR
	 */
	public EvnNextOrdenAnotacion(Usuario usuario, Folio folio) {
		super(usuario, NEXT_ORDEN_ANOTACION);
		this.folio = folio;
	}    

	/**
	 * @return folio
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	} 

}
