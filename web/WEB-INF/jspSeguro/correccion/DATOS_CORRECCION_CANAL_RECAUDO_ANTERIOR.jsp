<%@page import="org.auriga.util.FechaConFormato"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gov.sir.core.negocio.modelo.Usuario"%>
<%@page import="gov.sir.fenrir.FenrirService"%>
<%@page import="gov.sir.core.negocio.modelo.TipoPago"%>
<%@page import="gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced"%>
<%@page import="gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralCorreccionEnhanced"%>
<%@page import="gov.sir.core.negocio.modelo.Banco"%>
<%@page import="gov.sir.core.negocio.modelo.CirculoTipoPago"%>
<%@page import="gov.sir.core.negocio.modelo.DocPagoGeneralCorreccion"%>
<%@page import="gov.sir.core.negocio.modelo.UsuarioPk"%>
<%@page import="gov.sir.core.negocio.modelo.DocumentoPagoCorreccion"%>
<%@page import="gov.sir.core.web.helpers.correccion.PagoCanalesRecaudoHelper"%>
<%@page import="gov.sir.hermod.HermodException"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gov.sir.hermod.HermodService"%>
<%@page import="gov.sir.core.web.helpers.comun.DatosPagoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.AplicacionPago"%>
<%@page import="gov.sir.core.negocio.modelo.DocumentoPago"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCorreccionCanalRecaudo"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%
    TextHelper textHelper = new TextHelper();
    String datoVacio = "N/A";
    String razonPagoGral = "MAYOR VALOR"; 
    String conservacionmayor = (String) session.getAttribute(AWTrasladoTurno.VALOR_CONSERVACION_MAYOR_VALOR);
      
    HermodService hs;
    FenrirService fs;
    List canalesXCirculo = new ArrayList();
    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

    try {
        hs = HermodService.getInstance();
        canalesXCirculo = hs.getCanalesRecaudoXCirculo(circulo);
    } catch (HermodException e) {
    }
    request.getSession().setAttribute(WebKeys.LISTA_CANALES_RECAUDO_CIRCULO, canalesXCirculo);
  
    String posEfec =  request.getParameter("posEfec");    
    if(request.getParameter("posEfec") == null ||request.getParameter("posEfec").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posEfec")){
                posEfec = parts[1].toString();
            } 
        }   
    }else{
        String posEfec1 = "posEfec,"+posEfec;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posEfec1);
    }
    
    String posConv =  request.getParameter("posConv");
    if(request.getParameter("posConv") == null ||request.getParameter("posConv").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posConv")){
                posConv = parts[1].toString();
            }
        }
    }else{
        String posConv1 = "posConv,"+posConv;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posConv1);
    }
    String posPse =  request.getParameter("posPse");
    if(request.getParameter("posPse") == null ||request.getParameter("posPse").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posPse")){
                posPse = parts[1].toString();
            }
        }
    }else{
        String posPse1 = "posPse,"+posPse;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posPse1);
    }
    String posTDebito =  request.getParameter("posTDebito");
    if(request.getParameter("posTDebito") == null ||request.getParameter("posTDebito").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posTDebito")){
                posTDebito = parts[1].toString();
            }
        }
    }else{
        String posTDebito1 = "posTDebito,"+posTDebito;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posTDebito1);
    }
    String posTCredito = request.getParameter("posTCredito");
    if(request.getParameter("posTCredito") == null ||request.getParameter("posTCredito").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posTCredito")){
                posTCredito = parts[1].toString();
            }
        }
    }else{
        String posTCredito1 = "posTCredito,"+posTCredito;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posTCredito1);
    }
    String posCheque =  request.getParameter("posCheque");
    if(request.getParameter("posCheque") == null ||request.getParameter("posCheque").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posCheque")){
                posCheque = parts[1].toString();
            }
        }
    }else{
        String posCheque1 = "posCheque,"+posCheque;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posCheque1);
    }
    String posConsig =  request.getParameter("posConsig");
    if(request.getParameter("posConsig") == null ||request.getParameter("posConsig").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posConsig")){
                posConsig = parts[1].toString();
            }
        }
    }else{
        String posConsig1 = "posConsig,"+posConsig;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posConsig1);
    }
    String posGral =  request.getParameter("posGral");
    if(request.getParameter("posGral") == null ||request.getParameter("posGral").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posGral")){
                posGral = parts[1].toString();
            }
        }
    }else{
        String posGral1 = "posGral," + posGral;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posGral1);
    }
    String posMayorValor = request.getParameter("posMayorValor");
    String posEfecMV =  request.getParameter("posEfecMV");
    if(request.getParameter("posEfecMV") == null ||request.getParameter("posEfecMV").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posEfecMV")){
                posEfecMV = parts[1].toString();
            }
        }
    }else{
        String posEfecMV1 = "posEfecMV," + posEfecMV;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posEfecMV1);
    }
    String posConvMV =  request.getParameter("posConvMV");
    if(request.getParameter("posConvMV") == null ||request.getParameter("posConvMV").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posConvMV")){
                posConvMV = parts[1].toString();
            }
        }
    }else{
        String posConvMV1 = "posConvMV," + posConvMV;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posConvMV1);
    }
    String posPseMV =  request.getParameter("posPseMV");
    if(request.getParameter("posPseMV") == null ||request.getParameter("posPseMV").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posPseMV")){
                posPseMV = parts[1].toString();
            }
        }
    }else{
        String posPseMV1 = "posPseMV," + posPseMV;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posPseMV1);
    }
    String posTDebitoMV =  request.getParameter("posTDebitoMV");
    if(request.getParameter("posTDebitoMV") == null ||request.getParameter("posTDebitoMV").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posTDebitoMV")){
                posTDebitoMV = parts[1].toString();
            }
        }
    }else{
        String posTDebitoMV1 = "posTDebitoMV," + posTDebitoMV;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posTDebitoMV1);
    }
    String posTCreditoMV =  request.getParameter("posTCreditoMV");
    if(request.getParameter("posTCreditoMV") == null ||request.getParameter("posTCreditoMV").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posTCreditoMV")){
                posTCreditoMV = parts[1].toString();
            }
        }
    }else{
        String posTCreditoMV1 = "posTCreditoMV," + posTCreditoMV;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posTCreditoMV1);
    }
    String posChequeMV =  request.getParameter("posChequeMV");
    if(request.getParameter("posChequeMV") == null ||request.getParameter("posChequeMV").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posChequeMV")){
                posChequeMV = parts[1].toString();
            }
        }
    }else{
        String posChequeMV1 = "posChequeMV," + posChequeMV;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posChequeMV1);
    }
    String posConsigMV =  request.getParameter("posConsigMV");
    if(request.getParameter("posConsigMV") == null ||request.getParameter("posConsigMV").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posConsigMV")){
                posConsigMV = parts[1].toString();
            }
        }
    }else{
        String posConsigMV1 = "posConsigMV," + posConsigMV;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posConsigMV1);
    }
    String posGralMV =  request.getParameter("posGralMV");
    if(request.getParameter("posGralMV") == null ||request.getParameter("posGralMV").equals("")){
        if(request.getSession().getAttribute("ACTUAL1") != null){
            String Actual = (String) request.getSession().getAttribute("ACTUAL1");
            String[] parts = Actual.split(",");
            if(parts[0].equals("posGralMV")){
                posGralMV = parts[1].toString();
            }
        }
    }else{
        String posGralMV1 = "posGralMV," + posGralMV;
        request.getSession().removeAttribute("ACTUAL1");
        request.getSession().setAttribute("ACTUAL1", posGralMV1);
    }  

%>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
  <script>
    function cerrarpop(){window.close()}
  </script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>    
    <tr>
        <td>&nbsp;</td>
        <td class="tdtablaanexa02">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral"></td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_correccion_canal_recaudo.gif" width="16" height="21"></td>
                                        </tr>
                                    </table>
                                </td>                               
                                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table>
                    </td>                    
                    <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno asociado</td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_correccion_canal_recaudo.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        <br>
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Turno asociado</td>
                                <td>&nbsp;</td>
                            </tr>                            
                            <tr>
                                <td>&nbsp;</td>
                                <td>Turno sobre el cual se realizará la corrección</td>   
                                <td>
                                    <% try {
                                            textHelper.setNombre(CTurno.ID_TURNO);
                                            textHelper.setCssClase("camposformtext");
                                            textHelper.setId(CTurno.ID_TURNO);
                                            textHelper.setFuncion(CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR);
                                            textHelper.render(request, out);
                                        } catch (HelperException re) {
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>                               
                            </tr>
                        </table>                       
                    </td>
                    <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                    List canales = (List) session.getAttribute(AWTrasladoTurno.LISTA_CANAL_RECAUDO_Y_CUENTAS_BANCARIAS);
                    if (posGral != null) {
                        int i = Integer.parseInt(posGral);
                        List general = (List) session.getAttribute(AWTrasladoTurno.LISTA_GENERAL);
                        if (general != null && !general.isEmpty()) {
                        Object obs = ((List) general.get(i)).get(13);
                         String ob = obs.toString();
                        DocumentoPagoCorreccionEnhanced Documento = null;
                        DocPagoGeneralCorreccionEnhanced esss = null;
                        TipoPago formapago = null;
                        List lista = null;
                        String Usuariomodifica = "";
                        String UusarioCrea = "";
                        
                        try {
                             hs = HermodService.getInstance();
                              lista= hs.getDocumentoCorregido(ob);
                             fs = FenrirService.getInstance();
                             Documento = (DocumentoPagoCorreccionEnhanced) lista.get(0);
                             esss = (DocPagoGeneralCorreccionEnhanced) Documento;
                             if(Documento.getUsuariomodifica() != null){
                                 UsuarioPk llave = new UsuarioPk(Documento.getUsuariomodifica());
                                 Usuario modifica = fs.getUsuarioById(llave);
                                 Usuariomodifica = modifica.getUsername();
                             }
                             if(Documento.getUsuariocreacion()!= null){
                                 UsuarioPk llave = new UsuarioPk(Documento.getUsuariocreacion());
                                 Usuario creacion = fs.getUsuarioById(llave);
                                 UusarioCrea = creacion.getUsername();
                             }
                             
                        } catch (HermodException e) {
                            
                        }   catch (Throwable es){
                            
                        }
                        
                %>   
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <input type="hidden" name="id_dato_lista_general" id="id_dato_lista_general_<%=i%>" value="<%=i%>">
                       <table width="100%" class="camposform">                           
                            <br>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%=Documento.getNombreCanal() %></td>                                

                                <%if (!lista.get(2).toString().equals("")) {%> 
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=lista.get(2).toString()%></td>     
                                <%} else {%>
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (esss.getTipoPago()!= null) {%>
                                <td>Forma de Pago</td>                                
                                <td class="campositem"><%=esss.getTipoPago()%></td>
                                <%} else {%>
                                <td>Forma de Pago</td> 
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                <%if (!lista.get(3).toString().equals("")) {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=lista.get(3).toString()%> - <%=lista.get(1).toString()%></td>
                                <%}else {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                            <tr>
                                 <td>&nbsp;</td>
                                <%if (esss.getNoDocumento()!= null) {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=esss.getNoDocumento()%></td>
                                <%}else {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                <%if (esss.getNoAprobacion()!= null) {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=esss.getNoAprobacion()%></td>
                                <%}else {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>            
                                                                                         
                            </tr>
                            
                            <tr>
                               <td>&nbsp;</td>
                                <%if (Documento.getValorDocumento() != 0) {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=java.text.NumberFormat.getInstance().format(Documento.getValorDocumento())%></td>
                                <%} else {%>
                                <td>Valor documento pago</td>
                                <td class="campositem">0</td>
                                <%}%>   

                                <%if (esss.getNoConsignacion() != null) {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=esss.getNoConsignacion()%></td>
                                <%} else {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                             <tr>   
                                <td>&nbsp;</td>
                                <%if (!UusarioCrea.equals("")) {%>
                                <td>Usuario Creacion</td>
                                <td class="campositem"><%=UusarioCrea%></td>
                                <%} else {%>
                                <td>Usuario Creacion</td>
                                <td class="campositem">0</td>
                                <%}%>
                                
                                <%if (Documento.getFechaCreacion()!= null) { %>
                                <td>Fecha de Creacion</td>
                                <td class="campositem"><%= FechaConFormato.formatear(Documento.getFechaCreacion(), "dd/MM/yyyy")%></td>
                                <%} else {%>
                                <td>Fecha de Creacion</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                             <tr>   
                                <td>&nbsp;</td>
                                <%if (!Usuariomodifica.equals("")) {%>
                                <td>Usuario Modifica</td>
                                <td class="campositem"><%=Usuariomodifica%></td>
                                <%} else {%>
                                <td>Usuario Modifica</td>
                                <td class="campositem">0</td>
                                <%}%>  
                                
                                <%if (Documento.getFechamodificacion()!= null) { %>
                                <td>Fecha de Modificacion</td>
                                <td class="campositem"><%= Documento.getFechamodificacion()%></td>
                                <%} else {%>
                                <td>Fecha de Modificacion</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                             <tr>
                                <td>&nbsp;</td>
                                <%if (esss.getValorDocumento()!= 0) {
                                String valorPagado = java.text.NumberFormat.getInstance().format(esss.getValorDocumento());
                                %>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=valorPagado%></td>
                                <%} else {%>
                                <td>Valor pagado</td>
                                <td class="campositem">0</td>
                                <%}%>                              
                            </tr>
                            
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                
                <%
                        
                        
                        }
                    } else if (posMayorValor != null) {
                           
                    List mayorValor = (List) session.getAttribute(AWTrasladoTurno.LISTA_MAYOR_VALOR);
                    if (mayorValor != null && !mayorValor.isEmpty()) {
                          

                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">                        
                        <table width="100%" class="camposform">
                            <br>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Razón del pago</td>
                                <td width="211" class="campositem"><%=mayorValor.get(0)%></td>

                                <td width="146">Valor derechos de registro</td>
                                <td width="212" class="campositem"><%=mayorValor.get(1)%></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor certificados asociados</td>
                                <td class="campositem"><%= mayorValor.get(2)%> </td>
                                
                                <td>Valor de impuestos</td>
                                <td class="campositem"><%= mayorValor.get(3)%> </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Valor Liquidado</td>
                                <td class="campositem"><%= mayorValor.get(4)%> </td>
                                
                                <td>Valor Conservación Documental</td>
                                <%
                                    double vC = 0.0;
                                    if (conservacionmayor != null && !conservacionmayor.equalsIgnoreCase("null")) {
                                        vC = Double.parseDouble(conservacionmayor);
                                    }
                                %>
                                <td class="campositem"><%=java.text.NumberFormat.getInstance().format(vC)%> </td>
                            </tr>
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%
                        }
                    } else if (posGralMV != null) {
                        int i = Integer.parseInt(posGralMV);
                        List generalMV = (List) session.getAttribute(AWTrasladoTurno.LISTA_GENERAL_VM);
                        if (generalMV != null && !generalMV.isEmpty()) {
                            Object obs = ((List) generalMV.get(i)).get(13);
                            String ob = obs.toString();
                           request.getSession().setAttribute(CCorreccionCanalRecaudo.IDDOCPAG,ob);   
                        List lista = null;
                        DocumentoPagoCorreccionEnhanced Documento = null;
                        DocPagoGeneralCorreccionEnhanced esss = null;
                        TipoPago formapago = null;
                         String Usuariomodifica = "";
                        String UusarioCrea = "";
                        try {
                             hs = HermodService.getInstance();
                             lista= hs.getDocumentoCorregido(ob);
                            fs = FenrirService.getInstance();
                             Documento = (DocumentoPagoCorreccionEnhanced) lista.get(0);
                             esss = (DocPagoGeneralCorreccionEnhanced) Documento;

                             if(Documento.getUsuariomodifica() != null){
                                 UsuarioPk llave = new UsuarioPk(Documento.getUsuariomodifica());
                                 Usuario modifica = fs.getUsuarioById(llave);
                                 Usuariomodifica = modifica.getUsername();
                             }
                             if(Documento.getUsuariocreacion()!= null){
                                 UsuarioPk llave = new UsuarioPk(Documento.getUsuariocreacion());
                                 Usuario creacion = fs.getUsuarioById(llave);
                                 UusarioCrea = creacion.getUsername();
                             }
                             
                        } catch (HermodException e) {
                            
                        } catch (Throwable es){
                            
                        }
                          
                %>
                <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                         <table width="100%" class="camposform">                           
                            <br>
                            <tr>
                                <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="179">Canal de recaudo</td>
                                <td width="211" class="campositem"><%=Documento.getNombreCanal() %></td>                                

                                <%if (!lista.get(2).toString().equals("")) {%> 
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=lista.get(2).toString()%></td>     
                                <%} else {%>
                                <td width="146">Cuenta Destino</td>
                                <td width="212" class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (esss.getTipoPago()!= null) {%>
                                <td>Forma de Pago</td>                                
                                <td class="campositem"><%=esss.getTipoPago()%></td>
                                <%} else {%>
                                <td>Forma de Pago</td> 
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                <%if (!lista.get(3).toString().equals("")) {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=lista.get(3).toString()%> - <%=lista.get(1).toString()%></td>
                                <%}else {%>
                                <td>Banco/Franquicia</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                            <tr>
                                 <td>&nbsp;</td>
                                <%if (esss.getNoDocumento()!= null) {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=esss.getNoDocumento()%></td>
                                <%}else {%>
                                <td>No. Documento pago</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                                
                                <%if (esss.getNoAprobacion()!= null) {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=esss.getNoAprobacion()%></td>
                                <%}else {%>
                                <td>No. Aprobación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>            
                                                                                         
                            </tr>
                            
                            <tr>
                               <td>&nbsp;</td>
                                <%if (Documento.getValorDocumento() != 0) {%>
                                <td>Valor documento pago</td>
                                <td class="campositem"><%=java.text.NumberFormat.getInstance().format(Documento.getValorDocumento())%></td>
                                <%} else {%>
                                <td>Valor documento pago</td>
                                <td class="campositem">0</td>
                                <%}%>   

                                <%if (esss.getNoConsignacion() != null) {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=esss.getNoConsignacion()%></td>
                                <%} else {%>
                                <td>Número consignación</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>                              
                            </tr>
                             <tr>   
                                <td>&nbsp;</td>
                                <%if (!UusarioCrea.equals("")) {%>
                                <td>Usuario Creacion</td>
                                <td class="campositem"><%=UusarioCrea%></td>
                                <%} else {%>
                                <td>Usuario Creacion</td>
                                <td class="campositem">0</td>
                                <%}%>
                                
                                 <%if (Documento.getFechaCreacion()!= null) { %>
                                <td>Fecha de Creacion</td>
                                <td class="campositem"><%= FechaConFormato.formatear(Documento.getFechaCreacion(), "dd/MM/yyyy")%></td>
                                <%} else {%>
                                <td>Fecha de Creacion</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                             <tr>   
                                <td>&nbsp;</td>
                                <%if (!Usuariomodifica.equals("")) {%>
                                <td>Usuario Modifica</td>
                                <td class="campositem"><%=Usuariomodifica%></td>
                                <%} else {%>
                                <td>Usuario Modifica</td>
                                <td class="campositem">0</td>
                                <%}%>  
                               
                                <%if (Documento.getFechamodificacion()!= null) {%>
                                <td>Fecha de Modificacion</td>
                                <td class="campositem"><%= Documento.getFechamodificacion() %></td>
                                <%} else {%>
                                <td>Fecha de Modificacion</td>
                                <td class="campositem"><%=datoVacio%></td>
                                <%}%>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <%if (esss.getValorDocumento()!= 0) {
                                String valorPagado = java.text.NumberFormat.getInstance().format(esss.getValorDocumento());
                                %>
                                <td>Valor pagado</td>
                                <td class="campositem"><%=valorPagado%></td>
                                <%} else {%>
                                <td>Valor pagado</td>
                                <td class="campositem">0</td>
                                <%}%>                              
                            </tr>
                            
                        </table>
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <%                            
                        }
                    }
                %>                                             
                
               <tr>
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20"><a onclick="cerrarpop()"><input  type="image"  src="<%= request.getContextPath()%>/jsp/images/btn_cerrar_ventana.gif" width="150" height="21" border="0"></a></td>
                            </tr>                            
                        </table>                       
                    </td>
                    <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
            
                
                <tr>
                    <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                    <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                </tr>
            </table>           
        </td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>    
</table>