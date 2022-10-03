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
 * Clase que define la navegaci�n para la Acci�n Web, AWPagoFotocopia.
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
	 * M�todo de inicio
	 */
	public void doStart(HttpServletRequest arg0) {
	}



	/**
	* Este m�todo verifica el estado de los diferentes objetos que se encuentran
	* en la sesi�n, y de acuerdo a esto env�a una respuesta para que sea procesada
	* y asi poder tener una navegaci�n acertada.
	*/
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
          HttpSession session = request.getSession();

		String accion = (String) request.getParameter(WebKeys.ACCION);
                
                /** @author : HGOMEZ
                *** @change : Se obtiene de la sesi�n el proceso actual.
                *** Caso Mantis : 12288
                */
                Proceso proceso;
                proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La acci�n enviada por la acci�n web no es v�lida");
		}

		if( AWPagoFotocopia.ACEPTAR.equals( accion ) ) {
                  Liquidacion liquidacion = (Liquidacion)session.getAttribute( WebKeys.LIQUIDACION ) ;
                  /** @author : HGOMEZ
                  *** @change : Se agrega en la condici�n la verificaci�n
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
	 * M�todo para terminar el control de navegaci�n.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
