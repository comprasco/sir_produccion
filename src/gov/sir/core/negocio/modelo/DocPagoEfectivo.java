package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * en Efectivo.
 * @author eacosta
 */
public class DocPagoEfectivo extends DocumentoPago implements TransferObject{
    private static final long serialVersionUID = 1L;
	/** Metodo constructor*/
	public DocPagoEfectivo(){
    }
    /**
	 * Constructor que especifica los atributos de un pago realizado en efectivo.
     * @param valor
     */
	public DocPagoEfectivo(double valor){
        this.tipoPago = DocumentoPago.PAGO_EFECTIVO;
        this.valorDocumento = valor;
    }      
}
