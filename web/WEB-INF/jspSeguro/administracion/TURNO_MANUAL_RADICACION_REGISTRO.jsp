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
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Registro de Documentos </td>
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
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr> 
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"> 
            <!----aqui se coloca la form---->
            <form action="turnoManualRegistro.do" method="post" name="REGISTRO" id="REGISTRO">
            <input type="hidden" name="ACCION" id="ACCION" value="<%=AWLiquidacionRegistro.VALIDAR_SOLICITUD%>">
            <input type="hidden" name="AUX" id="AUX" value="">
            <input type="hidden" name="AUX" id="AUX" value="">

            
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
                  

            
            <!----Fin Seccion Nro. solicitud ---->
            <br>
            <hr class="linehorizontal">
            <%if(sol!=null){%>
            <!----Inicio Seccion de la lista de documentos ---->
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Lista de Documentos Entregados </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
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
              <hr class="linehorizontal">
              <!----Fin Seccion de la lista de documentos ---->
              
              <!----Inicio Seccion de botones ---->
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140">
                  
                  <input name="siguiente" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" onClick="javascript:cambiarAccion('<%= AWLiquidacionRegistro.CREAR_TURNO%>','<%=sYear%>','<%=sCirculo%>','<%=sProceso%>','siguiente')" width="139" height="21" border="0"></td>
                  
                  <td>&nbsp;</td>
                </tr>
              </table>
            </form>
             <!----Fin Seccion de botones ---->
              
            
              <br>
              <hr class="linehorizontal">
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
