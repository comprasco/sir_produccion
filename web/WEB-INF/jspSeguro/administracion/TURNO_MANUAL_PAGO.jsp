<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.administracion.DatosPagoTurnoManualHelper"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.CirculoTipoPago"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoPago"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>

<%//
            //Obtener si el pago es de registro
            Boolean registro;

            registro = (Boolean) session
                    .getAttribute("PAGO_REGISTRO_LIQUIDACION");
            registro = registro == null ? new Boolean(false) : registro;
            //

            javax.servlet.ServletContext context = session.getServletContext();
            String numliquidacion = (String) session
                    .getAttribute(WebKeys.VALOR_LIQUIDACION);
            List consignaciones = (List) session
                    .getAttribute(WebKeys.LISTA_CONSIGNACIONES);
            List cheques = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);
            AplicacionPago appEfectivo = (AplicacionPago) session
                    .getAttribute(WebKeys.APLICACION_EFECTIVO);
            AplicacionPago appTimbre = (AplicacionPago) session
                    .getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
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

            if (session.getAttribute(gov.sir.core.web.acciones.consulta.AWConsulta.TIPO_CONSULTA) != null &&
                ((String)session.getAttribute(gov.sir.core.web.acciones.consulta.AWConsulta.TIPO_CONSULTA)).equals
                  (gov.sir.core.negocio.modelo.TipoConsulta.TIPO_SIMPLE)) {
                    datosPagoHelper.setAsignarEstacion(true);
            }

            try {
                Circulo circulo = (Circulo) session
                        .getAttribute(WebKeys.CIRCULO);
     
               List tiposPago = (List)application.getAttribute(WebKeys.TIPOS_PAGO_LISTA);

                  for (Iterator itr = tiposPago.iterator(); itr
                        .hasNext();) {
                    TipoPago ctipo = (TipoPago) itr.next();
                    if (ctipo.getIdTipoDocPago() == CTipoPago.PAGO_EFECTIVO) {
                        datosPagoHelper.setSeQuiereEfectivo(true);
                    } else if (ctipo.getIdTipoDocPago() == CTipoPago.PAGO_CONSIGNACION) {
                        datosPagoHelper.setSeQuiereConsignacion(true);
                    } else if (ctipo.getIdTipoDocPago() == CTipoPago.PAGO_CHEQUE) {
                        datosPagoHelper.setSeQuiereCheques(true);
                    } else if (ctipo.getIdTipoDocPago() == CTipoPago.PAGO_TIMBRE_CONSTANCIA_LIQUIDACION
                            && registro.booleanValue()) {
                        datosPagoHelper
                                .setSeQuiereTimbreConstanciaLiquidacion(true);
                    }
                  } 

                //datosPagoHelper.setSeQuiereCheques(false);
                //datosPagoHelper.setSeQuiereConsignacion(false);
                //datosPagoHelper.setSeQuiereEfectivo(false);
                datosPagoHelper.render(request, out);
            } catch (HelperException re) {
                out.println("ERROR " + re.getMessage());
            }
            

        %>
