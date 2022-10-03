package gov.sir.core.web.acciones.excepciones;

import java.util.Iterator;
import java.util.List;

/**
 * Excepcion para validar los campos para consultas en Calificación de Folio.
 * @author ppabon
 */
public class ConsultaCorreccionFolioException extends ValidacionParametrosException {

	/**
	 * Constructor de la clase.
	 */
	public ConsultaCorreccionFolioException() {
		super();
	}
	
	
	/**
	 * Constructor de la clase.
	 */
	public ConsultaCorreccionFolioException(List errores) {
		super();
		if(errores!=null && errores.size()>0){
			Iterator it = errores.iterator();
			while(it.hasNext()){
				this.addError((String)it.next());
			}
		}
	}	

}
