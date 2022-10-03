/*
 * CAplicacionPago.java
 *
 * Created on 16 de noviembre de 2004, 14:52
 */

package gov.sir.core.negocio.modelo.constantes;

/**
 * Constantes de aplicaciones de pago
 * @author  dcantor
 */
public class CAplicacionPago {
    
   public static final String TIPO_APLICACION_PAGO = "TIPO_APLICACION_PAGO";
   public static final String EFECTIVO = "EFECTIVO";
   public static final String CHEQUE = "CHEQUE";
   public static final String CONSIGNACION = "CONSIGNACION";
public static final String TRANSFERENCIA = "ABONO A CUENTA";
   public static final String VALOR_APLICADO = "AP_PAGO_VALOR_APLICADO";
   // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
   // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
   public static final String NOTA_DEBITO = "NOTA DEBITO";
}