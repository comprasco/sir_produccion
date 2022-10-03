package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * en Efectivo.
 * @author eacosta
 */
public class DocPagoEfectivoCorreccion extends DocumentoPagoCorreccion implements TransferObject{
    private static final long serialVersionUID = 1L;
	/** Metodo constructor*/
	public DocPagoEfectivoCorreccion(){
    }
    /**
	 * Constructor que especifica los atributos de un pago realizado en efectivo.
     * @param valor
     */
	public DocPagoEfectivoCorreccion(double valor){
        this.tipoPago = DocumentoPagoCorreccion.PAGO_EFECTIVO;
        this.valorDocumento = valor;
    }      
}
