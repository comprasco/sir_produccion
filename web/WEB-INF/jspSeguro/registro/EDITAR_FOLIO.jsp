<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.helpers.correccion.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>


<%
	//LISTA DE ESTADO DEL FOLIO
	ListaElementoHelper estadoFolioHelper = new ListaElementoHelper();	
	List estadosFolio = (List)session.getServletContext().getAttribute(WebKeys.LISTA_ESTADOS_FOLIO_ELEMENTO_LISTA);
	if(estadosFolio == null){
		estadosFolio = new Vector();
	}		
	estadoFolioHelper.setOrdenar(false);
	estadoFolioHelper.setTipos(estadosFolio);
	estadoFolioHelper.setCssClase("camposformtext");	
	

	//LISTA DE EJES PARA LAS DIRECCIONES
	ListaElementoHelper ejesHelper = new ListaElementoHelper();
	List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
	if(ejes == null){
		ejes = new Vector();
	}
	ejesHelper.setCssClase("camposformtext");
	ejesHelper.setOrdenar(false);
	ejesHelper.setTipos(ejes);

	MostrarFechaHelper fecha = new MostrarFechaHelper();	
	DireccionesHelper dirHelper = new DireccionesHelper(gov.sir.core.web.acciones.registro.AWSegregacion.AGREGAR_DIRECCION,gov.sir.core.web.acciones.registro.AWSegregacion.ELIMINAR_DIRECCION);
	dirHelper.setNombreFormaEdicionDireccion("CORRECCION");
	
	
	dirHelper.setFolioSesion(WebKeys.FOLIO_EDITADO);
	ListaElementoHelper ejes2Helper = new ListaElementoHelper();
	ejes2Helper.setCssClase("camposformtext");
	ejes2Helper.setOrdenar(false);
	ejes2Helper.setTipos(ejes);
    TextHelper textHelper = new TextHelper();
    TDHelper tdHelper = new TDHelper();
    TextHelper hiddenHelper = new TextHelper();
    hiddenHelper.setTipo("hidden");
 	//hiddenHelper.setTipo("text");
    TextAreaHelper textAreaHelper = new TextAreaHelper();
    AnotacionesModificarFolioHelper anotacionesModificacionHelper = new AnotacionesModificarFolioHelper();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());

	//RECUPERAR INFORMACIÓN DE LA SESIÓN
	String idMatricula = (String)request.getSession().getAttribute(CFolio.ID_MATRICULA);
	String fechaApertura = (String)request.getSession().getAttribute(gov.sir.core.web.acciones.correccion.AWModificarFolio.FOLIO_FECHA_APERTURA);
	String documento     = (String)request.getSession().getAttribute(gov.sir.core.web.acciones.correccion.AWModificarFolio.FOLIO_COD_DOCUMENTO);
    Circulo circulo   = (Circulo)session.getAttribute(WebKeys.CIRCULO);
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>

<script type="text/javascript">
function cambiarCombo(text) {
	try{
		document.getElementById('<%=CDocumento.TIPO_DOCUMENTO%>').options[text].selected=true
	}catch(e){
		document.getElementById('<%=CDocumento.TIPO_DOCUMENTO%>').value='<%=WebKeys.SIN_SELECCIONAR%>';
		document.getElementById('<%=CDocumento.ID_TIPO_DOCUMENTO%>').value='';
	}
}
function cambiarAccion(text) {
	document.CORRECCION.ACCION.value = text;
	document.CORRECCION.submit();
}

function quitar(pos,accion){
	if(confirm("Esta seguro que desea eliminar la dirección ?")){
		document.CORRECCION.POSICION.value = pos;
		cambiarAccion(accion);		
	}
}
function setTipoOficina(){
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function oficinas(nombre,valor,dimensiones)
{
	document.CORRECCION.ACCION.value='<%//AWModificarFolio.PRESERVAR_INFO%>';
	var idDepto = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
}

function juridica(nombre,valor,dimensiones)
{
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.CORRECCION.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function locacion(nombre,valor,dimensiones){
	document.getElementById('id_depto').value=valor+"_ID_DEPTO";
	document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
	document.getElementById('id_munic').value=valor+"_ID_MUNIC";
	document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
	document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
	document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
    popup=window.open(nombre,valor,dimensiones);
    popup.focus();
}

</script>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">
           Editar folio derivado - Segregaci&oacute;n</td>
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
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <form action="segregacion.do" name="CORRECCION" id="CORRECCION" method="post">
    <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>">


    <input type="hidden" name="test" id="test" value="test">            
    <input type="hidden" name="<%=WebKeys.POSICION%>" id="<%=WebKeys.POSICION%>" >

    <input name="Depto" type="hidden" id="id_depto" value="">
    <input name="Depto" type="hidden" id="nom_Depto" value="">
    <input name="Mpio" type="hidden" id="id_munic" value="">
    <input name="Mpio" type="hidden" id="nom_munic" value="">    
    <input name="Ver" type="hidden" id="id_vereda" value="">
    <input name="Ver" type="hidden" id="nom_vereda" value="">
        
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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folio</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
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
	<%
	Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
	Fase faseCal = new Fase();
	faseCal.setID(CFase.CAL_CALIFICACION);
	if(fase.equals(faseCal)){%>
    <table width="100%" class="camposform">
        <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif" width="20" height="15"></td>
            <td width="20" class="campositem">N&ordm;</td>
            <td class="campositem"><%=idMatricula!=null?idMatricula:""%>&nbsp;</td>
        </tr>
    </table>
    <%}%>
    
    
    

    
    <br>
    
    
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr> 
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub">Datos B&aacute;sicos </td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
</table>
              <table width="100%" class="camposform">
                  <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Ubicaci&oacute;n</td>
                  </tr>
                  <tr> 
                      <td>&nbsp;</td>
                      <td>
                      	  <table width="100%" class="camposform">
                          <tr>
                              <td width="87">Circulo</td>
                              <td class="campositem"><%=(circulo!=null && circulo.getNombre()!=null)?circulo.getNombre():""%>&nbsp;</td>
                          </tr>
                      	  </table>
                      	  
                      	  
   	                      <table width="100%" class="camposform">
   	                      
	                      <tr>
	                      <td width="85">Departamento</td>
	                      <td>	
	                          <%
 						    try {
								hiddenHelper.setNombre(CDepartamento.ID_DEPARTAMENTO);
								hiddenHelper.setCssClase("camposformtext");
								hiddenHelper.setId(CDepartamento.ID_DEPARTAMENTO);
								hiddenHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}	                          
	                          
							try {
								tdHelper.setCssClase("campositem");
								tdHelper.setId(CDepartamento.NOMBRE_DEPARTAMENTO);
								tdHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							 %>
						  </td>
						  
	                      <td width="85">Municipio</td>
	                      <td>
	                        <%	
							try {
								hiddenHelper.setNombre(CMunicipio.ID_MUNICIPIO);
								hiddenHelper.setCssClase("camposformtext");
								hiddenHelper.setId(CMunicipio.ID_MUNICIPIO);
								hiddenHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}	                        
							try {
								tdHelper.setCssClase("campositem");
								tdHelper.setId(CMunicipio.NOMBRE_MUNICIPIO);
								tdHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
							%>
							</td>
							
	                        <td width="85">Vereda</td>
	                        <td>
	                        	<%	
								try {
									hiddenHelper.setNombre(CVereda.ID_VEREDA);
									hiddenHelper.setCssClase("camposformtext");
									hiddenHelper.setId(CVereda.ID_VEREDA);
									hiddenHelper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
									                        	
								try {
									tdHelper.setCssClase("campositem");
									tdHelper.setId(CVereda.NOMBRE_VEREDA);
									tdHelper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}								
								%>
	                          </td>
	                      </tr>
	                  </table>
	                  
  	         
	                  
                  </td>
                  </tr>
              </table>

	
				<!--Encabezado del documento-->
			    <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    <tr> 
				    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
				    <td class="bgnsub">Documento</td>
				    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
				    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			    </tr>
				</table>              
              
      
 
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
							<td class="campositem">&nbsp;                        
                        	<%
                        	String idTipoDocumento = (String)session.getAttribute(CDocumento.ID_TIPO_DOCUMENTO);
                        	String tipoDocumento = (String)session.getAttribute(CDocumento.TIPO_DOCUMENTO);
                        	String numDocumento = (String)session.getAttribute(CDocumento.NUM_DOCUMENTO);
                        	String fechaDocumento = (String)session.getAttribute(CDocumento.FECHA_RADICACION);                        	                        	
                        	
                        	%>
                        <%=idTipoDocumento%>
                        </td>                            
						<td class="campositem">&nbsp;                            
                            <%
							List tiposDocs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
							
							Iterator it = tiposDocs.iterator();
							while(it.hasNext()){
								ElementoLista el = (ElementoLista)it.next();
								if(el.getId().equals(idTipoDocumento)){
									tipoDocumento = el.getValor();
								}
							}
						    %>
							<%=tipoDocumento%>                          
							</td>
                            <td>N&uacute;mero</td>
							<td class="campositem">&nbsp;
							<%=numDocumento%>                            
                            </td>
                            <td>Fecha</td>
                            <td><table border="0" cellpadding="0" cellspacing="0">
                                <tr>
							<td class="campositem">&nbsp;
							<%=fechaDocumento%>							
							</td>

                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>

     
     

                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                      <td>Oficina de Procedencia </td>
                    </tr>
                <% 
                gov.sir.core.web.helpers.registro.OficinaHelper oficinaHelper = new gov.sir.core.web.helpers.registro.OficinaHelper ();
                try {
                    oficinaHelper.setReadOnly(true);                
                    oficinaHelper.render(request,out);
				}catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}
				%>                    
                    
				</td>
				</tr>
				</table>
				

			    
			    <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    <tr> 
				    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
				    <td class="bgnsub">Datos Folio</td>
				    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
				    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
			    </tr>
				</table>       
								
 
              
            <table width="100%" class="camposform">
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>Datos Folio</td>
                </tr>
                <tr>
                <td>&nbsp;</td>
                <td>
 
                 <table width="100%" class="camposform">
                
                    <tr>
                    <td width="130">C&oacute;digo Catastral</td>
                    <td >
                   <% try {
 		                       textHelper.setNombre(CFolio.FOLIO_COD_CATASTRAL);
                  			   textHelper.setCssClase("camposformtext");
                  			   textHelper.setId(CFolio.FOLIO_COD_CATASTRAL);
                  			    textHelper.setReadonly(false);                  			   
        			            textHelper.setEditable(true);                  			    
							   textHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						  }
					%>
                    </td>
                    <td width="20%">&nbsp;</td>
                    </tr>
                    
                    <tr>
                    <td>Complementaci&oacute;n</td>
                    <td>
                   <% try {
   		                       textAreaHelper.setNombre(CFolio.FOLIO_COMPLEMENTACION);
 		                       textAreaHelper.setCols("100");
 		                       textAreaHelper.setRows("7");
                  			   textAreaHelper.setCssClase("camposformtext");
                  			   textAreaHelper.setId(CFolio.FOLIO_COMPLEMENTACION);
							   textAreaHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						  }
					%>
                    </td>
                    <td width="20%">&nbsp;</td>
                    </tr>                                    
                
                    <tr>
                    <td>Descripción, cabida y linderos</td>
					<td class="campositem">&nbsp;
					<%
                    String linderos = (String)session.getAttribute(CFolio.FOLIO_LINDERO);
                    String idTipoPredio = (String)session.getAttribute(CFolio.FOLIO_TIPO_PREDIO);
                    String tipoPredio = (String)session.getAttribute(CFolio.FOLIO_TIPO_PREDIO);
                    %>
                    <%=linderos%>
                    </td>
                    <td width="20%">&nbsp;</td>
                    </tr>

                    
                    <tr>
                    <td>Tipo de Predio </td>
					<td class="campositem" >&nbsp;
                    <%
					List tiposPredio = (List)session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);
					
					Iterator itTP = tiposPredio.iterator();
					while(itTP.hasNext()){
						ElementoLista el = (ElementoLista)itTP.next();
						if(el.getId().equals(idTipoPredio)){
							tipoPredio = el.getValor();
						}
					}
				    %>					
					<%=tipoPredio%>
                    </td>
                    <td width="20%">&nbsp;</td>
                    </tr>
                    
                <%
                
                if(!fase.getID().equals(CFase.CAL_CALIFICACION)){%>    
                    
                <tr>
                    <td >Estado folio</td>
                    <td>
					<% try {
							estadoFolioHelper.setNombre(CFolio.FOLIO_ESTADO_FOLIO);
							estadoFolioHelper.setId(CFolio.FOLIO_ESTADO_FOLIO);
							estadoFolioHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
                    </td>   
                    <td width="20%">&nbsp;</td>                      
                </tr>      
                <%
                }else{
                %>
                <tr style='display:none'>
                    <td >Estado folio</td>
                    <td>
					<% try {
							estadoFolioHelper.setNombre(CFolio.FOLIO_ESTADO_FOLIO);
							estadoFolioHelper.setId(CFolio.FOLIO_ESTADO_FOLIO);
							estadoFolioHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
                    </td>           
                    <td width="20%">&nbsp;</td>              
                </tr>          
                <%
                }
                %>                
                              
                </table>
                
                
                </td>
                </tr>
            </table>
            
  
                          
            
            

              <hr class="linehorizontal">
            <br>              

                   <%try {
						   dirHelper.render(request,out);
					  }	catch(HelperException re){
						   out.println("ERROR " + re.getMessage());
					  }
					%>

          <hr class="linehorizontal">
          <br>
  
  
	<!--Tabla de Salvedad Folio-->  

	<%
	if(!fase.getID().equals(CFase.CAL_CALIFICACION)){
	%>	
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr> 
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub">Salvedad Folio </td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
	</table>
	     
    <table width="100%" class="camposform">
         <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td>Salvedad</td>
          </tr> 
              <tr>
                 <td width="20">&nbsp;</td>
                 <td>                    
                 <%try {
 		                 textAreaHelper.setNombre(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
 		                 textAreaHelper.setCols("100");
 		                 textAreaHelper.setRows("7");
                  	     textAreaHelper.setCssClase("camposformtext");
                  		 textAreaHelper.setId(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
						 textAreaHelper.render(request,out);
					}catch(HelperException re){
					 	 out.println("ERROR " + re.getMessage());
					}
					 %>                    
                </td>
           </tr>
     </table>     
     
     <%
     }
     %>                      
   
         </td>
         <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
		<tr> 
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
	</table>
	


					
						
	
			
	<!--TABLA DE ACEPTAR CANCELAR-->				
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
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
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">OPCIONES DEL FOLIO</td>
	<td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	<td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
	<td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
	</tr>
	</table>
	</td>
	<td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
	</tr>
	</table>
	</td>
	<td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
	</tr>
	<tr>
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
	<td valign="top" bgcolor="#79849B" class="tdtablacentral">
	<table width="100%" class="camposform">
	<tr>
	<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	<td width="140"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWSegregacion.GUARDAR_CAMBIOS_FOLIO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_grabartmp.gif" width="150" height="21" border="0"></a></td>
	<td width="140"><a href="tabla.segregacion.confirmacion.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="150" height="21" border="0"></a></td>	
	<td colspan='2'>&nbsp;</td>
	</form>
	<td>&nbsp;</td>
	</tr>
	</table>
	</td>
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
	</tr>
	<tr>
	<td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
	<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
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