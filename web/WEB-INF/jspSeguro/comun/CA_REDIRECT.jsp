<%-- 
    Document   : CA_REDIRECT
    Created on : 29/10/2015
    Author     : David Panesso
    Change     : 1241.ADAPTACION DEL PROCESO DE AUTENTICACIÓN Y SSO DE CA SITEMINDER
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <META http-equiv="refresh" content="0;URL=<%= request.getContextPath()%>/seguridad.do"> 
        <title>CA Redirect</title>
    </head>
    <body>
        <h1>Espere por favor....</h1>
    </body>
</html>
