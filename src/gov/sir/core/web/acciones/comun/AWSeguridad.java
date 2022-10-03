package gov.sir.core.web.acciones.comun;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;

import gov.sir.core.eventos.comun.EvnRespSeguridad;
import gov.sir.core.eventos.comun.EvnSeguridad;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.InfoUsuario;
import gov.sir.core.negocio.modelo.PantallaAdministrativa;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.EstacionInvalidaException;
import gov.sir.core.web.acciones.excepciones.LoginInvalidoException;
import gov.sir.core.web.acciones.excepciones.ProcesoInvalidoException;
import gov.sir.core.web.acciones.excepciones.RolInvalidoException;
import gov.sir.core.web.acciones.excepciones.UsuarioInvalidoException;
import gov.sir.fenrir.FenrirProperties;
import gov.sir.fenrir.dao.ConfiguracionPropiedadesException;
import gov.sir.hermod.dao.DAOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.core.modelo.daoObjects.DAOFactory;
import org.auriga.core.modelo.daoObjects.RolDAO;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Esta accion web genera eventos básicos asociados a seguridad Los eventos
 * básicos pueden ser de login, y de REQUERIMIENTOS ASOCIADOS: 1. Login 2.
 * Escoger proceso 3. Escoger fase
 *
 * @see <{gov.sir.core.eventos.radicacion.EvnTurnoRadicacion}>
 * @author I.Siglo21
 */
public class AWSeguridad extends SoporteAccionWeb {

    /**
     * Constante que identifica que el usuario quiere autenticarse
     */
    public final static String LOGIN = "LOGIN";

    /**
     * Constante que identifica que se quiere entrar a administración
     */
    public final static String INICIAR_ADMINISTRACION = "INICIAR_ADMINISTRACION";

    /**
     * Constante que identifica que el usuario quiere consultar un proceso especifico
     */
    public final static String CONSULTAR_PROCESO = "CONSULTAR_PROCESO";

    /**
     * Constante que identifica que el usuario quiere consultar un proceso especifico
     */
    public final static String CONSULTAR_ESTACION = "CONSULTAR_ESTACION";

    /**
     * Constante que identifica que el usuario quiere consultar un proceso especifico
     */
    public final static String CONSULTAR_ROL = "CONSULTAR_ROL";

    /**
     * Constante que identifica que el usuario quiere consultar un proceso especifico
     */
    public final static String CONSULTAR_ESTACIONES = "CONSULTAR_ESTACIONES";

    /**
     * Constante que identifica que el usuario quiere cerrar la sesion
     */
    public final static String LOGOUT = "LOGOUT";

    /**
     * Constante que identifica el campo del jsp donde se solicita el id de la estacion
     */
    public final static String ID_ESTACION = "ID_ESTACION";

    /**
     * Constante que identifica el campo del jsp donde se solicita el id del proceso
     */
    public final static String ID_PROCESO = "ID_PROCESO";

    /**
     * Constante que identifica el campo del jsp donde se solicita el id de la fase
     */
    public final static String ID_FASE = "ID_FASE";

    /**
     * Constante que identifica el campo del jsp donde se solicita el id del usuario
     */
    public final static String ID_ROL = "ID_ROL";

    /**
     * Constante que identifica el campo del jsp donde se solicita el id del usuario
     */
    public final static String ID_USUARIO = "ID_USUARIO";

    /**
     * Constante que identifica el campo del jsp donde se solicita la clave del usuario
     */
    public final static String CLAVE_USUARIO = "CLAVE_USUARIO";

    /**
     * Constante que identifica el campo del jsp donde se solicita la clave del usuario
     */
    public final static String INICIAR_COMO_ADMINISTRADOR = "INICIAR_COMO_ADMINISTRADOR";

    /**
     * Variable donde se guarda la accion que el usuario selecciono y esta
     * guardada en el HttpServletRequest
     */
    private String accion;

    /**
     * Método principal de esta acción web. Aqui se realiza toda la lógica
     * requerida de validación y de generación de eventos de negocio.
     *
     * @param request trae la informacion del formulario
     * @throws AccionWebException cuando ocurre un error
     * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier
     * otro caso
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);
        /**
         * @author David Panesso
         * @change 1241.ADAPTACION DEL PROCESO DE AUTENTICACIÓN Y SSO DE CA
         * SITEMINDER Se cambia la ubicación del usuario a un Header HTTP que se
         * recibe de CA SiteMinder Para desarrollo se mantiene el usuario por
         * parámetro HTTP
         */
        if (accion == null) {
            String accionHeader = request.getHeader("ACCION");
            if (accionHeader != null) {
                Log.getInstance().debug(AWSeguridad.class, " [SEGURIDAD AWSeguridad]: " + "ACCION en HTTP Header: " + accionHeader);
                if (accionHeader.equals(LOGIN)) {
                    request.getSession().removeAttribute(SMARTKeys.EXCEPCION);
                    return logIn(request);
                }
            }
        }

        if ((accion == null) || (accion.length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion");
        }
        if (accion.equals(LOGIN)) {
            return logIn(request);
        } else if (accion.equals(INICIAR_COMO_ADMINISTRADOR)) {
            return iniciarAdministracion(request);
        } else if (accion.equals(CONSULTAR_PROCESO)) {
            return consultarProceso(request);
        } else if (accion.equals(CONSULTAR_ROL)) {
            return consultarRol(request);
        } else if (accion.equals(CONSULTAR_ESTACION)) {
            return consultarEstacion(request);
        } else if (accion.equals(LOGOUT)) {
            return logOut(request);
        } else {
            throw new AccionInvalidaException(
                    "Debe indicar una accion valida. La accion " + accion + " no es valida");
        }
    }

    /**
     * Este metodo es el encargado de validar los parametros necesarios para
     * consultar las fases de una estacion
     *
     * Nombre: Tipo: ID_ESTACION String
     *
     * @param request trae la informacion del formulario
     * @return Un evento de seguridad cuando los parametros son validos.
     * @throws AccionWebException cuando el identificador de la estacion es
     * invalido
     */
    private EvnSeguridad consultarRol(HttpServletRequest request) throws AccionWebException {

        HttpSession sesion = request.getSession();
        sesion.removeAttribute(WebKeys.ESTACION);
        sesion.removeAttribute(WebKeys.LISTA_PROCESOS);
        sesion.removeAttribute(WebKeys.PROCESO);
        sesion.removeAttribute(WebKeys.FASE);
        sesion.removeAttribute(WebKeys.LISTA_FASES);

        org.auriga.core.modelo.transferObjects.Usuario usuario
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(
                        SMARTKeys.USUARIO_EN_SESION);
        String idRol = request.getParameter(AWSeguridad.ID_ROL);

        // Obtengo el UID para así poder saber si hay un servidor de impresión
        // correspondiente para el mismo
        String uid = request.getSession().getId();

        if (idRol == null) {
            throw new RolInvalidoException("El rol no es valido");
        }

        if (idRol.trim().equals("")) {
            throw new RolInvalidoException("El rol no puede ser vacío");
        }

        List roles = (List) request.getSession().getAttribute(WebKeys.LISTA_ROLES);

        Iterator itRoles = roles.iterator();
        Rol rol = null;
        Rol temp = null;
        while (itRoles.hasNext()) {
            temp = (Rol) itRoles.next();
            if (temp.getRolId().equals(idRol)) {
                rol = temp;
            }
        }

        EvnSeguridad esEvento = new EvnSeguridad(usuario, rol, EvnSeguridad.CONSULTAR_ESTACIONES,
                request.getRemoteAddr());

        esEvento.setUID(uid);

        return esEvento;

    }

    /**
     * Este metodo es el encargado de validar los parametros necesarios para
     * consultar las fases de una estacion
     *
     * Nombre: Tipo: ID_ESTACION String
     *
     * @param request trae la informacion del formulario
     * @return Un evento de seguridad cuando los parametros son validos.
     * @throws AccionWebException cuando el identificador de la estacion es
     * invalido
     */
    private EvnSeguridad consultarEstacion(HttpServletRequest request) throws AccionWebException {

        HttpSession sesion = request.getSession();
        sesion.removeAttribute(WebKeys.ESTACION);
        sesion.removeAttribute(WebKeys.LISTA_PROCESOS);
        sesion.removeAttribute(WebKeys.PROCESO);
        sesion.removeAttribute(WebKeys.FASE);
        sesion.removeAttribute(WebKeys.LISTA_FASES);

        org.auriga.core.modelo.transferObjects.Usuario usuario
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(
                        SMARTKeys.USUARIO_EN_SESION);
        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
        String idEstacion = request.getParameter(AWSeguridad.ID_ESTACION);
        InfoUsuario infoC = (InfoUsuario) request.getSession().getAttribute(CInfoUsuario.INFO_USUARIO);
        if (idEstacion == null) {
            throw new EstacionInvalidaException("La estacion no es valida");
        }

        if (idEstacion.trim().equals("")) {
            throw new EstacionInvalidaException("El rol no puede ser vacío");
        }
        List estaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ESTACIONES);

        Iterator itEstacion = estaciones.iterator();
        Estacion esta = null;
        Estacion temp = null;
        while (itEstacion.hasNext()) {
            temp = (Estacion) itEstacion.next();
            if (temp.getEstacionId().equals(idEstacion)) {
                esta = temp;
            }
        }

        return new EvnSeguridad(
                usuario,
                rol,
                esta,
                request.getRemoteAddr(),
                EvnSeguridad.CONSULTAR_PROCESOS
        );
    }

    /**
     * Este método es el encargado de validar los parametros necesarios para
     * consultar las procesos de una estacion.
     *
     * Nombre: Tipo: ID_PROCESO String
     *
     * @param request trae la informacion del formulario
     * @return Un evento de seguridad cuando los parametros son validos.
     * @throws AccionWebException cuando el identificador del proceso no es
     * valido
     */
    private EvnSeguridad consultarProceso(HttpServletRequest request) throws AccionWebException {

        String idProceso = request.getParameter(AWSeguridad.ID_PROCESO);
        String idFase = request.getParameter(AWSeguridad.ID_FASE);

        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);

        if ((rol == null) || rol.getRolId().equals("")) {
            throw new EstacionInvalidaException("La estacion no es valida");
        }

        if (idProceso == null) {
            throw new ProcesoInvalidoException("El proceso no es valido");
        }

        if (idProceso.trim().equals("")) {
            throw new ProcesoInvalidoException("El proceso no puede ser vacio");
        }

        org.auriga.core.modelo.transferObjects.Usuario usuario
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(
                        SMARTKeys.USUARIO_EN_SESION);
        List procesos = (List) request.getSession().getAttribute(WebKeys.LISTA_PROCESOS);

        Iterator itProcesos = procesos.iterator();
        Proceso proceso = null;
        Proceso temp = null;

        while (itProcesos.hasNext()) {
            temp = (Proceso) itProcesos.next();
            long valIdProceso = Long.parseLong(idProceso);
            if (temp.getIdProceso() == valIdProceso) {
                proceso = temp;
            }
        }

        return new EvnSeguridad(usuario, rol, proceso);
    }

    /**
     * Este método es encargado de validar los parametros necesarios para la
     * autenticacion.
     *
     * Nombre: Tipo: USUARIO String CLAVE String
     *
     * @param request trae la informacion del formulario
     * @return Un evento de seguridad cuando los parametros son validos.
     * @throws AccionWebException cuando el usuario o la clave son invalidos
     */
    private EvnSeguridad logIn(HttpServletRequest request) throws AccionWebException {

        String pass = request.getParameter(AWSeguridad.CLAVE_USUARIO);
        /**
         * @author David Panesso
         * @change 1241.ADAPTACION DEL PROCESO DE AUTENTICACIÓN Y SSO DE CA
         * SITEMINDER Se cambia la ubicación del usuario a un Header HTTP que se
         * recibe de CA SiteMinder Para desarrollo se mantiene el usuario por
         * parámetro HTTP
         */
        String login = request.getHeader("SM_USER");

        if (login == null) {
            FenrirProperties p = FenrirProperties.getInstancia();
            String authStr = p.getProperty(FenrirProperties.AUTENTICACION_LDAP);
            if (authStr == null || authStr.trim().equals("")) {
                try {
                    throw new ConfiguracionPropiedadesException(
                            ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
                } catch (ConfiguracionPropiedadesException ex) {
                    Logger.getLogger(AWSeguridad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            boolean autenticacion = false;
            autenticacion = Boolean.valueOf(authStr).booleanValue();
            if (!autenticacion) {
                login = request.getParameter(AWSeguridad.ID_USUARIO);
            }
        }

        boolean iniciaComoAdministrador = false;
        String iniciarAdmin = request.getParameter(AWSeguridad.INICIAR_COMO_ADMINISTRADOR);
        if (iniciarAdmin != null) {
            iniciaComoAdministrador = true;
        }

        if (login == null) {
            throw new LoginInvalidoException("El usuario no es valido");
        }

        if (login.trim().equals("")) {
            throw new LoginInvalidoException("El usuario no puede ser vacio");
        }

        /*if (pass == null) {
			throw new LoginInvalidoException("La clave no es valida");
		}

		if (pass.trim().equals("")) {
			throw new LoginInvalidoException("La clave no puede ser vacia");
		}*/
        Log.getInstance().debug(AWSeguridad.class, " [SEGURIDAD AWSeguridad]: " + "SM_USER en HTTP Header: " + login);
        org.auriga.core.modelo.transferObjects.Usuario usuario
                = new org.auriga.core.modelo.transferObjects.Usuario();

        usuario.setUsuarioId(login.toUpperCase());
        InfoUsuario infoCliente
                = new InfoUsuario(
                        request.getRemoteAddr(),
                        request.getRemoteHost(),
                        request.getRemoteUser(),
                        request.getSession(true).getId());

        EvnSeguridad evento = new EvnSeguridad(usuario, infoCliente, pass, EvnSeguridad.LOGIN);
        evento.setValidarPerfilAdministrativo(iniciaComoAdministrador);
        return evento;
    }

    /**
     * Este método es encargado de ingresar a las pantallas administrativas
     *
     * @param request trae la informacion del formulario
     * @return Un evento de seguridad cuando los parametros son validos.
     * @throws AccionWebException
     */
    private EvnSeguridad iniciarAdministracion(HttpServletRequest request) throws AccionWebException {

        boolean iniciaComoAdministrador = false;
        String iniciarAdmin = request.getParameter(AWSeguridad.INICIAR_COMO_ADMINISTRADOR);
        if (iniciarAdmin != null) {
            iniciaComoAdministrador = true;
        }
        
        Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        boolean allowReasign = false;
                List relRolUsuario = null;
                try{
                DAOFactory fact = DAOFactory.getFactory();
                RolDAO rolUsu = fact.getRolDAO();
                relRolUsuario = rolUsu.obtenerRolesUsuario(usuario);
                }catch (org.auriga.core.modelo.daoObjects.DAOException ex) {
                    Logger.getLogger(AWSeguridad.class.getName()).log(Level.SEVERE, null, ex);
                }

                Iterator itRolU = relRolUsuario.iterator();
                while(itRolU.hasNext()){
                    Rol role = (Rol) itRolU.next();
                    if( role.getRolId().equals("SIR_ROL_REGISTRADOR")
                            || role.getRolId().equals("SIR_ROL_COORDINADOR_JURIDICO")){
                        allowReasign = true;
                    }
                }
                
        request.getSession().setAttribute(WebKeys.PERMITIR_REASIGNACION, allowReasign);
        EvnSeguridad evento = new EvnSeguridad(usuario, EvnSeguridad.INICIAR_ADMINISTRACION);
        evento.setValidarPerfilAdministrativo(iniciaComoAdministrador);
        return evento;
    }

    /**
     * Este método es encargado de eliminar toda la informacion del usuario del
     * request
     *
     * @param request trae la informacion del formulario
     * @return Un evento de seguridad cuando los parametros son validos.
     * @throws AccionWebException cuando el usuario o la clave son invalidos
     */
    private EvnSeguridad logOut(HttpServletRequest request) throws AccionWebException {
        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        if (usuario == null) {
            throw new UsuarioInvalidoException("La sesión ha finalizado. Por favor regístrese de nuevo en el sistema.");
        }
        org.auriga.core.modelo.transferObjects.Usuario usuarioAu
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(
                        SMARTKeys.USUARIO_EN_SESION);
        request.getSession().removeAttribute(WebKeys.USUARIO);

        return new EvnSeguridad(usuarioAu, usuario.getLoginID(), EvnSeguridad.LOGOUT);
    }

    /**
     * @see
     * org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest,
     * org.auriga.smart.eventos.EventoRespuesta)
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        EvnRespSeguridad respuesta = (EvnRespSeguridad) evento;
        HttpSession session = request.getSession();

        if (respuesta != null) {
            String tipoEvento = respuesta.getTipoEvento();

            if (tipoEvento.equals(EvnRespSeguridad.LISTA_ROLES)) {
                /**
                 * @Autor: Edgar Lora
                 * @Mantis: 003_589
                 */
                boolean haySession = false;
                if (session.getAttribute(WebKeys.USUARIO) != null) {
                    haySession = true;
                }

                Enumeration enumeration = session.getAttributeNames();

                Map sesiones = (Map) request.getSession().getServletContext()
                        .getAttribute(WebKeys.LISTA_SESIONES);
                sesiones.remove(session.getId());
                String nombre = new String();

                while (enumeration.hasMoreElements()) {
                    nombre = (String) enumeration.nextElement();
                    if (!nombre.startsWith("org.auriga")) {
                        session.removeAttribute(nombre);
                    }
                }

                if (haySession) {
                    session.setAttribute(WebKeys.IDENTIFICADOR_DE_SESSION, WebKeys.IDENTIFICADOR_DE_SESSION);
                }
                session.setAttribute(
                        WebKeys.LISTENER,
                        new UsuarioEnSesionListener(respuesta.getUsuarioAuriga(), respuesta.getUsuario()));
                session.removeAttribute(WebKeys.LISTA_ESTACIONES);
                if (respuesta.getUsuario() != null) {
                    session.setAttribute(WebKeys.USUARIO, respuesta.getUsuario());
                }
                if (respuesta.getLista() != null) {
                    session.setAttribute(WebKeys.LISTA_ROLES, respuesta.getLista());
                }
                if (respuesta.getUsuarioAuriga() != null) {
                    session.setAttribute(SMARTKeys.USUARIO_EN_SESION, respuesta.getUsuarioAuriga());
                    //TODO MIRAR CUAL ES EL ATRIBUTO DEL USUARIO EN SESION Y EL BOOLEANO
                    //session.setAttribute(SMARTKeys.USUARIO_AUTENTICADO, new Boolean(true));
                }
                /*if(respuesta.getLista(EvnRespSeguridad.LISTA_ESTACIONES) != null) {
					session.setAttribute(WebKeys.LISTA_ESTACIONES_CIRCULO, respuesta.getLista(EvnRespSeguridad.LISTA_ESTACIONES));
				}*/

            } else if (tipoEvento.equals(EvnRespSeguridad.LISTA_ESTACIONES)) {
                if (respuesta.getRol() != null) {
                    request.getSession().setAttribute(WebKeys.ROL, respuesta.getRol());
                }
                if (respuesta.getLista() != null) {
                    request.getSession().setAttribute(WebKeys.LISTA_ESTACIONES, respuesta.getLista());
                }
                if (respuesta.getImpresoras() != null) {
                    Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
                    request.getSession().setAttribute(WebKeys.LISTA_IMPRESORAS + "_" + circulo.getIdCirculo(), respuesta.getImpresoras());
                }
            } else if (tipoEvento.equals(EvnRespSeguridad.LISTA_PROCESOS)) {
                if (respuesta.getRol() != null) {
                    request.getSession().setAttribute(WebKeys.ROL, respuesta.getRol());
                }
                if (respuesta.getEstacion() != null && accion.equals(CONSULTAR_ROL)) {
                    List lista = new Vector();
                    lista.add(respuesta.getEstacion());
                    session.setAttribute(WebKeys.LISTA_ESTACIONES, lista);
                }
                if (respuesta.getEstacion() != null) {
                    request.getSession().setAttribute(WebKeys.ESTACION, respuesta.getEstacion());
                }
                if (respuesta.getLista() != null) {
                    request.getSession().setAttribute(WebKeys.LISTA_PROCESOS, respuesta.getLista());
                    request.getSession().setAttribute(WebKeys.LISTA_PROCESOS_INICIABLES, respuesta.getListaInicia());
                }
                if (respuesta.getCirculo() != null) {
                    request.getSession().setAttribute(WebKeys.CIRCULO, respuesta.getCirculo());
                }
                if (respuesta.getImpresoras() != null) {
                    Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
                    request.getSession().setAttribute(WebKeys.LISTA_IMPRESORAS + "_" + circulo.getIdCirculo(), respuesta.getImpresoras());
                }

                List rolesUsuario = (List) session.getAttribute(WebKeys.LISTA_ROLES);
                List rolesAlerta = respuesta.getRolesAlertaAdmImpesionInactivo();
                boolean mostrarAlerta = false;

                if (rolesUsuario != null) {
                    for (Iterator rolesItera = rolesUsuario.iterator(); rolesItera.hasNext() && !mostrarAlerta;) {
                        Rol idRol = (Rol) rolesItera.next();
                        if (rolesAlerta != null) {
                            mostrarAlerta = rolesAlerta.contains(idRol.getRolId()) && !respuesta.isdministradorImpresionActivo();
                        }
                    }
                }
                session.setAttribute(CImpresion.ADMINISTRADOR_IMPRESION_ACTIVO, new Boolean(mostrarAlerta));

            } else if (tipoEvento.equals(EvnRespSeguridad.LISTA_FASES)) {
                request.getSession().removeAttribute(WebKeys.PROCESO);
                request.getSession().removeAttribute(WebKeys.FASE);
                if (respuesta.getProceso() != null) {
                    request.getSession().setAttribute(WebKeys.PROCESO, respuesta.getProceso());
                }
                if (respuesta.getLista() != null) {
                    request.getSession().setAttribute(WebKeys.LISTA_FASES, respuesta.getLista());
                }
                if (respuesta.getTabla() != null) {
                    Integer i
                            = (Integer) ((Hashtable) respuesta.getTabla()).get(
                                    EvnRespSeguridad.NUMERO_MAXIMO_IMPRESIONES);
                    if (i == null) {
                        i = new Integer(0);
                    }
                    List tipoNota = (List) respuesta.getTabla().get(WebKeys.LISTA_TIPOS_NOTAS);
                    session.setAttribute(WebKeys.LISTA_TIPOS_NOTAS, tipoNota);
                    session.setAttribute(WebKeys.NUMERO_MAX_IMPRESIONES, i);
                }
            } else if (tipoEvento.equals(EvnRespSeguridad.INICIAR_SESION_COMO_ADMINISTRADOR)) {

                session.setAttribute(WebKeys.INICIA_SESION_ADMINISTRACION, new Boolean(true));

                session.setAttribute(WebKeys.LISTADO_TIPOS_PANTALLAS, respuesta.getTiposPantalla());
                
                Boolean permitirReasignacion = (Boolean) session.getAttribute(WebKeys.PERMITIR_REASIGNACION);
                if(permitirReasignacion == null){
                    permitirReasignacion = false;
                }
                
                if(permitirReasignacion){
                    session.setAttribute(WebKeys.LISTADO_PANTALLAS_VISIBLES, respuesta.getPantallasVisibles());
                } else{
                    List sinReasign = new ArrayList();
                    List pantallas = respuesta.getPantallasVisibles();
                    Iterator itSin = pantallas.iterator();
                    while(itSin.hasNext()){
                        PantallaAdministrativa pA = (PantallaAdministrativa) itSin.next();
                        if(pA.getIdPantallaAdministrativa() != 214){
                            sinReasign.add(pA);
                        }
                    }
                    session.setAttribute(WebKeys.LISTADO_PANTALLAS_VISIBLES, sinReasign);
                }
               

                if (respuesta.getUsuario() != null) {
                    session.setAttribute(WebKeys.USUARIO, respuesta.getUsuario());
                }
                if (respuesta.getUsuarioAuriga() != null) {
                    session.setAttribute(SMARTKeys.USUARIO_EN_SESION, respuesta.getUsuarioAuriga());
                }
                if (respuesta.getCirculo() != null) {
                    request.getSession().setAttribute(WebKeys.CIRCULO, respuesta.getCirculo());
                }
                if (respuesta.getLista(EvnRespSeguridad.LISTA_ESTACIONES) != null) {
                    session.setAttribute(WebKeys.LISTA_ESTACIONES_CIRCULO, respuesta.getLista(EvnRespSeguridad.LISTA_ESTACIONES));
                }

                if (respuesta.getListaRoles() != null) {
                    session.setAttribute(WebKeys.LISTA_ROLES, respuesta.getListaRoles());
                }

            } else if (tipoEvento.equals(EvnRespSeguridad.LOGOUT)) {
                //session.invalidate();
                request.setAttribute(SMARTKeys.INVALIDAR_SESSION, SMARTKeys.INVALIDAR_SESSION_SI);
                Enumeration enumeration = session.getAttributeNames();

                Map sesiones = (Map) request.getSession().getServletContext()
                        .getAttribute(WebKeys.LISTA_SESIONES);
                sesiones.remove(session.getId());
                String nombre = new String();

                while (enumeration.hasMoreElements()) {
                    nombre = (String) enumeration.nextElement();
                    if (!nombre.startsWith("org.auriga")) {
                        session.removeAttribute(nombre);
                    }
                }

            }

        }
    }
}
