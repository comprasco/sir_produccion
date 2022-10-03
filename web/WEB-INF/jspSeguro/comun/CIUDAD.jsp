<%@page import ="gov.sir.core.web.helpers.comun.*,org.auriga.core.web.*" %>
<%@page import ="gov.sir.core.negocio.modelo.constantes.CVereda" %>


<%
	SeleccionarLocacionHelper helper = new SeleccionarLocacionHelper();
	String mostrarVeredas = (String)request.getSession().getAttribute(CVereda.MOSTRAR_VEREDA);
	
	String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
	session.setAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL,vistaAnterior);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    
    <title>Documento sin t&iacute;tulo</title>
    <script language="JavaScript" type="text/JavaScript">
    
    function submitform(){
		document.formulario.submit();
    }
    function visibilidad(nombre,ver){
        document.nombre.style.visibility=ver
    }
    function carga(){
		document.formulario.id_depto_00.value=opener.document.getElementById('id_depto').value;
		document.formulario.nom_depto_00.value=opener.document.getElementById('nom_depto').value;
		document.formulario.id_munic_00.value=opener.document.getElementById('id_munic').value;
		document.formulario.nom_munic_00.value=opener.document.getElementById('nom_munic').value;
		document.formulario.id_vereda_00.value=opener.document.getElementById('id_vereda').value;
		document.formulario.nom_vereda_00.value=opener.document.getElementById('nom_vereda').value;
		
		try{
			document.formulario.<%=CVereda.MOSTRAR_VEREDA%>.value='<%=mostrarVeredas!=null?mostrarVeredas.trim():CVereda.NO_MOSTRAR_VEREDA%>';
		}catch(e){
		}
   	}
    function value_formulario(iddepartamento,nomdepartamento,idmunicipio,nommunicipio,idvereda,nomvereda){
        opener.document.getElementById(document.formulario.id_depto_00.value).value=iddepartamento;
        opener.document.getElementById(document.formulario.nom_depto_00.value).value=nomdepartamento;
        opener.document.getElementById(document.formulario.id_munic_00.value).value=idmunicipio;
        opener.document.getElementById(document.formulario.nom_munic_00.value).value=nommunicipio;
        opener.document.getElementById(document.formulario.id_vereda_00.value).value=idvereda;
        opener.document.getElementById(document.formulario.nom_vereda_00.value).value=nomvereda;
        window.close();
	}

    function MM_findObj(n, d) { //v4.01
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
<body  onLoad="carga();">
<form action="seleccionar.locacion.view" method="get" name="formulario" id="formulario">

  <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
  
  
  <tr>
    <td width="7">&nbsp;</td>
    <td width="7" >&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
		<%
		
		try{
			if(mostrarVeredas!=null && mostrarVeredas.equals(CVereda.MOSTRAR_VEREDA)){
				helper.setBuscarVereda(true);
			}else{
				helper.setBuscarVereda(false);
			}
	
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
			
		%>
         
      </td>
    <td width="11" >&nbsp;</td>
    <td width="7">&nbsp;</td>
  </tr>
  
</table>
</form>
</body>
</html>
