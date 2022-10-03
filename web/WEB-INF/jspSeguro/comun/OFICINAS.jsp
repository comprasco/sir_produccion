<%@page import ="gov.sir.core.web.helpers.comun.*,org.auriga.core.web.*"%>
<%@page import ="gov.sir.core.web.WebKeys"%>
<%
	SeleccionarOficinaHelper helper = new SeleccionarOficinaHelper();
	
	String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
	session.setAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL,vistaAnterior);
	String vManual= (String)request.getSession().getAttribute(WebKeys.OFICINA_HELPER_MANUAL);	

	String tipoOficina="tipo_oficina";
	String numeroOficina="numero_oficina";
	String tipoNomOficina="tipo_nom_oficina";
	String idOficina="id_oficina";
         /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
        String version = "version";
	
	boolean manual=false;
	if(vManual!=null){
		if(vManual.equals("true")){
				manual=true;
		}
	}
	if(manual){
		tipoOficina=(String)session.getAttribute(WebKeys.TIPO_OFICINA);
		numeroOficina=(String)session.getAttribute(WebKeys.NUMERO_OFICINA);
		tipoNomOficina=(String)session.getAttribute(WebKeys.TIPO_NOM_OFICINA);
		idOficina=(String)session.getAttribute(WebKeys.ID_OFICINA);
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                version=(String)session.getAttribute("VERSION");
	}
	
	
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
    <script language="JavaScript" type="text/JavaScript">
function submitform(){
	document.formulario.submit();
}
function visibilidad(nombre,ver){
    document.nombre.style.visibility=ver
}


<% if(manual){ %>
function carga(){
        if(opener.document.getElementById('<%=tipoOficina %>').value != '' && opener.document.getElementById('<%=tipoOficina %>').value != ''){
        document.formulario.tipo_00.value=opener.document.getElementById('<%=tipoOficina %>').value;
        document.formulario.numero_00.value=opener.document.getElementById('<%=numeroOficina %>').value;
        document.formulario.tipo_nom_00.value=opener.document.getElementById('<%=tipoNomOficina %>').value;
        document.formulario.id_oficina_00.value=opener.document.getElementById('<%=idOficina %>').value;
         /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
        document.formulario.version_00.value=opener.document.getElementById('<%=version %>').value;
    } else {
        carga();
    }
}
<% }else{ %>
function carga(){
        if(opener.document.getElementById('tipo_oficina').value != '' && opener.document.getElementById('numero_oficina').value != ''){
        document.formulario.tipo_00.value=opener.document.getElementById('tipo_oficina').value;
        document.formulario.numero_00.value=opener.document.getElementById('numero_oficina').value;
        document.formulario.tipo_nom_00.value=opener.document.getElementById('tipo_nom_oficina').value;
        document.formulario.id_oficina_00.value=opener.document.getElementById('id_oficina').value;
         /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
        document.formulario.version_00.value=opener.document.getElementById('version').value;
    } else {
        carga();
    }
}

<% } %>


function value_formulario(tipo,nombre,numero,idOficina){
    /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
    var i = idOficina.indexOf('-');
    var id = idOficina.substring(0,i);
    var version = idOficina.substring(i+1,idOficina.length);

    opener.document.getElementById(document.formulario.tipo_00.value).value=tipo;
    opener.document.getElementById(document.formulario.tipo_nom_00.value).value=nombre;
    opener.document.getElementById(document.formulario.numero_00.value).value=numero;
    opener.document.getElementById(document.formulario.id_oficina_00.value).value=id;
    opener.document.getElementById(document.formulario.version_00.value).value = version;

    window.close();
}

function MM_findObj(n, d) { 
    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
    if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_changeProp(objName,x,theProp,theValue) { //v6.0
    var obj = MM_findObj(objName);
    if (obj && (theProp.indexOf("style.")==-1 || obj.style)){
    if (theValue == true || theValue == false)
		eval("obj."+theProp+"="+theValue);
    else eval("obj."+theProp+"='"+theValue+"'");
    }
}
  </script>
</head>
<body onLoad="carga();">
    <form action="seleccionar.oficina.view" method="get" name="formulario" id="formulario">
    
    
    <tr>
    <td width="7">&nbsp;</td>
    <td width="7">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
       <%
		try{
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%>
    </td>
    <td width="11">&nbsp;</td>
    <td width="7">&nbsp;</td>
    </tr>
    <tr>
   
    <td width="7"></td>
    </tr>
    </table>
    </form>
</body>
</html>
