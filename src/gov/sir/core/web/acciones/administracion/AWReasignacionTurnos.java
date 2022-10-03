package gov.sir.core.web.acciones.administracion;

import java.util.ArrayList;
import java.util.List;

import gov.sir.core.eventos.administracion.EvnReasignacionTurnos;
import gov.sir.core.eventos.administracion.EvnRespReasignacionTurnos;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CEstacion;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jdo.JDOObjectNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.core.modelo.daoObjects.DAOException;
import org.auriga.core.modelo.daoObjects.DAOFactory;
import org.auriga.core.modelo.daoObjects.RolDAO;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar todo lo relacionado con la reasignación de turnos.
 * Es usada cuando un funcionario sale de la oficina por ejemplo por vacaciones y se requiere que
 * otro funcionario realice sus trabajo.
 * @author ppabon
 */
public class AWReasignacionTurnos extends SoporteAccionWeb {

	/** Constante que identifica la acción de cargar los usuarios del circulo*/
	public static final String CARGAR_USUARIOS = "CARGAR_USUARIOS";

	/** Constante que identifica la acción de cargar los roles que tiene un usuario*/
	public static final String CARGAR_ROLES = "CARGAR_ROLES";
        
	/** Constante que identifica la acción de cargar los turnos que tiene el usuario para un rol determinado*/
	public static final String CARGAR_TURNOS = "CARGAR_TURNOS";
	
	/** Constante que identifica la acción de cargar las estaciones que tiene un usuario determinado*/
	public static final String CARGAR_ESTACIONES = "CARGAR_ESTACIONES";	
	
	/** Constante que identifica la acción de reasignar los turnos a otro usuario*/
	public static final String REASIGNAR_TURNOS = "REASIGNAR_TURNOS";
        
        /** Constante que identifica el permiso que tiene el usuario para reasignar turnos */
        public static final String PERMITIR_REASIGNACION = "PERMITIR_REASIGNACION";
	
	/** Constante que identifica la acción de reasignar los turnos a otro usuario, cuando 
	 * el usuario tiene datos temporales y el bloqueo sobre los folios del turno*/
	public static final String REASIGNAR_TURNOS_FORZOSO = "REASIGNAR_TURNOS_FORZOSO";	

	/** Constante que identifica la acción de reasignar los turnos a otro usuario, cuando 
	 * el usuario tiene datos temporales y el bloqueo sobre los folios del turno*/
	public static final String REASIGNAR_TURNOS_CON_TEMPORALES = "REASIGNAR_TURNOS_CON_TEMPORALES";	
	
	/** Constante que identifica la acción de regresar a la página principal de Pantallas Administrativas*/
	public static final String VOLVER = "VOLVER";

	//LISTA DE CONSTANTES.
	public static final String RT_LISTA_USUARIOS = "RT_LISTA_USUARIOS";
	public static final String RT_LISTA_ROLES = "RT_LISTA_ROLES";
	public static final String RT_LISTA_TURNOS = "RT_LISTA_TURNOS";
	public static final String RT_LISTA_TURNOS_SELECCIONADOS = "RT_LISTA_TURNOS_SELECCIONADOS";
	public static final String RT_LISTA_TURNOS_NO_REASIGNADOS = "RT_LISTA_TURNOS_NO_REASIGNADOS";
	public static final String RT_LISTA_TURNOS_REASIGNADOS = "RT_LISTA_TURNOS_REASIGNADOS";
	public static final String RT_LISTA_POSIBLES_USUARIOS = "RT_LISTA_POSIBLES_USUARIOS";
	public static final String RT_LISTA_POSIBLES_ESTACIONES = "RT_LISTA_POSIBLES_ESTACIONES";
	public static final String RT_ID_USUARIO_ORIGEN = "RT_ID_USUARIO_ORIGEN";
	public static final String RT_ID_USUARIO_DESTINO = "RT_ID_USUARIO_DESTINO";
	
	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
                String accion = request.getParameter(WebKeys.ACCION).trim();
                HttpSession session = request.getSession();
                
		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una acción válida");
		} else if (accion.equals(AWReasignacionTurnos.CARGAR_USUARIOS)) {
			return cargarUsuarios(request);
		} else if (accion.equals(AWReasignacionTurnos.CARGAR_ROLES)) {
			return cargarRoles(request);
		} else if (accion.equals(AWReasignacionTurnos.CARGAR_TURNOS)) {
			return cargarTurnos(request);
		}else if (accion.equals(AWReasignacionTurnos.CARGAR_ESTACIONES)) {
			return cargarEstaciones(request);
		} else if (accion.equals(AWReasignacionTurnos.REASIGNAR_TURNOS)) {
			return reasignarTurnos(request);
		} else if (accion.equals(AWReasignacionTurnos.REASIGNAR_TURNOS_FORZOSO)) {
			return reasignarTurnosForzoso(request);
		} else if (accion.equals(AWReasignacionTurnos.REASIGNAR_TURNOS_CON_TEMPORALES)) {
			return reasignarTurnosTemporales(request);
		} else if (accion.equals(AWReasignacionTurnos.VOLVER)) {
			return limpiarDatosSesion(request);
		}
		return null;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento que permita consultar los usuarios del circulo.
	 * @param request
	 * @return evento <code>EvnReasignacionTurnos</code> con la información de los usurios del circulo
	 * @throws AccionWebException
	 */
	private EvnReasignacionTurnos cargarUsuarios(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
              
		session.removeAttribute(RT_LISTA_USUARIOS);
		session.removeAttribute(RT_ID_USUARIO_ORIGEN);
		
		session.removeAttribute(RT_LISTA_ROLES);
		session.removeAttribute(CRol.ID_ROL);		

		session.removeAttribute(RT_LISTA_TURNOS);
		session.removeAttribute(RT_LISTA_TURNOS_SELECCIONADOS);		
		session.removeAttribute(RT_LISTA_TURNOS_NO_REASIGNADOS);		
		
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_USUARIOS);
		session.removeAttribute(RT_ID_USUARIO_DESTINO);		
		
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);
		session.removeAttribute(CEstacion.ESTACION_ID);
		
      
		return new EvnReasignacionTurnos(usuarioAuriga, EvnReasignacionTurnos.CARGAR_USUARIOS, circulo);
                 
	}
	
	/**
	 * Permite cargar la lista de roles que tiene un usuario.
	 * @param request La información del formulario
	 * @return Un evento EvnReasignacionTurnos de tipo CARGAR_ROLES
	 * @throws AccionWebException
	 */
	private EvnReasignacionTurnos cargarRoles(HttpServletRequest request)throws AccionWebException {
		HttpSession session = request.getSession();
		
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		
		ValidacionParametrosException exception = new ValidacionParametrosException();
		
		String idUsuarioOrigen = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN);
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN , idUsuarioOrigen);		
	
		if(idUsuarioOrigen==null || idUsuarioOrigen.equals("") || idUsuarioOrigen.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al que le desea reasignar los turnos.");
			throw exception;	
		}
		
		session.removeAttribute(RT_LISTA_ROLES);
		session.removeAttribute(CRol.ID_ROL);		

		session.removeAttribute(RT_LISTA_TURNOS);
		session.removeAttribute(RT_LISTA_TURNOS_SELECCIONADOS);		
		
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_USUARIOS);
		session.removeAttribute(RT_ID_USUARIO_DESTINO);		
		
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);
		session.removeAttribute(CEstacion.ESTACION_ID);

		
		Usuario usuarioOrigen = new Usuario();
		usuarioOrigen.setIdUsuario(new Long(idUsuarioOrigen).longValue());
			
		return new EvnReasignacionTurnos(usuario, EvnReasignacionTurnos.CARGAR_ROLES, circulo, usuarioOrigen);
        
	}

	/**
	* Permite cargar la lista de turnos que tiene un usuario en un rol determinado.
	* @param request La información del formulario
	* @return Un evento EvnReasignacionTurnos de tipo CARGAR_TURNOS
	* @throws AccionWebException
	*/
	private EvnReasignacionTurnos cargarTurnos(HttpServletRequest request)throws AccionWebException {
        	
		HttpSession session = request.getSession();
		
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		
		ValidacionParametrosException exception = new ValidacionParametrosException();
		
		String idUsuarioOrigen = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN);
		String idRol = request.getParameter(CRol.ID_ROL);
		
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN , idUsuarioOrigen);
		session.setAttribute(CRol.ID_ROL , idRol);
	
		if(idUsuarioOrigen==null || idUsuarioOrigen.equals("") || idUsuarioOrigen.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al que le desea reasignar los turnos.");
		}
		if(idRol==null || idRol.equals("") || idRol.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el rol del usuario para observar los turnos que tiene asignados.");
		}		
		if(exception.getErrores().size()>0){
			throw exception;	
		}
		

		session.removeAttribute(RT_LISTA_TURNOS);
		session.removeAttribute(RT_LISTA_TURNOS_SELECCIONADOS);		
		
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_USUARIOS);
		session.removeAttribute(RT_ID_USUARIO_DESTINO);		
		
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);
		session.removeAttribute(CEstacion.ESTACION_ID);

		Usuario usuarioOrigen = new Usuario();
		usuarioOrigen.setIdUsuario(new Long(idUsuarioOrigen).longValue());
		
		Rol rol = new Rol();
		rol.setRolId(idRol);
		
			
		return new EvnReasignacionTurnos(usuario, EvnReasignacionTurnos.CARGAR_TURNOS, circulo, usuarioOrigen , rol);
        
	}
	
	/**
	* Permite cargar la lista de las estaciones que tiene un usuario en un rol determinado.
	* @param request La información del formulario
	* @return Un evento EvnReasignacionTurnos de tipo CARGAR_TURNOS
	* @throws AccionWebException
	*/
	private EvnReasignacionTurnos cargarEstaciones(HttpServletRequest request)throws AccionWebException {
		HttpSession session = request.getSession();
		
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		
		ValidacionParametrosException exception = new ValidacionParametrosException();
		
		String idUsuarioOrigen = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN);
		String idRol = request.getParameter(CRol.ID_ROL);
		String idUsuarioDestino = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);
		
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN , idUsuarioOrigen);
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO, idUsuarioDestino);
		session.setAttribute(CRol.ID_ROL , idRol);
	
		if(idUsuarioOrigen==null || idUsuarioOrigen.equals("") || idUsuarioOrigen.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al que le desea reasignar los turnos.");
		}
		if(idRol==null || idRol.equals("") || idRol.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el rol del usuario para observar los turnos que tiene asignados.");
		}
		if(idUsuarioDestino==null || idUsuarioDestino.equals("") || idUsuarioDestino.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al quien le va a reasignar los turnos.");
		}	
		
		//VALIDAR QUE SE HAYA SELECCIONADO ALGÚN TURNO
		List turnosAReanotar = new ArrayList();
		String[] turnos = request.getParameterValues(CTurno.ID_TURNO);
		
		if(turnos !=null && turnos.length >0){
			for (int i = 0; i < turnos.length; i++) {
				String idWorkFlowTurno = turnos[i];
				turnosAReanotar.add(idWorkFlowTurno);
			}
		}
		session.setAttribute(RT_LISTA_TURNOS_SELECCIONADOS , turnosAReanotar);

		if(exception.getErrores().size()>0){
			throw exception;	
		}
		
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);
		session.removeAttribute(CEstacion.ESTACION_ID);
		
		Usuario usuarioOrigen = new Usuario();
		usuarioOrigen.setIdUsuario(new Long(idUsuarioOrigen).longValue());
		
		Rol rol = new Rol();
		rol.setRolId(idRol);
		
		Usuario usuarioDestino = new Usuario();
		usuarioDestino.setIdUsuario(new Long(idUsuarioDestino).longValue());		
		
		return new EvnReasignacionTurnos(usuario, EvnReasignacionTurnos.CARGAR_ESTACIONES, circulo, usuarioOrigen , rol, usuarioDestino);
        
	}	
	
	/**
	* Permite reasignar los turnos de un usuario en un determinado rol a otro usuario que tenga el mismo rol asignado.
	* @param request La información del formulario
	* @return Un evento EvnReasignacionTurnos de tipo CARGAR_TURNOS
	* @throws AccionWebException
	*/
	private EvnReasignacionTurnos reasignarTurnos(HttpServletRequest request) throws AccionWebException {
		
		HttpSession session = request.getSession();
		
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		
		ValidacionParametrosException exception = new ValidacionParametrosException();
		
		String idUsuarioOrigen = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN);
		String idRol = request.getParameter(CRol.ID_ROL);
		String idUsuarioDestino = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);
		String idEstacion = request.getParameter(CEstacion.ESTACION_ID);
		
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN , idUsuarioOrigen);
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO, idUsuarioDestino);
		session.setAttribute(CRol.ID_ROL , idRol);
		session.setAttribute(CEstacion.ESTACION_ID, idEstacion);
	
		if(idUsuarioOrigen==null || idUsuarioOrigen.equals("") || idUsuarioOrigen.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al que le desea reasignar los turnos.");
		}
		if(idRol==null || idRol.equals("") || idRol.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el rol del usuario para observar los turnos que tiene asignados.");
		}
		if(idUsuarioDestino==null || idUsuarioDestino.equals("") || idUsuarioDestino.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al quien le va a reasignar los turnos.");
		}	
		
		//VALIDAR QUE SE HAYA SELECCIONADO ALGÚN TURNO
		List turnosAReanotar = new ArrayList();
		String[] turnos = request.getParameterValues(CTurno.ID_TURNO);
		
		if(turnos !=null && turnos.length >0){
			for (int i = 0; i < turnos.length; i++) {
				String idWorkFlowTurno = turnos[i];
				turnosAReanotar.add(idWorkFlowTurno);
			}
		}
		session.setAttribute(RT_LISTA_TURNOS_SELECCIONADOS , turnosAReanotar);
                
                List confirmReasign = new ArrayList();
                List notaInfo = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
                boolean infoError = false;
                if(notaInfo == null || notaInfo.isEmpty()){
                    infoError = true;
                }
                try{
                    HermodService hs = HermodService.getInstance();
                    Iterator itT = turnosAReanotar.iterator();
                    Turno turn = new Turno();
                    while(itT.hasNext()){
                        turn.setIdWorkflow((String) itT.next());
                        int limiteNota = hs.getLimiteReasignacion(turn);
                        if(limiteNota == 2){
                            continue;
                        }
                        confirmReasign.add(turn.getIdWorkflow());
                    }
                   
                    if(turnosAReanotar != null && !turnosAReanotar.isEmpty()){
                    if(confirmReasign == null || confirmReasign.isEmpty()){
                        exception.addError("El turno ha alcanzado el limite máximo de reasignaciones (2)");
                        request.getSession().removeAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
                    }
                  }
                } catch (HermodException he){
                   System.out.println("ERROR No ha sido posible hacer la consulta");
                }
                
                
                String idProceso;
                String idTurno;
                Turno turno = new Turno();
                List notasInformativas = new ArrayList();
                if(confirmReasign != null && !confirmReasign.isEmpty()){
                    if(infoError){
                        exception.addError("No es posible reasignar el turno: Se requiere una nota informativa.");
                    }
                }
                if(notaInfo != null && !notaInfo.isEmpty() && turnosAReanotar != null){
                    Iterator itNota = notaInfo.iterator();
                    while(itNota.hasNext()){
                        Nota nota = (Nota) itNota.next();
                        Iterator itTurn = turnosAReanotar.iterator();
                        while(itTurn.hasNext()){
                            try{
                                String idWF = (String) itTurn.next();
                                Nota note = null;
                                note = (Nota) nota.clone();
                                HermodService hs = HermodService.getInstance();
                                Turno turn = hs.getTurnobyWF(idWF);
                                turno.setIdWorkflow(idWF);
                                int limiteNota = hs.getLimiteReasignacion(turn);
                                if(limiteNota >= hs.getReasignacion()){
                                    continue;
                                }
                                String[] idturno = idWF.split("-");
                                idProceso = idturno[2];
                                idTurno = idturno[3];
                                note.setIdProceso(Integer.parseInt(idProceso));
                                note.setIdTurno(idTurno);
                                note.setAnio(idturno[0]);
                                notasInformativas.add(note);
                            } catch (CloneNotSupportedException e){
                                System.out.println("ERROR No ha sido posible clonar el objeto");
                            } catch (HermodException he){
                                System.out.println("ERROR No ha sido posible hacer la consulta");
                            }
                            
                        }
                    }
                }
                

		if(turnosAReanotar==null || turnosAReanotar.size()==0){
			exception.addError("Debe seleccionar por lo menos turno a reasignar.");
		}		
		
		
		//VALIDAR QUE SE HAYA SELECCIONADO ALGUNA ESTACION CUANDO EL CASO LO AMERITE (EL USUARIO DESTINO TENGA MÁS DE UNA ESTACIÓN PARA EL ROL).
		List listaEstaciones = (List)session.getAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);
		if(listaEstaciones!=null && listaEstaciones.size()>0){
			if(idEstacion==null || idEstacion.equals("") || idEstacion.equals(WebKeys.SIN_SELECCIONAR) ){
				exception.addError("Debe seleccionar una estación para el usuario.");
			}	
		}
		
		
		if(exception.getErrores().size()>0){
			throw exception;	
		}
		
		Usuario usuarioOrigen = new Usuario();
		usuarioOrigen.setIdUsuario(new Long(idUsuarioOrigen).longValue());
		
		Rol rol = new Rol();
		rol.setRolId(idRol);
		
		Usuario usuarioDestino = new Usuario();
		usuarioDestino.setIdUsuario(new Long(idUsuarioDestino).longValue());		
		
		Estacion estacion = null;
		if(idEstacion!=null){
			estacion = new Estacion();
			estacion.setEstacionId(idEstacion);		
		}
                
                request.getSession().removeAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
                
                EvnReasignacionTurnos evn = new EvnReasignacionTurnos(usuario, EvnReasignacionTurnos.REASIGNAR_TURNOS, circulo, usuarioOrigen , rol ,usuarioDestino , turnosAReanotar ,estacion);
                evn.setNotasInformativas(notasInformativas);
		return evn;
        
	}	
	
	/**
	* Permite reasignar los turnos de un usuario en un determinado rol a otro usuario que tenga el mismo rol asignado.
	* Cuande el usuario que tenía el turno tenía folios con datos temporales y el bloqueo sobre los mismos
	* Se conservan los datos temporales
	* @param request La información del formulario
	* @return Un evento EvnReasignacionTurnos de tipo CARGAR_TURNOS
	* @throws AccionWebException
	*/
	private EvnReasignacionTurnos reasignarTurnosTemporales(HttpServletRequest request) throws AccionWebException {
		
		HttpSession session = request.getSession();
		
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		
		ValidacionParametrosException exception = new ValidacionParametrosException();
		
		String idUsuarioOrigen = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN);
		String idRol = request.getParameter(CRol.ID_ROL);
		String idUsuarioDestino = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);
		String idEstacion = request.getParameter(CEstacion.ESTACION_ID);
		
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN , idUsuarioOrigen);
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO, idUsuarioDestino);
		session.setAttribute(CRol.ID_ROL , idRol);
		session.setAttribute(CEstacion.ESTACION_ID, idEstacion);
	
		if(idUsuarioOrigen==null || idUsuarioOrigen.equals("") || idUsuarioOrigen.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al que le desea reasignar los turnos.");
		}
		if(idRol==null || idRol.equals("") || idRol.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el rol del usuario para observar los turnos que tiene asignados.");
		}
		if(idUsuarioDestino==null || idUsuarioDestino.equals("") || idUsuarioDestino.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al quien le va a reasignar los turnos.");
		}	
		
		//VALIDAR QUE SE HAYA SELECCIONADO ALGÚN TURNO
		List turnosAReanotar = new ArrayList();
		String[] turnos = request.getParameterValues(CTurno.ID_TURNO);
		
		if(turnos !=null && turnos.length >0){
			for (int i = 0; i < turnos.length; i++) {
				String idWorkFlowTurno = turnos[i];
				turnosAReanotar.add(idWorkFlowTurno);
			}
		}
		session.setAttribute(RT_LISTA_TURNOS_SELECCIONADOS , turnosAReanotar);

		if(turnosAReanotar==null || turnosAReanotar.size()==0){
			exception.addError("Debe seleccionar por lo menos turno a reasignar.");
		}		
		
		
		//VALIDAR QUE SE HAYA SELECCIONADO ALGUNA ESTACION CUANDO EL CASO LO AMERITE (EL USUARIO DESTINO TENGA MÁS DE UNA ESTACIÓN PARA EL ROL).
		List listaEstaciones = (List)session.getAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);
		if(listaEstaciones!=null && listaEstaciones.size()>0){
			if(idEstacion==null || idEstacion.equals("") || idEstacion.equals(WebKeys.SIN_SELECCIONAR) ){
				exception.addError("Debe seleccionar una estación para el usuario.");
			}	
		}
		
		
		if(exception.getErrores().size()>0){
			throw exception;	
		}
		
		Usuario usuarioOrigen = new Usuario();
		usuarioOrigen.setIdUsuario(new Long(idUsuarioOrigen).longValue());
		
		Rol rol = new Rol();
		rol.setRolId(idRol);
		
		Usuario usuarioDestino = new Usuario();
		usuarioDestino.setIdUsuario(new Long(idUsuarioDestino).longValue());		
		
		Estacion estacion = null;
		if(idEstacion!=null){
			estacion = new Estacion();
			estacion.setEstacionId(idEstacion);		
		}
			
		return new EvnReasignacionTurnos(usuario, EvnReasignacionTurnos.REASIGNAR_TURNOS_CON_TEMPORALES, circulo, usuarioOrigen , rol ,usuarioDestino , turnosAReanotar ,estacion);
        
	}		
	
	/**
	* Permite reasignar los turnos de un usuario en un determinado rol a otro usuario que tenga el mismo rol asignado.
	* Cuande el usuario que tenía el turno tenía folios con datos temporales y el bloqueo sobre los mismos
	* @param request La información del formulario
	* @return Un evento EvnReasignacionTurnos de tipo CARGAR_TURNOS
	* @throws AccionWebException
	*/
	private EvnReasignacionTurnos reasignarTurnosForzoso(HttpServletRequest request) throws AccionWebException {
		
		HttpSession session = request.getSession();
		
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		
		ValidacionParametrosException exception = new ValidacionParametrosException();
		
		String idUsuarioOrigen = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN);
		String idRol = request.getParameter(CRol.ID_ROL);
		String idUsuarioDestino = request.getParameter(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);
		String idEstacion = request.getParameter(CEstacion.ESTACION_ID);
		
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN , idUsuarioOrigen);
		session.setAttribute(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO, idUsuarioDestino);
		session.setAttribute(CRol.ID_ROL , idRol);
		session.setAttribute(CEstacion.ESTACION_ID, idEstacion);
	
		if(idUsuarioOrigen==null || idUsuarioOrigen.equals("") || idUsuarioOrigen.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al que le desea reasignar los turnos.");
		}
		if(idRol==null || idRol.equals("") || idRol.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el rol del usuario para observar los turnos que tiene asignados.");
		}
		if(idUsuarioDestino==null || idUsuarioDestino.equals("") || idUsuarioDestino.equals(WebKeys.SIN_SELECCIONAR) ){
			exception.addError("Debe seleccionar el usuario al quien le va a reasignar los turnos.");
		}	
		
		//VALIDAR QUE SE HAYA SELECCIONADO ALGÚN TURNO
		List turnosAReanotar = new ArrayList();
		String[] turnos = request.getParameterValues(CTurno.ID_TURNO);
		
		if(turnos !=null && turnos.length >0){
			for (int i = 0; i < turnos.length; i++) {
				String idWorkFlowTurno = turnos[i];
				turnosAReanotar.add(idWorkFlowTurno);
			}
		}
		session.setAttribute(RT_LISTA_TURNOS_SELECCIONADOS , turnosAReanotar);

		if(turnosAReanotar==null || turnosAReanotar.size()==0){
			exception.addError("Debe seleccionar por lo menos turno a reasignar.");
		}		
		
		
		//VALIDAR QUE SE HAYA SELECCIONADO ALGUNA ESTACION CUANDO EL CASO LO AMERITE (EL USUARIO DESTINO TENGA MÁS DE UNA ESTACIÓN PARA EL ROL).
		List listaEstaciones = (List)session.getAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);
		if(listaEstaciones!=null && listaEstaciones.size()>0){
			if(idEstacion==null || idEstacion.equals("") || idEstacion.equals(WebKeys.SIN_SELECCIONAR) ){
				exception.addError("Debe seleccionar una estación para el usuario.");
			}	
		}
		
		
		if(exception.getErrores().size()>0){
			throw exception;	
		}
		
		Usuario usuarioOrigen = new Usuario();
		usuarioOrigen.setIdUsuario(new Long(idUsuarioOrigen).longValue());
		
		Rol rol = new Rol();
		rol.setRolId(idRol);
		
		Usuario usuarioDestino = new Usuario();
		usuarioDestino.setIdUsuario(new Long(idUsuarioDestino).longValue());		
		
		Estacion estacion = null;
		if(idEstacion!=null){
			estacion = new Estacion();
			estacion.setEstacionId(idEstacion);		
		}
			
		return new EvnReasignacionTurnos(usuario, EvnReasignacionTurnos.REASIGNAR_TURNOS_FORZOSO, circulo, usuarioOrigen , rol ,usuarioDestino , turnosAReanotar ,estacion);
        
	}		


	/**
	 * Este método se encarga de eliminar los objetos que se subieron a la sesión en la ejecución de la reasignación de turnos.
	 * @param request
	 * @return evento <code>EvnReasignacionTurnos</code>.
	 * @throws AccionWebException
	 */
	private EvnReasignacionTurnos limpiarDatosSesion(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_USUARIOS);
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_ROLES);
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS);
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_NO_REASIGNADOS);
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_REASIGNADOS);
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_SELECCIONADOS);
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_USUARIOS);
		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);

		session.removeAttribute(CRol.ID_ROL);
		session.removeAttribute(CEstacion.ESTACION_ID);
		session.removeAttribute(AWReasignacionTurnos.RT_ID_USUARIO_ORIGEN);
		session.removeAttribute(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);


		session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);
		session.removeAttribute(CEstacion.ESTACION_ID);

		return null;
	}

	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente 
	 * de la acción de negocio. 
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespReasignacionTurnos respuesta = (EvnRespReasignacionTurnos) evento;

		HttpSession session = request.getSession();
		context = session.getServletContext();

		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
			if (tipoEvento.equals(EvnRespReasignacionTurnos.CARGAR_USUARIOS)) {
                                session.setAttribute(WebKeys.PROCESO,respuesta.getProceso());
                                session.setAttribute(WebKeys.LISTA_TIPOS_NOTAS, respuesta.getListaNotasInformativas());
				session.setAttribute(AWReasignacionTurnos.RT_LISTA_USUARIOS, respuesta.getPayload());
				return;
			} else if (tipoEvento.equals(EvnRespReasignacionTurnos.CARGAR_ROLES)) {
				session.setAttribute(AWReasignacionTurnos.RT_LISTA_ROLES, respuesta.getPayload());
				return;
			} else if (tipoEvento.equals(EvnRespReasignacionTurnos.CARGAR_TURNOS)) {
				session.setAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS, respuesta.getPayload());
				session.setAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_USUARIOS, respuesta.getListaPosiblesUsuarios());
				if(respuesta.getListaPosiblesEstaciones()!=null){
					session.setAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES, respuesta.getListaPosiblesEstaciones());	
				}				
				return;
			} else if (tipoEvento.equals(EvnRespReasignacionTurnos.CARGAR_ESTACIONES)) {
				session.setAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES, respuesta.getPayload());
				return;
			} else if (tipoEvento.equals(EvnRespReasignacionTurnos.REASIGNAR_TURNOS)) {
				
				if(respuesta.getListaTurnosNoReasignados()!=null&&!respuesta.getListaTurnosNoReasignados().isEmpty()){
					session.setAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_NO_REASIGNADOS , respuesta.getListaTurnosNoReasignados());	
				}
				if(respuesta.getListaTurnosReasignados()!=null&&!respuesta.getListaTurnosReasignados().isEmpty()){
					session.setAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_REASIGNADOS , respuesta.getListaTurnosReasignados());	
				} else{
                                    session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_REASIGNADOS);
                                }
						
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS);
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_SELECCIONADOS);
                                session.removeAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
				
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_USUARIOS);
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);

				session.removeAttribute(CRol.ID_ROL);				
				session.removeAttribute(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);
				session.removeAttribute(CEstacion.ESTACION_ID);				
				
				return;
			} else if (tipoEvento.equals(EvnRespReasignacionTurnos.REASIGNAR_TURNOS_FORZOSO)) {
				
				if(respuesta.getListaTurnosNoReasignados()!=null&&!respuesta.getListaTurnosNoReasignados().isEmpty()){
					session.setAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_NO_REASIGNADOS , respuesta.getListaTurnosNoReasignados());	
				}		
						
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS);
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_SELECCIONADOS);
				
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_USUARIOS);
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);

				session.removeAttribute(CRol.ID_ROL);				
				session.removeAttribute(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);
				session.removeAttribute(CEstacion.ESTACION_ID);				
				
				return;
			} else if (tipoEvento.equals(EvnRespReasignacionTurnos.REASIGNAR_TURNOS_CON_TEMPORALES)) {
				
				if(respuesta.getListaTurnosNoReasignados()!=null&&!respuesta.getListaTurnosNoReasignados().isEmpty()){
					session.setAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_NO_REASIGNADOS , respuesta.getListaTurnosNoReasignados());	
				}		
						
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS);
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_TURNOS_SELECCIONADOS);
				
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_USUARIOS);
				session.removeAttribute(AWReasignacionTurnos.RT_LISTA_POSIBLES_ESTACIONES);

				session.removeAttribute(CRol.ID_ROL);				
				session.removeAttribute(AWReasignacionTurnos.RT_ID_USUARIO_DESTINO);
				session.removeAttribute(CEstacion.ESTACION_ID);				
				
				return;
			}
		}
	}

}
