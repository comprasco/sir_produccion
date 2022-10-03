package gov.sir.core.negocio.acciones.excepciones;

import java.util.List;

/**
 * @author ppabon
 * Excepción lanzada cuando se intenta consultar un folio y ocurre un error en la consulta.
 * Principalmente se busca controlar que usuarios de un circulo no puedan consultar folios de otros circulos.
 */
public class ConsultaFolioException extends ValidacionParametrosException {

	/**
	 * Constructor de la axcepción
	 */
	public ConsultaFolioException() {
		super();
	}

	/**
	 * Constructor para excepciones de tipo ConsultaFolioException.
	 * @param l Lista con todos los errores asociados. 
	 */
	public ConsultaFolioException(List l) {
		super(l);
	}

}