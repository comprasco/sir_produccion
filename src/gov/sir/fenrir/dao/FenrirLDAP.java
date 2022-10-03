package gov.sir.fenrir.dao;

import java.util.List;

import gov.sir.core.negocio.modelo.Usuario;

import gov.sir.fenrir.FenrirException;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;

/**
 * Esta interfaz define los m�todos que deben implementar las clases encargadas 
 * de realizar las actividades relacionadas con el m�dulo de seguridad y operaci�n en el
 * LDAP para Fenrir.
 * @author jfrias, jmendez
 */
public interface FenrirLDAP {

	/**
	 * Se efect�a la autenticaci�n del usuario en el directorio
	 * a trav�s del protocolo LDAP
	 * @param user el identificador el usuario que se est� autenticando
	 * @param password la clave del usuario que se est� autenticando
	 * @return boolean <code>true</code> si pudo validar al usuario
	 * @throws FenrirException cuando hay un error con el proceso de
	 * autenticaci�n o la autenticaci�n es fallida.
	 */
	public boolean validarUsuario(String user, String password)
		throws
			ConfiguracionPropiedadesException,
			AuthenticationException,
			OperationNotSupportedException,
			NamingException;

	/**
	 * Se efect�a la creaci�n del usuario en el directorio
	 * 
	 * @param usuario <code>Usuario</code> se ser� creado
	 * @return boolean <code>true</code> si pudo crear el usuario el el ldap
	 * @throws FenrirException cuando hay un error con el proceso de
	 * autenticaci�n o la autenticaci�n es fallida.
	 */
	public boolean crearUsuario(Usuario usuario)
		throws
			ConfiguracionPropiedadesException,
			AuthenticationException,
			OperationNotSupportedException,
			NamingException;

	/**
	 * Determina si un <code>Usuario</code> tiene privilegios para acceder a las 
	 * pantallas administrativas  del sistema. 
	 * 
	 * @param roles  <code>List</code> con los roles autorizados para el usuario
	 * @return boolean true si tiene permisos administrativos o de lo contrario false.
	 * @throws DAOException
	 */
	public boolean tieneRolAdministrativo(List roles) throws DAOException;

	/**
	 * Permite Cambiar el Password de un <code>Usuario</code> 
	 * 
	 * @param roles  <code>Usuario</code> con la nueva clave
	 * @return boolean true si se pudo cambiar el password
	 * @throws DAOException
	 */
	public boolean cambiarPassword(Usuario usuario) throws DAOException;

	/**
	 * Habilita o deshabilita un  <code>Usuario</code> en el LDAP seg�n si su propiedad 
	 * activo es true o false. 
	 * 
	 * @param roles  <code>Usuario</code> con la nueva clave
	 * @return boolean true si se pudo cambiar el password
	 * @throws DAOException
	 */
	public void habilitarUsuario(Usuario usuario) throws DAOException;
	
	/**
	 * Actualiza la informaci�n de un <code>Usuario</code> en el LDAP. 
	 * @param usuario <code>Usuario</code> con la la informaci�n a modificar
	 * @return void
	 * @throws DAOException
	 */
	public void actualizarUsuario(Usuario usuario) throws DAOException;	

	/**
	 * Elimina un <code>Usuario</code> del LDAP 
	 * 
	 * @param roles  <code>Usuario</code> con la nueva clave
	 * @return boolean true si se pudo cambiar el password
	 * @throws DAOException
	 */
	public void eliminarUsuario(Usuario usuario) throws DAOException;

	
	/**
	 * M�todo utilizado para crear autom�ticamente en el LDAP los usuarios de SIR que no est�n
	 * actualmente registrados en el Directorio.
	 * 
	 * @throws DAOException
	 * @throws AuthenticationException
	 * @throws NamingException
	 */
	public void creacionAutomaticaDeUsuarios()
		throws DAOException, AuthenticationException, NamingException;

}
