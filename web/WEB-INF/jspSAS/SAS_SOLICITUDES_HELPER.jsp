<%@page contentType="text/html"%>
<%@page import="java.util.*,
                org.auriga.sas.modelo.daoObjects.*,
                org.auriga.sas.modelo.transferObjects.*"
%>
<% 
   SASDAOFactory factory = SASDAOFactory.getFactory();
   SolicitudDAO solicitudDAO = factory.getSolicitudDAO();
   List solicitudes = new ArrayList();
   try {
       solicitudes = (List)solicitudDAO.seleccionarTodas();
   }
   catch(SASDAOException jss){
       jss.printStackTrace();
       return;
   }
   
   List manuales = new ArrayList();
   
   Iterator i = solicitudes.iterator();
   Solicitud info = null;
   while(i.hasNext()){
      info = (Solicitud)i.next();
      if (info.isManual()) manuales.add(info);
   }
      
%>
<html>
<head>
<title>Lista de Seleccion de Solicitudes Concurrentes Manuales </title>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/jsp/sas.css" type="text/css">
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<script language="javascript">
 <!--
  function nuevaSolicitud(){
     var lista = document.menuform.itemlist.options;
     if (lista.selectedIndex < 0){
        alert("Debe seleccionar una solicitud de la lista");
     }
     else{
       var idSolicitud = lista[lista.selectedIndex].value;
       var idUsuario = 'TEST';
       var url = "<%=request.getContextPath() %>/sas.nueva.solicitud.view?idSolicitud="+idSolicitud+"&idUsuario="+idUsuario;
       window.open(url,document.parent,"menubar=false,resizable,status,scrollbars,left=50,top=30,height=420,width=600");
       window.close();
     }
  }
 //-->
 </script>
</head>
<body bgcolor="#FFFFFF">
<center>
  <form name="menuform">
    <table width="80%" border="0" cellspacing="2" cellpadding="1" bgcolor="#FFFFFF">
      <tr> 
        <td width="7%"><font face="arial, helvetica" size="-1">&nbsp; </font></td>
        <td width="93%" class="titulo">Solicitudes Concurrentes Disponibles</td>
      </tr>
      <tr> 
        <td width="7%">&nbsp;</td>
        <td width="93%"> <%
 if(manuales.size() == 0){
   out.println("<p> No hay solicitudes concurrentes disponibles</p>");
   out.println("</td></tr></table></form></body></html>");
   return;
  }
  else{ %> <p>Seleccione una solicitud de la lista</p></td>
      </tr>
      <tr> 
        <td width="7%">&nbsp;</td>
        <td width="93%"> 
		<select name="itemlist" size="10" class="lista">
        <%      
        Iterator it = manuales.iterator();
        while(it.hasNext()){
           Solicitud is = (Solicitud)it.next();
        %>
        <option value="<%= is.getIdSolicitud() %>"><%= is.getNombre() %></option>
        <%}
	 }%>
      </select> </td>
      </tr>
      <tr> 
        <td width="7%">&nbsp;</td>
        <td width="93%"> <input type="button" value="Seleccionar" onClick="nuevaSolicitud()" class="botones" name="button"> 
        </td>
      </tr>
    </table>
    <br>
    <br>
  </form>
</center>
</body>
</html>