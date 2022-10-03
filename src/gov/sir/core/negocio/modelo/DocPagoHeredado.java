package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * a través de su turno padre (certificados masivos, turno registro con certificados asociados)
 * @author fceballos
 */
public class DocPagoHeredado extends DocumentoPago implements TransferObject{
    private static final long serialVersionUID = 1L;
	/** Metodo constructor por defecto  */
	
	public DocPagoHeredado(){
    }
    /**
     * Constructor que especifica los atributos de un pago heredado
     * @param valor
     */
	public DocPagoHeredado(double valor){
        this.tipoPago = DocumentoPago.PAGO_HEREDADO;
        this.valorDocumento = valor;
    }
}
