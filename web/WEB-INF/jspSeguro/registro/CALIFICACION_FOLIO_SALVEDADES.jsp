<%
/**
*@Autor: Edgar Lora
*@Mantis: 11599
*@Requerimiento: 085_151
*/%>
<%@page import="gov.sir.core.negocio.modelo.util.ComparadorAnotaciones"%>
<%@page import="java.util.Collections"%>
<%@page import="gov.sir.core.negocio.modelo.util.ComparadorSalvedad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.negocio.modelo.SalvedadFolio"%>
<%@page import="gov.sir.core.negocio.modelo.SalvedadAnotacion"%>
<%@page import="gov.sir.core.negocio.modelo.Anotacion"%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%

		Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
		
		if(folio==null){
			folio = new Folio();
		}
		
		List salvAnotacion = (List)session.getAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES);
		
%>

<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->


        <tr>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Salvedades del Folio</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
          <%
			if(folio.getSalvedades()!=null&&folio.getSalvedades().size()>0){     
		  %>	
				<table width="100%" class="camposform">
				<tr>
				<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td width="15%">Usuario</td>
				<td width="85%">Salvedad</td>
				</tr>
			<%
                        /**
                        *@Autor: Edgar Lora
                        *@Mantis: 11599
                        *@Requerimiento: 085_151
                        */
                        ArrayList salvedades  = new ArrayList();
                        for(int i = 0; i < folio.getSalvedades().size(); i = i + 1){
                            salvedades.add(folio.getSalvedades().get(i));
                        }
                        Collections.sort(salvedades, new ComparadorSalvedad());
			Iterator is = salvedades.iterator();
			while(is.hasNext()){
				SalvedadFolio salvedad = (SalvedadFolio)is.next();
				String usr = null;
				if(salvedad.getUsuarioCreacion()!=null&&salvedad.getUsuarioCreacion().getUsername()!=null){
					usr = salvedad.getUsuarioCreacion().getUsername();					
				}
				if(usr == null && salvedad.getUsuarioCreacionTMP()!=null&&salvedad.getUsuarioCreacionTMP().getUsername()!=null){				
					usr = salvedad.getUsuarioCreacionTMP().getUsername();					
				}				
			%>	
				<tr>
				<td width="20">&nbsp;</td>
				<td width="15%" class="campositem"><%=(usr!=null)?usr:"&nbsp;"%></td>				
				<td width="85%" class="campositem"><%=(salvedad.getDescripcion()!=null)?salvedad.getDescripcion():"&nbsp;"%></td>
				</tr>
			<%
			}
			%>
			</table>
		<%
		}else{
		%>
			<table width="100%" class="camposform">
			<tr>
			<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			<td width="40">&nbsp;</td>
			<td>El folio No tiene Salvedades</td>
			</tr>
			</table>
		<%	
		}
		%>
	
          </td>
        </tr>
        <tr>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
          <br>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Salvedades de las Anotaciones del Folio</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
          
          <% 
			if(salvAnotacion!=null&&salvAnotacion.size()>0){     
		  %>	
				<table width="100%" class="camposform">
				<tr>
				<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td width="10%">Anotaci&oacute;n</td>
				<td width="15%">Usuario</td>				
				<td width="75%">Salvedad</td>
				</tr>
				<%
                                /**
                                *@Autor: Edgar Lora
                                *@Mantis: 11599
                                *@Requerimiento: 085_151
                                */
                                ArrayList anotaciones = new ArrayList();
                                for(int i = 0; i < salvAnotacion.size(); i = i + 1){
                                    anotaciones.add(salvAnotacion.get(i));
                                }
                                Collections.sort(anotaciones, new ComparadorAnotaciones());
				Iterator ia = anotaciones.iterator();
				while(ia.hasNext()){
				    Anotacion anotacion = (Anotacion)ia.next();
                                        /**
                                        *@Autor: Edgar Lora
                                        *@Mantis: 11599
                                        *@Requerimiento: 085_151
                                        */
                                        ArrayList salvedades = new ArrayList();
                                        for(int i = 0; i < anotacion.getSalvedades().size(); i = i + 1){
                                            salvedades.add(anotacion.getSalvedades().get(i));
                                        }
					Iterator is = salvedades.iterator();
					while(is.hasNext()){
						SalvedadAnotacion salvedad = (SalvedadAnotacion)is.next();				
					String usr = null;
					if(salvedad.getUsuarioCreacion()!=null&&salvedad.getUsuarioCreacion().getUsername()!=null){
						usr = salvedad.getUsuarioCreacion().getUsername();					
					}
					if(usr == null && salvedad.getUsuarioCreacionTMP()!=null&&salvedad.getUsuarioCreacionTMP().getUsername()!=null){				
						usr = salvedad.getUsuarioCreacionTMP().getUsername();					
					}							
				%>	
					<tr>
					<td width="20">&nbsp;</td>
					<td width="10%" class="campositem"><%=(anotacion.getOrden()!=null)?anotacion.getOrden():"&nbsp;"%></td>					
					<td width="15%" class="campositem"><%=(usr!=null)?usr:"&nbsp;"%></td>
					<td width="75%" class="campositem"><%=(salvedad.getDescripcion()!=null)?salvedad.getDescripcion():"&nbsp;"%></td>					
					</tr>
				<%
					}
				}
				%>
				</table>
			<%
			}else{
			%>
				<table width="100%" class="camposform">
				<tr>
				<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td width="40">&nbsp;</td>
				<td>El folio No tiene Salvedades</td>
				</tr>
				</table>
			<%	
			}
			%>          

      
          </td>
        </tr>        

      </table>
