package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Turno;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author ppabon
 */
public class EvnRespActuacionAdministrativa extends EvnSIRRespuesta {
	
	private Turno turno;
	private List listaNotas;

	//AVANCES
	public static final String APROBAR_ACTUACION = "APROBAR_ACTUACION";
	public static final String DEVOLVER_A_CORRECCION = "DEVOLVER_A_CORRECCION";
	public static final String ENVIAR_TURNO = "ENVIAR_TURNO";
	
	//EDICION EN ACTUACIONES ADMINISTRATIVAS
	public static final String CARGAR_NOTAS = "CARGAR_NOTAS";
	public static final String AGREGAR_NOTAS = "AGREGAR_NOTAS";
	public static final String EDITAR_NOTAS = "EDITAR_NOTAS";
	

	
	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespActuacionAdministrativa(
		Usuario usuario,
		Turno turno,
		String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespActuacionAdministrativa(List notas, String tipoEvento) {
		super(notas, tipoEvento);
	}
	
	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespActuacionAdministrativa(Turno turno, String tipoEvento) {
		super(turno, tipoEvento);
	}	


	/**
	 * @return
	 */
	public List getListaNotas() {
		return listaNotas;
	}

	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * @param list
	 */
	public void setListaNotas(List list) {
		listaNotas = list;
	}

	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

}
