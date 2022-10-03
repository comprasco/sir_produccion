//Script para deshabilitar el click derecho
/*function disableRightClick(e){
  var message = "Right click disabled";
  if(!document.rightClickDisabled){// initialize
    if(document.layers) {
      document.captureEvents(Event.MOUSEDOWN);
      document.onmousedown = disableRightClick;
    }
    else document.oncontextmenu = disableRightClick;
    return document.rightClickDisabled = true;
  }
  
  if(document.layers || (document.getElementById && !document.all))  {
    if (e.which==2||e.which==3)    {
      //alert(message);
      return false;
    }
  }
  else  {
    //alert(message);
    return false;
   }
}*/

//disableRightClick();

///////////////////////////////////////////////////////////////////////
//=====================================================================
// Disable some hot keys  F5(116), F11(122), or WinKey(93)
//=====================================================================
///////////////////////////////////////////////////////////////////////
document.onkeydown=chkKeyDwn
document.onkeypress=chkKeyDwn
var prvWhich = 0
var CPressed = false
var prvGoodK = false
function chkKeyDwn(key) { 
	var message = '';
	// determina el tipo de componente que origino el evento (solo funciona en explorer)
	var tipoComponenteOrigenEvento = window.event.srcElement.type;

    if ((navigator.appName == 'Netscape') &&
        (parseInt(navigator.appVersion)<=4)) {
        //Window when "Ctrl" key pressed
        if (key.modifiers == Event.CONTROL_MASK) {
           if (message.length>0) alert(message)
           return(false)
            }
        }
    else  if ((navigator.appName == 'Netscape') &&
        (parseInt(navigator.appVersion)>4)) {
        //F5==116
        if (key.which == 0 && prvWhich == 116) {
           if (message.length>0) alert(message)
           window.focus();
           prvWhich=key.which
           return false;
           }
        if (key.which == 116) {
           window.focus();
           prvWhich=key.which
           return false;
           }
        //OK button== 83 <--> 85
        if ((key.which >= 83) && (key.which <= 85)) {
           window.focus();
           prvWhich=key.which
           return;
           }
        //Ctrl key == 17
        if (key.which == 17) {
           /*CPressed=true;
           prvWhich=key.which
           return false;*/
           return;
           }
        else
        //s == 115, u == 117
        if (CPressed && ((key.which == 115)||(key.which == 117))) {
           if (message.length>0) alert(message)
           CPressed=false
           prvWhich=key.which
           window.focus();
           return false;
           }
        else
           CPressed=false;
           prvWhich=key.which
           window.focus();
           return;
        }
    else  if (navigator.appName == 'Microsoft Internet Explorer') {
       // valida si el evento proviene de un campo de texto o un textarea
       var esCampoDeTexto = 
			(tipoComponenteOrigenEvento == 'password' || tipoComponenteOrigenEvento == 'text'  ||	tipoComponenteOrigenEvento == 'textarea')?true: false;	
       // If F5(116), F11(122), or WinKey(93)
       //bloquea backspace(8) si NO proviene de un text o textarea    
  
       if ((window.event && window.event.keyCode == 116) ||
           (window.event && window.event.keyCode == 122) ||
           ((window.event && window.event.keyCode == 8) && !esCampoDeTexto 	)||
           (window.event && window.event.keyCode ==  93)) {
           	
            if (!prvGoodK) {
               window.event.keyCode = 38;  //up arrow
               
               
               if (message != '') {
				   alert(message)
				   }
               window.event.cancelBubble = true;
               window.event.returnValue = false;
               return false;
               }
            else  {
               prvGoodK=false;
               return;
               }
           }
           
       /*else if(window.event && window.event.keyCode == 17){ 
             alert("Teclas inválidas para la aplicación");
             prvGoodK = false;
             return;
             }*/      
                        
           
       // If NOT "F5(116), F11(122), or WinKey(93)"
       else  if (
       ((window.event && window.event.keyCode >= 8)
        &&
           ((window.event && window.event.keyCode <= 46)) ||
            (window.event && window.event.keyCode == 145) 
		   )
        ) { 
             prvGoodK = false;
             return;
             }
             

       else  {
             prvGoodK = (!prvGoodK);
             return;
             }
    }
    else
    	return true;
}
