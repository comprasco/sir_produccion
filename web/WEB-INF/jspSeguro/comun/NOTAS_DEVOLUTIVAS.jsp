<%@page import = "java.util.*"%>
<%@page import = "gov.sir.core.web.helpers.comun.*"%>
<%@page import = "org.auriga.core.web.*"%>
<%@page import = "gov.sir.core.web.WebKeys"%>
<%@page import = "gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import = "gov.sir.core.negocio.modelo.*"%>
<%@page import = "gov.sir.core.web.acciones.comun.AWNotas"%>
<%
	ListaElementoHelper tiposNotaHelper = new ListaElementoHelper();
	ListaElementoHelper subtiposNotaHelper = new ListaElementoHelper();
	ListaElementoHelper tiposVisibilidadHelper = new ListaElementoHelper();
	ListarNotasPasadas notasHelper = new ListarNotasPasadas();
	notasHelper.setMostrarImpresion(true);
	Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
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
	
	
    List listaNotasDevolutivas = (List)session.getAttribute(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS);
    List elementosTipoNota = new Vector();
    List elementosSubTipoNota = new Vector();
    
    Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
    Boolean existeDevolutiva = (Boolean)session.getAttribute(WebKeys.VALIDACION_NOTA_DEVOLUTIVA);
    
    String idTipoNotaPadre = new String();
    String tipoNotaSeleccionada = request.getParameter("TIPONOTA");
    String idTipoNotaSeleccionada = null;
    
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

	/*if(tipoNotaSeleccionada!=null  &&  !tipoNotaSeleccionada.trim().equals("")){
	for(Iterator iter = listaNotasDevolutivas.iterator(); iter.hasNext(); ){
		TipoNota tipoNota  = (TipoNota)iter.next();
		if(tipoNota.getIdTipoNota().trim().equals(tipoNotaSeleccionada.trim())){
			idTipoNotaSeleccionada = tipoNota.getIdTipoNota();
			session.setAttribute(CTipoNota.NOMBRE_TIPO_NOTA, tipoNota.getNombre());
			session.setAttribute(CTipoNota.DESCRIPCION, tipoNota.getDescripcion());
			session.setAttribute(CTipoNota.DEVOLUTIVA, new Boolean(true));
			session.setAttribute(CTipoNota.ID_SUBTIPO_NOTA, tipoNota.getIdTipoNota());
			session.setAttribute(CTipoNota.ID_TIPO_NOTA, tipoNota.getIdTipoNota());
			session.setAttribute("NOTA_DESCRIPCION", tipoNota.getNombre());
			}
		}
	}*/


		subtiposNotaHelper.setCssClase("camposformtext");
		subtiposNotaHelper.setId(CTipoNota.ID_TIPO_NOTA);
		subtiposNotaHelper.setNombre(CTipoNota.ID_TIPO_NOTA);
		subtiposNotaHelper.setTipos(elementosSubTipoNota);
			
		tiposNotaHelper.setCssClase("camposformtext");
		tiposNotaHelper.setId(CTipoNota.ID_SUBTIPO_NOTA);
		tiposNotaHelper.setNombre(CTipoNota.ID_SUBTIPO_NOTA);
		tiposNotaHelper.setTipos(elementosTipoNota); 

	
	
	TextAreaHelper helper = new TextAreaHelper();
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script>
function cambiarDesc(){
		document.all.<%=CTipoNota.DESCRIPCION%>.value = document.all.<%=CTipoNota.ID_TIPO_NOTA%>.value;
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
function cambiarAccion(text){
	document.NOTAS_INF.ACCION.value=text
	document.NOTAS_INF.submit();
}
function imprimirNotaInformativa(posicion){
	document.NOTAS_INF.ACCION.value='<%=AWNotas.IMPRIMIR_NOTA_INFORMATIVA%>';
	document.NOTAS_INF.<%=WebKeys.POSICION%>.value=posicion;
	document.NOTAS_INF.submit();
}
var submit = 0;
function editarNotaDevolutiva(text,descripcion,posicion){
	if(submit<1){
		submit++;
		document.NOTAS_INF.ITEM.value = text;
		document.NOTAS_INF.ACCION.value='<%=AWNotas.AGREGAR_NOTA_DEVOLUTIVA_EDITADA%>';
		document.NOTAS_INF.DESCRIPCION_PADRE.value = descripcion;
		document.NOTAS_INF.<%=WebKeys.POSICION%>.value=posicion;
		document.NOTAS_INF.submit();
	}
}
function imprimirNotaDevolutiva(text){
	document.NOTAS_INF.ACCION.value='<%=AWNotas.IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE%>';
	document.NOTAS_INF.ITEM.value = text;
	document.NOTAS_INF.submit();
}
function enviarTurno(){
	if('<%=fase.getID()%>'=='<%=CFase.CAL_CALIFICACION%>'){
		document.FORMVALIDACION.action='calificacion.do';
		document.FORMVALIDACION.ACCION.value='<%=gov.sir.core.web.acciones.registro.AWCalificacion.DEVOLVER%>';
		document.FORMVALIDACION.RESPUESTAWF.value = '<%=gov.sir.core.web.acciones.registro.AWCalificacion.DEVOLUCION%>';
		document.FORMVALIDACION.submit();
	}
}
function quitar(text,posicion) {
	   document.NOTAS_INF.ITEM.value = text;
	   document.NOTAS_INF.<%=WebKeys.POSICION%>.value=posicion;
	   cambiarAccion('ELIMINAR_NOTA_DEVOLUTIVA');
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
	<form name="FORMVALIDACION" id="FORMVALIDACION" action='' method='post'>
	    <input type="hidden" name="ACCION" value="">	
	    <input type="hidden" name="RESPUESTAWF" value="">	
	</form>


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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
    <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Ingresar 
            Nota Devolutiva </td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 
          <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Notas Devolutivas </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_notasagregar.gif" width="16" height="21"></td>
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
         <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=gov.sir.core.web.acciones.comun.AWNotas.AGREGAR_NOTA_DEVOLUTIVA%>">
         <input type="hidden" name="ID_TIPO_NOTA_PADRE" value="<%=idTipoNotaPadre%>">
         <input type="hidden" name="<%=WebKeys.POSICION%>" value="0">
          
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Nota</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_notas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <br>
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
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
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Escoger Subtipo Nota Devolutiva</td>
                  <td>
                  <input type="hidden" name="ID_TIPO_NOTA_PADRE" value="<%=idTipoNotaPadre%>">
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
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>				
					<td colspan='2'>
					<%
					try{
						helper.setCols("60");
						helper.setCssClase("campositem");
						helper.setId(CTipoNota.DESCRIPCION);
						helper.setNombre(CTipoNota.DESCRIPCION);
						helper.setRows("3");
						helper.setCols("105");
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
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
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
              
              <BR><hr class="linehorizontal"><BR>
            
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Causales de Devolución</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_notas.gif" width="16" height="21"></td>
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
				int posicion=0;
				while(it.hasNext()){
					Nota nota = (Nota)it.next();
					String id = nota.getTiponota().getDescripcion();
				%>
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>
                  
                  <!--
                  <input type="hidden" name="<%=WebKeys.NUMERO_COPIAS_IMPRESION%>" value="">
                  -->
                                    
					<%=(nota.getTiponota()!=null?nota.getTiponota().getDescripcion():"")%>
    			  <td align="center"><a href="javascript:quitar('<%=nota.getTiponota().getIdTipoNota()%>','<%=posicion%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" alt="Quitar" border="0"></a></td>
    			  <td width="40"><a href="javascript:imprimirNotaDevolutiva('<%=nota.getTiponota().getIdTipoNota()%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_imprimir.gif" width="35" height="13" border="0" alt="Imprimir Nota Devolutiva"></a></td>
    			  <td width="40"><a href="javascript:editarNotaDevolutiva('<%=nota.getTiponota().getIdTipoNota()%>','<%=nota.getTiponota().getDescripcion()%>','<%=posicion%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0" alt="Editar Nota Devolutiva"></a></td>
                  </td>
                </tr>
                
                <%
                posicion++;
                }%>
                
              </table>    
              
              
              <%}%>          
              
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td>Número de copias a imprimir: 1</td>
                  <td><input type="hidden" size="5" class="campositem" value="1" id="<%=WebKeys.NUMERO_COPIAS_IMPRESION%>" name="<%=WebKeys.NUMERO_COPIAS_IMPRESION%>" /></td>
                  <input type="hidden" name="ITEM" value="">
                  <input type="hidden" name="DESCRIPCION_PADRE" value="">
                  
                  <td width="139"><a href="javascript:cambiarAccion('<%=AWNotas.GUARDAR_NOTAS_DEVOLUTIVAS%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_terminar.gif" width="139" height="21" border="0"></a></td>                  
    			  <td ><a href='javascript:regresar();'><img name="imageField" src="<%=request.getContextPath()%>/jsp/images/btn_salir.gif"   width="139" height="21" border="0" ></a></td>



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
</body>>