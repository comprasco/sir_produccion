<%@page import="gov.sir.core.util.ContainerUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDevoluciones"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.devolucion.AWDevolucion"%>
<%@page import="gov.sir.hermod.HermodException"%>
<%@page import="gov.sir.hermod.HermodService"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoImprimible"%>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<!--
	@author Cesar Ramirez
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
        
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        
        SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
    
    //ver is la oficina es internacional o naciol
				String nomOficinaIntr="";
				String departamento="&nbsp;";
				String municipio="&nbsp;";
				String vereda="&nbsp;";
				String tipoOficina="&nbsp;";
				String numOficina="&nbsp;";
				boolean esInternacional=false;
				boolean esComentarioDocumento=false;				

				if(solicitud.getDocumento().getComentario()!=null){
					nomOficinaIntr=solicitud.getDocumento().getComentario();
					esComentarioDocumento=true;
				}else if(solicitud.getDocumento().getOficinaInternacional()!=null){
					nomOficinaIntr=solicitud.getDocumento().getOficinaInternacional();
					esInternacional=true;
				}
				else{

					//DEPARTAMENTO, MUNICIPIO, VEREDA
					if (solicitud.getDocumento().getOficinaOrigen()!=null){
						if(solicitud.getDocumento().getOficinaOrigen().getVereda()!=null){
							Vereda auxVereda=solicitud.getDocumento().getOficinaOrigen().getVereda();
							departamento=auxVereda.getMunicipio().getDepartamento().getNombre();
							municipio=auxVereda.getMunicipio().getNombre();
							vereda=auxVereda.getNombre();
						}
	
						//TIPO OFICINA
	
						if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null)
							tipoOficina=solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() + " - " + solicitud.getDocumento().getOficinaOrigen().getNombre();

						//NUM OFICINA

						if(solicitud.getDocumento().getOficinaOrigen().getNumero()!=null)
							numOficina=solicitud.getDocumento().getOficinaOrigen().getNumero();
					}
				}
    
                Date today;
                String fechaAct;
                today = new Date();
                fechaAct = DateFormatUtil.format(today);

                String vistaAnterior = (String) session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
                vistaAnterior = vistaAnterior != null
                        ? "javascript:window.location.href='fases.view';"
                        : "javascript:history.back();";
    
        
        String tipoChecked = (String) session.getAttribute(AWDevolucion.CHECK_JUZGADO);
        TextHelper txtHelper = new TextHelper(); 
    
	/** 
	 * @author Cesar Ramirez
	 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
	 * Se trae la informacion de testamento para visualizarlo en el rol registrador
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
                 * @change: Mantis 5643: Acta - Requerimiento No 211 - Opcion boton devolver calificacion
                 *          Se verifica si existe en la historia la fase CAL_CALIFICACION para poner en false
                 *          la variable isTestamento.       
                */
                else if(temp.getFase() != null && temp.getFase().equals(gov.sir.core.web.acciones.registro.AWMesa.CAL_CALIFICACION)){
			isTestamento = false;
		}
	}		
	
	
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
	Boolean oCarga= (Boolean)session.getAttribute("CARGAR_NOTA_NOTIFICADA");
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
	op = confirm("El turno sera enviado a Testamentos.\nDesea continuar?");
	if(op){
		document.DEVOLVER.submit();
	}
	return false;
}

function envioCoordinador()
{
	op = confirm("El turno sera enviado al Coordinador Juridico.\nDesea continuar?");
	if(op){
		document.DEVOLVER.submit();
	}
	return false;
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function cambiarAccion(text) {
	document.NOTA_DEVOLUTIVA_NOTIFICADA.ACCION.value = text;
    	document.NOTA_DEVOLUTIVA_NOTIFICADA.submit();
}

function alertaVencimiento(text){
    alert("El turno: " + turno +" ha sido avanzado a Certificados Asociados por vencimiento de tiempo.");
    cambiarAccion(text);
}

function cambiarAccionNotificacion(text){
    document.NOTIFICACION.ACCION.value = text;
    document.NOTIFICACION.submit();
}
function validarFecha(){
		if (document.NOTA_DEVOLUTIVA_NOTIFICADA.<%=AWDevolucion.FECHA_NOTIFICACION%>.value.length>0){
			var index=document.NOTA_DEVOLUTIVA_NOTIFICADA.<%=AWDevolucion.FECHA_NOTIFICACION%>.value.lastIndexOf('/')+1;
			if (index!=null){
				var fin=document.NOTA_DEVOLUTIVA_NOTIFICADA.<%=AWDevolucion.FECHA_NOTIFICACION%>.value.length;
				var texto=document.NOTA_DEVOLUTIVA_NOTIFICADA.<%=AWDevolucion.FECHA_NOTIFICACION%>.value.substring(index,fin);
				if (texto.length!=4){
					alert('Fecha incorrecta');
					document.NOTA_DEVOLUTIVA_NOTIFICADA.<%=AWDevolucion.FECHA_NOTIFICACION%>.value='';
					document.NOTA_DEVOLUTIVA_NOTIFICADA.<%=AWDevolucion.FECHA_NOTIFICACION%>.focus();
				}
			}
		}
	}
</script>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
   
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
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem"><%=((Folio)((SolicitudFolio)itSolFolios.next()).getFolio()).getIdMatricula()%></td>
		<td width="40" align="center"><a href="javascript:verAnotacion('consultar.folio.do?<%="POSICION" + "=" + i%>','Folio','width=900,height=450,scrollbars=yes','<%=i%>')" ><img src=<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif border="0" width="35" height="13"></a></td>
		<input type="hidden" name="POSICION" value="<%=i%>">
        </tr>
  	 <%i++;
    	}%>
    </table>

		<!--
			@author Cesar Ramirez
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
				<td width="18%">N&uacute;mero Anotaciones:</td>
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
                            <td width="18%">N&uacute;mero copias:</td>
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
                            <td width="18%" >Observaci&oacute;n:</td>
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
             <td>N&uacu&uacute;mero de Turno Asociado</td>
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
             <td>N&uac&uacute;mero de Matricula Asociada</td>
             <td class="campositem"><%=r.getCode()%></td>
             <td width="40" align="center"><a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?RSM=<%=r.getCode()%>&&ID=<%=r.getUsuariosir()%>&&TR=<%=r.getIdTurnoRaiz()%>&&desde=<%=r.getDesde()%>&&hasta=<%=r.getHasta()%>','name','width=800,height=600')" ><img src=<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif border="0" width="120" height="20"></a></td>
             <input type="hidden" name="POSICION" value="<%=i1%>">
             </tr>  
              <%i1++; }
             }%>
         </table>
    <%}%>
    <hr class="linehorizontal">
    
    <form name="NOTIFICACION" method="post" action="devolucion.do">
    <input type="hidden" name="<%=WebKeys.ACCION%>"> 
    <%
    List notifications = null;
    try{
        HermodService hs = HermodService.getInstance();
        notifications = hs.getNotaDevNotificada(turno.getIdWorkflow());
    } catch(HermodException he){
        System.out.println("ERROR: " + he.getMessage());
    }
    
    if(notifications != null && !notifications.isEmpty()){
        %>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Historial de Notificaciones</td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
            <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
        </table>
    <%
        Iterator itNot = notifications.iterator();
        while(itNot.hasNext()){
            NotificacionNota notify = (NotificacionNota) itNot.next();
            if(notify != null){
                
                boolean isNotificarUsuario  = false;
                boolean isNotificarOficina = false;
                    String destiny = notify.getDestino();
                    if(destiny != null && !destiny.isEmpty()){ 
                        if(destiny.equals(CDevoluciones.USUARIO)){ 
                            isNotificarUsuario = true;
                            isNotificarOficina = false;
                        } else if (destiny.equals(CDevoluciones.OFICINA_ORIGEN)){
                            isNotificarUsuario = false;
                            isNotificarOficina = true; 
                        }
                    }
                

                boolean isUsuario = false;
                boolean isOficina = false;

                    if(isNotificarUsuario){
                        isUsuario = true;
                        isOficina = false; 
                    } else if(isNotificarOficina){
                        isUsuario = false;
                        isOficina = true; 
                    } else{
                        isUsuario = false;
                        isOficina = false;
                    }
                
                String idNotification = (notify.getIdNotificacion()!=null?notify.getIdNotificacion():"&nbsp;");
                String fechaNot = "&nbsp;";
                destiny = (notify.getDestino()!=null?notify.getDestino():"&nbsp;");
                String tipo = (notify.getTipo()!=null?notify.getTipo():"&nbsp;");
                String correo = (notify.getCorreo()!=null?notify.getCorreo():"&nbsp;"); 
                String apoderado = String.valueOf(notify.getApoderado()); 
                String nombres = (notify.getNombres()!=null?notify.getNombres():"&nbsp;");
                String apellidos = (notify.getApellidos()!=null?notify.getApellidos():"&nbsp;");
                String tipoDocumento = (notify.getTipoDocumento()!=null?notify.getTipoDocumento():"&nbsp;");
                String numDocumento = (notify.getDocumento()!=null?notify.getDocumento():"&nbsp;");
                String direccion = (notify.getDireccion()!=null?notify.getDireccion():"&nbsp;");
                String telefono = (notify.getTelefono()!=null?notify.getTelefono():"&nbsp;");
                
                String alertNot = null;
                boolean hasAlert = false; 
                    
                List notificationAlerts = (List) session.getAttribute(CDevoluciones.ALERTA_VENCIMIENTO);
                    if(notificationAlerts != null && !notificationAlerts.isEmpty()){
                        Iterator itAlert = notificationAlerts.iterator();
                        while(itAlert.hasNext()){
                            ContainerUtil notAlert = (ContainerUtil) itAlert.next();
                            if(notAlert != null){
                                String idNot = notAlert.getIdNotificacion();
                                String alert = notAlert.getAlerta();
                                
                                if(idNot != null && idNot.equals(idNotification)){ 
                                    hasAlert = true;
                                    alertNot = alert;
                                } 
                            }
                        }
                    }

                if(notify.getFechaNotificacion() != null){
                    Date date = notify.getFechaNotificacion();
                    String dateFormat = "dd/MM/yyyy";
                    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                    fechaNot = formatter.format(notify.getFechaNotificacion());
                }

                boolean isCorreo = false;
                if(notify.getCorreo() != null && !notify.getCorreo().isEmpty()){
                        isCorreo = true; 
                }
                
                %>
    <table width="100%" class="camposform">
        <tr>
            <td>
        <%if(hasAlert){%>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td width="5">&nbsp;</td>
                        <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"><table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Alertas</td>
                              <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
                              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ico_alerta.gif" width="20" height="20"></td>
                            </tr>
                        </table></td>
                        <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
                        <td class="tdtablaanexa02">
                            <br>
                            <p style="color:red;"><strong><%=alertNot%></strong></p>
                            </td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
                        <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
                    </tr>
            </table>
        <%}%>
            </td>
        </tr>
        
        <tr>
            <td>
                <table class="camposformnoborder">
            <tr>
                <td width="200" nowrap>Fecha de Notificaci&oacute;n: </td>
                <td>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                        <td width="150" class="campositem">
                    <%= fechaNot %>
                    </td>
                    </tr>
                    </table>
                </td>
            </tr>

            <tr>
                <td>Destinatario: </td>
                <td width="150" class="campositem">
                    <%= destiny %>
                </td>             
            </tr>

            <tr>
                <td>Tipo de Notificaci&oacute;n: </td>
                <td width="150" class="campositem">
                    <%= tipo %>
                </td>
              <%if(isCorreo){%>
                                    <td width="150">Correo Electr&oacute;nico: </td>
                                    <td width="150" class="campositem">
                                        <%= correo %>
                                    </td>                   
              <%}%>
                                    
                 </tr>
                 
                 <% if(isUsuario){%>
                 <tr>
                     <td>Apoderado: </td>
                     <td width="150" class="campositem">
                         <%if(apoderado.equals("0")){%>
                         No
                         <%} else{%>
                         Si
                         <%}%>
                    </td> 
                 </tr>
                 
                 <tr>
                     <td>Nombre: </td>
                     <td width="150" class="campositem"><%= nombres
                         %>
                     </td>
                 </tr>
                 
                 <tr>
                     <td>Apellidos: </td>
                     <td width="150" class="campositem"><%= apellidos%>
                     </td>
                 </tr>
                 
                 <tr>
                     <td>Tipo de Documento: </td>
                     <td width="150" class="campositem"><%=tipoDocumento%></td>    

                     <td width="150">Numero de Documento: </td>
                     <td width="150"class="campositem"><%= numDocumento%></td>     
                 </tr>
                 
                 <%} else if(isOficina){%>
                   
                 <tr>
                  <td>Oficina de Procedencia </td>
                </tr>
                <tr>
                  
                  <td colspan="2">
                  <table width="100%" class="camposform">
					<%if(esComentarioDocumento){%>
					 <tr>
                        <td>Oficina Origen</td>
                        <td class="campositem">
                        <%=nomOficinaIntr%>
                        </td>
                      </tr>
                     <%}else if(!esInternacional){%>
                      <tr>
	                  <td width="12%">
		                   <DIV align="right">Codigo</DIV>
		              </td>
		              <td class="campositem" width="32%">
						<%=numOficina%>
	                  </td>
	                  <td width="15%">
	                    <DIV align="right">Nombre</DIV>
	                  </td>
	                  <td class="campositem" width="36%">
					    <%=tipoOficina%>
	                  </td>
		            </tr>

	                 <tr>
	                    <td width="12%">
	                      <DIV align="right">Departamento</DIV>
	                    </td>
	                    <td class="campositem" width="32%">
						<%=departamento%>
	                    </td>
	                    <td width="15%">
	                      <DIV align="right">Municipio</DIV>
	                    </td>
	                    <td class="campositem" width="36%">
							<%=municipio%>
	                    </td>
	                 </tr>
					<%}else{%>
					 <tr>
                        <td>Oficina internacional</td>
                        <td class="campositem">
                        <%=nomOficinaIntr%> 
                       </td>
                      </tr>
                     <%}%>
                    </table></td>
                </tr>

                    
                 <%}%>
                 
                 <tr>
                     <td>Direcci&oacute;n: </td>
                     <td width="150" class="campositem"><%= direccion%></td>    
                 </tr>
                 
                 <tr>
                     <td>Telefono: </td>
                     <td width="150" class="campositem"><%= telefono
                     %></td>    
                 </tr>
            </table>
            </td>
        </tr>
    </table>
    <%
                
            }
        }
    }
    %>
    <table width="100%" class="camposform">
        <tr>
                    <td width="20%">Interpone el recurso</td>
                    <td>
                     <%String checkedSi = "", checkedNo = "";
                            if(tipoChecked != null){
                            if (tipoChecked.equals(AWDevolucion.CHECKED_SI)) {
                                  checkedSi = "CHECKED";
                            } else{
                                  checkedNo = "CHECKED";
                            }}
                            %>
                        <input type="radio" name="<%= AWDevolucion.CHECK_JUZGADO%>" value="<%=AWDevolucion.CHECKED_SI%>" <%= checkedSi%> onClick="cambiarAccionNotificacion('<%=AWDevolucion.ENVIA_JUZGADO%>');"/> Si
                        &nbsp;
                        <input type="radio" name="<%= AWDevolucion.CHECK_JUZGADO%>" value="<%=AWDevolucion.CHECKED_NO%>" <%= checkedNo%> onClick="cambiarAccionNotificacion('<%=AWDevolucion.CONTINUE_WF%>');"/> No 
                    </td>
         </tr>
    </table>
    
    </form>           
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>        
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <table width="100%" class="camposform">
            <tr>

                <td><a href="javascript:cambiarAccionNotificacion('NOTA_NOTIFICADA_CONFIRMAR')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" border="0"></a> </td>

              <form name="NOTA_DEVOLUTIVA_NOTIFICADA" method="post" action="devolucion.do">
               <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWDevolucion.NOTIFICAR_NOTA_CONFIRMAR%>"> 
                </form>
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
                    helper.setNombreFormulario("NOTA_DEVOLUTIVA_NOTIFICADA");
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
<script>cambiarAccion('<%=AWDevolucion.NOTA_DEVOLUTIVA_NOTIFICADA%>')</script>
<%}%>
