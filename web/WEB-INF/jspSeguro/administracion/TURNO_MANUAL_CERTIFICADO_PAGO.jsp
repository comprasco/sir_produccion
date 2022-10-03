<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.administracion.DatosPagoTurnoManualHelper"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>

<%
	//Obtener si el pago es de registro
    Boolean registro;

	registro = (Boolean)session.getAttribute("PAGO_REGISTRO_LIQUIDACION");
	registro = registro == null ? new Boolean(false) : registro;

	javax.servlet.ServletContext context = session.getServletContext();
	String numliquidacion = (String)session.getAttribute(WebKeys.VALOR_LIQUIDACION);
	List consignaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);
	List cheques = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);
	AplicacionPago appEfectivo = (AplicacionPago) session.getAttribute(WebKeys.APLICACION_EFECTIVO);
	AplicacionPago appTimbre = (AplicacionPago) session.getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
	double valorLiquidacion = 0;
	List marcasCheques = (List)session.getAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
	List marcasConsignacion = (List)session.getAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);

	if (numliquidacion != null) {
		valorLiquidacion = Double.valueOf(numliquidacion).doubleValue();
	} else {
		valorLiquidacion = 0;
	}

	DatosPagoTurnoManualHelper datosPagoHelper = new DatosPagoTurnoManualHelper(request,
	valorLiquidacion, consignaciones, cheques, appEfectivo,
		appTimbre,marcasConsignacion,marcasCheques);

	try {
		datosPagoHelper.setAsignarEstacion(false);
		datosPagoHelper.setSeQuiereEfectivo(true);
		datosPagoHelper.setSeQuiereConsignacion(true);
		datosPagoHelper.setSeQuiereCheques(true);
		datosPagoHelper.setSeQuiereTimbreConstanciaLiquidacion(true);
		//datosPagoHelper.setAccion("turnoManualCertificado.do");
		datosPagoHelper.render(request, out);
	} catch (HelperException re) {
		out.println("ERROR " + re.getMessage());
	}
%>