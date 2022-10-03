package gov.sir.core.eventos.comun;

import gov.sir.core.negocio.modelo.Ciudadano;

/**
 * @author eacosta
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EvnRespCiudadano extends EvnSIRRespuesta {
	
	public static final String CIUDADANO_VALIDADO="CIUDADANO_VALIDADO";
	
	private Ciudadano ciudadano;
	private boolean ciudadanoEncontrado;
	private boolean mostrarCiudadano;

	/**
	 * @param payload
	 */
	public EvnRespCiudadano(Object payload) {
		super(payload, CIUDADANO_VALIDADO);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespCiudadano(Ciudadano ciudadano) {
		super(ciudadano, CIUDADANO_VALIDADO);
		this.ciudadano= ciudadano;
	}

	/**
	 * @return
	 */
	public Ciudadano getCiudadano() {
		return ciudadano;
	}
	/**
	 * @return
	 */
	public boolean isCiudadanoEncontrado() {
		return ciudadanoEncontrado;
	}

	/**
	 * @param b
	 */
	public void setCiudadanoEncontrado(boolean b) {
		ciudadanoEncontrado = b;
	}



	/**
	 * @return
	 */
	public boolean isMostrarCiudadano() {
		return mostrarCiudadano;
	}

	/**
	 * @param b
	 */
	public void setMostrarCiudadano(boolean b) {
		mostrarCiudadano = b;
	}

}
