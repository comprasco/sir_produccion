<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import="gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.registro.AnotacionCancelacionHelper"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoMostrarFolioHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<% 
	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	Folio f=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
	String vistaActual;
	String nomFormaCancelacion="FORMA_CANCELACION";
	String nomOrdenCancelada="NOM_ORDEN_CANCELADA";
	
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	
	String posicion="";
	String idAnotacionCancelacion="";
	String temp;
	temp=(String)session.getAttribute("ESCOGER_ANOTACION_CANCELACION");
	if(temp!=null){
		idAnotacionCancelacion=temp;
	}
	
	
	//se mira si ya esta seteado llavesPaginador
	LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
	LLaveMostrarFolioHelper lla=null;
	if(llaves==null){
		//se crea el objeto llavesPaginador y settear sus valores;
		llaves= new LLavesMostrarFolioHelper();
		lla= new LLaveMostrarFolioHelper();
		lla.setNombrePaginador("NOMBRE_PAGINADOR_ANTSISTEMA");
		lla.setNombreResultado("NOMBRE_RESULTADO_ANTSISTEMA");
		lla.setNombreNumPagina("NUM_PAGINA_ACTUAL_ANTSISTEMA");
		llaves.addLLave(lla);
		request.getSession().setAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO, llaves);
	}
	if(lla==null){
		lla=llaves.getLLave("NOMBRE_RESULTADO_ANTSISTEMA", "NOMBRE_PAGINADOR_ANTSISTEMA");
	}
	
	String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
	vistaActual= ultimaVista;

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
       //document.CANCELAR.ESCOGER_ANOTACION_CANCELACION.value = document.<%=nomFormaCancelacion%>.<%=nomOrdenCancelada%>.value;
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
	document.CANCELAR.ACCION.value='<%=AWAntiguoSistema.PRESERVAR_INFO_CANCELACION%>';
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

}
function quitar(pos,accion) {
	document.CANCELAR.POSICION.value = pos;
	cambiarAccion(accion);
}
function reescribirValor(valor,id){
	var my_str = valor;
	var miles=1;
	if(my_str.indexOf(".")==-1){
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=my_str.length;i++){
				var desde = my_str.length-i*3;
				var hasta = my_str.length-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr=nStr+".00";
		document.getElementById(id).value = nStr;		
		}
	} else {
		var largo = my_str.indexOf(".");
		var centavos = my_str.substr(largo,my_str.length);
		if(my_str.indexOf(",")==-1){
			var nStr = "";
			for(var i=1;i<=largo;i++){
				var desde = largo-i*3;
				var hasta = largo-(3*(i-1));
				var temp = my_str.slice(desde,hasta);
				var separador="";
				if(hasta>3){
					if(miles==1){
						miles=0;
						separador=",";
					} else {
						miles=1
						separador=",";
					}
					nStr=separador+temp+nStr;
				} else {
					if(hasta>0){
						temp=my_str.slice(0,hasta);
						nStr=temp+nStr;
					}
				}
			}
		nStr = nStr+centavos;
		document.getElementById(id).value = nStr;		
		}	
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
  </tr>
  <tr> 
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Cancelar</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 



          <table border="0" cellpadding="0" cellspacing="0">
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
						List falsaTradicion = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION);
						List anotacionesInvalidas = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS);	
			   
			   			//setear atributos del folio.
			   			mFolio.setFolio(f);
			   			mFolio.setTipoMostrarFolio(CTipoMostrarFolioHelper.TIPO_COMPRIMIDO);
			   			mFolio.setNomOrdenCancelada(nomOrdenCancelada);
			   			mFolio.setIdAnotacionCancelada(idAnotacionCancelacion);
			   			
			   			//setters de estilo
			   			mFolio.setECampos("camposform");
			   			mFolio.setECampoTexto("campositem");
			   			mFolio.setETituloFolio("titulotbcentral");
			   			mFolio.setETitulos("titresaltados");
			   			mFolio.setETitulosSecciones("bgnsub");
			   			
			   			//setters de imagenes 
			   			mFolio.setImagenFolio("/jsp/images/ico_matriculas.gif");
			   			mFolio.setImagenNAnotaciones("/jsp/images/ani_folios.gif");
			   			mFolio.setImagenSeccionEncabezado("/jsp/images/ico_matriculas.gif");
			   			mFolio.setImagenSeparador("/jsp/images/ind_campotxt.gif");
			   			
			   			mFolio.setNombreAccionFolio("antiguosistema.do");
			   			mFolio.setNombreAccionPaginador("paginadorAnotaciones.do");
			   			mFolio.setNombreAncla("ancla");
			   			mFolio.setNombreForma("PAGINADOR_ADENTRO");
			   			mFolio.setNombreFormaFolio("FORMA_FOLIO");
			   			mFolio.setNombreFormaPaginador("FORMA_PAGINADOR_FOLIO");
			   			mFolio.setNombreAccionCancelacion("antiguosistema.do");
			   			mFolio.setNombreFormaCancelacion(nomFormaCancelacion);
			   			mFolio.setnombreNumAnotacionTemporal("NUM_A_TEMPORAL_ANTSISTEMA");
			   			mFolio.setNombreOcultarAnotaciones("O_ANOTACIONES");
			   			mFolio.setNombreOcultarFolio("O_FOLIO");
			   			mFolio.setNombrePaginador(lla.getNombrePaginador());
			   			mFolio.setNombreResultado(lla.getNombreResultado());
			   			mFolio.setnombreNumPaginaActual(lla.getNombreNumPagina());
			   			mFolio.setPaginaInicial(0);
			   			mFolio.setVistaActual(vistaActual);
			   			//datos a mostrar encabezado
			   			mFolio.NoMostrarEncabezado();
			   			Calendar calendar = Calendar.getInstance();
			   			calendar.set(Calendar.YEAR,Integer.parseInt((String)session.getAttribute(AWAntiguoSistema.ANIO_VALIDACION_CANCELACION)));
						mFolio.setMostrarMedidasYGravamenes(true,calendar);
			   			mFolio.mostrarAnotacionesCancelacioMultiple(AWAntiguoSistema.GUARDAR_ANOTACIONES_TEMPORALES);
			   			mFolio.setEditarAnotacionesNoTemporales(true);
			   			mFolio.setGravamenes((List)session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO));
			   			mFolio.setMedCautelares((List)session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO));
			   			mFolio.setFalsaTradicion(falsaTradicion);
			   			mFolio.setAnotacionesInvalidas(anotacionesInvalidas);
			   			mFolio.render(request, out);
				   }catch(HelperException re){
					 	out.println("ERROR " + re.getMessage());
					}
				%>

	
    
    				
               	</table>
               
                <form action="antiguosistema.do" method="post" name="CANCELAR" id="CANCELAR"> 
                <input type="hidden" name="ACCION" value="">
                <!--<input name="ESCOGER_ANOTACION_CANCELACION" id="ESCOGER_ANOTACION_CANCELACION" type="hidden" value="">-->
 				<input type="hidden" name="POSICION" value="">
 				<input type="hidden" name="<%=CAnotacionCiudadano.SECUENCIA%>" value="-1">
 				<input type="hidden" name="CAMBIO" value="">
	<%/*try {
							   Liquidacion liq=(Liquidacion)turno.getSolicitud().getLiquidaciones().get(0);
							   Date fechaPago=liq.getPago().getFecha();
                              	if (fechaPago == null){
                              		fechaPago = new Date();
                              	}
                              	anotacionesModificacionHelper.setFormName("CANCELAR");
                              	anotacionesModificacionHelper.setFecha(fechaPago);
							    anotacionesModificacionHelper.render(request,out);
						    }
								catch(HelperException re){
						 	out.println("ERROR " + re.getMessage());
						}*/
%>

              
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><a href="javascript:guardar()"><img src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
                  <td><a href="javascript:cambiarAccion('CANCELAR_CANCELACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
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
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
  </tr>
</table>