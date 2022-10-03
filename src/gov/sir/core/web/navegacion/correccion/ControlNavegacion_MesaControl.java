package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.web.acciones.correccion.AwCorr_MesaControl;
/**
 * Control de Navegación
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
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
	 *
	 * @param request
	 * @return nombre de la acción siguiente
	 * @throws ControlNavegacionException
	 */
	public String
        procesarNavegacion( HttpServletRequest request )
        throws ControlNavegacionException {

		String action = (String) request.getParameter( WebKeys.ACCION );
		HttpSession sesion = request.getSession();

                if( ( null == action )
                  ||( "".equals( action ) ) ) {
                  throw new ControlNavegacionException( "No se ha definido destino válido para el control de la navegacion" );
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
	 * Finalización de la navegación
	 *
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
