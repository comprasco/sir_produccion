package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * en Efectivo.
 * @author eacosta
 */
public class DocPagoConvenio extends DocumentoPago implements TransferObject{
    private static final long serialVersionUID = 1L;
	/** Metodo constructor*/
	public DocPagoConvenio(){
    }
    /**
	 * Constructor que especifica los atributos de un pago realizado en efectivo.
     * @param valor
     */
	public DocPagoConvenio(double valor){
        this.tipoPago = DocumentoPago.PAGO_CONVENIO;
        this.valorDocumento = valor;
    }
}
