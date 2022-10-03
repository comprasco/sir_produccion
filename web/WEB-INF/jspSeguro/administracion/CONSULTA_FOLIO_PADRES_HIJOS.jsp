<%
/**
*@Autor: Edgar Lora
*@Mantis: 11599
*@Requerimiento: 085_151
*/%>
<%@page import="gov.sir.core.negocio.modelo.util.ComparadorSegregacion"%>
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>

<!--CONSULTA_FOLIO_PADRES_HIJOS.jsp -->
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%
	Folio f=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
	session.removeAttribute(WebKeys.RAZON_EXCEPCION);
	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));		

        TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
	List foliosPadre = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
	List foliosHijo  = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_HIJO);	
	
	List foliosDerivadoPadre = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_PADRE);
	List foliosDerivadoHijo  = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO);	


	if(foliosPadre==null){
		foliosPadre = new ArrayList();
	}
	if(foliosHijo==null){
		foliosHijo = new ArrayList();
	}	
	
	if(foliosDerivadoPadre==null){
		foliosDerivadoPadre = new ArrayList();
	}
	if(foliosDerivadoHijo==null){
		foliosDerivadoHijo = new ArrayList();
	}

	List matriculasPadre = new ArrayList();
	List matriculasHijo = new ArrayList();
	
	Vector imagenesPadre = new Vector();	
	Vector imagenesHijo = new Vector();		
	


	Iterator ip = foliosPadre.iterator();	

	List temp1 = new ArrayList();	
//	Iterator ip = temp1.iterator();	
	
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();

	while(ip.hasNext()){
	
		Folio folioId = (Folio)ip.next();
		String temp=(String) folioId.getIdMatricula();	
		Iterator ipDerivado = foliosDerivadoPadre.iterator();
		boolean encontrado = false;
		while(ipDerivado.hasNext() && !encontrado){
			FolioDerivado folioDerivado = (FolioDerivado) ipDerivado.next();
			if (folioDerivado.getIdMatricula().equals(temp)) {
                                /**
                                *@Autor: Edgar Lora
                                *@Mantis: 11599
                                *@Requerimiento: 085_151
                                */
                                String orden = "";
                                if(folioDerivado.getIdAnotacion() != null){
                                    orden = folioDerivado.getIdAnotacion();
                                }else{
                                    orden = validacionesSIR.getAnotacionNtcnOrden(folioDerivado.getIdAnotacion(), folioDerivado.getIdMatricula());
                                }
                                if(orden != null){
                                    temp = temp + " : " + orden;
                                }
				encontrado = true;
			}
			
		}
                matriculasPadre.add(temp);

		Imagen img = null;
		if(!folioId.isDefinitivo()){
			img = new Imagen();
			img.setNombre("ani_temporal.gif");
			img.setWidth("20");
			img.setHeight("15");
			img.setFuncion("");	
		}
		imagenesPadre.add(img);
		
	}
        /**
        *@Autor: Edgar Lora
        *@Mantis: 11599
        *@Requerimiento: 085_151
        */
        Collections.sort(matriculasPadre, new ComparadorSegregacion());
	session.setAttribute(WebKeys.LISTA_FOLIOS_PADRE+CFolio.ID_MATRICULA, matriculasPadre);	
	
	
	Iterator ih = foliosHijo.iterator();	
	while(ih.hasNext()){
		Folio folioId =(Folio)ih.next();
		String temp=(String) folioId.getIdMatricula();
		Iterator ihDerivado = foliosDerivadoHijo.iterator();
		boolean encontrado = false;
		while(ihDerivado.hasNext() && !encontrado){
			FolioDerivado folioDerivado = (FolioDerivado) ihDerivado.next();
			if (folioDerivado.getIdMatricula1().equals(temp)) {
                                /**
                                *@Autor: Edgar Lora
                                *@Mantis: 11599
                                *@Requerimiento: 085_151
                                */
                                String orden = "";
                                if(folioDerivado.getIdAnotacion() != null){
                                    orden = folioDerivado.getIdAnotacion();
                                }else{
                                    orden = validacionesSIR.getAnotacionNtcnOrden(folioDerivado.getIdAnotacion(), folioDerivado.getIdMatricula());
                                }
				if(orden != null){
                                    temp = temp + " : " + orden;
                                }
				encontrado = true;
			}
			
		}
                matriculasHijo.add(temp);
		
		Imagen img = null;
		if(!folioId.isDefinitivo()){	
			img = new Imagen();
			img.setNombre("ani_temporal.gif");
			img.setWidth("20");
			img.setHeight("15");
			img.setFuncion("");	
		}
		imagenesHijo.add(img);
	}	
        /**
        *@Autor: Edgar Lora
        *@Mantis: 11599
        *@Requerimiento: 085_151
        */
	Collections.sort(matriculasHijo, new ComparadorSegregacion());
	session.setAttribute(WebKeys.LISTA_FOLIOS_HIJO+CFolio.ID_MATRICULA, matriculasHijo);			

%>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
      <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

      <tr>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
        
          <form action="consultaFolio.do" method="post" name="CONSULTA" id="CONSULTA">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION%>" value="<%= gov.sir.core.web.acciones.administracion.AWConsultaFolio.CONSULTAR_FOLIO_ADMINISTRACION %>">
            <input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" id="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" value="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>">
        
        <table width="100%" class="camposform">
          <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td width="12"><img name="ind_turno" src="<%=request.getContextPath()%>/jsp/images/ind_turno.gif" width="20" height="15" border="0" alt=""></td>
            <td width="20" class="campositem">N&ordm;</td>
            <td class="campositem"><%=f.getIdMatricula()%></td>
          </tr>
        </table>
        <br> 
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td class="bgnsub">Mayor extensión </td>
            <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
            <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
          </tr>
        </table>

		  <%if(foliosPadre.size()>0){%>            
          <table width="100%" class="camposform">
          <tr>
            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
            <td>Matriculas</td>
            </tr>

			
          <tr>
            <td>&nbsp;</td>
            <td >
                  <% try {
                      tablaHelper.setColCount(5);
                      tablaHelper.setImagenes(imagenesPadre);
                      tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                      tablaHelper.setListName(WebKeys.LISTA_FOLIOS_PADRE+CFolio.ID_MATRICULA);
               		  tablaHelper.render(request, out);
                    }
                    catch(org.auriga.core.web.HelperException re){
                      out.println("ERROR " + re.getMessage());
                    }
                  %>
			</td>
          </tr>
        </table>
            <%}else{%>
          <table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td width="40">&nbsp;</td>
              <td>Sin mayor extensión</td>
            </tr>

          </table>            
            <%}%>    
            
		  <br>                
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
            <tr>
              <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
              <td class="bgnsub">Folios segregados</td>
              <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
              <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
            </tr>
          </table>
          
		  <%if(foliosHijo.size()>0){%>              
          <table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td>Matriculas</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td >
                  <% try {
                      tablaHelper.setColCount(5);
                      tablaHelper.setImagenes(imagenesHijo);
                      tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);                      
                      tablaHelper.setListName(WebKeys.LISTA_FOLIOS_HIJO+CFolio.ID_MATRICULA);
               		  tablaHelper.render(request, out);
                    }
                    catch(org.auriga.core.web.HelperException re){
                      out.println("ERROR " + re.getMessage());
                    }
                  %>
              </td>
            </tr>

          </table>
            <%}else{%>
          <table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td width="40">&nbsp;</td>
              <td>El folio no tiene folios segregados</td>
            </tr>

          </table>            
            <%}%>              
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0"></td>
                  <td>&nbsp;</td>
                </tr>
              </table>          
            </form>
          </td>

      </tr>

    </table>
