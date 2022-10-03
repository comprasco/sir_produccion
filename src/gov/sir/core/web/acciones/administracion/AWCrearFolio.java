package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.certificado.EvnCertificado;
import gov.sir.core.eventos.certificado.EvnRespCertificado;
import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.administracion.EvnCrearFolio;
import gov.sir.core.eventos.administracion.EvnRespConsultaFolio;
import gov.sir.core.eventos.administracion.EvnRespCrearFolio;
import gov.sir.core.eventos.administracion.EvnResp_AdminCrearFolioSaveInfo;
import gov.sir.core.eventos.administracion.Evn_AdminCrearFolioSaveInfo;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Cancelacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import gov.sir.core.negocio.modelo.TipoAnotacion;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.constantes.CAnotacion;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CDireccion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CSalvedadAnotacion;
import gov.sir.core.negocio.modelo.constantes.CSalvedadFolio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.negocio.modelo.constantes.CTipoPredio;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ErrorCreacionFolioException;
import gov.sir.core.web.acciones.excepciones.ErrorCrearAnotacionException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionAnotacionesCancelacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCancelacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoEdicionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoSegregacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEdicionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.web.acciones.registro.AWLiquidacionRegistro;
import gov.sir.core.web.helpers.comun.DatosRespuestaPaginador;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.MostrarFolioHelper;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicAbstractComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicStringComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.jxpath.FolioAbstractFactory;
import gov.sir.core.web.helpers.correccion.validate.utils.convertors.BasicConvertorActions;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicConditionalValidator;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicDatatypeString2IntegerConversionValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringNotNullOrEmptyValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringPatternValidatorWrapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.jxpath.ClassFunctions;
import org.apache.commons.jxpath.JXPathContext;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import gov.sir.core.web.helpers.comun.JvLocalUtils;

/**
 * @author ppabon Clase para manejar la capa Web de la opción de crear un nuevo
 * folio en administración.
 */
public class AWCrearFolio extends SoporteAccionWeb {

    public static final String CREAR_CANCELACION = "CREAR_CANCELACION";

    // PAGE-ID: 001
    public static final String PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO
            = "PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO";

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // :: [step1] ITEMS --------------------------------------------------------
    // REGION-01: COMPLEMENTACION
    // --------------------------
    // REGION-02: LINDERO
    // --------------------------
    // REGION-03: DIRECCION
    // --------------------------
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // :: [step2] (EdicionAnotacion)
    // ITEMS/KEYS
    public static final String PARAM__LOCAL_FOLIO_ID_MATRICULA = "PARAM:LOCAL_FOLIO_IDMATRICULA";
    public static final String PARAM__LOCAL_FOLIO_ID_ZONAREGISTRAL = "PARAM:LOCAL_FOLIO_IDZONAREGISTRAL";

    // BUTTON/ACTIONS ----------
    public static final String ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION
            = "ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION";

    public static final String ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION_EDIT
            = "ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION_EDIT";
    // -------------------------------------------------------------------------------

    // BUTTON/ACTIONS ----------
    public static final String ADMINCREARFOLIO_SAVEINFO_STEP0_FIND_ACTION
            = "ADMINCREARFOLIO_SAVEINFO_STEP0_FIND_ACTION";

    public static final String ADMINCREARFOLIO_SAVEINFO_STEP3_BACK_ACTION
            = "ADMINCREARFOLIO_SAVEINFO_STEP3_BACK_ACTION";

    // -------------------------------------------------------------------------------
    // :: [step2]
    // -------------------------------------------------------------------------------
    // :: [page3] (EdicionAnotacion)
    // actions
    public static final String ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP0_NEXT_ACTION
            = "ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP0_NEXT_ACTION";
    public static final String ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_BACK_ACTION
            = "ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_BACK_ACTION";
    public static final String ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_NEXT_ACTION
            = "ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_NEXT_ACTION";

    // -------------------------------------------------------------------------------
    // actions
    //ACCION EJECUTADA CUANDO SE HACE CLIC EN EL BOTON CREAR NUEVO FOLIO.
    public static final String INICIO_CREAR_NUEVO_FOLIO = "INICIO_CREAR_NUEVO_FOLIO";

    //OPCIONES PARA CARGAR LAS DIRECCIONES AL INGRESAR LA INFORMACIÓN DEL FOLIO
    public static final String AGREGAR_DIRECCION = "AGREGAR_DIRECCION";
    public static final String ELIMINAR_DIRECCION = "ELIMINAR_DIRECCION";

    //CREAR EL FOLIO CUANDO SE TIENE LA INFORMACIÓN NECESARIA
    public static final String CREAR_FOLIO = "CREAR_FOLIO";

    //OPCIONES PARA LA CARGA DE LOS CIUDADANOS
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS";
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS";
    public static final String AGREGAR_VARIOS_CIUDADANOS = "AGREGAR_VARIOS_CIUDADANOS";
    public static final String ELIMINAR_CIUDADANO_ANOTACION = "ELIMINAR_CIUDADANO_ANOTACION";
    public static final String VALIDAR_CIUDADANO = "VALIDAR_CIUDADANO";

    public static final String VALIDAR_CIUDADANO_EDICION = "VALIDAR_CIUDADANO_EDICION";

    public static final String AGREGAR_VARIOS_CIUDADANOS_EDICION = "AGREGAR_VARIOS_CIUDADANOS_EDICION";

    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION";

    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION";

    public static final String GRABAR_EDICION = "GRABAR_EDICION";

    public static final String AGREGAR_SALVEDAD_ANOTACION_EDICION = "AGREGAR_SALVEDAD_ANOTACION_EDICION";

    public static final String EDITAR_ANOTACION = "EDITAR_ANOTACION";

    public static final String ELIMINAR_ANOTACION_TEMPORAL = "ELIMINAR_ANOTACION_TEMPORAL";

    public static final String NUM_ANOTACION = "NUM_ANOTACION";

    public static final String NUM_ANOTACION_TEMPORAL = "NUM_ANOTACION_TEMPORAL";

    public static final String ELIMINAR_SALVEDAD_ANOTACION_EDICION = "ELIMINAR_SALVEDAD_ANOTACION_EDICION";

    public final static String ELIMINAR_CIUDADANO_EDICION = "ELIMINAR_CIUDADANO_EDICION";

    //OPCIONES PARA LA CARGA DE LA NATURALEZA JURÍDICA DE LA ANOTACIÓN
    public static final String CARGAR_DESCRIPCION_NATURALEZA = "CARGAR_DESCRIPCION_NATURALEZA";

    //AGREGAR LA ANOTACIÓN AL FOLIO QUE SE ESTA CREANDO
    public static final String AGREGAR_ANOTACION = "AGREGAR_ANOTACION";

    //CREAR EL NUEVO FOLIO
    public static final String TERMINAR_CREACION_FOLIO = "TERMINAR_CREACION_FOLIO";

    //OTRAS ACCIONES WEB COMPLEMENTARIAS
    public static final String REFRESCAR_ANOTACION = "REFRESCAR_ANOTACION";
    public static final String CONSULTA_FOLIO_COMPLEMENTACION = "CONSULTA_FOLIO_COMPLEMENTACION";

    //CONSTANTES PARA LA ACCIÓN WEB
    public static final String NUM_REGISTROS_TABLA_CIUDADANOS = "NUM_REGISTROS_TABLA_CIUDADANOS";
    public static final int DEFAULT_NUM_CIUDADANOS_TABLA = 2;

    //CANCELAR CREACIÓN DEL FOLIO
    public static final String CANCELAR_CREACION = "CANCELAR_CREACION";

    //VISUALIZAR ANTIGUO SISTEMA
    public static final String VER_ANTIGUO_SISTEMA = "VER_ANTIGUO_SISTEMA";
    public static final String VER_ANTIGUO_SISTEMA_ANOTACION = "VER_ANTIGUO_SISTEMA_ANOTACION";

    private String accion;

    //OPCIONES PARA CARGAR LAS SALVEDADES AL INGRESAR LA INFORMACIÓN DEL FOLIO
    public static final String AGREGAR_SALVEDAD_FOLIO = "AGREGAR_SALVEDAD_FOLIO";
    public static final String ELIMINAR_SALVEDAD_FOLIO = "ELIMINAR_SALVEDAD_FOLIO";

    //OPCIONES PARA CARGAR LAS SALVEDADES AL INGRESAR LA INFORMACIÓN DE LA ANOTACION
    public static final String AGREGAR_SALVEDAD_ANOTACION = "AGREGAR_SALVEDAD_ANOTACION";
    public static final String ELIMINAR_SALVEDAD_ANOTACION = "ELIMINAR_SALVEDAD_ANOTACION";
    // public static final String CREAR_CANCELACION = "CREAR_CANCELACION";

    //	OPCIONES PARA CANCELAR ANOTACIONES
    public static final String GUARDAR_ANOTACIONES_TEMPORALES = "GUARDAR_ANOTACIONES_TEMPORALES";
    public final static String REFRESCAR_CANCELACION = "REFRESCAR_CANCELACION";
    public final static String AGREGAR_VARIOS_CIUDADANOS_CANCELACION = "AGREGAR_VARIOS_CIUDADANOS_CANCELACION";
    /**
     * Constante que indica que se va a validar la existencia de un ciudadano en
     * anotaciones*
     */
    public static final String VALIDAR_CIUDADANO_CANCELACION = "VALIDAR_CIUDADANO_CANCELACION";
    public final static String ELIMINAR_CIUDADANO_ANOTACION_CANCELACION = "ELIMINAR_CIUDADANO_ANOTACION_CANCELACION";
    public final static String CANCELAR_ANOTACION = "CANCELAR_ANOTACION";
    public final static String CANCELAR_CANCELACION = "CANCELAR_CANCELACION";

    public static final String AGREGAR_SEG_ENG = "AGREGAR_SEG_ENG";
    public final static String AGREGAR_DERIVADO = "AGREGAR_DERIVADO";
    public final static String ELIMINAR_DERIVADO = "ELIMINAR_DERIVADO";
    public final static String AGREGAR_ENGLOBE = "AGREGAR_ENGLOBE";
    public final static String ELIMINAR_ENGLOBE = "ELIMINAR_ENGLOBE";
    public final static String VOLVER_DERIVADO = "VOLVER_DERIVADO";

    public static final String IMPRIMIR_HOJA_DE_RUTA = "IMPRIMIR_HOJA_DE_RUTA";

    /**
     *
     */
    public AWCrearFolio() {
        super();
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.length() == 0)) {
            throw new AccionWebException("Debe indicar una accion");
        }

        if (accion.equals(INICIO_CREAR_NUEVO_FOLIO)) {
            return crearNuevoFolio(request);
        } else if (accion.equals(AGREGAR_DIRECCION)) {
            return agregarDireccion(request);
        } else if (accion.equals(ELIMINAR_DIRECCION)) {
            return eliminarDireccion(request);
        } else if (accion.equals(AGREGAR_SALVEDAD_FOLIO)) {
            return agregarSalvedadFolio(request);
        } else if (accion.equals(ELIMINAR_SALVEDAD_FOLIO)) {
            return eliminarSalvedadFolio(request);
        } else if (accion.equals(AGREGAR_SALVEDAD_ANOTACION)
                || accion.equals(AGREGAR_SALVEDAD_ANOTACION_EDICION)) {
            return agregarSalvedadAnotacion(request);
        } else if (accion.equals(ELIMINAR_SALVEDAD_ANOTACION)) {
            return eliminarSalvedadAnotacion(request);
        } else if (accion.equals(ELIMINAR_SALVEDAD_ANOTACION_EDICION)) {
            return eliminarSalvedadAnotacionEdicion(request);
        } else if (accion.equals(CREAR_FOLIO)) {
            return crearFolio(request);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS)
                || accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS)
                || accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS)) {
            return agregarVariosCiudadanos(request);
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_EDICION)) {
            return agregarVariosCiudadanosEdicion(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION)) {
            return eliminarCiudadano(request);
        } else if (accion.equals(VALIDAR_CIUDADANO)
                || accion.equals(VALIDAR_CIUDADANO_EDICION)) {
            return validarCiudadano(request);
        } else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA)) {
            return cargarDescripcionNaturaleza(request);
        } else if (accion.equals(AGREGAR_ANOTACION)) {
            return agregarAnotacion(request);
        } else if (accion.equals(GRABAR_EDICION)) {
            return grabarEdicion(request);
        } else if (accion.equals(CREAR_CANCELACION)) {
            return crearCancelacion(request);
        } else if (accion.equals(EDITAR_ANOTACION)) {
            return editarAnotacion(request);
        } else if (accion.equals(ELIMINAR_ANOTACION_TEMPORAL)) {
            return eliminarAnotacionTemporal(request);
        } else if (accion.equals(TERMINAR_CREACION_FOLIO)) {
            return terminarFolio(request);
        } else if (accion.equals(CONSULTA_FOLIO_COMPLEMENTACION)) {
            return consultaFolioComplementacion(request);
        } else if (accion.equals(REFRESCAR_ANOTACION)) {
            return refrescarAnotacion(request);
        } else if (accion.equals(CANCELAR_CREACION)) {
            return cancelarCreacion(request);
        } else if (accion.equals(VER_ANTIGUO_SISTEMA)) {
            return verAntiguoSistema(request);
        } else if (accion.equals(VER_ANTIGUO_SISTEMA_ANOTACION)) {
            return verAntiguoSistemaAnotacion(request);
        } else if (accion.equals(ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION)) {
            return doProcess_AdminCrearFolio_SaveInfo_Step2Back(request);
        } else if (accion.equals(ADMINCREARFOLIO_SAVEINFO_STEP2_BACK_ACTION_EDIT)) {
            return doProcess_AdminCrearFolio_SaveInfo_Step2BackEdit(request);
        } else if (accion.equals(GUARDAR_ANOTACIONES_TEMPORALES)) {
            return guardarAnotacionesCancelacion(request);
        } else if (accion.equals(REFRESCAR_CANCELACION)) {
            return refrescarCancelacion(request);
        } else if (accion.equals(VALIDAR_CIUDADANO_CANCELACION)) {
            return validarCiudadano(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_EDICION)) {
            return eliminarCiudadanoEdicion(request);
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_CANCELACION)) {
            return agregarVariosCiudadanosCancelacion(request, accion);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION_CANCELACION)) {
            return eliminarCiudadanoCancelacion(request);
        } else if (accion.equals(CANCELAR_ANOTACION)) {
            return cancelarAnotacion(request);
        } else if (accion.equals(CANCELAR_CANCELACION)) {
            return cancelarCancelacion(request);
        } else if (accion.equals(AGREGAR_SEG_ENG)) {
            return agregarSegregacionEnglobe(request);
        } else if (accion.equals(AGREGAR_DERIVADO)) {
            return agregarDerivado(request);
        } else if (accion.equals(ELIMINAR_DERIVADO)) {
            return eliminarDerivado(request);
        } else if (accion.equals(AGREGAR_ENGLOBE)) {
            return agregarEnglobe(request);
        } else if (accion.equals(ELIMINAR_ENGLOBE)) {
            return eliminarEnglobe(request);
        } else if (accion.equals(VOLVER_DERIVADO)) {
            return cancelarSegregacionEnglobe(request);
        } else if (accion.equals(IMPRIMIR_HOJA_DE_RUTA)) {
            return imprimirFolio(request);
        } else if (accion.equals(ADMINCREARFOLIO_SAVEINFO_STEP0_FIND_ACTION)) {
            return doProcess_AdminCrearFolio_SaveInfo_Step0Find(request);
        } else if (accion.equals(ADMINCREARFOLIO_SAVEINFO_STEP3_BACK_ACTION)) {
            return doProcess_AdminCrearFolio_SaveInfo_Step3Back(request);
            // bug 05165 -----------------
        } else if (accion.equals(ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_NEXT_ACTION)) {
            return doProcess_AdminCrearFolio_SaveInfo_AnotacionCanceladoraCrearStep1_BtnNext(request);
        } else if (accion.equals(ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP1_BACK_ACTION)) {
            return doProcess_AdminCrearFolio_SaveInfo_AnotacionCanceladoraCrearStep1_BtnBack(request);
        } else if (accion.equals(ADMINCREARFOLIO_SAVEINFO_ANOTACIONCANCELADORACREARSTEP0_NEXT_ACTION)) {
            return doProcess_AdminCrearFolio_SaveInfo_AnotacionCanceladoraCrearStep0_BtnNext(request);
            // ---------------------------

        } else {
            throw new AccionInvalidaException("La acción " + accion + " no es válida.");
        }
    } // end-methiod

    // bug 05165
    // clear cache / put data into cache of
    // otems referenced by the wizard.
    // Usada si se necesitan cargar
    // valores en las anotaciones a cancelar
    // @see AWModificarFolio.doCorr_FolioEdit_AnotacionCanceladoraEditar_Step0_BtnStart
    private Evento
            doProcess_AdminCrearFolio_SaveInfo_AnotacionCanceladoraCrearStep0_BtnNext(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        // (modificado) TODO: verificar: tener en cuenta que estos datos se estan cargando
        // de cache;
        Evento event = crearCancelacion(request); // = null; // old event: // cargarAnotacion( request );

        // Evento event = cargarAnotacion( request );
        // List< String > to collect data of id's
        List local_ListAnotacionIdsToBeCanceled;
        // String[] to put data of id's
        String[] local_ArrayAnotacionIdsToBeCanceled;

        local_ListAnotacionIdsToBeCanceled = new ArrayList();

        // TODO: si se necesita poblar
        local_ArrayAnotacionIdsToBeCanceled = new String[local_ListAnotacionIdsToBeCanceled.size()];
        local_ArrayAnotacionIdsToBeCanceled = (String[]) local_ListAnotacionIdsToBeCanceled.toArray(local_ArrayAnotacionIdsToBeCanceled);

        // se coloca el arreglo de id's en sesion
        // luego se debe concatenar, para que se
        // conserve el estado de los seleccionados.
        session.setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, local_ArrayAnotacionIdsToBeCanceled);
        // String[] idsAnotaciones = request.getParameterValues("ESCOGER_ANOTACION_CANCELACION");

        return event;

    } // end method: doCorr_FolioEdit_AnotacionCanceladoraEditar_Step0_BtnStart

    // bug 05165
    private Evento
            doProcess_AdminCrearFolio_SaveInfo_AnotacionCanceladoraCrearStep1_BtnNext(HttpServletRequest request)
            throws AccionWebException {

        // capturar el set de anotaciones canceladoras seleccionadas
        HttpSession session = request.getSession();

        boolean activateEvaluation;

        activateEvaluation = true;

        if (activateEvaluation) {

            // bug 5042:
            // must be collected in the paginator
            final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID
                    = "TARGET_SELECTIONCOLLECTOR";

            String idsAnotacionesCsv = request.getParameter(
                    LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID);

            // only the last is added
            String[] idsAnotaciones = gov.sir.core.web.helpers.comun.JvLocalUtils.
                    csvStringToStringArray(idsAnotacionesCsv,
                            gov.sir.core.web.helpers.comun.JvLocalUtils.DEFAULT_SEPARATOR, true);
            ValidacionAnotacionesCancelacionException exception = new ValidacionAnotacionesCancelacionException();
            if (idsAnotaciones == null || idsAnotaciones.length == 0) {
                exception.addError(
                        "Debe seleccionar por lo menos una anotación para cancelar");
                throw exception;
            }
            session.setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION,
                    idsAnotaciones);

            // TODO: call first implementation actions
            // doFolioEdit_AnotacionCancelacion_OnLoad(request);
        } // if

        return null;

    } // end method:

    private Evento
            doProcess_AdminCrearFolio_SaveInfo_AnotacionCanceladoraCrearStep1_BtnBack(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        // last impl callback
        Evento result;
        result = cancelarCancelacion(request);

        // eliminarInfoBasicaAnotacion(request);
        // eliminarInfoBasicaVariosCiudadanos(request);
        // session.removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
        return result;

    } // end method:

    private Evento
            doProcess_AdminCrearFolio_SaveInfo_Step2BackEdit(HttpServletRequest request) throws AccionWebException {
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.VOLVER_EDICION);
        evnCrearFolio.setFolio(folio);

        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoAntiguoSistema(request);
        eliminarInfoBasicaDocumento(request);
        eliminarInfoBasicaSalvedadAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        return evnCrearFolio;
    }

    // cargar info
    private Evento
            doProcess_AdminCrearFolio_SaveInfo_Step2Back(HttpServletRequest request) throws AccionWebException {

        // 1. save state
        // 2. load data (step1:folio)
        HttpSession session;
        session = request.getSession();

        // session data -----------------------------------------------------------------
        org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
        param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo param_Circulo;
        param_Circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        // added
        Folio param_FolioEditado;

        param_FolioEditado = null;

        load_Data:
        {

            param_FolioEditado = new Folio();

            String local_FolioEditado_IdMatricula;
            String local_FolioEditado_IdZonaRegistral;

            local_FolioEditado_IdMatricula = (String) session.getAttribute(PARAM__LOCAL_FOLIO_ID_MATRICULA);
            local_FolioEditado_IdZonaRegistral = (String) session.getAttribute(PARAM__LOCAL_FOLIO_ID_ZONAREGISTRAL);

            param_FolioEditado.setIdMatricula(local_FolioEditado_IdMatricula);
            ZonaRegistral zona = new ZonaRegistral();
            zona.setIdZonaRegistral(local_FolioEditado_IdZonaRegistral);
            param_FolioEditado.setZonaRegistral(zona);

        } // :load_Data

        // build-message -----------------------------------------------------------------
        Evn_AdminCrearFolioSaveInfo local_Result;
        local_Result = new Evn_AdminCrearFolioSaveInfo(Evn_AdminCrearFolioSaveInfo.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENT);

        // get-set injection
        local_Result.setUsuarioAuriga(param_UsuarioAuriga); // o
        local_Result.setUsuarioSir(param_UsuarioSir);       // *
        local_Result.setFolioEditado(param_FolioEditado);   // *

        // ---------------------------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        return local_Result;
        // ---------------------------------------------------------------------------------

    } // end-method:

    private void
            doProcess_PaginadorMultiselect_SaveState(HttpServletRequest request) {

        HttpSession session;
        session = request.getSession();
        final String PREFIX = "MFOLIO:";
        final String CHECKBOXCONTROLLER_MULTISELECTENABLED = "CHECKBOXCONTROLLER_MULTISELECTENABLED";
        final String CHECKBOXCONTROLLER_TARGETFORMFIELDID = "CHECKBOXCONTROLLER_TARGETFORMFIELDID";

        Boolean checkBoxController_MultiSelectEnabled;
        checkBoxController_MultiSelectEnabled = null;

        if ((null == (checkBoxController_MultiSelectEnabled = (Boolean) session.getAttribute(PREFIX + CHECKBOXCONTROLLER_MULTISELECTENABLED))) // ||( !( Boolean.TRUE.equals( checkBoxController_MultiSelectEnabled ) ) )
                ) {
            return;

        } // if

        String checkboxController_TargetFormFieldId;
        checkboxController_TargetFormFieldId = null;
        if ((null == (checkboxController_TargetFormFieldId = (String) session.getAttribute(PREFIX + CHECKBOXCONTROLLER_TARGETFORMFIELDID)))) {
            return;
        }

        // bug 5042:
        // must be collected in the paginator
        final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID = checkboxController_TargetFormFieldId;

        String idsAnotacionesCsv = request.getParameter(LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID);

        // only the last is added
        String[] idsAnotaciones = JvLocalUtils.csvStringToStringArray(idsAnotacionesCsv, JvLocalUtils.DEFAULT_SEPARATOR, true);
        session.setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, idsAnotaciones);

        // remove the keys after save state
        session.removeAttribute(PREFIX + CHECKBOXCONTROLLER_MULTISELECTENABLED);
        session.removeAttribute(PREFIX + CHECKBOXCONTROLLER_TARGETFORMFIELDID);

    } // end-method: doProcess_PaginadorMultiselect_SaveState
// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

    // cargar info
    private Evento
            doProcess_AdminCrearFolio_SaveInfo_Step3Back(HttpServletRequest request) throws AccionWebException {

        HttpSession session;
        session = request.getSession();

        if (session.getAttribute(WebKeys.FOLIO_EDITADO) != null) {
            eliminarInfoBasicaFolio(request);
            eliminarInfoBasicaAnotacion(request);
            eliminarInfoDireccion(request);
            eliminarInfoBasicaVariosCiudadanos(request);
            eliminarInfoBasicaDocumento(request);

            session.removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
            session.removeAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
            session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
            session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
            session.removeAttribute(WebKeys.FOLIO_EDITADO);

            eliminarAtributosSesion(request);
        }

        session.removeAttribute(WebKeys.FOLIO_EDITADO);
        session.removeAttribute(WebKeys.FOLIO);

        // null transition
        return null;

    } // end-method

    // cargar info
    private Evento
            doProcess_AdminCrearFolio_SaveInfo_Step0Find(HttpServletRequest request) throws AccionWebException {

        // 1. save state
        // 2. load data (step1:folio)
        HttpSession session;
        session = request.getSession();

        // form2data-----------------------------------------------------------------------------
        String numMatricula;
        numMatricula = request.getParameter(CFolio.FOLIO_ID_MATRICULA);

        // boolean esAntiguoSistema;
        // esAntiguoSistema = request.getParameter("FOLIO_ANTIGUO_SISTEMA") != null;
        int numeroMatricula = 0;

        ErrorCreacionFolioException exception
                = new ErrorCreacionFolioException();

        // if(!esAntiguoSistema) {
        if (numMatricula == null || numMatricula.length() == 0) {
            exception.addError("Debe ingresar el número de la matrícula");
        } else {
            try {
                numeroMatricula = new Integer(numMatricula).intValue();
                if (numeroMatricula <= 0) {
                    exception.addError("El número de matrícula ingresado debe ser mayor a cero.");
                }
            } catch (Exception e) {
                exception.addError("El número de matrícula ingresado es inválido");
            }
        } // if
        //}

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // session data -----------------------------------------------------------------
        org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
        param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo param_Circulo;
        param_Circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        // bind
        Folio param_FolioEditado;

        param_FolioEditado = null;

        load_Data:
        {

            param_FolioEditado = new Folio();

            String local_FolioEditado_IdMatricula;
            String local_FolioEditado_IdZonaRegistral;

            local_FolioEditado_IdMatricula = param_Circulo.getIdCirculo() + "-" + numeroMatricula;
            local_FolioEditado_IdZonaRegistral = null;

            param_FolioEditado.setIdMatricula(local_FolioEditado_IdMatricula);

        } // :load_Data

        // build-message -----------------------------------------------------------------
        // se envia el mensaje para buscar el folio
        Evn_AdminCrearFolioSaveInfo local_Result;
        local_Result = new Evn_AdminCrearFolioSaveInfo(Evn_AdminCrearFolioSaveInfo.ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENT);

        // get-set injection
        local_Result.setUsuarioAuriga(param_UsuarioAuriga); // o
        local_Result.setUsuarioSir(param_UsuarioSir);       // *
        local_Result.setFolioEditado(param_FolioEditado);   // *

        // ---------------------------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        return local_Result;
        // ---------------------------------------------------------------------------------

    } // end-method:

    private Evento verAntiguoSistemaAnotacion(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String ver = request.getParameter(VER_ANTIGUO_SISTEMA_ANOTACION);
        session.setAttribute(VER_ANTIGUO_SISTEMA_ANOTACION, ver);

        this.preservarInfoBasicaAnotacion(request);

        return null;
    }

    private Evento verAntiguoSistema(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String ver = request.getParameter(VER_ANTIGUO_SISTEMA);
        session.setAttribute(VER_ANTIGUO_SISTEMA, ver);

        this.preservarInfoBasicaFolio(request);

        return null;
    }

    //SE LLAMA AL BOTON QUE INICIA LA CREACIÓN DEL FOLIO
    /**
     * @param request
     * @return
     */
    private Evento crearNuevoFolio(HttpServletRequest request) throws AccionWebException {
        eliminarInfoBasicaFolio(request);
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoDireccion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        eliminarInfoBasicaDocumento(request);
        request.getSession().removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        return null;
    }

    //ACCIONES PARA GUARDAR EN LA SESIÓN LAS DIRECCIONES DEL FOLIO
    /**
     * @param request
     * @return
     */
    private Evento agregarDireccion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        ErrorCreacionFolioException exception = new ErrorCreacionFolioException();

        String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);
        String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);
        String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);
        String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);
        String complemento = request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR);
        String especificacion = request.getParameter(AWModificarFolio.FOLIO_ESPECIFICACION_DIR);

        if ((valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el primer eje válido para la dirección");
        } else {
            if (!valorEje1.equals(CDireccion.SIN_DIRECCION)) {
                if (valorValor1.length() <= 0) {
                    exception.addError("Debe ingresar valor válido para el primer eje de la dirección");
                }

                if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
                    valorEje2 = new String();
                } else {
                    if (valorValor2.length() <= 0) {
                        exception.addError("Debe ingresar valor válido para el segundo eje  de la dirección");
                    }
                }
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List ejes = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();
        direccion.setEspecificacion((especificacion + " " + complemento).toUpperCase());

        if (ejes != null) {
            Iterator itEje = ejes.iterator();

            while (itEje.hasNext()) {
                ElementoLista elementoEje = (ElementoLista) itEje.next();
                Eje eje;

                if (elementoEje.getId().equals(valorEje1)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje(eje);
                    direccion.setValorEje(valorValor1);
                }

                if (elementoEje.getId().equals(valorEje2)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje1(eje);
                }

                if (valorValor2 != null) {
                    direccion.setValorEje1(valorValor2);
                }
            }
        } else {
            exception.addError("La lista de los ejes no se encontró");
        }

        List diferencias = (List) request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);

        if (diferencias == null) {
            diferencias = new ArrayList();
        }
        diferencias.add(direccion);
        //request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION, diferencias);

        direcciones.add(direccion);

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
        eliminarInfoDireccion(request);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarDireccion(HttpServletRequest request) {
        preservarInfoBasicaFolio(request);
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));

        List difDirecciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        if (difDirecciones == null) {
            difDirecciones = new ArrayList();
        }
        Direccion dir = (Direccion) direcciones.get(aplicacionNumero);

        int dif = -1;
        Iterator itDif = difDirecciones.iterator();
        int i = 0;
        while (itDif.hasNext()) {
            Direccion temp = (Direccion) itDif.next();

            if ((null == temp)
                    || (null == temp.getIdDireccion())) {
                continue;
            }

            if ((null == dir)
                    || (null == dir.getIdDireccion())) {
                continue;
            }

            if (temp.getIdDireccion().equals(dir.getIdDireccion())) {
                dif = i;
            }
            i++;
        }

        if (dif != -1) {
            ((Direccion) difDirecciones.get(dif)).setToDelete(true);
        } else {
            dir.setToDelete(true);
            difDirecciones.add(dir);
        }
        request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION, difDirecciones);

        ((Direccion) direcciones.get(aplicacionNumero)).setToDelete(true);

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);

        return null;
    }

//	ACCIONES PARA GUARDAR EN LA SESIÓN LAS SALVEDADES DEL FOLIO
    /**
     * @param request
     * @return
     */
    private Evento agregarSalvedadFolio(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);

        List salvedades = null;
        salvedades = new Vector();

        load_Salvedades:
        {

            List local_t0_FolioSalvedades;
            local_t0_FolioSalvedades = (List) request.getSession().getAttribute(WebKeys.LISTA_SALVEDADES_FOLIO);

            if (local_t0_FolioSalvedades == null) {
            } else {

                if (null == local_t0_FolioSalvedades) {
                    local_t0_FolioSalvedades = new ArrayList();
                }
                for (Iterator local_t0_FolioSalvedadesIterator
                        = local_t0_FolioSalvedades.iterator();
                        local_t0_FolioSalvedadesIterator.hasNext();) {
                    salvedades.add(local_t0_FolioSalvedadesIterator.next());
                }

            } // if

        } // :load_Salvedades

        ValidacionParametrosException exception = exception = new ValidacionParametrosException();

        String fechaSalvedad = request.getParameter(CSalvedadFolio.CALENDAR_RADICACION);
        Calendar fechaRadicacionSalvedad = null;

        if ((fechaSalvedad == null) || fechaSalvedad.trim().equals("")) {
            exception.addError("La fecha de radicación de la salvedad es inválida");
        } else {
            if (fechaSalvedad != null) {
                fechaRadicacionSalvedad = darFecha(fechaSalvedad);
            } else {
                fechaRadicacionSalvedad = null;
            }
            if (fechaRadicacionSalvedad == null) {
                exception.addError("La fecha de radicación de la salvedad es invalida");
            } else if (fechaRadicacionSalvedad.get(Calendar.YEAR) < AWLiquidacionRegistro.ANIO_MINIMO) {
                exception.addError("La fecha de radicación del folio no puede ser inferior a la fecha 01/01/" + AWLiquidacionRegistro.ANIO_MINIMO + ".");
            }
        }

        String numRadicacionSalvedad = request.getParameter(CSalvedadFolio.NUMERO_RADICACION_SALVEDAD_FOLIO);

        if ((numRadicacionSalvedad == null) || numRadicacionSalvedad.trim().equals("")) {
            exception.addError("Debe suministrar un número de radicación para la salvedad");
        }

        String descripcionSalvedad = request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);

        if ((descripcionSalvedad == null)
                || descripcionSalvedad.trim().equals("")
                || descripcionSalvedad.length() > 255) {
            exception.addError("Debe suministrar una descripción de máx. 255 caractéres para la salvedad");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        SalvedadFolio salvedadFolio = new SalvedadFolio();
        int pos = 1;

        if (salvedades != null) {
            pos += salvedades.size();
        }

        salvedadFolio.setIdSalvedad("" + pos);
        salvedadFolio.setDescripcion(descripcionSalvedad);
        salvedadFolio.setFechaCreacion(new Date(fechaRadicacionSalvedad.getTimeInMillis()));
        salvedadFolio.setNumRadicacion(numRadicacionSalvedad);

        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        salvedadFolio.setUsuarioCreacion(usuarioNeg);

        int torder = 0;
        torder = pos;

        torder++;
        String norder = Integer.toString(torder);
        request.getSession().setAttribute(CFolio.NEXT_ORDEN_SALVEDAD, norder);

        //LISTA CON LA SALVEDAD QUE DESEA GUARDARSE
        salvedades.add(salvedadFolio);
        request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_FOLIO, salvedades);

        eliminarInfoBasicaSalvedad(request);
        request.getSession().removeAttribute(WebKeys.HAY_REFRESCO);
        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");

        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento agregarSalvedadAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaAnotacion(request);
        List salvedadesAnotacion = (List) request.getSession().getAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION);
        if (salvedadesAnotacion == null) {
            salvedadesAnotacion = new Vector();
        }

        ValidacionParametrosException exception = exception = new ValidacionParametrosException();

        String fechaSalvedadAnotacion = request.getParameter(CSalvedadAnotacion.CALENDAR_RADICACION_SALVEDAD_ANOTACION);
        Calendar fechaRadicacionSalvedadAnotacion = null;

        if ((fechaSalvedadAnotacion == null) || fechaSalvedadAnotacion.trim().equals("")) {
            exception.addError("La fecha de radicación de la salvedad es inválida");
        } else {
            if (fechaSalvedadAnotacion != null) {
                fechaRadicacionSalvedadAnotacion = darFecha(fechaSalvedadAnotacion);
            } else {
                fechaRadicacionSalvedadAnotacion = null;
            }
            if (fechaRadicacionSalvedadAnotacion == null) {
                exception.addError("La fecha de radicación de la salvedad es invalida");
            } else if (fechaRadicacionSalvedadAnotacion.get(Calendar.YEAR) < AWLiquidacionRegistro.ANIO_MINIMO) {
                exception.addError("La fecha de radicación del folio no puede ser inferior a la fecha 01/01/" + AWLiquidacionRegistro.ANIO_MINIMO + ".");
            }
        }

        String numRadicacionSalvedadAnotacion = request.getParameter(CSalvedadAnotacion.NUMERO_RADICACION_SALVEDAD_ANOTACION);

        if ((numRadicacionSalvedadAnotacion == null) || numRadicacionSalvedadAnotacion.trim().equals("")) {
            exception.addError("Debe suministrar un número de radicación para la salvedad");
        }

        String descripcionSalvedadAnotacion = request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);

        if ((descripcionSalvedadAnotacion == null)
                || descripcionSalvedadAnotacion.trim().equals("")
                || descripcionSalvedadAnotacion.length() > 1024) {
            exception.addError("Debe suministrar una descripción de máx. 1024 caractéres para la salvedad");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        SalvedadAnotacion salvedadAnotacion = new SalvedadAnotacion();
        int pos = 1;

        if (salvedadesAnotacion != null) {
            pos += salvedadesAnotacion.size();
        }

        salvedadAnotacion.setIdSalvedad("" + pos);
        salvedadAnotacion.setDescripcion(descripcionSalvedadAnotacion);
        salvedadAnotacion.setFechaCreacion(new Date(fechaRadicacionSalvedadAnotacion.getTimeInMillis()));
        salvedadAnotacion.setNumRadicacion(numRadicacionSalvedadAnotacion);

        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        salvedadAnotacion.setUsuarioCreacion(usuarioNeg);

        int torder = 0;
        torder = pos;

        torder++;
        String norder = Integer.toString(torder);
        request.getSession().setAttribute(CAnotacion.NEXT_ORDEN_SALVEDAD_ANOTACION, norder);

        //LISTA CON LA SALVEDAD QUE DESEA GUARDARSE
        salvedadesAnotacion.add(salvedadAnotacion);
        request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION, salvedadesAnotacion);

        eliminarInfoBasicaSalvedadAnotacion(request);
        request.getSession().removeAttribute(WebKeys.HAY_REFRESCO);
        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");

        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarSalvedadFolio(HttpServletRequest request) {

        List salvedades = null;
        salvedades = new Vector();

        load_Salvedades:
        {

            List local_t0_FolioSalvedades;
            local_t0_FolioSalvedades = (List) request.getSession().getAttribute(WebKeys.LISTA_SALVEDADES_FOLIO);

            if (local_t0_FolioSalvedades == null) {
            } else {

                if (null == local_t0_FolioSalvedades) {
                    local_t0_FolioSalvedades = new ArrayList();
                }
                for (Iterator local_t0_FolioSalvedadesIterator
                        = local_t0_FolioSalvedades.iterator();
                        local_t0_FolioSalvedadesIterator.hasNext();) {
                    salvedades.add(local_t0_FolioSalvedadesIterator.next());
                }

            } // if

        } // :load_Salvedades

        final String PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO = "PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO";

        // List salvedades = (List) request.getSession().getAttribute(WebKeys.LISTA_SALVEDADES_FOLIO);
        String idsalvedadFolio = (String) request.getParameter(PAGEITEM__CREARFOLIO_IDSALVEDADFOLIO);

        salvedades.remove(Integer.parseInt(idsalvedadFolio));
        request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_FOLIO, salvedades);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarSalvedadAnotacion(HttpServletRequest request) {

        List salvedadesAnotacion = (List) request.getSession().getAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION);
        String idsalvedadAnotacion = (String) request.getParameter(CSalvedadAnotacion.ID_SALVEDAD_ANOTACION);

        salvedadesAnotacion.remove(Integer.parseInt(idsalvedadAnotacion));
        request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_FOLIO, salvedadesAnotacion);

        return null;
    }

    private Evento eliminarSalvedadAnotacionEdicion(HttpServletRequest request) {

        List salvedadesAnotacion = (List) request.getSession().getAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION);
        String idsalvedadAnotacion = (String) request.getParameter(CSalvedadAnotacion.ID_SALVEDAD_ANOTACION);

        //salvedadesAnotacion.remove(Integer.parseInt(idsalvedadAnotacion));
        SalvedadAnotacion c = (SalvedadAnotacion) salvedadesAnotacion.get(Integer.parseInt(idsalvedadAnotacion));
        c.setToDelete(true);

        request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_FOLIO, salvedadesAnotacion);

        return null;
    }

    //GUARDAR LA INFORMACIÓN INGRESADA AL DILIGENCIAR EL FORMULARIO DEL FOLIO
    /**
     * @param request
     * @return
     */
    private Evento crearFolio(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        preservarInfoBasicaFolio(request);
        ErrorCreacionFolioException exception = new ErrorCreacionFolioException();

        boolean esAntiguoSistema = request.getParameter("FOLIO_ANTIGUO_SISTEMA") != null;

        String numMatricula = request.getParameter(CFolio.FOLIO_ID_MATRICULA);
        int numeroMatricula = 0;
        if (!esAntiguoSistema) {
            if (numMatricula == null || numMatricula.length() == 0) {
                exception.addError("Debe ingresar el número de la matrícula");
            } else {
                try {
                    numeroMatricula = new Integer(numMatricula).intValue();
                    if (numeroMatricula <= 0) {
                        exception.addError("El número de matrícula ingresado debe ser mayor a cero.");
                    }
                } catch (Exception e) {
                    exception.addError("El número de matrícula ingresado es inválido");
                }
            }
        }

        String fechaApertura = request.getParameter(CSolicitudRegistro.CALENDAR_APERTURA);
        Calendar fechaAperturaFolio = null;

        if ((fechaApertura == null) || fechaApertura.trim().equals("")) {
            exception.addError("La fecha de apertura es inválida");
        } else {
            if (fechaApertura != null) {
                fechaAperturaFolio = darFecha(fechaApertura);
            } else {
                fechaAperturaFolio = null;
            }

            if (fechaAperturaFolio == null) {
                exception.addError("La fecha de apertura es invalida");
            } else if (fechaAperturaFolio.get(Calendar.YEAR) < AWLiquidacionRegistro.ANIO_MINIMO) {
                exception.addError("La fecha de apertura del folio no puede ser inferior a la fecha 01/01/" + AWLiquidacionRegistro.ANIO_MINIMO + ".");
            }

        }

        String fechaValidacion = request.getParameter(CSolicitudRegistro.CALENDAR);
        Calendar fechaDocumentoValidacion = null;

        if ((fechaValidacion == null) || fechaValidacion.trim().equals("")) {
            exception.addError("La fecha del encabezado es inválida");
        } else {
            if (fechaValidacion != null) {
                fechaDocumentoValidacion = darFecha(fechaValidacion);
                if (fechaDocumentoValidacion.getTimeInMillis() > (new Date()).getTime()) {
                    exception.addError("La fecha del documento no puede ser mayor al día de hoy");
                }
            } else {
                fechaDocumentoValidacion = null;
            }

            if (fechaDocumentoValidacion == null) {
                exception.addError("La fecha del documento es invalida");
            } else if (fechaDocumentoValidacion.get(Calendar.YEAR) < AWLiquidacionRegistro.ANIO_MINIMO) {
                exception.addError("La fecha del encabezado del documento no puede ser inferior a la fecha 01/01/" + AWLiquidacionRegistro.ANIO_MINIMO + ".");
            }

        }

        //se compara que la fecha de radicacion debe ser prosterior a la fecha del documento
        if (fechaAperturaFolio != null && fechaDocumentoValidacion != null) {
            if (fechaAperturaFolio.before(fechaDocumentoValidacion) && !fechaAperturaFolio.equals(fechaDocumentoValidacion)) {
                exception.addError("La fecha de Radicacion debe ser posterior a la fecha del Documento.");
            }
        }

        String numRadicacion = request.getParameter(CSolicitudRegistro.NUMERO_RADICACION);

        if (numRadicacion != null) {
            numRadicacion = numRadicacion.toUpperCase();
        }

        String folioEstado = (String) request.getParameter(CFolio.FOLIO_ID_ESTADO);
        String folioComentario = (String) request.getParameter(CFolio.FOLIO_COMENTARIO);

        if (folioEstado == null || folioEstado.length() != 1) {
            exception.addError("Debe especificar un estado para el folio");
        } else {
            // Validacion para solicitar comentario por cambio de estado de folio.
            // flag para verificar si la validacon se activa;
            //boolean validation1ValidationActive;
            //validation1ValidationActive = true;

            // revisar si existia el estado y si tenia algun valor
            Folio local_t0_Folio = null;

            if (null != (local_t0_Folio = (Folio) session.getAttribute(WebKeys.FOLIO_EDITADO))) {
                String local_t0_FolioEstado = "";
                String local_t1_FolioEstado = "";

                local_t1_FolioEstado = folioEstado;
                try {
                    local_t0_FolioEstado = local_t0_Folio.getEstado().getIdEstado();
                } catch (Exception e) {
                    //
                    local_t0_FolioEstado = "";
                }

                // solo se inactiva si no hay cambio de estado
                //if( local_t1_FolioEstado.equals( local_t0_FolioEstado ) ){
                //validation1ValidationActive = false;
                //} // if
            }

            //if( validation1ValidationActive ) {
            //exception.addError("Debe especificar un comentario para el cambio de estado del folio");
            //} // if
        } // if

        //SE RECUPERA LA INFORMACIÓN DEL FORMULARIO
        String valorDepartamento = request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO);

        if (valorDepartamento == null) {
            valorDepartamento = new String();
        }

        if (valorDepartamento.length() <= 0) {
            exception.addError("Debe seleccionar un departamento.");
        }

        String valorMunicipio = request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC);

        if (valorMunicipio == null) {
            valorMunicipio = new String();
        }

        if (valorMunicipio.length() <= 0) {
            exception.addError("Debe seleccionar un municipio válido.");
        }

        String valorVereda = request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA);

        if (valorVereda == null) {
            valorVereda = new String();
        }

        String valorTipoPredio = request.getParameter(CFolio.FOLIO_TIPO_PREDIO);

        if ((valorTipoPredio == null) || (valorTipoPredio.equals("SIN_SELECCIONAR"))) {
            exception.addError("Debe seleccionar un tipo de predio válido");
        }

        String valorNupre = request.getParameter(CFolio.FOLIO_NUPRE);

        String valorDeterminaInm = request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE);

        if ((valorDeterminaInm == null) || (valorTipoPredio.equals(WebKeys.SIN_SELECCIONAR))) {
            exception.addError("Debe seleccionar una determinaci&oacute;n de inmuble validad");
        }

        String valorPrivMetros = request.getParameter(CFolio.FOLIO_PRIVMETROS);

        String valorPrivCentimetros = request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS);

        String valorConsMetros = request.getParameter(CFolio.FOLIO_CONSMETROS);

        String valorConsCentimetros = request.getParameter(CFolio.FOLIO_CONSCENTIMETROS);

        String valorCoeficiente = request.getParameter(CFolio.FOLIO_COEFICIENTE);

        String valorHectareas = request.getParameter(CFolio.FOLIO_HECTAREAS);
        String valorMetros = request.getParameter(CFolio.FOLIO_METROS);
        String valorCentimetros = request.getParameter(CFolio.FOLIO_CENTIMETROS);
        int unidadmedidaexception = 3;

        if (valorHectareas == null || valorHectareas.length() <= 0) {
            unidadmedidaexception--;
            valorHectareas = "0";
        }

        if (valorMetros == null || valorMetros.length() <= 0) {
            unidadmedidaexception--;
            valorMetros = "0";
        }

        if (valorCentimetros == null || valorCentimetros.length() <= 0) {
            unidadmedidaexception--;
            valorCentimetros = "0";
        }

        if (unidadmedidaexception == 0) {
            exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area del Terreno");
        }

        boolean revisarVereda = true;

        if (valorTipoPredio.equals(CTipoPredio.TIPO_URBANO)) {
            revisarVereda = false;
        }

        if ((valorVereda.length() <= 0) && revisarVereda) {
            exception.addError("Debe seleccionar una vereda válida.");
        }

        String valorComplementacion = request.getParameter(CFolio.FOLIO_COMPLEMENTACION);
        if (valorComplementacion != null) {
            valorComplementacion = valorComplementacion.toUpperCase();
        }

        String idComplementacion = request.getParameter(CFolio.FOLIO_ID_COMPLEMENTACION);
        String tipoAccion = request.getParameter(CFolio.SELECCIONAR_FOLIO + AWModificarFolio.FOLIO_COMPLEMENTACION);

        if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
            if (idComplementacion == null) {
                exception.addError("Debe ingresar un identificador para la complementación válido");
            }
        }

        String valorLindero = request.getParameter(CFolio.FOLIO_LINDERO);
        if (valorLindero != null) {
            valorLindero = valorLindero.toUpperCase();
        }

        if (valorLindero == null) {
            exception.addError("Debe ingresar informacion del lindero válida");
        }

        String valorCodCatastral = request.getParameter(CFolio.FOLIO_COD_CATASTRAL);

        if (valorCodCatastral == null) {
            exception.addError("Debe ingresar un codigo catastral válido");
        }

        Folio folio = new Folio();

        folio.setRadicacion(numRadicacion);
        folio.setDirecto(0);

        if (null != fechaAperturaFolio) {

            folio.setFechaApertura(new Date(fechaAperturaFolio.getTimeInMillis()));

        } // if

        EstadoFolio estadoFolio = new EstadoFolio();
        estadoFolio.setIdEstado(folioEstado);

        if (folioComentario != null && !folioComentario.trim().equals("")) {
            estadoFolio.setComentario(folioComentario);
        }

        folio.setEstado(estadoFolio);

        // bug 05165 -------------
        // inclusion de comentario
        //if( ( null != folioComentario )
        // || ( !( "".equals( folioComentario.trim() ) ) ) ) {
        //  folio.setComentario( folioComentario );
        //}
        // -------------------------
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

        if (numeroMatricula > 0 && !esAntiguoSistema) {
            folio.setIdMatricula(circulo.getIdCirculo() + "-" + numeroMatricula);
        }

        folio.setCodCatastral(valorCodCatastral);

        if (circulo == null) {
            circulo = new Circulo();
        }

        folio.setLindero(valorLindero);
        folio.setNupre(valorNupre);
        folio.setDeterminaInm(valorDeterminaInm);
        folio.setPrivMetros(valorPrivMetros);
        folio.setPrivCentimetros(valorPrivCentimetros);
        folio.setConsMetros(valorConsMetros);
        folio.setConsCentimetros(valorConsCentimetros);
        folio.setCoeficiente(valorCoeficiente);
        folio.setHectareas(valorHectareas);
        folio.setMetros(valorMetros);
        folio.setCentimetros(valorCentimetros);

        //SE LE INGRESA LA COMPLEMENTACIÓN AL FOLIO
        Complementacion comp = new Complementacion();

        String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO + AWModificarFolio.FOLIO_COMPLEMENTACION);

        if (tipoComp.equals(CFolio.NUEVA) || tipoComp.equals(CFolio.COPIAR)) {
            comp.setComplementacion(valorComplementacion);
        }

        if (tipoComp.equals(CFolio.ASOCIAR)) {
            comp.setIdComplementacion(idComplementacion);
            comp.setComplementacion(valorComplementacion);
        }

        folio.setComplementacion(comp);

        //SE INGRESA EL TIPO DE PREDIO
        List tiposPredio = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);

        if (tiposPredio != null) {
            Iterator itPredios = tiposPredio.iterator();

            while (itPredios.hasNext()) {
                ElementoLista elementoTipoPredio = (ElementoLista) itPredios.next();

                if (elementoTipoPredio.getId().equals(valorTipoPredio)) {
                    TipoPredio tipoPredio = new TipoPredio();
                    tipoPredio.setIdPredio(valorTipoPredio);
                    tipoPredio.setNombre(elementoTipoPredio.getValor());
                    folio.setTipoPredio(tipoPredio);
                }
            }
        } else {
            exception.addError("La lista de los tipos de predio no se encontro");
        }

        ZonaRegistral zona = new ZonaRegistral();
        zona.setCirculo(circulo);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(valorDepartamento);

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(valorMunicipio);

        municipio.setDepartamento(departamento);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setMunicipio(municipio);
        zona.setVereda(vereda);

        folio.setZonaRegistral(zona);

        Documento documento = crearDocumento(request, exception, true);

        folio.setDocumento(documento);

        if (esAntiguoSistema) {
            // Verificar si se han digitado datos de antiguo sistema. De existir al menos un
            // campo no vacío significa que se deben crear datos de antiguo sistema
            StringBuffer buffer = new StringBuffer();
            buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS));

            // Si hay datos de antiguo sistema, se crea un objeto DatosAntiguoSistema y se almacena
            // en el contexto
            if (buffer.length() > 0) {
                DatosAntiguoSistema datosAntiguoSistema = new DatosAntiguoSistema();

                if (!request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS).equals("")) {
                    datosAntiguoSistema.setComentario(request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS));
                }
                if (!request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS).equals("")) {
                    datosAntiguoSistema.setLibroAnio(request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS));
                }
                if (!request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS).equals("")) {
                    datosAntiguoSistema.setLibroNumero(request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS));
                }
                if (!request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS).equals("")) {
                    datosAntiguoSistema.setLibroPagina(request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS));
                }
                if (!request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS).equals("")) {
                    datosAntiguoSistema.setLibroTipo(request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS));
                }
                if (!request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS).equals("")) {
                    datosAntiguoSistema.setTomoAnio(request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS));
                }
                if (!request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS).equals("")) {
                    datosAntiguoSistema.setTomoMunicipio(request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS));
                }
                if (!request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS).equals("")) {
                    datosAntiguoSistema.setTomoNumero(request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS));
                }
                if (!request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS).equals("")) {
                    datosAntiguoSistema.setTomoPagina(request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS));
                }

                Documento documentoAntiguoSistema = null;
                TipoDocumento tipoDocumento = null;
                OficinaOrigen oficinaOrigen = null;
                Vereda veredaAntiguoSistema = null;

                buffer = new StringBuffer();
                buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
                buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS));
                buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));

                if (buffer.length() > 0) {
                    veredaAntiguoSistema = new Vereda();
                    veredaAntiguoSistema.setIdVereda(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
                    veredaAntiguoSistema.setIdDepartamento(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS));
                    veredaAntiguoSistema.setIdMunicipio(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));
                }

                buffer = new StringBuffer();
                buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
                buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));

                if (buffer.length() > 0) {
                    oficinaOrigen = new OficinaOrigen();
                    oficinaOrigen.setIdOficinaOrigen(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
                    oficinaOrigen.setNombre(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));
                    oficinaOrigen.setVereda(veredaAntiguoSistema);
                    /*
                             *  @author Carlos Torres
                             *  @chage   se agrega validacion de version diferente
                             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                    oficinaOrigen.setVersion(Long.parseLong(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION)));
                }

                buffer = new StringBuffer();
                buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));

                if (buffer.length() > 0) {
                    tipoDocumento = new TipoDocumento();
                    tipoDocumento.setIdTipoDocumento(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
                }

                buffer = new StringBuffer();
                buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS));
                buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS));
                buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS));

                if (buffer.length() > 0) {
                    documentoAntiguoSistema = new Documento();
                    documentoAntiguoSistema.setComentario(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS));
                    documentoAntiguoSistema.setNumero(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS));
                    documentoAntiguoSistema.setOficinaOrigen(oficinaOrigen);
                    documentoAntiguoSistema.setTipoDocumento(tipoDocumento);

                    if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS).length() > 0) {

                        try {
                            Date fechaDocumento = DateFormatUtil.parse(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS));
                            documentoAntiguoSistema.setFecha(fechaDocumento);
                        } catch (ParseException e) {
                            exception.addError("El campo Fecha del documento de "
                                    + "antiguo sistema no es válido");
                        }
                    }
                }

                datosAntiguoSistema.setDocumento(documentoAntiguoSistema);

                request.getSession().setAttribute(WebKeys.DATOS_ANTIGUO_SISTEMA, datosAntiguoSistema);
            } else {
                exception.addError("Debe llenar al menos un campo de Antiguo Sistema");
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List salvedades = (List) request.getSession().getAttribute(WebKeys.LISTA_SALVEDADES_FOLIO);

        if ((salvedades != null) && (salvedades.size() > 0)) {
            Iterator it = salvedades.iterator();

            while (it.hasNext()) {
                SalvedadFolio salvedadFolio = (SalvedadFolio) it.next();
                salvedadFolio.setIdSalvedad(null);
                folio.addSalvedade(salvedadFolio);
            }
        }

        //folio.setDocumento(registro.getDocumento().get);
        // adicion temporal para que no vuelva
        // a insertar las direcciones:
        // eliminar las direcciones y volverlas a insetar
        local_Folio_Direcciones:
        {

            List t0_Folio_Direcciones;
            List t1_Folio_Direcciones;
            List t2_Folio_Direcciones;

            // initialize
            t0_Folio_Direcciones = (List) nvlCopy(folio.getDirecciones());
            t1_Folio_Direcciones = (List) nvlCopy(session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION));
            t2_Folio_Direcciones = null;

            if (null == t0_Folio_Direcciones) {
                t0_Folio_Direcciones = new ArrayList();
            }
            if (null == t1_Folio_Direcciones) {
                t1_Folio_Direcciones = new ArrayList();
            }
            if (null == t2_Folio_Direcciones) {
                t2_Folio_Direcciones = new ArrayList();
            }

            // 1. verificar si tiene id; si lo tiene, se coloca la marca to delete
            // se copia la lista de direcciones / t0
            // se marcan todos los datos con to-delete
            for (Iterator t0_Folio_DireccionesIterator = t0_Folio_Direcciones.iterator(); t0_Folio_DireccionesIterator.hasNext();) {
                Direccion direccion;
                direccion = (Direccion) t0_Folio_DireccionesIterator.next();

                if (null != direccion.getIdDireccion()) {
                    direccion.setToDelete(true);
                    t2_Folio_Direcciones.add(direccion);
                } // if

            } // for

            // se copia la lista de direcciones / t1
            // se adicionan todos los que no tienen to-delete que son nuevos
            for (Iterator t1_Folio_DireccionesIterator = t1_Folio_Direcciones.iterator(); t1_Folio_DireccionesIterator.hasNext();) {
                Direccion direccion;
                direccion = (Direccion) t1_Folio_DireccionesIterator.next();
                t2_Folio_Direcciones.add(direccion);

            } // for

            for (Iterator t2_Folio_DireccionesIterator = t2_Folio_Direcciones.iterator(); t2_Folio_DireccionesIterator.hasNext();) {
                Direccion direccion;
                direccion = (Direccion) t2_Folio_DireccionesIterator.next();
                folio.addDireccione(direccion);

            } // for


            /*

                  List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

                  if ((direcciones != null) && (direcciones.size() > 0)) {
                          Iterator it = direcciones.iterator();

                          while (it.hasNext()) {
                                  Direccion direccion = (Direccion) it.next();
                                  direccion.setIdDireccion(null);
                                  folio.addDireccione(direccion);
                          }
                  }

             */
        } // :local_Folio_Direcciones

        // TODO: revisar qué queda en este key
        request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, folio);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioSir = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.VALIDAR_MATRICULA_CREACION_FOLIO);

        // step0:
        // verificar si es para edicion / creacion;
        // esto se verifica si existen los keys de pk de folio en sesion
        // additional // PK
        //HttpSession session;
        //session = request.getSession();
        load_Data:
        {

            String local_FolioEditado_IdMatricula;
            String local_FolioEditado_IdZonaRegistral;

            local_FolioEditado_IdMatricula = (String) session.getAttribute(PARAM__LOCAL_FOLIO_ID_MATRICULA);
            local_FolioEditado_IdZonaRegistral = (String) session.getAttribute(PARAM__LOCAL_FOLIO_ID_ZONAREGISTRAL);

            // validar el id de folio editado
            if (null != local_FolioEditado_IdMatricula) {

                evnCrearFolio.setUpdating(true);

                if (null != folio) {

                    if ((null != folio.getIdMatricula())
                            && (!local_FolioEditado_IdMatricula.equals(folio.getIdMatricula()))) {
                        exception.addError("No esta permitido el cambio de identificador de folio cuando los datos editados ya se han creado temporalmente");
                    }

                    folio.setIdMatricula(local_FolioEditado_IdMatricula);
                    ZonaRegistral zona2 = new ZonaRegistral();
                    zona.setIdZonaRegistral(local_FolioEditado_IdZonaRegistral);
                    zona2.setIdZonaRegistral(local_FolioEditado_IdZonaRegistral);
                    folio.setZonaRegistral(zona2);

                } // if

            } // if

        } // load_Data

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        evnCrearFolio.setFolio(folio);
        evnCrearFolio.setUsuario(usuarioAuriga);
        evnCrearFolio.setUsuarioSir(usuarioSir);

        return evnCrearFolio;
    }

    private static Object nvlCopy(Object arg) {

        if (null == arg) {
            return null;
        }

        return copy(arg);
    }

    /**
     * Hace una copia de un objeto, para evitar referencias de apuntadores en
     * memoria. be serialized.
     *
     * @param orig
     * @return
     *
     */
    private static Object copy(Object orig) {
        Object obj = null;

        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        } catch (IOException e) {
            Log.getInstance().error(AWCrearFolio.class, e);
        } catch (ClassNotFoundException cnfe) {
            Log.getInstance().error(AWCrearFolio.class, cnfe);
        }

        return obj;
    }

    /**
     * Éste método recibe los valores ingresados en el formulario para ingresar
     * un documento, hace las validaciones necesarias cuando el documento sea
     * obligatorio, crea el objeto Documento con los valores ingresados y
     * retorna dicho objeto.
     *
     * @param request
     * @param exception
     * @param validarRequerido
     * @return
     */
    private Documento crearDocumento(HttpServletRequest request, ValidacionParametrosException exception, boolean validarRequerido) {

        Documento documento = null;
        Calendar fechaDocumento = null;
        HttpSession session = request.getSession();

        //SE CAPTURAN LOS DATOS DEL DOCUMENTO
        List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        String id_tipo_encabezado = request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
        String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
        String id_encabezado = request.getParameter(CSolicitudRegistro.ID_ENCABEZADO);
        String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);

        //SE CAPTURAN LOS DATOS DE LA OFICINA DÓNDE SE CREO EL DOCUMENTO.
        String idTipoOficina = request.getParameter(WebKeys.TIPO_OFICINA_I_N);
        idTipoOficina = (idTipoOficina != null) ? idTipoOficina : "";

        String nomDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String idDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);

        String nomMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String idMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);

        String nomVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        String idVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);

        if ((idVereda == null) || nomDepto.equals("") || nomMunic.equals("")) {
            List listaVeredas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS);
            idVereda = ValidacionDatosOficinaOrigen.obtenerVereda(listaVeredas, idDepto, idMunic);
        }

        String tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        String numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String id_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                *  @author Carlos Torres
                *  @chage   se agrega validacion de version diferente
                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String versionOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);

        String oficinaInternacional = request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL);

        //SE REALIZAN LAS VALIDACIONES NECESARIAS
        if (validarRequerido) {

            //VALIDACIONES DEL ENCABEZADO DEL DOCUMENTO
            if ((id_tipo_encabezado != null) && !id_tipo_encabezado.equals("")) {
                tipo_encabezado = existeId(tiposDoc, id_tipo_encabezado);

                if (tipo_encabezado.equals("")) {
                    exception.addError("El tipo de documento digitado no correponde al tipo de documento seleccionado");
                }
            }

            if (tipo_encabezado.equals("SIN_SELECCIONAR") && id_tipo_encabezado.equals("")) {
                exception.addError("El campo tipo del encabezado es inválido");
            }

            if (id_encabezado.trim().equals("") && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_SENTENCIA) && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_AUTO) && !id_tipo_encabezado.equals(CTipoDocumento.ID_TIPO_REMATE)) {
                exception.addError("El campo número del encabezado del documento no puede estar vacio");
            }

            if ((fecha == null) || fecha.trim().equals("")) {
                exception.addError("La fecha del encabezado es inválida");
            } else {
                if (fecha != null) {
                    fechaDocumento = darFecha(fecha);
                    if (fechaDocumento.getTimeInMillis() > (new Date()).getTime()) {
                        exception.addError("La fecha del documento no puede ser mayor al día de hoy");
                    }
                } else {
                    fechaDocumento = null;
                }

                if (fechaDocumento == null) {
                    exception.addError("La fecha del documento es invalida");
                } else if (fechaDocumento.get(Calendar.YEAR) < AWLiquidacionRegistro.ANIO_MINIMO) {
                    exception.addError("La fecha del encabezado del documento no puede ser inferior a la fecha 01/01/" + AWLiquidacionRegistro.ANIO_MINIMO + ".");
                }

            }

            //VALIDACIONES DE LA OFICINA DÓNDE SE CREO EL DOCUMENTO
            if (idTipoOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL)) {

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

            } else {
                if ((oficinaInternacional == null) || (oficinaInternacional.length() == 0)) {
                    exception.addError("El campo Oficina Ubicación Internacional no puede estar en blanco");
                }
            }

        }

        //SE CREA EL DOCUMENTO A PARTIR DE LA INFORMACIÓN INGRESADA
        if (tipo_encabezado != null && ((oficinaInternacional != null && oficinaInternacional.length() > 0) || (id_oficina != null && id_oficina.length() > 0))) {
            documento = new Documento();

            //DATOS DEL ENCABEZADO DEL DOCUMENTO
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
            documento.setFecha(fechaDocumento != null ? fechaDocumento.getTime() : null);

            //DATOS DE LA OFICINA DÓNDE SE CREO EL DOCUMENTO
            if (idTipoOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_NACIONAL)) {
                OficinaOrigen oficinaOrigen = new OficinaOrigen();

                if (tipo_oficina != null) {
                    oficinaOrigen.setNombre(tipo_oficina);
                }

                if (numero_oficina != null) {
                    oficinaOrigen.setNumero(numero_oficina);
                }

                oficinaOrigen.setIdOficinaOrigen(id_oficina);
                /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                oficinaOrigen.setVersion(Long.parseLong(versionOficina));

                Vereda vereda = new Vereda();

                if (nomVereda != null) {
                    vereda.setNombre(nomVereda);
                }

                vereda.setIdVereda(idVereda);
                vereda.setIdDepartamento(idDepto);
                vereda.setIdMunicipio(idMunic);

                oficinaOrigen.setVereda(vereda);

                if (id_oficina != null && id_oficina.length() > 0) {
                    documento.setOficinaOrigen(oficinaOrigen);
                }

            } else {
                documento.setOficinaInternacional(oficinaInternacional);
            }

        }

        //COLOCAR LAS VARIABLES EN LA SESIÓN NUEVAMENTE
        preservarInfoBasicaDocumento(request);
        return documento;
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

    //OPCIONES PARA INGRESAR LOS CIUDADANOS
    /**
     *
     * @param request
     * @return
     */
    private Evento aumentarNumeroVariosCiudadanos(HttpServletRequest request) {

        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        numCiud++;
        HttpSession session = request.getSession();
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        request.getSession().setAttribute(WebKeys.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    /**
     * @param request
     * @return
     */
    private int numeroRegistrosTablaAgregarCiudadanos(HttpServletRequest request) {

        Integer num = (Integer) request.getSession().getAttribute(NUM_REGISTROS_TABLA_CIUDADANOS);
        int numCiud;

        if (num == null) {
            numCiud = DEFAULT_NUM_CIUDADANOS_TABLA;
        } else {
            numCiud = num.intValue();
        }

        return numCiud;
    }

    /**
     *
     * @param request
     * @return
     */
    private Evento disminuirNumeroVariosCiudadanos(HttpServletRequest request) {

        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        if (numCiud > 1) {
            numCiud--;
        }

        HttpSession session = request.getSession();
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        request.getSession().setAttribute(WebKeys.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    /**
     * @param request
     * @param exception
     * @return
     * @throws AccionWebException
     *
     *
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request) throws AccionWebException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request); //varios

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        List ciudadanosFinales = new Vector(ciudadanos);

        int numCiudadanosTabla = this.numeroRegistrosTablaAgregarCiudadanos(request);
        for (int i = 0; i < numCiudadanosTabla; i++) {

            if (agregoPersona(request, i)) {
                int b = i + 1;
                boolean datosBien = true;
                String tipoPersona = request.getParameter(CFolio.ANOTACION_TIPO_PERSONA + i);

                if ((tipoPersona == null) || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de persona para el ciudadano " + b + " en la anotacion");
                    datosBien = false;
                }

                String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + i);

                if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de identificación para la persona " + b + " en la anotacion");
                    datosBien = false;
                }

                String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + i);
                String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
                String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
                String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA + i);

                String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA + i);

                if (!tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                    if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escojer un tipo de sexo para la persona en la anotacion");
                        datosBien = false;
                    }
                }

                if (tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {

                    if (tipoPersona.equals(CCiudadano.TIPO_PERSONA_NATURAL)) {
                        if (apellido1 == null || apellido1.trim().equals("")) {
                            exception.addError("Primer apellido invalido para la persona " + b);
                            datosBien = false;
                        }
                    } else {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razon social invalida para el ciudadano " + b);
                            datosBien = false;
                        }
                    }
                } else {
                    if ((numId == null) || numId.trim().equals("")) {
                        exception.addError("Documento Inválido para la persona " + b);
                        datosBien = false;
                    }
                    double valorId = 0.0d;
                    if (numId == null) {
                        exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                        datosBien = false;
                    } else {
                        if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                            try {
                                valorId = Double.parseDouble(numId);
                                if (valorId <= 0) {
                                    exception.addError("El valor del documento de la persona " + b + " no puede ser negativo o cero");
                                    datosBien = false;
                                }
                            } catch (NumberFormatException e) {
                                exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                                datosBien = false;
                            }
                        }
                    }
                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razón social inválida del campo " + b);
                            datosBien = false;
                        }
                    } else {
                        if ((nombres == null) || nombres.equals("")) {
                            exception.addError("Debe ingresar el nombre de la persona " + b + " en la anotación");
                            datosBien = false;
                        }

                        if ((apellido1 == null) || apellido1.equals("")) {
                            exception.addError("Debe ingresar el primer apellido de la persona " + b + " en la anotación");
                            datosBien = false;
                        }
                    }
                }

                String tipoIntervencion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);

                if ((tipoIntervencion == null) || tipoIntervencion.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe ingresar un tipo de intervención para la persona " + b + " en la anotación");
                    datosBien = false;
                }

                AnotacionCiudadano c;
                Iterator ic = ciudadanosFinales.iterator();
                while (ic.hasNext()) {
                    c = (AnotacionCiudadano) ic.next();
                    if ((tipoIntervencion.equals(c.getRolPersona())) && (numId.equals(c.getCiudadano().getDocumento())) && (tipoId.equals(c.getCiudadano().getTipoDoc())) && (!tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA))) {
                        exception.addError("La persona no puede tener dos veces el mismo rol");
                        datosBien = false;
                    }
                }

                String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);

                if ((participacion != null) && !participacion.equals("")) {
                    if (participacion.length() > 50) {
                        exception.addError("El campo de participacion de una persona no puede tener mas de 50 caracteres");
                        datosBien = false;
                    }
                }

                if (datosBien) {
                    AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
                    Ciudadano ciudadano = new Ciudadano();

                    if (apellido1 != null) {
                        ciudadano.setApellido1(apellido1);
                    }

                    if (apellido2 != null) {
                        ciudadano.setApellido2(apellido2);
                    }

                    ciudadano.setNombre(nombres);

                    if (numId != null) {
                        ciudadano.setDocumento(numId);
                    }

                    //Se setea el circulo del ciudadano
                    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                    ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

                    ciudadano.setTipoDoc(tipoId);
                    ciudadano.setTipoPersona(tipoPersona);
                    ciudadano.setSexo(sexo);
                    anotacionCiudadano.setCiudadano(ciudadano);
                    anotacionCiudadano.setRolPersona(tipoIntervencion);
                    anotacionCiudadano.setParticipacion(participacion.toUpperCase());

                    String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);

                    if (marcaPropietario == null || marcaPropietario.equals("")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaPropietario.equals("X")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaPropietario.equals("I")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }

                    ciudadanosFinales.add(anotacionCiudadano);
                }

            }
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        /*eliminarInfoBasicaVariosCiudadanos(request);

		List listaCompletaCiudadanos = new Vector();

		listaCompletaCiudadanos.addAll(ciudadanos);

		request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, listaCompletaCiudadanos);

		return null;*/
        eliminarInfoBasicaVariosCiudadanos(request);

        List listaCompletaCiudadanos = new Vector();

        listaCompletaCiudadanos.addAll(ciudadanosFinales);

        //request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, listaCompletaCiudadanos);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCrearFolio evento = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosFinales);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        return evento;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento agregarVariosCiudadanosEdicion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);//varios
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosCiudadanoEdicionCalificacionException();

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        boolean repetido = false;

        List ciudadanosAgregados = new ArrayList();
        List ciudadanosOrganizados = new ArrayList();
        int numCiudadanosTabla = this.numeroRegistrosTablaAgregarCiudadanos(request);
        for (int i = 0; i < numCiudadanosTabla; i++) {

            if (agregoPersona(request, i)) {
                int b = i + 1;
                boolean datosBien = true;
                String tipoPersona = request.getParameter(CFolio.ANOTACION_TIPO_PERSONA + i);

                if ((tipoPersona == null) || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de persona para el ciudadano " + b + " en la anotacion");
                    datosBien = false;
                }

                String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + i);

                if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de identificación para la persona " + b + " en la anotacion");
                    datosBien = false;
                }

                String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA + i);

                if (!tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                    if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escojer un tipo de sexo para la persona en la anotacion");
                        datosBien = false;
                    }
                }

                String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + i);
                String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
                String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
                String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA + i);

                if (tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {

                    if (tipoPersona.equals(CCiudadano.TIPO_PERSONA_NATURAL)) {
                        if (apellido1 == null || apellido1.trim().equals("")) {
                            exception.addError("Primer apellido invalido para la persona " + b);
                            datosBien = false;
                        }
                    } else {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razon social invalida para el ciudadano " + b);
                            datosBien = false;
                        }
                    }

                } else {
                    if ((numId == null) || numId.trim().equals("")) {
                        exception.addError("Documento Inválido para la persona " + b);
                        datosBien = false;
                    }
                    double valorId = 0.0d;
                    if (numId == null) {
                        exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                        datosBien = false;
                    } else {
                        if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                            try {
                                valorId = Double.parseDouble(numId);
                                if (valorId <= 0) {
                                    exception.addError("El valor del documento de la persona " + b + " no puede ser negativo o cero");
                                    datosBien = false;
                                }
                            } catch (NumberFormatException e) {
                                exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                                datosBien = false;
                            }
                        }
                    }
                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razón social inválida del campo " + b);
                            datosBien = false;
                        }
                    } else {
                        if ((nombres == null) || nombres.equals("")) {
                            exception.addError("Debe ingresar el nombre de la persona " + b + " en la anotación");
                            datosBien = false;
                        }

                        if ((apellido1 == null) || apellido1.equals("")) {
                            exception.addError("Debe ingresar el primer apellido de la persona " + b + " en la anotación");
                            datosBien = false;
                        }
                    }
                }

                String tipoIntervecion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);

                if ((tipoIntervecion == null) || tipoIntervecion.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe ingresar un tipo de intervención para la persona " + b + " en la anotación");
                    datosBien = false;
                }

                String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);

                if ((marcaPropietario == null) || marcaPropietario.equals(WebKeys.SIN_SELECCIONAR)) {
                    marcaPropietario = "";
                }

                String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);

                if ((participacion == null) || participacion.equals(WebKeys.SIN_SELECCIONAR)) {
                    participacion = "";
                }

                AnotacionCiudadano c;
                Iterator ic = ciudadanos.iterator();
                while (ic.hasNext()) {
                    c = (AnotacionCiudadano) ic.next();

                    if ((tipoIntervecion.equals(c.getRolPersona()))
                            && (numId.equals(c.getCiudadano().getDocumento()))
                            && (tipoId.equals(c.getCiudadano().getTipoDoc())
                            && (!tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)))) {
                        if ((marcaPropietario.equals(c.getStringMarcaPropietario()))
                                && (participacion.equals(c.getParticipacion()))) {
                            c.getCiudadano().setApellido1(apellido1);
                            c.getCiudadano().setApellido2(apellido2);
                            c.getCiudadano().setNombre(nombres);
                            c.getCiudadano().setSexo(sexo);
                        }
                        if (c.isToDelete()) {
                            c.setToDelete(false);
                            repetido = true;
                        } else {
                            exception.addError("La persona no puede tener dos veces el mismo rol");
                        }

                    }
                }

                if ((participacion != null) && !participacion.equals("")) {
                    if (participacion.length() > 50) {
                        exception.addError("El campo participación de una persona no puede tener mas de 50 caracteres");
                        datosBien = false;
                    }
                }

                if (datosBien && !repetido) {
                    AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
                    Ciudadano ciudadano = new Ciudadano();

                    if (apellido1 != null) {
                        ciudadano.setApellido1(apellido1);
                    }

                    if (apellido2 != null) {
                        ciudadano.setApellido2(apellido2);
                    }

                    ciudadano.setNombre(nombres);

                    if (numId != null) {
                        ciudadano.setDocumento(numId);
                    }

                    //Se setea el circulo del ciudadano
                    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                    ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

                    ciudadano.setTipoDoc(tipoId);
                    ciudadano.setTipoPersona(tipoPersona);
                    ciudadano.setSexo(sexo);
                    anotacionCiudadano.setCiudadano(ciudadano);
                    anotacionCiudadano.setRolPersona(tipoIntervecion);
                    anotacionCiudadano.setParticipacion(participacion);

                    if (marcaPropietario == null || marcaPropietario.equals("")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaPropietario.equals("X")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaPropietario.equals("I")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }

                    ciudadanosAgregados.add(anotacionCiudadano);
                    ciudadanosOrganizados.add(anotacionCiudadano);
                }

            }
        }
        this.validarCiudadanosRepetidos(ciudadanosOrganizados, exception);
        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            preservarInfoBasicaVariosCiudadanos(request);
            throw exception;
        }

        eliminarInfoBasicaVariosCiudadanos(request);

        //if (!existeEnListaCiudadanos(anotacionCiudadano, ciudadanos)) {
        List listaCompletaCiudadanos = new Vector();

        listaCompletaCiudadanos.addAll(ciudadanos);
        listaCompletaCiudadanos.addAll(ciudadanosAgregados);

        //request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, listaCompletaCiudadanos);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        /*} else {
			exception.addError("El ciudadano que esta intentado ingresar ya se encuentra registrado en la anotacion");
			throw exception;
		}*/
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCrearFolio evento = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosAgregados);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        return evento;
    }

    /**
     * Valida que los ciudadanos de varias anotaciones no sean repetidos (Rol,
     * numero documento tipo de documento)
     *
     * @param List ciudadanosOrganizados, exceoption
     */
    private void validarCiudadanosRepetidos(List ciudadanosOrganizados, ValidacionParametrosException exception) {

        Iterator itCiudadanos = ciudadanosOrganizados.iterator();
        List losA = new ArrayList();
        List losDe = new ArrayList();
        while (itCiudadanos.hasNext()) {
            AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
            String rol = anotacionCiudadano.getRolPersona();
            if (rol.equals("DE")) {
                losDe.add(anotacionCiudadano);
            } else if (rol.equals("A")) {
                losA.add(anotacionCiudadano);
            }
        }
        losDe.addAll(losA);
        ciudadanosOrganizados.clear();
        ciudadanosOrganizados = losDe;
        List ciudadanosTMP = new ArrayList(losDe);
        boolean hayExcepcion = false;

        for (Iterator iterator = ciudadanosOrganizados.iterator(); iterator.hasNext() && !hayExcepcion;) {

            AnotacionCiudadano ciudadano = (AnotacionCiudadano) iterator.next();
            ciudadanosTMP.remove(ciudadano);
            for (Iterator iter = ciudadanosTMP.iterator(); iter.hasNext();) {
                AnotacionCiudadano ciudadanoTemp = (AnotacionCiudadano) iter.next();
                if ((ciudadanoTemp.getCiudadano().getDocumento().equals(ciudadano.getCiudadano().getDocumento()))
                        && (ciudadanoTemp.getRolPersona().equals(ciudadano.getRolPersona()))
                        && (ciudadanoTemp.getCiudadano().getTipoDoc().equals(ciudadano.getCiudadano().getTipoDoc()))
                        && (!ciudadanoTemp.getCiudadano().getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_SECUENCIA))) {
                    exception.addError("El ciudadano no puede tener dos veces el mismo rol");
                    hayExcepcion = true;
                    break;
                }
            }

        }

    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento eliminarCiudadano(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }
        /**
         * Organizar los ciudadanos. Se debe tener en cuenta el orden ya que el
         * helper (VariosCiuddanosAnotacionHelper)los muestra de esta manera y
         * se necesita manejar el mismo contador para eliminar el que realmente
         * se esta indicando
         */

        Iterator itCiudadanos = ciudadanos.iterator();
        List losA = new ArrayList();
        List losDe = new ArrayList();
        while (itCiudadanos.hasNext()) {
            AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
            String rol = anotacionCiudadano.getRolPersona();
            if (rol.equals("DE")) {
                losDe.add(anotacionCiudadano);
            } else if (rol.equals("A")) {
                losA.add(anotacionCiudadano);
            }
        }
        losDe.addAll(losA);
        ciudadanos.clear();
        ciudadanos = losDe;

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
            ciudadanos.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            }
            throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
        }

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        return null;
    }

    private Evento eliminarCiudadanoEdicion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        // TODO: preservarInfoCancelacion(request);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }
        /**
         * Organizar los ciudadanos. Se debe tener en cuenta el orden ya que el
         * helper (VariosCiuddanosAnotacionHelper)los muestra de esta manera y
         * se necesita manejar el mismo contador para eliminar el que realmente
         * se esta indicando
         */

        Iterator itCiudadanos = ciudadanos.iterator();
        List losA = new ArrayList();
        List losDe = new ArrayList();
        while (itCiudadanos.hasNext()) {
            AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
            String rol = anotacionCiudadano.getRolPersona();
            if (rol.equals("DE")) {
                losDe.add(anotacionCiudadano);
            } else if (rol.equals("A")) {
                losA.add(anotacionCiudadano);
            }
        }
        losDe.addAll(losA);
        ciudadanos.clear();
        ciudadanos = losDe;

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(AWCalificacion.POSICION));
            AnotacionCiudadano c = (AnotacionCiudadano) ciudadanos.get(aplicacionNumero);
            c.setToDelete(true);
            // ciudadanos.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            }
            throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
        }

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);

        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));
        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");
        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }
        return null;
    }

    /**
     * Valida un ciudadano en anotación, recibe el request, crea el objeto
     * ciudadano y lanza un evento de negocio
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento validarCiudadano(HttpServletRequest request) throws AccionWebException {
        //eliminarInfoBasicaAnotacion(request);

        //Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosCiudadanoCalificacionException exception = new ValidacionParametrosCiudadanoCalificacionException();
        String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoBasicaAnotacion(request);

        String tipoDoc = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + ver);

        if ((tipoDoc == null) || tipoDoc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo de identificación para la persona en la anotacion");
        }

        String numDoc = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + ver);
        if ((numDoc == null) || numDoc.equals("")) {
            exception.addError("Debe digitar un número de identificación");
        }

        if (!exception.getErrores().isEmpty()) {
            preservarInfo(request);
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA + ver, numDoc);
        request.getSession().setAttribute(WebKeys.HAY_REFRESCO, new Boolean(true));

        Ciudadano ciud = new Ciudadano();
        ciud.setDocumento(numDoc);
        ciud.setTipoDoc(tipoDoc);

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciud.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        return new EvnCiudadano(usuarioArq, EvnCiudadano.CONSULTAR_CIUDADANO_ANOTACION, ciud);
    }

    //OPCIONES PARA LA CARGA DE LA NATURALEZA JURÍDICA DE LA ANOTACIÓN
    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento cargarDescripcionNaturaleza(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        ValidacionParametrosException exception = new ValidacionParametrosException();

        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, idNaturaleza);
        if ((idNaturaleza == null) || (idNaturaleza.trim().equals(""))) {
            exception.addError("Debe insertar un código de naturaleza jurídica");
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        //Preservar información:
        for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String key = (String) enumeration.nextElement();
            request.getSession().setAttribute(key, request.getParameter(key));
        }

        List grupoNaturalezas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);

        GrupoNaturalezaJuridica grupo = new GrupoNaturalezaJuridica();
        NaturalezaJuridica nat;
        boolean found = false;
        String descripcion = "";
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Se asigna valor a la propiedad version
         *
         */
        long version = 0;
        for (Iterator it = grupoNaturalezas.iterator(); it.hasNext() && !found;) {
            grupo = (GrupoNaturalezaJuridica) it.next();
            for (Iterator it2 = grupo.getNaturalezaJuridicas().iterator(); it2.hasNext() && !found;) {
                nat = (NaturalezaJuridica) it2.next();

                if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                    descripcion = nat.getNombre();
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     * @change : Se asigna valor a la propiedad version
                     */
                    version = nat.getVersion();
                    /*if(!nat.isHabilitadoCalificacion()){
						request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
						request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
						throw new NaturalezaInvalidaException("La naturaleza jurídica ("+idNaturaleza + " - "+descripcion +") no es válida.");
					}else{
						found = true;
					} */
                    found = true;
                }
            }
        }

        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, descripcion);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Se asigna valor a la propiedad version
         */
        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, version);
        request.getSession().setAttribute(WebKeys.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    //AGREGAR UNA NUEVA ANOTACIÓN AL FOLIO
    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento agregarAnotacion(HttpServletRequest request) throws AccionWebException {

        Calendar fechaRadicacionCalendar = null;
        Calendar fechaDocumentoCalendar = null;
        preservarInfoBasicaAnotacion(request);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        List anotacionesUnmodifiable = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        Vector anotacionesModifiable;

        if (anotacionesUnmodifiable == null) {
            anotacionesModifiable = new Vector();
        } else {
            anotacionesModifiable = new Vector(anotacionesUnmodifiable);
        }

        ValidacionParametrosException exception = exception = new ValidacionParametrosException();

        String anotacionEstado = request.getParameter(CAnotacion.ESTADO_ANOTACION);

        if (anotacionEstado == null || anotacionEstado.length() != 1) {
            exception.addError("Debe especificar un estado para la anotacion");
        }

        String numRadicaAnota = request.getParameter(CAnotacion.NUMERO_RADICACION_ANOTACION);

        if (numRadicaAnota != null) {
            numRadicaAnota = numRadicaAnota.toUpperCase();
        }

        String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        if (numEspecificacion != null) {
            numEspecificacion = numEspecificacion.replaceAll(",", "");
        }
        double valorEspecificacion = 0.0d;

        if ((numEspecificacion != null) && !numEspecificacion.equals("")) {
            try {
                valorEspecificacion = Double.parseDouble(numEspecificacion);
                if (valorEspecificacion < 0) {
                    exception.addError("El valor de la especificación no puede ser negativo o cero");
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor de la especificación en la anotación es inválido");
            }
        }

        if (ciudadanos == null || ciudadanos.isEmpty()) {
            exception.addError("Debe ingresar por lo menos un ciudadano");
        }

        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);

        String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        if (idNaturaleza == null || idNaturaleza.length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza juridica para la especificación en la anotación");
        }

        String modalidad = request.getParameter(CFolio.ANOTACION_MODALIDAD);
        if (idNaturaleza.equals("0125") && idNaturaleza.equals("0126")) {
            if (modalidad == null || modalidad.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar una modalidad para los códigos de naturaleza juridica 0125 y 0126 en la anotación");
            }
        }
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Variable natJuridica
         */
        NaturalezaJuridica natJuridica = null;
        boolean esta = false;
        List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");

        Iterator ig = grupoNaturalezas.iterator();
        while (ig.hasNext()) {
            GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
            List natus = group.getNaturalezaJuridicas();
            Iterator id = natus.iterator();
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change : Se agrega condicion al while para que termine en cuanto
             * se encuentre una coincidencia
             */
            while (id.hasNext() && !esta) {
                NaturalezaJuridica nat = (NaturalezaJuridica) id.next();

                if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     * @change : se abilita el uso de la clase
                     * VersionNaturalezaJuridicaComparador
                     */
                    natJuridica = nat;
                    esta = true;
                }
            }
        }
        if (!esta) {
            exception.addError("Debe colocar un codigo de naturaleza juridica válido");
        }

        String comentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        if (comentario != null) {
            comentario = comentario.toUpperCase();
        }

        String fechaRadicacion = request.getParameter(CSolicitudRegistro.CALENDAR2);
        String fechaDocumento = request.getParameter(CSolicitudRegistro.CALENDAR);

        int flagCapturarFechaRadiacion = 0;

        if ((fechaRadicacion == null) || fechaRadicacion.trim().equals("")) {
            exception.addError("La fecha de Radicación es inválida");
        } else {
            if (fechaRadicacion != null) {
                fechaRadicacionCalendar = darFecha(fechaRadicacion);
                if (fechaRadicacionCalendar.getTimeInMillis() > (new Date()).getTime()) {
                    exception.addError("La fecha de Radicación no puede ser mayor al día de hoy");
                } else {
                    flagCapturarFechaRadiacion = 1;
                }
            }
        }

        if ((fechaDocumento == null) || fechaDocumento.trim().equals("")) {
            exception.addError("La fecha del Documento es inválida");
        } else {
            if (fechaDocumento != null) {
                fechaDocumentoCalendar = darFecha(fechaDocumento);
                if (null == fechaDocumentoCalendar) {

                } else {

                    if ((null != fechaRadicacion) && (null != fechaRadicacionCalendar)
                            && (fechaRadicacionCalendar.getTimeInMillis() < fechaDocumentoCalendar.getTimeInMillis())) {
                        exception.addError("La fecha de Radicación no puede ser menor a la fecha del Documento");
                    } // if

                } // if

            } // if

        } // if

        //String idMatricula = (String) request.getSession().getAttribute(CFolio.ID_MATRICULA);
        //String fechaAnota = (String) request.getSession().getAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        //String fechaAnota = fechaRadicacion;
        Anotacion anotacion = new Anotacion();
        int pos = 1;

        if (anotacionesModifiable != null) {
            pos += anotacionesModifiable.size();
        }

        EstadoAnotacion estadoAnotacion = new EstadoAnotacion();
        estadoAnotacion.setIdEstadoAn(anotacionEstado);
        anotacion.setEstado(estadoAnotacion);

        anotacion.setNumRadicacion(numRadicaAnota);

        anotacion.setOrden(String.valueOf(pos));
        if (flagCapturarFechaRadiacion == 1) {
            try {
                Date feRa = fechaRadicacionCalendar.getTime();
                anotacion.setFechaRadicacion(feRa);
            } catch (Exception eee) {
                Log.getInstance().debug(AWCrearFolio.class, "Error en la captura de la fecha de Radicación");
            }
        }

        //anotacion.setNumRadicacion(turno.getIdWorkflow());
        //anotacion.setFechaRadicacion(turno.getFechaInicio());
        //anotacion.setDocumento(((SolicitudRegistro) turno.getSolicitud()).getDocumento());
        NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
        anotacion.setModalidad(modalidad);
        anotacion.setValor(valorEspecificacion);

        Documento documento = crearDocumento(request, exception, true);
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Se asigna valor a la propiedad version
         */
        naturalezaJuridica.setVersion(natJuridica.getVersion());
        if (nomNatJuridica != null) {
            naturalezaJuridica.setNombre(nomNatJuridica);
        }

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setEspecificacion(naturalezaJuridica.getNombre());
        anotacion.setComentario(comentario);

        if (ciudadanos != null) {
            Iterator itPersonas = ciudadanos.iterator();
            while (itPersonas.hasNext()) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                if (!ciudadano.isToDelete()) {
                    ciudadano.setAnotacion(anotacion);
                    anotacion.addAnotacionesCiudadano(ciudadano);
                }
            }
        }

        // Verificar si se han digitado datos de antiguo sistema. De existir al menos un
        // campo no vacío significa que se deben crear datos de antiguo sistema
        StringBuffer buffer = new StringBuffer();
        buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS + "1"));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS + "1"));

        // Si hay datos de antiguo sistema, se crea un objeto DatosAntiguoSistema y se almacena
        // en el contexto
        if (buffer.length() > 0) {
            DatosAntiguoSistema datosAntiguoSistema = new DatosAntiguoSistema();

            if (!request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS + "1").equals("")) {
                datosAntiguoSistema.setComentario(request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS + "1"));
            }
            if (!request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS + "1").equals("")) {
                datosAntiguoSistema.setLibroAnio(request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS + "1"));
            }
            if (!request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS + "1").equals("")) {
                datosAntiguoSistema.setLibroNumero(request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS + "1"));
            }
            if (!request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS + "1").equals("")) {
                datosAntiguoSistema.setLibroPagina(request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS + "1"));
            }
            if (!request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS + "1").equals("")) {
                datosAntiguoSistema.setLibroTipo(request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS + "1"));
            }
            if (!request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS + "1").equals("")) {
                datosAntiguoSistema.setTomoAnio(request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS + "1"));
            }
            if (!request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS + "1").equals("")) {
                datosAntiguoSistema.setTomoMunicipio(request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS + "1"));
            }
            if (!request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS + "1").equals("")) {
                datosAntiguoSistema.setTomoNumero(request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS + "1"));
            }
            if (!request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS + "1").equals("")) {
                datosAntiguoSistema.setTomoPagina(request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS + "1"));
            }

            Documento documentoAntiguoSistema = null;
            TipoDocumento tipoDocumento = null;
            OficinaOrigen oficinaOrigen = null;
            Vereda veredaAntiguoSistema = null;

            buffer = new StringBuffer();
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS + "1"));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS + "1"));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS + "1"));

            if (buffer.length() > 0) {
                veredaAntiguoSistema = new Vereda();
                veredaAntiguoSistema.setIdVereda(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS + "1"));
                veredaAntiguoSistema.setIdDepartamento(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS + "1"));
                veredaAntiguoSistema.setIdMunicipio(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS + "1"));
            }

            buffer = new StringBuffer();
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS + "1"));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS + "1"));

            if (buffer.length() > 0) {
                oficinaOrigen = new OficinaOrigen();
                oficinaOrigen.setIdOficinaOrigen(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS + "1"));
                oficinaOrigen.setNombre(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS + "1"));
                oficinaOrigen.setVereda(veredaAntiguoSistema);
                /*
                     *  @author Carlos Torres
                     *  @chage   se agrega validacion de version diferente
                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                oficinaOrigen.setVersion(Long.parseLong(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION + "1"));
            }

            buffer = new StringBuffer();
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1"));

            if (buffer.length() > 0) {
                tipoDocumento = new TipoDocumento();
                tipoDocumento.setIdTipoDocumento(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1"));
            }

            buffer = new StringBuffer();
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS + "1"));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS + "1"));
            buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS + "1") == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS + "1"));

            if (buffer.length() > 0) {
                documentoAntiguoSistema = new Documento();
                documentoAntiguoSistema.setComentario(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS + "1"));
                documentoAntiguoSistema.setNumero(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS + "1"));
                documentoAntiguoSistema.setOficinaOrigen(oficinaOrigen);
                documentoAntiguoSistema.setTipoDocumento(tipoDocumento);

                if (request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS).length() > 0) {

                    try {
                        Date fechaDoc = DateFormatUtil.parse(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS + "1"));
                        documentoAntiguoSistema.setFecha(fechaDoc);
                    } catch (ParseException e) {
                        exception.addError("El campo Fecha del documento de antiguo sistema no es válido");
                    }
                }
            }

            datosAntiguoSistema.setDocumento(documentoAntiguoSistema);

            anotacion.setDatosAntiguoSistema(datosAntiguoSistema);
        }

        TipoAnotacion tipoAn = new TipoAnotacion();
        tipoAn.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
        anotacion.setTipoAnotacion(tipoAn);
        anotacion.setTemporal(true);

        anotacion.setOrden("" + pos);
        anotacion.setDocumento(documento);

        List salvedadesAnotacion = (List) request.getSession().getAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION);

        if ((salvedadesAnotacion != null) && (salvedadesAnotacion.size() > 0)) {
            Iterator it = salvedadesAnotacion.iterator();

            while (it.hasNext()) {
                SalvedadAnotacion salvedadAnotacion = (SalvedadAnotacion) it.next();
                salvedadAnotacion.setIdSalvedad(null);
                anotacion.addSalvedade(salvedadAnotacion);
            }
        }

        //LISTA CON LA ANOTACIÓN QUE DESEA GUARDARSE
        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");

        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);

        //////////////////////
        Folio dummyFolio;
        dummyFolio = new Folio();
        dummyFolio.setIdMatricula(folio.getIdMatricula());
        dummyFolio.addAnotacione(anotacion);
        //////////////////////

        folio.setAnotaciones((List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO));
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioSir = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.AGREGAR_ANOTACION);
        evnCrearFolio.setFolio(dummyFolio);
        evnCrearFolio.setUsuario(usuarioAuriga);
        evnCrearFolio.setUsuarioSir(usuarioSir);
        evnCrearFolio.setGenerarNextOrden(true);
        evnCrearFolio.setAnotacionesAgregadas(anotacionesModifiable);
        evnCrearFolio.setAnotacion(anotacion);

        return evnCrearFolio;

        //org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        //Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        //return null;
    }

    /**
     * @param request
     * @return
     * @throws ValidacionParametrosException
     */
    private Evento grabarEdicion(HttpServletRequest request) throws ValidacionParametrosException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //Codigo  de grabar Edicion
        Calendar fechaRadicacionCalendar = null;
        Calendar fechaDocumentoCalendar = null;
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        //obtencion del folio
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);

        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosEdicionCalificacionException();

        //obtencion lista de ciudadanosT en el sesion (Aqui estan tanto los ciudadanos sin editar como los editados)
        List ciudadanosT = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        if (ciudadanosT == null) {
            ciudadanosT = new Vector();
        }

        if (ciudadanosT.size() == 0) {
            exception.addError("Debe ingresar ciudadanos a la anotacion");
        }
        List anotaCiudAux = new ArrayList();
        for (Iterator iterCiud = ciudadanosT.iterator(); iterCiud.hasNext();) {
            AnotacionCiudadano ciudadano = (AnotacionCiudadano) iterCiud.next();
            if (!ciudadano.isToDelete()) {
                anotaCiudAux.add(ciudadano);
            }
        }
        if (anotaCiudAux.size() == 0) {
            exception.addError("Debe ingresar ciudadanos a la anotacion");
        }
        //obtencion lista de ciudadanosB del folio (aqui estan solo los ciudadanos q existen antes de la edicion)
        List ciudadanosB = null; // se inicializara despues.

        NaturalezaJuridica nat = null;
        boolean cambioNat = false; //flag para saber si se cambio la naturaleza juridica
        String idNaturalezaB = "";
        String nombreNaturalezaB = "";
        String comentarioB = "";
        double valorB = 0;

        //aqui se comienza a ver q valores se han cambiado
        Anotacion a = new Anotacion();
        String pos = (String) request.getSession().getAttribute(NUM_ANOTACION_TEMPORAL);
        int numTemp = Integer.parseInt(pos);
        long numDef;
        //Se setea el id de la anotacion q se va cambiar
        List anotacionF = null;
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
            anotacionF = RPag.getAnotacionesActual();
        } else {
            //anotacionF=  (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
            anotacionF = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
            //List anotaciones = (List)request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        }

        Iterator iaf = anotacionF.iterator();
        while (iaf.hasNext()) {
            Anotacion temp = (Anotacion) iaf.next();
            if (pos.equals(temp.getOrden())) {
                a.setIdAnotacion(temp.getIdAnotacion());
                a.setIdMatricula(folio.getIdMatricula());

                //se obtiene los datos de la anotacion para compararlos con los datos q esten en la interfaz
                idNaturalezaB = temp.getNaturalezaJuridica().getIdNaturalezaJuridica();
                nombreNaturalezaB = temp.getNaturalezaJuridica().getNombre();
                valorB = temp.getValor();
                comentarioB = temp.getComentario();
                nat = temp.getNaturalezaJuridica();

                //obtener la lista de ciudadanos de esa anotacion y guardarla en ciudadanosB
                ciudadanosB = temp.getAnotacionesCiudadanos();
                break;
            }
        }

        if ((a.getIdAnotacion() == null) || (a.getIdAnotacion().equals(""))) {
            exception.addError("La anotación que va editar no existe.");
        }

        //Se valida los cambios
        String anotacionEstado = request.getParameter(CAnotacion.ESTADO_ANOTACION);

        if (anotacionEstado == null || anotacionEstado.length() != 1) {
            exception.addError("Debe especificar un estado para la anotacion");
        } else {
            EstadoAnotacion estadoAnotacion = new EstadoAnotacion();
            estadoAnotacion.setIdEstadoAn(anotacionEstado);
            a.setEstado(estadoAnotacion);

        }

        String numRadicaAnota = request.getParameter(CAnotacion.NUMERO_RADICACION_ANOTACION);

        if (numRadicaAnota != null) {
            numRadicaAnota = numRadicaAnota.toUpperCase();
            a.setNumRadicacion(numRadicaAnota);
        }

        String fechaRadicacion = request.getParameter(CSolicitudRegistro.CALENDAR2);
        String fechaDocumento = request.getParameter(CSolicitudRegistro.CALENDAR);

        int flagCapturarFechaRadiacion = 0;

        if ((fechaRadicacion == null) || fechaRadicacion.trim().equals("")) {
            exception.addError("La fecha de Radicación es inválida");
        } else {
            if (fechaRadicacion != null) {
                fechaRadicacionCalendar = darFecha(fechaRadicacion);
                if (fechaRadicacionCalendar.getTimeInMillis() > (new Date()).getTime()) {
                    exception.addError("La fecha de Radicación no puede ser mayor al día de hoy");
                } else {
                    flagCapturarFechaRadiacion = 1;
                }
            }
        }

        if ((fechaDocumento == null) || fechaDocumento.trim().equals("")) {
            exception.addError("La fecha del Documento es inválida");
        } else {
            if (fechaDocumento != null) {
                fechaDocumentoCalendar = darFecha(fechaDocumento);
                if (null == fechaDocumentoCalendar) {

                } else {

                    if ((null != fechaRadicacion) && (null != fechaRadicacionCalendar)
                            && (fechaRadicacionCalendar.getTimeInMillis() < fechaDocumentoCalendar.getTimeInMillis())) {
                        exception.addError("La fecha de Radicación no puede ser menor a la fecha del Documento");
                    } // if

                } // if

            } // if

        } // if

        //Se Debe actualizar la fecha de radicacion
        a.setFechaRadicacion(fechaRadicacionCalendar.getTime());

        //Se Actualiza la fecha del documento
        Documento documento = crearDocumento(request, exception, true);
        if (exception.getErrores().size() > 0) {
            throw exception;
        } else {
            a.setDocumento(documento);
        }

        //Se Actualiza los datos de Antiguo Sistema
//		 Verificar si se han digitado datos de antiguo sistema. De existir al menos un
        // campo no vacío significa que se deben crear datos de antiguo sistema
        StringBuffer buffer = new StringBuffer();
        buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS));
        buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS));
        buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS));
        buffer.append(request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS));
        buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS));
        buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS));
        buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS));
        buffer.append(request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
        buffer.append(request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS) == null ? "" : request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS));

        // Si hay datos de antiguo sistema, se crea un objeto DatosAntiguoSistema y se almacena
        // en el contexto
        if (buffer.length() > 0) {
            DatosAntiguoSistema datosAntiguoSistema = new DatosAntiguoSistema();

            /*if(!request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS ).equals(""))
				datosAntiguoSistema.setComentario(request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS ));*/
            if (!request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS).equals("")) {
                datosAntiguoSistema.setLibroAnio(request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS));
            }
            if (!request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS).equals("")) {
                datosAntiguoSistema.setLibroNumero(request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS));
            }
            if (!request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS).equals("")) {
                datosAntiguoSistema.setLibroPagina(request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS));
            }
            if (!request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS).equals("")) {
                datosAntiguoSistema.setLibroTipo(request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS));
            }
            if (!request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS).equals("")) {
                datosAntiguoSistema.setTomoAnio(request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS));
            }
            if (!request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS).equals("")) {
                datosAntiguoSistema.setTomoMunicipio(request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS));
            }
            if (!request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS).equals("")) {
                datosAntiguoSistema.setTomoNumero(request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS));
            }
            if (!request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS).equals("")) {
                datosAntiguoSistema.setTomoPagina(request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS));
            }

            a.setDatosAntiguoSistema(datosAntiguoSistema);
        }

        //Se debe actualizar las salvedades
        List salvedadesAnotacion = (List) request.getSession().getAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION);

        if ((salvedadesAnotacion != null) && (salvedadesAnotacion.size() > 0)) {
            Iterator it = salvedadesAnotacion.iterator();

            while (it.hasNext()) {
                SalvedadAnotacion salvedadAnotacion = (SalvedadAnotacion) it.next();
                salvedadAnotacion.setIdAnotacion(a.getIdAnotacion());
                a.addSalvedade(salvedadAnotacion);
            }
        }

        if (ciudadanosB == null) {
            ciudadanosB = new Vector();
        }
        if (idNaturalezaB == null) {
            idNaturalezaB = "";
        }
        if (nombreNaturalezaB == null) {
            nombreNaturalezaB = "";
        }
        if (comentarioB == null) {
            comentarioB = "";
        }

        //fin de obtención de los datosB (Before)
        Log.getInstance().debug(AWCrearFolio.class, "\n\n"
                + "La anotación a editar tenia los siguientes datos \n"
                + "ID =" + a.getIdAnotacion() + "\n"
                + "idNaturaleza =" + idNaturalezaB + "\n"
                + "nombreNaturaleza =" + nombreNaturalezaB + "\n"
                + "valor =" + Double.toString(valorB) + "\n"
                + "comentario =" + comentarioB + "\n"
                + "fin");

        //Comienzo de los datosA (After)
        String valorSA = (String) request.getSession().getAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        if (valorSA != null) {
            valorSA = valorSA.replaceAll(",", "");
        }

        double valorA = 0;
        if ((valorSA != null) && !valorSA.equals("")) {
            try {
                valorA = Double.parseDouble(valorSA);
            } catch (NumberFormatException e) {
                exception.addError("El valor de la especificación en la anotación es inválido");
            }
        }
        String idNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nombreNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        String comentarioA = (String) request.getSession().getAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        if (idNaturalezaA == null || idNaturalezaA.length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza juridica para la especificación en la anotación");
        }
        boolean esta = false;
        List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");

        Iterator ig = grupoNaturalezas.iterator();
        while (ig.hasNext()) {
            GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
            List natus = group.getNaturalezaJuridicas();
            Iterator id = natus.iterator();
            while (id.hasNext()) {
                NaturalezaJuridica natA = (NaturalezaJuridica) id.next();

                if (natA.getIdNaturalezaJuridica().equals(idNaturalezaA)) {
                    esta = true;
                }
            }
        }
        if (!esta) {
            exception.addError("Debe colocar un codigo de naturaleza juridica válido");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Log.getInstance().debug(AWCrearFolio.class, "\n\n"
                + "La anotacion en la interfaz tiene los siguientes datos \n"
                + "idNaturaleza =" + idNaturalezaA + "\n"
                + "nombreNaturaleza =" + nombreNaturalezaA + "\n"
                + "valor =" + Double.toString(valorA) + "\n"
                + "comentario =" + comentarioA + "\n"
                + "fin");

        //comienzo de comparacion y construccion de la anotacion edicion
        if (!idNaturalezaB.equals(idNaturalezaA)) {
            nat.setIdNaturalezaJuridica(idNaturalezaA);
            cambioNat = true;
        }
        if (!nombreNaturalezaB.equals(nombreNaturalezaA)) {
            nat.setNombre(nombreNaturalezaA);
            a.setEspecificacion(nombreNaturalezaA);
            cambioNat = true;
        }
        if (cambioNat) {
            a.setNaturalezaJuridica(nat);
        }

        if (valorA != valorB) {
            a.setValor(valorA);
            a.setToUpdateValor(true);
        }

        if (!comentarioB.equals(comentarioA)) {
            a.setComentario(comentarioA);
        }

        Log.getInstance().debug(AWCrearFolio.class, "\n\n"
                + "La anotación edición tiene los siguientes datos");
        if (a.getNaturalezaJuridica() != null) {
            Log.getInstance().debug(AWCrearFolio.class, "idNaturaleza =" + a.getNaturalezaJuridica().getIdNaturalezaJuridica() + "\n"
                    + "nombreNaturaleza =" + a.getNaturalezaJuridica().getNombre());
        } else {
            Log.getInstance().debug(AWCrearFolio.class, "idNaturaleza =" + "" + "\n"
                    + "nombreNaturaleza =" + "" + "\n");
        }
        Log.getInstance().debug(AWCrearFolio.class, "valor =" + Double.toString(a.getValor()) + "\n"
                + "comentario =" + a.getComentario() + "\n"
                + "fin");

        //parte de ciudadanos (aqui se recorrera la lista de ciudadanosT y se comparara con ciudadanosB para crear ciudadanosA)
        List ciudadanosA = new Vector();
        //se obtienen los ciudadanos mandados a eliminar
        Log.getInstance().debug(AWCrearFolio.class, "el # de ciudadanosB =" + ciudadanosB.size());
        Log.getInstance().debug(AWCrearFolio.class, "el # de ciudadanosT =" + ciudadanosT.size());

        int finAB = ciudadanosB.size();
        int cont = 0;
        while (cont != finAB) {
            AnotacionCiudadano cb = (AnotacionCiudadano) ciudadanosB.get(cont);
            AnotacionCiudadano ct = (AnotacionCiudadano) ciudadanosT.get(cont);
            Log.getInstance().debug(AWCrearFolio.class, "ciudadano cedula =" + ct.getCiudadano().getDocumento() + "toDelete = " + Boolean.toString(ct.isToDelete()));
            if ((cb.getIdCiudadano().equals(ct.getIdCiudadano())) && ct.isToDelete()) {
                ciudadanosA.add(ct);
            }
            cont++;
        }

        //se obtienen los ciudadanos agregados
        int inicioSL = ciudadanosB.size();
        int finSL = ciudadanosT.size();
        //List subList = ciudadanosT.subList(inicioSL, finSL);

        List temp = ciudadanosT.subList(inicioSL, finSL);
        List subList = new ArrayList();

        if (temp != null) {
            Iterator it = temp.iterator();
            while (it.hasNext()) {
                subList.add(it.next());
            }
        }

        //		toca remover los ciudadanos q han sido creados y eliminados en la misma pantalla
        Iterator isl = subList.iterator();
        while (isl.hasNext()) {
            AnotacionCiudadano c = (AnotacionCiudadano) isl.next();
            if (c.isToDelete()) {
                isl.remove();
            }
        }
        int SL = subList.size();
        Log.getInstance().debug(AWCrearFolio.class, "el tamaño de la Sublista =" + SL);

        ciudadanosA.addAll(subList);
        Log.getInstance().debug(AWCrearFolio.class, "el # de ciudadanos a editar y/o agregar es = " + ciudadanosA.size());

        /*		Iterator ica= ciudadanosA.iterator();
				while(ica.hasNext()){
				a.addAnotacionesCiudadano((AnotacionCiudadano) ica.next());
			}
         */
        Iterator ica = ciudadanosT.iterator();
        while (ica.hasNext()) {
            a.addAnotacionesCiudadano((AnotacionCiudadano) ica.next());
        }

        //Se valida si cambio los datos del documento
        //List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        List anotaciones = null;
        if (anotaciones == null) {
            anotaciones = new Vector();
        }
        anotaciones.add(a);

        //codigo de grabar en temporal
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();
            while (itAnotaciones.hasNext()) {
                folio.addAnotacione((Anotacion) itAnotaciones.next());
            }
        } else {
            return null;
        }
        request.getSession().setAttribute(WebKeys.FOLIO, folio);

        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuario, EvnCrearFolio.GRABAR_ANOTACIONES_TEMPORALMENTE);
        evnCrearFolio.setFolio(folio);
        evnCrearFolio.setUsuario(usuarioAuriga);
        evnCrearFolio.setUsuarioSir(usuarioNeg);
        evnCrearFolio.setAnotacionesGuardar(anotaciones);
        return evnCrearFolio;
    }

    //CREAR EL NUEVO FOLIO
    /**
     * @param request
     * @return
     */
    private Evento terminarFolio(HttpServletRequest request) throws AccionWebException {
        ErrorCrearAnotacionException exception = new ErrorCrearAnotacionException();

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        DatosAntiguoSistema datosAntiguoSistema = (DatosAntiguoSistema) request.getSession().getAttribute(WebKeys.DATOS_ANTIGUO_SISTEMA);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        //Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if ((anotaciones == null) || (anotaciones.size() == 0)) {
            exception.addError("No se puede crear un folio sin anotaciones");
            throw exception;
        }

        int tam = anotaciones.size();

        for (int i = 0; i < tam; i++) {
            Anotacion anota = (Anotacion) anotaciones.get(i);
            TipoAnotacion tipoAnota = new TipoAnotacion();
            tipoAnota.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
            anota.setTipoAnotacion(tipoAnota);
        }

        removerAnotacionesFromFolio(folio);
        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();

            while (itAnotaciones.hasNext()) {
                folio.addAnotacione((Anotacion) itAnotaciones.next());
            }
        }

        folio.setEstado(null);

        EvnCrearFolio evento = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.CREACION_FOLIO, folio, usuario);
        evento.setDatosAntiguoSistema(datosAntiguoSistema);
        evento.setCirculo(circulo);

        return evento;
    }

    /**
     * Remueve las anotaciones del folio.
     *
     * @param folio
     */
    private void removerAnotacionesFromFolio(Folio folio) {
        Anotacion anotacion;

        List anotaciones = null;

        if (folio != null) {
            anotaciones = folio.getAnotaciones();
        }

        if (anotaciones != null) {
            int max = anotaciones.size();

            for (int i = 0; i < max; i++) {
                anotacion = (Anotacion) anotaciones.get(0);
                folio.removeAnotacione(anotacion);
            }
        }
    }

    //CONSULTAR LA COMPLEMENTACIÓN
    /**
     * @param request
     * @return
     */
    private Evento consultaFolioComplementacion(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        preservarInfoBasicaFolio(request);

        String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
        request.getSession().removeAttribute(WebKeys.MENSAJE);

        if (idMatricula == null) {
            idMatricula = (String) request.getSession().getAttribute(CFolio.ID_MATRICULA);
        }

        String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO + AWModificarFolio.FOLIO_COMPLEMENTACION);
        request.getSession().setAttribute(CFolio.SELECCIONAR_FOLIO + AWModificarFolio.FOLIO_COMPLEMENTACION, tipoComp);

        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);

        return new EvnCertificado(usuarioAuriga, usuarioSIR, EvnCertificado.CONSULTA_FOLIO_COMPLEMENTACION, folio);
    }

    //REFRESCAR LA ANOTACIÓN
    /**
     * @param request
     * @return
     */
    private Evento refrescarAnotacion(HttpServletRequest request) {
        request.getSession().setAttribute(CAnotacionCiudadano.SECUENCIA, request.getParameter(CAnotacionCiudadano.SECUENCIA));
        request.getSession().setAttribute("CAMBIO", "SI");
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        return null;
    }

    //FUNCIONES PARA MANEJO DE LOS CIUDADANOS
    /**
     * @param request
     * @param i
     * @return
     */
    private boolean agregoPersona(HttpServletRequest request, int i) {
        int cantDatosValidos = 0;

        String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + i);
        if (numId != null && numId.trim().length() > 0) {
            cantDatosValidos++;
        }
        String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
        if (apellido1 != null && apellido1.trim().length() > 0) {
            cantDatosValidos++;
        }

        //Comentado dado que no es obligatorio el segundo apellido para ningun tipo de
        //Docuemento de identidad.
        /*String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
                        if (apellido2 != null) {
                                return true;
                        }*/
        String tipoID = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA);
        String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA + i);

        if (nombres != null && nombres.trim().length() > 0) {
            cantDatosValidos++;
        }

        boolean verificarNombre = false;
        if (tipoID != null && (tipoID.equals(CCiudadano.TIPO_DOC_ID_CEDULA)
                || tipoID.equals(CCiudadano.TIPO_DOC_ID_CEDULA_EXTRANJERIA)
                || tipoID.equals(CCiudadano.TIPO_DOC_ID_TARJETA))) {
            verificarNombre = true;
        }

        if (verificarNombre && nombres != null && nombres.trim().length() > 0) {
            cantDatosValidos++;
        }

        String valPorcentaje = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
        if (valPorcentaje != null && valPorcentaje.trim().length() > 0) {
            cantDatosValidos++;
        }

        if (cantDatosValidos > 0) {
            return true;
        }

        return false;
    }

    /**
     * @param request
     */
    private void preservarInfoBasicaCiudadano(HttpServletRequest request) {
        if (request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA, request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_PERSONA, request.getParameter(CFolio.ANOTACION_TIPO_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_COD_VERIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COD_VERIFICACION, request.getParameter(CFolio.ANOTACION_COD_VERIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_SEXO_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA, request.getParameter(CFolio.ANOTACION_SEXO_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA, request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA, request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA, request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA, request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION, request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION));
        }

        if (request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION, request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION));
        }
    }

    /**
     * @param request
     */
    private void preservarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        String key = null;
        Object parametro = null;

        for (int i = 0; i < numCiud; i++) {
            key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_SEXO_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_COD_VERIFICACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }
        }
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        for (int i = 0; i < numCiud; i++) {
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_COD_VERIFICACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);
        }
    }

    //FUNCIONES PRESERVAR LA INFORMACIÓN DEL FOLIO EN LA SESIÓN.
    /**
     * @param request
     */
    private void preservarInfoBasicaFolio(HttpServletRequest request) {
        //Se salva temporalmente la informacion sobre el folio. Esto no hace persistente el folio, ni cumple con el requerimiento de grabar de manera temporal

        if (request.getParameter(CSolicitudRegistro.CALENDAR_APERTURA) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.CALENDAR_APERTURA, request.getParameter(CSolicitudRegistro.CALENDAR_APERTURA));
        }

        if (request.getParameter(CSolicitudRegistro.NUMERO_RADICACION) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.NUMERO_RADICACION, request.getParameter(CSolicitudRegistro.NUMERO_RADICACION));
        }

        if (request.getParameter(CFolio.FOLIO_ID_ESTADO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_ID_ESTADO, request.getParameter(CFolio.FOLIO_ID_ESTADO));
        }

        if (request.getParameter(CFolio.FOLIO_NUM_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_NUM_RADICACION, request.getParameter(CFolio.FOLIO_NUM_RADICACION));
        }

        if (request.getParameter(CFolio.FOLIO_ID_MATRICULA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_ID_MATRICULA, request.getParameter(CFolio.FOLIO_ID_MATRICULA));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_COMENTARIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMENTARIO, request.getParameter(CFolio.FOLIO_COMENTARIO));
        }

        if (request.getParameter(CFolio.FOLIO_ESTADO_FOLIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_ESTADO_FOLIO, request.getParameter(CFolio.FOLIO_ESTADO_FOLIO));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO, request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
        }

        if (request.getParameter(CFolio.FOLIO_NUPRE) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_NUPRE, request.getParameter(CFolio.FOLIO_NUPRE));
        }

        if (request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE, request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE));
        }

        if (request.getParameter(CFolio.FOLIO_AVALUO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_AVALUO, request.getParameter(CFolio.FOLIO_AVALUO));
        }

        if (request.getParameter(CFolio.FOLIO_FECHA_AVALUO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_FECHA_AVALUO, request.getParameter(CFolio.FOLIO_FECHA_AVALUO));
        }

        if (request.getParameter(CFolio.FOLIO_DESTINACION_ECONOMICA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_DESTINACION_ECONOMICA, request.getParameter(CFolio.FOLIO_DESTINACION_ECONOMICA));
        }

        if (request.getParameter(CFolio.FOLIO_PRIVMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_PRIVMETROS, request.getParameter(CFolio.FOLIO_PRIVMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_PRIVCENTIMETROS, request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_CONSMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_CONSMETROS, request.getParameter(CFolio.FOLIO_CONSMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_CONSCENTIMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_CONSCENTIMETROS, request.getParameter(CFolio.FOLIO_CONSCENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_COEFICIENTE) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COEFICIENTE, request.getParameter(CFolio.FOLIO_COEFICIENTE));
        }

        if (request.getParameter(CFolio.FOLIO_HECTAREAS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_HECTAREAS, request.getParameter(CFolio.FOLIO_HECTAREAS));
        }

        if (request.getParameter(CFolio.FOLIO_METROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_METROS, request.getParameter(CFolio.FOLIO_METROS));
        }

        if (request.getParameter(CFolio.FOLIO_CENTIMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_CENTIMETROS, request.getParameter(CFolio.FOLIO_CENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_LINDEROS_DEFINIDOS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LINDEROS_DEFINIDOS, request.getParameter(CFolio.FOLIO_LINDEROS_DEFINIDOS));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTACION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, request.getParameter(CFolio.FOLIO_COMPLEMENTACION));
        }

        if (request.getParameter(CFolio.FOLIO_LINDERO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LINDERO, request.getParameter(CFolio.FOLIO_LINDERO));
        }

        if (request.getParameter(CFolio.FOLIO_COD_CATASTRAL) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COD_CATASTRAL, request.getParameter(CFolio.FOLIO_COD_CATASTRAL));
        }

        if (request.getParameter(CFolio.FOLIO_EJE1) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_EJE1, request.getParameter(CFolio.FOLIO_EJE1));
        }

        if (request.getParameter(CFolio.FOLIO_EJE2) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_EJE2, request.getParameter(CFolio.FOLIO_EJE2));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR1) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_VALOR1, request.getParameter(CFolio.FOLIO_VALOR1));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR2) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_VALOR2, request.getParameter(CFolio.FOLIO_VALOR2));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR, request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA));
        }
        /*
                *  @author Carlos Torres
                *  @chage   se agrega validacion de version diferente
                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_OFICINA_VERSION, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_DOCUMENTO, request.getParameter(CFolio.FOLIO_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.FOLIO_NUM_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_NUM_DOCUMENTO, request.getParameter(CFolio.FOLIO_NUM_DOCUMENTO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute("tipo_oficina", request.getParameter("tipo_oficina"));
            request.getSession().setAttribute("folio_tipo_nom_oficina", request.getParameter("tipo_nom_oficina"));
            request.getSession().setAttribute("folio_numero_oficina", request.getParameter("numero_oficina"));
            request.getSession().setAttribute("folio_id_oficina", request.getParameter("id_oficina"));
            /*
                            *  @author Carlos Torres
                            *  @chage   se agrega validacion de version diferente
                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            request.getSession().setAttribute("folio_version", request.getParameter("version"));
        }

        request.getSession().setAttribute("FOLIO_ANTIGUO_SISTEMA", request.getParameter("FOLIO_ANTIGUO_SISTEMA"));

        preservarInfoBasicaDocumento(request);
        preservarInfoAntiguoSistema(request);
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaFolio(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.FOLIO_NUM_RADICACION);

        request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR_APERTURA);
        request.getSession().removeAttribute(CFolio.FOLIO_ID_MATRICULA);
        request.getSession().removeAttribute(CSolicitudRegistro.NUMERO_RADICACION);
        request.getSession().removeAttribute(CFolio.FOLIO_ID_ESTADO);
        request.getSession().removeAttribute(CFolio.FOLIO_ID_MATRICULA);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        request.getSession().removeAttribute(CFolio.FOLIO_ESTADO_FOLIO);
        request.getSession().removeAttribute(CFolio.FOLIO_COMENTARIO);
        request.getSession().removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        request.getSession().removeAttribute(CFolio.FOLIO_NUPRE);
        request.getSession().removeAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE);
        request.getSession().removeAttribute(CFolio.FOLIO_AVALUO);
        request.getSession().removeAttribute(CFolio.FOLIO_FECHA_AVALUO);
        request.getSession().removeAttribute(CFolio.FOLIO_DESTINACION_ECONOMICA);
        request.getSession().removeAttribute(CFolio.FOLIO_PRIVMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_PRIVCENTIMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_CONSMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_CONSCENTIMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_COEFICIENTE);
        request.getSession().removeAttribute(CFolio.FOLIO_HECTAREAS);
        request.getSession().removeAttribute(CFolio.FOLIO_METROS);
        request.getSession().removeAttribute(CFolio.FOLIO_CENTIMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_LINDEROS_DEFINIDOS);
        request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTACION);
        request.getSession().removeAttribute(WebKeys.LISTA_SALVEDADES_FOLIO);
        request.getSession().removeAttribute(CFolio.FOLIO_LINDERO);
        request.getSession().removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        request.getSession().removeAttribute(CFolio.FOLIO_COD_CATASTRAL);

    }

    //FUNCIONES PARA PRESERVAR LA INFORMACIÓN DE LA ANOTACIÓN
    /**
     * @param request
     */
    private void preservarInfoBasicaAnotacion(HttpServletRequest request) {

        if (request.getParameter(CAnotacion.ESTADO_ANOTACION) != null) {
            request.getSession().setAttribute(CAnotacion.ESTADO_ANOTACION, request.getParameter(CAnotacion.ESTADO_ANOTACION));
        }

        if (request.getParameter(CAnotacion.NUMERO_RADICACION_ANOTACION) != null) {
            request.getSession().setAttribute(CAnotacion.NUMERO_RADICACION_ANOTACION, request.getParameter(CAnotacion.NUMERO_RADICACION_ANOTACION));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }

        if (request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO, request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_RADICACION, request.getParameter(CFolio.ANOTACION_FECHA_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_RADICACION, request.getParameter(CFolio.ANOTACION_NUM_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_MODALIDAD) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_MODALIDAD, request.getParameter(CFolio.ANOTACION_MODALIDAD));
        }

        if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO, request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO, request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute("tipo_oficina", request.getParameter("tipo_oficina"));
            request.getSession().setAttribute("tipo_nom_oficina", request.getParameter("tipo_nom_oficina"));
            request.getSession().setAttribute("numero_oficina", request.getParameter("numero_oficina"));
            request.getSession().setAttribute("id_oficina", request.getParameter("id_oficina"));
            /*
                         *  @author Carlos Torres
                         *  @chage   se agrega validacion de version diferente
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            request.getSession().setAttribute("version", request.getParameter("version"));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Se asigna valor a la propiedad version
         */

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }

        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS + "1",
                request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS + "1",
                request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.COMENTARIO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS + "1"));

        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS + "1"));

        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS + "1"));
        request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS + "1",
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS + "1"));
        preservarInfoBasicaDocumento(request);

    }

    /**
     * @param request
     */
    private void preservarInfoBasicaDocumento(HttpServletRequest request) {

        if (request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO, request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO));
        }
        if (request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO, request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO));
        }
        if (request.getParameter(CSolicitudRegistro.ID_ENCABEZADO) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ID_ENCABEZADO, request.getParameter(CSolicitudRegistro.ID_ENCABEZADO));
        }
        if (request.getParameter(CSolicitudRegistro.CALENDAR) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.CALENDAR, request.getParameter(CSolicitudRegistro.CALENDAR));
        }

        if (request.getParameter(CSolicitudRegistro.CALENDAR2) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.CALENDAR2, request.getParameter(CSolicitudRegistro.CALENDAR2));
        }

        if (request.getParameter(WebKeys.TIPO_OFICINA_I_N) != null) {
            request.getSession().setAttribute(WebKeys.TIPO_OFICINA_I_N, request.getParameter(WebKeys.TIPO_OFICINA_I_N));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO, request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }
        if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }
        if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        if (request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }
        if (request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL) != null) {
            request.getSession().setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL, request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL));
        }
    }

//	FUNCIONES PARA PRESERVAR LA INFORMACIÓN DE LA ANOTACIÓN
    /**
     * @param request
     */
    private void preservarInfoBasicaAnotacionEdicion(HttpServletRequest request) {

        //Metodo para cargar la informacion de un folio
        String numAnotacionEdicion = (String) request.getSession().getAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL);

        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        if (anotaciones == null) {
            anotaciones = new Vector();
        }
        Anotacion anotaEdicion = new Anotacion();

        if (!anotaciones.isEmpty()) {
            Iterator itAnotaciones = anotaciones.iterator();
            int i = 0;
            String msg_EditAnotacion;
            msg_EditAnotacion = "Editar anotaci&oacute;n";
            while (itAnotaciones.hasNext()) {
                Anotacion anotacion = (Anotacion) itAnotaciones.next();
                i++;
                if (i == Integer.parseInt(numAnotacionEdicion)) {
                    anotaEdicion = anotacion;
                }
            }
        }

        if (anotaEdicion.getEstado() != null) {
            request.getSession().setAttribute(CAnotacion.ESTADO_ANOTACION, anotaEdicion.getEstado().getIdEstadoAn());
        }

        if (anotaEdicion.getNumRadicacion() != null) {
            request.getSession().setAttribute(CAnotacion.NUMERO_RADICACION_ANOTACION, anotaEdicion.getNumRadicacion());
        }

        if (anotaEdicion.getDocumento().getNumero() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, anotaEdicion.getDocumento().getNumero());
        }

        if (anotaEdicion.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, anotaEdicion.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
        }

        if (anotaEdicion.getDocumento().getOficinaOrigen().getTipoOficina().getIdTipoOficina() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO, anotaEdicion.getDocumento().getOficinaOrigen().getTipoOficina().getIdTipoOficina());
        }

        if (anotaEdicion.getComentario() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, anotaEdicion.getComentario());
        }

        if (anotaEdicion.getDocumento().getFecha() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO, anotaEdicion.getDocumento().getFecha());
        }

        if (anotaEdicion.getFechaRadicacion() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_RADICACION, anotaEdicion.getFechaRadicacion());
        }

        if (anotaEdicion.getNumRadicacion() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_RADICACION, anotaEdicion.getNumRadicacion());
        }

        if (anotaEdicion.getNaturalezaJuridica().getNombre() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION, anotaEdicion.getNaturalezaJuridica().getNombre());
        }

        if (true) {
            request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, String.valueOf(anotaEdicion.getValor()));
        }

        if (anotaEdicion.getDocumento().getTipoDocumento() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO, anotaEdicion.getDocumento().getTipoDocumento());
        }

        if (anotaEdicion.getDocumento().getNumero() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO, anotaEdicion.getDocumento().getNumero());
        }

        if (anotaEdicion.getDocumento().getOficinaOrigen() != null) {
            if (anotaEdicion.getDocumento().getOficinaOrigen().getIdOficinaOrigen() != null) {
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, anotaEdicion.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
                /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, anotaEdicion.getDocumento().getOficinaOrigen().getVersion().toString());
            }

            if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getIdDepartamento() != null) {
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getIdDepartamento());
            }

            if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre() != null) {
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());
            }

            if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getIdMunicipio() != null) {
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getIdMunicipio());
            }

            if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre() != null) {
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre());
            }

            if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getIdVereda() != null) {
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getIdVereda());
            }

            if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getNombre() != null) {
                request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getNombre());
            }

            if (anotaEdicion.getDocumento().getOficinaOrigen() != null) {
                request.getSession().setAttribute("tipo_oficina", anotaEdicion.getDocumento().getOficinaOrigen().getTipoOficina().getIdTipoOficina());
                request.getSession().setAttribute("tipo_nom_oficina", anotaEdicion.getDocumento().getOficinaOrigen().getNombre());
                request.getSession().setAttribute("numero_oficina", anotaEdicion.getDocumento().getOficinaOrigen().getNumero());
                request.getSession().setAttribute("id_oficina", anotaEdicion.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
                /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                request.getSession().setAttribute("version", anotaEdicion.getDocumento().getOficinaOrigen().getVersion().toString());
            }
        }

        if (anotaEdicion.getDocumento().getOficinaInternacional() != null) {
            request.getSession().setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL, anotaEdicion.getDocumento().getOficinaInternacional());
        }

        if (anotaEdicion.getNaturalezaJuridica().getIdNaturalezaJuridica() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, anotaEdicion.getNaturalezaJuridica().getIdNaturalezaJuridica());
        }

        if (anotaEdicion.getNaturalezaJuridica().getNombre() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, anotaEdicion.getNaturalezaJuridica().getNombre());
        }

        if (anotaEdicion.getDatosAntiguoSistema() != null) {

            request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS, anotaEdicion.getDatosAntiguoSistema().getLibroTipo());

            request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS, anotaEdicion.getDatosAntiguoSistema().getLibroNumero());

            request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS, anotaEdicion.getDatosAntiguoSistema().getLibroPagina());

            request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS, anotaEdicion.getDatosAntiguoSistema().getLibroAnio());

            request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS, anotaEdicion.getDatosAntiguoSistema().getTomoNumero());

            request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS, anotaEdicion.getDatosAntiguoSistema().getTomoPagina());

            request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS, anotaEdicion.getDatosAntiguoSistema().getTomoMunicipio());

            request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS, anotaEdicion.getDatosAntiguoSistema().getTomoAnio());

            request.getSession().setAttribute(CDatosAntiguoSistema.COMENTARIO_AS, anotaEdicion.getDatosAntiguoSistema().getComentario());

            if (anotaEdicion.getDatosAntiguoSistema().getDocumento() != null) {

                request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getTipoDocumento());

                request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getTipoDocumento());

                request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getNumero());

                if (anotaEdicion.getDatosAntiguoSistema().getDocumento().getOficinaOrigen() != null) {

                    request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getOficinaOrigen().getVereda().getIdDepartamento());

                    request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());

                    request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getOficinaOrigen().getVereda().getIdMunicipio());

                    request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getOficinaOrigen().getVereda().getIdVereda());

                    request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getOficinaOrigen().getVereda().getNombre());

                    request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getOficinaOrigen().getNumero());

                    request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS, anotaEdicion.getDatosAntiguoSistema().getDocumento().getOficinaOrigen().getNumero());

                }

                request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS, anotaEdicion.getDatosAntiguoSistema().getFechaCreacion());

                request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS, anotaEdicion.getDatosAntiguoSistema().getComentario());
            }
        }

        //Subir los ciudadanos
        List anotacionCiudadanos = new ArrayList();
        anotacionCiudadanos.addAll(anotaEdicion.getAnotacionesCiudadanos());
        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, anotacionCiudadanos);

        //Subir salvedades al folio
        List salvedadesAnotacion = new ArrayList();
        salvedadesAnotacion.addAll(anotaEdicion.getSalvedades());
        request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION, salvedadesAnotacion);

        preservarInfoBasicaDocumentoEdicion(request, anotaEdicion);

    }

    /**
     * @param request
     */
    private void preservarInfoBasicaDocumentoEdicion(HttpServletRequest request, Anotacion anotaEdicion) {

        //anotaEdicion.getDocumento().getTipoDocumento()
        if (anotaEdicion.getDocumento().getTipoDocumento().getIdTipoDocumento() != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO, anotaEdicion.getDocumento().getTipoDocumento().getIdTipoDocumento());
        }
        if (anotaEdicion.getDocumento().getTipoDocumento().getIdTipoDocumento() != null) {
            request.getSession().setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO, anotaEdicion.getDocumento().getTipoDocumento().getIdTipoDocumento());
        }
        if (anotaEdicion.getDocumento().getNumero() != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ID_ENCABEZADO, anotaEdicion.getDocumento().getNumero());
        }

        if (anotaEdicion.getDocumento().getFecha() != null) {
            String cadenaFecha = DateFormatUtil.format(anotaEdicion.getDocumento().getFecha());
            request.getSession().setAttribute(CSolicitudRegistro.CALENDAR, cadenaFecha);
        }

        if (anotaEdicion.getDocumento().getFecha() != null) {
            String cadenaFecha = DateFormatUtil.format(anotaEdicion.getFechaRadicacion());
            request.getSession().setAttribute(CSolicitudRegistro.CALENDAR2, cadenaFecha);
        }

        if (request.getParameter(WebKeys.TIPO_OFICINA_I_N) != null) {
            request.getSession().setAttribute(WebKeys.TIPO_OFICINA_I_N, anotaEdicion.getDocumento().getOficinaOrigen().getTipoOficina().getIdTipoOficina());
        }

        if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());
        }
        if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getIdDepartamento() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getIdDepartamento());
        }
        if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre());
        }
        if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getIdMunicipio() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getIdMunicipio());
        }
        if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getNombre() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getNombre());
        }
        if (anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getIdVereda() != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, anotaEdicion.getDocumento().getOficinaOrigen().getVereda().getIdVereda());
        }

        if (anotaEdicion.getDocumento().getOficinaOrigen().getTipoOficina().getNombre() != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO, anotaEdicion.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
        }
        if (anotaEdicion.getDocumento().getOficinaOrigen().getNombre() != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM, anotaEdicion.getDocumento().getOficinaOrigen().getNombre());
        }
        if (anotaEdicion.getDocumento().getOficinaOrigen().getIdOficinaOrigen() != null) {
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, anotaEdicion.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
            /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            request.getSession().setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, anotaEdicion.getDocumento().getOficinaOrigen().getVersion().toString());

        }

        if (anotaEdicion.getDocumento().getOficinaInternacional() != null) {
            request.getSession().setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL, anotaEdicion.getDocumento().getOficinaInternacional());
            request.getSession().setAttribute(WebKeys.TIPO_OFICINA_I_N, "TIPO_OFICINA_INTERNACIONAL");
        } else {
            request.getSession().setAttribute(WebKeys.TIPO_OFICINA_I_N, "TIPO_OFICINA_NACIONAL");
        }
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaAnotacion(HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.removeAttribute(CAnotacion.NUMERO_RADICACION_ANOTACION);
        session.removeAttribute(CAnotacion.ESTADO_ANOTACION);
        session.removeAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION);
        session.removeAttribute(CFolio.ANOTACION_NUM_RADICACION);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        session.removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        session.removeAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
        session.removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        session.removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION);
        session.removeAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
        session.removeAttribute(CFolio.ANOTACION_MODALIDAD);
        session.removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        session.removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        session.removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        session.removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS);
        session.removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS);
        session.removeAttribute("tipo_oficina");
        session.removeAttribute("tipo_nom_oficina");
        session.removeAttribute("numero_oficina");
        session.removeAttribute("id_oficina");
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        session.removeAttribute("version");

        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.TOMO_ANO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.COMENTARIO_AS + "1");

        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS + "1");

        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS + "1");
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS + "1");

        session.removeAttribute(VER_ANTIGUO_SISTEMA_ANOTACION);

        eliminarInfoBasicaDocumento(request);
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaSalvedad(HttpServletRequest request) {
        request.getSession().removeAttribute(CSalvedadFolio.CALENDAR_RADICACION);
        request.getSession().removeAttribute(CSalvedadFolio.NUMERO_RADICACION_SALVEDAD_FOLIO);
        request.getSession().removeAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaSalvedadAnotacion(HttpServletRequest request) {
        request.getSession().removeAttribute(CSalvedadAnotacion.CALENDAR_RADICACION_SALVEDAD_ANOTACION);
        request.getSession().removeAttribute(CSalvedadAnotacion.NUMERO_RADICACION_SALVEDAD_ANOTACION);
        request.getSession().removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaDocumento(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.CALENDAR);
        session.removeAttribute(CSolicitudRegistro.CALENDAR2);

        session.removeAttribute(WebKeys.TIPO_OFICINA_I_N);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        session.removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);

        session.removeAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL);

    }

    //FUNCIONES COMPLEMENTARIOS
    /**
     * @param request
     * @return
     */
    private Evento preservarInfo(HttpServletRequest request) {
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaCiudadano(request);
        return null;
    }

    private void preservarInfoAntiguoSistema(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
        session.setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS,
                request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS));
        session.setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS));
        session.setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS,
                request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS));
        session.setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS,
                request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS));
        session.setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS));
        session.setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS,
                request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS));
        session.setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS,
                request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS));
        session.setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS,
                request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS));
        session.setAttribute(CDatosAntiguoSistema.COMENTARIO_AS,
                request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS));

        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));

        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS,
                request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS));
    }

    //OPCIÓN CANCELAR
    /**
     * @param request
     * @return
     */
    private Evento cancelarCreacion(HttpServletRequest request) throws AccionWebException {
        eliminarInfoBasicaFolio(request);
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoDireccion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        eliminarInfoBasicaDocumento(request);

        // refactored
        HttpSession session = request.getSession();

        session.removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        session.removeAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        session.removeAttribute(WebKeys.FOLIO_EDITADO);

        eliminarAtributosSesion(request);

        return null;
    }

    /**
     * @param request
     */
    private void eliminarInfoDireccion(HttpServletRequest request) {

        // refactored
        HttpSession session = request.getSession();

        session.removeAttribute(CFolio.FOLIO_EJE1);
        session.removeAttribute(CFolio.FOLIO_EJE2);
        session.removeAttribute(CFolio.FOLIO_VALOR1);
        session.removeAttribute(CFolio.FOLIO_VALOR2);
        session.removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
    }

    /**
     * @param fechaInterfaz
     * @return
     */
    private static Calendar darFecha(String fechaInterfaz) {
        try {
            /*date = */
            DateFormatUtil.parse(fechaInterfaz);
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

            if ((calendar.get(Calendar.YEAR) == año) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                return calendar;
            }
        }

        return null;
    }

//  guarda info folio
    private void doEndProcess_AdminCrearFolio_SaveInfo_Step1(HttpServletRequest request, EvnRespCrearFolio respuesta) {

        if (null == respuesta) {
            return;
        }

        HttpSession session;
        session = request.getSession();

        // Turno param_Turno;
        Folio param_FolioEditado;
        long param_FolioEditadoAnotacionesNextOrden;

        // param_Turno        = respuesta.getTurno();
        param_FolioEditado = (Folio) respuesta.getPayload();
        param_FolioEditadoAnotacionesNextOrden = respuesta.getAnotacionesNextOrden();

        // if( null == param_Turno ) {
        //	return;
        // }
        // session.setAttribute( WebKeys.TURNO, param_Turno );
        // session.removeAttribute( WebKeys.FOLIO_EDITADO );
        if (null == param_FolioEditado) {
            return;
        }

        // eliminarAtributosSesion( request );
        // session.setAttribute( WebKeys.FOLIO        , param_FolioEditado );
        // session.setAttribute( WebKeys.FOLIO_EDITADO, param_FolioEditado );
        session.setAttribute(WebKeys.FOLIO, param_FolioEditado);
        session.setAttribute(WebKeys.FOLIO_EDITADO, param_FolioEditado);

        session.setAttribute(CFolio.NEXT_ORDEN_ANOTACION, String.valueOf(param_FolioEditadoAnotacionesNextOrden));

        //    	 additional // PK
        doProcess_StepX_SaveState_FolioId(request);

        // reset para los fields de anotacion ------
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        request.getSession().removeAttribute(WebKeys.HAY_REFRESCO);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        // -----------------------------------------
        // reset para los fields de direccion ------
        session.removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        // -----------------------------------------

        // cargar el conjunto de anotaciones que
        // estan en el folio;
        load_Data:
        {

            List local_Folio_Object;

            local_Folio_Object = param_FolioEditado.getAnotaciones();
            session.setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, local_Folio_Object);

        } // load_Data

    } // end-method:

    private void
            doEndProcess_AdminCrearFolio_SaveInfo_StepX_LoadState(HttpServletRequest request, EvnResp_AdminCrearFolioSaveInfo respuesta) {

        if (null == respuesta) {
            return;
        }

        HttpSession session;
        session = request.getSession();

        // Turno param_Turno;
        Folio param_FolioEditado;

        // param_Turno        = respuesta.getTurno();
        param_FolioEditado = respuesta.getFolioEditado();

        // if( null == param_Turno ) {
        //	return;
        // }
        // session.setAttribute( WebKeys.TURNO, param_Turno );
        // session.removeAttribute( WebKeys.FOLIO_EDITADO );
        if (null == param_FolioEditado) {
            return;
        }

        // hacer reset del bean (limpiar la forma),
        // hacer bind (data2form)
        process_DeleteDataFolio:
        {

        } // :process_DeleteDataFolio

        process_LoadData:
        {

            // :: local variables ----------------------------------------------
            // ...... source variables
            JXPathContext local_JxContext;
            Object local_JxContextSource;
            // String 			local_JxSearchString;

            // ...... target variables
            // List local_TargetSalvedadesList;
            // target jx-object
            local_JxContextSource = param_FolioEditado;

            // :: initialize
            local_JxContext = JXPathContext.newContext(local_JxContextSource);
            local_JxContext.setLenient(true);
            local_JxContext.setFactory(getFolioAbstractFactory());
            local_JxContext.setFunctions(new ClassFunctions(Local_Formats.class, "format"));

            // (declare variables)
            local_JxContext.getVariables().declareVariable("nullValue", (String) null);

            // -----------------------------------------------------------------
            // fix the results
            // local_JxContext.setValue( "$local_NumRadicacion", local_Param_TurnoId );
            loadData_Folio:
            {

                session.setAttribute(
                        "ID_MATRICULA",
                        local_JxContext.getValue("format:substringAfter( idMatricula, '-' )")
                ); // TODO: Nvl( substring-after( '$idCirculo - ', idMatricula ) ) //DT:string
                session.setAttribute(
                        "FOLIO_ID_MATRICULA",
                        local_JxContext.getValue("format:substringAfter( idMatricula, '-' )")
                ); // TODO: Nvl( substring-after( '$idCirculo - ', idMatricula ) ) //DT:string
                session.setAttribute(
                        "CALENDAR_APERTURA",
                        local_JxContext.getValue("format:toString( fechaApertura, 'dd/MM/yyyy' )")
                ); // DT:date

                session.setAttribute(
                        "NUMERO_RADICACION",
                        local_JxContext.getValue("radicacion")
                ); // DT:string

                session.setAttribute(
                        "FOLIO_ID_ESTADO",
                        local_JxContext.getValue("estado/idEstado")
                ); // DT:string

                session.setAttribute(
                        "FOLIO_ESTADO_FOLIO",
                        local_JxContext.getValue("estado/idEstado") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                //// bug 05165
                session.setAttribute(
                        "FOLIO_COMENTARIO",
                        local_JxContext.getValue("estado/comentario") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_LOCACION_ID_DEPTO",
                        local_JxContext.getValue("zonaRegistral/vereda/municipio/departamento/idDepartamento") // "zonaRegistral/vereda/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_LOCACION_NOM_DEPTO",
                        local_JxContext.getValue("zonaRegistral/vereda/municipio/departamento/nombre") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_LOCACION_ID_MUNIC",
                        local_JxContext.getValue("zonaRegistral/vereda/municipio/idMunicipio") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_LOCACION_NOM_MUNIC",
                        local_JxContext.getValue("zonaRegistral/vereda/municipio/nombre") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_LOCACION_ID_VEREDA",
                        local_JxContext.getValue("zonaRegistral/vereda/idVereda") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_LOCACION_NOM_VEREDA",
                        local_JxContext.getValue("zonaRegistral/vereda/nombre") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_TIPO_PREDIO",
                        local_JxContext.getValue("tipoPredio/idPredio") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                // TODO: verificar si esta en complementacionTMP/idComplementacionTMP
                // TODO: verificar si esta en complementacionTMP/complementacion
                session.setAttribute(
                        "FOLIO_ID_COMPLEMENTACION",
                        local_JxContext.getValue("complementacion/idComplementacion") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_COMPLEMENTACION",
                        local_JxContext.getValue("complementacion/complementacion") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_LINDERO",
                        local_JxContext.getValue("lindero") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

                session.setAttribute(
                        "FOLIO_COD_CATASTRAL",
                        local_JxContext.getValue("codCatastral") // "zonaRegistral/vereda/municipio/departamento/idDepartamento"
                ); // DT:string

            } //: load_Data_Folio

            /////////////////////
            // :: documento
            ///////////////////
            loadData_Folio_Documento:
            {

                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_TIPO",
                        local_JxContext.getValue("documento/tipoDocumento/nombre")
                ); // DT:string

                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_ID_TIPO",
                        local_JxContext.getValue("documento/tipoDocumento/idTipoDocumento")
                ); // DT:string

                session.setAttribute(
                        "TIPO_ENCABEZADO",
                        local_JxContext.getValue("documento/tipoDocumento/idTipoDocumento")
                ); // DT:string

                session.setAttribute(
                        "ID_TIPO_ENCABEZADO",
                        local_JxContext.getValue("documento/tipoDocumento/idTipoDocumento")
                ); // DT:string

                session.setAttribute(
                        "ID_ENCABEZADO",
                        local_JxContext.getValue("documento/numero")
                ); // DT:string

                session.setAttribute(
                        "CALENDAR",
                        local_JxContext.getValue("format:toString( documento/fecha, 'dd/MM/yyyy' ) ")
                ); // DT:string

                // -----------
                session.setAttribute(
                        "TIPO_OFICINA_I_N",
                        local_JxContext.getValue("format:decodeCondition1( documento/oficinaInternacional, $nullValue, 'TIPO_OFICINA_INTERNACIONAL', 'TIPO_OFICINA_NACIONAL' ) ")
                ); // DT:string

                //session.setAttribute(
                //		"TIPO_OFICINA_INTERNACIONAL" // TIPO_OFICINA_I_N
                //   	, local_JxContext.getValue( "format:decodeCondition1( documento/oficinaInternacional, $nullValue, 'TIPO_OFICINA_NACIONAL', 'TIPO_OFICINA_INTERNACIONAL' ) " )
                //); // DT:string
                session.setAttribute(
                        "TIPO_OFICINA_INTERNACIONAL",
                        local_JxContext.getValue("documento/oficinaInternacional")
                ); // DT:string

                //session.setAttribute(
                //		"ANOTACION_OFICINA_DOCUMENTO_TIPO" // TIPO_OFICINA_I_N
                //    	, local_JxContext.getValue( "format:decodeCondition1( documento/oficinaInternacional, $nullValue, 'TIPO_OFICINA_NACIONAL', 'TIPO_OFICINA_INTERNACIONAL' ) " )
                //); // DT:string
                session.setAttribute(
                        "TIPO_OFICINA_INTERNACIONAL" // TIPO_OFICINA_I_N
                        ,
                         local_JxContext.getValue("documento/oficinaInternacional")
                ); // DT:string

                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO",
                        local_JxContext.getValue("documento/oficinaOrigen/vereda/idDepartamento")
                ); // DT:string

                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO",
                        local_JxContext.getValue("documento/oficinaOrigen/vereda/municipio/departamento/nombre")
                ); // DT:string

                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC",
                        local_JxContext.getValue("documento/oficinaOrigen/vereda/idMunicipio")
                ); // DT:string

                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC",
                        local_JxContext.getValue("documento/oficinaOrigen/vereda/municipio/nombre")
                ); // DT:string

                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA",
                        local_JxContext.getValue("documento/oficinaOrigen/vereda/idVereda")
                ); // DT:string

                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA",
                        local_JxContext.getValue("documento/oficinaOrigen/vereda/nombre")
                ); // DT:string

                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA",
                        local_JxContext.getValue("documento/oficinaOrigen/idOficinaOrigen")
                ); // DT:string

                /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION",
                        local_JxContext.getValue("documento/oficinaOrigen/version").toString()
                );
                session.setAttribute(
                        "ANOTACION_OFICINA_DOCUMENTO_NUM",
                        local_JxContext.getValue("documento/oficinaOrigen/numero")
                ); // DT:string

            } // :loadData_Folio_Documento

            loadData_Turno_Solicitud:
            {

                // TODO:solicitud[@id]/datosAntiguoSistema
            } // :loadData_Turno

            // TODO: antiguosistema, antiguosistema.documento
            // TODO: salvedades
            loadData_Folio_Salvedades:
            {

                //
                List local_Folio_Object;
                local_Folio_Object = (List) local_JxContext.getValue("salvedades");

                session.setAttribute(
                        "LISTA_SALVEDADES_FOLIO",
                        local_Folio_Object
                ); // DT:string

            } // :

//          TODO: direcciones
            loadData_Folio_Direcciones:
            {

                // comment:
                // no se cargan: se presume que el helper de Direcciones
                // los carga cuando se le dice dirHelper.setFolioSesion(WebKeys.FOLIO);
                //
                //List local_Folio_Object;
                //local_Folio_Object = (List)local_JxContext.getValue( "direcciones" );
                //    session.setAttribute(
                //                "LISTA_DIRECCION_ANOTACION"
                //                , local_Folio_Object
                //    ); // DT:string
            } // :loadData_Folio_Direcciones


            /*

          if (request.getParameter(CFolio.FOLIO_EJE1) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_EJE1, request.getParameter(CFolio.FOLIO_EJE1));
          }

          if (request.getParameter(CFolio.FOLIO_EJE2) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_EJE2, request.getParameter(CFolio.FOLIO_EJE2));
          }

          if (request.getParameter(CFolio.FOLIO_VALOR1) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_VALOR1, request.getParameter(CFolio.FOLIO_VALOR1));
          }

          if (request.getParameter(CFolio.FOLIO_VALOR2) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_VALOR2, request.getParameter(CFolio.FOLIO_VALOR2));
          }

          if (request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR, request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA));
          }

          if (request.getParameter(CFolio.FOLIO_TIPO_DOCUMENTO) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_TIPO_DOCUMENTO, request.getParameter(CFolio.FOLIO_TIPO_DOCUMENTO));
          }

          if (request.getParameter(CFolio.FOLIO_NUM_DOCUMENTO) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_NUM_DOCUMENTO, request.getParameter(CFolio.FOLIO_NUM_DOCUMENTO));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
                  request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA));
          }

          if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
                  request.getSession().setAttribute("tipo_oficina", request.getParameter("tipo_oficina"));
                  request.getSession().setAttribute("folio_tipo_nom_oficina", request.getParameter("tipo_nom_oficina"));
                  request.getSession().setAttribute("folio_numero_oficina", request.getParameter("numero_oficina"));
                  request.getSession().setAttribute("folio_id_oficina", request.getParameter("id_oficina"));
          }

          request.getSession().setAttribute("FOLIO_ANTIGUO_SISTEMA", request.getParameter("FOLIO_ANTIGUO_SISTEMA"));
             */
            // preservarInfoBasicaDocumento(request);
            // preservarInfoAntiguoSistema(request);
        } // :process_Load_Data

        // eliminarAtributosSesion( request );
        // session.setAttribute( WebKeys.FOLIO        , param_FolioEditado );
        // session.setAttribute( WebKeys.FOLIO_EDITADO, param_FolioEditado );
        session.setAttribute(WebKeys.FOLIO, param_FolioEditado);
        session.setAttribute(WebKeys.FOLIO_EDITADO, param_FolioEditado);

        // additional // PK
        doProcess_StepX_SaveState_FolioId(request);

    } // end-method: doEndProcess_AdminCrearFolio_SaveInfo_StepX_LoadState

//  guarda info folio
    private void
            doEndProcess_AdminCrearFolio_SaveInfo_Step0Find(HttpServletRequest request, EvnResp_AdminCrearFolioSaveInfo respuesta) {

        doEndProcess_AdminCrearFolio_SaveInfo_StepX_LoadState(request, respuesta);

    } // :process_LoadDataFolio

    //
    private void
            doEndProcess_AdminCrearFolio_SaveInfo_Step2Back(HttpServletRequest request, EvnResp_AdminCrearFolioSaveInfo respuesta) {

        doEndProcess_AdminCrearFolio_SaveInfo_StepX_LoadState(request, respuesta);

    } // end-method:doEndProcess_AdminCrearFolio_SaveInfo_Step2Back

    private static org.apache.commons.jxpath.AbstractFactory folioAbstractFactory = new FolioAbstractFactory();

    org.apache.commons.jxpath.AbstractFactory
            getFolioAbstractFactory() {

        return folioAbstractFactory;

    } // end-method:

    private void
            doProcess_StepX_SaveState_FolioId(HttpServletRequest request) {

        HttpSession session;
        session = request.getSession();

        Folio param_FolioEditado;
        param_FolioEditado = (Folio) session.getAttribute(WebKeys.FOLIO_EDITADO);

        save_PageItemState_Simple(session, PARAM__LOCAL_FOLIO_ID_MATRICULA, param_FolioEditado.getIdMatricula());
        save_PageItemState_Simple(session, PARAM__LOCAL_FOLIO_ID_ZONAREGISTRAL, param_FolioEditado.getZonaRegistral().getIdZonaRegistral());

    } // end-method:

    private void
            doProcess_StepX_RemoveState_FolioId(HttpServletRequest request) {

        HttpSession session;
        session = request.getSession();

        String[] itemIds;

        itemIds = new String[]{
            PARAM__LOCAL_FOLIO_ID_MATRICULA,
            PARAM__LOCAL_FOLIO_ID_ZONAREGISTRAL
        };

        delete_PageItemsState(itemIds, request, session);

    } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step1_RemoveState

    private void save_PageItemState_Simple(HttpSession session, String key, Object value) {

        session.setAttribute(key, value);

    } // save_PageItemState

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

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta eventoRespuesta) {
        if (eventoRespuesta != null) {
            if (eventoRespuesta instanceof EvnResp_AdminCrearFolioSaveInfo) {
                EvnResp_AdminCrearFolioSaveInfo respuesta = (EvnResp_AdminCrearFolioSaveInfo) eventoRespuesta;

                String local_TipoEvento;
                local_TipoEvento = (respuesta.getTipoEvento());

                if (EvnResp_AdminCrearFolioSaveInfo.ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENTRESP_OK.equals(local_TipoEvento)) {

                    doEndProcess_AdminCrearFolio_SaveInfo_Step0Find(request, respuesta);

                } // if
                if (EvnResp_AdminCrearFolioSaveInfo.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENTRESP_OK.equals(local_TipoEvento)) {

                    doEndProcess_AdminCrearFolio_SaveInfo_Step2Back(request, respuesta);

                } // if

            } else if (eventoRespuesta instanceof EvnRespCrearFolio) {
                EvnRespCrearFolio respuesta = (EvnRespCrearFolio) eventoRespuesta;

                String local_TipoEvento;
                local_TipoEvento = (respuesta.getTipoEvento());

                if (EvnRespCrearFolio.VALIDAR_MATRICULA_CREACION_FOLIO_OK.equals(local_TipoEvento)) {

                    doEndProcess_AdminCrearFolio_SaveInfo_Step1(request, respuesta);

                } else if (EvnRespCrearFolio.VALIDAR_MATRICULA_CREACION_FOLIO_UPDATING_OK.equals(local_TipoEvento)) {

                    doEndProcess_AdminCrearFolio_SaveInfo_Step1(request, respuesta);

                } else if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.AGREGAR_ANOTACION_OK)) {
                    request.getSession().removeAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
                    request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, respuesta.getPayload());
                    Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, folio.getAnotaciones());
                    if (respuesta.getAnotacionesAgregadas() != null) {
                        List anotaciones = respuesta.getAnotacionesAgregadas();
                        if (anotaciones == null) {
                            anotaciones = new Vector();
                        }
                        anotaciones.add(respuesta.getAnotacion());
                        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);
                    }
                    if ((respuesta.getNextOrden() != null) && (!respuesta.getNextOrden().equals(""))) {
                        request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, respuesta.getNextOrden());
                    }

                    eliminarInfoBasicaAnotacion(request);
                    eliminarInfoBasicaVariosCiudadanos(request);
                    request.getSession().removeAttribute(WebKeys.HAY_REFRESCO);
                    request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                    request.getSession().removeAttribute("listanat");

                } else if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.CANCELAR_ANOTACIONES_OK)) {
                    eliminarInfoBasicaAnotacion(request);
                    request.getSession().removeAttribute("listanat");
                    request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, respuesta.getPayload());
                    Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, folio.getAnotaciones());
                    request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                    if (folio.getAnotaciones().size() + 1 != new Integer((String) request.getSession().getAttribute(CFolio.NEXT_ORDEN_ANOTACION)).intValue()) {
                        String norder = Integer.toString(folio.getAnotaciones().size() + 1);
                        request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, norder);
                    }
                } else if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.CREACION_FOLIO)) {
                    eliminarAtributosSesion(request);

                    request.getSession().removeAttribute(WebKeys.FOLIO_EDITADO);
                    request.getSession().removeAttribute(WebKeys.FOLIO);
                    request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR_APERTURA);
                    request.getSession().removeAttribute(CSolicitudRegistro.NUMERO_RADICACION);
                    request.getSession().removeAttribute(CFolio.FOLIO_ID_ESTADO);

                    request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, respuesta.getPayload());
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getPayload());

                } else if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.AGREGAR_SEGREGACION_ENGLOBE_OK)) {
                    //eliminarAtributosSesion(request);

                    request.getSession().removeAttribute(WebKeys.FOLIO_EDITADO);
                    request.getSession().removeAttribute(WebKeys.FOLIO);
                    request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR_APERTURA);
                    request.getSession().removeAttribute(CSolicitudRegistro.NUMERO_RADICACION);
                    request.getSession().removeAttribute(CFolio.FOLIO_ID_ESTADO);

                    request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, respuesta.getPayload());
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getPayload());

                    //doEndProcess_AdminCrearFolio_SaveInfo_Step1(request, respuesta);
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE, respuesta.getFoliosPadre());
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO, respuesta.getFoliosHijo());

                } else if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.AGREGAR_DERIVACION_OK)) {
                    //eliminarAtributosSesion(request);

                    request.getSession().removeAttribute(WebKeys.FOLIO_EDITADO);
                    request.getSession().removeAttribute(WebKeys.FOLIO);
                    request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR_APERTURA);
                    request.getSession().removeAttribute(CSolicitudRegistro.NUMERO_RADICACION);
                    request.getSession().removeAttribute(CFolio.FOLIO_ID_ESTADO);

                    request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, respuesta.getPayload());
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getPayload());

                    //doEndProcess_AdminCrearFolio_SaveInfo_Step1(request, respuesta);
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE, respuesta.getFoliosPadre());
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO, respuesta.getFoliosHijo());

                } else if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.ELIMINAR_DERIVACION_OK)) {
                    //eliminarAtributosSesion(request);

                    request.getSession().removeAttribute(WebKeys.FOLIO_EDITADO);
                    request.getSession().removeAttribute(WebKeys.FOLIO);
                    request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR_APERTURA);
                    request.getSession().removeAttribute(CSolicitudRegistro.NUMERO_RADICACION);
                    request.getSession().removeAttribute(CFolio.FOLIO_ID_ESTADO);

                    request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, respuesta.getPayload());
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getPayload());

                    //doEndProcess_AdminCrearFolio_SaveInfo_Step1(request, respuesta);
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE, respuesta.getFoliosPadre());
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO, respuesta.getFoliosHijo());

                } else if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.AGREGAR_ENGLOBE_OK)) {
                    //eliminarAtributosSesion(request);

                    request.getSession().removeAttribute(WebKeys.FOLIO_EDITADO);
                    request.getSession().removeAttribute(WebKeys.FOLIO);
                    request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR_APERTURA);
                    request.getSession().removeAttribute(CSolicitudRegistro.NUMERO_RADICACION);
                    request.getSession().removeAttribute(CFolio.FOLIO_ID_ESTADO);

                    request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, respuesta.getPayload());
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getPayload());

                    //doEndProcess_AdminCrearFolio_SaveInfo_Step1(request, respuesta);
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE, respuesta.getFoliosPadre());
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO, respuesta.getFoliosHijo());

                } else if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.ELIMINAR_ENGLOBE_OK)) {
                    //eliminarAtributosSesion(request);

                    request.getSession().removeAttribute(WebKeys.FOLIO_EDITADO);
                    request.getSession().removeAttribute(WebKeys.FOLIO);
                    request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR_APERTURA);
                    request.getSession().removeAttribute(CSolicitudRegistro.NUMERO_RADICACION);
                    request.getSession().removeAttribute(CFolio.FOLIO_ID_ESTADO);

                    request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, respuesta.getPayload());
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getPayload());

                    //doEndProcess_AdminCrearFolio_SaveInfo_Step1(request, respuesta);
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE, respuesta.getFoliosPadre());
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO, respuesta.getFoliosHijo());

                } else if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.VOLVER_AGREGAR_CIUDADANOS)) {
                    request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, respuesta.getListaCompletaCiudadanos());
                }

            } else if (eventoRespuesta instanceof EvnRespConsultaFolio) {
                EvnRespConsultaFolio respuesta = (EvnRespConsultaFolio) eventoRespuesta;
                if (respuesta.getFolio() != null) {
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                }
            } else if (eventoRespuesta instanceof EvnRespCiudadano) {
                EvnRespCiudadano evn = (EvnRespCiudadano) eventoRespuesta;
                String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
                Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

                if (evn.isCiudadanoEncontrado() && circulo != null && circulo.getIdCirculo().equals(evn.getCiudadano().getIdCirculo())) {
                    request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + ver, evn.getCiudadano().getApellido1());
                    request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + ver, evn.getCiudadano().getApellido2());
                    request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + ver, evn.getCiudadano().getNombre());
                    request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA + ver, evn.getCiudadano().getSexo());
                    if (evn.isMostrarCiudadano()) {
                        request.getSession().setAttribute(CFolio.CIUDADANO_EDITABLE + ver, new Boolean(true));
                    } else {
                        request.getSession().removeAttribute(CFolio.CIUDADANO_EDITABLE + ver);
                    }
                } else {
                    request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + ver);
                    request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + ver);
                    request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + ver);
                    request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA + ver);
                    request.getSession().removeAttribute(CFolio.CIUDADANO_EDITABLE + ver);
                }
            } else if (eventoRespuesta instanceof EvnRespCertificado) {
                EvnRespCertificado respuesta = (EvnRespCertificado) eventoRespuesta;
                if (respuesta.getTipoEvento().equals(EvnRespCertificado.CONSULTA_FOLIO_COMPLEMENTACION)) {
                    if (respuesta.getFolio() != null && respuesta.getFolio().getComplementacion() != null) {
                        request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getComplementacion());
                        request.getSession().setAttribute(CFolio.FOLIO_ID_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getIdComplementacion());
                    }
                }
            }
        }
    }

    private Evento eliminarAtributosSesion(HttpServletRequest request) {
        eliminarInfoBasicaFolio(request);
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoDireccion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        eliminarInfoBasicaDocumento(request);
        eliminarInfoAntiguoSistema(request);

        HttpSession session;
        session = request.getSession();

        session.removeAttribute(CFolio.NEXT_ORDEN_ANOTACION);
        session.removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        session.removeAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        // added
        session.removeAttribute(WebKeys.LISTA_SALVEDADES_FOLIO);
        session.removeAttribute(WebKeys.LISTA_SALVEDADES_ANOTACION);
        session.removeAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES);

        // added : eliminar informacion del id
        doProcess_StepX_RemoveState_FolioId(request);

        return null;
    }

    private void eliminarInfoAntiguoSistema(HttpServletRequest request) {

        // refactored
        HttpSession session = request.getSession();

        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
        session.removeAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS);
        session.removeAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
        session.removeAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
        session.removeAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS);
        session.removeAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS);
        session.removeAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS);
        session.removeAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
        session.removeAttribute(CDatosAntiguoSistema.TOMO_ANO_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
        session.removeAttribute(CDatosAntiguoSistema.COMENTARIO_AS);

        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS);

        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);

        session.removeAttribute(VER_ANTIGUO_SISTEMA);
    }

    /**
     * @param request
     * @return
     */
    private Evento editarAnotacion(HttpServletRequest request) {
        /*obtencion de DatosRespuestaPaginador para saber si hay un paginador activo y sacar los datos de este*/
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        String ver;
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
            String nombreNumAnotacionTemporal = (String) request.getSession().getAttribute(WebKeys.NOMBRE_NUM_ANOTACION_TEMPORAL);
            ver = request.getParameter(nombreNumAnotacionTemporal);

            ArrayList list = new ArrayList();
            if (RPag != null && RPag.getAnotacionesActual() != null) {
                Iterator it = RPag.getAnotacionesActual().iterator();

                int i = 0;
                while (it.hasNext()) {
                    Object obj = (Object) it.next();
                    list.add(obj);
                }
            }
            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO, list);
        } else {
            ver = request.getParameter(AWCrearFolioOficio.NUM_A_TEMPORAL);
        }
        if (ver == null) {
            ver = request.getParameter(AWCrearFolioOficio.NUM_A_TEMPORAL);
        }
        request.getSession().setAttribute(AWCrearFolioOficio.NUM_ANOTACION_TEMPORAL, ver);

        Log.getInstance().debug(AWCrearFolio.class, "Anotacion a Editar " + ver);
        preservarInfoBasicaAnotacionEdicion(request);

        //Se debe consultar el documento temporal
        return null;
    }

    /**
     * eliminarAnotacionTemporal
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento eliminarAnotacionTemporal(HttpServletRequest request) {

        //obtencion e inicializacion de datos.
        String num = request.getParameter(AWCrearFolioOficio.NUM_A_TEMPORAL);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        Anotacion a = new Anotacion();
        List anotacionesTemporales = new Vector();
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
            String nombreNumAnotacionTemporal = (String) request.getSession().getAttribute(WebKeys.NOMBRE_NUM_ANOTACION_TEMPORAL);
            num = request.getParameter(nombreNumAnotacionTemporal);

            ArrayList list = new ArrayList();
            if (RPag != null && RPag.getAnotacionesActual() != null) {
                Iterator it = RPag.getAnotacionesActual().iterator();

                int i = 0;
                while (it.hasNext()) {
                    Object obj = (Object) it.next();
                    list.add(obj);
                }
            }
            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO, list);

            anotacionesTemporales = RPag.getAnotacionesActual();
        } else {
            anotacionesTemporales = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
            num = request.getParameter(AWCrearFolioOficio.NUM_A_TEMPORAL);

            if (anotacionesTemporales == null) {
                anotacionesTemporales = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
            }
        }

        if (anotacionesTemporales == null) {
            anotacionesTemporales = new Vector();
        }

        request.getSession().setAttribute(AWCrearFolioOficio.NUM_ANOTACION_TEMPORAL, num);

        int n = Integer.parseInt(num);
        n--;
        Log.getInstance().debug(AWCrearFolio.class, "Editando anotacion # --> " + n);
        Iterator ianot = anotacionesTemporales.iterator();
        int cont = 0;
        while (ianot.hasNext()) {
            Anotacion temp = (Anotacion) ianot.next();
            if (cont == n) {
                a = temp;
                break;
            }
            cont++;
        }

        a.setToDelete(true);

        //codigo de grabar en temporal
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        request.getSession().setAttribute(WebKeys.FOLIO, folio);
        // refresh page
        //request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
        if (llaves != null) {
            llaves.removeLLaves(request);
        }
        request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.ELIMINAR_ANOTACION);
        evnCrearFolio.setFolio(folio);
        evnCrearFolio.setUsuario(usuarioAuriga);
        evnCrearFolio.setUsuarioSir(usuarioNeg);
        evnCrearFolio.setAnotacion(a);
        return evnCrearFolio;

    }

    /**
     * @param request
     * @return
     */
    private Evento crearCancelacion(HttpServletRequest request) {

        this.eliminarInfoBasicaAnotacion(request);
        Folio f = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        List anotacionesFolio = f.getAnotaciones();
        /*obtencion de DatosRespuestaPaginador para saber si hay un paginador activo y sacar los datos de este*/
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);

            ArrayList list = new ArrayList();

            if (RPag != null) {
                RPag.setAnotacionesActual(anotacionesFolio);
            }

            if (RPag != null && RPag.getAnotacionesActual() != null) {
                RPag.setAnotacionesActual(anotacionesFolio);
                Iterator it = RPag.getAnotacionesActual().iterator();

                int i = 0;
                while (it.hasNext()) {
                    Object obj = (Object) it.next();
                    list.add(obj);
                }
            }
            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, list);
        }

        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, anotacionesFolio);
        request.getSession().setAttribute(WebKeys.CHECK_ANOTACION_TEMPORAL_MOSTRAR_FOLIO_HELPER, "true");

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.CREAR_CANCELACION);
        evnCrearFolio.setFolio(f);
        return evnCrearFolio;

        //return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento guardarAnotacionesCancelacion(HttpServletRequest request) throws ValidacionAnotacionesCancelacionException {

        String[] idsAnotaciones = request.getParameterValues("ESCOGER_ANOTACION_CANCELACION");
        ValidacionAnotacionesCancelacionException exception = new ValidacionAnotacionesCancelacionException();
        if (idsAnotaciones == null) {
            exception.addError("Debe seleccionar por lo menos una anotación para cancelar");
            throw exception;
        }
        request.getSession().setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, idsAnotaciones);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento refrescarCancelacion(HttpServletRequest request) {
        //eliminarInfoBasicaAnotacion(request);
        String ver = request.getParameter("SECUENCIA");
        request.getSession().setAttribute("SECUENCIA", ver);
        request.getSession().setAttribute("CAMBIO", "SI");
        preservarInfoAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoCancelacion(request);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento preservarInfoAnotacion(HttpServletRequest request) {
        preservarInfoBasicaAnotacion(request);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento preservarInfoCancelacion(HttpServletRequest request) {
        if (request.getParameter(AWCalificacion.POSICIONANOTACION) != null) {
            request.getSession().setAttribute(AWCalificacion.POSICIONANOTACION, request.getParameter(AWCalificacion.POSICIONANOTACION));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION) != null) {
            request.getSession().setAttribute(AWModificarFolio.ANOTACION_FECHA_RADICACION, request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION) != null) {
            request.getSession().setAttribute(AWModificarFolio.ANOTACION_NUM_RADICACION, request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }
        if (request.getParameter(CFolio.ANOTACION_ID_ANOTACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_ID_ANOTACION, request.getParameter(CFolio.ANOTACION_ID_ANOTACION));
        }

        if (request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO, request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_RADICACION, request.getParameter(CFolio.ANOTACION_FECHA_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO, request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO, request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute("tipo_oficina", request.getParameter("tipo_oficina"));
            request.getSession().setAttribute("tipo_nom_oficina", request.getParameter("tipo_nom_oficina"));
            request.getSession().setAttribute("numero_oficina", request.getParameter("numero_oficina"));
            request.getSession().setAttribute("id_oficina", request.getParameter("id_oficina"));
            /*
                             *  @author Carlos Torres
                             *  @chage   se agrega validacion de version diferente
                             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            request.getSession().setAttribute("version", request.getParameter("version"));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }

        if (request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION) != null) {
            request.getSession().setAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION, request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION));
        }
        if (request.getParameter("ESCOGER_ANOTACION_CANCELACION") != null) {
            request.getSession().setAttribute("ESCOGER_ANOTACION_CANCELACION", request.getParameter("ESCOGER_ANOTACION_CANCELACION"));
        }

        return null;
    }

    /**
     * @param request
     * @param accion1
     * @return
     * @throws AccionWebException
     */
    private Evento agregarVariosCiudadanosCancelacion(HttpServletRequest request, String accion1) throws AccionWebException {

        ValidacionParametrosException exception;

        if (accion1.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)) {
            exception = new ValidacionParametrosCiudadanoSegregacionCalificacionException();
        } else if (accion1.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS)) {
            exception = new ValidacionParametrosCiudadanoCalificacionException();
        } else {
            exception = new ValidacionParametrosException();
        }

        return agregarVariosCiudadanosCancelacion(request, exception);

    }

    /**
     * @param request
     * @param exception
     * @return
     * @throws AccionWebException
     *
     *
     */
    private Evento agregarVariosCiudadanosCancelacion(HttpServletRequest request,
            ValidacionParametrosException exception) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);//varios
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        List ciudadanosFinales = new Vector(ciudadanos);

        int numCiudadanosTabla = this.numeroRegistrosTablaAgregarCiudadanos(request);
        for (int i = 0; i < numCiudadanosTabla; i++) {

            if (agregoPersona(request, i)) {
                int b = i + 1;
                boolean datosBien = true;
                String tipoPersona = request.getParameter(CFolio.ANOTACION_TIPO_PERSONA + i);

                if ((tipoPersona == null) || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de persona para el ciudadano " + b + " en la anotacion");
                    datosBien = false;
                }

                String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + i);

                if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de identificación para la persona " + b + " en la anotacion");
                    datosBien = false;
                }

                String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA + i);

                if (!tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                    if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escojer un tipo de sexo para la persona en la anotacion");
                        datosBien = false;
                    }
                }

                String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + i);
                String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
                String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
                String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA + i);
                if (tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {

                    if (tipoPersona.equals(CCiudadano.TIPO_PERSONA_NATURAL)) {
                        if (apellido1 == null || apellido1.trim().equals("")) {
                            exception.addError("Primer apellido invalido para la persona " + b);
                            datosBien = false;
                        }
                    } else {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razon social invalida para el ciudadano " + b);
                            datosBien = false;
                        }
                    }

                } else {
                    if ((numId == null) || numId.trim().equals("")) {
                        exception.addError("Documento Inválido para la persona " + b);
                        datosBien = false;
                    }
                    double valorId = 0.0d;
                    if (numId == null) {
                        exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                        datosBien = false;
                    } else {
                        if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                            try {
                                valorId = Double.parseDouble(numId);
                                if (valorId <= 0) {
                                    exception.addError("El valor del documento de la persona " + b + " no puede ser negativo o cero");
                                    datosBien = false;
                                }
                            } catch (NumberFormatException e) {
                                exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                                datosBien = false;
                            }
                        }
                    }
                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razón social inválida del campo " + b);
                            datosBien = false;
                        }
                    } else {
                        if ((nombres == null) || nombres.equals("")) {
                            exception.addError("Debe ingresar el nombre de la persona " + b + " en la anotación");
                            datosBien = false;
                        }

                        if ((apellido1 == null) || apellido1.equals("")) {
                            exception.addError("Debe ingresar el primer apellido de la persona " + b + " en la anotación");
                            datosBien = false;
                        }
                    }
                }

                String tipoIntervencion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);

                if ((tipoIntervencion == null) || tipoIntervencion.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe ingresar un tipo de intervención para la persona " + b + " en la anotación");
                    datosBien = false;
                }

                AnotacionCiudadano c;
                Iterator ic = ciudadanosFinales.iterator();
                while (ic.hasNext()) {
                    c = (AnotacionCiudadano) ic.next();
                    if ((tipoIntervencion.equals(c.getRolPersona())) && (numId.equals(c.getCiudadano().getDocumento()))
                            && (tipoId.equals(c.getCiudadano().getTipoDoc())) && (!tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA))) {
                        exception.addError("La persona no puede tener dos veces el mismo rol");
                        datosBien = false;
                    }
                }

                String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
                if ((participacion != null) && !participacion.equals("")) {
                    if (participacion.length() > 50) {
                        exception.addError("El campo participación de una persona no puede tener mas de 50 caracteres");
                        datosBien = false;
                    }
                }

                if (datosBien) {
                    AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
                    Ciudadano ciudadano = new Ciudadano();

                    if (apellido1 != null) {
                        ciudadano.setApellido1(apellido1);
                    }

                    if (apellido2 != null) {
                        ciudadano.setApellido2(apellido2);
                    }

                    ciudadano.setNombre(nombres);
                    ciudadano.setSexo(sexo);

                    if (numId != null) {
                        ciudadano.setDocumento(numId);
                    }

                    //Se setea el circulo del ciudadano
                    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                    ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

                    ciudadano.setTipoDoc(tipoId);
                    ciudadano.setTipoPersona(tipoPersona);
                    anotacionCiudadano.setCiudadano(ciudadano);
                    anotacionCiudadano.setRolPersona(tipoIntervencion);
                    anotacionCiudadano.setParticipacion(participacion.toUpperCase());

                    String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);

                    if (marcaPropietario == null || marcaPropietario.equals("")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaPropietario.equals("X")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaPropietario.equals("I")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }

                    ciudadanosFinales.add(anotacionCiudadano);
                }

            }
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        // eliminarInfoBasicaVariosCiudadanos(request);
        //if (!existeEnListaCiudadanos(anotacionCiudadano, ciudadanos)) {
        //List listaCompletaCiudadanos = new Vector();
        //listaCompletaCiudadanos.addAll(ciudadanos);
        //request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, listaCompletaCiudadanos);

        /*} else {
			exception.addError("El ciudadano que esta intentado ingresar ya se encuentra registrado en la anotacion");
			throw exception;
		}*/
 /*		String vista=(String)request.getSession().getAttribute(WebKeys.ULTIMA_VISTA_TEMPORAL);
		if (vista!=null){
			request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL,vista);
		}
         */
        //return null;
        eliminarInfoBasicaVariosCiudadanos(request);

        List listaCompletaCiudadanos = new Vector();

        listaCompletaCiudadanos.addAll(ciudadanosFinales);

        //request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, listaCompletaCiudadanos);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCrearFolio evento = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosFinales);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        return evento;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento cancelarAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfoCancelacion(request);
        preservarInfoBasicaAnotacion(request);
        ValidacionParametrosCancelacionException exception = new ValidacionParametrosCancelacionException();

        //List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA);
        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        // String[] idsCanc = (String[])request.getSession().getAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
        /*if (idsCanc==null){
			exception.addError("Debe seleccionar por lo menos anotación a cancelar");
		}*/

        // permitir mas de una anotacion a cancelar -----------
        // Bug 005165
        // ids anotaciones para cancelar
        HttpSession session = request.getSession();
        String[] local_ListAnotacionIdsToBeCanceled;

        local_ListAnotacionIdsToBeCanceled = (String[]) session.getAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);

        if ((null == local_ListAnotacionIdsToBeCanceled)
                || (local_ListAnotacionIdsToBeCanceled.length == 0)) {

            exception.addError("Debe escoger por lo menos una anotación a cancelar.");
            local_ListAnotacionIdsToBeCanceled = new String[0];

        }

        /*if(cancelada==null){
                     exception.addError("Debe escoger una anotación a cancelar.");
		}*/
        Documento documento = crearDocumento(request, exception, true);

        //folio.setDocumento(documento);
        String valFechaRadicacion = request.getParameter(CFolio.ANOTACION_FECHA_RADICACION);

        Calendar fechaRadicacion = Calendar.getInstance();
        if (valFechaRadicacion != null) {
            fechaRadicacion = darFecha(valFechaRadicacion);
            if (fechaRadicacion == null) {
                exception.addError("La fecha de la radicación en la anotación es inválida");
            }
        }

        String anotaFechaRadicacion = request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION);

        Calendar fechaAnotaRadicacion = Calendar.getInstance();
        if (anotaFechaRadicacion != null) {
            fechaAnotaRadicacion = darFecha(anotaFechaRadicacion);
            if (fechaAnotaRadicacion == null) {
                exception.addError("La fecha de la radicación en la cancelación es inválida");
            }
        }

        String anotaNumRadicacion = request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION);

        if (anotaNumRadicacion == null || anotaNumRadicacion.trim().equals("")) {
            exception.addError("El número de radicación de la cancelación es inválido");
        }

        String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        if (numEspecificacion != null) {
            numEspecificacion = numEspecificacion.replaceAll(",", "");
        }
        double valorEspecificacion = 0.0d;
        if (numEspecificacion == null || numEspecificacion.equals("")) {
            numEspecificacion = "0";
        }

        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Declara variable natJuridica
         */
        NaturalezaJuridica natJuridica = new NaturalezaJuridica();

        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);

        if (idNaturaleza == null || idNaturaleza.length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza juridica para la especificación en la anotación");
        } else {

            //validacion de la idNaturalezaJuridica
            boolean esta = false;
            List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");
            Iterator ig = grupoNaturalezas.iterator();
            while (ig.hasNext() && !esta) {
                GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
                if (group.getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.ID_CANCELACION)) {
                    List natus = group.getNaturalezaJuridicas();
                    Iterator id = natus.iterator();
                    while (id.hasNext()) {
                        NaturalezaJuridica nat = (NaturalezaJuridica) id.next();
                        if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                            /**
                             * @author : Carlos Mario Torres Urina
                             * @casoMantis : 0012705.
                             * @actaReq : Acta - Requerimiento No
                             * 056_453_Modificiación_de_Naturaleza_Jurídica
                             * @change : Declara variable natJuridica
                             */
                            natJuridica = nat;
                            esta = true;
                            break;
                        }
                    }
                }
            }
            if (!esta) {
                exception.addError("Debe colocar un codigo de naturaleza juridica válido");
            }

        }

        String comentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Anotacion anotacion = new Anotacion();
        int pos = anotaciones.size() + 1;
        anotacion.setOrden(String.valueOf(pos));

        int torder = 0;
        torder = pos;

        torder++;
        String norder = Integer.toString(torder);
        request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, norder);

        //anotacion.setFechaRadicacion(fechaRadicacion.getTime());
        /*if (turno != null) {
			anotacion.setNumRadicacion(turno.getIdWorkflow());
			anotacion.setIdWorkflow(turno.getIdWorkflow());
			anotacion.setFechaRadicacion(turno.getFechaInicio());
		}*/
        //Se valida si se ingresa un nuevo documento para el caso de correcciones o si se ingresa el de la solicitud(registro)
        /*if(String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CORRECCIONES)){
			anotacion.setDocumento(documento);
		}else{
			anotacion.setDocumento(((SolicitudRegistro)turno.getSolicitud()).getDocumento());
		}*/
        anotacion.setFechaRadicacion(fechaAnotaRadicacion.getTime());
        anotacion.setNumRadicacion(anotaNumRadicacion);
        anotacion.setDocumento(documento);

        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Se asigna el valor para la naturaleza juridica
         */
        NaturalezaJuridica naturalezaJuridica = natJuridica;
        if ((numEspecificacion != null) && !numEspecificacion.equals("")) {
            try {
                valorEspecificacion = Double.parseDouble(numEspecificacion);
                anotacion.setValor(valorEspecificacion);
            } catch (NumberFormatException e) {
                ValidacionParametrosCancelacionException ex = new ValidacionParametrosCancelacionException();
                ex.addError("El valor de la naturaleza es invalido.");
                throw ex;
            }
        }

        naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
        if (nomNatJuridica != null) {
            naturalezaJuridica.setNombre(nomNatJuridica);
            anotacion.setEspecificacion(nomNatJuridica);
        }

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setComentario(comentario);

        //segmento de ingresar ciudadanos
        if (ciudadanos != null) {
            Iterator itPersonas = ciudadanos.iterator();
            while (itPersonas.hasNext()) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                ciudadano.setAnotacion(anotacion);
                //ciudadano.setIdMatricula("idMatricula");
                anotacion.addAnotacionesCiudadano(ciudadano);
            }
        }
        //fin : segmento de ingresar ciudadanos

        // --------------------------------------------------
        // cancelaciones
        //List< String >
        List local_ListAnotacionObjsToBeCanceled;
        local_ListAnotacionObjsToBeCanceled = new ArrayList();

        // @see AWCalificacion.cancelarAnotacion
        // TODO: revisar si realmente esta el set de anotaciones que se
        // va a cancelar
        //: Esta opcion no se puede hacer en este momento,
        // pues no todas las anotaciones estan en sesion
        //  Iterator local_ListOfAnotacionesIterator;
        //  local_ListOfAnotacionesIterator = anotaciones.iterator();
        //  Anotacion local_AnotacionItem;
        //  for( ;local_ListOfAnotacionesIterator.hasNext(); ) {
        //     local_AnotacionItem = (Anotacion)local_ListOfAnotacionesIterator.next();
        //     if( ( null == local_AnotacionItem )
        //         || ( null == local_AnotacionItem.getIdAnotacion() ) ) {
        //        continue;
        //     }
        //     for( int i=0; i < local_ListAnotacionIdsToBeCanceled.length; i++ ) {
        //        if( null == local_ListAnotacionIdsToBeCanceled[i] ) {
        //           continue;
        //        }
        //        if( local_AnotacionItem.getIdAnotacion().equals( local_ListAnotacionIdsToBeCanceled[i] ) ){
        //           local_ListAnotacionObjsToBeCanceled.add( local_AnotacionItem );
        //           break;
        //        }
        //     } // for
        //  } // for
        // copiar el conjunto de id's de anotaciones acancelar
        // a una lista, verificando que los datos seleccionados no
        // sean cadena vacia ni datos nulos
        for (int i = 0; i < local_ListAnotacionIdsToBeCanceled.length; i++) {
            if (null == local_ListAnotacionIdsToBeCanceled[i]) {
                continue;
            }
            if ("".equals(local_ListAnotacionIdsToBeCanceled[i])) {
                continue;
            }

            local_ListAnotacionObjsToBeCanceled.add(local_ListAnotacionIdsToBeCanceled[i]);
        } // for

        // permitir mas de una anotacion a cancelar -----------
        // Bug 0005165
        Iterator local_ListAnotacionObjsToBeCanceledIterator;
        local_ListAnotacionObjsToBeCanceledIterator = local_ListAnotacionObjsToBeCanceled.iterator();

        // iterate oover all anotaciones-to-be cancelled
        Cancelacion local_CancelacionItem;
        Anotacion local_AnotacionToBeCanceledItem;
        Anotacion local_AnotacionThis = anotacion;

        // fijar esta anotacion como canceladora
        TipoAnotacion local_AnotacionThisTipo = new TipoAnotacion();
        local_AnotacionThisTipo.setIdTipoAnotacion(CTipoAnotacion.CANCELACION);
        local_AnotacionThis.setTipoAnotacion(local_AnotacionThisTipo);

        // create Cancelacion objects
        for (; local_ListAnotacionObjsToBeCanceledIterator.hasNext();) {

            String strItem = (String) local_ListAnotacionObjsToBeCanceledIterator.next();
            local_CancelacionItem = buildCancelacion(local_AnotacionThis, strItem);
            local_AnotacionThis.addAnotacionesCancelacion(local_CancelacionItem);

        } // for

        //	if (anotaciones == null)
        //	{
        //		anotaciones = new Vector();
        //	}
        //	List canceladas = new ArrayList();
        //	Iterator itC=anotaciones.iterator();
        //	int c=anotaciones.size();
        //	while(itC.hasNext()){
        //		Anotacion an=(Anotacion)itC.next();
        //		for(int i = 0;i<idsCanc.length;i++){
        //			if (an.getIdAnotacion().equals(idsCanc[i])){
        //				canceladas.add(an);
        //				//break;
        //			}
        //		}
        //		Log.getInstance().debug(AWCrearFolio.class, c);
        //		c--;
        //	}
        //	Iterator itCanceladas = canceladas.iterator();
        //	while(itCanceladas.hasNext()){
        //		Cancelacion cancelacion = new Cancelacion();
        //		cancelacion.setCancelada((Anotacion)itCanceladas.next());
        //		cancelacion.setCanceladora(anotacion);
        //		anotacion.addAnotacionesCancelacion(cancelacion);
        //	}
        //-----------------------------------------------------
        //anotacion.setOrden(anotacion.getIdAnotacion());
        // TODO: verificar que pasa con las validaciones de negocio.
        // request.getSession().removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
        //TipoAnotacion tipo=new TipoAnotacion();
        //tipo.setIdTipoAnotacion(CTipoAnotacion.CANCELACION);
        //anotacion.setTipoAnotacion(tipo);
        // TODO: Solo enviar el folio, con la anotacion creada
        // para no tener que hacer este procedimiento en NEgocio
        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.CANCELAR_ANOTACIONES);
        evnCrearFolio.setFolio(folio);
        evnCrearFolio.setAnotacion(anotacion);
        evnCrearFolio.setUsuario(usuarioAuriga);
        evnCrearFolio.setUsuarioSir(usuarioNeg);

        return evnCrearFolio;
    }

    private Cancelacion
            buildCancelacion(Anotacion local_AnotacionCanceladora, String t0_ToBeCanceledElement) {

        Anotacion local_AnotacionCancelada;
        local_AnotacionCancelada = new Anotacion();
        local_AnotacionCancelada.setIdAnotacion(t0_ToBeCanceledElement);
        local_AnotacionCancelada.setIdMatricula(local_AnotacionCanceladora.getIdMatricula());

        Cancelacion local_Result;
        local_Result = new Cancelacion();
        local_Result.setCanceladora(local_AnotacionCanceladora);
        local_Result.setCancelada(local_AnotacionCancelada);

        return local_Result;

    } // end-method: buildCancelacion

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento eliminarCiudadanoCancelacion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }
        /**
         * Organizar los ciudadanos. Se debe tener en cuenta el orden ya que el
         * helper (VariosCiuddanosAnotacionHelper)los muestra de esta manera y
         * se necesita manejar el mismo contador para eliminar el que realmente
         * se esta indicando
         */

        Iterator itCiudadanos = ciudadanos.iterator();
        List losA = new ArrayList();
        List losDe = new ArrayList();
        while (itCiudadanos.hasNext()) {
            AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
            String rol = anotacionCiudadano.getRolPersona();
            if (rol.equals("DE")) {
                losDe.add(anotacionCiudadano);
            } else if (rol.equals("A")) {
                losA.add(anotacionCiudadano);
            }
        }
        losDe.addAll(losA);
        ciudadanos.clear();
        ciudadanos = losDe;

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(AWCalificacion.POSICION));
            ciudadanos.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            }
            throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
        }

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarCancelacion(HttpServletRequest request) {
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        request.getSession().removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
        request.getSession().removeAttribute("listanat");
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento agregarSegregacionEnglobe(HttpServletRequest request) {

        Folio f = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);

        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaFolio(request);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.CREAR_SEGREGACION_ENGLOBE);
        evnCrearFolio.setFolio(f);
        return evnCrearFolio;

        //return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento agregarDerivado(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosException();
        // Cargar el estado t0 de los objetos

        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == folio) {
            folio = new Folio();
        }
        // cargar id de matricula actual
        // String idMatricula = t0_folio.getIdMatricula();

        // cargar la lista de padres (no es necesario cargar la lista de hijos)
        // el fin de esta carga es ver si el item a agregar no se encuentra inscrito como padre
        java.util.List t0_folio_padres = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
        if (null == t0_folio_padres) {
            t0_folio_padres = new java.util.ArrayList();
        }

        // capturar los parametros de la forma:
        String param_Item_Folio_IdMatricula;
        String param_Item_Folio_IdAnotacion; // se debe buscar dentro de los folios padre = request.getParameter( CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID );
        String param_Item_Folio_IdZonaRegistral; // se debe colocar cuando se tenga el folio padre
        String param_Item_Folio_ThisIdAnotacion; // anotacion en el folio editado mediante la cuan lse hace el enlace con una anotacion en el otro folio

        // capturar los parametros de la forma: (escritos)
        param_Item_Folio_IdMatricula = request.getParameter(AWCrearFolioOficio.PARAM_ITEM_FOLIO_IDMATRICULA);
        param_Item_Folio_IdAnotacion = request.getParameter(AWCrearFolioOficio.PARAM_ITEM_FOLIO_ANOTACIONID);
        param_Item_Folio_ThisIdAnotacion = request.getParameter(AWCrearFolioOficio.PARAM_ITEM_THISFOLIO_ANOTACIONID);
        // se asume zona registral igual a la del folio editado
        param_Item_Folio_IdZonaRegistral = folio.getZonaRegistral().getIdZonaRegistral();

        // comparar si es la cadena vacia o nulo;
        BasicConditionalValidator validator;
        validator = new BasicStringNotNullOrEmptyValidatorWrapper();

        validator.setObjectToValidate(param_Item_Folio_IdMatricula);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-matricula: no se ha seleccionado una cadena valida");
        }

        validator.setObjectToValidate(param_Item_Folio_IdAnotacion);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-anotacion: no se ha seleccionado una cadena valida");
        }

        validator.setObjectToValidate(param_Item_Folio_IdZonaRegistral);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha encontrado valor
            exception.addError("id-xona registral: no se ha encontrado un valor en el folio");
        }

        validator.setObjectToValidate(param_Item_Folio_ThisIdAnotacion);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha encontrado valor
            exception.addError("id-anotacion (en este folio): no se ha digitado una cadena valida");
        }

        validator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        validator.setObjectToValidate(param_Item_Folio_IdAnotacion);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-anotacion: no es un numero valido");
        }

        validator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        validator.setObjectToValidate(param_Item_Folio_ThisIdAnotacion);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-anotacion (en este folio): no es un numero valido");
        }

        // 2. consultar si el folio indicado como parametro es
        // el mismo padre; si lo es throw exception
        validator = new BasicStringPatternValidatorWrapper(folio.getIdMatricula(), true, true);
        validator.setObjectToValidate(param_Item_Folio_IdMatricula);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            //
            exception.addError("id-matricula: la matricula indicada es la misma que el folio origen");
        }

        // para realizar busquedas
        StringBuffer jxpathQueryBuffer;
        JXPathContext searchContext;
        FolioDerivado targetLink;
        Iterator iterator;

        // ------------------------------------------------------
        // raise application error
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // ------------------------------------------------------

        // ------------------------------------------------------
        Anotacion padre = null; // PADRE
        Anotacion hijo = null; // HIJO

        // ------------------------------------------------------
        hijo = new Anotacion();

        hijo.setIdMatricula(param_Item_Folio_IdMatricula);
        hijo.setIdAnotacion(param_Item_Folio_IdAnotacion);

        // ------------------------------------------------------
        // a partir del folio derivado construir una anotacion:
        padre = new Anotacion();
        padre.setIdAnotacion(param_Item_Folio_ThisIdAnotacion); // se recibe como parametro // no se puede saber que anotacion se referencia si no se esta seguro que exista el folio source
        // inicialmente colocar el valor de la primera anotacion de tipo derivada que tenga esa matricula
        padre.setIdMatricula(folio.getIdMatricula());

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.CREAR_DERIVACION);
        evnCrearFolio.setAnotacionHijo(hijo);
        evnCrearFolio.setAnotacionPadre(padre);
        evnCrearFolio.setFolio(folio);
        evnCrearFolio.setUsuarioSir(usuarioSIR);

        return evnCrearFolio;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarDerivado(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosException();

        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == folio) {
            folio = new Folio();
        }

        List t0_folio_padres = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_HIJO);
        if (t0_folio_padres == null) {
            t0_folio_padres = new java.util.ArrayList();
        }

        String param_Item_Folio_IdMatricula = null;
        String param_Item_Folio_IdAnotacion = null; // se debe buscar dentro de los folios padre = request.getParameter( CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID );
        String param_Item_Folio_IdZonaRegistral = null; // se debe colocar cuando se tenga el folio padre

        //	 primero se debe tener en cuenta que es un checkbox, asi que se debe seleccinar un solo valor
        String[] param_Selected_Folio_IdMatriculas;

        param_Selected_Folio_IdMatriculas = request.getParameterValues("FOLIOEDIT_PADRESHIJOS_HIJO__ITEMS");

        if (null == param_Selected_Folio_IdMatriculas || param_Selected_Folio_IdMatriculas.length <= 0) {
            exception.addError("id-matricula: debe seleccionar los items a eliminar");
        } else {
            if (param_Selected_Folio_IdMatriculas.length > 1) {
                exception.addError("id-matricula: debe seleccionar un solo item");
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        param_Item_Folio_IdMatricula = param_Selected_Folio_IdMatriculas[0];

        BasicConditionalValidator validator;

        // comparar si es la cadena vacia o nulo;
        validator = new BasicStringNotNullOrEmptyValidatorWrapper();

        validator.setObjectToValidate(param_Item_Folio_IdMatricula);
        validator.validate();
        if (validator.getResult() != true) {
            exception.addError("id-matricula: no se ha seleccionado una cadena valida");
        }

        //	  buscar en la lista de folios padre el folio seleccionado
        // usar un comparador parqa las busquedas
        Folio selectedFolio = null;

        BasicAbstractComparator comparator = new BasicStringComparator(true, true);

        for (Iterator iterator = t0_folio_padres.iterator(); iterator.hasNext();) {
            Folio element = (Folio) iterator.next();

            if (element == null) {
                continue;
            }

            String element_idMatricula = element.getIdMatricula();

            if (comparator.compare(param_Item_Folio_IdMatricula, element_idMatricula) == 0) {
                // elementFounded
                selectedFolio = element;
                break;
            }

        }

        if (selectedFolio == null) {
            exception.addError("id-matricula: no se encontro el item seleccionado");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        //	  validations stage 2
        exception = new ValidacionParametrosException();

        Anotacion padre = null; // padre
        Anotacion hijo = null; // hijo

        // ------------------------------------------------------
        padre = new Anotacion();

        padre.setIdAnotacion(null); // no se conoce la anotacion por la cual se enlaza a param_folio // FolioDerivado.idAnotacion
        padre.setIdMatricula(folio.getIdMatricula()); // FolioDerivado.idMatricula1

        // en el folio derivado estan los datos de la anotacion origen;
        // con ellos completar los datos que faltaban:
        hijo = new Anotacion();
        hijo.setIdAnotacion(null); // no se la anotacion
        hijo.setIdMatricula(param_Item_Folio_IdMatricula); // seleccionada

        // validar que folio exista
        // validar que folio.anotacion exista
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.ELIMINAR_DERIVACION);
        evnCrearFolio.setAnotacionHijo(hijo);
        evnCrearFolio.setAnotacionPadre(padre);
        evnCrearFolio.setFolio(folio);
        evnCrearFolio.setUsuarioSir(usuarioSIR);

        return evnCrearFolio;
        //return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento agregarEnglobe(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosException();
        // Cargar el estado t0 de los objetos

        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == folio) {
            folio = new Folio();
        }
        // cargar id de matricula actual
        // String idMatricula = t0_folio.getIdMatricula();

        // cargar la lista de padres (no es necesario cargar la lista de hijos)
        // el fin de esta carga es ver si el item a agregar no se encuentra inscrito como padre
        java.util.List t0_folio_padres = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
        if (null == t0_folio_padres) {
            t0_folio_padres = new java.util.ArrayList();
        }

        // capturar los parametros de la forma:
        String param_Item_Folio_IdMatricula;
        String param_Item_Folio_IdAnotacion; // se debe buscar dentro de los folios padre = request.getParameter( CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID );
        String param_Item_Folio_IdZonaRegistral; // se debe colocar cuando se tenga el folio padre
        String param_Item_Folio_ThisIdAnotacion; // anotacion en el folio editado mediante la cuan lse hace el enlace con una anotacion en el otro folio

        // capturar los parametros de la forma: (escritos)
        param_Item_Folio_IdMatricula = request.getParameter(AWCrearFolioOficio.PARAM_ITEM_FOLIO_IDMATRICULA_ENGLOBE);
        param_Item_Folio_IdAnotacion = request.getParameter(AWCrearFolioOficio.PARAM_ITEM_FOLIO_ANOTACIONID_ENGLOBE);
        param_Item_Folio_ThisIdAnotacion = request.getParameter(AWCrearFolioOficio.PARAM_ITEM_THISFOLIO_ANOTACIONID_ENGLOBE);
        // se asume zona registral igual a la del folio editado
        param_Item_Folio_IdZonaRegistral = folio.getZonaRegistral().getIdZonaRegistral();

        // comparar si es la cadena vacia o nulo;
        BasicConditionalValidator validator;
        validator = new BasicStringNotNullOrEmptyValidatorWrapper();

        validator.setObjectToValidate(param_Item_Folio_IdMatricula);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-matricula: no se ha seleccionado una cadena valida");
        }

        validator.setObjectToValidate(param_Item_Folio_IdAnotacion);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-anotacion: no se ha seleccionado una cadena valida");
        }

        validator.setObjectToValidate(param_Item_Folio_IdZonaRegistral);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha encontrado valor
            exception.addError("id-xona registral: no se ha encontrado un valor en el folio");
        }

        validator.setObjectToValidate(param_Item_Folio_ThisIdAnotacion);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha encontrado valor
            exception.addError("id-anotacion (en este folio): no se ha digitado una cadena valida");
        }

        validator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        validator.setObjectToValidate(param_Item_Folio_IdAnotacion);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-anotacion: no es un numero valido");
        }

        validator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        validator.setObjectToValidate(param_Item_Folio_ThisIdAnotacion);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-anotacion (en este folio): no es un numero valido");
        }

        // 2. consultar si el folio indicado como parametro es
        // el mismo padre; si lo es throw exception
        validator = new BasicStringPatternValidatorWrapper(folio.getIdMatricula(), true, true);
        validator.setObjectToValidate(param_Item_Folio_IdMatricula);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            //
            exception.addError("id-matricula: la matricula indicada es la misma que el folio origen");
        }

        // para realizar busquedas
        StringBuffer jxpathQueryBuffer;
        JXPathContext searchContext;
        FolioDerivado targetLink;
        Iterator iterator;

        // ------------------------------------------------------
        // raise application error
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // ------------------------------------------------------

        // ------------------------------------------------------
        Anotacion padre = null; // PADRE
        Anotacion hijo = null; // HIJO

        // ------------------------------------------------------
        hijo = new Anotacion();

        hijo.setIdMatricula(param_Item_Folio_IdMatricula);
        hijo.setIdAnotacion(param_Item_Folio_IdAnotacion);

        // ------------------------------------------------------
        // a partir del folio derivado construir una anotacion:
        padre = new Anotacion();
        padre.setIdAnotacion(param_Item_Folio_ThisIdAnotacion); // se recibe como parametro // no se puede saber que anotacion se referencia si no se esta seguro que exista el folio source
        // inicialmente colocar el valor de la primera anotacion de tipo derivada que tenga esa matricula
        padre.setIdMatricula(folio.getIdMatricula());

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.CREAR_ENGLOBE);
        evnCrearFolio.setAnotacionHijo(padre);
        evnCrearFolio.setAnotacionPadre(hijo);
        evnCrearFolio.setFolio(folio);
        evnCrearFolio.setUsuarioSir(usuarioSIR);

        return evnCrearFolio;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarEnglobe(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosException();

        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == folio) {
            folio = new Folio();
        }

        List t0_folio_padres = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
        if (t0_folio_padres == null) {
            t0_folio_padres = new java.util.ArrayList();
        }

        String param_Item_Folio_IdMatricula = null;
        String param_Item_Folio_IdAnotacion = null; // se debe buscar dentro de los folios padre = request.getParameter( CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID );
        String param_Item_Folio_IdZonaRegistral = null; // se debe colocar cuando se tenga el folio padre

        //	 primero se debe tener en cuenta que es un checkbox, asi que se debe seleccinar un solo valor
        String[] param_Selected_Folio_IdMatriculas;

        param_Selected_Folio_IdMatriculas = request.getParameterValues("FOLIOEDIT_PADRESHIJOS_PADRE__ITEMS");

        if (null == param_Selected_Folio_IdMatriculas || param_Selected_Folio_IdMatriculas.length <= 0) {
            exception.addError("id-matricula: debe seleccionar los items a eliminar");
        } else {
            if (param_Selected_Folio_IdMatriculas.length > 1) {
                exception.addError("id-matricula: debe seleccionar un solo item");
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        param_Item_Folio_IdMatricula = param_Selected_Folio_IdMatriculas[0];

        BasicConditionalValidator validator;

        // comparar si es la cadena vacia o nulo;
        validator = new BasicStringNotNullOrEmptyValidatorWrapper();

        validator.setObjectToValidate(param_Item_Folio_IdMatricula);
        validator.validate();
        if (validator.getResult() != true) {
            exception.addError("id-matricula: no se ha seleccionado una cadena valida");
        }

        //	  buscar en la lista de folios padre el folio seleccionado
        // usar un comparador parqa las busquedas
        Folio selectedFolio = null;

        BasicAbstractComparator comparator = new BasicStringComparator(true, true);

        for (Iterator iterator = t0_folio_padres.iterator(); iterator.hasNext();) {
            Folio element = (Folio) iterator.next();

            if (element == null) {
                continue;
            }

            String element_idMatricula = element.getIdMatricula();

            if (comparator.compare(param_Item_Folio_IdMatricula, element_idMatricula) == 0) {
                // elementFounded
                selectedFolio = element;
                break;
            }

        }

        if (selectedFolio == null) {
            exception.addError("id-matricula: no se encontro el item seleccionado");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        //	  validations stage 2
        exception = new ValidacionParametrosException();

        Anotacion padre = null; // padre
        Anotacion hijo = null; // hijo

        // ------------------------------------------------------
        padre = new Anotacion();

        padre.setIdAnotacion(null); // no se conoce la anotacion por la cual se enlaza a param_folio // FolioDerivado.idAnotacion
        padre.setIdMatricula(param_Item_Folio_IdMatricula); // FolioDerivado.idMatricula1

        // en el folio derivado estan los datos de la anotacion origen;
        // con ellos completar los datos que faltaban:
        hijo = new Anotacion();
        hijo.setIdAnotacion(null); // no se la anotacion
        hijo.setIdMatricula(folio.getIdMatricula()); // seleccionada

        // validar que folio exista
        // validar que folio.anotacion exista
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        EvnCrearFolio evnCrearFolio = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.ELIMINAR_ENGLOBE);
        evnCrearFolio.setAnotacionHijo(hijo);
        evnCrearFolio.setAnotacionPadre(padre);
        evnCrearFolio.setFolio(folio);
        evnCrearFolio.setUsuarioSir(usuarioSIR);

        return evnCrearFolio;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarSegregacionEnglobe(HttpServletRequest request) {
        return null;
    }

    // inner class -----------------------------
    // @delegate
    public static class Local_Formats {

        static BasicStringComparator stringComparator = new BasicStringComparator(true, true, true);

        public static Date toDate(String string, String pattern) {
            return BasicConvertorActions.toDate(string, pattern);
        } // end-method:

        public static String toString(Date date, String pattern) {
            return BasicConvertorActions.toString(date, pattern);
        } // end-method:

        public static final String EMPTY = "";

        // @see org.apache.commons.lang.StringUtils
        public static String substringBefore(String str, String separator) {
            if (isEmpty(str) || separator == null) {
                return str;
            }
            if (separator.length() == 0) {
                return EMPTY;
            }
            int pos = str.indexOf(separator);
            if (pos == -1) {
                return str;
            }
            return str.substring(0, pos);
        }

        //	  @see org.apache.commons.lang.StringUtils
        public static String substringAfter(String str, String separator) {
            if (isEmpty(str)) {
                return str;
            }
            if (separator == null) {
                return EMPTY;
            }
            int pos = str.indexOf(separator);
            if (pos == -1) {
                return EMPTY;
            }
            return str.substring(pos + separator.length());
        }

        public static boolean isEmpty(String str) {
            return str == null || str.length() == 0;
        }

        public static String Nvl(Object obj, String replace) {
            if (null == obj) {
                return replace;
            }
            return obj.toString();
        } // end-method;

        public static String decodeCondition1(Object obj, String testValue1, String trueValue1, String falseValue1) {

            if ((obj != null)
                    && (!(obj instanceof java.lang.String))) {
                return falseValue1;
            } // if

            if (stringComparator.compare(obj, testValue1) != 0) {
                return falseValue1;
            }

            return trueValue1;

            /*

	          if( null == obj ) {
	        	  return falseValue1;
	          }
	          if( !( obj instanceof String ) ) {
	        	  return falseValue1;
	          }

	          String objAsString = (String)obj;

	          if( null == objAsString ) {

	        	  if( null != testValue1 ) {
	        		  return falseValue1;
	        	  }
	        	  else {
	        		  return trueValue1;
	        	  }

	          }

	          if( !testValue1.equals( objAsString ) ){
	        	  return falseValue1;
	          }

	          return trueValue1;
             */
        } // end-method;

    } // end-class:

    // -----------------------------------------
    private Evento imprimirFolio(HttpServletRequest request) throws AccionWebException {
        ErrorCrearAnotacionException exception = new ErrorCrearAnotacionException();

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        //Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if ((anotaciones == null) || (anotaciones.size() == 0)) {
            exception.addError("No se puede imprimir un folio sin anotaciones");
            throw exception;
        }

        int tam = anotaciones.size();

        for (int i = 0; i < tam; i++) {
            Anotacion anota = (Anotacion) anotaciones.get(i);
            TipoAnotacion tipoAnota = new TipoAnotacion();
            tipoAnota.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
            anota.setTipoAnotacion(tipoAnota);
        }

        removerAnotacionesFromFolio(folio);
        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();

            while (itAnotaciones.hasNext()) {
                folio.addAnotacione((Anotacion) itAnotaciones.next());
            }
        }
        EvnCrearFolio evn = new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.IMPRIMIR_HOJA_DE_RUTA, folio, usuario);
        evn.setCirculo(circulo);

        return evn;
    }

} // end-class:
