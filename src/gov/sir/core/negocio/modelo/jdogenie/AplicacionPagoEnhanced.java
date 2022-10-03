/*
 * Created on 04-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.AplicacionPago;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AplicacionPagoEnhanced extends Enhanced {

    private double valorAplicado;
    /**
     * @Autor: Santiago Vásquez
     * @Change: 1242.VER DETALLES DE TURNO VISUALIZACION FORMAS PAGO
     */
    private double valorAplicadoAnulacion;
    private DocumentoPagoEnhanced documentoPago;
    private PagoEnhanced pago;
    private String idDocumentoPago; //pk
    private String idLiquidacion; //pk
    private String idSolicitud; //pk
    private String circulo;

    public AplicacionPagoEnhanced() {
    }

    /**
     * Constructor que inicializa las referencias al Documento de Pago empleado
     * en esta aplicación y el valor aplicado de dicho Documento.
     *
     * @param documento
     * @param valor
     */
    public AplicacionPagoEnhanced(DocumentoPagoEnhanced documentoPago, double valorAplicado) {
        this.documentoPago = documentoPago;
        this.valorAplicado = valorAplicado;
    }

    /**
     * Este método sobreescribe el método en Object y retorna true solo si el
     * valor pagado y el documento de pago son iguales.
     */
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof AplicacionPagoEnhanced)) {
            return false;
        }

        AplicacionPagoEnhanced p = (AplicacionPagoEnhanced) other;

        return documentoPago.equals(p.documentoPago);
    }

    /**
     * @return
     */
    public DocumentoPagoEnhanced getDocumentoPago() {
        return documentoPago;
    }

    /**
     * @return
     */
    public double getValorAplicado() {
        return valorAplicado;
    }

    /**
     * @param pago
     */
    public void setDocumentoPago(DocumentoPagoEnhanced pago) {
        documentoPago = pago;
        this.setIdDocumentoPago(pago.getIdDocumentoPago());
    }

    /**
     * @param l
     */
    public void setValorAplicado(double l) {
        valorAplicado = l;
    }

    /**
     * @return
     */
    public PagoEnhanced getPago() {
        return pago;
    }

    /**
     * @param pago
     */
    public void setPago(PagoEnhanced pago) {
        this.pago = pago;
        this.setIdLiquidacion(pago.getIdLiquidacion());
        this.setIdSolicitud(pago.getIdSolicitud());
    }

    /**
     * @return
     */
    public String getIdDocumentoPago() {
        return idDocumentoPago;
    }

    /**
     * @param string
     */
    public void setIdDocumentoPago(String string) {
        idDocumentoPago = string;
    }

    /**
     * @return
     */
    public String getIdLiquidacion() {
        return idLiquidacion;
    }

    /**
     * @return
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param string
     */
    public void setIdLiquidacion(String string) {
        idLiquidacion = string;
    }

    /**
     * @param string
     */
    public void setIdSolicitud(String string) {
        idSolicitud = string;
    }

    public static AplicacionPagoEnhanced enhance(AplicacionPago aplicacionPago) {
        return (AplicacionPagoEnhanced) Enhanced.enhance(aplicacionPago);
    }

    public String getCirculo() {
        return circulo;
    }

    public void setCirculo(String circulo) {
        this.circulo = circulo;
    }

    /**
     * @Autor: Santiago Vásquez
     * @Change: 1242.VER DETALLES DE TURNO VISUALIZACION FORMAS PAGO
     */
    public double getValorAplicadoAnulacion() {
        return valorAplicadoAnulacion;
    }

    public void setValorAplicadoAnulacion(double valorAplicadoAnulacion) {
        this.valorAplicadoAnulacion = valorAplicadoAnulacion;
    }

}
