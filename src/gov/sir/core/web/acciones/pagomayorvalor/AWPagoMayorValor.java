package gov.sir.core.web.acciones.pagomayorvalor;

import gov.sir.core.eventos.pagomayorvalor.EvnPagoMayorValor;
import gov.sir.core.eventos.pagomayorvalor.EvnRespPagoMayorValor;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.util.ArrayList;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con el
 * pago por mayor valor. Se encarga de realizar las validaciones primarias a
 * nivel web y por medio de eventos hace llamados a la capa de negocio para que
 * su vez llame los servicios que se requieren.
 *
 * @author ppabon
 */
public class AWPagoMayorValor extends SoporteAccionWeb {

    /**
     * Constante que identifica que se aprobar la custodia
     */
    public final static String CUSTODIA_EXITO = "CUSTODIA_EXITO";

    /**
     * Constante que identifica que se aprobar la custodia
     */
    public final static String CUSTODIA_FRACASO = "CUSTODIA_FRACASO";

    /**
     * Constante que identifica que se desea NOTIFICAR_CIUDADANO
     */
    public final static String NOTIFICAR_CIUDADANO_EXITO = "NOTIFICAR_CIUDADANO_EXITO";

    /**
     * Constante que identifica que se desea NOTIFICAR_CIUDADANO
     */
    public final static String NOTIFICAR_CIUDADANO_FRACASO = "NOTIFICAR_CIUDADANO_FRACASO";

    /**
     * Constante que identifica que se desea REGISTRAR_PAGO
     */
    public final static String REGISTRAR_PAGO_EXITO = "REGISTRAR_PAGO_EXITO";

    /**
     * Constante que identifica que se desea REGISTRAR_PAGO
     */
    public final static String REGISTRAR_PAGO_FRACASO = "REGISTRAR_PAGO_FRACASO";

    /**
     * Constante que identifica que se desea CARGAR_PAGO
     */
    public final static String CARGAR_PAGO = "CARGAR_PAGO";

    /**
     * Constante que identifica que se desea NOTIFICAR_FUNCIONARIO
     */
    public final static String NOTIFICAR_FUNCIONARIO_EXITO = "NOTIFICAR_FUNCIONARIO_EXITO";

    /**
     * Constante que identifica que se desea NOTIFICAR_FUNCIONARIO
     */
    public final static String NOTIFICAR_FUNCIONARIO_FRACASO = "NOTIFICAR_FUNCIONARIO_FRACASO";

    /**
     * Variable donde se guarda la accion enviada en el request
     */
    private String accion;

    /**
     * Constructor de la clase AWCorreccion
     */
    public AWPagoMayorValor() {
        super();
    }

    /**
     * Método principal de esta acción web. Aqui se realiza toda la lógica
     * requerida de validación y de generación de eventos de negocio.
     *
     * @param request trae la informacion del formulario
     * @throws AccionWebException cuando ocurre un error
     * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier
     * otro caso
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        HttpSession session = request.getSession();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

        if (accion.equals(CUSTODIA_EXITO)) {
            return aprobarCustodiaExito(request);
        } else if (accion.equals(CUSTODIA_FRACASO)) {
            return aprobarCustodiaFracaso(request);
        } else if (accion.equals(NOTIFICAR_CIUDADANO_EXITO)) {
            return notificarCiudadanoExito(request);
        } else if (accion.equals(NOTIFICAR_CIUDADANO_FRACASO)) {
            return notificarCiudadanoFracaso(request);
        } else if (accion.equals(REGISTRAR_PAGO_EXITO)) {
            return registrarPagoExito(request);
        } else if (accion.equals(REGISTRAR_PAGO_FRACASO)) {
            return registrarPagoFracaso(request);
        } else if (accion.equals(CARGAR_PAGO)) {
            return cargarPago(request);
        } else if (accion.equals(NOTIFICAR_FUNCIONARIO_EXITO)) {
            return notificarFuncionarioExito(request);
        } else if (accion.equals(NOTIFICAR_FUNCIONARIO_FRACASO)) {
            return notificarFuncionarioFracaso(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion + " no es valida.");
        }
    }

    /**
     * Método que permite avanzar el workflow por éxito en la fase de custadia,
     * en el proceso de pago por mayor valor.
     *
     * @param request
     * @return
     */
    private EvnPagoMayorValor aprobarCustodiaExito(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnPagoMayorValor evn = new EvnPagoMayorValor(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnPagoMayorValor.CUSTODIA, EvnPagoMayorValor.EXITO, CAvanzarTurno.AVANZAR_CUALQUIERA);

        return evn;
    }

    /**
     * Método que permite avanzar el workflow por fracaso en la fase de
     * custadia, en el proceso de pago por mayor valor.
     *
     * @param request
     * @return
     */
    private EvnPagoMayorValor aprobarCustodiaFracaso(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnPagoMayorValor evn = new EvnPagoMayorValor(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnPagoMayorValor.CUSTODIA, EvnPagoMayorValor.FRACASO, CAvanzarTurno.AVANZAR_ELIMINANDO_PUSH);

        return evn;
    }

    /**
     * Método que permite avanzar el workflow por éxito en la fase de notificar
     * al ciudadano, en el proceso de pago por mayor valor.
     *
     * @param request
     * @return
     */
    private EvnPagoMayorValor notificarCiudadanoExito(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnPagoMayorValor evn = new EvnPagoMayorValor(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnPagoMayorValor.NOTIFICAR_CIUDADANO, EvnPagoMayorValor.EXITO, CAvanzarTurno.AVANZAR_CUALQUIERA);

        return evn;
    }

    /**
     * Método que permite avanzar el workflow por fracaso en la fase de
     * notificar al ciudadano, en el proceso de pago por mayor valor.
     *
     * @param request
     * @return
     */
    private EvnPagoMayorValor notificarCiudadanoFracaso(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnPagoMayorValor evn = new EvnPagoMayorValor(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnPagoMayorValor.NOTIFICAR_CIUDADANO, EvnPagoMayorValor.FRACASO, CAvanzarTurno.AVANZAR_POP);

        return evn;
    }

    /**
     * Método que permite avanzar el workflow por exito en la fase de registrar
     * pago. Esto significaría que el ciudadano pagó la sumo adicinal que debía
     * cancelar.
     *
     * @param request
     * @return
     */
    private EvnPagoMayorValor registrarPagoExito(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnPagoMayorValor evn = new EvnPagoMayorValor(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnPagoMayorValor.REGISTRAR_PAGO, EvnPagoMayorValor.EXITO, CAvanzarTurno.AVANZAR_CUALQUIERA);

        return evn;
    }

    /**
     * Método que permite avanzar el workflow por fracaso en la fase de
     * registrar pago. Esto significaría que el ciudadano no pagó la sumo
     * adicinal que debería cancelar.
     *
     * @param request
     * @return
     */
    private EvnPagoMayorValor registrarPagoFracaso(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnPagoMayorValor evn = new EvnPagoMayorValor(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnPagoMayorValor.REGISTRAR_PAGO, EvnPagoMayorValor.FRACASO, CAvanzarTurno.AVANZAR_POP);

        return evn;
    }

    /**
     * Método que permite cargar la liquidación en la capa de presentación, de
     * la liquidación se saca el valor de lo que se debe cobrar por parte del
     * cajero. en el procesi de pago por mayor valor.
     *
     * @param request
     * @return
     */
    private EvnPagoMayorValor cargarPago(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Solicitud solicitud = (Solicitud) turno.getSolicitud();

        HermodService hs;
        List canalesXCirculo = new ArrayList() ;
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        try {
            hs = HermodService.getInstance();
           canalesXCirculo = hs.getCanalesRecaudoXCirculo(circulo);
        } catch (HermodException e) {
        }
        request.getSession().setAttribute(WebKeys.LISTA_CANALES_RECAUDO_CIRCULO, canalesXCirculo);
        

        int meses = 2;
        Calendar fechaComp = Calendar.getInstance();
        fechaComp.add(Calendar.MONTH, -meses);
        fechaComp.set(Calendar.HOUR, 0);
        fechaComp.set(Calendar.AM_PM, 0);
        fechaComp.set(Calendar.MINUTE, 0);
        fechaComp.set(Calendar.SECOND, 0);
        fechaComp.set(Calendar.MILLISECOND, 0);
        Date fechaComparada = fechaComp.getTime();

        /* JAlcazar caso Mantis 2548 Acta - Requerimiento No 125 - Turnos de mayores valores cancelados por vencimiento
                 * Se define la toma de la fase se Solicitud  if(th.getFase().equals(WebKeys.SOLICITUD))
         */
        TurnoHistoria th = null;
        List historial_turno = turno.getHistorials();
        Iterator hist = historial_turno.iterator();
        while (hist.hasNext()) {
            th = (TurnoHistoria) hist.next();
            if (th.getFase().equals(WebKeys.SOLICITUD)) {
                break;
            }
        }

        if (th.getFecha().getTime() < fechaComparada.getTime()) {
            ValidacionParametrosException e = new ValidacionParametrosException();
            e.addError("Ya se vencio el plazo de mayor valor de este turno");
            throw e;
        }

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

        request.getSession().setAttribute(WebKeys.LIQUIDACION, liquidacion);

        if (liquidacion != null) {
            request.getSession().setAttribute(WebKeys.VALOR_LIQUIDACION, String.valueOf(liquidacion.getValor()));
        } else {
            request.getSession().setAttribute(WebKeys.VALOR_LIQUIDACION, "");
        }

        return null;
    }

    /**
     * Método que permite avanzar el workflow por éxito en la fase de notificar
     * al funcionario, en el proceso de pago por mayor valor.
     *
     * @param request
     * @return
     */
    private EvnPagoMayorValor notificarFuncionarioExito(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnPagoMayorValor evn = new EvnPagoMayorValor(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnPagoMayorValor.NOTIFICAR_FUNCIONARIO, EvnPagoMayorValor.EXITO, CAvanzarTurno.AVANZAR_POP);

        return evn;
    }

    /**
     * Método que permite avanzar el workflow por fracaso en la fase de
     * notificar al funcionario, en el proceso de pago por mayor valor.
     *
     * @param request
     * @return
     */
    private EvnPagoMayorValor notificarFuncionarioFracaso(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        EvnPagoMayorValor evn = new EvnPagoMayorValor(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnPagoMayorValor.NOTIFICAR_FUNCIONARIO, EvnPagoMayorValor.FRACASO, CAvanzarTurno.AVANZAR_POP);

        return evn;
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        EvnRespPagoMayorValor respuesta = (EvnRespPagoMayorValor) evento;

        if (respuesta != null) {
            /*if (respuesta.getTipoEvento().equals(EvnRespCorreccion.TOMAR_FOLIO)) {
			        request.getSession().setAttribute(WebKeys.FOLIOS_BLOQUEADOS, respuesta.getLlaveBloqueo());
			}*/
        }
    }
}
