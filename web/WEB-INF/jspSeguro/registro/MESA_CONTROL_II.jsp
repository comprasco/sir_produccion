<%@page import="gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CActo"%>
<%@page import="gov.sir.core.negocio.modelo.Acto"%>
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.registro.AWMesa"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudCertificado"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.TurnoHistoria"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRespuestaMesaControl"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRespuestaCalificacion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRespuesta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.auriga.core.web.HelperException"%>

<%	 
	//SE OBTIENE LA INFORMACIÓN DEL TURNO
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);


	//SE OBTIENE LA HISTORIA DEL TURNO
	List turnosHistoria = new ArrayList();
	if(turno.getHistorials() != null){
		turnosHistoria = turno.getHistorials();
	} 
	
	
	String respuesta = null;
	TurnoHistoria thUltimo = null;
	if(turnosHistoria!=null && turnosHistoria.size()>0){
		thUltimo = (TurnoHistoria)turnosHistoria.get(turnosHistoria.size()-1);
		
    	for (Iterator iterator = turnosHistoria.iterator(); iterator.hasNext();){
        	TurnoHistoria turnoAux = (TurnoHistoria) iterator.next();
        	
        	if(turnoAux.getFase() != null && (turnoAux.getFase()).equals(gov.sir.core.negocio.modelo.constantes.CFase.CAL_CALIFICACION)){				
				respuesta = turnoAux.getRespuesta();	     
        	}	

        	if(turnoAux.getFase() != null && (turnoAux.getFase()).equals(gov.sir.core.negocio.modelo.constantes.CFase.REG_TESTAMENTO)){				
				respuesta = turnoAux.getRespuesta();	     
        	}	
            	
        }
	}	
	
	
	if(respuesta!=null){
		if(respuesta.equals(CRespuestaCalificacion.RESPUESTA_INSCRITO) || respuesta.equals(CRespuesta.CONFIRMAR) ){
			respuesta = "INSCRITO";
		}else if(respuesta.equals(CRespuestaCalificacion.RESPUESTA_INSCRIPCION_PARCIAL)){
			respuesta = "INSCRITO PARCIALMENTE";
		}else if(respuesta.equals(CRespuestaCalificacion.RESPUESTA_DEVUELTO)  || respuesta.equals(CRespuesta.NEGAR) ){
			respuesta = "DEVUELTO";
		}else{
			respuesta = "&nbsp;";
		}
	}
	//FIN DE --SE OBTIENE LA RESPUESTA DE CALIFICACIÓN--

	//VALIDACIONES PARA DETERMINAR SI LOS CERTIFICADOS ASOCIADOS YA SE IMPRIMIERON.
	List certAsociados=new Vector();
	List valido=new Vector();
	boolean carga=false;
	boolean devuelto=false;

	/*se obtienen los datos del turno-->SolicitudRegistro-->SolictudesHijas-->SolicitudesCertificado*/
	List matriculasTurno= (List) session.getAttribute(AWMesa.LIST_SOL_FOLIO_ASOCIADAS);
	
	certAsociados= (List) request.getSession().getAttribute(AWMesa.LISTA_CERTIFICADOS_ASOCIADOS);
	valido= (List) request.getSession().getAttribute(AWMesa.LISTA_VALIDO);
	if(valido==null){
		valido= new Vector();
	}
	if(certAsociados==null || matriculasTurno == null){
		carga=true;
		certAsociados= new Vector();
	}
	

	session.setAttribute("CERRAR_VENTANA", new Boolean(false));
	//saber si tiene q cerrarse
	Boolean oCarga= (Boolean)session.getAttribute("CARGAR_MESA_CONTROL");
	if(oCarga!=null){
		carga= oCarga.booleanValue();
	}
	

	
	//Se mira si existen exceptciones.
	Boolean exception;
	exception = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
	
	boolean hayExcepcion = false;
	
	if(exception!=null){
		session.removeAttribute(WebKeys.HAY_EXCEPCION);		 
		hayExcepcion = true;
	}
	
	if(!hayExcepcion && carga){
		carga = true;
	}else{
		carga = false;
	}
	
    %>

  
   
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
       document.BUSCAR.<%= WebKeys.ACCION %>.value = accion;
       document.BUSCAR.submit();
}		

function cambiarAccion(accion, numcert) {
       document.BUSCAR.<%= WebKeys.ACCION %>.value = accion;
       document.BUSCAR.<%= AWMesa.NUM_CERT %>.value= numcert;
       document.BUSCAR.submit();
}	

function cambiarAccion(accion, numcert, nummat) {
       document.BUSCAR.<%= WebKeys.ACCION %>.value = accion;
       document.BUSCAR.<%= AWMesa.NUM_CERT %>.value= numcert;
       document.BUSCAR.<%= AWMesa.NUM_MAT %>.value= nummat;
       document.BUSCAR.submit();
}	

function cambiarSeleccion() {
    if(document.BUSCAR.seleccionado.checked == true){
	    setAllCheckBoxes('BUSCAR', '<%=AWMesa.NUM_CERT%>', true);
    }
    if(document.BUSCAR.seleccionado.checked == false){
    	setAllCheckBoxes('BUSCAR', '<%=AWMesa.NUM_CERT%>', false);
    }
}

function cambiarMatricula(nombre,valor, dimensiones) {
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}

function cargaInicial() {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%= AWMesa.VALIDAR_SOLICITUDES %>';
	document.BUSCAR.submit();
}

</script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<form action="mesa.do" method="POST" name="BUSCAR" id="BUSCAR">
<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="">
<input  type="hidden" name="<%= AWMesa.NUM_MAT %>" id="<%= AWMesa.NUM_MAT %>" value="">
<input  type="hidden" name="<%= AWMesa.NUM_CERT %>" id="<%= AWMesa.NUM_CERT %>" value="">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
 
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Certificados Asociados
  
                  </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    
                  </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr> 
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"> 
              <table width="100%" class="camposform">
              	<tr>
              		<td></td>
              		<td colspan="3" class="titulotbcentral">Respuesta de Calificaci&oacute;n </td>
              	</tr>
              	<tr>
                  <td width="1%">&nbsp;</td>
                  <td width="25%" align="left">Respuesta Calificaci&oacute;n:</td>
                  <td width="25%" align="left" class="campositem"><%=respuesta%></td>
                  <td width="50%">&nbsp;</td>
                </tr>
              </table>
                  
                 
               <%
                    if(!carga){  
                      if(respuesta.equals("INSCRITO PARCIALMENTE")){
                          JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO ();
                  System.out.println("Turno inscrip parcial "+turno.getIdWorkflow());
                  System.out.println("Solicitud inscrip parcial "+turno.getSolicitud().getIdSolicitud());
        
                  String actoEmbargo = jdoTurnosDAO.getActoSolicitud(turno.getSolicitud().getIdSolicitud());
                  
                  System.out.println("Tiene acto "+actoEmbargo);
                      
                  
                  if(actoEmbargo!=null && !actoEmbargo.equals("")){
                      System.out.println("por que entro aca?");
                      
                  %>
                  
                   <table width="100%" class="camposform">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td></td>
                  <td colspan="6" class="titulotbcentral">Lista de certificados asociados </td>
                </tr>
                <tr height="20">
                  <td width="1%">&nbsp;</td>
                  <td width="25%" align="center">Certificado</td>
                  <td width="25%" align="center">Matricula</td>
                  <td width="15%">&nbsp;</td>
                  <td width="7%" align="center"># copias</td>
                  <td width="5%" align="center">Imprimir</td>
                  <td width="13%">&nbsp;</td>
                </tr>
                <tr>
                  <td colspan="7">&nbsp;</td>
                </tr>
                <%	int certificadosAImprimir = 0;
	                int certificadosACambiarMatricula = 0;
                	if(!carga){
                	
	                	Iterator itCert= certAsociados.iterator();
	                	for(int contCert=0;itCert.hasNext(); contCert++){
	                		//se inicializara el boolean si es valido para impresion o no
	                		boolean vimprimir;
	                		Boolean btemp=(Boolean)valido.get(contCert);
	                		vimprimir= btemp.booleanValue();
                                        
                                        //Boolean btemp=(Boolean) true;
//	                		vimprimir= true;
                                        
                                        System.out.println("Solicitud btemp "+btemp);
                                        System.out.println("Solicitud vimprimir "+vimprimir);
                                        
	                		SolicitudCertificado solCert= (SolicitudCertificado) itCert.next();
	                		//se imprime la fila donde se muestran los datos de la solicitud de certificado
	                		String idCertificado="&nbsp;";
	                		String numCopias="1";
	                		
	                		List solicitudesFolio=new Vector();
	                		if(solCert!=null){
    	                		// Se obtiene el ID del turno
    	                		if(solCert.getTurno() != null) {
        	                		idCertificado = solCert.getTurno().getIdWorkflow();
    	                		}
		                		/*if(solCert.getIdSolicitud()!=null){
		                			if( !solCert.getIdSolicitud().equals("")){
		                				idCertificado=solCert.getIdSolicitud();
		                			}
		                		}*/
		                	  	if(solCert.getNumeroCertificados()>0){
		                	  		numCopias=Integer.toString(solCert.getNumeroCertificados());
		                	  	}
		                	  	
		                	  	solicitudesFolio=solCert.getSolicitudFolios();
                                                System.out.println("Solicitud folio "+solCert.getSolicitudFolios());
		                	  	if(solicitudesFolio==null || solicitudesFolio.size()==0){
		                	  		vimprimir=false;
		                	  	}
		                	}
                		%>
	                		<tr height="17">
			                  <td>&nbsp;</td>
			                  <td class="campositem"><%= idCertificado%></td>
			                  <td> &nbsp;</td>
			                  <td>&nbsp;</td>
			                  <td class="campositem" align="center"><%= numCopias%></td>
			                  <%if(vimprimir){
								certificadosAImprimir++;
			                  %>
			                  	<td align="center" height="20"><input name="<%= AWMesa.NUM_CERT%>" type="checkbox" value="<%= contCert %>"></td>
			                  <%	}else{%>
			                  	<td align="center">&nbsp;</td>
			                  <%}%>
			                  <td>&nbsp;</td>
			                </tr>
			                <%
			                if(solicitudesFolio.size()>0){
			                	Iterator isolf= solicitudesFolio.iterator();		 
			                	for(int contMat=0;isolf.hasNext(); contMat++){
			                		SolicitudFolio solF= (SolicitudFolio) isolf.next();
			                		String numMatricula="";
			                		if(solF!=null){
			                			if(solF.getIdMatricula()!=null && !solF.getIdMatricula().equals("")){
			                				numMatricula=solF.getIdMatricula();
			                			}
			                		}
			                		
			                		%>
			                		
			                		<tr>
					                  <td>&nbsp;</td>
					                  <%if(isolf.hasNext()){%>
					                  	<td align="right"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/linea_hijos.gif" width="32" height="17" border="0"></td>
					                  <%}else{%>
					                  	<td align="right"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/linea_hijos_final.gif" width="32" height="17" border="0"></td>
					                  <%}%>
					                  <td class="campositem"><%=numMatricula%></td>
					                  <%if(vimprimir){%>				                  
					                  	<td><a href="javascript:cambiarMatricula('cambiarmatricula.mesa.control.view?MATRICULA=<%= numMatricula%>&NUM_CERT=<%= contCert%>&NUM_MAT=<%= contMat%>','Cambio_matricula','width=900,height=450,scrollbars=yes')"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_matricula.gif" width="180" height="20" border="0" alt="modificar # de matricula"><a></td>
					                  <%	}else{%>
					                  	<td height="22">&nbsp;</td>
					                  <%}%>
					                  <td>&nbsp;</td>
					                  <td>&nbsp;</td>	
					                  <td>&nbsp;</td>		          
					                </tr>
			                		<%
			                	}
			                
			                	
			               	 } else{
			               	 	String numMatricula=" ";
			               	 	certificadosACambiarMatricula++;
			               	 	%>
			                		<tr height="20">
						                <td>&nbsp;</td>
						                <td align="right"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/linea_hijos_final.gif" width="32" height="17" border="0">
						                <td class="campositem"><%=numMatricula%></td>
						                <td><a href="javascript:cambiarMatricula('cambiarmatricula.mesa.control.view?MATRICULA=<%= numMatricula%>&NUM_CERT=<%= contCert%>&NUM_MAT=0','Cambio_matricula','width=900,height=450,scrollbars=yes')"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_matricula.gif" width="180" height="20" border="0" alt="modificar # de matricula"><a></td></td>
				               			<td>&nbsp;</td>	
						                <td>&nbsp;</td>
					            	</tr>
			                	<%	}
			                	%>
			                	<tr>
				                  <td colspan="6" class="lineaseparador">&nbsp;</td>
				                  <td>&nbsp;</td>
				                </tr>
				                <%		
				            
			                
	                	}
                	}
                	if(certAsociados.size()>0){
                	%>
                	<tr>
	                  <td>&nbsp;</td>
	                  <td>&nbsp;</td>
	                  <td>&nbsp;</td>
	                  <td align="right">Seleccionar todas :</td>
	                  <td>&nbsp;</td>
	                  <td align="center"><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'></td>
	                  <td>&nbsp;</td>
	                </tr>
	                <%if(certificadosAImprimir>0){%>
                	<input type='hidden' name="<%=AWMesa.CERTIFICADOS_A_IMPRIMIR%>" value="<%=""+(certificadosAImprimir)%>">
                	<input type='hidden' name="<%=AWMesa.CERTIFICADOS_A_CAMBIAR_MATRICULA%>" value="<%=""+(certificadosACambiarMatricula)%>">                	
                	<%}
                	}%>
                		
                	
                
              </table>
                  
                 <% 
                  } else {%>
<table width="100%" class="camposform">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td></td>
                  <td colspan="6" class="titulotbcentral">Lista de certificados asociados </td>
                </tr>
                <tr height="20">
                  <td width="1%">&nbsp;</td>
                  <td width="25%" align="center">Certificado</td>
                  <td width="25%" align="center">Matricula</td>
                  <td width="15%">&nbsp;</td>
                  <td width="7%" align="center"># copias</td>
                  <td width="5%" align="center">Imprimir</td>
                  <td width="13%">&nbsp;</td>
                </tr>
                <tr>
                  <td colspan="7">&nbsp;</td>
                </tr>

<%
    
    int certificadosAImprimir = 0;
	                int certificadosACambiarMatricula = 0;
                      //if(!carga){
                      Iterator itCert1= certAsociados.iterator();
	                	for(int i=0;itCert1.hasNext(); i++){
	                		SolicitudCertificado solCert1= (SolicitudCertificado) itCert1.next();
//se inicializara el boolean si es valido para impresion o no
	                		boolean vimprimir;
	                		Boolean btemp=(Boolean)valido.get(i);
	                		vimprimir= btemp.booleanValue();
	                		
	                		//se imprime la fila donde se muestran los datos de la solicitud de certificado
	                		String idCertificado="&nbsp;";
	                		String numCopias="1";
	                		
	                		
	                		List solicitudesFolio1=new Vector();
	                		if(solCert1!=null){
                                        // Se obtiene el ID del turno
    	                		if(solCert1.getTurno() != null) {
        	                		idCertificado = solCert1.getTurno().getIdWorkflow();
    	                		}
		                		
		                	  	if(solCert1.getNumeroCertificados()>0){
		                	  		numCopias=Integer.toString(solCert1.getNumeroCertificados());
		                	  	}
		                	  	
		                	  	solicitudesFolio1=solCert1.getSolicitudFolios();
                                                if(solicitudesFolio1==null || solicitudesFolio1.size()==0){
		                	  		vimprimir=false;
		                	  	}
                                        }
                  
			                if(solicitudesFolio1.size()>0){
			                	Iterator isolf1= solicitudesFolio1.iterator();		 
			                	for(int contMat1=0;isolf1.hasNext(); contMat1++){
			                		SolicitudFolio solF1= (SolicitudFolio) isolf1.next();
			                		String numMatricula1="";
			                		if(solF1!=null){
			                			if(solF1.getIdMatricula()!=null && !solF1.getIdMatricula().equals("")){
			                				numMatricula1=solF1.getIdMatricula();
                                                                        String anotacionFolio = jdoTurnosDAO.getAnotacionFolio(numMatricula1,turno.getSolicitud().getIdSolicitud());
                                                                        
                                                                        System.out.println("Folio maldito "+anotacionFolio);
                                                                        System.out.println("Folio maldito 2 "+numMatricula1);
                                                                        if(numMatricula1.equals(anotacionFolio)){
                                                                        System.out.println("dentro folio maldito");
                                                                 
                  %>
                                                                        
                                                                        
                <%	
                	if(!carga){
	                		
                		%>
	                		<tr height="17">
			                  <td>&nbsp;</td>
			                  <td class="campositem"><%= idCertificado%></td>
			                  <td> &nbsp;</td>
			                  <td>&nbsp;</td>
			                  <td class="campositem" align="center"><%= numCopias%></td>
			                  <%if(vimprimir){
								certificadosAImprimir++;
			                  %>
			                  	<td align="center" height="20"><input name="<%= AWMesa.NUM_CERT%>" type="checkbox" value="<%= i %>"></td>
			                  <%	}else{%>
			                  	<td align="center">&nbsp;</td>
			                  <%}%>
			                  <td>&nbsp;</td>
			                </tr>
			                <%
			                if(solicitudesFolio1.size()>0){
			                	Iterator isolf= solicitudesFolio1.iterator();		 
			                	for(int contMat=0;isolf.hasNext(); contMat++){
			                		SolicitudFolio solF= (SolicitudFolio) isolf.next();
			                		String numMatricula="";
			                		if(solF!=null){
			                			if(solF.getIdMatricula()!=null && !solF.getIdMatricula().equals("")){
			                				numMatricula=solF.getIdMatricula();
			                			}
			                		}
			                		
			                		%>
			                		
			                		<tr>
					                  <td>&nbsp;</td>
					                  <%if(isolf.hasNext()){%>
					                  	<td align="right"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/linea_hijos.gif" width="32" height="17" border="0"></td>
					                  <%}else{%>
					                  	<td align="right"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/linea_hijos_final.gif" width="32" height="17" border="0"></td>
					                  <%}%>
					                  <td class="campositem"><%=numMatricula%></td>
					                  <%if(vimprimir){%>				                  
					                  	<td><a href="javascript:cambiarMatricula('cambiarmatricula.mesa.control.view?MATRICULA=<%= numMatricula%>&NUM_CERT=<%= i%>&NUM_MAT=<%= contMat%>','Cambio_matricula','width=900,height=450,scrollbars=yes')"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_matricula.gif" width="180" height="20" border="0" alt="modificar # de matricula"><a></td>
					                  <%	}else{%>
					                  	<td height="22">&nbsp;</td>
					                  <%}%>
					                  <td>&nbsp;</td>
					                  <td>&nbsp;</td>	
					                  <td>&nbsp;</td>		          
					                </tr>
			                		<%
			                	}
			                
			                	
			               	 } else{
			               	 	String numMatricula=" ";
			               	 	certificadosACambiarMatricula++;
			               	 	%>
			                		<tr height="20">
						                <td>&nbsp;</td>
						                <td align="right"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/linea_hijos_final.gif" width="32" height="17" border="0">
						                <td class="campositem"><%=numMatricula%></td>
						                <td><a href="javascript:cambiarMatricula('cambiarmatricula.mesa.control.view?MATRICULA=<%= numMatricula%>&NUM_CERT=<%= i%>&NUM_MAT=0','Cambio_matricula','width=900,height=450,scrollbars=yes')"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_matricula.gif" width="180" height="20" border="0" alt="modificar # de matricula"><a></td>
                                                                <td>&nbsp;</td>
				               			<td>&nbsp;</td>	
						                <td>&nbsp;</td>
					            	</tr>
			                	<%	}
			                	%>
			                	<tr>
				                  <td colspan="6" class="lineaseparador">&nbsp;</td>
				                  <td>&nbsp;</td>
				                </tr>
				               
                                                                                                                                                
                                                                        <%}
			                			}
			                		}
                                                }
                                        }
                  
                  }%>
               	
 <%		
				            
			                
	                	//}
                	}
                	if(certAsociados.size()>0){
                	%>
                	<tr>
	                  <td>&nbsp;</td>
	                  <td>&nbsp;</td>
	                  <td>&nbsp;</td>
	                  <td align="right">Seleccionar todas :</td>
	                  <td>&nbsp;</td>
	                  <td align="center"><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'></td>
	                  <td>&nbsp;</td>
	                </tr>
	                <%if(certificadosAImprimir>0){%>
                	<input type='hidden' name="<%=AWMesa.CERTIFICADOS_A_IMPRIMIR%>" value="<%=""+(certificadosAImprimir)%>">
                	<input type='hidden' name="<%=AWMesa.CERTIFICADOS_A_CAMBIAR_MATRICULA%>" value="<%=""+(certificadosACambiarMatricula)%>">                	
                	<%}
                	}%>
                		
                	
                
              </table>
<%
                  //}
                  
                  }
}
                                  		
                      
                      else{   
                        System.out.println("aqui siiiii?");
                                                                        %>
                      <table width="100%" class="camposform">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td></td>
                  <td colspan="6" class="titulotbcentral">Lista de certificados asociados </td>
                </tr>
                <tr height="20">
                  <td width="1%">&nbsp;</td>
                  <td width="25%" align="center">Certificado</td>
                  <td width="25%" align="center">Matricula</td>
                  <td width="15%">&nbsp;</td>
                  <td width="7%" align="center"># copias</td>
                  <td width="5%" align="center">Imprimir</td>
                  <td width="13%">&nbsp;</td>
                </tr>
                <tr>
                  <td colspan="7">&nbsp;</td>
                </tr>
                <%	int certificadosAImprimir = 0;
	                int certificadosACambiarMatricula = 0;
                	if(!carga){
                	
	                	Iterator itCert= certAsociados.iterator();
	                	for(int contCert=0;itCert.hasNext(); contCert++){
	                		//se inicializara el boolean si es valido para impresion o no
	                		boolean vimprimir;
	                		Boolean btemp=(Boolean)valido.get(contCert);
	                		vimprimir= btemp.booleanValue();
	                		SolicitudCertificado solCert= (SolicitudCertificado) itCert.next();
	                		//se imprime la fila donde se muestran los datos de la solicitud de certificado
	                		String idCertificado="&nbsp;";
	                		String numCopias="1";
	                		
	                		List solicitudesFolio=new Vector();
	                		if(solCert!=null){
    	                		// Se obtiene el ID del turno
    	                		if(solCert.getTurno() != null) {
        	                		idCertificado = solCert.getTurno().getIdWorkflow();
    	                		}
		                		/*if(solCert.getIdSolicitud()!=null){
		                			if( !solCert.getIdSolicitud().equals("")){
		                				idCertificado=solCert.getIdSolicitud();
		                			}
		                		}*/
		                	  	if(solCert.getNumeroCertificados()>0){
		                	  		numCopias=Integer.toString(solCert.getNumeroCertificados());
		                	  	}
		                	  	
		                	  	solicitudesFolio=solCert.getSolicitudFolios();
		                	  	if(solicitudesFolio==null || solicitudesFolio.size()==0){
		                	  		vimprimir=false;
		                	  	}
		                	}
                		%>
	                		<tr height="17">
			                  <td>&nbsp;</td>
			                  <td class="campositem"><%= idCertificado%></td>
			                  <td> &nbsp;</td>
			                  <td>&nbsp;</td>
			                  <td class="campositem" align="center"><%= numCopias%></td>
			                  <%if(vimprimir){
								certificadosAImprimir++;
			                  %>
			                  	<td align="center" height="20"><input name="<%= AWMesa.NUM_CERT%>" type="checkbox" value="<%= contCert %>"></td>
			                  <%	}else{%>
			                  	<td align="center">&nbsp;</td>
			                  <%}%>
			                  <td>&nbsp;</td>
			                </tr>
			                <%
			                if(solicitudesFolio.size()>0){
			                	Iterator isolf= solicitudesFolio.iterator();		 
			                	for(int contMat=0;isolf.hasNext(); contMat++){
			                		SolicitudFolio solF= (SolicitudFolio) isolf.next();
			                		String numMatricula="";
			                		if(solF!=null){
			                			if(solF.getIdMatricula()!=null && !solF.getIdMatricula().equals("")){
			                				numMatricula=solF.getIdMatricula();
			                			}
			                		}
			                		
			                		%>
			                		
			                		<tr>
					                  <td>&nbsp;</td>
					                  <%if(isolf.hasNext()){%>
					                  	<td align="right"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/linea_hijos.gif" width="32" height="17" border="0"></td>
					                  <%}else{%>
					                  	<td align="right"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/linea_hijos_final.gif" width="32" height="17" border="0"></td>
					                  <%}%>
					                  <td class="campositem"><%=numMatricula%></td>
					                  <%if(vimprimir){%>				                  
					                  	<td><a href="javascript:cambiarMatricula('cambiarmatricula.mesa.control.view?MATRICULA=<%= numMatricula%>&NUM_CERT=<%= contCert%>&NUM_MAT=<%= contMat%>','Cambio_matricula','width=900,height=450,scrollbars=yes')"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_matricula.gif" width="180" height="20" border="0" alt="modificar # de matricula"><a></td>
					                  <%	}else{%>
					                  	<td height="22">&nbsp;</td>
					                  <%}%>
					                  <td>&nbsp;</td>
					                  <td>&nbsp;</td>	
					                  <td>&nbsp;</td>		          
					                </tr>
			                		<%
			                	}
			                
			                	
			               	 } else{
			               	 	String numMatricula=" ";
			               	 	certificadosACambiarMatricula++;
			               	 	%>
			                		<tr height="20">
						                <td>&nbsp;</td>
						                <td align="right"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/linea_hijos_final.gif" width="32" height="17" border="0">
						                <td class="campositem"><%=numMatricula%></td>
						                <td><a href="javascript:cambiarMatricula('cambiarmatricula.mesa.control.view?MATRICULA=<%= numMatricula%>&NUM_CERT=<%= contCert%>&NUM_MAT=0','Cambio_matricula','width=900,height=450,scrollbars=yes')"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/btn_cambiar_matricula.gif" width="180" height="20" border="0" alt="modificar # de matricula"><a></td></td>
				               			<td>&nbsp;</td>	
						                <td>&nbsp;</td>
					            	</tr>
			                	<%	}
			                	%>
			                	<tr>
				                  <td colspan="6" class="lineaseparador">&nbsp;</td>
				                  <td>&nbsp;</td>
				                </tr>
				                <%		
				            
			                
	                	}
                	}
                	if(certAsociados.size()>0){
                	%>
                	<tr>
	                  <td>&nbsp;</td>
	                  <td>&nbsp;</td>
	                  <td>&nbsp;</td>
	                  <td align="right">Seleccionar todas :</td>
	                  <td>&nbsp;</td>
	                  <td align="center"><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'></td>
	                  <td>&nbsp;</td>
	                </tr>
	                <%if(certificadosAImprimir>0){%>
                	<input type='hidden' name="<%=AWMesa.CERTIFICADOS_A_IMPRIMIR%>" value="<%=""+(certificadosAImprimir)%>">
                	<input type='hidden' name="<%=AWMesa.CERTIFICADOS_A_CAMBIAR_MATRICULA%>" value="<%=""+(certificadosACambiarMatricula)%>">                	
                	<%}
                	}%>
                		
                	
                
              </table>
                      
              <%} }      
                  %>
              
              
           
           
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                 <td width="15%"><a href="javascript:cambiarAccion('<%= AWMesa.AVANZAR_MESA_II %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" name="Folio" width="139" height="21" border="0" id="Aceptar"></a></td>
                  <td width="15%"><a href="javascript:cambiarAccion('<%= AWMesa.IMPRIMIR_CERTIFICADO %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" name="Folio" width="139" height="21" border="0" id="Imprimir"></a></td>
                  <td width="55%">&nbsp;</td>
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
      

        <%
        //Helper de Notas Informativas        
		try{
			gov.sir.core.web.helpers.comun.NotasInformativasHelper helper = new gov.sir.core.web.helpers.comun.NotasInformativasHelper();		
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%>
		
        
      </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
</form>
<%if(carga){%>
	<script>cargaInicial();</script>
<%}%>
