package gov.sir.fenrir;

import java.util.Iterator;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;

import org.auriga.core.modelo.Servicio;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Nivel;
import org.auriga.core.modelo.transferObjects.Rol;

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
import gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.dao.ConfiguracionPropiedadesException;
import gov.sir.fenrir.dao.DAOException;
import gov.sir.fenrir.dao.ErrorConexionException;
import gov.sir.fenrir.dao.FenrirDAO;
import gov.sir.fenrir.dao.FenrirFactory;
import gov.sir.fenrir.dao.FenrirLDAP;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * Esta clase es la fachada del servicio. El servicio Fenrir se encarga de
 * manejar la autenticaciï¿½n, autorizaciï¿½n y creaciï¿½n de objetos relacionados con
 * el manejo de seguridad en el sistema.
 */
public class FenrirService implements Servicio, FenrirServiceInterface {

    private static FenrirService instancia = new FenrirService();
    private FenrirFactory f = null;
    private FenrirDAO dao = null;
    private FenrirLDAP ldap = null;
    private static Hashtable cacheCirculosEstacion;

    /**
     * Constructor por defecto de la clase FenrirService
     */
    private FenrirService() {
        cacheCirculosEstacion = new Hashtable();
    }

    /**
     * Este mï¿½todo se encarga de retornar (o crear si aï¿½n no existe) la ï¿½nica
     * instancia de esta fachada del sesrvicio.
     *
     * @return <CODE>FenrirService</CODE>
     */
    public static FenrirService getInstance() {
        return instancia;
    }

    /**
     * Se encarga de validar el nombre de usuario y la contraseï¿½a
     *
     * @param login nombre de usuario ingresado
     * @param password contraseï¿½a del usuario
     * @return boolean si pudo validar satisfactoriamente al usuario
     * @throws FenrirException
     */
    public boolean validarUsuario(String login, String password) throws FenrirException {
        f = FenrirFactory.getInstance();

        try {
            ldap = f.getFenrirLDAP();
            return ldap.validarUsuario(login, password);
        } catch (AuthenticationException e) {
            FenrirException fe = new FenrirException(FenrirException.USUARIO_INVALIDO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (OperationNotSupportedException e) {
            FenrirException fe = new FenrirException(FenrirException.USUARIO_BLOQUEADO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (ConfiguracionPropiedadesException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (NamingException e) {
            FenrirException fe = new FenrirException(FenrirException.ERROR_CONEXION_DIRECTORIO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Se encarga de realizar la auditorï¿½a
     *
     * @param login nombre de usuario al que se le va a realizar la auditorï¿½a
     * @return un entero que identifica al usuario
     * @throws FenrirException
     */
    public int agregarAuditoria(long id, InfoUsuario info) throws FenrirException {
        f = FenrirFactory.getInstance();

        try {
            dao = f.getFenrirDAO();
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException("No fue posible agregar auditorï¿½a", e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }

        int b;

        try {
            b = dao.agregarAuditoria(id, info);
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }

        return b;
    }

    /**
     * Por medio de este mï¿½todo se obtienen todas las estaciones a las que
     * pertenece un usuario determinado
     *
     * @param login nombre de usuario del que se quieren saber sus estaciones
     * @return una lista con las estaciones a las que pertenece el usuario
     * @throws FenrirException
     */
    public List darRolUsuario(long id) throws FenrirException {
        f = FenrirFactory.getInstance();

        try {
            dao = f.getFenrirDAO();
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException("Error en la conexiï¿½n con la fuente de datos", e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }

        try {
            return dao.darRolUsuario(id);
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Por medio de este mï¿½todo se obtienen todas las estaciones a las que
     * pertenece un usuario determinado
     *
     * @param login nombre de usuario del que se quieren saber sus estaciones
     * @return una lista con las estaciones a las que pertenece el usuario
     * @throws FenrirException
     */
    public List darEstacionUsuario(long id, Rol rol) throws FenrirException {
        f = FenrirFactory.getInstance();

        try {
            dao = f.getFenrirDAO();
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException("Error en la conexiï¿½n con la fuente de datos", e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }

        try {
            return dao.darEstacionUsuario(id, rol);
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Por medio de este mï¿½todo se obtienen todas las estaciones que tiene un
     * usuario
     *
     * @param login nombre de usuario del que se quieren saber sus estaciones
     * @return una lista con las estaciones a las que pertenece el usuario
     * @throws FenrirException
     */
    public List darEstacionesUsuario(long id) throws FenrirException {
        f = FenrirFactory.getInstance();

        try {
            dao = f.getFenrirDAO();
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException("Error en la conexiï¿½n con la fuente de datos", e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }

        try {
            return dao.darEstacionesUsuario(id);
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Se hace logout del usuario en el sistema
     *
     * @param idLogin identificador del usuario
     * @throws FenrirException
     */
    public void hacerLogout(Integer idLogin) throws FenrirException {
        f = FenrirFactory.getInstance();

        try {
            dao = f.getFenrirDAO();
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }

        try {
            dao.hacerLogout(idLogin);
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Finaliza el DAO
     *
     * @throws FenrirException
     */
    public void finalizar() throws FenrirException {
        FenrirFactory fenrir = FenrirFactory.getInstance();

        if (fenrir != null) {
            FenrirDAO daoFenrir = null;

            try {
                daoFenrir = fenrir.getFenrirDAO();
            } catch (ErrorConexionException e) {
                FenrirException fe = new FenrirException(e.getMessage(), e);
                Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
                throw fe;
            } finally {
                if (daoFenrir != null) {
                    daoFenrir.finalizar();
                }
            }
        }
    }

    /**
     * Este mï¿½todo se encarga de consultar el Identificador numï¿½rico asociado a
     * un uusario determinado
     *
     * @param username
     * @return
     */
    public long darIdUsuario(String username) throws FenrirException {
        f = FenrirFactory.getInstance();

        try {
            dao = f.getFenrirDAO();
            return dao.getIdUsuario(username);

        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Mï¿½todo para consultar el cï¿½rculo asociado a una <code>Estaciï¿½n</code>
     * determinada
     *
     * @param estaciï¿½n
     */
    public Circulo darCirculoEstacion(Estacion estacion) throws FenrirException {
        f = FenrirFactory.getInstance();
        Circulo circulo = null;
        try {
            dao = f.getFenrirDAO();
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }

        try {
            String idEstacion = estacion.getEstacionId();
            circulo = (Circulo) cacheCirculosEstacion.get(idEstacion);
            if (circulo == null) {
                circulo = dao.darCirculoEstacion(estacion);
                cacheCirculosEstacion.put(idEstacion, circulo);
            }
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }
        return circulo;
    }

    /**
     * Consulta los usuarios Asociados con un Rol
     *
     * @param rol throws FenrirException
     */
    public List darUsuariosRol(Rol rol) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.darUsuariosRol(rol);
        } catch (ErrorConexionException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        }
    }

    /**
     * Este mï¿½todo obtiene todas la lista de Roles disponibles en el sistema.
     *
     * @return una lista con los roles disponibles en el sistema
     * @throws FenrirException
     * @see gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarRoles()
     */
    public List consultarRoles() throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarRoles();
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Este mï¿½todo obtiene todas una lista de usuarios respecto a un nombre de
     * usuario dado como parï¿½metro.
     *
     * @return una lista con los Usuarios que se ajusten al filtro
     * @throws FenrirException
     */
    public List consultarUsuarios(String nombreUsuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarUsuarios(nombreUsuario);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }
    
    public List consultarRespuestasUsuarios(int idUsuario, String turno) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarRespuestasUsuarios(idUsuario, turno);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }
    
    public void eliminarTramiteSuspensionTurno(int idUsuario, String turno) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            dao.eliminarTramiteSuspensionTurno(idUsuario, turno);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }
    

    /**
     * Este mï¿½todo obtiene todas una lista de justificaciones por usuariis
     * respecto a un id de usuarios de usuario dado como parï¿½metro.
     *
     * @return una lista con los justificaciones para el usuario solicitado que
     * se ajusten al filtro
     * @throws FenrirException
     */
    public List consultarJustificacionesUsuarios(int idUsuario, String fechaIni, String fechaFin) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarJustificacionesUsuarios(idUsuario, fechaIni, fechaFin);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }
    
    /**
     * Este mï¿½todo obtiene una lista de tipos de justificaciones
     * respecto a un tipoJust dado como parï¿½metro.
     *
     * @return una lista con los tipos de justificaciones para el tipoJust solicitado que
     * se ajusten al filtro
     * @throws FenrirException
     */
    public List consultarTiposJustificaciones(String tipoJust) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarTiposJustificaciones(tipoJust);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }   
    

     /**
             * Este mï¿½todo se encarga de la creaciï¿½n de un usuario en el sistema
             * (LDAP, Auriga, SIR)
             *
             * @param usuario
             * @param roles
             * @param responsabilidad
             * @return true si pudo crear el usuario
             * @throws FenrirException
             */

    public boolean crearUsuario(Usuario usuario, List roles, Usuario usuarioSistema)
            throws FenrirException {
        f = FenrirFactory.getInstance();

        boolean creacionUsuario = false;

        try {

            if (usuario.getUsername() == null || usuario.getUsername().trim().equals("")) {
                throw new DAOException("El nombre de usuario ingresado es invï¿½lido.");
            }

            dao = f.getFenrirDAO();
            ldap = f.getFenrirLDAP();

            try {
                ldap.crearUsuario(usuario);
            } catch (NamingException e) {

            }

            creacionUsuario = dao.crearUsuario(usuario, roles, usuarioSistema);

        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.USUARIOS_CREACION_FALLIDA, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }

        return creacionUsuario;
    }

    /**
     * Agrega un <code>Rol</code> a la configuraciï¿½n del sistema
     *
     * @param rol el <code>Rol</code> que se va a crear en el sistema.
     * @param usuario que crea el rol
     * @return <code>List</code> con la lista actualizada de roles existentes en
     * el sistema
     * @see org.auriga.core.modelo.transferObjects.Rol
     * @throws <code>FenrirException</code>
     */
    public List crearRol(Rol rol, Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.crearRol(rol, usuario);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.ROL_NO_CREADO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Este mï¿½todo obtiene todas la lista de estaciones disponibles en el
     * sistema.
     *
     * @return una lista con las estaciones disponibles en el sistema
     * @throws FenrirException
     * @see gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarEstaciones()
     */
    public List consultarEstaciones() throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarEstaciones();
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.ESTACION_NO_CONSULTADA, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Consulta la lista de Niveles disponibles en el sistema
     *
     * @return <code>List</code> con la lista actualizada de los niveles
     * existentes en el sistema
     * @throws <code>FenrirException</code>
     */
    public List consultarNiveles() throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarNiveles();
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.NIVEL_NO_CONSULTADO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Crea un <code>Nivel</code> en el sistema
     *
     * @param rol  <code>Nivel</code> a ser creado
     * @return lista actualizada de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List crearNivel(Nivel nivel) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.crearNivel(nivel);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.NIVEL_NO_CREADO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Elimina un <code>Nivel</code> de la configuraciï¿½n del sistema
     *
     * @param rol el <code>Nivel</code> que se va a eliminar del sistema.
     * @return <code>List</code> con la lista actualizada de niveles existentes
     * en el sistema
     * @see org.auriga.core.modelo.transferObjects.Rol
     * @throws <code>FenrirException</code>
     */
    public List eliminarNivel(Nivel nivel) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.eliminarNivel(nivel);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.NIVEL_NO_ELIMINADO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Consulta las estaciones relacionadas con un <code>Circulo</code>
     * determinado
     *
     * @param circulo el <code>Circulo</code> del cual se requiere consultar sus
     * estaciones
     * @return la lista de las estaciones relacionadas con el
     * <code>Circulo</code> dado como parï¿½metro
     * @throws FenrirException
     */
    public List consultarEstacionesPorCirculo(Circulo circulo) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarEstacionesPorCirculo(circulo);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.ESTACIONES_POR_CIRCULO_NO_CONSULTADAS, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo y un Rol
     *
     * @param circulo  <code>String</code> a ser consultado
     * @param rol  <code>String</code> a ser consultado
     * @return lista de usuarios que cumplan la condiciï¿½n
     * @throws FenrirException
     */
    public List consultarUsuariosPorCirculoRol(Circulo circulo, Rol rol) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarUsuariosPorCirculoRol(circulo, rol);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.USUARIOS_POR_CIRCULO_ROL_NO_CONSULTADOS, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @author Daniel Forero
     * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
     *
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarUsuariosPorCirculoRolByEstado(gov.sir.core.negocio.modelo.Circulo,org.auriga.core.modelo.transferObjects.Rol,
     * boolean)
     * @ejb:interface-method view-type="both"
     */
    public List consultarUsuariosPorCirculoRolByEstado(Circulo circulo, Rol rol, boolean estado) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarUsuariosPorCirculoRolByEstado(circulo, rol, estado);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.USUARIOS_POR_CIRCULO_ROL_NO_CONSULTADOS, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo
     *
     * @param circulo  <code>String</code> a ser consultado
     * @return lista de usuarios que cumplan la condiciï¿½n
     * @throws FenrirException
     */
    public List consultarUsuariosPorCirculo(Circulo circulo) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarUsuariosPorCirculo(circulo);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.USUARIOS_POR_CIRCULO_NO_CONSULTADOS, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Determina si un <code>Usuario</code> tiene privilegios para acceder a las
     * pantallas administrativas del sistema.
     *
     * @param roles  <code>List</code> con los roles autorizados para el usuario
     * @return boolean true si tiene permisos administrativos o de lo contrario
     * false.
     * @throws FenrirException
     */
    public boolean tieneRolAdministrativo(List roles) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            ldap = f.getFenrirLDAP();
            return ldap.tieneRolAdministrativo(roles);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.VALIDA_ROL_ADMIN, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Permite Cambiar el Password de un <code>Usuario</code>
     *
     * @param roles  <code>Usuario</code> con la nueva clave
     * @return boolean true si se pudo cambiar el password
     * @throws FenrirException
     */
    public boolean cambiarPassword(Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            ldap = f.getFenrirLDAP();
            return ldap.cambiarPassword(usuario);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.CAMBIO_PASSWORD_FALLIDO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Habilita o deshabilita un  <code>Usuario</code> en el LDAP segï¿½n si su
     * propiedad activo es true o false.
     *
     * @param roles  <code>Usuario</code> con la nueva clave
     * @return boolean true si se pudo cambiar el password
     * @throws FenrirException
     */
    public void habilitarUsuario(Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            //ldap = f.getFenrirLDAP();
            dao = f.getFenrirDAO();
            dao.habilitarUsuario(usuario);
            /**
             * No deshbilitar en el OID
             */
            /*if (usuario.isActivo()) {
                ldap.habilitarUsuario(usuario);
            }*/
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.HABILITACION_USUARIO_FALLIDA, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Actualiza un  <code>Usuario</code> en el modelo operativo, esta
     * actualizaciï¿½n incluye el nombre, apellido1 y apellido2
     *
     * @param usuario  <code>Usuario</code> con los nuevos atributos del mismo
     * @return void
     * @throws FenrirException
     */
    public void actualizarUsuario(Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            //ldap = f.getFenrirLDAP();
            dao = f.getFenrirDAO();
            dao.actualizarUsuario(usuario);
            //ldap.actualizarUsuario(usuario);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.HABILITACION_USUARIO_FALLIDA, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    public ArchivosJustifica nuevoArchivo(ArchivosJustifica archivo) throws FenrirException {
        System.out.println("llego a nuevoArchivo FenrirService geremias");
        f = FenrirFactory.getInstance();

        //boolean archivoGuardado = false;
        try {
            dao = f.getFenrirDAO();
            return dao.nuevoArchivo(archivo);

        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.NUEVO_ARCHIVO_FALLIDO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }

    }
    
    public void crearRespuestasUsuarios(TramiteSuspension tramSuspension, ArchivosJustifica infoArchivo) throws FenrirException {
        f = FenrirFactory.getInstance();

        try {
            dao = f.getFenrirDAO();
            dao.crearRespuestasUsuarios(tramSuspension, infoArchivo);

        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.NUEVA_JUSTIFICACION_FALLIDA, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    public void crearJustificaAdm(JustificaAdm justificaAdm, ArchivosJustifica infoArchivo, JustificaTipos justificaTipos) throws FenrirException {
        System.out.println("llego a JustificaAdm FenrirService geremias");
        f = FenrirFactory.getInstance();

        //boolean archivoGuardado = false;
        try {
            dao = f.getFenrirDAO();
            dao.crearJustificaAdm(justificaAdm, infoArchivo, justificaTipos);

        } catch (DAOException e) {
            FenrirException fe = new FenrirException(FenrirException.NUEVA_JUSTIFICACION_FALLIDA, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }

    }

    /**
     * Elimina un <code>Usuario</code> del LDAP
     *
     * @param roles  <code>Usuario</code> con la nueva clave
     * @return boolean true si se pudo cambiar el password
     * @throws FenrirException
     */
    public void eliminarUsuarioDelLDAP(Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            ldap = f.getFenrirLDAP();
            ldap.eliminarUsuario(usuario);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.ELIMINACION_USUARIO_LDAP_FALLIDA, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Mï¿½todo utilizado para crear automï¿½ticamente en el LDAP los usuarios de
     * SIR que no estï¿½n actualmente registrados en el Directorio.
     *
     * @throws FenrirException
     */
    public void creacionAutomaticaDeUsuarios() throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            ldap = f.getFenrirLDAP();
            ldap.creacionAutomaticaDeUsuarios();
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.CREACION_AUTOMATICA_USUARIOS_LDAP_FALLIDA, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Crea un <code>Usuario</code> en el LDAP
     *
     * @return boolean true si se pudo cambiar el password
     * @throws FenrirException
     */
    public void crearUsuarioEnLDAP(Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            boolean creacionUsuario = false;
            ldap = f.getFenrirLDAP();
            creacionUsuario = ldap.crearUsuario(usuario);
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Consulta la informaciï¿½n de un <code>Usuario</code> almacenada en la base
     * de datos partiendo de un Usuario de Auriga
     *
     * @param usuario  <code>Usuario</code>
     * @return
     * @throws FenrirException
     */
    public Usuario getUsuario(org.auriga.core.modelo.transferObjects.Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getUsuario(usuario);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.USUARIOS_NO_ENCONTRADO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }

    }

    /**
     * Consulta un <code>Usuario</code> almacenado en la base de datos
     *
     * @param usuario  <code>Usuario</code>
     * @return
     * @throws FenrirException
     */
    public Usuario getUsuarioById(gov.sir.core.negocio.modelo.UsuarioPk usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getUsuarioByID(usuario);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.USUARIOS_NO_ENCONTRADO, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getRecibosEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public EstacionRecibo getRecibosEstacion(Estacion estacion)
            throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getRecibosEstacion(estacion);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.RECIBOS_NO_ENCONTRADOS);
        } catch (Throwable th) {
            FenrirException fe
                    = new FenrirException(FenrirException.ERROR_CONEXION_DIRECTORIO);
        }
        return null;
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getRolesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getRolesEstacion(Estacion estacion) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getRolesEstacion(estacion);
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#loadRolesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public void loadRolesEstacion(Estacion estacion) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            dao.loadRolesEstacion(estacion);
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#loadUsuariosEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getUsuariosEstacion(Estacion estacion) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getUsuariosEstacion(estacion);
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getUsuariosPotencialesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getUsuariosPotencialesEstacion(Estacion estacion) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getUsuariosPotencialesEstacion(estacion);
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getRolesPotencialesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getRolesPotencialesEstacion(Estacion estacion) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getRolesPotencialesEstacion(estacion);
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#addRolEstacion(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol, Usuario usuario)
     */
    public void addRolEstacion(Estacion estacion, Rol rol, Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            dao.addRolEstacion(estacion, rol, usuario);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#removeRolEstacion(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol)
     */
    public void removeRolEstacion(Estacion estacion, Rol rol) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            dao.removeRolEstacion(estacion, rol);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacion(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     */
    public Estacion getEstacion(Usuario usuario, Rol rol) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getEstacion(usuario, rol);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstaciones(Usuario usuario, Rol rol) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getEstaciones(usuario, rol);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacionesUsuarioByEstadoRol(org.auriga.core.modelo.transferObjects.Rol,
     * boolean)
     */
    public List getEstacionesUsuarioByEstadoRol(Usuario usuario, Rol rol, boolean estado) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getEstacionesUsuarioByEstadoRol(usuario, rol, estado);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacion(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     */
    public Estacion getEstacionByEstado(Usuario usuario, Rol rol, boolean estado) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getEstacionByEstado(usuario, rol, estado);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    public Rol getRolByID(String idRol) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getRolByID(idRol);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    public void actualizarRol(Rol rol, Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            dao.actualizarRol(rol, usuario);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getUsuarioByUsername(java.lang.String)
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioByUsername(String username) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getUsuarioByUsername(username);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstacionesRol(Rol rol) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getEstacionesRol(rol);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstacionesRolByCirculo(Rol rol, Circulo circulo) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getEstacionesRolByCirculo(rol, circulo);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#obtenerEstacionesUsuarioByEstadoRol(Rol
     * rol, Circulo circulo, boolean activo)
     */
    public List obtenerEstacionesUsuarioByEstadoRol(Rol rol, Circulo circulo, boolean activo) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.obtenerEstacionesUsuarioByEstadoRol(rol, circulo, activo);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstacionesCirculo(Circulo circulo) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getEstacionesCirculo(circulo);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#agregarEstacionRolUsuario(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario,
     * Usuario usuario)
     */
    public void agregarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            dao.agregarEstacionRolUsuario(estacion, rol, usuario, usuarioSistema);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#eliminarEstacionRolUsuario(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario,
     * Circulo, List turnos)
     */
    public void eliminarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Circulo circulo, List turnos) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            //VALIDACIONES

            //1. Si se quiere eliminar el rol-estacion del usuario y existan mï¿½s usuarios que lo vean, lo puede
            //   eliminar sin ningï¿½n problema
            List usuarios = dao.consultarUsuariosRolEstacion(rol, estacion);
            if (usuarios.size() >= 2) {
                dao.eliminarEstacionRolUsuario(estacion, rol, usuario);
            } else {
                //2. En caso que sï¿½lo se tenga un usuario asociado se debe verificar si se puede eliminar
                //   tambiï¿½n la relaciï¿½n entre el rol y la estaciï¿½n para deshabilitar que sigan llegando
                //   turnos, para esto se deben aplicar las validaciones de eliminaciï¿½n de rol-estaciï¿½n:

                //2.1 validar que NO se pueden eliminar estaciones que tengan turnos para el respectivo rol
                if (!turnos.isEmpty()) {
                    String turnosText = "";
                    Iterator it = turnos.iterator();
                    if (it.hasNext()) {
                        String turno = (String) it.next();
                        turnosText = turno;
                        while (it.hasNext()) {
                            turno = (String) it.next();
                            turnosText = turnosText + ", " + turno;
                        }
                    }

                    throw new FenrirException("No se puede eliminar. Existen turnos asociados a la estaciï¿½n los cuales quedarï¿½an inaccesibles para los usuarios. Turnos: " + turnosText
                            + ". Si se desea eliminar esta estaciï¿½n de " + usuario.getUsuarioId() + " debe primero darle acceso a otro usuario para que pueda acceder a los respectivos turnos");
                }

                /**
                 * Author: Santiago Vï¿½squez Change:
                 * 1156.111.USUARIOS.ROLES.INACTIVOS Validaciï¿½n de roles no
                 * configurados
                 *
                 */
                /**
                 * Author: DNilson Olaya Gómez DNilson226  Change:
                 * Ajustes Rol Consulta Nacional
                 * desactivaciion validación de eliminación de roles
                 */                
                /**
                 * DNilson226 
                boolean rolesActivos = dao.hayEstacionAsociadoARolPorCirculo(circulo, rol, estacion);
                if (!rolesActivos) {
                    throw new FenrirException("No se puede desactivar la estaciï¿½n, debe existir por lo menos una estaciï¿½n activa del respectivo rol en el cï¿½rculo.");
                }

                //2.2 No se puede eliminar una estaciï¿½n si es la ï¿½nica que actualmente estï¿½ atendiendo la carga del rol para el cï¿½rculo
                List estaciones = dao.getEstacionesRolByCirculo(rol, circulo);
                if (estaciones.size() <= 1) {
                    Rol r = dao.getRolByID(rol.getRolId());
                    throw new FenrirException("No se puede eliminar, " + usuario.getUsuarioId() + " es el ï¿½ltimo usuario que posee la estaciï¿½n disponible para atender el rol " + r.getNombre()
                            + ". Si se desea eliminar esta estaciï¿½n de " + usuario.getUsuarioId() + " debe primero darle acceso a otro usuario para que pueda recibir los turnos que llegan al respectivo rol");
                }
                */

                //Si cumple con las validaciones se elimina el rol-estacion del usuario y finalmente el rol-estacion
                //para que no sigan llegando turnos.
                dao.eliminarEstacionRolUsuario(estacion, rol, usuario);
                dao.eliminarEstacionRol(estacion, rol);
            }

        } catch (DAOException e) {
            FenrirException fe = new FenrirException("ERROR: ", e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException("ERROR: ", th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#agregarRolUsuario(org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario,
     * Usuario usuario)
     */
    public void agregarRolUsuario(Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            dao.agregarRolUsuario(rol, usuario, usuarioSistema);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#agregarEstacionRol(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol)
     */
    public void agregarEstacionRol(Estacion estacion, Rol rol, Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            dao.agregarEstacionRol(estacion, rol, usuario);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#eliminarEstacionRol(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,
     * Circulo)
     */
    public void eliminarEstacionRol(Estacion estacion, Rol rol, Circulo circulo, List turnos) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();

            //Validaciones:
            //1. validar que no existan usuarios actuales a la estaciï¿½n en el rol
            List usuarios = dao.consultarUsuariosRolEstacion(rol, estacion);
            if (!usuarios.isEmpty()) {
                String usuariosText = "";
                Iterator it = usuarios.iterator();
                if (it.hasNext()) {
                    org.auriga.core.modelo.transferObjects.Usuario us = (org.auriga.core.modelo.transferObjects.Usuario) it.next();
                    usuariosText = us.getUsuarioId();
                    while (it.hasNext()) {
                        us = (org.auriga.core.modelo.transferObjects.Usuario) it.next();
                        usuariosText = usuariosText + ", " + us.getUsuarioId();
                    }
                }

                throw new FenrirException("No se puede eliminar, existen actualmente usuarios que tienen acceso a la estaciï¿½n: " + usuariosText);
            }

            //2. validar que NO se pueden eliminar estaciones que tengan turnos para el respectivo rol
            if (!turnos.isEmpty()) {
                String turnosText = "";
                Iterator it = turnos.iterator();
                if (it.hasNext()) {
                    String turno = (String) it.next();
                    turnosText = turno;
                    while (it.hasNext()) {
                        turno = (String) it.next();
                        turnosText = turnosText + ", " + turno;
                    }
                }

                throw new FenrirException("No se puede eliminar, existen actualmente turnos asignados a la estaciï¿½n del respectivo rol: " + turnosText);
            }

            //3. No se puede eliminar una estaciï¿½n si es la ï¿½nica que actualmente estï¿½ atendiendo la carga del rol para el cï¿½rculo
            List estaciones = dao.getEstacionesRolByCirculo(rol, circulo);
            if (estaciones.size() <= 1) {
                throw new FenrirException("No se puede eliminar, debe dejar por lo menos una estaciï¿½n a la cual lleguen los turnos del respectivo rol");
            }

            //Cumple todas las validaciones
            dao.eliminarEstacionRol(estacion, rol);

        } catch (DAOException e) {
            FenrirException fe = new FenrirException("ERROR: ", e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            th.printStackTrace();
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#consultarUsuariosRolEstacion(org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List consultarUsuariosRolEstacion(Rol rol, Estacion estacion) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarUsuariosRolEstacion(rol, estacion);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#getUsuarioByUsername(java.lang.String)
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioNRelationsByUsername(String username) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.getUsuarioNRelationsByUsername(username);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * @see
     * gov.sir.fenrir.interfaz.FenrirServiceInterface#actualizarEstadoEstacionRol
     * (org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,
     * Circulo, boolean, Usuario)
     */
    public void actualizarEstadoEstacionRol(Estacion estacion, Rol rol, Circulo circulo, boolean estado, Usuario usuario) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            
           /**
            * Author: DNilson Olaya Gómez DNilson226  Change:
            * Ajustes Rol Consulta Nacional
            * desactivaciion validación de eliminación de roles                   
           
            if (!estado) {
                //No se puede desactivar una estaciï¿½n si es la ï¿½nica que actualmente estï¿½ atendiendo la carga del rol para el cï¿½rculo
                List estaciones = dao.getEstacionesActivasRolByCirculo(rol, circulo);
                if (estaciones.size() <= 1) {
                    throw new FenrirException("No se puede desactivar la estaciï¿½n, debe existir por lo menos una estaciï¿½n activa del respectivo rol en el cï¿½rculo.");
                }
            }            
            */

            //Cumple todas las validaciones
            dao.actualizarEstadoEstacionRol(estacion, rol, circulo, estado, usuario);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException("ERROR: ", e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            th.printStackTrace();
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    public void trasladarUsuarioCirculo(Usuario usuario, Circulo circulo) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            dao.trasladarUsuarioCirculo(usuario, circulo);
        } catch (DAOException e) {
            FenrirException fe = new FenrirException(e.getMessage(), e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }

    /**
     * Consulta los roles de los usuarios por circulos
     *
     * @param circulo
     * @param roles
     * @return
     * @throws FenrirException
     */
    public List consultarUsuariosPorCirculoRoles(Circulo circulo, List roles) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarUsuariosPorCirculoRoles(circulo, roles);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.USUARIOS_POR_CIRCULO_ROL_NO_CONSULTADOS, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }
    
    /**
     * Consulta la lista de Campos de Captura dado una forma de pago
     *
     * @param tipoPago  <code>TipoPago</code> a ser consultado
     * @return lista de campos de captura que cumplan la condiciï¿½n
     * @throws FenrirException
     */
    public List consultarCamposCaptura(TipoPago tipoPago) throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.consultarCamposCaptura(tipoPago);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.CAMPOS_CAPTURA_NO_CONSULTADOS, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }
    
    /**
     * Actualiza la relacion de campo captura y forma pago en la tabla
     * SIR_OP_REL_FPAGO_CAMPOS
     *
     * @param formaPagoCampos  <code>FormaPagoCampos</code> a ser actualizado
     * @return true o false dependiendo resultado de la operaciï¿½n
     * @throws FenrirException
     */
    public boolean actualizarCamposCaptura(FormaPagoCampos formaPagoCampos) throws FenrirException {
        f = FenrirFactory.getInstance();
        boolean actualizado = false;
        try {
            dao = f.getFenrirDAO();
            actualizado = dao.actualizarCamposCaptura(formaPagoCampos);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.CAMPOS_CAPTURA_NO_ACTUALIZADOS, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
        
        return actualizado;
    }
    
    /**
     * Consulta la lista de todos los Campos de Captura
     *
     * @return lista de campos de captura del sistema
     * @throws FenrirException
     */
    public List cargarCamposCaptura() throws FenrirException {
        f = FenrirFactory.getInstance();
        try {
            dao = f.getFenrirDAO();
            return dao.cargarCamposCaptura();
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.CARGA_CAMPOS_CAPTURA_NO_CONSULTADOS, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
    }
        
    /**
     * Crea una nueva forma de pago en la tabla SIR_OP_TIPO_DOC_PAGO
     *
     * @param nombreFormaPago  <code>String</code> a ser creado
     * @return objeto TipoPago
     * @throws FenrirException
     */
    public TipoPago crearFormaPago(String nombreFormaPago) throws FenrirException {
        f = FenrirFactory.getInstance();
        TipoPago tipoPago = new TipoPago();
        try {
            dao = f.getFenrirDAO();
            tipoPago = dao.crearFormaPago(nombreFormaPago);
        } catch (DAOException e) {
            FenrirException fe
                    = new FenrirException(FenrirException.FORMA_PAGO_NO_CREADA, e);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), e);
            throw fe;
        } catch (Throwable th) {
            FenrirException fe = new FenrirException(th.getMessage(), th);
            Log.getInstance().error(FenrirService.class, fe.getMessage(), th);
            throw fe;
        }
        
        return tipoPago;
    }

}
