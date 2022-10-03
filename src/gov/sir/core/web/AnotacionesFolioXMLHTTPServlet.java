package gov.sir.core.web;

import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 * Servlet que se encarga de las peticiones XMLHTTP para las anotaciones de los folios.
 * @author OSBERT LINERO - IRIDIUM
 */
public class AnotacionesFolioXMLHTTPServlet extends HttpServlet {

    private static final String ACCION = "ACCION";

    private static final String GET_NUM_ANOTACIONES_FOLIO = "getNumAnotacionesFolio";

    private static final String ID_MATRIV_CULA = "ID_MATRICULA";

    StringBuffer retorno;
   
    /** 
     * M�todo que se encarga de procesar las peticiones HTTP (POST) con la acci�n "getNumAnotacionesFolio".
     *
     *
     *
     * @param request - objeto tipo HttpServletRequest con la petici�n.
     * @param response - objeto tipo HttpServletResponse con la respuesta.
     * @throws ServletException si ocurre un error espec�fico de servlet.
     * @throws IOException si ocurreo un erro de entrada/salida.
     */
    protected void getNumAnotacionesFolio(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            String id_matricula = request.getParameter(ID_MATRIV_CULA);
            ServiceLocator service = ServiceLocator.getInstancia();
            ForsetiServiceInterface forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
            long numAnotacionesM = forseti.getCountAnotacionesFolio(id_matricula);
            retorno = new StringBuffer();
            retorno.append("<root>");
                retorno.append("<totalAnotaciones>");
                    retorno.append(numAnotacionesM);
                retorno.append("</totalAnotaciones>");
            retorno.append("</root>");
            response.setHeader("Cache-Control", "no-cache");
			response.setHeader("expires", "0");
			response.setContentType("application/xml");
            response.setContentLength(retorno.toString().getBytes().length);
			response.getOutputStream().write(retorno.toString().getBytes());
			response.getOutputStream().flush();
        } catch (ServiceLocatorException sLE) {
            retorno = new StringBuffer(doXMLError(sLE.getMessage()));
            response.setHeader("Cache-Control", "no-cache");
			response.setHeader("expires", "0");
			response.setContentType("application/xml");
            response.setContentLength(retorno.toString().getBytes().length);
			response.getOutputStream().write(retorno.toString().getBytes());
			response.getOutputStream().flush();
        } catch (Throwable tE) {
            retorno = new StringBuffer(doXMLError(tE.getMessage()));
            response.setHeader("Cache-Control", "no-cache");
			response.setHeader("expires", "0");
			response.setContentType("application/xml");
            response.setContentLength(retorno.toString().getBytes().length);
			response.getOutputStream().write(retorno.toString().getBytes());
			response.getOutputStream().flush();
        }
    } 

    /** 
     * M�todo que se encarga de procesar las peticiones HTTP (GET).
     * Redirige la petici�n al m�todo doPost(HttpServletRequest request, HttpServletResponse response).
     * @param request - objeto tipo HttpServletRequest con la petici�n.
     * @param response - objeto tipo HttpServletResponse con la respuesta.
     * @throws ServletException si ocurre un error espec�fico de servlet.
     * @throws IOException si ocurreo un erro de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    } 

    /** 
     * M�todo que se encarga de procesar las peticiones HTTP (POST).
     * En el request debe venir un par�metro "accion" con la acci�n deseada.
     *  - Si la acci�n es "getNumAnotacionesFolio" la petici�n es redirigida al m�todo getNumAnotacionesFolio(HttpServletRequest request, HttpServletResponse response).
     *  - Si el par�metro acci�n no es recibido se retorna un XML de error especificando que no se ha recibido ninguna acci�n.
     * @param request - objeto tipo HttpServletRequest con la petici�n.
     * @param response - objeto tipo HttpServletResponse con la respuesta.
     * @throws ServletException si ocurre un error espec�fico de servlet.
     * @throws IOException si ocurreo un erro de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String accion = request.getParameter(ACCION);
        if(GET_NUM_ANOTACIONES_FOLIO.equals(accion)){
            getNumAnotacionesFolio(request, response);
        }else{
            retorno = new StringBuffer();
            retorno.append("<root>");
                retorno.append("<XMLError>");
                    retorno.append("<descripcion>");
                        retorno.append("No fue recibida ninguna acci�n o la acci�n recibida no es correcta");
                    retorno.append("</descripcion>");
                retorno.append("</XMLError>");
            retorno.append("</root>");
            response.setHeader("Cache-Control", "no-cache");
			response.setHeader("expires", "0");
			response.setContentType("application/xml");
            response.setContentLength(retorno.toString().getBytes().length);
			response.getOutputStream().write(retorno.toString().getBytes());
			response.getOutputStream().flush();
        }
    }

    /**
     * M�todo que genera un xml de error con el mensaje recibido como par�metro.
     * La estructura es la siguiente:
     *  <root>
     *      <XMLError>
     *          <descripcion>
     *          </descripcion>
     *      </XMLError>
     *  </root>
     * El mensaje de error debe ir en la etiqueta <descripcion>
     * @param mensaje String con el mensaje del error.
     * @return String con el xml de error.
     */
    public String doXMLError(String mensaje){
        StringBuffer xmlError = new StringBuffer();
            xmlError.append("<root>");
                xmlError.append("<XMLError>");
                    xmlError.append("<descripcion>");
                        xmlError.append(mensaje);
                    xmlError.append("</descripcion>");
                xmlError.append("</XMLError>");
            xmlError.append("</root>");
        return xmlError.toString();
    }

    /** 
     * Retorna la descripci�n del servlet.
     * @return String con la descripci�n del servlet.
     */
    public String getServletInfo() {
        return "Servlet que se encarga de las peticiones XMLHTTP para las anotaciones de los folios. Debe recibir como par�metros la id de la matricula y la acci�n a realizar.";
    }

    

}
