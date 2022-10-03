package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoEfectivo;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado en Efectivo.
 *
 * @author eacosta
 */
public class DocPagoEfectivoEnhanced extends DocumentoPagoEnhanced {


    public DocPagoEfectivoEnhanced() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en
     * efectivo.
     *
     * @param valor
     */
    public DocPagoEfectivoEnhanced(double valor) {
        this.tipoPago = DocumentoPagoEnhanced.PAGO_EFECTIVO;
        this.valorDocumento = valor;
    }

    public static DocPagoEfectivoEnhanced enhance(DocPagoEfectivo docPagoEfectivo) {
        return (DocPagoEfectivoEnhanced) Enhanced.enhance(docPagoEfectivo);
    }
}
