<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<%response.setHeader("CACHE-CONTROL","NO-CACHE");%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Untitled Document</title>

<script type="text/javascript">
function liveActionInternal(topHelpWindow, pluginId, className, argument)
{

	// construct the proper url for communicating with the server
	var url= window.location.href;

	var i = url.indexOf("?");
	if(i>0)
		url=url.substring(0, i);

	i = url.indexOf("/topic/");
	if(i < 0)
		i = url.lastIndexOf("/");

	url=url.substring(0, i+1);
	var encodedArg=encodeURIComponent(argument);
	url=url+"livehelp/?pluginID="+pluginId+"&class="+className+"&arg="+encodedArg+"&nocaching="+Math.random();

	// we need to find the toolbar frame.
	// to do: cleanup this, including the location of the hidden livehelp frame.
	var toolbarFrame = topHelpWindow.HelpFrame.ContentFrame.ContentToolbarFrame;
	if (!toolbarFrame){
		return;
	}

	if(toolbarFrame.liveHelpFrame){
		toolbarFrame.liveHelpFrame.location=url;
	}

}
function showTopicInContentsInternal(topHelpWindow, topic) {
	try{
		topHelpWindow.HelpFrame.NavFrame.displayTocFor(topic);
	}catch(e){
	}
}

</script>


<style type="text/css">
frameset {
	border: 0px;
}
</style>


<%
String vistaParaRegresar = (String)request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
session.setAttribute(gov.sir.core.web.WebKeys.VISTA_PARA_REGRESAR, vistaParaRegresar);
//System.out.println("Vista para regresar RESULT : " + vistaParaRegresar);
%>

<%

   	String param_Uri = (String)session.getAttribute( gov.sir.core.web.acciones.administracion.AWReportes.REPORTSSERVICES_REPORTURI );

//System.out.println("URI" + param_Uri);

	String param_RedirectPointUri = "ReportResults_Section2.jsp";

	if( null != param_Uri ) {
		param_RedirectPointUri = param_Uri ;
	}

%>


<%-- Sof:JavascriptLoader --%>


<script type="text/javascript" >

function openDoc2(filename,target,windowTitle){

  //to ensure no global variable conflict with other script
  var strWinHandle = target + "objDocWin";

  //just focus to the corresponding window if it is already open
  if (window[strWinHandle] && !window[strWinHandle].closed){
    window[strWinHandle].focus();
    return false;
  }

  window[strWinHandle] = window;

	//change window name (target); later, target will be used as frame name
	window[strWinHandle].name = strWinHandle;

	//put a little delay; Netscape6 causes a null document object when called directly
	setTimeout('loadDoc("'+strWinHandle+'","'+target+'","'+filename+'")',0);

	return false; //this cancels href of the calling link

}

function loadDoc(strWinHandle,target,filename){
  //Flash the 'Loading...' message
  var strHTML = '<html>\n<head>\n<title></title>\n';
  strHTML += '<style type="text/css">\nbody{margin:20px 0px 0px 0px;}\n#container{width:100%;text-align:center;font-family:Arial,Tahoma,Verdana}\n#status{font-weight:bold;font-size:14px;color:red;width:100%;}\n#alternative{font-size:11px;color:gray;width:100%;}\n</style>';
  strHTML += '</head>\n<body>';
  strHTML += '<div id="container">\n<div id="status">Generando el reporte... Por favor espere.</div>\n';
  //provide link to close window (for browsers with no appropriate plugin for the needed software)
  strHTML += '<div id="alternative">Si usted no tiene el software/plugin necesitado para cargar el documento dentro del browser, favor <a href="#" onclick="top.close();return false;">cerrar</a> esta ventana.</div>\n</div>';
  strHTML += '</body>\n</html>';
  var winDocFrame = window[strWinHandle].frames[target];

  winDocFrame.document.write(strHTML); //winDocFrame.document is null in Netscape6 if called directly
  winDocFrame.document.close();

  // alert( winDocFrame.document + " :" + strWinHandle + " " + target + " " + filename );

  //preload the document
  var doc = new Image();
  doc.onerror = function(){
    //check window if still open, the user might have closed it
    if (window[strWinHandle] && !window[strWinHandle].closed){
      winDocFrame.location.replace(this.src); //finally, set frameset's location to the document filename
    }
  }
  doc.src = filename; //onerror handler fires since image src is not actually an image
}


  function onloadHandler( filename, target ) {

    window.focus();

    openDoc2( filename, target, "title1" );

    return false;

  }

</script>

<script type="text/javascript">

	var docToOpen = "<%= param_RedirectPointUri %>";
	// alert( docToOpen );

</script>


<%-- Eof:JavascriptLoader--%>



</head>


</head>




<frameset id="indexFrameset" onload="onloadHandler( docToOpen, 'MainFrame' )" rows="24,*"  frameborder="0" framespacing="0" border=0 spacing=0>
<!--
  	src="ReportResults_Section1.htm"
 -->
  <frame
  	src="<%=request.getContextPath()%>/jsp/plantillas/report-displayer/contentToolbar.jsp"
  	name="TopFrame"
  	noresize="true"

	marginwidth="0"
	marginheight="0"
	scrolling="no"
	frameborder="0"

  	title="Main Help Toolbar"

  ></frame>
  <frame
  	src="about:blank"
  	name="MainFrame"
	marginwidth="0"
	marginheight="0"
	scrolling="yes"
	frameborder="0"

  	title="Layout frame: MainFrame"

  	></frame>

</frameset>


<frameset id="indexFrameset" rows="25,*" cols="*" framespacing="0" frameborder="no" border="0" spacing="0">
</frameset>
<noframes>
	<body>
	This page does not support framesets
	</body>
</noframes>
</html>
