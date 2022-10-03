<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.OtorganteNatural"%>
<%@page import="gov.sir.core.web.acciones.repartonotarial.AWLiquidacionRepartoNotarial"%>
<%@page import="gov.sir.core.negocio.modelo.EntidadPublica"%>
<%@page import="gov.sir.core.negocio.modelo.CirculoNotarial"%>
<%@page import="gov.sir.core.negocio.modelo.AccionNotarial"%>
<%@page import="gov.sir.core.negocio.modelo.MinutaAccionNotarial"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CImpresion" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CMinuta" %>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<%
    ListaAccionesNotarialesHelper accionHelper = new ListaAccionesNotarialesHelper();
    ListaElementoHelper docHelper = new ListaElementoHelper(); 
	boolean recargaPopUp = false;
	if(session.getAttribute(WebKeys.RECARGA)!=null){  
		recargaPopUp = ((Boolean)session.getAttribute(WebKeys.RECARGA)).booleanValue();
	}

    TextHelper textHelper = new TextHelper();    
	TextHelper hiddenHelper = new TextHelper();
	NotasInformativasHelper helperNotas = new NotasInformativasHelper();
	hiddenHelper.setTipo("hidden");
    RadioHelper radioHelper = new RadioHelper();   	
     
    TextAreaHelper textAreaHelper = new TextAreaHelper();      
    
    if(session.getAttribute(CMinuta.ACCION_NOTARIAL_CON_CUANTIA)== null)
    {
    	session.setAttribute(CMinuta.ACCION_NOTARIAL_CON_CUANTIA, "ACCION_NOTARIAL_CON_CUANTIA");
    }
    if(session.getAttribute(CMinuta.IMPRIMIR_CONSTANCIA)== null)
    {
    	session.setAttribute(CMinuta.IMPRIMIR_CONSTANCIA, "IMPRIMIR_CONSTANCIA");
    }
    	
%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">

function mostrarCategoria(nombre,valor,dimensiones){
		var elegido;
		for (i=0; i < document.REPARTONOTARIAL.TIPO_REPARTO.length; i++) {
 			if (document.REPARTONOTARIAL.TIPO_REPARTO[i].checked) {
   				elegido = i;
   				break;
			}
		}
		url = nombre;
                if(document.REPARTONOTARIAL.TIPO_REPARTO.length > 1){
                    url += '&'+'TIPO_REPARTO='+document.REPARTONOTARIAL.TIPO_REPARTO[elegido].value;
                }else{
                    url += '&'+'TIPO_REPARTO='+document.REPARTONOTARIAL.TIPO_REPARTO.value;
                }
		url += '&'+'CIRCULO_NOTARIAL='+document.REPARTONOTARIAL.CIRCULO_NOTARIAL.options[document.REPARTONOTARIAL.CIRCULO_NOTARIAL.selectedIndex].value;
    	url += '&'+'NRO_FOLIOS_MINUTA='+document.REPARTONOTARIAL.NRO_FOLIOS_MINUTA.value;
		url += '&'+'OBSERVACIONES_REPARTO='+ document.REPARTONOTARIAL.OBSERVACIONES_REPARTO.value;
		url += '&'+'CUANTIA='+document.REPARTONOTARIAL.CUANTIA.value;
		url += '&'+'UNIDADES='+document.REPARTONOTARIAL.UNIDADES.value;
		url += '&'+'IMPRIMIR_CONSTANCIA='+document.REPARTONOTARIAL.IMPRIMIR_CONSTANCIA.value;
		popup=window.open(url,valor,dimensiones);
	    popup.focus();
}

function recargar() {
	document.getElementById('ID_TIPO_ACCION_NOTARIAL').value=document.getElementById('TIPO_ACCION_NOTARIAL').value;        
	cambiarAccion('<%=AWLiquidacionRepartoNotarial.PRESERVAR_INFO%>')
}

function elegirCuantia() 
{
	if(document.REPARTONOTARIAL.<%= CMinuta.ACCION_NOTARIAL_CON_CUANTIA%>.checked == true)
	{
		conCuantia();
	}
	else
	{
		sinCuantia();
	}
}
function sinCuantia() {
	document.getElementById('<%=CMinuta.CUANTIA%>').setAttribute("readonly" , "readonly" , false);
	document.REPARTONOTARIAL.<%=CMinuta.CUANTIA%>.value = '0';
}
function conCuantia() {
	document.getElementById('<%=CMinuta.CUANTIA%>').removeAttribute("readonly"  , false);
	
	document.REPARTONOTARIAL.<%=CMinuta.CUANTIA%>.readonly = false;	
		
}
function cambiarAccion(text) {
	document.REPARTONOTARIAL.ACCION.value = text;
	document.REPARTONOTARIAL.submit();
}
function eliminar(pos, ejecucion) {
	document.REPARTONOTARIAL.<%=WebKeys.POSICION%>.value = pos;
        cambiarAccion(ejecucion);
}

/**
 * @author: Diana Lora
 * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
 */
function actualizarCuantia(radio, cuantia, unidades, idAccionNotarial){
    if(radio.checked == true){
        document.getElementById('<%=CMinuta.CUANTIA_SELECCIONADA%>').value = cuantia;
        document.getElementById('<%=CMinuta.UNIDAD_SELECCIONADA%>').value = unidades;
        document.getElementById('<%=CMinuta.ID_ACCION_NOTARIAL_SELECCIONADA%>').value = idAccionNotarial;
    }
}

function locacion(nombre,valor,dimensiones){
	document.getElementById('id_depto').value=valor+"_ID_DEPTO";
	document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
	document.getElementById('id_munic').value=valor+"_ID_MUNIC";
	document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
	document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
	document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
    popup=window.open(nombre,valor,dimensiones);
    popup.focus();
}

function cambiarValorTipoAccionNotarial(text)
{
       try
       {
         document.getElementById('TIPO_ACCION_NOTARIAL').value=document.getElementById("ID_TIPO_ACCION_NOTARIAL").value;
         if(document.getElementById('TIPO_ACCION_NOTARIAL').value=="")
         {
           document.getElementById('TIPO_ACCION_NOTARIAL').value='<%=WebKeys.SIN_SELECCIONAR%>';
       	   document.getElementById('ID_TIPO_ACCION_NOTARIAL').value='';
         }
       }catch(e){
       	document.getElementById('TIPO_ACCION_NOTARIAL').value='<%=WebKeys.SIN_SELECCIONAR%>';
       	document.getElementById('ID_TIPO_ACCION_NOTARIAL').value='';
       }
}

function cambiarAccionForm(text) 
{
	   document.REGISTRO.ACCION.value = text;
}
</script>

<%
	List circuloNotarial = (List)session.getAttribute(WebKeys.LISTA_CIRCULOS_NOTARIALES);
	if(circuloNotarial ==null){
%>
<script>
function send() {
	document.CARGAR_CIRCULO_NOTARIAL.submit();
} 
</script>
<body bgcolor="#CDD8EA"  onLoad="send()"   background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <form action="turnoLiquidacionRepartoNotarial.do" method="POST" name="CARGAR_CIRCULO_NOTARIAL"  id="CARGAR_CIRCULO_NOTARIAL">
  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=WebKeys.ACCION%>" value="<%=AWLiquidacionRepartoNotarial.CARGAR_CIRCULOS_NOTARIALES%>">
	</form>
</body>
<%
   }
%>


<%
	String accionNotarial = (String)session.getAttribute(CMinuta.TIPO_ACCION_NOTARIAL);
	boolean sinCuantia = false;
	if(accionNotarial!=null){
		session.setAttribute(CMinuta.TIPO_ACCION_NOTARIAL,accionNotarial);
	}
%>


<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Radicaci&oacute;n de minuta </td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral"> Minuta </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                    </tr>
                  </table></td>
                <!--AHERRENO 17/05/2012
                    REQ 076_151 TRANSACCIONES-->        
                <td width="120" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                        <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                        <td class="titulotbcentral"><%= request.getSession().getAttribute("TIEMPO_TRANSACCION")%> Seg.</td>                
                    </tr>
                </table></td>                                                                                  
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr> 
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"> 
            <form action="turnoLiquidacionRepartoNotarial.do" method="post" name="REPARTONOTARIAL" id="REPARTONOTARIAL">

				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.repartonotarial.AWLiquidacionRepartoNotarial.LIQUIDAR%>">
				<input type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>">		            

				
              <br>
              
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos 
                    Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                <tr> 
                  
                  <td width="20%"><strong>Tipo de radicación</strong></td>
                  <%
                  String repartoMinutaOrdinario = (String)request.getSession().getAttribute(WebKeys.REPARTO_MINUTAS_EN_HORARIO);
                  if(repartoMinutaOrdinario.equals("true")){
                  %>
                  <td width="20%" align='right'><strong>Ordinaria</strong></td>
                  <%
                  }else{
                    %>
                    <td width="20%" align='right'>Ordinaria</td>
                    <%
                  }
                  %>
                  <td width="20%">
						<% try {
 		                        String defecto = (String)request.getSession().getAttribute(CMinuta.TIPO_REPARTO);
 		                        if(defecto==null){
                                            if(repartoMinutaOrdinario.equals("true")){
 		                        	request.getSession().setAttribute(CMinuta.TIPO_REPARTO,CMinuta.ORDINARIO);
                                             }else{
                                                request.getSession().setAttribute(CMinuta.TIPO_REPARTO,CMinuta.EXTRAORDINARIO);
                                             }
 		                        }														
 		                        
                                            if(repartoMinutaOrdinario.equals("true")){
                                                radioHelper.setValordefecto(CMinuta.ORDINARIO);
                                                radioHelper.setNombre(CMinuta.TIPO_REPARTO);
                  			        radioHelper.setId(CMinuta.TIPO_REPARTO);
                                                radioHelper.render(request,out);
                                            }
							    
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
						 %>    
  				
					</td>
                  <td width="20%" align='right'><strong>Extraordinaria</strong></td>
                  <td width="20%">
						<% try {
 		                        radioHelper.setNombre(CMinuta.TIPO_REPARTO);
                  			    radioHelper.setId(CMinuta.TIPO_REPARTO);
                  			    radioHelper.setValordefecto(CMinuta.EXTRAORDINARIO);                  			    
							    radioHelper.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
						 %>   
                  </td>
                </tr>
              </table>              

              <br>
              <hr class="linehorizontal">
              <br>   
              
              <table width="100%" class="camposform">
                <tr> 
                  
                  <td width="15%">Circulo notarial</td>
                  <td colspan='3'>
                      <% try {
                      		List circulosNotariales = (List) session.getAttribute(WebKeys.LISTA_CIRCULOS_NOTARIALES);
                      		List listaCirculosNotariales = new ArrayList();
							
							if(circulosNotariales!=null){
								Iterator it = circulosNotariales.iterator();
								
                                                                /**
                                                                *@autor:Edgar Lora
                                                                *@mantis:0010815
                                                                *@Requerimiento:072_151
                                                                */
								while (it.hasNext()){                                                                    
									CirculoNotarial cn = (CirculoNotarial)it.next();
                                                                        if(cn.isIncluirEnReparto()){
                                                                            listaCirculosNotariales.add(new ElementoLista(cn.getIdCirculo(), cn.getNombre() ));
                                                                        }
								}
							}
                      		
                      		if(circulosNotariales == null){
                      			circulosNotariales = new Vector();
                      		}
                   			
                   			docHelper.setTipos(listaCirculosNotariales);
		                    docHelper.setNombre(CMinuta.CIRCULO_NOTARIAL);
                   			docHelper.setCssClase("camposformtext");
                   			docHelper.setId(CMinuta.CIRCULO_NOTARIAL);
							docHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						%>           

					</td>

                </tr>
              </table>      
              
              <table width="100%" class="camposform">
                
                
<tr>
               
               
               
										
</td>
<td width="15%">
                   						
No folios.
</td>
<td width="30%">
<%
							try {
								textHelper.setNombre(CMinuta.NRO_FOLIOS_MINUTA);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(CMinuta.NRO_FOLIOS_MINUTA);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}                      
							%>    
</td>
<td width="35%">
</td>
<td width="15%">
</td>
<td width="35%">
 </td>
                </tr>
                
                <tr>
                  
                  <td width="15%">Observaciones</td>
                  <td  width="85%" align='left' colspan='3'>
                        <%
							try {
								textAreaHelper.setNombre(CMinuta.OBSERVACIONES_REPARTO);
								textAreaHelper.setId(CMinuta.OBSERVACIONES_REPARTO);
								textAreaHelper.setCols("95");							
								textAreaHelper.setRows("5");							
								textAreaHelper.setCssClase("camposformtext");
								textAreaHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}                      
						%>                       
                  </td>

                </tr>
                
              </table>
              
              
        
        	<!--ACTOS-->
			  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                 
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Acciones notariales</td>
                 
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <%//SE MUESTRA EL LISTADO DE LOS ACTOS AGREGADOS
				List actosCargados = (List)session.getAttribute(WebKeys.LISTA_ACTOS_MINUTA);
				long unidadesTotal = 0;
				double valortotal = 0;
				if(actosCargados!=null && actosCargados.size()>0){
                                    String idAccionNotarialSeleccionada = (String)session.getAttribute(CMinuta.ID_ACCION_NOTARIAL_SELECCIONADA);
                                    String cuantiaSeleccionada = (String)session.getAttribute(CMinuta.CUANTIA_SELECCIONADA);
                                    String unidadSeleccionada = (String)session.getAttribute(CMinuta.UNIDAD_SELECCIONADA);
				%>
					<table width="100%" class="camposform">
					<tr>
								<td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                                                <td class="titulotbcentral" width="10%">Cuantía y/o Unidades</td>
								<td class="titulotbcentral"> # </td>
								<td class="titulotbcentral"> Tipo Accion Notarial </td>
								<td class="titulotbcentral"> Cuant&iacute;a </td>
								<td class="titulotbcentral"> Unidades </td>
								<td class="titulotbcentral"> Con Cuant&iacute;a </td>
							</tr>
                                                        <input type="hidden" id="<%=CMinuta.CUANTIA_SELECCIONADA%>" name="<%=CMinuta.CUANTIA_SELECCIONADA%>" value="<%
                                                            if(cuantiaSeleccionada != null && cuantiaSeleccionada.trim().length() > 0){
                                                                out.print(cuantiaSeleccionada);
                                                            }else if(cuantiaSeleccionada == null || cuantiaSeleccionada.trim().isEmpty()){
                                                                out.print("");
                                                            }
                                                                %>"/>
                                                        <input type="hidden" id="<%=CMinuta.UNIDAD_SELECCIONADA%>" name="<%=CMinuta.UNIDAD_SELECCIONADA%>" value="<%
                                                            if(unidadSeleccionada != null && unidadSeleccionada.trim().length() > 0){
                                                                out.print(unidadSeleccionada);
                                                            }else if(unidadSeleccionada == null || unidadSeleccionada.trim().isEmpty()){
                                                                out.print("");
                                                            }
                                                                %>"/>
                                                        <input type="hidden" id="<%=CMinuta.ID_ACCION_NOTARIAL_SELECCIONADA%>" name="<%=CMinuta.ID_ACCION_NOTARIAL_SELECCIONADA%>" value="<%
                                                            if(idAccionNotarialSeleccionada != null && idAccionNotarialSeleccionada.trim().length() > 0){
                                                                out.print(idAccionNotarialSeleccionada);
                                                            }else if(idAccionNotarialSeleccionada == null || idAccionNotarialSeleccionada.trim().isEmpty()){
                                                                out.print("");
                                                            }
                                                        %>"/>
                                                        
				<%
					int a = 0;
					Iterator itActos = actosCargados.iterator();				
					while(itActos.hasNext()){
						//AccionNotarial accionNotarialLista = (AccionNotarial) itActos.next();
						MinutaAccionNotarial actoMinutaTmp = (MinutaAccionNotarial) itActos.next();
						AccionNotarial accionNotarialLista = actoMinutaTmp.getAccionNotarial();
						unidadesTotal = unidadesTotal + actoMinutaTmp.getUnidades();
						valortotal = valortotal + actoMinutaTmp.getValor();
						String valor = String.valueOf(NumberFormat.getInstance().format(actoMinutaTmp.getValor()));
						String unidades = String.valueOf(actoMinutaTmp.getUnidades());
						String conCuantia = String.valueOf(actoMinutaTmp.getConCuantia());
						String labelConCuantia = "SI";
						if (!conCuantia.equals("1")) {
							labelConCuantia = "NO";
						}
				%>		
					<tr>
						<td><img src="<%=request.getContextPath()%>/jsp/images/ico_acto.gif" width="16" height="21"></td>
                                                <td>
                                                    <%
                                                        if(idAccionNotarialSeleccionada != null && idAccionNotarialSeleccionada.equals(accionNotarialLista.getIdAccionNotarial())){
                                                    %>
                                                    <input type="radio" id="radioCuantia<%=a%>" name="radioCuantia" checked="checked" onclick="actualizarCuantia(this, '<%=""+valor%>', '<%=""+unidades%>', '<%=accionNotarialLista.getIdAccionNotarial()%>')" value="<%=accionNotarialLista.getIdAccionNotarial()%>" />
                                                    <%
                                                        }else{
                                                    %>
                                                    <input type="radio" id="radioCuantia<%=a%>" name="radioCuantia" onclick="actualizarCuantia(this, '<%=""+valor%>', '<%=""+unidades%>', '<%=accionNotarialLista.getIdAccionNotarial()%>')" />
                                                    <%
                                                        }
                                                    %>
                                                </td>
						<td class="campositem"><%=(a+1)%></td>
						<td class="campositem"><%=(accionNotarialLista!=null && accionNotarialLista.getNombre()!=null ? accionNotarialLista.getNombre(): "&nbsp;")%></td>
						<td class="campositem"><%=(valor!=null && valor!=null ? valor: "&nbsp;")%></td>
						<td class="campositem"><%=(unidades!=null && unidades!=null ? unidades: "&nbsp;")%></td>
						<td class="campositem"><%=labelConCuantia%></td>
		                <td width="20" align='left'>  
							<a href="javascript:eliminar('<%=""+a%>','<%=AWLiquidacionRepartoNotarial.ELIMINAR_ACCION_NOTARIAL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" alt="Eliminar accion notarial" width="35" height="13" border="0"></a>
		                </td>						
						</tr>
				<%  a++;
					}
				%>
					</table>
				<%

				}                                

                                DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                                simbolos.setDecimalSeparator('.');
                                NumberFormat numberFormat = new DecimalFormat("##0.0",simbolos);                                
                                
				session.setAttribute(CMinuta.CUANTIA,String.valueOf(numberFormat.format(valortotal)));
				session.setAttribute(CMinuta.UNIDADES,String.valueOf(unidadesTotal));
				%>
				
              	<table width="100%" class="camposform">
              		<tr>
              			<td width="10%"> Total Cuant&iacute;a</td>
                  		<td  width="15%" align='left'>
                        <%
						try {
							textHelper.setReadonly(true);
							textHelper.setNombre(CMinuta.CUANTIA);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(CMinuta.CUANTIA);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}                      
						%>                      
                  		</td>
                  
	                  <td width="25%" align='right'>Total Unidades Inmobiliarias</td>
	                  <td width="20%">
	                        <%
								try {
									textHelper.setReadonly(true);							
									textHelper.setNombre(CMinuta.UNIDADES);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CMinuta.UNIDADES);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}                      
							%>                      
	                  </td>                  
                	</tr>
              	</table>
              	
              <table width="100%" class="camposform">
              	<tr> 
              		<td width="10%" align='left'>Con Cuantía</td>
              		<td width="20%">
                  		<input type="checkbox" value="ACCION_NOTARIAL_CON_CUANTIA" name="<%= CMinuta.ACCION_NOTARIAL_CON_CUANTIA%>" onclick='elegirCuantia()' 
						<%= 
							session.getAttribute(CMinuta.ACCION_NOTARIAL_CON_CUANTIA).equals("ACCION_NOTARIAL_CON_CUANTIA") ? "CHECKED" : ""%>
						>
                  	</td>
              	</tr>
              	<tr> 
              
                  <td width="15%">Tipo de Contrato</td>
                  
                  
								<td width="30%"><% try {
                        		TextHelper textHelperIdAccionNotarial = new TextHelper();
                        		textHelperIdAccionNotarial.setNombre(CMinuta.ID_TIPO_ACCION_NOTARIAL);
                        		textHelperIdAccionNotarial.setCssClase("camposformtext");
                        		textHelperIdAccionNotarial.setFuncion("onchange=\"javascript:cambiarValorTipoAccionNotarial(this.value);\"");
                        		textHelperIdAccionNotarial.setId(CMinuta.ID_TIPO_ACCION_NOTARIAL);
                        		textHelperIdAccionNotarial.setEditable(true);
                        		textHelperIdAccionNotarial.setReadonly(false);
                        		textHelperIdAccionNotarial.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>

                  <td width="35%">
                      <% try {
	                    	//String stringTipoAccionNotarial = (String) session.getAttribute(CMinuta.TIPO_ACCION_NOTARIAL);	
	                    	
                      		List acionesNotariales = (List) session.getServletContext().getAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES);
                      		if(acionesNotariales == null){
                      			acionesNotariales = new Vector();
                      		}
                   				accionHelper.setTipos(acionesNotariales);
		                        accionHelper.setNombre(CMinuta.TIPO_ACCION_NOTARIAL);
                   			    accionHelper.setCssClase("camposformtext");
                   			    accionHelper.setId(CMinuta.TIPO_ACCION_NOTARIAL);                                            
	              			    //accionHelper.setFuncion("onchange=getElementById('ID_TIPO_ACCION_NOTARIAL').value=this.value;");
	              			    accionHelper.setFuncion("onchange='javascript:recargar()'");
								accionHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>           

					</td>
	            </tr>
	            
	            
              		<tr>
                  		<td width="15%">Cuant&iacute;a</td>
                  		<td  width="35%" align='left'>
                        <%
						try {
							textHelper.setNombre(CMinuta.CUANTIA_ACTO);
							textHelper.setCssClase("camposformtext");
							textHelper.setReadonly(false);									
							textHelper.setId(CMinuta.CUANTIA_ACTO);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}                      
						%>                      
                  		</td>
                  
	                  <td width="15%" align='right'>Unidades Inmobiliarias</td>
	                  <td width="35%">
	                        <%
								try {
									textHelper.setReadonly(false);							
									textHelper.setNombre(CMinuta.UNIDADES_ACTO);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CMinuta.UNIDADES_ACTO);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}                      
							%>                      
	                  </td>                  
                	</tr>
              	
                                
                
                <tr>
                	<td width="15%">&nbsp;</td>
                  	<td width="85%" align='left' colspan='3'>  
					<a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.repartonotarial.AWLiquidacionRepartoNotarial.AGREGAR_ACTO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a>
                  </td>
                </tr>
                
              </table>








              <!--ENTIDADES PUBLICAS.-->			
			  <br>	
			  <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                 
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Entidad p&uacute;blica relacionada</td>
                 
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>



				<%
				List entidadesCargadas = (List)session.getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
				
				if(entidadesCargadas!=null && entidadesCargadas.size()>0){
				%>
					<table width="100%" class="camposform">
				<%
					int a = 0;
					Iterator itEntidades = entidadesCargadas.iterator();				
					while(itEntidades.hasNext()){
						EntidadPublica entidadPublica = (EntidadPublica) itEntidades.next();
						boolean entidadexento = entidadPublica.isExento();
						String tentidadexento = "&nbsp;";
						if(entidadexento){
							tentidadexento = "EXENTO";
						}
				%>		
						<tr>
						
						<td class="titulotbcentral"><%=(entidadPublica!=null && entidadPublica.getNombre()!=null ? entidadPublica.getNombre(): "&nbsp;")%></td>
		                <td width="50" align='left'><%=tentidadexento%></td>
		                <td width="20" align='left'>  
							<a href="javascript:eliminar('<%=""+a%>','<%=AWLiquidacionRepartoNotarial.ELIMINAR_ENTIDAD_PUBLICA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" alt="Eliminar otorgante natural" width="35" height="13" border="0"></a>                  
		                </td>						
						</tr>
				<%  a++;
					}
				%>
					</table>
				<%
				} 
				%>


              <table width="100%" class="camposform">
                <tr>
                  
                  <td width="15%">Entidad pública:</td>
                  <td  width="45%" align='left'>
                        <%
                        	List entidades = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS_ACTIVAS);
							List entidadesLista = new Vector();
							ListaElementoHelper helper = new ListaElementoHelper();							
							
							if(entidades!=null){
								Iterator it = entidades.iterator();
								while(it.hasNext()){
									EntidadPublica entidadPublica = (EntidadPublica)it.next();
									ElementoLista el = new ElementoLista(entidadPublica.getIdEntidadPublica(),entidadPublica.getNombre());
									entidadesLista.add(el);
								}
								
								helper.setTipos(entidadesLista);							
							 
							 }
							
	                  		 try {
	                   			helper.setId(CMinuta.ENTIDAD_PUBLICA);
	                   			helper.setNombre(CMinuta.ENTIDAD_PUBLICA);
	                   			helper.setCssClase("camposformtext");
	                  			helper.render(request,out);
							 }
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							 }
						%>
                  
                  </td>
				  <td  width="20%" align='left'>
				      <input name='<%=CMinuta.ENTIDAD_PUBLICA_EXENTO%>' id="<%=CMinuta.ENTIDAD_PUBLICA_EXENTO%>" type="radio" value='<%=CMinuta.EXENTO%>' false> Exento 
				  </td>
                  <td width="40%" align='left'>  
					<a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.repartonotarial.AWLiquidacionRepartoNotarial.AGREGAR_ENTIDAD_PUBLICA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a>
                  </td>		
                
              </table>







     		  <!--OTORGANTES-->
              <br>
              <br>
        
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Persona Natural ó Jur&iacute;dica</td>
                 
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>



				<%
				List otorgantes = (List)session.getAttribute(WebKeys.LISTA_OTORGANTES);
				
				if(otorgantes!=null && otorgantes.size()>0){
				%>
					<table width="100%" class="camposform">
				<%
					int a = 0;
					Iterator itOtorgantes = otorgantes.iterator();				
					while(itOtorgantes.hasNext()){
						OtorganteNatural otorgante = (OtorganteNatural) itOtorgantes.next();
						boolean exento = otorgante.isExento();
						String texento = "&nbsp;";
						if(exento){
							texento = "EXENTO";
						}
				%>		
						<tr>
						
						<td class="titulotbcentral"><%=(otorgante!=null && otorgante.getNombre()!=null ? otorgante.getNombre() : "&nbsp;")%></td>
		                <td width="50" align='left'><%=texento%></td>
		                <td width="20" align='left'>  
							<a href="javascript:eliminar('<%=""+a%>','<%=AWLiquidacionRepartoNotarial.ELIMINAR_OTORGANTE_NATURAL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" alt="Eliminar otorgante natural" width="35" height="13" border="0"></a>                  
		                </td>						
						</tr>
				<%  a++;
					}
				%>
					</table>
				<%
				} 
				%>
       
       

              <table width="100%" class="camposform">
                <tr>
                  
                  <td width="15%">Otorgante natural:</td>
                  <td  width="45%" align='left'>
                        <%
							try {
								textHelper.setReadonly(false);							
								textHelper.setNombre(CMinuta.OTORGANTE_NATURAL);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(CMinuta.OTORGANTE_NATURAL);
								textHelper.setSize("60");								
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}                      
						%>                       
                  </td>
				  <td  width="20%" align='left'>
				      <input name='<%=CMinuta.OTORGANTE_NATURAL_EXENTO%>' id="<%=CMinuta.OTORGANTE_NATURAL_EXENTO%>" type="radio" value='<%=CMinuta.EXENTO%>' false> Exento 
				  </td>
                  <td width="20%" align='left'>  
					<a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.repartonotarial.AWLiquidacionRepartoNotarial.AGREGAR_OTORGANTE_NATURAL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a>
                  </td>		
                 
              </table>
              <br>	
              <hr class="linehorizontal">
              <table width="30%" class="camposform">
              <tr> 
              		<td width="30%" align='left'>Imprimir Constancia de Radicación</td>
              		<td width="5%">
                  		<input type="checkbox" value="IMPRIMIR_CONSTANCIA" name="<%= CMinuta.IMPRIMIR_CONSTANCIA%>" 
						<%= 
							session.getAttribute(CMinuta.IMPRIMIR_CONSTANCIA).equals("IMPRIMIR_CONSTANCIA") ? "CHECKED" : ""%>
						>
                  	</td>
              	</tr>
              </table>
	 <%  
		 try
		 { 
		   //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		   //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		   //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		   helperNotas.setNombreFormulario("REPARTONOTARIAL");
		   helperNotas.render(request,out);
		 }
		 catch(HelperException re)
		 {
			 out.println("ERROR " + re.getMessage());
		 }	
	%>       
            
                            
              

			  <br>	
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                
                <tr> 
                  
                  <td> 
					<a href="javascript:cambiarAccion('<%=AWLiquidacionRepartoNotarial.VALIDAR_INFORMACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></a>
                  </td>
                </tr>
              </table>
            </form>
            
          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
	
	</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
 <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
 
</table>

<script>
<%
if(accionNotarial==null){
%>
	conCuantia();
<%
}else{
	if(!accionNotarial.equals(WebKeys.SIN_SELECCIONAR)){
		List accionesNotariales = (List) session.getServletContext().getAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES);	
		Iterator it = accionesNotariales.iterator();
		
		while(it.hasNext()){
			AccionNotarial an = (AccionNotarial)it.next();
			if(an.getIdAccionNotarial().equals(accionNotarial)){
				//sinCuantia = an.isCuantia();				
			}
		}
	}
	if(sinCuantia){
%>
		sinCuantia();
<%	
	}else{
%>
		conCuantia();
<%	
	}
}%>
</script>
<%

if(recargaPopUp){
	recargaPopUp = false;
	session.removeAttribute(CImpresion.ADMINISTRADOR_IMPRESION_ACTIVO);
	session.removeAttribute(WebKeys.RECARGA);%>
	<script>mostrarCategoria('turnoLiquidacionRepartoNotarial.do?<%= WebKeys.ACCION %>=<%=AWLiquidacionRepartoNotarial.OBTENER_CATEGORIA%>','Categoria', 'width=460,height=360');</script>
<%}%>

