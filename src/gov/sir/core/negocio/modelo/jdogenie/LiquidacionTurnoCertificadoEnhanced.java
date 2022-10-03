/*
 * Created on 05-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LiquidacionTurnoCertificadoEnhanced extends LiquidacionEnhanced{

	private TipoCertificadoEnhanced tipoCertificado;
	
	/**
	* Tipo de tarifa que debe ser aplicado a la liquidación. 
	*/
	private String tipoTarifa;
	
	
	/**
	 * UID de la sesión. Usado para establecer la impresión del recibo del certificado asociado.
	 */
	private String uid;
	
	private String justificacionMayorValor;
	
	public String getJustificacionMayorValor() {
		return justificacionMayorValor;
	}

	public void setJustificacionMayorValor(String justificacionMayorValor) {
		this.justificacionMayorValor = justificacionMayorValor;
	}

	public LiquidacionTurnoCertificadoEnhanced(){
	}

	/**
	 * @return
	 */
	public TipoCertificadoEnhanced getTipoCertificado() {
		return tipoCertificado;
	}
	
	/**
	 * @return El tipo de tarifa asociado con la liquidación.
	 */
	public String getTipoTarifa() {
		return tipoTarifa;
	}
	
	/**
	 * Asocia al atributo tarifa, el valor recibido como
	 * parámetro.
	 */
	public void setTipoTarifa(String tipoTarifa) {
		this.tipoTarifa = tipoTarifa;
	}

	/**
	 * @param certificado
	 */
	public void setTipoCertificado(TipoCertificadoEnhanced certificado) {
		tipoCertificado = certificado;
	}
	
	public static LiquidacionTurnoCertificadoEnhanced enhance(LiquidacionTurnoCertificado liquidacion){
		return (LiquidacionTurnoCertificadoEnhanced) Enhanced.enhance(liquidacion);
	}
	

	/**
	 * @return
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param string
	 */
	public void setUid(String string) {
		uid = string;
	}

}
