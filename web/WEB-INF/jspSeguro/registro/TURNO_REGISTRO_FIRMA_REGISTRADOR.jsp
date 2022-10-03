
<%@page import="gov.sir.hermod.HermodException"%>
<%@page import="gov.sir.hermod.HermodService"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoImprimible"%>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<!--
	@author Cesar Ramírez
	@change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
-->
<%@page import="gov.sir.core.negocio.modelo.constantes.COPLookupCodes" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTestamentos" %>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>
<% 
	session.removeAttribute("FIRMAR_REGISTRO_RELACION");
	boolean isTestamento = false;
        boolean isparcial = false;
        boolean isreproduccion = false;
	/**
	 * @author Cesar Ramírez
	 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
	 * Se trae la información de testamento para visualizarlo en el rol registrador
	 **/
	Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
	TextHelper textHelper = new TextHelper();
	if(testamento!= null) {
		request.getSession().setAttribute(CTestamentos.TOMO,testamento.getTomo());	
		request.getSession().setAttribute(CTestamentos.NUMERO_ANOTACIONES,testamento.getNumeroAnotaciones());
		request.getSession().setAttribute(CTestamentos.NUMERO_COPIAS,testamento.getNumeroCopias());
		request.getSession().setAttribute(CTestamentos.REVOCA_ESCRITURA,testamento.getRevocaEscritura());
		request.getSession().setAttribute(CTestamentos.OBSERVACION,testamento.getObservacion());
	}
        
	MostrarAntiguoSistemaHelper MASHelper= new MostrarAntiguoSistemaHelper();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper(); 
	String ocultarTurno = request.getParameter(WebKeys.OCULTAR_TURNO);	
	if(ocultarTurno == null){
		ocultarTurno = (String)session.getAttribute(WebKeys.OCULTAR_TURNO);
		if(ocultarTurno==null){
			ocultarTurno = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_TURNO,ocultarTurno);
	}
	
	String ocultarFolio = request.getParameter(WebKeys.OCULTAR_FOLIO);	
	if(ocultarFolio == null){
		ocultarFolio = (String)session.getAttribute(WebKeys.OCULTAR_FOLIO);
		if(ocultarFolio==null){
			ocultarFolio = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_FOLIO,ocultarFolio);
	}
	
		String ocultarAnotaciones = request.getParameter(WebKeys.OCULTAR_ANOTACIONES);	
	if(ocultarAnotaciones == null){
		ocultarAnotaciones = (String)session.getAttribute(WebKeys.OCULTAR_ANOTACIONES);
		if(ocultarAnotaciones==null){
			ocultarAnotaciones = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_ANOTACIONES,ocultarAnotaciones);
	}
	NotasInformativasHelper helper = new NotasInformativasHelper();
	
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        
        boolean isDevuelto = false;
        try{
            HermodService hs = HermodService.getInstance();
            if(hs.isTurnoDevuelto(turno.getIdWorkflow()).booleanValue()){
                isDevuelto = true; 
            }
        } catch(HermodException he){
            System.out.println("No ha sido posible consultar si el turno es devuelto");
        }
	
	//SE VERIFICA SI EL TURNO VIENE DE TESTAMENTOS PARA QUITAR EL BOTON DE DEVOLVER, YA QUE PARA TESTAMENTOS N0 APLICA.
	List historia= new Vector();
	historia=turno.getHistorials();
	
	if(historia != null){
	} else {
		historia = new Vector();
	}
	
	Iterator ih= historia.iterator();
	for(;ih.hasNext();){
		TurnoHistoria temp= (TurnoHistoria)ih.next();
		if(temp.getFase() != null && temp.getFase().equals(gov.sir.core.web.acciones.registro.AWMesa.REG_TESTAMENTO)){
			isTestamento = true;
		}
                /**
                 * @author: Fernando Padilla
                 * @change: Mantis 5643: Acta - Requerimiento No 211 - Opcion boton devolver calificación
                 *          Se verifica si existe en la historia la fase CAL_CALIFICACION para poner en false
                 *          la variable isTestamento.       
                */
                else if(temp.getFase() != null && temp.getFase().equals(gov.sir.core.web.acciones.registro.AWMesa.CAL_CALIFICACION)){
			isTestamento = false;
		}
	}		
	
	
	SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
	LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();
	List liquidaciones = solicitud.getLiquidaciones();
	for(int i=0;i<liquidaciones.size();i++){
		double id = new Double(((LiquidacionTurnoRegistro)liquidaciones.get(i)).getIdLiquidacion()).doubleValue();
		if(id==solicitud.getLastIdLiquidacion()){
			liquidacion = (LiquidacionTurnoRegistro)liquidaciones.get(i);
		}
	}

	Turno turnoAnterior = solicitud.getTurnoAnterior();
	Ciudadano ciudadano = solicitud.getCiudadano();
	
	if(ciudadano.getTipoDoc()==null)
		ciudadano.setTipoDoc("");
	if(ciudadano.getDocumento()==null)
		ciudadano.setDocumento("");
	if(ciudadano.getApellido1()==null)
		ciudadano.setApellido1("");
	if(ciudadano.getApellido2()==null)
		ciudadano.setApellido2("");
	if(ciudadano.getNombre()==null)
		ciudadano.setNombre("");
        
        
	
	//ver si se necesita carga
	Boolean oCarga= (Boolean)session.getAttribute("CARGA_FIRMA_REGISTRADOR");
	boolean carga=true;
	if(oCarga!=null){
		carga=oCarga.booleanValue();
	}
        
	Boolean oIsparcial= (Boolean)session.getAttribute("ISPARCIAL");
	if(oIsparcial!=null){
		isparcial=oIsparcial.booleanValue();
	}
        Boolean oReproduccion= (Boolean)session.getAttribute("ISREPRODUCCION");
	if(oReproduccion!=null){
		isreproduccion=oReproduccion.booleanValue();
	}
%>

<script type="text/javascript">
var op = true;

function envioTestamento()
{
	op = confirm("El turno será enviado a Testamentos.\nDesea continuar?");
	if(op){
		document.DEVOLVER.submit();
	}
	return false;
}

function envioCoordinador()
{
	op = confirm("El turno será enviado al Coordinador Jurídico.\nDesea continuar?");
	if(op){
		document.DEVOLVER.submit();
	}
	return false;
}

function envioNotificadorNotasDev()
{
	op = confirm("El turno será enviado a Notificador Notas Devolutivas.\nDesea continuar?");
	if(op){
		document.ENTREGA.submit();
	}
	return false;
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function cambiarAccion(text) {

	document.ENTREGA.ACCION.value = text;
    	document.ENTREGA.submit();
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
   
   
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr> 
    <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>
    <tr> 
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
    
    
    
    <table border="0" cellpadding="0" cellspacing="0">
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
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
    <table border="0" align="right" cellpadding="0" cellspacing="2">
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
            <td width="20" class="campositem">N&ordm;</td>
            <td class="campositem"><%=turno.getIdWorkflow()%></td>
        </tr>
    </table>
          
           <%if(ocultarTurno.equals("FALSE")){%>
           
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td class="bgnsub">Datos Turno</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="12"><img name="sub_r1_c1"  src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Certificado Anterior</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>&iquest; Est&aacute; asociado a un turno anterior ?</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>N&uacute;mero del Turno Anterior</td>
            <td class="campositem">
            <%if (turnoAnterior!=null){
            %><%=turnoAnterior.getIdWorkflow()%>
            <%}
            else {
            out.write("No hay turnos anteriores");
            }%></td>
        </tr>
    </table>
			
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
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>	
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Adicionales </td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
            <%
       		try{
      			MASHelper.setMostrarDocumento(true);
      			MASHelper.render(request,out);
      		}catch(HelperException re)
		    {
				re.printStackTrace();
				out.println("ERROR " + re.getMessage());
			}
        %>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
    	<%
    		String tipoDoc="&nbsp;";
			String numDoc="&nbsp;";
			String apellido1="&nbsp;";
			String apellido2="&nbsp;";
			String nombre="&nbsp;";
    		if(ciudadano!=null){
    			
    			if(ciudadano.getTipoDoc()!=null && !ciudadano.getTipoDoc().equals("")){
    				tipoDoc=ciudadano.getTipoDoc();
    			}
    			if(ciudadano.getDocumento()!=null && !ciudadano.getDocumento().equals("")){
    				numDoc=ciudadano.getDocumento();
    			}
    			if(ciudadano.getApellido1()!=null && !ciudadano.getApellido1().equals("")){
    				apellido1=ciudadano.getApellido1();
    			}
    			if(ciudadano.getApellido2()!=null && !ciudadano.getApellido2().equals("")){
    				apellido2=ciudadano.getApellido2();
    			}
    			if(ciudadano.getNombre()!=null && !ciudadano.getNombre().equals("")){
    				nombre=ciudadano.getNombre();
    			}
    		}
    	%>
    
        <tr> 
            <td width="179">Tipo de Identificaci&oacute;n</td>
            <td width="211" class="campositem"><%=tipoDoc%></td>
            <td width="146">N&uacute;mero</td>
            <td width="212" class="campositem"><%=numDoc%></td>
        </tr>
        <tr> 
            <td>Primer Apellido</td>
            <td class="campositem"><%=apellido1%></td>
            <td>Segundo Apellido</td>
            <td class="campositem"><%=apellido2%></td>
        </tr>
        <tr> 
            <td>Nombre</td>
            <td class="campositem"><%=nombre%></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
			
			
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Liquidaci&oacute;n</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
            <td width="293">Valor</td>
            <td width="11">$</td>
            <td width="445" class="campositem"><%=liquidacion.getValor()%></td>
        </tr>
    </table>
    <br>
    <hr class="linehorizontal">
       <%}%>
    </td>
		
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
         

			<table border="0" cellpadding="0" cellspacing="0">
              <tr>
              <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folios</td>
              <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
              <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  </tr>
              </table></td>
              <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td>
          <img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <table border="0" align="right" cellpadding="0" cellspacing="2">
              <tr>
					<%if(ocultarFolio.equals("FALSE")){%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>
						
					<%}else{%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR_FOLIO%>" value="FALSE">
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
    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

    </table>
        <%if(ocultarFolio.equals("FALSE")){%>
          
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub"><p>Listado de Folios del Turno</p></td>
   <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
    </table>
    <br>
	<form name="CALIFICACION" method="post" action="calificacion.do">
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
    <%
		Iterator itSolFolios = (solicitud.getSolicitudFolios()).iterator();
		int i = 0;
    	while(itSolFolios.hasNext()){%>
	    <tr>
        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
        <td>Número de Matrícula</td>
        <td class="campositem"><%=((Folio)((SolicitudFolio)itSolFolios.next()).getFolio()).getIdMatricula()%></td>
		<td width="40" align="center"><a href="javascript:verAnotacion('consultar.folio.do?<%="POSICION" + "=" + i%>','Folio','width=900,height=450,scrollbars=yes','<%=i%>')" ><img src=<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif border="0" width="35" height="13"></a></td>
		<input type="hidden" name="POSICION" value="<%=i%>">
        </tr>
  	 <%i++;
    	}%>
    </table>

		<!--
			@author Cesar Ramírez
			@change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
		-->
		<!-- Inicio: Datos del Testeador -->
		<%
		if(isTestamento) {
		%>
		<br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Testadores</td>
				<td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			</tr>
		</table>
		<table width="100%" border="0" class="camposform">
			<tr>
			<%
			if (testamento != null && testamento.getTestadores() != null && testamento.getTestadores().size() > 0) {
				Ciudadano ciud;
				TestamentoCiudadano testamentoCiudadano;
				String ciudadanoMostrar = null;
				String docIdentidad;
				for (int ic = 0; ic < testamento.getTestadores().size(); ic++) {
					ciudadanoMostrar = "";
					docIdentidad = "";
					testamentoCiudadano = (TestamentoCiudadano) testamento.getTestadores().get(ic);
					ciud = testamentoCiudadano.getCiudadano();
					if(ciud != null) {
						if(ciud.getApellido1() != null)
							ciudadanoMostrar = ciudadanoMostrar + ciud.getApellido1() + " ";
						if(ciud.getApellido2() != null)
							ciudadanoMostrar = ciudadanoMostrar + ciud.getApellido2() + " ";
						if(ciud.getNombre() != null)
							ciudadanoMostrar = ciudadanoMostrar + ciud.getNombre();
						if (ciud.getTipoDoc() != null && !ciud.getTipoDoc().equals("") && !ciud.getTipoDoc().equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
							docIdentidad = ciud.getTipoDoc();
							docIdentidad += "   " + ciud.getDocumento();
						}
					}
			%>
			<tr>
				<td width="20\"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"width="20" height="15"></td>
				<td class="titulotbcentral"><%=ciudadanoMostrar%></td>
				<td class="titulotbcentral"><%=docIdentidad%></td>
			</tr>
			<%
				}
			}
			%>
			</tr>
		</table>
		<!-- Fin: Datos del Testeador -->
		<!-- Inicio: Datos del Registro de Testamentos -->
		<br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Registro de Testamentos</td>
				<td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			</tr>
		</table>
		<table width="100%" class="camposform">
			<tr>
				<td width="18%">Tomo:</td>
				<td align='left'>
					<div class="campositem" style="width:450px">
					<%
					try {
						textHelper.setNombre(CTestamentos.TOMO);
						textHelper.setEditable(false);
						textHelper.setId(CTestamentos.TOMO);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
					%>
					</div>
				</td>
			</tr>
			<tr>
				<td width="18%">Número Anotaciones:</td>
				<td align='left'>
					<div class="campositem" style="width:450px">
					<%
					try {
						textHelper.setNombre(CTestamentos.NUMERO_ANOTACIONES);
						textHelper.setEditable(false);
						textHelper.setId(CTestamentos.NUMERO_ANOTACIONES);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
					%>
					</div>
				</td>
			</tr>
			<tr>
				<td width="18%">Número copias:</td>
				<td align='left'>
					<div class="campositem" style="width:450px">
					<%
					try {
						textHelper.setNombre(CTestamentos.NUMERO_COPIAS);
						textHelper.setEditable(false);
						textHelper.setId(CTestamentos.NUMERO_COPIAS);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
					%>
					</div>
				</td>
			</tr>
			<tr>
				<td width="18%">Revoca Escritura:</td>
				<td align='left'>
					<div class="campositem" style="width:450px">
					<%
					try {
						textHelper.setNombre(CTestamentos.REVOCA_ESCRITURA);
						textHelper.setEditable(false);
						textHelper.setId(CTestamentos.REVOCA_ESCRITURA);
						textHelper.render(request,out);
					} catch(HelperException re) {
						out.println("ERROR " + re.getMessage());
					}
					%>
					</div>
				</td>
			</tr>
			<tr>
				<td width="18%" >Observación:</td>
				<td align='left'>
				<%
				try {
					TextAreaHelper area = new TextAreaHelper();
					area.setCols("90");
					area.setRows("8");
					area.setNombre(CTestamentos.OBSERVACION);
					area.setCssClase("campositem");
					area.setId(CTestamentos.OBSERVACION);
					area.setReadOnly(true);
					area.render(request,out);
				} catch(HelperException re) {
					out.println("ERROR " + re.getMessage());
				}
				%>
				</td>
			</tr>
		</table>
		<%
		}
		%>
    </form>
    
    
    <hr class="linehorizontal">
		<%}%>
   <%if(isreproduccion){
    List repro =  (List) session.getAttribute("LISTISREPRO");
   %>  
        <br>
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
         <tr>
         <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
         <td class="bgnsub"><p>Listado de Turnos en la Reproduccion de Sellos</p></td>
        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
         </tr>
         </table>
         <br>
            <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
         <%
                     Iterator Sellos = repro.iterator();
                     int i = 0;
             while(Sellos.hasNext()){
                 ReproduccionSellos r = (ReproduccionSellos) Sellos.next(); 
                 if(r.getOpcion() == 1){%>
                   <tr>
             <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
             <td>Número de Turno Asociado</td>
             <td class="campositem"><%=r.getCode()%></td>
             <td width="40" align="center"><a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?RST=<%=r.getCode()%>&&ID=<%=r.getUsuariosir()%>','name','width=800,height=600')" ><img src=<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif border="0" width="120" height="20"></a></td>
             <input type="hidden" name="POSICION" value="<%=i%>">
             </tr>  
              <%i++; }
             }%>
         </table>
         <br>
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
         <tr>
         <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
         <td class="bgnsub"><p>Listado de Matriculas en la Reproduccion de Sellos</p></td>
        <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
         </tr>
         </table>
         <br>
            <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
         <%
                     Iterator Sellos1 = repro.iterator();
                     int i1 = 0;
             while(Sellos1.hasNext()){
                 ReproduccionSellos r = (ReproduccionSellos) Sellos1.next();
                 if(r.getOpcion() == 2){%>
                   <tr>
             <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
             <td>Número de Matricula Asociada</td>
             <td class="campositem"><%=r.getCode()%></td>
             <td width="40" align="center"><a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?RSM=<%=r.getCode()%>&&ID=<%=r.getUsuariosir()%>&&TR=<%=r.getIdTurnoRaiz()%>&&desde=<%=r.getDesde()%>&&hasta=<%=r.getHasta()%>','name','width=800,height=600')" ><img src=<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif border="0" width="120" height="20"></a></td>
             <input type="hidden" name="POSICION" value="<%=i1%>">
             </tr>  
              <%i1++; }
             }%>
         </table>
    <%}%>
    <hr class="linehorizontal">
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>        
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <table width="100%" class="camposform">
              <form name="ENTREGA" method="post" action="calificacion.do">
                  
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
              <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCalificacion.FIRMA_REGISTRO_CONFIRMAR%>">
              <input type="hidden" name="<%=AWCalificacion.RESPUESTAWF%>" value="<%=AWCalificacion.FIRMA_REGISTRO_CONFIRMAR%>">
              <%if(isDevuelto){%> 
              <td width="150" align="center"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0" onClick=" return envioNotificadorNotasDev()"></td>
              <%} else {%>
              <td width="150" align="center"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></td>
              <%}%>
              </form>
              <%if(!isTestamento){%>
			  <form name="DEVOLVER" method="post" action="calificacion.do">
              <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCalificacion.FIRMA_REGISTRO_CONFIRMAR%>">
              <input type="hidden" name="<%=AWCalificacion.RESPUESTAWF%>" value="<%=AWCalificacion.FIRMA_REGISTRO_DEVOLVER%>">
              <td width="150" align="center"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_calificacion.gif" width="139" height="21" border="0" onClick=" return envioCoordinador()"></td>
              
              
              <td width="15%"><a href="admin.relacion.view?FIRMAR_REGISTRO_RELACION=TRUE"><img src="<%=request.getContextPath()%>/jsp/images/btn_crear_relaciones.gif" name="Folio" width="180" height="21" border="0" id="Folio"></a></td>
              
              
              </form>
              <%}else{%>
              <form name="DEVOLVER" method="post" action="calificacion.do">
              <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWCalificacion.DEVOLVER_FIRMA_TESTAMENTO%>">
              <input type="hidden" name="<%=AWCalificacion.RESPUESTAWF%>" value="<%=AWCalificacion.DEVOLVER_FIRMA_TESTAMENTO%>">
              <td width="150" align="center"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_devolver_testamento.gif" name="Folio" width="170" height="21" border="0" onClick=" return envioTestamento()"></td>
              </form>
              <%}%>
              <%if(isreproduccion){%>
              
              
              
              <%} else if(!isparcial){%>
                  <td width="150" align="center">
              <a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?Serv=1','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif" name="Folio" width="139" height="21" border="0" id="Folio"/>
             </a>
                  </td>
                  <%}else{%>
                  
                <td width="150" align="center">
              <a target="popup"  onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?Calf=<%=turno.getIdWorkflow()%>','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_observar_formularioC.gif" name="Folio" border="0" width="139" height="21"  id="Folio"/>
             </a>
                  </td>
                        <td width="150" align="center">
              <a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?ServRE=<%=turno.getIdWorkflow()%>','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_observar_notaD.gif" name="Folio1" border="0" width="139" height="21"  id="Folio1"/>
             </a>
                  </td>
                  
                  <%}%>
              <td>&nbsp;</td>
              </tr> 
          </table>
          </td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        
        <!-- hasta aqui-->
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
    
             <%
		try{
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
<%if(carga){%>
<script>cambiarAccion('<%=AWCalificacion.OBTENER_BLOQUEO_FOLIOS_FIRMA%>')</script>
<%}%>