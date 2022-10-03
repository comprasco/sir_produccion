<%@page contentType="text/html"%>
<%@page import="java.util.List,java.util.Iterator,org.auriga.sas.SASKeys,org.auriga.sas.solicitud.InformacionSolicitud, org.auriga.sas.solicitud.InformacionParametro, org.auriga.sas.solicitud.jdvs.*" %>
<% 
  String nombreParametro = request.getParameter(SASKeys.NOMBRE_PARAMETRO);
  InformacionSolicitud is = (InformacionSolicitud)session.getAttribute(SASKeys.SOLICITUD_EN_SESION);
  InformacionParametro ipm = is.getInformacionParametro(nombreParametro);
%>
<html>
<head><title>Lista de Seleccion <%= ipm.getPrompt() %></title>

<SCRIPT LANGUAGE="JavaScript">
<!-- Begin
function SelObj(formname,selname,textname,str) {
this.formname = formname;
this.selname = selname;
this.textname = textname;
this.select_str = str || '';
this.selectArr = new Array();
this.initialize = initialize;
this.bldInitial = bldInitial;
this.bldUpdate = bldUpdate;
}

function initialize() {
if (this.select_str =='') {
for(var i=0;i<document.forms[this.formname][this.selname].options.length;i++) {
this.selectArr[i] = document.forms[this.formname][this.selname].options[i];
this.select_str += document.forms[this.formname][this.selname].options[i].value+":"+
document.forms[this.formname][this.selname].options[i].text+",";
   }
}
else {
var tempArr = this.select_str.split(',');
for(var i=0;i<tempArr.length;i++) {
var prop = tempArr[i].split(':');
this.selectArr[i] = new Option(prop[1],prop[0]);
   }
}
return;
}
function bldInitial() {
this.initialize();
for(var i=0;i<this.selectArr.length;i++)
document.forms[this.formname][this.selname].options[i] = this.selectArr[i];
document.forms[this.formname][this.selname].options.length = this.selectArr.length;
return;
}

function bldUpdate() {
var str = document.forms[this.formname][this.textname].value.replace('^\\s*','');
if(str == '') {this.bldInitial();return;}
this.initialize();
var j = 0;
pattern1 = new RegExp("^"+str,"i");
for(var i=0;i<this.selectArr.length;i++)
if(pattern1.test(this.selectArr[i].text)) 
document.forms[this.formname][this.selname].options[j++] = this.selectArr[i];
document.forms[this.formname][this.selname].options.length = j;
if(j==1){
document.forms[this.formname][this.selname].options[0].selected = true;
document.forms[this.formname][this.textname].value = document.forms[this.formname][this.selname].options[0].value;
   }
}
function setUp() {
obj1 = new SelObj('menuform','itemlist','entry');
// menuform is the name of the form you use
// itemlist is the name of the select pulldown menu you use
// entry is the name of text box you use for typing in
obj1.bldInitial(); 
}

function sendValue(s){
var selvalue = s.value;
window.opener.document.forms[0].<%= ipm.getNombre() %>.value = selvalue;
window.close();
}

function llenar(){
document.menuform.entry.value = document.menuform.itemlist.options[document.menuform.itemlist.selectedIndex].value;
}
//  End -->
</script>
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<link rel="stylesheet" href="<%= request.getContextPath()  %>/jsp/estilos.css" type="text/css">
</head>
<body OnLoad="javascript:setUp()" bgcolor="#FFFFFF">
<center>
<form name="menuform" onSubmit="javascript:window.location = document.menuform.itemlist.options[document.menuform.itemlist.selectedIndex].value;return false;">

                <table width="100%" border="0" cellspacing="2" cellpadding="1" bgcolor="#FFFFFF">
                  <tr> 
                    
        <td width="7%"><font face="arial, helvetica" size="-1">&nbsp; </font></td>
                    <td width="93%" class="titulosverdes"><%= ipm.getPrompt() %></td>
                  </tr>
                  <tr> 
                    <td width="7%">&nbsp;</td>
                    <td width="93%">
                      <p><% if (ipm.getDescripcion()!= null) pageContext.getOut().print(ipm.getDescripcion()); %></p>
                    </td>
                  </tr>
                  <tr> 
                    <td width="7%">&nbsp;</td>
                    <td width="93%">
                      <p>Por favor ingrese un valor para este campo:</p>
                    </td>
                  </tr>
                  <tr> 
                    <td width="7%">&nbsp;</td>
                    <td width="93%"> 
                      <div align="left">
                        
            <input type="text" name="entry" size="40" onKeyUp="javascript:obj1.bldUpdate();" class="completar">
                        <input type=button value="Seleccionar" onClick="sendValue(this.form.entry);" class="botones">
                      </div>
                    </td>
                  </tr>
                  <tr> 
                    <td width="7%">&nbsp;</td>
                    <td width="93%"> 
                      <div align="center"> 
<%
 if(ipm.getClaseJuegoDeValores() == null){
%>
                        <p> este parametro no tiene un juego de valores asociado 
                        </p>
                        <%
  }
  else{
     JuegoDeValores jdv = ipm.instanciarJuegoDeValores();
     if(jdv.obtenerValores() == null){%>
                        <p> el juego de valores asociado a este parametro no tiene 
                          una lista de validacion </p>
                        <%   }
     else{%>
                        <div align="left">
                          
              <select name="itemlist" size=5 class="lista" onChange="llenar();">
                <%      List li = jdv.obtenerValores();
        Iterator it = li.iterator();
        while(it.hasNext()){
           InformacionValor iv = (InformacionValor)it.next();
%>
                <option value="<%= iv.getValor() %>"><%= iv.getValor() %> <%= iv.getDescripcion()==null ? "" : iv.getDescripcion() %> </option>
<%      }  
     }
  }
%>
                          </select>
                        </div>
                      </div>
                    </td>
                  </tr>
                </table>
      <br>
    <br>
  </form>
</center>
</body>
</html>
