/*
 * Created on 03-nov-2004
 *
 */
package gov.sir.core.web.acciones.registro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.sir.core.eventos.registro.EvnReparto;
import gov.sir.core.eventos.registro.EvnRespReparto;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.util.IDidTurnoComparator;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Turno;


import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * La accion web reparto se encarga de construir una lista de turnos a repartir entre
 * funcionarios (Abogados).
 *
 * @author dcantor
 *
 */
public class AWReparto extends SoporteAccionWeb {

    public static final String CREAR = "CREAR_REPARTO";

    /**
     * Se genera un evento con la información de la estacion actual.
     * De esta forma se hara el reparto de los turnos que se encuentren en esta estacion
     * en la capa de negocio.
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
    	String accion = request.getParameter(WebKeys.ACCION);
    	
		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}
		
		if (accion.equals(CREAR)){
			return crearReparto(request);
		}
		else {
			throw new AccionInvalidaException(
				"La accion " + accion + " no es valida.");
		}
    }

    private EvnReparto crearReparto(HttpServletRequest request)
        throws AccionWebException {
    	HttpSession sesion = request.getSession();
        Estacion e = (Estacion) sesion.getAttribute(WebKeys.ESTACION);
        Usuario u = (Usuario) sesion.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        List turnos = (List) sesion.getAttribute(WebKeys.LISTA_TURNOS);
        Circulo c = (Circulo) sesion.getAttribute(WebKeys.CIRCULO);
        if(turnos.isEmpty()){
        	throw new AccionWebException("No existen turnos para repartir");
        }
        
        //SE DEBE ORDENAR POR idWorkflow
        try {
			Collections.sort(turnos, new IDidTurnoComparator());
		} catch (Exception ee) {
			Log.getInstance().debug(AWReparto.class, "No se pudieron ordenar los Turnos");
		}
		
        //Turno turnoDesdeObj = (Turno)turnos.get(0);
        
        //Validar que sea del proceso 6 y validar que sea del circulo
        
        //Se debe buscar el turno en la lista y se ingresan los turnos hasta hay
        String anio = request.getParameter(CTurno.TURNO_HASTA);
        long valorAnio;
        String idTurno = request.getParameter(CTurno.TURNO_HASTA1);
        long valoridTurno;
        
        boolean isRango = false;
        
        List turnosRango = new ArrayList();
        List turnosRangoFinal = new ArrayList();
        
        for (int i=0; i<turnos.size();i++){
        	turnosRango.add(((Turno)turnos.get(i)).getIdWorkflow());
        }
        if (anio == null){
        	anio = "";
        }
        if (idTurno == null){
        	idTurno = "";
        }
        
        if (anio.length()!=0 && idTurno.length()!=0) {
        	try{
    			if((anio!=null)&&(anio.trim().length()!=0)){
    				valorAnio = Long.parseLong(anio);
    			}
    		}
    		catch (NumberFormatException ex){
    			throw new AccionWebException("El Año es inválido: " + anio);
    		}
    		
    		try{
    			if((idTurno!=null)&&(idTurno.trim().length()!=0)){
    				valoridTurno = Long.parseLong(idTurno);
    			}
    		}
    		catch (NumberFormatException ex){
    			throw new AccionWebException("El turno es inválido: " + idTurno);
    		}
            
            Turno hastaT = new Turno();
            hastaT.setAnio(anio);
            hastaT.setIdCirculo(c.getIdCirculo());
            hastaT.setIdProceso(Long.parseLong(CProceso.PROCESO_REGISTRO));
            hastaT.setIdTurno(idTurno);
            hastaT.setIdWorkflow(hastaT.getAnio() + "-" + hastaT.getIdCirculo() + "-" + hastaT.getIdProceso() + "-" + hastaT.getIdTurno());
            
            //Se valida que el turno este en el listado
            if (!turnosRango.contains(hastaT.getIdWorkflow())) {
            	throw new AccionWebException("El turno " + hastaT.getAnio() + "-" + hastaT.getIdCirculo() + "-" + hastaT.getIdProceso() + "-" + hastaT.getIdTurno() + ", es inválido, no esta en el rango.");
            }
            
            int posicionTurnoIngresado = turnosRango.indexOf(hastaT.getIdWorkflow());
            
            //Se van a ingresar los turnos menores a ese
            
            for (int i=0; i<=posicionTurnoIngresado;i++){
            	turnosRangoFinal.add(((Turno)turnos.get(i)).getIdWorkflow());
            }

            if (turnosRangoFinal.size() == 0) {
            	throw new AccionWebException("El turno " + hastaT.getAnio() + "-" + hastaT.getIdCirculo() + "-" + hastaT.getIdProceso() + "-" + hastaT.getIdTurno() + ", es inválido, no esta en el rango.");
            }
            isRango = true;
        } else {
        	if (anio.length()==0 && idTurno.length()==0) {
	        	turnosRangoFinal = turnosRango;
        	} else {
        		if (anio.length()==0) {
        			throw new AccionWebException("Debe ingresar una Valor para el Año.");
        		}
        		if (idTurno.length()==0) {
        			throw new AccionWebException("Debe ingresar una Valor para el turno.");
        		}
        	}
        }

        EvnReparto evento;
        
        if (isRango) {
        	evento = new EvnReparto(u, e, EvnReparto.CREAR_REPARTO_RANGO, c);
        } else {
        	evento = new EvnReparto(u, e, EvnReparto.CREAR_REPARTO, c);
        }
		
        evento.setTurnosRepartir(turnosRangoFinal);
        return evento;
    }
    
    public void doEnd(HttpServletRequest request, EventoRespuesta eventoRespuesta) {
        EvnRespReparto respuesta = (EvnRespReparto)eventoRespuesta;
        if (respuesta == null) return;
        request.getSession().setAttribute(WebKeys.MAPA_ABOGADO_TURNO, respuesta.getAbogadoTurno());
        request.getSession().setAttribute(WebKeys.MAPA_ABOGADO_ESTACION, respuesta.getAbogadoEstacion());
        request.getSession().setAttribute(WebKeys.MAPA_ABOGADO_RELACION, respuesta.getRelacionesUsuario());
        request.getSession().setAttribute(WebKeys.LISTA_OBSERVACIONES_REPARTO_ABOGADOS, respuesta.getObservaciones());
		request.getSession().setAttribute(WebKeys.ID_PROCESO_REPARTO, respuesta.getIdProcesoReparto());
    }       
}
