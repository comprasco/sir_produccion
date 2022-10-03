package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocumentoPago;

import java.util.Date;

/**
 * Clase abstracta que generaliza todas las formas de pago empleadas. Identifica
 * la forma de pago y el valor pagado. Toda forma de pago o combinación de
 * ellas, debe derivarse de esta clase.
 *
 * @author eacosta
 */
public class DocumentoPagoEnhanced extends Enhanced {

    /**
     * Forma de pago en Efectivo
     */
    public static final String PAGO_EFECTIVO = "EFECTIVO";

    /**
     * Forma de pago por medio de Cheque
     */
    public static final String PAGO_CHEQUE = "CHEQUE";

    /**
     * Forma de pago a través de consignación bancaria
     */
    public static final String PAGO_CONSIGNACION = "CONSIGNACIÓN";

    /**
     * Forma de pago por medio de Nota debito Creado por OSBERT LINERO - Iridium
     * Telecomunicaciones e informática Ltda. Cambio para agregar nota débito
     * requerimiento 108 - Incidencia Mantis 02360.
     */
    public static final String PAGO_NOTA_DEBITO = "NOTA DEBITO";

    /**
     * Forma de pago a través de consignación bancaria
     */
    public static final String TIMBRE_CONSTANCIA_LIQUIDACION = "TIMBRE CONSTANCIA LIQUIDACION";

    /**
     * Forma de pago a través del turno padre asociado
     */
    public static final String PAGO_HEREDADO = "PAGO HEREDADO";

    /**
     * Forma de pago a través del sistema de pagos PSE de ACH (portal Internet)
     */
    public static final String PAGO_PSE = "PAGO ELECTRONICO PSE";

    /**
     * Forma de pago a través del convenio
     */
    public static final String PAGO_CONVENIO = "CONVENIO";

    /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Variables usadas en la nueva forma de pago.
     */
    public static final String PAGO_TARJETA_CREDITO = "TARJETA CREDITO";
    public static final String PAGO_TARJETA_DEBITO = "TARJETA DEBITO";

    /**
     * Identifica la forma de pago al que corresponde el actual pago.
     */
    protected String tipoPago = PAGO_EFECTIVO;
    
    protected String nombreCanal;

    private Date fechaCreacion;

    /**
     * Contiene el valor pagado.
     */
    protected double valorDocumento;
    protected String idDocumentoPago;
    protected double saldoDocumento;  // Aplica para DocPagoCheque y DocPagoConsignacion
    protected double saldoDocumentoAnulacion;  // Aplica para DocPagoCheque y DocPagoConsignacion
    private String circulo;
    protected int estadocorreccion;
    private String consignacion_ant;
    private String tarjeta_ant;
    private String aprobacion_ant;

    public String getConsignacion_ant() {
        return consignacion_ant;
    }

    public void setConsignacion_ant(String consignacion_ant) {
        this.consignacion_ant = consignacion_ant;
    }

    public String getTarjeta_ant() {
        return tarjeta_ant;
    }

    public void setTarjeta_ant(String tarjeta_ant) {
        this.tarjeta_ant = tarjeta_ant;
    }

    public String getAprobacion_ant() {
        return aprobacion_ant;
    }

    public void setAprobacion_ant(String aprobacion_ant) {
        this.aprobacion_ant = aprobacion_ant;
    }

    public DocumentoPagoEnhanced() {

    }

    /**
     * @return
     */
    public double getValorDocumento() {
        return valorDocumento;
    }

    public static DocumentoPagoEnhanced enhance(DocumentoPago documento) {
        return (DocumentoPagoEnhanced) Enhanced.enhance(documento);
    }

    /**
     * @param l
     */
    public void setValorDocumento(double l) {
        valorDocumento = l;
    }

    /**
     * @return
     */
    public String getTipoPago() {
        return tipoPago;
    }

    /**
     * @param b
     */
    public void setTipoPago(String s) {
        tipoPago = s;
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
    
    public int getEstadocorreccion() {
        return estadocorreccion;
    }

    public void setEstadocorreccion(int estadocorreccion) {
        this.estadocorreccion = estadocorreccion;
    }
    /**
     * @return
     */
    public double getSaldoDocumento() {
        return saldoDocumento;
    }

    /**
     * @param d
     */
    public void setSaldoDocumento(double d) {
        saldoDocumento = d;
    }

    /**
     * @return
     */
    public double getSaldoDocumentoAnulacion() {
        return saldoDocumentoAnulacion;
    }

    /**
     * @param d
     */
    public void setSaldoDocumentoAnulacion(double d) {
        saldoDocumentoAnulacion = d;
    }

    /**
     * @return
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param date
     */
    public void setFechaCreacion(Date date) {
        fechaCreacion = date;
    }

    public String getCirculo() {
        return circulo;
    }

    public void setCirculo(String circulo) {
        this.circulo = circulo;
    }
    
        public String getNombreCanal() {
        return nombreCanal;
    }

    public void setNombreCanal(String nombreCanal) {
        this.nombreCanal = nombreCanal;
    }
    
}
