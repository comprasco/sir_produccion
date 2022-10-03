package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
/**
 * @autor HGOMEZ 
 * @mantis 12422 
 * @Requerimiento 049_453 
 * @descripcion Se importan las siguientes clases para dar solución
 * al requerimiento.
 */
import gov.sir.core.negocio.modelo.DocPagoTarjetaCredito;
import gov.sir.core.negocio.modelo.DocPagoTarjetaDebito;
import gov.sir.core.negocio.modelo.DocPagoElectronicoPSE;
import gov.sir.core.negocio.modelo.DocPagoConvenio;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPago;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author mmunoz
 * Esta clase muestra en la pantalla de validacion de las diferentes formas de pago
 * cuales si pueden ser utilizadas para registrar el pago
 */
public class ValidacionPagoHelper extends Helper {
    
	/**
	 * Constante donde se guarda el valor a liquidar
	 */
	private double valorLiquidacion;
	
	/**
	 * Constante donde se guarda la liquidacion de la solicitud de certificado
	 */
	private Liquidacion liquidacion;
	
	/**
	 * Constante donde se guarda el rango de aceptacion para poder registrar un pago 
	 */
	private Double rango;
	
	/**
	 * En esta constante se va guardando el valor aplicado total solo de los documentos validos
	 */
	private double valorAplicadoTotal;
	
	/**
	 * Objeto pago que se encuentra el la sesion 
	 */
	private Pago pago;
	
	/**
	 * En esta constante se guarda si existe alguna aplicacion invalida
	 */
	private boolean hayAplicacionInvalida;
	
	private List cheques;
	
	private List consignaciones;
        
	/**
        * @autor HGOMEZ 
        * @mantis 12422 
        * @Requerimiento 049_453 
        * @descripcion Se capturan las listas Tarjeta Crédito y Tarjeta Debito.
        */
        private List tarjetaCredito;
        private List tarjetaDebito;
        //private List listPse;
	
	private AplicacionPago appEfectivo;
        
        /**
        * @autor HGOMEZ 
        * @mantis 12422 
        * @Requerimiento 049_453 
        * @descripcion Se crean las variables appConvenio y appPagoElectronicoPSE.
        */
        private AplicacionPago appConvenio;
        private AplicacionPago appPagoElectronicoPSE;
	
	private NumberFormat formateador = NumberFormat.getInstance();

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	/**
        * @autor HGOMEZ 
        * @mantis 12422 
        * @Requerimiento 049_453 
        * @descripcion Se refactoriza el método.
        */
    public void setProperties(HttpServletRequest request) throws HelperException {
        hayAplicacionInvalida=false;
        HttpSession session = request.getSession();
        session.removeAttribute("OCULTAR");
        pago = (Pago) session.getAttribute(WebKeys.PAGO);
        rango = (Double) session.getAttribute(WebKeys.PRECISION_PAGO);
        liquidacion = pago.getLiquidacion();
        valorLiquidacion = liquidacion.getValor();
        valorAplicadoTotal = 0;
        cheques = new Vector();
        consignaciones = new Vector();
        
        tarjetaCredito = new Vector();
        tarjetaDebito = new Vector();
        //listPse = new Vector();

        List listaApps = pago.getAplicacionPagos();
        Iterator itApps = listaApps.iterator();
        while(itApps.hasNext()){
            AplicacionPago app = (AplicacionPago) itApps.next();
            DocumentoPago doc = app.getDocumentoPago();
            if (doc instanceof DocPagoCheque) {
                cheques.add(app);
            } else if (doc instanceof DocPagoConsignacion) {
                consignaciones.add(app);
            } else if (doc instanceof DocPagoTarjetaCredito) {
                tarjetaCredito.add(app);
            } else if (doc instanceof DocPagoTarjetaDebito) {
                tarjetaDebito.add(app);
            } else if (doc instanceof DocPagoEfectivo) {
                appEfectivo = app;
            } else if (doc instanceof DocPagoElectronicoPSE) {
                appPagoElectronicoPSE = app;
            } else if (doc instanceof DocPagoConvenio) {
                appConvenio = app;
            }
        }

        session.setAttribute(WebKeys.LISTA_CHEQUES,cheques);
        session.setAttribute(WebKeys.LISTA_CONSIGNACIONES,consignaciones);

        session.setAttribute(WebKeys.LISTA_TARJETA_CREDITO, tarjetaCredito);
        session.setAttribute(WebKeys.LISTA_TARJETA_DEBITO, tarjetaDebito);
        //session.setAttribute(WebKeys.LISTA_PSE, listPse);

        session.setAttribute(WebKeys.APLICACION_EFECTIVO,appEfectivo);

        session.setAttribute(WebKeys.APLICACION_CONVENIO,appConvenio);
        session.setAttribute(WebKeys.APLICACION_PAGO_ELECTRONICO_PSE,appPagoElectronicoPSE);
		
    }

    
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {

		String blanco ="&nbsp;";
        out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.write("<tr>");
        out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+ request.getContextPath() +"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write("<td background=\"" + request.getContextPath() +"/jsp/images/sub_bgn001.gif\" class=\"bgnsub\">Liquidaci&oacute;n</td>");
        out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
        out.write("<td width=\"15\"> <img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
        out.write("<tr>");
        out.write("<td width=\"16\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
        out.write("<td>Valor $</td>");
        out.write("<td class=\"campositem\">"+ formateador.format(valorLiquidacion) +"</td>");
        out.write("</tr>");
        out.write("</table>");
        out.write("<br>");
        out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.write("<tr>");
        out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write("<td background=\"" + request.getContextPath() + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\">Detalles del Pago</td>");
        out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_datosuser.gif\" width=\"16\" height=\"21\"></td>");
        out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() +  "/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
        out.write("<td width=\"15\"> <img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.write("</tr>");
        out.write("</table>");
        
        
        if(!consignaciones.isEmpty()){
			//<!--Consignacion-->
			out.write("<table width=\"100%\">");
			out.write("<tr>");
			out.write("<td class=\"contenido\">");
			out.write("<table width=\"100%\">");
			out.write("<tr>");
			out.write("<td class=\"campostitle\">Consignaci&oacute;n</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td valign=\"top\">");
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Fecha</td>");
			out.write("<td>Nombre Banco</td>");
			out.write("<td>C&oacute;digo Sucursal</td>");
			out.write("<td>N&ordm; Consignaci&oacute;n </td>");
			out.write("<td>Valor Consignaci&oacute;n $</td>");
			out.write("<td>Valor a Pagar $</td>");
			out.write("<td align=\"center\">Eliminar</td>");
			out.write("</tr>");
	            
	            
			int numConsignaciones = 0;
			Iterator itAplicaciones = consignaciones.iterator();
			List nApps = new Vector();
			while(itAplicaciones.hasNext()){
				AplicacionPago app = (AplicacionPago) itAplicaciones.next();
				nApps.add(app);
				DocPagoConsignacion consignacion = (DocPagoConsignacion) app.getDocumentoPago();
	
				out.write("<tr>");
				out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/" + getImagen(app)+ "\" width=\"20\" height=\"15\"></td>");
				if(consignacion.getFecha() != null){
					out.write("<td class=\"campositem\">"+ consignacion.getFecha() +"</td>");	
				} else {
					out.write("<td class=\"campositem\">"+ blanco +"</td>");
				}
				
				if(consignacion.getBanco().getNombre() != null){
					out.write("<td class=\"campositem\">"+ consignacion.getBanco().getNombre()+"</td>");	
				} else {
					out.write("<td class=\"campositem\">"+ blanco +"</td>");
				}
				
				if(consignacion.getCodSucursal() != null && consignacion.getCodSucursal().length() >= 0 ){
					out.write("<td class=\"campositem\">"+ consignacion.getCodSucursal() +"</td>");	
				} else {
					out.write("<td class=\"campositem\">"+ blanco +"</td>");
				}
				            
				if(consignacion.getNoConsignacion() != null){
					out.write("<td class=\"campositem\">"+ consignacion.getNoConsignacion() +"</td>");					            
				} else {
					out.write("<td class=\"campositem\">"+ blanco +"</td>");
				}
				
				out.write("<td class=\"campositem\">"+ formateador.format(consignacion.getValorDocumento())+"</td>");
				out.write("<td class=\"campositem\">"+ formateador.format(app.getValorAplicado())+"</td>");
				out.write("<form name=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
				out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION + "\" value=\""+AWPago.ELIMINAR_APLICACION_VALIDACION+"\">");
				out.write("<input type=\"hidden\" name=\""+AWPago.FORMA_PAGO+"\" value=\""+WebKeys.FORMA_PAGO_CONSIGNACION+"\">");
				out.write("<input type=\"hidden\" name=\"" + AWPago.NUMERO_APLICACION + "\" value=\""+numConsignaciones+"\">");
				out.write("<td width=\"50\" align=\"center\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
				out.write("</form>");
				numConsignaciones++;
			}
			
			request.getSession().setAttribute(WebKeys.LISTA_CONSIGNACIONES,nApps);
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
	
			out.write("<hr class=\"linehorizontal\">");
        }
		//<!--Consignacion-->            
		
		//Cheque
		if(!cheques.isEmpty()){
			Iterator itCheque = cheques.iterator();
	
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"contenido\">");
			out.write("<tr>");
			out.write("<tr>");
			out.write("<td class=\"campostitle\">Cheques</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td valign=\"top\">");
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +  "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Nombre Banco</td>");
			out.write("<td>C&oacute;digo Sucursal</td>");
			out.write("<td>N&ordm; Cheque </td>");
			out.write("<td>N&ordm; Cuenta</td>");
			out.write("<td>Valor Cheque $</td>");
			out.write("<td>Valor a Pagar $</td>");
			out.write("<td align=\"center\">Eliminar</td>");
			out.write("</tr>");
			int i = 0;
		
			List nApps = new Vector();
			Iterator itAplicaciones = cheques.iterator();
			while(itAplicaciones.hasNext()){
			
				AplicacionPago app = (AplicacionPago) itAplicaciones.next();
				nApps.add(app);
				DocPagoCheque cheque = (DocPagoCheque) app.getDocumentoPago();
				out.write("<tr>");
				out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/" + getImagen(app) + "\" width=\"20\" height=\"15\"></td>");
				if(cheque.getBanco().getNombre() != null || cheque.getBanco().getNombre().length() > 0){
					out.write("<td class=\"campositem\">"+cheque.getBanco().getNombre()+"</td>");
				} else {
					out.write("<td class=\"campositem\">"+ blanco +"</td>");
				}
				
				if(cheque.getSucursal() != null & cheque.getSucursal().length() > 0 ){
					out.write("<td class=\"campositem\">"+ cheque.getSucursal()+"</td>");	
				} else {
					out.write("<td class=\"campositem\">"+ blanco +"</td>");
				}
				
				if(cheque.getNoCheque() != null){
					out.write("<td class=\"campositem\">"+ cheque.getNoCheque() +"</td>");	
				} else {
					out.write("<td class=\"campositem\">"+ blanco +"</td>");
				}
				
				if(cheque.getNoCuenta() != null){
					out.write("<td class=\"campositem\">"+ cheque.getNoCuenta()+"</td>");	
				} else {
					out.write("<td class=\"campositem\">"+ blanco + "</td>");
				}
				
				out.write("<td class=\"campositem\">"+ formateador.format(cheque.getValorDocumento()) +"</td>");
				out.write("<td class=\"campositem\">"+ formateador.format(app.getValorAplicado()) +"</td>");
				out.write("<form name=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
				out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION + "\" value=\""+AWPago.ELIMINAR_APLICACION_VALIDACION+"\">");
				out.write("<input type=\"hidden\" name=\""+AWPago.FORMA_PAGO+"\" value=\""+WebKeys.FORMA_PAGO_CHEQUE+"\">");
				out.write("<input type=\"hidden\" name=\"" + AWPago.NUMERO_APLICACION + "\" value=\""+i+"\">");
				out.write("<td width=\"50\" align=\"center\"><input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
				out.write("</form>");
				out.write("</tr>");
				i++;
			}		
			request.getSession().setAttribute(WebKeys.LISTA_CHEQUES,nApps);
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("<hr class=\"linehorizontal\">");
        }
        
        
		String efectivo = "";
		if(appEfectivo!=null){
			efectivo = formateador.format(appEfectivo.getValorAplicado());
			getImagen(appEfectivo);
		} else {
			efectivo = blanco;
		}
			
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"contenido\">");
			out.write("<tr>");
			out.write("<td class=\"campostitle\">Efectivo</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td valign=\"top\">");
			out.write("<form name=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/	jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>&nbsp;</td>");
			out.write("<td>Valor Aplicado</td>");
			out.write("<td>Valor a Pagar</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td width=\"10\">$</td>");
			out.write("<td class=\"campositem\">"+ efectivo.replaceAll("\\.","").replaceAll(",","") +"</td>");
			out.write("<td><input name=\"" + AWPago.VALOR_EFECTIVO + "\" type=\"text\" class=\"camposformtext\" id=\"" + AWPago.VALOR_EFECTIVO + "\" value=\""+ efectivo.replaceAll("\\.","").replaceAll(",","") +"\"></td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\" align=\"right\" valign=\"top\">");
			out.write("<tr>"); 
			out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Agregar Efectivo</td>");
			out.write("<td>");
			out.write("<input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\">");
			out.write("<input type=\"hidden\" name=\""+AWPago.FORMA_PAGO+"\" value=\""+WebKeys.FORMA_PAGO_EFECTIVO+"\">");
			out.write("<input type=\"hidden\" name=\""+WebKeys.ACCION+"\" value=\""+AWPago.ADICIONAR_APLICACION_VALIDACION+"\">");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("</form>");
			out.write("</td>");
			out.write("</tr>");
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.write("<tr>");
			out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+ request.getContextPath() +"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.write("<td background=\"" + request.getContextPath() +"/jsp/images/sub_bgn001.gif\" class=\"titulotbcentral\">Total Validado: " + formateador.format(valorAplicadoTotal)+"</td>");
			out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
			out.write("<td width=\"15\"> <img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("<hr class=\"linehorizontal\">");
					
			if(esSuficiente()){
				out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<form name=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
				out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<input type=\"hidden\" name=\"ACCION\" value=\""+AWPago.PROCESAR+"\">");
				out.write("<td width=\"139\"> <input name=\"REGISTRAR\" type=\"image\" id=\"REGISTRAR\" src=\"" + request.getContextPath() + "/jsp/images/btn_regpago.gif\" width=\"139\" height=\"21\" border=\"0\">");
				out.write("</td>");
				out.write("</form>");
				out.write("<form name=\"CancelarPagoCertificados\" method=\"post\" action=\"turno.certificado.registrar.pago.view\" type=\"submit\">");
				out.write("<input type=\"hidden\" name=\"CANCELAR_VALIDACION\" value=\"CANCELAR_VALIDACION\">");
				out.write("<td width=\"139\"> <input name=\"CANCELAR\" type=\"image\" id=\"CANCELAR\" src=\"" + request.getContextPath() + "/jsp/images/btn_cancelar.gif\" width=\"139\" height=\"21\" border=\"0\">");
				out.write("<td>&nbsp;</td>");
				out.write("</form>");
				out.write("</tr>");
				out.write("</table>");
			} else { 
				out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
				out.write("<tr>");
				out.write("<form name=\"pagoCertificados\" method=\"post\" action=\"pago.do\" type=\"submit\">");
				out.write("<td width=\"20\">");
				out.write("<img src=\"" + request.getContextPath() + "/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
				out.write("<input type=\"hidden\" name=\"ACCION\" value=\""+AWPago.VALIDAR+"\">");
				out.write("<td width=\"139\">");
				out.write("<input name=\"VALIDAR\" type=\"image\" id=\"VALIDAR\" src=\"" + request.getContextPath() + "/jsp/images/btn_validarpago.gif\" width=\"139\" height=\"21\" border=\"0\">");
				out.write("</td>");
				out.write("</form>");
				out.write("<form name=\"CancelarPagoCertificados\" method=\"post\" action=\"turno.certificado.registrar.pago.view\" type=\"submit\">");
				out.write("<td width=\"139\">");
				out.write("<input type=\"hidden\" name=\"CANCELAR_VALIDACION\" value=\"CANCELAR_VALIDACION\">"); 
				out.write("<input name=\"CANCELAR\" type=\"image\" id=\"CANCELAR\" src=\"" + request.getContextPath() + "/jsp/images/btn_cancelar.gif\" width=\"139\" height=\"21\" border=\"0\">");
				out.write("</td>");
				out.write("<td>&nbsp;</td>");
				out.write("</tr>");
				out.write("</form>");
				out.write("</table>");
			}

    }
    
    /**
     * Este metodo es le que selecciona la imagen que se muestra si la aplicacion es valida o no.
     * Además va sumando las el valor de las aplicaciones validas  
	 * @param app
	 * @return String con el nombre de la imagen
	 */
	private String getImagen(AplicacionPago app) {
		if(app.getDocumentoPago().getSaldoDocumento() == 0 ){
			if(!(app.getDocumentoPago() instanceof DocPagoEfectivo)){
				hayAplicacionInvalida = true;
			} else {
				double valor = app.getValorAplicado();
				valorAplicadoTotal += valor;
			}
			return "ind_no_ok.gif";
		} if (app.getDocumentoPago().getSaldoDocumento() >= app.getValorAplicado()){
			valorAplicadoTotal += app.getValorAplicado();
			return "ind_ok.gif";
		} if (app.getDocumentoPago().getSaldoDocumento() < app.getValorAplicado()){
			//valorAplicadoTotal += app.getValorAplicado();
			hayAplicacionInvalida = true;
			return "ind_interrogante.gif";
		}
		
		
		return null;
	}


	/**
	 * Este método es el que se encarga de verificar que el valor total 
	 * aplicado sea suficiente para pagar una liquidacion, hay que tener en cuenta
	 * que este metodo utiliza el rango de aceptacion para poder hacer la confirmacion
	 * @return boolean True si el valor aplicado alcanza para pagar
	 */
	private boolean esSuficiente() {
		
		
		if(valorLiquidacion - rango.doubleValue() <= valorAplicadoTotal 
				&& hayAplicacionInvalida == false
				&& valorLiquidacion + rango.doubleValue() >= valorAplicadoTotal){
			return true;
		}
		return false;
	}
}
