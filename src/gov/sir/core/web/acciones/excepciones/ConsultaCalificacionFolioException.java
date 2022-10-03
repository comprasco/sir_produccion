package gov.sir.core.web.acciones.excepciones;

import java.util.Iterator;
import java.util.List;


/**
 * Excepcion para validar los campos para consultas en Calificación de Folio.
 * @author ppabon
 */
public class ConsultaCalificacionFolioException extends ValidacionParametrosException {

	/**
	 * Constructor de la clase.
	 */
	public ConsultaCalificacionFolioException() {
		super();
	}
	
	/**
	 * Constructor de la clase.
	 */
	public ConsultaCalificacionFolioException(List errores) {
		super();
		if(errores!=null && errores.size()>0){
			Iterator it = errores.iterator();
			while(it.hasNext()){
				this.addError((String)it.next());
			}
		}
	}

}
