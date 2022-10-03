<%@page import="org.auriga.core.web.*" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.negocio.modelo.*" %>
<%@page import="java.util.*" %>
<%@page import="gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema" %>
<%@page import="gov.sir.core.web.helpers.comun.MostrarAntiguoSistemaHelper"%>
<%-- includes --%>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="org.apache.commons.jxpath.JXPathContext"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %>
<%-- + + + +  --%>
<%

	MostrarAntiguoSistemaHelper MASHelper= new MostrarAntiguoSistemaHelper();

	java.text.NumberFormat formato = java.text.NumberFormat.getInstance();
	MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
	String ocultarTurno = request.getParameter(WebKeys.OCULTAR_TURNO);
	if(ocultarTurno == null){
		ocultarTurno = (String)session.getAttribute(WebKeys.OCULTAR_TURNO);
		if(ocultarTurno==null){
			ocultarTurno = "FALSE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR_TURNO,ocultarTurno);
	}


	NotasInformativasHelper helper = new NotasInformativasHelper();

	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = turno.getSolicitud();
	Ciudadano ciudadano = solicitud.getCiudadano();
	List solFolios = solicitud.getSolicitudFolios();

	Turno turnoAnterior = solicitud.getTurnoAnterior();
	
	Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);
	 
%>


<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- mostrar si es correccion interna o externa (bug:0003584) --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<%



  local_SearchImpl_jx: {

            // :: local variables ----------------------------------------------
            Turno local_Turno;
            JXPathContext local_TurnoSearchContext;
            // // List< TurnoHistoria >
            List local_TurnoHistoria;
            Iterator local_TurnoSearchContextIterator;

            Boolean foundedElelementsCount; // elementos encontrados
            boolean evaluateNextRule; // flag para evaluar las reglas
            String local_TipoCorreccion;

            final String TIPO_CORRECCION_INTERNA = "INTERNA";
            final String TIPO_CORRECCION_EXTERNA = "EXTERNA";


            // initialize context & variables
            local_Turno = turno;
            local_TurnoHistoria = local_Turno.getHistorials(); // por el momento se observa el registro historico que tiene el turno
            local_TurnoSearchContext = JXPathContext.newContext( local_TurnoHistoria );
            local_TurnoSearchContext.setLenient( true );

            evaluateNextRule = true;
            local_TipoCorreccion = null;

            // declare jxpath variables
            local_TurnoSearchContext.getVariables().declareVariable( "local_ProcesoId"         , new Long( 0L ) );
            local_TurnoSearchContext.getVariables().declareVariable( "local_FaseId"            , "" );
            local_TurnoSearchContext.getVariables().declareVariable( "local_RespuestaId"       , "" );

            // -----------------------------------------------------------------
            // simple queries
            // apply first



            if( evaluateNextRule  ) {

              // ruleA: search-rule:
              //  que se haya iniciado siendo proceso de correcciones
              //  y que haya pasado por solicitud correccion;
              //  en este caso es externa

              String searchRule = "count( .[ (@idProceso = $local_ProcesoId) and (@fase = $local_FaseId ) ] ) > 0";

              local_TurnoSearchContext.setValue( "$local_ProcesoId"  , Long.valueOf( CProceso.PROCESO_CORRECCIONES ) );
              local_TurnoSearchContext.setValue( "$local_FaseId"     , "COS_SOLICITUD_CORRECCION"   );

              foundedElelementsCount = (Boolean)local_TurnoSearchContext.getValue( searchRule );
              evaluateNextRule = ( ( foundedElelementsCount.booleanValue() ) != true );

              local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_EXTERNA):(null);

            } // :if

            if( evaluateNextRule  ) {

              // ruleB: search-rule:
              // se escoge el ultimo elemento
              // del turno historia que sea la primera fase de antiguo sistema;
              // se observa el turno historia anterior
              // si la fase es de correcciones, sin importar el proceso, se coloca correccion interna.

              //String searchRule = "count( .[ (@fase = $local_FaseId ) ] ) > 0";
              // .[ ( @fase = 'ANT_REVISION_INICIAL' ) ] ) [last()] devuelve el ultimo nodo con fase antiguo sistema
              // preceding-sibling::*[1] busca el nodo inmediatamente anterior del contexto
              // [starts-with(@fase,'CORR')] busca si el nombre de la fase de ese nodo es de correcciones
              // count cuenta el conjunto de nodos que cumplen con esa regla
              // jxpath:
              // count( ( .[ ( @fase = 'ANT_REVISION_INICIAL' ) ] ) [last()] /preceding-sibling::*[1]/[ starts-with( @fase,'COR' ) ] ) > 0

              // jxpath no genera esta regla asi que se debe descomponer en 2:
              // 1. buscar la posicion del nodo de interes en el contexto
              // 2. escoger el anterior y hacer la consulta

              // forma 2: en el elemento se guarda la fase anterior:

              String searchRule = "count( ( .[ ( @fase = 'ANT_REVISION_INICIAL' ) ] )[last()][starts-with( @faseAnterior, 'COR' )] ) > 0";

              Object foundedElement;
              foundedElement = local_TurnoSearchContext.getValue( searchRule );

              foundedElelementsCount = (Boolean)foundedElement;
              evaluateNextRule = ( ( foundedElelementsCount.booleanValue() ) != true );

              local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_INTERNA):(null);

            } // :if



            // only informative display
            if( null != local_TipoCorreccion ) {

              request.setAttribute( "LOCAL_TIPO_CORRECCION", local_TipoCorreccion );

            } // if



  } // :searchImpl_jx


%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>


<%




            // rule0:
            // [<proceso>,<fase>,<avance>]

            // rule7: (todas las que inician sobre correcciones son internas; incluye actuaciones administrativas)
            //   [CORRECCIONES,COS_SOLICITUD_CORRECCION, *] -> externa
            //    // rule1: [CORRECCIONES,COS_SOLICITUD_CORRECCION, *] -> externa
            //    // rule5: [CORRECCIONES/ACTUACIONES_ADMINISTRATIVA,ACT_ESTUDIO_ANALISIS,ANTIGUO_SISTEMA] -> externa

            // rule8: (que se haya enviado a correcciones desde registro)

            // rule2:
            // [REGISTRO,REG_CONFRONTAR,REG_CONFRONTAR] -> null

            // rule3:
            // [REGISTRO/CALIFICACION,CAL_CALIFICACION,ANTIGUO_SISTEMA] -> null

            // rule4:
            // [CERTIFICADOS,CER_SOLICITUD,ANTIGUO_SISTEMA] -> null

            // CADENA DE BUSQUEDA
            // que haya pasado por correcciones
            // y ademas se haya redireccionado a antiguo sistema
            // A: count (fase local con antiguo sistema ) > 0 - - > A >= 1
            // B: count (turno ha pasado por correccion ) > 0 - - > B >= 1
            // A+B >=2

            //final String UNIQUE_SEARCH_RULE = "count( .[ (@idProceso = $local_ProcesoId) and (@fase = $local_FaseId ) and ( @respuesta = $local_RespuestaId ) ] ) > 0"
            //                                + " and "
            //                                + "count( .[ ( @fase = $static_FaseId ) ] ) > 0"; // and (@idProceso = $static_ProcesoId) (@fase = $static_FaseId )
            //; //"count( .[ (@idProceso = $local_ProcesoId) and (@fase = $local_FaseId]) and ( @respuesta = $local_RespuestaId ) ] )";



            // if( evaluateNextRule  ) {

            //  // rule 2:
            //  // proceso: REGISTRO
            //  //    fase: REG_CONFRONTAR
            //  //  avance: ANTIGUO_SISTEMA
            //  // (no debe salir nada; eliminar regla; si va desde registro hacia antiguo sistema, no sale nada)


            //  local_TurnoSearchContext.setValue( "$local_ProcesoId"  ,  Long.valueOf( CProceso.PROCESO_REGISTRO ) );
            //  local_TurnoSearchContext.setValue( "$local_FaseId"     , "REG_CONFRONTAR"   );
            //  local_TurnoSearchContext.setValue( "$local_RespuestaId", "ANTIGUO_SISTEMA"         );

            //  foundedElelementsCount = (Double)local_TurnoSearchContext.getValue( UNIQUE_SEARCH_RULE );

            //  evaluateNextRule = ( ( foundedElelementsCount.intValue() ) <= 0 );
            //  local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_INTERNA):(null);
            // } // :if

            // if( evaluateNextRule  ) {

            //   // rule 3:
            //   // proceso: REGISTRO/CALIFICACION
            //   //    fase: CAL_CALIFICACION
            //   //  avance: ANTIGUO_SISTEMA

            //   local_TurnoSearchContext.setValue( "$local_ProcesoId"  , Long.valueOf( CProceso.PROCESO_REGISTRO ) );
            //   local_TurnoSearchContext.setValue( "$local_FaseId"     , "CAL_CALIFICACION"   );
            //   local_TurnoSearchContext.setValue( "$local_RespuestaId", "ANTIGUO_SISTEMA"         );

            //   foundedElelementsCount = (Boolean)local_TurnoSearchContext.getValue( UNIQUE_SEARCH_RULE );
            //   evaluateNextRule = ( ( foundedElelementsCount.booleanValue() ) != true );

            //   local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_INTERNA):(null);

            // } // :if


            /*
            if( evaluateNextRule  ) {

              // rule 3:
              // proceso: REGISTRO/CALIFICACION -> CORRECCION -> antiguo sistema
              //    fase: CAL_CALIFICACION
              //  avance: CORRECCION

              local_TurnoSearchContext.setValue( "$local_ProcesoId"  , Long.valueOf( CProceso.PROCESO_REGISTRO ) );
              local_TurnoSearchContext.setValue( "$local_FaseId"     , "CAL_CALIFICACION"   );
              local_TurnoSearchContext.setValue( "$local_RespuestaId", "CORRECCION"         );


              String searchRule = UNIQUE_SEARCH_RULE;

              foundedElelementsCount = (Boolean)local_TurnoSearchContext.getValue(  );
              evaluateNextRule = ( ( foundedElelementsCount.booleanValue() ) != true );

              local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_INTERNA):(null);

            } // :if


            if( evaluateNextRule  ) {

              // rule 4:
              // proceso: CERTIFICADOS
              //    fase: CER_SOLICITUD
              //  avance: ANTIGUO_SISTEMA

              local_TurnoSearchContext.setValue( "$local_ProcesoId"  , Long.valueOf( CProceso.PROCESO_CERTIFICADOS ) );
              local_TurnoSearchContext.setValue( "$local_FaseId"     , "CER_SOLICITUD"   );
              local_TurnoSearchContext.setValue( "$local_RespuestaId", "ANTIGUO_SISTEMA"         );

              foundedElelementsCount = (Boolean)local_TurnoSearchContext.getValue( UNIQUE_SEARCH_RULE );
              evaluateNextRule = ( ( foundedElelementsCount.booleanValue() ) != true );

              local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_INTERNA):(null);

            } // :if

            if( evaluateNextRule  ) {

              // rule 5:
              // proceso: CORRECCIONES/ACTUACIONES_ADMINISTRATIVAS
              //    fase: ACT_ESTUDIO_ANALISIS
              //  avance: ANTIGUO_SISTEMA

              local_TurnoSearchContext.setValue( "$local_ProcesoId"  , Long.valueOf( CProceso.PROCESO_CORRECCIONES ) );
              local_TurnoSearchContext.setValue( "$local_FaseId"     , "ACT_ESTUDIO_ANALISIS"   );
              local_TurnoSearchContext.setValue( "$local_RespuestaId", "ANTIGUO_SISTEMA"         );

              foundedElelementsCount = (Boolean)local_TurnoSearchContext.getValue( UNIQUE_SEARCH_RULE );
              evaluateNextRule = ( ( foundedElelementsCount.booleanValue() ) != true );

              local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_INTERNA):(null);

            } // :if

            if( evaluateNextRule  ) {

              // rule 1:
              // proceso: CORRECCIONES
              //    fase: COR_REVISION_ANALISIS
              //  avance: ANTIGUO_SISTEMA

              local_TurnoSearchContext.setValue( "$local_ProcesoId"  , Long.valueOf( CProceso.PROCESO_CORRECCIONES ) );
              local_TurnoSearchContext.setValue( "$local_FaseId"     , "COR_REVISION_ANALISIS"   );
              local_TurnoSearchContext.setValue( "$local_RespuestaId", "ANTIGUO_SISTEMA"         );

              foundedElelementsCount = (Boolean)local_TurnoSearchContext.getValue( UNIQUE_SEARCH_RULE );
              evaluateNextRule = ( ( foundedElelementsCount.booleanValue() ) != true );

              local_TipoCorreccion = ( !evaluateNextRule )?(TIPO_CORRECCION_EXTERNA):(null);

            } // :if

//            // search matches
//            // apply first
//            local_TurnoSearchContext.setValue("$idFase", "COR_REVISAR_APROBAR");
//            local_TurnoSearchContextIterator = local_TurnoSearchContext.iterate(
//                ".[@fase = $idFase]");
//
//            for (; local_TurnoSearchContextIterator.hasNext(); ) {
//              TurnoHistoria element = (TurnoHistoria) local_TurnoSearchContextIterator.
//                  next();
//            }

            */

%>

<script type="text/javascript">
function checkscript()
{
	if (<%=!solFolios.isEmpty() %> )
	{
		var answer = confirm("Se desharán las hojas de ruta creadas; desea continuar?")
		
		if (answer)
		{
		  return true;
	    }
	    else
	    {
	    	return false;
	    }
	}
	else
	{
		return true;
	}
}
function cambiarAccion( text ) {
	alert('El turno corresponde al proceso de correcciones. No se puede crear otro turno. ');
    return void(0);
}

</script>



<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>
    <tr>
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Revisión Inicial</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                
            </tr>
        </table></td>
        <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table></td>
    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table border="0" align="right" cellpadding="0" cellspacing="2">
        <tr>
					<%if(ocultarTurno.equals("FALSE")){%>
        <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="TRUE">
            <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
        </form>

					<%}else{%>
        <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
            <input type="hidden" name="<%=WebKeys.OCULTAR_TURNO%>" value="FALSE">
            <td width="170" class="contenido">Haga click para maximizar</td>
            <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
        </form>
					<%}%>
        </tr>
    </table>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>

    <td valign="top" bgcolor="#79849B" class="tdtablacentral">


    <table width="100%" class="camposform">
        <tr>
            
            <td width="20" class="campositem">N&ordm;</td>
            <td class="campositem"><%=turno.getIdWorkflow()%></td>
        </tr>
    </table>

           <%if(ocultarTurno.equals("FALSE")){%>

    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
           
            <td class="bgnsub">Datos Turno</td>
            
            <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>


  <!--Descomentarear las siguientes líneas para el caso que se necesite dejar el tipo de certificado  en Antiguo sistema de Certificados-->
  <!--table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
        <tr>
            <td>Tipo de Certificado</td>
            <td>Cantidad</td	>
        </tr>
        <tr>
            <td class="campositem"><% //((TipoCertificado)liquidacion.getTipoCertificado()).getNombre()%></td>
            <td class="campositem"><% //solicitud.getNumeroCertificados()%></td>
        </tr>
    </table>
    <br-->


    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Certificado Anterior</td>
            
            
            <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>
    <table width="100%" class="camposform">
        <tr>
           
            <td>&iquest; Est&aacute; asociado a un turno anterior ?</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            
            <td>N&uacute;mero del Turno Anterior</td>
            <td class="campositem">
            <%if (turnoAnterior!=null){
            %><%=turnoAnterior.getIdWorkflow()%>
            <%}
            else {
            out.write("No hay turnos anteriores");
            }%>&nbsp;</td>
        </tr>
    </table>

    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
        
        <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Matr&iacute;cula Inmobiliaria de la Propiedad</td>
        
        
        <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>

    <!-- MOSTRAR LA LISTA DE LAS MATRICULAS Y NAVEGAR POR ELLAS -->
    <table width="100%" class="camposform">

					<%
					Iterator itSolFolios = solFolios.iterator();
					if(!solFolios.isEmpty()){
						while(itSolFolios.hasNext()){
							SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();%>
        <tr>
       
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem"><%=(solFolio.getFolio()).getIdMatricula()%></td>
        </tr>

						<% }%>
					<% }else{%>
        <tr>
        
        <td>N&uacute;mero de Matr&iacute;cula</td>
        <td class="campositem">No hay elementos que mostrar</td>
        </tr>
					<%}%>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            
            <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Adicionales </td>
           <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>


        <%
       		try{
      			MASHelper.setMostrarDocumento(true);
      			MASHelper.render(request,out);
      		}catch(HelperException re)
		    {
				re.printStackTrace();
				out.println("ERROR " + re.getMessage());
			}
        %>
    <br>

    <hr class="linehorizontal" />
       <%}%>



    <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
    <%

	String tipoCorreccion="EXTERNA";
	if(turnoPadre != null){
		tipoCorreccion="INTERNA";
	}



    %>


    <%

    if( null != tipoCorreccion && turno!=null && turno.getIdProceso()!=1 && turno.getIdProceso()!=6) {

    %>

      <table width="100%" class="camposform">
        <tr>
          <td width="20"><img alt="[]" src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15" /></td>
            <td>Tipo de correcci&oacute;n </td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>La corrección es </td>

            <td class="campositem"><%=tipoCorreccion%>&nbsp;</td>

          </tr>
        </table>

    <hr class="linehorizontal" />


    <%
    } // :if
    %>

    <%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>


















    <br>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
        
        <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Resultado Revisión Inicial</td>
        <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>
    <br>

     <table width="100%" class="camposform">
              <tr>
             
	          
	          <td width="150" align="center"><p>
	          Aprobar Revisión</p></td>
	         
	         <td width="150" align="center"><p>
	          <%if(turno!=null && turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {%>
	          	Enviar a Mayor valor</p></td>
			  <%}else{%>
	          	El folio existe</p></td>
	          <%}%>
				  <td width="150" align="center"><p>
	          Crear Turno Correción</p></td>



	        <table width="100%" class="camposform">
              <tr>
             
	          <form name="REVISION" method="post" action="antiguosistema.do">
	          <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWAntiguoSistema.AVANZAR_APROBAR_REVISION_INICIAL%>">
	          <td width="150" align="center"><p>
	          <input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aprobar.gif" width="139" height="21" border="0">
	          </p></td>
	          </form>
	          <%
	          	if (!(solicitud instanceof SolicitudCertificado)){
    	          	%>
    	        	  <form name="REVISION" method="post" action="antiguosistema.do">
			          <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWAntiguoSistema.AVANZAR_EXISTENTE_REVISION_INICIAL%>">
		              <td width="150" align="center"><p>
		              <input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_folio_existente.gif" width="139" height="21" border="0">
		              </p></td>
		              </form>
	          	<%} else {
	           %>
					  <!-- Redirigir a Mayor Valor -->
					  <%--
					  <form name="REVISION" method="post" action="antiguosistema.do">
			          <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWAntiguoSistema.AVANZAR_MAYOR_VALOR%>">
		              <td width="150" align="center"><p>
		              <input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mayor_valor.gif" width="139" height="21" border="0">
		              </p></td>
		              </form>
		              --%>
					  <td width="150" align="center"><p>
		              <a href="revision.inicial.mayor.valor.view"><img  src="<%=request.getContextPath()%>/jsp/images/btn_mayor_valor.gif" width="139" height="21" border="0" style=""></a>
		              </p></td>
				<%}%>

			  <form name="REVISION" method="post" action="antiguosistema.do">
	          <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWAntiguoSistema.AVANZAR_CORRECCIONES%>">
	          <td width="150" align="center"><p>
				<%
					long proceso=turno.getIdProceso();
					if(proceso!=3){%>
			          <input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_corregir.gif" width="139" height="21" border="0">
				<%	}else{   %>
					  Este turno es de correcciones
				<%} %>
	          </p></td>
	          </form>

              </tr>
          </table>

    <br>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
        
        <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Negar Revisión Inicial</td>
        <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
        </tr>
    </table>

	        <table width="100%" class="camposform">
	          <form name="REVISION" method="post" action="antiguosistema.do" 
			  onsubmit="return checkscript()"
	          >
	          <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWAntiguoSistema.AVANZAR_NEGAR_REVISION_INICIAL%>"
	          >


                  <tr>
                  <td>Motivo Negación</td>
                  <td align="center">
                   	<%
                   		try {
								TextAreaHelper area = new TextAreaHelper();
								area.setCols("70");
                  				area.setRows("10");
                  				area.setCssClase("camposformtext");
                  				area.setId(AWAntiguoSistema.COMENTARIO_NEGAR);
                  				area.setNombre(AWAntiguoSistema.COMENTARIO_NEGAR);
                                area.setReadOnly(false);
                  				area.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
				  </td>
                 </tr>

			  <br>
			  <br>
              <tr>
              <td>&nbsp;</td>
              <td align="center"><p>
              <input name="imageField52" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_negar.gif" width="139" height="21" border="0"
              >
              </p></td>
              </form>
              <td>&nbsp;</td>
              </tr>
          </table>



    </td>

    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    </tr>

    <tr>
    <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
    </tr>

</table>



             <%
		try{
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
		%>

      </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
 
</table>
