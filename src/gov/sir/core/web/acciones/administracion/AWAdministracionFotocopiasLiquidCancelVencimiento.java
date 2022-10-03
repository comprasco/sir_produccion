package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnAdministracionFotocopiasLiquidCancelVencimiento;
import gov.sir.core.eventos.administracion.EvnRespAdministracionFotocopiasLiquidCancelVencimiento;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.ValidacionFotocopiasAuthCancelarXVencimientoException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


/**
 * Esta accion web se encarga de generar eventos que permitan gestionar los turnos.
 * REQUERIMIENTOS ASOCIADOS:
 * 6 - Control de turnos anteriores
 * @author mmunozs
 */
public class AWAdministracionFotocopiasLiquidCancelVencimiento extends SoporteAccionWeb {
  public static final String CIRCULO = WebKeys.CIRCULO;

    /** Constante que identifica que se desea obtener una lista de turnos */
    public static final String ACCION_LISTAR = "ACCION_LISTAR";
    public static final String RESULTADO_LISTAR = "RESULTADO_LISTAR";

    public static final String RESULTADO_LISTAR_LISTATURNOSVENCIDOS = "RESULTADO_LISTAR_LISTATURNOSVENCIDOS";
    public static final String RESULTADO_VERDETALLE_TURNO = "RESULTADO_VERDETALLE_TURNO";


    public static final String ACCION_VOLVER = "ACCION_VOLVER";
    public static final String RESULTADO_VOLVER = "RESULTADO_VOLVER";


    public static final String ACCION_VERDETALLES = "ACCION_VERDETALLES";
    public static final String RESULTADO_VERDETALLES = "RESULTADO_VERDETALLES";



    /**Accion que selecciono el usuario */
    private String accion;

	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter( WebKeys.ACCION );

        if ((accion == null) || (accion.length() == 0)) {
            throw new AccionWebException("Debe indicar una accion");
        }

        if ( ACCION_LISTAR.equals( accion ) ) {
            return listar( request );
        }
        if ( ACCION_VERDETALLES.equals( accion ) ) {
          return verDetalles( request );
        }
        if ( ACCION_VOLVER.equals( accion ) ) {
        	limpiarSesion(request );
        	return null; // MAPPED:REDIRECT-TO
        }
        throw new AccionWebException(
            "Debe indicar una accion valida. La accion " + accion +
            " no es valida");
    }

    /**
     * Este metodo es el encargado de validar los parametros necesarios
     * para consultar los turnos de una fase
     *
     * Nombre:                        Tipo:
     * ID_FASE                String
     *
     * @param request trae la informacion del formulario
     * @return Un evento de seguridad cuando los parametros son validos.
     * @throws AccionWebException cuando el identificador de la fase es invalido
     */
    private EvnAdministracionFotocopiasLiquidCancelVencimiento
    listar(HttpServletRequest request)
    throws AccionWebException {

      // EXCEPCION QUE ACUMULA LOS ERRORES // Collector
      ValidacionFotocopiasAuthCancelarXVencimientoException exception
        = new ValidacionFotocopiasAuthCancelarXVencimientoException();
      /*
      Date referenceDate = null;

      // param_Values
      String param_SourceFieldValueAsDate;
      // obtener la fecha de referencia para hacer la busqueda
      validator_DateFormat: {
            if (null != request.getParameter("CALENDARDESDE")) {
              param_SourceFieldValueAsDate = request.getParameter("CALENDARDESDE");
              final String REFERENCE_DATE_FORMAT = "dd/MM/yyyy";

              java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
                  REFERENCE_DATE_FORMAT);

              try {
                referenceDate = dateFormat.parse( param_SourceFieldValueAsDate );
              }
              catch (Exception e) {
                logger.error(e);
                exception.addError(
                    "Verifique que el formato de la fecha tomada como referencia tiene el <br />");
                exception.addError(" siguiente formato: " + REFERENCE_DATE_FORMAT + ".");
              }

            }
            else {
              exception.addError(
                  "Debe definir una fecha de referencia para hacer la consulta.");
            }
      } // :validator_DateFormat
      
      */
      if( ( null != exception.getErrores() )
       && ( exception.getErrores().size() > 0 ) ) {
         throw exception;
      }

      // save state
      saveState( request, request.getSession() );



      Circulo circulo = (Circulo) request.getSession().getAttribute( CIRCULO );

      org.auriga.core.modelo.transferObjects.Usuario usuario=(org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
      String tipoEvento = ACCION_LISTAR;

      EvnAdministracionFotocopiasLiquidCancelVencimiento evento;
      evento = new EvnAdministracionFotocopiasLiquidCancelVencimiento( usuario, tipoEvento  );
      evento.setCirculo( circulo );
      /*
      evento.setReferenceDate( referenceDate );
      */

      return evento;
    }


    protected void saveState( HttpServletRequest request, HttpSession session ) {
      String param_SourceFieldValueAsDate = request.getParameter( "CALENDARDESDE" );
      session.setAttribute("CALENDARDESDE", param_SourceFieldValueAsDate );
    }


    private EvnAdministracionFotocopiasLiquidCancelVencimiento
	 verDetalles( HttpServletRequest request )
    throws AccionWebException {

		  // EXCEPCION QUE ACUMULA LOS ERRORES // Collector
		  ValidacionFotocopiasAuthCancelarXVencimientoException exception
			 = new ValidacionFotocopiasAuthCancelarXVencimientoException();

      // se le pasa el id de la cadena en el evento y el evento
      // de respuesta regresa los detalles del turno
      String turnoId = request.getParameter( CTurno.ID_TURNO );


	  //

	   if( ( null == turnoId )
		||( turnoId.equals( "" ) ) ) {
		   exception.addError( "Debe seleccionar un turno." );
		}


		if( ( null != exception.getErrores() )
		 && ( exception.getErrores().size() > 0 ) ) {
			throw exception;
		}


      Circulo circulo = (Circulo) request.getSession().getAttribute( CIRCULO );

      org.auriga.core.modelo.transferObjects.Usuario usuario=(org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

      String tipoEvento = ACCION_VERDETALLES;

      EvnAdministracionFotocopiasLiquidCancelVencimiento evento;
      evento = new EvnAdministracionFotocopiasLiquidCancelVencimiento( usuario, tipoEvento, turnoId  );
      evento.setCirculo( circulo );

      // invocar a metodo de negocio para obtener los detalles del turno

      return evento;
    }

    
    private void limpiarSesion(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	session.removeAttribute( AWAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_LISTAR_LISTATURNOSVENCIDOS );
    	session.removeAttribute("CALENDARDESDE");
    	return;
    }

	/**
	 * Este método permite procesar cualquier evento de respuesta de la capa
	 * de negocio, en caso de recibir alguno.
	 * @param request la información del formulario
	 * @param eventoRespuesta el evento de respuesta de la capa de negocio, en caso
	 * de existir alguno
	 */
    public void doEnd( HttpServletRequest request, EventoRespuesta evento ) {

        // forward mappings
        if( null == evento )
          return;


      EvnRespAdministracionFotocopiasLiquidCancelVencimiento event = (EvnRespAdministracionFotocopiasLiquidCancelVencimiento)evento;
      HttpSession session = request.getSession();

      if( EvnRespAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_LISTAR_OK.equals( event.getTipoEvento() ) ){
        session.setAttribute( RESULTADO_LISTAR_LISTATURNOSVENCIDOS, event.getTurnosFotocopiasConPagoVencido());
        request.getSession().setAttribute( "CALENDARDESDE", event.getDateString() );
      }
      else if( EvnRespAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_VERDETALLES_OK.equals( event.getTipoEvento() ) ) {
        session.setAttribute( RESULTADO_VERDETALLE_TURNO, event.getTurno() ); // para uso del paso negar
        session.setAttribute( WebKeys.TURNO, event.getTurno() );  // para uso de las notas informativas
        session.setAttribute( WebKeys.FASE, event.getFase() );  // para uso de las notas informativas
        session.setAttribute( WebKeys.PROCESO, event.getProceso() );  // para uso de las notas informativas
        session.setAttribute( WebKeys.LISTA_TIPOS_NOTAS, event.getTiposNotasInformativas() );  // para uso de las notas informativas
        // session.setAttribute( WebKeys.LISTA_TIPOS_NOTAS, event.getTiposNotasInformativas() );  // para uso de las notas informativas

        // session.setAttribute( "INITIAL_THRESHOLD", new Double( event.getInitialThreshold() ) );

        // para hacer el refresh
        session.removeAttribute( RESULTADO_LISTAR_LISTATURNOSVENCIDOS );

      }

    }


}
