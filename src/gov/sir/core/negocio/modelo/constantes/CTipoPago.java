package gov.sir.core.negocio.modelo.constantes;


/**
 * @author jmendez
 *
 */
public class CTipoPago {
    /** Constante que define el identificador de tipo de pago */
    public static final String ID_TIPO_PAGO = "ID_TIPO_PAGO";

    /** Constante que define la forma de pago efectivo */
    public static final long PAGO_EFECTIVO = 1;

    /** Constante que define la forma de pago consignación  */
    public static final long PAGO_CONSIGNACION = 2;

    /** Constante que define la forma de pago cheque  */
    public static final long PAGO_CHEQUE = 3;

    /** Constante que define la forma de pago cheque gerencia  */
    public static final long PAGO_CHEQUE_GERENCIA = 4;

    /** Constante que define la forma de pago cheque postfechado  */
    public static final long PAGO_CHEQUE_POSFECHADO = 5;

    /** Constante que define la forma de pago convenio  */
    public static final long PAGO_CONVENIO = 6;

    /** Constante que define la forma de pago timbre constancia liquidación  */
    public static final long PAGO_TIMBRE_CONSTANCIA_LIQUIDACION = 7;
    
    /**
     * @autor HGOMEZ 
     * @mantis 12422 
     * @Requerimiento 049_453 
     * @descripcion Se declaran las constantes para pago electronico pse, 
     * tarjeta credido y tarjeta debito.
     */
    /** Constante que define la forma de pago electronico pse  */
    public static final long PAGO_ELECTRONICO_PSE = 9;
    
    /** Constante que define la forma de pago tarjeta credito  */
    public static final long PAGO_TARJETA_CREDITO = 11;
    
    /** Constante que define la forma de pago tarjeta debito  */
    public static final long PAGO_TARJETA_DEBITO = 12;
    
    
    public static final String ID_REL_FORMA_CAMPOS = "ID_REL_FORMA_CAMPOS";
    
    public static final String ID_CAMPO_CAPTURA = "ID_CAMPO_CAPTURA";
    
    public static final String ESTADO_CAMPO_CAPTURA = "ESTADO_CAMPO_CAPTURA";
    
    /** Constante que define el nombre de tipo de pago */
    public static final String NOMBRE_TIPO_PAGO = "NOMBRE_TIPO_PAGO";
    
    public static final String CANAL_SIR = "CANAL_SIR";
}
