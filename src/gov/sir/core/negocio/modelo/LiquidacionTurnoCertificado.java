/*
 * Created on 05-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela los datos de liquidaciones del proceso de certificados
 * @author fceballos
 */
public class LiquidacionTurnoCertificado extends Liquidacion implements TransferObject{

	private TipoCertificado tipoCertificado;
	private static final long serialVersionUID = 1L;
	/**
	* Tipo de tarifa que debe ser aplicado a la liquidación
	*/
	private String tipoTarifa;
	
	/**
	 * UID de la sesión. Usado para establecer la impresión del recibo del certificado asociado
	 */
	private String uid;
	
	private String justificacionMayorValor;
        
        /**
        * @Autor: Santiago Vásquez
        * @Change: 2062.TARIFAS.REGISTRALES.2017
        */
        private boolean esFolioMayorExtension;

        public boolean isEsFolioMayorExtension() {
            return esFolioMayorExtension;
        }

        public void setEsFolioMayorExtension(boolean esFolioMayorExtension) {
            this.esFolioMayorExtension = esFolioMayorExtension;
        }
        
	public String getJustificacionMayorValor() {
		return justificacionMayorValor;
	}

	public void setJustificacionMayorValor(String justificacionMayorValor) {
		this.justificacionMayorValor = justificacionMayorValor;
	}

	/** Constructor por defecto */
	public LiquidacionTurnoCertificado(){
	}

	/** Retorna el identificador del tipo de certificado (Certificados)
	 * @return tipoCertificado  	 */
	public TipoCertificado getTipoCertificado() {
		return tipoCertificado;
	}

	/** Cambia el identificador del tipo de certificado (Certificados)
	 * @param certificado   	 */
	public void setTipoCertificado(TipoCertificado certificado) {
		tipoCertificado = certificado;
	}
	

	/** Retorna el tipo de tarifa para certificados
	 * @return El tipo de tarifa asociado con la liquidación	 */
	public String getTipoTarifa() {
		return tipoTarifa;
	}

	/** Asocia al atributo tarifa, el valor recibido como parámetro  	*/
	public void setTipoTarifa(String tipoTarifa) {
		this.tipoTarifa = tipoTarifa;
	}

	/** Retorna el identificador de sesión para impresión de recibos asociados
	 * @return uid 	 */
	public String getUid() {
		return uid;
	}

	/** Cambia el identificador de sesión para impresión de recibos asociados
	 * @param string 	 */
	public void setUid(String string) {
		uid = string;
	}

}
