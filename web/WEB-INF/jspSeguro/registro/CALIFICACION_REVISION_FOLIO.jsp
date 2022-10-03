<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>

<%
	session.setAttribute(WebKeys.VISTA_INICIAL,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));		
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();
	//SE LLENA LA LISTA DE MATRÍCULAS PARA LA REVISIÓN DE CONFRONTACIÓN
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
		
		idmatriculas.add(elista);
	}	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idmatriculas);		
	
	//IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}

	//HELPER PARA MOSTRAR
	TextHelper textHelper = new TextHelper();	

	//OPCIONES DE DELEGACIÓN O REENVIO
	List avanceWF = new Vector();
	avanceWF.add(new ElementoLista(AWCalificacion.ANT_SISTEMA,"Antiguo Sistema"));
	avanceWF.add(new ElementoLista(AWCalificacion.CORRECCION,"Correcciones"));
        avanceWF.add(new ElementoLista(AWCalificacion.SUSPENSION_DEL_TRAMITE_REGISTRO_PREVENCION, "Suspensión del Tramite de Registro a Prevención"));
        avanceWF.add(new ElementoLista(AWCalificacion.SUSPENSION_TEMPORAL_DEL_TRAMITE_REGISTRO, "Suspensión Temporal del Tramite de Registro"));
	//SE ELIMINA LA OPCIÓN DE ESPECIALIZADO, YA QUE ESTO SE REFIERE A ACTUACIONES ADMINISTRATIVAS Y DEBE ENTRAR POR LA OPCIÓN DE CORRECCIONES.
	//avanceWF.add(new ElementoLista(AWCalificacion.ESPECIALIZADO,"Especializado"));
	ListaElementoHelper helper = new ListaElementoHelper();
	helper.setTipos(avanceWF);

	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));
	session.removeAttribute(gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE);	
   %> 
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
   document.CALIFICACION.ACCION.value = text;
   document.CALIFICACION.submit();
}

function calificar(text) {
   document.CALIFICACION.ITEM.value = text;
   cambiarAccion('CALIFICAR_FOLIO');
}

function reenviarCalificacion(text) {
	if(confirm('¿Esta seguro que desea reenviar el turno para que sea revisado en otras dependencias?')){
	   document.CALIFICACION.ACCION.value = text;
	   document.CALIFICACION.action = 'calificacion.do';
	   document.CALIFICACION.submit();
	}
}

</script>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02">
    
    

    <form action="consultaFolio.do" method="post" name="CAL" id="CAL">
    <input type="hidden" name="ACCION" value="<%=gov.sir.core.web.acciones.administracion.AWConsultaFolio.CONSULTAR_FOLIO_ADMINISTRACION%>">

      
      
    <!--INICIO MATRICULAS ASOCIADAS, OPCIONES DE CONSULTA-->  
      
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Consultar información de los folios</td>
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
	            </table>
            </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>


        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

              <table width="100%">
                <tr>
                  <td>
	                  <% try {
	                      TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
						  
						  tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_RADIO);
	               		  tablaHelper.setInputName(CFolio.ID_MATRICULA);
	               		  tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
	                    
	                  %>

                  </td>
                </tr>
              </table>
              
              
            <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform" >
              <tr>
                <td width="20">
                  
                </td>
                <td>Consultar Seleccionada</td>
                <td><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_short_buscar.gif" border="0"></td>
				  
              </tr>
            </table>

      </form>

    <form action="consultaFolio.do" method="post" name="CALIFICACION" id="CALIFICACION">
    <input type="hidden" name="ACCION" value="<%=gov.sir.core.web.acciones.administracion.AWConsultaFolio.CONSULTAR_FOLIO_ADMINISTRACION%>">

			  <br>
			  <br>
 			  <br>
               <table width="100%" class="camposform">
                <tr> 
                  <td class="contenido" width="40%">Consulta de folio por n&uacute;mero de matr&iacute;cula </td>
                  <td class="contenido">&nbsp;</td>
                  <td class="contenido" width="60%"><input name="<%= gov.sir.core.negocio.modelo.constantes.CFolio.ID_MATRICULA%>" type="text" class="camposformtext" value="<%=idCirculo%>-"></td>
                </tr>
              </table>
			
			  <br>
			  
			  
              <table width="100%" class="camposform">
                <tr>

                <td width="140"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0"></td>                  
                  <td>                 
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
      
    <!--FIN DE INICIO MATRICULAS ASOCIADAS, OPCIONES DE CONSULTA-->
      
      
    <!--OPCIONES DE REENVIO-->  
      
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Solicitar información de folios a otras dependencias</td>
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
	            </table>
            </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>


        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

            <table width="100%" class="camposform">
              <tr>
               <td>Enviar el caso a : </td>
               <td>
                   <% try {
                           helper.setId(AWCalificacion.RESPUESTAWF);
                           helper.setNombre(AWCalificacion.RESPUESTAWF);
                           helper.setCssClase("camposformtext");
                           helper.render(request, out);
                       } catch (HelperException re) {
                           out.println("ERROR " + re.getMessage());
                       }
                   %>
               </td>
                <td width="150"><a href="javascript:reenviarCalificacion('<%=AWCalificacion.AVANZAR%>')"><img name="imageField" src="<%=request.getContextPath()%>/jsp/images/btn_enviar_caso.gif" width="139" height="21" border="0"></a></td>
                <td width="240">&nbsp;</td>
              </tr>
<tr>
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
      
      <!--OPCIONES DE REENVIO-->      
      
      
      
    </form>
      
      
      

  </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
