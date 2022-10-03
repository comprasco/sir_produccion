package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * en Efectivo.
 * @author eacosta
 */
public class DocPagoTimbreConstanciaLiquidacionCorreccion extends DocumentoPagoCorreccion
    implements TransferObject {
    /** Número o serial de timbre del banco*/
    private String numeroTimbre;
    private static final long serialVersionUID = 1L;
    /**
     * Fecha
     */
    private String fecha = "";

    /**  Constructor por defecto*/
    public DocPagoTimbreConstanciaLiquidacionCorreccion() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en efectivo.
     * @param valor
     * @param numTimbre
     * @param fecha
     */
    public DocPagoTimbreConstanciaLiquidacionCorreccion(double valor, String numTimbre,
        String fecha) {
        this.tipoPago = DocumentoPagoCorreccion.TIMBRE_CONSTANCIA_LIQUIDACION;
        this.valorDocumento = valor;
        this.numeroTimbre = numTimbre;
        this.fecha = fecha;
    }

    /**
     * Obtener el atributo fecha
     *
     * @return Retorna el atributo fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Actualizar el valor del atributo fecha
     * @param fecha El nuevo valor del atributo fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtener el atributo numeroTimbre
     *
     * @return Retorna el atributo numeroTimbre
     */
    public String getNumeroTimbre() {
        return numeroTimbre;
    }

    /**
     * Actualizar el valor del atributo numeroTimbre
     * @param numeroTimbre El nuevo valor del atributo numeroTimbre.
     */
    public void setNumeroTimbre(String numeroTimbre) {
        this.numeroTimbre = numeroTimbre;
    }
}
