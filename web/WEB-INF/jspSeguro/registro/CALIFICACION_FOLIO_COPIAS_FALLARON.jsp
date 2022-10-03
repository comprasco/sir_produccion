<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.consulta.AWConsulta"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Solicitud"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="org.auriga.core.web.HelperException"%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%
	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));		
	
	
	Turno t = (Turno) session.getAttribute(WebKeys.TURNO);
    Solicitud sol = t.getSolicitud();
    
	//SE LLENA LA LISTA DE MATR�CULAS PARA LA REVISI�N DE CONFRONTACI�N
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
                  <td class="bgnsub"><span class="titulotbcentral">Datos Origen</span></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido" width="25%" align="right">N&uacute;mero de matr&iacute;cula </td>
                  <td class="contenido" width="25%"><input name="<%= gov.sir.core.negocio.modelo.constantes.CFolio.NUMERO_MATRICULA_INMOBILIARIA%>" type="text" class="camposformtext"></td>
                  <td class="contenido" width="20%" align="right">Orden de la Anotaci�n </td>
                  <td class="contenido" width="30%"><input name="<%= gov.sir.core.negocio.modelo.constantes.CAnotacion.ORDEN_ANOTACION%>" type="text" class="camposformtext"></td>
                </tr>
              </table>        
              
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub"><span class="titulotbcentral">Folios Destino</span></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>              
              <table width="100%">
                <tr>
                  <td>

				    	<% try {
				    		  //HELPER TABLA MATRICULAS
							  gov.sir.core.web.helpers.comun.TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
							  
		                      tablaHelper.setColCount(5);
		                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
	                          tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
		               		  tablaHelper.render(request, out);
		                    }
		                    catch(HelperException re){
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
                  <td width="140"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_copiar_anotacion.gif" width="150" height="21" border="0"></td>
                  <td>&nbsp;</td>
                </tr>
              </table>
              
            </form>              
              
    </td>
  </tr>              
    </table>