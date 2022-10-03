package gov.sir.fenrir.dao;

import java.util.List;
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
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced;
import java.util.Date;
import java.util.Map;

/**
 * Esta interfaz define los m�todos de acceso a datos que deben implementar las
 * implementaciones concretas para el servicio Fenrir
 *
 * @author jfrias
 * @author dsalas
 */
public interface FenrirDAO {

    /**
     * Se hace la auditoria del login el usuario
     *
     * @param user el usuario al que se le va a agregar la auditoria
     * @return un numero entero que identifica al usuario
     * @throws CredencialesInvalidasException cuando hay un error con los datos
     * del usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     * @throws UsuarioDuplicadoException cuando ya hay un usuario registrado con
     * el mismo nombre de usuario
     */
    public int agregarAuditoria(long id, InfoUsuario info) throws ErrorConexionException;

    /**
     * Se obtienen las estaciones a las que pertenece el usuario
     *
     * @param user el usuario del que se quieren saber sus estaciones
     * @return una lista con las estaciones a las que pertenece el usuario
     * @throws CredencialesInvalidasException cuando hay un error con los datos
     * del usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darRolUsuario(long id) throws ErrorConexionException;

    /**
     * Se obtienen las estaciones a las que pertenece un usuario
     *
     * @param id el usuario del que se quieren sus roles
     * @return una lista con los roles a los que pertenece el usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darEstacionUsuario(long id, Rol rol) throws ErrorConexionException;

    /**
     * Se obtienen las estaciones que tiene un usuario.
     *
     * @param id el usuario del que se quieren sus roles
     * @return una lista con los roles a los que pertenece el usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darEstacionesUsuario(long id) throws ErrorConexionException;

    /**
     * Se finaliza todo el DAO
     *
     */
    public void finalizar();

    /**
     * Se registra el logout del usuario
     *
     * @param idLogin el identificador del usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public void hacerLogout(Integer idLogin) throws ErrorConexionException;

    /**
     * Se obtiene el id del usuario ingresado
     *
     * @param username el Username del usuario
     * @return el id del usuario
     */
    public long getIdUsuario(String username) throws ErrorConexionException;

    /**
     * M�todo para consultar el c�rculo asociado a una <code>Estaci�n</code>
     * determinada
     *
     * @param estaci�n
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public Circulo darCirculoEstacion(Estacion estacion) throws ErrorConexionException;

    /**
     * Consulta los usuarios Asociados con un Rol
     *
     * @param rol
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darUsuariosRol(Rol rol) throws ErrorConexionException;

    /**
     * Consulta los roles existentes en el sistema
     *
     * @return la lista de los roles existentes en el sistema
     * @throws DAOException
     */
    public List consultarRoles() throws DAOException;

    /**
     * Consulta los usuarios existentes en el sistema
     *
     * @param nombre el Username del usuario
     * @return la lista de las responsabilidades existentes en el sistema
     * @throws DAOException
     */
    public List consultarUsuarios(String nombre) throws DAOException;

    public List consultarRespuestasUsuarios(int idUsuario, String turno) throws DAOException;
    
    public void eliminarTramiteSuspensionTurno(int idUsuario, String turno) throws DAOException;
    
    /**
     * Consulta las justificaciones por usuarios existentes en el sistema
     *
     * @param idUsuario el Id del usuario
     * @param fechaIni el Id del usuario
     * @param fechaFin el Id del usuario
     * @return la lista de las justificaciones para el usuario existentes en el
     * sistema
     * @throws DAOException
     */
    public List consultarJustificacionesUsuarios(int idUsuario, String fechaIni, String fechaFin) throws DAOException;
    
    /**
     * Consulta los tipos de justificaciones
     *
     * @param tipoJust el nombre de los tipos de justificaciones a buscar de ese tipo
     * @return la lista de los tipos justificaciones 
     * @throws DAOException
     */
    public List consultarTiposJustificaciones(String tipoJust) throws DAOException;

    /**
     * Consulta las estaciones existentes en el sistema
     *
     * @return la lista de las estaciones existentes en el sistema
     * @throws DAOException
     */
    public List consultarEstaciones() throws DAOException;

    /**
     * Crea un usuario en el sistema
     *
     * @param usuario el <code>Usuario</code> a ser creado
     * @param roles  <code>List</code> con la lista de roles asignados al usuario
     * @param responsabilidad  <code>Responsabilidad</code> que puede tener el
     * usuario en el sistema
     * @return lista actualizada de los usuarios existentes en el sistema
     * @throws DAOException
     */
    public boolean crearUsuario(Usuario usuario, List roles, Usuario usuarioSistema)
            throws DAOException;

    /**
     * Crea un <code>Rol</code> en el sistema
     *
     * @param rol  <code>Rol</code> a ser creado
     * @param usuario que crea el rol
     * @return lista actualizada de los roles existentes en el sistema
     * @throws DAOException
     */
    public List crearRol(Rol rol, Usuario usuario) throws DAOException;

    /**
     * Consulta los niveles existentes en el sistema
     *
     * @return la lista de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List consultarNiveles() throws DAOException;

    /**
     * Crea un <code>Nivel</code> en el sistema
     *
     * @param rol  <code>Nivel</code> a ser creado
     * @return lista actualizada de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List crearNivel(Nivel nivel) throws DAOException;

    /**
     * Elimina un <code>Nivel</code> del sistema
     *
     * @param rol  <code>Nivel</code> a ser eliminado
     * @return lista actualizada de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List eliminarNivel(Nivel nivel) throws DAOException;

    /**
     * Consulta las estaciones relacionadas con un <code>Circulo</code>
     * determinado
     *
     * @param circulo el <code>Circulo</code> del cual se requiere consultar sus
     * estaciones
     * @return la lista de las estaciones relacionadas con el
     * <code>Circulo</code> dado como par�metro
     * @throws DAOException
     */
    public List consultarEstacionesPorCirculo(Circulo circulo) throws DAOException;

    /**
     * Consulta los horarios de trabajo existentes en el sistema
     *
     * @return la lista de los horarios de trabajo
     * @throws DAOException
     */
    public List consultarHorarios() throws DAOException;

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo y un Rol
     *
     * @param circulo  <code>Circulo</code> a ser consultado
     * @param rol  <code>Rol</code> a ser consultado
     * @return lista de usuarios que cumplan la condici�n
     * @throws DAOException
     */
    public List consultarUsuariosPorCirculoRol(Circulo circulo, Rol rol) throws DAOException;

    /**
     * @author Daniel Forero
     * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
     *
     * Consulta la lista de usuarios del sistema dado un Circulo y un Rol. La
     * lista de usuarios se filtra de acuerdo a su estado (activo, inactivo)
     *
     * @param circulo  <code>Circulo</code> a ser consultado
     * @param rol  <code>Rol</code> a ser consultado
     * @param estado  <code>Boolean</code> representa el estado del rol (activo,
     * inactivo)
     * @return lista de usuarios que cumplan la condici�n
     * @throws DAOException Si ocurre una excepcion consultando la lista de
     * usuarios.
     */
    public List consultarUsuariosPorCirculoRolByEstado(Circulo circulo, Rol rol, boolean estado) throws DAOException;

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo
     *
     * @param circulo  <code>Circulo</code> a ser consultado
     * @return lista de usuarios que cumplan la condici�n
     * @throws DAOException
     */
    public List consultarUsuariosPorCirculo(Circulo circulo) throws DAOException;

    /**
     * Habilita o deshabilita un  <code>Usuario</code> seg�n si su propiedad
     * activo es true o false.
     *
     * @param roles  <code>Usuario</code> con la nueva clave
     * @return boolean true si se pudo cambiar el password
     * @throws DAOException
     */
    public void habilitarUsuario(Usuario usuario) throws DAOException;

    /**
     * Actualiza un  <code>Usuario</code> en el modelo operativo, esta
     * actualizaci�n incluye el nombre, apellido1 y apellido2
     *
     * @param usuario  <code>Usuario</code> con los nuevos atributos del mismo
     * @return void
     * @throws DAOException
     */
    public void actualizarUsuario(Usuario usuario) throws DAOException;

    /**
     * crea un  <code>ArchivosJustifica</code> en el modelo operativo
     *
     * @param archivo  <code>ArchivosJustifica</code> con los nuevos atributos
     * del mismo
     * @return <code>ArchivosJustifica</code> con todos sus atributos
     * @throws DAOException
     */
    public ArchivosJustifica nuevoArchivo(ArchivosJustifica archivo) throws DAOException;

    
    /**
     * crea un  <code>TramiteSuspension</code> en el modelo operativo
     *
     * @param tramSuspension <code>JustificaAdm</code> con los nuevos atributos
     * del mismo
     * @param infoArchivo <code>ArchivosJustifica</code> con los nuevos
     * atributos del mismo
     * @return void
     * @throws DAOException
     */
    public void crearRespuestasUsuarios(TramiteSuspension tramSuspension, ArchivosJustifica infoArchivo) throws DAOException;

    /**
     * crea un  <code>JustificaAdm</code> en el modelo operativo
     *
     * @param justificaAdm <code>JustificaAdm</code> con los nuevos atributos
     * del mismo
     * @param infoArchivo <code>ArchivosJustifica</code> con los nuevos
     * atributos del mismo
     * @param justificaTipos <code>JustificaTipos</code> con los nuevos
     * atributos del mismo
     * @return void
     * @throws DAOException
     */
    public void crearJustificaAdm(JustificaAdm justificaAdm, ArchivosJustifica infoArchivo, JustificaTipos justificaTipos) throws DAOException;

    /**
     * Consulta la informaci�n de un <code>Usuario</code> almacenada en la base
     * de datos partiendo de un Usuario de Auriga
     *
     * @param usuario  <code>Usuario</code>
     * @return
     * @throws DAOException
     */
    public Usuario getUsuario(org.auriga.core.modelo.transferObjects.Usuario usuario) throws DAOException;

    /**
     * Obtiene un <code>Usuario</code> dado su identificador.<p>
     * Retorna null, si no se encontr� el <code>Usuario</code> con el
     * identificador dado.
     *
     * @param bID identificador del banco.
     * @return <code>Usuario</code> con todos sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    public Usuario getUsuarioByID(UsuarioPk bID) throws DAOException;

    /**
     * Obtiene el n�mero de recibo inicial y final de una <code>estacion</code>
     *
     * @param estacion
     * @return Objeto <code>EstacionRecibo</code> con serie de recibos
     * @throws DAOException
     */
    public EstacionRecibo getRecibosEstacion(Estacion estacion)
            throws DAOException;

    /**
     * Retorna los roles que tienen acceso a una estacion
     *
     * @param estacion
     * @return
     * @throws DAOException
     */
    public List getRolesEstacion(Estacion estacion) throws ErrorConexionException;

    /**
     * Carga en el objeto
     *
     * @param estacion
     * @throws ErrorConexionException
     */
    public void loadRolesEstacion(Estacion estacion) throws ErrorConexionException;

    /**
     * Devuelve la lista de roles potenciales para una estacion
     *
     * @param estacion
     * @return
     * @throws ErrorConexionException
     */
    public List getRolesPotencialesEstacion(Estacion estacion) throws ErrorConexionException;

    /**
     * Devuelve la lista de usuarios que tienen acceso a una estaci�n en un
     * momento dado
     *
     * @param estacion
     * @return
     * @throws ErrorConexionException
     */
    public List getUsuariosEstacion(Estacion estacion) throws ErrorConexionException;

    /**
     * Devuelve el conjunto de usuarios de los roles asociados a la estacion,
     * pero que no tienen acceso a la estaci�n
     *
     * @param estacion
     * @return
     * @throws ErrorConexionException
     */
    public List getUsuariosPotencialesEstacion(Estacion estacion) throws ErrorConexionException;

    /**
     * A�ade un rol a una estacion
     *
     * @param estacion
     * @param rol
     * @param Usuario que adiciona el rol en la estacion
     * @throws ErrorConexionException
     */
    public void addRolEstacion(Estacion estacion, Rol rol, Usuario usuario) throws DAOException;

    /**
     * Quita un rol a una estacion
     *
     * @param estacion
     * @param rol
     * @throws ErrorConexionException
     */
    public void removeRolEstacion(Estacion estacion, Rol rol) throws DAOException;

    /**
     * Agrega un usuario a una estaci�n, para un rol particular
     *
     * @param estacion
     * @param rol
     * @param usuario
     * @throws DAOException
     */
    public void addRolUsuarioEstacion(Estacion estacion, Rol rol, Usuario usuario)
            throws DAOException;

    /**
     * Quita un usuario a una estaci�n, para un rol particular
     *
     * @param estacion
     * @param rol
     * @param usuario
     * @throws DAOException
     */
    public void removeRolUsuarioEstacion(Estacion estacion, Rol rol, Usuario usuario)
            throws DAOException;

    /**
     * Obtener una estaci�n en la que el usuario tenga permisos para trabajar en
     * el rol
     *
     * @param usuario
     * @param rol
     * @return
     * @throws DAOException
     */
    public Estacion getEstacion(Usuario usuario, Rol rol)
            throws DAOException;

    /**
     * Obtener una estaci�n en la que el usuario tenga permisos para trabajar en
     * el rol
     *
     * @param usuario
     * @param rol
     * @return
     * @throws DAOException
     */
    public List getEstaciones(Usuario usuario, Rol rol)
            throws DAOException;

    /**
     * Obtener una estaci�n activa en la que el usuario tenga permisos para
     * trabajar en el rol
     *
     * @param usuario
     * @param rol
     * @return
     * @throws DAOException
     */
    public List getEstacionesUsuarioByEstadoRol(Usuario usuario, Rol rol, boolean estado)
            throws DAOException;

    /**
     * Obtener una estaci�n en la que el usuario tenga permisos para trabajar en
     * el rol
     *
     * @param usuario
     * @param rol
     * @return
     * @throws DAOException
     */
    public Estacion getEstacionByEstado(Usuario usuario, Rol rol, boolean estado)
            throws DAOException;

    /**
     * Obtener un rol a partir de su identificador
     *
     * @param idRol
     * @return
     * @throws DAOException
     */
    public Rol getRolByID(String idRol)
            throws DAOException;

    /**
     * Actualiza todos los datos del rol
     *
     * @param rol, usuario que actualiza el rol
     * @throws DAOException
     */
    public void actualizarRol(Rol rol, Usuario usuario)
            throws DAOException;

    /**
     * Se obtiene el usuario de auriga, dado el nombre de usuario
     *
     * @param username
     * @throws DAOException
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioByUsername(String username)
            throws DAOException;

    /**
     * Se obtiene una lista de estaciones del rol seleccionado
     *
     * @param rol
     * @return
     * @throws DAOException
     */
    public List getEstacionesRol(Rol rol)
            throws DAOException;

    /**
     * Se obtiene una lista de estaciones del rol y nivel seleccionado
     *
     * @param rol
     * @param nivel
     * @return
     * @throws DAOException
     */
    public List getEstacionesRolByCirculo(Rol rol, Circulo circulo)
            throws DAOException;

    /**
     * Se obtiene la lista de estaciones de un rol, a partir de un circulo y el
     * estado
     *
     * @param rol
     * @param circulo
     * @param activo
     * @return
     * @throws Throwable
     */
    public List obtenerEstacionesUsuarioByEstadoRol(Rol rol, Circulo circulo, boolean activo)
            throws DAOException;

    /**
     * Se obtiene una lista de estaciones del nivel seleccionado
     *
     * @param rol
     * @param nivel
     * @return
     * @throws DAOException
     */
    public List getEstacionesCirculo(Circulo circulo)
            throws DAOException;

    /**
     * Agregar una estacion a un rol de un usuario espec�fico
     *
     * @param estacion
     * @param rol
     * @param usuario
     * @param usuario que agrega la estacion
     * @throws DAOException
     */
    public void agregarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema)
            throws DAOException;

    /**
     * Eliminar una estacion de un rol de un usuario espec�fico
     *
     * @param estacion
     * @param rol
     * @param usuario
     * @throws DAOException
     */
    public void eliminarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario)
            throws DAOException;

    /**
     * Agregar un rol a un usuario espec�fico
     *
     * @param rol
     * @param usuario
     * @param usuario que adiciona el rol al usuario
     * @throws DAOException
     */
    public void agregarRolUsuario(Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema)
            throws DAOException;

    /**
     * Agregar una estacion a un rol
     *
     * @param estacion
     * @param rol
     * @param Usuario que agrega la estacion a un rol
     * @throws DAOException
     */
    public void agregarEstacionRol(Estacion estacion, Rol rol, Usuario usuario)
            throws DAOException;

    /**
     * Eliminar una estacion de un rol
     *
     * @param estacion
     * @param rol
     * @throws DAOException
     */
    public void eliminarEstacionRol(Estacion estacion, Rol rol)
            throws DAOException;

    /**
     * Consulta los turnos que tienen asignados actualmente una estaci�n en el
     * rol determinado
     *
     * @param rol
     * @param estacion
     * @return
     * @throws DAOException
     */
    /*public List determinarTurnosUsuario(Rol rol, Estacion estacion)throws DAOException;*/
    /**
     * Consulta los usuarios de un rol y estacion especificos
     *
     * @param rol
     * @param estacion
     * @return
     * @throws DAOException
     */
    public List consultarUsuariosRolEstacion(Rol rol, Estacion estacion)
            throws DAOException;

    /**
     * Se obtiene el usuario de auriga, dado el nombre de usuario
     *
     * @param username
     * @throws DAOException
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioNRelationsByUsername(String username)
            throws DAOException;

    /**
     * Se obtiene una lista de estaciones del rol y nivel seleccionado
     *
     * @param rol
     * @param nivel
     * @return
     * @throws DAOException
     */
    public List getEstacionesActivasRolByCirculo(Rol rol, Circulo circulo)
            throws DAOException;

    /**
     * Eliminar una estacion de un rol
     *
     * @param estacion
     * @param rol
     * @param Usuario que modifica el estado de la estacion del rol
     * @throws DAOException
     */
    public void actualizarEstadoEstacionRol(Estacion estacion, Rol rol, Circulo circulo, boolean estado, Usuario usuario)
            throws DAOException;

    /**
     * Traslada un usuario a un c�rculo espec�fico. S�lo traslada la estaci�n
     * "propia" del usuario
     *
     * @param usuario Usuario a trasladar
     * @param circulo C�rculo al que va a pertenecer el usuario
     * @throws DAOException
     */
    public void trasladarUsuarioCirculo(Usuario usuario, Circulo circulo) throws DAOException;

    public List consultarUsuariosPorCirculoRoles(Circulo circulo, List roles) throws DAOException;

    /**
     * Author: Santiago V�squez Change: 1156.111.USUARIOS.ROLES.INACTIVOS
     * Validaci�n de roles no configurados
     */
    public boolean hayEstacionAsociadoARolPorCirculo(Circulo circulo, Rol rol, Estacion estacion) throws DAOException;

    /**
     * Consulta la lista de campos de captura para una forma de pago
     *
     * @param tipoPago  <code>TipoPago</code> a ser consultado
     * @return lista de campos de captura que cumplan la condici�n
     * @throws DAOException
     */
    public List consultarCamposCaptura(TipoPago tipoPago) throws DAOException;
    
    /**
     * Actualiza la relacion campo captura y forma de pago en la tabla
     * SIR_OP_REL_FPAGO_CAMPOS
     *
     * @param formaPagoCampos  <code>FormaPagoCampos</code> a ser actualizado
     * @return true o false dependiendo del resultado de la operaci�n
     * @throws DAOException
     */
    public boolean actualizarCamposCaptura(FormaPagoCampos formaPagoCampos) throws DAOException;
    
    /**
     * Consulta la lista de campos de captura del sistema
     *
     * @return lista de campos de captura del sistema
     * @throws DAOException
     */
    public List cargarCamposCaptura() throws DAOException;
    
    /**
     * Crea una nueva forma de pago
     *
     * @param nombreFormaPago  <code>String</code> a ser creado
     * @return Objeto TipoPago
     * @throws DAOException
     */
    public TipoPago crearFormaPago(String nombreFormaPago) throws DAOException;
}
