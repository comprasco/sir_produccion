package gov.sir.core.web.navegacion.repartonotarial;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.repartonotarial.AWRepartoNotarial;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Clase que define la navegación para la Acción Web, AWRepartoNotarial.
 * @author ppabon
 */
public class ControlNavegacionRepartoNotarial implements ControlNavegacion {

	/** Constante que indica que se realizó el reaprto de minutas entre las notarias satisfactoriamente*/
	public static final String REPARTO_OK = "REPARTO_OK";
	
	/** Constante que indica que se realizó el reaprto de minutas entre las notarias satisfactoriamente*/
	public static final String REPARTO_NOTARIAL_FAILED = "REPARTO_NOTARIAL_FAILED";

	/** Constante que indica que se entrego exitosamente el resultado del reparto de minutas*/
	public static final String ENTREGA_OK = "ENTREGA_OK";
	
	/** Constante que indica que se entrego exitosamente el resultado del reparto de minutas*/
	public static final String ENTREGA_MASIVA_OK = "ENTREGA_MASIVA_OK";

	/** Constante que indica que se entrego exitosamente el resultado del reparto de minutas. Pero el workflow que siguió fue el de fracaso.*/
	public static final String ENTREGA_FAILED = "ENTREGA_FAILED";
        /**
         * @author: Diana Lora
         * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         * En caso que se realice un reparto notarial extraordinario, se debe agregar una
         * nota informativa para dicho reparto.
         */
        public final static String AGREGAR_NOTA_INFORMATIVA = "AGREGAR_NOTA_INFORMATIVA";

	/**
	 * Método de inicio
	 */
	public void doStart(HttpServletRequest arg0) {
	}

	/**
	 *        Este método lo que hace es la verificación de los diferentes objectos que se encuentran en la sesión,
	 *        y de acuerdo a esa verificación manda una respuesta para que sea procesada y asi poder tener una
	 *        navegación acertada.
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La acción enviada por la acción web no es válida");
		}

		if (accion.equals(AWRepartoNotarial.EJECUTAR_REPARTO)) {
			if(request.getSession().getAttribute(WebKeys.REPARTO_NOTARIAL_FAILED) != null)
				return REPARTO_NOTARIAL_FAILED;
			else
				return REPARTO_OK;
		} else if (accion.equals(AWRepartoNotarial.NOTIFICAR_CIUDADANO_EXITO)) {
			return ENTREGA_OK;
		} else if (accion.equals(AWRepartoNotarial.NOTIFICAR_CIUDADANO_EXITO_MASIVA)) {
			return ENTREGA_OK;
		} else if (accion.equals(AWRepartoNotarial.NOTIFICAR_CIUDADANO_FRACASO)) {
			return ENTREGA_FAILED;
		} else if (accion.equals(AWRepartoNotarial.NOTIFICAR_CIUDADANO_FRACASO_MASIVA)) {
			return ENTREGA_FAILED;
		} else if (accion.equals(AWRepartoNotarial.AGREGAR_NOTA_INFORMATIVA)) {
			return AGREGAR_NOTA_INFORMATIVA;
		} else if (accion.equals(AWRepartoNotarial.COLOCAR_DESCRIPCION)) {
			return AGREGAR_NOTA_INFORMATIVA;
		} else if (accion.equals(AWRepartoNotarial.AGREGAR_NOTA)) {
			return AGREGAR_NOTA_INFORMATIVA;
		} else if (accion.equals(AWRepartoNotarial.IMPRIMIR_NOTA_INFORMATIVA)) {
			return AGREGAR_NOTA_INFORMATIVA;
		} else if (accion.equals(AWRepartoNotarial.CANCELAR_REPARTO_NOTARIAL)) {
			return REPARTO_NOTARIAL_FAILED;
		}
		//Cargar listado de turnos de restitución asociados con una minuta.
		else if (accion.equals(AWRepartoNotarial.CARGAR_TURNOS_RESTITUCION_MINUTA)){
			return AWRepartoNotarial.CARGAR_TURNOS_RESTITUCION_MINUTA;
		}
		
		else {
			return null;
		}
	}

	/**
	 * Método para terminar el control de navegación.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
