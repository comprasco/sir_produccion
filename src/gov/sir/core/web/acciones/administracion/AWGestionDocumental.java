package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnGestionDocumental;
import gov.sir.core.eventos.administracion.EvnRespGestionDocumental;

import gov.sir.core.negocio.modelo.Circulo;
import org.auriga.core.modelo.transferObjects.Usuario;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.acciones.excepciones.GestionDocumentalException;
import gov.sir.core.web.acciones.excepciones.ValidacionConsultaUsuariosException;
import java.util.Date;
import java.util.List;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.EventoRespuesta;
/**
 *
 * @author Ellery R
 */

public class AWGestionDocumental extends SoporteAccionWeb {

    public static final String GESTION_DOCUMENTAL = "GESTION_DOCUMENTAL";
    public static final String TERMINA = "TERMINA";
    public static final String SELECCIONA_CIRCULO = "SELECCIONA_CIRCULO";
    public static final String SELECCIONA_CIRCULO_RECARGA = "SELECCIONA_CIRCULO_RECARGA";
    public static final String LISTA_TURNOS_GD_SL = "LISTA_TURNOS_GD_SL";
    public static final String LISTA_TURNOS_GD_L = "LISTA_TURNOS_GD_L";
    public static final String LISTA_TURNOS_GESTION_DOCUMENTAL = "LISTA_TURNOS_GESTION_DOCUMENTAL";
    public static final String TURNO_REENCOLAR = "TURNO_REENCOLAR";
    public static final String FECHA_INICIO = "FECHA_INICIO";
    public static final String FECHA_FIN = "FECHA_FIN";
    public static final String SELECCIONA_TURNOS_POR_FECHA = "SELECCIONA_TURNOS_POR_FECHA";
    public static final String TURNOS_POR_FECHA = "TURNOS_POR_FECHA";
    public static final String DEPURAR_TURNOS_POR_FECHA = "DEPURAR_TURNOS_POR_FECHA";
    public static final String DEPURAR_TURNOS_POR_FECHA_OK = "DEPURAR_TURNOS_POR_FECHA_OK";

    /**
     * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
     * de acuerdo al tipo de accion que tenga como parámetro.
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {

        String accion = request.getParameter(WebKeys.ACCION).trim();
        
        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }else if (accion.equals(AWGestionDocumental.SELECCIONA_CIRCULO)) {
                return consultarCirculos(request);                
        }else if (accion.equals(AWGestionDocumental.TURNO_REENCOLAR)) {
                return turnoReencolar(request);
        }else if (accion.equals(AWGestionDocumental.SELECCIONA_TURNOS_POR_FECHA)) {
                return seleccionaTurnosPorFecha(request);
        }else if (accion.equals(AWGestionDocumental.DEPURAR_TURNOS_POR_FECHA)) {
                return depurarTurnosPorFecha(request);
        }else if (accion.equals(AWGestionDocumental.TERMINA)) {
                return limpiarSesion(request);
        }
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
     * generar un evento de consulta de usuarios por círculo
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información del circulo.
     * @throws AccionWebException
     */
    private EvnGestionDocumental consultarCirculos(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionConsultaUsuariosException exception = new ValidacionConsultaUsuariosException();

        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if(idCirculo!=null)
                session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);

        EvnGestionDocumental evento =
                new EvnGestionDocumental(usuario, EvnGestionDocumental.TURNOS_GESTION_DOCUMENTAL);
        evento.setCirculo(idCirculo);
        return evento;
    }

    private EvnGestionDocumental limpiarSesion(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        session.removeAttribute(CCirculo.ID_CIRCULO);
        session.removeAttribute(AWGestionDocumental.LISTA_TURNOS_GD_SL);
        session.removeAttribute(AWGestionDocumental.LISTA_TURNOS_GD_L);
        session.removeAttribute(AWGestionDocumental.TURNO_REENCOLAR);
        session.removeAttribute(AWGestionDocumental.SELECCIONA_TURNOS_POR_FECHA);
        //session.removeAttribute(AWGestionDocumental.FECHA_INICIO);
        //session.removeAttribute(AWGestionDocumental.FECHA_FIN);
        session.removeAttribute(AWGestionDocumental.TURNOS_POR_FECHA);
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
     * generar un evento de habilitación de un usuario
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información del usuario.
     * @throws AccionWebException
     */
    private EvnGestionDocumental turnoReencolar (HttpServletRequest request)
            throws AccionWebException {

            HttpSession session = request.getSession();

            String anio = request.getParameter(CTurno.ANIO);
            String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
            String idProceso = request.getParameter(CProceso.PROCESO_ID);
            String idTurno = request.getParameter(CTurno.ID_TURNO);

            if(anio!=null)
                session.setAttribute(CTurno.ANIO, anio);

            if(idCirculo!=null)
                session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);

            if(idProceso!=null)
                session.setAttribute(CProceso.PROCESO_ID, idProceso);

            if(idTurno!=null)
                session.setAttribute(CTurno.ID_TURNO, idTurno);

            Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

            EvnGestionDocumental evento =
                    new EvnGestionDocumental(usuario, EvnGestionDocumental.TURNO_REENCOLAR);
            evento.setAnio(anio);
            evento.setCirculo(idCirculo);
            evento.setProceso(idProceso);
            evento.setTurno(idTurno);
            return evento;
    }

    /**
     * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
     * generar un evento de consulta de usuarios por círculo
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información del circulo.
     * @throws AccionWebException
     */
    private EvnGestionDocumental seleccionaTurnosPorFecha(HttpServletRequest request)
            throws GestionDocumentalException {
        
        HttpSession session = request.getSession();
        GestionDocumentalException exception = new GestionDocumentalException();

        String fechaInicioS = request.getParameter(AWGestionDocumental.FECHA_INICIO);
        String fechaFinS = request.getParameter(AWGestionDocumental.FECHA_FIN);
        Date fechaInicio = null;
        Date fechaFin = null;
        //String tmp = null;

        if(fechaInicioS.equals("")){
            exception.addError("La fecha Inicio no puede estar vacia. ");
        }else{
            try {
                fechaInicio = DateFormatUtil.parse(fechaInicioS);
            } catch (Exception e) {
                exception.addError("La fecha Inicio es inválida. " + e.getMessage());
            }
        }

        if(fechaFinS.equals("")) {
            exception.addError("La fecha Fin no puede estar vacia. ");
        }else{
            try {
                fechaFin = DateFormatUtil.parse(fechaFinS);
            } catch (Exception e) {
                exception.addError("La fecha Fin es inválida. " + e.getMessage());
            }
        }

        if((fechaInicio != null) && (fechaFin != null)){
            if(fechaFin.before(fechaInicio)) {
                exception.addError("La fecha de inicio no puede ser mayor que la fecha fin");
            }
        }

        if(exception.getErrores().size() > 0) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnGestionDocumental evento =
                new EvnGestionDocumental(usuario, EvnGestionDocumental.SELECCIONA_TURNOS_POR_FECHA);
        evento.setFechaInicio(fechaInicioS);
        evento.setFechaFin(fechaFinS);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
     * generar un evento de consulta de usuarios por círculo
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información del circulo.
     * @throws AccionWebException
     */
    private EvnGestionDocumental depurarTurnosPorFecha(HttpServletRequest request)
            throws GestionDocumentalException {

        HttpSession session = request.getSession();
        session.removeAttribute(AWGestionDocumental.TURNOS_POR_FECHA);

        String fechaInicioS = request.getParameter(AWGestionDocumental.FECHA_INICIO);
        String fechaFinS = request.getParameter(AWGestionDocumental.FECHA_FIN);

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnGestionDocumental evento =
                new EvnGestionDocumental(usuario, EvnGestionDocumental.DEPURAR_TURNOS_POR_FECHA);
        evento.setFechaInicio(fechaInicioS);
        evento.setFechaFin(fechaFinS);
        return evento;
    }

        public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespGestionDocumental respuesta = (EvnRespGestionDocumental) evento;

		HttpSession session = request.getSession();
		context = session.getServletContext();
                
		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
                        if (tipoEvento.equals(EvnRespGestionDocumental.TURNOS_GESTION_DOCUMENTAL_OK)) {
                                   List todos = (List)respuesta.getPayload();
				   session.setAttribute(AWGestionDocumental.LISTA_TURNOS_GD_SL ,todos.get(0));
                                   session.setAttribute(AWGestionDocumental.LISTA_TURNOS_GD_L ,todos.get(1));
			}else if (tipoEvento.equals(EvnRespGestionDocumental.TURNO_REENCOLAR)) {
                                   List todos = (List)respuesta.getPayload();
				   session.setAttribute(AWGestionDocumental.LISTA_TURNOS_GD_SL ,todos.get(0));
                                   session.setAttribute(AWGestionDocumental.LISTA_TURNOS_GD_L ,todos.get(1));
			}else if (tipoEvento.equals(EvnRespGestionDocumental.SELECCIONA_TURNOS_POR_FECHA)) {
                                   List rangoTurnosValor  = (List)respuesta.getPayload();
                                   session.setAttribute(AWGestionDocumental.TURNOS_POR_FECHA ,rangoTurnosValor);
			}else if (tipoEvento.equals(EvnRespGestionDocumental.DEPURAR_TURNOS_POR_FECHA)) {
                                   String turnosDepurarPorFecha = (String)respuesta.getPayload();
                                   session.setAttribute(AWGestionDocumental.DEPURAR_TURNOS_POR_FECHA_OK ,turnosDepurarPorFecha);
			}
		}
	}
   
}
