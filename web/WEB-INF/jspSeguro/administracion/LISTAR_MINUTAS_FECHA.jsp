<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.negocio.modelo.Minuta" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.RepartoNotarial" %>
<%@page import="gov.sir.core.negocio.modelo.OficinaCategoria" %>
<%@page import="gov.sir.core.negocio.modelo.OficinaOrigen" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%
	TextHelper textHelper = new TextHelper(); 
	Hashtable turnos = (Hashtable)session.getAttribute(WebKeys.TABLA_MINUTAS);
	if (turnos == null){
		turnos = new Hashtable();
	}
	Enumeration enu = turnos.keys();
	
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
function oficinas(nombre,valor,dimensiones)
{
	popup=window.open(nombre+'?<%=CTurno.ID_TURNO%>='+valor,'Detalles',dimensiones);
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Listar Minutas Repartidas por Fecha</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listar Minutas Repartidas por Fecha</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Rango de Fechas</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
        <form action="consultasReparto.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWConsultasReparto.LISTAR_POR_FECHA%>">
           <table width="100%" class="camposform">
           		<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td allign="left" width="80">Fecha inicio:</td>
                    <td width="40">
                    <% try {
                    textHelper.setNombre(AWConsultasReparto.FECHA_INICIO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(AWConsultasReparto.FECHA_INICIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
				  </td>
				  <td allign="left">
				<a href="javascript:NewCal('<%=AWConsultasReparto.FECHA_INICIO%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
				  </td>
                  </tr>
					<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td allign="left" width="60">Fecha Fin:</td>
                    <td width="40">
                    <% try {
                    textHelper.setNombre(AWConsultasReparto.FECHA_FIN);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(AWConsultasReparto.FECHA_FIN);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
				  </td>
				  <td allign="left">
				<a href="javascript:NewCal('<%=AWConsultasReparto.FECHA_FIN%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
				  </td>
                  </tr>                  
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">&nbsp;</td>
                    <td class="campostip04">Cuando termine de consultar haga click en aceptar. </td>
                  </tr>
                  
                                   <td align="center">
                 	</td>
                  
                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWConsultasReparto.LISTAR_POR_FECHA%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0">
                    </td>
                   	 <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWConsultasReparto.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                  </tr>
                </table>
            </FORM>
                
                
                
                
            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
		<tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
		</table>
	    <br>
	    
	    <%if(enu.hasMoreElements()){%>
	    <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></span></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
               
               
               <tr>
                  <td>Turno</td>
                  <td>Fecha Minuta</td>
                  <td>Círculo Notarial</td>
                  <td>Reparto</td>
                  <td>Notaría Asignada</td>
                  <td width="50" align="center">Ver Detalles</td>
                </tr>
       <% 
                for(;enu.hasMoreElements();){
                	String dato = (String)enu.nextElement();
                	Minuta min = (Minuta) turnos.get(dato);
                	Date fecha = min.getFechaCreacion();
                	String cirNot = min.getCirculoNotarial().getNombre();
                    RepartoNotarial reparto = min.getRepartoNotarial();
                    OficinaCategoria notariaCategoria = min.getOficinaCategoriaAsignada();
                    String idCirculoTurno= null;
                    String year=null;
                    boolean esCirculo = false;
                    
                    //Determinar si la minuta pertenece al circulo del usuario.
                    Circulo circuloSesion = (Circulo) session.getAttribute(WebKeys.CIRCULO);
                    String idCirculoSesion = circuloSesion.getIdCirculo();

			        StringTokenizer st = new StringTokenizer(dato, "-");
			        year = st.nextToken();
			        if (st.hasMoreTokens()) {
				          idCirculoTurno = st.nextToken();
					}

                    if (idCirculoTurno != null)
                    {
                       if (idCirculoTurno.equals(idCirculoSesion))
                       {
                          esCirculo = true;
                       }
                    }
                    
                    String idReparto = null;
                    String idNotaria = null;
                	
                	if (reparto != null)
                	{
                	   idReparto = reparto.getIdRepartoNotarial();
                	}
                	
                	//Determinar nombre de la notaria
                	if (notariaCategoria != null)
                	{
                	   OficinaOrigen notaria = notariaCategoria.getOficinaOrigen();
                	   if (notaria != null)
                	   {
                	      idNotaria = notaria.getNombre();
                	   }
                	}
                %>   
                
                <%if (esCirculo)
                {%>         
                <tr>
                 <td class="campositem"><%= dato%></td>
                 
                   <td  width="90" class="campositem">                            <%
                            try {

                            MostrarFechaHelper fechaH = new MostrarFechaHelper();
                            fechaH.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
                            fechaH.setDate(fecha);
                            fechaH.render(request, out);

                            }
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}

                            %>
                   </td>      

                 <td class="campositem"><%= cirNot%></td>
                 <td class="campositem"><%=idReparto%></td> 
                 <td class="campositem"><%=idNotaria%></td>
                 <td align="center">
                  		<a href="javascript:oficinas('admin.detalles.minuta.view','<%=dato%>','scrollbars=yes,width=800,height=600,menubar=no')"><image src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a> 
                  	</td>
                </tr>
              <% }
                } 
                %> 
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
        <%}%>
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