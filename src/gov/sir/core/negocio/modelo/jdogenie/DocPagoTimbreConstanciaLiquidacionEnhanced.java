package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoEfectivo;


/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * en Efectivo.
 * @author eacosta
 */
public class DocPagoTimbreConstanciaLiquidacionEnhanced extends DocumentoPagoEnhanced{
	
	private String numeroTimbre;
	

	/**
	 * Fecha
	 */
	private String fecha = "";
	
	
	public DocPagoTimbreConstanciaLiquidacionEnhanced(){
	}
	/**
	 * Constructor que especifica los atributos de un pago realizado en efectivo.
	 * @param valor
	 */
	public DocPagoTimbreConstanciaLiquidacionEnhanced(double valor, String numTimbre, String fecha){
		this.tipoPago = DocumentoPagoEnhanced.TIMBRE_CONSTANCIA_LIQUIDACION;
		this.valorDocumento = valor;
		this.numeroTimbre = numTimbre;
		this.fecha = fecha;
	}
	
	public static DocPagoTimbreConstanciaLiquidacionEnhanced enhance(DocPagoEfectivo docPagoEfectivo){
	   return (DocPagoTimbreConstanciaLiquidacionEnhanced)Enhanced.enhance(docPagoEfectivo);
	}
	/**
	 * @return
	 */
	public String getNumeroTimbre() {
		return numeroTimbre;
	}

	/**
	 * @param string
	 */
	public void setNumeroTimbre(String string) {
		numeroTimbre = string;
	}

	/**
	 * @return
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param string
	 */
	public void setFecha(String string) {
		fecha = string;
	}

}