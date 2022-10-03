<%@page import="gov.sir.core.negocio.modelo.Solicitud"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.negocio.modelo.CopiaAnotacion"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCopiaAnotacion"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="java.util.*;"%>

<% 	
	Turno t = (Turno) session.getAttribute(WebKeys.TURNO);
    Solicitud sol = t.getSolicitud();
    
	gov.sir.core.negocio.modelo.CopiaAnotacion copiaAnotacion = (CopiaAnotacion)session.getAttribute(WebKeys.COPIA_ANOTACION);
	List foliosACopiar = copiaAnotacion.getFoliosACopiar(); 
	
	String extra = "";
	if(session.getAttribute(AWCopiaAnotacion.NUM_COPIAS_CANCELACION) != null && session.getAttribute(gov.sir.core.web.acciones.registro.AWCopiaAnotacion.LISTA_COPIA_ANOTACIONES_CANCELADAS) != null){
		int toGo = ((List)session.getAttribute(gov.sir.core.web.acciones.registro.AWCopiaAnotacion.LISTA_COPIA_ANOTACIONES_CANCELADAS)).size();
		Integer integer = new Integer((String)session.getAttribute(AWCopiaAnotacion.NUM_COPIAS_CANCELACION));
		int tam = integer.intValue();
		extra  = "(" + String.valueOf(tam-toGo) + "/" + String.valueOf(tam) + ")";
	}
   %>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
       document.COPIAANOTACION.ACCION.value = text;
       document.COPIAANOTACION.submit();
       }
</script>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">COPIA ANOTACIÓN - SELECCIONAR ANOTACIONES A CANCELAR <%=extra%></td> 
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">INGRESE LA INFORMACIÓN DE LAS ANOTACIONES A CANCELAR</td>
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


            <form action="copiaAnotacion.do" method="post" name="COPIAANOTACION" id="COPIAANOTACION">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION%>" value="<%= gov.sir.core.web.acciones.registro.AWCopiaAnotacion.SELECCIONAR_CANCELADA%>">

              <br>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub"><span class="titulotbcentral">Ingrese las anotaciones a cancelar en cada folio.</span></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>


              <table width="100%" class="camposform">
              

			  <%
			  	int a=1;
			  	Iterator it = foliosACopiar.iterator();
				String labelMatricula = null;			  	
			  	while (it.hasNext()){
				  	Folio folio = (Folio)it.next();
				  	
					if(folio.isDefinitivo()){
						labelMatricula= folio.getIdMatricula();			
					}else{
						//labelMatricula= folio.getNombreLote();
						labelMatricula= folio.getIdMatricula();
					}				  	
			  %>
		  

                <tr> 
                  <td class="campositem" width="20%" align="right"><%=a%></td>
                  <td class="campositem" width="40%"><%=labelMatricula!=null?labelMatricula:"&nbsp;"%></td>
                  <%
                  	String temp = (String)session.getAttribute(gov.sir.core.negocio.modelo.constantes.CAnotacion.ORDEN_ANOTACION + folio.getIdMatricula());
                  %>   	                  
                  <td class="contenido" width="40%"><input name="<%= gov.sir.core.negocio.modelo.constantes.CAnotacion.ORDEN_ANOTACION + folio.getIdMatricula()%>" type="text" class="camposformtext" value="<%=temp!=null?temp:""%>"></td>
                </tr>

              <%
              		a++;
              	}
              %>    
                          
              </table>        
                                      
              
              
              <br>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_copiar_anotacion.gif" width="150" height="21" border="0"></td>
                  <td width="150"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWCopiaAnotacion.REGRESAR%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="150" height="21" border="0" id="Folio"></a></td>                  
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