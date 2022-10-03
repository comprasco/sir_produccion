<%@page import="gov.sir.core.negocio.modelo.Departamento"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWReportes"%>
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


<%
   String RUTA_DESTINO_ARCHIVO = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO);
   String SO = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.SO);

    HashMap matriculasAsociadas = (HashMap) session.getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
   
    List elementos = new java.util.ArrayList();
    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
    ElementoLista circulo_actual = new ElementoLista(circulo.getIdCirculo(), circulo.getNombre());
    JDOGenieZonaRegistralDAO zr = new JDOGenieZonaRegistralDAO();
    
    if (request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_SIR) == null) {

        List circulosSIR = zr.getCirculosFechaProd();


        for (java.util.Iterator iter = circulosSIR.iterator(); iter.hasNext();) {
            Circulo circ = (Circulo) iter.next();
            elementos.add(new ElementoLista(circ.getIdCirculo(), circ.getNombre()));
        }
        elementos.remove(circulo_actual);
        request.getSession().setAttribute(WebKeys.LISTADO_CIRCULOS_SIR, elementos);
    } else {
        elementos = (List) request.getSession().getAttribute(WebKeys.LISTADO_CIRCULOS_SIR);
        elementos.remove(circulo_actual);
    }
    if(session.getAttribute(AWAdministracionForseti.ARCHIVO_ORIGEN)!=null)
    {
        try
        {
            java.io.File archivo = (java.io.File)session.getAttribute(AWAdministracionForseti.ARCHIVO_ORIGEN);
            java.io.FileInputStream input = new java.io.FileInputStream(archivo);
            
            int longitud = input.available();
            byte[] datos = new byte[longitud];
            input.read(datos);
            input.close();
            
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition","attachment;filename="+archivo.getName()); 

            ServletOutputStream ouputStream = response.getOutputStream();
            ouputStream.write(datos);
            ouputStream.flush();
        }
        catch(Exception e)
        { 
            e.printStackTrace();
        } 
        
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

    function cambiarAccion(text) {
        document.TRASLADO.ACCION.value = text;
        document.TRASLADO.submit();
    }

    function cambiarTabla(text){
        if(text == 'CIRCULOS_EN_SIR'){
            document.getElementById(text).style.display = 'none';
            document.getElementById("CIRCULOS_FOLIO").style.display = '';
            document.getElementById("r1").checked = true;
        }else{
            document.getElementById(text).style.display = 'none';
            document.getElementById("CIRCULOS_EN_SIR").style.display = '';
            document.getElementById("r2").checked = true; 
        }
    }
    function cambiarAccionAndSend(text,formulario) {
        var form = document.getElementById(formulario);
        form.<%= WebKeys.ACCION%>.value = text;
        form.submit();
    }
    function Confirmation(text){
   
        if (document.getElementById('CIRCULOS_NOSIR').value != "SIN_SELECCIONAR" || document.getElementById('CIRCULOS_SIR').value != "SIN_SELECCIONAR")
        {
            var datos = " ";
            var datos2 = "";
            if (document.getElementById('r2').checked == true){
                
                datos2 = document.TRASLADO_DESTINO.CIRCULOS_SIR.options[document.TRASLADO_DESTINO.CIRCULOS_SIR.selectedIndex].text;
            }else {
                
                datos2 = document.TRASLADO_DESTINO.CIRCULOS_NOSIR.options[document.TRASLADO_DESTINO.CIRCULOS_NOSIR.selectedIndex].text;
            }
            var answer = confirm(" Esta seguro de trasladar la matricula al círculo: " + datos2 + "\nCLICK en ACEPTAR si esta seguro de la operacíon, de lo contrario CLICK en CANCELAR" );

            if(answer == true)
            {
                document.TRASLADO_DESTINO.ACCION.value = text;                
            }
            else
            {
                document.TRASLADO_DESTINO.action = "";
                alert("Por Favor: Verifique de nuevo el círculo seleccionado.");
                return false;
            }
        }
        else
        {
            document.TRASLADO_DESTINO.ACCION.value = text;           
        }
    }

    function validarFecha(){
        if (document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value.length>0){
            var index=document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value.lastIndexOf('/')+1;
            if (index!=null){
                var fin=document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value.length;
                var texto=document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value.substring(index,fin);
                if (texto.length!=4){
                    alert('Fecha incorrecta');
                    document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.value='';
                    document.TRASLADO_DESTINO.<%=CSolicitudRegistro.CALENDAR%>.focus();
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
    function oficinas(nombre,valor,dimensiones){
        var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
        var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
        var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
        document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
        document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
        document.getElementById('numero_oficina').value=valor+"_NUM";
        document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
        popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
        popup.focus();
    }

    function locacion(nombre,valor,dimensiones){
        document.getElementById('id_depto').value=valor+"_ID_DEPTO";
        document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
        document.getElementById('id_munic').value=valor+"_ID_MUNIC";
        document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
        document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
        document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
        popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.MOSTRAR_VEREDA%>',valor,dimensiones);
        popup.focus();
    }

    function locacion1(nombre,valor,dimensiones){
        document.getElementById('id_depto').value=valor+"_ID_DEPTO";
        document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
        document.getElementById('id_munic').value=valor+"_ID_MUNIC";
        document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
        document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
        document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
        popup=window.open(nombre,valor,dimensiones);
        popup.focus();
    }
    function checkAll(check)
    {
        if(!document.forms['TRASLADO_DESTINO'])
		return;
	var objCheckBoxes = document.forms['TRASLADO_DESTINO'].elements['FOLIO_CONFIRMACION'];
	if(!objCheckBoxes)
		return;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = check.checked;
	else
        for(var i = 0; i < countCheckBoxes; i++){
	    objCheckBoxes[i].checked = check.checked;
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
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Traslado Masivo</td>
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
                <tr>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Archivo Traslado Folio</td>
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
                        <form action="administracionForseti.do" method="post"  name="TRASLADO" id="TRASLADO" >
                            <input type="hidden" name="ACCION" value="CANCELAR_SOLICITUD">
							
                             <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Circulo Registral</td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td align="left" width="150"><div align="left" >C&iacute;rculo Registral:</div></td>
                                    <td width="200">
                                        <% try {
                                                ListaElementoHelper circuloHelper = new ListaElementoHelper();
                                                circuloHelper.setOrdenar(false);
                                                circuloHelper.setNombre(AWAdministracionForseti.CIRCULOS_SIR);
                                                circuloHelper.setId(AWAdministracionForseti.CIRCULOS_SIR);
                                                circuloHelper.setCssClase("camposformtext");
                                                circuloHelper.setTipos(elementos);
                                                circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('" + AWAdministracionForseti.CONSULTAR_ARCHIVOS + "','TRASLADO')\"");
                                                circuloHelper.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                        </form>
                     <a href="javascript:cambiarAccionAndSend('<%=AWAdministracionForseti.TERMINA%>','TRASLADO')"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="150" height="21" border="0" ></a>
                    </td>
                    <td width="11" background="/SNR/jsp/images/tabla_central_bgn008.gif">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                     <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td class="tdtablacentral">
                        <%if (matriculasAsociadas != null && !matriculasAsociadas.isEmpty()) {
                                    Iterator iter = matriculasAsociadas.keySet().iterator();
                                    int i = 0;
                            %>
                   
                            <table width="100%" class="camposform">
                                <tr>
                                    <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                                    <td>Folios de esta solicitud</td>
                                </tr>
                                <tr>
                                    <td width="20">&nbsp;
                                    </td>
                                    <td>
                                    <div style="overflow:scroll;width:100%;height:350px;">
                                            <table width="100%">
                                                <tr>
                                                    <td class="titulotbcentral">&nbsp;No.</td>
                                                    <td class="titulotbcentral">&nbsp;FOLIO</td>
                                                    <td class="titulotbcentral">&nbsp;FECHA DE CREACION</td>
                                                    <td class="titulotbcentral">&nbsp;DESCARGAR</td>
                                                </tr>
                                                <%
                                                    while (iter.hasNext()) {
                                                        String folio = (String)iter.next();
                                                        String fecha = (String)matriculasAsociadas.get(folio);
                                                        i++;
                                                %>
                                                

                                                <tr>
                                                    <td class="campositem">
                                                        <%=i%>
                                                    </td>
                                                    <td class="campositem"><%=folio%> </td>
                                                    <td class="campositem"><%=fecha%> </td>
                                                    <td width="40"><a href="<%=request.getContextPath()%>/jsp/descargar.jsp?nArchivo=<%= folio %>"><img name="btn_mini_verdetalles.gif" src="/SNR/jsp/images/btn_mini_guardar.gif" width="35" height="13"  style="cursor:'hand'"  border="0"></a></td>
                                                </tr>

                                                <%
                                                    }

                                                %>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <%}%>
                    </td>
                     <td width="11" background="/SNR/jsp/images/tabla_central_bgn008.gif">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
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
