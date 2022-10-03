/*
 * Created on 20-sep-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EvnTurnoManualCertificado extends EvnSIR {
	      
	public static final String LIQUIDAR = "LIQUIDAR";
	public static final String SOLICITAR_LIQUIDAR = "SOLICITAR_LIQUIDAR";
	public static final String ASOCIAR_TURNO = "ASOCIAR_TURNO";
	private Turno turno;
	private String idTurno;
	private String anio;

	private Liquidacion liquidacion;

	private Proceso proceso;

	private Estacion estacion;

	private boolean habilitarPago;
    private boolean asignarEstacion;

	/** Identificador unico de usuario*/
	private String UID;

	/** Identificador del circulo registral*/
	private String idCirculo;

	/** Circulo Registral*/
	private Circulo circulo;

	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	
	private boolean omitirRecibo;

	
	/**
	 * @param usuario
	 */
	public EvnTurnoManualCertificado(Usuario usuario) {
		super(usuario);
	}
	
	public EvnTurnoManualCertificado(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	public EvnTurnoManualCertificado(Usuario usuario, Liquidacion liquidacion, Proceso proceso,
		Estacion estacion, boolean habilitarPago, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {

		super(usuario, LIQUIDAR);
		this.liquidacion = liquidacion;
		this.proceso = proceso;
		this.estacion = estacion;
		this.habilitarPago = habilitarPago;
		this.usuarioSIR = usuarioSIR;
	}

	public EvnTurnoManualCertificado(Usuario usuario, Liquidacion liquidacion, Proceso proceso,
		Estacion estacion, boolean habilitarPago, gov.sir.core.negocio.modelo.Usuario usuarioSIR,
		String tipoEvento) {

		super(usuario, tipoEvento);
		this.liquidacion = liquidacion;
		this.proceso = proceso;
		this.estacion = estacion;
		this.habilitarPago = habilitarPago;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * @return
	 */
	public Liquidacion getLiquidacion() {
		return liquidacion;
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
	public Proceso getProceso() {
		return proceso;
	}

	/**
	 * @return
	 */
	public boolean isHabilitarPago() {
		return habilitarPago;
	}

	/**
	 * @return
	 */
	public boolean isAsignarEstacion() {
		return asignarEstacion;
	}

	/**
	 * @param boolean
	 */
	public void setAsignarEstacion(boolean asignarEstacion) {
		this.asignarEstacion = asignarEstacion;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioSIR = usuario;
	}

	/**
	 * @return
	 */
	public String getUID() {
		return UID;
	}

	/**
	 * @param string
	 */
	public void setUID(String string) {
		UID = string;
	}

	/**
	 * @return
	 */
	public String getIdCirculo() {
		return idCirculo;
	}

	/**
	 * @param string
	 */
	public void setIdCirculo(String string) {
		idCirculo = string;
	}

	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
	}


  /**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @return
	 */
	public boolean isOmitirRecibo() {
		return omitirRecibo;
	}

	/**
	 * @param b
	 */
	public void setOmitirRecibo(boolean b) {
		omitirRecibo = b;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	
	public String getIdTurno() {
		return idTurno;
	}
	
	public void setAnio(String anio) {
		this.anio = anio;
	}
	
	public String getAnio() {
		return anio;
	}
}