<%@page import="gov.sir.core.negocio.modelo.SolicitudCorreccion"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.Direccion"%>
<%@page import="gov.sir.core.negocio.modelo.Anotacion"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.WebFolioHeredado"%>
<%@page import="gov.sir.core.negocio.modelo.WebEnglobe"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio" %>
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %>
<%@page import="gov.sir.core.web.acciones.registro.AWEnglobe"%>
<%@page import="gov.sir.core.negocio.modelo.WebFolioNuevo"%>
<%@page import="gov.sir.core.negocio.modelo.WebAnotacion"%>
<%@page import="gov.sir.core.negocio.modelo.WebDocumento"%>
<%@page import="gov.sir.core.negocio.modelo.NaturalezaJuridica"%>
<%@page import="gov.sir.core.negocio.modelo.WebCiudadano"%>
<%@page import="gov.sir.core.negocio.modelo.AnotacionCiudadano"%>
<%@page import="gov.sir.core.negocio.modelo.Ciudadano"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.OficinaOrigen"%>
<%@page import="gov.sir.core.negocio.modelo.TipoDocumento"%>
<%@page import="gov.sir.core.negocio.modelo.Documento"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica"%>

<%
	Turno local_Turno;
	Solicitud local_Solicitud;
	local_Turno = (Turno)session.getAttribute( WebKeys.TURNO );
	local_Solicitud = (Solicitud)local_Turno.getSolicitud();
	Circulo circulo= (Circulo)session.getAttribute( WebKeys.CIRCULO);	
	
	Turno turno = local_Turno;
	Solicitud solicitud = local_Solicitud;

 	//SE OBTIENEN LOS FOLIOS A ENGLOBAR
	TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();

	WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
	if (webEnglobe == null) {
		webEnglobe = new WebEnglobe();
	}
	
	List matriculas = webEnglobe.getFoliosHeredados();
	List idmatriculas = new ArrayList();
	Iterator it = matriculas.iterator();
	while(it.hasNext()){
		WebFolioHeredado folio=(WebFolioHeredado)it.next();
		String temp=(String) folio.getIdMatricula();
		idmatriculas.add(temp);
	}

	//SE OBTIENE EL FOLIO DERIVADO Y LA NUEVA DIRECCIÓN
	WebFolioNuevo folioDerivado = webEnglobe.getFolioNuevo();
	if(folioDerivado==null){
		folioDerivado = new WebFolioNuevo();
	}

	List direcciones = (List)session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
	if(direcciones==null){
		direcciones = new ArrayList();
	}
	Direccion direccion = null;
	if(direcciones.size()>0){
		direccion = (Direccion)direcciones.get((direcciones.size()-1));
	}
	if(direccion==null){
		direccion = new Direccion();
	}

	//SE OBTIENE LA ANOTACIÓN QUE DARÁ ORIGEN AL ENGLOBE
	//SE RECUPERA LA ANOTACIÓN ORIGINAL
	Anotacion anotacion = new Anotacion();
	WebAnotacion webAnotacion = webEnglobe.getAnotacion(); 
	WebDocumento webDocumento = webAnotacion.getDocumento();

	Documento documento = new Documento();
	if(webAnotacion.getIdDocumento()!=null && !webAnotacion.getIdDocumento().equals("")){
		documento.setIdDocumento(webAnotacion.getIdDocumento());
		
		if( local_Solicitud instanceof SolicitudRegistro ){
	        SolicitudRegistro solicitudReg =(SolicitudRegistro)local_Solicitud;
	        if(solicitudReg.getDocumento()!=null){
	        	documento = solicitudReg.getDocumento();
	        }	
	    }	
	}else{
		documento.setComentario(webDocumento.getComentario());
		documento.setFecha(webDocumento.getFecha());
		documento.setNumero(webDocumento.getNumero());
		documento.setOficinaInternacional(webDocumento.getOficinaInternacional());
		OficinaOrigen oficinaOrigen = (OficinaOrigen)session.getAttribute(WebKeys.OFICINA_ORIGEN);
		documento.setOficinaOrigen(oficinaOrigen);
		TipoDocumento tipoDocumento = new TipoDocumento();
		tipoDocumento.setIdTipoDocumento(webDocumento.getIdTipoDocumento());
		documento.setTipoDocumento(tipoDocumento);
	}
	
	NaturalezaJuridica naturaleza = new NaturalezaJuridica();
	String descNaturaleza = determinarDescripcionNaturaleza(webAnotacion.getIdNaturalezaJuridica(), request);
	naturaleza.setIdNaturalezaJuridica(webAnotacion.getIdNaturalezaJuridica());
	if(descNaturaleza!=null){
		naturaleza.setNombre(descNaturaleza);
	}else{
		naturaleza.setNombre("");
	}


	anotacion.setDocumento(documento);
	anotacion.setComentario(webAnotacion.getComentario());
	anotacion.setValor(new Double(webAnotacion.getValor()).doubleValue());
        anotacion.setModalidad(webAnotacion.getModalidad());
	anotacion.setNumRadicacion(webAnotacion.getNumeroRadicacion());
	anotacion.setIdWorkflow(turno.getIdWorkflow());
	anotacion.setFechaRadicacion(webAnotacion.getFechaRadicacion());		
	anotacion.setTemporal(true);
	anotacion.setNaturalezaJuridica(naturaleza);
	//anotacion.setOrden(sOrden);
	
	List webCiudadanos = webAnotacion.getCiudadanos();
	Iterator itCiudadanos = webCiudadanos.iterator();
	while(itCiudadanos.hasNext()){
		WebCiudadano webCiudadano = (WebCiudadano)itCiudadanos.next();
		AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
		Ciudadano ciudadano = new Ciudadano();
                ciudadano.setTipoPersona(webCiudadano.getTipoPersona());
                ciudadano.setSexo(webCiudadano.getSexo());
		ciudadano.setApellido1(webCiudadano.getApellido1());
		ciudadano.setApellido2(webCiudadano.getApellido2());
		ciudadano.setTipoDoc(webCiudadano.getTipoDocumento());
		ciudadano.setDocumento(webCiudadano.getNumDocumento());
		ciudadano.setNombre(webCiudadano.getNombre());
		ciudadano.setSolicitante(false);
		ciudadano.setNombre(webCiudadano.getNombre());
		ciudadano.setIdCirculo(circulo.getIdCirculo());
		anotacionCiudadano.setCiudadano(ciudadano);
		anotacionCiudadano.setParticipacion(webCiudadano.getPorcentaje());
		anotacionCiudadano.setRolPersona(webCiudadano.getTipoIntervencion());
		anotacionCiudadano.setMarcaPropietario(new Integer(webCiudadano.getPropietario()).intValue());
		anotacion.addAnotacionesCiudadano(anotacionCiudadano);
	}	

	gov.sir.core.web.helpers.registro.VerAnotacionHelper helper = new gov.sir.core.web.helpers.registro.VerAnotacionHelper();
	helper.setAnotacion(anotacion);


	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idmatriculas);

   %>
   
<%!
	public String determinarDescripcionNaturaleza(String codigo , HttpServletRequest req){
	
		String rta = null;
		
		if(codigo!=null){
			
		    List grupoNaturalezas = (List) req.getSession().getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);		
	
			GrupoNaturalezaJuridica grupo = new GrupoNaturalezaJuridica();
			NaturalezaJuridica nat;
			boolean found = false;
			for(java.util.Iterator it = grupoNaturalezas.iterator(); it.hasNext() && !found;){
				grupo = (GrupoNaturalezaJuridica)it.next();
				for(java.util.Iterator it2=grupo.getNaturalezaJuridicas().iterator(); it2.hasNext() && !found;){
					nat = (NaturalezaJuridica)it2.next();
					
					if(nat.getIdNaturalezaJuridica().equals(codigo)){
					
						if(!nat.isHabilitadoCalificacion()){
							found = true;
							break;
						}else{
							rta = nat.getNombre();
							found = true;
							break;
						} 
					
					}
				}
			}
		}
				
		return rta;
	
	}

%>     
   
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
       document.CALIFICACION.ACCION.value = text;
       document.CALIFICACION.submit();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">ASISTENTE ENGLOBE - PASO 6 - CONFIRMACIÓN</td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">DATOS DEL ENGLOBE</td>
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





<!-- static resources -->
<%-- --%>

<%-- Bug 3563 --%>


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

     if( formObject == null ) {
       alert( 'form-undefined' );
	   return;
     }
     formObject.action = formAction;
   }

   LocalForm.prototype.setValue = function( elementName, elementValue ) {
	 formObject = findObj( this.formName );

     if( formObject == null )
	   return;

     eval( "formObject."+ elementName + ".value" + "=" + "elementValue" );
   }

</script>
<script type="text/javascript">
	// local form dependant resources

	var actionField = "<%=WebKeys.ACCION%>";
	var PAGE_REGION__FINISHANOTACIONENGLOBE_ACTION = "<%= AWModificarFolio.PAGE_REGION__FINISHANOTACIONENGLOBE_ACTION %>";
</script>

<script type="text/javascript">

   function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

   function js_OnEvent2( formName, actionValue, handlerUri ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.setFormAction( handlerUri );
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


<%-- --%>
















            <form action="englobe.do" method="post" name="CALIFICACION" id="CALIFICACION" >
            <input type="hidden" name="ACCION" value="ESCOGER_FOLIOS">
            <input type="hidden" name="POSSCROLL" id="POSSCROLL" value="<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">
                    Folios a englobar </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

              <table width="100%">
                <tr>
                  <td>

                  	 <%
                  	 	//COLOCA LA LISTA DE FOLIOS ASOCIADOS A LA SOLICITUD.
                  	 	try {
	                      tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
                          //tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
	               		  tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
	                  %>


                  </td>
                </tr>
              </table>



              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">
                    Datos del nuevo folio </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>



           		<table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="25%" >&nbsp;&nbsp;&nbsp;Nombre o número del inmueble: </td>
                  <td width="35%" class="campositem">
 				  <%=folioDerivado.getNombre()!=null?folioDerivado.getNombre():""%>
                  </td>
                  <td colspan="2">
                      <table class="camposformnoborder">
                  <td>&nbsp;&nbsp;&nbsp;Area del Inmueble: </td>
                  <td>&nbsp;&nbsp;&nbsp;Hectareas </td>
                  <td width="100" class="campositem">
 				  <%=folioDerivado.getHectareas()!=null?folioDerivado.getHectareas():"&nbsp;"%>
                  </td>
                  <td>&nbsp;&nbsp;&nbsp;Metros<sup>2</sup> </td>
                  <td width="100" class="campositem">
 				  <%=folioDerivado.getMetros()!=null?folioDerivado.getMetros():"&nbsp;"%>
                  </td>
                  <td>&nbsp;&nbsp;&nbsp;Centimetros<sup>2</sup> </td>
                  <td width="100" class="campositem">
 				  <%=folioDerivado.getCentimetros()!=null?folioDerivado.getCentimetros():"&nbsp;"%>
                  </td>
                      </table>
                  </td>
                </tr>
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="20%" >&nbsp;&nbsp;&nbsp;Complementación: </td>
                  <td width="80%" colspan="3" class="campositem">&nbsp;
 				  <%=folioDerivado.getDescripcion()!=null?folioDerivado.getDescripcion():""%>
                  </td>
                </tr>
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="20%" >&nbsp;&nbsp;&nbsp;Dirección: </td>
                  <td width="80%" colspan="3" class="campositem">
				  	<%=direccion!=null?direccion.toString():""%>&nbsp;
                  </td>
                </tr>

		    </table>

            <br>
            <br>
        <%
		try{

			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
		%>

     <!--
       * @author      :   Julio Alcázar Rivas
       * @change      :   Se agrega el TextHelper para ingresar la salvedad
       * Caso Mantis  :   04131
    !-->

     <%
      if( local_Solicitud instanceof SolicitudCorreccion ) {
     %>
     <span class="bgnsub" style="background:" >Salvedad </span>
     <table width="100%" class="camposform">
     <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td>Salvedad</td>
          </tr>
              <tr>
                 <td width="20">&nbsp;</td>
                 <td>

                 <% // id

                 try {
                   TextHelper local_TextHelper;

                   local_TextHelper = new TextHelper();
                   local_TextHelper.setNombre(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID);
                   local_TextHelper.setId(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID);
                   local_TextHelper.setTipo( "hidden" );
                   local_TextHelper.setCssClase("camposformtext");
                   local_TextHelper.render( request, out );

                 }
                 catch( HelperException re ) {
		  out.println( "ERROR " + re.getMessage() );
                 }

                 try {
                    TextAreaHelper textAreaHelper = new TextAreaHelper();
                    textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=2' ");
                    textAreaHelper.setNombre(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
                    textAreaHelper.setId(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
 		    textAreaHelper.setCols( "130" );
 		    textAreaHelper.setRows( "2" );
                    textAreaHelper.setReadOnly(false);
                    textAreaHelper.setCssClase("camposformtext");
		    textAreaHelper.render(request,out);
		 }
                  catch( HelperException re ){
	             out.println("ERROR " + re.getMessage());
                  }
                 %>

                </td>
           </tr>
     </table>
     <%
     }
     %>


              <hr class="linehorizontal">
              <br>
              <table width="100%" class="camposform">

                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="150">



<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- SOF:REGION TYPE="CONDITIONAL" --%>

                    <a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWEnglobe.ENGLOBAR%>')">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_terminar_englobe.gif" name="Folio" width="180" height="21" border="0" id="Folio" />
                    </a>



<%-- (condition) --%>
<%-- EOF:REGION--%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>





                  </td>

					<!--BOTON REGRESAR-->                 
                    <TD width="150">
	                <a href="registro.englobar.informacion.lote.view?POSSCROLL=<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
	                 	<img alt="Regresar" src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
	                </a>
					</TD>
					
					<!--BOTON CANCELAR-->   
                  <td width="150">
					<%-- SOF:SCRIPT-VARS --%>
					<script type="text/javascript">
					   var CANCELAR_ENGLOBE = "<%= AWEnglobe.CANCELAR_ENGLOBE %>";
					   var ELIMINAR_ENGLOBE = "<%= AWEnglobe.ELIMINAR_ENGLOBE %>";
					   function eliminarEnglobe(){
					      if(confirm('¿Desea eliminar el englobe en curso?')){
					         cambiarAccion( ELIMINAR_ENGLOBE );
					      }
					   }					   
					</script>
					<%-- EOF:SCRIPT-VARS --%>
		              <%-- SOF:BUTTON --%>
		                <a href="javascript:cambiarAccion( CANCELAR_ENGLOBE );">
		                 <img alt="Salir del englobe en curso" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
		                </a>
		              <%-- EOF:BUTTON --%>
                  </td>
                  
	              <!--ELIMINAR ENGLOBE EN CURSO-->
	              <td width="150">
	                <a href="javascript:eliminarEnglobe();">
	                 <img alt="Eliminar englobe en curso" src="<%=request.getContextPath()%>/jsp/images/btn_eliminar_englobe.gif" name="Folio" width="180" height="21" border="0" id="Folio"  />
	                </a>
              	  </td>               


    			  <td>&nbsp;</td>
    			  <td>&nbsp;</td>
    			  <td>&nbsp;</td>
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
