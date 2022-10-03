<%@page import="java.util.*"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.Minuta" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.OtorganteNatural"%>
<%@page import="gov.sir.core.negocio.modelo.MinutaEntidadPublica"%>
<%@page import="gov.sir.core.negocio.modelo.MinutaAccionNotarial"%>
<%@page import="gov.sir.core.web.acciones.repartonotarial.AWRepartoNotarial"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="java.text.NumberFormat"%>

<%
	//OBTENER LA MINUTA QUE SE QUIERE MOSTRAR
    Minuta minuta = (Minuta)session.getAttribute(WebKeys.MINUTA);
	String idTurno = request.getParameter(CTurno.ID_TURNO);
	if (idTurno == null) {
	  idTurno = (String) session.getAttribute(CTurno.ID_TURNO);
	}
	//HELPERS
    TextHelper textHelper = new TextHelper();     
	textHelper.setReadonly(true);
    TextAreaHelper textAreaHelper = new TextAreaHelper();        

	//CODIGO PARA MOSTRAR LA INFORMACIÓN DE LA MINUTA
	String tipo = new String();
	String categoria = new String();
	
	//SI LA MINUTA ES NO NULA SE SUBEN LOS ATRIBUTOS A LA SESIÓN
	if(minuta != null){	
		
		//TIPO DE MINUTA
		if(minuta.isNormal()){
			tipo = CMinuta.ORDINARIO;
		}else{
			tipo = CMinuta.EXTRAORDINARIO;
		}

		//CAMPO ACCION NOTARIAL
		String accionNotarial = "";
		/*if(minuta.getAccionNotarial()!=null && minuta.getAccionNotarial().getNombre()!=null){
			accionNotarial = minuta.getAccionNotarial().getNombre();		
		}*/

		//FECHA DE CREACIÓN	
		
		Date fechaCreacion = null;
		if(minuta.getSolicitud()!=null
		   &&minuta.getSolicitud().getTurno()!=null
		   &&minuta.getSolicitud().getTurno().getFechaInicio()!=null){
			fechaCreacion =	minuta.getSolicitud().getTurno().getFechaInicio();		   
		   }

		String fechaCrea="";
		if(fechaCreacion!=null){
			fechaCrea = DateFormatUtil.format(fechaCreacion);
		}

		//FECHA DE REPARTO
		Date rep = null;
		if(minuta.getRepartoNotarial() !=null && minuta.getRepartoNotarial().getFechaCreacion()!=null){
			rep=minuta.getRepartoNotarial().getFechaCreacion();		
		}

		String fechaRep ="";
		if(rep!=null){
			fechaRep = DateFormatUtil.format(rep);		
		}

		//NÚMERO DE REPARTO
		String numRep = "";
		if(minuta.getRepartoNotarial()!=null &&minuta.getRepartoNotarial().getIdRepartoNotarial()!=null){
			numRep=minuta.getRepartoNotarial().getIdRepartoNotarial();
		}

		//SE SUBEN DATOS A LA SESIÓN.
		session.setAttribute(CMinuta.FECHA_CREACION, fechaCrea);
		session.setAttribute(CMinuta.FECHA_REPARTO, fechaRep);
		session.setAttribute(CMinuta.NUMERO_REPARTO, numRep);
		//session.setAttribute(CMinuta.TIPO_ACCION_NOTARIAL,accionNotarial);
		session.setAttribute(CMinuta.ID_MINUTA,minuta.getIdMinuta());
		session.setAttribute(CMinuta.TURNO_ID_WORKFLOW,idTurno);
		String numFolios = String.valueOf(minuta.getNumeroFolios());
		session.setAttribute(CMinuta.NRO_FOLIOS_MINUTA,numFolios);
		
		//CATEGORIA ASIGNADA
		if(minuta.getCategoria()!=null && minuta.getCategoria().getNombre()!=null){
			categoria = minuta.getCategoria().getNombre();
		}
		
		session.setAttribute(CMinuta.CATEGORIA_NOM,categoria);		
		
		//CIRCULO NOTARIAL
		String circuloNotarial = null;
		if(minuta.getCirculoNotarial() !=null &&minuta.getCirculoNotarial().getNombre()!=null){
			circuloNotarial = minuta.getCirculoNotarial().getNombre();
		}
		session.setAttribute(CMinuta.CIRCULO_NOTARIAL_NOM,circuloNotarial);
		
		String notaria = "";
		if(minuta.getOficinaCategoriaAsignada()!=null  
		   && minuta.getOficinaCategoriaAsignada().getOficinaOrigen()!=null
		   && minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getNombre()!=null){
			notaria = minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getNombre();
		}
		
		session.setAttribute(CMinuta.NOTARIA,notaria);	
		String cuantia = String.valueOf(NumberFormat.getInstance().format(minuta.getValor()));
		session.setAttribute(CMinuta.CUANTIA,cuantia);
		String unidades = String.valueOf(minuta.getUnidades());
		session.setAttribute(CMinuta.UNIDADES,unidades);
		String obsReparto = minuta.getComentario();
		session.setAttribute(CMinuta.OBSERVACIONES_REPARTO,obsReparto);	
		session.setAttribute(WebKeys.LISTA_OTORGANTES,minuta.getOtorgantesNaturales());
		session.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS, minuta.getEntidadesPublicas());
		session.setAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES, minuta.getAccionesNotariales());
		
		String estado = "" + minuta.getEstado();
		session.setAttribute(CMinuta.ESTADO,estado);	
		
	}
	
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript"
	src="/SIR/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
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
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
    <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Consulta de Minutas por Número de Radicación</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Consulta de Minutas por Número de Radicación</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Informaci&oacute;n Radicaci&oacute;n</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
        <form action="consultasReparto.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWConsultasReparto.MINUTA_RADICACION%>">
           <table width="100%" class="camposform">
           		<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td allign="left" width="150">N&uacute;mero de Radicaci&oacute;n:</td>
                    <td width="40">&nbsp;
                    <% try {
                    textHelper.setReadonly(false);
                    textHelper.setNombre(CTurno.ID_TURNO);
                    textHelper.setSize("30");                    
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CTurno.ID_TURNO);
					textHelper.render(request,out);
					textHelper.setReadonly(true);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
				  </td>
				  <td allign="left">
				  </td>
                  </tr>
                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWConsultasReparto.MINUTA_RADICACION%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0">
                    </td>
                   	 <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWConsultasReparto.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                  </tr>
                </table>
            </FORM>
                
                
                
                
            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
		<tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
		</table>
	    <br>
	    
	    <%if(minuta!=null){%>
	    

    <table border="0" cellpadding="0" cellspacing="0" width="100%">
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
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
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

				<input name="id_depto" type="hidden" id="id_depto" value="">
				<input name="nom_depto" type="hidden" id="nom_depto" value="">
				<input name="id_munic" type="hidden" id="id_munic" value="">
				<input name="nom_munic" type="hidden" id="nom_munic" value="">
				<input name="id_vereda" type="hidden" id="id_vereda" value="">
				<input name="nom_vereda" type="hidden" id="nom_vereda" value="">            
				<input name="mostrar_vereda" type="hidden" id="mostrar_vereda" value="">        				
				
              <br>
              
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos 
                    Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td align='left'><strong>Tipo de radicación: <%=tipo%></strong></td>
                  <td align='left'><strong>Categor&iacute;a: <%=categoria%></strong></td>                  
                  <td align='left'>
                  </td>
                </tr>
              </table>              

              <br>
              <hr class="linehorizontal">
              <br>              
              
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">N&uacute;mero de Radicación</td>
                  <td width="35%">&nbsp;
                    <%
					try {
						textHelper.setNombre(CMinuta.TURNO_ID_WORKFLOW);
						textHelper.setCssClase("campositem");
						textHelper.setId(CMinuta.TURNO_ID_WORKFLOW);
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}                      
					%>
                  </td>
                  <td width="15%" align='right'>Fecha de Creaci&oacute;n</td>
                  <td width="35%">&nbsp;
                    <%
					try {
						textHelper.setNombre(CMinuta.FECHA_CREACION);
						textHelper.setCssClase("campositem");
						textHelper.setId(CMinuta.FECHA_CREACION);
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
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Fecha de Reparto</td>
                  <td width="35%">&nbsp;
                    <%
					try {
						textHelper.setNombre(CMinuta.FECHA_REPARTO);
						textHelper.setCssClase("campositem");
						textHelper.setId(CMinuta.FECHA_REPARTO);
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}                      
					%>
                  </td>
                  <td width="15%" align='right'>N&uacute;mero de Reparto</td>
                  <td width="35%">&nbsp;
                    <%
					try {
						textHelper.setNombre(CMinuta.NUMERO_REPARTO);
						textHelper.setCssClase("campositem");
						textHelper.setId(CMinuta.NUMERO_REPARTO);
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
				<td align="left" width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td align="left" width="15%">Circulo Notarial: </td>
                <td align="left" width="35%">&nbsp;
                <%
				try {
					textHelper.setNombre(CMinuta.CIRCULO_NOTARIAL_NOM);
					textHelper.setCssClase("campositem");
					textHelper.setId(CMinuta.CIRCULO_NOTARIAL_NOM);
					textHelper.render(request,out);
				}
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}                      
				%>                       
                </td>
                
                  <td width="15%" align='right'>Notar&iacute;a.</td>
                  <td width="35%">&nbsp;
                    <%
					try {
						textHelper.setNombre(CMinuta.NOTARIA);
						textHelper.setCssClase("campositem");
						textHelper.setId(CMinuta.NOTARIA);
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
				<td align="left" width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td align="left" width="15%">Estado Minuta: </td>
                <td align="left" width="35%">&nbsp;
                <%
				try {
					textHelper.setNombre(CMinuta.ESTADO);
					textHelper.setCssClase("campositem");
					textHelper.setId(CMinuta.ESTADO);
					textHelper.render(request,out);
				}
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}                      
				%>                       
                </td>
                <td width="15%" align='right'>No folios.</td>
                  <td width="35%">&nbsp;
                    <%
					try {
						textHelper.setNombre(CMinuta.NRO_FOLIOS_MINUTA);
						textHelper.setCssClase("campositem");
						textHelper.setId(CMinuta.NRO_FOLIOS_MINUTA);
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}                      
					%>                  
                  </td>
                </tr> 
				</table>        


			
				<!--Tabla de unidades-->
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Valor o Cuant&iacute;a</td>
                  <td  width="35%" align='left'>&nbsp;
                    <%
					try {
						textHelper.setNombre(CMinuta.CUANTIA);
						textHelper.setCssClase("campositem");
						textHelper.setId(CMinuta.CUANTIA);
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}                      
					%>  
                  </td>
                  
                  <td width="15%" align='right'>Unidades Inmobiliarias</td>
                  <td width="35%">&nbsp;
                    <%
						try {
							textHelper.setNombre(CMinuta.UNIDADES);
							textHelper.setCssClase("campositem");
							textHelper.setId(CMinuta.UNIDADES);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}                      
					%>                  
                  </td>                  

                </tr>
                
                
                
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Observaciones</td>
                  <td  width="85%" align='left' colspan='3'>&nbsp;
                        <%
							try {
								textAreaHelper.setNombre(CMinuta.OBSERVACIONES_REPARTO);
								textAreaHelper.setId(CMinuta.OBSERVACIONES_REPARTO);
								textAreaHelper.setCols("95");							
								textAreaHelper.setReadOnly(true);
								textAreaHelper.setRows("5");							
								textAreaHelper.setCssClase("campositem");
								textAreaHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}                      
						%>                       
                  </td>
                </tr>                
                
              </table>


			  <!--ACCIONES NOTARIALES.-->			
			  <br>	
			  <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Acci&oacute;n notarial relacionada</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

			  <%
				List accionesCargadas = (List)session.getAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES);
				
				if(accionesCargadas!=null && accionesCargadas.size()>0){
				%>
					<table width="100%" class="camposform">
				<%
					int a = 0;
					Iterator itAcciones = accionesCargadas.iterator();				
					while(itAcciones.hasNext()){
						MinutaAccionNotarial accionNotarial = (MinutaAccionNotarial) itAcciones.next();
				%>		
						<tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td class="titulotbcentral"><%=(accionNotarial!=null && accionNotarial.getAccionNotarial().getNombre()!=null ? accionNotarial.getAccionNotarial().getNombre(): "&nbsp;")%></td>
						</tr>
				<% }
				%>
					</table>
				<%
				} 
				%>





              <!--ENTIDADES PUBLICAS.-->			
			  <br>	
			  <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Entidad p&uacute;blica relacionada</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
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
						MinutaEntidadPublica entidadPublica = (MinutaEntidadPublica) itEntidades.next();
				%>		
						<tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td class="titulotbcentral"><%=(entidadPublica!=null && entidadPublica.getEntidadPublica().getNombre()!=null ? entidadPublica.getEntidadPublica().getNombre(): "&nbsp;")%></td>
						</tr>
				<% }
				%>
					</table>
				<%
				} 
				%>


              


     		  <!--OTORGANTES-->
              <br>
              <br>
        
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Persona natural o jurídica</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
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
				%>		
						<tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td class="titulotbcentral"><%=(otorgante!=null && otorgante.getNombre()!=null ? otorgante.getNombre(): "&nbsp;")%></td>
						</tr>
				<%}
				%>
					</table>
				<%
				} 
				%>
				
				<hr class="linehorizontal">
            
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                   <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                   <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Relación de la Minuta con turnos de Restitución</td>
                   <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                   <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                   <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                 </tr>
               </table>
            
      
            
               
               <%List listaAsociados = (List) session.getAttribute(AWRepartoNotarial.LISTADO_TURNOS_RESTITUCION_MINUTA);
               if (listaAsociados != null && listaAsociados.size() >0)
               {
                  
                  for (int i=0; i<listaAsociados.size(); i++)
                  {
                  
                  Turno turnoRestitucion = (Turno) listaAsociados.get(i);
                  String idTurnoRestitucion = turnoRestitucion.getIdWorkflow();
                  session.setAttribute("TURNO_RESTITUCION"+i,idTurnoRestitucion);
                  
                  List listaHistorials = turnoRestitucion.getHistorials();
                  if (listaHistorials != null)
                  {
                      for (int k= 0; k<listaHistorials.size(); k++)
                      {
                         TurnoHistoria th = (TurnoHistoria) listaHistorials.get(k);
                         if (th != null)
                         {
                           session.setAttribute("ESTADO_RESTITUCION",th.getNombreFase());
                         }
                      }
                    
                  }
                  
                  //Resultado del Análisis
                  SolicitudRestitucionReparto solRestitucion = (SolicitudRestitucionReparto) turnoRestitucion.getSolicitud();
                  if (solRestitucion != null)
                  {
                     CausalRestitucion causal = solRestitucion.getCausalRestitucion();
                     if (causal != null)
                     {
                        session.setAttribute("CAUSAL_RESTITUCION",causal.getDescripcion());
                      }
                      
                     String resolucion = solRestitucion.getResolucion();
                     if (resolucion == null || resolucion.length()==0)
                     resolucion ="   ";
                     session.setAttribute("RESOLUCION",resolucion);
                   }
                  
                  
                  %>
                    
                    <table width="100%" class="camposform">
                    <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="15%">Turno de Restitución</td>
                    <td  width="35%" align='left'>
                    <%
					try {
						textHelper.setNombre("TURNO_RESTITUCION"+i);
						textHelper.setCssClase("campositem");
						textHelper.setId("TURNO_RESTITUCION"+i);
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}                      
					%>  
                  </td>
                  
                  <td width="15%" align='right'>Estado Turno Restitución</td>
                  <td width="35%">
                    <%
                    
                    try {
							textHelper.setSize("30");
							textHelper.setNombre("ESTADO_RESTITUCION");
							textHelper.setCssClase("campositem");
							textHelper.setId("ESTADO_RESTITUCION");
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}                      
					%>                  
                  </td>                  

                </tr>
                
                <tr>
                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="15%">Causal de Restitución</td>
                    <td  width="35%" align='left' colspan="3">
                    <%
					try {
                        textHelper.setSize("110");
						textHelper.setNombre("CAUSAL_RESTITUCION");
						textHelper.setCssClase("campositem");
						textHelper.setId("CAUSAL_RESTITUCION");
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}                      
					%>  
                  </td>
                 </tr>
                
                
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Resultado Análisis</td>
                  <td  width="85%" align='left' colspan='3'>
                        <%
							try {
								textAreaHelper.setNombre("RESOLUCION");
								textAreaHelper.setId("RESOLUCION");
								textAreaHelper.setCols("95");							
								textAreaHelper.setReadOnly(true);
								textAreaHelper.setRows("5");							
								textAreaHelper.setCssClase("campositem");
								textAreaHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}                      
						%>                       
                  </td>

                </tr>                
                
              </table>
                    
                    
                    
               <%   }
                  
                } 
               %>


			  <br>	
              <hr class="linehorizontal">
          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
	<%}%>
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
        

