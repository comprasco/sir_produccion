package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoEfectivoCorreccion;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado en Efectivo.
 *
 * @author eacosta
 */
public class DocPagoEfectivoCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced {


    public DocPagoEfectivoCorreccionEnhanced() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en
     * efectivo.
     *
     * @param valor
     */
    public DocPagoEfectivoCorreccionEnhanced(double valor) {
        this.tipoPago = DocumentoPagoCorreccionEnhanced.PAGO_EFECTIVO;
        this.valorDocumento = valor;
    }

    public static DocPagoEfectivoCorreccionEnhanced enhance(DocPagoEfectivoCorreccion docPagoEfectivo) {
        return (DocPagoEfectivoCorreccionEnhanced) Enhanced.enhance(docPagoEfectivo);
    }
}
