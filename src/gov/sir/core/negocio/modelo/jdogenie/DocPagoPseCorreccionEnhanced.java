package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoPseCorreccion;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado en Efectivo.
 *
 * @author eacosta
 */
public class DocPagoPseCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced {

    public DocPagoPseCorreccionEnhanced() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en
     * efectivo.
     *
     * @param valor
     */
    public DocPagoPseCorreccionEnhanced(double valor) {
        this.tipoPago = DocumentoPagoCorreccionEnhanced.PAGO_PSE;
        this.valorDocumento = valor;
    }

    public static DocPagoPseCorreccionEnhanced enhance(DocPagoPseCorreccion docPagoPse) {
        return (DocPagoPseCorreccionEnhanced) Enhanced.enhance(docPagoPse);
    }
}
