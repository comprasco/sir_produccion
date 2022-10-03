package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.web.acciones.correccion.AwCorr_MesaControl;
/**
 * Control de Navegaci�n
 *
 */
public class ControlNavegacion_MesaControl implements ControlNavegacion {

	// report xx - graph ------------------------------
        protected static final String EVNRESPOK_MESACONTROL_ONOK
            = "EVNRESPOK_MESACONTROL_ONOK";
        
        protected static final String IMPRIMIR_MESA     = "IMPRIMIR_MESA";
        
        protected static final String VOLVER_MESA    = "VOLVER_MESA";

	/**
	 *  Constructor por Default de <CODE>ControlNavegacionReportes</CODE>
	 */
	public ControlNavegacion_MesaControl() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegaci�n.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * M�todo que procesa la siguiente acci�n de navegaci�n dentro del flujo de pantallas
	 *
	 * @param request
	 * @return nombre de la acci�n siguiente
	 * @throws ControlNavegacionException
	 */
	public String
        procesarNavegacion( HttpServletRequest request )
        throws ControlNavegacionException {

		String action = (String) request.getParameter( WebKeys.ACCION );
		HttpSession sesion = request.getSession();

                if( ( null == action )
                  ||( "".equals( action ) ) ) {
                  throw new ControlNavegacionException( "No se ha definido destino v�lido para el control de la navegacion" );
                } else if( AwCorr_MesaControl.MESACONTROLONOK__PAGEPROCESSING_PROCESS_ACTION.equals( action ) ) {
                  // verificaciones ...
                  // target-page (point)
                  return EVNRESPOK_MESACONTROL_ONOK;
                } else if (AwCorr_MesaControl.IMPRIMIR_MESA.equals( action )) {
        			return IMPRIMIR_MESA;
        		} else if (AwCorr_MesaControl.IMPRIMIR.equals( action )) {
        			return IMPRIMIR_MESA;
        		} else if (AwCorr_MesaControl.VOLVER_MESA.equals( action )) {
        			return VOLVER_MESA;
        		} 

              return null;

	}

	/**
	 * Finalizaci�n de la navegaci�n
	 *
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
