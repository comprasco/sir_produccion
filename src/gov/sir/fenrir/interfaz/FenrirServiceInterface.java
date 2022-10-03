package gov.sir.fenrir.interfaz;

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
import gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced;
import gov.sir.fenrir.FenrirException;
import java.util.Date;

/**
 * @author jfrias
 * @author dsalas
 */
public interface FenrirServiceInterface {

    /**
     * Se encarga de validar el nombre de usuario y la contrase�a
     *
     * @param login nombre de usuario ingresado
     * @param password contrase�a del usuario
     * @return boolean si pudo validar satisfactoriamente al usuario
     * @throws Throwable
     */
    public boolean validarUsuario(String login, String password) throws Throwable;

    /**
     * Se encarga de realizar la auditor�a
     *
     * @param login nombre de usuario al que se le va a realizar la auditor�a
     * @return un entero que identifica al usuario
     * @throws Throwable
     */
    public int agregarAuditoria(long id, InfoUsuario info) throws Throwable;

    /**
     * Por medio de este m�todo se obtienen todas las estaciones a las que
     * pertenece un usuario determinado
     *
     * @param login nombre de usuario del que se quieren saber sus estaciones
     * @return una lista con las estaciones a las que pertenece el usuario
     * @throws Throwable
     */
    public List darRolUsuario(long id) throws Throwable;

    /**
     * Por medio de este m�todo se obtienen todas las estaciones que tiene un
     * usuario en un rol.
     *
     * @param login nombre de usuario del que se quieren saber sus estaciones
     * @return una lista con las estaciones a las que pertenece el usuario
     * @throws Throwable
     */
    public List darEstacionUsuario(long id, Rol rol) throws Throwable;

    /**
     * Por medio de este m�todo se obtienen todas las estaciones que tiene un
     * usuario.
     *
     * @param login nombre de usuario del que se quieren saber sus estaciones
     * @return una lista con las estaciones a las que pertenece el usuario
     * @throws Throwable
     */
    public List darEstacionesUsuario(long id) throws Throwable;

    /**
     * Se hace logout del usuario en el sistema
     *
     * @param idLogin identificador del usuario
     * @throws FenrirException
     */
    public void hacerLogout(Integer idLogin) throws Throwable;

    /**
     * Este m�todo se encarga de consultar el Identificador num�rico asociado a
     * un uusario determinado
     *
     * @param username
     * @return
     */
    public long darIdUsuario(String username) throws Throwable;

    /**
     * M�todo para consultar el c�rculo asociado a una <code>Estaci�n</code>
     * determinada
     *
     * @param estaci�n
     */
    public Circulo darCirculoEstacion(Estacion estacion) throws Throwable;

    /**
     * Consulta los usuarios Asociados con un Rol
     *
     * @param rol throws Throwable
     */
    public List darUsuariosRol(Rol rol) throws Throwable;

    /**
     * Finaliza el DAO
     *
     * @throws FenrirException
     */
    public void finalizar() throws Throwable;

    /**
     * Consulta la lista de Roles disponibles en el sistema
     *
     * @return <code>List</code> con la lista actualizada de los roles
     * existentes en el sistema
     * @throws <code>Throwable</code>
     */
    public List consultarRoles() throws Throwable;

    /**
     * Este m�todo obtiene todas una lista de usuarios respecto a un nombre de
     * usuario dado como par�metro.
     *
     * @return una lista con los Usuarios que se ajusten al filtro
     * @throws Throwable
     */
    public List consultarUsuarios(String nombreUsuario) throws Throwable;
    
   
    /**
     * Este m�todo obtiene todas las justificaciones de un usuario respecto a un id de
     * usuario dado como par�metro.
     *
     * @return una lista con las justificaciones por Usuario que se ajusten al filtro
     * @throws Throwable
     */
    public List consultarJustificacionesUsuarios(int idUsuario, String fechaIni, String fechaFin) throws Throwable;
    
    /**
     * Este m�todo obtiene todas los tipos de justificaciones de un tipo
     *
     * @return una lista con los tipos de justificaciones por un tipo de justificaci�n
     * @throws Throwable
     */
    public List consultarTiposJustificaciones(String tipoJust) throws Throwable;

    /**
     * Este m�todo se encarga de la creaci�n de un usuario en el sistema (LDAP,
     * Auriga, SIR)
     *
     * @param usuario que se va a crear
     * @param usuarioSistema que va a crear al usuario Adicionar combo de
     * c�rculos a consulta de �ndices por pantalla adm. para nacional
     * @param responsabilidad
     * @return true si pudo crear el usuario
     * @throws Throwable
     */
    public boolean crearUsuario(Usuario usuario, List roles, Usuario usuarioSistema)
            throws Throwable;

    /**
     * Agrega un <code>Rol</code> a la configuraci�n del sistema
     *
     * @param rol el <code>Rol</code> que se va a crear en el sistema.
     * @param usuario que crea el rol
     * @return <code>List</code> con la lista actualizada de roles existentes en
     * el sistema
     * @see org.auriga.core.modelo.transferObjects.Rol
     * @throws <code>Throwable</code>
     */
    public List crearRol(Rol rol, Usuario usuario) throws Throwable;

    /**
     * Consulta la lista de Estaciones disponibles en el sistema
     *
     * @return <code>List</code> con la lista actualizada de las estaciones
     * existentes en el sistema
     * @throws <code>Throwable</code>
     */
    public List consultarEstaciones() throws Throwable;

    /**
     * Consulta la lista de Niveles disponibles en el sistema
     *
     * @return <code>List</code> con la lista actualizada de los niveles
     * existentes en el sistema
     * @throws <code>Throwable</code>
     */
    public List consultarNiveles() throws Throwable;

    /**
     * Agrega un <code>Nivel</code> a la configuraci�n del sistema
     *
     * @param rol el <code>Nivel</code> que se va a crear en el sistema.
     * @return <code>List</code> con la lista actualizada de niveles existentes
     * en el sistema
     * @see org.auriga.core.modelo.transferObjects.Rol
     * @throws <code>Throwable</code>
     */
    public List crearNivel(Nivel nivel) throws Throwable;

    /**
     * Elimina un <code>Nivel</code> de la configuraci�n del sistema
     *
     * @param rol el <code>Nivel</code> que se va a eliminar del sistema.
     * @return <code>List</code> con la lista actualizada de niveles existentes
     * en el sistema
     * @see org.auriga.core.modelo.transferObjects.Rol
     * @throws <code>Throwable</code>
     */
    public List eliminarNivel(Nivel nivel) throws Throwable;

    /**
     * Consulta las estaciones relacionadas con un <code>Circulo</code>
     * determinado
     *
     * @param circulo el <code>Circulo</code> del cual se requiere consultar sus
     * estaciones
     * @return la lista de las estaciones relacionadas con el
     * <code>Circulo</code> dado como par�metro
     * @throws Throwable
     */
    public List consultarEstacionesPorCirculo(Circulo circulo) throws Throwable;

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo y un Rol
     *
     * @param circulo  <code>String</code> a ser consultado
     * @param rol  <code>String</code> a ser consultado
     * @return lista de usuarios que cumplan la condici�n
     * @throws Throwable
     */
    public List consultarUsuariosPorCirculoRol(Circulo circulo, Rol rol) throws Throwable;

    /**
     * @author Daniel Forero
     * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
     *
     * Consulta la lista de usuarios del sistema dado un Circulo y un Rol. La
     * lista de usuarios se filtra de acuerdo a su estado (activo, inactivo)
     *
     * @param circulo <code>Circulo</code> a ser consultado
     * @param rol <code>Rol</code> a ser consultado
     * @param estado <code>Boolean</code> representa el estado del rol (activo,
     * inactivo)
     * @return lista de usuarios que cumplan la condici�n
     * @throws FenrirException Si ocurre una excepcion consultando la lista de
     * usuarios.
     */
    public List consultarUsuariosPorCirculoRolByEstado(Circulo circulo, Rol rol, boolean estado) throws Throwable;

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo
     *
     * @param circulo  <code>String</code> a ser consultado
     * @return lista de usuarios que cumplan la condici�n
     * @throws Throwable
     */
    public List consultarUsuariosPorCirculo(Circulo circulo) throws Throwable;

    /**
     * Determina si un <code>Usuario</code> tiene privilegios para acceder a las
     * pantallas administrativas del sistema.
     *
     * @param roles  <code>List</code> con los roles autorizados para el usuario
     * @return boolean true si tiene permisos administrativos o de lo contrario
     * false.
     * @throws Throwable
     */
    public boolean tieneRolAdministrativo(List roles) throws Throwable;

    /**
     * Devuelve la lista de roles de una estaci�n
     *
     * @param estacion
     * @return
     * @throws Throwable
     */
    public List getRolesEstacion(Estacion estacion) throws Throwable;

    /**
     * Permite Cambiar el Password de un <code>Usuario</code>
     *
     * @param roles  <code>Usuario</code> con la nueva clave
     * @return boolean true si se pudo cambiar el password
     * @throws Throwable
     */
    public boolean cambiarPassword(Usuario usuario) throws Throwable;

    /**
     * Habilita o deshabilita un  <code>Usuario</code> en el LDAP seg�n si su
     * propiedad activo es true o false.
     *
     * @param roles  <code>Usuario</code> con la nueva clave
     * @return boolean true si se pudo cambiar el password
     * @throws Throwable
     */
    public void habilitarUsuario(Usuario usuario) throws Throwable;

    /**
     * Actualiza un  <code>Usuario</code> en el modelo operativo, esta
     * actualizaci�n incluye el nombre, apellido1 y apellido2
     *
     * @param usuario  <code>Usuario</code> con los nuevos atributos del mismo
     * @return void
     * @throws Throwable
     */
    public void actualizarUsuario(Usuario usuario) throws Throwable;

    /**
     * Crea un nuevo registo en la tabla de <code>ArchivosJustifica</code>
     * @param archivo <code>ArchivosJustifica</code> con los nuevos atributos del mismo
     * @return <code>ArchivosJustifica</code> con todos sus atributos
     * @throws Throwable
     */
    public ArchivosJustifica nuevoArchivo(ArchivosJustifica archivo) throws Throwable;
    
    
    /**
     * Crea un nuevo registo en la tabla de <code>TramiteSuspension</code>
     * @param tramSuspension <code>JustificaAdm</code> con los nuevos atributos del mismo
     * @param infoArchivo <code>ArchivosJustifica</code> con los nuevos atributos del mismo   
     * @return void
     * @throws Throwable
     */
    public void crearRespuestasUsuarios(TramiteSuspension tramSuspension, ArchivosJustifica infoArchivo) throws Throwable;
    
    
    /**
     * Crea un nuevo registo en la tabla de <code>JustificaAdm</code>
     * @param justificaAdm <code>JustificaAdm</code> con los nuevos atributos del mismo
     * @param infoArchivo <code>ArchivosJustifica</code> con los nuevos atributos del mismo
     * @param justificaTipos <code>JustificaTipos</code> con los nuevos atributos del mismo     
     * @return void
     * @throws Throwable
     */
    public void crearJustificaAdm(JustificaAdm justificaAdm, ArchivosJustifica infoArchivo, JustificaTipos justificaTipos) throws Throwable;

    /**
     * Elimina un <code>Usuario</code> del LDAP
     *
     * @return boolean true si se pudo cambiar el password
     * @throws Throwable
     */
    public void eliminarUsuarioDelLDAP(Usuario usuario) throws Throwable;

    /**
     * Crea un <code>Usuario</code> en el LDAP
     *
     * @return boolean true si se pudo cambiar el password
     * @throws Throwable
     */
    public void crearUsuarioEnLDAP(Usuario usuario) throws Throwable;

    /**
     * M�todo utilizado para crear autom�ticamente en el LDAP los usuarios de
     * SIR que no est�n actualmente registrados en el Directorio.
     *
     * @throws Throwable
     */
    public void creacionAutomaticaDeUsuarios() throws Throwable;

    /**
     * Consulta la informaci�n de un <code>Usuario</code> almacenada en la base
     * de datos partiendo de un Usuario de Auriga
     *
     * @param usuario  <code>Usuario</code>
     * @return
     * @throws Throwable
     */
    public Usuario getUsuario(org.auriga.core.modelo.transferObjects.Usuario usuario) throws Throwable;

    /**
     * Consulta la informaci�n de un <code>Usuario</code> almacenada en la base
     * de datos partiendo de un Usuario de Auriga
     *
     * @param usuario  <code>Usuario</code>
     * @return
     * @throws Throwable
     */
    public Usuario getUsuarioById(gov.sir.core.negocio.modelo.UsuarioPk usuario)
            throws Throwable;

    /**
     * Devuelve la serie de recibos asociados a una estacion
     *
     * @param estacion
     * @return
     */
    public EstacionRecibo getRecibosEstacion(Estacion estacion)
            throws Throwable;

    /**
     * Carga en el objeto estaci�n, sus roles
     *
     * @param estacion
     * @throws Throwable
     */
    public void loadRolesEstacion(Estacion estacion) throws Throwable;

    /**
     * Devuelve la lista de roles potenciales para una estacion
     *
     * @param estacion
     * @return
     * @throws Throwable
     */
    public List getRolesPotencialesEstacion(Estacion estacion) throws Throwable;

    /**
     * Devuelve la lista de usuarios de una estacion
     *
     * @param estacion
     * @return
     * @throws Throwable
     */
    public List getUsuariosEstacion(Estacion estacion) throws Throwable;

    /**
     * Devuelve la lista de usuarios de los roles asociados a la estaci�n, pero
     * que no tienen acceso a la estaci�n
     *
     * @param estacion
     * @return
     * @throws Throwable
     */
    public List getUsuariosPotencialesEstacion(Estacion estacion) throws Throwable;

    /**
     * Agrega un rol a una estacion
     *
     * @param estacion
     * @param rol
     * @param usuario que adiciona el rol a la estacion
     * @throws Throwable
     */
    public void addRolEstacion(Estacion estacion, Rol rol, Usuario usuario) throws Throwable;

    /**
     * Quita un rol a una estacion
     *
     * @param estacion
     * @param rol
     * @throws Throwable
     */
    public void removeRolEstacion(Estacion estacion, Rol rol) throws Throwable;

    /**
     * Obtener una estaci�n en la que el usuario tenga permisos para trabajar en
     * el rol dado
     *
     * @param usuario
     * @param rol
     * @return
     * @throws Throwable
     */
    public Estacion getEstacion(Usuario usuario, Rol rol) throws Throwable;

    /**
     * Obtener una estaci�n en la que el usuario tenga permisos para trabajar en
     * el rol dado
     *
     * @param usuario
     * @param rol
     * @return
     * @throws Throwable
     */
    public List getEstaciones(Usuario usuario, Rol rol) throws Throwable;

    /**
     * Obtener las estaci�nes activas en la que el usuario tenga permisos para
     * trabajar en el rol dado
     *
     * @param usuario
     * @param rol
     * @param estado
     * @return
     * @throws Throwable
     */
    public List getEstacionesUsuarioByEstadoRol(Usuario usuario, Rol rol, boolean estado) throws Throwable;

    /**
     * Obtener una estaci�n en la que el usuario tenga permisos para trabajar en
     * el rol dado
     *
     * @param usuario
     * @param rol
     * @return
     * @throws Throwable
     */
    public Estacion getEstacionByEstado(Usuario usuario, Rol rol, boolean estado) throws Throwable;

    /**
     * Obtener un rol por el identificador
     *
     * @param idRol Identificador del rol
     * @return
     * @throws Throwable
     */
    public Rol getRolByID(String idRol) throws Throwable;

    /**
     * Se actualizan todos los datos del rol.
     *
     * @param rol, usuario que actualiza el rol
     * @throws Throwable
     */
    public void actualizarRol(Rol rol, Usuario usuario) throws Throwable;

    /**
     * Se obtiene el usuario de Auriga a partid del nombre de usuario
     *
     * @param username
     * @return
     * @throws Throwable
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioByUsername(String username) throws Throwable;

    /**
     * Se obtiene la lista de estaciones de un rol
     *
     * @param rol
     * @return
     * @throws Throwable
     */
    public List getEstacionesRol(Rol rol) throws Throwable;

    /**
     * Se obtiene la lista de estaciones de un rol
     *
     * @param rol
     * @return
     * @throws Throwable
     */
    public List getEstacionesRolByCirculo(Rol rol, Circulo circulo) throws Throwable;

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
    public List obtenerEstacionesUsuarioByEstadoRol(Rol rol, Circulo circulo, boolean activo) throws Throwable;

    /**
     * Se obtiene la lista de estaciones de un circulo
     *
     * @param circulo
     * @return
     * @throws Throwable
     */
    public List getEstacionesCirculo(Circulo circulo) throws Throwable;

    /**
     * Agrega una estacion a un rol de un usuario espec�fico
     *
     * @param estacion
     * @param rol
     * @param usuario
     * @param usuario que adiciona la estacion a un rol
     * @throws Throwable
     */
    public void agregarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws Throwable;

    /**
     * Elimina una estacion de un rol de un usuario espec�fico
     *
     * @param estacion
     * @param rol
     * @param usuario
     * @param circulo TODO
     * @param List turnos de una estacion de un rol especifico
     * @throws Throwable
     */
    public void eliminarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Circulo circulo, List turnos) throws Throwable;

    /**
     * Agrega un rol a un usuario espec�fico
     *
     * @param rol
     * @param usuario
     * @param usuario que agrega el rol al usuario
     * @throws Throwable
     */
    public void agregarRolUsuario(Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws Throwable;

    /**
     * Agrega una estacion a un rol
     *
     * @param estacion
     * @param rol
     * @param Usuario que agrega una estacion al rol
     * @throws Throwable
     */
    public void agregarEstacionRol(Estacion estacion, Rol rol, Usuario usuario) throws Throwable;

    /**
     * Elimina una estacion de un rol
     *
     * @param estacion
     * @param rol
     * @param circulo TODO
     * @throws Throwable
     */
    public void eliminarEstacionRol(Estacion estacion, Rol rol, Circulo circulo, List turnos) throws Throwable;

    /**
     * Consulta los usuarios que tienen una estacion y un rol especificos
     *
     * @param rol
     * @param estacion
     * @return
     * @throws Throwable
     */
    public List consultarUsuariosRolEstacion(Rol rol, Estacion estacion) throws Throwable;
    

    /**
     * Se obtiene el usuario de Auriga a partid del nombre de usuario
     *
     * @param username
     * @return
     * @throws Throwable
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioNRelationsByUsername(String username) throws Throwable;

    /**
     * Desactiva una estacion de un rol de un c�rculo espec�fico
     *
     * @param estacion
     * @param rol
     * @param usuario
     * @param circulo
     * @param estado
     * @param Usuario que modifica el estado de la estacion
     * @throws Throwable
     */
    public void actualizarEstadoEstacionRol(Estacion estacion, Rol rol, Circulo circulo, boolean estado, Usuario usuario) throws Throwable;

    /**
     * Traslada un usuario a un c�rculo espec�fico. Este m�todo s�lo cambia la
     * estaci�n privada del usuario (la que corresponde a su nombre de usuario),
     * no traslada las dem�s estaciones a las que pertenezca
     *
     * @param usuario Usuario a trasladar
     * @param circulo Circulo al que va a pertenecer el usuario
     * @throws Throwable
     */
    public void trasladarUsuarioCirculo(Usuario usuario, Circulo circulo) throws Throwable;
    
    /**
     *
     * @param idUsuario
     * @return
     * @throws Throwable
     */
    public List consultarRespuestasUsuarios (int idUsuario, String turno) throws Throwable;
    
    /**
     *
     * @param idUsuario
     * @return
     * @throws Throwable
     */
    public void eliminarTramiteSuspensionTurno (int idUsuario, String turno) throws Throwable;

    /**
     * Author: Santiago V�squez Change: 1156.111.USUARIOS.ROLES.INACTIVOS Este
     * metodo consulta los roles de usuario por circulos, usado cuando se quiera
     * eliminar un rol de un circulo
     *
     * @param circulo
     * @param roles
     * @return
     * @throws Throwable
     */
    public List consultarUsuariosPorCirculoRoles(Circulo circulo, List roles) throws Throwable;
    
    /**
     * Consulta la lista de campos de captura dado una forma de pago
     *
     * @param tipoPago  <code>TipoPago</code> a ser consultado
     * @return lista de campos de captura que cumplan la condici�n
     * @throws Throwable
     */
    public List consultarCamposCaptura(TipoPago tipoPago) throws Throwable;
    
    /**
     * Actualiza la relacion campo captura y forma pago en la tabla
     * SIR_OP_REL_FPAGO_CAMPOS
     *
     * @param formaPagoCampos
     * @return boolean si pudo actualizar la relaci�n
     * @throws Throwable
     */
    public boolean actualizarCamposCaptura(FormaPagoCampos formaPagoCampos) throws Throwable;
    
    /**
     * Consulta la lista de campos de captura del sistema
     *
     * @return lista de campos de captura del sistema
     * @throws Throwable
     */
    public List cargarCamposCaptura() throws Throwable;
    
    /**
     * Crea una nueva forma pago en la tabla
     * SIR_OP_TIPO_DOC_PAGO
     *
     * @param nombreFormaPago
     * @return objeto TipoPago
     * @throws Throwable
     */
    public TipoPago crearFormaPago(String nombreFormaPago) throws Throwable;

}
