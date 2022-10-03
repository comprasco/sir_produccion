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
<%@page import="gov.sir.core.negocio.modelo.constantes.CTabs"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import ="gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants"%>
<%@page import ="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CAccionFolioEditPadresHijos"%>
<%@page import="org.auriga.smart.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="org.auriga.core.web.HelperException"%>
<jsp:directive.page import="gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoTMP"/>


<%@taglib prefix="xRegionTemplate" uri="/xRegionTemplateTags" %>
<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<%-- privileges resources --%>
<!--CORRECCION_FOLIO_PADRES_HIJOS.jsp -->
  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/forms-lib.js" ></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/AnchorPosition.js" ></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/privileged/PopupWindow.js" ></script>
  <style type="text/css">      

	  .forms-help {
		 border-style: dotted;
		 border-width: 1px;
		 padding: 5px;
		 background-color:#FFFFC0; /* light yellow */
		 width: 200px; /* otherwise IE does a weird layout */
		 z-index:1000; /* must be higher than forms-tabContent */
	   }


  </style>



<%-- + + + + + + + + + + + + + + + + + + + + + + + +  --%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css"></link>
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
	
    TextAreaHelper textAreaHelper = new TextAreaHelper();

	Vector imagenesPadre = new Vector();
	Vector imagenesHijo = new Vector();



	Iterator ip = foliosPadre.iterator();

	while(ip.hasNext()){
		Folio folioId = (Folio)ip.next();
		String temp=(String) folioId.getIdMatricula();
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

	List foliosDerivadoHijo  = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO);
	List foliosDerHijosTmp=(List)session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO_TMP);
	if(foliosDerivadoHijo==null){
		foliosDerivadoHijo = new ArrayList();
	}

        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
	
	Iterator ih = foliosHijo.iterator();
	String anotacion;
	while(ih.hasNext()){
		Folio folioId =(Folio)ih.next();
		String temp=(String) folioId.getIdMatricula();
		anotacion="";
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
                                String orden = validacionesSIR.getAnotacionNtcnOrden(folioDerivado.getIdAnotacion(), folioDerivado.getIdMatricula());
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

<%

  String idCirculo = "";

%>

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%
  // Manejo de permisos de modificacion sobre la forma
  gov.sir.core.web.helpers.correccion.region.model.RegionManager pageLocalRegionManager = null;

  // recuperar valores de regionManager de la sesion
  pageLocalRegionManager = (gov.sir.core.web.helpers.correccion.region.model.RegionManager)session.getAttribute( "param_RegionManager" );

%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant" />
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user" />


<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- "panel de acciones / resources" --%>

  <!-- block:sof add effects lib -->
  <!-- @see: http://script.aculo.us/ -->

  <script src="<%=request.getContextPath()%>/jsp/plantillas/panels/prototype.js" type="text/javascript"  ></script>
  <script src="<%=request.getContextPath()%>/jsp/plantillas/panels/effects.js"   type="text/javascript"  ></script>
  <script src="<%=request.getContextPath()%>/jsp/plantillas/panels/dragdrop.js"  type="text/javascript"  ></script>

  <!-- block:eof -->

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- para manejo de formularios a traves de js                   --%>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script type="text/javascript">
// Libraries: FindObject BrowserCompatible

// Example: obj = findObj("image1");
function findObj(theObj, theDoc)
{
  var p, i, foundObj;

  if(!theDoc) theDoc = document;
  if( (p = theObj.indexOf("?")) > 0 && parent.frames.length)
  {
    theDoc = parent.frames[theObj.substring(p+1)].document;
    theObj = theObj.substring(0,p);
  }
  if(!(foundObj = theDoc[theObj]) && theDoc.all) foundObj = theDoc.all[theObj];
  for (i=0; !foundObj && i < theDoc.forms.length; i++)
    foundObj = theDoc.forms[i][theObj];
  for(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
    foundObj = findObj(theObj,theDoc.layers[i].document);
  if(!foundObj && document.getElementById) foundObj = document.getElementById(theObj);

  return foundObj;
}

</script>

<script type="text/javascript">
	// local form manipulation

   function LocalForm( formName ) {
     this.formName = formName;
   }
   LocalForm.prototype = new LocalForm();
   LocalForm.prototype.constructor = LocalForm;
   // Form.prototype.superclass = Object;

   LocalForm.prototype.submitForm = function() {
	 formObject = findObj( this.formName );
     formObject.submit();
   }

   LocalForm.prototype.setFormAction = function( formAction ) {
	 formObject = findObj( this.formName );
     formObject.action = formAction;
   }

   LocalForm.prototype.setValue = function( elementName, elementValue ) {
	 formObject = findObj( this.formName );

     if( formObject == null )
	   return;

     eval( "formObject."+ elementName + ".value" + "=" + ((null==elementValue)?("''"):("elementValue")) );
   }

</script>
<script type="text/javascript">
	// local form dependant resources

	var actionField = "<%=WebKeys.ACCION%>";

	var FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_ACTION = "<%=AwCorrecciones_Constants.FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_ACTION%>";
	var FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_ACTION = "<%=AwCorrecciones_Constants.FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_ACTION%>";
	var FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_ACTION  = "<%=AwCorrecciones_Constants.FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_ACTION%>";
	var FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_ACTION  = "<%=AwCorrecciones_Constants.FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_ACTION%>";

        // // //

        var QUERY_VER_FOLIO_CALIFICACION_FOLIO         = "<%=gov.sir.core.web.acciones.consulta.AWConsulta.CONSULTAS_FOLIOCORRECCION_DISPLAYBYID_ACTION%>";

</script>
<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>

<script type="text/javascript">


   function js_OnEvent( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     
     htmlForm.setValue("<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>",document.FOLIOEDIT_PADRESHIJOS_HIJO_SALVEDADES.<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>.value);
     htmlForm.setValue("<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION %>",document.FOLIOEDIT_PADRESHIJOS_HIJO_SALVEDADES.<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION %>.value);
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }

   function js_OnEventConfirm( formName, actionValue, msg ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );

     if( confirm( msg) ) {

       htmlForm.setValue("<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>",document.FOLIOEDIT_PADRESHIJOS_HIJO_SALVEDADES.<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>.value);
       htmlForm.setValue("<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION %>",document.FOLIOEDIT_PADRESHIJOS_HIJO_SALVEDADES.<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION %>.value);
       htmlForm.setValue( actionField, actionValue );
       htmlForm.submitForm();
       return true;
     }
     return void(0);

   }

   function js_OnEventQuery( formName, actionValue ) {

     var htmlForm;
     htmlForm = new LocalForm( formName );
     htmlForm.setValue( actionField, actionValue );
     htmlForm.submitForm();

   }


</script>

<script type="text/javascript">

   // colocar el valor referenciado en un campo oculto
   // para hacer la consulta

   // compatibility: ie

   function putValue( formItem ) {
      var formName = "CONSULTA";
      var hiddenFieldName = "<%=WebKeys.TITULO_CHECKBOX_ELIMINAR%>";

      var htmlForm;
      htmlForm = new LocalForm( formName );

      var obj = formItem;

      if( obj.checked ) {

         htmlForm.setValue( hiddenFieldName,  obj.value );
      }
      else {
         htmlForm.setValue( hiddenFieldName,  null );
      }

    }


</script>


<table border="0" cellpadding="0" cellspacing="0" width="100%">
      <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

      <tr>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral">



<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_FoliosMatricesMain"
  regionName="folios matrices"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

<form id="FOLIOEDIT_PADRESHIJOS_PADRE" name="FOLIOEDIT_PADRESHIJOS_PADRE" action="modificacion.do"  onsubmit="ed_checkForm(); return false;" method="post">

    <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= gov.sir.core.web.acciones.consulta.AWConsulta.VER_FOLIO_CALIFICACION_FOLIO%>" value="<%= gov.sir.core.web.acciones.consulta.AWConsulta.VER_FOLIO_CALIFICACION_FOLIO %>" />
    <input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" id="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" value="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>" />
    <input  type="hidden" name="<%= CTabs.TAB %>" id="<%= CTabs.TAB %>" value="<%= CTabs.TAB3 %>" />
    <input  type="hidden" name="<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>" id="<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>" />
    <input  type="hidden" name="<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION %>" id="<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION %>" />

      <!-- folios padre existentes -->


 <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
            <td class="bgnsub">Mayor extensi&oacute;n </td>
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
                      tablaHelper.setInputName( "FOLIOEDIT_PADRESHIJOS_PADRE__ITEMS" );

                      tablaHelper.setCheckboxComplementaryAttributes( "onclick='javascript:putValue(this);'" );
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
              <td>Sin mayor extensi&oacute;n</td>
            </tr>

          </table>
            <%}%>


      <!-- -->





  <!-- declarar panel de acciones -->


<!-- menu acciones -->


          <table width="100%" class="camposform">
            <tr>
              <td width="1">&nbsp;</td>
              <td valign="top" align="left" width="60">
                <div id="edmainpanel" style="width:100%;text-align:left;vertical-align:top;table-layout:inherit;" >
                   <a onclick="new Effect.SlideDown('edsubpanel');return false;" href="#" >AGREGAR/ELIMINAR</a>
                   <td width="1">&nbsp;</td>
                </div>
              </td>
              <td valign="top" align="left" width="60">
                  <div style="width:100%;text-align:left;vertical-align:top;table-layout:inherit;">
                    <a onclick="new Effect.SlideDown('demo_source'); return false;" href="#">ayuda</a>
                  </div>
              </td>
              <td valign="top" align="left" width="250">
                  <div id="demo_source" style="display: none; width:100%;" onclick="new Effect.SlideUp(this);">
                   <span style="width:100%;text-align:left;vertical-align:top;table-layout:inherit;font-size:xx-small;">
                   Use el panel desplegable para a�adir nuevos padres al folio,
                   o eliminar algunos de ellos mediante la seleccion de los check's de la parte superior.
                   </span>
                  </div>
              </td>
              <td >&nbsp;</td>
            </tr>
          </table>




  <!--
  <div id="edmainpanel"	>
     <a onclick="new Effect.SlideDown('edsubpanel');return false;" href="#" >AGREGAR/ELIMINAR</a>
  </div>
  -->

  <div id="edsubpanel" style="display:none;">
  	<div>

          <!-- contenidos del panel -->

          <table>
            <tr>
              <td colspan="2" class="camposform">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="2" width="20" valign="top" class="camposform" >

                <table>

                  <tr>

                    <td valign="top">
                <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15" alt=""/>
                    </td>

                    <td>

                <!-- conjunto de acciones -->



<%


  idCirculo = "";
  if ( null != request.getSession().getAttribute( WebKeys.CIRCULO ) ) {
          idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
          idCirculo = idCirculo + "-";
  }



%>


            <table>
              <tr>
                <td>


			 <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                           <tr>
                             <td width="20">
                                 <a href="javascript:js_OnEvent( 'FOLIOEDIT_PADRESHIJOS_PADRE' ,FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_ACTION);" >
                               <img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" alt="" />
                                 </a>
                             </td>
                             <td>Agregar</td>
                           </tr>
			 </table>

                </td>
                <td>


			 <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                           <tr>
                             <td width="20">
                                 <a href="javascript:js_OnEventConfirm( 'FOLIOEDIT_PADRESHIJOS_PADRE' ,FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_ACTION,'Esta seguro que desea eliminar la seleccion' );" >
                               <img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0" alt="" />
                                 </a>
                             </td>
                             <td>Eliminar</td>
                           </tr>
			 </table>


                </td>

              </tr>
              <tr>


<table width="100%" class="camposform">
               <tr>&nbsp;</tr>
               <tr>
                 <%--
                 <td width="20" valign="top">
                   <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15" alt=""/>
                 </td>
                 --%>

                 <td>

                   <table class="camposform" style="border:0px;">
                     <tr>
                      <td>
                   N&uacute;mero matricula:
                   <input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>" type="text" value="<%=idCirculo%>"  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>');" class="camposformtext" />
                   <img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />

                      </td>
                     </tr>
                     <tr>
                       <td>

                   N&uacute;mero anotacion:
                   <input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>" type="text" value=""  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>');" class="camposformtext" />
                   <img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />


                       </td>
                     </tr>

                     <tr>
                      <td>
                   Anotacion en este folio:
                   <input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID%>" type="text" value=""  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>');" class="camposformtext" />
                   <img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />

                      </td>
                     </tr>


                   </table>


                 </td>

                 <!--
                 <td>Identificacion de Matr&iacute;cula</td>
                 -->
              </tr>
</table>
                <!--
              </tr>
            </table>
            -->


                    </td>
                  </tr>
                </table>



              </td>

            </tr>
            <tr>
              <td colspan="2" class="camposform" >

                <table width="100%" class="camposform" style="border:0px;">
                  <tr>
                    <td>
            <!-- nombre del panel -->
            edicion de padres del folio seleccionado.

                    </td>
                    <td width="40">
            <!-- barra superior de acciones -->

            <a onclick="new Effect.SlideUp('edsubpanel'); return false;" href="#">close</a>
            <!-- block:sof "conjunto de items de la forma" -->
            <!--
            <input id="eddetails" name="details" type="checkbox" checked="checked" onchange="new Effect.SlideUp('edsubpanel');" />
            Show details (always active in demo)
            -->

                    </td>
                  </tr>
                </table>

              </td>
            </tr>
          </table>


          <!-- contenido de panel -->



		<!-- block:eof -->
  	</div>
  </div>

</form>
<script type="text/javascript" language="javascript">
      // <![CDATA[
      function ed_checkForm() {
        if(Form.Element.getValue('edsearch').length<3) {
          Effect.Shake('edmainpanel');
          Effect.Appear('edwarning',
            {duration:2.0, transition:Effect.Transitions.wobble});
        } else {
          if($("edsubpanel").style.display=="")
            Effect.SlideUp("edsubpanel");
          if($("edwarning").style.display=="")
            Effect.Fade("edwarning");
          Effect.SlideDown("edresults");
        }
      }
      // ]]>
</script>






<!-- -->









<!-- add matricula asociacion as padre -->
<%--
Default values for id-matricula
--%>



<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

		  <br />


<!-- TAG: BEGIN-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->

<xRegionTemplate:DisplayPoint
  regionId="htRgn_FoliosSegregadosMain"
  regionName="folios segregados"
  debugEnabled="false"
  displayExtraMessage="null,true,false"
  regionManager="<%=(gov.sir.core.web.helpers.correccion.region.model.RegionManager)pageLocalRegionManager%>"
>	<!--   -->

<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->

<form id="FOLIOEDIT_PADRESHIJOS_HIJO" name="FOLIOEDIT_PADRESHIJOS_HIJO" action="modificacion.do"  onsubmit="ed_checkForm2(); return false;" method="post">

    <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= gov.sir.core.web.acciones.consulta.AWConsulta.VER_FOLIO_CALIFICACION_FOLIO%>" value="<%= gov.sir.core.web.acciones.consulta.AWConsulta.VER_FOLIO_CALIFICACION_FOLIO %>" />
    <input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" id="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" value="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>" />
    <input  type="hidden" name="<%= CTabs.TAB %>" id="<%= CTabs.TAB %>" value="<%= CTabs.TAB3 %>" />
    <input  type="hidden" name="<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>" id="<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>" />
    <input  type="hidden" name="<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION %>" id="<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION %>" />
    

      <!-- folios hijo existentes -->

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
                      tablaHelper.setInputName( "FOLIOEDIT_PADRESHIJOS_HIJO__ITEMS" );
                      tablaHelper.setCheckboxComplementaryAttributes( "onclick='javascript:putValue(this);'" );
                      tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOXTEXTFIELD);
                      tablaHelper.setListName(WebKeys.LISTA_FOLIOS_HIJO+CFolio.ID_MATRICULA);
               		  tablaHelper.render(request, out);
                    }
                    catch(org.auriga.core.web.HelperException re){
                      out.println("ERROR " + re.getMessage());
                    }

                  %>
              </td>
            </tr>
  
                 <tr>
                   <td width="20">
                       <a href="javascript:js_OnEventConfirm( 'FOLIOEDIT_PADRESHIJOS_HIJO' ,'CAMBIAR_ANOTACIONES_CORRECCION','Desea cambiar las anotaciones de los folios seleccionados?');" >                       
                     <img src="<%=request.getContextPath()%>/jsp/images/btn_mini_agregar.gif" width="20" height="15" border="0" alt="" />
                       </a>
                   </td>
                   <td>Cambiar Anotacion</td>
                 </tr>            

          </table>
            <%}else{%>
          <table width="100%" class="camposform">
            <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td width="40">&nbsp;</td>
              <td>Folio sin segregados</td>
            </tr>

          </table>
            <%}%>







  <!-- declarar panel de acciones -->


<!-- menu acciones -->


          <table width="100%" class="camposform">
            <tr>
              <td width="20">&nbsp;</td>
              <td valign="top" align="left" width="60">
                <div id="edmainpanel" style="width:100%;text-align:left;vertical-align:top;table-layout:inherit;" >
                   <a onclick="new Effect.SlideDown('edsubpanel2');return false;" href="#" >AGREGAR/ELIMINAR</a>
                </div>
                <td width="1">&nbsp;</td>
              </td>
              <td valign="top" align="left" width="60">
                  <div style="width:100%;text-align:left;vertical-align:top;table-layout:inherit;">
                    <a onclick="new Effect.SlideDown('demo_source2'); return false;" href="#">ayuda</a>
                  </div>
              </td>
              <td valign="top" align="left" width="250">
                  <div id="demo_source2" style="display: none; width:100%;" onclick="new Effect.SlideUp(this);">
                   <span style="width:100%;text-align:left;vertical-align:top;table-layout:inherit;font-size:xx-small;">
                   Use el panel desplegable para a�adir nuevos hijos al folio,
                   o eliminar algunos de ellos mediante la seleccion de los check's de la parte superior.
                   </span>
                  </div>
              </td>
              <td>&nbsp;</td>
            </tr>
          </table>




  <!--
  <div id="edmainpanel"	>
     <a onclick="new Effect.SlideDown('edsubpanel');return false;" href="#" >AGREGAR/ELIMINAR</a>
  </div>
  -->

  <div id="edsubpanel2" style="display:none;">
  	<div>

          <!-- contenidos del panel -->

          <table>
            <tr>
              <td colspan="2" class="camposform">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="2" width="20" valign="top" class="camposform" >

                <table>

                  <tr>

                    <td valign="top">
                <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15" alt=""/>
                    </td>

                    <td>

                <!-- conjunto de acciones -->



<%


  idCirculo = "";
  if ( null != request.getSession().getAttribute( WebKeys.CIRCULO ) ) {
          idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
          idCirculo = idCirculo + "-";
  }



%>


            <table>
              <tr>
                <td>


			 <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                           <tr>
                             <td width="20">
                                 <a href="javascript:js_OnEvent( 'FOLIOEDIT_PADRESHIJOS_HIJO' ,FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_ACTION);" >
                               <img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" alt="" />
                                 </a>
                             </td>
                             <td>Agregar</td>
                           </tr>
			 </table>

                </td>
                <td>


			 <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                           <tr>
                             <td width="20">
                                 <a href="javascript:js_OnEventConfirm( 'FOLIOEDIT_PADRESHIJOS_HIJO' ,FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_ACTION,'Esta seguro que desea eliminar la seleccion' );" >
                               <img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0" alt="" />
                                 </a>
                             </td>
                             <td>Eliminar</td>
                           </tr>
			 </table>


                </td>

              </tr>
              <tr>


<table width="100%" class="camposform">
               <tr>&nbsp;</tr>
               <tr>
                 <%--
                 <td width="20" valign="top">
                   <img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15" alt=""/>
                 </td>
                 --%>

                 <td>

                   <table class="camposform" style="border:0px;">
                     <tr>
                      <td>
                   N&uacute;mero matricula:
                   <input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>" type="text" value="<%=idCirculo%>"  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>');" class="camposformtext" />
                   <img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />

                      </td>
                     </tr>
                     <tr>
                       <td>

                   N&uacute;mero anotacion:
                   <input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>" type="text" value=""  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>');" class="camposformtext" />
                   <img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />


                       </td>
                     </tr>

                     <tr>
                      <td>
                   Anotacion en este folio:
                   <input name="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID%>" id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID%>" type="text" value=""  onFocus="campoactual('<%=CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA%>');" class="camposformtext" />
                   <img id="<%=CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID%>_img" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo" alt="" />

                      </td>
                     </tr>


                   </table>


                 </td>

                 <!--
                 <td>Identificacion de Matr&iacute;cula</td>
                 -->
              </tr>
</table>
                <!--
              </tr>
            </table>
            -->


                    </td>
                  </tr>
                </table>



              </td>

            </tr>
            <tr>
              <td colspan="2" class="camposform" >

                <table width="100%" class="camposform" style="border:0px;">
                  <tr>
                    <td>
            <!-- nombre del panel -->
            edicion de hijos del folio seleccionado.

                    </td>
                    <td width="40">
            <!-- barra superior de acciones -->

            <a onclick="new Effect.SlideUp('edsubpanel2'); return false;" href="#">close</a>
            <!-- block:sof "conjunto de items de la forma" -->
            <!--
            <input id="eddetails" name="details" type="checkbox" checked="checked" onchange="new Effect.SlideUp('edsubpanel');" />
            Show details (always active in demo)
            -->

                    </td>
                  </tr>
                </table>

              </td>
            </tr>
          </table>


          <!-- contenido de panel -->



		<!-- block:eof -->
  	</div>
  </div>

</form>
<script type="text/javascript" language="javascript">
      // <![CDATA[
      function ed_checkForm2() {
        document.FOLIOEDIT_PADRESHIJOS_HIJO.<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>.value =  document.FOLIOEDIT_PADRESHIJOS_HIJO_SALVEDADES.<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>.value;      
        alert(document.FOLIOEDIT_PADRESHIJOS_HIJO.<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>.value);
        alert(document.FOLIOEDIT_PADRESHIJOS_HIJO_SALVEDADES.<%= AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID %>.value);
        if(Form.Element.getValue('edsearch2').length<3) {
          Effect.Shake('edmainpanel2');
          Effect.Appear('edwarning2',
            {duration:2.0, transition:Effect.Transitions.wobble});
        } else {
          if($("edsubpanel2").style.display=="")
            Effect.SlideUp("edsubpanel2");
          if($("edwarning2").style.display=="")
            Effect.Fade("edwarning2");
          Effect.SlideDown("edresults2");
        }
      }
      // ]]>
</script>






<!-- -->









<!-- add matricula asociacion as hijo -->
<%--
Default values for id-matricula
--%>












<!-- TAG: END-REGION -->
<!-- + + + + + + + + + + + + + + + + + + + + -->
</xRegionTemplate:DisplayPoint>
<!-- + + + + + + + + + + + + + + + + + + + + -->
<!-- END-TAG -->



<!-- formulario de consultas -->

          <form action="consultas.do" method="post" name="CONSULTA" id="CONSULTA">
            <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= gov.sir.core.web.acciones.consulta.AWConsulta.VER_FOLIO_CALIFICACION_FOLIO%>" value="<%= gov.sir.core.web.acciones.consulta.AWConsulta.VER_FOLIO_CALIFICACION_FOLIO %>" />
            <input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" id="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" value="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>" />
            <!-- input  type="hidden" name="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>" id="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA%>" value="<%= gov.sir.core.negocio.modelo.constantes.CTabs.TAB_CONSULTA_PADRE%>" /-->
            <input  type="hidden" name="<%= CTabs.TAB %>" id="<%= CTabs.TAB %>" value="<%= CTabs.TAB3 %>" />

            <%
              // para que no presente errores la pantalla de consulta
              String nombreFormaEdicionDatosEncabezado   = "NOMBREFORMAEDICIONDATOSENCABEZADO";
              String nombreAcccionEdicionCodigoCatastral = "NOMBREACCCIONEDICIONCODIGOCATASTRAL";
              String nombreFormaEdicionCodCatastral      = "NOMBREFORMAEDICIONCODCATASTRAL";
              String nombreFormaEdicionLinderos          = "NOMBREFORMAEDICIONLINDEROS";
              String nombreFormaEdicionDireccion         = "NOMBREFORMAEDICIONDIRECCION";

              session.setAttribute( "CORRECCIONES:PARAMETERS" , "CORRECCIONES:PARAMETERS" );
              session.setAttribute( "NOMBREFORMAEDICIONDATOSENCABEZADO"  , nombreFormaEdicionDatosEncabezado );
              session.setAttribute( "NOMBREACCCIONEDICIONCODIGOCATASTRAL", nombreAcccionEdicionCodigoCatastral );
              session.setAttribute( "NOMBREFORMAEDICIONCODCATASTRAL"     , nombreFormaEdicionCodCatastral );
              session.setAttribute( "NOMBREFORMAEDICIONLINDEROS"         , nombreFormaEdicionLinderos );
              session.setAttribute( "NOMBREFORMAEDICIONDIRECCION"        , nombreFormaEdicionDireccion );

              session.setAttribute( "VISTA_VOLVER", session.getAttribute( SMARTKeys.VISTA_ACTUAL ) );

            %>

                <hr class="linehorizontal" />


                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="50"><input type="text" id="<%=WebKeys.TITULO_CHECKBOX_ELIMINAR%>" name="<%=WebKeys.TITULO_CHECKBOX_ELIMINAR%>" class="campositem" readonly="true" /></td>
                    <td width="140">
                      <a href="javascript:js_OnEventQuery( 'CONSULTA' ,QUERY_VER_FOLIO_CALIFICACION_FOLIO );">
                        <img alt="" src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0" />
                      </a>
                    </td>
                    <td>&nbsp;</td>
                  </tr>
                </table>

          </form>
          

<form id="FOLIOEDIT_PADRESHIJOS_HIJO_SALVEDADES" name="FOLIOEDIT_PADRESHIJOS_HIJO_SALVEDADES" action=""  onsubmit="" method="post">
                    
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
    <td class="bgnsub">Salvedad Folio </td>
    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
    </tr>
	</table>
	<!--
    <span class="bgnsub" style="background:" >Salvedad: Folio </span>
    -->

    <table width="100%" class="camposform">
         <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <td>Salvedad</td>
          </tr>
              <tr>
                 <td width="20">&nbsp;</td>
                 <td>

                 <% // id

                 try {
                   TextHelper local_TextHelper;

                   local_TextHelper = new TextHelper();
                   local_TextHelper.setNombre(  AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID );
                   local_TextHelper.setId(      AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID );
                   local_TextHelper.setTipo( "hidden" );
                   local_TextHelper.setCssClase("camposformtext");
                   local_TextHelper.render( request, out );

                 }
                 catch( HelperException re ) {
		  out.println( "ERROR " + re.getMessage() );
                 }



                 %>
                 <% // text
                          try {
                                  textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=2' ");

 		                  textAreaHelper.setNombre(  AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION );
                  		  textAreaHelper.setId(      AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION );
 		                  textAreaHelper.setCols( "130" );
 		                  textAreaHelper.setRows( "2" );
                  	          textAreaHelper.setCssClase("camposformtext");
				  textAreaHelper.render(request,out);
			  }
                          catch( HelperException re ){
				  out.println("ERROR " + re.getMessage());
                          }
                 %>

                </td>
           </tr>
     </table>

</FORM>
          </td>

      </tr>

    </table>
