/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.web.acciones.correccion;

import gov.sir.core.eventos.comun.EvnPago;
import gov.sir.core.eventos.correccion.EvnCorreccionCanalRecaudo;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancoFranquicia;
import gov.sir.core.negocio.modelo.CamposCaptura;
import gov.sir.core.negocio.modelo.CanalesRecaudo;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoConvenio;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocPagoElectronicoPSE;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.DocPagoTarjetaCredito;
import gov.sir.core.negocio.modelo.DocPagoTarjetaDebito;
import gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.DocumentoPagoPk;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoNotaPk;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CCorreccionCanalRecaudo;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWTrasladoTurno;
import gov.sir.core.web.acciones.comun.AWPago;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosRegistroPagoException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.forseti.ForsetiService;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import gov.sir.hermod.dao.impl.jdogenie.JDOPagosDAO;
import gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 *
 * @author developer5
 */
public class AWCorreccionCanalRecaudo extends SoporteAccionWeb {

    private String accion;
    public static final String MODIFICAR_FORMA_PAGO = "MODIFICAR_FORMA_PAGO";
    public final static String CARGAR_FORMAS_PAGO = "CARGAR_FORMAS_PAGO";
    public final static String CARGAR_CAMPOS_CAPTURA_X_FORMA = "CARGAR_CAMPOS_CAPTURA_X_FORMA";
    public final static String CUENTAS_X_CIRCULO_BANCO = "CUENTAS_X_CIRCULO_BANCO";
    public final static String CONSTANTESIGUARDO = "CONSTANTESIGUARDO";
    public final static String CANALES_RECAUDO = "CANALES_RECAUDO";
    public final static String FORMA_TIPOS_PAGOS = "FORMA_TIPOS_PAGOS";
     private final String APLICACION_PAGO_CONVENIO = "CONVENIO";
    private final String APLICACION_PAGO_ELECTRONICO_PSE = "PAGO_ELECTRONICO_PSE";
    private final String APLICACION_PAGO_TARJETA_CREDITO = "TARJETA_CREDITO";
    public final static String APLICACION_PAGO_TARJETA_DEBITO = "TARJETA_DEBITO";
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

    public AWCorreccionCanalRecaudo() {
        super();
    }

    @Override
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }
        this.preservarInfo(request);
        if (accion.equals(MODIFICAR_FORMA_PAGO)) {
            return modificarFormaPago(request);
        } else if (accion.equals(CARGAR_FORMAS_PAGO)) {
            return cargarFormasPago(request);
        } else if (accion.equals(CARGAR_CAMPOS_CAPTURA_X_FORMA)) {
            return cargarCampoCapturaXFormaPago(request);
        } else if (accion.equals(CUENTAS_X_CIRCULO_BANCO)) {
            return cuentasBancariasXCirculo(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion+ " no es valida.");
        }        
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
    
    private Evento cargarFormasPago(HttpServletRequest request) throws AccionWebException {
        
        HttpSession session = request.getSession();
        session.setAttribute(AWPago.CARGAR_CAMPOS_CAPTURA, false);

        String idCanalRecaudo = request.getParameter(AWPago.CANALES_RECAUDO);
        String formasPagoMap = request.getParameter(AWPago.FORMAS_PAGO_MAP);

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
                listaFormasPago.add(new ElementoLista(formasPago2[1], formasPago2[2]));
            }

        }

        session.setAttribute(WebKeys.LISTA_FORMAS_PAGO_CANAL, listaFormasPago);

        return null;
    }
    
    private EvnCorreccionCanalRecaudo modificarFormaPago(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        session.removeAttribute(CONSTANTESIGUARDO);
        String turnoActual = (String) session.getAttribute(CTurno.ID_TURNO);       
        String dato = (String) request.getSession().getAttribute(CCorreccionCanalRecaudo.IDDOCPAG);
        JDOPagosDAO jdoPagosDAO = new JDOPagosDAO ();
        HermodService hs;
        ForsetiService fs;
        Turno turno;        
        DocPagoGeneral n ;
        boolean salio = false;
        boolean exitoso = false;
        ValidacionParametrosException vpe = new ValidacionParametrosException();
        try {
                hs = HermodService.getInstance();
                turno = hs.getTurnobyWF(turnoActual);
                List documentospagos = hs.getDocumentoPagoBySolicitud(turno.getSolicitud().getIdSolicitud());
                int cuentaCorregido = 0;
                for(int nee = 0; nee < documentospagos.size(); nee++){
                    DocumentoPago dpg = (DocumentoPago) documentospagos.get(nee);
                    DocumentoPago u = jdoPagosDAO.getDocumentobyIdDocPago(dpg.getIdDocumentoPago());
                    if(u.getEstadocorreccion() == 1){
                        cuentaCorregido = cuentaCorregido + 1;
                    }
                }
                int notascorre = 0;
                List notas = turno.getNotas();
                for(int ne = 0; ne < notas.size(); ne++){
                    Nota nr = (Nota) notas.get(ne);
                    TipoNotaPk tn = new TipoNotaPk();
                    tn.idTipoNota = nr.getTiponota().getIdTipoNota();
                    tn.activo = 1;
                    TipoNota tnp = (TipoNota) hs.getTipoNota(tn);
                    if(tnp.getFase().equals(CFase.COR_CANAL_RECAUDO)){
                         salio = true;
                         notascorre = notascorre + 1;
                    }
                }
                if(!salio){
                     vpe.addError("No es posible corregir la forma de pago del turno. Se requiere una nota informativa para continuar");
                      throw vpe;
                }
                if(notascorre <= cuentaCorregido){
                     vpe.addError("Se requiere una nueva nota informativa para continuar");
                      throw vpe;
                }
                DocumentoPagoPk p = new DocumentoPagoPk();
                p.idDocumentoPago = dato;
                n = (DocPagoGeneral)jdoPagosDAO.getDocumentosPagoById(p);
                exitoso = adicionarAplicacion(request,n,dato);
        } catch (HermodException ex) {
            Logger.getLogger(AWCorreccionCanalRecaudo.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(exitoso){
            session.setAttribute(CONSTANTESIGUARDO, 1);    
        }else{
            session.setAttribute(CONSTANTESIGUARDO, 0);    
        }
        return null;
    }
    public DocumentoPago SacarPagoAplicacion(HttpServletRequest request,DocPagoGeneral Pago,String doc) throws AccionWebException{
     
        ValidacionParametrosException vpe = new ValidacionParametrosException();
        String canalRecaudo = request.getParameter(AWPago.CANALES_RECAUDO);
        String formaPago = request.getParameter(AWPago.FORMA_TIPOS_PAGOS);
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
        
        int formaPagoEscogida = Integer.parseInt(formaPago);
        int canalRecaudoEscogido = Integer.parseInt(canalRecaudo);

        List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS);
        if (listaAplicaciones == null) {
            listaAplicaciones = new Vector();
        }

        DocumentoPago docPago = null;

        if (formaPagoEscogida > 0) {
            docPago = construirNuevaAplicacionPagoGeneral(request, formaPagoEscogida, canalRecaudoEscogido,Pago);
        } else {
            vpe.addError("La forma de pago es inválida.");
        }

     
        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }
        return docPago;

    }
       private DocumentoPago construirNuevaAplicacionPagoGeneral(HttpServletRequest request,
            int formaPagoEscogida, int canalRecaudoEscogido, DocPagoGeneral doc) throws ValidacionParametrosException {
        ValidacionParametrosException vpe = new ValidacionParametrosRegistroPagoException();
        HttpSession session = request.getSession();

        List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);

        List listaBancosFranquicias = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS);

        return this.construirAplicacionPagoGeneral(request, vpe, formaPagoEscogida, listaBancos, listaBancosFranquicias, canalRecaudoEscogido,doc);

    }
       private DocumentoPago construirAplicacionPagoGeneral(
            HttpServletRequest request, ValidacionParametrosException vpe, int formaPagoEscogida,
            List listaBancos, List listaBancosFranquicias, int canalRecaudoEscogido,DocPagoGeneral docu)
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
                        banco.setIdBanco(codigoBanco);
//                        bancoFranquicia = bf;
//                        ((DocPagoGeneral) documentoPago).setBancoFranquicia(bf);
//                        ((DocPagoGeneral) documentoPago).setNombreBancoFranquicia(arrayDatosBancoFranquicia[2]);
                        nombreBancoFranquicia = arrayDatosBancoFranquicia[2];
                    }

                } else if (cc.getFormName().equals(AWPago.VLR_DOC_PAGO)) {
                    valor = 0.0d;
                    
                } else if (cc.getFormName().equals(AWPago.VLR_LIQUIDADO)) {
                    valorAplicado = 0.0d;
                    
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
        } catch (HermodException e) {
            vpe.addError("No existen campos de captura configurados para validar");
        }
        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }
        documentoPago = new DocPagoGeneral(formaPagoEscogida, fechaDocu, noConsignacion, codSucursal,
                noCuenta, valorDocumento, noAprobacion, noDocumento, valorLiquidado, Integer.valueOf(idCtp),
                nombreFormaPago, nombreCanal, nombreBancoFranquicia, bancoFranquicia, banco, saldoDocumento);

        
        return documentoPago;
    }

    private boolean adicionarAplicacion(HttpServletRequest request,DocPagoGeneral Pago,String doc) throws AccionWebException {

        ValidacionParametrosException vpe = new ValidacionParametrosException();
        HttpSession session = request.getSession();
        boolean Resp = false;
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        preservarInfo(request);
        //SacarPagoAplicacion
            try {
                HermodService hs ;
                hs = HermodService.getInstance();
                boolean existe = hs.validacionCorreccion(doc);
                if(existe){
                    return true;
                }
                DocumentoPago pag = SacarPagoAplicacion(request,Pago,doc);
                boolean tempres = hs.guardardocumentopagoantesdecorreccion(doc,String.valueOf(usuarioNeg.getIdUsuario()));
                pag.setIdDocumentoPago(doc);
                if(!tempres){
                    vpe.addError("Fallo almacenando el registro.");
                    throw vpe;
                }else{
                    boolean actualizar = hs.actualizarEstadoDocumento(pag);
                    if(actualizar){
                         Resp = true;
                    }else{
                        Resp  = false;
                        vpe.addError("Fallo en el servicio de Actualizacion.");
                        throw vpe;
                        
                    }
                    
                }
            } catch (HermodException ex) {
                Logger.getLogger(AWCorreccionCanalRecaudo.class.getName()).log(Level.SEVERE, null, ex);
            }
        return Resp;
    }
    
    protected static Object copy(Object orig) {
    		Object obj = null;

    		try {
    			// Write the object out to a byte array
    			ByteArrayOutputStream bos = new ByteArrayOutputStream();
    			ObjectOutputStream out = new ObjectOutputStream(bos);
    			out.writeObject(orig);
    			out.flush();
    			out.close();

    			// Make an input stream from the byte array and read
    			// a copy of the object back in.
    			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
    			obj = in.readObject();
    		} catch (IOException e) {
    			
    		} catch (ClassNotFoundException cnfe) {
    			
    		}

    		return obj;
    	}
        /**
     * * @author : HGOMEZ
     *** @change : Nuevo flujo para las formas de pago. ** TODO el campo int
     * formaPagoEscogida no debe ir, se debe comentar el método de arriba **
     * Caso Mantis : 12422
     */
    private DocumentoPago construirNuevaDocumentoPago(HttpServletRequest request,
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
        }
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
    private DocumentoPago construirAplicacionPagoEfectivo(
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
        
        return documentoEfectivo;
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
    private DocumentoPago construirAplicacionPagoTimbreBanco(
            HttpServletRequest request, ValidacionParametrosException vpe)
            throws ValidacionParametrosException {
        String numeroTimbre = "";
       
        
        numeroTimbre = request.getParameter(AWPago.NO_DOC_PAGO).replaceAll(" ", "");
        numeroTimbre = (numeroTimbre == null) ? "" : numeroTimbre;

        Date d = null;

       

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
                numeroTimbre);
        
        return documentopago;
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
        
        String nomBanco = "";

   
            //Inicializar los atributos si es nueva consignacion.

            codBanco = request.getParameter(AWPago.COD_BANCO);
            
            nomBanco = getNombreBanco(codBanco, listaBancos);
            if ((codBanco == null) || nomBanco.equals("")) {
                vpe.addError("Para la consignación el código del Banco no puede ser vacío.");
            }

        
            codSucursal = request.getParameter(AWPago.COD_SUCURSAL_BANCO);
            if (codSucursal == null) {
                vpe.addError("El código de la sucursal es inválido.");
            }

            strFecha = request.getParameter(AWPago.FECHA_CONSIGNACION);
            //strFecha = request.getParameter(AWPago.FECHA_DOCUMENTO_PAGOS);
            if (strFecha == null) {
                vpe.addError("Para la consignación la fecha de consigación no puede ser vacía.");
            }

            fecha = darFecha(strFecha);
            if (fecha == null) {
                vpe.addError("Para la consignación la fecha dada es inválida.");
            }
          
          
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
    private DocumentoPago construirAplicacionNuevaPagoConsignacion(
            HttpServletRequest request, ValidacionParametrosException vpe,
            List listaBancos, boolean nueva) throws ValidacionParametrosException {

        String codBanco;            // Banco/Franquicia
        String codSucursal = "";    // Ya no será necesario su uso por tanto se inicia vacía
        String numConsignacion = "";     // Número documento pago
       
        String nomBanco = "";

     
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
            //Edgar Lora: Mantis: 0012422
             numConsignacion = request.getParameter(AWPago.NO_DOC_PAGO);
            if (numConsignacion == null || numConsignacion.length() <= 0) {
                vpe.addError("El número del documento con tarjeta de crédito o tarjeta débito es inválido.");
            }else{
                numConsignacion = numConsignacion.trim();
            }
            if (vpe.getErrores().size() > 0) {
                throw vpe;
            }
        
        Banco banco = new Banco(codBanco, nomBanco);
        DocumentoPago documentoConsignacion = new DocPagoConsignacion(banco, codSucursal, numConsignacion);
        
        return documentoConsignacion;
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo.
     */
    private DocumentoPago construirAplicacionPagoTarjetaCreditoDebito(
            HttpServletRequest request, ValidacionParametrosException vpe,
            List listaBancos, List listaBancosFranquicias, boolean nueva,
            int formaPagoEscogida) throws ValidacionParametrosException {

        String codigoBanco;             // Banco
        int codigoFranquicia;           // Franquicia
        String numeroDocumentoPago;     // Número documento pago
        
        String nombreBanco = "";
        String nombreFranquicia = "";

        String numeroAprobacion = "";

        List listaTc = (List) request.getSession().getAttribute(WebKeys.LISTA_TARJETA_CREDITO);
        List listaTd = (List) request.getSession().getAttribute(WebKeys.LISTA_TARJETA_DEBITO);
        String formaPago = request.getParameter(AWPago.FORMA_TIPOS_PAGOS);

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

            
            numeroDocumentoPago = request.getParameter(AWPago.NO_DOC_PAGO);
            if (numeroDocumentoPago == null || numeroDocumentoPago.length() <= 0) {
                vpe.addError("El número del documento con tarjeta de crédito o tarjeta débito es inválido.");
            }else{
                numeroDocumentoPago = numeroDocumentoPago.trim();
            }

            numeroAprobacion = request.getParameter(AWPago.NUMERO_APROBACION_PAGOS).replaceAll(" ", "");
            numeroAprobacion = (numeroAprobacion == null) ? "" : numeroAprobacion;
            if (numeroAprobacion.length() == 0) {
                vpe.addError("El campo número aprobación no puede estar vacío.");
            }

            if (vpe.getErrores().size() > 0) {
                throw vpe;
            }
        
        BancoFranquicia bancoFranquicia = new BancoFranquicia(codigoBanco, codigoFranquicia);

        DocumentoPago doc = null;
        if (formaPagoEscogida == 11) {
             doc = new DocPagoTarjetaCredito(bancoFranquicia, numeroDocumentoPago, numeroAprobacion);
           
        }

        if (formaPagoEscogida == 12) {
             doc = new DocPagoTarjetaDebito(bancoFranquicia, numeroDocumentoPago, numeroAprobacion);
            
        }

        return doc;
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
    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo.
     */
    //Edgar Lora: Mantis: 0012422
    private DocumentoPago construirAplicacionPagoPse(
            HttpServletRequest request, ValidacionParametrosException vpe,
            List listaBancosFranquicias)
            throws ValidacionParametrosException {
        String numeroAprobacion = "";
        numeroAprobacion = request.getParameter(AWPago.NUMERO_APROBACION_PAGOS).replaceAll(" ", "");
        numeroAprobacion = (numeroAprobacion == null) ? "" : numeroAprobacion;
        
        
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

        

        //Edgar Lora: Mantis: 0012422
        BancoFranquicia bancoFranquicia = new BancoFranquicia(codigoBanco, codigoFranquicia);
        DocumentoPago documentopago = new DocPagoElectronicoPSE(bancoFranquicia, numeroAprobacion);
        
//        DocumentoPago documentopago = new DocPagoElectronicoPSE(numeroAprobacion, fechaString, valorPSE);
//        AplicacionPago aplicacionPagoElectronicoPSE = new AplicacionPago(documentopago, valorPSE);
        return documentopago;
    }

    /*
     * @autor         : HGOMEZ 
     * @mantis        : 12422 
     * @Requerimiento : 049_453 
     * @descripcion   : Método nuevo.
     */
    private DocumentoPago construirAplicacionConvenio(
            HttpServletRequest request, ValidacionParametrosException vpe)
            throws ValidacionParametrosException {

        double valorConvenio = 0;

  
        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        DocumentoPago documentoConvenio = new DocPagoConvenio(valorConvenio);
        
        return documentoConvenio;
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
    private DocumentoPago construirAplicacionPagoCheque(
            HttpServletRequest request, ValidacionParametrosException vpe,
            List listaBancos, boolean nuevoCheque) throws ValidacionParametrosException {

        //atributos cheque
        String codBanco = "";       // Banco/Franquicia
        String codSucursal = "";    // Ya no será necesario su uso por tanto se inicia vacía
        // para no afectar la lógica de negocio existente   
        String strFechaCheque = ""; // Fecha documento
        String numCuenta = "";
        String numCheque = "";      // Número documento pago
        
        String nomBanco = "";

        
            //Obtener parametros de la tabla de nuevo cheque
            codBanco = request.getParameter(AWPago.COD_BANCO);
            nomBanco = getNombreBanco(codBanco, listaBancos);
            if ((codBanco == null) || nomBanco.equals("")) {
                vpe.addError("Para el cheque el código del Banco es inválido.");
            }
            strFechaCheque = request.getParameter(AWPago.FECHA_DOCU);
            numCuenta = (request.getParameter(AWPago.NUM_CUENTA) != null) ? request.getParameter(AWPago.NUM_CUENTA) : "";
            numCheque = (request.getParameter(AWPago.NO_DOC_PAGO) != null) ? request.getParameter(AWPago.NO_DOC_PAGO) : "";
            if (numCheque.length() == 0) {
                vpe.addError("Para el cheque el número de cheque es inválido.");
            }
        

        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }

        Banco banco = new Banco(codBanco, nomBanco);
        DocumentoPago documentoCheque = new DocPagoCheque(banco, codSucursal, numCuenta, numCheque);
        
    
        return documentoCheque;
    }

    private DocPagoGeneral ActualizarObjetoDocumentopago(
            HttpServletRequest request, ValidacionParametrosException vpe, int formaPagoEscogida,
            List listaBancos, List listaBancosFranquicias, int canalRecaudoEscogido,DocPagoGeneral documentoPago)
            throws ValidacionParametrosException {
        
        HermodService hs;
        int codigoFranquicia = 0, idTipoFranquicia = 0;
        CanalesRecaudo canalesRecaudo;
        TipoPago tipoPago;
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        String fechaDocu = null, noConsignacion = null, codSucursal = null, noCuenta = null, noAprobacion = null, noDocumento = null,
                noPin = null, noNir = null, noCus = null, codigoBanco = null, nombreCanal = null, nombreFormaPago = null,
                nombreBancoFranquicia = null, idCtp = null, saldoDocumentoString = null;

        String cuentaDestino = request.getParameter(AWPago.ID_CUENTA_BANCARIA);

        Banco banco = new Banco();
        BancoFranquicia bancoFranquicia = new BancoFranquicia();
        Date date = new Date();
        try {
//            ((DocPagoGeneral) documentoPago).setIdTipoDocumentoPago(formaPagoEscogida);
            hs = HermodService.getInstance();
            List listaCamposCaptura = hs.camposCapturaXFormaPago(formaPagoEscogida + "");
            canalesRecaudo = hs.getCanalRecaudoByID(canalRecaudoEscogido);
            tipoPago = hs.getTipoPagoByID(formaPagoEscogida);

           

            if (canalesRecaudo != null) {
                idCtp = hs.getIdCtpByParamenters(String.valueOf(formaPagoEscogida), circulo.getIdCirculo(), canalesRecaudo.getIdCanal(), cuentaDestino);
                nombreCanal = canalesRecaudo.getNombreCanal();
            }

            if (tipoPago != null) {
                nombreFormaPago = tipoPago.getNombre();
            }

            Iterator itr = listaCamposCaptura.iterator();
            int idCuentaBancaria = 0;

            while (itr.hasNext()) {

                CamposCaptura cc = (CamposCaptura) itr.next();
                String campoValidar = request.getParameter(cc.getFormName());
                  if(cc.getFormName().equals(AWPago.NUM_CUENTA)){
                         noCuenta = request.getParameter(cc.getFormName());
                        if (noCuenta == null || noCuenta.trim().equals("")) {
                            vpe.addError("El No Cuenta no puede estar vacio");
                        }
                  }
                  if (cc.getFormName().equals(AWPago.CUENTA_DESTINO)) {

                    if (cuentaDestino == null || cuentaDestino.trim().equals("") || cuentaDestino.equals(WebKeys.SIN_SELECCIONAR)) {
                        vpe.addError("El campo CUENTA DESTINO no puede estar vacío.");
                    } else {
                        idCuentaBancaria = Integer.parseInt(cuentaDestino);
                        CuentasBancarias cuentasBancarias;

                        try {
                            cuentasBancarias = hs.getCuentasBancariasByID(idCuentaBancaria);
                            if (cuentasBancarias != null) {
                            }
                        } catch (HermodException e) {
                            vpe.addError("No existe la cuenta bancaria relacionada, por favor validar");
                        }
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
                        bancoFranquicia = new BancoFranquicia(codigoBanco, codigoFranquicia);
                        banco.setIdBanco(codigoBanco);
                        nombreBancoFranquicia = arrayDatosBancoFranquicia[2];
                    }

                } else if (cc.getFormName().equals(AWPago.VLR_DOC_PAGO)) {
                    
                } else if (cc.getFormName().equals(AWPago.VLR_LIQUIDADO)) {
                    
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
        } catch (HermodException e) {
            vpe.addError("No existen campos de captura configurados para validar");
        }
        if (vpe.getErrores().size() > 0) {
            throw vpe;
        }
        documentoPago.setBanco(banco);
        documentoPago.setBancoFranquicia(bancoFranquicia);
        documentoPago.setNombreBancoFranquicia(nombreBancoFranquicia);
        documentoPago.setIdTipoDocPago(formaPagoEscogida);
        documentoPago.setNoConsignacion(noConsignacion);
        documentoPago.setCodSucursal(codSucursal);
        documentoPago.setNoDocumento(noDocumento);
        documentoPago.setNoCuenta(noCuenta);
        documentoPago.setNoAprobacion(noAprobacion);
        documentoPago.setNombreFormaPago(nombreFormaPago);
        documentoPago.setIdCtp(Integer.valueOf(idCtp));
        documentoPago.setNombreCanal(nombreCanal);
        return documentoPago;
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

    private void preservarInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(AWPago.COD_BANCO, request.getParameter(AWPago.COD_BANCO));
        session.setAttribute(AWPago.COD_BANCO_YA_REGISTRADA, request.getParameter(AWPago.COD_BANCO_YA_REGISTRADA));
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
        session.setAttribute(AWTrasladoTurno.DEVOLUCION, session.getAttribute(AWTrasladoTurno.DEVOLUCION));
        session.setAttribute(AWTrasladoTurno.MAYOR_VALOR, session.getAttribute(AWTrasladoTurno.MAYOR_VALOR));
        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO, session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO));
        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO, session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO));
        session.setAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE, session.getAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE));
        session.setAttribute(AWTrasladoTurno.LISTA_EFECTIVO, session.getAttribute(AWTrasladoTurno.LISTA_EFECTIVO));
        session.setAttribute(AWTrasladoTurno.LISTA_CONVENIO,session.getAttribute(AWTrasladoTurno.LISTA_CONVENIO));
        session.setAttribute(AWTrasladoTurno.LISTA_CONSIGNACION,session.getAttribute(AWTrasladoTurno.LISTA_CONSIGNACION));
        session.setAttribute(AWTrasladoTurno.LISTA_CHEQUE, session.getAttribute(AWTrasladoTurno.LISTA_CHEQUE));

        session.setAttribute(AWTrasladoTurno.LISTA_GENERAL,session.getAttribute(AWTrasladoTurno.LISTA_GENERAL));
        session.setAttribute(AWTrasladoTurno.LISTA_GENERAL_VM,session.getAttribute(AWTrasladoTurno.LISTA_GENERAL_VM));

        session.setAttribute(AWTrasladoTurno.LISTA_MAYOR_VALOR,session.getAttribute(AWTrasladoTurno.LISTA_MAYOR_VALOR));
        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO_VM, session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO_VM));
        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO_VM, session.getAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO_VM));
        session.setAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE_VM, session.getAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE_VM));
        session.setAttribute(AWTrasladoTurno.LISTA_EFECTIVO_VM,  session.getAttribute(AWTrasladoTurno.LISTA_EFECTIVO_VM));
        session.setAttribute(AWTrasladoTurno.LISTA_CONVENIO_VM,  session.getAttribute(AWTrasladoTurno.LISTA_CONVENIO_VM));
        session.setAttribute(AWTrasladoTurno.LISTA_CONSIGNACION_VM, session.getAttribute(AWTrasladoTurno.LISTA_CONSIGNACION_VM));
        session.setAttribute(AWTrasladoTurno.LISTA_CHEQUE_VM,session.getAttribute(AWTrasladoTurno.LISTA_CHEQUE_VM));

    }
    
    private void eliminarInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(AWPago.COD_BANCO, "SIN_SELECCIONAR");
        session.removeAttribute(AWPago.COD_BANCO_YA_REGISTRADA);
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

    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
           request.getSession().removeAttribute("ACTUAL");
    }

}
