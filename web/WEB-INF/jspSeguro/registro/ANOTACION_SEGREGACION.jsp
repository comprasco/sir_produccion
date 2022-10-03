<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCorreccion"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.negocio.modelo.Liquidacion"%>
<%@page import="gov.sir.core.negocio.modelo.Solicitud"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.registro.VariosCiudadanosAnotacionHelper"%>
<%@page import="gov.sir.core.web.acciones.registro.*"%>
<%@page import="gov.sir.core.negocio.modelo.Pago"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAnotacion"%>
<%@page import="gov.sir.core.web.helpers.comun.RadioHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFase"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.WebSegregacion"%>
<%@page import="gov.sir.core.negocio.modelo.WebAnotacion"%>
<%@page import="gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica"%>
<%@page import="gov.sir.core.negocio.modelo.NaturalezaJuridica"%>
<%@page import="gov.sir.core.negocio.modelo.OficinaOrigen"%>
<%@page import="gov.sir.core.util.ListasContextoUtil"%>

<%
	session.removeAttribute(WebKeys.APROBAR_CALIFICACION);
	//BASICOS
    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    // datos de solicitud bind-data2form
    String nomOficina= "";
    boolean isInternacional=false;
    boolean isComentarioAnotacion=false;
    
    ListaElementoHelper tipoModalidadHelper = new ListaElementoHelper();
    List modalidad = ListasContextoUtil.getModalidades();
            if(modalidad == null){
                modalidad = new ArrayList();
            }
    tipoModalidadHelper.setTipos(modalidad);
    tipoModalidadHelper.setCssClase("camposformtext");

    // datos de la solicitud
    Solicitud local_Solicitud;

    loadData_TurnoSolicitud: {

      local_Solicitud = turno.getSolicitud();

      // Bug 3563
      if( local_Solicitud == null ) {
      }
      else if( local_Solicitud instanceof SolicitudRegistro ) {
        SolicitudRegistro solicitud =(SolicitudRegistro)local_Solicitud;

    	if(solicitud.getDocumento()!=null){

    		if(solicitud.getDocumento().getComentario()!=null){
    			isComentarioAnotacion=true;
    			nomOficina= solicitud.getDocumento().getComentario();
    		}
    		if(solicitud.getDocumento().getOficinaOrigen()!=null){

    			if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina()!=null){
	    			if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null){
		    			nomOficina=solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
	    			}
    			}
    			if(solicitud.getDocumento().getOficinaOrigen().getNombre()!=null){
    				nomOficina= nomOficina + " " +solicitud.getDocumento().getOficinaOrigen().getNombre();
    			}
    		}
    		if(solicitud.getDocumento().getOficinaInternacional()!=null){
    			isInternacional=true;
    			nomOficina= solicitud.getDocumento().getOficinaInternacional();
    		}
    	} // if

        // load display data

        //VARIABLES POR DEFECTO DEL DOCUMENTO.
        request.getSession().setAttribute(CFolio.ANOTACION_ID_DOCUMENTO,solicitud.getDocumento().getIdDocumento());
        request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO,solicitud.getDocumento().getTipoDocumento().getNombre());
        request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,solicitud.getDocumento().getNumero());
        request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO,solicitud.getDocumento().getFecha());

        if(isComentarioAnotacion){
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
        }
        else if(isInternacional){
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
        }
        else{
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,solicitud.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre());
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,solicitud.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,solicitud.getDocumento().getOficinaOrigen().getNumero());
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_COD,solicitud.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
        } // if


      }
      else if( local_Solicitud instanceof SolicitudCorreccion ) {
        //solicitud = local_Solicitud;
      }
      else {
        // raise ?
      }

    } // :loadData_TurnoSolicitud

    Solicitud solicitud = local_Solicitud;

	Folio folio = (Folio)request.getSession().getAttribute(WebKeys.FOLIO);


    List grupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
	session.setAttribute("listanat",grupoNaturalezas);
    session.removeAttribute("cancelar");

    //HELPER VARIOS CIUDADANOS
	VariosCiudadanosAnotacionHelper variosCiudadanosHelper = new VariosCiudadanosAnotacionHelper();
	Integer auxNumFilas = (Integer)session.getAttribute(AWCalificacion.NUM_REGISTROS_TABLA_CIUDADANOS);
	List lciudadanos= (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
	int numFilas;
	if(auxNumFilas == null)
		numFilas=AWCalificacion.DEFAULT_NUM_CIUDADANOS_TABLA;
	else
		numFilas=auxNumFilas.intValue();
	List tiposID = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
        List tiposIDNatural = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL);
        List tiposPersona = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PERSONA);
        List tiposSexo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_SEXO);
        List tiposIDJuridica = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA);

	//PREPARACION HELPERS
        TextHelper tiposDocHelper = new TextHelper();
	TextHelper textHelper = new TextHelper();
	TextHelper hiddenHelper = new TextHelper();
	hiddenHelper.setTipo("hidden");
	TextAreaHelper textAreaHelper = new TextAreaHelper();
	MostrarFechaHelper fecha = new MostrarFechaHelper();

	//NUMERO DE LA SIGUIENTE ANOTACION
	String pos = (String) session.getAttribute(CFolio.NEXT_ORDEN_ANOTACION);
	if(pos == null)
		pos="1";

	//Se mira si existen exceptciones.
	Boolean exception;
	exception = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
	if(exception!=null){
		 variosCiudadanosHelper.setCentrar(false);
	}
	session.removeAttribute(WebKeys.HAY_EXCEPCION);

	%>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.SEGREGACION.ACCION.value = text;
	document.SEGREGACION.submit();
}
function quitar(pos,accion) {
	document.SEGREGACION.POSICION.value = pos;
	cambiarAccion(accion);
}
function juridica(nombre,valor,dimensiones)
{
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
         <%--/**
               * @Autor: Carlos Torres
               * @Mantis: 0012705
               * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
               * @Descripcion: Se asigna valor a campo en el formulario
               */
           --%>
        document.getElementById('natjuridica_ver').value = valor+"_VER";
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}

function verFolio(nombre,valor,dimensiones){
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}

function Secuencia(text){
	document.SEGREGACION.SECUENCIA.value = text;
	cambiarAccion('REFRESCAR_ANOTACION_SEGREGACION');
}

function cambiarAccionAndSendTipoTarifa(text) {
	if(document.SEGREGACION.<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID %>.value!=""){
		document.SEGREGACION.ACCION.value = text;
		document.SEGREGACION.action = 'calificacion.do';
		document.SEGREGACION.submit();
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

    function esSegregacion(){
        /**
              * @author: Guillermo Cabrera.
              * @change: Al direccion a la accion segregación
              * se setea el valor a true, para identificar posteriormente
              * la accion a realizar.
              * MANTIS: 1726
        **/
        document.SEGREGACION.<%=WebKeys.CAMPO_ES_SEGREGACION%>.value = "TRUE";
        cambiarAccion("<%=AWSegregacion.SEGREGACION_ANOTACION%>");
    }

</script>

<script type="text/javascript" >

	function oficinas(nombre,valor,dimensiones){
		//document.REGISTRO.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
		var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
		var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
		var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
		document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
		document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
		document.getElementById('numero_oficina').value=valor+"_NUM";
		document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
                 /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                document.getElementById('version').value=valor+"_OFICINA_VERSION";
		popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
		popup.focus();
	}

	function validarFecha(){
		if (document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR%>.value.length>0){
			var index=document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR%>.value.lastIndexOf('/')+1;
			if (index!=null){
				var fin=document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR%>.value.length;
				var texto=document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR%>.value.substring(index,fin);
				if (texto.length!=4){
					alert('Fecha incorrecta');
					document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR%>.value='';
					document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR%>.focus();
				}
			}
		}
		if (document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR2%>.value.length>0){
			var index=document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR2%>.value.lastIndexOf('/')+1;
			if (index!=null){
				var fin=document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR2%>.value.length;
				var texto=document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR2%>.value.substring(index,fin);
				if (texto.length!=4){
					alert('Fecha de la anotación incorrecta');
					document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR2%>.value='';
					document.SEGREGACION.<%=CSolicitudRegistro.CALENDAR2%>.focus();
				}
			}
		}
	}



function locacionTipoPredio(nombre,valor,dimensiones){
	document.getElementById('id_depto').value=valor+"_ID_DEPTO";
	document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
	document.getElementById('id_munic').value=valor+"_ID_MUNIC";
	document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
	document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
	document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
	if(document.CREAR.<%=CFolio.FOLIO_TIPO_PREDIO%>.value == '<%=gov.sir.core.negocio.modelo.constantes.CTipoPredio.TIPO_URBANO%>'){
		popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.NO_MOSTRAR_VEREDA%>',valor,dimensiones);
	} else {
		popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.MOSTRAR_VEREDA%>',valor,dimensiones);
	}
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


function cambiarImagen() {
	if(document.all.<%=CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION%>.value=='<%=CFolio.COPIAR%>'){
		document.all.caja.style.display = ''
		document.all.boton.style.display = ''
	}else if(document.all.<%=CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION%>.value=='<%=CFolio.ASOCIAR%>'){
		document.all.caja.style.display = ''
		document.all.boton.style.display = ''
	}else{
		document.all.caja.style.display = 'none'
		document.all.boton.style.display = 'none'
	}
}
function consultarFolio(text) {
	document.CREAR.ACCION.value = text;
	document.CREAR.submit();
}

function reescribirfecha(valor,id){
	var my_str = valor;
	if(my_str.search("/")==-1){
		var separator = "/";
		uno= my_str.slice(0,2);
		dos = my_str.slice(2,4);
		tres = my_str.slice(4,my_str.length);
		document.getElementById(id).value = "";
		my_str = uno+separator+dos+separator+tres;
		document.getElementById(id).value = my_str;
	}
}


</script>

















    <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
 <form action="segregacion.do" method="post" name="SEGREGACION" id="SEGREGACION">
 <input type="hidden" name="ACCION" value="">
 <input type="hidden" name="POSICION" value="">
 <input type="hidden" name="SECUENCIA" value="-1">
 <input type="hidden" name="VER_SALVEDAD" value="">
 <input type="hidden" name="POSSCROLL" id="POSSCROLL" value="<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">


            <%-- sof:block ( Bug 3563 ) : Needed For Correcciones --%>

				<input name="Depto" type="hidden" id="id_depto" value="" />
				<input name="Depto" type="hidden" id="nom_Depto" value="" />
				<input name="Mpio" type="hidden" id="id_munic" value="" />
				<input name="Mpio" type="hidden" id="nom_munic" value="" />
				<input name="Ver" type="hidden" id="id_vereda" value="" />
				<input name="Ver" type="hidden" id="nom_vereda" value="" />

            <%-- eof:block ( Bug 3563 ) --%>

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">ASISTENTE SEGREGACIÓN - PASO 1 - CREACIÓN ANOTACIÓN</td>
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
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">ANOTACI&Oacute;N</td>
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
              <br />
              
  <%
    WebSegregacion webSegregacion = (WebSegregacion)session.getAttribute(WebKeys.WEB_SEGREGACION);
    WebAnotacion webAnotacion =null;
    if(webSegregacion!=null){
	    webAnotacion = (WebAnotacion)webSegregacion.getAnotacion();	  
    }
    if(webAnotacion!=null){
		session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, webAnotacion.getIdNaturalezaJuridica());
		session.setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, webAnotacion.getValor());
		session.setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, webAnotacion.getComentario());	

		//SE CARGA LA DESCRIPCIÓN DE LA ANOTACIÓN.
		GrupoNaturalezaJuridica grupo = new GrupoNaturalezaJuridica();
		NaturalezaJuridica nat;
		boolean found = false;
		for(java.util.Iterator it = grupoNaturalezas.iterator(); it.hasNext() && !found;){
			grupo = (GrupoNaturalezaJuridica)it.next();
			for(java.util.Iterator it2=grupo.getNaturalezaJuridicas().iterator(); it2.hasNext() && !found;){
				nat = (NaturalezaJuridica)it2.next();
				
				if(nat.getIdNaturalezaJuridica().equals(webAnotacion.getIdNaturalezaJuridica())){
				
					if(!nat.isHabilitadoCalificacion()){
						request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
						request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                                                /**
                                                    * @Autor: Carlos Torres
                                                    * @Mantis: 0012705
                                                    * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                                                    * @Descripcion: Se asigna valor a campo en el formulario
                                                    */
                                                request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
					}else{
						request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, nat.getNombre());
						request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, nat.getIdNaturalezaJuridica());
                                                /**
                                                * @Autor: Carlos Torres
                                                * @Mantis: 0012705
                                                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                                                * @Descripcion: Se asigna valor a campo en el formulario
                                                */
                                                request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, String.valueOf(nat.getVersion()));
					} 
				
				}
			}
		}

    }
    
  %>              
              
              

<%-- SOF:REGION (CONDITIONAL) REGION1 --%>
<%-- condition code --%>
<%
if( local_Solicitud instanceof SolicitudRegistro ) {

%>
<%-- condition code --%>
<!-- DATOS DEL DOCUMENTOS-->



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
            <td class="campositem" align="right" width="10%"> <%=pos%> </td>
            <%if(turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO) && turno.getIdFase().equals(CFase.CAL_CALIFICACION)){ %>
            	<td width="15%">Folio </td>
                <td class="campositem" align="right" width="15%"> <%=folio.getIdMatricula()%> </td>
                <td width="15%"><a href="javascript:verFolio('consultar.folio.do?POSICION=0&FOLIO_ANOTACION=FOLIO_ANOTACION','Folio','width=900,height=450,scrollbars=yes')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a></td>
            <%}else{ %>
                <td>&nbsp;</td>
            <%} %>
          </tr>
        </table>
      </td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td  width="1%"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>Datos del documento</td>
      </tr>
	  

      <tr>
        <td>&nbsp;</td>
        <td><table width="100%" class="camposform">
          <tr>
            <td>Tipo</td>
            <td class="campositem">
              <%try {
              textHelper.setTipo("text");
              tiposDocHelper.setId(CFolio.ANOTACION_TIPO_DOCUMENTO);
              tiposDocHelper.setNombre(CFolio.ANOTACION_TIPO_DOCUMENTO);
              tiposDocHelper.setCssClase("camposformtext");
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
            textHelper.setId(CFolio.ANOTACION_NUM_DOCUMENTO);
            textHelper.setNombre(CFolio.ANOTACION_NUM_DOCUMENTO);
            textHelper.setCssClase("camposformtext");
            textHelper.setEditable(false);
            textHelper.render(request,out);
          }catch(HelperException re){
            out.println("ERROR " + re.getMessage());
          }
          %>
          </td>
          <tr>
          </tr>
          <td>Fecha</td>
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
        <td>
        </td>
      </tr>

      <tr>
        <%if(isComentarioAnotacion){%>
        <td width="20%">Oficina procedencia</td>
        <td class="campositem" width="20%">
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
        <td width="25%">&nbsp;</td>
        <td width="25%">&nbsp;</td>
        <%}else if(isInternacional){%>
        <td width="20%">Oficina procedencia</td>
        <td class="campositem" width="20%">
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
        <td width="25%">&nbsp;</td>
        <td width="25%">&nbsp;</td>
        <%}else{%>
        <td width="40">Oficina procedencia</td>
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
      <%}%>
    </tr>
  </table></td>
</tr>
</table>

<!-- DATOS DEL DOCUMENTOS-->
<%-- condition code --%>
<%
}
%>
<%-- condition code --%>
<%-- EOF:REGION (CONDITIONAL) REGION1 --%>




<%-- SOF:REGION (CONDITIONAL) REGION2 --%>
<%-- condition code --%>
<%
if( local_Solicitud instanceof SolicitudCorreccion ) {
gov.sir.core.negocio.modelo.WebDocumento webDocumento = null;
if(webAnotacion!=null){
	webDocumento = webAnotacion.getDocumento();
	String sFecha = DateFormatUtil.format(webAnotacion.getFechaRadicacion());
	
	session.setAttribute(CSolicitudRegistro.NUMERO_RADICACION, webAnotacion.getNumeroRadicacion());
	session.setAttribute(CSolicitudRegistro.CALENDAR2, sFecha);
}

if(webDocumento!=null){
	String sFecha = DateFormatUtil.format(webDocumento.getFecha());

	session.setAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO ,webDocumento.getIdTipoDocumento());
	session.setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO ,webDocumento.getIdTipoDocumento());
	session.setAttribute(CSolicitudRegistro.ID_ENCABEZADO ,webDocumento.getNumero());
	session.setAttribute(CSolicitudRegistro.CALENDAR ,sFecha);
	session.setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL, webDocumento.getOficinaInternacional());	
}
OficinaOrigen oficinaOrigen = (OficinaOrigen)session.getAttribute(WebKeys.OFICINA_ORIGEN);
if(oficinaOrigen!=null){
      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,oficinaOrigen.getVereda().getMunicipio().getNombre());
      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,oficinaOrigen.getVereda().getMunicipio().getIdMunicipio());      
      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,oficinaOrigen.getVereda().getMunicipio().getDepartamento().getNombre());
      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,oficinaOrigen.getVereda().getMunicipio().getDepartamento().getIdDepartamento());      
      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,oficinaOrigen.getVereda().getNombre());
      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,oficinaOrigen.getVereda().getIdVereda());      

      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,oficinaOrigen.getTipoOficina().getNombre());
      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,oficinaOrigen.getIdOficinaOrigen());
      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,oficinaOrigen.getNombre());
       /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
      request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION,oficinaOrigen.getVersion().toString());
}
%>
<%-- condition code --%>
<!-- DATOS DEL DOCUMENTOS-->
<table width="100%" class="camposform">
	<tr>
		<td width="20"><img	src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		<td>Datos Documento</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td>
			<table width="100%" class="camposform">
				<tr>
					<td>Tipo</td>
					<td><% try {
 		                        textHelper.setNombre(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setFuncion("onchange=\"javascript:cambiarValorTipoDocumento(this.value);\"");
                  			    textHelper.setId(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
								textHelper.render(request,out);
								textHelper.setFuncion("");
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
					</td>
					<td><% try {
								String stringTipoEncabezado = (String) session.getAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
								ListaElementoHelper tipoEncabezado= new ListaElementoHelper();

	 		                    List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
                  			    tipoEncabezado.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);
								tipoEncabezado.setOrdenar(false);
                   				tipoEncabezado.setTipos(tiposDoc);
                  			    tipoEncabezado.setNombre(CSolicitudRegistro.TIPO_ENCABEZADO);
								tipoEncabezado.setCssClase("camposformtext");
                  			    tipoEncabezado.setId(CSolicitudRegistro.TIPO_ENCABEZADO);
	              			    tipoEncabezado.setFuncion("onchange=getElementById('ID_TIPO_ENCABEZADO').value=this.value;");
								tipoEncabezado.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
					</td>
					<script>
                       	document.getElementById('ID_TIPO_ENCABEZADO').value=document.getElementById('TIPO_ENCABEZADO').value;
                       	if(document.getElementById('ID_TIPO_ENCABEZADO').value=='SIN_SELECCIONAR'){
                       		document.getElementById('ID_TIPO_ENCABEZADO').value='';
                       	}
                       </script>
					<td>N&uacute;mero</td>
					<td><% try {
 		                       textHelper.setNombre(CSolicitudRegistro.ID_ENCABEZADO);
							textHelper.setCssClase("camposformtext");
                  		    textHelper.setId(CSolicitudRegistro.ID_ENCABEZADO);
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
					</td>
					<td>Fecha</td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><% try {
				 	                        textHelper.setNombre(CSolicitudRegistro.CALENDAR);
											textHelper.setCssClase("camposformtext");
				               			    textHelper.setId(CSolicitudRegistro.CALENDAR);
				               			    textHelper.setFuncion(" onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR+"');\" "
                                                                            + " onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR+"')\" "
                                                                            + "onBlur=\"javascript:validarFecha()\"" );
											textHelper.render(request,out);
										}
											catch(HelperException  re){
											out.println("ERROR " + re.getMessage());
										}
									%>
								</td>
								<td>
									<a	href="javascript:NewCal('calendar','ddmmmyyyy',true,24)">
										<img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
										alt="Fecha" width="16" height="21" border="0"
										onClick="javascript:Valores('<%=request.getContextPath()%>')">
									</a>
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr></tr>
			</table>
		</td>

	</tr>

	<tr>
		<td>
			<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15">
		</td>
		<td>Oficina de Procedencia</td>
	</tr>

	<!-- EL HELPER DE OFICINA EMPIEZA ACA -->
	<jsp:include page="HELPER_OFICINAS.jsp" flush="true" />
	<!-- EL HELPER DE OFICINA TERMINA ACA -->
	<tr>
		<td width="20"><img	src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		<td>Datos Anotación</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<table width="100%" class="camposform">
				<tr>
					<td>Número de radicación&nbsp;
					<% try {
 		                    textHelper.setNombre(CSolicitudRegistro.NUMERO_RADICACION);
							textHelper.setCssClase("camposformtext");
                  		    textHelper.setId(CSolicitudRegistro.NUMERO_RADICACION);
                  		    textHelper.setFuncion("");
							textHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
					</td>
					
					<td>Fecha de la anotaci&oacute;n&nbsp;
						<% try { 
	 	                        textHelper.setNombre(CSolicitudRegistro.CALENDAR2); 
								textHelper.setCssClase("camposformtext"); 
	               			    textHelper.setId(CSolicitudRegistro.CALENDAR2); 
	               			    textHelper.setFuncion("onBlur=\"javascript:validarFecha()\"" ); 
								textHelper.render(request,out); 
							} 
								catch(HelperException  re){ 
								out.println("ERROR " + re.getMessage()); 
							} 
						%>
						<a	href="javascript:NewCal('calendar2','ddmmmyyyy',true,24)">
							<img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
							alt="Fecha" width="16" height="21" border="0"
							onClick="javascript:Valores('<%=request.getContextPath()%>')">
						</a>
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- FIN DATOS DEL DOCUMENTOS-->
<%-- condition code --%>
<%
}
%>
<%-- condition code --%>
<%-- EOF:REGION (CONDITIONAL) REGION2 --%>




























              <br />
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

                              	Solicitud sol=(Solicitud)turno.getSolicitud();
                              	Liquidacion liq=(Liquidacion)solicitud.getLiquidaciones().get(0);
                              	Pago pago=(Pago)liq.getPago();
                              	Date fechaPago=liq.getPago().getFecha();
                              	fecha.setDate(fechaPago);
								fecha.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
								fecha.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
								catch(Exception e){
								out.println("ERROR " + e.getMessage());
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


					<td width="150">Naturaleza Jur&iacute;dica </td>
					<td width="40" align="justify">ID
					</td>
					<td width="40">
					<%
					try {
							textHelper.setTipo("text");
							textHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
							textHelper.setSize("5");
							textHelper.setFuncion("onBlur=\"cambiarAccionAndSendTipoTarifa('"+AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_SEGREGACION+"')\"");
							textHelper.setEditable(true);
							textHelper.render(request,out);
							textHelper.setFuncion("");
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
                                                                    * @Descripcion: se agregan nuevos campos a el formulario
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
								<!--<td><a href="javascript:juridica('seleccionar.naturaleza.juridica.view?cancelacion=false&calificacion=true','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a></td>-->
								<td><a href="javascript:juridica('seleccionar-naturaleza-juridica.do?cancelacion=false&calificacion=true&<%= WebKeys.ACCION %>=<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','<%=CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION%>','width=800,height=350,menubar=no')"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" alt="Permite seleccionar la naturaleza juridica" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a></td>
							</tr>
						</table>
                                                        
                                        </td>
                                        
                                        <td>
                                            <% 
                                                String validarModalidad = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                                                boolean activarModalidad = false;
                                                if(validarModalidad != null && (validarModalidad.equals("0125") || validarModalidad.equals("0126"))){
                                                out.write("Modalidad");
                                                activarModalidad = true; 
                                                }
                                            %>
                                        </td>
                                  <td> 
                                      <%
                                          try { 
                                              if(activarModalidad){
                                        tipoModalidadHelper.setNombre(CFolio.ANOTACION_MODALIDAD); 
                                        tipoModalidadHelper.setId(CFolio.ANOTACION_MODALIDAD);
                                        tipoModalidadHelper.render(request, out);
                                              }
                                          }catch(HelperException re){ 
									out.println("ERROR " + re.getMessage()); 
								}%>
                                  </td>        
                                  
						<td width="50">Valor</td>
					<td width="150">
						<%try {
							textHelper.setTipo("text");
							textHelper.setNombre(CFolio.ANOTACION_VALOR_ESPECIFICACION);
							textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
							textHelper.setCssClase("camposformtext");
							textHelper.setId(CFolio.ANOTACION_VALOR_ESPECIFICACION);
							textHelper.setEditable(true);
							textHelper.setSize("20");
							textHelper.render(request,out);
							textHelper.setFuncion("");
						}catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						%>
					</td>
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

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td colspan='5'>Copiar comentario en los folios segregados ?</td>
                </tr>
                <tr>
                  <td width="20">&nbsp;</td>
                  <td width="20">
					<%
							gov.sir.core.web.helpers.comun.RadioHelper radio = new RadioHelper();

							if(webAnotacion!=null){
								long copiaAnotacionSegregadas  = webAnotacion.getCopiaComentario();
								boolean copiarComentarioAnotacion = false;
								if(copiaAnotacionSegregadas==1){
									copiarComentarioAnotacion = true;
								}							
								if(copiarComentarioAnotacion){
									session.setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION , CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION);
								}else{
									session.setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION , CAnotacion.NO_GUARDAR_COMENTARIO_ANOTACION);
								}
							}

 		                    String defecto = (String)request.getSession().getAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
 		                    if(defecto==null){
 		                    	request.getSession().setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION,CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION);
 		                    }

							try {
 		                        radio.setNombre(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
                  			    radio.setId(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
                  			    radio.setValordefecto(CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION);
							    radio.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
					%>
 				  </td>
                  <td width="60"> Si </td>
                  <td width="20">
							<% try {
 		                        radio.setNombre(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
                  			    radio.setId(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
                  			    radio.setValordefecto(CAnotacion.NO_GUARDAR_COMENTARIO_ANOTACION);
							    radio.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
						 %>
                  </td>
                  <td width="60">No</td>
                  <td width="70%">&nbsp;</td>
                </tr>
              </table>

              <br>
              <hr class="linehorizontal">

          <%
          	try {
				List ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
				if(ciudadanos == null) {
					
					ciudadanos = new java.util.ArrayList();

					if(webAnotacion!=null){
					
						List webCiudadanos = webAnotacion.getCiudadanos();
						
						if(webCiudadanos!=null && !webCiudadanos.isEmpty()){
						
							java.util.Iterator itCiud = webCiudadanos.iterator();
							
							while(itCiud.hasNext()){
							
								gov.sir.core.negocio.modelo.WebCiudadano webCiudadano =
								(gov.sir.core.negocio.modelo.WebCiudadano)itCiud.next();
							
								gov.sir.core.negocio.modelo.AnotacionCiudadano anotacionCiudadano = new gov.sir.core.negocio.modelo.AnotacionCiudadano();
								gov.sir.core.negocio.modelo.Ciudadano ciudadano = new gov.sir.core.negocio.modelo.Ciudadano();

								ciudadano.setApellido1(webCiudadano.getApellido1());
								if (webCiudadano.getApellido2()!=null)
								  ciudadano.setApellido2(webCiudadano.getApellido2());
				
								ciudadano.setNombre(webCiudadano.getNombre());
				
								if (webCiudadano.getNumDocumento()!=null)
									ciudadano.setDocumento(webCiudadano.getNumDocumento());
				
				           		//Se setea el circulo del ciudadano
				        		gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
				        		ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);
				
								ciudadano.setTipoDoc(webCiudadano.getTipoDocumento());
								anotacionCiudadano.setCiudadano(ciudadano);
								anotacionCiudadano.setRolPersona(webCiudadano.getTipoIntervencion());
								anotacionCiudadano.setParticipacion(webCiudadano.getPorcentaje());
								
								String marcaPropietario = webCiudadano.getPropietario();
								
								int imarca = 0;
								
								try{
									imarca = new Integer(marcaPropietario).intValue();								
								}catch(Exception e){}

								if (imarca==0){
								  anotacionCiudadano.setMarcaPropietario(0);
								}else if(imarca==1){
								  anotacionCiudadano.setMarcaPropietario(1);
								}else if(imarca==2){
								  anotacionCiudadano.setMarcaPropietario(2);
								}	
				
								ciudadanos.add(anotacionCiudadano);
														
							
							}
						
							session.setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION ,ciudadanos);	
						
						}
					
					}

				}
				
	variosCiudadanosHelper.setPropertiesHandly(numFilas,tiposIDNatural,tiposPersona,tiposSexo,tiposIDJuridica,AWSegregacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION,
																AWSegregacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION,
																AWSegregacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION,
																AWSegregacion.ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION,
																ciudadanos, AWSegregacion.VALIDAR_CIUDADANO_SEGREGACION, "SEGREGACION");				
	variosCiudadanosHelper.setAccionUltimosPropietarios(AWCalificacion.GET_ULTIMOS_PROPIETARIOS);
	variosCiudadanosHelper.setMostrarBotonConsultaPropietario(true);
	request.getSession().setAttribute("paramVistaAnterior","ANOTACION_SEGREGACION_REGISTRO");
				
				variosCiudadanosHelper.render(request,out);
		  	}
			catch(HelperException re) {
				out.println("ERROR " + re.getMessage());
				re.printStackTrace();
			}
		  %>

              </td>
          </tr>
        </table>
        <br>
		<table width="100%" class="camposform">
          <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
            <!--@author: Guillermo Cabrera
                @change: SE ADICIONA EL CAMPO ES SEGREGACION.
                MANTIS: 1726
			-->
            <td width="150"><input type="hidden" name="<%=WebKeys.CAMPO_ES_SEGREGACION%>" id="<%=WebKeys.CAMPO_ES_SEGREGACION%>" value="FALSE" /><a href="javascript:esSegregacion();"><img src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" width="139" height="21" border="0"></a></td>
			<!--SI SE QUIERE LA OPCIÓN DE SELECCIONAR LA ANOTACIÓN A PARTIR DE UNA ANOTACIÓN TEMPORAL SE DEBE DESELECCIONAR LA SIGUIENTE LINEA.
				LA FUNNCIONALIDAD DE ESCOGER DICHA ANOTACIÓN YA EXISTE.
			-->
			<%--<td width="150"><a href="javascript:cambiarAccion('<%=AWSegregacion.SEGREGACION_SIN_ANOTACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_saltar_pagina.gif" name="Folio" width="150" height="21" border="0" id="Folio"></a></td> --%>
            <td width="150">
              <%--
              <%

              String tab= (String)session.getAttribute(gov.sir.core.negocio.modelo.constantes.CTabs.TAB);

              String local_ControlNavegacionTarget;
              String local_ControlNavegacionTargetTab;

              local_ControlNavegacionTarget    = (session.getAttribute(gov.sir.core.web.WebKeys.VISTA_ORIGINADORA)!=null)?((String)session.getAttribute(gov.sir.core.web.WebKeys.VISTA_ORIGINADORA)):"procesos.view";
              local_ControlNavegacionTargetTab = tab!=null?tab:"1";

              %>

              <a href="<%=local_ControlNavegacionTarget %>">
                <img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio" alt="[]" />
              </a>
              <input type='hidden' name='<%=gov.sir.core.negocio.modelo.constantes.CTabs.TAB%>' value="<%=local_ControlNavegacionTargetTab%>" />

              --%>


			<%-- SOF:SCRIPT-VARS --%>
			<script type="text/javascript">
			   var CANCELAR_SEGREGACION = "<%= AWSegregacion.CANCELAR_SEGREGACION %>";
			   var ELIMINAR_SEGREGACION = "<%= AWSegregacion.ELIMINAR_SEGREGACION %>";
			   function eliminarSegregacion(){
			      if(confirm('¿Desea eliminar la segregación en curso?')){
			         cambiarAccion( ELIMINAR_SEGREGACION );
			      }
			   }				   
			</script>
			<%-- EOF:SCRIPT-VARS --%>

              <%-- SOF:BUTTON --%>
                <a href="javascript:cambiarAccion( CANCELAR_SEGREGACION );">
                 <img alt="Salir de la segregación en curso" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
                </a>
              <%-- EOF:BUTTON --%>

              <!--ELIMINAR SEGREGACIÓN EN CURSO-->
              <td width="150">
                <a href="javascript:eliminarSegregacion();">
                 <img alt="Eliminar segregación en curso" src="<%=request.getContextPath()%>/jsp/images/btn_eliminar_segregacion.gif" name="Folio" width="180" height="21" border="0" id="Folio"  />
                </a>
          	  </td>        


            </td>
            <td>&nbsp;</td>
            <td width="380">&nbsp;</td>
          </tr>
        </table>

        <br>

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
  </form>
</table>
