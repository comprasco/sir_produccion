package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * en Efectivo.
 * @author eacosta
 */
public class DocPagoTimbreConstanciaLiquidacion extends DocumentoPago
    implements TransferObject {
    /** Número o serial de timbre del banco*/
    private String numeroTimbre;
    private static final long serialVersionUID = 1L;
    /**
     * Fecha
     */
    private String fecha = "";

    /**  Constructor por defecto*/
    public DocPagoTimbreConstanciaLiquidacion() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado en efectivo.
     * @param valor
     * @param numTimbre
     * @param fecha
     */
    public DocPagoTimbreConstanciaLiquidacion(double valor, String numTimbre,
        String fecha) {
        this.tipoPago = DocumentoPago.TIMBRE_CONSTANCIA_LIQUIDACION;
        this.valorDocumento = valor;
        this.numeroTimbre = numTimbre;
        this.fecha = fecha;
    }
/**
     * Constructor que especifica los atributos de un pago para correccion realizado en efectivo.
     * @param valor
     * @param numTimbre
     * @param fecha
     */
    public DocPagoTimbreConstanciaLiquidacion(double valor, String numTimbre) {
        this.tipoPago = DocumentoPago.TIMBRE_CONSTANCIA_LIQUIDACION;
        this.valorDocumento = valor;
        this.numeroTimbre = numTimbre;
        
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
