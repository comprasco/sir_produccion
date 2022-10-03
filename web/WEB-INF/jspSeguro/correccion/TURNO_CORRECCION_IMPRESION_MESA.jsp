
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.acciones.certificado.AWCertificado" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoImprimible" %>
<%@page import="org.apache.commons.jxpath.JXPathContext" %>
<%
	MostrarAntiguoSistemaHelper MASHelper= new MostrarAntiguoSistemaHelper();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
	String ocultarTurno = request.getParameter(WebKeys.OCULTAR_TURNO);
	if(ocultarTurno == null){
		ocultarTurno = (String)session.getAttribute(WebKeys.OCULTAR_TURNO);
		if(ocultarTurno==null){
			ocultarTurno = "FALSE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_TURNO,ocultarTurno);
	}

	String ocultarFolio = request.getParameter(WebKeys.OCULTAR_FOLIO);
	if(ocultarFolio == null){
		ocultarFolio = (String)session.getAttribute(WebKeys.OCULTAR_FOLIO);
		if(ocultarFolio==null){
			ocultarFolio = "FALSE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_FOLIO,ocultarFolio);
	}


	String printspecial = request.getParameter(AWCertificado.PRINTESPECIAL);
	if(printspecial == null){
		printspecial = (String)session.getAttribute(AWCertificado.PRINTESPECIAL);
		if(printspecial==null){
			printspecial = "FALSE";
		}
	} else {
		session.setAttribute(AWCertificado.PRINTESPECIAL,printspecial);
	}

	NotasInformativasHelper helper = new NotasInformativasHelper();

	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = (Solicitud) turno.getSolicitud();

	Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);

	Turno turnoAnterior = solicitud.getTurnoAnterior();
	Ciudadano ciudadano = solicitud.getCiudadano();
	Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);

    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
    List folios=turno.getSolicitud().getSolicitudFolios();
    List matriculas = new ArrayList();

	Iterator it = folios.iterator();
    while(it.hasNext()){
    	SolicitudFolio sol = (SolicitudFolio)it.next();
    	if(sol.getFolio().getIdMatricula()!=null){
	    	matriculas.add(sol.getFolio().getIdMatricula());
	    }
    }
    session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION,matriculas);


%>

<script type="text/javascript">
function imprimir(accion, matricula){
	document.CORRECCION.<%=WebKeys.ACCION%>.value = accion;
	document.CORRECCION.<%=gov.sir.core.negocio.modelo.constantes.CFolio.ID_MATRICULA%>.value = matricula;
	document.CORRECCION.submit();
}
function cambiarAccion(accion){
	document.CORRECCION.<%=WebKeys.ACCION%>.value = accion;
	document.CORRECCION.submit();
}
function cambiarAccion1(accion){
	document.CORRECCION1.<%=WebKeys.ACCION%>.value = accion;
	document.CORRECCION1.submit();
}
function cambiarAccion2(accion){
	document.CORRECCION2.<%=WebKeys.ACCION%>.value = accion;
	document.CORRECCION2.submit();
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Correcciones</td>
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
    <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>
    <tr>
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turno</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            </tr>
        </table></td>
        <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table></td>
    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table border="0" align="right" cellpadding="0" cellspacing="2">
        <tr>
					<%if(ocultarTurno.equals("FALSE")){%>
        <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="TRUE">
            <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
        </form>

					<%}else{%>
        <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="FALSE">
            <td width="170" class="contenido">Haga click para maximizar</td>
            <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
        </form>
					<%}%>
        </tr>
    </table>
    
    
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>

    <td valign="top" bgcolor="#79849B" class="tdtablacentral">


    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif" width="20" height="15"></td>
            <td width="20" class="campositem">N&ordm;</td>
            <td class="campositem"><%=turno.getIdWorkflow()%></td>
        </tr>
    </table>

           <%if(ocultarTurno.equals("FALSE")){%>

    <br>

    <hr class="linehorizontal">
       <%}%>
           <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Matr&iacute;cula Inmobiliaria de la Propiedad</td>
        <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
        <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
        <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" class="camposform">

					<%List solFolios = solicitud.getSolicitudFolios();
					Iterator itSolFolios = solFolios.iterator();
					if(!solFolios.isEmpty()){
						while(itSolFolios.hasNext()){
							SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();%>
        <tr>
        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem"><%=((Folio)solFolio.getFolio()).getIdMatricula()%></td>
        </tr>

						<% }%>
					<% }else{%>
        <tr>
        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem">No hay elementos que mostrar</td>
        </tr>
					<%}%>
    </table>
       
    </td>
    
    

    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>

    <tr>
    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
    </tr>
</table>



<% List foliosNoImpresos = (List) session.getAttribute(gov.sir.core.web.acciones.correccion.AWCorreccion.FOLIOS_NO_IMPRESOS);

if (foliosNoImpresos != null){


%>

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
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folios válidos para imprimir</td>
              <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  </tr>
              </table></td>
              <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td>
          <img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>

    <tr>
    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

       <form name="CORRECCION2" method="post" action="correccion.mesa-control.do">
       <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.correccion.AWCorreccion.IMPRIMIR%>">
       <input type="hidden" name="<%=gov.sir.core.negocio.modelo.constantes.CFolio.ID_MATRICULA%>">
        <%
			  try {
                   tablaHelper.setColCount(5);
                   tablaHelper.setListName(gov.sir.core.web.acciones.correccion.AWCorreccion.FOLIOS_NO_IMPRESOS);
                   tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_RADIO);
            	   tablaHelper.render(request, out);
              }
              catch(HelperException re){
                   out.println("ERROR " + re.getMessage());
              }

		%>

          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>


        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

	          <table width="100%" class="camposform">
              <tr align='left'>
              <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			  <td width='150'><a href="javascript:cambiarAccion2('<%=gov.sir.core.web.acciones.correccion.AwCorr_MesaControl.IMPRIMIR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif"  alt="Imprimir" width="139" height="21" border="0"></a></td>
              <td>&nbsp;</td>
              </tr>
			  <tr>
                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                <td allign="left" width="250">Seleccionar Impresora:</td>
				<td width="80">

                   <%

                   try {
							//IDCIRCULO
							String idCirculo = "";
							if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
								idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
							}

                            String key = WebKeys.LISTA_IMPRESORAS+"_"+idCirculo;

                                    // Bug 3570:
                                    // esta sacando una lista para
                                    // poblar un helper y es una tabla hash

                                    Object local_PrinterListAsObject;

                                    local_PrinterListAsObject = session.getAttribute( key );

                                    java.util.List local_PrintersList = null;

                                    // initial list to populate

 		                    java.util.List impresoras;

                                    impresoras = new java.util.Vector();

                                    if( null == local_PrinterListAsObject ) {
                                      local_PrinterListAsObject = new java.util.ArrayList();
                                    }


                                    if( local_PrinterListAsObject instanceof java.util.Map ) {

                                      // transform (map2list)
                                      Map local_PrintersMap = (Map)local_PrinterListAsObject;
                                      String local_CirculoId = ((gov.sir.core.negocio.modelo.Circulo)session.getAttribute( WebKeys.CIRCULO )).getIdCirculo();
                                      String local_TipoImprimibleId = CTipoImprimible.CERTIFICADO_INMEDIATO;
                                      local_PrintersList = jspMethod_ValueObjectsProcessor_FilterImpresorasByCirculoTipo(
                                         local_PrintersMap
                                       , local_TipoImprimibleId
                                       , local_CirculoId
                                      );

		 		      //              gov.sir.core.web.helpers.comun.ElementoLista elemento = new gov.sir.core.web.helpers.comun.ElementoLista();
		 		      //              gov.sir.core.negocio.modelo.CirculoImpresora impre = (gov.sir.core.negocio.modelo.CirculoImpresora)tipos.get(i);
		 		      //              elemento.setId(impre.getIdImpresora());
		 		      //              elemento.setValor(impre.getIdImpresora());
		 		      //              impresoras.add(elemento);

                                      local_PrinterListAsObject = local_PrintersList;
                                    }
                                    if( local_PrinterListAsObject instanceof java.util.List ) {

                                      local_PrintersList = (List)local_PrinterListAsObject;
                                      Iterator local_PrintersListIterator;
                                      local_PrintersListIterator = local_PrintersList.iterator();
                                      gov.sir.core.negocio.modelo.CirculoImpresora local_PrintersElement;

                                      gov.sir.core.web.helpers.comun.ElementoLista local_TiposElement;
                                      for( ;local_PrintersListIterator.hasNext(); ) {
                                        local_PrintersElement = (gov.sir.core.negocio.modelo.CirculoImpresora)local_PrintersListIterator.next();
                                        local_TiposElement = new gov.sir.core.web.helpers.comun.ElementoLista();
                                        local_TiposElement.setId( local_PrintersElement.getIdImpresora() );
                                        local_TiposElement.setValor( local_PrintersElement.getIdImpresora() );

		 		        impresoras.add( local_TiposElement );

                                      } // for



                                    }

 		                    if( session.getAttribute("IMPRESORA") == null ) {
		 		        session.setAttribute("IMPRESORA",request.getParameter("IMPRESORA") );
                                    }


 		                    // if (tipos!=null)
 		                    // {
	 		            //        for (int i=0; i<tipos.size(); i++)
	 		            //        {
		 		    //                gov.sir.core.web.helpers.comun.ElementoLista elemento = new gov.sir.core.web.helpers.comun.ElementoLista();
		 		    //                gov.sir.core.negocio.modelo.CirculoImpresora impre = (gov.sir.core.negocio.modelo.CirculoImpresora)tipos.get(i);
		 		    //                elemento.setId(impre.getIdImpresora());
		 		    //                elemento.setValor(impre.getIdImpresora());
		 		    //                impresoras.add(elemento);
		 		    //        }
 		                    // }

						    gov.sir.core.web.helpers.comun.ListaElementoHelper impresorasHelper = new gov.sir.core.web.helpers.comun.ListaElementoHelper();
 		        			impresorasHelper.setTipos(impresoras);
              			    impresorasHelper.setNombre(WebKeys.IMPRESORA);
              			    impresorasHelper.setCssClase("camposformtext");
              			    impresorasHelper.setId(WebKeys.IMPRESORA);

							impresorasHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}

					%>

					</td>
		            <td>&nbsp;</td>
                </tr>
	          </table>
        </form>
          </td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>


  <%

  }

%>



      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Acciones</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion.gif" width="16" height="21"></td>
                    </tr>
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

              <br>
              <table width="100%" class="camposform">
                <tr>
               <form name="CORRECCION1" method="post" action="correccion.mesa-control.do">
       			<input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.correccion.AwCorr_MesaControl.VOLVER_MESA%>">

              <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			  <td width='150'><a href="javascript:cambiarAccion1('<%=gov.sir.core.web.acciones.correccion.AwCorr_MesaControl.VOLVER_MESA%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif"  alt="Aceptar" width="139" height="21" border="0"></a></td>
         </form>
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






<%
String tipoCorreccion="EXTERNA";
if(turnoPadre != null){
	tipoCorreccion="INTERNA";
}
%>


      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Creación de la solicitud</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion.gif" width="16" height="21"></td>
                    </tr>
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

            <table width="100%" class="camposform">
            <tr>


                              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Tipo de Corrección</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_devoluciones.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>&iquest; Qué Tipo de corrección es ?</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                          <tr>
                            <td>&nbsp;</td>
                            <td>La corrección es </td>

                  <td class="campositem"><%=tipoCorreccion%>&nbsp;</td>

                            </td>
                          </tr>
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
  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>


<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- class methods --%>

<%-- Bug 3570: Filtro temporal de impresoras --%>

<%!


// realiza una transformacion de un mapa
// en una lista

// estructura del mapa:
//   key: (TipoImpresion)
// value: (List< CirculoImpresora >)

public static List
jspMethod_ValueObjectsProcessor_FilterImpresorasByCirculoTipo(
    Map local_SourceObject
  , String local_TipoImprimibleId
  , String local_CirculoId
) {


    List local_Result;
    local_Result = new ArrayList();


      local_SearchImpl_jx: {

                 // :: local variables ----------------------------------------------
                 // ...... source variables

                 // ...... target variables
                 Map local_TmpMapTargetObject;


                 local_TmpMapTargetObject = new java.util.TreeMap(
                    new java.util.Comparator() {

                      public boolean equals(Object o2 ) {
                        return false;
                      } // end method

                      public int compare(Object o1, Object o2) {
                        if( !( ( o1 instanceof gov.sir.core.negocio.modelo.CirculoImpresora )
                            &&( o2 instanceof gov.sir.core.negocio.modelo.CirculoImpresora ) ) ){
                          return -1;
                        }

                        gov.sir.core.negocio.modelo.CirculoImpresora p1 = (gov.sir.core.negocio.modelo.CirculoImpresora)o1;
                        gov.sir.core.negocio.modelo.CirculoImpresora p2 = (gov.sir.core.negocio.modelo.CirculoImpresora)o2;


                        return( p1.getIdCirculo().compareTo( p2.getIdCirculo() )
                              + p1.getIdImpresora().compareTo( p2.getIdImpresora() ) );

                      } // end method

                    } // end class

                 );

                 JXPathContext 	                local_JxContext;
                 Object 			local_JxContextSource;
                 String 			local_JxSearchString;

                 // search-1:
                 // " .[ (@idTipoImprimible = $local_TipoImprimibleId ) ]";
                 // local_JxContext.setValue( "local_TipoImprimibleId", local_TipoImprimibleId );
                 //
                 Iterator local_TargetIterator = null;

                 local_Search_JxTipoImprimible: {

                   Iterator   local_SourceObjectEntryIterator = local_SourceObject.entrySet().iterator();
                   Map.Entry  local_SourceObjectEntry;
                   List       local_Search1Result;


                   gov.sir.core.negocio.modelo.TipoImprimible local_SourceObjectElementKey;
                   Object                                     local_SourceObjectElementValue;

                   local_Search1Result = new ArrayList();

                   for( ; local_SourceObjectEntryIterator.hasNext(); ) {
                     local_SourceObjectEntry = (Map.Entry)local_SourceObjectEntryIterator.next();
                     local_SourceObjectElementKey = (gov.sir.core.negocio.modelo.TipoImprimible)local_SourceObjectEntry.getKey();

                     if(!(  local_TipoImprimibleId.equals( local_SourceObjectElementKey.getIdTipoImprimible() ) ) ) {
                       continue;
                     } //

                     local_SourceObjectElementValue = local_SourceObjectEntry.getValue();

                     local_Search1Result.add( local_SourceObjectElementValue );

                     break;

                   } // for

                   local_TargetIterator = local_Search1Result.iterator();

                   // jxpath does not support map keys as objects, only strings

                   // // (declare variables)
                   // local_JxContext.getVariables().declareVariable( "local_TipoImprimibleId", "" );

                   // local_JxSearchString = " .[ (@idTipoImprimible = $local_TipoImprimibleId ) ]";
                   // local_JxContext.setValue( "local_TipoImprimibleId", local_TipoImprimibleId );

                   // // :: get the results
                   // // single object   : local_JxContext.getValue
                   // // multiple object : local_JxContext.iterate

                   // Iterator local_TargetIterator;
                   // local_TargetIterator = local_JxContext.iterate( local_JxSearchString );

                 } // :local_Search_JxTipoImprimible

                 // :: initialize



                 // se usa un mapa para eliminar elementos duplicados

                 for( ; local_TargetIterator.hasNext() ; )  {
                     // consume
                     ArrayList local_TargetElement = (ArrayList)local_TargetIterator.next();

                     for( Iterator local_TmpListIterator = local_TargetElement.iterator(); local_TmpListIterator.hasNext(); ) {
                       Object circuloImpresora = local_TmpListIterator.next();
                       local_TmpMapTargetObject.put( circuloImpresora, circuloImpresora );
                     } // for

                 } // for

                 local_TargetIterator = null;

                 // luego se colocan los resultados en la lista objetivo
          // transform the map & return
          Map.Entry mapEntry; // to iterate through the map


          for( Iterator iterator = local_TmpMapTargetObject.entrySet().iterator(); iterator.hasNext();) {
            mapEntry =  (Map.Entry) iterator.next();

            local_Result.add( mapEntry.getValue() );

          } // for

          local_TmpMapTargetObject = null;

          // fix the results
          return local_Result;
          // ----------------------------------------------------------
      } // :local_SearchImpl_jx




/*

  Map local_PrintersMap = local_SourceObject;
  Iterator local_PrintersMapKeyIterator;
  gov.sir.core.negocio.modelo.CirculoImpresora local_PrintersElement;

  Map.Entry           local_PrintersMapEntry;
  java.util.ArrayList local_PrintersMapEntryValue;

  local_PrintersMapKeyIterator = local_PrintersMap.entrySet().iterator();

  gov.sir.core.web.helpers.comun.ElementoLista local_TiposElement;
  for( ; local_PrintersMapKeyIterator.hasNext(); ) {

    // el key es un tipoimprimible
    // el value de cada entry es una lista

    local_PrintersMapEntry = (Map.Entry)local_PrintersMapKeyIterator.next();
    local_PrintersMapEntryValue = (java.util.ArrayList)local_PrintersMapEntry.getValue();

    // al parecer el query que se devuelve coloca
    // todos los circulos;
    // 1. se debe filtrar solo los tipos del circulo actual
    // 2. se debe colocar solo si no existe actualmente

    // 3. (TODO: temporal debido al cambio que se hizo de impresion)

    for( Iterator local_PrintersMapEntryValueIterator = local_PrintersMapEntryValue.iterator(); local_PrintersMapEntryValueIterator.hasNext(); ) {
      local_PrintersElement = (gov.sir.core.negocio.modelo.CirculoImpresora)local_PrintersMapEntryValueIterator.next();
      local_TiposElement = new gov.sir.core.web.helpers.comun.ElementoLista();
      local_TiposElement.setId( local_PrintersElement.getIdImpresora() );
      local_TiposElement.setValor( local_PrintersElement.getIdImpresora() );

      impresoras.add( local_TiposElement );

    } // for


  return null;
*/

} // end-method: jspMethod_ValueObjectsProcessor_FilterImpresorasByCirculoTipo


%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>




