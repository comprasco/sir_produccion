<%@page import="java.util.List" %>
<%@page import="java.util.Enumeration" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import = "java.util.*"%>
<%@page import = "gov.sir.core.negocio.modelo.*"%>
<%@page import = "gov.sir.core.negocio.modelo.util.*"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWRelacion" %>
<%@page import="gov.sir.core.web.acciones.registro.AWMesa"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>

<%@page import="gov.sir.core.negocio.modelo.Relacion" %>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%@page import="gov.sir.core.negocio.modelo.Fase" %>

<%@page import="org.auriga.core.web.HelperException" %>

<%      
        boolean notificarNota = false;
        boolean firmarRegistro=false;
	boolean confrontacion = false;
        
	//VER SI SE VIENE DE MESA DE CONTROL
	boolean mesa = false;
	String sMesa = request.getParameter(AWMesa.MESA_CONTROL);
	if(sMesa== null){
		sMesa = (String) session.getAttribute( AWMesa.MESA_CONTROL );
	}
	if(sMesa!=null){
		session.setAttribute( AWMesa.MESA_CONTROL, sMesa );
                notificarNota = false;
		mesa = true;
	}	

	//VER SI SE VIENE DE MESA DE CONTROL CORRECCIONES
	boolean mesaC = false; 	

	String sMesaC = request.getParameter(AWMesa.MESA_CONTROL+"C");
	if(sMesaC== null){
		sMesaC = (String) session.getAttribute( AWMesa.MESA_CONTROL+"C" );
	}
	if(sMesaC!=null){
		session.setAttribute( AWMesa.MESA_CONTROL+"C", sMesaC );
                notificarNota = false;
		mesaC = true;
	}
	
	String firmarRegistroRelacion=request.getParameter("FIRMAR_REGISTRO_RELACION");
	if(firmarRegistroRelacion==null){
		firmarRegistroRelacion=(String)session.getAttribute("FIRMAR_REGISTRO_RELACION");
        } else{
		session.setAttribute("FIRMAR_REGISTRO_RELACION",firmarRegistroRelacion);
		mesaC=false;
		mesa=false;
                notificarNota = false; 
                confrontacion = false;
		firmarRegistro=true;
	}	

	String confrontacionRelacion=request.getParameter("CONFRONTACION");
	if(confrontacionRelacion==null)
		confrontacionRelacion=(String)session.getAttribute("CONFRONTACION");
	if(confrontacionRelacion!=null){
		session.setAttribute("CONFRONTACION",confrontacionRelacion);
		mesaC=false;
		mesa=false;
		firmarRegistro=false;
                notificarNota = false;   
		confrontacion = true;
	}
        
        String notificarNotaRelacion = request.getParameter("NOTIFICAR_NOTA_RELACION");
        if(notificarNotaRelacion==null)
               notificarNotaRelacion=(String)session.getAttribute("NOTIFICAR_NOTA_RELACION");
        if(notificarNotaRelacion!=null){
            session.setAttribute("NOTIFICAR_NOTA_RELACION",notificarNotaRelacion);
            mesaC=false;
            mesa=false;
            firmarRegistro=false;
            confrontacion=false;
            notificarNota = true;          
        }
	
	String certAsociados = (String)session.getAttribute(AWRelacion.AVANCE_TURNOS_AUTOMATICAMENTE);

	java.util.Hashtable errorTurnos = new java.util.Hashtable() ;
	java.util.Hashtable avanzadosTurnos = new java.util.Hashtable();	
	try{

	
	if (session.getAttribute(AWRelacion.ERRORES_RELACION) != null ) {
		errorTurnos = (java.util.Hashtable)session.getAttribute(AWRelacion.ERRORES_RELACION);
	} 
	
	
	if (session.getAttribute(AWRelacion.TURNOS_AVANZADOS_RELACION) != null) {
		avanzadosTurnos = (java.util.Hashtable)session.getAttribute(AWRelacion.TURNOS_AVANZADOS_RELACION);
	}
	}catch(Exception e){
	e.printStackTrace();}


	TextHelper textHelper = new TextHelper();
	TextAreaHelper textAreaHelper = new TextAreaHelper();
	ListaElementoHelper procesosHelper = new ListaElementoHelper();
	ListaElementoHelper fasesHelper = new ListaElementoHelper();
	
	boolean mostrarProcesos = false;
	boolean mostrarFases = false;
	boolean cargarDatos = false;

	String idProceso = null;
	String idFase = null;

	List elementos = null;
	
	Relacion relacion = (Relacion)session.getAttribute(AWRelacion.RELACION);
	idProceso = (String)session.getAttribute(AWRelacion.ID_PROCESO);
	idFase = (String)session.getAttribute(AWRelacion.ID_FASE);
	List fases = new ArrayList();

	if(relacion != null && idProceso != null && idFase != null) {
		session.setAttribute(AWRelacion.ID_RELACION, relacion.getIdRelacion());
	} else {
		// Se crea la lista de procesos
		procesosHelper = new ListaElementoHelper();
		elementos = (List)session.getAttribute(AWRelacion.LISTA_PROCESOS_RELACION);
		if(elementos == null) {
			cargarDatos = true;
			elementos = new ArrayList();
		}
		List procesos = new ArrayList();
		for(Iterator itElementos = elementos.iterator(); itElementos.hasNext();) {
			Proceso proceso = (Proceso)itElementos.next();
			procesos.add(new ElementoLista("" + proceso.getIdProceso(), proceso.getNombre()));
		}
		procesosHelper.setTipos(procesos);

		// Se crea la lista de fases
		fasesHelper = new ListaElementoHelper();
		elementos = (List)session.getAttribute(AWRelacion.LISTA_FASES_RELACION);
		if(elementos == null)
			elementos = new ArrayList();
		for(Iterator itElementos = elementos.iterator(); itElementos.hasNext();) {
			Fase fase = (Fase)itElementos.next();
			fases.add(new ElementoLista(fase.getID(), fase.getNombre()));
		}
		fasesHelper.setTipos(fases);
		
		mostrarProcesos = true;
		mostrarFases = true;
	}
%>

<script type="text/javascript">
	function cambiarAccion(text) {
		document.IMPRIMIR.<%= WebKeys.ACCION %>.value = text;
    	document.IMPRIMIR.submit();;
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
    	<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
    		<table border="0" cellpadding="0" cellspacing="0">
        		<tr> 
					<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          			<td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          			<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          			<td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          			<td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Imprimir Relaciones</td>
          			<td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        		</tr>
      		</table>
      	</td>
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
            		<td><img alt="image" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            		<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img alt="image" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            		<td><img alt="image" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          		</tr>
          		<tr>
            		<td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            		<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
            			<table border="0" cellpadding="0" cellspacing="0">
                			<tr>
                  				<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Impresi&oacute;n de Relaciones</td>
                  				<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  				<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      					<tr>
                        					<td><img alt="image" src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                        					<td><span class="bgnsub"><img alt="image" src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
                      					</tr>
                  					</table>
                  				</td>
                  				<td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                			</tr>
            			</table>
					</td>
            		<td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          		</tr>
				<tr>
            		<td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            		<td valign="top" bgcolor="#79849B" class="tdtablacentral">
            			
                        <br>

                        <form action="relacion.do" method="POST" name="IMPRIMIR" id="IMPRIMIR">
        					<input  type="hidden" name="<%= WebKeys.ACCION %>">
        				
        				<table width="100%" class="camposform">
							<%
								if(mostrarProcesos) {
							%>
							<tr>
								<td width="20"><img alt="image" src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
								<td width="160">Proceso</td>
								<td>
								<%
									try {
										procesosHelper.setNombre(AWRelacion.ID_PROCESO);
										procesosHelper.setCssClase("camposformtext");
										procesosHelper.setId(AWRelacion.ID_PROCESO);
										procesosHelper.setFuncion("onChange=\"cambiarAccion('" + AWRelacion.SELECCIONAR_PROCESO_IMPRESION + "')\"");
										procesosHelper.render(request, out);
									} catch(HelperException he) {
										out.println("ERROR " + he.getMessage());
									}
								%>
								</td>
							</tr>
							<%
								} else {
							%>
							<input type="hidden" name="<%= AWRelacion.ID_PROCESO %>" value="<%= idProceso %>">
							<%
								}

								if(mostrarFases) {
							%>
							<tr>
								<td width="20"><img alt="image" src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
								<td width="160">Fase</td>
								<td>
								<%
									try {
										fasesHelper.setNombre(AWRelacion.ID_FASE);
										fasesHelper.setCssClase("camposformtext");
										fasesHelper.setId(AWRelacion.ID_FASE);
										fasesHelper.setFuncion("onChange=\"cambiarAccion('" + AWRelacion.SELECCIONAR_FASE_IMPRESION + "')\"");
										fasesHelper.render(request, out);
									} catch(HelperException he) {
										out.println("ERROR " + he.getMessage());
									}
								%>
								<%
								if (certAsociados!=null && certAsociados.equals("true")&& fases!=null && fases.size()==0){
								%>
								<input type="hidden" name="<%= AWRelacion.ID_FASE %>" value="<%= idFase %>">									
								<%
								}
								%>
								</td>
							</tr>
							<%
								} else {
							%>
							<input type="hidden" name="<%= AWRelacion.ID_FASE %>" value="<%= idFase %>">
							<%
								}
							%>
                  			<tr>
                    			<td width="20"><img alt="image" src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    			<td width="160">N&uacute;mero de Relaci&oacute;n</td>
                    			<td>
                    			<%
									try {
                    					textHelper.setNombre(AWRelacion.ID_RELACION);
                  						textHelper.setCssClase("camposformtext");
                  						textHelper.setId(AWRelacion.ID_RELACION);
										if(relacion != null)
											textHelper.setEditable(false);
										textHelper.render(request,out);
										textHelper.setEditable(true);
									} catch(HelperException re) {
										out.println("ERROR " + re.getMessage());
									}
			  					%>
                    			</td>
                  			</tr>
                		</table>
						<br>
						<%
                  			if (avanzadosTurnos.size() > 0) {
						%>
        				<table width="100%" border="0" cellpadding="0" cellspacing="0">
           					<tr>
                    			<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    			<td class="bgnsub">Turnos Avanzados en la Relación</td>
                    			<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  			</tr>
                		</table>
                		<table class="camposform" width="100%">
                		 <tr>
                		 	<td width="100%">
							
                			<%
                			    java.util.List tempAux = new java.util.ArrayList();
                			    java.util.List temp = new java.util.Vector();
                  				Enumeration eavanzadosTurnos = avanzadosTurnos.keys();
								while (eavanzadosTurnos.hasMoreElements()){
									String itemkeyOk = (String)eavanzadosTurnos.nextElement();
									String turnoSplit[] = itemkeyOk.split("-");
									Turno turno = new Turno();
									turno.setAnio(turnoSplit[0]);
									turno.setIdProceso(Long.parseLong(turnoSplit[2]));
									turno.setIdCirculo(turnoSplit[1]);
									turno.setIdTurno(turnoSplit[3]);
									turno.setIdWorkflow(itemkeyOk);
									tempAux.add(turno);
								}
									
								 // Se deben ordenar los turnos correctamente
								try {
									Collections.sort(tempAux, new IDidTurnoComparator());
								} catch (Exception re) {
									out.println("ERROR " + re.getMessage());
								}

								for(Iterator iter = tempAux.iterator(); iter.hasNext();){
									temp.add(((Turno)iter.next()).getIdWorkflow());
								}
                  				session.setAttribute("TURNOS_VALIDOS",temp);
                  				                		
							%>                		 	
                		 	
							 <% try {
							 //System.out.println("33333");
	                                //HELPER TABLA MATRICULAS
									gov.sir.core.web.helpers.comun.TablaMatriculaHelper tablaHelper = 
									new gov.sir.core.web.helpers.comun.TablaMatriculaHelper();      
	                                tablaHelper.setColCount(5);
	                                tablaHelper.setListName("TURNOS_VALIDOS");
	                                //tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
	                                tablaHelper.render(request, out);
                                }catch(Exception re){
                                    out.println("ERROR " + re.getMessage());
                                }
                                //System.out.println("44444");
                             %>
                             
                		 	</td>
                		 </tr>
                		</table>
                		<%
                  			}
						%>
						<%
                  			if (errorTurnos.size() > 0) {
						%>
						<br>
        				<table width="100%" border="0" cellpadding="0" cellspacing="0">
           					<tr>
                    			<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    			<td class="bgnsub">Turnos NO Avanzados en la Relación</td>
                    			<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  			</tr>
                		</table>
                		<table  class="camposform" width="100%">
                			<%
                  				Enumeration eerrorTurnos = errorTurnos.keys();
								while (eerrorTurnos.hasMoreElements()){
									String itemkeyError = (String)eerrorTurnos.nextElement();
									String errorItemError = (String) errorTurnos.get(itemkeyError);
							%>
        							<tr>
        								<td width="120"><B><%=itemkeyError%></B></td>
        		            			<td width="600"><%=errorItemError%></td>
                		    		</tr>
                    		<%
                  				}
							%>
                		</table>
                		<%
                  			}
						%>
						<br>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
           					<tr>
                    			<td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    			<td class="bgnsub">Nota informativa</td>
                    			<td width="16" class="bgnsub"><img alt="" src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    			<td width="16" class="bgnsub"><img alt="" src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    			<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  			</tr>
                		</table>
						<br>
						<table width="100%" class="camposform">
				            <tr> 
			                  	<td width="20" valign="top"><img alt="" src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			                  	<td valign="top">Descripci&oacute;n</td>
			                  	<td colspan="2">
								<% 
									try {
										textAreaHelper.setCols("100");
										textAreaHelper.setReadOnly(false);
										textAreaHelper.setCssClase("camposformtext");
										textAreaHelper.setId(AWRelacion.DESCRIPCION_NOTA);
										textAreaHelper.setNombre(AWRelacion.DESCRIPCION_NOTA);
										textAreaHelper.setRows("10");
										textAreaHelper.render(request,out);
									} catch(HelperException re) {
										out.println("ERROR " + re.getMessage());
									}	
								%>
			                  	</td>
			                </tr>
						</table>
						<hr class="linehorizontal">
              			<table width="100%" class="camposform">
                  			<tr> 
                 				<td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                				<%
                                                    if(!notificarNota){
                				boolean isImprimir = ((Boolean)session.getAttribute(AWRelacion.BOTON_IMPRESION_RELACION)).booleanValue(); 
                				if(isImprimir){%>
                					<td width="150" align="center"><a href="javascript:cambiarAccion('<%= AWRelacion.IMPRIMIR %>')"><img  src="<%= request.getContextPath()%>/jsp/images/btn_imprimir.gif" width="139" height="21" border="0"></a></td>
                				<%}}%>
    							<%if(mesa || mesaC || firmarRegistro || confrontacion || notificarNota){%>
    								<td align="left"><a href="confirmacion.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_finalizar.gif" width="139" height="21" border="0"></a></td>
                  				<%}else if (certAsociados!=null && certAsociados.equals("true")){%>
									<td><a href="turnos.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></a></td>
                  				<%}else{%>
									<td><a href="admin.relacion.view?borrarListaTurnos=true"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></a></td>
                  				<%}%>



                  			</tr>
              			</table>
             			</FORM>   
            		</td>
            		<td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          		</tr>
				<tr>
            		<td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            		<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img alt="image" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
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
<% if(cargarDatos) { %>
<script>
	cambiarAccion('<%= AWRelacion.CARGAR_DATOS_IMPRESION %>');
</script>
<% } %>