/*
 * Created on 21-ene-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.eventos.comun;

import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author eacosta
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EvnCiudadano extends EvnSIR {

	/**
	 * Constante que indica que se debe buscar un ciudadano como solicitante
	 */
	public static final String CONSULTAR = "CONSULTAR";
	

	/**
	 * Constante que indica que se debe buscar un ciudadano en anotación
	 */
	public static final String CONSULTAR_CIUDADANO_ANOTACION = "CONSULTAR_CIUDADANO_ANOTACION";
                              /*
        * @author : CTORRES
        * @change : Constante que indica quese debe consultar testador
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
        public static final String CONSULTAR_TESTADOR = "CONSULTAR_TESTADOR";
	
	private Ciudadano ciudadano;
	
	private boolean isRegistro = false;
	private boolean isCorrecciones = false;
	private boolean isAdministrativa = false;
	
	private Turno turno;
	
	
	/**
	 * @param usuario
	 */
	public EvnCiudadano(Usuario usuario) {
		super(usuario);
	}

	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnCiudadano(Usuario usuario, String tipoEvento, Ciudadano ciudadano) {
		super(usuario, tipoEvento);
		this.ciudadano = ciudadano;
	}

	/**
	 * @return
	 */
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	public boolean isAdministrativa() {
		return isAdministrativa;
	}

	public void setAdministrativa(boolean isAdministrativa) {
		this.isAdministrativa = isAdministrativa;
	}

	public boolean isCorrecciones() {
		return isCorrecciones;
	}

	public void setCorrecciones(boolean isCorrecciones) {
		this.isCorrecciones = isCorrecciones;
	}

	public boolean isRegistro() {
		return isRegistro;
	}

	public void setRegistro(boolean isRegistro) {
		this.isRegistro = isRegistro;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
}
