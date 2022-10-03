<!--**
* @Autor: Edgar Lora
* @Mantis: 0013038
* @Requerimiento: 060_453
*-->
<%@page import="gov.sir.core.negocio.modelo.Folio"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio"%>
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica"%>
<%--
@author: Cesar Ramirez
@change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
Se añade la clase con las constantes CTipoAnotacion
--%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoAnotacion"%>
<%//Se cambió la importación de todo el paquete por las importaciones de las clases que se necesitan en la página.%>
<%//@page import="gov.sir.core.web.*"%>
<%@page import="gov.sir.core.web.acciones.registro.AWCalificacion"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRegistro"%>
<%@page import="gov.sir.core.web.helpers.registro.MatriculaCalifHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoImprimible" %>
<%@page import="gov.sir.core.negocio.modelo.CirculoImpresora" %>
<%@page import="gov.sir.core.negocio.modelo.TipoImprimible" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%//TODO
//Se cambió la importación de todo el paquete por las importaciones de las clases que se necesitan en la página.%>
<%//@page import="java.util.*" %>
<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<script language="javascript" src="<%=request.getContextPath()%>/jsp/plantillas/js/conXMLHTTP.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/jsp/plantillas/js/anotacionFolioXMLHTTP.js"></script>
<%

Object obj = (Object)session.getAttribute(WebKeys.APROBAR_CALIFICACION);
		
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
    SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();
    
	//HELPER PARA MOSTRAR
	MatriculaCalifHelper matHelper = new MatriculaCalifHelper();
	matHelper.setFolios(solicitud.getSolicitudFolios());

	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));
	java.util.Hashtable ht = (java.util.Hashtable) session.getAttribute(WebKeys.VALIDACION_APROBAR_CALIFICACION);
	matHelper.setValidacionAnotacionesTemporales(ht);
	
	TextAreaHelper helper = new TextAreaHelper();
   %>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
function cambiarAccion(text) {
   document.CALIFICACION.ACCION.value = text;
   document.CALIFICACION.submit();
}

function calificar(text) {
   document.CALIFICACION.ITEM.value = text;
   document.CALIFICACION.POSSCROLL.value='0';
   cambiarAccion('CALIFICAR_FOLIO');
}

function cerrar(text) {
   if(confirm("¿Esta seguro que desea cerrar los folios " + text +" ?")){
	   document.CALIFICACION.ITEM.value = text;
	   if(document.all.<%=AWCalificacion.RAZON_CERRAR_FOLIO%>.value!=""){
		   cambiarAccion('<%=AWCalificacion.CERRAR_FOLIO%>');	   
	   }else{
	   	   alert('Se debe ingresar una razón para el cierre del folio. Por favor ingresela en la opción que se encuentra en la parte inferior.');
	   }
	   
	}
}


function cerrarFolios() {
	if(document.getElementsByName("ckBoxCerrar") == null || document.getElementsByName("ckBoxCerrar").length <= 0){
		alert("no hay folios a cerrar");	
	}else{
		var sw = false;
		var elemento = document.getElementsByName("ckBoxCerrar");
		for(i =0; i<elemento.length; i++){
			if(elemento[i].checked == true){				
				sw = true;
				break;
			}
		}
		if(sw == true){
			if(confirm("¿Esta seguro que desea cerrar los folios ?")){
	   		   document.CALIFICACION.ITEM.value = "CERRAR_FOLIO";
	   	 	   if(document.all.<%=AWCalificacion.RAZON_CERRAR_FOLIO%>.value!=""){
		   		cambiarAccion('<%=AWCalificacion.CERRAR_FOLIO%>');	    
	   	         }else{
	   	            alert('Se debe ingresar una razón para el cierre del folio. Por favor ingresela en la opción que se encuentra en la parte inferior.');
	   	         }
			}
		}else{
			alert('Debe existir al menos un folio a CERRAR');
		}
	   
	}
}


function AprobarRechazarSeleccionar_todo(campo){

	var element = document.CALIFICACION.cbAprobar;		
	
   	if(element != null){   				
		var elemento = document.getElementsByName("ckBoxCerrar");
		if(elemento == null || !(elemento.length >0)){
			document.CALIFICACION.cbAprobar.checked = false;
			alert("no hay folios a cerrar")
		}	
   		if(element.checked){
   			for (i=0;i<elemento.length;i++){
				elemento[i].checked = true;	
			}
		}else{
			for (i=0;i<elemento.length;i++){
				elemento[i].checked = false;	
			}
		}
   	} 	
}




function finalizarCalificacion(text){
    <% 
       /**
        * @Autor: Edgar Lora
        * @Mantis: 0013038
        * @Requerimiento: 060_453
        */
       String mensaje = "";
       String mensajeAlerta = "";
       ValidacionesSIR validacionesSIR = new ValidacionesSIR();
       List folios = solicitud.getSolicitudFolios();
       for(int i = 0; i < folios.size(); i = i + 1){
            SolicitudFolio sf = (SolicitudFolio) folios.get(i);
            Folio f = sf.getFolio();
            String matricula = f.getIdMatricula();
            String lindero = null;
            if(validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)){
                lindero = validacionesSIR.getLinderoDeMatricula(matricula);
                /**
                 * @author: Cesar Ramirez
                 * @change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
                 * Se cambia el método de de validarLinderos por validarLinderosPrimeraAnotacionDerivada.
                 **/
                if (validacionesSIR.validarLinderosPrimeraAnotacionDerivada(matricula, CNaturalezaJuridica.NATURALEZA_OPCIONAL_PARA_VALIDAR_LINDEROS, CTipoAnotacion.DERIVADO)) {
                    String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                    if(lindero.indexOf(articulo) != -1){
                        int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                        if(lindero.length() - tamArticulo < 100){
                            if(mensaje.length() > 0){
                                mensaje = mensaje + "\\n";
                            }
                            mensaje = mensaje + "No se ha incluido la información de linderos para la matricula " + matricula;
                        }
                    }else{
                        if(mensajeAlerta.length() > 0){
                            mensajeAlerta = mensajeAlerta + "\\n";
                        }
                        mensajeAlerta = "El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto 'Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012', Porfavor no lo borre";
                    }
                }
            }
       }
       if(mensaje.length() > 0){
           mensaje = mensaje + "\\n ¿esta seguro que desea terminar la calificación?";
       }
       if(mensajeAlerta.length() <= 0 && mensaje.length() > 0){
       %>
            var message = new String( "<%=mensaje %>" );

            if(confirm(message)){
		cambiarAccion(text);
            }
        <%
        }else if(mensajeAlerta.length() > 0){
        %>
          alert("<%=mensajeAlerta %>");  
        <% 
        }else {
           %>
            var message = new String( "¿Esta seguro que desea terminar la calificación?" );

            if(confirm(message)){
		cambiarAccion(text);
            }
            <%
        }
        %>
} 

function crearFolio(text){
   document.CALIFICACION.action = 'crearfolio.do';
   document.CALIFICACION.ACCION.value = text;
   document.CALIFICACION.submit();
} 

function send(){
  // document.forma.submit()
}
</script>

<%
  Boolean aprobarCal = null;

  Boolean hayExcepcion=(Boolean)session.getAttribute(WebKeys.EXCEPCION);
  if(hayExcepcion==null){
  	hayExcepcion= new Boolean(false);
  }
  
  Boolean hayExc = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
  if(hayExc!=null){
  	hayExcepcion= new Boolean(true);
  	session.removeAttribute(WebKeys.HAY_EXCEPCION);
  }
  
  if(!hayExcepcion.booleanValue()){
  	aprobarCal=(Boolean)session.getAttribute(WebKeys.APROBAR_CALIFICACION);
  	if (aprobarCal==null)
  	{
%>
<body bgcolor="#CDD8EA"  onLoad="send()"   background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <form action="calificacion.do" method="POST" name="forma"  id="forma">
  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWCalificacion.MOSTRAR%>" value="<%=AWCalificacion.MOSTRAR%>">
	</form>
<%
 	 }
   }
%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
 
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02">
    
    <!-- <input type="checkbox" onclick=""> -->

    <form action="calificacion.do" method="post" name="CALIFICACION" id="CALIFICACION">
    <input type="hidden" name="ACCION" value="CALIFICAR">

      
      
    <!--INICIO INSCRIPCIÓN -->  
      
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Inscripción de folios</td>
	                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                       </tr>
	                  </table></td>
	                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
	              </tr>
	            </table>
            </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>


        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

              <table width="100%">
                <tr>
                  <td>

                      <% try {
                  			    matHelper.render(request,out);
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
                  <td width="140">
                    <a href="javascript:crearFolio('<%=gov.sir.core.web.acciones.registro.AWCrearFolio.INICIO_CREAR_NUEVO_FOLIO%>')">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_crear_folio.gif" name="Siguiente" width="150" height="21" border="0" id="Siguiente"/>
                    </a>
                  </td> 
                  <td width="140">
                    <a href="javascript:cerrarFolios()">
                      <img src="<%=request.getContextPath()%>/jsp/images/btn_cerrar_folios.gif" name="Siguiente" width="150" height="21" border="0" id="Siguiente"/>
                    </a>
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
      
    <!--FIN INSCRIPCIÓN -->  

	<!--INICIO CERRAR FOLIO -->

	  <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Cerrar folios</td>
	                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                       </tr>
	                  </table></td>
	                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
	              </tr>
	            </table>
            </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>


        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">


              
              <table width="100%" class="camposform">
                <tr>
                	<td width="15%">Raz&oacute;n del cierre</td>
                    <td>
                    <% 
					try{
						helper.setCols("100");
						helper.setReadOnly(false);
						helper.setCssClase("camposformtext");
						helper.setId(AWCalificacion.RAZON_CERRAR_FOLIO);
						helper.setNombre(AWCalificacion.RAZON_CERRAR_FOLIO);
						helper.setRows("2");
						helper.render(request,out);
					}catch(HelperException re){
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
	
	<!--FIN CERRAR FOLIO -->    

    <!--INICIO TERMINAR -->  
	  <%
	  //aprobarCal=(Boolean)session.getAttribute(WebKeys.APROBAR_CALIFICACION);
	  //if( (aprobarCal!=null)&& (aprobarCal.booleanValue()) )
	  //{
	  %>    
      
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 
	          <table border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Terminar calificación</td>
	                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
	                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      </tr>
	                  </table></td>
	                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
	              </tr>
	            </table>
            </td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>


		<%
	 	    java.util.List foliosSinActualizarNomenclatura = (java.util.List)session.getAttribute(WebKeys.LISTA_FOLIOS_SIN_ACTUALIZACION_NOMENCLATURA);
			if(foliosSinActualizarNomenclatura!=null && foliosSinActualizarNomenclatura.size()>0){
		%>

		<tr>
			<td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
			
			<td valign="top" bgcolor="#79849B" class="tdtablacentral">
			
				<table width="100%" class="camposform">
					<tr>               
						<td width='80' align='center'><IMG src='<%= request.getContextPath()%>/jsp/images/mensaje_animated.gif' width='40' height='40'></td>
						
							<td>
								<table >               
									<tr>
										<td colspan='3'>&nbsp;</td>
									</tr> 		
									<%
										String matriculas = ""; 
										java.util.Iterator it = foliosSinActualizarNomenclatura.iterator();
										while(it.hasNext()){
											String idMatricula = (String)it.next();										
											matriculas = matriculas + idMatricula + ", ";
										}
										if(matriculas.length()>2){
											matriculas = matriculas.substring(0, (matriculas.length() - 2));
										}
									%>										
									<tr>
										<td>&nbsp;</td>
										<td colspan='3' align='left'> <SPAN class='botontextualRojo'><FONT  size='2'><b>No se ha actualizado la nomenclatura para los folios : <%=matriculas%>.&nbsp;&nbsp;</b></FONT></SPAN></td>
										<td>&nbsp;</td>                 
									</tr>
									<tr>
										<td colspan='3'>&nbsp;</td>
									</tr>                
								</table>
							</td>
						   
						<td width='60'>&nbsp;</td>
					</tr>          
				</table>			
			
			</td>
			
			<td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
		</tr>
        
        <%}%>


        <tr>
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">
              <table width="100%" class="camposform">
                <tr>
        			<td>Número de copias a imprimir:</td>
					<td>	
						<% 
							try {
								TextHelper textHelper = new TextHelper();
								textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
								textHelper.setSize("5");
								textHelper.render(request,out);
							}catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}	
						%>
					</td>
                </tr>
               	<tr>
        			<td>Seleccionar impresora:</td>
					<td>	
                   		<% 
                   			try { 
								//IDCIRCULO
								String idCirculo = "";
								if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
									idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
								}
		                   
	                            String key = WebKeys.LISTA_IMPRESORAS+"_"+idCirculo;
	 		                    java.util.List tipos = new ArrayList();
 		                    Map configuracion=(Map)session.getAttribute(key);
 		                    if (configuracion!=null){
 		                    Iterator itImpr=configuracion.keySet().iterator();
						 	while(itImpr.hasNext()){
								TipoImprimible impr=(TipoImprimible)itImpr.next();
								if (impr.getIdTipoImprimible().equals(CTipoImprimible.FORMULARIO_CALIFICACION)){
									List impresoras=(List)configuracion.get(impr);
									int j=0;
									Iterator itImpresoras=impresoras.iterator();
									
									while(itImpresoras.hasNext()){
										CirculoImpresora circImp=(CirculoImpresora)itImpresoras.next();
										Iterator itTipos=tipos.iterator();
										boolean agregar=true;
										
										while(itTipos.hasNext()){
											ElementoLista el=(ElementoLista)itTipos.next();
											if (el.getId().equals(circImp.getIdImpresora())){
												agregar=false;
												break;
											}
										}
										if (agregar){
											ElementoLista elem=new ElementoLista();
											elem.setId(circImp.getIdImpresora());
											elem.setValor(circImp.getIdImpresora());
											tipos.add(elem);
										}
									}
						 	    }
						 	}
 		                    }
 		                   java.util.Vector impresoras = new java.util.Vector();
 		                    
 		                    if(session.getAttribute("IMPRESORA") == null)
 		                    {
		 		            	session.setAttribute("IMPRESORA",request.getParameter("IMPRESORA") );
	 		                }


						    gov.sir.core.web.helpers.comun.ListaElementoHelper impresorasHelper = new gov.sir.core.web.helpers.comun.ListaElementoHelper();
 		        			impresorasHelper.setTipos(tipos);
              			    impresorasHelper.setNombre(WebKeys.IMPRESORA);
              			    impresorasHelper.setCssClase("camposformtext");
              			    impresorasHelper.setId(WebKeys.IMPRESORA);
              			
							impresorasHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						 }
						%>
					</td>
                </tr>  
                 <tr>
                    <td>Imprimir certificado de calificacion:</td>
                    <td>
			<input name='<%=WebKeys.ImprimirCC%>' type="checkbox" value='<%=WebKeys.ImprimirCC%>' > 
                    </td>
                </tr>
                <tr>
                  	<td>
                  		<br>
                  		
                    	<a href="javascript:finalizarCalificacion('<%=gov.sir.core.web.acciones.registro.AWCalificacion.CONFIRMAR%>')">                    
                      		<img src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif" name="Siguiente" border="0" id="Siguiente"/>
                    	</a>
                  	</td> 
                          <td>
              <a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?Calf=1','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_visualizar.gif" name="Folio" border="0" id="Folio"/>
             </a>
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
      
    <!--FIN TERMINAR -->  
      
      <%
      //}
	  session.removeAttribute(WebKeys.APROBAR_CALIFICACION);
      %>      
       <input type="hidden" name="POSSCROLL" id="POSSCROLL" value="">
    </form>
      



  </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
  
  
  
  
  
</table>
