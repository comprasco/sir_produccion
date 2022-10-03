<%@page import="org.auriga.core.web.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gov.sir.core.negocio.modelo.SubtipoSolicitud"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaCheckboxHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.negocio.modelo.TurnoFolioMig"%>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.negocio.modelo.CheckItem"%>
<%@page import="gov.sir.core.web.helpers.registro.OficinaHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.web.acciones.registro.AWLiquidacionRegistro"%>
<%@page import="java.util.Calendar"%>
<%@page import="gov.sir.core.negocio.modelo.Proceso"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>

<%
   TextAreaHelper textArea = new TextAreaHelper();
   ListaElementoHelper subtipo = new ListaElementoHelper();
   ListaElementoHelper tipoEncabezado= new ListaElementoHelper();
   OficinaHelper oficinaHelper = new OficinaHelper();
   ListaCheckboxHelper docHelper = new ListaCheckboxHelper();
   TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
   List matriculasAsociadas=(List)session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
   List turnosFolioMig=(List)session.getAttribute(WebKeys.LISTA_MATRICULAS_TURNO_FOLIO_MIG);
   List matriculasAsociadasMig=(List)session.getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);
   String style = "camposformtext";
   Turno turnoAnteriorObjeto = (Turno)session.getAttribute(CTurno.TURNO_ANTERIOR_OBJETO);
   boolean tieneMatriculaAsociada = false;
   if(turnoAnteriorObjeto!=null && turnoAnteriorObjeto.getIdMatriculaUltima()!=null){
   		tieneMatriculaAsociada = true;
   }
   
   Boolean readOnly =  (Boolean)session.getAttribute(WebKeys.TURNO_ANTERIOR_OK);
   
   boolean read = false;
   if(readOnly!=null && readOnly.booleanValue()){
   	   read = true;
   	   style = "campositem";
   }
   
   
   String msgMatricula = (String) session.getAttribute(AWLiquidacionRegistro.MATRICULA_BLOQUEADA_NOTA_DEV);
   boolean cargarMsg = false;
   if(msgMatricula != null){
       cargarMsg = true;
       session.removeAttribute(AWLiquidacionRegistro.MATRICULA_BLOQUEADA_NOTA_DEV);
   }
   
   String ocultarAS=(String)session.getAttribute("VER_ANTIGUO_SISTEMA");
   if (ocultarAS==null){
   		ocultarAS="TRUE";
   }
   else{
   		session.setAttribute("VER_ANTIGUO_SISTEMA",ocultarAS);
   }

   String ultimo= null;
   List subtipos = new ArrayList();
   List subtiposObj = (List) session.getServletContext().getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);


   if (subtiposObj!= null && subtiposObj.size()>0){
	    Iterator itActo =subtiposObj.iterator();
		ultimo=(String)session.getAttribute("ultimo_campo_editado");
		session.removeAttribute("ultimo_campo_editado");
		while(itActo.hasNext()){
		    SubtipoSolicitud sub=(SubtipoSolicitud)itActo.next();
		    if (sub!= null){
		        String idTipo = sub.getIdSubtipoSol();
		        String nombre = sub.getNombre();
		        if (idTipo!= null && nombre!= null){
		        	ElementoLista elemLista = new ElementoLista(idTipo,nombre);
 				    subtipos.add(elemLista);
				}
			}
		}
   }


    Object ss = request.getSession().getAttribute("SUBTIPO_SOLICITUD");
    Object lsc = request.getSession().getAttribute("LISTA_SUBTIPO_CHEQUEO");
    if ( ss == null || lsc == null) {
        String sel = "1";
        request.getSession().setAttribute("SUBTIPO_SOLICITUD", sel);
		List lsubtipos = (List) request.getSession().getServletContext().getAttribute(
				WebKeys.LISTA_SUBTIPOS_SOLICITUD);
		if (lsubtipos != null && lsubtipos.size()>0){
			Iterator it = lsubtipos.iterator();
			List chequeo = new ArrayList();
			while (it.hasNext()) {
				SubtipoSolicitud sub = (SubtipoSolicitud) it.next();
				if (sel.equals(sub.getIdSubtipoSol())) {
					List checked = sub.getCheckItems();
					Iterator it2 = checked.iterator();
					while (it2.hasNext()) {
						CheckItem ch = (CheckItem) it2.next();
						if(ch!= null){
							chequeo.add(new ElementoLista(ch.getIdCheckItem(), ch.getNombre()));
						}
					}
					request.getSession().setAttribute("LISTA_SUBTIPO_CHEQUEO", chequeo);
				}
			}
		}
   }


	//inicializando el # del turno anterior

	String turnoAnterior= (String) session.getAttribute("TURNO_ANTERIOR");
	if(turnoAnterior==null){
		turnoAnterior="";
	}

        // -----------------------------------------------------------------------------------------
        // Inicializando la solicitud vinculada
        String solicitudVinculada
        = (String) session.getAttribute( "SOLICITUD_VINCULADA" );

        if( null == solicitudVinculada ){
          solicitudVinculada = "";
        }
        // -----------------------------------------------------------------------------------------





	//IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}
%>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>

<script>
	var ultimo_campo_editado;
	var vinculo;

/* JAlcazar 29/05/2009
     *Se Agrego una nueva funcion JavaScript llamada cambiarAccion2 la cual tiene el mismo
     comportamiento del proceso Cajero->Registro
*/

   	function cambiarAccion2(text,anio,circulo,proceso,sec) {
	   document.REGISTRO.<%=AWLiquidacionRegistro.ID_SOLICITUD%>.value=anio+"-"+circulo+"-"+proceso+"-"+
	   		document.REGISTRO.AUXA.value;
       document.REGISTRO.ACCION.value = text;
       document.REGISTRO.submit();
    }
    function ocultarAntiguoSistema(text) {
	   document.REGISTRO.VER_ANTIGUO_SISTEMA.value = text;
	   cambiarAccion('VER_ANTIGUO_SISTEMA');
  	}

   	function cambiarAccionForm(text) {
	   document.REGISTRO.ACCION.value = text;
  	}

	function validarFecha(){
		if (document.REGISTRO.<%=CSolicitudRegistro.CALENDAR%>.value.length>0){
			var index=document.REGISTRO.<%=CSolicitudRegistro.CALENDAR%>.value.lastIndexOf('/')+1;
			if (index!=null){
				var fin=document.REGISTRO.<%=CSolicitudRegistro.CALENDAR%>.value.length;
				var texto=document.REGISTRO.<%=CSolicitudRegistro.CALENDAR%>.value.substring(index,fin);
				if (texto.length!=4){
					alert('Fecha incorrecta');
					document.REGISTRO.<%=CSolicitudRegistro.CALENDAR%>.value='';
					document.REGISTRO.<%=CSolicitudRegistro.CALENDAR%>.focus();
				}
			}
		}
		
		
	}

  	function matriculaFocus(){/*
  		<%
	  	if (ultimo!=null && ultimo.equals("ID_MATRICULA")){
	  		Integer nume = (Integer) request.getSession().getAttribute(CFolio.NUM_MATRICULAS);
	  		String campo=CFolio.ID_MATRICULA+nume.toString();
	  		%>
	  		document.REGISTRO.<%=campo%>.focus();
  		<%}
	  	%>*/
  	}

	function validacion (){
		ultimo_campo_editado="<%=ultimo%>";
		document.getElementById("ultimo_campo_editado").value=ultimo_campo_editado;
		vinculo="#"+ultimo_campo_editado;
		document.location.href=vinculo
		elemento=document.getElementById("ultimo_campo_editado").value
		if(document.getElementById(ultimo_campo_editado+"_img")!=null){document.getElementById(ultimo_campo_editado+"_img").className="imagen_campo_editar"}
	}

	function campoactual(id){
		if (document.getElementById("ultimo_campo_editado")!=null){
			document.getElementById("ultimo_campo_editado").value=id;
		}
	}

	function asociarTurno() {
	    document.REGISTRO.ACCION.value = 'VALIDAR_TURNO_ANTERIOR';
	    document.REGISTRO.submit();
	}

	function liquidarRegistro(){
		document.REGISTRO.SUBTIPO_SOLICITUD.value = document.REGISTRO.SUBTIPO_SOLICITUD.value;
		cambiarAccion('LIQUIDAR_REGISTRO');
	}

	function eliminarMatriculas(text) {
	   document.REGISTRO.SUBTIPO_SOLICITUD.value = document.REGISTRO.SUBTIPO_SOLICITUD.value;
	   document.REGISTRO.ACCION.value = text;
	   document.REGISTRO.submit();
	}

	function agregarSubtipo(text) {
		document.REGISTRO.ACCION.value = text;
	    document.REGISTRO.submit();
	}

	function cargarSubTipo(){
		   <%if (request.getSession().getAttribute("SUBTIPO_SOLICITUD") != null){
		   		Object b = request.getSession().getAttribute("SUBTIPO_SOLICITUD");
		   		System.out.print(b.getClass().getName());
		   		if(b!=null){
			   		try {
			   		  String ssol = (String) b;
			   		  subtipo.setSelected(ssol);
			   		} catch (Exception e){
			   		  subtipo.setSelected(null);
			   		}
		   		}
		   	}%>
	}

	function cambiarAccion(text) {
	    document.REGISTRO.ACCION.value = text;
	    document.REGISTRO.submit();
	}

	function agregarMatricula(text) {
		//document.REGISTRO.SUBTIPO_SOLICITUD.value = document.REGISTRO.SUBTIPO_SOLICITUD.value;
	    document.REGISTRO.ACCION.value = text;
	    document.REGISTRO.submit();
	}

	function agregarMatriculaSinMigrar(text) {
		if(confirm('¿Esta seguro que la matrícula aún no se ha migrado?. Si no es así utilice la opción de :     Número de Matrícula.')){
			//document.REGISTRO.SUBTIPO_SOLICITUD.value = document.REGISTRO.SUBTIPO_SOLICITUD.value;
	    	document.REGISTRO.ACCION.value = text;
	    	document.REGISTRO.submit();
		}
	}

	function quitar(text) {
		document.REGISTRO.ITEM.value = text;
		cambiarAccion('ELIMINAR');
	}

	function setTipoOficina(){
	/*	document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
		document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
		document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";
	*/
	}

	function oficinas(nombre,valor,dimensiones){
		document.REGISTRO.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
		var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
		var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
		var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
		document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
		document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
		document.getElementById('numero_oficina').value=valor+"_NUM";
		document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
                 /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                document.getElementById('version').value=valor+"_OFICINA_VERSION";
		popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
		popup.focus();
	}

	function juridica(nombre,valor,dimensiones){
		document.getElementById('natjuridica_id').value=valor+"_ID";
		document.getElementById('natjuridica_nom').value=valor+"_NOM";
		popup=window.open(nombre,valor,dimensiones);
		popup.focus();
	}

	function verAnotacion(nombre,valor,dimensiones,pos){
		document.CREAR.POSICION.value=pos;
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

	function cambiarValorTipoDocumento(text){
        try{
        	document.getElementById('TIPO_ENCABEZADO').options[text].selected=true;
        }catch(e){
        	document.getElementById('TIPO_ENCABEZADO').value=' <%=WebKeys.SIN_SELECCIONAR%>';
        	document.getElementById('ID_TIPO_ENCABEZADO').value='';
        }
   }

	function habilitarModificacion(){
		document.all.display_modificacion_off.style.display = 'none';
		document.all.display_modificacion_on.style.display = 'block';
		document.all.display_modificacion_1.style.display = 'block';
		document.all.display_modificacion_2.style.display = 'block';
		document.all.display_modificacion_3.style.display = 'block';
	}
	function desHabilitarModificacion(){
		document.all.display_modificacion_off.style.display = 'block';
		document.all.display_modificacion_on.style.display = 'none';
		document.all.display_modificacion_1.style.display = 'none';
		document.all.display_modificacion_2.style.display = 'none';
		document.all.display_modificacion_3.style.display = 'none';
	}

	function habilitarAsociados(){
		document.all.display_asociados_off.style.display = 'none';
		document.all.display_asociados_on.style.display = 'block';
		document.all.display_asociados_1.style.display = 'block';
		document.all.display_asociados_2.style.display = 'block';
		document.all.display_asociados_3.style.display = 'block';
	}
	function desHabilitarAsociados(){
		document.all.display_asociados_off.style.display = 'block';
		document.all.display_asociados_on.style.display = 'none';
		document.all.display_asociados_1.style.display = 'none';
		document.all.display_asociados_2.style.display = 'none';
		document.all.display_asociados_3.style.display = 'none';
	}
        function validarNumMatriculas(){
    
    var matriculas = document.getElementsByName("ELIMINAR_CHECKBOX");
    var paramMatr = "0";
       paramMatr  =  document.getElementById("NUM_MAT_AGR").value;
    
        
    if(paramMatr !== "0" &&  matriculas.length > 0)
    {
        
        if(paramMatr > matriculas.length)
        {
           
            if(confirm("Esta seguro que desea avanzar el turno. Faltan matriculas."))
            {
                liquidarRegistro();
            }
                
        } else{
            liquidarRegistro();
        }

    }else{
        liquidarRegistro();
    }
    
}
	function modificarLiquidacion(text,anio,circulo,proceso,sec) {
		if(document.REGISTRO.AUX.value!=""){
			document.REGISTRO.<%=AWLiquidacionRegistro.ID_SOLICITUD%>.value=anio+"-"+circulo+"-"+proceso+"-"+document.REGISTRO.AUX.value;
		    document.REGISTRO.ACCION.value = text;
		    document.REGISTRO.submit();
	    }
	}

	cargarSubTipo();
</script>
<script type="text/javascript">

function jsEvent_VincularTurno_AddItem() {
    // send action-event: VALIDAR_TURNO_VINCULADO
    var actionId = 'REGISTRO_VINCULARTURNO_ADDITEM_ACTION' ;//'VALIDAR_TURNO_VINCULADO';
    cambiarAccion( actionId );
}
function jsEvent_VincularTurno_DelItem() {
    var actionId = 'REGISTRO_VINCULARTURNO_DELITEM_ACTION' ;//'ELIMINAR_TURNO_VINCULADO';
    cambiarAccion( actionId );
}


</script>



<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css"	rel="stylesheet" type="text/css" />
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>

	<input name="Depto" type="hidden" id="id_depto" value="">
	<input name="Depto" type="hidden" id="nom_Depto" value="">
	<input name="Mpio" type="hidden" id="id_munic" value="">
	<input name="Mpio" type="hidden" id="nom_munic" value="">
	<input name="Ver" type="hidden" id="id_vereda" value="">
	<input name="Ver" type="hidden" id="nom_vereda" value="">

	
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td class="tdtablaanexa02">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			<tr>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="7" height="10"></td>









				<form action="turnoLiquidacionRegistro.do" method="post" name="REGISTRO" id="REGISTRO">
					<input type="hidden" name="ACCION" id="ACCION" value="LIQUIDAR_REGISTRO">
					<input name="ultimo_campo_editado" type="hidden" id="ultimo_campo_editado">
					<input type="hidden" name="VER_ANTIGUO_SISTEMA"	id="VER_ANTIGUO_SISTEMA">
					<input type='image' src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="00" height="00">

				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img
					src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"> </td>
				<td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
					width="10" height="10"></td>
			</tr>
			<tr>
				<td><img name="tabla_central_r1_c1"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif"
					width="7" height="29" border="0" alt=""></td>
				<td
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif"
							class="titulotbcentral">Solicitud de Registro</td>
						<td width="9"><img name="tabla_central_r1_c3"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif"
							width="9" height="29" border="0" alt=""></td>
						<td width="20" align="center" valign="top"
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_new.gif"
									width="16" height="21"></td>
								<td><img
									src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif"
									width="16" height="21"></td>
							</tr>
						</table>
						</td>
                                        <!--AHERRENO 17/05/2012
                                            REQ 076_151 TRANSACCIONES-->        
                                        <td width="120" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                                                <td class="titulotbcentral"><%= request.getSession().getAttribute("TIEMPO_TRANSACCION")%> Seg.</td>                
                                            </tr>
                                        </table></td>
						<td width="12"><img name="tabla_central_r1_c5"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif"
							width="12" height="29" border="0" alt=""></td>
					</tr>
				</table>
				</td>
				<td><img name="tabla_central_pint_r1_c7"
					src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif"
					width="11" height="29" border="0" alt=""></td>
			</tr>


			<tr>
				<td width="7"
					background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
				<td valign="top" bgcolor="#79849B" class="tdtablacentral">

				<!--
				<table border="0" width="100%" cellpadding="0" cellspacing="2"	id="OCULTAR">

					<tr id='display_modificacion_on' style='display:none ; cursor:hand'>
						<td width="100%"></td>
						<td width="16">
							<img id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0" onClick="javascript:desHabilitarModificacion()">
						</td>
						<img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
					</tr>

					<tr id='display_modificacion_off' style='display:block; cursor:hand'>
						<td width="100%" align="right" class="campostip04">Haga click para agregar certificados asociados a un turno existente</td>
						<td width="16">
							<img img id="MAXIMIZAR"	src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0" onClick="javascript:habilitarModificacion()">
						</td>
						<img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
					</tr>

				</table>
				-->


				<table border="0" width="100%" cellpadding="0" cellspacing="2"	id="OCULTAR">

					<tr id='display_modificacion_on' style='display:none ; cursor:hand'>
						<td width="100%"></td>
						<td width="16">
							<img id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0" onClick="javascript:desHabilitarModificacion()">
						</td>
						<img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
					</tr>

					<tr id='display_modificacion_off' style='display:block; cursor:hand'>
						<td width="100%" align="right" class="campostip04">Haga click para modificar una liquidación existente</td>
						<td width="16">
							<img img id="MAXIMIZAR"	src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0" onClick="javascript:habilitarModificacion()">
						</td>
						<img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
					</tr>

				</table>


				<table width="100%" border="0" cellpadding="0" cellspacing="0" id='display_modificacion_1' style='display:none'>
					<tr>
						
						<td	background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">
							Modificar liquidación existente
						</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" ></td>
					</tr>
				</table>

				<table width="100%" class="camposform" id='display_modificacion_2' style='display:none'>
					<tr>
						<td width="20"><img	src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"	width="20" height="15"></td>
						<td width="20%">Constancia de liquidación</td>
						<td width="30%">
				            <%java.util.Calendar cal=Calendar.getInstance();
				            int year=cal.get(Calendar.YEAR);
				            String sYear=String.valueOf(year);

				            Circulo circulo=(Circulo)session.getAttribute(WebKeys.CIRCULO);
				            gov.sir.core.negocio.modelo.Proceso proceso=(Proceso)session.getAttribute(WebKeys.PROCESO);

				            String sCirculo=circulo.getIdCirculo();
				            long lProceso=proceso.getIdProceso();
				            String sProceso=String.valueOf(lProceso);
				            %>

							L-<%=sYear%>-<%=sCirculo%>-<%=sProceso%>-

				            <%try {
                                TextHelper textHelper = new TextHelper();
				                textHelper.setNombre("AUX");
								textHelper.setCssClase(style);
				                textHelper.setId("AUX");
				                textHelper.setSize("15");
				                textHelper.setTipo("text");
								textHelper.setReadonly(read);				                
				                textHelper.render(request, out);

				                textHelper.setNombre(AWLiquidacionRegistro.ID_SOLICITUD);
								textHelper.setCssClase(style);				                
				                textHelper.setId(AWLiquidacionRegistro.ID_SOLICITUD);
				                textHelper.setSize("15");
				                textHelper.setTipo("hidden");
								textHelper.setReadonly(read);				                
				                textHelper.render(request, out);
				            } catch (HelperException re) {
				                out.println("ERROR " + re.getMessage());
				            }

				            %>
						</td>
						<td width="50%">
							<a
								href="javascript:modificarLiquidacion('<%=AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION%>','<%=sYear%>','<%=sCirculo%>','<%=sProceso%>')"><img
								src="<%=request.getContextPath()%>/jsp/images/btn_validar.gif"
								name="Siguiente" width="139" height="21" border="0" id="Siguiente">
							</a>
						</td>



					</tr>
				</table>
				<br>
				<table border="0" width="100%" cellpadding="0" cellspacing="2"	id='display_modificacion_3' style='display:none'>

					<tr>
						<td>
						<hr class="linehorizontal">
						</td>
					</tr>

				</table>

				<table border="0" width="100%" cellpadding="0" cellspacing="2"	id="OCULTAR">

					<tr id='display_asociados_on' style='display:none ; cursor:hand'>
						<td width="100%"></td>
						<td width="16">
							<img id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0" onClick="javascript:desHabilitarAsociados()">
						</td>
						<img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
					</tr>

					<tr id='display_asociados_off' style='display:block; cursor:hand'>
						<td width="100%" align="right" class="campostip04">Haga click para agregar certificados asociados a un turno ya creado</td>
						<td width="16">
							<img img id="MAXIMIZAR"	src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0" onClick="javascript:habilitarAsociados()">
						</td>
						<img id="OCULTAR_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo">
					</tr>

				</table>
				
				<table width="100%" border="0" cellpadding="0" cellspacing="0" id='display_asociados_1' style='display:none'>
					<tr>
						
						<td	background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">
							Agregar certificados asociados
						</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" ></td>
					</tr>
				</table>

				<table width="100%" class="camposform" id='display_asociados_2' style='display:none'>
					<tr>
						<td width="20"><img	src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"	width="20" height="15"></td>
						<td width="20%">Agregar certificados asociados</td>
						<td width="50%">
							<a
								href="javascript:cambiarAccion('<%=AWLiquidacionRegistro.LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS%>')"><img
								src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif"
								name="Siguiente" width="139" height="21" border="0" id="Siguiente">
							</a>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<br>
				<table border="0" width="100%" cellpadding="0" cellspacing="2"	id='display_asociados_3' style='display:none'>

					<tr>
						<td>
						<hr class="linehorizontal">
						</td>
					</tr>

				</table>

				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
					<tr>
						
						<td
							background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Preliquidar</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<table width="100%" class="camposform">
					<tr>
						
						<td align="left">
							<table border="0" cellpadding="0" cellspacing="2" class="camposform">
							<tr>
								<td style="padding-left:20px;width:160px;">Preliquidar</td>
								<td><a href="javascript:cambiarAccion('<%=AWLiquidacionRegistro.COMENZAR_PRELIQUIDACION%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
							</tr>
							</table>
						</td>
					</tr>
				</table>
				<br>
				<br>
				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
					<tr>
						
						<td
							background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Subtipo de Solicitud</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>Subtipo</td>
						<td><% try {
							String stringSubtipo = (String) session.getAttribute(WebKeys.SUBTIPO_SOLICITUD);
               				subtipo.setTipos(subtipos);
               				subtipo.setNombre(WebKeys.SUBTIPO_SOLICITUD);
              			    subtipo.setCssClase("camposformtext");
              			    subtipo.setId(WebKeys.SUBTIPO_SOLICITUD);

              			    if(stringSubtipo!=null && read){
	              			    subtipo.setFuncion("onChange=\"this.value = '"+(stringSubtipo)+"'\"");              			    
              			    }
              			    
              			    //subtipo.setFuncion("onChange=\"agregarSubtipo('CARGAR_DOCUMENTOS')\"");
              			    //subtipo.setFuncion("onChange=\"guardarSubTipo(this.value)\"");
							subtipo.render(request,out);
						} catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%></td>
					</tr>
				</table>
				<br>
				<br>
				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
					<tr>
						<td	background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Nuevas entradas</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<table width="100%" class="camposform">
					
					<tr>
						<td>N&uacute;mero del Turno Anterior</td>
						<td width="165px">
						<%
						turnoAnterior = (String)session.getAttribute("TURNO_ANTERIOR");
						
	                    if (turnoAnterior==null || turnoAnterior.equals("")){%>
						<td><% try {
                                TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre("TURNO_ANTERIOR");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("TURNO_ANTERIOR");
                  			    textHelper.setFuncion("onFocus=\"javascript:cambiarAccionForm('VALIDAR_TURNO_ANTERIOR')\"");
								textHelper.setReadonly(read);                  			    
								textHelper.render(request,out);
                  			    textHelper.setFuncion("");
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="2"
							class="camposform">
							<tr>
								<td style="padding-left:20px;width:160px;">Asociar turno anterior</td>
								<td><a href='javascript:asociarTurno()'><img
									src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif"
									width="35" height="25" border="0"></a></td>
							</tr>
						</table>

						<%}else{%>
						<td>
							<% try {
                  				TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre("TURNO_ANTERIOR");
                  			    textHelper.setCssClase(style);
                  			    textHelper.setId("TURNO_ANTERIOR");
                  			    textHelper.setEditable(false);
								textHelper.setReadonly(read);
								textHelper.render(request,out);
								textHelper.setEditable(true);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
							<%if(tieneMatriculaAsociada){ %>
							<br><br>
								El turno anterior tenía asociada la matricula <%=turnoAnteriorObjeto.getIdMatriculaUltima() %>
							<%}%>
						</td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="2"
							class="camposform">
							<tr>
								<td>Eliminar turno anterior</td>
								<td><a
									href="javascript:cambiarAccion('ELIMINAR_TURNO_ANTERIOR')"><img
									src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif"
									width="35" height="25" border="0"></a></td>
							</tr>
						</table>
						<%}
                  %>
					</tr>
				</table>

<br />

<!-- sof:block turno-vinculado -->

<%

   final String PARAM_SOLICITUD_VINCULADA = "SOLICITUD_VINCULADA";


%>



<table width="100%" class="camposform">
 
 <tr>
  <td colspan="2">N&uacute;mero de Liquidación</td>
<%
   solicitudVinculada = (String)session.getAttribute( gov.sir.core.negocio.modelo.constantes.CTurno.SOLICITUD_VINCULADA );
  
%>

<%
if( ( null == solicitudVinculada )
  ||( "".equals( solicitudVinculada ) ) ) {
%>
<!-- JAlcazar 29/05/2009
     *Se modifico el TextHelper tornandose similar al definido en el proceso Cajero->Registro
     *Se Utiliza una nueva funcion JavaScript en el boton llamada cambiarAccion2 la cual tiene el mismo
     comportamiento del proceso Cajero->Registro
-->
  <td>
  L-<%=sYear%>-<%=sCirculo%>-<%=sProceso%>-
  <%

  // :turnovinculado: render the helper

  try {

    TextHelper textHelper = new TextHelper();
    textHelper.setNombre("AUXA");
    textHelper.setCssClase("camposformtext");
    textHelper.setId("AUXA");
    textHelper.setSize("15");
    textHelper.setTipo("text");
    textHelper.render(request, out);
    
                  }
  catch( HelperException re ){
    out.println( "ERROR " + re.getMessage() );
  }


  %>
  <!-- JAlcazar 29/05/2009
     *Script para limpiar el input AUXA
-->
  <script>
      if(document.all("AUXA")){
          document.all("AUXA").value = "";
      }

  </script>
  </td>

  <td align="right" rowspan="2">
    <table border="0" cellpadding="0" cellspacing="2" class="camposform">
      <tr>
              <td style="padding-left:20px;width:160px;">Asociar N&uacute;mero de Liquidación Para Registrar Pago</td>
              <td><a href="javascript:cambiarAccion2('<%=AWLiquidacionRegistro.BUSCAR_SOLICITUD_PAGO%>','<%=sYear%>','<%=sCirculo%>','<%=sProceso%>')"><img
                      src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif"
                      width="35" height="25" border="0" alt="" /></a></td>
      </tr>
    </table>
  </td>
<%
}
else {
%>
  <td>
  <%
  try {
    TextHelper textHelper = new TextHelper();
    textHelper.setNombre( PARAM_SOLICITUD_VINCULADA );
    textHelper.setCssClase(style);    
    textHelper.setId( PARAM_SOLICITUD_VINCULADA );
    textHelper.setEditable( false );
	textHelper.setReadonly(read);    
    textHelper.render( request, out );
    textHelper.setEditable( true );
  }
  catch( HelperException re ){
    out.println( "ERROR " + re.getMessage() );
  }
  %>
  </td>
  <td align="right">
    <table border="0" cellpadding="0" cellspacing="2" class="camposform">
      <tr>
	<td>Eliminar turno asociado</td>
        <td>
          <a href="javascript:jsEvent_VincularTurno_DelItem()">
           <img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" />
          </a>
        </td>
      </tr>
    </table>
<%
}
%>
 </tr>
</table>


<!-- eof:block -->











                <br>
				<br>
				<br>

				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>  <td width="20">&nbsp;</td> </tr>
					<tr>
						
						<td
							background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Asociar una Matr&iacute;cula</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br> <%if (matriculasAsociadas!=null && !matriculasAsociadas.isEmpty()){%>

				<table width="100%" class="camposform">
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif"
							width="20" height="15"></td>
						<td>Folios de esta solicitud</td>
					</tr>
					<tr>
						<td width="20">&nbsp;</td>
						<td><% try {
                      tablaHelper.setColCount(5);
                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
                      tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);

               		  tablaHelper.render(request, out);
                    }
                    catch(HelperException re){
                      out.println("ERROR " + re.getMessage());
                    }
                  %></td>
					</tr>
				</table>


				<table border="0" align="right" cellpadding="0" cellspacing="2"
					class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
							width="20" height="15" /></td>
						<td>Eliminar Seleccionadas</td>
						<td>
						

						<a href="javascript:eliminarMatriculas('ELIMINAR')"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif"
							width="35" height="25" border="0" /></a>

							
							
							</td>
					</tr>
				</table>			


				<%}%>


				<%if (turnosFolioMig!=null && !turnosFolioMig.isEmpty()){
				
				List turnosFolioMigString = new ArrayList();
				Iterator it = turnosFolioMig.iterator();
				while (it.hasNext()){
					TurnoFolioMig turnoFolioMig = (TurnoFolioMig)it.next();
					turnosFolioMigString.add(turnoFolioMig.getIdFolio());
				}
				session.setAttribute(WebKeys.LISTA_MATRICULAS_TURNO_FOLIO_MIG_STRING,turnosFolioMigString);
				%>
				<br>
				

				<table width="100%" class="camposform">
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif"
							width="20" height="15"></td>
						<td>Folios de esta solicitud</td>
					</tr>
					<tr>
						<td width="20">&nbsp;</td>
						<td><% try {
                      tablaHelper.setColCount(5);
                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_TURNO_FOLIO_MIG_STRING);
                      tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_PLAIN );
               		  tablaHelper.render(request, out);
                    }
                    catch(HelperException re){
                      out.println("ERROR " + re.getMessage());
                    }
                  %></td>
					</tr>
				</table>
				 <%}%>

				<P>&nbsp;</P>
				<table width="100%" class="camposform">
					<tr></tr>
					<tr>   
                                  
                                                <td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
                                                <td>Cantidad de Matr&iacute;culas a Agregar</td>
                                                <td>
                                                <%
                                                     TextHelper numMatAgr = new TextHelper();
                                                     numMatAgr.setId(AWLiquidacionRegistro.NUM_MAT_AGR);
                                                     numMatAgr.setNombre(AWLiquidacionRegistro.NUM_MAT_AGR);
                                                     numMatAgr.setCssClase("camposformtext");
                                                     numMatAgr.setSize("8");
                                                     numMatAgr.setFuncion(" onkeypress=\"return valideKey(event);\" "); 
                                                     numMatAgr.render(request, out);
                                                %>
                                                </td>
                                           
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>N&uacute;mero de Matr&iacute;cula</td>
						<td width="130px">
						<td><%=idCirculo%>-<input name="AGREGAR_MATRICULA_REGISTRO"
							id="AGREGAR_MATRICULA_REGISTRO" type="text"
							value=""   
							onFocus="campoactual('AGREGAR_MATRICULA_REGISTRO'); cambiarAccionForm('AGREGAR');"
							class="camposformtext"> <img id="AGREGAR_MATRICULA_REGISTRO_img"
							src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
							class="imagen_campo"></td>
							
							<td>
							    <table border="0" cellpadding="0" cellspacing="2" class="camposform">
					              <tr>
						          
						          <td>Agregar Matr&iacute;cula</td>
						          <td align="right">
						          <a href="javascript:agregarMatricula('AGREGAR')">
						          <img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0">
						          </a>
						          </td>
					              </tr>
				                 </table>
				            </td>
					</tr>
				</table>


				<!-- Opciones de matriculas sin migrar -->
				<%
					if(circulo!=null&&circulo.isProcesoMigracion()){
				%>

				<br> <%if (matriculasAsociadasMig!=null && !matriculasAsociadasMig.isEmpty()){%>

				<table width="100%" class="camposform">
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif"
							width="20" height="15"></td>
						<td>Folios <B>NO MIGRADOS</B> de esta solicitud</td>
					</tr>
					<tr>
						<td width="20">&nbsp;</td>
						<td><% try {
                      			tablaHelper.setColCount(5);
                      			tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);
                      			tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
					 			tablaHelper.setInputName(WebKeys.TITULO_CHECKBOX_ELIMINAR_SIR_MIG);
               		  			tablaHelper.render(request, out);
                    			}
                    			catch(HelperException re){
                      			out.println("ERROR " + re.getMessage());
                    			}
                  %></td>
					</tr>
				</table>


				<%if(!read){%>
				<table border="0" align="right" cellpadding="0" cellspacing="2"
					class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
							width="20" height="15" /></td>
						<td>Eliminar Seleccionadas</td>
						<td>
						

						<a href="javascript:eliminarMatriculas('ELIMINAR_MIG_INC')"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif"
							width="35" height="25" border="0" /></a>

							
							
							</td>
					</tr>
				</table>
				<%}}%>		


				<P>&nbsp;</P>
				<table width="100%" class="camposform">
					<tr></tr>
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>N&uacute;mero de Matr&iacute;cula <B>SIN MIGRAR</B></td>
						<td width="130px">
						<td><%=idCirculo%>-<input name="AGREGAR_MATRICULA_REGISTRO_MIG_INC"
							id="AGREGAR_MATRICULA_REGISTRO_MIG_INC" type="text"
							value="" <%=(read)?"readonly":""%>   
							onFocus="campoactual('AGREGAR_MATRICULA_REGISTRO_MIG_INC'); cambiarAccionForm('AGREGAR_MIG_INC');"
							class="<%=style%>"> <img id="AGREGAR_MATRICULA_REGISTRO_MIG_INC"
							src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
							class="imagen_campo"></td>
							
							<td>
							    <table border="0" cellpadding="0" cellspacing="2" class="camposform">
					              <tr>
						          
						          <td>Agregar Matr&iacute;cula</td>
						          <td align="right">
						          <a href="javascript:agregarMatriculaSinMigrar('AGREGAR_MIG_INC')">
						          <img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0">
						          </a>
						          </td>
					              </tr>
				                 </table>
				            </td>
					</tr>
				</table>

				<!--End Opciones de matriculas sin migrar-->
				
				<%
				}
				%>
				
				
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
				 	<br>  
					<br> 
					<br> 
					<tr>
						
						<td
							background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Datos Opcionales para Antiguo Sistema</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
		

				<table border="0" width="100%" cellpadding="0" cellspacing="2"
					id="OCULTAR">
					<%if(ocultarAS.equals("FALSE")){%>
					<tr>
						<td>
						<hr class="linehorizontal">
						</td>
					</tr>
					<tr>
						<td></td>
						<td width="16"><a
							href="javascript:ocultarAntiguoSistema('TRUE');campoactual('OCULTAR');"><img
							id="MINIMIZAR"
							src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif"
							width="16" height="16" border="0"></a></td>
						<img id="OCULTAR_img"
							src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
							class="imagen_campo">
					</tr>
					<%}else{%>
					<tr>
						<td align="right" class="campostip04">Haga click para maximizar
						los datos de Antiguo Sistema</td>
						<td width="16"><a
							href="javascript:ocultarAntiguoSistema('FALSE');campoactual('OCULTAR');"><img
							img id="MAXIMIZAR"
							src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif"
							width="16" height="16" border="0"></a></td>
						<img id="OCULTAR_img"
							src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
							class="imagen_campo">
					</tr>
					<%}%>
				</table>
				<!-- Mostrar helper de antiguo sistema --> <%if(ocultarAS.equals("FALSE")){%>

				<%
	                    AntiguoSistemaHelper ash = new AntiguoSistemaHelper();
	                    if(session.getAttribute("antiguoSistemaExistente")==null)
                			ash.setObtenerJSP(true);
	                    ash.setProperties(request);
	                    ash.setNLibroTipoAS(CDatosAntiguoSistema.LIBRO_TIPO_AS);
                        ash.setNLibroNumeroAS(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
                        ash.setNLibroPaginaAS(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
                        ash.setNLibroAnoAS(CDatosAntiguoSistema.LIBRO_ANO_AS);

                        ash.setNTomoNumeroAS(CDatosAntiguoSistema.TOMO_NUMERO_AS);
                        ash.setNTomoPaginaAS(CDatosAntiguoSistema.TOMO_PAGINA_AS);
                        ash.setNTomoMunicipioAS(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
                        ash.setNTomoAnoAS(CDatosAntiguoSistema.TOMO_ANO_AS);

                        ash.setNDocumentoFechaAS(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
                        ash.setNDocumentoTipoAS(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
                        ash.setNDocumentoNumeroAS(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
                        ash.setNDocumentoComentarioAS(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);

						ash.setNPrefijoOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS);
                        ash.setNIdDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
                        ash.setNNomDepartamento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
                        ash.setNIdMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
                        ash.setNNomMunicipio(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
                        ash.setNIdVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
                        ash.setNNomVereda(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
                        ash.setNIdOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
                        ash.setNNumOficina(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
                        ash.setNIdDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO);
                        ash.setNNomDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO);
                        ash.setNNumOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN);
                        ash.setNTipoNomOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN);
                        ash.setNTipoOficinaHiddenn(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN);
                        ash.setNIdOficinaHidden(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN);
						if(read){
							ash.setReadOnly(read);
						}
                    	ash.render(request, out);
					%> <!-- Fin Mostrar helper de antiguo sistema --> <%} else {%> <%}%>
				
				
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
				  	<br>    
					<br>  
					<br>  
					<tr>
						
						<td
							background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif"
							class="bgnsub">Comentarios (Opcionales)</td>
						<td width="16" class="bgnsub"></td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				
				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>Comentario</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><% try {
 		                        textArea.setNombre("COMENTARIO");
 		                        textArea.setCols("60");
 		                        textArea.setRows("5");
                  			    textArea.setCssClase(style);                  			    
                  			    textArea.setId("COMENTARIO");
								textArea.setReadOnly(read);                  			    
								textArea.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
					</tr>
				</table>
				<!--
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
				<!--
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Lista de Documentos Entregados </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>

              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_checkbox.gif" width="20" height="15"></td>
                  <td>Documentos Entregados </td>
                </tr>


                <% try {
	 		                    List tipos = (List) session.getAttribute("LISTA_SUBTIPO_CHEQUEO") ;
                   				if (tipos==null){
                   					tipos = new Vector();
                   				}
                   				docHelper.setTipos(tipos);
                  			    docHelper.setCssClase(style);                  			    
                  			    docHelper.setId("DOCUMENTOS_ENTREGADOS");
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>


              </table>
              -->
				<br>
				<br>
				<br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
					<tr>
						
						<td class="bgnsub">Encabezado del Documento</td>
						<td width="16" class="bgnsub"></td>
						<td width="15"><img name="sub_r1_c4"
							src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
							width="15" height="22" border="0" alt=""></td>
					</tr>
				</table>
				<br>
				<table width="100%" class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>Datos B&aacute;sicos</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
						<table width="100%" class="camposform">
							<tr>
								<td>Tipo</td>
								<td><% try {
                        		TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			    textHelper.setCssClase(style);
                  			    textHelper.setFuncion("onchange=\"javascript:cambiarValorTipoDocumento(this.value); cambiarAccionForm('LIQUIDAR_REGISTRO');\"");
                  			    textHelper.setId(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			    textHelper.setEditable(true);
                  			    textHelper.setReadonly(read);
								textHelper.render(request,out);
								textHelper.setFuncion("");
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
								<td><% try {
								String stringTipoEncabezado = (String) session.getAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);							
								
	 		                    List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
                  			    tipoEncabezado.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);
                   				tipoEncabezado.setOrdenar(false);
								tipoEncabezado.setTipos(tiposDoc);
                  			    tipoEncabezado.setNombre(CSolicitudRegistro.TIPO_ENCABEZADO);
								tipoEncabezado.setCssClase(style);                  			    
                  			    tipoEncabezado.setId(CSolicitudRegistro.TIPO_ENCABEZADO);
	              			    if(stringTipoEncabezado!=null && read){	              			    
									tipoEncabezado.setFuncion("onchange=\"this.value = '"+(stringTipoEncabezado)+"';\"");
	              			    }else{
		              			    tipoEncabezado.setFuncion("onchange=getElementById('ID_TIPO_ENCABEZADO').value=this.value;");
	              			    }                  			    
								tipoEncabezado.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
								<script>
                        	document.getElementById('ID_TIPO_ENCABEZADO').value=document.getElementById('TIPO_ENCABEZADO').value;
                        	if(document.getElementById('ID_TIPO_ENCABEZADO').value=='SIN_SELECCIONAR'){
                        		document.getElementById('ID_TIPO_ENCABEZADO').value='';
                        	} 
                                
                        </script>
								<td>N&uacute;mero</td>
								<td><% try {
                        		TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre(CSolicitudRegistro.ID_ENCABEZADO);
								textHelper.setCssClase(style);
                  			    textHelper.setId(CSolicitudRegistro.ID_ENCABEZADO);
                  			    textHelper.setReadonly(read);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
								<td>Fecha</td> 
								<td>
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td><% try {
                              	TextHelper textHelper = new TextHelper();
 		                        textHelper.setNombre(CSolicitudRegistro.CALENDAR);
								textHelper.setCssClase(style);
                  			    textHelper.setId(CSolicitudRegistro.CALENDAR);
                  			    textHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR+"');\" "
                                                    + " onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR+"')\"   onBlur=\"javascript:validarFecha()\"" );
                  			    textHelper.setReadonly(read);
								textHelper.render(request,out);
							}
								catch(HelperException  re){
								out.println("ERROR " + re.getMessage());
							}
						%></td>
										<td>
											<%if(!read){%><a
											href="javascript:NewCal('calendar','ddmmmyyyy',true,24)"><img
											src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
											alt="Fecha" width="16" height="21" border="0"
											onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>
											
											<%}%>&nbsp;
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>Oficina de Procedencia</td>
					</tr>

					<%String parametro1 = "HELPER_OFICINAS_NO_POPUP.jsp?soloLectura=true";
					  String parametro2 = "HELPER_OFICINAS_NO_POPUP.jsp";
					  String parametro = read ? parametro1 : parametro2;
					%>
					<!-- EL HELPER DE OFICINA EMPIEZA ACA -->
					<jsp:include page="<%=parametro%>" flush="true" />
					<!-- EL HELPER DE OFICINA TERMINA ACA -->








					<!--</table>-->

					<table width="100%" class="camposform">
										<br>
					<br>
					<br>
						<tr>
							<td width="20"><img
								src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
								width="20" height="15"></td>
							<td width="140"><a href="javascript:validarNumMatriculas()"><img
								src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif"
								name="Siguiente" width="139" height="21" border="0"
								id="Siguiente"></a></td>
							<td>&nbsp;</td>
						</tr>
					</table>



					<td width="11"
						background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>

					<tr>
						<td><img name="tabla_central_r3_c1"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif"
							width="7" height="6" border="0" alt=""></td>
						<td
							background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img
							src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
							width="15" height="6"></td>
						<td><img name="tabla_central_pint_r3_c7"
							src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif"
							width="11" height="6" border="0" alt=""></td>
					</tr>
				</table>



					</form>

				

				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			
		</table>
<%if(cargarMsg){%> 
<script>alert('<%=msgMatricula%>');</script>
<%}%>
		<script>
	matriculaFocus();
	validacion();
</script>
