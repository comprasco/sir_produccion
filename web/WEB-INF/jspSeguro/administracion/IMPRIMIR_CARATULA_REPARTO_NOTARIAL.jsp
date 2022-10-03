<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWImpresionRepartoNotarial" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoNota" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoImprimible" %>
<%@page import="gov.sir.core.negocio.modelo.CirculoImpresora" %>
<%@page import="gov.sir.core.negocio.modelo.TipoImprimible" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="java.util.*" %>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/cookies.js"></script>


<%
	TextHelper textHelper = new TextHelper();
	TextAreaHelper helper = new TextAreaHelper();

	boolean impresionOK = false;
	String matricula = "";
	if(session.getAttribute(AWImpresionRepartoNotarial.IMPRESION_CARATULA_EJECUTADA) != null){ 
		impresionOK = true;
		matricula = (String)session.getAttribute(CTurno.ID_TURNO);
		session.removeAttribute(CTurno.ID_TURNO);
		session.removeAttribute(AWImpresionRepartoNotarial.IMPRESION_CARATULA_EJECUTADA);
	}
	
	//ver si toca recagar la pagina
    boolean recarga=true;
    Boolean rec= (Boolean)session.getAttribute(WebKeys.RECARGA);
    if(rec!=null){
    	recarga=rec.booleanValue();
    }
%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
function cambiarAccionSubmit(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}
</script>


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
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Reimpresi&oacute;n
                  	de Reparto Notarial</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          
        <form action="impresionRepartoNotarial.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWImpresionRepartoNotarial.IMPRIMIR_CARATULA%>" value="<%= AWImpresionRepartoNotarial.IMPRIMIR_CARATULA %>">
          
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                   
                    <td class="bgnsub">Carátulas</td>
                    <td width="16" class="bgnsub"></td>
                    <td width="16" class="bgnsub"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
         <table width="100%" class="camposform">
         <%
            if(impresionOK){
            %>  
              <tr>
               <td width="20"></td>
               <td >Se realizó satisfactoriamente la impresión de la carátula.</td>
             </tr>
            <%
            }
            %>         
         </table>   
                
        <br>
        <table width="100%" class="camposform">
           		<tr>
                    
                    <td allign="left" width="250">N&uacute;mero del Turno:</td>
                  <td width="80">
                    <% try {
	                    textHelper.setNombre(CTurno.ID_TURNO);
	                  	textHelper.setCssClase("camposformtext");
	                  	textHelper.setId(CTurno.ID_TURNO);
						textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
				  	</td>
				  	
				  </tr>
				  
				  <!-- 
				  <tr>
                
                
                            
                            <td allign="left" width="250">Seleccionar Impresora:</td>
					        
							 <td width="80">	
		
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
								
								if (impr.getIdTipoImprimible().equals(CTipoImprimible.CERTIFICADO_INMEDIATO)||impr.getIdTipoImprimible().equals(CTipoImprimible.CERTIFICADO_EXENTO)||impr.getIdTipoImprimible().equals(CTipoImprimible.CERTIFICADO_EXTENSO)||impr.getIdTipoImprimible().equals(CTipoImprimible.CERTIFICADO_ASOCIADO)){
								
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
                

		                  <td>&nbsp;</td>                  
		                  <td>                 
		                 
                		</tr>
				  
				   -->
				  
				  <tr>
                
						    
                            <td allign="left" width="250">Número de copias a imprimir:</td>
							 <td width="80">
		
		<% try {
		textHelper = new TextHelper();
		textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
		textHelper.setCssClase("camposformtext");
		textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
		textHelper.setSize("5");
		textHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
		
		%>
		
							               
                  			</td>
                		</tr>
			
			
			
			
			
			
			 <tr>
                     
         
          <td allign="left" width="250">Causal de Reimpresión:</td>
					        
			 <td width="80">
		
            <% 
                   
             try 
             { 
				
				java.util.List tiposNotas = (java.util.List) session.getAttribute(AWImpresionRepartoNotarial.LISTA_INFORMATIVAS_REIMPRESION);
				java.util.Vector vectorNotas = new java.util.Vector();
						 	
				if(tiposNotas==null)
				{
					tiposNotas = new java.util.ArrayList();						 		
				}
					
					
				 		                    
 		        if (tiposNotas!=null)
 		        {
	 		         
	 		         
	 		         for (int i=0; i<tiposNotas.size(); i++)
	 		         {
		 		            
		 		            gov.sir.core.web.helpers.comun.ElementoLista elemento = new gov.sir.core.web.helpers.comun.ElementoLista();

		 		            gov.sir.core.negocio.modelo.TipoNota tipoNota = (gov.sir.core.negocio.modelo.TipoNota)tiposNotas.get(i);
                            		 		            
		 		            elemento.setId(tipoNota.getIdTipoNota());
		 		            
		 		            elemento.setValor(tipoNota.getNombre());
		 		            
		 		            vectorNotas.add(elemento);
		 		      } 
 		        }        

			
			
			
			    gov.sir.core.web.helpers.comun.ListaElementoHelper notasHelper = new gov.sir.core.web.helpers.comun.ListaElementoHelper();

 		        notasHelper.setTipos(vectorNotas);
 		        notasHelper.setNombre(CTipoNota.ID_TIPO_NOTA);
                notasHelper.setCssClase("camposformtext");
                notasHelper.setId(CTipoNota.ID_TIPO_NOTA);
                notasHelper.render(request,out);
                 
			}
							 
			catch(HelperException re)
			{
				out.println("ERROR " + re.getMessage());
			}
			
			catch(Throwable t)
			{
				out.println(t.fillInStackTrace());
				t.printStackTrace();
			}
						 
					%>
		
						  </td>
                

		                  <td>&nbsp;</td>                  
		                  <td>                 
		                 
                		</tr>	  
		
		
		
		
		
		
		
		 <tr>
                     
          
          <td allign="left" width="250">Descripción Motivo Reimpresión:</td>
					        
			 <td width="80">
		
		  
								<%
								try{
									helper.setCols("90");
									helper.setCssClase("camposformtext");
									helper.setId(CTipoNota.DESCRIPCION);
									helper.setNombre(CTipoNota.DESCRIPCION);
									helper.setRows("8");
									helper.setReadOnly(false);
									helper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}	
								%>
								
							  </td>
        </table>
        
                <br>
                <br>
                <table width="100%" class="camposform">
                  <tr>
                    
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWImpresionRepartoNotarial.IMPRIMIR_CARATULA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_reimprimir.gif" width="150" height="21" border="0">
                    </td>
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWImpresionRepartoNotarial.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
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
        </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td></td>
    <td>&nbsp;</td>
    <td></td>
    <td>&nbsp;</td>
  </tr>
</table>
<%if(recarga){%>
	<script>
		cambiarAccionSubmit('<%=AWImpresionRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO_CARATULA_REPARTO%>');
	</script>
<%}%>

</body>

