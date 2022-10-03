/*
 * Created on 05-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela los datos de liquidaciones del proceso de certificados masivos
 * @author mrios    */

public class LiquidacionTurnoCertificadoMasivo extends Liquidacion implements TransferObject{

	private TipoCertificado tipoCertificado;
        private static final long serialVersionUID = 1L;
	/**
	* Tipo de tarifa que debe ser aplicado a la liquidación
	*/
	private String tipoTarifa;
	
	/** Constructor por defecto */
	public LiquidacionTurnoCertificadoMasivo(){
	}

	/** Retorna el identificador del tipo de certificado (Certificados)
	 * @return tipoCertificado
	 */
	public TipoCertificado getTipoCertificado() {
		return tipoCertificado;
	}

	/** Cambia el identificador del tipo de certificado (Certificados)
	 * @param certificado
	 */
	public void setTipoCertificado(TipoCertificado certificado) {
		tipoCertificado = certificado;
	}
	
	
	/** Rerorna el tipo de tarifa para certificados
	 * @return tipoTarifa
	 */
	public String getTipoTarifa() {
		return tipoTarifa;
	}

	/** Cambia el tipo de tarifa para certificados
	 * @param string
	 */
	public void setTipoTarifa(String string) {
		tipoTarifa = string;
	}

}
