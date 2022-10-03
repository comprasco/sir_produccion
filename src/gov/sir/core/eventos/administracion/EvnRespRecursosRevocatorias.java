package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * con recursos y revocatorias 
 * @author ppabon
 */
public class EvnRespRecursosRevocatorias extends EvnSIRRespuesta {

	/** Constante que identifica la acción de bloquear recursos*/
	public static final String BLOQUEAR_RECURSOS = "BLOQUEAR_RECURSOS";
	
	/** Constante que identifica la acción de agregar matrículas para ser bloqueadas*/
	public static final String AGREGAR_MATRICULA_BLOQUEO = "AGREGAR_MATRICULA_BLOQUEO";
	
	/** Constante que identifica la acción de consultar los turnos bloqueados*/
	public static final String CONSULTAR_TURNOS_BLOQUEADOS = "CONSULTAR_TURNOS_BLOQUEADOS";

	/** Constante que identifica la acción de reanotar los recursos y revocatorias directas*/
	public static final String REANOTAR_RECURSOS = "REANOTAR_RECURSOS";

	/** Constante que identifica la acción de rechazar los recursos y revocatorias directas*/
	public static final String RECHAZAR_RECURSOS = "RECHAZAR_RECURSOS";
	
	/** Lista para almacenar la lista de personas que pueden calificar el turno que se quiere reanotar*/
	List listaCalificadores = null;

        /*
        * @author      : Julio Alcázar Rivas
        * @change      : nueva variable para manejar en el evento los usuarios con  el rol testamentos
        * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
        */
        List listaCalTestamentos = null;

        public List getListaCalTestamentos() {
            return listaCalTestamentos;
        }

        public void setListaCalTestamentos(List listaCalTestamentos) {
            this.listaCalTestamentos = listaCalTestamentos;
        }

	/**
	 * @param payload
	 */
	public EvnRespRecursosRevocatorias(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespRecursosRevocatorias(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

	/**
	 * @return
	 */
	public List getListaCalificadores() {
		return listaCalificadores;
	}

	/**
	 * @param list
	 */
	public void setListaCalificadores(List list) {
		listaCalificadores = list;
	}

}
