package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoEfectivoCorreccion;


/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * en Efectivo.
 * @author eacosta
 */
public class DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced{
	
	private String numeroTimbre;
	

	/**
	 * Fecha
	 */
	private String fecha = "";
	
	
	public DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced(){
	}
	/**
	 * Constructor que especifica los atributos de un pago realizado en efectivo.
	 * @param valor
	 */
	public DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced(double valor, String numTimbre, String fecha){
		this.tipoPago = DocumentoPagoCorreccionEnhanced.TIMBRE_CONSTANCIA_LIQUIDACION;
		this.valorDocumento = valor;
		this.numeroTimbre = numTimbre;
		this.fecha = fecha;
	}
	
	public static DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced enhance(DocPagoEfectivoCorreccion docPagoEfectivo){
	   return (DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced)Enhanced.enhance(docPagoEfectivo);
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