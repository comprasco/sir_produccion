package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnAdministracionFotocopiasLiquidCancelVencimiento;
import gov.sir.core.eventos.administracion.EvnRespAdministracionFotocopiasLiquidCancelVencimiento;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.ValidacionFotocopiasAuthCancelarXVencimientoItemException;

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
public class AWAdministracionFotocopiasLiquidCancelVencimientoItem extends SoporteAccionWeb {
  public static final String CIRCULO = WebKeys.CIRCULO;

    /** Constante que identifica que se desea obtener una lista de turnos */

    public static final String ACCION_VOLVER = "ACCION_VOLVER";
    public static final String RESULTADO_VOLVER = "RESULTADO_VOLVER";


    public static final String ACCION_NEGAR = "ACCION_NEGAR";
    public static final String RESULTADO_NEGAR = "RESULTADO_NEGAR";



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

        if ( ACCION_NEGAR.equals( accion ) ) {
            return negar( request );
        }
        if ( ACCION_VOLVER.equals( accion ) ) {
            return null; // MAPPED:REDIRECT-TO
        }
        throw new AccionWebException(
            "Debe indicar una accion valida. La accion " + accion +
            " no es valida");
    }

    private EvnAdministracionFotocopiasLiquidCancelVencimiento
    negar( HttpServletRequest request )
    throws AccionWebException {


      // EXCEPCION QUE ACUMULA LOS ERRORES
      ValidacionFotocopiasAuthCancelarXVencimientoItemException exception = new ValidacionFotocopiasAuthCancelarXVencimientoItemException();


      Circulo circulo = (Circulo) request.getSession().getAttribute( CIRCULO );

      // invocar a metodo de negocio para obtener los detalles del turno
      // se le pasa el id de la cadena en el evento y el evento
      // de respuesta regresa los detalles del turno
      String turnoId;

      String param_Turno;
      param_Turno = AWAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_VERDETALLE_TURNO;
      // param_Turno = WebKeys.TURNO;

      Turno turno = (Turno) request.getSession().getAttribute( param_Turno );

      // onHandValidators : ----------------------------------------------------
      // :: validar que tenga notas informativas

      if( ( null == turno.getNotas() )
       || ( turno.getNotas().size() == 0 ) ) {
         // throw new AccionWebException( "Se deben escribir acciones informativas sobre la accion que genero la negacion de este servicio." );
         exception.addError( "Se deben escribir acciones informativas sobre la accion que genero la negacion de este servicio." );
      }

      // :: raise

      if (exception.getErrores().size() > 0) {
              throw exception;
      }

      // -----------------------------------------------------------------------


     // = request.getParameter( CTurno.ID_TURNO );

      org.auriga.core.modelo.transferObjects.Usuario usuario=(org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
      gov.sir.core.negocio.modelo.Usuario usuarioSIR =  (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

      String tipoEvento = ACCION_NEGAR;

      EvnAdministracionFotocopiasLiquidCancelVencimiento evento;
      evento = new EvnAdministracionFotocopiasLiquidCancelVencimiento( usuario, tipoEvento, turno  );
      evento.setCirculo( circulo );
      HttpSession session = request.getSession();
      evento.setSessionId( session.getId() ); // propagate to print
      evento.setUsuarioSIR( usuarioSIR );

      return evento;
    }


	/**
	 * Este método permite procesar cualquier evento de respuesta de la capa
	 * de negocio, en caso de recibir alguno.
	 * @param request la información del formulario
	 * @param eventoRespuesta el evento de respuesta de la capa de negocio, en caso
	 * de existir alguno
	 */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

      // forward mappings
      if( null == evento )
        return;


      EvnRespAdministracionFotocopiasLiquidCancelVencimiento event = (EvnRespAdministracionFotocopiasLiquidCancelVencimiento)evento;

      HttpSession session = request.getSession();

      if( ACCION_NEGAR.equals( event.getTipoEvento() ) ) {
          // para hacer el refresh
          session.removeAttribute( AWAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_LISTAR_LISTATURNOSVENCIDOS );
      }
      /*

      if( EvnRespAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_LISTAR_OK.equals( event.getTipoEvento() ) ){
        session.setAttribute( RESULTADO_LISTAR_LISTATURNOSVENCIDOS,
        event.getTurnosFotocopiasConPagoVencido());
      }
      else if( EvnRespAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_VERDETALLES_OK.equals( event.getTipoEvento() ) ) {
        session.setAttribute( RESULTADO_VERDETALLE_TURNO,
        event.getTurno() );
      }
      */
    }



}
