package gov.sir.fenrir.ejb;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.InfoUsuario;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.ArchivosJustifica;
import gov.sir.core.negocio.modelo.FormaPagoCampos;
import gov.sir.core.negocio.modelo.JustificaAdm;
import gov.sir.core.negocio.modelo.JustificaTipos;
import gov.sir.core.negocio.modelo.TramiteSuspension;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.FenrirService;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import java.util.Date;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Nivel;
import org.auriga.core.modelo.transferObjects.Rol;

/**
 * @author Mc'Carthy Newball
 *
 * Clase cascaron EJB para el servicio Fenrir FenrirServiceBean
 *
 * @ejb:bean generate="true" name="ejb/FenrirServiceEJB"
 * display-name="FenrirService Session Bean" description="FenrirService Session
 * Bean" type="Stateless" transaction-type="Bean"
 * jndi-name="ejb/FenrirServiceEJB" view-type="both"
 *
 * @ejb.transaction type="Never"
 *
 * @oc4j:bean
 *
 * @ejb.security-identity use-caller-identity="true"
 */
public class FenrirServiceEJBBean implements SessionBean, FenrirServiceInterface {

    private static FenrirService fenrir = null;

    public FenrirServiceEJBBean() {
        fenrir = FenrirService.getInstance();
    }

    public void ejbCreate() throws CreateException, EJBException {
        // Write your code here
    }

    /**
     * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(SessionContext arg0) throws EJBException {

    }

    /**
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove() throws EJBException {

    }

    /**
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate() throws EJBException {

    }

    /**
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate() throws EJBException {

    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#validarUsuario(java.lang.String,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean validarUsuario(String login, String password) throws FenrirException {
        return fenrir.validarUsuario(login, password);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#agregarAuditoria(long,
     * gov.sir.core.negocio.modelo.InfoUsuario)
     * @ejb:interface-method view-type="both"
     */
    public int agregarAuditoria(long id, InfoUsuario info) throws FenrirException {
        return fenrir.agregarAuditoria(id, info);
    }

    /**
     * @see gov.sir.fenrir.interfaz.FenrirServiceInterface#darRolUsuario(long)
     * @ejb:interface-method view-type="both"
     */
    public List darRolUsuario(long id) throws FenrirException {
        return fenrir.darRolUsuario(id);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#darEstacionUsuario(long,
     * org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List darEstacionUsuario(long id, Rol rol) throws FenrirException {
        return fenrir.darEstacionUsuario(id, rol);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#darEstacionUsuario(long,
     * org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List darEstacionesUsuario(long id) throws FenrirException {
        return fenrir.darEstacionesUsuario(id);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#hacerLogout(java.lang.Integer)
     * @ejb:interface-method view-type="both"
     */
    public void hacerLogout(Integer idLogin) throws FenrirException {
        fenrir.hacerLogout(idLogin);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#darIdUsuario(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public long darIdUsuario(String username) throws FenrirException {
        return fenrir.darIdUsuario(username);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#darCirculoEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     * @ejb:interface-method view-type="both"
     */
    public Circulo darCirculoEstacion(Estacion estacion) throws FenrirException {
        return fenrir.darCirculoEstacion(estacion);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#darUsuariosRol(org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List darUsuariosRol(Rol rol) throws FenrirException {
        return fenrir.darUsuariosRol(rol);
    }

    /**
     * @see gov.sir.fenrir.interfaz.FenrirServiceInterface#finalizar()
     * @ejb:interface-method view-type="both"
     */
    public void finalizar() throws FenrirException {
        fenrir.finalizar();
    }

    /**
     * @see gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarRoles()
     * @ejb:interface-method view-type="both"
     */
    public List consultarRoles() throws FenrirException {
        return fenrir.consultarRoles();
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarUsuarios(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List consultarUsuarios(String nombreUsuario) throws FenrirException {
        return fenrir.consultarUsuarios(nombreUsuario);
    }
    
    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarRespuestasUsuarios(java.lang.Integer, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List consultarRespuestasUsuarios(int idUsuario, String turno) throws FenrirException {
        return fenrir.consultarRespuestasUsuarios(idUsuario, turno);
    }
    
    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#eliminarTramiteSuspensionTurno(java.lang.Integer, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public void eliminarTramiteSuspensionTurno(int idUsuario, String turno) throws FenrirException {
        fenrir.eliminarTramiteSuspensionTurno(idUsuario, turno);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarJustificacionesUsuarios(java.lang.Integer, java.util.Date, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List consultarJustificacionesUsuarios(int idUsuario, String fechaIni, String fechaFin) throws FenrirException {
        return fenrir.consultarJustificacionesUsuarios(idUsuario, fechaIni, fechaFin);
    }
    
    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarTiposJustificaciones(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List consultarTiposJustificaciones(String tipoJust) throws FenrirException {
        return fenrir.consultarTiposJustificaciones(tipoJust);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#crearUsuario(gov.sir.core.negocio.modelo.Usuario,
     * java.util.List, org.auriga.core.modelo.transferObjects.Responsabilidad,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean crearUsuario(Usuario usuario, List roles, Usuario usuarioSistema) throws FenrirException {
        return fenrir.crearUsuario(usuario, roles, usuarioSistema);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#crearRol(org.auriga.core.modelo.transferObjects.Rol,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public List crearRol(Rol rol, Usuario usuario) throws FenrirException {
        return fenrir.crearRol(rol, usuario);
    }

    /**
     * @see gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarEstaciones()
     * @ejb:interface-method view-type="both"
     */
    public List consultarEstaciones() throws FenrirException {
        return fenrir.consultarEstaciones();
    }

    /**
     * @see gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarNiveles()
     * @ejb:interface-method view-type="both"
     */
    public List consultarNiveles() throws FenrirException {
        return fenrir.consultarNiveles();
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#crearNivel(org.auriga.core.modelo.transferObjects.Nivel)
     * @ejb:interface-method view-type="both"
     */
    public List crearNivel(Nivel nivel) throws FenrirException {
        return fenrir.crearNivel(nivel);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#eliminarNivel(org.auriga.core.modelo.transferObjects.Nivel)
     * @ejb:interface-method view-type="both"
     */
    public List eliminarNivel(Nivel nivel) throws FenrirException {
        return fenrir.eliminarNivel(nivel);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarEstacionesPorCirculo(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List consultarEstacionesPorCirculo(Circulo circulo) throws FenrirException {
        return fenrir.consultarEstacionesPorCirculo(circulo);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarUsuariosPorCirculoRol(gov.sir.core.negocio.modelo.Circulo,
     * org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List consultarUsuariosPorCirculoRol(Circulo circulo, Rol rol) throws FenrirException {
        return fenrir.consultarUsuariosPorCirculoRol(circulo, rol);
    }

    /**
     * @author Daniel Forero
     * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
     *
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarUsuariosPorCirculoRolByEstado(gov.sir.core.negocio.modelo.Circulo,
     * org.auriga.core.modelo.transferObjects.Rol, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List consultarUsuariosPorCirculoRolByEstado(Circulo circulo, Rol rol, boolean estado) throws FenrirException {
        return fenrir.consultarUsuariosPorCirculoRolByEstado(circulo, rol, estado);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarUsuariosPorCirculo(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List consultarUsuariosPorCirculo(Circulo circulo) throws FenrirException {
        return fenrir.consultarUsuariosPorCirculo(circulo);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#tieneRolAdministrativo(java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public boolean tieneRolAdministrativo(List roles) throws FenrirException {
        return fenrir.tieneRolAdministrativo(roles);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getRolesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     * @ejb:interface-method view-type="both"
     */
    public List getRolesEstacion(Estacion estacion) throws FenrirException {
        return fenrir.getRolesEstacion(estacion);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#cambiarPassword(gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean cambiarPassword(Usuario usuario) throws FenrirException {
        return fenrir.cambiarPassword(usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#habilitarUsuario(gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public void habilitarUsuario(Usuario usuario) throws FenrirException {
        fenrir.habilitarUsuario(usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#actualizarUsuario(gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public void actualizarUsuario(Usuario usuario) throws FenrirException {
        fenrir.actualizarUsuario(usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#nuevoArchivo(gov.sir.core.negocio.modelo.ArchivosJustifica)
     * @ejb:interface-method view-type="both"
     */
    public ArchivosJustifica nuevoArchivo(ArchivosJustifica archivo) throws FenrirException {
        return fenrir.nuevoArchivo(archivo);
    }

     /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#crearRespuestasUsuarios(gov.sir.core.negocio.modelo.TramiteSuspension,
     * gov.sir.core.negocio.modelo.ArchivosJustifica)
     * @ejb:interface-method view-type="both"
     */
    public void crearRespuestasUsuarios(TramiteSuspension tramSuspension, ArchivosJustifica infoArchivo) throws FenrirException {
        fenrir.crearRespuestasUsuarios(tramSuspension, infoArchivo);
    }
    
    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#crearJustificaAdm(gov.sir.core.negocio.modelo.JustificaAdm,
     * gov.sir.core.negocio.modelo.ArchivosJustifica,
     * gov.sir.core.negocio.modelo.JustificaTipos)
     * @ejb:interface-method view-type="both"
     */
    public void crearJustificaAdm(JustificaAdm justificaAdm, ArchivosJustifica infoArchivo, JustificaTipos justificaTipos) throws FenrirException {
        fenrir.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#eliminarUsuarioDelLDAP(gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public void eliminarUsuarioDelLDAP(Usuario usuario) throws FenrirException {
        fenrir.eliminarUsuarioDelLDAP(usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#crearUsuarioEnLDAP(gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public void crearUsuarioEnLDAP(Usuario usuario) throws FenrirException {
        fenrir.crearUsuarioEnLDAP(usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#creacionAutomaticaDeUsuarios()
     * @ejb:interface-method view-type="both"
     */
    public void creacionAutomaticaDeUsuarios() throws FenrirException {
        fenrir.creacionAutomaticaDeUsuarios();
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getUsuario(org.auriga.core.modelo.transferObjects.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Usuario getUsuario(org.auriga.core.modelo.transferObjects.Usuario usuario) throws FenrirException {
        return fenrir.getUsuario(usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getUsuarioById(gov.sir.core.negocio.modelo.Usuario.UsuarioPk)
     * @ejb:interface-method view-type="both"
     */
    public Usuario getUsuarioById(UsuarioPk usuario) throws FenrirException {
        return fenrir.getUsuarioById(usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getRecibosEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     * @ejb:interface-method view-type="both"
     */
    public EstacionRecibo getRecibosEstacion(Estacion estacion) throws FenrirException {
        return fenrir.getRecibosEstacion(estacion);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#loadRolesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     * @ejb:interface-method view-type="both"
     */
    public void loadRolesEstacion(Estacion estacion) throws FenrirException {
        fenrir.loadRolesEstacion(estacion);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getRolesPotencialesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     * @ejb:interface-method view-type="both"
     */
    public List getRolesPotencialesEstacion(Estacion estacion) throws FenrirException {
        return fenrir.getRolesPotencialesEstacion(estacion);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getUsuariosEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     * @ejb:interface-method view-type="both"
     */
    public List getUsuariosEstacion(Estacion estacion) throws FenrirException {
        return fenrir.getUsuariosEstacion(estacion);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getUsuariosPotencialesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     * @ejb:interface-method view-type="both"
     */
    public List getUsuariosPotencialesEstacion(Estacion estacion) throws FenrirException {
        return fenrir.getUsuariosPotencialesEstacion(estacion);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#addRolEstacion(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol, Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public void addRolEstacion(Estacion estacion, Rol rol, Usuario usuario) throws FenrirException {
        fenrir.addRolEstacion(estacion, rol, usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#removeRolEstacion(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public void removeRolEstacion(Estacion estacion, Rol rol) throws FenrirException {
        fenrir.removeRolEstacion(estacion, rol);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacion(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public Estacion getEstacion(Usuario usuario, Rol rol) throws FenrirException {
        return fenrir.getEstacion(usuario, rol);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacion(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List getEstaciones(Usuario usuario, Rol rol) throws FenrirException {
        return fenrir.getEstaciones(usuario, rol);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacion(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List getEstacionesUsuarioByEstadoRol(Usuario usuario, Rol rol, boolean estado) throws FenrirException {
        return fenrir.getEstacionesUsuarioByEstadoRol(usuario, rol, estado);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacion(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public Estacion getEstacionByEstado(Usuario usuario, Rol rol, boolean estado) throws FenrirException {
        return fenrir.getEstacionByEstado(usuario, rol, estado);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getRolByID(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Rol getRolByID(String idRol) throws FenrirException {
        return fenrir.getRolByID(idRol);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#actualizarRol(org.auriga.core.modelo.transferObjects.Rol,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public void actualizarRol(Rol rol, Usuario usuario) throws FenrirException {
        fenrir.actualizarRol(rol, usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getUsuarioByUsername(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioByUsername(String username) throws FenrirException {
        return fenrir.getUsuarioByUsername(username);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List getEstacionesRol(Rol rol) throws FenrirException {
        return fenrir.getEstacionesRol(rol);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List getEstacionesRolByCirculo(Rol rol, Circulo circulo) throws FenrirException {
        return fenrir.getEstacionesRolByCirculo(rol, circulo);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#obtenerEstacionesUsuarioByEstadoRol(Rol
     * rol, Circulo circulo,boolean activo)
     * @ejb:interface-method view-type="both"
     */
    public List obtenerEstacionesUsuarioByEstadoRol(Rol rol, Circulo circulo, boolean activo) throws FenrirException {
        return fenrir.obtenerEstacionesUsuarioByEstadoRol(rol, circulo, activo);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List getEstacionesCirculo(Circulo circulo) throws FenrirException {
        return fenrir.getEstacionesCirculo(circulo);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#agregarEstacionRolUsuario(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public void agregarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws FenrirException {
        fenrir.agregarEstacionRolUsuario(estacion, rol, usuario, usuarioSistema);

    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#eliminarEstacionRolUsuario(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario,
     * Circulo, List turnos)
     * @ejb:interface-method view-type="both"
     */
    public void eliminarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Circulo circulo, List turnos) throws FenrirException {
        fenrir.eliminarEstacionRolUsuario(estacion, rol, usuario, circulo, turnos);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#agregarRolUsuario(org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public void agregarRolUsuario(Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws FenrirException {
        fenrir.agregarRolUsuario(rol, usuario, usuarioSistema);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#agregarEstacionRol(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public void agregarEstacionRol(Estacion estacion, Rol rol, Usuario usuario) throws FenrirException {
        fenrir.agregarEstacionRol(estacion, rol, usuario);
    }

    /**
     * @param circulo TODO
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#eliminarEstacionRol(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public void eliminarEstacionRol(Estacion estacion, Rol rol, Circulo circulo, List turnos) throws FenrirException {
        fenrir.eliminarEstacionRol(estacion, rol, circulo, turnos);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#eliminarEstacionRol(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public List consultarUsuariosRolEstacion(Rol rol, Estacion estacion) throws FenrirException {
        return fenrir.consultarUsuariosRolEstacion(rol, estacion);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getUsuarioByUsername(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioNRelationsByUsername(String username) throws FenrirException {
        return fenrir.getUsuarioNRelationsByUsername(username);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#actualizarEstadoEstacionRol
     * (org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,
     * Circulo, boolean)
     * @ejb:interface-method view-type="both"
     */
    public void actualizarEstadoEstacionRol(Estacion estacion, Rol rol, Circulo circulo, boolean estado, Usuario usuario) throws FenrirException {
        fenrir.actualizarEstadoEstacionRol(estacion, rol, circulo, estado, usuario);
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#trasladarUsuarioCirculo
     * (org.auriga.core.modelo.transferObjects.Usuario, Circulo)
     * @ejb:interface-method view-type="both"
     */
    public void trasladarUsuarioCirculo(Usuario usuario, Circulo circulo) throws FenrirException {
        fenrir.trasladarUsuarioCirculo(usuario, circulo);
    }

    /**
     * Author: Santiago V�squez Change: 1156.111.USUARIOS.ROLES.INACTIVOS
     *
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarUsuariosPorCirculoRol(gov.sir.core.negocio.modelo.Circulo,
     * java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public List consultarUsuariosPorCirculoRoles(Circulo circulo, List roles) throws FenrirException {
        return fenrir.consultarUsuariosPorCirculoRoles(circulo, roles);
    }
    
    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarCamposCaptura(gov.sir.core.negocio.modelo.TipoPago)
     * @ejb:interface-method view-type="both"
     */
    public List consultarCamposCaptura(TipoPago tipoPago) throws FenrirException {
        return fenrir.consultarCamposCaptura(tipoPago);
    }
    
    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#actualizarCamposCaptura(gov.sir.core.negocio.modelo.FormaPagoCampos)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarCamposCaptura(FormaPagoCampos formaPagoCampos) throws FenrirException {
        return fenrir.actualizarCamposCaptura(formaPagoCampos);
    }
    
    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#cargarCamposCaptura()
     * @ejb:interface-method view-type="both"
     */
    public List cargarCamposCaptura() throws FenrirException {
        return fenrir.cargarCamposCaptura();
    }
    
    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#crearFormaPago(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public TipoPago crearFormaPago(String nombreFormaPago) throws FenrirException {
        return fenrir.crearFormaPago(nombreFormaPago);
    }

}
