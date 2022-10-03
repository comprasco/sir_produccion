<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.negocio.modelo.Vereda"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="gov.sir.core.util.DateFormatUtil"%>
<%@page import="gov.sir.core.web.helpers.registro.ActosHelper"%>
<%@page import="gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro"%>
<%@page import="java.util.*"%>

<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.web.helpers.registro.MatriculaCalifHelper"%>


<%
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();
    
    //SE LLENA LA LISTA DE ACTOS PARA MOSTRAR LAS LIQUIDACIONES
    List actos = new ArrayList();
	List liquidaciones = solicitud.getLiquidaciones();    
	Iterator it = liquidaciones.iterator();
	while(it.hasNext()){
		LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro)it.next();		
		actos.addAll(liq.getActos());
	}

	//SE LLENA LA LISTA DE MATRÍCULAS PARA LA REVISIÓN DE CONFRONTACIÓN
	List matriculas = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	
	Iterator is = matriculas.iterator();	
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		idmatriculas.add(temp);
	}	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idmatriculas);		
	

	//HELPER PARA MOSTRAR
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
	TextHelper textHelper = new TextHelper();	
	MatriculaCalifHelper matHelper = new MatriculaCalifHelper();//BORRAR
	matHelper.setFolios(solicitud.getSolicitudFolios());//BORRAR

	//BORRAR
	List avanceWF = new Vector();
	avanceWF.add(new ElementoLista(AWCalificacion.CORRECCION,"Correccion"));
	avanceWF.add(new ElementoLista(AWCalificacion.DIGITACION,"Digitacion"));
	avanceWF.add(new ElementoLista(AWCalificacion.MICRO,"Microfilmacion"));
	avanceWF.add(new ElementoLista(AWCalificacion.ESPECIALIZADO,"Especializdo"));
	avanceWF.add(new ElementoLista(AWCalificacion.ANT_SISTEMA,"Antiguo Sistema"));
	//avanceWF.add(new ElementoLista(AWCalificacion.DEVOLUCION,"Devolucion"));
	ListaElementoHelper helper = new ListaElementoHelper();
	helper.setTipos(avanceWF);

	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));

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


function send(){
   document.forma.submit()
}
</script>

<%
  Boolean aprobarCal;
  Boolean hayExcepcion=(Boolean)session.getAttribute(WebKeys.EXCEPCION);
  if(hayExcepcion==null){
  	hayExcepcion= new Boolean(false);
  }
  if(!hayExcepcion.booleanValue()){
  	aprobarCal=(Boolean)session.getAttribute(WebKeys.APROBAR_CALIFICACION);
  	if (aprobarCal==null)
  	{
%>
<body bgcolor="#CDD8EA" onLoad="send()" background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <form action="calificacion.do" method="POST" name="forma"  id="forma">
  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWCalificacion.MOSTRAR%>" value="<%=AWCalificacion.MOSTRAR%>">
	</form>
<%
 	 }
   }
%>

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Información del turno</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
    
    

    <form action="calificacion.do" method="post" name="CALIFICACION" id="CALIFICACION">
    <input type="hidden" name="ACCION" value="CALIFICAR">



    <!--INICIO REVISIÓN ENCABEZADO-->        
    
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Revisión encabezado</td>
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
				<br>
			    
			    <%
				//TIPO DOCUMENTO
				String tipoDocumento="&nbsp;";
				if(solicitud.getDocumento().getTipoDocumento().getNombre()!=null)
					tipoDocumento= solicitud.getDocumento().getTipoDocumento().getIdTipoDocumento() + " - " + solicitud.getDocumento().getTipoDocumento().getNombre();
					
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
				if(solicitud.getDocumento().getOficinaOrigen().getVereda()!=null){
					Vereda auxVereda=solicitud.getDocumento().getOficinaOrigen().getVereda();
					departamento=auxVereda.getMunicipio().getDepartamento().getNombre();
					municipio=auxVereda.getMunicipio().getNombre();
					vereda=auxVereda.getNombre();
				}
				
				//TIPO OFICINA
				String tipoOficina="&nbsp;";
				if(solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null)
					tipoOficina=solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() + " - " + solicitud.getDocumento().getOficinaOrigen().getNombre();
				
				//NUM OFICINA
				String numOficina="&nbsp;";
				if(solicitud.getDocumento().getOficinaOrigen().getNumero()!=null)
					numOficina=solicitud.getDocumento().getOficinaOrigen().getNumero();			    
			    %>
			    
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Datos B&aacute;sicos </td>
                </tr>
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
                  <td><table width="100%" class="camposform">


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


                    </table></td>
                </tr>
              </table>
	    
			    
              <hr class="linehorizontal">

              <table width="100%" class="camposform">
                <tr>

                <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td>
                  <a href="javascript:cambiarAccion('<%=AWCalificacion.ENVIAR_CORRECCION_ENCABEZADO%>')">
                    <img src="<%=request.getContextPath()%>/jsp/images/btn_correccion_encabezado.gif" name="Folio" width="190" height="21" border="0" id="Folio"/>
                  </a>
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
      
    <!--FIN REVISIÓN ENCABEZADO-->        
      
      
      
      
      
    <!--INICIO REVISIÓN CONFRONTACIÓN-->  
      
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
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Revisión confrontación</td>
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
				    		  //HELPER TABLA MATRICULAS
							  TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
							  
		                      tablaHelper.setColCount(5);
		                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
	                          //tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
		               		  tablaHelper.render(request, out);
		                    }
		                    catch(HelperException re){
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
                  <td>
                  <a href="javascript:cambiarAccion('<%=AWCalificacion.CONFRONTACION%>')">
                    <img src="<%=request.getContextPath()%>/jsp/images/btn_enviar_confrontacion.gif" name="Folio" width="190" height="21" border="0" id="Folio"/>
                  </a>
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
      
      <!--FIN DE REVISIÓN DE CONFRONTACIÓN-->
      
      
      
      
      
      
    <!--INICIO REVISIÓN LIQUIDACIÓN-->  
      
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
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Revisión liquidación</td>
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
							    ActosHelper actoHelper = new ActosHelper();							    
							    actoHelper.setActos(actos);
							    actoHelper.setMostrarEliminar(false);
		          			    actoHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>

                  </td>
                </tr>
              </table>
              

			    
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Otros impuestos</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
					
					  <%
					  int paso = 0;
					  it = liquidaciones.iterator();
					  
					  while(it.hasNext()){
					  	LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro)it.next();
					  	if(liq.getOtroImpuesto()!=null){
					  %>
                      
                      <tr>
                        <td width="25%" align="right">Descripción</td>
                        <td class="campositem" width="25%" align="left">
                        <%=liq.getOtroImpuesto()%>
                        </td>
                        <td width="25%" align="right">Valor</td>
                        <td class="campositem" width="25%" align="left"> 
                        <%="" + java.text.NumberFormat.getInstance().format(liq.getValorImpuestos())%>
                        </td>
                      </tr>        
                      <%
                       paso++;
                       }
                      }
                      %>              
                      <%
                      if(paso==0){
                      %>
                      <tr>
                        <td>No hay otros impuestos asociados</td>
                      </tr> 
                      <%
                      }
                      %>                        
                      
                  </table></td>
                </tr>
               </table>


              <hr class="linehorizontal">

              <table width="100%" class="camposform">
                <tr>

                <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>

				  <td ><a href="javascript:document.CALIFICACION.action='calificacion.mayor.valor.view'; document.CALIFICACION.submit();"><img src="<%=request.getContextPath()%>/jsp/images/btn_mayor_valor.gif" name="Folio" width="150" height="21" border="0" id="Folio" ></a></td>                  
                  
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
      
      <!--FIN DE REVISIÓN DE LIQUIDACIÓN-->      
      
      
      
      
      
    <!--INICIO DE LO QUE HAY QUE BORRAR-->        
    
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Revisión encabezado</td>
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

			    
              <table width="100%">
                <tr>
                  <td>

                      <% try {
                  			    matHelper.render(request,out);
							}
								catch(HelperException re){
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
                <td>Selecione al Rol al cual desea Enviar el Caso</td>
                <td>
                   <% try {
                   				helper.setId(AWCalificacion.RESPUESTAWF);
                   				helper.setNombre(AWCalificacion.RESPUESTAWF);
                   				helper.setCssClase("camposformtext");
                  			    helper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                </td>
                <td width="150"><a href="javascript:cambiarAccion('AVANZAR')"><img name="imageField" src="<%=request.getContextPath()%>/jsp/images/btn_enviar_caso.gif" width="139" height="21" border="0"></a></td>
                <td>
                  <DIV align="center">
                    <a href="javascript:cambiarAccion('ENGLOBAR')">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_englobar.gif" name="Siguiente" width="150" height="21" border="0" id="Siguiente"/>
                    </a>
                  </DIV>
                </td>
              </tr>
            </table>


              <table width="100%" class="camposform">
                <tr>



                  <%
                  aprobarCal=(Boolean)session.getAttribute(WebKeys.APROBAR_CALIFICACION);
                  if( (aprobarCal!=null)&& (aprobarCal.booleanValue()) )
                  {
                  %>
	                  <td width="150" align="center">
	                  <a href="javascript:cambiarAccion('CONFIRMAR')">
	                    <img src="<%=request.getContextPath()%>/jsp/images/btn_aprobar.gif" name="Folio" width="139" height="21" border="0" id="Folio"/>
	                  </a></td>
	                  <td width="150" align="center">
	                  <a href="<%=request.getContextPath()%>/servlet/PdfServlet?<%=gov.sir.core.web.PdfServlet.TIPO_DOCUMENTO%>=<%=gov.sir.core.negocio.modelo.constantes.CTipoImprimible.TIPO_CALIFICACION%>">
	                    <img src="<%=request.getContextPath()%>/jsp/images/btn_vistaprevia.gif" name="Folio" width="139" height="21" border="0" id="Folio"/>
	                  </a></td>
                  <%
                  }
                  else
                  {
                  %>
	                  <td width="150" align="center">&nbsp;</td>
                  <%
                  }
                  session.removeAttribute(WebKeys.APROBAR_CALIFICACION);
                  %>


                  <td width="150">
                  <a href="javascript:devolver()">
                    <img src="<%=request.getContextPath()%>/jsp/images/btn_devolver.gif" name="Folio" width="139" height="21" border="0" id="Folio"/>
                  </a>
                  <input type="hidden" name="DEVOLUCION" value=""/>
                  </td>
				  <td width="150"><a href="javascript:document.CALIFICACION.action='calificacion.mayor.valor.view'; document.CALIFICACION.submit();"><img src="<%=request.getContextPath()%>/jsp/images/btn_mayor_valor.gif" name="Folio" width="150" height="21" border="0" id="Folio" ></a></td>
                  <td width="150"><a href="javascript:cambiarAccion('<%= AWCalificacion.IMPRIMIR_FORMULARIO_CALIFICACION %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" name="Folio" width="150" height="21" border="0" id="Folio" ></a></td>
                  <td>&nbsp;</td>
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
      
    <!--BORRAR-->        
      
        
      
    </form>
      
      


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
