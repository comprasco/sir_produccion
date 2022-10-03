<%-- 
    Document   : descargar
    Created on : May 20, 2013, 10:54:45 AM
    Author     : Carlos Torres
--%>

<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CArchivosJustifica" %>
<%
    try {
        String RUTA_DESTINO_ARCHIVO = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO);
        String RUTA_DESTINO_ARCHIVO_JUSTIFICACION = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO_JUSTIFICACION);

        String SO = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.SO);

        String nFile = request.getParameter("nArchivo");
        String ruta = request.getParameter("rArchivo");
        FileInputStream archivo = null;
        if (ruta.equals(CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA)) {
            archivo = new FileInputStream(RUTA_DESTINO_ARCHIVO_JUSTIFICACION + nFile);
        } else {
            archivo = new FileInputStream(RUTA_DESTINO_ARCHIVO + request.getSession().getAttribute(AWAdministracionForseti.CIRCULOS_SIR) + SO + nFile);
        }
        int longitud = archivo.available();
        byte[] datos = new byte[longitud];
        archivo.read(datos);
        archivo.close();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + nFile);

        ServletOutputStream ouputStream = response.getOutputStream();
        ouputStream.write(datos);
        ouputStream.flush();
        ouputStream.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
%>
