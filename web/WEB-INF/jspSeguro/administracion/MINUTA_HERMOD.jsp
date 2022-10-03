<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.OtorganteNatural"%>
<%@page import="gov.sir.core.negocio.modelo.Minuta"%>
<%@page import="gov.sir.core.negocio.modelo.MinutaEntidadPublica"%>
<%@page import="gov.sir.core.negocio.modelo.EntidadPublica"%>
<%@page import="gov.sir.core.negocio.modelo.CirculoNotarial"%>
<%@page import="gov.sir.core.negocio.modelo.MinutaAccionNotarial"%>
<%@page import="gov.sir.core.negocio.modelo.AccionNotarial"%>
<%@page import="java.text.DecimalFormat"%>



<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<%

	DecimalFormat df = new DecimalFormat("################.00");
	TextAreaHelper nota = new TextAreaHelper();
    ListaAccionesNotarialesHelper accionHelper = new ListaAccionesNotarialesHelper();
    ListaElementoHelper circuloNotHelper = new ListaElementoHelper();       
    TextHelper textHelper = new TextHelper();    
	TextHelper hiddenHelper = new TextHelper();
	hiddenHelper.setTipo("hidden");
    RadioHelper radioHelper = new RadioHelper();   	
    TextAreaHelper textAreaHelper = new TextAreaHelper();        
  	String tipo = new String();
  	String categoria = new String();
   	Minuta minuta = (Minuta)session.getAttribute(WebKeys.MINUTA);
   	
   		if(minuta != null){	
		if(minuta.isNormal()){
			tipo = CMinuta.ORDINARIO;
		}else{
			tipo = CMinuta.EXTRAORDINARIO;
		}
		session.setAttribute(CMinuta.TIPO_REPARTO,tipo);
		//String accionNotarial = minuta.getAccionNotarial().getIdAccionNotarial();
		//session.setAttribute(CMinuta.TIPO_ACCION_NOTARIAL,accionNotarial);
		categoria = minuta.getCategoria().getNombre();
		session.setAttribute(CMinuta.CATEGORIA_NOM,categoria);		
		String numFolios = String.valueOf(minuta.getNumeroFolios());
		session.setAttribute(CMinuta.NRO_FOLIOS_MINUTA,numFolios);
		String idCirculoNot = minuta.getCirculoNotarial().getIdCirculo();
		session.setAttribute(CMinuta.CIRCULO_NOTARIAL_ID,idCirculoNot);
		String nomCirculo = minuta.getCirculoNotarial().getNombre();
		session.setAttribute(CMinuta.CIRCULO_NOTARIAL_NOM,nomCirculo);
		String cuantia = df.format(minuta.getValor());
		session.setAttribute(CMinuta.CUANTIA,cuantia);
		if(session.getAttribute(CMinuta.ACCION_NOTARIAL_CON_CUANTIA)== null)
    {
    	session.setAttribute(CMinuta.ACCION_NOTARIAL_CON_CUANTIA, "ACCION_NOTARIAL_CON_CUANTIA");
    }
		String unidades = String.valueOf(minuta.getUnidades());
		session.setAttribute(CMinuta.UNIDADES,unidades);
		String obsReparto = minuta.getComentario();
		session.setAttribute(CMinuta.OBSERVACIONES_REPARTO,obsReparto);
		
		if(session.getAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES) == null){
			List nAcciones = new ArrayList();
			Iterator itAcciones = (minuta.getAccionesNotariales()).iterator();
			while(itAcciones.hasNext()){
				Object objeto = new Object();
				objeto = itAcciones.next();
				nAcciones.add(objeto);
			}
			session.setAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES,nAcciones);
		}
		
		if(session.getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS) == null){
			List nEntidades = new ArrayList();
			Iterator itEntidades = (minuta.getEntidadesPublicas()).iterator();
			while(itEntidades.hasNext()){
				Object objeto = new Object();
				objeto = itEntidades.next();
				nEntidades.add(objeto);
			}
			session.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS,nEntidades);
		}
		if(session.getAttribute(WebKeys.LISTA_OTORGANTES) == null){		
			List nOtorgantes = new ArrayList();
			Iterator itOtorgantes = (minuta.getOtorgantesNaturales()).iterator();
			while(itOtorgantes.hasNext()){
				Object objeto = new Object();
				objeto = itOtorgantes.next();
				nOtorgantes.add(objeto);
			}
			session.setAttribute(WebKeys.LISTA_OTORGANTES, nOtorgantes);
		}
			
	}
	List circs = (List)session.getAttribute(WebKeys.LISTA_CIRCULOS_NOTARIALES);
	if(circs == null){
		circs = new ArrayList();
	}
	Iterator itCirculos = circs.iterator();
	List elementos = new ArrayList();
	while(itCirculos.hasNext()){
		CirculoNotarial circ = (CirculoNotarial)itCirculos.next();
		elementos.add(new ElementoLista(circ.getIdCirculo(),circ.getNombre()));
	}
	circuloNotHelper.setTipos(elementos);
	
	if(session.getAttribute(CMinuta.IMPRIMIR_CONSTANCIA)== null)
    {
    	session.setAttribute(CMinuta.IMPRIMIR_CONSTANCIA, "IMPRIMIR_CONSTANCIA");
    }

%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">

function recargar() {
	document.getElementById('ID_TIPO_ACCION_NOTARIAL').value=document.getElementById('TIPO_ACCION_NOTARIAL').value;
	//cambiarAccion2('<%=AWConsultasReparto.PRESERVAR_INFO%>')
}

function cambiarAccion(text) {
	document.BUSCAR.<%=WebKeys.ACCION%>.value = text;
	document.BUSCAR.submit();	
}
function cambiarAccion2(text) {
	if (text== '<%=AWConsultasReparto.ENVIAR_EDICION%>')
	{
		if (confirm("Esta seguro que desea modificar la minuta"))
		{	
			document.BUSCAR2.<%=WebKeys.ACCION%>.value = text;
			document.BUSCAR2.submit();	
		}
	} else if (text== '<%=AWConsultasReparto.ANULAR_MINUTA%>')
	{
		if (confirm("Esta seguro que desea anular la minuta"))
		{	
			document.BUSCAR2.<%=WebKeys.ACCION%>.value = text;
			document.BUSCAR2.submit();	
		}
	}  else
	{
		document.BUSCAR2.<%=WebKeys.ACCION%>.value = text;
		document.BUSCAR2.submit();	
	}
}

function validarAnula(nombre) {
	if (confirm('Esta seguro que desea anular la Minuta')){    
      cambiarAccion2(nombre);
	}
}
function eliminar(pos, ejecucion) {
	document.BUSCAR2.<%=WebKeys.POSICION%>.value = pos;
	document.BUSCAR2.<%=WebKeys.ACCION%>.value = ejecucion;
	document.BUSCAR2.submit();		
} 
function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA%>').value ="";
}
function oficinas(nombre,valor,dimensiones)
{
	var idDepto = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
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

function elegirCuantia() 
{
	if(document.BUSCAR2.<%= CMinuta.ACCION_NOTARIAL_CON_CUANTIA%>.checked == true)
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
	document.BUSCAR2.<%=CMinuta.CUANTIA%>.value = '0';
}
function conCuantia() {
	document.getElementById('<%=CMinuta.CUANTIA%>').removeAttribute("readonly"  , false);
	
	document.BUSCAR2.<%=CMinuta.CUANTIA%>.readonly = false;	
		
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Modificaci&oacute;n de Radicaciones de Reparto Notarial</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Consulta de Minutas de Reparto Notarial</td>
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
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%=AWConsultasReparto.CONSULTAR_MINUTA_MODIFICACION%>">
           <table width="100%" class="camposform">
           		<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td allign="left" width="150">N&uacute;mero de Radicaci&oacute;n:</td>
                    <td width="40">
                    <% try {
                    textHelper.setNombre(CTurno.ID_TURNO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CTurno.ID_TURNO);
					textHelper.render(request,out);
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
                    <input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0">
                    </td>
		</form>
        <form action="consultasReparto.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="<%=AWConsultasReparto.TERMINA %>">
                    
                   	 <td>
						<input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                  </tr>
                </table>
                
                
            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
		<tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
		</table>
    </form>
    

    
    <%if(minuta != null){
    	boolean minutaEditable = true;
    	if(minuta.getEstado() == CReparto.MINUTA_ANULADA || minuta.getEstado() == CReparto.MINUTA_REPARTIDA){
    		minutaEditable = false;
    		textHelper.setReadonly(true);
    	}%>
    <form action="consultasReparto.do" method="POST" name="BUSCAR2" id="BUSCAR2">
    <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
          <table border="0" cellpadding="0" cellspacing="0">
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

           
				<input type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>">		            
				
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
              	<%if(minutaEditable){%>
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="20%"><strong>Tipo de radicación</strong></td>
                  <td width="20%" align='right'><strong>Ordinaria</strong></td>
                  <td width="20%">
						<% try {
 		                        String defecto = (String)request.getSession().getAttribute(CMinuta.TIPO_REPARTO);
 		                        if(defecto==null){
 		                        	request.getSession().setAttribute(CMinuta.TIPO_REPARTO,CMinuta.ORDINARIO);
 		                        }														
 		                        radioHelper.setNombre(CMinuta.TIPO_REPARTO);
                  			    radioHelper.setId(CMinuta.TIPO_REPARTO);
                  			    radioHelper.setValordefecto(CMinuta.ORDINARIO);                  			    
							    radioHelper.render(request,out);
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
                 <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                 <td align='left'><strong>Categor&iacute;a: <%=categoria%></strong></td>
                </tr>
                <%} else {%>
                 <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td align='left'><strong>Tipo de radicación: <%=tipo%></strong></td>
                  <td align='left'><strong>Categor&iacute;a: <%=categoria%></strong></td>                  
                  <td align='left'>
                  </td>
                </tr>
                <%}%>

              </table>              

              <br>
              <hr class="linehorizontal">
              <br>              
              
       
				<!--Aca se válida el ingreso de departamento, municipio y ciudad para llenar la Vereda de origen. Encabezado-->
				<table width="100%" class="camposform">
				<tr>
				<td align="left" width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td width="15%" align='right'>No folios.</td>
	            <td width="35%">
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
				<td align="left" width="15%">Circulo Notarial: </td>
                <td align="left" width="35%">
                <%
                if(minutaEditable){
					try {
						circuloNotHelper.setNombre(CMinuta.CIRCULO_NOTARIAL_ID);
						circuloNotHelper.setCssClase("camposformtext");
						circuloNotHelper.setId(CMinuta.CIRCULO_NOTARIAL_ID);
						circuloNotHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				}else{
					try {
						textHelper.setNombre(CMinuta.CIRCULO_NOTARIAL_NOM);
						textHelper.setCssClase("camposformtext");
						textHelper.setNombre(CMinuta.CIRCULO_NOTARIAL_NOM);
						textHelper.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
				}
				%>                       
                </td>
                </tr> 
				</table>
			
				<!--Tabla de unidades-->
              <table width="100%" class="camposform">                
                
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Observaciones</td>
                  <td  width="85%" align='left' colspan='3'>
                        <%
							try {
								textAreaHelper.setNombre(CMinuta.OBSERVACIONES_REPARTO);
								textAreaHelper.setId(CMinuta.OBSERVACIONES_REPARTO);
								textAreaHelper.setCols("95");							
								textAreaHelper.setRows("5");							
								textAreaHelper.setCssClase("camposformtext");
								if(!minutaEditable){
									textAreaHelper.setReadOnly(true);
								}
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
              <!--nuevo cambio-->
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
			  List actosCargados = (List)session.getAttribute(WebKeys.LISTA_ACTOS_MINUTA);
				long unidadesTotal = 0;
				double valortotal = 0;
				if(actosCargados!=null && actosCargados.size()>0){
				%>
					<table width="100%" class="camposform">
					<tr>
								<td width="3%"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
								<td class="titulotbcentral"> # </td>
								<td class="titulotbcentral"> Tipo Accion Notarial </td>
								<td class="titulotbcentral"> Cuant&iacute;a </td>
								<td class="titulotbcentral"> Unidades </td>
								<td class="titulotbcentral"> Con Cuant&iacute;a </td>
							</tr>
				<%
					int a = 0;
					Iterator itActos = actosCargados.iterator();				
					while(itActos.hasNext()){
						//AccionNotarial accionNotarialLista = (AccionNotarial) itActos.next();
						MinutaAccionNotarial actoMinutaTmp = (MinutaAccionNotarial) itActos.next();
						AccionNotarial accionNotarialLista = actoMinutaTmp.getAccionNotarial();
						unidadesTotal = unidadesTotal + actoMinutaTmp.getUnidades();
						valortotal = valortotal + actoMinutaTmp.getValor();
						String valor = String.valueOf(actoMinutaTmp.getValor());
						String unidades = String.valueOf(actoMinutaTmp.getUnidades());
						String conCuantia = String.valueOf(actoMinutaTmp.getConCuantia());
						String labelConCuantia = "SI";
						if (!conCuantia.equals("1")) {
							labelConCuantia = "NO";
						}
				%>		
					<tr>
						<td><img src="<%=request.getContextPath()%>/jsp/images/ico_acto.gif" width="16" height="21"></td>
						<td class="campositem"><%=(a+1)%></td>
						<td class="campositem"><%=(accionNotarialLista!=null && accionNotarialLista.getNombre()!=null ? accionNotarialLista.getNombre(): "&nbsp;")%></td>
						<td class="campositem"><%=(valor!=null && valor!=null ? valor: "&nbsp;")%></td>
						<td class="campositem"><%=(unidades!=null && unidades!=null ? unidades: "&nbsp;")%></td>
						<td class="campositem"><%=labelConCuantia%></td>
		                <td width="20" align='left'>  
		                <%
		                	if (minutaEditable)
		                	 {
		                %>
							<a href="javascript:eliminar('<%=""+a%>','<%=AWConsultasReparto.ELIMINAR_ACCION_NOTARIAL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" alt="Eliminar accion notarial" width="35" height="13" border="0"></a>                  
						<%
							} else {
						%>
							&nbsp;
						<%	}%>
		                </td>						
						</tr>
				<%  a++;
					}
				%>
					</table>
				<%
				} 
				session.setAttribute(CMinuta.CUANTIA,String.valueOf(valortotal));
				session.setAttribute(CMinuta.UNIDADES,String.valueOf(unidadesTotal));
				
				if(minutaEditable)
				{
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
					<a href="javascript:cambiarAccion2('<%=AWConsultasReparto.AGREGAR_ACCION_NOTARIAL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a>                  
                  </td>
                </tr>
                
              </table>
			  
			  <% } %>


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
						boolean entidadexento = entidadPublica.isExento();
						String tentidadexento = "&nbsp;";
						if(entidadexento){
							tentidadexento = "EXENTO";
						}
				%>		
				
						<tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td class="titulotbcentral"><%=(entidadPublica.getEntidadPublica()!=null && entidadPublica.getEntidadPublica().getNombre()!=null ? entidadPublica.getEntidadPublica().getNombre(): "&nbsp;")%></td>
						<td width="50" align='left'><%=tentidadexento%></td>
		                <%if(minutaEditable){%>
		                <td width="20" align='left'>  
							<a href="javascript:eliminar('<%=""+a%>','<%=AWConsultasReparto.ELIMINAR_ENTIDAD_PUBLICA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" alt="Eliminar otorgante natural" width="35" height="13" border="0"></a>                  
		                </td>
		                <%}%>						
						</tr>
				<%  a++;
					}
				%>
					</table>
				<%
				} 
				%>

			<%if(minutaEditable){%>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Entidad pública:</td>
                  <td  width="45%" align='left'>
                        <%
                        	List entidades = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
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
					<a href="javascript:cambiarAccion2('<%=AWConsultasReparto.AGREGAR_ENTIDAD_PUBLICA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a>                  
                  </td>		
                
              </table>
		<%}%>
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
						boolean otorganteexento = otorgante.isExento();
						String totorganteexento = "&nbsp;";
						if(otorganteexento){
							totorganteexento = "EXENTO";
						}
				%>		
						<tr>
						<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td class="titulotbcentral"><%=(otorgante!=null && otorgante.getNombre()!=null ? otorgante.getNombre(): "&nbsp;")%></td>
						<td width="50" align='left'><%=totorganteexento%></td>
						<%if(minutaEditable){%>
		                <td width="20" align='left'>  
							<a href="javascript:eliminar('<%=""+a%>','<%=AWConsultasReparto.ELIMINAR_OTORGANTE_NATURAL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" alt="Eliminar otorgante natural" width="35" height="13" border="0"></a>                  
		                </td>
		                <%}%>						
						</tr>
				<%  a++;
					}
				%>
					</table>
				<%
				} 
				%>
       
       
			<%if(minutaEditable){%>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Otorgante natural:</td>
                  <td  width="45%" align='left'>
                        <%
							try {
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
                  <td width="40%" align='left'>
					<a href="javascript:cambiarAccion2('<%=AWConsultasReparto.AGREGAR_OTORGANTE_NATURAL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a>                  
                  </td>		
                
              </table>
			<%}%>
			<br>
            <br>
            <%if(minutaEditable){%>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Nota Informativa</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
			<table width="100%" class="camposform">
                <tr> 
                  <td>
					<% 
					try{
						nota.setCols("100");
						nota.setReadOnly(false);
						nota.setCssClase("camposformtext");
						nota.setId(CNota.DESCRIPCION);
						nota.setNombre(CNota.DESCRIPCION);
						nota.setRows("10");
						nota.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
					%>
                  </td>
                </tr>
              </table>
              <br>
              <br>
              <%}%>
              <hr class="linehorizontal">
              <%if(minutaEditable){%>
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td> 
					<a href="javascript:cambiarAccion2('<%=AWConsultasReparto.ENVIAR_EDICION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_guardar.gif" width="139" height="21" border="0"></a>
					<a href="javascript:validarAnula('<%=AWConsultasReparto.ANULAR_MINUTA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_anular.gif" width="139" height="21" border="0"></a>					
                  </td>
                </tr>
              </table>
              
              <%}%>
              
              
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
      <%}%>
	<%/*
		try{
 		    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("CORRECCION");
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}*/
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