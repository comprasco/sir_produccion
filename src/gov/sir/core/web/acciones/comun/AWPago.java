package gov.sir.core.web.acciones.comun;

import co.com.iridium.generalSIR.comun.modelo.Transaccion;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import gov.sir.core.eventos.comun.EvnPago;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion;

/**
 * @autor : HGOMEZ
 * @mantis : 12422
 * @Requerimiento : 049_453
 * @descripcion : Nuevos imports para dar solución al caso.
 */
import gov.sir.core.negocio.modelo.BancoFranquicia;
import gov.sir.core.negocio.modelo.CamposCaptura;
import gov.sir.core.negocio.modelo.CanalesRecaudo;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.DocPagoTarjetaCredito;
import gov.sir.core.negocio.modelo.DocPagoTarjetaDebito;
import gov.sir.core.negocio.modelo.DocPagoElectronicoPSE;
import gov.sir.core.negocio.modelo.DocPagoConvenio;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;

import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CAlcanceGeografico;
import gov.sir.core.negocio.modelo.constantes.CCertificado;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CLiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSolicitudAsociada;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CSubtipoAtencion;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado;
import gov.sir.core.web.acciones.certificadosmasivos.AWCertificadoMasivo;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.AplicacionPagoNoAdicionadaException;
import gov.sir.core.web.acciones.excepciones.PagoNoProcesadoException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosRegistroPagoException;
import gov.sir.core.web.acciones.registro.AWLiquidacionRegistro;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Esta accion web genera eventos basicos asociados a una radicación. Los
 * eventos básicos pueden ser de creación, consulta y actualización de una
 * radicación en curso. REQUERIMIENTOS ASOCIADOS: 20. Genera certificado
 * asociado 70. Revisa detalles certificado
 *
 * @author mmunoz
 */
public class AWPago extends SoporteAccionWeb {

    /*
             * @autor         : HGOMEZ 
             * @mantis        : 12422 
             * @Requerimiento : 049_453 
             * @descripcion   : Constante que identifica el campo del jsp donde se solicita 
             * la forma de pago.
     */
    public final static String FORMA_PAGO_PAGOS = "FORMA_PAGO_PAGOS";
    /**
     * Constante que identifica el campo del jsp donde se solicita especificar
     * si la la forma de pago es nueva o ya se encuentra registrada
     */
    public final static String NUEVA_FORMA_PAGO_PAGOS = "NUEVA_FORMA_PAGO_PAGOS";
    /**
     * Constante que identifica el campo del jsp donde se solicita el banco o
     * franquicia de pago
     */
    public final static String BANCO_FRANQUICIA_PAGOS = "BANCO_FRANQUICIA_PAGOS";
    /**
     * Constante que identifica el campo del jsp donde se solicita el número
     * documento de pago
     */
    public final static String NUMERO_DOCUMENTO_PAGOS = "NUMERO_DOCUMENTO_PAGOS";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor del
     * documento de pago
     */
    public final static String VALOR_DOCUMENTO_PAGOS = "VALOR_DOCUMENTO_PAGOS";
    /**
     * Constante que identifica el campo del jsp donde se solicita el número
     * aprobacion de pago
     */
    public final static String NUMERO_APROBACION_PAGOS = "NUMERO_APROBACION_PAGOS";
    /**
     * Constante que identifica el campo del jsp donde se solicita la fecha
     * documento de pago
     */
    public final static String FECHA_DOCUMENTO_PAGOS = "FECHA_DOCUMENTO_PAGOS";

    /**
     * Constante que identifica el campo del jsp donde se solicita la fecha
     * documento de pago req canales de recaudo
     */
    public final static String FECHA_DOCU = "FECHA_DOCU";

    /**
     * Constante que identifica el campo del jsp donde se solicita la cuenta
     * destino de pago req canales de recaudo
     */
    public final static String CUENTA_DESTINO = "CUENTA_DESTINO";

    /**
     * Constante que identifica el campo del jsp donde se solicita el nro
     * documento de pago req canales de recaudo
     */
    public final static String NO_DOC_PAGO = "NO_DOC_PAGO";

    /**
     * Constante que identifica el campo del jsp donde se solicita el nro de
     * aprobacion req canales de recaudo
     */
    public final static String NO_APROBACION = "NO_APROBACION";

    /**
     * Constante que identifica el campo del jsp donde se solicita el nro de pin
     * req canales de recaudo
     */
    public final static String NO_PIN = "NO_PIN";

    /**
     * Constante que identifica el campo del jsp donde se solicita el nro de nir
     * req canales de recaudo
     */
    public final static String NO_NIR = "NO_NIR";

    /**
     * Constante que identifica el campo del jsp donde se solicita el nro de cus
     * req canales de recaudo
     */
    public final static String NO_CUS = "NO_CUS";

    /**
     * Constante que identifica el campo del jsp donde se solicita el valor
     * liquidado req canales de recaudo
     */
    public final static String VLR_LIQUIDADO = "VLR_LIQUIDADO";

    /**
     * Constante que identifica el campo del jsp donde se solicita el valor
     * documento pago req canales de recaudo
     */
    public final static String VLR_DOC_PAGO = "VLR_DOC_PAGO";

    /**
     * Constante que identifica el campo del jsp donde se solicita el valor
     * documento pago req canales de recaudo
     */
    public final static String NO_CONSIGNACION = "NO_CONSIGNACION";

    /**
     * Constante que identifica el campo del jsp donde se solicita el valor a
     * pagar
     */
    public final static String VALOR_PAGAR_PAGOS = "VALOR_PAGAR_PAGOS";
    /**
     * Constante que identifica que se desea buscar una forma de pago ya
     * existente.
     */
    public final static String BUSCAR_FORMA_DE_PAGO = "BUSCAR_FORMA_DE_PAGO";
    /**
     * ******************** FORMA PAGO REGISTRADA ************************
     */
    /**
     * Constante que identifica el campo del jsp donde se solicita la forma de
     * pago
     */
    public final static String FORMA_PAGO_PAGOS_YA_REGISTRADA = "FORMA_PAGO_PAGOS_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita especificar
     * si la la forma de pago es nueva o ya se encuentra registrada
     */
    public final static String NUEVA_FORMA_PAGO_PAGOS_YA_REGISTRADA = "NUEVA_FORMA_PAGO_PAGOS_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el banco o
     * franquicia de pago
     */
    public final static String BANCO_FRANQUICIA_PAGOS_YA_REGISTRADA = "BANCO_FRANQUICIA_PAGOS_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el número
     * documento de pago
     */
    public final static String NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA = "NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor del
     * documento de pago
     */
    public final static String VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA = "VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el número
     * aprobacion de pago
     */
    public final static String NUMERO_APROBACION_PAGOS_YA_REGISTRADA = "NUMERO_APROBACION_PAGOS_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita la fecha
     * documento de pago
     */
    public final static String FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA = "FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor a
     * pagar
     */
    public final static String VALOR_PAGAR_PAGOS_YA_REGISTRADA = "VALOR_PAGAR_PAGOS_YA_REGISTRADA";
    /**
     * Constante que identifica que se desea buscar una forma de pago ya
     * existente.
     */
    public final static String BUSCAR_FORMA_DE_PAGO_YA_REGISTRADA = "BUSCAR_FORMA_DE_PAGO_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el numero de
     * la consignacion
     */
    public final static String NUMERO_FORMA_PAGO_YA_REGISTRADA = "NUMERO_FORMA_PAGO_YA_REGISTRADA";

    public final static String SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA = "SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA";

    public static final String WF_LINK_FOTOCOPIAS_PAGOFOTOCOPIAS2FOTOCOPIA = "CONFIRMAR";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor del
     * pago en efectivo
     */
    public final static String VALOR_EFECTIVO = "VALOR_EFECTIVO";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor de
     * una consignacion
     */
    public final static String VALOR_CONSIGNACION = "VALOR_CONSIGNACION";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor de
     * una consignacion
     */
    public final static String SALDO_CONSIGNACION = "SALDO_CONSIGNACION";
    /**
     * Constante que identifica el campo del jsp donde se solicita el numero de
     * la consignacion
     */
    public final static String NUM_CONSIGNACION = "NUM_CONSIGNACION";
    /**
     * Constante que identifica el campo del jsp donde se solicita la fecha de
     * la consignacion
     */
    public final static String FECHA_CONSIGNACION = "FECHA_CONSIGNACION";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor a
     * aplicar
     */
    public final static String VALOR_APLICADO = "VALOR_APLICADO";
    /**
     * ******************** CONSIGNACION REGISTRADA ************************
     */
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor de
     * una consignacion
     */
    public final static String VALOR_CONSIGNACION_YA_REGISTRADA = "VALOR_CONSIGNACION_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se desplegará el saldo
     * del documento
     */
    public final static String SALDO_CONSIGNACION_YA_REGISTRADA = "SALDO_CONSIGNACION_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el numero de
     * la consignacion
     */
    public final static String NUM_CONSIGNACION_YA_REGISTRADA = "NUM_CONSIGNACION_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita la fecha de
     * la consignacion
     */
    public final static String FECHA_CONSIGNACION_YA_REGISTRADA = "FECHA_CONSIGNACION_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor a
     * aplicar
     */
    public final static String VALOR_APLICADO_YA_REGISTRADA = "VALOR_APLICADO_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el numero de
     * la sucursal del banco
     */
    public final static String COD_SUCURSAL_BANCO_YA_REGISTRADA = "COD_SUCURSAL_BANCO_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el codigo del
     * banco
     */
    public final static String COD_BANCO_YA_REGISTRADA = "COD_BANCO_YA_REGISTRADA";

    /*
             * @autor         : HGOMEZ 
             * @mantis        : 12422 
             * @Requerimiento : 049_453 
             * @descripcion   : Constante que identifica el campo del jsp donde se solicita el codigo del banco.
     */
    public final static String COD_FRANQUICIA_YA_REGISTRADA = "COD_FRANQUICIA_YA_REGISTRADA";

    /**
     * Constante que identifica si la consignacion es nueva o no
     */
    public final static String CONSIGNACION_NUEVA = "CONSIGNACION_NUEVA";
    /**
     * Constante que identifica si la consignacion es nueva o no
     */
    public final static String CONSIGNACION_NUEVA_FORMA_PAGO = "CONSIGNACION_NUEVA_FORMA_PAGO";
    public final static String CHEQUE_NUEVA_FORMA_PAGO = "CHEQUE_NUEVA_FORMA_PAGO";
    public final static String LLAVE_GENERAL_APLICACION = "LLAVE_GENERAL_APLICACION";
    /**
     * ******************** END CONSIGNACION REGISTRADA
     * ************************
     */
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor de
     * un cheque
     */
    public final static String VALOR_CHEQUE = "VALOR_CHEQUE";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor de
     * un cheque
     */
    public final static String VALOR_CHEQUE_YA_REGISTRADA = "VALOR_CHEQUE_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el valor de
     * un cheque
     */
    public final static String SALDO_CHEQUE_YA_REGISTRADA = "SALDO_CHEQUE_YA_REGISTRADA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el numero del
     * cheque
     */
    public final static String NUM_CHEQUE = "NUM_CHEQUE";
    /**
     * Constante que identifica el campo del jsp donde se solicita el numero del
     * cheque
     */
    public final static String NUM_CHEQUE_YA_REGISTRADA = "NUM_CHEQUE_YA_REGISTRADA ";
    /**
     * Constante que identifica el campo del jsp donde se solicita el numero de
     * la cuenta del ciudadano
     */
    public final static String NUM_CUENTA = "NUM_CUENTA";
    /**
     * Constante que identifica el campo del jsp donde se solicita el numero de
     * la sucursal del banco
     */
    public final static String COD_SUCURSAL_BANCO = "COD_SUCURSAL_BANCO";
    /**
     * Constante que identifica el campo del jsp donde se solicita el codigo del
     * banco
     */
    public final static String COD_BANCO = "COD_BANCO";

    /**
     * Constante que identifica el campo del jsp donde se solicita el codigo de
     * la cuenta bancaria
     */
    public final static String ID_CUENTA_BANCARIA = "ID_CUENTA_BANCARIA";


    /*
             * @autor         : HGOMEZ 
             * @mantis        : 12422 
             * @Requerimiento : 049_453 
             * @descripcion   : Constante que identifica el campo del jsp donde 
             * se solicita el código del banco franquicia y código franquicia
             * la forma de pago.
     */
    public final static String COD_BANCO_FRANQUICIA = "COD_BANCO_FRANQUICIA";
    public final static String COD_FRANQUICIA = "COD_FRANQUICIA";

    public final static String BANCO_FRANQUICIA = "BANCO_FRANQUICIA";

    /**
     * Constante que identifica el campo del jsp donde se solicita el numero de
     * la aplicacion a eliminar
     */
    public static String NUMERO_APLICACION = "NUMERO_APLICACION";
    /**
     * Constante que identifica el campo donde se tiene la forma de pago
     */
    public static String FORMA_PAGO = "FORMA_PAGO";
    /**
     * Constante que identifica el campo del jsp donde se solicita la fecha del
     * cheque
     */
    public final static String FECHA_CHEQUE = "FECHA_CHEQUE";
    /**
     * Constante que identifica el campo del jsp donde se solicita la fecha del
     * cheque
     */
    public final static String FECHA_CHEQUE_YA_REGISTRADA = "FECHA_CHEQUE_YA_REGISTRADA ";
    /**
     * Constante que identifica el campo del jsp donde se indica si es un nuevo
     * cheque
     */
    public final static String NUEVO_CHEQUE = "NUEVO_CHEQUE";

    /**
     * Constante que identifica el campo del jsp donde se guardan las formas de
     * pago para cada canal
     */
    public final static String FORMAS_PAGO_MAP = "FORMAS_PAGO_MAP";

    /**
     * Constante que identifica el campo oculto que indica si se debe asignar
     * estación
     */
    public final static String ASIGNAR_ESTACION = "ASIGNAR_ESTACION";
    /**
     * Constante que identifica que se desea verificar el pago hecho por el
     * ciudadano frente a la liquidación
     */
    public final static String VALIDAR = "VALIDAR";
    /**
     * Constante que identifica que se desea confirmar la solicitud de un
     * certificado
     */
    public final static String PROCESAR = "PROCESAR";
    /**
     * Constante que identifica que se desea procesar el pago y seguir a radicar
     * el turno de registro.
     */
    public final static String PROCESAR_REGISTRO_CONTINUAR = "PROCESAR_REGISTRO_CONTINUAR";
    /**
     * Constante que identifica que se desea añadir una nueva aplicacíon a la
     * lista de aplicaciones de cheques o consignaciones
     */
    public final static String ADICIONAR_APLICACION = "ADICIONAR_APLICACION";
    /**
     * Constante que identifica que se desea añadir una nueva aplicacíon a la
     * lista de aplicaciones de cheques o consignaciones
     */
    public final static String ADICIONAR_APLICACION_VALIDACION = "ADICIONAR_APLICACION_VALIDACION";
    /**
     * Constante que identifica que se desea eliminar una aplicacion de la lista
     * de aplicaciones de cheques o consignaciones
     */
    public final static String ELIMINAR_APLICACION = "ELIMINAR_APLICACION";
    /**
     * Constante que identifica que se desea eliminar una aplicacion de la lista
     * de aplicaciones de cheques o consignaciones en la pantalla de validacion
     */
    public final static String ELIMINAR_APLICACION_VALIDACION = "ELIMINAR_APLICACION_VALIDACION";
    /**
     * Constante que identifica que se desea buscar una consignacion ya
     * existente.
     */
    public final static String BUSCAR_CONSIGNACION = "BUSCAR_CONSIGNACION";
    
    public final static String BUSCAR_GENERAL = "BUSCAR_GENERAL";
    /*
             * @autor         : HGOMEZ 
             * @mantis        : 12422 
             * @Requerimiento : 049_453 
             * @descripcion   : Constante necesarias para dar solución al caso
             * la forma de pago.
     */
    public final static String BUSCAR_PAGO_TARJETA_CREDITO = "BUSCAR_PAGO_TARJETA_CREDITO";
    public final static String BUSCAR_PAGO_TARJETA_DEBITO = "BUSCAR_PAGO_TARJETA_DEBITO";
    public final static String BUSCAR_PAGO_PSE = "BUSCAR_PAGO_PSE";
    public final static String CONF_TIPO_BANCO_FRANQUICIA = "CONF_TIPO_BANCO_FRANQUICIA";
    public final static String TIPO_BANCO_FRANQUICIA = "TIPO_BANCO_FRANQUICIA";
    public final static String FORMA_TIPOS_PAGOS = "FORMA_TIPOS_PAGOS";
    public final static String CANALES_RECAUDO = "CANALES_RECAUDO";
    private final String APLICACION_PAGO_CONVENIO = "CONVENIO";
    private final String APLICACION_PAGO_ELECTRONICO_PSE = "PAGO_ELECTRONICO_PSE";
    public final static String CORR_CANAL_RECAUDO = "CORR_CANAL_RECAUDO";
    private final String APLICACION_PAGO_TARJETA_CREDITO = "TARJETA_CREDITO";
    private final String APLICACION_PAGO_TARJETA_DEBITO = "TARJETA_DEBITO";
    public final static String TARJETA_CREDITO_PAGO_NUEVO = "TARJETA_CREDITO_PAGO_NUEVO";
    public final static String TARJETA_DEBITO_PAGO_NUEVO = "TARJETA_DEBITO_PAGO_NUEVO";
    public final static String CONVENIO_PAGO_NUEVO = "CONVENIO_PAGO_NUEVO";
    public final static String PAGO_ELECTRONICO_PSE_PAGO_NUEVO = "PAGO_ELECTRONICO_PSE_PAGO_NUEVO";
    public final static String LISTA_BANCOS_FRANQUICIAS = "LISTA_BANCOS_FRANQUICIAS";
    //public final static String BUSCAR_PAGO_CONVENIO = "BUSCAR_PAGO_CONVENIO";
    /**
     * Constante que identifica que se desea buscar una consignacion ya
     * existente.
     */
    public final static String BUSCAR_CHEQUE = "BUSCAR_CHEQUE";
    /**
     * Constante que indica que el tipo de la aplicacion es efectivo
     */
    private final String APLICACION_PAGO_EFECTIVO = "EFECTIVO";
    /**
     * Constante que indica que el tipo de la aplicacion es timbre
     */
    private final String APLICACION_PAGO_TIMBRE_BANCO = "TIMBRE_BANCO";
    /**
     * Constante que indica que el tipo de la aplicacion es un cheque
     */
    private final String APLICACION_PAGO_CHEQUE = "CHEQUE";
    /**
     * Constante que indica que el tipo de aplicacion es consignacion
     */
    private final String APLICACION_PAGO_CONSIGNACION = "CONSIGNACION";

    public final static String CANALES_X_CIRCULO = "CANALES_X_CIRCULO";

    public final static String CIRCULO = "CIRCULO";

    public final static String CARGAR_FORMAS_PAGO = "CARGAR_FORMAS_PAGO";

    public final static String CARGAR_CAMPOS_CAPTURA_X_FORMA = "CARGAR_CAMPOS_CAPTURA_X_FORMA";

    public final static String CUENTAS_X_CIRCULO_BANCO = "CUENTAS_X_CIRCULO_BANCO";

    public final static String CARGAR_CAMPOS_CAPTURA = "CARGAR_CAMPOS_CAPTURA";

    /**
     * Constante para avanzar un turno a la fase de pagos por mayor valor.
     */
    public final String RESULTADO_MAYOR_VALOR = "EXITO";
    public final static String REMOVER_INFO = "REMOVER_INFO";
    /**
     * Variable donde se guarda la accion enviada en el request
     */
    private String accion;

    /**
     * Crea una nueva instancia de AWPago
     */
    public AWPago() {
    }

    /**
     * Método principal de esta acción web. Aqui se realiza toda la lógica
     * requerida de validación y de generación de eventos de negocio.
     *
     * @param request trae la informacion del formulario
     * @throws AccionWebException cuando ocurre un error
     * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier
     * otro caso
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);
        /*
         * AHERRENO 14/05/2012 REQ 176_151 TRANSACCION
         */
            request.getSession().setAttribute("TIEMPO_INICIO_TRANSACCION", new Date());
        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        if (accion.equals(VALIDAR)) {
            return validarPago(request);
        } else if (accion.equals(PROCESAR)) {
            return procesarPago(request);
        } else if (accion.equals(PROCESAR_REGISTRO_CONTINUAR)) {
            return procesarPago(request);
        } else if (accion.equals(ADICIONAR_APLICACION)) {
            return adicionarAplicacion(request);
        } else if (accion.equals(CARGAR_FORMAS_PAGO)) {
            return cargarFormasPago(request);
        } else if (accion.equals(CARGAR_CAMPOS_CAPTURA_X_FORMA)) {
            return cargarCampoCapturaXFormaPago(request);
        } else if (accion.equals(CUENTAS_X_CIRCULO_BANCO)) {
            return cuentasBancariasXCirculo(request);
        } else if (accion.equals(ADICIONAR_APLICACION_VALIDACION)) {
            return adicionarAplicacionValidacion(request);
        } else if (accion.equals(ELIMINAR_APLICACION)) {
            return eliminarAplicacion(request);
        } else if (accion.equals(ELIMINAR_APLICACION_VALIDACION)) {
            return eliminarAplicacionValidacion(request);
        } else if (accion.equals(BUSCAR_CONSIGNACION)) {
            return buscarConsignacion(request);
        } else if (accion.equals(BUSCAR_CHEQUE)) {
            return buscarCheque(request);
        } /*
             * @autor         : HGOMEZ 
             * @mantis        : 12422 
             * @Requerimiento : 049_453 
             * @descripcion   : Se valida tipo pagao tarjeta crédito y pse.
         */ else if (accion.equals(BUSCAR_PAGO_TARJETA_CREDITO)) {
            return buscarPagoTarjetaCredito(request);
        } else if (accion.equals(BUSCAR_PAGO_TARJETA_DEBITO)) {
            return buscarPagoTarjetaDebito(request);
        } else if (accion.equals(BUSCAR_PAGO_PSE)) {
            return buscarPagoPse(request);
        } else if (accion.equals(CONF_TIPO_BANCO_FRANQUICIA)) {
            return buscarPagoPse(request);
        } else if (accion.equals(AWLiquidacionRegistro.REMOVER_INFO)) {
            return removerInfo(request);
        } else if (accion.equals(BUSCAR_GENERAL)){
            return buscarGeneral(request);
        }else {
            throw new AccionInvalidaException("La accion " + accion
                    + " no es valida.");
        }
    }

    /**
     * Este método es usado para eliminar una aplicación de pago de la lista de
     * cheques o consignaciones actualmente configurada y redirigiar a la
     * pantalla de validacion de aplicaciones
     *
     * @param request
     * @return null
     * @throws AccionWebException
     */
    private Evento eliminarAplicacionValidacion(HttpServletRequest request)
            throws AccionWebException {
        request.getSession().removeAttribute(WebKeys.PAGO);
        eliminarAplicacion(request);
        request.getSession().setAttribute(WebKeys.PAGO, construirPago(request));

        return null;
    }

    /**
     * Este método es usado para adicionar una aplicación de pago sobre un
     * cheque o consignación a la lista de cheques o consignaciones y redirigiar
     * a la pantalla de validacion de aplicaciones
     *
     * @param request
     * @return null
     * @throws AccionWebException
     */
    private Evento adicionarAplicacionValidacion(HttpServletRequest request)
            throws AccionWebException {
        request.getSession().removeAttribute(WebKeys.PAGO);
        adicionarAplicacion(request);
        request.getSession().setAttribute(WebKeys.PAGO, construirPago(request));

        return null;
    }

    /**
     * Este método es usado para adicionar una aplicación de pago sobre un
     * cheque o consignación a la lista de cheques o consignaciones
     *
     * @param request
     * @return null
     * @throws AccionWebException
     */
    private Evento eliminarAplicacion(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        System.out.println("ELIMINANDO APLICACION SABADO 16");

        List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS);

        if (listaAplicaciones == null) {
            listaAplicaciones = new Vector();
        }

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(
                    AWPago.NUMERO_APLICACION));
            System.out.println("ELIMINANDO APLICACION NUMERO " + aplicacionNumero);
            listaAplicaciones.remove(aplicacionNumero);
            session.setAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS, listaAplicaciones);

        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número de aplicación a eliminar es inválido.");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (listaAplicaciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía.");
            }
            throw new ParametroInvalidoException("El índice de la aplicación a eliminar está fuera del rango.");
        }

        /*String formaPago = request.getParameter(AWPago.FORMA_PAGO);

        if (formaPago == null) {
            ValidacionParametrosException vpe = new ValidacionParametrosException();
            vpe.addError("La forma de pago no es válida.");
            throw vpe;
        }

        if (WebKeys.FORMA_PAGO_CHEQUE.equals(formaPago)) {
            List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);

            if (listaAplicaciones == null) {
                listaAplicaciones = new Vector();
            }

            try {
                int aplicacionNumero = Integer.parseInt(request.getParameter(
                        AWPago.NUMERO_APLICACION));
                listaAplicaciones.remove(aplicacionNumero);
                session.setAttribute(WebKeys.LISTA_CHEQUES, listaAplicaciones);
                List marcas = (List) session.getAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
                marcas.remove(aplicacionNumero);
                session.setAttribute(WebKeys.LISTA_CHEQUES_MARCAS, marcas);
            } catch (NumberFormatException e) {
                throw new ParametroInvalidoException("El número de aplicación a eliminar es inválido.");
            } catch (ArrayIndexOutOfBoundsException e) {
                if (listaAplicaciones.size() == 0) {
                    throw new ParametroInvalidoException("La lista es vacía.");
                }
                throw new ParametroInvalidoException("El índice de la aplicación a eliminar está fuera del rango.");
            }
        } else if (WebKeys.FORMA_PAGO_CONSIGNACION.equals(formaPago)) {
            List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);

            if (listaAplicaciones == null) {
                listaAplicaciones = new Vector();
            }

            try {
                int aplicacionNumero = Integer.parseInt(request.getParameter(
                        AWPago.NUMERO_APLICACION));
                listaAplicaciones.remove(aplicacionNumero);
                session.setAttribute(WebKeys.LISTA_CONSIGNACIONES,
                        listaAplicaciones);
                List marcas = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);
                marcas.remove(aplicacionNumero);
                session.setAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS, marcas);
            } catch (NumberFormatException e) {
                throw new ParametroInvalidoException("El número de aplicación a eliminar es inválido.");
            } catch (ArrayIndexOutOfBoundsException e) {
                if (listaAplicaciones.size() == 0) {
                    throw new ParametroInvalidoException("La lista es vacía.");
                }
                throw new ParametroInvalidoException("El índice de la aplicación a eliminar está fuera del rango.");
            }
        } else if (WebKeys.FORMA_PAGO_TARJETA_CREDITO.equals(formaPago)) {
            List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_TARJETA_CREDITO);

            if (listaAplicaciones == null) {
                listaAplicaciones = new Vector();
            }

            try {
                int aplicacionNumero = Integer.parseInt(request.getParameter(AWPago.NUMERO_APLICACION));
                listaAplicaciones.remove(aplicacionNumero);
                session.setAttribute(WebKeys.LISTA_TARJETA_CREDITO, listaAplicaciones);
                List marcas = (List) session.getAttribute(WebKeys.LISTA_TARJETA_CREDITO_MARCAS);
                marcas.remove(aplicacionNumero);
                session.setAttribute(WebKeys.LISTA_TARJETA_CREDITO_MARCAS, marcas);
            } catch (NumberFormatException e) {
                throw new ParametroInvalidoException("El número de aplicación a eliminar es inválido.");
            } catch (ArrayIndexOutOfBoundsException e) {
                if (listaAplicaciones.size() == 0) {
                    throw new ParametroInvalidoException("La lista es vacía.");
                }
                throw new ParametroInvalidoException("El índice de la aplicación a eliminar está fuera del rango.");
            }
        } else if (WebKeys.FORMA_PAGO_TARJETA_DEBITO.equals(formaPago)) {
            List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_TARJETA_DEBITO);

            if (listaAplicaciones == null) {
                listaAplicaciones = new Vector();
            }

            try {
                int aplicacionNumero = Integer.parseInt(request.getParameter(AWPago.NUMERO_APLICACION));
                listaAplicaciones.remove(aplicacionNumero);
                session.setAttribute(WebKeys.LISTA_TARJETA_DEBITO, listaAplicaciones);
                List marcas = (List) session.getAttribute(WebKeys.LISTA_TARJETA_DEBITO_MARCAS);
                marcas.remove(aplicacionNumero);
                session.setAttribute(WebKeys.LISTA_TARJETA_DEBITO_MARCAS, marcas);
            } catch (NumberFormatException e) {
                throw new ParametroInvalidoException("El número de aplicación a eliminar es inválido.");
            } catch (ArrayIndexOutOfBoundsException e) {
                if (listaAplicaciones.size() == 0) {
                    throw new ParametroInvalidoException("La lista es vacía.");
                }
                throw new ParametroInvalidoException("El índice de la aplicación a eliminar está fuera del rango.");
            }
        } else if (WebKeys.FORMA_PAGO_CONVENIO.equals(formaPago)) {
            request.getSession().removeAttribute(WebKeys.APLICACION_CONVENIO);
        } else if (WebKeys.FORMA_PAGO_EFECTIVO.equals(formaPago)) {
            request.getSession().removeAttribute(WebKeys.APLICACION_EFECTIVO);
        } else if (WebKeys.FORMA_PAGO_PSE.equals(formaPago)) {
            request.getSession().removeAttribute(WebKeys.FORMA_PAGO_PSE);
        } else if (WebKeys.FORMA_PAGO_TIMBRE_BANCO.equals(formaPago)) {
            request.getSession().removeAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
        } else {
            ValidacionParametrosException vpe = new ValidacionParametrosException();
            vpe.addError("La forma de pago es inválida.");
            throw vpe;
        }  */
        return null;
    }

    /**
     * Este método es usado para eliminar una aplicación de pago de la lista de
     * cheques o consignaciones actualmente configurada
     *
     * @param request
     * @return null
     * @throws AccionWebException
     */
    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Se refactoriza el método para dar solución al requerimiento.
     */
    private Evento adicionarAplicacionOld(HttpServletRequest request)
            throws AccionWebException {
        String formaPago = request.getParameter(AWPago.FORMA_TIPOS_PAGOS);
        String fechaDocument = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS);

        /**
         * @author Cesar Ramírez
         * @change: 1954.VALIDACION.BANCO.FRANQUICIA.CONSIGNACION
         *
         */
        ValidacionParametrosException vpe = new ValidacionParametrosException();

        if (formaPago == null) {
            formaPago = request.getParameter(AWPago.FORMA_PAGO);
        }

        String aplicacionNueva = "";
        String nombreLista = "";
        HttpSession session = request.getSession();
        List listaAplicaciones = null;

        preservarInfo(request);

        int formaPagoEscogida = 0;

        if (WebKeys.FORMA_PAGO_CHEQUE.equals(formaPago)) {
            aplicacionNueva = APLICACION_PAGO_CHEQUE;
            nombreLista = WebKeys.LISTA_CHEQUES;
            listaAplicaciones = (List) session.getAttribute(nombreLista);
        } else if (WebKeys.FORMA_PAGO_CONSIGNACION.equals(formaPago)) {
            aplicacionNueva = APLICACION_PAGO_CONSIGNACION;
            nombreLista = WebKeys.LISTA_CONSIGNACIONES;
            listaAplicaciones = (List) session.getAttribute(nombreLista);
        } else if (WebKeys.FORMA_PAGO_EFECTIVO.equals(formaPago)) {
            aplicacionNueva = APLICACION_PAGO_EFECTIVO;
        } else if (WebKeys.FORMA_PAGO_TIMBRE_BANCO.equalsIgnoreCase(formaPago)) {
            aplicacionNueva = APLICACION_PAGO_TIMBRE_BANCO;

        } else if (formaPago.equals("1") || formaPago.equals("2") || formaPago.equals("3")
                || formaPago.equals("6") || formaPago.equals("7") || formaPago.equals("9")
                || formaPago.equals("11") || formaPago.equals("12")) {
            formaPagoEscogida = Integer.parseInt(formaPago);
            switch (formaPagoEscogida) {
                case 1:
                    aplicacionNueva = APLICACION_PAGO_EFECTIVO;
                    break;
                case 2:
                    aplicacionNueva = APLICACION_PAGO_CONSIGNACION;
                    nombreLista = WebKeys.LISTA_CONSIGNACIONES;
                    listaAplicaciones = (List) session.getAttribute(nombreLista);
                    break;
                case 3:
                    aplicacionNueva = APLICACION_PAGO_CHEQUE;
                    nombreLista = WebKeys.LISTA_CHEQUES;
                    listaAplicaciones = (List) session.getAttribute(nombreLista);
                    break;
                case 6:
                    aplicacionNueva = APLICACION_PAGO_CONVENIO;
                    break;
                case 7:
                    aplicacionNueva = APLICACION_PAGO_TIMBRE_BANCO;
                    break;
                case 9:
                    aplicacionNueva = APLICACION_PAGO_ELECTRONICO_PSE;
//                    nombreLista = WebKeys.LISTA_PSE;
//                    listaAplicaciones = (List) session.getAttribute(nombreLista);
                    break;
                case 11:
                    aplicacionNueva = APLICACION_PAGO_TARJETA_CREDITO;
                    nombreLista = WebKeys.LISTA_TARJETA_CREDITO;
                    listaAplicaciones = (List) session.getAttribute(nombreLista);
                    break;
                case 12:
                    aplicacionNueva = APLICACION_PAGO_TARJETA_DEBITO;
                    nombreLista = WebKeys.LISTA_TARJETA_DEBITO;
                    listaAplicaciones = (List) session.getAttribute(nombreLista);
                    break;
                default:
                    break;
            }
        } else {
            /**
             * @author Cesar Ramírez
             * @change: 1954.VALIDACION.BANCO.FRANQUICIA.CONSIGNACION
             *
             */
            if ((formaPago == null) || formaPago.equals("") || formaPago.equals(WebKeys.SIN_SELECCIONAR)) {
                vpe.addError("Se debe seleccionar la forma de pago.");
                throw vpe;
            }
        }
        /*
            * @autor         : CTORRES
            * @mantis        : 12422
            * @Requerimiento : 049_453
            * @descripcion   : Métodos javascripts para limpiar formulario despues de seleccionar un nuevo tipo de pago.
         */
        if (fechaDocument != null && !fechaDocument.equals("")) {
            Date fechaDoc = null;
            try {
                fechaDoc = DateFormatUtil.parse(fechaDocument);
            } catch (ParseException ex) {
                Logger.getLogger(AWPago.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (fechaDoc != null && fechaDoc.after(new Date())) {
                /**
                 * @author Cesar Ramírez
                 * @change: 1954.VALIDACION.BANCO.FRANQUICIA.CONSIGNACION
                 *
                 */
                vpe.addError("La fecha del documento es inválida.");
            }
        }

        double liquidacion = ((Liquidacion) session.getAttribute(WebKeys.LIQUIDACION)).getValor();

        /**
         * En caso de que haya certificados asociados se agrega el valor de la
         * liquidacion de los certificados
         */
        Double valorCertificadosAsociados = (Double) request.getSession().getAttribute(WebKeys.VALOR_CERTIFICADOS_ASOCIADOS);
        if (valorCertificadosAsociados != null) {
            liquidacion += valorCertificadosAsociados.doubleValue();
        }

        if (listaAplicaciones == null) {
            listaAplicaciones = new Vector();
        }

        if ((aplicacionNueva != APLICACION_PAGO_EFECTIVO) || (aplicacionNueva != APLICACION_PAGO_CONVENIO)) {
            double valorTotal = 0;
        }

        AplicacionPago aplicacionPago = null;

        if (formaPagoEscogida > 0) {
            aplicacionPago = construirNuevaAplicacionPago(request, aplicacionNueva, formaPagoEscogida);
        } else {
            aplicacionPago = construirAplicacionPago(request, aplicacionNueva);
        }

        if (WebKeys.FORMA_PAGO_TIMBRE_BANCO.equalsIgnoreCase(formaPago) || formaPago.equalsIgnoreCase("7")) {
            DocPagoTimbreConstanciaLiquidacion doc;
            aplicacionPago.setValorAplicado(liquidacion);
            doc = (DocPagoTimbreConstanciaLiquidacion) aplicacionPago.getDocumentoPago();
            //doc.setValorDocumento(liquidacion);//Henry Gomez
            doc.setValorDocumento(Double.valueOf(request.getParameter(AWPago.VALOR_PAGAR_PAGOS)));//Osbert Linero
            session.setAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO, aplicacionPago);
            aplicacionPago.setValorAplicado(Double.valueOf(request.getParameter(AWPago.VALOR_PAGAR_PAGOS)));//Osbert Linero
        } else if (aplicacionNueva.equals(APLICACION_PAGO_EFECTIVO) || formaPago.equalsIgnoreCase("1")) {
            session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
            session.setAttribute(WebKeys.APLICACION_EFECTIVO, aplicacionPago);
        } else if (aplicacionNueva.equals(APLICACION_PAGO_CONVENIO) || formaPago.equalsIgnoreCase("6")) {  //HGOMEZ 12422
            session.removeAttribute(WebKeys.APLICACION_CONVENIO);
            session.setAttribute(WebKeys.APLICACION_CONVENIO, aplicacionPago);
        } else if (aplicacionNueva.equals(APLICACION_PAGO_ELECTRONICO_PSE) || formaPago.equalsIgnoreCase("9")) {  //HGOMEZ 12422
            DocPagoElectronicoPSE doc;
            //aplicacionPago.setValorAplicado(liquidacion); //Henry Gomez
            aplicacionPago.setValorAplicado(Double.valueOf(request.getParameter(AWPago.VALOR_PAGAR_PAGOS)));//Osbert Linero

            doc = (DocPagoElectronicoPSE) aplicacionPago.getDocumentoPago();
            //doc.setValorDocumento(liquidacion); //Henry Gomez
            doc.setValorDocumento(Double.valueOf(request.getParameter(AWPago.VALOR_PAGAR_PAGOS)));//Osbert Linero
            session.setAttribute(WebKeys.FORMA_PAGO_PSE, aplicacionPago);
            /*
            * @autor         : CTORRES
            * @mantis        : 12422: Acta - Requerimiento No. 008_589 - Formas_de_Pago
             */
            eliminarInfo(request);
        } else {
            if (aplicacionPago != null) {
                if (!existeAplicacion(aplicacionPago, listaAplicaciones)) {
                    Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);
                    session.setAttribute(nombreLista, listaAplicaciones);

                    //Edgar Lora: Mantis: 0012422
                    if (aplicacionPago.getDocumentoPago() instanceof DocPagoTarjetaCredito) {
                        DocPagoTarjetaCredito doc = (DocPagoTarjetaCredito) aplicacionPago.getDocumentoPago();
                        doc.setValorDocumento(Double.valueOf(request.getParameter(AWPago.VALOR_PAGAR_PAGOS)));
                    } else if (aplicacionPago.getDocumentoPago() instanceof DocPagoTarjetaDebito) {
                        DocPagoTarjetaDebito doc = (DocPagoTarjetaDebito) aplicacionPago.getDocumentoPago();
                        doc.setValorDocumento(Double.valueOf(request.getParameter(AWPago.VALOR_PAGAR_PAGOS)));
                    }

                    EvnPago evento = new EvnPago(usuario, aplicacionPago);

                    //mirara si se va ingresar una nueva consignacion o una ya registrada.
                    if (WebKeys.FORMA_PAGO_CONSIGNACION.equals(formaPago) || formaPago.equalsIgnoreCase("2")) {
                        boolean nuevaConsignacion = true;
                        String nueva = request.getParameter(AWPago.CONSIGNACION_NUEVA_FORMA_PAGO);
                        if (nueva != null && !nueva.equals("")) {
                            if (nueva.equals("true")) {
                                nuevaConsignacion = true;
                            } else {
                                nuevaConsignacion = false;
                            }
                        }
                        evento.setNuevaAplicacion(nuevaConsignacion);
                        //*** @change : En la sesion de código siguiente se valida el codigo del tipo de forma de pago seleccionada.
                    } else if (WebKeys.FORMA_PAGO_CHEQUE.equals(formaPago) || formaPago.equalsIgnoreCase("3")) {
                        boolean nuevoCheque = true;
                        String nueva = request.getParameter(AWPago.NUEVO_CHEQUE);
                        if (nueva != null && !nueva.equals("")) {
                            if (nueva.equals("true")) {
                                nuevoCheque = true;
                            } else {
                                nuevoCheque = false;
                            }
                        }
                        evento.setNuevaAplicacion(nuevoCheque);
                    } else if (formaPago.equalsIgnoreCase("11")) {
                        boolean nuevoPagoTarjetaCredito = true;
                        String nueva = request.getParameter(AWPago.TARJETA_CREDITO_PAGO_NUEVO);
                        if (nueva != null && !nueva.equals("")) {
                            if (nueva.equals("true")) {
                                nuevoPagoTarjetaCredito = true;
                            } else {
                                nuevoPagoTarjetaCredito = false;
                            }
                        }
                        evento.setNuevaAplicacion(nuevoPagoTarjetaCredito);
                    } else if (formaPago.equalsIgnoreCase("12")) {
                        boolean nuevoPagoTarjetaDebito = true;
                        String nueva = request.getParameter(AWPago.TARJETA_DEBITO_PAGO_NUEVO);
                        if (nueva != null && !nueva.equals("")) {
                            if (nueva.equals("true")) {
                                nuevoPagoTarjetaDebito = true;
                            } else {
                                nuevoPagoTarjetaDebito = false;
                            }
                        }
                        evento.setNuevaAplicacion(nuevoPagoTarjetaDebito);
                    }

                    return evento;
                } else {
                    /**
                     * @author Cesar Ramírez
                     * @change: 1954.VALIDACION.BANCO.FRANQUICIA.CONSIGNACION
                     *
                     */
                    //Edgar Lora: Mantis: 0012422 27/09/2013-2
                    vpe.addError("La forma de Pago ingresada ya se encuentra asociada a esta solicitud.");
                }
            } else {
                throw new AplicacionPagoNoAdicionadaException("La aplicación de pago no pudo ser adicionada.");
            }
            /**
             * @author Cesar Ramírez
             * @change: 1954.VALIDACION.BANCO.FRANQUICIA.CONSIGNACION
             *
             */
            if (vpe.getErrores().size() > 0) {
                throw vpe;
            }
        }

        return null;
    }

    /**
     * Este metodo verifica si la aplicacion de pago que se quiere ingresar se
     * enceuntra o no en la lista.
     *
     * @param aplicacionPago
     * @param listaAplicaciones
     * @return true si encuentra la aplicación, false si no se encuentra
     */
    private boolean existeAplicacion(AplicacionPago aplicacionPago,
            List listaAplicaciones) {
        for (Iterator it = listaAplicaciones.iterator(); it.hasNext();) {
            AplicacionPago aplicacion = (AplicacionPago) it.next();
            /*
             * if (aplicacionPago.equals(aplicacion)) { return true;
            }
             */
            DocumentoPago documentoAplicado = aplicacionPago.getDocumentoPago();
            DocumentoPago documento = aplicacion.getDocumentoPago();
            if ((documento instanceof DocPagoCheque && documentoAplicado instanceof DocPagoCheque)) {
                DocPagoCheque docAplicado = (DocPagoCheque) documentoAplicado;
                DocPagoCheque doc = (DocPagoCheque) documento;
                if ((docAplicado.getBanco().getIdBanco().equals(doc.getBanco().getIdBanco()))
                        && (docAplicado.getNoCheque().equals(doc.getNoCheque()))) {
                    return true;
                }
            } else if (documento instanceof DocPagoConsignacion && documentoAplicado instanceof DocPagoConsignacion) {
                DocPagoConsignacion docAplicado = (DocPagoConsignacion) documentoAplicado;
                DocPagoConsignacion doc = (DocPagoConsignacion) documento;
                if ((docAplicado.getBanco().getIdBanco().equals(doc.getBanco().getIdBanco()))
                        && (docAplicado.getNoConsignacion().equals(doc.getNoConsignacion()))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Este método se encarga de construir el pago a partir de la información
     * proporcionada y construye un Evento Pago para la validación de éste.
     *
     * @param request
     * @return Evento pago
     * @throws ValidacionParametrosException
     */
    private EvnPago validarPago(HttpServletRequest request)
            throws ValidacionParametrosException {
        EvnPago evnPago = null;
        Pago pago = construirPago(request);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        evnPago = new EvnPago(usuario, pago, proceso, estacion,
                EvnPago.VALIDAR, usuarioSir);

        return evnPago;
    }

    /**
     * Recibe los datos de registro de pago y los que fueron validados en
     * LIQUIDAR Valida que el pago sea exactamente igual a la liquidacion
     * Registra el pago Crea una instancia del caso en el workflow Envia un
     * recibo de pago a la impresora
     *
     * @param request contenedor de tipo HttpServletRequest donde deben
     * encontrarse los parametros arriba mencionados
     * @return Objeto de tipo EvnTurnoCertificado que contiene el usuario que
     * esta haciendo la transaccion, el tipo de transacción solicitada (En este
     * caso, SOLICITAR) el tipo de Certificado, el número de matricula, el
     * número de copias solicitadas, el ciudadano que está haciendo la
     * solicitud, el valor de la Liquidacion, y el valor del pago efectuado.
     * @throws PagoNoProcesadoException
     * @throws ValidacionParametrosException
     */
    private EvnPago procesarPago(HttpServletRequest request)
            throws PagoNoProcesadoException, ValidacionParametrosException {

        Pago pago = construirPago(request);


        //limpiarConsignacionYaRegistrada(request);
        EvnPago evnPago = null;
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        /**
         * @author : HGOMEZ ** @change : Se captura el valor del tipo de
         * certificado en ** la variable tipoCertificado. ** Caso Mantis : 11598
         */
        String tipoCertificado = (String) request.getSession().getAttribute(WebKeys.CERTIFICADO_TIPO);
        gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        pago.setUsuario(usuarioSir);

        pago.setFecha(new Date());

        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        System.out.println("PROCESO " + proceso);
        if (pago != null) {
            System.out.println("PAGO DIFERENTE NULL");
            Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

            evnPago = new EvnPago(usuario, pago, proceso, estacion,
                    EvnPago.PROCESAR, usuarioSir);

            //se setea el identificador único de usuario para la impresion del recibo
            evnPago.setUID(request.getSession().getId());

            //Se obtiene de sesión la lista de notas informativas creadas antes de la existencia del turno.
            //Se pasa esta nota dentro del Evento de pago.
            List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
            if (listaNotas != null) {
                System.out.println("LISTA NOTAS " + listaNotas.size());
            } else {
                System.out.println("LISTA NOTAS NULO");
            }
            evnPago.setListaNotasSinTurno(listaNotas);
        } else {
            throw new PagoNoProcesadoException("No existe pago a procesar.");
        }

        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        System.out.println("CIRCULO PAGO " + circulo.getIdCirculo());
        evnPago.setCirculo(circulo);

        String impresora = (String) request.getSession().getAttribute(WebKeys.IMPRESORA);
        System.out.println("IMPRESORA PAGO " + impresora);
        evnPago.setImpresora(impresora);

        Hashtable validaciones = (Hashtable) request.getSession().getAttribute(WebKeys.LISTA_VALIDACIONES_MASIVOS);

        if ((validaciones != null) && (evnPago != null)) {
            evnPago.setValidacionesMasivos(validaciones);
        }

        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);

        if ((rol != null) && (evnPago != null)) {
            System.out.println("ROL PAGO " + rol.getNombre());
            evnPago.setRol(rol);
        }

        Boolean registro;

        registro = (Boolean) request.getSession().getAttribute("PAGO_REGISTRO_LIQUIDACION");
        registro = (registro == null) ? new Boolean(false) : registro;

        System.out.println("REGISTRO PAGO " + registro.toString());

        evnPago.setEsPagoRegistro(registro.booleanValue());

        if (request.getParameter(ASIGNAR_ESTACION) != null) {
            evnPago.setAsignarEstacion(true);
        }

        if (null != pago.getLiquidacion()) {
            System.out.println("LIQUIDACION PAGO NO NULA ");

            Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

            if (pago.getLiquidacion() instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia) {
                System.out.println("LIQUIDACION INSTANCIA TURNO FOTOCOPIA ");
                evnPago.setEsPagoFotocopias(true);

                //Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
                //Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
                Solicitud solicitud = pago.getLiquidacion().getSolicitud(); //request.getAttribute( WebKeys.SOLICITUD );

                evnPago.setSolicitud(solicitud);
                //evnPago.setTurno(turno);
                //evnPago.setFase(fase);
                evnPago.setRespuestaWF(WF_LINK_FOTOCOPIAS_PAGOFOTOCOPIAS2FOTOCOPIA);

                // para generar el recibo de pago
                evnPago.setSessionId(request.getSession().getId());
            } //SI ES EL CASO DE UNA LIQUIDACION DE REGISTRO DE DOCUMENTOS:
            //En caso de que la liquidaciones sea de regisro, y exista un turno, debe marcarse el
            //evento como de registro con evnPago.esPagoRegistro, para que en la acción de negocio, no
            //se intente nuevamente generar un turno.
            else if (pago.getLiquidacion() instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro) {
                System.out.println("LIQUIDACION INSTANCIA TURNO REGISTRO ");
                evnPago.setEsPagoRegistro(true);

                //Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
                //Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
                //evnPago.setFase(fase);request.getSession().setAttribute(WebKeys.HAY_EXCEPCION, new Boolean(true));
                if ((request.getSession().getAttribute(WebKeys.VALOR_CERTIFICADOS_ASOCIADOS) != null)) {
                    double valorCertificados = ((Double) request.getSession().getAttribute(WebKeys.VALOR_CERTIFICADOS_ASOCIADOS)).doubleValue();
                    evnPago.setValorCertificadosAsociados(valorCertificados);
                }
                evnPago.setSessionId(request.getSession().getId());
                evnPago.setRespuestaWF(RESULTADO_MAYOR_VALOR);

            } else if (pago.getLiquidacion() instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion) {
                System.out.println("LIQUIDACION INSTANCIA TURNO CORRECCION ");
                evnPago.setEsPagoCorreccion(true);

                //Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
                //Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
                //evnPago.setFase(fase);
                evnPago.setSessionId(request.getSession().getId());
                evnPago.setRespuestaWF(RESULTADO_MAYOR_VALOR);
            } else if (pago.getLiquidacion() instanceof LiquidacionTurnoCertificado) {
                System.out.println("LIQUIDACION INSTANCIA TURNO CERTIFICADO ");
                if (turno != null && turno.getIdFase().equals(CFase.PMY_REGISTRAR)) {
                    evnPago.setEsPagoCertificadoMayorValor(true);
                    evnPago.setSessionId(request.getSession().getId());
                    evnPago.setRespuestaWF(RESULTADO_MAYOR_VALOR);
                }
                //Caso especial: pagar certificados asociados a turnos de registro ya radicados
                Boolean certificadosAsociados;
                certificadosAsociados = (Boolean) request.getSession().getAttribute(AWLiquidacionRegistro.FLAG_CERTIFICADOS_ASOCIADOS_TURNO);
                certificadosAsociados = certificadosAsociados == null ? new Boolean(false) : certificadosAsociados;
                if (certificadosAsociados.booleanValue()) {
                    List listaLiquidacionesCertificadosAsociados = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_LIQUIDACIONES_CERTIFICADOS_ASOCIADOS);
                    evnPago.setPagoCertificadosAsociadosTurno(true);
                    evnPago.setLiquidacionesCertificadosAsociados(listaLiquidacionesCertificadosAsociados);
                }

                //se pregunta de las matriculas con la matricula sin migrar
                List matriculasSirMig = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);
                if (matriculasSirMig != null) {
                    evnPago.setTieneMatriculasSinMigrar(true);
                    evnPago.setMatriculasSinMigrar(matriculasSirMig);
                } else {
                    evnPago.setTieneMatriculasSinMigrar(false);
                }

                boolean isNacional = false;
                isNacional = request.getSession().getAttribute(AWLiquidacionCertificado.TIPO_OFICINA_ORIGEN) != null && request.getSession().getAttribute(AWLiquidacionCertificado.TIPO_OFICINA_ORIGEN).equals(CCertificado.OFICINA_ORIGEN_NACIONAL);
                evnPago.setTurnoNacional(isNacional);
            } else if (pago.getLiquidacion() instanceof LiquidacionTurnoConsulta) {
                System.out.println("LIQUIDACION INSTANCIA TURNO CONSULTA ");
                boolean isNacional = false;
                isNacional = request.getSession().getAttribute(CAlcanceGeografico.ALCANCE_CONSULTA) != null && request.getSession().getAttribute(CAlcanceGeografico.ALCANCE_CONSULTA).equals(CAlcanceGeografico.ID_NACIONAL);
                evnPago.setTurnoNacional(isNacional);
            }

            Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
            if (fase != null) {
                System.out.println("LIQUIDACION FASE " + fase.getDescripcion());
            } else {
                System.out.println("LIQUIDACION FASE NULA");
            }
            Solicitud solicitud = pago.getLiquidacion().getSolicitud(); //request.getAttribute( WebKeys.SOLICITUD );
            evnPago.setSolicitud(solicitud);
            evnPago.setTurno(turno);
            evnPago.setFase(fase);

            /**
             * @author : HGOMEZ ** @change : Se le asigna el tipo de certificado
             * a la variable tipoCertificado de la ** clase EvnPago.java. **
             * Caso Mantis : 11598
             */
            if (proceso.getIdProceso() == 1 && (tipoCertificado.equals("1") || tipoCertificado.equals("2")
                    || tipoCertificado.equals("3") || tipoCertificado.equals("11") || tipoCertificado.equals("13"))) {
                System.out.println("LIQUIDACION TIPO CERTIFICADO " + tipoCertificado);
                evnPago.setTipoCertificado(tipoCertificado);
            }
        }

        if (request.getSession().getAttribute(AWCertificadoMasivo.NOTA_INF_CERTIFICADOS_MASIVOS) != null) {
            System.out.println("LIQUIDACION CERTIFICADO MASIVO EXCENTO ");
            evnPago.setImprimirNotaCertMasivoExento(true);
        }
        
        String cuentaDestino = request.getParameter(AWPago.ID_CUENTA_BANCARIA);
        evnPago.setBanco(cuentaDestino);
  
        Boolean isCertificadoEspecial = (Boolean) request.getSession().getAttribute(WebKeys.IS_CERTIFICADO_ESPECIAL);
        if(isCertificadoEspecial == null){
            isCertificadoEspecial = false;
        }
        if(isCertificadoEspecial && isCertificadoEspecial != null){
            evnPago.setCertificadoEspecial(isCertificadoEspecial);
            boolean isCertificadoTramite = (Boolean) request.getSession().getAttribute(WebKeys.IS_CERTIFICADO_TRAMITE);
            boolean isCertificadoActuacion = (Boolean) request.getSession().getAttribute(WebKeys.IS_CERTIFICADO_ACTUACION);
            if(isCertificadoTramite){
                evnPago.setCertificadoTramite(isCertificadoTramite);
                List turnoTramite = (List) request.getSession().getAttribute(WebKeys.TURNO_TRAMITE_FOLIO);
                    if(turnoTramite != null){
                        evnPago.setTurnoTramite(turnoTramite);
                    }
            }
            if(isCertificadoActuacion){
                evnPago.setCertificadoActuacion(isCertificadoActuacion);
            }
            
        }
        request.getSession().removeAttribute(WebKeys.IS_CERTIFICADO_ESPECIAL);
        request.getSession().removeAttribute(WebKeys.IS_CERTIFICADO_TRAMITE);
        request.getSession().removeAttribute(WebKeys.TURNO_TRAMITE_FOLIO);
        
        return evnPago;
    }

    /**
     * Este método permite procesar cualquier evento de respuesta de la capa de
     * negocio, en caso de recibir alguno.
     *
     * @param request la información del formulario
     * @param eventoRespuesta el evento de respuesta de la capa de negocio, en
     * caso de existir alguno
     */
    public void doEnd(HttpServletRequest request,
            EventoRespuesta eventoRespuesta) {
        EvnRespPago respuesta = (EvnRespPago) eventoRespuesta;
        HttpSession session = request.getSession();
        /*
         * AHERRENO 14/05/2012 REQ 176_151 TRANSACCION
         */
        Turno turno = null;

        if (respuesta != null) {
            if (respuesta.getTipoEvento().equals(EvnRespPago.PROCESAMIENTO_PAGO)) {
                request.getSession().removeAttribute(AWCertificadoMasivo.NOTA_INF_CERTIFICADOS_MASIVOS);
                /**
                 * @author : Ellery David Robles Gómez. @casoMantis : 08056.
                 * @actaRequerimiento : 242 - Error Proceso Certificados al
                 * agregar Nota Informativa. @change : Se remueve de la sesion
                 * la variable de las Notas Informativas.
                 */
                request.getSession().removeAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
                Pago pago = respuesta.getPago();
                /*
                 * AHERRENO 14/05/2012 REQ 176_151 TRANSACCION
                 */
                turno = respuesta.getTurno();
                Liquidacion liqTemp = (Liquidacion) request.getSession().getAttribute(WebKeys.LIQUIDACION);

                /**
                 * @author Cesar Ramírez
                 * @change 1195.IMPRESION.CERTIFICADOS.EXENTOS.PDF Se envía el
                 * valor del Tipo de Tarifa para verificar si es de tipo Exento
                 * en el JSP CONFIRMACION_COPIA.jsp
                 */
                if (liqTemp instanceof LiquidacionTurnoCertificadoMasivo) {
                    LiquidacionTurnoCertificadoMasivo liqTemp_isExento = (LiquidacionTurnoCertificadoMasivo) request.getSession().getAttribute(WebKeys.LIQUIDACION);
                    request.getSession().setAttribute(CTipoTarifa.EXENTO, liqTemp_isExento.getTipoTarifa());
                }

                request.getSession().setAttribute(WebKeys.TURNO, turno);
                request.getSession().setAttribute(WebKeys.PAGO, pago);
                request.getSession().removeAttribute(WebKeys.LIQUIDACION);
                request.getSession().removeAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);
                //daniel
                if (turno != null && turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {
                    long secuencial = (long) respuesta.getValorSecuencial();
                    session.setAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION, "" + secuencial);
                }

                //CASO ESPECIAL DE REGISTRO QUE SE PROCESA EL PAGO PERO NO SE CREA EL TURNO.
                if (respuesta.getSolicitud() != null) {
                    //session.removeAttribute(WebKeys.LIQUIDACION);
                    session.setAttribute(WebKeys.LIQUIDACION, liqTemp);
                    long secuencial = (long) respuesta.getValorSecuencial();
                    session.setAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION, "" + secuencial);
                    session.setAttribute(WebKeys.SECUENCIAL_RECIBO_CONSULTADO, "" + secuencial);
                    session.setAttribute(WebKeys.SOLICITUD, respuesta.getSolicitud());

                    if (respuesta.getEsCajeroRegistro() != null) {
                        session.setAttribute(WebKeys.ES_CAJERO_REGISTRO, respuesta.getEsCajeroRegistro());

                    }
                }

                request.getSession().setAttribute(WebKeys.PAGO, pago);
                if (respuesta.getIdImprimible() != 0) {
                    request.getSession().setAttribute(WebKeys.ID_IMPRIMIBLE, new Integer(respuesta.getIdImprimible()));
                }

                request.getSession().removeAttribute(WebKeys.TURNO_ANTERIOR_OK);
            } else if (respuesta.getTipoEvento().equals(EvnRespPago.VALIDACION_PAGO)) {
                Pago pago = respuesta.getPago();

                if (pago != null) {
                    request.getSession().setAttribute(WebKeys.PAGO, pago);
                    request.getSession().setAttribute(WebKeys.PRECISION_PAGO,
                            new Double(respuesta.getRangoAceptablePago()));
                }
            } else if (respuesta.getTipoEvento().equals(EvnRespPago.VERIFICACION_APLICACION_PAGO)) {
                AplicacionPago aplicacionPago = (AplicacionPago) respuesta.getPayload();
                DocumentoPago documento = aplicacionPago.getDocumentoPago();
                if (documento instanceof DocPagoCheque) {
                    boolean marca = false;
                    List marcas = (List) session.getAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
                    if (marcas == null) {
                        marcas = new ArrayList();
                    }
                    DocPagoCheque cheque = (DocPagoCheque) documento;
                    List aplicaciones = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);
                    if (respuesta.isNueva()) {
                        aplicaciones.add((AplicacionPago) respuesta.getPayload());
                        AplicacionPago aplicacionAplicada = (AplicacionPago) aplicaciones.get(aplicaciones.size() - 1);
                        DocPagoCheque chequeAplicado = (DocPagoCheque) aplicacionAplicada.getDocumentoPago();
                        if (chequeAplicado.getValorDocumento() != cheque.getValorDocumento()) {
                            chequeAplicado.setValorDocumento(cheque.getValorDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        if (chequeAplicado.getSaldoDocumento() != cheque.getSaldoDocumento()) {
                            chequeAplicado.setSaldoDocumento(cheque.getSaldoDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        if (!respuesta.isNueva() && aplicacionAplicada.getValorAplicado() > chequeAplicado.getSaldoDocumento()) {
                            aplicacionAplicada.setValorAplicado(chequeAplicado.getSaldoDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        aplicacionAplicada.setDocumentoPago(chequeAplicado);
                        aplicaciones.set(aplicaciones.size() - 1, aplicacionAplicada);
                        marcas.add(new Boolean(marca));
                        session.setAttribute(WebKeys.LISTA_CHEQUES, aplicaciones);
                        session.setAttribute(WebKeys.LISTA_CHEQUES_MARCAS, marcas);
                    } else {
                        //colocar variables  en los campos del cheque ya registrado
                        //limpiar sesion
                        limpiarConsignacionYaRegistrada(request);
                        limpiarChequeYaRegistrada(request);
                        //colocar variables en el resto de campos
                        session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, cheque.getBanco().getIdBanco());
                        session.setAttribute(AWPago.NUM_CHEQUE_YA_REGISTRADA, cheque.getNoCheque());
                        /*
                         * @autor         : HGOMEZ 
                         * @mantis        : 12422 
                         * @Requerimiento : 049_453 
                         * @descripcion   : Se mantienen los valores en sesion de la nuevas variables.
                         */
                        session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA, cheque.getNoCheque());
                        session.setAttribute(AWPago.FECHA_CHEQUE_YA_REGISTRADA, cheque.getFecha());
                        session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA, cheque.getFecha());
                        session.setAttribute(AWPago.SALDO_CHEQUE_YA_REGISTRADA, Double.toString(cheque.getSaldoDocumento()));
                        session.setAttribute(AWPago.VALOR_CHEQUE_YA_REGISTRADA, Double.toString(cheque.getValorDocumento()));
                        session.setAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA, Double.toString(cheque.getSaldoDocumento()));
                        session.setAttribute(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA, Double.toString(cheque.getValorDocumento()));
                        try {
                            request.getSession().setAttribute(WebKeys.PAGO, construirPago(request));
                        } catch (ValidacionParametrosException e) {
                            Log.getInstance().error(AWPago.class, e);
                        }
                    }
                } else if (documento instanceof DocPagoConsignacion) {
                    List marcas = (ArrayList) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);
                    boolean marca = false;
                    if (marcas == null) {
                        marcas = new ArrayList();
                    }
                    DocPagoConsignacion consignacion = (DocPagoConsignacion) documento;
                    List aplicaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);

                    if (respuesta.isNueva()) {
                        aplicaciones.add((AplicacionPago) respuesta.getPayload());
                        AplicacionPago aplicacionAplicada = (AplicacionPago) aplicaciones.get(aplicaciones.size() - 1);
                        DocPagoConsignacion consignacionAplicado = (DocPagoConsignacion) aplicacionAplicada.getDocumentoPago();
                        if (consignacionAplicado.getValorDocumento() != consignacion.getValorDocumento()) {
                            consignacionAplicado.setValorDocumento(consignacion.getValorDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        if (consignacionAplicado.getSaldoDocumento() != consignacion.getSaldoDocumento()) {
                            consignacionAplicado.setSaldoDocumento(consignacion.getSaldoDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        if (!respuesta.isNueva() && aplicacionAplicada.getValorAplicado() > consignacionAplicado.getSaldoDocumento()) {
                            aplicacionAplicada.setValorAplicado(consignacionAplicado.getSaldoDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        aplicacionAplicada.setDocumentoPago(consignacionAplicado);
                        aplicaciones.set(aplicaciones.size() - 1, aplicacionAplicada);
                        marcas.add(new Boolean(marca));
                        session.setAttribute(WebKeys.LISTA_CONSIGNACIONES, aplicaciones);
                        session.setAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS, marcas);
                    } else {
                        //limpiar sesion
                        /**
                         * @author Fernando Padilla @change Se agrega para
                         * formatear los valores de la consignación cuando esta
                         * ya esta registrada en la base de datos. Caso mantis
                         * 0001929: Acta - Requerimiento No 083.
                         *
                         */
                        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                        simbolos.setDecimalSeparator('.');
                        NumberFormat numberFormat = new DecimalFormat("##0.0", simbolos);

                        limpiarConsignacionYaRegistrada(request);
                        /*
                        * @autor         : HGOMEZ 
                        * @mantis        : 12422 
                        * @Requerimiento : 049_453 
                        * @descripcion   : Se comenta la siguiente linea.
                         */
                        //limpiarChequeYaRegistrada(request);
                        //colocar variables en el resto de campos
                        session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, consignacion.getBanco().getIdBanco());
                        session.setAttribute(AWPago.COD_SUCURSAL_BANCO_YA_REGISTRADA, consignacion.getCodSucursal());

                        /*
                        * @autor         : HGOMEZ 
                        * @mantis        : 12422 
                        * @Requerimiento : 049_453 
                        * @descripcion   : Se comentan las siguientes linea.
                         */
                        //session.setAttribute(AWPago.NUM_CONSIGNACION_YA_REGISTRADA, consignacion.getNoConsignacion());
                        //session.setAttribute(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA, consignacion.getFecha());                        
                        session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA, consignacion.getNoConsignacion());
                        session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA, consignacion.getFecha());

                        /**
                         * @author Fernando Padilla @change Se agrega para
                         * formatear los valores de la consignación cuando esta
                         * ya esta registrada en la base de datos. Caso mantis
                         * 0001929: Acta - Requerimiento No 083.
                         *
                         */
                        /*
                        * @autor         : HGOMEZ 
                        * @mantis        : 12422 
                        * @Requerimiento : 049_453 
                        * @descripcion   : Se comenta las dos siguientes líneas de código y se hace uso de las nuevas
                        * constantes declaradas para la nueva interfaz de formas de pago.
                         */
                        session.setAttribute(AWPago.SALDO_CONSIGNACION_YA_REGISTRADA, numberFormat.format(consignacion.getSaldoDocumento()));
                        session.setAttribute(AWPago.VALOR_CONSIGNACION_YA_REGISTRADA, numberFormat.format(consignacion.getValorDocumento()));
                        session.setAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA, numberFormat.format(consignacion.getSaldoDocumento()));
                        session.setAttribute(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA, numberFormat.format(consignacion.getValorDocumento()));

                        try {
                            request.getSession().setAttribute(WebKeys.PAGO, construirPago(request));
                        } catch (ValidacionParametrosException e) {
                            Log.getInstance().error(AWPago.class, e);
                        }
                    }
                } /*
                 * @autor         : HGOMEZ 
                 * @mantis        : 12422 
                 * @Requerimiento : 049_453 
                 * @descripcion   : Constantes declaradas para la nueva interfaz de formas de pago.
                 */ else if (documento instanceof DocPagoTarjetaCredito) {
                    List marcas = (ArrayList) session.getAttribute(WebKeys.LISTA_TARJETA_CREDITO_MARCAS);
                    boolean marca = false;
                    if (marcas == null) {
                        marcas = new ArrayList();
                    }
                    DocPagoTarjetaCredito tarjetaCredito = (DocPagoTarjetaCredito) documento;
                    List aplicaciones = (List) session.getAttribute(WebKeys.LISTA_TARJETA_CREDITO);

                    if (respuesta.isNueva()) {
                        aplicaciones.add((AplicacionPago) respuesta.getPayload());
                        AplicacionPago aplicacionAplicada = (AplicacionPago) aplicaciones.get(aplicaciones.size() - 1);
                        DocPagoTarjetaCredito tarjetaCreditoAplicado = (DocPagoTarjetaCredito) aplicacionAplicada.getDocumentoPago();
                        if (tarjetaCreditoAplicado.getValorDocumento() != tarjetaCredito.getValorDocumento()) {
                            tarjetaCreditoAplicado.setValorDocumento(tarjetaCredito.getValorDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        if (tarjetaCreditoAplicado.getSaldoDocumento() != tarjetaCredito.getSaldoDocumento()) {
                            tarjetaCreditoAplicado.setSaldoDocumento(tarjetaCredito.getSaldoDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        if (!respuesta.isNueva() && aplicacionAplicada.getValorAplicado() > tarjetaCreditoAplicado.getSaldoDocumento()) {
                            aplicacionAplicada.setValorAplicado(tarjetaCreditoAplicado.getSaldoDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        aplicacionAplicada.setDocumentoPago(tarjetaCreditoAplicado);
                        aplicaciones.set(aplicaciones.size() - 1, aplicacionAplicada);
                        marcas.add(new Boolean(marca));
                        session.setAttribute(WebKeys.LISTA_TARJETA_CREDITO, aplicaciones);
                        session.setAttribute(WebKeys.LISTA_TARJETA_CREDITO_MARCAS, marcas);
                    } else {
                        //limpiar sesion
                        /**
                         * @author Fernando Padilla @change Se agrega para
                         * formatear los valores de la consignación cuando esta
                         * ya esta registrada en la base de datos. Caso mantis
                         * 0001929: Acta - Requerimiento No 083.
                         *
                         */
                        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                        simbolos.setDecimalSeparator('.');
                        NumberFormat numberFormat = new DecimalFormat("##0.0", simbolos);

                        limpiarTarjetaCreditoDebitoYaRegistrada(request);
                        //limpiarChequeYaRegistrada(request);
                        //colocar variables en el resto de campos
                        session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, tarjetaCredito.getBancoFranquicia().getIdBanco());
                        session.setAttribute(AWPago.COD_FRANQUICIA_YA_REGISTRADA, tarjetaCredito.getBancoFranquicia().getIdTipoFranquicia());

                        session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA, tarjetaCredito.getNumeroTarjeta());
                        session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA, tarjetaCredito.getFecha());

                        session.setAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA, numberFormat.format(tarjetaCredito.getSaldoDocumento()));
                        session.setAttribute(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA, numberFormat.format(tarjetaCredito.getValorDocumento()));

                        try {
                            request.getSession().setAttribute(WebKeys.PAGO, construirPago(request));
                        } catch (ValidacionParametrosException e) {
                            Log.getInstance().error(AWPago.class, e);
                        }
                    }
                } else if (documento instanceof DocPagoTarjetaDebito) {
                    List marcas = (ArrayList) session.getAttribute(WebKeys.LISTA_TARJETA_DEBITO_MARCAS);
                    boolean marca = false;
                    if (marcas == null) {
                        marcas = new ArrayList();
                    }
                    DocPagoTarjetaDebito tarjetaDebito = (DocPagoTarjetaDebito) documento;
                    List aplicaciones = (List) session.getAttribute(WebKeys.LISTA_TARJETA_DEBITO);

                    if (respuesta.isNueva()) {
                        aplicaciones.add((AplicacionPago) respuesta.getPayload());
                        AplicacionPago aplicacionAplicada = (AplicacionPago) aplicaciones.get(aplicaciones.size() - 1);
                        DocPagoTarjetaDebito tarjetaDebitoAplicado = (DocPagoTarjetaDebito) aplicacionAplicada.getDocumentoPago();
                        if (tarjetaDebitoAplicado.getValorDocumento() != tarjetaDebito.getValorDocumento()) {
                            tarjetaDebitoAplicado.setValorDocumento(tarjetaDebito.getValorDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        if (tarjetaDebitoAplicado.getSaldoDocumento() != tarjetaDebito.getSaldoDocumento()) {
                            tarjetaDebitoAplicado.setSaldoDocumento(tarjetaDebito.getSaldoDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        if (!respuesta.isNueva() && aplicacionAplicada.getValorAplicado() > tarjetaDebitoAplicado.getSaldoDocumento()) {
                            aplicacionAplicada.setValorAplicado(tarjetaDebitoAplicado.getSaldoDocumento());
                            marca = true;
                        } else {
                            marca = false;
                        }
                        aplicacionAplicada.setDocumentoPago(tarjetaDebitoAplicado);
                        aplicaciones.set(aplicaciones.size() - 1, aplicacionAplicada);
                        marcas.add(new Boolean(marca));
                        session.setAttribute(WebKeys.LISTA_TARJETA_DEBITO, aplicaciones);
                        session.setAttribute(WebKeys.LISTA_TARJETA_DEBITO_MARCAS, marcas);
                    } else {
                        //limpiar sesion
                        /**
                         * @author Fernando Padilla @change Se agrega para
                         * formatear los valores de la consignación cuando esta
                         * ya esta registrada en la base de datos. Caso mantis
                         * 0001929: Acta - Requerimiento No 083.
                         *
                         */
                        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                        simbolos.setDecimalSeparator('.');
                        NumberFormat numberFormat = new DecimalFormat("##0.0", simbolos);

                        limpiarTarjetaCreditoDebitoYaRegistrada(request);
                        //limpiarChequeYaRegistrada(request);
                        //colocar variables en el resto de campos
                        session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, tarjetaDebito.getBancoFranquicia().getIdBanco());
                        session.setAttribute(AWPago.COD_FRANQUICIA_YA_REGISTRADA, tarjetaDebito.getBancoFranquicia().getIdTipoFranquicia());

                        session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA, tarjetaDebito.getNumeroTarjeta());
                        session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA, tarjetaDebito.getFecha());

                        session.setAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA, numberFormat.format(tarjetaDebito.getSaldoDocumento()));
                        session.setAttribute(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA, numberFormat.format(tarjetaDebito.getValorDocumento()));

                        try {
                            request.getSession().setAttribute(WebKeys.PAGO, construirPago(request));
                        } catch (ValidacionParametrosException e) {
                            Log.getInstance().error(AWPago.class, e);
                        }
                    }
                } else if (documento instanceof DocPagoGeneral) {
                    List marcas = (ArrayList) session.getAttribute(WebKeys.LISTA_GENERAL_MARCAS);
                    boolean marca = false;
                    if (marcas == null) {
                        marcas = new ArrayList();
                    }
                    DocPagoGeneral general = (DocPagoGeneral) documento;
////                    List aplicaciones = (List) session.getAttribute(WebKeys.LISTA_GENERAL);
                    List aplicaciones = (List) session.getAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS);
//
                    if (respuesta.isNueva()) {
//                        aplicaciones.add((AplicacionPago) respuesta.getPayload());
//                        AplicacionPago aplicacionAplicada = (AplicacionPago) aplicaciones.get(aplicaciones.size() - 1);
//                        DocPagoGeneral generalAplicado = (DocPagoGeneral) aplicacionAplicada.getDocumentoPago();
//                        if (generalAplicado.getValorDocumento() != general.getValorDocumento()) {
//                            generalAplicado.setValorDocumento(general.getValorDocumento());
//                            marca = true;
//                        } else {
//                            marca = false;
//                        }
//                        if (generalAplicado.getSaldoDocumento() != general.getSaldoDocumento()) {
//                            generalAplicado.setSaldoDocumento(general.getSaldoDocumento());
//                            marca = true;
//                        } else {
//                            marca = false;
//                        }
//                        if (!respuesta.isNueva() && aplicacionAplicada.getValorAplicado() > generalAplicado.getSaldoDocumento()) {
//                            aplicacionAplicada.setValorAplicado(generalAplicado.getSaldoDocumento());
//                            marca = true;
//                        } else {
//                            marca = false;
//                        }
//                        aplicacionAplicada.setDocumentoPago(generalAplicado);
//                        aplicaciones.set(aplicaciones.size() - 1, aplicacionAplicada);
//                        marcas.add(new Boolean(marca));
//                        session.setAttribute(WebKeys.LISTA_GENERAL, aplicaciones);
//                        session.setAttribute(WebKeys.LISTA_GENERAL_MARCAS, marcas);
                        aplicaciones.add((AplicacionPago) respuesta.getPayload());
                        session.setAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS, aplicaciones);
                    } else {
                        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                        simbolos.setDecimalSeparator('.');
                        NumberFormat numberFormat = new DecimalFormat("##0.0", simbolos);

                        limpiarConsignacionYaRegistrada(request);
                        //limpiarChequeYaRegistrada(request);
                        //colocar variables en el resto de campos
//                        session.setAttribute(AWPago.COD_BANCO_FRANQUICIA, general.getBanco().getIdBanco());
                        session.setAttribute(AWPago.COD_BANCO_FRANQUICIA, request.getParameter(AWPago.COD_BANCO_FRANQUICIA));                   
//                        session.setAttribute(AWPago.COD_SUCURSAL_BANCO_YA_REGISTRADA, general.getCodSucursal());
                        //session.setAttribute(AWPago.NUM_CONSIGNACION_YA_REGISTRADA, consignacion.getNoConsignacion());
                        //session.setAttribute(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA, consignacion.getFecha());                        
                        session.setAttribute(AWPago.NO_CONSIGNACION, general.getNoConsignacion());
                        session.setAttribute(AWPago.FECHA_DOCU, general.getFechaDocu());
//                        session.setAttribute(AWPago.SALDO_CONSIGNACION_YA_REGISTRADA, numberFormat.format(general.getSaldoDocumento()));
                        session.setAttribute(AWPago.VLR_DOC_PAGO, numberFormat.format(general.getValorDocumento()));
                        session.setAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA, numberFormat.format(general.getSaldoDocumento()));
                        session.setAttribute(AWPago.NO_APROBACION, general.getNoAprobacion());
                        session.setAttribute(AWPago.NO_PIN, general.getNoAprobacion());
                        session.setAttribute(AWPago.VLR_LIQUIDADO, general.getValorLiquidado());

//                        try {
//                            request.getSession().setAttribute(WebKeys.PAGO, construirPago(request));
//                        } catch (ValidacionParametrosException e) {
//                            Log.getInstance().error(AWPago.class, e);
//                        }

                        if (respuesta.isNueva()) {
                            System.out.println("LA RESPUESTA SI ES NUEVA GERE");
                            aplicaciones.add((AplicacionPago) respuesta.getPayload());
                            session.setAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS, aplicaciones);
                        } else {
                            System.out.println("LA RESPUESTA NO ES NUEVA GERE");
                        }
                    }
                } 
                else {
                    List aplicaciones = (List) session.getAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS);

                    if (respuesta.isNueva()) {
                        System.out.println("LA RESPUESTA SI ES NUEVA GERE");
                        aplicaciones.add((AplicacionPago) respuesta.getPayload());
                        session.setAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS, aplicaciones);
                    } else {
                        System.out.println("LA RESPUESTA NO ES NUEVA GERE");
                    }
                }
                /*
            * @autor         : CTORRES
            * @mantis        : 12422
            * @Requerimiento : 049_453
                 */
                if (respuesta.isNueva()) {
                    eliminarInfo(request);
                }
            }
            //Osbert Linero 
            //Eliminar pagos registrados de sesiòn
            if (accion.equals(PROCESAR_REGISTRO_CONTINUAR) || accion.equals(PROCESAR)) {
                limpiarPseYaRegistrada(request);
                session.removeAttribute(WebKeys.FORMA_PAGO_PSE);
                session.removeAttribute(WebKeys.APLICACION_CONVENIO);
                session.removeAttribute(APLICACION_PAGO_CONVENIO);
                session.removeAttribute(AWPago.COD_BANCO_YA_REGISTRADA);
                /*
                        * @autor         : CTORRES
                        * @mantis        : 12422 
                        * @Requerimiento : 049_453 
                 */
                session.removeAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
                session.removeAttribute(WebKeys.LISTA_CHEQUES);
                session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES);
                session.removeAttribute(WebKeys.LISTA_TARJETA_CREDITO);
                session.removeAttribute(WebKeys.LISTA_TARJETA_DEBITO);
                session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
            }
        }
        /*
         * AHERRENO 14/05/2012 REQ 176_151 TRANSACCION
         */
        Date fechaIni = (Date) request.getSession().getAttribute("TIEMPO_INICIO_TRANSACCION");
        Date fechaFin = new Date();
        TransaccionSIR transaccion = new TransaccionSIR();
        List<Transaccion> acciones = (List<Transaccion>) request.getSession().getAttribute("LISTA_TRANSACCION");
        double tiempoSession = (Double) request.getSession().getAttribute("TIEMPO_TRANSACCION");
        long calTiempo = 0;
        try {
            calTiempo = transaccion.calculoTransaccion(fechaIni, fechaFin);
        } catch (Exception ex) {
            Logger.getLogger(AWPago.class.getName()).log(Level.SEVERE, null, ex);
        }
        DecimalFormat df = new DecimalFormat("0.000");
        double calculo = Double.valueOf(df.format(tiempoSession + ((double) calTiempo / 1000)).replace(',', '.'));
        System.out.println("El tiempo de la accion " + request.getParameter(WebKeys.ACCION) + " en milisegundos " + calTiempo);
        request.getSession().setAttribute("TIEMPO_TRANSACCION", calculo);

        Transaccion transaccionReg = new Transaccion();
        transaccionReg.setFechaTransaccion(fechaFin);
        transaccionReg.setAccionWeb(accion);
        transaccionReg.setTiempo(calTiempo);
        acciones.add(transaccionReg);

        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
        if (rol.getNombre().equals("CAJERO CERTIFICADOS") || rol.getNombre().equals("LIQUIDADOR REGISTRO") || rol.getNombre().equals("CAJERO CONSULTAS")) {
            if (accion.equals(PROCESAR)) {
                if (turno != null) {
                    /*
                     * Se recorre la lista para almacenar la informacion del turno
                     */
                    for (Transaccion transacion : acciones) {
                        transacion.setAnio(turno.getAnio());
                        transacion.setIdCirculo(turno.getIdCirculo());
                        transacion.setIdProceso(turno.getIdProceso());
                        transacion.setIdTurno(turno.getIdTurno());
                    }
                    transaccion.guardarTransaccion(acciones);
                }
                acciones.clear();
                request.getSession().setAttribute("LISTA_TRANSACCION", acciones);
                request.getSession().setAttribute("TIEMPO_TRANSACCION", Double.valueOf(0));
            }
        }
    }

    /**
     * Este método obtiene todos los valores de los parámetros apropiados de
     * acuerdo a la forma de pago empleada y construye el objeto AplicacionPago
     * correspondiente.
     *
     * @param request
     * @param formaPago
     * @return Objeto AplicacioónPago con los datos asociados a la forma de pago
     * @throws ValidacionParametrosException
     */
    private AplicacionPago construirAplicacionPago(HttpServletRequest request,
            String formaPago) throws ValidacionParametrosException {
        ValidacionParametrosException vpe = new ValidacionParametrosRegistroPagoException();
        HttpSession session = request.getSession();
        List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);

        if (APLICACION_PAGO_CHEQUE.equals(formaPago)) {
            boolean nuevoCheque = true;
            String nueva = request.getParameter(AWPago.NUEVO_CHEQUE);
            if (nueva != null && !nueva.equals("")) {
                if (nueva.equals("true")) {
                    nuevoCheque = true;
                } else {
                    nuevoCheque = false;
                }
            }
            return construirAplicacionPagoCheque(request, vpe, listaBancos, nuevoCheque);
        } else if (APLICACION_PAGO_CONSIGNACION.equals(formaPago)) {
            boolean nuevaConsignacion = true;
            String nueva = request.getParameter(AWPago.CONSIGNACION_NUEVA);
            if (nueva != null && !nueva.equals("")) {
                if (nueva.equals("true")) {
                    nuevaConsignacion = true;
                } else {
                    nuevaConsignacion = false;
                }
            }
            return construirAplicacionPagoConsignacion(request, vpe, listaBancos, nuevaConsignacion);
        } else if (APLICACION_PAGO_EFECTIVO.equals(formaPago)) {
            return construirAplicacionPagoEfectivo(request, vpe);
        } else if (APLICACION_PAGO_TIMBRE_BANCO.equals(formaPago)) {
            return this.construirAplicacionPagoTimbreBanco(request, vpe);
        } else {
            vpe.addError("La forma de pago es inválida.");
            throw vpe;
        }
    }

    /**
     * * @author : HGOMEZ
     *** @change : Nuevo flujo para las formas de pago. ** TODO el campo int
     * formaPagoEscogida no debe ir, se debe comentar el método de arriba **
     * Caso Mantis : 12422
     */
    private AplicacionPago construirNuevaAplicacionPago(HttpServletRequest request,
            String formaPago, int formaPagoEscogida) throws ValidacionParametrosException {
        ValidacionParametrosException vpe = new ValidacionParametrosRegistroPagoException();
        HttpSession session = request.getSession();

        List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);

        List listaBancosFranquicias = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS);

        if (APLICACION_PAGO_CHEQUE.equals(formaPago)) {
            boolean nuevoCheque = true;
            String nueva = request.getParameter(AWPago.NUEVO_CHEQUE);
            if (nueva != null && !nueva.equals("")) {
                if (nueva.equals("true")) {
                    nuevoCheque = true;
                } else {
                    nuevoCheque = false;
                }
            }
            return construirAplicacionPagoCheque(request, vpe, listaBancos, nuevoCheque);
        } else if (APLICACION_PAGO_CONSIGNACION.equals(formaPago)) {
            boolean nuevaConsignacion = true;
            String nueva = request.getParameter(AWPago.CONSIGNACION_NUEVA_FORMA_PAGO);
            if (nueva != null && !nueva.equals("")) {
                if (nueva.equals("true")) {
                    nuevaConsignacion = true;
                } else {
                    nuevaConsignacion = false;
                }
            }
            return construirAplicacionNuevaPagoConsignacion(request, vpe, listaBancos, nuevaConsignacion);
        } else if (APLICACION_PAGO_TARJETA_CREDITO.equals(formaPago)) {
            boolean nuevaTarjetaCredito = true;
            String nueva = request.getParameter(AWPago.TARJETA_CREDITO_PAGO_NUEVO);
            if (nueva != null && !nueva.equals("")) {
                if (nueva.equals("true")) {
                    nuevaTarjetaCredito = true;
                } else {
                    nuevaTarjetaCredito = false;
                }
            }
            return construirAplicacionPagoTarjetaCreditoDebito(request, vpe, listaBancos, listaBancosFranquicias, nuevaTarjetaCredito, formaPagoEscogida);
        } else if (APLICACION_PAGO_TARJETA_DEBITO.equals(formaPago)) {
            boolean nuevaTarjetaDebito = true;
            String nueva = request.getParameter(AWPago.TARJETA_DEBITO_PAGO_NUEVO);
            if (nueva != null && !nueva.equals("")) {
                if (nueva.equals("true")) {
                    nuevaTarjetaDebito = true;
                } else {
                    nuevaTarjetaDebito = false;
                }
            }
            return construirAplicacionPagoTarjetaCreditoDebito(request, vpe, listaBancos, listaBancosFranquicias, nuevaTarjetaDebito, formaPagoEscogida);
        } //        else if (APLICACION_PAGO_ELECTRONICO_PSE.equals(formaPago)) {
        //            boolean nuevaPse = true;
        //            String nueva = request.getParameter(AWPago.PAGO_ELECTRONICO_PSE_PAGO_NUEVO);
        //            if (nueva != null && !nueva.equals("")) {
        //                if (nueva.equals("true")) {
        //                    nuevaPse = true;
        //                } else {
        //                    nuevaPse = false;
        //                }
        //            }
        //            return construirAplicacionPagoPse(request, vpe, nuevaPse);
        //        }
        else if (APLICACION_PAGO_ELECTRONICO_PSE.equals(formaPago)) {
            return construirAplicacionPagoPse(request, vpe, listaBancosFranquicias);
        } else if (APLICACION_PAGO_CONVENIO.equals(formaPago)) {
            return construirAplicacionConvenio(request, vpe);
        } else if (APLICACION_PAGO_EFECTIVO.equals(formaPago)) {
            return construirAplicacionPagoEfectivo(request, vpe);
        } else if (APLICACION_PAGO_TIMBRE_BANCO.equals(formaPago)) {
            return this.construirAplicacionPagoTimbreBanco(request, vpe);
        } else {
            vpe.addError("La forma de pago es inválida.");
            throw vpe;
        }
    }

    /**
     * @param request
     * @param vpe
     * @return Objeto pago con efectivo
     * @throws ValidacionParametrosException
     */
    private AplicacionPago construirAplicacionPagoEfectivo(
            HttpServletRequest request, ValidacionParametrosException vpe)
            throws ValidacionParametrosException {
        double valorEfectivo = 0.0d;

        try {
            /*
             * @autor         : HGOMEZ 
             * @mantis        : 12422 
             * @Requerimiento : 049_453 
             * @descripcion   : Se hace uso de la nueva variable general de pagos VALOR_PAGAR_PAGOS.
             */
            valorEfectivo = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS));

            if (valorEfectivo < 0) {
                vpe.addError("El valor no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            vpe.addError("El valor en efectivo no es válido.");
        }

        //Edgar Lora: Mantis: 0012422 27/09/2013-1
        if (request.getSession().getAttribute(WebKeys.APLICACION_EFECTIVO) != null) {
            vpe.addError("La forma de pago ingresada ya se encuentra asociada a esta solicitud.");
        }

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        DocumentoPago documentoEfectivo = new DocPagoEfectivo(valorEfectivo);
        AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo,
                valorEfectivo);

        return aplicacionEfectivo;
    }

    /**
     * Construir la Aplicacion Pago para forma de pago con timbre de banco para
     * la constancia de liquidación de registro
     *
     * @param request
     * @param vpe
     * @return Objeto pago con timbre banco
     * @throws ValidacionParametrosException
     */
    private AplicacionPago construirAplicacionPagoTimbreBanco(
            HttpServletRequest request, ValidacionParametrosException vpe)
            throws ValidacionParametrosException {
        String numeroTimbre = "";
        /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Se hace uso de las nuevas variables generales de pagos 
         * FECHA_DOCUMENTO_PAGOS, VALOR_PAGAR_PAGOS.
         */
        String fechaString = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS);

        numeroTimbre = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS).replaceAll(" ", "");
        numeroTimbre = (numeroTimbre == null) ? "" : numeroTimbre;

        Date d = null;

        try {
            d = DateFormatUtil.parse(fechaString);
        } catch (ParseException e) {
            vpe.addError("El valor de la fecha de pago del timbre no es válido.");
        }

        if (numeroTimbre.length() == 0) {
            //vpe.addError("El campo número timbre no puede estar vacío");
        }

        //Edgar Lora: Mantis: 0012422
        if (request.getSession().getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO) != null) {
            vpe.addError("La forma de pago ingresada ya se encuentra asociada a esta solicitud.");
        }

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(d);

        DocumentoPago documentopago = new DocPagoTimbreConstanciaLiquidacion(0,
                numeroTimbre, fechaString);
        AplicacionPago aplicacionPagoTimbreConstanciaLiquidacion = new AplicacionPago(documentopago, 0);

        return aplicacionPagoTimbreConstanciaLiquidacion;
    }

    //ASI FUNCIONABA ANTES
    /**
     * @param request
     * @param vpe
     * @param listaBancos
     * @return Objeto con el pago de la consignación
     * @throws ValidacionParametrosException
     */
    private AplicacionPago construirAplicacionPagoConsignacion(
            HttpServletRequest request, ValidacionParametrosException vpe,
            List listaBancos, boolean nueva) throws ValidacionParametrosException {

        /**
         * * @author : HGOMEZ
         *** @change : Se detallan a continuacion en frente de cada atributo de
         * la forma ** de pago consignación un comentario que hace explicito la
         * información que ** almacenará cada atributo teniendo en cuenta la
         * nueva interfaz de forma de pagos ** del aplicativo SIR. ** Caso
         * Mantis : 12422
         */
        //atributos consignacion
        String codBanco;            // Banco/Franquicia
        String codSucursal = "";    // Ya no será necesario su uso por tanto se inicia vacía
        // para no afectar la lógica de negocio existente   
        String strFecha;            // Fecha documento
        Calendar fecha;
        String numConsignacion;     // Número documento pago
        double valor;               // Valor documento
        double valorAplicado;       // Valor pagado
        /**
         * * @author : HGOMEZ
         *** @change : Se reubica la declararación de la variable nombreBanco
         * para que solo ** se llame la función getNombreBanco cuando sea
         * necesario. ** Caso Mantis : 12422
         */
        String nomBanco = "";

        if (nueva) {
            //Inicializar los atributos si es nueva consignacion.

            codBanco = request.getParameter(AWPago.COD_BANCO);
            /**
             * * @author : HGOMEZ
             *** @change : Se asigna valor a la variable nomBanco y se usa la
             * misma ** en lugar de de llamar la función getNombre. ** Caso
             * Mantis : 12422
             */
            nomBanco = getNombreBanco(codBanco, listaBancos);
            if ((codBanco == null) || nomBanco.equals("")) {
                vpe.addError("Para la consignación el código del Banco no puede ser vacío.");
            }

            /**
             * * @author : HGOMEZ
             *** @change : Se comentan las siguientes cuatro lineas de código
             * dado que ya no será ** necesario consultar el código de la
             * sucursal del banco por lo cual a la variable ** se le iniciará
             * vacía (""). ** Caso Mantis : 12422
             */
            codSucursal = request.getParameter(AWPago.COD_SUCURSAL_BANCO);
            if (codSucursal == null) {
                vpe.addError("El código de la sucursal es inválido.");
            }

            /**
             * * @author : HGOMEZ
             *** @change : Se comenta la siguiente línea de código y se hace
             * uso de las nuevas ** constantes declaradas para la nueva interfaz
             * de formas de pago. ** Caso Mantis : 12422
             */
            strFecha = request.getParameter(AWPago.FECHA_CONSIGNACION);
            //strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS);
            if (strFecha == null) {
                vpe.addError("Para la consignación la fecha de consigación no puede ser vacía.");
            }

            fecha = darFecha(strFecha);
            if (fecha == null) {
                vpe.addError("Para la consignación la fecha dada es inválida.");
            }
            /*
             * @author : CTORRES @change : SE UTILIZO LA FUNCION replaceAll()
             * PARA ELIMINAR TODOS LOS ESPACION EN BLANCO DEL NUMERO DE
             * CONSIGNACION Caso Mantis : 9844
             */

            /**
             * * @author : HGOMEZ
             *** @change : Se comenta la siguiente línea de código y se hace
             * uso de las nuevas ** constantes declaradas para la nueva interfaz
             * de formas de pago. ** Caso Mantis : 12422
             */
            numConsignacion = request.getParameter(AWPago.NUM_CONSIGNACION).replaceAll(" ", "");
            //numConsignacion = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS).replaceAll(" ", "");
            if (numConsignacion == null) {
                vpe.addError("El número de consignación es inválido.");
            }

            valor = 0.0d;
            try {
                /**
                 * * @author : HGOMEZ
                 *** @change : Se comenta la siguiente línea de código y se
                 * hace uso de las nuevas ** constantes declaradas para la nueva
                 * interfaz de formas de pago. ** Caso Mantis : 12422
                 */
                valor = Double.parseDouble(request.getParameter(AWPago.VALOR_CONSIGNACION));
                //valor = Double.parseDouble(request.getParameter(AWPago.VALOR_DOCUMENTO_PAGOS));
                if (valor <= 0) {
                    vpe.addError("Para la consignación el valor del documento no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("Para la consignación el valor de la consignación es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("Para la consignación el valor de la consignación es inválido.");
            }

            valorAplicado = 0.0d;
            try {
                /**
                 * * @author : HGOMEZ
                 *** @change : Se comenta la siguiente línea de código y se
                 * hace uso de las nuevas ** constantes declaradas para la nueva
                 * interfaz de formas de pago. ** Caso Mantis : 12422
                 */
                valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_APLICADO));
                //valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS));
                if (valorAplicado <= 0) {
                    vpe.addError("Para la consignación el valor a aplicar no puede ser negativo.");
                }
                if ((valor > 0) && (valorAplicado > valor)) {
                    vpe.addError("Para la consignación el valor a aplicar no puede ser mayor al valor del documento.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor aplicado de la consignación es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor aplicado de la consignación es inválido.");
            }

            if (vpe.getErrores().size() > 0) {
                throw vpe;
            }
        } else {
            //Inicializar atributos si es una consignacion ya registrada.

            codBanco = request.getParameter(AWPago.COD_BANCO_YA_REGISTRADA);
            /**
             * * @author : HGOMEZ
             *** @change : Se asigna valor a la variable nomBanco y se usa la
             * misma ** en lugar de de llamar la función getNombre. ** Caso
             * Mantis : 12422
             */
            nomBanco = getNombreBanco(codBanco, listaBancos);
            if ((codBanco == null) || nomBanco.equals("")) {
                vpe.addError("Para la consignación el código del Banco no puede ser vacío.");
            }

            /**
             * * @author : HGOMEZ
             *** @change : Se comentan las siguientes cuatro lineas de código
             * dado que ya no será ** necesario consultar el código de la
             * sucursal del banco por lo cual a la variable ** se le iniciará
             * vacía (""). ** Caso Mantis : 12422
             */
            codSucursal = request.getParameter(AWPago.COD_SUCURSAL_BANCO_YA_REGISTRADA);
            if (codSucursal == null) {
                vpe.addError("El código de la sucursal es inválido.");
            }

            /**
             * * @author : HGOMEZ
             *** @change : Se comenta la siguiente línea de código y se hace
             * uso de las nuevas ** constantes declaradas para la nueva interfaz
             * de formas de pago. ** Caso Mantis : 12422
             */
            strFecha = request.getParameter(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA);
            //strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);
            if (strFecha == null) {
                vpe.addError("Para la consignación la fecha de consigación no puede ser vacía.");
            }

            fecha = darFecha(strFecha);
            if (fecha == null) {
                vpe.addError("Para la consignación la fecha dada es inválida.");
            }

            /**
             * * @author : HGOMEZ
             *** @change : Se comenta la siguiente línea de código y se hace
             * uso de las nuevas ** constantes declaradas para la nueva interfaz
             * de formas de pago. ** Caso Mantis : 12422
             */
            numConsignacion = request.getParameter(AWPago.NUM_CONSIGNACION_YA_REGISTRADA);
            //numConsignacion = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA);
            if (numConsignacion == null) {
                vpe.addError("El número de consignación es inválido.");
            }

            valor = 0.0d;
            try {
                /**
                 * * @author : HGOMEZ
                 *** @change : Se comenta la siguiente línea de código y se
                 * hace uso de las nuevas ** constantes declaradas para la nueva
                 * interfaz de formas de pago. ** Caso Mantis : 12422
                 */
                valor = Double.parseDouble(request.getParameter(AWPago.VALOR_CONSIGNACION_YA_REGISTRADA));
                //valor = Double.parseDouble(request.getParameter(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA));
                if (valor <= 0) {
                    vpe.addError("Para la consignación el valor del documento no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("Para la consignación el valor de la consignación es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("Para la consignación el valor de la consignación es inválido.");
            }

            valorAplicado = 0.0d;
            try {
                /**
                 * * @author : HGOMEZ
                 *** @change : Se comenta la siguiente línea de código y se
                 * hace uso de las nuevas ** constantes declaradas para la nueva
                 * interfaz de formas de pago. ** Caso Mantis : 12422
                 */
                valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_APLICADO_YA_REGISTRADA));
                //valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS_YA_REGISTRADA));

                /**
                 * * @author : HGOMEZ
                 *** @change : Se comenta la siguiente línea de código y se
                 * hace uso de las nuevas ** constantes declaradas para la nueva
                 * interfaz de formas de pago. ** Caso Mantis : 12422
                 */
                double saldo = Double.parseDouble(request.getParameter(AWPago.SALDO_CONSIGNACION_YA_REGISTRADA));
                //double saldo = Double.parseDouble(request.getParameter(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA));

                if (valorAplicado <= 0) {
                    vpe.addError("Para la consignación el valor a aplicar no puede ser negativo.");
                }

                if ((valor > 0) && (valorAplicado > valor)) {
                    vpe.addError("Para la consignación el valor a aplicar no puede ser mayor al valor del documento.");
                }

                if (valorAplicado > saldo) {
                    vpe.addError("Para la consignación el valor a aplicar no puede ser mayor al saldo del documento.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor aplicado de la consignación es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor aplicado de la consignación es inválido.");
            }

            if (vpe.getErrores().size() > 0) {
                throw vpe;
            }

            //eliminar datos de la sesion (datos ya registrado)
            this.limpiarConsignacionYaRegistrada(request);
            this.limpiarChequeYaRegistrada(request);

        }
        /**
         * * @author : HGOMEZ
         *** @change : Se comenta la siguiente linea y se cambia de posición
         * para ** que solo se llame la función getNombre cuando sea necesario.
         * ** Caso Mantis : 12422
         */
        //String nomBanco = getNombreBanco(codBanco, listaBancos);
        Banco banco = new Banco(codBanco, nomBanco);
        DocumentoPago documentoConsignacion = new DocPagoConsignacion(banco, codSucursal, strFecha, numConsignacion, valor);
        AplicacionPago aplicacionConsignacion = new AplicacionPago(documentoConsignacion, valorAplicado);

        return aplicacionConsignacion;
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo.
     */
    private AplicacionPago construirAplicacionNuevaPagoConsignacion(
            HttpServletRequest request, ValidacionParametrosException vpe,
            List listaBancos, boolean nueva) throws ValidacionParametrosException {

        //atributos consignacion
        String codBanco;            // Banco/Franquicia
        String codSucursal = "";    // Ya no será necesario su uso por tanto se inicia vacía
        // para no afectar la lógica de negocio existente   
        String strFecha;            // Fecha documento
        Calendar fecha;
        String numConsignacion;     // Número documento pago
        double valor;               // Valor documento
        double valorAplicado;       // Valor pagado

        String nomBanco = "";

        if (nueva) {
            //Inicializar los atributos si es nueva consignacion.

            codBanco = request.getParameter(AWPago.COD_BANCO);
            nomBanco = getNombreBanco(codBanco, listaBancos);
            /**
             * @author Cesar Ramírez
             * @change: 1954.VALIDACION.BANCO.FRANQUICIA.CONSIGNACION
             *
             */
            if ((codBanco == null) || codBanco.equals("") || codBanco.equals(WebKeys.SIN_SELECCIONAR)) {
                vpe.addError("Para la consignación el código del Banco es inválido.");
            }

            strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS);
            if (strFecha == null) {
                vpe.addError("Para la consignación la fecha de consigación no puede ser vacía.");
            }

            fecha = darFecha(strFecha);
            if (fecha == null) {
                vpe.addError("Para la consignación la fecha dada es inválida.");
            }

            //Edgar Lora: Mantis: 0012422
            numConsignacion = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS).trim();
            if (numConsignacion == null || numConsignacion.length() <= 0) {
                vpe.addError("El número de consignación es inválido.");
            }

            valor = 0.0d;
            try {
                valor = Double.parseDouble(request.getParameter(AWPago.VALOR_DOCUMENTO_PAGOS));
                if (valor <= 0) {
                    vpe.addError("Para la consignación el valor del documento no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("Para la consignación el valor de la consignación es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("Para la consignación el valor de la consignación es inválido.");
            }

            valorAplicado = 0.0d;
            try {
                valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS));
                if (valorAplicado <= 0) {
                    vpe.addError("Para la consignación el valor a aplicar no puede ser negativo.");
                }
                if ((valor > 0) && (valorAplicado > valor)) {
                    vpe.addError("Para la consignación el valor pagado no puede ser mayor al valor del documento.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor aplicado de la consignación es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor aplicado de la consignación es inválido.");
            }

            if (vpe.getErrores().size() > 0) {
                throw vpe;
            }
        } else {
            codBanco = request.getParameter(AWPago.COD_BANCO_YA_REGISTRADA);
            nomBanco = getNombreBanco(codBanco, listaBancos);
            /**
             * @author Cesar Ramírez
             * @change: 1954.VALIDACION.BANCO.FRANQUICIA.CONSIGNACION
             *
             */
            if ((codBanco == null) || codBanco.equals("") || codBanco.equals(WebKeys.SIN_SELECCIONAR)) {
                vpe.addError("Para la consignación el código del Banco es inválido.");
            }

            strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);
            if (strFecha == null) {
                vpe.addError("Para la consignación la fecha de consigación no puede ser vacía.");
            }

            fecha = darFecha(strFecha);
            if (fecha == null) {
                vpe.addError("Para la consignación la fecha dada es inválida.");
            }

            numConsignacion = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA);
            if (numConsignacion == null) {
                vpe.addError("El número de consignación es inválido");
            }

            valor = 0.0d;
            try {
                valor = Double.parseDouble(request.getParameter(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA));
                if (valor <= 0) {
                    vpe.addError("Para la consignación el valor del documento no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("Para la consignación el valor de la consignación es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("Para la consignación el valor de la consignación es inválido.");
            }

            valorAplicado = 0.0d;
            try {
                valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS_YA_REGISTRADA));

                double saldo = Double.parseDouble(request.getParameter(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA));

                if (valorAplicado <= 0) {
                    vpe.addError("Para la consignación el valor a aplicar no puede ser negativo.");
                }

                if ((valor > 0) && (valorAplicado > valor)) {
                    vpe.addError("Para la consignación el valor a aplicar no puede ser mayor al valor del documento.");
                }

                if (valorAplicado > saldo) {
                    vpe.addError("Para la consignación el valor a aplicar no puede ser mayor al saldo del documento.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor aplicado de la consignación es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor aplicado de la consignación es inválido.");
            }

            if (vpe.getErrores().size() > 0) {
                throw vpe;
            }

            //eliminar datos de la sesion (datos ya registrado)
            this.limpiarConsignacionYaRegistrada(request);
            this.limpiarChequeYaRegistrada(request);

        }
        Banco banco = new Banco(codBanco, nomBanco);
        DocumentoPago documentoConsignacion = new DocPagoConsignacion(banco, codSucursal, strFecha, numConsignacion, valor);
        AplicacionPago aplicacionConsignacion = new AplicacionPago(documentoConsignacion, valorAplicado);

        return aplicacionConsignacion;
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo.
     */
    private AplicacionPago construirAplicacionPagoTarjetaCreditoDebito(
            HttpServletRequest request, ValidacionParametrosException vpe,
            List listaBancos, List listaBancosFranquicias, boolean nueva,
            int formaPagoEscogida) throws ValidacionParametrosException {

        //atributos tarjeta credito
        String codigoBanco;             // Banco
        int codigoFranquicia;           // Franquicia
        String strFecha;                // Fecha documento
        Calendar fecha;
        String numeroDocumentoPago;     // Número documento pago
        double valor;                   // Valor documento
        double valorAplicado;           // Valor pagado

        String nombreBanco = "";
        String nombreFranquicia = "";

        //Edgar Lora: Mantis: 0012422
        String numeroAprobacion = "";

        /*
            * @autor         : CTORRES
            * @mantis        : 12422
            * @Requerimiento : 049_453
         */
        List listaTc = (List) request.getSession().getAttribute(WebKeys.LISTA_TARJETA_CREDITO);
        List listaTd = (List) request.getSession().getAttribute(WebKeys.LISTA_TARJETA_DEBITO);
        String formaPago = request.getParameter(AWPago.FORMA_TIPOS_PAGOS);

        if (nueva) {
            //Inicializar los atributos si es nueva consignacion.
            int idListaBancosFranquicias = 0;
            /**
             * @author Cesar Ramírez
             * @change: 1954.VALIDACION.BANCO.FRANQUICIA.CONSIGNACION
             *
             */
            String codBancoFranquicia = request.getParameter(AWPago.COD_BANCO_FRANQUICIA);
            if ((codBancoFranquicia == null) || codBancoFranquicia.equals("") || codBancoFranquicia.equals(WebKeys.SIN_SELECCIONAR)) {
                if (formaPago.equals("11")) {
                    vpe.addError("Para la Tarjeta Crédito el código del Banco es inválido.");
                } else if (formaPago.equals("12")) {
                    vpe.addError("Para la Tarjeta Débito el código del Banco es inválido.");
                }
            } else {
                idListaBancosFranquicias = Integer.parseInt(request.getParameter(AWPago.COD_BANCO_FRANQUICIA));
            }

            String BancosFranquiciasSeleccionada = listaBancosFranquicias.get(idListaBancosFranquicias).toString();

            String[] arrayDatosBancoFranquicia = BancosFranquiciasSeleccionada.split(",");

            codigoBanco = arrayDatosBancoFranquicia[0];
            codigoFranquicia = Integer.parseInt(arrayDatosBancoFranquicia[1]);

            String[] arrayNombreBancoFranquicia = arrayDatosBancoFranquicia[2].split("-");
            nombreBanco = arrayNombreBancoFranquicia[0].replace(" ", "");
            nombreFranquicia = arrayNombreBancoFranquicia[1].replace(" ", "");

            if ((codigoBanco == null) || nombreBanco.equals("") || codigoFranquicia == 0 || nombreFranquicia.equals("")) {
                vpe.addError("Para el pago con tarjeta de crédito o tarjeta debito el código del Banco y Franquicia no puede estar vacío.");
            }

            strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS);
            if (strFecha == null) {
                vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito la fecha no puede estar vacía.");
            }

            fecha = darFecha(strFecha);
            if (fecha == null) {
                vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito la fecha dada es invalida");
            }

            //Edgar Lora: Mantis: 0012422
            numeroDocumentoPago = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS).trim();
            if (numeroDocumentoPago == null || numeroDocumentoPago.length() <= 0) {
                vpe.addError("El número del documento con tarjeta de crédito o tarjeta débito es inválido.");
            }

            valor = 0.0d;

            valorAplicado = 0.0d;
            try {
                valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS));
                if (valorAplicado <= 0) {
                    vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito el valor a aplicar no puede ser negativo.");
                }
                if ((valor > 0) && (valorAplicado > valor)) {
                    vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito el valor a aplicar no puede ser mayor al valor del documento.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor aplicado del pago con tarjeta de crédito o tarjeta débito es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor aplicado del pago con tarjeta de crédito o tarjeta débito es inválido.");
            }

            //Edgar Lora: Mantis: 0012422
            numeroAprobacion = request.getParameter(AWPago.NUMERO_APROBACION_PAGOS).replaceAll(" ", "");
            numeroAprobacion = (numeroAprobacion == null) ? "" : numeroAprobacion;
            if (numeroAprobacion.length() == 0) {
                vpe.addError("El campo número aprobación no puede estar vacío.");
            }

            /*
            * @autor         : CTORRES
            * @mantis        : 12422
            * @Requerimiento : 049_453
             */
            if (formaPago.equals("11") && listaTc != null) {
                for (Object row : listaTc) {
                    AplicacionPago aplica = (AplicacionPago) row;
                    DocPagoTarjetaCredito docPago = (DocPagoTarjetaCredito) aplica.getDocumentoPago();
                    Calendar fechaTC = darFecha(docPago.getFecha());
                    if (docPago.getBancoFranquicia().getIdBanco().equals(codigoBanco)
                            && docPago.getBancoFranquicia().getIdTipoFranquicia() == codigoFranquicia
                            && docPago.getNumeroAprobacion().equals(numeroAprobacion)
                            && fechaTC.get(Calendar.YEAR) == fecha.get(Calendar.YEAR)) {
                        vpe.addError("El número de aprobación de la transacción ya se encuentra asociada a la liquidación.");
                        break;
                    }
                }
            } else if (formaPago.equals("12") && listaTd != null) {
                for (Object row : listaTd) {
                    AplicacionPago aplica = (AplicacionPago) row;
                    DocPagoTarjetaDebito docPago = (DocPagoTarjetaDebito) aplica.getDocumentoPago();
                    Calendar fechaTC = darFecha(docPago.getFecha());
                    if (docPago.getBancoFranquicia().getIdBanco().equals(codigoBanco)
                            && docPago.getBancoFranquicia().getIdTipoFranquicia() == codigoFranquicia
                            && docPago.getNumeroAprobacion().equals(numeroAprobacion)
                            && fechaTC.get(Calendar.YEAR) == fecha.get(Calendar.YEAR)) {
                        vpe.addError("El número de aprobación de la transacción ya se encuentra asociada a la liquidación.");
                        break;
                    }
                }
            }
            if (vpe.getErrores().size() > 0) {
                throw vpe;
            }
        } else {//Inicializar atributos si es una tarjeta de credito o debito ya registrada.

            codigoBanco = request.getParameter(AWPago.COD_BANCO_YA_REGISTRADA);
            codigoFranquicia = Integer.parseInt(request.getParameter(AWPago.COD_FRANQUICIA_YA_REGISTRADA));

            nombreBanco = getNombreBanco(codigoBanco, listaBancos);
            nombreFranquicia = getNombreFranquicia(codigoFranquicia, listaBancosFranquicias);

            if ((codigoBanco == null) || nombreBanco.equals("") || codigoFranquicia == 0 || nombreFranquicia.equals("")) {
                vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito el código del Banco y Franquicia no puede ser vacío.");
            }

            strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);
            if (strFecha == null) {
                vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito no puede ser vacía.");
            }

            fecha = darFecha(strFecha);
            if (fecha == null) {
                vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito la fecha dada es inválida.");
            }

            numeroDocumentoPago = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA);
            if (numeroDocumentoPago == null) {
                vpe.addError("El número de el pago con tarjeta de crédito o tarjeta débito es inválido.");
            }

            valor = 0.0d;
            try {
                valor = Double.parseDouble(request.getParameter(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA));
                if (valor <= 0) {
                    vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito el valor del documento no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito el valor de la consignación es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito el valor de la consignación es inválido.");
            }

            valorAplicado = 0.0d;
            try {
                valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS_YA_REGISTRADA));

                double saldo = Double.parseDouble(request.getParameter(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA));

                if (valorAplicado <= 0) {
                    vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito el valor a aplicar no puede ser negativo.");
                }

                if ((valor > 0) && (valorAplicado > valor)) {
                    vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito el valor a aplicar no puede ser mayor al valor del documento.");
                }

                if (valorAplicado > saldo) {
                    vpe.addError("Para el pago con tarjeta de crédito o tarjeta débito el valor a aplicar no puede ser mayor al saldo del documento.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor aplicado del pago con tarjeta de crédito o tarjeta débito es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor aplicado del pago con tarjeta de crédito o tarjeta débito es inválido.");
            }

            if (vpe.getErrores().size() > 0) {
                throw vpe;
            }

            //eliminar datos de la sesion (datos ya registrado)
            this.limpiarConsignacionYaRegistrada(request);
            this.limpiarChequeYaRegistrada(request);
        }

        BancoFranquicia bancoFranquicia = new BancoFranquicia(codigoBanco, codigoFranquicia);

        AplicacionPago aplicacionPago = null;

        if (formaPagoEscogida == 11) {
            DocumentoPago documentoPagoTarjetaCredito = new DocPagoTarjetaCredito(bancoFranquicia, numeroDocumentoPago, strFecha, numeroAprobacion);
            aplicacionPago = new AplicacionPago(documentoPagoTarjetaCredito, valorAplicado);
        }

        if (formaPagoEscogida == 12) {
            DocumentoPago documentoPagoTarjetaDebito = new DocPagoTarjetaDebito(bancoFranquicia, numeroDocumentoPago, strFecha, numeroAprobacion);
            aplicacionPago = new AplicacionPago(documentoPagoTarjetaDebito, valorAplicado);
        }

        return aplicacionPago;
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo.
     */
    //Edgar Lora: Mantis: 0012422
    private AplicacionPago construirAplicacionPagoPse(
            HttpServletRequest request, ValidacionParametrosException vpe,
            List listaBancosFranquicias)
            throws ValidacionParametrosException {
        String numeroAprobacion = "";
        String fechaString = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS);
        //double valorPSE = 0.0d;
        numeroAprobacion = request.getParameter(AWPago.NUMERO_APROBACION_PAGOS).replaceAll(" ", "");
        numeroAprobacion = (numeroAprobacion == null) ? "" : numeroAprobacion;
        //valorPSE = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS));

        Date d = null;

        //Edgar Lora: Mantis: 0012422
        String codigoBanco;             // Banco
        int codigoFranquicia;           // Franquicia

        int idListaBancosFranquicias = 0;
        /**
         * @author Cesar Ramírez
         * @change: 1954.VALIDACION.BANCO.FRANQUICIA.CONSIGNACION
         *
         */
        String codBancoFranquicia = request.getParameter(AWPago.COD_BANCO_FRANQUICIA);
        if ((codBancoFranquicia == null) || codBancoFranquicia.equals("") || codBancoFranquicia.equals(WebKeys.SIN_SELECCIONAR)) {
            vpe.addError("Para el Pago Electrónico PSE el código del Banco es inválido.");
        } else {
            idListaBancosFranquicias = Integer.parseInt(request.getParameter(AWPago.COD_BANCO_FRANQUICIA));
        }

        String BancosFranquiciasSeleccionada = listaBancosFranquicias.get(idListaBancosFranquicias).toString();

        String[] arrayDatosBancoFranquicia = BancosFranquiciasSeleccionada.split(",");

        codigoBanco = arrayDatosBancoFranquicia[0];
        codigoFranquicia = Integer.parseInt(arrayDatosBancoFranquicia[1]);

        try {
            d = DateFormatUtil.parse(fechaString);
        } catch (ParseException e) {
            vpe.addError("El valor de la fecha de pago del timbre no es válido.");
        }

        if (numeroAprobacion.length() == 0) {
            vpe.addError("El campo número aprobación no puede estar vacío.");
        }

        //Edgar Lora: Mantis: 0012422
        if (request.getSession().getAttribute(WebKeys.FORMA_PAGO_PSE) != null) {
            vpe.addError("La forma de pago ingresada ya se encuentra asociada a esta solicitud.");
        }

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(d);

        //Edgar Lora: Mantis: 0012422
        BancoFranquicia bancoFranquicia = new BancoFranquicia(codigoBanco, codigoFranquicia);
        DocumentoPago documentopago = new DocPagoElectronicoPSE(bancoFranquicia, numeroAprobacion, fechaString);
        AplicacionPago aplicacionPagoElectronicoPSE = new AplicacionPago(documentopago, 0);

//        DocumentoPago documentopago = new DocPagoElectronicoPSE(numeroAprobacion, fechaString, valorPSE);
//        AplicacionPago aplicacionPagoElectronicoPSE = new AplicacionPago(documentopago, valorPSE);
        return aplicacionPagoElectronicoPSE;
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo.
     */
    private AplicacionPago construirAplicacionConvenio(
            HttpServletRequest request, ValidacionParametrosException vpe)
            throws ValidacionParametrosException {

        double valorConvenio = 0.0d;

        try {
            valorConvenio = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS));

            if (valorConvenio < 0) {
                vpe.addError("El valor no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            vpe.addError("El valor en convenio no es válido.");
        }

        //Edgar Lora: Mantis: 0012422
        if (request.getSession().getAttribute(WebKeys.APLICACION_CONVENIO) != null) {
            vpe.addError("La forma de pago ingresada ya se encuentra asociada a esta solicitud.");
        }

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        DocumentoPago documentoConvenio = new DocPagoConvenio(valorConvenio);
        AplicacionPago aplicacionConvenio = new AplicacionPago(documentoConvenio, valorConvenio);

        return aplicacionConvenio;
    }

    /**
     * @param request
     * @param vpe
     * @param listaBancos
     * @return Retorna un objeto con un Pago de cheque
     * @throws ValidacionParametrosException
     */
    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Se refactoriza el método.
     */
    private AplicacionPago construirAplicacionPagoCheque(
            HttpServletRequest request, ValidacionParametrosException vpe,
            List listaBancos, boolean nuevoCheque) throws ValidacionParametrosException {

        //atributos cheque
        String codBanco = "";       // Banco/Franquicia
        String codSucursal = "";    // Ya no será necesario su uso por tanto se inicia vacía
        // para no afectar la lógica de negocio existente   
        String strFechaCheque = ""; // Fecha documento
        String numCuenta = "";
        String numCheque = "";      // Número documento pago
        double valor = 0;           // Valor documento
        double valorAplicado = 0;   // Valor pagado
        double saldo = 0;

        String nomBanco = "";

        if (nuevoCheque) {
            //Obtener parametros de la tabla de nuevo cheque
            codBanco = request.getParameter(AWPago.COD_BANCO);
            nomBanco = getNombreBanco(codBanco, listaBancos);
            if ((codBanco == null) || nomBanco.equals("")) {
                vpe.addError("Para el cheque el código del Banco es inválido.");
            }

            strFechaCheque = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS);

            numCuenta = (request.getParameter(AWPago.NUM_CUENTA) != null) ? request.getParameter(AWPago.NUM_CUENTA) : "";

            numCheque = (request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS) != null) ? request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS) : "";

            if (numCheque.length() == 0) {
                vpe.addError("Para el cheque el número de cheque es inválido.");
            }

            valor = 0.0d;
            valorAplicado = 0.0d;
            saldo = 0.0d;

            try {
                valor = Double.parseDouble(request.getParameter(AWPago.VALOR_DOCUMENTO_PAGOS));

                if (valor <= 0) {
                    vpe.addError("El valor del cheque no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor del cheque es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor del cheque es inválido.");
            }

            try {
                valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS));

                if (valorAplicado <= 0) {
                    vpe.addError("Para el cheque el valor a aplicar no puede ser negativo.");
                }

                if ((valor > 0) && (valorAplicado > valor)) {
                    vpe.addError("Para el cheque el valor a aplicar no puede ser mayor al valor del documento.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor aplicado del cheque es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor aplicado del cheque es inválido.");
            }
        } else {
            //Obtener parametros de tabla de cheque ya registrado
            codBanco = request.getParameter(AWPago.COD_BANCO_YA_REGISTRADA);
            nomBanco = getNombreBanco(codBanco, listaBancos);
            if ((codBanco == null) || nomBanco.equals("")) {
                vpe.addError("Para el cheque el código del Banco es inválido.");
            }

            strFechaCheque = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);

            numCuenta = request.getParameter(AWPago.NUM_CUENTA);
            numCuenta = (numCuenta == null) ? "" : numCuenta;

            numCheque = (request.getParameter(AWPago.NUM_CHEQUE_YA_REGISTRADA) != null) ? request.getParameter(AWPago.NUM_CHEQUE_YA_REGISTRADA) : "";
            numCheque = (request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS) != null) ? request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS) : "";
            numCheque = (String) request.getSession().getAttribute(AWPago.NUM_CHEQUE_YA_REGISTRADA);

            if (numCheque.length() == 0) {
                vpe.addError("Para el cheque el número de cheque es inválido.");
            }

            valor = 0.0d;
            valorAplicado = 0.0d;
            saldo = 0.0d;

            try {
                valor = Double.parseDouble(request.getParameter(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA));

                if (valor <= 0) {
                    vpe.addError("El valor del cheque no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor del cheque es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor del cheque es inválido.");
            }

            try {
                saldo = Double.parseDouble(request.getParameter(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA));

                if (valor <= 0) {
                    vpe.addError("El saldo del cheque no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El saldo del cheque es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El saldo del cheque es inválido.");
            }

            try {
                valorAplicado = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS_YA_REGISTRADA));

                if (valorAplicado <= 0) {
                    vpe.addError("Para el cheque el valor a aplicar no puede ser negativo.");
                }

                if ((valor > 0) && (valorAplicado > saldo)) {
                    vpe.addError("Para el cheque el valor a aplicar no puede ser mayor al valor del documento.");
                }
            } catch (NumberFormatException e) {
                vpe.addError("El valor aplicado del cheque es inválido.");
            } catch (NullPointerException e) {
                vpe.addError("El valor aplicado del cheque es inválido.");
            }

        }

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        Banco banco = new Banco(codBanco, nomBanco);
        DocumentoPago documentoCheque = new DocPagoCheque(banco, codSucursal, numCuenta, numCheque, valor);
        ((DocPagoCheque) documentoCheque).setFecha(strFechaCheque);

        AplicacionPago aplicacionCheque = new AplicacionPago(documentoCheque, valorAplicado);

        //eliminar datos de la sesion (datos ya registrado)
        this.limpiarConsignacionYaRegistrada(request);
        this.limpiarChequeYaRegistrada(request);

        return aplicacionCheque;
    }

    /**
     * Este método se encarga de buscar en la lista de Bancos el nombre del
     * banco correspondiente al código de banco proporcionado.
     *
     * @param codBanco
     * @param listaBancos
     * @return Nombre del banco
     */
    private String getNombreBanco(String codBanco, List listaBancos) {
        if ((codBanco != null) && (listaBancos != null)) {
            for (int i = 0; i < listaBancos.size(); i++) {
                Banco banco = (Banco) listaBancos.get(i);

                if (banco.getIdBanco().equals(codBanco)) {
                    return banco.getNombre();
                }
            }
        }

        return "";
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo.
     */
    /**
     * Este método se encarga de buscar en la lista de Bancos el nombre del
     * banco correspondiente al código de banco proporcionado.
     *
     * @param codBanco
     * @param listaBancos
     * @return Nombre del banco
     */
    private String getNombreFranquicia(int codFranquicia, List listaBancosFranquicias) {
        if ((codFranquicia != 0) && (listaBancosFranquicias != null)) {
            for (int i = 0; i < listaBancosFranquicias.size(); i++) {
                String BancosFranquiciasSeleccionada = listaBancosFranquicias.get(i).toString();

                String[] arrayDatosBancoFranquicia = BancosFranquiciasSeleccionada.split(",");

                Banco banco = (Banco) listaBancosFranquicias.get(i);

                if (Integer.toString(codFranquicia).equals(arrayDatosBancoFranquicia[0])) {
                    String[] arrayNombreBancoFranquicia = arrayDatosBancoFranquicia[2].split("-");
                    String nombreFranquicia = arrayNombreBancoFranquicia[1];
                    return nombreFranquicia.replace(" ", "");
                }
            }
        }
        return "";
    }

    /**
     * Este método se encarga de construir el objeto Pago a partir de las
     * aplicaciones de pago empleadas
     *
     * @param request
     * @return Pago con todos los Documentos de pago asociados
     * @throws ValidacionParametrosException
     */
    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Se refactoriza .
     */
    private Pago construirPago(HttpServletRequest request)
            throws ValidacionParametrosException {
        HttpSession session = request.getSession();

        //Usuario usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);
        Liquidacion liquidacion = (Liquidacion) request.getSession().getAttribute(WebKeys.LIQUIDACION);

        System.out.println("CONSTRUIR PAGO LIQUIDACION " + liquidacion);

        if (liquidacion == null) {
            ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();
            vpe.addError("Liquidación inexistente.");
            throw vpe;
        }

        Pago pago = new Pago(liquidacion, null);
        ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();

        List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS);

        if (listaAplicaciones != null) {
            System.out.println("CONSTRUIR PAGO LISTA APLICACIONES " + listaAplicaciones.size());
            for (int i = 0; i < listaAplicaciones.size(); i++) {
                pago.addAplicacionPago((AplicacionPago) listaAplicaciones.get(i));
            }
        }

        /*AplicacionPago appTimbre = (AplicacionPago) session.getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);

        if (appTimbre != null) {
            pago.addAplicacionPago(appTimbre);
        } //else {
        List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);

        if (listaAplicaciones != null) {
            for (int i = 0; i < listaAplicaciones.size(); i++) {
                pago.addAplicacionPago((AplicacionPago) listaAplicaciones.get(
                        i));
            }
        }

        listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);

        if (listaAplicaciones != null) {
            for (int i = 0; i < listaAplicaciones.size(); i++) {
                pago.addAplicacionPago((AplicacionPago) listaAplicaciones.get(i));
            }
        }

        listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_TARJETA_CREDITO);

        if (listaAplicaciones != null) {
            for (int i = 0; i < listaAplicaciones.size(); i++) {
                pago.addAplicacionPago((AplicacionPago) listaAplicaciones.get(i));
            }
        }

        listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_TARJETA_DEBITO);

        if (listaAplicaciones != null) {
            for (int i = 0; i < listaAplicaciones.size(); i++) {
                pago.addAplicacionPago((AplicacionPago) listaAplicaciones.get(i));
            }
        }

        AplicacionPago appEfectivo = (AplicacionPago) session.getAttribute(WebKeys.APLICACION_EFECTIVO);

        if (appEfectivo != null) {
            pago.addAplicacionPago(appEfectivo);
        }

        AplicacionPago appConvenio = (AplicacionPago) session.getAttribute(WebKeys.APLICACION_CONVENIO);

        if (appConvenio != null) {
            pago.addAplicacionPago(appConvenio);
        }

        AplicacionPago appPagoElectronicoPSE = (AplicacionPago) session.getAttribute(WebKeys.FORMA_PAGO_PSE);

        if (appPagoElectronicoPSE != null) {
            pago.addAplicacionPago(appPagoElectronicoPSE);
        }
        //}
         */
        if (pago.getAplicacionPagos().isEmpty()) {
            vpe.addError("No se ha registrado ningún tipo de pago.");
            throw vpe;
        }
        return pago;
    }

    /**
     * Este metodo verifica la forma de las fechas y que sea una fecha valida.
     *
     * @param fechaInterfaz
     * @return Calendar
     */
    private static Calendar darFecha(String fechaInterfaz) {
        java.util.Date date = null;

        try {
            date = DateFormatUtil.parse(fechaInterfaz);
            if (fechaInterfaz.indexOf("-") != -1) {
                return null;
            }
        } catch (ParseException e) {
            return null;
        } catch (Throwable t) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        String[] partido = fechaInterfaz.split("/");
        Date dHoy = new Date();

        Calendar hoy = Calendar.getInstance();
        hoy.setTime(dHoy);

        if ((partido.length == 3) && (partido[0].length() > 0)
                && (partido[1].length() > 0) && (partido[2].length() > 0)) {
            int dia = Integer.parseInt(partido[0]);
            int mes = Integer.parseInt(partido[1]) - 1;
            int año = Integer.parseInt(partido[2]);
            calendar.set(año, mes, dia);

            if ((calendar.get(Calendar.YEAR) == año)
                    && (calendar.get(Calendar.MONTH) == mes)
                    && (calendar.get(Calendar.DAY_OF_MONTH) == dia)
                    && !calendar.after(hoy)) {
                return calendar;
            }
        }

        return null;
    }

    /**
     * Este método es usado para buscar una forma de pago de Tarjeta Credito
     * dando los datos basicos de esta.
     *
     * @param request
     * @return null
     * @throws AccionWebException
     */
    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Se refactoriza el método.
     */
    private Evento buscarConsignacion(HttpServletRequest request)
            throws AccionWebException {

        ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();
        HttpSession session = request.getSession();

        List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);

        //Verificar que el Codigo del banco y el numero de la consignacion no esten vacios.
        String codBanco = request.getParameter(AWPago.COD_BANCO_YA_REGISTRADA);

        String nomBanco = getNombreBanco(codBanco, listaBancos);

        if (codBanco == null) {
            codBanco = request.getParameter(AWPago.COD_BANCO);
        }

        if ((codBanco == null) || codBanco.equals("")) {
            vpe.addError("Para la consignación el código del Banco no puede ser vacío.");
        } else {
            session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, codBanco);
        }

        String numConsignacion = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA);
        //if(numConsignacion == null || numConsignacion.equals(""))
        //    numConsignacion = request.getParameter(AWPago.NUM_CONSIGNACION_YA_REGISTRADA);

        if (numConsignacion == null || numConsignacion.equals("")) {
            vpe.addError("Por favor digite un número de consignación.");
        } else {
            //session.setAttribute(AWPago.NUM_CONSIGNACION_YA_REGISTRADA, numConsignacion);
            session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA, numConsignacion);
        }

        String strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);
        //if(strFecha == null || strFecha.equals(""))
        //    strFecha = request.getParameter(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA);

        if (strFecha == null) {
            vpe.addError("Para la consignación la fecha de consigación no puede ser vacía.");
        } else {
            //session.setAttribute(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA, strFecha);
            session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA, strFecha);
        }

        Calendar fecha = darFecha(strFecha);
        if (fecha == null) {
            vpe.addError("Para la consignación la fecha dada es inválida.");
        }

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        //Si los datos son correctos crear evento para buscar la consignacion;
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);
        Banco banco = new Banco(codBanco, nomBanco);
        DocumentoPago documentoConsignacion = new DocPagoConsignacion(banco, "", strFecha, numConsignacion, 0);
        AplicacionPago aplicacionConsignacion = new AplicacionPago(documentoConsignacion, 0);
        EvnPago evento = new EvnPago(usuario, aplicacionConsignacion);
        evento.setTipoEvento(EvnPago.BUSCAR_APLICACION);
        evento.setNuevaAplicacion(false);
        return evento;
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo. Este método es usado para buscar una tarjeta de credito dando los datos basicos
     * de esta.
     */
    private Evento buscarPagoTarjetaCredito(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();
        HttpSession session = request.getSession();

        List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);
        List listaBancosFranquicias = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS);

        //Verificar que el Codigo del banco y el numero de la consignacion no esten vacios.
        String codigoBanco = request.getParameter(AWPago.COD_BANCO);
        String nombreBanco = getNombreBanco(codigoBanco, listaBancos);

        if (codigoBanco == null) {
            codigoBanco = request.getParameter(AWPago.COD_BANCO);
        }

        int codigoFranquicia = Integer.parseInt(request.getParameter(AWPago.COD_FRANQUICIA));
        String nombreFranquicia = getNombreFranquicia(codigoFranquicia, listaBancosFranquicias);

        if ((codigoBanco == null) || nombreBanco.equals("") || codigoFranquicia == 0 || nombreFranquicia.equals("")) {
            vpe.addError("Para el pago con tarjeta de crédito el código del Banco no puede ser vacío.");
        } else {
            session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, codigoBanco);
            session.setAttribute(AWPago.COD_FRANQUICIA_YA_REGISTRADA, codigoFranquicia);
        }

        String numeroDocumentoPago = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA);
        if (numeroDocumentoPago == null || numeroDocumentoPago.equals("")) {
            vpe.addError("Por favor digite un número de la tarjeta de crédito.");
        } else {
            session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA, numeroDocumentoPago);
        }

        String strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);
        if (strFecha == null) {
            vpe.addError("Para el pago con tarjeta de crédito la fecha de consigación no puede ser vacía.");
        } else {
            //session.setAttribute(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA, strFecha);
            session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA, strFecha);
        }

        Calendar fecha = darFecha(strFecha);
        if (fecha == null) {
            vpe.addError("Para el pago con tarjeta de crédito la fecha dada es inválida.");
        }

        //Edgar Lora: Mantis: 0012422
        String numeroAprobacion;
        numeroAprobacion = request.getParameter(AWPago.NUMERO_APROBACION_PAGOS).replaceAll(" ", "");
        numeroAprobacion = (numeroAprobacion == null) ? "" : numeroAprobacion;
        if (numeroAprobacion.length() == 0) {
            vpe.addError("El campo número aprobación no puede estar vacío.");
        }

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        //Si los datos son correctos crear evento para buscar la consignacion;
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);
        BancoFranquicia bancoFranquicia = new BancoFranquicia(codigoBanco, codigoFranquicia);
        DocumentoPago documentoPagoTarjetaCredito = new DocPagoTarjetaCredito(bancoFranquicia, numeroDocumentoPago, strFecha, numeroAprobacion);
        AplicacionPago aplicacionPagoTarjetaCredito = new AplicacionPago(documentoPagoTarjetaCredito, 0);
        EvnPago evento = new EvnPago(usuario, aplicacionPagoTarjetaCredito);
        evento.setTipoEvento(EvnPago.BUSCAR_APLICACION);
        evento.setNuevaAplicacion(false);
        return evento;
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo. Este método es usado para buscar una tarjeta de debito dando los datos basicos
     * de esta.
     */
    private Evento buscarPagoTarjetaDebito(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();
        HttpSession session = request.getSession();

        List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);
        List listaBancosFranquicias = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS);

        //Verificar que el Codigo del banco y el numero de la consignacion no esten vacios.
        String codigoBanco = request.getParameter(AWPago.COD_BANCO);
        String nombreBanco = getNombreBanco(codigoBanco, listaBancos);

        if (codigoBanco == null) {
            codigoBanco = request.getParameter(AWPago.COD_BANCO);
        }

        int codigoFranquicia = Integer.parseInt(request.getParameter(AWPago.COD_FRANQUICIA));
        String nombreFranquicia = getNombreFranquicia(codigoFranquicia, listaBancosFranquicias);

        if ((codigoBanco == null) || nombreBanco.equals("") || codigoFranquicia == 0 || nombreFranquicia.equals("")) {
            vpe.addError("Para el pago con tarjeta débito el código del Banco no puede ser vacío.");
        } else {
            session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, codigoBanco);
            session.setAttribute(AWPago.COD_FRANQUICIA_YA_REGISTRADA, codigoFranquicia);
        }

        String numeroDocumentoPago = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA);
        if (numeroDocumentoPago == null || numeroDocumentoPago.equals("")) {
            vpe.addError("Por favor digite un número de tarjeta débito.");
        } else {
            session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA, numeroDocumentoPago);
        }

        String strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);
        if (strFecha == null) {
            vpe.addError("Para el pago con tarjeta débito la fecha de consigación no puede ser vacía.");
        } else {
            //session.setAttribute(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA, strFecha);
            session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA, strFecha);
        }

        Calendar fecha = darFecha(strFecha);
        if (fecha == null) {
            vpe.addError("Para el pago con tarjeta débito la fecha dada es inválida.");
        }

        //Edgar Lora: Mantis: 0012422
        String numeroAprobacion;
        numeroAprobacion = request.getParameter(AWPago.NUMERO_APROBACION_PAGOS).replaceAll(" ", "");
        numeroAprobacion = (numeroAprobacion == null) ? "" : numeroAprobacion;
        if (numeroAprobacion.length() == 0) {
            vpe.addError("El campo número aprobación no puede estar vacío.");
        }

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        //Si los datos son correctos crear evento para buscar la consignacion;
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);
        BancoFranquicia bancoFranquicia = new BancoFranquicia(codigoBanco, codigoFranquicia);
        DocumentoPago documentoPagoTarjetaDebito = new DocPagoTarjetaDebito(bancoFranquicia, numeroDocumentoPago, strFecha, numeroAprobacion);
        AplicacionPago aplicacionPagoTarjetaDebito = new AplicacionPago(documentoPagoTarjetaDebito, 0);
        EvnPago evento = new EvnPago(usuario, aplicacionPagoTarjetaDebito);
        evento.setTipoEvento(EvnPago.BUSCAR_APLICACION);
        evento.setNuevaAplicacion(false);
        return evento;
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo. Este método es usado para buscar una tarjeta de debito dando los datos basicos
     * de esta.
     */
    private Evento buscarPagoPse(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();
        HttpSession session = request.getSession();

        String numeroAprobacion = request.getParameter(AWPago.NUMERO_APROBACION_PAGOS_YA_REGISTRADA);
        if (numeroAprobacion == null || numeroAprobacion.equals("")) {
            vpe.addError("Por favor digite un número de aprobación.");
        } else {
            session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA, numeroAprobacion);
        }

        String strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);
        if (strFecha == null) {
            vpe.addError("Para el pago electrónico PSE la fecha del documento no puede ser vacía.");
        } else {
            session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA, strFecha);
        }

        Calendar fecha = darFecha(strFecha);
        if (fecha == null) {
            vpe.addError("Para el pago electrónico PSE la fecha ingresada es inválida.");
        }

        //Edgar Lora: Mantis: 0012422
        String codigoBanco;             // Banco
        int codigoFranquicia;           // Franquicia
        List listaBancosFranquicias = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS);

        int idListaBancosFranquicias = 0;
        if (!request.getParameter(AWPago.COD_BANCO_FRANQUICIA).equals("SIN_SELECCIONAR")) {
            idListaBancosFranquicias = Integer.parseInt(request.getParameter(AWPago.COD_BANCO_FRANQUICIA));
        } else {
            vpe.addError("Debe seleccionar Banco/Franquicia.");
        }
        String BancosFranquiciasSeleccionada = listaBancosFranquicias.get(idListaBancosFranquicias).toString();

        String[] arrayDatosBancoFranquicia = BancosFranquiciasSeleccionada.split(",");

        codigoBanco = arrayDatosBancoFranquicia[0];
        codigoFranquicia = Integer.parseInt(arrayDatosBancoFranquicia[1]);

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

//        double valorPSE = Double.parseDouble(request.getParameter(AWPago.VALOR_PAGAR_PAGOS));
//        if (valorPSE == 0.0d) {
//            vpe.addError("Por favor ingres un valor para el Pago PSE");
//        } else {
        session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA, strFecha);
//        }
        //Si los datos son correctos crear evento para buscar la consignacion;
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);
        //Edgar Lora: Mantis: 0012422
        BancoFranquicia bancoFranquicia = new BancoFranquicia(codigoBanco, codigoFranquicia);
        DocumentoPago documentoPagoPse = new DocPagoElectronicoPSE(bancoFranquicia, numeroAprobacion, strFecha);
        AplicacionPago aplicacionPagoPse = new AplicacionPago(documentoPagoPse, 0);
        EvnPago evento = new EvnPago(usuario, aplicacionPagoPse);
        evento.setTipoEvento(EvnPago.BUSCAR_APLICACION);
        evento.setNuevaAplicacion(false);
        return evento;
    }

    private void limpiarConsignacionYaRegistrada(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.removeAttribute(AWPago.COD_BANCO_YA_REGISTRADA);
        session.removeAttribute(AWPago.COD_SUCURSAL_BANCO_YA_REGISTRADA);
        session.removeAttribute(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA);
        /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Se comenta las siguientes líneas de código y se hace uso de las nuevas
         *** constantes declaradas para la nueva interfaz de formas de pago.
         */
        //session.removeAttribute(AWPago.NUM_CONSIGNACION_YA_REGISTRADA);
        //session.removeAttribute(AWPago.SALDO_CONSIGNACION_YA_REGISTRADA);
        //session.removeAttribute(AWPago.VALOR_CONSIGNACION_YA_REGISTRADA);
        session.removeAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA);
        session.removeAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA);
        session.removeAttribute(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA);
    }

    /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Método nuevo.
     */
    private void limpiarTarjetaCreditoDebitoYaRegistrada(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.removeAttribute(AWPago.COD_BANCO_YA_REGISTRADA);
        session.removeAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA);
        session.removeAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);
        session.removeAttribute(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA);
    }

    /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Método nuevo.
     */ private void limpiarPseYaRegistrada(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.removeAttribute(AWPago.NUMERO_APROBACION_PAGOS_YA_REGISTRADA);
        session.removeAttribute(AWPago.FECHA_DOCUMENTO_PAGOS_YA_REGISTRADA);
        session.removeAttribute(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA);
    }

    /**
     * Este método es usado para buscar un cheque dado los datos basicos de
     * este.
     *
     * @param request
     * @return null
     * @throws AccionWebException
     */
    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Se refactoriza el método.
     */
    private Evento buscarCheque(HttpServletRequest request)
            throws AccionWebException {

        ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();
        HttpSession session = request.getSession();

        List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);

        //Verificar que el Codigo del banco y el numero de la consignacion no esten vacios.
        String codBanco = request.getParameter(AWPago.COD_BANCO_YA_REGISTRADA);
        if ((codBanco == null) || codBanco.equals("") || getNombreBanco(codBanco, listaBancos).equals("")) {
            vpe.addError("Para el cheque el código del Banco no puede ser vacío.");
        } else {
            session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, codBanco);
        }

        /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Se comenta la siguiente linea y se hace uso de la 
         * nueva variable NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA.
         */
        //String numCheque = request.getParameter(AWPago.NUM_CHEQUE_YA_REGISTRADA);
        String numCheque = request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA);
        if (numCheque == null || numCheque.equals("")) {
            vpe.addError("Por favor digite un número de cheque.");
        } else {
            /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Se comenta la siguiente linea y se hace uso de la 
         * nueva variable NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA.
             */
            //session.setAttribute(AWPago.NUM_CONSIGNACION_YA_REGISTRADA, numCheque);
            session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA, numCheque);
        }

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        //Si los datos son correctos crear evento para buscar el cheque;
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);
        String nomBanco = getNombreBanco(codBanco, listaBancos);
        Banco banco = new Banco(codBanco, nomBanco);
        DocumentoPago documentoCheque = new DocPagoCheque(banco, "", "", numCheque, 0);
        AplicacionPago aplicacionConsignacion = new AplicacionPago(documentoCheque, 0);
        EvnPago evento = new EvnPago(usuario, aplicacionConsignacion);
        evento.setTipoEvento(EvnPago.BUSCAR_APLICACION);
        evento.setNuevaAplicacion(false);
        return evento;
    }

    private void limpiarChequeYaRegistrada(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.removeAttribute(AWPago.COD_BANCO_YA_REGISTRADA);
        session.removeAttribute(AWPago.NUM_CHEQUE_YA_REGISTRADA);
        session.removeAttribute(AWPago.FECHA_CHEQUE_YA_REGISTRADA);
        /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Se comenta las siguientes líneas de código y se hace uso de las nuevas
         * constantes declaradas para la nueva interfaz de formas de pago.
         */
        //session.removeAttribute(AWPago.SALDO_CHEQUE_YA_REGISTRADA);
        //session.removeAttribute(AWPago.VALOR_CHEQUE_YA_REGISTRADA);
        session.removeAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA);
        session.removeAttribute(AWPago.VALOR_DOCUMENTO_PAGOS_YA_REGISTRADA);
    }

    /**
     * Este metodo borra de la sesion la informacion que se puso en los campos,
     * ademas de la informacion de las matriculas que se pone en sesion.
     *
     * @param request HttpServletRequest
     * @return Evento nulo ya que no se requiere que viaje hasta el negocio
     */
    private Evento removerInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        session.removeAttribute(AWPago.LLAVE_GENERAL_APLICACION);
        session.removeAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA);
        
        session.removeAttribute(AWPago.FECHA_CHEQUE_YA_REGISTRADA);
        session.removeAttribute(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA);
        session.removeAttribute(WebKeys.PAGO_REGISTRO_LIQUIDACION);

        session.removeAttribute(CActo.NUM_ACTOS);
        session.removeAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
        session.removeAttribute(AWLiquidacionRegistro.LISTA_ELEMENTOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute(AWLiquidacionRegistro.LISTA_TURNOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);

        //variables de acto
        session.removeAttribute(CActo.ID_ACTO);
        session.removeAttribute(CActo.TIPO_ACTO);
        session.removeAttribute(CActo.TIPO_DERECHO);
        session.removeAttribute(CActo.VALOR_ACTO);
        session.removeAttribute(CActo.COBRA_IMPUESTO);
        session.removeAttribute(CActo.CUANTIA);
        session.removeAttribute(CActo.TIPO_TARIFA);
        session.removeAttribute(CActo.VALOR_IMPUESTO);

        //limpiar variables de certificados asociados
        List temp = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS);
        Iterator it = temp.iterator();
        String nombreTarifa;
        while (it.hasNext()) {
            ElementoLista el = (ElementoLista) it.next();
            nombreTarifa = el.getValor();
            session.removeAttribute("NUMERO_" + nombreTarifa);
        }
        session.removeAttribute("NUMERO_" + CTipoTarifa.TARIFA_ESPECIAL);

        //remover atributos de respuesta de la preliquidacion
        session.removeAttribute(AWLiquidacionRegistro.PRELIQUIDACION);

        //remover atributos de encabezado de documento
        session.removeAttribute(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
        session.removeAttribute(CLiquidacionTurnoRegistro.DESCRIPCION);
        session.removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.CALENDAR);

        session.removeAttribute("AUX");
        session.removeAttribute(WebKeys.LIQUIDACION);
        session.removeAttribute(AWLiquidacionRegistro.VIENE_DE_CAJERO);
        session.removeAttribute(WebKeys.ES_CAJERO_REGISTRO);
        session.removeAttribute(WebKeys.GENERAR_PAGO_EFECTIVO);
        session.removeAttribute(WebKeys.VALOR_LIQUIDACION);
        session.removeAttribute(AWLiquidacionRegistro.ID_SOLICITUD);
        session.removeAttribute(AWLiquidacionRegistro.SOLICITUD);

        session.removeAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_SEGREGACION);
        session.removeAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA);
        session.removeAttribute(CActo.CUANTIA_EDICION);
        session.removeAttribute("TELEFONO");
        session.removeAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute("TIPO_TARIFA_CERTIFICADOSOPCION_MATRICULA");
        session.removeAttribute("TIPO_TARIFA_CERTIFICADOSOPCION_SEGREGACION");
        session.removeAttribute(AWLiquidacionRegistro.AGREGAR_MATRICULA_REGISTRO);
        session.removeAttribute(WebKeys.ES_CAJERO);
        session.removeAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
        session.removeAttribute(CFolio.ID_MATRICULA);
        session.removeAttribute(WebKeys.TIPO_OFICINA_I_N);
        session.removeAttribute(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_SEGREGACION);
        session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
        session.removeAttribute(WebKeys.ITEM);
        session.removeAttribute(CActo.NOMBRE_TIPO_CALCULO);
        session.removeAttribute(WebKeys.POSICION);
        session.removeAttribute(CSubtipoAtencion.LISTA_SUBTIPO_CHEQUEO);
        session.removeAttribute(CTipoNota.DEVOLUTIVA);
        session.removeAttribute(CNota.NOMBRE);
        session.removeAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL);
        session.removeAttribute(WebKeys.ACCION);
        Enumeration keysEnum = session.getAttributeNames();
        while (keysEnum.hasMoreElements()) {
            String clave = (String) keysEnum.nextElement();
            if (clave.startsWith("ACTO")) {
                session.removeAttribute(clave);
            }
        }

        session.removeAttribute("imageField.y");
        session.removeAttribute("imageField.x");
        session.removeAttribute("y");
        session.removeAttribute("x");
        session.removeAttribute("antiguoSistemaExistente");

        return null;
    }

    /*
            * @autor         : CTORRES
            * @mantis        : 12422
            * @Requerimiento : 049_453
            * @descripcion   : Métodos javascripts para limpiar formulario despues de seleccionar un nuevo tipo de pago.
     */
    private void preservarInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(AWPago.COD_BANCO, request.getParameter(AWPago.COD_BANCO));
        session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, request.getParameter(AWPago.COD_BANCO_YA_REGISTRADA));
        //session.setAttribute(AWPago.COD_BANCO_FRANQUICIA, request.getParameter(AWPago.COD_BANCO_FRANQUICIA));
        session.setAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS, request.getParameter(AWPago.NUMERO_DOCUMENTO_PAGOS));
        session.setAttribute(AWPago.VALOR_DOCUMENTO_PAGOS, request.getParameter(AWPago.VALOR_DOCUMENTO_PAGOS));
        session.setAttribute(AWPago.NUMERO_APROBACION_PAGOS, request.getParameter(AWPago.NUMERO_APROBACION_PAGOS));
        session.setAttribute(AWPago.FECHA_DOCUMENTO_PAGOS, request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS));
        session.setAttribute(AWPago.VALOR_PAGAR_PAGOS, request.getParameter(AWPago.VALOR_PAGAR_PAGOS));

        /*CANALES RECAUDO*/
        session.setAttribute(AWPago.COD_BANCO_FRANQUICIA, request.getParameter(AWPago.COD_BANCO_FRANQUICIA));
        session.setAttribute(AWPago.FECHA_DOCU, request.getParameter(AWPago.FECHA_DOCU));
        session.setAttribute(AWPago.ID_CUENTA_BANCARIA, request.getParameter(AWPago.ID_CUENTA_BANCARIA));
        session.setAttribute(AWPago.NO_DOC_PAGO, request.getParameter(AWPago.NO_DOC_PAGO));
        session.setAttribute(AWPago.NO_APROBACION, request.getParameter(AWPago.NO_APROBACION));
        session.setAttribute(AWPago.NO_PIN, request.getParameter(AWPago.NO_PIN));
        session.setAttribute(AWPago.NO_NIR, request.getParameter(AWPago.NO_NIR));
        session.setAttribute(AWPago.NO_CUS, request.getParameter(AWPago.NO_CUS));
        session.setAttribute(AWPago.VLR_DOC_PAGO, request.getParameter(AWPago.VLR_DOC_PAGO));
        session.setAttribute(AWPago.NO_CONSIGNACION, request.getParameter(AWPago.NO_CONSIGNACION));
        session.setAttribute(AWPago.VLR_LIQUIDADO, request.getParameter(AWPago.VLR_LIQUIDADO));

    }

    /*
            * @autor         : CTORRES
            * @mantis        : 12422
            * @Requerimiento : 049_453
            * @descripcion   : Métodos javascripts para limpiar formulario despues de seleccionar un nuevo tipo de pago.
     */
    private void eliminarInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(AWPago.COD_BANCO, "SIN_SELECCIONAR");
        session.removeAttribute(AWPago.COD_BANCO_YA_REGISTRADA);
        //session.removeAttribute(AWPago.COD_BANCO_FRANQUICIA);
        session.removeAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS);
        session.removeAttribute(AWPago.VALOR_DOCUMENTO_PAGOS);
        session.removeAttribute(AWPago.NUMERO_APROBACION_PAGOS);
        session.removeAttribute(AWPago.FECHA_DOCUMENTO_PAGOS);
        session.removeAttribute(AWPago.VALOR_PAGAR_PAGOS);

        /*CANALES RECAUDO*/
        session.removeAttribute(AWPago.COD_BANCO_FRANQUICIA);
        session.removeAttribute(AWPago.FECHA_DOCU);
        session.removeAttribute(AWPago.ID_CUENTA_BANCARIA);
        session.removeAttribute(AWPago.NO_DOC_PAGO);
        session.removeAttribute(AWPago.NO_APROBACION);
        session.removeAttribute(AWPago.NO_PIN);
        session.removeAttribute(AWPago.NO_NIR);
        session.removeAttribute(AWPago.NO_CUS);
        session.removeAttribute(AWPago.VLR_DOC_PAGO);
        session.removeAttribute(AWPago.NO_CONSIGNACION);
        session.removeAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA);

    }

    private Evento cargarFormasPago(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        session.setAttribute(AWPago.CARGAR_CAMPOS_CAPTURA, false);

        String idCanalRecaudo = request.getParameter(AWPago.CANALES_RECAUDO);
        String formasPagoMap = request.getParameter(AWPago.FORMAS_PAGO_MAP);
//        System.out.println("ID PARA EL CANAL DE RECAUDO 22 ENERO " + idCanalRecaudo);
//        System.out.println("FORMAS PAGO MAP 22 ENERO " + formasPagoMap);

        List listaFormasPago = new Vector();
        String id = null;
        String valor = null;
        ElementoLista obj = new ElementoLista();
        String[] formasPago = formasPagoMap.split(";");
        for (int i = 0; i < formasPago.length; i++) {
            boolean llaveForma = true;
            String[] formasPago2 = formasPago[i].split(",");
            Iterator it = listaFormasPago.iterator();
            while (it.hasNext()) {
                 obj = (ElementoLista)it.next();
                 id = obj.getId();
                 valor = obj.getValor();
                if(id.equalsIgnoreCase(formasPago2[1])&&valor.equalsIgnoreCase(formasPago2[2])){
                    llaveForma = false;
                }  
            }            
            if (formasPago2[0].equals(idCanalRecaudo)&& llaveForma == true) {
                //System.out.println("FORMA PAGO PARA EL CANAL CON ID "+idCanalRecaudo+": "+formasPago2[2]);
                listaFormasPago.add(new ElementoLista(formasPago2[1], formasPago2[2]));
            }

        }

        session.setAttribute(WebKeys.LISTA_FORMAS_PAGO_CANAL, listaFormasPago);


        /*Circulo circulo  = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        System.out.println("CIRCULO PARA BUSCAR CANALES "+circulo.getIdCirculo());*/
        this.eliminarInfo(request);
        return null;
    }

    private Evento cargarCampoCapturaXFormaPago(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        session.setAttribute(AWPago.CARGAR_CAMPOS_CAPTURA, true);

        String idFormaPago = request.getParameter(AWPago.FORMA_TIPOS_PAGOS);
        String idCanalRecaudo = request.getParameter(AWPago.CANALES_RECAUDO);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        ValidacionParametrosException vpe = new ValidacionParametrosException();

        if ((idFormaPago == null) || idFormaPago.equals("") || idFormaPago.equals(WebKeys.SIN_SELECCIONAR)) {
            vpe.addError("Se debe seleccionar la forma de pago.");
            throw vpe;
        }

        HermodService hs;
        try {
            hs = HermodService.getInstance();
            session.setAttribute(WebKeys.LISTA_CAMPOS_CAPTURA_X_FORMA, hs.camposCapturaXFormaPago(idFormaPago));
            session.setAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS_X_CIRCULO, hs.getCuentasBancariasXCirculoCanalForma(circulo, idCanalRecaudo, idFormaPago));

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    private Evento cuentasBancariasXCirculo(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        HermodService hs;

        try {
            hs = HermodService.getInstance();
            session.setAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS_X_CIRCULO, hs.getCuentasBancariasXCirculo(circulo));

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    /**
     * @param request
     * @return null
     * @throws AccionWebException
     */
    private Evento adicionarAplicacion(HttpServletRequest request)
            throws AccionWebException {

        ValidacionParametrosException vpe = new ValidacionParametrosException();

        String canalRecaudo = request.getParameter(AWPago.CANALES_RECAUDO);
        String formaPago = request.getParameter(AWPago.FORMA_TIPOS_PAGOS);
        String cuentaDestino = request.getParameter(AWPago.ID_CUENTA_BANCARIA);
        
        if (formaPago == null) {
            formaPago = request.getParameter(AWPago.FORMA_PAGO);
        }

        if ((canalRecaudo == null) || canalRecaudo.equals("") || canalRecaudo.equals(WebKeys.SIN_SELECCIONAR)) {
            vpe.addError("Se debe seleccionar un canal de recaudo.");
            throw vpe;
        }

        if ((formaPago == null) || formaPago.equals("") || formaPago.equals(WebKeys.SIN_SELECCIONAR)) {
            vpe.addError("Se debe seleccionar la forma de pago.");
            throw vpe;
        }

        String aplicacionNueva = "";
        String nombreLista = "";
        HttpSession session = request.getSession();
        //List listaAplicaciones = null;

        preservarInfo(request);

        int formaPagoEscogida = Integer.parseInt(formaPago);
        int canalRecaudoEscogido = Integer.parseInt(canalRecaudo);

        List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS);

        double liquidacion = ((Liquidacion) session.getAttribute(WebKeys.LIQUIDACION)).getValor();
        /**
         * En caso de que haya certificados asociados se agrega el valor de la
         * liquidacion de los certificados
         */
        Double valorCertificadosAsociados = (Double) request.getSession().getAttribute(WebKeys.VALOR_CERTIFICADOS_ASOCIADOS);
        if (valorCertificadosAsociados != null) {
            liquidacion += valorCertificadosAsociados.doubleValue();
        }

        if (listaAplicaciones == null) {
            listaAplicaciones = new Vector();
        }

        AplicacionPago aplicacionPago = null;
        
        if (formaPagoEscogida > 0) {
            aplicacionPago = construirNuevaAplicacionPagoGeneral(request, formaPagoEscogida, canalRecaudoEscogido);
        } else {
            vpe.addError("La forma de pago es inválida.");
        }

        if (aplicacionPago != null) {
            session.removeAttribute(WebKeys.APLICACION_PAGO);
            session.setAttribute(WebKeys.APLICACION_PAGO, aplicacionPago);
            session.setAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS, listaAplicaciones);
            String noCanal = null;
            
            try{
            HermodService hs = HermodService.getInstance();
            List listaCamposCaptura = hs.camposCapturaXFormaPago(formaPagoEscogida + "");
            Iterator itr = listaCamposCaptura.iterator();
            while(itr.hasNext()){
               CamposCaptura cc = (CamposCaptura) itr.next();
               if(cc.getFormName().equals(AWPago.NO_CONSIGNACION)){
                   noCanal = request.getParameter(AWPago.NO_CONSIGNACION);
                   break;
               } else if(cc.getFormName().equals(AWPago.NO_APROBACION)){
                   noCanal = request.getParameter(AWPago.NO_APROBACION);
                   break;
               } else if(cc.getFormName().equals(AWPago.NO_PIN)){
                   noCanal = request.getParameter(AWPago.NO_PIN);
                   break;
               } else if(cc.getFormName().equals(AWPago.NO_CUS)){
                   noCanal = request.getParameter(AWPago.NO_CUS);
                   break;
               } else if(cc.getFormName().equals(AWPago.NO_NIR)){
                   noCanal = request.getParameter(AWPago.NO_NIR);
                   break;
               } 
            }
            } catch(HermodException he){
                System.out.println("ERROR AL VALIDAR CAMPOS CAPTURA" + he);
            }
            
            Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);
            EvnPago evento = new EvnPago(usuario, aplicacionPago);
            evento.setBanco(cuentaDestino);
            evento.setCanal(formaPago);
            evento.setNoCanal(noCanal);
            boolean nuevaGeneral = true;
            String nueva = request.getParameter(AWPago.LLAVE_GENERAL_APLICACION);
            if (nueva != null) {
                if (nueva.equals("true")) {
                    nuevaGeneral = true;
                } else {
                    nuevaGeneral = false;
                }
            }
            evento.setNuevaAplicacion(nuevaGeneral);
            return evento;
        } else {
            vpe.addError("La aplicación de pago no pudo ser adicionada..");
//            throw new AplicacionPagoNoAdicionadaException("La aplicación de pago no pudo ser adicionada.");
        }
        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        return null;
    }

    private AplicacionPago construirNuevaAplicacionPagoGeneral(HttpServletRequest request,
            int formaPagoEscogida, int canalRecaudoEscogido) throws ValidacionParametrosException {
        ValidacionParametrosException vpe = new ValidacionParametrosRegistroPagoException();
        HttpSession session = request.getSession();

        List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);

        List listaBancosFranquicias = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS);

        return this.construirAplicacionPagoGeneral(request, vpe, formaPagoEscogida, listaBancos, listaBancosFranquicias, canalRecaudoEscogido);

    }

    private AplicacionPago construirAplicacionPagoGeneral(
            HttpServletRequest request, ValidacionParametrosException vpe, int formaPagoEscogida,
            List listaBancos, List listaBancosFranquicias, int canalRecaudoEscogido)
            throws ValidacionParametrosException {
        HermodService hs;
        int codigoFranquicia = 0, idTipoFranquicia =0;
        CanalesRecaudo canalesRecaudo;
        TipoPago tipoPago;
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        String fechaDocu = null, noConsignacion = null, codSucursal = null, noCuenta = null, noAprobacion = null, noDocumento = null,
                noPin = null, noNir = null, noCus = null, codigoBanco = null, nombreCanal = null, nombreFormaPago = null,
                nombreBancoFranquicia = null, idCtp = null, saldoDocumentoString = null;
        
        String cuentaDestino = request.getParameter(AWPago.ID_CUENTA_BANCARIA);

        double valor = 0d, valorAplicado = 0d, valorDocumento = 0d, valorLiquidado = 0d, saldoDocumento = 0d;
        Banco banco = new Banco();
        BancoFranquicia bancoFranquicia = new BancoFranquicia();
        Date date = new Date();
        DocumentoPago documentoPago = new DocPagoGeneral();
        try {
//            ((DocPagoGeneral) documentoPago).setIdTipoDocumentoPago(formaPagoEscogida);
            hs = HermodService.getInstance();
            List listaCamposCaptura = hs.camposCapturaXFormaPago(formaPagoEscogida + "");
            canalesRecaudo = hs.getCanalRecaudoByID(canalRecaudoEscogido);
            tipoPago = hs.getTipoPagoByID(formaPagoEscogida);

            saldoDocumentoString = request.getParameter(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA);
            if(saldoDocumentoString != "" && saldoDocumentoString != null){
                saldoDocumento = Double.parseDouble(request.getParameter(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA));
            }

            if (canalesRecaudo != null) {
                idCtp = hs.getIdCtpByParamenters(String.valueOf(formaPagoEscogida), circulo.getIdCirculo(), canalesRecaudo.getIdCanal(),cuentaDestino);
//                ((DocPagoGeneral) documentoPago).setNombreCanal(canalesRecaudo.getNombreCanal());
                nombreCanal = canalesRecaudo.getNombreCanal();
            }

            if (tipoPago != null) {
//                ((DocPagoGeneral) documentoPago).setNombreFormaPago(tipoPago.getNombre());
                nombreFormaPago = tipoPago.getNombre();
            }
                        
            Iterator itr = listaCamposCaptura.iterator();
            int idCuentaBancaria = 0;

            while (itr.hasNext()) {

                CamposCaptura cc = (CamposCaptura) itr.next();

                String campoValidar = request.getParameter(cc.getFormName());

                if (cc.getFormName().equals(AWPago.CUENTA_DESTINO)) {                    

                    if (cuentaDestino == null || cuentaDestino.trim().equals("") || cuentaDestino.equals(WebKeys.SIN_SELECCIONAR)) {
                        vpe.addError("El campo CUENTA DESTINO no puede estar vacío.");
//********************************************* Parte por definir Geremias *****************************************
                    } else {
                        idCuentaBancaria = Integer.parseInt(cuentaDestino);
                        CuentasBancarias cuentasBancarias;

                        try {
                            cuentasBancarias = hs.getCuentasBancariasByID(idCuentaBancaria);
                            if (cuentasBancarias != null) {
//                                ((DocPagoGeneral) documentoPago).setCuentasBancarias(cuentasBancarias);
//                                banco = cuentasBancarias.getBanco();
//                                ((DocPagoGeneral) documentoPago).setBanco(banco);
                            }
                        } catch (HermodException e) {
                            vpe.addError("No existe la cuenta bancaria relacionada, por favor validar");
                        }
//********************************************* Parte por definir Geremias *****************************************
                    }
                } else if (cc.getFormName().equals(AWPago.BANCO_FRANQUICIA)) {

                    int idBancoFranquicia = 0;

                    String bancoFranquiciaString = request.getParameter(AWPago.COD_BANCO_FRANQUICIA);

                    if (bancoFranquiciaString == null || bancoFranquiciaString.trim().equals("") || bancoFranquiciaString.equals(WebKeys.SIN_SELECCIONAR)) {
                        vpe.addError("El campo " + cc.getFormName() + " no puede estar vacío.");
                    } else {
                        idBancoFranquicia = Integer.parseInt(bancoFranquiciaString);
                        String BancosFranquiciasSeleccionada = listaBancosFranquicias.get(idBancoFranquicia).toString();

                        String[] arrayDatosBancoFranquicia = BancosFranquiciasSeleccionada.split(",");

                        codigoBanco = arrayDatosBancoFranquicia[0];
                        codigoFranquicia = Integer.parseInt(arrayDatosBancoFranquicia[1]);
                        bancoFranquicia  = new BancoFranquicia(codigoBanco, codigoFranquicia);
//                        bancoFranquicia = bf;
//                        ((DocPagoGeneral) documentoPago).setBancoFranquicia(bf);
//                        ((DocPagoGeneral) documentoPago).setNombreBancoFranquicia(arrayDatosBancoFranquicia[2]);
                        nombreBancoFranquicia = arrayDatosBancoFranquicia[2];
                    }

                } else if (cc.getFormName().equals(AWPago.VLR_DOC_PAGO)) {
                    valor = 0.0d;
                    try {
                        valor = Double.parseDouble(request.getParameter(AWPago.VLR_DOC_PAGO));
                        if (valor <= 0) {
                            vpe.addError("El valor del documento no puede ser negativo.");
                        }
                        valorDocumento = valor;
//                        documentoPago.setValorDocumento(valor);
                    } catch (NumberFormatException e) {
                        vpe.addError("El valor de la consignación es inválido.");
                    } catch (NullPointerException e) {
                        vpe.addError("El valor de la consignación es inválido.");
                    }
                } else if (cc.getFormName().equals(AWPago.VLR_LIQUIDADO)) {
                    valorAplicado = 0.0d;
                    try {
                        valorAplicado = Double.parseDouble(request.getParameter(AWPago.VLR_LIQUIDADO));
                        if (valorAplicado <= 0) {
                            vpe.addError("El valor a aplicar no puede ser negativo.");
                        }
                        if ((valor > 0) && (valorAplicado > valor)) {
                            vpe.addError("El valor a aplicar no puede ser mayor al valor del documento.");
                        }
                        valorLiquidado = valorAplicado;
                        if (valorDocumento <= 0) {
                            valorDocumento = valorAplicado;
                        }
                    } catch (NumberFormatException e) {
                        vpe.addError("El valor aplicado es inválido.");
                    } catch (NullPointerException e) {
                        vpe.addError("El valor aplicado es inválido.");
                    }
                } else if (cc.getFormName().equals(AWPago.NO_CONSIGNACION)) {
                    noConsignacion = request.getParameter(AWPago.NO_CONSIGNACION).replaceAll(" ", "");
                    if (noConsignacion == null || noConsignacion.trim().equals("")) {
                        vpe.addError("El número de consignación es inválido.");
                    }
                } else if (cc.getFormName().equals(AWPago.FECHA_DOCU)) {
                    String fechaDocument = request.getParameter(cc.getFormName());

                    if (fechaDocument == null || fechaDocument.trim().equals("")) {
                        vpe.addError("El campo FECHA no puede ser vacía.");
                    }
                    Calendar fecha = darFecha(fechaDocument);
                    if (fecha == null) {
                        vpe.addError("El campo FECHA es inválida, no tiene el formato adecuado.");
                    }
                    fechaDocu = fechaDocument;

                } else if (cc.getFormName().equals(AWPago.NO_DOC_PAGO)) {
                    if ((campoValidar == null) || campoValidar.trim().equals("")) {
                        vpe.addError("El campo " + cc.getFormLabel() + " no puede estar vacío.");
                    }
                    noDocumento = campoValidar;
                } else if (cc.getFormName().equals(AWPago.NO_APROBACION)) {
                    if ((campoValidar == null) || campoValidar.trim().equals("")) {
                        vpe.addError("El campo " + cc.getFormLabel() + " no puede estar vacío.");
                    }
                    noAprobacion = campoValidar;
                } else if (cc.getFormName().equals(AWPago.NO_PIN)) {
                    if ((campoValidar == null) || campoValidar.trim().equals("")) {
                        vpe.addError("El campo " + cc.getFormLabel() + " no puede estar vacío.");
                    }
                    noAprobacion = campoValidar;
                } else if (cc.getFormName().equals(AWPago.NO_NIR)) {
                    if ((campoValidar == null) || campoValidar.trim().equals("")) {
                        vpe.addError("El campo " + cc.getFormLabel() + " no puede estar vacío.");
                    }
                    noDocumento = campoValidar;
                } else if (cc.getFormName().equals(AWPago.NO_CUS)) {
                    if ((campoValidar == null) || campoValidar.trim().equals("")) {
                        vpe.addError("El campo " + cc.getFormLabel() + " no puede estar vacío.");
                    }
                    noDocumento = campoValidar;
                } else {
                    System.out.println("Campo diferente a validar");
                    if ((campoValidar == null) || campoValidar.trim().equals("")) {
                        vpe.addError("Problemas validando campos");
                    }
                }
            }
            
            String cuentaDest = request.getParameter(AWPago.ID_CUENTA_BANCARIA);
            String idBanco = null;
            try{
                 idBanco = hs.getIdBanco(cuentaDest);
                } catch(HermodException he){
                    System.out.println("NO HA SIDO POSIBLE OBTENER EL ID DEL BANCO EN LA APLICACION PAGO "+ he);
                }
            banco.setIdBanco(idBanco);
            
        } catch (HermodException e) {
            vpe.addError("No existen campos de captura configurados para validar");
        }
        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }
        documentoPago = new DocPagoGeneral(formaPagoEscogida, fechaDocu, noConsignacion, codSucursal,
                noCuenta, valorDocumento, noAprobacion, noDocumento, valorLiquidado, Integer.valueOf(idCtp),
                nombreFormaPago, nombreCanal, nombreBancoFranquicia, bancoFranquicia, banco, saldoDocumento);
        request.getSession().setAttribute(AWPago.NO_APROBACION, noAprobacion);
        AplicacionPago aplicacionPago = new AplicacionPago(documentoPago, valorAplicado);
        return aplicacionPago;
    }
    
        private Evento buscarGeneral(HttpServletRequest request)throws AccionWebException {
            
            ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();
            HttpSession session = request.getSession();      
            String codSucursal = null, noCuenta = null, nombreCanal = null, nombreFormaPago = null,
            nombreBancoFranquicia = null, idCtp = "0";
            int codigoFranquicia = 0;
            double valorDocumento = 0d, valorLiquidado = 0d, saldoDocumento = 0d;
            BancoFranquicia bancoFranquicia = new BancoFranquicia();
            String noAprobacion = null;
            String noDocumento = null;
            String noConsignacion = null;
            String cuentaDestino = request.getParameter(AWPago.ID_CUENTA_BANCARIA);
            Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);       
            int idBancoFranquicia = 0, idTipoFranquicia = 0;
            String codigoBanco = null, fechaDocu = null;
            Banco banco = new Banco();
            
            String formaPago = request.getParameter(AWPago.FORMA_TIPOS_PAGOS);

            if (formaPago == null) {
                formaPago = request.getParameter(AWPago.FORMA_PAGO);
            }

            if ((formaPago == null) || formaPago.equals("") || formaPago.equals(WebKeys.SIN_SELECCIONAR)) {
                vpe.addError("Se debe seleccionar la forma de pago.");
                throw vpe;
            }

            int formaPagoEscogida = Integer.parseInt(formaPago);
            
            if (noAprobacion == null) {
                noAprobacion = request.getParameter(AWPago.NO_APROBACION);
            }
            if(noAprobacion == null){
                noAprobacion = request.getParameter(AWPago.NO_PIN);
            }                        
            
            fechaDocu = request.getParameter(AWPago.FECHA_DOCU);
            noConsignacion = request.getParameter(AWPago.NO_CONSIGNACION); 
            List listaBancosFranquicias = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS);
            if (!request.getParameter(AWPago.VLR_DOC_PAGO).isEmpty()) {
                valorDocumento = Double.parseDouble(request.getParameter(AWPago.VLR_DOC_PAGO)); 
            }
            valorLiquidado = Double.parseDouble(request.getParameter(AWPago.VLR_LIQUIDADO)); 
            String bancoFranquiciaString = request.getParameter(AWPago.COD_BANCO_FRANQUICIA);
            
                    if (bancoFranquiciaString == null || bancoFranquiciaString.trim().equals("") || bancoFranquiciaString.equals(WebKeys.SIN_SELECCIONAR)) {
                        vpe.addError("El campo " + AWPago.COD_BANCO_FRANQUICIA + " no puede estar vacío.");
                    } else {
                        idBancoFranquicia = Integer.parseInt(bancoFranquiciaString);
                        String BancosFranquiciasSeleccionada = listaBancosFranquicias.get(idBancoFranquicia).toString();

                        String[] arrayDatosBancoFranquicia = BancosFranquiciasSeleccionada.split(",");

                        codigoBanco = arrayDatosBancoFranquicia[0];
                        codigoFranquicia = Integer.parseInt(arrayDatosBancoFranquicia[1]);
                        bancoFranquicia  = new BancoFranquicia(codigoBanco, codigoFranquicia);
                        banco.setIdBanco(codigoBanco);
                        nombreBancoFranquicia = arrayDatosBancoFranquicia[2];
                    }
            
            List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);
            String nomBanco = getNombreBanco(codigoBanco, listaBancos);
            banco = new Banco(codigoBanco, nomBanco);
            DocumentoPago documentoGeneral = new DocPagoGeneral(formaPagoEscogida, fechaDocu, noConsignacion, codSucursal,
            noCuenta, valorDocumento, noAprobacion, noDocumento, valorLiquidado, Integer.valueOf(idCtp),
            nombreFormaPago, nombreCanal, nombreBancoFranquicia, bancoFranquicia, banco, saldoDocumento);        
            AplicacionPago aplicacionConsignacion = new AplicacionPago(documentoGeneral, 0);
            EvnPago evento = new EvnPago(usuario, aplicacionConsignacion);
            evento.setTipoEvento(EvnPago.BUSCAR_APLICACION);
            evento.setNuevaAplicacion(false);
            request.setAttribute(AWPago.LLAVE_GENERAL_APLICACION, false);
            return evento;
    }       
}
