<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.registro.AWMesa"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.TurnoHistoria"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRespuestaMesaControl"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRespuesta"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>

<%	 
	session.removeAttribute(AWMesa.MESA_CONTROL);

	TextHelper textHelper= new TextHelper();
	ListaElementoHelper tipoRespuesta= new ListaElementoHelper();
	String matricula="";
	List listaTurnos= new Vector();
	List listaResultadosTurnos=new Vector();
	List listaResultadosRespuesta= new Vector();
	List listaRespuesta= new Vector();
	Calendar cal= Calendar.getInstance();
	Date da= new Date();
	cal.setTime(da);
	int valano=cal.get(Calendar.YEAR);

	int origenMayorValor = 0;
	
	//obtener la lista de turnos
	listaTurnos=(List)request.getSession().getAttribute(AWMesa.LISTA_TURNOS_CONSULTA);
	
	//inicializar los valores de las listas de respuesta
	if(listaTurnos!=null && listaTurnos.size()>0){
		Iterator iturnos=listaTurnos.iterator();
		for(;iturnos.hasNext();){
			Turno temp=(Turno) iturnos.next();
			String idTurno= temp.getIdWorkflow();
			SolicitudRegistro stemp = (SolicitudRegistro)temp.getSolicitud();
			String clasificacion="";
			if(stemp.getClasificacionRegistro()!=null)
				clasificacion=stemp.getClasificacionRegistro();
			if(clasificacion==null || clasificacion.trim().equals("")){
				if(temp.getUltimaRespuesta().equals(CRespuesta.OK) || temp.getUltimaRespuesta().equals(CRespuesta.CONFIRMAR)){
					clasificacion = CRespuestaMesaControl.INSCRITO;
				}else if(temp.getUltimaRespuesta().equals(CRespuesta.MAYOR_VALOR)){
					clasificacion = CRespuestaMesaControl.MAYOR_VALOR;
				}else if(temp.getUltimaRespuesta().equals(CRespuesta.DEVOLUCION)){
					clasificacion = CRespuestaMesaControl.DEVUELTO;
				}else if(temp.getUltimaRespuesta().equals(CRespuesta.INSCRIPCION_PARCIAL)){
					clasificacion = CRespuestaMesaControl.INSCRIPCION_PARCIAL;
				}else{
					clasificacion = "&nbsp;";
				}
			}
			listaResultadosTurnos.add(idTurno);
			listaResultadosRespuesta.add(clasificacion);
		}
	}
	
    String ano=Integer.toString(valano);
	String circulo="";
	
	Circulo oCirculo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
	circulo=oCirculo.getIdCirculo();
	
	String proceso=AWMesa.ID_PROCESO_REGISTRO;
	
	matricula=ano+"-"+circulo+"-"+proceso+"-";
	if (session.getAttribute(AWMesa.TURNO_DESDE) == null)
	{
		session.setAttribute(AWMesa.TURNO_DESDE,matricula);
	}
	if (session.getAttribute(AWMesa.TURNO_HASTA) == null)
	{
		session.setAttribute(AWMesa.TURNO_HASTA,matricula);	
	}
	
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	
	//Activar flags de mayorValor, inscripcionParcial y devuelto segun la respuesta del turno
	boolean mayorValor=false;
	boolean devuelto=false;
	boolean inscripcionParcial = false;
	boolean isTestamento = false;
	//obtener historia del turno
	List historia= new Vector();
	historia=turno.getHistorials();
	
	if(historia != null){
	} else {
		historia = new Vector();
	}
	
	List turnosCalificacion=new Vector();
	List turnosTestamentos=new Vector();	
	Iterator ih= historia.iterator();
	for(;ih.hasNext();){
		TurnoHistoria temp= (TurnoHistoria)ih.next();
		if(temp.getFase() != null && temp.getFase().equals(AWMesa.CAL_CALIFICACION)){
			turnosCalificacion.add(temp);
		}
		if(temp.getFase() != null && temp.getFase().equals(AWMesa.REG_TESTAMENTO)){
			turnosTestamentos.add(temp);
			isTestamento = true;
		}		
	}
	
	ih= turnosCalificacion.iterator();
	TurnoHistoria th=null;
	String idTH="-1";
	for(;ih.hasNext();){
		TurnoHistoria temp= (TurnoHistoria)ih.next();
		if(Integer.parseInt(idTH)<Integer.parseInt(temp.getIdTurnoHistoria())){
			idTH=temp.getIdTurnoHistoria();
			th=temp;
		}
	}
	
	ih= turnosTestamentos.iterator();	
	TurnoHistoria thTestamento=null;
	idTH="-1";
	for(;ih.hasNext();){
		TurnoHistoria temp= (TurnoHistoria)ih.next();
		if(Integer.parseInt(idTH)<Integer.parseInt(temp.getIdTurnoHistoria())){
			idTH=temp.getIdTurnoHistoria();
			thTestamento=temp;
		}
	}	
	
	if(th!=null){
		if(th.getRespuesta().equals(AWMesa.DEVOLUCION)){
			devuelto=true;
		}else if(th.getRespuesta().equals(AWMesa.MAYOR_VALOR)){
			mayorValor=true;
		}else if(th.getRespuesta().equals(AWMesa.INSCRIPCION_PARCIAL)){
			inscripcionParcial=true;
		}
		
		if(mayorValor){
			listaRespuesta.add(new ElementoLista(CRespuestaMesaControl.MAYOR_VALOR,CRespuestaMesaControl.MAYOR_VALOR));
		}else if(devuelto){
			listaRespuesta.add(new ElementoLista(CRespuestaMesaControl.DEVUELTO,CRespuestaMesaControl.DEVUELTO));
		}else if(inscripcionParcial){
			//listaRespuesta.add(new ElementoLista(CRespuestaMesaControl.REGISTRO_PARCIAL,CRespuestaMesaControl.REGISTRO_PARCIAL));
			listaRespuesta.add(new ElementoLista(CRespuestaMesaControl.REGISTRO_PARCIAL,CRespuestaMesaControl.INSCRIPCION_PARCIAL));
		}else {
			listaRespuesta.add(new ElementoLista(CRespuestaMesaControl.INSCRITO,CRespuestaMesaControl.INSCRITO));
			//listaRespuesta.add(new ElementoLista(CRespuestaMesaControl.REGISTRO_PARCIAL,CRespuestaMesaControl.REGISTRO_PARCIAL));
		}		
	}
	
	if(thTestamento!=null){
		if(thTestamento.getRespuesta().equals(AWMesa.CONFIRMAR)){
			listaRespuesta.add(new ElementoLista(CRespuestaMesaControl.INSCRITO,CRespuestaMesaControl.INSCRITO));
		}else {
			listaRespuesta.add(new ElementoLista(CRespuestaMesaControl.DEVUELTO,CRespuestaMesaControl.DEVUELTO));
		}
	}
%>

  
   
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
	   document.BUSCAR.<%= AWMesa.ANIO %>.value = '<%=ano %>';
       document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
       document.BUSCAR.submit();
}		

</script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<form action="mesa.do" method="POST" name="BUSCAR" id="BUSCAR">
<input  type="hidden" name="<%= WebKeys.ACCION %>" id="" value="">
<input  type="hidden" name="<%= AWMesa.ANIO %>" id="" value="">

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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Mesa
                  de control
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
             
             <BR>
             <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td class="bgnsub">Listar turnos por clasificaci&oacute;n </td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              
              <table width="100%" class="camposform">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="1%"></td>
                  <td colspan="3" class="titulotbcentral">Busqueda de turnos por rangos </td>
         
                </tr>
                <tr>
                  <td align="left">&nbsp;</td>
                  <td width="5%" align="left">Turno desde:</td>
                  <!--<td width="5%" align="right"><%=matricula%></td>-->
                  <td width="15%">
                  		<%try {
     							    							
 		                        textHelper.setNombre(AWMesa.TURNO_DESDE);
 		                        textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(AWMesa.TURNO_DESDE);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							%>
                  </td>
                  <td width="25%">&nbsp;</td>
                </tr>
                <tr>
                  <td align="left">&nbsp;</td>
                  <td>Turno hasta:</td>
                  <!--<td align="right"><%=matricula%></td>-->
                  <td>
                  		<%try {
     							    							
 		                        textHelper.setNombre(AWMesa.TURNO_HASTA);
 		                        textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(AWMesa.TURNO_HASTA);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							%>
                  </td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td align="center" colspan="2">&nbsp;</td>
                  <td><a href="javascript:cambiarAccion('<%= AWMesa.CONSULTAR_TURNOS %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
                </tr>
              </table>
              
              
              <br>
              
              <table width="100%" class="camposform">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td colspan="3" class="titulotbcentral">Resultado de la busqueda </td>
                </tr>
                <tr>
                 
                  <td width="15%" align="center">Numero del turno</td>
                  <td width="15%" align="center">Resultado</td>
                  <td width="70%">&nbsp;</td>
                </tr>
                <%
                	
                	if(listaResultadosTurnos!= null && listaResultadosTurnos.size()!=0){
                		if(listaResultadosRespuesta!= null && listaResultadosRespuesta.size()!=0){
                			if(listaResultadosTurnos.size()== listaResultadosRespuesta.size()){
	                			Iterator it= listaResultadosTurnos.iterator(); 
	                			Iterator ir= listaResultadosRespuesta.iterator();
	                			for(;it.hasNext();){
	                				String sturno= (String)it.next();
	                				String resultado= (String) ir.next();
	                				%>
	                				<tr>
					                  <td>&nbsp;</td>
					                  <td class="campositem"><%=sturno%></td>
					                  <td class="campositem"><%=resultado%></td>
					                  <td>&nbsp;</td>
					                </tr>
	                				<%
	                			}
                			}
                		}
                	}
                		%>
                
              </table>
              
              
           
           
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td wudth="15%">Respuesta: </td>
                  <td width="20%">
                  <%try {
                  			tipoRespuesta.setCssClase("camposformtext");
                  			tipoRespuesta.setId(AWMesa.RESPUESTA);
                  			tipoRespuesta.setTipos(listaRespuesta);
                  			
                  			if(mayorValor){
                  				tipoRespuesta.setSelected(CRespuestaMesaControl.MAYOR_VALOR);
								origenMayorValor = 1;
                  			}else if(devuelto){
                  				tipoRespuesta.setSelected(CRespuestaMesaControl.DEVUELTO);
                  			}
                  			
                  			tipoRespuesta.setNombre(AWMesa.RESPUESTA);
                   			tipoRespuesta.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
						
					%>
                  
                  </td>
                  <td width="15%"><a href="javascript:cambiarAccion('<%= AWMesa.ENVIAR_RESPUESTA %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
                  <%if (origenMayorValor==0 && !isTestamento) {%>
                  	<td width="15%"><a href="javascript:cambiarAccion('<%= AWMesa.DEVOLVER_REVISION %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_calificacion.gif" name="Folio" width="150" height="21" border="0" id="Folio"></td>
                  <%}%>
                  	<!-- Modifica Pablo Quintana junio 19 2008 Sí es testamento se da la opción de devolverlo a testamento para corrección-->
                  	<%if(isTestamento){ %>
	                  	<td width="15%"><a href="javascript:cambiarAccion('<%= AWMesa.DEVOLVER_TESTAMENTO %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_devolver_testamento.gif" name="Folio" width="170" height="21" border="0" id="Folio"></td>
                  	<%} %>
					<td width="15%"><a href="admin.relacion.view?MESA_CONTROL=TRUE"><img src="<%=request.getContextPath()%>/jsp/images/btn_crear_relaciones.gif" name="Folio" width="180" height="21" border="0" id="Folio"></a></td>
          <% if(inscripcionParcial){
              
          %>
                      <td>
              <a target="popup"  onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?Calf=<%=turno.getIdWorkflow()%>','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_observar_formularioC.gif" name="Folio" border="0" width="150" height="21"  id="Folio"/>
             </a>
                  </td>
                        <td>
              <a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?ServRE=<%=turno.getIdWorkflow()%>','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_observar_notaD.gif" name="Folio1" border="0" width="150" height="21"  id="Folio1"/>
             </a>
                  </td>
                  <% }else{   %>
                  
                     <td>
              <a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?Serv=1','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif" name="Folio" width="150" height="21" border="0" id="Folio"/>
             </a>
                  </td>
                  <% } %>
                  <td width="35%">&nbsp;</td>
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
