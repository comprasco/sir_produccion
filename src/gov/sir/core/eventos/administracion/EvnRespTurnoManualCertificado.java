/*
 * Created on 20-sep-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EvnRespTurnoManualCertificado extends EvnSIRRespuesta {
	/**
	 * Esta constante indica que la respuesta es a una solicitud de liquidación.
	 */
	public static final String LIQUIDACION = "LIQUIDACION";
	public static final String VALIDACION = "VALIDACION";
	public static final String VALIDACION_MATRICULA = "VALIDACION_MATRICULA";
	public static final String VALIDACION_MATRICULA_ASOCIADA = "VALIDACION_MATRICULA_ASOCIADA";
	public static final String TURNO_ANTERIOR_VALIDADO="TURNO_ANTERIOR_VALIDADO";
	public static final String ASOCIAR_TURNO_OK = "ASOCIAR_TURNO_OK";


	/**
	 * Liquidación efectuada para la solicitud atendida
	 */
	private Liquidacion liquidacion;

	/**
	 * Determina si el usuario que hace la liquidación es cajero, para evitar hacer otro paso,
	 * entonces el mismo usuario registra el pago.
	 */
	private Boolean esCajero;
	
	/**
	 * Determina si el usuario que hace la liquidación es cajero de registro 
	 * para habilitar o no el botón de siguiente.
	 */
	private Boolean esCajeroRegistro;	




	/**
	 * @param payload
	 */
	public EvnRespTurnoManualCertificado(Liquidacion liquidacion, String tipoRespuesta) {
		super(liquidacion, tipoRespuesta);
		this.liquidacion = liquidacion;
	}


	public EvnRespTurnoManualCertificado(Object payload){
		super(payload,VALIDACION);
	}

	public EvnRespTurnoManualCertificado(Object payload, String tipoRespuesta){
		super(payload,tipoRespuesta);
	}

	public EvnRespTurnoManualCertificado(Turno turno){
		super(turno,TURNO_ANTERIOR_VALIDADO);
	}

	public EvnRespTurnoManualCertificado(String matricula, String tipoRespuesta){
		super(matricula,tipoRespuesta);
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
	public Boolean getEsCajero() {
		return esCajero;
	}

	/**
	 * @param boolean1
	 */
	public void setEsCajero(Boolean boolean1) {
		esCajero = boolean1;
	}

	/**
	 * @return
	 */
	public Boolean getEsCajeroRegistro() {
		return esCajeroRegistro;
	}

	/**
	 * @param boolean1
	 */
	public void setEsCajeroRegistro(Boolean boolean1) {
		esCajeroRegistro = boolean1;
	}

}
