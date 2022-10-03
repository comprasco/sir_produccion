package gov.sir.core.web;

import gov.sir.hermod.HermodProperties;
import java.io.*;
import java.util.zip.ZipException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cesar Ramírez
 * @change 1195.IMPRESION.CERTIFICADOS.EXENTOS.PDF
 * Clase para servir archivo Zip
 */
public class ZipServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePath = HermodProperties.getInstancia().getProperty(HermodProperties.HERMOD_RUTA_TEMP_GENERACION);
        String turno = request.getParameter("TURNO");
        String file = filePath + turno + ".zip";

        File downloadFile = null;
        FileInputStream inStream = null;
        OutputStream outStream = null;

        String mimeType = "application/octet-stream";
        String headerKey = "Content-Disposition";

        try {
            String headerValue = "";
            downloadFile = new File(file);
            inStream = new FileInputStream(downloadFile);

            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
        } catch (FileNotFoundException eff) {
            System.out.println("ERROR SERVLET SIRVIENDO ZIP: " + eff.getMessage());
            response.getWriter().write("<HTML><BODY>NO SE ENCONTRÓ EL ZIP SOLICITADO<BODY><HTML>");
        } catch (ZipException ez) {
            System.out.println("ERROR SERVLET SIRVIENDO ZIP: " + ez.getMessage());
        } catch (IOException eio) {
            System.out.println("ERROR SERVLET SIRVIENDO ZIP (IO): " + eio.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR SERVLET SIRVIENDO ZIP: " + e.getMessage());
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
                if(downloadFile != null){
                    downloadFile.delete();
                }
            } catch (Exception ex) {
                System.out.println("ERROR SERVLET SIRVIENDO ZIP: " + ex.getMessage());
            }
        }
    }
}
