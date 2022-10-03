<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.registro.AnotacionCancelacionHelper"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro"%>
<%@page import="gov.sir.core.web.WebKeys"%>

<%
	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	Folio f = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
	String vistaActual;
	String nomFormaCancelacion="FORMA_CANCELACION";
	String nomOrdenCancelada="NOM_ORDEN_CANCELADA";

	TextHelper textHelperDoc = new TextHelper();

	AnotacionCancelacionHelper anotacionesModificacionHelper = new AnotacionCancelacionHelper();
	request.getSession().setAttribute(MostrarFechaHelper.CUADRO_DE_TEXTO, new Boolean(true));
	anotacionesModificacionHelper.setEditarFecha(true);
	anotacionesModificacionHelper.setEditarNumRadicacion(true);
	anotacionesModificacionHelper.setAccionorigen("crearfolioadministracionoficio.do");
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	List grupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
	session.setAttribute("listanat",grupoNaturalezas);

	String posicion="";
	String idAnotacionCancelacion="";
	String temp;
	temp=(String)session.getAttribute("ESCOGER_ANOTACION_CANCELACION");
	if(temp!=null){
		idAnotacionCancelacion=temp;
	}


	//se mira si ya esta seteado llavesPaginador
	LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
	LLaveMostrarFolioHelper lla=null;
	if(llaves==null){
		//se crea el objeto llavesPaginador y settear sus valores;
		llaves= new LLavesMostrarFolioHelper();
		lla= new LLaveMostrarFolioHelper();
		lla.setNombrePaginador("NOMBRE_PAGINADOR_CALIFICACION");
		lla.setNombreResultado("NOMBRE_RESULTADO_CALIFICACION");
		lla.setNombreNumPagina("NUM_PAGINA_ACTUAL_CALIFICACION");
		llaves.addLLave(lla);
		request.getSession().setAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO, llaves);
	}
	if(lla==null){
		lla=llaves.getLLave("NOMBRE_RESULTADO_CALIFICACION", "NOMBRE_PAGINADOR_CALIFICACION");
	}

	String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
	vistaActual= ultimaVista;

   %>
   <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
   <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
   <script type="text/javascript">
   function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.CANCELAR.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
	}
	function cambiarAccion(text) {
       document.CANCELAR.ACCION.value = text;
       //document.CANCELAR.ESCOGER_ANOTACION_CANCELACION.value = document.<%=nomFormaCancelacion%>.<%=nomOrdenCancelada%>.value;
       document.CANCELAR.submit();
     }

	 function cargarAnotaciones(text) {
       document.CANCELAR.MATRICULA.value = text;
       cambiarAccion('CARGAR_ANOTACIONES');
       }

     function grabarTemp(text) {
       document.CANCELAR.MATRICULA.value = text;
       cambiarAccion('GRABACION_TEMPORAL_ANOTACION_CANCELACION');
       }

       function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function oficinas(nombre,valor,dimensiones)
{
	document.CANCELAR.ACCION.value='<%=AWCalificacion.PRESERVAR_INFO_CANCELACION%>';
	var idDepto = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
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

function juridica(nombre,valor,dimensiones)
{
	<%session.setAttribute("cancelar", "true"); %>
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
	document.CANCELAR.POSICION.value=pos;
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
	document.CANCELAR.<%=CAnotacionCiudadano.SECUENCIA %>.value = text;
	cambiarAccion('<%=AWCalificacion.REFRESCAR_CANCELACION%>');
}
function quitar(pos,accion) {
	document.CANCELAR.POSICION.value = pos;
	cambiarAccion(accion);
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












<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- para manejo de formularios a traves de js                   --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script type="text/javascript">
// Libraries: FindObject BrowserCompatible

// Example: obj = findObj("image1");
function findObj(theObj, theDoc)
{
  var p, i, foundObj;

  if(!theDoc) theDoc = document;
  if( (p = theObj.indexOf("?")) > 0 && parent.frames.length)
  {
    theDoc = parent.frames[theObj.substring(p+1)].document;
    theObj = theObj.substring(0,p);
  }
  if(!(foundObj = theDoc[theObj]) && theDoc.all) foundObj = theDoc.all[theObj];
  for (i=0; !foundObj && i < theDoc.forms.length; i++)
    foundObj = theDoc.forms[i][theObj];
  for(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
    foundObj = findObj(theObj,theDoc.layers[i].document);
  if(!foundObj && document.getElementById) foundObj = document.getElementById(theObj);

  return foundObj;
}

</script>

<script type="text/javascript">
	// local form manipulation

   function LocalForm( formName ) {
     this.formName = formName;
   }
   LocalForm.prototype = new LocalForm();
   LocalForm.prototype.constructor = LocalForm;
   // Form.prototype.superclass = Object;

   LocalForm.prototype.submitForm = function() {
	 formObject = findObj( this.formName );
     formObject.submit();
   }

   LocalForm.prototype.setFormAction = function( formAction ) {
	 formObject = findObj( this.formName );
     formObject.action = formAction;
   }

   LocalForm.prototype.setValue = function( elementName, elementValue ) {
	 formObject = findObj( this.formName );

     if( formObject == null )
	   return;

     eval( "formObject."+ elementName + ".value" + "=" + ((null==elementValue)?("''"):("elementValue")) );
   }

    // added -------------
   LocalForm.prototype.getFormObject = function() {
     var local_FormObject;
     local_FormObject = findObj( this.formName );
     return local_FormObject;
   }

   LocalForm.prototype.getValue = function( elementName ) {
	 formObject = findObj( this.formName );

     if( formObject == null )
	   return;

     var elementValue;

     eval( "elementValue" + "=" + "formObject."+ elementName + ".value" );
     // alert( "valor de " + elementName + " es " + elementValue )
     return elementValue;
   }

   LocalForm.prototype.formElementFocus = function( elementName ) {
     var local_FormObject;
     local_FormObject = findObj( this.formName );

     if( formObject == null )
	   return;

     eval( "formObject."+ elementName + ".focus()" );

   }

   // -------------------

</script>



<script type="text/javascript">
	// local form dependant resources
   var actionField = "<%=WebKeys.ACCION%>";

</script>

<script type="text/javascript">

  // Para validaciones de widgets tipo fecha
  var ITEM__PG_CREARANOTACIONCANCELACION_CSOLICITUDREGISTRO_CALENDAR1 = "<%= CSolicitudRegistro.CALENDAR  %>";
  var ITEM__PG_CREARANOTACIONCANCELACION_CSOLICITUDREGISTRO_CALENDAR2 = '<%= "ANOTACION_FECHA_RADICACION" %>';

</script>

<script type="text/javascript">
</script>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<script type="text/javascript">

   function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

   function js_OnEvent2( formName, actionValue, processorId ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setFormAction( processorId );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

   function js_OnEventConfirm( formName, actionValue, msg ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );

     if( confirm( msg) ) {

       htmlForm.setValue( actionField, actionValue );
       htmlForm.submitForm();
       return true;
     }
     return void(0);

   }

</script>





<script type="text/javascript">


  function js_DateFormatValidator( formName, fieldName ){


    var htmForm;
    htmForm = new LocalForm( formName );

    var formObject;
    formObject = htmForm.getFormObject();

    // alert("formObject "+formObject);


    if( ( null == formObject )
      || ( undefined == formObject ) ) {

        alert( "ERROR: No se encuentra la referencia al formulario (jsDateFormatValidator)" );
        return;
    }

    var fieldValue;
    fieldValue = htmForm.getValue( fieldName );

    // alert("fieldValue "+fieldValue);



    if( fieldValue.length > 0 ) {

      var index = fieldValue.lastIndexOf('/') + 1;

      if (index!=null){
        var fin = fieldValue.length;
        var texto=fieldValue.substring( index,fin );

        if( texto.length != 4 ){
          alert( 'Fecha incorrecta' );
          htmForm.setValue( fieldName, '' );
          htmForm.formElementFocus( fieldName );
        } // if

      } // if

    } // if

    /*

<%--

    if (document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value.length>0){
      var index=document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value.lastIndexOf('/')+1;
      if (index!=null){
        var fin=document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value.length;
        var texto=document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value.substring(index,fin);
        if (texto.length!=4){
                alert('Fecha incorrecta');
                document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.value='';
                document.CREAR.<%=CSolicitudRegistro.CALENDAR%>.focus();
        }
      }
    }

--%>
    */
  }


</script>


<script type="text/javascript">


  function validarFecha() {
    js_DateFormatValidator( 'CANCELAR', ITEM__PG_CREARANOTACIONCANCELACION_CSOLICITUDREGISTRO_CALENDAR2 );
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
  </tr>
  <tr>
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Cancelar Anotación</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Cancelaci&oacute;n</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
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


                <!--Se muestra el encabezado del documento, únicamente en el caso de un turno de Registro-->
               <%
			   String nomOficina= "";
			   boolean isInternacional=false;
			   boolean isComentarioAnotacion=false;

               if(true){


			    	if(f.getDocumento()!=null){

						if(f.getDocumento().getComentario()!=null){
							isComentarioAnotacion=true;
							nomOficina= f.getDocumento().getComentario();
						}
			    		if(f.getDocumento().getOficinaOrigen()!=null){

			    			if(f.getDocumento().getOficinaOrigen().getTipoOficina()!=null){
				    			if(f.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null){
					    			nomOficina=f.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
				    			}
			    			}
			    			if(f.getDocumento().getOficinaOrigen().getNombre()!=null){
			    				nomOficina= nomOficina + " " +f.getDocumento().getOficinaOrigen().getNombre();
			    			}
			    		}
			    		if(f.getDocumento().getOficinaInternacional()!=null){
			    			isInternacional=true;
			    			nomOficina= f.getDocumento().getOficinaInternacional();
			    		}
			    	}


				    session.setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO,f.getDocumento().getTipoDocumento().getNombre());
				    session.setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,f.getDocumento().getNumero());
				    session.setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO,f.getDocumento().getFecha());
				    if(isComentarioAnotacion){
				    	request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
				    }else if(isInternacional){
				    	request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
				    }else{
					    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,f.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre());
					    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,f.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());
					    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,f.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
					    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,f.getDocumento().getOficinaOrigen().getNumero());
					    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,nomOficina);
					    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_COD,f.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
					}

					//PREPARACION HELPERS
				    TextHelper tiposDocHelper = new TextHelper();
					TextHelper textHelper = new TextHelper();
					MostrarFechaHelper fecha = new MostrarFechaHelper();
	            %>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Datos B&aacute;sicos </td>
                </tr>
				<!--
                <tr>
                	<td>&nbsp;</td>
                	<td><table width="100%" class="camposform">
                		<tr>
                			<td width="15%">N&uacute;mero anotaci&oacute;n </td>
                			<td class="campositem" align="right" width="15%"> <%//pos%> </td>
                			<td>&nbsp;</td>
                		</tr>
                		</table>
                	</td>
                	<td>&nbsp;</td>
                </tr>
                -->
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">

                      <tr>
                        <td width="3%" align="right">Tipo</td>
                        <td class="campositem" align="left">
                        <%try {
		 		            tiposDocHelper.setId(gov.sir.core.negocio.modelo.constantes.CFolio.ANOTACION_TIPO_DOCUMENTO);
						    tiposDocHelper.setCssClase("camposformtext");
							tiposDocHelper.setNombre(CFolio.ANOTACION_TIPO_DOCUMENTO);
							tiposDocHelper.setEditable(false);
							tiposDocHelper.render(request,out);
						}catch(HelperException re){
						 	 out.println("ERROR " + re.getMessage());
						}
					 %>
                        </td>
                        <td width="20%">&nbsp;</td>
                        <td width="5%" align="right">N&uacute;mero</td>
                        <td class="campositem" align="left">
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
                        <td width="20%">&nbsp;</td>
                        <td width="3%" align="right">Fecha</td>
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
                  </table></td>
                </tr>
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Oficina de Procedencia </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
				 <%if(isComentarioAnotacion){%>
	                  <table width="100%" class="camposform">
                        <td width="17%">Oficina Origen</td>
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
					  <%
					  }else if(!isInternacional){%>
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


               <%
               }
               //FIN DE ENCABEZADO DEL DOCUMENTO PARA EL CASO DE SOLICITUDES DE RESGISTRO.
               %>

                <form action="crearfolioadministracionoficio.do" method="post" name="CANCELAR" id="CANCELAR">
                <input type="hidden" name="ACCION" value="">
                <input name="ESCOGER_ANOTACION_CANCELACION" id="ESCOGER_ANOTACION_CANCELACION" type="hidden" value="">
 				<input type="hidden" name="POSICION" value="">
 				<input type="hidden" name="<%=CAnotacionCiudadano.SECUENCIA%>" value="-1">
 				<input type="hidden" name="CAMBIO" value="">

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
														<td>
														<%
															try {
 		                        								textHelperDoc.setNombre(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			    								textHelperDoc.setCssClase("camposformtext");
                  			    								textHelperDoc.setFuncion("onchange=\"javascript:cambiarValorTipoDocumento(this.value);\"");
                  			    								textHelperDoc.setId(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
																textHelperDoc.render(request,out);
																textHelperDoc.setFuncion("");
															} catch(HelperException re) {
																out.println("ERROR " + re.getMessage());
															}
														%>
														</td>
														<td>
														<%
															try {
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
															} catch(HelperException re) {
																out.println("ERROR " + re.getMessage());
															}
														%>
														</td>
														<script>
                       										document.getElementById('ID_TIPO_ENCABEZADO').value=document.getElementById('ID_TIPO_ENCABEZADO').value;
                       										if(document.getElementById('ID_TIPO_ENCABEZADO').value=='SIN_SELECCIONAR'){
                       											document.getElementById('ID_TIPO_ENCABEZADO').value='';
                       										}
                       									</script>
														<td>N&uacute;mero</td>
														<td>
														<%
															try {
 		                       									textHelperDoc.setNombre(CSolicitudRegistro.ID_ENCABEZADO);
																textHelperDoc.setCssClase("camposformtext");
                  		    									textHelperDoc.setId(CSolicitudRegistro.ID_ENCABEZADO);
																textHelperDoc.render(request,out);
															} catch(HelperException re) {
																out.println("ERROR " + re.getMessage());
															}
														%>
														</td>
														<td>Fecha</td>
														<td>
															<table border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																		<%
																			try {
				 	                        									textHelperDoc.setNombre(CSolicitudRegistro.CALENDAR);
																	textHelperDoc.setCssClase("camposformtext");
				               			    									textHelperDoc.setId(CSolicitudRegistro.CALENDAR);
				               			    									//textHelperDoc.setFuncion("onBlur=\"javascript:validarFecha()\"" );
                                                                                                                                        textHelperDoc.setFuncion("onBlur=\"javascript:js_DateFormatValidator('CANCELAR',ITEM__PG_CREARANOTACIONCANCELACION_CSOLICITUDREGISTRO_CALENDAR1 )\"" );


																	textHelperDoc.render(request,out);
																			} catch(HelperException re) {
																				out.println("ERROR " + re.getMessage());
																			}
																		%>
																	</td>
																	<td>

									<a
									  href="javascript:NewCal('<%=CSolicitudRegistro.CALENDAR%>','ddmmmyyyy',true,24)"
									>
									  <img
									    src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
									    alt="Fecha"
									    width="16"
									    height="21"
									    border="0"
									    onClick="javascript:Valores('<%=request.getContextPath()%>')" />
									</a>

<%--


																		<a	href="javascript:NewCal('calendar','ddmmmyyyy',true,24)">
																			<img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
																				alt="Fecha" width="16" height="21" border="0"
																				onClick="javascript:Valores('<%=request.getContextPath()%>')">
																		</a>
--%>
																		&nbsp;
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15">
											</td>
											<td>Oficina de Procedencia</td>
										</tr>
										<%
										session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
										session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
										session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
										session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
										session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
										%>
										<!-- EL HELPER DE OFICINA EMPIEZA ACA -->
										<jsp:include page="../../registro/HELPER_OFICINAS.jsp" flush="true" />
										<!-- EL HELPER DE OFICINA TERMINA ACA -->
									</table>
									<!-- FIN DATOS DEL DOCUMENTOS-->




				<%try {
					//Liquidacion liq=(Liquidacion)turno.getSolicitud().getLiquidaciones().get(0);
					//Date fechaPago=liq.getPago().getFecha();
					Date fechaPago= null;
					if (fechaPago == null){
						fechaPago = new Date();
					}
					anotacionesModificacionHelper.setFormName("CANCELAR");
					anotacionesModificacionHelper.setFecha(fechaPago);
					anotacionesModificacionHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}%>

              <br>

              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><a href="javascript:cambiarAccion('CANCELAR_ANOTACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aprobar_cancelacion.gif" name="Folio" width="195" height="21" border="0" id="Folio"></a></td>
                  <td><a href="javascript:cambiarAccion('CANCELAR_CANCELACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
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

      </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
  </tr>
</table>
