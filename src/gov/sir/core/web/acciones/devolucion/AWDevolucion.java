package gov.sir.core.web.acciones.devolucion;

import gov.sir.core.eventos.devolucion.EvnDevolucion;
import gov.sir.core.eventos.devolucion.EvnRespDevolucion;
import gov.sir.core.eventos.registro.EvnCalificacion;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.registro.ANCalificacion;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.AnalisisDevolucion_InvalidParametersExceptionCollector;
import gov.sir.core.web.acciones.excepciones.PagoDevolucionException;
import gov.sir.core.web.acciones.excepciones.ResolucionInvalidaException;
import gov.sir.core.web.acciones.administracion.AWAdministracionHermod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicValidationActions;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Consignacion;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OficioPk;
import gov.sir.core.negocio.modelo.Recurso;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CAplicacionPago;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDevoluciones;
import gov.sir.core.negocio.modelo.constantes.CError;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CQueries;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.acciones.administracion.AWTrasladoTurno;
import gov.sir.core.web.acciones.excepciones.DevolucionResolucion_InvalidParametersExceptionCollector;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.ForsetiService;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz, jvelez
 */
public class AWDevolucion extends SoporteAccionWeb {

    private HttpSession session;

    /**
     * Accion que el usuario selecciona en la interfaz
     */
    private String accion = "";

    /**
     * Esta constante guarda la accion confirmar en analisis
     */
    public static final String ANALISIS_ACEPTAR = "ANALISIS_ACEPTAR";

    /**
     * Esta constante guarda la accion negar para analisis
     */
    public static final String ANALISIS_NEGAR = "ANALISIS_NEGAR";

    /**
     * Esta constante guarda la accion confirmar para resolucion
     */
    public static final String RESOLUCION_ACEPTAR = "RESOLUCION_ACEPTAR";

    public static final String RESOLUCION_FIRMAR_ACEPTAR = "RESOLUCION_FIRMAR_ACEPTAR";
    
    public static final String TIPOS_DOC = "TIPOS_DOC";
    
    public static final String ALERTA_VENCIMIENTO = "ALERTA_VENCIMIENTO";
    
    public static final String ALERTA_VENCIMIENTO_RECURSOS = "ALERTA_VENCIMIENTO_RECURSOS";
    
    public static final String AGREGAR_RESOLUCION_RECURSOS_NOT = "AGREGAR_RESOLUCION_RECURSOS_NOT";
    
    public static final String SECUENCIA = "SECUENCIA";

    /**
     * Esta constante guarda la accion imprimir para resolucion
     */
    public static final String RESOLUCION_IMPRIMIR = "RESOLUCION_IMPRIMIR";

    /**
     * Esta constante guarda la accion mayor valor para confirmar
     */
    public static final String NIVEL_CENTRAL_CONFIRMAR = "NIVEL_CENTRAL_CONFIRMAR";

    /**
     * Esta constante guarda la accion default
     */
    public static final String DEFAULT = "DEFAULT";
    
    public static final String REANOTAR_TURNO = "REANOTAR_TURNO";

    public static final String NOTIFICACION_ACEPTAR = "NOTIFICACION_ACEPTAR";

    public static final String PAGO_CONFIRMAR = "PAGO_CONFIRMAR";

    public static final String DEVOLVER_RECURSOS = "DEVOLVER_RECURSOS";

    public static final String DEVOLVER_RESOLUCION = "DEVOLVER_RESOLUCION";
    
    public static final String DEVOLVER_A_CALIFICACION = "DEVOLVER_A_CALIFICACION";
    
    public static final String DOCUMENTOS_IDENTIDAD_NATURAL = "DOCUMENTOS_IDENTIDAD_NATURAL";
    
    public static final String NOT_DESTINO = "NOT_DESTINO";
    
    public static final String NOT_TIPO = "NOT_TIPO";
    
    public static final String TIPO_RECURSOS = "TIPO_RECURSOS";
            
    public static final String CALENDAR = "CALENDAR";
    
    public static final String APODERADO_SI = "APODERADO_SI";

    public static final String APODERADO_NO = "APODERADO_NO";
    
    public static final String NOTA_DEV_NOTIFICADA = "NOTA_DEV_NOTIFICADA";
    
    public static final String FAVORABLE = "FAVORABLE";
    
    public static final String NO_FAVORABLE = "NO_FAVORABLE";
    
    public static final String RESOLUTION_ADDED = "RESOLUTION_ADDED";

    /**
     * Esta constante guarda el texto de la resolucion
     */
    public static final String RESOLUCION = "RESOLUCION";
    public static final String RESOLUCION_NUMERO = "RESOLUCION_NUMERO";
    public static final String CHEQUE_NUMERO = "CHEQUE_NUMERO";
    public static final String CHEQUE_VALOR = "CHEQUE_VALOR";
    public static final String CHEQUE_BANCO = "CHEQUE_BANCO";
    public static final String FECHA_CHEQUE = "FECHA_CHEQUE";
    public static final String AGREGAR_NOTIFICACION = "AGREGAR_NOTIFICACION";
    /**
     * Contantes de la Notificacion de Notas Devolutivas
     */
    public static final String DESTINO_NOTIFICACION = "DESTINO_NOTIFICACION";
    public static final String TIPO_NOTIFICACION = "TIPO_NOTIFICACION";
    public static final String APODERADO = "APODERADO";
    public static final String NOMBRES_NOTIFICACION = "NOMBRES_NOTIFICACION";
    public static final String APELLIDOS_NOTIFICACION = "APELLIDOS_NOTIFICACION";
    public static final String TIPO_DOCUMENTO = "TIPO_DOCUMENTO";
    public static final String NUMERO_DOCUMENTO = "NUMERO_DOCUMENTO"; 
    public static final String DIRECCION = "DIRECCION";
    public static final String TELEFONO = "TELEFONO";
    public static final String FECHA_NOTIFICACION = "FECHA_NOTIFICACION";
    public static final String OFICINA_ORIGEN = "OFICINA_ORIGEN";
    public static final String CORREO = "CORREO";
    public static final String IS_NOTIFICAR_USUARIO = "IS_NOTIFICAR_USUARIO";
    public static final String IS_NOTIFICAR_CORREO = "IS_NOTIFICAR_CORREO";
    
    // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
    // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
    public static final String NOTA_DEBITO_NUMERO = "NOTA_DEBITO_NUMERO";
    public static final String NOTA_DEBITO_VALOR = "NOTA_DEBITO_VALOR";
    public static final String NOTA_DEBITO_BANCO = "NOTA_DEBITO_BANCO";
    public static final String FECHA_NOTA_DEBITO = "FECHA_NOTA_DEBITO";

    public static final String EFECTIVO_REGRESADO = "EFECTIVO_REGRESADO";
    public static final String FORMA_PAGO = "FORMA_PAGO";

    public static final String CUENTA_NUMERO = "CUENTA_NUMERO";
    public static final String TRANSFERENCIA_VALOR = "TRANSFERENCIA_VALOR";
    public static final String TRANSFERENCIA_BANCO = "TRANSFERENCIA_BANCO";
    public static final String TITULAR_TRANSFERENCIA = "TITULAR_TRANSFERENCIA";

    public static final String NUMERO_ORDEN = "NUMERO_ORDEN";
    public static final String FECHA_ORDEN = "FECHA_ORDEN";

    public static final String REPOSICION_RECURSO = "REPOSICION_RECURSO";

    public static final String APELACION_RECURSO = "APELACION_RECURSO";

    public static final String NO_RECURSO = "NO_RECURSO";

    public static final String RECURSO = "RECURSO";
    
    public static final String CHECK_RECURSO_FAVORABLE = "CHECK_RECURSO_FAVORABLE";

    /**
     * Esta constante guarda la acción confirmar en reposición
     */
    public static final String REPOSICION_CONFIRMAR = "REPOSICION_CONFIRMAR";

    /**
     * Esta constante guarda la acción negar en restitución
     */
    public static final String REPOSICION_NEGAR = "REPOSICION_NEGAR";

    /**
     * Esta constante guarda la acción confirmar en apelación
     */
    public static final String APELACION_CONFIRMAR = "APELACION_CONFIRMAR";

    /**
     * Esta constante guarda la acción negar en apelación
     */
    public static final String APELACION_NEGAR = "APELACION_NEGAR";

    /**
     * Esta constante guarda la acción para imprimir un borrador
     */
    public static final String IMPRIMIR_BORRADOR = "IMPRIMIR_BORRADOR";

    public static final String PAGEITEM__LIQUIDVALORDERECHOS = "PAGEITEM__LIQUIDVALORDERECHOS";

    public static final String PAGEITEM__LIQUIDVALORIMPUESTOS = "PAGEITEM__LIQUIDVALORIMPUESTOS";

    public static final String PAGEITEM__LIQUIDVALORCERTIFICADOS = "PAGEITEM__LIQUIDVALORCERTIFICADOS";

    public static final String PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR = "PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR";

    public static final String PAGEITEM__LIQUIDVALORIMPUESTOS_MAYORVALOR = "PAGEITEM__LIQUIDVALORIMPUESTOS_MAYORVALOR";

    public static final String PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR = "PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR";
    
    public static final String DESTINATARIOS = "DESTINATARIOS";
    
    public static final String TIPOS = "TIPOS";
    
    public static final String TIPO_RECURSO = "TIPO_RECURSO";
            
    public static final String CHECK_APODERADO = "CHECK_APODERADO";

    /**
     * yeferson inicio
     */
    public static final String LIQUIDA_CONSERVA = "LIQUIDA_CONSERVA";
    public static final String LIQUIDA_CONSERVA_GLOBAL = "LIQUIDA_CONSERVA_GLOBAL";
    //fin

    public static final String PAGEITEM__LIQUIDVALORTOTAL = "PAGEITEM__LIQUIDVALORTOTAL";

    public static final String PAGEITEM__LIQUIDACIONTOTAL = "PAGEITEM__LIQUIDACIONTOTAL";

    public static final String PAGEITEM__LIQUIDVALORCONSIGNACIONES = "PAGEITEM__LIQUIDVALORCONSIGNACIONES";

    public static final String PAGEITEM__LIQUIDSALDOFAVOR = "PAGEITEM__LIQUIDSALDOFAVOR";

    public static final String ESPERAR_INTERPONER_RECURSOS = "ESPERAR_INTERPONER_RECURSOS";

    public static final String DEVOLVER_SIN_RECURSOS = "DEVOLVER_SIN_RECURSOS";

    public static final String FINALIZAR_SIN_RECURSOS = "FINALIZAR_SIN_RECURSOS";

    public static final String CONFIRMAR_RECURSOS = "CONFIRMAR_RECURSOS";

    public static final String NEGAR_RECURSOS = "NEGAR_RECURSOS";
    
    public static final String NOTIFICAR_NOTA_DEVOLUTIVA = "NOTIFICAR_NOTA_DEVOLUTIVA";
    
    public static final String NOTA_DEVOLUTIVA_NOTIFICADA = "NOTA_DEVOLUTIVA_NOTIFICADA";
    
    public static final String RECURSOS_NOTA_DEVOLUTIVA = "RECURSOS_NOTA_DEVOLUTIVA";
    
    public static final String NOTIFICAR_NOTA_CONFIRMAR = "NOTIFICAR_NOTA_CONFIRMAR";
    
    public static final String NOTA_NOTIFICADA_CONFIRMAR = "NOTA_NOTIFICADA_CONFIRMAR";
    
    public static final String CHECK_RECURSOS_NOT = "CHECK_RECURSOS_NOT";
    
    public static final String RECURSOS_NOT = "RECURSOS_NOT";
    
    public static final String NO_RECURSOS_NOT = "NO_RECURSO_NOT";
    
    public static final String AGREGAR_RECURSO_NOTA_DEVOLUTIVA = "AGREGAR_RECURSO_NOTA_DEVOLUTIVA";

    /**
     * Constante que identifica la acción que confirma que ya está disponible el
     * dinero para realizar la devolución.
     */
    public static final String CONFIRMAR_DINERO = "CONFIRMAR_DINERO";

    /**
     * Constante que identifica la acción que permite agregar recursos a un
     * turno de devolución.
     */
    public static final String AGREGAR_RECURSO = "AGREGAR_RECURSO";

    public static final String ELIMINAR = "ELIMINAR";

    public static final String ELIMINAR_EN_RECURSOS = "ELIMINAR_EN_RECURSOS";

    /**
     * Constantes del formulario para agregar recursos al turno de devolución
     * (Indica el tipo de recurso que se va a interponer)
     */
    public static final String T_RECURSO = "T_RECURSO";

    /**
     * Constantes del formulario para agregar recursos al turno de devolución
     * (Indica el usuario que agrega la anotación acerca del recurso)
     */
    
    public static final String USUARIO_RECURSO = "USUARIO_RECURSO";

    /**
     * Constantes del formulario para agregar recursos al turno de devolución
     * (Indica el usuario que agrega la anotación acerca del recurso)
     */
    public static final String DESCRIPCION_RECURSO = "DESCRIPCION_RECURSO";

    public static final String CHECK_RECURSOS = "CHECK_RECURSOS";
    
    public static final String CHECK_JUZGADO = "CHECK_JUZGADO";
    
    public static final String ENVIA_JUZGADO = "ENVIA_JUZGADO";
    
    public static final String CONTINUE_WF = "CONTINUE_WF";

    public static final String CHECKED_SI = "CHECKED_SI";

    public static final String CHECKED_NO = "CHECKED_NO";

    public static final String FECHA_RECURSO = "FECHA_RECURSO";

    public static final String FECHA_EJECUTORIA = "FECHA_EJECUTORIA";

    public static final String DESCRIPCION_AMPLIACION_ = "DESCRIPCION_AMPLIACION_";

    public static final String EDITAR_RECURSO = "EDITAR_RECURSO";
    
    public static final String EDITAR_RECURSO_NOTA_DEVOLUTIVA = "EDITAR_RECURSO_NOTA_DEVOLUTIVA";
    
    public static final String GUARDAR_RECURSO_NOTA_DEVOLUTIVA = "GUARDAR_RECURSO_NOTA_DEVOLUTIVA";
    
    public static final String GUARDAR_NOTIFICACION = "GUARDAR_NOTIFICACION";
    
    public static final String ID_TO_MODIFY = "ID_TO_MODIFY";
    
    public static final String ELIMINAR_NOTIFICACION = "ELIMINAR_NOTIFICACION";
    
    public static final String EDITAR_NOTIFICACION = "EDITAR_NOTIFICACION";
    
    public static final String MODIFY_NOTIFICATION = "MODIFY_NOTIFICATION";
    
    public static final String ELIMINAR_RECURSO_NOTA_DEVOLUTIVA = "ELIMINAR_RECURSO_NOTA_DEVOLUTIVA";
    
    public static final String CARGAR_RECURSOS_NOTA = "CARGAR_RECURSOS_NOTA";

    /**
     * Constante que indica que se han aceptado los recursos interpuestos por el
     * ciudadano
     */
    public static final String ACEPTAR_INTERPOSICION_RECURSOS = "ACEPTAR_INTERPOSICION_RECURSOS";
    
    /**
     * Constante que indica que se han aceptado los recursos interpuestos por el
     * ciudadano en Recursos Nota Devolutiva
     */
    public static final String ACEPTAR_INTERPOSICION_RECURSOS_NOT = "ACEPTAR_INTERPOSICION_RECURSOS_NOT";
    /**
     * Constante que indica que se han negado los recursos interpuestos por el
     * ciudadano
     */
    public static final String NEGAR_INTERPOSICION_RECURSOS = "NEGAR_INTERPOSICION_RECURSOS";

    /**
     * Constante para indicar que se regresa un turno de resolución a la fase de
     * análisis
     */
    public static final String REGRESAR_RESOLUCION_ANALISIS = "REGRESAR_RESOLUCION_ANALISIS";

    /**
     * Constante que indica que debe realizarse la modificación del valor de la
     * devolución una vez se ha pasado por la fase de recursos.
     */
    public static final String MODIFICAR_VALOR_DEVOLUCION = "MODIFICAR_VALOR_DEVOLUCION";

    public static final String AGREGAR_RESOLUCION = "AGREGAR_RESOLUCION";

    public static final String LISTA_RESOLUCIONES = "LISTA_RESOLUCIONES";

    public static final String FECHA_RESOLUCION = "FECHA_RESOLUCION";

    public static final String AGREGAR_RESOLUCION_RECURSO = "AGREGAR_RESOLUCION_RECURSO";
    
    public static final String CURRENT_STATE_NOTA_NOTIFICADA = "CURRENT_STATE_NOTA_NOTIFICADA";
    
    public static final String CHANGE_STATE_NOTA_NOTIFICADA = "CHANGE_STATE_NOTA_NOTIFICADA";

    public static final String RELOAD_PAGE = "RELOAD_PAGE";
    
    public static final String REGRESAR_TURNO = "REGRESAR_TURNO";
    
    private List resoluciones;

    public static final String CAMBIAR_TURNO_DEVOLUCION = "CAMBIAR_TURNO_DEVOLUCION";
    public static final String ELIMINAR_CONSIGNACION_CHEQUE = "ELIMINAR_CONSIGNACION_CHEQUE";
    public static final String ELIMINAR_CONSIGNACION_CHEQUE_ANALISIS = "ELIMINAR_CONSIGNACION_CHEQUE_ANALISIS";
    public static final String TIPO_BUSQUEDA = "TIPO_BUSQUEDA";
    public static final String BUSQUEDA_CONSIGNACION = "BUSQUEDA_CONSIGNACION";
    public static final String AGREGAR_CONSIGNACION_CHEQUE = "AGREGAR_CONSIGNACION_CHEQUE";
    
    public static final String ELIMINAR_RECURSOS_NOT = "ELIMINAR_RECURSOS_NOT";

    public static final String LISTA_RESOLUCIONES_PAGO = "LISTA_RESOLUCIONES_PAGO";
    
    public static final String CARGAR_CONTEXTO = "CARGAR_CONTEXTO";

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
        accion = request.getParameter("ACCION");

        if ((accion == null) || (accion.length() == 0)) {
            throw new AccionWebException("Debe indicar una accion");
        }

        session = request.getSession();

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);

        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Rol rol = (Rol) session.getAttribute(WebKeys.ROL);

        if (accion.equals(AWDevolucion.ANALISIS_ACEPTAR)) {
            SolicitudDevolucion solicitud = (SolicitudDevolucion) turno.getSolicitud();
            if (solicitud.getTurnoAnterior() == null && (solicitud.getConsignaciones() == null || solicitud.getConsignaciones().size() == 0)) {
                AnalisisDevolucion_InvalidParametersExceptionCollector excepcion = new AnalisisDevolucion_InvalidParametersExceptionCollector();
                excepcion.addError("El turno debe tener un turno anterior a devolver o consignaciones/cheques ");
                throw excepcion;
            }
            EvnDevolucion evnDevolucion = new EvnDevolucion(usuarioAuriga, EvnDevolucion.ANALISIS_ACEPTAR, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);
            evnDevolucion.setUID(request.getSession().getId());
            Boolean modificadoTurno = (Boolean) session.getAttribute(WebKeys.TURNO_DEVOLUCION_MODIFICADO);
            evnDevolucion.setTurnoDevolucionModificado(modificadoTurno != null ? modificadoTurno.booleanValue() : false);
            if (turno.getSolicitud() != null && turno.getSolicitud().getTurnoAnterior() != null) {
                validarTurnoAnt(request, false);
                List notasInformativas = (List) session.getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
                evnDevolucion.setNotasInformativas(notasInformativas);
                if (turno.getSolicitud().getTurnoAnterior().getIdWorkflow() != null) {
                    evnDevolucion.setIdWfTurnoAnterior(turno.getSolicitud().getTurnoAnterior().getIdWorkflow());
                }
            }
            return evnDevolucion;
        } else if (accion.equals(AWDevolucion.ANALISIS_NEGAR)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.ANALISIS_NEGAR, turno, fase, estacion, EvnDevolucion.NEGAR, usuario);
        } else if (accion.equals(AWDevolucion.NOTA_DEVOLUTIVA_NOTIFICADA)) {
           return notaDevolutivaNotificada(request);  
        } else if (accion.equals(CARGAR_CONTEXTO)){
            return cargarContexto(request);
        } 
        if (accion.equals(AWDevolucion.CONFIRMAR_RECURSOS)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.CONFIRMAR_RECURSOS, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);

        } else if (accion.equals(AWDevolucion.NEGAR_RECURSOS)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.ANALISIS_NEGAR, turno, fase, estacion, EvnDevolucion.NEGAR, usuario);

        } else if (accion.equals(AWDevolucion.RESOLUCION_ACEPTAR)) {
            /*
			EvnDevolucion evn = new EvnDevolucion(usuarioAuriga,
			                                      EvnDevolucion.
			                                      RESOLUCION_ACEPTAR, turno,
			                                      fase, estacion,
			                                      EvnDevolucion.DEFAULT,
			                                      usuario);
			evn.setResolucion(texto);
			evn.setUID(session.getId());
             */
            EvnDevolucion evn;
            evn = (EvnDevolucion) do_PageProcessingProcess_Resolucion_OnAceptar(request);

            return evn;

        } else if (accion.equals(AGREGAR_RESOLUCION)) {
            return agregarResolucion(request);
            
        } else if (accion.equals(NOTIFICAR_NOTA_DEVOLUTIVA)) {
            return notificarNotaDevolutiva(request);       
        } else if (accion.equals(DEVOLVER_A_CALIFICACION)){
            return devolverCalificacion(request);
        } else if (accion.equals(AGREGAR_NOTIFICACION)) {
               return agregarNotificacion(request);
        } else if (accion.equals(NOTIFICAR_NOTA_CONFIRMAR)) {
            return avanzarNotificarNotaDevolutiva(request);
        } else if (accion.equals(NOTA_NOTIFICADA_CONFIRMAR)) {
            return notaNotificadaConfirmar(request);
        } else if (accion.equals(GUARDAR_NOTIFICACION) || accion.equals(EDITAR_NOTIFICACION) || accion.equals(ELIMINAR_NOTIFICACION)){
            return crudNotification(request,accion);
        } else if (accion.equals(ALERTA_VENCIMIENTO)) {
            return avanzarNotaNotificadaACertifados(request);
        } else if (accion.equals(ALERTA_VENCIMIENTO_RECURSOS)){
            return avanzarNotaNotificadaACertifados(request);
        } else if (accion.equals(AGREGAR_RECURSO_NOTA_DEVOLUTIVA)){
            return agregarRecursosNotaDevolutiva(request);
        } else if (accion.equals(CHANGE_STATE_NOTA_NOTIFICADA)){
            return changeStateNotaNotificada(request);
        } else if (accion.equals(ACEPTAR_INTERPOSICION_RECURSOS_NOT)){
            return agregarRecursosNota(request);
        } else if (accion.equals(AGREGAR_RESOLUCION_RECURSO)) {
            return agregarResolucion(request);
        } else if (accion.equals(CARGAR_RECURSOS_NOTA)){
            return recursosNotaDevolutiva(request);
        } else if (accion.equals(AGREGAR_RESOLUCION_RECURSOS_NOT)){
            return agregarResolucionRecursosNot(request);
        } else if (accion.equals(ELIMINAR) || accion.equals(ELIMINAR_EN_RECURSOS)) {
            return eliminarResoluciones(request);
        } else if (accion.equals(ELIMINAR_RECURSOS_NOT)){
            return eliminarResolucionesNot(request);
        } else if (accion.equals(AWDevolucion.RESOLUCION_FIRMAR_ACEPTAR)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.RESOLUCION_FIRMAR_ACEPTAR, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);

        } else if (accion.equals(AWDevolucion.NIVEL_CENTRAL_CONFIRMAR)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.AVANZAR, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);

        } //Regresar de la fase de resolución a la fase de análisis
        else if (accion.equals(AWDevolucion.REGRESAR_RESOLUCION_ANALISIS)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.DEVOLVER_RESOLUCION_ANALISIS, turno, fase, estacion, EvnDevolucion.NEGAR, usuario);
        } //Regresar de la fase de pago a la fase de resolucion
        else if (accion.equals(AWDevolucion.DEVOLVER_RESOLUCION)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.DEVOLVER_RESOLUCION, turno, fase, estacion, EvnDevolucion.NEGAR_RESOLUCION, usuario);
        } //Regresar de la fase de pago a la fase de recursos
        else if (accion.equals(AWDevolucion.DEVOLVER_RECURSOS)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.DEVOLVER_RECURSOS, turno, fase, estacion, EvnDevolucion.NEGAR_RECURSOS, usuario);
        } else if (accion.equals(AWDevolucion.EDITAR_RECURSO)) {
            String posicion = (String) request.getParameter(WebKeys.POSICION);
            int pos = Integer.parseInt(posicion);
            String ampliacion = (String) request.getParameter(AWDevolucion.DESCRIPCION_AMPLIACION_ + posicion);
            if (ampliacion == null || ampliacion.trim().equals("")) {
                throw new ResolucionInvalidaException("No hay ampliación para el recurso No: " + String.valueOf(pos + 1));
            }

            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.EDITAR_RECURSO, turno, fase, estacion, EvnDevolucion.DEFAULT, usuario);
            evn.setDatoAmpliacionRecurso(ampliacion);
            evn.setPosicionRecurso(pos);
            return evn;
        } //Confirmar dinero SNR.
        else if (accion.equals(AWDevolucion.CONFIRMAR_DINERO)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.CONFIRMAR_DINERO, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);

        } //Los recursos interpuestos fueron aceptados.
        else if (accion.equals(AWDevolucion.ACEPTAR_INTERPOSICION_RECURSOS)) {

            String fechaEjecutoria = request.getParameter(AWDevolucion.FECHA_EJECUTORIA);
            Date dateEjecutoria = null;
            List resoluciones = (List) session.getAttribute(LISTA_RESOLUCIONES);
            if(resoluciones == null || resoluciones.isEmpty()){
                try{
                HermodService hs = HermodService.getInstance();
                TurnoPk tid = new TurnoPk();
                tid.anio = turno.getAnio();
                tid.idCirculo = turno.getIdCirculo();
                tid.idProceso = turno.getIdProceso();
                tid.idTurno = turno.getIdTurno(); 
                resoluciones = hs.getOficiosTurno(tid); 

                } catch(HermodException he){
                    System.out.println("ERROR: " + he);
                }
            }
            if (resoluciones == null || resoluciones.size() == 0) {
                throw new ResolucionInvalidaException("No hay resoluciones en el turno");
            }
            Oficio resolucion = (Oficio) resoluciones.get(resoluciones.size() - 1);
            if (resolucion.getFechaFirma() == null) {

                if ((fechaEjecutoria == null)
                        || (fechaEjecutoria.equals(""))) {
                    throw new ResolucionInvalidaException("Debe ingresar la fecha ejecutoria");

                } else {

                    try {
                        dateEjecutoria = DateFormatUtil.parse(fechaEjecutoria);
                    } catch (Exception e) {
                        throw new ResolucionInvalidaException("La fecha ejecutoria es inválida" + e.getMessage());
                    }
                }
                if (dateEjecutoria.getTime() <= resolucion.getFechaCreacion().getTime()) {
                    throw new ResolucionInvalidaException("La fecha ejecutoría debe ser mayor a la fecha de la última resolución");
                }
            }

            EvnDevolucion evn;
            String interponeRecursos = (String) session.getAttribute(AWDevolucion.CHECK_RECURSOS);
            EvnDevolucion evento = new EvnDevolucion(usuarioAuriga, EvnDevolucion.ACEPTAR_INTERPOSICION_RECURSOS, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);
            if (interponeRecursos == null || interponeRecursos.equals(AWDevolucion.CHECKED_SI)) {
                evn = (EvnDevolucion) do_PageProcessingProcess_Resolucion_OnAceptar(request);
                evento.setLiquidacion(evn.getLiquidacion());
                if (evn.getLiquidacionMayorValor() != null) {
                    evento.setLiquidacionMayorValor(evn.getLiquidacionMayorValor());
                }
                evento.setValorSaldoFavor(evn.getValorSaldoFavor());
            }
            evento.setInterponeRecursos(interponeRecursos != null ? interponeRecursos : "");
            evento.setFechaEjecutoria(dateEjecutoria);
            return evento;

        } //Los recursos interpuestos fueron rechazados.
        else if (accion.equals(AWDevolucion.NEGAR_INTERPOSICION_RECURSOS)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.NEGAR_INTERPOSICION_RECURSOS, turno, fase, estacion, EvnDevolucion.NEGAR, usuario);

        } else if (accion.equals(AWDevolucion.ESPERAR_INTERPONER_RECURSOS)
                || accion.equals(AWDevolucion.DEVOLVER_SIN_RECURSOS)
                || accion.equals(AWDevolucion.FINALIZAR_SIN_RECURSOS)) {

            String texto = "";
            texto = request.getParameter(AWDevolucion.RESOLUCION_NUMERO);
            if (texto == null || texto.length() <= 0) {
                throw new ResolucionInvalidaException("El número de resolucion que desea agregar es invalido");
            }

            String accionEvento = "";
            if (accion.equals(AWDevolucion.ESPERAR_INTERPONER_RECURSOS)) {
                accionEvento = EvnDevolucion.ESPERAR_INTERPONER_RECURSOS;
            } else if (accion.equals(AWDevolucion.DEVOLVER_SIN_RECURSOS)) {
                accionEvento = EvnDevolucion.DEVOLVER_SIN_RECURSOS;
            } else {
                accionEvento = EvnDevolucion.FINALIZAR_SIN_RECURSOS;
            }

            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, accionEvento, turno, fase, estacion, EvnDevolucion.DEFAULT, usuario);
            evn.setNumeroResolucion(texto);
            return evn;

        } else if (accion.equals(AWDevolucion.PAGO_CONFIRMAR)) {

            PagoDevolucionException pde = new PagoDevolucionException();

            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.PAGO_CONFIRMAR, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);

            evn.setUID(session.getId());
            evn.setRol(rol);
            String forma_pago = "";

            SolicitudDevolucion sol = (SolicitudDevolucion) turno.getSolicitud();
            if (sol.isAprobada()) {
                forma_pago = request.getParameter(AWDevolucion.FORMA_PAGO);

                String cheque_numero = "";
                cheque_numero = request.getParameter(AWDevolucion.CHEQUE_NUMERO);

                String cheque_banco = "";
                cheque_banco = request.getParameter(AWDevolucion.CHEQUE_BANCO);

                String cheque_valor = "";
                cheque_valor = request.getParameter(AWDevolucion.CHEQUE_VALOR);

                String cheque_fecha = "";
                cheque_fecha = request.getParameter(AWDevolucion.FECHA_CHEQUE);

                // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
                // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
                String notaDebitoNumero = "";
                notaDebitoNumero = request.getParameter(AWDevolucion.NOTA_DEBITO_NUMERO);

                String notaDebitoBanco = "";
                notaDebitoBanco = request.getParameter(AWDevolucion.NOTA_DEBITO_BANCO);

                String notaDebitoValor = "";
                notaDebitoValor = request.getParameter(AWDevolucion.NOTA_DEBITO_VALOR);

                String notaDebitoFecha = "";
                notaDebitoFecha = request.getParameter(AWDevolucion.FECHA_NOTA_DEBITO);

                String trans_banco = "";
                trans_banco = request.getParameter(AWDevolucion.TRANSFERENCIA_BANCO);

                String trans_titular = "";
                trans_titular = request.getParameter(AWDevolucion.TITULAR_TRANSFERENCIA);

                String trans_numero = "";
                trans_numero = request.getParameter(AWDevolucion.CUENTA_NUMERO);

                String trans_valor = "";
                trans_valor = request.getParameter(AWDevolucion.TRANSFERENCIA_VALOR);

                String efectivo = "";
                efectivo = request.getParameter(AWDevolucion.EFECTIVO_REGRESADO);

                String orden_numero = "";
                orden_numero = request.getParameter(AWDevolucion.NUMERO_ORDEN);

                String orden_fecha = "";
                orden_fecha = request.getParameter(AWDevolucion.FECHA_ORDEN);

                request.getSession().setAttribute(AWDevolucion.FORMA_PAGO, forma_pago);
                request.getSession().setAttribute(AWDevolucion.CHEQUE_NUMERO, cheque_numero);
                request.getSession().setAttribute(AWDevolucion.CHEQUE_BANCO, cheque_banco);
                request.getSession().setAttribute(AWDevolucion.CHEQUE_VALOR, cheque_valor);
                request.getSession().setAttribute(AWDevolucion.FECHA_CHEQUE, cheque_fecha);
                // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
                // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
                request.getSession().setAttribute(AWDevolucion.NOTA_DEBITO_NUMERO, notaDebitoNumero);
                request.getSession().setAttribute(AWDevolucion.NOTA_DEBITO_BANCO, notaDebitoBanco);
                request.getSession().setAttribute(AWDevolucion.NOTA_DEBITO_VALOR, notaDebitoValor);
                request.getSession().setAttribute(AWDevolucion.FECHA_NOTA_DEBITO, notaDebitoFecha);
                request.getSession().setAttribute(AWDevolucion.TRANSFERENCIA_BANCO, trans_banco);
                request.getSession().setAttribute(AWDevolucion.TITULAR_TRANSFERENCIA, trans_titular);
                request.getSession().setAttribute(AWDevolucion.CUENTA_NUMERO, trans_numero);
                request.getSession().setAttribute(AWDevolucion.TRANSFERENCIA_VALOR, trans_valor);
                request.getSession().setAttribute(AWDevolucion.EFECTIVO_REGRESADO, efectivo);

                request.getSession().setAttribute(AWDevolucion.NUMERO_ORDEN, orden_numero);
                request.getSession().setAttribute(AWDevolucion.FECHA_ORDEN, orden_fecha);

                Date dateCheque = null;
                Date dateNotaDebito = null;

                //SI HAY DATOS EN UN CAMPO DE CHEQUE, GARANTIZAR QUE ESTEN
                //LLENOS TODOS LOS DATOS DE CHEQUE.
                if (forma_pago.equals(WebKeys.SIN_SELECCIONAR)) {
                    pde.addError("Debe elegir una forma de pago válida");
                    throw pde;
                } else if (forma_pago.equals(CAplicacionPago.CHEQUE)) {

                    if (cheque_numero == null || cheque_numero.length() <= 0) {
                        pde.addError("El número del cheque está en blanco, por favor ingréselo.");
                    }

                    if ((cheque_fecha == null)
                            || ("".equals(cheque_fecha))) {
                        pde.addError("Debe ingresar la fecha del cheque");

                    } else {
                        //String send1 = null;

                        try {
                            dateCheque = DateFormatUtil.parse(cheque_fecha);
                        } catch (Exception e) {
                            pde.addError("La fecha del cheque es inválida" + e.getMessage());
                        }
                    }

                    if (cheque_banco == null || cheque_banco.length() <= 0 || cheque_banco.equals(WebKeys.SIN_SELECCIONAR)) {
                        pde.addError("Seleccione el banco.");
                    }

                    if (cheque_valor == null || cheque_valor.length() <= 0) {
                        pde.addError("El valor del cheque está en blanco, por favor ingréselo.");
                    } else {
                        try {
                            //cheque_valor.replaceAll(".", "");
                            cheque_valor = cheque_valor.replaceAll(",", "");
                            int i = new Double(cheque_valor).intValue();
                        } catch (Exception e) {
                            pde.addError("El valor del cheque debe ser numérico.");
                        }
                    }

                    // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
                    // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
                } else if (forma_pago.equals(CAplicacionPago.NOTA_DEBITO)) {

                    if (notaDebitoNumero == null || "".equals(notaDebitoNumero) || notaDebitoNumero.length() <= 0) {
                        if (notaDebitoNumero == null || "".equals(notaDebitoNumero)) {
                            pde.addError("El número de la nota débito está en blanco, por favor ingréselo.");
                        } else {
                            pde.addError("El número de la nota débito no debe ser menor a 1, por favor ingréselo nuevamente.");
                        }

                    }

                    if ((notaDebitoFecha == null) || ("".equals(notaDebitoFecha))) {
                        pde.addError("Debe ingresar la fecha de la nota débito");

                    } else {
                        //String send1 = null;

                        try {
                            dateNotaDebito = DateFormatUtil.parse(notaDebitoFecha);
                        } catch (Exception e) {
                            pde.addError("La fecha de la nota débito es inválida" + e.getMessage());
                        }
                    }

                    if (notaDebitoBanco == null || notaDebitoBanco.length() <= 0 || notaDebitoBanco.equals(WebKeys.SIN_SELECCIONAR)) {
                        pde.addError("Seleccione el banco.");
                    }

                    if (notaDebitoValor == null || notaDebitoValor.length() <= 0) {
                        pde.addError("El valor de la nota débito está en blanco, por favor ingréselo.");
                    } else {
                        try {
                            //cheque_valor.replaceAll(".", "");
                            notaDebitoValor = notaDebitoValor.replaceAll(",", "");
                            int i = new Double(notaDebitoValor).intValue();
                        } catch (Exception e) {
                            pde.addError("El valor de la nota débito debe ser numérico.");
                        }
                    }

                } else if (forma_pago.equals(CAplicacionPago.TRANSFERENCIA)) {

                    if (trans_banco == null || trans_banco.length() <= 0 || trans_banco.equals(WebKeys.SIN_SELECCIONAR)) {
                        pde.addError("Seleccione el banco.");
                    }

                    if (trans_titular == null || trans_titular.length() <= 0) {
                        pde.addError("El titular de está en blanco, por favor ingréselo.");
                    }

                    if (trans_numero == null || trans_numero.length() <= 0) {
                        pde.addError("El número de la cuenta está en blanco, por favor ingréselo.");
                    }

                    if (trans_valor == null || trans_valor.length() <= 0) {
                        pde.addError("El valor de la transferencia está en blanco, por favor ingréselo.");
                    } else {
                        try {
                            //trans_valor.replaceAll(".", "");
                            trans_valor = trans_valor.replaceAll(",", "");
                            int i = new Double(trans_valor).intValue();
                        } catch (Exception e) {
                            pde.addError("El valor de la transferencia debe ser numérico.");
                        }
                    }

                } else if (forma_pago.equals(CAplicacionPago.EFECTIVO)) {

                    if (efectivo == null || efectivo.length() <= 0) {
                        pde.addError("El valor en efectivo está en blanco, por favor ingréselo.");
                    } else {
                        try {
                            //efectivo.replaceAll(".", "");
                            efectivo = efectivo.replaceAll(",", "");
                            int i = new Double(efectivo).intValue();
                        } catch (Exception e) {
                            pde.addError("El valor del efectivo debe ser numérico.");
                        }
                    }

                }

                if (orden_numero == null || orden_numero.length() <= 0) {
                    pde.addError("El número de la orden de pago está en blanco, por favor ingréselo.");
                }

                Date dateOrden = null;
                if ((orden_fecha == null) || ("".equals(orden_fecha))) {
                    pde.addError("Debe ingresar la fecha de la orden de pago");

                } else {
                    try {
                        dateOrden = DateFormatUtil.parse(orden_fecha);
                    } catch (Exception e) {
                        pde.addError("La fecha de orden de pago es inválida" + e.getMessage());
                    }
                }

                evn.setNumeroOrden(orden_numero);
                evn.setFechaOrden(dateOrden);

                if (pde.getErrores().size() > 0) {
                    throw pde;
                }

                //NUMERO DEL CHEQUE.
                if (forma_pago.equals(CAplicacionPago.CHEQUE)) {
                    if (cheque_numero != null && cheque_numero.length() > 0) {
                        evn.setChequeNumero(cheque_numero);
                    }
                    if (cheque_banco != null && cheque_banco.length() > 0) {
                        evn.setChequeBanco(cheque_banco);
                    }
                    if (cheque_valor != null && cheque_valor.length() > 0) {
                        evn.setChequeValor(new Double(cheque_valor).doubleValue());
                    }
                    if (cheque_fecha != null && cheque_fecha.length() > 0) {
                        evn.setChequeFecha(dateCheque);
                    }
                } else if (forma_pago.equals(CAplicacionPago.NOTA_DEBITO)) {
                    if (notaDebitoNumero != null && notaDebitoNumero.length() > 0) {
                        evn.setNotaDebitoNumero(notaDebitoNumero);
                    }
                    if (notaDebitoBanco != null && notaDebitoBanco.length() > 0) {
                        evn.setNotaDebitoBanco(notaDebitoBanco);
                    }
                    if (notaDebitoValor != null && notaDebitoValor.length() > 0) {
                        evn.setNotaDebitoValor(new Double(notaDebitoValor).doubleValue());
                    }
                    if (notaDebitoFecha != null && notaDebitoFecha.length() > 0) {
                        evn.setNotaDebitoFecha(dateNotaDebito);
                    }
                } else if (forma_pago.equals(CAplicacionPago.TRANSFERENCIA)) {//Transferencia.
                    if (trans_numero != null && trans_numero.length() > 0) {
                        evn.setTransferenciaNumero(trans_numero);
                    }
                    if (trans_titular != null && trans_titular.length() > 0) {
                        evn.setTransferenciaTitular(trans_titular);
                    }
                    if (trans_valor != null && trans_valor.length() > 0) {
                        evn.setTransferenciaValor(new Double(trans_valor).doubleValue());
                    }
                    if (trans_banco != null && trans_banco.length() > 0) {
                        evn.setTransferenciaBanco(trans_banco);
                    }
                } else if (forma_pago.equals(CAplicacionPago.EFECTIVO)) {

                    if (efectivo != null && efectivo.length() > 0) {
                        evn.setEfectivoValor(new Double(efectivo).doubleValue());
                    }

                }
            }

            evn.setFormaPago(forma_pago);

            return evn;

        } else if (accion.equals(AWDevolucion.REPOSICION_RECURSO)) {
            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.REPOSICION_RECURSO, turno, fase, estacion, EvnDevolucion.REPOSICION_RECURSO, usuario);
            return evn;

        } else if (accion.equals(AWDevolucion.APELACION_RECURSO)) {
            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.APELACION_RECURSO, turno, fase, estacion, EvnDevolucion.APELACION_RECURSO, usuario);
            return evn;

        } else if (accion.equals(AWDevolucion.NO_RECURSO) || accion.equals(AWDevolucion.RECURSO)) {
            session.setAttribute(AWDevolucion.CHECK_RECURSOS, request.getParameter(AWDevolucion.CHECK_RECURSOS));
            return null;
            
        } else if (accion.equals(AWDevolucion.ENVIA_JUZGADO) || accion.equals(AWDevolucion.CONTINUE_WF)) {
            String v = request.getParameter(AWDevolucion.CHECK_JUZGADO);
            session.setAttribute(AWDevolucion.CHECK_JUZGADO, request.getParameter(AWDevolucion.CHECK_JUZGADO));
            return null;
            
        } else if (accion.equals(AWDevolucion.FAVORABLE) || accion.equals(AWDevolucion.NO_FAVORABLE)){
            session.setAttribute(AWDevolucion.CHECK_RECURSO_FAVORABLE, request.getParameter(CHECK_RECURSO_FAVORABLE));
            return null;
            
        } else if (accion.equals(AWDevolucion.APODERADO_SI) || accion.equals(AWDevolucion.APODERADO_NO)) {
            preservarInfo(request);
            String idNot = (String) request.getSession().getAttribute(AWDevolucion.MODIFY_NOTIFICATION);
            if(idNot != null){
               session.setAttribute(AWDevolucion.CHECK_APODERADO + "E", accion); 
            } else{
               session.setAttribute(AWDevolucion.CHECK_APODERADO, request.getParameter(AWDevolucion.CHECK_APODERADO));
            }
            return null;
            
        } else if (accion.equals(AWDevolucion.NO_RECURSOS_NOT) || accion.equals(AWDevolucion.RECURSOS_NOT)) {
            String secuencia = (String) request.getSession().getAttribute(SECUENCIA);
            secuencia = request.getParameter(SECUENCIA);
            if(secuencia != null){
                session.setAttribute(AWDevolucion.CHECK_RECURSOS_NOT + secuencia,(accion.equals(RECURSOS_NOT)?CHECKED_SI:CHECKED_NO));
            }
            return null;
            
        } else if (accion.equals(AWDevolucion.REPOSICION_CONFIRMAR)) {
            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.REPOSICION_ACEPTAR, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);
            return evn;
            
        } else if(accion.equals(AWDevolucion.REGRESAR_TURNO)) {
            return regresarTurno(request);
        } else if(accion.equals(AWDevolucion.EDITAR_RECURSO_NOTA_DEVOLUTIVA)){
            request.getSession().setAttribute(CDevoluciones.EDITAR_RECURSO,"TRUE");
            return null;
        } else if(accion.equals(AWDevolucion.GUARDAR_RECURSO_NOTA_DEVOLUTIVA)){
            return guardarRecursoNotaEdited(request);
        } else if(accion.equals(AWDevolucion.ELIMINAR_RECURSO_NOTA_DEVOLUTIVA)){
            return eliminarRecursoNota(request);
        } else if (accion.equals(AWDevolucion.REPOSICION_NEGAR)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.AVANZAR, turno, fase, estacion, EvnDevolucion.NEGAR, usuario);

        } else if (accion.equals(AWDevolucion.APELACION_CONFIRMAR)) {
            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.APELACION_ACEPTAR, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);
            return evn;

        } else if (accion.equals(AWDevolucion.APELACION_NEGAR)) {
            return new EvnDevolucion(usuarioAuriga, EvnDevolucion.AVANZAR, turno, fase, estacion, EvnDevolucion.NEGAR, usuario);

        } else if (accion.equals(AWDevolucion.IMPRIMIR_BORRADOR)) {
            String texto = "";
            texto = request.getParameter(AWDevolucion.RESOLUCION);
            if (texto == null || texto.length() <= 0) {
                throw new ResolucionInvalidaException("La resolucion que desea agregar es invalida");
            }
            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.IMPRIMIR_BORRADOR, turno, fase, estacion, EvnDevolucion.DEFAULT, usuario);
            evn.setResolucion(texto);
            evn.setUID(session.getId());

            session.setAttribute(AWDevolucion.RESOLUCION, request.getParameter(AWDevolucion.RESOLUCION));
            return evn;

        } //MODIFICAR VALOR DE LA LIQUIDACION DE DEVOLUCION
        else if (accion.equals(AWDevolucion.MODIFICAR_VALOR_DEVOLUCION)) {
            //Obtener los valores de la liquidación.
            String valorDerechos = request.getParameter(AWAdministracionHermod.VALOR_DERECHOS);
            String valorImpuestos = request.getParameter(AWAdministracionHermod.VALOR_IMPUESTOS);
            String valorCertificados = request.getParameter(AWAdministracionHermod.VALOR_CERTIFICADOS);

            double derechos = 0d;
            double impuestos = 0d;
            double certificados = 0d;
            boolean crear = false;

            //Se realizan las respectivas validaciones.
            //Ingresar al menos un criterio de devolución (Registro, Certificados, Impuestos)
            if ((valorDerechos == null || valorDerechos.length() <= 0)
                    && (valorImpuestos == null || valorImpuestos.length() <= 0)
                    && (valorCertificados == null || valorCertificados.length() <= 0)) {
                throw new AccionInvalidaException("Debe ingresar al menos un valor para realizar la devolución");
            }

            //Verificar que los valores ingresados sean numericos
            //Derechos de Registro:
            if (valorDerechos != null && valorDerechos.length() > 0) {
                try {
                    derechos = Double.parseDouble(valorDerechos);
                } catch (NumberFormatException e) {
                    throw new AccionInvalidaException("El valor de devolución de derechos de registro no es numérico");
                }
            }

            //Verificar que los valores ingresados sean numéricos
            //Impuestos:
            if (valorImpuestos != null && valorImpuestos.length() > 0) {
                try {
                    impuestos = Double.parseDouble(valorImpuestos);
                } catch (NumberFormatException e) {
                    throw new AccionInvalidaException("El valor de devolución de impuestos no es numérico");
                }
            }

            //Verificar que los valores ingresados sean numéricos
            //Certificados:
            if (valorCertificados != null && valorCertificados.length() > 0) {
                try {
                    certificados = Double.parseDouble(valorCertificados);
                } catch (NumberFormatException e) {
                    throw new AccionInvalidaException("El valor de devolución de certificados no es numérico");
                }
            }

            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.MODIFICAR_VALOR_DEVOLUCION, turno, fase, estacion, EvnDevolucion.CONFIRMAR, usuario);
            evn.setDevolucionCertificados(certificados);
            evn.setDevolucionDerechos(derechos);
            evn.setDevolucionImpuestos(impuestos);
            return evn;

        } //Agregar Recursos al turno de Devolución
        else if (accion.equals(AWDevolucion.AGREGAR_RECURSO)) {

            preservarInfo(request);

            String textoTipo = "";

            //Si no se agrega el tipo de recurso se genera una excepción.
            textoTipo = request.getParameter(AWDevolucion.TIPO_RECURSO).trim();
            textoTipo = textoTipo.toUpperCase();
            if (textoTipo == null || textoTipo.length() <= 0) {
                throw new ResolucionInvalidaException("Debe agregar un tipo de recurso");
            }

            String usuarioRecurso = "";
            usuarioRecurso = request.getParameter(AWDevolucion.USUARIO_RECURSO).trim();
            usuarioRecurso = usuarioRecurso.toUpperCase();
            //Si no se agrega el usuario del recurso se genera una excepción. 		
            if (usuarioRecurso == null || usuarioRecurso.length() <= 0) {
                throw new ResolucionInvalidaException("Debe agregar el usuario que agrega el recurso");
            }

            String descripcionRecurso = "";
            //Si no se agrega el tipo de recurso se genera una excepción. 	
            descripcionRecurso = request.getParameter(AWDevolucion.DESCRIPCION_RECURSO).trim();
            if (descripcionRecurso == null || descripcionRecurso.length() <= 0) {
                throw new ResolucionInvalidaException("Debe agregar la observacion del recurso");
            }

            boolean isRecurso = false;
            isRecurso = request.getParameter(CHECK_RECURSOS) != null && request.getParameter(CHECK_RECURSOS).equals(CHECKED_SI);

            String fechaRecurso = request.getParameter(AWDevolucion.FECHA_RECURSO);
            Date dateRecurso = null;
            if (isRecurso) {
                if ((fechaRecurso == null)
                        || (fechaRecurso.equals(""))) {
                    throw new ResolucionInvalidaException("Debe ingresar la fecha del recurso");

                } else {
                    try {
                        dateRecurso = DateFormatUtil.parse(fechaRecurso);
                    } catch (Exception e) {
                        throw new ResolucionInvalidaException("La fecha del recurso es inválida" + e.getMessage());
                    }
                }
            }
            /*
	        String fechaEjecutoria = request.getParameter(AWDevolucion.FECHA_EJECUTORIA);
			Date dateEjecutoria = null;
	        if(( fechaEjecutoria == null) 
	        		|| (fechaEjecutoria.equals(""))) {
	        	throw new ResolucionInvalidaException( "Debe ingresar la fecha ejecutoria" );

	     	} else {
	   			SimpleDateFormat df = new SimpleDateFormat();
	     		df.applyPattern("dd/MM/yyyy");
	     		String send1 = null;

	     		try {
	     			dateEjecutoria = df.parse(fechaEjecutoria);
	     			df.applyPattern("yyyy-MM-dd");
	     		}
	     		catch (Exception e) {
	     			throw new ResolucionInvalidaException("La fecha ejecutoria es inválida" + e.getMessage() );
	     		}
	     	}
             */
            //Asociar el turno correspondiente.

            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.AGREGAR_RECURSO, turno, fase, estacion, EvnDevolucion.DEFAULT, usuario);
            evn.setDescripcionRecurso(descripcionRecurso);
            evn.setUsuarioRecurso(usuarioRecurso);
            evn.setTipoRecurso(textoTipo);
            evn.setFechaRecurso(dateRecurso);

            return evn;

        } else if (accion.equals(CAMBIAR_TURNO_DEVOLUCION)) {
            return validarTurnoAnt(request, true);
        } else if (accion.equals(ELIMINAR_CONSIGNACION_CHEQUE)) {
            eliminarConsignacionCheque(request);
            return null;
        } else if (accion.equals(AGREGAR_CONSIGNACION_CHEQUE)) {
            return validarConsignacionCheque(request);
        } else if (accion.equals(REANOTAR_TURNO)){
            preservarInfo(request);
            Turno turn = (Turno) session.getAttribute(WebKeys.TURNO);
            session.setAttribute(AWTrasladoTurno.IS_NOTIFY, "TRUE");
            session.setAttribute(AWTrasladoTurno.TURNO_REANOTAR, turn);
            return null;
        } else {
            throw new AccionInvalidaException("La acción " + accion + " no es válida.");
        }
    }
    
    private Evento editarRecursoNota(HttpServletRequest request) throws AccionWebException{
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
       
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        String posicion = (String) request.getParameter(WebKeys.POSICION);
        int pos = Integer.parseInt(posicion);
        String tipo = request.getParameter("TIPO_"+posicion);
        if(tipo == null || tipo.equals(WebKeys.SIN_SELECCIONAR)){
            exception.addError("Debe especificar un tipo de Recurso");
        }
        String fecha = request.getParameter("FECHA_"+posicion);
        if(fecha == null || fecha.isEmpty()){
            exception.addError("Debe ingresar una fecha valida");
        }
        String user = request.getParameter("USUARIO_"+posicion);
        if(user == null || user.isEmpty()){
            exception.addError("Debe especificar un Usuario SIR");
        }
        String descripcion = request.getParameter("DESCRIPCION_"+posicion);
        if(descripcion == null || descripcion.isEmpty()){
            exception.addError("Debe especificar la descripción del Recurso");
        }
        
        if(exception.getErrores().size() > 0){
            throw exception;
        }
        
        Recurso recurso = new Recurso();
        pos++;
        posicion = String.valueOf(pos);
        recurso.setIdRecurso(posicion);
        recurso.setTitulo(tipo);
        Date date = null;
        try{
            date = DateFormatUtil.parse(fecha);
        } catch(ParseException ps){
            System.out.println("ERROR: " + ps.getMessage());
        }
        recurso.setFecha(date);
        recurso.setTextoUsuario(user);
        recurso.setTextoRecurso(descripcion);
        
        Turno newTurno = null;
        try{
            HermodService hs = HermodService.getInstance();
            hs.updateRecurso(recurso, turno.getIdWorkflow());
            newTurno = hs.getTurnobyWF(turno.getIdWorkflow());
        } catch(HermodException he){
            System.out.println("ERROR: " + he.getMessage());
        }
        
        if(newTurno != null){
            request.getSession().setAttribute(WebKeys.TURNO, newTurno);
        }
        return null;
    }
    
    private Evento agregarNotificacion(HttpServletRequest request) throws AccionWebException {
      HttpSession session = request.getSession();
      org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
      ValidacionParametrosException exception = new ValidacionParametrosException();
      Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
      Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
      this.preservarInfo(request);
      boolean hasNote = false;
      int lastId = this.getLastIdHistoria(request);
      List notas = turno.getNotas();
      String idNot = (String) request.getSession().getAttribute(AWDevolucion.MODIFY_NOTIFICATION);
      if(idNot != null){
          exception.addError("Debe guardar la edición de la notificación para poder agregar otra");
          throw exception;
      }
      if (notas == null) {
         throw new AccionInvalidaException("Debe ingresar una nota informativa de tipo NOTIFICAR NOTA DEVOLUTIVA");
      } else {
         Iterator itNot = notas.iterator();

         while(itNot.hasNext()) {
            Nota nota = (Nota)itNot.next();
            if (nota.getTiponota().getIdTipoNota().equals("23185") && nota.getIdTurnoHistoria().equals(String.valueOf(lastId))) {
               hasNote = true;
            }
         }

         if (!hasNote) {
            exception.addError("Debe ingresar una nota informativa de tipo NOTIFICAR NOTA DEVOLUTIVA");
            throw exception;
         } else {
            String destino = request.getParameter(DESTINO_NOTIFICACION);
            if (destino == null || destino.equals(WebKeys.SIN_SELECCIONAR)) {
               exception.addError("Debe especificar un destinatario para la notificación");
            }

            String fechaNot = (String) request.getSession().getAttribute(FECHA_NOTIFICACION);
            if (fechaNot == null || fechaNot.isEmpty()) {
               exception.addError("Debe especificar la fecha de notificación");
            }

            String tipoNot = request.getParameter(TIPO_NOTIFICACION);
            if (tipoNot == null || tipoNot.equals(WebKeys.SIN_SELECCIONAR)) {
               exception.addError("Debe especificar un tipo de notificación");
            }

            String isNotificarUsuario = (String)session.getAttribute(IS_NOTIFICAR_USUARIO);
            boolean isUsuario = false;
            boolean isOficina = false;
            if (isNotificarUsuario != null && !isNotificarUsuario.isEmpty()) {
               if (isNotificarUsuario.equals(CDevoluciones.NOT_USUARIO)) {
                  isUsuario = true;
                  isOficina = false;
               } else if (isNotificarUsuario.equals(CDevoluciones.NOT_OFICINA)) {
                  isUsuario = false;
                  isOficina = true;
               } else {
                  isUsuario = false;
                  isOficina = false;
               }
            }

            String isApoderado = request.getParameter(CHECK_APODERADO);
            int apoderado = 0;
            String nombres = request.getParameter(NOMBRES_NOTIFICACION);
            String apellidos = request.getParameter(APELLIDOS_NOTIFICACION);
            String tDocumento = request.getParameter(TIPO_DOCUMENTO);
            String numeroDoc = request.getParameter(NUMERO_DOCUMENTO);
            if (isUsuario) {
               if (isApoderado != null && isApoderado.equals(APODERADO_SI)) {
                  apoderado = 1;
               }
               if(isApoderado == null){
                   exception.addError("Debe especificar si es apoderado o no.");
               }

               if (nombres == null || nombres.isEmpty()) {
                  exception.addError("Debe especificar el nombre del notificado(a)");
               }

               if (apellidos == null || apellidos.isEmpty()) {
                  exception.addError("Debe especificar los apellido del notificado(a)");
               }

               if (tDocumento == null || tDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
                  exception.addError("Debe especificar el tipo de documento del notificado(a)");
               }

               if (numeroDoc == null || numeroDoc.isEmpty()) {
                  exception.addError("Debe especificar el número de documento del notificado(a)");
               }
            }

            String direccion = request.getParameter(DIRECCION);
            if (direccion == null || direccion.isEmpty()) {
               exception.addError("Debe especificar una dirección válida");
            }

            String telefono = request.getParameter(TELEFONO);
            if (telefono == null || telefono.isEmpty()) {
               exception.addError("Debe especificar un teléfono válida");
            }

            String isNotificarCorreo = (String)session.getAttribute(IS_NOTIFICAR_CORREO);
            boolean isCorreo = false;
            if (isNotificarCorreo != null && !isNotificarCorreo.isEmpty() && isNotificarCorreo.equals(CDevoluciones.CORREO_ELECTRONICO)) {
               isCorreo = true;
            }

            String correo = request.getParameter(CORREO);
            if (isCorreo && (correo == null || correo.isEmpty())) {
               exception.addError("Debe especificar un correo electrónico válido");
            }

            if (exception.getErrores().size() > 0) {
               throw exception;
            } else {
               String dateFormat = "dd/MM/yyyy";
               SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
               Date fechaNotificacion = null;

               try {
                  fechaNotificacion = (Date)formatter.parseObject(fechaNot);
               } catch (ParseException var34) {
                  System.out.println("ERROR" + var34.getMessage());
               }

               NotificacionNota notify;
               if (isUsuario) {
                  notify = new NotificacionNota(destino, tipoNot, apoderado, nombres, apellidos, tDocumento, numeroDoc, fechaNotificacion, turno.getIdWorkflow());
               } else {
                  SolicitudRegistro solicitud = (SolicitudRegistro)turno.getSolicitud();
                  String idOficinaOrigen = solicitud.getDocumento().getOficinaOrigen().getIdOficinaOrigen();
                  notify = new NotificacionNota(destino, tipoNot, turno.getIdWorkflow(), fechaNotificacion, idOficinaOrigen);
               }

               if (direccion != null && !direccion.isEmpty()) {
                  notify.setDireccion(direccion);
               }

               if (telefono != null && !telefono.isEmpty()) {
                  notify.setTelefono(telefono);
               }

               if (correo != null && !correo.isEmpty()) {
                  notify.setCorreo(correo);
               }

               try {
                  HermodService hs = HermodService.getInstance();
                  hs.notificarNotaDevolutiva(notify);
               } catch (HermodException var33) {
                  System.out.println("ERROR: " + var33.getMessage());
               }
               
               removeFromSession(request);

               return null;
            }
         }
      }
    }
    
    private Evento crudNotification(HttpServletRequest request, String accion) throws AccionWebException{
        ValidacionParametrosException exception = new ValidacionParametrosException();
        String idNot = (String) request.getSession().getAttribute(ID_TO_MODIFY);
        idNot = request.getParameter(ID_TO_MODIFY);
        if(idNot == null){
            exception.addError("El identificador del la notificación no es válida.");
            throw exception;
        }
        try{
           HermodService hs = HermodService.getInstance();
           request.getSession().removeAttribute(AWDevolucion.MODIFY_NOTIFICATION);
           if(accion.equals(GUARDAR_NOTIFICACION)){
               
            String destino = request.getParameter(DESTINO_NOTIFICACION + "E");
            if (destino == null || destino.equals(WebKeys.SIN_SELECCIONAR)) {
               exception.addError("Debe especificar un destinatario para la notificación");
            }

            String fechaNot = (String) request.getSession().getAttribute(FECHA_NOTIFICACION + "E");
            if (fechaNot == null || fechaNot.isEmpty()) {
               exception.addError("Debe especificar la fecha de notificación");
            }

            String tipoNot = request.getParameter(TIPO_NOTIFICACION + "E");
            if (tipoNot == null || tipoNot.equals(WebKeys.SIN_SELECCIONAR)) {
               exception.addError("Debe especificar un tipo de notificación");
            }

            boolean isUsuario = false;
            boolean isOficina = false;
            if (destino != null && !destino.isEmpty()) {
               if (destino.equals(CDevoluciones.USUARIO)) {
                  isUsuario = true;
                  isOficina = false;
               } else if (destino.equals(CDevoluciones.OFICINA_ORIGEN)) {
                  isUsuario = false;
                  isOficina = true;
               } else {
                  isUsuario = false;
                  isOficina = false;
               }
            }

            String isApoderado = request.getParameter(CHECK_APODERADO + "E");
            int apoderado = 0;
            String nombres = request.getParameter(NOMBRES_NOTIFICACION + "E");
            String apellidos = request.getParameter(APELLIDOS_NOTIFICACION + "E");
            String tDocumento = request.getParameter(TIPO_DOCUMENTO + "E");
            String numeroDoc = request.getParameter(NUMERO_DOCUMENTO + "E");
            if (isUsuario) {
               if (isApoderado != null && isApoderado.equals(APODERADO_SI)) {
                  apoderado = 1;
               }

               if (nombres == null || nombres.isEmpty()) {
                  exception.addError("Debe especificar el nombre del notificado(a)");
               }

               if (apellidos == null || apellidos.isEmpty()) {
                  exception.addError("Debe especificar los apellido del notificado(a)");
               }

               if (tDocumento == null || tDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
                  exception.addError("Debe especificar el tipo de documento del notificado(a)");
               }

               if (numeroDoc == null || numeroDoc.isEmpty()) {
                  exception.addError("Debe especificar el número de documento del notificado(a)");
               }
            }

            String direccion = request.getParameter(DIRECCION + "E");
            if (direccion == null || direccion.isEmpty()) {
               exception.addError("Debe especificar una dirección válida");
            }

            String telefono = request.getParameter(TELEFONO + "E");
            if (telefono == null || telefono.isEmpty()) {
               exception.addError("Debe especificar un teléfono válida");
            }

            boolean isCorreo = false;
            if (tipoNot != null && tipoNot.equals(CDevoluciones.CORREO_ELECTRONICO)) {
               isCorreo = true;
            }

            String correo = request.getParameter(CORREO + "E");
            if (isCorreo && (correo == null || correo.isEmpty())) {
               exception.addError("Debe especificar un correo electrónico válido");
            }

            if (exception.getErrores().size() > 0) {
               throw exception;
            }
            
            String dateFormat = "dd/MM/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date fechaNotificacion = null;

            try {
               fechaNotificacion = (Date)formatter.parseObject(fechaNot);
            } catch (ParseException var34) {
               System.out.println("ERROR" + var34.getMessage());
            }

            NotificacionNota notify = new NotificacionNota();
            if (isUsuario) {
               notify.setDestino(destino);
               notify.setTipo(tipoNot);
               notify.setApoderado(apoderado);
               notify.setNombres(nombres);
               notify.setApellidos(apellidos);
               notify.setTipoDocumento(tDocumento);
               notify.setDocumento(numeroDoc);
               notify.setFechaNotificacion(fechaNotificacion);
            } else {
               notify.setDestino(destino);
               notify.setTipo(tipoNot);
               notify.setFechaNotificacion(fechaNotificacion);
            }

            if (direccion != null && !direccion.isEmpty()) {
               notify.setDireccion(direccion);
            }

            if (telefono != null && !telefono.isEmpty()) {
               notify.setTelefono(telefono);
            }

            if (correo != null && !correo.isEmpty()) {
               notify.setCorreo(correo);
            }
            
            notify.setIdNotificacion(idNot);
            
            hs.executeDMLFromSQL(CQueries.updateNotificacionNota(notify));
               
           } else if(accion.equals(EDITAR_NOTIFICACION)){
              request.getSession().setAttribute(MODIFY_NOTIFICATION, idNot);
           } else if(accion.equals(ELIMINAR_NOTIFICACION)){
              NotificacionNota notify = new NotificacionNota();
              notify.setIdNotificacion(idNot);
              hs.executeDMLFromSQL(CQueries.deleteNotificacionNota(notify));
           } else{
                exception.addError("la acción no es válida.");
                throw exception;
           }
       } catch(HermodException he){
           System.out.println("ERROR: " + he);
       }
        
        return null;
    }
    
    private Evento guardarRecursoNotaEdited(HttpServletRequest request) throws AccionWebException{
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
       
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        String posicion = (String) request.getParameter(WebKeys.POSICION);
        int pos = Integer.parseInt(posicion);
        
        String tipo = request.getParameter("TIPO_"+posicion);
        String exTip = (String) request.getSession().getAttribute("TIPO_"+posicion);
        if(exTip != null && exTip.equals("NO_PROCEDE_RECURSO")){
            tipo = exTip;
        } else{
            if(tipo == null || tipo.equals(WebKeys.SIN_SELECCIONAR)){
            exception.addError("Debe especificar un tipo de Recurso");
            }
        }
        
        String fecha = request.getParameter("FECHA_"+posicion);
        if(fecha == null || fecha.isEmpty()){
            exception.addError("Debe ingresar una fecha valida");
        }
        String user = request.getParameter("USUARIO_"+posicion);
        if(user == null || user.isEmpty()){
            exception.addError("Debe especificar un Usuario SIR");
        }
        String descripcion = request.getParameter("DESCRIPCION_"+posicion);
        if(descripcion == null || descripcion.isEmpty()){
            exception.addError("Debe especificar la descripción del Recurso");
        }
        
        if(exception.getErrores().size() > 0){
            throw exception;
        }
        
        Recurso recurso = new Recurso();
        pos++;
        posicion = String.valueOf(pos);
        recurso.setIdRecurso(posicion);
        recurso.setTitulo(tipo);
        Date date = null;
        try{
            date = DateFormatUtil.parse(fecha);
        } catch(ParseException ps){
            System.out.println("ERROR: " + ps.getMessage());
        }
        recurso.setFecha(date);
        recurso.setTextoUsuario(user);
        recurso.setTextoRecurso(descripcion);
        
        Turno newTurno = null;
        try{
            HermodService hs = HermodService.getInstance();
            hs.updateRecurso(recurso, turno.getIdWorkflow());
            newTurno = hs.getTurnobyWF(turno.getIdWorkflow());
        } catch(HermodException he){
            System.out.println("ERROR: " + he.getMessage());
        }
        
        if(newTurno != null){
            request.getSession().setAttribute(WebKeys.TURNO, newTurno);
        }
        
        request.getSession().setAttribute(CDevoluciones.EDITAR_RECURSO,"FALSE");
        return null;
    }
    
    private Evento eliminarRecursoNota(HttpServletRequest request) throws AccionWebException{
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
       
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        String posicion = (String) request.getParameter(WebKeys.POSICION);
        int pos = Integer.parseInt(posicion);
        pos++;
        posicion = String.valueOf(pos);
        
        
        Turno newTurno = null;
        try{
            HermodService hs = HermodService.getInstance();
            hs.deleteRecurso(posicion, turno.getIdWorkflow());
            newTurno = hs.getTurnobyWF(turno.getIdWorkflow());
        } catch(HermodException he){
            System.out.println("ERROR: " + he.getMessage());
        }
        
        if(newTurno != null){
            request.getSession().setAttribute(WebKeys.TURNO, newTurno);
        }
        return null;
    }
    
    private Evento regresarTurno(HttpServletRequest request) throws AccionWebException{       
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION); 
        try{
            HermodService hs = HermodService.getInstance();
            hs.setStateNotaNotificada(CDevoluciones.START_NOTA_NOTIFICADA, turno.getIdWorkflow());
        } catch(HermodException th){
          System.out.println("No ha sido posible regresar el turno de estado.");   
        }
        return null;
    }
    
    private Evento devolverCalificacion(HttpServletRequest request) throws AccionWebException{
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
       
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnDevolucion ev = new EvnDevolucion(usuarioAuriga, EvnDevolucion.DEVOLVER_CALIFICACION);
        ev.setTurno(turno);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);
        ev.setUsuarioSir(usuarioSIR); 
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        ev.setInfoUsuario(infoUsuario);
        ev.setRespuestaWF(CRespuesta.REC_INTERPUESTO);
        return ev;
    }
    
    private Evento agregarRecursosNota(HttpServletRequest request) throws AccionWebException{
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        int lastId = this.getLastIdHistoria(request);
        List listaTodosRecursos = new ArrayList();

        List listaHistorials = turno.getHistorials();
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
        
        String checkRecursos = (String) request.getSession().getAttribute(CHECK_RECURSOS_NOT);
        boolean validarRec = false;
        if(checkRecursos != null && checkRecursos.equals(CHECKED_SI)){
            validarRec = true;
        } else if(!listaTodosRecursos.isEmpty()){
            validarRec = true;
        }
        
        if(listaTodosRecursos.isEmpty()){
            exception.addError("Debe agregar un recurso");
            throw exception;
        }
        
        if(validarRec){
        String fechaEjecutoria = null;
        fechaEjecutoria = (String) request.getSession().getAttribute(AWDevolucion.FECHA_EJECUTORIA);
        if(fechaEjecutoria == null || fechaEjecutoria.isEmpty()){
           fechaEjecutoria = request.getParameter(AWDevolucion.FECHA_EJECUTORIA);
           request.getSession().setAttribute(AWDevolucion.FECHA_EJECUTORIA, fechaEjecutoria);
        }   
            Date dateEjecutoria = null;
            List resolutions = null;
            try {
            HermodService hs = HermodService.getInstance();
            Turno turn = hs.getTurnobyWF(turno.getIdWorkflow());
            TurnoPk oid = new TurnoPk();
            oid.anio = turn.getAnio();
            oid.idCirculo = turn.getIdCirculo();
            oid.idProceso = turn.getIdProceso();
            oid.idTurno = turn.getIdTurno();
            resolutions = hs.getOficiosTurno(oid);
            } catch(HermodException he){
              System.out.println("ERROR: " + he.getMessage());  
            }
            if (resolutions == null || resolutions.size() == 0) {
                exception.addError("No hay resoluciones en el turno");
                throw exception;
            }
            Oficio resolucion = (Oficio) resolutions.get(resolutions.size() - 1);
            if (resolucion.getFechaFirma() == null) {

                if ((fechaEjecutoria == null)
                        || (fechaEjecutoria.equals(""))) {
                    exception.addError("Debe ingresar la fecha ejecutoria");
                    throw exception;
                } else {

                    try {
                        dateEjecutoria = DateFormatUtil.parse(fechaEjecutoria);
                    } catch (Exception e) {
                        exception.addError("La fecha ejecutoria es inválida" + e.getMessage());
                    }
                }
                if (dateEjecutoria.getTime() <= resolucion.getFechaCreacion().getTime()) {
                    exception.addError("La fecha ejecutoría debe ser mayor a la fecha de la última resolución");
                }
            }
            
        if(exception.getErrores().size() > 0){
            throw exception;
        }
       
        try{
        HermodService hs = HermodService.getInstance();
        TurnoPk oid = new TurnoPk();
        oid.anio = turno.getAnio();
        oid.idCirculo = turno.getIdCirculo();
        oid.idProceso = turno.getIdProceso();
        oid.idTurno = turno.getIdTurno();

        List oficios = hs.getOficiosTurno(oid);
        Oficio resolution = (Oficio) oficios.get(oficios.size() - 1);
        if (fechaEjecutoria == null && resolucion.getFechaFirma() == null) {
            exception.addError("No es posible avanzar el turno. Se requiere de la fecha de ejecutoria para avanzar desde esta fase");
            throw exception;
        }
        hs.agregarFechaFirmaOficio(new OficioPk(resolucion.getIdOficio()), dateEjecutoria);
        
        } catch(HermodException he){
            System.out.println("ERROR: " + he.getMessage());
        }
            
        boolean hasNote = true;
        boolean hasNoteRec = false;
        boolean hasNoteNoR = false;
        boolean noProcede = false;
        boolean aintNote = false;
        boolean recApel = false;
        boolean proced = false;
        
        
        List notasInf = null;
        notasInf = turno.getNotas();
        
        Iterator itRec = listaTodosRecursos.iterator();
        int sequence = 0;
        String recursoFavorable = (String) request.getSession().getAttribute(AWDevolucion.CHECK_RECURSO_FAVORABLE);
        while(itRec.hasNext()){
            String procede = (String) request.getSession().getAttribute(CHECK_RECURSOS_NOT + String.valueOf(sequence));
            sequence++;
            Recurso rec = (Recurso) itRec.next();
            if(rec.getTitulo().equals("RECURSO DE REPOSICION DE SUBSIDIO DE APELACION")){
                hasNote = false;
            }
            
            if(rec.getTitulo().equals("RECURSO DE REPOSICION")){
                recApel = true;
            }
                        
            if(rec.getTitulo().equals("NO PROCEDE RECURSO")){
                noProcede = true;
            }
            
            
            if(procede == null && recursoFavorable == null && hasNote && !noProcede){
                exception.addError("Debe indicar si el recurso N° " + String.valueOf(sequence) + " procede o no.");
            }
            
            if(procede != null && procede.equals(CHECKED_SI)){
                proced = true;
            }
            
        }
        
        if(!exception.getErrores().isEmpty()){
            throw exception;
        }
        

  
        Iterator itNotJ = notasInf.iterator();
        while(itNotJ.hasNext()){
            Nota nota = (Nota) itNotJ.next();
            if(nota.getTiponota().getIdTipoNota().equals(CNota.RESPUESTA_SEGUNDA_INSTANCIA) && nota.getIdTurnoHistoria().equals(String.valueOf(lastId))){ 
                hasNote = true;
                aintNote = true;
            }
            if(nota.getTiponota().getIdTipoNota().equals(CNota.RECURSO_DE_REPOSICION) && nota.getIdTurnoHistoria().equals(String.valueOf(lastId))){ 
                hasNoteRec = true;
            }
            if(nota.getTiponota().getIdTipoNota().equals(CNota.NO_PROCEDE_RECURSO) && nota.getIdTurnoHistoria().equals(String.valueOf(lastId))){ 
                hasNoteNoR = true;
            }
        }

        if(!hasNote){
            exception.addError("Debe ingresar una nota informativa de tipo RECURSO DE REPOSICION CON SUBSIDIO DE APELACION");
        } else if(recApel && !hasNoteRec){
            exception.addError("Debe ingresar una nota informativa de tipo RECURSO DE REPOSICION");
        } else if(noProcede && !hasNoteNoR){
            exception.addError("Debe ingresar una nota informativa de tipo NO PROCEDE RECURSO");
        }
        
        
        
        if(exception.getErrores().size() > 0){
            throw exception;
        }
        
        boolean recursoF = false;
        if(recursoFavorable != null && recursoFavorable.equals(CHECKED_SI)){
            recursoF = true;
        } else{
            if(recursoFavorable == null && aintNote && !recApel){
          exception.addError("Debe indicar si el recurso es favorable o no para continuar.");  
          throw exception;
        }
        }
        
        try{
        HermodService hs = HermodService.getInstance();
        Turno turn = hs.getTurnobyWF(turno.getIdWorkflow());
        String grandError = CError.validarPrincipioPrioridadRecNot(turn);
        if(grandError != null){
            exception.addError(grandError);
            throw exception;
        }
        } catch(HermodException fe){
            System.out.println("ERROR: " + fe);
        }
            
        if(recursoF || proced){
            return devolverCalificacion(request);
        }
        
        }
        
        try{
            HermodService hs = HermodService.getInstance();
            Turno turn = hs.getTurnobyWF(turno.getIdWorkflow());
            String grandError = CError.validarPrincipioPrioridadRecNot(turn);
            if(grandError != null){
                exception.addError(grandError);
                throw exception;
            }
            } catch(HermodException fe){
                System.out.println("ERROR: " + fe);
        }
       
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnDevolucion ev = new EvnDevolucion(usuarioAuriga, EvnDevolucion.AVANZAR_NOTA_DEV_NOTIFICADA);
        ev.setTurno(turno);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);
        ev.setUsuarioSir(usuarioSIR); 
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        ev.setInfoUsuario(infoUsuario);
        ev.setRespuestaWF(String.valueOf(CRespuesta.FRACASO));
        
        gov.sir.core.util.TLSHttpClientComponent notifyREL = new gov.sir.core.util.TLSHttpClientComponent();
                try{
            HermodService hs = HermodService.getInstance();
            boolean turnoREL = (hs.isTurnoREL(turno.getIdWorkflow()));
            if(turnoREL){
                notifyREL.setFase(CRespuesta.DEVUELTO_FINALIZAR);
                notifyREL.sendMsgToREL(turno.getIdWorkflow());
            }
        } catch(Exception e){
            Logger.getLogger(ANCalificacion.class.getName()).log(Level.SEVERE, null, e);
             exception.addError("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
             throw exception;
        } catch(Throwable th){
             exception.addError("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
             throw exception;
        }
                
        return ev;
    }
    
    private Evento agregarRecursosNotaDevolutiva(HttpServletRequest request) throws AccionWebException {
            preservarInfo(request);
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
            Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
            ValidacionParametrosException exception = new ValidacionParametrosException();
            
            boolean noProcede = false;
            noProcede = request.getParameter(CHECK_RECURSOS_NOT) != null && request.getParameter(CHECK_RECURSOS_NOT).equals(CHECKED_NO);
            String textoTipo = "";
            
            //Si no se agrega el tipo de recurso se genera una excepción.
            textoTipo = request.getParameter(AWDevolucion.TIPO_RECURSO);
            if (textoTipo == null || textoTipo.equals(WebKeys.SIN_SELECCIONAR)) {
                throw new ResolucionInvalidaException("Debe agregar un tipo de recurso");
            }
            
            if(noProcede){
                if(!textoTipo.equals(CDevoluciones.NO_PROCEDE_RECURSO)){
                    exception.addError("Debe ingresar un recurso de tipo NO PROCEDE RECURSO");
                    throw exception;
                }
            }

            String usuarioRecurso = "";
            usuarioRecurso = request.getParameter(AWDevolucion.USUARIO_RECURSO).trim();
            usuarioRecurso = usuarioRecurso.toUpperCase();
            //Si no se agrega el usuario del recurso se genera una excepción. 		
            if (usuarioRecurso == null || usuarioRecurso.length() <= 0) {
                throw new ResolucionInvalidaException("Debe agregar el usuario que agrega el recurso");
            }

            String descripcionRecurso = "";
            //Si no se agrega el tipo de recurso se genera una excepción. 	
            descripcionRecurso = request.getParameter(AWDevolucion.DESCRIPCION_RECURSO).trim();
            if (descripcionRecurso == null || descripcionRecurso.length() <= 0) {
                throw new ResolucionInvalidaException("Debe agregar la observacion del recurso");
            }

            boolean isRecurso = false;
            isRecurso = request.getParameter(CHECK_RECURSOS_NOT) != null && request.getParameter(CHECK_RECURSOS_NOT).equals(CHECKED_SI);

            String fechaRecurso = request.getParameter(AWDevolucion.FECHA_RECURSO);
            Date dateRecurso = null;
                if ((fechaRecurso == null)
                        || (fechaRecurso.equals(""))) {
                    throw new ResolucionInvalidaException("Debe ingresar la fecha del recurso");

                } else {
                    try {
                        dateRecurso = DateFormatUtil.parse(fechaRecurso);
                    } catch (Exception e) {
                        throw new ResolucionInvalidaException("La fecha del recurso es inválida" + e.getMessage());
                    }
                }
            
            request.getSession().removeAttribute(TIPO_RECURSO);
            request.getSession().removeAttribute(USUARIO_RECURSO);
            request.getSession().removeAttribute(DESCRIPCION_RECURSO);
            request.getSession().removeAttribute(FECHA_RECURSO);
                
            
            /*
	        String fechaEjecutoria = request.getParameter(AWDevolucion.FECHA_EJECUTORIA);
			Date dateEjecutoria = null;
	        if(( fechaEjecutoria == null) 
	        		|| (fechaEjecutoria.equals(""))) {
	        	throw new ResolucionInvalidaException( "Debe ingresar la fecha ejecutoria" );

	     	} else {
	   			SimpleDateFormat df = new SimpleDateFormat();
	     		df.applyPattern("dd/MM/yyyy");
	     		String send1 = null;

	     		try {
	     			dateEjecutoria = df.parse(fechaEjecutoria);
	     			df.applyPattern("yyyy-MM-dd");
	     		}
	     		catch (Exception e) {
	     			throw new ResolucionInvalidaException("La fecha ejecutoria es inválida" + e.getMessage() );
	     		}
	     	}
             */
            //Asociar el turno correspondiente.
            EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.AGREGAR_RECURSO_NOTA_DEVOLUTIVA);
            evn.setDescripcionRecurso(descripcionRecurso);
            evn.setUsuarioRecurso(usuarioRecurso);
            evn.setTipoRecurso(textoTipo);
            evn.setFechaRecurso(dateRecurso);
            evn.setTurno(turno);
            return evn;
    }
    
    private Evento changeStateNotaNotificada(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionParametrosException exception = new ValidacionParametrosException();
        String checked = request.getParameter(AWDevolucion.CHECK_JUZGADO);
        String currentState = (String) session.getAttribute(AWDevolucion.CURRENT_STATE_NOTA_NOTIFICADA);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        List notasInf = null;
        
       try{
        HermodService hs = HermodService.getInstance();
        
        if(currentState.equals(CDevoluciones.START_NOTA_NOTIFICADA)){
            if(checked == null || checked.equals(CHECKED_SI)){
                hs.setStateNotaNotificada(CDevoluciones.JUZGADO_NOTA_NOTIFICADA,turno.getIdWorkflow());
                boolean hasNoteJuzgado = false;
                notasInf = turno.getNotas();
                
                if(notasInf == null || notasInf.isEmpty()){
                    exception.addError("Debe ingresar una nota informativa de tipo NOTA INFORMATIVA DOCUMENTOS JUZGADO");
                } else{
                    Iterator itNotJ = notasInf.iterator();
                    while(itNotJ.hasNext()){
                        Nota nota = (Nota) itNotJ.next();
                        if(nota.getTiponota().getIdTipoNota().equals(CNota.DOCUMENTOS_JUZGADO)){
                            hasNoteJuzgado = true;
                        }
                    }

                    if(!hasNoteJuzgado){
                        exception.addError("Debe ingresar una nota informativa de tipo NOTA INFORMATIVA DOCUMENTOS JUZGADO");
                    }
                }
            } else{
                hs.setStateNotaNotificada(CDevoluciones.RECURSOS_NOTA_NOTIFICADA,turno.getIdWorkflow());   
            }
                
                if (exception.getErrores().size() > 0) {
                    throw exception;
                }
            return notaDevolutivaNotificada (request);
            
        } else if(currentState.equals(CDevoluciones.JUZGADO_NOTA_NOTIFICADA)){
            hs.setStateNotaNotificada(CDevoluciones.RECURSOS_NOTA_NOTIFICADA,turno.getIdWorkflow());   
            return notaDevolutivaNotificada (request);
            
        } else{
            exception.addError("El estado de la nota devolutiva notifica no es valida");
        }
        
        } catch (HermodException he){
                    System.out.println("No fue posible actualizar el estado de la nota devolutiva notificada");
        }
        return null;
    }
    
    private int getLastIdHistoria(HttpServletRequest request) throws AccionWebException{
        int lastId = 0;
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        
        try{
           HermodService hs = HermodService.getInstance();
           Turno turn = hs.getTurnobyWF(turno.getIdWorkflow());
           lastId = (int) turn.getLastIdHistoria();
        } catch(HermodException he){
            System.out.println("ERROR: " + he.getMessage());
        }
        return lastId;
    }
    
    private Evento notaNotificadaConfirmar(HttpServletRequest request) throws AccionWebException {
      String checked = request.getParameter(CHECK_JUZGADO);
      List notasInf = null;
      Turno turno = (Turno)this.session.getAttribute(WebKeys.TURNO);
      ValidacionParametrosException exception = new ValidacionParametrosException();
      if(checked == null){
          exception.addError("Debe especificar si interpone o no recurso.");
      }
       
         boolean hasNoteRecursos = false;
         notasInf = turno.getNotas();
         List notifications = null;
         int lastId = 0;

         try {
            HermodService hs = HermodService.getInstance();
            Turno turn = hs.getTurnobyWF(turno.getIdWorkflow());
            String grandError = CError.validarPrincipioPrioridadNotNotaNot(turn);
            if(grandError != null){
                exception.addError(grandError);
                throw exception;
            }
            notifications = hs.getNotaDevNotificada(turno.getIdWorkflow());
            List historias = turn.getHistorials();
            if (historias != null) {
               TurnoHistoria tHist = (TurnoHistoria)historias.get(historias.size() - 1);
               lastId = Integer.parseInt(tHist.getIdTurnoHistoria());
            }
         } catch (HermodException var13) {
            System.out.println("ERROR: " + var13.getMessage());
         }

         if (notifications != null && !notifications.isEmpty()) {
            Iterator itNot = notifications.iterator();

            label74:
            while(true) {
               while(true) {
                  if (!itNot.hasNext()) {
                     break label74;
                  }

                  NotificacionNota notify = (NotificacionNota)itNot.next();
                  Iterator itNotJ;
                  Nota nota;
                  if (notify != null && notify.getDestino().equals(CDevoluciones.USUARIO)) {
                     itNotJ = notasInf.iterator();

                     while(itNotJ.hasNext()) {
                        nota = (Nota)itNotJ.next();
                        if (nota.getTiponota().getIdTipoNota().equals(CNota.DOCUMENTOS_USUARIO) && nota.getIdTurnoHistoria().equals(String.valueOf(lastId))) {
                           hasNoteRecursos = true;
                        }
                     }

                     if (!hasNoteRecursos) {
                        exception.addError("Debe ingresar una nota informativa de tipo NOTA INFORMATIVA DOCUMENTOS USUARIO");
                     }
                  } else {
                     itNotJ = notasInf.iterator();

                     while(itNotJ.hasNext()) {
                        nota = (Nota)itNotJ.next();
                        if (nota.getTiponota().getIdTipoNota().equals(CNota.DOCUMENTOS_JUZGADO) && nota.getIdTurnoHistoria().equals(String.valueOf(lastId))) {
                           hasNoteRecursos = true;
                        }
                     }

                     if (!hasNoteRecursos) {
                        exception.addError("Debe ingresar una nota informativa de tipo NOTA INFORMATIVA DOCUMENTOS JUZGADO");
                     }
                  }
               }
            }
         
         
         if (exception.getErrores().size() > 0) {
            throw exception;
         } else if (checked != null && !checked.equals(CHECKED_SI)) {
             return this.avanzarNotaNotificadaACertifados(request);
          } else {
            return this.avanzarNotaNotificadaARecursos(request);
         }
      }
         return null;
    }
    
    private Evento avanzarNotaNotificadaACertifados (HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        gov.sir.core.util.TLSHttpClientComponent notifyREL = new gov.sir.core.util.TLSHttpClientComponent();
                try{
            HermodService hs = HermodService.getInstance();
            boolean turnoREL = (hs.isTurnoREL(turno.getIdWorkflow()));
            if(turnoREL){
                notifyREL.setFase(CRespuesta.DEVUELTO_FINALIZAR);
                notifyREL.sendMsgToREL(turno.getIdWorkflow());
            }
        } catch(Exception e){
            Logger.getLogger(ANCalificacion.class.getName()).log(Level.SEVERE, null, e);
             exception.addError("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
             throw exception;
        } catch(Throwable th){
             exception.addError("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
             throw exception;
        }
                
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnDevolucion ev = new EvnDevolucion(usuarioAuriga, EvnDevolucion.AVANZAR_NOTA_DEV_NOTIFICADA);
        ev.setTurno(turno);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);
        ev.setUsuarioSir(usuarioSIR); 
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        ev.setInfoUsuario(infoUsuario);
        if(fase.getID().equals(CFase.NOT_RECURSOS_NOTA)){
            ev.setRespuestaWF("FRACASO"); 
        } else{
            ev.setRespuestaWF(CRespuesta.CONFIRMAR);
        }
        
        return ev;
    }
    
    private Evento avanzarNotaNotificadaARecursos (HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
       
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnDevolucion ev = new EvnDevolucion(usuarioAuriga, EvnDevolucion.AVANZAR_NOTA_DEV_NOTIFICADA);
        ev.setTurno(turno);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);
        ev.setUsuarioSir(usuarioSIR); 
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        ev.setInfoUsuario(infoUsuario);
        ev.setRespuestaWF(CRespuesta.RECURSOS);
        
        return ev;
    }
    
     /**
     * @param request
     * @return
     */
    
    private Evento avanzarNotificarNotaDevolutiva(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Turno turn = null;
        preservarInfo(request);
        
        String idNot = (String) request.getSession().getAttribute(AWDevolucion.MODIFY_NOTIFICATION);
        if(idNot != null){
            exception.addError("Debe guardar la edición de la notificación para poder agregar otra");
            throw exception;
        }
        
         List notifications = null;
        try{
            HermodService hs = HermodService.getInstance();
            notifications = hs.getNotaDevNotificada(turno.getIdWorkflow());
            turn = hs.getTurnobyWF(turno.getIdWorkflow());
            String grandError = CError.validarPrincipioPrioridadNotNotaDev(turn);
            if(grandError != null){
                exception.addError(grandError);
                throw exception;
            }
        } catch(HermodException he){
            System.out.println("ERROR: " + he);
        }
        
        if(notifications == null || notifications.isEmpty()){
            exception.addError("Debe agregar al menos una notificación para continuar.");
            throw exception;
        }
        
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnDevolucion ev = new EvnDevolucion(usuarioAuriga, EvnDevolucion.NOTIFICAR_NOTA_DEVOLUTIVA);
        ev.setTurno(turno);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);
        ev.setUsuarioSir(usuarioSIR); 
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        ev.setInfoUsuario(infoUsuario);
        ev.setRespuestaWF(CRespuesta.EXITO);
        return ev;
    }
    
    
   private Evento notaDevolutivaNotificada(HttpServletRequest request) throws AccionWebException {
      HttpSession session = request.getSession();
      Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
      session.setAttribute("CARGAR_NOTA_NOTIFICADA", new Boolean(false));
      org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
      EvnDevolucion evento = new EvnDevolucion(usuarioAuriga, "ESPERANDO_JUZGADO");
      evento.setTurno(turno);
      return evento;
   }
    
    private Evento recursosNotaDevolutiva(HttpServletRequest request) throws AccionWebException {
      HttpSession session = request.getSession();
      Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
      session.setAttribute("CARGAR_RECURSOS_NOTA", new Boolean(false));
      List listaTodosRecursos = new ArrayList();
      List listaHistorials;
      if (turno != null) {
         listaHistorials = turno.getHistorials();
         if (listaHistorials != null) {
            for(int k = 0; k < listaHistorials.size(); ++k) {
               TurnoHistoria turnoH = (TurnoHistoria)listaHistorials.get(k);
               if (turnoH != null) {
                  List listaRecTurno = turnoH.getRecursos();
                  if (listaRecTurno != null) {
                     for(int t = 0; t < listaRecTurno.size(); ++t) {
                        Recurso rec = (Recurso)listaRecTurno.get(t);
                        if (rec != null) {
                           listaTodosRecursos.add(rec);
                        }
                     }
                  }
               }
            }
         }
      }

      listaHistorials = null;
      ArrayList tiposRec = new ArrayList();

      try {
         HermodService hs = HermodService.getInstance();
         List tipoRecurso = hs.getValorLookupCodesByTipo("TIPO_RECURSOS");
         if (tipoRecurso == null) {
            tipoRecurso = new ArrayList();
         }

         Iterator itTipo = ((List)tipoRecurso).iterator();

         while(itTipo.hasNext()) {
            OPLookupCodes code = (OPLookupCodes)itTipo.next();
            ElementoLista el = new ElementoLista();
            el.setId(code.getCodigo());
            el.setValor(code.getValor());
            tiposRec.add(el);
         }

         if (!listaTodosRecursos.isEmpty()) {
            Iterator itRec = listaTodosRecursos.iterator();

            for(int sec = 0; itRec.hasNext(); ++sec) {
               Recurso rec = (Recurso)itRec.next();
               String titulo = rec.getTitulo();
               Iterator itTip = tiposRec.iterator();

               while(itTip.hasNext()) {
                  ElementoLista el = (ElementoLista)itTip.next();
                  if (el.getValor().equals(titulo)) {
                     titulo = el.getId();
                     break;
                  }
               }

               request.getSession().setAttribute("TIPO_" + String.valueOf(sec), titulo);
            }
         }
      } catch (HermodException var15) {
         System.out.println("ERROR: " + var15.getMessage());
      }

      org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
      EvnDevolucion evento = new EvnDevolucion(usuarioAuriga, EvnDevolucion.ESPERANDO_JUZGADO);
      evento.setTurno(turno);
      return null;
   }
    
    public void cargarListas(HttpServletRequest request){
        List destinos;
        List destinatarios = new ArrayList();
        try{
            HermodService hs = HermodService.getInstance();
            destinos = hs.getValorLookupCodesByTipo(AWDevolucion.NOT_DESTINO);


        Iterator itDest = destinos.iterator();
        while(itDest.hasNext()){
            OPLookupCodes code = (OPLookupCodes) itDest.next();
            ElementoLista el = new ElementoLista();
            el.setId(code.getCodigo());
            el.setValor(code.getValor());
            destinatarios.add(el); 
        }

        } catch(HermodException he){
            System.out.println("ERROR: No fue posible obtener el listado");
        } 
        
        List tipoNot = null;
        List tipos = new ArrayList();
        try{
            HermodService hs = HermodService.getInstance();
            tipoNot = hs.getValorLookupCodesByTipo(AWDevolucion.NOT_TIPO);


            if(tipoNot == null){
                tipoNot = new ArrayList();
            }
        Iterator itTipo = tipoNot.iterator(); 
        while(itTipo.hasNext()){
            OPLookupCodes code = (OPLookupCodes) itTipo.next();
            ElementoLista el = new ElementoLista();
            el.setId(code.getCodigo());
            el.setValor(code.getValor());
            tipos.add(el);
        }

        } catch(HermodException he){
            System.out.println("ERROR: No fue posible obtener el listado");
        }
        
        List tipoDocumento = null;
        List tiposDoc = new ArrayList();
        try{
            HermodService hs = HermodService.getInstance();
            tipoDocumento = hs.getValorLookupCodesByTipo(AWDevolucion.DOCUMENTOS_IDENTIDAD_NATURAL);

            if(tipoDocumento == null){
                tipoDocumento = new ArrayList();
            }
        Iterator itTipo = tipoDocumento.iterator(); 
        while(itTipo.hasNext()){
            OPLookupCodes code = (OPLookupCodes) itTipo.next();
            ElementoLista el = new ElementoLista();
            if(!code.getCodigo().equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)){
            el.setId(code.getCodigo());
            el.setValor(code.getValor());
            tiposDoc.add(el);
            }
        }
        
        } catch(HermodException he){
            System.out.println("ERROR: No fue posible obtener el listado");
        }
        
        List tipoRecurso = null;
        List tiposRec = new ArrayList();
        try{
            HermodService hs = HermodService.getInstance();
            tipoRecurso = hs.getValorLookupCodesByTipo(AWDevolucion.TIPO_RECURSOS);

            if(tipoRecurso == null){
                tipoRecurso = new ArrayList();
            }
        Iterator itTipo = tipoRecurso.iterator(); 
        while(itTipo.hasNext()){
            OPLookupCodes code = (OPLookupCodes) itTipo.next();
            ElementoLista el = new ElementoLista();
            el.setId(code.getCodigo());
            el.setValor(code.getValor());
            tiposRec.add(el);
        }

        } catch(HermodException he){
            System.out.println("ERROR: No fue posible obtener el listado");
        }
        
        session.setAttribute(TIPOS,tipos);
        session.setAttribute(DESTINATARIOS,destinatarios);
        session.setAttribute(TIPOS_DOC,tiposDoc);
        session.setAttribute(TIPO_RECURSO, tiposRec);
    }
    
    private Evento notificarNotaDevolutiva(HttpServletRequest request) throws AccionWebException{
        HttpSession session = request.getSession();
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Usuario usuarioNeg = (Usuario) session.getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        SolicitudRegistro sol = (SolicitudRegistro) turno.getSolicitud();
        boolean isparcial = false;        
        
        try {
            if ("INSCRITO PARCIALMENTE".equals(sol.getClasificacionRegistro()) || "REGISTRO PARCIAL".equals(sol.getClasificacionRegistro())) {
                isparcial = true;
            }
        } catch (Exception e) {
            isparcial = false;
        }
        
        cargarListas(request);
        session.setAttribute("CARGA_FIRMA_REGISTRADOR", new Boolean(false));
        session.setAttribute("ISPARCIAL", isparcial);

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.OBTENER_BLOQUEO_FOLIOS);
        evento.setTurno(turno);
        evento.setUsuarioNeg(usuarioNeg);
        ANCalificacion cal = null;
        List Reproduccion = null;
        try {
            cal = new ANCalificacion();
            Reproduccion = cal.ObtenerReproduccionSellos(evento);
        } catch (EventoException ex) {
            Logger.getLogger(AWCalificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Reproduccion == null) {
            Reproduccion = new ArrayList();
        }
        if (Reproduccion.isEmpty()) {
            session.setAttribute("ISREPRODUCCION", false);
        } else {
            session.setAttribute("ISREPRODUCCION", true);
            session.setAttribute("LISTISREPRO", Reproduccion);
        }
        return evento;
    }
    
    private Evento eliminarResolucionesNot(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        List listaResoluciones = new ArrayList();
        List resolutions = null;
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        try {
            HermodService hs = HermodService.getInstance();
            Turno turn = hs.getTurnobyWF(turno.getIdWorkflow());
            TurnoPk oid = new TurnoPk();
            oid.anio = turn.getAnio();
            oid.idCirculo = turn.getIdCirculo();
            oid.idProceso = turn.getIdProceso();
            oid.idTurno = turn.getIdTurno();
            resolutions = hs.getOficiosTurno(oid);
        } catch(HermodException he){
          System.out.println("ERROR: " + he.getMessage());  
        }
        try {
            String check[] = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);

            for (int i = 0; i < check.length; i++) {
                Oficio oficio = (Oficio) resolutions.get(Integer.valueOf(check[i]).intValue());
                listaResoluciones.add(oficio);
                //resoluciones.remove(Integer.valueOf(check[i]).intValue());
            }

            //sesion.setAttribute(AWDevolucion.LISTA_RESOLUCIONES, resoluciones);
        } catch (Exception e) {
        }
        if (listaResoluciones.size() == 0) {
            throw new AccionInvalidaException("No se han seleccionado resoluciones a eliminar");
        }
        EvnDevolucion evento = new EvnDevolucion(usuarioAuriga, listaResoluciones, EvnDevolucion.ELIMINAR_RESOLUCIONES_NOT);
        evento.setTurno(turno);
        return evento;
    }
    
    private Evento eliminarResoluciones(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        List listaResoluciones = new ArrayList();
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        try {
            String check[] = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);

            for (int i = 0; i < check.length; i++) {
                Oficio oficio = (Oficio) resoluciones.get(Integer.valueOf(check[i]).intValue());
                listaResoluciones.add(oficio);
                //resoluciones.remove(Integer.valueOf(check[i]).intValue());
            }

            //sesion.setAttribute(AWDevolucion.LISTA_RESOLUCIONES, resoluciones);
        } catch (Exception e) {
        }
        if (listaResoluciones.size() == 0) {
            throw new AccionInvalidaException("No se han seleccionado resoluciones a eliminar");
        }
        EvnDevolucion evento = new EvnDevolucion(usuarioAuriga, listaResoluciones, EvnDevolucion.ELIMINAR_RESOLUCIONES);
        evento.setTurno(turno);
        return evento;
    }

    public void doStart(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        resoluciones = (List) sesion.getAttribute(AWDevolucion.LISTA_RESOLUCIONES);

        if (resoluciones == null) {
            resoluciones = new ArrayList();
            sesion.setAttribute(AWDevolucion.LISTA_RESOLUCIONES, resoluciones);
        }
    }

    private Evento agregarResolucion(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new DevolucionResolucion_InvalidParametersExceptionCollector();

        preservarInfo(request);
        HttpSession session = request.getSession();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        session.removeAttribute(AWDevolucion.RESOLUCION);
        session.removeAttribute(AWDevolucion.FECHA_RESOLUCION);
        String numRes = request.getParameter(AWDevolucion.RESOLUCION);
        String fecha = request.getParameter(AWDevolucion.FECHA_RESOLUCION);

        if (numRes == null
                || numRes.trim().equals("")) {
            exception.addError("Debe ingresar el numero de resolucion");
        } else {

            try {
                Integer.valueOf(numRes).intValue();
            } catch (NumberFormatException e) {
                exception.addError("El numero de resolucion es inválido");
            }
        }

        Date date = null;
        if ((fecha == null)
                || (fecha.equals(""))) {
            exception.addError("Debe ingresar la fecha de la resolucion");

        } else {
            try {
                date = DateFormatUtil.parse(fecha);
            } catch (Exception e) {
                exception.addError("La fecha de resolucion es inválida" + e.getMessage());
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Oficio of = new Oficio();
        of.setNumero(numRes);
        of.setFechaCreacion(date);
        
        //this.resoluciones.add(of);
        EvnDevolucion evento = new EvnDevolucion(usuarioAuriga, resoluciones, EvnDevolucion.AGREGAR_RESOLUCION);
        evento.setOficioResolucion(of);
        evento.setTurno(turno);
        return evento;

    }
    
    private Evento agregarResolucionRecursosNot (HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();

        preservarInfo(request);
        HttpSession session = request.getSession();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        session.removeAttribute(AWDevolucion.RESOLUCION);
        session.removeAttribute(AWDevolucion.FECHA_RESOLUCION);
        String numRes = request.getParameter(AWDevolucion.RESOLUCION);
        String fecha = request.getParameter(AWDevolucion.FECHA_RESOLUCION);

        if (numRes == null
                || numRes.trim().equals("")) {
            exception.addError("Debe ingresar el numero de resolucion");
        } else {

            try {
                Integer.valueOf(numRes).intValue();
            } catch (NumberFormatException e) {
                exception.addError("El numero de resolucion es inválido");
            }
        }

        Date date = null;
        if ((fecha == null)
                || (fecha.equals(""))) {
            exception.addError("Debe ingresar la fecha de la resolucion");

        } else {
            try {
                date = DateFormatUtil.parse(fecha);
            } catch (Exception e) {
                exception.addError("La fecha de resolucion es inválida" + e.getMessage());
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Oficio of = new Oficio();
        of.setNumero(numRes);
        of.setFechaCreacion(date);

        //this.resoluciones.add(of);
        EvnDevolucion evento = new EvnDevolucion(usuarioAuriga, resoluciones, EvnDevolucion.AGREGAR_RESOLUCION_RECURSOS_NOT);
        evento.setOficioResolucion(of);
        evento.setTurno(turno);
        return evento;

    }

    /**
     * @param itemId
     * @param request
     * @param session
     */
    public void save_PageItemState_Simple(String itemId, HttpServletRequest request, HttpSession session) {

        Object itemValue;
        itemValue = request.getParameter(itemId);
        session.setAttribute(itemId, itemValue);

    }

    /**
     * @param itemId
     * @param request
     * @param session
     */
    public void delete_PageItemState_Simple(String itemId, HttpServletRequest request, HttpSession session) {

        session.removeAttribute(itemId);

    }
    
    public Evento cargarContexto(HttpServletRequest request){
        preservarInfo(request);
        String destino = (String) request.getSession().getAttribute(AWDevolucion.DESTINO_NOTIFICACION);
        String destinoE = (String) request.getSession().getAttribute(AWDevolucion.DESTINO_NOTIFICACION + "E");
        String tipo = (String) request.getSession().getAttribute(AWDevolucion.TIPO_NOTIFICACION);
        String idNot = (String) request.getSession().getAttribute(AWDevolucion.MODIFY_NOTIFICATION);
        
        if(destinoE != null){
            
        String destinoEdition = (String) request.getSession().getAttribute(AWDevolucion.DESTINO_NOTIFICACION + "E");
        String tipoEdition = (String) request.getSession().getAttribute(AWDevolucion.TIPO_NOTIFICACION + "E");
        
           if(destinoEdition.equals(CDevoluciones.USUARIO)){
              request.getSession().setAttribute(IS_NOTIFICAR_USUARIO + "E", CDevoluciones.NOT_USUARIO);  
            } else if (destino.equals(CDevoluciones.OFICINA_ORIGEN)){
              request.getSession().setAttribute(IS_NOTIFICAR_USUARIO + "E", CDevoluciones.NOT_OFICINA);  
            } else {
              request.getSession().setAttribute(IS_NOTIFICAR_USUARIO + "E", CDevoluciones.DEFAULT);  
            }
        
        
            if(tipoEdition != null && !tipoEdition.isEmpty()){
                if(tipo.equals(CDevoluciones.CORREO_ELECTRONICO)){
                  request.getSession().setAttribute(IS_NOTIFICAR_CORREO + "E", CDevoluciones.CORREO_ELECTRONICO);  
                } else{
                  request.getSession().setAttribute(IS_NOTIFICAR_CORREO + "E", CDevoluciones.DEFAULT);  
                }
            }
        }
        if(destino != null){
        if(!destino.isEmpty()){
            if(destino.equals(CDevoluciones.USUARIO)){
              request.getSession().setAttribute(IS_NOTIFICAR_USUARIO, CDevoluciones.NOT_USUARIO);  
            } else if (destino.equals(CDevoluciones.OFICINA_ORIGEN)){
              request.getSession().setAttribute(IS_NOTIFICAR_USUARIO, CDevoluciones.NOT_OFICINA);  
            } else {
              request.getSession().setAttribute(IS_NOTIFICAR_USUARIO, CDevoluciones.DEFAULT);  
            }
        }
        
        if(tipo != null && !tipo.isEmpty()){
            if(tipo.equals(CDevoluciones.CORREO_ELECTRONICO)){
              request.getSession().setAttribute(IS_NOTIFICAR_CORREO, CDevoluciones.CORREO_ELECTRONICO);  
            } else{
              request.getSession().setAttribute(IS_NOTIFICAR_CORREO, CDevoluciones.DEFAULT);  
            }
        }
        }
        cargarListas(request);
        return null;
    }

    /**
     * @param request
     */
    public void do_PageProcessingProcess_Resolucion_OnAceptar_SaveState(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // guardar el estado de la pagina
        save_PageItemState_Simple(AWDevolucion.RESOLUCION, request, session);
        save_PageItemState_Simple(PAGEITEM__LIQUIDVALORDERECHOS, request, session);
        save_PageItemState_Simple(PAGEITEM__LIQUIDVALORIMPUESTOS, request, session);
        save_PageItemState_Simple(PAGEITEM__LIQUIDVALORTOTAL, request, session);
        save_PageItemState_Simple(PAGEITEM__LIQUIDVALORCERTIFICADOS, request, session);
        save_PageItemState_Simple(PAGEITEM__LIQUIDVALORCONSIGNACIONES, request, session);

    }

    /**
     * @param request
     */
    public void do_PageProcessingProcess_Resolucion_OnAceptar_RemoveState(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // borrar el estado de la pagina
        delete_PageItemState_Simple(AWDevolucion.RESOLUCION, request, session);
        delete_PageItemState_Simple(PAGEITEM__LIQUIDVALORDERECHOS, request, session);
        delete_PageItemState_Simple(PAGEITEM__LIQUIDVALORIMPUESTOS, request, session);
        delete_PageItemState_Simple(PAGEITEM__LIQUIDVALORTOTAL, request, session);
        delete_PageItemState_Simple(PAGEITEM__LIQUIDVALORCERTIFICADOS, request, session);
        delete_PageItemState_Simple(PAGEITEM__LIQUIDVALORCONSIGNACIONES, request, session);

    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    public Evento do_PageProcessingProcess_Resolucion_OnAceptar(HttpServletRequest request) throws AccionWebException {
        // session
        HttpSession session = request.getSession();

        // exception collector
        ValidacionParametrosException exception;

        // save state
        do_PageProcessingProcess_Resolucion_OnAceptar_SaveState(request);

        // parameters forwarded to business-methods ----------------------------------
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        SolicitudDevolucion solicitudDevolucion = (SolicitudDevolucion) turno.getSolicitud();
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        // ---------------------------------------------------------------------------
        // initialize exception collector
        exception = new DevolucionResolucion_InvalidParametersExceptionCollector();
        // form2data --------------------------------------------

        String texto = "";
        texto = request.getParameter(AWDevolucion.RESOLUCION);

        String pageItem_ValorDerechos;
        String pageItem_ValorImpuestos;
        String pageItem_ValorCertificados;
        String pageItem_ValorDerechosMayorValor;
        String pageItem_ValorImpuestosMayorValor;
        String pageItem_ValorCertificadosMayorValor;
        String pageItem_ValorTotal;
        String pageItem_ValorConsignaciones;
        String pageItem_ValorSaldoFavor;
        /**
         * YEFERSON
         */
        String pageItem_valorliquidaconserva;
        String pageItem_valorliquidaconservaGlobal;
        //fin

        double pageModel_ValorDerechos = 0;
        double pageModel_ValorImpuestos = 0;
        double pageModel_ValorTotal = 0;
        double pageModel_ValorCertificados = 0;
        double pageModel_ValorDerechosMayorValor = 0;
        double pageModel_ValorImpuestosMayorValor = 0;
        double pageModel_ValorCertificadosMayorValor = 0;
        double pageModel_ValorConsignaciones = 0;
        double pageModel_ValorSaldoFavor = 0;
        double pageModel_valorliquidaconserva = 0;

        // Obtener los datos para la liquidación (raw data)
        pageItem_ValorDerechos = request.getParameter(PAGEITEM__LIQUIDVALORDERECHOS);
        pageItem_ValorImpuestos = request.getParameter(PAGEITEM__LIQUIDVALORIMPUESTOS);
        pageItem_ValorCertificados = request.getParameter(PAGEITEM__LIQUIDVALORCERTIFICADOS);
        pageItem_ValorDerechosMayorValor = request.getParameter(PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR);
        pageItem_ValorImpuestosMayorValor = "0";
        pageItem_ValorCertificadosMayorValor = request.getParameter(PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR);
        pageItem_ValorTotal = request.getParameter(PAGEITEM__LIQUIDVALORTOTAL);
        pageItem_ValorConsignaciones = request.getParameter(PAGEITEM__LIQUIDVALORCONSIGNACIONES);
        pageItem_ValorSaldoFavor = request.getParameter(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR);
        /**
         * yeferson
         * Se establecen en 0 los campos eliminados desde la interfaz 
         */
        pageItem_valorliquidaconserva = "0";

        double valorTotalConservaDoc = 0;

        String conserva = request.getParameter(LIQUIDA_CONSERVA_GLOBAL);
        if (conserva == null || conserva.trim().equals("")) {
            conserva = "0";
        }
        conserva = conserva.replaceAll(",", "");
        valorTotalConservaDoc = Double.parseDouble(conserva);

//fin
        // ---------------------------------------------------------------------------
        // Validations ---------------------------------------------------------------

        /*if (texto == null || texto.length() <= 0) {
			// ResolucionInvalidaException
			exception.addError("La resolución que desea agregar es inválida");
		}*/
        String localFieldValue = "";
        boolean validateCondition;
        boolean poseeCertificadosAsociados = false;

        Solicitud local_SolicitudAnterior = null;
        Turno local_TurnoAnterior = null;
        if (solicitudDevolucion.getConsignaciones().size() == 0) {
            local_TurnoAnterior = turno.getSolicitud().getTurnoAnterior();
            local_SolicitudAnterior = (null == local_TurnoAnterior) ? (null) : (local_TurnoAnterior.getSolicitud());

            /*if ((null == local_TurnoAnterior) || (null == local_SolicitudAnterior)) {

				exception.addError("No se tiene referencia al turno anterior");

			}*/
        }

        //Obtener el valor de las solicitudes devueltas
        double valorRegistroDevuelto = 0;
        double valorImpuestoDevuelto = 0;
        double valorCertificadoDevuelto = 0;
        double valorConsignacionDevuelto = 0;
        double ValorTotalDevuelto = 0;
        double valorLiquidacionTurnoRegistro = 0;
        double valorRegistroDevueltoMayorValor = 0;
        double valorImpuestoDevueltoMayorValor = 0;
        double valorCertificadoDevueltoMayorValor = 0;
        double saldoFavor = 0;

        while (local_TurnoAnterior != null) {
            //Si el turno de registro posee turnos de certificados asociados
            if (local_TurnoAnterior.getSolicitud().getSolicitudesHijas().size() > 0) {
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
                                    if (chPago.getSaldoDocumentoAnulacion() > 0) {
                                        saldoFavor += chPago.getSaldoDocumento();
                                    }
                                } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                    DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                    if (chPago.getSaldoDocumentoAnulacion() > 0) {
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

                        valorCertificadoDevueltoAux = valorLiquidacionTurnoRegistroAux - valorRegistroDevueltoAux - valorImpuestoDevueltoAux;

                        valorCertificadoDevueltoMayorValor += valorCertificadoDevueltoAux;

                        if (liqReg.getPago() != null && liqReg.getPago().getAplicacionPagos() != null) {
                            List aPagos = liqReg.getPago().getAplicacionPagos();
                            for (int j = 0; j < aPagos.size(); j++) {
                                if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoCheque) {
                                    DocPagoCheque chPago = (DocPagoCheque) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                    if (chPago.getSaldoDocumentoAnulacion() > 0) {
                                        saldoFavor += chPago.getSaldoDocumento();
                                    }
                                } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                    DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                    if (chPago.getSaldoDocumentoAnulacion() > 0) {
                                        saldoFavor += chPago.getSaldoDocumento();
                                    }
                                }
                            }
                        }
                    }

                }

                //Se toma el valor de la liquidación del turno de certificados asociados
                if (poseeCertificadosAsociados) {
                    List solHijas = local_SolicitudAnterior.getSolicitudesHijas();
                    Iterator itHijas = solHijas.iterator();
                    while (itHijas.hasNext()) {
                        Liquidacion liq = (Liquidacion) ((SolicitudAsociada) itHijas.next()).getSolicitudHija().getLiquidaciones().get(0);
                        valorCertificadoDevuelto += liq.getValor() > 0 ? liq.getValor() : 0;
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
                                if (chPago.getSaldoDocumentoAnulacion() > 0) {
                                    saldoFavor += chPago.getSaldoDocumento();
                                }
                            } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                if (chPago.getSaldoDocumentoAnulacion() > 0) {
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
                LiquidacionTurnoFotocopia liqFoto = (LiquidacionTurnoFotocopia) local_SolicitudAnterior.getLiquidaciones().get(0);
                ValorTotalDevuelto += liqFoto.getValor() > 0 ? liqFoto.getValor() : 0;
                if (liqFoto.getPago() != null && liqFoto.getPago().getAplicacionPagos() != null) {
                    List aPagos = liqFoto.getPago().getAplicacionPagos();
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
                LiquidacionTurnoCertificadoMasivo liqFoto = (LiquidacionTurnoCertificadoMasivo) local_SolicitudAnterior.getLiquidaciones().get(0);
                ValorTotalDevuelto += liqFoto.getValor() > 0 ? liqFoto.getValor() : 0;
                if (liqFoto.getPago() != null && liqFoto.getPago().getAplicacionPagos() != null) {
                    List aPagos = liqFoto.getPago().getAplicacionPagos();
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

        if (local_TurnoAnterior == null && solicitudDevolucion.getConsignaciones().size() > 0) {
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

        if (solicitudDevolucion.isAprobada()) {
            // 1: validate:
            // field: valor derechos
            double valorRegistro = 0;
            validate_ValorDerechos:
            {

                // activar las validaciones solo si
                validateCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

                if (validateCondition) {

                    localFieldValue = pageItem_ValorDerechos;

                    if (!BasicValidationActions.stringNotNullOrNotEmpty(localFieldValue)) {
                        exception.addError("valor derechos: no puede ser nulo");
                    } else {
                        localFieldValue = localFieldValue.replaceAll(",", "");

                        if (!BasicValidationActions.isDouble(localFieldValue)) {

                            exception.addError("valor derechos: debe ser número");

                        } else {
                            pageModel_ValorDerechos = Double.parseDouble(localFieldValue);

                            if (pageModel_ValorDerechos < 0) {
                                exception.addError("valor derechos: No puede ser un valor negativo.");
                            } else {
                                valorRegistro = valorRegistro + pageModel_ValorDerechos;
                            }

                            /*
							if (!BasicValidationActions.isInRange(Double.NEGATIVE_INFINITY, 0d, pageModel_ValorDerechos)) {
	
								exception.addError("valor derechos: debe ser mayor que cero");
	
							}*/ // :if
                        } // :if

                    } // :if

                } // :if

            } // :validate_ValorDerechos

            validate_ValorDerechosMayorValor:
            {

                // activar las validaciones solo si
                validateCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

                if (validateCondition) {

                    localFieldValue = pageItem_ValorDerechosMayorValor;

                    if (localFieldValue == null || localFieldValue.trim().equals("")) {
                        localFieldValue = "0";
                    }
                    localFieldValue = localFieldValue.replaceAll(",", "");

                    if (!BasicValidationActions.isDouble(localFieldValue)) {

                        exception.addError("valor derechos: debe ser número");

                    } else {
                        pageModel_ValorDerechosMayorValor = Double.parseDouble(localFieldValue);
                        if (pageModel_ValorDerechosMayorValor < 0) {
                            exception.addError("valor derechos: No puede ser un valor negativo.");
                        } else {
                            valorRegistro = valorRegistro + pageModel_ValorDerechosMayorValor;
                            //pageModel_ValorDerechos += pageModel_ValorDerechosMayorValor;
                        }

                        /*
						if (!BasicValidationActions.isInRange(Double.NEGATIVE_INFINITY, 0d, pageModel_ValorDerechos)) {

							exception.addError("valor derechos: debe ser mayor que cero");

						}*/ // :if
                    } // :if

                } // :if

            } // :validate_ValorDerechosMayorValor

            // 2: validate:
            // field: valor impuestos
            validate_ValorImpuestos:
            {

                // activar las validaciones solo si
                validateCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

                if (validateCondition) {

                    localFieldValue = pageItem_ValorImpuestos;

                    if (!BasicValidationActions.stringNotNullOrNotEmpty(localFieldValue)) {
                        exception.addError("valor impuestos: no puede ser nulo");
                    } else {
                        localFieldValue = localFieldValue.replaceAll(",", "");

                        if (!BasicValidationActions.isDouble(localFieldValue)) {

                            exception.addError("valor impuestos: debe ser número");

                        } else {
                            pageModel_ValorImpuestos = Double.parseDouble(localFieldValue);

                            if (pageModel_ValorImpuestos < 0) {
                                exception.addError("valor impuestos: No puede ser un valor negativo.");
                            } else {
                                valorRegistro = valorRegistro + pageModel_ValorImpuestos;
                            }
                            /*if (BasicValidationActions.isInRange(Double.NEGATIVE_INFINITY, 0d, pageModel_ValorImpuestos)) {
	
								exception.addError("valor impuestos: debe ser mayor que cero");
	
							} */ // :if

                        } // :if*/

                    } // :if

                } // :if

            } // :validate_ValorImpuestos
            validate_ValorImpuestosMayorValor:
            {

                // activar las validaciones solo si
                validateCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

                if (validateCondition) {

                    localFieldValue = pageItem_ValorImpuestosMayorValor;

                    if (localFieldValue == null || localFieldValue.trim().equals("")) {
                        localFieldValue = "0";
                    }
                    localFieldValue = localFieldValue.replaceAll(",", "");

                    if (!BasicValidationActions.isDouble(localFieldValue)) {

                        exception.addError("valor derechos: debe ser número");

                    } else {
                        pageModel_ValorImpuestosMayorValor = Double.parseDouble(localFieldValue);

                        if (pageModel_ValorImpuestosMayorValor < 0) {
                            exception.addError("valor derechos: No puede ser un valor negativo.");
                        } else {
                            valorRegistro = valorRegistro + pageModel_ValorImpuestosMayorValor;
                            //pageModel_ValorImpuestos += pageModel_ValorImpuestosMayorValor;
                        }

                        /*
						if (!BasicValidationActions.isInRange(Double.NEGATIVE_INFINITY, 0d, pageModel_ValorDerechos)) {

							exception.addError("valor derechos: debe ser mayor que cero");

						}*/ // :if
                    }

                } // :if

            } // :validate_ValorImpuestosMayorValor

//			 1: validate:
            // field: valor certificado
            validate_ValorCertificado:
            {

                // activar las validaciones solo si
                validateCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

                if (validateCondition) {

                    localFieldValue = pageItem_ValorCertificados;

                    if (!BasicValidationActions.stringNotNullOrNotEmpty(localFieldValue)) {
                        exception.addError("valor certificado: no puede ser nulo");
                    } else {
                        localFieldValue = localFieldValue.replaceAll(",", "");

                        if (!BasicValidationActions.isDouble(localFieldValue)) {

                            exception.addError("valor certificados: debe ser número");

                        } else {
                            pageModel_ValorCertificados = Double.parseDouble(localFieldValue);

                            if (pageModel_ValorCertificados < 0) {
                                exception.addError("valor certificados: No puede ser un valor negativo.");
                            } else {
                                valorRegistro = valorRegistro + pageModel_ValorCertificados;
                            }

                        } // :if

                    } // :if

                } // :if

            } // :validate_ValorCertificado
            // field: valor certificado
            validate_ValorCertificadoMayorValor:
            {

                // activar las validaciones solo si
                validateCondition = local_SolicitudAnterior instanceof SolicitudRegistro;

                if (validateCondition) {

                    localFieldValue = pageItem_ValorCertificadosMayorValor;

                    if (localFieldValue == null || localFieldValue.trim().equals("")) {
                        localFieldValue = "0";
                    }
                    localFieldValue = localFieldValue.replaceAll(",", "");

                    if (!BasicValidationActions.isDouble(localFieldValue)) {

                        exception.addError("valor certificados: debe ser número");

                    } else {
                        pageModel_ValorCertificadosMayorValor = Double.parseDouble(localFieldValue);

                        if (pageModel_ValorCertificadosMayorValor < 0) {
                            exception.addError("valor certificados: No puede ser un valor negativo.");
                        } else {
                            valorRegistro = valorRegistro + pageModel_ValorCertificadosMayorValor ;
                            //pageModel_ValorCertificados += pageModel_ValorCertificadosMayorValor;
                        }

                    } // :if
                }

            } // :validate_ValorCertificadoMayorValor

            validate_ValorSaldoFavor:
            {

                localFieldValue = pageItem_ValorSaldoFavor;

                if (localFieldValue == null || localFieldValue.trim().equals("")) {
                    localFieldValue = "0";
                } // :if
                localFieldValue = localFieldValue.replaceAll(",", "");

                if (!BasicValidationActions.isDouble(localFieldValue)) {

                    exception.addError("valor consignaciones: debe ser número");

                } else {
                    pageModel_ValorSaldoFavor = Double.parseDouble(localFieldValue);

                    if (pageModel_ValorSaldoFavor < 0) {
                        exception.addError("valor saldo a favor: No puede ser un valor negativo.");
                    } else {

                        if (pageModel_ValorSaldoFavor > saldoFavor
                                && ((local_SolicitudAnterior instanceof SolicitudConsulta) || (local_SolicitudAnterior instanceof SolicitudFotocopia))) {
                            exception.addError("valor saldo a favor: No puede ser mayor al saldo a devolver.");
                        }

                    }
                    //pageModel_ValorTotal += pageModel_ValorSaldoFavor;
                }
            } // :validate_ValorSaldoFavor

            if (local_SolicitudAnterior instanceof SolicitudRegistro) {
                if (valorRegistro <= 0 && pageModel_ValorSaldoFavor <= 0) {
                    exception.addError("El valor a devolver debe ser mayor a cero.");
                }
            }

            // 2: validate:
            // field: valor total
            validate_ValorTotal:
            {

                // activar las validaciones solo si
                /*JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno Certificado Masivos*/
                validateCondition = (local_SolicitudAnterior instanceof SolicitudCertificado) ? true : (local_SolicitudAnterior instanceof SolicitudCertificadoMasivo) ? true : false;

                if (validateCondition) {

                    localFieldValue = pageItem_ValorTotal;

                    if (!BasicValidationActions.stringNotNullOrNotEmpty(localFieldValue)) {
                        exception.addError("valor total: no puede ser nulo");
                    } else {
                        localFieldValue = localFieldValue.replaceAll(",", "");

                        if (!BasicValidationActions.isDouble(localFieldValue)) {

                            exception.addError("valor total: debe ser numero");

                        } else {
                            pageModel_ValorTotal = Double.parseDouble(localFieldValue);

                            if (BasicValidationActions.isInRange(Double.NEGATIVE_INFINITY, 0d, pageModel_ValorTotal)) {

                                exception.addError("valor total: debe ser mayor que cero");

                            } // :if

                        } // :if

                    } // :if

                } // :if

            }

            validate_liquidaConservaDoc:
            {

                localFieldValue = pageItem_valorliquidaconserva;

                if (localFieldValue == null || localFieldValue.trim().equals("")) {
                    localFieldValue = "0";
                } // :if
                localFieldValue = localFieldValue.replaceAll(",", "");

                if (!BasicValidationActions.isDouble(localFieldValue)) {

                    exception.addError("valor conservaci?ocumental: debe ser número");

                } else {
                    pageModel_valorliquidaconserva = Double.parseDouble(localFieldValue);
                    if (pageModel_valorliquidaconserva < 0) {
                        exception.addError("valor conservacion documental: No puede ser un valor negativo.");
                    } else {

                        if (pageModel_valorliquidaconserva > valorTotalConservaDoc) {
                            exception.addError("valor conservacion documental: No puede ser mayor al saldo a devolver.");
                        }
                        valorRegistro = valorRegistro + pageModel_valorliquidaconserva;
                    }

                }
            }

// :validate_ValorImpuestos
            // 2: validate:
            // field: valor total
            validate_ValorConsultas:
            {

                // activar las validaciones solo si
                validateCondition = local_SolicitudAnterior instanceof SolicitudConsulta;

                if (validateCondition) {

                    localFieldValue = pageItem_ValorTotal;

                    if (!BasicValidationActions.stringNotNullOrNotEmpty(localFieldValue)) {
                        exception.addError("valor total: no puede ser nulo");
                    } else {
                        localFieldValue = localFieldValue.replaceAll(",", "");

                        if (!BasicValidationActions.isDouble(localFieldValue)) {

                            exception.addError("valor total: debe ser numero");

                        } else {
                            pageModel_ValorTotal = Double.parseDouble(localFieldValue);
                          

                            if (pageModel_ValorTotal > ValorTotalDevuelto) {
                                exception.addError("valor total: No puede ser mayor al valor de la solicitud a devolver.");
                            }

                            if (BasicValidationActions.isInRange(Double.NEGATIVE_INFINITY, 0d, pageModel_ValorTotal)) {

                                exception.addError("valor total: debe ser mayor que cero");

                            } // :if

                        } // :if

                    } // :if

                } // :if

            } // :validate_ValorImpuestos

            // 2: validate:
            // field: valor total
            validate_ValorFotocopias:
            {

                // activar las validaciones solo si
                validateCondition = local_SolicitudAnterior instanceof SolicitudFotocopia;

                if (validateCondition) {

                    localFieldValue = pageItem_ValorTotal;

                    if (!BasicValidationActions.stringNotNullOrNotEmpty(localFieldValue)) {
                        exception.addError("valor total: no puede ser nulo");
                    } else {
                        localFieldValue = localFieldValue.replaceAll(",", "");

                        if (!BasicValidationActions.isDouble(localFieldValue)) {

                            exception.addError("valor total: debe ser numero");

                        } else {
                            pageModel_ValorTotal = Double.parseDouble(localFieldValue);

                            if (pageModel_ValorTotal > ValorTotalDevuelto) {
                                exception.addError("valor total: No puede ser mayor al valor de la solicitud a devolver.");
                            }

                            if (BasicValidationActions.isInRange(Double.NEGATIVE_INFINITY, 0d, pageModel_ValorTotal)) {

                                exception.addError("valor total: debe ser mayor que cero");

                            } // :if

                        } // :if

                    } // :if

                } // :if

            } // :validate_ValorImpuestos
//			 1: validate:
            // field: valor consignaciones
            validate_ValorConsignacion:
            {

                // activar las validaciones solo si
                validateCondition = (local_SolicitudAnterior == null && solicitudDevolucion.getConsignaciones().size() > 0);

                if (validateCondition) {

                    localFieldValue = pageItem_ValorConsignaciones;

                    if(pageItem_ValorConsignaciones == null){
                        pageItem_ValorConsignaciones = String.valueOf(ValorTotalDevuelto);
            //            pageItem_ValorConsignaciones = String.valueOf(valorConsignacionDevuelto);
                   }
                    localFieldValue = pageItem_ValorConsignaciones;

                    if (!BasicValidationActions.stringNotNullOrNotEmpty(localFieldValue)) {
                        exception.addError("valor consignaciones: no puede ser nulo");
                    } else {
                        localFieldValue = localFieldValue.replaceAll(",", "");

                        if (!BasicValidationActions.isDouble(localFieldValue)) {

                            exception.addError("valor consignaciones: debe ser número");

                        } else {
                            pageModel_ValorConsignaciones = Double.parseDouble(localFieldValue);

                            if (pageModel_ValorConsignaciones < 0) {
                                exception.addError("valor consignaciones: No puede ser un valor negativo.");
                            } else {

                                if (pageModel_ValorConsignaciones > valorConsignacionDevuelto) {
                                    exception.addError("valor consignaciones: No puede ser mayor al valor de la solicitud a devolver.");
                                }
                                valorRegistro = valorRegistro + pageModel_ValorConsignaciones;
                                pageModel_ValorTotal = pageModel_ValorConsignaciones;
                            }

                        } // :if

                    } // :if

                } // :if

            } // :validate_ValorConsignacion

        }

        // ---------------------------------------------------------------------------
        // raise application error if required ----------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // ---------------------------------------------------------------------------
        // create liquidReference
        Liquidacion local_Liquidacion;

        // compute liquid
        // crear liquidacion a partir de los datos
        /*JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno Certificado Masivos*/
        local_Liquidacion = build_LocalLiqudacion(local_SolicitudAnterior, pageModel_ValorDerechos, pageModel_ValorImpuestos, pageModel_ValorCertificados, pageModel_ValorTotal, pageModel_ValorSaldoFavor, texto, pageModel_valorliquidaconserva);

// debug purposes // local_Liquidacion = new LiquidacionTurnoCertificado();
        if (local_Liquidacion != null) {
            local_Liquidacion.setUsuario(usuario);
            local_Liquidacion.setFecha(new Date());
        }

        // turno lleva los datos de la liquidacion
        EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.RESOLUCION_ACEPTAR, turno, fase, estacion, EvnDevolucion.DEFAULT, usuario);

        evn.setLiquidacion(local_Liquidacion);

        if (pageModel_ValorCertificadosMayorValor > 0 || pageModel_ValorDerechosMayorValor > 0 || pageModel_ValorImpuestosMayorValor > 0) {
            /*JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno Certificado Masivos*/
            Liquidacion local_Liquidacion2 = build_LocalLiqudacion(local_SolicitudAnterior, pageModel_ValorDerechosMayorValor, pageModel_ValorImpuestosMayorValor, pageModel_ValorCertificadosMayorValor, 0, pageModel_ValorSaldoFavor, texto, 0);
            evn.setLiquidacionMayorValor(local_Liquidacion2);
        }
        evn.setValorSaldoFavor((-1) * pageModel_ValorSaldoFavor);
        //evn.setResolucion(texto);
        //evn.setResoluciones(resoluciones);
        evn.setUID(session.getId());

        /**
         * yeferson
         */
        evn.setValorDevolucionConservaDoc(pageModel_valorliquidaconserva);
        return evn;

    }

    /**
     * @param solicitud
     * @param valorDerechos
     * @param valorImpuestos
     * @param valorTotal
     * @param justificacionTx
     * @return
     */
    /*JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno Certificado Masivos*/
    private Liquidacion build_LocalLiqudacion(Solicitud solicitud, double valorDerechos, double valorImpuestos, double valorCertificados, double valorTotal, double valorSaldoFavor, String justificacionTx, double conservacionDoc) {
        Liquidacion result = null;
        if (null == solicitud) {
            LiquidacionTurnoDevolucion local_Liquid = new LiquidacionTurnoDevolucion();
            valorTotal *= (-1);
            local_Liquid.setValor(valorTotal);

            result = local_Liquid;
            return result;
        } else if (solicitud instanceof SolicitudRegistro) {
            LiquidacionTurnoRegistro local_Liquid = new LiquidacionTurnoRegistro();

            
            conservacionDoc *= -1;
            valorDerechos *= -1;
            valorImpuestos *= -1;
            valorCertificados *= -1;
            double local_ValorTotal = valorDerechos + valorImpuestos + valorCertificados + valorTotal + conservacionDoc;
//            valorDerechos =  valorDerechos + conservacionDoc;
            local_Liquid.setValorDerechos(valorDerechos);
            local_Liquid.setValorImpuestos(valorImpuestos);
            local_Liquid.setValor(local_ValorTotal);
            local_Liquid.setJustificacionMayorValor(justificacionTx);
            local_Liquid.setValorConservacionDoc(conservacionDoc);

            result = local_Liquid;
            return result;
        } else if (solicitud instanceof SolicitudCertificado) {
            LiquidacionTurnoCertificado local_Liquid = new LiquidacionTurnoCertificado();
            valorTotal *= (-1);
            local_Liquid.setValor(valorTotal);

            result = local_Liquid;
            return result;
        } else if (solicitud instanceof SolicitudConsulta) {
            LiquidacionTurnoConsulta local_Liquid = new LiquidacionTurnoConsulta();
            valorTotal *= (-1);
            local_Liquid.setValor(valorTotal);

            result = local_Liquid;
            return result;
        } else if (solicitud instanceof SolicitudFotocopia) {
            LiquidacionTurnoFotocopia local_Liquid = new LiquidacionTurnoFotocopia();
            valorTotal *= (-1);
            local_Liquid.setValor(valorTotal);

            result = local_Liquid;
            return result;
        } /*JAlcazar Caso Mantis 3905 Creacion Proceso Devolucion Turno Certificado Masivos*/ else if (solicitud instanceof SolicitudCertificadoMasivo) {
            LiquidacionTurnoCertificadoMasivo local_Liquid = new LiquidacionTurnoCertificadoMasivo();
            valorSaldoFavor *= (-1);
            local_Liquid.setValor(valorSaldoFavor);
            result = local_Liquid;
            return result;
        }
        return null;

    }

    private void preservarInfo(HttpServletRequest request) {
        session.setAttribute(AWDevolucion.CHECK_RECURSOS, request.getParameter(AWDevolucion.CHECK_RECURSOS));
        session.setAttribute(AWDevolucion.CHECK_JUZGADO, request.getParameter(AWDevolucion.CHECK_JUZGADO));
        session.setAttribute(AWDevolucion.CHECK_RECURSOS_NOT, request.getParameter(AWDevolucion.CHECK_RECURSOS_NOT));
        session.setAttribute(AWDevolucion.TIPO_RECURSO, request.getParameter(AWDevolucion.TIPO_RECURSO));
        session.setAttribute(AWDevolucion.USUARIO_RECURSO, request.getParameter(AWDevolucion.USUARIO_RECURSO));
        session.setAttribute(AWDevolucion.DESCRIPCION_RECURSO, request.getParameter(AWDevolucion.DESCRIPCION_RECURSO));
        session.setAttribute(AWDevolucion.FECHA_RECURSO, request.getParameter(AWDevolucion.FECHA_RECURSO));
        session.setAttribute(AWDevolucion.FECHA_EJECUTORIA, request.getParameter(AWDevolucion.FECHA_EJECUTORIA));
        
        if(request.getParameter(AWDevolucion.DESTINO_NOTIFICACION) != null){
            session.setAttribute(AWDevolucion.DESTINO_NOTIFICACION, request.getParameter(AWDevolucion.DESTINO_NOTIFICACION));
        }
            
        if(request.getParameter(AWDevolucion.FECHA_NOTIFICACION) != null){
            session.setAttribute(AWDevolucion.FECHA_NOTIFICACION, request.getParameter(AWDevolucion.FECHA_NOTIFICACION));
        }
        
        if(request.getParameter(AWDevolucion.TIPO_NOTIFICACION) != null){
            session.setAttribute(AWDevolucion.TIPO_NOTIFICACION, request.getParameter(AWDevolucion.TIPO_NOTIFICACION));
        }
        
        if(request.getParameter(AWDevolucion.CHECK_APODERADO) != null){
            session.setAttribute(AWDevolucion.CHECK_APODERADO, request.getParameter(AWDevolucion.CHECK_APODERADO));
        }
        
        if(request.getParameter(AWDevolucion.NOMBRES_NOTIFICACION) != null){
            session.setAttribute(AWDevolucion.NOMBRES_NOTIFICACION, request.getParameter(AWDevolucion.NOMBRES_NOTIFICACION));   
        }
        
        if(request.getParameter(AWDevolucion.APELLIDOS_NOTIFICACION) != null){
            session.setAttribute(AWDevolucion.APELLIDOS_NOTIFICACION, request.getParameter(AWDevolucion.APELLIDOS_NOTIFICACION));   
        }
        
        if(request.getParameter(AWDevolucion.TIPO_DOCUMENTO) != null){
            session.setAttribute(AWDevolucion.TIPO_DOCUMENTO, request.getParameter(AWDevolucion.TIPO_DOCUMENTO));   
        }
        
        if(request.getParameter(AWDevolucion.NUMERO_DOCUMENTO) != null){
            session.setAttribute(AWDevolucion.NUMERO_DOCUMENTO, request.getParameter(AWDevolucion.NUMERO_DOCUMENTO));   
        }
        
        if(request.getParameter(AWDevolucion.DIRECCION) != null){
            session.setAttribute(AWDevolucion.DIRECCION, request.getParameter(AWDevolucion.DIRECCION));   
        }
        
        if(request.getParameter(AWDevolucion.TELEFONO) != null){
            session.setAttribute(AWDevolucion.TELEFONO, request.getParameter(AWDevolucion.TELEFONO));   
        }
        
        if(request.getParameter(AWDevolucion.OFICINA_ORIGEN) != null){
            session.setAttribute(AWDevolucion.OFICINA_ORIGEN, request.getParameter(AWDevolucion.OFICINA_ORIGEN));   
        }
        
        if(request.getParameter(AWDevolucion.CORREO) != null){
            session.setAttribute(AWDevolucion.CORREO, request.getParameter(AWDevolucion.CORREO));   
        }
    }
    
    private void removeFromSession(HttpServletRequest request){
        request.getSession().removeAttribute(DESTINO_NOTIFICACION);
        request.getSession().removeAttribute(FECHA_NOTIFICACION);
        request.getSession().removeAttribute(TIPO_NOTIFICACION);
        request.getSession().removeAttribute(CHECK_APODERADO);
        request.getSession().removeAttribute(NOMBRES_NOTIFICACION);
        request.getSession().removeAttribute(APELLIDOS_NOTIFICACION);
        request.getSession().removeAttribute(TIPO_DOCUMENTO);
        request.getSession().removeAttribute(NUMERO_DOCUMENTO);
        request.getSession().removeAttribute(DIRECCION);
        request.getSession().removeAttribute(TELEFONO);
        request.getSession().removeAttribute(OFICINA_ORIGEN);
        request.getSession().removeAttribute(CORREO);
        request.getSession().removeAttribute(IS_NOTIFICAR_USUARIO);
    }

    /**
     * Este método permite procesar cualquier evento de respuesta de la capa de
     * negocio, en caso de recibir alguno.
     *
     * @param request la información del formulario
     * @param eventoRespuesta el evento de respuesta de la capa de negocio, en
     * caso de existir alguno
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        //session.setAttribute(LISTA_RESOLUCIONES, resoluciones);
        session.removeAttribute(LISTA_RESOLUCIONES_PAGO);
        List resolucion = null;
        if (evento instanceof EvnRespDevolucion) {
            EvnRespDevolucion respuesta = (EvnRespDevolucion) evento;           
            if (respuesta != null && respuesta.getTipoEvento() != null && respuesta.getTipoEvento().equals(EvnDevolucion.ESPERANDO_JUZGADO)){
                session.setAttribute(CURRENT_STATE_NOTA_NOTIFICADA, respuesta.getCurrentStateNotaNotificada());
            }
            if (respuesta != null && respuesta.getTipoEvento() != null
                    && (respuesta.getTipoEvento().equals(AWDevolucion.AGREGAR_RECURSO) || respuesta.getTipoEvento().equals(EDITAR_RECURSO) || respuesta.getTipoEvento().equals(AWDevolucion.AGREGAR_RECURSO_NOTA_DEVOLUTIVA)
                    || respuesta.getTipoEvento().equals(AWDevolucion.EDITAR_RECURSO_NOTA_DEVOLUTIVA)))
            {
                session.setAttribute(WebKeys.TURNO, respuesta.getTurnoRespuesta());
            } else if (respuesta != null && respuesta.getTipoEvento() != null && respuesta.getTipoEvento().equals(AWDevolucion.CAMBIAR_TURNO_DEVOLUCION)) {
                double valorRegistroDevuelto = 0;
                double valorImpuestoDevuelto = 0;
                double valorCertificadoDevuelto = 0;
                double valorRegistroDevueltoMayorValor = 0;
                double valorImpuestoDevueltoMayorValor = 0;
                double valorCertificadoDevueltoMayorValor = 0;
                double ValorTotalDevuelto = 0;
                double valorLiquidacionTurnoRegistro = 0;
                double saldoFavor = 0;
                /**
                 * yeferson
                 */
                double valorLiquidaConserva = 0;

                Turno local_TurnoAnterior = respuesta.getTurnoRespuestaAnterior();
                Solicitud local_SolicitudAnterior = (null == local_TurnoAnterior) ? (null) : (local_TurnoAnterior.getSolicitud());
                boolean poseeCertificadosAsociados = false;
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

                                valorCertificadoDevueltoAux = valorLiquidacionTurnoRegistroAux - valorRegistroDevueltoAux - valorImpuestoDevueltoAux;

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

                        //Se toma el valor de la liquidación del turno de certificados asociados
                        if (poseeCertificadosAsociados) {
                            List solHijas = local_SolicitudAnterior.getSolicitudesHijas();
                            Iterator itHijas = solHijas.iterator();
                            while (itHijas.hasNext()) {
                                Liquidacion liq = (Liquidacion) ((SolicitudAsociada) itHijas.next()).getSolicitudHija().getLiquidaciones().get(0);
                                valorCertificadoDevuelto += liq.getValor() > 0 ? liq.getValor() : 0;
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
                                            saldoFavor = chPago.getSaldoDocumento();
                                        }
                                    } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                        DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                        if (chPago.getSaldoDocumento() > 0) {
                                            saldoFavor = chPago.getSaldoDocumento();
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
                                        saldoFavor = chPago.getSaldoDocumento();
                                    }
                                } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                    DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                    if (chPago.getSaldoDocumento() > 0) {
                                        saldoFavor = chPago.getSaldoDocumento();
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
                                        saldoFavor = chPago.getSaldoDocumento();
                                    }
                                } else if (((AplicacionPago) aPagos.get(j)).getDocumentoPago() instanceof DocPagoConsignacion) {
                                    DocPagoConsignacion chPago = (DocPagoConsignacion) ((AplicacionPago) aPagos.get(j)).getDocumentoPago();
                                    if (chPago.getSaldoDocumento() > 0) {
                                        saldoFavor = chPago.getSaldoDocumento();
                                    }
                                }
                            }
                        }
                    }

                    local_TurnoAnterior = local_SolicitudAnterior.getTurnoAnterior();
                    local_SolicitudAnterior = (null == local_TurnoAnterior) ? (local_SolicitudAnterior) : (local_TurnoAnterior.getSolicitud());
                } //:while turno_anterior

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
                //fin

                NumberFormat format = new DecimalFormat("###,###,###,###,###,###,###,###.00");
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS, format.format(valorRegistroDevuelto));
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS, format.format(valorCertificadoDevuelto));
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS, format.format(valorImpuestoDevuelto));
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORDERECHOS_MAYORVALOR, format.format(valorRegistroDevueltoMayorValor));
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCERTIFICADOS_MAYORVALOR, format.format(valorCertificadoDevueltoMayorValor));
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORIMPUESTOS_MAYORVALOR, format.format(valorImpuestoDevueltoMayorValor));
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL, format.format(ValorTotalDevuelto));
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDSALDOFAVOR, format.format(saldoFavor));

                session.setAttribute(WebKeys.TURNO_ANT_DEV, respuesta.getTurnoRespuestaAnterior());
                session.setAttribute(WebKeys.TURNO_DEVOLUCION_MODIFICADO, new Boolean(true));
                /**
                 * yeferson
                 */
                session.setAttribute(AWDevolucion.LIQUIDA_CONSERVA, format.format(valorLiquidaConserva));

                //fin
            } else if (respuesta != null && respuesta.getTipoEvento().equals(AGREGAR_CONSIGNACION_CHEQUE)) {
                double valorConsignacionDevuelto = 0;
                double ValorTotalDevuelto = 0;
                Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
                Consignacion consignacion = new Consignacion();
                SolicitudDevolucion solicitud = (SolicitudDevolucion) turno.getSolicitud();
                consignacion.setIdSolicitud(solicitud.getIdSolicitud());
                if (respuesta.getDocumentoPago().get(0) instanceof DocPagoConsignacion) {
                    DocPagoConsignacion docPago = (DocPagoConsignacion) respuesta.getDocumentoPago().get(0);
                    consignacion.setDocPago(docPago);
                } else if (respuesta.getDocumentoPago().get(0) instanceof DocPagoCheque) {
                    DocPagoCheque docPago = (DocPagoCheque) respuesta.getDocumentoPago().get(0);
                    consignacion.setDocPago(docPago);
                }
                solicitud.addConsignacion(consignacion);
                Iterator iter = solicitud.getConsignaciones().iterator();
                while (iter.hasNext()) {
                    Consignacion consig = (Consignacion) iter.next();
                    DocumentoPago docPago = consig.getDocPago();
                    if (docPago instanceof DocPagoConsignacion) {
                        valorConsignacionDevuelto += ((DocPagoConsignacion) docPago).getValorDocumento();
                    } else if (docPago instanceof DocPagoCheque) {
                        valorConsignacionDevuelto += ((DocPagoCheque) docPago).getValorDocumento();
                    }
                }
                ValorTotalDevuelto = valorConsignacionDevuelto;
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORTOTAL, "" + (long) ValorTotalDevuelto);
                session.setAttribute(AWDevolucion.PAGEITEM__LIQUIDVALORCONSIGNACIONES, "" + (long) valorConsignacionDevuelto);
                session.setAttribute(WebKeys.TURNO, turno);
                session.setAttribute(WebKeys.TURNO_DEVOLUCION_MODIFICADO, new Boolean(true));
            } else if (respuesta != null && (respuesta.getTipoEvento().equals(EvnDevolucion.AGREGAR_RESOLUCION)
                    || respuesta.getTipoEvento().equals(EvnDevolucion.ELIMINAR_RESOLUCIONES) || respuesta.getTipoEvento().equals(EvnDevolucion.AGREGAR_RESOLUCION_RECURSOS_NOT))) {
                resolucion = respuesta.getResoluciones();
            }

        }
        session.setAttribute(LISTA_RESOLUCIONES, resolucion);
    }

    /**
     * validarTurnoAnt
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento validarTurnoAnt(HttpServletRequest request, boolean change) throws AccionWebException {
        preservarInfo(request);
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        String idWF = request.getParameter(CTurno.TURNO_ANTERIOR);
        AnalisisDevolucion_InvalidParametersExceptionCollector excepcion = new AnalisisDevolucion_InvalidParametersExceptionCollector();
        List notasInformativas = (List) session.getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        if (idWF == null || idWF.equals("")) {
            excepcion.addError("El codigo del turno no es completo");
            throw excepcion;
        }
        String idWorkFlowTurno[] = idWF.split("-");
        if (idWorkFlowTurno.length != 4) {
            excepcion.addError("El formato del turno es inválido.");
            throw excepcion;
        }
        SolicitudDevolucion solicitud = (SolicitudDevolucion) turno.getSolicitud();
        if (solicitud != null && solicitud.getConsignaciones() != null && solicitud.getConsignaciones().size() > 0) {
            excepcion.addError("Existen consignaciones asociadas al turno de devolucion");
            throw excepcion;
        }
        try {
            Integer aux = new Integer(idWorkFlowTurno[0]);
            aux = new Integer(idWorkFlowTurno[2]);
            aux = new Integer(idWorkFlowTurno[3]);
        } catch (Exception e) {
            excepcion.addError("El formato del turno es inválido.");
            throw excepcion;
        }
        if (!idWorkFlowTurno[1].equals(circulo.getIdCirculo())) {
            excepcion.addError("El turno no es del mismo circulo.");
            throw excepcion;
        }
        EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.CAMBIAR_TURNO_DEVOLUCION, turno, fase,
                estacion, null, usuario);
        evn.setIdWfTurnoAnterior(idWF.toUpperCase());
        if (notasInformativas != null) {
            evn.setNotasInformativas(notasInformativas);
        }
        return evn;
    }

    private void eliminarConsignacionCheque(HttpServletRequest request) {
        preservarInfo(request);
        int pos = Integer.parseInt(request.getParameter(WebKeys.POSICION));
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        SolicitudDevolucion solicitud = (SolicitudDevolucion) turno.getSolicitud();
        solicitud.removeConsignacion((Consignacion) solicitud.getConsignaciones().get(pos));
        session.setAttribute(WebKeys.TURNO, turno);
        session.setAttribute(WebKeys.TURNO_DEVOLUCION_MODIFICADO, new Boolean(true));
    }

    private Evento validarConsignacionCheque(HttpServletRequest request) throws AccionWebException {
        preservarInfo(request);
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        String conCh = request.getParameter("NUMERO");
        String banco = request.getParameter("BANCO");
        String fecha = request.getParameter(AWLiquidacionDevolucion.FECHA_CONSIGNACION);
        String strValor = request.getParameter("VALOR_DOCUMENTO");
        String strSaldo = request.getParameter("SALDO_A_FAVOR");
        boolean isConsignacion = false;
        isConsignacion = request.getParameter(TIPO_BUSQUEDA) != null && request.getParameter(TIPO_BUSQUEDA).equals(BUSQUEDA_CONSIGNACION);
        session.setAttribute(TIPO_BUSQUEDA, request.getParameter(TIPO_BUSQUEDA));
        AnalisisDevolucion_InvalidParametersExceptionCollector excepcion = new AnalisisDevolucion_InvalidParametersExceptionCollector();
        List notasInformativas = (List) session.getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
        String idWF = request.getParameter(CTurno.TURNO_ANTERIOR);
        if (idWF != null && !idWF.equals("")) {
            excepcion.addError("No puede ingresar turno y consignacion/cheque en una misma solicitud");
            throw excepcion;
        }
        if (conCh == null || conCh.equals("")) {
            excepcion.addError("El codigo de la consignacion o cheque no esta completo");
            throw excepcion;
        }
        Date date = null;
        if ((fecha == null)
                || (fecha.equals(""))) {
            excepcion.addError("Debe ingresar la fecha de la consignacion");

        } else {
            try {
                date = DateFormatUtil.parse(fecha);
            } catch (Exception e) {
                excepcion.addError("La fecha de consignacion es inválida" + e.getMessage());
            }
        }
        if (banco == null || banco.length() <= 0 || banco.equals(WebKeys.SIN_SELECCIONAR)) {
            excepcion.addError("Seleccione el banco.");
            throw excepcion;
        }
        List bancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);
        Banco objBanco = new Banco();
        //objBanco.setIdBanco(banco.toUpperCase());
        if (bancos != null && bancos.size() > 0) {
            for (int i = 0; i < bancos.size(); i++) {
                if (((Banco) bancos.get(i)).getIdBanco() != null && ((Banco) bancos.get(i)).getIdBanco().equals(banco)) {
                    //objBanco.setNombre(((Banco)bancos.get(i)).getNombre());
                    objBanco = (Banco) bancos.get(i);
                }
            }
        }
        double valor = 0;
        if (strValor == null
                || strValor.trim().equals("")) {
            throw new AccionInvalidaException("Debe ingresar el valor de la consignacion o cheque");
        } else {
            try {
                valor = Double.valueOf(strValor).doubleValue();
                if (valor < 0) {
                    throw new AccionInvalidaException("El valor de la consignacion o cheque no puede ser negativo");
                }
            } catch (NumberFormatException e) {
                throw new AccionInvalidaException("El valor de la consignacion o cheque es inválido");
            }
        }
        double saldo = 0;
        if (strSaldo == null
                || strSaldo.trim().equals("")) {
            throw new AccionInvalidaException("Debe ingresar el saldo a favor");
        } else {
            try {
                saldo = Double.valueOf(strSaldo).doubleValue();
                if (saldo < 0) {
                    throw new AccionInvalidaException("El valor del saldo a favor no puede ser negativo");
                }
            } catch (NumberFormatException e) {
                throw new AccionInvalidaException("El valor del saldo a favor es inválido");
            }
        }

        EvnDevolucion evn = new EvnDevolucion(usuarioAuriga, EvnDevolucion.AGREGAR_CONSIGNACION_CHEQUE, turno,
                fase, estacion, null, usuario);
        if (isConsignacion) {
            DocPagoConsignacion docPagoCon = new DocPagoConsignacion();
            docPagoCon.setNoConsignacion(conCh.toUpperCase());
            docPagoCon.setBanco(objBanco);
            docPagoCon.setFecha(fecha);
            docPagoCon.setValorDocumento(valor);
            docPagoCon.setSaldoDocumento(saldo);
            docPagoCon.setTipoPago("CONSIGNACION");
            evn.setDocPago(docPagoCon);
        } else {
            DocPagoCheque docPagoCheque = new DocPagoCheque();
            docPagoCheque.setNoCheque(conCh.toUpperCase());
            docPagoCheque.setBanco(objBanco);
            docPagoCheque.setFecha(fecha);
            docPagoCheque.setValorDocumento(valor);
            docPagoCheque.setSaldoDocumento(saldo);
            docPagoCheque.setTipoPago("CHEQUE");
            evn.setDocPago(docPagoCheque);
        }
        if (notasInformativas != null) {
            evn.setNotasInformativas(notasInformativas);
        }
        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
        evn.setValorDevolucionConservaDoc(valor);
        evn.setRol(rol);
        return evn;
    }

}
