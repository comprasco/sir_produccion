<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Liquidacion"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.negocio.modelo.Vereda"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.negocio.modelo.SubtipoSolicitud"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro"%>

<%	 
//	MostrarAntiguoSistemaHelper MASHelper= new MostrarAntiguoSistemaHelper();
	//OBTENER TURNO
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();
	List sfolios=solicitud.getSolicitudFolios();

	//HELPER TABLA MATRICULAS
	TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
	
	List matriculas = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	
	Iterator is = matriculas.iterator();	
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		idmatriculas.add(temp);
	}	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, idmatriculas);		

	
	//TURNO ANTERIOR
	String turnoAnterior="&nbsp;";
    if(solicitud.getTurnoAnterior()!=null)
       	turnoAnterior=turno.getSolicitud().getTurnoAnterior().getIdWorkflow();
       	
	//HELPER COMBO SUBTIPO DE SOLICITUD SIR-91
	List subTiposObj = (List) session.getServletContext().getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
	if(subTiposObj==null){
		subTiposObj = new Vector();
	}
	Iterator itSubTiposObj = subTiposObj.iterator();
	List subTipos = new Vector();
	int c=0;
	while(itSubTiposObj.hasNext() && c<4){
		SubtipoSolicitud sub=(SubtipoSolicitud)itSubTiposObj.next();
		subTipos.add(new ElementoLista(sub.getIdSubtipoSol(),sub.getNombre()));
    c++;
	}
	ListaElementoHelper subTipoSolicitud = new ListaElementoHelper();
    subTipoSolicitud.setTipos(subTipos);
   	subTipoSolicitud.setNombre(CSolicitudRegistro.SUBTIPO_SOLICITUD);
    subTipoSolicitud.setCssClase("camposformtext");
    subTipoSolicitud.setId(CSolicitudRegistro.SUBTIPO_SOLICITUD);
    subTipoSolicitud.setSelected(solicitud.getSubtipoSolicitud().getIdSubtipoSol());       	

	//COMENTARIO
	String comentario="&nbsp;";
	if(solicitud.getComentario()!=null)
		comentario=solicitud.getComentario();
		
	//TIPO DOCUMENTO
	String tipoDocumento="&nbsp;";
	if(solicitud.getDocumento().getTipoDocumento().getNombre()!=null)
		tipoDocumento=solicitud.getDocumento().getTipoDocumento().getNombre();
	
	//NUM DOCUMENTO
	String numDocumento="&nbsp;";
	if(solicitud.getDocumento().getNumero()!=null)
		numDocumento=solicitud.getDocumento().getNumero();
		
	//FECHA DOCUMENTO
	String fechaDocumento="&nbsp;";
	if(solicitud.getDocumento().getFecha()!=null){
		fechaDocumento = DateFormatUtil.format(solicitud.getDocumento().getFecha());
	}
	
	//DEPARTAMENTO, MUNICIPIO, VEREDA
	String departamento="&nbsp;";
	String municipio="&nbsp;";
	String vereda="&nbsp;";
	String internacional="";
	boolean isInternacional=false;
	String tipoOficina="&nbsp;";
	String numOficina="&nbsp;";
	if(solicitud!=null&&solicitud.getDocumento()!=null) {
		if(solicitud.getDocumento().getOficinaInternacional()!=null) {
			internacional=solicitud.getDocumento().getOficinaInternacional();
			isInternacional=true;
		}else if(solicitud.getDocumento().getOficinaOrigen()!=null) {
			if(solicitud.getDocumento().getOficinaOrigen().getVereda()!=null) {
				Vereda auxVereda=solicitud.getDocumento().getOficinaOrigen().getVereda();
				departamento=auxVereda.getMunicipio().getDepartamento().getNombre();
				municipio=auxVereda.getMunicipio().getNombre();
				vereda=auxVereda.getNombre();
				//TIPO OFICINA
				if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null)
					tipoOficina=solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
				//NUM OFICINA
				if(solicitud.getDocumento().getOficinaOrigen().getNumero()!=null)
					numOficina=solicitud.getDocumento().getOficinaOrigen().getNumero();
			}
		}
	}
	
	//TIPO IDENTIFICACION
	String tipoIdentificacion="&nbsp;";
	if(solicitud.getCiudadano().getTipoDoc()!=null)
		tipoIdentificacion=solicitud.getCiudadano().getTipoDoc();
	
	//NUM IDENTIFICACION
	String numIdentificacion="&nbsp;";
	if(solicitud.getCiudadano().getDocumento()!=null)
		numIdentificacion=solicitud.getCiudadano().getDocumento();
		
	//APELLIDO1
	String apellido1="&nbsp;";
	if(solicitud.getCiudadano().getApellido1()!=null)
		apellido1=solicitud.getCiudadano().getApellido1();
		
	//APELLIDO2
	String apellido2="&nbsp;";
	if(solicitud.getCiudadano().getApellido2()!=null)
		apellido2=solicitud.getCiudadano().getApellido2();
		
	//NOMBRE
	String nombre="&nbsp;";
	if(solicitud.getCiudadano().getNombre()!=null)
		nombre=solicitud.getCiudadano().getNombre();
  
  //IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}
	
        	//HABILITAR CAMPOS OCULTOS DATOS ANTIGUO SISTEMA
	String ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR_ANTIGUO_SISTEMA);
	if( ocultarAntSist == null ){
		ocultarAntSist = "TRUE";
		session.setAttribute(WebKeys.OCULTAR_ANTIGUO_SISTEMA,ocultarAntSist);		
	}	

        //MOSTRAR U OCULTAR CAMPO NUMERO MATRICULAS SOLICITUD
        List liquidaciones = solicitud.getLiquidaciones();
        Iterator itLiq = liquidaciones.iterator();
        String valMatAgr = null;
        int numMatAgr = 0;
        if(itLiq.hasNext())
        {
          Liquidacion li = (Liquidacion) itLiq.next();
          valMatAgr = li.getNumMatAgr();
          if(valMatAgr!=null)
          numMatAgr = Integer.parseInt(valMatAgr);
          
        }
  //OCULTAR DATOS DE RADICACION
  String ocultarRadicacion =(String)session.getAttribute("VER_RADICACION");
   		if (ocultarRadicacion ==null){
   			ocultarRadicacion="TRUE";
   		}
   else{
   		session.setAttribute("VER_RADICACION",ocultarRadicacion);
   }
    %>
 <script>
   function cambiarAccion(text) {
       document.CONFRONTACION.ACCION.value = text;
       document.CONFRONTACION.submit();
   }
</script>
  
   
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
       document.CONFRONTACION.ACCION.value = text;
       document.CONFRONTACION.submit();
}		
function eliminarMatriculas() {
    document.CONFRONTACION.ACCION.value = 'ELIMINAR';
    document.CONFRONTACION.submit();
}   
function autoFocus(){
	document.ASOCIAR_MAT.<%=CFolio.ID_MATRICULA%>.focus();
}
function cambiarSeleccion() {
    if(document.CONFRONTACION.seleccionado.checked == true){
	    setAllCheckBoxes('CONFRONTACION', 'ELIMINAR_CHECKBOX', true);
    }
    if(document.CONFRONTACION.seleccionado.checked == false){
    	setAllCheckBoxes('CONFRONTACION', 'ELIMINAR_CHECKBOX', false);
    }
}
function validarNumMatriculas(){
    var matriculas = document.getElementsByName("ELIMINAR_CHECKBOX");
    var paramMatr = "0";
       paramMatr  =  document.getElementById("numMatAgr").value;
    
        
    if(paramMatr !== "0" &&  matriculas.length > 0)
    {
        
        if(paramMatr > matriculas.length)
        {
           
            if(confirm("Esta seguro que desea avanzar el turno. Faltan matriculas."))
            {
                cambiarAccionAceptar('CONFIRMAR');
            }
            else
                return false;
                
        }

    }else
    {
        cambiarAccionAceptar('CONFIRMAR');
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Registro de Documentos </td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Confrontaci&oacute;n</td>
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
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Encabezado del Documento </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Datos B&aacute;sicos </td>
                </tr>
                <%Turno turnoHijo = (Turno)session.getAttribute(WebKeys.TURNO_HIJO);
                   if(turnoHijo!=null){
                   		String mensaje = "Este turno depende del turno de Correcciones No: " + turnoHijo.getIdWorkflow();
                   %>
                   <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td class="titresaltados" colspan="2"> <img src="<%=request.getContextPath()%>/jsp/images/ico_advertencia.gif" width="16" height="21"><%=mensaje %></td>	
                      </tr>
                  </table></td>
                </tr>
                   <%} %>
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td>Tipo</td>
                        <td class="campositem">
                        <%=tipoDocumento%>
						
                        
                        </td>
                        <td width="15%">&nbsp;</td>
                        <td>N&uacute;mero</td>
                        <td class="campositem">
                        
                        <%=numDocumento%>
                        </td>
                        <td width="15%">&nbsp;</td>
                        <td>Fecha</td>
                        <td class="campositem">
                        <%=fechaDocumento%>
                        
                        </td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Oficina de Procedencia </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                    <table width="100%" class="camposform">
                    <%if(isInternacional) {%>
                      <tr>
                        <td>Oficina internacional</td>
                        <td class="campositem">
                        <%=internacional%>
                        </td>
                      </tr>
                    <%} else {%>
                      <tr>
                        <td>Departamento</td>
                        <td class="campositem">
                        <%=departamento%>
                        </td>
                      </tr>
                      <tr>
                        <td>Municipio</td>
                        <td class="campositem">
                        <%=municipio%>
                        </td>
                      </tr>
                    <%} %>
                    </table>
                      <table width="100%" class="camposform">
                        <tr>
                          <td>Tipo</td>
                          <td class="campositem">
                          
                          <%=tipoOficina%>
                          
                          </td>
                          <td>N&uacute;mero</td>
                          <td class="campositem">
                          
                          <%=numOficina%>
                          
                          </td>
                        </tr>
                    </table></td>
                </tr>
              </table>
              <br>

              <br>
              <form action="confrontacion.do" method="post" name="CONFRONTACION" id="CONFRONTACION">              
                  <input type="hidden" name="ITEM" value="NINGUNO">
		          <input type="hidden" name="ACCION" value="">
		          <input type="hidden" name ="VER_RADICACION" id="VER_RADICACION">
		                        
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Asociar una Matr&iacute;cula </td>
                  <td align='right' width='20' class="bgnsub"><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'>&nbsp;</td>                  
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
             

              <table width="100%">

                <tr>
                  <td>
	                  <% try {
	                      tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
	               		  tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
	                  %>
		          
                  </td>
                </tr>


                <tr><td>
                        <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform" >
              <tr>
                  
                <td>Numero de Matriculas de la Solicitud</td>
                <td class="campositem" width="10%" style="text-align: center">
                     <% if(valMatAgr!=null){ %>
            <input type="hidden" id="numMatAgr" name="numMatAgr" value="<%= valMatAgr %>">
                     
                    <%= numMatAgr %>
                <%} %>
                </td>
              </tr>
            </table>
                    
                    
            <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform" >
              <tr>
                <td width="20">
                  <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/>
                </td>
                <td>Eliminar Seleccionadas</td>
                <td>
                  <a href="javascript:eliminarMatriculas()"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a>
                </td>
              </tr>
            </table>    
                </form>            
            
            <P>&nbsp;</P>                                
<br>
                
                <form action="confrontacion.do" method="post" name="ASOCIAR_MAT" id="ASOCIAR_MAT">
           		<input type="hidden" name="ACCION" value="ASOCIAR_UNA_MATRICULA">   
                <table class="camposform" width="100%"><tr>
					<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					<td width="50%">N&uacute;mero de Matr&iacute;cula</td>
					<td width="25%">	
						<table class="camposform" width="95%">
							<tr>
								<td>
								<%=idCirculo%>-
								<input name="<%=CFolio.ID_MATRICULA%>" type="text" value="" class="camposformtext">
								</td>
							</tr>
						</table>
					</td>
                    <td width="25%" align="left">
                      	<table border="0" cellpadding="0" cellspacing="2" class="camposform" width="90%">
                      		<tr> 
                      			<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                      			<td>Agregar Matr&iacute;cula</td>
                      			<td><input type="image" name="btn_short_anadir" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0""> 
                      		</tr>
                      	</table>
                    </td>
            </form>
                </tr>
                <tr>
            <form action="confrontacion.do" method="post" name="ASOCIAR_RAN" id="ASOCIAR_RAN">
            <input type="hidden" name="ACCION" value="ASOCIAR_UN_RANGO">
					<td width="20">
						<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					<td width="50%">Rango de Matr&iacute;culas</td>

					<td width="25%">
						<table class="camposform" width="95%">
							<tr>
								<td>
									<%=idCirculo%>-
									<input name="<%=CFolio.ID_MATRICULA_RL%>" type="text" value="" class="camposformtext">
								</td>
							</tr>
							<tr>
								<td>
									<%=idCirculo%>-
									<input name="<%=CFolio.ID_MATRICULA_RR%>" type="text" value="" class="camposformtext">
								</td>
							</tr>
						</table>
					</td>
                   	<td width="25%" align="left">
                   	  	<table border="0" cellpadding="0" cellspacing="2" class="camposform" width="90%">
                      		<tr> 
                      			<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                      			<td>Agregar Rango</td>
                      			<td><input type="image" name="btn_short_anadir" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0""> 
                      		</tr>
                      	</table>
                   	</td>
            </form>
                </tr>
                </table></td></tr>
                
              </table>
              
              <!-- SIR-91 -->
              <br>

          <table width="100%" border="0" cellpadding="0" cellspacing="0">
	          <tr>
    	          <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Subtipo de Solicitud</td>
        	      <td width="16" class="bgnsub"></td>
            	  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
	           </tr>
          </table>
    	  <br>
          <table width="100%" class="camposform">
            <form action="confrontacion.do" method="post" name="SUBTIPO_SOLICITUD" id="SUBTIPO_SOLICITUD">
            <input type="hidden" name="ACCION" value="CONFIRMAR">   
		    <tr>
                  <td>Subtipo solicitud</td>
                  <td>
                  <%try {
        	      			subTipoSolicitud.setCssClase("camposformtext");
                   			subTipoSolicitud.render(request,out);
						}catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
				  %>
                  </td>
                </tr>
          </table>
              <br>

              

           
           
              <hr class="linehorizontal">
          
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" onClick = "return validarNumMatriculas()" name="Folio" width="139" height="21" border="0" id="Folio"></td>
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
<script>
	autoFocus();
</script>