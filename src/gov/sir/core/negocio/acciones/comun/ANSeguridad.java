package gov.sir.core.negocio.acciones.comun;

import gov.sir.core.eventos.comun.EvnRespSeguridad;
import gov.sir.core.eventos.comun.EvnSeguridad;
import gov.sir.core.negocio.acciones.excepciones.ANSeguridadException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaEstacionesNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaProcesosNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.FaseNoProcesadaException;
import gov.sir.core.negocio.acciones.excepciones.LoginNoProcesadoException;
import gov.sir.core.negocio.acciones.excepciones.LogoutNoProcesadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.UsuarioInvalidoException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.ImpresionPk;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioCirculo;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.FenrirProperties;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.server.dao.DAOException;
import gov.sir.print.server.dao.FactoryException;
import gov.sir.print.server.dao.ImpresionDAO;
import gov.sir.print.server.dao.PrintFactory;
import gov.sir.core.negocio.modelo.Impresion;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * Esta accion de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnSeguridad</CODE> y generar eventos de respuesta del tipo
 * <CODE>EvnRespSeguridad</CODE>
 *
 * @author jfrias, mmunoz
 */
public class ANSeguridad extends SoporteAccionNegocio {

    /**
     * Instancia de fenrir
     */
    private FenrirServiceInterface fenrir;
    /**
     * Instancia de forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia del service locator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de PrintJobs
     */
    private PrintJobsInterface printJobs;

    /**
     * Constructor de la clase ANSeguridad Es el encargado de invocar al Service
     * Locator y pedirle una instancia del servicio que necesita
     */
    public ANSeguridad() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();
        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

            if (hermod == null) {
                throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod", e);
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
        }

        try {
            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

            if (fenrir == null) {
                throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio fenrir", e);
        }

        if (fenrir == null) {
            throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
        }

        try {
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

            if (forseti == null) {
                throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio forseti", e);
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
        }

        try {
            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

            if (printJobs == null) {
                throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJobs", e);
        }

        if (printJobs == null) {
            throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
        }
    }

    /**
     * Recibe un evento de seguridad y devuelve un evento de respuesta
     *
     * @param e el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnSeguridad</CODE>
     * @throws ANSeguridadException cuando ocurre un problema que no se pueda
     * manejar
     * @return el evento de respuesta. Este evento debe ser del tipo
     * <CODE>EvnRespSeguridad</CODE>
     * @see gov.sir.core.eventos.comun.EvnSeguridad
     * @see gov.sir.core.eventos.comun.EvnRespSeguridad
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnSeguridad evento = (EvnSeguridad) e;
         if (evento.getTipoEvento().equals(EvnSeguridad.LOGIN)) {
            // return procesarLogin( evento );
            return doProcess_Login(evento);
        } else if (evento.getTipoEvento().equals(EvnSeguridad.INICIAR_ADMINISTRACION)) {
            return iniciarAdministracion(evento);
        } else if (evento.getTipoEvento().equals(EvnSeguridad.CONSULTAR_PROCESOS)) {
            return procesarConsultaProcesos(evento);
        } else if (evento.getTipoEvento().equals(EvnSeguridad.CONSULTAR_FASES)) {
            return procesarFase(evento);
        } else if (evento.getTipoEvento().equals(EvnSeguridad.LOGOUT)) {
            return procesarLogout(evento);
        } else if (evento.getTipoEvento().equals(EvnSeguridad.CONSULTAR_ESTACIONES)) {
            return procesarEstaciones(evento);
        }
        return null;
    }

    //	 -----------------------------------------------------------------------------------------
    private EventoRespuesta doProcess_Login(EvnSeguridad evento) throws EventoException {
        // in ----------------------------------------------
        EventoRespuesta result;
        // -------------------------------------------------

        long local_EofTime;
        long local_SofTime;
        long local_Duration;

        local_SofTime = System.currentTimeMillis();
        // call --------------------------------------------
        result = procesarLogin(evento);
        // -------------------------------------------------

        local_EofTime = System.currentTimeMillis();
        local_Duration = local_EofTime - local_SofTime;
        String local_DurationAsString;
        local_DurationAsString = format_DeltaTime(local_Duration);

        // out ---------------------------------------------
        return result;
        // -------------------------------------------------

    }

    private String format_DeltaTime(long local_Duration) {
        // TODO Convertir a YYYY-MM-DD HH24:MI
        return "" + local_Duration + " milliseconds.";
    }

    // -----------------------------------------------------------------------------------------
    /**
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta procesarEstaciones(EvnSeguridad evento) throws EventoException {
        EvnRespSeguridad evRespuesta = null;
        List estaciones = null;
        PrintFactory pfImpresion = null;

        try {
            org.auriga.core.modelo.transferObjects.Usuario val = evento.getUsuario();
            Rol rol = evento.getRol();

            if (hermod.isValidarSesionImpresion()) {
                // Instancio un DAO de impresión para determinar si
                // existe un servidor de impresión instalado en el
                // cliente
                pfImpresion = PrintFactory.getFactory();

                ImpresionDAO idaAccesoImpresion = pfImpresion.getImpresionDAO();
                ImpresionPk iId = new ImpresionPk();
                iId.idSesion = evento.getUID();
                Impresion iImpresion = idaAccesoImpresion.getSesionImpresion(iId);

                if (iImpresion == null) {

                    //Se intenta consultar en el property para ver si se lanza o no
                    //la excepción cuando no se ha cargado una sesión de impresión.
                    boolean lanzarExcepcionImpresion = true;
                    Boolean b = new Boolean(false);

                    FenrirProperties p = FenrirProperties.getInstancia();
                    String propLanzarExcepcionImpresion = p.getProperty(FenrirProperties.LANZAR_EXCEPCION_IMPRESION);

                    if (propLanzarExcepcionImpresion != null && propLanzarExcepcionImpresion.equals(b.toString())) {
                        lanzarExcepcionImpresion = false;
                    }

                    if (lanzarExcepcionImpresion) {
                        throw new ConsultaEstacionesNoEfectuadaException("No se ha establecido una sesión de " + "impresión. Presionar el botón <b>\"Iniciar Aplicativo de Impresi&oacute;n\"</b>", null);
                    }

                } else if (iImpresion.getPuerto() != null && iImpresion.getPuerto().equals("INVISIBLE")) {
                    throw new ConsultaEstacionesNoEfectuadaException("El cliente de impresión para " + iImpresion.getDireccionIP() + " es inasequible. No será posible imprimir " + "sobre la máquina local. <br><b>Verifique que el aplicativo de impresi&oacute;n" + " haya sido iniciado correctamente.<br>Si el problema persiste, consulte a su " + "administrador de red.</b>", null);
                }
            }

            // Si llegó hasta este punto es porque el cliente 
            // tiene asociado un servidor de impresión
            long id = fenrir.darIdUsuario(val.getUsuarioId());
            estaciones = fenrir.darEstacionUsuario(id, rol);

            if (rol.getRolId().equals(CRoles.SIR_ROL_CALIFICADOR)) {
                List aux = new ArrayList();
                for (int i = 0; i < estaciones.size(); i++) {
                    Estacion estacion = (Estacion) estaciones.get(i);
                    if (estacion.getEstacionId().substring(2).equals(val.getUsuarioId())) {
                        aux.add(estacion);
                        break;
                    }
                }
                estaciones = aux;
            }

            evRespuesta = new EvnRespSeguridad(rol, estaciones);
        } catch (FactoryException fe) {
            Log.getInstance().error(ANSeguridad.class, "Se produjo una excepcion al intentar crear el PrintFactory");
            throw new ConsultaEstacionesNoEfectuadaException(fe.getMessage(), fe);
        } catch (DAOException daeException) {
            Log.getInstance().error(ANSeguridad.class, "Se produjo una excepcion al intentar obtener la impresión");
            throw new ConsultaEstacionesNoEfectuadaException(daeException.getMessage(), daeException);
        } catch (FenrirException e) {
            throw new ConsultaEstacionesNoEfectuadaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANSeguridad.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new ConsultaEstacionesNoEfectuadaException(e.getMessage(), e);
        }

        if (estaciones.size() != 1) {
            return evRespuesta;
        } else {
            return procesarConsultaProcesos(new EvnSeguridad(evento.getUsuario(), evento.getRol(), (Estacion) estaciones.get(0), evento.getRemoteAd(), EvnSeguridad.CONSULTAR_PROCESOS));
        }

    }

    /**
     * Por medio de este metodo el usuario puede hacer logout
     *
     * @param evento el evento recibido. Este evento debe ser del tipo
     * <CODE>EvnSeguridad</CODE>
     * @throws ANSeguridadException cuando ocurre un problema que no se pueda
     * manejar
     * @return el evento de tipo <CODE>EvnRespSeguridad</CODE>
     * @see gov.sir.core.eventos.comun.EvnSeguridad
     * @see gov.sir.core.eventos.comun.EvnRespSeguridad
     */
    private EvnRespSeguridad procesarLogout(EvnSeguridad evento) throws EventoException {
        EvnRespSeguridad evRespuesta = null;

        try {
            org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
            String loginid = evento.getID();
            fenrir.hacerLogout(new Integer(loginid));
            //evRespuesta = new EvnRespSeguridad(new Usuario(user.getUsuarioId(), user.getNombre(), user.getPassword(),user.getLoginId()));
            evRespuesta = new EvnRespSeguridad(new Usuario(user.getUsuarioId(), user.getNombre(), "", ""));
        } catch (FenrirException e) {
            throw new LogoutNoProcesadoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANSeguridad.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new LogoutNoProcesadoException(e.getMessage(), e);
        }

        return evRespuesta;
    }

    /**
     * Por medio de este método se obtiene una lista de fases a las que puede
     * acceder una estación determinada en un proceso determinado
     *
     * @param evento
     * @return el evento de tipo <CODE>EvnRespSeguridad</CODE>
     * @throws ANSeguridadException cuando ocurre un problema que no se pueda
     * manejar
     * @see gov.sir.core.eventos.comun.EvnSeguridad
     * @see gov.sir.core.eventos.comun.EvnRespSeguridad
     */
    private EvnRespSeguridad procesarFase(EvnSeguridad evento) throws EventoException {
        Proceso proceso = evento.getProceso();
        Rol rol = evento.getRol();
        Hashtable tabla = new Hashtable();

        int i = 0;

        try {
            i = hermod.getNumeroMaximoImpresiones(rol, proceso);
        } catch (HermodException e) {
            throw new FaseNoProcesadaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANSeguridad.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new FaseNoProcesadaException(e.getMessage(), e);
        }

        List fases;
        try {
            fases = hermod.getFases(rol, proceso);
            if (proceso.getIdProceso() == Long.parseLong(CProceso.PROCESO_ANTIGUO_SISTEMA)
                    && rol.getRolId().equals(CRol.SIR_ROL_ENCARGADO_ANTIGUO_SISTEMA)) {
                List fasesAux = new ArrayList();
                for (int j = fases.size() - 1; j >= 0; j--) {
                    fasesAux.add(fases.get(j));
                }
                fases = fasesAux;
            }
        } catch (HermodException e) {
            throw new FaseNoProcesadaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANSeguridad.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new FaseNoProcesadaException(e.getMessage(), e);
        }

        tabla.put(EvnRespSeguridad.NUMERO_MAXIMO_IMPRESIONES, new Integer(100));
        //TODO: Esta quemado la cantidad de veces que se pueda imprimir mientras se
        //estabiliza la base de datos

        return new EvnRespSeguridad(proceso, fases, tabla);
    }

    /**
     * Por medio de este método se obtiene una lista de procesos a los que una
     * estacion determinada puede acceder
     *
     * @param evento evento recibido. Este evento debe ser un evento de
     * seguridad
     * @return un evento de respuesta. Este evento debe ser un evento de
     * seguridad
     * @throws ANSeguridadException se lanza cuando no se puede consultar los
     * procesos
     * @see gov.sir.core.eventos.comun.EvnSeguridad
     * @see gov.sir.core.eventos.comun.EvnRespSeguridad
     */
    private EvnRespSeguridad procesarConsultaProcesos(EvnSeguridad evento) throws EventoException {
        Rol rol = evento.getRol();
        Estacion esta = evento.getEstacion();
        String infoUsu = evento.getRemoteAd();
        List procesos;
        List procesosInicia;
        Circulo circulo;
        Map impresoras = null;

        try {
            circulo = fenrir.darCirculoEstacion(esta);
            if (circulo != null) {
                CirculoPk cid = new CirculoPk();
                cid.idCirculo = circulo.getIdCirculo();
                circulo = forseti.getCirculo(cid);
                impresoras = forseti.getConfiguracionImpresoras(circulo.getIdCirculo());
            }
            procesos = hermod.getProcesos(rol.getRolId());
            if (rol.getRolId().equals(CRol.SIR_ROL_MESA_CONTROL)) {
                List procesosAux = new ArrayList();
                for (int i = 0; i < procesos.size(); i++) {
                    Proceso proc = (Proceso) procesos.get(i);
                    if (!proc.getNombre().equals(CProceso.NOMBRE_PROCESO_CERTIFICADOS)) {
                        procesosAux.add(proc);
                    }
                }
                procesos = procesosAux;
            }
            procesosInicia = hermod.getProcesosQueInicia(rol.getRolId());
        } catch (HermodException e1) {
            throw new ConsultaProcesosNoEfectuadaException(e1.getMessage(), e1);
        } catch (FenrirException e) {
            throw new ConsultaProcesosNoEfectuadaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANSeguridad.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new ConsultaProcesosNoEfectuadaException(e.getMessage(), e);
        }

        boolean administradoImpresorasActivo = false;
        List rolesAlertaAdmImprActivo = null;

        try {
            administradoImpresorasActivo = printJobs.estaActivoAdministrador(circulo.getIdCirculo());
            rolesAlertaAdmImprActivo = printJobs.getRolesAlertaAdmImpInactivo();

        } catch (PrintJobsException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANSeguridad.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
        }

        EvnRespSeguridad eventoResp = new EvnRespSeguridad(rol, esta, procesos, procesosInicia, circulo, impresoras);

        eventoResp.setAdministradorImpresionActivo(administradoImpresorasActivo);
        eventoResp.setRolesAlertaAdmImpesionInactivo(rolesAlertaAdmImprActivo);

        return eventoResp;
    }

    /**
     * Toma los datos del usuario y por medio del servicio Fenrir decide si el
     * usuario se pudo registrar Se obtiene un Evento de respuesta con una lista
     * de las estaciones a las que pertenece el usuario
     *
     * @param evento evento recibido. Este evento debe ser un evento de
     * seguridad
     * @return un evento de respuesta. Este evento debe ser un evento de
     * respuesta de seguridad
     * @throws ANSeguridadException se lanza cuando no se puede registrar al
     * usuario
     * @see gov.sir.core.eventos.comun.EvnSeguridad
     * @see gov.sir.core.eventos.comun.EvnRespSeguridad
     */
    private EvnRespSeguridad procesarLogin(EvnSeguridad evento) throws EventoException {
        /**
         * @author David Panesso
         * @change 1241.ADAPTACION DEL PROCESO DE AUTENTICACIÓN Y SSO DE CA
         * SITEMINDER Se desactiva autenticación por LDAP. CA SiteMinder se
         * encarga de la autenticación y envía el usuario
         */
        //boolean val = false;
        List roles = null;
        List estaciones = null;
        EvnRespSeguridad evRespuesta = null;
        List listaPantallasVisibles = null;
        List listaTiposPantallas = null;
        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
        try {
            //val = fenrir.validarUsuario(user.getUsuarioId(), evento.getPassword());

            //if (val) {
            Log.getInstance().info(ANSeguridad.class, "LOGIN : Se encontró el usuario " + user.getUsuarioId() + " en el LDAP");
            long id = fenrir.darIdUsuario(user.getUsuarioId());
            if (id == -1) {
                Log.getInstance().info(ANSeguridad.class, "LOGIN : El usuario no existe en el sistema");
                throw new LoginNoProcesadoException("Usuario inválido");
            }
            Integer loginid = new Integer(fenrir.agregarAuditoria(id, evento.getInfoUsuario()));
            roles = fenrir.darRolUsuario(id);
            Log.getInstance().info(ANSeguridad.class, "LOGIN : Se obtuvieron los roles del usuario");

            /*if (evento.isValidarPerfilAdministrativo()) {
					boolean esAdministrador = fenrir.tieneRolAdministrativo(roles);
					if (!esAdministrador) {
						Log.getInstance().info(ANSeguridad.class,"LOGIN : El usuario no tiene privilegios de administración del sistema");
						throw new LoginNoProcesadoException("Usuario inválido");
					}
				}*/
            //Usuario todo = new Usuario(user.getUsuarioId(), user.getNombre(), evento.getPassword(), loginid.toString());
            UsuarioPk uid = new UsuarioPk();
            uid.idUsuario = id;
            Usuario todo = fenrir.getUsuarioById(uid);

            if (!todo.isActivo()) {
                throw new UsuarioInvalidoException("El usuario está inhabilitado");
            }

            UsuarioCirculo usCir = (UsuarioCirculo) todo.getUsuarioCirculos().get(0);
            Circulo circulo = usCir.getCirculo();
            if (!circulo.isActivo()) {
                throw new UsuarioInvalidoException("El círculo está inhabilitado");
            }
            todo.setLoginID(loginid.toString());

            todo.setIdUsuario(id);

            estaciones = fenrir.consultarEstacionesPorCirculo(circulo);

            evRespuesta = new EvnRespSeguridad(todo, user, roles);
            evRespuesta.setCirculo(circulo);
            evRespuesta.addLista(EvnRespSeguridad.LISTA_ESTACIONES, estaciones);

            if (evento.isValidarPerfilAdministrativo()) {

                try {
                    //Obtener lista de pantallas administrativas visibles.

                    listaTiposPantallas = hermod.obtenerTiposPantallasAdministrativas();
                    /**
                     * @author : Carlos Torres
                     * @change : Obtener pantallas administrativas del usuario
                     * @Caso Mantis : 14371: Acta - Requerimiento No
                     * 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
                     */
                    listaPantallasVisibles = hermod.obtenerAdministrativasPorRol(todo);
                } catch (Throwable t) {
                    t.printStackTrace();
                    throw new UsuarioInvalidoException("Error obteniendo lista de pantallas administrativas para el rol", t);
                }
                evRespuesta.setTipoEvento(EvnRespSeguridad.INICIAR_SESION_COMO_ADMINISTRADOR);
                evRespuesta.setListaRoles(roles);
                evRespuesta.setTiposPantalla(listaTiposPantallas);
                evRespuesta.setPantallasVisibles(listaPantallasVisibles);
            }
            /*} else {
				Log.getInstance().info(ANSeguridad.class,"LOGIN : Usuario no valido");
				throw new UsuarioInvalidoException("Usuario inválido");
			}*/
        } catch (FenrirException e) {
            Log.getInstance().error(ANSeguridad.class, e);
            Log.getInstance().info(ANSeguridad.class, "LOGIN : " + e.getMessage());
            throw new LoginNoProcesadoException("Usuario inválido");
        } catch (Throwable e) {
            Log.getInstance().error(ANSeguridad.class, e);
            Log.getInstance().error(ANSeguridad.class, "LOGIN : Ha ocurrido una excepcion inesperada ", e);
            throw new LoginNoProcesadoException("Usuario inválido");
        }
        return evRespuesta;
    }

    /**
     * Permite iniciar a las pantallas administrativas.
     *
     * @param evento evento recibido. Este evento debe ser un evento de
     * seguridad
     * @return un evento de respuesta. Este evento debe ser un evento de
     * respuesta de seguridad
     * @throws ANSeguridadException
     * @see gov.sir.core.eventos.comun.EvnSeguridad
     * @see gov.sir.core.eventos.comun.EvnRespSeguridad
     */
    private EvnRespSeguridad iniciarAdministracion(EvnSeguridad evento) throws EventoException {
        boolean val = false;
        List listaPantallasVisibles = null;
        List listaTiposPantallas = null;
        List roles = null;
        List estaciones = null;
        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();

        EvnRespSeguridad evRespuesta = null;

        try {

            long id = fenrir.darIdUsuario(user.getUsuarioId());
            if (id == -1) {
                throw new LoginNoProcesadoException("Usuario inválido");
            }
            roles = fenrir.darRolUsuario(id);

            //Usuario todo = new Usuario(user.getUsuarioId(), user.getNombre(), evento.getPassword(), loginid.toString());
            UsuarioPk uid = new UsuarioPk();
            uid.idUsuario = id;
            Usuario todo = fenrir.getUsuarioById(uid);
            UsuarioCirculo usCir = (UsuarioCirculo) todo.getUsuarioCirculos().get(0);
            Circulo circulo = usCir.getCirculo();
            if (!circulo.isActivo()) {
                throw new UsuarioInvalidoException("El círculo está inhabilitado");
            }
            estaciones = fenrir.consultarEstacionesPorCirculo(circulo);
            Estacion estacion = fenrir.getEstacion(todo, (Rol) roles.get(0));


            /*if (evento.isValidarPerfilAdministrativo()) {
				//boolean esAdministrador = fenrir.tieneRolAdministrativo(roles);
				//if (!esAdministrador) {
				//	Log.getInstance().info(ANSeguridad.class,"LOGIN : El usuario no tiene privilegios de administración del sistema");
				//	throw new LoginNoProcesadoException("Usuario inválido");
				//}
			//}*/
            try {
                //Obtener lista de pantallas administrativas visibles.
                listaTiposPantallas = hermod.obtenerTiposPantallasAdministrativas();
                /**
                 * @author : Carlos Torres
                 * @change : Obtener pantallas administrativas del usuario
                 * @Caso Mantis : 14371: Acta - Requerimiento No
                 * 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
                 */
                listaPantallasVisibles = hermod.obtenerAdministrativasPorRol(todo);
            } catch (Throwable t) {
                throw new UsuarioInvalidoException("Error obteniendo lista de pantallas administrativas para el rol");
            }

            evRespuesta = new EvnRespSeguridad(EvnRespSeguridad.INICIAR_SESION_COMO_ADMINISTRADOR, listaTiposPantallas, listaPantallasVisibles);
            evRespuesta.setListaRoles(roles);
            evRespuesta.setCirculo(circulo);
            evRespuesta.addLista(EvnRespSeguridad.LISTA_ESTACIONES, estaciones);

        } catch (FenrirException e) {
            Log.getInstance().info(ANSeguridad.class, "LOGIN : " + e.getMessage());
            throw new EventoException("Usuario inválido");
        } catch (Throwable e) {
            Log.getInstance().error(ANSeguridad.class, "LOGIN : Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException("Usuario inválido");
        }

        return evRespuesta;
    }
}
