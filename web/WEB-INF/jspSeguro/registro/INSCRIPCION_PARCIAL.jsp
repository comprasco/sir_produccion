<!--**
* @Autor: Edgar Lora
* @Mantis: 0013038
* @Requerimiento: 060_453
*-->
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR"%>
<%@page import = "java.util.*"%>
<%@page import = "java.util.ArrayList"%>
<%@page import = "gov.sir.core.web.helpers.comun.*"%>
<%@page import = "gov.sir.core.web.helpers.registro.*"%>
<%@page import = "org.auriga.core.web.*"%>
<%@page import = "gov.sir.core.web.WebKeys"%>
<%@page import = "gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import = "gov.sir.core.negocio.modelo.*"%>
<%@page import = "gov.sir.core.web.acciones.comun.AWNotas"%>

<%
	ListaElementoHelper tiposNotaHelper = new ListaElementoHelper();
	ListaElementoHelper subtiposNotaHelper = new ListaElementoHelper();
	ListaElementoHelper tiposVisibilidadHelper = new ListaElementoHelper();
	MatriculaCalifHelper matriculasCalificadas = new MatriculaCalifHelper();
	Hashtable validacionAnotacionesTemporales;
	ListarNotasPasadas notasHelper = new ListarNotasPasadas();
	notasHelper.setMostrarImpresion(true);
	
    List listaNotasDevolutivas = (List)session.getAttribute(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS);
    List elementosTipoNota = new Vector();
    List elementosSubTipoNota = new Vector();
    
    Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
    Boolean existeDevolutiva = (Boolean)session.getAttribute(WebKeys.VALIDACION_NOTA_DEVOLUTIVA);
    String idTipoNotaPadre = new String();
    
    if(listaNotasDevolutivas==null ){
	    List listaNotasInformativas = (List)session.getAttribute(WebKeys.LISTA_TIPOS_NOTAS);    	
	    listaNotasDevolutivas = new ArrayList();
	    
	    Iterator it = listaNotasInformativas.iterator();
	    while(it.hasNext()){
	    	TipoNota tipoNota = (TipoNota)it.next();
	    	if(tipoNota.isDevolutiva()){
	    		listaNotasDevolutivas.add(tipoNota);
	    	}
	    }
	    
    }
    
    String tipoNota1 = (String)session.getAttribute(CTipoNota.ID_SUBTIPO_NOTA);
    
	
	if (listaNotasDevolutivas != null)
	{
	   Iterator itTipos = listaNotasDevolutivas.iterator();
	   
	   while (itTipos.hasNext()) {
	   		TipoNota tipoNota = (TipoNota) itTipos.next();

	   		String cadenaTipo= "";
	   		if(tipoNota!=null && tipoNota.getNombre()!=null && tipoNota.getNombre().length()>=4){
	   			cadenaTipo = tipoNota.getNombre().substring(0, 4);
	   		}

	   		String cadena="* * ";
	   		if(cadenaTipo.equals(cadena)){
	   			elementosTipoNota.add(new ElementoLista(tipoNota.getIdTipoNota(), tipoNota.getIdTipoNota() + " - " +tipoNota.getNombre()));
	   		}
	   }
	   
	   String idTipoNota= (String) session.getAttribute(CTipoNota.ID_SUBTIPO_NOTA);
	   String idSiguienteTipoNota= null;
         if(idTipoNota!=null){
                    try{
                      int limiteInferior = Integer.parseInt(idTipoNota);
                       
                      int limiteSuperior = 10000;
                      int distancia = limiteSuperior- limiteInferior;
                      Iterator itSiguiente= elementosTipoNota.iterator();
                      while(itSiguiente.hasNext()){
                                   ElementoLista temp= (ElementoLista)itSiguiente.next();
                                   if(temp.getId().matches("[+-]?\\d*(\\.\\d+)?") && temp.getId().equals("")==false){
                                        int limiteSuperiorTemp= Integer.parseInt(temp.getId());
                                         int distTemp=limiteSuperiorTemp-limiteInferior;
                                         if(distTemp<=0){
                                                 distTemp= 10000;
                                         }
                                         if(distTemp<distancia){
                                                 distancia= distTemp;
                                                 limiteSuperior=limiteSuperiorTemp;
                                         }
                                   }

                      }

                     idTipoNotaPadre = new String(""+limiteInferior);


                      itTipos = listaNotasDevolutivas.iterator();
                        while (itTipos.hasNext()) {
                                     TipoNota tipoNota = (TipoNota) itTipos.next();
                                        if(tipoNota.getIdTipoNota().matches("[+-]?\\d*(\\.\\d+)?") && tipoNota.getIdTipoNota().equals("")==false){
                                             String idSubtipoNota = tipoNota.getIdTipoNota();
                                             int subtiponota = Integer.parseInt(idSubtipoNota);
                                             if((limiteInferior < subtiponota) && (subtiponota < limiteSuperior)){
                                                     elementosSubTipoNota.add(new ElementoLista(tipoNota.getIdTipoNota(), tipoNota.getIdTipoNota() + " - " +tipoNota.getNombre()));
                                             }
                                       }	
                        }
                    }catch(NumberFormatException ex){
                               try{
                                                
                                                  itTipos = listaNotasDevolutivas.iterator();
                                                     while (itTipos.hasNext()) {
                                                                  TipoNota tipoNota = (TipoNota) itTipos.next();
                                                                  String idSubtipoNota = tipoNota.getIdTipoNota();
                                                                  String[] tiponota = idSubtipoNota.split("-");
                                                                    
                                                                  if(tiponota[0].equals(idTipoNota)){
                                                                      if(idSubtipoNota != idTipoNota){
                                                                          if(tiponota.length > 1){
                                                                            
                                                                              elementosSubTipoNota.add(new ElementoLista(tipoNota.getIdTipoNota(), tiponota[1] + " - " +tipoNota.getNombre()));
                                                                          }
                                                                      }   
                                                                 }
                                        }
                              }catch(Throwable e){
                                  
                              }   	
                     }catch(Throwable es){
                         
                     }
             }
           
	}
	
	subtiposNotaHelper.setCssClase("camposformtext");
	subtiposNotaHelper.setId(CTipoNota.ID_TIPO_NOTA);
	subtiposNotaHelper.setNombre(CTipoNota.ID_TIPO_NOTA);
	subtiposNotaHelper.setTipos(elementosSubTipoNota);
		
	tiposNotaHelper.setCssClase("camposformtext");
	tiposNotaHelper.setId(CTipoNota.ID_SUBTIPO_NOTA);
	tiposNotaHelper.setNombre(CTipoNota.ID_SUBTIPO_NOTA);
	tiposNotaHelper.setTipos(elementosTipoNota); 

	
	
	TextAreaHelper helper = new TextAreaHelper();


	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	List notasDevolutivasCalificacion = new ArrayList();
	
	Iterator iter = turno.getNotas().iterator();
	
	if(turno.getIdFase().equals(CFase.CAL_CALIFICACION)){
		while(iter.hasNext()){
			Nota nota = (Nota)iter.next();
			if(nota.getTiponota()!=null && nota.getTiponota().isDevolutiva()){
				notasDevolutivasCalificacion.add(nota);
			}
		}
		session.setAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION,notasDevolutivasCalificacion);
	}
	
	
	SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
    MatriculaViewHelper matViewHelper = new MatriculaViewHelper();        
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
	List solicitudFolios = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	Iterator is= solicitudFolios.iterator();
	
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();

		Folio folio = sf.getFolio();
		String labelMatricula = "";
		String idMatricula ="";
		
		if(folio.isDefinitivo()){
			labelMatricula= folio.getIdMatricula();			
			idMatricula= folio.getIdMatricula();						
		}else{
			//labelMatricula= folio.getNombreLote();
			labelMatricula= folio.getIdMatricula();
			idMatricula= folio.getIdMatricula();
		}			

		ElementoLista elista = new ElementoLista();
		elista.setId(idMatricula);
		elista.setValor(labelMatricula!=null?labelMatricula:"");
		
		idmatriculas.add(elista);
	}		
	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO,idmatriculas);

	//setear atributo para mostrar que uno esta en INSCRIPCION_PARCIAL
	session.setAttribute(AWNotas.RUTA_ESPECIAL , AWNotas.RUTA_INSCRIPCION_PARCIAL);

	//setear un atributo para mostrar que esta en haciendo una INSCRIPCION_PARCIAL
	session.setAttribute(AWNotas.FLAG_INSCRIPCION_PARCIAL , AWNotas.FLAG_INSCRIPCION_PARCIAL );
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script>
function cambiarDesc(){
	document.all.<%=CTipoNota.DESCRIPCION%>.value= document.all.<%=CTipoNota.ID_TIPO_NOTA%>.value;
}
function submitform(){
	document.NOTAS_INF.ACCION.value='<%=AWNotas.COLOCAR_DESCRIPCION_DEVOLUTIVA%>';
	document.NOTAS_INF.submit();
}

function buscarSubtipo(){
	document.NOTAS_INF.ACCION.value='<%=AWNotas.BUSCAR_SUBTIPO_DEVOLUTIVA%>';
	document.NOTAS_INF.submit();
}
function regresar(){
	document.NOTAS_INF.ACCION.value='<%=AWNotas.REGRESAR%>';
	document.NOTAS_INF.submit();
}
function imprimirNotaInformativa(posicion){
	document.NOTAS_INF.ACCION.value='<%=AWNotas.IMPRIMIR_NOTA_INFORMATIVA%>';
	document.NOTAS_INF.<%=WebKeys.POSICION%>.value=posicion;
	document.NOTAS_INF.submit();
}
function cambiarAccion(text){
	if(text == '<%=AWNotas.REGISTRAR_CALIFICACION_PARCIAL%>')
		alert("Se realizara un Registro Parcial de los folios seleccionados, si alguno de ellos no se encuentra en estado inscrito estos seran referenciados en la parte superior del formulario")
	document.NOTAS_INF.ACCION.value=text
	document.NOTAS_INF.submit();
}
function terminar(text){
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
                if(validacionesSIR.validarLinderos(matricula, CNaturalezaJuridica.NATURALEZA_OPCIONAL_PARA_VALIDAR_LINDEROS)){
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
           mensaje = mensaje + "\\n se realizara un Registro Parcial de los folios seleccionados, si alguno de ellos no se encuentra en estado inscrito estos seran referenciados en la parte superior del formulario";
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
            if(text == '<%=AWNotas.REGISTRAR_CALIFICACION_PARCIAL%>')
		alert("Se realizara un Registro Parcial de los folios seleccionados, si alguno de ellos no se encuentra en estado inscrito estos seran referenciados en la parte superior del formulario")
                document.NOTAS_INF.ACCION.value=text
                document.NOTAS_INF.submit();
            <%
        }
        %>
}
function enviarTurno(){
	if('<%=fase.getID()%>'=='<%=CFase.CAL_CALIFICACION%>'){
		document.FORMVALIDACION.action='calificacion.do';
		document.FORMVALIDACION.ACCION.value='<%=gov.sir.core.web.acciones.registro.AWCalificacion.INSCRIPCION_PARCIAL%>';
		document.FORMVALIDACION.RESPUESTAWF.value = '<%=gov.sir.core.web.acciones.registro.AWCalificacion.DEVOLUCION%>';
		document.FORMVALIDACION.submit();
	}
}
function validarEnvio(){
	<%if(existeDevolutiva!=null && existeDevolutiva.booleanValue()){ %>
		enviarTurno();
	<%
	}
	%>
}
</script>

<body onload='validarEnvio()'>

<%java.util.Hashtable ht = (java.util.Hashtable) session.getAttribute(WebKeys.MATRICULAS_INSCRITAS);
	ArrayList matriculasImp = (ArrayList)session.getAttribute(""+CSolicitudFolio.ESTADO_INSCRITO);
	if(matriculasImp == null)
	if(ht != null && ht.size() > 0){
		for(int i = 0; i < matriculasImp.size(); i++){
			String matricula = (String)matriculasImp.get(i);
			String estado = new String();
			if(ht.get(matricula).toString().equals("true"))
				estado = "INSCRITO";
			else
				estado = "INSCRITO TEMPORALMENTE";
			%>
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
			<td width="20">&nbsp;</td>
			<td width="12"><img src="/SIR/jsp/images/bullet_mal.gif" width="12" height="11" border="0"></td>
			<td class="error">La matricula: <%=matricula%> se encuentra en estado: <%=estado%></td>
			</tr>
			</table>
			<%
		}
	}
	session.removeAttribute(WebKeys.MATRICULAS_INSCRITAS);
	%>

	<form name="FORMVALIDACION" id="FORMVALIDACION" action='' method='post'>
	    <input type="hidden" name="ACCION" value="">	
	    <input type="hidden" name="RESPUESTAWF" value="">	
	</form>
	
	

<table width="100%" border="0" cellpadding="0" cellspacing="0">

  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Inscripci&oacute;n parcial de folios </td>
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
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"> 
          <br>

		 <form action="notas.do" method="post" name="NOTAS_INF" id="NOTAS_INF">
                    <!--
                    * @Autor: Edgar Lora
                    * @Mantis: 0013038
                    * @Requerimiento: 060_453
                    *
                    -->
                    <input type="hidden" name="INSCRIPCION_PARCIAL" value="INSCRIPCION_PARCIAL" />
                    <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.comun.AWNotas.AGREGAR_NOTA_DEVOLUTIVA%>">
                    <input type="hidden" name="ID_TIPO_NOTA_PADRE" value="<%=idTipoNotaPadre%>">
		


         
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td class="bgnsub">Nota Devolutiva</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <br>
              <table width="100%" class="camposform">
                <tr> 
                  <td>Escoger Tipo Nota Devolutiva</td>
                  <td>
					<% 
					try {
						tiposNotaHelper.setFuncion("onChange=\"buscarSubtipo();\"");
						tiposNotaHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
					%>
					</td>
					<td  rowspan="3"><input name="imageField2" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"> &nbsp;</td>					
				</tr>
				<tr> 
                  <td>Escoger Subtipo Nota Devolutiva</td>
                  <td>
					<% 
					try {
						subtiposNotaHelper.setFuncion("onChange=\"submitform();\"");
						subtiposNotaHelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
					%>
				  </td>
				  <td >&nbsp;</td>
				</tr>
				<tr>
                 	<td colspan="2">
					<%
					try{
						helper.setCols("105");
						helper.setCssClase("campositem");
						helper.setId(CTipoNota.DESCRIPCION);
						helper.setNombre(CTipoNota.DESCRIPCION);
						helper.setRows("3");
						helper.setReadOnly(true);
						helper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
					%>	
					</td>
					<td >&nbsp;</td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr> 
                  <td>Descripci&oacute;n</td>
                </tr>
                <tr> 
                  <td>&nbsp;</td>
                  <td>
					<% 
					try{
						helper.setCols("100");
						helper.setReadOnly(false);
						helper.setCssClase("camposformtext");
						helper.setId(CNota.DESCRIPCION);
						helper.setNombre(CNota.DESCRIPCION);
						helper.setRows("10");
						helper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
					%>
                  </td>
                </tr>
              </table>
			  <br>
			  
		 	<!-- Aqui se coloca la lista de matriculas de los folios asociados  al turno -->
		   	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Lista de matr&iacute;culas asociadas al turno</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <%try{
              		tablaHelper.setColCount(5);
                    tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
					tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                    tablaHelper.render(request, out);
              
				}catch(HelperException re){
					out.println("ERROR " + re.getMessage());
				}
			  %>  
              

			  <br>
			  
              <BR><hr class="linehorizontal"><BR>
            
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td class="bgnsub">Causales de Devolución</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
            

			  <%
            	List causalesDevolucion = (List)session.getAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION);
            	if(causalesDevolucion!=null && causalesDevolucion.size()>0){
            	
            	
            %>
            
              
			<table width="100%" class="camposform">
				<%
				Iterator it = causalesDevolucion.iterator();
				while(it.hasNext()){
					Nota nota = (Nota)it.next();
				%>
                <tr> 
                 <td>
					<%=(nota.getTiponota()!=null?nota.getTiponota().getDescripcion():"")%>
                  </td>
                </tr>
                
                <%}%>
                
              </table>    
              
              
              <%}%>       			  
			  

			  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Terminar inscripci&oacute;n parcial</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
			  <br>
              <table width="100%" class="camposform">
                   <tr>
                    <td>Imprimir inscripcion parcial</td>
                    <td>
			<input name='<%=WebKeys.ImprimirCC%>' type="checkbox" value='<%=WebKeys.ImprimirCC%>' > 
                    </td>
                </tr>
                <tr> 
                  <td>Número de copias a imprimir:</td>	
                  <td>
					<% 
					try{

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
                  
                  <td width="139"><a href="javascript:cambiarAccion('<%=AWNotas.REGISTRAR_CALIFICACION_PARCIAL%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_registrar.gif" width="139" height="21" border="0"></a></td>
                  <td width="139"><a href="javascript:terminar('<%=AWNotas.GUARDAR_NOTAS_DEVOLUTIVAS%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif" width="139" height="21" border="0"></a></td>
    			  <td width="139"><a href='javascript:regresar();'><img name="imageField" src="<%=request.getContextPath()%>/jsp/images/btn_salir.gif"   width="139" height="21" border="0" ></a></td>
                      <td width="139">
              <a target="popup"  onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?Calf=1','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_observar_formularioC.gif" name="Folio" border="0" width="139" height="21"  id="Folio"/>
             </a>
                  </td>
                    <%
            	if(causalesDevolucion!=null && causalesDevolucion.size()>0){	
            %>
                    <td>
              <a target="popup" onclick="window.open('<%=request.getContextPath()%>/servlet/PdfServlet?DevloIns=1','name','width=800,height=600')">
               <img src="<%=request.getContextPath()%>/jsp/images/btn_observar_notaD.gif" name="Folio1" border="0" width="139" height="21"  id="Folio1"/>
             </a>
                  </td>
  <%
            	}	
            %>
                </tr>
              </table>
		<!--<hr class="linehorizontal">              -->
		<% 
		/*try{
			notasHelper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}*/	
		%>
              
              
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
</body>
