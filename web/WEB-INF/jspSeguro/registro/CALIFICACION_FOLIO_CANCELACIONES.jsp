<%
/**
*@Autor: Edgar Lora
*@Mantis: 11599
*@Requerimiento: 085_151
*/%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gov.sir.core.negocio.modelo.util.ComparadorCanceladoras"%>
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.negocio.modelo.Anotacion"%>
<%@page import="gov.sir.core.negocio.modelo.Cancelacion"%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%
	/**
        *@Autor: Edgar Lora
        *@Mantis: 11599
        *@Requerimiento: 085_151
        */
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
	List anotacionesCanceladoras = (List)session.getAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION);
	List cancelacionAnotacion = new ArrayList();
        for(int i = 0; i < anotacionesCanceladoras.size(); i = i + 1){
            Anotacion a = (Anotacion)anotacionesCanceladoras.get(i);
            cancelacionAnotacion.add(a);
        }
        Collections.sort(cancelacionAnotacion, new ComparadorCanceladoras());
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
              <td class="bgnsub">Cancelaciones del Folio</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
		
          <% 
			if(cancelacionAnotacion!=null&&cancelacionAnotacion.size()>0){     
		  %>	
				<table width="100%" class="camposform">
				<tr>
				<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				<td width="50%">Canceladora</td>
				<td width="50%">Cancelada</td>
				</tr>
				<%			
				Iterator ia = cancelacionAnotacion.iterator();	
				while(ia.hasNext()){
				    Anotacion anotacion = (Anotacion)ia.next();	
					/**
                                        *@Autor: Edgar Lora
                                        *@Mantis: 11599
                                        *@Requerimiento: 085_151
                                        */
					List listaCancelaciones = anotacion.getAnotacionesCancelacions();
                                        List cancelaciones = new ArrayList();
                                        for(int i = 0; i < listaCancelaciones.size(); i = i + 1){
                                            Cancelacion c = (Cancelacion)listaCancelaciones.get(i);
                                            cancelaciones.add(c);
                                        }
                                        Collections.sort(cancelaciones, new ComparadorCanceladoras());
					Iterator ic = cancelaciones.iterator();
					while(ic.hasNext()){
						Cancelacion cancelacion = (Cancelacion)ic.next();				
						Anotacion cancelada = cancelacion.getCancelada();
                                                String orden = validacionesSIR.getAnotacionNtcnOrden(cancelada.getIdAnotacion(), cancelada.getIdMatricula());
			
				%>	
					<tr>
					<td width="20">&nbsp;</td>
					<td width="50%" class="campositem"><%=(anotacion.getOrden()!=null)?anotacion.getOrden():"&nbsp;"%></td>
					<td width="50%" class="campositem"><%=(orden!=null)?orden:"&nbsp;"%></td>
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
				<td>El folio No tiene Cancelaciones</td>
				</tr>
				</table>
			<%	
			}
			%>         
	
          </td>
        </tr>
 

      </table>
