package gov.sir.core.web.acciones.comun;

import co.com.iridium.generalSIR.comun.modelo.Transaccion;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.web.acciones.administracion.AWRelacion;

import gov.sir.core.eventos.comun.EvnRespSistema;
import gov.sir.core.eventos.comun.EvnRespTurno;
import gov.sir.core.eventos.comun.EvnTurno;
import gov.sir.core.eventos.correccion.EvnResp_CorrSimpleMain_VerAlertasOptions;
import gov.sir.core.eventos.correccion.Evn_CorrSimpleMain_VerAlertasOptions;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionConservacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Recurso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CDevoluciones;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRelacion;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.util.ContainerUtil;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.ListarException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.acciones.devolucion.AWDevolucion;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.web.acciones.registro.AWConfrontacion;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.auriga.smart.eventos.EventoException;

/**
 * Esta accion web se encarga de generar eventos que permitan gestionar los
 * turnos. REQUERIMIENTOS ASOCIADOS: 6 - Control de turnos anteriores
 *
 * @author mmunozs
 */
public class AWTurno extends SoporteAccionWeb {

    public static final String LISTA_CANALES_RECAUDO = "LISTA_CANALES_RECAUDO";

    /**
     * Constante que identifica que se desea obtener una lista de turnos
     */
    public static final String LISTAR = "LISTAR";

    /**
     * Constante que identifica que el usuario quiere iniciar un proceso
     * especifico
     */
    public final static String INICIAR_PROCESO = "INICIAR_PROCESO";

    /**
     * Constante que identifica que el usuario quiere continuar un turno
     * especifico
     */
    public final static String CONTINUAR_TURNO = "CONTINUAR_TURNO";

    public final static String CONTINUAR_INDIVIDUAL = "CONTINUAR_INDIVIDUAL";

    /**
     * Constante que identifica el campo del jsp donde se solicita el id de la
     * fase
     */
    public final static String ID_FASE = "ID_FASE";

    /**
     * Constante que identifica el campo del jsp donde se solicita el id del
     * proceso
     */
    public final static String ID_PROCESO = "ID_PROCESO";

    /**
     * Constante que identifica que se desea obtener los datos para iniciar una
     * fase
     */
    public static final String FASE_INICIAL = "FASE_INICIAL";

    /**
     * Constante que identifica el campo del jsp donde se solicita el id del
     * turno en el workflow
     */
    public final static String ID_TURNO = "ID_TURNO";

    public final static String TURNO_INDIVIDUAL = "TURNO_INDIVIDUAL";

    /**
     * Constante que identifica el campo del jsp donde se solicita el id del
     * turno en el workflow
     */
    public final static String POS_ABS_TURNO = "POS_ABS_TURNO";

    /**
     * Constante que identifica que se desea obtener los detalles del turno
     */
    public static final String CONSULTAR = "CONSULTAR";

    /**
     * Constante que identifica que se desea obtener los detalles del turno
     */
    public static final String CONSULTAR_CONFIRMACION = "CONSULTAR_CONFIRMACION";

    public static final String VERIFICAR_DEPENDENCIAS_TURNO = "VERIFICAR_DEPENDENCIAS_TURNO";

    /**
     * Accion que selecciono el usuario
     */
    private String accion;

    public static final String TURNO_CONFIRMADO = "TURNO_CONFIRMADO";

    public static final String LISTA_USUARIOS_TURNO = "LISTA_USUARIOS_TURNO";

    public static final String FASE_TURNO = "FASE_TURNO";

    public static final String CONSULTAR_RELACION = "CONSULTAR_RELACION";

    /**
     * Constante que identifica que el usuario quiere continuar un turno
     * especifico
     */
    public final static String PROCESAR_TURNOS_MULTIPLES = "PROCESAR_TURNOS_MULTIPLES";

    /**
     * Constante que identifica que se desea obtener una lista de turnos para
     * entrega de registro
     */
    public static final String LISTAR_TURNOS_ENTREGA_REG = "LISTAR_TURNOS_ENTREGA_REG";

    /**
     * Identifica la hora de inicio de la Transaccion de la acccion
     */
    public static final String TIEMPO_INICIO_TRANSACCION = "TIEMPO_INICIO_TRANSACCION";

    /**
     * Identifica el tiempo de la Transaccion de la acccion
     */
    public static final String TIEMPO_TRANSACCION = "TIEMPO_TRANSACCION";

    /**
     * Esta variable identifica el objeto con la lista de las acciones que se
     * han ejecuta en el proceso de radicacion de un turno
     */
    public static final String LISTA_TRANSACCION = "LISTA_TRANSACCION";

    /**
     * Mï¿½todo principal de esta acciï¿½n web. Aqui se realiza toda la lï¿½gica
     * requerida de validaciï¿½n y de generaciï¿½n de eventos de negocio.
     *
     * @param request trae la informacion del formulario
     * @throws AccionWebException cuando ocurre un error
     * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier
     * otro caso
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);
        /*AHERRENO
		14/05/2012
        REQ 076_151 TRANSACCION*/
        request.getSession().setAttribute(AWTurno.TIEMPO_INICIO_TRANSACCION, new Date());

        if ((accion == null) || (accion.length() == 0)) {
            throw new AccionWebException("Debe indicar una accion");
        }

        if (accion.equals(LISTAR)) {
            return listar(request);
        } else if (accion.equals(INICIAR_PROCESO)) {
            return iniciarProceso(request);
        } else if (accion.equals(CONTINUAR_TURNO)) {
            return continuar(request);
        } else if (accion.equals(CONTINUAR_INDIVIDUAL)) {
            return continuarIndividual(request);
        } else if (accion.equals(PROCESAR_TURNOS_MULTIPLES)) {
            return procesarTurnosMultiples(request);
        } else if (accion.equals(CONSULTAR_CONFIRMACION)) {
            return consultar(request);
        } else if (accion.equals(CONSULTAR_RELACION)) {
            return consultarRelacion(request);
        } else if (accion.equals(VERIFICAR_DEPENDENCIAS_TURNO)) {
            return verificarDependencias(request);
        } else {
            throw new AccionWebException(
                    "Debe indicar una accion valida. La accion " + accion
                    + " no es valida");
        }
    }

    private Evento verificarDependencias(HttpServletRequest request) {

        // Se pasa el turno como parï¿½metro a la acciï¿½n de negocio.
        String idTurno = request.getParameter(AWTurno.ID_TURNO);
        //EvnTurno evento = new EvnTurno(idTurno, EvnTurno.VERIFICAR_DEPENDENCIAS_TURNO);
        return null;
    }
    
    
    private void notaDevolutivaAlert(HttpServletRequest request, String fase, String limit, String turnoWF) {
      String[] turno = turnoWF.split("-");
      List notifications = null;
      ArrayList alerts = new ArrayList();

      try {
         HermodService hs = HermodService.getInstance();
         notifications = hs.getNotaDevNotificada(turnoWF);
         if (notifications != null && !notifications.isEmpty()) {
            Iterator itNot = notifications.iterator();
            
            while(itNot.hasNext()){
                NotificacionNota notify = (NotificacionNota) itNot.next();
                String msg = null;
               String fechaNot = null;
               String idCirculo = turno[1];
               Date date = notify.getFechaNotificacion();
               fechaNot = DateFormatUtil.format(date);
               String plazo = hs.getValorLookupCodes(CDevoluciones.PLAZO_NOTIFICACION_NOTA_DEV, (notify.getDestino().equals(CDevoluciones.OFICINA_ORIGEN)?CDevoluciones.PLAZO_NOTIFICADA_JUZGADO:limit));
               int diasH = hs.diasHabiles(idCirculo, fechaNot);
               int timeNotifies = Integer.parseInt(plazo);
               int daysLeft = timeNotifies - diasH;
               if(notify.getDestino().equals(CDevoluciones.USUARIO)){
                if (diasH <= daysLeft || diasH < timeNotifies) {
                   msg = CDevoluciones.getMessageDays(String.valueOf(daysLeft), fase, CDevoluciones.DAYS_LEFT);
                } else if (diasH >= timeNotifies) {
                   msg = CDevoluciones.getMessageDays(String.valueOf(timeNotifies), fase, CDevoluciones.EXPIRES);
                }
               } else{
                   msg = "Lleva " + diasH + " días que se envió la notificación a la oficina de origen.";
               }

               if (msg != null) {
                  ContainerUtil notAlert = new ContainerUtil();
                  notAlert.setIdNotificacion(notify.getIdNotificacion());
                  notAlert.setAlerta(msg);
                  alerts.add(notAlert);
               }
            }
            
            if (!alerts.isEmpty()) {
                     request.getSession().setAttribute(CDevoluciones.ALERTA_VENCIMIENTO, alerts);
                  }
         }
      } catch (HermodException he) {
         System.out.println("ERROR: " + he);
      }

   }

    /**
     * Este metodo es llamdo cuando se quiere continuar con un turno en
     * diferentes fases.
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EvnTurno continuar(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        List turnos = (List) request.getSession().getAttribute(WebKeys.LISTA_TURNOS);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
        String idTurno = request.getParameter(AWTurno.ID_TURNO);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        String posAbsTurno = request.getParameter(AWTurno.POS_ABS_TURNO);
        
        if (fase.getID().equals(CFase.NOT_NOTA_DEVOLUTIVA)) {
         try {
            HermodService hs = HermodService.getInstance();
            Turno turn = hs.getTurnobyWF(idTurno);
            
            if (turn != null) {
               String msg = null;
               String fechaNot = null;
               String idCirculo = turn.getIdCirculo();
               List turnoH = turn.getHistorials();
               if (turnoH != null && !turnoH.isEmpty()) {
                  TurnoHistoria tHist = (TurnoHistoria)turnoH.get(turnoH.size() - 1);
                  int lastId = Integer.parseInt(tHist.getIdTurnoHistoria());
                  Date date = null;
                  Iterator itHist = turnoH.iterator();

                  int diasH;
                  while(itHist.hasNext()) {
                     TurnoHistoria story = (TurnoHistoria)itHist.next();
                     diasH = Integer.parseInt(story.getIdTurnoHistoria());
                     if (diasH == lastId) {
                        date = story.getFecha();
                     }
                  }

                  fechaNot = DateFormatUtil.format(date);
                  String plazo = hs.getValorLookupCodes(CDevoluciones.PLAZO_NOTIFICACION_NOTA_DEV, CDevoluciones.PLAZO_NOTIFICAR);
                  diasH = hs.diasHabiles(idCirculo, fechaNot);
                  int timeNotifies = Integer.parseInt(plazo);
                  int daysLeft = timeNotifies - diasH;
                  if (diasH <= daysLeft || diasH < timeNotifies) {
                     msg = CDevoluciones.getMessageDays(String.valueOf(daysLeft), CFase.NOT_NOTA_DEVOLUTIVA, CDevoluciones.DAYS_LEFT);
                  } else if (diasH >= timeNotifies) {
                     msg = CDevoluciones.getMessageDays(String.valueOf(timeNotifies), CFase.NOT_NOTA_DEVOLUTIVA, CDevoluciones.EXPIRES);
                  }

                  if (msg != null) {
                     request.getSession().setAttribute(CDevoluciones.ALERTA_VENCIMIENTO, msg);
                  }
               }
            }
         } catch (HermodException he) {
            System.out.println("ERROR: " + he);
         }
      } else if (fase.getID().equals(CFase.NOT_NOTA_NOTIFICADA)) {
         notaDevolutivaAlert(request, CFase.NOT_NOTA_NOTIFICADA, CDevoluciones.PLAZO_NOTIFICADA, idTurno);
      } else if (fase.getID().equals(CFase.NOT_RECURSOS_NOTA)) {
            String[] turno = idTurno.split("-");
            List alerts = new ArrayList();

            try {
               HermodService hs = HermodService.getInstance();
               Turno turn = hs.getTurnobyWF(idTurno);
               List listaTodosRecursos = new ArrayList();
               List listaHistorials = turn.getHistorials();
                if (listaHistorials != null) {
                    for (int k = 0; k < listaHistorials.size(); k++) {
                        TurnoHistoria turnoH = (TurnoHistoria) listaHistorials.get(k);

                        if (turnoH != null) {
                            List listaRecTurno = turnoH.getRecursos();

                            if (listaRecTurno != null) {
                                for (int t = 0; t < listaRecTurno.size(); t++) {
                                    Recurso rec = (Recurso) listaRecTurno.get(t);
                                    if (rec != null) {
                                        listaTodosRecursos.add(rec);
                                    }
                                }
                            }
                        }
                    }
                }
               if (!listaTodosRecursos.isEmpty()) {
                  Iterator itRec = listaTodosRecursos.iterator();
                  
                  while(itRec.hasNext()){
                      Recurso resource = (Recurso) itRec.next();
                      if(resource != null){
                        String msg = null;
                        String fechaNot = null;
                        String idCirculo = turno[1];
                        Date date = resource.getFecha();
                        boolean isApel = (resource.getTitulo().equals("RECURSO DE REPOSICION DE SUBSIDIO DE APELACION") || resource.getTitulo().equals(CDevoluciones.REC_APEL));
                        fechaNot = DateFormatUtil.format(date);
                        String plazo = hs.getValorLookupCodes(CDevoluciones.PLAZO_NOTIFICACION_NOTA_DEV, CDevoluciones.PLAZO_RECURSO);
                        
                        Calendar calendar = Calendar.getInstance();
                        Date dateObj = calendar.getTime();
                        String formattedDate = DateFormatUtil.format(dateObj);
                        Date fechaInicial = null;
                        Date fechaFinal = null;
                        try{
                            fechaInicial = DateFormatUtil.parse(fechaNot);
                            fechaFinal= DateFormatUtil.parse(formattedDate);
                        } catch(ParseException e){
                            System.out.println("ERROR: " + e.getMessage());
                        }
                        int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
                        
                        int timeNotifies = Integer.parseInt(plazo);
                        int daysLeft = timeNotifies - dias;
                        if (dias <= daysLeft || dias < timeNotifies) {
                           msg = CDevoluciones.getMessageDays(String.valueOf(daysLeft), CFase.NOT_RECURSOS_NOTA, CDevoluciones.DAYS_LEFT);
                        } else if (dias >= timeNotifies) {
                           msg = CDevoluciones.getMessageDays(String.valueOf(timeNotifies), CFase.NOT_RECURSOS_NOTA, CDevoluciones.EXPIRES);
                        }

                        if (msg != null) {
                           ContainerUtil notAlert = new ContainerUtil();
                           notAlert.setIdRecurso(resource.getIdRecurso());
                           notAlert.setAlerta(msg);
                           alerts.add(notAlert);
                        }
                      }
                  }
                  
                  if(!alerts.isEmpty()){
                      request.getSession().setAttribute(CDevoluciones.ALERTA_VENCIMIENTO, alerts);
                  }
               }
            } catch (HermodException he) {
               System.out.println("ERROR: " + he);
            }
      }
        
        if ((idTurno == null || idTurno.trim().equals("")) && !rol.getRolId().equals(CRol.SIR_ROL_USUARIO_ADMINISTRATIVO)) {
            throw new ListarException("Debe seleccionar un turno para continuar");
        }

        if ((usuario == null) || usuario.getUsuarioId().equals("")) {
            throw new ListarException("El usuario no es valido");
        }

        if ((estacion == null) || estacion.getEstacionId().equals("")) {
            throw new ListarException("La estacion no es valida");
        }

        if ((proceso == null) || proceso.getIdProceso() == 0) {
            throw new ListarException("El proceso no es valido");
        }

        if ((rol == null) || rol.getRolId().trim().equals("")) {
            throw new ListarException("El rol no es valido");
        }

        if ((fase == null) || (fase.getID().trim().equals(""))) {
            throw new ListarException("La fase no puede ser vacia");
        }

        Turno turno = null;
        Turno temp = null;
        if (turnos != null) {
            if (request.getSession().getAttribute(WebKeys.TURNO_DEVOLUCION) == null) {
                Iterator itTurnos = turnos.iterator();

                while (itTurnos.hasNext()) {
                    temp = (Turno) itTurnos.next();
                    if (temp.getIdWorkflow().equals(idTurno)) {
                        turno = temp;
                    }
                }
            } else {
                Turno turnoDev = (Turno) request.getSession().getAttribute(WebKeys.TURNO_DEVOLUCION);

                request.getSession().removeAttribute(WebKeys.TURNO_DEVOLUCION);

                Iterator it = turnos.iterator();

                while (it.hasNext()) {
                    temp = (Turno) it.next();
                    if (temp.getIdWorkflow().equals(turnoDev.getIdWorkflow())) {
                        turno = temp;
                    }
                }
            }

        } else {
            throw new ListarException("No se encontro la lista de turnos");
        }
        
        return new EvnTurno(usuario, EvnTurno.CONSULTAR, turno, usuarioNeg);
    }

    /**
     * Este metodo es llamdo cuando se quiere continuar con un turno en
     * diferentes fases.
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EvnTurno continuarIndividual(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
        String idworkflow = request.getParameter(AWTurno.TURNO_INDIVIDUAL);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        /**
         * @author : Carlos Torres
         * @Caso Mantis : 11604: Acta - Requerimiento No
         * 004_589_Funcionario_Fase_ Entregado
         */
        //Si es entrega de documentos primero se valida si consultaron turno
        if (fase != null && fase.getID() != null && fase.getID().equals(CFase.REG_FINALIZADO)) {
            if (proceso.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                String anio = request.getParameter("ANIO");
                String idTurno = request.getParameter("ID_TURNO");
                if (anio != null && !anio.equals("") && idTurno != null && !idTurno.equals("")) {
                    int valida = 1;
                    try {
                        valida = Integer.valueOf(anio).intValue();
                        if (valida < 1) {
                            throw new ListarException("El aï¿½o no es valido");
                        }
                    } catch (Exception e) {
                        throw new ListarException("Formato de aï¿½o invï¿½lido");
                    }
                    try {
                        valida = Integer.valueOf(idTurno).intValue();
                        if (valida < 1) {
                            throw new ListarException("El id del turno no es valido");
                        }
                    } catch (Exception e) {
                        throw new ListarException("Formato de idturno invï¿½lido");
                    }
                    idworkflow = anio + "-" + circulo.getIdCirculo() + "-" + proceso.getIdProceso() + "-" + idTurno;
                }
            }
        }

        if (idworkflow == null || idworkflow.trim().equals("")) {
            throw new ListarException("Debe ingresar un turno para continuar");
        }

        if ((usuario == null) || usuario.getUsuarioId().equals("")) {
            throw new ListarException("El usuario no es valido");
        }

        if ((estacion == null) || estacion.getEstacionId().equals("")) {
            throw new ListarException("La estacion no es valida");
        }

        if ((proceso == null) || proceso.getIdProceso() == 0) {
            throw new ListarException("El proceso no es valido");
        }

        if ((rol == null) || rol.getRolId().trim().equals("")) {
            throw new ListarException("El rol no es valido");
        }

        if ((fase == null) || (fase.getID().trim().equals(""))) {
            throw new ListarException("La fase no puede ser vacia");
        }

        Turno turno = new Turno();
        turno.setIdWorkflow(idworkflow);
        boolean wfValido = false;

        if (idworkflow.indexOf("-") != -1) {
            String[] partes = idworkflow.split("-");
            if (partes.length == 4) {
                wfValido = true;
                TurnoPk idT = new TurnoPk(idworkflow);
                turno.setAnio(partes[0]);
                turno.setIdCirculo(partes[1]);
                turno.setIdProceso(Long.parseLong(partes[2]));
                turno.setIdTurno(partes[3]);
            }
        }

        if (!wfValido) {
            throw new ListarException("El formato del Turno es invalido");
        }
        EvnTurno evn = new EvnTurno(usuario, EvnTurno.CONSULTAR, turno, usuarioNeg);
        evn.setFase(fase);
        evn.setEstacion(estacion);
        evn.setValidarIndividual(true);
        return evn;
    }

    /**
     * Este metodo es llamdo cuando se quiere continuar con un turno en
     * diferentes fases.
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EvnTurno procesarTurnosMultiples(HttpServletRequest request) throws AccionWebException {

        EvnTurno evnTurno = null;
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        List turnos = (List) request.getSession().getAttribute(WebKeys.LISTA_TURNOS);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);

        String idTurnos[] = request.getParameterValues(AWTurno.ID_TURNO);
        if (idTurnos == null || idTurnos.length < 1) {
            throw new ListarException("Debe seleccionar al menos un turno para continuar");
        }

        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        if ((usuario == null) || usuario.getUsuarioId().equals("")) {
            throw new ListarException("El usuario no es valido");
        }

        if ((estacion == null) || estacion.getEstacionId().equals("")) {
            throw new ListarException("La estacion no es valida");
        }

        if ((proceso == null) || proceso.getIdProceso() == 0) {
            throw new ListarException("El proceso no es valido");
        }

        if ((rol == null) || rol.getRolId().trim().equals("")) {
            throw new ListarException("El rol no es valido");
        }

        if ((fase == null) || (fase.getID().trim().equals(""))) {
            throw new ListarException("La fase no puede ser vacia");
        }

        if (fase.getID().equals(CFase.REP_ENTREGA)) {
            evnTurno = this.entregaMasivaRepNot(turnos, idTurnos, usuario, usuarioNeg);
        }

        return evnTurno;
    }

    /**
     * Este metodo es el encargado de validar los parametros necesarios para
     * consultar los turnos de una fase
     *
     * Nombre: Tipo: ID_FASE String
     *
     * @param request trae la informacion del formulario
     * @return Un evento de seguridad cuando los parametros son validos.
     * @throws AccionWebException cuando el identificador de la fase es invalido
     */
    private EvnTurno listar(HttpServletRequest request)
            throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        List fases = (List) request.getSession().getAttribute(WebKeys.LISTA_FASES);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        String idFase = request.getParameter(AWTurno.ID_FASE);

        if ((usuario == null) || usuario.getUsuarioId().equals("")) {
            throw new ListarException("El usuario no es valido");
        }

        if ((estacion == null) || estacion.getEstacionId().equals("")) {
            throw new ListarException("La estacion no es valida");
        }

        if ((proceso == null) || proceso.getIdProceso() == 0) {
            throw new ListarException("El proceso no es valido");
        }

        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
        if ((rol == null) || rol.getRolId().trim().equals("")) {
            throw new ListarException("El rol no es valido");
        }

        if ((idFase == null) || (idFase.trim().equals(""))) {
            throw new ListarException("La fase no puede ser vacia");
        }

        Iterator itFases = fases.iterator();
        Fase fase = null;
        Fase temp = null;

        while (itFases.hasNext()) {
            temp = (Fase) itFases.next();

            if (temp.getID().equals(idFase)) {
                fase = temp;
            }
        }
        String accionNeg = AWTurno.LISTAR;
        /*
        * @autor         : JATENCIA 
        * @mantis        : 0011604 
        * @Requerimiento : 004_589_Funcionario_Fase_ Entregado  
        * @descripciï¿½n   : Se agrega la validaciï¿½n  que si el turno se encuentra 
        * en face de COS_ENTREGAR_ASOCIADO valide para generar la lista que muestra 
        * turnos con sus relaciones.
         */        

        if (fase.getID().equals(CFase.REG_FINALIZADO) || fase.getID().equals(CFase.REG_ENTREGA) || fase.getID().equals(CFase.COS_ENTREGAR_ASOCIADOS) || fase.getID().equals(CFase.REG_ENTREGA_EXTERNO)) {
            accionNeg = AWTurno.LISTAR_TURNOS_ENTREGA_REG;
        }
        /* Fin del bloque */
        request.getSession().setAttribute(WebKeys.FASE, fase);
        return new EvnTurno(usuario, accionNeg, fase, proceso, estacion, rol, circulo);
    }

    /**
     * Este mï¿½todo es llamado cuando se quiere iniciar uno de los procesos.
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EvnTurno iniciarProceso(HttpServletRequest request) {
        request.getSession().removeAttribute(WebKeys.LISTA_CERT_NO_MAT);
        String idProceso = request.getParameter(AWTurno.ID_PROCESO);
        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        List procesos = (List) request.getSession().getAttribute(WebKeys.LISTA_PROCESOS_INICIABLES);
        Iterator itProcesos = procesos.iterator();
        Proceso proceso = new Proceso(0, "", "");
        while (itProcesos.hasNext()) {
            Proceso tmp = (Proceso) itProcesos.next();
            long valIdProceso = Long.parseLong(idProceso);
            if (tmp.getIdProceso() == valIdProceso) {
                proceso = tmp;
            }
        }

        request.getSession().setAttribute(WebKeys.PROCESO, proceso);
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        return new EvnTurno(usuario, AWTurno.FASE_INICIAL, null, proceso, estacion, rol, circulo);
    }

    /**
     * Este mï¿½todo es llamado cuando se quiere obtener la informaciï¿½n del turno
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EvnTurno consultar(HttpServletRequest request) {

        String idWorkflow = request.getParameter(WebKeys.TURNO);
        return new EvnTurno(idWorkflow, AWTurno.CONSULTAR);
    }

    /**
     * Este mï¿½todo es llamado cuando se quiere consultar los turnos de una
     * relaciï¿½n.
     *
     * @param request HttpServletRequest
     * @return EvnTurno
     */
    private EvnTurno consultarRelacion(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        Log.getInstance().debug(AWTurno.class, "ENTRO CONSULTA RELACION aw");

        //SE RECUPERA LA INFORMACIï¿½N DEL FORMULARIO
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        String idRelacion = request.getParameter(AWRelacion.ID_RELACION);

        //SE REALIZAN LAS VALIDACIONES NECESARIAS SOBRE LOS DATOS RECIBIDOS
        if (proceso == null) {
            throw new ListarException("El proceso recibido es invï¿½lido.");
        }
        if (fase == null) {
            throw new ListarException("La fase recibida es invï¿½lida.");
        }
        if (idRelacion == null || idRelacion.equals("")) {
            throw new ListarException("Se debe ingresar un nï¿½mero vï¿½lido de relaciï¿½n.");
        }

        //SE DEJA EN LA SESIï¿½N LA INFORMACIï¿½N PARA EVITAR QUE SE BORRE SI SE GENERA EXCEPCIï¿½N.
        if ((idRelacion != null) && (idRelacion.trim().length() != 0)) {
            session.setAttribute(AWRelacion.ID_RELACION, idRelacion);
        }

        EvnTurno evnTurno = new EvnTurno(EvnTurno.CONSULTAR_RELACION);

        evnTurno.setProceso(proceso);
        evnTurno.setFase(fase);
        evnTurno.setIdRelacion(idRelacion);
        evnTurno.setCirculo(circulo);

        return evnTurno;
    }

    /**
     * Este mï¿½todo permite procesar cualquier evento de respuesta de la capa de
     * negocio, en caso de recibir alguno.
     *
     * @param request la informaciï¿½n del formulario
     * @param eventoRespuesta el evento de respuesta de la capa de negocio, en
     * caso de existir alguno
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

        HttpSession session = request.getSession();

        if (evento instanceof EvnRespTurno) {
            EvnRespTurno evResp = (EvnRespTurno) evento;
            
            session.setAttribute(LISTA_CANALES_RECAUDO, evResp.getCanalRecaudoYcuentas());
            session.setAttribute(AWCalificacion.NOTAS_INFORMATIVAS_INICIALES, evResp.getNotasInformativasCal());
            session.setAttribute(AWConfrontacion.NOTAS_INFORMATIVAS_CNFR, evResp.getNotasICon());
            session.setAttribute(AWConfrontacion.NOTAS_INFORMATIVAS_CNFR_D, evResp.getNotasIConD());
            session.setAttribute(AWConfrontacion.NOTAS_INFORMATIVAS_CNFR_CORR, evResp.getNotasIConCo());
            session.setAttribute(AWConfrontacion.NOTAS_INFORMATIVAS_CNFR_CORR_D, evResp.getNotasIConCoD());
            System.out.print("en la webTurno"+evResp.getCanalRecaudoYcuentas());
            
            
            
            Hashtable tabla = evResp.getParametros();
            if (tabla != null) {
                List tiposNota = (List) tabla.get(WebKeys.LISTA_TIPOS_NOTAS);
                List tiposNotaDevolutivas = (List) tabla.get(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS);

                if (tiposNota != null) {
                    request.getSession().setAttribute(WebKeys.LISTA_TIPOS_NOTAS, tiposNota);
                }
                if (tiposNotaDevolutivas != null) {
                    request.getSession().setAttribute(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS, tiposNotaDevolutivas);
                }
                /**
                 * @author : Diana Lora
                 * @change : Mantis 0010397: Acta - Requerimiento No 063_151 -
                 * Ajustes Reparto Notarial
                 */
                String repartoMinutas = (String) tabla.get(WebKeys.REPARTO_MINUTAS_EN_HORARIO);
                if (repartoMinutas != null) {
                    request.getSession().setAttribute(WebKeys.REPARTO_MINUTAS_EN_HORARIO, repartoMinutas);
                }
                /**
                 * @author : Edgar Lora
                 * @change : Mantis 0010397: Acta - Requerimiento No 063_151 -
                 * Ajustes Reparto Notarial
                 */
                request.getSession().removeAttribute(WebKeys.REPARTO_NOTARIAL_EN_HORARIO);
                if (!evResp.isEnHorario()) {
                    request.getSession().setAttribute(WebKeys.REPARTO_NOTARIAL_EN_HORARIO, "No se puede hacer reparto notarial en este horario");
                }

            }

        }

        if (request.getParameter(WebKeys.ACCION).equals(AWTurno.INICIAR_PROCESO) && evento instanceof EvnRespSistema) {
            EvnRespSistema respuesta = (EvnRespSistema) evento;
            if (respuesta != null) {
                Hashtable info = (Hashtable) respuesta.getPayload();
                List tiposId = (List) info.get(EvnRespSistema.LISTA_TIPOS_ID);
                if (tiposId == null) {
                    tiposId = new Vector();
                }
                Iterator itTiposId = tiposId.iterator();
                List elementoTiposId = new Vector();
                while (itTiposId.hasNext()) {
                    OPLookupCodes tipoId = (OPLookupCodes) itTiposId.next();
                    elementoTiposId.add(new ElementoLista(tipoId.getCodigo(), tipoId.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementoTiposId, 0, elementoTiposId.size() - 1);

                request.getSession().getServletContext().setAttribute(WebKeys.LISTA_TIPOS_ID, elementoTiposId);

                List tiposCertificado = (List) info.get(EvnRespSistema.LISTA_TIPOS_CERTIFICADOS);
                if (tiposCertificado == null) {
                    tiposCertificado = new Vector();
                }
                Iterator itTiposCertificado = tiposCertificado.iterator();
                List elementosTiposCertificado = new Vector();
                while (itTiposCertificado.hasNext()) {
                    TipoCertificado tipoCertificado = (TipoCertificado) itTiposCertificado.next();
                    elementosTiposCertificado.add(new ElementoLista(tipoCertificado.getIdTipoCertificado(), tipoCertificado.getNombre()));
                }
                request.getSession().getServletContext().setAttribute(WebKeys.LISTA_TIPOS_CERTIFICADOS, elementosTiposCertificado);

                List bancos = (List) info.get(EvnRespSistema.LISTA_BANCOS);
                if (bancos == null) {
                    new Vector();
                }
                request.getSession().getServletContext().setAttribute(WebKeys.LISTA_BANCOS, bancos);

            }
            
        } else {
            EvnRespTurno respuesta = (EvnRespTurno) evento;
            if (respuesta != null) {
                if (respuesta.getTipoEvento().equals(EvnRespTurno.CONSULTAR)) {
                    if (respuesta.getFolio() != null) {
                        session.setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                    }
                    if (respuesta.getFolioDefinitivo() != null) {
                        session.setAttribute(CFolio.FOLIO_DEF, respuesta.getFolioDefinitivo());
                    }
                    if (respuesta.isTurnoDependiente()) {
                        /**
                         * @author Fernando Padilla Velez
                         * @change Caso mantis 8555: Acta - Requerimiento
                         * No.028_Error al avanzar el turno, Se valida si el
                         * turno esta anulado antes de setearlo en sesion.
                         */
                        if (respuesta.getTurnoHijo() != null && !respuesta.getTurnoHijo().getAnulado().equals("S")) {
                            session.setAttribute(WebKeys.DEPENDENCIA_TURNO, new Boolean(respuesta.isTurnoDependiente()));
                            session.setAttribute(WebKeys.TURNO_HIJO, respuesta.getTurnoHijo());
                        }
                    }
                    session.setAttribute(WebKeys.TURNO_PADRE, respuesta.getTurnoPadre());
                    session.setAttribute(WebKeys.TURNO, respuesta.getTurno());
                    //PQ Se coloca el testamento en la session
                    session.setAttribute(WebKeys.TESTAMENTO_SESION, respuesta.getTestamento());

                    //PQ SIR-57
                    session.setAttribute(WebKeys.TURNOS_POSTERIORES_CONF_TESTAMENTO, respuesta.getTurnosPosterioresConfTestamento());

                    //3570: Generar la alerta para las matrï¿½culas no modificadas
                    if (respuesta.getTurno() != null && respuesta.getTurno().getIdFase().equals(CFase.COR_REVISAR_APROBAR)) {
                        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

                        gov.sir.core.negocio.modelo.Usuario usuarioSir;
                        usuarioSir = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                        try {
                            ProcesadorEventosNegocioProxy proxy = new ProcesadorEventosNegocioProxy();

                            Evn_CorrSimpleMain_VerAlertasOptions eventoAlerta;
                            eventoAlerta = new Evn_CorrSimpleMain_VerAlertasOptions(Evn_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENT);

                            eventoAlerta.setUsuarioAuriga(usuarioAuriga);
                            eventoAlerta.setUsuarioSir(usuarioSir);
                            eventoAlerta.setTurno(respuesta.getTurno());

                            EvnResp_CorrSimpleMain_VerAlertasOptions eventoRespuesta
                                    = (EvnResp_CorrSimpleMain_VerAlertasOptions) proxy.manejarEvento(
                                            eventoAlerta);

                            List lstFoliosSinCambios = eventoRespuesta.getFoliosInTurnoSinCambiosList();
                            if (lstFoliosSinCambios != null && lstFoliosSinCambios.size() > 0) {
                                request.getSession().setAttribute("GENERAR_ALERTA_SIN_CAMBIOS", "TRUE");
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                    List oficios = respuesta.getOficiosDevoluciones();

                    if (oficios != null) {
                        request.getSession().setAttribute(AWDevolucion.LISTA_RESOLUCIONES, oficios);
                    }

                    Turno nTurno = respuesta.getTurno();

                    //Obtener el valor de las solicitudes devueltas
                    double valorRegistroDevuelto = 0;
                    double valorImpuestoDevuelto = 0;
                    double valorCertificadoDevuelto = 0;
                    double valorRegistroDevueltoMayorValor = 0;
                    double valorImpuestoDevueltoMayorValor = 0;
                    double valorCertificadoDevueltoMayorValor = 0;
                    double valorConsignacionDevuelto = 0;
                    double ValorTotalDevuelto = 0;
                    double valorLiquidacionTurnoRegistro = 0;
                    double saldoFavor = 0;

                    double valorLiquidaConserva = respuesta.getValorLiquidaConserva();
                    double valorSinconserva = respuesta.getValorSinConservacion();
                    double valorConservaMayorValor = respuesta.getValorConservaMayorValor();
                    double valorSinConservaMayorValor = respuesta.getValorSinConservaMayorValor();
                    
                    if (nTurno.getIdProceso() == Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)
                            && (nTurno.getIdFase().equals(CFase.DEV_RECURSOS) || nTurno.getIdFase().equals(CFase.DEV_PAGO_DEVOLUCION))) {

                        LiquidacionTurnoDevolucion liquidacion = (LiquidacionTurnoDevolucion) nTurno.getSolicitud().getLiquidaciones().get(nTurno.getSolicitud().getLiquidaciones().size() - 1);
                        valorRegistroDevuelto = Math.abs(liquidacion.getValorDerechos());
                        valorImpuestoDevuelto = Math.abs(liquidacion.getValorImpuestos());
                        saldoFavor = Math.abs(liquidacion.getValorSaldoFavor());
                        valorRegistroDevueltoMayorValor = Math.abs(liquidacion.getValorDerechosMayorValor());
                        valorImpuestoDevueltoMayorValor = Math.abs(liquidacion.getValorImpuestosMayorValor());
                        valorCertificadoDevueltoMayorValor = Math.abs(liquidacion.getValorMayorValor()) - valorRegistroDevueltoMayorValor - valorImpuestoDevueltoMayorValor;
                        valorCertificadoDevuelto = Math.abs(liquidacion.getValor()) - valorRegistroDevuelto - valorImpuestoDevuelto - saldoFavor - Math.abs(liquidacion.getValorMayorValor());

                        if (nTurno.getSolicitud().getTurnoAnterior() != null && nTurno.getSolicitud().getTurnoAnterior().getSolicitud() != null) {
                            Solicitud solAux = nTurno.getSolicitud().getTurnoAnterior().getSolicitud();
                            if (!(solAux instanceof SolicitudRegistro)) {
                                if (((Liquidacion) solAux.getLiquidaciones().get(0)).getValor() > 0) {
                                    ValorTotalDevuelto = ((Liquidacion) solAux.getLiquidaciones().get(0)).getValor();
                                }
                            }
                        } else if (((SolicitudDevolucion) nTurno.getSolicitud()).getConsignaciones() != null
                                && ((SolicitudDevolucion) nTurno.getSolicitud()).getConsignaciones().size() > 0) {
                            valorConsignacionDevuelto = Math.abs(((Liquidacion) nTurno.getSolicitud().getLiquidaciones().get(nTurno.getSolicitud().getLiquidaciones().size() - 1)).getValor());
                        }
                    } else {
                        boolean poseeCertificadosAsociados = false;
                        Solicitud local_SolicitudAnterior = null;
                        Turno local_TurnoAnterior = null;
                        SolicitudDevolucion solicitudDevolucion = null;
                        if (nTurno.getSolicitud() instanceof SolicitudDevolucion) {
                            solicitudDevolucion = (SolicitudDevolucion) nTurno.getSolicitud();
                            if (solicitudDevolucion.getConsignaciones().size() == 0) {
                                local_TurnoAnterior = nTurno.getSolicitud().getTurnoAnterior();
                                local_SolicitudAnterior = (null == local_TurnoAnterior) ? (null) : (local_TurnoAnterior.getSolicitud());
                            }
                        } else {
                            local_TurnoAnterior = nTurno.getSolicitud().getTurnoAnterior();
                            local_SolicitudAnterior = (null == local_TurnoAnterior) ? (null) : (local_TurnoAnterior.getSolicitud());
                        }

                        while (local_TurnoAnterior != null) {
                            //Si el turno de registro posee turnos de certificados asociados
                            if (local_TurnoAnterior.getSolicitud() != null
                                    && local_TurnoAnterior.getSolicitud().getSolicitudesHijas() != null
                                    && local_TurnoAnterior.getSolicitud().getSolicitudesHijas().size() > 0) {
                                poseeCertificadosAsociados = true;
                            }

                            if (local_SolicitudAnterior instanceof SolicitudRegistro) {

                                //Se recorren las liqudiaciones para obtener el valor de registro y el de impuesto
                                List liquidaciones = local_SolicitudAnterior.getLiquidaciones();
                                for (int i = 0; i < liquidaciones.size(); i++) {
                                    if (i == 0) {
                                        LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) liquidaciones.get(i);
                                        valorLiquidacionTurnoRegistro += liqReg.getValor() > 0 ? liqReg.getValor() : 0;
                                        valorRegistroDevuelto += liqReg.getValorDerechos() > 0 ? liqReg.getValorDerechos() : 0;

                                        valorImpuestoDevuelto += liqReg.getValorImpuestos() > 0 ? liqReg.getValorImpuestos() : 0;

                                        valorImpuestoDevuelto += liqReg.getValorOtroImp() > 0 ? liqReg.getValorOtroImp() : 0;

                                        valorImpuestoDevuelto += liqReg.getValorMora() > 0 ? liqReg.getValorMora() : 0;

                                        if (liqReg.getPago() != null && liqReg.getPago().getAplicacionPagos() != null) {
                                            List aPagos = liqReg.getPago().getAplicacionPagos();
                                            for (int j = 0; j < aPagos.size(); j++) {
                                                if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoCheque) {
                                                    DocPagoCheque chPago = (DocPagoCheque) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                                    if (chPago.getSaldoDocumento() > 0) {
                                                        saldoFavor += chPago.getSaldoDocumento();
                                                    }
                                                } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                                    DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                                    if (chPago.getSaldoDocumento() > 0) {
                                                        saldoFavor += chPago.getSaldoDocumento();
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        double valorRegistroDevueltoAux = 0;
                                        double valorImpuestoDevueltoAux = 0;
                                        double valorCertificadoDevueltoAux = 0;
                                        double valorLiquidacionTurnoRegistroAux = 0;

                                        LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro) liquidaciones.get(i);
                                        valorLiquidacionTurnoRegistroAux += liqReg.getValor() > 0 ? liqReg.getValor() : 0;
                                        valorLiquidacionTurnoRegistro += valorLiquidacionTurnoRegistroAux;

                                        valorRegistroDevueltoAux += liqReg.getValorDerechos() > 0 ? liqReg.getValorDerechos() : 0;
                                        valorRegistroDevueltoMayorValor += valorRegistroDevueltoAux;

                                        valorImpuestoDevueltoAux += liqReg.getValorImpuestos() > 0 ? liqReg.getValorImpuestos() : 0;

                                        valorImpuestoDevueltoAux += liqReg.getValorOtroImp() > 0 ? liqReg.getValorOtroImp() : 0;

                                        valorImpuestoDevueltoAux += liqReg.getValorMora() > 0 ? liqReg.getValorMora() : 0;

                                        valorImpuestoDevueltoMayorValor += valorImpuestoDevueltoAux;

                                        valorCertificadoDevueltoAux = valorLiquidacionTurnoRegistroAux - valorRegistroDevueltoAux - valorImpuestoDevueltoAux - valorConservaMayorValor;

                                        valorCertificadoDevueltoMayorValor += valorCertificadoDevueltoAux;

                                        if (liqReg.getPago() != null && liqReg.getPago().getAplicacionPagos() != null) {
                                            List aPagos = liqReg.getPago().getAplicacionPagos();
                                            for (int j = 0; j < aPagos.size(); j++) {
                                                if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoCheque) {
                                                    DocPagoCheque chPago = (DocPagoCheque) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                                    if (chPago.getSaldoDocumento() > 0) {
                                                        saldoFavor += chPago.getSaldoDocumento();
                                                    }
                                                } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                                    DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                                    if (chPago.getSaldoDocumento() > 0) {
                                                        saldoFavor += chPago.getSaldoDocumento();
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }

                                //Se toma el valor de la liquidaciï¿½n del turno de certificados asociados
                                if (poseeCertificadosAsociados && nTurno.getSolicitud() instanceof SolicitudDevolucion) {
                                    List solHijas = local_SolicitudAnterior.getSolicitudesHijas();
                                    for (int j = 0; j < solHijas.size(); j++) {
                                        if (((SolicitudAsociada) solHijas.get(j)).getSolicitudHija() != null
                                                && ((SolicitudAsociada) solHijas.get(j)).getSolicitudHija().getLiquidaciones() != null
                                                && ((SolicitudAsociada) solHijas.get(j)).getSolicitudHija().getLiquidaciones().size() > 0) {
                                            Liquidacion liq = (Liquidacion) ((SolicitudAsociada) solHijas.get(j)).getSolicitudHija().getLiquidaciones().get(0);
                                            valorCertificadoDevuelto += liq.getValor() > 0 ? liq.getValor() : 0;
                                        }
                                    }
                                    //valorCertificadoDevuelto += valorLiquidacionTurnoRegistro - (valorRegistroDevuelto + valorImpuestoDevuelto);
                                }
                            }

                            if (local_SolicitudAnterior instanceof SolicitudCertificado) {
                                //Se recorren las liqudiaciones para obtener el valor total
                                List liquidaciones = local_SolicitudAnterior.getLiquidaciones();
                                Iterator i = liquidaciones.iterator();
                                while (i.hasNext()) {
                                    LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado) i.next();
                                    ValorTotalDevuelto += liqCert.getValor() > 0 ? liqCert.getValor() : 0;
                                    if (liqCert.getPago() != null && liqCert.getPago().getAplicacionPagos() != null) {
                                        List aPagos = liqCert.getPago().getAplicacionPagos();
                                        for (int j = 0; j < aPagos.size(); j++) {
                                            if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoCheque) {
                                                DocPagoCheque chPago = (DocPagoCheque) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                                if (chPago.getSaldoDocumento() > 0) {
                                                    saldoFavor += chPago.getSaldoDocumento();
                                                }
                                            } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                                DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                                if (chPago.getSaldoDocumento() > 0) {
                                                    saldoFavor += chPago.getSaldoDocumento();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (local_SolicitudAnterior instanceof SolicitudConsulta) {
                                LiquidacionTurnoConsulta liqCon = (LiquidacionTurnoConsulta) local_SolicitudAnterior.getLiquidaciones().get(0);
                                ValorTotalDevuelto += liqCon.getValor() > 0 ? liqCon.getValor() : 0;
                                if (liqCon.getPago() != null && liqCon.getPago().getAplicacionPagos() != null) {
                                    List aPagos = liqCon.getPago().getAplicacionPagos();
                                    for (int j = 0; j < aPagos.size(); j++) {
                                        if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoCheque) {
                                            DocPagoCheque chPago = (DocPagoCheque) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                            if (chPago.getSaldoDocumento() > 0) {
                                                saldoFavor += chPago.getSaldoDocumento();
                                            }
                                        } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                            DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                            if (chPago.getSaldoDocumento() > 0) {
                                                saldoFavor += chPago.getSaldoDocumento();
                                            }
                                        }
                                    }
                                }
                            }

                            if (local_SolicitudAnterior instanceof SolicitudFotocopia) {
                                LiquidacionTurnoFotocopia liqFot = (LiquidacionTurnoFotocopia) local_SolicitudAnterior.getLiquidaciones().get(0);
                                ValorTotalDevuelto += liqFot.getValor() > 0 ? liqFot.getValor() : 0;
                                if (liqFot.getPago() != null && liqFot.getPago().getAplicacionPagos() != null) {
                                    List aPagos = liqFot.getPago().getAplicacionPagos();
                                    for (int j = 0; j < aPagos.size(); j++) {
                                        if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoCheque) {
                                            DocPagoCheque chPago = (DocPagoCheque) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                            if (chPago.getSaldoDocumento() > 0) {
                                                saldoFavor += chPago.getSaldoDocumento();
                                            }
                                        } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                            DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                            if (chPago.getSaldoDocumento() > 0) {
                                                saldoFavor += chPago.getSaldoDocumento();
                                            }
                                        }
                                    }
                                }
                            }

                            /*JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno Certificado Masivos*/
                            if (local_SolicitudAnterior instanceof SolicitudCertificadoMasivo) {
                                LiquidacionTurnoCertificadoMasivo liqCon = (LiquidacionTurnoCertificadoMasivo) local_SolicitudAnterior.getLiquidaciones().get(0);
                                ValorTotalDevuelto += liqCon.getValor() > 0 ? liqCon.getValor() : 0;
                                if (liqCon.getPago() != null && liqCon.getPago().getAplicacionPagos() != null) {
                                    List aPagos = liqCon.getPago().getAplicacionPagos();
                                    for (int j = 0; j < aPagos.size(); j++) {
                                        if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoCheque) {
                                            DocPagoCheque chPago = (DocPagoCheque) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                            if (chPago.getSaldoDocumento() > 0) {
                                                saldoFavor += chPago.getSaldoDocumento();
                                            }
                                        } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                            DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                            if (chPago.getSaldoDocumento() > 0) {
                                                saldoFavor += chPago.getSaldoDocumento();
                                            }
                                        }
                                    }
                                }
                            }
                            local_TurnoAnterior = local_SolicitudAnterior.getTurnoAnterior();
                            local_SolicitudAnterior = (null == local_TurnoAnterior) ? (local_SolicitudAnterior) : (local_TurnoAnterior.getSolicitud());
                        } //:while turno_anterior

                        if (local_TurnoAnterior == null && solicitudDevolucion != null && solicitudDevolucion.getConsignaciones().size() > 0) {
                            //Se recorren las liqudiaciones para obtener el valor total de las consignaciones
                            List liquidaciones = solicitudDevolucion.getLiquidaciones();
                            Iterator i = liquidaciones.iterator();
                            while (i.hasNext()) {
                                LiquidacionTurnoDevolucion liqCert = (LiquidacionTurnoDevolucion) i.next();
                                if (liqCert.getValor() > 0) {
                                    valorConsignacionDevuelto += liqCert.getValor();
                                    ValorTotalDevuelto += liqCert.getValor();
                                }
                            }
                        }
                    }

                    session.removeAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS);
                    session.removeAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS);
                    session.removeAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS);
                    session.removeAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR);
                    session.removeAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR);
                    session.removeAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS_MAYORVALOR);
                    session.removeAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL);
                    session.removeAttribute(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);
                    /**
                     * yeferson
                     */
                    session.removeAttribute(AWDevolucion.LIQUIDA_CONSERVA);
                    session.removeAttribute(AWDevolucion.LIQUIDA_CONSERVA_GLOBAL);
                    //fin

                    NumberFormat format = new DecimalFormat("###,###,###,###,###,###,###,###.00");

                    if (valorSinconserva > 0) {
                        //session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS, format.format(valorRegistroDevuelto));
                        session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS, format.format(valorSinconserva));
                        session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR, format.format(valorSinConservaMayorValor));

                    } else {
                        if (valorLiquidaConserva > 0) {
                            valorRegistroDevuelto = valorRegistroDevuelto - valorLiquidaConserva;

                        }
                        if (valorConservaMayorValor > 0) {
                            valorRegistroDevueltoMayorValor = valorRegistroDevueltoMayorValor - valorConservaMayorValor;

                        }

                        session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS, format.format(valorRegistroDevuelto));
                        session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR, format.format(valorRegistroDevueltoMayorValor));
                    }

                    //   session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS, format.format(valorRegistroDevuelto));
                    session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS, format.format(valorCertificadoDevuelto));
                    session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS, format.format(valorImpuestoDevuelto));
                    //  session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR, format.format(valorRegistroDevueltoMayorValor));
                    session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR, format.format(valorCertificadoDevueltoMayorValor));
                    session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS_MAYORVALOR, format.format(valorImpuestoDevueltoMayorValor));
                    session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL, format.format(ValorTotalDevuelto));
                    session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCONSIGNACIONES, format.format(valorConsignacionDevuelto));
                    session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR, format.format(saldoFavor));

                    session.setAttribute(AWDevolucion.LIQUIDA_CONSERVA, format.format(valorLiquidaConserva));
                    session.setAttribute(AWDevolucion.LIQUIDA_CONSERVA_GLOBAL, format.format(valorRegistroDevuelto));

                } else if (respuesta.getTipoEvento().equals(EvnRespTurno.ENTREGA_MASIVA_REPARTO_NOTARIAL)) {
                    if (respuesta.getFolio() != null) {
                        session.setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                    }
                    if (respuesta.isTurnoDependiente()) {
                        session.setAttribute(WebKeys.DEPENDENCIA_TURNO, new Boolean(respuesta.isTurnoDependiente()));
                        session.setAttribute(WebKeys.TURNO_HIJO, respuesta.getTurnoHijo());
                    }
                    session.setAttribute(WebKeys.TURNOS, respuesta.getMapaTurnosHijos());

                } else if (respuesta.getTipoEvento().equals(EvnRespTurno.LISTAR)) {                    
                    session.setAttribute(WebKeys.LISTA_TURNOS, respuesta.getTurnos());
                    session.setAttribute(WebKeys.LISTA_TURNOS_SIR_MIG, respuesta.getTurnoSirMig());
                    session.setAttribute(WebKeys.LISTA_TURNOS_PARCIAL, respuesta.getListadoPorRangos());
                    
                } else if (respuesta.getTipoEvento().equals(EvnRespTurno.CONSULTAR_CONFIRMACION)) {
                    session.setAttribute(AWTurno.TURNO_CONFIRMADO, respuesta.getTurno());
                    session.setAttribute(AWTurno.LISTA_USUARIOS_TURNO, respuesta.getUsuarios());
                    session.setAttribute(AWTurno.FASE_TURNO, respuesta.getNombreFase());
                    session.setAttribute(AWTurno.TURNO_CONFIRMADO, AWTurno.TURNO_CONFIRMADO);
                } else if (respuesta.getTipoEvento().equals(EvnRespTurno.CONSULTAR_RELACION)) {
                    Log.getInstance().debug(AWTurno.class, "ENTRO CONSULTA RELACION doend");
                    session.setAttribute(WebKeys.LISTA_TURNOS_RELACION, respuesta.getPayload());
                    session.setAttribute(WebKeys.LISTA_TURNOS_VALIDOS, respuesta.getTurnosCertificadosValidos());
                    session.setAttribute(CRelacion.RELACION_FIRMA, respuesta.getRelacion());
                } else if (respuesta.getTipoEvento().equals(EvnRespTurno.LISTAR_TURNOS_ENTREGA_DOCUMENTOS)) {
                    session.setAttribute(WebKeys.LISTA_TURNOS_PARCIAL, respuesta.getListadoPorRangos());
                    if (respuesta.getTurnos() != null) {
                        session.setAttribute(WebKeys.LISTA_TURNOS, respuesta.getTurnos());
                    }
                }                
            }
        }
        
        /*AHERRENO
                 14/05/2012
                REQ 076_151 TRANSACCION*/
        Date fechaIni = (Date) request.getSession().getAttribute("TIEMPO_INICIO_TRANSACCION");
        Date fechaFin = new Date();
        TransaccionSIR transaccion = new TransaccionSIR();
        List<Transaccion> acciones = (List<Transaccion>) request.getSession().getAttribute("LISTA_TRANSACCION");        
        if (acciones == null) {
            acciones = new ArrayList<Transaccion>();
        }
        long calTiempo = 0;
        try {
            calTiempo = transaccion.calculoTransaccion(fechaIni, fechaFin);
        } catch (Exception ex) {
            Logger.getLogger(AWTurno.class.getName()).log(Level.SEVERE, null, ex);
        }
        DecimalFormat df = new DecimalFormat("0.000");
        double calculo = Double.valueOf(df.format((double) calTiempo / 1000).replace(',', '.'));
        System.out.println("El tiempo de la accion " + request.getParameter(WebKeys.ACCION) + " en milisegundos " + calTiempo);
        session.setAttribute(AWTurno.TIEMPO_TRANSACCION, calculo);
        Transaccion transaccionReg = new Transaccion();
        transaccionReg.setFechaTransaccion(fechaFin);
        transaccionReg.setAccionWeb(accion);
        transaccionReg.setTiempo(calTiempo);
        acciones.add(transaccionReg);
        session.setAttribute(AWTurno.LISTA_TRANSACCION, acciones);
        

        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
        if (rol.getNombre().equals("CAJERO CONSULTAS")) {
            if (accion.equals(CONTINUAR_TURNO)) {
                acciones.clear();
                request.getSession().setAttribute("LISTA_TRANSACCION", acciones);
                request.getSession().setAttribute("TIEMPO_TRANSACCION", Double.valueOf(0));
            }
        }
                
        List turnos = (List) request.getSession().getAttribute(WebKeys.LISTA_TURNOS);
        if (turnos != null) {
            Iterator itTurnos = turnos.iterator();
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                    = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

            gov.sir.core.negocio.modelo.Usuario usuarioSir;
            usuarioSir = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
            int acumTrams = 0;
            while (itTurnos.hasNext()) {
                Turno elemento = (Turno) itTurnos.next();
                String[] datosTurno = elemento.getIdWorkflow().split("-");
                int fechaTurno = Integer.parseInt(datosTurno[0]);

                Calendar cal = Calendar.getInstance();
                int fechaActual = cal.get(Calendar.YEAR);

                if (elemento.getIdFase().equals(CFase.REG_TRAMITE_SUSP_PREV) || elemento.getIdFase().equals(CFase.REG_TRAMITE_SUSP_TEMP)) {
                    if (fechaTurno < fechaActual) {
                        if (elemento.getIdFase().equals(CFase.REG_TRAMITE_SUSP_PREV)) {
                            acumTrams += 1;
                        } else if (elemento.getIdFase().equals(CFase.REG_TRAMITE_SUSP_TEMP)) {
                            acumTrams += 1;
                        }

                        try {
                            ProcesadorEventosNegocioProxy proxy = new ProcesadorEventosNegocioProxy();

                            Evn_CorrSimpleMain_VerAlertasOptions eventoAlerta;
                            eventoAlerta = new Evn_CorrSimpleMain_VerAlertasOptions(Evn_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENT);

                            eventoAlerta.setUsuarioAuriga(usuarioAuriga);
                            eventoAlerta.setUsuarioSir(usuarioSir);
                            eventoAlerta.setTurno(elemento);

                            EvnResp_CorrSimpleMain_VerAlertasOptions eventoRespuesta
                                    = (EvnResp_CorrSimpleMain_VerAlertasOptions) proxy.manejarEvento(
                                            eventoAlerta);

                            List lstFoliosSinCambios = eventoRespuesta.getFoliosInTurnoSinCambiosList();
                            if (lstFoliosSinCambios != null && lstFoliosSinCambios.size() > 0) {
                                session.setAttribute("GENERAR_ALERTA_SIN_CAMBIOS", "TRUE");
                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }                
                session.setAttribute("TRAMS_TURNOS", acumTrams);
            }
            
        }
    }

    public EvnTurno entregaMasivaRepNot(List turnos, String idsTurnosSeleccionados[], org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioNeg) {
        Iterator itTurnos = turnos.iterator();
        List turnosSel = new Vector();
        Turno temp = null;

        //Fixed-size list
        List idsTurnosSeleccionadosList = Arrays.asList(idsTurnosSeleccionados);

        while (itTurnos.hasNext()) {
            temp = (Turno) itTurnos.next();
            if (idsTurnosSeleccionadosList.contains(temp.getIdTurno())) {
                turnosSel.add(temp);
            }
        }

        return new EvnTurno(usuario, EvnTurno.ENTREGA_MASIVA_REPARTO_NOTARIAL, turnosSel, usuarioNeg);
    }

}
