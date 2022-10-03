package gov.sir.fenrir.dao.impl.oracle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.auriga.core.modelo.transferObjects.Rol;

import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.FenrirProperties;
import gov.sir.fenrir.dao.ConfiguracionPropiedadesException;
import gov.sir.fenrir.dao.DAOException;
import gov.sir.fenrir.dao.FenrirLDAP;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.fenrir.dao.impl.jdogenie.AdministradorPM;

/**
 * Implementación de los servicios de LDAP en Fenrir
 * 
 * @author jmendez
 */
public class OracleFenrirLDAP implements FenrirLDAP {

	/**
	 * The relative distinguished name of user entries
	 */
	public static final String RDN = "cn";
	
	/**
	 * The firstname. Also called Christian name
	 */
	public static final String givenName = "givenName";

	private String factoryClass = "";
	private String url = "";
	private String authenticationType = "";
	private String protocol = "";
	private String ldapUser = "";
	private String ldapPassword = "";
	private String emailDomain = "";

	/** Password default para los usuarios creados automáticamente en el LDAP  */
	private String ldapDefaultPassword = "";

	/**
	 * User context under Identity Realm, this is where all user entries	  are present
	 */
	private String distinguishedName = "";
	boolean autenticacion = false;
	private List listaRolesAdmin;

	/**
	 * Constructor encargado de tomar los parámetros de configuración del LDAP provenientes
	 * del archivo .properties correspondiente.
	 * También se encarga de validar la existencia de todos los parámetros requeridos
	 * para la conexión con el LDAP.  (En el caso de fallar arroja una excepción 
	 * <code>ConfiguracionPropiedadesException</code>
	 * @throws ConfiguracionPropiedadesException
	 */
	public OracleFenrirLDAP() throws ConfiguracionPropiedadesException {
		FenrirProperties p = FenrirProperties.getInstancia();

		String authStr = p.getProperty(FenrirProperties.AUTENTICACION_LDAP);
		if (authStr == null || authStr.trim().equals("")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, "Falta parámetro de configuración " + FenrirProperties.AUTENTICACION_LDAP);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}
		autenticacion = Boolean.valueOf(authStr).booleanValue();

		factoryClass = p.getProperty(OracleKeys.LDAP_FACTORY);
		if (factoryClass == null || factoryClass.trim().equals("")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, "Falta parámetro de configuración " + OracleKeys.LDAP_FACTORY);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		ldapUser = p.getProperty(OracleKeys.LDAP_USER);
		if (ldapUser == null || ldapUser.trim().equals("")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, "Falta parámetro de configuración " + OracleKeys.LDAP_USER);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		ldapPassword = p.getProperty(OracleKeys.LDAP_PASSWORD);
		if (ldapPassword == null || ldapPassword.trim().equals("")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, "Falta parámetro de configuración " + OracleKeys.LDAP_PASSWORD);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		emailDomain = p.getProperty(OracleKeys.LDAP_EMAIL_DOMAIN);
		if (emailDomain == null || emailDomain.trim().equals("")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, "Falta parámetro de configuración " + OracleKeys.LDAP_EMAIL_DOMAIN);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		if (!emailDomain.startsWith("@")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, 
				"El valor del parámetro de configuración "
					+ OracleKeys.LDAP_EMAIL_DOMAIN
					+ " debe iniciar con @ ");
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		ldapDefaultPassword = p.getProperty(OracleKeys.LDAP_DEFAULT_USER_PASSWORD);
		if (ldapDefaultPassword == null || ldapDefaultPassword.trim().equals("")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, 
				"Falta parámetro de configuración " + OracleKeys.LDAP_DEFAULT_USER_PASSWORD);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		url = p.getProperty(OracleKeys.LDAP_URL);
		if (url == null || url.trim().equals("")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, "Falta parámetro de configuración " + OracleKeys.LDAP_URL);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		authenticationType = p.getProperty(OracleKeys.LDAP_AUTHENTICATION);
		if (authenticationType == null || authenticationType.trim().equals("")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, "Falta parámetro de configuración " + OracleKeys.LDAP_AUTHENTICATION);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		protocol = p.getProperty(OracleKeys.LDAP_PROTOCOL);
		/*
		if (protocol == null || protocol.trim().equals("")) {
			logEstatico.fatal("Falta parámetro de configuración " + OracleKeys.LDAP_PROTOCOL);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}
		*/

		distinguishedName = p.getProperty(OracleKeys.LDAP_DISTINGUISHED_NAME);
		if (distinguishedName == null || distinguishedName.trim().equals("")) {
			Log.getInstance().fatal(OracleFenrirLDAP.class, "Falta parámetro de configuración " + OracleKeys.LDAP_DISTINGUISHED_NAME);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		/*String rolesAdmin = p.getProperty(FenrirProperties.ADMIN_ID_ROL);
		if (rolesAdmin == null || rolesAdmin.trim().equals("")) {
			logEstatico.fatal("Falta parámetro de configuración " + FenrirProperties.ADMIN_ID_ROL);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}

		listaRolesAdmin = new ArrayList();
		StringTokenizer st = new StringTokenizer(rolesAdmin, ",");
		while (st.hasMoreTokens()) {
			listaRolesAdmin.add(st.nextToken());
		}

		if (listaRolesAdmin.isEmpty()) {
			logEstatico.fatal(
				"Faltan valores para el parámetro de configuración " + FenrirProperties.ADMIN_ID_ROL);
			throw new ConfiguracionPropiedadesException(
				ConfiguracionPropiedadesException.CONFIGURACION_INVALIDA);
		}*/
	}

	/**
	* Authenticates the user credentials with Directory.
	*  
	* @param username  User Name of the user
	* @param passwd Password of the user
	* @return  true - if the credentials are valid
	* 
	* @exception AuthenticationException If credentials are invalid
	* @exception NamingException if any directory operation fails
	*/
	public boolean validarUsuario(String username, String passwd)
		throws AuthenticationException, NamingException {

		if (autenticacion) {
			//Si no puede validar al usuario retorna una excepción
			try {
				validarUsuarioAndGetContext(username, passwd);
			} catch (javax.naming.OperationNotSupportedException e) {
				throw new AuthenticationException("La Cuenta del Usuario se encuentra deshabilitada. Por favor contacte al administrador del sistema.");
			}
		}
		return true;
	}

	/**
	 * Autentica las credenciales del usuario en el directorio además de retornar una
	 * referencia al <code>DirContext</code> creado para operaciones posteriores
	 * @param username
	 * @param passwd
	 * @return referencia al contexto creado para el usuario
	 * @throws AuthenticationException
	 * @throws NamingException
	 */
	public DirContext validarUsuarioAndGetContext(String username, String passwd)
		throws AuthenticationException, NamingException {

		DirContext dCtx = null;

		try {
			// 	Get the Distinguished Name 
			String dn = getUserDN(username);
			// Authenticate with Directory
			dCtx = getDirectoryContext(dn, passwd);
			Log.getInstance().info(OracleFenrirLDAP.class, "Autenticación exitosa del usuario " + username + " en el LDAP");
		} catch (AuthenticationException e) {
			e.printStackTrace(System.out);
			throw new AuthenticationException("Clave Incorrecta ");
		}
		return dCtx;
	}

	/**
	* Se efectúa la creación del usuario en el directorio
	 * @param usuario <code>Usuario</code> se será creado
	 * @return boolean <code>true</code> si pudo crear el usuario el el ldap
	 * @throws FenrirException cuando hay un error con el proceso de
	 * autenticación o la autenticación es fallida.
	 */
	public boolean crearUsuario(Usuario usuario)
		throws
			ConfiguracionPropiedadesException,
			AuthenticationException,
			OperationNotSupportedException,
			NamingException{

		boolean creacionUsuarioOK = false;
		
		try {
			DirContext dCtx = validarUsuarioAndGetContext(ldapUser, ldapPassword);
			
			String temp = getUserDN(usuario.getUsername());
			
			if(temp!=null){
				throw new NamingException("El usuario en LDAP ya existe.");
			}else{				
				temp = getUserDN(usuario.getUsername().toLowerCase());
				if(temp!=null){
					throw new NamingException("El usuario en LDAP ya existe.");
				}
			}

			
			String uid = usuario.getUsername();
			String uname = usuario.getNombre();
			String sname = usuario.getApellido1() + ((usuario.getApellido2() == null) ? "" : " " + usuario.getApellido2());
			String password = usuario.getPassword();

			Map attrs = new HashMap();
			List objclass = new ArrayList();

			// Object classes that the user must use
			objclass.add("top");
			objclass.add("person");
			objclass.add("orcluser");
			objclass.add("orcluserv2");
			objclass.add("inetOrgPerson");
			objclass.add("organizationalperson");

			// create other attributes and their values
			attrs.put(givenName, uname);
			attrs.put(RDN, uid);
			attrs.put("uid", uid);
			attrs.put("sn", sname);
			attrs.put("mail", uid + emailDomain);
			//	attrs.put("postaladdress", emp.getAddress());
			//	attrs.put("mail", emp.getEmail());
			//	attrs.put("hireDate", dateFormatter.format(emp.getHireDate()));
			attrs.put("userpassword", password);
			attrs.put("orclisenabled", "ENABLED");
			//	attrs.put("orclactivestartdate", new java.util.Date());

			//	create the Directory Entry with the specified attributes
			addDirectoryEntry(RDN + "=" + uid + "," + distinguishedName, objclass, attrs, dCtx);
			validarUsuario(uid, password);
			creacionUsuarioOK = true;
		} catch (NameAlreadyBoundException e) {
			throw new NamingException("El nombre de usuario ya existe");

		}
		return creacionUsuarioOK;
	}

	//////////////////////////////////////////////////////////////////
	/**
	   *  Initializes a Directory Context with the specified credentials and return it.
	   *  If the password is blank(null), it binds as anonymous user and returns the
	   *  context.
	   *  
	   * @param username Directory user name
	   * @param password Directory user password
	   * @return  valid directory context, if credentials are valid
	   * @exception AuthenticationException  if credentails are invalid
	   * @exception NamingException if directory operation fails
	   */
	private DirContext getDirectoryContext(String username, String password)
		throws AuthenticationException, NamingException {

		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, factoryClass);
		env.put(Context.PROVIDER_URL, url);

		// if password is specified, set the credentials
		if (password != null) {
			env.put(Context.SECURITY_AUTHENTICATION, authenticationType);
			env.put(Context.SECURITY_PRINCIPAL, username);
			env.put(Context.SECURITY_CREDENTIALS, password);
		}

		// Bind and initialize the Directory context
		DirContext dCtx = new InitialDirContext(env);
		return dCtx;
	}

	/**
	  *  Delete the specified entry from Directory.
	  *  
	  * @param dn The distinguished name of the entry to be removed
	  * @param dCtx DirContext
	  * @exception NamingException if entry not found
	  */
	private void deleteDirectoryEntry(String dn, DirContext dCtx) throws NamingException {
		// delete the entry with this DN
		dCtx.destroySubcontext(dn);
	}

	/** 
	  * Replaces the specified attributes of an entry.
	  * 
	  * @param dn The distinguished name of the entry whose attributes have to be modified
	  * @param map Attribute,Value pair to be replaced
	  * @param dCtx DirContext
	  * @exception NamingException if directory operation fails, or attribute is not present
	  */
	private void modifyDirectoryEntry(String dn, Map map, DirContext dCtx) throws NamingException {

		// Get the attribute,newvalue mapping
		Iterator attrsIter = map.entrySet().iterator();
		// Construct the attributes list
		Attributes attrs = new BasicAttributes(true); // case-ignore

		while (attrsIter.hasNext()) {
			Map.Entry attr = (Map.Entry) attrsIter.next();
			attrs.put(new BasicAttribute((String) attr.getKey(), attr.getValue()));
		}
		// Replace the existing attribute values with the specified new values
		dCtx.modifyAttributes(dn, DirContext.REPLACE_ATTRIBUTE, attrs);
	}

	/**
	 * Obtiene una referencia al <code>DirContext</code> registrándose como un usuario
	 * anónimo
	 * @return DirContext
	 * @throws AuthenticationException
	 * @throws NamingException
	 */
	private DirContext getDirectoryContext() throws AuthenticationException, NamingException {
		return getDirectoryContext(distinguishedName, null);
	}

	/**
	   * Retrieves the Distinguished name of them of the specified RDN.
	   * 
	   * @param uname  Relative Distinguished name.
	   * @return  Distinguished name of the user
	   * @exception NamingException if directory operation fails 
	   */
	public String getUserDN(String uname) throws NamingException {

		DirContext dCtx = getDirectoryContext();

		SearchResult searchResult = null;
		NamingEnumeration results = null;
		String userDN = null;

		String filter = "(" + RDN + " =" + uname + ")";

		// To set search controls to search with subtree scope
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		// Search the directory based on the search string from the specified context
		results = dCtx.search(distinguishedName, filter, searchControls);

		// If matching record found 
		if (results.hasMore()) {
			searchResult = (SearchResult) results.next();
			// Build the User DN
			userDN = searchResult.getName() + "," + distinguishedName;

		} else {
			// User not found
			throw new NamingException("Nombre de Usuario Inválido ");
		}
		return userDN;
	}

	/**
	 * Creates an entry in Directory with the specified attributes and objectclass,  
	 * with the specified Distingushed Name.
	 * 
	 * @param dn Distinguished name of the entry to be created
	 * @param objCls Object classes that the entry must use
	 * @param map Attribute,value mappings of the entry
	 * @exception NamingException if adding entry fails
	 * @exception NameAlreadyBoundException if user already exists
	 */
	public void addDirectoryEntry(String dn, List objCls, Map map, DirContext dCtx)
		throws NamingException, NameAlreadyBoundException {

		// Create attribute list, ignore case of attribute names
		Attributes attrs = new BasicAttributes(true);

		if (!objCls.isEmpty()) {
			Attribute objclass = new BasicAttribute("objectclass");
			// Iterate thriough the collection and add the object classes to the attribute
			Iterator objclsIter = objCls.iterator();
			while (objclsIter.hasNext()) {
				// Add the object classes        
				objclass.add(objclsIter.next());
			}
			// Add the object class attribute to list
			attrs.put(objclass);
		}

		// Iterate through other attributes and add to attributes list
		Iterator attrsIter = map.entrySet().iterator();

		while (attrsIter.hasNext()) {
			Map.Entry attr = (Map.Entry) attrsIter.next();
			attrs.put(new BasicAttribute((String) attr.getKey(), attr.getValue()));
		}

		// add the directory entry to the directory with the attributes
		dCtx.createSubcontext(dn, attrs);
	}

	/**
	   * Searchs the directory under the specified context with specified filter 
	   * and returns the search results.
	   * The scope is set to one-level.
	   * 
	   * @param ctxname Context under which seach has to be done.
	   * @param filter Search filter
	   * @return  Attributes matching the search specification
	   * @exception NamingException if directory search fails
	   */
	public Collection search(String ctxname, String filter, DirContext dCtx) throws NamingException {

		SearchResult searchResult = null;
		NamingEnumeration results = null;
		Collection retColl = new ArrayList();
		Attributes attrs = null;

		// Initialize search search controls with one-level scope
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

		// Search the directory based on the search string under the specified context
		results = dCtx.search(ctxname, filter, searchControls);

		// Check if any matching results were found in Directory
		if (results.hasMore()) {

			// Iterate through the results and populate the return collection
			do {
				searchResult = (SearchResult) results.next();
				// Get the attributes 
				attrs = searchResult.getAttributes();

				retColl.add(attrs);

			} while (results.hasMore());
		}
		return retColl;
	}

	/**
	 * Determina si un <code>Usuario</code> tiene privilegios para acceder a las pantallas 
	 * administrativas comparando los roles que tiene asignado con los roles autorizados 
	 * en el sistema para la visualización de pantallas administrativas. 
	 * 
	 * @param roles  <code>List</code> con los roles autorizados para el usuario
	 * @return boolean true si tiene permisos administrativos o de lo contrario false.
	 * @throws DAOException
	 */
	public boolean tieneRolAdministrativo(List roles) throws DAOException {
		//Esta funcionalidad potencialmente podría realizarse a través de un LDAP
		boolean respuesta = false;
		for (Iterator iter = roles.iterator(); iter.hasNext();) {
			Rol rol = (Rol) iter.next();
			String idRol = rol.getRolId();

			if (listaRolesAdmin.contains(idRol)) {
				respuesta = true;
				break;
			}
		}
		return respuesta;
	}

	/**
	 * Permite Cambiar el Password de un <code>Usuario</code> 
	 * 
	 * @param roles  <code>Usuario</code> con la nueva clave
	 * @return boolean true si se pudo cambiar el password
	 * @throws DAOException
	 */
	public boolean cambiarPassword(Usuario usuario) throws DAOException {
		try {
			DirContext dCtx = validarUsuarioAndGetContext(ldapUser, ldapPassword);
			String nombreUsuario = usuario.getUsername();
			String nuevoPassword = usuario.getPassword();
			// 	Get the Distinguished Name 
			String dn = getUserDN(nombreUsuario);

			Map attrs = new HashMap();
			attrs.put("userpassword", nuevoPassword);
			modifyDirectoryEntry(dn, attrs, dCtx);

			// Authenticate con la nueva clave en el directorio
			getDirectoryContext(dn, nuevoPassword);
			Log.getInstance().info(OracleFenrirLDAP.class, 
				"Cambio de clave exitoso para el usuario " + usuario.getUsername() + " en el LDAP");
		} catch (NamingException e) {
			e.printStackTrace(System.out);
			throw new DAOException("No se pudo Cambiar la Clave, el usuario se encuentra bloqueado.", e);
		}
		return true;
	}

	/**
	 * Habilita o deshabilita un  <code>Usuario</code> en el LDAP según si su propiedad 
	 * activo es true o false. 
	 * 
	 * @param roles  <code>Usuario</code> con la nueva clave
	 * @return boolean true si se pudo cambiar el password
	 * @throws DAOException
	 */
	public void habilitarUsuario(Usuario usuario) throws DAOException {
		try {
			DirContext dCtx = validarUsuarioAndGetContext(ldapUser, ldapPassword);
			String nombreUsuario = usuario.getUsername();
			String password = usuario.getPassword();
			// 	Get the Distinguished Name 
			String dn = getUserDN(nombreUsuario);

			Map attrs = new HashMap();
			if (usuario.isActivo()) {
				attrs.put("orclisenabled", "ENABLED");
			} else {
				attrs.put("orclisenabled", "DISABLED");
			}

			modifyDirectoryEntry(dn, attrs, dCtx);
			// Authenticate con la nueva clave en el directorio
			getDirectoryContext(dn, password);
			Log.getInstance().info(OracleFenrirLDAP.class, 
				"Cambio de ESTADO exitoso para el usuario " + usuario.getUsername() + " en el LDAP");
		} catch (NamingException e) {
			e.printStackTrace(System.out);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	/**
	 * Actualiza la información  <code>Usuario</code> en el LDAP
	 * @param usuario <code>Usuario</code> con la información a actualizar
	 * @return void
	 * @throws DAOException
	 */
	public void actualizarUsuario(Usuario usuario) throws DAOException {
		try {
			DirContext dCtx = validarUsuarioAndGetContext(ldapUser, ldapPassword);
			String nombreUsuario = usuario.getUsername();
			String password = usuario.getPassword();
			// 	Get the Distinguished Name 
			String dn = getUserDN(nombreUsuario);

			Map attrs = new HashMap();
			attrs.put("givenName", usuario.getNombre());
			attrs.put("sn", usuario.getApellido1());

			modifyDirectoryEntry(dn, attrs, dCtx);
			// Authenticate con la nueva clave en el directorio
			getDirectoryContext(dn, password);
			Log.getInstance().info(OracleFenrirLDAP.class, "Actualización de usuario " + usuario.getUsername() + " en el LDAP");
		} catch (NamingException e) {
			e.printStackTrace(System.out);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	

	/**
	 * Elimina un <code>Usuario</code> del LDAP 
	 * 
	 * @param roles  <code>Usuario</code> con la nueva clave
	 * @return boolean true si se pudo cambiar el password
	 * @throws DAOException
	 */
	public void eliminarUsuario(Usuario usuario) throws DAOException {
		try {
			DirContext dCtx = validarUsuarioAndGetContext(ldapUser, ldapPassword);
			String nombreUsuario = usuario.getUsername();
			String password = usuario.getPassword();
			// 	Get the Distinguished Name 
			String dn = getUserDN(nombreUsuario);

			deleteDirectoryEntry(dn, dCtx);
			Log.getInstance().info(OracleFenrirLDAP.class, 
				"Se eliminó satisfactoriamente al usuario  " + usuario.getUsername() + " del LDAP");
		} catch (NamingException e) {
			e.printStackTrace(System.out);
			throw new DAOException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @throws DAOException
	 */
	public void creacionAutomaticaDeUsuarios()
		throws DAOException, AuthenticationException, NamingException {
		List usuariosSIR = getUsuarios();

		DirContext dCtx = validarUsuarioAndGetContext(ldapUser, ldapPassword);

		for (Iterator iter = usuariosSIR.iterator(); iter.hasNext();) {
			Usuario usuario = (Usuario) iter.next();
			String filter = "(cn=" + usuario.getUsername() + ")";

			//Busca si el usuario ya existe en el sistema
			Collection resultados = search(distinguishedName, filter, dCtx);

			//Únicamente crea el usuario si no existe actualmente en el LDAP
			if (resultados.size() == 0) {
				try {
					usuario.setPassword(ldapDefaultPassword);
					crearUsuario(usuario);
				} catch (AuthenticationException e) {
					Log.getInstance().error(OracleFenrirLDAP.class, e.getMessage(), e);
				} catch (OperationNotSupportedException e) {
					Log.getInstance().error(OracleFenrirLDAP.class, e.getMessage(), e);
				} catch (ConfiguracionPropiedadesException e) {
					Log.getInstance().error(OracleFenrirLDAP.class, e.getMessage(), e);
				} catch (NamingException e) {
					Log.getInstance().error(OracleFenrirLDAP.class, e.getMessage(), e);
				}
			}
		}
	}

	/**
	* Obtiene una lista con los usuarios existentes en SIR
	* @return Lista con los usuarios que actualmente existen en sir 
	* @throws DAOException.
	*/
	private List getUsuarios() throws DAOException {
		List lista = new ArrayList();

		try {
			PersistenceManager pm = AdministradorPM.getPM();

			Query q = pm.newQuery(UsuarioEnhanced.class);
			//Se establece como criterio de ordenamiento el nombre 
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			while (it.hasNext()) {
				UsuarioEnhanced obj = (UsuarioEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			q.close(results);
			pm.close();
		} catch (Exception e) {
			Log.getInstance().error(OracleFenrirLDAP.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}

}
