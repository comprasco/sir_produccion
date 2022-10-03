package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoConvenio;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado en Efectivo.
 *
 * @author eacosta
 */
public class DocPagoConvenioEnhanced extends DocumentoPagoEnhanced {

    public DocPagoConvenioEnhanced() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en
     * efectivo.
     *
     * @param valor
     */
    public DocPagoConvenioEnhanced(double valor) {
        this.tipoPago = DocumentoPagoEnhanced.PAGO_CONVENIO;
        this.valorDocumento = valor;
    }    

    public static DocPagoConvenioEnhanced enhance(DocPagoConvenio docPagoConvenio) {
        return (DocPagoConvenioEnhanced) Enhanced.enhance(docPagoConvenio);
    }
}
