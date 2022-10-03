<%@page import="gov.sir.core.negocio.modelo.CirculoImpresora"%>
<%@page import="gov.sir.core.negocio.modelo.TipoImprimible"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Date" %>

<%@page import="java.text.DateFormat" %>
<%@page import="gov.sir.core.util.DateFormatUtil" %>

<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.registro.AWMesa"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWRelacion" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>

<%@page import="gov.sir.core.negocio.modelo.Solicitud" %>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro" %>
<%@page import="gov.sir.core.negocio.modelo.Liquidacion" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="gov.sir.core.negocio.modelo.Documento" %>
<%@page import="gov.sir.core.negocio.modelo.Fase" %>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFase" %>
<%@page import="gov.sir.core.negocio.modelo.TipoRelacion" %>

<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.core.modelo.transferObjects.Rol" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRol" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoRelacion" %>
<%@page import="gov.sir.core.web.acciones.correccion.AWConfrontacion"%>

<%
	//VER SI SE VIENE DE MESA DE CONTROL REGISTRO O  DE FIRMAR REGISTRO
	boolean mesa = false;
	boolean firmarRegistro=false;
	boolean confrontacion = false;
	boolean notificarNota = false;
	boolean isCajeroCorrecciones = false;
	List rolesUsuario = (List) request.getSession().getAttribute(WebKeys.LISTA_ROLES);
    if(rolesUsuario!=null){
    	Iterator itRoles = rolesUsuario.iterator();
    	while(itRoles.hasNext()){
    		Rol rol = (Rol)itRoles.next();
    		if(rol.getRolId().equals(CRol.SIR_ROL_CAJERO_CORRECCIONES)){
    			isCajeroCorrecciones = true;
    			break;
    		}
    	}
    }  
	
	//TFS 3742: Cuando se avance un turno por relacion y se regresa de la pantalla, borre el listado de turnos, y 
	//vuelva a seleccionar las fases
	boolean borreListado = request.getParameter("borrarListaTurnos")!=null ? true : false;
	if(borreListado){
		session.removeAttribute(AWRelacion.ID_PROCESO);
		session.removeAttribute(AWRelacion.ID_FASE);
		session.removeAttribute(AWRelacion.ID_TIPO_RELACION);
		session.removeAttribute(AWRelacion.ID_RELACION);
		session.removeAttribute(AWRelacion.LISTA_TURNOS_RELACION);
		session.removeAttribute(AWRelacion.LISTA_ID_TURNOS);
		session.removeAttribute(AWRelacion.LISTA_PROCESOS_RELACION);
		session.removeAttribute(AWRelacion.LISTA_FASES_RELACION);
		session.removeAttribute(AWRelacion.LISTA_RELACIONES);
		session.removeAttribute(AWRelacion.DIGITAR_ID_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_VENCIMIENTO_MAYOR_VALOR);
		session.removeAttribute(AWRelacion.MOSTRAR_RESPUESTA_RELACION);
		session.removeAttribute(AWRelacion.RESPUESTA_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO);
	}	

	String sMesa = request.getParameter(AWMesa.MESA_CONTROL);
	if(sMesa== null){
		sMesa = (String) session.getAttribute( AWMesa.MESA_CONTROL );
	}
	if(sMesa!=null){
		session.setAttribute( AWMesa.MESA_CONTROL, sMesa );
		mesa = true;
                notificarNota = false;
		confrontacion = false;
	}	
	

	//VER SI SE VIENE DE MESA DE CONTROL CORRECCIONES
	boolean mesaC = false; 	

	String sMesaC = request.getParameter(AWMesa.MESA_CONTROL+"C");
	if(sMesaC== null){
		sMesaC = (String) session.getAttribute( AWMesa.MESA_CONTROL+"C" );
	}
	if(sMesaC!=null){
		session.setAttribute( AWMesa.MESA_CONTROL+"C", sMesaC );
		mesaC = true;
		firmarRegistro=false;
                notificarNota = false;
		confrontacion = false;
                
	}

	String firmarRegistroRelacion=request.getParameter("FIRMAR_REGISTRO_RELACION");
	if(firmarRegistroRelacion==null)
		firmarRegistroRelacion=(String)session.getAttribute("FIRMAR_REGISTRO_RELACION");
	if(firmarRegistroRelacion!=null){
		session.setAttribute("FIRMAR_REGISTRO_RELACION",firmarRegistroRelacion);
		mesaC=false;
		mesa=false;
                notificarNota = false;   
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
        if(notificarNotaRelacion==null){
               notificarNotaRelacion=(String)session.getAttribute("NOTIFICAR_NOTA_RELACION");
        }
        if(notificarNotaRelacion!=null){
            session.setAttribute("NOTIFICAR_NOTA_RELACION",notificarNotaRelacion);
            mesaC=false;
            mesa=false;
            firmarRegistro=false;
            confrontacion=false;
            notificarNota = true;          
        }
        
	// Se obtienen los turnos que cumplen el criterio de búsqueda (si existen)
	List turnosRelacion = (List)session.getAttribute(AWRelacion.LISTA_TURNOS_RELACION);

	TextHelper textHelper = new TextHelper();
	boolean cargarPagina = false;
	List elementos = null;

	// Se crea la lista de procesos
	ListaElementoHelper procesosHelper = new ListaElementoHelper();
	elementos = (List)session.getAttribute(AWRelacion.LISTA_PROCESOS_RELACION);
	String idProceso = (String)session.getAttribute(AWRelacion.ID_PROCESO);
	if(elementos == null) {
		cargarPagina = true;
		elementos = new ArrayList();
	}
	List procesos = new ArrayList();
	for(Iterator itElementos = elementos.iterator(); itElementos.hasNext();) {
		Proceso proceso = (Proceso)itElementos.next();		
		if(mesa || firmarRegistro || confrontacion){
			if(proceso.getIdProceso()== Long.parseLong(CProceso.PROCESO_REGISTRO) ){
				procesos.add(new ElementoLista("" + proceso.getIdProceso(), proceso.getNombre()));
			}
		}else if(mesaC){
			if(proceso.getIdProceso()== Long.parseLong(CProceso.PROCESO_CORRECCIONES) ){
				procesos.add(new ElementoLista("" + proceso.getIdProceso(), proceso.getNombre()));
			}
		}else{
			procesos.add(new ElementoLista("" + proceso.getIdProceso(), proceso.getNombre()));
		}
		
	}
	procesosHelper.setTipos(procesos);

	// Se crea la lista de fases
	ListaElementoHelper fasesHelper = new ListaElementoHelper();
	elementos = (List)session.getAttribute(AWRelacion.LISTA_FASES_RELACION);
	String idFase = (String)session.getAttribute(AWRelacion.ID_FASE);
	if(elementos == null)
		elementos = new ArrayList();
	List fases = new ArrayList();
	for(Iterator itElementos = elementos.iterator(); itElementos.hasNext();) {
		Fase fase = (Fase)itElementos.next();
		if(mesa){
			if(fase.getID().equals(CFase.REG_MESA_CONTROL)){
				fases.add(new ElementoLista(fase.getID(), fase.getNombre()));
			}
		}else if(mesaC){
			if(fase.getID().equals(CFase.COR_MESA_CONTROL)){
				fases.add(new ElementoLista(fase.getID(), fase.getNombre()));
			}
		}else if(firmarRegistro){
			if(fase.getID().equals(CFase.REG_FIRMAR))
				fases.add(new ElementoLista(fase.getID(), fase.getNombre()));
		}else if(confrontacion){
			if(fase.getID().equals(CFase.REG_CONFRONTAR))
				fases.add(new ElementoLista(fase.getID(), fase.getNombre()));
		}else if(notificarNota){
                        if(fase.getID().equals(CFase.NOT_NOTA_DEVOLUTIVA))
				fases.add(new ElementoLista(fase.getID(), fase.getNombre()));
                }else{
			fases.add(new ElementoLista(fase.getID(), fase.getNombre()));
		}
	}
	fasesHelper.setTipos(fases);

	// Se crea la lista de relaciones
	ListaElementoHelper relacionesHelper = new ListaElementoHelper();
	elementos = (List)session.getAttribute(AWRelacion.LISTA_RELACIONES);
	if(elementos == null)
		elementos = new ArrayList();
	List relaciones = new ArrayList();
	for(Iterator itElementos = elementos.iterator(); itElementos.hasNext();) {
		TipoRelacion relacion = (TipoRelacion)itElementos.next();
		if(!relacion.getIdTipoRelacion().equals(CTipoRelacion.ID_SOLICITUDES_DE_CORRECCIONES)){
			relaciones.add(new ElementoLista(relacion.getIdTipoRelacion(), relacion.getNombre()));
		}else if(isCajeroCorrecciones){
			relaciones.add(new ElementoLista(relacion.getIdTipoRelacion(), relacion.getNombre()));
		}
	}
	relacionesHelper.setTipos(relaciones);

	String idRelacion = (String)session.getAttribute(AWRelacion.ID_RELACION);        
	if(idRelacion == null)
		idRelacion = "";

	// Se obtiene la lista de turnos
	List turnos = (List)session.getAttribute(AWRelacion.LISTA_TURNOS_RELACION);
   	if(turnos == null)
		turnos = new ArrayList();

	boolean mostrarNumeroRelacion = false;
	boolean mostrarDetalleRelacion = false;
	boolean mostrarFechaVencimientoMayorValor = false;
	boolean mostrarNumeroDocumento = false;
	boolean mostrarimprimible = false;
        boolean mostrarimprimible2 = false;
	boolean mostrarRespuestaRelacion = false;
	String respuestaRelacion = (String)session.getAttribute(AWRelacion.RESPUESTA_RELACION);
	
	Boolean tempBoolean = (Boolean)session.getAttribute(AWRelacion.DIGITAR_ID_RELACION);
	if(tempBoolean != null) {
		mostrarNumeroRelacion = tempBoolean.booleanValue();
	}

	tempBoolean = (Boolean)session.getAttribute(AWRelacion.MOSTRAR_DETALLE_RELACION);
	if(tempBoolean != null) {
		mostrarDetalleRelacion = tempBoolean.booleanValue();
	}

	tempBoolean = (Boolean)session.getAttribute(AWRelacion.MOSTRAR_VENCIMIENTO_MAYOR_VALOR);
	if(tempBoolean != null) {
		mostrarFechaVencimientoMayorValor = tempBoolean.booleanValue();
	}

	tempBoolean = (Boolean)session.getAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO);
	if(tempBoolean != null) {
		mostrarNumeroDocumento = tempBoolean.booleanValue();
	}
	
	tempBoolean = (Boolean)session.getAttribute(AWRelacion.MOSTRAR_RESPUESTA_RELACION);
	if(tempBoolean != null) {
		mostrarRespuestaRelacion = tempBoolean.booleanValue();
	}
	tempBoolean = (Boolean)session.getAttribute(AWRelacion.VER_IMP);
	if(tempBoolean != null) {
		mostrarimprimible = tempBoolean.booleanValue();
	}
        tempBoolean = (Boolean)session.getAttribute(AWRelacion.VER_IMP2);
	if(tempBoolean != null) {
		mostrarimprimible2 = tempBoolean.booleanValue();
	}
	if (respuestaRelacion == null){
		mostrarRespuestaRelacion = false;
	}
%>


<script type="text/javascript">
	function cambiarAccion(text) {
		document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
    	document.BUSCAR.submit();;
	}

	function cambiarAccionCrear(text) {
		document.getElementById('id_btn_aceptar').width=0;
		document.CREAR.<%= WebKeys.ACCION %>.value = text;
    	document.CREAR.submit();;
	}

	function seleccionarTodos(checkbox) {
		var lengthLista = 0;
		if(document.CREAR.<%= AWRelacion.LISTA_ID_TURNOS %>)
			lengthLista = document.CREAR.<%= AWRelacion.LISTA_ID_TURNOS %>.length;

		if(checkbox.checked) {
			if(lengthLista == null) {
				document.CREAR.<%= AWRelacion.LISTA_ID_TURNOS %>.checked = true;
			} else {
				for(i = 0; i < lengthLista; i++) {
					if(!document.CREAR.<%= AWRelacion.LISTA_ID_TURNOS %>[i].checked)
						document.CREAR.<%= AWRelacion.LISTA_ID_TURNOS %>[i].checked = true;
				}
			}
		} else {
			if(lengthLista == null) {
				document.CREAR.<%= AWRelacion.LISTA_ID_TURNOS %>.checked = false;
			} else {
				for(i = 0; i < lengthLista; i++) {
					if(document.CREAR.<%= AWRelacion.LISTA_ID_TURNOS %>[i].checked)
						document.CREAR.<%= AWRelacion.LISTA_ID_TURNOS %>[i].checked = false;
				}
			}
		}
	}
</script>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	
  	
  	<tr> 
    	<td>&nbsp;</td>
    	<td>&nbsp;</td>
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
                  				<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Crear una Relaci&oacute;n </td>
                  				<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  				<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      					<tr>
                        					<td></td>
                        					<td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
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
            			<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  		<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  			<tr>
                    			
                    			<td class="bgnsub">Datos B&aacute;sicos </td>
                    			<td width="16" class="bgnsub"></td>
                    			<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  			</tr>
                		</table>
                		<table width="100%" class="camposform">
							<form action="relacion.do" method="POST" name="BUSCAR" id="BUSCAR">
        						<input  type="hidden" name="<%= WebKeys.ACCION %>">
                  			<tr>
                    			<td width="20"></td>
                    			<td width="160">Proceso</td>
                    			<td>
                    			<% 
									try {
                    					procesosHelper.setNombre(AWRelacion.ID_PROCESO);
                  						procesosHelper.setCssClase("camposformtext");
                  						procesosHelper.setId(AWRelacion.ID_PROCESO);
										procesosHelper.setFuncion("onChange=\"cambiarAccion('" + AWRelacion.SELECCIONAR_PROCESO + "')\"");
										procesosHelper.render(request,out);
									} catch(HelperException re) {
										out.println("ERROR " + re.getMessage());
									}
			  					%>
                    			</td>
                  			</tr>
                  			<tr>
                    			<td width="20"></td>
                    			<td width="160">Fase</td>
                    			<td>
                    			<% 
									try {
                    					fasesHelper.setNombre(AWRelacion.ID_FASE);
                  						fasesHelper.setCssClase("camposformtext");
										fasesHelper.setId(AWRelacion.ID_FASE);
										fasesHelper.setFuncion("onChange=\"cambiarAccion('" + AWRelacion.SELECCIONAR_FASE + "')\"");
										fasesHelper.render(request,out);
									} catch(HelperException re) {
										out.println("ERROR " + re.getMessage());
									}
			  					%>
                    			</td>
                  			</tr>
                  			<tr>
                    			<td width="20"></td>
                    			<td width="160">Relaci&oacute;n</td>
                    			<td>
                    			<%                                       
									try {
                    					relacionesHelper.setNombre(AWRelacion.ID_TIPO_RELACION);
                  						relacionesHelper.setCssClase("camposformtext");
                  						relacionesHelper.setId(AWRelacion.ID_TIPO_RELACION);                                                                
										relacionesHelper.setFuncion("onChange=\"cambiarAccion('" + AWRelacion.SELECCIONAR_RELACION + "')\"");
										relacionesHelper.render(request,out);
									} catch(HelperException re) {
										out.println("ERROR " + re.getMessage());
									}
			  					%>
                    			</td>
							</tr>
							
							<%
								if(mostrarNumeroRelacion) {
							%>
				  			<tr>
                    			<td width="20"></td>
                    			<td width="160">N&uacute;mero de Relaci&oacute;n</td>
                    			<td>
									<table width="100%" border="0">
										<tr>
											<td>
                    						<% 
												try {
                    								textHelper.setNombre(AWRelacion.ID_RELACION);
                  									textHelper.setCssClase("camposformtext");
                  									textHelper.setId(AWRelacion.ID_RELACION);
													textHelper.render(request,out);
												} catch(HelperException re) {
													out.println("ERROR " + re.getMessage());
												}
			  								%>
											</td>
											<td>
												<a onClick="cambiarAccion('<%=AWRelacion.INGRESAR_RELACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="150" height="21" border="0"></a>	
											</td>
											<%
												if(mostrarDetalleRelacion) {
											%>
											<td>
												<a onClick="cambiarAccion('<%=AWRelacion.VER_DETALLE_RELACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_verdetalles.gif" width="150" height="21" border="0"></a>	
											</td>
											<%
												}
											%>
										</tr>
									</table>
                    			</td>
                  			</tr>
							<%
								}
							%>
							</form>
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
		
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
        	<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        		<tr>
            		<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            		<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            		<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        		</tr>
        		<tr>
          			<td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          			<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
	          			<table border="0" cellpadding="0" cellspacing="0">
	                		<tr>
	                  			<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turnos Disponibles</td>
	                  			<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                  			<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                      				<tr>
	                        				<td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
	                        				<td></td>	
	                      				</tr>
	                  				</table>
	                 			</td>
	                  			<td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
	                		</tr>
	          			</table>
          			</td>
          			<td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        		</tr>
        		<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        		<tr>
					<form action="relacion.do" method="POST" name="CREAR" id="CREAR">
        				<input type="hidden" name="<%= WebKeys.ACCION %>" value="<%= AWRelacion.CREAR_RELACION   %>">
						<input type="hidden" name="<%= AWRelacion.ID_RELACION %>" value="<%= idRelacion %>">
        			<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        			<td valign="top" bgcolor="#79849B" class="tdtablacentral">
            			<table width="100%" class="camposform">
	      					<tr>
	            				<td width="20"></td>
	              				<td>Listado
	              				<% 
								if(mostrarRespuestaRelacion) { 
								%>
									de Turnos <%=respuestaRelacion%>	
								<%}%>
	              				</td>
	              				<td>&nbsp;</td>
								<td>&nbsp;</td>
								<% 
									if(mostrarFechaVencimientoMayorValor) { 
								%>
								<td>&nbsp;</td>
								<% 
									} 
								%>
								<td>&nbsp;</td>
	            			</tr>
                			<tr>
                  				<td width="10"></td>
								<td class="titulotbcentral">Agregar a Relaci&oacute;n</td>
								
                  				<td class="titulotbcentral">Número de Turno</td>
								<td class="titulotbcentral">Fecha de Radicaci&oacute;n </td>
                                                                <td class="titulotbcentral">Origen </td>
								<%
									if(mostrarNumeroDocumento) {
								%>
                  				<td class="titulotbcentral">N&uacute;mero de Documento</td>
								<% 
									} 

									if(mostrarFechaVencimientoMayorValor) {  
								%>
								<td class="titulotbcentral">Fecha de Vencimiento Mayor Valor</td>
								<% 
									} 
                                                                       if(mostrarimprimible){
                                                            	%>
				  				<td class="titulotbcentral">Visualizar</td>
								<% 
                                                                        }    if(mostrarimprimible2){
                                                                 %>
                                                                 <td class="titulotbcentral">Visualizar</td>
                                                                 <% 
                                                                        }    
                                                                 %>
                                        </tr> 
							<tr>
                  				<td>&nbsp;</td>
								<td width="30" class="titulotbcentral" align="left"><input type="checkbox" name="TODOS" value="TODOS" onClick="seleccionarTodos(this)"></td>
                  				<td class="titulotbcentral">&nbsp;</td>
								<td class="titulotbcentral">&nbsp;</td>
								<%
									if(mostrarNumeroDocumento) {
								%>
                  				<td class="titulotbcentral">&nbsp;</td>
								<% 
									}

									if(mostrarFechaVencimientoMayorValor) { 
								%>
								<td class="titulotbcentral">&nbsp;</td>
								<% 
									} 
								%>
				  				
							</tr> 
              				<%  
								Solicitud solicitud;
								int totalTurnos = turnos.size();
                                                               
                				for(Iterator iter = turnos.iterator(); iter.hasNext();) {
                			%>   
                			<tr>
                  				<td>&nbsp;</td>
								<%
									Turno turnoActual = (Turno)iter.next();
								%>
								<td width="35" align="center"><input type="checkbox" name="<%= AWRelacion.LISTA_ID_TURNOS %>" value="<%= turnoActual.getIdWorkflow() %>"></td>
                  				<td class="campositem"><%= turnoActual.getIdWorkflow() %></td>
                  				<td class="campositem"><%= DateFormatUtil.format(turnoActual.getFechaInicio()) %></td>
                                                <% String turnoREL = (turnoActual.getTurnoREL() != null? "REL":"SIR");%> 
                                                <td class="campositem"><%=turnoREL%></td> 
								<%	
									if(mostrarNumeroDocumento) {
										solicitud = turnoActual.getSolicitud();
										Documento documento = ((SolicitudRegistro)solicitud).getDocumento();
										String numeroDocumento = documento.getNumero();
								%>
								<td class="campositem"><%= numeroDocumento %></td>
								<%
									}
					
									if(mostrarFechaVencimientoMayorValor) {
										solicitud = turnoActual.getSolicitud();
										List liquidaciones = solicitud.getLiquidaciones();
										Date fechaVencimiento = null;
										if(liquidaciones.size() > 1) {
											Liquidacion ultimaLiquidacion = (Liquidacion)liquidaciones.get(liquidaciones.size() - 1);
											fechaVencimiento = ultimaLiquidacion.getFecha();
										}
								%>
								<td class="campositem"><%= fechaVencimiento != null ? DateFormatUtil.format(fechaVencimiento) : "" %></td>
								<%
									}
								if(mostrarimprimible){
                                                                      
                                                           	%>
                                                                	<td  >
                                                                        <a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?ServRE=<%=turnoActual.getIdWorkflow()%>','name','width=800,height=600')">
                                                                         <img src="<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif" name="Folio"  id="Folio"/>
                                                                       </a>
                                                                         </td>
								<% 
                                                                } if(mostrarimprimible2){
                                                                 %>
                                                                 <td >
                                                                         <a target="popup"  onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?Calf=<%=turnoActual.getIdWorkflow()%>','name','width=800,height=600')">
                                                                            <img src="<%=request.getContextPath()%>/jsp/images/btn_observar_formularioC.gif" name="Folio" border="0" width="139" height="21"  id="Folio"/>
                                                                          </a>

                                                                        <a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?ServRE=<%=turnoActual.getIdWorkflow()%>','name','width=800,height=600')">
                                                                         <img src="<%=request.getContextPath()%>/jsp/images/btn_observar_notaD.gif" name="Folio1" border="0" width="139" height="21"  id="Folio1"/>
                                                                       </a>
                 
                                                              </td>
                                                                 <% 
                                                                } 
                                                                 %>
                                        </tr>
                			<% 
								}
							%>
						</table>
						<table width="15%" class="camposform">
								<tr>
                  				<td class="titulotbcentral">Total de Turnos:</td>
                  				<td class="titulotbcentral"><%=totalTurnos%></td>
                  				</tr>
                  				</table>
						<hr class="linehorizontal">
                        
                        <table width="100%" class="camposform">
                            <% 
                                if(idFase != null){
                               if(idFase.equals(CFase.REG_FIRMAR)){
                                                                 %>
                            <tr>
                                <td>Seleccionar impresora:</td>
                                                      <td>	
                                              <% 
                                                      try { 
                                                                              //IDCIRCULO
                                                                              String idCirculo = "";
                                                                              if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
                                                                                      idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
                                                                              }

                                                  String key = WebKeys.LISTA_IMPRESORAS+"_"+idCirculo;
                                                          java.util.List tipos = new ArrayList();
                                                  Map configuracion=(Map)session.getAttribute(key);
                                                  if (configuracion!=null){
                                                  Iterator itImpr=configuracion.keySet().iterator();
                                                                      while(itImpr.hasNext()){
                                                                              TipoImprimible impr=(TipoImprimible)itImpr.next();

                                                                                      List impresoras=(List)configuracion.get(impr);
                                                                                      int j=0;
                                                                                      Iterator itImpresoras=impresoras.iterator();

                                                                                      while(itImpresoras.hasNext()){
                                                                                              CirculoImpresora circImp=(CirculoImpresora)itImpresoras.next();
                                                                                              Iterator itTipos=tipos.iterator();
                                                                                              boolean agregar=true;

                                                                                              while(itTipos.hasNext()){
                                                                                                      ElementoLista el=(ElementoLista)itTipos.next();
                                                                                                      if (el.getId().equals(circImp.getIdImpresora())){
                                                                                                              agregar=false;
                                                                                                              break;
                                                                                                      }
                                                                                              }
                                                                                              if (agregar){
                                                                                                      ElementoLista elem=new ElementoLista();
                                                                                                      elem.setId(circImp.getIdImpresora());
                                                                                                      elem.setValor(circImp.getIdImpresora());
                                                                                                      tipos.add(elem);
                                                                                              }
                                                                                      }

                                                                      }
                                                  }
                                                 java.util.Vector impresoras = new java.util.Vector();

                                                  if(session.getAttribute("IMPRESORA") == null)
                                                  {
                                                              session.setAttribute("IMPRESORA",request.getParameter("IMPRESORA") );
                                                      }


                                                                  gov.sir.core.web.helpers.comun.ListaElementoHelper impresorasHelper = new gov.sir.core.web.helpers.comun.ListaElementoHelper();
                                                              impresorasHelper.setTipos(tipos);
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
                            </tr>
                            <tr>
                                    <td>&nbsp;</td>
                             </tr>
                              <tr>
                                    <td>&nbsp;</td>
                             </tr>
                            <tr>
                              <% 
								}
                        }
							%>
                                <td width="155">
                                    <a onClick="cambiarAccionCrear('<%=AWRelacion.CREAR_RELACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" id="id_btn_aceptar" width="150" height="21" border="0"></a>
                                </td>
                                <td>
									<%if(mesa){%>
									<a href="turno.registro.mesa.control.pre.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="150" height="21" border="0"></a>
                                	<%}else if(mesaC){%>
									<a href="correccion.mesa-control.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="150" height="21" border="0"></a>
                                	<%}else if(firmarRegistro){%>
									<a href="turno.registro.firma.registrador.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="150" height="21" border="0"></a>
									<%}else if(confrontacion){%>
									<a href="confrontacion.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="150" height="21" border="0"></a>
									<%}else if(notificarNota){%>
                                                                        <a href="notificar.nota.devolutiva.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="150" height="21" border="0"></a>
                                                                        <%} else{%>
									<a onClick="cambiarAccionCrear('<%=AWRelacion.TERMINA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="150" height="21" border="0"></a>
									<%}%>
                                </td>
                                <td>&nbsp;</td>
                            </tr>
                        </table>
          			</td>
          			<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
					</form>
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
  	
</table>
<% if(cargarPagina) { %>
<script>
	cambiarAccion('<%= AWRelacion.CARGAR_DATOS %>');
</script>
<% } %>