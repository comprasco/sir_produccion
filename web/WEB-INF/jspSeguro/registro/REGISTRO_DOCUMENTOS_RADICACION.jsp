<%@page import="org.auriga.core.web.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.core.negocio.modelo.SubtipoSolicitud"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaCheckboxHelper"%>
<%@page import="java.util.List,java.util.Vector"%>
<%@page import="gov.sir.core.negocio.modelo.CheckItem"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.web.acciones.registro.AWLiquidacionRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page	import="java.util.Calendar"%>
<%@page	import="gov.sir.core.negocio.modelo.Proceso"%>

<% 
	ListaCheckboxHelper docHelper = new ListaCheckboxHelper();
	TextHelper textHelper= new TextHelper();
	
	
	//se inician los atributos necesarios para el JSP
	
	SolicitudRegistro sol= (SolicitudRegistro) request.getSession().getAttribute(WebKeys.SOLICITUD);
	List docs= new Vector();
	if(sol!=null){
		SubtipoSolicitud subsol=sol.getSubtipoSolicitud();
		if(subsol!=null){
			
			List lchequeo= subsol.getCheckItems();
			Iterator ichequeo= lchequeo.iterator();
			for(;ichequeo.hasNext();){
				CheckItem temp= (CheckItem) ichequeo.next();
				docs.add(new ElementoLista(temp.getIdCheckItem(), temp.getNombre()));
			}
	    	
	    }
  	}
  	request.getSession().setAttribute("LISTA_SUBTIPO_CHEQUEO", docs);
  	
	String idCirculo = "";
   	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
		idCirculo = idCirculo + "-";
  	}
				
	//inicializando el # del turno anterior
	String turnoAnterior=(String)session.getAttribute("TURNO_ANTERIOR");
	if(turnoAnterior==null){
		turnoAnterior="";
	}
	
	/*
	//DUMMIE Creation Center:D
	//creacion de la solicitud
	SolicitudRegistro sol= new SolicitudRegistro();
	sol.setIdSolicitud("1");
	
	//creacion subtipos de solicitud
	SubtipoSolicitud subSol1= new SubtipoSolicitud();
	subSol1.setIdSubtipoSol("1");
	subSol1.setNombre("SUBTIPO 1");
	
	SubtipoSolicitud subSol2= new SubtipoSolicitud();
	subSol2.setIdSubtipoSol("2");
	subSol2.setNombre("SUBTIPO 2");
	
	SubtipoSolicitud subSol3= new SubtipoSolicitud();
	subSol3.setIdSubtipoSol("3");
	subSol3.setNombre("SUBTIPO 3");
	
	//creacion de checkitems 
	CheckItem check= new CheckItem();
	check.setIdCheckItem("1");
	check.setIdSubtipoSol("1");
	check.setNombre("DOC 1");
	subSol1.addCheckItem(check);
	subSol2.addCheckItem(check);
	
	check= new CheckItem();
	check.setIdCheckItem("1");
	check.setIdSubtipoSol("1");
	check.setNombre("DOC 2");
	subSol1.addCheckItem(check);
	subSol3.addCheckItem(check);
	
	
	
	check= new CheckItem();;
	check.setIdCheckItem("1");
	check.setIdSubtipoSol("1");
	check.setNombre("DOC 2");
	subSol1.addCheckItem(check);
	subSol2.addCheckItem(check);
	
	//creacion lista
	List docs= new Vector();
	List lchequeo= subSol1.getCheckItems();
	Iterator ichequeo= lchequeo.iterator();
	for(;ichequeo.hasNext();){
		CheckItem temp= (CheckItem) ichequeo.next();
		docs.add(new ElementoLista(temp.getIdCheckItem(), temp.getNombre()));
	}
    request.getSession().setAttribute("LISTA_SUBTIPO_CHEQUEO", docs);*/
	
	
	
%>
<!--entra-->	
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script>
	   
function cambiarAccion(text,anio,circulo,proceso,sec) {
	   document.REGISTRO.<%=AWLiquidacionRegistro.ID_SOLICITUD%>.value=anio+"-"+circulo+"-"+proceso+"-"+
	   		document.REGISTRO.AUX.value;
       document.REGISTRO.ACCION.value = text;
       document.REGISTRO.submit();
}

function cambiarAccionInc(text) {
       document.INC.ACCION.value = text;
       document.INC.submit();
}

function cambiarAccionReg(text,anio,circulo,proceso,sec) {
	   document.getElementById('id_btn_aceptar').width=0; 
	   document.REGISTRO.<%=AWLiquidacionRegistro.ID_SOLICITUD%>.value=anio+"-"+circulo+"-"+proceso+"-"+
	   document.REGISTRO.AUX.value;
       document.REGISTRO.ACCION.value = text;
       document.REGISTRO.submit();
}

</script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
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
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">
 
          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Radicaci&oacute;n</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
                    </tr>
                  </table></td>
                    <!--AHERRENO 03/06/2012
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
            <!----aqui se coloca la form---->
            <form action="turnoLiquidacionRegistro.do" method="post" name="REGISTRO" id="REGISTRO">
            <input type="hidden" name="ACCION" id="ACCION" value="<%=AWLiquidacionRegistro.VALIDAR_SOLICITUD%>">
            
            <!----Inicio Seccion Nro. solicitud ---->
             <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="12%">N&uacute;mero Solicitud </td>
                  <td width="60%">
                  
	            <%Calendar cal=Calendar.getInstance();
	            int year=cal.get(Calendar.YEAR);
	            String sYear=String.valueOf(year);
	            
	            Circulo circulo=(Circulo)session.getAttribute(WebKeys.CIRCULO);
	            Proceso proceso=(Proceso)session.getAttribute(WebKeys.PROCESO);
	            
	            String sCirculo=circulo.getIdCirculo();
	            long lProceso=proceso.getIdProceso();
	            String sProceso=String.valueOf(lProceso);
	            %>
	
				L-<%=sYear%>-<%=sCirculo%>-<%=sProceso%>-
                  
                  
                  <%
                  	try {
     						textHelper.setNombre("AUX");
 		                    textHelper.setCssClase("camposformtext");
                  			textHelper.setId("AUX");
                  			textHelper.setTipo("text");
                  			textHelper.setSize("15");
							textHelper.render(request,out);

     						textHelper.setNombre(AWLiquidacionRegistro.ID_SOLICITUD);
 		                    textHelper.setCssClase("camposformtext");
                  			textHelper.setId(AWLiquidacionRegistro.ID_SOLICITUD);
                  			textHelper.setTipo("hidden");
							textHelper.render(request,out);
				  		}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
                  %>
                  </td>
                  <td width="50%">&nbsp;</td>
                  <td width="15%">
                  
                  <a href="javascript:cambiarAccion('<%=AWLiquidacionRegistro.VALIDAR_SOLICITUD%>','<%=sYear%>','<%=sCirculo%>','<%=sProceso%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_validar.gif" width="139" height="21" border="0"></a></td>  
                </tr>
             </table>

			<%if(sol==null) {%>
            </form>
            <%}%>
              
            
            <!----Fin Seccion Nro. solicitud ---->
            <br>
            <br>
            <br>
            <%if(sol!=null){%>
            <!----Inicio Seccion de la lista de documentos ---->
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Lista de Documentos Entregados </td>
                  <td width="16" class="bgnsub"></td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_checkbox.gif" width="20" height="15"></td>
                  <td>Documentos Entregados </td>
                </tr>
                
                
                <% try {
	 		                    List tipos = (List) session.getAttribute("LISTA_SUBTIPO_CHEQUEO") ;
                   				if (tipos==null){
                   					tipos = new Vector();
                   				}
                   				docHelper.setTipos(tipos);
                  			    docHelper.setCssClase("campositem");
                  			    docHelper.setId("DOCUMENTOS_ENTREGADOS");
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                
                
              </table>
              <br>
              
              <!----Fin Seccion de la lista de documentos ---->
              
              <!----Inicio Seccion de botones ---->
              
              <table width="100%" class="camposform">
                <tr>
                 
                  <td width="140">
                  
                  <a href="javascript:cambiarAccionReg('<%= AWLiquidacionRegistro.CREAR_TURNO%>','<%=sYear%>','<%=sCirculo%>','<%=sProceso%>','siguiente')"><img src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" id="id_btn_aceptar" width="139" height="21" border="0"></a></td>
                  
                  <td>&nbsp;</td>
                </tr>
              </table>
            </form>
             <!----Fin Seccion de botones ---->
              
              <!----Inicio Seccion serial de recibo ---->
            <form action="turnoLiquidacionRegistro.do" method="post" name="INC" id="INC">
            <input type="hidden" name="ACCION" id="ACCION" value="<%=AWLiquidacionRegistro.VALIDAR_SOLICITUD%>">
              <br>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
	              <tr> 
	               
	                <td class="bgnsub">Secuencial de Recibos</td>
	                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
	                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt="" ></td>
	              </tr>
              </table>
          
	          <table width="100%">
			  	<tr>
					<td>
				    <input type="hidden" name="ITEM" value="NINGUNO">
					<table width="100%" class="camposform"><tr></tr>
			        	<tr>
			          		<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			          		<td>Valor Actual del Secuencial</td>
			          			 <a name="#verAnotaciones"></a> 
			           			<% String valorSecuencial = (String)session.getAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION);
			           			   if(valorSecuencial==null){
			           			   	valorSecuencial="&nbsp;";
			           			   }
			           			%>
			          		<td><input name="ID_MATRICULA0" id="ID_MATRICULA0" type="text" value="<%=valorSecuencial%>"  onFocus="campoactual('ID_MATRICULA');" class="camposformtext">
			          
			          		<img id="ID_MATRICULA_img" src="<%= request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo"></td>
			         	</tr>
			        </table>
				   	</td>
				</tr>
			    <tr>
					<td>	    
						<table width="100%" class="camposform"><tr></tr>
			         		<tr>
			          			<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			          			<td>Motivo Incremento del Secuencial</td>
			          			<% 
			          			
			          			String valorSecuencial2 = (String)session.getAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION);%>
			          			<td><input name="MOTIVO_INCREMENTO_SECUENCIAL" id="MOTIVO_INCREMENTO_SECUENCIAL" type="text" value=""  onFocus="campoactual('MOTIVO_INCREMENTO_SECUENCIAL');" class="camposformtext" size='120'>
			          
			          			<img id="ID_MATRICULA_img" src="<%= request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo"></td>
			         		</tr>
			        	</table>
					</td>
			    </tr>
			      
				<tr>
			    	<td align="right">
				    	<table border="0" cellpadding="0" cellspacing="2" class="camposform">
					    	<tr>
					        	
					          	<td>Incrementar Secuencial</td>
					          	<td><a href="javascript:cambiarAccionInc('INCREMENTAR_SECUENCIAL_RECIBO')"><img src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
					      	</tr>
					    </table>
					</td>
			    </tr>
		      </table>	
		       
		      </form>
              <!----Fin Seccion serial de recibo ---->
              
              
             
             <%}%>
          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
<%
        //Helper de Notas Informativas        
		try{
			gov.sir.core.web.helpers.comun.NotasInformativasHelper helper = new gov.sir.core.web.helpers.comun.NotasInformativasHelper();		
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%>
      
      </td>
    <td>&nbsp;</td>
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
