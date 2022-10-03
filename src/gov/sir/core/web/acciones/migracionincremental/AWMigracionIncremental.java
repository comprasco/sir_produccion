package gov.sir.core.web.acciones.migracionincremental;

import gov.sir.core.eventos.migracionincremental.EvnMigracionIncremental;
import gov.sir.core.web.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

public class AWMigracionIncremental extends SoporteAccionWeb {

	/**Accion que llega de la interfaz*/
	private String accion;
	
	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter("ACCION");

		if ((accion == null) || (accion.length() == 0)) {
			throw new AccionWebException("Debe indicar una accion");
		}

		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
		String tipoEvento = accion;
		
		String matricula = request.getParameter("MATRICULA");
		String numTurno = request.getParameter("NUM_TURNO");
		
		
		EvnMigracionIncremental evento = new EvnMigracionIncremental(usuarioAuriga,usuario,tipoEvento,matricula,numTurno);
		return evento;
	}
	

	
}
