package gov.sir.core.eventos.administracion;

import java.util.Hashtable;
import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Proceso;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * con recursos y revocatorias 
 * @author ppabon
 */
public class EvnRespReasignacionTurnos extends EvnSIRRespuesta {

	/** Constante que identifica la acción de cargar los usuarios del circulo*/
	public static final String CARGAR_USUARIOS = "CARGAR_USUARIOS";

	/** Constante que identifica la acción de cargar los roles que tiene un usuario*/
	public static final String CARGAR_ROLES = "CARGAR_ROLES";
	
	/** Constante que identifica la acción de cargar los turnos que tiene el usuario para un rol determinado*/
	public static final String CARGAR_TURNOS = "CARGAR_TURNOS";
	
	/** Constante que identifica la acción de cargar las estaciones que tiene un usuario en rol determinado*/
	public static final String CARGAR_ESTACIONES = "CARGAR_ESTACIONES";
	
	/** Constante que identifica la acción de reasignar los turnos a otro usuario*/
	public static final String REASIGNAR_TURNOS = "REASIGNAR_TURNOS";
	
	/** Constante que identifica la acción de reasignar los turnos a otro usuario, cuando 
	 * el usuario tiene datos temporales y el bloqueo sobre los folios del turno*/
	public static final String REASIGNAR_TURNOS_FORZOSO = "REASIGNAR_TURNOS_FORZOSO";		
	
	/** Constante que identifica la acción de reasignar los turnos a otro usuario, cuando 
	 * el usuario tiene datos temporales y el bloqueo sobre los folios del turno*/
	public static final String REASIGNAR_TURNOS_CON_TEMPORALES = "REASIGNAR_TURNOS_CON_TEMPORALES";	
	
	/** Lista para almacenar los usuarios a los cuales se les podrian asignar los turnos de un usuario dado.*/
	List listaPosiblesUsuarios = null;
	
	/** Lista para almacenar las estaciones a las cuales se les podrian asignar los turnos de un usuario dado.*/
	List listaPosiblesEstaciones = null;
	
	/** Lista para almacenar los turnos que no fueron reasignados.*/
	Hashtable listaTurnosNoReasignados = null;
	
	/** Lista para almacenar los turno que si fueron reasignados.*/
	List listaTurnosReasignados = null;
        
        /**
         *  Lista para almacenar el tipo de nita infomativa coprrespondiente a reasigancion.
         */
        List listaNotasInformativas = null;
        
        Proceso proceso = new Proceso();

	/**
	 * @param payload
	 */
	public EvnRespReasignacionTurnos(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespReasignacionTurnos(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

	/**
	 * @return
	 */
	public List getListaPosiblesEstaciones() {
		return listaPosiblesEstaciones;
	}

	/**
	 * @return
	 */
	public List getListaPosiblesUsuarios() {
		return listaPosiblesUsuarios;
	}

	/**
	 * @param list
	 */
	public void setListaPosiblesEstaciones(List list) {
		listaPosiblesEstaciones = list;
	}

	/**
	 * @param list
	 */
	public void setListaPosiblesUsuarios(List list) {
		listaPosiblesUsuarios = list;
	}

	/**
	 * @return
	 */
	public Hashtable getListaTurnosNoReasignados() {
		return listaTurnosNoReasignados;
	}

	/**
	 * @param list
	 */
	public void setListaTurnosNoReasignados(Hashtable list) {
		listaTurnosNoReasignados = list;
	}

	public List getListaTurnosReasignados() {
		return listaTurnosReasignados;
	}

	public void setListaTurnosReasignados(List listaTurnosReasignados) {
		this.listaTurnosReasignados = listaTurnosReasignados;
	}

        public List getListaNotasInformativas() {
            return listaNotasInformativas;
        }

        public void setListaNotasInformativas(List listaNotasInformativas) {
            this.listaNotasInformativas = listaNotasInformativas;
        }
        
        public Proceso getProceso(){
            return proceso;
        }
        
        public void setProceso(Proceso proceso){
            this.proceso = proceso;
        }
}
