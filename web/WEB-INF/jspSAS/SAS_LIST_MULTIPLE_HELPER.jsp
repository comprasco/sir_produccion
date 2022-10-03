<%@page contentType="text/html"%>
<%@page import="java.util.List,java.util.Iterator,org.auriga.sas.SASKeys,org.auriga.sas.solicitud.InformacionSolicitud, org.auriga.sas.solicitud.InformacionParametro, org.auriga.sas.solicitud.jdvs.*" %>
<% 
  String nombreParametro = request.getParameter(SASKeys.NOMBRE_PARAMETRO);
  InformacionSolicitud is = (InformacionSolicitud)session.getAttribute(SASKeys.SOLICITUD_EN_SESION);
  InformacionParametro ipm = is.getInformacionParametro(nombreParametro);
%>
<html>
<head><title>Lista de Seleccion Multiple : <%= ipm.getPrompt() %></title>

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
obj1 = new SelObj('menuform','srcList','entry');
// menuform is the name of the form you use
// itemlist is the name of the select pulldown menu you use
// entry is the name of text box you use for typing in
obj1.bldInitial(); 
}

function llenar(){
document.menuform.entry.value = document.menuform.srcList.options[document.menuform.srcList.selectedIndex].value;
}

function addSelectedItemsToParent() {
var destList = window.document.forms[0].destList;
var resultado = destList.options[0].value;
if (resultado == null) { window.close(); }
for(var i = 1; i < destList.options.length; i++) {
  resultado = resultado + ';' + destList.options[i].value;
}
window.opener.document.forms[0].<%= ipm.getNombre() %>.value = resultado;
window.close();
}


// Add the selected items from the source to destination list
function addSrcToDestList() {
destList = window.document.forms[0].destList;
srcList = window.document.forms[0].srcList; 
var len = destList.length;
for(var i = 0; i < srcList.length; i++) {
if ((srcList.options[i] != null) && (srcList.options[i].selected)) {
//Check if this value already exist in the destList or not
//if not then add it otherwise do not add it.
var found = false;
for(var count = 0; count < len; count++) {
if (destList.options[count] != null) {
if (srcList.options[i].text == destList.options[count].text) {
found = true;
break;
      }
   }
}
if (found != true) {
destList.options[len] = new Option(srcList.options[i].text,srcList.options[i].value); 
len++;
         }
      }
   }
}
// Deletes from the destination list.
function deleteFromDestList() {
var destList  = window.document.forms[0].destList;
var len = destList.options.length;
for(var i = (len-1); i >= 0; i--) {
if ((destList.options[i] != null) && (destList.options[i].selected == true)) {
destList.options[i] = null;
      }
   }
}
// End -->

</script>
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<link rel="stylesheet" href="<%= request.getContextPath()  %>/jsp/estilos.css" type="text/css">
</head>
<body OnLoad="javascript:setUp()" bgcolor="#FFFFFF">
<center>
<form name="menuform">
                <table width="100%" border="0" cellspacing="2" cellpadding="1" bgcolor="#FFFFFF">
                  <tr> 
                    
        <td width="7%"><img src="<%= request.getContextPath()  %>/jsp/imagenes/helpmin.gif"><font face="arial, helvetica" size="-1">&nbsp; 
          </font></td>
                    <td width="93%" class="titulosverdes"><%= ipm.getPrompt() %></td>
                  </tr>
                  <tr> 
                    <td width="7%">&nbsp;</td>
                    <td width="93%"> 
                      <p>Explicaci&oacute;n de este campo</p>
                    </td>
                  </tr>
                  <tr> 
                    <td width="7%">&nbsp;</td>
                    <td width="93%"> 
                      <div align="center"> 
                        <% 
 if(ipm.getClaseJuegoDeValores() == null){
%>
                        <p> este parametro no tiene un juego de valores asociado</p>
                        <%
  }
  else{
     JuegoDeValores jdv = ipm.instanciarJuegoDeValores();
     if(jdv.obtenerValores() == null){%>
                        <p> el juego de valores asociado a este parametro no tiene 
                          una lista de validacion </p>
                        <%   }
     else{
%>
                        <div align="left"> 
                          
              <table width="100%" border="0" cellspacing="2" cellpadding="1" align="center">
                <tr> 
                              
                  <td width="45%"> 
                    <div align="center" class="subtitulostablas">Disponibles</div>
                              </td>
                              
                  <td>&nbsp; </td>
                              
                  <td width="45%"> 
                    <div align="center" class="subtitulostablas"> 
                                  Seleccionados </div>
                              </td>
                            </tr>
                            <tr> 
                              
                  <td width="45%"> 
                    <input type="text" name="entry" size="30" onKeyUp="javascript:obj1.bldUpdate();" class="completar">
                              </td>
                              
                  <td>&nbsp; </td>
                              
                  <td width="45%"> 
                    <div align="center"> </div>
                              </td>
                            </tr>
                            <tr> 
                              
                  <td width="45%"> 
                    <select name="srcList" multiple size=5 class="lista" onChange="llenar();">
                      <%      List li = jdv.obtenerValores();
        Iterator it = li.iterator();
        while(it.hasNext()){
           InformacionValor iv = (InformacionValor)it.next();
%>
                      <option value="<%= iv.getValor() %>"> <%= iv.getValor() %> 
                                  <%= iv.getDescripcion()%> 
                                  <%      }  
%>
                                </select>
                              </td>
                              
                  <td height="103"> 
                    <table width="100%" border="0" cellspacing="2" cellpadding="1" align="center">
                                  <tr> 
                                    <td> 
                                      <div align="center"> 
                                        <input type="button" name="Button" value="&gt;&gt;" class="botones" onClick="addSrcToDestList();">
                                      </div>
                                    </td>
                                  </tr>
                                  <tr> 
                                    <td> 
                                      <div align="center"></div>
                                    </td>
                                  </tr>
                                  <tr> 
                                    <td> 
                                      <div align="center"> 
                                        <input type="button" name="Submit2" value="&lt;&lt;" class="botones" onClick="deleteFromDestList();">
                                      </div>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                              
                  <td width="45%" height="103"> 
                    <select name="destList" multiple size="5" class="lista">
                    </select>
                              </td>
                            </tr>
                          </table>
                          <div align="center"> 
                            <input type=button value="Seleccionar" onClick="addSelectedItemsToParent();" class="botones" name="button">
                          </div>
                        </div>
                        <div align="center"> 
                          <%     }
  }
%>
                        </div>
                      </div>
                    </td>
                  </tr>
                </table>
  </form>
</center>
</body>
</html>
