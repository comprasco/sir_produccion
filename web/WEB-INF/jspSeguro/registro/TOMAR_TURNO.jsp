<%@page import="org.auriga.core.web.*,gov.sir.core.web.helpers.comun.*,java.util.*,gov.sir.core.negocio.modelo.*,gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%      
        NotasInformativasHelper notasHelper = new NotasInformativasHelper();
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
    MatriculaViewHelper matViewHelper = new MatriculaViewHelper();        
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
	session.removeAttribute(gov.sir.core.web.acciones.registro.AWCalificacion.HAY_ACTOS_VENCIDOS);
	List solicitudFolios = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	Iterator is= solicitudFolios.iterator();
	
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
	
	
	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO,idmatriculas);
%>
<SCRIPT>
function cambiarAccion(text) {
    document.CALIFICACION.ACCION.value = text;
    document.CALIFICACION.submit();
}
function cambiarAccion1(text) {
    document.CALIFICACION.<%=WebKeys.ACCION%>.value = text;
    document.CALIFICACION.submit();
}

function cambiarAccion2( text ) {

     var message = new String( "Esto eliminara los cambios registrados como temporales en este turno, ¿desea continuar? " );

     if( !js_ConfirmMessage( message ) )
        return void(0);

    cambiarAccion1( text );
}



   function js_ConfirmMessage( message ) {
     var agree = confirm( message );
     if( agree )
        return true;
     return false;
   }
</SCRIPT>
		
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Tomar Folios de la Calificaci&oacute;n</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td></td>
                      
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
            <form action="calificacion.do" method="post" name="CALIFICACION" id="CALIFICACION">
		    <input type="hidden" name="ACCION" value="TOMAR_FOLIO">	
 			<% 	Proceso proceso = ((Proceso)session.getAttribute(WebKeys.PROCESO));

               	if(proceso != null){
            %>
            <input type="hidden" name="ID_PROCESO" value="<%= proceso.getIdProceso()%>">
            <%
               	}
            %>	                
              <br>            
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;culas Asociadas a la calificaci&oacute;n </td>
                  <td width="16" class="bgnsub"></td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <%try {
              		tablaHelper.setColCount(5);
                    tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
					tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                    tablaHelper.render(request, out);
              
				}catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}
			  %>  
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"></td>
                  <td width="150"><a href="javascript:cambiarAccion('TOMAR_TURNO')"><img src="<%=request.getContextPath()%>/jsp/images/btn_tomar_todos_folios.gif" name="Folio" width="190" height="21" border="0" id="Folio"></a></td>
                  <td width="150"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWCalificacion.DESASOCIAR_FOLIOS%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_desasociar_folios.gif" name="Folio" width="180" height="21" border="0" id="Folio"></a></td>   
				  <td width="150"><a href="javascript:cambiarAccion2('<%=AWCalificacion.PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION%>');"><img alt="[eliminar datos temporales]" src="<%=request.getContextPath()%>/jsp/images/btn_deshacercambios.gif" border="0"></a></td>        
                  <td width="95%">&nbsp;</td>
                  <!--<td width="150"><a href="javascript:cambiarAccion('CONFRONTACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_confrontacion.gif" name="Confrontacion" width="150" height="21" border="0" id="Confrontacion"></a></td>-->
                  <!--<td><a href="<%=request.getContextPath()%>/turnos.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Cancelar" width="150" height="21" border="0" id="Cancelar"></a></td>-->
                  
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
      <td colspan="5">
           <% 
                    try
		{
		  notasHelper.setNombreFormulario("BUSCAR");
		  notasHelper.render(request,out);
		}
		catch(HelperException re)
		{
			out.println("ERROR " + re.getMessage());
		}	%>
      </td>

  </tr>
 
</table>
