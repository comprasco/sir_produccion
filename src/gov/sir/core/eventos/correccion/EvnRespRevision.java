package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes de respuesta con respecto a la fase de confrontación en el proceso de 
 * correcciones desde la capa de negocio a la capa web
 * @author ppabon
 */
public class EvnRespRevision extends EvnSIRRespuesta {
	private Turno turno;

	public static final String TURNO="TURNO";
	
	/**
	 * Constante que identifica que se desea avanzar por ANTIGUO SISTEMA
	 */
	public final static String ANTIGUO_SISTEMA = "ANTIGUO_SISTEMA";

	/**
	 * Constante que identifica que se desea avanzar por ACTOS ADMINISTRATIVOS
	 */
	public final static String ACTOS_ADMIN = "ACTOS_ADMIN";
	
	/**
	 * Constante que identifica que se desea avanzar por MAYOR_VALOR
	 */
	public final static String MAYOR_VALOR = "MAYOR_VALOR";


	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespRevision(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}
	
	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespRevision(Object payload) {
		super(payload, TURNO);
	}



}
