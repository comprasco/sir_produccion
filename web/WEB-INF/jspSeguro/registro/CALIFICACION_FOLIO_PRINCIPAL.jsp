<%@page import="org.auriga.core.web.*"%>
<%@page import="org.auriga.smart.SMARTKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*,
java.util.*,gov.sir.core.web.WebKeys,
gov.sir.core.web.acciones.registro.AWCalificacion,
gov.sir.core.web.acciones.consulta.AWConsulta,
gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones,
gov.sir.core.negocio.modelo.constantes.CFolio,
gov.sir.core.web.helpers.comun.*,
java.text.NumberFormat,
co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR"%>
<% 
	
	boolean esFolioMayorExtension=false;
	String vistaActual;
	
	MostrarFolioHelper mFolio= new MostrarFolioHelper();
	Folio f=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
	if(f==null){
	}
	
	Turno t = (Turno) session.getAttribute(WebKeys.TURNO);
	if(t==null){
	}
	
	//limpieza de valores del session
	request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
	
	Boolean temp=(Boolean)session.getAttribute(WebKeys.MAYOR_EXTENSION_FOLIO);
	if(temp!=null){
		esFolioMayorExtension = temp.booleanValue();	
	}
	
	//inicializacion Vista generadora
			
	String ultimaVista = (String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
	request.getSession().setAttribute(AWPaginadorAnotaciones.VISTA_ORIGINADORA, ultimaVista);
	vistaActual= ultimaVista;
	
	java.math.BigDecimal totalAnotaciones = (java.math.BigDecimal) request.getSession().getAttribute(WebKeys.TOTAL_ANOTACIONES);          	 	
	List gravamenes = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN);          	 	
	List medCautelares = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES); 
	List falsaTradicion = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION);
	List anotacionesInvalidas = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS);	
	List anotacionesPatrimonioFamiliar = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR);	
	List anotacionesAfectacionVivienda = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA);
        List historialAreas = (List) request.getSession().getAttribute(WebKeys.HISTORIAL_AREAS);
	//se quita el refresco de varios ciudadanos.
	request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);
	List dirTemporales = (List)request.getSession().getAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES);

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
        
    /**
     * @author: Cesar Ramirez
     * @change: 1245.HABILITAR.TIPO.PREDIO Método
     * A través de GeneralSir realiza la validación para habilitar el Tipo de Predio.
     **/
    boolean edicionTipoPredio = false;
    ValidacionesSIR validacionesSIR = new ValidacionesSIR();
    edicionTipoPredio = validacionesSIR.habilitarEdicionTipoPredio(f.getIdMatricula());    
%>
<script>
function cambiarAccion(text) {
       document.CALIFICAR.ACCION.value = text;
       document.CALIFICAR.POSSCROLL.value =(document.body ? document.body.scrollTop :0);				  				  									  				  						
       document.CALIFICAR.submit();
}
       
function eliminarFolio(text) {
       document.CALIFICAR.action = 'crearfolio.do';
       document.CALIFICAR.ACCION.value = text;
       document.CALIFICAR.submit();
}       

function crearFolio(text){
   document.CALIFICAR.action = 'crearfolio.do';
   document.CALIFICAR.ACCION.value = text;
   document.CALIFICAR.submit();
} 
       
function cambiarAccionCorreccion(text) {
       document.DIRECCION_FORM.ACCION.value = text;
       document.DIRECCION_FORM.submit();
       }   
       
function quitar(pos,accion){
	if(confirm("Esta seguro que desea eliminar este item ?")){
		document.DIRECCION_FORM.POSICION.value = pos-1;
		cambiarAccionCorreccion(accion);
	}
}

function f_scrollTop() {	
	window.scroll(0,<%=(request.getParameter("POSSCROLL")!= null && !request.getParameter("POSSCROLL").equals("")?request.getParameter("POSSCROLL"):"0")%>);		
}           

</script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Documento sin t&iacute;tulo</title>
    <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        <!--
        .Estilo2 {font-size: 11px; color: #3A414E; text-decoration: none; background-color: #FFFFFF; border-top: 1px solid #5D687D; border-right: 1px solid #5D687D; border-bottom: 1px solid #5D687D; border-left: 5px solid #6A7891; font-family: Verdana, Arial, Helvetica, sans-serif;}
        -->
    </style>
</head>
<body id="body1"  onload="f_scrollTop()">
	
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_error.png" fwbase="tabla_error.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    


    </tr>

    <tr>

    <td>&nbsp;</td>
    <td align="center" class="tdtablaanexa02">
  
   <%try{
   			//setear atributos del folio.
   			mFolio.setFolio(f);
   			
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
   			
   			mFolio.setMayorExtension(esFolioMayorExtension);
   			mFolio.setNombreAccionFolio("calificacion.do");
   			mFolio.setNombreAccionPaginador("paginadorAnotaciones.do");
   			mFolio.setNombreAncla("ancla");
   			mFolio.setNombreForma("PAGINADOR_ADENTRO");
   			mFolio.setNombreFormaFolio("FORMA_FOLIO");
   			mFolio.setNombreFormaPaginador("FORMA_PAGINADOR_FOLIO");
   			mFolio.setnombreNumAnotacionTemporal("NUM_A_TEMPORAL_CALIFICACION");
   			mFolio.setNombreOcultarAnotaciones("O_ANOTACIONES");
   			mFolio.setNombreOcultarFolio("O_FOLIO");
   			mFolio.setNombrePaginador(lla.getNombrePaginador());
   			mFolio.setNombreResultado(lla.getNombreResultado());
   			mFolio.setnombreNumPaginaActual(lla.getNombreNumPagina());
   			mFolio.setPaginaInicial(0);
   			mFolio.setVistaActual(vistaActual);
   			mFolio.setTotalAnotaciones(totalAnotaciones);
   			mFolio.setGravamenes(gravamenes);
   			mFolio.setMedCautelares(medCautelares);
   			mFolio.setFalsaTradicion(falsaTradicion);
   			mFolio.setAnotacionesInvalidas(anotacionesInvalidas);   
   			mFolio.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
   			mFolio.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);	
                        mFolio.setHistorialAreas(historialAreas);
   			//datos a mostrar encabezado
   			mFolio.MostrarAperturaFolio();
   			mFolio.MostrarCabidaLinderos();
   			mFolio.MostrarGravamenes();
            /**
             * @author: Cesar Ramirez
             * @change: 1245.HABILITAR.TIPO.PREDIO
             * Dibuja y muestra el campo de tipo del predio.
             **/
            mFolio.MostrarTipoPredio();
   			mFolio.MostrarDireccionInmueble();
   			mFolio.MostrarComplementacion();
   			mFolio.setMostrarHistorialEstados(true);
   			mFolio.setConsultarAnotacionesDefinitivas(true);
   			
   			mFolio.setDirTemporales(dirTemporales);
   			if(dirTemporales!=null && dirTemporales.size()>0){
   				mFolio.setTurno(t);
   			}   			
   			
   			
   			//Boolean habB1 = (Boolean)session.getAttribute(AWCalificacion.HABILITAR_EDICION_NOMENCLATURA);
   			//boolean habilitar1 = habB1 != null ? habB1.booleanValue(): false;
   			
   			Boolean habB2 = (Boolean)session.getAttribute(AWCalificacion.HABILITAR_EDICION_COD_CATASTRAL);
   			boolean habilitar2 = habB2 != null ? habB2.booleanValue(): false;
   			   			
   			Boolean habB3 = (Boolean)session.getAttribute(AWCalificacion.HABILITAR_EDICION_DIRECCION);
   			boolean habilitar3 = habB3 != null ? habB3.booleanValue(): false;

   			Boolean habB4 = (Boolean)session.getAttribute(AWCalificacion.HABILITAR_EDICION_LINDEROS);
   			boolean habilitar4 = habB4 != null ? habB4.booleanValue(): false;
                        
                        /**
                         * @author: Cesar Ramirez
                         * @change: 1245.HABILITAR.TIPO.PREDIO Método
                         * Envía el valor boleano para mostrar el combobox de habilitar la edición de Tipo de Predio.
                         **/
                        mFolio.setEdicionTipoPredio(edicionTipoPredio);
   			  			
   			mFolio.setEdicionDatosCodCatastral(habilitar2);
   			mFolio.setEdicionDatosDireccion(habilitar3);
   			mFolio.setEdicionDatosLinderos(habilitar4);
   			
   			mFolio.setNombreDoEdicionDatosEncabezado("calificacion.do");
   			mFolio.setNombreAcccionEdicionCodigoCatastral(AWCalificacion.ACTUALIZAR_FOLIO_COD_CATASTRAL);
   			mFolio.setNombreAccionEdicionCabidaYLinderos(AWCalificacion.ACTUALIZAR_FOLIO_CABIDA_LINDEROS);
            /**
             * @author: Cesar Ramirez
             * @change: 1245.HABILITAR.TIPO.PREDIO
             * Envía el nombre de la acción para actualizar el tipo del predio.
             **/
            mFolio.setNombreAccionEdicionTipoPredio(AWCalificacion.ACTUALIZAR_FOLIO_TIPO_PREDIO);
   			
   			mFolio.setNombreAccionEdicionDireccion(AWCalificacion.ACTUALIZAR_FOLIO_DIRECCIONES);   	
   			mFolio.setNombreAccionAgregarDireccion(AWCalificacion.AGREGAR_DIRECCION_CALIFICACION); 
   			mFolio.setNombreAccionEliminarDireccion(AWCalificacion.ELIMINAR_DIRECCION_CALIFICACION);
   			mFolio.setNombreAccionEliminarDireccionDefinitiva(AWCalificacion.ELIMINAR_DIRECCION_DEFINITIVA_CALIFICACION);
   			  			
   			/**
             * @author: Cesar Ramirez
             * @change: 1245.HABILITAR.TIPO.PREDIO
             * Define el nombre del formulario para editar y mostrar el tipo del predio.
             **/
            mFolio.setNombreFormaEdicionTipoPredio("TIPO_PREDIO_FORM");
            mFolio.setNombreFormaEdicionLinderos("LINDEROS_FORM");
   			mFolio.setNombreFormaEdicionCodCatastral("COD_CATASTRAL_FORM");
   			mFolio.setNombreFormaEdicionDireccion("DIRECCION_FORM");
   			
   			mFolio.render(request, out);
    }catch(HelperException re){
	 	out.println("ERROR " + re.getMessage());
	 			re.printStackTrace();
	}catch(Exception e){
		e.printStackTrace();
	}%>
	
	
    
    
    <td>&nbsp;</td>

    </tr>
    <tr>

    <td>&nbsp;</td>
    <td align="center" class="tdtablaanexa02">
    
    
    
			
    
			
			


     
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>
    <tr>
    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td align="left" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Nuevas Anotaciones </td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_anotacion.gif" width="16" height="21"></td>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_agregar_items.gif" width="16" height="21"></td>
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
 <form action="calificacion.do" method="post" name="CALIFICAR" id="CALIFICAR">
 <input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>" value="">
 <input type="hidden" name="<%=AWCalificacion.NUM_ANOTACION_TEMPORAL%>" id="<%=AWCalificacion.NUM_ANOTACION_TEMPORAL%>" value="">
    <table width="100%" class="camposform">
        <tr>
			
               
                
                <td align="center">
                	<a href="javascript:cambiarAccion('CREAR_ANOTACION')">
						<img src="<%=request.getContextPath()%>/jsp/images/btn_crear.gif" name="Siguiente" width="139" height="21" border="0" id="Siguiente" >
                 	</a>
                 </td>
                 
                 <td align="center">
                 	<a href="javascript:cambiarAccion('CANCELAR')">
                   		 <img src="<%=request.getContextPath()%>/jsp/images/btn_crear_cancelacion.gif" name="Siguiente" width="159" height="21" border="0" id="Siguiente">
                 	</a>
                 </td>
                 
                 <td>
	                <a href="copia.anotacion.escoger.folios.view">
	                    <img src="<%=request.getContextPath()%>/jsp/images/btn_copiar_anotacion.gif" name="Folio" width="150" height="21" border="0" id="Folio"/>
	                </a>
                 </td>    
                          
                 <td align="center">
                    <a href="javascript:cambiarAccion('<%=AWCalificacion.SEGREGACION_ANOTACION%>')">
                    <img src="<%=request.getContextPath()%>/jsp/images/btn_segregar.gif" name="Siguiente" width="150" height="21" border="0" id="Siguiente">
                    </a>
                 </td>
                 
                <td>
                    <a href="javascript:cambiarAccion('<%=AWCalificacion.ENGLOBAR%>')">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_englobar.gif" name="Englobar" width="150" height="21" border="0" id="Siguiente"/>
                    </a>
                </td>
                                  
        </tr>
        

        
    </table>
    <%
    	if(f!=null && !f.isDefinitivo()){

			List foliosPadre = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
			List foliosHijo  = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_HIJO);	
			if(foliosPadre==null){
				foliosPadre = new ArrayList();
			}
			if(foliosHijo==null){
				foliosHijo = new ArrayList();
			}	
			boolean mostrarEliminarFolio = true;
			
			Iterator itPadres = foliosPadre.iterator();
			while(itPadres.hasNext()){
				mostrarEliminarFolio = false;
				break;
			}	
			Iterator itHijos = foliosHijo.iterator();						
			if(mostrarEliminarFolio == true){
				while(itHijos.hasNext()){
					mostrarEliminarFolio = false;
					break;
				}				
			}
			
    %>
    <input type='hidden' name='<%=CFolio.ID_MATRICULA%>' value="<%=f.getIdMatricula()%>">
    <input type='hidden' name='<%=CFolio.ID_ZONA_REGISTRAL%>' value="<%=f!=null&&f.getZonaRegistral()!=null?f.getZonaRegistral().getIdZonaRegistral():""%>">
	<hr class="linehorizontal">
    <table width="100%" class="camposform">
        <tr>
             <td  align="left" width="20%">
              	<a href="javascript:cambiarAccion('IMPRIMIR_FOLIO_TEMPORAL')">
					<img src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" name="Siguiente" width="139" height="21" border="0" id="Siguiente" >
               	</a>
              </td>
              <%if(mostrarEliminarFolio){%>
             <td  align="left" width="20%">
              	<a href="javascript:eliminarFolio('ELIMINAR_FOLIO_TEMPORAL')">
					<img src="<%=request.getContextPath()%>/jsp/images/btn_eliminar.gif" name="Siguiente" width="139" height="21" border="0" id="Siguiente" >
               	</a>
              </td>
              <%}else{%>
             <td  align="left" width="20%">
				&nbsp;
              </td>              
              <%}%>              
             <td  align="left" colspan='3' width="60%">&nbsp;
              </td>
              
              
       </tr>
    </table>    
    <%}%>
    
    <input type="hidden" name="POSSCROLL" id="POSSCROLL" value="">
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
    <td>&nbsp;</td>
    
    </tr>
    
    </table>
</body>

</html>
