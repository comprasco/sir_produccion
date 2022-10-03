<%@page import="gov.sir.core.util.DepartamentosPorCirculoSingletonUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculoTraslado" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.CirculoTraslado" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import=" org.auriga.util.FechaConFormato" %>



<%
TextHelper textHelper = new TextHelper();

/*if(session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO_DESTINO)==null){
	List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
	List elementos = new ArrayList();
	for (Iterator iter = circulos.iterator(); iter.hasNext();) {
		Circulo circulo = (Circulo) iter.next();
		elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getNombre()+"-"+circulo.getIdCirculo()));
		}
	session.setAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO_DESTINO, elementos);
	}
	

List circulos = (List)session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO_DESTINO);
ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(circulos);*/

/**
* AHERRENO 20/04/2013
* Requerimiento 064_453_Duplicidad_Nombre_Circulo 
* descripcion Se instancia DepartamentosPorCirculoSingletonUtil para obtener el listado
* de departamentos por circulo.
*/
        List listaCirculoDepartamento = new Vector();
        DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
        listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

        int idCirculoInt = 0;
        String nombreCirculoDepartamento = "";
        String idCirculoString = "";

if(session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO_DESTINO)==null){
	List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
	List elementos = new ArrayList();
	for (Iterator iter = circulos.iterator(); iter.hasNext();) {
		Circulo circulo = (Circulo) iter.next();
		/*elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getNombre()+"-"+circulo.getIdCirculo()));*/
                idCirculoString = circulo.getIdCirculo();
                if(departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)){
                    idCirculoInt = Integer.parseInt(idCirculoString);
                    nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                    if(nombreCirculoDepartamento != ""){
                        elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                    }
                }
                
		}
	session.setAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO_DESTINO, elementos);
	}
	

List circulos = (List)session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO_DESTINO);
ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(circulos);

List circulosInhab=(List)session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO_INHABILITADO);
if (circulosInhab==null){
	circulosInhab = new ArrayList();
}

List elemInhab=new ArrayList();
for (Iterator iter = circulosInhab.iterator(); iter.hasNext();) {
		CirculoTraslado circulo = (CirculoTraslado) iter.next();
		elemInhab.add(new ElementoLista(circulo.getCirculoOrigen().getIdCirculo(), circulo.getCirculoOrigen().getNombre()+"-"+circulo.getCirculoOrigen().getIdCirculo()));
		}

ListaElementoHelper circuloInhabHelper = new ListaElementoHelper();
circuloInhabHelper.setTipos(elemInhab);

%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
</script>

<script type="text/javascript">
function onChangeComboCirculos(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}
function insertarIdentificacion() {
	var circulo = document.BUSCAR.<%=CCirculoTraslado.CIRCULO_ORIGEN%>.value;
	if (circulo == 'SIN_SELECCIONAR'){
		document.getElementById('circDesde').innerHTML="";
		document.getElementById('circHasta').innerHTML="";	
	}else{
		document.getElementById('circDesde').innerHTML=circulo+'- ';
		document.getElementById('circHasta').innerHTML=circulo+'- ';	
	}
	
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Traslado Masivo de Folios para C&iacute;rculos Inhabilitados</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Traslado Masivo de Folios</td>
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
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
        <form action="administracionForseti.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionForseti.ADICIONA_CIRCULO_FESTIVO %>" value="<%= AWAdministracionForseti.ADICIONA_CIRCULO_FESTIVO  %>">
        
        
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Datos del C&iacute;rculo Destino</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&iacute;rculo Destino </td>
                    <td>
			  <% try {
                    circuloHelper.setNombre(CCirculoTraslado.CIRCULO_DESTINO);
                  	circuloHelper.setCssClase("camposformtext");
                  	circuloHelper.setId(CCirculoTraslado.CIRCULO_DESTINO);
                  	circuloHelper.setFuncion("onChange=\"onChangeComboCirculos('"+AWAdministracionForseti.CARGAR_CIRCULOS_INHABILITADOS+"')\"");
					circuloHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                                                                      
                </table>
        
        
            	
                
                <br/>
                
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Datos del C&iacute;rculo Inhabilitado</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
       
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&iacute;rculo Inhabilitado </td>
                    <td>
			  <% try {
                    circuloInhabHelper.setNombre(CCirculoTraslado.CIRCULO_ORIGEN);
                  	circuloInhabHelper.setCssClase("camposformtext");
                  	circuloInhabHelper.setId(CCirculoTraslado.CIRCULO_ORIGEN);
                  	circuloInhabHelper.setFuncion("onChange=\"insertarIdentificacion()\"");
					circuloInhabHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Desde:</td>
                    <td>
			  <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                            <td class="camposform"><a id="circDesde"></a></td>
                              <td>
                              <% try {
 		                        textHelper.setNombre(CCirculoTraslado.CIRCULO_ORIGEN_DESDE);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCirculoTraslado.CIRCULO_ORIGEN_DESDE);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                             </tr>
                        </table>
                    </td>
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Hasta:</td>
                    <td>
			  <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                            <td class="camposform"><a id="circHasta"></a></td>
                              <td>
                              <% try {
 		                        textHelper.setNombre(CCirculoTraslado.CIRCULO_ORIGEN_HASTA);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCirculoTraslado.CIRCULO_ORIGEN_HASTA);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
							</td>
                             </tr>
                        </table>
                    </td>
                  </tr>
                                                      
                </table>
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    </td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.TRASLADAR_FOLIOS%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_trasladar.gif" width="150" height="21" border="0">
                    </td>
                    <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
