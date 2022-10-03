/*
 * Created on 23-sep-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.administracion;

import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPago;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import java.io.IOException;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DatosPagoTurnoManualHelper extends Helper {
	/** Constante para definir un esapcio en blanco de html */
	private static String blanco = "&nbsp;";
	
	/** Acción que debe realizar el formulario **/
	private String sAccion = "turnoManualCertificadoPago.do";

	/**
	 * Constante donde se almacenan la lista de los bancos donde se puede hacer el pago
	 */
	private static ListaElementoHelper listaBancos = null;

	/**
	 * Constante donde se guarda la lista de los cheques que se han ingresado
	 */
	private List cheques;

	/**
	 * Constante donde se guarda la lista de las consignaciones que se han ingresado
	 */
	private List consignaciones;

	/**
	* Forma de pago  de recibo timbre
	*/
	private DocPagoTimbreConstanciaLiquidacion formaReciboTimbre = null;

	/** Valor total de la liquidación*/
	private double valorLiquidacion = 0;

	/** Documento de pago en efectivo */
	private DocPagoEfectivo docEfectivo = null;

	/** Tipo de formato numérico utilizado por el helper */
	private NumberFormat formateador = new DecimalFormat("###,###,###,###,###,###,###,###.00");
	
	/** Tipo de formato numérico utilizado por el helper */
	private NumberFormat formateador2 = new DecimalFormat("#########################.00");	

	/** Flag que indica que se debe mostrar forma de pago consignación*/
	private boolean seQuiereConsignacion = false;

	/** Flag que indica que se debe mostrar forma de pago cheques*/
	private boolean seQuiereCheques = false;

	/** Flag que indica que se debe mostrar forma de pago efectivo*/
	private boolean seQuiereEfectivo = false;

	/** Flag que indica que se debe mostrar forma de timbre de constancia liquidación*/
	private boolean seQuiereTimbreConstanciaLiquidacion = false;

	/** Flag que indica si luego de la generación del turno se debe asignar la
	 *  primera fase al usuario/estación en sesión
	 */
	private boolean asignarEstacion = false;
	
	/**
	 * Constante donde se guarda la lista de las marcas de los cheques que se han ingresado
	 */
	private List marcasCheques;

	/**
	 * Constante donde se guarda la lista de las marcas las consignaciones que se han ingresado
	 */
	private List marcasConsignaciones;

	/**
	 * Constructor Helper Pagos.
	 * Si se desea que el pago efectivo no sea aplicado por defecto toca colocar
	 * la constante @see <WebKeys.GENERAR_PAGO_EFECTIVO> como Boolean false
	 *
	 * @param context
	 * @param valorLiquidado
	 * @param listaConsignaciones lista con las consignaciones agregadas por el usuario
	 * @param listaCheques lista con los cheques agregados por el usuario
	 * @param appEfectivo Pago en efectivo
	 * @param timbreBanco Pago con recibo de constancia de Liquidación en el banco
	 */
	public DatosPagoTurnoManualHelper(HttpServletRequest request, double valorLiquidado,
		List listaConsignaciones, List listaCheques,
		AplicacionPago appEfectivo, AplicacionPago timbreBanco,List listaMarcasConsignaciones, List listaMarcasCheques) {
		Boolean generarEfectivo = (Boolean) request.getSession().getAttribute(WebKeys.GENERAR_PAGO_EFECTIVO);
		generarEfectivo = (generarEfectivo == null) ? new Boolean(true)
													: generarEfectivo;

		/*if ((appEfectivo == null) && generarEfectivo.booleanValue()) {
			DocumentoPago documentoEfectivo = new DocPagoEfectivo(valorLiquidado);
			appEfectivo = new AplicacionPago(documentoEfectivo, valorLiquidado);
			request.getSession().setAttribute(WebKeys.APLICACION_EFECTIVO,
				appEfectivo);
			request.getSession().setAttribute(WebKeys.GENERAR_PAGO_EFECTIVO,
				new Boolean(false));
		}*/

		if (listaBancos == null) {
			this.cargarListaBancos(request.getSession().getServletContext());
		}

		if (listaConsignaciones == null) {
			listaConsignaciones = new Vector();
		}

		if (listaCheques == null) {
			listaCheques = new Vector();
		}
		
		if (listaMarcasConsignaciones == null) {
			listaMarcasConsignaciones = new ArrayList();
		}
		this.marcasConsignaciones = listaMarcasConsignaciones;
		if (listaMarcasCheques == null) {
			listaMarcasCheques = new ArrayList();
		}
		this.marcasCheques = listaMarcasCheques;
		this.valorLiquidacion = valorLiquidado;
		this.consignaciones = listaConsignaciones;
		this.cheques = listaCheques;

		if (appEfectivo != null) {
			docEfectivo = (DocPagoEfectivo) appEfectivo.getDocumentoPago();
		}

		if (timbreBanco != null) {
			this.formaReciboTimbre = (DocPagoTimbreConstanciaLiquidacion) timbreBanco.getDocumentoPago();
		}
	}

	/**
	 * Carga la lista de bancos que se encuentra en contexto y la convierte
	 * en una lista helper
	 *
	 * @param context
	 */
	private void cargarListaBancos(ServletContext context) {
		List bancos = (List) context.getAttribute(WebKeys.LISTA_BANCOS);

		if (bancos == null) {
			bancos = new Vector();
		}

		listaBancos = new ListaElementoHelper();
		listaBancos.setCssClase("camposformtext");
		listaBancos.setId(AWPago.COD_BANCO);
		listaBancos.setNombre(AWPago.COD_BANCO);

		Iterator itBancos = bancos.iterator();
		List nBancos = new Vector();

		while (itBancos.hasNext()) {
			Banco banco = (Banco) itBancos.next();
			nBancos.add(new ElementoLista(banco.getIdBanco(),
					banco.getIdBanco() + " - " + banco.getNombre()));
		}

		listaBancos.setTipos(nBancos);
	}

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
		//HttpSession session = request.getSession();

		/*
		String numliquidacion = (String) session.getAttribute(WebKeys.VALOR_LIQUIDACION);

		if (numliquidacion != null) {
			valorLiquidacion = Double.valueOf(numliquidacion).doubleValue();
		} else {
			//throw new HelperException("El valor de la liquidacion es invalido");
		}
		*/
		/*
		consignaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);

		if (consignaciones == null) {
			consignaciones = new Vector();
		}
		*/
		/*
		cheques = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);

		if (cheques == null) {
			cheques = new Vector();
		}
		*/
		/*
		AplicacionPago appEfectivo = (AplicacionPago) session.getAttribute(WebKeys.APLICACION_EFECTIVO);

		if (appEfectivo != null) {
			docEfectivo = (DocPagoEfectivo) appEfectivo.getDocumentoPago();
		}

		this.formaReciboTimbre = (DocPagoTimbreConstanciaLiquidacion) session.getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
		*/
	}

	/**
	 * Este método pinta en la pantalla de manera agradable las formas de pago
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws IOException, HelperException {
		//Aca se muestran las opciones del pago cuando previamente no se ha seleccionado ninguno
		double valorTotalLiquidado = 0;

		out.write(
			"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
		out.write("<tr>");
		out.write("<td><img name=\"tabla_central_r1_c1\" src=\"" +
			request.getContextPath() +
			"/jsp/images/tabla_central_r1_c1.gif\" width=\"7\" height=\"29\" border=\"0\" alt=\"\"></td>");
		out.write("<td background=\"" + request.getContextPath() +
			"/jsp/images/tabla_central_bgn003.gif\">");
		out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>");
		out.write("<td background=\"" + request.getContextPath() +
			"/jsp/images/tabla_central_bgn001.gif\" class=\"titulotbcentral\">PAGO</td>");
		out.write("<td width=\"9\"><img name=\"tabla_central_r1_c3\" src=\"" +
			request.getContextPath() +
			"/jsp/images/tabla_central_r1_c3.gif\" width=\"9\" height=\"29\" border=\"0\" alt=\"\"></td>");
		out.write(
			"<td width=\"20\" align=\"center\" valign=\"top\" background=\"" +
			request.getContextPath() +
			"/jsp/images/tabla_central_bgn002.gif\">");
		out.write(
			"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		out.write("<tr>");
		out.write("<td><img src=\"" + request.getContextPath() +
			"/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("<td width=\"12\"><img name=\"tabla_central_r1_c5\" src=\"" +
			request.getContextPath() +
			"/jsp/images/tabla_central_r1_c5.gif\" width=\"12\" height=\"29\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("<td><img name=\"tabla_central_pint_r1_c7\" src=\"" +
			request.getContextPath() +
			"/jsp/images/tabla_central_pint_r1_c7.gif\" width=\"11\" height=\"29\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td width=\"7\" background=\"" + request.getContextPath() +
			"/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
		out.write(
			"<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");

		out.write(
			"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.write("<tr>");
		out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
			request.getContextPath() +
			"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("<td background=\"" + request.getContextPath() +
			"/jsp/images/sub_bgn001.gif\" class=\"bgnsub\">Liquidaci&oacute;n</td>");
		out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
			request.getContextPath() +
			"/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td width=\"15\"> <img name=\"sub_r1_c4\" src=\"" +
			request.getContextPath() +
			"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");

		out.write(
			"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
			"/jsp/images/ico_pago.gif\" width=\"16\" height=\"21\"></td>");
		out.write("<td width=\"293\">Valor</td>");
		out.write("<td width=\"11\">$</td>");
		out.write("<td width=\"445\" class=\"campositem\">" +
			formateador.format(valorLiquidacion) + "</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<br>");

		if (this.seQuiereTimbreConstanciaLiquidacion) {
			this.pintarPagoTimbreBanco(request, out);
		}

		//Aca se pintan los campos cuando la forma de pagar es consignaciones
		out.write("<table width=\"100%\">");
		out.write("<tr>");
		out.write("<td class=\"contenido\">");

		if (seQuiereConsignacion) {
			valorTotalLiquidado = pintarFormaPagoConsignacion(request, out,
					valorTotalLiquidado);
		}

		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<hr class=\"linehorizontal\">");

		//Aca se muestran los campos para que el Ciudadano pague con  Cheques
		out.write("<br>");
		out.write(
			"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"contenido\">");

		if (seQuiereCheques) {
			valorTotalLiquidado = pintarFormaPagoCheques(request, out,
					valorTotalLiquidado);
		}

		out.write("</tr>");
		valorTotalLiquidado = valorLiquidacion - valorTotalLiquidado;

		if (valorTotalLiquidado < 0) {
			valorTotalLiquidado = 0;
		}

		if (seQuiereEfectivo) {
			pintarFormaPagoEfectivo(request, out, valorTotalLiquidado);
		}

		if (seQuiereEfectivo || seQuiereCheques || seQuiereConsignacion ||
				this.seQuiereTimbreConstanciaLiquidacion) {
			pintarTablaEfectuarCancelarPago(request, out);
		} else {
			pintarTablaNoExisteFormasPagoCirculo(request, out);
		}
	}

	/**
	 * Genera el código HTML de la forma de pago Consignación
	 *
	 * @param request
	 * @param out OutputStream donde se escibre el código html
	 * @param valorTotalLiquidado valorLiquidado hasta el momento
	 * @return  Valor liquidado por esta forma de pago
	 * @throws IOException
	 * @throws HelperException
	 */
	private double pintarFormaPagoConsignacion(HttpServletRequest request,
		JspWriter out, double valorTotalLiquidado)
		throws IOException, HelperException {
		out.write("<table width=\"100%\">");
		out.write("<tr>");
		out.write("<td class=\"campostitle\">Consignaci&oacute;n</td>");
		out.write("</tr>");

		if ((consignaciones != null) && !consignaciones.isEmpty()) {
			//Aca se pinta el encabezado de los campos que han sido ingresados
			out.write("<tr>");
			out.write("<td valign=\"top\">");
			out.write(
				"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\" height=\"17\"><img src=\"" +
				request.getContextPath() +
				"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Fecha</td>");
			out.write("<td>Nombre Banco</td>");
			out.write("<td>C&oacute;digo Sucursal</td>");
			out.write("<td>N&ordm; Consignacion</td>");
			out.write("<td>Valor Consignacion $</td>");
			out.write("<td>Valor a Pagar $</td>");
			out.write("<td>Eliminar</td>");
			out.write("</tr>");

			Iterator itConsignacion = consignaciones.iterator();
			int i = 0;

			while (itConsignacion.hasNext()) {
				AplicacionPago aplicacion = (AplicacionPago) itConsignacion.next();
				DocPagoConsignacion consignacion = (DocPagoConsignacion) aplicacion.getDocumentoPago();
				Banco banco = consignacion.getBanco();

				//Aca se llenan los campos que ya han sido ingresados
				out.write("<tr>");
				out.write("<td>&nbsp;</td>");

				if (consignacion.getFecha() != null) {
					out.write("<td class=\"campositem\">" +
						consignacion.getFecha() + "</td>");
				} else {
					out.write("<td class=\"campositem\">" + blanco + "</td>");
				}

				if (banco.getNombre() != null) {
					out.write("<td class=\"campositem\">" + banco.getNombre() +
						"</td>");
				} else {
					out.write("<td class=\"campositem\">" + blanco + "</td>");
				}

				if (consignacion.getCodSucursal() != null) {
					out.write("<td class=\"campositem\">" +
						consignacion.getCodSucursal() + "</td>");
				} else {
					out.write("<td class=\"campositem\">" + blanco + "</td>");
				}

				if (consignacion.getNoConsignacion() != null) {
					out.write("<td class=\"campositem\">" +
						consignacion.getNoConsignacion() + "</td>");
				} else {
					out.write("<td class=\"campositem\">" + banco + "</td>");
				}

				out.write("<td class=\"campositem\">" +
					formateador.format(consignacion.getValorDocumento()) +
					"</td>");
				valorTotalLiquidado += aplicacion.getValorAplicado();
				out.write("<td class=\"campositem\">" +
					formateador.format(aplicacion.getValorAplicado()) +
					"</td>");

				out.write(
					"<form name=\"pagoCertificados\" method=\"post\" action=\"turnoManualCertificadoPago.do\" type=\"submit\">");

				out.write("<input type=\"hidden\" name=\"ACCION\" value=\"" +
					AWPago.ELIMINAR_APLICACION + "\">");
				out.write(
					"<input type=\"hidden\" name=\"NUMERO_APLICACION\" value=\"" +
					i + "\">");
				out.write("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO +
					"\" value=\"" + WebKeys.FORMA_PAGO_CONSIGNACION + "\">");
				out.write(
					"<td><input name=\"imageField\" type=\"image\" src=\"" +
					request.getContextPath() +
					"/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
				out.write("</form>");
				if(((Boolean)marcasConsignaciones.get(i)).booleanValue()){
					out.write(
						"<td><image src=\"" +
						request.getContextPath() +
						"/jsp/images/ico_advertencia.gif\" width=\"16\" height=\"21\" border=\"0\" alt=\"Aplicación existente en el sistema\"></td>");					
				}
				out.write("</tr>");
				i++;
			}

			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
		}

		//Aca se pintan los campos para que se ingrese una nueva consignacion
		out.write(
			"<form name=\"pagoCertificados\" method=\"post\" action=\"turnoManualCertificadoPago.do\" type=\"submit\">");
		out.write("<tr>");
		out.write("<td valign=\"top\">");
		out.write(
			"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\" height=\"17\"><img src=\"" +
			request.getContextPath() +
			"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Fecha</td>");
		out.write("<td align=\"left\"></td>");
		out.write("<td>Nombre Banco </td>");
		out.write("<td>C&oacute;digo Sucursal</td>");
		out.write("<td>N&ordm; Consignaci&oacute;n </td>");
		out.write("<td>Valor Consignaci&oacute;n $</td>");
		out.write("<td>Valor Pagado $</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>&nbsp;</td>");
		out.write("<td> <input name=\"" + AWPago.FECHA_CONSIGNACION +
			"\" type=\"text\" size=\"10\" class=\"camposformtext\" id=\"" +
			AWPago.FECHA_CONSIGNACION + "\"></td>");
		out.write("<td align=\"left\"><a href=\"javascript:NewCal('" +
			AWPago.FECHA_CONSIGNACION + "','ddmmmyyyy',true,24)\"><img src=\"" +
			request.getContextPath() +
			"/jsp/images/ico_calendario.gif\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('" +
			request.getContextPath() + "')\"></a></td>");
		out.write("<td>");

		if (listaBancos != null) {
			listaBancos.render(request, out);
		}

		out.write("<td><input name=\"" + AWPago.COD_SUCURSAL_BANCO +
			"\" type=\"text\" class=\"camposformtext\" id=\"" +
			AWPago.COD_SUCURSAL_BANCO + "\"></td>");
		out.write("<td><input name=\"" + AWPago.NUM_CONSIGNACION +
			"\" type=\"text\" class=\"camposformtext\" id=\"" +
			AWPago.NUM_CONSIGNACION + "\"></td>");
		out.write("<td><input name=\"" + AWPago.VALOR_CONSIGNACION +
			"\" type=\"text\" class=\"camposformtext\" id=\"" +
			AWPago.VALOR_CONSIGNACION + "\"></td>");
		out.write("<td><input name=\"" + AWPago.VALOR_APLICADO +
			"\" type=\"text\" class=\"camposformtext\" id=\"" +
			AWPago.VALOR_APLICADO + "\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td align=\"right\" valign=\"top\">");
		out.write(
			"<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
			"/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<input type=\"hidden\" name=\"ACCION\" value=\"" +
			AWPago.ADICIONAR_APLICACION + "\">");
		out.write("<td>Agregar Consignaci&oacute;n</td>");
		out.write("<td>");
		out.write("<input name=\"imageField2\" type=\"image\" src=\"" +
			request.getContextPath() +
			"/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\">");
		out.write("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO +
			"\" value=\"" + WebKeys.FORMA_PAGO_CONSIGNACION + "\">");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</form>");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");

		return valorTotalLiquidado;
	}

	/**
	 * Genera el código HTML de la forma de pago de cheques
	 *
	 * @param request
	 * @param out OutputStream donde se escibre el código html
	 * @param valorTotalLiquidado  valor liquidado hasta el momento
	 * @return  valor liquidado en esta forma de pago
	 * @throws IOException
	 * @throws HelperException
	 */
	private double pintarFormaPagoCheques(HttpServletRequest request,
		JspWriter out, double valorTotalLiquidado)
		throws IOException, HelperException {
		out.write("<tr>");
		out.write("<td class=\"campostitle\">Cheques</td>");
		out.write("</tr>");

		if ((cheques != null) && !cheques.isEmpty()) {
			Iterator itCheques = cheques.iterator();

			//Este es el encabezado donde se identifican los campos que ya han sido introducidos
			out.write("<tr>");
			out.write("<td valign=\"top\">");
			out.write(
				"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\" height=\"17\"><img src=\"" +
				request.getContextPath() +
				"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Fecha</td>");
			out.write("<td>Nombre Banco</td>");

			//out.write("<td>C&oacute;digo Sucursal</td>");
			out.write("<td>N&ordm; Cheque </td>");

			//out.write("<td>N&ordm; Cuenta</td>");
			out.write("<td>Valor Cheque $</td>");
			out.write("<td>Valor a Pagar $</td>");
			out.write("<td>Eliminar</td>");
			out.write("</tr>");

			int i = 0;

			while (itCheques.hasNext()) {
				AplicacionPago aplicacion = (AplicacionPago) itCheques.next();
				DocumentoPago documento = aplicacion.getDocumentoPago();
				DocPagoCheque cheque = (DocPagoCheque) documento;
				Banco banco = cheque.getBanco();

				//Se llenan los campos que ya han sido introducidos
				out.write("<tr>");
				out.write("<td>&nbsp;</td>");

				if (cheque.getFecha() != null) {
					out.write("<td class=\"campositem\">" + cheque.getFecha() +
						"</td>");
				} else {
					out.write("<td class=\"campositem\">" + blanco + "</td>");
				}

				if (banco.getNombre() != null) {
					out.write("<td class=\"campositem\">" + banco.getNombre() +
						"</td>");
				} else {
					out.write("<td class=\"campositem\">" + blanco + "</td>");
				}

				/*
				if (cheque.getSucursal() != null) {
					out.write("<td class=\"campositem\">" +
						cheque.getSucursal() + "</td>");
				} else {
					out.write("<td class=\"campositem\">" + blanco + "</td>");
				}
				*/
				if (cheque.getNoCheque() != null) {
					out.write("<td class=\"campositem\">" +
						cheque.getNoCheque() + "</td>");
				} else {
					out.write("<td class=\"campositem\">" + blanco + "</td>");
				}

				/*
				if (cheque.getNoCuenta() != null) {
					out.write("<td class=\"campositem\">" +
						cheque.getNoCuenta() + "</td>");
				} else {
					out.write("<td class=\"campositem\">" + blanco + "</td>");
				}
				*/
				out.write("<td class=\"campositem\">" +
					formateador.format(cheque.getValorDocumento()) + "</td>");
				out.write("<td class=\"campositem\">" +
					formateador.format(aplicacion.getValorAplicado()) +
					"</td>");
				valorTotalLiquidado += aplicacion.getValorAplicado();
				out.write(
					"<form name=\"pagoCertificados\" method=\"post\" action=\"turnoManualCertificadoPago.do\" type=\"submit\">");
				out.write("<input type=\"hidden\" name=\"ACCION\" value=\"" +
					AWPago.ELIMINAR_APLICACION + "\">");
				out.write(
					"<input type=\"hidden\" name=\"NUMERO_APLICACION\" value=\"" +
					i + "\">");
				out.write("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO +
					"\" value=\"" + WebKeys.FORMA_PAGO_CHEQUE + "\">");
				out.write(
					"<td><input name=\"imageField\" type=\"image\" src=\"" +
					request.getContextPath() +
					"/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
				out.write("</form>");
				if(((Boolean)marcasCheques.get(i)).booleanValue()){
					out.write(
						"<td><image src=\"" +
						request.getContextPath() +
						"/jsp/images/ico_advertencia.gif\" width=\"16\" height=\"21\" border=\"0\" alt=\"Aplicación existente en el sistema\"></td>");					
				}
				out.write("</tr>");
				i++;
			}

			out.write("</table>");
			out.write("</td>");
		}

		out.write("</tr>");

		//Aca se pintan los campos donde se puede registrar algun cheque
		out.write(
			"<form name=\"pagoCertificados\" method=\"post\" action=\"turnoManualCertificadoPago.do\" type=\"submit\">");
		out.write("<input type=\"hidden\" name=\"ACCION\" value=\"" +
			AWPago.ADICIONAR_APLICACION + "\">");
		out.write("<tr>");
		out.write("<td valign=\"top\">");
		out.write(
			"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\" height=\"17\"><img src=\"" +
			request.getContextPath() +
			"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Fecha</td>");
		out.write("<td></td>");
		out.write("<td>Nombre Banco</td>");

		//out.write("<td>C&oacute;digo Sucursal</td>");
		out.write("<td>N&ordm; Cheque </td>");

		//out.write("<td>N&ordm; Cuenta</td>");
		out.write("<td>Valor Cheque $</td>");
		out.write("<td>Valor a Pagar $</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>&nbsp;</td>");
		out.write("<td> <input name=\"" + AWPago.FECHA_CHEQUE +
			"\" type=\"text\" size=\"10\" class=\"camposformtext\" id=\"" +
			AWPago.FECHA_CHEQUE + "\"></td>");
		out.write("<td align=\"left\"><a href=\"javascript:NewCal('" +
			AWPago.FECHA_CHEQUE + "','ddmmmyyyy',true,24)\"><img src=\"" +
			request.getContextPath() +
			"/jsp/images/ico_calendario.gif\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('" +
			request.getContextPath() + "')\"></a></td>");
		out.write("<td>");

		if (listaBancos != null) {
			listaBancos.render(request, out);
		}

		out.write("</td>");

		/*
		out.write("<td><input name=\"" + AWPago.COD_SUCURSAL_BANCO +
			"\" type=\"text\" size=\"14\" class=\"camposformtext\" id=\"" +
			AWPago.COD_SUCURSAL_BANCO + "\"></td>");
		*/
		out.write("<td><input name=\"" + AWPago.NUM_CHEQUE +
			"\" type=\"text\" size=\"14\" class=\"camposformtext\" id=\"" +
			AWPago.NUM_CHEQUE + "\"></td>");

		/*
		out.write("<td><input name=\"" + AWPago.NUM_CUENTA +
			"\" type=\"text\" size=\"14\" class=\"camposformtext\" id=\"" +
			AWPago.NUM_CUENTA + "\"></td>");
		*/
		out.write("<td><input name=\"" + AWPago.VALOR_CHEQUE +
			"\" type=\"text\" size=\"14\" class=\"camposformtext\" id=\"" +
			AWPago.VALOR_CHEQUE + "\"></td>");
		out.write("<td><input name=\"" + AWPago.VALOR_APLICADO +
			"\" type=\"text\" size=\"14\" class=\"camposformtext\" id=\"" +
			AWPago.VALOR_CHEQUE + "\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td align=\"right\" valign=\"top\">");
		out.write(
			"<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
			"/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td>Agregar Cheque</td>");
		out.write("<td>");
		out.write("<input name=\"imageField\" type=\"image\" src=\"" +
			request.getContextPath() +
			"/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\">");
		out.write("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO +
			"\" value=\"" + WebKeys.FORMA_PAGO_CHEQUE + "\">");
		out.write("</td>");
		out.write("</tr>");
		out.write("</form>");
		out.write("</table>");
		out.write("<hr class=\"linehorizontal\">");
		out.write("</td>");

		return valorTotalLiquidado;
	}

	/**
	 * Generar el código HTML indicando que el circulo actual no tiene
	 * asociado formas de pago
	 *
	 * @param request
	 * @param out OutputStream de escritura del codigo html
	 * @throws IOException
	 */
	private void pintarTablaNoExisteFormasPagoCirculo(
		HttpServletRequest request, JspWriter out) throws IOException {
		out.write(
			"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
			"/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
		out.write("<td class=\"campositem\">");
		out.write("El circulo no tiene configurada ninguna opcion de pago");
		out.write("</td>");
		out.write("<td>&nbsp;</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("<td width=\"11\" background=\"" + request.getContextPath() +
			"/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><img name=\"tabla_central_r3_c1\" src=\"" +
			request.getContextPath() +
			"/jsp/images/tabla_central_r3_c1.gif\" width=\"7\" height=\"6\" border=\"0\" alt=\"\"></td>");
		out.write("<td background=\"" + request.getContextPath() +
			"/jsp/images/tabla_central_bgn006.gif\"><img src=\"" +
			request.getContextPath() +
			"/jsp/images/spacer.gif\" width=\"15\" height=\"6\"></td>");
		out.write("<td><img name=\"tabla_central_pint_r3_c7\" src=\"" +
			request.getContextPath() +
			"/jsp/images/tabla_central_pint_r3_c7.gif\" width=\"11\" height=\"6\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");
	}

	/**
	 * Generar el código HTML de la tabla que muestra los botones de
	 * Aceptar y cancelar el pago
	 *
	 * @param request
	 * @param out OutputStream de escritura del codigo html
	 * @throws IOException
	 */
	private void pintarTablaEfectuarCancelarPago(HttpServletRequest request,
		JspWriter out) throws IOException {

		out.write("\n<script>\n");
		out.write("function continuarPagoReparto(){\n");
		out.write("		document.ACEPTARPAGO.ACCION.value = 'PROCESAR_REGISTRO_CONTINUAR'\n");
		out.write("		document.ACEPTARPAGO.submit();");		
		out.write("}\n");	
		out.write("</script>\n");        			
			
			
		//Campo donde se pintan los botones de Verificar y cancelar
		out.write(
			"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() +
			"/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
		out.write(
			"<form name=\"ACEPTARPAGO\" method=\"post\" action=\"" + sAccion + "\" type=\"submit\">");
		out.write("<td width=\"150\">");

		//out.write("<input type=\"hidden\" name=\"ACCION\" value=\""+AWPago.VALIDAR+"\">");
		out.write("<input type=\"hidden\" name=\"ACCION\" value=\"" +
			AWPago.PROCESAR + "\">");

		if (asignarEstacion) {
			out.write("<input type=\"hidden\" name=" + AWPago.ASIGNAR_ESTACION +
				" value=\"TRUE\">");
		}

		//out.write("<input name=\"ACEPTAR\" type=\"image\" id=\"ACEPTAR\" src=\"" + request.getContextPath() + "/jsp/images/btn_validarpago.gif\" width=\"139\" height=\"21\" border=\"0\">");
		out.write("<input name=\"ACEPTAR\" type=\"image\" id=\"ACEPTAR\" src=\"" +	request.getContextPath() + "/jsp/images/btn_regpago.gif\" width=\"139\" height=\"21\" border=\"0\" alt=\"Registrar el pago.\">");
		out.write("</td>");
		

		Boolean pagoRegistro = (Boolean)request.getSession().getAttribute("PAGO_REGISTRO_LIQUIDACION");        
		Boolean continuar = (Boolean)request.getSession().getAttribute("CONTINUAR");        
        
		if(pagoRegistro!=null && pagoRegistro.booleanValue() && continuar!=null && continuar.booleanValue()){

			Boolean esCajero = (Boolean)request.getSession().getAttribute(WebKeys.ES_CAJERO_REGISTRO);
			if(esCajero!=null && esCajero.booleanValue()){
				out.write("<td width=\"150\">");
				out.write("<a href=\"javascript:continuarPagoReparto()\"><img src=\"" + request.getContextPath() +"/jsp/images/btn_seguir.gif\" width=\"139\" height=\"21\" border=\"0\" alt=\"Registrar el pago y radicar el turno de solicitud de registro.\"></a>");			
				out.write("</td>");				
			}
			
		}			
		
		
		
		out.write("</form>");

		//out.write("<form name=\"CancelarPagoCertificados\" method=\"post\" action=\"turno.certificado.registrar.pago.view\" type=\"submit\">");
		out.write("<td width=\"150\">");

		String funcion = "";
		String vistaSolicitud = (String) request.getSession().getAttribute(WebKeys.VISTA_SOLICITUD);

		if (vistaSolicitud != null) {
			funcion = vistaSolicitud;
		} else {
			String vistaAnterior = (String)request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
			vistaAnterior = vistaAnterior !=null 
				? vistaAnterior
				: "javascript:history.back();";
			funcion = vistaAnterior;
		}

		out.write("<a href=\"" + funcion + "\"><img src=\"" +
			request.getContextPath() +
			"/jsp/images/btn_cancelar.gif\" width=\"139\" height=\"21\" border=\"0\" alt=\"Cancelar.\"></a>");
		out.write("</td>");

		//out.write("</form>");
		out.write("<td>&nbsp;</td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("<td width=\"11\" background=\"" + request.getContextPath() +
			"/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><img name=\"tabla_central_r3_c1\" src=\"" +
			request.getContextPath() +
			"/jsp/images/tabla_central_r3_c1.gif\" width=\"7\" height=\"6\" border=\"0\" alt=\"\"></td>");
		out.write("<td background=\"" + request.getContextPath() +
			"/jsp/images/tabla_central_bgn006.gif\"><img src=\"" +
			request.getContextPath() +
			"/jsp/images/spacer.gif\" width=\"15\" height=\"6\"></td>");
		out.write("<td><img name=\"tabla_central_pint_r3_c7\" src=\"" +
			request.getContextPath() +
			"/jsp/images/tabla_central_pint_r3_c7.gif\" width=\"11\" height=\"6\" border=\"0\" alt=\"\"></td>");
		out.write("</tr>");
		out.write("</table>");
	}

	/**
	 * Genera el código html de la forma de pago en efectivo
	 *
	 * @param request
	 * @param out  OutputStream de escritura del codigo html
	 * @param valorTotalLiquidado  valor de la liquidación
	 * @throws IOException
	 */
	private void pintarFormaPagoEfectivo(HttpServletRequest request,
		JspWriter out, double valorTotalLiquidado) throws IOException {
		if (docEfectivo == null) {
			out.write("<tr>");
			out.write("<td class=\"campostitle\">Efectivo</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td valign=\"top\">");
			out.write(
				"<form name=\"pagoCertificados\" method=\"post\" action=\"turnoManualCertificadoPago.do\" type=\"submit\">");
			out.write(
				"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" +
				request.getContextPath() +
				"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Valor</td>");
			out.write("<td width=\"10\">$</td>");
			out.write("<td><input name=\"" + AWPago.VALOR_EFECTIVO +
				"\" type=\"text\" class=\"camposformtext\" value=\"" +
				formateador2.format(valorTotalLiquidado) + "\" id=\"" +				
				AWPago.VALOR_EFECTIVO + "\">");
				
			//.replaceAll("\\.", "").replaceAll(",", "")				
				
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write(
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\" align=\"right\" valign=\"top\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" +
				request.getContextPath() +
				"/jsp/images/ind_vinculo.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Agregar Efectivo</td>");
			out.write("<td>");
			out.write("<input name=\"imageField\" type=\"image\" src=\"" +
				request.getContextPath() +
				"/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\">");
			out.write("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO +
				"\" value=\"" + WebKeys.FORMA_PAGO_EFECTIVO + "\">");
			out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION +
				"\" value=\"" + AWPago.ADICIONAR_APLICACION + "\">");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("</form>");
			out.write("</td>");
			out.write("</td>");
			out.write("</tr>");
			out.write("</tr>");
			out.write("</table>");
			out.write("<hr class=\"linehorizontal\">");
		} else {
			out.write("<tr>");
			out.write("<td class=\"campostitle\">Efectivo</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td valign=\"top\">");
			out.write(
				"<form name=\"pagoCertificados\" method=\"post\" action=\"turnoManualCertificadoPago.do\" type=\"submit\">");
			out.write(
				"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" +
				request.getContextPath() +
				"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Valor</td>");
			out.write("<td width=\"10\">$</td>");
			out.write("<td class=\"campositem\">" +
				formateador2.format(docEfectivo.getValorDocumento())
							+
				"</td>");
				//.replaceAll("\\.", "").replaceAll(",", "")
			out.write("<td width=\"40\">Eliminar</td>");
			out.write("<td>&nbsp;</td>");
			out.write(
				"<td width=\"40\"><input name=\"imageField\" type=\"image\" src=\"" +
				request.getContextPath() +
				"/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\" border=\"0\"></td>");
			out.write("<input type=\"hidden\" name=\"" + WebKeys.ACCION +
				"\" value=\"" + AWPago.ELIMINAR_APLICACION + "\">");
			out.write("<input type=\"hidden\" name=\"" + AWPago.FORMA_PAGO +
				"\" value=\"" + WebKeys.FORMA_PAGO_EFECTIVO + "\">");
			out.write("</tr>");
			out.write("</table>");
			out.write("</form>");
			out.write("</td>");
			out.write("</tr>");
			out.write("</tr>");
			out.write("</table>");
			out.write("<hr class=\"linehorizontal\">");
		}
	}

	/**
	 * Genera el código html para la forma de pago Timbre Banco
	 *
	 * @param request
	 * @param out OutputStream de escritura del codigo html
	 * @throws IOException
	 */
	private void pintarPagoTimbreBanco(HttpServletRequest request, JspWriter out)
		throws IOException {
		out.println("<table width='100%'>");
		out.println("<tr>");
		out.println("<td class='contenido'><table width='100%'>");
		out.println("<tr>");
		out.println(
			"<td class='campostitle'>Timbre Constancia de Liquidaci&oacute;n </td>");
		out.println("</tr>");

		//Valor del registro de timbre agregado
		if (this.formaReciboTimbre != null) {
			out.println("<tr>");
			out.println(
				"<td valign='top'><table width='100%' border='0' cellpadding='0' cellspacing='2' class='camposform'>");
			out.println("<tr>");
			out.println("<td width='20' height='17'><img src='" +
				request.getContextPath() +
				"/jsp/images/ind_campotxt.gif' width='20' height='15'></td>");
			out.println("<td>Fecha</td>");
			//out.println("<td>N&uacute;mero liquidaci&oacute;n </td>");
			out.println("<td>N&uacute;mero de timbre </td>");
			out.println("<td>Valor Pagado</td>");
			out.println("<td>Valor a Pagar $</td>");
			out.println("<td>Eliminar</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>&nbsp;</td>");
			out.println("<td class='campositem'>" +
				this.formaReciboTimbre.getFecha() + "</td>");
			/*out.println("<td class='campositem'>" +
				this.formaReciboTimbre.getFecha() + "</td>");*/
			out.println("<td class='campositem'>" +
				this.formaReciboTimbre.getNumeroTimbre() + "&nbsp;</td>");
			out.println("<td class='campositem'>" +
				this.formateador.format(this.formaReciboTimbre.getValorDocumento()) + "</td>");
			out.println("<td class='campositem'>" +
				this.formateador.format(this.formaReciboTimbre.getValorDocumento()) + "</td>");
			out.println(
				"<form name='pagoCertificados' method='post' action='turnoManualCertificadoPago.do' type='submit'>");
			out.println(
				"<input type='hidden' name='ACCION' value='ELIMINAR_APLICACION'>");
			out.println(
				"<input type='hidden' name='FORMA_PAGO' value='"+ WebKeys.FORMA_PAGO_TIMBRE_BANCO +"'>");
			out.println("<td><input name='imageField' type='image' " + "src='" +
				request.getContextPath() +
				"/jsp/images/btn_mini_eliminar.gif' width='35' height='13' border='0'></td>");
			out.println("</form>");
			out.println("</tr>");
			out.println("</table></td>");
			out.println("</tr>");
		}

		//Fin Valor del registro de timbre agregado
		//Agregar nuevo registro
		out.println(
			"<form name='pagoCertificados' method='post' action='turnoManualCertificadoPago.do' type='submit'>");
		out.println("<tr>");
		out.println("<td valign='top'><table width='100%' border='0' " +
			"cellpadding='0' cellspacing='2' class='camposform'>");
		out.println("<tr>");
		out.println("<td width='20' height='17'><img src='" +
			request.getContextPath() + "/jsp/images/ind_campotxt.gif' " +
			"width='20' height='15'></td>");
		out.println("<td>Fecha</td>");
		out.println("<td align='left'></td>");
		out.println("<td>N&uacute;mero liquidaci&oacute;n </td>");
		out.println("<td>N&uacute;mero de timbre </td>");
		out.println("<td>Valor Pagado</td>");
		out.println("<td>Valor a Pagar $</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>&nbsp;</td>");
		out.println("<td><input name='" + WebKeys.CAMPO_FECHA_PAGO_TIMBRE +
			"' type='text' size='10' " +
			"class='camposformtext' id='FECHA_PAGO_TIMBRE'></td>");
		out.println("<td align='left'>" + "<a href=\"javascript:NewCal('" +
			WebKeys.CAMPO_FECHA_PAGO_TIMBRE + "','ddmmmyyyy',true,24)\">" +
			"<img src='" + request.getContextPath() +
			"/jsp/images/ico_calendario.gif' width='16' height='21' border='0' " +
			"onClick=\"javascript:Valores('/proyecto')\"></a></td>");
		out.println("<td><input name='" +
			WebKeys.CAMPO_NUMERO_SOLICITUD_LIQUIDACION +
			"' type='text' class='camposformtext' " + "id='" +
			WebKeys.CAMPO_NUMERO_SOLICITUD_LIQUIDACION + "'></td>");
		out.println("<td><input name='" + WebKeys.CAMPO_NUMERO_TIMBRE_BANCO +
			"' type='text' class='camposformtext' " + "id='" +
			WebKeys.CAMPO_NUMERO_TIMBRE_BANCO + "'></td>");
		out.println("<td class='campositem'>&nbsp;" +
			this.formateador.format(this.valorLiquidacion) + "</td>");
		out.println("<td class='campositem'>" +
			this.formateador.format(this.valorLiquidacion) + "</td>");
		out.println("</tr>");
		out.println("</table></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println(
			"<td align='right' valign='top'><table border='0' cellpadding='0' " +
			"cellspacing='2' class='camposform'>");
		out.println("<tr>");
		out.println("<td width='20'><img src='" + request.getContextPath() +
			"/jsp/images/ind_vinculo.gif' " + "width='20' height='15'></td>");
		out.println(
			"<input type='hidden' name='ACCION' value='ADICIONAR_APLICACION'>");
		out.println("<td>Agregar Pago con Timbre </td>");
		out.println("<td><input name='imageField2' type='image' " + "src='" +
			request.getContextPath() +
			"/jsp/images/btn_short_anadir.gif' width='35' " +
			"height='25' border='0'>");
		out.println("<input type='hidden' name='FORMA_PAGO' value='" +
			WebKeys.FORMA_PAGO_TIMBRE_BANCO + "'></td>");
		out.println("</tr>");
		out.println("</table></td>");
		out.println("</tr>");
		out.println("</form>");
		out.println("</table></td>");
		out.println("</tr>");
		out.println("</table>");
	}

	/**
	 * Indicar si el helper muestra la opción de cheques
	 *
	 * @param b
	 */
	public void setSeQuiereCheques(boolean b) {
		seQuiereCheques = b;
	}

	/**
	 * Indicar si el helper muestra la opción de consignación
	 *
	 * @param b
	 */
	public void setSeQuiereConsignacion(boolean b) {
		seQuiereConsignacion = b;
	}

	/**
	 * Indicar si el helper muestra la opción de efectivo
	 *
	 * @param b
	 */
	public void setSeQuiereEfectivo(boolean b) {
		seQuiereEfectivo = b;
	}

	/**
	 * Indicar si el helper muestra la opción de pago con timbre
	 * @param b The seQuiereTimbreConstanciaLiquidacion to set.
	 */
	public void setSeQuiereTimbreConstanciaLiquidacion(boolean b) {
		this.seQuiereTimbreConstanciaLiquidacion = b;
	}

	/**
	 * Indica si se quiere asignar la misma estación luego de generar turno
	 * @param b asignarEstacion to set
	 */
	public void setAsignarEstacion(boolean b) {
		this.asignarEstacion = b;
	}
	
	/**
	 * Obtiene la acción asociada a este formulario
	 * @return La acción asociada a este formulario
	 */
	public String getAccion() {
		return sAccion;
	}
	
	/**
	 * Establece la acción asociada a este formulario
	 * @param sAccion La acción asociada a este formualrio
	 */
	public void setAccion(String sAccion) {
		this.sAccion = sAccion;
	}
}
