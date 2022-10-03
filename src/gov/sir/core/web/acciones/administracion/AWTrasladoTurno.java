package gov.sir.core.web.acciones.administracion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.administracion.EvnConsultaFolio;
import gov.sir.core.eventos.administracion.EvnRespConsultaFolio;
import gov.sir.core.eventos.administracion.EvnRespTrasladoTurno;
import gov.sir.core.eventos.administracion.EvnTrasladoTurno;
import gov.sir.core.eventos.certificado.EvnCambioMatricula;
import gov.sir.core.eventos.certificado.EvnRespCambioMatricula;
import gov.sir.core.eventos.comun.EvnNotas;
import gov.sir.core.negocio.acciones.comun.ANTurno;
import gov.sir.core.negocio.acciones.excepciones.ANTurnoException;
/**
 * Author: Santiago Vásquez Change:
 * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
 */
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CTestamentos;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.forseti.ForsetiService;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWAdminNotas;
import gov.sir.core.web.acciones.correccion.AWCorreccionCanalRecaudo;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ConsultarTurnoException;
import gov.sir.core.web.acciones.excepciones.TrasladoTurnoException;
import java.util.Calendar;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO;
import gov.sir.hermod.dao.impl.jdogenie.JDOPagosDAO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
/**
 * @Autor: Carlos Torres
 * @Mantis: 13176
 */
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.util.ExceptionPrinter;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos de
 * administración de ciudadanos provenientes de solicitudes a través del
 * protocolo HTTP
 *
 * @author jmendez
 */
public class AWTrasladoTurno extends SoporteAccionWeb {
    
    private HermodServiceInterface hermod;
    
    private ForsetiServiceInterface forseti;

    public static final String PARAM_OBJS_IMPRIMIBLESPENDIENTESENABLED = "PARAM:OBJS:IMPRIMIBLESPENDIENTESENABLED";

    public static final String PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOSCOUNT = "PARAM:OBJS:IMPRIMIBLESPENDIENTESTURNOHIJOSCOUNT";

    public static final String PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRECOUNT = "PARAM:OBJS:IMPRIMIBLESPENDIENTESTURNOPADRECOUNT";

    public static final String PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOS = "PARAM:OBJS:IMPRIMIBLESPENDIENTESTURNOHIJOS";

    public static final String PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRE = "PARAM:OBJS:IMPRIMIBLESPENDIENTESTURNOPADRE";

    /**
     * Constante que identifica la acción de adicionar una prohibición
     */
    public static final String CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO
            = "CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO";

    /**
     * Constante que identifica la acción de trasladar un turno
     */
    public static final String TRASLADAR_TURNO = "TRASLADAR_TURNO";
    
    public static final String IS_NOTIFY = "IS_NOTIFY";
    
    public static final String TURNO_REANOTAR = "TURNO_REANOTAR";
    
    public static final String BACK_NOT_NOTA = "BACK_NOT_NOTA";

    /**
     * Constante que identifica la acción de CONSULTAR un turno
     */
    public static final String CONSULTAR_TURNO = "CONSULTAR_TURNO";

    
    public static final String CORRECCION_CANAL_RECAUDO = "CORRECCION_CANAL_RECAUDO";
    /**
     * Constante que identifica la acción de CONSULTAR un turno certificado
     * cambio matricula
     */
    public static final String CONSULTAR_TURNO_CERTIFICADO_CAMBIO_MATRICULA = "CONSULTAR_TURNO_CERTIFICADO_CAMBIO_MATRICULA";

    /**
     * Constante que identifica la acción de ANULAR un turno
     */
    public static final String ANULAR_TURNO = "ANULAR_TURNO";

    /**
     * Constante que identifica la acción de HABILTIAR un turno
     */
    public static final String HABILITAR_TURNO = "HABILITAR_TURNO";

    /**
     * Constante que identifica la acción de iniciar la reanotacion de un turno
     */
    public static final String REANOTAR_TURNO_INICIAR = "REANOTAR_TURNO_INICIAR";

    /**
     * Constante que identifica la acción de REANOTAR de un turno
     */
    public static final String REANOTAR_TURNO = "REANOTAR_TURNO";

    /**
     * Constante que identifica la accion de cancelar una reanotacion
     */
    public static final String CANCELAR_REANOTACION = "CANCELAR_REANOTACION";

    /**
     * Constante que identifica la acción de ver los detalles un turno
     */
    public static final String VER_DETALLES_TURNO = "VER_DETALLES_TURNO";

    /**
     * Constante que identifica la acción de terminar la visualización de los
     * detalles un turno
     */
    public static final String TERMINA_VER_DETALLES_TURNO = "TERMINA_VER_DETALLES_TURNO";

    /**
     * Constante que identifica las acción de terminar la utilización de los
     * servicios de la acción WEB (Para limpiar la sesión y redirigir a la
     * página principal de páginas administrativas
     */
    public static final String TERMINA = "TERMINA";

    ///////////////////////
    /**
     * Constante que identifica la variable del HttpSession donde se almacena la
     * lista de ciudadanos del círculo
     */
    public static final String USUARIOS_CIRCULO = "USUARIOS_CIRCULO";

    /**
     * Constante que identifica la variable del HttpSession donde se almacena la
     * lista de turnos del círculo y proceso seleccionados
     */
    public static final String TURNOS_CIRCULO = "TURNOS_CIRCULO";

    /**
     * Constante que identifica la variable del HttpSession donde se almacena la
     * lista de circulos para ser desplegados en un ListaElementoHelper
     */
    public static final String LISTA_ELEMENTOS_CIRCULO = "LISTA_ELEMENTOS_CIRCULO";

    /**
     * Constante que identifica la variable del HttpSession donde se almacena
     * una lista de turnos resultantes de una consulta por el número de
     * matrícula
     */
    public static final String LISTA_TURNOS_CONSULTADOS_POR_MATRICULA
            = "LISTA_TURNOS_CONSULTADOS_POR_MATRICULA";

    /**
     * Constante que identifica la variable del HttpSession donde se almacena un
     * turno seleccionado
     */
    public static final String TURNO_SELECCIONADO = "TURNO_SELECCIONADO";

    /**
     * Constante que identifica la variable del HttpSession donde se almacena la
     * lista de turnos siguientes al turno seleccionado
     */
    public static final String LISTA_TURNOS_ANTERIORES = "LISTA_TURNOS_ANTERIORES";

    /**
     * Constante que identifica la variable del HttpSession donde se almacena la
     * lista de turnos siguientes al turno seleccionado
     */
    public static final String LISTA_TURNOS_SIGUIENTES = "LISTA_TURNOS_SIGUIENTES";

    /**
     * Constante que identifica la variable del HttpSession donde se almacena la
     * lista de turnos de las solicitudes asociadas
     */
    public static final String LISTA_TURNOS_ASOCIADOS = "LISTA_TURNOS_ASOCIADOS";

    /**
     * Constante que identifica la variable del HttpSession donde se almacena la
     * estacion de un turno asociado
     */
    public static final String ID_AMINISTRADOR_SAS_TURNO_ASOCIADO = "ID_AMINISTRADOR_SAS_TURNO_ASOCIADO";

    public static final String CONSULTAR_MATRICULA = "CONSULTAR_MATRICULA";

    public static final String TERMINA_CONSULTA_MATRICULA = "TERMINA_CONSULTA_MATRICULA";

    public static final String TURNO_PADRE = "TURNO_HIJO";

    public static final String TURNO_HIJO = "TURNO_PADRE";

    public static final String LISTA_TURNOS_FOLIO_MIG = "LISTA_TURNOS_FOLIO_MIG";

    //Constante que gurda la categoria de la minuta
    public static final String CATEGORIA_MINUTA = "CATEGORIA_MINUTA";

    public static final String CAMBIAR_FOLIO_MATRICULA_ID = "CAMBIAR_FOLIO_MATRICULA_ID";

    public static final String FOLIO_MATRICULA_ID = "FOLIO_MATRICULA_ID";

    public static final String PROCESO_EXITOSO_CAMBIO_MATRICULA = "PROCESO_EXITOSO_CAMBIO_MATRICULA";

    public static final String CANCELAR_CAMBIAR_FOLIO_MATRICULA_ID = "CANCELAR_CAMBIAR_FOLIO_MATRICULA_ID";

    //Variables que manejan la informacion del Ciudadano
    public static final String VALIDAR_CIUDADANO_EDICION = "VALIDAR_CIUDADANO_EDICION";

    public static final String REALIZAR_CIUDADANO_EDICION = "REALIZAR_CIUDADANO_EDICION";

    public static final String CIUDADANO_TO_EDIT = "CIUDADANO_TO_EDIT";

    public static final String CIUDADANO_TO_EDIT_ID_CIUDADANO = "CIUDADANO_TO_EDIT_ID_CIUDADANO";

    public static final String CIUDADANO_TO_EDIT_DOCUMENTO = "CIUDADANO_TO_EDIT_DOCUMENTO";

    public static final String CIUDADANO_TO_EDIT_TIPO_DOCUMENTO = "CIUDADANO_TO_EDIT_TIPO_DOCUMENTO";

    public static final String CIUDADANO_TO_EDIT_NOMBRE = "CIUDADANO_TO_EDIT_NOMBRE";

    public static final String CIUDADANO_TO_EDIT_APELLIDO1 = "CIUDADANO_TO_EDIT_APELLIDO1";

    public static final String CIUDADANO_TO_EDIT_APELLIDO2 = "CIUDADANO_TO_EDIT_APELLIDO2";

    public static final String CIUDADANO_TO_EDIT_TELEFONO = "CIUDADANO_TO_EDIT_TELEFONO";

    public static final String CIUDADANO_TO_EDIT_MENSAJE = "CIUDADANO_TO_EDIT_MENSAJE";

    public static final String CIRCULO_CERTIFICADO_NACIONAL = "CIRCULO_CERTIFICADO_NACIONAL";

    public static final String ES_TURNO_INTERNET = "ES_TURNO_INTERNET";

    public static final String IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE = "IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE";

    public static final String RESOLUCIONES = "RESOLUCIONES";

    public static final String LISTA_CALIFICADORES = "LISTA_CALIFICADORES";

    public static final String CALIFICADOR_SELECCIONADO = "CALIFICADOR_SELECCIONADO";
    /* @author : CTORRES
        * @change : se agrega constante VER_DETALLES_TURNO_TESTAMENTO
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */

    public static final String VER_DETALLES_TURNO_TESTAMENTO = "VER_DETALLES_TURNO_TESTAMENTO";

    /**
     * Constante que identifica la variable del HttpSession donde se almacena la
     * lista de turnos de las solicitudes asociadas
     *
     * @author: Julio Alcazar
     * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno -
     * Turnos Devolucion Negados
     */
    public static final String LISTA_TURNOS_DEVOLUCION = "LISTA_TURNOS_DEVOLUCION";

    /**
     * Author: Santiago Vásquez Change:
     * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
     */
    public static final String DEVOLUCION = "DEVOLUCION";
    public static final String MAYOR_VALOR = "MAYOR_VALOR";
    public static final String LIQUIDACIONES = "LIQUIDACIONES";
    public static final String LISTA_TARJETA_CREDITO = "LISTA_TARJETA_CREDITO";
    public static final String LISTA_TARJETA_DEBITO = "LISTA_TARJETA_DEBITO";
    public static final String LISTA_PAGOS_PSE = "LISTA_TARJETA_PSE";
    public static final String LISTA_EFECTIVO = "LISTA_TARJETA_EFECTIVO";
    public static final String LISTA_CONVENIO = "LISTA_TARJETA_CONVENIO";
    public static final String LISTA_CONSIGNACION = "LISTA_TARJETA_CONSIGNACION";
    public static final String LISTA_CHEQUE = "LISTA_TARJETA_CHEQUE";
    public static final String LISTA_MAYOR_VALOR = "LISTA_MAYOR_VALOR";
    public static final String LISTA_TARJETA_CREDITO_VM = "LISTA_TARJETA_CREDITO_VM";
    public static final String LISTA_TARJETA_DEBITO_VM = "LISTA_TARJETA_DEBITO_VM";
    public static final String LISTA_PAGOS_PSE_VM = "LISTA_TARJETA_PSE_VM";
    public static final String LISTA_EFECTIVO_VM = "LISTA_TARJETA_EFECTIVO_VM";
    public static final String LISTA_CONVENIO_VM = "LISTA_TARJETA_CONVENIO_VM";
    public static final String LISTA_CONSIGNACION_VM = "LISTA_TARJETA_CONSIGNACION_VM";
    public static final String LISTA_CHEQUE_VM = "LISTA_TARJETA_CHEQUE_VM";

    public static final String LISTA_GENERAL = "LISTA_GENERAL";
    public static final String LISTA_GENERAL_VM = "LISTA_GENERAL_VM";

    /**
     * @autor:yeferson martinez
     * @change: caso 317097
     */
    public static final String OBSERVACIONES_OFICIO_PERTENENCIA = "OBSERVACIONES_OFICIO_PERTENENCIA";
    
//    CARLOS
    public static final String LISTA_CANAL_RECAUDO_Y_CUENTAS_BANCARIAS = "LISTA_CANAL_RECAUDO_Y_CUENTAS_BANCARIAS";
    //

    /**
     * @autor:David A Rubio
     * @change: Declara variable cadena observaciones testamento
     */
    public static final String OBSERVACIONES_TESTAMENTO = "OBSERVACIONES_TESTAMENTO";

    public static final String SUMA_CONSERVACION = "SUMA_CONSERVACION";
    /**
     * @autor:Desarrollo 1
     * @change: Declara variableS cadena testamento
     */
    public static final String TOMO_TESTAMENTO = "TOMO_TESTAMENTO";
    public static final String NUMERO_ANOTACIONES = "NUMERO_ANOTACIONES";
    public static final String NUMERO_COPIAS = "NUMERO_COPIAS";
    public static final String REVOCA_ESCRITURA = "REVOCA_ESCRITURA";
    public static final String COPIAS_IMPRIMIR = "COPIAS_IMPRIMIR";
    public static final String FECHA_CREACION = "FECHA_CREACION";
    public static final String LIST_TESTADORES = "LIST_TESTADORES";

    public static final String VALOR_CONSERVACION_MAYOR_VALOR = "VALOR_CONSERVACION_MAYOR_VALOR";

    /**
     * Este método se encarga de procesar la solicitud del
     * <code>HttpServletRequest</code> de acuerdo al tipo de accion que tenga
     * como parámetro
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        String accion = request.getParameter(WebKeys.ACCION).trim();
        HttpSession session = request.getSession();

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        } else if (accion.equals(AWTrasladoTurno.CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO)) {
            return consultaTurnosUsuariosPorCirculo(request);
        } else if (accion.equals(AWTrasladoTurno.TERMINA)) {
            return limpiarSesion(request);
        } else if(accion.equals(AWTrasladoTurno.BACK_NOT_NOTA)){
            return null;
        } else if (accion.equals(AWTrasladoTurno.TERMINA_VER_DETALLES_TURNO)) {
            if (session.getAttribute(RESOLUCIONES) != null) {
                session.removeAttribute(RESOLUCIONES);
            }
            if (session.getAttribute("TURNOS_DERIVADOS") != null) {
                List turnos_derivados = (List) session.getAttribute("TURNOS_DERIVADOS");
                turnos_derivados.clear();
                session.setAttribute("TURNOS_DERIVADOS", turnos_derivados);
                System.out.println("TERMINA_VER_DETALLES_TURNO");
            }
            return null;
        } else if (accion.equals(AWTrasladoTurno.CONSULTAR_TURNO) || accion.equals(AWTrasladoTurno.CORRECCION_CANAL_RECAUDO)) {
            return consultarTurno(request);
        } else if (accion.equals(AWTrasladoTurno.CONSULTAR_TURNO_CERTIFICADO_CAMBIO_MATRICULA)) {
            return consultarTurnoCertificadoCambioMatricula(request);
        } else if (accion.equals(AWTrasladoTurno.ANULAR_TURNO)) {
            return anularTurno(request);
        } else if (accion.equals(AWTrasladoTurno.HABILITAR_TURNO)) {
            return habilitarTurno(request);
        } else if (accion.equals(REANOTAR_TURNO_INICIAR)) {
            return reanotarTurnoInicio(request);
        } else if (accion.equals(REANOTAR_TURNO)) {
            return reanotarTurno(request);
        } else if (accion.equals(CANCELAR_REANOTACION)) {
            return cancelarReanotacion(request);
        } else if (accion.equals(AWTrasladoTurno.VER_DETALLES_TURNO)) {
            return verDetallesDeTurno(request);
        } else if (accion.equals(CONSULTAR_MATRICULA)) {
            return consultarMatricula(request);
        } else if (accion.equals(TERMINA_CONSULTA_MATRICULA)) {
            return terminaConsultaMatricula(request);
        } else if (accion.equals(CAMBIAR_FOLIO_MATRICULA_ID)) {
            return cambiarFolioTurnoCertificado(request);
        } else if (accion.equals(CANCELAR_CAMBIAR_FOLIO_MATRICULA_ID)) {
            return cancelarCambiarFolioTurnoCertificado(request);
        } else if (accion.equals(VALIDAR_CIUDADANO_EDICION)) {
            return consultarCiudadanoEdicion(request);
        } else if (accion.equals(REALIZAR_CIUDADANO_EDICION)) {
            return realizarCiudadanoEdicion(request);
        } else if (accion.equals(IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE)) {
            return imprimirNotaDevolutiva(request);
            /* @author : CTORRES
                 * @change : se agrega condicion para la accion VER_DETALLES_TURNO_TESTAMENTO
                 * Caso Mantis : 12291
                 * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
             */
        } else if (accion.equals(VER_DETALLES_TURNO_TESTAMENTO)) {
            return verDetallesTurnoTestamento(request);
        }

        return null;
    }

    private Evento cancelarReanotacion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if(turno != null && !turno.getIdFase().equals(CFase.NOT_NOTA_DEVOLUTIVA)){
            session.removeAttribute(CTurno.ID_TURNO);
            session.removeAttribute(WebKeys.TURNO);
        }
        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_SIGUIENTES);
        session.removeAttribute(WebKeys.LISTA_TIPOS_NOTAS);
        session.removeAttribute(AWTrasladoTurno.LISTA_CALIFICADORES);
        session.removeAttribute(AWTrasladoTurno.CALIFICADOR_SELECCIONADO);
        session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
        session.removeAttribute(CNota.DESCRIPCION);
        return null;
    }

    private Evento terminaConsultaMatricula(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(WebKeys.FOLIO);
        session.removeAttribute(WebKeys.MAYOR_EXTENSION_FOLIO);
        session.removeAttribute(WebKeys.TOTAL_ANOTACIONES);
        session.removeAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN);
        session.removeAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES);
        session.removeAttribute(WebKeys.LISTA_FOLIOS_PADRE);
        session.removeAttribute(WebKeys.LISTA_FOLIOS_HIJO);
        session.removeAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES);
        session.removeAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION);
        session.removeAttribute(WebKeys.TURNO_TRAMITE);
        session.removeAttribute(WebKeys.TURNO_DEUDA);
        session.removeAttribute(WebKeys.USUARIO_BLOQUEO);
        session.removeAttribute(WebKeys.RECARGA);
        session.removeAttribute(WebKeys.RAZON_EXCEPCION);
        /**
         * @author : Carlos Torres Caso Mantis : 14985: Acta - Requerimiento
         * 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
         */
        request.getSession().removeAttribute(WebKeys.TRASLADO_ORIGEN);
        request.getSession().removeAttribute(WebKeys.TRASLADO_DESTINO);
        request.getSession().removeAttribute(WebKeys.CIRCULO_ORIGEN);
        request.getSession().removeAttribute(WebKeys.CIRCULO_DESTINO);
        return null;
    }

    private Evento consultarMatricula(HttpServletRequest request) {
        String id = request.getParameter(CFolio.ID_MATRICULA);
        HttpSession session = request.getSession();
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        session.setAttribute(CFolio.ID_MATRICULA_INICIAL, id);
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnConsultaFolio evento = new EvnConsultaFolio(usuario, EvnConsultaFolio.CONSULTAR_FOLIO, id, usuarioSIR);
        evento.setConsultarDefinitivo(true);
        evento.setCirculo(circulo);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios y turnos por círculo
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * círculo
     * @throws AccionWebException
     */
    private EvnTrasladoTurno consultaTurnosUsuariosPorCirculo(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        TrasladoTurnoException exception = new TrasladoTurnoException();

        String id = request.getParameter(CCirculo.ID_CIRCULO);
        if ((id == null) || (id.trim().length() == 0) || id.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un Círculo.");
        } else {
            session.setAttribute(CCirculo.ID_CIRCULO, id);
        }

        String idproceso = request.getParameter(CProceso.PROCESO_ID);
        if ((idproceso == null)
                || (idproceso.trim().length() == 0)
                || idproceso.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idproceso);
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Circulo dato = new Circulo();
        dato.setIdCirculo(id);

        Fase fase = new Fase();
        fase.setID(CFase.PMY_REGISTRAR);

        Proceso proceso = new Proceso();
        proceso.setIdProceso(Long.parseLong(idproceso));

        EvnTrasladoTurno evento
                = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO);
        evento.setCirculo(dato);
        evento.setProceso(proceso);
        evento.setFase(fase);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * ciudadanos por tipo de documento y número de documento
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno verDetallesDeTurno(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        ConsultarTurnoException exception = new ConsultarTurnoException();

        String idTurno = request.getParameter(CTurno.ID_TURNO);
        if (idTurno == null || idTurno.trim().length() == 0) {
            exception.addError("Debe seleccionar el turno a consultar");
        }

        String idMatricula = request.getParameter(CTurno.MATRICULA_TURNO);
        session.setAttribute(CTurno.MATRICULA_TURNO, idMatricula);

        Turno turno = new Turno();
        turno.setIdWorkflow(idTurno);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnTrasladoTurno evento
                = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.CONSULTAR_TURNO_POR_IDENTIFICADOR);
        evento.setTurno(turno);
        evento.setCirculo(circulo);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de un
     * turno por identificador o número de matrícula
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno consultarTurno(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        if (request.getParameter(CTurno.ERROR_FIND) != null) {
            System.out.println("ERROR_FIND");
            List turnos_derivados = null;
            //turnos_derivados.clear();
            session.setAttribute("TURNOS_DERIVADOS", turnos_derivados);
            
        }

        System.out.println("PARAMETRO REGRESAR TURNO " + request.getParameter(WebKeys.REGRESAR_TURNO));
        System.out.println("PARAMETRO ID TURNO " + request.getParameter(CTurno.ID_TURNO));
        System.out.println("PARAMETRO TIPO CONSULTA " + request.getParameter("TIPO_CONSULTA"));

        if (request.getParameter(WebKeys.REGRESAR_TURNO) != null) {
            System.out.println("LLEGO DESDE BOTON REGRESAR");
            List listaTurnosSession = (List) session.getAttribute("TURNOS_DERIVADOS");
            listaTurnosSession.remove(listaTurnosSession.size() - 1);
            System.out.println("LISTA TURNOS TAMAÑO REGRESAR_TURNO " + listaTurnosSession.size());

        } else {
            System.out.println("LLEGO DESDE BOTON VER/CONSULTAR");
            List listaTurnos = null;
            List listaTurnosSession = (List) session.getAttribute("TURNOS_DERIVADOS");
            if (listaTurnosSession == null) {
                System.out.println("LISTA DE TURNOS VACIA");
                listaTurnos = new ArrayList();
                listaTurnos.add(request.getParameter(CTurno.ID_TURNO));
                session.setAttribute("TURNOS_DERIVADOS", listaTurnos);
                System.out.println("TAMAÑO LISTA DE TURNOS " + listaTurnos.size());
            } else {
                System.out.println("LISTA DE TURNOS LLENA");
                listaTurnos = (List) session.getAttribute("TURNOS_DERIVADOS");
                listaTurnos.add(request.getParameter(CTurno.ID_TURNO));
                session.setAttribute("TURNOS_DERIVADOS", listaTurnos);
                System.out.println("TAMAÑO LISTA DE TURNOS " + listaTurnos.size());
            }
        }

//		sea agrega una variable de sesion
        boolean impresionpermitida = false;
        java.util.List roles = (List) session.getAttribute(WebKeys.LISTA_ROLES);
        if (roles != null) {
            Iterator it = roles.iterator();
            while (it.hasNext()) {
                org.auriga.core.modelo.transferObjects.Rol unRol = (org.auriga.core.modelo.transferObjects.Rol) it.next();
                if (unRol != null && unRol.getRolId() != null) {
                    if (unRol.getRolId().equals(CRoles.SIR_ROL_CALIFICADOR) || unRol.getRolId().equals(CRoles.SIR_ROL_REGISTRADOR) || unRol.getRolId().equals(CRoles.SIR_ROL_COORDINADOR_JURIDICO)) {
                        impresionpermitida = true;
                    }
                }
            }
        }

        if (impresionpermitida) {
            session.setAttribute("impresionpermitida", "1");
            //evRespuesta.setPermisoImpresion(true);
        }

        //se termina el agregue de una variable de sesion 
        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_CONSULTADOS_POR_MATRICULA);
        session.removeAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_ASOCIADOS);
        session.removeAttribute(AWCorreccionCanalRecaudo.CONSTANTESIGUARDO);
        session.removeAttribute(AWTrasladoTurno.TURNO_HIJO);
        session.removeAttribute(AWTrasladoTurno.TURNO_PADRE);

        EvnTrasladoTurno evento = null;
        ConsultarTurnoException exception = new ConsultarTurnoException();

        String tipoConsultaTurno = request.getParameter("TIPO_CONSULTA");

        if (tipoConsultaTurno == null || tipoConsultaTurno.trim().length() == 0) {
            tipoConsultaTurno = CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR;
        }

        request.getSession().setAttribute("TIPO_CONSULTA", tipoConsultaTurno);

        if (tipoConsultaTurno.equals(CTurno.CONSULTA_TURNO_POR_IDENTIFICADOR)) {
            String idTurno = request.getParameter(CTurno.ID_TURNO);
            session.setAttribute(CTurno.ID_TURNO, idTurno);
            session.removeAttribute(CTurno.MATRICULA_TURNO);
            if (idTurno == null || idTurno.trim().length() == 0) {
                exception.addError("Debe digitar el identificador del turno a consultar");
            }

            if (idTurno.indexOf("-") == -1) {
                exception.addError("El turno a consultar es inválido");
            } else {
                String[] partes = idTurno.split("-");
                if (partes.length != 4) {
                    exception.addError("El turno a consultar es inválido");
                }
            }

            Turno turno = new Turno();
            turno.setIdWorkflow(idTurno);

            evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.CONSULTAR_TURNO_POR_IDENTIFICADOR);
            evento.setTurno(turno);
            evento.setCirculo(circulo);

        } else if (tipoConsultaTurno.equals(CTurno.CONSULTA_TURNO_POR_MATRICULA)) {
            String matricula = request.getParameter(CTurno.MATRICULA_TURNO);
            String idProceso = request.getParameter(CProceso.PROCESO_ID);
            Proceso proceso = null;

            if (matricula == null || matricula.trim().length() == 0) {
                exception.addError("Debe digitar el número de Matricula");
            } else if (matricula.indexOf("-") == -1) {
                exception.addError("La mátricula ingresada es inválida");
            }

            // Si se seleccionó un proceso específico, y si no se seleccionó la opción
            // de "CUALQUIER" proceso, se crea el filtro de proceso, de lo contrario se
            // busca para todos los procesos.
            if (idProceso != null && !idProceso.equals("") && !idProceso.equals("CUALQUIERA") && !idProceso.equals(WebKeys.SIN_SELECCIONAR)) {
                long numIdProceso = Long.parseLong(idProceso);
                proceso = new Proceso();
                proceso.setIdProceso(numIdProceso);
                session.setAttribute(CProceso.PROCESO_ID, idProceso);
            }

            Folio folio = new Folio();
            folio.setIdMatricula(matricula);

            session.setAttribute(CTurno.MATRICULA_TURNO, matricula);
            session.setAttribute(CProceso.PROCESO_ID, idProceso);
            session.removeAttribute(CTurno.ID_TURNO);

            //evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.CONSULTAR_TURNO_POR_MATRICULA);
            if (proceso != null) {
                evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.CONSULTAR_TURNO_POR_MATRICULA_CIRCULO_PROCESO);
                evento.setFolio(folio);
                evento.setProceso(proceso);
                evento.setCirculo(circulo);
            } else {
                evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.CONSULTAR_TURNO_POR_MATRICULA_CIRCULO);
                evento.setFolio(folio);
                evento.setCirculo(circulo);
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de un
     * turno por identificador o número de matrícula, pero elimina el ultimo id
     * de turno guardado en la lista de turnos
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno consultarTurnoRegresar(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();

        List listaTurnosSession = (List) session.getAttribute("TURNOS_DERIVADOS");
        listaTurnosSession.remove(listaTurnosSession.size() - 1);
        System.out.println("LISTA TURNOS TAMAÑO " + listaTurnosSession.size());

        return consultarTurno(request);
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de un
     * turno por identificador o número de matrícula
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno consultarTurnoCertificadoCambioMatricula(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        Log.getInstance().debug(AWTrasladoTurno.class, "ENTRO A LA CONSULTA CAMBIO MATRICULA");

        EvnTrasladoTurno evento = null;
        ConsultarTurnoException exception = new ConsultarTurnoException();

        Log.getInstance().debug(AWTrasladoTurno.class, request.getParameter(CTurno.ID_TURNO));

        String idTurno = request.getParameter(CTurno.ID_TURNO);
        session.setAttribute(CTurno.ID_TURNO, idTurno);
        session.removeAttribute(CTurno.MATRICULA_TURNO);
        if (idTurno == null || idTurno.trim().length() == 0) {
            exception.addError("Debe digitar el identificador del turno a consultar");
        }

        if (idTurno.indexOf("-") == -1) {
            exception.addError("El turno a consultar es inválido");
        } else {
            String[] partes = idTurno.split("-");
            if (partes.length != 4) {
                exception.addError("El turno a consultar es inválido");
            }
        }

        Turno turno = new Turno();
        turno.setIdWorkflow(idTurno);

        evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.CONSULTAR_TURNO_CERTIFICADO_CAMBIO_MATRICULA);
        evento.setTurno(turno);
        evento.setCirculo(circulo);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        return evento;
    }

////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de un
     * turno por identificador o número de matrícula
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnCambioMatricula cambiarFolioTurnoCertificado(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        Boolean notaAdminAgregada = (Boolean) session.getAttribute(AWAdminNotas.NOTA_AGREGADA_ADMIN);
        String matNueva = (String) request.getParameter(FOLIO_MATRICULA_ID);

        ValidacionParametrosException exception = new ValidacionParametrosException();

        if (notaAdminAgregada == null || !notaAdminAgregada.booleanValue()) {
            exception.addError("Debe ingresar una Nota Informativa para cambiar la Matricula");
        }

        if (matNueva == null || matNueva.equals("")) {
            exception.addError("Debe ingresar una Matricula Nueva");
        }

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (turno.isNacional() && request.getParameter(AWTrasladoTurno.CIRCULO_CERTIFICADO_NACIONAL).equals("SIN_SELECCIONAR")) {
            exception.addError("Debe escoger un Círculo");
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String idCirculo = circulo.getIdCirculo();
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo circ = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        String matriculaNueva = null;

        if (!turno.isNacional()) {
            matriculaNueva = circ.getIdCirculo() + "-";
        } else {
            String idCircNuevo = null;
            idCircNuevo = request.getParameter(AWTrasladoTurno.CIRCULO_CERTIFICADO_NACIONAL);
            matriculaNueva = idCircNuevo + "-";
        }
        matriculaNueva += matNueva;
        /**
         * @autor Edgar Lora
         * @mantis 11987
         */
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
        try {
            if (validacionesSIR.isEstadoFolioBloqueado(matriculaNueva)) {
                exception.addError("La matricula por la que desea cambiar se encuentra en estado 'Bloqueado'.");
            }
        } catch (GeneralSIRException ex) {
            Logger.getLogger(AWTrasladoTurno.class.getName()).log(Level.SEVERE, null, ex);
        }

        SolicitudCertificado solCerti = (SolicitudCertificado) turno.getSolicitud();
        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        /*
                 * @author      : Julio Alcázar Rivas
                 * @change      : a)Se verifica que el exista un folio asociado a la solicitud
                 *                b)Se reubica el lugar donde se lanzan las excepciones
                 * Caso Mantis  :   07647
         */
        String matriculaActual = null;
        if (folio != null) {
            matriculaActual = folio.getIdMatricula();

            if (matriculaActual.equals(matriculaNueva)) {
                exception.addError("El numero de matricula ingresado es el mismo que está asociado al turno");
            }
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List liquidaciones = solCerti.getLiquidaciones();
        LiquidacionTurnoCertificado liquida = (LiquidacionTurnoCertificado) liquidaciones.get(liquidaciones.size() - 1);
        TipoCertificado tipoCerti = liquida.getTipoCertificado();
        String tipoCertificado = tipoCerti.getIdTipoCertificado();

        TurnoPk tid = new TurnoPk();
        tid.anio = turno.getAnio();
        tid.idCirculo = turno.getIdCirculo();
        tid.idProceso = turno.getIdProceso();
        tid.idTurno = turno.getIdTurno();

        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        EvnCambioMatricula evnCambioMatricula = new EvnCambioMatricula(usuarioAuriga, matriculaActual, matriculaNueva, tipoCertificado, tid, usuarioNeg);
        evnCambioMatricula.setNacional(turno.isNacional());
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(usuarioNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new java.util.Date(request.getSession().getCreationTime()));
        evnCambioMatricula.setInfoUsuario(infoUsuario);
        return evnCambioMatricula;
    }

////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de un
     * turno por identificador o número de matrícula
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno cancelarCambiarFolioTurnoCertificado(HttpServletRequest request) throws AccionWebException {
        //Quitar de la session los atributos que no sirven
        HttpSession session = request.getSession();
        session.removeAttribute(WebKeys.TURNO);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        session.removeAttribute(AWAdminNotas.NOTA_AGREGADA_ADMIN);
        EvnTrasladoTurno evento = null;
        evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.CANCELAR_CAMBIO_MATRICULA);
        return evento;
    }

    /**
     * Este método se encarga de validar la edicion del ciudadano generar un
     * evento de consulta de un tipo y numero de identificación de usuario
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno consultarCiudadanoEdicion(HttpServletRequest request) throws AccionWebException {
        //Quitar de la session los atributos que no sirven
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circ = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String tipoDocumento = (String) request.getParameter(CCiudadano.TIPODOC);
        String numeroDocumento = (String) request.getParameter(CCiudadano.IDCIUDADANO);

        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_ID_CIUDADANO);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_DOCUMENTO);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_TIPO_DOCUMENTO);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_NOMBRE);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO1);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO2);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_TELEFONO);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_MENSAJE);

        Ciudadano ciudadanoToEdit = new Ciudadano();
        ciudadanoToEdit.setTipoDoc(tipoDocumento);
        ciudadanoToEdit.setDocumento(numeroDocumento);
        ciudadanoToEdit.setIdCirculo(circ.getIdCirculo());

        EvnTrasladoTurno evento = null;
        evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.VALIDAR_CIUDADANO_EDICION);
        evento.setCiudadanoToEdit(ciudadanoToEdit);
        return evento;
    }

    /**
     * Este método se encarga de realizar la edicion del Ciudadano generar un
     * evento de consulta de un tipo y numero de identificación de usuario
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno realizarCiudadanoEdicion(HttpServletRequest request) throws AccionWebException {
        //Quitar de la session los atributos que no sirven
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circ = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        ValidacionParametrosException exception = new ValidacionParametrosException();

        String ciudadanoId = (String) session.getAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_ID_CIUDADANO);//request.getParameter(  PAGEITEM__CIUDADANO_IDCIUDADANO );
        String numId = request.getParameter(AWTrasladoTurno.CIUDADANO_TO_EDIT_DOCUMENTO);
        String nombres = request.getParameter(AWTrasladoTurno.CIUDADANO_TO_EDIT_NOMBRE);
        String tipoId = request.getParameter(AWTrasladoTurno.CIUDADANO_TO_EDIT_TIPO_DOCUMENTO);
        String apellido1 = request.getParameter(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO1);
        String apellido2 = request.getParameter(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO2);
        String telefono = request.getParameter(AWTrasladoTurno.CIUDADANO_TO_EDIT_TELEFONO);

        Ciudadano ciudadano = new Ciudadano();

        if (ciudadanoId == null || ciudadanoId.trim().equals("")) {
            exception.addError("No existe un id de ciudadano para editar");
        }

        if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo de identificación para el ciudadano");
        } else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
            if (apellido1 == null || apellido1.trim().equals("")) {
                exception.addError("Debe ingresar el primer apellido del Ciudadano");
            }
        } else if (tipoId.equals(COPLookupCodes.NIT)) {
            if (apellido1 == null || apellido1.trim().equals("")) {
                exception.addError("Debe ingresar el primer apellido del Ciudadano");
            }
        } else {
            double valorId = 0.0d;
            if (numId == null || numId.trim().equals("")) {
                exception.addError("Debe ingresar el número de identificación del Ciudadano");
            } else {
                if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
                    try {
                        valorId = Double.parseDouble(numId);
                        if (valorId <= 0) {
                            exception.addError("El valor del documento no puede ser negativo o cero");
                        }
                    } catch (NumberFormatException e) {
                        exception.addError("El número de identificación de la persona es inválido. No puede ser alfanumérico");
                    }
                }
            }
            if (nombres == null || nombres.trim().equals("")) {
                exception.addError("Debe ingresar el nombre del Ciudadano");
            }
            if (apellido1 == null || apellido1.trim().equals("")) {
                exception.addError("Debe ingresar el primer apellido del Ciudadano");
            }
        }

        if (ciudadanoId != null) {
            ciudadano.setIdCiudadano(ciudadanoId);
        }

        if (telefono != null) {
            ciudadano.setTelefono(telefono);
        }

        if (tipoId != null) {
            ciudadano.setTipoDoc(tipoId);
        }

        if (numId != null) {
            ciudadano.setDocumento(numId);
        }

        if (nombres != null) {
            ciudadano.setNombre(nombres);
        }

        if (apellido1 != null) {
            ciudadano.setApellido1(apellido1);
        }

        if (apellido2 != null) {
            ciudadano.setApellido2(apellido2);
        }

        ciudadano.setIdCirculo(circ.getIdCirculo());

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        EvnTrasladoTurno evento = null;
        evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.RELIZAR_CIUDADANO_EDICION);
        evento.setCiudadanoToEdit(ciudadano);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de un
     * turno por identificador o número de matrícula
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno anularTurno(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        
        
        /*
        
        Requerimiento CAMBIO SOBRE PROCESO DE ANULACIÓN:
        - anular el turno durante los dos (2) días hábiles siguientes a la radicación del mismo
        - Tratándose del usuario que tenga el rol registrador no debe existir límite de tiempo para la anulación del turno
        
        */
        
        List<Rol> roles = (List<Rol>) session.getAttribute(WebKeys.LISTA_ROLES);
        
        
        
        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_CONSULTADOS_POR_MATRICULA);
        session.removeAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_ASOCIADOS);

        EvnTrasladoTurno evento = null;
        ConsultarTurnoException exception = new ConsultarTurnoException();

        String idTurno = request.getParameter(CTurno.ID_TURNO);
        session.setAttribute(CTurno.ID_TURNO, idTurno);
        session.removeAttribute(CTurno.MATRICULA_TURNO);
        if (idTurno == null || idTurno.trim().length() == 0) {
            exception.addError("Debe digitar el identificador del turno a anular");
        }

        if (idTurno.indexOf("-") == -1) {
            exception.addError("El turno a anular es inválido");
        } else {
            String[] partes = idTurno.split("-");
            if (partes.length != 4) {
                exception.addError("El turno a anular es inválido");
            }
        }

        String observacionesAnulacion = request.getParameter(CTurno.OBSERVACIONES_ANULACION);

        if (observacionesAnulacion == null || observacionesAnulacion.trim().length() == 0) {
            exception.addError("Debe digitar una observación para anular el turno");
        }

        Turno turno = new Turno();

        turno.setIdWorkflow(idTurno);
        turno.setObservacionesAnulacion(observacionesAnulacion);
        
        
        evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.ANULAR_TURNO);
        evento.setTurno(turno);
        evento.setCirculo(circulo);
        evento.setObservacionesAnulacion(observacionesAnulacion);
        evento.setUsuarioNegocio(usuarioSIR);
        evento.setRoles(roles);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        /**
         * @autor HGOMEZ
         * @mantis 13176
         * @Requerimiento 061_453_Requerimiento_Auditoria
         * @descripcion Mantiene la información del usuario.
         */
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(), String.valueOf(usuarioNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);

        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de un
     * turno por identificador o número de matrícula
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno habilitarTurno(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_CONSULTADOS_POR_MATRICULA);
        session.removeAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_ASOCIADOS);

        EvnTrasladoTurno evento = null;
        ConsultarTurnoException exception = new ConsultarTurnoException();

        String idTurno = request.getParameter(CTurno.ID_TURNO);
        session.setAttribute(CTurno.ID_TURNO, idTurno);
        session.removeAttribute(CTurno.MATRICULA_TURNO);
        if (idTurno == null || idTurno.trim().length() == 0) {
            exception.addError("Debe digitar el identificador del turno a anular");
        }

        if (idTurno.indexOf("-") == -1) {
            exception.addError("El turno a anular es inválido");
        } else {
            String[] partes = idTurno.split("-");
            if (partes.length != 4) {
                exception.addError("El turno a anular es inválido");
            }
        }

        String observacionesHabilitar = request.getParameter(CTurno.OBSERVACIONES_HABILITAR);

        if (observacionesHabilitar == null || observacionesHabilitar.trim().length() == 0) {
            exception.addError("Debe digitar una observación para habilitar el turno");
        }

        Turno turno = new Turno();

        turno.setIdWorkflow(idTurno);
        turno.setObservacionesHabilitar(observacionesHabilitar);

        evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.HABILITAR_TURNO);
        evento.setTurno(turno);
        evento.setCirculo(circulo);
        evento.setObservacionesHabilitar(observacionesHabilitar);
        evento.setUsuarioNegocio(usuarioSIR);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de un
     * turno por identificador o número de matrícula
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno reanotarTurnoInicio(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        List roles = (List) session.getAttribute(WebKeys.LISTA_ROLES);
        boolean esCoordinadorJuridico = false;
        boolean esJefeOperativo = false;

        Iterator itRol = roles.iterator();
        while (itRol.hasNext()) {
            Rol rol = (Rol) itRol.next();
            if (rol.getRolId().equals(CRoles.SIR_ROL_REGISTRADOR)) {
                esCoordinadorJuridico = true;
                esJefeOperativo = true;
            } else if (rol.getRolId().equals(CRoles.SIR_ROL_COORDINADOR_JURIDICO)) {
                esCoordinadorJuridico = true;
            } else if (rol.getRolId().equals(CRoles.SIR_ROL_JEFE_OPERATIVO)) {
                esJefeOperativo = true;
            }
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_CONSULTADOS_POR_MATRICULA);
        session.removeAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_ASOCIADOS);

        EvnTrasladoTurno evento = null;
        ConsultarTurnoException exception = new ConsultarTurnoException();

        String idTurno = request.getParameter(CTurno.ID_TURNO);
        if(idTurno == null || idTurno.isEmpty()){
            idTurno = (String) request.getSession().getAttribute(CTurno.ID_TURNO);
        }
        
        session.setAttribute(CTurno.ID_TURNO, idTurno);
        session.removeAttribute(CTurno.MATRICULA_TURNO);
        if (idTurno == null || idTurno.trim().length() == 0) {
            exception.addError("Debe digitar el identificador del turno a reanotar");
        }

        if (idTurno.indexOf("-") == -1) {
            exception.addError("El turno a reanotar es inválido");
        } else {
            String[] partes = idTurno.split("-");
            if (partes.length != 4) {
                exception.addError("El turno a reanotar es inválido");
            }
            if (!partes[2].trim().equals(CProceso.PROCESO_CERTIFICADOS) && !partes[2].trim().equals(CProceso.PROCESO_REGISTRO)) {
                exception.addError("El turno a reanotar debe ser de certificados o de registro");
            }
            if (partes[2].trim().equals(CProceso.PROCESO_CERTIFICADOS) && !esJefeOperativo) {
                exception.addError("Solo el Registrador o Jefe Operativo puede reanotar Turnos de Certificados");
            }
            if (partes[2].trim().equals(CProceso.PROCESO_REGISTRO) && !esCoordinadorJuridico) {
                exception.addError("Solo el Registrador o Coordinador Jurídico puede reanotar Turnos de Registro");
            }
        }

        Turno turno = new Turno();

        turno.setIdWorkflow(idTurno);

        evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.REANOTAR_TURNO_ESPECIFICACION);
        evento.setTurno(turno);
        evento.setCirculo(circulo);
        evento.setUsuarioNegocio(usuarioSIR);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de un
     * turno por identificador o número de matrícula
     *
     * @param request
     * @return evento <code>EvnTrasladoTurno</code> con la información del
     * <code>Ciudadano</code> a consultar.
     * @throws AccionWebException
     */
    private EvnTrasladoTurno reanotarTurno(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        session.setAttribute(AWTrasladoTurno.CALIFICADOR_SELECCIONADO, request.getParameter(AWTrasladoTurno.CALIFICADOR_SELECCIONADO));
        session.setAttribute(CTipoNota.ID_TIPO_NOTA, request.getParameter(CTipoNota.ID_TIPO_NOTA));
        session.setAttribute(CNota.DESCRIPCION, request.getParameter(CNota.DESCRIPCION));
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario user = null;

        EvnTrasladoTurno evento = new EvnTrasladoTurno(usuario, EvnTrasladoTurno.REANOTAR_TURNO);

        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        ConsultarTurnoException exception = new ConsultarTurnoException();

        String calificador = request.getParameter(AWTrasladoTurno.CALIFICADOR_SELECCIONADO);
        if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO) && !turno.getIdFase().equals(CFase.FINALIZADO)) {
            if (calificador == null || calificador.trim().equals("") || calificador.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar el calificador al cual se le asignará el turno");
            } else {
                user = new gov.sir.core.negocio.modelo.Usuario();
                user.setIdUsuario(Long.parseLong(calificador));
                evento.setUsuarioCalificador(user);
            }
        }
        String tipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
        if (tipoNota == null || tipoNota.trim().equals("") || tipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el tipo de nota informativa");
        }

        String detallesNota = request.getParameter(CNota.DESCRIPCION);
        if (detallesNota == null || detallesNota.trim().equals("")) {
            exception.addError("Debe digitar la descripción de la nota informativa");
        }

        /**
         * Autor: Edgar Lora Mantis: 13898
         */
        if (detallesNota.length() < 30) {
            exception.addError("La descripcion debe contener al menos 30 caracteres, por favor digitelos.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Nota nota = new Nota();
        TipoNota tNota = new TipoNota();
        tNota.setIdTipoNota(tipoNota);
        nota.setTiponota(tNota);
        nota.setUsuario(usuarioSIR);
        nota.setDescripcion(detallesNota);

        evento.setTurno(turno);
        evento.setCirculo(circulo);
        evento.setUsuarioNegocio(usuarioSIR);
        evento.setNota(nota);

        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de limpiar la sesión luego que el usuario ha
     * terminado de usar las pantallas administrativas relacionadas con
     * Ciudadanos
     */
    private EvnTrasladoTurno limpiarSesion(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        session.removeAttribute(AWTrasladoTurno.USUARIOS_CIRCULO);
        session.removeAttribute(AWTrasladoTurno.TURNOS_CIRCULO);
        session.removeAttribute(AWTrasladoTurno.LISTA_ELEMENTOS_CIRCULO);
        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_ASOCIADOS);
        session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_CONSULTADOS_POR_MATRICULA);
        session.removeAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);

        session.removeAttribute(CCirculo.ID_CIRCULO);
        session.removeAttribute(CProceso.PROCESO_ID);
        session.removeAttribute(WebKeys.TURNO);
        session.removeAttribute("TIPO_CONSULTA");
        session.removeAttribute(CTurno.MATRICULA_TURNO);
        session.removeAttribute(CTurno.ID_TURNO);

        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_ID_CIUDADANO);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_DOCUMENTO);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_TIPO_DOCUMENTO);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_NOMBRE);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO1);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO2);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_TELEFONO);
        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_MENSAJE);

        return null;
    }

    private void save_PageItemState_Simple(String itemId, HttpServletRequest request, HttpSession session) {

        Object itemValue;
        itemValue = request.getParameter(itemId);
        session.setAttribute(itemId, itemValue);

    } // save_PageItemState

    private void delete_PageItemState_Simple(String itemId, HttpServletRequest request, HttpSession session) {

        session.removeAttribute(itemId);

    } // save_PageItemState

    private void save_PageItemsState(String[] itemIds, HttpServletRequest request, HttpSession session) {
        if (null == itemIds) {
            return;
        }

        for (int i = 0; i < itemIds.length; i++) {

            if (null == itemIds[i]) {
                continue;
            }
            if ("".equals(itemIds[i])) {
                continue;
            }

            save_PageItemState_Simple(itemIds[i], request, session);
        } // :for
    } // :save_PageItemsState

    private void delete_PageItemsState(String[] itemIds, HttpServletRequest request, HttpSession session) {
        if (null == itemIds) {
            return;
        }

        for (int i = 0; i < itemIds.length; i++) {

            if (null == itemIds[i]) {
                continue;
            }
            if ("".equals(itemIds[i])) {
                continue;
            }

            delete_PageItemState_Simple(itemIds[i], request, session);
        } // :for
    } // :save_PageItemsState

    private void save_PageItemsState_TablaHelper(String requestKey, String sessionKey, HttpServletRequest request, HttpSession session) {

        String[] local_Data;
        local_Data = request.getParameterValues(requestKey);

        if (null == local_Data) {
            return;
        }

        if (local_Data.length <= 0) {
            return;
        }

        // put data into cache
        // put data into cache
        java.util.Vector cache_Data; // Vector< Vector< String > >

        cache_Data = new java.util.Vector();
        String local_ElementId;

        // Column 1
        Vector column;
        column = new java.util.Vector();
        for (int i = 0; i < local_Data.length; i++) {

            // get local-data
            local_ElementId = local_Data[i];

            // set cache-data
            column.add(i, local_ElementId);

        }
        cache_Data = column;

        session.setAttribute(sessionKey, cache_Data);

    }

//	 ---------------------------------------------------------------------------------------
//	 ---------------------------------------------------------------------------------------
    private void
            doEndProcess_ConsultaTurno_TerminaVerDetallesTurno(HttpServletRequest request, EventoRespuesta respuesta) {
        HttpSession session;
        session = request.getSession();

        String[] itemIds;

        itemIds = new String[]{
            PARAM_OBJS_IMPRIMIBLESPENDIENTESENABLED,
            PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRE,
            PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOS,
            PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRECOUNT,
            PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOSCOUNT
        };

        delete_PageItemsState(itemIds, request, session);

    } // end-method: doEndProcess_ConsultaTurno_TerminaVerDetallesTurno 

    private void
            doEndProcess_ConsultaTurno_ConsultaTurnoPorIdentificador(HttpServletRequest request, EvnRespTrasladoTurno respuesta) {

        HttpSession session;
        session = request.getSession();

        // session data -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        Turno turno = (Turno) respuesta.getTurno();
        session.setAttribute(AWTrasladoTurno.TURNO_SELECCIONADO, turno);
        session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_ANTERIORES, respuesta.getTurnosAnteriores());
        session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_SIGUIENTES, respuesta.getTurnosSiguientes());
        session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_ASOCIADOS, respuesta.getTurnosAsociados());
        /**
         * @autor:yeferson martinez
         * @change: caso 317097
         */
        session.setAttribute(AWTrasladoTurno.OBSERVACIONES_OFICIO_PERTENENCIA, respuesta.getObsPertenencia());

//        CARLOS
        session.setAttribute(AWTrasladoTurno.LISTA_CANAL_RECAUDO_Y_CUENTAS_BANCARIAS, respuesta.getCanalRecaudoYcuentas());
        /**
         * @autor:David A Rubio J
         * @change: Añade la obserbación del testamento a la sesión
         */
        session.setAttribute(AWTrasladoTurno.SUMA_CONSERVACION, respuesta.getSumaConservacion());
        session.setAttribute(AWTrasladoTurno.VALOR_CONSERVACION_MAYOR_VALOR, respuesta.getLiquidaConservacionMayorvalor());
        session.removeAttribute(AWCorreccionCanalRecaudo.CONSTANTESIGUARDO);
        if (respuesta.getTestamento() != null) {
            session.setAttribute(AWTrasladoTurno.OBSERVACIONES_TESTAMENTO, respuesta.getTestamento().getObservacion());
            session.setAttribute(AWTrasladoTurno.TOMO_TESTAMENTO, respuesta.getTestamento().getTomo());
            session.setAttribute(AWTrasladoTurno.NUMERO_ANOTACIONES, respuesta.getTestamento().getNumeroAnotaciones());
            session.setAttribute(AWTrasladoTurno.NUMERO_COPIAS, respuesta.getTestamento().getNumeroCopias());
            session.setAttribute(AWTrasladoTurno.REVOCA_ESCRITURA, respuesta.getTestamento().getRevocaEscritura());
            session.setAttribute(AWTrasladoTurno.COPIAS_IMPRIMIR, respuesta.getTestamento().getNumeroCopias());
            session.setAttribute(AWTrasladoTurno.FECHA_CREACION, respuesta.getTestamento().getFechaCreacion());
            session.setAttribute(AWTrasladoTurno.LIST_TESTADORES, respuesta.getTestamento().getTestadores());
        }
        //
        // Bug 04810
        Map imprimiblesPendientesTurnoPadre = respuesta.getImprimiblesPendientesTurnoPadre();
        Map imprimiblesPendientesTurnoHijos = respuesta.getImprimiblesPendientesTurnoHijos();
        // int imprimiblesPendientesTurnoPadreCount = respuesta.getImprimiblesPendientesTurnoPadreCount();
        // int imprimiblesPendientesTurnoHijosCount = respuesta.getImprimiblesPendientesTurnoHijosCount();

        // Se filtran los resultados
        // filtro1:  + tipos imprimible admimitidos 
        //           + solo uno de los imprimibles de este tipo
        imprimiblesPendientesTurnoPadre = filter_ImprimiblesPendientes(imprimiblesPendientesTurnoPadre);
        imprimiblesPendientesTurnoHijos = filter_ImprimiblesPendientes(imprimiblesPendientesTurnoHijos);
        session.setAttribute(PARAM_OBJS_IMPRIMIBLESPENDIENTESENABLED, Boolean.TRUE);
        session.setAttribute(PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRE, imprimiblesPendientesTurnoPadre);
        session.setAttribute(PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOS, imprimiblesPendientesTurnoHijos);
        session.setAttribute(PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOPADRECOUNT, getSize(imprimiblesPendientesTurnoPadre));
        session.setAttribute(PARAM_OBJS_IMPRIMIBLESPENDIENTESTURNOHIJOSCOUNT, getSize(imprimiblesPendientesTurnoHijos));
        // ---------------------------------------------------------------------------------

        // request data -----------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------
    } // end-method: doEndProcess_ConsultaTurno_ConsultaTurnoPorIdentificador

    private Integer getSize(Map sourceMap) {
        if (null == sourceMap) {
            return new Integer(0);
        }
        return new Integer(sourceMap.size());
    }

    private Map filter_ImprimiblesPendientes(Map sourceMap) {

        if (null == sourceMap) {
            return null;
        }

        Map targetMap;

        targetMap = new HashMap();

        List local_TargetMap_ValueList;

        String[] acceptedTipoImprimible = new String[]{
            gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo.class.getName()
        //	, gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario.class.getName()
        };

//        local_JxSearch: {
//			String tipoImprimible01;
//			String tipoImprimible02;
//			
//			tipoImprimible01 = gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo.class.getName();
//			tipoImprimible02 = gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario.class.getName();
//			
//			final String JX_SEARCH_STRING = ".[ " 
//				                          + "tipoImprimible= '" + tipoImprimible01 + "'" + " or "
//				                          + "tipoImprimible= '" + tipoImprimible02 + "'"
//				                          + "']" ;		
//
//            JXPathContext local_Context;
//            Object        local_ContextObject;
//
//            String        local_JxSearchString;
//
//            local_JxSearchString = JX_SEARCH_STRING;
//
//            local_ContextObject = imprimiblesPendientesTurnoPadre;
//            
//            local_Context = JXPathContext.newContext( local_ContextObject );
//            
//        } // :local_JxSearch
        local_SimpleSearch:
        {

            Iterator local_MapEntryIterator;
            Map.Entry local_MapEntryElement;
            String local_MapEntryElementKey;

            java.util.List local_MapEntryElementValue;
            gov.sir.core.negocio.modelo.Imprimible local_MapEntryElementValueItem;

            local_MapEntryIterator = sourceMap.entrySet().iterator();

            boolean accept;

            for (; local_MapEntryIterator.hasNext();) {

                local_MapEntryElement = (Map.Entry) local_MapEntryIterator.next();
                local_MapEntryElementKey = (java.lang.String) local_MapEntryElement.getKey();
                local_MapEntryElementValue = (java.util.List) local_MapEntryElement.getValue();

                if ((null != (local_MapEntryElementValue))
                        && (local_MapEntryElementValue.size() > 0)) {

                    local_MapEntryElementValueItem = null;

                    accept = false;
                    // escoger el primero de 
                    // cualquiiera de los tipos aceptados
                    for (Iterator iterator = local_MapEntryElementValue.iterator(); iterator.hasNext();) {

                        local_MapEntryElementValueItem = (gov.sir.core.negocio.modelo.Imprimible) iterator.next();
                        if (null == local_MapEntryElementValueItem) {
                            continue;
                        }
                        // tipos aceptados
                        for (int i = 0; i < acceptedTipoImprimible.length; i++) {
                            if (acceptedTipoImprimible[i].equals(local_MapEntryElementValueItem.getTipoImprimible())) {
                                accept = true;
                                break;
                            }
                        }

                    } // for

                    if ((accept) && (null != local_MapEntryElementValueItem)) {
                        local_TargetMap_ValueList = new ArrayList();
                        local_TargetMap_ValueList.add(local_MapEntryElementValueItem);
                        targetMap.put(local_MapEntryElementKey, local_TargetMap_ValueList);
                    } // if

                } // if

            } // for

        } // :local_SimpleSearch

        return targetMap;

    } // end-method: filter_ImprimiblesPendientes

    /**
     * Imprime la nota devolutiva del turno de registro
     */
    private EvnNotas imprimirNotaDevolutiva(HttpServletRequest request) throws ValidacionParametrosException {

        ValidacionParametrosException exception = new ValidacionParametrosException();

        List notas = null;
        List notasImpresion = new Vector();
        Nota nota = null;

        String valCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
        int copias = 1;

        if (valCopias != null) {
            try {
                copias = Integer.valueOf(valCopias).intValue();
                if (copias < 1) {
                    exception.addError("El número de copias es inválido");
                }
            } catch (Exception e) {
                exception.addError("El número de copias es inválido");
            }
        }
        if (valCopias == null || valCopias.equals("")) {
            exception.addError("Por favor ingrese el numero de copias a imprimir.");
        }

        //1. Si existe un turno, se recupera la lista de notas informativas asociada 
        //con el turno.
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        if (turno != null) {
            notas = ((Turno) request.getSession().getAttribute(WebKeys.TURNO)).getNotas();
        } //2. Si el turno no ha sido creado, se recupera de sesión la lista de notas informativas. 
        else {
            List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);

            //2.1 Si la lista no es vacía se obtienen las notas informativas.
            if (listaNotas != null) {
                notas = listaNotas;
            }
        }
        //Si se requiere la impresion de notas devolutivas
        //if(request.getParameter(WebKeys.NOTA_DEVOLUTIVA)){
        List listaAuxNotasDevolutivas = new ArrayList();
        for (int i = 0; i < notas.size(); i++) {
            Nota notaAux = (Nota) notas.get(i);
            if (notaAux != null && notaAux.getIdFase().equals(CFase.CAL_CALIFICACION)) {
                TipoNota tipoNota = notaAux.getTiponota();
                if (tipoNota != null) {
                    if (tipoNota.isDevolutiva()) {
                        String idTipoNota = notaAux.getTiponota().getIdTipoNota();
                        String idTipoNotaPadre = "";
                        if (!idTipoNota.equals("999") && !idTipoNota.substring(1, 3).equals("50")
                                && !idTipoNota.substring(1, 3).equals("00")) {
                                try{
                                    int separador = Integer.parseInt(notaAux.getTiponota().getIdTipoNota().substring(1, 2));
                                    if (separador >= 5) {
                                        separador = 5;
                                    } else {
                                        separador = 0;
                                    }
                                idTipoNotaPadre = notaAux.getTiponota().getIdTipoNota().substring(0, 1) + Integer.toString(separador) + "0";
                                
                            }catch(NumberFormatException ex){
                               
                            }
                        }
                        String complemento = "(" + (idTipoNotaPadre.equals("") ? "" : idTipoNotaPadre + " ") + idTipoNota + ")";
                        notaAux.getTiponota().setDescripcion(notaAux.getTiponota().getDescripcion() + complemento);
                        listaAuxNotasDevolutivas.add(notaAux);
                    }
                }
            }
        }
        notas = listaAuxNotasDevolutivas;

        if (notas == null) {
            notas = new Vector();
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        for (int j = 0; j < notas.size(); j++) {
            nota = (Nota) notas.get(j);
            if (turno != null) {
                nota.setTurno(turno);
            }
            notasImpresion.add(nota);
        }

        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        Usuario usuarioAuriga = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        String UID = request.getSession().getId();

        EvnNotas evn = new EvnNotas(usuarioAuriga, EvnNotas.IMPRIMIR_NOTA_INFORMATIVA, notasImpresion, UID, circulo, turno, usuarioSIR);
        evn.setNumCopiasImpresion(copias);
        evn.setImprimirYN(copias);
        return evn;
    }

    /* @author : CTORRES
                 * @change : Nuevo metodo verDetallesTurnoTestamento
                 * Caso Mantis : 12291
                 * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
    private EvnTrasladoTurno verDetallesTurnoTestamento(HttpServletRequest request) throws ValidacionParametrosException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        if (request.getParameter(CFolio.ID_MATRICULA) == null) {
            exception.addError("No se ha seleccionado turno");
            throw exception;
        }

        request.getSession().setAttribute("editable", false);

        Turno turno = (Turno) request.getSession().getAttribute(AWTrasladoTurno.TURNO_SELECCIONADO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnTrasladoTurno evento = new EvnTrasladoTurno(usuarioAuriga, VER_DETALLES_TURNO_TESTAMENTO);
        evento.setTurno(turno);

        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de manejar el evento de respuesta proveniente de
     * la acción de negocio. Sube datos a sesión de acuerdo al tipo de respuesta
     * proveniente en el evento
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        if (evento instanceof EvnRespTrasladoTurno) {
            EvnRespTrasladoTurno respuesta = (EvnRespTrasladoTurno) evento;
            String r = respuesta.getLiquidaConservacionMayorvalor();
            double valorConservacionMayorValor = 0;
            if (r != null && !r.equalsIgnoreCase("null")) {
                valorConservacionMayorValor = Double.parseDouble(r);
            }
            Log.getInstance().debug(AWTrasladoTurno.class, "yefrson el valor de valorConservacionMayorValor es " + valorConservacionMayorValor);
            if (respuesta != null) {
                String tipoEvento = respuesta.getTipoEvento();
                if (tipoEvento.equals(EvnRespTrasladoTurno.CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO_OK)) {
                    List usuarios = (List) respuesta.getPayload();
                    List turnos = respuesta.getTurnos();

                    List elementosUsuario = new ArrayList();
                    for (Iterator iter = usuarios.iterator(); iter.hasNext();) {
                        gov.sir.core.negocio.modelo.Usuario usuario
                                = (gov.sir.core.negocio.modelo.Usuario) iter.next();
                        String nombre = usuario.getUsername();
                        elementosUsuario.add(new ElementoLista(usuario.getIdUsuario() + "", nombre));
                    }
                    session.setAttribute(AWTrasladoTurno.USUARIOS_CIRCULO, elementosUsuario);

                    List elementosTurno = new ArrayList();
                    for (Iterator iter = turnos.iterator(); iter.hasNext();) {
                        Turno turno = (Turno) iter.next();
                        elementosTurno.add(new ElementoLista(turno.getIdWorkflow(), turno.getIdWorkflow()));
                    }
                    session.setAttribute(AWTrasladoTurno.TURNOS_CIRCULO, elementosTurno);
                    return;
                } else if (tipoEvento.equals(EvnRespTrasladoTurno.CONSULTAR_TURNOS_POR_MATRICULA_OK)) {
                    List turnos = (List) respuesta.getPayload();
                    session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_CONSULTADOS_POR_MATRICULA, turnos);
                    return;
                } else if (tipoEvento.equals(EvnRespTrasladoTurno.CONSULTAR_TURNOS_POR_IDENTIFICADOR_OK)) {
//                    System.out.println("CONSULTA POR IDENTIFICACION ");
//                    System.out.println("HAY ERROR EN LA CONSULTA: " + String.valueOf(respuesta.isExiste_error_evento()));
//                    if (respuesta.isExiste_error_evento()) {
//                        System.out.println("HAY ERROR EN LA CONSULTA LIMPIA SESSION ");
//                        List listaTurnosSession = (List) session.getAttribute("TURNOS_DERIVADOS");
//                        listaTurnosSession.remove(listaTurnosSession.size() - 1);
//                        System.out.println("LISTA TURNOS TAMAÑO REGRESAR_TURNO " + listaTurnosSession.size());
//                    }
                    Turno turno = (Turno) respuesta.getTurno();
                    List liquidaciones = turno.getSolicitud().getLiquidaciones();
                    if (liquidaciones == null) {
                        liquidaciones = new ArrayList();
                    }
                    Iterator itPagos = liquidaciones.iterator();
                    /**
                     * Author: Santiago Vásquez Change:
                     * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                     */
                    Boolean devolucion = Boolean.FALSE;
                    Boolean mayorValor = Boolean.FALSE;
                    ArrayList liquidacionesCorreciones = new ArrayList();
                    Map resumenPagos = new HashMap();
                    Map resumenMayorValor = new HashMap();
                    List listaMayorValor = new ArrayList();
                    for (int i = 0; i < liquidaciones.size(); i++) {
                        Liquidacion liq = (Liquidacion) liquidaciones.get(i);
                        LiquidacionTurnoRegistro liqReg = null;
                        if (liq instanceof LiquidacionTurnoRegistro) {
                            liqReg = (LiquidacionTurnoRegistro) liq;
                            if (liqReg.getJustificacionMayorValor() != null && liqReg.getJustificacionMayorValor().length() > 0) {
                                Double valorCerMV = new Double(liqReg.getValor() - (liqReg.getValorDerechos() + liqReg.getValorImpuestos()) - valorConservacionMayorValor);
                                String razonPago = liqReg.getJustificacionMayorValor();
                                String derechosRegistro = java.text.NumberFormat.getInstance().format(liqReg.getValorDerechos());
                                String certificadosAsoc = java.text.NumberFormat.getInstance().format(valorCerMV);
                                String impuestos = java.text.NumberFormat.getInstance().format(liqReg.getValorImpuestos());
                                String valorPagado = java.text.NumberFormat.getInstance().format(liqReg.getValor());
                                listaMayorValor.add(razonPago);
                                listaMayorValor.add(derechosRegistro);
                                listaMayorValor.add(certificadosAsoc);
                                listaMayorValor.add(impuestos);
                                listaMayorValor.add(valorPagado);
                                Pago pagoAux = liqReg.getPago();
                                if (pagoAux != null) {
                                    List listaAplicaciones = pagoAux.getAplicacionPagos();
                                    if (listaAplicaciones != null) {
                                        resumenMayorValor = resumirFormasDePago(listaAplicaciones, turno);
                                        mayorValor = Boolean.TRUE;
                                    }
                                }
                            } else {
                                Pago pagoAux = liqReg.getPago();
                                if (pagoAux != null) {
                                    List listaAplicaciones = pagoAux.getAplicacionPagos();
                                    if (listaAplicaciones != null) {
                                        resumenPagos = resumirFormasDePago(listaAplicaciones, turno);
                                    }
                                }
                            }
                        } else if (liq instanceof LiquidacionTurnoCertificado) {
                            LiquidacionTurnoCertificado liqCer = (LiquidacionTurnoCertificado) liq;
                            if (liqCer.getJustificacionMayorValor() != null && liqCer.getJustificacionMayorValor().length() > 0) {
                                Pago pagoAux = liqCer.getPago();
                                if (pagoAux != null) {
                                    List listaAplicaciones = pagoAux.getAplicacionPagos();
                                    if (listaAplicaciones != null) {
                                        resumenMayorValor = resumirFormasDePago(listaAplicaciones, turno);
                                        mayorValor = Boolean.TRUE;
                                    }
                                }
                            } else {
                                Pago pagoAux = liqCer.getPago();
                                if (pagoAux != null) {
                                    List listaAplicaciones = pagoAux.getAplicacionPagos();
                                    if (listaAplicaciones != null) {
                                        for (int k = 0; k < listaAplicaciones.size(); k++) {
                                            AplicacionPago apPago = (AplicacionPago) listaAplicaciones.get(k);
                                            if (apPago != null) {
                                                DocumentoPago doc = apPago.getDocumentoPago();
                                                if (doc != null) {
                                                    if (doc.getTipoPago().equals(DocumentoPago.PAGO_HEREDADO)) {
                                                        devolucion = Boolean.TRUE;
                                                    }
                                                }
                                            }
                                        }
                                        resumenPagos = resumirFormasDePago(listaAplicaciones, turno);
                                    }
                                }
                            }
                            /**
                             * @author Santiago Vasquez
                             * @change:
                             * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                             *
                             */
                        } else if (liq instanceof LiquidacionTurnoConsulta) {
                            LiquidacionTurnoConsulta liqCer = (LiquidacionTurnoConsulta) liq;
                            Pago pagoAux = liqCer.getPago();
                            if (pagoAux != null) {
                                List listaAplicaciones = pagoAux.getAplicacionPagos();
                                if (listaAplicaciones != null) {
                                    resumenPagos = resumirFormasDePago(listaAplicaciones, turno);
                                }
                            }
                        } else if (liq instanceof LiquidacionTurnoCorreccion) {
                            LiquidacionTurnoCorreccion liqCor = (LiquidacionTurnoCorreccion) liq;
                            Pago pagoAux = liqCor.getPago();
                            if (liqCor.getJustificacionMayorValor() != null && liqCor.getJustificacionMayorValor().length() > 0) {
                                Double valorCerMV = new Double(liqCor.getValor() - (liqCor.getValorDerechos() + liqCor.getValorImpuestos()) - valorConservacionMayorValor);
                                String razonPago = liqCor.getJustificacionMayorValor();
                                String derechosRegistro = java.text.NumberFormat.getInstance().format(liqCor.getValorDerechos());
                                String certificadosAsoc = java.text.NumberFormat.getInstance().format(valorCerMV);
                                String impuestos = java.text.NumberFormat.getInstance().format(liqCor.getValorImpuestos());
                                String valorPagado = java.text.NumberFormat.getInstance().format(liqCor.getValor());
                                listaMayorValor.add(razonPago);
                                listaMayorValor.add(derechosRegistro);
                                listaMayorValor.add(certificadosAsoc);
                                listaMayorValor.add(impuestos);
                                listaMayorValor.add(valorPagado);
                                if (pagoAux != null) {
                                    List listaAplicaciones = pagoAux.getAplicacionPagos();
                                    if (listaAplicaciones != null) {
                                        liquidacionesCorreciones.add(liq);
                                        resumenMayorValor = resumirFormasDePago(listaAplicaciones, turno);
                                        devolucion = Boolean.FALSE;
                                        mayorValor = Boolean.TRUE;
                                    }
                                }
                            } else {
                                if (pagoAux != null) {
                                    List listaAplicaciones = pagoAux.getAplicacionPagos();
                                    if (listaAplicaciones != null) {
                                        liquidacionesCorreciones.add(0, liq);
                                        resumenPagos = resumirFormasDePago(listaAplicaciones, turno);
                                        devolucion = Boolean.TRUE;
                                    }
                                }
                            }
                        } else if (liq instanceof LiquidacionTurnoFotocopia) {
                            LiquidacionTurnoFotocopia liqFot = (LiquidacionTurnoFotocopia) liq;
                            Pago pagoAux = liqFot.getPago();
                            if (pagoAux != null) {
                                List listaAplicaciones = pagoAux.getAplicacionPagos();
                                if (listaAplicaciones != null) {
                                    resumenPagos = resumirFormasDePago(listaAplicaciones, turno);
                                }
                            }
                        } else if (liq instanceof LiquidacionTurnoCertificadoMasivo) {
                            LiquidacionTurnoCertificadoMasivo liqCerMas = (LiquidacionTurnoCertificadoMasivo) liq;
                            Pago pagoAux = liqCerMas.getPago();
                            if (pagoAux != null) {
                                List listaAplicaciones = pagoAux.getAplicacionPagos();
                                if (listaAplicaciones != null) {
                                    resumenPagos = resumirFormasDePago(listaAplicaciones, turno);
                                }
                            }
                        } else {
                            devolucion = Boolean.TRUE;
                        }
                    }
                    if (mayorValor.booleanValue()) {
                        devolucion = Boolean.FALSE;
                    }
                    session.setAttribute(AWTrasladoTurno.DEVOLUCION, devolucion);
                    session.setAttribute(AWTrasladoTurno.MAYOR_VALOR, mayorValor);
                    if (!liquidacionesCorreciones.isEmpty()) {
                        session.setAttribute(AWTrasladoTurno.LIQUIDACIONES, liquidacionesCorreciones);
                    } else {
                        session.removeAttribute(AWTrasladoTurno.LIQUIDACIONES);
                    }
                    if (!devolucion.booleanValue()) {
                        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO, resumenPagos.get(DocumentoPago.PAGO_TARJETA_CREDITO));
                        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO, resumenPagos.get(DocumentoPago.PAGO_TARJETA_DEBITO));
                        session.setAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE, resumenPagos.get(DocumentoPago.PAGO_ELECTRONICO_PSE));
                        session.setAttribute(AWTrasladoTurno.LISTA_EFECTIVO, resumenPagos.get(DocumentoPago.PAGO_EFECTIVO));
                        session.setAttribute(AWTrasladoTurno.LISTA_CONVENIO, resumenPagos.get(DocumentoPago.PAGO_CONVENIO));
                        session.setAttribute(AWTrasladoTurno.LISTA_CONSIGNACION, resumenPagos.get(DocumentoPago.PAGO_CONSIGNACION));
                        session.setAttribute(AWTrasladoTurno.LISTA_CHEQUE, resumenPagos.get(DocumentoPago.PAGO_CHEQUE));

                        session.setAttribute(AWTrasladoTurno.LISTA_GENERAL, resumenPagos.get(DocumentoPago.PAGO_GENERAL));
                        session.setAttribute(AWTrasladoTurno.LISTA_GENERAL_VM, resumenMayorValor.get(DocumentoPago.PAGO_GENERAL));
                        
                        session.setAttribute(AWTrasladoTurno.LISTA_MAYOR_VALOR, listaMayorValor);
                        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO_VM, resumenMayorValor.get(DocumentoPago.PAGO_TARJETA_CREDITO));
                        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO_VM, resumenMayorValor.get(DocumentoPago.PAGO_TARJETA_DEBITO));
                        session.setAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE_VM, resumenMayorValor.get(DocumentoPago.PAGO_ELECTRONICO_PSE));
                        session.setAttribute(AWTrasladoTurno.LISTA_EFECTIVO_VM, resumenMayorValor.get(DocumentoPago.PAGO_EFECTIVO));
                        session.setAttribute(AWTrasladoTurno.LISTA_CONVENIO_VM, resumenMayorValor.get(DocumentoPago.PAGO_CONVENIO));
                        session.setAttribute(AWTrasladoTurno.LISTA_CONSIGNACION_VM, resumenMayorValor.get(DocumentoPago.PAGO_CONSIGNACION));
                        session.setAttribute(AWTrasladoTurno.LISTA_CHEQUE_VM, resumenMayorValor.get(DocumentoPago.PAGO_CHEQUE));
                    } else {
                        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_EFECTIVO, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_CONVENIO, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_CONSIGNACION, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_CHEQUE, new ArrayList());

                        session.setAttribute(AWTrasladoTurno.LISTA_GENERAL, new ArrayList());

                        session.setAttribute(AWTrasladoTurno.LISTA_MAYOR_VALOR, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_CREDITO_VM, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_TARJETA_DEBITO_VM, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_PAGOS_PSE_VM, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_EFECTIVO_VM, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_CONVENIO_VM, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_CONSIGNACION_VM, new ArrayList());
                        session.setAttribute(AWTrasladoTurno.LISTA_CHEQUE_VM, new ArrayList());
                        
                        session.setAttribute(AWTrasladoTurno.LISTA_GENERAL_VM, new ArrayList());
                    }

                    session.setAttribute(AWTrasladoTurno.TURNO_SELECCIONADO, turno);
                    session.setAttribute(WebKeys.TURNO, turno);
                    session.setAttribute(AWTrasladoTurno.ID_AMINISTRADOR_SAS_TURNO_ASOCIADO, respuesta.getIdAdministradorSAS());
                    session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_ANTERIORES, respuesta.getTurnosAnteriores());
                    session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_SIGUIENTES, respuesta.getTurnosSiguientes());
                    session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_ASOCIADOS, respuesta.getTurnosAsociados());
                    session.setAttribute(AWTrasladoTurno.TURNO_HIJO, respuesta.getTurnoDerivado());
                    session.setAttribute(AWTrasladoTurno.TURNO_PADRE, respuesta.getTurnoPadre());
                    session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_FOLIO_MIG, respuesta.getListaTurnoFolioMig());
                    session.setAttribute(AWTrasladoTurno.CATEGORIA_MINUTA, respuesta.getCategoriaMinuta());
                    //TODO: borrar el atributo cuando no se necesita
                    session.setAttribute(AWTrasladoTurno.RESOLUCIONES, respuesta.getResoluciones());

                    /*
                                         * se guarda en session el listado de turnos de devolución
                                         * @author: Julio Alcazar
                                         * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
                     */
                    session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_DEVOLUCION, respuesta.getTurnosDevoluciones());

                    /*
                    @change Se guarda en la sesión la cadena con las observaciones del testamento
                    @author David Andrés Rubio J*/
                    if (respuesta.getTestamento() != null) {
                        session.setAttribute(AWTrasladoTurno.OBSERVACIONES_TESTAMENTO, respuesta.getTestamento().getObservacion());
                        session.setAttribute(AWTrasladoTurno.TOMO_TESTAMENTO, respuesta.getTestamento().getTomo());
                        session.setAttribute(AWTrasladoTurno.NUMERO_ANOTACIONES, respuesta.getTestamento().getNumeroAnotaciones());
                        session.setAttribute(AWTrasladoTurno.NUMERO_COPIAS, respuesta.getTestamento().getNumeroCopias());
                        session.setAttribute(AWTrasladoTurno.REVOCA_ESCRITURA, respuesta.getTestamento().getRevocaEscritura());
                        session.setAttribute(AWTrasladoTurno.COPIAS_IMPRIMIR, respuesta.getTestamento().getNumeroCopias());
                        session.setAttribute(AWTrasladoTurno.FECHA_CREACION, respuesta.getTestamento().getFechaCreacion());
                        session.setAttribute(AWTrasladoTurno.LIST_TESTADORES, respuesta.getTestamento().getTestadores());
                    } else {
                        session.setAttribute(AWTrasladoTurno.OBSERVACIONES_TESTAMENTO, "");
                        session.setAttribute(AWTrasladoTurno.TOMO_TESTAMENTO, "");
                        session.setAttribute(AWTrasladoTurno.NUMERO_ANOTACIONES, "");
                        session.setAttribute(AWTrasladoTurno.NUMERO_COPIAS, "");
                        session.setAttribute(AWTrasladoTurno.REVOCA_ESCRITURA, "");
                        session.setAttribute(AWTrasladoTurno.COPIAS_IMPRIMIR, "");
                        session.setAttribute(AWTrasladoTurno.FECHA_CREACION, null);
                        session.setAttribute(AWTrasladoTurno.LIST_TESTADORES, new ArrayList());
                    }

                    if (respuesta.isTurnoInternet()) {
                        Boolean turnoInternet = new Boolean(true);
                        session.setAttribute(AWTrasladoTurno.ES_TURNO_INTERNET, turnoInternet);
                    }
                    doEndProcess_ConsultaTurno_ConsultaTurnoPorIdentificador(request, respuesta);
                    return;
                } else if (tipoEvento.equals(EvnRespTrasladoTurno.ANULAR_TURNO_POR_IDENTIFICADOR_OK)) {
                    Turno turno = (Turno) respuesta.getTurno();
                    session.setAttribute(WebKeys.TURNO, turno);
                    session.removeAttribute(CTurno.ID_TURNO);
                    session.removeAttribute(CTurno.OBSERVACIONES_ANULACION);
                    return;
                } else if (tipoEvento.equals(EvnRespTrasladoTurno.HABILITAR_TURNO_POR_IDENTIFICADOR_OK)) {
                    Turno turno = (Turno) respuesta.getTurno();
                    session.setAttribute(WebKeys.TURNO, turno);
                    session.removeAttribute(CTurno.ID_TURNO);
                    session.removeAttribute(CTurno.OBSERVACIONES_HABILITAR);
                    return;
                } else if (tipoEvento.equals(EvnRespTrasladoTurno.TURNO_CERTIFICADO_CAMBIO_MATRICULA)) {
                    Turno turno = (Turno) respuesta.getTurno();
                    Folio folio = (Folio) respuesta.getFolio();
                    session.setAttribute(WebKeys.TURNO, turno);
                    session.setAttribute(WebKeys.FOLIO, folio);
                    session.setAttribute(WebKeys.LISTA_TIPOS_NOTAS, respuesta.getListaTipoNota());
                    Proceso proceso = new Proceso();
                    /*
                                         * @author      :   Julio Alcázar Rivas
                                         * @change      :   se cambia el idProceso a 10 el cual corresponde al proceso Reimpresion_Certificados
                                         * Caso Mantis  :   02359
                     */
                    proceso.setIdProceso(10);
                    session.setAttribute(WebKeys.PROCESO, proceso);
                    session.removeAttribute(CTurno.ID_TURNO);
                    session.removeAttribute(CTurno.OBSERVACIONES_ANULACION);

                    return;
                } else if (tipoEvento.equals(EvnRespTrasladoTurno.CANCELAR_CAMBIO_MATRICULA)) {
                    session.removeAttribute(CTurno.ID_TURNO);
                    session.removeAttribute(CTurno.OBSERVACIONES_ANULACION);
                    session.removeAttribute(WebKeys.TURNO);
                    session.removeAttribute(WebKeys.FOLIO);
                    session.removeAttribute(AWTrasladoTurno.PROCESO_EXITOSO_CAMBIO_MATRICULA);
                    return;
                } else if (tipoEvento.equals(EvnRespTrasladoTurno.VALIDAR_CIUDADANO_EDICION_OK)) {
                    Ciudadano ciudadanoToEdit = respuesta.getCiudadanoToEdit();
                    if (ciudadanoToEdit != null) {
                        session.setAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT, ciudadanoToEdit);
                        session.setAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_ID_CIUDADANO, ciudadanoToEdit.getIdCiudadano());
                        session.setAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_DOCUMENTO, ciudadanoToEdit.getDocumento());
                        session.setAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_TIPO_DOCUMENTO, ciudadanoToEdit.getTipoDoc());
                        session.setAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_NOMBRE, ciudadanoToEdit.getNombre());
                        session.setAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO1, ciudadanoToEdit.getApellido1());
                        session.setAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO2, ciudadanoToEdit.getApellido2());
                        session.setAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_TELEFONO, ciudadanoToEdit.getTelefono());
                        session.setAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_MENSAJE, respuesta.getMensajeEdicion());
                    } else {
                        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT);
                        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_ID_CIUDADANO);
                        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_DOCUMENTO);
                        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_TIPO_DOCUMENTO);
                        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_NOMBRE);
                        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO1);
                        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_APELLIDO2);
                        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_TELEFONO);
                        session.removeAttribute(AWTrasladoTurno.CIUDADANO_TO_EDIT_MENSAJE);
                    }
                    return;
                } else if (tipoEvento.equals(EvnRespTrasladoTurno.REANOTAR_TURNO_ESPECIFICACION_OK)) {
                    Turno turno = (Turno) respuesta.getTurno();
                    List turnosSiguientes = respuesta.getTurnosSiguientes();
                    session.setAttribute(WebKeys.TURNO, turno);
                    session.setAttribute(AWTrasladoTurno.LISTA_TURNOS_SIGUIENTES, turnosSiguientes);
                    session.setAttribute(WebKeys.LISTA_TIPOS_NOTAS, respuesta.getListaTipoNota());
                    session.setAttribute(AWTrasladoTurno.LISTA_CALIFICADORES, respuesta.getCalificadores());
                    return;
                } else if (tipoEvento.equals(EvnTrasladoTurno.REANOTAR_TURNO)) {
                    session.setAttribute(WebKeys.TURNO, respuesta.getTurno());
                    session.removeAttribute(AWTrasladoTurno.LISTA_TURNOS_SIGUIENTES);
                    session.removeAttribute(WebKeys.LISTA_TIPOS_NOTAS);
                    session.removeAttribute(AWTrasladoTurno.LISTA_CALIFICADORES);
                    session.removeAttribute(AWTrasladoTurno.CALIFICADOR_SELECCIONADO);
                    session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
                    session.removeAttribute(CNota.DESCRIPCION);
                    session.removeAttribute(CTurno.ID_TURNO);
                    return;
                    /* @author : CTORRES
                 * @change : Se agrega condicion para el tipo de evento CARGAR_TESTAMENTO_OK
                 * Caso Mantis : 12291
                 * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                     */
                } else if (tipoEvento.equals(EvnRespTrasladoTurno.CARGAR_TESTAMENTO_OK)) {
                    Turno turno = (Turno) respuesta.getTurno();
                    session.setAttribute(WebKeys.TURNO, turno);
                    session.removeAttribute(CTurno.ID_TURNO);
                    session.removeAttribute(CTurno.OBSERVACIONES_ANULACION);
                    Map salvedad = respuesta.getSalvedadTest();
                    SolicitudRegistro solicitud = new SolicitudRegistro();
                    request.getSession().setAttribute(WebKeys.TESTAMENTO_SESION, respuesta.getTestamento());
                    if (salvedad != null) {
                        request.getSession().setAttribute("SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID", salvedad.get("SLTS_DESCRIPCION"));
                    } else {
                        request.getSession().setAttribute("SALVEDAD_TURNO_TESTAMENTO_DESCRICION_ID", null);
                    }
                    if (respuesta.getDocumento() != null) {
                        solicitud.setDocumento(respuesta.getDocumento());
                        request.getSession().setAttribute(CTestamentos.SOLICITUD_MODIFICADA, solicitud);
                    } else {
                        request.getSession().setAttribute(CTestamentos.SOLICITUD_MODIFICADA, null);
                    }

                    return;
                }
            }
        } else if (evento instanceof EvnRespConsultaFolio) {
            EvnRespConsultaFolio respuesta = (EvnRespConsultaFolio) evento;
            if (respuesta != null) {
                String tipoEvento = respuesta.getTipoEvento();
                if (tipoEvento.equals(EvnRespConsultaFolio.CONSULTAR_FOLIO)) {

                    session.setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                    session.setAttribute(WebKeys.MAYOR_EXTENSION_FOLIO, new Boolean(respuesta.isEsMayorExtension()));
                    session.setAttribute(WebKeys.TOTAL_ANOTACIONES, new BigDecimal(respuesta.getNumeroAnotaciones()));

                    session.setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN, respuesta.getGravamenes());
                    session.setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES, respuesta.getMedidasCautelares());

                    session.setAttribute(WebKeys.LISTA_FOLIOS_PADRE, respuesta.getFoliosPadre());
                    session.setAttribute(WebKeys.LISTA_FOLIOS_HIJO, respuesta.getFoliosHijo());
                    session.setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES, respuesta.getSalvedadesAnotaciones());
                    session.setAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION, respuesta.getCancelaciones());

                    session.setAttribute(WebKeys.TURNO_TRAMITE, respuesta.getTurnoTramite());
                    session.setAttribute(WebKeys.TURNO_DEUDA, respuesta.getTurnoDeuda());
                    session.setAttribute(WebKeys.USUARIO_BLOQUEO, respuesta.getUsuarioBloqueo());
                    session.setAttribute(WebKeys.RECARGA, new Boolean(true));

                    /**
                     * @author : Carlos Torres Caso Mantis : 14985: Acta -
                     * Requerimiento
                     * 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                     */
                    session.setAttribute(WebKeys.TRASLADO_DESTINO, respuesta.getTrasladoDestino());
                    session.setAttribute(WebKeys.TRASLADO_ORIGEN, respuesta.getTrasladoOrigen());
                    session.setAttribute(WebKeys.CIRCULO_DESTINO, respuesta.getCirculoDestino());
                    session.setAttribute(WebKeys.CIRCULO_ORIGEN, respuesta.getCirculoOrigen());

                    if (respuesta.getFoliosDerivadoHijo() != null) {
                        session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO, respuesta.getFoliosDerivadoHijo());
                    }
                }
            }
        } else if (evento instanceof EvnRespCambioMatricula) {
            request.getSession().removeAttribute(WebKeys.FOLIO);
            request.getSession().removeAttribute(FOLIO_MATRICULA_ID);
            request.getSession().removeAttribute(WebKeys.TURNO);
            request.getSession().removeAttribute(AWAdminNotas.NOTA_AGREGADA_ADMIN);
        } else if (null == evento) {

            /*System.out.println("EVENTO NULL ");
            List listaTurnos = null;
            listaTurnos = new ArrayList();
            listaTurnos.add(request.getParameter(CTurno.ID_TURNO));
            session.setAttribute("TURNOS_DERIVADOS", listaTurnos);*/
            // acciones para limpiar estado, sin ir a accion de negocio
            String local_ParamAction;
            local_ParamAction = Nvl(request.getParameter(WebKeys.ACCION), getNullableString(true));

            if (TERMINA_VER_DETALLES_TURNO.equals(local_ParamAction)) {
                request.getSession().removeAttribute(WebKeys.TURNO);
                doEndProcess_ConsultaTurno_TerminaVerDetallesTurno(request, evento);
            }

        } // if
    }

//	 -----------------------------------------------------------------------------	
    public static String
            Nvl(String string, String replaceIfNull) {
        return (null == string) ? (replaceIfNull) : (string);
    } // end-method: Nvl

    public static String
            getNullableString(boolean treatBlankAsNull) {
        return ((treatBlankAsNull) ? ("") : (null));
    } // end-method: Nvl

//	 -----------------------------------------------------------------------------
    /**
     * Author: Santiago Vásquez Change:
     * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
     */
    private String encontrarBancosFranquicia(List bancosFranquicias, String idBanco, String idBancoFranquicia) {
        String datosBancoFranquicia = "";
        String nombreBancoFranquicia = "";
        for (Object datos : bancosFranquicias) {
            datosBancoFranquicia = (String) datos;
            String[] arrayDatosBancoFranquicia = datosBancoFranquicia.split(",");
            if (arrayDatosBancoFranquicia[0].equals(idBanco) && arrayDatosBancoFranquicia[1].equals(idBancoFranquicia)) {
                nombreBancoFranquicia = arrayDatosBancoFranquicia[2];
                break;
            }
        }
        return nombreBancoFranquicia;
    }

    private Map<String, List> resumirFormasDePago(List listaAplicaciones, Turno turno) {
        Map<String, List> resumen = new HashMap<String, List>();
        JDOTurnosDAO jdoTurnosDAO = new JDOTurnosDAO ();
        JDOPagosDAO jdoPagosDAO = new JDOPagosDAO ();
        List bancosFranquicias = (List) context.getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS);
        List listaTarjetaCredito = new ArrayList();
        List listaTarjetaDebito = new ArrayList();
        List listaPagoElecPse = new ArrayList();
        List listaEfectivo = new ArrayList();
        List listaConvenio = new ArrayList();
        List listaConsignacion = new ArrayList();
        List listaCheque = new ArrayList();
        CanalesRecaudo canalesRecaudo = null;
        int idCtpTest = 0;
        
        DocumentoPago aux = null; 
        
        List listaGeneral = new ArrayList();
        
        HermodService hs;
        ForsetiService fs;
        for (int k = 0; k < listaAplicaciones.size(); k++) {
            AplicacionPago apPago = (AplicacionPago) listaAplicaciones.get(k);
            if (apPago != null) {
                DocumentoPago doc = apPago.getDocumentoPago();
                if (doc != null) {
                    if(doc instanceof DocPagoGeneral){
                        DocPagoGeneral generalTest = (DocPagoGeneral) doc; 
                        idCtpTest = generalTest.getIdCtp();
                    }
                    if(doc instanceof DocPagoGeneral && idCtpTest != 0){
                        DocPagoGeneral general = (DocPagoGeneral) doc; 
                            String nombreBanco = null;
                            String nroDocPago = null;
                            String nombreBancoFranquicia = null;    
                            String idCr = null;
                            String tipoPago = null;
                            String nombreCanal = null;
                            String noConsignacion = null;
                            String idCb = null;
                            String cuentaBancaria = null;
                            String bancoId = null;
                            String bancoNombre = null;
                            if(general.getBanco() != null){
                                nombreBanco = general.getBanco().getNombre();
                            }
                            if(general.getBancoFranquicia() != null){
                                nombreBancoFranquicia = encontrarBancosFranquicia(bancosFranquicias, general.getBancoFranquicia().getIdBanco(), Integer.toString(general.getBancoFranquicia().getIdTipoFranquicia()));
                            }
                            if(general.getNoConsignacion() != null){
                                noConsignacion = general.getNoConsignacion();
                            }
                            if(general.getNoDocumento() != null){
                                nroDocPago = general.getNoDocumento();
                            }

                            int idCtp = general.getIdCtp();
                            tipoPago = general.getTipoPago();
                            try {
                                hs = HermodService.getInstance();
                                idCr = jdoTurnosDAO.encontrarIdCrByIdCtp(String.valueOf(idCtp));
                                canalesRecaudo = hs.getCanalRecaudoByID(Integer.parseInt(idCr));
                                if(canalesRecaudo != null){
                                    nombreCanal = canalesRecaudo.getNombreCanal();
                                }                            
                                idCb = jdoTurnosDAO.encontrarIdCbByIdCtp(String.valueOf(idCtp));
                                if(idCb != null){
                                    cuentaBancaria = jdoTurnosDAO.encontrarNumerosCuenta(idCb);
                                }
                                if(cuentaBancaria!= null){
                                    bancoId = jdoTurnosDAO.encontrarIdBancoByCuentaBancaria(cuentaBancaria);
                                }
                                if(bancoId != null){
                                    bancoNombre = jdoTurnosDAO.encontrarBancoByIdBanco(bancoId);
                                }
                            } catch (HermodException e) {
                                 Logger.getLogger(AWTrasladoTurno.class.getName()).log(Level.SEVERE, null);
                            }

                            String nroAprobacion = general.getNoAprobacion();
                            String fechaDoc = general.getFechaDocu();
                            String valorPagado = java.text.NumberFormat.getInstance().format(apPago.getValorAplicado());
                            String valorDocumento = java.text.NumberFormat.getInstance().format(general.getValorDocumento());
                            String valorPagadoAnulado = turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO) ? java.text.NumberFormat.getInstance().format(apPago.getValorAplicadoAnulacion()) : "0";
                            DocumentoPago u;
                            int Estado = 0;
                        try {
                            fs = ForsetiService.getInstance();
                            u = jdoPagosDAO.getDocumentobyIdDocPago(doc.getIdDocumentoPago());
                            
                            if(u.getEstadocorreccion() == 0){
                                Date Ferchacreacion ;
                                if(u.getFechaCreacion() != null){
                                    Ferchacreacion = u.getFechaCreacion();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(Ferchacreacion);
                                    CirculoPk cid = new CirculoPk();
                                    cid.idCirculo = turno.getIdCirculo();
                                    Date FechaActual = new Date();
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(FechaActual);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.setTime(Ferchacreacion);
                                    int days = 0;
                                    while (calendar3.compareTo(calendar2) < 0) {
                                        calendar3.add(Calendar.DAY_OF_MONTH, 1); 
                                        days++;
                                    }
                                    int count = 0;
                                    for(int t = 1 ; t <= days ; t++){
                                        Date dateciclo = calendar.getTime();
                                        boolean isFes = false;
                                        try {
                                            isFes = fs.isFestivo(dateciclo, cid);
                                        } catch (Throwable ex) {
                                            isFes = false;
                                        }
                                        if(!isFes){
                                            count = count + 1;
                                        }
                                        if(count == 4){
                                            t = days;
                                        }
                                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    }
                                    if(count > 3){
                                        Estado = 1;
                                    }else{
                                        Estado = 0;
                                    }
                                }else{
                                    Estado =2;
                                }                    
                            }else{
                                Estado = 3;
                            }
                        } catch (Throwable ex) {
                            
                        }
                        String iddocpago = doc.getIdDocumentoPago();
                        listaGeneral.add(Arrays.asList(nombreBancoFranquicia, nroDocPago, nroAprobacion, fechaDoc, valorPagado, nombreCanal, tipoPago, nombreBanco, noConsignacion, cuentaBancaria, bancoNombre, valorDocumento,Estado,iddocpago));
                    }else if (doc.getTipoPago().equals(DocumentoPago.PAGO_TARJETA_CREDITO)) {
                        DocPagoTarjetaCredito credito = (DocPagoTarjetaCredito) doc;
                        String nombreBanco = encontrarBancosFranquicia(bancosFranquicias, credito.getBancoFranquicia().getIdBanco(), Integer.toString(credito.getBancoFranquicia().getIdTipoFranquicia()));
                        String nroDocPago = credito.getNumeroTarjeta();
                        DocumentoPago u;
                        int Estado = 0;
                        try {
                            fs = ForsetiService.getInstance();
                            u = jdoPagosDAO.getDocumentobyIdDocPago(doc.getIdDocumentoPago());

                            if(u.getEstadocorreccion() == 0){
                                Date Ferchacreacion ;
                                if(u.getFechaCreacion() != null){
                                    Ferchacreacion = u.getFechaCreacion();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(Ferchacreacion);
                                    CirculoPk cid = new CirculoPk();
                                    cid.idCirculo = turno.getIdCirculo();
                                    Date FechaActual = new Date();
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(FechaActual);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.setTime(Ferchacreacion);
                                    int days = 0;
                                    while (calendar3.compareTo(calendar2) < 0) {
                                        calendar3.add(Calendar.DAY_OF_MONTH, 1); 
                                        days++;
                                    }
                                    int count = 0;
                                    for(int t = 1 ; t <= days ; t++){
                                        Date dateciclo = calendar.getTime();
                                        boolean isFes = false;
                                        try {
                                            isFes = fs.isFestivo(dateciclo, cid);
                                        } catch (Throwable ex) {
                                            isFes = false;
                                        }
                                        if(!isFes){
                                            count = count + 1;
                                        }
                                        if(count == 4){
                                            t = days;
                                        }
                                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    }
                                    if(count > 3){
                                        Estado = 1;
                                    }else{
                                        Estado = 0;
                                    }
                                }else{
                                    Estado =2;
                                }                    
                            }else{
                                Estado = 3;
                            }
                        } catch (Throwable ex) {
                            
                        }
                        
                        String nroAprobacion = credito.getNumeroAprobacion();
                        String fechaDoc = credito.getFecha();
                        String valorPagado = java.text.NumberFormat.getInstance().format(apPago.getValorAplicado());
                        String iddocpago = doc.getIdDocumentoPago();
                        listaTarjetaCredito.add(Arrays.asList(nombreBanco, nroDocPago, nroAprobacion, fechaDoc, valorPagado,Estado,iddocpago));
                    } else if (doc.getTipoPago().equals(DocumentoPago.PAGO_TARJETA_DEBITO)) {
                        DocPagoTarjetaDebito debito = (DocPagoTarjetaDebito) doc;
                        String nombreBanco = encontrarBancosFranquicia(bancosFranquicias, debito.getBancoFranquicia().getIdBanco(), Integer.toString(debito.getBancoFranquicia().getIdTipoFranquicia()));
                        String nroDocPago = debito.getNumeroTarjeta();
                        String nroAprobacion = debito.getNumeroAprobacion();
                        String fechaDoc = debito.getFecha();
                        String valorPagado = java.text.NumberFormat.getInstance().format(apPago.getValorAplicado());
                        DocumentoPago u;
                        int Estado = 0;
                        try {
                            fs = ForsetiService.getInstance();
                            u = jdoPagosDAO.getDocumentobyIdDocPago(doc.getIdDocumentoPago());
                            
                            
                            if(u.getEstadocorreccion() == 0){
                                Date Ferchacreacion ;
                                if(u.getFechaCreacion() != null){
                                    Ferchacreacion = u.getFechaCreacion();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(Ferchacreacion);
                                    CirculoPk cid = new CirculoPk();
                                    cid.idCirculo = turno.getIdCirculo();
                                    Date FechaActual = new Date();
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(FechaActual);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.setTime(Ferchacreacion);
                                    int days = 0;
                                    while (calendar3.compareTo(calendar2) < 0) {
                                        calendar3.add(Calendar.DAY_OF_MONTH, 1); 
                                        days++;
                                    }
                                    int count = 0;
                                    for(int t = 1 ; t <= days ; t++){
                                        Date dateciclo = calendar.getTime();
                                        boolean isFes = false;
                                        try {
                                            isFes = fs.isFestivo(dateciclo, cid);
                                        } catch (Throwable ex) {
                                            isFes = false;
                                        }
                                        if(!isFes){
                                            count = count + 1;
                                        }
                                        if(count == 4){
                                            t = days;
                                        }
                                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    }
                                    if(count > 3){
                                        Estado = 1;
                                    }else{
                                        Estado = 0;
                                    }
                                }else{
                                    Estado =2;
                                }                    
                            }else{
                                Estado = 3;
                            }
                        } catch (Throwable ex) {
                            
                        }
                        String iddocpago = doc.getIdDocumentoPago();
                        listaTarjetaDebito.add(Arrays.asList(nombreBanco, nroDocPago, nroAprobacion, fechaDoc, valorPagado,Estado,iddocpago));
                    } else if (doc.getTipoPago().equals(DocumentoPago.PAGO_ELECTRONICO_PSE)) {
                        DocPagoElectronicoPSE pse = (DocPagoElectronicoPSE) doc;
                        String nombreBanco = encontrarBancosFranquicia(bancosFranquicias, pse.getBancoFranquicia().getIdBanco(), Integer.toString(pse.getBancoFranquicia().getIdTipoFranquicia()));
                        String nroAprobacion = pse.getNumeroAprobacion();
                        String fechaDoc = pse.getFechaDocumento();
                        String valorPagado = java.text.NumberFormat.getInstance().format(apPago.getValorAplicado());
                        DocumentoPago u;
                        int Estado = 0;
                        try {
                            fs = ForsetiService.getInstance();
                            u = jdoPagosDAO.getDocumentobyIdDocPago(doc.getIdDocumentoPago());
                            
                            
                            if(u.getEstadocorreccion() == 0){
                                Date Ferchacreacion ;
                                if(u.getFechaCreacion() != null){
                                    Ferchacreacion = u.getFechaCreacion();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(Ferchacreacion);
                                    CirculoPk cid = new CirculoPk();
                                    cid.idCirculo = turno.getIdCirculo();
                                    Date FechaActual = new Date();
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(FechaActual);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.setTime(Ferchacreacion);
                                    int days = 0;
                                    while (calendar3.compareTo(calendar2) < 0) {
                                        calendar3.add(Calendar.DAY_OF_MONTH, 1); 
                                        days++;
                                    }
                                    int count = 0;
                                    for(int t = 1 ; t <= days ; t++){
                                        Date dateciclo = calendar.getTime();
                                        boolean isFes = false;
                                        try {
                                            isFes = fs.isFestivo(dateciclo, cid);
                                        } catch (Throwable ex) {
                                            isFes = false;
                                        }
                                        if(!isFes){
                                            count = count + 1;
                                        }
                                        if(count == 4){
                                            t = days;
                                        }
                                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    }
                                    if(count > 3){
                                        Estado = 1;
                                    }else{
                                        Estado = 0;
                                    }
                                }else{
                                    Estado =2;
                                }                    
                            }else{
                                Estado = 3;
                            }
                        } catch (Throwable ex) {
                            
                        }
                        String iddocpago = doc.getIdDocumentoPago();
                        listaPagoElecPse.add(Arrays.asList(nombreBanco, nroAprobacion, fechaDoc, valorPagado,Estado,iddocpago));
                    } else if (doc.getTipoPago().equals(DocumentoPago.PAGO_EFECTIVO)) {
//                        DocPagoEfectivo efectivo = (DocPagoEfectivo) doc;
//                        DocPagoGeneral efectivo = (DocPagoGeneral) doc;
                        String valorPagado = java.text.NumberFormat.getInstance().format(apPago.getValorAplicado());
                        DocumentoPago u;
                        int Estado = 0;
                        try {
                            fs = ForsetiService.getInstance();
                            u = jdoPagosDAO.getDocumentobyIdDocPago(doc.getIdDocumentoPago());
                            
                            
                            if(u.getEstadocorreccion() == 0){
                                Date Ferchacreacion ;
                                if(u.getFechaCreacion() != null){
                                    Ferchacreacion = u.getFechaCreacion();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(Ferchacreacion);
                                    CirculoPk cid = new CirculoPk();
                                    cid.idCirculo = turno.getIdCirculo();
                                    Date FechaActual = new Date();
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(FechaActual);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.setTime(Ferchacreacion);
                                    int days = 0;
                                    while (calendar3.compareTo(calendar2) < 0) {
                                        calendar3.add(Calendar.DAY_OF_MONTH, 1); 
                                        days++;
                                    }
                                    int count = 0;
                                    for(int t = 1 ; t <= days ; t++){
                                        Date dateciclo = calendar.getTime();
                                        boolean isFes = false;
                                        try {
                                            isFes = fs.isFestivo(dateciclo, cid);
                                        } catch (Throwable ex) {
                                            isFes = false;
                                        }
                                        if(!isFes){
                                            count = count + 1;
                                        }
                                        if(count == 4){
                                            t = days;
                                        }
                                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    }
                                    if(count > 3){
                                        Estado = 1;
                                    }else{
                                        Estado = 0;
                                    }
                                }else{
                                    Estado =2;
                                }                    
                            }else{
                                Estado = 3;
                            }
                        } catch (Throwable ex) {
                            
                        }
                        listaEfectivo.add(valorPagado);
                        listaEfectivo.add(Estado);
                        String iddocpago = doc.getIdDocumentoPago();
                        listaEfectivo.add(iddocpago);
                    } else if (doc.getTipoPago().equals(DocumentoPago.PAGO_CONVENIO)) {
                        DocPagoConvenio convenio = (DocPagoConvenio) doc;
                        String valorPagado = java.text.NumberFormat.getInstance().format(apPago.getValorAplicado());
                        DocumentoPago u;
                        int Estado = 0;
                        try {
                            fs = ForsetiService.getInstance();
                            u = jdoPagosDAO.getDocumentobyIdDocPago(doc.getIdDocumentoPago());
                            
                            if(u.getEstadocorreccion() == 0){
                                Date Ferchacreacion ;
                                if(u.getFechaCreacion() != null){
                                    Ferchacreacion = u.getFechaCreacion();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(Ferchacreacion);
                                    CirculoPk cid = new CirculoPk();
                                    cid.idCirculo = turno.getIdCirculo();
                                    Date FechaActual = new Date();
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(FechaActual);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.setTime(Ferchacreacion);
                                    int days = 0;
                                    while (calendar3.compareTo(calendar2) < 0) {
                                        calendar3.add(Calendar.DAY_OF_MONTH, 1); 
                                        days++;
                                    }
                                    int count = 0;
                                    for(int t = 1 ; t <= days ; t++){
                                        Date dateciclo = calendar.getTime();
                                        boolean isFes = false;
                                        try {
                                            isFes = fs.isFestivo(dateciclo, cid);
                                        } catch (Throwable ex) {
                                            isFes = false;
                                        }
                                        if(!isFes){
                                            count = count + 1;
                                        }
                                        if(count == 4){
                                            t = days;
                                        }
                                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    }
                                    if(count > 3){
                                        Estado = 1;
                                    }else{
                                        Estado = 0;
                                    }
                                }else{
                                    Estado =2;
                                }                    
                            }else{
                                Estado = 3;
                            }
                        } catch (Throwable ex) {
                            
                        }
                        listaConvenio.add(valorPagado);
                        listaConvenio.add(Estado);
                        String iddocpago = doc.getIdDocumentoPago();
                        listaConvenio.add(iddocpago);
                    } else if (doc.getTipoPago().equals(DocumentoPago.PAGO_CONSIGNACION)) {
                        DocPagoConsignacion consignacion = (DocPagoConsignacion) doc;
                        String nombreBanco = consignacion.getBanco().getNombre();
                        String nroDocPago = consignacion.getNoConsignacion();
                        String fechaDoc = consignacion.getFecha();
                        String valorDocumento = java.text.NumberFormat.getInstance().format(consignacion.getValorDocumento());
                        String valorPagado = java.text.NumberFormat.getInstance().format(apPago.getValorAplicado());
                        String valorPagadoAnulado = turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO) ? java.text.NumberFormat.getInstance().format(apPago.getValorAplicadoAnulacion()) : "0";
                        DocumentoPago u;
                        int Estado = 0;
                        try {
                            fs = ForsetiService.getInstance();
                            u = jdoPagosDAO.getDocumentobyIdDocPago(doc.getIdDocumentoPago());
                            
                            
                            if(u.getEstadocorreccion() == 0){
                                Date Ferchacreacion ;
                                if(u.getFechaCreacion() != null){
                                    Ferchacreacion = u.getFechaCreacion();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(Ferchacreacion);
                                    CirculoPk cid = new CirculoPk();
                                    cid.idCirculo = turno.getIdCirculo();
                                    Date FechaActual = new Date();
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(FechaActual);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.setTime(Ferchacreacion);
                                    int days = 0;
                                    while (calendar3.compareTo(calendar2) < 0) {
                                        calendar3.add(Calendar.DAY_OF_MONTH, 1); 
                                        days++;
                                    }
                                    int count = 0;
                                    for(int t = 1 ; t <= days ; t++){
                                        Date dateciclo = calendar.getTime();
                                        boolean isFes = false;
                                        try {
                                            isFes = fs.isFestivo(dateciclo, cid);
                                        } catch (Throwable ex) {
                                            isFes = false;
                                        }
                                        if(!isFes){
                                            count = count + 1;
                                        }
                                        if(count == 4){
                                            t = days;
                                        }
                                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    }
                                    if(count > 3){
                                        Estado = 1;
                                    }else{
                                        Estado = 0;
                                    }
                                }else{
                                    Estado =2;
                                }                    
                            }else{
                                Estado = 3;
                            }
                        } catch (Throwable ex) {
                            
                        }
                        String iddocpago = doc.getIdDocumentoPago();
                        listaConsignacion.add(Arrays.asList(nombreBanco, nroDocPago, valorDocumento, fechaDoc, valorPagado, valorPagadoAnulado,Estado,iddocpago));
                    } else if (doc.getTipoPago().equals(DocumentoPago.PAGO_CHEQUE)) {
                        DocPagoCheque cheque = (DocPagoCheque) doc;
                        String nombreBanco = cheque.getBanco().getNombre();
                        String nroDocPago = cheque.getNoCheque();
                        String valorDocumento = java.text.NumberFormat.getInstance().format(cheque.getValorDocumento());
                        String fechaDoc = cheque.getFecha();
                        String valorPagado = java.text.NumberFormat.getInstance().format(apPago.getValorAplicado());
                        String valorPagadoAnulado = turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO) ? java.text.NumberFormat.getInstance().format(apPago.getValorAplicadoAnulacion()) : "0";
                        DocumentoPago u;
                        int Estado = 0;
                        try {
                            fs = ForsetiService.getInstance();
                            u = jdoPagosDAO.getDocumentobyIdDocPago(doc.getIdDocumentoPago());
                            
                            
                            if(u.getEstadocorreccion() == 0){
                                Date Ferchacreacion ;
                                if(u.getFechaCreacion() != null){
                                    Ferchacreacion = u.getFechaCreacion();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(Ferchacreacion);
                                    CirculoPk cid = new CirculoPk();
                                    cid.idCirculo = turno.getIdCirculo();
                                    Date FechaActual = new Date();
                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.setTime(FechaActual);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.setTime(Ferchacreacion);
                                    int days = 0;
                                    while (calendar3.compareTo(calendar2) < 0) {
                                        calendar3.add(Calendar.DAY_OF_MONTH, 1); 
                                        days++;
                                    }
                                    int count = 0;
                                    for(int t = 1 ; t <= days ; t++){
                                        Date dateciclo = calendar.getTime();
                                        boolean isFes = false;
                                        try {
                                            isFes = fs.isFestivo(dateciclo, cid);
                                        } catch (Throwable ex) {
                                            isFes = false;
                                        }
                                        if(!isFes){
                                            count = count + 1;
                                        }
                                        if(count == 4){
                                            t = days;
                                        }
                                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    }
                                    if(count > 3){
                                        Estado = 1;
                                    }else{
                                        Estado = 0;
                                    }
                                }else{
                                    Estado =2;
                                }                    
                            }else{
                                Estado = 3;
                            }
                        } catch (Throwable ex) {
                            
                        }
                        String iddocpago = doc.getIdDocumentoPago();
                        listaCheque.add(Arrays.asList(nombreBanco, nroDocPago, valorDocumento, fechaDoc, valorPagado, valorPagadoAnulado,Estado,iddocpago));
                    }
                }
            }
        }
        resumen.put(DocumentoPago.PAGO_TARJETA_CREDITO, listaTarjetaCredito);
        resumen.put(DocumentoPago.PAGO_TARJETA_DEBITO, listaTarjetaDebito);
        resumen.put(DocumentoPago.PAGO_ELECTRONICO_PSE, listaPagoElecPse);
        resumen.put(DocumentoPago.PAGO_EFECTIVO, listaEfectivo);
        resumen.put(DocumentoPago.PAGO_CONVENIO, listaConvenio);
        resumen.put(DocumentoPago.PAGO_CONSIGNACION, listaConsignacion);
        resumen.put(DocumentoPago.PAGO_CHEQUE, listaCheque);
        resumen.put(DocumentoPago.PAGO_GENERAL, listaGeneral);//---------

        return resumen;
    }
}
