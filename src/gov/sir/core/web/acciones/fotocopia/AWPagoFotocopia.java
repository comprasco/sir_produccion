package gov.sir.core.web.acciones.fotocopia;

import java.util.List;

import gov.sir.core.eventos.fotocopia.EvnFotocopia;
import gov.sir.core.eventos.fotocopia.EvnPagoFotocopia;
import gov.sir.core.eventos.fotocopia.EvnRespFotocopia;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


import javax.servlet.http.HttpServletRequest;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.DocumentoPago;
import javax.servlet.http.HttpSession;
import gov.sir.core.eventos.comun.EvnPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.util.ArrayList;
import org.auriga.core.modelo.transferObjects.Estacion;

/**
 * @author dlopez
 */
public class AWPagoFotocopia extends SoporteAccionWeb {

    /**
     * Constante que identifica que el valor del pago es cero.
     */
    public static final String PAGO_FOTOCOPIAS_ZERO_VALUE = EvnFotocopia.PAGO_FOTOCOPIAS_ZERO_VALUE;
    public static final String PARAM_PAGO_FOTOCOPIAS_ZERO_VALUE = "PARAM_" + EvnFotocopia.PAGO_FOTOCOPIAS_ZERO_VALUE;
    /**
     * Constante que identifica que se desea continuar con el proceso de
     * procesar el pago de un turno de fotocopias.
     */
    public final static String ACEPTAR = "ACEPTAR";
    /**
     * Constante que identifica que se desea cancelar el proceso de procesar el
     * pago de un turno de fotocopias.
     */
    public final static String CANCELAR = "CANCELAR";
    /**
     * Variable donde se guarda la accion enviada en el request
     */
    private String accion;

    /**
     * Constructor de la clase AWPagoFotocopia
     */
    public AWPagoFotocopia() {
        super();
    }

    /**
     * Método principal de esta acción web. Aqui se realiza toda la lógica
     * requerida de validación y de generación de eventos de negocio.
     *
     * @param request trae la informacion del formulario
     * @throws AccionWebException cuando ocurre un error
     * @return un
     * <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        if (accion.equals(ACEPTAR)) {
            // TODO: revisar el valor de la liquidacion; si es 0, se hace fwd a
            // la pantalla siguiente y se avanza el flujo;
            // si no, el procesamiento se hace por pago.


            // TODO: procesarla por ANPagoFotocopia
            // TODO: crear evento para mapeo, con los valores normales necesitados
            // para avanzar turno.

            return cargarPago(request);
        } else if (accion.equals(CANCELAR)) {
            return cancelar(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion + " no es válida.");
        }
    }

    /**
     * @param request
     * @return
     */
    private EvnPagoFotocopia cancelar(HttpServletRequest request) throws AccionWebException {

        //Se recibe la información que viene del formulario.
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnPagoFotocopia evn = new EvnPagoFotocopia(usuarioAuriga, usuarioSIR, turno, fase, EvnFotocopia.CANCELAR_PAGO, EvnFotocopia.NEGAR);

        return evn;

    }


    /*
     *
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {


        if (evento != null) {


            //Se obtuvo como respuesta un evento de fotocopias.
            if (evento instanceof EvnRespFotocopia) {

                EvnRespFotocopia auxEvento = (EvnRespFotocopia) evento;
                if (auxEvento.getTurno() != null) {
                    request.getSession().setAttribute(WebKeys.TURNO, auxEvento.getTurno());
                }

                // remover atributo de navegacion alternativa cuando el valor de liquidacion fotocopias es cero
                if (EvnRespFotocopia.PAGOFOTOCOPIASZEROVALUE_EVENTRESP_OK.equals(auxEvento.getTipoEvento())) {
                    request.getSession().removeAttribute(PAGO_FOTOCOPIAS_ZERO_VALUE);
                }
            }




        }
    }

    /**
     * @param request
     * @return
     */
    // EvnPagoFotocopia
    private EvnPago cargarPago(HttpServletRequest request) throws AccionWebException {

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Solicitud solicitud = (Solicitud) turno.getSolicitud();
        /** @author : HGOMEZ
        *** @change : Se reubica la variable session y proceso para que sean 
        *** accesibles para el código correspondiente a este caso mantis.
        *** Caso Mantis : 12288
        */
        HttpSession session = request.getSession();
        Proceso proceso;
        proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);        
        
        HermodService hs;
        List canalesXCirculo = new ArrayList() ;
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        try {
            hs = HermodService.getInstance();
           canalesXCirculo = hs.getCanalesRecaudoXCirculo(circulo);
        } catch (HermodException e) {
        }
        request.getSession().setAttribute(WebKeys.LISTA_CANALES_RECAUDO_CIRCULO, canalesXCirculo);
        
        // busca la ultima liquidacion de la lista de liquidaciones.
        List liquidaciones = solicitud.getLiquidaciones();
        int i = 0;
        Liquidacion liquidacion = null;

        if ((liquidaciones != null) && (liquidaciones.size() > 0)) {
            for (i = (liquidaciones.size() - 1); i >= 0; i--) {
                liquidacion = (Liquidacion) liquidaciones.get(i);

                if (liquidacion.getIdLiquidacion().equals("" + solicitud.getLastIdLiquidacion())) {
                    solicitud.setTurno(turno);
                    liquidacion.setSolicitud(solicitud);

                    break;
                }
            }
        }

        // coloca el valor de la liquidacion en sesion
        request.getSession().setAttribute(WebKeys.LIQUIDACION, liquidacion);

        if (null != liquidacion) {
            request.getSession().setAttribute(WebKeys.VALOR_LIQUIDACION, String.valueOf(liquidacion.getValor()));

            // carga por defecto la opcion de pago en efectivo
            DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
            AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());
            //HttpSession session = request.getSession();
            session.setAttribute(WebKeys.APLICACION_EFECTIVO, aplicacionEfectivo);
            // creo que hay que colocar este valor en una lista



        } else {
            request.getSession().setAttribute(WebKeys.VALOR_LIQUIDACION, "");
        }

        // luego comparar si:     |valor| < threshold ~ 0.
        //AGREGAR CONDICION DE EXENTO
        /** @author : HGOMEZ
        *** @change : Se agrega en la condición la verificación
        *** del proceso 5 Fotocopia.
        *** Caso Mantis : 12288
        */
        if (!(proceso.getIdProceso() == 5)  && (null != liquidacion) && liquidacion.getValor() == 0d) {

            //  EvnPagoFotocopiaZeroValue event;
            //  event = new EvnPagoFotocopiaZeroValue( usuarioAuriga, turno, fase, PAGO_FOTOCOPIAS_ZERO_VALUE , AW_FotocopiasConstants.WF_LINK_VERIFICAR2CONFIRMAR );
            //
            //  request.getSession().setAttribute( PARAM_PAGO_FOTOCOPIAS_ZERO_VALUE, new Boolean( true ) );
            //
            //  return event;

            org.auriga.core.modelo.transferObjects.Usuario usuario;
            Pago pago;
            // Fase fase;
            
            Estacion estacion;
            String tipoEvento;
            gov.sir.core.negocio.modelo.Usuario usuarioSIR;

            usuario = usuarioAuriga;
            pago = new Pago(liquidacion, null);
            estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
            tipoEvento = EvnPago.PROCESAR;
            usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
            fase = (Fase) session.getAttribute(WebKeys.FASE);

            EvnPago event;
            event = new EvnPago(usuario, pago, proceso, estacion, tipoEvento, usuarioSIR);
            event.setEsPagoFotocopias(true);
            event.setRespuestaWF(AW_FotocopiasConstants.WF_LINK_VERIFICAR2CONFIRMAR);
            event.setFase(fase);
            return event;
        }
        return null; // forward a forma de pago.
    }
}