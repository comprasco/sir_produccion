package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.DocPagoElectronicoPSE;
import gov.sir.core.negocio.modelo.DocPagoConvenio;
import gov.sir.core.negocio.modelo.BancoFranquicia;
import gov.sir.core.negocio.modelo.DocPagoTarjetaCredito;
import gov.sir.core.negocio.modelo.DocPagoTarjetaDebito;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancosXCirculo;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CBanco;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPago;
import gov.sir.core.web.acciones.registro.AWLiquidacionRegistro;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;
import org.auriga.core.modelo.transferObjects.Rol;
import java.io.IOException;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/*
* @autor         : HGOMEZ 
* @mantis        : 12422 
* @Requerimiento : 049_453 
* @descripcion   : Se importa ValidacionesSIR para tener acceso a las formas de pago
 */
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import co.com.iridium.generalSIR.comun.GeneralSIRException;
import gov.sir.core.negocio.modelo.CamposCaptura;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.TipoPago;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author mmunoz
 */
public class DatosPagoHelper extends Helper {

    /**
     * Constante para definir un esapcio en blanco de html
     */
    private static String blanco = "&nbsp;";
    /**
     * Constante donde se almacenan la lista de los bancos donde se puede hacer
     * el pago
     */
    private static ListaElementoHelper listaBancos = null;
    /**
     * Constante donde se almacenan la lista de las cuentas bancarias para el
     * circulo
     */
    private static ListaElementoHelper listaCuentasBancarias = null;
    /**
     * Constante donde se almacenan la lista de los bancos donde se puede hacer
     * el pago
     */
    private static ListaElementoHelper listaBancosYa = null;
    /**
     * Constante donde se guarda la lista de los cheques que se han ingresado
     */
    private List cheques;
    /**
     * Constante donde se guarda la lista de las consignaciones que se han
     * ingresado
     */
    private List consignaciones;

    /**
     * Constante donde se guarda la lista de los pagos que se han ingresado
     */
    private List pagosRegistrados;
    /**
     * Forma de pago de recibo timbre
     */

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Variables para controlar formas de pago correspondientes
    * a tarjeta de crédito y tarjeta debito.
     */
    private List tarjetasCredito;
    private List tarjetasDebito;
    //private List pse;

    private DocPagoTimbreConstanciaLiquidacion formaReciboTimbre = null;
    /**
     * Valor total de la liquidación
     */
    private double valorLiquidacion = 0;
    /**
     * Documento de pago en efectivo
     */
    private DocPagoEfectivo docEfectivo = null;

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Documento de pago convenio y PSE.
     */
    private DocPagoConvenio docConvenio = null;

    private DocPagoGeneral docGeneral = null;

    private DocPagoElectronicoPSE docPagoElectronicoPSE = null;
    /**
     * Tipo de formato numérico utilizado por el helper
     */
    private NumberFormat formateador = new DecimalFormat("###,###,###,###,###,###,###,###.00");
    /**
     * Tipo de formato numérico utilizado por el helper
     */
    private NumberFormat formateador2 = new DecimalFormat("#########################.00");
    /**
     * Flag que indica que se debe mostrar forma de pago consignación
     */
    private boolean seQuiereConsignacion = false;
    /**
     * Flag que indica que se debe mostrar forma de pago cheques
     */
    private boolean seQuiereCheques = false;
    /**
     * Flag que indica que se debe mostrar forma de pago efectivo
     */
    private boolean seQuiereEfectivo = false;

    /**
     * Flag que indica que si existen o no formas de pago;
     */
    private boolean existeFormaPago = false;
    /**
     * Flag que indica que se debe mostrar forma de timbre de constancia
     * liquidación
     */
    private boolean seQuiereTimbreConstanciaLiquidacion = false;
    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Se declaran las siguientes variables para validar si se mostrará las
     * opciones pago electronico pse, tarjeta credito y tarjeta debito.
     */
    private boolean seQuiereConvenio = false;
    private boolean seQuierePagoElectronicoPSE = false;
    private boolean seQuiereTarjetaCredito = false;
    private boolean seQuiereTarjetaDebito = false;
    /**
     * Flag que indica si luego de la generación del turno se debe asignar la
     * primera fase al usuario/estación en sesión
     */
    private boolean asignarEstacion = false;
    /**
     * Flag que indica si la liquidacion es de registro.
     */
    private boolean liquidacionRegistro = false;
    /**
     * Constante donde se guarda la lista de las marcas de los cheques que se
     * han ingresado
     */
    private List marcasCheques;
    /**
     * Constante donde se guarda la lista de las marcas las consignaciones que
     * se han ingresado
     */
    private List marcasConsignaciones;

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Se declaran las siguientes variables para dar solución
    * al caso.
     */
    private List marcasPse;
    private List marcasTarjetasCredito;
    private List marcasTarjetasDebito;

    private TextHelper textHelper;
    private String fechaHoy;
    private String idCirculo;
    /*
     * JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion
     * _Tarifas Resolucion_81_2010-imm.doc Nueva Variable para manejar eventos
     * de redondeo.
     */
    private boolean liquidacionRedondeo = false;

    private boolean camposCaptura = true;
    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Constante donde se almacenan la lista de las formas de pago que tiene asignado cada
     * circulo.
     */
    List listaFormasPago = new Vector();
    List listaCanalesRecaudo = new Vector();
    List listaBancoFranquicia = new Vector();
    String formasPago = "";

    ValidacionesSIR validacionesSIR = new ValidacionesSIR();
    private static ListaElementoHelper listaElementoHelperFormasPago = null;
    private static ListaElementoHelper listaElementoHelperBancoFranquicia = null;

    private static ListaElementoHelper listaElementoHelperCanalesRecaudo = null;

    //Edgar Lora: Mantis: 0012422
    private double valorTotalLiquidado;

    /**
     * Constructor Helper Pagos. Si se desea que el pago efectivo no sea
     * aplicado por defecto toca colocar la constante
     *
     * @see <WebKeys.GENERAR_PAGO_EFECTIVO> como Boolean false
     *
     * @param context
     * @param valorLiquidado
     * @param listaConsignaciones lista con las consignaciones agregadas por el
     * usuario
     * @param listaCheques lista con los cheques agregados por el usuario
     * @param appEfectivo Pago en efectivo
     * @param timbreBanco Pago con recibo de constancia de Liquidación en el
     * banco
     */
    public DatosPagoHelper(HttpServletRequest request, double valorLiquidado,
            List listaConsignaciones, List listaCheques,
            AplicacionPago appEfectivo, AplicacionPago timbreBanco,
            List listaMarcasConsignaciones, List listaMarcasCheques, List listaPagosRegistrados) {

        System.out.println("CONSTRUCTOR DATOS PAGO HELPER PEQUEÑO");

        Boolean generarEfectivo = (Boolean) request.getSession().getAttribute(WebKeys.GENERAR_PAGO_EFECTIVO);
        generarEfectivo = (generarEfectivo == null) ? new Boolean(false)
                : generarEfectivo;

        if (request.getSession().getAttribute(WebKeys.LISTA_FORMAS_PAGO_CANAL) != null) {
            listaFormasPago = (List) request.getSession().getAttribute(WebKeys.LISTA_FORMAS_PAGO_CANAL);
        }


        /*
         * if ((appEfectivo == null) && generarEfectivo.booleanValue()) {
         * DocumentoPago documentoEfectivo = new
         * DocPagoEfectivo(valorLiquidado); appEfectivo = new
         * AplicacionPago(documentoEfectivo, valorLiquidado);
         * request.getSession().setAttribute(WebKeys.APLICACION_EFECTIVO,
         * appEfectivo);
         * request.getSession().setAttribute(WebKeys.GENERAR_PAGO_EFECTIVO, new
         * Boolean(false)); }
         */
        //if (listaBancos == null) {
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        if (circulo != null && circulo.getIdCirculo() != null) {
            idCirculo = circulo.getIdCirculo();
        }
        listaBancos = this.cargarListaBancos(request.getSession().getServletContext(), AWPago.COD_BANCO);
        listaCuentasBancarias = this.cargarListaCuentasBancarias(request.getSession().getServletContext(),request, AWPago.ID_CUENTA_BANCARIA);
        //}
        if (listaBancosYa == null) {
            listaBancosYa = this.cargarListaBancos(request.getSession().getServletContext(), AWPago.COD_BANCO_YA_REGISTRADA);
        }

        if (listaConsignaciones == null) {
            listaConsignaciones = new Vector();
        }

        if (listaPagosRegistrados == null) {
            listaPagosRegistrados = new Vector();
        }

        if (listaCheques == null) {
            listaCheques = new Vector();
        }

        if (listaMarcasConsignaciones == null) {
            listaMarcasConsignaciones = new ArrayList();
        }
        this.marcasConsignaciones = listaMarcasConsignaciones;
        if (listaMarcasCheques == null) {
            listaMarcasCheques = new ArrayList();
        }
        this.marcasCheques = listaMarcasCheques;
        this.valorLiquidacion = valorLiquidado;
        this.consignaciones = listaConsignaciones;
        this.pagosRegistrados = listaPagosRegistrados;
        this.cheques = listaCheques;

        /*if (appEfectivo != null) {
            docEfectivo = (DocPagoEfectivo) appEfectivo.getDocumentoPago();
        }*/
        if (timbreBanco != null) {
            this.formaReciboTimbre = (DocPagoTimbreConstanciaLiquidacion) timbreBanco.getDocumentoPago();
        }
        Date dHoy = new Date();
        Calendar hoy = Calendar.getInstance();
        hoy.setTime(dHoy);
        fechaHoy = hoy.get(Calendar.DAY_OF_MONTH) + "/" + (hoy.get(Calendar.MONTH) + 1) + "/" + hoy.get(Calendar.YEAR);

    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Se refactoriza el método.
     */
    public DatosPagoHelper(HttpServletRequest request, double valorLiquidado,
            List listaConsignaciones, List listaCheques,
            List listaTarjetasCredito, List listaTarjetasDebito,
            AplicacionPago appPagoElectronicoPSE, AplicacionPago appConvenio,
            AplicacionPago appEfectivo, AplicacionPago timbreBanco,
            List listaMarcasConsignaciones, List listaMarcasCheques,
            List listaMarcasTarjetasCredito, List listaMarcasTarjetasDebito,
            List listaMarcasPse, AplicacionPago appGeneral, List listaPagosRegistrados) {
        if (request.getSession().getAttribute(WebKeys.LISTA_FORMAS_PAGO_CANAL) != null) {
            listaFormasPago = (List) request.getSession().getAttribute(WebKeys.LISTA_FORMAS_PAGO_CANAL);
        }

        Boolean generarEfectivo = (Boolean) request.getSession().getAttribute(WebKeys.GENERAR_PAGO_EFECTIVO);
        generarEfectivo = (generarEfectivo == null) ? new Boolean(false)
                : generarEfectivo;

        /*if ((appEfectivo == null) && generarEfectivo.booleanValue()) {
			DocumentoPago documentoEfectivo = new DocPagoEfectivo(valorLiquidado);
			appEfectivo = new AplicacionPago(documentoEfectivo, valorLiquidado);
			request.getSession().setAttribute(WebKeys.APLICACION_EFECTIVO,
				appEfectivo);
			request.getSession().setAttribute(WebKeys.GENERAR_PAGO_EFECTIVO,
				new Boolean(false));
		}*/
        //if (listaBancos == null) {
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        if (circulo != null && circulo.getIdCirculo() != null) {
            idCirculo = circulo.getIdCirculo();
        }

        /*listaElementoHelperFormasPago = this.cargarListaPagos(AWPago.FORMA_TIPOS_PAGOS);
        listaElementoHelperFormasPago.setSelected(request.getParameter(AWPago.FORMA_TIPOS_PAGOS));*/
 /*listaElementoHelperCanalesRecaudo = this.cargarCanales(AWPago.CANALES_RECAUDO);
        listaElementoHelperCanalesRecaudo.setSelected(request.getParameter(AWPago.CANALES_RECAUDO));*/
        listaBancos = this.cargarListaBancos(request.getSession().getServletContext(), AWPago.COD_BANCO);
        listaCuentasBancarias = this.cargarListaCuentasBancarias(request.getSession().getServletContext(),request, AWPago.ID_CUENTA_BANCARIA);

        String codBancoRegistrado = (String) request.getSession().getAttribute(AWPago.COD_BANCO_YA_REGISTRADA);
        if (codBancoRegistrado != null) {
            //listaBancos.setSelected(codBancoRegistrado);
        }
        //}
        if (listaBancosYa == null) {
            listaBancosYa = this.cargarListaBancos(request.getSession().getServletContext(), AWPago.COD_BANCO_YA_REGISTRADA);
        }

        ValidacionesSIR validacion = new ValidacionesSIR();
        try {
            listaBancoFranquicia = validacion.getBancoFranquicia();
        } catch (GeneralSIRException ex) {
            Logger.getLogger(DatosPagoHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        listaElementoHelperBancoFranquicia = this.cargarListaBancosFranquicia(request.getSession().getServletContext(), AWPago.COD_BANCO_FRANQUICIA);
        if (listaConsignaciones == null) {
            listaConsignaciones = new Vector();
        }
        this.consignaciones = listaConsignaciones;

        if (listaPagosRegistrados == null) {
            listaPagosRegistrados = new Vector();
        }
        this.pagosRegistrados = listaPagosRegistrados;

        if (listaCheques == null) {
            listaCheques = new Vector();
        }
        this.cheques = listaCheques;

        if (listaTarjetasCredito == null) {
            listaTarjetasCredito = new Vector();
        }
        this.tarjetasCredito = listaTarjetasCredito;

        if (listaTarjetasDebito == null) {
            listaTarjetasDebito = new Vector();
        }
        this.tarjetasDebito = listaTarjetasDebito;

//        if (listaPse == null) {
//            listaPse = new Vector();
//        }
//        this.pse = listaPse;
        if (listaMarcasConsignaciones == null) {
            listaMarcasConsignaciones = new ArrayList();
        }
        this.marcasConsignaciones = listaMarcasConsignaciones;

        if (listaMarcasCheques == null) {
            listaMarcasCheques = new ArrayList();
        }
        this.marcasCheques = listaMarcasCheques;

        if (listaMarcasTarjetasCredito == null) {
            listaMarcasTarjetasCredito = new Vector();
        }
        this.marcasTarjetasCredito = listaMarcasTarjetasCredito;

        if (listaMarcasTarjetasDebito == null) {
            listaMarcasTarjetasDebito = new Vector();
        }
        this.marcasTarjetasDebito = listaMarcasTarjetasDebito;

        if (listaMarcasPse == null) {
            listaMarcasPse = new Vector();
        }
        this.marcasPse = listaMarcasPse;

        this.valorLiquidacion = valorLiquidado;

        /*if (appEfectivo != null) {
            docEfectivo = (DocPagoEfectivo) appEfectivo.getDocumentoPago();
        }*/
        if (appConvenio != null) {
            docConvenio = (DocPagoConvenio) appConvenio.getDocumentoPago();
        }

        if (appGeneral != null) {
            docGeneral = (DocPagoGeneral) appGeneral.getDocumentoPago();
        }

        if (appPagoElectronicoPSE != null) {
            docPagoElectronicoPSE = (DocPagoElectronicoPSE) appPagoElectronicoPSE.getDocumentoPago();
        }

        if (timbreBanco != null) {
            this.formaReciboTimbre = (DocPagoTimbreConstanciaLiquidacion) timbreBanco.getDocumentoPago();
        }

        Date dHoy = new Date();
        Calendar hoy = Calendar.getInstance();
        hoy.setTime(dHoy);
        fechaHoy = hoy.get(Calendar.DAY_OF_MONTH) + "/" + (hoy.get(Calendar.MONTH) + 1) + "/" + hoy.get(Calendar.YEAR);
    }

    /**
     * Carga la lista de bancos que se encuentra en contexto y la convierte en
     * una lista helper
     *
     * @param context
     */
    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Se refactoriza el método.
     */
    private ListaElementoHelper cargarListaBancos(ServletContext context, String nombreSession) {
        ListaElementoHelper lista = new ListaElementoHelper();
        lista.setCssClase("camposformtext");
        lista.setId(nombreSession);
        lista.setNombre(nombreSession);
        //lista.setFuncion(" style=\"display:none\" ");

        List bancosXCirculo = (List) context.getAttribute(WebKeys.LISTA_RELACION_BANCOS_CIRCULO);
        if (bancosXCirculo == null) {
            bancosXCirculo = new Vector();
        }

        Iterator itBancos = bancosXCirculo.iterator();
        List nBancos = new Vector();

        while (itBancos.hasNext()) {
            BancosXCirculo bancoXCirculo = (BancosXCirculo) itBancos.next();
            if (bancoXCirculo != null && bancoXCirculo.getIdCirculo() != null && bancoXCirculo.getIdCirculo().equals(idCirculo)) {
                if (bancoXCirculo.getBanco() != null) {
                    nBancos.add(new ElementoLista(bancoXCirculo.getBanco().getIdBanco(),
                            bancoXCirculo.getBanco().getIdBanco() + " - " + bancoXCirculo.getBanco().getNombre()));
                }

            }
        }
        lista.setTipos(nBancos);
        return lista;
    }

    /**
     * Carga la lista de cuentas bancarias para el circulo
     *
     */
    private ListaElementoHelper cargarListaCuentasBancarias(ServletContext context, HttpServletRequest request, String nombre) {
        ListaElementoHelper lista = new ListaElementoHelper();
        lista.setCssClase("camposformtext");
        lista.setId(nombre);
        lista.setNombre(nombre);
        //lista.setFuncion(" style=\"display:none\" ");

        List cuentasBancariasXCirculo = (List) request.getSession().getAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS_X_CIRCULO);
        if (cuentasBancariasXCirculo == null) {
            cuentasBancariasXCirculo = new Vector();
        }

        Iterator itCuentasBancariasXCirculo = cuentasBancariasXCirculo.iterator();
        List cuentasBancarias = new Vector();

        while (itCuentasBancariasXCirculo.hasNext()) {
            CuentasBancarias cb = (CuentasBancarias) itCuentasBancariasXCirculo.next();
            if (cb != null) {
                cuentasBancarias.add(new ElementoLista(cb.getId() + "",
                        cb.getBanco().getNombre() + " - " + cb.getNroCuenta()));

            }
        }
        lista.setTipos(cuentasBancarias);
        return lista;
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Carga la lista de formas de pago que se encuentran 
    * aprobadas para el circulo actual y la convierte en una lista helper.
     */
    private ListaElementoHelper cargarListaPagos(String nombreSession) {
        ListaElementoHelper lista = new ListaElementoHelper();
        lista.setCssClase("camposformtext");
        lista.setId(nombreSession);
        lista.setNombre(nombreSession);
        /*
            * @autor         : CTORRES
            * @mantis        : 12422
            * @Requerimiento : 049_453
            * @descripcion   : Métodos javascripts para limpiar formulario despues de seleccionar un nuevo tipo de pago.
         */
        lista.setFuncion("onchange=\"cargarCamposCaptura();\"");
        lista.setTipos(listaFormasPago);
        return lista;
    }

    /*
    * @autor         : Geremias Ortiz
    * @descripcion   : Carga el listado de canales de recaudo para el circulo
    actual.
     */
    private ListaElementoHelper cargarCanales(String nombre) {
        ListaElementoHelper lista = new ListaElementoHelper();
        lista.setCssClase("camposformtext");
        lista.setId(nombre);
        lista.setNombre(nombre);
        lista.setFuncion("onchange=\"cargarFormasPago('" + formasPago + "');\"");
        lista.setTipos(listaCanalesRecaudo);
        return lista;
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Carga la lista de bancos y franquicias.
     */
    private ListaElementoHelper cargarListaBancosFranquicia(ServletContext context, String nombreSession) {
        ListaElementoHelper lista = new ListaElementoHelper();
        lista.setCssClase("camposformtext");
        lista.setId(nombreSession);
        lista.setNombre(nombreSession);
        //lista.setFuncion(" style=\"display:none\" ");
        List listaBancoFranquiciaFinal = new Vector();
        String bancoFranquicia;
        int i = 0;
        for (Object datos : listaBancoFranquicia) {
            bancoFranquicia = (String) datos;
            String[] arrayDatosBancoFranquicia = bancoFranquicia.split(",");
            listaBancoFranquiciaFinal.add(new ElementoLista(Integer.toString(i), arrayDatosBancoFranquicia[2]));
            i++;
        }

        lista.setTipos(listaBancoFranquiciaFinal);

        return lista;
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Encuentra todos los bancos y las franquicias que tengan
    * asociadas.
     */
    private String encontrarBancosFranquicia(ServletContext context, String idBanco, String idBancoFranquicia) {
        List bancosFranquicias = (List) context.getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS);
        String datosBancoFranquicia = "";
        String nombreBancoFranquicia = "";
        for (Object datos : bancosFranquicias) {
            datosBancoFranquicia = (String) datos;
            String[] arrayDatosBancoFranquicia = datosBancoFranquicia.split(",");
            if (arrayDatosBancoFranquicia[0].equals(idBanco) && arrayDatosBancoFranquicia[1].equals(idBancoFranquicia)) {
                nombreBancoFranquicia = arrayDatosBancoFranquicia[2];
                break;
            }
        }
        return nombreBancoFranquicia;
    }

    /**
     * Este método recoje los atributos necesarios del request para poder
     * inicializar los atributos propios de la clase
     *
     * @param request Trae toda la informacion que se a guardado sobre el
     * usuario
     */
    public void setProperties(HttpServletRequest request)
            throws HelperException {

        textHelper = new TextHelper();
    }

    /**
     * Este método pinta en la pantalla de manera agradable las formas de pago
     *
     * @param request Trae toda la informacion que ha sido guardada del usuario
     * @param out Se utilizar para poder escribir el codigo HTML de manera
     * dinamica
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
            throws IOException, HelperException {
        //Aca se muestran las opciones del pago cuando previamente no se ha seleccionado ninguno
        //Edgar Lora: Mantis: 0012422
        valorTotalLiquidado = 0;

        out.write(
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        out.write("<tr>");
        out.write("<td><img name=\"tabla_central_r1_c1\" src=\""
                + request.getContextPath()
                + "/jsp/images/tabla_central_r1_c1.gif\" width=\"7\" height=\"29\" border=\"0\" alt=\"\"></td>");
        out.write("<td background=\"" + request.getContextPath()
                + "/jsp/images/tabla_central_bgn003.gif\">");
        out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.write("<tr>");
        out.write("<td background=\"" + request.getContextPath()
                + "/jsp/images/tabla_central_bgn001.gif\" class=\"titulotbcentral\">PAGO</td>");
        out.write("<td width=\"9\"><img name=\"tabla_central_r1_c3\" src=\""
                + request.getContextPath()
                + "/jsp/images/tabla_central_r1_c3.gif\" width=\"9\" height=\"29\" border=\"0\" alt=\"\"></td>");
        out.write(
                "<td width=\"20\" align=\"center\" valign=\"top\" background=\""
                + request.getContextPath()
                + "/jsp/images/tabla_central_bgn002.gif\">");
        out.write(
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.write("<tr>");
        out.write("<td><img src=\"" + request.getContextPath()
                + "/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("</td>");
        /*AHERRENO 17/05/2012
                  REQ 076_151  TRANSACCIONES*/
        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
        if (rol.getNombre().equals("CAJERO CERTIFICADOS") || rol.getNombre().equals("LIQUIDADOR REGISTRO") || rol.getNombre().equals("CAJERO CONSULTAS")) {
            out.write("<td width=\"120\" align=\"center\" valign=\"top\" background=\""
                    + request.getContextPath()
                    + "/jsp/images/tabla_central_bgn002.gif\">");
            out.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            out.write("<tr>");
            out.write("<td><img src=\"" + request.getContextPath()
                    + "/jsp/images/ico_reloj.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td class=\"titulotbcentral\">"
                    + request.getSession().getAttribute("TIEMPO_TRANSACCION")
                    + " Seg.</td>");
            out.write("</tr>");
            out.write("</table></td>");
        }

        out.write("<td width=\"12\"><img name=\"tabla_central_r1_c5\" src=\""
                + request.getContextPath()
                + "/jsp/images/tabla_central_r1_c5.gif\" width=\"12\" height=\"29\" border=\"0\" alt=\"\"></td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("</td>");
        out.write("<td><img name=\"tabla_central_pint_r1_c7\" src=\""
                + request.getContextPath()
                + "/jsp/images/tabla_central_pint_r1_c7.gif\" width=\"11\" height=\"29\" border=\"0\" alt=\"\"></td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td width=\"7\" background=\"" + request.getContextPath()
                + "/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
        out.write(
                "<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");

        out.write(
                "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.write("<tr>");
        out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\""
                + request.getContextPath()
                + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write("<td background=\"" + request.getContextPath()
                + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\">Liquidaci&oacute;n</td>");
        out.write("<td width=\"16\" class=\"bgnsub\"><img src=\""
                + request.getContextPath()
                + "/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
        out.write("<td width=\"15\"> <img name=\"sub_r1_c4\" src=\""
                + request.getContextPath()
                + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write("</tr>");
        out.write("</table>");

        if (liquidacionRegistro) {
            LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) request.getSession().getAttribute(WebKeys.LIQUIDACION);

            double valorDerechos = liq.getValorDerechos();
            double valorImpuestos = liq.getValorImpuestos();
            double valorMora = liq.getValorMora();
            double valorOtroImp = liq.getValorOtroImp();
            double valorConsevacion = liq.getValorConservacionDoc();

            //Obtener valor de certificados asociados.
            double valorAsociados = 0.0;
            Solicitud sol = (Solicitud) liq.getSolicitud();
            List solhijas = sol.getSolicitudesHijas();
            for (Iterator i = solhijas.iterator(); i.hasNext();) {
                SolicitudAsociada solasoc = (SolicitudAsociada) i.next();
                Solicitud solhija = solasoc.getSolicitudHija();
                Liquidacion liqhija = (Liquidacion) solhija.getLiquidaciones().get(0);
                valorAsociados += liqhija.getValor();
            }

            out.write(
                    "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td width=\"20\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td width=\"293\">Valor derechos</td>");
            out.write("<td width=\"11\">$</td>");
            out.write("<td width=\"445\" class=\"campositem\">"
                    + formateador.format(valorDerechos) + "</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.println("<td>&nbsp;</td>");
            out.write("<td width=\"293\">Valor conservacion documental</td>");
            out.write("<td width=\"11\">$</td>");
            out.write("<td width=\"445\" class=\"campositem\">"
                    + formateador.format(valorConsevacion) + "</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.println("<td>&nbsp;</td>");
            out.write("<td width=\"293\">Valor impuestos</td>");
            out.write("<td width=\"11\">$</td>");
            out.write("<td width=\"445\" class=\"campositem\">"
                    + formateador.format(valorImpuestos) + "</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.println("<td>&nbsp;</td>");
            out.write("<td width=\"293\">Valor multa</td>");
            out.write("<td width=\"11\">$</td>");
            out.write("<td width=\"445\" class=\"campositem\">"
                    + formateador.format(valorMora) + "</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.println("<td>&nbsp;</td>");
            out.write("<td width=\"293\">Valor certificados asociados</td>");
            out.write("<td width=\"11\">$</td>");
            out.write("<td width=\"445\" class=\"campositem\">"
                    + formateador.format(valorAsociados) + "</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.println("<td>&nbsp;</td>");
            out.write("<td width=\"293\">Valor otros impuestos</td>");
            out.write("<td width=\"11\">$</td>");
            out.write("<td width=\"445\" class=\"campositem\">"
                    + formateador.format(valorOtroImp) + "</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.println("<td>&nbsp;</td>");
            out.println("<td>&nbsp;</td>");
            out.println("<td>&nbsp;</td>");
            out.println("<td>&nbsp;</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.println("<td>&nbsp;</td>");
            /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                         * Cambio del Texto
             */
            out.write("<td width=\"293\" class=\"campostitle\" >Valor total ajustado a la centena por actos</td>");
            out.write("<td width=\"11\">$</td>");
            out.write("<td width=\"445\" class=\"campositem\">"
                    + formateador.format(valorLiquidacion) + "</td>");
            out.write("</tr>");
            out.write("</table>");
            /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                 * Se agrego un nuevo if, para tener en cuenta los eventos de redondeo
             */
        } else if (liquidacionRedondeo) {
            out.write(
                    "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td width=\"20\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td width=\"293\">Valor total ajustado a la centena por actos</td>");
            out.write("<td width=\"11\">$</td>");

            /**
             * @author : HGOMEZ
             *** @change : Se verifica el valor de la variable valorLiquidacion
             * ** para presentar el valor de la misma en un formato correcto. **
             * Caso Mantis : 12288
             */
            if (valorLiquidacion == 0.0d) {
                out.write("<td width=\"445\" class=\"campositem\">"
                        + valorLiquidacion + "</td>");
            } else {
                out.write("<td width=\"445\" class=\"campositem\">"
                        + formateador.format(valorLiquidacion) + "</td>");
            }

            out.write("</tr>");
            out.write("</table>");
        } else {
            out.write(
                    "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
            out.write("<tr>");
            out.write("<td width=\"20\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
            out.write("<td width=\"293\">Valor</td>");
            out.write("<td width=\"11\">$</td>");
            out.write("<td width=\"445\" class=\"campositem\">"
                    + formateador.format(valorLiquidacion) + "</td>");
            out.write("</tr>");
            out.write("</table>");
        }

        out.write("<br>");

        //Aca se pintan los campos cuando la forma de pagar es consignaciones
        out.write("<table width=\"100%\">");
        out.write("<tr>");
        out.write("<td class=\"contenido\">");

        out.write("</td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("<hr class=\"linehorizontal\">");

        //Aca se muestran los campos para que el Ciudadano pague con  Cheques
        out.write("<br>");
        out.write(
                "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"contenido\">");

        out.write("</tr>");
        valorTotalLiquidado = valorLiquidacion - valorTotalLiquidado;

        if (valorTotalLiquidado < 0) {
            valorTotalLiquidado = 0;
        }

        /*
        * @autor         : HGOMEZ 
        * @mantis        : 12422 
        * @Requerimiento : 049_453 
        * @descripcion   : Se ejecuta el método pintarFormasPago y se comentan
        * los método que ya no se usarán.
         */
        //Edgar Lora: Mantis: 0012422
        //GEREMIAS 14 ENERO 2019
        pintarFormasPago(request, out);
        //GEREMIAS 14 ENERO 2019

        /*Req Canales de recaudo*/
        if (existeFormaPago) {
            pintarTablaEfectuarCancelarPago(request, out);
        } else {
            pintarTablaNoExisteFormasPagoCirculo(request, out);
        }
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Presenta nueva interfaz para ingresar los datos de pago.
     */
    //Edgar Lora: Mantis: 0012422 - refactoring
    private double pintarFormasPago(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        SeleccionarOficinaHelper helperOficina = new SeleccionarOficinaHelper();

        out.println("<table width=\"100%\">");
        //SEGMENTO PAGOS REGISTRADOS
        /*Req canales recaudo*/
        pagoRegistrado(request, out);
        //efectivoRegistrado(request, out);        //1 EFECTIVOS REGISTRADOS
        /*consignacionRegistrada(request, out);      //2 CONSIGNACIONES REGISTRADAS
        chequeRegistrado(request, out);            //3 CHEQUES REGISTRADOS
        convenioRegistrado(request, out);          //4 CONVENIOS REGISTRADOS
        pagoElectronicoRegistrado(request, out);   //5 PAGO ELECTRONICOS PSE REGISTRADOS
        timbreRegistrado(request, out);            //7 TIMBRE
        tarjetaCreditoRegistrada(request, out);    //11 Tarjeta Crédito
        tarjetaDebitoRegistrada(request, out);     //11 Tarjeta Debito
         */

        //SEGMENTO JAVASCRIPT
//        metodosJavaScriptPintarFormasPago(out);
        /*Req canales recaudo
         */
        //SEGMENTO NUEVA FORMA DE PAGOS SIR
        pintarNuevaFormaPago(request, out);
        out.println("</table>");

        if (request.getSession().getAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA) != null && !"".equals(request.getSession().getAttribute(AWPago.NUMERO_DOCUMENTO_PAGOS_YA_REGISTRADA))) {
            out.println("		<script>");
            out.println("                   mostrarNuevaFormaPago('false');");
            out.println("		</script>");
        }

        return valorTotalLiquidado;
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Métodos javascripts usados en método pintarFormasPago.
     */
    private void metodosJavaScriptPintarFormasPago(JspWriter out) throws IOException, HelperException {
        out.println("<script>");
        out.println("   function mostrarNuevaFormaPago(nuevo){");
        out.println("           document.getElementById('" + AWPago.CONSIGNACION_NUEVA_FORMA_PAGO + "').value= nuevo;");
        out.println("           document.getElementById('" + AWPago.NUEVO_CHEQUE + "').value= nuevo;");
        //out.println("           alert(nuevo);");
        out.println("           if (nuevo=='true'){");
        out.println("               document.getElementById('" + AWPago.COD_BANCO_YA_REGISTRADA + "').style.display='none';");
        out.println("               document.getElementById('idViejaFormaPago').style.display='none';");
        out.println("               document.getElementById('" + AWPago.COD_BANCO + "').style.display='block';");
        out.println("               document.getElementById('idNuevaFormaPago').style.display='block';");
        out.println("           } else {");
        out.println("               document.getElementById('" + AWPago.COD_BANCO + "').style.display='none';");
        out.println("               document.getElementById('idNuevaFormaPago').style.display='none';");
        out.println("               document.getElementById('" + AWPago.COD_BANCO_YA_REGISTRADA + "').style.display='block';");
        out.println("               document.getElementById('idViejaFormaPago').style.display='block';");
        out.println("           }");
        out.println("   }");
//        out.println("   function validarFormaPago(formaPago) {");
//        //out.println("       alert(formaPago);");
//        out.println("       if (formaPago=='2'){");
//        out.println("           document.getElementById('FormaPago').ACCION.value = '" + AWPago.BUSCAR_CONSIGNACION + "';");
//        out.println("           document.getElementById('FormaPago').submit();");
//        out.println("       }");
//        out.println("       if (formaPago=='3'){");
//        out.println("           document.getElementById('FormaPago').ACCION.value = '" + AWPago.BUSCAR_CHEQUE + "';");
//        out.println("           document.getElementById('FormaPago').submit();");
//        out.println("       }");
//        out.println("   }");
//        out.println("   function cargarConfiguracionTiposPagos() {");
//        out.println("       if (document.getElementById('FORMA_TIPOS_PAGOS').value == '1' || "
//                + "         document.getElementById('FORMA_TIPOS_PAGOS').value == '6') {");
//        out.println("           document.getElementById('idViejaFormaPago').style.display='none';");
//        out.println("           document.getElementById('idNuevaFormaPago').style.display='block';");
//        //2
//        out.println("           document.getElementById('Label_NuevaExisteSaldo').style.display='none';");
//        out.println("           document.getElementById('Radio_FormaPagoNueva').style.display='none';");
//        //3
//        out.println("           document.getElementById('Label_BancoFranquicia').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO + "').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_YA_REGISTRADA + "').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_FRANQUICIA + "').style.display='none';");
//        //4
//        out.println("           document.getElementById('Label_NumeroDocumentoPago').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_DOCUMENTO_PAGOS + "').style.display='none';");
//        //5
//        out.println("           document.getElementById('Label_ValorDocumento').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.VALOR_DOCUMENTO_PAGOS + "').style.display='none';");
//        //6
//        out.println("           document.getElementById('Label_NumeroAprobacion').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_APROBACION_PAGOS + "').style.display='none';");
//        //7
//        out.println("           document.getElementById('Label_FechaDocumento').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.FECHA_DOCUMENTO_PAGOS + "').style.display='none';");
//        out.println("           document.getElementById('ImagenFechaDocumentosPagos').style.display='none';");
//        //8
//        out.println("           document.getElementById('Label_ValorPagado').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').style.display='block';");
//        out.println("           if(document.getElementById('FORMA_TIPOS_PAGOS').value == '1'){");
//        //Edgar Lora: Mantis: 0012422
//        if (valorTotalLiquidado > 0) {
//            out.println("               document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').value='" + formateador2.format(valorTotalLiquidado) + "';");
//        } else {
//            out.println("               document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').value='';");
//        }
//        out.println("           }else{");
//        out.println("               document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').value='';");
//        out.println("           }");
//        out.println("       } else if (document.getElementById('FORMA_TIPOS_PAGOS').value == '2' || "
//                + "          document.getElementById('FORMA_TIPOS_PAGOS').value == '3') {");
//        out.println("           document.getElementById('idViejaFormaPago').style.display='none';");
//        out.println("           document.getElementById('idNuevaFormaPago').style.display='block';");
//        //2
//        out.println("           document.getElementById('Label_NuevaExisteSaldo').style.display='block';");
//        out.println("           document.getElementById('Radio_FormaPagoNueva').style.display='block';");
//        //Edgar Lora: Mantis: 0012422
//        out.println("           document.getElementById('FORMA_PAGO_NUEVA').checked='true';");
//        out.println("           document.getElementById('FORMA_PAGO_EXISTENTE').checked='false';");
//        //3
//        out.println("           document.getElementById('Label_BancoFranquicia').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_YA_REGISTRADA + "').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO + "').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_FRANQUICIA + "').style.display='none';");
//        //4
//        out.println("           document.getElementById('Label_NumeroDocumentoPago').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_DOCUMENTO_PAGOS + "').style.display='block';");
//        //5
//        out.println("           document.getElementById('Label_ValorDocumento').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.VALOR_DOCUMENTO_PAGOS + "').style.display='block';");
//        //6
//        out.println("           document.getElementById('Label_NumeroAprobacion').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_APROBACION_PAGOS + "').style.display='none';");
//        //7
//        out.println("           document.getElementById('Label_FechaDocumento').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.FECHA_DOCUMENTO_PAGOS + "').style.display='block';");
//        out.println("           document.getElementById('ImagenFechaDocumentosPagos').style.display='block';");
//        //8
//        out.println("           document.getElementById('Label_ValorPagado').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').style.display='block';");
//        //Edgar Lora: Mantis: 0012422
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').value='';");
//        out.println("       } else if (document.getElementById('FORMA_TIPOS_PAGOS').value == '9') {");
//        out.println("           document.getElementById('idViejaFormaPago').style.display='none';");
//        out.println("           document.getElementById('idNuevaFormaPago').style.display='block';");
//        //2
//        out.println("           document.getElementById('Label_NuevaExisteSaldo').style.display='none';");
//        out.println("           document.getElementById('Radio_FormaPagoNueva').style.display='none';");
//        //3
//        //Edgar Lora: Mantis: 0012422
//        out.println("           document.getElementById('Label_BancoFranquicia').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO + "').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_YA_REGISTRADA + "').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_FRANQUICIA + "').style.display='block';");            //4
//        out.println("           document.getElementById('Label_NumeroDocumentoPago').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_DOCUMENTO_PAGOS + "').style.display='none';");
//        //5
//        out.println("           document.getElementById('Label_ValorDocumento').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.VALOR_DOCUMENTO_PAGOS + "').style.display='none';");
//        //6
//        out.println("           document.getElementById('Label_NumeroAprobacion').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_APROBACION_PAGOS + "').style.display='block';");
//        //7
//        out.println("           document.getElementById('Label_FechaDocumento').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.FECHA_DOCUMENTO_PAGOS + "').style.display='block';");
//        out.println("           document.getElementById('ImagenFechaDocumentosPagos').style.display='block';");
//        //8
//        out.println("           document.getElementById('Label_ValorPagado').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').style.display='block';");
//        //Edgar Lora: Mantis: 0012422
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').value='';");
//        out.println("       } else if (document.getElementById('FORMA_TIPOS_PAGOS').value == '11' || "
//                + "          document.getElementById('FORMA_TIPOS_PAGOS').value == '12') {");
//        out.println("           document.getElementById('idViejaFormaPago').style.display='none';");
//        out.println("           document.getElementById('idNuevaFormaPago').style.display='block';");
//        //2
//        out.println("           document.getElementById('Label_NuevaExisteSaldo').style.display='none';");
//        out.println("           document.getElementById('Radio_FormaPagoNueva').style.display='none';");
//        //3
//        out.println("           document.getElementById('Label_BancoFranquicia').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO + "').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_YA_REGISTRADA + "').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_FRANQUICIA + "').style.display='block';");
//        //4
//        out.println("           document.getElementById('Label_NumeroDocumentoPago').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_DOCUMENTO_PAGOS + "').style.display='block';");
//        //5
//        out.println("           document.getElementById('Label_ValorDocumento').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.VALOR_DOCUMENTO_PAGOS + "').style.display='none';");
//        //6
//        out.println("           document.getElementById('Label_NumeroAprobacion').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_APROBACION_PAGOS + "').style.display='block';");
//        //7
//        out.println("           document.getElementById('Label_FechaDocumento').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.FECHA_DOCUMENTO_PAGOS + "').style.display='block';");
//        out.println("           document.getElementById('ImagenFechaDocumentosPagos').style.display='block';");
//        //8
//        out.println("           document.getElementById('Label_ValorPagado').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').style.display='block';");
//        //Edgar Lora: Mantis: 0012422
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').value='';");
//        out.println("       } else if (document.getElementById('FORMA_TIPOS_PAGOS').value == '7') {");
//        out.println("           document.getElementById('idViejaFormaPago').style.display='none';");
//        out.println("           document.getElementById('idNuevaFormaPago').style.display='block';");
//        //2
//        out.println("           document.getElementById('Label_NuevaExisteSaldo').style.display='none';");
//        out.println("           document.getElementById('Radio_FormaPagoNueva').style.display='none';");
//        //3
//        out.println("           document.getElementById('Label_BancoFranquicia').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO + "').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_YA_REGISTRADA + "').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_FRANQUICIA + "').style.display='none';");
//        //4
//        out.println("           document.getElementById('Label_NumeroDocumentoPago').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_DOCUMENTO_PAGOS + "').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_DOCUMENTO_PAGOS + "').value = '';");
//        //5
//        out.println("           document.getElementById('Label_ValorDocumento').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.VALOR_DOCUMENTO_PAGOS + "').style.display='none';");
//        //6
//        out.println("           document.getElementById('Label_NumeroAprobacion').style.display='none';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_APROBACION_PAGOS + "').style.display='none';");
//        //7
//        out.println("           document.getElementById('Label_FechaDocumento').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.FECHA_DOCUMENTO_PAGOS + "').style.display='block';");
//        out.println("           document.getElementById('ImagenFechaDocumentosPagos').style.display='block';");
//        //8
//        out.println("           document.getElementById('Label_ValorPagado').style.display='block';");
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').style.display='block';");
//        //Edgar Lora: Mantis: 0012422
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').value='';");
//        out.println("       }");
//        out.println("   }");
//        /*
//            * @autor         : CTORRES
//            * @mantis        : 12422
//            * @Requerimiento : 049_453
//            * @descripcion   : Métodos javascripts para limpiar formulario despues de seleccionar un nuevo tipo de pago.
//         */
//        out.println("   function limpiarCampos() {");
//        out.println("     if (document.getElementById('FORMA_TIPOS_PAGOS').value != '1'){");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO + "').value='SIN_SELECCIONAR';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_YA_REGISTRADA + "').value='';");
//        out.println("           document.getElementById('" + AWPago.COD_BANCO_FRANQUICIA + "').value='';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_DOCUMENTO_PAGOS + "').value='';");
//        out.println("           document.getElementById('" + AWPago.VALOR_DOCUMENTO_PAGOS + "').value='';");
//        out.println("           document.getElementById('" + AWPago.NUMERO_APROBACION_PAGOS + "').value='';");
//        out.println("           document.getElementById('" + AWPago.FECHA_DOCUMENTO_PAGOS + "').value='';");
//        out.println("           document.getElementById('" + AWPago.VALOR_PAGAR_PAGOS + "').value='';");
//        out.println("   }");
//        out.println("   }");
//
//
//        /*
//            * @author : CTORRES @change : SE IMPLEMENTO DOS FUNCIONES JAVASCRIPT,
//            * soloLetras() PARA EVITAR LA DIGITACION DE CARACTERES DISTINTOS DE
//            * LETRAS Y NUMEROS , limpiar() ELIMINA CUALQUIER CARACTER NO VALIDO AL
//            * CAMBIAR EL FOCO DE LA CAJA DE TEXTO Caso Mantis : 9844
//         */
        String funcion = "function soloLetras(e){ "
                + " var key = e.keyCode || e.which; "
                + " var tecla = String.fromCharCode(key).toLowerCase(); "
                + " var letras = 'abcdefghijklmnopqrstuvwxyz1234567890'; "
                + " var especiales = [2000,2001];"
                + " var tecla_especial = false; "
                + " for(var i in especiales){ "
                + " if(key == especiales[i]){ "
                + "     tecla_especial = true; "
                + "     break;                  "
                + "}}"
                + " if(letras.indexOf(tecla)==-1 && !tecla_especial)return false;  }"
                + " function limpia(){ "
                + " var val = document.getElementById('" + AWPago.NUMERO_DOCUMENTO_PAGOS + "').value; "
                + " var tam = val.length; "
                + " var letras = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890'; "
                + " var buffer = val;"
                + " for(var i=0;i<tam;i++){ "
                + " var l = val.substring(i,i+1);"
                + " var is = letras.indexOf(l);"
                + " if(is==-1) {"
                + "     buffer = buffer.replace(l,'');}"
                + "}"
                + " document.getElementById('" + AWPago.NUMERO_DOCUMENTO_PAGOS + "').value=buffer;"
                + "}";
        out.println(funcion);
        out.println("</script>");
    }

    private void pagoRegistrado(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        if ((pagosRegistrados != null) && !pagosRegistrados.isEmpty()) {
            System.out.println("DOCUMENTO GENERAL LLENOO");
            out.println("<tr>");
            out.println("<td valign=\"top\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");

            /*out.println("<tr>");
            out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            pintarlabelPagosRegistrados(request, out);
            out.println("<td>Eliminar</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>&nbsp;</td>");
            pintarInputPagosRegistrados(request, out);
            out.println("<td>&nbsp;</td>");
            out.println("</tr>");*/
            Iterator itPagosRegistrados = pagosRegistrados.iterator();
            int i = 0;
            while (itPagosRegistrados.hasNext()) {
                AplicacionPago aplicacion = (AplicacionPago) itPagosRegistrados.next();
                DocPagoGeneral general = (DocPagoGeneral) aplicacion.getDocumentoPago();
                System.out.println("VALOR DOCUMENTO IMPRIMIR ARRIBA " + general.getValorDocumento());
                pintarlabelPagosRegistrados(request, out, general);
                pintarInputPagosRegistrados(request, out, general, i);
                i++;
            }

            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
        } else {
            System.out.println("DOCUMENTO GENERAL NULO");
        }
    }

    private void pintarlabelPagosRegistrados(HttpServletRequest request, JspWriter out, DocPagoGeneral docPago) throws IOException, HelperException {

        out.println("<tr>");
        out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath()
                + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");

        out.println("<td width=\"250\">Forma de Pago</td>");

        if (docPago.getFechaDocu() != null) {
            out.println("<td>Fecha Docu</td>");
        }
        if (docPago.getNoConsignacion() != null) {
            out.println("<td>Num Consignacion</td>");
        }
        if (docPago.getValorDocumento() > 0) {
            out.println("<td>Valor Documento</td>");
        }
        if (docPago.getValorLiquidado() > 0) {
            out.println("<td>Valor Liquidado</td>");
        }
        if (docPago.getNoAprobacion() != null) {
            out.println("<td>Num Aprobación</td>");
        }
        if (docPago.getNombreBancoFranquicia() != null) {
            out.println("<td>Banco/Franquicia</td>");
        }
        if (docPago.getNoPin() != null) {
            out.println("<td>No PIN</td>");
        }
        if (docPago.getNoNir() != null) {
            out.println("<td>No NIR</td>");
        }
        if (docPago.getNoCus() != null) {
            out.println("<td>No CUS</td>");
        }
        if (docPago.getCuentasBancarias() != null) {
            out.println("<td>Cuenta Destino</td>");
        }

        out.println("<td>Eliminar</td>");
        out.println("</tr>");

        /*System.out.println("FECHA " + docGeneral.getFechaDocu());
        System.out.println("NO CONSIGNACION " + docGeneral.getNoConsignacion());
        System.out.println("VALOR DOCUMENTO " + docGeneral.getValorDocumento());
        System.out.println("VALOR LIQUIDADO " + docGeneral.getValorLiquidado());
        System.out.println("NUM APROBACION " + docGeneral.getNoAprobacion());
        System.out.println("BANCO/FRANQUICIA " + docGeneral.getBancoFranquicia());
        System.out.println("NO PIN " + docGeneral.getNoPin());
        System.out.println("NO NIR " + docGeneral.getNoNir());
        System.out.println("NO CUS " + docGeneral.getNoCus());*/
    }

    private void pintarInputPagosRegistrados(HttpServletRequest request, JspWriter out, DocPagoGeneral docPago, int i) throws IOException, HelperException {
        out.println("<tr>");
        out.println("<td>&nbsp;</td>");

        out.println("<td width=\"250\">" + docPago.getNombreCanal() + "-" + docPago.getTipoPago() + "</td>");

        if (docPago.getFechaDocu() != null) {
            out.println("<td class=\"campositem\">" + docPago.getFechaDocu() + "</td>");
        }
        if (docPago.getNoConsignacion() != null) {
            out.println("<td class=\"campositem\">" + docPago.getNoConsignacion() + "</td>");
        }
        if (docPago.getValorDocumento() > 0) {
            out.println("<td class=\"campositem\">" + formateador.format(docPago.getValorDocumento()) + "</td>");
        }
        if (docPago.getValorLiquidado() > 0) {
            out.println("<td class=\"campositem\">" + formateador.format(docPago.getValorLiquidado()) + "</td>");
            valorTotalLiquidado = valorTotalLiquidado - docPago.getValorLiquidado();
            if (valorTotalLiquidado < 0) {
                valorTotalLiquidado = 0;
            }
        }
        if (docPago.getNoAprobacion() != null) {
            out.println("<td class=\"campositem\">" + docPago.getNoAprobacion() + "</td>");
        }
        if (docPago.getNombreBancoFranquicia() != null) {
            out.println("<td class=\"campositem\">" + docPago.getNombreBancoFranquicia() + "</td>");
        }
        if (docPago.getNoPin() != null) {
            out.println("<td class=\"campositem\">" + docPago.getNoPin() + "</td>");
        }
        if (docPago.getNoNir() != null) {
            out.println("<td class=\"campositem\">" + docPago.getNoNir() + "</td>");
        }
        if (docPago.getNoCus() != null) {
            out.println("<td class=\"campositem\">" + docPago.getNoCus() + "</td>");
        }
        if (docPago.getCuentasBancarias() != null) {
            out.println("<td class=\"campositem\">" + docPago.getCuentasBancarias().getBanco().getNombre() + "-" + docPago.getCuentasBancarias().getNroCuenta() + "</td>");
        }

        out.println("<form name=\"pagoCertificados\" id=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
        out.println("<input type=\"hidden\" name=\"ACCION\" value=\""
                + AWPago.ELIMINAR_APLICACION + "\">");
        out.println("<input type=\"hidden\" name=\"NUMERO_APLICACION\" value=\"" + i + "\">");
        /*out.println("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO
                + "\" value=\"" + WebKeys.FORMA_PAGO_CONSIGNACION + "\">");*/
        //Edgar Lora: Mantis: 0012422
        out.println("<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath()
                + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
        out.println("</form>");

        out.println("</tr>");

    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Presenta los pagos de pagos efectivos registrados.
     */
    private void efectivoRegistrado(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        if (docEfectivo != null) {
            out.println("<tr>");
            out.println("<td valign=\"top\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");

            out.println("<tr>");
            out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">Forma de Pago</td>");
            //2
            out.println("<td>Valor Pagado $</td>");
            //3
            out.println("<td>Eliminar</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>&nbsp;</td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">EFECTIVO</td>");
            //2
            if (valorLiquidacion == 0.0d) {
                out.println("<td class=\"campositem\">" + docEfectivo.getValorDocumento() + "</td>");
            } else {
                out.println("<td class=\"campositem\">" + formateador.format(docEfectivo.getValorDocumento()) + "</td>");
            }
            //Edgar Lora: Mantis: 0012422
            valorTotalLiquidado = valorTotalLiquidado - docEfectivo.getValorDocumento();
            if (valorTotalLiquidado < 0) {
                valorTotalLiquidado = 0;
            }
            //3
            out.println("<form name=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
            out.println("<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\""
                    + request.getContextPath()
                    + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
            out.println("<input type=\"hidden\" name=\"" + WebKeys.ACCION
                    + "\" value=\"" + AWPago.ELIMINAR_APLICACION + "\">");
            out.println("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO
                    + "\" value=\"" + WebKeys.FORMA_PAGO_EFECTIVO + "\">");
            out.println("</form>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
        }
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Presenta los pagos de pagos consignaciones regristradas.
     */
    private void consignacionRegistrada(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        if ((consignaciones != null) && !consignaciones.isEmpty()) {
            //Aca se pinta el encabezado de los campos que han sido ingresados
            out.println("<tr>");
            out.println("<td valign=\"top\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");

            out.println("<tr>");
            out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">Forma de Pago</td>");
            //2
            out.println("<td>Banco/Franquicia</td>");
            //3
            out.println("<td>N&ordm; Documento Pago</td>");
            //4
            out.println("<td>Valor Documento</td>");
            //out.println("<td>N&ordm; Aprobaci&oacute;n $</td>");
            //5
            out.println("<td>Fecha Documento</td>");
            //6
            out.println("<td>Valor Pagado $</td>");
            //7
            out.println("<td>Eliminar</td>");
            out.println("</tr>");

            Iterator itConsignacion = consignaciones.iterator();
            int i = 0;

            while (itConsignacion.hasNext()) {
                AplicacionPago aplicacion = (AplicacionPago) itConsignacion.next();
                DocPagoConsignacion consignacion = (DocPagoConsignacion) aplicacion.getDocumentoPago();
                Banco banco = consignacion.getBanco();

                //Aca se llenan los campos que ya han sido ingresados
                out.println("<tr>");
                out.println("<td>&nbsp;</td>");
                //1
                //Edgar Lora: Mantis: 0012422
                out.println("<td width=\"250\">CONSIGNACION</td>");
                //2
                if (banco.getNombre() != null) {
                    out.println("<td class=\"campositem\">" + banco.getNombre() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }

                //3
                if (consignacion.getNoConsignacion() != null) {
                    out.println("<td class=\"campositem\">" + consignacion.getNoConsignacion() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + banco + "</td>");
                }

                //4
                out.println("<td class=\"campositem\">" + formateador.format(consignacion.getValorDocumento()) + "</td>");

                //5
                if (consignacion.getFecha() != null) {
                    out.println("<td class=\"campositem\">" + consignacion.getFecha() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }

                //6
                //Edgar Lora: Mantis: 0012422
                valorTotalLiquidado = valorTotalLiquidado - aplicacion.getValorAplicado();
                if (valorTotalLiquidado < 0) {
                    valorTotalLiquidado = 0;
                }
                out.println("<td class=\"campositem\">" + formateador.format(aplicacion.getValorAplicado()) + "</td>");

                //7
                out.println("<form name=\"pagoCertificados\" id=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
                out.println("<input type=\"hidden\" name=\"ACCION\" value=\""
                        + AWPago.ELIMINAR_APLICACION + "\">");
                out.println("<input type=\"hidden\" name=\"NUMERO_APLICACION\" value=\"" + i + "\">");
                out.println("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO
                        + "\" value=\"" + WebKeys.FORMA_PAGO_CONSIGNACION + "\">");
                //Edgar Lora: Mantis: 0012422
                out.println("<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath()
                        + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
                out.println("</form>");

                if (((Boolean) marcasConsignaciones.get(i)).booleanValue()) {
                    out.println("<td>");
                    out.println("<image src=\"" + request.getContextPath()
                            + "/jsp/images/ico_advertencia.gif\" width=\"16\" height=\"21\" border=\"0\" alt=\"Aplicación existente en el sistema\">");
                    out.println("</td>");
                }
                out.println("</tr>");
                i++;
            }

            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
        }
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Presenta los pagos de pagos cheques regristrados.
     */
    private void chequeRegistrado(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        if ((cheques != null) && !cheques.isEmpty()) {
            Iterator itCheques = cheques.iterator();

            //Este es el encabezado donde se identifican los campos que ya han sido introducidos
            out.println("<tr>");
            out.println("<td valign=\"top\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");

            out.println("<tr>");
            out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">Forma de Pago</td>");
            //2
            out.println("<td>Banco/Franquicia</td>");
            //3
            out.println("<td>N&ordm; Documento Pago</td>");
            //4
            out.println("<td>Valor Documento</td>");
            //out.println("<td>N&ordm; Aprobaci&oacute;n $</td>");
            //5
            out.println("<td>Fecha Documento</td>");
            //6
            out.println("<td>Valor Pagado $</td>");
            //7
            out.println("<td>Eliminar</td>");
            out.println("</tr>");

            int i = 0;

            while (itCheques.hasNext()) {
                AplicacionPago aplicacion = (AplicacionPago) itCheques.next();
                DocumentoPago documento = aplicacion.getDocumentoPago();
                DocPagoCheque cheque = (DocPagoCheque) documento;
                Banco banco = cheque.getBanco();

                //Se llenan los campos que ya han sido introducidos
                out.println("<tr>");
                out.println("<td>&nbsp;</td>");
                //1
                //Edgar Lora: Mantis: 0012422
                out.println("<td width=\"250\">CHEQUE</td>");
                //2
                if (banco.getNombre() != null) {
                    out.println("<td class=\"campositem\">" + banco.getNombre() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }
                //3
                if (cheque.getNoCheque() != null) {
                    out.println("<td class=\"campositem\">" + cheque.getNoCheque() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }
                //4
                out.println("<td class=\"campositem\">"
                        + formateador.format(cheque.getValorDocumento()) + "</td>");
                //out.println("<td class=\"campositem\">" + formateador.format(aplicacion.getValorAplicado()) + "</td>"); //Henry Gomez
                //5
                if (cheque.getFecha() != null) {
                    out.println("<td class=\"campositem\">" + cheque.getFecha() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }

                out.println("<td class=\"campositem\">" + formateador.format(aplicacion.getValorAplicado()) + "</td>");//Osbert Linero

                //6
                //Edgar Lora: Mantis: 0012422
                valorTotalLiquidado = valorTotalLiquidado - aplicacion.getValorAplicado();
                if (valorTotalLiquidado < 0) {
                    valorTotalLiquidado = 0;
                }
                //7
                out.println("<form name=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
                out.println("<input type=\"hidden\" name=\"ACCION\" value=\"" + AWPago.ELIMINAR_APLICACION + "\">");
                out.println("<input type=\"hidden\" name=\"NUMERO_APLICACION\" value=\"" + i + "\">");
                out.println("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO + "\" value=\"" + WebKeys.FORMA_PAGO_CHEQUE + "\">");
                //Edgar Lora: Mantis: 0012422
                out.println("<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath()
                        + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
                out.println("</form>");

                if (((Boolean) marcasCheques.get(i)).booleanValue()) {
                    out.println("<td>");
                    out.println("<image src=\"" + request.getContextPath()
                            + "/jsp/images/ico_advertencia.gif\" width=\"16\" height=\"21\" border=\"0\" alt=\"Aplicación existente en el sistema\">");
                    out.println("<td>");
                }
                out.println("</tr>");
                i++;
            }

            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
        }
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Presenta los pagos de pagos convenio regristradas.
     */
    private void convenioRegistrado(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        if (docConvenio != null) {
            out.println("<tr>");
            out.println("<td valign=\"top\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");

            out.println("<tr>");
            out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">Forma de Pago</td>");
            //2
            out.println("<td>Valor Pagado $</td>");
            //3
            out.println("<td>Eliminar</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>&nbsp;</td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">CONVENIO</td>");
            //2
            if (valorLiquidacion == 0.0d) {
                out.println("<td class=\"campositem\">" + docConvenio.getValorDocumento() + "</td>");
            } else {
                out.println("<td class=\"campositem\">" + formateador.format(docConvenio.getValorDocumento()) + "</td>");
            }
            //Edgar Lora: Mantis: 0012422
            valorTotalLiquidado = valorTotalLiquidado - docConvenio.getValorDocumento();
            if (valorTotalLiquidado < 0) {
                valorTotalLiquidado = 0;
            }
            //3
            out.println("<form name=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\""
                    + request.getContextPath()
                    + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
            out.println("<input type=\"hidden\" name=\"" + WebKeys.ACCION
                    + "\" value=\"" + AWPago.ELIMINAR_APLICACION + "\">");
            out.println("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO
                    + "\" value=\"" + WebKeys.FORMA_PAGO_CONVENIO + "\">");
            out.println("</form>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
        }
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Presenta los pagos de pagos PSE regristrados.
     */
    private void pagoElectronicoRegistrado(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        if (docPagoElectronicoPSE != null) {
            out.println("<tr>");
            out.println("<td valign=\"top\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");

            out.println("<tr>");
            out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">Forma de Pago</td>");
            //2
            //Edgar Lora: Mantis: 0012422
            out.println("<td>Banco/Franquicia</td>");
            //3
            out.println("<td>Número Aprobación</td>");
            //4
            out.println("<td>Fecha Documento</td>");
            //5
            out.println("<td>Valor Pagado $</td>");
            //6
            out.println("<td>Eliminar</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>&nbsp;</td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">PAGO ELECTRONICO PSE</td>");
            BancoFranquicia bancoFranquicia = docPagoElectronicoPSE.getBancoFranquicia();
            String nombreBancoFranquicia = encontrarBancosFranquicia(request.getSession().getServletContext(),
                    bancoFranquicia.getIdBanco(), Integer.toString(bancoFranquicia.getIdTipoFranquicia()));
            if (nombreBancoFranquicia != null) {
                out.println("<td class=\"campositem\">" + nombreBancoFranquicia
                        + "</td>");
            } else {
                out.println("<td class=\"campositem\">" + blanco + "</td>");
            }
            //2
            if (docPagoElectronicoPSE.getNumeroAprobacion() != null) {
                out.println("<td class=\"campositem\">" + docPagoElectronicoPSE.getNumeroAprobacion() + "</td>");
            } else {
                out.println("<td class=\"campositem\">" + blanco + "</td>");
            }
            //3
            if (docPagoElectronicoPSE.getFechaDocumento() != null) {
                out.println("<td class=\"campositem\">" + docPagoElectronicoPSE.getFechaDocumento()
                        + "</td>");
            } else {
                out.println("<td class=\"campositem\">" + blanco + "</td>");
            }
            //4
            if (valorLiquidacion == 0.0d) {
                out.println("<td class=\"campositem\">" + docPagoElectronicoPSE.getValorDocumento() + "</td>");
            } else {
                out.println("<td class=\"campositem\">" + formateador.format(docPagoElectronicoPSE.getValorDocumento()) + "</td>");
            }
            //Edgar Lora: Mantis: 0012422
            valorTotalLiquidado = valorTotalLiquidado - docPagoElectronicoPSE.getValorDocumento();
            if (valorTotalLiquidado < 0) {
                valorTotalLiquidado = 0;
            }
            //5
            out.println("<form name=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\""
                    + request.getContextPath()
                    + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
            out.println("<input type=\"hidden\" name=\"" + WebKeys.ACCION
                    + "\" value=\"" + AWPago.ELIMINAR_APLICACION + "\">");
            out.println("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO
                    + "\" value=\"" + WebKeys.FORMA_PAGO_PSE + "\">");
            out.println("</form>");
            out.println("</tr>");

            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
        }
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Presenta los pagos de pagos Timbre regristrados.
     */
    private void timbreRegistrado(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        if (this.formaReciboTimbre != null) {
            out.println("<tr>");
            out.println("<td valign=\"top\">");
            out.println("<table width='100%' border='0' cellpadding='0' cellspacing='2' class='camposform'>");
            out.println("<tr>");
            out.println("<td width='20' height='17'><img src='" + request.getContextPath()
                    + "/jsp/images/ind_campotxt.gif' width='20' height='15'></td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">Forma de Pago</td>");
            //2
            out.println("<td>Número Documento Pago</td>");
            //5
            out.println("<td>Fecha Documento</td>");
            //6
            out.println("<td>Valor Pagado $</td>");
            //7
            out.println("<td>Eliminar</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>&nbsp;</td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td class='campositem'>TIMBRE CONSTANCIA LIQUIDACION</td>");
            //2
            out.println("<td class='campositem'>"
                    + this.formaReciboTimbre.getNumeroTimbre() + "&nbsp;</td>");
            //3
            out.println("<td class='campositem'>" + this.formaReciboTimbre.getFecha() + "</td>");
            //4
            out.println("<td class='campositem'>" + this.formateador.format(this.formaReciboTimbre.getValorDocumento()) + "</td>");
            //Edgar Lora: Mantis: 0012422
            valorTotalLiquidado = valorTotalLiquidado - this.formaReciboTimbre.getValorDocumento();
            if (valorTotalLiquidado < 0) {
                valorTotalLiquidado = 0;
            }
            out.println("<form name='pagoCertificados' method='post' action='pago.do' type='submit'>");
            out.println("<input type='hidden' name='ACCION' value='ELIMINAR_APLICACION'>");
            out.println("<input type='hidden' name='FORMA_PAGO' value='" + WebKeys.FORMA_PAGO_TIMBRE_BANCO + "'>");
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"40\"><input name='imageField' type='image' " + "src='"
                    + request.getContextPath()
                    + "/jsp/images/btn_mini_eliminar.gif' width='35' height='13' border='0'></td>");
            out.println("</form>");
            out.println("</tr>");
            out.println("</table></td>");
            out.println("</tr>");
        }
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Presenta los pagos de pagos Tarjeta Credito regristrados.
     */
    private void tarjetaCreditoRegistrada(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        if ((tarjetasCredito != null) && !tarjetasCredito.isEmpty()) {
            //Aca se pinta el encabezado de los campos que han sido ingresados
            out.println("<tr>");
            out.println("<td valign=\"top\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
            out.println("<tr>");
            out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">Forma de Pago</td>");
            //2
            out.println("<td>Banco/Franquicia</td>");
            //3
            out.println("<td>N&ordm; Documento Pago</td>");
            //Edgar Lora: Mantis: 0012422
            //4
            out.println("<td>N&ordm; Aprobacion</td>");
            //5
            out.println("<td>Fecha Documento</td>");
            //6
            out.println("<td>Valor Pagado $</td>");
            //7
            out.println("<td>Eliminar</td>");
            out.println("</tr>");

            Iterator itConsignacion = tarjetasCredito.iterator();
            int i = 0;

            while (itConsignacion.hasNext()) {
                AplicacionPago aplicacion = (AplicacionPago) itConsignacion.next();
                DocPagoTarjetaCredito tarjetaCredito = (DocPagoTarjetaCredito) aplicacion.getDocumentoPago();
                BancoFranquicia bancoFranquicia = tarjetaCredito.getBancoFranquicia();

                //Aca se llenan los campos que ya han sido ingresados
                out.println("<tr>");
                out.println("<td>&nbsp;</td>");
                //1
                //Edgar Lora: Mantis: 0012422
                out.println("<td width=\"250\">TARJETA CREDITO</td>");
                //2
                String nombreBancoFranquicia = encontrarBancosFranquicia(request.getSession().getServletContext(),
                        bancoFranquicia.getIdBanco(), Integer.toString(bancoFranquicia.getIdTipoFranquicia()));
                if (nombreBancoFranquicia != null) {
                    out.println("<td class=\"campositem\">" + nombreBancoFranquicia
                            + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }
                //3
                if (tarjetaCredito.getNumeroTarjeta() != null) {
                    out.println("<td class=\"campositem\">"
                            + tarjetaCredito.getNumeroTarjeta() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }
                //Edgar Lora: Mantis: 0012422
                //4
                out.println("<td class=\"campositem\">" + tarjetaCredito.getNumeroAprobacion() + "</td>");
                //5
                if (tarjetaCredito.getFecha() != null) {
                    out.println("<td class=\"campositem\">"
                            + tarjetaCredito.getFecha() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }
                //6
                //Edgar Lora: Mantis: 0012422
                valorTotalLiquidado = valorTotalLiquidado - aplicacion.getValorAplicado();
                if (valorTotalLiquidado < 0) {
                    valorTotalLiquidado = 0;
                }
                out.println("<td class=\"campositem\">" + formateador.format(aplicacion.getValorAplicado()) + "</td>");
                //7
                out.println("<form name=\"pagoCertificados\" id=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
                out.println("<input type=\"hidden\" name=\"ACCION\" value=\"" + AWPago.ELIMINAR_APLICACION + "\">");
                out.println("<input type=\"hidden\" name=\"NUMERO_APLICACION\" value=\"" + i + "\">");
                out.println("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO + "\" value=\"" + WebKeys.FORMA_PAGO_TARJETA_CREDITO + "\">");
                //Edgar Lora: Mantis: 0012422
                out.println("<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath()
                        + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
                out.println("</form>");
                if (((Boolean) marcasTarjetasCredito.get(i)).booleanValue()) {
                    out.println("<td>");
                    out.println("<image src=\"" + request.getContextPath()
                            + "/jsp/images/ico_advertencia.gif\" width=\"16\" height=\"21\" border=\"0\" alt=\"Aplicación existente en el sistema\">");
                    out.println("</td>");
                }
                out.println("</tr>");
                i++;
            }

            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
        }
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Presenta los pagos de pagos Tarjeta Debito regristrados.
     */
    private void tarjetaDebitoRegistrada(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        if ((tarjetasDebito != null) && !tarjetasDebito.isEmpty()) {
            //Aca se pinta el encabezado de los campos que han sido ingresados
            out.println("<tr>");
            out.println("<td valign=\"top\">");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
            out.println("<tr>");
            out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath()
                    + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            //1
            //Edgar Lora: Mantis: 0012422
            out.println("<td width=\"250\">Forma de Pago</td>");
            //2
            out.println("<td>Banco/Franquicia</td>");
            //3
            out.println("<td>N&ordm; Documento Pago</td>");
            //Edgar Lora: Mantis: 0012422
            //4
            out.println("<td>N&ordm; Aprobacion</td>");
            //5
            out.println("<td>Fecha Documento</td>");
            //6
            out.println("<td>Valor Pagado $</td>");
            //7x
            out.println("<td>Eliminar</td>");
            out.println("</tr>");

            Iterator itConsignacion = tarjetasDebito.iterator();
            int i = 0;

            while (itConsignacion.hasNext()) {
                AplicacionPago aplicacion = (AplicacionPago) itConsignacion.next();
                DocPagoTarjetaDebito tarjetaDebito = (DocPagoTarjetaDebito) aplicacion.getDocumentoPago();
                BancoFranquicia bancoFranquicia = tarjetaDebito.getBancoFranquicia();

                //Aca se llenan los campos que ya han sido ingresados
                out.println("<tr>");
                out.println("<td>&nbsp;</td>");
                //1
                //Edgar Lora: Mantis: 0012422
                out.println("<td width=\"250\">TARJETA DEBITO</td>");
                //2
                String nombreBancoFranquicia = encontrarBancosFranquicia(request.getSession().getServletContext(),
                        bancoFranquicia.getIdBanco(), Integer.toString(bancoFranquicia.getIdTipoFranquicia()));
                if (nombreBancoFranquicia != null) {
                    out.println("<td class=\"campositem\">" + nombreBancoFranquicia
                            + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }
                //3
                if (tarjetaDebito.getNumeroTarjeta() != null) {
                    out.println("<td class=\"campositem\">" + tarjetaDebito.getNumeroTarjeta() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }
                //Edgar Lora: Mantis: 0012422
                //4
                out.println("<td class=\"campositem\">" + tarjetaDebito.getNumeroAprobacion() + "</td>");
                //5
                if (tarjetaDebito.getFecha() != null) {
                    out.println("<td class=\"campositem\">" + tarjetaDebito.getFecha() + "</td>");
                } else {
                    out.println("<td class=\"campositem\">" + blanco + "</td>");
                }
                //6
                //Edgar Lora: Mantis: 0012422
                valorTotalLiquidado = valorTotalLiquidado - aplicacion.getValorAplicado();
                if (valorTotalLiquidado < 0) {
                    valorTotalLiquidado = 0;
                }
                out.println("<td class=\"campositem\">" + formateador.format(aplicacion.getValorAplicado()) + "</td>");
                //7
                out.println("<form name=\"pagoCertificados\" id=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
                out.println("<input type=\"hidden\" name=\"ACCION\" value=\"" + AWPago.ELIMINAR_APLICACION + "\">");
                out.println("<input type=\"hidden\" name=\"NUMERO_APLICACION\" value=\"" + i + "\">");
                out.println("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO + "\" value=\"" + WebKeys.FORMA_PAGO_TARJETA_DEBITO + "\">");
                //Edgar Lora: Mantis: 0012422
                out.println("<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath()
                        + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
                out.println("</form>");
                if (((Boolean) marcasTarjetasDebito.get(i)).booleanValue()) {
                    out.println("<td>");
                    out.println("<image src=\"" + request.getContextPath()
                            + "/jsp/images/ico_advertencia.gif\" width=\"16\" height=\"21\" border=\"0\" alt=\"Aplicación existente en el sistema\">");
                    out.println("<td>");
                }
                out.println("</tr>");
                i++;
            }

            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
        }
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Pinta interfaz nueva forma de pago.
     */
    private void pintarNuevaFormaPago(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
        out.println("<form name=\"FormaPago\" id=\"FormaPago\" method=\"post\" action=\"pago.do\" type=\"submit\">");

        out.println("<tr>");
        out.println("<td valign=\"top\">");
        out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">"); //1

        out.println("<tr>");
        out.println("<td>");
        out.println("<table width=\"100%\" id=\"primeraSesionFormaPago\" cellpadding=\"0\" cellspacing=\"2\" style=\"border: none\" class=\"camposform\">"); //2

        out.println("<tr>");
        out.println("<td width=\"20\" height=\"17\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");

        out.println("<td><label id=\"Label_FormaPago\">Canal de Recaudo</label></td>");
        out.println("<td><label id=\"Label_FormaPago\">Forma de Pago</label></td>");

        out.println("</tr>");

        out.println("<tr>");
        out.println("<td></td>");
        out.println("<td>");
        listaElementoHelperCanalesRecaudo = this.cargarCanales(AWPago.CANALES_RECAUDO);
        if (listaElementoHelperCanalesRecaudo != null) {
            listaElementoHelperCanalesRecaudo.setSelected(request.getParameter(AWPago.CANALES_RECAUDO));
            listaElementoHelperCanalesRecaudo.render(request, out);
        }
        out.println("</td>");
        out.println("<td>");
        //Nro. 1 Forma de PagolistaFormasPago
        listaElementoHelperFormasPago = this.cargarListaPagos(AWPago.FORMA_TIPOS_PAGOS);
        if (listaElementoHelperFormasPago != null) {
            listaElementoHelperFormasPago.setSelected(request.getParameter(AWPago.FORMA_TIPOS_PAGOS));
            listaElementoHelperFormasPago.render(request, out);
        }
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");    //2c
        out.println("</td>");

        if (request.getSession().getAttribute(WebKeys.LISTA_CAMPOS_CAPTURA_X_FORMA) != null) {
            out.println("<td>");
            out.println("<table width=\"100%\" id=\"idNuevaFormaPago\" cellpadding=\"0\" cellspacing=\"2\" style=\"border: none; \" class=\"camposform\">"); //3

            List listaCamposXForma = (List) request.getSession().getAttribute(WebKeys.LISTA_CAMPOS_CAPTURA_X_FORMA);
            int tamListaCamposXForma = listaCamposXForma.size();

            int iterateTime = 0;
            int nRows = 1;

            String[] fIter = new String[]{"1", "4", "7", "10"};
            String[] sIter = new String[]{"2", "5", "8", "11"};
            String[] tIter = new String[]{"3", "6", "9", "12"};

            if (Arrays.asList(fIter).contains(tamListaCamposXForma + "")) {
                iterateTime = 1;
            }
            if (Arrays.asList(sIter).contains(tamListaCamposXForma + "")) {
                iterateTime = 2;
            }
            if (Arrays.asList(tIter).contains(tamListaCamposXForma + "")) {
                iterateTime = 3;
            }
            System.out.println("ITERATE ARRAY " + iterateTime);

            if (tamListaCamposXForma > 3 && tamListaCamposXForma <=6) {
                nRows = 2;
            }
            if (tamListaCamposXForma > 6 && tamListaCamposXForma <=9) {
                nRows = 3;
            }
            if (tamListaCamposXForma > 9) {
                nRows = 4;
            }

            System.out.println("CANTIDAD ROWS " + nRows);

            List camposCapturaXFormaPago = (List) request.getSession().getAttribute(WebKeys.LISTA_CAMPOS_CAPTURA_X_FORMA);
            if (camposCapturaXFormaPago == null) {
                camposCapturaXFormaPago = new Vector();
            }

            boolean cargarCamposCaptura = (Boolean) request.getSession().getAttribute(AWPago.CARGAR_CAMPOS_CAPTURA);
            if (cargarCamposCaptura) {
                pintarCamposCaptura(request, out, camposCapturaXFormaPago, nRows, iterateTime);
            }

            out.println("</table>"); //3c
            out.println("</td>");
        }

        //out.println("<tr>");
        out.println("</table>"); //1c
        out.println("</td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td align=\"right\" valign=\"top\">");
        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");

        out.println("<tr>");
        out.println("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
        out.println("<input type=\"hidden\" name=\"ACCION\" value=\"" + AWPago.ADICIONAR_APLICACION + "\">");
        out.println("<td>Agregar Pago</td>");
        out.println("<td>");
        out.println("<input name=\"imageField2\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\">");
        out.println("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO + "\" value=\"" + request.getSession().getAttribute("BANCO_FRANQUICIA") + "\">");
        out.println("<input type=\"hidden\" name=\"" + AWPago.CONSIGNACION_NUEVA_FORMA_PAGO + "\" id=\"" + AWPago.CONSIGNACION_NUEVA_FORMA_PAGO + "\" value=\"\">");
        out.println("<input type=\"hidden\" name=\"" + AWPago.NUEVO_CHEQUE + "\" id=\"" + AWPago.NUEVO_CHEQUE + "\" value=\"\">");
        out.println("<input type=\"hidden\" name=\"" + AWPago.FORMAS_PAGO_MAP + "\" id=\"" + AWPago.FORMAS_PAGO_MAP + "\" value=\"\">");

        out.println("</td>");
        out.println("</tr>");

        out.println("</table>");
        out.println("</tr>");
        out.println("</form>");
        
        out.println("<script>");
        //out.println(" cargarConfiguracionTiposPagos(); ");
        out.println("</script>");
    }

    private void pintarCamposCaptura(
            HttpServletRequest request, JspWriter out, List camposCaptura, int nRows, int iterateTimes) throws IOException, HelperException {

        if (nRows == 1) {
            out.println("<tr>");
            for (int i = 0; i < iterateTimes; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 0; i < iterateTimes; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/
        }
        if (nRows == 2) {
            out.println("<tr>");
            for (int i = 0; i <= 2; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 0; i <= 2; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/
            out.println("<tr>");
            for (int i = 3; i < (3 + iterateTimes); i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 3; i < (3 + iterateTimes); i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/
        }
        if (nRows == 3) {
            out.println("<tr>");
            for (int i = 0; i <= 2; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 0; i <= 2; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/
            out.println("<tr>");
            for (int i = 3; i <= 5; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 3; i <= 5; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/
            out.println("<tr>");
            for (int i = 6; i < (6 + iterateTimes); i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 6; i < (6 + iterateTimes); i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/

        }
        if (nRows == 4) {
            out.println("<tr>");
            for (int i = 0; i <= 2; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 0; i <= 2; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/
            out.println("<tr>");
            for (int i = 3; i <= 5; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 3; i <= 5; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/
            out.println("<tr>");
            for (int i = 6; i <= 8; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 6; i <= 8; i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/
            out.println("<tr>");
            for (int i = 9; i < (9 + iterateTimes); i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarLabelCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*init input*/
            out.println("<tr>");
            for (int i = 9; i < (9 + iterateTimes); i++) {
                CamposCaptura cc = (CamposCaptura) camposCaptura.get(i);
                pintarInputCamposCaptura(request, out, cc);
            }
            out.println("</tr>");
            /*end input*/
        }
    }

    private void pintarLabelCamposCaptura(
            HttpServletRequest request, JspWriter out, CamposCaptura camposCaptura) throws IOException {

        if (camposCaptura.getFormName().equals(WebKeys.CAMPO_BANCO_FRANQUICIA)) {
            out.println("<td><label id='LABEL_" + camposCaptura.getFormName() + "'>" + camposCaptura.getFormLabel() + "</label></td>");
            //Cuenta Destino
            //out.println("<td><label id=\"cuentaDestino\">CUENTA DESTINO</label></td>");
        } else if (camposCaptura.getFormName().equals(AWPago.FECHA_DOCU)) {
            out.println("<td><label id='LABEL_" + camposCaptura.getFormName() + "'>" + camposCaptura.getFormLabel() + "</label></td>");
            out.println("<td></td>");
        } else {
            out.println("<td><label id='LABEL_" + camposCaptura.getFormName() + "'>" + camposCaptura.getFormLabel() + "</label></td>");
        }
        if (camposCaptura.getFormName().equalsIgnoreCase("VLR_DOC_PAGO")) {
            
            out.println("<td><label id=\"Label_NuevaExisteSaldo\">Nueva o Existe Saldo</label></td>"); 
            if(request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA) != null && !request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA).equals("")){
                out.println("<td><label id=\"idViejaFormaPago\" style=\"display:block\">SALDO DOCUMENTO</label></td>"); 
            }else{
                out.println("<td><label id=\"idViejaFormaPago\" style=\"display:none\">SALDO DOCUMENTO</label></td>"); 
            }              
        }
    }

    private void pintarInputCamposCaptura(
            HttpServletRequest request, JspWriter out, CamposCaptura camposCaptura) throws IOException, HelperException {
        if (camposCaptura.getFormName().equals(WebKeys.CAMPO_BANCO_FRANQUICIA)) {
            //Banco/Franquicia
            out.println("<td>");
            if (listaElementoHelperBancoFranquicia != null) {
                listaElementoHelperBancoFranquicia.render(request, out);
            }
            out.println("</td>");
        } else if (camposCaptura.getFormName().equals(AWPago.FECHA_DOCU)) {
            out.println("<td><input id=\"" + camposCaptura.getFormName() + "\" name=\""
                    + camposCaptura.getFormName()
                    + "\" value=\"" + ((request.getSession().getAttribute(camposCaptura.getFormName()) != null) ? request.getSession().getAttribute(camposCaptura.getFormName()) : "") + "\""
                    + "\" type=\"text\" class=\"camposformtext\" onkeypress=\"return valideDate(event,'"+AWPago.FECHA_DOCU+"');\" "
                    + " onChange=\"fixDate('"+AWPago.FECHA_DOCU+"')\"   onBlur=\"javascript:validarFecha()\" );></td>");
            out.println("<td align=\"left\"><div id=\"ImagenFechaDocumentosPagos\"><a href=\"javascript:NewCal('"
                    + AWPago.FECHA_DOCU + "','ddmmmyyyy',true,24)\"><img src=\""
                    + request.getContextPath()
                    + "/jsp/images/ico_calendario.gif\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('"
                    + request.getContextPath() + "')\"></a></div></td>");
        } else if (camposCaptura.getFormName().equals(AWPago.CUENTA_DESTINO)) {
            out.println("<td>");
            if (listaCuentasBancarias != null) {
                listaCuentasBancarias.render(request, out);
            }
            out.println("</td>");
        } else if (camposCaptura.getFormName().equals(AWPago.VLR_LIQUIDADO)) {
            out.println("<td><input id=\"" + camposCaptura.getFormName() + "\" name=\""
                    + camposCaptura.getFormName()
                    + "\" value=\"" + ((valorTotalLiquidado > 0) ? formateador2.format(valorTotalLiquidado) : "") + "\""
                    + "\" type=\"text\" class=\"camposformtext\"></td>");

        } else if (camposCaptura.getFormName().equals(AWPago.NO_DOC_PAGO)) {
            out.println("<td><input id=\"" + camposCaptura.getFormName() + "\" name=\""
                    + camposCaptura.getFormName()
                    + "\" onkeypress=\"return soloLetras(event)\" onblur=\"limpia()\" value=\"" + ((request.getSession().getAttribute(AWPago.NO_DOC_PAGO) != null) ? request.getSession().getAttribute(AWPago.NO_DOC_PAGO) : "") + "\""
                    + "\" type=\"text\" class=\"camposformtext\"></td>");
        } else if (camposCaptura.getFormName().equals(AWPago.NO_CONSIGNACION)) {
            
            out.println("<td><input id=\"" + camposCaptura.getFormName() + "\" name=\""
                    + camposCaptura.getFormName()
                    + "\" onkeypress=\"return soloLetras(event)\" onblur=\"limpia()\" value=\"" + ((request.getSession().getAttribute(camposCaptura.getFormName()) != null) ? request.getSession().getAttribute(camposCaptura.getFormName()) : "") + "\""
                    + "\" type=\"text\" class=\"camposformtext\"></td>");
            
        }else {
            out.println("<td><input id=\"" + camposCaptura.getFormName() + "\" name=\""
                    + camposCaptura.getFormName()
                    + "\" value=\"" + ((request.getSession().getAttribute(camposCaptura.getFormName()) != null) ? request.getSession().getAttribute(camposCaptura.getFormName()) : "") + "\""
                    + "\" type=\"text\" class=\"camposformtext\"></td>");
        }
        if (camposCaptura.getFormName().equalsIgnoreCase("VLR_DOC_PAGO")) {
            out.println("<td>");
            out.println("<div id=\"Radio_FormaPagoNueva\" style=\"display:block\">");
            if (request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA) != null && !request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA).equals("") ) {
                out.println("<input name='FORMA_PAGO_NUEVA' id=\"FORMA_PAGO_EXISTENTE\" type=\"radio\" value='true' onClick=\"mostrarNuevaFormaPago(true);\">");
                out.println("<input name='FORMA_PAGO_NUEVA' id=\"FORMA_PAGO_NUEVA\" type=\"radio\" value='false' checked=\"true\" onClick=\"mostrarNuevaFormaPago(false);\"> ");             
            } else {
                out.println("<input name='FORMA_PAGO_NUEVA' id=\"FORMA_PAGO_EXISTENTE\" type=\"radio\" value='true' checked=\"true\"  onClick=\"mostrarNuevaFormaPago(true);\">");
                out.println("<input name='FORMA_PAGO_NUEVA' id=\"FORMA_PAGO_NUEVA\" type=\"radio\" value='false' onClick=\"mostrarNuevaFormaPago(false);\"> ");
            }           
            out.println("</div>");
            out.println("</td>");
            
            out.println("<td>");
            if(request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA) != null && !request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA).equals("")){
                out.println("<div id=\"campoSaldoDocumento\" style=\"display:block\">");
            }else{
                out.println("<div id=\"campoSaldoDocumento\" style=\"display:none\">");
            }
            textHelper.setNombre(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA);
            textHelper.setCssClase("camposformtext");
            textHelper.setId(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA);
            textHelper.setReadonly(true);
            textHelper.setSize("17");
            textHelper.render(request, out);
            out.println("</div>");
            out.println("</td>");
            out.println("<td>");
            if(request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA) != null && !request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA).equals("")){
                out.println("<div id=\"botonValidar\" style=\"display:block\">");
            }else{
                out.println("<div id=\"botonValidar\" style=\"display:none\">");
            }
            out.println("<width=\"140\"><input name=\"imageField2\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_validar.gif\" width=\"139\" height=\"21\" border=\"0\" onClick=\"validarGeneral()\">");                      
            out.println("</div>");
            
            if(request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA) != null && !request.getSession().getAttribute(AWPago.SALDO_DOCUMENTO_PAGOS_YA_REGISTRADA).equals("")){
                out.println("<input type=\"hidden\" name=\"" + AWPago.LLAVE_GENERAL_APLICACION + "\" id=\"" + AWPago.LLAVE_GENERAL_APLICACION + "\" value = false >");
            }else{
                out.println("<input type=\"hidden\" name=\"" + AWPago.LLAVE_GENERAL_APLICACION + "\" id=\"" + AWPago.LLAVE_GENERAL_APLICACION + "\" value = true >");
            }                       
            out.println("</td>");
            
                       
        }
    }

    /**
     * Generar el código HTML indicando que el circulo actual no tiene asociado
     * formas de pago
     *
     * @param request
     * @param out OutputStream de escritura del codigo html
     * @throws IOException
     */
    private void pintarTablaNoExisteFormasPagoCirculo(
            HttpServletRequest request, JspWriter out) throws IOException {
        out.println(
                "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
        out.println("<tr>");
        out.println("<td width=\"20\"><img src=\"" + request.getContextPath()
                + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
        out.println("<td class=\"campositem\">");
        out.println("El circulo no tiene configurada ninguna opcion de pago");
        out.println("</td>");
        out.println("<td>&nbsp;</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</td>");
        out.println("<td width=\"11\" background=\"" + request.getContextPath()
                + "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td><img name=\"tabla_central_r3_c1\" src=\""
                + request.getContextPath()
                + "/jsp/images/tabla_central_r3_c1.gif\" width=\"7\" height=\"6\" border=\"0\" alt=\"\"></td>");
        out.println("<td background=\"" + request.getContextPath()
                + "/jsp/images/tabla_central_bgn006.gif\"><img src=\""
                + request.getContextPath()
                + "/jsp/images/spacer.gif\" width=\"15\" height=\"6\"></td>");
        out.println("<td><img name=\"tabla_central_pint_r3_c7\" src=\""
                + request.getContextPath()
                + "/jsp/images/tabla_central_pint_r3_c7.gif\" width=\"11\" height=\"6\" border=\"0\" alt=\"\"></td>");
        out.println("</tr>");
        out.println("</table>");
    }

    /**
     * Generar el código HTML de la tabla que muestra los botones de Aceptar y
     * cancelar el pago
     *
     * @param request
     * @param out OutputStream de escritura del codigo html
     * @throws IOException
     */
    private void pintarTablaEfectuarCancelarPago(HttpServletRequest request,
            JspWriter out) throws IOException {

        out.println("\n<script>\n");
        out.println("function continuarPagoReparto(){\n");
        out.println("		document.ACEPTARPAGO.ACCION.value = 'PROCESAR_REGISTRO_CONTINUAR'\n");
        out.println("		document.ACEPTARPAGO.submit();");
        out.println("}\n");
        out.println("function modificarLiquidacion(){\n");
        out.println("		document.REGISTRO.ACCION.value = '" + AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION + "'\n");
        out.println("		document.REGISTRO.submit();");
        out.println("}\n");
        out.println("function cancelar(){\n");
        out.println("		document.ACEPTARPAGO.ACCION.value = '" + AWPago.REMOVER_INFO + "'\n");
        out.println("		document.ACEPTARPAGO.submit();");
        out.println("}\n");

        out.println("function ocultarButton(){\n");
        out.println("		document.getElementById('ACEPTAR').width=0;");
        out.println("}\n");
        out.println("</script>\n");

        //Campo donde se pintan los botones de Verificar y cancelar
        out.println(
                "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
        out.println("<tr>");
        out.println("<td width=\"20\"><img src=\"" + request.getContextPath()
                + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");

        //Obtener el dato si toca poner pantalla de espera
        Boolean espera = (Boolean) request.getSession().getAttribute(WebKeys.PANTALLA_ESPERA);
        request.getSession().removeAttribute(WebKeys.PANTALLA_ESPERA);
        if (espera != null) {
            Log.getInstance().debug(DatosPagoHelper.class, "La siguiente vista es wait.view =" + espera.booleanValue());
        }

        if (espera == null) {

            out.println("<form name=\"ACEPTARPAGO\" method=\"post\" action=\"pago.do\" type=\"submit\">");
            out.println("<input type=\"hidden\" name=\"ACCION\" value=\"" + AWPago.PROCESAR + "\">");
        } else {
            out.println("<form name=\"ACEPTARPAGO\" method=\"post\" action=\"wait.view\" type=\"submit\">");
        }

        out.println("<td width=\"150\">");

        //out.println("<input type=\"hidden\" name=\"ACCION\" value=\""+AWPago.VALIDAR+"\">");
        if (asignarEstacion) {
            out.println("<input type=\"hidden\" name=" + AWPago.ASIGNAR_ESTACION + " value=\"TRUE\">");
        }

        //out.println("<input name=\"ACEPTAR\" type=\"image\" id=\"ACEPTAR\" src=\"" + request.getContextPath() + "/jsp/images/btn_validarpago.gif\" width=\"139\" height=\"21\" border=\"0\">");
        if (espera == null) {
            //Caso que no este setteada que haya pantalla de espera
            out.println("<input name=\"ACEPTAR\" type=\"image\" id=\"ACEPTAR\" onclick=\"ocultarButton()\" src=\"" + request.getContextPath() + "/jsp/images/btn_regpago.gif\" width=\"139\" height=\"21\" border=\"0\" alt=\"Registrar el pago.\">");
        } else {
            //Caso que este setteada que haya pantalla de espera
            out.println("<input type=\"hidden\" name=\"" + WebKeys.REDIRECCION_DO + "\" value=\"pago.do\" >");
            out.println("<input type=\"hidden\" name=\"" + WebKeys.ACCION + "\" value=\"" + AWPago.PROCESAR + "\">");
            out.println("<input name=\"ACEPTAR\" type=\"image\" id=\"ACEPTAR\" src=\"" + request.getContextPath() + "/jsp/images/btn_regpago.gif\" width=\"139\" height=\"21\" border=\"0\" alt=\"Registrar el pago.\">");
        }

        out.println("</td>");

        Boolean pagoRegistro = (Boolean) request.getSession().getAttribute("PAGO_REGISTRO_LIQUIDACION");

        if (pagoRegistro != null && pagoRegistro.booleanValue()) {

            Boolean esCajero = (Boolean) request.getSession().getAttribute(WebKeys.ES_CAJERO_REGISTRO);
            if (esCajero != null && esCajero.booleanValue()) {
                out.println("<td width=\"150\">");
                out.println("<a href=\"javascript:continuarPagoReparto()\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_seguir.gif\" width=\"139\" height=\"21\" border=\"0\" alt=\"Registrar el pago y radicar el turno de solicitud de registro.\"></a>");
                out.println("</td>");
            }

            //Boton con la funcionalidad para poder modificar liquidacion.
            out.println("<td width=\"150\">");
            out.println("<a href=\"javascript:modificarLiquidacion()\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_editar.gif\" width=\"139\" height=\"21\" border=\"0\" alt=\"Editar la liquidacion actual.\"></a>");
            out.println("</td>");

        }

        out.println("</form>");

        //Forma para comunicarse AWLiquidacion Registro
        if (pagoRegistro != null && pagoRegistro.booleanValue()) {
            LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) request.getSession().getAttribute(WebKeys.LIQUIDACION);

            out.println("<form name=\"REGISTRO\" method=\"post\" action=\"turnoLiquidacionRegistro.do\" type=\"submit\">");
            out.println("<input type=\"hidden\" name=\"ACCION\" value=\"\">");
            out.println("<input type=\"hidden\" name=\"ID_SOLICITUD\" value=\"" + liq.getIdSolicitud() + "\">");
            out.println("<input type=\"hidden\" name=\"VIENE_DE_CAJERO\" value=\"true\">");
            out.println("</form>");
        }

        //out.println("<form name=\"CancelarPagoCertificados\" method=\"post\" action=\"turno.certificado.registrar.pago.view\" type=\"submit\">");
        out.println("<td width=\"150\">");

        out.println("<a href=\"javascript:cancelar();\"><img src=\""
                + request.getContextPath()
                + "/jsp/images/btn_cancelar.gif\" width=\"139\" height=\"21\" border=\"0\" alt=\"Cancelar.\"></a>");
        out.println("</td>");

        //out.println("</form>");
        out.println("<td>&nbsp;</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</td>");
        out.println("<td width=\"11\" background=\"" + request.getContextPath()
                + "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td><img name=\"tabla_central_r3_c1\" src=\""
                + request.getContextPath()
                + "/jsp/images/tabla_central_r3_c1.gif\" width=\"7\" height=\"6\" border=\"0\" alt=\"\"></td>");
        out.println("<td background=\"" + request.getContextPath()
                + "/jsp/images/tabla_central_bgn006.gif\"><img src=\""
                + request.getContextPath()
                + "/jsp/images/spacer.gif\" width=\"15\" height=\"6\"></td>");
        out.println("<td><img name=\"tabla_central_pint_r3_c7\" src=\""
                + request.getContextPath()
                + "/jsp/images/tabla_central_pint_r3_c7.gif\" width=\"11\" height=\"6\" border=\"0\" alt=\"\"></td>");
        out.println("</tr>");
        out.println("</table>");
    }

    /**
     * Indicar si el helper muestra la opción de cheques
     *
     * @param b
     */
    public void setSeQuiereCheques(boolean b) {
        seQuiereCheques = b;
        listaFormasPago.add(new ElementoLista("3", "03 - Cheque"));
    }

    /**
     * Indicar si el helper muestra la opción de consignación
     *
     * @param b
     */
    public void setSeQuiereConsignacion(boolean b) {
        seQuiereConsignacion = b;
        listaFormasPago.add(new ElementoLista("2", "02 - Consignacion"));
    }

    /**
     * Indicar si el helper muestra la opción de efectivo
     *
     * @param b
     */
    public void setSeQuiereEfectivo(boolean b) {
        seQuiereEfectivo = b;
        listaFormasPago.add(new ElementoLista("1", "01 - Efectivo"));
    }

    /**
     * Indicar si el helper muestra la opción de pago con timbre
     *
     * @param b The seQuiereTimbreConstanciaLiquidacion to set.
     */
    public void setSeQuiereTimbreConstanciaLiquidacion(boolean b) {
        this.seQuiereTimbreConstanciaLiquidacion = b;
        listaFormasPago.add(new ElementoLista("7", "07 - Timbre constancia liquidacion"));
    }

    /*
    * @autor         : HGOMEZ 
    * @mantis        : 12422 
    * @Requerimiento : 049_453 
    * @descripcion   : Se declaran los siguiente métodos para conocer que tipo
    * de pago se desea usar.
     */
    public void setSeQuiereConvenio(boolean b) {
        this.seQuiereConvenio = b;
        listaFormasPago.add(new ElementoLista("6", "06 - Convenio"));
    }

    public void setSeQuierePagoElectronicoPSE(boolean b) {
        this.seQuierePagoElectronicoPSE = b;
        listaFormasPago.add(new ElementoLista("9", "09 - Pago electronico PSE"));
    }

    public void setSeQuiereTarjetaCredito(boolean b) {
        this.seQuiereTarjetaCredito = b;
        listaFormasPago.add(new ElementoLista("11", "11 - Tarjeta credito"));
    }

    public void setSeQuiereTarjetaDebito(boolean b) {
        this.seQuiereTarjetaDebito = b;
        listaFormasPago.add(new ElementoLista("12", "12 - Tarjeta debito"));
    }

    /**
     * Indica si se quiere asignar la misma estación luego de generar turno
     *
     * @param b asignarEstacion to set
     */
    public void setAsignarEstacion(boolean b) {
        this.asignarEstacion = b;
    }

    public boolean isLiquidacionRegistro() {
        return liquidacionRegistro;
    }

    public void setLiquidacionRegistro(boolean liquidacionRegistro) {
        this.liquidacionRegistro = liquidacionRegistro;
    }

    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Nuevos metodos para el manejo del evento de redondeo.
     */
    public boolean isliquidacionRedondeo() {
        return liquidacionRedondeo;
    }

    public void setliquidacionRedondeo(boolean liquidacionRedondeo) {
        this.liquidacionRedondeo = liquidacionRedondeo;
    }

    /**
     * Indicar si el helper muestra la opción de efectivo
     *
     * @param b
     */
    public void setExisteFormaPago(boolean b) {
        this.existeFormaPago = b;
    }

    public void setCanalesRecaudo(String id, String nombre) {
        if (!listaCanalesRecaudo.contains(new ElementoLista(id, nombre))) {
            listaCanalesRecaudo.add(new ElementoLista(id, nombre));
        }
    }

    public void setFormasPagoMap(String idCanal, long idFormaPago, String nombreFormaPago) {
        formasPago += idCanal + "," + idFormaPago + "," + nombreFormaPago.trim() + ";";
    }
}
