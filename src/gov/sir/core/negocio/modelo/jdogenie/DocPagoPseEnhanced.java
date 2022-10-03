package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoPse;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado en Efectivo.
 *
 * @author eacosta
 */
public class DocPagoPseEnhanced extends DocumentoPagoEnhanced {

    public DocPagoPseEnhanced() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en
     * efectivo.
     *
     * @param valor
     */
    public DocPagoPseEnhanced(double valor) {
        this.tipoPago = DocumentoPagoEnhanced.PAGO_PSE;
        this.valorDocumento = valor;
    }

    public static DocPagoPseEnhanced enhance(DocPagoPse docPagoPse) {
        return (DocPagoPseEnhanced) Enhanced.enhance(docPagoPse);
    }
}
