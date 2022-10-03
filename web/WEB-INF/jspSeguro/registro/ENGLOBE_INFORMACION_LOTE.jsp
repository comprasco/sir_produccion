<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.web.helpers.correccion.DireccionesHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolioDerivado"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %>
<%@page import="gov.sir.core.web.acciones.registro.AWEnglobe"%>
<%@page import="gov.sir.core.negocio.modelo.WebEnglobe"%>
<%@page import="gov.sir.core.negocio.modelo.WebFolioNuevo"%>


<%
        TextHelper areasHelper  = new TextHelper();
        areasHelper.setCssClase("camposformtext");

	Turno local_Turno;
	Solicitud local_Solicitud;	
	local_Turno = (Turno)session.getAttribute( WebKeys.TURNO );
	local_Solicitud = (Solicitud)local_Turno.getSolicitud();	
	Turno turno = local_Turno;


	ListaElementoHelper helperEje1 = new ListaElementoHelper();
	ListaElementoHelper helperEje2 = new ListaElementoHelper();
	TextAreaHelper textAreaHelper = new TextAreaHelper();

	TextHelper textHelper = new TextHelper();

    gov.sir.core.web.helpers.correccion.DireccionesHelper dirHelper = new DireccionesHelper(
    gov.sir.core.web.acciones.registro.AWEnglobe.AGREGAR_DIRECCION,
    gov.sir.core.web.acciones.registro.AWEnglobe.ELIMINAR_DIRECCION);
    dirHelper.setFuncionCambiarAccion("cambiarAccionSubmit");
    dirHelper.setFuncionQuitar("quitarDireccion");
    dirHelper.setNombreFormaEdicionDireccion("CALIFICACION");


	WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
	if (webEnglobe == null) {
		webEnglobe = new WebEnglobe();
	}
	WebFolioNuevo folioNuevo =  webEnglobe.getFolioNuevo();
	if(folioNuevo!=null){
		if(folioNuevo.getDescripcion()!=null){
			session.setAttribute(CFolioDerivado.DESCRIPCION, folioNuevo.getDescripcion());
		}
		if(folioNuevo.getNombre()!=null){
			session.setAttribute(CFolioDerivado.NOMBRE_NUMERO, folioNuevo.getNombre());
		}
		if(folioNuevo.getArea()!=null){
			session.setAttribute(CFolioDerivado.AREA, folioNuevo.getArea());
		}
	}

%>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>



<script type="text/javascript">
    
  function valideKey(evt){
    var code = (evt.which) ? evt.which : evt.keyCode;

    if(code==8) { 
      return true;
    } else if(code>=48 && code<=57) {
      return true;
    } else{
      return false;
    }
  }
  
  function cambiarAccion(text) {
         document.CALIFICACION.ACCION.value = text;
         document.CALIFICACION.submit();
         }

  function cambiarAccionSubmit(text) {
          document.CALIFICACION.ACCION.value = text;
          document.CALIFICACION.submit();
  }

  function quitarDireccion(pos,accion){
          if(confirm("Esta seguro que desea eliminar la dirección ?")){
                  document.CALIFICACION.POSICION.value = pos;
                  cambiarAccionSubmit(accion);
          }
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">ASISTENTE ENGLOBE - PASO 5 - INFORMACIÓN DEL	INMUEBLE</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">INGRESAR INFORMACIÓN DEL INMBUEBLE</td>
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
            <form action="englobe.do" method="post" name="CALIFICACION" id="CALIFICACION">
            <input type="hidden" name="ACCION" value="">
			<input type="hidden" name="POSICION" id="POSICION" />
			<input type="hidden" name="POSSCROLL" id="POSSCROLL" value="<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
                <table width="100%" class="camposform">



                  <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="200">N&uacute;mero o nombre:</td>
                  <td>

     				  <%
							 try {
								 textHelper.setNombre(CFolioDerivado.NOMBRE_NUMERO);
								 textHelper.setCssClase("camposformtext");
								 textHelper.setId(CFolioDerivado.NOMBRE_NUMERO);
								 textHelper.setSize("40");
								 textHelper.render(request,out);

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
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td colspan="2">
                  <table class="camposformnoborder">
                  <td width="200">Area:</td>
                                <td align="right">Hectareas</td>
                                <td nowrap>
				<%
				try {
	                areasHelper.setNombre(CFolioDerivado.HECTAREAS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolioDerivado.HECTAREAS);
                        areasHelper.setSize("20");
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolioDerivado.HECTAREAS+"');\"  "
                                + "onChange=\"valideDot('"+CFolioDerivado.HECTAREAS+"')\"  "
                                + "onBlur=\"hectareasFormatter('"+CFolioDerivado.HECTAREAS+"','"+CFolioDerivado.METROS+"','"+CFolioDerivado.CENTIMETROS+"')\" ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                Metros<sup>2</sup> &nbsp;
				<%
				try {
	                areasHelper.setNombre(CFolioDerivado.METROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolioDerivado.METROS);
                        areasHelper.setMaxlength("20");
                        areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolioDerivado.METROS+"');\"  "
                                + "onChange=\"valideDot('"+CFolioDerivado.METROS+"')\"  "
                                + "onBlur=\"metrosFormatter('"+CFolioDerivado.HECTAREAS+"','"+CFolioDerivado.METROS+"','"+CFolioDerivado.CENTIMETROS+"')\" ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) {
	                out.println("ERROR " + re.getMessage());
	            }
            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                             Centimetros<sup>2</sup> &nbsp;
                             <%
				try {
	                areasHelper.setNombre(CFolioDerivado.CENTIMETROS);
	                areasHelper.setCssClase("camposformtext");
	                areasHelper.setId(CFolioDerivado.CENTIMETROS);
                        areasHelper.setMaxlength("4");
                        areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" ");
                        areasHelper.setReadonly(false);
	                areasHelper.render(request, out);
	            } catch (HelperException re) { 
	                out.println("ERROR " + re.getMessage());
	            }
            %></td>
                  </table>
                  </td>
                  <td colspan='2'>&nbsp;</td>
                  </tr>
                  </table>


                  <table width="100%" class="camposform">
                  <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="200">Complementación:</td>
                  <td>

                  <%
		                textAreaHelper.setNombre(CFolioDerivado.DESCRIPCION);
		                textAreaHelper.setCols("100");
		                textAreaHelper.setReadOnly(false);
		                textAreaHelper.setRows("5");
		                textAreaHelper.setCssClase("camposformtext");
		                textAreaHelper.setId(CFolioDerivado.DESCRIPCION);
		                textAreaHelper.render(request,out);
		           %>

                </td>
                  </tr>
                  </table>


		<%
			try {
                dirHelper.render(request, out);
            } catch (HelperException re) {
	           	re.printStackTrace();
            }catch(Throwable t){
	           	t.printStackTrace();
            }

            %>

              <hr class="linehorizontal">
              <br>
              <table width="100%" class="camposform">

                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="150"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.registro.AWEnglobe.INFORMACION_LOTE%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" name="Folio" width="139" height="21" border="0" id="Folio"></td>

					<!--BOTON REGRESAR-->                 
                    <TD width="150">
	                <a href="registro.englobar.ubicacion.geografica.view?POSSCROLL=<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
	                 	<img alt="Regresar" src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
	                </a>
					</TD>
					
					<!--BOTON CANCELAR-->   
                  <td width="150">
					<%-- SOF:SCRIPT-VARS --%>
					<script type="text/javascript">
					   var CANCELAR_ENGLOBE = "<%= AWEnglobe.CANCELAR_ENGLOBE %>";
					   var ELIMINAR_ENGLOBE = "<%= AWEnglobe.ELIMINAR_ENGLOBE %>";
					   function eliminarEnglobe(){
					      if(confirm('¿Desea eliminar el englobe en curso?')){
					         cambiarAccion( ELIMINAR_ENGLOBE );
					      }
					   }					   
					</script>
					<%-- EOF:SCRIPT-VARS --%>
		              <%-- SOF:BUTTON --%>
		                <a href="javascript:cambiarAccion( CANCELAR_ENGLOBE );">
		                 <img alt="Salir del englobe en curso" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
		                </a>
		              <%-- EOF:BUTTON --%>
                  </td>


	              <!--ELIMINAR ENGLOBE EN CURSO-->
	              <td width="150">
	                <a href="javascript:eliminarEnglobe();">
	                 <img alt="Eliminar englobe en curso" src="<%=request.getContextPath()%>/jsp/images/btn_eliminar_englobe.gif" name="Folio" width="180" height="21" border="0" id="Folio"  />
	                </a>
              	  </td>               


    			  <td>&nbsp;</td>
    			  <td>&nbsp;</td>
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
