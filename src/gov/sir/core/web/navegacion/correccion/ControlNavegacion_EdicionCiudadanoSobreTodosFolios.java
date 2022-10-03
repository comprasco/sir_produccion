package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.web.acciones.correccion.AwCorr_EdicionCiudadanoSobreTodosFolios;
/**
 * Control de Navegación
 *
 */
public class ControlNavegacion_EdicionCiudadanoSobreTodosFolios implements ControlNavegacion {

	// report xx - graph ------------------------------
        protected static final String EVNRESPOK_EDITCIUDADANO_ONLOAD
            = "EVNRESPOK_EDITCIUDADANO_ONLOAD";
        protected static final String EVNRESPOK_EDITCIUDADANO_ONLOCKFOLIOS
            = "EVNRESPOK_EDITCIUDADANO_ONLOCKFOLIOS";
        protected static final String EVNRESPOK_EDITCIUDADANO_ONAPPLY
            = "EVNRESPOK_EDITCIUDADANO_ONAPPLY";
        protected static final String EVNRESPOK_EDITCIUDADANO_ONCANCEL
            = "EVNRESPOK_EDITCIUDADANO_ONCANCEL";

	/**
	 *  Constructor por Default de <CODE>ControlNavegacionReportes</CODE>
	 */
	public ControlNavegacion_EdicionCiudadanoSobreTodosFolios() {
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
                }

                if( AwCorr_EdicionCiudadanoSobreTodosFolios.EDITCIUDADANOONLOAD__PAGERENDERING_PROCESS_ACTION.equals( action ) ) {
                  // verificaciones ...
                  // target-page (point)
                  return EVNRESPOK_EDITCIUDADANO_ONLOAD;
                }
                if( AwCorr_EdicionCiudadanoSobreTodosFolios.EDITCIUDADANOONLOCKFOLIOS__PAGEPROCESSING_PROCESS_ACTION.equals( action ) ) {
                  // verificaciones ...
                  // target-page (point)
                  return EVNRESPOK_EDITCIUDADANO_ONLOCKFOLIOS;
                }
                if( AwCorr_EdicionCiudadanoSobreTodosFolios.EDITCIUDADANOONAPPLY__PAGEPROCESSING_PROCESS_ACTION.equals( action ) ) {
                  // verificaciones ...
                  // target-page (point)
                  return EVNRESPOK_EDITCIUDADANO_ONAPPLY;
                }
                if( AwCorr_EdicionCiudadanoSobreTodosFolios.EDITCIUDADANOONCANCEL__PAGEPROCESSING_PROCESS_ACTION.equals( action ) ) {
                  // verificaciones ...
                  // target-page (point)
                  return EVNRESPOK_EDITCIUDADANO_ONCANCEL;
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
