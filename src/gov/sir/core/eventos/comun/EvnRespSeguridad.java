package gov.sir.core.eventos.comun;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Usuario;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;



/**
 * Obtiene información sobre autenticación y autorización, de la capa de negocio.
 * @author dcantor
 * @author dsalas
 */
public class EvnRespSeguridad extends EvnSIRRespuesta {
    
    /**
     * Indica que este evento de respuesta esta devolviendo una lista de estaciones.
     * Es necesario entonces hacer el cast correspondiente cuando se llame al metodo
     * <CODE>getLista()</CODE>
     * @see gov.sir.core.negocio.modelo.Estacion
     */    
    public static final String LISTA_ESTACIONES="LISTA_ESTACIONES";
    
	/**
		 * Indica que este evento de respuesta esta devolviendo una lista de estaciones.
		 * Es necesario entonces hacer el cast correspondiente cuando se llame al metodo
		 * <CODE>getLista()</CODE>
		 * @see gov.sir.core.negocio.modelo.Estacion
		 */    
	public static final String LISTA_ROLES="LISTA_ROLES";
    
    /**
     * Indica que este evento de respuesta esta devolviendo una lista de procesos.
     * Es necesario entonces hacer el cast correspondiente cuando se llame al metodo
     * <CODE>getLista()</CODE>
     * @see gov.sir.core.negocio.modelo.Proceso
     */    
    public static final String LISTA_PROCESOS="LISTA_PROCESOS";
    
    /**
     * Indica que este evento de respuesta esta devolviendo una lista de Fases.
     * Es necesario entonces hacer el cast correspondiente cuando se llame al metodo
     * <CODE>getLista()</CODE>
     * @see gov.sir.core.negocio.modelo.Fase
     */    
    public static final String LISTA_FASES = "LISTA_FASES";
    
	/**
	 * Indica que este evento de respuesta se produjo porque el usuario estaba 
	 * saliendo del sistema a traves de un logout
	 */    
	public static final String LOGOUT = "LOGOUT";
	
	/**
	 * Llave identifica el objeto Integer que contiene 
	 */
	public static final String NUMERO_MAXIMO_IMPRESIONES = "NUMERO_MAXIMO_IMPRESIONES";
	
	public static final String INICIAR_SESION_COMO_ADMINISTRADOR = "INICIAR_SESION_COMO_ADMINISTRADOR";
	
	
	/**
	* Lista para el manejo del tipo de pantallas administrativas utilizadas en la aplicación.
	*/
	public static final String LISTA_TIPOS_PANTALLAS = "LISTA_TIPOS_PANTALLAS";
	

	/**
	* Lista para el manejo de las pantallas administrativas visibles para el usuario en particular.
	*/
	public static final String LISTA_PANTALLAS_USUARIO = "LISTA_PANTALLAS_USUARIO";	
  
    
	/**
	 * Indica que este evento de respuesta esta devolviendo un identificador unico para el login.
	 * Es necesario entonces hacer el cast correspondiente cuando se llame al metodo
	 */    
    private String loginID ;
    
    
    private Usuario usuario;
    private org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga;
    private Rol rol;
    private Estacion estacion;
    private Proceso proceso;
    private List listaInicia;
    private Circulo circulo;
    private Hashtable tabla;
	private Hashtable tablaListas;
    private Map impresoras;
    private List tiposPantalla;
    private List pantallasVisibles;
    private List listaRoles;
    
    private boolean administradorImpresionActivo;
	
	private List rolesAlertaAdmImpesionInactivo;
	

    
	   /**
     * Crea un evento de respuesta del tipo LISTA_ESTACIONES.
     * Este evento se genera como respuesta a un evento de seguridad del tipo LOGIN.
     * @param usuario el usuario autenticado
     * @param estaciones una lista de objetos <CODE>Estacion</CODE> asociados al usuario autenticado
     * @see gov.sir.core.negocio.modelo.Estacion
     */
    public EvnRespSeguridad(Usuario usuario, org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga, List roles){
        super(roles,EvnRespSeguridad.LISTA_ROLES);
        this.usuario = usuario;
        this.usuarioAuriga=usuarioAuriga;
    }
    
	/**
	* Crea un evento de respuesta del tipo LOGOUT
	* Este evento se genera como respuesta a un evento de seguridad del tipo LOGOUT
	* @param usuario el usuario autenticado
	*/
	public EvnRespSeguridad(Usuario usuario){
			super(usuario,EvnRespSeguridad.LOGOUT);
			this.usuario = usuario;
	}
    
    /**
     * Crea un evento de respuesta del tipo LISTA_PROCESOS. Este evento
     * se genera como respuesta a un evento de seguridad del tipo CONSULTAR_PROCESOS.
     * @param estacion la estacion asociada con la lista de procesos
     * @param procesos la lista de procesos
     */
    public EvnRespSeguridad(Rol rol, List estaciones) {
        super(estaciones, EvnRespSeguridad.LISTA_ESTACIONES);
        this.rol = rol;
    }
    
    
    
	/**
	 * Crea un evento de respuesta del tipo LISTA_PROCESOS. Este evento
	 * se genera como respuesta a un evento de seguridad del tipo CONSULTAR_PROCESOS.
	 * @param estacion la estacion asociada con la lista de procesos
	 * @param procesos la lista de procesos
	 */
	public EvnRespSeguridad(Rol rol, Estacion estacion, List procesos, List procInicia, Circulo circulo, Map impresoras) {
		super(procesos, EvnRespSeguridad.LISTA_PROCESOS);
		this.rol = rol;
		this.estacion = estacion;
		this.listaInicia = procInicia;
		this.circulo=circulo;
		this.impresoras = impresoras;
	}
    
    /**
     * Crea un evento de respuesta del tipo LISTA_FASES. Este evento se genera
     * com o respuesta a un evento de seguridad del tipo CONSULTAR_FASES.
     * @param proceso el proceso consultado para una estacion
     * @param fases las fases asociadas al proceso y a la estacion
     */
     public EvnRespSeguridad(Proceso proceso, List fases, Hashtable tabla){
         super(fases, EvnRespSeguridad.LISTA_FASES);
         this.proceso = proceso;
         this.tabla = tabla;
     }
     
	/**
	 * Crea un evento de respuesta del tipo LISTA_ESTACIONES.
	 * Este evento se genera como respuesta a un evento de seguridad del tipo LOGIN.
	 * @param usuario el usuario autenticado
	 * @param estaciones una lista de objetos <CODE>Estacion</CODE> asociados al usuario autenticado
	 * @see gov.sir.core.negocio.modelo.Estacion
	 */
	public EvnRespSeguridad(String tipoEvento, List tiposPantalla, List pantallasVisibles){
		super(tipoEvento,tipoEvento);
		this.tiposPantalla = tiposPantalla;
		this.pantallasVisibles = pantallasVisibles;
	}
     

    
     /**
      * Devuelve una lista de objectos <CODE>Estacion, Proceso o Fase</CODE> dependiendo
      * del tipo de evento generado.
      * @see gov.sir.core.negocio.modelo.Proceso
      * @return la lista de procesos
      */
    public List getLista() {
        return (List)getPayload();
    }
	
	/**
	 * Devuelve una lista de asociada al nombre suministrado
	 * @param nombreLista
	 * @return la lista asociada al nombre suministrado
	 */
	public List getLista(String nombreLista) {
		if(tablaListas == null)
			return null;
		
		List lista = (List)tablaListas.get(nombreLista);
		return lista;
	}
    
    /**
     * Obtiene la estacion cuando el tipo de evento es LISTA_PROCESOS
     * @return la estacion asociada al usuario
     */    
    public Estacion getEstacion(){
        return estacion;
    }
    
    /**
     * Obtiene el usuario cuando el tipo de evento es LISTA_ESTACIONES
     * @return el usuario autenticado
     */    
    public Usuario getUsuario(){
        return usuario;
    }
    
    /**
     * Obtiene el proceso cuando el tipo de evento es LISTA_FASES
     * @return el proceso asociado a la estacion
     */    
    public Proceso getProceso(){
        return proceso;
    }
    /**
     * Obtiene el identificador del usuario con el cual sera identificado en la auditoria
     * @return el identificador cuando el tipo de evento es LISTA_ESTACIONES 
     */
    public String getID(){
    	return loginID;
    }
    
    public List getListaInicia(){
    	return listaInicia;
    }
	/**
	 * @return
	 */
	public Rol getRol() {
		return rol;
	}


	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * @return
	 */
	public org.auriga.core.modelo.transferObjects.Usuario getUsuarioAuriga() {
		return usuarioAuriga;
	}


	/**
	 * @return
	 */
	public Hashtable getTabla() {
		return tabla;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}
	
	/**
	 * Agrega una nueva lista con el nombre suministrado
	 * @param nombreLista El nombre al que se asociará la lista
	 * @param lista La lista a agregar
	 */
	public void addLista(String nombreLista, List lista) {
		if(tablaListas == null)
			tablaListas = new Hashtable();
		
		tablaListas.put(nombreLista, lista);
	}

	/**
	 * @return
	 */
	public Map getImpresoras() {
		return impresoras;
	}

	/**
	 * @return listado de las pantallas visibles para el usuario.
	 */
	public List getPantallasVisibles() {
		return pantallasVisibles;
	}

	/**
	 * @return listado de tipos de pantallas definidos en la aplicación. 
	 */
	public List getTiposPantalla() {
		return tiposPantalla;
	}

	/**
	 * @param list
	 */
	public void setPantallasVisibles(List list) {
		pantallasVisibles = list;
	}

	/**
	 * @param list
	 */
	public void setTiposPantalla(List list) {
		tiposPantalla = list;
	}

	public List getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List listaRoles) {
		this.listaRoles = listaRoles;
	}
	
	public boolean isdministradorImpresionActivo() {
		return administradorImpresionActivo;
	}

	public void setAdministradorImpresionActivo(
			boolean administradorImpresionActivo) {
		this.administradorImpresionActivo = administradorImpresionActivo;
	}

	public List getRolesAlertaAdmImpesionInactivo() {
		return rolesAlertaAdmImpesionInactivo;
	}

	public void setRolesAlertaAdmImpesionInactivo(
			List rolesAlertaAdmImpesionInactivo) {
		this.rolesAlertaAdmImpesionInactivo = rolesAlertaAdmImpesionInactivo;
	}
}
