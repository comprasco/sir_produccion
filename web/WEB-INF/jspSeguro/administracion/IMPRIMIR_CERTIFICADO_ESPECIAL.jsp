<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWImpresionFolioEspecial" %>
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
<!--
 * @author      :   Julio Alc?zar Rivas
 * @change      :   Nueva funcion para cambiar action y value del form
 * Caso Mantis  :   02359
!-->
<%@page import="gov.sir.core.web.acciones.administracion.AWTrasladoTurno" %>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/cookies.js"></script>


<%
	TextHelper textHelper = new TextHelper();
	TextAreaHelper helper = new TextAreaHelper();

	boolean impresionOK = false;
	String matricula = "";
	if(session.getAttribute(AWImpresionFolioEspecial.IMPRESION_CERTIFICADO_EJECUTADA) != null){ 
		impresionOK = true;
		matricula = (String)session.getAttribute(CTurno.ID_TURNO);
		session.removeAttribute(CTurno.ID_TURNO);
		session.removeAttribute(AWImpresionFolioEspecial.IMPRESION_CERTIFICADO_EJECUTADA);
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
	document.BUSCAR_ESPECIAL.<%= WebKeys.ACCION %>.value = text;
}
function cambiarAccionSubmit(text) {
	document.BUSCAR_ESPECIAL.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR_ESPECIAL.submit();
}
/*
 * @author      :   Julio Alc?zar Rivas
 * @change      :   Nueva funcion para cambiar action y value del form
 * Caso Mantis  :   02359
*/
function cambiarAccionSubmit2(text1,text2) {
        document.BUSCAR_ESPECIAL.action = text2;
	document.BUSCAR_ESPECIAL.<%= WebKeys.ACCION %>.value = text1;
}
function generarCertificadoEspecial(text){
        document.BUSCAR_ESPECIAL.action = 'impresionFolioEspecial.do';
        document.BUSCAR_ESPECIAL.ACCION.value = text;
        document.BUSCAR_ESPECIAL.submit();
}
function goBack(text){
        document.BUSCAR_ESPECIAL.action = 'impresionFolio.do';
        document.BUSCAR_ESPECIAL.ACCION.value = text;
        document.BUSCAR_ESPECIAL.submit();
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
                  	de Certificados</td>
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
          
        <form action="impresionFolioEspecial.do" method="POST" name="BUSCAR_ESPECIAL" id="BUSCAR_ESPECIAL">
        <input type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWImpresionFolioEspecial.IMPRIMIR_CERTIFICADO%>" value="<%= AWImpresionFolioEspecial.IMPRIMIR_CERTIFICADO %>">
          
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                   
                    <td class="bgnsub">Certificados</td>
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
               <td >Se realiz? satisfactoriamente la impresi?n del certificado <%= matricula %></td>
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
				  
				  
				  
				  <tr>
                
						    
                            <td allign="left" width="250">N?mero de copias a imprimir:</td>
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
                     
         
          <td allign="left" width="250">Causal de Reimpresi?n:</td>
					        
			 <td width="80">
		
            <% 
                   
             try 
             { 
				
				java.util.List tiposNotas = (java.util.List) session.getAttribute(AWImpresionFolioEspecial.LISTA_INFORMATIVAS_REIMPRESION);
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
                     
          
          <td allign="left" width="250">Descripci?n Motivo Reimpresi?n:</td>
					        
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
                    
                    <td width="10%">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWImpresionFolioEspecial.IMPRIMIR_CERTIFICADO%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_reimprimir.gif" width="150" height="21" border="0">
                    </td>
                    <!--
                      * @author      :   Julio Alc?zar Rivas
                      * @change      :   Se agregan los botones de los procesos Oficio Pertenencia y Cambio de Matricula
                                         Se elimina el boton Volver cuya funcion pasa al .jsp header de la vista
                      * Caso Mantis  :   02359
                    !-->
                    <td><a href="javascript:goBack('BACK')"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" border="0"></a> </td>

                    <td width="67%">
                    </td>
                    <!--
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWImpresionFolioEspecial.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
                    !-->
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
		cambiarAccionSubmit('<%=AWImpresionFolioEspecial.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO%>');
	</script>
<%}%>
</body>

