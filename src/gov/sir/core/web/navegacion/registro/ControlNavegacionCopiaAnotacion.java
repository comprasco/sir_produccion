package gov.sir.core.web.navegacion.registro;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWCopiaAnotacion;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.negocio.modelo.CopiaAnotacion;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.web.navegacion.correccion.ControlNavegacionModificacionFolio;
import javax.servlet.http.HttpSession;

/**
 * Clase que define la navegación para la Acción Web, AWCopiaAnotacion de las opciones de copia de anotaciones y cancelaciones.
 * @author ppabon
*/
public class ControlNavegacionCopiaAnotacion implements ControlNavegacion {


    /** wz-pasox: [cancelar] = redireccion a correcciones */
    public static final String OPCIONCOPIAANOTACION_PASOX_BTNCANCELAR_OK_CORRECCIONES
        = "OPCIONCOPIAANOTACION_PASOX_BTNCANCELAR_OK_CORRECCIONES";

    /** wz-pasox: [cancelar] = redireccion a registro */
    public static final String OPCIONCOPIAANOTACION_PASOX_BTNCANCELAR_OK_REGISTRO
       = "OPCIONCOPIAANOTACION_PASOX_BTNCANCELAR_OK_REGISTRO";


	/**
	* Acción que indica que se desean seleccionar las anotaciones a cancelar.
	*/
	public static final String CANCELADA ="CANCELADA";

	/**
	* Acción que indica que se desea corregir las anotaciones a cancelar.
	*/
	public static final String CORRECCION_CANCELADA ="CORRECCION_CANCELADA";

	/**
	* Acción que indica que se desean regresar a la calificación del folio.
	*/
	public static final String REGRESAR_CALIFICACION ="REGRESAR_CALIFICACION";



	/**
	 * Crea una instancia del control de navegacion
	 */
	public ControlNavegacionCopiaAnotacion() {
		super();
	}

	/**
	*/
	public void doStart(HttpServletRequest request) {

	}

	/**
	* Método que genera una respuesta de acuerdo a la acción recibida en el request.
	*/
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
                HttpSession session = request.getSession();

		String accion = (String) request.getParameter(WebKeys.ACCION);


		if (accion.equals(AWCopiaAnotacion.COPIAR_ANOTACION)) {
			List canceladas = (List) request.getSession().getAttribute(AWCopiaAnotacion.LISTA_COPIA_ANOTACIONES_CANCELADAS);
			if(canceladas!=null && canceladas.size()>0){
				request.getSession().setAttribute(WebKeys.COPIA_ANOTACION,canceladas.get(0));
				canceladas.remove(0);
				request.getSession().setAttribute(AWCopiaAnotacion.LISTA_COPIA_ANOTACIONES_CANCELADAS,canceladas);
				return CANCELADA;
			}


                        // cuando se procesa de forma normal,
                        // debe decidir a que lugar irse segun CopiaAnotacion.[proceso]
                        CopiaAnotacion local_CopiaAnotacion;

                        local_CopiaAnotacion = (CopiaAnotacion)session.getAttribute( WebKeys.COPIA_ANOTACION );

                        if( null != local_CopiaAnotacion ) {
                            // opc: Proceso.CORRECCIONES

                           if( null != local_CopiaAnotacion.getProceso()
                            && CProceso.PROCESO_CORRECCIONES.equals( local_CopiaAnotacion.getProceso() ) ) {
                              // PAGE_REGION__STARTANOTACIONCOPIARANOTACION_ACTION
                              return ControlNavegacionModificacionFolio.PAGE_REGION__FINISHANOTACIONCOPIARANOTACION__EVENTRESP_OK;

                           }

                        } // if


			return REGRESAR_CALIFICACION;
		}else if (accion.equals(AWCopiaAnotacion.SELECCIONAR_CANCELADA)) {
			List canceladas = (List) request.getSession().getAttribute(AWCopiaAnotacion.LISTA_COPIA_ANOTACIONES_CANCELADAS);
			if(canceladas!=null && canceladas.size()>0){
				request.getSession().setAttribute(WebKeys.COPIA_ANOTACION,canceladas.get(0));
				canceladas.remove(0);
				request.getSession().setAttribute(AWCopiaAnotacion.LISTA_COPIA_ANOTACIONES_CANCELADAS,canceladas);
				return CANCELADA;
			}

                       // cuando se procesa de forma normal,
                       // debe decidir a que lugar irse segun CopiaAnotacion.[proceso]
                       CopiaAnotacion local_CopiaAnotacion;

                       local_CopiaAnotacion = (CopiaAnotacion)session.getAttribute( WebKeys.COPIA_ANOTACION );

                       if( null != local_CopiaAnotacion ) {
                           // opc: Proceso.CORRECCIONES

                          if( null != local_CopiaAnotacion.getProceso()
                          && CProceso.PROCESO_CORRECCIONES.equals( local_CopiaAnotacion.getProceso() ) ) {
                             // PAGE_REGION__STARTANOTACIONCOPIARANOTACION_ACTION
                             return ControlNavegacionModificacionFolio.PAGE_REGION__FINISHANOTACIONCOPIARANOTACION__EVENTRESP_OK;

                          }

                       } // if

			return REGRESAR_CALIFICACION;

		}else if (accion.equals(AWCopiaAnotacion.CORREGIR_CANCELADA)) {
                      return doNav_OpcionCopiaAnotacionPasoXGenerico( request );
		}else if (accion.equals( AWCopiaAnotacion.REGRESAR )) {
                      return doNav_OpcionCopiaAnotacionPasoXGenerico( request );
		}

		return null;
	}


   public String
   doNav_OpcionCopiaAnotacionPasoXGenerico( HttpServletRequest request )
   throws ControlNavegacionException {

       // -------------------------------------------------

       // the session
       HttpSession session;
       session = request.getSession();

       //
       CopiaAnotacion local_CopiaAnotacion;
       local_CopiaAnotacion = (CopiaAnotacion)session.getAttribute( WebKeys.COPIA_ANOTACION );


       // acciones segun el proceso
       // acciones segun atributos en sesion

       // caso 1: correcciones

       if( null != local_CopiaAnotacion ) {


          if( ( null != local_CopiaAnotacion.getProceso() )
           && ( CProceso.PROCESO_CORRECCIONES.equals( local_CopiaAnotacion.getProceso() ) ) ) {


           return OPCIONCOPIAANOTACION_PASOX_BTNCANCELAR_OK_CORRECCIONES;


          } // if

       } // if


       // caso 2: registro (default)
       String local_ControlNavegacionTarget;

       local_ControlNavegacionTarget    = (session.getAttribute(gov.sir.core.web.WebKeys.VISTA_ORIGINADORA)!=null)?(String) session.getAttribute(gov.sir.core.web.WebKeys.VISTA_ORIGINADORA):"procesos.view";

       // return local_ControlNavegacionTarget;

       return OPCIONCOPIAANOTACION_PASOX_BTNCANCELAR_OK_REGISTRO;

                    // -------------------------------------------------
   } // end-method: doNav_OpcionEnglobePasoXCancelar



	/**
	*/
	public void doEnd(HttpServletRequest request) {

	}

}
