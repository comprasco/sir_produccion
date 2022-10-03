<%@ taglib uri="/smartags" prefix="smartview"  %>
<html>
<head> 
<title><smartview:mostrar parametro="titulo"/></title>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath() %>/jsp/auriga/auriga.css" type="text/css">
<script language="JavaScript">
var ID_EJECUCION = 0;

function seleccionarSolicitud(idEjecucion){
ID_EJECUCION = idEjecucion;
}

function cancelar(){
var idEjecucion = ID_EJECUCION;
var url = "<%=request.getContextPath() %>/jsp/sas/cancelarSolicitud.jsp?ID="+idEjecucion;
window.open(url,"Cancelar_Solicitud_Concurrente","menubar=false,resizable,status,scrollbars,left=50,top=30,height=150,width=150");
}

function verDetalles(){
var idEjecucion = ID_EJECUCION;
var url = "<%=request.getContextPath() %>/log.sas?ID="+idEjecucion+"&TIPO=DETALLES";
window.open(url,"Resultado_Solicitud_Concurrente","menubar=false,resizable,status,scrollbars,left=50,top=30,height=650,width=800");
}

function verResultado(){
var idEjecucion = ID_EJECUCION;
var url = "<%=request.getContextPath() %>/log.sas?ID="+idEjecucion+"&TIPO=RESULTADO";
window.open(url,"Resultado_Solicitud_Concurrente","menubar=false,resizable,status,scrollbars,left=50,top=50,height=600,width=800");
}

function verLogEjecucion(){
var idEjecucion = ID_EJECUCION;
var url = "<%=request.getContextPath() %>/log.sas?ID="+idEjecucion+"&TIPO=EJECUCION";
window.open(url,"Log_Ejecucion_Solicitud_Concurrente","menubar=false,resizable,status,scrollbars,left=50,top=50,height=600,width=800");
}


function nuevaSolicitud(){
var url = "<%=request.getContextPath() %>/sas.solicitudes.helper.view";
window.open(url,'solicitudesHelper','status=yes,scrollbars=yes,width=600,height=400,top=100,left=100');
}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr><td>
<smartview:insertar parametro="topFrame"/>
</td></tr>
<tr><td>
<smartview:insertar parametro="mainFrame"/>
</td></tr>
<tr><td>
<smartview:insertar parametro="bottomFrame"/>
</td></tr>
</table>
</body>
</html>