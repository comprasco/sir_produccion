<!--
/**
 * JSP para realizar la solicitud en el proceso traslado de folio
 * @version 2.0, 18/03/2011
 * @author Julio Alcazar
 */
-->
<%@page import="gov.sir.forseti.dao.impl.jdogenie.JDOGenieZonaRegistralDAO"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWLiquidacionCorreccion"%>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants" %>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas" %>
<%@page import="gov.sir.core.web.helpers.registro.OficinaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR" %>

<%/**
 * @autor HGOMEZ 
 * @mantis 13407 
 * @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
 * @descripcion Se importa ValidacionesSIR para tener acceso a los circulos y 
 * departamentos asociados a los mismos.
 */%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.util.DepartamentosPorCirculoSingletonUtil"%>


<%
   TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
   List matriculasAsociadas= (List)session.getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
   TextAreaHelper textAreaHelper = new TextAreaHelper();
   String idCirculo = "";
   /**
 * @Autor: Ellery Robles
 * @Mantis: 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
 */ 
   String numeroFundamento = "";
   List tiposFundamento = new java.util.ArrayList();
   List fundamentosAsociados = (List)session.getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
		idCirculo = idCirculo + "-";
	}

List elementos = new java.util.ArrayList();
List elementos2 = new java.util.ArrayList();
Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
ElementoLista circulo_actual = new ElementoLista(circulo.getIdCirculo(), circulo.getNombre());
JDOGenieZonaRegistralDAO zr = new JDOGenieZonaRegistralDAO();

/**
* @autor HGOMEZ 
* @mantis 13407 
* @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
* @descripcion Se instancia DepartamentosPorCirculoSingletonUtil para obtener el listado
* de departamentos por circulo.
*/
List listaCirculoDepartamento = new Vector();
DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

int idCirculoInt = 0;
String nombreCirculoDepartamento = "";
String idCirculoString = "";

if(request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_SIR) == null ){
    List  circulosSIR = zr.getCirculosFechaProd();
    for (java.util.Iterator iter = circulosSIR.iterator(); iter.hasNext();) {
        Circulo circ = (Circulo) iter.next();
        idCirculoString = circ.getIdCirculo();
        if(departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)){
            idCirculoInt = Integer.parseInt(idCirculoString);
            nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
            if(nombreCirculoDepartamento != ""){
                elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
            }
        }
    }
    elementos.remove(circulo_actual);
    request.getSession().setAttribute(WebKeys.LISTADO_CIRCULOS_SIR, elementos);
}else{
    elementos = (List) request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_SIR);
    elementos.remove(circulo_actual);
}

if(request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_NOSIR) == null ){

    List circulosNOSIR = zr.getCirculos();
    for (java.util.Iterator iter = circulosNOSIR.iterator(); iter.hasNext();) {
        Circulo circ = (Circulo) iter.next();
        elementos2.add(new ElementoLista(circ.getIdCirculo(), circ.getNombre()));
    }
    elementos2.remove(circulo_actual);
    Iterator iter = elementos.iterator();
    while(iter.hasNext()){
       ElementoLista circulo_sir = (ElementoLista) iter.next();
       elementos2.remove(circulo_sir);
    }
    request.getSession().setAttribute(WebKeys.LISTADO_CIRCULOS_NOSIR, elementos2);
}else{
    elementos2 = (List) request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_NOSIR);
    elementos2.remove(circulo_actual);
}

TrasladoSIR traslado = new TrasladoSIR();
List folios = traslado.FoliosBloqueadosTraslado(circulo.getIdCirculo());
   /**
 * @Autor: Ellery Robles
 * @Mantis: 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
 */ 
    if(request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR) == null) {
        List  tiposFundamentoSIR = traslado.TiposFundamento();
        for (java.util.Iterator iter = tiposFundamentoSIR.iterator(); iter.hasNext();) {
            String[] tf = iter.next().toString().split(";");
            tiposFundamento.add(new ElementoLista(tf[0], tf[1]));
        }
    //    elementos.remove(circulo_actual);
        request.getSession().setAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR, tiposFundamento);
    } else {
        tiposFundamento = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR);
    //    elementos.remove(circulo_actual);
    }
   %>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
   <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" >

function agregarMatricula(text) {
    document.TRASLADO.ACCION.value = text;
    document.TRASLADO.submit();
}

function agregarMatriculaRango(text) {
   document.TRASLADO.ACCION.value = text;
   document.TRASLADO.submit();
}

function eliminarMatriculas(text) {
    document.TRASLADO.ACCION.value = text;
    document.TRASLADO.submit();
}
/**
 * @Autor: Ellery Robles
 * @Mantis: 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
 */   
function agregarFundamento(text) {
   document.TRASLADO.ACCION.value = text;
   document.TRASLADO.submit();
}

function eliminarFundamento(text) {
    document.TRASLADO.ACCION.value = text;
    document.TRASLADO.submit();
}

function cambiarAccion(text) {
    document.TRASLADO.ACCION.value = text;
    document.TRASLADO.submit();
}

function cambiarTabla(text){
    if(text == 'CIRCULOS_EN_SIR'){
        document.getElementById(text).style.display = 'none';
        document.getElementById("CIRCULOS_FOLIO").style.display = '';

    }else{
        document.getElementById(text).style.display = 'none';
        document.getElementById("CIRCULOS_EN_SIR").style.display = '';
    }
}

function Confirmation(text){
   
        if (document.getElementById('CIRCULOS_NOSIR').value != "SIN_SELECCIONAR" || document.getElementById('CIRCULOS_SIR').value != "SIN_SELECCIONAR")
        {
            var datos = " ";
            var datos2 = "";
            if (document.getElementById('r2').checked == true){
                
                datos2 = document.TRASLADO.CIRCULOS_SIR.options[document.TRASLADO.CIRCULOS_SIR.selectedIndex].text;
            }else {
                
                datos2 = document.TRASLADO.CIRCULOS_NOSIR.options[document.TRASLADO.CIRCULOS_NOSIR.selectedIndex].text;
            }
            var answer = confirm(" Esta seguro de trasladar la matricula al círculo: " + datos2 + "\nCLICK en ACEPTAR si esta seguro de la operacíon, de lo contrario CLICK en CANCELAR" );

            if(answer == true)
            {
                document.TRASLADO.ACCION.value = text;                
            }
            else
            {
                document.TRASLADO.action = "";
                alert("Por Favor: Verifique de nuevo el círculo seleccionado.");
                return false;
            }
        }
        else
        {
           document.TRASLADO.ACCION.value = text;           
        }
}

function validarFecha(){
    if (document.TRASLADO.<%=CSolicitudRegistro.CALENDAR%>.value.length>0){
        var index=document.TRASLADO.<%=CSolicitudRegistro.CALENDAR%>.value.lastIndexOf('/')+1;
	if (index!=null){
            var fin=document.TRASLADO.<%=CSolicitudRegistro.CALENDAR%>.value.length;
            var texto=document.TRASLADO.<%=CSolicitudRegistro.CALENDAR%>.value.substring(index,fin);
            if (texto.length!=4){
                alert('Fecha incorrecta');
                document.TRASLADO.<%=CSolicitudRegistro.CALENDAR%>.value='';
                document.TRASLADO.<%=CSolicitudRegistro.CALENDAR%>.focus();
	    }
	}
    }
}

function folio_confirmacion(val, text){
    document.TRASLADO.FOLIO_CONFIRMACION.value = val;    
    document.TRASLADO.ACCION.value = text;
    document.TRASLADO.submit();
}

function verDetalle(nombre,valor,matricula){
    popup=window.open(nombre+'&ID_MATRICULA='+matricula,valor);
    popup.focus();
}
function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}
</script>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Traslado</td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Solicitud de Traslado</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
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
            <form action="administracionForseti.do" method="post"  name="TRASLADO" id="TRASLADO">
			    <input type="hidden" name="ACCION" value="CANCELAR_SOLICITUD">
                            <input type="hidden" id="FOLIO_CONFIRMACION" name="FOLIO_CONFIRMACION" />
<!--
			<input type="hidden" name="VER_ANTIGUO_SISTEMA"	id="VER_ANTIGUO_SISTEMA">
-->
              <br />
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;cula Inmobiliaria de la Propiedad</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>


              <%if (matriculasAsociadas!=null && !matriculasAsociadas.isEmpty()){%>

              <table width="100%" class="camposform">
              <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                <td>Folios de esta solicitud</td>
              </tr>
              <tr>
                <td width="20">&nbsp;
                </td>
                <td>
                  <% try {
                      tablaHelper.setColCount(5);
                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_TRASLADO);
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
            <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
              <tr>
                <td width="20">
                  <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/>
                </td>
                <td>Eliminar Seleccionadas</td>
                <td>
                  <a href="javascript:eliminarMatriculas('ELIMINAR')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a>
                </td>
              </tr>
            </table>
            <P>&nbsp;</P>
            <%}%>
            <table width="100%" class="camposform">
			 <tr>
			 </tr>
			 <tr>
				 
				 <!-- cosas nuevas que se metieron por el cambio a rango de matriculas se modifica el boton agregarmatricula -->
		           <tr>
						<td width="20">
							<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
						<td width="60%">N&uacute;mero de Matr&iacute;cula</td>

						<td><input name="<%=CFolio.ID_MATRICULA%>" id="<%=CFolio.ID_MATRICULA%>" type="text" value="<%=idCirculo%>"  onFocus="campoactual('<%=CFolio.ID_MATRICULA%>');" class="camposformtext">
					    <img id="<%=CFolio.ID_MATRICULA%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
				        </td>
	                    <td align="right">
	                     <table border="0" cellpadding="0" cellspacing="2" class="camposform">
			 				<tr>
			 				<td width="20"><img src=" <%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			 					<td>Agregar Matr&iacute;cula</td>
			 					<td align="right">
				 				<a href="javascript:agregarMatricula( 'AGREGAR' );" name="imageField">
					 			<img alt="[add matricula]" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
				 			</a>
				 	
				 		<!--
				 		<input name="" type="image" src=""  onClick="">
				 		-->
			 		</td>
			 		</tr>
	                      	</table>
	                    </td>
                	</tr>
				
				 <!--hasta aqui lo que se metio nuevo -->
				 
				 
				</tr>
			</table>

			
			  <!-- Opcion de asociar un rango de matriculas -->
			  
			  <!-- hasta aca se ha metido de nuevo de acuerdo al ticket se cambia el boton agregar matricula-->
		
              <br>
              
             <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Datos de la Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
                <!--             
                /**
                 * @Autor: Ellery Robles
                 * @Mantis: 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                 */ 
                -->  
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Fundamento</td>
                </tr>
              </table>
              
              <% if (fundamentosAsociados != null && !fundamentosAsociados.isEmpty()) { %>
                    <table width="100%" border="0" class="camposform">
                        <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                            <td colspan="4">Fundamentos de esta solicitud</td>
                        </tr>
                        
                        <tr>
                            <td width="20">&nbsp;</td>
                            <td width="5px">&nbsp;</td>
                            <td class="titulotbcentral">Tipo</td>
                            <td class="titulotbcentral">Número</td>
                            <td class="titulotbcentral">Fecha</td>
                        </tr>
                        <%  
                            String f = null;
                            String[] s = null;
                            Iterator item;
                            item = fundamentosAsociados.iterator();
                            while (item.hasNext()) {
                                f = item.next().toString();
                                s = f.split(";");
                        %>
                        <tr>
                            <td width="20px">&nbsp;</td>
                            <td width="5px" class="campositem">
                                <input type="checkbox" id="<%= f %>" name="<%= WebKeys.ELIMINAR_FUNDAMENTOS_CHECKBOX %>" value="<%= f %>"/>
                            </td>
                            <td class="campositem"><%= s[1] %></td>
                            <td class="campositem"><%= s[2] %></td>
                            <td class="campositem"><%= s[3] %></td>
                        </tr>
                        <% } %>
                    </table>
                    
                    <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                        <tr>
                            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>
                            <td>Eliminar Fundamento</td>
                            <td><a href="javascript:eliminarFundamento('ELIMINAR_FUNDAMENTO')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a></td>
                        </tr>
                    </table>
                    <P>&nbsp;</P>
            <% } %>
                  
              <table width="100%" border="0" class="camposform">
                  <tbody>
                      <tr>
                          <td width="20" height="18">&nbsp;</td>
                          <td class="titulotbcentral">Tipo</td>
                          <td>
                              <% try {
                                    ListaElementoHelper tiposFundamentoHelper = new ListaElementoHelper();
                                    tiposFundamentoHelper.setOrdenar(false);
                                    tiposFundamentoHelper.setNombre(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR);
                                    tiposFundamentoHelper.setId(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR);
                                    tiposFundamentoHelper.setCssClase("camposformtext");
                                    tiposFundamentoHelper.setTipos(tiposFundamento);
                                    tiposFundamentoHelper.render(request, out);
                                } catch(HelperException re) {
                                    out.println("ERROR " + re.getMessage());
                                }
                              %>
                          </td>
                          <td class="titulotbcentral">N&uacute;mero</td>
                          <td>
                              <input name="<%=CFolio.NUMERO_FUNDAMENTO%>" id="<%=CFolio.NUMERO_FUNDAMENTO%>" type="text" value="<%=numeroFundamento%>" class="camposformtext">
                          </td>
                          <td class="titulotbcentral">Fecha</td>
                          <td>
                              <table border="0" cellpadding="0" cellspacing="0">
                                  <tbody>
                                      <tr>
                                          <td>
                                              <% try {
                                                        TextHelper textHelper = new TextHelper();
                                                        textHelper.setNombre(CSolicitudRegistro.CALENDAR);
                                                        textHelper.setCssClase("camposformtext");
                                                        textHelper.setId(CSolicitudRegistro.CALENDAR);
                                                        textHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR+"');\" "
                                                    + " onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR+"')\"   onBlur=\"javascript:validarFecha()\"" );
                                                        textHelper.setReadonly(false);
                                                        textHelper.render(request,out);
                                                     } catch(HelperException  re) {
                                                         out.println("ERROR " + re.getMessage());
                                                     }
                                              %>
                                          </td>
                                          <td align="left">
                                              <a href="javascript:NewCal('calendar','ddmmmyyyy',true,24)">
                                                  <img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" 
                                                       alt="Fecha" width="16" height="21" border="0" 
                                                       onClick="javascript:Valores('<%=request.getContextPath()%>')">
                                              </a>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </td>
                          <td align="right">
                              <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                                  <tbody>
                                      <td><img src=" <%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                      <td>Agregar Fundamento</td>
                                      <td align="right">
                                          <a href="javascript:agregarFundamento('AGREGAR_FUNDAMENTO');" name="imageField">
                                              <img alt="[add fundamento]" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
                                          </a>
                                      </td>
                                  </tbody>
                              </table>
                          </td>
                      </tr>
                  </tbody>
              </table>
              <br>

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Circulo</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Circulo Destino</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>
                  <td class="contenido">
                      <table width="100%">
                          <tr>
                              <td>
                                  <input type="hidden" name="ITEM" value="NINGUNO">
                                  <input type="radio" id="r1" onclick="cambiarTabla('CIRCULOS_EN_SIR')" name="CIRCULO_OFICINA" value="F" /> FOLIO &nbsp;&nbsp;&nbsp;
                                  <input type="radio" id="r2" onclick="cambiarTabla('CIRCULOS_FOLIO')" name="CIRCULO_OFICINA" value="S" checked /> SIR
                                  <table width="100%" id="CIRCULOS_EN_SIR" class="camposform">
                                      <tr>

                                      </tr>
                                      <tr>
                                      <tr>
                                          <td class="titulotbcentral" width="20%">C&iacute;rculo</td>
                                          <td class="titulotbcentral" width="60%">&nbsp;</td>
                                      </tr>
                                      <tr>
                                          <td align="rigth">
					<% try {
                                            ListaElementoHelper circuloHelper = new ListaElementoHelper();
                                            circuloHelper.setOrdenar(false);
                                            circuloHelper.setNombre(AWAdministracionForseti.CIRCULOS_SIR);
                                            circuloHelper.setId(AWAdministracionForseti.CIRCULOS_SIR);
                                            circuloHelper.setCssClase("camposformtext");
                                            circuloHelper.setTipos(elementos);
                                            circuloHelper.render(request, out);
 		                           }catch(HelperException re){
                                               out.println("ERROR " + re.getMessage());
					   }
					%>
                                          </td>
                                      </tr>
                                  </table>

                                  <table width="100%" id="CIRCULOS_FOLIO" class="camposform" style="display: none">
                                      <tr>

                                      </tr>
                                      <tr>
                                      <tr>
                                          <td class="titulotbcentral" width="20%">C&iacute;rculo</td>
                                          <td class="titulotbcentral" width="60%">&nbsp;</td>
                                      </tr>
                                      <tr>
                                          <td align="rigth">
					<% try {
                                            ListaElementoHelper circuloHelper = new ListaElementoHelper();
                                            circuloHelper.setOrdenar(false);
                                            circuloHelper.setNombre(AWAdministracionForseti.CIRCULOS_NOSIR);
                                            circuloHelper.setId(AWAdministracionForseti.CIRCULOS_NOSIR);
                                            circuloHelper.setCssClase("camposformtext");
                                            circuloHelper.setTipos(elementos2);
                                            circuloHelper.render(request, out);
 		                           }catch(HelperException re){
                                               out.println("ERROR " + re.getMessage());
					   }
					%>
                                          </td>
                                      </tr>
                                  </table>
                              </td>
                          </tr>
                          <tr>
                              <td align="right">
                              </td>
                          </tr>
                      </table>
                  </td>
                </tr>
              </table>
              <br>
              

<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +   -->
              <hr class="linehorizontal" />
              <!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + +  -->



              
	      <hr class="linehorizontal">
          


              



              <table width="100%" class="camposform">
                  <tr>
                      <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                      <td width="155">
                          <input name="imageField" type="image" onClick="Confirmation('<%=AWAdministracionForseti.TRASLADAR%>')"  src="<%=request.getContextPath()%>/jsp/images/btn_trasladar.gif" width="150" height="21" border="0">
                      </td>
                      <td>
                          <input name="Cancelar" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.TERMINA%>')" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="150" height="21" border="0" id="Cancelar">
                      </td>
                      <td>&nbsp;</td>
                  </tr>
             </table>
             <%
             if(!folios.isEmpty()){
                 Iterator iter = folios.iterator();
                 int i = 0;
             %>
             <br>
             <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Folios Traslados</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
             <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Confirmacion</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>
                  <td class="contenido">
                        <div style="overflow:scroll;width:100%;height:350px;">
                           <table width="100%">
                                <tr>
                                   <td class="titulotbcentral">&nbsp;No.</td>
                                   <td class="titulotbcentral">&nbsp;FOLIO</td>
                                   <td class="titulotbcentral">&nbsp;VER</td>
                                   <td align="center" class="titulotbcentral">&nbsp;CONFIRMAR</td>
                                </tr>
             <%
                 while (iter.hasNext()){
                  String folio = (String)iter.next();
                  i++;
             %>
              

                                <tr>
                                   <td class="campositem"><%=i%></td>
                                   <td class="campositem"><%=folio%> </td>
                                   <td width="40"><img name="btn_mini_verdetalles.gif" src="/SNR/jsp/images/btn_mini_verdetalles.gif" width="35" height="13"  style="cursor:'hand'"  border="0" onClick="verDetalle('consultar.folio.traslado.do?ACCION=CONSULTAR_FOLIO&VER_FOLIO_TRASLADO=VER_FOLIO_TRASLADO','Folio','<%=folio%>')"></td>
                                   <td align="center" width="40"><img align="middle" name="btn_mini_activar.gif" src="/SNR/jsp/images/btn_mini_activar.gif" width="35" height="13"  style="cursor:'hand'"  border="0" onClick="folio_confirmacion('<%=folio%>','FOLIO_CONFIRMACION')"></td>
                                </tr>
              
             <%
                 }
             
             %>
                             </table>
                          </div>
                         </td>
                     </tr>
               </table>
             <%

             }
             %>

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
