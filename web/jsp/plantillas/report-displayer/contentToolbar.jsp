<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%response.setHeader("CACHE-CONTROL","NO-CACHE");%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Toolbar</title>
 
<style type="text/css">

/* need this one for Mozilla */
HTML { 
	margin:0px;
	padding:0px;
}
 
BODY {
	background:ButtonFace;
}

#titleText {
	font-weight:bold;
	color:WindowText;
}
 
.buttonOn a { 
	display:block;
	margin-left:2px;
	margin-right:2px;
	width:20px;
	height:20px;
	border:1px solid Highlight;
	writing-mode:tb-rl;
	vertical-align:middle;
	background: Window;
}

.button a { 
	display:block;
	margin-left:2px;
	margin-right:2px;
	width:20px;
	height:20px;
	border:1px solid ButtonFace;
	writing-mode:tb-rl;
	vertical-align:middle;
}

.button a:hover { 
	border-top:1px solid ButtonHighlight; 
	border-left:1px solid ButtonHighlight; 
	border-right:1px solid ButtonShadow; 
	border-bottom:1px solid ButtonShadow;
}


#b6:hover{
	border:1px solid ButtonFace;
}


.separator {
	background-color: ThreeDShadow;
	height:100%;
	width: 1px;
	border-top:2px solid ButtonFace;
	border-bottom:2px solid ButtonFace;
	border-left:3px solid ButtonFace;
	border-right:3px solid ButtonFace;
	
}

#container {
	border-bottom:1px solid ThreeDShadow;
 

}



</style>

<script language="JavaScript">

var bRestore = false;
// Preload images

	var back = new Image();
	back.src = "images/e_back.gif";

	var forward = new Image();
	forward.src = "images/e_forward.gif";

	var synch = new Image();
	synch.src = "images/e_synch_toc_nav.gif";

	var add_bkmrk = new Image();
	add_bkmrk.src = "images/e_add_bkmrk.gif";

	var print = new Image();
	print.src = "images/e_print_edit.gif";

	var maximize_restore = new Image();
	maximize_restore.src = "images/e_maximize.gif";


function setTitle(label)
{
	if( label == null) label = "";
	var title = document.getElementById("titleText");
	if (title == null) return;
	var text = title.lastChild;
	if (text == null) return;
	text.nodeValue = label;
}


function registerMaximizedChangedListener(){
	// get to the frameset
	var p = parent;
	while (p && !p.registerMaximizeListener)
		p = p.parent;
	
	if (p!= null){
		p.registerMaximizeListener('Toolbar', maximizedChanged);
	}
}
/*  registerMaximizedChangedListener(); */

/**
 * Handler for double click: maximize/restore this view
 * Note: Mozilla browsers prior to 1.2.1 do not support programmatic frame resizing well.
 */
function mouseDblClickHandler(e) {
	// ignore double click on buttons
	var target=window.event.srcElement;
	if (target.tagName && (target.tagName == "A" || target.tagName == "IMG"))
		return;
	toggleFrame();
	return false;
}		
function restore_maximize(button)
{
	toggleFrame();
	if (isIE && button && document.getElementById(button)){
		document.getElementById(button).blur();
	}
}
function toggleFrame(){
	// get to the frameset
	var p = parent;
	while (p && !p.toggleFrame)
		p = p.parent;
	
	if (p!= null){
		p.toggleFrame('');
	}
	document.selection.clear;	
}

function maximizedChanged(maximizedNotRestored){
	if(maximizedNotRestored){
		document.getElementById("maximize_restore").src="images/e_restore.gif";
		document.getElementById("maximize_restore").setAttribute("title", "Restore");
		document.getElementById("maximize_restore").setAttribute("alt", "Restore");
		bRestore = true;
	}else{
		document.getElementById("maximize_restore").src="images/e_maximize.gif";
		document.getElementById("maximize_restore").setAttribute("title", "Maximize");
		document.getElementById("maximize_restore").setAttribute("alt", "Maximize");
		bRestore = false;
	}
}

document.ondblclick = mouseDblClickHandler;


function setButtonState(buttonName, pressed) {
	if(!document.getElementById("tdb_"+buttonName))
		return;
	if(pressed){
		document.getElementById("tdb_"+buttonName).className="buttonOn";
	}else{
		document.getElementById("tdb_"+buttonName).className="button";
	}
}

function setWindowStatus(buttonName){
	
		if (buttonName == "back"){
			if (buttonName == "maximize_restore"){
				if (bRestore){
					window.status = "Restore";
				}else{
					window.status = "Maximize";
				}
			}else{
				window.status = "Go Back";
			}
		}
	
		if (buttonName == "forward"){
			if (buttonName == "maximize_restore"){
				if (bRestore){
					window.status = "Restore";
				}else{
					window.status = "Maximize";
				}
			}else{
				window.status = "Go Forward";
			}
		}
	
		if (buttonName == "null"){
			if (buttonName == "maximize_restore"){
				if (bRestore){
					window.status = "Restore";
				}else{
					window.status = "Maximize";
				}
			}else{
				window.status = "null";
			}
		}
	
		if (buttonName == "synch"){
			if (buttonName == "maximize_restore"){
				if (bRestore){
					window.status = "Restore";
				}else{
					window.status = "Maximize";
				}
			}else{
				window.status = "Show in Table of Contents";
			}
		}
	
		if (buttonName == "add_bkmrk"){
			if (buttonName == "maximize_restore"){
				if (bRestore){
					window.status = "Restore";
				}else{
					window.status = "Maximize";
				}
			}else{
				window.status = "Bookmark Document";
			}
		}
	
		if (buttonName == "print"){
			if (buttonName == "maximize_restore"){
				if (bRestore){
					window.status = "Restore";
				}else{
					window.status = "Maximize";
				}
			}else{
				window.status = "Print Page";
			}
		}
	
		if (buttonName == "maximize_restore"){
			if (buttonName == "maximize_restore"){
				if (bRestore){
					window.status = "Restore";
				}else{
					window.status = "Maximize";
				}
			}else{
				window.status = "Maximize";
			}
		}
	
}
</script>


<script language="JavaScript" src="contentActions.js"></script>


</head>
 

	<body dir="ltr">

<%
String vistaParaRegresar = (String)request.getSession().getAttribute(gov.sir.core.web.WebKeys.VISTA_PARA_REGRESAR);
//System.out.println("Vista para regresar CONTENT : " + vistaParaRegresar);
%>
<table id="container" width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" style='padding-left:5px;'>

	<tr>
		<td nowrap style="font: icon" valign="middle">
			<div id="titleTextTableDiv" style="overflow:hidden; height:22px;"><table><tr><td nowrap style="font:icon"><div id="titleText" >&nbsp;</div></td></tr></table>
			</div>
		
		
		<div style="position:absolute; top:1px; right:0px;">
		<table width="100%" border="0" cellspacing="1" cellpadding="0" height="100%">
			<tr>
				<td align="right">
					<table align="right" border="0" cellspacing="0" cellpadding="0" height="100%" style="background:ButtonFace">
					<tr>

						<td align="middle" id="tdb_back" class="button" height=18>
							
							<a href="javascript:goBackUrl('b0', '<%=vistaParaRegresar%>');" 
							   onmouseover="javascript:setWindowStatus('back');return true;" 
							   onmouseout="window.status='';"
							   id="b0">
							   <img src="images/e_back.gif" 
									alt='Go Back' 
									title='Go Back' 
									border="0"
									id="back">
							</a>
						</td>

						<td align="middle" id="tdb_forward" class="button" height=18>
							<a href="javascript:goForward('b1');" 
							   onmouseover="javascript:setWindowStatus('forward');return true;" 
							   onmouseout="window.status='';"
							   id="b1">
							   <img src="images/e_forward.gif" 
									alt='Go Forward' 
									title='Go Forward' 
									border="0"
									id="forward">
							</a>
						</td>

						<td align="middle" class="separator" valign="middle">
						</td>

						<td align="middle" id="tdb_print" class="button" height=18>
							<a href="javascript:printContent('b5');" 
							   onmouseover="javascript:setWindowStatus('print');return true;" 
							   onmouseout="window.status='';"
							   id="b5">
							   <img src="images/e_print_edit.gif" 
									alt='Print Page' 
									title='Print Page' 
									border="0"
									id="print">
							</a>
						</td>

						<td align="middle" id="tdb_maximize_restore" class="button" height=18>
							<a href="javascript:restore_maximize('b6');" 
							   onmouseover="javascript:setWindowStatus('maximize_restore');return true;" 
							   onmouseout="window.status='';"
							   id="b6">
						</a></td>
				
					</tr>
					</table>
				</td>
			</tr>
		</table> 
		</div>
		</td>
	</tr>
</table>


    <iframe name="liveHelpFrame" title="Layout frame: liveHelpFrame" style="visibility:hidden" tabindex="-1" frameborder="no" width="0" height="0" scrolling="no">
    </iframe>


</body>     
</html>


