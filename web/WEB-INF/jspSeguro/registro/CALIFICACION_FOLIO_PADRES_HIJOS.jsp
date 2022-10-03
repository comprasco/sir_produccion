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
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista"%>

<!--CALIFICACION_FOLIO_PADRES_HIJOS.jsp -->
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<%
	session.setAttribute(WebKeys.VISTA_ORIGINADORA,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));		
	session.removeAttribute(WebKeys.RAZON_EXCEPCION);
        TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
	List foliosPadre = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
	List foliosHijo  = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_HIJO);	


	if(foliosPadre==null){
		foliosPadre = new ArrayList();
	}
	if(foliosHijo==null){
		foliosHijo = new ArrayList();
	}	

	List matriculasPadre = new ArrayList();
	List matriculasHijo = new ArrayList();
	
	Vector imagenesPadre = new Vector();	
	Vector imagenesHijo = new Vector();		
	
	List foliosDerivadoPadre  = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_PADRE);
	if(foliosDerivadoPadre==null){
		foliosDerivadoPadre = new ArrayList();
	}
	
	String MATRIZ = "Matriz ";
	int a = 1;
        /**
        *@Autor: Edgar Lora
        *@Mantis: 11599
        *@Requerimiento: 085_151
        */
        Collections.sort(foliosPadre, new ComparadorSegregacion());
	Iterator ip = foliosPadre.iterator();	
	int k = 0;
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
	while(ip.hasNext()){
		Folio folioId = (Folio)ip.next();

		String labelMatricula = "";
		String idMatricula ="";
		
		if(folioId.isDefinitivo()){
			labelMatricula= folioId.getIdMatricula();			
			idMatricula= folioId.getIdMatricula();	
			
			Iterator ihMatriz = foliosDerivadoPadre.iterator();
			boolean encontrado = false;						
			while(ihMatriz.hasNext() && !encontrado){
				FolioDerivado folioDerivado = (FolioDerivado) ihMatriz.next();
				if (folioDerivado.getIdMatricula().equals(labelMatricula)) {
                                        /**
                                        *@Autor: Edgar Lora
                                        *@Mantis: 11599
                                        *@Requerimiento: 085_151
                                        */
					String orden = validacionesSIR.getAnotacionNtcnOrden(folioDerivado.getIdAnotacion(), folioDerivado.getIdMatricula());
                                        if(orden != null){
                                            labelMatricula = labelMatricula + " : " + orden;
                                        }
					encontrado = true;
				}
			}					
		}else{
			labelMatricula= MATRIZ + a;
			a++;
			idMatricula= folioId.getIdMatricula();						
		}		

		ElementoLista elista = new ElementoLista();
		elista.setId(idMatricula);
		elista.setValor(labelMatricula);

		matriculasPadre.add(elista);

		Imagen img = new Imagen();
		img.setNombre("btn_mini_verdetalles.gif");
		img.setHeight("13");
		img.setWidth("35");
		img.setFuncion("verAnotacion('consultar.folio.do?POSICION="+k+"&RELACION_FOLIO=PADRE','Folio','width=900,height=450,scrollbars=yes','"+k+"')");
		imagenesPadre.add(img);
		k++;
		
	}
        /**
        *@Autor: Edgar Lora
        *@Mantis: 11599
        *@Requerimiento: 085_151
        */
        Collections.sort(matriculasPadre, new ComparadorSegregacion());
	session.setAttribute(WebKeys.LISTA_FOLIOS_PADRE+CFolio.ID_MATRICULA, matriculasPadre);	
	
	
	List foliosDerivadoHijo  = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO);
	if(foliosDerivadoHijo==null){
		foliosDerivadoHijo = new ArrayList();
	}
	
	String SEGREGADO = "Segregado ";
	a = 1;
        /**
        *@Autor: Edgar Lora
        *@Mantis: 11599
        *@Requerimiento: 085_151
        */
        Collections.sort(foliosHijo, new ComparadorSegregacion());
	Iterator ih = foliosHijo.iterator();
	int l = 0;	
	while(ih.hasNext()){
		Folio folioId =(Folio)ih.next();
		
		String labelMatricula = "";
		String idMatricula ="";
		
		if(folioId.isDefinitivo()){
			labelMatricula= folioId.getIdMatricula();			
			idMatricula= folioId.getIdMatricula();
			
			Iterator ihDerivado = foliosDerivadoHijo.iterator();
			boolean encontrado = false;						
			while(ihDerivado.hasNext() && !encontrado){
			FolioDerivado folioDerivado = (FolioDerivado) ihDerivado.next();
			if (folioDerivado.getIdMatricula1().equals(labelMatricula)) {
                                /**
                                *@Autor: Edgar Lora
                                *@Mantis: 11599
                                *@Requerimiento: 085_151
                                */
                                String orden = validacionesSIR.getAnotacionNtcnOrden(folioDerivado.getIdAnotacion(), folioDerivado.getIdMatricula());
                                if(orden != null){
                                    labelMatricula = labelMatricula + " : " + orden;
                                }				
				encontrado = true;
			}
		}	
			
			
			
			
		}else{
			labelMatricula= SEGREGADO + a;
			a++;
			idMatricula= folioId.getIdMatricula();						
		}		

		ElementoLista elista = new ElementoLista();
		elista.setId(idMatricula);
		elista.setValor(labelMatricula);

		matriculasHijo.add(elista);
		
		Imagen img = new Imagen();
		img.setNombre("btn_mini_verdetalles.gif");
		img.setHeight("13");
		img.setWidth("35");
		img.setFuncion("verAnotacion('consultar.folio.do?POSICION="+l+"&RELACION_FOLIO=HIJO','Folio','width=900,height=450,scrollbars=yes','"+l+"')");
		imagenesHijo.add(img);
		l++;
	}	
	/**
        *@Autor: Edgar Lora
        *@Mantis: 11599
        *@Requerimiento: 085_151
        */
	Collections.sort(matriculasHijo, new ComparadorSegregacion());
	session.setAttribute(WebKeys.LISTA_FOLIOS_HIJO+CFolio.ID_MATRICULA, matriculasHijo);			

%>
<script type="text/javascript">
function verAnotacion(nombre,valor,dimensiones,pos){
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}
</script>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
      <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

      <tr>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
        
          <form action="consultas.do" method="post" name="CONSULTA" id="CONSULTA">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= gov.sir.core.web.acciones.consulta.AWConsulta.VER_FOLIO_CALIFICACION_FOLIO%>" value="<%= gov.sir.core.web.acciones.consulta.AWConsulta.VER_FOLIO_CALIFICACION_FOLIO %>">
            <input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" id="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" value="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>">
			<input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>" id="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>" value="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>">            
        
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
                      //tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
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
            <br>
			<!--<table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td width="40">&nbsp;</td>
              <td>Sin mayor extensión</td>
            </tr>

          </table>-->
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
                      tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_PLAIN);
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
		<br> 
		<!--<table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td width="40">&nbsp;</td>
              <td>El folio no tiene folios segregados</td>
            </tr>

          </table>-->            
          

            <%}%>              
                       
            </form>
          </td>

      </tr>

    </table>
