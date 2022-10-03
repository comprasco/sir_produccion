package gov.sir.core.web.acciones.registro;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.comun.modelo.Transaccion;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import gov.sir.core.eventos.certificado.EvnCertificado;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.eventos.registro.EvnLiquidacionRegistro;
import gov.sir.core.eventos.registro.EvnRespLiquidacionRegistro;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCheckedItem;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoActoDerechoRegistral;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CDocumento;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CLiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSolicitudAsociada;
import gov.sir.core.negocio.modelo.constantes.CSolicitudCheckedItem;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CSubtipoAtencion;
import gov.sir.core.negocio.modelo.constantes.CSubtipoSolicitud;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado;
import gov.sir.core.web.acciones.comun.AWPago;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.CrearTurnoException;
import gov.sir.core.web.acciones.excepciones.ValidacionCertificadoAsociadoException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionRegistroException;
import gov.sir.core.web.acciones.excepciones.ValidacionMatriculaAsociadaRegistroException;
import gov.sir.core.web.acciones.excepciones.ValidacionMatriculaRegistroException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosActoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosRegistroException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import java.math.BigDecimal;

import java.text.NumberFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicConditionalValidator;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringNotNullOrEmptyValidatorWrapper;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jfrias
 */
public class AWLiquidacionRegistro extends SoporteAccionWeb {

    // edicion-turno-vinculado -------------------------------------------
    public static final String REGISTRO_VINCULARTURNO_ADDITEM_ACTION
            = "REGISTRO_VINCULARTURNO_ADDITEM_ACTION";
    public static final String REGISTRO_VINCULARTURNO_DELITEM_ACTION
            = "REGISTRO_VINCULARTURNO_DELITEM_ACTION";

    // -------------------------------------------------------------------
    /**
     * Indicador de la accion web agregar.
     * <p>
     * Agregar descripcion aca.
     */
    public final static String AGREGAR = "AGREGAR";
    
    /**
     * Refresca la página
     */
    public final static String REFRESH = "REFRESH";
    
    /**
     * Indicador de la accion web agregar un folio que aún no se han migrado.
     */
    public final static String AGREGAR_MIG_INC = "AGREGAR_MIG_INC";

    /**
     * Indicador de la accion web eliminar.
     * <p>
     * Agregar descripcion aca.
     */
    public final static String ELIMINAR = "ELIMINAR";
    
    /**
     * Identificador del Numero de Matriculas a Agregar
     */
    public final static String NUM_MAT_AGR = "NUM_MAT_AGR";
    
    public static final String MATRICULA_BLOQUEADA_NOTA_DEV = "MATRICULA_BLOQUEADA_NOTA_DEV";
    /**
     * Identificador del Numero de Matriculas Liquidador
     */
    public final static String NUM_MAT_LIQ = "NUM_MAT_LIQ";
    /**
     * Indicador de la accion web eliminar, un folio asociado, este folio es de
     * los que no se han migrado.
     */
    public final static String ELIMINAR_MIG_INC = "ELIMINAR_MIG_INC";

    /**
     * Indicador de la accion web liquida.
     * <p>
     * Accion en la cual se obtienen los datos de la pagina, para crear una
     * liquidacion, añadirla a la solicitud de registro, para que en una
     * siguiente etapa esta sea cancelada (pagada).
     */
    public final static String LIQUIDAR = "LIQUIDAR";

    /**
     * Indicador de la accion web liquida.
     * <p>
     * Accion en la cual se obtienen los datos de la pagina, para crear una
     * liquidacion, añadirla a la solicitud de registro, para que en una
     * siguiente etapa esta sea cancelada (pagada).
     */
    public final static String PRELIQUIDAR = "PRELIQUIDAR";

    /**
     * Indicar de la accion web de regresar liquidacion
     */
    public final static String REGRESAR_LIQUIDACION = "REGRESAR_LIQUIDACION";

    /**
     *
     */
    public final static String COMENZAR_PRELIQUIDACION = "COMENZAR_PRELIQUIDACION";

    /**
     * Indicador de la accion web liquida.
     * <p>
     * Accion en la cual se obtienen los datos de la pagina, para crear una
     * liquidacion, añadirla a la solicitud de registro, para que en una
     * siguiente etapa esta sea cancelada (pagada). A diferencia de liquidar,
     * esta acción permite procesar el pago de la solicitud de registro.
     */
    public final static String LIQUIDAR_CONTINUAR = "LIQUIDAR_CONTINUAR";

    /**
     * Indicar de la accion web de regresar liquidacion
     */
    public final static String REGRESAR_CALIFICACION = "REGRESAR_CALIFICACION";

    /**
     * Indicador de la accion web que edita una liquidación.
     * <p>
     * Accion en la cual se obtienen los datos de la pagina, para crear una
     * liquidacion, añadirla a la solicitud de registro, y editar una
     * liquidación existente con la nueva información.
     */
    public final static String EDITAR_LIQUIDACION = "EDITAR_LIQUIDACION";

    /**
     * Indicador de la accion web preservar info.
     * <p>
     * Accion en la cual se guardan los datos obtenidos de la pagina para que se
     * vuelvan a mostrar al refrescarse esta.
     */
    public final static String PRESERVAR_INFO = "PRESERVAR_INFO";

    /**
     * Indicador de la accion web preservar info en la página de adición de
     * actos.
     * <p>
     * Accion en la cual se guardan los datos obtenidos de la pagina para que se
     * vuelvan a mostrar al refrescarse esta.
     */
    public final static String PRESERVAR_INFO_LIQUIDAR = "PRESERVAR_INFO_LIQUIDAR";

    /**
     * Indicador de la accion web liquidar_registro.
     * <p>
     * Accion en la cual se crea la solicitud, se agregan los datos obtenidos en
     * la 1era pantalla de la solicitud de registro y se guarda esta en la
     * session para ser usada por la siguiente y concluir el proceso de
     * liquidacion.
     */
    public final static String LIQUIDAR_REGISTRO = "LIQUIDAR_REGISTRO";

    /**
     * Indicador de la accion web agregar acto.
     * <p>
     * Accion en la se agrega un acto a la solicitud, cada acto tiene un costo
     * aparte, y puede o no que tenga impuesto. En esta accion se calculara
     * cuanto costara el acto y se guardara para cuando se cree la liquidacion.
     */
    public final static String AGREGAR_ACTO = "AGREGAR_ACTO";

    /**
     * Indicador de la accion web agregar acto.
     * <p>
     * Accion en la se agrega un acto a la solicitud, cada acto tiene un costo
     * aparte, y puede o no que tenga impuesto. En esta accion se calculara
     * cuanto costara el acto y se guardara para cuando se cree la liquidacion.
     */
    public final static String AGREGAR_ACTO_PRELIQUIDACION = "AGREGAR_ACTO_PRELIQUIDACION";

    /**
     * Indicador de la accion web eliminar un acto
     * <p>
     * Accion en la cual se elimina uno de los actos anteriormente agregados.
     */
    public final static String ELIMINAR_ACTO = "ELIMINAR_ACTO";

    /**
     * Indicador de la accion web eliminar un acto
     * <p>
     * Accion en la cual se elimina uno de los actos anteriormente agregados.
     */
    public final static String ELIMINAR_ACTO_PRELIQUIDACION = "ELIMINAR_ACTO_PRELIQUIDACION";

    /**
     * Indicador de la accion web cargar derechos
     * <p>
     * Colocar descripcion aca.
     */
    public final static String CARGAR_DERECHOS = "CARGAR_DERECHOS";

    /**
     * Indicador de la accion web calcular los derecos de registro de un acto
     * <p>
     * Al editar un acto se debe cargar el valor de sus derechos
     */
    public final static String CARGAR_VALOR_DERECHOS = "CARGAR_VALOR_DERECHOS";
    /**
     * Indicador de la accion web cargar derechos
     * <p>
     * Colocar descripcion aca.
     */
    public final static String CARGAR_DERECHOS_PRELIQUIDACION = "CARGAR_DERECHOS_PRELIQUIDACION";

    /**
     * Indicador de la accion web cargar documentos
     * <p>
     * Colocar descripcion aca.
     */
    public final static String CARGAR_DOCUMENTOS = "CARGAR_DOCUMENTOS";

    /**
     * Indicador de la accion validar turno anterior
     * <p>
     * Accion en la cual se valida un turno dado, para ver si cumple las
     * condiciones para que sea base para un nueva solicitud de registro.
     * <p>
     * Validaciones para que sea un turno Anterior:
     * <p>
     * -Un turno ya cerrado
     * <p>
     * -colocar demas validaciones.
     */
    public final static String VALIDAR_TURNO_ANTERIOR = "VALIDAR_TURNO_ANTERIOR";

    /**
     * Indicador de la accion web de adicionar un certificado asociado.
     * <p>
     * Accion en la cual se añade un certificado asociado a la solicitud de
     * registro.
     */
    public final static String ADICIONAR_CERTIFICADO_ASOCIADO = "ADICIONAR_CERTIFICADO_ASOCIADO";

    /**
     * Indicador de la accion web de eliminar un certificados asociado.
     * <p>
     * Accion en la cual se elimina un certificado asociado a la solicitud de
     * registro.
     */
    public final static String ELIMINA_CERTIFICADO_ASOCIADO = "ELIMINA_CERTIFICADO_ASOCIADO";

    /**
     * Indicador de la accion web de crear un nuevo certificado asociado.
     * <p>
     * Accion en la cual se lleva a la pantalla para crear un nuevo certificado
     * asociado.
     */
    public final static String NUEVO_CERTIFICADO_ASOCIADO = "NUEVO_CERTIFICADO_ASOCIADO";

    /**
     * Esta constante se utiliza para incrementar el secuencial de recibo de la
     * estación.
     */
    public static final String INCREMENTAR_SECUENCIAL_RECIBO = "INCREMENTAR_SECUENCIAL_RECIBO";

    /**
     * Esta constante se utiliza para validar una solicitud de registro antes de
     * la radicacion.
     */
    public static final String VALIDAR_SOLICITUD = "VALIDAR_SOLICITUD";

    /**
     * Constante que se utiliza para identificar la accion de listar los turnos
     * validos para agregarle certificados asociados.
     */
    public static final String LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS = "LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS";

    /**
     * Constante que se utliza para identificar la accion de ir a la pantalla de
     * agregar certificados asociados al turno
     */
    public static final String SOLICITAR_AGREGAR_CERTIFICADOS_ASOCIADOS = "SOLICITAR_AGREGAR_CERTIFICADOS_ASOCIADOS";

    /**
     *
     */
    public final static String REFRESCAR_AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO = "REFRESCAR_AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO";

    /**
     *
     */
    public final static String AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO = "AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO";

    /**
     *
     */
    public final static String ELIMINAR_TURNO_ANTERIOR = "ELIMINAR_TURNO_ANTERIOR";

    /**
     *
     */
    public final static String AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO = "AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO";

    /**
     *
     */
    public final static String AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO_EDICION = "AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO_EDICION";

    /**
     *
     */
    public final static String ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO = "ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO";

    /**
     *
     */
    public final static String ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO_EDICION = "ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO_EDICION";

    /**
     *
     */
    public final static String AGREGAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA
            = "AGREGAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA";

    /**
     *
     */
    public final static String AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION = "AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION";

    /**
     *
     */
    public final static String AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION_EDICION = "AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION_EDICION";

    /**
     *
     */
    public final static String ELIMINAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA
            = "ELIMINAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA";

    /**
     *
     */
    public final static String ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION = "ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION";

    /**
     *
     */
    public final static String ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION_EDICION = "ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION_EDICION";

    /**
     *
     */
    // public final static String CANCELAR_ADICION_CERTIFICADO_ASOCIADO =
    // "CANCELAR_ADICION_CERTIFICADO_ASOCIADO";
    /**
     *
     */
    public final static String VER_ANTIGUO_SISTEMA = "VER_ANTIGUO_SISTEMA";

    /**
     *
     */
    public final static String ID_SOLICITUD = "ID_SOLICITUD";

    /**
     *
     */
    public final static String BUSCAR_SOLICITUD = "BUSCAR_SOLICITUD";

    /**
     *
     */
    public static final String BUSCAR_SOLICITUD_EDICION = "BUSCAR_SOLICITUD_EDICION";

    /**
     * Constante que define que debe buscar la solicitud para realizar el pago
     * de registro
     */
    public final static String BUSCAR_SOLICITUD_PAGO = "BUSCAR_SOLICITUD_PAGO";

    /**
     *
     */
    public final static String CREAR_TURNO = "CREAR_TURNO";

    /**
     *
     */
    public static final String SOLICITUD = "SOLICITUD";

    // VARIABLES DE SESION PARA EL CERTIFICADO ASOCIADO
    /**
     *
     */
    public final static String LISTA_CERTIFICADOS_ASOCIADOS = "LISTA_CERTIFICADOS_ASOCIADOS";

    /**
     *
     */
    public final static String LISTA_CERTIFICADOS_ASOCIADOS_AGREGAR_TURNO = "LISTA_CERTIFICADOS_ASOCIADOS_AGREGAR_TURNO";

    /**
     *
     */
    public final static String LISTA_CERTIFICADOS_ASOCIADOS_YA_INGRESADOS_AGREGAR_TURNO = "LISTA_CERTIFICADOS_ASOCIADOS_YA_INGRESADOS_AGREGAR_TURNO";

    /**
     *
     */
    public final static String LISTA_LIQUIDACIONES_CERTIFICADOS_ASOCIADOS = "LISTA_LIQUIDACIONES_CERTIFICADOS_ASOCIADOS";

    /**
     *
     */
    public final static String FLAG_CERTIFICADOS_ASOCIADOS_TURNO = "FLAG_CERTIFICADOS_ASOCIADOS_TURNO";

    /**
     *
     */
    public final static String LISTA_MATRICULAS_CERTIFICADO_ASOCIADO = "LISTA_MATRICULAS_CERTIFICADO_ASOCIADO";

    /**
     *
     */
    public final static String LISTA_MATRICULAS_CERTIFICADO_ASOCIADO_YA_REGISTRADOS_AGREGAR_TURNO = "LISTA_MATRICULAS_CERTIFICADO_ASOCIADO_YA_REGISTRADOS_AGREGAR_TURNO";

    /**
     *
     */
    public final static String LISTA_MATRICULAS_CERTIFICADO_ASOCIADO_AGREGAR_TURNO = "LISTA_MATRICULAS_CERTIFICADO_ASOCIADO_AGREGAR_TURNO";

    /**
     *
     */
    public final static String CERTIFICADO_ASOCIADO_NUM_MATRICULAS = "CERTIFICADO_ASOCIADO_NUM_MATRICULAS";

    /**
     *
     */
    public final static String CERTIFICADO_ASOCIADO_ID_MATRICULA = "CERTIFICADO_ASOCIADO_ID_MATRICULA";

    /**
     *
     */
    public final static String CAPTURANDO_CERTIFICADO_ASOCIADO = "CAPTURANDO_CERTIFICADO_ASOCIADO";

    /**
     *
     */
    public final static String CERTIFICADO_ASOCIADO_ITEM = "CERTIFICADO_ASOCIADO_ITEM";

    /**
     *
     */
    public final static String AGREGAR_MATRICULA_REGISTRO = "AGREGAR_MATRICULA_REGISTRO";

    /**
     *
     */
    public final static String AGREGAR_MATRICULA_REGISTRO_MIG_INC = "AGREGAR_MATRICULA_REGISTRO_MIG_INC";

    /**
     *
     */
    public final static String VIENE_DE_CAJERO = "VIENE_DE_CAJERO";

    /**
     *
     */
    public final static String PRELIQUIDACION = "PRELIQUIDACION";

    /**
     *
     */
    public final static String LISTA_TURNOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS = "LISTA_TURNOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS";

    /**
     *
     */
    public final static String LISTA_ELEMENTOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS = "LISTA_ELEMENTOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS";

    /**
     * Variable para diferenciar si se esta editando una liquidación o si se
     * esta creando
     */
    public final static String ES_EDICION_LIQUIDACION = "ES_EDICION_LIQUIDACION";

    public final static String REMOVER_INFO = "REMOVER_INFO";

    public final static int ANIO_MINIMO = 1850;

    private static final String ID_OFICINA_ORIGEN_DEFECTO = "554";

    public final static String LISTA_MATRICULAS_REMOVE = "LISTA_MATRICULAS_REMOVE";

    /**
     *
     */
    public AWLiquidacionRegistro() {
        super();
    }

    /**
     * @see
     * org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        String accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion válida");
        }
        /*AHERRENO
                 29/05/2012
                 REQ 076_151 TRANSACCION*/
        request.getSession().setAttribute("TIEMPO_INICIO_TRANSACCION", new Date());
        this.preservarParametros(request);

        // turno-vinculado  ----------------------------------------------
        if (REGISTRO_VINCULARTURNO_ADDITEM_ACTION.equals(accion)) {
            return doRegistro_VincularTurno_AddItem(request);
        } else if (REGISTRO_VINCULARTURNO_DELITEM_ACTION.equals(accion)) {
            return doRegistro_VincularTurno_DelItem(request);
        } // -----------------------------------------------
        else if (accion.equals(LIQUIDAR)) {
            return liquidar(request);
        } else if (accion.equals(LIQUIDAR_CONTINUAR)) {
            return liquidar(request);
        } else if (accion.equals(EDITAR_LIQUIDACION)) {
            return editarLiquidacion(request);
        } else if (accion.equals(LIQUIDAR_REGISTRO)) {
            return liquidarRegistro(request);
        } else if (accion.equals(COMENZAR_PRELIQUIDACION)) {
            return comenzarPreliquidacion(request);
        } else if (accion.equals(PRELIQUIDAR)) {
            return preliquidar(request);
        } else if (accion.equals(REGRESAR_LIQUIDACION) || accion.equals(REGRESAR_CALIFICACION)) {
            return regresarLiquidacion(request);
        } else if (accion.equals(AGREGAR)) {
            return agregarMatricula(request);
        } else if (accion.equals(AGREGAR_MIG_INC)) {
            return agregarMatriculaSirMig(request);
        } else if (accion.equals(AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO)
                || accion.equals(AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO_EDICION)) {
            return agregarMatriculaAsociada(request);
        } else if (accion.equals(ELIMINAR)) {
            return eliminarMatricula(request);
        } else if (accion.equals(ELIMINAR_MIG_INC)) {
            return eliminarMatriculaSirMig(request);
        } else if (accion.equals(PRESERVAR_INFO)) {
            return preservarInfo(request);
        } else if (accion.equals(PRESERVAR_INFO_LIQUIDAR)) {
            preservarInfoLiquidar(request);

            return null;
        } else if (accion.equals(AGREGAR_ACTO) || accion.equals(AGREGAR_ACTO_PRELIQUIDACION)) {
            return agregarActo(request);
        } else if (accion.equals(ELIMINAR_ACTO) || accion.equals(ELIMINAR_ACTO_PRELIQUIDACION)) {
            return eliminarActo(request);
        } else if (accion.equals(CARGAR_DERECHOS)
                || accion.equals(CARGAR_DERECHOS_PRELIQUIDACION)) {
            return cargarDerechos(request);
        } else if (accion.equals(CARGAR_VALOR_DERECHOS)) {
            return cargarValorDerechos(request);
        } else if (accion.equals(CARGAR_DOCUMENTOS)) {
            return cargarDocumentos(request);
        } else if (accion.equals(VALIDAR_TURNO_ANTERIOR)) {
            return validarTurnoAnterior(request);
        } else if (accion.equals(ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO)
                || accion.equals(ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO_EDICION)) {
            return eliminarMatriculaCertificadoAsociado(request);
        } else if (accion.equals(
                AGREGAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA)) {
            return agregarCertificadosAntiguoSistema(request);
        } else if (accion.equals(AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION)
                || accion.equals(AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION_EDICION)) {
            return agregarCertificadosSegregacion(request);
        } else if (accion.equals(
                ELIMINAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA)) {
            return eliminarCertificadosAntiguoSistema(request);
        } else if (accion.equals(ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION)
                || accion.equals(ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION_EDICION)) {
            return eliminarCertificadosSegregacion(request);
        } else if (accion.equals(ELIMINAR_TURNO_ANTERIOR)) {
            return eliminarTurnoAnterior(request);
        } else if (accion.equals(VER_ANTIGUO_SISTEMA)) {
            return verAntiguoSistema(request);
        } else if (accion.equals(AWLiquidacionRegistro.BUSCAR_SOLICITUD)
                || accion.equals(AWLiquidacionRegistro.BUSCAR_SOLICITUD_PAGO)) {
            return buscarSolicitud(request);
        } else if (accion.equals(AWLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION)) {
            return buscarSolicitudEdicion(request);
        } else if (accion.equals(AWLiquidacionRegistro.VALIDAR_SOLICITUD)) {
            return validarSolicitud(request);
        } else if (accion.equals(
                AWLiquidacionRegistro.INCREMENTAR_SECUENCIAL_RECIBO)) {
            return incrementarSecuencialRecibo(request);
        } else if (accion.equals(AWLiquidacionRegistro.CREAR_TURNO)) {
            return crearTurnoRegistro(request);
        } else if (accion.equals(AWLiquidacionRegistro.LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS)) {
            return listarTurnosRegistroParaAgregarCertificadosAsociados(request);
        } else if (accion.equals(AWLiquidacionRegistro.SOLICITAR_AGREGAR_CERTIFICADOS_ASOCIADOS)) {
            return solicitarAgregarCertificadosAsociadosTurno(request);
        } else if (accion.equals(AWLiquidacionRegistro.AGREGAR_CERTIFICADOS_ASOCIADOS_TURNO)) {
            return agregarCertificadosAsociadosTurno(request);
        } else if (accion.equals(AWLiquidacionRegistro.REMOVER_INFO)) {
            return removerInfo(request);
        } else if (accion.equals(AWLiquidacionRegistro.REFRESH)) {
            return refresh(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion
                    + " no es valida.");
        }
    }
    
    private Evento refresh(HttpServletRequest request){
        preservarInfo(request);
        preservarInfoLiquidar(request);
        return null;
    }

    /**
     * doRegistro_VincularTurno_DelItem
     *
     * @param request HttpServletRequest
     * @return Evento
     * @see eliminarTurnoAnterior
     */
    private Evento doRegistro_VincularTurno_DelItem(HttpServletRequest request) {

        HttpSession session = request.getSession();
        // session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
        // session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
        // session.removeAttribute(CSolicitudRegistro.CALENDAR);
        //
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        // session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);

        session.removeAttribute(CTurno.SOLICITUD_VINCULADA);

        return null;

    }

    /**
     * doRegistro_VincularTurno_AddItem
     *
     * @param request HttpServletRequest
     * @return Evento
     * @see validarTurnoAnterior
     */
    private Evento
            doRegistro_VincularTurno_AddItem(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        // parametro con id de solicitud vincualda
        final String PARAM_SOLICITUD_VINCULADA = "SOLICITUD_VINCULADA";

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        ValidacionLiquidacionRegistroException exception = new ValidacionLiquidacionRegistroException();

        session.removeAttribute(CTurno.SOLICITUD_VINCULADA);
        String param_SolicitudVinculadaId = request.getParameter(PARAM_SOLICITUD_VINCULADA); //  CTurno.TURNO_VINCULADO

        if (null == param_SolicitudVinculadaId) {
            param_SolicitudVinculadaId = "";
        }

        BasicConditionalValidator validator;
        validator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validator.setObjectToValidate(param_SolicitudVinculadaId);
        validator.validate();

        if (validator.getResult() != true) {
            exception.addError("solicitud-vinculada; debe indicar una cadena valida");
        }

        gov.sir.core.negocio.modelo.SolicitudVinculada localItem
                = new gov.sir.core.negocio.modelo.SolicitudVinculada();

        // decompose test; delegated validation
        String anno = null;
        String circ = null;
        String proceso = null;
        String turnoId = null;

        decompose_SolicitudId:
        {

            int pos1 = 0;
            int pos2 = param_SolicitudVinculadaId.indexOf("-", 1);

            if (pos2 != -1) {
                anno = param_SolicitudVinculadaId.substring(pos1, pos2);
                pos1 = pos2 + 1;
                pos2 = param_SolicitudVinculadaId.indexOf("-", pos1);

                if ((pos1 != -1) && (pos2 != -1)) {
                    circ = param_SolicitudVinculadaId.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    pos2 = param_SolicitudVinculadaId.indexOf("-", pos1);

                    if ((pos1 != -1) && (pos2 != -1)) {
                        proceso = param_SolicitudVinculadaId.substring(pos1, pos2);
                        pos1 = pos2 + 1;

                        if ((pos1 != -1) && (pos2 != -1)) {
                            turnoId = param_SolicitudVinculadaId.substring(pos1);
                        }
                    }
                }
            }

        } //:decompose_Validation

        // message to next layer:
        EvnLiquidacionRegistro evento = null;

        validate_SolicitudId:
        {

            if ((param_SolicitudVinculadaId != null)
                    && (anno != null)
                    && (circ != null)
                    && (proceso != null)
                    && (turnoId != null)) {

                param_SolicitudVinculadaId = param_SolicitudVinculadaId.trim();
                param_SolicitudVinculadaId = param_SolicitudVinculadaId.toUpperCase();
                // fix pk: solicitud hija
                localItem.setIdSolicitud(param_SolicitudVinculadaId);

                gov.sir.core.negocio.modelo.Proceso thisProceso
                        = (gov.sir.core.negocio.modelo.Proceso) session.getAttribute(WebKeys.PROCESO);

                if ((null != thisProceso)
                        && (thisProceso.getIdProceso() != Long.parseLong(proceso))) {
                    exception.addError("solicitud-vinculada; Solicitud no es del mismo proceso" + " (" + param_SolicitudVinculadaId + ")");
                } else {
                    // session.setAttribute(CTurno.TURNO_ANTERIOR, idWfTurno);
                    evento = new EvnLiquidacionRegistro(usuario);
                    evento.setTipoEvento(EvnLiquidacionRegistro.REGISTRO_VINCULARTURNO_ADDITEM_EVENT);
                    evento.setSolicitudVinculada(localItem);
                }
            } else {
                exception.addError("solicitud-vinculada; El codigo de solicitud no esta completo");
            }

        } //:validate_SolicitudId

        // ------------------------------------------------------------------------------
        // raise app error
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // ------------------------------------------------------------------------------

        return evento;

    }

    /**
     * Cuando se asocia un turno anterior a la solicitud, los datos del
     * documento del turno anterior, se deben asociar a la solicitud que se está
     * elaborando actualmente, así que cuando se elimina el turno anterior de la
     * solicitud, estos datos también se deben eliminar
     *
     * @param request Tiene la información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> nulo, ya que no se
     * necesita generar un evento
     */
    private Evento eliminarTurnoAnterior(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.CALENDAR);

        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        session.removeAttribute("antiguoSistemaExistente");

        session.removeAttribute(CTurno.TURNO_ANTERIOR);
        session.removeAttribute(WebKeys.TURNO_ANTERIOR_OK);

        return null;
    }

    /**
     * Se utiliza para maximizar/minimizar los datos de antiguo sistema de la
     * solicitud En el parámetro VER_ANTIGUO_SISTEMA de la solicitud viene la
     * información para maximizar o minimizar la información de antiguo sistema.
     * Este parámetro se pone en la sesión, para que cuando se recargue la
     * página se lea este atributo de la sesión.
     *
     * @param request Tiene la información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> nulo, ya que no se
     * necesita generar un evento
     */
    private EvnLiquidacionRegistro verAntiguoSistema(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String ver = request.getParameter(VER_ANTIGUO_SISTEMA);
        session.setAttribute(VER_ANTIGUO_SISTEMA, ver);

        this.preservarInfo(request);

        return null;
    }

    /**
     * Cuando se va a asociar un turno anterior a la solicitud, es necesario
     * validar que este turno exista y que sea un turno de registro de
     * documentos. Si lo es, la información del encabezado de la solicitud
     * perteneciente al turno anterior, se debe copiar a la solicitud que se
     * está diligenciando
     *
     * @param request Tiene la información del formulario
     * @return Un evento de tipo <CODE>EvnLiquidacionRegistro</CODE> con el
     * usuario y el turno que se quiere validar
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro validarTurnoAnterior(
            HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        // Turno turnoAnterior = new Turno();
        ValidacionLiquidacionRegistroException exception = new ValidacionLiquidacionRegistroException();
        session.removeAttribute(CTurno.TURNO_ANTERIOR);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        String idWfTurno = request.getParameter(CTurno.TURNO_ANTERIOR);
        String anno = null;
        String circ = null;
        String proceso = null;
        String turnoId = null;

        int pos1 = 0;
        int pos2 = idWfTurno.indexOf("-", 1);

        if (pos2 != -1) {
            anno = idWfTurno.substring(pos1, pos2);
            pos1 = pos2 + 1;
            pos2 = idWfTurno.indexOf("-", pos1);

            if ((pos1 != -1) && (pos2 != -1)) {
                circ = idWfTurno.substring(pos1, pos2);
                pos1 = pos2 + 1;
                pos2 = idWfTurno.indexOf("-", pos1);

                if ((pos1 != -1) && (pos2 != -1)) {
                    proceso = idWfTurno.substring(pos1, pos2);
                    pos1 = pos2 + 1;

                    if ((pos1 != -1) && (pos2 != -1)) {
                        turnoId = idWfTurno.substring(pos1);
                    }
                }
            }
        }
        if (circ != null) {
            if (!circ.equals(circulo.getIdCirculo())) {
                exception.addError("El turno no es del mismo circulo");
            }
        }
        Turno turno = new Turno();
        EvnLiquidacionRegistro evento = null;

        if ((idWfTurno != null) && (anno != null) && (circ != null)
                && (proceso != null) && (turnoId != null)) {
            idWfTurno = idWfTurno.toUpperCase();
            turno.setIdWorkflow(idWfTurno);
            turno.setAnio(anno);
            turno.setIdCirculo(circ);
            turno.setIdTurno(turnoId);
            turno.setIdProceso(Long.parseLong(proceso));

            if (((Proceso) request.getSession().getAttribute(WebKeys.PROCESO)).getIdProceso() != Long.parseLong(
                    proceso)) {
                exception.addError("El turno no es del mismo proceso");
            } else {
                // session.setAttribute(CTurno.TURNO_ANTERIOR, idWfTurno);
                evento = new EvnLiquidacionRegistro(usuario, turno);
            }
        } else {
            exception.addError("El codigo del turno no es completo");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        return evento;
    }

    /**
     * Cuando se escoge un subtipo de solicitud para la solicitud, se deben
     * cargar ciertos documentos que se pueden entregar dependiendo de este
     * subtipo de solicitud
     *
     * @param request Tiene la información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> nulo, ya que no se
     * necesita generar un evento
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    private EvnLiquidacionRegistro cargarDocumentos(HttpServletRequest request) {
        String sel = request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD);

        // La lista de subtipos de solicitud está en el ServletContext bajo la
        // llave LISTA_SUBTIPOS_SOLICITUD
        List subtipos = (List) request.getSession().getServletContext()
                .getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
        Iterator it = subtipos.iterator();
        List chequeo = new ArrayList();

        while (it.hasNext()) {
            SubtipoSolicitud sub = (SubtipoSolicitud) it.next();

            if (sel.equals(sub.getIdSubtipoSol())) {
                List checked = sub.getCheckItems();
                Iterator it2 = checked.iterator();

                while (it2.hasNext()) {
                    CheckItem ch = (CheckItem) it2.next();
                    chequeo.add(new ElementoLista(ch.getIdCheckItem(),
                            ch.getNombre()));
                }

                // Se guarda la lista con los documentos que se pueden entregar,
                // de acuerdo al subtipo de solicitud escogido
                request.getSession().setAttribute("LISTA_SUBTIPO_CHEQUEO",
                        chequeo);

                // Se quiere guardar este campo en la sesión, para que una
                // función de javascript tome este atributo y sepa donde poner
                // una imagen
                String ultimo = request.getParameter("ultimo_campo_editado");
                request.getSession().setAttribute("ultimo_campo_editado", ultimo);

                // Si el subtipo de solicitud que se escogió es de ANTIGUO
                // SISTEMA, se deben borrar todas las matrículas ingresadas
                // anteriormente
                if (sub.getNombre().equals("ANTIGUO SISTEMA")) {
                    request.getSession().removeAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
                }

                preservarInfo(request);

                return null;
            }
        }

        String ultimo = request.getParameter("ultimo_campo_editado");
        request.getSession().setAttribute("ultimo_campo_editado", ultimo);
        preservarInfo(request);

        return null;
    }

    /**
     * Dependiendo del tipo de acto escogido, se deben cargar los derechos
     * registrales correspondientes al tipo de acto escogido
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> nulo, ya que no se
     * necesita generar un evento
     * @throws AccionWebException
     * @see gov.sir.core.negocio.modelo.TipoActoDerechoRegistral
     */
    private EvnLiquidacionRegistro cargarDerechos(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        String tipo = request.getParameter(CActo.TIPO_ACTO);
        ValidacionParametrosActoException excepcion = new ValidacionParametrosActoException();

        if ((tipo == null) || tipo.equals("SIN_SELECCIONAR")) {
            excepcion.addError("Tipo de acto inválido");
        }

        preservarInfoLiquidar(request);

        if (excepcion.getErrores().size() > 0) {
            throw excepcion;
        }

        TipoActo tipoActo = new TipoActo();
        tipoActo.setIdTipoActo(tipo);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        return new EvnLiquidacionRegistro(usuarioAuriga, EvnLiquidacionRegistro.CARGAR_TIPO_ACTO, tipoActo);
    }

    /**
     * Dependiendo del la cuantia o valor tipo del acto editado, se debe cargar
     * el valor de los derechos
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE>
     * @throws AccionWebException
     * @see gov.sir.core.negocio.modelo.TipoActoDerechoRegistral
     */
    private EvnLiquidacionRegistro cargarValorDerechos(HttpServletRequest request) throws AccionWebException {

        String posicion = request.getParameter(CActo.POSICION);
        String cuantia = request.getParameter(CActo.CUANTIA_EDICION);
        Turno turnoAnterior = (Turno) request.getSession().getAttribute(CTurno.TURNO_ANTERIOR_OBJETO);
        preservarInfoLiquidar(request);
        ValidacionParametrosActoException excepcion = new ValidacionParametrosActoException();
        if (cuantia == null || cuantia.equals("")) {
            excepcion.addError("Valor del acto inválido");
        }
        if (excepcion.getErrores().size() > 0) {
            throw excepcion;
        }
        Acto acto = (Acto) request.getSession().getAttribute(CActo.ACTO
                + posicion);
        String[] valorCuantia = cuantia.split(",");
        String cuantiaAux = "";
        for (int i = 0; i < valorCuantia.length; i++) {
            cuantiaAux = cuantiaAux + valorCuantia[i];

        }
        try {
            acto.setCuantia(new Double(cuantia).doubleValue());
        } catch (Exception e) {
            try {
                acto.setCuantia(new Double(cuantiaAux).doubleValue());
            } catch (Exception e1) {
                excepcion.addError("Valor del acto inválido");
            }
        }

        if (excepcion.getErrores().size() > 0) {
            throw excepcion;
        }
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnLiquidacionRegistro evento = new EvnLiquidacionRegistro(usuarioAuriga, EvnLiquidacionRegistro.CARGAR_VALOR_DERECHOS, acto);
        evento.setTurnoAnterior(turnoAnterior);
        evento.setPosicion(Integer.parseInt(posicion) + 1);
        return evento;
    }

    /**
     * Para eliminar una acto de la solicitud, que fue ingresado anteriormente
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> nulo, ya que no se
     * necesita generar un evento
     */
    private EvnLiquidacionRegistro eliminarActo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer num = (Integer) session.getAttribute(CActo.NUM_ACTOS);
        int val = num.intValue();

        // En el parametro ITEM está el acto a eliminar
        String item = request.getParameter("ITEM");

        boolean antes = true;

        for (int i = 0; i < val; i++) {
            Integer actual = new Integer(i);

            if (antes) {
                String id = CActo.ACTO + actual.toString();

                if (id.equals(item)) {
                    antes = false;
                    session.removeAttribute(item);
                }
            } else {
                Integer mover = new Integer(i - 1);
                Acto itemActual = (Acto) session.getAttribute(CActo.ACTO
                        + actual.toString());
                session.setAttribute(CActo.ACTO + mover.toString(), itemActual);
                session.removeAttribute(CActo.ACTO + actual.toString());
            }
        }

        val--;
        session.setAttribute(CActo.NUM_ACTOS, new Integer(val));

        preservarInfoLiquidar(request);
        this.verificarHipotecaPatrimonioVendidos(request);
        
        return null;
    }

    /**
     * Recorre los actos asociados y al turno y verifica si se han vencido los
     * términos para los actos hipoteca y patrimonio de familia, si es así
     * coloca el respectivo mensaje de error en la sesión.
     *
     * @param request
     */
    private void verificarHipotecaPatrimonioVendidos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer num = (Integer) session.getAttribute(CActo.NUM_ACTOS);

        Boolean alertasDocumentos = (Boolean) session.getAttribute(CDocumento.ALERTAS_DOCUMENTOS);

        if (num != null) {

            int val = num.intValue();

            SolicitudRegistro solRegistro = (SolicitudRegistro) session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
            session.removeAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_PATRIMONIO);
            session.removeAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_HIPOTECA);

            for (int i = 0; i < val; i++) {

                String id = CActo.ACTO + i;
                Acto acto = (Acto) session.getAttribute(id);

                //VALIDAR SI SE VENCIÓ LOS TÉRMINOS PARA EL ACTO PATRIMONIO DE FAMILIA
                if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_PATRIMONIO_DE_FAMILIA)) {
                    String mensajeVencimientoTerminosPatrimonio = obtenerMensajeVencimientoTerminos(acto, solRegistro, alertasDocumentos);
                    if (mensajeVencimientoTerminosPatrimonio != null) {
                        session.setAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_PATRIMONIO, mensajeVencimientoTerminosPatrimonio);
                    }
                }
                //VALIDAR SI SE VENCIÓ LOS TÉRMINOS PARA EL ACTO PATRIMONIO DE FAMILIA
                if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_HIPOTECA)) {
                    String mensajeVencimientoTerminosHipoteca = obtenerMensajeVencimientoTerminos(acto, solRegistro, alertasDocumentos);
                    if (mensajeVencimientoTerminosHipoteca != null) {
                        session.setAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_HIPOTECA, mensajeVencimientoTerminosHipoteca);
                    }
                }

            }

        }

    }

    /**
     * Para agregar un acto a la solicitud. Con los datos ingresados por el
     * usuario, el servicio Hermod debe calcular el valor del acto
     *
     * @param request Los datos del formulario
     * @return Un evento <CODE>EvnLiquidacionRegistro</CODE> con el usuario y el
     * <CODE>Acto</CODE>
     * @see gov.sir.core.negocio.modelo.Acto
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro agregarActo(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionParametrosActoException excepcion = new ValidacionParametrosActoException();
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turnoAnterior = (Turno) request.getSession().getAttribute(CTurno.TURNO_ANTERIOR_OBJETO);
        // String nombreCampo = CActo.ACTO+ num.toString();
        // String acto= request.getParameter(nombreCampo);
        String tipoActo = request.getParameter(CActo.TIPO_ACTO);
        String tipoDerecho = request.getParameter(CActo.TIPO_DERECHO);
        String valorActo = request.getParameter(CActo.VALOR_ACTO);

        String seCobraImp = request.getParameter(CActo.COBRA_IMPUESTO);
        String cuantia = request.getParameter(CActo.CUANTIA);
        String tipoTarifa = request.getParameter(CActo.TIPO_TARIFA);
        String valorImp = request.getParameter(CActo.VALOR_IMPUESTO);
        String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);

        if ((tipoActo == null) || tipoActo.equals("SIN_SELECCIONAR")) {
            excepcion.addError("Tipo de acto inválido");
        }

        if ((tipoDerecho == null) || tipoDerecho.equals("SIN_SELECCIONAR")) {
            excepcion.addError("Tipo de derecho inválido");
        }

        if ((valorActo == null) || valorActo.equals("")) {
            excepcion.addError("Valor del acto inválido");
        }

        try {
            double v = Double.parseDouble(valorActo);
        } catch (NumberFormatException e) {
            excepcion.addError("Valor del acto inválido");
        }

        if (excepcion.getErrores().size() > 0) {
            preservarInfoLiquidar(request);
            throw excepcion;
        }

        // Se busca el tipo de acto escogido por el usuario
        Acto acto = new Acto();
        TipoActo actType = (TipoActo) session.getAttribute(CActo.TIPO_ACTO_CARGADO);
        if (actType == null) {
            excepcion.addError("No se encontró el tipo de acto.");
            preservarInfoLiquidar(request);
            throw excepcion;
        }

        acto.setTipoActo(actType);

        // Se busca el tipo de derecho registral escogido por el usuario
        List derechosreg = actType.getTiposDerechosRegistrales();
        Iterator itD = derechosreg.iterator();
        TipoActoDerechoRegistral def = null;

        while (itD.hasNext()) {
            TipoActoDerechoRegistral tadr = (TipoActoDerechoRegistral) itD.next();

            if (tipoDerecho.equals(tadr.getTipoDerechoRegistral()
                    .getIdTipoDerechoReg())) {
                def = tadr;
            }
        }

        acto.setTipoDerechoReg(def);

        double actValue = 0;

        try {
            actValue = Double.parseDouble(valorActo);
        } catch (NumberFormatException e) {
            excepcion.addError("Valor acto inválido");
        }

        if (actType.getTipoCalculo().getIdTipoCalculo().equals(CActo.MANUAL)) {
            if (seCobraImp != null) { // FUE CHEQUEADO EL CHECKBOX

                if ((valorImp == null) || valorImp.equals("")) {
                    excepcion.addError("Valor impuesto inválido");
                } else {
                    double impuestoValue = 0;

                    try {
                        impuestoValue = Double.parseDouble(valorImp);
                    } catch (NumberFormatException e) {
                        excepcion.addError("Valor impuesto inválido");
                    }

                    acto.setCobroImpuestos(true);
                    acto.setValorImpuestos(impuestoValue);

                    List tipoImp = (List) session.getServletContext()
                            .getAttribute(WebKeys.LISTA_TIPOS_IMPUESTO);
                    Iterator itImp = tipoImp.iterator();

                    while (itImp.hasNext()) {
                        TipoImpuesto impact = (TipoImpuesto) itImp.next();

                        if (tipoTarifa.equals(impact.getIdTipoImpuesto())) {
                            acto.setTipoImpuesto(impact);
                        }
                    }
                }
            }

            acto.setValor(actValue);
        } else {
            if (seCobraImp != null) { // FUE CHEQUEADO EL CHECKBOX

                if ((tipoTarifa == null)
                        || tipoTarifa.equals("SIN_SELECCIONAR")) {
                    excepcion.addError("Tipo de tarifa inválida");
                }
            }
        }

        if (excepcion.getErrores().size() > 0) {
            preservarInfoLiquidar(request);
            throw excepcion;
        }

        // Cuantía o cantidad de actos a registrar ingresada por el usuario
        acto.setCuantia(actValue);

        // Si el usuario decidió que sí se cobran impuestos, se obtienen los
        // impuestos que escogió el usuario
        if (seCobraImp != null) { // FUE CHEQUEADO EL CHECKBOX

            if (!actType.getTipoCalculo().getIdTipoCalculo().equals(CActo.MANUAL)) {
                acto.setCobroImpuestos(true);

                List tipoImp = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_IMPUESTO);
                Iterator itImp = tipoImp.iterator();

                while (itImp.hasNext()) {
                    TipoImpuesto impact = (TipoImpuesto) itImp.next();

                    if (tipoTarifa.equals(impact.getIdTipoImpuesto())) {
                        acto.setTipoImpuesto(impact);
                    }
                }
            }
        } else {
            acto.setCobroImpuestos(false);
        }

        preservarInfoLiquidar(request);

        Liquidacion liq = new LiquidacionTurnoRegistro();
        Solicitud sol = new Solicitud();
        sol.setCirculo(circulo);
        sol.setTurnoAnterior(turnoAnterior);
        liq.setSolicitud(sol);
        acto.setLiquidacion(liq);
        EvnLiquidacionRegistro evento = new EvnLiquidacionRegistro(usuario, acto);
        evento.setTurnoAnterior(turnoAnterior);

        SolicitudRegistro solRegistro = (SolicitudRegistro) session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
        if (solRegistro != null && solRegistro.getDocumento() != null) {
            evento.setSolicitudRegistro(solRegistro);
        }

        return evento;
    }

    /**
     * Eliminar de session los datos del acto.
     *
     * @param request petición del usuario
     */
    private void eliminarInfoActoAsociada(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CActo.ID_ACTO);
        session.removeAttribute(CActo.VALOR_ACTO);
        session.removeAttribute(CActo.TIPO_ACTO);
        session.removeAttribute(CActo.TIPO_DERECHO);
    }

    /**
     * Obtener todos los datos de la solicitud, para que el servicio Hermod los
     * valide y cree la solicitud
     *
     * @param request La información del formulario
     * @return Un evento <CODE>EvnLiquidacionRegistro</CODE> con el usuario y la
     * solicitud de registro creada
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro liquidarRegistro(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionLiquidacionRegistroException exception = new ValidacionLiquidacionRegistroException();
        SolicitudRegistro solicitud = new SolicitudRegistro();
        String turno = request.getParameter(CTurno.TURNO_ANTERIOR);
        Turno turnoObjeto = (Turno) session.getAttribute(CTurno.TURNO_ANTERIOR_OBJETO);

        String comentario = request.getParameter(CSolicitudRegistro.COMENTARIO);
        
        // En este arreglo de strings están los documentos entregados que el
        // usuario chequeó
        String[] entregados = request.getParameterValues(CSolicitudCheckedItem.DOCUMENTOS_ENTREGADOS);

        List matriculas = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

        String nomDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String idDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);

        String nomMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String idMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);

        String nomVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        String idVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);

        String idTipoOficina = request.getParameter(WebKeys.TIPO_OFICINA_I_N);
        idTipoOficina = (idTipoOficina == null) ? (idTipoOficina = "")
                : idTipoOficina;

        String oficinaInternacional = request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL);

        if ((idVereda == null) || nomDepto.equals("") || nomMunic.equals("") || idVereda.equals("")) {
            List listaVeredas = (List) request.getSession().getServletContext()
                    .getAttribute(WebKeys.LISTA_DEPARTAMENTOS);
            idVereda = ValidacionDatosOficinaOrigen.obtenerVereda(listaVeredas,
                    idDepto, idMunic);
        }

        String tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);

        String numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String id_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);

        String subtipo = request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        
        boolean isSubtipo = false;
        if(subtipo.equals("4") || subtipo.equals("2")){
            isSubtipo = true;
        }
        
        String numMatAgr = request.getParameter(NUM_MAT_AGR);
        request.getSession().setAttribute(NUM_MAT_AGR,numMatAgr);
        if((numMatAgr == null || numMatAgr.isEmpty()) && !isSubtipo){
            exception.addError("Debe diligenciar el campo Cantidad de Matriculas a Agregar");
        }
        
        List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
        String id_tipo_encabezado = request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO);

        // Antiguo Sistema
        String libro_tipo_as = request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS);
        libro_tipo_as = (libro_tipo_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS) : ""
                : libro_tipo_as;

        String libro_numero_as = request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
        libro_numero_as = (libro_numero_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS) : ""
                : libro_numero_as;

        String libro_pagina_as = request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
        libro_pagina_as = (libro_pagina_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS) : ""
                : libro_pagina_as;

        String libro_ano_as = request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS);
        libro_ano_as = (libro_ano_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS) : ""
                : libro_ano_as;

        String tomo_numero_as = request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS);
        tomo_numero_as = (tomo_numero_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS) : ""
                : tomo_numero_as;

        String tomo_pagina_as = request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS);
        tomo_pagina_as = (tomo_pagina_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS) : ""
                : tomo_pagina_as;

        String tomo_municipio_as = request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
        tomo_municipio_as = (tomo_municipio_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS) : ""
                : tomo_municipio_as;

        String tomo_ano_as = request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS);
        tomo_ano_as = (tomo_ano_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.TOMO_ANO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.TOMO_ANO_AS) : ""
                : tomo_ano_as;

        String documento_tipo_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
        documento_tipo_as = (documento_tipo_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS) : ""
                : documento_tipo_as;

        String documento_numero_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
        documento_numero_as = (documento_numero_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS) : ""
                : documento_numero_as;

        String documento_comentario_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);
        documento_comentario_as = (documento_comentario_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS) : ""
                : documento_comentario_as;

        String documento_fecha_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
        documento_fecha_as = (documento_fecha_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS) : ""
                : documento_fecha_as;

        String comentario_as = request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS);
        comentario_as = (comentario_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.COMENTARIO_AS) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.COMENTARIO_AS) : ""
                : comentario_as;

        String oficina_depto_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
        oficina_depto_id_as = (oficina_depto_id_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO) : ""
                : oficina_depto_id_as;

        String oficina_depto_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
        oficina_depto_nom_as = (oficina_depto_nom_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO) : ""
                : oficina_depto_nom_as;

        String oficina_muni_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
        oficina_muni_id_as = (oficina_muni_id_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC) : ""
                : oficina_muni_id_as;

        String oficina_muni_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
        oficina_muni_nom_as = (oficina_muni_nom_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC) : ""
                : oficina_muni_nom_as;

        String oficina_vereda_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
        oficina_vereda_id_as = (oficina_vereda_id_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA) : ""
                : oficina_vereda_id_as;

        String oficina_vereda_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
        oficina_vereda_nom_as = (oficina_vereda_nom_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA) : ""
                : oficina_vereda_nom_as;

        String oficina_oficia_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
        oficina_oficia_id_as = (oficina_oficia_id_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA) : ""
                : oficina_oficia_id_as;
        /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficina_oficia_version_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION);
        oficina_oficia_version_as = (oficina_oficia_version_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION) : "0"
                : oficina_oficia_version_as;

        String oficina_oficia_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
        oficina_oficia_nom_as = (oficina_oficia_nom_as == null)
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM) != null
                ? (String) session.getAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM) : ""
                : oficina_oficia_nom_as;

        if (session.getAttribute("antiguoSistemaExistente") != null) {
            oficina_depto_id_as = (String) session.getAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            oficina_depto_nom_as = (String) session.getAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
            oficina_muni_id_as = (String) session.getAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
            oficina_muni_nom_as = (String) session.getAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
            oficina_vereda_id_as = (String) session.getAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
            oficina_vereda_nom_as = (String) session.getAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
            oficina_oficia_id_as = (String) session.getAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
            oficina_oficia_nom_as = (String) session.getAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        }

        // ash.setNNomDocumento(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO);
        // //////////////////////////////////////////////////////////////////////
        if ((id_tipo_encabezado != null) && !id_tipo_encabezado.equals("")) {
            tipo_encabezado = existeId(tiposDoc, id_tipo_encabezado);

            if (tipo_encabezado.equals("")) {
                exception.addError(
                        "El tipo de documento digitado no correponde al tipo de documento seleccionado");
            }
        }

        subtipo = (subtipo == null) ? (subtipo = "") : subtipo;

        String id_encabezado = request.getParameter(CSolicitudRegistro.ID_ENCABEZADO);
        String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);

        id_encabezado = (id_encabezado == null) ? "" : id_encabezado;
        tipo_encabezado = (tipo_encabezado == null) ? "" : tipo_encabezado;
        id_tipo_encabezado = (id_tipo_encabezado == null) ? ""
                : id_tipo_encabezado;

        if ((subtipo.length() == 0) || subtipo.equals("SIN_SELECCIONAR")) {
            exception.addError("El subtipo es inválido");
        }

        if (tipo_encabezado.equals("SIN_SELECCIONAR")
                && id_tipo_encabezado.equals("")) {
            exception.addError("El campo tipo del encabezado es inválido");
        }

        if (id_encabezado.trim().equals("")
                && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_SENTENCIA)
                && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_AUTO)
                && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_REMATE)) {
            exception.addError("El campo número del encabezado del documento no puede estar vacio");
        }

        if ((fecha == null) || fecha.trim().equals("")) {
            exception.addError("La fecha del encabezado es inválida");
        }

        // El usuario escoge tipo de oficina nacional
        if (idTipoOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL)) {

            if (turnoObjeto == null) {
                if ((idVereda == null) || idVereda.trim().equals("")) {
                    exception.addError("La vereda o ciudad es inválida");
                }

                if ((idDepto == null) || idDepto.trim().equals("")) {
                    exception.addError("El departamento es inválido");
                }

                if ((idMunic == null) || idMunic.trim().equals("")) {
                    exception.addError("El municipio es inválido");
                }

                if ((id_oficina == null) || id_oficina.trim().equals("")) {
                    exception.addError("El tipo de oficina es inválido");
                }
            }

        } else {
            // tipo de oficina internacional
            if ((oficinaInternacional == null)
                    || (oficinaInternacional.length() == 0)) {
                exception.addError(
                        "El campo Oficina Ubicación Internacional no puede estar en blanco");
            }
        }

        Calendar fechaDocumento;
        if (fecha != null) {
            fechaDocumento = darFecha(fecha);
        } else {
            fechaDocumento = null;
        }

        if (fechaDocumento == null) {
            exception.addError("La fecha del documento es invalida");
        } else if (fechaDocumento.get(Calendar.YEAR) < ANIO_MINIMO) {
            exception.addError("La fecha del encabezado del documento no puede ser inferior a la fecha 01/01/" + ANIO_MINIMO + ".");
        }

        //BUG:3396
        //if (((matriculas == null) || matriculas.isEmpty()) &&
        //        !subtipo.equals(CSubtipoSolicitud.ANTIGUO_SISTEMA)) {
        //    exception.addError("Debe haber al menos una matricula asociada");
        //}
        List subS = (List) session.getServletContext().getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
        Iterator itS = subS.iterator();

        while (itS.hasNext()) {
            SubtipoSolicitud sub = (SubtipoSolicitud) itS.next();

            if (subtipo.equals(sub.getIdSubtipoSol())) {
                solicitud.setSubtipoSolicitud(sub);
            }
        }

        DatosAntiguoSistema das = null;

        // cargar datos antiguo sistema
        if (subtipo.equals(CSubtipoSolicitud.ANTIGUO_SISTEMA)) {
            das = new DatosAntiguoSistema();

            StringBuffer sb = new StringBuffer("");
            StringBuffer sbDocAs = new StringBuffer("");
            StringBuffer sbOfiAs = new StringBuffer("");
            StringBuffer sbTipoDocAS = new StringBuffer("");
            Date docFecha = null;

            sb.append(libro_tipo_as);
            sb.append(libro_numero_as);
            sb.append(libro_pagina_as);
            sb.append(libro_ano_as);
            sb.append(tomo_numero_as);
            sb.append(tomo_pagina_as);
            sb.append(tomo_municipio_as);
            sb.append(tomo_ano_as);
            sb.append(documento_tipo_as);
            sb.append(documento_numero_as);
            sb.append(documento_comentario_as);
            sb.append(documento_fecha_as);
            sb.append(oficina_depto_id_as);
            sb.append(oficina_depto_nom_as);
            sb.append(oficina_muni_id_as);
            sb.append(oficina_muni_nom_as);
            sb.append(oficina_vereda_id_as);
            sb.append(oficina_vereda_nom_as);
            sb.append(oficina_oficia_id_as);
            sb.append(oficina_oficia_nom_as);

            if (documento_fecha_as.length() > 0) {

                try {
                    docFecha = DateFormatUtil.parse(documento_fecha_as);
                } catch (ParseException e) {
                    exception.addError("El campo Fecha del documento de "
                            + "antiguo sistema no es válido");
                }
            }

            das.setLibroAnio(libro_ano_as);
            das.setLibroNumero(libro_numero_as);
            das.setLibroPagina(libro_pagina_as);
            das.setLibroTipo(libro_tipo_as);
            das.setTomoAnio(tomo_ano_as);
            das.setTomoMunicipio(tomo_municipio_as);
            das.setTomoNumero(tomo_numero_as);
            das.setTomoPagina(tomo_pagina_as);
            das.setComentario(comentario_as);

            Documento docAS = new Documento();
            TipoDocumento docTipo = new TipoDocumento();
            OficinaOrigen oficinaOrigenAS = new OficinaOrigen();
            Vereda veredaAS = new Vereda();

            veredaAS.setNombre(nomVereda);
            veredaAS.setIdVereda(oficina_vereda_id_as);
            veredaAS.setIdDepartamento(oficina_depto_id_as);
            veredaAS.setIdMunicipio(oficina_muni_id_as);

            oficinaOrigenAS.setIdOficinaOrigen(oficina_oficia_id_as);
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            if (oficina_oficia_version_as != null && !"".equals(oficina_oficia_version_as)) {
                oficinaOrigenAS.setVersion(Long.parseLong(oficina_oficia_version_as));
            }
            oficinaOrigenAS.setNombre(oficina_oficia_nom_as);
            oficinaOrigenAS.setVereda(veredaAS);

            // docTipo.setNombre(documento_tipo_as);
            docTipo.setIdTipoDocumento(documento_tipo_as);

            docAS.setComentario(documento_comentario_as);
            docAS.setFecha(docFecha);
            docAS.setNumero(documento_numero_as);

            // docAS.setTipoDocumento(docTipo);
            sbDocAs.append(documento_comentario_as);
            sbDocAs.append(docFecha);
            sbDocAs.append(documento_numero_as);
            sbDocAs.append(documento_tipo_as);

            sbOfiAs.append(oficina_oficia_id_as);
            sbOfiAs.append(oficina_oficia_nom_as);

            sbTipoDocAS.append(documento_tipo_as);

            if (sbDocAs.length() > 0) {
                if (sbTipoDocAS.length() > 0) {
                    das.setDocumento(docAS);
                    docAS.setTipoDocumento(docTipo);

                    if (sbOfiAs.length() > 0) {
                        docAS.setOficinaOrigen(oficinaOrigenAS);
                        Log.getInstance().debug(AWLiquidacionRegistro.class,
                                "[Colocando en Antiguo sistema Oficina de origen]");
                    }

                    Log.getInstance().debug(AWLiquidacionRegistro.class,
                            "[Colocando en Antiguo sistema tipo documento]");
                }

                Log.getInstance().debug(AWLiquidacionRegistro.class,
                        "[Colocando en datos de actiguo sistema el documento]");
            }

            // Puede existir una solicitud de registro con subtipo SIN MATRICULA que no tenga
            // asociados valores de antiguo sistema, en cuyo caso el workflow no seguiría a
            // a Antiguo Sistema, sino a reparto.
            /*logger.debug("\n\n\n [SB]" + sb.toString() + "\n\n\n");

            if (sb.length() == 0) {
                exception.addError("Debe ingresar al menos un dato " +
                    "en los campos de antiguo sistema");
            }*/
        }

        preservarInfo(request);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();
        
        String numMAtAgr = (String) request.getSession().getAttribute(NUM_MAT_AGR);
        liquidacion.setNumMatAgr(numMAtAgr);  

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

        solicitud.setCirculo(circulo);
        solicitud.setComentario(comentario);
        solicitud.setFecha(new Date(System.currentTimeMillis()));
        solicitud.setProceso(proceso);

        solicitud.setDatosAntiguoSistema(das);

        if ((turno != null) && !turno.equals("")) {
            Turno turnoAnterior = new Turno();
            turnoAnterior.setIdWorkflow(turno);
            solicitud.setTurnoAnterior(turnoAnterior);
        }

        if ((matriculas != null) && !matriculas.isEmpty()) {
            Iterator iter = matriculas.iterator();

            while (iter.hasNext()) {
                String matricula = (String) iter.next();
                SolicitudFolio solFolio = new SolicitudFolio();
                Folio folio = new Folio();
                folio.setIdMatricula(matricula);
                solFolio.setFolio(folio);
                solicitud.addSolicitudFolio(solFolio);
            }
        }

        String valorDepartamento = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String valorMunicipio = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String valorVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String idOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        String nomOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        String numOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);

        Documento documento = new Documento();
        documento.setFecha(fechaDocumento.getTime());

        // Set oficina Origen
        if (idTipoOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL)) {
            OficinaOrigen oficinaOrigen = new OficinaOrigen();

            if (nomOficina != null) {
                oficinaOrigen.setNombre(nomOficina);
            }

            if (numOficina != null) {
                oficinaOrigen.setNumero(numOficina);
            }

            oficinaOrigen.setIdOficinaOrigen(idOficina);
            /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            oficinaOrigen.setVersion((oficinaVersion != null && !"".equals(oficinaVersion)) ? Long.parseLong(oficinaVersion) : null);
            Vereda vereda = new Vereda();

            if (nomVereda != null) {
                vereda.setNombre(nomVereda);
            }

            vereda.setIdVereda(valorVereda);
            vereda.setIdDepartamento(valorDepartamento);
            vereda.setIdMunicipio(valorMunicipio);

            oficinaOrigen.setVereda(vereda);

            if (idOficina != null && idOficina.length() > 0) {
                documento.setOficinaOrigen(oficinaOrigen);
            } else {
                if (turnoObjeto != null) {
                    if (turnoObjeto.getSolicitud() != null) {
                        SolicitudRegistro solReg = (SolicitudRegistro) turnoObjeto.getSolicitud();
                        if (solReg != null && solReg.getDocumento() != null && solReg.getDocumento().getComentario() != null) {
                            documento.setComentario(solReg.getDocumento().getComentario());
                        }
                    }
                }
            }

        } else {
            documento.setOficinaInternacional(oficinaInternacional);
        }

        TipoDocumento tipoDoc = new TipoDocumento();
        tipoDoc.setIdTipoDocumento(tipo_encabezado);

        List tiposDocs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        Iterator itTiposDocs = tiposDocs.iterator();

        while (itTiposDocs.hasNext()) {
            ElementoLista elemento = (ElementoLista) itTiposDocs.next();

            if (elemento.getId().equals(tipo_encabezado)) {
                tipoDoc.setNombre(elemento.getValor());
            }
        }

        documento.setTipoDocumento(tipoDoc);
        documento.setNumero(id_encabezado);
        solicitud.setDocumento(documento);

        Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnLiquidacionRegistro evnLiq = new EvnLiquidacionRegistro(usuario, solicitud);
        evnLiq.setUsuarioNec(usuarioSIR);
        evnLiq.setUsuarioSIR(usuarioSIR);

        return evnLiq;
    }

    /**
     * Utilizado para preservar la información ingresada anteriormente cuando se
     * recarga la página en la segunda pantalla de registro
     * (turno.registro.liquidacion.view)
     *
     * @param request La información del formulario que se quiere preservar
     */
    private void preservarInfoLiquidar(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String tipoActo = request.getParameter(CActo.TIPO_ACTO);
        String idActo = request.getParameter(CActo.ID_ACTO);
        String tipoDerecho = request.getParameter(CActo.TIPO_DERECHO);
        String valorActo = request.getParameter(CActo.ACTO);
        String cobrarImp = request.getParameter(CActo.COBRA_IMPUESTO);
        String impuesto = request.getParameter(CActo.IMPUESTO);
        String tipocalc = request.getParameter(CActo.TIPO_CALCULO);
        String valorImp = request.getParameter(CActo.VALOR_IMPUESTO);
        String valorOtro = request.getParameter(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
        String descripcion = request.getParameter(CActo.DESCRIPCION);
        String tipoTarifa = request.getParameter(CLiquidacionTurnoRegistro.TIPO_TARIFA);
        String documento = request.getParameter(CCiudadano.DOCUMENTO);
        String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
        String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
        String telefono = request.getParameter(CCiudadano.TELEFONO);
        String nombre = request.getParameter(CCiudadano.NOMBRE);
        String tipoDoc = request.getParameter(CCiudadano.TIPODOC);

        if ((tipoActo != null) && !tipoActo.equals("SIN_SELECCIONAR")) {
            session.setAttribute(CActo.TIPO_ACTO, tipoActo);
        }

        if ((tipoDerecho != null) && !tipoDerecho.equals("SIN_SELECCIONAR")) {
            session.setAttribute(CActo.TIPO_ACTO, tipoActo);
        }

        if ((valorActo != null) && !valorActo.equals("")) {
            session.setAttribute(CActo.VALOR_ACTO, valorActo);
        }

        if ((cobrarImp != null) && !cobrarImp.equals("")) {
            session.setAttribute(CActo.COBRA_IMPUESTO, cobrarImp);
        }

        if ((impuesto != null) && !impuesto.equals("")) {
            session.setAttribute(CActo.IMPUESTO, impuesto);
        }

        if ((idActo != null) && !idActo.equals("")) {
            session.setAttribute(CActo.ID_ACTO, idActo);
        }

        if ((valorOtro != null) && !valorOtro.equals("")) {
            session.setAttribute(CActo.VALOR_OTRO_IMPUESTO, valorOtro);
        }

        if ((descripcion != null) && !descripcion.equals("")) {
            session.setAttribute(CActo.DESCRIPCION, descripcion);
        }

        if ((tipoTarifa != null) && !tipoTarifa.equals("SIN_SELECCIONAR")) {
            session.setAttribute(CLiquidacionTurnoRegistro.TIPO_TARIFA,
                    tipoTarifa);
        }

        if ((tipoDoc != null) && !tipoDoc.equals("SIN_SELECCIONAR")) {
            session.setAttribute(CCiudadano.TIPODOC, tipoDoc);
        }

        if ((documento != null) && !documento.equals("")) {
            session.setAttribute(CCiudadano.DOCUMENTO, documento);
        }

        if ((apellido1 != null) && !apellido1.equals("")) {
            session.setAttribute(CCiudadano.APELLIDO1, apellido1);
        }

        if ((apellido2 != null) && !apellido2.equals("")) {
            session.setAttribute(CCiudadano.APELLIDO2, apellido2);
        }

        if ((telefono != null) && !telefono.equals("")) {
            session.setAttribute(CCiudadano.TELEFONO, telefono);
        }

        if ((nombre != null) && !nombre.equals("")) {
            session.setAttribute(CCiudadano.NOMBRE, nombre);
        }

        if ((tipocalc != null) && !tipocalc.equals("")) {
            session.setAttribute(CActo.TIPO_CALCULO, tipocalc);
        }

        if ((valorImp != null) && !valorImp.equals("")) {
            session.setAttribute(CActo.VALOR_IMPUESTO, valorImp);
        }
    }

    /**
     * Utilizado para preservar la información ingresada anteriormente cuando se
     * recarga la página en la primera pantalla de registro
     * (turno.registro.view)
     *
     * @param request La información del formulario que se quiere preservar
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> nulo, ya que no se
     * necesita generar un evento
     */
    private EvnLiquidacionRegistro preservarInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if ((request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD) != null)
                && !request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD)
                        .equals("")) {
            session.setAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD,
                    request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD));
        }

        if ((request.getParameter("SUBTIPO_SOLICITUD") != null)
                && !request.getParameter("SUBTIPO_SOLICITUD").equals("")) {
            session.setAttribute("SUBTIPO_SOLICITUD",
                    request.getParameter("SUBTIPO_SOLICITUD"));
        }

        // Salvar información primera pantalla de registro
        session.setAttribute(WebKeys.TIPO_OFICINA_I_N,
                request.getParameter(WebKeys.TIPO_OFICINA_I_N));
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));

        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));

        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));

        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));

        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));

        /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));

        session.setAttribute(WebKeys.TEXT_OFICINA_INTERNACIONAL,
                request.getParameter(WebKeys.TEXT_OFICINA_INTERNACIONAL));

        session.setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL,
                request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL));

        session.setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO,
                request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO));

        session.setAttribute(CSolicitudRegistro.ID_ENCABEZADO,
                request.getParameter(CSolicitudRegistro.ID_ENCABEZADO));

        session.setAttribute(CSolicitudRegistro.CALENDAR,
                request.getParameter(CSolicitudRegistro.CALENDAR));

        // Salvar informacion AntiguoSistema
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS,
                request.getParameter(
                        CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS));

        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS,
                    request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS,
                    request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS,
                    request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS,
                    request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS,
                    request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS,
                    request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS,
                    request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS,
                    request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS,
                    request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS,
                    request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS,
                    request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.COMENTARIO_AS,
                    request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS));
        }

        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS,
                    request.getParameter(
                            CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS,
                    request.getParameter(
                            CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS,
                    request.getParameter(
                            CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS,
                    request.getParameter(
                            CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS,
                    request.getParameter(
                            CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS,
                    request.getParameter(
                            CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS,
                    request.getParameter(
                            CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));
        }

        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS,
                    request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS));
        }
        if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS) != null) {
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS,
                    request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS));
        }

        if (request.getParameterValues("DOCUMENTOS_ENTREGADOS") != null) {
            String[] entregados = request.getParameterValues(CSolicitudCheckedItem.DOCUMENTOS_ENTREGADOS);
            session.setAttribute("DOCUMENTOS_ENTREGADOS", entregados);
        }

        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO);

        if ((numCopiasStr != null) && !numCopiasStr.trim().equals("")) {
            session.setAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO,
                    numCopiasStr);
        }

        return null;
    }

    /**
     * Desasociar una matrícula de la solicitud
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> nulo, ya que no se
     * necesita generar un evento
     */
    private EvnLiquidacionRegistro eliminarMatricula(HttpServletRequest request) {
        List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

        if (matriculas == null) {
            matriculas = new ArrayList();
        }

        String[] matriculasElim = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);

        if (matriculasElim != null) {
            for (int i = 0; i < matriculasElim.length; i++) {
                String actual = matriculasElim[i];
                matriculas.remove(actual);
                //Si está en certificados asociados se elimina.pq
                List matriculasAsociadas = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
                if (matriculasAsociadas != null) {
                    matriculasAsociadas.remove(actual);
                }
                List certificadosAsociados = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);
                List solicitudesFolio;
                Iterator iterador;
                SolicitudFolio solicitudFolio;
                if (certificadosAsociados != null) {
                    SolicitudCertificado solCer;
                    for (int j = 0; j < certificadosAsociados.size(); j++) {
                        solCer = (SolicitudCertificado) certificadosAsociados.get(j);
                        solicitudesFolio = null;
                        if (solCer != null) {
                            solicitudesFolio = solCer.getSolicitudFolios();
                            iterador = solicitudesFolio.iterator();
                            while (iterador.hasNext()) {
                                solicitudFolio = (SolicitudFolio) iterador.next();
                                if (solicitudFolio != null && solicitudFolio.getIdMatricula() != null
                                        && solicitudFolio.getIdMatricula().equals(actual)) {
                                    certificadosAsociados.remove(j);
                                    j = certificadosAsociados.size() + 1;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        preservarInfo(request);

        return null;
    }

    /**
     * Desasociar una matrícula de la solicitud
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> nulo, ya que no se
     * necesita generar un evento
     */
    private EvnLiquidacionRegistro eliminarMatriculaSirMig(HttpServletRequest request) {
        List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);

        if (matriculas == null) {
            matriculas = new ArrayList();
        }

        String[] matriculasElim = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR_SIR_MIG);

        if (matriculasElim != null) {
            for (int i = 0; i < matriculasElim.length; i++) {
                String actual = matriculasElim[i];
                matriculas.remove(actual);
            }
        }

        preservarInfo(request);

        return null;
    }

    /**
     * Asociar una matrícula a la solicitud
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> con el usuario y el String
     * de la matrícula que se quiere asociar para que sea validada
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro agregarMatricula(HttpServletRequest request)
            throws AccionWebException {
        
        
        List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

        if (matriculas == null) {
            matriculas = new ArrayList();
        }
        
        String subtipo = request.getParameter(WebKeys.SUBTIPO_SOLICITUD);
        if(subtipo == null){
            subtipo = "";
        }
        boolean isSubtipo = false;
        if(subtipo.equals("4") || subtipo.equals("2")){
            isSubtipo = true;
        }
        
        String numMatAgr = request.getParameter(NUM_MAT_AGR);
        if((numMatAgr == null || numMatAgr.isEmpty()) && !isSubtipo){
            throw new ValidacionMatriculaRegistroException("Debe diligenciar el campo Cantidad de Matriculas a Agregar");
        }
        
        if(numMatAgr != null){
            request.getSession().setAttribute(WebKeys.NUM_MATRICULAS_LIQUIDADOR, numMatAgr);
        }

        Circulo circ = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        String matricula = circ.getIdCirculo() + "-";
        matricula += request.getParameter(AGREGAR_MATRICULA_REGISTRO);
        preservarInfo(request);
        
                try{
           HermodService hs = HermodService.getInstance();
           List turnos = null;
           String msg = null;
            turnos = hs.isMatriculaNotificacionDev(matricula);
            if(turnos != null && !turnos.isEmpty()) {
                msg = "La matricula se encuentra bloqueada por nota devolutiva con";
                if(turnos.size() <= 1){
                    msg += " el turno: " + turnos.get(0);
                } else{
                    Iterator itTurn = turnos.iterator();
                    msg += " los turnos: ";
                    while(itTurn.hasNext()){
                        String turn = (String) itTurn.next();
                        msg += turn + ", ";
                    }
                }
            } 
            
            if(msg != null){
                request.getSession().setAttribute(MATRICULA_BLOQUEADA_NOTA_DEV, msg);
            }
            
        } catch (HermodException e){
            System.out.println("ERROR: " + e.getMessage());
        }

        if ((matricula == null) || matricula.trim().equals("")) {
            throw new ValidacionMatriculaRegistroException("Matrícula inválida");
        }

        if (matriculas.contains(matricula)) {
            throw new ValidacionMatriculaRegistroException(
                    "La matrícula que quiere ingresar, ya fue ingresada");
        }
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);           

        //TFS 5351: Solo se puede asociar la ultima matricula eliminada del turno anterior 
        /*Turno turnoAnteriorObjeto = (Turno)request.getSession().getAttribute(CTurno.TURNO_ANTERIOR_OBJETO);
        if(turnoAnteriorObjeto!=null && turnoAnteriorObjeto.getIdMatriculaUltima()!=null){
        	if(!matricula.equals(turnoAnteriorObjeto.getIdMatriculaUltima())){
	        	throw new ValidacionMatriculaRegistroException(
	            "Sólo puede asignar la matrícula " + turnoAnteriorObjeto.getIdMatriculaUltima() 
	            + " la cual pertenecía al turno anterior");
        	}
        }*/
        /**
         * @autor Edgar Lora
         * @mantis 11987
         */
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();     
        
        try {              
            if (validacionesSIR.isEstadoFolioBloqueado(matricula)) {
                throw new ValidacionMatriculaRegistroException("La matricula que desea agregar tiene como estado 'Bloqueado'.");
            }
        } catch (GeneralSIRException ex) {
            if (ex.getMessage() != null) {
                throw new ValidacionMatriculaRegistroException(ex.getMessage());
            }
        }
        
        EvnLiquidacionRegistro evn = new EvnLiquidacionRegistro(usuario, matricula.toUpperCase());
        return evn;
    }

    /**
     * Asociar una matrícula que aún no se ha migrado a la solicitud
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> vacio String de la
     * matrícula sin migrar que se quiere asociar para que sea
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro agregarMatriculaSirMig(HttpServletRequest request)
            throws AccionWebException {
        List matriculasSirMig = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);

        if (matriculasSirMig == null) {
            matriculasSirMig = new ArrayList();
        }

        Circulo circ = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        String matricula = circ.getIdCirculo() + "-";
        matricula += request.getParameter(AGREGAR_MATRICULA_REGISTRO_MIG_INC);
        preservarInfo(request);

        if ((matricula == null) || matricula.trim().equals("")) {
            throw new ValidacionMatriculaRegistroException("Matrícula inválida");
        }

        if (matriculasSirMig.contains(matricula)) {
            throw new ValidacionMatriculaRegistroException(
                    "La matrícula que quiere ingresar, ya fue ingresada");
        }

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnLiquidacionRegistro evn = new EvnLiquidacionRegistro(usuario, matricula.toUpperCase());
        evn.setTipoEvento(EvnLiquidacionRegistro.VALIDAR_MATRICULA_MIG);
        return evn;
        //request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO,matriculasSirMig);

        //return null;
    }

    /**
     * Captura todos los datos de la solicitud y crea la liquidación para que
     * sea evaluada por hermod
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> con el usuario,
     * <CODE>LiquidacionTurnoRegistro</CODE>
     * @throws AccionWebException
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     */
    private EvnLiquidacionRegistro liquidar(HttpServletRequest request)
            throws AccionWebException {
        System.out.println("Desarollo1:: Ingresa a Liquidar Solicitud AWLiquidacionRegistro:");
        HttpSession session = request.getSession();
        
        List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS);
        listaAplicaciones = null;
        session.setAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS, listaAplicaciones);
        
        ValidacionParametrosRegistroException excepcion = new ValidacionParametrosRegistroException();
        LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();
        SolicitudRegistro solicitud = (SolicitudRegistro) session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
        Integer cantidadActos = (Integer) session.getAttribute(CActo.NUM_ACTOS);
        int val = cantidadActos.intValue();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
        
        String numMatAgr = (String) request.getSession().getAttribute(NUM_MAT_AGR);
        liquidacion.setNumMatAgr(numMatAgr);
        
        if (val == 0) {
            excepcion.addError("Debe agregar al menos un acto");
        } else {
            for (int i = 0; i < val; i++) {
                Integer actual = new Integer(i);
                String id = CActo.ACTO + actual.toString();
                Acto acto = (Acto) session.getAttribute(id);
                liquidacion.addActo(acto);
            }
        }

        String valorOtro = request.getParameter(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
        String descripcion = request.getParameter(CLiquidacionTurnoRegistro.DESCRIPCION);
        String tipoTarifa = request.getParameter(CLiquidacionTurnoRegistro.TIPO_TARIFA);
        String tipoDoc = request.getParameter(CCiudadano.TIPODOC);
        String documento = request.getParameter(CCiudadano.DOCUMENTO);
        String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
        String telefono = request.getParameter(CCiudadano.TELEFONO);

        // String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
        // String nombre = request.getParameter(CCiudadano.NOMBRE);
        this.preservarInfoLiquidar(request);

        if ((tipoDoc == null) || tipoDoc.equals("SIN_SELECCIONAR")) {
            excepcion.addError("Tipo de Documento Inválido");
        }

        if (tipoDoc.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
            if ((apellido1 == null) || apellido1.trim().equals("")) {
                excepcion.addError("Primer apellido inválido");
            }
        } else {
            if ((documento == null) || documento.trim().equals("")) {
                excepcion.addError("Documento Inválido");
            }

            if ((apellido1 == null) || apellido1.trim().equals("")) {
                excepcion.addError("Nombre ó razón social inválido");
            }

            /*
             * if (tipoDoc.equals(CCiudadano.TIPO_DOC_ID_NIT)) { if ((apellido1 ==
             * null) || apellido1.trim().equals("")) {
             * excepcion.addError("Primer apellido inválido"); } }
             */
        }
        
        if(!(tipoDoc.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA) || 
                        tipoDoc.equals(CCiudadano.TIPO_DOC_ID_CEDULA_EXTRANJERIA) ||
                        tipoDoc.equals(CCiudadano.TIPO_DOC_ID_NIT))){
                         try {
                                 Integer.parseInt(documento);
                         } catch (NumberFormatException nfe){
                                 excepcion.addError("El numero de documento no puede contener caracteres alfanumericos.");
                                 throw excepcion;
                         }
                }

        if ((valorOtro == null) || valorOtro.trim().equals("")) {
            if ((descripcion != null) && !descripcion.equals("")) {
                excepcion.addError("Debe ingresar un valor");
            }
        }

        if ((descripcion == null) || descripcion.trim().equals("")) {
            if ((valorOtro != null) && !valorOtro.equals("")) {
                excepcion.addError("Debe ingresar una descripcion");
            }
        }

        if ((valorOtro != null) && !valorOtro.trim().equals("")) {
            if ((descripcion != null) || !descripcion.equals("")) {
                try {
                    float v = Float.parseFloat(valorOtro);
                } catch (NumberFormatException e) {
                    excepcion.addError("Impuesto inválido");
                }
            }
        }

        List certificadosAsociados = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);
        Turno turnoAnterior = (Turno) session.getAttribute(CTurno.TURNO_ANTERIOR_OBJETO);

        if (excepcion.getErrores().size() > 0) {
            preservarInfoLiquidar(request);
            throw excepcion;
        }

        List matriculas = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

        List turnosFolioMig = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);

        SubtipoSolicitud subtipo = solicitud.getSubtipoSolicitud();

        if (!subtipo.getIdSubtipoSol().equals(CSubtipoSolicitud.TESTAMENTO)
                && !subtipo.getIdSubtipoSol().equals(CSubtipoSolicitud.ANTIGUO_SISTEMA) && turnoAnterior == null
                && (matriculas == null || matriculas.size() == 0) && (turnosFolioMig == null || turnosFolioMig.size() == 0)) {
            excepcion.addError("Debe haber al menos una matricula asociada, regresa a la solicitud e ingresela.");
            throw excepcion;
        } else {
            solicitud.setSolicitudFolios(new ArrayList());
            if ((matriculas != null) && !matriculas.isEmpty()) {
                Iterator iter = matriculas.iterator();

                while (iter.hasNext()) {
                    String matricula = (String) iter.next();
                    SolicitudFolio solFolio = new SolicitudFolio();
                    Folio folio = new Folio();
                    folio.setIdMatricula(matricula);
                    solFolio.setFolio(folio);
                    solicitud.addSolicitudFolio(solFolio);
                }
            }
        }

        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setTipoDoc(tipoDoc);

        if (documento != null) {
            ciudadano.setDocumento(documento);
        }

        if (telefono != null) {
            ciudadano.setTelefono(telefono);
        }

        if (apellido1 != null) {
            ciudadano.setApellido1(apellido1);
        }

        //Se setea el circulo del ciudadano
        ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        /*
         * if (nombre != null) { ciudadano.setNombre(nombre); } if (apellido2 !=
         * null) { ciudadano.setApellido2(apellido2); }
         */
        solicitud.setCiudadano(ciudadano);

        if ((valorOtro != null) && !valorOtro.equals("")) {
            liquidacion.setOtroImpuesto(descripcion);
            /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
             * Cambio de tipo de dato
             */
            liquidacion.setValorOtroImp(Double.valueOf(valorOtro));
        }

        solicitud.setCirculo(circulo);
        solicitud.setUsuario(usuarioNeg);
        solicitud.setProceso(proceso);

        // SE AGREGAN LOS CERTIFICADOS ASOCIADOS A LA SOLICITUD DE REGISTRO.
        if (certificadosAsociados != null) {
            Iterator it = certificadosAsociados.iterator();

            while (it.hasNext()) {
                SolicitudCertificado solCert = (SolicitudCertificado) it.next();
                solCert.setCiudadano(ciudadano);

                SolicitudAsociada solAsoc = new SolicitudAsociada();
                solAsoc.setSolicitudHija(solCert);

                solicitud.addSolicitudesHija(solAsoc);
            }
        }

        if (turnoAnterior != null) {
            solicitud.setTurnoAnterior(turnoAnterior);

            if (turnoAnterior != null) {
                if (turnoAnterior.getSolicitud() != null) {
                    SolicitudRegistro solReg = (SolicitudRegistro) turnoAnterior.getSolicitud();
                    if (solReg != null && solReg.getDocumento() != null) {
                        solicitud.setDocumento(solReg.getDocumento());
                    }
                }
            }

        }

        // -----------------------------------------------------------------------------------------
        // adicionar el turno vinculado
        gov.sir.core.negocio.modelo.SolicitudVinculada solicitudVinculada
                = (gov.sir.core.negocio.modelo.SolicitudVinculada) session.getAttribute(CTurno.SOLICITUD_VINCULADA_OBJETO);
        if (null != solicitudVinculada) {
            // TODO: db-insert
            solicitud.addSolicitudesVinculada(solicitudVinculada);
        }
        // -----------------------------------------------------------------------------------------

        liquidacion.setSolicitud(solicitud);
        liquidacion.setRol((Rol) request.getSession().getAttribute(WebKeys.ROL));

        String accion = request.getParameter(WebKeys.ACCION);

        EvnLiquidacionRegistro evento = new EvnLiquidacionRegistro(usuario,
                liquidacion, (Proceso) session.getAttribute(WebKeys.PROCESO),
                (Estacion) session.getAttribute(WebKeys.ESTACION), true,
                usuarioNeg);

        evento.setUID(request.getSession().getId());
        evento.setTurnosFolioMig(turnosFolioMig);
        evento.setCirculo(circulo);

        if (accion != null) {
            //NO SE DEBE IMPRIMIR CONSTANCIA SI SE VA A REGISTRAR EL PAGO
            if (accion.equals(AWLiquidacionRegistro.LIQUIDAR_CONTINUAR)) {
                evento.setImprimirConstancia(false);
            } else {
                evento.setImprimirConstancia(true);
            }
        }        
        
        return evento;
    }

    /**
     * Captura todos los datos de la solicitud y crea la liquidación para que
     * sea evaluada por hermod
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> con el usuario,
     * <CODE>LiquidacionTurnoRegistro</CODE>
     * @throws AccionWebException
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     */
    private EvnLiquidacionRegistro preliquidar(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionParametrosRegistroException excepcion = new ValidacionParametrosRegistroException();
        LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();
        SolicitudRegistro solicitud = new SolicitudRegistro();
        Integer cantidadActos = (Integer) session.getAttribute(CActo.NUM_ACTOS);
        int val = cantidadActos.intValue();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

        String valorOtro = request.getParameter(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
        String descripcion = request.getParameter(CLiquidacionTurnoRegistro.DESCRIPCION);

        if (val == 0) {
            excepcion.addError("Debe agregar al menos un acto");
        } else {
            for (int i = 0; i < val; i++) {
                Integer actual = new Integer(i);
                String id = CActo.ACTO + actual.toString();
                Acto acto = (Acto) session.getAttribute(id);
                liquidacion.addActo(acto);
            }
        }

        //agregar oficina de origen
//      En este arreglo de strings están los documentos entregados que el
        // usuario chequeó
        String[] entregados = request.getParameterValues(CSolicitudCheckedItem.DOCUMENTOS_ENTREGADOS);

        List matriculas = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

        //Valores dummies para poder realizar la preliquidacion: Se supondra que el documento viene de la misma oficina de origen.
        String nomDepto = "BOGOTA D C";
        String idDepto = "11";
        String nomMunic = "SANTAFE DE BOGOTA";
        String idMunic = "001";
        String nomVereda = "SANTAFE DE BOGOTA";
        String idVereda = "000";
        String idTipoOficina = "TIPO_OFICINA_NACIONAL";
        String oficinaInternacional = "";
        String tipo_oficina = "ALCALDIA";
        String numero_oficina = "ALCALDIA_MAYOR";
        String id_oficina = "554";
        //fin valores dummies

        String subtipo = request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
        String id_tipo_encabezado = request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO);

        if ((id_tipo_encabezado != null) && !id_tipo_encabezado.equals("")) {
            tipo_encabezado = existeId(tiposDoc, id_tipo_encabezado);

            if (tipo_encabezado.equals("")) {
                excepcion.addError(
                        "El tipo de documento digitado no correponde al tipo de documento seleccionado");
            }
        }

        if ((valorOtro == null) || valorOtro.trim().equals("")) {
            if ((descripcion != null) && !descripcion.equals("")) {
                excepcion.addError("Debe ingresar un valor");
            }
        }

        if ((descripcion == null) || descripcion.trim().equals("")) {
            if ((valorOtro != null) && !valorOtro.equals("")) {
                excepcion.addError("Debe ingresar una descripcion");
            }
        }

        if ((valorOtro != null) && !valorOtro.trim().equals("")) {
            if ((descripcion != null) || !descripcion.equals("")) {
                try {
                    float v = Float.parseFloat(valorOtro);
                } catch (NumberFormatException e) {
                    excepcion.addError("Impuesto inválido");
                }
            }
        }

        subtipo = (subtipo == null) ? (subtipo = "") : subtipo;

        String id_encabezado = request.getParameter(CSolicitudRegistro.ID_ENCABEZADO);
        String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);

        id_encabezado = (id_encabezado == null) ? "" : id_encabezado;
        tipo_encabezado = (tipo_encabezado == null) ? "" : tipo_encabezado;
        id_tipo_encabezado = (id_tipo_encabezado == null) ? ""
                : id_tipo_encabezado;

        if ((subtipo.length() == 0) || subtipo.equals("SIN_SELECCIONAR")) {
            excepcion.addError("El subtipo es inválido");
        }

        if (tipo_encabezado.equals("SIN_SELECCIONAR")
                && id_tipo_encabezado.equals("")) {
            excepcion.addError("El campo tipo del encabezado es inválido");
        }

        if (id_encabezado.trim().equals("")
                && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_SENTENCIA)
                && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_AUTO)) {
            excepcion.addError("El campo número del encabezado del documento no puede estar vacio");
        }

        if ((fecha == null) || fecha.trim().equals("")) {
            excepcion.addError("La fecha del encabezado es inválida");
        }

        // El usuario escoge tipo de oficina nacional
        if (idTipoOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL)) {

            if ((idVereda == null) || idVereda.trim().equals("")) {
                excepcion.addError("La vereda o ciudad es inválida");
            }

            if ((idDepto == null) || idDepto.trim().equals("")) {
                excepcion.addError("El departamento es inválido");
            }

            if ((idMunic == null) || idMunic.trim().equals("")) {
                excepcion.addError("El municipio es inválido");
            }

            if ((id_oficina == null) || id_oficina.trim().equals("")) {
                excepcion.addError("El tipo de oficina es inválido");
            }

        } else {
            // tipo de oficina internacional
            if ((oficinaInternacional == null)
                    || (oficinaInternacional.length() == 0)) {
                excepcion.addError(
                        "El campo Oficina Ubicación Internacional no puede estar en blanco");
            }
        }

        Calendar fechaDocumento;
        if (fecha != null) {
            fechaDocumento = darFecha(fecha);
        } else {
            fechaDocumento = null;
        }

        if (fechaDocumento == null) {
            excepcion.addError("La fecha del documento es invalida");
        } else if (fechaDocumento.get(Calendar.YEAR) < ANIO_MINIMO) {
            excepcion.addError("La fecha del encabezado del documento no puede ser inferior a la fecha 01/01/" + ANIO_MINIMO + ".");
        }

        String valorDepartamento = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String valorMunicipio = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String valorVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String idOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        String nomOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        String numOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);

        if (idOficina == null) {
            idOficina = ID_OFICINA_ORIGEN_DEFECTO;
        }
        Documento documento = new Documento();
        documento.setFecha(fechaDocumento.getTime());

        // Set oficina Origen
        if (idTipoOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL)) {
            OficinaOrigen oficinaOrigen = new OficinaOrigen();

            if (nomOficina != null) {
                oficinaOrigen.setNombre(nomOficina);
            }

            if (numOficina != null) {
                oficinaOrigen.setNumero(numOficina);
            }

            oficinaOrigen.setIdOficinaOrigen(idOficina);
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            oficinaOrigen.setVersion((oficinaVersion != null && !oficinaVersion.equals("")) ? Long.parseLong(oficinaVersion) : null);

            Vereda vereda = new Vereda();

            if (nomVereda != null) {
                vereda.setNombre(nomVereda);
            }

            vereda.setIdVereda(valorVereda);
            vereda.setIdDepartamento(valorDepartamento);
            vereda.setIdMunicipio(valorMunicipio);

            oficinaOrigen.setVereda(vereda);

            if (idOficina != null && idOficina.length() > 0) {
                documento.setOficinaOrigen(oficinaOrigen);
            }

        } else {
            documento.setOficinaInternacional(oficinaInternacional);
        }

        TipoDocumento tipoDoc = new TipoDocumento();
        tipoDoc.setIdTipoDocumento(tipo_encabezado);

        List tiposDocs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        Iterator itTiposDocs = tiposDocs.iterator();

        for (boolean termino = false; itTiposDocs.hasNext() && !termino;) {
            ElementoLista elemento = (ElementoLista) itTiposDocs.next();

            if (elemento.getId().equals(tipo_encabezado)) {
                tipoDoc.setNombre(elemento.getValor());
                termino = true;
            }
        }

        documento.setTipoDocumento(tipoDoc);
        documento.setNumero(id_encabezado);
        solicitud.setDocumento(documento);

        List actos = liquidacion.getActos();

        /*boolean testamento = tieneTestamentos(actos);
		SubtipoSolicitud subtipo = solicitud.getSubtipoSolicitud();*/
        if ((valorOtro != null) && !valorOtro.equals("")) {
            liquidacion.setOtroImpuesto(descripcion);
            try {
                liquidacion.setValorOtroImp(Float.parseFloat(valorOtro));
            } catch (NumberFormatException e) {
                excepcion.addError("Valor otro Impuesto inválido");
            }
        }

        //Seccion certificados asociados
        //Obtener el # de certificados
        List temp = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS);
        List tipos = null;
        tipos = new ArrayList();
        Iterator it = temp.iterator();
        String nombreTarifa;
        while (it.hasNext()) {
            ElementoLista el = (ElementoLista) it.next();
            nombreTarifa = el.getValor();
            String snumero = request.getParameter("NUMERO_" + nombreTarifa);
            if (snumero != null) {
                int numero = Integer.parseInt(snumero);

                SolicitudCertificado solCert = new SolicitudCertificado();
                solCert.setCirculo(circulo);
                solCert.setProceso(proceso);
                solCert.setDocumento(documento);
                solCert.setNumeroCertificados(numero);
                //solCert.set
                solCert.setPreliquidacion(true);

                LiquidacionTurnoCertificado liq = new LiquidacionTurnoCertificado();
                liq.setTipoTarifa(nombreTarifa);
                solCert.addLiquidacion(liq);

                SolicitudAsociada solAsoc = new SolicitudAsociada();
                solAsoc.setSolicitudHija(solCert);

                solicitud.addSolicitudesHija(solAsoc);

            }
        }

        //caso tarifa especial
        nombreTarifa = CTipoTarifa.TARIFA_ESPECIAL;
        String snumero = request.getParameter("NUMERO_" + nombreTarifa);
        if (snumero != null) {
            int numero = Integer.parseInt(snumero);
            if (numero > 0) {
                SolicitudCertificado solCert = new SolicitudCertificado();
                solCert.setCirculo(circulo);
                solCert.setProceso(proceso);
                solCert.setDocumento(documento);
                solCert.setNumeroCertificados(numero);
                //solCert.set
                solCert.setPreliquidacion(true);

                LiquidacionTurnoCertificado liq = new LiquidacionTurnoCertificado();
                liq.setTipoTarifa(nombreTarifa);
                solCert.addLiquidacion(liq);

                SolicitudAsociada solAsoc = new SolicitudAsociada();
                solAsoc.setSolicitudHija(solCert);

                solicitud.addSolicitudesHija(solAsoc);
            }
        }
        solicitud.setCirculo(circulo);
        solicitud.setUsuario(usuarioNeg);
        solicitud.setProceso(proceso);
        solicitud.addLiquidacion(liquidacion);

        liquidacion.setSolicitud(solicitud);
        session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, solicitud);

        Boolean alertasDocumentos = (Boolean) session.getAttribute(CDocumento.ALERTAS_DOCUMENTOS);

        for (Iterator it1 = actos.iterator(); it1.hasNext();) {
            Acto acto = (Acto) it1.next();
            if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_PATRIMONIO_DE_FAMILIA)) {
                String mensajeVencimientoTerminosPatrimonio = obtenerMensajeVencimientoTerminos(acto, solicitud, alertasDocumentos);
                if (mensajeVencimientoTerminosPatrimonio != null) {
                    session.setAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_PATRIMONIO, mensajeVencimientoTerminosPatrimonio);
                }
            }
            if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_HIPOTECA)) {
                String mensajeVencimientoTerminosHipoteca = obtenerMensajeVencimientoTerminos(acto, solicitud, alertasDocumentos);
                if (mensajeVencimientoTerminosHipoteca != null) {
                    session.setAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_HIPOTECA, mensajeVencimientoTerminosHipoteca);
                }
            }
        }

        String accion = request.getParameter(WebKeys.ACCION);

        EvnLiquidacionRegistro evento = new EvnLiquidacionRegistro(usuario,
                liquidacion, (Proceso) session.getAttribute(WebKeys.PROCESO),
                (Estacion) session.getAttribute(WebKeys.ESTACION), true,
                usuarioNeg);

        evento.setTipoEvento(EvnLiquidacionRegistro.PRELIQUIDAR);

        evento.setUID(request.getSession().getId());

        return evento;
    }

    /**
     * Captura todos los datos de la solicitud y crea la liquidación para que
     * sea evaluada por hermod
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> con el usuario,
     * <CODE>LiquidacionTurnoRegistro</CODE>
     * @throws AccionWebException
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     */
    private EvnLiquidacionRegistro regresarLiquidacion(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        session.removeAttribute(CActo.NUM_ACTOS);
        session.removeAttribute(AWLiquidacionRegistro.LISTA_ELEMENTOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute(AWLiquidacionRegistro.LISTA_TURNOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);

        //variables de acto
        session.removeAttribute(CActo.ID_ACTO);
        session.removeAttribute(CActo.TIPO_ACTO);
        session.removeAttribute(CActo.TIPO_DERECHO);
        session.removeAttribute(CActo.VALOR_ACTO);
        session.removeAttribute(CActo.COBRA_IMPUESTO);
        session.removeAttribute(CActo.CUANTIA);
        session.removeAttribute(CActo.TIPO_TARIFA);
        session.removeAttribute(CActo.VALOR_IMPUESTO);

        //limpiar variables de certificados asociados
        List temp = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS);
        Iterator it = temp.iterator();
        String nombreTarifa;
        while (it.hasNext()) {
            ElementoLista el = (ElementoLista) it.next();
            nombreTarifa = el.getValor();
            session.removeAttribute("NUMERO_" + nombreTarifa);
        }
        session.removeAttribute("NUMERO_" + CTipoTarifa.TARIFA_ESPECIAL);

        //remover atributos de respuesta de la preliquidacion
        session.removeAttribute(AWLiquidacionRegistro.PRELIQUIDACION);

        //remover atributos de encabezado de documento
        session.removeAttribute(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
        session.removeAttribute(CLiquidacionTurnoRegistro.DESCRIPCION);
        session.removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.CALENDAR);

        return null;
    }

    /**
     * Limpia la session para poder iniciar la preliquidacion.
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> con el usuario,
     * <CODE>LiquidacionTurnoRegistro</CODE>
     * @throws AccionWebException
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     */
    private EvnLiquidacionRegistro comenzarPreliquidacion(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        session.removeAttribute(CActo.NUM_ACTOS);
        session.removeAttribute(AWLiquidacionRegistro.LISTA_ELEMENTOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute(AWLiquidacionRegistro.LISTA_TURNOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);

        //remover atributos de encabezado de documento
        session.removeAttribute(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
        session.removeAttribute(CLiquidacionTurnoRegistro.DESCRIPCION);
        session.removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.CALENDAR);
        return null;
    }

    /**
     * Determina si dentro de la lista de actos se encuentra uno de tipo
     * TESTAMENTOS.
     *
     * @param actos
     * @return
     */
    private boolean tieneTestamentos(List actos) {
        boolean tieneTestamento = false;
        if (actos != null) {
            Iterator it = actos.iterator();
            while (it.hasNext()) {
                Acto acto = (Acto) it.next();
                if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_TESTAMENTOS) || acto.getTipoActo().getIdTipoActo().equals(CActo.ID_REVOCATORIA_TESTAMENTO)) {
                    tieneTestamento = true;
                    break;
                }
            }
        }

        return tieneTestamento;
    }

    /**
     * Método que permite realizar la edición de una liquidación.
     *
     * @param request La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> con el usuario,
     * <CODE>LiquidacionTurnoRegistro</CODE>
     * @throws AccionWebException
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     */
    private EvnLiquidacionRegistro editarLiquidacion(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
                
        ValidacionParametrosRegistroException excepcion = new ValidacionParametrosRegistroException();

        LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();
        SolicitudRegistro solicitud = (SolicitudRegistro) session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
        
        String numMatAgr = (String) request.getSession().getAttribute(NUM_MAT_AGR);
        
        liquidacion.setNumMatAgr(numMatAgr);
        Integer cantidadActos = (Integer) session.getAttribute(CActo.NUM_ACTOS);
        int val = cantidadActos.intValue();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

        if (val == 0) {
            excepcion.addError("Debe agregar al menos un acto");
        } else {
            for (int i = 0; i < val; i++) {
                Integer actual = new Integer(i);
                String id = CActo.ACTO + actual.toString();
                Acto acto = (Acto) session.getAttribute(id);
                liquidacion.addActo(acto);
            }
        }

        String valorOtro = request.getParameter(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
        String descripcion = request.getParameter(CLiquidacionTurnoRegistro.DESCRIPCION);
        String tipoTarifa = request.getParameter(CLiquidacionTurnoRegistro.TIPO_TARIFA);
        String tipoDoc = request.getParameter(CCiudadano.TIPODOC);
        String documento = request.getParameter(CCiudadano.DOCUMENTO);
        String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
        String telefono = request.getParameter(CCiudadano.TELEFONO);

        // String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
        // String nombre = request.getParameter(CCiudadano.NOMBRE);
        this.preservarInfoLiquidar(request);

        if ((tipoDoc == null) || tipoDoc.equals("SIN_SELECCIONAR")) {
            excepcion.addError("Tipo de Documento Inválido");
        }

        if (tipoDoc.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
            if ((apellido1 == null) || apellido1.trim().equals("")) {
                excepcion.addError("Primer apellido inválido");
            }
        } else {
            if ((documento == null) || documento.trim().equals("")) {
                excepcion.addError("Documento Inválido");
            }

            if ((apellido1 == null) || apellido1.trim().equals("")) {
                excepcion.addError("Nombre ó razón social inválido");
            }

            /*
			 * if (tipoDoc.equals(CCiudadano.TIPO_DOC_ID_NIT)) { if ((apellido1 ==
			 * null) || apellido1.trim().equals("")) {
			 * excepcion.addError("Primer apellido inválido"); } }
             */
        }
        
        if(!(tipoDoc.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA) || 
                        tipoDoc.equals(CCiudadano.TIPO_DOC_ID_CEDULA_EXTRANJERIA) ||
                        tipoDoc.equals(CCiudadano.TIPO_DOC_ID_NIT))){
                         try {
                                 Integer.parseInt(documento);
                         } catch (NumberFormatException nfe){
                                 excepcion.addError("El numero de documento no puede contener caracteres alfanumericos.");
                                 throw excepcion;
                         }
                }


        if ((valorOtro == null) || valorOtro.trim().equals("")) {
            if ((descripcion != null) && !descripcion.equals("")) {
                excepcion.addError("Debe ingresar un valor");
            }
        }

        if ((descripcion == null) || descripcion.trim().equals("")) {
            if ((valorOtro != null) && !valorOtro.equals("") && !valorOtro.equals("0") && !valorOtro.equals("0.0")) {
                excepcion.addError("Debe ingresar una descripcion");
            }
        }

        if ((valorOtro != null) && !valorOtro.trim().equals("")) {
            if ((descripcion != null) || !descripcion.equals("")) {
                try {
                    float v = Float.parseFloat(valorOtro);
                } catch (NumberFormatException e) {
                    excepcion.addError("Impuesto inválido");
                }
            }
        }

        List certificadosAsociados = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (excepcion.getErrores().size() > 0) {
            preservarInfoLiquidar(request);
            throw excepcion;
        }

        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setTipoDoc(tipoDoc);

        if (documento != null) {
            ciudadano.setDocumento(documento);
        }

        if (telefono != null) {
            ciudadano.setTelefono(telefono);
        }

        if (apellido1 != null) {
            ciudadano.setApellido1(apellido1);
        }

        //Se setea el circulo del ciudadano
        ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        /*
		 * if (nombre != null) { ciudadano.setNombre(nombre); } if (apellido2 !=
		 * null) { ciudadano.setApellido2(apellido2); }
         */
        solicitud.setCiudadano(ciudadano);

        if ((valorOtro != null) && !valorOtro.equals("")) {
            liquidacion.setOtroImpuesto(descripcion);
            /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                         * Cambio de tipo de dato
             */
            liquidacion.setValorOtroImp(Double.parseDouble(valorOtro));
        }

        solicitud.setCirculo(circulo);
        solicitud.setUsuario(usuarioNeg);
        solicitud.setProceso(proceso);
        solicitud.addLiquidacion(liquidacion);

        // SE AGREGAN LOS CERTIFICADOS ASOCIADOS A LA SOLICITUD DE REGISTRO.
        if (certificadosAsociados != null) {
            Iterator it = certificadosAsociados.iterator();

            while (it.hasNext()) {
                SolicitudCertificado solCert = (SolicitudCertificado) it.next();

                //si ya tienen un id de solicitud certificado no se añaden a la solicitud
                if (solCert.getIdSolicitud() == null || solCert.getIdSolicitud().equals("")) {
                    solCert.setCiudadano(ciudadano);

                    SolicitudAsociada solAsoc = new SolicitudAsociada();
                    solAsoc.setSolicitudHija(solCert);

                    solicitud.addSolicitudesHija(solAsoc);
                } else {
                    //se busca en las solicitudes asociadas a la solicitud si estan se coloca el flag de editado
                    List solicitudesHijas = solicitud.getSolicitudesHijas();
                    Iterator itHijas = solicitudesHijas.iterator();
                    while (itHijas.hasNext()) {
                        SolicitudAsociada solAsoc = (SolicitudAsociada) itHijas.next();
                        SolicitudCertificado solCertTemp = (SolicitudCertificado) solAsoc.getSolicitudHija();
                        if (solCert.getIdSolicitud().equals(solCertTemp.getIdSolicitud())) {
                            solCert.setEditado(true);
                        }
                    }
                }

            }
        }

        //Se recorre las solicitudes hijas buscando aquellas solicitudes que tengan id pero no tengan el flag de editado
        List solicitudesHijas = (List) solicitud.getSolicitudesHijas();
        int tamhijas = solicitudesHijas.size();
        for (int indice = 0; indice < tamhijas; indice++) {
            SolicitudAsociada solAsoc = (SolicitudAsociada) solicitudesHijas.get(indice);
            SolicitudCertificado solCertTemp = (SolicitudCertificado) solAsoc.getSolicitudHija();
            if (solCertTemp.getIdSolicitud() != null && !solCertTemp.getIdSolicitud().equals("")) {
                if (!solCertTemp.isEditado()) {
                    solCertTemp.setEliminar(true);
                }
            }
        }

        Turno turnoAnterior = (Turno) session.getAttribute(CTurno.TURNO_ANTERIOR_OBJETO);

        if (turnoAnterior != null) {
            solicitud.setTurnoAnterior(turnoAnterior);
        }

        liquidacion.setSolicitud(solicitud);
        liquidacion.setRol((Rol) request.getSession().getAttribute(WebKeys.ROL));
        session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, solicitud);

        boolean vieneCajero = false;
        Boolean vieneDeCajero = (Boolean) session.getAttribute(AWLiquidacionRegistro.VIENE_DE_CAJERO);
        if (vieneDeCajero != null) {
            vieneCajero = vieneDeCajero.booleanValue();
        }

        EvnLiquidacionRegistro evento = new EvnLiquidacionRegistro(usuario,
                liquidacion, (Proceso) session.getAttribute(WebKeys.PROCESO),
                (Estacion) session.getAttribute(WebKeys.ESTACION), true,
                usuarioNeg, EvnLiquidacionRegistro.EDITAR_LIQUIDACION);

        evento.setUID(request.getSession().getId());
        evento.setVieneDeCajero(vieneCajero);
        
        HermodService hs;
        List canalesXCirculo = new ArrayList() ;
        circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        try {
            hs = HermodService.getInstance();
           canalesXCirculo = hs.getCanalesRecaudoXCirculo(circulo);
        } catch (HermodException e) {
        }
        session.setAttribute(WebKeys.LISTA_CANALES_RECAUDO_CIRCULO, canalesXCirculo);  

        return evento;
    }

    // //////////////////////////////////////////////////////////////////////////////////////
    // /////////////////////// CERTIFICADOS ASOCIADOS ////////////////
    // //////////////////////////////////////////////////////////////////////////////////////
    /**
     * Recibe una matrícula para agregarla como certificado asociado, válida que
     * no este en las que ya se han agregado y de lo contrario envia una evento
     * a la acción de negocio para validar que el folio exista.
     *
     * @param request
     * @return evento respuesta
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro agregarMatriculaAsociada(
            HttpServletRequest request) throws AccionWebException {
        List matriculas = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);

        if (matriculas == null) {
            matriculas = new ArrayList();
        }

        String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS
                + CSolicitudAsociada.OPCION_MATRICULA);
        if ((tipoTarifa == null) || tipoTarifa.trim().equals("")) {
            throw new ValidacionMatriculaAsociadaRegistroException("Debe proporcionar tipo de tarifa válido.");
        } else {
            if (tipoTarifa.equals(WebKeys.SIN_SELECCIONAR)) {
                throw new ValidacionMatriculaAsociadaRegistroException("Seleccione por favor el tipo de tarifa.");
            }
        }

        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

        String idCirculo = "";
        if (circulo != null) {
            idCirculo = circulo.getIdCirculo();
            idCirculo = idCirculo + "-";
        }
        String[] matriculasVarias = null;
        String matricula = request.getParameter(CFolio.ID_MATRICULA);
        if (matricula == null || matricula.equals("")) {
            matriculasVarias = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR_MATSOL_REGISTRO);
        } else {
            matricula = idCirculo + matricula;
            /**
             * @autor Edgar Lora
             * @mantis 11987
             */
            ValidacionesSIR validacionesSIR = new ValidacionesSIR();
            try {
                if (validacionesSIR.isEstadoFolioBloqueado(matricula)) {
                    throw new ValidacionMatriculaAsociadaRegistroException("La matricula que desea asociar se encuentra en estado 'Bloqueado'.");
                }
            } catch (GeneralSIRException ex) {
                Logger.getLogger(AWLiquidacionRegistro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((matricula == null || matricula.equals("")) && (matriculasVarias == null || matriculasVarias.length == 0)) {
            throw new ValidacionMatriculaAsociadaRegistroException("Debe indicar matricula");
        }

        List matriculasRemove = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_REMOVE);
        if (matriculasRemove == null) {
            matriculasRemove = new ArrayList();
        }

        if (matricula != null && !matricula.equals("")) {
            if (matriculas.contains(matricula)) {
                request.getSession().setAttribute(CFolio.ID_MATRICULA, matricula);
                throw new ValidacionMatriculaAsociadaRegistroException(
                        "La matrícula que quiere ingresar, ya fue ingresada");
            }
            if (!matriculasRemove.contains(matricula)) {
                matriculasRemove.add(matricula);
            }
        } else if (matriculasVarias != null) {
            for (int i = 0; i < matriculasVarias.length; i++) {
                if (matriculas.contains(matriculasVarias[i])) {
                    request.getSession().setAttribute(CFolio.ID_MATRICULA, matriculasVarias[i]);
                    throw new ValidacionMatriculaAsociadaRegistroException("La matrícula que quiere ingresar, ya fue ingresada");
                }
                if (!matriculasRemove.contains(matriculasVarias[i])) {
                    matriculasRemove.add(matriculasVarias[i]);
                }
            }
        }

        request.getSession().setAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_REMOVE, matriculasRemove);

        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA);

        if ((numCopiasStr == null) || numCopiasStr.trim().equals("")) {
            request.getSession().setAttribute(CFolio.ID_MATRICULA, matricula);
            throw new ValidacionMatriculaAsociadaRegistroException(
                    "Debe Proporcionar un número de copias para el certificado asociado");
        }

        int numCopias = 0;

        try {
            numCopias = Integer.parseInt(numCopiasStr);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute(CFolio.ID_MATRICULA, matricula);
            throw new ValidacionMatriculaAsociadaRegistroException(
                    " no es un valor numérico válido");
        }

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnLiquidacionRegistro evento = new EvnLiquidacionRegistro(usuario,
                matricula.toUpperCase());
        evento.setTipoEvento(EvnLiquidacionRegistro.VALIDAR_MATRICULA_ASOCIADA);

        List matriculasRegistro = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

        evento.setMatriculasVarias(matriculasVarias);

        return evento;
    }

    /**
     * Elimina una matricula de la lista de certificados asociados que se han
     * añadido
     *
     * @param request
     * @return evento respuesta
     */
    private EvnLiquidacionRegistro eliminarMatriculaCertificadoAsociado(
            HttpServletRequest request) {
        List matriculas = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
        List certificadosAsociados = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (certificadosAsociados == null) {
            certificadosAsociados = new ArrayList();
        }

        Iterator it = null;

        if (matriculas == null) {
            matriculas = new ArrayList();
        }

        String[] matriculasElim = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);

        if (matriculasElim != null) {
            // SE ELIMINAN LAS SOLICITUDES DE CERTIFICADOS QUE ESTAN ASOCIADOS
            // CON LAS MATRICULAS A ELIMINAR
            for (int i = 0; i < matriculasElim.length; i++) {
                String actual = matriculasElim[i];
                it = certificadosAsociados.iterator();

                while (it.hasNext()) {
                    SolicitudCertificado solCert = (SolicitudCertificado) it.next();

                    if (!solCert.getSolicitudFolios().isEmpty()) {
                        SolicitudFolio solFolio = (SolicitudFolio) solCert.getSolicitudFolios()
                                .get(0);

                        if (solFolio.getIdMatricula().equals(actual)) {
                            certificadosAsociados.remove(solCert);

                            break;
                        }
                    }
                }
            }
            List matriculasRemove = (List) request.getSession().getAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_REMOVE);
            if (matriculasRemove == null) {
                matriculasRemove = new ArrayList();
            }
            // SE ELIMINAN LAS SOLICITUDES DE CERTIFICADOS QUE ESTAN ASOCIADOS
            // CON LAS MATRICULAS A ELIMINAR
            for (int i = 0; i < matriculasElim.length; i++) {
                String actual = matriculasElim[i];
                matriculas.remove(actual);

                //Se agregan en una lista las add para no cargarlas nuevamente en la lista de folios de registro 
                if (matriculasRemove.contains(actual)) {
                    matriculasRemove.remove(actual);
                }
            }
            request.getSession().setAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_REMOVE, matriculasRemove);
        }

        return null;
    }

    /**
     * Agrega certificados asociados a partir de los datos de antiguo sistema
     * del turno de registro.
     *
     * @param request
     * @return evento respuesta
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro agregarCertificadosAntiguoSistema(
            HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        List certificadosAsociados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (certificadosAsociados == null) {
            certificadosAsociados = new ArrayList();
        }

        ValidacionCertificadoAsociadoException excepcion = new ValidacionCertificadoAsociadoException();

        // SE VALIDA QUE HAYAN DATOS DE ANTIGUO SISTEMA
        Solicitud solicitud = (Solicitud) session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);

        if ((solicitud == null)
                || (solicitud.getDatosAntiguoSistema() == null)) {
            excepcion.addError(
                    "No se puede crear un certificado asociado porque el turno de registro no tiene información de antiguo sistema.");
            throw excepcion;
        }

        // SE RECUPERA LA INFORMACIÓN DE NÚMERO DE COPIAS Y DE UNIDADES DE
        // CERTIFICADOS.
        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_ANT_SIS);
        String numUnidadesStr = request.getParameter(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_ANT_SIS);
        String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS
                + CSolicitudAsociada.OPCION_ANT_SIS);

        if ((numCopiasStr == null) || numCopiasStr.trim().equals("")) {
            excepcion.addError(
                    "Debe proporcionar un número de copias para el certificado asociado.");
        }

        if ((numUnidadesStr == null) || numUnidadesStr.trim().equals("")) {
            excepcion.addError(
                    "Debe proporcionar un número de unidades para el certificado asociado.");
        }

        if ((tipoTarifa == null) || tipoTarifa.trim().equals("")) {
            excepcion.addError("Debe proporcionar tipo de tarifa válido.");
        }

        // SE VALIDA LA INFORMACIÓN INGRESADA.
        int numCopias = 0;

        try {
            numCopias = Integer.parseInt(numCopiasStr);

            if (numCopias <= 0) {
                excepcion.addError(" El número de copias debe ser mayor a uno");
            }
        } catch (NumberFormatException e) {
            excepcion.addError(
                    " El número de copias no es un valor numérico válido");
        }

        int numUnidades = 0;

        try {
            numUnidades = Integer.parseInt(numUnidadesStr);

            if (numUnidades <= 0) {
                excepcion.addError(
                        " El número de unidades debe ser mayor a uno");
            }
        } catch (NumberFormatException e) {
            excepcion.addError(
                    " El número de unidades no es un valor numérico válido");
        }

        if (excepcion.getErrores().size() > 0) {
            throw excepcion;
        }

        // SE BORRAN LOS CERTIFICADOS CREADOS CON ANTIGUO SISTEMA
        List elementosRemover = new ArrayList();

        for (int a = 0; a < certificadosAsociados.size(); a++) {
            SolicitudCertificado solCert = (SolicitudCertificado) certificadosAsociados.get(a);

            if (solCert.getDatosAntiguoSistema() != null) {
                elementosRemover.add(solCert);
            }
        }

        certificadosAsociados.removeAll(elementosRemover);

        // SE CREAN LOS NUEVOS CERTIFICADOS
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);

        Proceso proceso = new Proceso();
        proceso.setIdProceso(new BigDecimal(CProceso.PROCESO_CERTIFICADOS).longValue());

        DatosAntiguoSistema datAntSist = solicitud.getDatosAntiguoSistema();

        for (int a = 0; a < numUnidades; a++) {
            SolicitudCertificado solCert = new SolicitudCertificado();
            solCert.setProceso(proceso);
            solCert.setCirculo(circulo);
            solCert.setUsuario(usuario);
            solCert.setDatosAntiguoSistema(datAntSist);
            solCert.setNumeroCertificados(new BigDecimal(numCopiasStr).intValue());

            LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();
            liquidacion.setTipoTarifa(tipoTarifa);
            solCert.addLiquidacion(liquidacion);

            certificadosAsociados.add(solCert);
        }

        session.setAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
                certificadosAsociados);

        return null;
    }

    /**
     * Elimina certificados asociados a partir de los datos de antiguo sistema
     * del turno de registro.
     *
     * @param request
     * @return evento respuesta
     */
    private EvnLiquidacionRegistro eliminarCertificadosAntiguoSistema(
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        List certificadosAsociados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (certificadosAsociados == null) {
            certificadosAsociados = new ArrayList();
        }

        // SE BORRAN LOS CERTIFICADOS CREADOS CON ANTIGUO SISTEMA
        List elementosRemover = new ArrayList();

        for (int a = 0; a < certificadosAsociados.size(); a++) {
            SolicitudCertificado solCert = (SolicitudCertificado) certificadosAsociados.get(a);

            if (solCert.getDatosAntiguoSistema() != null) {
                elementosRemover.add(solCert);
            }
        }

        certificadosAsociados.removeAll(elementosRemover);

        session.setAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
                certificadosAsociados);

        return null;
    }

    /**
     * Agrega certificados asociados a partir de los datos de antiguo sistema
     * del turno de registro.
     *
     * @param request
     * @return evento respuesta
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro agregarCertificadosSegregacion(
            HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        List certificadosAsociados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (certificadosAsociados == null) {
            certificadosAsociados = new ArrayList();
        }

        ValidacionCertificadoAsociadoException excepcion = new ValidacionCertificadoAsociadoException();

        // SE RECUPERA LA INFORMACIÓN DE NÚMERO DE COPIAS Y DE UNIDADES DE
        // CERTIFICADOS.
        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_SEGREGACION);
        String numUnidadesStr = request.getParameter(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_SEGREGACION);
        String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS
                + CSolicitudAsociada.OPCION_SEGREGACION);
        request.getSession().setAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_MATRICULA, tipoTarifa);

        if ((numCopiasStr == null) || numCopiasStr.trim().equals("")) {
            excepcion.addError(
                    "Debe proporcionar un número de copias para el certificado asociado.");
        }

        if ((numUnidadesStr == null) || numUnidadesStr.trim().equals("")) {
            excepcion.addError(
                    "Debe proporcionar un número de unidades para el certificado asociado.");
        }

        if ((tipoTarifa == null) || tipoTarifa.trim().equals("") || tipoTarifa.equals(WebKeys.SIN_SELECCIONAR)) {
            excepcion.addError("Debe proporcionar tipo de tarifa válido.");
        }

        // SE VALIDA LA INFORMACIÓN INGRESADA.
        int numCopias = 0;

        try {
            numCopias = Integer.parseInt(numCopiasStr);

            if (numCopias <= 0) {
                excepcion.addError(" El número de copias debe ser mayor a uno");
            }
        } catch (NumberFormatException e) {
            excepcion.addError(
                    " El número de copias no es un valor numérico válido");
        }

        int numUnidades = 0;

        try {
            numUnidades = Integer.parseInt(numUnidadesStr);

            if (numUnidades <= 0) {
                excepcion.addError(
                        " El número de unidades de certificados asociados debe ser mayor a uno");
            }
        } catch (NumberFormatException e) {
            excepcion.addError(
                    " El número de unidades de certificados asociados no es un valor numérico válido");
        }

        if (excepcion.getErrores().size() > 0) {
            throw excepcion;
        }

        // SE BORRAN LOS CERTIFICADOS CREADOS CON SEGREGACIÓN
        List elementosRemover = new ArrayList();

        for (int a = 0; a < certificadosAsociados.size(); a++) {
            SolicitudCertificado solCert = (SolicitudCertificado) certificadosAsociados.get(a);

            if (solCert.getComentario() != null) {
                elementosRemover.add(solCert);
            }
        }

        certificadosAsociados.removeAll(elementosRemover);

        // SE CREAN LOS NUEVOS CERTIFICADOS
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);

        Proceso proceso = new Proceso();
        proceso.setIdProceso(new BigDecimal(CProceso.PROCESO_CERTIFICADOS).longValue());

        for (int a = 0; a < numUnidades; a++) {
            SolicitudCertificado solCert = new SolicitudCertificado();
            solCert.setProceso(proceso);
            solCert.setCirculo(circulo);
            solCert.setUsuario(usuario);

            String TEXTO_DEFECTO = "Certificado asociado a partir de una segregación";
            solCert.setComentario(TEXTO_DEFECTO);

            LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();
            liquidacion.setTipoTarifa(tipoTarifa);
            solCert.addLiquidacion(liquidacion);

            solCert.setNumeroCertificados(new BigDecimal(numCopiasStr).intValue());
            certificadosAsociados.add(solCert);
        }

        session.setAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
                certificadosAsociados);

        return null;
    }

    /**
     * Elimina certificados asociados a partir de los datos de antiguo sistema
     * del turno de registro.
     *
     * @param request
     * @return evento respuesta
     */
    private EvnLiquidacionRegistro eliminarCertificadosSegregacion(
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        List certificadosAsociados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (certificadosAsociados == null) {
            certificadosAsociados = new ArrayList();
        }

        // SE BORRAN LOS CERTIFICADOS CREADOS CON SEGREGACIÓN
        List elementosRemover = new ArrayList();

        for (int a = 0; a < certificadosAsociados.size(); a++) {
            SolicitudCertificado solCert = (SolicitudCertificado) certificadosAsociados.get(a);

            if (solCert.getComentario() != null) {
                elementosRemover.add(solCert);
            }
        }

        certificadosAsociados.removeAll(elementosRemover);

        session.setAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
                certificadosAsociados);

        return null;
    }

    /**
     * @param matricula
     * @param request
     * @return evento respuesta
     */
    private EvnLiquidacionRegistro adicionarCertificadoAsociado(
            String matricula, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);

        Proceso proceso = new Proceso();
        proceso.setIdProceso(new BigDecimal(CProceso.PROCESO_CERTIFICADOS).longValue());

        SolicitudCertificado solCert = new SolicitudCertificado();
        solCert.setProceso(proceso);
        solCert.setCirculo(circulo);
        solCert.setUsuario(usuario);

        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA);
        String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS
                + CSolicitudAsociada.OPCION_MATRICULA);
        request.getSession().setAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_MATRICULA, tipoTarifa);

        // SE LE COLOCA LA MATRÍCULA QUE SE QUIERE ASOCIAR.
        SolicitudFolio solFolio = new SolicitudFolio();
        Folio folio = new Folio();
        folio.setIdMatricula(matricula);
        solFolio.setIdMatricula(matricula);
        solFolio.setFolio(folio);
        solCert.addSolicitudFolio(solFolio);

        // SE LE COLOCA LOS DEMAS ATRIBUTOS QUE SE REQUIEREN.
        // solCert.setNumImpresiones(new BigDecimal(numCopiasStr).intValue());
        solCert.setNumeroCertificados(new BigDecimal(numCopiasStr).intValue());

        LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();
        liquidacion.setTipoTarifa(tipoTarifa);
        solCert.addLiquidacion(liquidacion);

        List certificadosAsociados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (certificadosAsociados == null) {
            certificadosAsociados = new ArrayList();
        }

        certificadosAsociados.add(solCert);
        session.setAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
                certificadosAsociados);

        return null;
    }

    /**
     * Método que se encarga de subir a la sesión los certificados asociados que
     * tiene una solicitud. Es usado cuando se desea editar una liquidación
     * existente y que aún no ha sido pagada.
     *
     * @param List certificados
     * @param request
     * @return evento respuesta
     */
    private void cargarCertificadoAsociados(HttpSession session, List certificados) {

        List matriculasAsociadas = new ArrayList();
        List certificadosAsociados = new ArrayList();

        List solicitudFolios = null;
        Iterator itSolFolios = null;

        Iterator itCertificados = certificados.iterator();
        while (itCertificados.hasNext()) {
            SolicitudAsociada solAsociada = (SolicitudAsociada) itCertificados.next();

            SolicitudCertificado solCertificado = (SolicitudCertificado) solAsociada.getSolicitudHija();
            certificadosAsociados.add(solCertificado);

            if (solCertificado.getSolicitudFolios().size() > 0) {
                solicitudFolios = solCertificado.getSolicitudFolios();
                itSolFolios = solicitudFolios.iterator();

                while (itSolFolios.hasNext()) {
                    SolicitudFolio solFolio = (SolicitudFolio) itSolFolios.next();
                    matriculasAsociadas.add(solFolio.getIdMatricula());
                }
            }
        }

        session.setAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO, matriculasAsociadas);
        session.setAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS, certificadosAsociados);
    }

    // FIN DE CERTIFICADOS ASOCIADOS.
    /**
     * @param request
     * @return evento respuesta
     * @throws ValidacionParametrosException
     */
    private EvnLiquidacionRegistro buscarSolicitud(HttpServletRequest request)
            throws ValidacionParametrosException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        request.getSession().removeAttribute(AWLiquidacionRegistro.SOLICITUD);

        // obtener parametros
        String idSolicitud = request.getParameter(AWLiquidacionRegistro.ID_SOLICITUD);
        request.getSession().setAttribute(AWLiquidacionRegistro.ID_SOLICITUD,
                idSolicitud);

        // validar parametros
        if ((idSolicitud == null) || idSolicitud.equals("")) {
            exception.addError("Numero de solicitud vacio.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // creacion del evento
        EvnLiquidacionRegistro evento = new EvnLiquidacionRegistro(usuario);
        Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        evento.setTipoEvento(EvnLiquidacionRegistro.BUSCAR_SOLICITUD);
        evento.setIdSolicitud(idSolicitud);
        evento.setUsuarioNec(usuarioSIR);
        evento.setUsuarioSIR(usuarioSIR);

        return evento;
    }

    /**
     * Método que permite consultar una solicitud. junto con sus liquidaciones,
     * con el objeto de poder editarlas.
     *
     * @param request
     * @return evento respuesta
     * @throws ValidacionParametrosException
     */
    private EvnLiquidacionRegistro buscarSolicitudEdicion(HttpServletRequest request)
            throws ValidacionParametrosException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        request.getSession().removeAttribute(AWLiquidacionRegistro.SOLICITUD);

        // obtener parametros
        String idSolicitud = request.getParameter(AWLiquidacionRegistro.ID_SOLICITUD);
        request.getSession().setAttribute(AWLiquidacionRegistro.ID_SOLICITUD,
                idSolicitud);

        String vieneDeCajero = request.getParameter(AWLiquidacionRegistro.VIENE_DE_CAJERO);
        if (vieneDeCajero != null) {
            if (!vieneDeCajero.equals("")) {
                request.getSession().setAttribute(AWLiquidacionRegistro.VIENE_DE_CAJERO, new Boolean(true));
            }
        }

        // validar parametros
        if ((idSolicitud == null) || idSolicitud.equals("")) {
            exception.addError("Número de solicitud vacio.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // creacion del evento
        EvnLiquidacionRegistro evento = new EvnLiquidacionRegistro(usuario);
        evento.setTipoEvento(EvnLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION);
        evento.setIdSolicitud(idSolicitud);

        return evento;
    }

    /**
     * @param request
     * @return evento respuesta
     * @throws ValidacionParametrosException
     */
    private EvnLiquidacionRegistro validarSolicitud(HttpServletRequest request)
            throws ValidacionParametrosException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);

        // obtener parametros
        String idSolicitud = request.getParameter(AWLiquidacionRegistro.ID_SOLICITUD);
        request.getSession().setAttribute(AWLiquidacionRegistro.ID_SOLICITUD,
                idSolicitud);

        // validar parametros
        if ((idSolicitud == null) || idSolicitud.equals("")) {
            exception.addError("Numero de solicitud vacio.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // creacion del evento
        EvnLiquidacionRegistro evento = new EvnLiquidacionRegistro(usuario);
        evento.setTipoEvento(EvnLiquidacionRegistro.VALIDAR_SOLICITUD);
        evento.setIdSolicitud(idSolicitud);
        evento.setEstacion(estacion);

        return evento;
    }

    /**
     * Permite incrementar el secuencial de recibos.
     *
     * @param request
     * @return EvnLiquidacionRegistro
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro incrementarSecuencialRecibo(
            HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        String motivo = request.getParameter(WebKeys.MOTIVO_INCREMENTO_SECUENCIAL);

        if ((motivo == null) || motivo.equals("")) {
            ValidacionParametrosException e = new ValidacionParametrosException();
            e.addError(
                    "no se ha ingresado el motivo del incremento del secuencial de recibo");
            throw e;
        }

        // Obtener atributos del request.
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String idCirculo = circulo.getIdCirculo();
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

        EvnLiquidacionRegistro eventoReg = new EvnLiquidacionRegistro(usuarioAuriga,
                EvnLiquidacionRegistro.INCREMENTAR_SECUENCIAL_RECIBO, null,
                fase, estacion, EvnCertificado.INCREMENTAR_SECUENCIAL_RECIBO,
                usuario);
        eventoReg.setMotivo(motivo);

        return eventoReg;
    }

    /**
     * Permite incrementar el secuencial de recibos.
     *
     * @param request
     * @return EvnLiquidacionRegistro
     * @throws AccionWebException
     * @throws CrearTurnoException
     */
    private EvnLiquidacionRegistro crearTurnoRegistro(
            HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        SolicitudRegistro sol = (SolicitudRegistro) session.getAttribute(AWLiquidacionRegistro.SOLICITUD);
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Estacion estacion = (Estacion) session.getAttribute((WebKeys.ESTACION));
        if (sol == null) {
            throw new CrearTurnoException(
                    "Es necesario asociar una solitiud para crear el turno.");
        }

        // Obtener atributos del request.
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);

        // obtener datos de la lista de chequeo
        List checkItems = sol.getSubtipoSolicitud().getCheckItems();
        List checkitemsSi = new ArrayList();
        String[] llavesChe = request.getParameterValues("DOCUMENTOS_ENTREGADOS");
        request.getSession().setAttribute("DOCUMENTOS_ENTREGADOS", llavesChe);

        if (llavesChe != null) {
            for (int i = 0; i < llavesChe.length; i++) {
                String idcheck = llavesChe[i];
                Iterator ic = checkItems.iterator();

                for (; ic.hasNext();) {
                    CheckItem check = (CheckItem) ic.next();

                    if (check.getIdCheckItem().equals(idcheck)) {
                        checkitemsSi.add(check);
                    }
                }
            }
        }

        Iterator ic = checkItems.iterator();

        for (; ic.hasNext();) {
            CheckItem check = (CheckItem) ic.next();

            if (check.isObligatorio()) {
                Iterator iob = checkitemsSi.iterator();
                boolean paso = false;

                for (; iob.hasNext();) {
                    CheckItem si = (CheckItem) iob.next();

                    if (si.getNombre().equals(check.getNombre())) {
                        paso = true;
                    }
                }

                if (!paso) {
                    throw new CrearTurnoException(check.getNombre()
                            + " es obligatorio para la solicitud.");
                }
            }
        }

        // Bug: Que Salga Impreso el DD MM YYY a las XX XX XX
        // Obtener fecha impresion
        String fechaImpresion = "";
        // Date fecha = new Date();
        // fechaImpresion = this.darFormato(fecha);

        fechaImpresion = getFechaImpresionFormatted();

        if (fechaImpresion == null) {
            throw new AccionWebException("Fecha invalida");
        }
        List matriculas = (List) session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
        
        String UID = request.getSession().getId();

        EvnLiquidacionRegistro eventoReg = new EvnLiquidacionRegistro(usuarioAuriga);
        eventoReg.setTipoEvento(EvnLiquidacionRegistro.CREAR_TURNO);
        eventoReg.setSolicitudRegistro(sol);
        eventoReg.setUsuarioSIR(usuario);
        eventoReg.setFechaImpresion(fechaImpresion);
        eventoReg.setCirculo(circulo);
        eventoReg.setUID(UID);
        eventoReg.setEstacion(estacion);
        eventoReg.setMatriculasLiquidador(matriculas);

        session.removeAttribute(WebKeys.PAGO);

        return eventoReg;
    }

    /**
     * Lista los turnos de registro validos para agregar certificados asociados.
     *
     * @param request
     * @return EvnLiquidacionRegistro
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro listarTurnosRegistroParaAgregarCertificadosAsociados(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();

        // Obtener atributos del request.
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        EvnLiquidacionRegistro eventoReg = new EvnLiquidacionRegistro(usuarioAuriga);
        eventoReg.setTipoEvento(EvnLiquidacionRegistro.LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        eventoReg.setCirculo(circulo);

        return eventoReg;
    }

    /**
     * Coloca el turno en la session y lleva a la pantalla para agregar los
     * certificados asociados al turno
     *
     * @param request
     * @return EvnLiquidacionRegistro
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro solicitarAgregarCertificadosAsociadosTurno(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();

        // Obtener atributos del request.
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        //turno
        String sturno = "";
        sturno = request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR);
        session.setAttribute(WebKeys.TITULO_CHECKBOX_ELIMINAR, sturno);

        //obtener el objeto turno
        List turnos = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_TURNOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        Iterator it = turnos.iterator();

        Turno t = null;
        while (it.hasNext()) {
            Turno turno = (Turno) it.next();
            if (turno.getIdWorkflow().equals(sturno)) {
                t = turno;
            }
        }

        if (t == null) {
            ValidacionParametrosException val = new ValidacionParametrosException();
            val.addError("No se encontro el turno seleccionado");
            throw val;
        }

        EvnLiquidacionRegistro eventoReg = new EvnLiquidacionRegistro(usuarioAuriga);
        eventoReg.setTipoEvento(EvnLiquidacionRegistro.SOLICITAR_AGREGAR_CERTIFICADOS_ASOCIADOS);
        eventoReg.setTurno(t);

        return eventoReg;
    }

    /**
     * Coloca el turno en la session y lleva a la pantalla para agregar los
     * certificados asociados al turno
     *
     * @param request
     * @return EvnLiquidacionRegistro
     * @throws AccionWebException
     */
    private EvnLiquidacionRegistro agregarCertificadosAsociadosTurno(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();

        // Obtener atributos del request.
        /*
         * Proceso: Se obtiene los certificados asociados a agregar y se compara con los certificados asociados ya registrados
         * si hay algun certificado con la misma matricula que uno ya registrado se lanza una excepcion.
         * 
         * Despues de validar los certificados asociados se crean las respectivas solicitudes asociadas y se envian en una lista
         * a la accion de negocio
         */
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
        List certificadosAsociadosYaRegistrados = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS_YA_INGRESADOS_AGREGAR_TURNO);
        List certificadosAsociadosAAgregar = (List) session.getAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        ValidacionParametrosException e = new ValidacionParametrosException();

        //Se validan los certificados asociados a agregar
        Iterator it = certificadosAsociadosAAgregar.iterator();
        while (it.hasNext()) {
            SolicitudCertificado solCertAAgregar = (SolicitudCertificado) it.next();
            if (solCertAAgregar.getSolicitudFolios().size() > 0) {
                SolicitudFolio solFolioAAgregar = (SolicitudFolio) solCertAAgregar.getSolicitudFolios().get(0);
                Iterator it2 = certificadosAsociadosYaRegistrados.iterator();
                while (it2.hasNext()) {
                    SolicitudCertificado solCertYaRegistrado = (SolicitudCertificado) it2.next();
                    if (solCertYaRegistrado.getSolicitudFolios().size() > 0) {
                        SolicitudFolio solFolioYaRegistrado = (SolicitudFolio) solCertYaRegistrado.getSolicitudFolios().get(0);
                        if (solFolioYaRegistrado.getIdMatricula().equals(solFolioAAgregar.getIdMatricula())) {
                            e.addError("Ya se encuentra registrado un certificado asociado con la matricula " + solFolioYaRegistrado.getIdMatricula() + " .");
                        }
                    }
                }

            }
        }

        //Final de la validacion.
        if (e.getErrores().size() > 0) {
            throw e;
        }

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Solicitud solicitud = turno.getSolicitud();
        String nombreTipoCertificado = CTipoCertificado.ASOCIADO_NOMBRE;
        String idTipoCertificado = CTipoCertificado.TIPO_ASOCIADO_ID;

        Ciudadano ciudadano = solicitud.getCiudadano();

        List liquidacionesCertificadosAsociadosAAgregar = new ArrayList();

        // SE AGREGAN LOS CERTIFICADOS ASOCIADOS A LA SOLICITUD DE REGISTRO.
        if (certificadosAsociadosAAgregar != null) {
            Iterator it3 = certificadosAsociadosAAgregar.iterator();

            while (it3.hasNext()) {
                SolicitudCertificado solCert = (SolicitudCertificado) it3.next();
                //si ya tienen un id de solicitud certificado no se añaden a la solicitud
                solCert.setCiudadano(ciudadano);
                //el flag editado nos ayudara a saber que turnos son nuevos.
                solCert.setCirculo(solicitud.getCirculo());

                //Crear la liquidacion respectiva
                LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado) solCert.getLiquidaciones().get(0);
                liqCert.setSolicitud(solCert);
                liqCert.setUsuario(usuario);

                TipoCertificado tipoCert = new TipoCertificado();
                tipoCert.setIdTipoCertificado(idTipoCertificado);
                tipoCert.setNombre(nombreTipoCertificado);
                liqCert.setTipoCertificado(tipoCert);

                liquidacionesCertificadosAsociadosAAgregar.add(liqCert);
            }
        }

        EvnLiquidacionRegistro eventoReg = new EvnLiquidacionRegistro(usuarioAuriga);
        eventoReg.setTipoEvento(EvnLiquidacionRegistro.AGREGAR_CERTIFICADOS_ASOCIADOS);
        eventoReg.setTurno(turno);
        eventoReg.setLiquidacionesSolicitudesCertificadosAAgregar(liquidacionesCertificadosAsociadosAAgregar);

        return eventoReg;

    }

    // //////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta respuesta) {
        HttpSession session = request.getSession();

        if (respuesta != null) {
            if (respuesta instanceof EvnRespLiquidacion) {
                EvnRespLiquidacion evento = (EvnRespLiquidacion) respuesta;

                if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.VALIDACION)) {
                    SolicitudRegistro solicitud = (SolicitudRegistro) evento.getPayload();
                    session.setAttribute(CDocumento.ALERTAS_DOCUMENTOS, new Boolean(evento.isAlertasDocumentos()));

                    if (solicitud != null) {
                        session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO,
                                solicitud);
                    }

                    if (evento.getEsCajero() != null) {
                        session.setAttribute(WebKeys.ES_CAJERO, evento.getEsCajero());
                    }

                    this.verificarHipotecaPatrimonioVendidos(request);

                } else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.LIQUIDACION)) {                    
                    
                    if(evento.getCanalesXCirculo() != null){
                        session.setAttribute(WebKeys.LISTA_CANALES_RECAUDO_CIRCULO, evento.getCanalesXCirculo());
                    }                    
                    
                    Liquidacion liquidacion = evento.getLiquidacion();
                    session.setAttribute(WebKeys.LIQUIDACION, liquidacion);

                    if (liquidacion != null && liquidacion.getSolicitud() != null) {
                        session.setAttribute(WebKeys.SOLICITUD, liquidacion.getSolicitud());
                    }
                    if (liquidacion instanceof LiquidacionTurnoRegistro) {
                        session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, liquidacion.getSolicitud());
                    }

                    if (evento.getEsCajero() != null) {
                        session.setAttribute(WebKeys.ES_CAJERO, evento.getEsCajero());
                    }

                    if (evento.getEsCajeroRegistro() != null) {
                        session.setAttribute(WebKeys.ES_CAJERO_REGISTRO, evento.getEsCajeroRegistro());
                    }

                    if (evento.getIdImprimible() != 0) {
                        request.getSession().setAttribute(WebKeys.ID_IMPRIMIBLE, new Integer(evento.getIdImprimible()));
                    }

                    NumberFormat nf = NumberFormat.getInstance();
                    session.setAttribute(WebKeys.VALOR_LIQUIDACION,
                            String.valueOf(liquidacion.getValor()));
                    SolicitudRegistro s = (SolicitudRegistro) liquidacion.getSolicitud();

                    //Se adiciona el valor de los certificados asociados
                    List certAsocs = s.getSolicitudesHijas();
                    double valorCertificados = 0;
                    for (Iterator iter = certAsocs.iterator(); iter.hasNext();) {
                        SolicitudAsociada solAsoc = (SolicitudAsociada) iter.next();
                        SolicitudCertificado solCert = (SolicitudCertificado) solAsoc.getSolicitudHija();
                        if (!solCert.isEliminar()) {
                            LiquidacionTurnoCertificado liqCertAux = (LiquidacionTurnoCertificado) solCert.getLiquidaciones().get(0);
                            valorCertificados += liqCertAux.getValor();
                        }
                    }
                    if (valorCertificados != 0) {
                        session.setAttribute(WebKeys.VALOR_CERTIFICADOS_ASOCIADOS,
                                new Double(valorCertificados));
                        DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor() + valorCertificados);
                        AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo,
                                liquidacion.getValor() + valorCertificados);
                        session.setAttribute(WebKeys.APLICACION_EFECTIVO,
                                aplicacionEfectivo);
                        session.setAttribute(WebKeys.VALOR_LIQUIDACION,
                                String.valueOf(liquidacion.getValor() + valorCertificados));
                    } else {
                        DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
                        AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo,
                                liquidacion.getValor());
                        session.setAttribute(WebKeys.APLICACION_EFECTIVO,
                                aplicacionEfectivo);
                    }
                    session.setAttribute(CActo.NUM_ACTOS, new Integer(0));

                    session.removeAttribute(CTurno.TURNO_ANTERIOR);
                    session.removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);

                    session.removeAttribute(CSolicitudRegistro.COMENTARIO);
                    session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
                    session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
                    session.removeAttribute(CSolicitudRegistro.CALENDAR);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
                    session.removeAttribute("DOCUMENTOS_ENTREGADOS");
                    session.removeAttribute("tipo_oficina");
                    session.removeAttribute("tipo_nom_oficina");
                    session.removeAttribute("numero_oficina");
                    session.removeAttribute("id_oficina");

                    session.removeAttribute(CActo.TIPO_ACTO);
                    session.removeAttribute(CActo.VALOR_ACTO);
                    session.removeAttribute(CActo.COBRA_IMPUESTO);
                    session.removeAttribute(CActo.IMPUESTO);
                    session.removeAttribute(CActo.VALOR_OTRO_IMPUESTO);
                    session.removeAttribute(CActo.DESCRIPCION);
                    session.removeAttribute(CLiquidacionTurnoRegistro.TIPO_TARIFA);
                    session.removeAttribute(CCiudadano.TIPODOC);
                    session.removeAttribute(CCiudadano.DOCUMENTO);
                    session.removeAttribute(CCiudadano.APELLIDO1);
                    session.removeAttribute(CCiudadano.APELLIDO2);
                    session.removeAttribute(CCiudadano.NOMBRE);
                    session.removeAttribute("ultimo_campo_editado");

                } else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.VALIDACION_MATRICULA)) {
                    String matricula = (String) evento.getPayload();

                    if (matricula != null) {
                        List matriculas = (List) request.getSession()
                                .getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

                        if (matriculas == null) {
                            matriculas = new ArrayList();
                        }

                        matriculas.add(matricula);
                        request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO,
                                matriculas);

                        String ultimo = request.getParameter(
                                "ultimo_campo_editado");
                        request.getSession().setAttribute("ultimo_campo_editado",
                                ultimo);
                        preservarInfo(request);
                    }
                } else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.VALIDACION_MATRICULA_MIG)) {
                    String matricula = (String) evento.getPayload();

                    if (matricula != null) {
                        List matriculasSirMig = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);

                        if (matriculasSirMig == null) {
                            matriculasSirMig = new ArrayList();
                        }
                        matriculasSirMig.add(matricula);
                        request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO, matriculasSirMig);
                    }

                } else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.VALIDACION_MATRICULA_ASOCIADA)) {
                    String matricula;
                    List matriculas = (List) evento.getPayload();
                    for (int i = 0; i < matriculas.size(); i++) {
                        matricula = (String) matriculas.get(i);
                        if (matricula != null) {
                            List matriculasAsociadas = (List) request.getSession()
                                    .getAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);

                            if (matriculasAsociadas == null) {
                                matriculasAsociadas = new ArrayList();
                            }

                            matriculasAsociadas.add(matricula);
                            request.getSession().setAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO,
                                    matriculasAsociadas);

                            this.adicionarCertificadoAsociado(matricula, request);
                        }
                    }
                } else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.TURNO_ANTERIOR_VALIDADO)) {
                    doEndValidarTurnoAnterior(request, session, evento);
                } else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.PRELIQUIDACION)) {
                    Liquidacion liquidacion = evento.getLiquidacion();
                    session.setAttribute(AWLiquidacionRegistro.PRELIQUIDACION, liquidacion);
                }

            } else if (respuesta instanceof EvnRespLiquidacionRegistro) {
                // casted event
                EvnRespLiquidacionRegistro evento = (EvnRespLiquidacionRegistro) respuesta;

                session.setAttribute(CDocumento.ALERTAS_DOCUMENTOS, new Boolean(evento.isAlertasDocumentos()));

                if (EvnRespLiquidacionRegistro.REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK.equalsIgnoreCase(evento.getTipoEvento())) {
                    // escribir valores a la sesion de turno vinculado
                    doEndRegistro_VincularTurno_AddItem(session, evento);
                } else {
                    doEndProcesarEventoLiquidacionRegistro(request, respuesta, session);
                }
            } else if (respuesta instanceof EvnRespPago) {
                EvnRespPago evento = (EvnRespPago) respuesta;

                if (evento != null) {
                    if (evento.getTipoEvento().equals(EvnRespPago.PROCESAMIENTO_PAGO)) {
                        Turno turno = evento.getTurno();
                        session.setAttribute(WebKeys.TURNO, turno);
                    }
                }
                session.removeAttribute("antiguoSistemaExistente");
            }

        }
        /*AHERRENO
                 28/05/2012
                 REQ 076_151 TRANSACCION*/
        Date fechaIni = (Date) request.getSession().getAttribute("TIEMPO_INICIO_TRANSACCION");
        double tiempoSession = (Double) request.getSession().getAttribute("TIEMPO_TRANSACCION");
        Date fechaFin = new Date();
        TransaccionSIR transaccion = new TransaccionSIR();
        List<Transaccion> acciones = (List<Transaccion>) request.getSession().getAttribute("LISTA_TRANSACCION");
        long calTiempo = 0;
        try {
            calTiempo = transaccion.calculoTransaccion(fechaIni, fechaFin);
        } catch (Exception ex) {
            Logger.getLogger(AWLiquidacionRegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
        DecimalFormat df = new DecimalFormat("0.000");
        double calculo = Double.valueOf(df.format(tiempoSession + ((double) calTiempo / 1000)).replace(',', '.'));
        System.out.println("El tiempo de la accion " + request.getParameter("ACCION") + " en milisegundos " + calTiempo);
        request.getSession().setAttribute("TIEMPO_TRANSACCION", calculo);
        Transaccion transaccionReg = new Transaccion();
        transaccionReg.setFechaTransaccion(fechaFin);
        transaccionReg.setAccionWeb(request.getParameter("ACCION"));
        transaccionReg.setTiempo(calTiempo);
        acciones.add(transaccionReg);
        request.getSession().setAttribute("LISTA_TRANSACCION", acciones);

        if (request.getParameter("ACCION").equals("CREAR_TURNO")) {
            EvnRespLiquidacionRegistro eventoResp = (EvnRespLiquidacionRegistro) respuesta;
            Turno turno = null;
            if (eventoResp.getPayload() instanceof Turno) {
                turno = (Turno) eventoResp.getPayload();
                /*Se recorre la lista para almacenar la informacion del turno*/
                if (turno != null) {
                    for (Transaccion transacion : acciones) {
                        transacion.setAnio(turno.getAnio());
                        transacion.setIdCirculo(turno.getIdCirculo());
                        transacion.setIdProceso(turno.getIdProceso());
                        transacion.setIdTurno(turno.getIdTurno());
                    }
                    transaccion.guardarTransaccion(acciones);
                    acciones.clear();
                    request.getSession().setAttribute("LISTA_TRANSACCION", acciones);
                    request.getSession().setAttribute("TIEMPO_TRANSACCION", Double.valueOf(0));
                }
            }
        }

        if (request.getParameter("ACCION").equals("LIQUIDAR")) {
            acciones.clear();
            request.getSession().setAttribute("LISTA_TRANSACCION", acciones);
            request.getSession().setAttribute("TIEMPO_TRANSACCION", Double.valueOf(0));
        }
    }

    /**
     * Realizar el doEnd para cargar los datos del tipo de Acto, en la
     * liquidación de los actos asociados
     *
     * @param session
     * @param evento
     */
    private void doEndCargarTipoActo(HttpServletRequest request, HttpSession session, EvnRespLiquidacionRegistro evento) {

        TipoActo tipoActo = (TipoActo) evento.getPayload();
        List derechos = new ArrayList();
        List checked = tipoActo.getTiposDerechosRegistrales();

        Iterator it2 = checked.iterator();
        while (it2.hasNext()) {
            TipoActoDerechoRegistral ch = (TipoActoDerechoRegistral) it2.next();
            derechos.add(new ElementoLista(ch.getTipoDerechoRegistral().getIdTipoDerechoReg(), ch.getTipoDerechoRegistral().getNombre()));
        }

        // La lista de tipos de derechos registrales, se guarda en la
        // sesión bajo la llave LISTA_TIPOS_DERECHOS_REGISTRALES
        session.setAttribute("LISTA_TIPOS_DERECHOS_REGISTRALES", derechos);

        // guardar el tipo calculo en la session.
        session.setAttribute(CActo.TIPO_CALCULO, tipoActo.getTipoCalculo().getIdTipoCalculo());
        session.setAttribute(CActo.NOMBRE_TIPO_CALCULO, tipoActo.getTipoCalculo().getNombre());
        session.setAttribute(CActo.TIPO_ACTO_CARGADO, tipoActo);

    }

    /**
     * doEndRegistro_VincularTurno_AddItem
     *
     * @param session HttpSession
     * @param evento EvnRespLiquidacion
     * @see doEndValidarTurnoAnterior
     */
    private void
            doEndRegistro_VincularTurno_AddItem(HttpSession session, EvnRespLiquidacionRegistro evento) {
        gov.sir.core.negocio.modelo.SolicitudVinculada param_SolicitudVinculada
                = evento.getSolicitudVinculada();

        if (null != param_SolicitudVinculada) {
            session.setAttribute(CTurno.SOLICITUD_VINCULADA, param_SolicitudVinculada.getIdSolicitud());
            session.setAttribute(CTurno.SOLICITUD_VINCULADA_OBJETO, param_SolicitudVinculada);
        }

    }

    /**
     * Realizar el doEnd para cargar los datos del turno anterior
     *
     * @param session
     * @param evento
     */
    private void doEndValidarTurnoAnterior(HttpServletRequest request, HttpSession session, EvnRespLiquidacion evento) {
        Turno turno = (Turno) evento.getPayload();
        double totalOtrosImpuestos = evento.getTotalOtroImp();

        if (turno != null) {
            if (turno.getSolicitud() instanceof SolicitudRegistro) {
                SolicitudRegistro solicitudReg = (SolicitudRegistro) turno.getSolicitud();

                SubtipoAtencion subAtencion = solicitudReg.getSubtipoAtencion();
                SubtipoSolicitud subSol = solicitudReg.getSubtipoSolicitud();
                List subtiposObj = (List) session.getServletContext().getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
                List solFolios = solicitudReg.getSolicitudFolios();
                Iterator it = solFolios.iterator();
                List folios = new ArrayList();

                while (it.hasNext()) {
                    SolicitudFolio solFol = (SolicitudFolio) it.next();
                    Folio folio = solFol.getFolio();

                    if (folio.getIdMatricula() != null) {
                        folios.add(folio.getIdMatricula());
                    }
                }

                List checkeds = solicitudReg.getCheckedItems();
                Iterator itc = checkeds.iterator();
                String[] docs = new String[checkeds.size()];
                int i = 0;

                while (itc.hasNext()) {
                    SolicitudCheckedItem solCh = (SolicitudCheckedItem) itc.next();
                    CheckItem ch = solCh.getCheckItem();

                    if (ch.getIdCheckItem() != null) {
                        docs[i] = ch.getIdCheckItem();
                        i++;
                    }
                }

                //long idLiq = solicitudReg.getLastIdLiquidacion() - 1;
                //Liquidacion liq = (Liquidacion) solicitudReg.getLiquidaciones().get((int) idLiq);
                String descOtros = "";

                Iterator itLiquidaciones = solicitudReg.getLiquidaciones().iterator();
                while (itLiquidaciones.hasNext()) {

                    Liquidacion liq = (Liquidacion) itLiquidaciones.next();
                    if (liq instanceof LiquidacionTurnoRegistro) {
                        LiquidacionTurnoRegistro liqReq = (LiquidacionTurnoRegistro) liq;
                        List actos = liqReq.getActos();
                        Iterator ita = actos.iterator();

                        while (ita.hasNext()) {
                            Acto acto = (Acto) ita.next();
                            Integer num = (Integer) session.getAttribute(CActo.NUM_ACTOS);

                            if (num == null) {
                                num = new Integer(0);
                            }

                            session.setAttribute(CActo.ACTO + num.toString(), acto);

                            int v = num.intValue();
                            v++;

                            Integer nuevo = new Integer(v);
                            session.setAttribute(CActo.NUM_ACTOS, nuevo);
                        }

                        // Se introduce el valor de otro impuesto, si existe
                        if ((liqReq.getValorOtroImp() > 0) || (liqReq.getOtroImpuesto() != null && !liqReq.getOtroImpuesto().equals(""))) {
                            /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                                                         * Cambio de tipo de dato
                             */
                            double otroImpuesto = liqReq.getValorOtroImp();
                            descOtros = descOtros + liqReq.getOtroImpuesto();
                        }

                    }
                }

                if (totalOtrosImpuestos != 0 || !descOtros.equals("")) {
                    session.setAttribute(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO, "" + totalOtrosImpuestos);
                    session.setAttribute(CLiquidacionTurnoRegistro.DESCRIPCION, descOtros);
                }

                Ciudadano ciu = solicitudReg.getCiudadano();
                if (ciu != null) {
                    session.setAttribute(CCiudadano.TIPODOC, ciu.getTipoDoc());
                    session.setAttribute(CCiudadano.DOCUMENTO, ciu.getDocumento());
                    session.setAttribute(CCiudadano.APELLIDO1, ciu.getApellido1());
                    session.setAttribute(CCiudadano.APELLIDO2, ciu.getApellido2());
                    session.setAttribute(CCiudadano.NOMBRE, ciu.getNombre());
                    session.setAttribute(CCiudadano.TELEFONO, ciu.getTelefono());
                }

                session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, folios);
                session.setAttribute(WebKeys.LISTA_MATRICULAS_TURNO_FOLIO_MIG, evento.getTurnosFolioMig());
                session.setAttribute(CSolicitudCheckedItem.DOCUMENTOS_ENTREGADOS, docs);

                Documento documento = solicitudReg.getDocumento();
                String tipoDoc = documento.getTipoDocumento().getIdTipoDocumento();
                String numDoc = documento.getNumero();
                Date fechaDoc = documento.getFecha();

                String tipoOficinaNI = "";
                String oficinaInternacional = "";

                oficinaInternacional = documento.getOficinaInternacional();
                tipoOficinaNI = (oficinaInternacional == null) ? "" : oficinaInternacional;

                if (tipoOficinaNI.length() == 0) {
                    session.setAttribute(WebKeys.TIPO_OFICINA_I_N, WebKeys.TIPO_OFICINA_NACIONAL);
                } else {
                    session.setAttribute(WebKeys.TIPO_OFICINA_I_N, WebKeys.TIPO_OFICINA_INTERNACIONAL);
                    session.setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL, oficinaInternacional);
                    session.setAttribute(WebKeys.TEXT_OFICINA_INTERNACIONAL, oficinaInternacional);
                }

                OficinaOrigen oficinaOrigen = documento.getOficinaOrigen();

                String nomOficina = "";
                String numOficina = "";
                String nomMunicpio = "";
                String nomDepto = "";
                String valorVereda = "";
                String valorDepartamento = "";
                String valorMunicipio = "";
                String nomVereda = "";
                String idOficina = "";
                String idTipo = "";
                /*
                                     *  @author Carlos Torres
                                     *  @chage   se agrega validacion de version diferente
                                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                String version = "";

                if (oficinaOrigen != null) {
                    nomOficina = oficinaOrigen.getNombre();
                    numOficina = oficinaOrigen.getNumero();

                    Vereda vereda = oficinaOrigen.getVereda();
                    Municipio municipio = vereda.getMunicipio();
                    nomMunicpio = municipio.getNombre();

                    Departamento depto = municipio.getDepartamento();
                    nomDepto = depto.getNombre();
                    valorVereda = vereda.getIdVereda();
                    valorDepartamento = vereda.getIdDepartamento();
                    valorMunicipio = vereda.getIdMunicipio();
                    nomVereda = vereda.getNombre();

                    TipoOficina tipo = oficinaOrigen.getTipoOficina();
                    idOficina = oficinaOrigen.getIdOficinaOrigen();
                    /*
                                         *  @author Carlos Torres
                                         *  @chage   se agrega validacion de version diferente
                                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                    version = oficinaOrigen.getVersion().toString();
                    idTipo = tipo.getIdTipoOficina();
                }

                DatosAntiguoSistema das = solicitudReg.getDatosAntiguoSistema();

                //Cargar Datos de antiguo sistema
                Log.getInstance().debug(AWLiquidacionRegistro.class, "\n[DAS] " + das + "\n");
                if (das != null) {
                    cargarDatosAntioguoSistemaASesion(session, das);
                } //End if das!=null

                session.setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO, tipoDoc);
                session.setAttribute(CSolicitudRegistro.ID_ENCABEZADO, numDoc);
                session.setAttribute(CSolicitudRegistro.CALENDAR, darFormato(fechaDoc));
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM, numOficina);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO, nomOficina);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO, idTipo);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, idOficina);
                /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, version);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, valorDepartamento);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, nomDepto);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, valorMunicipio);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, nomMunicpio);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, valorVereda);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, nomVereda);
                session.setAttribute(CSolicitudRegistro.SUBTIPO_ATENCION, subAtencion);
                session.setAttribute(CSolicitudRegistro.SUBTIPO_SOLICITUD, subSol.getIdSubtipoSol());

                session.setAttribute(CTurno.TURNO_ANTERIOR, turno.getIdWorkflow());
                session.setAttribute(CTurno.TURNO_ANTERIOR_OBJETO, turno);
                session.setAttribute(WebKeys.TURNO_ANTERIOR_OK, new Boolean(true));

                if (oficinaInternacional == null && oficinaOrigen == null && documento != null && documento.getComentario() != null) {
                    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, documento.getComentario());
                }

                //CARGAR LOS CERTIFICADOS ASOCIADOS
                List certificadosAsociados = solicitudReg.getSolicitudesHijas();
                List certAsociados = new ArrayList();
                List matAsociadas = new ArrayList();

                if (certificadosAsociados != null) {
                    Iterator itca = certificadosAsociados.iterator();
                    SolicitudAsociada solAsoc = null;
                    Solicitud solicitud = null;
                    SolicitudCertificado solCert = null;
                    SolicitudCertificado newSolCert = null;
                    List solfolios = new ArrayList();
                    SolicitudFolio solicitudfolio = null;

                    while (itca.hasNext()) {
                        solAsoc = (SolicitudAsociada) itca.next();
                        solicitud = solAsoc.getSolicitudHija();
                        if (solicitud instanceof SolicitudCertificado) {
                            solCert = (SolicitudCertificado) solicitud;

                            //SI EL TURNO NO HA SIDO IMPRESO SE PUEDE ASOCIAR AL NUEVO TURNO, SINO NO.
                            if (validarImpresion(solCert)) {
                                if (solCert.getSolicitudFolios().size() > 0) {
                                    solfolios = solCert.getSolicitudFolios();
                                    Iterator itsf = solfolios.iterator();
                                    if (itsf.hasNext()) {
                                        solicitudfolio = (SolicitudFolio) itsf.next();
                                        matAsociadas.add(solicitudfolio.getIdMatricula());
                                        newSolCert = crearSolicitudCertificado(solCert, solicitudfolio.getIdMatricula(), request);
                                        certAsociados.add(newSolCert);

                                    }
                                } else {
                                    newSolCert = crearSolicitudCertificado(solCert, null, request);
                                    certAsociados.add(newSolCert);
                                }
                            }

                        }
                    }
                }
                if (!certAsociados.isEmpty()) {
                    session.setAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS, certAsociados);
                }
                if (!matAsociadas.isEmpty()) {
                    session.setAttribute(LISTA_MATRICULAS_CERTIFICADO_ASOCIADO, matAsociadas);
                }
            }
        }
    }

    /**
     * @param matricula
     * @param request
     * @return evento respuesta
     */
    private boolean validarImpresion(SolicitudCertificado solicitudOrigen) {

        //SI EL TURNO DE CERTIFICADO ASOCIADO YA FUE IMPRESO, NO PUEDE USARSE 
        //PARA EL NUEVO TURNO DE REGISTRO COMO CERTIFICADO ASOCIADO.
        boolean turnoValido = true;
        if (solicitudOrigen.getNumImpresiones() > 0) {
            turnoValido = false;
        }

        return turnoValido;
    }

    /**
     * @param matricula
     * @param request
     * @return evento respuesta
     */
    private SolicitudCertificado crearSolicitudCertificado(SolicitudCertificado solicitudOrigen, String matricula, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);

        Proceso proceso = new Proceso();
        proceso.setIdProceso(new BigDecimal(CProceso.PROCESO_CERTIFICADOS).longValue());

        SolicitudCertificado solCert = new SolicitudCertificado();
        solCert.setProceso(proceso);
        solCert.setCirculo(circulo);
        solCert.setUsuario(usuario);

        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA);
        String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS + CSolicitudAsociada.OPCION_MATRICULA);
        solCert.setNumeroCertificados(solicitudOrigen.getNumeroCertificados());

        LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();
        if (solicitudOrigen.getLiquidaciones().size() > 0) {
            liquidacion.setTipoTarifa(((LiquidacionTurnoCertificado) solicitudOrigen.getLiquidaciones().get(solicitudOrigen.getLiquidaciones().size() - 1)).getTipoTarifa());
            solCert.addLiquidacion(liquidacion);
        }

        // SE LE COLOCA LA MATRÍCULA QUE SE QUIERE ASOCIAR.
        if (matricula != null && !matricula.equals("")) {
            SolicitudFolio solFolio = new SolicitudFolio();
            Folio folio = new Folio();
            folio.setIdMatricula(matricula);
            solFolio.setIdMatricula(matricula);
            solFolio.setFolio(folio);
            solCert.addSolicitudFolio(solFolio);
        }

        if (solicitudOrigen.getComentario() != null) {
            solCert.setComentario(solicitudOrigen.getComentario());
        }

        if (solicitudOrigen.getTurno() != null) {
            solCert.setTurnoAnterior(solicitudOrigen.getTurno());
        }

        if (solicitudOrigen.getDatosAntiguoSistema() != null) {
            solCert.setDatosAntiguoSistema(solicitudOrigen.getDatosAntiguoSistema());
        }

        return solCert;
    }

    /**
     * Cargar los datos del objeto Antiguo sistema en memoria
     *
     * @param session Session para cargar los datos
     * @param das Objeto @see <DatosAntiguoSistema> para obtener los datos
     */
    private void cargarDatosAntioguoSistemaASesion(HttpSession session,
            DatosAntiguoSistema das) {
        Documento documento = das.getDocumento();
        OficinaOrigen oficina = null;
        if (documento != null) {
            oficina = das.getDocumento().getOficinaOrigen();

            if (documento.getTipoDocumento() != null) {
                session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS,
                        documento.getTipoDocumento().getIdTipoDocumento());
            }

            if (oficina != null) {
                Vereda vereda = oficina.getVereda();

                if (vereda != null) {
                    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS,
                            vereda.getIdDepartamento());

                    //session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS,"");
                    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS,
                            vereda.getIdMunicipio());
                    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS,
                            vereda.getIdVereda());

                    //session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS,"");
                    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS,
                            oficina.getIdOficinaOrigen());
                    /*
                     *  @author Carlos Torres
                     *  @chage   se agrega validacion de version diferente
                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION,
                            oficina.getVersion().toString());
                    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS,
                            oficina.getNumero());
                } //End if vereda!=null
            } //End if oficina!=null

            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS,
                    documento.getNumero());

            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS,
                    this.darFormato(das.getDocumento().getFecha()));
            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS,
                    documento.getComentario());
        } //End if documento!=null

        session.setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS,
                das.getLibroTipo());
        session.setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS,
                das.getLibroNumero());
        session.setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS,
                das.getLibroPagina());
        session.setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS,
                das.getLibroAnio());
        session.setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS,
                das.getTomoNumero());
        session.setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS,
                das.getTomoPagina());
        session.setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS,
                das.getTomoMunicipio());
        session.setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS, das.getTomoAnio());
        session.setAttribute(CDatosAntiguoSistema.COMENTARIO_AS,
                das.getComentario());

        session.setAttribute("antiguoSistemaExistente", "true");
    }

    /**
     * Procesar el doEnd para el evento de Respuesta Liquidación Registro
     *
     * @param request
     * @param respuesta
     * @param session
     */
    private void doEndProcesarEventoLiquidacionRegistro(
            HttpServletRequest request, EventoRespuesta respuesta,
            HttpSession session) {
        EvnRespLiquidacionRegistro evento = (EvnRespLiquidacionRegistro) respuesta;

        if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.AGREGAR_ACTO)) {
            Acto acto = (Acto) evento.getPayload();
            eliminarInfoActoAsociada(request);
            if (acto != null) {
                session.removeAttribute(CActo.TIPO_ACTO);
                session.removeAttribute(CActo.TIPO_DERECHO);
                session.removeAttribute(CActo.VALOR_ACTO);
                session.removeAttribute(CActo.COBRA_IMPUESTO);
                session.removeAttribute(CActo.IMPUESTO);
                session.removeAttribute(CActo.TIPO_TARIFA);
                session.removeAttribute(CActo.TIPO_CALCULO);
                session.removeAttribute(CActo.VALOR_IMPUESTO);
                session.removeAttribute(CActo.TIPO_ACTO_CARGADO);
                session.removeAttribute("LISTA_TIPOS_DERECHOS_REGISTRALES");

                Integer num = (Integer) session.getAttribute(CActo.NUM_ACTOS);
                session.setAttribute(CActo.ACTO + num.toString(), acto);

                int v = num.intValue();
                v++;

                Integer nuevo = new Integer(v);

                session.setAttribute(CActo.NUM_ACTOS, nuevo);
            }

            SolicitudRegistro solRegistro = (SolicitudRegistro) session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
            Boolean alertasDocumentos = (Boolean) session.getAttribute(CDocumento.ALERTAS_DOCUMENTOS);
            if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_PATRIMONIO_DE_FAMILIA)) {
                String mensajeVencimientoTerminosPatrimonio = obtenerMensajeVencimientoTerminos(acto, solRegistro, alertasDocumentos);
                if (mensajeVencimientoTerminosPatrimonio != null) {
                    session.setAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_PATRIMONIO, mensajeVencimientoTerminosPatrimonio);
                }
            }
            if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_HIPOTECA)) {
                String mensajeVencimientoTerminosHipoteca = obtenerMensajeVencimientoTerminos(acto, solRegistro, alertasDocumentos);
                if (mensajeVencimientoTerminosHipoteca != null) {
                    session.setAttribute(WebKeys.MENSAJE_VENCIMIENTO_TERMINOS_HIPOTECA, mensajeVencimientoTerminosHipoteca);
                }
            }

        } else if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.VALIDAR_SOLICITUD)) {
            request.getSession().setAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION,
                    "" + evento.getValorSecuencial());
            request.getSession().setAttribute(WebKeys.SECUENCIAL_RECIBO_CONSULTADO,
                    "" + evento.getValorSecuencial());
            session.setAttribute(WebKeys.SOLICITUD, evento.getPayload());
        } else if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.BUSCAR_SOLICITUD)) {
            this.doEndBuscarSolicitud(session, evento);
        } else if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.BUSCAR_SOLICITUD_EDICION)) {
            this.doEndBuscarSolicitudEdicion(session, evento);
        } else if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.INCREMENTAR_SECUENCIAL_RECIBO)) {
            long secuencialRecibo = 0;
            Long temp = null;

            if (evento.getPayload() != null) {
                temp = (Long) evento.getPayload();
                secuencialRecibo = temp.longValue();
            }

            request.getSession().setAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION,
                    "" + secuencialRecibo);
            request.getSession().setAttribute(WebKeys.SECUENCIAL_RECIBO_CONSULTADO,
                    "" + secuencialRecibo);
        } else if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.CREAR_TURNO)) {
            // subir el turno a la sesion.
            request.getSession().setAttribute(WebKeys.TURNO, evento.getPayload());
            if (evento.getIdImprimible() != 0) {
                request.getSession().setAttribute(WebKeys.ID_IMPRIMIBLE, new Integer(evento.getIdImprimible()));
            }
            Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
            if (rol.getNombre().equals(CLiquidacionTurnoRegistro.LIQUIDADOR_REGISTRO)) {
                //SIR-86
                request.getSession().removeAttribute(WebKeys.SOLICITUD);
                request.getSession().removeAttribute(WebKeys.LIQUIDACION);
                request.getSession().setAttribute(WebKeys.LIQUIDADOR, CLiquidacionTurnoRegistro.LIQUIDADOR_REGISTRO);
            }
        } else if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.CARGAR_TIPO_ACTO)) {
            this.doEndCargarTipoActo(request, session, evento);
        } else if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.LISTAR_TURNOS_REGISTRO_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS)) {

            if (evento.getPayload() == null) {
                //Datos dummies - lista de elementoLista
                List turnos = new ArrayList();
                turnos.add(new ElementoLista("2006-PRU-6-1", "prueba1"));
                turnos.add(new ElementoLista("2006-PRU-6-2", "prueba2"));
                turnos.add(new ElementoLista("2006-PRU-6-3", "prueba3"));
                turnos.add(new ElementoLista("2006-PRU-6-4", "prueba4"));
                turnos.add(new ElementoLista("2006-PRU-6-5", "prueba5"));

                request.getSession().setAttribute(AWLiquidacionRegistro.LISTA_ELEMENTOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS, turnos);
            } else {
                List elementos = new ArrayList();
                List turnos = (List) evento.getPayload();

                if (turnos == null) {
                    turnos = new ArrayList();
                }
                Iterator it = turnos.iterator();
                while (it.hasNext()) {
                    Turno turno = (Turno) it.next();
                    elementos.add(new ElementoLista(turno.getIdWorkflow(), turno.getIdWorkflow()));
                }
                request.getSession().setAttribute(AWLiquidacionRegistro.LISTA_ELEMENTOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS, elementos);

                request.getSession().setAttribute(AWLiquidacionRegistro.LISTA_TURNOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS, turnos);
            }
        } else if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.SOLICITAR_AGREGAR_CERTIFICADOS_ASOCIADOS)) {
            request.getSession().setAttribute(WebKeys.TURNO, evento.getPayload());

            //Obtener los certificados asociados del turno
            Turno turno = (Turno) evento.getPayload();
            Solicitud sol = turno.getSolicitud();
            List solicitudesAsociadas = sol.getSolicitudesHijas();
            List certificadosAsociados = new ArrayList();
            List matAsociadas = new ArrayList();
            Iterator it = solicitudesAsociadas.iterator();
            while (it.hasNext()) {
                SolicitudAsociada solasoc = (SolicitudAsociada) it.next();
                certificadosAsociados.add(solasoc.getSolicitudHija());
                SolicitudCertificado solCert = (SolicitudCertificado) solasoc.getSolicitudHija();
                List solfolios = solCert.getSolicitudFolios();
                if (solfolios.size() != 0) {
                    SolicitudFolio solfol = (SolicitudFolio) solfolios.get(0);
                    matAsociadas.add(solfol.getIdMatricula());
                }

            }
            request.getSession().setAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS_YA_INGRESADOS_AGREGAR_TURNO, certificadosAsociados);

            if (!matAsociadas.isEmpty()) {
                session.setAttribute(LISTA_MATRICULAS_CERTIFICADO_ASOCIADO_YA_REGISTRADOS_AGREGAR_TURNO, matAsociadas);
            }
        } else if (evento.getTipoEvento().equals(EvnRespLiquidacionRegistro.AGREGAR_CERTIFICADOS_ASOCIADOS)) {
            //Crear liquidacion temporal dodne se reuna el valor total de la lista de liquidaciones de certificados
            List liquidacionesCertificados = (List) evento.getPayload();
            session.setAttribute(AWLiquidacionRegistro.LISTA_LIQUIDACIONES_CERTIFICADOS_ASOCIADOS, liquidacionesCertificados);

            LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();
            double valorTotalCertificados = 0.0;
            Iterator it = liquidacionesCertificados.iterator();
            while (it.hasNext()) {
                LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado) it.next();
                valorTotalCertificados += liqCert.getValor();
            }

            liquidacion.setValor(valorTotalCertificados);

            session.setAttribute(WebKeys.VALOR_LIQUIDACION, Double.toString(liquidacion.getValor()));
            session.setAttribute(WebKeys.LIQUIDACION, liquidacion);
            //flag para que en el momento de registrar el pago se llame al evento deseado
            session.setAttribute(AWLiquidacionRegistro.FLAG_CERTIFICADOS_ASOCIADOS_TURNO, new Boolean(true));

            /*
        	request.getSession().removeAttribute(WebKeys.TURNO);
        	request.getSession().removeAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS_YA_INGRESADOS_AGREGAR_TURNO);
			session.removeAttribute(LISTA_MATRICULAS_CERTIFICADO_ASOCIADO_YA_REGISTRADOS_AGREGAR_TURNO);		
			session.removeAttribute(AWLiquidacionRegistro.LISTA_CERTIFICADOS_ASOCIADOS);
			session.removeAttribute(AWLiquidacionRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
             */
        }
    }

    /**
     * Método que permite devolver el mensaje de advertencia por vencimiento de
     * términos, ya sea de una hipoteca o de patrimonio de familia.
     *
     * @param acto
     * @param solicitudRegistro
     * @return
     */
    private String obtenerMensajeVencimientoTerminos(Acto acto, SolicitudRegistro solicitudRegistro, Boolean alertasDocumentos) {
        String rta = null;
        //int meses = 3;
        String VENCIMIENTO_HIPOTECA = "El documento, presenta vencimiento de términos de más de 90 días para la hipoteca.";
        String VENCIMIENTO_PATRIMONIO = "El documento, presenta vencimiento de términos de más de 90 días para el patrimonio de familia.";
        if (acto != null) {
            if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_PATRIMONIO_DE_FAMILIA) || acto.getTipoActo().getIdTipoActo().equals(CActo.ID_HIPOTECA)) {
                if (solicitudRegistro != null) {
                    Documento documento = solicitudRegistro.getDocumento();
                    if (documento != null) {
                        if (documento.getFecha() != null) {

                            //Calendar doc = Calendar.getInstance();
                            //doc.setTime(documento.getFecha());
                            //doc.add(Calendar.MONTH, meses);
                            //Calendar hoy = Calendar.getInstance();
                            //long diasHabiles=(hoy.getTime().getTime() - doc.getTime().getTime())/(1000 * 60 * 60 * 24);
                            //if(doc.before(hoy)){
                            if (alertasDocumentos.booleanValue()) {
                                if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_PATRIMONIO_DE_FAMILIA)) {
                                    rta = VENCIMIENTO_PATRIMONIO;
                                }
                                if (acto.getTipoActo().getIdTipoActo().equals(CActo.ID_HIPOTECA)) {
                                    rta = VENCIMIENTO_HIPOTECA;
                                }
                            }

                        }
                    }
                }
            }
        }

        return rta;
    }

    /**
     * @param session
     * @param evento
     */
    private void doEndBuscarSolicitud(HttpSession session, EvnRespLiquidacionRegistro evento) {
        Solicitud sol = (Solicitud) evento.getPayload();
        double valLiquidacion = 0;
        
        HermodService hs;
        List canalesXCirculo = new ArrayList() ;
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        try {
            hs = HermodService.getInstance();
           canalesXCirculo = hs.getCanalesRecaudoXCirculo(circulo);
        } catch (HermodException e) {
        }
        session.setAttribute(WebKeys.LISTA_CANALES_RECAUDO_CIRCULO, canalesXCirculo);
        
        
        if (sol != null) {
            List liquidaciones = sol.getLiquidaciones();

            if ((liquidaciones != null) && (liquidaciones.size() > 0)) {
                Liquidacion liq = (Liquidacion) liquidaciones.get(0);
                SolicitudRegistro s = (SolicitudRegistro) liq.getSolicitud();
                List certAsocs = s.getSolicitudesHijas();
                double valorCertificados = 0;
                for (Iterator iter = certAsocs.iterator(); iter.hasNext();) {
                    SolicitudAsociada solAsoc = (SolicitudAsociada) iter.next();
                    SolicitudCertificado solCert = (SolicitudCertificado) solAsoc.getSolicitudHija();
                    if (!solCert.isEliminar()) {
                        LiquidacionTurnoCertificado liqCertAux = (LiquidacionTurnoCertificado) solCert.getLiquidaciones().get(0);
                        valorCertificados += liqCertAux.getValor();
                    }
                }
                if (valorCertificados != 0) {
                    session.setAttribute(WebKeys.VALOR_CERTIFICADOS_ASOCIADOS, new Double(valorCertificados));
                }
                session.setAttribute(WebKeys.VALOR_LIQUIDACION, String.valueOf(liq.getValor() + valorCertificados));
                session.setAttribute(WebKeys.LIQUIDACION, liq);
            }

            session.setAttribute(WebKeys.SOLICITUD, evento.getPayload());

            session.setAttribute(WebKeys.GENERAR_PAGO_EFECTIVO, new Boolean(true));
            session.setAttribute(WebKeys.ES_CAJERO_REGISTRO, evento.getEsCajeroRegistro());

            /*
            DocumentoPago documentoEfectivo = new DocPagoEfectivo(valLiquidacion);
            AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, valLiquidacion);
            session.setAttribute(WebKeys.APLICACION_EFECTIVO, aplicacionEfectivo);
             */
        }
    }

    /**
     * @param session
     * @param evento
     */
    private void doEndBuscarSolicitudEdicion(HttpSession session, EvnRespLiquidacionRegistro evento) {

        SolicitudRegistro solRegistro = (SolicitudRegistro) evento.getPayload();
        session.setAttribute(WebKeys.SOLICITUD, solRegistro);
        session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, solRegistro);

        if (solRegistro != null) {
            List liquidaciones = solRegistro.getLiquidaciones();

            LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro) liquidaciones.get(liquidaciones.size() - 1);

            List actos = liquidacion.getActos();
            Iterator itActos = actos.iterator();
            while (itActos.hasNext()) {
                Acto acto = (Acto) itActos.next();

                Integer num = (Integer) session.getAttribute(CActo.NUM_ACTOS);

                if (num == null) {
                    num = new Integer(0);
                }

                session.setAttribute(CActo.ACTO + num.toString(), acto);

                int v = num.intValue();
                v++;

                Integer nuevo = new Integer(v);
                session.setAttribute(CActo.NUM_ACTOS, nuevo);
            }

            Ciudadano ciu = solRegistro.getCiudadano();

            if (ciu != null) {
                session.setAttribute(CCiudadano.TIPODOC, ciu.getTipoDoc());
                session.setAttribute(CCiudadano.DOCUMENTO, ciu.getDocumento());
                session.setAttribute(CCiudadano.APELLIDO1, ciu.getApellido1());
                session.setAttribute(CCiudadano.APELLIDO2, ciu.getApellido2());
                session.setAttribute(CCiudadano.NOMBRE, ciu.getNombre());
                session.setAttribute(CCiudadano.TELEFONO, ciu.getTelefono());
            }

            session.removeAttribute(CActo.TIPO_ACTO);
            session.removeAttribute(CActo.TIPO_DERECHO);
            session.removeAttribute(CActo.VALOR_ACTO);
            session.removeAttribute(CActo.COBRA_IMPUESTO);
            session.removeAttribute(CActo.IMPUESTO);
            session.removeAttribute(CActo.TIPO_TARIFA);
            session.removeAttribute(CActo.TIPO_CALCULO);
            session.removeAttribute(CActo.VALOR_IMPUESTO);
            session.removeAttribute("LISTA_TIPOS_DERECHOS_REGISTRALES");

            if (liquidacion.getValorOtroImp() > 0) {
                /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                             * Cambio de formato
                 */
                session.setAttribute(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO, StringFormat.getNumeroFormateado(liquidacion.getValorOtroImp()).replaceAll(",", ""));
            }

            session.setAttribute(CActo.DESCRIPCION, liquidacion.getOtroImpuesto());

            //SE CARGAN LOS CERTIFICADOS ASOCIADOS QUE SE HABIAN INGREZADO INICIALMENTE.
            cargarCertificadoAsociados(session, solRegistro.getSolicitudesHijas());

            //SE CARGA LOS FOLIOS DE LA SOLICITUD
            List solFolios = solRegistro.getSolicitudFolios();
            Iterator it = solFolios.iterator();
            List folios = new ArrayList();

            while (it.hasNext()) {
                SolicitudFolio solFol = (SolicitudFolio) it.next();
                Folio folio = solFol.getFolio();

                if (folio.getIdMatricula() != null) {
                    folios.add(folio.getIdMatricula());
                }
            }

            session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, folios);
            /*

				HttpSession session = request.getSession();
				String tipoTarifa = request.getParameter(CLiquidacionTurnoRegistro.TIPO_TARIFA);

				if ((tipoTarifa != null) && !tipoTarifa.equals("SIN_SELECCIONAR")) {
					session.setAttribute(CLiquidacionTurnoRegistro.TIPO_TARIFA,
						tipoTarifa);
				}
				}

				if ((tipocalc != null) && !tipocalc.equals("")) {
					session.setAttribute(CActo.TIPO_CALCULO, tipocalc);
				}
             */
        }
    }

    // adicionado; para que imprima Impreso el XX ------------------------------------------------
    // Copiado de ANLiquidacionFotocopia; (TODO: estandarizar estas funciones en un repositoprio de clases de utilidades)
    protected static String formato(int i) {
        if (i < 10) {
            return "0" + (new Integer(i)).toString();
        }

        return (new Integer(i)).toString();
    }

    protected static String getFechaImpresionFormatted() {
        Calendar c = Calendar.getInstance();
        int dia;
        int ano;
        int hora;
        String min;
        String seg;
        String mes;

        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
        ano = c.get(Calendar.YEAR);

        hora = c.get(Calendar.HOUR_OF_DAY);

        if (hora > 12) {
            hora -= 12;
        }

        min = formato(c.get(Calendar.MINUTE));
        seg = formato(c.get(Calendar.SECOND));

        String fechaImp = "Impreso el " + dia + " de " + mes + " de " + ano
                + " a las " + formato(hora) + ":" + min + ":" + seg + " "
                + DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

        return fechaImp;
    }

    // ------------------------------------------------
    /**
     * Obtener una cadena de fecha con formato apartir de un Date
     *
     * @param fechaDoc fecha a la que se aplica el formato
     * @return Fecha con formato d/M/yyyy
     */
    private String darFormato(Date fechaDoc) {
        Calendar c = Calendar.getInstance();

        if (fechaDoc != null) {
            c.setTime(fechaDoc);
            return DateFormatUtil.format(fechaDoc);

            /*
            String dia = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
            String mes = String.valueOf(c.get(Calendar.MONTH) + 1);
            String year = String.valueOf(c.get(Calendar.YEAR));

            return dia + "/" + mes + "/" + year;
             */
        }

        return null;
    }

    /**
     * Convertir una cadena fecha en un Calendar de la fecha
     *
     * @param fechaInterfaz fecha que se va a convertir a Date en formato
     * dd/MM/yyyy;
     * @return Calendar con la fecha
     */
    private Calendar darFecha(String fechaInterfaz) {
        java.util.Date date = null;

        try {
            date = DateFormatUtil.parse(fechaInterfaz);
            if (fechaInterfaz.indexOf("-") != -1) {
                return null;
            }
        } catch (ParseException e) {
            return null;
        } catch (Throwable t) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        String[] partido = fechaInterfaz.split("/");

        if (partido.length == 3) {
            int dia = Integer.parseInt(partido[0]);
            int mes = Integer.parseInt(partido[1]) - 1;
            int año = Integer.parseInt(partido[2]);
            calendar.set(año, mes, dia);

            if ((calendar.get(Calendar.YEAR) == año)
                    && (calendar.get(Calendar.MONTH) == mes)
                    && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                if (!calendar.after(Calendar.getInstance())) {
                    return calendar;
                }
            }
        }

        return null;
    }

    /**
     * Verificar si exite el id_tipo_encabezado en la Lista de tiposDoc
     *
     * @param tiposDoc Lista con los datos
     * @param id_tipo_encabezado cadena que se busca que exista en la lista
     * @return true si existe id_tipo_encabeado en la lista, false si no
     */
    private String existeId(List tiposDoc, String id_tipo_encabezado) {
        for (Iterator it = tiposDoc.iterator(); it.hasNext();) {
            ElementoLista e = (ElementoLista) it.next();
            int id = Integer.parseInt(e.getId());

            if (id == Integer.parseInt(id_tipo_encabezado)) {
                return e.getId();
            }
        }

        return "";
    }

    /**
     * Colocar todos los parametros que llegaron en el request en sesión
     *
     * @param request
     */
    private void preservarParametros(HttpServletRequest request) {
        Enumeration parametrosEnum = request.getParameterNames();
        HttpSession session = request.getSession();

        while (parametrosEnum.hasMoreElements()) {
            String parametro = (String) parametrosEnum.nextElement();
            String valorParametro = request.getParameter(parametro);

            if ((valorParametro != null) && (valorParametro.length() > 0)) {
                session.setAttribute(parametro, valorParametro);
            }
        }
    }

    /**
     * Este metodo borra de la sesion la informacion que se puso en los campos,
     * ademas de la informacion de las matriculas que se pone en sesion.
     *
     * @param request HttpServletRequest
     * @return Evento nulo ya que no se requiere que viaje hasta el negocio
     */
    private Evento removerInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(AWPago.FECHA_CHEQUE_YA_REGISTRADA);
        session.removeAttribute(AWPago.FECHA_CONSIGNACION_YA_REGISTRADA);
        session.removeAttribute(WebKeys.PAGO_REGISTRO_LIQUIDACION);

        session.removeAttribute(CActo.NUM_ACTOS);
        session.removeAttribute(AWLiquidacionRegistro.LISTA_ELEMENTOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute(AWLiquidacionRegistro.LISTA_TURNOS_REGISTRO_VALIDOS_PARA_AGREGAR_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);

        //variables de acto
        session.removeAttribute(CActo.ID_ACTO);
        session.removeAttribute(CActo.TIPO_ACTO);
        session.removeAttribute(CActo.TIPO_DERECHO);
        session.removeAttribute(CActo.VALOR_ACTO);
        session.removeAttribute(CActo.COBRA_IMPUESTO);
        session.removeAttribute(CActo.CUANTIA);
        session.removeAttribute(CActo.TIPO_TARIFA);
        session.removeAttribute(CActo.VALOR_IMPUESTO);

        session.removeAttribute(CDocumento.ALERTAS_DOCUMENTOS);

        //limpiar variables de certificados asociados
        List temp = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS);
        Iterator it = temp.iterator();
        String nombreTarifa;
        while (it.hasNext()) {
            ElementoLista el = (ElementoLista) it.next();
            nombreTarifa = el.getValor();
            session.removeAttribute("NUMERO_" + nombreTarifa);
        }
        session.removeAttribute("NUMERO_" + CTipoTarifa.TARIFA_ESPECIAL);

        //remover atributos de respuesta de la preliquidacion
        session.removeAttribute(AWLiquidacionRegistro.PRELIQUIDACION);

        //remover atributos de encabezado de documento
        session.removeAttribute(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
        session.removeAttribute(CLiquidacionTurnoRegistro.DESCRIPCION);
        session.removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.CALENDAR);

        session.removeAttribute("AUX");
        session.removeAttribute(WebKeys.LIQUIDACION);
        session.removeAttribute(AWLiquidacionRegistro.VIENE_DE_CAJERO);
        session.removeAttribute(WebKeys.ES_CAJERO_REGISTRO);
        session.removeAttribute(WebKeys.GENERAR_PAGO_EFECTIVO);
        session.removeAttribute(WebKeys.VALOR_LIQUIDACION);
        session.removeAttribute(ID_SOLICITUD);
        session.removeAttribute(SOLICITUD);

        session.removeAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_SEGREGACION);
        session.removeAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA);
        session.removeAttribute(CActo.CUANTIA_EDICION);
        session.removeAttribute("TELEFONO");
        session.removeAttribute(LISTA_CERTIFICADOS_ASOCIADOS);
        session.removeAttribute("TIPO_TARIFA_CERTIFICADOSOPCION_MATRICULA");
        session.removeAttribute("TIPO_TARIFA_CERTIFICADOSOPCION_SEGREGACION");
        session.removeAttribute(AGREGAR_MATRICULA_REGISTRO);
        session.removeAttribute(WebKeys.ES_CAJERO);
        session.removeAttribute(LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
        session.removeAttribute(CFolio.ID_MATRICULA);
        session.removeAttribute(WebKeys.TIPO_OFICINA_I_N);
        session.removeAttribute(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_SEGREGACION);
        session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
        session.removeAttribute(WebKeys.ITEM);
        session.removeAttribute(CActo.NOMBRE_TIPO_CALCULO);
        session.removeAttribute(WebKeys.POSICION);
        session.removeAttribute(CSubtipoAtencion.LISTA_SUBTIPO_CHEQUEO);
        session.removeAttribute(CTipoNota.DEVOLUTIVA);
        session.removeAttribute(CNota.NOMBRE);
        session.removeAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL);
        session.removeAttribute(WebKeys.ACCION);
        Enumeration keysEnum = session.getAttributeNames();
        while (keysEnum.hasMoreElements()) {
            String clave = (String) keysEnum.nextElement();
            if (clave.startsWith("ACTO")) {
                session.removeAttribute(clave);
            }
        }

        session.removeAttribute("imageField.y");
        session.removeAttribute("imageField.x");
        session.removeAttribute("y");
        session.removeAttribute("x");
        session.removeAttribute("antiguoSistemaExistente");

        return null;
    }
}
