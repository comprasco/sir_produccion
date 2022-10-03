<%@page import="java.io.IOException"%>
<%@page import="gov.sir.core.web.helpers.correccion.PagoCanalesRecaudoHelper"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPago"%>
<%@page import="org.jboss.util.collection.Iterators"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.DatosPagoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.CirculoTipoPago"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoPago"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWCorreccionCanalRecaudo"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import ="gov.sir.core.web.acciones.comun.AWLocacion" %>
<script>
    function cerrarpop(){window.close()}
    function enviarNuevaForma() {
        if (confirm('Esta seguro que desea guardar los cambios?')){
            document.FormaPago.submit();
          } else {
          }
        
    }
    function CloseMySelf(){
        window.opener.HablaConmigo('true');
        cerrarpop(); 
    } 

</script>
<%
    //Obtener si el pago es de registro
    Boolean registro;

    registro = (Boolean) session.getAttribute("PAGO_REGISTRO_LIQUIDACION");
    registro = registro == null ? new Boolean(false) : registro;
    
    javax.servlet.ServletContext context = session.getServletContext();
    //String numliquidacion = (String) session.getAttribute(WebKeys.VALOR_LIQUIDACION);
    List consignaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);
    List pagosRegistrados = (List) session.getAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS);
    
    List tarjetaCredito = (List) session.getAttribute(WebKeys.LISTA_TARJETA_CREDITO);
    try{
         String isLevantar = (String) session.getAttribute(AWCorreccionCanalRecaudo.CONSTANTESIGUARDO);
        if(isLevantar != null){
         if(isLevantar.equals("1")){
               %>
               <script>
                    CloseMySelf();
                    
               </script>
                <%
           }
        }
    }catch(Exception ezx){
        %>
        <script>
         CloseMySelf();
        </script>
         <%
    }
   
    List tarjetaDebito = (List) session.getAttribute(WebKeys.LISTA_TARJETA_DEBITO);
    //List listPse = (List) session.getAttribute(WebKeys.LISTA_PSE);
    AplicacionPago appConvenio = (AplicacionPago) session.getAttribute(WebKeys.APLICACION_CONVENIO);
    AplicacionPago appPagoElectronicoPSE = (AplicacionPago) session.getAttribute(WebKeys.FORMA_PAGO_PSE);
    AplicacionPago appGeneral = (AplicacionPago) session.getAttribute(WebKeys.APLICACION_PAGO);

    List cheques = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);
    AplicacionPago appEfectivo = (AplicacionPago) session.getAttribute(WebKeys.APLICACION_EFECTIVO);
    AplicacionPago appTimbre = (AplicacionPago) session.getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
    double valorLiquidacion = 0;
    List marcasCheques = (List) session.getAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
    List marcasConsignacion = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);
   
    List marcasTarjetaCredito = (List) session.getAttribute(WebKeys.LISTA_TARJETA_CREDITO_MARCAS);
    List marcasTarjetaDebito = (List) session.getAttribute(WebKeys.LISTA_TARJETA_DEBITO_MARCAS);
    List marcasPse = (List) session.getAttribute(WebKeys.LISTA_PSE_MARCAS);

    /*if (numliquidacion != null) {
        valorLiquidacion = Double.valueOf(numliquidacion).doubleValue();
    } else {
        valorLiquidacion = 0;
    }*/
    String ver = request.getParameter("justview");
    if(ver != null){

    }else{
        PagoCanalesRecaudoHelper datosPagoHelper = new PagoCanalesRecaudoHelper(request, valorLiquidacion,
                   consignaciones, cheques,
                   tarjetaCredito, tarjetaDebito,
                   appPagoElectronicoPSE, appConvenio,
                   appEfectivo, appTimbre,
                   marcasConsignacion, marcasCheques,
                   marcasTarjetaCredito, marcasTarjetaDebito,
                   marcasPse,appGeneral,pagosRegistrados);

           if (session.getAttribute(WebKeys.LIQUIDACION) instanceof LiquidacionTurnoCertificado || session.getAttribute(WebKeys.LIQUIDACION) instanceof LiquidacionTurnoCertificadoMasivo
                   || session.getAttribute(WebKeys.LIQUIDACION) instanceof LiquidacionTurnoFotocopia || session.getAttribute(WebKeys.SOLICITUD) instanceof SolicitudConsulta) {
               datosPagoHelper.setliquidacionRedondeo(true);
           }

           if (session.getAttribute(gov.sir.core.web.acciones.consulta.AWConsulta.TIPO_CONSULTA) != null) {

               if (((String) session.getAttribute(gov.sir.core.web.acciones.consulta.AWConsulta.TIPO_CONSULTA)).equals(gov.sir.core.negocio.modelo.TipoConsulta.TIPO_SIMPLE)) {
                   datosPagoHelper.setAsignarEstacion(true);
               }
           }

           if (registro.booleanValue()) {
               datosPagoHelper.setLiquidacionRegistro(true);
           }

           try {

               List canalCirculoTipoPago = (List) session.getAttribute(WebKeys.LISTA_CANALES_RECAUDO_CIRCULO);
               Iterator itr = canalCirculoTipoPago.iterator();

               if (canalCirculoTipoPago.size() > 0) {
                   datosPagoHelper.setExisteFormaPago(true);
                   while (itr.hasNext()) {

                       CirculoTipoPago circuloTipoPago = (CirculoTipoPago) itr.next();
                       datosPagoHelper.setCanalesRecaudo(circuloTipoPago.getIdCanalRecaudo(), circuloTipoPago.getCanalesRecaudo().getNombreCanal());
                       datosPagoHelper.setFormasPagoMap(circuloTipoPago.getIdCanalRecaudo(),
                               circuloTipoPago.getIdTipoDocPago(),
                               circuloTipoPago.getTipoPago().getNombre());
                   }
               }

               datosPagoHelper.render(request, out);
           } catch (HelperException re) {
               out.println("ERROR " + re.getMessage());
           }

    }

%>


<script language="javascript" src="<%=request.getContextPath()%>/jsp/plantillas/js/pagos.js"></script>
