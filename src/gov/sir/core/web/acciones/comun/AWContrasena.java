package gov.sir.core.web.acciones.comun;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.eventos.comun.EvnContrasena;
import gov.sir.core.eventos.comun.EvnRespContrasena;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.helpers.comun.ElementoLista;

public class AWContrasena extends SoporteAccionWeb{

	/** Constante que identifica las acción de consultar los datos de la mesa de ayuda*/
	public static final String CONSULTAR_MESA_AYUDA = "CONSULTAR_MESA_AYUDA";
	
	/** Constante que identifica las variable del HttpSession donde se almacena un oplookup type seleccionado */
	public static final String OPLOOKUP_TYPE_SELECCIONADO = "OPLOOKUP_TYPE_SELECCIONADO";
	
	/** Constante que identifica las variable del HttpSession donde se almacena una lista de  oplookup codes seleccionadoa */
	public static final String OPLOOKUP_CODES_SELECCIONADOS = "OPLOOKUP_CODES_SELECCIONADOS";
	

	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
	
		//HttpSession session = request.getSession();
		String accion = request.getParameter(WebKeys.ACCION).trim();
		if (accion.equals(AWContrasena.CONSULTAR_MESA_AYUDA))
			return seleccionaOPLookupType(request);
		return null;
		
	}
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de selección de un oplookup type en la sesión
	 * @param request
	 * @return evento <code>EvnContrasena</code> con los datos del oplookup type
	 * @throws AccionWebException
	 */
	private EvnContrasena seleccionaOPLookupType(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();
	
		String codigo = request.getParameter(COPLookupTypes.NOMBRE_OPLOOKUP_TYPE);
		if ((codigo == null) || (codigo.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código de Lookup Type.");
		}
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
	
		List elementos = (List) context.getAttribute(WebKeys.LISTA_OPLOOKUP_TYPES);
		for (Iterator iter = elementos.iterator(); iter.hasNext();) {
			ElementoLista dato = (ElementoLista) iter.next();
			if (dato.getId().equals(codigo)) {
				EvnContrasena evento=null;
				if(codigo.equals("DATOS_HELP_DESK"))
				 evento=
					new EvnContrasena(usuario, EvnContrasena.LISTAR_OPLOOKUP_CODES_MESA_AYUDA);
				OPLookupTypes opLookUpType = new OPLookupTypes();
				opLookUpType.setTipo(codigo);
				evento.setOpLookupType(opLookUpType);
				session.setAttribute(AWContrasena.OPLOOKUP_TYPE_SELECCIONADO, opLookUpType);
				return evento;
			}
		}
		throw new AccionInvalidaException("El Lookup Type proporcionado no existe en el sistema.");
	}
		
	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente
	 * de la acción de negocio.
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespContrasena respuesta = (EvnRespContrasena) evento;

		HttpSession session = request.getSession();
		context = session.getServletContext();
		
		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
			if(tipoEvento.equals(EvnRespContrasena.LISTADO_LOOKUP_CODES_MESA_AYUDA_OK)) {
				List elementos = (List) respuesta.getPayload();
				session.setAttribute(AWContrasena.OPLOOKUP_CODES_SELECCIONADOS, elementos);
			}
		}
	}
}
