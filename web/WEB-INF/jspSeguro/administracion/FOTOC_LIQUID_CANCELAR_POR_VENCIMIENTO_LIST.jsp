<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarFechaHelper" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFotocopiasLiquidCancelVencimiento" %>


<%

// declare dateComponent Helper ------------------------------------------------
TextHelper textHelper = new TextHelper();
textHelper.setId( "CALENDARDESDE" );
textHelper.setNombre( "CALENDARDESDE" );
MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
// -----------------------------------------------------------------------------

java.util.List turnosFotocopiasConPagoVencido = null;

double initialThreshold = 0d;
boolean carga = false;

if( null == request.getSession().getAttribute( AWAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_LISTAR_LISTATURNOSVENCIDOS ) ) {
  // ---------------- ON_LOAD_BEFORE_HEAD
  carga = true;
  turnosFotocopiasConPagoVencido = new ArrayList();


  if( null == request.getSession().getAttribute( "CALENDARDESDE" )  )  {
    /*
    final double DAY_DELTATIME = ( 1.0d ) * ( 24 * 60 * 60 ) * 1000;

    double currentTimeMillis = System.currentTimeMillis();
    double dayThreshold      = DAY_DELTATIME ;
    initialThreshold  = ( 60.0d ) * DAY_DELTATIME; // 2 meses antes aprox

    long milliseconds = (long)( currentTimeMillis - initialThreshold ) ;
    java.util.Date defaultSelectedDate = new java.util.Date( milliseconds );
    String dateAsString = gov.sir.core.util.DateFormatUtil.format( defaultSelectedDate );

    // colocarlo en session para que lo coja el componente
    request.getSession().setAttribute( "CALENDARDESDE", dateAsString );
    */
  }



}
else{

    turnosFotocopiasConPagoVencido
    = (List) request.getSession().getAttribute( AWAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_LISTAR_LISTATURNOSVENCIDOS );

}

%>

<%

List idmatriculas = new ArrayList();
int i =0;
for(Iterator iterator = turnosFotocopiasConPagoVencido.iterator(); iterator.hasNext();i++){
  Turno dato = (Turno)iterator.next();
  idmatriculas.add( i, dato.getIdWorkflow() );
}

request.getSession().setAttribute( WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idmatriculas );
%>



<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.FIND.<%= WebKeys.ACCION %>.value = text;
}

function submitForm2() {
        document.ACCION_VERDETALLES.submit();
}


function cambiarAccionAndSend(text) {
	document.FIND.<%= WebKeys.ACCION %>.value = text;
	document.FIND.submit();
}

function cambiarSeleccion() {
    if(document.ELIMINAR.seleccionado.checked == true){
	    setAllCheckBoxes('ELIMINAR', 'ELIMINAR_SOL', true);
    }
    if(document.ELIMINAR.seleccionado.checked == false){
    	setAllCheckBoxes('ELIMINAR', 'ELIMINAR_SOL', false);
    }
}

function cargaInicial() {
	document.FIND.<%= WebKeys.ACCION %>.value = '<%= AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_LISTAR %>';
	document.FIND.submit();
}
</script>




<!-- ------------------------------------------------------------------------------------------------------------------------- -->

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Consulta Solicitudes fotoc. vencidas</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Consulta de Detalles de Turnos</td>
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Datos del Turno</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>

<!-- form:paramas -->

<form
  action="fotoc.liquid.cancelar-por-vencimiento.list.do"
  method="post"
  name="FIND"
  id="FIND"
>
        <input
          type="hidden"
          name="<%= WebKeys.ACCION %>"
          id="<%= WebKeys.ACCION   %>"
          value="<%=  AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_LISTAR %>"
        />
             <table width="100%" class="camposform">

                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    </td>
                    <td width="220">Asignados para el dia: </td>
                    <td>
                    	<table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>

                          <%
						  		try {

									textHelper.setCssClase( "camposformtext" );
                                                                        textHelper.setReadonly( true );
									textHelper.render( request,out );
								}
							    catch( HelperException re ){
									out.println("ERROR " + re.getMessage());
								}
						%>

                         		<td><a href="javascript:NewCal('calendardesde','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>
                  			</tr>
                  		</table>
                    </td>
                </tr>


              </table>

              <table width="100%" class="camposform">
                  <tr>
                    <td width="20">&nbsp;</td>
                    <td class="campostip04">Elija uno de los turnos de fotocopias para editarla y enviarla a finalizacion. </td>
                  </tr>
              </table>

              <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_LISTAR  %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0">
                    </td>
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%= AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_VOLVER %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0">
                    </td>
                  </tr>
              </table>
</form>

<!-- /form:paramas -->

            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
		<tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
		</table>

<!-- HERE -->

              <form
                 action="fotoc.liquid.cancelar-por-vencimiento.list.do"
                 method="post"
                 name="ACCION_VERDETALLES"
                 id="ACCION_VERDETALLES"
              >
                <input
                  type="hidden"
                  name="<%= WebKeys.ACCION %>"
                  id="<%= WebKeys.ACCION %>"
                  value="<%=gov.sir.core.web.acciones.administracion.AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_VERDETALLES%>"
                />

                <%
                                    try {
                              //HELPER TABLA MATRICULAS
                                      gov.sir.core.web.helpers.comun.TablaMatriculaHelper tablaHelper
                                        = new gov.sir.core.web.helpers.comun.TablaMatriculaHelper();

		                      tablaHelper.setColCount(5);
		                      // tablaHelper.setInputName(WebKeys.FOLIO_ORIGEN);
                                      tablaHelper.setInputName( CTurno.ID_TURNO );
		                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
	                              tablaHelper.setContenidoCelda(gov.sir.core.web.helpers.comun.TablaMatriculaHelper.CELDA_RADIO);
                                      tablaHelper.render(request, out);
		                    }
		                    catch(HelperException re){
		                      out.println("ERROR " + re.getMessage());
		                    }
		                 %>


                <%--
                <input
                  type="hidden"
                  name="<%= CTurno.ID_TURNO %>"
                  id="<%= CTurno.ID_TURNO %>"
                  value="<%= dato.getIdWorkflow() %>">
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%= dato.getIdWorkflow()%></td>
                  <td class="campositem"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_buscar.gif" width="35" height="13" border="0">
                  </td>
                </tr>
                  --%>
               </form>

<!-- HERE -->

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


              <hr class="linehorizontal" />

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><a href="javascript:submitForm2()"><img src="<%=request.getContextPath()%>/jsp/images/btn_observar.gif" name="Folio" width="150" height="21" border="0" id="Folio"></a></td>
                  <td width="150">&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>


<!-- ----------------------------------------------------------------------------------------------------------------------- -->

                 <%--
                for(Iterator iterator = turnosFotocopiasConPagoVencido.iterator(); iterator.hasNext();){
                  Turno dato = (Turno)iterator.next();
                --%>
<%--
              <form
                 action="fotoc.liquid.cancelar-por-vencimiento.list.do"
                 method="post"
                 name="ACCION_VERDETALLES"
                 id="ACCION_VERDETALLES"
              >

                <input
                  type="hidden"
                  name="<%= WebKeys.ACCION %>"
                  id="<%= WebKeys.ACCION %>"
                  value="<%=gov.sir.core.web.acciones.administracion.AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_VERDETALLES%>"
                />
                <input
                  type="hidden"
                  name="<%= CTurno.ID_TURNO %>"
                  id="<%= CTurno.ID_TURNO %>"
                  value="<%= dato.getIdWorkflow() %>">
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%= dato.getIdWorkflow()%></td>
                  <td class="campositem"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_buscar.gif" width="35" height="13" border="0">
                  </td>
                </tr>
                </form>
--%>
                <%--
                 }
                --%>



<%

// gov.sir.core.web.helpers.administracion.fotocopias.RptItem_TurnoFotocopiaHelper drawHelper =
// new gov.sir.core.web.helpers.administracion.fotocopias.RptItem_TurnoFotocopiaHelper();
// drawHelper.render( request, out );


%>

<%
   if( carga ){
%>
	<script type="text/javascript" >
          cargaInicial();
        </script>
<%
   }
%>
