package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoHeredado;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado a través de su turno padre (certificados masivos, turno registro
 * con certificados asociados)
 *
 * @author fceballos
 */
public class DocPagoHeredadoEnhanced extends DocumentoPagoEnhanced {

    public DocPagoHeredadoEnhanced() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en
     * efectivo.
     *
     * @param valor
     */
    public DocPagoHeredadoEnhanced(double valor) {
        this.tipoPago = DocumentoPagoEnhanced.PAGO_HEREDADO;
        this.valorDocumento = valor;
    }

    public static DocPagoHeredadoEnhanced enhance(DocPagoHeredado docPagoHeredado) {
        return (DocPagoHeredadoEnhanced) Enhanced.enhance(docPagoHeredado);
    }

}
