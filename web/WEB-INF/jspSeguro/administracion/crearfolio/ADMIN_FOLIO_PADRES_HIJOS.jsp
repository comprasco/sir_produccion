<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.devolucion.*"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.negocio.modelo.Anotacion,
java.util.*,
java.util.Iterator,
java.util.List,
java.util.Vector,
gov.sir.core.web.helpers.comun.MostrarFechaHelper,
gov.sir.core.web.helpers.comun.TextAreaHelper,
gov.sir.core.web.helpers.comun.TextHelper,
gov.sir.core.web.helpers.comun.AntiguoSistemaHelper,
org.auriga.core.web.*,
gov.sir.core.negocio.modelo.*,
gov.sir.core.negocio.modelo.constantes.*,
gov.sir.core.negocio.modelo.constantes.CAnotacion,
gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano,
gov.sir.core.web.WebKeys,
gov.sir.core.web.helpers.registro.VariosCiudadanosAnotacionHelper,
gov.sir.core.negocio.modelo.constantes.CFolio,
gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema,
gov.sir.core.web.acciones.administracion.AWCrearFolio,
gov.sir.core.web.acciones.correccion.*,
gov.sir.core.web.acciones.registro.AWCalificacion,
gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro,
gov.sir.core.web.helpers.comun.ListaElementoHelper,
gov.sir.core.web.acciones.comun.*"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTabs"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import ="gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants"%>
<%@page import ="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CAccionFolioEditPadresHijos"%>
<%@page import="org.auriga.smart.*"%>


<%



TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
	List foliosPadre = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
	List foliosHijo  = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_HIJO);
	
	if	(foliosPadre == null) {
		foliosPadre = new ArrayList();
		//System.out.println("********************foliosPadre es nullo");
	} else {
		//System.out.println("********************foliosPadre NO es nullo");
	}
	
	if	(foliosHijo == null) {
		foliosHijo = new ArrayList();
		//System.out.println("****************foliosHijo es nullo");
	} else {
		//System.out.println("********************foliosHijo NO es nullo");
	}
	
	List matriculasPadre = new ArrayList();
	List matriculasHijo = new ArrayList();
	
	Vector imagenesPadre = new Vector();
	Vector imagenesHijo = new Vector();

	Iterator ip = foliosPadre.iterator();
	while(ip.hasNext()){
		//System.out.println("********************eNTRO A ITERATOR DE FOLIOPADRE");
		Folio folioId = (Folio)ip.next();
		String temp=(String) folioId.getIdMatricula();
		matriculasPadre.add(temp);
		
	}
	
	Iterator ipp = foliosHijo.iterator();
	while(ipp.hasNext()){
		//System.out.println("********************eNTRO A ITERATOR DE FOLIOHIJO");
		Folio folioId = (Folio)ipp.next();
		String temp=(String) folioId.getIdMatricula();
		matriculasHijo.add(temp);
	}
	
    //Se mira si existen exceptciones.
	Boolean exception;
	exception = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
	
	session.removeAttribute(WebKeys.HAY_EXCEPCION);
	
	session.setAttribute(WebKeys.LISTA_FOLIOS_HIJO+CFolio.ID_MATRICULA, matriculasHijo);
	session.setAttribute(WebKeys.LISTA_FOLIOS_PADRE+CFolio.ID_MATRICULA, matriculasPadre);
	
	
	 String idCirculo = "";
  if ( null != request.getSession().getAttribute( WebKeys.CIRCULO ) ) {
          idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
          idCirculo = idCirculo + "-";
  }
  
TextHelper textHelper = new TextHelper();
List tipos = (List)application.getAttribute(WebKeys.LISTA_TIPOS_CALCULO);

%>
<script type="text/javascript">
function cambiarAccion(text) {
	document.CREARDERIVADO.ACCION.value = text;
	document.CREARDERIVADO.submit();
}
function cambioFocus(text){
	document.getElementById('<%=CFolio.ANOTACION_NUM_ID_PERSONA%>0').focus();
}

function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

</script>
   <link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
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
    <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">ADMINISTRACIÓN - GRABACIÓN FOLIO - INGRESO MATRIZ/SEGREGACIÓN </td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Matriz/Segregación</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                     <td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                    </tr>
                  </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>   
        <tr>
         
          <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          
              <br>
              <%
        			final String PARAM__LOCAL_FOLIO_ID_MATRICULA     = "PARAM:LOCAL_FOLIO_IDMATRICULA";
			        final String PARAM__LOCAL_FOLIO_ID_ZONAREGISTRAL = "PARAM:LOCAL_FOLIO_IDZONAREGISTRAL";

    			    String local_Folio_IdMatricula;
		        	String local_Folio_IdZonaRegistral;

        			if( ( null != ( local_Folio_IdMatricula     = (String)session.getAttribute( PARAM__LOCAL_FOLIO_ID_MATRICULA     ) ) )
          			&&( null != ( local_Folio_IdZonaRegistral = (String)session.getAttribute( PARAM__LOCAL_FOLIO_ID_ZONAREGISTRAL ) ) ) ) {
			        %>
	         			<table width="100%" border="0" cellpadding="0" cellspacing="0">
                		<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                		
                		<tr>
                  			<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif" width="20" height="15" /></td>
                  			<td width="20" class="campositem">N&ordm;</td>
                  			<td class="campositem">
							<%
							try {
								TextHelper local_TextHelper;
								local_TextHelper = new TextHelper();
								local_TextHelper.setId(     PARAM__LOCAL_FOLIO_ID_MATRICULA );
								local_TextHelper.setNombre( PARAM__LOCAL_FOLIO_ID_MATRICULA );
								local_TextHelper.setCssClase( "campositem" );
								local_TextHelper.setFuncion( "style='border:0px;'" );
								local_TextHelper.setReadonly(true);
								local_TextHelper.render( request, out );
               	} catch (HelperException re) {
                  out.println("ERROR " + re.getMessage());
	            }
	            %>
	          	&nbsp;
	          	</td>
	          	</tr>
              			</table>
              		<br>
				<%}%>    
              
              <br>
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Folios Segregación</td>
                  <td>&nbsp;</td>
                </tr>
                
                <form action="crearfolioadministracionoficio.do" method="post" name="" id="CREARDERIVADO">
                <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= gov.sir.core.web.acciones.administracion.AWAdministracionHermod.SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION%>" value="<%= gov.sir.core.web.acciones.administracion.AWAdministracionHermod.SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION%>">
                <tr>
                    <td>&nbsp;</td>
                    <td>
							
								<%if(foliosHijo.size()>0){
								//System.out.println("FOLIOHIJO*************** NO NULL");
								%>
          						<table width="100%" class="camposform">
          						<tr>
									<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
									<td>Matriculas</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>
									<% try {
										tablaHelper.setColCount(5);
										tablaHelper.setImagenes(imagenesPadre);
										tablaHelper.setInputName( "FOLIOEDIT_PADRESHIJOS_HIJO__ITEMS" );
										tablaHelper.setCheckboxComplementaryAttributes( "onclick='javascript:putValue(this);'" );
										tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
										tablaHelper.setListName(WebKeys.LISTA_FOLIOS_HIJO+CFolio.ID_MATRICULA);
										tablaHelper.render(request, out);
									} catch(org.auriga.core.web.HelperException re){
											out.println("ERROR " + re.getMessage());
									}
									%>
									</td>
								</tr>
								</table>
								<%}%>
								
                 </tr>
                 <tr> 
                 		<td>&nbsp;</td>
						<td>
						<table border="0" cellpadding="0" cellspacing="2" class="camposform">
                			<tr>
                				<td width="20">
                   				<a href="javascript:cambiarAccion( '<%=gov.sir.core.web.acciones.administracion.AWCrearFolioOficio.AGREGAR_DERIVADO%>')">
                    			<img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" alt="" />
                    			</a>
                    			</td>
                    			<td>Agregar</td>
                    			<td width="20">
                    			<td width="20">
                   				<a href="javascript:cambiarAccion( '<%=gov.sir.core.web.acciones.administracion.AWCrearFolioOficio.ELIMINAR_DERIVADO%>')" >
                    			<img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" alt="" />
                    			</a>
                    			</td>
                    			<td>Eliminar</td>
                			</tr>
			 			</table>
			 			</td>
				</tr> 
				
				<tr>
				<td>&nbsp;</td>
							<td>
							<table width="100%" class="camposform">
			               		<tr>
                 	   				<td>
					                   <table class="camposform" style="border:0px;">
        				     	          <tr>
                    					  	<td>N&uacute;mero matricula:
						                     <input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>" type="text" value="<%=idCirculo%>"  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>');" class="camposformtext" />
			                   			     <img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />
					                         </td>
         				 	             </tr>
                     					 <tr>
                       		   				<td>N&uacute;mero anotacion:
                       		   				<input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>" type="text" value=""  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>');" class="camposformtext" />
			                   				<img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />
		                       				</td>
                     					</tr>
                     					<tr>
                      						<td>Anotacion en este folio:
    			                			<input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID%>" type="text" value=""  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>');" class="camposformtext" />
            			        			<img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />
                      						</td>
                     					</tr>
						 			</table>
                      			</td>
	              			</tr>
			  			</table>
						</td>
					</tr> 

              </table>
             
             	<br>

              <br>
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Folios Englobados</td>
                  <td>&nbsp;</td>
                </tr>
                
                <tr>
                    <td>&nbsp;</td>
                    <td>
							
								<%if(foliosPadre.size()>0){
								//System.out.println("FOLIOPADRE*************** NO NULL");
								%>
          						<table width="100%" class="camposform">
          						<tr>
									<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
									<td>Matriculas</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>
									<% try {
										tablaHelper.setColCount(5);
										tablaHelper.setImagenes(imagenesPadre);
										tablaHelper.setInputName( "FOLIOEDIT_PADRESHIJOS_PADRE__ITEMS" );
										tablaHelper.setCheckboxComplementaryAttributes( "onclick='javascript:putValue(this);'" );
										tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
										tablaHelper.setListName(WebKeys.LISTA_FOLIOS_PADRE+CFolio.ID_MATRICULA);
										tablaHelper.render(request, out);
									} catch(org.auriga.core.web.HelperException re){
											out.println("ERROR " + re.getMessage());
									}
									%>
									</td>
								</tr>
								</table>
								<%}%>
                 </tr>
                 <tr> 
                 		<td>&nbsp;</td>
						<td>
						<table border="0" cellpadding="0" cellspacing="2" class="camposform">
                			<tr>
                				<td width="20">
                   				<a href="javascript:cambiarAccion( '<%=gov.sir.core.web.acciones.administracion.AWCrearFolioOficio.AGREGAR_ENGLOBE%>')">
                    			<img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" alt="" />
                    			</a>
                    			</td>
                    			<td>Agregar</td>
                    			<td width="20">
                    			<td width="20">
                   				<a href="javascript:cambiarAccion( '<%=gov.sir.core.web.acciones.administracion.AWCrearFolioOficio.ELIMINAR_ENGLOBE%>')" >
                    			<img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" alt="" />
                    			</a>
                    			</td>
                    			<td>Eliminar</td>
                			</tr>
			 			</table>
			 			</td>
				</tr> 
				
				<tr>
				<td>&nbsp;</td>
							<td>
							<table width="100%" class="camposform">
			               		<tr>
                 	   				<td>
					                   <table class="camposform" style="border:0px;">
        				     	          <tr>
                    					  	<td>N&uacute;mero matricula:
						                     <input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA_ENGLOBE%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA_ENGLOBE%>" type="text" value="<%=idCirculo%>"  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA_ENGLOBE%>');" class="camposformtext" />
			                   			     <img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA_ENGLOBE%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />
					                         </td>
         				 	             </tr>
                     					 <tr>
                       		   				<td>N&uacute;mero anotacion:
                       		   				<input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID_ENGLOBE%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID_ENGLOBE%>" type="text" value=""  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID_ENGLOBE%>');" class="camposformtext" />
			                   				<img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID_ENGLOBE%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />
		                       				</td>
                     					</tr>
                     					<tr>
                      						<td>Anotacion en este folio:
    			                			<input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID_ENGLOBE%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID_ENGLOBE%>" type="text" value=""  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA_ENGLOBE%>');" class="camposformtext" />
            			        			<img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID_ENGLOBE%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />
                      						</td>
                     					</tr>
						 			</table>
                      			</td>
	              			</tr>
			  			</table>
						</td>
					</tr> 

              </table>
                          
             	<br>

				<table class="camposform">
                <tr>
                   <td>&nbsp;</td>
                   <td>&nbsp;</td>
                   <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                    <td><a href="javascript:cambiarAccion( '<%=gov.sir.core.web.acciones.administracion.AWCrearFolioOficio.VOLVER_DERIVADO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
                    <td>&nbsp;</td>
                    <td><a href="javascript:cambiarAccion( '<%=gov.sir.core.web.acciones.administracion.AWCrearFolioOficio.CANCELAR_CREACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
            		<td>&nbsp;</td>
                    
                 </tr>
					
                </form>
              </table>

              
              <hr class="linehorizontal">
              
              					
                </form>
             
                             
              
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
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
