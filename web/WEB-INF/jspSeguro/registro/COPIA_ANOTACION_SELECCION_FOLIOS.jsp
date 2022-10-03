<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.Solicitud"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.RadioHelper"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAnotacion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstadoFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso"%>

<%
	Turno t = (Turno) session.getAttribute(WebKeys.TURNO);
    Solicitud sol = t.getSolicitud();
    Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);

	//SE LLENA LA LISTA DE MATRÍCULAS PARA LA REVISIÓN DE CONFRONTACIÓN
	List matriculas = sol.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	RadioHelper radioHelper = new RadioHelper();

	/*Iterator it = matriculas.iterator();
	while(it.hasNext()){
		gov.sir.core.negocio.modelo.SolicitudFolio sf=(SolicitudFolio)it.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		if(!(folio!=null && folio.getIdMatricula().equals(temp))){
			idmatriculas.add(temp);
		}

	}*/

	Iterator is = matriculas.iterator();
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();

		Folio folioTemp = sf.getFolio();
		String labelMatricula = "";
		String idMatricula ="";


                /*
                * @author       :   Henry Gómez Rocha
                * @change       :   Se comentariaron if else debido a que se ejecutan las mismas
                *                   sentencias en ambos lados, se realiza este cambio
                *                   para optimizar el proceso ya que se elimina una pregunta.
                * Caso Mantis   :   0004480
                */

		//if(folioTemp.isDefinitivo()){
			labelMatricula= folioTemp.getIdMatricula();
			idMatricula= folioTemp.getIdMatricula();
		//}else{
			//labelMatricula= folioTemp.getNombreLote();
			//labelMatricula= folioTemp.getIdMatricula();
			//idMatricula= folioTemp.getIdMatricula();
		//}
		
		/*
                * @author       :   Henry Gómez Rocha
                * @change       :   Se comentarió el if dado que inhabilita presentar los Folios Destino cuando se
                *                   crea una anotación canceladora y posteriormente se desea Copiar Anotación.
                * Caso Mantis   :   0004480
                */
		gov.sir.core.web.helpers.comun.ElementoLista elista = new gov.sir.core.web.helpers.comun.ElementoLista();
		/*/if((folioTemp.getEstado() != null && folioTemp.getEstado().getIdEstado() != null
			&& !folioTemp.getEstado().getIdEstado().equals(CEstadoFolio.CERRADO))
			|| t.getIdProceso()==Long.parseLong(CProceso.PROCESO_CORRECCIONES)){*/
		
			elista.setId(idMatricula);
			elista.setValor(labelMatricula!=null?labelMatricula:"");
	
			if(folio==null){
				idmatriculas.add(elista);
			} else if(folio!=null && !folio.getIdMatricula().equals(elista.getId())){
				idmatriculas.add(elista);
			}
		//}

	}

	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idmatriculas);

   %>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
       document.COPIAANOTACION.ACCION.value = text;
       document.COPIAANOTACION.submit();
}

function cambiarSeleccion() {
    if(document.COPIAANOTACION.seleccionado.checked == true){
	    setAllCheckBoxes('COPIAANOTACION', 'ELIMINAR_CHECKBOX', true);
    }
    if(document.COPIAANOTACION.seleccionado.checked == false){
    	setAllCheckBoxes('COPIAANOTACION', 'ELIMINAR_CHECKBOX', false);
    }
}
</script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">COPIA ANOTACIÓN - ESCOGER FOLIO, ANOTACIÓN Y FOLIOS A COPIAR</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">INGRESE LA INFORMACIÓN PARA LA COPIA DE LA ANOTACIÓN</td>
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



            <form action="copiaAnotacion.do" method="post" name="COPIAANOTACION" id="COPIAANOTACION">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION%>" value="<%= gov.sir.core.web.acciones.registro.AWCopiaAnotacion.COPIAR_ANOTACION%>">
				
				
				<input type="hidden" name="POSSCROLL" id="POSSCROLL" value="<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="campostip04"><b>Tenga en cuenta las siguientes recomendaciones: </b><br>
                  Si el rango de anotaciones no tiene anotaciones canceladoras, todas las anotaciones del rango seran copiadas a los folios seleccionados.<br>
                  Si alguna de las anotaciones es canceladora debe conocer la anotación que desea cancelar en cada uno de los folios.<br>
                  Si el rango tiene dos anotaciones canceladoras se debe realizar una copia para cada anotación canceladora y se debe conocer igualmente la anotación a cancelar en cada folio.
                  </td>
                </tr>
              </table>

              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub"><span class="titulotbcentral">Folio de origen</span></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>


              <table width="100%">
                <tr>
                  <td class='campositem'>&nbsp;&nbsp;&nbsp;
                  		<%
                  			if(folio!=null){
                  		%>
                  		<input type='hidden' name='<%=WebKeys.FOLIO_ORIGEN%>' value='<%=folio.getIdMatricula()%>'><strong><%=folio.getIdMatricula()%></strong>
                  		<%
                  			}else{
                  		%>

				    	<% try {
				    		  //HELPER TABLA MATRICULAS
							  gov.sir.core.web.helpers.comun.TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();

		                      tablaHelper.setColCount(5);
		                      tablaHelper.setInputName(WebKeys.FOLIO_ORIGEN);
		                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
	                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_RADIO);
		               		  tablaHelper.render(request, out);
		                    }
		                    catch(HelperException re){
		                      out.println("ERROR " + re.getMessage());
		                    }
		                 %>
		               <%
		               	}
		               %>

                  </td>
                </tr>
              </table>



              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub"><span class="titulotbcentral">Rango de Anotaciones</span></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido" align="left" width="2">Desde: </td>
                  <%
                  	String temp = (String)session.getAttribute(gov.sir.core.web.acciones.registro.AWCopiaAnotacion.DESDE);
                  %>
                  <td class="contenido" align="left"  width="2"><input name="<%=gov.sir.core.web.acciones.registro.AWCopiaAnotacion.DESDE%>" type="text" class="camposformtext" value="<%=temp!=null?temp:""%>" size="10"></td>
                  <!--<td class="contenido" width="5" align="right">&nbsp;</td>-->


                  <td class="contenido" align="left" width="2">Hasta: </td>
                  <%
                  	String temp2 = (String)session.getAttribute(gov.sir.core.web.acciones.registro.AWCopiaAnotacion.DESDE);
                  %>
                  <td class="contenido" align="left"><input name="<%= gov.sir.core.web.acciones.registro.AWCopiaAnotacion.HASTA%>" type="text" class="camposformtext" value="<%=temp2!=null?temp2:""%>" size="10"></td>

                </tr>
              </table>

              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub"><span class="titulotbcentral">Folios Destino</span></td>
                  <td align='right' width='20' class="bgnsub"><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'>&nbsp;</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%">
                <tr>
                  <td>

				    	<% try {
				    		  //HELPER TABLA MATRICULAS
							  gov.sir.core.web.helpers.comun.TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();

		                      tablaHelper.setColCount(5);
		                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
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


              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub"><span class="titulotbcentral">Copiar comentario en las anotaciones ?</span></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_copias.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td width="20">
					<%
							RadioHelper radio = new RadioHelper();

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
                  <td>No</td>
                </tr>
              </table>


              <br>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140">
                    <a href="javascript:cambiarAccion('<%= gov.sir.core.web.acciones.registro.AWCopiaAnotacion.COPIAR_ANOTACION %>')">
                     <img alt="[process]" src="<%=request.getContextPath()%>/jsp/images/btn_copiar_anotacion.gif"  width="150" height="21" border="0" />
                    </a>
                    </td>
                  <td width="150">
                    <a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWCopiaAnotacion.REGRESAR%>')">
                      <img alt="[cancel]"  src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="150" height="21" border="0" id="Folio" />
                    </a></td>
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
