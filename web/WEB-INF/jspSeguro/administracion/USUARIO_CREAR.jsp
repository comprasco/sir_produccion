<%@page import="java.util.List" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFenrir" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRol" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRoles" %>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>


<%
TextHelper textHelper = new TextHelper();

ListaElementoHelper rolesHelper = new ListaElementoHelper();
rolesHelper.setTipoMultiple(true); 
List roles = (List)application.getAttribute(WebKeys.LISTA_ROLES_SISTEMA);

List temp = new java.util.ArrayList();
java.util.Iterator itTemp = roles.iterator();
while(itTemp.hasNext()){
	ElementoLista rol = (ElementoLista)itTemp.next();
	
	if(!
		(
		rol.getId().equals(CRoles.SIR_ROL_DECISOR) ||
		rol.getId().equals(CRoles.SIR_ROL_IMPRESION)
		)
		){
			temp.add(rol);
	}

}

rolesHelper.setTipos(temp);



List rolesUsuario = (List)session.getAttribute(WebKeys.LISTA_ROLES);
if( null == rolesUsuario ) {
  rolesUsuario = new java.util.ArrayList();
}
boolean isAdministradorNacional = false;


java.util.Iterator local_RolesListIterator = rolesUsuario.iterator();
org.auriga.core.modelo.transferObjects.Rol local_RolesListElement;

for( ;local_RolesListIterator.hasNext(); ) {
	local_RolesListElement =(org.auriga.core.modelo.transferObjects.Rol)local_RolesListIterator.next();
	String rol = local_RolesListElement.getRolId();
	if( rol.equals(CRoles.ADMINISTRADOR_NACIONAL) ) {
		isAdministradorNacional = true;
		break;
	}
}

//SI ES ADMINISTRADOR NACIONAL SE MOSTRARÁN TODOS LOS CIRCULOS PARA ESCOGER EN DÓNDE CREAR EL USUARIO
//SINO ES ADMINISTRADOR NACIONAL MOSTRARÁ ÚNICAMENTE EL CIRCULO AL QUE PERTENECE EL USUARIO
List elementos = new java.util.ArrayList();
if(isAdministradorNacional){
	List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
	for( java.util.Iterator iter = circulos.iterator(); iter.hasNext(); ) {
		gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
		elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getNombre()));
	}
}else{
	gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);
	List circulosUsuario = usuario.getUsuarioCirculos();
	java.util.Iterator itCirculosCiudadano = circulosUsuario.iterator();
	gov.sir.core.negocio.modelo.UsuarioCirculo usuarioCirculoTemp = null;
	gov.sir.core.negocio.modelo.Circulo circuloTemp = null;	
	for(;itCirculosCiudadano.hasNext();) {
	  usuarioCirculoTemp = (gov.sir.core.negocio.modelo.UsuarioCirculo)itCirculosCiudadano.next();
	  circuloTemp = usuarioCirculoTemp.getCirculo();
	  elementos.add(new ElementoLista(circuloTemp.getIdCirculo(), circuloTemp.getNombre()));
	} 
}

ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(elementos);

//DNilson226 Tipo y num Doc
ListaElementoHelper docHelper = new ListaElementoHelper();

%>


<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Crear Usuarios </td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar un Usuario </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          
          
        <form action="administracionFenrir.do" method="get" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionFenrir.USUARIOS_CREAR %>" value="<%= AWAdministracionFenrir.USUARIOS_CREAR %>">
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Datos B&aacute;sicos </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
               </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="160">Nombre del Usuario en el Sistema</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CUsuario.LOGIN_USUARIO);
                  	textHelper.setCssClase("camposformtext_noCase");
                  	textHelper.setId(CUsuario.LOGIN_USUARIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                    <td width="105">Contrase&ntilde;a</td>
                    <td>
                    <input id="<%=  CUsuario.CLAVE_USUARIO  %>" size="" name="<%=  CUsuario.CLAVE_USUARIO  %>"  type="password" maxlength="" class="camposformtext_noCase"  > </td>
                    
                    <td width="105">Confirmacion de Contrase&ntilde;a</td>
                    <td>
                    <input id="<%=  CUsuario.CLAVE_USUARIO_CONFIRMACION  %>" size="" name="<%=  CUsuario.CLAVE_USUARIO_CONFIRMACION  %>"  type="password" maxlength="" class="camposformtext_noCase"  > </td>
                    
                    
                  </tr>
                  
                  <!-- DNilson226 Tipo y Num Doc -->
                  <tr>
                        <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td width="179">Tipo de Identificaci&oacute;n</td>
                        <td width="211">
                            <% try {
                                    List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                                    if (docs == null) {
                                        docs = new Vector();
                                    }
                                   /**
                                    * @author DNilson Olaya
                                    * DNilson 226 Se Agrega Validacion de eleminar elemento Secuencia de Tipos de Documentos         
                                    */
                                   
                                   List docsCopySet = new ArrayList();                                   
                                   
                                   for(int i=0; i<docs.size(); i++){
                                       try{
                                        ElementoLista elementLista = (ElementoLista) docs.get(i);
                                        //System.out.println("DNilson226 vlrs 1A.elementListaDocs.id:["+elementLista.getId()+"]  2B.elementListaDocs.valor:[ "+elementLista.getValor()+"]");
                                            if(!(elementLista.getId().trim().equals("SE"))){
                                             //System.out.println(">>>>>>>DNilson226 1AV. antes de eliminar element Secuencia de Listado de Tipos Docs");
                                            //docs.remove(elementLista);                                        
                                                //docs.remove(i);                               
                                                   docsCopySet.add(elementLista);
                                            //System.out.println(">>>>>>>DNilson226 2BV. despues de eliminar element Secuencia de Listado de Tipos Docs");
                                            }
                                        }catch(Exception e){
                                            System.err.println("<<<<error ejec:" +e.getMessage() +">>>>");
                                            e.printStackTrace();
                                        }
                                   }                           
                                   
                                   
                                    docHelper.setOrdenar(false);
                                    //docHelper.setTipos(docs);
                                    docHelper.setTipos(docsCopySet);
                                    docHelper.setNombre(CCiudadano.TIPODOC);
                                    docHelper.setCssClase("camposformtext");
                                    docHelper.setId(CCiudadano.TIPODOC);
                                    docHelper.render(request, out);
                                } catch (HelperException re) {
                                    out.println("ERROR " + re.getMessage());
                                }
                            %>
                        </td>
                        <td width="146">N&uacute;mero</td>
                        <td width="212">
                            <% try {
                                    textHelper.setNombre(CCiudadano.IDCIUDADANO);
                                    textHelper.setCssClase("camposformtext");
                                    textHelper.setId(CCiudadano.DOCUMENTO);
                                    textHelper.render(request, out);
                                } catch (HelperException re) {
                                    out.println("ERROR " + re.getMessage());
                                }
                            %>
                        </td>
                </tr>
                  <!-- DNilson226 Tipo y Num Doc -->
                   <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="160">Nombre</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CUsuario.NOMBRE_USUARIO);
                  	textHelper.setCssClase("camposformtext_noCase");
                  	textHelper.setId(CUsuario.NOMBRE_USUARIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                    <td width="105">Apellido 1</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CUsuario.APELLIDO1_USUARIO);
                  	textHelper.setCssClase("camposformtext_noCase");
                  	textHelper.setId(CUsuario.APELLIDO1_USUARIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                    
                    <td width="160">Apellido 2</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CUsuario.APELLIDO2_USUARIO);
                  	textHelper.setCssClase("camposformtext_noCase");
                  	textHelper.setId(CUsuario.APELLIDO2_USUARIO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
					  %>
                    </td>                    
                    
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="160">Circulo:</td>
                    <td>
                    <%

                    try {
	                  	circuloHelper.setId( gov.sir.core.negocio.modelo.constantes.CCirculo.ID_CIRCULO );
	                    circuloHelper.setNombre( gov.sir.core.negocio.modelo.constantes.CCirculo.ID_CIRCULO );
	                  	circuloHelper.setCssClase("camposformtext");
	                  	circuloHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}

			 		%>
                    </td>
                    <td colspan="4">&nbsp;</td>
                  </tr>
                </table>
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>Roles para el Usuario </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td class="campostip04">Oprima CTRL para hacer una selecci&oacute;n multiple. </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>
                    <% 
               try {
                    rolesHelper.setNombre(CRol.ID_ROL);
                  	rolesHelper.setCssClase("camposformtext");
                  	rolesHelper.setId(CRol.ID_ROL);
                  	rolesHelper.setShowInstruccion(false);
					rolesHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %> 
                    </td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Opciones</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></span></td>
                      </tr>
                  </table>
                  </td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
          </table>
          </td>
            <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              <tr>
                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                <td class="bgnsub">Opciones</td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
              </tr>
            </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionFenrir.USUARIOS_CREAR %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></td>
                  <td><input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionFenrir.CANCELA_CREAR_USUARIO %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></td>
                </tr>
              </table>
              </td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr></table>
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