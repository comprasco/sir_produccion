package gov.sir.core.negocio.acciones.administracion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.eventos.administracion.EvnAdministracionFenrir;
import gov.sir.core.eventos.administracion.EvnRespAdministracionFenrir;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.modelo.ArchivosJustifica;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.dao.DAOException;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import org.auriga.core.modelo.transferObjects.Estacion;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.FormaPagoCampos;
import gov.sir.core.negocio.modelo.JustificaAdm;
import gov.sir.core.negocio.modelo.JustificaTipos;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.constantes.CJustificaAdm;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.acciones.excepciones.ValidacionCreacionUsuarioException;
import java.io.File;
import java.util.Date;
import javax.jdo.PersistenceManager;
import org.apache.commons.fileupload.FileItem;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 * Acción de negocio encargada de manejar los eventos de tipo
 * <code>EvnAdministracionFenrir</code> destinados a manejar la administración
 * de los objetos del servicio Forseti
 *
 * @author jmendez
 */
public class AnAdministracionFenrir extends SoporteAccionNegocio {

    /**
     * Instancia del ServiceLocator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de la interfaz de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de la intefaz de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia de la intefaz de Fenrir
     */
    private FenrirServiceInterface fenrir;

    /**
     * Constructor encargado de inicializar los servicios a ser utilizados por
     * la acción de Negocio
     *
     * @throws EventoException
     */
    public AnAdministracionFenrir() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();
        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
            if (hermod == null) {
                throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
        }

        try {
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

            if (forseti == null) {
                throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti");
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
        }

        try {
            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

            if (fenrir == null) {
                throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
        }
    }

    /**
     * Manejador de eventos de tipo <code>EvnAdministracionFenrir</code>. Se
     * encarga de procesar las acciones solicitadas por la capa Web de acuerdo
     * al tipo de evento que llegue a la acción de negocio. Este método redirige
     * la acción a otros métodos en la clase de acuerdo al tipo de evento que
     * llega como parámetro.
     *
     * @param evento <code>EvnAdministracionFenrir</code> evento con los
     * parámetros de la acción a realizar utilizando los servicios disponibles
     * en la clase.
     *
     * @return <code>EventoRespuesta</code> con la información resultante de la
     * ejecución de la acción sobre los servicios
     *
     * @throws <code>EventoException</code>
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnAdministracionFenrir evento = (EvnAdministracionFenrir) e;

        if (evento == null || evento.getTipoEvento() == null) {
            return null;
        }

        if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CONSULTAR_USUARIOS)) {
            return consultaUsuarios(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CONSULTAR_USUARIOS_POR_CIRCULO)) {
            return consultaUsuariosPorCirculo(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.USUARIO_CREAR)) {
            return crearUsuario(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.ROL_CREAR)) {
            return crearRol(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.NIVEL_CREAR)) {
            return crearNivel(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.NIVEL_ELIMINAR)) {
            return eliminarNivel(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.USUARIO_CAMBIAR_CLAVE)) {
            return cambiarClaveUsuario(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.USUARIO_HABILITAR)) {
            return habilitarUsuario(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.USUARIOS_ACTUALIZAR_INFORMACION)) {
            return actualizarUsuario(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CONSULTAR_ROL_EDITAR)) {
            return consultarRolEditar(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.EDITAR_ROL)) {
            return editarRol(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CONSULTAR_RELACIONES_USUARIO)) {
            return consultarRelacionesUsuario(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CONSULTAR_USUARIO_CON_RELACIONES)) {
            return consultarUsuarioConRelaciones(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CARGAR_ESTACIONES_ROL)) {
            return cargarEstacionesRol(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CARGAR_ESTACIONES_ROL_CIRCULO)) {
            return cargarEstacionesRolByCirculo(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CARGAR_ESTACIONES_CIRCULO)) {
            return cargarEstacionesByCirculo(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.AGREGAR_ROL_ESTACION_USUARIO)) {
            return agregarRolEstacionUsuario(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.ELIMINAR_ROL_ESTACION_USUARIO)) {
            return eliminarRolEstacionUsuario(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.ACTUALIZAR_ESTADO_REL_ROL_ESTACION)) {
            return actualizarEstadoRelRolEstacion(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.AGREGAR_ROL_ESTACION)) {
            return agregarRolEstacion(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.ELIMINAR_ROL_ESTACION)) {
            return eliminarRolEstacion(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CARGAR_USUARIOS_ROL_ESTACION)) {
            return cargarUsuariosRolEstacion(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.AGREGAR_NUEVO_USUARIO_ROL_ESTACION)) {
            return agregarNuevoUsuarioRolEstacion(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.ELIMINAR_USUARIO_ROL_ESTACION_ACTUAL)) {
            return eliminarUsuarioRolEstacionActual(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.MOSTRAR_CIRCULO_USUARIO)) {
            return mostrarCirculoUsuario(evento);
        } /**
         * @author Geremias Ortiz
         * @requerimiento 317090 Telebucaramanga
         * @descripcion evento para consultar las novedades de un usuario en
         * particular
         */
        else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CONSULTAR_JUSTIFICACIONES_USUARIO)) {
            return consultaJustificacionesUsuario(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CONSULTAR_CAMPOS_CAPTURA)) {
            return consultarCamposCaptura(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.ACTUALIZAR_ESTADO_CAMPOS_CAPTURA)) {
            return actualizarCamposCaptura(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CARGAR_CAMPOS_CAPTURA)) {
            return cargarCamposCaptura(evento);
        } else if (evento.getTipoEvento().equals(EvnAdministracionFenrir.CREAR_FORMA_PAGO)) {
            return crearFormaPago(evento);
        }

        return null;
    }

    /**
     * Esta acción de negocio se encarga de eliminar una estación de un rol de
     * un usuario El evento debe tener el rol, la estación y el usuario del cual
     * se va a eliminar
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta eliminarUsuarioRolEstacionActual(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = evento.getRol();
        Estacion estacion = evento.getEstacion();
        Usuario usuario = evento.getUsuarioPerfil();
        try {
            List turnos = determinarTurnosUsuario(rol, estacion);
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            java.util.Map infoUsuario = new java.util.HashMap();
            if (evento.getInfoUsuario() != null) {
                infoUsuario.put("user", evento.getInfoUsuario().getUser());
                infoUsuario.put("host", evento.getInfoUsuario().getHost());
                infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                infoUsuario.put("idTurno", null);
                infoUsuario.put("rolId", rol.getRolId());
                infoUsuario.put("usuarioId", null);
                infoUsuario.put("estacionId", estacion.getEstacionId());
            }

            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
            auditoria.guardarDatosTerminal(infoUsuario);
            fenrir.eliminarEstacionRolUsuario(estacion, rol, usuario, null, turnos);
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            auditoria.borrarDatosTerminal(estacion.getEstacionId(), rol.getRolId(), usuario.getUsuarioId());
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al eliminar la estación del rol del usuario", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al eliminar la estación del rol del usuario", e);
            throw new EventoException(e.getMessage(), e);
        }
        return cargarUsuariosRolEstacion(evento);
    }

    /**
     * Esta acción de negocio se encarga de agregar una estación a un rol de un
     * usuario El evento debe tener el rol, la estación y el usuario al que se
     * le va a agregar
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta agregarNuevoUsuarioRolEstacion(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = evento.getRol();
        Estacion estacion = evento.getEstacion();
        Usuario usuario = evento.getUsuarioPerfil();
        try {
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            java.util.Map infoUsuario = new java.util.HashMap();
            if (evento.getInfoUsuario() != null) {
                infoUsuario.put("user", evento.getInfoUsuario().getUser());
                infoUsuario.put("host", evento.getInfoUsuario().getHost());
                infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                infoUsuario.put("idTurno", null);
                infoUsuario.put("rolId", rol.getRolId());
                infoUsuario.put("usuarioId", usuario.getUsuarioId());
                infoUsuario.put("estacionId", estacion.getEstacionId());
            }

            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
            auditoria.guardarDatosTerminal(infoUsuario);
            fenrir.agregarEstacionRolUsuario(estacion, rol, usuario, fenrir.getUsuario(evento.getUsuario()));
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            auditoria.borrarDatosTerminal(estacion.getEstacionId(), rol.getRolId(), usuario.getUsuarioId());
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al agregar una estación al rol del usuario", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al agregar una estación al rol del usuario", e);
            throw new EventoException(e.getMessage(), e);
        }
        return cargarUsuariosRolEstacion(evento);
    }

    /**
     * Esta acción de negocio se encarga de cargar los usuarios con un rol y una
     * estación determinados
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta cargarUsuariosRolEstacion(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = evento.getRol();
        Estacion estacion = evento.getEstacion();
        try {
            List list = fenrir.consultarUsuariosRolEstacion(rol, estacion);
            return new EvnRespAdministracionFenrir(list, EvnRespAdministracionFenrir.LISTA_USUARIOS_ROL_ESTACION);
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al cargar los usuarios", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al cargar los usuarios", e);
            throw new EventoException(e.getMessage(), e);
        }
    }

    /**
     * Esta acción de negocio se encarga de eliminar una estación de un rol El
     * evento debe tener el rol, la estación que se va a eliminar
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta eliminarRolEstacion(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = evento.getRol();
        Estacion estacion = evento.getEstacion();
        Circulo circulo = evento.getCirculo();
        try {
            List turnos = determinarTurnosUsuario(rol, estacion);
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            java.util.Map infoUsuario = new java.util.HashMap();
            if (evento.getInfoUsuario() != null) {
                infoUsuario.put("user", evento.getInfoUsuario().getUser());
                infoUsuario.put("host", evento.getInfoUsuario().getHost());
                infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                infoUsuario.put("idTurno", null);
                infoUsuario.put("rolId", rol.getRolId());
                infoUsuario.put("usuarioId", null);
                infoUsuario.put("estacionId", estacion.getEstacionId());
            }

            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
            auditoria.guardarDatosTerminal(infoUsuario);

            fenrir.eliminarEstacionRol(estacion, rol, circulo, turnos);
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            auditoria.borrarDatosTerminal(estacion.getEstacionId(), rol.getRolId(), null);
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al eliminar la estación del rol", e);
            throw new EventoException("ERROR ", e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al eliminar la estación del rol", e);
            throw new EventoException("ERROR ", e);
        }
        return cargarEstacionesRol(evento);
    }

    /**
     * Esta acción de negocio se encarga de agregar una estación a un rol El
     * evento debe tener el rol al que se le va a agregar la estación, y la
     * estación a agregar
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta agregarRolEstacion(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = evento.getRol();
        Estacion estacion = evento.getEstacion();
        try {
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            java.util.Map infoUsuario = new java.util.HashMap();
            if (evento.getInfoUsuario() != null) {
                infoUsuario.put("user", evento.getInfoUsuario().getUser());
                infoUsuario.put("host", evento.getInfoUsuario().getHost());
                infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                infoUsuario.put("idTurno", null);
                infoUsuario.put("rolId", rol.getRolId());
                infoUsuario.put("usuarioId", null);
                infoUsuario.put("estacionId", estacion.getEstacionId());
            }

            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
            auditoria.guardarDatosTerminal(infoUsuario);

            fenrir.agregarEstacionRol(estacion, rol, fenrir.getUsuario(evento.getUsuario()));
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            auditoria.borrarDatosTerminal(estacion.getEstacionId(), rol.getRolId(), null);
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al agregar la estación al rol", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al agregar la estación al rol", e);
            throw new EventoException(e.getMessage(), e);
        }
        return cargarEstacionesRol(evento);
    }

    /**
     * Esta acción de negocio se encarga de eliminar una estación de un rol de
     * un usuario y devolver la nueva lista El evento debe tener el rol, la
     * estación y el usuario del cual se va a eliminar
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta eliminarRolEstacionUsuario(EvnAdministracionFenrir evento) throws EventoException {
        List roles = evento.getRoles();
        List estaciones = evento.getEstaciones();
        Usuario usuario = evento.getUsuarioPerfil();
        Circulo circulo = evento.getCirculo();
        System.out.println("LISTA ROLES SIZE: " + roles.size());
        System.out.println("LISTA ESTACIONES SIZE: " + estaciones.size());

        Boolean insertJustificacion = true;
        ArchivosJustifica infoArchivo = null;

        if (roles.size() == estaciones.size()) {
            Iterator iterRoles = roles.iterator();
            Iterator iterEstaciones = estaciones.iterator();
            while (iterRoles.hasNext() && iterEstaciones.hasNext()) {
                org.auriga.core.modelo.transferObjects.Rol rol
                        = (org.auriga.core.modelo.transferObjects.Rol) iterRoles.next();

                org.auriga.core.modelo.transferObjects.Estacion estacion
                        = (org.auriga.core.modelo.transferObjects.Estacion) iterEstaciones.next();

                try {
                    List turnos = determinarTurnosUsuario(rol, estacion);
                    /**
                     * @Author Carlos Torres
                     * @Mantis 13176
                     * @Chaged
                     */
                    java.util.Map infoUsuario = new java.util.HashMap();
                    if (evento.getInfoUsuario() != null) {
                        infoUsuario.put("user", evento.getInfoUsuario().getUser());
                        infoUsuario.put("host", evento.getInfoUsuario().getHost());
                        infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                        infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                        infoUsuario.put("idTurno", null);
                        infoUsuario.put("rolId", rol.getRolId());
                        infoUsuario.put("usuarioId", usuario.getUsuarioId());
                        infoUsuario.put("estacionId", estacion.getEstacionId());
                    }

                    co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                    auditoria.guardarDatosTerminal(infoUsuario);

                    String uploadPath = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO_JUSTIFICACION);

                    if (evento.getArchivosJustifica() != null) {
                        ArchivosJustifica archivosJustifica = fenrir.nuevoArchivo(evento.getArchivosJustifica());
//                        ArchivosJustifica infoArchivo = evento.getArchivosJustifica();
                        infoArchivo = evento.getArchivosJustifica();
                        String filePath = uploadPath + archivosJustifica.getJusIdArchivo() + "." + archivosJustifica.getJusTipoArchivo();
                        if (archivosJustifica.getJusIdArchivo() > 0) {
                            System.out.println("Guarde archivo filePath: " + filePath);
                            FileItem fileToSave = evento.getFileItem();
                            System.out.println("fileToSave geremias " + fileToSave);
                            File storeFile = new File(filePath);
                            fileToSave.write(storeFile);
                            
                           /**
                            * Requerimiento Rol Consulta Nacional
                            * @author DNilson Olaya Gómez
                            */
                            String soffset = "&&$%";
                            String sobservacionAndRolUsr = "";
                            JustificaAdm justificaAdm = evento.getJustificaAdm();
                            sobservacionAndRolUsr =  justificaAdm.getAdmDescripcion() + soffset + rol.getRolId();
                            justificaAdm.setAdmDescripcion(sobservacionAndRolUsr);
                            JustificaTipos justificaTipos = evento.getJustificaTipos();
                            justificaAdm.setAdmIdUsuario((int) fenrir.darIdUsuario(usuario.getUsuarioId()));
                            fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
                            
                            fenrir.eliminarEstacionRolUsuario(estacion, rol, usuario, circulo, turnos);

                        } else {
                            ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();
                            exception.addError("No fue posible realizar los cambios solicitados, intente de nuevo");
                        }

                    } else {
                        infoArchivo = null;
                       /**
                        * Requerimiento Rol Consulta Nacional
                        * @author DNilson Olaya Gómez
                        */
                        String soffset = "&&$%";
                        String sobservacionAndRolUsr = "";
                        JustificaAdm justificaAdm = evento.getJustificaAdm();
                        sobservacionAndRolUsr =  justificaAdm.getAdmDescripcion() + soffset + rol.getRolId();
                        justificaAdm.setAdmDescripcion(sobservacionAndRolUsr);
                        JustificaTipos justificaTipos = evento.getJustificaTipos();
                        justificaAdm.setAdmIdUsuario((int) fenrir.darIdUsuario(usuario.getUsuarioId()));
                        fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
                        fenrir.eliminarEstacionRolUsuario(estacion, rol, usuario, circulo, turnos);
                    }

                    /**
                     * @Author Carlos Torres
                     * @Mantis 13176
                     * @Chaged
                     */
                    auditoria.borrarDatosTerminal(estacion.getEstacionId(), rol.getRolId(), usuario.getUsuarioId());
                    //evento.setNombreUsuario(usuario.getUsuarioId());
                    //return consultarUsuarioConRelaciones(evento);
                } catch (FenrirException e) {
                    insertJustificacion = false;
                    Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al eliminar las estaciones al rol del usuario", e);
                    throw new EventoException("ERROR ", e);
                } catch (Throwable e) {
                    insertJustificacion = false;
                    Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al eliminar las estaciones al rol del usuario", e);
                    throw new EventoException("ERROR ", e);
                }
               
                /**
                 * Requer. Rol Consulta Nacional
                 * @author DNilson Olaya Gómez
                 *
                 * 
                System.out.println("No paso luego del error de eliminación");
                
                if (insertJustificacion) {
                    try {
                       
                        String soffset = "&&$%";
                        String sobservacionAndRolUsr = "";
                        JustificaAdm justificaAdm = evento.getJustificaAdm();
                        JustificaTipos justificaTipos = evento.getJustificaTipos();
                        justificaAdm.setAdmIdUsuario((int) fenrir.darIdUsuario(usuario.getUsuarioId()));
                        fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
                    } catch (Throwable e) {
                        throw new EventoException("ERROR ", e);
                    }
                }
                */

            }
        } else {
            //catch listas no coinciden
            return null;
        }
        evento.setNombreUsuario(usuario.getUsuarioId());
        return consultarUsuarioConRelaciones(evento);

    }

    /**
     * Método que determina los turnos que tiene una estacion con un rol
     * especifico
     *
     * @param Rol rol, Estacion estacion
     * @return List turnos
     * @throws EventoException
     */
    /**
     *
     */
    public List determinarTurnosUsuario(Rol rol, Estacion estacion) throws DAOException {
        List turnos = new ArrayList();

        try {
            // DETERMINAR LOS PROCESOS EN DONDE PUEDEN HABER TURNOS PARA EL ROL
            List procesos = hermod.getProcesos(rol.getRolId());
            List fases = new ArrayList();

            // SE OBTIENEN TODAS LAS FASES DE CADA PROCESO DONDE ESTE ASOCIADO
            // EL ROL
            Iterator itProcesos = procesos.iterator();
            while (itProcesos.hasNext()) {
                Proceso proceso = (Proceso) itProcesos.next();
                List fasesTemp = hermod.getFases(rol, proceso);
                fases.addAll(fasesTemp);
            }

            // PARA CADA FASE SE DETERMINA EL ID_WORKFLOW DE LOS TURNOS EN LA ESTACION DETERMINADA Y SE
            // AGREGAN A LA LISTA.
            Iterator itFases = fases.iterator();

            while (itFases.hasNext()) {
                Fase fase = (Fase) itFases.next();
                List turnosTemp = hermod.getTurnosAReasignar(estacion, fase);
                if (turnosTemp != null) {
                    for (int i = 0; i < turnosTemp.size(); i++) {
                        turnos.add(((Turno) turnosTemp.get(i)).getIdWorkflow());
                    }
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
            Log.getInstance().error(AnAdministracionFenrir.class, t.getMessage(), t);
            throw new DAOException(t.getMessage(), t);
        }
        return turnos;
    }

    /**
     * Método que determina los turnos que un usuario
     *
     * @param Rol rol, Estacion estacion
     * @return List turnos
     * @throws EventoException
     */
    /**
     *
     */
    public List getTurnosAReasignarUsuario(gov.sir.core.negocio.modelo.Usuario usuario) throws EventoException {
        List turnos = new ArrayList();

        try {
            // DETERMINAR LOS PROCESOS EN DONDE PUEDEN HABER TURNOS PARA EL ROL
            List rolesUsuario = fenrir.darRolUsuario(usuario.getIdUsuario());
            List estacionesUsuario = fenrir.darEstacionesUsuario(usuario.getIdUsuario());
            if (rolesUsuario == null || estacionesUsuario == null) {
                return null;
            }
            for (Iterator itRoles = rolesUsuario.iterator(); itRoles.hasNext();) {
                Rol rol = (Rol) itRoles.next();

//				 SE OBTIENEN TODAS LAS FASES DE CADA PROCESO DONDE ESTE ASOCIADO
                // EL ROL
                List procesos = hermod.getProcesos(rol.getRolId());
                List fases = new ArrayList();
                for (Iterator itProcesos = procesos.iterator(); itProcesos.hasNext();) {
                    Proceso proceso = (Proceso) itProcesos.next();
                    List fasesTemp = hermod.getFases(rol, proceso);
                    fases.addAll(fasesTemp);
                }

//				 PARA CADA FASE SE DETERMINA EL ID_WORKFLOW DE LOS TURNOS EN EJECUCION EN CADA ESTACION DETERMINADA Y SE
                // AGREGAN A LA LISTA.
                for (Iterator itEstaciones = estacionesUsuario.iterator(); itEstaciones.hasNext();) {
                    Estacion estacion = (Estacion) itEstaciones.next();
                    for (Iterator itFases = fases.iterator(); itFases.hasNext();) {
                        Fase fase = (Fase) itFases.next();
                        List turnosTemp = hermod.getTurnosAReasignar(estacion, fase);
                        if (turnosTemp != null) {
                            for (int i = 0; i < turnosTemp.size(); i++) {
                                turnos.add(((Turno) turnosTemp.get(i)).getIdWorkflow());
                            }
                        }
                    }
                }

            }
        } catch (Throwable t) {
            t.printStackTrace();
            Log.getInstance().error(AnAdministracionFenrir.class, t.getMessage(), t);
            throw new EventoException(t.getMessage(), t);
        }
        return turnos;
    }

    /**
     * Esta acción de negocio se encarga de eliminar una estación de un rol de
     * un usuario y devolver la nueva lista El evento debe tener el rol, la
     * estación y el usuario del cual se va a eliminar
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta actualizarEstadoRelRolEstacion(EvnAdministracionFenrir evento) throws EventoException {
        List roles = evento.getRoles();
        List estaciones = evento.getEstaciones();
        List estados = evento.getEstados();
        Usuario usuario = evento.getUsuarioPerfil();
        Circulo circulo = evento.getCirculo();

        System.out.println("LISTA ROLES SIZE: " + roles.size());
        System.out.println("LISTA ESTACIONES SIZE: " + estaciones.size());
        System.out.println("LISTA ESTADOS SIZE: " + estados.size());
        if ((roles.size() == estaciones.size()) && (estados.size() == roles.size())) {
            Iterator iterRoles = roles.iterator();
            Iterator iterEstaciones = estaciones.iterator();
            Iterator iterEstados = estados.iterator();
            while (iterRoles.hasNext() && iterEstaciones.hasNext() && iterEstados.hasNext()) {
                org.auriga.core.modelo.transferObjects.Rol rol
                        = (org.auriga.core.modelo.transferObjects.Rol) iterRoles.next();

                org.auriga.core.modelo.transferObjects.Estacion estacion
                        = (org.auriga.core.modelo.transferObjects.Estacion) iterEstaciones.next();

                String estadoRolEstacion = (String) iterEstados.next();

                boolean estado = false;
                if (estadoRolEstacion.equals("true")) {
                    estado = true;
                } else {
                    estado = false;
                }

                try {
                    /**
                     * @Author Carlos Torres
                     * @Mantis 13176
                     * @Chaged
                     */
                    java.util.Map infoUsuario = new java.util.HashMap();
                    if (evento.getInfoUsuario() != null) {
                        infoUsuario.put("user", evento.getInfoUsuario().getUser());
                        infoUsuario.put("host", evento.getInfoUsuario().getHost());
                        infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                        infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                        infoUsuario.put("idTurno", null);
                        infoUsuario.put("rolId", rol.getRolId());
                        infoUsuario.put("usuarioId", usuario.getUsuarioId());
                        infoUsuario.put("estacionId", estacion.getEstacionId());
                    }

                    co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                    auditoria.guardarDatosTerminal(infoUsuario);

                    String uploadPath = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO_JUSTIFICACION);
                    if (evento.getArchivosJustifica() != null) {
                        ArchivosJustifica archivosJustifica = fenrir.nuevoArchivo(evento.getArchivosJustifica());
                        ArchivosJustifica infoArchivo = evento.getArchivosJustifica();

                        String filePath = uploadPath + archivosJustifica.getJusIdArchivo() + "." + archivosJustifica.getJusTipoArchivo();
                        if (archivosJustifica.getJusIdArchivo() > 0) {
                            System.out.println("Guarde archivo filePath: " + filePath);
                            FileItem fileToSave = evento.getFileItem();
                            System.out.println("fileToSave geremias " + fileToSave);
                            File storeFile = new File(filePath);
                            fileToSave.write(storeFile);
                           /**
                            * Requerimiento Rol Consulta Nacional
                            * @author DNilson Olaya Gómez
                            */
                            String soffset = "&&$%";
                            String sobservacionAndRolUsr = "";
                            JustificaAdm justificaAdm = evento.getJustificaAdm();
                            sobservacionAndRolUsr =  justificaAdm.getAdmDescripcion() + soffset + rol.getRolId();
                            justificaAdm.setAdmDescripcion(sobservacionAndRolUsr);
                            JustificaTipos justificaTipos = evento.getJustificaTipos();
                            //justificaAdm.setAdmJusIdArchivo(archivosJustifica.getJusIdArchivo());
                            justificaAdm.setAdmIdUsuario((int) fenrir.darIdUsuario(usuario.getUsuarioId()));
                            fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);

                            fenrir.actualizarEstadoEstacionRol(estacion, rol, circulo, estado, fenrir.getUsuario(evento.getUsuario()));
                        } else {
                            ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();
                            exception.addError("No fue posible realizar los cambios solicitados, intente de nuevo");
                        }
                    } else {
                        ArchivosJustifica infoArchivo = null;
                       /**
                        * Requerimiento Rol Consulta Nacional
                        * @author DNilson Olaya Gómez
                        */
                        String soffset = "&&$%";
                        String sobservacionAndRolUsr = "";
                        JustificaAdm justificaAdm = evento.getJustificaAdm();
                        sobservacionAndRolUsr =  justificaAdm.getAdmDescripcion() + soffset + rol.getRolId();
                        justificaAdm.setAdmDescripcion(sobservacionAndRolUsr);
                        JustificaTipos justificaTipos = evento.getJustificaTipos();

                        System.out.println("Usuario al que se le realiza la adicion de rol: " + fenrir.getUsuario(evento.getUsuario()).getIdUsuario());
                        justificaAdm.setAdmIdUsuario((int) fenrir.darIdUsuario(usuario.getUsuarioId()));
                        
                        fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
                        fenrir.actualizarEstadoEstacionRol(estacion, rol, circulo, estado, fenrir.getUsuario(evento.getUsuario()));
                    }

                    /**
                     * @Author Carlos Torres
                     * @Mantis 13176
                     * @Chaged
                     */
                    auditoria.borrarDatosTerminal(estacion.getEstacionId(), rol.getRolId(), usuario.getUsuarioId());

                } catch (FenrirException e) {
                    Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al actualizar el estado de la estacion al rol del usuario", e);
                    throw new EventoException("ERROR ", e);
                } catch (Throwable e) {
                    Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al actualizar el estado de la estacion al rol del usuario", e);
                    throw new EventoException("ERROR ", e);
                }

            }
        } else {
            //catch listas no coinciden
            return null;
        }
        evento.setNombreUsuario(usuario.getUsuarioId());
        return consultarUsuarioConRelaciones(evento);

    }

    /**
     * Esta acción de negocio se encarga de agregar una estación a un rol de un
     * usuario El evento debe tener el rol, la estación y el usuario al cual se
     * va a agregar
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta agregarRolEstacionUsuario(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = evento.getRol();
        Estacion estacion = evento.getEstacion();
        Usuario usuario = evento.getUsuarioPerfil();

        try {
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            java.util.Map infoUsuario = new java.util.HashMap();
            if (evento.getInfoUsuario() != null) {
                infoUsuario.put("user", evento.getInfoUsuario().getUser());
                infoUsuario.put("host", evento.getInfoUsuario().getHost());
                infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                infoUsuario.put("idTurno", null);
                infoUsuario.put("rolId", rol.getRolId());
                infoUsuario.put("usuarioId", usuario.getUsuarioId());
                infoUsuario.put("estacionId", estacion.getEstacionId());
            }

            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
            auditoria.guardarDatosTerminal(infoUsuario);

            String uploadPath = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO_JUSTIFICACION);

            System.out.println("Guarde archivo uploadPath: " + uploadPath);
            System.out.println("VALUE DE ARCHIVOSJUSTIFICA: " + evento.getArchivosJustifica());
            if (evento.getArchivosJustifica() != null) {
                ArchivosJustifica archivosJustifica = fenrir.nuevoArchivo(evento.getArchivosJustifica());
                ArchivosJustifica infoArchivo = evento.getArchivosJustifica();
                String filePath = uploadPath + archivosJustifica.getJusIdArchivo() + "." + archivosJustifica.getJusTipoArchivo();
                if (archivosJustifica.getJusIdArchivo() > 0) {
                    System.out.println("Guarde archivo filePath: " + filePath);
                    FileItem fileToSave = evento.getFileItem();
                    System.out.println("fileToSave geremias " + fileToSave);
                    File storeFile = new File(filePath);
                    fileToSave.write(storeFile);
                    
                   /**
                    * Requer. Rol Consulta Nacional
                    * @author DNilson Olaya Gómez
                    */
                    String soffset = "&&$%";
                    String sobservacionAndRolUsr = "";
                    JustificaAdm justificaAdm = evento.getJustificaAdm();
                    sobservacionAndRolUsr =  justificaAdm.getAdmDescripcion() + soffset + rol.getRolId();
                    justificaAdm.setAdmDescripcion(sobservacionAndRolUsr);
                    JustificaTipos justificaTipos = evento.getJustificaTipos();
                    //justificaAdm.setAdmJusIdArchivo(archivosJustifica.getJusIdArchivo());
                    System.out.println("Usuario al que se le realiza la adicion de rol: " + fenrir.getUsuario(evento.getUsuario()).getIdUsuario());
                    justificaAdm.setAdmIdUsuario((int) fenrir.darIdUsuario(usuario.getUsuarioId()));
                    fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
                    fenrir.agregarEstacionRolUsuario(estacion, rol, usuario, fenrir.getUsuario(evento.getUsuario()));

                } else {
                    ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();
                    exception.addError("No fue posible realizar los cambios solicitados, intente de nuevo");
                }
            } else {
                /**
                 * Requer. Rol Consulta Nacional
                 * @author DNilson Olaya Gómez
                 */
                String soffset = "&&$%";
                String sobservacionAndRolUsr = "";
                ArchivosJustifica infoArchivo = null;
                JustificaAdm justificaAdm = evento.getJustificaAdm();
                //sobservacionAndRolUsr =  justificaAdm.getAdmDescripcion() + soffset + rol.getNombre();
                sobservacionAndRolUsr =  justificaAdm.getAdmDescripcion() + soffset + rol.getRolId();
                justificaAdm.setAdmDescripcion(sobservacionAndRolUsr);
                JustificaTipos justificaTipos = evento.getJustificaTipos();

                System.out.println("Usuario al que se le realiza la adicion de rol: " + fenrir.getUsuario(evento.getUsuario()).getIdUsuario());
                justificaAdm.setAdmIdUsuario((int) fenrir.darIdUsuario(usuario.getUsuarioId()));
                fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
                fenrir.agregarEstacionRolUsuario(estacion, rol, usuario, fenrir.getUsuario(evento.getUsuario()));
            }

            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            auditoria.borrarDatosTerminal(estacion.getEstacionId(), rol.getRolId(), usuario.getUsuarioId());
            evento.setNombreUsuario(usuario.getUsuarioId());

            return consultarUsuarioConRelaciones(evento);
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al agregar las estaciones al rol del usuario", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al agregar las estaciones al rol del usuario", e);
            throw new EventoException(e.getMessage(), e);
        }
    }

    /**
     * Esta acción de negocio se encarga de cargar las estaciones de un rol El
     * evento debe tener el rol
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta cargarEstacionesRol(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = evento.getRol();
        List estaciones = null;
        try {
            estaciones = fenrir.getEstacionesRol(rol);
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar las estaciones del rol", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar las estaciones del rol", e);
            throw new EventoException(e.getMessage(), e);
        }
        return new EvnRespAdministracionFenrir(estaciones, EvnRespAdministracionFenrir.LISTA_ESTACIONES_ROL);
    }

    /**
     * Esta acción de negocio se encarga de cargar las estaciones de un rol El
     * evento debe tener el rol
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta cargarEstacionesByCirculo(EvnAdministracionFenrir evento) throws EventoException {
        Circulo circulo = evento.getCirculo();
        List estaciones = null;
        try {
            estaciones = fenrir.getEstacionesCirculo(circulo);
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar las estaciones del circulo", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar las estaciones del circulo", e);
            throw new EventoException(e.getMessage(), e);
        }
        return new EvnRespAdministracionFenrir(estaciones, EvnRespAdministracionFenrir.LISTA_ESTACIONES_CIRCULO);
    }

    /**
     * Esta acción de negocio se encarga de cargar las estaciones de un rol El
     * evento debe tener el rol
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta cargarEstacionesRolByCirculo(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = evento.getRol();
        Circulo circulo = evento.getCirculo();
        List estaciones = null;
        try {
            estaciones = fenrir.getEstacionesRolByCirculo(rol, circulo);
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar las estaciones del rol", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar las estaciones del rol", e);
            throw new EventoException(e.getMessage(), e);
        }
        return new EvnRespAdministracionFenrir(estaciones, EvnRespAdministracionFenrir.LISTA_ESTACIONES_ROL);
    }

    /**
     * Esta acción de negocio se encarga de consultar el perfil de un usuario
     * (roles y estaciones) El evento debe tener el username del usuario
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta consultarRelacionesUsuario(EvnAdministracionFenrir evento) throws EventoException {
        String username = evento.getNombreUsuario();
        Usuario usuario = null;
        try {
            usuario = fenrir.getUsuarioByUsername(username);
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar el usuario", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar el usuario", e);
            throw new EventoException(e.getMessage(), e);
        }
        return new EvnRespAdministracionFenrir(usuario, EvnRespAdministracionFenrir.CONSULTA_PERFIL_USUARIO_OK);
    }

    private EventoRespuesta editarRol(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = evento.getRol();
        try {
            fenrir.actualizarRol(rol, fenrir.getUsuario(evento.getUsuario()));
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al actualizar el rol", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al actualizar el rol", e);
            throw new EventoException(e.getMessage(), e);
        }
        return new EvnRespAdministracionFenrir(rol, EvnRespAdministracionFenrir.EDITAR_ROL_OK);
    }

    private EventoRespuesta consultarRolEditar(EvnAdministracionFenrir evento) throws EventoException {
        Rol rol = null;
        try {
            rol = fenrir.getRolByID(evento.getIdRol());
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar el rol por el identificador", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar el rol por el identificador", e);
            throw new EventoException(e.getMessage(), e);
        }
        return new EvnRespAdministracionFenrir(rol, EvnRespAdministracionFenrir.CONSULTAR_ROL_EDITAR_OK);
    }

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método consulta la lista de objetos de tipo <code>Usuario</code>
     * existentes en el sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Usuario</code> a ser consultado.
     *
     * @return <code>EvnRespAdministracionFenrir</code> con la lista los
     * usuarios existentes en el sistema.
     *
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir consultaUsuarios(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            datos = fenrir.consultarUsuarios(evento.getNombreUsuario());
            if (datos == null) {
                throw new EventoException(
                        "Ocurrió un error al consultar la lista de usuarios en el sistema",
                        null);
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de usuarios en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de usuarios en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.CONSULTA_USUARIOS_OK);
        return evRespuesta;
    }

    /**
     * Este método consulta la lista de objetos de tipo
     * <code>JustificaAdm</code> existentes en el sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>JustificaAdm</code> a ser consultado.
     *
     * @return <code>EvnRespAdministracionFenrir</code> con la lista los
     * justificaciones (notas) existentes en el sistema.
     *
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir consultaJustificacionesUsuario(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;
        Usuario usuarioRespuesta = null;
        boolean administradorRegional = false;
        List rolesUsuario = null;
        Usuario usuario = null;
        List tiposJustificaciones = null;

        List roles = null;


        try {

            int usuarioId = 0;

            if (evento.getJustificaAdm() != null) {
                usuarioId = evento.getJustificaAdm().getAdmIdUsuario();
            } else {
                usuario = evento.getUsuarioPerfil();                
                usuarioId = (int) fenrir.darIdUsuario(usuario.getUsuarioId());
                usuarioRespuesta = fenrir.getUsuarioNRelationsByUsername(usuario.getUsuarioId());
            }
            
            String fechaIni = evento.getFechaIni();
            String fechaFin = evento.getFechaFin();
            
            datos = fenrir.consultarJustificacionesUsuarios(usuarioId, fechaIni, fechaFin);
            if (datos == null) {
                throw new EventoException(
                        "Ocurrió un error al consultar la lista de justificaciones por usuarios en el sistema",
                        null);
            }
            long idUsuario = fenrir.darIdUsuario(evento.getUsuario().getUsuarioId());
            System.out.println("USUARIO PARA BUSCAR ROLES " + idUsuario);
            rolesUsuario = fenrir.darRolUsuario(idUsuario);
            List rolesString = new ArrayList();
            Iterator itRoles = rolesUsuario.iterator();
            while (itRoles.hasNext()) {
                Rol rol = (Rol) itRoles.next();
                rolesString.add(rol.getRolId());
                System.out.println(rol.getRolId());
            }
            if (!rolesString.contains(CRoles.ADMINISTRADOR_NACIONAL)) {
                administradorRegional = true;
            }
            if (administradorRegional) {
                System.out.println("entro a buscar roles");
                roles = hermod.getOPLookupCodes(COPLookupTypes.ADMINISTRADOR_REGIONAL_ROLES);
            }

            String tipoJust = evento.getTipoJust();
            tiposJustificaciones = fenrir.consultarTiposJustificaciones(tipoJust);
            if (tiposJustificaciones == null) {
                throw new EventoException(
                        "Ocurrió un error al consultar la lista de justificaciones por usuarios en el sistema",
                        null);
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de justificaciones por usuarios en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de justificaciones por usuarios en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(usuarioRespuesta, EvnRespAdministracionFenrir.CONSULTA_USUARIOS_JUSTIFICACIONES_OK);
        evRespuesta.setJustificacionesPerfiles(datos);
        evRespuesta.setRolesAdministradorRegional(roles);
        evRespuesta.setTipoJustificaciones(tiposJustificaciones);
        return evRespuesta;
    }

    /**
     * Este método consulta la lista de objetos de tipo <code>Usuario</code>
     * existentes en el sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Usuario</code> a ser consultado.
     *
     * @return <code>EvnRespAdministracionFenrir</code> con la lista los
     * usuarios existentes en el sistema.
     *
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir consultaUsuariosPorCirculo(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            datos = fenrir.consultarUsuariosPorCirculo(evento.getCirculo());
            if (datos == null) {
                throw new EventoException(
                        "Ocurrió un error al consultar la lista de usuarios en el sistema",
                        null);
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de usuarios en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de usuarios en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(
                        datos,
                        EvnRespAdministracionFenrir.CONSULTA_USUARIOS_POR_CIRCULO_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de invocar el servicio para la creación de
     * usuarios en el sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Usuario</code> a ser consultado.
     *
     * @return <code>EvnRespAdministracionFenrir</code> con la lista los
     * usuarios existentes en el sistema.
     *
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir crearUsuario(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            List roles = evento.getRoles();
            if (fenrir.crearUsuario(evento.getUsuarioNegocio(), roles, fenrir.getUsuario(evento.getUsuario()))) {
                datos = fenrir.consultarUsuariosPorCirculo(evento.getCirculo());
            }
            //if (datos == null) {
            //	throw new EventoException("Ocurrió un error al crear el usuario en el sistema", null);
            //}

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al crear el usuario en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al crear el usuario en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.USUARIO_CREAR_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método crea un  <code>Rol</code> en el sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Rol</code> a ser creado.
     * @return <code>EvnRespAdministracionFenrir</code> con la lista actualizada
     * de los roles existentes en el sistema
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir crearRol(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            datos = fenrir.crearRol(evento.getRol(), fenrir.getUsuario(evento.getUsuario()));
            if (datos == null) {
                throw new EventoException("Ocurrió un error al crear  en el sistema.", null);
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al crear el rol en el sistema.", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al crear el rol en el sistema.", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.ROL_CREAR_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método crea un  <code>Nivel</code> en el sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Nivel</code> a ser creado.
     * @return <code>EvnRespAdministracionFenrir</code> con la lista actualizada
     * de los niveles existentes en el sistema
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir crearNivel(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            datos = fenrir.crearNivel(evento.getNivel());
            if (datos == null) {
                throw new EventoException("Ocurrió un error al crear el nivel en el sistema.", null);
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al crear el nivel en el sistema.", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al crear el nivel en el sistema.", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.NIVEL_CREAR_OK);
        return evRespuesta;
    }

    /**
     * Este método elimina un  <code>Nivel</code> del sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Nivel</code> a ser eliminado.
     * @return <code>EvnRespAdministracionFenrir</code> con la lista actualizada
     * de los niveles existentes en el sistema
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir eliminarNivel(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;
        try {
            datos = fenrir.eliminarNivel(evento.getNivel());
            if (datos == null) {
                throw new EventoException("Ocurrió un error al eliminar el nivel en el sistema.", null);
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al eliminar el nivel en el sistema.", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al eliminar el nivel en el sistema.", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.NIVEL_CREAR_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de invocar el servicio para el cambio de clave del
     * usuario en el sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Usuario</code> al que se le cambiará la clave
     *
     * @return <code>EvnRespAdministracionFenrir</code>
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir cambiarClaveUsuario(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;
        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
        try {
            if (fenrir.validarUsuario(user.getUsuarioId(), evento.getOldPassword())) {
                gov.sir.core.negocio.modelo.Usuario usuarioNeg = fenrir.getUsuario(user);
                usuarioNeg.setPassword(evento.getUsuarioNegocio().getPassword());
                fenrir.cambiarPassword(usuarioNeg);
            } else {
                ValidacionParametrosException exception = new ValidacionParametrosException();
                exception.addError("La contraseña actual ingresada no es correcta.");
                throw exception;
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al cambiar la clave del usuario.", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al cambiar la clave del usuario.", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.USUARIO_CAMBIO_CLAVE_OK);
        return evRespuesta;
    }

    private EvnRespAdministracionFenrir mostrarCirculoUsuario(EvnAdministracionFenrir evento) throws EventoException {

        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
        boolean administradorNacional = false;
        List resultado;

        try {
            //SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
            long idUsuario = fenrir.darIdUsuario(user.getUsuarioId());
            List roles = fenrir.darRolUsuario(idUsuario);
            List rolesString = new ArrayList();

            Iterator itRoles = roles.iterator();
            while (itRoles.hasNext()) {
                Rol rol = (Rol) itRoles.next();
                rolesString.add(rol.getRolId());
            }
            if (rolesString.contains(CRoles.ADMINISTRADOR_NACIONAL)) {
                administradorNacional = true;
            }

            if (administradorNacional) {
                resultado = forseti.getCirculos();
            } else {
                resultado = new ArrayList();
            }
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(resultado, EvnRespAdministracionFenrir.MOSTRAR_CIRCULO_USUARIO_OK);
        return evRespuesta;
    }
//	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de invocar el servicio para la habilitación /
     * deshabilitación de un usuario en el sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Usuario</code> al que se le cambiarán los
     * privilegios de "activación"
     * @return <code>EvnRespAdministracionFenrir</code>
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir habilitarUsuario(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            List turnosEjecucion = this.getTurnosAReasignarUsuario(evento.getUsuarioNegocio());
            if (!evento.getUsuarioNegocio().isActivo() && (turnosEjecucion != null && !turnosEjecucion.isEmpty())) {
                throw new EventoException("El usuario tiene turnos pendientes");
            }
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
            java.util.Map infoUsuario = new java.util.HashMap();
            if (evento.getInfoUsuario() != null) {
                infoUsuario.put("user", evento.getInfoUsuario().getUser());
                infoUsuario.put("host", evento.getInfoUsuario().getHost());
                infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                infoUsuario.put("idTurno", String.valueOf(evento.getUsuarioNegocio().getIdUsuario()));
            }
            auditoria.guardarDatosTerminal(infoUsuario);

            String uploadPath = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO_JUSTIFICACION);

            System.out.println("Guarde archivo uploadPath: " + uploadPath);
            //ArchivosJustifica ArchivosJustificaTemp = evento.getArchivosJustifica();
            if (evento.getArchivosJustifica() != null) {
                ArchivosJustifica archivosJustifica = fenrir.nuevoArchivo(evento.getArchivosJustifica());
                ArchivosJustifica infoArchivo = evento.getArchivosJustifica();

                String filePath = uploadPath + archivosJustifica.getJusIdArchivo() + "." + archivosJustifica.getJusTipoArchivo();
                if (archivosJustifica.getJusIdArchivo() > 0) {
                    System.out.println("Guarde archivo filePath: " + filePath);
                    FileItem fileToSave = evento.getFileItem();
                    System.out.println("fileToSave geremias " + fileToSave);
                    File storeFile = new File(filePath);
                    fileToSave.write(storeFile);

                    JustificaAdm justificaAdm = evento.getJustificaAdm();
                    JustificaTipos justificaTipos = evento.getJustificaTipos();
                    //justificaAdm.setAdmJusIdArchivo(archivosJustifica.getJusIdArchivo());

                    fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);

                    fenrir.habilitarUsuario(evento.getUsuarioNegocio());
                } else {
                    ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();
                    exception.addError("No fue posible realizar los cambios solicitados, intente de nuevo");
                }
            } else {
                ArchivosJustifica infoArchivo = null;
                JustificaAdm justificaAdm = evento.getJustificaAdm();
                JustificaTipos justificaTipos = evento.getJustificaTipos();

                System.out.println("Usuario al que se le realiza la adicion de rol: " + fenrir.getUsuario(evento.getUsuario()).getIdUsuario());
                fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
                fenrir.habilitarUsuario(evento.getUsuarioNegocio());
            }

            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            auditoria.borrarDatosTerminal(String.valueOf(evento.getUsuarioNegocio().getIdUsuario()));
            datos = fenrir.consultarUsuariosPorCirculo(evento.getCirculo());

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al cambiar los privilegios de activación del usuario.", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al cambiar los privilegios de activación del usuario.", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.USUARIO_HABILITACION_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de actualizar la información de un usuario los
     * atributos a actualizar son el nombre, apellido1 y apellido2
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Usuario</code> <code>ArchivosJustifica</code> al
     * que se le cambiarán los datos.
     * @return <code>EvnRespAdministracionFenrir</code>
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir actualizarUsuario(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
            java.util.Map infoUsuario = new java.util.HashMap();
            if (evento.getInfoUsuario() != null) {
                infoUsuario.put("user", evento.getInfoUsuario().getUser());
                infoUsuario.put("host", evento.getInfoUsuario().getHost());
                infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                infoUsuario.put("idTurno", String.valueOf(evento.getUsuarioNegocio().getIdUsuario()));
            }
            auditoria.guardarDatosTerminal(infoUsuario);
            String uploadPath = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO_JUSTIFICACION);

            System.out.println("Guarde archivo uploadPath: " + uploadPath);
            //ArchivosJustifica ArchivosJustificaTemp = evento.getArchivosJustifica();

            if (evento.getArchivosJustifica() != null) {
                ArchivosJustifica archivosJustifica = fenrir.nuevoArchivo(evento.getArchivosJustifica());
                ArchivosJustifica infoArchivo = evento.getArchivosJustifica();

                String filePath = uploadPath + archivosJustifica.getJusIdArchivo() + "." + archivosJustifica.getJusTipoArchivo();
                if (archivosJustifica.getJusIdArchivo() > 0) {
                    System.out.println("Guarde archivo filePath: " + filePath);
                    FileItem fileToSave = evento.getFileItem();
                    System.out.println("fileToSave geremias " + fileToSave);
                    File storeFile = new File(filePath);
                    fileToSave.write(storeFile);

                    JustificaAdm justificaAdm = evento.getJustificaAdm();
                    JustificaTipos justificaTipos = evento.getJustificaTipos();
                    //justificaAdm.setAdmJusIdArchivo(archivosJustifica.getJusIdArchivo());

                    fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
                    fenrir.actualizarUsuario(evento.getUsuarioNegocio());

                } else {
                    ValidacionCreacionUsuarioException exception = new ValidacionCreacionUsuarioException();
                    exception.addError("No fue posible realizar los cambios solicitados, intente de nuevo");
                }
            } else {
                ArchivosJustifica infoArchivo = null;
                JustificaAdm justificaAdm = evento.getJustificaAdm();
                JustificaTipos justificaTipos = evento.getJustificaTipos();

                System.out.println("Usuario al que se le realiza la adicion de rol: " + fenrir.getUsuario(evento.getUsuario()).getIdUsuario());
                fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
                fenrir.actualizarUsuario(evento.getUsuarioNegocio());
            }

            /**
             * @Author Carlos Torres
             * @Mantis 13176
             * @Chaged
             */
            auditoria.borrarDatosTerminal(String.valueOf(evento.getUsuarioNegocio().getIdUsuario()));
            datos = fenrir.consultarUsuariosPorCirculo(evento.getCirculo());
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al actualizar los datos del usuario.", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al actualizar los datos del usuario.", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.USUARIO_HABILITACION_OK);
        return evRespuesta;
    }

    /**
     * Esta acción de negocio se encarga de consultar el perfil de un usuario
     * (roles y estaciones) El evento debe tener el username del usuario
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta consultarUsuarioConRelaciones(EvnAdministracionFenrir evento) throws EventoException {
        String username = evento.getNombreUsuario();
        System.out.println("USUARIO BY NAME " + username);

        Usuario usuario = null;
        List rolesUsuario = null;
        List tiposJustificaciones = null;
        /**
         * Roles del usuario en sesion
         */
        List roles = null;
        /**
         * Roles permitidos para asignar el administrador regional
         */
        boolean administradorRegional = false;
        List datos = null;
        try {
            usuario = fenrir.getUsuarioNRelationsByUsername(username);
            System.out.println("usuarioRespuesta" + usuario.getUsuarioId());
            String fechaIni = evento.getFechaIni();
            String fechaFin = evento.getFechaFin();
            /**
             * Si es administrador regional lista unicamente los roles
             * permitodos para el
             */
            long idUsuario = fenrir.darIdUsuario(evento.getUsuario().getUsuarioId());

//            System.out.println("ID DE USUARIO CONSULTAR JUSTIFICACIONES: " + fenrir.darIdUsuario(usuario.getUsuarioId()));
            datos = fenrir.consultarJustificacionesUsuarios((int) fenrir.darIdUsuario(usuario.getUsuarioId()), fechaIni, fechaFin);
            if (datos == null) {
                throw new EventoException(
                        "Ocurrió un error al consultar la lista de justificaciones por usuarios en el sistema",
                        null);
            }

            String tipoJust = evento.getTipoJust();
            tiposJustificaciones = fenrir.consultarTiposJustificaciones(tipoJust);
            if (tiposJustificaciones == null) {
                throw new EventoException(
                        "Ocurrió un error al consultar la lista de justificaciones por usuarios en el sistema",
                        null);
            }

            System.out.println("USUARIO PARA BUSCAR ROLES " + idUsuario);
            rolesUsuario = fenrir.darRolUsuario(idUsuario);
            List rolesString = new ArrayList();
            Iterator itRoles = rolesUsuario.iterator();
            while (itRoles.hasNext()) {
                Rol rol = (Rol) itRoles.next();
                rolesString.add(rol.getRolId());
                System.out.println(rol.getRolId());
            }
            /**
             * @Autor: Edgar Lora
             * @Mantis: 0013008
             * @Requerimiento: 058_453
             */
            if (!rolesString.contains(CRoles.ADMINISTRADOR_NACIONAL)) {
                administradorRegional = true;
            }
            if (administradorRegional) {
                System.out.println("entro a buscar roles");
                roles = hermod.getOPLookupCodes(COPLookupTypes.ADMINISTRADOR_REGIONAL_ROLES);
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar el usuario", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar el usuario", e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionFenrir ev = new EvnRespAdministracionFenrir(usuario, EvnRespAdministracionFenrir.CONSULTA_PERFIL_USUARIO_OK);
        ev.setRolesAdministradorRegional(roles);
        ev.setJustificacionesPerfiles(datos);
        ev.setTipoJustificaciones(tiposJustificaciones);
        return ev;
    }

    /**
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información de la  <code>FormasPago</code> a ser consultado.
     *
     * @return <code>EvnRespAdministracionFenrir</code> con la lista los campos
     * de captura para la forma de pago solicitada
     *
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir consultarCamposCaptura(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            datos = fenrir.consultarCamposCaptura(evento.getTipoPago());
            if (datos == null) {
                throw new EventoException(
                        "Ocurrió un error al consultar la lista de campos de captura",
                        null);
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de campos de captura", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de campos de captura", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.CONSULTA_CAMPOS_CAPTURA_OK);
        return evRespuesta;
    }

    /**
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información de  <code>FormaPagoCampos</code> a ser actualizado.
     *
     * @return <code>EvnRespAdministracionFenrir</code> con la lista los campos
     * de captura para la forma de pago solicitada
     *
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir actualizarCamposCaptura(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            if (fenrir.actualizarCamposCaptura(evento.getFormaPagoCampos())) {
                datos = fenrir.consultarCamposCaptura(evento.getTipoPago());
            }
        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al actualizar el campo de captura", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al actualizar el campo de captura", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.CONSULTA_CAMPOS_CAPTURA_OK);
        return evRespuesta;
    }

    /**
     * @param evento de tipo <code>EvnAdministracionFenrir</code>
     *
     * @return <code>EvnRespAdministracionFenrir</code> con la lista los campos
     * de captura en el sistema
     *
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir cargarCamposCaptura(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            datos = fenrir.cargarCamposCaptura();
            if (datos == null) {
                throw new EventoException(
                        "Ocurrió un error al consultar la lista de campos de captura",
                        null);
            }

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de campos de captura", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al consultar la lista de campos de captura", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.CARGA_CAMPOS_CAPTURA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de invocar el servicio para la creación de
     * usuarios en el sistema
     *
     * @param evento de tipo <code>EvnAdministracionFenrir</code> con la
     * información del <code>Usuario</code> a ser consultado.
     *
     * @return <code>EvnRespAdministracionFenrir</code> con la lista los
     * usuarios existentes en el sistema.
     *
     * @throws <code>EventoException</code>
     */
    private EvnRespAdministracionFenrir crearFormaPago(EvnAdministracionFenrir evento)
            throws EventoException {

        List datos = null;

        try {
            TipoPago tipoPago = fenrir.crearFormaPago(evento.getNombreFormaPago());
            if (tipoPago.getIdTipoDocPago() > 0) {
                String camposCaptura = evento.getCamposCaptura();

                String[] camposCapturaArray = camposCaptura.split(",");
                for (int i = 0; i < camposCapturaArray.length; i++) {
                    FormaPagoCampos formaPagoCampos = new FormaPagoCampos();
                    formaPagoCampos.setIdCamposCaptura(Integer.parseInt(camposCapturaArray[i]));
                    formaPagoCampos.setIdFormaPago((int)tipoPago.getIdTipoDocPago());
                    formaPagoCampos.setEstado(true);
                    fenrir.actualizarCamposCaptura(formaPagoCampos);
                }

            }
            System.out.println("ID FORMA PAGO GUARDADA: "+tipoPago.getIdTipoDocPago());
            
            datos = hermod.getTiposPago();
            /* if (fenrir.crearFormaPago(evento.getNombreFormaPago())) {
                //datos = fenrir.consultarUsuariosPorCirculo(evento.getCirculo());
            } else {
                throw new EventoException("Ocurrió un error al crear la forma de pago en el sistema", null);
            }*/

        } catch (FenrirException e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al crear la forma de pago en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(AnAdministracionFenrir.class, "Ocurrió un error al crear la forma de pago en el sistema", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionFenrir evRespuesta
                = new EvnRespAdministracionFenrir(datos, EvnRespAdministracionFenrir.FORMA_PAGO_CREAR_OK);
        return evRespuesta;
    }

}
