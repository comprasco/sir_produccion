package gov.sir.core.web.navegacion.comun;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWSeguridad;

/**
 *
 * @author I.Siglo21
 * Esta clase obtiene el request de AWSeguridad y de acuerdo a los objetos que se tengan en la sesion,
 * esta clase esta encargada de direccionar al usuario a la pantalla siguiente.
 * Se verifican que los diferentes objetos sean validos, y si no lo son se manda el mensaje correspondiente
 */
public class ControlNavegacionSeguridad implements ControlNavegacion {
	/** Constante que identifica que la operacion de autenticacion fue exitosa */
	public static final String LOGIN_OK = "LOGIN_OK";

	/** Constante que identifica que la operacion de autenticacion no fue exitosa */
	public static final String LOGIN_FAILED = "LOGIN_FAILED";

	/** Constante que identifica que el rol es valido */
	public static final String ROL_OK = "ROL_OK";

	/** Constante que identifica que el rol no es valido */
	public static final String ROL_FAILED = "ROL_FAILED";

	/** Constante que identifica que la lista no es valida */
	public static final String ROLES_FAILED = "ROLES_FAILED";

	/** Constante que identifica que la lista de estaciones no es valida */
	public static final String ESTACIONES_FAILED = "ESTACIONES_FAILED";

	/** Constante que identifica que la estacion no es valida */
	public static final String ESTACION_FAILED = "ESTACION_FAILED";

	/** Constante que identifica que la estacion es valida */
	public static final String ESTACION_OK = "ESTACION_OK";

	/** Constante que identifica que la lista de procesos no es valida */
	public static final String PROCESOS_FAILED = "PROCESOS_FAILED";

	/** Constante que identifica que el proceso no es valido */
	public static final String PROCESO_FAILED = "PROCESO_FAILED";

	/** Constante que identifica que el proceso es valido */
	public static final String PROCESO_OK = "PROCESO_OK";

	/** Constante que identifica que la lista de fases no es valida */
	public static final String FASES_FAILED = "FASES_FAILED";

	/** Constante que identifica que la operacion de cerrar la sesion fue exitosa */
	public static final String LOGOUT_OK = "LOGOUT_OK";

	/** Constante que identifica que la operacion de inicio de sesión como administrador fué exitosa */
	public static final String ADMINISTRACION_OK = "ADMINISTRACION_OK";

	/** Constante que identifica el campo del jsp donde se solicita la clave del usuario*/
	public final static String INICIAR_COMO_ADMINISTRADOR = "INICIAR_COMO_ADMINISTRADOR";
    

	/**
	 *
	 */
	public void doStart(HttpServletRequest arg0) {
	}

	/**
	 *        Este método lo que hace es la verificacion de los diferentes objectos que se encuentran el la sesion,
	 *        y deacuerdo a esa verificacion manda una respuesta para que sea procesada y asi poder tener una
	 *        navegacion acertada.
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {

		String accion = (String) request.getParameter(WebKeys.ACCION);
                /**
                 * @author David Panesso
                 * @change 1241.ADAPTACION DEL PROCESO DE AUTENTICACIÓN Y SSO DE CA SITEMINDER
                 * Se cambia la ubicación del usuario a un Header HTTP que se recibe de CA SiteMinder
                 * Para desarrollo se mantiene el usuario por parámetro HTTP
                 */
                if(accion == null){
                    String accionHeader = request.getHeader("ACCION");                
                    if (accionHeader != null && accionHeader.equals(AWSeguridad.LOGIN)) {
                        accion = accionHeader;
                    }
                }
		HttpSession session = request.getSession();
                List procesos = null;                                
                
                if ((accion == null) || accion.equals("")) {
                        throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
                }
		if (accion.equals(AWSeguridad.LOGIN)) {
			Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
			List roles = (List) session.getAttribute(WebKeys.LISTA_ROLES);

			Boolean administra = (Boolean)session.getAttribute(WebKeys.INICIA_SESION_ADMINISTRACION);
			
			if(administra != null && administra.booleanValue()){
				session.removeAttribute(WebKeys.INICIA_SESION_ADMINISTRACION);
				return ADMINISTRACION_OK;
			}

			if ((usuario == null) || usuario.getUsername().equals("")) {
				return LOGIN_FAILED;
			} else if (roles != null) {
				return LOGIN_OK;
			} else {
				return ROLES_FAILED;
			}
		} else if (accion.equals(AWSeguridad.CONSULTAR_ROL)) {
			if (((List) session.getAttribute(WebKeys.LISTA_ESTACIONES)).size() == 1) {
                request.setAttribute(WebKeys.NUM_ESTACIONES, new Integer(((List) session.getAttribute(WebKeys.LISTA_ESTACIONES)).size()));
				Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
				procesos = (List) session.getAttribute(WebKeys.LISTA_PROCESOS);
                request.setAttribute(WebKeys.NUM_PROCESOS, new Integer(((List) session.getAttribute(WebKeys.LISTA_PROCESOS)).size()));
				if ((estacion == null) || estacion.getEstacionId().equals("")) {
					return ESTACION_FAILED;
				} else if (procesos != null) {
                    /* JAlcazar 26/05/2009
                  * Elimina de la Lista Procesos el Proceso CORRECCIONES
                  * que corresponde al Rol SIR_ROL_RESPONSABLE_CORRECCIONES
                 */
                if(CRol.SIR_ROL_RESPONSABLE_CORRECCIONES.equals(((Rol) session.getAttribute(WebKeys.ROL)).getRolId())){
                    int tamProcesos = procesos.size();
                    for (int iP = 0; iP < tamProcesos; iP++){
                        if (!"CORRECCION".equals(((Proceso)procesos.get(iP)).getDescripcion())){
                            procesos.remove(iP);
                        }
                    }
                }
                 /* JAlcazar 20/05/2009
                 Agrega un atributo al request con el tamaño de la lista de procesos */
                request.setAttribute(WebKeys.NUM_PROCESOS, new Integer(((List) procesos).size()));
					return ESTACION_OK;
				} else {
					return PROCESOS_FAILED;
				}
			} else {
				Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
				List estaciones = (List) session.getAttribute(WebKeys.LISTA_ESTACIONES);

				if ((rol == null) || rol.getRolId().equals("")) {
					return ROL_FAILED;
				} else if (estaciones != null) {
					return ROL_OK;
				} else {
					return ESTACIONES_FAILED;
				}
			}

		} else if (accion.equals(AWSeguridad.CONSULTAR_ESTACION)) {
			Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
			procesos = (List) session.getAttribute(WebKeys.LISTA_PROCESOS);

			if ((estacion == null) || estacion.getEstacionId().equals("")) {
				return ESTACION_FAILED;
			} else if (procesos != null) {
                 /* JAlcazar 26/05/2009
                  * Elimina de la Lista Procesos el Proceso CORRECCIONES
                  * que corresponde al Rol SIR_ROL_RESPONSABLE_CORRECCIONES
                 */
                if(CRol.SIR_ROL_RESPONSABLE_CORRECCIONES.equals(((Rol) session.getAttribute(WebKeys.ROL)).getRolId())){
                    int tamProcesos = procesos.size();
                    for (int iP = 0; iP < tamProcesos; iP++){
                        if (!"CORRECCION".equals(((Proceso)procesos.get(iP)).getDescripcion())){
                            procesos.remove(iP);
                        }
                    }
                }
                 /* JAlcazar 20/05/2009
                 Agrega un atributo al request con el tamaño de la lista de procesos */
                request.setAttribute(WebKeys.NUM_PROCESOS, new Integer(((List) procesos).size()));
                return ESTACION_OK;
			} else {
				return PROCESOS_FAILED;
			}
		} else if (accion.equals(AWSeguridad.CONSULTAR_PROCESO)) {
			Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
			List fases = (List) session.getAttribute(WebKeys.LISTA_FASES);

                         /**
                         * @author: Henry Gómez Rocha.
                         * @change: Se remueve del rol "USUARIO OPERATIVO CONSULTAS" la fase "ENTREGAR CONSULTA COMPLEJA O EXENTA".
                         * Mantis:  5166 Acta - Requerimiento No 151.
                         */

                        if(CRol.SIR_ROL_USUARIO_OPERATIVO_CONSULTAS.equals(((Rol) session.getAttribute(WebKeys.ROL)).getRolId())){
                            fases.remove(1);
                        }


            request.setAttribute(WebKeys.NUM_FASES, new Integer(fases.size()));
			if ((proceso == null) || proceso.getIdProceso() == 0) {
				return PROCESO_FAILED;
			} else if (fases != null) {
				return PROCESO_OK;
			} else {
				return FASES_FAILED;
			}
		} else if (accion.equals(AWSeguridad.LOGOUT)) {
			request.getSession(true);
			return LOGOUT_OK;
		} else if (accion.equals(AWSeguridad.INICIAR_COMO_ADMINISTRADOR)) {
			session.setAttribute(AWSeguridad.INICIAR_COMO_ADMINISTRADOR, new Boolean(true));
			session.removeAttribute(WebKeys.INICIA_SESION_ADMINISTRACION);
			return INICIAR_COMO_ADMINISTRADOR;			
		} else {
			return null;
		}
	}

	/**
	 *
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
