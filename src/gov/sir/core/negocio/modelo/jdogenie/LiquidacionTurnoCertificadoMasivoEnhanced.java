/*
 * Created on 05-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;

/**
 * @author mrios
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LiquidacionTurnoCertificadoMasivoEnhanced extends LiquidacionEnhanced{

	private TipoCertificadoEnhanced tipoCertificado;

	/**
	* Tipo de tarifa que debe ser aplicado a la liquidación. 
	*/
	private String tipoTarifa;
	
	public LiquidacionTurnoCertificadoMasivoEnhanced(){
	}

	/**
	 * @return
	 */
	public TipoCertificadoEnhanced getTipoCertificado() {
		return tipoCertificado;
	}

	/**
	 * @param certificado
	 */
	public void setTipoCertificado(TipoCertificadoEnhanced certificado) {
		tipoCertificado = certificado;
	}
	
	public static LiquidacionTurnoCertificadoMasivoEnhanced enhance(LiquidacionTurnoCertificado liquidacion){
		return (LiquidacionTurnoCertificadoMasivoEnhanced) Enhanced.enhance(liquidacion);
	}
	/**
	 * @return
	 */
	public String getTipoTarifa() {
		return tipoTarifa;
	}

	/**
	 * @param string
	 */
	public void setTipoTarifa(String string) {
		tipoTarifa = string;
	}

}