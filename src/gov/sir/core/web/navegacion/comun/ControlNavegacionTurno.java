package gov.sir.core.web.navegacion.comun;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWTurno;


/**
 * @author mmunoz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ControlNavegacionTurno implements ControlNavegacion{

	public static final String LISTAR = "LISTAR"; 
	public static final String TURNO_DEPENDIENTE = "TURNO_DEPENDIENTE"; 
	public static final String ENTREGA_MASIVA_REPARTO_NOTARIAL = "ENTREGA_MASIVA_REPARTO_NOTARIAL";
	public static final String CERTIFICADOS_ASOCIADOS_REGISTRO = "CERTIFICADOS_ASOCIADOS_REGISTRO";
	public static final String CERTIFICADOS_SIMPLIFICADO = "CERTIFICADOS_SIMPLIFICADO";

	/**
	 * 
	 */
	public void doStart(HttpServletRequest arg0) {
		
	}

	 /**
	  *	Este método lo que hace es la verificacion de los diferentes objectos que se encuentran el la sesion, 
	  *	y deacuerdo a esa verificacion manda una respuesta para que sea procesada y asi poder tener una
	  *	navegacion acertada.
	  */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
 		String accion = (String) request.getParameter(WebKeys.ACCION);
		HttpSession session = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException(
				"La accion enviada por la accion web no es valida");
		}
		if(accion.equals(AWTurno.LISTAR)){
			return LISTAR;
		} else if(accion.equals(AWTurno.INICIAR_PROCESO)){
			return ((Proceso)session.getAttribute(WebKeys.PROCESO)).getNombre();
		} else if(accion.equals(AWTurno.PROCESAR_TURNOS_MULTIPLES)){
			Map mapaTurnosHijos = (Map) session.getAttribute(WebKeys.TURNOS);
			if(mapaTurnosHijos!=null && !mapaTurnosHijos.isEmpty() )
				return this.ENTREGA_MASIVA_REPARTO_NOTARIAL;
		}else if(accion.equals(AWTurno.CONSULTAR_RELACION)){
			Log.getInstance().debug(ControlNavegacionTurno.class,"ENTRO CONSULTA RELACION control");
			return CERTIFICADOS_ASOCIADOS_REGISTRO;
		}
		
		if(accion.equals(AWTurno.CONTINUAR_TURNO) || accion.equals(AWTurno.CONTINUAR_INDIVIDUAL)){
			Boolean turnoDependiente = (Boolean) session.getAttribute(WebKeys.DEPENDENCIA_TURNO);
			Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
			if(turnoDependiente != null && turnoDependiente.booleanValue() && turno!=null &&
					!turno.getIdFase().equals(CFase.CAL_CALIFICACION) && !turno.getIdFase().equals(CFase.CAL_CONFRONTACION_CORRECTIVA) &&
					!turno.getIdFase().equals(CFase.REG_CORREGIR_ENCABEZADO)) {
				return TURNO_DEPENDIENTE;
			}
			Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
			if(fase != null && !fase.getID().trim().equals("")){
				return fase.getID();
			}
			
		}
		return null;
	}

	/**
	 * 
	 */
	public void doEnd(HttpServletRequest arg0) {
		
	}

}
