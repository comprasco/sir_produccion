package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas la reasignación de turnos.
 * @author ppabon
 */
public class EvnReasignacionTurnos extends EvnSIR {

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

	private Circulo circulo;	
	
	private gov.sir.core.negocio.modelo.Usuario usuarioOrigen;
	
	private Rol rol;
        
        private List notasInformativas;
	
	private List turnos;
	
	private gov.sir.core.negocio.modelo.Usuario usuarioDestino;
	
	private Estacion estacion; 	
	
	private String justificacion;

	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	

	/**
	 * Constructor para el envio de datos a la capa de negocio con la acción a ejecutar y el usuario
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnReasignacionTurnos(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}
	
	/**
	 * Constructor para el envio de datos a la capa de negocio con la acción a ejecutar y el usuario
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnReasignacionTurnos(Usuario usuario, String tipoEvento, Circulo circulo) {
		super(usuario, tipoEvento);
		this.circulo = circulo;
	}	
	
	/**
	 * Constructor para el envio de datos a la capa de negocio con la acción a ejecutar y el usuario
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnReasignacionTurnos(Usuario usuario, String tipoEvento, Circulo circulo, gov.sir.core.negocio.modelo.Usuario usuarioOrigen) {
		super(usuario, tipoEvento);
		this.circulo = circulo;
		this.usuarioOrigen =usuarioOrigen;
	}	
	
	/**
	 * Constructor para el envio de datos a la capa de negocio con la acción a ejecutar y el usuario
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnReasignacionTurnos(Usuario usuario, String tipoEvento, Circulo circulo, gov.sir.core.negocio.modelo.Usuario usuarioOrigen, Rol rol) {
		super(usuario, tipoEvento);
		this.circulo = circulo;
		this.usuarioOrigen =usuarioOrigen;
		this.rol = rol;
	}	
	
	/**
	 * Constructor para el envio de datos a la capa de negocio con la acción a ejecutar y el usuario
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnReasignacionTurnos(Usuario usuario, String tipoEvento, Circulo circulo, gov.sir.core.negocio.modelo.Usuario usuarioOrigen, Rol rol, gov.sir.core.negocio.modelo.Usuario usuarioDesino) {
		super(usuario, tipoEvento);
		this.circulo = circulo;
		this.usuarioOrigen =usuarioOrigen;
		this.usuarioDestino = usuarioDesino;
		this.rol = rol;
	}		
	
	/**
	 * Constructor para el envio de datos a la capa de negocio con la acción a ejecutar y el usuario
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnReasignacionTurnos(Usuario usuario, String tipoEvento, Circulo circulo, gov.sir.core.negocio.modelo.Usuario usuarioOrigen, Rol rol, gov.sir.core.negocio.modelo.Usuario usuarioDestino, List turnosAReasignar, Estacion estacion) {
		super(usuario, tipoEvento);
		this.circulo = circulo;
		this.usuarioOrigen =usuarioOrigen;
		this.rol = rol;
		this.usuarioDestino = usuarioDestino;
		this.turnos = turnosAReasignar;
		this.estacion = estacion;
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
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * @return
	 */
	public String getJustificacion() {
		return justificacion;
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
	public List getTurnos() {
		return turnos;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioDestino() {
		return usuarioDestino;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioOrigen() {
		return usuarioOrigen;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	/**
	 * @param estacion
	 */
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	/**
	 * @param string
	 */
	public void setJustificacion(String string) {
		justificacion = string;
	}

	/**
	 * @param rol
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @param list
	 */
	public void setTurnos(List list) {
		turnos = list;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioDestino(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioDestino = usuario;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioOrigen(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioOrigen = usuario;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioSIR = usuario;
	}

        public List getNotasInformativas() {
            return notasInformativas;
        }

        public void setNotasInformativas(List notasInformativas) {
            this.notasInformativas = notasInformativas;
        }   

}
