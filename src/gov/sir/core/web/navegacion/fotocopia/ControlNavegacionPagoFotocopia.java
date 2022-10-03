package gov.sir.core.web.navegacion.fotocopia;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.fotocopia.AWPagoFotocopia;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.eventos.fotocopia.EvnRespFotocopia;
import gov.sir.core.negocio.modelo.Liquidacion;
/** @author : HGOMEZ
*** @change : Se habilita el uso del paquete Proceso
*** en esta clase.
*** Caso Mantis : 12288
*/
import gov.sir.core.negocio.modelo.Proceso;
import javax.servlet.http.HttpSession;

/**
 * Clase que define la navegación para la Acción Web, AWPagoFotocopia.
 * @author dlopez
 */
public class ControlNavegacionPagoFotocopia implements ControlNavegacion {


	/**
	* Constante que indica que se debe procesar el pago.
	*/
	public static final String PROCESAR_PAGO_FOTOCOPIAS = "PROCESAR_PAGO_FOTOCOPIAS";


	/**
	* Constante que indica que no se debe procesar el pago  y que se debe
	* terminar el turno.
	*/
	public static final String FINALIZAR_TURNO = "FINALIZAR_TURNO";


	/**
	 * Método de inicio
	 */
	public void doStart(HttpServletRequest arg0) {
	}



	/**
	* Este método verifica el estado de los diferentes objetos que se encuentran
	* en la sesión, y de acuerdo a esto envía una respuesta para que sea procesada
	* y asi poder tener una navegación acertada.
	*/
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
          HttpSession session = request.getSession();

		String accion = (String) request.getParameter(WebKeys.ACCION);
                
                /** @author : HGOMEZ
                *** @change : Se obtiene de la sesión el proceso actual.
                *** Caso Mantis : 12288
                */
                Proceso proceso;
                proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La acción enviada por la acción web no es válida");
		}

		if( AWPagoFotocopia.ACEPTAR.equals( accion ) ) {
                  Liquidacion liquidacion = (Liquidacion)session.getAttribute( WebKeys.LIQUIDACION ) ;
                  /** @author : HGOMEZ
                  *** @change : Se agrega en la condición la verificación
                  *** del proceso 5 Fotocopia.
                  *** Caso Mantis : 12288
                  */
                  if(!(proceso.getIdProceso() == 5)  && ( null!= liquidacion )
                    &&( liquidacion.getValor() == 0d ) ) {
                    return EvnRespFotocopia.PAGOFOTOCOPIASZEROVALUE_EVENTRESP_OK;
                  }
		  return PROCESAR_PAGO_FOTOCOPIAS;
		}
		else if (accion.equals(AWPagoFotocopia.CANCELAR)) {
					return FINALIZAR_TURNO;
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
