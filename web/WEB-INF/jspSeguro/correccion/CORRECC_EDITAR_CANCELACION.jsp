<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.registro.AnotacionesCancelarHelper"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="gov.sir.core.negocio.modelo.Cancelacion"%>
<%@page import="gov.sir.core.negocio.modelo.Anotacion"%>
<%@page import="gov.sir.core.negocio.modelo.Liquidacion"%>
<%@page import="gov.sir.core.web.helpers.registro.AnotacionCancelacionHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano"%>
<%@page import="gov.sir.core.negocio.modelo.LLaveMostrarFolioHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoMostrarFolioHelper"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="java.util.*;"%>
<%
	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	Folio f=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
	String vistaActual;

	AnotacionesCancelarHelper anot=new AnotacionesCancelarHelper();
	AnotacionCancelacionHelper anotacionesModificacionHelper = new AnotacionCancelacionHelper();
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	List grupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
	session.setAttribute("listanat",grupoNaturalezas);


	String num= (String) request.getSession().getAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL);

	request.getSession().setAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL_CANCELACION, num);


	Boolean refresco = (Boolean)request.getSession().getAttribute(AWCalificacion.HAY_REFRESCO);
	String idAnotacionCancelada="";
	String posicion="";
	String t=(String)session.getAttribute("ESCOGER_ANOTACION_CANCELACION");
	if(t!=null){
		idAnotacionCancelada=t;
	}
	if(refresco==null){
		List anotacionesTemporales = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
		Iterator iat= anotacionesTemporales.iterator();
		Anotacion a=null;
		for(;iat.hasNext();){
			Anotacion temp= (Anotacion) iat.next();
			if(temp.getOrden().equals(num)){
				a=temp;
			}
		}

		String ultimavista=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
		request.getSession().setAttribute("ULITIMA_VISTA_TEMPORAL", ultimavista);
		Cancelacion c ;

		//iniciando en el session los valores de la anotacion correspondiente
		List anotacionesCanceladas= (List)a.getAnotacionesCancelacions();
		if(anotacionesCanceladas!= null && anotacionesCanceladas.size()>0){
			c = (Cancelacion) anotacionesCanceladas.get(0);
		}else{
			c= new Cancelacion();
		}
		posicion = c.getCancelada().getOrden();
		idAnotacionCancelada= c.getCancelada().getIdAnotacion();
		String idNat = (String)request.getSession().getAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
		String nomNat = (String)request.getSession().getAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
		if(idNat==null){
			request.getSession().setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID,a.getNaturalezaJuridica().getIdNaturalezaJuridica());
			request.getSession().setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM,a.getNaturalezaJuridica().getNombre());
                         /**
                        * @Autor: Carlos Torres
                        * @Mantis: 0012705
                        * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                        * @Descripcion: Se asigna valores a campos en el formulario
                        */
                        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER,String.valueOf(a.getNaturalezaJuridica().getVersion()));
		}
		if( request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION) ==null ){
			request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION,a.getAnotacionesCiudadanos());
		}
		request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, Double.toString(a.getValor()));
		request.getSession().setAttribute(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION,a.getComentario());
		request.getSession().setAttribute(AWCalificacion.ANOTACION_A_EDITAR, a);
	}else{
		Anotacion a = null;
		a= (Anotacion)request.getSession().getAttribute(AWCalificacion.ANOTACION_A_EDITAR);
		Cancelacion c ;
		List anotacionesCanceladas= (List)a.getAnotacionesCancelacions();
		if(anotacionesCanceladas!= null && anotacionesCanceladas.size()>0){
			c = (Cancelacion) anotacionesCanceladas.get(0);
		}else{
			c= new Cancelacion();
		}
		posicion = c.getCancelada().getOrden();
		idAnotacionCancelada= c.getCancelada().getIdAnotacion();

	}
	request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);
	//se mira si ya esta seteado llavesPaginador
	LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
	LLaveMostrarFolioHelper lla=null;
	if(llaves==null){
		//se crea el objeto llavesPaginador y settear sus valores;
		llaves= new LLavesMostrarFolioHelper();
		lla= new LLaveMostrarFolioHelper();
		lla.setNombrePaginador("NOMBRE_PAGINADOR_CALIFICACION");
		lla.setNombreResultado("NOMBRE_RESULTADO_CALIFICACION");
		lla.setNombreNumPagina("NUM_PAGINA_ACTUAL_CALIFICACION");
		llaves.addLLave(lla);
		request.getSession().setAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO, llaves);
	}
	if(lla==null){
		lla=llaves.getLLave("NOMBRE_RESULTADO_CALIFICACION", "NOMBRE_PAGINADOR_CALIFICACION");
	}

	String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
	vistaActual= ultimaVista;
	String nomFormaCancelacion="FORMA_CANCELACION";
	String nomOrdenCancelada="NOM_ORDEN_CANCELADA";
%>
   <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
   <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
   <script type="text/javascript">
   function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.CANCELAR.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
	}
	function cambiarAccion(text) {
       document.CANCELAR.ACCION.value = text;
       if(text== '<%=AWCalificacion.GRABAR_EDICION_CANCELACION%>' ){
       	document.CANCELAR.ESCOGER_ANOTACION_CANCELACION.value = document.<%=nomFormaCancelacion%>.<%=nomOrdenCancelada%>.value;
       }
       document.CANCELAR.submit();
    }

	 function cargarAnotaciones(text) {
       document.CANCELAR.MATRICULA.value = text;
       cambiarAccion('CARGAR_ANOTACIONES');
       }

     function grabarTemp(text) {
       document.CANCELAR.MATRICULA.value = text;
       cambiarAccion('GRABACION_TEMPORAL_ANOTACION_CANCELACION');
       }

       function setTipoOficina(){
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}
function oficinas(nombre,valor,dimensiones)
{
	document.CANCELAR.ACCION.value='<%=AWCalificacion.PRESERVAR_INFO_CANCELACION%>';
	var idDepto = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
	popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
	popup.focus();
}

function juridica(nombre,valor,dimensiones)
{
	<%session.setAttribute("cancelar", "true"); %>
	document.getElementById('natjuridica_id').value=valor+"_ID";
	document.getElementById('natjuridica_nom').value=valor+"_NOM";
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.CANCELAR.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
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

function Secuencia(text){
	document.CANCELAR.<%=CAnotacionCiudadano.SECUENCIA %>.value = text;
	cambiarAccion('<%=AWCalificacion.REFRESCAR_EDICION_CANCELACION%>');
}
function quitar(pos,accion) {
	document.CANCELAR.POSICION.value = pos;
	cambiarAccion(accion);
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Editar Cancelaci&oacute;n</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Cancelaci&oacute;n</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
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

              <br>

              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">
                    Escoja la anotaci&oacute;n que desea cancelar </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_checkbox.gif" width="20" height="15"></td>
                  <td>Anotaciones</td>
                  <td>&nbsp;</td>
                </tr>



			   <%try{
			   			//setear atributos del folio.
			   			mFolio.setFolio(f);
			   			mFolio.setTipoMostrarFolio(CTipoMostrarFolioHelper.TIPO_COMPRIMIDO);
			   			mFolio.setNomOrdenCancelada(nomOrdenCancelada);
			   			mFolio.setIdAnotacionCancelada(idAnotacionCancelada);
			   			mFolio.setOrdenCancelada(posicion);

			   			/*setters de estilo*/
			   			mFolio.setECampos("camposform");
			   			mFolio.setECampoTexto("campositem");
			   			mFolio.setETituloFolio("titulotbcentral");
			   			mFolio.setETitulos("titresaltados");
			   			mFolio.setETitulosSecciones("bgnsub");

			   			/*setters de imagenes */
			   			mFolio.setImagenFolio("/jsp/images/ico_matriculas.gif");
			   			mFolio.setImagenNAnotaciones("/jsp/images/ani_folios.gif");
			   			mFolio.setImagenSeccionEncabezado("/jsp/images/ico_matriculas.gif");
			   			mFolio.setImagenSeparador("/jsp/images/ind_campotxt.gif");

			   			mFolio.setNombreAccionFolio("modificacion.do");
			   			mFolio.setNombreAccionPaginador("paginadorAnotaciones.do");
			   			mFolio.setNombreAncla("ancla");
			   			mFolio.setNombreForma("PAGINADOR_ADENTRO");
			   			mFolio.setNombreFormaFolio("FORMA_FOLIO");
			   			mFolio.setNombreFormaPaginador("FORMA_PAGINADOR_FOLIO");
			   			mFolio.setNombreAccionCancelacion("modificacion.do");
			   			mFolio.setNombreFormaCancelacion(nomFormaCancelacion);
			   			mFolio.setnombreNumAnotacionTemporal("NUM_A_TEMPORAL_CALIFICACION");
			   			mFolio.setNombreOcultarAnotaciones("O_ANOTACIONES");
			   			mFolio.setNombreOcultarFolio("O_FOLIO");
			   			mFolio.setNombrePaginador(lla.getNombrePaginador());
			   			mFolio.setNombreResultado(lla.getNombreResultado());
			   			mFolio.setnombreNumPaginaActual(lla.getNombreNumPagina());
			   			mFolio.setPaginaInicial(0);
			   			mFolio.setVistaActual(vistaActual);
			   			//datos a mostrar encabezado
			   			mFolio.NoMostrarEncabezado();
			   			mFolio.render(request, out);
				   }catch(HelperException re){
					 	out.println("ERROR " + re.getMessage());
					}%>

			  </table>

              <form action="modificacion.do" method="post" name="CANCELAR" id="CANCELAR">
              <input type="hidden" name="ACCION" value="">
              <input name="ESCOGER_ANOTACION_CANCELACION" id="ESCOGER_ANOTACION_CANCELACION" type="hidden" value="">
 			  <input type="hidden" name="POSICION" value="">
 			  <input type="hidden" name="<%=CAnotacionCiudadano.SECUENCIA%>" value="-1">
 			  <input type="hidden" name="CAMBIO" value="">

	<%try {
							   Liquidacion liq=(Liquidacion)turno.getSolicitud().getLiquidaciones().get(0);
							   Date fechaPago=liq.getPago().getFecha();
                              	if (fechaPago == null){
                              		fechaPago = new Date();
                              	}
 								anotacionesModificacionHelper.setEditar(true);
                              	anotacionesModificacionHelper.setFormName("CANCELAR");
                              	anotacionesModificacionHelper.setFecha(fechaPago);
							    anotacionesModificacionHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						}
%>
              <br>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><a href="javascript:cambiarAccion('GRABAR_EDICION_CANCELACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aprobar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
                  <td><a href="javascript:cambiarAccion('CANCELAR_EDICION_CANCELACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
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
