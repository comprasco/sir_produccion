<%@page import="org.auriga.sas.motor.*" %>
<%@page contentType="text/html"%>
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<% String sid = request.getParameter("ID");
   if(sid == null || sid.length() == 0 || sid.equals("0")){
    out.println("Debe indicar una solicitud");
   }
   else{
      long id = Long.parseLong(sid);
      out.println("OK, ya identificamos la solicitud: "+id);
      MotorSolicitudesConcurrentes motor = MotorSolicitudesConcurrentes.getInstancia();
      motor.cancelarSolicitud(id);
      out.println("cancelando la solicitud "+id);
   }
%>