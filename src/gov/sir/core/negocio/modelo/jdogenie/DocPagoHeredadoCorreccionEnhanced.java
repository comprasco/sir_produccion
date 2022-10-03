package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoHeredadoCorreccion;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado a través de su turno padre (certificados masivos, turno registro
 * con certificados asociados)
 *
 * @author fceballos
 */
public class DocPagoHeredadoCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced {

    public DocPagoHeredadoCorreccionEnhanced() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en
     * efectivo.
     *
     * @param valor
     */
    public DocPagoHeredadoCorreccionEnhanced(double valor) {
        this.tipoPago = DocumentoPagoCorreccionEnhanced.PAGO_HEREDADO;
        this.valorDocumento = valor;
    }

    public static DocPagoHeredadoCorreccionEnhanced enhance(DocPagoHeredadoCorreccion docPagoHeredado) {
        return (DocPagoHeredadoCorreccionEnhanced) Enhanced.enhance(docPagoHeredado);
    }

}
