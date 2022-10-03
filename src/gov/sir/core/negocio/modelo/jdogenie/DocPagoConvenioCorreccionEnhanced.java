package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoConvenioCorreccion;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado en Efectivo.
 *
 * @author eacosta
 */
public class DocPagoConvenioCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced {

    public DocPagoConvenioCorreccionEnhanced() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en
     * efectivo.
     *
     * @param valor
     */
    public DocPagoConvenioCorreccionEnhanced(double valor) {
        this.tipoPago = DocumentoPagoEnhanced.PAGO_CONVENIO;
        this.valorDocumento = valor;
    }    

    public static DocPagoConvenioCorreccionEnhanced enhance(DocPagoConvenioCorreccion docPagoConvenio) {
        return (DocPagoConvenioCorreccionEnhanced) Enhanced.enhance(docPagoConvenio);
    }
}
