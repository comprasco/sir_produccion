<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.helpers.registro.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.registro.AWFolio"%>
<%@page import="gov.sir.core.web.acciones.consulta.AWConsulta"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.web.acciones.comun.AWLocacion"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDepartamento" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CMunicipio" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda" %>
<%@page import="java.lang.reflect.Field"%>
<%@page import="org.auriga.util.FechaConFormato"%>

<%
    Folio folio = (Folio)session.getAttribute(AWConsulta.FOLIO);
    
    ZonaRegistral zonaRegistral = folio.getZonaRegistral();
    Circulo circulo = zonaRegistral.getCirculo();
    
    if (session.getAttribute(AWAdministracionForseti.CIRCULO_TRASLADO) != null) {
        Circulo circuloTraslado = (Circulo)session.getAttribute(AWAdministracionForseti.CIRCULO_TRASLADO);
        session.setAttribute(AWAdministracionForseti.ID_MATRICULA_TRASLADO, circuloTraslado.getIdCirculo() + "-" + (circuloTraslado.getLastNoMatricula() + 1));
    } else {
        session.setAttribute(AWAdministracionForseti.ID_MATRICULA_TRASLADO, "");
    }
    
    Vereda vereda = zonaRegistral.getVereda();
    TextHelper textHelper = new TextHelper();
    MostrarFechaHelper fechaHelper = new MostrarFechaHelper();

    List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);

    List listaCirculos = new ArrayList();
    for (Iterator iter = circulos.iterator(); iter.hasNext();) {
            Circulo c = (Circulo) iter.next();
            if (!c.getIdCirculo().equals(circulo.getIdCirculo())) {
                listaCirculos.add(new ElementoLista(c.getIdCirculo(), c.getNombre()));
            }
    }
    ListaElementoHelper circulosHelper = new ListaElementoHelper();
    circulosHelper.setTipos(listaCirculos);        
        
    if (session.getAttribute(CCirculo.ID_CIRCULO) != null && 
        session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS) == null) {
        Map map = (Map)application.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);
        List zonasRegistrales = (List)session.getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
        List listaDeptos = new ArrayList();
        List listaDptosSession = new ArrayList();
        Hashtable tempZonas = new Hashtable();
        for (Iterator iter = zonasRegistrales.iterator(); iter.hasNext();) {
            ZonaRegistral zona = (ZonaRegistral)iter.next();
            String idDpto = zona.getVereda().getIdDepartamento();
            tempZonas.put(idDpto,zona);
        }
        for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
            String key = (String)iter.next();
            Departamento dpto = (Departamento)map.get(key);
            String idDpto = dpto.getIdDepartamento();
            if (tempZonas.get(idDpto) != null) {
                String nombre = dpto.getNombre();
                listaDeptos.add(new ElementoLista(key, nombre));
                listaDptosSession.add(dpto);
            }
        }
        session.setAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS, listaDeptos);	
        session.setAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO, listaDptosSession);
    }
    
    ListaElementoHelper deptoHelper = new ListaElementoHelper();
    if (session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS) != null) {
        List listaDeptos = (List)session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS);
        deptoHelper.setTipos(listaDeptos);
    } else {
        deptoHelper.setTipos(new ArrayList());
    }

    ListaElementoHelper municipioHelper = new ListaElementoHelper();
    if(session.getAttribute(CDepartamento.ID_DEPARTAMENTO) != null) {
        String idDpto = (String)session.getAttribute(CDepartamento.ID_DEPARTAMENTO);
        Map map = (Map)application.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);
        Departamento dpto = (Departamento)map.get(idDpto);
        List municipios = dpto.getMunicipios();
        List listaMunicipios = new ArrayList();
        List zonasRegistrales = (List)session.getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
        Hashtable tempZonas = new Hashtable();
        for (Iterator iter = zonasRegistrales.iterator(); iter.hasNext();) {
            ZonaRegistral zona = (ZonaRegistral)iter.next();
            String idMuni = zona.getVereda().getIdMunicipio();
            tempZonas.put(idMuni,zona);
        }
        for (Iterator it = municipios.iterator(); it.hasNext();) {
            Municipio muni = (Municipio)it.next();
            if (tempZonas.get(muni.getIdMunicipio()) != null) {
                listaMunicipios.add(new ElementoLista(muni.getNombre() + "-" + muni.getIdMunicipio(), muni.getNombre()));
            }
        }
        municipioHelper.setTipos(listaMunicipios);
    } else {
        municipioHelper.setTipos(new ArrayList());
    }

    ListaElementoHelper veredaHelper = new ListaElementoHelper();
    if(session.getAttribute(CMunicipio.ID_MUNICIPIO) != null) {
        String idMunicipio = (String)session.getAttribute(CMunicipio.ID_MUNICIPIO);
        Map map = (Map)session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
        Municipio muni = (Municipio)map.get(idMunicipio);
        List veredas = muni.getVeredas();
        List listaVeredas = new ArrayList();
        List zonasRegistrales = (List)session.getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
        Hashtable tempZonas = new Hashtable();
        for (Iterator iter = zonasRegistrales.iterator(); iter.hasNext();) {
            ZonaRegistral zona = (ZonaRegistral)iter.next();
            String idVere = zona.getVereda().getIdVereda();
            tempZonas.put(idVere,zona);
        }
        for (Iterator it = veredas.iterator(); it.hasNext();) {
            Vereda vere = (Vereda)it.next();
            if (tempZonas.get(vere.getIdVereda()) != null) {
                listaVeredas.add(new ElementoLista(vere.getIdVereda(), vere.getNombre()));
            }
        }
        veredaHelper.setTipos(listaVeredas);
    } else {
        veredaHelper.setTipos(new ArrayList());
    }
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
    document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
    document.BUSCAR.submit();
}
function locacion(nombre,valor,dimensiones){
    document.getElementById('id_depto').value=valor+"_ID_DEPTO";
    document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
    document.getElementById('id_munic').value=valor+"_ID_MUNIC";
    document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
    document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
    document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
    popup=window.open(nombre,valor,dimensiones);
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
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
            <table border="0" cellpadding="0" cellspacing="0">
                <tr> 
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Traslado de Folios </td>
                    <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
                </tr>
            </table>
        </td>
        <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
        <td width="12">&nbsp;</td>
    </tr>
    <tr> 
        <td>&nbsp;</td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
        <td class="tdtablaanexa02">

            <form action="administracionForseti.do" method="POST" name="BUSCAR" id="BUSCAR">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="">
            
            <table border="0" cellpadding="0" cellspacing="0" width="100%">

                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr> 
                    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr> 
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">FOLIOS</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table>
                    </td>
                    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>
                <tr> 
                    <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">

                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr> 
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td class="bgnsub">Datos B&aacute;sicos - Folio Original </td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>
                        
                        <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td >N&uacute;mero de Matr&iacute;cula</td>
                                <td class="campositem"> <%=   (folio.getIdMatricula()==null)?"&nbsp;":folio.getIdMatricula()  %> </td>
                            </tr>
                        </table>			

                        <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Circulo Registral:</td>
                                <td class="campositem">  <%= (circulo==null)?"&nbsp;":circulo.getNombre()%>  </td>
                                <td>Depto:</td>
                                <td class="campositem"> <%= (vereda==null)?"&nbsp;":vereda.getMunicipio().getDepartamento().getNombre()%> </td>
                                <td>Municipio:</td>
                                <td class="campositem"> <%= (vereda==null)?"&nbsp;":vereda.getMunicipio().getNombre()%> </td>
                                <td>Vereda:</td>
                                <td class="campositem">  <%= (vereda==null)?"&nbsp;":vereda.getNombre() %>  </td>  
                            </tr>
                            <tr>
                                <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Fecha Apertura: </td>
                                <td class="campositem">
                                    <%= ((folio.getFechaApertura()==null)?"":FechaConFormato.formatear(folio.getFechaApertura())) %>
                                </td>
                                <td>Radicacion:</td>
                                <td class="campositem">
                                    <%  
                                        List anotaciones = folio.getAnotaciones();   
                                        Anotacion primeraAnotacion = null;
                                        Documento docPrimeraAnotacion = null;
                                        TipoDocumento tipoDocPrimeraAnotacion = null;
                                        if(!anotaciones.isEmpty()){
                                            primeraAnotacion = (Anotacion)anotaciones.get(0);
                                            docPrimeraAnotacion = primeraAnotacion.getDocumento();
                                            if(docPrimeraAnotacion != null){
                                                tipoDocPrimeraAnotacion = docPrimeraAnotacion.getTipoDocumento();
                                            }
                                        }
                                    %>
                                    <%= (primeraAnotacion==null)?"&nbsp;":primeraAnotacion.getNumRadicacion()%>
                                </td>
                                <td>Con:</td>
                                <td class="campositem">
                                    <%= (tipoDocPrimeraAnotacion==null)?"&nbsp;":tipoDocPrimeraAnotacion.getNombre()%> de: <%=(docPrimeraAnotacion==null)?"&nbsp;":""+docPrimeraAnotacion.getNumero()%>
                                </td>
                            <td>Cod Catastral : </td>
                            <td class="campositem">  <%= (folio.getCodCatastral()==null)?"&nbsp;":folio.getCodCatastral()   %>     </td>
                            </tr>
                        </table>		  

                        <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>ESTADO DEL FOLIO </td>
                                <td class="campositem"><strong>
                                    <%= folio.getEstado().getNombre()%>
                                </strong></td>
                            </tr>
                        </table>

                        <hr class="linehorizontal">

                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                            <tr> 
                                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td class="bgnsub">Datos B&aacute;sicos - Nuevo Folio </td>
                                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>
                        </table>

						<table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td >Resoluci&oacute;n</td>
                                <td>
                                    <%
                                        try {
											TextAreaHelper textAreaHelper = new TextAreaHelper();
											textAreaHelper.setCols("60");
 		                        			textAreaHelper.setRows("7");
											textAreaHelper.setNombre(AWAdministracionForseti.RESOLUCION_TRASLADO);
											textAreaHelper.setCssClase("camposformtext");
											textAreaHelper.setId(AWAdministracionForseti.RESOLUCION_TRASLADO);
											textAreaHelper.render(request, out);
                                        }catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                            </tr>
                        </table>			
                        
                        <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td >N&uacute;mero de Matr&iacute;cula</td>
                                <td>
                                    <%
                                        try {
                                            textHelper.setNombre(AWAdministracionForseti.ID_MATRICULA_TRASLADO);
                                            textHelper.setCssClase("camposformtext");
                                            textHelper.setId(AWAdministracionForseti.ID_MATRICULA_TRASLADO);
                                            textHelper.setProperties(request);
                                            textHelper.render(request,out);
                                        }catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                            </tr>
                        </table>			
                        
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td width="300">Seleccione el nuevo C&iacute;rculo Registral </td>
                                <td>&nbsp;</td>
                            </tr>

                            <tr>
                                <td>&nbsp;</td>
                                <td>C&iacute;rculo</td>
                                <td>
                                    <% 
                                        try {
                                            circulosHelper.setNombre(CCirculo.ID_CIRCULO);
                                            circulosHelper.setCssClase("camposformtext");
                                            circulosHelper.setId(CCirculo.ID_CIRCULO);
                                            circulosHelper.setFuncion("onChange=\"cambiarAccion('"+AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO+"')\"");
                                            circulosHelper.render(request,out);
                                        }
                                        catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Departamento</td>
                                <td>
                                    <% 
                                        try {
                                            deptoHelper.setNombre(CDepartamento.ID_DEPARTAMENTO);
                                            deptoHelper.setCssClase("camposformtext");
                                            deptoHelper.setId(CDepartamento.ID_DEPARTAMENTO);
                                            deptoHelper.setFuncion("onChange=\"cambiarAccion('"+AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO_DEPTO+"')\"");
                                            deptoHelper.render(request,out);
                                        }
                                        catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Municipio </td>
                                <td>
                                    <% 
                                        try {
                                            municipioHelper.setNombre(CMunicipio.ID_MUNICIPIO);
                                            municipioHelper.setCssClase("camposformtext");
                                            municipioHelper.setId(CMunicipio.ID_MUNICIPIO);
                                            municipioHelper.setFuncion("onChange=\"cambiarAccion('"+AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO_MUNICIPIO+"')\"");
                                            municipioHelper.render(request,out);
                                        }
                                        catch(HelperException re){
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Vereda</td>
                                <td>
                                    <% try {
                                        veredaHelper.setNombre(CVereda.ID_VEREDA);
                                        veredaHelper.setCssClase("camposformtext");
                                        veredaHelper.setId(CVereda.ID_VEREDA);
                                        veredaHelper.render(request,out);
                                    }
                                    catch(HelperException re){
                                        out.println("ERROR " + re.getMessage());
                                    }
                                    %>
                                </td>
                            </tr>
                        </table>

                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                <td>Apertura </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    <table width="100%" class="camposform">
                                        <tr>
                                            <td>Fecha</td>
                                            <td class="campositem">
                                                <%
                                                    try {
                                                        fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
                                                        fechaHelper.setDate(new Date());
                                                        fechaHelper.render(request,out);
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
                        
                        <hr class="linehorizontal">
                        
                        <table width="100%" class="camposform">
                            <tr>
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                <td width="155">
                                    <input name="imageField" type="image" onClick="document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%=AWAdministracionForseti.TRASLADAR%>'"  src="<%=request.getContextPath()%>/jsp/images/btn_trasladar.gif" width="150" height="21" border="0">
                                </td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/admin.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Cancelar" width="150" height="21" border="0" id="Cancelar"></a>
                                </td>
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                        
                    </td>
                    <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                </tr>
                <tr> 
                    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                </tr>
            </table>
            </form>
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