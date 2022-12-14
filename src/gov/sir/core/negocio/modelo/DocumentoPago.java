package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;


/**
 * Clase abstracta que generaliza todas las formas de pago empleadas.
 * Identifica la forma de pago y el valor pagado.
 * Toda forma de pago o combinación de ellas, debe derivarse de esta clase.
 * @author eacosta
 */
public class DocumentoPago implements TransferObject{
    /**
     * Forma de pago en Efectivo
     */
    public static final String PAGO_EFECTIVO = "EFECTIVO";
    private static final long serialVersionUID = 1L;
    
    public static final String PAGO_GENERAL = "GENERAL";

    /**
     * Forma de pago por medio de Cheque
     */
    public static final String PAGO_CHEQUE = "CHEQUE";

    /**
     * Forma de pago por medio de Nota debito.
     * Creado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
     * Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
     */
    public static final String PAGO_NOTA_DEBITO = "NOTA DEBITO";

    /**
     * Forma de pago a través de consignación bancaria
     */
    public static final String PAGO_CONSIGNACION = "CONSIGNACIÓN";

    /**
     * Forma de pago a través de consignación bancaria
     */
    public static final String TIMBRE_CONSTANCIA_LIQUIDACION = "TIMBRE CONSTANCIA LIQUIDACION";

    /**
     * Forma de pago a través del convenio
     */
    public static final String PAGO_CONVENIO = "CONVENIO";

    /**
     * Forma de pago a través del turno padre asociado
     */
    public static final String PAGO_HEREDADO = "PAGO HEREDADO";

    /**
     * Forma de pago a través del sistema de pagos PSE de ACH (portal Internet)
     */
    public static final String PAGO_PSE = "PAGO ELECTRONICO PSE";

    /**
     * Identifica la forma de pago al que corresponde el actual pago.
     */
    protected String tipoPago = PAGO_EFECTIVO;
    
    protected String nombreCanal;

    /**
     * Contiene el valor pagado.
     */
    protected double valorDocumento;
    protected String idDocumentoPago;
    protected double saldoDocumento;  // Aplica para DocPagoCheque y DocPagoConsignacion
    protected double saldoDocumentoAnulacion;  // Aplica para DocPagoCheque y DocPagoConsignacion
    protected int estadocorreccion;
    private String circulo; 
    private Date fechaCreacion;
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
    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Variables usadas en la nueva forma de pago.
     */
    public static final String PAGO_ELECTRONICO_PSE = "PAGO ELECTRONICO PSE";
    public static final String PAGO_TARJETA_CREDITO = "TARJETA CREDITO";
    public static final String PAGO_TARJETA_DEBITO = "TARJETA DEBITO";

/** Constructor por defecto */
    public DocumentoPago()
    {

    }

    /** Retorna el valor total del documento
     * @return valorDocumento
     */
    public double getValorDocumento() {
        return valorDocumento;
    }

    /** Cambia el valor total del documento
     * @param l
     */
    public void setValorDocumento(double l) {
        valorDocumento = l;
    }

    /** Retorna la descripción del tipo de documento
     * @return tipoPago
     */
    public String getTipoPago() {
        return tipoPago;
    }

    /** Cambia la descripción del tipo de documento
     * @param b
     */
    public void setTipoPago(String s) {
        tipoPago = s;
    }

    /** Retorna el identificador del documento de pago
     * @return
     */
    public String getIdDocumentoPago() {
        return idDocumentoPago;
    }

    /** Cambia el identificador del documento de pago
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
    /** Retorna el saldo del documento
     * @return
     */
    public double getSaldoDocumento() {
        return saldoDocumento;
    }

	/** Cambia el saldo del documento
     * @param d
     */
    public void setSaldoDocumento(double d) {
        saldoDocumento = d;
    }

	/** Retorna el saldo del documento Anulacion
     * @return
     */
    public double getSaldoDocumentoAnulacion() {
        return saldoDocumentoAnulacion;
    }

	/** Cambia el saldo del documento Anulacion
     * @param d
     */
    public void setSaldoDocumentoAnulacion(double d) {
        saldoDocumentoAnulacion = d;
    }

	/** Retorna la fecha de creación del registro en la base de datos
     * @return fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

	/** Cambia la fecha de creación del registro en la base de datos
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
