<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.Solicitud"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.web.acciones.registro.AWEnglobe"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.WebEnglobe"%>
<%@page import="gov.sir.core.negocio.modelo.WebFolioHeredado"%>

<%  session.removeAttribute(WebKeys.APROBAR_CALIFICACION);
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    Solicitud solicitud =(Solicitud)turno.getSolicitud();

 	//HELPER TABLA MATRICULAS
	TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();

	WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
	if (webEnglobe == null) {
		webEnglobe = new WebEnglobe();
	}

	Vector foliosSeleccionados = new Vector();
	if(webEnglobe.getFoliosHeredados()!=null && webEnglobe.getFoliosHeredados().size()>0){

		Iterator itFolios  =  webEnglobe.getFoliosHeredados().iterator();

		while(itFolios.hasNext()){
			gov.sir.core.negocio.modelo.WebFolioHeredado webFolioHeredado = (WebFolioHeredado)itFolios.next();
			foliosSeleccionados.add(webFolioHeredado.getIdMatricula());
		}
	}


	List matriculas = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();

	Iterator is = matriculas.iterator();
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();

		Folio folio = sf.getFolio();
		String labelMatricula = "";
		String idMatricula ="";

		if(folio.isDefinitivo()){
			labelMatricula= folio.getIdMatricula();
			idMatricula= folio.getIdMatricula();
		}else{
			//labelMatricula= folio.getNombreLote();
			labelMatricula= folio.getIdMatricula();
			idMatricula= folio.getIdMatricula();
		}

		ElementoLista elista = new ElementoLista();
		elista.setId(idMatricula);
		elista.setValor(labelMatricula!=null?labelMatricula:"");

		//QUITAR ESTAS LINEAS PARA NO PERMITIR LA EDICI�N DE FOLIOS CERRADOS.
		if(folio!=null && folio.getEstado()!=null && !folio.getEstado().getIdEstado().equals(gov.sir.core.negocio.modelo.constantes.CEstadoFolio.CERRADO)){
			idmatriculas.add(elista);
		}
	}
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idmatriculas);

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">ASISTENTE ENGLOBE - PASO 1 - ESCOGER FOLIOS A ENGLOBAR</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">ESCOGER FOLIOS PARA ENGLOBAR</td>
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
            <form action="englobe.do" method="post" name="CALIFICACION" id="CALIFICACION">
            <input type="hidden" name="POSSCROLL" id="POSSCROLL" value="<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
            <input type="hidden" name="ACCION" value="ESCOGER_FOLIOS">
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">
                    Folios asociados a la solicitud </td>
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
                  	 	session.setAttribute("SELECCIONADOS" ,foliosSeleccionados);
                  	 	try {
	                      tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
	                      tablaHelper.setSelectedValues_Enabled(true);

			              tablaHelper.setSelectedValues_Ids_Key( "SELECCIONADOS" );

	                      //tablaHelper.setSelectedValues_Ids(foliosSeleccionados);
                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
	               		  tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
	                  %>


                  </td>
                </tr>
              </table>

              <hr class="linehorizontal">
              <br>
              <table width="100%" class="camposform">

                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="150"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWEnglobe.ESCOGER_FOLIOS%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" name="Folio" width="139" height="21" border="0" id="Folio"></td>


				  <!--BOTON CANCELAR-->
                  <td width="150">
					<%-- SOF:SCRIPT-VARS --%>
					<script type="text/javascript">
					   var CANCELAR_ENGLOBE = "<%= AWEnglobe.CANCELAR_ENGLOBE %>";
					   var ELIMINAR_ENGLOBE = "<%= AWEnglobe.ELIMINAR_ENGLOBE %>";
					   function eliminarEnglobe(){
					      if(confirm('�Desea eliminar el englobe en curso?')){
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
