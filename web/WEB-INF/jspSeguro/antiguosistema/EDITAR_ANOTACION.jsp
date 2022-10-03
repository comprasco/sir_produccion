<%@page import="java.util.Date"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gov.sir.core.negocio.modelo.Anotacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.negocio.modelo.Solicitud"%>
<%@page import="gov.sir.core.negocio.modelo.Liquidacion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.registro.VariosCiudadanosAnotacionAntiguoSistemaHelper"%>
<%@page import="gov.sir.core.web.acciones.correccion.*"%>
<%@page import="gov.sir.core.web.acciones.registro.*"%>
<%@page import="gov.sir.core.web.acciones.comun.*"%>
<%@page import="java.util.Vector"%>
<jsp:directive.page import="org.auriga.core.modelo.transferObjects.Rol"/>
<jsp:directive.page import="gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema"/>
<jsp:directive.page import="gov.sir.core.negocio.modelo.constantes.CRol"/>
<jsp:directive.page import="gov.sir.core.negocio.modelo.FolioDerivado"/>

<%
	//BASICOS
    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

    Solicitud solicitud =turno.getSolicitud();
	Folio folio = (Folio)request.getSession().getAttribute(WebKeys.FOLIO);

    List anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
	if(anotaciones == null){
		anotaciones = new java.util.Vector();
	}

	//NUMERO DE LA ANOTACION A EDITAR.
	int pos=0;


	String num= (String) request.getSession().getAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL);

	Anotacion a=(Anotacion)request.getSession().getAttribute(WebKeys.ANOTACION_EDITAR);

	/*
	List anotacionesTemporales = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
	Iterator iat= anotacionesTemporales.iterator();
	Anotacion a=null;
	for(;iat.hasNext();){
		Anotacion temp= (Anotacion) iat.next();
		if(temp.getOrden().equals(num)){
			a=temp;
			pos=Integer.parseInt(a.getOrden());
		}
	}*/
	String nomOficina= "";
    boolean isInternacional=false;
    if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudRegistro){
        gov.sir.core.negocio.modelo.SolicitudRegistro solicitudReg=(gov.sir.core.negocio.modelo.SolicitudRegistro)solicitud;
        if(solicitudReg !=null){
        	if(solicitudReg.getDocumento()!=null){
        		if(solicitudReg.getDocumento().getOficinaOrigen()!=null){

        			if(solicitudReg.getDocumento().getOficinaOrigen().getTipoOficina()!=null){
    	    			if(solicitudReg.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null){
    		    			nomOficina=solicitudReg.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
    	    			}
        			}
        			if(solicitudReg.getDocumento().getOficinaOrigen().getNombre()!=null){
        				nomOficina= nomOficina + " " +solicitudReg.getDocumento().getOficinaOrigen().getNombre();
        			}
        		}
        		if(solicitudReg.getDocumento().getOficinaInternacional()!=null){
        			isInternacional=true;
        			nomOficina= solicitudReg.getDocumento().getOficinaInternacional();
        		}
        	}
        }
    }else{
        if(a.getDocumento()!=null){
    		if(a.getDocumento().getOficinaOrigen()!=null){

    			if(a.getDocumento().getOficinaOrigen().getTipoOficina()!=null){
	    			if(a.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null){
		    			nomOficina=a.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
	    			}
    			}
    			if(a.getDocumento().getOficinaOrigen().getNombre()!=null){
    				nomOficina= nomOficina + " " +a.getDocumento().getOficinaOrigen().getNombre();
    			}
    		}
    		if(a.getDocumento().getOficinaInternacional()!=null){
    			isInternacional=true;
    			nomOficina= a.getDocumento().getOficinaInternacional();
    		}
    	}
    }


	NumberFormat formateador = NumberFormat.getInstance();


		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudRegistro){
            gov.sir.core.negocio.modelo.SolicitudRegistro solicitudReg=(gov.sir.core.negocio.modelo.SolicitudRegistro)solicitud;
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO,solicitudReg.getDocumento().getTipoDocumento().getNombre());
    	    request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,solicitudReg.getDocumento().getNumero());
    	    request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO,solicitudReg.getDocumento().getFecha());
    	    if(isInternacional){
    	    	request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
    	    }else{
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,solicitudReg.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre());
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,solicitudReg.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,solicitudReg.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,solicitudReg.getDocumento().getOficinaOrigen().getNumero());
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_COD,solicitudReg.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
    		}
		}else{
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO,a.getDocumento().getTipoDocumento().getNombre());
    	    request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,a.getDocumento().getNumero());
    	    request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO,a.getDocumento().getFecha());
    	    if(isInternacional){
    	    	request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
    	    }else{
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,a.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre());
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,a.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());
				if (a.getDocumento().getOficinaOrigen().getTipoOficina()!=null){
                    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,a.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
        		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,a.getDocumento().getOficinaOrigen().getNumero());
				}
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
    		    request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_COD,a.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
    		}
		}

	    List grupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
		session.setAttribute("listanat",grupoNaturalezas);
	    session.removeAttribute("cancelar");

	    //iniciando en el session los valores de la anotacion correspondiente
		if(request.getSession().getAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION)==null){
					request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, formateador.format(a.getValor()));		
		}
		String idNat = (String)request.getSession().getAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
		String nomNat = (String)request.getSession().getAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
		if(idNat==null){
			request.getSession().setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID,a.getNaturalezaJuridica().getIdNaturalezaJuridica());
			request.getSession().setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM,a.getNaturalezaJuridica().getNombre());
		}
		if(request.getSession().getAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION)==null){
			request.getSession().setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION,a.getComentario());
		}
		if( request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION) ==null ){
			request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION,a.getAnotacionesCiudadanos());
		}

	request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);


	//PREPARACION HELPERS
    TextHelper tiposDocHelper = new TextHelper();
	TextHelper textHelper = new TextHelper();
	TextHelper hiddenHelper = new TextHelper();
	hiddenHelper.setTipo("hidden");
	TextAreaHelper textAreaHelper = new TextAreaHelper();
	MostrarFechaHelper fecha = new MostrarFechaHelper();

	//HELPER VARIOS CIUDADANOS
	VariosCiudadanosAnotacionAntiguoSistemaHelper variosCiudadanosHelper = new VariosCiudadanosAnotacionAntiguoSistemaHelper();
	Integer auxNumFilas = (Integer)session.getAttribute(AWCalificacion.NUM_REGISTROS_TABLA_CIUDADANOS);
	List lciudadanos= (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
	if(lciudadanos==null){
		lciudadanos= new Vector();
	}
	int numFilas;
	if(auxNumFilas == null)
		numFilas=AWCalificacion.DEFAULT_NUM_CIUDADANOS_TABLA;
	else
		numFilas=auxNumFilas.intValue();
	List tiposID = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
	variosCiudadanosHelper.setPropertiesHandly(numFilas,tiposID,AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION,
																AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION,
																AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_EDICION,
																AWCalificacion.ELIMINAR_CIUDADANO_EDICION,
																lciudadanos, AWCalificacion.VALIDAR_CIUDADANO_EDICION, "ANOTACION");


    //Se mira si existen excepciones.
	Boolean exception;
	exception = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
	if(exception!=null){
		 variosCiudadanosHelper.setCentrar(false);
	}
	session.removeAttribute(WebKeys.HAY_EXCEPCION);
    %>
    <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
    <script type="text/javascript">

    function ocultarSalvedad(text) {
	   document.ANOTACION.VER_SALVEDAD.value = text;
	   cambiarAccion('VER_SALVEDAD');
  	}

function cambiarAccion(text) {
	/*alert("text    "+text);*/
	document.ANOTACION.ACCION.value = text;
	/*alert("text    "+text);*/
	document.ANOTACION.submit();
	/*alert("text    "+text);*/
}
function quitar(pos,accion) {
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function cargarAnotacion(pos,accion) {
	document.ANOTACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function oficinas(nombre,valor,dimensiones)
{
	document.ANOTACION.ACCION.value='<%=AWCalificacion.PRESERVAR_INFO_ANOTACION%>';
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

function juridica(nombre,valor,dimensiones)
{
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
         /**
                * @Autor: Carlos Torres
                * @Mantis: 0012705
                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                * @Descripcion: Se asigna valores a campos en el formulario
                */
        document.getElementById('natjuridica_ver').value = valor+"_VER";
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.ANOTACION.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
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

function Secuencia(text){
	document.ANOTACION.SECUENCIA.value = text;
	cambiarAccion('REFRESCAR_EDICION');
}

function cambiarAccionAndSendTipoTarifa(text) {
	if(document.ANOTACION.<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID %>.value!=""){
		document.ANOTACION.ACCION.value = text;
		document.ANOTACION.submit();
	}
}
function reescribirValor(valor,id){
	var my_str = valor;
	var miles=1;
	if(my_str.indexOf(".")==-1){
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=my_str.length;i++){
				var desde = my_str.length-i*3;
				var hasta = my_str.length-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr=nStr+".00";
		document.getElementById(id).value = nStr;
		}
	} else {
		var largo = my_str.indexOf(".");
		var centavos = my_str.substr(largo,my_str.length);
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=largo;i++){
				var desde = largo-i*3;
				var hasta = largo-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr = nStr+centavos;
		document.getElementById(id).value = nStr;
		}
	}
}
</script>
    <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
 <form action="antiguosistema.do" method="post" name="ANOTACION" id="ANOTACION">
 <input type="hidden" name="ACCION" value="">
 <input type="hidden" name="POSICION" value="">
 <input type="hidden" name="VER_SALVEDAD" value="">
 <input type="hidden" name="SECUENCIA" value="-1">
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Edici&oacute;n Anotaciones </td>
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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">ANOTACIONES</td>
              <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                  </tr>
              </table></td>
              <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
            </tr>
        </table></td>
        <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
      </tr>

      <tr>
        <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" >
          <tr>
            <td>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Datos B&aacute;sicos </td>
                </tr>
                <tr>
                	<td>&nbsp;</td>
                	<td><table width="100%" class="camposform">
                		<tr>
                			<td width="15%">N&uacute;mero anotaci&oacute;n </td>
                			<td class="campositem" align="right" width="15%">  <%=a.getOrden()%></td>
                			<td>&nbsp;</td>
                		</tr>
                		</table>
                	</td>
                	<td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td>Tipo</td>
                        <td class="campositem">
                        <%try {
		 		                tiposDocHelper.setId(CFolio.ANOTACION_TIPO_DOCUMENTO);
								tiposDocHelper.setCssClase("camposformtext");
								tiposDocHelper.setNombre(CFolio.ANOTACION_TIPO_DOCUMENTO);
								tiposDocHelper.setEditable(false);
								tiposDocHelper.render(request,out);
					}catch(HelperException re){
					 	 out.println("ERROR " + re.getMessage());
					}
					 %>
                        </td>
                        <td>N&uacute;mero</td>
                        <td class="campositem">
                        <%try {
		 		                 textHelper.setTipo("text");
								textHelper.setNombre(CFolio.ANOTACION_NUM_DOCUMENTO);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(CFolio.ANOTACION_NUM_DOCUMENTO);
								textHelper.setEditable(false);
								textHelper.render(request,out);
					}catch(HelperException re){
					 	 out.println("ERROR " + re.getMessage());
					}
					 %>
                        </td>
                        <td>Fecha</td>
                        <td>
                        <table align="center">
                        <tr>
                        <td class="campositem">
                        <%try {
		 		                 Date dateDoc=(Date)request.getSession().getAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
								fecha.setDate(dateDoc);
								fecha.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
								fecha.render(request,out);
					}catch(HelperException re){
					 	 out.println("ERROR " + re.getMessage());
					}
					 %>
					 </td>
					 </tr>
					 </table>
                        </td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Oficina de Procedencia </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                  <%if(!isInternacional){%>
	                  <table width="100%" class="camposform">
	                      <tr>
	                        <td width="30">Codigo</td>
	                        <td class="campositem">
	                       <%try {
			 		                textHelper.setTipo("text");
									textHelper.setCssClase("camposformtext");
									textHelper.setNombre("ANOTACION_OFICINA_DOCUMENTO_COD");
									textHelper.setId("ANOTACION_OFICINA_DOCUMENTO_COD");
									textHelper.setEditable(false);
									textHelper.render(request,out);
							}catch(HelperException re){
						 		 out.println("ERROR " + re.getMessage());
							}
						 %>
	                        </td>
	                        <td width="50">Nombre</td>
	                        <td class="campositem">
	                         <%try {
									textHelper.setTipo("text");
									textHelper.setCssClase("camposformtext");
									textHelper.setNombre("ANOTACION_OFICINA_DOCUMENTO_NOM");
									textHelper.setId("ANOTACION_OFICINA_DOCUMENTO_NOM");
									textHelper.setEditable(false);
									textHelper.render(request,out);
							}catch(HelperException re){
						 		 out.println("ERROR " + re.getMessage());
							}
						 %>
	                        </td>
	                     </tr>
	                     <tr>
	                     	<td width="80">Departamento</td>
	                            <td class="campositem">
	                            <%try {
			 		                 textHelper.setTipo("text");
									textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
									textHelper.setEditable(false);
									textHelper.render(request,out);
							}catch(HelperException re){
						 		 out.println("ERROR " + re.getMessage());
							}
						 %>
	                       <td width="60">Municipio</td>
	                            <td class="campositem">
	                            <%try {
			 		                 textHelper.setTipo("text");
									textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
									textHelper.setEditable(false);
									textHelper.render(request,out);
							}catch(HelperException re){
						 		 out.println("ERROR " + re.getMessage());
							}
						 %>
	                            </td>
	                        </td>
	                     </tr>
	                  </table>
	                 <%}else{%>
	                  <table width="100%" class="camposform">
                        <td width="17%">Oficina internacional</td>
                        <td class="campositem">
                        <% try {
 		                        textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                        </td>
                        <td width="50%">&nbsp;</td>
                      </table>
	                 <%}%>
                  </td>
                </tr>
              </table>



              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Especificaci&oacute;n</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
					<tr>
						<td>Fecha de Radicaci&oacute;n</td>
                        <td><table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="campositem">

                              <% try {

                              	Liquidacion liq=(Liquidacion)turno.getSolicitud().getLiquidaciones().get(0);

                              	Date fechaPago=liq.getPago().getFecha();
                              	fecha.setDate(fechaPago);
								fecha.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
								fecha.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>

                              </td>
                            </tr>
                        </table></td>
					</tr>
				</table>
  				<table width="100%" class="camposform">
				  <tr>
					<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					<td width="50">Valor</td>
					<td width="150">
						<%try {
							textHelper.setTipo("text");
							textHelper.setNombre(CFolio.ANOTACION_VALOR_ESPECIFICACION);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(CFolio.ANOTACION_VALOR_ESPECIFICACION);
							textHelper.setEditable(true);
							textHelper.setSize("20");
							textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
							textHelper.render(request,out);
						}catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						%>
					</td>


					<td width="150">Naturaleza Jur&iacute;dica </td>
					<td width="40" align="justify">ID
					</td>
					<td width="40">
						<%try {
							textHelper.setFuncion("");
							textHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
							textHelper.setFuncion("onBlur=\"cambiarAccionAndSendTipoTarifa('"+AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION+"')\"");
							textHelper.setSize("5");
							textHelper.render(request,out);
						}catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}%></td>
					<td width="100" align="right">Descripci&oacute;n</td>
					<td align="justify">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<input name="natjuridica_id" type="hidden" id="natjuridica_id" value="">
								<input name="natjuridica_nom" type="hidden" id="natjuridica_nom" value="">
								 <%--/**
                                                                                * @Autor: Carlos Torres
                                                                                * @Mantis: 0012705
                                                                                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                                                                                * @Descripcion: Se agrega campos al formulario
                                                                                */
                                                                --%>
                                                                <input name="natjuridica_ver" type="hidden" id="natjuridica_ver" value="">
                                                                <input name="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" type="hidden" id="<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER%>" value="<%=session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER)%>">
                                                                <td align="right">
								<%try {
									textAreaHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
									textAreaHelper.setCols("50");
									textAreaHelper.setRows("1");
									textAreaHelper.setReadOnly(true);
									textAreaHelper.setCssClase("camposformtext");
									textAreaHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
									textAreaHelper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}%></td>
								<!--<td><a href="javascript:juridica('seleccionar.naturaleza.juridica.view?cancelacion=false&calificacion=false','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a></td>-->
								<td><a href="javascript:juridica('seleccionar-naturaleza-juridica.do?cancelacion=false&calificacion=false&<%= WebKeys.ACCION %>=<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a></td>
							</tr>
						</table>
					</td>
				  </tr>
				</table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Comentario</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                  <%try {
						textAreaHelper.setNombre(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
						textAreaHelper.setCols("60");
						textAreaHelper.setRows("3");
						textAreaHelper.setReadOnly(false);
						textAreaHelper.setCssClase("camposformtext");
						textAreaHelper.setId(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
						textAreaHelper.render(request,out);
						}catch(HelperException re){
					 		 	out.println("ERROR " + re.getMessage());
								}%>
                  </td>
                </tr>
              </table>
              <br>
              <hr class="linehorizontal">


	              <%
	          	try {
    	          	variosCiudadanosHelper.setMostrarBotonAgregar(false);
    	          	variosCiudadanosHelper.setMostrarListaCiudadanos(false);
					variosCiudadanosHelper.render(request,out);
			  	}
				catch(HelperException re) {
					re.printStackTrace();
				}
			  %>
				
				
				
			<%
			
			
			if(request != null && request.getSession() != null && request.getSession().getAttribute(WebKeys.ROL) != null){
        	Rol rol =(Rol)request.getSession().getAttribute(WebKeys.ROL);
        	if(rol != null && rol.getNombre() != null && rol.getNombre().equals(CRol.SIR_ROL_BUSCADOR_AS)){
        		
        		String strMatriculasP = request.getParameter("textmatriculaPadreE");
        		String strAnotacionesP = request.getParameter("textanotacionPadreE");
				      		        
				if(strMatriculasP == null){				      		        
	        		if(a!= null && a.getAnotacionesPadre() != null){
	               		List lstAnotaPadre = a.getAnotacionesPadre();
	               		if(lstAnotaPadre.size() > 0){
		               		FolioDerivado flDerivado = (FolioDerivado)lstAnotaPadre.get(0);
		               		if(flDerivado != null){
		               			strMatriculasP = flDerivado.getIdMatricula();
		               			strAnotacionesP = flDerivado.getIdAnotacion();
		               		}		
		               	}
	               	}
	            }
        		
        		
				///////para las matriculas Padre
        		out.println("<tr>");
                out.println(
                    "<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");
               out.println( "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                out.println("<tr>");
                out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
                    request.getContextPath() +
                    "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
                out.println("<td class=\"bgnsub\">Asociar Matricula Padre</td>");
                out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
                out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ico_asociar.gif\" width=\"16\" height=\"21\"></td>");
                out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
                    request.getContextPath() +
                    "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
                out.println("</tr>");
                out.println("</table>");
                
                //request.getSession().getAttribute(arg0)
                out.println("<table width=\"100%\" class=\"camposform\">");                
               
                out.println("<tr>");
                out.println("<td width=\"20\"><img src=\"" + request.getContextPath() +
                    "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
                      
                out.write("<td>Matricula</td>");
                out.write("<td>");
//                textHelper.setSize("");
//                textHelper.setNombre("textmatriculaPadre");
//                textHelper.setCssClase("camposformtext");
//                textHelper.setId("textmatriculaPadre");
//                
//                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
//                textHelper.setReadonly(false);                
//                textHelper.setFuncion("");
//                textHelper.render(request, out);
                
                String Nombre = "", cssClase ="", Id ="", valor = "";
                
                Nombre = "textmatriculaPadreE";
                cssClase ="camposformtext";
                Id ="textmatriculaPadreE";
                if(strMatriculasP!= null)
                	valor = strMatriculasP;
                
                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");               
                out.println("<input id=\"" + Id + "\"  name=\"" + Nombre + "\" value=\"" + valor +"\" "+
							" class=\"" + cssClase + "\" >");

                out.write("</td>");
                
                out.write("<td>Anotacion</td>");
                out.write("<td>");
                
                Nombre = "textanotacionPadreE";
                cssClase ="camposformtext";
                Id ="textanotacionPadreE";
              
                if(strAnotacionesP != null)
                	valor = strAnotacionesP;
                else{                	
                	valor = "";
                }
                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");               
                out.println("<input id=\"" + Id + "\"  name=\"" + Nombre + "\" value=\"" + valor +"\" "+
							" class=\"" + cssClase + "\" >");

                out.write("</td>");
                
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
                               
	            out.println("<tr>");
	            out.println("<td>&nbsp;</td>");
	            out.println("</tr>");
	            out.println("</table>");
	            out.println("</td>");
	            
	            
	            
	            
	            out.println("<td background=\"" + request.getContextPath() +
                    "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
                out.println("</tr>");
                
        		
        		///////para las matriculas hija
        		out.println("<tr>");
                
                out.println(
                    "<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");
                out.println(
                    "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                out.println("<tr>");
                out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
                    request.getContextPath() +
                    "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
                out.println("<td class=\"bgnsub\">Asociar Matriculas Hijas</td>");
                out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
                out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ico_asociar.gif\" width=\"16\" height=\"21\"></td>");
                out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
                    request.getContextPath() +
                    "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
                out.println("</tr>");
                out.println("</table>");
                
                //request.getSession().getAttribute(arg0)
                Integer nuMatriculas = new Integer(1);
                String[] strVectorMatriculas = {};
                String[] strVectorAnotaciones = {};
                if( request.getSession().getAttribute(AWAntiguoSistema.NUM_REGISTROS_TABLA_MATRICULAS_EDIT) != null){
                	nuMatriculas = (Integer) request.getSession().getAttribute(AWAntiguoSistema.NUM_REGISTROS_TABLA_MATRICULAS_EDIT);
                    strVectorMatriculas = request.getParameterValues("textmatriculahijaE");
	                strVectorAnotaciones = request.getParameterValues("textanotacionhijaE");
                }else{
                	if(a != null && a.getAnotacionesHijos() != null){
                		List lstAnotaHijos =  a.getAnotacionesHijos();
                		Iterator iteraAnotaHijos = lstAnotaHijos.iterator();
                		if(lstAnotaHijos.size() > 0){
			           		strVectorMatriculas = new String[lstAnotaHijos.size()];      
	                		strVectorAnotaciones =new String[lstAnotaHijos.size()];
	                		int i = 0;
	                		while(iteraAnotaHijos.hasNext()){
	                			FolioDerivado folioDerivado = (FolioDerivado)iteraAnotaHijos.next();
								strVectorMatriculas[i]= folioDerivado.getIdMatricula1();
								strVectorAnotaciones[i]= folioDerivado.getIdAnotacion1();
								i = i+1;
	                		}
	                		request.getSession().setAttribute(AWAntiguoSistema.NUM_REGISTROS_TABLA_MATRICULAS_EDIT,new Integer(lstAnotaHijos.size()));                	
	                		nuMatriculas = new Integer(lstAnotaHijos.size());
	                	}else{
                			request.getSession().setAttribute(AWAntiguoSistema.NUM_REGISTROS_TABLA_MATRICULAS_EDIT,new Integer(1));                	
	                		nuMatriculas = new Integer(1);
	                	}
                	}else{
	                	request.getSession().setAttribute(AWAntiguoSistema.NUM_REGISTROS_TABLA_MATRICULAS_EDIT,nuMatriculas);                	
	                }
                }
                
               /* strVectorMatriculas = request.getParameterValues("textmatriculahija");
                strVectorAnotaciones = request.getParameterValues("textanotacionhija");*/
                
                out.println("<table width=\"100%\" class=\"camposform\">");                
                for(int i=0;i<nuMatriculas.intValue();i++){
	               
	                out.println("<tr>");
	                out.println("<td width=\"20\"><img src=\"" + request.getContextPath() +
	                    "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
	                      
	                out.write("<td>Matricula</td>");
	                out.write("<td>");
//	                textHelper.setSize("");
//	                textHelper.setNombre("textmatriculahija");
//	                textHelper.setCssClase("camposformtext");
//	                textHelper.setId("textmatriculahija");
//	                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
//	                textHelper.setReadonly(false);	                
//	                textHelper.setFuncion("");
//	                textHelper.render(request, out);
	                
	                
	                Nombre = "textmatriculahijaE";
	                cssClase ="camposformtext";
	                Id ="textmatriculahijaE";
	                if(strVectorMatriculas != null && i<strVectorMatriculas.length)
	                	valor = strVectorMatriculas[i];
	                else
	                	valor = "";
	                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");               
	                out.write("<input id=\"" + Id + "\"  name=\"" + Nombre + "\" value=\"" + valor +"\" "+
								" class=\"" + cssClase + "\" >");
	                
	
	                out.write("</td>");
	                
	                out.write("<td>Anotacion</td>");
	                out.write("<td>");
//	                textHelper.setSize("");
//	                textHelper.setNombre("textanotacionhija");
//	                textHelper.setCssClase("camposformtext");
//	                textHelper.setId("textanotacionhija");
//	                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
//	                textHelper.setReadonly(false);	               
//	                textHelper.setFuncion("");
//	                textHelper.render(request, out);
	                Nombre = "textanotacionhijaE";
	                cssClase ="camposformtext";
	                Id ="textanotacionhijaE";
	                if(strVectorAnotaciones != null && i<strVectorAnotaciones.length)
	                	valor = strVectorAnotaciones[i];
	                else
	                	valor = "";
	                //textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");               
	                out.write("<input id=\"" + Id + "\"  name=\"" + Nombre + "\" value=\"" + valor +"\" "+
								" class=\"" + cssClase + "\" >");
	
	                out.write("</td>");	                	                	         
	
	                out.println("<td>&nbsp;</td>");
	                out.println("</tr>");
	                        
	                
                }
                
                               
	            out.println("<tr>");
	
	            //aumentar un registro
	            out.println("<td>");
	            
	            /*<input type =\"image\" src=\"" + request.getContextPath() +
	                "/jsp/images/sub_btn_agregar.gif\" name=\"agregarMatricula\"  alt=\"Agregar un registro más\" width=\"18\" height=\"14\" border=\"0\"   onClick=\"cambiarAccion('" +
	                    AWAntiguoSistema.AGREGAR_MATRICULA_ASO_EDIT + "')\"></td>");*/
	            %>        
	            <a href="javascript:cambiarAccion('AGREGAR_MATRICULA_ASO_EDIT')">
	            	<img src="<%=request.getContextPath()%>/jsp/images/sub_btn_agregar.gif" name="agregarMatricula" width="18" height="14" border="0" id="agregarMatricula">
	            </a>
				<%
				out.println("</td>");
	            //out.println("<td width=\"5\">&nbsp;</td>");
	
	            //disminuir un registro
	            /*out.println("<td><input type =\"image\" src=\"" + request.getContextPath() +
	                "/jsp/images/sub_btn_eliminar.gif\" name=\"removerMatricula\"  alt=\"Eliminar un Registro\" width=\"18\" height=\"14\" border=\"0\" onClick=\"cambiarAccion('" +
	                    AWAntiguoSistema.BORRAR_MATRICULA_ASO_EDIT + "')\"></td>");*/
	                    
	            out.println("<td>");
	            %>        
	            <a href="javascript:cambiarAccion('BORRAR_MATRICULA_ASO_EDIT')">
	            	<img src="<%=request.getContextPath()%>/jsp/images/sub_btn_eliminar.gif" name="removerMatricula" width="18" height="14" border="0" id="removerMatricula">
	            </a>
				<%
				out.println("</td>");        
	            
	            out.println("<td>&nbsp;</td>");
	            
	            out.println("</tr>");
	            
	            
	            out.println("</table>");
	            
	            
	            
	            out.println("</td>");
	            
	            
                
                
                
                
                
                out.println("<td background=\"" + request.getContextPath() +
                    "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
                out.println("</tr>");
                
                
                
                ////////////////////////////////////////////
        	}
        } %>
				

              </td>
          </tr>
        </table>
         </td>
        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
      </tr>
      <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
        </table></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">GRABACION TEMPORAL</td>
              <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                  </tr>
              </table></td>
              <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
            </tr>
        </table></td>
        <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
      </tr>
      <tr>
        <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" >

        </table>
         </td>
        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
      </tr>
      <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
          <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
            <td width="150"><a href="javascript:cambiarAccion('GRABAR_EDICION_ANOTACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif" name="Folio" width="150" height="21" border="0" id="Folio"></a></td>
            <td><a href="javascript:cambiarAccion('CANCELAR_EDICION_ANOTACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
          </tr>
        </table></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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
  </form>
</table>
