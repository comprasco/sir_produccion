package gov.sir.core.web.acciones.excepciones;

import java.util.Iterator;
import java.util.List;

/**
 * Excepcion para validar los campos para consultas en Calificación de Folio.
 * @author ppabon
 */
public class ConsultaFolioEspecializadoException extends ValidacionParametrosException {

	/**
	 * Constructor de la clase.
	 */
	public ConsultaFolioEspecializadoException() {
		super();
	}

	/**
	 * Constructor de la clase.
	 */
	public ConsultaFolioEspecializadoException(List errores) {
		super();
		if(errores!=null && errores.size()>0){
			Iterator it = errores.iterator();
			while(it.hasNext()){
				this.addError((String)it.next());
			}
		}
	}

}
