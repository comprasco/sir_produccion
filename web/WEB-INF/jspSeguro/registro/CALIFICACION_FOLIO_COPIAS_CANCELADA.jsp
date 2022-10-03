<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.consulta.AWConsulta"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Solicitud"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%
	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));		
	
	
	Turno t = (Turno) session.getAttribute(WebKeys.TURNO);
    Solicitud sol = t.getSolicitud();
    
	//SE LLENA LA LISTA DE MATRÍCULAS PARA LA REVISIÓN DE CONFRONTACIÓN
	List matriculas = sol.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	
	Iterator it = matriculas.iterator();	
	while(it.hasNext()){
		gov.sir.core.negocio.modelo.SolicitudFolio sf=(SolicitudFolio)it.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		idmatriculas.add(temp);
	}	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idmatriculas);			
%>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr> 
		<td>
            
            
            
            <form action="copiarAnotacion.do" method="post" name="COPIAANOTACION" id="COPIAANOTACION">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWConsulta.VER_FOLIO_CALIFICACION_FOLIO%>" value="<%= AWConsulta.VER_FOLIO_CALIFICACION_FOLIO %>">
            <input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" id="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" value="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_MATRICULA %>">            

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
                <tr> 
                  <td class="campositem" width="20%" align="right">1 </td>
                  <td class="campositem" width="40%">Folio </td>
                  <td class="contenido" width="40%">Orden de la Anotación </td>
                </tr>
              </table>        
                                      
              
              <br>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_copiar_anotacion.gif" width="150" height="21" border="0"></td>
                  <td width="140"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></td>                  
                  <td>&nbsp;</td>
                </tr>
              </table>
              
            </form>              
              
    </td>
  </tr>              
    </table>