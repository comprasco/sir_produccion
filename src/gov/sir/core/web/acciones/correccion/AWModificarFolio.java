package gov.sir.core.web.acciones.correccion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnPaginadorAnotaciones;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.correccion.EvnCorreccion;
import gov.sir.core.eventos.correccion.EvnCorreccionFolioPadresHijos;
import gov.sir.core.eventos.correccion.EvnRespCorreccion;
import gov.sir.core.eventos.correccion.EvnRespCorreccionFolioPadresHijos;
import gov.sir.core.eventos.registro.EvnCalificacion;
import gov.sir.core.eventos.registro.EvnFolio;
import gov.sir.core.eventos.registro.EvnRespCalificacion;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.eventos.registro.EvnRespNextOrdenAnotacion;
import gov.sir.core.eventos.registro.EvnRespSegregacion;
import gov.sir.core.eventos.registro.EvnSegregacion;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Cancelacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.CopiaAnotacion;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Diferencias;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoAnotacion;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.WebAnotacion;
import gov.sir.core.negocio.modelo.WebCiudadano;
import gov.sir.core.negocio.modelo.WebDireccion;
import gov.sir.core.negocio.modelo.WebDocumento;
import gov.sir.core.negocio.modelo.WebEnglobe;
import gov.sir.core.negocio.modelo.WebFolioHeredado;
import gov.sir.core.negocio.modelo.WebFolioNuevo;
import gov.sir.core.negocio.modelo.WebSegEng;
import gov.sir.core.negocio.modelo.WebSegregacion;
import gov.sir.core.negocio.modelo.constantes.CAccionFolioEditPadresHijos;
import gov.sir.core.negocio.modelo.constantes.CAnotacion;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CComplementacion;
import gov.sir.core.negocio.modelo.constantes.CDepartamento;
import gov.sir.core.negocio.modelo.constantes.CDigitacionMasiva;
import gov.sir.core.negocio.modelo.constantes.CDireccion;
import gov.sir.core.negocio.modelo.constantes.CDocumento;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CMunicipio;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSalvedadAnotacion;
import gov.sir.core.negocio.modelo.constantes.CSalvedadFolio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CTabs;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.negocio.modelo.constantes.CVereda;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.NaturalezaInvalidaException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionAnotacionesCancelacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAgregarAnotacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAnotacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCancelacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoEdicionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoSegregacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosDigitacionMasivaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEdicionCancelacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEditarFolioException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosModificarAnotacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosModificarFolioDigitacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosModificarFolioEspecializadoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosModificarFolioException;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import static gov.sir.core.web.acciones.registro.AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_CANCELACION;
import static gov.sir.core.web.acciones.registro.AWCalificacion.CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION;
import gov.sir.core.web.acciones.registro.AWLiquidacionRegistro;
import gov.sir.core.web.helpers.comun.DatosRespuestaPaginador;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.JvLocalUtils;
import gov.sir.core.web.helpers.comun.MostrarFolioHelper;
import gov.sir.core.web.helpers.comun.SeleccionarNaturalezaJuridicaHelper;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;
import gov.sir.core.web.helpers.correccion.diff.BasicJXPathForwardDiffProcessor;
import gov.sir.core.web.helpers.correccion.diff.utils.BasicJxPathUtils;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicAbstractComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicDateFormatComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicDoublePlusThresholdComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicStringComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.jxpath.FolioAbstractFactory;
import gov.sir.core.web.helpers.correccion.diff.utils.jxpath.FolioAnotacionAbstractFactory;
import gov.sir.core.web.helpers.correccion.region.model.BindManager;
import gov.sir.core.web.helpers.correccion.region.model.FolioAnotacionEditBindManager;
import gov.sir.core.web.helpers.correccion.region.model.FolioEditBindManager;
import gov.sir.core.web.helpers.correccion.region.model.ProcessManager;
import gov.sir.core.web.helpers.correccion.region.model.ValidatorManager;
import gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants;
import gov.sir.core.web.helpers.correccion.validate.utils.conditions.BasicStaticBooleanCondition;
import gov.sir.core.web.helpers.correccion.validate.utils.conditions.ConditionComponent;
import gov.sir.core.web.helpers.correccion.validate.utils.convertors.BasicConvertorActions;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicConditionalValidator;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicDatatypeString2DateConversionValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicDatatypeString2DoubleConversionValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicDatatypeString2IntegerConversionValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicDoubleRangeValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicObjectNotNullValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringMaxLengthValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringMinLengthValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringNotNullOrEmptyValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringPatternValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.ConditionalValidator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.jxpath.AbstractFactory;
import org.apache.commons.jxpath.JXPathContext;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
/**
 * @author : Carlos Mario Torres Urina
 * @casoMantis : 0012705.
 * @actaReq : Acta - Requerimiento No
 * 056_453_Modificiación_de_Naturaleza_Jurídica
 * @change : Declara variable natJuridica
 */
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicLongPlusThresholdComparator;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con la
 * modificación de un folio en el proceso de correcciones. Se encarga de
 * realizar las validaciones primarias a nivel web y por medio de eventos hace
 * llamados a la capa de negocio para que su vez llame los servicios que se
 * requieren.
 *
 * @author ppabon
 */
public class AWModificarFolio extends SoporteAccionWeb {

    //SIR-41(R1)
    /**
     * Esta constante identifica el campo donde se introduce el número de la
     * complementacion del folio
     */
    public static final String FOLIO_COMPLEMENTACION = "FOLIO_COMPLEMENTACION";

    // Bug 3552
    public static final String PARAM__OPTION_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONADICIONAENABLED
            = "PARAM__OPTION_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONADICIONAENABLED";
    // --
    public static final String PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT0
            = "PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT0";
    public static final String PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT2
            = FOLIO_COMPLEMENTACION; // = "PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT2";

    // --
    public static final String PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_FORWARDDIFFCOMPARISONRESULTS
            = "PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_FORWARDDIFFCOMPARISONRESULTS";
    public static final String PARAM__ITEM_CORRSIMPLE_FOLIOANOTACIONXEDIT_FORWARDDIFFCOMPARISONRESULTS
            = "PARAM__ITEM_CORRSIMPLE_FOLIOANOTACIONXEDIT_FORWARDDIFFCOMPARISONRESULTS";

    public static final String ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION
            = "ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION";

    public static final String ITEMID_FOLIOEDICION_SALVEDADFOLIOID
            = "ITEMID_FOLIOEDICION_SALVEDADFOLIOID";

    public static final String MODIFICA_DEFINITIVA = "MODIFICA_DEFINITIVA";

    public static final String EDITAR_CANCELACION = "EDITAR_CANCELACION";

    public static final String PAGE_REGION__STARTANOTACIONSEGREGACION_ACTION
            = "PAGE_REGION__STARTANOTACIONSEGREGACION_ACTION";
    public static final String PAGE_REGION__STARTANOTACIONENGLOBE_ACTION
            = "PAGE_REGION__STARTANOTACIONENGLOBE_ACTION";
    public static final String PAGE_REGION__FINISHANOTACIONSEGREGACION_ACTION
            = "PAGE_REGION__FINISHANOTACIONSEGREGACION_ACTION";
    public static final String PAGE_REGION__FINISHANOTACIONENGLOBE_ACTION
            = "PAGE_REGION__FINISHANOTACIONENGLOBE_ACTION";

    public static final String PAGE_REGION__STARTANOTACIONCOPIARANOTACION_ACTION
            = "PAGE_REGION__STARTANOTACIONCOPIARANOTACION_ACTION";

    //
    public static final String PAGE_ANOTACIONCANCELADORACREARSTEP0_BTNSTART_ACTION
            = "PAGE_ANOTACIONCANCELADORACREARSTEP0_BTNSTART_ACTION";
    public static final String PAGE_ANOTACIONCANCELADORACREARSTEP1_BTNBACK_ACTION
            = "PAGE_ANOTACIONCANCELADORACREARSTEP1_BTNBACK_ACTION";
    public static final String PAGE_ANOTACIONCANCELADORACREARSTEP1_BTNNEXT_ACTION
            = "PAGE_ANOTACIONCANCELADORACREARSTEP1_BTNNEXT_ACTION";

    //
    public static final String PAGE_ANOTACIONCANCELADORAEDITARSTEP0_BTNSTART_ACTION
            = EDITAR_CANCELACION;
    public static final String PAGE_ANOTACIONCANCELADORAEDITARSTEP1_BTNBACK_ACTION
            = "PAGE_ANOTACIONCANCELADORAEDITARSTEP1_BTNBACK_ACTION";
    public static final String PAGE_ANOTACIONCANCELADORAEDITARSTEP1_BTNNEXT_ACTION
            = "PAGE_ANOTACIONCANCELADORAEDITARSTEP1_BTNNEXT_ACTION";

// TODO: do_* para englobe
    //
    // edicion de folio-padres-hijos
    public static final String FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_ACTION = AwCorrecciones_Constants.FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_ACTION;
    public static final String FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_ACTION = AwCorrecciones_Constants.FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_ACTION;

    public static final String FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_ACTION = AwCorrecciones_Constants.FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_ACTION;
    public static final String FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_ACTION = AwCorrecciones_Constants.FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_ACTION;

    public static final String NUMERO_SEGREGACIONES_VACIAS = "NUMERO_SEGREGACIONES_VACIAS";

    // recarga solicitada al actualizar un ciudadano sobre varios folios y anotaciones
    public static final String FOLIOEDIT_RELOADBYUPDATECIUDADANO_EDITANOTACIONNORMAL_PAGERENDERING_PROCESS_ACTION
            = AwCorrecciones_Constants.FOLIOEDIT_RELOADBYUPDATECIUDADANO_EDITANOTACIONNORMAL_PAGERENDERING_PROCESS_ACTION;

    // edicion de folio-direccion - definitiva
    public static final String FOLIOEDIT_DIRECCIONDEFINITIVA_DELITEM_ACTION = "FOLIOEDIT_DIRECCIONDEFINITIVA_DELITEM_ACTION";
    public static final String FOLIOEDIT_DIRECCIONDEFINITIVA_EDITITEM_ACTION = "FOLIOEDIT_DIRECCIONDEFINITIVA_EDITITEM_ACTION";

    // creacion anotacion-canceladora
    public static final String CANCELAR_CANCELACION = "CANCELAR_CANCELACION";
    public static final String CANCELAR_ANOTACION = "CANCELAR_ANOTACION";
    public static final String REFRESCAR_CANCELACION = "REFRESCAR_CANCELACION";
    public static final String CANCELAR = "CANCELAR";
    public static final String AGREGAR_VARIOS_CIUDADANOS_CANCELACION = "AGREGAR_VARIOS_CIUDADANOS_CANCELACION";
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION";
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION";
    public static final String ELIMINAR_CIUDADANO_ANOTACION_CANCELACION = "ELIMINAR_CIUDADANO_ANOTACION_CANCELACION";
    public static final String VALIDAR_CIUDADANO_CANCELACION = "VALIDAR_CIUDADANO_CANCELACION ";

    // edicion anotacion-canceladora
    public static final String CANCELAR_EDICION_CANCELACION = "CANCELAR_EDICION_CANCELACION";
    public static final String REFRESCAR_EDICION_CANCELACION = "REFRESCAR_EDICION_CANCELACION";
    public static final String AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION = "AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION";
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION";
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION";
    public static final String ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION = "ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION";
    public static final String VALIDAR_CIUDADANO_EDICION_CANCELACION = "VALIDAR_CIUDADANO_EDICION_CANCELACION ";

    //Para Cargar el codigo de las anotaciones
    public static final String CARGAR_DESCRIPCION_NATURALEZA_CANCELACION = "CARGAR_DESCRIPCION_NATURALEZA_CANCELACION";
    public static final String CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION = "CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION";
    public static final String CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION = "CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION";
    // creacion anotacion-normal (varios-ciudadanos)

    public static final String CRTANOTACIONNORMAL_CIUDADANO_ADDITEM = "CRTANOTACIONNORMAL_CIUDADANO_ADDITEM";
    public static final String CRTANOTACIONNORMAL_CIUDADANO_DELITEM = "CRTANOTACIONNORMAL_CIUDADANO_DELITEM";
    public static final String CRTANOTACIONNORMAL_CIUDADANO_ADDREPEATER_1FIELD = "CRTANOTACIONNORMAL_CIUDADANO_ADDREPEATER_1FIELD";
    public static final String CRTANOTACIONNORMAL_CIUDADANO_DELREPEATER_1FIELD = "CRTANOTACIONNORMAL_CIUDADANO_DELREPEATER_1FIELD";
    public static final String CRTANOTACIONNORMAL_CIUDADANO_VALIDATEITEM = "CRTANOTACIONNORMAL_CIUDADANO_VALIDATEITEM";
    //

    public static final String GRABAR_EDICION_CANCELACION = "GRABAR_EDICION_CANCELACION";

    public static final String ELIMINAR_ANOTACION_TEMPORAL = "ELIMINAR_ANOTACION_TEMPORAL";

    public static final String ELEMENTO_EDITAR = "ELEMENTO_EDITAR";

    public static final String ELIMINAR_CAMBIOS_ANOTACION_DEFINITIVA_TEMPORAL
            = "ELIMINAR_CAMBIOS_ANOTACION_DEFINITIVA_TEMPORAL";

    /**
     * Esta constante identifica el numero de la anotacion que se va modificar
     */
    public static final String NUM_ANOTACION_TEMPORAL = "NUM_ANOTACION_TEMPORAL";

    public static final String VALIDAR_CIUDADANO_EDICION = "VALIDAR_CIUDADANO_EDICION";

    public static final String REFRESCAR_EDICION = "REFRESCAR_EDICION";

    /**
     * Constante que indica que se va a agregar un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION";

    /**
     * Constante que indica que se va a remover un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION";

    public static final String AGREGAR_VARIOS_CIUDADANOS_EDICION = "AGREGAR_VARIOS_CIUDADANOS_EDICION";

    /**
     * Constante que indica que se usa en el request para obtener el numero de
     * registros de la tabla de adicion de ciudadanos a la anotacion.*
     */
    public static final String NUM_REGISTROS_TABLA_CIUDADANOS = "NUM_REGISTROS_TABLA_CIUDADANOS";

    //Varios ciudadanos
    /**
     * HELPER DE VARIOS CIUDADANOS
     */
    public static final int DEFAULT_NUM_CIUDADANOS_TABLA = 2;

    /**
     * Constantes que identifican las acciones posibles dentro de la acción Web
     */
    /**
     * Constante que indica que se desea consultar los detalles de un folio para
     * Digitación Masiva
     */
    public static final String CONSULTA_DIGITACION_MASIVA = "CONSULTA_DIGITACION_MASIVA";

    /**
     * Constante que indica que se desea guardar cambios a los folios por la
     * opción de digitación masiva.
     */
    public static final String EDITAR_FOLIO_DIGITACION_MASIVA = "EDITAR_FOLIO_DIGITACION_MASIVA";

    /**
     * Constante que indica que se desea Cargar un folio para ser editado
     */
    public static final String CARGAR_FOLIO_CORRECCION = "CARGAR_FOLIO_CORRECCION";

    /**
     * Constante que indica que se desea Cargar un folio para ser editado
     */
    public static final String EDICION_FOLIO = "EDICION_FOLIO";

    /**
     * Constante que indica que se desea Cargar un folio para ser editado
     */
    public static final String CARGAR_FOLIO_DIGITACION = "CARGAR_FOLIO_DIGITACION";

    /**
     * Constante que indica que se desea Cargar un folio para ser editado
     */
    public static final String CARGAR_FOLIO_ESPECIALIZADO = "CARGAR_FOLIO_ESPECIALIZADO";

    /**
     * Constante que indica que se desea Cargar una Anotación para ser editada
     */
    public static final String CARGAR_ANOTACION = "CARGAR_ANOTACION";

    /**
     * Constante que indica que se desea agregar una anotación.*
     */
    public static final String CARGAR_ANOTACION_AGREGAR = "CARGAR_ANOTACION_AGREGAR";

    /**
     * Constante que indica que se desea crear un folio desenglobándolo desde
     * otro
     */
    public static final String DESENGLOBAR = "DESENGLOBAR";

    /**
     * Constante que indica que se desea crear un folio englobando una lista de
     * folios existentes
     */
    public static final String ENGLOBAR = "ENGLOBAR";

    /**
     * Constante que identifica que el usuario ha llenado una anotación y la va
     * agregar a la lista de anotaciones
     */
    public static final String AGREGAR_ANOTACION = "AGREGAR_ANOTACION";

    /**
     * Constante que identifica que el usuario desea cancelar una anotación del
     * folio anotaciones
     */
    public static final String CANCELAR_ANOTACION_SESSION = "CANCELAR_ANOTACION_SESSION";

    /**
     * Constante que identifica que el usuario ha llenado una anotación y la va
     * agregar a la lista de anotaciones
     */
    public static final String AGREGAR_ANOTACION_CORRECCION = "AGREGAR_ANOTACION_CORRECCION";

    /**
     * Constante que identifica que el usuario ha llenado una anotación y la va
     * agregar a la lista de anotaciones
     */
    public static final String AGREGAR_ANOTACION_ESPECIALIZADO = "AGREGAR_ANOTACION_ESPECIALIZADO";

    /**
     * Constante que identifica que el usuario va a eliminar una anotación a la
     * lista de anotaciones
     */
    public static final String ELIMINAR_ANOTACION = "ELIMINAR_ANOTACION";

    /**
     * Constante que identifica que el usuario va a eliminar una anotación a la
     * lista de anotaciones
     */
    public static final String ELIMINAR_ANOTACION_CORRECCION = "ELIMINAR_ANOTACION_CORRECCION";

    /**
     * Constante que identifica que el usuario va a eliminar una anotación a la
     * lista de anotaciones
     */
    public static final String ELIMINAR_ANOTACION_ESPECIALIZADO = "ELIMINAR_ANOTACION_ESPECIALIZADO";

    /**
     * Constante que identifica que el usuario va a Editar una anotación
     */
    public static final String EDITAR_ANOTACION = "EDITAR_ANOTACION";

    /**
     * Constante que identifica que el usuario va a Aceptar la edición de un
     * folio
     */
    public static final String ACEPTAR_EDICION_CORRECCION = "ACEPTAR_EDICION_CORRECCION";

    /**
     * Constante que identifica que el usuario va a Aceptar la edición de un
     * folio. Es ususada para guardar los cambios que se hicieron a un folio
     * temporal después de la segregación de un folio
     */
    public static final String GUARDAR_CAMBIOS_FOLIO = "GUARDAR_CAMBIOS_FOLIO";

    /**
     * Constante que identifica que el usuario va a Cancelar la edición de un
     * folio
     */
    public static final String CANCELAR_EDICION_CORRECCION = "CANCELAR_EDICION_CORRECCION";

    /**
     * Constante que identifica que el usuario va a Aceptar la edición de un
     * folio en Digitación
     */
    public static final String ACEPTAR_EDICION_DIGITACION = "ACEPTAR_EDICION_DIGITACION";

    /**
     * Constante que identifica que el usuario va a Cancelar la edición de un
     * folio en Digitación
     */
    public static final String CANCELAR_EDICION_DIGITACION = "CANCELAR_EDICION_DIGITACION";

    /**
     * Constante que identifica que el usuario va a Aceptar la edición de un
     * folio en Especializado
     */
    public static final String ACEPTAR_EDICION_ESPECIALIZADO = "ACEPTAR_EDICION_ESPECIALIZADO";

    /**
     * Constante que identifica que el usuario va a Cancelar la edición de un
     * folio en Especializado
     */
    public static final String CANCELAR_EDICION_ESPECIALIZADO = "CANCELAR_EDICION_ESPECIALIZADO";

    /**
     * Constante que identifica que el usuario va a Cancelar la edición de una
     * anotación
     */
    public static final String CANCELAR_EDICION_ANOTACION = "CANCELAR_EDICION_ANOTACION";

    /**
     * Constante que identifica que el usuario va a Aceptar la edición de una
     * anotación
     */
    public static final String ACEPTAR_EDICION_ANOTACION = "ACEPTAR_EDICION_ANOTACION";

    /**
     * Constante que identifica que el usuario ha llenado una DIRECCION en el
     * folio
     */
    public static final String AGREGAR_DIRECCION_CORRECCION = "AGREGAR_DIRECCION_CORRECCION";

    /**
     * Constante que identifica que el usuario a editado una DIRECCION y esta es
     * temporal en el folio
     */
    public static final String EDITAR_DIRECCION_CORRECCION_TEMP = "EDITAR_DIRECCION_CORRECCION_TEMP";

    /**
     * Constante que identifica que el usuario a aceptado la edicion de una
     * DIRECCION temporal en el folio
     */
    public static final String EDITAR_DIRECCION_CORRECCION_ACEPTAR_TEMP = "EDITAR_DIRECCION_CORRECCION_ACEPTAR_TEMP";

    /**
     * Constante que identifica que el usuario a aceptado la edicion de una
     * DIRECCION definitiva en el folio
     */
    public static final String EDITAR_DIRECCION_CORRECCION_ACEPTAR_DEF = "EDITAR_DIRECCION_CORRECCION_ACEPTAR_DEF";

    /**
     * Constante que identifica que el usuario a editado una DIRECCION en el
     * folio
     */
    public static final String EDITAR_ACEPTAR_DIRECCION_CORRECCION = "EDITAR_ACEPTAR_DIRECCION_CORRECCION";

    /**
     * Constante que identifica que el usuario quiere quitar una Dirección al
     * folio
     */
    public static final String ELIMINAR_DIRECCION_CORRECCION = "ELIMINAR_DIRECCION_CORRECCION";

    /**
     * Constante que identifica que el usuario ha llenado una DIRECCION en el
     * folio
     */
    public static final String AGREGAR_DIRECCION_DIGITACION = "AGREGAR_DIRECCION_DIGITACION";

    /**
     * Constante que identifica que el usuario quiere quitar una Dirección al
     * folio
     */
    public static final String ELIMINAR_DIRECCION_DIGITACION = "ELIMINAR_DIRECCION_DIGITACION";

    /**
     * Constante que identifica que el usuario ha llenado una DIRECCION en el
     * folio
     */
    public static final String AGREGAR_DIRECCION_ESPECIALIZADO = "AGREGAR_DIRECCION_ESPECIALIZADO";

    /**
     * Constante que identifica que el usuario quiere quitar una Dirección al
     * folio
     */
    public static final String ELIMINAR_DIRECCION_ESPECIALIZADO = "ELIMINAR_DIRECCION_ESPECIALIZADO";

    /**
     * Constante que identifica que el usuario ha llenado una DIRECCION en el
     * folio
     */
    public static final String AGREGAR_DIRECCION_EDICION = "AGREGAR_DIRECCION_EDICION";

    /**
     * Constante que identifica que el usuario quiere quitar una Dirección al
     * folio en la edición de un folio
     */
    public static final String ELIMINAR_DIRECCION_EDICION = "ELIMINAR_DIRECCION_EDICION";

    /**
     * Constante que identifica que el usuario ha llenado una persona en una
     * anotación y la va agregar a la lista de ciudadanos
     */
    public static final String AGREGAR_CIUDADANO = "AGREGAR_CIUDADANO";

    /**
     * Constante que identifica que el usuario ha llenado una persona en una
     * anotación y la va agregar a la lista de ciudadanos
     */
    public static final String AGREGAR_CIUDADANO_CORRECCION = "AGREGAR_CIUDADANO_CORRECCION";

    /**
     * Constante que identifica que el usuario ha llenado una persona en una
     * anotación y la va agregar a la lista de ciudadanos
     */
    public static final String AGREGAR_CIUDADANO_ESPECIALIZADO = "AGREGAR_CIUDADANO_ESPECIALIZADO";

    /**
     * Constante que identifica que el usuario ha llenado una persona en una
     * anotación y la va agregar a la lista de ciudadanos
     */
    public static final String ELIMINAR_CIUDADANO_CORRECCION = "ELIMINAR_CIUDADANO_CORRECCION";

    /**
     * Constante que identifica que el usuario ha llenado una persona en una
     * anotación y la va agregar a la lista de ciudadanos
     */
    public static final String ELIMINAR_CIUDADANO_ESPECIALIZADO = "ELIMINAR_CIUDADANO_ESPECIALIZADO";

    /**
     * Constante que identifica que el usuario ha llenado una persona en una
     * anotación y la va agregar a la lista de ciudadanos
     */
    public static final String AGREGAR_CIUDADANO_ANOTACION = "AGREGAR_CIUDADANO_ANOTACION";

    /**
     * Constante que identifica que el usuario ha llenado una persona en una
     * anotación y la va agregar a la lista de ciudadanos
     */
    public static final String EDITAR_CIUDADANO_ANOTACION = "EDITAR_CIUDADANO_ANOTACION";

    /**
     * Constante que identifica que el usuario va a eliminar una persona a la
     * lista de ciudadanos
     */
    public static final String ELIMINAR_CIUDADANO = "ELIMINAR_CIUDADANO";

    /**
     * Usada por compatibilidad de helpers con registro,calificacion
     */
    public static final String ELIMINAR_CIUDADANO_EDICION = "ELIMINAR_CIUDADANO_EDICION";

    /**
     * Constante que identifica que el usuario va a eliminar una persona a la
     * lista de ciudadanos en el formulario de Anotaciones
     */
    public static final String ELIMINAR_CIUDADANO_ANOTACION = "ELIMINAR_CIUDADANO_ANOTACION";

    /**
     * Constantes que identifican las constantes para la anotación
     */
    /**
     * Constante que identifica *
     */
    public static final String PRESERVAR_INFO = "PRESERVAR_INFO";

    /**
     * Constante que identifica que se desea guardar una copia de una salvedad*
     */
    public static final String ANOTACION_COPIA = "ANOTACION_COPIA";

    /**
     * Esta constante identifica el campo donde se introduce el número de la
     * radicacion en la anotación
     */
    public static final String ANOTACION_NUM_RADICACION = "ANOTACION_NUM_RADICACION";

    /**
     * Esta constante identifica el campo donde se introduce la fecha de la
     * radicacion en la anotación
     */
    public static final String ANOTACION_FECHA_RADICACION = "ANOTACION_FECHA_RADICACION";

    /**
     * Esta constante identifica el campo donde se introduce el tipo de
     * docuemtno en la anotación
     */
    public static final String ANOTACION_TIPO_DOCUMENTO = "ANOTACION_TIPO_DOCUMENTO";

    /**
     * Esta constante identifica el campo donde se introduce el tipo de
     * docuemtno en la anotación
     */
    public static final String ANOTACION_ID_TIPO_DOCUMENTO = "ANOTACION_ID_TIPO_DOCUMENTO";

    /**
     * Esta constante identifica el campo donde se introduce el número del
     * documento en la anotación
     */
    public static final String ANOTACION_NUM_DOCUMENTO = "ANOTACION_NUM_DOCUMENTO";

    /**
     * Esta constante identifica el campo donde se introduce la fecha del
     * documento en la anotación
     */
    public static final String ANOTACION_FECHA_DOCUMENTO = "ANOTACION_FECHA_DOCUMENTO";

    /**
     * Constante que ayuda a poder pasar los atributos de la oficina del pop up
     * a la ventana principal*
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO = "ANOTACION_OFICINA_DOCUMENTO";

    /**
     * Esta constante identifica el campo donde se introduce el tipo de oficina
     * del documento en la anotación
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_TIPO = "ANOTACION_OFICINA_DOCUMENTO_TIPO";

    /**
     * Esta constante identifica el campo donde se introduce el tipo de oficina
     * del documento en la anotación
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_ID_TIPO = "ANOTACION_OFICINA_DOCUMENTO_ID_TIPO";

    /**
     * Esta constante identifica el campo donde se introduce el número de
     * oficina del documento en la anotación
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_NUM = "ANOTACION_OFICINA_DOCUMENTO_NUM";

    /**
     * @author : Ellery David Robles Gómez.
     * @casoMantis : 08375.
     * @actaRequerimiento : 261 - Correccion Oficina de Origen Internacional.
     * @change : Se agrega la variable
     * "ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL" para identificarla en
     * la sesion.
     */
    /**
     * Esta constante identifica el campo donde se introduce la oficina
     * internacional del documento en la anotación
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL = "ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL";

    /**
     * Esta constante identifica el campo donde se introduce el identificador de
     * la oficina del documento en la anotación
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA = "ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA";

    /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
    /**
     * Esta constante identifica el campo donde se introduce el identificador de
     * la oficina del documento en la anotación
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION = "ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION";

    /**
     * Esta constante identifica el campo donde se guardara el identificador del
     * departamento
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO = "ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO";

    /**
     * Esta constante identifica el campo donde se guardara el nombre del
     * departamento
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO = "ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO";

    /**
     * Esta constante identifica el campo donde se guardara el identificador del
     * municipio
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC = "ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC";

    /**
     * Esta constante identifica el campo donde se guardara el nombre del
     * municipio
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC = "ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC";

    /**
     * Esta constante identifica el campo donde se guardara el identificador de
     * la vereda
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA = "ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA";

    /**
     * Esta constante identifica el campo donde se guardara el nombre de la
     * vereda
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA = "ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA";

    /**
     * Esta constante identifica el campo donde se introduce el valor de la
     * especificación de la anotación
     */
    public static final String ANOTACION_VALOR_ESPECIFICACION = "ANOTACION_VALOR_ESPECIFICACION";

    /**
     * Esta constante identifica el campo donde se introduce el id de la
     * naturaleza juridica de la especificación de la anotación
     */
    public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID = "ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID";

    /**
     * Esta constante identifica el campo donde se introduce el nombre de la
     * naturaleza juridica de la especificación de la anotación
     */
    public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM = "ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM";

    /**
     * Prefijo que identifica la pareja de datos id y nombre para la naturaleza
     * juridica
     */
    public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION = "ANOTACION_NAT_JURIDICA_ESPECIFICACION";

    /**
     * Esta constante identifica el campo donde se introduce el comentario de la
     * especificación en la anotación
     */
    public static final String ANOTACION_COMENTARIO_ESPECIFICACION = "ANOTACION_COMENTARIO_ESPECIFICACION";

    /**
     * Esta constante identifica el campo donde se introduce el tipo de
     * identificacion del ciudadano en la anotación
     */
    public static final String ANOTACION_TIPO_ID_PERSONA = "ANOTACION_TIPO_ID_PERSONA";

    /**
     * Esta constante identifica el campo donde se introduce el número de
     * identificacion en la anotación
     */
    public static final String ANOTACION_NUM_ID_PERSONA = "ANOTACION_NUM_ID_PERSONA";

    /**
     * Esta constante identifica el campo donde se introduce el apellido de la
     * persona en la anotación
     */
    public static final String ANOTACION_APELLIDO_1_PERSONA = "ANOTACION_APELLIDO_1_PERSONA";

    /**
     * Esta constante identifica el campo donde se introduce el apellido de la
     * persona en la anotación
     */
    public static final String ANOTACION_APELLIDO_2_PERSONA = "ANOTACION_APELLIDO_2_PERSONA";

    /**
     * Esta constante identifica el campo donde se introduce los nombres de la
     * persona en la anotación
     */
    public static final String ANOTACION_NOMBRES_PERSONA = "ANOTACION_NOMBRES_PERSONA";

    /**
     * Esta constante identifica el campo donde se introduce los tipos de la
     * persona en la anotación
     */
    public static final String ANOTACION_TIPO_PERSONA = "ANOTACION_TIPO_PERSONA";

    /**
     * Esta constante identifica el campo donde se introduce el sexo de la
     * persona en la anotación
     */
    public static final String ANOTACION_SEXO_PERSONA = "ANOTACION_SEXO_PERSONA";

    /**
     * Esta constante identifica el campo donde se genera el codigo de
     * verificacion del NIT en la anotación
     */
    public static final String ANOTACION_COD_VERIFICACION = "ANOTACION_COD_VERIFICACION";

    /**
     * Esta constante identifica el campo donde se ingresa la modalidad en la
     * anotación
     */
    public static final String ANOTACION_MODALIDAD = "ANOTACION_MODALIDAD";

    /**
     * Esta constante identifica el campo donde se introduce el tipo de
     * intervencion en la asocioacion en la anotación
     */
    public static final String ANOTACION_TIPO_INTER_ASOCIACION = "ANOTACION_TIPO_INTER_ASOCIACION";

    /**
     * Esta constante identifica el campo donde se introduce si el ciudadano es
     * el propiuetario en la asocioacion en la anotación
     */
    public static final String ANOTACION_ES_PROPIETARORIO_ASOCIACION = "ANOTACION_ES_PROPIETARORIO_ASOCIACION";

    /**
     * Esta constante identifica el campo donde se introduce si el ciudadano es
     * el propiuetario en la asocioacion en la anotación
     */
    public static final String ANOTACION_PORCENTAJE_ASOCIACION = "ANOTACION_PORCENTAJE_ASOCIACION";

    /**
     * Esta constante identifica el estado de la anotación
     */
    public static final String ANOTACION_ESTADO = "ANOTACION_ESTADO";

    /**
     * Constantes que identifican las constantes para el folio
     */
    /**
     * Esta constante identifica el número de algun objeto que se quiere
     * eliminar
     */
    public static final String POSICION = "POSICION";

    /**
     * Esta constante identifica el número de la posición de la anotación que se
     * esta editando
     */
    public static final String POSICIONANOTACION = "POSICIONANOTACION";

    /**
     * Esta constante identifica la anotación que se esta editando
     */
    public static final String ANOTACION_EDITADA = "ANOTACION_EDITADA";

    /**
     * Esta constante identifica el prefijo utilizado para que el popup pueda
     * regrezar los valores a la ventana que lo abrio
     */
    public static final String FOLIO_LOCACION = "FOLIO_LOCACION";

    /**
     * Esta constante identifica el campo donde se guardara el identificador del
     * departamento
     */
    public static final String FOLIO_LOCACION_ID_DEPTO = "FOLIO_LOCACION_ID_DEPTO";

    /**
     * Esta constante identifica el campo donde se guardara el nombre del
     * departamento
     */
    public static final String FOLIO_LOCACION_NOM_DEPTO = "FOLIO_LOCACION_NOM_DEPTO";

    /**
     * Esta constante identifica el campo donde se guardara el identificador del
     * municipio
     */
    public static final String FOLIO_LOCACION_ID_MUNIC = "FOLIO_LOCACION_ID_MUNIC";

    /**
     * Esta constante identifica el campo donde se guardara el nombre del
     * municipio
     */
    public static final String FOLIO_LOCACION_NOM_MUNIC = "FOLIO_LOCACION_NOM_MUNIC";

    /**
     * Esta constante identifica el campo donde se guardara el identificador de
     * la vereda
     */
    public static final String FOLIO_LOCACION_ID_VEREDA = "FOLIO_LOCACION_ID_VEREDA";

    /**
     * Esta constante identifica el campo donde se guardara el nombre de la
     * vereda
     */
    public static final String FOLIO_LOCACION_NOM_VEREDA = "FOLIO_LOCACION_NOM_VEREDA";

    /**
     * Esta constante identifica el campo donde se introduce el estado del folio
     */
    public static final String FOLIO_ESTADO_FOLIO = "FOLIO_ESTADO_FOLIO";

    /**
     * Esta constante identifica el campo donde se introduce el estado del folio
     */
    public static final String FOLIO_ESTADO_FOLIO_COMENTARIO = "FOLIO_ESTADO_FOLIO_COMENTARIO";

    /**
     * Esta constante identifica el campo donde se introduce el tipo del predio
     * del folio
     */
    public static final String FOLIO_TIPO_PREDIO = "FOLIO_TIPO_PREDIO";

    /**
     * Esta constante identifica el campo donde se muestra el código del
     * documento
     */
    public static final String FOLIO_COD_DOCUMENTO = "FOLIO_COD_DOCUMENTO";

    /**
     * Esta constante identifica el campo donde se muestra la fecha de apertura
     */
    public static final String FOLIO_FECHA_APERTURA = "FOLIO_FECHA_APERTURA";

    /**
     * Esta constante identifica el campo donde se introduce el número del
     * codigo catastral del folio
     */
    public static final String FOLIO_COD_CATASTRAL = "FOLIO_COD_CATASTRAL";

    /**
     * Esta constante identifica el campo donde se introduce el eje del folio
     */
    public static final String FOLIO_EJE1 = "FOLIO_EJE1";

    /**
     * Esta constante identifica el campo donde se introduce el eje del folio
     */
    public static final String FOLIO_EJE2 = "FOLIO_EJE2";

    /**
     * Esta constante identifica el campo donde se introduce el valor de la
     * direccion del folio
     */
    public static final String FOLIO_VALOR1 = "FOLIO_VALOR1";

    /**
     * Esta constante identifica el campo donde se introduce el valor de la
     * direccion del folio
     */
    public static final String FOLIO_VALOR2 = "FOLIO_VALOR2";

    /**
     * Esta constante identifica el campo donde se introduce el complemento de
     * la direccion del folio
     */
    public static final String FOLIO_COMPLEMENTO_DIR = "FOLIO_COMPLEMENTO_DIR";

    /**
     * Esta constante identifica el campo donde se introduce la especificación
     * de la direccion del folio
     */
    public static final String FOLIO_ESPECIFICACION_DIR = "FOLIO_ESPECIFICACION_DIR";

    /**
     * Esta constante identifica el campo donde se introduce el número de la
     * radicacion del folio
     */
    public static final String FOLIO_NUM_RADICACION = "FOLIO_NUM_RADICACION";

    /**
     * Esta constante identifica el campo donde se introduce el lindero del
     * folio
     */
    public static final String FOLIO_LINDERO = "FOLIO_LINDERO";

    /**
     * Esta constante identifica el campo donde se introduce el comentario del
     * folio
     */
    public static final String FOLIO_COMENTARIO = "FOLIO_COMENTARIO";

    /**
     * Esta constante identifica el campo donde se introduce el nupre del folio
     */
    public static final String FOLIO_NUPRE = "FOLIO_NUPRE";

    /**
     * Esta constante identifica el campo donde se introduce el coeficiente del
     * folio
     */
    public static final String FOLIO_COEFICIENTE = "FOLIO_COEFICIENTE";

    /**
     * Esta constante identifica el campo donde se introduce la determinacion
     * del inmueble del folio
     */
    public static final String FOLIO_DETERMINACION_INMUEBLE = "FOLIO_DETERMINACION_INMUEBLE";

    /**
     * Esta constante identifica el campo donde se introduce la modalidad del
     * folio
     */
    public static final String FOLIO_MODALIDAD = "FOLIO_MODALIDAD";

    /**
     * Esta constante identifica el campo donde se introduce el avaluo del folio
     */
    public static final String FOLIO_AVALUO = "FOLIO_AVALUO";

    /**
     * Esta constante identifica el campo donde se introduce la fecha de avaluo
     * del folio
     */
    public static final String FOLIO_FECHA_AVALUO = "FOLIO_FECHA_AVALUO";

    /**
     * Esta constante identifica el campo donde se introduce la destinacion
     * economica del folio
     */
    public static final String FOLIO_DESTINACION_ECONOMICA = "FOLIO_DESTINACION_ECONOMICA";

    /**
     * Esta constante identifica el campo donde se introduce los linderos
     * definidos del folio
     */
    public static final String FOLIO_LINDEROS_DEFINIDOS = "FOLIO_LINDEROS_DEFINIDOS";

    /**
     * Esta constante identifica el campo donde se introduce los metros
     * correspondientes al area privada del folio
     */
    public static final String FOLIO_PRIVMETROS = "FOLIO_PRIVMETROS";

    /**
     * Esta constante identifica el campo donde se introduce los centimetros
     * correspondientes al area privada del folio
     */
    public static final String FOLIO_PRIVCENTIMETROS = "FOLIO_PRIVCENTIMETROS";

    /**
     * Esta constante identifica el campo donde se introduce los metros
     * correspondientes al area construida del folio
     */
    public static final String FOLIO_CONSMETROS = "FOLIO_CONSMETROS";

    /**
     * Esta constante identifica el campo donde se introduce los centimetros
     * correspondientes al area construida del folio
     */
    public static final String FOLIO_CONSCENTIMETROS = "FOLIO_CONSCENTIMETROS";

    /**
     * Esta constante identifica el campo donde se introduce los hectareas
     * correspondientes al area del terreno del folio
     */
    public static final String FOLIO_HECTAREAS = "FOLIO_HECTAREAS";

    /**
     * Esta constante identifica el campo donde se introduce los metros
     * correspondientes al area del terreno del folio
     */
    public static final String FOLIO_METROS = "FOLIO_METROS";

    /**
     * Esta constante identifica el campo donde se introduce los centimetros
     * correspondientes al area del terreno del folio
     */
    public static final String FOLIO_CENTIMETROS = "FOLIO_CENTIMETROS";
    
    
    /**
     * Esta constante almacena el permiso de Area
     * establecido en responsable correciones
     */
    public static final String PERMISO_AREA = "PERMISO_AREA";

    /**
     * Esta constante identifica el identificador de la anotación
     */
    public static final String ANOTACION_ID_ANOTACION = "ANOTACION_ID_ANOTACION";

    /**
     * Esta constante identifica el orden de la anotación
     */
    public static final String ANOTACION_ORDEN = "ANOTACION_ORDEN";

    /**
     * Esta constante permite redireccionar desde la edición de una anotación a
     * la edición de una cancelación
     */
    public static final String CONVERTIR_ANOTACION_CANCELACION = "CONVERTIR_ANOTACION_CANCELACION";

    /**
     * Esta constante identifica que la antotación canceladora se va a
     * convertira a estandar
     *
     */
    public static final String CONVERTIR_ANOTACION_ESTANDAR = "CONVERTIR_ANOTACION_ESTANDAR";

    public static final String FOLIO_COD_CATASTRAL_ANT = "FOLIO_COD_CATASTRAL_ANT";

    /**
     * Constante que indica que se van a agregar consultar los ultimos
     * propietarios de una folio*
     */
    public static final String GET_ULTIMOS_PROPIETARIOS = "GET_ULTIMOS_PROPIETARIOS";

    /**
     * Constante que indica que se van a agregar consultar los ultimos
     * propietarios de una folio*
     */
    public static final String LISTA_PROPIETARIOS_FOLIO = "LISTA_PROPIETARIOS_FOLIO";

    /**
     * Constante que indica que se van a agregar al la lista de ciudadanos los
     * propietarios de una folio*
     */
    public static final String GUARDAR_PROPIETARIOS = "GUARDAR_PROPIETARIOS";

    /**
     * Constante que indica que se cancela el proceso de consultar los ultimos
     * propietarios*
     */
    public static final String CANCELAR_PROPIETARIOS = "CANCELAR_PROPIETARIOS";

    public static final String CONVERTIR_CANCELADORA_ESTANDAR = "CONVERTIR_CANCELADORA_ESTANDAR";

    public static final String REFRESCAR_ANOTACION = "REFRESCAR_ANOTACION";

    public static final String SEGREGAR = "SEGREGAR";

    public static final String VISTA_EDICION_ANOTACION = "editar.anotacion.view";

    public static final String VISTA_SEGREGACION_ANOTACION_DEFINITIVA = "VISTA_SEGREGACION";

    /**
     * Aca se guarda la session global para la clase
     */
    private HttpSession session;

    /**
     * Variable donde se guarda la accion enviada en el request
     */
    private String accion;

    public static final String CONSULTAR_ANOTACIONES_FOLIO = "CONSULTAR_ANOTACIONES_FOLIO";

    public static final String CAMBIAR_ANOTACIONES_CORRECCION = "CAMBIAR_ANOTACIONES_CORRECCION";

    public static final String EDITAR_ANOTACION_CIUDADANO = "EDITAR_ANOTACION_CIUDADANO";

    public static final String EDITAR_ANOTACION_CIUDADANO_EDICION = "EDITAR_ANOTACION_CIUDADANO_EDICION";

    /**
     * ANOTACIONES ASOCIADAS Caso Mantis: 7275 Autor: Ellery Robles Gómez
     */
    public static final String ANOTACIONES_ASOCIADAS = "ANOTACIONES_ASOCIADAS";

    /**
     * Esta constante identifica el numero del folio
     */
    public static final String FOLIO_NUM_FOLIO = "FOLIO_NUM_FOLIO";

    /**
     * Esta constante identifica si hay anotaciones asociadas
     */
    public static final String EXISTEN_ANOTACIONES_ASOCIADAS = "EXISTEN_ANOTACIONES_ASOCIADAS";

    /**
     * Esta constante identifica si hay anotaciones asociadas
     */
    public static final String CONSULTA_ANOTACIONES_RESP = "CONSULTA_ANOTACIONES_RESP";

    /**
     * Esta constante identifica el tipo de cambio a la anotacion
     */
    public static final String TIPO_CAMBIO_ANOTACION = "TIPO_CAMBIO_ANOTACION";
    
    public static final String CARGAR_DESCRIPCION_NATURALEZA = "CARGAR_DESCRIPCION_NATURALEZA";

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
        // else if( .equals( accion ) ) {
        //
        //}

        session = request.getSession();
        accion = request.getParameter(WebKeys.ACCION);

        // basic break ---------------------------------------------------------------------
        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion válida");
        } // ---------------------------------------------------------------------------------
        //  edicion-folio.anotacion-canceladora.crear --------------------------------------
        else if (PAGE_ANOTACIONCANCELADORACREARSTEP0_BTNSTART_ACTION.equals(accion)) {
            return doCorr_FolioEdit_AnotacionCanceladoraCrear_Step0_BtnStart(request);
        } else if (PAGE_ANOTACIONCANCELADORACREARSTEP1_BTNBACK_ACTION.equals(accion)) {
            return doCorr_FolioEdit_AnotacionCanceladoraCrear_Step1_BtnBack(request);
        } else if (PAGE_ANOTACIONCANCELADORACREARSTEP1_BTNNEXT_ACTION.equals(accion)) {
            return doCorr_FolioEdit_AnotacionCanceladoraCrear_Step1_BtnNext(request);
        } // ---------------------------------------------------------------------------------
        //  edicion-folio.anotacion-canceladora.editar --------------------------------------
        else if (PAGE_ANOTACIONCANCELADORAEDITARSTEP0_BTNSTART_ACTION.equals(accion)) {
            return doCorr_FolioEdit_AnotacionCanceladoraEditar_Step0_BtnStart(request);
        } else if (PAGE_ANOTACIONCANCELADORAEDITARSTEP1_BTNBACK_ACTION.equals(accion)) {
            return doCorr_FolioEdit_AnotacionCanceladoraEditar_Step1_BtnBack(request);
        } else if (PAGE_ANOTACIONCANCELADORAEDITARSTEP1_BTNNEXT_ACTION.equals(accion)) {
            return doCorr_FolioEdit_AnotacionCanceladoraEditar_Step1_BtnNext(request);
        } // ---------------------------------------------------------------------------------
        //  edicion folio.padreshijos ----------------------------------------------
        else if (FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_ACTION.equals(accion)) {
            return doFolioEdit_PadresHijos_Padre_AddItem_Action(request);
        } else if (FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_ACTION.equals(accion)) {
            return doFolioEdit_PadresHijos_Padre_DelItem_Action(request);
        } else if (FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_ACTION.equals(accion)) {
            return doFolioEdit_PadresHijos_Hijo_AddItem_Action(request);
        } else if (FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_ACTION.equals(accion)) {
            return doFolioEdit_PadresHijos_Hijo_DelItem_Action(request);
        } else if (FOLIOEDIT_RELOADBYUPDATECIUDADANO_EDITANOTACIONNORMAL_PAGERENDERING_PROCESS_ACTION.equals(accion)) {
            return doFolioEdit_ReloadByUpdateCiudadano_Process_Action(request);
        } // ---------------------------------------------------------------------------------
        //  edicion folio-englobe/segregacion.anotacion ------------------------------------
        else if (PAGE_REGION__STARTANOTACIONSEGREGACION_ACTION.equals(accion)) {
            // TODO
            return doCorr_PageEvent_StartAnotacionSegregacion(request);
        } else if (PAGE_REGION__STARTANOTACIONENGLOBE_ACTION.equals(accion)) {
            // TODO
            return doCorr_PageEvent_StartAnotacionEnglobe(request);
        } else if (PAGE_REGION__FINISHANOTACIONSEGREGACION_ACTION.equals(accion)) {
            // TODO
            return doCorr_PageEvent_FinishAnotacionSegregacion(request);
        } else if (PAGE_REGION__FINISHANOTACIONENGLOBE_ACTION.equals(accion)) {
            // TODO
            return doCorr_PageEvent_FinishAnotacionEnglobe(request);
        } //  edicion folio-copiar.anotacion ------------------------------------
        else if (PAGE_REGION__STARTANOTACIONCOPIARANOTACION_ACTION.equals(accion)) {
            // TODO
            return doCorr_PageEvent_StartAnotacionCopiarAnotacion(request);
        } // ---------------------------------------------------------------------------------
        //  edicion folio.direccion normal ----------------------------------------------
        else if (FOLIOEDIT_DIRECCIONDEFINITIVA_DELITEM_ACTION.equals(accion)) {
            // return cancelarCancelacion(request);
            return doFolioEdit_DireccionDefinitiva_DelItem(request);
        } else if (FOLIOEDIT_DIRECCIONDEFINITIVA_EDITITEM_ACTION.equals(accion)) {
            // return cancelarCancelacion(request);
            return doFolioEdit_DireccionDefinitiva_EditItem(request);
        } // ---------------------------------------------------------------------------------
        //  creacion anotaciones normal ----------------------------------------------
        // varios ciudadanos
        else if (CRTANOTACIONNORMAL_CIUDADANO_ADDITEM.equals(accion)) {
            // return cancelarCancelacion(request);
            return doCiudadanoAddItem_AnotacionNormal(request);
        } else if (CRTANOTACIONNORMAL_CIUDADANO_DELITEM.equals(accion)) {
            // return cancelarCancelacion(request);
            return doCiudadanoDelItem_AnotacionNormal(request);
        } else if (CRTANOTACIONNORMAL_CIUDADANO_VALIDATEITEM.equals(accion)) {
            // return cancelarCancelacion(request);
            return doCiudadanoValidateItem_AnotacionNormal(request);
        } else if (CRTANOTACIONNORMAL_CIUDADANO_ADDREPEATER_1FIELD.equals(accion)) {
            // return cancelarCancelacion(request);
            return aumentarNumeroVariosCiudadanos(request);
        } else if (CRTANOTACIONNORMAL_CIUDADANO_DELREPEATER_1FIELD.equals(accion)) {
            return disminuirNumeroVariosCiudadanos(request);
        } // TODO: revisar si se necesita la de validar
        // ---------------------------------------------------------------------------------
        //  creacion anotaciones canceladoras ----------------------------------------------
        else if (CANCELAR_CANCELACION.equals(accion)) {
            return cancelarCancelacion(request);
        } else if (CANCELAR_ANOTACION.equals(accion)) {
            return cancelarAnotacion(request);
        } else if (REFRESCAR_CANCELACION.equals(accion)) {
            return refrescarCancelacion(request);
        } else if (CANCELAR.equals(accion)) {
            return cancelar(request);
        } // varios ciudadanos
        else if (AGREGAR_VARIOS_CIUDADANOS_CANCELACION.equals(accion)) {
            return agregarVariosCiudadanos(request, accion);
        } else if (AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION.equals(accion)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION.equals(accion)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (ELIMINAR_CIUDADANO_ANOTACION_CANCELACION.equals(accion)) {
            return eliminarCiudadano(request);
        } else if (VALIDAR_CIUDADANO_CANCELACION.trim().equals(accion)) { // simple correction
            return validarCiudadano(request);
        } // ---------------------------------------------------------------------------------
        //  edicion anotaciones canceladoras -----------------------------------------------
        // else if( EDITAR_CANCELACION.equals( accion ) ) {
        //    return doFolioEdit_AnotacionCancelacion_OnLoad( request );
        //    // return editarCancelacion(request);
        // }
        else if (GRABAR_EDICION_CANCELACION.equals(accion)) {
            // return grabarEdicionCancelacion(request);
            EvnCorreccion ev = doFolioEdit_AnotacionCancelacion_SaveChanges(request);
            ev.setCargarSalvedad(false);
            return (ev);
        } else if (CANCELAR_EDICION_CANCELACION.equals(accion)) {
            return cancelarEdicionCancelacion(request);
        } else if (REFRESCAR_EDICION_CANCELACION.equals(accion)) {
            return refrescarEdicionCancelacion(request);
        } // varios ciudadanos
        else if (AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION.equals(accion)) {
            return agregarVariosCiudadanosEdicion(request);
        } else if (AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION.equals(accion)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION.equals(accion)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION.equals(accion)) {
            return eliminarCiudadanoEdicion(request);
        } else if (VALIDAR_CIUDADANO_EDICION_CANCELACION.equals(accion)) {
            return validarCiudadano(request);
        } else if (CARGAR_DESCRIPCION_NATURALEZA_CANCELACION.equals(accion) || CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION.equals(accion)
                || CARGAR_DESCRIPCION_NATURALEZA.equals(accion)) {
            return cargarDescripcionNaturaleza(request);
        } // ---------------------------------------------------------------------------------
        //  edicion anotacion basica -------------------------------------------------------
        else if (ELIMINAR_ANOTACION_TEMPORAL.equals(accion)) {
            return eliminarAnotacionTemporal(request);
        } else if (ELIMINAR_CAMBIOS_ANOTACION_DEFINITIVA_TEMPORAL.equals(accion)) {
            return doProcess_CorreccionSimpleMain_EliminarCambiosAnotacionDefinitivaTemporal(request);
        } else if (REFRESCAR_EDICION.equals(accion)) {
            return refrescarEdicion(request);
        } else if (VALIDAR_CIUDADANO_EDICION.equals(accion)) {
            return validarCiudadano(request);
        } else if (EDITAR_ANOTACION.equals(accion)) {
            // edicion de anotacion 2
            return (do_Corr_PageEvent_FolioAnotacionXLoad(request));
        } else if (ELIMINAR_CIUDADANO_EDICION.equals(accion)) {
            // eiliminacion de ciudadano v2
            return doEliminarCiudadanoEdicion(request);
        } else if (ELIMINAR_CIUDADANO.equals(accion)) {
            return eliminarCiudadano(request);
        } else if (AGREGAR_VARIOS_CIUDADANOS_EDICION.equals(accion)) {
            return agregarVariosCiudadanosEdicion(request);
        } else if (EDITAR_ANOTACION_CIUDADANO.equals(accion)
                || EDITAR_ANOTACION_CIUDADANO_EDICION.equals(accion)) {
            return editarAnotacionCiudadano(request);
        } else if (AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION.equals(accion)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION.equals(accion)) {
            return disminuirNumeroVariosCiudadanos(request);
        } // ---------------------------------------------------------------------------------
        else if (ACEPTAR_EDICION_CORRECCION.equals(accion)) {
            return doFolioEdit_InfoFolio(request, accion);
            // return aceptarModificacionFolio(request, ACEPTAR_EDICION_CORRECCION);
        } else if (ACEPTAR_EDICION_ANOTACION.equals(accion)) {
            EvnCorreccion evento = doFolioEdit_Anotacion_SaveChanges(request);
            evento.setCargarSalvedad(false);
            return (evento);
            // return aceptarEdicionAnotacion(request);
        } else if (SEGREGAR.equals(accion)) {
            return SegregarAnotacionDefinitiva(request);
            // return aceptarEdicionAnotacion(request);
        } //  otros --------------------------------------------------------------------------
        else if (accion.equals(CARGAR_FOLIO_DIGITACION)) {
            return consultarFolioUsuario(request);
        } else if (accion.equals(CARGAR_FOLIO_CORRECCION)) {
            return cargarFolioCorreccion(request);
        } else if (accion.equals(CARGAR_FOLIO_ESPECIALIZADO)) {
            return cargarFolio(request);
        } else if (accion.equals(EDICION_FOLIO)) {
            return consultarFolioUsuario(request);
        } else if (accion.equals(AGREGAR_ANOTACION)) {
            EvnCorreccion evento = agregarAnotacion(request);
            evento.setCargarSalvedad(false);
            return evento;
        } else if (accion.equals(ELIMINAR_ANOTACION)) {
            return eliminarAnotacion(request);
        } else if (accion.equals(CANCELAR_ANOTACION_SESSION)) {
            return cancelarAnotacionSession(request);
        } else if (accion.equals(AGREGAR_DIRECCION_CORRECCION)) {
            return agregarDireccion(request, AGREGAR_DIRECCION_CORRECCION);

        } else if (accion.equals(EDITAR_DIRECCION_CORRECCION_TEMP)) {
            return editarDireccion(request);
        } else if (accion.equals(EDITAR_DIRECCION_CORRECCION_ACEPTAR_TEMP)) {  //
            return editarDireccionAceptar(request, EDITAR_DIRECCION_CORRECCION_ACEPTAR_TEMP);
        } else if (accion.equals(EDITAR_DIRECCION_CORRECCION_ACEPTAR_DEF)) {  //
            return editarDireccionAceptar(request, EDITAR_DIRECCION_CORRECCION_ACEPTAR_DEF);

        } else if (accion.equals(ELIMINAR_DIRECCION_CORRECCION)) {
            return eliminarDireccion(request);
        } else if (accion.equals(AGREGAR_DIRECCION_DIGITACION)) {
            return agregarDireccion(request, AGREGAR_DIRECCION_DIGITACION);
        } else if (accion.equals(ELIMINAR_DIRECCION_DIGITACION)) {
            return eliminarDireccion(request);
        } else if (accion.equals(AGREGAR_DIRECCION_ESPECIALIZADO)) {
            return agregarDireccion(request, AGREGAR_DIRECCION_ESPECIALIZADO);
        } else if (accion.equals(ELIMINAR_DIRECCION_ESPECIALIZADO)) {
            return eliminarDireccion(request);
        } else if (accion.equals(AGREGAR_DIRECCION_EDICION)) {
            return agregarDireccion(request, AGREGAR_DIRECCION_EDICION);
        } else if (accion.equals(ELIMINAR_DIRECCION_EDICION)) {
            return eliminarDireccion(request);
        } else if (accion.equals(AGREGAR_CIUDADANO)) {
            return agregarCiudadano(request);
        } else if (accion.equals(GUARDAR_CAMBIOS_FOLIO)) {
            return guardarCambiosFolio(request);
        } else if (accion.equals(CANCELAR_EDICION_CORRECCION)) {
            return cancelarModificacionFolio(request);
        } else if (accion.equals(ACEPTAR_EDICION_ESPECIALIZADO)) {
            return aceptarModificacionFolio(request, ACEPTAR_EDICION_ESPECIALIZADO);
        } else if (accion.equals(CANCELAR_EDICION_ESPECIALIZADO)) {
            return cancelarModificacionFolio(request);
        } else if (accion.equals(ACEPTAR_EDICION_DIGITACION)) {
            return aceptarModificacionFolio(request, ACEPTAR_EDICION_DIGITACION);
        } else if (accion.equals(CANCELAR_EDICION_DIGITACION)) {
            return cancelarModificacionFolio(request);
        } else if (accion.equals(CARGAR_ANOTACION)) {
            return cargarAnotacion(request);
        } else if (accion.equals(CARGAR_ANOTACION_AGREGAR)) {
            return cargarAnotacionAgregar(request);
        } else if (accion.equals(AGREGAR_CIUDADANO_ANOTACION)) {
            return agregarCiudadanoAnotacion(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION)) {
            return eliminarCiudadanoAnotacion(request);
        } else if (accion.equals(EDITAR_CIUDADANO_ANOTACION)) {
            return editarCiudadano(request);
        } else if (accion.equals(CANCELAR_EDICION_ANOTACION)) {
            return cancelarEdicionAnotacion(request);
        } else if (accion.equals(PRESERVAR_INFO)) {
            return preservarInfo(request);
        } else if (accion.equals(CONSULTA_DIGITACION_MASIVA)) {
            return consultaFolioDigitacionMasiva(request);
        } else if (accion.equals(EDITAR_FOLIO_DIGITACION_MASIVA)) {
            return editarFoliosDigitacionMasiva(request);
        } else if (accion.equals(CONVERTIR_ANOTACION_CANCELACION)) {
            session.removeAttribute(MODIFICA_DEFINITIVA);
            return null;
        } else if (accion.equals(GET_ULTIMOS_PROPIETARIOS)) {
            return consultarUltimosPropietarios(request);
        } else if (accion.equals(CANCELAR_PROPIETARIOS)) {
            return cancelarPropietario(request);
        } else if (accion.equals(GUARDAR_PROPIETARIOS)) {
            return guardarPropietario(request);
        } else if (accion.equals(CONVERTIR_ANOTACION_ESTANDAR)) {
            return convertirCanceladoraAEstandar(request);
        } else if (accion.equals(REFRESCAR_ANOTACION)) {
            return refrescarAnotacion(request);
        } else if (CONSULTAR_ANOTACIONES_FOLIO.equals(accion)) {
            return consultarAnotacionesFolio(request);
        } else if (CAMBIAR_ANOTACIONES_CORRECCION.equals(accion)) {
            return cambiarAnotacionesCorreccion(request);
        } else if (ANOTACIONES_ASOCIADAS.equals(accion)) {
            return anotacionesAsociadas(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion + " no es válida.");
        }
        // ---------------------------------------------------------------------------------
        // return null;
    }
    
    private Evento editarAnotacionCiudadano(HttpServletRequest request) {
        // TODO Auto-generated method stub
        String posicion = request.getParameter(WebKeys.POSICION);
        int pos = Integer.parseInt(posicion);
        String rol = request.getParameter(CAnotacionCiudadano.ANOTACION_TIPO_INTER_ASOCIACION_EDIT + posicion);
        String marcaProp = request.getParameter(CAnotacionCiudadano.ANOTACION_ES_PROPIETARIO_ASOCIACION_EDIT + posicion);
        String participacion = request.getParameter(CAnotacionCiudadano.ANOTACION_PORCENTAJE_ASOCIACION_EDIT + posicion);

        List anotacionCiudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        if (pos < anotacionCiudadanos.size()) {
            AnotacionCiudadano anotaCiud = (AnotacionCiudadano) anotacionCiudadanos.get(pos);
            if (rol != null);
            anotaCiud.setRolPersona(rol);
            if (marcaProp == null || marcaProp.equals("")) {
                anotaCiud.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
            } else if (marcaProp.equals("X")) {
                anotaCiud.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
            } else if (marcaProp.equals("I")) {
                anotaCiud.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
            }
            anotaCiud.setParticipacion(participacion != null ? participacion : "");
            anotaCiud.setToUpdate(true);
            session.setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, anotacionCiudadanos);
        }

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento refrescarAnotacion(HttpServletRequest request) {
        //eliminarInfoBasicaAnotacion(request);
        String ver = request.getParameter("SECUENCIA");
        request.getSession().setAttribute("SECUENCIA", ver);
        request.getSession().setAttribute("CAMBIO", "SI");
        preservarInfoAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);
        return null;
    }

    private Evento convertirCanceladoraAEstandar(HttpServletRequest request)
            throws AccionWebException {
        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoAnotacion(request);
        preservarInfoCancelacion(request);

        request.getSession().setAttribute(CONVERTIR_CANCELADORA_ESTANDAR, CONVERTIR_CANCELADORA_ESTANDAR);
        request.getSession().setAttribute("cancelar", "false");

        return null;
    }

    private NaturalezaJuridica getNaturalezaJuridicaById(String idNatuJuri) {
        NaturalezaJuridica natJuridica = null;
        List grupoNaturalezas = null;

        grupoNaturalezas = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);

        naturalezas:
        for (Iterator grupoNatItera = grupoNaturalezas.iterator();
                grupoNatItera.hasNext();) {
            GrupoNaturalezaJuridica grupoNat = (GrupoNaturalezaJuridica) grupoNatItera.next();
            List naturalezasGrupo = grupoNat.getNaturalezaJuridicas();
            for (Iterator natuJurItera = naturalezasGrupo.iterator();
                    natuJurItera.hasNext();) {
                NaturalezaJuridica natuJuriAux = (NaturalezaJuridica) natuJurItera.next();
                if (natuJuriAux.getIdNaturalezaJuridica().equals(idNatuJuri)) {
                    natJuridica = natuJuriAux;
                    natJuridica.setGrupoNaturalezaJuridica(grupoNat);
                    break naturalezas;
                }
            }
        }

        return natJuridica;
    }
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// bug 3562 ---------------------------------------------------------------------

    private Evento
            doCorr_FolioEdit_AnotacionCanceladoraCrear_Step0_BtnStart(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        session.removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        session.removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        session.removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        session.removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        session.removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        session.removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        /*obtencion de DatosRespuestaPaginador para saber si hay un paginador activo y sacar los datos de este*/
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);

            ArrayList list = new ArrayList();
            if (RPag != null && RPag.getAnotacionesActual() != null) {
                Iterator it = RPag.getAnotacionesActual().iterator();

                int i = 0;
                while (it.hasNext()) {
                    Object obj = (Object) it.next();
                    list.add(obj);
                }
            }
            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, list);

        }

        return null;

    } // end method: doCorr_FolioEdit_AnotacionCanceladoraCrear_Step0

    private Evento
            doCorr_FolioEdit_AnotacionCanceladoraCrear_Step1_BtnBack(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        session.removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);

        return null;

    } // end method: doCorr_FolioEdit_AnotacionCanceladoraCrear_Step1_BtnBack

    private Evento
            doCorr_FolioEdit_AnotacionCanceladoraCrear_Step1_BtnNext(HttpServletRequest request)
            throws AccionWebException {
        // capturar el set de anotaciones canceladoras seleccionadas

        HttpSession session = request.getSession();

        //	bug 10371:
        // must be collected in the paginator
        final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID = "TARGET_SELECTIONCOLLECTOR";

        String idsAnotacionesCsv = request.getParameter(LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID);

        // only the last is added
        String[] idsAnotaciones = gov.sir.core.web.helpers.comun.JvLocalUtils.csvStringToStringArray(idsAnotacionesCsv, gov.sir.core.web.helpers.comun.JvLocalUtils.DEFAULT_SEPARATOR, true);
        ValidacionAnotacionesCancelacionException exception = new ValidacionAnotacionesCancelacionException();

        //String[] idsAnotaciones = request.getParameterValues("ESCOGER_ANOTACION_CANCELACION");
        //ValidacionAnotacionesCancelacionException exception = new ValidacionAnotacionesCancelacionException();
        if (idsAnotaciones == null) {
            exception.addError("Debe seleccionar por lo menos una anotación para cancelar");
            throw exception;
        }
        session.setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, idsAnotaciones);

        return null;

    } // end method: doCorr_FolioEdit_AnotacionCanceladoraCrear_Step1_BtnNext

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
    /**
     * doFolioEdit_PadresHijos_Hijo_DelItem_Action
     *
     * @param request HttpServletRequest
     */
    private EvnCorreccion
            doFolioEdit_PadresHijos_Hijo_DelItem_Action(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        this.preservarInfo(request);

        // Cargar los permisos de la sesion
        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager
                = doExtractAuthorization_ProcessManager(request);

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosException();

        // Cargar el estado t0 de los objetos
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (null == turno) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == t0_folio) {
            t0_folio = new Folio();
        }
        // cargar id de matricula actual
        // String idMatricula = t0_folio.getIdMatricula();

        // cargar la lista de padres (no es necesario cargar la lista de hijos)
        // el fin de esta carga es obtener solo la zona registral
        java.util.List t0_folio_hijos = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_HIJO);
        if (null == t0_folio_hijos) {
            t0_folio_hijos = new java.util.ArrayList();
        }

        // target: [padre] (this)
        // source: [hijo ] (param)
        // ------------------------------------------------------
        //
        // capturar los parametros de la forma:
        String param_Item_Folio_IdMatricula = null;
        String param_SalvedadFolio_Id; //Id de la salvedad de modificación del folio
        String param_SalvedadFolio_Descripcion; //Descripción de la salvedad de modificación del folio

        // a partir de este dato, obtener el conjunto de datos necesarios para saber el padre a eliminar;
        // primero se debe tener en cuenta que es un checkbox, asi que se debe seleccinar un solo valor
        String[] param_Selected_Folio_IdMatriculas;

        param_Selected_Folio_IdMatriculas = request.getParameterValues("FOLIOEDIT_PADRESHIJOS_HIJO__ITEMS");
        param_SalvedadFolio_Id = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID);
        param_SalvedadFolio_Descripcion = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION);

        if (null == param_Selected_Folio_IdMatriculas || param_Selected_Folio_IdMatriculas.length <= 0) {
            exception.addError("id-matricula: debe seleccionar los items a eliminar");
        }

        // buscar en la lista de folios hijo el folio seleccionado
        // usar un comparador parqa las busquedas
        BasicAbstractComparator comparator = new BasicStringComparator(true, true);

        List selectedFolios = new ArrayList();

        for (java.util.Iterator iterator = t0_folio_hijos.iterator(); iterator.hasNext();) {
            Folio element = (Folio) iterator.next();

            if (null == element) {
                continue;
            }

            String element_idMatricula = element.getIdMatricula();

            for (int i = 0; i < param_Selected_Folio_IdMatriculas.length; i++) {
                param_Item_Folio_IdMatricula = param_Selected_Folio_IdMatriculas[i];

                if (comparator.compare(param_Item_Folio_IdMatricula, element_idMatricula) == 0) {
                    // elementFounded
                    selectedFolios.add(element);
                    break;
                }
            }
        }

        if (selectedFolios.isEmpty()) {
            // throw exception
            // elemento no encontrado dentro de la lista de folios-hijo
            exception.addError("id-matricula: no se encontro los items seleccionado");

        }

        BasicConditionalValidator stage1_validator;

        // 1: validar salvedad
        // 1:A not null
        stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; debe escribir un valor en la salvedad");
        }

        // 1:B maxima longitud
        stage1_validator = new BasicStringMaxLengthValidatorWrapper(255);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener menos de 255 caracteres");
        }

        // 1:C minima longitud
        stage1_validator = new BasicStringMinLengthValidatorWrapper(30);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener mas de 30 caracteres");
        }

        // TODO: en este momento el folioSelected no
        // tiene a sus anotaciones; por esto las busquedas se deben efectuar sobre t0_folio
        // se buscan los parametros dependientes del folio seleccionado
        // 1: zonaRegistral
        //param_Item_Folio_IdZonaRegistral = selectedFolio.getZonaRegistral().getIdZonaRegistral();
        // ------------------------------------------------------
        // raise application error
        if (exception.getErrores().size() > 0) {
            ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
            throw ex1;
        }
        // ------------------------------------------------------

        // ------------------------------------------------------
        Anotacion source = null; // hijo
        Anotacion target = null; // padre

        // ------------------------------------------------------
        target = new Anotacion();

        target.setIdAnotacion(null); // no se conoce la anotacion por la cual se enlaza a param_folio // FolioDerivado.idAnotacion
        target.setIdMatricula(t0_folio.getIdMatricula()); // FolioDerivado.idMatricula1

        // en el folio derivado estan los datos de la anotacion origen;
        // con ellos completar los datos que faltaban:
        List sources = new ArrayList();
        for (Iterator folSelItera = selectedFolios.iterator();
                folSelItera.hasNext();) {
            Folio selFolio = (Folio) folSelItera.next();
            source = new Anotacion();
            source.setIdAnotacion(null); // no se la anotacion
            source.setIdMatricula(selFolio.getIdMatricula()); // seleccionada
            sources.add(source);
        }

        SalvedadFolio salvedadFolio = new SalvedadFolio();
        salvedadFolio.setIdSalvedad(param_SalvedadFolio_Id);
        salvedadFolio.setDescripcion(param_SalvedadFolio_Descripcion);
        salvedadFolio.setIdMatricula(t0_folio.getIdMatricula());
        salvedadFolio.setNumRadicacion(turno.getIdWorkflow());

        List salvsFolio = new ArrayList();
        salvsFolio.add(salvedadFolio);
        t0_folio.setSalvedades(salvsFolio);

        // validar que folio exista
        // validar que folio.anotacion exista
        // generar evento para pasar parametros a negocio
        EvnCorreccion result = null;

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        // wrap results
        // TODO: en negocio:
        // 1. encontrar la anotacion que relaciona a target(thisFolio) con source(paramFolio)
        // 2. si se encuentra esta relacion es que paramFolio esta dentro de los hijos.
        EvnCorreccionFolioPadresHijos resultWrap;
        resultWrap = new EvnCorreccionFolioPadresHijos(
                usuarioAuriga,
                usuarioSIR,
                t0_folio,
                turno,
                EvnCorreccionFolioPadresHijos.FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_EVENT // EvnCorreccion.EDITAR_FOLIO
        );

        resultWrap.setSources(sources);
        resultWrap.setSource(source);
        resultWrap.setTarget(target);
        resultWrap.setActionName(EvnCorreccionFolioPadresHijos.ACTION_DELITEM);
        resultWrap.setRelationName(EvnCorreccionFolioPadresHijos.RELATION_PADRESHIJOS_HIJO);

        result = resultWrap;
        return result;

    }

    private void
            doEnd_FolioEdit_ReloadByUpdateCiudadano_Process_Action(HttpServletRequest request) {
        // TODO: implement
    }

    // -------------------------------------------------------------------------
    private EvnCorreccion
            doFolioEdit_ReloadByUpdateCiudadano_Process_Action(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        // generar evento para pasar parametros a negocio
        EvnCorreccion result = null;

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        String param_UpdateResult = request.getParameter("RELOAD_DATA");
        if ((null != param_UpdateResult)
                && ("".equals(param_UpdateResult.trim()))) {
            //TODO: reload is enabled;
            return cargarFolio(request);
        }

        // wrap results
        // TODO: en negocio:
        // 1. encontrar la anotacion que relaciona a target(thisFolio) con source(paramFolio)
        // 2. si se encuentra esta relacion es que paramFolio esta dentro de los hijos.

        /*

        EvnCorreccionFolioPadresHijos resultWrap;
        resultWrap = new EvnCorreccionFolioPadresHijos(
           usuarioAuriga
         , usuarioSIR
         , t0_folio
         , turno
         , EvnCorreccionFolioPadresHijos.FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_EVENT // EvnCorreccion.EDITAR_FOLIO
        );

        resultWrap.setSource( source );
        resultWrap.setTarget( target );
        resultWrap.setActionName( EvnCorreccionFolioPadresHijos.ACTION_DELITEM );
        resultWrap.setRelationName( EvnCorreccionFolioPadresHijos.RELATION_PADRESHIJOS_HIJO );

        result = resultWrap;
        return result;

         */
        return null;

    }

    // -------------------------------------------------------------------------
    /**
     * doFolioEdit_PadresHijos_Hijo_AddItem_Action
     *
     * @param request HttpServletRequest
     */
    private EvnCorreccion
            doFolioEdit_PadresHijos_Hijo_AddItem_Action(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        this.preservarInfo(request);
        // Cargar los permisos de la sesion

        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager
                = doExtractAuthorization_ProcessManager(request);

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosException();
        // Cargar el estado t0 de los objetos

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (null == turno) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == t0_folio) {
            t0_folio = new Folio();
        }
        // cargar id de matricula actual
        // String idMatricula = t0_folio.getIdMatricula();

        // cargar la lista de padres (no es necesario cargar la lista de hijos)
        // el fin de esta carga es ver si el item a agregar no se encuentra inscrito como padre
        java.util.List t0_folio_hijos = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_HIJO);
        if (null == t0_folio_hijos) {
            t0_folio_hijos = new java.util.ArrayList();
        }

        // target: [padre] (this)
        // source: [hijo ] (param)
        // ------------------------------------------------------
        // capturar los parametros de la forma:
        String param_Item_Folio_IdMatricula;
        String param_Item_Folio_IdAnotacion; // se debe buscar dentro de los folios padre = request.getParameter( CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID );
        String param_Item_Folio_IdZonaRegistral; // se debe colocar cuando se tenga el folio padre
        String param_Item_Folio_ThisIdAnotacion; // anotacion en el folio editado mediante la cuan lse hace el enlace con una anotacion en el otro folio
        String param_SalvedadFolio_Id; //Id de la salvedad de modificación del folio
        String param_SalvedadFolio_Descripcion; //Descripción de la salvedad de modificación del folio

        // capturar los parametros de la forma: (escritos)
        param_Item_Folio_IdMatricula = request.getParameter(CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA);
        param_Item_Folio_IdAnotacion = request.getParameter(CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID);
        param_Item_Folio_ThisIdAnotacion = request.getParameter(CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID);
        param_SalvedadFolio_Id = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID);
        param_SalvedadFolio_Descripcion = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION);

        // se asume zona registral igual a la del folio editado
        param_Item_Folio_IdZonaRegistral = t0_folio.getZonaRegistral().getIdZonaRegistral();

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
        validator = new BasicStringPatternValidatorWrapper(t0_folio.getIdMatricula(), true, true);
        validator.setObjectToValidate(param_Item_Folio_IdMatricula);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            //
            exception.addError("id-matricula: la matricula indicada es la misma que el folio origen");
        }

        BasicConditionalValidator stage1_validator;

        // 1: validar salvedad
        // 1:A not null
        stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; debe escribir un valor en la salvedad");
        }

        // 1:B maxima longitud
        stage1_validator = new BasicStringMaxLengthValidatorWrapper(255);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener menos de 255 caracteres");
        }

        // 1:C minima longitud
        stage1_validator = new BasicStringMinLengthValidatorWrapper(30);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener mas de 30 caracteres");
        }

        // para realizar busquedas
        StringBuffer jxpathQueryBuffer;
        JXPathContext searchContext;
        FolioDerivado targetLink;
        Iterator iterator;

        // // consultas dentro de this folio
        // JXPathContext searchContext = JXPathContext.newContext( t0_folio );
        // searchContext.setLenient( true );
        //
        // // TODO: a negocio:
        // // 1. probar si existe alguna anotacion dentro de thisFolio
        // //    que referencie al folio recibido como parametro dentro de los folios hijo. Si
        // //    lo estaba, throw exception (inicialmente no importa la anotacion indicada y la zona registral indicada)
        // TODO: a negocio
        // // 2. probar si existe alguna anotacion dentro de thisFolio
        // //    que referencie al folio recibido como parametro dentro de las anotaciones padre. Si
        // //    si existe, throw exception: un padre no puede ser a la vez un hijo del mosmo folio
        //
        // ------------------------------------------------------
        // raise application error
        if (exception.getErrores().size() > 0) {
            ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
            throw ex1;
        }
        // ------------------------------------------------------

        // el contexto en este caso es el t0_folio; el folio tiene anotaciones que tienen
        // foliosHijo, foliosPadre de tipo List<FolioDerivado>
        // ------------------------------------------------------
        Anotacion source = null; // source: [hijo ] (param)
        Anotacion target = null; // target: [padre] (this)

        // ------------------------------------------------------
        source = new Anotacion();

        source.setIdMatricula(param_Item_Folio_IdMatricula);
        source.setIdAnotacion(param_Item_Folio_IdAnotacion);

        // ------------------------------------------------------
        // a partir del folio derivado construir una anotacion:
        target = new Anotacion();
        target.setIdAnotacion(param_Item_Folio_ThisIdAnotacion); // se recibe como parametro // no se puede saber que anotacion se referencia si no se esta seguro que exista el folio source
        // inicialmente colocar el valor de la primera anotacion de tipo derivada que tenga esa matricula
        target.setIdMatricula(t0_folio.getIdMatricula());

        SalvedadFolio salvedadFolio = new SalvedadFolio();
        salvedadFolio.setIdSalvedad(param_SalvedadFolio_Id);
        salvedadFolio.setDescripcion(param_SalvedadFolio_Descripcion);
        salvedadFolio.setIdMatricula(t0_folio.getIdMatricula());
        salvedadFolio.setNumRadicacion(turno.getIdWorkflow());

        List salvsFolio = new ArrayList();
        salvsFolio.add(salvedadFolio);
        t0_folio.setSalvedades(salvsFolio);

        // configurar source:(folio, anotacion)
        // validar que folio exista
        // validar que folio.anotacion exista
        // configurar target:(folio, anotacion)
        // generar evento para pasar parametros a negocio
        EvnCorreccion result = null;

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        // wrap results
        EvnCorreccionFolioPadresHijos resultWrap;
        resultWrap = new EvnCorreccionFolioPadresHijos(
                usuarioAuriga,
                usuarioSIR,
                t0_folio,
                turno,
                EvnCorreccionFolioPadresHijos.FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_EVENT // EvnCorreccion.EDITAR_FOLIO
        );

        resultWrap.setSource(source);
        resultWrap.setTarget(target);
        resultWrap.setActionName(EvnCorreccionFolioPadresHijos.ACTION_ADDITEM);
        resultWrap.setRelationName(EvnCorreccionFolioPadresHijos.RELATION_PADRESHIJOS_HIJO);

        result = resultWrap;
        return result;

    }

    /**
     * doFolioEdit_PadresHijos_Padre_DelItem_Action
     *
     * @param request HttpServletRequest
     */
    private EvnCorreccion
            doFolioEdit_PadresHijos_Padre_DelItem_Action(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        this.preservarInfo(request);
        // Cargar los permisos de la sesion

        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager
                = doExtractAuthorization_ProcessManager(request);

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosException();

        // Cargar el estado t0 de los objetos
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (null == turno) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == t0_folio) {
            t0_folio = new Folio();
        }
        // cargar id de matricula actual
        // String idMatricula = t0_folio.getIdMatricula();

        // cargar la lista de padres (no es necesario cargar la lista de hijos)
        // el fin de esta carga es obtener solo la zona registral
        java.util.List t0_folio_padres = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
        if (null == t0_folio_padres) {
            t0_folio_padres = new java.util.ArrayList();
        }

        // target: [hijo ] (this)
        // source: [padre] (param)
        // ------------------------------------------------------
        //
        // capturar los parametros de la forma:
        String param_Item_Folio_IdMatricula = null;
        String param_Item_Folio_IdAnotacion = null; // se debe buscar dentro de los folios padre = request.getParameter( CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID );
        String param_Item_Folio_IdZonaRegistral = null; // se debe colocar cuando se tenga el folio padre
        String param_SalvedadFolio_Id; //Id de la salvedad de modificación del folio
        String param_SalvedadFolio_Descripcion; //Descripción de la salvedad de modificación del folio

        // a partir de este dato, obtener el conjunto de datos necesarios para saber el padre a eliminar;
        // primero se debe tener en cuenta que es un checkbox, asi que se debe seleccinar un solo valor
        String[] param_Selected_Folio_IdMatriculas;

        param_Selected_Folio_IdMatriculas = request.getParameterValues("FOLIOEDIT_PADRESHIJOS_PADRE__ITEMS");
        param_SalvedadFolio_Id = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID);
        param_SalvedadFolio_Descripcion = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION);

        if (null == param_Selected_Folio_IdMatriculas || param_Selected_Folio_IdMatriculas.length <= 0) {
            exception.addError("id-matricula: debe seleccionar los items a eliminar");
        }

        if (exception.getErrores().size() > 0) {
            ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
            throw ex1;
        }

        // por el momento, permitir eliminar de a un solo item
        // para tener claridad en los errores que han surgido.
        param_Item_Folio_IdMatricula = param_Selected_Folio_IdMatriculas[0];

        BasicConditionalValidator validator;

        // comparar si es la cadena vacia o nulo;
        validator = new BasicStringNotNullOrEmptyValidatorWrapper();

        validator.setObjectToValidate(param_Item_Folio_IdMatricula);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-matricula: no se ha seleccionado una cadena valida");
        }

        // buscar en la lista de folios padre el folio seleccionado
        // usar un comparador parqa las busquedas
        Folio selectedFolio = null;

        BasicAbstractComparator comparator = new BasicStringComparator(true, true);

        List selectedFolios = new ArrayList();
        for (java.util.Iterator iterator = t0_folio_padres.iterator(); iterator.hasNext();) {
            Folio element = (Folio) iterator.next();

            if (null == element) {
                continue;
            }

            String element_idMatricula = element.getIdMatricula();

            for (int i = 0; i < param_Selected_Folio_IdMatriculas.length; i++) {
                param_Item_Folio_IdMatricula = param_Selected_Folio_IdMatriculas[i];

                if (comparator.compare(param_Item_Folio_IdMatricula, element_idMatricula) == 0) {
                    // elementFounded
                    selectedFolios.add(element);
                    break;
                }
            }
        }

        if (selectedFolios.isEmpty()) {
            // throw exception
            // elemento no encontrado dentro de la lista de folios-hijo
            exception.addError("id-matricula: no se encontro los items seleccionado");

        }

        BasicConditionalValidator stage1_validator;

        // 1: validar salvedad
        // 1:A not null
        stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; debe escribir un valor en la salvedad");
        }

        // 1:B maxima longitud
        stage1_validator = new BasicStringMaxLengthValidatorWrapper(255);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener menos de 255 caracteres");
        }

        // 1:C minima longitud
        stage1_validator = new BasicStringMinLengthValidatorWrapper(30);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener mas de 30 caracteres");
        }

        // TODO: en este momento el folioSelected no
        // tiene a sus anotaciones; por esto las busquedas se deben efectuar sobre t0_folio
        // se buscan los parametros dependientes del folio seleccionado
        // 1: zonaRegistral
        //param_Item_Folio_IdZonaRegistral = selectedFolio.getZonaRegistral().getIdZonaRegistral();
        // ------------------------------------------------------
        // raise application error
        if (exception.getErrores().size() > 0) {
            ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
            throw ex1;
        }
        // ------------------------------------------------------

        // validations stage 2
        exception = new ValidacionParametrosException();

        // ------------------------------------------------------
        // raise application error
        if (exception.getErrores().size() > 0) {
            ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
            throw ex1;
        }
        // ------------------------------------------------------

        // ------------------------------------------------------
        Anotacion source = null; // padre
        Anotacion target = null; // hijo

        // ------------------------------------------------------
        target = new Anotacion();

        target.setIdAnotacion(null); // no se conoce la anotacion por la cual se enlaza a param_folio // FolioDerivado.idAnotacion
        target.setIdMatricula(t0_folio.getIdMatricula()); // FolioDerivado.idMatricula1

        // en el folio derivado estan los datos de la anotacion origen;
        // con ellos completar los datos que faltaban:
        List sources = new ArrayList();
        for (Iterator folSelItera = selectedFolios.iterator();
                folSelItera.hasNext();) {
            Folio selFolio = (Folio) folSelItera.next();
            source = new Anotacion();
            source.setIdAnotacion(null); // no se la anotacion
            source.setIdMatricula(selFolio.getIdMatricula()); // seleccionada
            sources.add(source);
        }

        // validar que folio exista
        // validar que folio.anotacion exista
        SalvedadFolio salvedadFolio = new SalvedadFolio();
        salvedadFolio.setIdSalvedad(param_SalvedadFolio_Id);
        salvedadFolio.setDescripcion(param_SalvedadFolio_Descripcion);
        salvedadFolio.setIdMatricula(t0_folio.getIdMatricula());
        salvedadFolio.setNumRadicacion(turno.getIdWorkflow());

        List salvsFolio = new ArrayList();
        salvsFolio.add(salvedadFolio);
        t0_folio.setSalvedades(salvsFolio);

        // generar evento para pasar parametros a negocio
        EvnCorreccion result = null;

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        // wrap results
        // TODO: en negocio:
        // 1. encontrar la anotacion que relaciona a target(thisFolio) con source(paramFolio)
        // 2. si se encuentra esta relacion es que paramFolio esta dentro de los hijos.
        EvnCorreccionFolioPadresHijos resultWrap;
        resultWrap = new EvnCorreccionFolioPadresHijos(
                usuarioAuriga,
                usuarioSIR,
                t0_folio,
                turno,
                EvnCorreccionFolioPadresHijos.FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_EVENT // EvnCorreccion.EDITAR_FOLIO
        );

        resultWrap.setSources(sources);
        resultWrap.setTarget(target);
        resultWrap.setActionName(EvnCorreccionFolioPadresHijos.ACTION_DELITEM);
        resultWrap.setRelationName(EvnCorreccionFolioPadresHijos.RELATION_PADRESHIJOS_PADRE);

        result = resultWrap;
        return result;

    } // :doFolioEdit_PadresHijos_Padre_DelItem_Action

    /**
     * doFolioEdit_PadresHijos_Padre_AddItem_Action
     *
     * @param request HttpServletRequest
     */
    private EvnCorreccion
            doFolioEdit_PadresHijos_Padre_AddItem_Action(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        this.preservarInfo(request);
        // Cargar los permisos de la sesion

        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager
                = doExtractAuthorization_ProcessManager(request);

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosException();
        // Cargar el estado t0 de los objetos

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (null == turno) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == t0_folio) {
            t0_folio = new Folio();
        }
        // cargar id de matricula actual
        // String idMatricula = t0_folio.getIdMatricula();

        // cargar la lista de padres (no es necesario cargar la lista de hijos)
        // el fin de esta carga es ver si el item a agregar no se encuentra inscrito como padre
        java.util.List t0_folio_padres = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
        if (null == t0_folio_padres) {
            t0_folio_padres = new java.util.ArrayList();
        }

        // target: [hijo ] (this)
        // source: [padre] (param)
        // ------------------------------------------------------
        // capturar los parametros de la forma:
        String param_Item_Folio_IdMatricula;
        String param_Item_Folio_IdAnotacion; // se debe buscar dentro de los folios padre = request.getParameter( CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID );
        String param_Item_Folio_IdZonaRegistral; // se debe colocar cuando se tenga el folio padre
        String param_Item_Folio_ThisIdAnotacion; // anotacion en el folio editado mediante la cuan lse hace el enlace con una anotacion en el otro folio
        String param_SalvedadFolio_Id; //Id de la salvedad de modificación del folio
        String param_SalvedadFolio_Descripcion; //Descripción de la salvedad de modificación del folio

        // capturar los parametros de la forma: (escritos)
        param_Item_Folio_IdMatricula = request.getParameter(CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_IDMATRICULA);
        param_Item_Folio_IdAnotacion = request.getParameter(CAccionFolioEditPadresHijos.PARAM_ITEM_FOLIO_ANOTACIONID);
        param_Item_Folio_ThisIdAnotacion = request.getParameter(CAccionFolioEditPadresHijos.PARAM_ITEM_THISFOLIO_ANOTACIONID);
        // se asume zona registral igual a la del folio editado
        param_Item_Folio_IdZonaRegistral = t0_folio.getZonaRegistral().getIdZonaRegistral();
        param_SalvedadFolio_Id = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID);
        param_SalvedadFolio_Descripcion = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION);

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
        validator = new BasicStringPatternValidatorWrapper(t0_folio.getIdMatricula(), true, true);
        validator.setObjectToValidate(param_Item_Folio_IdMatricula);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            //
            exception.addError("id-matricula: la matricula indicada es la misma que el folio origen");
        }

        BasicConditionalValidator stage1_validator;

        // 1: validar salvedad
        // 1:A not null
        stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; debe escribir un valor en la salvedad");
        }

        // 1:B maxima longitud
        stage1_validator = new BasicStringMaxLengthValidatorWrapper(255);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener menos de 255 caracteres");
        }

        // 1:C minima longitud
        stage1_validator = new BasicStringMinLengthValidatorWrapper(30);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener mas de 30 caracteres");
        }

        // ------------------------------------------------------
        // raise application error
        if (exception.getErrores().size() > 0) {
            ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
            throw ex1;
        }
        // ------------------------------------------------------

        // el contexto en este caso es el t0_folio; el folio tiene anotaciones que tienen
        // foliosHijo, foliosPadre de tipo List<FolioDerivado>
        // ------------------------------------------------------
        Anotacion source = null; // padre
        Anotacion target = null; // hijo

        // ------------------------------------------------------
        source = new Anotacion();

        source.setIdMatricula(param_Item_Folio_IdMatricula);
        source.setIdAnotacion(param_Item_Folio_IdAnotacion);

        // ------------------------------------------------------
        // a partir del folio derivado construir una anotacion:
        target = new Anotacion();
        target.setIdAnotacion(param_Item_Folio_ThisIdAnotacion); // se recibe como parametro // no se puede saber que anotacion se referencia si no se esta seguro que exista el folio source
        // inicialmente colocar el valor de la primera anotacion de tipo derivada que tenga esa matricula
        target.setIdMatricula(t0_folio.getIdMatricula());

        SalvedadFolio salvedadFolio = new SalvedadFolio();
        salvedadFolio.setIdSalvedad(param_SalvedadFolio_Id);
        salvedadFolio.setDescripcion(param_SalvedadFolio_Descripcion);
        salvedadFolio.setIdMatricula(t0_folio.getIdMatricula());
        salvedadFolio.setNumRadicacion(turno.getIdWorkflow());

        List salvsFolio = new ArrayList();
        salvsFolio.add(salvedadFolio);
        t0_folio.setSalvedades(salvsFolio);

        // configurar source:(folio, anotacion)
        // validar que folio exista
        // validar que folio.anotacion exista
        // configurar target:(folio, anotacion)
        // generar evento para pasar parametros a negocio
        EvnCorreccion result = null;

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        // wrap results
        EvnCorreccionFolioPadresHijos resultWrap;
        resultWrap = new EvnCorreccionFolioPadresHijos(
                usuarioAuriga,
                usuarioSIR,
                t0_folio,
                turno,
                EvnCorreccionFolioPadresHijos.FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_EVENT // EvnCorreccion.EDITAR_FOLIO
        );

        resultWrap.setSource(source);
        resultWrap.setTarget(target);
        resultWrap.setActionName(EvnCorreccionFolioPadresHijos.ACTION_ADDITEM);
        resultWrap.setRelationName(EvnCorreccionFolioPadresHijos.RELATION_PADRESHIJOS_PADRE);

        result = resultWrap;
        return result;

    }

    /**
     * eliminarCiudadanoEdicion
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento eliminarCiudadanoEdicion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);

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
     * refrescarEdicionCancelacion
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento refrescarEdicionCancelacion(HttpServletRequest request) {
        //eliminarInfoBasicaAnotacion(request);
        String ver = request.getParameter("SECUENCIA");
        request.getSession().setAttribute("SECUENCIA", ver);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));
        preservarInfoAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);
        return null;
    }

    /**
     * cancelarEdicionCancelacion
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento cancelarEdicionCancelacion(HttpServletRequest request) {
        eliminarInfoBasicaCiudadano();
        eliminarInfoBasicaAnotacion();
        eliminarInfoBasicaVariosCiudadanos(request);
        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);
        session.removeAttribute(WebKeys.HAY_EXCEPCION);
        session.removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
        session.removeAttribute("cancelar");
        return null;
    }

    protected EvnCorreccion
            doFolioEdit_AnotacionCancelacion_SaveChanges(HttpServletRequest request)
            throws AccionWebException {
        /*
            String pos= (String)request.getSession().getAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL_CANCELACION);
            int numTemp=Integer.parseInt(pos);
         */

        // es una funcion wrap, para que funcione
        // la validacion con permisos;
        // adicionalmente, se debe colocar lo siguiente:
        /* TODO: editar para porder elegir el numero de la anotacion canceladora

           preservarInfoCancelacion(request);
           preservarInfoBasicaFolio(request);

           String numCanceladaB="";
           String numCanceladaA= request.getParameter("ESCOGER_ANOTACION_CANCELACION");


           List cs;// lsita de las anotacion canceladas
           Cancelacion c= new Cancelacion();// la anotacion cancelada
           long numDef;

           // B: before
           // A: after
           boolean cambioCan = false; //falg para saber si se cambio la anotacion cancelada


           // anotacion_t0:
           c=(Cancelacion)anotAnt.getAnotacionesCancelacions().get(0);

           if(c.getCancelada().getIdAnotacion()!=null){
                   numCanceladaB=c.getCancelada().getIdAnotacion();
           }


           Cancelacion ca= new Cancelacion();
           if(!numCanceladaB.equals(numCanceladaA)){
                   Anotacion cancelada=null;
                   Iterator itC=anotacionF.iterator();
                   while(itC.hasNext()){
                           Anotacion an=(Anotacion)itC.next();
                           if (an.getIdAnotacion().equals(numCanceladaA)){
                                   cancelada=an;
                                   //break;
                           }
                   }
                   ca.setCancelada(cancelada);
                   a.addAnotacionesCancelacion(ca);
                   c.setToDelete(true);
                   a.addAnotacionesCancelacion(c);

           }

         */
        // TODO: parametros a adicionar:
        request.setAttribute(CTipoAnotacion.CANCELACION, CTipoAnotacion.CANCELACION);
        return doFolioEdit_Anotacion_SaveChanges(request, true);

    }

    /**
     * grabarEdicionCancelacion
     *
     * @param request HttpServletRequest
     * @return Evento
     * @deprecated use: doFolioEdit_AnotacionCancelacion_SaveChanges
     */
    private Evento grabarEdicionCancelacion(HttpServletRequest request) throws ValidacionParametrosException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //Codigo  de grabar Cancelacion
        preservarInfoCancelacion(request);
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        //obtencion del folio
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosEdicionCancelacionCalificacionException();

        NaturalezaJuridica nat = null;
        boolean cambioNat = false; //flag para saber si se cambio la naturaleza juridica
        boolean cambioCan = false; //falg para saber si se cambio la anotacion cancelada
        String idNaturalezaB = "";
        String nombreNaturalezaB = "";
        String comentarioB = "";
        String numCanceladaB = "";
        double valorB = 0.0d;

        //obtencion lista de ciudadanosT en el sesion (Aqui estan tanto los ciudadanos sin editar como los editados)
        List ciudadanosT = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        if (ciudadanosT == null) {
            ciudadanosT = new Vector();
        }

        //obtencion lista de ciudadanosB del folio (aqui estan solo los ciudadanos q existen antes de la edicion)
        List ciudadanosB = null; // se inicializara despues.

        //aqui se comienza a ver q valores se han cambiado
        Anotacion a = new Anotacion();
        Anotacion anotAnt = null;
        List cs;// lsita de las anotacion canceladas
        Cancelacion c = new Cancelacion();// la anotacion cancelada
        String pos = (String) request.getSession().getAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL_CANCELACION);
        int numTemp = Integer.parseInt(pos);
        long numDef;

        //Se setea el id de la anotacion q se va cambiar
        List anotacionF = null;
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
            anotacionF = RPag.getAnotacionesActual();
        } else {
            anotacionF = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
        }
        anotAnt = (Anotacion) request.getSession().getAttribute(AWCalificacion.ANOTACION_A_EDITAR);
        //aettear id a la anotacion de edicion
        a.setIdAnotacion(anotAnt.getIdAnotacion());

        nat = anotAnt.getNaturalezaJuridica();
        nombreNaturalezaB = nat.getNombre();
        idNaturalezaB = nat.getIdNaturalezaJuridica();
        valorB = anotAnt.getValor();
        comentarioB = anotAnt.getComentario();
        c = (Cancelacion) anotAnt.getAnotacionesCancelacions().get(0);
        ciudadanosB = anotAnt.getAnotacionesCiudadanos();

        if ((anotAnt.getIdAnotacion() == null) || (anotAnt.getIdAnotacion().equals(""))) {
            exception.addError("La anotacion q va editar no existe.");
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
        if (c.getCancelada().getIdAnotacion() != null) {
            numCanceladaB = c.getCancelada().getIdAnotacion();
        }

        //fin de obtencion de los datosB (Before)
        Log.getInstance().debug(AWModificarFolio.class, "\n\n"
                + "La Cancelacion a editar tenia los siguientes datos \n"
                + "ID =" + a.getIdAnotacion() + "\n"
                + "idNaturaleza =" + idNaturalezaB + "\n"
                + "nombreNaturaleza =" + nombreNaturalezaB + "\n"
                + "valor =" + valorB + "\n"
                + "idAnotacion a canelar =" + numCanceladaB + "\n"
                + "comentario =" + comentarioB + "\n"
                + "fin");

        //Comienzo de los datosA (After)
        String idNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nombreNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        String comentarioA = (String) request.getSession().getAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        String numCanceladaA = request.getParameter("ESCOGER_ANOTACION_CANCELACION");
        String sValorA = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        double valorA = 0.0d;

        boolean esta = false;
        List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");
        Iterator ig = grupoNaturalezas.iterator();
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Declara variable natJuridica
         */
        NaturalezaJuridica natJuridica = null;

        while (ig.hasNext()) {
            GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
            List natus = group.getNaturalezaJuridicas();
            Iterator id = natus.iterator();
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change : Se agrega condicion al while para detener la busqueda
             *
             */

            while (id.hasNext() && !esta) {
                NaturalezaJuridica natA = (NaturalezaJuridica) id.next();

                if (natA.getIdNaturalezaJuridica().equals(idNaturalezaA)) {
                    esta = true;
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     * @change : Se asigna la variable natJuridica
                     */
                    natJuridica = natA;

                }
            }
        }
        if (!esta) {
            exception.addError("Debe colocar un codigo de naturaleza juridica valido");
        }

        if (numCanceladaA == null) {
            exception.addError("Debe seleccionar una anotación a cancelar");
        }

        if (sValorA == null) {
            exception.addError("Debe colocar un valor");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        valorA = Double.parseDouble(sValorA);

        Log.getInstance().debug(AWModificarFolio.class, "\n\n"
                + "La anotacion en la interfaz tiene los siguientes datos \n"
                + "valor = " + sValorA + "\n"
                + "idNaturaleza =" + idNaturalezaA + "\n"
                + "nombreNaturaleza =" + nombreNaturalezaA + "\n"
                + "idAnotacion a canelar =" + numCanceladaA + "\n"
                + "comentario =" + comentarioA + "\n"
                + "fin");

        //comienzo de comparacion y construccion de la anotacion edicion
        if (!idNaturalezaB.equals(idNaturalezaA)) {
            nat.setIdNaturalezaJuridica(idNaturalezaA);
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change : Se establese la propiedad version
             */
            nat.setVersion(natJuridica.getVersion());
            cambioNat = true;
        }
        if (!nombreNaturalezaB.equals(nombreNaturalezaA)) {
            nat.setNombre(nombreNaturalezaA);
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change : Se establese la propiedad version
             */
            nat.setVersion(natJuridica.getVersion());
            cambioNat = true;
        }
        if (cambioNat) {
            a.setNaturalezaJuridica(nat);
        }
        Cancelacion ca = new Cancelacion();
        if (!numCanceladaB.equals(numCanceladaA)) {
            Anotacion cancelada = null;
            Iterator itC = anotacionF.iterator();
            while (itC.hasNext()) {
                Anotacion an = (Anotacion) itC.next();
                if (an.getIdAnotacion().equals(numCanceladaA)) {
                    cancelada = an;
                    //break;
                }
            }
            ca.setCancelada(cancelada);
            a.addAnotacionesCancelacion(ca);
            c.setToDelete(true);
            a.addAnotacionesCancelacion(c);

        }
        if (valorA != valorB) {
            a.setValor(valorA);
            a.setToUpdateValor(true);
        }
        if (!comentarioB.equals(comentarioA)) {
            a.setComentario(comentarioA);
        }

        Log.getInstance().debug(AWModificarFolio.class, "\n\n"
                + "La anotacion edicion tiene los siguientes datos");
        Log.getInstance().debug(AWModificarFolio.class, "valor = " + Double.toString(a.getValor()));
        if (a.getNaturalezaJuridica() != null) {
            Log.getInstance().debug(AWModificarFolio.class, "idNaturaleza =" + a.getNaturalezaJuridica().getIdNaturalezaJuridica() + "\n"
                    + "nombreNaturaleza =" + a.getNaturalezaJuridica().getNombre());
        } else {
            Log.getInstance().debug(AWModificarFolio.class, "idNaturaleza =" + "" + "\n"
                    + "nombreNaturaleza =" + "" + "\n");
        }
        if (a.getAnotacionesCancelacions().size() > 0) {
            Log.getInstance().debug(AWModificarFolio.class, "idAnotacion a cancelar = se adiciona -->" + c.getCancelada().getOrden() + "se elimina --> " + ca.getCancelada().getOrden() + "\n");
        } else {
            Log.getInstance().debug(AWModificarFolio.class, "idAnotacion a cancelar =" + "" + "\n");
        }
        Log.getInstance().debug(AWModificarFolio.class, "comentario =" + a.getComentario() + "\n"
                + "fin");

        //parte de ciudadanos (aqui se recorrera la lista de ciudadanosT y se comparara con ciudadanosB para crear ciudadanosA)
        List ciudadanosA = new Vector();
        //se obtienen los ciudadanos mandados a eliminar
        Log.getInstance().debug(AWModificarFolio.class, "el # de ciudadanosB =" + ciudadanosB.size());
        Log.getInstance().debug(AWModificarFolio.class, "el # de ciudadanosT =" + ciudadanosT.size());

        int finAB = ciudadanosB.size();
        int cont = 0;
        while (cont != finAB) {
            AnotacionCiudadano cb = (AnotacionCiudadano) ciudadanosB.get(cont);
            AnotacionCiudadano ct = (AnotacionCiudadano) ciudadanosT.get(cont);
            Log.getInstance().debug(AWModificarFolio.class, "ciudadano cedula =" + ct.getCiudadano().getDocumento() + "toDelete = " + Boolean.toString(ct.isToDelete()));
            if ((cb.getIdCiudadano().equals(ct.getIdCiudadano())) && ct.isToDelete()) {
                ciudadanosA.add(ct);
            }
            cont++;
        }

        //se obtienen los ciudadanos agregados
        int inicioSL = ciudadanosB.size();
        int finSL = ciudadanosT.size();
        //List subList=ciudadanosT.subList(inicioSL,finSL);

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
            AnotacionCiudadano ci = (AnotacionCiudadano) isl.next();
            if (ci.isToDelete()) {
                isl.remove();
            }
        }
        int SL = subList.size();
        Log.getInstance().debug(AWModificarFolio.class, "el tamaño de la Sublista =" + SL);

        ciudadanosA.addAll(subList);
        Log.getInstance().debug(AWModificarFolio.class, "el # de ciudadanos a editar y/o agregar es = " + ciudadanosA.size());

        Iterator ica = ciudadanosA.iterator();
        while (ica.hasNext()) {
            a.addAnotacionesCiudadano((AnotacionCiudadano) ica.next());
        }

        //codigo de grabar en temporal
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        request.getSession().setAttribute(WebKeys.FOLIO, folio);

        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        Log.getInstance().debug(AWModificarFolio.class, "enviando a ANCalificacion!!!");

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, folio, a, usuarioNeg);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        evento.setTurno(turno);
        return new EvnCalificacion(usuarioAuriga, folio, a, usuarioNeg);

    }

    /**
     * agregarVariosCiudadanos
     *
     * @param request HttpServletRequest
     * @param accion String
     * @return Evento
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request,
            String accion) throws AccionWebException {

        ValidacionParametrosException exception;

        if (accion.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)) {
            exception = new ValidacionParametrosCiudadanoSegregacionCalificacionException();
        } else if (accion.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS)) {
            exception = new ValidacionParametrosCiudadanoCalificacionException();
        } else {
            exception = new ValidacionParametrosException();
        }

        return agregarVariosCiudadanos(request, exception);
    }

    /**
     * @param request
     * @param exception
     * @return
     * @throws AccionWebException
     *
     *
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request,
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
        boolean repetido = false;
        List ciudadanosFinales = new ArrayList();
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

                        /* @author : CTORRES
             * @change : Se implemento la validación para solo permitir caracteres alfanuméricos en 
             *           numId cuando el tipo de identificación es PASAPORTE.
             * Caso Mantis : 11056
             * No Requerimiento: 073_151
                         */
                        if (tipoId.contains("PS")) {
                            String regexSL = "^[a-zA-Z]+$";
                            String regexSN = "^[0-9]+$";
                            String regexLN = "^[a-zA0-Z9]+$";
                            java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                            java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                            java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                            boolean esC = false;
                            if (patternSL.matcher(numId).matches()) {
                                esC = true;
                            } else if (patternSN.matcher(numId).matches()) {
                                esC = true;
                            } else if (patternLN.matcher(numId).matches()) {
                                esC = true;
                            } else {
                                datosBien = false;
                                exception.addError("El número de identificación de la persona es inválido. Debe ser alfanumérico");
                            }

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
                Iterator ic = ciudadanos.iterator();
                while (ic.hasNext()) {
                    c = (AnotacionCiudadano) ic.next();
                    if ((tipoIntervencion.equals(c.getRolPersona()))
                            && (numId.equals(c.getCiudadano().getDocumento()))
                            && (tipoId.equals(c.getCiudadano().getTipoDoc()))
                            && !(tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA))) {
                        if (c.isToDelete()) {
                            c.setToDelete(false);
                            repetido = true;
                        } else {
                            exception.addError("La persona no puede tener dos veces el mismo rol");
                        } // end if
                    }
                }

                String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);

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
                    anotacionCiudadano.setParticipacion(participacion);

                    String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);

                    if (marcaPropietario == null || marcaPropietario.equals("")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaPropietario.equals("X")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaPropietario.equals("I")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }

                    ciudadanosFinales.add(anotacionCiudadano);
                    ciudadanosOrganizados.add(anotacionCiudadano);
                }

            }
        }
        this.validarCiudadanosRepetidos(ciudadanosOrganizados, exception);
        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        eliminarInfoBasicaVariosCiudadanos(request);

        //if (!existeEnListaCiudadanos(anotacionCiudadano, ciudadanos)) {
        List listaCompletaCiudadanos = new Vector();

        listaCompletaCiudadanos.addAll(ciudadanos);
        listaCompletaCiudadanos.addAll(ciudadanosFinales);

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
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCorreccion evento = new EvnCorreccion(usuarioAuriga, EvnCorreccion.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosFinales);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        evento.setTurno(turno);
        return evento;
    }

    /**
     * cancelar
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento cancelar(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        request.getSession().removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        /*obtencion de DatosRespuestaPaginador para saber si hay un paginador activo y sacar los datos de este*/
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);

            ArrayList list = new ArrayList();
            if (RPag != null && RPag.getAnotacionesActual() != null) {
                Iterator it = RPag.getAnotacionesActual().iterator();

                while (it.hasNext()) {
                    Object obj = (Object) it.next();
                    list.add(obj);
                }
            }

            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, list);

        }
        return null;

    }

    /**
     * refrescarCancelacion
     *
     * @param request HttpServletRequest
     * @return Evento
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
     * cancelarAnotacion
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento cancelarAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);//varios
        ValidacionParametrosCancelacionException exception = new ValidacionParametrosCancelacionException();
        //List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA);
        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        /*
        String idCanc=request.getParameter("ESCOGER_ANOTACION_CANCELACION");
        if (idCanc==null){
                exception.addError("Debe seleccionar una anotación a cancelar");
        }
         */

        if (anotaciones == null) {
            anotaciones = new java.util.ArrayList();
        }
        // permitir mas de una anotacion a cancelar -----------
        // Bug 0003562

        // ids anotaciones para cancelar
        HttpSession session = request.getSession();
        String[] local_ListAnotacionIdsToBeCanceled;

        local_ListAnotacionIdsToBeCanceled = (String[]) session.getAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);

        if ((null == local_ListAnotacionIdsToBeCanceled)
                && (local_ListAnotacionIdsToBeCanceled.length == 0)) {

            exception.addError("Debe escoger por lo menos una anotación a cancelar.");
            local_ListAnotacionIdsToBeCanceled = new String[0];

        }

        //List< Anotacion >
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
        //
        //        if( local_AnotacionItem.getIdAnotacion().equals( local_ListAnotacionIdsToBeCanceled[i] ) ){
        //           local_ListAnotacionObjsToBeCanceled.add( local_AnotacionItem );
        //           break;
        //        }
        //     } // for
        //
        //  } // for
        String valFechaRadicacion = request.getParameter(CFolio.ANOTACION_FECHA_RADICACION);

        Calendar fechaRadicacion = Calendar.getInstance();
        if (valFechaRadicacion != null) {
            fechaRadicacion = darFecha(valFechaRadicacion);
            if (fechaRadicacion == null) {
                exception.addError("La fecha de la radicacion en la anotacion es invalida");
            }
        }

        Calendar fechaDocumento = null;

        //Se hacen las validaciones si se requiere validar los campos del Documento, para el caso de correcciones por ejemplo.
        Documento documento = null;
        if (String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CORRECCIONES)) {
            String tipoDocumento = request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
            if ((tipoDocumento == null) || tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe escoger un tipo para el documento de la anotacion");
            }

            String numDocumento = request.getParameter(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);

            if ((numDocumento == null) || numDocumento.equals("")) {
                exception.addError("El valor del numero del documento en la anotacion es inválido");
            }

            String valFechaDocumento = request.getParameter(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
            if ((valFechaDocumento == null) || valFechaDocumento.equals("")) {
                exception.addError("La fecha del documento en la anotacion no puede ser vacia");
            }

            fechaDocumento = darFecha(valFechaDocumento);
            if (fechaDocumento == null) {
                exception.addError("La fecha del documento en la anotacion es inválida");
            }

            String valorDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
            String nomDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);

            if ((valorDepartamento.length() <= 0)) {
                exception.addError("Debe seleccionar un departamento valido para la oficina de procedencia del documento el la anotacion");
            }

            String valorMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
            String nomMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);

            if ((valorMunicipio.length() <= 0)) {
                exception.addError("Debe seleccionar un municipio valido para la oficina de procedencia del documento el la anotacion");
            }

            String valorVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
            if ((valorVereda == null || (valorVereda.trim().equals(""))) || nomDepartamento.equals("") || nomMunicipio.equals("")) {
                valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), valorDepartamento, valorMunicipio);
            }

            String nomVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);

            if ((valorVereda.length() <= 0)) {
                exception.addError("Debe seleccionar una vereda valida para la oficina de procedencia del documento el la anotacion");
            }

            String numOficina = new String();
            String nomOficina = new String();
            String idOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
            /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            String oficinaVersion = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);

            if ((idOficina == null) || (idOficina.length() <= 0)) {
                exception.addError("Debe seleccionar una entidad pública válida asociada al reparto notarial");
            } else {
                nomOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
                numOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
            }

            if (exception.getErrores().size() <= 0) {
                documento = new Documento();
                TipoDocumento tipoDoc = new TipoDocumento();

                Iterator itTiposDocs = ((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO)).iterator();
                while (itTiposDocs.hasNext()) {
                    ElementoLista elemento = (ElementoLista) itTiposDocs.next();
                    if (elemento.getId().equals(tipoDocumento)) {
                        tipoDoc.setNombre(elemento.getValor());
                    }
                }
                tipoDoc.setIdTipoDocumento(tipoDocumento);
                documento.setFecha(fechaDocumento.getTime());
                documento.setNumero(numDocumento);

                OficinaOrigen oficina = new OficinaOrigen();
                oficina.setIdOficinaOrigen(idOficina);
                /*
                         *  @author Carlos Torres
                         *  @chage   se agrega validacion de version diferente
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                oficina.setVersion((oficinaVersion != null && !oficinaVersion.equals("")) ? Long.parseLong(oficinaVersion) : null);
                oficina.setNumero(numOficina);
                oficina.setNombre(nomOficina);

                Vereda vereda = new Vereda();
                vereda.setIdDepartamento(valorDepartamento);
                vereda.setIdMunicipio(valorMunicipio);
                vereda.setIdVereda(valorVereda);
                vereda.setNombre(nomVereda);
                oficina.setVereda(vereda);
                documento.setTipoDocumento(tipoDoc);
                documento.setOficinaOrigen(oficina);
            }
        }

        //Fin Validación Campos del Documento
        // bug 3569 fecha de radicacion no puede ser anterior a fecha de documento
        if ((null != fechaRadicacion)
                && (null != fechaDocumento)) {

            if (fechaRadicacion.before(fechaDocumento)) {
                exception.addError("fecha radicacion no puede ser menor a fecha de documento");
            } // :if

        } // :if

        // tratar de obtener las anotaciones segun este id;
        //
        String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        if (numEspecificacion != null) {
            numEspecificacion = numEspecificacion.replaceAll(",", "");
        }
        double valorEspecificacion = 0.0d;
        if (numEspecificacion == null) {
            numEspecificacion = "0";
        }

        Log.getInstance().debug(AWModificarFolio.class, "\n\n Comenzar comparacion de naturalezas juridicas");
        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Declara variable natJuridica
         */
        NaturalezaJuridica natJuridica = new NaturalezaJuridica();
        String versionNJ = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
        if ((numEspecificacion != null) && !numEspecificacion.equals("")) {

            if (idNaturaleza == null || idNaturaleza.length() <= 0) {
                exception.addError("Debe seleccionar una naturaleza juridica para la especificacion en la anotacion");
            }

            //validacion de la idNaturalezaJuridica
            boolean esta = false;
            List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");
            //Log.getInstance().debug(AWModificarFolio.class, "lista ="+grupoNaturalezas);
            Iterator ig = grupoNaturalezas.iterator();
            while (ig.hasNext()) {
                GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
                if (group.getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.ID_CANCELACION)) {
                    List natus = group.getNaturalezaJuridicas();
                    Iterator id = natus.iterator();
                    while (id.hasNext()) {
                        NaturalezaJuridica nat = (NaturalezaJuridica) id.next();
                        //Log.getInstance().debug(AWModificarFolio.class, nat.getIdNaturalezaJuridica()+"- comparando con -" +idNaturaleza);
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
                exception.addError("Debe colocar un codigo de naturaleza juridica valido");
            }
            request.getSession().removeAttribute("listanat");
        }

        String comentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        Anotacion anotacion = new Anotacion();
        int pos = anotaciones.size() + 1;
        anotacion.setOrden(String.valueOf(pos));

        //anotacion.setFechaRadicacion(fechaRadicacion.getTime());
        if (turno != null) {
            //anotacion.setNumRadicacion(turno.getIdWorkflow());
            anotacion.setIdWorkflow(turno.getIdWorkflow());
            //anotacion.setFechaRadicacion(turno.getFechaInicio());
        }

        //Se valida si se ingresa un nuevo documento para el caso de correcciones o si se ingresa el de la solicitud(registro)
        if (String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CORRECCIONES)) {
            anotacion.setDocumento(documento);
            if (request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION) != null
                    && !request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION).equals("")) {
                anotacion.setNumRadicacion(request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION));
            } else {
                exception.addError("Debe Digitar el numero de radicacion");
            }
            if (request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION) != null
                    && !request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION).equals("")) {
                if (valFechaRadicacion != null) {
                    if (fechaRadicacion == null) {
                        exception.addError("La fecha de la radicacion en la anotacion es invalida");
                    } else {
                        anotacion.setFechaRadicacion(fechaRadicacion.getTime());
                    }
                } else {
                    exception.addError("La fecha de la radicacion no puede ser vacia");
                }
            } else {
                exception.addError("Debe Digitar el numero de radicacion");
            }
        } else {
            anotacion.setDocumento(((SolicitudRegistro) turno.getSolicitud()).getDocumento());
        }

        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Declara variable natJuridica
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
        }
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Declara variable natJuridica
         */
        naturalezaJuridica.setVersion(Long.parseLong(versionNJ));

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setComentario(comentario);

        //segmento de ingresar ciudadanos
        List ciudadanoTemp = new ArrayList();
        if (ciudadanos != null) {
            Iterator itPersonas = ciudadanos.iterator();
            while (itPersonas.hasNext()) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                if (!ciudadano.isToDelete()) {
                    ciudadanoTemp.add(ciudadano);
                }
            }
        }
        if (ciudadanoTemp.isEmpty() || ciudadanoTemp.size() == 0) {
            exception.addError("Debe ingresar por lo menos un ciudadano");
        }

        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se realiza validaciones de la información de la salvedad
         * Caso Mantis  :   04131
         */
        if (turno.getSolicitud() instanceof SolicitudCorreccion) {
            String param_AnotacionSalvedad_Descripcion = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
            String param_AnotacionSalvedad_Id = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID);

            BasicConditionalValidator stage1_validator;

            stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
            stage1_validator.setObjectToValidate(param_AnotacionSalvedad_Descripcion);
            stage1_validator.validate();

            if (stage1_validator.getResult() != true) {
                exception.addError("anotacion:salvedad; debe escribir un valor en la salvedad");
            }

            // 1:B maxima longitud
            /**
             * @Autor: Edgar Lora
             * @@mantis: 0011623
             * @requerimiento: 028_453
             */
            stage1_validator = new BasicStringMaxLengthValidatorWrapper(1024);
            stage1_validator.setObjectToValidate(param_AnotacionSalvedad_Descripcion);
            stage1_validator.validate();

            if (stage1_validator.getResult() != true) {
                /**
                 * @Autor: Edgar Lora
                 * @@mantis: 0011623
                 * @requerimiento: 028_453
                 */
                exception.addError("anotacion:salvedad; la salvedad debe tener maximo de 1024 caracteres");
            }

            // 1:C minima longitud
            stage1_validator = new BasicStringMinLengthValidatorWrapper(30);
            stage1_validator.setObjectToValidate(param_AnotacionSalvedad_Descripcion);
            stage1_validator.validate();

            if (stage1_validator.getResult() != true) {
                exception.addError("anotacion:salvedad; la salvedad debe tener mas de 30 caracteres");
            }

            String param_AnotacionSalvedad_NumRadicacion;
            param_AnotacionSalvedad_NumRadicacion = turno.getIdWorkflow();

            if ((null != param_AnotacionSalvedad_Id)
                    && ("".equals(param_AnotacionSalvedad_Id.trim()))) {
                param_AnotacionSalvedad_Id = null;
            }

            SalvedadAnotacion local_SalvedadAnotacion = new SalvedadAnotacion();
            local_SalvedadAnotacion.setDescripcion(param_AnotacionSalvedad_Descripcion);
            local_SalvedadAnotacion.setIdSalvedad(param_AnotacionSalvedad_Id);
            local_SalvedadAnotacion.setIdMatricula(folio.getIdMatricula());
            local_SalvedadAnotacion.setIdAnotacion(String.valueOf(folio.getLastIdAnotacion() + 1));
            local_SalvedadAnotacion.setNumRadicacion(param_AnotacionSalvedad_NumRadicacion);

            List local_SalvedadList = new ArrayList();

            local_SalvedadList.add(local_SalvedadAnotacion);
            anotacion.setSalvedades(local_SalvedadList);
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
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

        List canceladas = new ArrayList();

        for (int i = 0; i < local_ListAnotacionIdsToBeCanceled.length; i++) {
            Anotacion an = new Anotacion();
            an.setIdMatricula(folio.getIdMatricula());
            an.setIdAnotacion(local_ListAnotacionIdsToBeCanceled[i]);
            canceladas.add(an);
        }

        // permitir mas de una anotacion a cancelar -----------
        // Bug 0003562
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

        anotacion.setTipoAnotacion(local_AnotacionThisTipo);

        // create Cancelacion objects
        for (; local_ListAnotacionObjsToBeCanceledIterator.hasNext();) {
            local_AnotacionToBeCanceledItem = (Anotacion) local_ListAnotacionObjsToBeCanceledIterator.next();
            local_CancelacionItem = new Cancelacion();
            local_CancelacionItem.setCancelada(local_AnotacionToBeCanceledItem);
            local_CancelacionItem.setCanceladora(local_AnotacionThis);

            local_AnotacionThis.addAnotacionesCancelacion(local_CancelacionItem);

        } // for
        // ---------------------------------------------------------------

        Iterator itCanceladas = canceladas.iterator();
        while (itCanceladas.hasNext()) {
            Cancelacion cancelacion = new Cancelacion();
            cancelacion.setCancelada((Anotacion) itCanceladas.next());
            cancelacion.setCanceladora(local_AnotacionThis);
            anotacion.addAnotacionesCancelacion(cancelacion);
        }

        // delete cache objects ------------------------------------------------
        // ---------------------------------------------------------------------
        //anotacion.setOrden(anotacion.getIdAnotacion());
        // refresh screen deleting some key values in session
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
        if (llaves != null) {
            llaves.removeLLaves(request);
        }
        session.removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
        //

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, folio, anotacion, usuarioNeg);
        evento.setTurno(turno);

        return evento;
    }

    /**
     * cancelarCancelacion
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento cancelarCancelacion(HttpServletRequest request) {
        // bug 3562
        HttpSession session = request.getSession();
        // delete cache objects ------------------------------------------------
        session.removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
        // ---------------------------------------------------------------------

        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        session.removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
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
        String num = request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);
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
            num = request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);
        }

        int n = Integer.parseInt(num);
        n--;
        Log.getInstance().debug(AWModificarFolio.class, "Editando anotacion # --> " + n);
        Iterator ianot = anotacionesTemporales.iterator();
        // cambio:
        // comparar contra el id de la anotacion, no contra el orden
        String local_AnotacionId;
        while (ianot.hasNext()) {
            Anotacion temp = (Anotacion) ianot.next();
            // local_AnotacionId = temp.getOrden();
            local_AnotacionId = temp.getIdAnotacion();
            if (local_AnotacionId.equals(num)) {
                a = temp;
                break;
            }
        }

        a.setToDelete(true);

        //codigo de grabar en temporal
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        request.getSession().setAttribute(WebKeys.FOLIO, folio);

        // refresh page
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
        if (llaves != null) {
            llaves.removeLLaves(request);
        }
        session.removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

        // delegado a calificacion para hacer la eliminacion
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, folio, a, usuarioNeg, EvnCalificacion.ELIMINAR_ANOTACION);
        evento.setProceso(new Proceso(new Long(CProceso.PROCESO_CORRECCION).longValue(), "", ""));
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        evento.setTurno(turno);
        evento.setAnotaciones(new ArrayList());

        return evento;
    }

    private Evento
            doProcess_CorreccionSimpleMain_EliminarCambiosAnotacionDefinitivaTemporal(HttpServletRequest request) {

        // build-message -----------------------------------------------------------------
        EvnCalificacion local_Result;
        local_Result = (EvnCalificacion) eliminarAnotacionTemporal(request);

        // get-set injection
        local_Result.setEliminarCambiosAnotacionDefinitivaTemporalEnabled(true);

        // ---------------------------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        return local_Result;
        // ---------------------------------------------------------------------------------

    } // end-method: doProcess_CorreccionSimpleMain_EliminarCambiosAnotacionDefinitivaTemporal

    /*

   private Evento
   doProcess_CorreccionSimpleMain_EliminarCambiosAnotacionDefinitivaTemporal( HttpServletRequest request ) {

    HttpSession session;
    session = request.getSession();


    // params to send -----------------------------------------------------------------------------------
    org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
    gov.sir.core.negocio.modelo.Usuario            param_UsuarioSir;
    gov.sir.core.negocio.modelo.Turno              param_Turno;
    gov.sir.core.negocio.modelo.Folio              param_Folio;
    gov.sir.core.negocio.modelo.Anotacion          param_Anotacion;
    // --------------------------------------------------------------------------------------------------

    // populate params -----------------------------------------------------------------------------------
    param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario)session.getAttribute( WebKeys.USUARIO_AURIGA );
    param_UsuarioSir    = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );
    param_Turno         = (gov.sir.core.negocio.modelo.Turno)session.getAttribute( WebKeys.TURNO );
    param_Folio         = (gov.sir.core.negocio.modelo.Folio)session.getAttribute( WebKeys.FOLIO );
    param_Anotacion     = null;

    // --------




    // capture anotacion
    local_SearchAnotacionById : {

        // @see eliminarAnotacionTemporal

        //obtencion e inicializacion de datos.
        String num = request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);
        Folio folio = param_Folio;
        String nombreResultado= (String )session.getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        Anotacion a=new Anotacion();
        List anotacionesTemporales=new Vector();
        if(nombreResultado!=null && !nombreResultado.equals("")){
                DatosRespuestaPaginador RPag=(DatosRespuestaPaginador) session.getAttribute(nombreResultado);
                String nombreNumAnotacionTemporal= (String )session.getAttribute(WebKeys.NOMBRE_NUM_ANOTACION_TEMPORAL);
                num= request.getParameter(nombreNumAnotacionTemporal);

                                    ArrayList list = new ArrayList();
                                    if(RPag!=null && RPag.getAnotacionesActual()!=null){
                                            Iterator it = RPag.getAnotacionesActual().iterator();

                                            int i= 0;
                                            while(it.hasNext()){
                                                    Object obj = (Object)it.next();
                                                    list.add(obj);
                                            }
                                    }
                                    session.setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO, list);

                anotacionesTemporales=RPag.getAnotacionesActual();
        }
        else{
                anotacionesTemporales = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
                num = request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);
        }

        int n= Integer.parseInt(num);
        n--;
        Log.getInstance().debug(AWModificarFolio.class, "Editando anotacion # --> "+ n);
        Iterator ianot = anotacionesTemporales.iterator();
        // cambio:
        // comparar contra el id de la anotacion, no contra el orden
        String local_AnotacionId;
        while( ianot.hasNext() ){
                Anotacion temp = (Anotacion) ianot.next();
                // local_AnotacionId = temp.getOrden();
                local_AnotacionId = temp.getIdAnotacion();
                if( local_AnotacionId.equals( num ) ){
                        a=temp;
                        break;
                } // if
        } // while



        param_Anotacion = a;

    } // :local_SearchAnotacionById

    // --------------------------------------------------------------------------------------------------

    local_SessionReloadMark: {

        // @see eliminarAnotacionTemporal

        // refresh page
        session.removeAttribute( WebKeys.LISTA_ANOTACIONES_FOLIO );
        LLavesMostrarFolioHelper llaves
            =(LLavesMostrarFolioHelper) session.getAttribute( WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO );
        if( llaves != null ){
                llaves.removeLLaves(request);
        }
        session.removeAttribute( WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO );

     } // :local_SessionReloadMark

    //

    // build-message -----------------------------------------------------------------

     EvnCorreccion local_Result;
     local_Result = new EvnCorreccion( EvnCorreccion.ELIMINARCAMBIOSANOTACIONDEFINITIVATEMPORAL_EVENT );

     // get-set injection

     local_Result.setUsuarioAuriga( param_UsuarioAuriga );
     local_Result.setUsuarioSir( param_UsuarioSir );
     local_Result.setTurno( param_Turno );
     local_Result.setFolio( param_Folio );
     local_Result.setAnotacion( param_Anotacion );

     // ---------------------------------------------------------------------------------

     // send-message -----------------------------------------------------------------
     return local_Result;
     // ---------------------------------------------------------------------------------

   } // end-method: doProcess_CorreccionSimpleMain_EliminarCambiosAnotacionDefinitivaTemporal


     */
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// bug 3562 ---------------------------------------------------------------------
    private Evento
            doCorr_FolioEdit_AnotacionCanceladoraEditar_Step0_BtnStart(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        // (modificado) TODO: verificar: tener en cuenta que estos datos se estan cargando
        // de cache;
        Evento event = cargarAnotacion(request);

        // bug 3562
        // clear cache / put data into cache of
        // otems referencesd by the wizard.
        Anotacion local_Anotacion;
        local_Anotacion = (Anotacion) session.getAttribute(AWModificarFolio.ANOTACION_EDITADA);

        // TODO: load selected items of first page;
        ValidacionAnotacionesCancelacionException exception;
        exception = new ValidacionAnotacionesCancelacionException();

        TipoAnotacion local_TipoAnotacion = null;

        if (null == local_Anotacion) {
            exception.addError("No se ha podido tener acceso a la anotacion");
        } else if (null == (local_TipoAnotacion = local_Anotacion.getTipoAnotacion())) {
            exception.addError("No se ha podido obtener el tipo de la anotacion");
        } else if (!(CTipoAnotacion.CANCELACION.equals(local_TipoAnotacion.getIdTipoAnotacion()))) {
            exception.addError("La anotacion editada no es canceladora");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Turno param_Turno = (Turno) session.getAttribute(WebKeys.TURNO);
        java.util.List listaPermisos = null;
        if (param_Turno.getSolicitud() instanceof SolicitudCorreccion) {
            SolicitudCorreccion sol = (SolicitudCorreccion) param_Turno.getSolicitud();
            listaPermisos = sol.getPermisos();
        }

        if (listaPermisos != null && local_Anotacion != null) {
            if (local_Anotacion.isTemporal() && !local_Anotacion.isTemporalConContraparteDefinitiva()) {
                List permisosAdicionales = new ArrayList();
                SolicitudPermisoCorreccion solicitud = new SolicitudPermisoCorreccion();
                solicitud.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_TCANCELASELECT_ID);
                permisosAdicionales.add(solicitud);
                for (Iterator permisosItera = listaPermisos.iterator(); permisosItera.hasNext();) {
                    SolicitudPermisoCorreccion permisoCorr1 = (SolicitudPermisoCorreccion) permisosItera.next();
                    for (Iterator permisosAdItera = permisosAdicionales.iterator(); permisosAdItera.hasNext();) {
                        SolicitudPermisoCorreccion permisoCorr2 = (SolicitudPermisoCorreccion) permisosAdItera.next();
                        if (permisoCorr1.getIdPermiso() != null && permisoCorr1.getIdPermiso().equals(permisoCorr2.getIdPermiso())) {
                            permisosAdItera.remove();
                        }
                    }
                }
                permisosAdicionales.addAll(listaPermisos);
                listaPermisos = permisosAdicionales;
            }
        }
        session.setAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST, listaPermisos);

        // List< String > to collect data of id's
        List local_ListAnotacionIdsToBeCanceled;
        // String[] to put data of id's
        String[] local_ArrayAnotacionIdsToBeCanceled;

        local_ListAnotacionIdsToBeCanceled = new ArrayList();

        // List< Cancelacion >
        List local_AnotacionCancelacionesObjList;
        local_AnotacionCancelacionesObjList = local_Anotacion.getAnotacionesCancelacions();

        Iterator local_AnotacionCancelacionesObjListIterator;
        local_AnotacionCancelacionesObjListIterator = local_AnotacionCancelacionesObjList.iterator();

        // iterate oover all anotaciones-to-be cancelled
        Cancelacion local_CancelacionItem;
        Anotacion local_AnotacionToBeCanceledItem;
        String local_AnotacionToBeCanceledItemId;

        for (; local_AnotacionCancelacionesObjListIterator.hasNext();) {
            local_CancelacionItem = (Cancelacion) local_AnotacionCancelacionesObjListIterator.next();
            local_AnotacionToBeCanceledItem = local_CancelacionItem.getCancelada();
            local_AnotacionToBeCanceledItemId = local_AnotacionToBeCanceledItem.getIdAnotacion();
            local_ListAnotacionIdsToBeCanceled.add(local_AnotacionToBeCanceledItemId);
        } // for

        local_ArrayAnotacionIdsToBeCanceled = new String[local_ListAnotacionIdsToBeCanceled.size()];

        local_ArrayAnotacionIdsToBeCanceled = (String[]) local_ListAnotacionIdsToBeCanceled.toArray(local_ArrayAnotacionIdsToBeCanceled);

        // se coloca el arreglo de id's en sesion
        // luego se debe concatenar, para que se
        // conserve el estado de los seleccionados.
        session.setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, local_ArrayAnotacionIdsToBeCanceled);
        // String[] idsAnotaciones = request.getParameterValues("ESCOGER_ANOTACION_CANCELACION");

        return event;

    } // end method: doCorr_FolioEdit_AnotacionCanceladoraEditar_Step0_BtnStart

    private Evento
            doCorr_FolioEdit_AnotacionCanceladoraEditar_Step1_BtnBack(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        session.removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        return null;

    } // end method: doCorr_FolioEdit_AnotacionCanceladoraCrear_Step1_BtnBack

    private Evento
            doCorr_FolioEdit_AnotacionCanceladoraEditar_Step1_BtnNext(HttpServletRequest request)
            throws AccionWebException {

        // capturar el set de anotaciones canceladoras seleccionadas
        // (Bug  3547) solamente cuando tiene el permiso
        // de anotacion cancelacion se fija atrubito en sesion
        HttpSession session = request.getSession();

        // 1: cargar conjunto de permisos de la sesion aplicando el filtro de
        // permisos segun las autorizaciones dadas.
        // con el localProcessManager se filtran los delta a aplicar
        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager
                = doExtractAuthorization_ProcessManager(request);

        boolean activateEvaluation;

        activateEvaluation = ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:anotacionesCancelacion");

        if (activateEvaluation) {

            // bug 5042:
            // must be collected in the paginator
            final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID = "TARGET_SELECTIONCOLLECTOR";

            String idsAnotacionesCsv = request.getParameter(LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID);

            // only the last is added
            String[] idsAnotaciones = gov.sir.core.web.helpers.comun.JvLocalUtils.csvStringToStringArray(idsAnotacionesCsv, gov.sir.core.web.helpers.comun.JvLocalUtils.DEFAULT_SEPARATOR, true);
            if (idsAnotacionesCsv == null || idsAnotacionesCsv.equals("")) {
                idsAnotaciones = null;
            }
            ValidacionAnotacionesCancelacionException exception = new ValidacionAnotacionesCancelacionException();
            if (idsAnotaciones == null) {
                exception.addError("Debe seleccionar por lo menos una anotación para cancelar");
            }

            Anotacion local_Anotacion;
            local_Anotacion = (Anotacion) session.getAttribute(AWModificarFolio.ANOTACION_EDITADA);
            if (idsAnotaciones != null) {
                List anotaciones = null;
                Anotacion anotacion = null;
                String nombreResultado = null;
                nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
                DatosRespuestaPaginador RPag = null;//(DatosRespuestaPaginador) request.getSession().getAttribute( "RESULTADOS" );
                RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
                anotaciones = RPag.getAnotacionesActual();
                int anotacionSel;
                int orden;

                int idAnotacionEd = Integer.parseInt(local_Anotacion.getOrden());
                for (int i = 0; i < idsAnotaciones.length; i++) {
                    try {
                        int idAnotacionSel = Integer.parseInt(idsAnotaciones[i]);
                        //Con el id de la anotacion seleccionada consulto el orden
                        orden = idAnotacionSel;
                        for (int j = 0; j < anotaciones.size(); j++) {
                            anotacion = (Anotacion) anotaciones.get(j);
                            anotacionSel = Integer.parseInt(anotacion.getIdAnotacion());
                            if (idAnotacionSel == anotacionSel) {
                                orden = Integer.parseInt(anotacion.getOrden());
                                break;
                            }
                        }

                        //la comparacion no se hace con idAnotacionSel sino con orden
                        if (orden >= idAnotacionEd) {
                            //exception.addError("La anotacion " + idsAnotaciones[i] + " es mayor a la anotación canceladora");
                            exception.addError("La anotacion " + orden + " es mayor a la anotación canceladora");
                        }

                    } catch (NumberFormatException e) {
                        exception.addError("Error validando el orden de las anotaciones");
                    }
                }
            }
            if (exception != null && exception.getErrores() != null && !exception.getErrores().isEmpty()) {
                throw exception;
            }

            session.setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, idsAnotaciones);

            session.setAttribute("cancelar", "true");

            session.setAttribute("soloCancelar", "true");

            // do the first implementation actions
            doFolioEdit_AnotacionCancelacion_OnLoad(request);

        } // if

        return null;

    } // end method: doCorr_FolioEdit_AnotacionCanceladoraCrear_Step1_BtnNext

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------
    // Bug 05042
    private void filter_PaginadorMultiselect_Enabled(HttpServletRequest request) {
        doProcess_PaginadorMultiselect_SaveState(request);
    } // end-method: filter_PaginadorMultiselect_Enabled

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

    private Evento
            doFolioEdit_AnotacionCancelacion_OnLoad(HttpServletRequest request)
            throws AccionWebException {

        // Loaded in Step= of wizard
        // Evento event = cargarAnotacion( request );
        // TODO:  datos que se tenian de anotacion que se estaba cancelando;
        String ver;
        String nombreNumAnotacionTemporal = (String) request.getSession().getAttribute(WebKeys.NOMBRE_NUM_ANOTACION_TEMPORAL);
        ver = request.getParameter(nombreNumAnotacionTemporal);
        request.getSession().setAttribute(AWModificarFolio.NUM_ANOTACION_TEMPORAL, ver);
        // request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO, RPag.getAnotacionesActual());
        // request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, RPag.getAnotacionesActual());

        // return event;
        return null;

    }

    /**
     * @param request
     * @return
     * @deprecated see: doFolioEdit_AnotacionCancelacion_OnLoad
     */
    private Evento editarCancelacion(HttpServletRequest request) {

        // cargar la anotacion
        // segun el id seleccionado del paginador.
        // anterior;
        // obtencion de DatosRespuestaPaginador para saber si hay un paginador activo y sacar los datos de este
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
            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, list);
        } else {
            ver = request.getParameter(AWModificarFolio.NUM_ANOTACION_TEMPORAL);
        }
        request.getSession().setAttribute(AWModificarFolio.NUM_ANOTACION_TEMPORAL, ver);
        return null;

        // actual; similar a la carga de una anotacion con permisos;
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
     * Valida un ciudadano en anotación, recibe el request, crea el objeto
     * ciudadano y lanza un evento de negocio
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento validarCiudadano(HttpServletRequest request) throws AccionWebException {
        //eliminarInfoBasicaAnotacion(request);

        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosCiudadanoCalificacionException exception = new ValidacionParametrosCiudadanoCalificacionException();
        String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoAnotacion(request);
        preservarInfoCancelacion(request);

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
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        Ciudadano ciud = new Ciudadano();
        ciud.setDocumento(numDoc);
        ciud.setTipoDoc(tipoDoc);

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciud.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        EvnCiudadano evnt = new EvnCiudadano(usuarioArq, EvnCiudadano.CONSULTAR_CIUDADANO_ANOTACION, ciud);
        evnt.setCorrecciones(true);
        evnt.setTurno((Turno) request.getSession().getAttribute(WebKeys.TURNO));
        return evnt;
    }

    /**
     * @param request
     * @return
     */
    private Evento refrescarEdicion(HttpServletRequest request) {
        //eliminarInfoBasicaAnotacion(request);
        String ver = request.getParameter("SECUENCIA");
        request.getSession().setAttribute("SECUENCIA", ver);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));
        preservarInfoAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);
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
     *
     * @param request
     * @return
     */
    private Evento aumentarNumeroVariosCiudadanos(HttpServletRequest request) {

        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        numCiud++;
        HttpSession session = request.getSession();
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    /**
     *
     * @param request
     * @return
     */
    private Evento disminuirNumeroVariosCiudadanos(HttpServletRequest request) {

        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        if (numCiud > 1) {
            numCiud--;
        }

        HttpSession session = request.getSession();
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    /**
     * @param request
     * @return
     */
    private int numeroRegistrosTablaAgregarCiudadanos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer num = (Integer) session.getAttribute(NUM_REGISTROS_TABLA_CIUDADANOS);
        int numCiud;

        if (num == null) {
            numCiud = DEFAULT_NUM_CIUDADANOS_TABLA;
        } else {
            numCiud = num.intValue();
        }

        return numCiud;
    }

    /**
     * @param request
     */
    private void preservarInfoBasicaVariosCiudadanos(HttpServletRequest request) {

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        HttpSession session = request.getSession();
        String key = null;
        Object parametro = null;

        for (int i = 0; i < numCiud; i++) {

            key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_COD_VERIFICACION + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_SEXO_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

        }
    }

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

    private Evento doCiudadanoAddItem_AnotacionNormal(HttpServletRequest request) throws AccionWebException {
        // inicialmente se utiliza la accion de agregar varios ciudadanos edicion
        return agregarVariosCiudadanosEdicion(request);
    }

    private Evento doCiudadanoDelItem_AnotacionNormal(HttpServletRequest request) throws AccionWebException {
        // inicialmente se utiliza la accion de eliminar varios ciudadanos edicion
        return eliminarCiudadanoEdicion(request);
    }

    private Evento doCiudadanoValidateItem_AnotacionNormal(HttpServletRequest request) throws AccionWebException {
        // inicialmente se utiliza la accion de eliminar varios ciudadanos edicion
        return validarCiudadano(request);
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
                    exception.addError("Debe escoger un tipo de persona en la anotacion");
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
                        if (tipoId.contains("PS")) {
                            String regexSL = "^[a-zA-Z]+$";
                            String regexSN = "^[0-9]+$";
                            String regexLN = "^[a-zA0-Z9]+$";
                            java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                            java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                            java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                            boolean esC = false;
                            if (patternSL.matcher(numId).matches()) {
                                esC = true;
                            } else if (patternSN.matcher(numId).matches()) {
                                esC = true;
                            } else if (patternLN.matcher(numId).matches()) {
                                esC = true;
                            } else {
                                datosBien = false;
                                exception.addError("El número de identificación de la persona es inválido. Debe ser alfanumérico");
                            }
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
                    if (null == c) {
                        continue;
                    }

                    if (((null != tipoIntervecion)
                            && (tipoIntervecion.equals(c.getRolPersona())))
                            && ((numId != null)
                            && (numId.equals(c.getCiudadano().getDocumento())))
                            && ((tipoId != null)
                            && (tipoId.equals(c.getCiudadano().getTipoDoc()))
                            && !(tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)))) {
                        if (c.isToDelete()) {
                            if ((marcaPropietario.equals(c.getStringMarcaPropietario()))
                                    && (participacion.equals(c.getParticipacion()))) {
                                c.setToDelete(false);
                                repetido = true;
                            }
                        } else {
                            exception.addError("La persona no puede tener dos veces el mismo rol");
                        } // end if
                    } // end if
                } // end while

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
                    ciudadano.setSexo(sexo);
                    ciudadano.setTipoPersona(tipoPersona);
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
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCorreccion evento = new EvnCorreccion(usuarioAuriga, EvnCorreccion.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosAgregados);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        evento.setTurno(turno);
        return evento;

    }

    /**
     * @param request
     * @return
     */
    private Evento preservarInfoCancelacion(HttpServletRequest request) {
        if (request.getParameter(AWCalificacion.POSICIONANOTACION) != null) {
            request.getSession().setAttribute(AWCalificacion.POSICIONANOTACION, request.getParameter(AWCalificacion.POSICIONANOTACION));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }
        if (request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO, request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO));
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
     * doCorreccionEditarAnotacion
     *
     * @param request HttpServletRequest
     * @return any
     */
    private Evento
            do_Corr_PageEvent_FolioAnotacionXLoad(HttpServletRequest request)
            throws AccionWebException {
        // revision: esta cargando
        // bien los datos de la anotacion
        // se deja la misma accion:

        return cargarAnotacion(request);

        /*

            // obtencion de DatosRespuestaPaginador
            //   para saber si hay un paginador activo
            //   y sacar los datos de este

            // bind cache-params (initial) ---------------------------------------------------------------
            // cargar los paramrtros para realizar la busqueda del
            // objeto a cargar en Page:Anotaciones
            // nombreResultado = key en sesion para buscar el paginador
            String nombreResultado = (String)request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
            String ver;
            if( nombreResultado != null
             && !nombreResultado.equals("") ) {

                    // paginador en sesion
                    DatosRespuestaPaginador RPag=(DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
                    String nombreNumAnotacionTemporal= (String )request.getSession().getAttribute(WebKeys.NOMBRE_NUM_ANOTACION_TEMPORAL);
                    ver= request.getParameter(nombreNumAnotacionTemporal);
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO, RPag.getAnotacionesActual());
            }
            else{
                    ver = request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);
            }
            request.getSession().setAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL, ver);

            // -------------------------------------------------------------------------------------
            // bind data2temp ----------------------------------------------------------------------
            // realizar la busqueda a traves de los parametros
            // TODO: (doEnd?)
            // realizado temporalmente sobre la jsp, tal y como se hace en calificacion (CORREC_EDITAR_ANOTACION)
            // -------------------------------------------------------------------------------------
            // doCorreccionEditarFolio_Anotacion_OnLoad_Data2Temp();


            return null;
         */
    } // end-method: do_Corr_PageEvent_FolioAnotacionXLoad

    private void
            doCorreccionEditarFolio_Anotacion_OnLoad_Data2Temp(HttpServletRequest request) {

    }

    /**
     * Guarda la información referente al folio y las anotaciones en la sesión.
     *
     * @param request
     * @return
     */
    private Evento preservarInfo(HttpServletRequest request) {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        return null;
    }

    /**
     * Permite guardar la información de una anotación, cuando se esta agregando
     * al folio.
     *
     * @param request
     * @return
     */
    private Evento cargarAnotacionAgregar(HttpServletRequest request) {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        request.getSession().setAttribute(AWModificarFolio.AGREGAR_ANOTACION, request.getParameter(AWModificarFolio.AGREGAR_ANOTACION));
        request.getSession().removeAttribute("cancelar");
        return null;
    }

    /**
     * Permite borrar de la sesión la información de la anotación que estaba
     * siendo objeto de corrección.
     *
     * @param request
     * @return
     */
    private Evento cancelarAnotacionSession(HttpServletRequest request) {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        request.getSession().removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        return null;
    }

    /**
     * Permite consultar un folio para observar su información.
     *
     * @param request
     * @return
     */
    private EvnFolio consultaFolio(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String idMatricula = request.getParameter(CFolio.ID_MATRICULA);

        if (idMatricula == null) {
            idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
        }

        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);

        return new EvnFolio(usuarioAuriga, EvnFolio.CONSULTA, folio);
    }

    /**
     * Permite consultar un folio para traer la información que puede ser
     * copiada a otros folios.
     *
     * @param request
     * @return
     */
    private EvnFolio consultaFolioDigitacionMasiva(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Circulo circ = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String idMatricula = circ.getIdCirculo() + "-";
        idMatricula += request.getParameter(CFolio.ID_MATRICULA);
        request.getSession().removeAttribute(WebKeys.MENSAJE);

        if (idMatricula == null) {
            idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
        }

        Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);

        EvnFolio evnFolio = new EvnFolio(usuarioAuriga, EvnFolio.CONSULTA_DIGITACION_MASIVA, folio);
        evnFolio.setUsuarioSIR(usuarioSIR);
        return evnFolio;
    }

    /**
     * Permite cargar el folio para que pueda ser editado.
     *
     * @param request
     * @return
     */
    private EvnCorreccion cargarFolioCorreccion(HttpServletRequest request) throws AccionWebException {
        EvnCorreccion eventoCorreccion = cargarFolio(request);
        eventoCorreccion.setCargarSalvedad(false);
        
        Turno param_Turno = (Turno) session.getAttribute(WebKeys.TURNO);
        java.util.List listaPermisos = null;
        if (param_Turno.getSolicitud() instanceof SolicitudCorreccion) {
            SolicitudCorreccion sol = (SolicitudCorreccion) param_Turno.getSolicitud();
            listaPermisos = sol.getPermisos();
        }
        
        if(listaPermisos != null){
        boolean permisoAreas = false;
        Iterator itPermisos = listaPermisos.iterator();
        while(itPermisos.hasNext()){
           SolicitudPermisoCorreccion validador = (SolicitudPermisoCorreccion) itPermisos.next();
           if(validador.getIdPermiso().equals("FOL_AREA")){
               permisoAreas = true;
           }
        }
        
        if(permisoAreas){
            request.getSession().setAttribute(PERMISO_AREA,"FOL_AREA");
        } else{
            request.getSession().setAttribute(PERMISO_AREA,"FOL_AREA_N");
        }
        }
        return eventoCorreccion;
    }

    /**
     * Permite cargar el folio para que pueda ser editado.
     *
     * @param request
     * @return
     */
    private EvnCorreccion cargarFolio(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        // se limpian se existen los campos de la edicion	
        request.getSession().removeAttribute(AWModificarFolio.ELEMENTO_EDITAR);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_VALOR1);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_VALOR2);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_EJE1);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_EJE2);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_ESPECIFICACION_DIR);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
        //fin de la limpieza

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
        eliminarInfoBasicaFolio();
        eliminarInfoBasicaAnotacion();
        eliminarInfoBasicaCiudadano();
        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        // * REMOVE?
        //session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        session.removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        // // FixThePaginatorValues
        // String nombreResultado = "RESULTADO2";//request.getParameter(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        // session.setAttribute( WebKeys.NOMBRE_RESULTADOS_PAGINADOR, nombreResultado );
        LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
        if (llaves != null) {
            llaves.removeLLaves(request);
        }
        request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

        if (idMatricula == null) {
            idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
        }

        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);

        // bug 3530/3560
        // remove state salvedad-folio
        do_FolioEdit_SalvedadFolio_RemoveState(request);

        EvnCorreccion local_Result;
        local_Result = new EvnCorreccion(usuarioAuriga, (Usuario) session.getAttribute(WebKeys.USUARIO), folio, EvnCorreccion.CONSULTAR_FOLIO);
        local_Result.setTurno(turno);

        return local_Result;
    }

    /**
     * Permite cargar el folio para que pueda ser editado.
     *
     * @param request
     * @return
     */
    private EvnCorreccion consultarFolioUsuario(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
        eliminarInfoBasicaFolio();
        eliminarInfoBasicaAnotacion();
        eliminarInfoBasicaCiudadano();
        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        //session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        session.removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (idMatricula == null) {
            idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
        }

        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);

        return new EvnCorreccion(usuarioAuriga, (Usuario) session.getAttribute(WebKeys.USUARIO), folio, EvnCorreccion.CONSULTAR_FOLIO_USUARIO);
    }

    /**
     * Buscar dentro de una lista de anotaciones la n-esima anotacion con id =
     * aplicacionNumero.
     */
    public static Anotacion doFindAnotacionById(List anotaciones, String aplicacionNumero) {

        if ((null == anotaciones)
                || (anotaciones.size() == 0)) {
            return null;
        }

        Anotacion anotacionTemp = null;
        for (Iterator iterator = anotaciones.iterator(); iterator.hasNext();) {

            Anotacion anotacionTempOnSearch = (Anotacion) iterator.next();
            String testAplicacionNumero = anotacionTempOnSearch.getIdAnotacion();

            if ((anotacionTempOnSearch.getIdAnotacion() != null)
                    && (testAplicacionNumero.equalsIgnoreCase(aplicacionNumero))) {

                anotacionTemp = anotacionTempOnSearch;
                break;

            }
        }
        return anotacionTemp;
    }

    /**
     * Buscar dentro de una lista de anotaciones la n-esima anotacion con orden
     * = aplicacionNumero.
     */
    public static Anotacion doFindAnotacionByOrden(List anotaciones, String aplicacionNumero, boolean castAsInteger) {
        if ((null == anotaciones)
                || (anotaciones.size() == 0)) {
            return null;
        }

        // orden de la anotacion as int.
        int testPosition = 0;
        int aplicacionNumeroAsInt = -2;

        if (castAsInteger) {
            try {
                aplicacionNumeroAsInt = Integer.parseInt(aplicacionNumero);
            } catch (NumberFormatException e) {
            }
        }

        Anotacion anotacionTemp = null;
        for (Iterator iterator = anotaciones.iterator(); iterator.hasNext();) {
            Anotacion anotacionTempOnSearch = (Anotacion) iterator.next();
            String testAplicacionNumero = anotacionTempOnSearch.getOrden();
            if (castAsInteger) {
                testPosition = -1;
                try {
                    testPosition = Integer.parseInt(
                            testAplicacionNumero);
                } catch (NumberFormatException e) {

                }
                if (aplicacionNumeroAsInt == testPosition) {
                    anotacionTemp = anotacionTempOnSearch;
                    break;
                }
            } else {
                if ((testAplicacionNumero != null)
                        && (testAplicacionNumero.
                                equalsIgnoreCase(aplicacionNumero))) {

                    anotacionTemp
                            = anotacionTempOnSearch;
                    break;

                }
            }
        }
        return anotacionTemp;
    }

    /**
     * Permite cargar una anotación para que pueda ser editada.
     *
     * @param request
     * @return
     */
    private Evento cargarAnotacion(HttpServletRequest request) throws AccionWebException {

        // ---------------------------------------------------------------------------------
        // eliminar los datos dirty en algunos campos;
        eliminarInfoBasicaAnotacion();
        eliminarInfoBasicaCiudadano();
        eliminarInfoBasicaVariosCiudadanos(request);

        session.removeAttribute(AWCalificacion.HAY_REFRESCO);
        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        session.removeAttribute("cancelar");

        // ---------------------------------------------------------------------------------
        preservarInfo(request);
        List anotaciones = null;

        String nombreResultado = null;
        nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);

        // cambio para incluir el uso del paginador
        // en la extraccion de las anotaciones,
        // y escoger la anotacion actual a editar.
        if ((null != nombreResultado)
                && (!"".equals(nombreResultado))) {

            DatosRespuestaPaginador RPag = null;//(DatosRespuestaPaginador) request.getSession().getAttribute( "RESULTADOS" );
            RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
            anotaciones = RPag.getAnotacionesActual();

            // compatibilidad con anteriores paginas
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
            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, list);

            // String nombreNumAnotacionTemporal= (String )request.getSession().getAttribute(WebKeys.NOMBRE_NUM_ANOTACION_TEMPORAL);
            // ver= request.getParameter(nombreNumAnotacionTemporal);
            // request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO, RPag.getAnotacionesActual());
            // request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, RPag.getAnotacionesActual());
        } else {
            anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        }

        if (anotaciones == null) {
            anotaciones = new Vector();
        }

        Vector vector = new Vector();

        Anotacion local_T1Anotacion = null;

        try {

            Anotacion anotacion = new Anotacion();
            Anotacion local_SelectedAnotacionToEdit = null;

            // ya no es un numero dentro de una lista;
            // se recibe como parametro el
            // id de la anotacion
            String aplicacionNumero = request.getParameter("NUM_A_TEMPORAL_CALIFICACION");//WebKeys.POSICION );

            local_SelectedAnotacionToEdit = doFindAnotacionById(anotaciones, aplicacionNumero);
            //anotacionTemp = doFindAnotacionByOrden( anotaciones, aplicacionNumero, true );

            // temporalmente: colocar el tipo de documento de la lista original
            temporalBlock:
            {
                List temporalListOfAnotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
                Anotacion temporalSelectedAnotaciones = doFindAnotacionByOrden(temporalListOfAnotaciones, aplicacionNumero, true);
                if (null != temporalSelectedAnotaciones) {
                    TipoDocumento tDoc = (null != temporalSelectedAnotaciones.getDocumento()) ? (temporalSelectedAnotaciones.getDocumento().getTipoDocumento()) : (null);
                    if (null != tDoc) {
                        local_SelectedAnotacionToEdit.getDocumento().setTipoDocumento(tDoc);
                    }

                }

            }

            // raise; deberia encontrar una
            if (null == local_SelectedAnotacionToEdit) {
                throw new AccionWebException("Anotacion No encontrada dentro de la lista de busqueda;");
            }

            // copia los datos de la anotacion
            // y los carga a los id's en sesion para que la pagina los
            // puede desplegar.
            // datos basicos
            anotacion = (Anotacion) copy(local_SelectedAnotacionToEdit);
            String modalidad = anotacion.getModalidad();
            if(modalidad != null){
            session.setAttribute(AWModificarFolio.ANOTACION_MODALIDAD, modalidad);
            }
            local_T1Anotacion = (Anotacion) copy(local_SelectedAnotacionToEdit);

            cargarInfoBasicaAnotacion(anotacion, request);

            // ciudadanos
            List ciudadanos = (List) anotacion.getAnotacionesCiudadanos();
            List ciudadanosSession = new Vector();
            ciudadanosSession.addAll(ciudadanos);

            String agregarAnotacion = request.getParameter(AWModificarFolio.AGREGAR_ANOTACION);
            session.setAttribute(AWModificarFolio.AGREGAR_ANOTACION, "" + agregarAnotacion);

            session.setAttribute(AWModificarFolio.POSICIONANOTACION, "" + aplicacionNumero);
            session.setAttribute(AWModificarFolio.ANOTACION_EDITADA, local_T1Anotacion);
            session.setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanosSession);
            session.setAttribute(AWModificarFolio.ANOTACION_COPIA, anotacion);

        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número de anotación a editar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (anotaciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice de la anotación es inválido");
            }
        }

        // Bug 3541
        // Cambio:
        // con el id de la anotacion seleccionadaç
        // debe ir y recuperar
        // el estado del folio en t0 y t1.
        // inicialmente solo se recupera el estado en t0
        // ya que el estado en t1 esta en sesion
        // session data -----------------------------------------------------------------
        org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
        param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo param_Circulo;
        param_Circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        Turno param_Turno;
        param_Turno = (Turno) session.getAttribute(WebKeys.TURNO);

        java.util.List listaPermisos = null;
        if (param_Turno.getSolicitud() instanceof SolicitudCorreccion) {
            SolicitudCorreccion sol = (SolicitudCorreccion) param_Turno.getSolicitud();
            listaPermisos = sol.getPermisos();
        }

        if (listaPermisos != null && local_T1Anotacion != null) {
            //si la anotacion actual es temporal y nunca ha sido definitiva y ademas, el que esta realizand
            if (local_T1Anotacion.isTemporal() && !local_T1Anotacion.isTemporalConContraparteDefinitiva()) {
                List permisosAdicionales = new ArrayList();
                SolicitudPermisoCorreccion solicitud = new SolicitudPermisoCorreccion();
                solicitud.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_CIUDADANOS_ID);
                SolicitudPermisoCorreccion anotaciondocid = new SolicitudPermisoCorreccion();
                anotaciondocid.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_DOCUMENTO_ID);
                SolicitudPermisoCorreccion anotacionespec = new SolicitudPermisoCorreccion();
                anotacionespec.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_ESPECIFICACION_ID);
                SolicitudPermisoCorreccion anotacionfecha = new SolicitudPermisoCorreccion();
                anotacionfecha.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_FECHA_ID);
                SolicitudPermisoCorreccion anotacionnumeroid = new SolicitudPermisoCorreccion();
                anotacionnumeroid.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_NUMERO_ID);
                SolicitudPermisoCorreccion anotacionradicacionmanual = new SolicitudPermisoCorreccion();
                anotacionradicacionmanual.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_RADICACIONMANUAL_ID);
                SolicitudPermisoCorreccion anotacionroldeciudadanos = new SolicitudPermisoCorreccion();
                anotacionroldeciudadanos.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_ROLDECIUDADANOSENANOTACION_ID);
                SolicitudPermisoCorreccion anotacioncancelacion = new SolicitudPermisoCorreccion();
                anotacioncancelacion.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_TCANCELASELECT_ID);
                SolicitudPermisoCorreccion anotacionvaloracto = new SolicitudPermisoCorreccion();
                anotacionvaloracto.setIdPermiso(PermisosCorreccionAspectModelConstants.ANOTACION_VALORACTO_ID);
                permisosAdicionales.add(solicitud);
                permisosAdicionales.add(anotaciondocid);
                permisosAdicionales.add(anotacionespec);
                permisosAdicionales.add(anotacionfecha);
                permisosAdicionales.add(anotacionnumeroid);
                permisosAdicionales.add(anotacionradicacionmanual);
                permisosAdicionales.add(anotacionroldeciudadanos);
                permisosAdicionales.add(anotacioncancelacion);
                permisosAdicionales.add(anotacionvaloracto);
                for (Iterator permisosItera = listaPermisos.iterator(); permisosItera.hasNext();) {
                    SolicitudPermisoCorreccion permisoCorr1 = (SolicitudPermisoCorreccion) permisosItera.next();
                    for (Iterator permisosAdItera = permisosAdicionales.iterator(); permisosAdItera.hasNext();) {
                        SolicitudPermisoCorreccion permisoCorr2 = (SolicitudPermisoCorreccion) permisosAdItera.next();
                        if (permisoCorr1.getIdPermiso() != null && permisoCorr1.getIdPermiso().equals(permisoCorr2.getIdPermiso())) {
                            permisosAdItera.remove();
                        }
                    }
                }
                permisosAdicionales.addAll(listaPermisos);
                listaPermisos = permisosAdicionales;
            }
        }
        session.setAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST, listaPermisos);

        // build-message -----------------------------------------------------------------
        EvnCorreccion local_Result;
        local_Result = new EvnCorreccion(EvnCorreccion.CORRECCIONSIMPLEMAIN_LOADFOLIOANOTACIONXOPTION_BTNSTART__EVENT);
        // ---------------------------------------------------------------------------------
        // get-set injection --------------------------------------------------------------
        local_Result.setUsuarioAuriga(param_UsuarioAuriga); // o
        local_Result.setUsuarioSir(param_UsuarioSir);       // *
        local_Result.setTurno(param_Turno);                 // *
        local_Result.setT1_Anotacion(local_T1Anotacion);
        // ---------------------------------------------------------------------------------

        // send-message -----------------------------------------------------------------
        return local_Result;
        // ---------------------------------------------------------------------------------
    }

    protected gov.sir.core.web.helpers.correccion.region.model.ProcessManager
            doExtractAuthorization_ProcessManager(HttpServletRequest request, java.util.List modelPermisosList) {
        // decodificar permisos para la parte web/
        // mapa de permisos model
        java.util.HashMap modelPermisosMap = new java.util.HashMap();

        // populate the model
        String modelPermisosMap_Key = null;
        gov.sir.core.web.helpers.correccion.region.model.Process.AspectModel modelPermisosMap_Value = null;

        // cargar model permisos
        java.util.Iterator iterator
                = modelPermisosList.iterator();

        for (; iterator.hasNext();) {
            gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion permiso
                    = (gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion) iterator.next();

            modelPermisosMap_Key = permiso.getIdPermiso();//gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.FOLIO_NUMEROCATASTRAL_ID ;
            modelPermisosMap_Value = new gov.sir.core.web.helpers.correccion.region.model.Process.AspectModel(modelPermisosMap_Key);
            modelPermisosMap_Value.setNivelPermiso(gov.sir.core.web.helpers.correccion.region.model.Process.AspectModel.UNLOCK); // LOCK no se evalua

            modelPermisosMap.put(modelPermisosMap_Key, modelPermisosMap_Value);

        } // for

        // filtrarPermisos dados al usuario ---------------------------------------------------
        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager
                = new gov.sir.core.web.helpers.correccion.region.model.EditFoliosProcessManager();

        localProcessManager.filter(modelPermisosMap);

        // con el localProcessManager se filtran los delta a aplicar
        // -------------------------------------------------------------------
        return localProcessManager;
    } // end-method

    /**
     * Cargar el set de permisos de la sesion
     */
    protected gov.sir.core.web.helpers.correccion.region.model.ProcessManager
            doExtractAuthorization_ProcessManager(HttpServletRequest request) {
        java.util.List modelPermisosList = null;
        modelPermisosList = (java.util.List) session.getAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST);

        return doExtractAuthorization_ProcessManager(request, modelPermisosList);

    } // end-method

// ----------------------------------------------------------------------------------------------
    protected EvnCorreccion
            doFolioEdit_Anotacion_SaveChanges(HttpServletRequest request)
            throws AccionWebException {
        return doFolioEdit_Anotacion_SaveChanges(request, false);
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

    private void
            do_FolioEdit_SalvedadFolio_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            ITEMID_FOLIOEDICION_SALVEDADFOLIOID,
            ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_FolioEdit_SalvedadFolio_SaveState

    private void
            do_FolioEdit_SalvedadFolio_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            ITEMID_FOLIOEDICION_SALVEDADFOLIOID,
            ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_FolioEdit_SalvedadFolio_RemoveState

// ----------------------------------------------------------------------------------------------
    /**
     * edicion de anotacion
     *
     */
    protected EvnCorreccion
            doFolioEdit_Anotacion_SaveChanges(HttpServletRequest request, boolean enableCancelaciones)
            throws AccionWebException {

        // Log.getInstance().debug(AWModificarFolio.class, "CANCELADA ES ");
        // Log.getInstance().debug(AWModificarFolio.class, request.getParameter("ESCOGER_ANOTACION_CANCELACION"));
        // cargar conjunto de permisos de la sesion aplicando el filtro de
        // permisos segun las autorizaciones dadas.
        // con el localProcessManager se filtran los delta a aplicar
        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager
                = doExtractAuthorization_ProcessManager(request);

        String tipoCambio = null;
        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        // temp2cache
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaCiudadano(request);
        // bug 3530 /3560
        // temp2cache
        // do_FolioEdit_SalvedadFolio_SaveState( request );

        // Bug 3565
        Turno local_Turno;
        local_Turno = (Turno) session.getAttribute(WebKeys.TURNO);

        // Atributos en obj session
        // -------------------------------------------------------------------
        // obtener la lista de anotaciones
        // filtrada por un paginador
        List anotaciones = null;
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
            anotaciones = RPag.getAnotacionesActual();
        } else {
            anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        }

        String currentIdAnotacion = null;
        Anotacion t0_folio_anotacion = null; // objeto en t0 (anotacion inicial)

        // obtiene el id de la anotacion a modificar
        // trabajar con orden temporalmente, por compatibilidad con registro.calificacion
        currentIdAnotacion = (String) session.getAttribute(AWModificarFolio.POSICIONANOTACION);

        // trata de recuperar
        // el objeto en t-1
        //anotacion = this.doFindAnotacionById( anotaciones,currentIdAnotacion );
        t0_folio_anotacion = (Anotacion) session.getAttribute(AWModificarFolio.ANOTACION_EDITADA);// doFindAnotacionByOrden( anotaciones, currentIdAnotacion, true );

        if (null == t0_folio_anotacion) {
            throw new AccionWebException("No se ha encontrado la anotacion a modificar");
        }

        boolean convertirCanceladoraAEstandar = session.getAttribute(CONVERTIR_CANCELADORA_ESTANDAR) != null;

        // se construye la anotacion t1, t2
        // cambios en sesion
        Anotacion t1_folio_anotacion;
        // variaciones respecto al modelo
        Anotacion t2_folio_anotacion;

        // bind form2temp ------------------------------------------------------------------
        //   ----------------------------------
        // cargar datos de request; colocar los
        // datos extraidos en variables temporales
        // anotacion.fechaRadicacion
        String valFechaRadicacion = request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION);
        java.util.Calendar fechaRadicacion_t1 = darFecha(valFechaRadicacion);
        // anotacion.numRadicacion
        String anotacion_t1_NumRadicacion = request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION);

        // anotacion.orden
        String strOrden = request.getParameter(AWModificarFolio.ANOTACION_ORDEN);
        // Documento.datosbasicos
        String tipoDocumento = request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
        String parameter_anotacion_documento_tipoDocumento_IdTipoDocumento = null;
        String parameter_anotacion_documento_tipoDocumento_nombre = null;
        String numDocumento = request.getParameter(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);
        String valFechaDocumento = request.getParameter(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
        java.util.Calendar fechaDocumento = darFecha(valFechaDocumento);
        // anotacion.ciudadanos
        java.util.List ciudadanos = (java.util.List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        /*
   // Datos de anotacion.documento ---------------------------------------------------
     // Documento.datosbasicos
   String idTipoDoc = request.getParameter(CDocumento.ID_TIPO_DOCUMENTO); // id escrito (idTipoDoc==tipoDoc)?
   String tipoDoc = request.getParameter(CDocumento.TIPO_DOCUMENTO); // id seleccionado (idTipoDoc==tipoDoc)?
   String parameter_folio_documento_tipoDocumento_IdTipoDocumento = null; // se verifica su valor sobre una lista
   String parameter_folio_documento_tipoDocumento_nombre = null; // este valor se obtendra al recorrer la lista de la sesion
   String numDoc = request.getParameter(CDocumento.NUM_DOCUMENTO);
   String fechaRad = request.getParameter(CDocumento.FECHA_RADICACION);
   java.util.Calendar documento_fecha_t1 = darFecha( fechaRad );
         */
        String idDoc = (null != t0_folio_anotacion.getDocumento()) ? (t0_folio_anotacion.getDocumento().getIdDocumento()) : (null); //TODO: ajuste temporal; colocar el id, si en t0 existe;// request.getParameter(CDocumento.ID_DOCUMENTO);

        // Documento.oficinaProcedencia.locators
        String valorDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String nomDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String valorMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String nomMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String valorVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String nomVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        // Documento.oficinaProcedencia
        String idOficinaOrigen = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
    *  @author Carlos Torres
    *  @chage   se agrega validacion de version diferente
    *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        if (oficinaVersion == null || "".equals(oficinaVersion)) {
            if (request.getSession().getAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
                oficinaVersion = request.getSession().getAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION).toString();
            } else {
                oficinaVersion = "1";
            }
        }
        String numOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String nomOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Se define la variable version
         */
        String version = null;
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : se obtiene el valor del parametro
         */
        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER) != null) {
            version = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
        } else if (session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER) != null) {
            version = session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER).toString();
        } else {
            version = "0";
        }

        /**
         * @author : Ellery David Robles Gómez.
         * @casoMantis : 08375.
         * @actaRequerimiento : 261 - Correccion Oficina de Origen
         * Internacional.
         * @change : Se agrega la variable "oficinaInternacional" para capturar
         * su valor de la respuesta.
         */
        String oficinaInternacional = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL);
        // Documento.oficinaProcedencia.vereda
        delegatedConvertor_Documento_OficinaProcedencia_Vereda:
        {

            if ((valorVereda == null || (valorVereda.trim().equals("")))
                    || nomDepartamento.equals("")
                    || nomMunicipio.equals("")) {
                valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), valorDepartamento, valorMunicipio);
            }

        } // :delegatedConvertor_Documento_OficinaProcedencia_Vereda

        // Datos Anotacion / Naturaleza juridica
        String idNaturaleza = null;
        String nomNatJuridica = null; //  este valor no esta con binding; VERIFY: naturalezaJuridica.setNombre(nomNatJuridica);
        String modalidad = null;
        String comentario = null;
        String estadoAnotacion = null;

        idNaturaleza = request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        nomNatJuridica = request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        modalidad = request.getParameter(AWModificarFolio.ANOTACION_MODALIDAD);
        comentario = request.getParameter(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        estadoAnotacion = request.getParameter(AWModificarFolio.ANOTACION_ESTADO);

        // Datos Anotacion / Salvedad
        // salvedad no incluido en politicas de acceso
        // bug 3530 / 3560
        // comentar salvedad
        // aclaracion: dejar edicion salvedad
        // folio.salvedad
        String param_AnotacionSalvedadX_Descripcion = request.getParameter(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
        String param_AnotacionSalvedadX_Id = request.getParameter(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID);

        String param_AnotacionSalvedadX_NumRadicacion;
        param_AnotacionSalvedadX_NumRadicacion = local_Turno.getIdWorkflow();

        // param_SalvedadFolio_Id
        if ((null != param_AnotacionSalvedadX_Id)
                && ("".equals(param_AnotacionSalvedadX_Id.trim()))) {
            param_AnotacionSalvedadX_Id = null;
        } // if

        // String salvedad = request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        // Datos Anotacion / Especificacion / valor
        String numEspecificacion = request.getParameter(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION);

        // Convertor:
        // Eliminar errores causados por el formatter usado en la vista;
        // usar una expresion regular : "*,*" -> ""
        numEspecificacion = (null == numEspecificacion) ? (null) : (numEspecificacion.replaceAll(",", ""));

        // TODO: Pregunta: aplicar convertor al dato en t0 ?
        // validaciones (stage 1): ---------------------------------------------------------------------
        // Estos son los validadores que aplican siempre,
        // independientemente si existen o no cambios en los campos
        exception = new ValidacionParametrosException();
        
        if(idNaturaleza != null){
        if(idNaturaleza.equals("0125") || idNaturaleza.equals("0126")){
            if(modalidad == null || modalidad.equals(WebKeys.SIN_SELECCIONAR)){
                exception.addError("Debe escoger una modalidad para la naturaleza juridica seleccionada");
            }
        }
            }

        // ---------------------------------------------------------------------------------------------
        Iterator ica = ciudadanos.iterator();
        List anotacionesCiudadanos = new ArrayList();
        while (ica.hasNext()) {
            AnotacionCiudadano ciudadano = (AnotacionCiudadano) ica.next();
            if (!ciudadano.isToDelete()) {
                anotacionesCiudadanos.add(ciudadano);
            }
        }

        if (anotacionesCiudadanos.isEmpty() || anotacionesCiudadanos.size() == 0) {
            exception.addError("ciudadanos; debe escribir por lo menos un ciudadano");
        }
        String validatorManagerItem_fieldId;
        BasicConditionalValidator stage1_validator;

        // bug 3530/3560
        // + aclaracion: dejar las validaciones que existian
        // 1: validar salvedad
        // 1:A not null
        stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
        stage1_validator.setObjectToValidate(param_AnotacionSalvedadX_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("anotacion:salvedad; debe escribir un valor en la salvedad");
        }

        // 1:B maxima longitud
        stage1_validator = new BasicStringMaxLengthValidatorWrapper(1024);
        stage1_validator.setObjectToValidate(param_AnotacionSalvedadX_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("anotacion:salvedad; la salvedad debe tener maximo de 1024 caracteres");
        }

        // 1:C minima longitud
        stage1_validator = new BasicStringMinLengthValidatorWrapper(30);
        stage1_validator.setObjectToValidate(param_AnotacionSalvedadX_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("anotacion:salvedad; la salvedad debe tener mas de 30 caracteres");
        }

        // 3: validar estado anotacion
        // 3:A inicialmente no se valida; se valida luego, viendo si tiene cambios ?
        /*

    stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
    stage1_validator.setObjectToValidate( estadoAnotacion );
    stage1_validator.validate();

    if( stage1_validator.getResult() != true ) {
        exception.addError( "anotacion:estado; el estado de una anotacion no puede ser nulo" );
    }
         */
        // 3:B not equals to WebKeys.SIN_SELECCIONAR
        stage1_validator = new BasicStringPatternValidatorWrapper(WebKeys.SIN_SELECCIONAR, true, true);
        stage1_validator.setObjectToValidate(estadoAnotacion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("anotacion:estado; debe escoger un estado valido");
        }

        // 4.A para poder comparar el valor en la especificacion
        // los valores a comparar deben ser del mismo tipo;
        // por esto, se obliga a que el valor sea nulo o sea numerico siempre
        // 16.0:A valor nulo
        stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
        stage1_validator.setObjectToValidate(numEspecificacion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            // el valor es nulo; pasa la prueba
            // exception.addError( "anotacion:estado; debe escoger un estado valido" );
        } else {
            // el valor no es nulo;
            // probar si es numerico

            // 16.0:B valor numerico
            stage1_validator = new BasicDatatypeString2DoubleConversionValidatorWrapper();
            stage1_validator.setObjectToValidate(numEspecificacion);
            stage1_validator.validate();

            if (stage1_validator.getResult() != true) {
                exception.addError("especificacion-valor; acepta valores numericos");
            }

        }

        // adicion: bug 3569
        // fecha radicacion no puede ser anterior a fecha de documento de la anotacion
        // 3: validar tipoDocumento.ElementInList    (este solo deja en nulo un capo si no encuentra un valor)
        //    para que funcione con las condiciones de activacion para un tipo de documento determinado.
        //    solo hace un raise inmediato si la lista no se encontro en la sesion.
        //    tambien se verifica que exista por lo menos uno de los valores tipoDoc o idTipoDoc dentro de la lista
        //    primero se evalua por el numero escrito; luego se evalua pro el numero seleccionado
        delegatedValidator_Folio_Documento_TipoDocumento_ElementInList:
        {

            List tiposDocumento = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);

            boolean elementFounded = false;

            if (null != tiposDocumento) {
                Iterator iterator = tiposDocumento.iterator();
                for (; iterator.hasNext();) {

                    ElementoLista element = (ElementoLista) iterator.next();

                    // politica: primero encontrado
                    // if( element.getId().equals( idTipoDoc ) ){
                    //    parameter_folio_documento_tipoDocumento_IdTipoDocumento = element.getId();
                    //    parameter_folio_documento_tipoDocumento_nombre          = element.getValor();
                    //    elementFounded = true;
                    //    break;
                    // }
                    if (element.getId().equals(tipoDocumento)) {
                        parameter_anotacion_documento_tipoDocumento_IdTipoDocumento = element.getId();
                        parameter_anotacion_documento_tipoDocumento_nombre = element.getValor();
                        elementFounded = true;
                        break;
                    }
                }
                // if( !elementFounded ){
                //    exception.addError( "tipoDocumento; no se encontro el valor seleccionado" );
                // }
            } else {
                exception.addError("tipoDocumento; La lista de los tipos de documento no se encontro");
            }
        }

        // -------------------------------------------------------------------------------------
        // bug 3562 (part2)
        // recopilar las anotaciones canceladoras
        // en el caso en el cual el tipo de anotacion es canceladora
        // [validation]
        boolean activateEvaluation;

        activateEvaluation = ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:anotacionesCancelacion");

        String[] local_ListAnotacionIdsToBeCanceled = null;

        if (enableCancelaciones && activateEvaluation) {

            local_ListAnotacionIdsToBeCanceled = (String[]) session.getAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);

            if ((null == local_ListAnotacionIdsToBeCanceled)
                    || (local_ListAnotacionIdsToBeCanceled.length == 0)) {

                exception.addError("Debe seleccionar por lo menos una anotación para cancelar");
                local_ListAnotacionIdsToBeCanceled = new String[0];
            }

        } // if

        // -------------------------------------------------------------------------------------
        double local_AnotacionMaxOrderAllowed;
        String local_AnotacionMaxOrderAllowedAsString;
        local_AnotacionMaxOrderAllowed = 0.0d;
        local_AnotacionMaxOrderAllowedAsString = "";

        java.math.BigDecimal param_AnotacionMaxOrderAllowed;
        param_AnotacionMaxOrderAllowed = (java.math.BigDecimal) session.getAttribute(WebKeys.TOTAL_ANOTACIONES);

        // TODO: deberia tener un valor
        if (null != param_AnotacionMaxOrderAllowed) {
            local_AnotacionMaxOrderAllowed = param_AnotacionMaxOrderAllowed.doubleValue();
            local_AnotacionMaxOrderAllowedAsString = BasicConvertorActions.toString(local_AnotacionMaxOrderAllowed, "######");
        } else {
            exception.addError("No se puede encontrar el id:TOTAL_ANOTACIONES, para realizar las validaciones sobre el orden de la anotacion");
        }

        // tfs 3159
        /**
         * @Autor: Edgar Lora
         * @Mantis: 0010688
         * @Requerimiento: 066_151
         * @Descripcion: Se cambio la fecha para la validacion de cambio de
         * naturaleza juridica.
         */
        String valFechaRadicacionAnotacion = request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION);
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaRadicacion = null;
        if (valFechaRadicacionAnotacion != null) {
            try {
                fechaRadicacion = formatoDelTexto.parse(valFechaRadicacionAnotacion);
            } catch (Exception ex) {
                Logger.getLogger(AWModificarFolio.class.getName()).log(Level.SEVERE, null, ex);
                fechaRadicacion = null;
            }
        } else {
            fechaRadicacion = t0_folio_anotacion.getFechaRadicacion();
        }
        Date fechaRadicacionFolio = null;
        String fechaAperturaFolio = (String) session.getAttribute(AWModificarFolio.FOLIO_FECHA_APERTURA);
        if (fechaAperturaFolio != null) {
            try {
                fechaRadicacionFolio = formatoDelTexto.parse(fechaAperturaFolio);
            } catch (Exception ex) {
                Logger.getLogger(AWModificarFolio.class.getName()).log(Level.SEVERE, null, ex);
                fechaRadicacionFolio = null;
            }
        }

        Date fechaComparacion = null;
        try {
            fechaComparacion = formatoDelTexto.parse(CNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS);
        } catch (Exception ex) {
            Logger.getLogger(AWModificarFolio.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean mostrarAntiguas = true;
        boolean mostrarNuevas = true;
        if (fechaRadicacion != null && idNaturaleza != null && fechaRadicacionFolio != null) {
            NaturalezaJuridica nat = t0_folio_anotacion.getNaturalezaJuridica();
            if (local_Turno == null || local_Turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCION)) {
                if (fechaRadicacion.before(fechaComparacion) && fechaRadicacion.compareTo(fechaComparacion) != 0) {
                    if (fechaRadicacionFolio.before(fechaComparacion) && fechaRadicacionFolio.compareTo(fechaComparacion) != 0 && !idNaturaleza.equals(nat.getIdNaturalezaJuridica()) && idNaturaleza.length() == 4) {
                        exception.addError("Naturaleza juridica no coincide con fecha de radicación: El código de la naturaleza Juridica debe ser de 3 dígitos");
                    }
                } else {
                    if (!idNaturaleza.equals(nat.getIdNaturalezaJuridica()) && idNaturaleza.length() == 3) {
                        exception.addError("Naturaleza juridica no coincide con fecha de radicación: El código de la naturaleza Juridica debe ser de 4 dígitos");
                    }
                }
            } else {
                if (fechaRadicacion != null) {
                    if (fechaRadicacion.before(fechaComparacion)) {
                        if (nat != null && idNaturaleza != null && !idNaturaleza.equals(nat.getIdNaturalezaJuridica())
                                && (idNaturaleza.length() == 4
                                && !(nat.getIdNaturalezaJuridica().equals("915") || nat.getIdNaturalezaJuridica().equals("999")))) {
                            exception.addError("Naturaleza juridica no coincide con fecha de radicación de documento: El código de la naturaleza Juridica debe ser de 3 dígitos");
                        }
                    }
                }
            }
        }

        // validaciones (stage 1): raise ---------------------------------------------------------------
        if (exception.getErrores().size() > 0) {
            ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
            throw ex1;
        }

        // ---------------------------------------------------------------------------------------------
        // no se debe evaluar todo
        // el objeto; solamente se debe evaluar
        // un subconjunto del arbol Anotacion
        BindManager bindManager = new FolioAnotacionEditBindManager();

        DeltaAnalysisParams deltaParams = new DeltaFolioAnotacionAnalysisParams(bindManager, localProcessManager);

        String[] jxpath_DeltaPaths = deltaParams.getDeltaPaths();
        java.util.Comparator[] comparatorsToApply = deltaParams.getComparators();
        boolean[] jxpath_AuthAccess = deltaParams.getAuthAccess();

        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento").setValue(valorDepartamento);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/nombre").setValue(nomDepartamento);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/idMunicipio").setValue(valorMunicipio);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/nombre").setValue(nomMunicipio);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/idVereda").setValue(valorVereda);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/nombre").setValue(nomVereda);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/idOficinaOrigen").setValue(idOficinaOrigen);
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/version").setValue((oficinaVersion != null && !"".equals(oficinaVersion)) ? Long.parseLong(oficinaVersion) : null);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/numero").setValue(numOficina);
//  bindManager.getBinderById( "field:folio:anotacion[i]:documento/oficinaOrigen/nombre"                                       ).setValue( null ); // //nomOficina; // porque esta llegando nulo en t0 y al realizar el delta tendra un valor y fallaria en la insercion // detecta variacion porque es nulo si ya tiene algun valor; por esta razon se elimina de la comparacion
        /**
         * @author : Ellery David Robles Gómez.
         * @casoMantis : 08375.
         * @actaRequerimiento : 261 - Correccion Oficina de Origen
         * Internacional.
         * @change : Se setea el valor de "oficinaInternacional" a bindManager
         * array que contiene los campos de la anotacion:documento.
         */
        bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaInternacional").setValue(oficinaInternacional);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/idDocumento").setValue(idDoc); // idDoc ?? no hay ninguno
        bindManager.getBinderById("field:folio:anotacion[i]:documento/tipoDocumento/idTipoDocumento").setValue(parameter_anotacion_documento_tipoDocumento_IdTipoDocumento);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/tipoDocumento/nombre").setValue(parameter_anotacion_documento_tipoDocumento_nombre);// tipoDoc??
        bindManager.getBinderById("field:folio:anotacion[i]:documento/numero").setValue(numDocumento);
        bindManager.getBinderById("field:folio:anotacion[i]:documento/fecha").setValue((null == fechaDocumento) ? (null) : (fechaDocumento.getTime()));

        bindManager.getBinderById("field:folio:anotacion[i]:orden").setValue(strOrden);
        bindManager.getBinderById("field:folio:anotacion[i]:fechaRadicacion").setValue((null == fechaRadicacion_t1) ? (null) : (fechaRadicacion_t1.getTime()));
        bindManager.getBinderById("field:folio:anotacion[i]:numRadicacion").setValue(anotacion_t1_NumRadicacion);

        bindManager.getBinderById("field:folio:anotacion[i]:valor").setValue( /*Double.valueOf(*/numEspecificacion /*)*/); // double?
        bindManager.getBinderById("field:folio:anotacion[i]:naturalezaJuridica/idNaturalezaJuridica").setValue(idNaturaleza);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Se establese el valor de la propiedad version
         */
        bindManager.getBinderById("field:folio:anotacion[i]:naturalezaJuridica/version").setValue(Long.parseLong(version));
        bindManager.getBinderById("field:folio:anotacion[i]:comentario").setValue(comentario);

        bindManager.getBinderById("field:folio:anotacion[i]:estado/idEstadoAn").setValue(estadoAnotacion);

        // colocar los valores en el binder que se van a validar y que aplican para cambio delta
        java.lang.Object[] t1_folio_documento_values = new java.lang.Object[]{
            bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/nombre").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/idMunicipio").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/nombre").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/idVereda").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/nombre").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/idOficinaOrigen").getValue() /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */,
            bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/version").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/numero").getValue() //       , bindManager.getBinderById( "field:folio:anotacion[i]:documento/oficinaOrigen/nombre"                                       ).getValue()
            ,
             bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaInternacional").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/idDocumento").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/tipoDocumento/idTipoDocumento").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/tipoDocumento/nombre").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/numero").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:documento/fecha").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:orden").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:fechaRadicacion").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:numRadicacion").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:valor").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:naturalezaJuridica/idNaturalezaJuridica").getValue() /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change : Se obtiene el valor de la propiedad version
             */
            ,
             bindManager.getBinderById("field:folio:anotacion[i]:naturalezaJuridica/version").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:comentario").getValue(),
            bindManager.getBinderById("field:folio:anotacion[i]:estado/idEstadoAn").getValue()
        };

        /*
  double anotacion_t0_valorEspecificacion = anotacion.getValor();
  double anotacion_t1_valorEspecificacion = valorEspecificacion;

  BasicComparatorComponent comparator;

  comparator = new BasicDoublePlusThresholdComparator( 0.00005d );
  comparator.compare( new Double( anotacion_t0_valorEspecificacion ), new Double( anotacion_t1_valorEspecificacion )  ) != 0

         */
        // binding (delta)
        t2_folio_anotacion = new Anotacion();
        // binding (t1)
        t1_folio_anotacion = new Anotacion();
        t1_folio_anotacion = buildAnotacion(t1_folio_anotacion, jxpath_DeltaPaths, t1_folio_documento_values);

        // cambiado para que observe las diferencias solo so la persona esta autirizada para
        // verlas; el problemna es que si no esta autorizado,
        // no tendria valores en sesion, y por tanto, estos valores llegarian nulos;
        // si existia algo en el objeto diferente de null en (t0), al compararlo contra un null
        // en (t1), generaria un cambio : volverlo a null
        // lo que no se puede permitir, porque puede generar errores
        // TODO: realizar el cambio para folio tambien si se presentan fallas
        BasicJXPathForwardDiffProcessor processor;

        AbstractFactory abstractFactory = new FolioAnotacionAbstractFactory();

        processor = jx_ProcessForwardDifferences(
                jxpath_DeltaPaths,
                comparatorsToApply,
                jxpath_AuthAccess,
                t0_folio_anotacion,
                t1_folio_anotacion,
                t2_folio_anotacion,
                abstractFactory
        );

        /**
         * En caso de que se borre el comentario de la anotacion
         */
        if (comentario != null && comentario.equals("")) {
            t2_folio_anotacion.setComentario("");
        }
        // aplicar validaciones

        // validaciones (stage 2): ---------------------------------------------------------------------
        // Estos son los validadores que aplican si hubo cambios,
        // y si existen permisos sobre los campos.
        exception = new ValidacionParametrosException();

        // ---------------------------------------------------------------------------------------------
        ValidatorManager validatorManager = new ValidatorManager();

        validatorManagerItem_fieldId = "";
        ConditionalValidator validatorManagerItem_conditionalValidator;
        String validatorManagerItem_errorMessage;

        // para cada uno de los items a validar cuando cambien:
        // escribe condicionales para el validador y escribe el campo a validar
        // agrega registro al validator manager
        // 1:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:oficina de procedencia:departamento; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 2:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/nombre";
        //TODO: element in list validator
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:departamento; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 3:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/idMunicipio";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 4:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/nombre";
        // TODO: element in list validator
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:departamento; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 5:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/idVereda";
        // VERIFY: no tiene validadores
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 6:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/nombre";
        // VERIFY: no tiene validadores
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 7:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/idOficinaOrigen";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:oficinaOrigen; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 7.5
        // VERIFY: no tiene validadores
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/numero";
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 8:
        //validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/nombre";
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaInternacional";
        // VERIFY: no tiene validadores
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 9:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/idDocumento";
        // VERIFY: no tiene validadores: este valor es asignado al realizar la insercion
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 10.A:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/tipoDocumento/idTipoDocumento";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:tipo documento; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));
        // 10.B: (TODO: agregar esta validacion tambien a folio)
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/tipoDocumento/idTipoDocumento";
        validatorManagerItem_conditionalValidator = new BasicStringPatternValidatorWrapper(WebKeys.SIN_SELECCIONAR, true, true);
        validatorManagerItem_errorMessage = "documento:tipo documento; debe escribir un valor valido para este campo";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 11:
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/tipoDocumento/nombre";
        // TODO: element in list validator
        // TODO: revisar si en el caso de edicion de documento, este valor esta repetido en el numeral 15:A:
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:departamento; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 12:A
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/numero";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:numero; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 12:B
        // VERIFY: es requerido ?
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/numero";
        validatorManagerItem_conditionalValidator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:numero; este campo acepta valores numericos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 13:A
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/fecha";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:fecha; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, valFechaDocumento, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 13:B (se debe probar que la fecha se haya escrito en un formato valido; usa el objeto original del request para hacer la prueba)
        // inicialmente aplica un validador de objeto no nulo, al resultado de la funcion dar_fecha, que prueba la conversion;
        // TODO: hacer validador de fecha con un formato determinado.
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/fecha";
        validatorManagerItem_conditionalValidator = new BasicObjectNotNullValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:fecha; debe escribir una fecha con formato valido";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, fechaDocumento, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 13.5:A
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:fechaRadicacion";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "fecha-radicacion; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, valFechaRadicacion, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 13.5:B
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:fechaRadicacion";
        validatorManagerItem_conditionalValidator = new BasicObjectNotNullValidatorWrapper();
        validatorManagerItem_errorMessage = "fecha-radicacion; debe escribir una fecha con formato valido";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, fechaRadicacion_t1, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 14:A (en este caso, la condicion siempre se evalua, sin tener en cuenta los permisos ni las variaciones ) // cambiado: ahora se valida
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:estado/idEstadoAn";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:estado; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 14:B
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:estado/idEstadoAn";
        validatorManagerItem_conditionalValidator = new BasicStringPatternValidatorWrapper(WebKeys.SIN_SELECCIONAR, true, true);
        validatorManagerItem_errorMessage = "documento:estado; debe escribir un valor valido";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 14:B: Estado de anotacion; se necesita solo concer si varió idEstadoFolio
        //    y si varió, debe colocar algo en el valor de comentario de estado
        //    No se mira el cambio en los permisos
        // VERIFY:ver si existe una validacion similar a la de folio con comentario en la variacion de una anotacion
        // validatorManagerItem_fieldId = "field:folio:anotacion[i]:estado/idEstadoAn";
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "estadoFolio; se ha detectado que este campo ha variado; debe escribir un comentario justificando el cambio de estado";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, <??>, true, false );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/numero";
        validatorManagerItem_conditionalValidator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:numero; este campo acepta valores numericos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 16:a; validar que si varian datos en la oficina de procedencia,
        // se escriba o especifique la oficina origen
        // puede que en si la oficina origen no este variando; pero si debe tener permisos para cambiar el campo
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/idOficinaOrigen";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "oficina origen; se han detectado cambios en datos de la oficina origen; se debe elegir el set de datos de la oficina.";
        // aparte de las condiciones basicas se registra otra condicion: la de la variacion de los campos de ubicacion de oficina
        write_altern_conditions:
        {

            String fieldId;
            BindManager.Binder searchedBind;
            String path;
            boolean[] conditions = new boolean[4];

            int conditionCount = 0;

            fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            conditions[conditionCount++] = processor.pathHasChanges(path); // el atributo ha cambiado

            fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/idMunicipio";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            conditions[conditionCount++] = processor.pathHasChanges(path); // el atributo ha cambiado

            fieldId = "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/idVereda";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            conditions[conditionCount] = processor.pathHasChanges(path); // el atributo ha cambiado
            conditionCount++;

            conditionCount = 0;
            validatorManagerItem_conditionalValidator.addCondition(new BasicStaticBooleanCondition(conditions[conditionCount++] || conditions[conditionCount++] || conditions[conditionCount++]));

        }
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, false, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 14.5:A orden no nulo
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:orden";
        validatorManagerItem_conditionalValidator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        validatorManagerItem_errorMessage = "numero de anotacion; debe escribir un valor para este campo";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 14.5:B orden numerico
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:orden";
        validatorManagerItem_conditionalValidator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        validatorManagerItem_errorMessage = "numero de anotacion; este campo acepta solo valores numericos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 14.5:B orden numerico > 0
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:orden";
        // configurar validador:
        BasicDoubleRangeValidatorWrapper validatorTmp2;
        validatorTmp2 = new BasicDoubleRangeValidatorWrapper(true);
        validatorTmp2.setLeft(1.0d);
        //
        validatorManagerItem_conditionalValidator = validatorTmp2;
        validatorManagerItem_errorMessage = "numero de anotacion; este campo acepta solo valores numericos (enteros positivos)";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // Bug 3543
        // 14.6 orden de las anotaciones:
        // el orden debe ser menor que el numero calculado de anotaciones
        // local_AnotacionMaxOrderAllowed
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:orden";
        validatorTmp2 = new BasicDoubleRangeValidatorWrapper(true);
        // validatorTmp2.setLeft( 1.0d );
        validatorTmp2.setRight(local_AnotacionMaxOrderAllowed);
        validatorManagerItem_conditionalValidator = validatorTmp2;
        validatorManagerItem_errorMessage = "El orden de anotacion debe estar contenido dentro del numero de anotaciones existente: " + local_AnotacionMaxOrderAllowedAsString;
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 15:A idnaturalezajuridica no nulo
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:naturalezaJuridica/idNaturalezaJuridica";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "naturaleza juridica; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // id belongs to list validator
        // TODO: (search in the right session attribute param in session list of naturalezaJuridica)
        /*
      boolean exists = false;
      List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");


      Iterator ig= grupoNaturalezas.iterator();
      while( ig.hasNext() ){
              GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
              List natus= group.getNaturalezaJuridicas();
              Iterator id= natus.iterator();
              while( id.hasNext() ){
                      NaturalezaJuridica natA = (NaturalezaJuridica)id.next();

                      if( natA.getIdNaturalezaJuridica().equals(idNaturaleza) ){
                              exists = true;
                      }
              }
      }
      if( !exists ){
              exception.addError( "Debe colocar un codigo de naturaleza juridica valido" );
      }
         */
        // 16.0:A valor no nulo // ya se comprobo antes;
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:valor";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "especificacion - valor ; debe escribir un valor para este campo";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, numEspecificacion, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 16.0:B valor numerico
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:valor";
        validatorManagerItem_conditionalValidator = new BasicDatatypeString2DoubleConversionValidatorWrapper();
        validatorManagerItem_errorMessage = "especificacion - valor ; este campo acepta solo valores numericos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 16.0:C valor >= 0; [0, +inf)
        validatorManagerItem_fieldId = "field:folio:anotacion[i]:valor";
        // configurar validador:
        BasicDoubleRangeValidatorWrapper validatorTmp3 = new BasicDoubleRangeValidatorWrapper(true);
        validatorTmp2.setLeft(0d);
        //
        validatorManagerItem_conditionalValidator = validatorTmp3;
        validatorManagerItem_errorMessage = "especificacion - valor ; este campo acepta solo valores numericos (enteros positivos)";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 17: validador especial; debe existir por lo menos un ciudadano en una lista dada .
        // se prueba solo si ha variado.
        // Luego se puede observar la forma de pasarlo a jxpath
        validate_anotacion_ciudadanos_exists:
        {

            // :: si valor numerico ha variado, colocar activo el flag de cambio
            // se mira manualmente la condicion de cambio
            // y los privilegios
            String fieldId;
            BindManager.Binder searchedBind;
            String path;
            boolean[] conditions = new boolean[4];

            int conditionCount = 0;

            // fieldId = "field:folio:anotacion[i]:anotacionesCiudadano";
            // searchedBind = bindManager.getBinderById(fieldId);
            // path = searchedBind.getPath();
            // conditions[conditionCount++] = processor.pathHasChanges(path); // el atributo ha cambiado
            fieldId = "field:folio:anotacion[i]:anotacionesCiudadano";
            conditions[conditionCount++] = ProcessManager.StaticEvaluator.evaluate(localProcessManager, fieldId);

            conditions[conditionCount++] = (ciudadanos == null) || (ciudadanos.size() == 0);

            conditionCount = 0;
            if (conditions[conditionCount++] && (conditions[conditionCount++])) {
                exception.addError("ciudadanos; debe escribir por lo menos un ciudadano");
            }

        } // :validate_anotacion_ciudadanos_exists

        // adicion: bug 3569
        // fecha radicacion no puede ser anterior a fecha de documento de la anotacion
        // Luego observar la forma de pasarlo a jxpath
        compare_Gt_FechaRadicacion_FechaDocumento:
        {

            String fieldId;
            BindManager.Binder searchedBind;
            String path;

            boolean[] conditions = new boolean[2];
            int conditionCount = 0;

            java.util.Date value_FechaRad;
            java.util.Date value_FechaDoc;

            fieldId = "field:folio:anotacion[i]:documento/fecha";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();
            value_FechaDoc = (java.util.Date) searchedBind.getValue();

            conditions[conditionCount++] = processor.pathHasChanges(path); // el atributo ha cambiado

            fieldId = "field:folio:anotacion[i]:fechaRadicacion";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();
            value_FechaRad = (java.util.Date) searchedBind.getValue();

            conditions[conditionCount++] = processor.pathHasChanges(path); // el atributo ha cambiado

            // si uno de los dos cambia,
            // se activa la validacion
            conditionCount = 0;
            if ((conditions[conditionCount++] || conditions[conditionCount++])) {

                // validar que la fecha de radicacion no sea
                // anterior a la fecha del documento
                if ((null != value_FechaRad)
                        && (null != value_FechaDoc)) {
                    int comparison;
                    comparison = value_FechaRad.compareTo(value_FechaDoc);

                    if (comparison < 0) {
                        // si fecha rad < fechadoc
                        exception.addError("fecha radicacion no puede ser anterior a fecha de documento");

                    } //:if

                } // :if

            } //:if

        } // :compare_Gt_FechaRadicacion_FechaDocumento

        GrupoNaturalezaJuridica grupNat = null;
        if (t1_folio_anotacion != null && t1_folio_anotacion.getNaturalezaJuridica() != null) {
            String idNat = t1_folio_anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica();
            NaturalezaJuridica natJuri = this.getNaturalezaJuridicaById(idNat);
            if (natJuri != null) {
                grupNat = natJuri.getGrupoNaturalezaJuridica();
            }
        }
        if (request.getAttribute(CTipoAnotacion.CANCELACION) != null) {
            if (grupNat != null && !grupNat.getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.ID_CANCELACION)) {
                exception.addError("La naturaleza jurídica debe ser tipo Cancelación");
            }
        } else if (convertirCanceladoraAEstandar) {
            if (grupNat != null && grupNat.getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.ID_CANCELACION)) {
                exception.addError("La naturaleza jurídica no puede ser tipo Cancelación");
            }
        }

        // realizar prueba de validadores
        validatorManager.trigger(exception);

        // validaciones (stage 2): raise ---------------------------------------------------------------
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // ---------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------------
        // fijar el conjunto de valores que inicialmente no se estan manejando con jxpath (Colocar el resto de valores que no admiten jxpath manualmente)
        // fijar los valores dependientes de la variacion en los campos
        //  (flags de activacion de cambio)
        write_anotacionValorChangeEnabled:
        {

            // :: si valor numerico ha variado, colocar activo el flag de cambio
            // se mira manualmente la condicion de cambio
            // y los privilegios
            String fieldId;
            BindManager.Binder searchedBind;
            String path;
            boolean[] conditions = new boolean[4];

            int conditionCount = 0;

            fieldId = "field:folio:anotacion[i]:valor";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            conditions[conditionCount++] = processor.pathHasChanges(path); // el atributo ha cambiado

            fieldId = "field:folio:anotacion[i]:valor";
            conditions[conditionCount++] = ProcessManager.StaticEvaluator.evaluate(localProcessManager, fieldId);

            conditionCount = 0;
            if ((conditions[conditionCount++] && conditions[conditionCount++])) {
                t2_folio_anotacion.setToUpdateValor(true);
            }

        }

        // este caso es especial;
        // debido a la variacion realizada para poder aplicar permisos antes de
        // observar si un campo ha variado,
        // los objetos que no tenian permiso alguno se vieron alterados;
        // de esta forma, la comparacion de la variacion
        // no se realizo, si no se encontro el
        // permiso;
        // por ello, se debe realizar la construccion condicional
        // de los objetos sin permisos
        // en una nueva lista que se le pasa a un procesador de diferencias hacia adelante;
        // notese que la evaliacion del estado se esta haciendo 2 veces;
        // se deja temporalmente por si ellos necesitan la evaluacion del estado como
        // un permiso adicional.
        // las validaciones sobre este campo especifico no se hicieron dependientes
        // del resultado de las variaciones.

        /*

      write_anotacionEstadoEnabled: {

          // se mira manualmente la condicion de cambio
          // y los privilegios; en este caso no existen privilegios
          // sobre el estado, o no se especificaron en el caso de uso;

          // 1: observar si hubo variaciones mediante un comparador sencillo;
          // 2. observar permisos; (true)

          BasicJXPathForwardDiffProcessor fwDiffLocalProcessor
          = new gov.sir.core.web.helpers.correccion.diff.BasicJxPathBooleanAuthForwardDiffProcessor(
                  new String[] { "estado/idEstadoAn" }
                , new java.util.Comparator[] { new BasicStringComparator(true,true) }
                , new boolean[] { true }
          );
          // para que se pueda construir el conjunto de objetos si hay diferencia encontrada
          fwDiffLocalProcessor.setAbstractFactory( new FolioAnotacionAbstractFactory() );

          // si cambios activos, fijar el ojeto creado
          fwDiffLocalProcessor.execute( t0_folio_anotacion, t1_folio_anotacion, t2_folio_anotacion );

          // imprimir los resultados
          BasicJXPathForwardDiffProcessor.printComparisonResults( System.out, new String[] { "estado/idEstadoAn" }, fwDiffLocalProcessor.getComparisonResults() );

      }
         */
        // ---------------------------------------------------------------------------------------
        // bug 3530/3560
        // salvedad se maneja a nivel de folio
        // + aclaracion: tambien a nivel de anotacion, pero se debe poder editar la
        // salvedad
        SalvedadAnotacion local_SalvedadAnotacion = new SalvedadAnotacion();
        local_SalvedadAnotacion.setDescripcion(param_AnotacionSalvedadX_Descripcion);
        local_SalvedadAnotacion.setIdSalvedad(param_AnotacionSalvedadX_Id);
        local_SalvedadAnotacion.setIdMatricula(t0_folio_anotacion.getIdMatricula());
        local_SalvedadAnotacion.setIdAnotacion(t0_folio_anotacion.getIdAnotacion());
        local_SalvedadAnotacion.setNumRadicacion(param_AnotacionSalvedadX_NumRadicacion);

        // List< SalvedadAnotacion >
        List local_SalvedadList;
        local_SalvedadList = new ArrayList();

        local_SalvedadList.add(local_SalvedadAnotacion);
        t2_folio_anotacion.setSalvedades(local_SalvedadList);

        // ---------------------------------------------------------------------------------------
        //Verificar si la descripcion de naturaleza jurídica 
        //es diferente en la anotación.
        String t0_Especificacion = t0_folio_anotacion.getEspecificacion();
        String t1_Especificacion = t1_folio_anotacion.getNaturalezaJuridica() == null ? null : getDescripcionFromIdNaturalezaJuridica(request, t1_folio_anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica());

        // regla
        if ((null == t2_folio_anotacion.getNaturalezaJuridica())
                && (null != t0_Especificacion)
                && (null != t1_Especificacion)
                && (!t0_Especificacion.equalsIgnoreCase(t1_Especificacion))) {
            t2_folio_anotacion.setNaturalezaJuridica(t1_folio_anotacion.getNaturalezaJuridica());
            t2_folio_anotacion.setEspecificacion(t1_Especificacion);
        }
        // regla
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
         * @Descripcion: Se valida si la naturaleza juridica cambio
         */
        if (t2_folio_anotacion.getNaturalezaJuridica() != null
                && t2_folio_anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() == null
                && t2_folio_anotacion.getNaturalezaJuridica().getVersion() != 0) {
            t2_folio_anotacion.getNaturalezaJuridica().
                    setIdNaturalezaJuridica(t1_folio_anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica());

        }

        /*

      // enlazar salvedad
        // salvedad :: no protegido (siempre se agrega una salvedad)

      SalvedadAnotacion salvedadAnotacion = new SalvedadAnotacion();
      salvedadAnotacion.setDescripcion( salvedad );
      t2_folio_anotacion.addSalvedade( salvedadAnotacion );
         */
        // enlazar ciudadanos
        // ---------------------------------------
        // A: Conjunto de ciudadanos a insertar:
        List t0_folio_anotacion_ciudadanos = null; // existentes en db
        List t1_folio_anotacion_ciudadanos = null; // existentes en sesion
        List t2_folio_anotacion_ciudadanos = null; // a enviar como delta

        delta_folio_anotacion_ciudadanos:
        {

            // Conditional javaSrcBlock:
            // debe tener permiso para modificar ciudadanos
            // el manejo de las listas es un poco distinto al que se le daba a las direcciones;
            // en este caso, en sesion se esta modificando la lista original
            // y por tanto, ella es la que tiene los items a añadir y tambien a borrar.
            // no hay una lista que mantenga los datos temporales.
            // como solo se deben enviar modificaciones, se debe realizar
            // un recorrido sobre la otra lista para ver si el item existe
            // se compara por el id.
            activateEvaluation = ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:anotacionesCiudadano");

            if (activateEvaluation) {

                // criterio para enviar ciudadanos  creacion: no tiene identificador
                // criterio para enviar ciudadanos a eliminacion: setToDelete : true
                // A: recorrido por ciudadanos temporales
                // configurar-general
                t0_folio_anotacion_ciudadanos = t0_folio_anotacion.getAnotacionesCiudadanos();
                t2_folio_anotacion_ciudadanos = new java.util.ArrayList();

                if (null == t0_folio_anotacion_ciudadanos) {
                    t0_folio_anotacion_ciudadanos = new java.util.ArrayList();
                }

                // configurar para esta comparacion
                t1_folio_anotacion_ciudadanos
                        = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

                if (null == t1_folio_anotacion_ciudadanos) {
                    t1_folio_anotacion_ciudadanos = new java.util.ArrayList();
                }

                // forma de realizarlo:
                // 1. explorar nuevas adiciones
                // : las que estan en t1, pero no en t0 y no estan para eliminar
                // como posiblemente t1==t0, se crea una lista temporal
                // con los valores
                AnotacionCiudadano[] t0_folio_anotacion_ciudadanos_array
                        = new AnotacionCiudadano[t0_folio_anotacion_ciudadanos.size()];

                t0_folio_anotacion_ciudadanos_array
                        = (AnotacionCiudadano[]) t0_folio_anotacion_ciudadanos.toArray(t0_folio_anotacion_ciudadanos_array);

                boolean existsInT0;
                int posicion = 0;
                for (java.util.Iterator iteratorT1 = t1_folio_anotacion_ciudadanos.iterator();
                        iteratorT1.hasNext();) {
                    AnotacionCiudadano t1_element = (AnotacionCiudadano) iteratorT1.next();
                    String t1_element_id = t1_element.getIdCiudadano();
                    existsInT0 = false;

                    String rolPersona = request.getParameter(CAnotacionCiudadano.ANOTACION_TIPO_INTER_ASOCIACION_EDIT + posicion);
                    if (rolPersona != null) {
                        t1_element.setRolPersona(rolPersona);
                    }
                    String participacion = request.getParameter(CAnotacionCiudadano.ANOTACION_PORCENTAJE_ASOCIACION_EDIT + posicion);
                    t1_element.setParticipacion(participacion != null ? participacion : "");
                    String marcaProp = request.getParameter(CAnotacionCiudadano.ANOTACION_ES_PROPIETARIO_ASOCIACION_EDIT + posicion);
                    if (marcaProp == null || marcaProp.equals("")) {
                        t1_element.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaProp.equals("X")) {
                        t1_element.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaProp.equals("I")) {
                        t1_element.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }
                    t1_element.setToUpdate(true);

                    for (int i = 0; i < t0_folio_anotacion_ciudadanos_array.length; i++) {
                        AnotacionCiudadano t0_element = (AnotacionCiudadano) t0_folio_anotacion_ciudadanos_array[i];
                        // tener en cuenta null's en id;
                        String t0_element_id = t0_element.getIdCiudadano();

                        if ((null != t0_element_id)
                                && (null != t1_element_id)
                                && (t0_element_id.equalsIgnoreCase(t1_element_id))) {
                            existsInT0 = true;
                            break;
                        } // end if

                    } // end for

                    if (!existsInT0) {
                        // ver si tiene flag set to delete en true; si lo tiene, no es necesario agregarla
                        // otra condicion es que no puede tener el id, porque hasta ahora, no se ha creado

                        if (t1_element.isToDelete()) {
                            continue;
                        }
                        // TODO: ver si tambien se necesita getIdAnotacion()
                        if (null != t1_element.getIdCiudadano()) {
                            continue;
                        }

                        // se añade a la lista de cambios, enlazando la anotacion
                        t1_element.setAnotacion(t0_folio_anotacion);

                        t2_folio_anotacion_ciudadanos.add(t1_element);

                        Log.getInstance().debug(AWModificarFolio.class, "-->" + existsInT0);
                        // se remueve de la otra lista ?
                        // por el momento, no, para no afectar la iteracion; luego se podrian dejar en un arreglo

                    } // end if
                    posicion++;

                } // end for

                // B: recorrido por ciudadanos definitivos
                // B.a: configurar para esta comparacion
                // la lista modificada es la misma que se mantiene en sesion (t1)
                // t1_folio_anotacion_ciudadanos = t0_folio_anotacion_ciudadanos;
                if (null == t1_folio_anotacion_ciudadanos) {
                    t1_folio_anotacion_ciudadanos = new java.util.ArrayList();
                }

                for (java.util.Iterator iteratorT1 = t1_folio_anotacion_ciudadanos.iterator();
                        iteratorT1.hasNext();) {
                    AnotacionCiudadano t1_element = (AnotacionCiudadano) iteratorT1.next();

                    // si tiene flag setToDelete = true, se agrega en la lista de modificaciones
                    if (null == t1_element) {
                        continue;
                    }

                    // debe tener id (ciudadano,anotacion?)
                    if (null == t1_element.getIdCiudadano()) {
                        continue;
                    }

                    if (t1_element.isToDelete()) {
                        t2_folio_anotacion_ciudadanos.add(t1_element);
                    }

                    if (t1_element.isToUpdate()) {
                        boolean existeInT2 = false;
                        for (int i = 0; i < t2_folio_anotacion_ciudadanos.size(); i++) {
                            AnotacionCiudadano t2_element = (AnotacionCiudadano) t2_folio_anotacion_ciudadanos.get(i);
                            String t2_element_id = t2_element.getIdCiudadano();

                            if ((null != t2_element_id)
                                    && (null != t1_element.getIdCiudadano())
                                    && (t2_element_id.equalsIgnoreCase(t1_element.getIdCiudadano()))) {
                                existeInT2 = true;
                                break;
                            } // end if
                        }
                        if (!existeInT2) {
                            t2_folio_anotacion_ciudadanos.add(t1_element);
                        }
                    }

                } // end for

                // 3. si no hay cambios, se deja nula la lista de cambios
                if ((null == t2_folio_anotacion_ciudadanos)
                        || (t2_folio_anotacion_ciudadanos.size() == 0)) {
                    t2_folio_anotacion_ciudadanos = null;
                }

                // 4. se agregan las variaciones en la anotacion
                if (null != t2_folio_anotacion_ciudadanos) {
                    for (Iterator iterator = t2_folio_anotacion_ciudadanos.iterator(); iterator.hasNext();) {
                        AnotacionCiudadano element = (AnotacionCiudadano) iterator.next();
                        t2_folio_anotacion.addAnotacionesCiudadano(element);
                    } // end for
                } // end if

            } // end if

        } // :delta_folio_anotacion_ciudadanos

        //
        deltaFolio_anotacion_cancelacion:
        {

            // bug 3547
            // observar si los permisos estan habilitados
            activateEvaluation = ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:anotacionesCancelacion");

            if ((enableCancelaciones || convertirCanceladoraAEstandar) && activateEvaluation) {

                // Bug 3562, 5042
                Anotacion local_AnotacionThis;
                local_AnotacionThis = t2_folio_anotacion;

                // fijar pk
                local_AnotacionThis.setIdAnotacion(t0_folio_anotacion.getIdAnotacion());
                local_AnotacionThis.setIdMatricula(t0_folio_anotacion.getIdMatricula());

                // analisis de cambios
                // A. las que estan seleccionadas en t1
                // B. las que estan seleccioandas en t0
                // C. las que se escriben (t2)
                // List< Cancelacion >
                List t2_ToBeCanceled;
                t2_ToBeCanceled = new ArrayList();

                String[] t0_ToBeCanceled;
                String[] t1_ToBeCanceled;

                t0_ToBeCanceled = null;
                t1_ToBeCanceled = null;

                Cancelacion local_T1T0_Cancelacion;
                Cancelacion local_T0T1_Cancelacion;

                local_FindValues:
                {

                    // buscar los id's de las anotaciones canceladoras
                    List local_Result;
                    local_Result = new ArrayList();

                    List local_t0_AnotacionCancelaciones;
                    Iterator local_t0_AnotacionCancelacionesIterator;
                    Cancelacion local_t0_AnotacionCancelacionesElement;
                    String local_ElementId;

                    local_t0_AnotacionCancelaciones = t0_folio_anotacion.getAnotacionesCancelacions();
                    local_t0_AnotacionCancelacionesIterator = local_t0_AnotacionCancelaciones.iterator();

                    for (; local_t0_AnotacionCancelacionesIterator.hasNext();) {
                        local_t0_AnotacionCancelacionesElement = (Cancelacion) local_t0_AnotacionCancelacionesIterator.next();
                        local_ElementId = local_t0_AnotacionCancelacionesElement.getCancelada().getIdAnotacion();
                        local_Result.add(local_ElementId);
                    } // for
                    t0_ToBeCanceled = new String[local_Result.size()];
                    t0_ToBeCanceled = (String[]) local_Result.toArray(t0_ToBeCanceled);

                    // -------------------------------------------------------------
                    // -------------------------------------------------------------
                    // t1_ToBeCanceled son los seleccionados
                    if (!convertirCanceladoraAEstandar) {
                        t1_ToBeCanceled = local_ListAnotacionIdsToBeCanceled;
                    } else {
                        t1_ToBeCanceled = new String[0];
                    }

                } // :local_FindValues

                String t0_ToBeCanceledElement;
                String t1_ToBeCanceledElement;
                boolean founded;

                // 1. t1<-t0 negative
                // (las que estaban y ya no se encuentran)
                for (int i = 0; i < t0_ToBeCanceled.length; i++) {

                    t0_ToBeCanceledElement = t0_ToBeCanceled[i];
                    founded = false;
                    for (int j = 0; j < t1_ToBeCanceled.length; j++) {
                        if (t0_ToBeCanceledElement.equals(t1_ToBeCanceled[j])) {
                            founded = true;
                            break;
                        }
                    } // for

                    if (!founded) {

                        local_T1T0_Cancelacion = buildCancelacion(local_AnotacionThis, t0_ToBeCanceledElement);
                        local_T1T0_Cancelacion.setToDelete(true);
                        t2_ToBeCanceled.add(local_T1T0_Cancelacion);

                    } // if

                } // for

                // 1. t0-t1 positive
                // (las que se deben adicionar)
                for (int i = 0; i < t1_ToBeCanceled.length; i++) {

                    t1_ToBeCanceledElement = t1_ToBeCanceled[i];
                    founded = false;
                    for (int j = 0; j < t0_ToBeCanceled.length; j++) {
                        if (t1_ToBeCanceledElement.equals(t0_ToBeCanceled[j])) {
                            founded = true;
                            break;
                        }
                    } // for

                    if (!founded) {

                        local_T0T1_Cancelacion = buildCancelacion(local_AnotacionThis, t1_ToBeCanceledElement);
                        local_T0T1_Cancelacion.setToDelete(false);
                        t2_ToBeCanceled.add(local_T0T1_Cancelacion);

                    } // if

                } // for

                // fix values 2 object
                local_FixValues:
                {
                    Iterator t2_ToBeCanceledIterator;
                    t2_ToBeCanceledIterator = t2_ToBeCanceled.iterator();
                    Cancelacion t2_ToBeCanceledElement;

                    for (; t2_ToBeCanceledIterator.hasNext();) {
                        t2_ToBeCanceledElement = (Cancelacion) t2_ToBeCanceledIterator.next();
                        t2_folio_anotacion.addAnotacionesCancelacion(t2_ToBeCanceledElement);
                    } // for

                } // :local_FixValues

            } // end if (enableCancelaciones)

        } // :deltaFolio_anotacion_cancelacion

        // clear cache
        /*
      eliminarInfoBasicaAnotacion();
      eliminarInfoBasicaCiudadano();

      session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
         */
        // celar cache - force update
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_FOLIO);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA);
        request.getSession().removeAttribute(AWPaginadorAnotaciones.PAGINADOR_AVANZADO);
        request.getSession().removeAttribute(CONVERTIR_CANCELADORA_ESTANDAR);
        request.getSession().removeAttribute(AWNaturalezaJuridica.ANOTACION_FECHA_RADICACION);
        request.getSession().removeAttribute(AWNaturalezaJuridica.ANOTACION_MODICADA);
        request.getSession().removeAttribute(AWModificarFolio.ANOTACION_EDITADA);

        // test condition
        if (false) {
            return null;
        }

        // real condition
        String idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);

        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        Folio t2_folio = new Folio();

        // -----------------------------------------------------------------------------------------------
        // fijar pk de folio y hacer paso de registro en sesion
        // para enviar los valores a negocio
        t2_folio.setIdMatricula(t0_folio.getIdMatricula());

        /*
      * @author      :   Henry Gómez Rocha
      * @change      :   Se comentarea la siguiente línea para evitar que se guarde información
      *                  en la tabla SIR_NE_FOLIO_DATOS_TMP ya que los datos de la zona registral
      *                  son diferentes de null y  por tal motivo el sistema lo interpreta como
      *                  si siempre se estuviesen haciendo modificaciones al folio.
      * Caso Mantis  :   0004503
         */
        //t2_folio.setZonaRegistral( t0_folio.getZonaRegistral() );
        // fijar pk de anotacion
        t2_folio_anotacion.setIdAnotacion(t0_folio_anotacion.getIdAnotacion());
        if (t2_folio_anotacion.getDocumento() == null) {
            t2_folio_anotacion.setDocumento(t0_folio_anotacion.getDocumento());
        }

        /**
         * En caso de que se vaya a convertir una anotacion a canceladora se
         * debe cambiar el tipo de anotacion
         */
        if (request.getAttribute(CTipoAnotacion.CANCELACION) != null) {
            Anotacion local_AnotacionThis = t2_folio_anotacion;

            // fijar esta anotacion como canceladora
            TipoAnotacion local_AnotacionThisTipo = new TipoAnotacion();
            local_AnotacionThisTipo.setIdTipoAnotacion(CTipoAnotacion.CANCELACION);
            local_AnotacionThis.setTipoAnotacion(local_AnotacionThisTipo);

            t2_folio_anotacion.setTipoAnotacion(local_AnotacionThisTipo);
        }

        if (convertirCanceladoraAEstandar) {
            Anotacion local_AnotacionThis = t2_folio_anotacion;

            // fijar esta anotacion como canceladora
            TipoAnotacion local_AnotacionThisTipo = new TipoAnotacion();
            local_AnotacionThisTipo.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
            local_AnotacionThis.setTipoAnotacion(local_AnotacionThisTipo);

            t2_folio_anotacion.setTipoAnotacion(local_AnotacionThisTipo);
        }
        /**
         * Verificar que los id de departamento, municipio y ofinica origen no
         * quede en null
         */
        try {
            if (request.getParameter(WebKeys.TIPO_OFICINA_I_N).equals(WebKeys.TIPO_OFICINA_INTERNACIONAL)) {
                Documento documento = (Documento) t2_folio_anotacion.getDocumento();
                documento.setOficinaInternacional(request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL));
                documento.setOficinaOrigen(null);
                t2_folio_anotacion.setDocumento(documento);
            }

            if (request.getParameter(WebKeys.TIPO_OFICINA_I_N).equals(WebKeys.TIPO_OFICINA_NACIONAL)) {
                Documento documento = (Documento) t2_folio_anotacion.getDocumento();
                documento.setOficinaInternacional(null);
                OficinaOrigen oficinaTemp = t2_folio_anotacion.getDocumento().getOficinaOrigen();
                Vereda vereda = oficinaTemp.getVereda();
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                if (oficinaTemp.getIdOficinaOrigen() == null) {
                    oficinaTemp.setIdOficinaOrigen(idOficinaOrigen);
                    oficinaTemp.setVersion(Long.parseLong(oficinaVersion));
                }

                if (vereda.getIdDepartamento() == null) {
                    vereda.setIdDepartamento(valorDepartamento);
                }
                if (vereda.getIdMunicipio() == null) {
                    vereda.setIdMunicipio(valorMunicipio);
                }
                if (vereda.getIdVereda() == null) {
                    vereda.setIdVereda(valorVereda);
                }
                oficinaTemp.setVereda(vereda);
                documento.setOficinaOrigen(oficinaTemp);
                t2_folio_anotacion.setDocumento(documento);
            }
        } catch (Exception e) {
            exception.addError(e.getMessage());
        }

        // -----------------------------------------------------------------------------------------------
        // agregar anotacion
        Boolean actualizarNatJuridica = (Boolean) request.getSession().getAttribute(WebKeys.ACTUALIZAR);
        if (actualizarNatJuridica == null) {
            t2_folio_anotacion.setEspecificacion(null);
            t2_folio_anotacion.setNaturalezaJuridica(null);
        }

//      // HAGR500
//
//      Iterator itCiudadanos = t1_folio_anotacion_ciudadanos.iterator();
//    	List losA = new ArrayList();
//    	List losDe = new ArrayList();
//    	while(itCiudadanos.hasNext()){
//    		AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
//    		String rol = anotacionCiudadano.getRolPersona();
//    		if(rol.equals("DE") && !anotacionCiudadano.isToDelete()){
//    			losDe.add(anotacionCiudadano);
//    		} else if(rol.equals("A") && !anotacionCiudadano.isToDelete()){
//    			losA.add(anotacionCiudadano);
//    		}
//    	}
//   		losDe.addAll(losA);
//
//      // 4. se agregan las variaciones en la anotacion HAGR500
//              if( null != losDe ) {
//                  for( Iterator iterator = losDe.iterator(); iterator.hasNext();) {
//                      AnotacionCiudadano element = (AnotacionCiudadano)iterator.next();
//                      t2_folio_anotacion.addAnotacionesCiudadano( element );
//                  } // end for
//              } // end if
        if(modalidad != null && !modalidad.equals(WebKeys.SIN_SELECCIONAR)){
            t2_folio_anotacion.setModalidad(modalidad);
        }
        t2_folio.addAnotacione(t2_folio_anotacion);
        // -----------------------------------------------------------------------------------------------

        tipoCambio = request.getParameter(AWModificarFolio.TIPO_CAMBIO_ANOTACION);
        if (tipoCambio != null) {
            if (tipoCambio.equals("INDIVIDUAL")) {
                t2_folio.setTipoCambio(true);
            } else if (tipoCambio.equals("MULTIPLE")) {
                t2_folio.setTipoCambio(false);
            }
        }
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        // -----------------------------------------------------------------------------------------------
        EvnCorreccion result;
        result = new EvnCorreccion(
                usuarioAuriga,
                usuarioSIR,
                t2_folio,
                EvnCorreccion.EDITAR_FOLIO_ANOTACION
        );
        result.setTurno(local_Turno);

        return result;

    }

// ----------------------------------------------------------------------------------------------
    private String getDescripcionFromIdNaturalezaJuridica(HttpServletRequest request, String idNaturalezaJuridica) {
        final String ID_SESION_LISTA_GRUPOS_NAT_JURIDICA = WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS;
        //
        ServletContext context = request.getSession().getServletContext();
        List grupoNaturalezas = (List) context.getAttribute(ID_SESION_LISTA_GRUPOS_NAT_JURIDICA);
        for (Iterator grupoNaturalezasIterator = grupoNaturalezas.iterator(); grupoNaturalezasIterator.hasNext();) {
            GrupoNaturalezaJuridica grupo = (GrupoNaturalezaJuridica) grupoNaturalezasIterator.next();
            List naturalezaJuridicaByGrupo = grupo.getNaturalezaJuridicas();
            for (Iterator naturalezaJuridicaByGrupoIterator = naturalezaJuridicaByGrupo.iterator(); naturalezaJuridicaByGrupoIterator
                    .hasNext();) {
                NaturalezaJuridica element = (NaturalezaJuridica) naturalezaJuridicaByGrupoIterator.next();

                if (element.getIdNaturalezaJuridica().equalsIgnoreCase(idNaturalezaJuridica)) {
                    return element.getNombre();
                }

            }
            // group.getNaturalezaJuridicas();

        }
        return "";
    }

    /**
     * Version anterior de modificacion de folio para que no influya con
     * aplicacion de permisos correcciones
     *
     * Permite guardar los cambios que se efectuaron sobre una anotación.
     *
     * @param request
     * @return
     */
    private EvnCorreccion aceptarEdicionAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaCiudadano(request);

        List anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        ValidacionParametrosModificarAnotacionException exception = new ValidacionParametrosModificarAnotacionException();
        int posicAnotacion = 0;
        Anotacion anotacion = null;

        if (session.getAttribute(AWModificarFolio.POSICIONANOTACION) != null) {
            posicAnotacion = Integer.parseInt((String) session.getAttribute(AWModificarFolio.POSICIONANOTACION));
        }

        if (anotaciones == null) {
            anotaciones = new Vector();
        }

        try {
            anotacion = (Anotacion) anotaciones.get(posicAnotacion);
        } catch (IndexOutOfBoundsException e) {
            exception.addError("No se han podido recuperar las anotaciones.");
            throw exception;
        }

        String valFechaRadicacion = request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION);
        if ((valFechaRadicacion == null) || valFechaRadicacion.equals("")) {
            exception.addError("La fecha de la radicacion en la anotación no puede ser vacia");
        }

        String orden = request.getParameter(AWModificarFolio.ANOTACION_ORDEN);
        int ordenAnotacion = 0;
        if ((orden != null) && !orden.equals("")) {
            try {
                ordenAnotacion = Integer.parseInt(orden);
            } catch (Exception e) {
                exception.addError("El orden ingresado es inválido");
            }
        }

        Calendar fechaRadicacion = darFecha(valFechaRadicacion);
        if (fechaRadicacion == null) {
            exception.addError("La fecha de la radicacion en la anotación es inválida");
        }

        String tipoDocumento = request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
        if ((tipoDocumento == null) || tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo para el documento de la anotación");
        }

        String numDocumento = request.getParameter(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);
        if ((numDocumento == null) || numDocumento.equals("")) {
            exception.addError("El valor del número del documento en la anotación es inválido");
        }

        String valFechaDocumento = request.getParameter(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
        if ((valFechaDocumento == null) || valFechaDocumento.equals("")) {
            exception.addError("La fecha del documento en la anotación no puede ser vacia");
        }

        Calendar fechaDocumento = darFecha(valFechaDocumento);
        if (fechaDocumento == null) {
            exception.addError("La fecha del documento en la anotación es inválida");
        }

        //RECIBE LA INFORMACIÓN DE LA OFICINA ORIGEN, PERO NO SE VÁLIDA QUE TENGA INFORMACIÓN
        //PORQUE NO TODOS LOS FOLIOS TIENEN ESTA INFORMACIÓN, ES DECIR NO ES OBLIGATORIO.
        String valorDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String nomDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String valorMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String nomMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String valorVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String nomVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);

        String idOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        String numOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String nomOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);

        if ((valorVereda == null || (valorVereda.trim().equals(""))) || nomDepartamento.equals("") || nomMunicipio.equals("")) {
            valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), valorDepartamento, valorMunicipio);
        }

        if (valorVereda != null && !valorVereda.equals("")) {
            if ((idOficina == null) || (idOficina.length() <= 0)) {
                exception.addError("Debe seleccionar una oficina para la oficina de procedencia del documento en la anotación");
            }
        }

        String numEspecificacion = request.getParameter(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION);
        double valorEspecificacion = 0.0d;

        if ((numEspecificacion != null) && !numEspecificacion.equals("")) {
            try {
                valorEspecificacion = Double.parseDouble(numEspecificacion);
                if (valorEspecificacion < 0) {
                    exception.addError("El valor del número de la especificación en la anotación no puede ser negativo o cero");
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor de la especificación en la anotación es inválido");
            }
        }

        String idNaturaleza = request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nomNatJuridica = request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        if ((idNaturaleza == null) || (idNaturaleza.length() <= 0)) {
            exception.addError("Debe seleccionar una naturaleza juridica para la especificación en la anotación");
        }

        String comentario = request.getParameter(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        String salvedad = request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        if ((salvedad == null) || salvedad.length() <= 0) {
            exception.addError("Debe ingresar una Salvedad");
        } else {
            if (salvedad.length() < 30) {
                exception.addError("La salvedad debe tener por lo menos 30 carácteres.");
            }
        }

        String estadoAnotacion = null;
        estadoAnotacion = request.getParameter(AWModificarFolio.ANOTACION_ESTADO);
        if (estadoAnotacion == null || estadoAnotacion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el estado para la anotación");
        }

        List ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        if (ciudadanos == null || ciudadanos.size() == 0) {
            exception.addError("Se debe ingresar por lo menos un ciudadano");
        }

        String idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        //SE LLENA LA ANOTACIÓN CON LA NUEVA INFORMACIÓN
        if (ordenAnotacion != 0) {
            anotacion.setOrden(orden);
        }
        anotacion.setFechaRadicacion(fechaRadicacion.getTime());

        Documento documento = new Documento();
        TipoDocumento tipoDoc = new TipoDocumento();
        Iterator itTiposDocs = ((List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO)).iterator();
        while (itTiposDocs.hasNext()) {
            ElementoLista elemento = (ElementoLista) itTiposDocs.next();

            if (elemento.getId().equals(tipoDocumento)) {
                tipoDoc.setNombre(elemento.getValor());
            }
        }

        tipoDoc.setIdTipoDocumento(tipoDocumento);

        documento.setFecha(fechaDocumento.getTime());
        documento.setNumero(numDocumento);

        OficinaOrigen oficina = new OficinaOrigen();
        oficina.setIdOficinaOrigen(idOficina);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        oficina.setVersion(Long.parseLong(oficinaVersion));
        oficina.setNumero(numOficina);
        oficina.setNombre(nomOficina);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(valorDepartamento);
        departamento.setNombre(nomDepartamento);

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(valorMunicipio);
        municipio.setNombre(nomMunicipio);
        municipio.setDepartamento(departamento);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setNombre(nomVereda);
        vereda.setMunicipio(municipio);
        oficina.setVereda(vereda);

        documento.setTipoDocumento(tipoDoc);
        documento.setOficinaOrigen(oficina);

        anotacion.setDocumento(documento);

        if (valorEspecificacion > 0) {
            anotacion.setValor(valorEspecificacion);
            anotacion.setToUpdateValor(true);
        }

        NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
        naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
        naturalezaJuridica.setNombre(nomNatJuridica);

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setComentario(comentario);

        SalvedadAnotacion salvedadAnotacion = new SalvedadAnotacion();
        salvedadAnotacion.setDescripcion(salvedad);
        anotacion.addSalvedade(salvedadAnotacion);

        EstadoAnotacion estado = null;
        if (estadoAnotacion != null && !estadoAnotacion.equals("")) {
            estado = new EstadoAnotacion();
            estado.setIdEstadoAn(estadoAnotacion);
            anotacion.setEstado(estado);
        }

        Vector vectorCiudadanos = new Vector();
        List ciudadanosClear = (List) anotacion.getAnotacionesCiudadanos();
        int i = 0;

        if (ciudadanosClear != null) {
            Iterator itPersonas = ciudadanosClear.iterator();

            while (itPersonas.hasNext()) {
                i++;

                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                ciudadano.setAnotacion(anotacion);
                vectorCiudadanos.add(ciudadano);
            }

            for (i = 0; i < vectorCiudadanos.size(); i++) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) vectorCiudadanos.get(i);
                anotacion.removeAnotacionesCiudadano(ciudadano);
            }
        }

        if (ciudadanos != null) {
            Iterator itPersonas = ciudadanos.iterator();

            while (itPersonas.hasNext()) {

                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                ciudadano.setAnotacion(anotacion);

                anotacion.addAnotacionesCiudadano(ciudadano);
            }
        }

        eliminarInfoBasicaAnotacion();
        eliminarInfoBasicaCiudadano();
        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Folio folioOriginal = (Folio) session.getAttribute(WebKeys.FOLIO);
        Folio folio = new Folio();
        folio.setIdMatricula(folioOriginal.getIdMatricula());
        folio.setZonaRegistral(folioOriginal.getZonaRegistral());
        folio.addAnotacione(anotacion);

        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_FOLIO);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA);
        request.getSession().removeAttribute(AWPaginadorAnotaciones.PAGINADOR_AVANZADO);

        return new EvnCorreccion(usuarioAuriga, (Usuario) session.getAttribute(WebKeys.USUARIO), folio, EvnCorreccion.EDITAR_FOLIO_ANOTACION);
    }

    /**
     * Permite deshacer los cambios que se efectuaron sobre una anotación.
     *
     * @param request
     * @return
     */
    private EvnFolio cancelarEdicionAnotacion(HttpServletRequest request) throws AccionWebException {
        try {
            eliminarInfoBasicaCiudadano();
            eliminarInfoBasicaAnotacion();
            eliminarInfoBasicaVariosCiudadanos(request);
            session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
            session.removeAttribute(AWModificarFolio.AGREGAR_ANOTACION);
            session.removeAttribute(WebKeys.HAY_EXCEPCION);
            session.removeAttribute(AWModificarFolio.ANOTACION_EDITADA);
            session.removeAttribute(AWModificarFolio.CONSULTA_ANOTACIONES_RESP);
            session.removeAttribute(AWModificarFolio.EXISTEN_ANOTACIONES_ASOCIADAS);
            request.getSession().removeAttribute(AWNaturalezaJuridica.ANOTACION_FECHA_RADICACION);
            request.getSession().removeAttribute(AWNaturalezaJuridica.ANOTACION_MODICADA);
        } catch (Exception e) {
            throw new ParametroInvalidoException("No se pudo cancelar la operación" + e.getMessage());
        }
        session.removeAttribute(AWModificarFolio.MODIFICA_DEFINITIVA);
        return null;
    }

    /**
     * Permite realizar la modificacion de un folio a traves de un conjunto de
     * permisos Las validaciones se aplican condicionalmente Los cambios se
     * aplican condicionalmente
     *
     */
    private EvnCorreccion
            doFolioEdit_InfoFolio(HttpServletRequest request, String parametro)
            throws AccionWebException {

        // politica de modificacion:
        // 1. si tiene permisos sobre los campos, se activan las validaciones
        // 2.1 si tiene permiso sobre los campos, se activan las modificaciones
        // 2.2 se aplica cambio, si y solo si se han modificado los datos originales.
        // 2.3 si el dato anterior resulta ser nulo, se crea el objeto con los cambios.
        // 2.4 se aplican diferencias hacia adelante, es decir, el objeto mas reciente tiene los cambios.
        // Cargar los permisos de la sesion
        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager
                = doExtractAuthorization_ProcessManager(request);

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        // Cargar el estado t0 de los objetos
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (null == turno) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == t0_folio) {
            t0_folio = new Folio();
        }

        // cargar id de matricula
        String idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);

        // ----------------------------------
        // temp2cache
        preservarInfoBasicaFolio(request);

        // bug 3530 /3560
        // temp2cache
        do_FolioEdit_SalvedadFolio_SaveState(request);

        // construir un objeto folio para observar los cambios que se han aplicado
        Folio t1_folio;
        //t1_folio= new Folio(); // not necessary at this time

        // construir un objeto folio que va a tener los cambios
        Folio t2_folio;
        //t2_folio= new Folio(); // not necessary at this time

        //   ----------------------------------
        // cargar datos de request; colocar los
        // datos extraidos en variables temporales
        // Datos de folio.documento ---------------------------------------------------
        // Documento.datosbasicos
        String idDoc = request.getParameter(CDocumento.ID_DOCUMENTO);
        String idTipoDoc = request.getParameter(CDocumento.ID_TIPO_DOCUMENTO); // id escrito (idTipoDoc==tipoDoc)?
        String tipoDoc = request.getParameter(CDocumento.TIPO_DOCUMENTO); // id seleccionado (idTipoDoc==tipoDoc)?
        String parameter_folio_documento_tipoDocumento_IdTipoDocumento = null; // se verifica su valor sobre una lista
        String parameter_folio_documento_tipoDocumento_nombre = null; // este valor se obtendra al recorrer la lista de la sesion
        String numDoc = request.getParameter(CDocumento.NUM_DOCUMENTO);
        String fechaRad = request.getParameter(CDocumento.FECHA_RADICACION);
        java.util.Calendar documento_fecha_t1 = darFecha(fechaRad);
        // Documento.oficinaProcedencia.locators
        String valorDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String nomDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String valorMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String nomMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String valorVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String nomVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        // Documento.oficinaProcedencia
        String idOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        String numOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String nomOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);

        // Bug: 3580
        String local_Folio_Radicacion;
        String local_Folio_FechaApertura;

        local_Folio_Radicacion = request.getParameter(AWModificarFolio.FOLIO_NUM_RADICACION);
        local_Folio_FechaApertura = request.getParameter(AWModificarFolio.FOLIO_FECHA_APERTURA);

        // convertor cache
        Date local_Folio_FechaApertura_AsDate; // parse as date :: value
        local_Folio_FechaApertura_AsDate = BasicConvertorActions.toDate(local_Folio_FechaApertura, "dd/MM/yyyy");

        // Documento.oficinaProcedencia.vereda
        delegatedConvertor_Documento_OficinaProcedencia_Vereda:
        {

            if ((valorVereda == null || (valorVereda.trim().equals("")))
                    || nomDepartamento.equals("")
                    || nomMunicipio.equals("")) {
                valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), valorDepartamento, valorMunicipio);
            }

        } // :delegatedConvertor_Documento_OficinaProcedencia_Vereda

        // Ajuste de id de doucmento
        idDoc = (null != t0_folio.getDocumento()) ? (t0_folio.getDocumento().getIdDocumento()) : (null); //TODO: ajuste temporal; colocar el id, si en t0 existe;// request.getParameter(CDocumento.ID_DOCUMENTO);

        // Datos de folio ---------------------------------------------------
        // folio.tipopredio.id
        String valorTipoPredio = request.getParameter(AWModificarFolio.FOLIO_TIPO_PREDIO);
        // folio.tipopredio.nombre}
        String nombreTipoPredio = null; // este valor se obtendra al recorrer la lista de la sesion
        // folio.complementacion
        String valorComplementacion = request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTACION);

        // Bug 4999 (solucion temporal)
        if (null != valorComplementacion) {
            valorComplementacion = valorComplementacion.toUpperCase();
        } else {
            valorComplementacion = "";
        }

        // folio.lindero
        String valorLindero = request.getParameter(AWModificarFolio.FOLIO_LINDERO);
        // folio.codcatastral
        String valorCodCatastral = request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL);
        // folio.codcatastral.anterior
        String valorCodCatastralAnt = request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT);
        // folio.nupre
        String valorNupre = request.getParameter(AWModificarFolio.FOLIO_NUPRE);
        // folio.determinaInm
        String valorDeterminaInm = request.getParameter(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE);
        // folio.privMetros
        String valorPrivMetros = request.getParameter(AWModificarFolio.FOLIO_PRIVMETROS);
        // folio.priveCentimetros
        String valorPrivCentimetros = request.getParameter(AWModificarFolio.FOLIO_PRIVCENTIMETROS);
        // folio.consMetros
        String valorConsMetros = request.getParameter(AWModificarFolio.FOLIO_CONSMETROS);
        // folio.consCentimetros
        String valorConsCentimetros = request.getParameter(AWModificarFolio.FOLIO_CONSCENTIMETROS);
        // folio.coeficiente
        String valorCoeficiente = request.getParameter(AWModificarFolio.FOLIO_COEFICIENTE);
        // folio.hectareas
        String valorHectareas = request.getParameter(AWModificarFolio.FOLIO_HECTAREAS);
        // folio.metros
        String valorMetros = request.getParameter(AWModificarFolio.FOLIO_METROS);
        // folio.centimetros
        String valorCentimetros = request.getParameter(AWModificarFolio.FOLIO_CENTIMETROS);

        // folio.estado.comentario
        String comentarioEstadoFolio = request.getParameter(AWModificarFolio.FOLIO_ESTADO_FOLIO_COMENTARIO);
        // folio.estadoFolio
        String estadoFolio = request.getParameter(AWModificarFolio.FOLIO_ESTADO_FOLIO);
        // folio.zonaRegistral
        String zonaRegistral_vereda_idVereda = request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA);
        String zonaRegistral_vereda_nombre = request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        String zonaRegistral_vereda_municipio_idMunicipio = request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC);
        String zonaRegistral_vereda_municipio_nombre = request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        String zonaRegistral_vereda_municipio_departamento_idDepartamento = request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO);
        String zonaRegistral_vereda_municipio_departamento_nombre = request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO);

        // folio.salvedad
        String param_SalvedadFolio_Descripcion = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION);
        String param_SalvedadFolio_Id = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID);
        // Bug 3565
        String param_SalvedadFolio_NumRadicacion;
        param_SalvedadFolio_NumRadicacion = turno.getIdWorkflow();

        // param_SalvedadFolio_Id
        if ((null != param_SalvedadFolio_Id)
                && ("".equals(param_SalvedadFolio_Id.trim()))) {
            param_SalvedadFolio_Id = null;
        } // if

        // validaciones (stage 1): ---------------------------------------------------------------------
        // Estos son los validadores que aplican siempre,
        // independientemente si existen o no cambios en los campos
        exception = new ValidacionParametrosException();
        // Validacion de NUPRE  ------------------------------------------------------------------------
        if (valorNupre != null && valorNupre.length() > 14) {
            exception.addError("Debe digitar menos de 15 digitos para el campo NUPRE");
        }

        // Validacion de Areas  ------------------------------------------------------------------------
        String hectareasT = "0";
        String metrosT = "0";
        String centimetrosT = "0";
        int unidadmedidaexception = 3;

        if (valorHectareas == null || valorHectareas.length() <= 0) {
            unidadmedidaexception--;
            valorHectareas = "0";
        } else {
            hectareasT = valorHectareas;
        }

        if (valorMetros == null || valorMetros.length() <= 0) {
            unidadmedidaexception--;
            valorMetros = "0";
        } else {
            metrosT = valorMetros;
        }

        if (valorCentimetros == null || valorCentimetros.length() <= 0) {
            unidadmedidaexception--;
            valorCentimetros = "0";
        } else {
            centimetrosT = valorCentimetros;
        }

        if (unidadmedidaexception != 0) {

            boolean datosPrivBien = true;
            boolean privArea = false;

            String metrosP = "0";
            String centimetrosP = "0";

            if (valorPrivMetros != null && !valorPrivMetros.isEmpty()) {
                privArea = true;
                metrosP = valorPrivMetros;
            } else {
                valorPrivMetros = "0";
            }

            if (valorPrivCentimetros != null && !valorPrivCentimetros.isEmpty()) {
                privArea = true;
                centimetrosP = valorPrivCentimetros;
            } else {
                valorPrivCentimetros = "0";
            }

            if (privArea) {
                unidadmedidaexception = 2;
                String metrosC = "0";
                String centimetrosC = "0";

                if (valorConsMetros == null || valorConsMetros.length() <= 0) {
                    unidadmedidaexception--;
                    valorConsMetros = "0";
                } else {
                    metrosC = valorConsMetros;
                }

                if (valorConsCentimetros == null || valorConsCentimetros.length() <= 0) {
                    unidadmedidaexception--;
                    valorConsCentimetros = "0";
                } else {
                    centimetrosC = valorConsCentimetros;
                }

                if (unidadmedidaexception == 0) {
//                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
//                    throw exception;
                }

                //Area Privada para Validar
                int pMetros = Integer.parseInt(metrosP);
                int pCentimetros = Integer.parseInt(centimetrosP);
                //Area Construida para Validar
                int cMetros = Integer.parseInt(metrosC);
                int cCentimetros = Integer.parseInt(centimetrosC);
                //Area del Terreno para Validar
                int tHectareas = Integer.parseInt(hectareasT);
                int tMetros = Integer.parseInt(metrosT);
                int tCentimetros = Integer.parseInt(centimetrosT);

                if (pMetros > cMetros) {
                    datosPrivBien = false;
                } else {
                    if (pMetros == cMetros) {
                        if (pCentimetros > cCentimetros) {
                            datosPrivBien = false;
                        }
                    }
                }

                if (!datosPrivBien) {
//                    exception.addError("El area privada no puede ser mayor al area construida");
//                    throw exception;
                }
                boolean datosConsBien = true;

                if (tHectareas <= 0) {
                    if (cMetros > tMetros) {
                        datosConsBien = false;
                    } else {
                        if (cMetros == tMetros) {
                            if (cCentimetros > tCentimetros) {
                                datosConsBien = false;
                            }
                        }
                    }
                }

                if (!datosConsBien) {
//                    exception.addError("El area construida no puede ser mayor al area del terreno");
//                    throw exception;
                }

            } else {

                boolean consArea = false;

                String metrosC = "0";
                String centimetrosC = "0";

                if (valorConsMetros != null && !valorConsMetros.isEmpty()) {
                    consArea = true;
                    metrosC = valorConsMetros;
                } else {
                    valorConsMetros = "0";
                }

                if (valorConsCentimetros != null && !valorConsCentimetros.isEmpty()) {
                    consArea = true;
                    centimetrosC = valorConsCentimetros;
                } else {
                    valorConsCentimetros = "0";
                }

                if (consArea) {
                    //Area Construida para Validar
                    int cMetros = Integer.parseInt(metrosC);
                    int cCentimetros = Integer.parseInt(centimetrosC);
                    //Area Construida para Validar
                    int tHectareas = Integer.parseInt(hectareasT);
                    int tMetros = Integer.parseInt(metrosT);
                    int tCentimetros = Integer.parseInt(centimetrosT);

                    boolean datosConsBien = true;
                    if (tHectareas <= 0) {
                        if (cMetros > tMetros) {
                            datosConsBien = false;
                        } else {
                            if (cMetros == tMetros) {
                                if (cCentimetros > tCentimetros) {
                                    datosConsBien = false;
                                }
                            }
                        }
                    }

                    if (!datosConsBien) {
//                        exception.addError("El area construida no puede ser mayor al area del terreno");
//                        throw exception;
                    }
                }
            }

        } else {
            boolean privArea = false;

            if (valorPrivMetros != null && !valorPrivMetros.isEmpty()) {
                privArea = true;
            }

            if (valorPrivCentimetros != null && !valorPrivCentimetros.isEmpty()) {
                privArea = true;
            }

            if (privArea) {
                boolean consArea = false;

                if (valorConsMetros != null && !valorConsMetros.isEmpty()) {
                    consArea = true;
                }

                if (valorConsCentimetros != null && !valorConsCentimetros.isEmpty()) {
                    consArea = true;
                }

                if (consArea) {
//                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area del Terreno");
                } else {
//                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                }
            } else {
                boolean consArea = false;

                if (valorConsMetros != null && !valorConsMetros.isEmpty()) {
                    consArea = true;
                }

                if (valorConsCentimetros != null && !valorConsCentimetros.isEmpty()) {
                    consArea = true;
                }

                if (consArea) {
//                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                }
            }
        }
        // ---------------------------------------------------------------------------------------------

        String validatorManagerItem_fieldId;
        BasicConditionalValidator stage1_validator;

        // 1: validar salvedad
        // 1:A not null
        stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; debe escribir un valor en la salvedad");
        }

        // 1:B maxima longitud
        stage1_validator = new BasicStringMaxLengthValidatorWrapper(255);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener menos de 255 caracteres");
        }

        // 1:C minima longitud
        stage1_validator = new BasicStringMinLengthValidatorWrapper(30);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener mas de 30 caracteres");
        }

        // 3: validar tipoDocumento.ElementInList    (este solo deja en nulo un capo si no encuentra un valor)
        //    para que funcione con las condiciones de activacion para un tipo de documento determinado.
        //    solo hace un raise inmediato si la lista no se encontro en la sesion.
        //    tambien se verifica que exista por lo menos uno de los valores tipoDoc o idTipoDoc dentro de la lista
        //    primero se evalua por el numero escrito; luego se evalua pro el numero seleccionado
        delegatedValidator_Folio_Documento_TipoDocumento_ElementInList:
        {

            List tiposDocumento = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);

            boolean elementFounded = false;

            if (null != tiposDocumento) {
                Iterator iterator = tiposDocumento.iterator();
                for (; iterator.hasNext();) {

                    ElementoLista element = (ElementoLista) iterator.next();

                    // politica: primero encontrado
                    if (element.getId().equals(idTipoDoc)) {
                        parameter_folio_documento_tipoDocumento_IdTipoDocumento = element.getId();
                        parameter_folio_documento_tipoDocumento_nombre = element.getValor();
                        elementFounded = true;
                        break;
                    }
                    if (element.getId().equals(tipoDoc)) {
                        parameter_folio_documento_tipoDocumento_IdTipoDocumento = element.getId();
                        parameter_folio_documento_tipoDocumento_nombre = element.getValor();
                        elementFounded = true;
                        break;
                    }
                }
                // if( !elementFounded ){
                //    exception.addError( "tipoDocumento; no se encontro el valor seleccionado" );
                // }
            } else {
                exception.addError("tipoDocumento; La lista de los tipos de documento no se encontro");
            }
        }

        /**
         * @Autor: Edgar Lora
         * @Mantis: 0013038
         * @Requerimiento: 060_453
         */
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
        try {
            String matricula = t0_folio.getIdMatricula();
            String lindero = valorLindero;
            if (validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)) {
                if (validacionesSIR.validarLinderos(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS)) {
                    String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                    if (lindero.indexOf(articulo) != -1) {
                        int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                        if (lindero.length() - tamArticulo < 100) {
                            exception.addError("Debe añadir minimo 100 caracteres al campo de linderos de la matricula: " + matricula);
                        }
                    } else {
                        exception.addError("El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto \"Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012\", Porfavor no lo borre.");
                    }
                }
            }
        } catch (GeneralSIRException ex) {
            exception.addError(ex.getMessage());
        }
        // validaciones (stage 1): raise ---------------------------------------------------------------

        if (exception.getErrores().size() > 0) {
            ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
            throw ex1;
        }

        // ---------------------------------------------------------------------------------------------
        // no se debe evaluar todo
        // el objeto; solamente se debe evaluar
        // un subconjunto del arbol Folio
        // ---------------------------
        // ---------------------------
        // ---------------------------
        // DOCUMENTO+BASICOS DE FOLIO
        // ---------------------------
        // ---------------------------
        // ---------------------------
        BindManager bindManager = new FolioEditBindManager();

        DeltaAnalysisParams deltaParams = new DeltaFolioAnalysisParams(bindManager, localProcessManager);

        String[] jxpath_DeltaPaths = deltaParams.getDeltaPaths();
        java.util.Comparator[] comparatorsToApply = deltaParams.getComparators();
        boolean[] jxpath_AuthAccess = deltaParams.getAuthAccess();

        // aplicacion de convertors
        // construir el objeto en t1
        // hacer bind al objeto en t1
        // los que no tienen permisos
        String[] jxpath_DeltaPaths2 = new String[]{ // bindManager.getBinderById( "field:folio:complementacion/complementacion+"                                      ).getPath()
        };

        java.util.Comparator[] comparatorsToApply2 = new java.util.Comparator[]{ // new BasicStringComparator(true,true,true) // cambiado para filtrar breaks
        };

        // autorizacion para escribir los campos
        boolean[] jxpath_AuthAccess2 = new boolean[]{ //  ProcessManager.StaticEvaluator.evaluate( localProcessManager, "field:folio:complementacion/complementacion+"                                      )
        };

        bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento").setValue(valorDepartamento);
        bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/departamento/nombre").setValue(nomDepartamento);
        bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/idMunicipio").setValue(valorMunicipio);
        bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/nombre").setValue(nomMunicipio);
        bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/idVereda").setValue(valorVereda);
        bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/nombre").setValue(nomVereda);
        bindManager.getBinderById("field:folio:documento/oficinaOrigen/idOficinaOrigen").setValue(idOficina);
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        bindManager.getBinderById("field:folio:documento/oficinaOrigen/version").setValue((oficinaVersion != null && !"".equals(oficinaVersion)) ? Long.parseLong(oficinaVersion) : null);
//  bindManager.getBinderById( "field:folio:documento/oficinaOrigen/numero"                                       ).setValue( numOficina )        ; //numOficina
        bindManager.getBinderById("field:folio:documento/oficinaOrigen/nombre").setValue(numOficina); //nomOficina; // porque esta llegando nulo en t0 y al realizar el delta tendra un valor y fallaria en la insercion
        bindManager.getBinderById("field:folio:documento/idDocumento").setValue(idDoc);
        bindManager.getBinderById("field:folio:documento/tipoDocumento/idTipoDocumento").setValue(parameter_folio_documento_tipoDocumento_IdTipoDocumento);
        bindManager.getBinderById("field:folio:documento/tipoDocumento/nombre").setValue(parameter_folio_documento_tipoDocumento_nombre);// tipoDoc??
        bindManager.getBinderById("field:folio:documento/numero").setValue(numDoc);
        bindManager.getBinderById("field:folio:documento/fecha").setValue((null == documento_fecha_t1) ? (null) : (documento_fecha_t1.getTime()));

        bindManager.getBinderById("field:folio:lindero").setValue(valorLindero);
        bindManager.getBinderById("field:folio:complementacion/complementacion").setValue(valorComplementacion);
        bindManager.getBinderById("field:folio:codCatastral").setValue(valorCodCatastral);
        bindManager.getBinderById("field:folio:codCatastralAnterior").setValue(valorCodCatastralAnt);
        bindManager.getBinderById("field:folio:nupre").setValue(valorNupre);
        bindManager.getBinderById("field:folio:determinaInm").setValue(valorDeterminaInm);
        bindManager.getBinderById("field:folio:privMetros").setValue(valorPrivMetros);
        bindManager.getBinderById("field:folio:privCentimetros").setValue(valorPrivCentimetros);
        bindManager.getBinderById("field:folio:consMetros").setValue(valorConsMetros);
        bindManager.getBinderById("field:folio:consCentimetros").setValue(valorConsCentimetros);
        bindManager.getBinderById("field:folio:coeficiente").setValue(valorCoeficiente);
        bindManager.getBinderById("field:folio:hectareas").setValue(valorHectareas);
        bindManager.getBinderById("field:folio:metros").setValue(valorMetros);
        bindManager.getBinderById("field:folio:centimetros").setValue(valorCentimetros);

        bindManager.getBinderById("field:folio:zonaRegistral/vereda/idVereda").setValue(zonaRegistral_vereda_idVereda);
        bindManager.getBinderById("field:folio:zonaRegistral/vereda/nombre").setValue(zonaRegistral_vereda_nombre);
        bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/idMunicipio").setValue(zonaRegistral_vereda_municipio_idMunicipio);
        bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/nombre").setValue(zonaRegistral_vereda_municipio_nombre);
        bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/departamento/idDepartamento").setValue(zonaRegistral_vereda_municipio_departamento_idDepartamento);
        bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/departamento/nombre").setValue(zonaRegistral_vereda_municipio_departamento_nombre);

        // -----
        bindManager.getBinderById("field:folio:estado/idEstado").setValue(estadoFolio);
        bindManager.getBinderById("field:folio:estado/comentario").setValue(comentarioEstadoFolio);
        bindManager.getBinderById("field:folio:tipoPredio/idPredio").setValue(valorTipoPredio);

        // bug: 3580 ----
        bindManager.getBinderById("field:folio:radicacion").setValue(local_Folio_Radicacion);
        bindManager.getBinderById("field:folio:fechaApertura").setValue(local_Folio_FechaApertura_AsDate);

        // bindManager.getBinderById( "field:folio:complementacion/complementacion+"                                      ).setValue( valorComplementacion );
        /*
    String valorTipoPredio = request.getParameter(AWModificarFolio.FOLIO_TIPO_PREDIO);
      // folio.salvedad
    String salvedad = request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
      // folio.estado.comentario
    String comentarioEstadoFolio = request.getParameter(AWModificarFolio.FOLIO_ESTADO_FOLIO_COMENTARIO);
         */
        // colocar los valores en el binder que se van a validar y que aplican para cambio delta
        java.lang.Object[] t1_folio_documento_values = new java.lang.Object[]{
            bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento").getValue(),
            bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/departamento/nombre").getValue(),
            bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/idMunicipio").getValue(),
            bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/nombre").getValue(),
            bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/idVereda").getValue(),
            bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/nombre").getValue(),
            bindManager.getBinderById("field:folio:documento/oficinaOrigen/idOficinaOrigen").getValue() /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */,
            bindManager.getBinderById("field:folio:documento/oficinaOrigen/version").getValue() //       , bindManager.getBinderById( "field:folio:documento/oficinaOrigen/numero"                                       ).getValue()
            ,
             bindManager.getBinderById("field:folio:documento/oficinaOrigen/nombre").getValue(),
            bindManager.getBinderById("field:folio:documento/idDocumento").getValue(),
            bindManager.getBinderById("field:folio:documento/tipoDocumento/idTipoDocumento").getValue(),
            bindManager.getBinderById("field:folio:documento/tipoDocumento/nombre").getValue() // tipoDoc??
            ,
             bindManager.getBinderById("field:folio:documento/numero").getValue(),
            bindManager.getBinderById("field:folio:documento/fecha").getValue(),
            bindManager.getBinderById("field:folio:lindero").getValue(),
            bindManager.getBinderById("field:folio:complementacion/complementacion").getValue(),
            bindManager.getBinderById("field:folio:codCatastral").getValue(),
            bindManager.getBinderById("field:folio:codCatastralAnterior").getValue(),
            bindManager.getBinderById("field:folio:nupre").getValue(),
            bindManager.getBinderById("field:folio:determinaInm").getValue(),
            bindManager.getBinderById("field:folio:privMetros").getValue(),
            bindManager.getBinderById("field:folio:privCentimetros").getValue(),
            bindManager.getBinderById("field:folio:consMetros").getValue(),
            bindManager.getBinderById("field:folio:consCentimetros").getValue(),
            bindManager.getBinderById("field:folio:coeficiente").getValue(),
            bindManager.getBinderById("field:folio:hectareas").getValue(),
            bindManager.getBinderById("field:folio:metros").getValue(),
            bindManager.getBinderById("field:folio:centimetros").getValue(),
            bindManager.getBinderById("field:folio:zonaRegistral/vereda/idVereda").getValue(),
            bindManager.getBinderById("field:folio:zonaRegistral/vereda/nombre").getValue(),
            bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/idMunicipio").getValue(),
            bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/nombre").getValue(),
            bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/departamento/idDepartamento").getValue(),
            bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/departamento/nombre").getValue(),
            bindManager.getBinderById("field:folio:estado/idEstado").getValue(),
            bindManager.getBinderById("field:folio:estado/comentario").getValue(),
            bindManager.getBinderById("field:folio:tipoPredio/idPredio").getValue(),
            bindManager.getBinderById("field:folio:radicacion").getValue(),
            bindManager.getBinderById("field:folio:fechaApertura").getValue()
        };

        java.lang.Object[] t1_folio_documento_values2 = new java.lang.Object[]{ // bindManager.getBinderById( "field:folio:complementacion/complementacion+"                                      ).getValue()
        };

        // binding (delta)
        // Documento t2_folio_documento;
        // t2_folio_documento = new Documento();
        t2_folio = new Folio();

        // construir objeto con valores nulos
        // t2_folio_documento = buildDocumento( jxpath_DeltaPaths, new Object[jxpath_DeltaPaths.length] );
        // construir objeto basico
        // binding (t1)
        t1_folio = new Folio();
        // t1_folio = buildFolio( t1_folio, jxpath_DeltaPaths2, t1_folio_documento_values2, comparatorsToApply2 );
        t1_folio = buildFolio(t1_folio, jxpath_DeltaPaths, t1_folio_documento_values, comparatorsToApply);

        BasicJXPathForwardDiffProcessor processor;
        BasicJXPathForwardDiffProcessor processor2;

        AbstractFactory abstractFactory = new FolioAbstractFactory();
        //bug 5188, bug 5289
        processor2 = jx_ProcessForwardDifferences(
                jxpath_DeltaPaths2,
                comparatorsToApply2,
                jxpath_AuthAccess2,
                t0_folio,
                t1_folio,
                t2_folio,
                abstractFactory
        );

        processor = jx_ProcessForwardDifferences(
                jxpath_DeltaPaths,
                comparatorsToApply,
                jxpath_AuthAccess,
                t0_folio,
                t1_folio,
                t2_folio,
                abstractFactory
        );

        // solo se crea la rama de estado si idEstado ha cambiado
        check_field_estado:
        {

            // es caso especial debido
            // a que puede que el comentario anterior de cambio de estado se haya fijado
            // previamente, y que cuando se vaya a editar
            // regrese un valor nulo, lo que detectaria un
            // cambio.
            // la politica de activacion solo debe observar
            // si hay cambios en el idEstado
            String fieldId;
            BindManager.Binder searchedBind;
            String path;
            boolean[] conditions = new boolean[2];

            int conditionCount = 0;

            fieldId = "field:folio:estado/idEstado";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            // write conditions
            conditions[conditionCount++] = (!processor.pathHasChanges(path)); // el atributo no ha cambiado
            conditions[conditionCount++] = (null != t2_folio.getEstado()); // pero el estado si

            // test applicable conditions (and)
            conditionCount = 0;
            if (conditions[conditionCount++]
                    && conditions[conditionCount++]) {

                t2_folio.setEstado(null);

            } // :if

            /*

         write_anotacionEstadoEnabled: {

             // se mira manualmente la condicion de cambio
             // y los privilegios; en este caso no existen privilegios
             // sobre el estado, o no se especificaron en el caso de uso;

             // 1: observar si hubo variaciones mediante un comparador sencillo;
             // 2. observar permisos; (true)

             BasicJXPathForwardDiffProcessor fwDiffLocalProcessor
             = new gov.sir.core.web.helpers.correccion.diff.BasicJxPathBooleanAuthForwardDiffProcessor(
                     new String[] { "estado/idEstadoAn" }
                   , new java.util.Comparator[] { new BasicStringComparator(true,true) }
                   , new boolean[] { true }
             );
             // para que se pueda construir el conjunto de objetos si hay diferencia encontrada
             fwDiffLocalProcessor.setAbstractFactory( new FolioAnotacionAbstractFactory() );

             // si cambios activos, fijar el ojeto creado
             fwDiffLocalProcessor.execute( t0_folio_anotacion, t1_folio_anotacion, t2_folio_anotacion );

             // imprimir los resultados
             BasicJXPathForwardDiffProcessor.printComparisonResults( System.out, new String[] { "estado/idEstadoAn" }, fwDiffLocalProcessor.getComparisonResults() );

         }
             */
        }

        // bug 3552
        // detectar cambios de adicion
        local_Folio_Adds_Detect:
        {

            // si esta habilitada
            // la opcion de cambios:
            Boolean ruleComplementacionAdicionaEnabledWrapper;
            boolean ruleComplementacionAdicionaEnabled;

            ruleComplementacionAdicionaEnabled = false;
            if (null != (ruleComplementacionAdicionaEnabledWrapper = ((Boolean) session.getAttribute(AWModificarFolio.PARAM__OPTION_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONADICIONAENABLED)))) {
                ruleComplementacionAdicionaEnabled = ruleComplementacionAdicionaEnabledWrapper.booleanValue();

            } // if

            if (ruleComplementacionAdicionaEnabled) {

                Folio local_T1Folio;
                local_T1Folio = new Folio();

                Folio local_T2Folio;
                local_T2Folio = t2_folio;//new Folio();

                String[] local_Paths = new String[]{
                    bindManager.getBinderById("field:folio:complementacion/complementacion+").getPath()
                };
                java.util.Comparator[] local_Comparators = new java.util.Comparator[]{
                    new BasicStringComparator(true, true, true)
                };
                boolean[] local_Enableds = new boolean[]{
                    ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:complementacion/complementacion+")
                };

                java.lang.Object[] local_Values = new java.lang.Object[]{
                    valorComplementacion
                };

                local_T1Folio = buildFolio(local_T1Folio, local_Paths, local_Values, local_Comparators);

                BasicJXPathForwardDiffProcessor fwDiffLocalProcessor
                        = new gov.sir.core.web.helpers.correccion.diff.BasicJxPathBooleanAuthForwardDiffProcessor(
                                local_Paths,
                                local_Comparators,
                                local_Enableds
                        );
                // para que se pueda construir el conjunto de objetos si hay diferencia encontrada
                fwDiffLocalProcessor.setAbstractFactory(new FolioAbstractFactory());

                // si cambios activos, fijar el ojeto creado
                fwDiffLocalProcessor.execute(t0_folio, local_T1Folio, local_T2Folio);

                // imprimir los resultados
                Log.getInstance().debug(AWModificarFolio.class, "-- -- -- -- -- -- ");
                //BasicJXPathForwardDiffProcessor.printComparisonResults( System.out, local_Paths, fwDiffLocalProcessor.getComparisonResults() );

            } // if

        } // :local_Folio_Adds_Detect

        // aplicar validaciones
        // validaciones (stage 2): ---------------------------------------------------------------------
        // Estos son los validadores que aplican si hubo cambios,
        // y si existen permisos sobre los campos.
        exception = new ValidacionParametrosException();

        // ---------------------------------------------------------------------------------------------
        ValidatorManager validatorManager = new ValidatorManager();

        validatorManagerItem_fieldId = "";
        ConditionalValidator validatorManagerItem_conditionalValidator;
        String validatorManagerItem_errorMessage;

        // para cada uno de los items a validar cuando cambien:
        // escribe condicionales para el validador y escribe el campo a validar
        // agrega registro al validator manager
        // 1:
        validatorManagerItem_fieldId = "field:folio:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:oficina de procedencia:departamento; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 2:
        validatorManagerItem_fieldId = "field:folio:documento/oficinaOrigen/vereda/municipio/departamento/nombre";
        //TODO: element in list validator
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:departamento; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 3:
        validatorManagerItem_fieldId = "field:folio:documento/oficinaOrigen/vereda/municipio/idMunicipio";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 4:
        validatorManagerItem_fieldId = "field:folio:documento/oficinaOrigen/vereda/municipio/nombre";
        // TODO: element in list validator
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:departamento; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 5:
        validatorManagerItem_fieldId = "field:folio:documento/oficinaOrigen/vereda/idVereda";
        // VERIFY: no tiene validadores
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 6:
        validatorManagerItem_fieldId = "field:folio:documento/oficinaOrigen/vereda/nombre";
        // VERIFY: no tiene validadores
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 7:
        validatorManagerItem_fieldId = "field:folio:documento/oficinaOrigen/idOficinaOrigen";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:oficinaOrigen; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 8:
        validatorManagerItem_fieldId = "field:folio:documento/oficinaOrigen/nombre";
        // VERIFY: no tiene validadores
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 9:
        validatorManagerItem_fieldId = "field:folio:documento/idDocumento";
        // VERIFY: no tiene validadores: este valor es asignado al realizar la insercion
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:municipio; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 10:
        validatorManagerItem_fieldId = "field:folio:documento/tipoDocumento/idTipoDocumento";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:tipo documento; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 11:
        validatorManagerItem_fieldId = "field:folio:documento/tipoDocumento/nombre";
        // TODO: element in list validator
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "documento:oficina de procedencia:departamento; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 12:A
        validatorManagerItem_fieldId = "field:folio:documento/numero";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:numero; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 12:B
        // VERIFY: es requerido ?
        validatorManagerItem_fieldId = "field:folio:documento/numero";
        validatorManagerItem_conditionalValidator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:numero; este campo acepta valores numericos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 13:A
        validatorManagerItem_fieldId = "field:folio:documento/fecha";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:fecha; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, fechaRad, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 13:B (se debe probar que la fecha se haya escrito en un formato valido; usa el objeto original del request para hacer la prueba)
        // inicialmente aplica un validador de objeto no nulo, al resultado de la funcion dar_fecha, que prueba la conversion;
        // TODO: hacer validador de fecha con un formato determinado.
        validatorManagerItem_fieldId = "field:folio:documento/fecha";
        validatorManagerItem_conditionalValidator = new BasicObjectNotNullValidatorWrapper();
        validatorManagerItem_errorMessage = "documento:fecha; debe escribir una fecha con formato valido";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, documento_fecha_t1, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 14:A: Estado de folio; se necesita solo concer si varió idEstadoFolio
        //    y si varió, debe colocar algo en el valor de comentario de estado
        //    Si se mira el cambio en los permisos
        validatorManagerItem_fieldId = "field:folio:estado/idEstado";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "estadoFolio; se ha detectado que este campo ha variado; debe escribir un comentario justificando el cambio de estado";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, comentarioEstadoFolio, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 14:B: Este es un validador similar;
        //    si ha variado, no puede haberse escogido null; WebKeys.SIN_SELECCIONAR
        //    Si se mira el cambio en los permisos
        validatorManagerItem_fieldId = "field:folio:estado/idEstado";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "estadoFolio; debe seleccionar un valor valido para le estado del folio.";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 14:C: Este es un validador similar;
        //    si ha variado, no puede haberse escogido null; WebKeys.SIN_SELECCIONAR
        //    No se mira el cambio en los permisos
        validatorManagerItem_fieldId = "field:folio:estado/idEstado";
        validatorManagerItem_conditionalValidator = new BasicStringPatternValidatorWrapper(WebKeys.SIN_SELECCIONAR, true, true);
        validatorManagerItem_errorMessage = "estadoFolio; debe seleccionar un valor valido para el estado del folio.";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 15:A: Es un validador para el tipo de documento
        // Previamente se ha recorrido una lista; en este punto, se observa
        // si segun los permisos y segun la condicion de activacion, se ha seleccinado un valor valido.
        // el validador observa si ha variado el valor de idTipoDocumento
        validatorManagerItem_fieldId = "field:folio:documento/tipoDocumento/idTipoDocumento";
        validatorManagerItem_conditionalValidator = new BasicObjectNotNullValidatorWrapper();
        validatorManagerItem_errorMessage = "documento.tipoDocumento; debe seleccionar un tipo de documento existente.";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 16:a; validar que si varian datos en la oficina de procedencia,
        // se escriba o especifique la oficina origen
        // puede que en si la oficina origen no este variando; pero si debe tener permisos para cambiar el campo
        validatorManagerItem_fieldId = "field:folio:documento/oficinaOrigen/idOficinaOrigen";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "oficina origen; se han detectado cambios en datos de la oficina origen; se debe elegir la oficina para continuar.";

        // -----------------------------------------------------------------------------
        // 2: validar tipo predio
        // 2:A not null
        validatorManagerItem_fieldId = "field:folio:tipoPredio/idPredio";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "folio:tipo-predio; el tipo de predio no puede ser nulo";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        //    not WebKeys.SIN_SELECCIONAR
        validatorManagerItem_fieldId = "field:folio:tipoPredio/idPredio";
        validatorManagerItem_conditionalValidator = new BasicStringPatternValidatorWrapper(WebKeys.SIN_SELECCIONAR, true, true);
        validatorManagerItem_errorMessage = "folio:tipo-predio; debe escoger un tipo de predio valido";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // -----------------------------------------------------------------------------
        // Bug: 3580
        //
        //
        validatorManagerItem_fieldId = "field:folio:fechaApertura";
        validatorManagerItem_conditionalValidator = new BasicDatatypeString2DateConversionValidatorWrapper("dd/MM/yyyy"); // new BasicStringNotNullOrEmptyValidatorWrapper();//
        validatorManagerItem_errorMessage = "fechaApertura; este campo acepta fechas (dd/MM/yyyy)";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, local_Folio_FechaApertura, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        //
        validatorManagerItem_fieldId = "field:folio:radicacion";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "radicacion; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        /*






    // 3: validar tipoPredio.ElementInList

    // folio.tipoPredio
    delegatedValidator_Folio_TipoPredio_ElementInList: {

        List tiposPredio = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);

        boolean elementFounded = false;

        if( null != tiposPredio ) {
            Iterator iterator = tiposPredio.iterator();
            for (; iterator.hasNext(); ) {

                ElementoLista elementoTipoPredio = (ElementoLista) iterator.next();

                if (elementoTipoPredio.getId().equals(valorTipoPredio)) {
                    nombreTipoPredio = elementoTipoPredio.getValor();
                    elementFounded = true;
                    break;
                }
            }
            if( !elementFounded ){
                exception.addError( "tipoPredio; no se encontro el valor seleccionado" );
            }
        }
        else {
            exception.addError("tipoPredio; La lista de los tipos de predio no se encontro");
        }

    } // :delegatedValidator_Folio_TipoPredio_ElementInList




         */
        // aparte de las condiciones basicas se registra otra condicion: la de la variacion de los campos de ubicacion de oficina
        write_altern_conditions:
        {

            String fieldId;
            BindManager.Binder searchedBind;
            String path;
            boolean[] conditions = new boolean[4];

            int conditionCount = 0;

            fieldId = "field:folio:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            conditions[conditionCount++] = processor.pathHasChanges(path); // el atributo ha cambiado

            fieldId = "field:folio:documento/oficinaOrigen/vereda/municipio/idMunicipio";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            conditions[conditionCount++] = processor.pathHasChanges(path); // el atributo ha cambiado

            fieldId = "field:folio:documento/oficinaOrigen/vereda/idVereda";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            conditions[conditionCount] = processor.pathHasChanges(path); // el atributo ha cambiado
            conditionCount++;

            conditionCount = 0;
            validatorManagerItem_conditionalValidator.addCondition(new BasicStaticBooleanCondition(conditions[conditionCount++] || conditions[conditionCount++] || conditions[conditionCount++]));

        }
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, false, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // aparte de las condiciones basicas se registra otra condicion: el comentario debe
        // tener alguin valor si el estado de folio cambia
        write_altern_conditions_FolioEstado_Comentario:
        {

        }

        // adicional: si se construye la vereda, el servicio necesita que se envie
        // idDepto, idMunicipio, idVereda
        write_UbicaGeoFields_IfExists:
        {

            String fieldId;
            BindManager.Binder searchedBind;
            String path;
            Object value;
            boolean local_Condition;
            JXPathContext local_Context;
            boolean[] conditions = new boolean[4];

            int conditionCount = 0;

            Object zonaRegistral_IdVereda;
            String zonaRegistral_IdMunicipio;
            String zonaRegistral_IdDepartamento;

            // ----
            fieldId = "field:folio:zonaRegistral/vereda/idVereda";
            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            local_Condition = processor.pathHasChanges(path);
            if (local_Condition) {
                local_Context = processor.getDeltaContext();
            } else {
                local_Context = processor.getSourceContext();
            }
            zonaRegistral_IdVereda = BasicJXPathForwardDiffProcessor.getProperty(local_Context, path);

            conditions[conditionCount++] = local_Condition; // el atributo ha cambiado
            // ----

            fieldId = "field:folio:zonaRegistral/vereda/municipio/idMunicipio";

            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            local_Condition = processor.pathHasChanges(path);
            if (local_Condition) {
                local_Context = processor.getDeltaContext();
            } else {
                local_Context = processor.getSourceContext();
            }
            zonaRegistral_IdMunicipio = (String) BasicJXPathForwardDiffProcessor.getProperty(local_Context, path);

            conditions[conditionCount++] = local_Condition; // el atributo ha cambiado
            // ----

            fieldId = "field:folio:zonaRegistral/vereda/municipio/departamento/idDepartamento";

            searchedBind = bindManager.getBinderById(fieldId);
            path = searchedBind.getPath();

            local_Condition = processor.pathHasChanges(path);
            if (local_Condition) {
                local_Context = processor.getDeltaContext();
            } else {
                local_Context = processor.getSourceContext();
            }
            zonaRegistral_IdDepartamento = (String) BasicJXPathForwardDiffProcessor.getProperty(local_Context, path);

            conditions[conditionCount++] = local_Condition; // el atributo ha cambiado
            // ----

            // put attribs
            conditionCount = 0;
            if (conditions[conditionCount++]
                    || conditions[conditionCount++]
                    || conditions[conditionCount++]) {

                path = "zonaRegistral/vereda/idVereda";
                value = zonaRegistral_IdVereda;
                BasicJXPathForwardDiffProcessor.setProperty(processor.getDeltaContext(), path, value);

                path = "zonaRegistral/vereda/idMunicipio";
                value = zonaRegistral_IdMunicipio;
                BasicJXPathForwardDiffProcessor.setProperty(processor.getDeltaContext(), path, value);

                path = "zonaRegistral/vereda/idDepartamento";
                value = zonaRegistral_IdDepartamento;
                BasicJXPathForwardDiffProcessor.setProperty(processor.getDeltaContext(), path, value);

            } // :if

        } // :write_UbicaGeoFields_IfExists

        // 17: validar no nulo
        validatorManagerItem_fieldId = "field:folio:zonaRegistral/vereda/idVereda";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "ubicacion geografica (zona registral) - vereda ; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 18: validar no nulo
        // VERIFY: es requerido ?
        validatorManagerItem_fieldId = "field:folio:zonaRegistral/vereda/nombre";
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "ubicacion geografica (zona registral) - vereda ; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 19: validar no nulo
        validatorManagerItem_fieldId = "field:folio:zonaRegistral/vereda/municipio/idMunicipio";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "ubicacion geografica (zona registral) - municipio ; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 20: validar no nulo
        // VERIFY: es requerido ?
        validatorManagerItem_fieldId = "field:folio:zonaRegistral/vereda/municipio/nombre";
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "ubicacion geografica (zona registral) - vereda ; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // 21: validar no nulo
        validatorManagerItem_fieldId = "field:folio:zonaRegistral/vereda/municipio/departamento/idDepartamento";
        validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        validatorManagerItem_errorMessage = "ubicacion geografica (zona registral) - departamento ; este campo no acepta valores nulos";
        writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true);
        validatorManager.addBinder(new ValidatorManager.Binder(validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage));

        // 22: validar no nulo
        // VERIFY: es requerido ?
        validatorManagerItem_fieldId = "field:folio:zonaRegistral/vereda/municipio/departamento/nombre";
        // validatorManagerItem_conditionalValidator = new BasicStringNotNullOrEmptyValidatorWrapper();
        // validatorManagerItem_errorMessage = "ubicacion geografica (zona registral) - vereda ; este campo no acepta valores nulos";
        // writeBasicConditionsToValidator(validatorManagerItem_conditionalValidator, bindManager, validatorManagerItem_fieldId, processor, localProcessManager, true, true  );
        // validatorManager.addBinder( new ValidatorManager.Binder( validatorManagerItem_fieldId, validatorManagerItem_conditionalValidator, validatorManagerItem_errorMessage )  );

        // ---------------------------
        // ---------------------------
        // ---------------------------
        // END: DOCUMENTO
        // ---------------------------
        // ---------------------------
        // ---------------------------
        // realizar prueba de validadores
        validatorManager.trigger(exception);

        // validaciones (stage 2): raise ---------------------------------------------------------------
        if (exception.getErrores().size() > 0) {
            if (parametro.equals(AWModificarFolio.ACEPTAR_EDICION_CORRECCION)) {
                ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
                throw ex1;
            }

            if (parametro.equals(AWModificarFolio.ACEPTAR_EDICION_DIGITACION)) {
                ValidacionParametrosModificarFolioDigitacionException ex2 = new ValidacionParametrosModificarFolioDigitacionException(exception.getErrores());
                throw ex2;
            }

            if (parametro.equals(AWModificarFolio.ACEPTAR_EDICION_ESPECIALIZADO)) {
                ValidacionParametrosModificarFolioEspecializadoException ex3 = new ValidacionParametrosModificarFolioEspecializadoException(exception.getErrores());
                throw ex3;
            }
        }
        // ---------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------------
        // fijar el conjunto de valores que inicialmente no se estan manejando con jxpath (Colocar el resto de valores que no admiten jxpath manualmente)

        // ---------------------------------------
        // A: Conjunto de direcciones a insertar:
        List t0_folio_direcciones = null; // direcciones existentes en db
        List t1_folio_direcciones = null; // direccione existentes en sesion
        List t2_folio_direcciones = null; // direcciones a enviar como delta

        // modificar las direcciones
        delta_folio_direcciones:
        {

            // Conditional javaSrcBlock:
            // debe tener permiso para modificar direcciones
            boolean activateEvaluation;

            activateEvaluation = ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:direcciones");

            if (activateEvaluation) {

                // criterio para enviar direcciones a creacion: no tiene identificador
                // criterio para enviar direcciones a eliminacion: setToDelete : true
                // se debe tener en cuenta que se muestran 2 listas: una con los cambios definitivos y otra
                // con los temporales
                // para adicionar, solamente se recorre la lista de temporales
                // para eliminar, solamente se recorre la lista de definitivos
                // En sesion se cargan a t1 los valores de t0; se manipulan,
                // y luego se hace la actualizacion; se deben observar las variaciones
                // en este punto.
                // paso general:
                // para cada una de las direcciones en sesion:
                // observar si ya existia;
                // si tiene el flag toDelete = true, se coloca en la otra lista y se envia a eliminar
                // si no tiene id de direccion, se envia, para que se cree.
                // se hace con 2 ciclos para mayor claridad
                // A: recorrido por direcciones temporales
                // configurar-general
                t0_folio_direcciones = t0_folio.getDirecciones();
                t2_folio_direcciones = new java.util.ArrayList();

                if (null == t0_folio_direcciones) {
                    t0_folio_direcciones = new java.util.ArrayList();
                }

                // configurar para esta comparacion
                t1_folio_direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

                if (null == t1_folio_direcciones) {
                    t1_folio_direcciones = new java.util.ArrayList();
                }

                // forma de realizarlo:
                // 1. explorar nuevas adiciones
                // : las que estan en t1
                for (java.util.Iterator iteratorT1 = t1_folio_direcciones.iterator();
                        iteratorT1.hasNext();) {
                    Direccion t1_element = (Direccion) iteratorT1.next();
                    String t1_element_id = t1_element.getIdDireccion();
                    boolean existsInT0 = false;
                    // for (java.util.Iterator iteratorT0 = t0_folio_direcciones.iterator();
                    //                                     iteratorT0.hasNext(); ) {
                    //    Direccion t0_element = (Direccion) iteratorT0.next();
                    //    // tener en cuenta null's en id;
                    //    String t0_element_id = t0_element.getIdDireccion();
                    //
                    //    if ((null != t0_element_id)
                    //        && (null != t1_element_id)
                    //        && (t0_element_id.equalsIgnoreCase(t1_element_id))) {
                    //        existsInT0 = true;
                    //        break;
                    //    } // end if
                    // } // end for

                    if (!existsInT0) {
                        // ver si tiene flag set to delete en true; si lo tiene, no es necesario agregarla
                        // otra condicion es que no puede tener el id, porque hasta ahora, no se ha creado

                        if (t1_element.isToDelete()) {
                            continue;
                        }
                        if (null != t1_element.getIdDireccion()) {
                            continue;
                        }

                        // se añade a la lista de cambios
                        t2_folio_direcciones.add(t1_element);
                        // se remueve de la otra lista ?
                        // por el momento, no, para no afectar la iteracion; luego se podrian dejar en un arreglo

                    } // end if

                } // end for

                // B: recorrido por definitivos
                // B.a: configurar para esta comparacion
                // configurar para esta comparacion
                // la lista modificada es la misma que se usa en t0
                //t1_folio_direcciones = t0_folio_direcciones;
                if (null == t1_folio_direcciones) {
                    t1_folio_direcciones = new java.util.ArrayList();
                }

                for (java.util.Iterator iteratorT1 = t1_folio_direcciones.iterator();
                        iteratorT1.hasNext();) {
                    Direccion t1_element = (Direccion) iteratorT1.next();

                    // si tiene flag setToDelete = true, se agrega en la lista de modificaciones
                    if (null == t1_element) {
                        continue;
                    }

                    // debe tener id
                    if (null == t1_element.getIdDireccion()) {
                        continue;
                    }

                    if (t1_element.isToDelete()) {
                        t2_folio_direcciones.add(t1_element);
                    }

                }

                // 3. si no hay cambios, se deja nula la lista de cambios
                if ((null == t2_folio_direcciones)
                        || (t2_folio_direcciones.size() == 0)) {
                    t2_folio_direcciones = null;
                }

                // 4. se agregan las variaciones en las direcciones
                if (null != t2_folio_direcciones) {
                    for (Iterator iterator = t2_folio_direcciones.iterator(); iterator.hasNext();) {
                        Direccion element = (Direccion) iterator.next();
                        t2_folio.addDireccione(element);
                    }

                }

            } // end if

        } // :delta_folio_direcciones

        // ---------------------------------------
        // B: Salvedad
        // bug 3530/3560 modified
        SalvedadFolio salvedadFolio = new SalvedadFolio();
        salvedadFolio.setDescripcion(param_SalvedadFolio_Descripcion);
        salvedadFolio.setIdSalvedad(param_SalvedadFolio_Id);
        salvedadFolio.setIdMatricula(t0_folio.getIdMatricula());
        salvedadFolio.setNumRadicacion(param_SalvedadFolio_NumRadicacion);

        // List< SalvedadFolio >
        List local_SalvedadFolioList;
        local_SalvedadFolioList = new ArrayList();

        local_SalvedadFolioList.add(salvedadFolio);
        t2_folio.setSalvedades(local_SalvedadFolioList);

        // if (idMatricula != null) {
        //        salvedadFolio.setIdMatricula(idMatricula);
        // }
        // t2_folio.addSalvedade( salvedadFolio );
        // -----------------------------------------------------------------------------------------------
        // fijar pk de folio y hacer paso de registro en sesion
        // para enviar los valores a negocio
        t2_folio.setIdMatricula(t0_folio.getIdMatricula());

        // ESTA QUITANDO EL DATO NUEVO DE ZONA REGISTRAL
        //t2_folio.setZonaRegistral( t0_folio.getZonaRegistral() );
        // -----------------------------------------------------------------------------------------------
        // test condition

        /*
    * @author      :   Henry Gómez Rocha
    * @change      :   Con el propósito de optimizar el rendimiento del aplicativo se comentarea
    *                  el siguiente if debido a que nunca ingresa al cuerpo del mismo.
    * Caso Mantis  :   0004503
         */
//    if( false )
//      return null;
        // real condition
        // -----------------------------------------------------------------------------------------------
        // fijar los valores en sesion y despachar el evento
        session.setAttribute(WebKeys.FOLIO_EDITADO, t2_folio);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCorreccion result;
        /**
         * @author: Fernando Padilla Velez
         * @date: 04/01/2010 16:00.
         * @change: Mantis 3524, en este momento el documento del folio contiene
         * solo nulls en sus objetos.
         */

        if (t2_folio.getDocumento() != null && t2_folio.getDocumento().getIdDocumento() == null) {
            /*
          * @author      :   Henry Gómez Rocha
          * @change      :   Se envía t1_folio.getDocumento() en lugar de t0_folio.getDocumento() ya
          *                  que este último no contiene los datos de la sesión.
          * Caso Mantis  :   0004503
             */
            t2_folio.setDocumento(t1_folio.getDocumento());
        }

        if (valorPrivMetros == null || valorPrivMetros.isEmpty()) {
            valorPrivMetros = "0";
        }
        if (valorPrivCentimetros == null || valorPrivCentimetros.isEmpty()) {
            valorPrivCentimetros = "0";
        }
        if (valorConsMetros == null || valorConsMetros.isEmpty()) {
            valorConsMetros = "0";
        }
        if (valorConsCentimetros == null || valorConsCentimetros.isEmpty()) {
            valorConsCentimetros = "0";
        }
        if (valorHectareas == null || valorHectareas.isEmpty()) {
            valorHectareas = "0";
        }
        if (valorMetros == null || valorMetros.isEmpty()) {
            valorMetros = "0";
        }
        if (valorCentimetros == null || valorCentimetros.isEmpty()) {
            valorCentimetros = "0";
        }

        if (valorNupre != null) {
            t2_folio.setNupre(valorNupre);
        }

        if (valorDeterminaInm != null) {
            t2_folio.setDeterminaInm(valorDeterminaInm);
        }

        if (!(valorPrivCentimetros.equals("0") && valorPrivMetros.equals("0"))) {
            t2_folio.setPrivMetros(valorPrivMetros);
            t2_folio.setPrivCentimetros(valorPrivCentimetros);
        }

        if (!(valorConsCentimetros.equals("0") && valorConsMetros.equals("0"))) {
            t2_folio.setConsMetros(valorConsMetros);
            t2_folio.setConsCentimetros(valorConsCentimetros);
        }

        if (valorCoeficiente != null) {
            t2_folio.setCoeficiente(valorCoeficiente);
        }

        if (!(valorHectareas.equals("0") && valorMetros.equals("0") && valorCentimetros.equals("0"))) {
            t2_folio.setHectareas(valorHectareas);
            t2_folio.setMetros(valorMetros);
            t2_folio.setCentimetros(valorCentimetros);
        }

        result = new EvnCorreccion(usuarioAuriga,
                (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO),
                t2_folio,
                turno,
                EvnCorreccion.EDITAR_FOLIO
        );

        // -----------------------------------------------------------------------------------------------
        return result;

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
     * jx_ProcessForwardDifferences
     *
     * @param jxpath_DeltaPaths String[]
     * @param comparatorsToApply Comparator[]
     * @param jxpath_AuthAccess boolean[]
     * @param t0_folio Folio
     * @param t1_folio Folio
     * @param t2_folio Folio
     */
    private BasicJXPathForwardDiffProcessor jx_ProcessForwardDifferences(
            String[] jxpath_DeltaPaths,
            Comparator[] comparatorsToApply,
            boolean[] jxpath_AuthAccess,
            Object t0_Object,
            Object t1_Object,
            Object t2_Object,
            AbstractFactory abstractFactory) {

        BasicJXPathForwardDiffProcessor processor
                = new gov.sir.core.web.helpers.correccion.diff.BasicJxPathBooleanAuthForwardDiffProcessor(
                        jxpath_DeltaPaths,
                        comparatorsToApply,
                        jxpath_AuthAccess
                );
        processor.setAbstractFactory(abstractFactory);
        processor.execute(t0_Object, t1_Object, t2_Object);
        //BasicJXPathForwardDiffProcessor.printComparisonResults( System.out, jxpath_DeltaPaths, processor.getComparisonResults() );

        return processor;

    } // end-method: jx_ProcessForwardDifferences

    public static void writeBasicConditionsToValidator(ConditionalValidator validator, BindManager bindManager, String localFieldId, BasicJXPathForwardDiffProcessor processor, gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager, boolean activateDeltaCondition, boolean activatePermisosCondition) {
        final String LOCAL_FIELD_ID = localFieldId;
        Object objectToValidate = bindManager.getBinderById(LOCAL_FIELD_ID).getValue();
        writeBasicConditionsToValidator(validator, bindManager, localFieldId, processor, localProcessManager, objectToValidate, activateDeltaCondition, activatePermisosCondition);
    }

    /**
     * @param validator al cual se le agregaran las condiciones
     * @param bindManager objeto que tiene los pathis, id's de los campos y los
     * valores
     * @param localFieldId id del campo
     * @param processor procesador ya ejecutado que busca las diferencias entre
     * un objeto en t0 y un objeto en t1 para ver si hubo variacion
     * @param localProcessManager el conjunto de permisos compilados
     * @param paramObjectToValidate el objeto a validar; este parametro se busca
     * en el mapa por una funcion con el mismo nombre, sobrecargada.
     *
     */
    public static void writeBasicConditionsToValidator(ConditionalValidator validator, BindManager bindManager, String localFieldId, BasicJXPathForwardDiffProcessor processor, gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager, Object paramObjectToValidate, boolean activateDeltaCondition, boolean activatePermisosCondition) {
        final String LOCAL_FIELD_ID = localFieldId;
        ConditionComponent condition;

        boolean condition1;
        boolean condition2 = true; // tiene permisos para aplicar el cambio //TODO: aplicar segun la tabla hash; significa que

        writeCondition1:
        {
            // condicion 1: el atributo ha cambiado

            String fieldId = LOCAL_FIELD_ID;
            BindManager.Binder searchedBind = bindManager.getBinderById(fieldId);
            String path = searchedBind.getPath();

            condition1 = processor.pathHasChanges(path); // el atributo ha cambiado
        }

        writeCondition2:
        {
            // condicion 2: se tiene permisos para escribir el campo

            String fieldId = LOCAL_FIELD_ID;
            condition2 = ProcessManager.StaticEvaluator.evaluate(localProcessManager, fieldId);
        }

        if (activateDeltaCondition) {
            // se construye la primera condicion
            condition = new BasicStaticBooleanCondition(condition1);
            validator.addCondition(condition);
        }

        if (activatePermisosCondition) {
            // se construye la segunda condicion
            condition = new BasicStaticBooleanCondition(condition2);
            validator.addCondition(condition);
        }

        // se coloca el valor del campo a validar
        // TODO: hacerlo depender tambien de path
        Object objectToValidate = paramObjectToValidate;
        validator.addParameter(BasicConditionalValidator.OBJECT_TO_VALIDATE, objectToValidate);

    }

    protected Folio buildFolio(Folio result, String[] paths, Object[] values, Comparator[] comparators) {

        org.apache.commons.jxpath.JXPathContext context = org.apache.commons.jxpath.JXPathContext.newContext(result);
        context.setFactory(new FolioAbstractFactory());

        for (int i = 0; i < paths.length; i++) {
            if ((null != paths[i])
                    && (null != values[i])) {

                if ((null != comparators[i])
                        && (comparators[i] instanceof BasicStringComparator)) {

                    BasicStringComparator stringComparatorInstance = (BasicStringComparator) comparators[i];
                    if (stringComparatorInstance.isTreatBlankAsNull()) {
                        if (stringComparatorInstance.compare(values[i], "") == 0) {
                            continue;
                        } // :if
                    }

                } // :if

                //   if( ( values[i] instanceof java.lang.String )
                //      &&( "".equalsIgnoreCase( ( (java.lang.String)values[i] ).trim() ) ) ){
                //       continue;
                //   }
                context.createPathAndSetValue(paths[i], values[i]);
            } // :if
        } // :for
        return result;
    }

    protected Cancelacion buildCancelacion(Cancelacion result, String[] paths, Object[] values) {

        org.apache.commons.jxpath.JXPathContext context = org.apache.commons.jxpath.JXPathContext.newContext(result);
        context.setFactory(new FolioAbstractFactory());

        for (int i = 0; i < paths.length; i++) {
            if ((null != paths[i])
                    && (null != values[i])) {
                if ((values[i] instanceof java.lang.String)
                        && ("".equalsIgnoreCase(((java.lang.String) values[i]).trim()))) {
                    continue;
                }
                context.createPathAndSetValue(paths[i], values[i]);
            }
        }
        return result;
    }

    protected Anotacion buildAnotacion(Anotacion result, String[] paths, Object[] values) {
        org.apache.commons.jxpath.JXPathContext context = org.apache.commons.jxpath.JXPathContext.newContext(result);
        context.setFactory(new FolioAnotacionAbstractFactory());

        for (int i = 0; i < paths.length; i++) {
            if ((null != paths[i])
                    && (null != values[i])) {
                if ((values[i] instanceof java.lang.String)
                        && ("".equalsIgnoreCase(((java.lang.String) values[i]).trim()))) {
                    continue;
                }
                context.createPathAndSetValue(paths[i], values[i]);
            }
        }
        return result;
    }

    /**
     * version anterior de modificacion de folio para que no influya con la
     * aplicacion de permisos correcciones;
     *
     * Permite guardar los cambios que se efectuaron sobre un folio.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnCorreccion aceptarModificacionFolio(HttpServletRequest request, String parametro) throws AccionWebException {
        ValidacionParametrosException exception = new ValidacionParametrosException();

        Folio folioOriginal = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (folioOriginal == null) {
            folioOriginal = new Folio();
        }

        //Realizar validaciones
        preservarInfoBasicaFolio(request);

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (turno == null) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        String valorTipoPredio = request.getParameter(AWModificarFolio.FOLIO_TIPO_PREDIO);
        if (valorTipoPredio == null || valorTipoPredio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo de predio válido");
        }

        String valorComplementacion = request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTACION);
        String valorLindero = request.getParameter(AWModificarFolio.FOLIO_LINDERO);
        String valorCodCatastral = request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL);
        String valorCodCatastralAnt = request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT);
        String valorNupre = request.getParameter(AWModificarFolio.FOLIO_NUPRE);
        String valorDeterminaInm = request.getParameter(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE);
        String valorPrivMetros = request.getParameter(AWModificarFolio.FOLIO_PRIVMETROS);
        String valorPrivCentimetros = request.getParameter(AWModificarFolio.FOLIO_PRIVCENTIMETROS);
        String valorConsMetros = request.getParameter(AWModificarFolio.FOLIO_CONSMETROS);
        String valorConsCentimetros = request.getParameter(AWModificarFolio.FOLIO_CONSCENTIMETROS);
        String valorCoeficiente = request.getParameter(AWModificarFolio.FOLIO_COEFICIENTE);
        String valorHectareas = request.getParameter(AWModificarFolio.FOLIO_HECTAREAS);
        String valorMetros = request.getParameter(AWModificarFolio.FOLIO_METROS);
        String valorCentimetros = request.getParameter(AWModificarFolio.FOLIO_CENTIMETROS);

        String metrosT = "0";
        String centimetrosT = "0";
        int unidadmedidaexception = 3;

        if (valorHectareas == null || valorHectareas.length() <= 0) {
            unidadmedidaexception--;
        }

        if (valorMetros == null || valorMetros.length() <= 0) {
            unidadmedidaexception--;
        } else {
            metrosT = valorMetros;
        }

        if (valorCentimetros == null || valorCentimetros.length() <= 0) {
            unidadmedidaexception--;
        } else {
            centimetrosT = valorCentimetros;
        }

        if (unidadmedidaexception == 0) {
            exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area del Terreno");
        }

        // VALIDAR AREA PRIVADA
        boolean datosPrivBien = true;
        boolean privArea = false;

        String metrosP = "0";
        String centimetrosP = "0";

        if (valorPrivMetros != null && !valorPrivMetros.isEmpty()) {
            privArea = true;
            metrosP = valorPrivMetros;
        }

        if (valorPrivCentimetros != null && !valorPrivCentimetros.isEmpty()) {
            privArea = true;
            centimetrosP = valorPrivCentimetros;
        }

        if (privArea) {
            unidadmedidaexception = 2;
            String metrosC = "0";
            String centimetrosC = "0";

            if (valorConsMetros == null || valorConsMetros.length() <= 0) {
                unidadmedidaexception--;
            } else {
                metrosC = valorConsMetros;
            }

            if (valorConsCentimetros == null || valorConsCentimetros.length() <= 0) {
                unidadmedidaexception--;
            } else {
                centimetrosC = valorConsCentimetros;
            }

            if (unidadmedidaexception == 0) {
                exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
            }

            //Area Privada para Validar
            int pMetros = Integer.parseInt(metrosP);
            int pCentimetros = Integer.parseInt(centimetrosP);
            //Area Construida para Validar
            int cMetros = Integer.parseInt(metrosC);
            int cCentimetros = Integer.parseInt(centimetrosC);
            //Area Construida para Validar
            int tMetros = Integer.parseInt(metrosT);
            int tCentimetros = Integer.parseInt(centimetrosT);

            if (pMetros > cMetros) {
                datosPrivBien = false;
            } else {
                if (pMetros == cMetros) {
                    if (pCentimetros > cCentimetros) {
                        datosPrivBien = false;
                    }
                }
            }

            if (!datosPrivBien) {
                exception.addError("El area privada no puede ser mayor al area construida");
            }
            boolean datosConsBien = true;
            if (cMetros > tMetros) {
                datosConsBien = false;
            } else {
                if (cMetros == tMetros) {
                    if (cCentimetros > tCentimetros) {
                        datosConsBien = false;
                    }
                }
            }

            if (!datosConsBien) {
                exception.addError("El area construida no puede ser mayor al area del terreno");
            }

        } else {

            boolean consArea = false;

            String metrosC = "0";
            String centimetrosC = "0";

            if (valorConsMetros != null && !valorConsMetros.isEmpty()) {
                consArea = true;
                metrosC = valorConsMetros;
            }

            if (valorConsCentimetros != null && !valorConsCentimetros.isEmpty()) {
                consArea = true;
                centimetrosC = valorConsCentimetros;
            }

            if (consArea) {
                //Area Construida para Validar
                int cMetros = Integer.parseInt(metrosC);
                int cCentimetros = Integer.parseInt(centimetrosC);
                //Area Construida para Validar
                int tMetros = Integer.parseInt(metrosT);
                int tCentimetros = Integer.parseInt(centimetrosT);

                boolean datosConsBien = true;
                if (cMetros > tMetros) {
                    datosConsBien = false;
                } else {
                    if (cMetros == tMetros) {
                        if (cCentimetros > tCentimetros) {
                            datosConsBien = false;
                        }
                    }
                }

                if (!datosConsBien) {
                    exception.addError("El area construida no puede ser mayor al area del terreno");
                }
            }
        }

        String salvedad = request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
        if (!String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_REGISTRO)) {
            if (salvedad.length() <= 0) {
                exception.addError("Debe ingresar una salvedad para el Folio");
            } else {
                if (salvedad.length() < 30) {
                    exception.addError("La salvedad debe tener por lo menos 30 carácteres.");
                }
            }
        }

        String estadoFolio = null;
        String comentarioEstadoFolio = request.getParameter(AWModificarFolio.FOLIO_ESTADO_FOLIO_COMENTARIO);
        if (parametro.equals(AWModificarFolio.ACEPTAR_EDICION_CORRECCION) || parametro.equals(AWModificarFolio.ACEPTAR_EDICION_ESPECIALIZADO)) {
            estadoFolio = request.getParameter(AWModificarFolio.FOLIO_ESTADO_FOLIO);
            if (estadoFolio == null || estadoFolio.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar el estado para el folio.");
            } else if (!estadoFolio.equals(folioOriginal.getEstado().getIdEstado())) {
                if (comentarioEstadoFolio == null || comentarioEstadoFolio.trim().length() == 0) {
                    exception.addError("Debe ingresar un comentario para la razón del cambio de estado.");
                }
            }
        }

        //DATOS DEL DOCUMENTO
        String idDoc = request.getParameter(CDocumento.ID_DOCUMENTO);
        String idTipoDoc = request.getParameter(CDocumento.ID_TIPO_DOCUMENTO);
        String tipoDoc = request.getParameter(CDocumento.TIPO_DOCUMENTO);
        String numDoc = request.getParameter(CDocumento.NUM_DOCUMENTO);
        String fechaRad = request.getParameter(CDocumento.FECHA_RADICACION);

        //DATOS DE LA OFICINA DE PROCEDENCIA DEL DOCUMENTO
        String valorDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String nomDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String valorMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String nomMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String valorVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String nomVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);

        String idOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        String numOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String nomOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);

        if ((valorVereda == null || (valorVereda.trim().equals(""))) || nomDepartamento.equals("") || nomMunicipio.equals("")) {
            valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), valorDepartamento, valorMunicipio);
        }

        if (valorVereda != null && !valorVereda.equals("")) {
            if ((idOficina == null) || (idOficina.length() <= 0)) {
                exception.addError("Debe seleccionar una oficina para la oficina de procedencia del documento en la anotación");
            }
        }

        //Lanzar excepción si existio en la validación
        if (exception.getErrores().size() > 0) {
            if (parametro.equals(AWModificarFolio.ACEPTAR_EDICION_CORRECCION)) {
                ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
                throw ex1;
            }

            if (parametro.equals(AWModificarFolio.ACEPTAR_EDICION_DIGITACION)) {
                ValidacionParametrosModificarFolioDigitacionException ex2 = new ValidacionParametrosModificarFolioDigitacionException(exception.getErrores());
                throw ex2;
            }

            if (parametro.equals(AWModificarFolio.ACEPTAR_EDICION_ESPECIALIZADO)) {
                ValidacionParametrosModificarFolioEspecializadoException ex3 = new ValidacionParametrosModificarFolioEspecializadoException(exception.getErrores());
                throw ex3;
            }
        }

        //Llenar campos con la nueva información
        Folio folio = new Folio();
        folio.setIdMatricula(folioOriginal.getIdMatricula());
        //folio.setZonaRegistral(folioOriginal.getZonaRegistral());

        List tiposPredio = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);

        if (tiposPredio != null) {
            Iterator itPredios = tiposPredio.iterator();

            while (itPredios.hasNext()) {
                ElementoLista elementoTipoPredio = (ElementoLista) itPredios.next();

                if (elementoTipoPredio.getId().equals(valorTipoPredio)) {
                    TipoPredio tipoPredio = new TipoPredio();
                    tipoPredio.setIdPredio(valorTipoPredio);
                    tipoPredio.setNombre(elementoTipoPredio.getValor());

                    if (folioOriginal.getTipoPredio() == null) {
                        folio.setTipoPredio(tipoPredio);
                    } else if (!tipoPredio.getIdPredio().equals(folioOriginal.getTipoPredio().getIdPredio())) {
                        folio.setTipoPredio(tipoPredio);
                    }
                }
            }
        } else {
            exception.addError("La lista de los tipos de predio no se encontro");
        }

        EstadoFolio estado = null;
        if (estadoFolio != null && !estadoFolio.equals("")) {
            estado = new EstadoFolio();
            estado.setIdEstado(estadoFolio);
            estado.setComentario(comentarioEstadoFolio);

            if (!estadoFolio.equals(folioOriginal.getEstado().getIdEstado())) {
                folio.setEstado(estado);
            }
        }

        Complementacion comp = new Complementacion();
        comp.setComplementacion(valorComplementacion);

        if ((valorComplementacion != null) && (folioOriginal.getComplementacion() == null)) {
            folio.setComplementacion(comp);
        } else if ((valorComplementacion != null) && (folioOriginal.getComplementacion() != null) && (folioOriginal.getComplementacion().getComplementacion() != null) && !valorComplementacion.equals(folioOriginal.getComplementacion().getComplementacion())) {
            folio.setComplementacion(comp);
        }

        if ((valorLindero != null) && (folioOriginal.getLindero() == null)) {
            folio.setLindero(valorLindero);
        } else if ((valorLindero != null) && (folioOriginal.getLindero() != null) && !valorLindero.equals(folioOriginal.getLindero())) {
            folio.setLindero(valorLindero);
        }

        if ((valorCodCatastral != null) && (folioOriginal.getCodCatastral() == null)) {
            folio.setCodCatastral(valorCodCatastral);
        } else if ((valorCodCatastral != null) && (folioOriginal.getCodCatastral() != null) && !valorCodCatastral.equals(folioOriginal.getCodCatastral())) {
            folio.setCodCatastral(valorCodCatastral);
        }

        if ((valorCodCatastralAnt != null) && (folioOriginal.getCodCatastralAnterior() == null)) {
            folio.setCodCatastralAnterior(valorCodCatastralAnt);
        } else if ((valorCodCatastralAnt != null) && (folioOriginal.getCodCatastralAnterior() != null) && !valorCodCatastralAnt.equals(folioOriginal.getCodCatastralAnterior())) {
            folio.setCodCatastralAnterior(valorCodCatastralAnt);
        }

        if ((valorNupre != null) && (folioOriginal.getNupre() == null)) {
            folio.setNupre(valorNupre);
        } else if ((valorNupre != null) && (folioOriginal.getNupre() != null) && !valorNupre.equals(folioOriginal.getNupre())) {
            folio.setNupre(valorNupre);
        }

        if ((valorDeterminaInm != null) && (folioOriginal.getDeterminaInm() == null)) {
            folio.setDeterminaInm(valorDeterminaInm);
        } else if ((valorDeterminaInm != null) && (folioOriginal.getDeterminaInm() != null) && !valorDeterminaInm.equals(folioOriginal.getDeterminaInm())) {
            folio.setDeterminaInm(valorDeterminaInm);
        }

        if ((valorPrivMetros != null) && (folioOriginal.getPrivMetros() == null)) {
            folio.setPrivMetros(valorPrivMetros);
        } else if ((valorPrivMetros != null) && (folioOriginal.getPrivMetros() != null) && !valorPrivMetros.equals(folioOriginal.getPrivMetros())) {
            folio.setPrivMetros(valorPrivMetros);
        }

        if ((valorPrivCentimetros != null) && (folioOriginal.getPrivCentimetros() == null)) {
            folio.setPrivCentimetros(valorPrivCentimetros);
        } else if ((valorPrivCentimetros != null) && (folioOriginal.getPrivCentimetros() != null) && !valorPrivCentimetros.equals(folioOriginal.getPrivCentimetros())) {
            folio.setPrivCentimetros(valorPrivCentimetros);
        }

        if ((valorConsMetros != null) && (folioOriginal.getConsMetros() == null)) {
            folio.setConsMetros(valorConsMetros);
        } else if ((valorConsMetros != null) && (folioOriginal.getConsMetros() != null) && !valorConsMetros.equals(folioOriginal.getConsMetros())) {
            folio.setConsMetros(valorConsMetros);
        }

        if ((valorConsCentimetros != null) && (folioOriginal.getConsCentimetros() == null)) {
            folio.setConsCentimetros(valorConsCentimetros);
        } else if ((valorConsCentimetros != null) && (folioOriginal.getConsCentimetros() != null) && !valorConsCentimetros.equals(folioOriginal.getConsCentimetros())) {
            folio.setConsCentimetros(valorConsCentimetros);
        }

        if ((valorCoeficiente != null) && (folioOriginal.getCoeficiente() == null)) {
            folio.setCoeficiente(valorCoeficiente);
        } else if ((valorCoeficiente != null) && (folioOriginal.getCoeficiente() != null) && !valorCoeficiente.equals(folioOriginal.getCoeficiente())) {
            folio.setCoeficiente(valorCoeficiente);
        }

        if ((valorHectareas != null) && (folioOriginal.getHectareas() == null)) {
            folio.setHectareas(valorHectareas);
        } else if ((valorHectareas != null) && (folioOriginal.getHectareas() != null) && !valorHectareas.equals(folioOriginal.getHectareas())) {
            folio.setHectareas(valorHectareas);
        }

        if ((valorMetros != null) && (folioOriginal.getMetros() == null)) {
            folio.setMetros(valorMetros);
        } else if ((valorMetros != null) && (folioOriginal.getMetros() != null) && !valorMetros.equals(folioOriginal.getMetros())) {
            folio.setMetros(valorMetros);
        }

        if ((valorCentimetros != null) && (folioOriginal.getCentimetros() == null)) {
            folio.setCentimetros(valorCentimetros);
        } else if ((valorCentimetros != null) && (folioOriginal.getCentimetros() != null) && !valorCentimetros.equals(folioOriginal.getCentimetros())) {
            folio.setCoeficiente(valorCentimetros);
        }

        //SET DEL DOCUMENTO
        Documento documento = new Documento();

        TipoDocumento tipDoc = new TipoDocumento();
        Iterator itTiposDocs = ((List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO)).iterator();
        while (itTiposDocs.hasNext()) {
            ElementoLista elemento = (ElementoLista) itTiposDocs.next();

            if (elemento.getId().equals(idTipoDoc)) {
                tipDoc.setIdTipoDocumento(idTipoDoc);
                tipDoc.setNombre(elemento.getValor());
                break;
            }
            if (elemento.getId().equals(tipoDoc)) {
                tipDoc.setIdTipoDocumento(tipoDoc);
                tipDoc.setNombre(elemento.getValor());
                break;
            }
        }

        OficinaOrigen oficina = new OficinaOrigen();
        oficina.setIdOficinaOrigen(idOficina);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        oficina.setVersion(Long.parseLong(oficinaVersion));
        oficina.setNumero(numOficina);
        oficina.setNombre(nomOficina);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(valorDepartamento);
        departamento.setNombre(nomDepartamento);

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(valorMunicipio);
        municipio.setNombre(nomMunicipio);
        municipio.setDepartamento(departamento);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setNombre(nomVereda);
        vereda.setMunicipio(municipio);
        oficina.setVereda(vereda);

        documento.setIdDocumento(idDoc);
        documento.setOficinaOrigen(oficina);
        documento.setTipoDocumento(tipDoc);
        if (fechaRad != null && fechaRad.length() > 0) {
            documento.setFecha(darFecha(fechaRad).getTime());
        }

        documento.setNumero(numDoc);

        if (documento.getNumero() != null && documento.getOficinaOrigen() != null && documento.getOficinaOrigen().getVereda() != null && documento.getOficinaOrigen().getVereda().getIdVereda() != null && documento.getTipoDocumento() != null && documento.getTipoDocumento().getIdTipoDocumento() != null) {

            folio.setDocumento(documento);
        }

        //SET DE LA SALVEDAD
        String idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
        SalvedadFolio salvedadFolio = new SalvedadFolio();
        salvedadFolio.setDescripcion(salvedad);

        if (idMatricula != null) {
            salvedadFolio.setIdMatricula(idMatricula);
        }

        folio.addSalvedade(salvedadFolio);

        List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones != null) {
            Iterator itDirecciones = direcciones.iterator();

            while (itDirecciones.hasNext()) {
                Direccion temp = ((Direccion) itDirecciones.next());
                folio.addDireccione(temp);
            }
        }

        session.setAttribute(WebKeys.FOLIO_EDITADO, folio);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        return new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), folio, turno, EvnCorreccion.EDITAR_FOLIO);

    }

    /**
     * Permite guardar los cambios que se efectuaron sobre un folio, en el
     * proceso de segregación.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnCorreccion guardarCambiosFolio(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosEditarFolioException exception = new ValidacionParametrosEditarFolioException();

        Folio folioOriginal = (Folio) session.getAttribute(WebKeys.FOLIO_EDITADO);
        if (folioOriginal == null) {
            folioOriginal = new Folio();
        }

        Folio folio = new Folio();

        folio.setIdMatricula(folioOriginal.getIdMatricula());
        //folio.setZonaRegistral(folioOriginal.getZonaRegistral());

        //Realizar validaciones
        preservarInfoBasicaFolio(request);

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (turno == null) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        String valorTipoPredio = request.getParameter(AWModificarFolio.FOLIO_TIPO_PREDIO);
        if (valorTipoPredio == null || valorTipoPredio.equals(WebKeys.SIN_SELECCIONAR)) {
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
        String metrosT = "0";
        String centimetrosT = "0";
        int unidadmedidaexception = 3;

        if (valorHectareas == null || valorHectareas.length() <= 0) {
            unidadmedidaexception--;
        }

        if (valorMetros == null || valorMetros.length() <= 0) {
            unidadmedidaexception--;
        } else {
            metrosT = valorMetros;
        }

        if (valorCentimetros == null || valorCentimetros.length() <= 0) {
            unidadmedidaexception--;
        } else {
            centimetrosT = valorCentimetros;
        }

        if (unidadmedidaexception == 0) {
            exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area del Terreno");
        }

        // VALIDAR AREA PRIVADA
        boolean datosPrivBien = true;
        boolean privArea = false;

        String metrosP = "0";
        String centimetrosP = "0";

        if (valorPrivMetros != null && !valorPrivMetros.isEmpty()) {
            privArea = true;
            metrosP = valorPrivMetros;
        }

        if (valorPrivCentimetros != null && !valorPrivCentimetros.isEmpty()) {
            privArea = true;
            centimetrosP = valorPrivCentimetros;
        }

        if (privArea) {
            unidadmedidaexception = 2;
            String metrosC = "0";
            String centimetrosC = "0";

            if (valorConsMetros == null || valorConsMetros.length() <= 0) {
                unidadmedidaexception--;
            } else {
                metrosC = valorConsMetros;
            }

            if (valorConsCentimetros == null || valorConsCentimetros.length() <= 0) {
                unidadmedidaexception--;
            } else {
                centimetrosC = valorConsCentimetros;
            }

            if (unidadmedidaexception == 0) {
                exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
            }

            //Area Privada para Validar
            int pMetros = Integer.parseInt(metrosP);
            int pCentimetros = Integer.parseInt(centimetrosP);
            //Area Construida para Validar
            int cMetros = Integer.parseInt(metrosC);
            int cCentimetros = Integer.parseInt(centimetrosC);
            //Area Construida para Validar
            int tMetros = Integer.parseInt(metrosT);
            int tCentimetros = Integer.parseInt(centimetrosT);

            if (pMetros > cMetros) {
                datosPrivBien = false;
            } else {
                if (pMetros == cMetros) {
                    if (pCentimetros > cCentimetros) {
                        datosPrivBien = false;
                    }
                }
            }

            if (!datosPrivBien) {
                exception.addError("El area privada no puede ser mayor al area construida");
            }
            boolean datosConsBien = true;
            if (cMetros > tMetros) {
                datosConsBien = false;
            } else {
                if (cMetros == tMetros) {
                    if (cCentimetros > tCentimetros) {
                        datosConsBien = false;
                    }
                }
            }

            if (!datosConsBien) {
                exception.addError("El area construida no puede ser mayor al area del terreno");
            }

        } else {

            boolean consArea = false;

            String metrosC = "0";
            String centimetrosC = "0";

            if (valorConsMetros != null && !valorConsMetros.isEmpty()) {
                consArea = true;
                metrosC = valorConsMetros;
            }

            if (valorConsCentimetros != null && !valorConsCentimetros.isEmpty()) {
                consArea = true;
                centimetrosC = valorConsCentimetros;
            }

            if (consArea) {
                //Area Construida para Validar
                int cMetros = Integer.parseInt(metrosC);
                int cCentimetros = Integer.parseInt(centimetrosC);
                //Area Construida para Validar
                int tMetros = Integer.parseInt(metrosT);
                int tCentimetros = Integer.parseInt(centimetrosT);

                boolean datosConsBien = true;
                if (cMetros > tMetros) {
                    datosConsBien = false;
                } else {
                    if (cMetros == tMetros) {
                        if (cCentimetros > tCentimetros) {
                            datosConsBien = false;
                        }
                    }
                }

                if (!datosConsBien) {
                    exception.addError("El area construida no puede ser mayor al area del terreno");
                }
            }
        }

        String valorComplementacion = request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTACION);
        if (valorComplementacion != null) {
            valorComplementacion = valorComplementacion.toUpperCase();
        }
        String valorLindero = request.getParameter(AWModificarFolio.FOLIO_LINDERO);
        String valorCodCatastral = request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL);
        String valorCodCatastralAnt = request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT);

        String salvedad = request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
        if (!String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_REGISTRO)) {
            if (salvedad.length() <= 0) {
                exception.addError("Debe ingresar una salvedad para el Folio");
            } else {
                if (salvedad.length() < 30) {
                    exception.addError("La salvedad debe tener por lo menos 30 carácteres.");
                }
            }
        }

        String estadoFolio = null;
        estadoFolio = request.getParameter(AWModificarFolio.FOLIO_ESTADO_FOLIO);
        if (estadoFolio == null || estadoFolio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el estado para el folio");
        }

        //DATOS DEL DOCUMENTO
        String idDoc = request.getParameter(CDocumento.ID_DOCUMENTO);
        String idTipoDoc = request.getParameter(CDocumento.ID_TIPO_DOCUMENTO);
        String tipoDoc = request.getParameter(CDocumento.TIPO_DOCUMENTO);
        String numDoc = request.getParameter(CDocumento.NUM_DOCUMENTO);
        String fechaRad = request.getParameter(CDocumento.FECHA_RADICACION);

        //DATOS DE LA OFICINA DE PROCEDENCIA DEL DOCUMENTO
        String valorDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String nomDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String valorMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String nomMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String valorVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String nomVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);

        String idOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        String numOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String nomOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);

        if ((valorVereda == null || (valorVereda.trim().equals(""))) || nomDepartamento.equals("") || nomMunicipio.equals("")) {
            valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), valorDepartamento, valorMunicipio);
        }

        if (valorVereda != null && !valorVereda.equals("")) {
            if ((idOficina == null) || (idOficina.length() <= 0)) {
                exception.addError("Debe seleccionar una oficina para la oficina de procedencia del documento en la anotación");
            }
        }

        //Lanzar excepción si existio en la validación
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List tiposPredio = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);

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

        EstadoFolio estado = null;
        if (estadoFolio != null && !estadoFolio.equals("")) {
            estado = new EstadoFolio();
            estado.setIdEstado(estadoFolio);
            folio.setEstado(estado);
        }

        Complementacion comp = new Complementacion();
        comp.setComplementacion(valorComplementacion);

        folio.setComplementacion(comp);
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
        folio.setCodCatastral(valorCodCatastral);
        folio.setCodCatastralAnterior(valorCodCatastralAnt);

        //SET DEL DOCUMENTO
        Documento documento = new Documento();

        TipoDocumento tipDoc = new TipoDocumento();
        Iterator itTiposDocs = ((List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO)).iterator();
        while (itTiposDocs.hasNext()) {
            ElementoLista elemento = (ElementoLista) itTiposDocs.next();

            if (elemento.getId().equals(idTipoDoc)) {
                tipDoc.setIdTipoDocumento(idTipoDoc);
                tipDoc.setNombre(elemento.getValor());
                break;
            }
            if (elemento.getId().equals(tipoDoc)) {
                tipDoc.setIdTipoDocumento(tipoDoc);
                tipDoc.setNombre(elemento.getValor());
                break;
            }
        }

        OficinaOrigen oficina = new OficinaOrigen();
        oficina.setIdOficinaOrigen(idOficina);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        oficina.setVersion(Long.parseLong(oficinaVersion));
        oficina.setNumero(numOficina);
        oficina.setNombre(nomOficina);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(valorDepartamento);
        departamento.setNombre(nomDepartamento);

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(valorMunicipio);
        municipio.setNombre(nomMunicipio);
        municipio.setDepartamento(departamento);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setNombre(nomVereda);
        vereda.setMunicipio(municipio);
        oficina.setVereda(vereda);

        documento.setIdDocumento(idDoc);
        documento.setOficinaOrigen(oficina);
        documento.setTipoDocumento(tipDoc);
        if (fechaRad != null && fechaRad.length() > 0) {
            documento.setFecha(darFecha(fechaRad).getTime());
        }

        documento.setNumero(numDoc);

        if (documento.getNumero() != null && documento.getOficinaOrigen() != null && documento.getOficinaOrigen().getVereda() != null && documento.getOficinaOrigen().getVereda().getIdVereda() != null && documento.getTipoDocumento() != null && documento.getTipoDocumento().getIdTipoDocumento() != null) {

            folio.setDocumento(documento);
        }

        //SET DE LA SALVEDAD
        String idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
        SalvedadFolio salvedadFolio = new SalvedadFolio();
        salvedadFolio.setDescripcion(salvedad);

        if (idMatricula != null) {
            salvedadFolio.setIdMatricula(idMatricula);
        }

        folio.addSalvedade(salvedadFolio);

        List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones != null) {
            Iterator itDirecciones = direcciones.iterator();

            while (itDirecciones.hasNext()) {
                Direccion temp = ((Direccion) itDirecciones.next());
                folio.addDireccione(temp);
            }
        }

        session.setAttribute(WebKeys.FOLIO_EDITADO, folio);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        return new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), folio, turno, EvnCorreccion.EDITAR_FOLIO);
    }

    /**
     * Permite guardar los cambios que se efectuaron a un folio en la fase de
     * digitación masiva en el proceso de correcciones.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnCorreccion editarFoliosDigitacionMasiva(HttpServletRequest request) throws AccionWebException {
        ValidacionParametrosDigitacionMasivaException exception = new ValidacionParametrosDigitacionMasivaException();
        String parametro = request.getParameter(WebKeys.PARAMETRO);
        Folio folio = new Folio();
        Oficio oficio = null;
        List folios = new Vector();
        String desde = null;
        String hasta = null;
        String tipoActualizaccion = null;

        request.getSession().removeAttribute(WebKeys.MENSAJE);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        long proceso = turno.getIdProceso();

        String salvedad = request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
        if ((String.valueOf(proceso).equals(CProceso.PROCESO_CORRECCION) || String.valueOf(proceso).equals(CProceso.PROCESO_CORRECCIONES))) {

            if ((salvedad == null) || salvedad.equals("")) {
                exception.addError("Debe ingresar una salvedad válida");
            } else {
                if (salvedad.length() < 30) {
                    exception.addError("La salvedad debe tener por lo menos 30 carácteres.");
                }
            }

        }

        if (parametro.equals(CDigitacionMasiva.OPCION_COMPLEMENTACION)) {
            tipoActualizaccion = CDigitacionMasiva.OPCION_COMPLEMENTACION;
            String complementacion = request.getParameter(CDigitacionMasiva.OPCION_COMPLEMENTACION);

            String idComplementacion = request.getParameter(CComplementacion.ID_COMPLEMENTACION);
            String tipoAccion = request.getParameter(CFolio.SELECCIONAR_FOLIO + CDigitacionMasiva.OPCION_COMPLEMENTACION);
            desde = request.getParameter(CFolio.DESDE_MATRICULA + CDigitacionMasiva.OPCION_COMPLEMENTACION);
            hasta = request.getParameter(CFolio.HASTA_MATRICULA + CDigitacionMasiva.OPCION_COMPLEMENTACION);

            if ((complementacion == null) || complementacion.equals("")) {
                exception.addError("Debe ingresar una complementación válida");
            }

            if ((desde == null) || desde.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar un rango inferior correcto");
            }

            if ((hasta == null) || hasta.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar un rango superior correcto");
            }

            if (!desde.equals(WebKeys.SIN_SELECCIONAR) && !hasta.equals(WebKeys.SIN_SELECCIONAR)) {
                if ((new Integer(desde).intValue()) > (new Integer(hasta).intValue())) {
                    exception.addError("Seleccione un rango válido. El rango inferior no debe ser estar por encima del rango superior");
                }
            }

            if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
                if (idComplementacion == null) {
                    exception.addError("Debe ingresar un identificador para la complementación válido");
                }
            }

            if (exception.getErrores().size() > 0) {
                throw exception;
            } else {
                Complementacion comp = new Complementacion();
                comp.setComplementacion(complementacion);

                if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
                    comp.setIdComplementacion(idComplementacion);
                }

                folio.setComplementacion(comp);
            }
        }

        if (parametro.equals(CDigitacionMasiva.OPCION_LINDERO)) {
            tipoActualizaccion = CDigitacionMasiva.OPCION_LINDERO;
            String lindero = request.getParameter(CDigitacionMasiva.OPCION_LINDERO);
            desde = request.getParameter(CFolio.DESDE_MATRICULA + CDigitacionMasiva.OPCION_LINDERO);
            hasta = request.getParameter(CFolio.HASTA_MATRICULA + CDigitacionMasiva.OPCION_LINDERO);

            if ((lindero == null) || lindero.equals("")) {
                exception.addError("Debe ingresar un lindero válido");
            }

            if ((desde == null) || desde.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar un rango inferior correcto");
            }

            if ((hasta == null) || hasta.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar un rango superior correcto");
            }

            if (!desde.equals(WebKeys.SIN_SELECCIONAR) && !hasta.equals(WebKeys.SIN_SELECCIONAR)) {
                if ((new Integer(desde).intValue()) > (new Integer(hasta).intValue())) {
                    exception.addError("Seleccione un rango válido. El rango inferior no debe ser estar por encima del rango superior");
                }
            }

            if (exception.getErrores().size() > 0) {
                throw exception;
            } else {
                folio.setLindero(lindero);
            }
        }

        if (parametro.equals(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL)) {
            tipoActualizaccion = CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL;
            String despacho = request.getParameter(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL);

            if ((despacho == null) || despacho.equals("")) {
                exception.addError("Debe ingresar un despacho judicial válido");
            }

            if (exception.getErrores().size() > 0) {
                throw exception;
            } else {
                oficio = new Oficio();
                oficio.setTextoOficio(despacho);
                oficio.setTurnoHistoria(obtenerTurnoHistoriaActual(turno));
                oficio.setFirmado(false);
            }
        }

        if (parametro.equals(CDigitacionMasiva.OPCION_DIRECCION)) {
            tipoActualizaccion = CDigitacionMasiva.OPCION_DIRECCION;
            List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

            if (direcciones == null) {
                direcciones = new Vector();
            }

            String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);
            String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);
            String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);
            String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);

            desde = request.getParameter(CFolio.DESDE_MATRICULA + CDigitacionMasiva.OPCION_DIRECCION);
            hasta = request.getParameter(CFolio.HASTA_MATRICULA + CDigitacionMasiva.OPCION_DIRECCION);

            if ((valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar el primer eje válido para la dirección");
            }

            if (valorValor1.length() <= 0) {
                if (valorEje1 != null && !valorEje1.equals(CDireccion.SIN_DIRECCION)) {
                    exception.addError("Debe ingresar valor válido para el primer eje de la dirección");
                }
            }

            if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
                valorEje2 = new String();
            } else {
                if (valorValor2.length() <= 0) {
                    exception.addError("Debe ingresar valor válido para el segundo eje  de la dirección");
                }
            }

            List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
            Direccion direccion = new Direccion();

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
                exception.addError("La lista de los ejes no se encontro");
            }

            String complemento = request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
            String spec = null;
            if (direccion.getEje().getNombre() != null) {
                spec = direccion.getEje().getNombre() + " " + valorValor1;
                if (direccion.getEje1() != null && direccion.getEje1().getNombre() != null) {
                    spec += " " + direccion.getEje1().getNombre();
                    if (valorValor2 != null) {
                        spec += " " + valorValor2;
                    }
                }
            }

            if (complemento != null && spec != null) {
                complemento = spec + " " + complemento;
            } else if (complemento == null && spec != null) {
                complemento = spec;
            }
            direccion.setEspecificacion(complemento != null ? complemento.toUpperCase() : null);

            if ((desde == null) || desde.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar un rango inferior correcto");
            }

            if ((hasta == null) || hasta.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar un rango superior correcto");
            }

            if (!desde.equals(WebKeys.SIN_SELECCIONAR) && !hasta.equals(WebKeys.SIN_SELECCIONAR)) {
                if ((new Integer(desde).intValue()) > (new Integer(hasta).intValue())) {
                    exception.addError("Seleccione un rango válido. El rango inferior no debe ser estar por encima del rango superior");
                }
            }

            if (exception.getErrores().size() > 0) {
                throw exception;
            } else {
                folio.addDireccione(direccion);
            }
        }

        if (!parametro.equals(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL)) {

            if (salvedad != null && !salvedad.equals("") && salvedad.length() >= 50) {
                SalvedadFolio salvedadFolio = new SalvedadFolio();
                salvedadFolio.setDescripcion(salvedad);
                folio.addSalvedade(salvedadFolio);
            }

            Solicitud solicitud = (Solicitud) turno.getSolicitud();
            if (solicitud == null) {
                solicitud = new Solicitud();
            }

            List solFolio = solicitud.getSolicitudFolios();
            if (solFolio == null) {
                solFolio = new Vector();
            }

            List temp = new ArrayList();
            temp.addAll(solFolio);

            Collections.sort(temp, new Comparator() {
                public int compare(Object arg0, Object arg1) {
                    SolicitudFolio solFolio1 = (SolicitudFolio) arg0;
                    SolicitudFolio solFolio2 = (SolicitudFolio) arg1;
                    return solFolio1.getIdMatricula().compareTo(solFolio2.getIdMatricula());
                }
            });

            int i = 0;
            int from = (new Integer(desde)).intValue();
            int to = (new Integer(hasta)).intValue();

            for (i = from; i <= to; i++) {
                SolicitudFolio sol = (SolicitudFolio) temp.get(i);
                Log.getInstance().debug(AWModificarFolio.class, "ID" + sol.getIdMatricula());
                FolioPk folioID = new FolioPk();
                folioID.idMatricula = sol.getFolio().getIdMatricula();
                folios.add(folioID);
            }
        }

        return new EvnCorreccion(usuarioAuriga, usuarioSIR, folio, folios, turno, EvnCorreccion.EDITAR_FOLIOS, tipoActualizaccion, oficio);
    }

    private TurnoHistoria obtenerTurnoHistoriaActual(Turno turno) {
        List tHistorias = turno.getHistorials();
        ListIterator iter = tHistorias.listIterator();

        String faseActual = turno.getIdFase();

        TurnoHistoria activo = null;
        while (iter.hasNext()) {
            TurnoHistoria tH = (TurnoHistoria) iter.next();
            if (tH.isActivo() && tH.getFase().equals(faseActual)) {
                activo = tH;
                break;
            }
        }
        return activo;
    }

    /**
     * Permite construir el objeto Anotación que debe ingresarse.
     *
     * @param anotacion
     * @param anotaciones
     * @return
     */
    private Anotacion crearAnotacionAIngresar(Anotacion anotacion, List anotaciones) throws AccionWebException {
        Anotacion retorno = new Anotacion();
        List diferencias = (List) session.getAttribute(WebKeys.LISTA_DIFERENCIAS);

        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();
            boolean entro = false;

            while (itAnotaciones.hasNext()) {
                Anotacion temp = ((Anotacion) itAnotaciones.next());

                if ((anotacion.getIdAnotacion() != null) && (temp.getIdAnotacion() != null) && anotacion.getIdAnotacion().equals(temp.getIdAnotacion())) {
                    entro = true;

                    anotacion.setIdAnotacion(anotacion.getIdAnotacion());
                    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
                    anotacion.setIdWorkflow(turno != null ? turno.getIdWorkflow() : null);

                    if ((anotacion.getNumRadicacion() != null) && (temp.getNumRadicacion() == null)) {
                        retorno.setNumRadicacion(anotacion.getNumRadicacion());
                        diferencias.add(new Diferencias(WebKeys.ANOTACION + " " + CAnotacion.NUMERO_RADICACION, " ", anotacion.getNumRadicacion()));
                    } else if ((anotacion.getNumRadicacion() != null) && (temp.getNumRadicacion() != null) && !anotacion.getNumRadicacion().equals(temp.getNumRadicacion())) {
                        retorno.setNumRadicacion(anotacion.getNumRadicacion());
                        diferencias.add(new Diferencias(WebKeys.ANOTACION + " " + CAnotacion.NUMERO_RADICACION, temp.getNumRadicacion(), anotacion.getNumRadicacion()));
                    }

                    if ((anotacion.getDocumento() != null) && (temp.getDocumento() == null)) {
                        retorno.setDocumento(anotacion.getDocumento());

                        //diferencias.add(new Diferencias(WebKeys.ANOTACION  + " "+ CAnotacion.NUMERO_RADICACION, " ", anotacion.getNumRadicacion()));
                    } else if ((anotacion.getDocumento() != null) && (temp.getDocumento() != null) && !anotacion.getDocumento().equals(temp.getDocumento())) {
                        retorno.setDocumento(anotacion.getDocumento());

                        //diferencias.add(new Diferencias(WebKeys.ANOTACION  + " "+ CAnotacion.NUMERO_RADICACION, temp.getNumRadicacion(), anotacion.getNumRadicacion()));
                    }

                    if ((anotacion.getNaturalezaJuridica() != null) && (temp.getNaturalezaJuridica() == null)) {
                        retorno.setNaturalezaJuridica(anotacion.getNaturalezaJuridica());
                        diferencias.add(new Diferencias(WebKeys.ANOTACION + " " + CAnotacion.NATURALEZA_JURIDICA, " ", anotacion.getNaturalezaJuridica().getNombre()));
                    } else if ((anotacion.getNaturalezaJuridica() != null) && (temp.getNaturalezaJuridica() != null) && !anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(temp.getNaturalezaJuridica().getIdNaturalezaJuridica())) {
                        retorno.setNaturalezaJuridica(anotacion.getNaturalezaJuridica());
                        diferencias.add(new Diferencias(WebKeys.ANOTACION + " " + CAnotacion.NATURALEZA_JURIDICA, temp.getNaturalezaJuridica().getNombre(), anotacion.getNaturalezaJuridica().getNombre()));
                    }

                    if ((anotacion.getComentario() != null) && (temp.getComentario() == null)) {
                        retorno.setComentario(anotacion.getComentario());
                        diferencias.add(new Diferencias(WebKeys.ANOTACION + " " + CAnotacion.COMENTARIO, " ", anotacion.getComentario()));
                    } else if ((anotacion.getComentario() != null) && (temp.getComentario() != null) && !anotacion.getComentario().equals(temp.getComentario())) {
                        retorno.setComentario(anotacion.getComentario());
                        diferencias.add(new Diferencias(WebKeys.ANOTACION + " " + CAnotacion.COMENTARIO, temp.getComentario(), anotacion.getComentario()));
                    }

                    if (anotacion.getSalvedades().size() > 0) {
                        retorno.addSalvedade((SalvedadAnotacion) anotacion.getSalvedades().get((anotacion.getSalvedades().size()) - 1));
                        diferencias.add(new Diferencias(WebKeys.ANOTACION + " " + CAnotacion.SALVEDADES, " ", ((SalvedadAnotacion) anotacion.getSalvedades().get((anotacion.getSalvedades().size()) - 1)).getDescripcion()));
                    }

                    List ciudadanos = anotacion.getAnotacionesCiudadanos(); //(List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

                    if (ciudadanos != null) {
                        Iterator itPersonas = ciudadanos.iterator();

                        while (itPersonas.hasNext()) {
                            AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();

                            if ((temp.getAnotacionesCiudadanos() != null) && (temp.getAnotacionesCiudadanos().size() > 0) && !temp.getAnotacionesCiudadanos().contains(ciudadano)) {
                                AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) crearCiudadanoAIngresar(ciudadano, temp.getAnotacionesCiudadanos());
                                retorno.addAnotacionesCiudadano(anotacionCiudadano);
                            }
                        }
                    }
                }
            }

            if (entro == false) {
                retorno = anotacion;
            }
        } else {
            retorno = anotacion;
        }

        session.setAttribute(WebKeys.LISTA_DIFERENCIAS, diferencias);
        return retorno;
    }

    /**
     * Permite crear el objeto AnotacionCiudadano que debe ingresarse.
     *
     * @param anotacionCiudadano
     * @param ciudadanos
     * @return
     */
    private AnotacionCiudadano crearCiudadanoAIngresar(AnotacionCiudadano anotacionCiudadano, List ciudadanos) throws AccionWebException {
        AnotacionCiudadano retorno = new AnotacionCiudadano();
        Ciudadano ciudadano = new Ciudadano();
        retorno.setCiudadano(ciudadano);

        List diferencias = (List) session.getAttribute(WebKeys.LISTA_DIFERENCIAS);

        if (ciudadanos != null) {
            Iterator itCiudadanos = ciudadanos.iterator();
            boolean entro = false;

            while (itCiudadanos.hasNext()) {
                AnotacionCiudadano temp = (AnotacionCiudadano) itCiudadanos.next();

                if (temp.getCiudadano().getDocumento().equals(anotacionCiudadano.getCiudadano().getDocumento()) && temp.getCiudadano().getTipoDoc().equals(anotacionCiudadano.getCiudadano().getTipoDoc())) {
                    entro = true;

                    if ((anotacionCiudadano.getRolPersona() != null) && (temp.getRolPersona() == null)) {
                        retorno.setRolPersona(anotacionCiudadano.getRolPersona());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CAnotacionCiudadano.ROL, " ", anotacionCiudadano.getRolPersona()));
                    } else if ((anotacionCiudadano.getRolPersona() != null) && (temp.getRolPersona() != null) && !anotacionCiudadano.getRolPersona().equals(temp.getRolPersona())) {
                        retorno.setRolPersona(anotacionCiudadano.getRolPersona());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CAnotacionCiudadano.ROL, temp.getRolPersona(), anotacionCiudadano.getRolPersona()));
                    }

                    if ((anotacionCiudadano.getParticipacion() != null) && (temp.getParticipacion() == null)) {
                        retorno.setParticipacion(anotacionCiudadano.getParticipacion().toUpperCase());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CAnotacionCiudadano.PORCENTAJE, " ", anotacionCiudadano.getParticipacion()));
                    } else if ((anotacionCiudadano.getParticipacion() != null) && (temp.getParticipacion() != null) && !anotacionCiudadano.getParticipacion().equals(temp.getParticipacion())) {
                        retorno.setParticipacion(anotacionCiudadano.getParticipacion().toUpperCase());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CAnotacionCiudadano.ROL, temp.getParticipacion(), anotacionCiudadano.getParticipacion()));
                    }

                    if ((anotacionCiudadano.getCiudadano().getApellido1() != null) && (temp.getCiudadano().getApellido1() == null)) {
                        ciudadano.setApellido1(anotacionCiudadano.getCiudadano().getApellido1());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CCiudadano.APELLIDO1, temp.getCiudadano().getApellido1(), anotacionCiudadano.getCiudadano().getApellido1()));
                    } else if ((anotacionCiudadano.getCiudadano().getApellido1() != null) && (temp.getCiudadano().getApellido1() != null) && !anotacionCiudadano.getCiudadano().getApellido1().equals(temp.getCiudadano().getApellido1())) {
                        ciudadano.setApellido1(anotacionCiudadano.getCiudadano().getApellido1());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CCiudadano.APELLIDO1, temp.getCiudadano().getApellido1(), anotacionCiudadano.getCiudadano().getApellido1()));
                    }

                    if ((anotacionCiudadano.getCiudadano().getApellido2() != null) && (temp.getCiudadano().getApellido2() == null)) {
                        ciudadano.setApellido2(anotacionCiudadano.getCiudadano().getApellido2());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CCiudadano.APELLIDO2, temp.getCiudadano().getApellido2(), anotacionCiudadano.getCiudadano().getApellido2()));
                    } else if ((anotacionCiudadano.getCiudadano().getApellido2() != null) && (temp.getCiudadano().getApellido2() != null) && !anotacionCiudadano.getCiudadano().getApellido2().equals(temp.getCiudadano().getApellido2())) {
                        ciudadano.setApellido2(anotacionCiudadano.getCiudadano().getApellido2());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CCiudadano.APELLIDO2, temp.getCiudadano().getApellido2(), anotacionCiudadano.getCiudadano().getApellido2()));
                    }

                    if ((anotacionCiudadano.getCiudadano().getNombre() != null) && (temp.getCiudadano().getNombre() == null)) {
                        ciudadano.setNombre(anotacionCiudadano.getCiudadano().getNombre());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CCiudadano.NOMBRE, temp.getCiudadano().getNombre(), anotacionCiudadano.getCiudadano().getNombre()));
                    } else if ((anotacionCiudadano.getCiudadano().getNombre() != null) && (temp.getCiudadano().getNombre() != null) && !anotacionCiudadano.getCiudadano().getNombre().equals(temp.getCiudadano().getNombre())) {
                        ciudadano.setNombre(anotacionCiudadano.getCiudadano().getNombre());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CCiudadano.NOMBRE, temp.getCiudadano().getNombre(), anotacionCiudadano.getCiudadano().getNombre()));
                    }

                    if ((anotacionCiudadano.getCiudadano().getSexo() != null) && (temp.getCiudadano().getSexo() == null)) {
                        ciudadano.setSexo(anotacionCiudadano.getCiudadano().getSexo());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CCiudadano.SEXO, temp.getCiudadano().getSexo(), anotacionCiudadano.getCiudadano().getSexo()));
                    } else if ((anotacionCiudadano.getCiudadano().getSexo() != null) && (temp.getCiudadano().getSexo() != null) && !anotacionCiudadano.getCiudadano().getSexo().equals(temp.getCiudadano().getSexo())) {
                        ciudadano.setSexo(anotacionCiudadano.getCiudadano().getSexo());
                        diferencias.add(new Diferencias(WebKeys.CIUDADANO + " " + CCiudadano.SEXO, temp.getCiudadano().getSexo(), anotacionCiudadano.getCiudadano().getSexo()));
                    }
                }
            }

            if (entro == false) {
                if (anotacionCiudadano.getCiudadano() != null) {
                    retorno.setCiudadano(anotacionCiudadano.getCiudadano());
                }
            }
        } else {
            retorno = anotacionCiudadano;
        }

        session.setAttribute(WebKeys.LISTA_DIFERENCIAS, diferencias);
        return retorno;
    }

    /**
     * Permite no hacer definitivos los cambios que se realizaron al folio.
     *
     * @param request
     * @return
     */
    private EvnFolio cancelarModificacionFolio(HttpServletRequest request) throws AccionWebException {
        try {
            eliminarInfoBasicaFolio();
            eliminarInfoBasicaCiudadano();
            eliminarInfoBasicaAnotacion();
            session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
            session.removeAttribute(AWModificarFolio.AGREGAR_ANOTACION);
        } catch (Exception e) {
            throw new ParametroInvalidoException("No se pudo cancelar la operación" + e.getMessage());
        }
        return null;
    }

    /**
     * Permite agregar una nueva anotación al folio objeto de corrección.
     *
     * @param request
     * @return
     */
    private EvnCorreccion agregarAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfo(request);

        // added
        String param_NumRadicacion = request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION);

        //Se trae para saber cuál es el orden a colocarle a la nueva anotación
        List anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if (anotaciones == null) {
            anotaciones = new Vector();
        }

        ValidacionParametrosException exception = new ValidacionParametrosException();

        String valFechaRadicacion = request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION);
        if ((valFechaRadicacion == null) || valFechaRadicacion.equals("")) {
            exception.addError("La fecha de la radicacion en la anotación no puede ser vacia");
        }

        Date fechaRadicacion = null;
        try {
            fechaRadicacion = toDate(valFechaRadicacion);
        } catch (ParseException ex) {
            Logger.getLogger(AWModificarFolio.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (fechaRadicacion == null || fechaRadicacion.equals("")) {
            exception.addError("La fecha de la radicacion en la anotación es inválida");
        }

        //Parametros del Documento
        String tipoDocumento = request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
        if ((tipoDocumento == null) || tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo para el documento de la anotación");
        }

        String numDocumento = request.getParameter(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);
        if ((numDocumento == null) || numDocumento.equals("")) {
            exception.addError("El valor del número del documento en la anotación es inválido");
        }

        String valFechaDocumento = request.getParameter(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
        if ((valFechaDocumento == null) || valFechaDocumento.equals("")) {
            exception.addError("La fecha del documento en la anotación no puede ser vacia");
        }

        Date fechaDocumento = null;
        try {
            fechaDocumento = toDate(valFechaDocumento);
        } catch (ParseException ex) {
            Logger.getLogger(AWModificarFolio.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (fechaDocumento == null) {
            exception.addError("La fecha del documento en la anotación es inválida");
        }

        String valorDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String nomDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
//		if ((valorDepartamento.length() <= 0)) {
//			exception.addError("Debe seleccionar un departamento válido para la oficina de procedencia del documento en la anotación");
//		}

        // bug 3569 fecha de radicacion no puede ser anterior a fecha de documento
        if ((null != fechaRadicacion)
                && (null != fechaDocumento)) {

            if (fechaRadicacion.before(fechaDocumento) && fechaRadicacion.compareTo(fechaDocumento) != 0) {
                exception.addError("fecha radicacion no puede ser menor a fecha de documento");
            } // :if

        } // :if

        String valorMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String nomMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
//		if ((valorMunicipio.length() <= 0)) {
//			exception.addError("Debe seleccionar un municipio válido para la oficina de procedencia del documento en la anotación");
//		}

        String valorVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);

        if ((valorVereda == null || (valorVereda.trim().equals(""))) || nomDepartamento.equals("") || nomMunicipio.equals("")) {
            valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), valorDepartamento, valorMunicipio);
        }

        String nomVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
//		if ((valorVereda.length() <= 0)) {
//			exception.addError("Debe seleccionar una vereda válida para la oficina de procedencia del documento en la anotación");
//		}

        String idOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        String numOficina = new String();
        String nomOficina = new String();
        String oficinaInternacional = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL);

        if (request.getParameter(WebKeys.TIPO_OFICINA_I_N).equals(WebKeys.TIPO_OFICINA_NACIONAL)) {
            if ((valorDepartamento.length() <= 0)) {
                exception.addError("Debe seleccionar un departamento válido para la oficina de procedencia del documento en la anotación");
            }

            if ((valorMunicipio.length() <= 0)) {
                exception.addError("Debe seleccionar un municipio válido para la oficina de procedencia del documento en la anotación");
            }

            if ((valorVereda.length() <= 0)) {
                exception.addError("Debe seleccionar una vereda válida para la oficina de procedencia del documento en la anotación");
            }

            if ((idOficina == null) || (idOficina.length() <= 0)) {
                exception.addError("Debe seleccionar una oficina para la oficina de procedencia del documento en la anotación");
            } else {
                nomOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
                numOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
            }
        } else {
            if ((oficinaInternacional == null) || (oficinaInternacional.length() <= 0)) {
                exception.addError("Debe indicar una oficina internacional para la procedencia del documento en la anotación");
            }
        }
        //fin parametros del documento

        String numEspecificacion = request.getParameter(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION);
        double valorEspecificacion = 0.0d;

        if ((numEspecificacion != null) && !numEspecificacion.equals("")) {
            try {
                valorEspecificacion = Double.parseDouble(numEspecificacion);
                if (valorEspecificacion < 0) {
                    exception.addError("El valor del número de la especificación en la anotación no puede ser negativo o cero");
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor de la especificación en la anotación es inválido");
            }
        }

        String idNaturaleza = request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nomNatJuridica = request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        String modalidad = request.getParameter(AWModificarFolio.ANOTACION_MODALIDAD);
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
         * @Descripcion: Se agrega la variable versionNatJuridica para obtener
         * la version de la naturalesa juridica seleccionada
         */
        String versionNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);

        if ((idNaturaleza == null) || (idNaturaleza.length() <= 0)) {
            exception.addError("Debe seleccionar una naturaleza juridica para la especificación en la anotación");
        }

        if (idNaturaleza.equals("0125") || idNaturaleza.equals("0126")) {
            if (modalidad == null || modalidad.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar una modalidad para los códigos de naturaleza juridica 0125 y 0126 en la anotación");
            }
        }

        /**
         * @Autor: Edgar Lora
         * @Mantis: 0010688
         * @Requerimiento: 066_151
         * @Descripcion: Se cambio la fecha para la validacion de cambio de
         * naturaleza juridica.
         */
        Turno local_Turno;
        local_Turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Date fechaRadicacionFolio = null;
        String fechaAperturaFolio = (String) session.getAttribute(AWModificarFolio.FOLIO_FECHA_APERTURA);
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        if (fechaAperturaFolio != null) {
            try {
                fechaRadicacionFolio = formatoDelTexto.parse(fechaAperturaFolio);
            } catch (Exception ex) {
                Logger.getLogger(AWModificarFolio.class.getName()).log(Level.SEVERE, null, ex);
                fechaRadicacionFolio = null;
            }
        }

        if (idNaturaleza != null) {
            Date fechaComparacion = null;
            try {
                fechaComparacion = formatoDelTexto.parse(CNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS);
            } catch (Exception ex) {
                Logger.getLogger(AWModificarFolio.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean mostrarAntiguas = true;
            boolean mostrarNuevas = true;
            if (local_Turno == null || local_Turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCION)) {
                if (fechaRadicacion != null && idNaturaleza != null) {
                    if (fechaRadicacion.before(fechaComparacion) && fechaRadicacion.compareTo(fechaComparacion) != 0) {
                        if (fechaRadicacionFolio.before(fechaComparacion) && fechaRadicacionFolio.compareTo(fechaComparacion) != 0 && idNaturaleza.length() == 4) {
                            exception.addError("Naturaleza juridica no coincide con fecha de radicación: El código de la naturaleza Juridica debe ser de 3 dígitos");
                        }
                    } else {
                        if (idNaturaleza.length() == 3) {
                            exception.addError("Naturaleza juridica no coincide con fecha de radicación: El código de la naturaleza Juridica debe ser de 4 dígitos");
                        }
                    }
                }
            } else {
                if (fechaRadicacion != null) {
                    if (fechaRadicacion.before(fechaComparacion)) {
                        if (idNaturaleza != null && !idNaturaleza.equals("") && idNaturaleza.length() == 4) {
                            exception.addError("Naturaleza juridica no coincide con fecha de radicación de documento: El código de la naturaleza Juridica debe ser de 3 dígitos");
                        }
                    } else if (idNaturaleza != null && !idNaturaleza.equals("") && idNaturaleza.length() != 4) {
                        exception.addError("Naturaleza juridica no coincide con fecha de radicación de documento: El código de la naturaleza Juridica debe ser de 4 dígitos");
                    }
                }
            }
        }

        String comentario = request.getParameter(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        // TODO: salvedad-verificar;
        // por el momento se coloca el id de wf
        String param_AnotacionSalvedadX_NumRadicacion;
        param_AnotacionSalvedadX_NumRadicacion = local_Turno.getIdWorkflow();

        String salvedad = request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        /**
         * @author: Fernando Padilla
         * @change: 2075: Acta - Requerimiento No 067 - Agregar Salvedad Crear
         * Anotacion, por lo que se asegura la obigatoriedad del campo salvedad
         * al crear la anotación.
         *
         */
        if (salvedad == null || salvedad.trim().equals("")) {
            exception.addError("anotacion:salvedad; debe escribir un valor en la salvedad");
        }

        if ((salvedad != null) && !salvedad.equals("")) {
            if (salvedad.length() < 30) {
                exception.addError("La salvedad debe tener por lo menos 30 carácteres.");
            }
        }

        BasicConditionalValidator stage1_validator;

        // 1:B maxima longitud
        stage1_validator = new BasicStringMaxLengthValidatorWrapper(1024);
        stage1_validator.setObjectToValidate(salvedad);
        stage1_validator.validate();

        if (stage1_validator.getResult() != true) {
            exception.addError("anotacion:salvedad; la salvedad debe tener maximo de 1024 caracteres");
        }

        String idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
        if ((tipoDocumento == null) || tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo para el documento de la anotación");
        }

        String agregarAnotacion = request.getParameter(AWModificarFolio.AGREGAR_ANOTACION);
        if (agregarAnotacion == null) {
            agregarAnotacion = "";
        }

        List ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        if (ciudadanos == null || ciudadanos.size() == 0) {
            exception.addError("Se debe ingresar por lo menos un ciudadano");
        }

        // reference to import validators
        BasicConditionalValidator validator;

        // added validator
        // comparar si es la cadena vacia o nulo;
        validator = new BasicStringNotNullOrEmptyValidatorWrapper();

        validator.setObjectToValidate(param_NumRadicacion);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("Se debe escribir un numero de radicacion");
        }

        // // comparar si es numerica
        // validator = new BasicDatatypeString2IntegerConversionValidatorWrapper();
        // validator.setObjectToValidate( param_NumRadicacion );
        // validator.validate();
        // if( validator.getResult() != true )  {
        //  // throw exception
        //  // no se ha seleccionado una cadena valida
        //  exception.addError( "Se debe escribir un numero de radicacion valido" );
        // }
        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);

            String procesoAdicion = request.getParameter(AWModificarFolio.AGREGAR_ANOTACION);
            session.setAttribute(AWModificarFolio.AGREGAR_ANOTACION, procesoAdicion);

            ValidacionParametrosAgregarAnotacionException ex = new ValidacionParametrosAgregarAnotacionException(exception.getErrores());
            throw ex;

        }

        Anotacion anotacion = new Anotacion();

        TipoAnotacion tipAnot = new TipoAnotacion();
        tipAnot.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);

        anotacion.setTipoAnotacion(tipAnot);
        anotacion.setModalidad(modalidad);

        int pos = anotaciones.size() + 1;
        anotacion.setOrden(String.valueOf(pos));
        anotacion.setFechaRadicacion(fechaRadicacion);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        // added num-radicacion
        anotacion.setNumRadicacion(param_NumRadicacion);
        anotacion.setIdWorkflow(turno != null ? turno.getIdWorkflow() : null);

        Documento documento = new Documento();

        TipoDocumento tipoDoc = new TipoDocumento();

        Iterator itTiposDocs = ((List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO)).iterator();

        while (itTiposDocs.hasNext()) {
            ElementoLista elemento = (ElementoLista) itTiposDocs.next();

            if (elemento.getId().equals(tipoDocumento)) {
                tipoDoc.setNombre(elemento.getValor());
            }
        }

        tipoDoc.setIdTipoDocumento(tipoDocumento);
        documento.setFecha(fechaDocumento);
        documento.setNumero(numDocumento);

        if (request.getParameter(WebKeys.TIPO_OFICINA_I_N).equals(WebKeys.TIPO_OFICINA_NACIONAL)) {
            OficinaOrigen oficina = new OficinaOrigen();
            oficina.setIdOficinaOrigen(idOficina);
            /*
                     *  @author Carlos Torres
                     *  @chage   se agrega validacion de version diferente
                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            oficina.setVersion(Long.parseLong(oficinaVersion));
            oficina.setNumero(numOficina);
            oficina.setNombre(nomOficina);

            Departamento departamento = new Departamento();
            departamento.setIdDepartamento(valorDepartamento);
            departamento.setNombre(nomDepartamento);

            Municipio municipio = new Municipio();
            municipio.setIdMunicipio(valorMunicipio);
            municipio.setNombre(nomMunicipio);
            municipio.setDepartamento(departamento);

            Vereda vereda = new Vereda();
            vereda.setIdDepartamento(valorDepartamento);
            vereda.setIdMunicipio(valorMunicipio);
            vereda.setIdVereda(valorVereda);
            vereda.setNombre(nomVereda);
            vereda.setMunicipio(municipio);
            oficina.setVereda(vereda);

//		documento.setTipoDocumento(tipoDoc);
            documento.setOficinaOrigen(oficina);
        } else {
            documento.setOficinaInternacional(oficinaInternacional);
        }

        documento.setTipoDocumento(tipoDoc);
        //
        anotacion.setDocumento(documento);

        if (valorEspecificacion > 0) {
            anotacion.setValor(valorEspecificacion);
            anotacion.setToUpdateValor(true);
        }

        NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
        naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
        naturalezaJuridica.setNombre(nomNatJuridica);
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
         * @Descripcion: Se agrega la variable versionNatJuridica para obtener
         * la version de la naturalesa juridica seleccionada
         */
        naturalezaJuridica.setVersion(Long.parseLong(versionNatJuridica));

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setComentario(comentario);

        if (salvedad != null && !salvedad.equals("")) {

            SalvedadAnotacion local_SalvedadAnotacion = new SalvedadAnotacion();
            local_SalvedadAnotacion.setDescripcion(salvedad);
            local_SalvedadAnotacion.setNumRadicacion(param_AnotacionSalvedadX_NumRadicacion);
            anotacion.addSalvedade(local_SalvedadAnotacion);

        }

        if (ciudadanos != null) {
            Iterator itPersonas = ciudadanos.iterator();
            int posicion = 0;
            while (itPersonas.hasNext()) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                if (!ciudadano.isToDelete()) {
                    ciudadano.setAnotacion(anotacion);

                    String marcaProp = request.getParameter(CAnotacionCiudadano.ANOTACION_ES_PROPIETARIO_ASOCIACION_EDIT + posicion);
                    if (marcaProp == null || marcaProp.equals("")) {
                        ciudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaProp.equals("X")) {
                        ciudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaProp.equals("I")) {
                        ciudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }
                    ciudadano.setRolPersona(request.getParameter(CAnotacionCiudadano.ANOTACION_TIPO_INTER_ASOCIACION_EDIT + posicion));
                    String participacion = request.getParameter(CAnotacionCiudadano.ANOTACION_PORCENTAJE_ASOCIACION_EDIT + posicion);
                    ciudadano.setParticipacion(participacion != null ? participacion : "");

                    anotacion.addAnotacionesCiudadano(ciudadano);
                }
                posicion++;
            }
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Folio folioOriginal = (Folio) session.getAttribute(WebKeys.FOLIO);
        Folio folio = new Folio();
        folio.setIdMatricula(folioOriginal.getIdMatricula());
        //folio.setZonaRegistral(folioOriginal.getZonaRegistral());
        folio.addAnotacione(anotacion);
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (Usuario) session.getAttribute(WebKeys.USUARIO), folio, EvnCorreccion.EDITAR_FOLIO_ANOTACION);
        evn.setTurno(local_Turno);
        return evn;
    }

    /**
     * Permite agregar un nuevo ciudadano a la anotación.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio agregarCiudadano(HttpServletRequest request) throws AccionWebException {
        //preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        ValidacionParametrosAgregarAnotacionException exception = new ValidacionParametrosAgregarAnotacionException();

        List ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        String tipoPersona = request.getParameter(AWModificarFolio.ANOTACION_MODALIDAD);
        String sexo = request.getParameter(AWModificarFolio.ANOTACION_SEXO_PERSONA);
        String tipoId = request.getParameter(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA);
        String numId = request.getParameter(AWModificarFolio.ANOTACION_NUM_ID_PERSONA);
        String apellido1 = request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA);
        String apellido2 = request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA);
        String nombres = request.getParameter(AWModificarFolio.ANOTACION_NOMBRES_PERSONA);

        //Validación de Ciudadano
        if (tipoPersona == null || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo de persona para el ciudadano");
        }
        if (sexo == null || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un sexo para el ciudadano");
        }
        if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo de identificación para el ciudadano");
        } else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
            if (apellido1 == null || apellido1.trim().equals("")) {
                exception.addError("Debe ingresar el primer apellido del Ciudadano");
            }
        } else if (tipoId.equals(COPLookupCodes.NIT)) {
            if (nombres == null || nombres.trim().equals("")) {
                exception.addError("Debe ingresar la razón social del Ciudadano");
            }
        } else {
            double valorId = 0.0d;
            if (numId == null || numId.trim().equals("")) {
                exception.addError("Debe ingresar el número de identificacion del Ciudadano");
            } else {
                if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
                    try {
                        valorId = Double.parseDouble(numId);
                        if (valorId <= 0) {
                            exception.addError("El valor del documento no puede ser negativo o cero");
                        }
                    } catch (NumberFormatException e) {
                        exception.addError("El número de identificación de la persona en la anotación es inválido. No puede ser alfanumérico");
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

        String tipoIntervecion = request.getParameter(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION);
        if ((tipoIntervecion == null) || tipoIntervecion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe ingresar un tipo de intervencion para la persona en la anotación");
        }

        String participacion = request.getParameter(AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION);

        if ((participacion != null) && !participacion.equals("")) {
            if (participacion.length() > 50) {
                exception.addError("El campo participación de una persona no puede tener mas de 50 caracteres");
            }
        }

        String agregarCiudadano = request.getParameter(AWModificarFolio.AGREGAR_CIUDADANO);
        if (agregarCiudadano == null) {
            agregarCiudadano = "";
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        eliminarInfoBasicaCiudadano();

        AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setApellido1(apellido1);
        ciudadano.setApellido2(apellido2);
        ciudadano.setNombre(nombres);
        ciudadano.setSexo(sexo);
        ciudadano.setDocumento(numId);
        ciudadano.setTipoDoc(tipoId);
        ciudadano.setTipoPersona(tipoPersona);

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        anotacionCiudadano.setCiudadano(ciudadano);
        anotacionCiudadano.setRolPersona(tipoIntervecion);
        anotacionCiudadano.setParticipacion(participacion);

        if (request.getParameter(AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION) == null) {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
        } else {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
        }

        if (sePuedeInsertar(anotacionCiudadano, ciudadanos.iterator())) {
            ciudadanos.add(anotacionCiudadano);
            session.setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        } else {
            exception.addError("El ciudadano que esta intentado ingresar ya se encuentra registrado en la anotación");
            throw exception;
        }

        return null;
    }

    /**
     * Permite agregar una nueva dirección al folio objeto de corrección.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio agregarDireccion(HttpServletRequest request, String parametro) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        if (direcciones == null) {
            direcciones = new Vector();
        }

        ValidacionParametrosException exception = new ValidacionParametrosException();
        String valorEje1 = request.getParameter(AWModificarFolio.FOLIO_EJE1);
        if ((null == valorEje1) || (valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el primer eje válido para la direccion");
        }

        String valorValor1 = request.getParameter(AWModificarFolio.FOLIO_VALOR1);
        if ((null == valorValor1) || (valorValor1.length() <= 0)) {
            exception.addError("Debe ingresar valor válido para el primer eje de la direccion");
        }

        String valorEje2 = request.getParameter(AWModificarFolio.FOLIO_EJE2);
        String valorValor2 = request.getParameter(AWModificarFolio.FOLIO_VALOR2);
        if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
            valorEje2 = new String();
        } else {
            if (valorValor2.length() <= 0) {
                exception.addError("Debe ingresar valor válido para el segundo eje  de la direccion");
            }
        }

        if (exception.getErrores().size() > 0) {
            if (parametro.equals(AWModificarFolio.AGREGAR_DIRECCION_CORRECCION)) {
                ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
                throw ex1;
            }

            if (parametro.equals(AWModificarFolio.AGREGAR_DIRECCION_DIGITACION)) {
                ValidacionParametrosModificarFolioDigitacionException ex2 = new ValidacionParametrosModificarFolioDigitacionException(exception.getErrores());
                throw ex2;
            }

            if (parametro.equals(AWModificarFolio.AGREGAR_DIRECCION_ESPECIALIZADO)) {
                ValidacionParametrosModificarFolioEspecializadoException ex3 = new ValidacionParametrosModificarFolioEspecializadoException(exception.getErrores());
                throw ex3;
            }

            if (parametro.equals(AWModificarFolio.AGREGAR_DIRECCION_EDICION)) {
                ValidacionParametrosEditarFolioException ex4 = new ValidacionParametrosEditarFolioException(exception.getErrores());
                throw ex4;
            }
        }

        List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();

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
            exception.addError("La lista de los ejes no se encontro");
        }

        String complemento = request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
        String spec = null;
        if (direccion.getEje().getNombre() != null) {
            spec = direccion.getEje().getNombre() + " " + valorValor1;
            if ((null != direccion.getEje1())
                    && (null != direccion.getEje1().getNombre())) {
                spec += " " + direccion.getEje1().getNombre();
                if (valorValor2 != null) {
                    spec += " " + valorValor2;
                }
            }
        }

        if (complemento != null && spec != null) {
            complemento = spec + " " + complemento;
        } else if (complemento == null && spec != null) {
            complemento = spec;
        }
        direccion.setEspecificacion(complemento != null ? complemento.toUpperCase() : null);

        direcciones.add(direccion);
        eliminarInfoBasicaDireccion();

        session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
        return null;
    }

    /**
     * Permite editar una dirección al folio objeto de corrección.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio editarDireccionAceptar(HttpServletRequest request, String parametro) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        ValidacionParametrosException exception = new ValidacionParametrosException();
        String valorEje1 = request.getParameter(AWModificarFolio.FOLIO_EJE1);
        if ((null == valorEje1) || (valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el primer eje válido para la direccion");
        }

        String valorValor1 = request.getParameter(AWModificarFolio.FOLIO_VALOR1);
        if ((null == valorValor1) || (valorValor1.length() <= 0)) {
            exception.addError("Debe ingresar valor válido para el primer eje de la direccion");
        }

        String valorEje2 = request.getParameter(AWModificarFolio.FOLIO_EJE2);
        String valorValor2 = request.getParameter(AWModificarFolio.FOLIO_VALOR2);
        if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
            valorEje2 = new String();
        } else {
            if (valorValor2.length() <= 0) {
                exception.addError("Debe ingresar valor válido para el segundo eje  de la direccion");
            }
        }

        if (exception.getErrores().size() > 0) {
            if (parametro.equals(AWModificarFolio.EDITAR_DIRECCION_CORRECCION_ACEPTAR_TEMP)
                    || parametro.equals(AWModificarFolio.EDITAR_DIRECCION_CORRECCION_ACEPTAR_DEF)) {
                ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(exception.getErrores());
                throw ex1;
            }

            /*if (parametro.equals(AWModificarFolio.AGREGAR_DIRECCION_DIGITACION)) {
				ValidacionParametrosModificarFolioDigitacionException ex2 = new ValidacionParametrosModificarFolioDigitacionException(exception.getErrores());
				throw ex2;
			}

			if (parametro.equals(AWModificarFolio.AGREGAR_DIRECCION_ESPECIALIZADO)) {
				ValidacionParametrosModificarFolioEspecializadoException ex3 = new ValidacionParametrosModificarFolioEspecializadoException(exception.getErrores());
				throw ex3;
			}

			if (parametro.equals(AWModificarFolio.AGREGAR_DIRECCION_EDICION)) {
				ValidacionParametrosEditarFolioException ex4 = new ValidacionParametrosEditarFolioException(exception.getErrores());
				throw ex4;
			}*/
        }

        List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();

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
            exception.addError("La lista de los ejes no se encontro");
        }

        String complemento = request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
        String spec = null;
        if (direccion.getEje().getNombre() != null) {
            if (valorValor1 == null) {
                valorValor1 = "";
            }
            spec = direccion.getEje().getNombre().trim() + " " + valorValor1.trim();
            if ((null != direccion.getEje1())
                    && (null != direccion.getEje1().getNombre())) {
                spec += " " + direccion.getEje1().getNombre().trim();
//				if (valorValor2 != null) {
//					spec += " " + valorValor2;
//				}
            }
            if (valorValor2 != null) {
                spec += " " + valorValor2.trim();
            }
        }

        if (complemento != null && spec != null) {
            complemento = spec + " " + complemento;
        } else if (complemento == null && spec != null) {
            complemento = spec;
        }
        direccion.setEspecificacion(complemento != null ? complemento.toUpperCase() : null);

        ///FALTA ÑA INCLUSION EN LA ANOTACION TEMPORAL de aqui hacia abajo
        if (parametro.equals(AWModificarFolio.EDITAR_DIRECCION_CORRECCION_ACEPTAR_TEMP)) {
            List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
            if (direcciones == null) {
                direcciones = new Vector();
            }
            Integer iTem = (Integer) request.getSession().getAttribute(WebKeys.POSICION_EDIT_TEMP);
            Direccion dirTmp2 = new Direccion();
            if (iTem != null && direccion != null) {
                if (iTem.intValue() < direcciones.size()) {
                    final Iterator itDirecccion = direcciones.iterator();
                    int i = 0;
                    while (itDirecccion.hasNext()) {
                        Direccion dirTmp = (Direccion) itDirecccion.next();
                        if (dirTmp != null && !dirTmp.isToDelete()) {
                            if (i == iTem.intValue()) {
                                //dirTmp.setToDelete(true);
                                dirTmp.setEje(direccion.getEje());
                                dirTmp.setEje1(direccion.getEje1());
                                dirTmp.setValorEje(direccion.getValorEje());
                                dirTmp.setValorEje1(direccion.getValorEje1());
                                dirTmp.setEspecificacion(direccion.getEspecificacion());
                                if (dirTmp.getIdDireccion() != null) {
                                    dirTmp2.setIdDireccion(dirTmp.getIdDireccion());
                                    dirTmp2.setIdMatricula(dirTmp.getIdMatricula());
                                    dirTmp2.setEje(dirTmp.getEje());
                                    dirTmp2.setEje1(dirTmp.getEje1());
                                    dirTmp2.setValorEje(dirTmp.getValorEje());
                                    dirTmp2.setValorEje1(dirTmp.getValorEje1());
                                    dirTmp2.setEspecificacion(dirTmp.getEspecificacion());
                                    if (dirTmp.getOrden() == null || dirTmp.getOrden().equals("")) {
                                        dirTmp.setOrden(Integer.valueOf(dirTmp.getIdDireccion()));
                                    }
                                    dirTmp.setIdDireccion(null);
                                    dirTmp.setIdMatricula(null);

                                    dirTmp2.setToDelete(true);
                                    dirTmp2.setOrden(dirTmp.getOrden());
                                }
                                break;
                            }
                            i = i + 1;
                        }
                    }

                    if (dirTmp2.getIdDireccion() != null) {
                        direcciones.add(dirTmp2);
                        session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
                    }
                }
            }
            //session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
        } else {
            Integer iDef = (Integer) request.getSession().getAttribute(WebKeys.POSICION_EDIT_DEF);
            if (iDef != null && direccion != null) {
                Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
                if (t0_folio != null && t0_folio.getDirecciones() != null) {
                    List direcciones = t0_folio.getDirecciones();
                    Iterator iterDef = direcciones.iterator();
                    while (iterDef.hasNext()) {
                        Direccion dirAux = (Direccion) iterDef.next();
                        if (dirAux != null && iDef != null && !iDef.equals("")
                                && (String.valueOf(iDef)).equals(dirAux.getIdDireccion())) {
                            dirAux.setToDelete(true);

                            List direccionesTmp = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
                            if (direccionesTmp == null) {
                                direccionesTmp = new Vector();
                            }
                            direccionesTmp.add(direccion);
                            direccion.setOrden(Integer.valueOf(dirAux.getIdDireccion()));
                            session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direccionesTmp);
                        }

                    }

                    /*direccionDef.setEje(direccion.getEje()); 
				    direccionDef.setEje1(direccion.getEje1());
				    direccionDef.setValorEje(direccion.getValorEje());
				    direccionDef.setValorEje1(direccion.getValorEje1());
				    direccionDef.setEspecificacion(direccion.getEspecificacion());*/
                    //direcciones.set(iDef.intValue(), direccion);
                }
            }
        }
        eliminarInfoBasicaDireccionEditar();
        eliminarInfoBasicaDireccion();
        return null;
    }

    /**
     * Permite agregar un nuevo ciudadano a una anotación determinada.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio agregarCiudadanoAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaAnotacion(request);
        eliminarInfoBasicaCiudadano();

        ValidacionParametrosModificarAnotacionException exception = new ValidacionParametrosModificarAnotacionException();

        List ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        String tipoPersona = request.getParameter(AWModificarFolio.ANOTACION_MODALIDAD);
        String sexo = request.getParameter(AWModificarFolio.ANOTACION_SEXO_PERSONA);
        String tipoId = request.getParameter(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA);
        String numId = request.getParameter(AWModificarFolio.ANOTACION_NUM_ID_PERSONA);
        String apellido1 = request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA);
        String apellido2 = request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA);
        String nombres = request.getParameter(AWModificarFolio.ANOTACION_NOMBRES_PERSONA);

        if (tipoPersona == null || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo de persona para el ciudadano");
        }
        if (sexo == null || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un sexo para el ciudadano");
        }
        if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo de identificación para el ciudadano");
        } else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
            if (apellido1 == null || apellido1.trim().equals("")) {
                exception.addError("Debe ingresar el primer apellido del Ciudadano");
            }
        } else if (tipoId.equals(COPLookupCodes.NIT)) {
            if (nombres == null || nombres.trim().equals("")) {
                exception.addError("Debe ingresar el primer apellido del Ciudadano");
            }
        } else {
            double valorId = 0.0d;
            if (numId == null || numId.trim().equals("")) {
                exception.addError("Debe ingresar el número de identificacion del Ciudadano");
            } else {
                if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
                    try {
                        valorId = Double.parseDouble(numId);
                        if (valorId <= 0) {
                            exception.addError("El valor del documento no puede ser negativo o cero");
                        }
                    } catch (NumberFormatException e) {
                        exception.addError("El número de identificación de la persona en la anotación es inválido. No puede ser alfanumérico");
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

        String tipoIntervecion = request.getParameter(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION);
        if ((tipoIntervecion == null) || tipoIntervecion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe ingresar un tipo de intervencion para la persona en la anotación");
        }

        String participacion = request.getParameter(AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION);
        if ((participacion != null) && !participacion.equals("")) {
            if (participacion.length() > 50) {
                exception.addError("El campo participación de una persona no puede tener mas de 50 caracteres");
            }
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setApellido1(apellido1);
        ciudadano.setApellido2(apellido2);
        ciudadano.setNombre(nombres);
        ciudadano.setSexo(sexo);
        ciudadano.setDocumento(numId);
        ciudadano.setTipoDoc(tipoId);
        ciudadano.setTipoPersona(tipoPersona);

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        anotacionCiudadano.setCiudadano(ciudadano);
        anotacionCiudadano.setRolPersona(tipoIntervecion);
        anotacionCiudadano.setParticipacion(participacion);

        if (request.getParameter(AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION) == null) {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
        } else {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
        }

        if (sePuedeInsertar(anotacionCiudadano, ciudadanos.iterator())) {
            ciudadanos.add(anotacionCiudadano);
            session.setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        } else {
            exception.addError("El ciudadano que esta intentado ingresar ya se encuentra registrado en la anotación");
            throw exception;
        }

        return null;
    }

    /**
     * Permite guardar el cambio de los datos básciso de un ciudadano, como los
     * nombres y apellidos.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio editarCiudadano(HttpServletRequest request) throws AccionWebException {
        //preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        List ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        AnotacionCiudadano anotacionCiudadano = null;
        Ciudadano ciudadano = null;
        int aplicacionNumero = 0;

        try {
            aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
            anotacionCiudadano = (AnotacionCiudadano) ciudadanos.get(aplicacionNumero);
            ciudadano = anotacionCiudadano.getCiudadano();
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        ValidacionParametrosModificarAnotacionException exception = new ValidacionParametrosModificarAnotacionException();

        String tipoPersona = request.getParameter(AWModificarFolio.ANOTACION_MODALIDAD + aplicacionNumero);
        String sexo = request.getParameter(AWModificarFolio.ANOTACION_SEXO_PERSONA + aplicacionNumero);
        String tipoId = request.getParameter(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA + aplicacionNumero);
        String numId = request.getParameter(AWModificarFolio.ANOTACION_NUM_ID_PERSONA + aplicacionNumero);
        String apellido1 = request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA + aplicacionNumero);
        String apellido2 = request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA + aplicacionNumero);
        String nombres = request.getParameter(AWModificarFolio.ANOTACION_NOMBRES_PERSONA + aplicacionNumero);

        if (tipoPersona == null || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo de persona para el ciudadano");
        }
        if (sexo == null || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un sexo para el ciudadano");
        }
        if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un tipo de identificación para el ciudadano");
        } else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
            if (apellido1 == null || apellido1.trim().equals("")) {
                exception.addError("Debe ingresar el primer apellido del Ciudadano");
            }
        } else if (tipoId.equals(COPLookupCodes.NIT)) {
            if (nombres == null || nombres.trim().equals("")) {
                exception.addError("Debe ingresar el primer apellido del Ciudadano");
            }
        } else {
            double valorId = 0.0d;
            if (numId == null || numId.trim().equals("")) {
                exception.addError("Debe ingresar el número de identificacion del Ciudadano");
            } else {
                if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
                    try {
                        valorId = Double.parseDouble(numId);
                        if (valorId <= 0) {
                            exception.addError("El valor del documento no puede ser negativo o cero");
                        }
                    } catch (NumberFormatException e) {
                        exception.addError("El número de identificación de la persona en la anotación es inválido. No puede ser alfanumérico");
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

        String tipoIntervecion = request.getParameter(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION + aplicacionNumero);
        if ((tipoIntervecion == null) || tipoIntervecion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe ingresar un tipo de intervencion para la persona en la anotación");
        }

        String participacion = request.getParameter(AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION + aplicacionNumero);
        if ((participacion != null) && !participacion.equals("")) {
            if (participacion.length() > 50) {
                exception.addError("El campo participación de una persona no puede tener mas de 50 caracteres");
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        ciudadano.setApellido1(apellido1);
        ciudadano.setApellido2(apellido2);
        ciudadano.setNombre(nombres);
        ciudadano.setSexo(sexo);
        ciudadano.setDocumento(numId);
        ciudadano.setTipoDoc(tipoId);
        ciudadano.setTipoPersona(tipoPersona);

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        AnotacionCiudadano anotacionTemp = new AnotacionCiudadano();
        anotacionTemp.setCiudadano(ciudadano);
        anotacionTemp.setRolPersona(tipoIntervecion);
        anotacionTemp.setParticipacion(participacion);

        if (request.getParameter(AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION + aplicacionNumero) == null) {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
        } else {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
        }

        try {
            if (sePuedeEditar(anotacionTemp, aplicacionNumero, ciudadanos.iterator())) {
                anotacionCiudadano.setCiudadano(ciudadano);
                anotacionCiudadano.setRolPersona(tipoIntervecion);
                anotacionCiudadano.setParticipacion(participacion);
                ciudadanos.add(aplicacionNumero, anotacionCiudadano);
                ciudadanos.remove(aplicacionNumero + 1);
                session.setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
            } else {
                exception.addError("El ciudadano que esta intentado editar ya se encuentra registrado en la anotación");
                throw exception;
            }
        } catch (Exception e) {
            exception.addError("No se ha podido editar el ciudadano");
            throw exception;
        }

        return null;
    }

    /**
     * Permite eliminar un ciudadano de una anotación en la edición de una
     * anotación.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio eliminarCiudadano(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        List ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

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
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        session.setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);

        return null;
    }

    /**
     * Eliminar ciudadanos v2
     *
     *
     */
    private Evento doEliminarCiudadanoEdicion(HttpServletRequest request) throws AccionWebException {
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
     * Permite eliminar una dirección de un folio determinado.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio eliminarDireccion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
            //direcciones.remove(aplicacionNumero);
            final Iterator itDirecccion = direcciones.iterator();
            int i = 0;
            while (itDirecccion.hasNext()) {
                final Direccion direccion = (Direccion) itDirecccion.next();
                if (!direccion.isToDelete()) {
                    if (i == aplicacionNumero) {
                        direccion.setToDelete(true);
                        break;
                    }
                    i = i + 1;
                }
            }
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (direcciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
        /// se limpian todos los campos de la edicion		
        request.getSession().removeAttribute(AWModificarFolio.ELEMENTO_EDITAR);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_VALOR1);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_VALOR2);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_EJE1);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_EJE2);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_ESPECIFICACION_DIR);
        request.getSession().removeAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
        return null;
    }

    /**
     * Permite editar una dirección que no es existente de un folio.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio editarDireccion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
            //direcciones.remove(aplicacionNumero);

            if (request.getParameter(WebKeys.POSICION) != null && !request.getParameter(WebKeys.POSICION).equals("")) {
                request.getSession().setAttribute(WebKeys.POSICION_EDIT_TEMP, new Integer(aplicacionNumero));
            }

            final Iterator itDirecccion = direcciones.iterator();
            int i = 0;
            while (itDirecccion.hasNext()) {
                final Direccion direccion = (Direccion) itDirecccion.next();
                if (!direccion.isToDelete()) {
                    if (i == aplicacionNumero) {
                        request.getSession().setAttribute(this.ELEMENTO_EDITAR, direccion);
                        break;
                    }
                    i = i + 1;
                }
            }
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (direcciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);

        return null;
    }

    /**
     * Permite la eliminacion de una direccion que esta relacionada en la lista
     * de direcciones definitivas
     *
     */
    protected EvnFolio
            doFolioEdit_DireccionDefinitiva_DelItem(HttpServletRequest request)
            throws AccionWebException {

        // save state
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        // list to modify
        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        List direcciones = t0_folio.getDirecciones();

        // solo para que la iteracion no falle
        if (null == direcciones) {
            direcciones = new Vector();
        }

        // obtener id de item a eliminar;
        // se debe usar la misma forma de recorrido que direcciones helper
        // para que no falle la obtencion del item a eliminar.
        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));

            // se obtiene el item a eliminar:
            Iterator iterDef = direcciones.iterator();
            while (iterDef.hasNext()) {
                Direccion dirAux = (Direccion) iterDef.next();
                if (dirAux != null && (String.valueOf(aplicacionNumero)).equals(dirAux.getIdDireccion())) {
                    //request.getSession().setAttribute(this.ELEMENTO_EDITAR, dirAux);
                    dirAux.setToDelete(true);
                }

            }

            //Direccion element = (Direccion)direcciones.get( aplicacionNumero );
            /*if( null != element ) {
                    element.setToDelete( true );
                }*/
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (direcciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        return null;

    }

    /**
     * Permite la edicion de una direccion que esta relacionada en la lista de
     * direcciones definitivas
     *
     */
    protected EvnFolio doFolioEdit_DireccionDefinitiva_EditItem(HttpServletRequest request)
            throws AccionWebException {

        // save state
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        // list to modify
        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        List direcciones = t0_folio.getDirecciones();

        // solo para que la iteracion no falle
        if (null == direcciones) {
            direcciones = new Vector();
        }

        // obtener id de item a eliminar;
        // se debe usar la misma forma de recorrido que direcciones helper
        // para que no falle la obtencion del item a eliminar.
        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));

            // se obtiene el item a eliminar:
            if (request.getParameter(WebKeys.POSICION) != null && !request.getParameter(WebKeys.POSICION).equals("")) {
                request.getSession().setAttribute(WebKeys.POSICION_EDIT_DEF, new Integer(aplicacionNumero));
            }

            Iterator iterDef = direcciones.iterator();
            while (iterDef.hasNext()) {
                Direccion dirAux = (Direccion) iterDef.next();
                if (dirAux != null && (String.valueOf(aplicacionNumero)).equals(dirAux.getIdDireccion())) {
                    request.getSession().setAttribute(this.ELEMENTO_EDITAR, dirAux);
                }

            }
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (direcciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        return null;

    }

    /**
     * Permite eliminar un ciudadano de una anotación.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnFolio eliminarCiudadanoAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaAnotacion(request);

        List ciudadanos = (List) session.getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
            ciudadanos.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        session.setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);

        return null;
    }

    /**
     * Cuando una anotación esta en temporal puede ser borrada, este método
     * permite ejecutar esta labor.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnCorreccion eliminarAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfo(request);

        // Cambiado; uso de paginador
        List anotaciones = null;
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
            anotaciones = RPag.getAnotacionesActual();
        } else {
            anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        }

        if (anotaciones == null) {
            anotaciones = new Vector();
        }

        Anotacion anotacion = null;

        try {
            // cambiado; se maneja el id de la anotacion
            // int aplicacionNumero = Integer.parseInt( request.getParameter(WebKeys.POSICION) );
            // ya no es un numero dentro de una lista;
            // se recibe como parametro el
            // id de la anotacion
            String aplicacionNumero = request.getParameter(WebKeys.POSICION);

            for (Iterator iterator = anotaciones.iterator(); iterator.hasNext();) {
                Anotacion anotacionTempOnSearch = (Anotacion) iterator.next();
                String testAplicacionNumero = anotacionTempOnSearch.getIdAnotacion();
                if ((anotacionTempOnSearch.getIdAnotacion() != null)
                        && (testAplicacionNumero.equalsIgnoreCase(aplicacionNumero))) {

                    anotacion = anotacionTempOnSearch;
                    break;

                }

            }

            // raise; deberia encontrar una
            if (null == anotacion) {
                throw new AccionWebException("Anotacion No encontrada dentor de la lista de busqueda;");
            }

            // anotacion = (Anotacion) anotaciones.get(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (anotaciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            } else {
                throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
            }
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Folio folioOriginal = (Folio) session.getAttribute(WebKeys.FOLIO);
        Folio folio = new Folio();
        folio.setIdMatricula(folioOriginal.getIdMatricula());
        //folio.setZonaRegistral(folioOriginal.getZonaRegistral());
        anotacion.setToDelete(true);
        folio.addAnotacione(anotacion);

        return new EvnCorreccion(usuarioAuriga, (Usuario) session.getAttribute(WebKeys.USUARIO), folio, EvnCorreccion.EDITAR_FOLIO_ANOTACION);
    }

    /**
     * Método que determina si se puede insertar un nuevo ciudadano. Basicamente
     * permite el ingreso de un nuevo ciudadano cuando este no este en la
     * anotación, o si el mismo tiene un rol diferente es decir
     * compredor-vendedor. De - A . Etc.
     *
     * @param ciudadano
     * @param iterator
     * @return
     */
    private boolean sePuedeInsertar(AnotacionCiudadano antCiudadano, Iterator iterator) {
        Ciudadano ciudadano = antCiudadano.getCiudadano();
        AnotacionCiudadano antTemp = null;
        boolean resuelto = false;
        boolean respuesta = true;
        while (iterator.hasNext() && !resuelto) {
            antTemp = (AnotacionCiudadano) iterator.next();
            Ciudadano temp = antTemp.getCiudadano();

            if (temp.getTipoDoc().equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
                if (temp.getApellido1().equalsIgnoreCase(ciudadano.getApellido1()) && temp.getNombre().equalsIgnoreCase(ciudadano.getNombre()) && temp.getTipoDoc().equals(ciudadano.getTipoDoc()) && antTemp.getRolPersona().equalsIgnoreCase(antCiudadano.getRolPersona())) {
                    resuelto = true;
                    respuesta = false;
                }
            } else {
                if (temp.getDocumento().equals(ciudadano.getDocumento()) && temp.getTipoDoc().equals(ciudadano.getTipoDoc()) && (antTemp.getRolPersona().equalsIgnoreCase(antCiudadano.getRolPersona()))) {
                    resuelto = true;
                    respuesta = false;
                }
            }
        }
        return respuesta;
    }

    /**
     * Método que determina si se puede editar los datos de un ciudadano.
     *
     * @param ciudadano
     * @param iterator
     * @return
     */
    private boolean sePuedeEditar(AnotacionCiudadano antCiudadano, int posicion, Iterator iterator) {
        Ciudadano ciudadano = antCiudadano.getCiudadano();
        AnotacionCiudadano antTemp = null;
        boolean resuelto = false;
        boolean respuesta = true;
        int i = 0;
        while (iterator.hasNext() && !resuelto) {
            antTemp = (AnotacionCiudadano) iterator.next();
            Ciudadano temp = antTemp.getCiudadano();
            if (i != posicion) {
                if (temp.getTipoDoc().equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
                    if (temp.getApellido1().equalsIgnoreCase(ciudadano.getApellido1()) && temp.getNombre().equalsIgnoreCase(ciudadano.getNombre()) && temp.getTipoDoc().equals(ciudadano.getTipoDoc()) && antTemp.getRolPersona().equalsIgnoreCase(antCiudadano.getRolPersona())) {
                        resuelto = true;
                        respuesta = false;
                    }
                } else {
                    if (temp.getDocumento().equals(ciudadano.getDocumento()) && temp.getTipoDoc().equals(ciudadano.getTipoDoc()) && (antTemp.getRolPersona().equalsIgnoreCase(antCiudadano.getRolPersona()))) {
                        resuelto = true;
                        respuesta = false;
                    }
                }
            }
            i++;
        }
        return respuesta;
    }

    /**
     * Determina si una anotación determinada ya se encuentra en la lista que se
     * le pasa.
     *
     * @param anotacion
     * @param anotaciones
     * @return
     */
    private boolean existeEnListaAnotaciones(Anotacion anotacion, List anotaciones) {
        Iterator itAnotaciones = anotaciones.iterator();

        while (itAnotaciones.hasNext()) {
            Anotacion temp = (Anotacion) itAnotaciones.next();

            if (temp.getIdAnotacion().equals(anotacion.getIdAnotacion()) && temp.getIdMatricula().equals(anotacion.getIdMatricula())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Guarda en la sesión la información de un folio.
     */
    private void preservarInfoBasicaFolio(HttpServletRequest request) {
        //Se salva temporalmente la informacion sobre el folio. Esto no hace persistente el folio, ni cumple con el requerimiento de grabar de manera temporal
        if (request.getParameter(CFolio.ID_MATRICULA) != null) {
            session.setAttribute(CFolio.ID_MATRICULA, request.getParameter(CFolio.ID_MATRICULA));
        }

        if (request.getParameter(CDepartamento.ID_DEPARTAMENTO) != null) {
            session.setAttribute(CDepartamento.ID_DEPARTAMENTO, request.getParameter(CDepartamento.ID_DEPARTAMENTO));
        }

        if (request.getParameter(CDepartamento.NOMBRE_DEPARTAMENTO) != null) {
            session.setAttribute(CDepartamento.NOMBRE_DEPARTAMENTO, request.getParameter(CDepartamento.NOMBRE_DEPARTAMENTO));
        }

        if (request.getParameter(CMunicipio.ID_MUNICIPIO) != null) {
            session.setAttribute(CMunicipio.ID_MUNICIPIO, request.getParameter(CMunicipio.ID_MUNICIPIO));
        }

        if (request.getParameter(CMunicipio.NOMBRE_MUNICIPIO) != null) {
            session.setAttribute(CMunicipio.NOMBRE_MUNICIPIO, request.getParameter(CMunicipio.NOMBRE_MUNICIPIO));
        }

        if (request.getParameter(CVereda.ID_VEREDA) != null) {
            session.setAttribute(CVereda.ID_VEREDA, request.getParameter(CVereda.ID_VEREDA));
        }

        if (request.getParameter(CVereda.NOMBRE_VEREDA) != null) {
            session.setAttribute(CVereda.NOMBRE_VEREDA, request.getParameter(CVereda.NOMBRE_VEREDA));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_ESTADO_FOLIO) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_ESTADO_FOLIO, request.getParameter(AWModificarFolio.FOLIO_ESTADO_FOLIO));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_TIPO_PREDIO) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_TIPO_PREDIO, request.getParameter(AWModificarFolio.FOLIO_TIPO_PREDIO));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_NUPRE) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_NUPRE, request.getParameter(AWModificarFolio.FOLIO_NUPRE));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE, request.getParameter(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_AVALUO) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_AVALUO, request.getParameter(AWModificarFolio.FOLIO_AVALUO));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_FECHA_AVALUO) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_FECHA_AVALUO, request.getParameter(AWModificarFolio.FOLIO_FECHA_AVALUO));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_DESTINACION_ECONOMICA) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_DESTINACION_ECONOMICA, request.getParameter(AWModificarFolio.FOLIO_DESTINACION_ECONOMICA));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_PRIVMETROS) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_PRIVMETROS, request.getParameter(AWModificarFolio.FOLIO_PRIVMETROS));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_PRIVCENTIMETROS) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_PRIVCENTIMETROS, request.getParameter(AWModificarFolio.FOLIO_PRIVCENTIMETROS));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_CONSMETROS) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_CONSMETROS, request.getParameter(AWModificarFolio.FOLIO_CONSMETROS));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_CONSCENTIMETROS) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_CONSCENTIMETROS, request.getParameter(AWModificarFolio.FOLIO_CONSCENTIMETROS));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_COEFICIENTE) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_COEFICIENTE, request.getParameter(AWModificarFolio.FOLIO_COEFICIENTE));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_HECTAREAS) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_HECTAREAS, request.getParameter(AWModificarFolio.FOLIO_HECTAREAS));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_METROS) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_METROS, request.getParameter(AWModificarFolio.FOLIO_METROS));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_CENTIMETROS) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_CENTIMETROS, request.getParameter(AWModificarFolio.FOLIO_CENTIMETROS));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_LINDEROS_DEFINIDOS) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_LINDEROS_DEFINIDOS, request.getParameter(AWModificarFolio.FOLIO_LINDEROS_DEFINIDOS));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTACION) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_COMPLEMENTACION, request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTACION));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_LINDERO) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_LINDERO, request.getParameter(AWModificarFolio.FOLIO_LINDERO));
        }

        /*
                * @author      :   Henry Gómez Rocha
                * @change      :   Con el propósito de optimizar el rendimiento del aplicativo se comentarea
                *                  el siguiente if debido a que ya se encuentra en la línea 11856.
                * Caso Mantis  :   0004503
         */
//		if (request.getParameter(AWModificarFolio.FOLIO_TIPO_PREDIO) != null) {
//			session.setAttribute(AWModificarFolio.FOLIO_TIPO_PREDIO, request.getParameter(AWModificarFolio.FOLIO_TIPO_PREDIO));
//		}
        if (request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_COD_CATASTRAL, request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL));
        }
        if (request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT, request.getParameter(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_EJE1) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_EJE1, request.getParameter(AWModificarFolio.FOLIO_EJE1));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_EJE2) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_EJE2, request.getParameter(AWModificarFolio.FOLIO_EJE2));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_VALOR1) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_VALOR1, request.getParameter(AWModificarFolio.FOLIO_VALOR1));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_VALOR2) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_VALOR2, request.getParameter(AWModificarFolio.FOLIO_VALOR2));
        }

        if (request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTO_DIR) != null) {
            session.setAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR, request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTO_DIR));
        }

        if (request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO) != null) {
            session.setAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO, request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO));
        }

        if (request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION) != null) {
            session.setAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION, request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION));
        }

        //DATOS DEL DOCUMENTO
        if (request.getParameter(CDocumento.ID_DOCUMENTO) != null) {
            session.setAttribute(CDocumento.ID_DOCUMENTO, request.getParameter(CDocumento.ID_DOCUMENTO));
        }

        if (request.getParameter(CDocumento.ID_TIPO_DOCUMENTO) != null) {
            session.setAttribute(CDocumento.ID_TIPO_DOCUMENTO, request.getParameter(CDocumento.ID_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CDocumento.TIPO_DOCUMENTO) != null) {
            session.setAttribute(CDocumento.TIPO_DOCUMENTO, request.getParameter(CDocumento.TIPO_DOCUMENTO));
        }

        if (request.getParameter(CDocumento.NUM_DOCUMENTO) != null) {
            session.setAttribute(CDocumento.NUM_DOCUMENTO, request.getParameter(CDocumento.NUM_DOCUMENTO));
        }

        if (request.getParameter(CDocumento.FECHA_RADICACION) != null) {
            session.setAttribute(CDocumento.FECHA_RADICACION, request.getParameter(CDocumento.FECHA_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }
        /*
                *  @author Carlos Torres
                *  @chage   se agrega validacion de version diferente
                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL));
        }

        // informacion de ubicacion geografica ---------------------------------------------
        if (null != request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO)) {
            session.setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO));
        }
        if (null != request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO)) {
            session.setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO));
        }
        if (null != request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC)) {
            session.setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC));
        }
        if (null != request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC)) {
            session.setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC));
        }
        if (null != request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA)) {
            session.setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA));
        }
        if (null != request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA)) {
            session.setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA));
        }

        // ---------------------------------------------------------------------------------
        // bug: 0003580 --------------------------------------------------------------------
        if (null != request.getParameter(AWModificarFolio.FOLIO_NUM_RADICACION)) {
            session.setAttribute(AWModificarFolio.FOLIO_NUM_RADICACION, request.getParameter(AWModificarFolio.FOLIO_NUM_RADICACION));
        }
        if (null != request.getParameter(AWModificarFolio.FOLIO_FECHA_APERTURA)) {
            session.setAttribute(AWModificarFolio.FOLIO_FECHA_APERTURA, request.getParameter(AWModificarFolio.FOLIO_FECHA_APERTURA));
        }

        // ---------------------------------------------------------------------------------
    }

    /**
     * Carga en la sesión la información de un folio.
     */
    private void cargarInfoBasicaFolio(Folio folio) {
        //Se salva temporalmente la informacion sobre el folio. Esto no hace persistente el folio, ni cumple con el requerimiento de grabar de manera temporal
        eliminarInfoBasicaFolio();

        if ((folio != null) && (folio.getZonaRegistral() != null) && (folio.getZonaRegistral().getVereda() != null)) {
            Vereda vereda = folio.getZonaRegistral().getVereda();

            if ((vereda != null) && (vereda.getIdVereda() != null)) {
                session.setAttribute(CVereda.ID_VEREDA, vereda.getIdVereda());
            }

            if ((vereda != null) && (vereda.getNombre() != null)) {
                session.setAttribute(CVereda.NOMBRE_VEREDA, vereda.getNombre());
            }

            if (vereda.getMunicipio() != null) {
                Municipio municipio = vereda.getMunicipio();

                if ((municipio != null) && (municipio.getIdMunicipio() != null)) {
                    session.setAttribute(CMunicipio.ID_MUNICIPIO, municipio.getIdMunicipio());
                }

                if ((municipio != null) && (municipio.getNombre() != null)) {
                    session.setAttribute(CMunicipio.NOMBRE_MUNICIPIO, municipio.getNombre());
                }

                if (municipio.getDepartamento() != null) {
                    Departamento departamento = municipio.getDepartamento();

                    if ((departamento != null) && (departamento.getIdDepartamento() != null)) {
                        session.setAttribute(CDepartamento.ID_DEPARTAMENTO, departamento.getIdDepartamento());
                    }

                    if ((departamento != null) && (departamento.getNombre() != null)) {
                        session.setAttribute(CDepartamento.NOMBRE_DEPARTAMENTO, departamento.getNombre());
                    }
                }
            }
        }

        if ((folio != null) && (folio.getDocumento() != null)) {

            if (folio.getDocumento().getIdDocumento() != null) {
                session.setAttribute(CDocumento.ID_DOCUMENTO, folio.getDocumento().getIdDocumento());
            }

            if (folio.getDocumento().getTipoDocumento() != null) {
                session.setAttribute(CDocumento.ID_TIPO_DOCUMENTO, folio.getDocumento().getTipoDocumento().getIdTipoDocumento());
                session.setAttribute(CDocumento.TIPO_DOCUMENTO, folio.getDocumento().getTipoDocumento().getIdTipoDocumento());
            }

            if (folio.getDocumento().getNumero() != null) {
                session.setAttribute(CDocumento.NUM_DOCUMENTO, folio.getDocumento().getNumero());
            }

            if (folio.getDocumento().getFecha() != null) {
                Date date = folio.getDocumento().getFecha();
                String fechaApertura = "";
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                if (date != null) {
                    fechaApertura = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                }
                session.setAttribute(CDocumento.FECHA_RADICACION, fechaApertura);
            }

            if (folio.getDocumento().getOficinaOrigen() != null) {
                session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, folio.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
                session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, folio.getDocumento().getOficinaOrigen().getNombre());
                /*
                                    *  @author Carlos Torres
                                    *  @chage   se agrega validacion de version diferente
                                    *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, folio.getDocumento().getOficinaOrigen().getVersion().toString());
                if (folio.getDocumento().getOficinaOrigen().getTipoOficina() != null) {
                    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, folio.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
                }

            }

            if (folio.getDocumento().getOficinaOrigen() != null && folio.getDocumento().getOficinaOrigen().getVereda() != null) {

                Vereda vereda = folio.getDocumento().getOficinaOrigen().getVereda();

                if ((vereda != null) && (vereda.getIdVereda() != null)) {
                    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, vereda.getIdVereda());
                }

                if ((vereda != null) && (vereda.getNombre() != null)) {
                    session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, vereda.getNombre());
                }

                if (vereda.getMunicipio() != null) {
                    Municipio municipio = vereda.getMunicipio();

                    if ((municipio != null) && (municipio.getIdMunicipio() != null)) {
                        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, municipio.getIdMunicipio());
                    }

                    if ((municipio != null) && (municipio.getNombre() != null)) {
                        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, municipio.getNombre());
                    }

                    if (municipio.getDepartamento() != null) {
                        Departamento departamento = municipio.getDepartamento();

                        if ((departamento != null) && (departamento.getIdDepartamento() != null)) {
                            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, departamento.getIdDepartamento());
                        }

                        if ((departamento != null) && (departamento.getNombre() != null)) {
                            session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, departamento.getNombre());
                        }
                    }
                }
            }

            if (folio.getDocumento().getOficinaInternacional() != null) {
                String oficinaInternacional = folio.getDocumento().getOficinaInternacional();
                session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL, oficinaInternacional);
            }
        }

        if ((folio != null) && (folio.getIdMatricula() != null)) {
            session.setAttribute(CFolio.ID_MATRICULA, folio.getIdMatricula());
        }

        // if ((folio != null) && (folio.getFechaApertura() != null)) {
        // 	Date date = folio.getFechaApertura();
        // 	String fechaApertura = "";
        // 	Calendar calendar = Calendar.getInstance();
        // 	calendar.setTime(date);
        //
        // 	if (date != null) {
        // 		fechaApertura = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        // 	}
        //
        // 	session.setAttribute(AWModificarFolio.FOLIO_FECHA_APERTURA, fechaApertura);
        // }
        if ((folio != null) && (folio.getTipoPredio() != null) && (folio.getTipoPredio().getIdPredio() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_TIPO_PREDIO, folio.getTipoPredio().getIdPredio());
        }

        if ((folio != null) && (folio.getComplementacion() != null) && (folio.getComplementacion().getComplementacion() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_COMPLEMENTACION, folio.getComplementacion().getComplementacion());
        }

        if ((folio != null) && (folio.getLindero() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_LINDERO, folio.getLindero());
        }

        if ((folio != null) && (folio.getComentario() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_COMENTARIO, folio.getComentario());
        }

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        if ((folio != null) && (folio.getCodCatastral() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_COD_CATASTRAL, folio.getCodCatastral());
        }
        if ((folio != null) && (folio.getCodCatastralAnterior() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT, folio.getCodCatastralAnterior());
        }

        if ((folio != null) && (folio.getNupre() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_NUPRE, folio.getNupre());
        }

        if ((folio != null) && (folio.getDeterminaInm() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE, folio.getDeterminaInm());
        }

        if ((folio != null) && (folio.getPrivMetros() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_PRIVMETROS, folio.getPrivMetros());
        }

        if ((folio != null) && (folio.getPrivCentimetros() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_PRIVCENTIMETROS, folio.getPrivCentimetros());
        }

        if ((folio != null) && (folio.getConsMetros() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_CONSMETROS, folio.getConsMetros());
        }

        if ((folio != null) && (folio.getConsCentimetros() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_CONSCENTIMETROS, folio.getConsCentimetros());
        }

        if ((folio != null) && (folio.getCoeficiente() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_COEFICIENTE, folio.getCoeficiente());
        }

        if ((folio != null) && (folio.getHectareas() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_HECTAREAS, folio.getHectareas());
        }

        if ((folio != null) && (folio.getMetros() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_METROS, folio.getMetros());
        }

        if ((folio != null) && (folio.getCentimetros() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_CENTIMETROS, folio.getCentimetros());
        }

        if ((folio != null) && (folio.getTipoPredio() != null) && (folio.getTipoPredio().getIdPredio() != null)) {
            session.setAttribute(AWModificarFolio.FOLIO_TIPO_PREDIO, folio.getTipoPredio().getIdPredio());
        }

        if ((folio != null) && (folio.getDirecciones() != null)) {
            List direcciones = folio.getDirecciones();

            if (direcciones.size() > 0) {
                Direccion direccion = (Direccion) direcciones.get(direcciones.size() - 1);

                if ((direccion != null) && (direccion.getEje() != null)) {
                    Eje eje = direccion.getEje();

                    if (eje.getIdEje() != null) {
                        session.setAttribute(AWModificarFolio.FOLIO_EJE1, eje.getIdEje());
                    }
                }

                if ((direccion != null) && (direccion.getEje1() != null)) {
                    Eje eje1 = direccion.getEje1();

                    if (eje1.getIdEje() != null) {
                        session.setAttribute(AWModificarFolio.FOLIO_EJE2, eje1.getIdEje());
                    }
                }

                if ((direccion != null) && (direccion.getValorEje() != null)) {
                    session.setAttribute(AWModificarFolio.FOLIO_VALOR1, direccion.getValorEje());
                }

                if ((direccion != null) && (direccion.getValorEje1() != null)) {
                    session.setAttribute(AWModificarFolio.FOLIO_VALOR2, direccion.getValorEje1());
                }

                if ((direccion != null) && (direccion.getEspecificacion() != null)) {
                    session.setAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR, direccion.getEspecificacion());
                }
            }
        }

        if ((folio != null) && (folio.getEstado() != null)) {
            EstadoFolio estadoFolio = folio.getEstado();

            if ((estadoFolio != null) && (estadoFolio.getIdEstado() != null)) {
                session.setAttribute(AWModificarFolio.FOLIO_ESTADO_FOLIO, estadoFolio.getIdEstado());
            }
        }

        // cargar a sesion informacion de ubicacion geografica ------------------------------
        // jxpath para cargar atributos
        chain:
        {

            JXPathContext sourceContext = JXPathContext.newContext(folio);
            sourceContext.setFactory(new FolioAbstractFactory());

            String parameter_id;
            String path;
            Object value;

            // folio.ubicacion-geografica - vereda - id
            parameter_id = CFolio.FOLIO_LOCACION_ID_VEREDA;
            path = "zonaRegistral/vereda/idVereda";
            value = BasicJxPathUtils.getProperty(sourceContext, path);

            if (null != value) {
                session.setAttribute(parameter_id, value);
            }

            // folio.ubicacion-geografica - vereda - nombre
            parameter_id = CFolio.FOLIO_LOCACION_NOM_VEREDA;
            path = "zonaRegistral/vereda/nombre";
            value = BasicJxPathUtils.getProperty(sourceContext, path);

            if (null != value) {
                session.setAttribute(parameter_id, value);
            }

            // folio.ubicacion-geografica - municipio - id
            parameter_id = CFolio.FOLIO_LOCACION_ID_MUNIC;
            path = "zonaRegistral/vereda/municipio/idMunicipio";
            value = BasicJxPathUtils.getProperty(sourceContext, path);

            if (null != value) {
                session.setAttribute(parameter_id, value);
            }

            // folio.ubicacion-geografica - municipio - nombre
            parameter_id = CFolio.FOLIO_LOCACION_NOM_MUNIC;
            path = "zonaRegistral/vereda/municipio/nombre";
            value = BasicJxPathUtils.getProperty(sourceContext, path);

            if (null != value) {
                session.setAttribute(parameter_id, value);
            }

            // folio.ubicacion-geografica - departamento id
            parameter_id = CFolio.FOLIO_LOCACION_ID_DEPTO;
            path = "zonaRegistral/vereda/municipio/departamento/idDepartamento";
            value = BasicJxPathUtils.getProperty(sourceContext, path);

            if (null != value) {
                session.setAttribute(parameter_id, value);
            }

            // folio.ubicacion-geografica - departamento nombre
            parameter_id = CFolio.FOLIO_LOCACION_NOM_DEPTO;
            path = "zonaRegistral/vereda/municipio/departamento/nombre";
            value = BasicJxPathUtils.getProperty(sourceContext, path);

            if (null != value) {
                session.setAttribute(parameter_id, value);
            }

            // bug: 3580 ###############################################
            // folio.fechaApertura (ya se esta cargando)
            parameter_id = AWModificarFolio.FOLIO_FECHA_APERTURA;
            path = "fechaApertura";
            value = BasicJxPathUtils.getProperty(sourceContext, path);

            if (null != value) {
                String local_Value;

                // apply convertor date2string
                local_Value = BasicConvertorActions.toString((Date) value, "dd/MM/yyyy");

                // set value
                session.setAttribute(parameter_id, local_Value);
            }

            // CFolio.FOLIO_NUM_RADICACION
            parameter_id = AWModificarFolio.FOLIO_NUM_RADICACION;
            path = "radicacion";
            value = BasicJxPathUtils.getProperty(sourceContext, path);

            if (null != value) {
                session.setAttribute(parameter_id, value);
            }

            // #########################################################
        } // : chain

    }

    /**
     * Carga en la sesión la información de una anotación.
     */
    private void preservarInfoBasicaAnotacion(HttpServletRequest request) {

        if (request.getParameter(AWModificarFolio.ANOTACION_ORDEN) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_ORDEN, request.getParameter(AWModificarFolio.ANOTACION_ORDEN));
        }

        if (request.getParameter(AWModificarFolio.POSICIONANOTACION) != null) {
            session.setAttribute(AWModificarFolio.POSICIONANOTACION, request.getParameter(AWModificarFolio.POSICIONANOTACION));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO, request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO));
        }
        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_MODALIDAD) != null) {
            request.getSession().setAttribute(AWModificarFolio.ANOTACION_MODALIDAD, request.getParameter(AWModificarFolio.ANOTACION_MODALIDAD));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }
        /*
                *  @author Carlos Torres
                *  @chage   se agrega validacion de version diferente
                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }
        if (request.getParameter(AWModificarFolio.ANOTACION_ID_ANOTACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_ID_ANOTACION, request.getParameter(AWModificarFolio.ANOTACION_ID_ANOTACION));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION, request.getParameter(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO, request.getParameter(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_FECHA_RADICACION, request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION));
        }
        if (request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_NUM_RADICACION, request.getParameter(AWModificarFolio.ANOTACION_NUM_RADICACION));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION, request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_MODALIDAD) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_MODALIDAD, request.getParameter(CFolio.ANOTACION_MODALIDAD));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO, request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION, request.getParameter(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_NUM_DOCUMENTO) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_NUM_DOCUMENTO, request.getParameter(AWModificarFolio.ANOTACION_NUM_DOCUMENTO));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            session.setAttribute("tipo_oficina", request.getParameter("tipo_oficina"));
            session.setAttribute("tipo_nom_oficina", request.getParameter("tipo_nom_oficina"));
            session.setAttribute("numero_oficina", request.getParameter("numero_oficina"));
            session.setAttribute("id_oficina", request.getParameter("id_oficina"));
            /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            session.setAttribute("version", request.getParameter("version"));
        }

        if (request.getParameter(WebKeys.TIPO_OFICINA_I_N) != null) {
            session.setAttribute(WebKeys.TIPO_OFICINA_I_N, request.getParameter(WebKeys.TIPO_OFICINA_I_N));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL, request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
         * @Descripcion: Se agrega la variable versionNatJuridica para optener
         * la version de la naturalesa juridica seleccionada
         */
        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER) != null) {
            session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER));
        }

        // if (request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION) != null) {
        //	session.setAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION, request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION));
        //}
        if (request.getParameter(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION) != null) {
            session.setAttribute(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION, request.getParameter(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION));
        }
        /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   Se preserva la información de la salvedad de cancelacion en correciones
                 * Caso Mantis  :   04131
         */
        if (request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION) != null) {
            session.setAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION, request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION));
        }
        if (request.getParameter(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID) != null) {
            session.setAttribute(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID, request.getParameter(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID));
        }
        String param_AnotacionSalvedadX_Id = request.getParameter(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID);

        if (request.getParameter(AWModificarFolio.ANOTACION_ESTADO) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_ESTADO, request.getParameter(AWModificarFolio.ANOTACION_ESTADO));
        }
        if (request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION) != null) {
            session.setAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION, request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION));
        }

        String idCanc;
        if ((idCanc = request.getParameter("ESCOGER_ANOTACION_CANCELACION")) != null) {
            session.setAttribute("ESCOGER_ANOTACION_CANCELACION", idCanc);
        }

        do_FolioEdit_AnotacionXSalvedad_SaveState(request);
    }

    /**
     * Colocar los datos de una anotacion, modo antiguo en correcciones
     */
    private void
            doCorreccionEditarAnotacion_BindData2Form_Mode2(Anotacion anotacion, HttpServletRequest local_Request) {
        cargarInfoBasicaAnotacion(anotacion, local_Request);
    }

    /**
     * Cargar informacion de anotacion en sesion, modificada de calificacion
     */
    public static void
            doCargarInfoBasicaAnotacion_Documento_Mode2(Anotacion anotacion, HttpServletRequest request) {
        HttpSession session = request.getSession();

        // obtener el documento
        gov.sir.core.negocio.modelo.Documento thisDocumento = anotacion.getDocumento();

        // -------------------------
        // Collect params:
        String paramValue_Documento_TipoDocumento_Nombre = "";
        String paramValue_Documento_Numero = "";
        java.util.Date paramValue_Documento_Fecha = new java.util.Date();
        String paramValue_Documento_OficinaOrigen_TipoOficina_Nombre = "";
        String paramValue_Documento_OficinaOrigen_Nombre = "";
        String paramValue_Documento_OficinaOrigen_Numero = "";
        String paramValue_Documento_OficinaOrigen_Id = "";
        String paramValue_Documento_OficinaOrigen_Vereda_Municipio_Nombre = "";
        String paramValue_Documento_OficinaOrigen_Vereda_Municipio_Depto_Nombre = "";

        // -------------------------
        // chain object
        Object currentObject = null;

        // start-of validation-chain
        currentObject = thisDocumento;

        if (null != currentObject) {
            gov.sir.core.negocio.modelo.Documento currentObj_Documento = (gov.sir.core.negocio.modelo.Documento) currentObject;
            // datos de documento

            // 1. nombre tipo documento
            paramValue_Documento_TipoDocumento_Nombre = "" + ((null == currentObj_Documento.getTipoDocumento()) ? ("") : (currentObj_Documento.getTipoDocumento().getNombre()));

            // 2. numero de documento
            paramValue_Documento_Numero = ((null == currentObj_Documento.getNumero()) ? ("") : (currentObj_Documento.getNumero()));

            // 3. fecha
            paramValue_Documento_Fecha = ((null == thisDocumento.getFecha()) ? (new java.util.Date()) : (thisDocumento.getFecha()));

            // next: documento.oficinaOrigen
            currentObject = currentObj_Documento.getOficinaOrigen();
        }

        if (null != currentObject) {
            gov.sir.core.negocio.modelo.OficinaOrigen currentObj_OficinaOrigen = (gov.sir.core.negocio.modelo.OficinaOrigen) currentObject;
            // datos de oficina origen

            // 4. documento.oficinaOrigen.tipoOficina.nombre
            paramValue_Documento_OficinaOrigen_TipoOficina_Nombre = "" + ((null == currentObj_OficinaOrigen.getTipoOficina()) ? ("") : (currentObj_OficinaOrigen.getTipoOficina().getNombre()));

            // 5.1 documento.oficinaOrigen.numero
            paramValue_Documento_OficinaOrigen_Numero = "" + ((null == currentObj_OficinaOrigen.getNumero()) ? ("") : (currentObj_OficinaOrigen.getNumero()));

            // 5.2 documento.oficinaOrigen.nombre
            paramValue_Documento_OficinaOrigen_Nombre = "" + ((null == currentObj_OficinaOrigen.getNombre()) ? ("") : (currentObj_OficinaOrigen.getNombre()));

            // 6.documento.oficinaOrigen.id
            paramValue_Documento_OficinaOrigen_Id = "" + ((null == currentObj_OficinaOrigen.getIdOficinaOrigen()) ? ("") : (currentObj_OficinaOrigen.getIdOficinaOrigen()));

            // next: documento.oficinaOrigen.vereda
            currentObject = currentObj_OficinaOrigen.getVereda();
        }

        if (null != currentObject) {
            gov.sir.core.negocio.modelo.Vereda currentObj_Vereda = (gov.sir.core.negocio.modelo.Vereda) currentObject;
            // datos de vereda

            // next: documento.oficinaOrigen.vereda.municipio
            currentObject = currentObj_Vereda.getMunicipio();
        }

        if (null != currentObject) {
            gov.sir.core.negocio.modelo.Municipio currentObj_Municipio = (gov.sir.core.negocio.modelo.Municipio) currentObject;
            // datos de municipio

            // 1. nombre municipio
            paramValue_Documento_OficinaOrigen_Vereda_Municipio_Nombre = ((null == currentObj_Municipio.getNombre()) ? ("") : (currentObj_Municipio.getNombre()));

            // next: documento.oficinaOrigen.vereda
            currentObject = currentObj_Municipio.getDepartamento();
        }

        if (null != currentObject) {
            gov.sir.core.negocio.modelo.Departamento currentObj_Departamento = (gov.sir.core.negocio.modelo.Departamento) currentObject;
            // datos de departamento

            // 1. nombre departamento
            paramValue_Documento_OficinaOrigen_Vereda_Municipio_Depto_Nombre = ((null == currentObj_Departamento.getNombre()) ? ("") : (currentObj_Departamento.getNombre()));

            // next: end-chain
            currentObject = null;
        }

        // draw the collected data
        session.setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO, paramValue_Documento_TipoDocumento_Nombre);
        session.setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO, paramValue_Documento_Numero);
        session.setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO, paramValue_Documento_Fecha);
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, paramValue_Documento_OficinaOrigen_TipoOficina_Nombre);
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM, paramValue_Documento_OficinaOrigen_Nombre);
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, paramValue_Documento_OficinaOrigen_Numero);
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_COD, paramValue_Documento_OficinaOrigen_Id);
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, paramValue_Documento_OficinaOrigen_Vereda_Municipio_Nombre);
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, paramValue_Documento_OficinaOrigen_Vereda_Municipio_Depto_Nombre);

        // --------------------------------------------------
        // request.getSession().setAttribute( CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,thisDocumento.getOficinaOrigen().getTipoOficina().getNombre());
        // request.getSession().setAttribute( CFolio.ANOTACION_TIPO_DOCUMENTO,thisDocumento.getTipoDocumento().getNombre());
        // request.getSession().setAttribute( CFolio.ANOTACION_NUM_DOCUMENTO,thisDocumento.getNumero());
        // request.getSession().setAttribute( CFolio.ANOTACION_FECHA_DOCUMENTO,thisDocumento.getFecha());
        // request.getSession().setAttribute( CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,thisDocumento.getOficinaOrigen().getVereda().getMunicipio().getNombre());
        // request.getSession().setAttribute( CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,thisDocumento.getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());
        // request.getSession().setAttribute( CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,thisDocumento.getOficinaOrigen().getNumero());
        // request.getSession().setAttribute( CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM,thisDocumento.getOficinaOrigen().getNombre());
        // request.getSession().setAttribute( CFolio.ANOTACION_OFICINA_DOCUMENTO_COD,thisDocumento.getOficinaOrigen().getIdOficinaOrigen());
        //------------------------------------------------------------------------
    }

    /**
     * Cargar informacion de anotacion-documento en sesion, modo anterior
     */
    private void
            doCargarInfoBasicaAnotacion_Documento_Mode1(Anotacion anotacion) {
        if (anotacion.getDocumento() != null) {
            Documento documento = anotacion.getDocumento();

            if (documento != null) {
                if (documento.getOficinaOrigen() != null) {
                    OficinaOrigen oficinaOrigen = documento.getOficinaOrigen();

                    if (oficinaOrigen.getVereda() != null) {
                        Vereda vereda = oficinaOrigen.getVereda();

                        if ((vereda != null) && (vereda.getIdVereda() != null)) {
                            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, vereda.getIdVereda());
                        }

                        if ((vereda != null) && (vereda.getNombre() != null)) {
                            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, vereda.getNombre());
                        }

                        if (vereda.getIdMunicipio() != null) {
                            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, vereda.getIdMunicipio());
                        }

                        if (vereda.getIdDepartamento() != null) {
                            session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, vereda.getIdDepartamento());
                        }

                        if (vereda.getMunicipio() != null) {
                            Municipio municipio = vereda.getMunicipio();

                            if ((municipio != null) && (municipio.getIdMunicipio() != null)) {
                                session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, municipio.getIdMunicipio());
                            }

                            if ((municipio != null) && (municipio.getNombre() != null)) {
                                session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, municipio.getNombre());
                            }

                            if (municipio.getDepartamento() != null) {
                                Departamento departamento = municipio.getDepartamento();

                                if ((departamento != null) && (departamento.getIdDepartamento() != null)) {
                                    session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, departamento.getIdDepartamento());
                                }

                                if ((departamento != null) && (departamento.getNombre() != null)) {
                                    session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, departamento.getNombre());
                                }
                            }
                        }
                    }

                    if (oficinaOrigen.getNumero() != null) {
                        session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, oficinaOrigen.getNumero());
                    }

                    if (oficinaOrigen.getIdOficinaOrigen() != null) {
                        session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, oficinaOrigen.getIdOficinaOrigen());
                        /*
                                                            *  @author Carlos Torres
                                                            *  @chage   se agrega validacion de version diferente
                                                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                         */
                        session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, oficinaOrigen.getVersion().toString());
                    }

                    if (oficinaOrigen.getNombre() != null) {
                        session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, oficinaOrigen.getNombre());
                    }

                }

                if (documento.getOficinaInternacional() != null) {
                    session.setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL, documento.getOficinaInternacional());
                }

                // added, anotacion documento-comentario
                if (documento.getComentario() != null) {
                    session.setAttribute("CFolio.ANOTACION_DOCUMENTO_COMENTARIO", documento.getComentario());
                }

                if (documento.getFecha() != null) {
                    Date date = documento.getFecha();
                    String fechaDoc = "";

                    if (date != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        fechaDoc = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                    }

                    session.setAttribute(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO, fechaDoc);
                }

                session.setAttribute(AWModificarFolio.ANOTACION_NUM_DOCUMENTO, "" + documento.getNumero());

                if (documento.getTipoDocumento() != null) {
                    TipoDocumento tipoDoc = documento.getTipoDocumento();

                    if ((tipoDoc != null) && (tipoDoc.getIdTipoDocumento() != null)) {
                        session.setAttribute(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO, tipoDoc.getIdTipoDocumento());
                    }
                }

            }
        }
    } // end-method

    /**
     * Guarda en la sesión la información de una anotación.
     */
    private void cargarInfoBasicaAnotacion(Anotacion anotacion, HttpServletRequest local_Request) {
        eliminarInfoBasicaAnotacion(local_Request);

        if (anotacion != null) {
            if (anotacion.getIdMatricula() != null) {
                session.setAttribute(CFolio.ID_MATRICULA, anotacion.getIdMatricula());
            }

            if (anotacion.getOrden() != null) {

                session.setAttribute(AWModificarFolio.ANOTACION_ORDEN, anotacion.getOrden());
            }

            if (anotacion.getIdAnotacion() != null) {
                session.setAttribute(AWModificarFolio.ANOTACION_ID_ANOTACION, anotacion.getIdAnotacion());
            }

            if (anotacion.getEstado() != null) {
                EstadoAnotacion estado = anotacion.getEstado();
                if ((estado != null) && (estado.getIdEstadoAn() != null)) {
                    session.setAttribute(AWModificarFolio.ANOTACION_ESTADO, estado.getIdEstadoAn());
                }
                session.setAttribute(AWModificarFolio.ANOTACION_ID_ANOTACION, anotacion.getIdAnotacion());
            }

            if (anotacion.getFechaRadicacion() != null) {
                Date date = anotacion.getFechaRadicacion();
                String fechaRad = "";

                if (date != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    fechaRad = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                }

                session.setAttribute(AWModificarFolio.ANOTACION_FECHA_RADICACION, fechaRad);
            }

            if (null != anotacion.getNumRadicacion()) {
                String anotacion_NumRadicacion = anotacion.getNumRadicacion();
                session.setAttribute(AWModificarFolio.ANOTACION_NUM_RADICACION, anotacion_NumRadicacion);
            }

            doCargarInfoBasicaAnotacion_Documento_Mode1(anotacion);

            session.setAttribute(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION, "" + new BigDecimal(anotacion.getValor()).toString());

            if (anotacion.getNaturalezaJuridica() != null) {
                NaturalezaJuridica natJuridica = anotacion.getNaturalezaJuridica();

                if ((natJuridica != null) && (natJuridica.getIdNaturalezaJuridica() != null)) {
                    session.setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, natJuridica.getIdNaturalezaJuridica());
                }

                if ((natJuridica != null) && (natJuridica.getNombre() != null)) {
                    session.setAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, natJuridica.getNombre());
                }
                /**
                 * @Autor: Carlos Torres
                 * @Mantis: 0012705
                 * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
                 * @Descripcion: Se agrega la variable versionNatJuridica para
                 * optener la version de la naturalesa juridica seleccionada
                 */
                if ((natJuridica != null) && (natJuridica.getVersion() != 0)) {
                    session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, natJuridica.getVersion());
                }

            }

            if (anotacion.getComentario() != null) {
                session.setAttribute(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION, anotacion.getComentario());
            }

            Documento documentoAnotacion = anotacion.getDocumento();
            if (documentoAnotacion != null) {
                String[] paramsFecha = CNaturalezaJuridica.FECHA_NUEVAS_NATURALEZAS.split("/");
                Calendar cal = Calendar.getInstance();
                if (paramsFecha.length == 3) {
                    cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(paramsFecha[0]));
                    cal.set(Calendar.MONTH, Integer.parseInt(paramsFecha[1]) - 1);
                    cal.set(Calendar.YEAR, Integer.parseInt(paramsFecha[2]));
                } else {
                    cal.set(Calendar.DAY_OF_MONTH, 15);
                    cal.set(Calendar.MONTH, 07);
                    cal.set(Calendar.YEAR, 2001);
                }

                Date fechaComparacion = cal.getTime();
                boolean mostrarAntiguas = true;
                boolean mostrarNuevas = true;
                if (documentoAnotacion.getFecha() != null) {
                    if (documentoAnotacion.getFecha().before(fechaComparacion)) {
                        NaturalezaJuridica nat = anotacion.getNaturalezaJuridica();
                        if (nat != null
                                && (nat.getIdNaturalezaJuridica().length() != 4
                                && (!nat.getIdNaturalezaJuridica().equals("915")
                                && !nat.getIdNaturalezaJuridica().equals("999")))) {
                            mostrarNuevas = false;
                        }
                    } else {
                        mostrarAntiguas = false;
                    }
                }
            }

            // load the cancelation id
            // anotacion.getAnotacionesCancelacions()
            addedBlock:
            {
                // una anotacion canceladora.
                List cancelationsList = anotacion.getAnotacionesCancelacions();
                if ((null != cancelationsList)
                        && (cancelationsList.size() > 0)) {

                    Cancelacion cancelacion;
                    cancelacion = (Cancelacion) cancelationsList.get(0);

                    String param_IdCancelacion = cancelacion.getCancelada().getIdAnotacion(); //.getOrden()

                    //session.setAttribute( "ESCOGER_ANOTACION_CANCELACION", param_IdCancelacion );
                }
            } //:addedBlock
            // load salvedad if applies

            // ------------------------------------------------------
            // bug 3530/3560
            // cargar datos de salvedad-anotacionx para editarla
            // condiciones: se asume la ultima salvedad procesada por el usuario
            // process: get-data
            Anotacion local_Anotacion;
            List local_Salvedades; // List< SalvedadAnotacion >

            local_Anotacion = anotacion;
            local_Salvedades = local_Anotacion.getSalvedades();

            if (null == local_Salvedades) {
                local_Salvedades = new ArrayList();
            }

            // obtener la primera Salvedad, fijar los atributos respectivos
            Iterator local_SalvedadesIterator;
            local_SalvedadesIterator = local_Salvedades.iterator();
            SalvedadAnotacion local_SalvedadesElement = null;
            for (; local_SalvedadesIterator.hasNext();) {
                local_SalvedadesElement = (SalvedadAnotacion) local_SalvedadesIterator.next();
                // break;
            } // for

//                      se valida que sea del turno
            String radSal = "";
            try {
                Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
                if (local_SalvedadesElement != null) {
                    radSal = local_SalvedadesElement.getNumRadicacion();
                }
                if (radSal == null || !radSal.equals(turno.getIdWorkflow())) {
                    local_SalvedadesElement = null;
                }
            } catch (Exception ee) {
            }

            if (null != local_SalvedadesElement) {
                session.setAttribute(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION, local_SalvedadesElement.getDescripcion());
                session.setAttribute(ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID, local_SalvedadesElement.getIdSalvedad());
            } // if
            // ------------------------------------------------------

        } // if

    } // end-method

    /**
     * Guarda en la sesión la información de un ciudadano.
     */
    private void preservarInfoBasicaCiudadano(HttpServletRequest request) {
        if (request.getParameter(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA, request.getParameter(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_TIPO_PERSONA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_TIPO_PERSONA, request.getParameter(AWModificarFolio.ANOTACION_TIPO_PERSONA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_COD_VERIFICACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_COD_VERIFICACION, request.getParameter(AWModificarFolio.ANOTACION_COD_VERIFICACION));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_SEXO_PERSONA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_SEXO_PERSONA, request.getParameter(AWModificarFolio.ANOTACION_SEXO_PERSONA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_NUM_ID_PERSONA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_NUM_ID_PERSONA, request.getParameter(AWModificarFolio.ANOTACION_NUM_ID_PERSONA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA, request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA, request.getParameter(AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_NOMBRES_PERSONA) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_NOMBRES_PERSONA, request.getParameter(AWModificarFolio.ANOTACION_NOMBRES_PERSONA));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION, request.getParameter(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION, request.getParameter(AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION));
        }

        if (request.getParameter(AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION) != null) {
            session.setAttribute(AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION, request.getParameter(AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION));
        }
    }

    /**
     * Elimina de la sesión la información de un folio.
     */
    private void eliminarInfoBasicaFolio() {
        session.removeAttribute(CFolio.ID_MATRICULA);
        session.removeAttribute(AWModificarFolio.FOLIO_COD_CATASTRAL);
        session.removeAttribute(AWModificarFolio.FOLIO_COD_CATASTRAL_ANT);
        session.removeAttribute(AWModificarFolio.FOLIO_LOCACION_ID_DEPTO);
        session.removeAttribute(AWModificarFolio.FOLIO_LOCACION_NOM_DEPTO);
        session.removeAttribute(AWModificarFolio.FOLIO_LOCACION_ID_MUNIC);
        session.removeAttribute(AWModificarFolio.FOLIO_LOCACION_NOM_MUNIC);
        session.removeAttribute(AWModificarFolio.FOLIO_LOCACION_ID_VEREDA);
        session.removeAttribute(AWModificarFolio.FOLIO_LOCACION_NOM_VEREDA);
        session.removeAttribute(AWModificarFolio.FOLIO_TIPO_PREDIO);
        session.removeAttribute(AWModificarFolio.FOLIO_NUPRE);
        session.removeAttribute(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE);
        session.removeAttribute(AWModificarFolio.FOLIO_AVALUO);
        session.removeAttribute(AWModificarFolio.FOLIO_FECHA_AVALUO);
        session.removeAttribute(AWModificarFolio.FOLIO_DESTINACION_ECONOMICA);
        session.removeAttribute(AWModificarFolio.FOLIO_PRIVMETROS);
        session.removeAttribute(AWModificarFolio.FOLIO_PRIVCENTIMETROS);
        session.removeAttribute(AWModificarFolio.FOLIO_CONSMETROS);
        session.removeAttribute(AWModificarFolio.FOLIO_CONSCENTIMETROS);
        session.removeAttribute(AWModificarFolio.FOLIO_COEFICIENTE);
        session.removeAttribute(AWModificarFolio.FOLIO_HECTAREAS);
        session.removeAttribute(AWModificarFolio.FOLIO_METROS);
        session.removeAttribute(AWModificarFolio.FOLIO_CENTIMETROS);
        session.removeAttribute(AWModificarFolio.FOLIO_LINDEROS_DEFINIDOS);
        session.removeAttribute(AWModificarFolio.FOLIO_COMPLEMENTACION);
        session.removeAttribute(AWModificarFolio.FOLIO_LINDERO);
        session.removeAttribute(AWModificarFolio.FOLIO_COMENTARIO);
        session.removeAttribute(AWModificarFolio.FOLIO_EJE1);
        session.removeAttribute(AWModificarFolio.FOLIO_VALOR1);
        session.removeAttribute(AWModificarFolio.FOLIO_EJE2);
        session.removeAttribute(AWModificarFolio.FOLIO_VALOR2);
        session.removeAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
        session.removeAttribute(AWModificarFolio.FOLIO_COD_DOCUMENTO);
        session.removeAttribute(AWModificarFolio.FOLIO_FECHA_APERTURA);
        session.removeAttribute(AWModificarFolio.FOLIO_TIPO_PREDIO);
        session.removeAttribute(AWModificarFolio.FOLIO_ESTADO_FOLIO);
        session.removeAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);

        // eliminacion de informacion de ubicacion geografica ------------------------------
        // elimina (temp-cache)
        session.removeAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO);
        session.removeAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO);
        session.removeAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC);
        session.removeAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        session.removeAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA);
        session.removeAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        // ---------------------------------------------------------------------------------
        // bug:  0003580 ------------------------------------------------------------------
        session.removeAttribute(AWModificarFolio.FOLIO_FECHA_APERTURA);
        session.removeAttribute(AWModificarFolio.FOLIO_NUM_RADICACION);
        // ---------------------------------------------------------------------------------

    }

    /**
     * Elimina de la sesión la información de una anotación.
     */
    private void eliminarInfoBasicaAnotacion() {
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                *  @author Carlos Torres
                *  @chage   se agrega validacion de version diferente
                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        session.removeAttribute(AWModificarFolio.ANOTACION_ORDEN);
        session.removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        session.removeAttribute(AWModificarFolio.ANOTACION_NUM_RADICACION);

        session.removeAttribute(AWModificarFolio.ANOTACION_ID_ANOTACION);
        session.removeAttribute(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        session.removeAttribute(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
        session.removeAttribute(AWModificarFolio.ANOTACION_FECHA_RADICACION);
        session.removeAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION);
        session.removeAttribute(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
        session.removeAttribute(AWModificarFolio.ANOTACION_VALOR_ESPECIFICACION);
        session.removeAttribute(AWModificarFolio.ANOTACION_MODALIDAD);
        session.removeAttribute(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);
        session.removeAttribute(WebKeys.TIPO_OFICINA_I_N);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_INTERNACIONAL);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        session.removeAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        session.removeAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        session.removeAttribute(AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
         * @Descripcion: Se agrega la variable versionNatJuridica para optener
         * la version de la naturalesa juridica seleccionada
         */
        session.removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);

        session.removeAttribute(AWModificarFolio.ANOTACION_ESTADO);
        // session.removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
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
        session.removeAttribute("ESCOGER_ANOTACION_CANCELACION");
        session.removeAttribute(WebKeys.ACTUALIZAR);
    }

    private void eliminarInfoBasicaAnotacion(HttpServletRequest request) {
        // compatibility
        eliminarInfoBasicaAnotacion();
        do_FolioEdit_AnotacionXSalvedad_RemoveState(request);
    }

    /**
     * Elimina de la sesión la información de una dirección.
     */
    private void eliminarInfoBasicaDireccionEditar() {
        session.removeAttribute(AWModificarFolio.ELEMENTO_EDITAR);
    }

    /**
     * Elimina de la sesión la información de una dirección.
     */
    private void eliminarInfoBasicaDireccion() {
        session.removeAttribute(AWModificarFolio.FOLIO_EJE1);
        session.removeAttribute(AWModificarFolio.FOLIO_VALOR1);
        session.removeAttribute(AWModificarFolio.FOLIO_EJE2);
        session.removeAttribute(AWModificarFolio.FOLIO_VALOR2);
        session.removeAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
    }

    /**
     * Elimina de la sesión la información de un ciudadano.
     */
    private void eliminarInfoBasicaCiudadano() {
        session.removeAttribute(AWModificarFolio.ANOTACION_TIPO_ID_PERSONA);
        session.removeAttribute(AWModificarFolio.ANOTACION_TIPO_PERSONA);
        session.removeAttribute(AWModificarFolio.ANOTACION_SEXO_PERSONA);
        session.removeAttribute(AWModificarFolio.ANOTACION_COD_VERIFICACION);
        session.removeAttribute(AWModificarFolio.ANOTACION_NUM_ID_PERSONA);
        session.removeAttribute(AWModificarFolio.ANOTACION_APELLIDO_1_PERSONA);
        session.removeAttribute(AWModificarFolio.ANOTACION_APELLIDO_2_PERSONA);
        session.removeAttribute(AWModificarFolio.ANOTACION_NOMBRES_PERSONA);
        session.removeAttribute(AWModificarFolio.ANOTACION_TIPO_INTER_ASOCIACION);
        session.removeAttribute(AWModificarFolio.ANOTACION_PORCENTAJE_ASOCIACION);
        session.removeAttribute(AWModificarFolio.ANOTACION_ES_PROPIETARORIO_ASOCIACION);
    }

    /**
     * Método que convierte una cadena de texto en un objeto tipo Calendar
     *
     * @param fechaInterfaz
     * @return
     */
    private Calendar darFecha(String fechaInterfaz) {

        if ((null == fechaInterfaz)
                || ("".equals(fechaInterfaz.trim()))) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        String[] partido = fechaInterfaz.trim().split("/");

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

    /**
     * Método que convierte una cadena de texto en un objeto tipo Date
     *
     * @param fechaInterfaz
     * @return
     */
    private Date toDate(String fechaInterfaz) throws ParseException {
        if ((null == fechaInterfaz) || ("".equals(fechaInterfaz.trim()))) {
            return null;
        }
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        return formatoDelTexto.parse(fechaInterfaz);
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        for (int i = 0; i < numCiud; i++) {
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_COD_VERIFICACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);
            request.getSession().removeAttribute(CFolio.CIUDADANO_EDITABLE + i);
        }
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

        if (evento instanceof EvnRespFolio) {
            EvnRespFolio respuesta = (EvnRespFolio) evento;
            if (respuesta != null) {
                if (respuesta.getTipoEvento().equals(EvnRespFolio.CREAR)) {
                    Folio folioOriginal = new Folio();
                    folioOriginal = (Folio) copy(respuesta.getFolio());
                    session.setAttribute(WebKeys.FOLIO, folioOriginal);
                }

                if (respuesta.getTipoEvento().equals(EvnRespFolio.CONSULTAR)) {
                    Log.getInstance().debug(AWModificarFolio.class, "ACCION EN DOEND ES:" + accion);

                    if (accion.equals(AWModificarFolio.CONSULTA_DIGITACION_MASIVA)) {
                        if (respuesta.getFolio() != null) {
                            String parametro = request.getParameter(WebKeys.PARAMETRO);

                            if (parametro.equals(CDigitacionMasiva.OPCION_COMPLEMENTACION)) {
                                if ((respuesta.getFolio().getComplementacion() != null) && (respuesta.getFolio().getComplementacion().getComplementacion() != null)) {
                                    session.setAttribute(CDigitacionMasiva.OPCION_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getComplementacion());
                                } else {
                                    session.removeAttribute(CDigitacionMasiva.OPCION_COMPLEMENTACION);
                                }

                                if ((respuesta.getFolio().getComplementacion() != null) && (respuesta.getFolio().getComplementacion().getIdComplementacion() != null)) {
                                    session.setAttribute(CComplementacion.ID_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getIdComplementacion());
                                } else {
                                    session.removeAttribute(CComplementacion.ID_COMPLEMENTACION);
                                }
                            }

                            if (parametro.equals(CDigitacionMasiva.OPCION_LINDERO)) {
                                if (respuesta.getFolio().getLindero() != null) {
                                    session.setAttribute(CDigitacionMasiva.OPCION_LINDERO, respuesta.getFolio().getLindero());
                                } else {
                                    session.removeAttribute(CDigitacionMasiva.OPCION_LINDERO);
                                }
                            }

                            if (parametro.equals(CDigitacionMasiva.OPCION_DIRECCION)) {
                                if ((respuesta.getFolio().getDirecciones() != null) && (respuesta.getFolio().getDirecciones().size() > 0)) {
                                    Direccion direccion = (Direccion) respuesta.getFolio().getDirecciones().get(respuesta.getFolio().getDirecciones().size() - 1);

                                    session.removeAttribute(CFolio.FOLIO_EJE1);
                                    session.removeAttribute(CFolio.FOLIO_EJE2);
                                    session.removeAttribute(CFolio.FOLIO_VALOR1);
                                    session.removeAttribute(CFolio.FOLIO_VALOR2);
                                    session.removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);

                                    if ((direccion != null) && (direccion.getEje() != null)) {
                                        Eje eje = direccion.getEje();

                                        if (eje.getIdEje() != null) {
                                            session.setAttribute(AWModificarFolio.FOLIO_EJE1, eje.getIdEje());
                                        }
                                    }

                                    if ((direccion != null) && (direccion.getEje1() != null)) {
                                        Eje eje1 = direccion.getEje1();

                                        if (eje1.getIdEje() != null) {
                                            session.setAttribute(AWModificarFolio.FOLIO_EJE2, eje1.getIdEje());
                                        }
                                    }

                                    if ((direccion != null) && (direccion.getValorEje() != null)) {
                                        session.setAttribute(AWModificarFolio.FOLIO_VALOR1, direccion.getValorEje());
                                    }

                                    if ((direccion != null) && (direccion.getValorEje1() != null)) {
                                        session.setAttribute(AWModificarFolio.FOLIO_VALOR2, direccion.getValorEje1());
                                    }

                                    if ((direccion != null) && (direccion.getEspecificacion() != null)) {
                                        session.setAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR, direccion.getEspecificacion());
                                    }
                                } else {
                                    session.removeAttribute(CFolio.FOLIO_EJE1);
                                    session.removeAttribute(CFolio.FOLIO_EJE2);
                                    session.removeAttribute(CFolio.FOLIO_VALOR1);
                                    session.removeAttribute(CFolio.FOLIO_VALOR2);
                                    session.removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
                                }
                            }
                        }
                        if (datosFoliosForaneos(request, respuesta)) {
                            session.setAttribute(WebKeys.ALERTA_FOLIOS_FORANEOS, new Boolean(true));
                        }
                    }
                }
            }

        } else if (evento instanceof EvnRespCorreccion) {
            EvnRespCorreccion respuesta = (EvnRespCorreccion) evento;
            if (respuesta.getTipoEvento().equals(EvnCorreccion.EDITAR_FOLIOS)) {
                session.setAttribute(WebKeys.MENSAJE, "La operación ha sido exitosa");
            } else if (EvnRespCorreccion.CONSULTAR_FOLIO.equals(respuesta.getTipoEvento())) {

                // :: moved2: doEnd_CargarFolio
                session.setAttribute("ANOTACION_DELTA", new Boolean(true));
                eliminarInfoBasicaAnotacion();
                eliminarInfoBasicaVariosCiudadanos(request);
                session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                session.removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
                session.removeAttribute(AWModificarFolio.AGREGAR_ANOTACION);
                session.removeAttribute(WebKeys.HAY_EXCEPCION);
                session.removeAttribute("cancelar");
                session.removeAttribute(AWModificarFolio.NUMERO_SEGREGACIONES_VACIAS);
                session.removeAttribute(AWModificarFolio.MODIFICA_DEFINITIVA);
                doEnd_Corr_PageEvent_CargarFolioCorreccion(request, respuesta);
            } else if (EvnRespCorreccion.CORRECCIONSIMPLEMAIN_LOADFOLIOANOTACIONXOPTION_BTNSTART__EVENTRESP_OK.equals(respuesta.getTipoEvento())) {

                // :: moved2: doEnd_FolioAnotacionXLoad
                doEnd_Corr_PageEvent_FolioAnotacionXLoad(request, respuesta);

            } else if (respuesta.getTipoEvento().equals(EvnCorreccion.CONSULTAR_FOLIO_USUARIO)) {
                eliminarInfoBasicaFolio();
                eliminarInfoBasicaCiudadano();
                eliminarInfoBasicaAnotacion();
                session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                session.removeAttribute(AWModificarFolio.AGREGAR_ANOTACION);

                Folio folio = (Folio) respuesta.getPayload();
                session.setAttribute(WebKeys.FOLIO_EDITADO, folio);

                cargarInfoBasicaFolio(folio);

                Folio folioOriginal = new Folio();
                folioOriginal = (Folio) copy(folio);
                session.setAttribute(WebKeys.FOLIO_EDITADO, folioOriginal);

                List anotaciones = folio.getAnotaciones();
                List anotacionesSession = new Vector();

                if (anotaciones != null) {
                    Iterator it = anotaciones.iterator();

                    while (it.hasNext()) {
                        Anotacion anot = (Anotacion) it.next();

                        if ((anot != null) && (anot.getIdAnotacion() == null)) {
                            anotacionesSession.add(anot);
                        }
                    }
                }

                session.setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotacionesSession);
            } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.CONSULTAR_SEG_ENG_EXISTENTES)) {

                // bug 05370
                // incluir el conjunto de estados del folio
                // dentro de turno/solicitud/solicitudfolio[]
                Turno local_Turno;
                local_Turno = (Turno) session.getAttribute(WebKeys.TURNO);
                Map querySolicitudFolioEstadoMap = null;
                querySolicitudFolioEstadoMap = respuesta.getQuerySolicitudFolioEstadoMap();

                if (null == querySolicitudFolioEstadoMap) {
                    querySolicitudFolioEstadoMap = new HashMap();
                }

                // para cada solicitudFolio/folio, observar si se ha fijado el estado
                List local_TSSolicitudFolios = null;
                try {
                    local_TSSolicitudFolios = local_Turno.getSolicitud().getSolicitudFolios();
                } catch (Exception e) {
                }
                if (null == local_TSSolicitudFolios) {
                    local_TSSolicitudFolios = new ArrayList();
                }

                SolicitudFolio local_TSSolicitudFoliosElement;
                String local_TSSolicitudFoliosElement_IdMatricula;
                Folio local_FolioElement;
                Folio queryLocalFolioResult;
                EstadoFolio queryLocalFolioResultEstadoFolio;
                for (Iterator local_TSSolicitudFoliosIterator = local_TSSolicitudFolios.iterator(); local_TSSolicitudFoliosIterator.hasNext();) {
                    local_TSSolicitudFoliosElement = (SolicitudFolio) local_TSSolicitudFoliosIterator.next();
                    local_TSSolicitudFoliosElement_IdMatricula = local_TSSolicitudFoliosElement.getIdMatricula();
                    local_FolioElement = local_TSSolicitudFoliosElement.getFolio();

                    // fix state ----------------------
                    if (null != local_FolioElement.getEstado()) {
                        continue;
                    }

                    if (querySolicitudFolioEstadoMap.containsKey(local_TSSolicitudFoliosElement_IdMatricula)) {

                        queryLocalFolioResult = (Folio) querySolicitudFolioEstadoMap.get(local_TSSolicitudFoliosElement_IdMatricula);
                        queryLocalFolioResultEstadoFolio = queryLocalFolioResult.getEstado();

                        if (null != queryLocalFolioResultEstadoFolio) {
                            EstadoFolio estadoFolio = (EstadoFolio) copy(queryLocalFolioResultEstadoFolio);
                            local_FolioElement.setEstado(estadoFolio);
                        } // if

                    } // if

                    // ------------------------------
                } // for

                List webSegEng = (List) respuesta.getPayload();

                if (webSegEng == null) {
                    webSegEng = new ArrayList();
                }
                Iterator it = webSegEng.iterator();
                while (it.hasNext()) {
                    WebSegEng segEng = (WebSegEng) it.next();
                    if (segEng instanceof WebEnglobe) {
                        session.setAttribute(WebKeys.WEB_ENGLOBE, segEng);

                        List direcciones = new ArrayList();

                        WebEnglobe webEnglobe = (WebEnglobe) segEng;
                        WebFolioNuevo webFolioNuevo = webEnglobe.getFolioNuevo();

                        if (webFolioNuevo != null) {

                            List webDirecciones = webFolioNuevo.getDirecciones();

                            Iterator itdireccion = webDirecciones.iterator();
                            while (itdireccion.hasNext()) {
                                WebDireccion webDireccion = (WebDireccion) itdireccion.next();
                                Direccion direccion = new Direccion();
                                Eje eje1 = new Eje();
                                eje1.setIdEje(webDireccion.getIdEje1());
                                direccion.setEje(eje1);
                                direccion.setValorEje(webDireccion.getValorEje1());
                                Eje eje2 = new Eje();
                                eje2.setIdEje(webDireccion.getIdEje2());
                                direccion.setEje1(eje2);
                                direccion.setValorEje1(webDireccion.getValorEje2());
                                direccion.setEspecificacion(webDireccion.getEspecificacion());
                                direccion.setIdDireccion(webDireccion.getIdWebDireccion());
                                direcciones.add(direccion);
                            }

                        }

                        session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);

                    }

                    if (segEng instanceof WebSegregacion) {
                        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
                        if (folio != null) {
                            WebSegregacion webSegregacion = (WebSegregacion) segEng;
                            if (webSegregacion.getFoliosHeredados() != null && webSegregacion.getFoliosHeredados().size() > 0) {
                                WebFolioHeredado webFolioHeredado = (WebFolioHeredado) webSegregacion.getFoliosHeredados().get(0);
                                if (folio.getIdMatricula().equals(webFolioHeredado.getIdMatricula())) {
                                    session.setAttribute(WebKeys.WEB_SEGREGACION, segEng);
                                    if (respuesta.getOficinaOrigen() != null) {
                                        session.setAttribute(WebKeys.OFICINA_ORIGEN, respuesta.getOficinaOrigen());
                                    }
                                }
                            }
                            /**
                             * @author : Carlos Mario Torres Urina
                             * @casoMantis : 0012705.
                             * @actaReq : Acta - Requerimiento No
                             * 056_453_Modificiación_de_Naturaleza_Jurídica
                             * @change : Se verifica si ecciste la anotacion
                             * para asignar el valor en la session
                             */
                            if (webSegregacion.getAnotacion() != null) {
                                session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, webSegregacion.getAnotacion().getVersion());
                                /*
                                                            *  @author Carlos Torres
                                                            *  @chage   se agrega validacion de version diferente
                                                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                 */
                                session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, webSegregacion.getAnotacion().getDocumento().getVersion().toString());
                            }

                        }
                    }
                }

                request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, respuesta.getNextOrden());

            } else if (respuesta.getTipoEvento().equals(EvnRespCorreccion.CONSULTAR_PROPIETARIOS_FOLIO)) {
                request.getSession().setAttribute(AWModificarFolio.LISTA_PROPIETARIOS_FOLIO, respuesta.getPropietariosFolios());
            } else if (respuesta.getTipoEvento().equals(EvnRespCorreccion.VOLVER_AGREGAR_CIUDADANOS)) {
                request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, respuesta.getListaCompletaCiudadanos());
            } else if (respuesta.getTipoEvento().equals(EvnRespCorreccion.APROBAR_ESPECIALIZADO)) {
                if (respuesta.getFoliosDerivadoHijo() != null) {
                    session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO, respuesta.getFoliosDerivadoHijo());
                }
                if (respuesta.getFoliosDerHijosTmp() != null) {
                    session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO_TMP, respuesta.getFoliosDerHijosTmp());
                }
            } else if (respuesta.getTipoEvento().equals(EvnRespCorreccion.ANOTACIONES_ASOCIADAS_OK)) {
                session.setAttribute(AWModificarFolio.CONSULTA_ANOTACIONES_RESP, "1");
                session.setAttribute(AWModificarFolio.EXISTEN_ANOTACIONES_ASOCIADAS, respuesta.getPayload());
            }

        }

        // bind the page action results
        // se podria usar PPR para esto ?
        if (evento instanceof EvnRespCiudadano) {
            EvnRespCiudadano evn = (EvnRespCiudadano) evento;
            String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);

            if (evn.isCiudadanoEncontrado()) {
                request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + ver, evn.getCiudadano().getApellido1());
                request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + ver, evn.getCiudadano().getApellido2());
                request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + ver, evn.getCiudadano().getNombre());
                request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA + ver, evn.getCiudadano().getSexo());
                request.getSession().setAttribute(CFolio.CIUDADANO_EDITABLE + ver, new Boolean(true));
            } else {
                request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + ver);
                request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + ver);
                request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + ver);
                request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA + ver);
                request.getSession().removeAttribute(CFolio.CIUDADANO_EDITABLE + ver);
            }
        }

        if (evento instanceof EvnRespCorreccionFolioPadresHijos) {
            // unwrap the event and update view

            // por el momento, se estan actualizando padres e hijos.
            // sin importar el tipo de evento enviado
            EvnRespCorreccionFolioPadresHijos thisEvent
                    = (EvnRespCorreccionFolioPadresHijos) evento;

            // eliminar datos de cache
            eliminarInfoBasicaFolio();
            eliminarInfoBasicaCiudadano();
            eliminarInfoBasicaAnotacion();

            // fijar listas de padres e hijos en sesion
            session.setAttribute(WebKeys.LISTA_FOLIOS_PADRE, thisEvent.getFoliosPadre());
            session.setAttribute(WebKeys.LISTA_FOLIOS_HIJO, thisEvent.getFoliosHijo());

            // actualizar los datos de folio
            // este paso se hace porque el conjunto de anotaciones pudo haber variado
            // (codigo ya existente; )
            Folio folioOriginal = new Folio();
            folioOriginal = (Folio) copy(thisEvent.getFolio());
            session.setAttribute(WebKeys.FOLIO, folioOriginal);

            List anotaciones = (List) thisEvent.getFolio().getAnotaciones();
            List anotacionesSession = new Vector();
            anotacionesSession.addAll(anotaciones);

            List anotacionesTemp = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

            if (null != anotacionesTemp) {
                Iterator it = anotacionesTemp.iterator();

                for (; it.hasNext();) {
                    Anotacion anot = (Anotacion) it.next();

                    if ((null != anot)
                            && (null == anot.getIdAnotacion())) {
                        anotacionesSession.add(anot);
                    } // end if
                } // end for
            } // end if

            session.setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotacionesSession);

            // eliminar los datos del paginador, para obligar reload de anotaciones
            LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
            if (llaves != null) {
                llaves.removeLLaves(request);
            }
            session.removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

            // eliminar otros datos en sesion
            session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
            session.removeAttribute(AWModificarFolio.AGREGAR_ANOTACION);

            // fijar otros datos relacionados con el folio
            // no es necesario actualizar:
            // session.setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN, respuesta.getGravamenes());
            // session.setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES, respuesta.getMedidasCautelares());
            // session.setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES, respuesta.getSalvedadesAnotaciones());
            // session.setAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION, respuesta.getCancelaciones());
            List salvsFolio = folioOriginal.getSalvedades();
            String salvedadFolioId = null;
            String salvedadFolioDesc = null;
            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

            for (Iterator salvItera = salvsFolio.iterator();
                    salvItera.hasNext();) {
                SalvedadFolio salvedadFolio = (SalvedadFolio) salvItera.next();
                String nroRadica = salvedadFolio.getNumRadicacion();
                if (nroRadica != null && nroRadica.equals(turno.getIdWorkflow())) {
                    if (salvedadFolioId == null
                            || Integer.parseInt(salvedadFolioId) < Integer.parseInt(salvedadFolio.getIdSalvedad())) {
                        salvedadFolioId = salvedadFolio.getIdSalvedad();
                    }
                    salvedadFolioDesc = salvedadFolio.getDescripcion();
                }
            }

            session.setAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID, salvedadFolioId);
            session.setAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION, salvedadFolioDesc);

            session.setAttribute(WebKeys.TOTAL_ANOTACIONES, new BigDecimal(thisEvent.getNumeroAnotaciones()));

        }

        if (evento instanceof EvnRespNextOrdenAnotacion) {
            EvnRespNextOrdenAnotacion evn = (EvnRespNextOrdenAnotacion) evento;
            request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, evn.getPayload());
            Log.getInstance().debug(AWModificarFolio.class, "Payload =" + (String) evn.getPayload());
        }
        if (evento instanceof EvnRespCalificacion) {
            eliminarInfoBasicaAnotacion(request);
            eliminarInfoBasicaVariosCiudadanos(request);
            request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
            /*
                                         * @author      :   Julio Alcázar Rivas
                                         * @change      :   Eliminar el atributo de salvedad de la session
                                         * Caso Mantis  :   04131
             */
            session.removeAttribute(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
            session.removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
            session.removeAttribute(WebKeys.HAY_EXCEPCION);
            EvnRespCalificacion respuesta = (EvnRespCalificacion) evento;
            if (respuesta.getNumeroSegregacionesVacias() != -1) {
                session.removeAttribute(NUMERO_SEGREGACIONES_VACIAS);
                session.setAttribute(NUMERO_SEGREGACIONES_VACIAS, new Long(respuesta.getNumeroSegregacionesVacias()));
            }
            if (session.getAttribute(WebKeys.WEB_SEGREGACION) != null) {
                session.removeAttribute(WebKeys.WEB_SEGREGACION);
                session.removeAttribute(WebKeys.TURNO);
                session.setAttribute(WebKeys.TURNO, respuesta.getTurno());
            }
        }
        if (evento instanceof EvnRespSegregacion) {
            EvnRespSegregacion respuesta = (EvnRespSegregacion) evento;
            if (respuesta.getTipoEvento().equals(EvnRespSegregacion.CONSULTA_FOLIO)) {
                //SE RECUPERA EL FOLIO QUE SE CONSULTO.
                Folio padre = (Folio) respuesta.getPayload();
                List anotaciones = new ArrayList();

                if (padre != null) {
                    anotaciones.addAll(padre.getAnotaciones());
                }

                if (respuesta.getWebSegEng() != null) {
                    request.getSession().setAttribute(WebKeys.WEB_SEGREGACION, respuesta.getWebSegEng());
                }

                request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);

                LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
                if (llaves != null) {
                    llaves.removeLLaves(request);
                }
                request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
            }
        }
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento cargarDescripcionNaturaleza(HttpServletRequest request) throws AccionWebException {
        request.getSession().setAttribute(WebKeys.ACTUALIZAR, new Boolean(true));
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
         */
        Long version = 0L;

        String ac = request.getParameter(WebKeys.ACCION);

        ValidacionParametrosAnotacionCalificacionException exception = new ValidacionParametrosAnotacionCalificacionException();

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
        for (Iterator it = grupoNaturalezas.iterator(); it.hasNext() && !found;) {
            grupo = (GrupoNaturalezaJuridica) it.next();
            for (Iterator it2 = grupo.getNaturalezaJuridicas().iterator(); it2.hasNext() && !found;) {
                nat = (NaturalezaJuridica) it2.next();
                if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                    descripcion = nat.getNombre();
                    /**
                     * @Autor: Carlos Torres
                     * @Mantis: 0012705
                     * @Requerimiento:
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     * @Descripcion: Se agrega la variable versionNatJuridica
                     * para optener la version de la naturalesa juridica
                     * seleccionada
                     */
                    version = nat.getVersion();
                    found = true;
                }
            }
        }

        if ((ac.equals(CARGAR_DESCRIPCION_NATURALEZA_CANCELACION)) || (ac.equals(CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION))) {
            if (!CGrupoNaturalezaJuridica.ID_CANCELACION.equals(grupo.getIdGrupoNatJuridica())) {
                request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                /**
                 * @Autor: Carlos Torres
                 * @Mantis: 0012705
                 * @Requerimiento: 056_453_Modificiacion_de_Naturaleza_Juridica
                 * @Descripcion: se define variable version
                 */
                request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
                if (found) {
                    throw new NaturalezaInvalidaException("La naturaleza juridica (" + idNaturaleza + " - " + descripcion + ") no es de cancelacion.");
                }
            }
        }
        
        if (!found) {
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
            /**
             * @Autor: Carlos Torres
             * @Mantis: 0012705
             * @Requerimiento: 056_453_Modificiacion_de_Naturaleza_Juridica
             * @Descripcion: se define variable version
             */
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
            throw new NaturalezaInvalidaException("La naturaleza juridica (" + idNaturaleza + ") no existe.");
        } else {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, descripcion);
            /**
             * @Autor: Carlos Torres
             * @Mantis: 0012705
             * @Requerimiento: 056_453_Modificiacion_de_Naturaleza_Juridica
             * @Descripcion: se define variable version
             */

            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, version);
        }
        session.setAttribute(WebKeys.RECARGA, new Boolean(true));
        //request.getSession().setAttribute(AWModificacionFolio.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    /**
     * @param request
     * @param matriculas
     * @return
     */
    private boolean datosFoliosForaneos(HttpServletRequest request, EvnRespFolio respuesta) {

        List matriculas = new ArrayList();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Solicitud solicitud = turno.getSolicitud();
        List solFolios = solicitud.getSolicitudFolios();

        Iterator it = solFolios.iterator();
        while (it.hasNext()) {
            SolicitudFolio solfolio = (SolicitudFolio) it.next();
            matriculas.add(solfolio.getIdMatricula());
        }

        if (respuesta == null || respuesta.getFolio() == null) {
            return false;
        }

        String matFolioTraido = respuesta.getFolio().getIdMatricula();
        Iterator itMatriculas = matriculas.iterator();
        if (matriculas.size() == 0) {
            return true;
        }

        boolean existe = false;
        while (itMatriculas.hasNext()) {
            String matricula = (String) itMatriculas.next();
            if (matricula.equals(matFolioTraido)) {
                existe = true;
            }
        }
        if (!existe) {
            return true;
        }

        return false;
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
            Log.getInstance().error(AWModificarFolio.class, e);
        } catch (ClassNotFoundException cnfe) {
            Log.getInstance().error(AWModificarFolio.class, cnfe);
        }

        return obj;
    }

    private void
            doEndCorr_PageEvent_StartAnotacionSegregacion(HttpServletRequest request, EventoRespuesta evento) {

        HttpSession session = request.getSession();
        EvnRespNextOrdenAnotacion evn = (EvnRespNextOrdenAnotacion) evento;
        session.setAttribute(CFolio.NEXT_ORDEN_ANOTACION, evn.getPayload());
        Log.getInstance().debug(AWModificarFolio.class, "Payload =" + (String) evn.getPayload());

    }  // end-method: doEndCorr_PageEvent_StartAnotacionSegregacion

    private Evento
            doCorr_PageEvent_StartAnotacionSegregacion(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session;
        session = request.getSession();

        // Correcciones (Session Marks For SharedProcess)
        WebSegregacion webSegregacion = new WebSegregacion();

        webSegregacion.setIdProceso(new Integer(CProceso.PROCESO_CORRECCIONES).intValue());

        session.setAttribute(WebKeys.WEB_SEGREGACION, webSegregacion);

        // Build Message
        Evento result;
        result = segregarAnotacion(request);

        return result;

    } // end-method: doCorr_PageEvent_StartAnotacionSegregacion

    private Evento
            doCorr_PageEvent_FinishAnotacionSegregacion(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session;
        session = request.getSession();

        return null;

    } // end-method: doCorr_PageEvent_StartAnotacionSegregacion

    private Evento
            doCorr_PageEvent_FinishAnotacionEnglobe(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session;
        session = request.getSession();

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        EvnCorreccion evento = new EvnCorreccion(usuarioAuriga, EvnCorreccion.CONSULTAR_SEG_ENG_EXISTENTES);
        evento.setTurno(turno);
        return evento;

    } // end-method: doCorr_PageEvent_StartAnotacionSegregacion

    private Evento
            doCorr_PageEvent_StartAnotacionEnglobe(HttpServletRequest request)
            throws AccionWebException {

        // Bug 3563:
        // guardar el folio anterior por si se
        // le da cancelar, poder recuperarlo;
        // metodo englobar remueve WebKeys.FOLIO
        session.setAttribute("LOCAL:WEBKEYS.FOLIO", session.getAttribute(WebKeys.FOLIO));

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
        }
        String idProc = "" + turno.getIdProceso();
        webEnglobe.setIdProceso(new Integer(idProc).intValue());

        session.setAttribute(WebKeys.WEB_ENGLOBE, webEnglobe);

        Evento result;
        result = englobar(request);

        return result;

    } // end-method: doCorr_PageEvent_StartAnotacionSegregacion

    private void
            doEnd_Corr_PageEvent_CargarFolioCorreccion(HttpServletRequest request, EventoRespuesta evento) {
        // throws AccionWebException {

        HttpSession session = request.getSession();

        EvnRespCorreccion respuesta = (EvnRespCorreccion) evento;

        eliminarInfoBasicaFolio();
        eliminarInfoBasicaAnotacion();
        eliminarInfoBasicaDireccion();

        LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
        if (llaves != null) {
            llaves.removeLLaves(request);
        }
        session.removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

        session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        session.removeAttribute(AWModificarFolio.AGREGAR_ANOTACION);

        session.setAttribute(WebKeys.FOLIO, respuesta.getFolio());
        Folio folio = respuesta.getFolio();
        session.setAttribute(AWModificarFolio.FOLIO_NUPRE, folio.getNupre());
        session.setAttribute(AWModificarFolio.FOLIO_DETERMINACION_INMUEBLE, folio.getDeterminaInm());
        session.setAttribute(AWModificarFolio.FOLIO_PRIVMETROS, folio.getPrivMetros());
        session.setAttribute(AWModificarFolio.FOLIO_PRIVCENTIMETROS, folio.getPrivCentimetros());
        session.setAttribute(AWModificarFolio.FOLIO_CONSMETROS, folio.getConsMetros());
        session.setAttribute(AWModificarFolio.FOLIO_CONSCENTIMETROS, folio.getConsCentimetros());
        session.setAttribute(AWModificarFolio.FOLIO_COEFICIENTE, folio.getCoeficiente());
        session.setAttribute(AWModificarFolio.FOLIO_HECTAREAS, folio.getHectareas());
        session.setAttribute(AWModificarFolio.FOLIO_METROS, folio.getMetros());
        session.setAttribute(AWModificarFolio.FOLIO_CENTIMETROS, folio.getCentimetros());

        session.setAttribute(NUMERO_SEGREGACIONES_VACIAS, new Long(respuesta.getNumSegregacionesVacias()));
        session.setAttribute(WebKeys.MAYOR_EXTENSION_FOLIO, respuesta.getFolio());
        session.setAttribute(WebKeys.LISTA_FOLIOS_PADRE, respuesta.getFoliosPadre());
        session.setAttribute(WebKeys.LISTA_FOLIOS_HIJO, respuesta.getFoliosHijo());
        session.setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN, respuesta.getGravamenes());
        session.setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES, respuesta.getMedidasCautelares());
        if (respuesta.getFalsaTradicion() != null) {
            session.setAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION, respuesta.getFalsaTradicion());
        }
        if (respuesta.getAnotacionesInvalidas() != null) {
            session.setAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS, respuesta.getAnotacionesInvalidas());
        }

        session.setAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR, respuesta.getAnotacionesPatrimonioFamiliar());
        session.setAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA, respuesta.getAnotacionesAfectacionVivienda());

        session.setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES, respuesta.getSalvedadesAnotaciones());
        session.setAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION, respuesta.getCancelaciones());
        // Bug 3543 ( session-key: "TOTAL_ANOTACIONES", para validar el cambio de orden )
        session.setAttribute(WebKeys.TOTAL_ANOTACIONES, new BigDecimal(respuesta.getNumeroAnotaciones()));
        //pedir a mostrarFolioHelper la recarga
        session.setAttribute(WebKeys.RECARGA, new Boolean(true));

        cargarInfoBasicaFolio(respuesta.getFolio());

        // este es el folio definitivo + cambios
        Folio t1_Folio = new Folio();
        t1_Folio = (Folio) copy(respuesta.getFolio());
        session.setAttribute(WebKeys.FOLIO, t1_Folio);

        /*

      List anotaciones = (List) respuesta.getFolio().getAnotaciones();
      List anotacionesSession = new Vector();
      anotacionesSession.addAll(anotaciones);

      List anotacionesTemp = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

      if (anotacionesTemp != null) {
              Iterator it = anotacionesTemp.iterator();

              while (it.hasNext()) {
                      Anotacion anot = (Anotacion) it.next();

                      if ((anot != null) && (anot.getIdAnotacion() == null)) {
                              anotacionesSession.add(anot);
                      }
              }
      }

      session.setAttribute( WebKeys.LISTA_ANOTACIONES_FOLIO, anotacionesSession );

         */
        // cargar a sesion conjunto de permisos asociados ------------------
        gov.sir.core.negocio.modelo.Turno turno
                = (gov.sir.core.negocio.modelo.Turno) session.getAttribute(gov.sir.core.web.WebKeys.TURNO);

        java.util.List modelPermisosList = null;

        if (turno.getSolicitud() instanceof SolicitudCorreccion) {
            SolicitudCorreccion solicitudCorreccion
                    = (SolicitudCorreccion) turno.getSolicitud();
            modelPermisosList = solicitudCorreccion.getPermisos();
        }

        if (turno.getSolicitud() instanceof SolicitudRegistro) {
            SolicitudRegistro solicitudCorreccion
                    = (SolicitudRegistro) turno.getSolicitud();
            modelPermisosList = solicitudCorreccion.getPermisos();
        }

        /*
       * si el folio fue producto de segregación se debe permitir editar tipo de predio
       * TFS 3575
         */
        if (modelPermisosList != null && t1_Folio != null && !t1_Folio.isDefinitivo()) {
            /*
    	   * A la colección inicial de permisos se le añade los permisos
    	   * FOLIO_TIPO_PREDIO_ID
             */
            List permisosAdicionales = new ArrayList();
            SolicitudPermisoCorreccion permisoCRAdicional = null;

            permisoCRAdicional = new SolicitudPermisoCorreccion();
            permisoCRAdicional.setIdPermiso(PermisosCorreccionAspectModelConstants.FOLIO_TIPOPREDIO_ID);
            permisosAdicionales.add(permisoCRAdicional);

            for (Iterator permisosItera = modelPermisosList.iterator();
                    permisosItera.hasNext();) {
                SolicitudPermisoCorreccion permisoCorr1 = (SolicitudPermisoCorreccion) permisosItera.next();
                for (Iterator permisosAdItera = permisosAdicionales.iterator();
                        permisosAdItera.hasNext();) {
                    SolicitudPermisoCorreccion permisoCorr2 = (SolicitudPermisoCorreccion) permisosAdItera.next();
                    if (permisoCorr1.getIdPermiso() != null
                            && permisoCorr1.getIdPermiso().equals(permisoCorr2.getIdPermiso())) {
                        permisosAdItera.remove();
                    }
                }
            }
            permisosAdicionales.addAll(modelPermisosList);
            modelPermisosList = permisosAdicionales;
        }

        // ------------------------------------------------------
        // bug 3530/3560
        // cargar datos de salvedad-folio para editarla
        // condiciones: se asume la ultima salvedad procesada por el usuario
        // process: get-data
        Folio local_t1Folio;
        List local_t1FolioSalvedades; // List< SalvedadFolio >

        local_t1Folio = t1_Folio;
        local_t1FolioSalvedades = local_t1Folio.getSalvedades();

        if (null == local_t1FolioSalvedades) {
            local_t1FolioSalvedades = new ArrayList();
        }

        // obtener la ultima*/primera Salvedad, fijar los atributos respectivos
        Iterator local_FolioSalvedadesIterator;
        local_FolioSalvedadesIterator = local_t1FolioSalvedades.iterator();
        SalvedadFolio local_FolioSalvedadesElement = null;
        for (; local_FolioSalvedadesIterator.hasNext();) {
            local_FolioSalvedadesElement = (SalvedadFolio) local_FolioSalvedadesIterator.next();
            // break;
        } // for

        if (null != local_FolioSalvedadesElement) {
            if (!respuesta.isCargarSalvedad()) {
                if (local_FolioSalvedadesElement.isTemporal()) {
                    session.setAttribute(ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION, local_FolioSalvedadesElement.getDescripcion());
                    session.setAttribute(ITEMID_FOLIOEDICION_SALVEDADFOLIOID, local_FolioSalvedadesElement.getIdSalvedad());
                }
            } else {
                session.setAttribute(ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION, local_FolioSalvedadesElement.getDescripcion());
                session.setAttribute(ITEMID_FOLIOEDICION_SALVEDADFOLIOID, local_FolioSalvedadesElement.getIdSalvedad());
            }

        }

        // ------------------------------------------------------
        session.setAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST, modelPermisosList);
        // eliminar el cache de direcciones; los campos de agregar direccion
        // deben estar vacios
        eliminarInfoBasicaDireccion();

        // ------------------------------------------------------
        // Bug 3552
        String t0Complementacion;
        String t1Complementacion;
        String t2Complementacion; // delta

        boolean ruleComplementacionAdicionaEnabled = respuesta.isRuleComplementacionAdicionaEnabled();

        session.setAttribute(PARAM__OPTION_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONADICIONAENABLED, new Boolean(ruleComplementacionAdicionaEnabled));

        if (ruleComplementacionAdicionaEnabled) {

            t0Complementacion = respuesta.getFolioT0_complementacion_complementacion();
            t1Complementacion = respuesta.getFolioT1_complementacion_complementacion();
            t2Complementacion = respuesta.getFolioT2_complementacion_complementacion();

            session.setAttribute(PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT0, t0Complementacion);
            session.setAttribute(PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_COMPLEMENTACIONT2, t2Complementacion);

        } // if

        // ------------------------------------------------------
        // ------------------------------------------------------
        // Bug 3541
        // Cargar t0_folio (definitivo), Cargar t1_folio (definitivo+cambios)
        // Realizar analisis de diferencias hacia adelante
        gov.sir.core.negocio.modelo.Folio local_t0Folio;
        gov.sir.core.negocio.modelo.Folio local_t2Folio;

        local_t0Folio = (Folio) Nvl(respuesta.getT0_Folio(), new Folio());

        local_t2Folio = new gov.sir.core.negocio.modelo.Folio();

        BasicJXPathForwardDiffProcessor processor;
        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager;
        BindManager bindManager = new FolioEditBindManager();
        localProcessManager = doExtractAuthorization_ProcessManager(request, modelPermisosList);
        DeltaFolioAnalysisParams deltaParams = new DeltaFolioAnalysisParams(bindManager, localProcessManager);

        AbstractFactory abstractFactory;
        abstractFactory = new FolioAbstractFactory();

        Log.getInstance().debug(AWModificarFolio.class, "@@ correcciones: sof-analysis-fw-differences (load-folio)");

        processor = jx_ProcessForwardDifferences(
                deltaParams.getDeltaPaths(), deltaParams.getComparators(), deltaParams.getAuthAccess(), local_t0Folio, local_t1Folio, local_t2Folio, abstractFactory);

        Log.getInstance().debug(AWModificarFolio.class, "@@ correcciones: eof-analysis-fw-differences (load-folio)");

        Map local_FwdDiffComparisonResultsMap = processor.getComparisonResultsMap(bindManager);
        session.setAttribute(PARAM__ITEM_CORRSIMPLE_FOLIOEDIT_FORWARDDIFFCOMPARISONRESULTS, local_FwdDiffComparisonResultsMap);

        // ------------------------------------------------------
        // -----------------------------------------------------------------
        // colocar por defecto el tab seleccionado en 1
        final String initTab = CTabs.TAB1;
        session.setAttribute(CTabs.TAB, initTab);

        if (respuesta.getFoliosDerivadoHijo() != null) {
            session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO, respuesta.getFoliosDerivadoHijo());
        }
        if (respuesta.getFoliosHijo() != null) {
            session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO_TMP, respuesta.getFoliosDerHijosTmp());
        }
        // -----------------------------------------------------------------

    }  // end-method: doEnd_Corr_PageEvent_CargarFolioCorreccion

    // ------------------------------------------------------------------------------------------------------------
    private void
            doEnd_Corr_PageEvent_FolioAnotacionXLoad(HttpServletRequest request, EventoRespuesta evento) {
        // throws AccionWebException {

        HttpSession session;
        session = request.getSession();

        EvnRespCorreccion respuesta;
        respuesta = (EvnRespCorreccion) evento;

        // extraer la lista de permisos
        java.util.List modelPermisosList = null;
        modelPermisosList = (java.util.List) session.getAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST);

        gov.sir.core.negocio.modelo.Anotacion local_t1Anotacion;
        local_t1Anotacion = (gov.sir.core.negocio.modelo.Anotacion) session.getAttribute(AWModificarFolio.ANOTACION_EDITADA);

        // ------------------------------------------------------
        // Bug 3541
        // Cargar t0_anotacion (definitivo), Cargar t1_anotacion (definitivo+cambios)
        // Realizar analisis de diferencias hacia adelante
        gov.sir.core.negocio.modelo.Anotacion local_t0Anotacion;
        gov.sir.core.negocio.modelo.Anotacion local_t2Anotacion;

        local_t0Anotacion = (Anotacion) Nvl(respuesta.getT0_Anotacion(), new Anotacion());
        local_t2Anotacion = new gov.sir.core.negocio.modelo.Anotacion();

        BasicJXPathForwardDiffProcessor processor;
        gov.sir.core.web.helpers.correccion.region.model.ProcessManager localProcessManager;
        BindManager bindManager = new FolioAnotacionEditBindManager();
        localProcessManager = doExtractAuthorization_ProcessManager(request, modelPermisosList);
        DeltaAnalysisParams deltaParams = new DeltaFolioAnotacionAnalysisParams(bindManager, localProcessManager);

        AbstractFactory abstractFactory = new FolioAnotacionAbstractFactory();

        Log.getInstance().debug(AWModificarFolio.class, "@@ correcciones: sof-analysis-fw-differences (load-folio-anotacionx)");

        processor = jx_ProcessForwardDifferences(
                deltaParams.getDeltaPaths(), deltaParams.getComparators(), deltaParams.getAuthAccess(), local_t0Anotacion, local_t1Anotacion, local_t2Anotacion, abstractFactory);

        Log.getInstance().debug(AWModificarFolio.class, "@@ correcciones: eof-analysis-fw-differences (load-folio-anotacionx)");

        Map local_FwdDiffComparisonResultsMap = processor.getComparisonResultsMap(bindManager);
        session.setAttribute(PARAM__ITEM_CORRSIMPLE_FOLIOANOTACIONXEDIT_FORWARDDIFFCOMPARISONRESULTS, local_FwdDiffComparisonResultsMap);

        request.getSession().removeAttribute(MODIFICA_DEFINITIVA);
        session.setAttribute(MODIFICA_DEFINITIVA, new Boolean(!local_t0Anotacion.isTemporal() | local_t0Anotacion.isTemporalConContraparteDefinitiva()));

        // ------------------------------------------------------
        // -----------------------------------------------------------------
    }  // end-method: doEnd_Corr_PageEvent_FolioAnotacionXLoad

    // ------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // from AWCalificacion.segregarAnotacion
    // -----------------------------------------------------------------------------
    private Evento segregarAnotacion(HttpServletRequest request) {

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
        }
        String idProc = "" + turno.getIdProceso();
        webSegregacion.setIdProceso(new Integer(idProc).intValue());

        EvnCorreccion evento = new EvnCorreccion(usuarioAuriga, folio, EvnCorreccion.CONSULTAR_SEG_ENG_EXISTENTES);
        evento.setTurno(turno);
        return evento;

    }
    // -----------------------------------------------------------------------------
    // from AWCalificacion.englobar
    // -----------------------------------------------------------------------------

    private Evento englobar(HttpServletRequest request) {
        request.getSession().removeAttribute(WebKeys.FOLIO);

        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        EvnCorreccion evento = new EvnCorreccion(usuarioAuriga, EvnCorreccion.CONSULTAR_SEG_ENG_EXISTENTES);
        evento.setTurno(turno);
        evento.setUsuarioSir(usuarioSIR);
        return evento;

    }
    // -----------------------------------------------------------------------------

    private Evento
            doCorr_PageEvent_StartAnotacionCopiarAnotacion(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session;
        session = request.getSession();

        // Correcciones (Session Marks For SharedProcess)
        CopiaAnotacion local_CopiaAnotacion;
        local_CopiaAnotacion = new CopiaAnotacion();

        local_CopiaAnotacion.setProceso(CProceso.PROCESO_CORRECCIONES);

        session.setAttribute(WebKeys.COPIA_ANOTACION, local_CopiaAnotacion);

        // Build Message
        //Evento result;
        //result = segregarAnotacion( request );
        return null;

    } // end-method: doCorr_PageEvent_StartAnotacionSegregacion

    public static final String ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION
            = "ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION";

    public static final String ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID
            = "ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID";

    private void
            do_FolioEdit_AnotacionXSalvedad_SaveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION,
            ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID
        };

        session = request.getSession();

        save_PageItemsState(itemIds, request, session);

    } // :do_FolioEdit_AnotacionXSalvedad_SaveState

    private void
            do_FolioEdit_AnotacionXSalvedad_RemoveState(HttpServletRequest request) {

        HttpSession session;

        String[] itemIds = new String[]{
            ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION,
            ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID
        };

        session = request.getSession();

        delete_PageItemsState(itemIds, request, session);

    } // :do_FolioEdit_AnotacionXSalvedad_RemoveState

    // ----------------------------------------------------------------------------------------------------------------
    public static abstract class DeltaAnalysisParams {

        protected ProcessManager localProcessManager;
        protected BindManager bindManager;

        private String[] jxpath_DeltaPaths;
        private java.util.Comparator[] comparatorsToApply;
        private boolean[] jxpath_AuthAccess;

        public String[] getDeltaPaths() {
            return this.jxpath_DeltaPaths;
        } // end-method

        public java.util.Comparator[] getComparators() {
            return this.comparatorsToApply;
        } // end-method

        public boolean[] getAuthAccess() {
            return this.jxpath_AuthAccess;
        } // end-method

        public DeltaAnalysisParams(BindManager bindManager, ProcessManager localProcessManager) {
            this.bindManager = bindManager;
            this.localProcessManager = localProcessManager;
            buildParams();
        } // end-constructor

        protected void buildParams() {

            jxpath_DeltaPaths = buildDeltaPaths();
            comparatorsToApply = buildComparators();
            jxpath_AuthAccess = buildAuthAccess();

        } // end-method

        protected abstract boolean[] buildAuthAccess();

        protected abstract java.util.Comparator[] buildComparators();

        protected abstract String[] buildDeltaPaths();

    } // class

    // ----------------------------------------------------------------------------------------------------------------
    // sof:inner-class
    public static class DeltaFolioAnalysisParams extends DeltaAnalysisParams {

        public DeltaFolioAnalysisParams(BindManager bindManager, ProcessManager localProcessManager) {
            super(bindManager, localProcessManager);
        } // end-constructor

        protected boolean[] buildAuthAccess() {
            boolean[] local_AuthAccess;

            local_AuthAccess = new boolean[]{
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/oficinaOrigen/vereda/municipio/departamento/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/oficinaOrigen/vereda/municipio/idMunicipio"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/oficinaOrigen/vereda/municipio/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/oficinaOrigen/vereda/idVereda"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/oficinaOrigen/vereda/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/oficinaOrigen/idOficinaOrigen") /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */,
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/oficinaOrigen/version") //        , ProcessManager.StaticEvaluator.evaluate( localProcessManager, "field:folio:documento/oficinaOrigen/numero"                                       )
                ,
                 ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/oficinaOrigen/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/idDocumento"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/tipoDocumento/idTipoDocumento"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/tipoDocumento/nombre") // tipoDoc??
                ,
                 ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/numero"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:documento/fecha"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:lindero"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:complementacion/complementacion"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:codCatastral"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:codCatastralAnterior"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:nupre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:determinaInm"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:privMetros"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:privCentimetros"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:consMetros"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:consCentimetros"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:coeficiente"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:hectareas"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:metros"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:centimetros"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:zonaRegistral/vereda/idVereda"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:zonaRegistral/vereda/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:zonaRegistral/vereda/municipio/idMunicipio"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:zonaRegistral/vereda/municipio/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:zonaRegistral/vereda/municipio/departamento/idDepartamento"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:zonaRegistral/vereda/municipio/departamento/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:estado/idEstado"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:estado/comentario"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:tipoPredio/idPredio"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:radicacion"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:fechaApertura")
            };

            return local_AuthAccess;
        } // end-method

        protected java.util.Comparator[] buildComparators() {
            java.util.Comparator[] local_Comparators;

            local_Comparators = new java.util.Comparator[]{
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true) /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */,
                new BasicLongPlusThresholdComparator() //      , new BasicStringComparator(true,true)
                ,
                 new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicDateFormatComparator("dd/MM/yyyy"),
                new BasicStringComparator(true, true, true) // cambiado para filtrar breaks
                ,
                 new BasicStringComparator(true, true, true),
                new BasicStringComparator(false, true),
                new BasicStringComparator(false, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true, true) // cambiado para filtrar breaks
                ,
                 new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicDateFormatComparator("dd/MM/yyyy")
            };

            return local_Comparators;
        } // end-method

        protected String[] buildDeltaPaths() {
            String[] local_DeltaPaths;
            local_DeltaPaths = new String[]{
                bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento").getPath(),
                bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/departamento/nombre").getPath(),
                bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/idMunicipio").getPath(),
                bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/municipio/nombre").getPath(),
                bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/idVereda").getPath(),
                bindManager.getBinderById("field:folio:documento/oficinaOrigen/vereda/nombre").getPath(),
                bindManager.getBinderById("field:folio:documento/oficinaOrigen/idOficinaOrigen").getPath() /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */,
                bindManager.getBinderById("field:folio:documento/oficinaOrigen/version").getPath() //       , bindManager.getBinderById( "field:folio:documento/oficinaOrigen/numero"                                       ).getPath()
                ,
                 bindManager.getBinderById("field:folio:documento/oficinaOrigen/nombre").getPath(),
                bindManager.getBinderById("field:folio:documento/idDocumento").getPath(),
                bindManager.getBinderById("field:folio:documento/tipoDocumento/idTipoDocumento").getPath(),
                bindManager.getBinderById("field:folio:documento/tipoDocumento/nombre").getPath() // tipoDoc??
                ,
                 bindManager.getBinderById("field:folio:documento/numero").getPath(),
                bindManager.getBinderById("field:folio:documento/fecha").getPath(),
                bindManager.getBinderById("field:folio:lindero").getPath(),
                bindManager.getBinderById("field:folio:complementacion/complementacion").getPath(),
                bindManager.getBinderById("field:folio:codCatastral").getPath(),
                bindManager.getBinderById("field:folio:codCatastralAnterior").getPath(),
                bindManager.getBinderById("field:folio:nupre").getPath(),
                bindManager.getBinderById("field:folio:determinaInm").getPath(),
                bindManager.getBinderById("field:folio:privMetros").getPath(),
                bindManager.getBinderById("field:folio:privCentimetros").getPath(),
                bindManager.getBinderById("field:folio:consMetros").getPath(),
                bindManager.getBinderById("field:folio:consCentimetros").getPath(),
                bindManager.getBinderById("field:folio:coeficiente").getPath(),
                bindManager.getBinderById("field:folio:hectareas").getPath(),
                bindManager.getBinderById("field:folio:metros").getPath(),
                bindManager.getBinderById("field:folio:centimetros").getPath(),
                bindManager.getBinderById("field:folio:zonaRegistral/vereda/idVereda").getPath(),
                bindManager.getBinderById("field:folio:zonaRegistral/vereda/nombre").getPath(),
                bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/idMunicipio").getPath(),
                bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/nombre").getPath(),
                bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/departamento/idDepartamento").getPath(),
                bindManager.getBinderById("field:folio:zonaRegistral/vereda/municipio/departamento/nombre").getPath(),
                bindManager.getBinderById("field:folio:estado/idEstado").getPath(),
                bindManager.getBinderById("field:folio:estado/comentario").getPath(),
                bindManager.getBinderById("field:folio:tipoPredio/idPredio").getPath(),
                bindManager.getBinderById("field:folio:radicacion").getPath(),
                bindManager.getBinderById("field:folio:fechaApertura").getPath()

            };
            return local_DeltaPaths;
        } // end-method

        // aplicacion de convertors
        // construir el objeto en t1
        // hacer bind al objeto en t1
    } // end-class
    // eof:inner-class

    // ----------------------------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------
    // sof:inner-class
    public static class DeltaFolioAnotacionAnalysisParams extends DeltaAnalysisParams {

        public DeltaFolioAnotacionAnalysisParams(BindManager bindManager, ProcessManager localProcessManager) {
            super(bindManager, localProcessManager);
        } // end-constructor

        protected boolean[] buildAuthAccess() {
            boolean[] local_AuthAccess;

            local_AuthAccess = new boolean[]{
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/idMunicipio"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/idVereda"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/nombre"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/idOficinaOrigen") /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */,
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/version"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/numero") //     , ProcessManager.StaticEvaluator.evaluate( localProcessManager, "field:folio:anotacion[i]:documento/oficinaOrigen/nombre"                                       )
                ,
                 ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/oficinaInternacional"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/idDocumento"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/tipoDocumento/idTipoDocumento"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/tipoDocumento/nombre") // tipoDoc??
                ,
                 ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/numero"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:documento/fecha"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:orden"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:fechaRadicacion"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:numRadicacion"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:valor"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:naturalezaJuridica/idNaturalezaJuridica") /**
                 * @Autor: Carlos Torres
                 * @Mantis: 0012705
                 * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
                 * @Descripcion: Se incluye version
                 */
                ,
                 ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:naturalezaJuridica/version"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:comentario"),
                ProcessManager.StaticEvaluator.evaluate(localProcessManager, "field:folio:anotacion[i]:estado/idEstadoAn")
            };

            return local_AuthAccess;
        } // end-method

        protected java.util.Comparator[] buildComparators() {
            java.util.Comparator[] local_Comparators;

            local_Comparators = new java.util.Comparator[]{
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true) /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */,
                new BasicLongPlusThresholdComparator(),
                new BasicStringComparator(true, true) // , new BasicStringComparator(true,true)
                ,
                 new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true),
                new BasicDateFormatComparator("dd/MM/yyyy"),
                new BasicStringComparator(true, true),
                new BasicDateFormatComparator("dd/MM/yyyy"),
                new BasicStringComparator(true, true),
                new BasicDoublePlusThresholdComparator(0.00005d) // new BasicStringComparator(true,true) // TODO: (obtener entero, fijar entero) BasicDoublePlusThresholdComparator( 0.00005d )
                ,
                 new BasicStringComparator(true, true) /**
                 * @Autor: Carlos Torres
                 * @Mantis: 0012705
                 * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
                 * @Descripcion: Se incluye comparador long para la version
                 */
                ,
                 new BasicLongPlusThresholdComparator(),
                new BasicStringComparator(true, true),
                new BasicStringComparator(true, true)

            };

            return local_Comparators;
        } // end-method

        protected String[] buildDeltaPaths() {
            String[] local_DeltaPaths;
            local_DeltaPaths = new String[]{
                bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/nombre").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/idMunicipio").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/nombre").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/idVereda").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/vereda/nombre").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/idOficinaOrigen").getPath() /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */,
                bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/version").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaOrigen/numero").getPath() //     , bindManager.getBinderById( "field:folio:anotacion[i]:documento/oficinaOrigen/nombre"                                       ).getPath()
                ,
                 bindManager.getBinderById("field:folio:anotacion[i]:documento/oficinaInternacional").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/idDocumento").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/tipoDocumento/idTipoDocumento").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/tipoDocumento/nombre").getPath() // tipoDoc??
                ,
                 bindManager.getBinderById("field:folio:anotacion[i]:documento/numero").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:documento/fecha").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:orden").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:fechaRadicacion").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:numRadicacion").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:valor").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:naturalezaJuridica/idNaturalezaJuridica").getPath() /**
                 * @Autor: Carlos Torres
                 * @Mantis: 0012705
                 * @Requerimiento: 056_453_Modificiación_de_Naturaleza_Jurídica
                 * @Descripcion: Se incluye version
                 */
                ,
                 bindManager.getBinderById("field:folio:anotacion[i]:naturalezaJuridica/version").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:comentario").getPath(),
                bindManager.getBinderById("field:folio:anotacion[i]:estado/idEstadoAn").getPath()

            };
            return local_DeltaPaths;
        } // end-method

        // aplicacion de convertors
        // construir el objeto en t1
        // hacer bind al objeto en t1
    } // end-class
    // eof:inner-class

    // ----------------------------------------------------------------------------------------------------------------
    /**
     * @param request
     * @return
     */
    private Evento consultarUltimosPropietarios(HttpServletRequest request) {

        HttpSession session = request.getSession();

        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request
                .getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(
                WebKeys.USUARIO);

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        String numAnotacionTmp = (String) session.getAttribute(CFolio.NEXT_ORDEN_ANOTACION);
        //String numAnotacionTmp = (String) request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);

        if (numAnotacionTmp != null) {
            request.getSession().setAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL, numAnotacionTmp);
        }
        /*request.getSession().setAttribute(
 				AWCalificacion.NUM_ANOTACION_TEMPORAL,
 				);*/

        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        EvnCorreccion evento = new EvnCorreccion(usuarioAuriga, EvnCorreccion.CONSULTAR_ULTIMOS_PROPIETARIOS);
        evento.setTurno(turno);
        evento.setFolio(folio);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarPropietario(HttpServletRequest request) {
        request.getSession().removeAttribute(
                AWCalificacion.LISTA_PROPIETARIOS_FOLIO);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento guardarPropietario(HttpServletRequest request) throws AccionWebException {
        // request.getSession().removeAttribute(AWCalificacion.LISTA_PROPIETARIOS_FOLIO);

        ValidacionParametrosAnotacionCalificacionException exception = new ValidacionParametrosAnotacionCalificacionException();

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        int auxnumCiud = numCiud;

        int ciudadanosEliminados = 0;

        HttpSession session = request.getSession();
        String key = null;
        Object parametro = null;

        // Aca se valida la cantidad de registro que de verdad estan llenos.
        boolean continuarGuardado = false;
        while (!continuarGuardado && (auxnumCiud > 0)) {
            key = CFolio.ANOTACION_NUM_ID_PERSONA + (auxnumCiud - 1);

            if (request.getSession().getAttribute(key) != null
                    && !(((String) request.getSession().getAttribute(key))
                            .equals(""))) {
                continuarGuardado = true;
            }
            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null
                    && !(((String) request.getSession().getAttribute(key))
                            .equals(""))) {
                continuarGuardado = true;
            }
            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null
                    && !(((String) request.getSession().getAttribute(key))
                            .equals(""))) {
                continuarGuardado = true;
            }
            key = CFolio.ANOTACION_NOMBRES_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null
                    && !(((String) request.getSession().getAttribute(key))
                            .equals(""))) {
                continuarGuardado = true;
            }

            if (!continuarGuardado) {
                ciudadanosEliminados++;
            }
            auxnumCiud--;
        }

        String[] numPropietario = request
                .getParameterValues("ESCOGER_PROPIETARIO");

        if (numPropietario == null) {
            exception.addError("Debe Seleccionar un Ciudadano");
            throw exception;
        }

        int numSelecionados = numPropietario.length;
        String[] selPropietarios = new String[numPropietario.length];

        for (int i = 0; i < numPropietario.length; i++) {
            selPropietarios[i] = numPropietario[i];
        }

        List listaUltimosPropietarios = (List) request.getSession()
                .getAttribute(AWCalificacion.LISTA_PROPIETARIOS_FOLIO);

        int contadorSelecion = 0;
        for (int i = (numCiud - ciudadanosEliminados); i < ((numCiud - ciudadanosEliminados) + numSelecionados); i++) {
            AnotacionCiudadano anotacionciu = (AnotacionCiudadano) listaUltimosPropietarios.get(Integer
                    .valueOf(selPropietarios[contadorSelecion]).intValue());
            /*Ciudadano ciu = (Ciudadano) listaUltimosPropietarios.get(Integer
 					.valueOf(selPropietarios[contadorSelecion]).intValue());*/
            Ciudadano ciu = anotacionciu.getCiudadano();
            contadorSelecion++;
            /*
 			 * key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION+i; parametro =
 			 * request.getParameter(key); if (parametro != null)
 			 * session.setAttribute(key, parametro);
             */

            key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
            parametro = (String) ciu.getTipoDoc();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            if (!parametro.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
                key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
                parametro = (String) ciu.getDocumento();
                if (parametro != null) {
                    session.setAttribute(key, parametro);
                }
                key = CFolio.CIUDADANO_EDITABLE + i;
                Boolean par = new Boolean(true);
                parametro = par;
                if (parametro != null) {
                    session.setAttribute(key, parametro);
                }
            }

            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
            parametro = (String) ciu.getApellido1();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
            parametro = (String) ciu.getApellido2();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
            parametro = (String) ciu.getNombre();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

        }
        request.getSession().removeAttribute(
                AWCalificacion.LISTA_PROPIETARIOS_FOLIO);
        numCiud = (numCiud - ciudadanosEliminados) + numSelecionados;
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        return null;
    }

    public Object Nvl(Object source, Object replace) {
        if (null == source) {
            return replace;
        }
        return source;

    } // end-method

    /**
     * Método que permite llamar a los métodos de la acción de neegocio para
     * consultar las anotaciones de un folio
     *
     * @param request
     * @return
     */
    private Evento consultarAnotacionesFolio(HttpServletRequest request) throws AccionWebException {

        boolean datosValidos = true;
        boolean consulta = false;
        boolean consultarAnotacionesDefinitivas = false;
        boolean anotacionDelta = false;

        //se verifica si se debe consultar únicamente las definitivas
        String anotacionesDefinitivas = request.getParameter(AWPaginadorAnotaciones.ANOTACIONES_DEFINITIVAS);

        if (anotacionesDefinitivas != null && anotacionesDefinitivas.equals("TRUE")) {
            consultarAnotacionesDefinitivas = true;
        }

        request.getSession().setAttribute("folioPosibleTemporal", "true");

        //inicio de parametros para la consulta
        String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
        String idZonaRegistral = request.getParameter(CFolio.ID_ZONA_REGISTRAL);
        String inicio = request.getParameter(WebKeys.INICIO);
        String cantidad = request.getParameter(WebKeys.CANTIDAD);
        String tPaginaInicial = request.getParameter(AWPaginadorAnotaciones.PAGINA_INICIAL);
        String nombrePaginador = request.getParameter(WebKeys.NOMBRE_PAGINADOR);
        request.getSession().setAttribute(WebKeys.NOMBRE_PAGINADOR, nombrePaginador);
        String nombreResultado = request.getParameter(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        request.getSession().setAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR, nombreResultado);

        boolean consultarAnotacionesFolioTemporal = false;
        final String ANOTACIONES_FOLIO_TEMPORAL = "ANOTACIONES_FOLIO_TEMPORAL";
        String consultarAnotacionesFolioTemporalStr = request.getParameter(ANOTACIONES_FOLIO_TEMPORAL);
        if (consultarAnotacionesFolioTemporalStr != null && consultarAnotacionesFolioTemporalStr.equals("TRUE")) {
            consultarAnotacionesFolioTemporal = true;
        }

        Boolean tempdelta = (Boolean) request.getSession().getAttribute("ANOTACION_DELTA");
        if (tempdelta != null) {
            anotacionDelta = tempdelta.booleanValue();
        }

        int paginaInicial = 0;
        if (tPaginaInicial != null && !tPaginaInicial.equals("")) {
            paginaInicial = Integer.parseInt(tPaginaInicial);
        }
        Boolean temp = (Boolean) request.getSession().getAttribute(AWPaginadorAnotaciones.TIPO_CONSULTA);
        if (temp != null) {
            consulta = temp.booleanValue();
        }

        if (idMatricula == null || idZonaRegistral == null) {
            datosValidos = false;
        }
        if (inicio == null) {
            datosValidos = false;
        } else {
            try {
                Integer d = new Integer(inicio);
            } catch (Exception e) {
                datosValidos = false;
            }
        }
        if (cantidad == null) {
            datosValidos = false;
        } else {
            try {
                Integer d = new Integer(cantidad);
            } catch (Exception e) {
                datosValidos = false;
            }
        }

        if (datosValidos == true) {
            FolioPk folioID = new FolioPk();
            folioID.idMatricula = idMatricula;
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
            Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

            EvnPaginadorAnotaciones e = new EvnPaginadorAnotaciones(usuarioAuriga, folioID, new Integer(inicio).intValue(), new Integer(cantidad).intValue(), EvnPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO, usuarioNeg, consultarAnotacionesDefinitivas);
            if (consulta) {
                e.setConsulta(consulta);
            }
            if (anotacionDelta) {
                e.setGetAnotacionesConDeltadas(true);
            }

            e.setNombrePaginador(nombrePaginador);
            e.setNombreResultado(nombreResultado);
            e.setConsultarAnotacionesFolioTemporal(consultarAnotacionesFolioTemporal);
            if (paginaInicial > 0) {
                e.setPaginaInicial(paginaInicial);
            }

            return e;
        } else {
            return null;
        }

    }

    private Evento SegregarAnotacionDefinitiva(HttpServletRequest request) throws ValidacionParametrosException {

        HttpSession session = request.getSession();
        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Date fechaAnotacion = null;
        List gruposNaturalezaJuridica = (List) session.getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);

        if (gruposNaturalezaJuridica == null) {
            gruposNaturalezaJuridica = new ArrayList();
        }
        //VALIDACIONES
        ValidacionParametrosException exception = new ValidacionParametrosException();

        String sIdNatJur = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Declara variable sVersion
         */

        String sVersion = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Declara variable sVersion
         */
        if (sIdNatJur == null) {
            sIdNatJur = (String) session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change : Declara variable sVersion
             */
            sVersion = (session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER) != null) ? session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER).toString() : "1";
        }

        String sModalidad = request.getParameter(CFolio.ANOTACION_MODALIDAD);

        if (sIdNatJur == null || sIdNatJur.equals("")) {
            exception.addError("Naturaleza jurídica inválida");
        }

        if (sIdNatJur.equals("0125") && sIdNatJur.equals("0126")) {
            if (sModalidad == null || sModalidad.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar una modalidad para los códigos de naturaleza juridica 0125 y 0126 en la anotación");
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        request.getSession().removeAttribute(WebKeys.WEB_SEGREGACION);
        WebSegregacion webSegregacion = new WebSegregacion();
        String idProc = "" + turno.getIdProceso();
        webSegregacion.setIdProceso(new Integer(idProc).intValue());

        Anotacion anota = (Anotacion) request.getSession().getAttribute(ANOTACION_EDITADA);
        String sIdDocumento = (String) session.getAttribute(CFolio.ANOTACION_ID_DOCUMENTO);
        String sOrden = (String) session.getAttribute(CFolio.ANOTACION_ORDEN);

        String sNomNatJur = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        if (sNomNatJur == null) {
            sNomNatJur = (String) session.getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        }
        String sComentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        if (sComentario == null) {
            sComentario = (String) session.getAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        }
        String sValor = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        if (sValor == null) {
            sValor = (String) session.getAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        }
        String copiarComentarioSegregadas = "SI";
        String sNumeroRadicacion = request.getParameter(CFolio.ANOTACION_NUM_RADICACION);
        if (sNumeroRadicacion == null) {
            sNumeroRadicacion = (String) session.getAttribute(CFolio.ANOTACION_NUM_RADICACION);
        }
        String sFechaAnotacion = request.getParameter(CFolio.ANOTACION_FECHA_RADICACION);
        if (sFechaAnotacion == null) {
            sFechaAnotacion = (String) session.getAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        }

        if (sValor != null) {
            sValor = sValor.replaceAll(",", "");
        }

        session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, sIdNatJur);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change : Declara variable sVersion
         */
        session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, sVersion);
        session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, sNomNatJur);
        session.setAttribute(CFolio.ANOTACION_MODALIDAD, sModalidad);
        session.setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, sComentario);
        session.setAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION, copiarComentarioSegregadas);
        session.setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, sValor);
        session.setAttribute(CSolicitudRegistro.NUMERO_RADICACION, sNumeroRadicacion);
        session.setAttribute(CSolicitudRegistro.CALENDAR2, sFechaAnotacion);

        //SOLICITU DEL TURNO
        Solicitud local_Solicitud;
        local_Solicitud = turno.getSolicitud();

        if (sOrden == null || sOrden.equals("")) {
            exception.addError("Orden inválido");
        }

        if (sNumeroRadicacion == null || sNumeroRadicacion.equals("")) {
            exception.addError("El número de radicación no debe estar vacio");
        }
        if (sFechaAnotacion == null || sFechaAnotacion.equals("")) {
            exception.addError("La fecha de la anotación no debe estar vacía");
        } else {
            try {
                fechaAnotacion = DateFormatUtil.parse(sFechaAnotacion);
            } catch (ParseException e) {
                exception.addError("La fecha de la anotación no es válida");
            }
        }

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        if (ciudadanos == null || ciudadanos.size() == 0) {
            exception.addError("Ingrese por lo menos un ciudadano");
        }

        double valor = 0;
        try {
            if (sValor != null && sValor.length() > 0) {
                valor = Double.parseDouble(sValor);
            }
        } catch (NumberFormatException e) {
            exception.addError("El valor de la anotación no es un valor numérico");
        }

        //SE CREA EL DOCUMENTO CUANDO ES UNA SEGREGACIÓN EN CORRECCIONES
        Documento local_Documento = null;

        // datos de documento para la anotacion
        local_Documento = crearDocumento(request, exception, true);

        if (exception.getErrores().size() < 1) {
            //OBJETOS AUXILIARES
            Documento documento = new Documento();
            documento.setIdDocumento(sIdDocumento);
            NaturalezaJuridica natJur = new NaturalezaJuridica();
            natJur.setIdNaturalezaJuridica(sIdNatJur);
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change : Declara variable sVersion
             */
            natJur.setVersion(Long.parseLong(sVersion));
            natJur.setNombre(sNomNatJur);

            //OBJETO ANOTACION
            Anotacion anotacion = new Anotacion();
            anotacion.setDocumento(documento);
            anotacion.setNaturalezaJuridica(natJur);
            anotacion.setComentario(sComentario);
            anotacion.setOrden(sOrden);
            anotacion.setValor(valor);
            anotacion.setModalidad(sModalidad);
            anotacion.setTemporal(true);

            //OBJETO WEBANOTACION 
            WebAnotacion webAnotacion = new WebAnotacion();
            webAnotacion.setIdNaturalezaJuridica(sIdNatJur);
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             * @change : Declara variable sVersion
             */
            webAnotacion.setVersion(Long.parseLong(sVersion));
            webAnotacion.setComentario(sComentario);
            webAnotacion.setModalidad(sModalidad);
            webAnotacion.setValor("" + valor);
            if (copiarComentarioSegregadas.equals(CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION)) {
                webAnotacion.setCopiaComentario(1);
            } else {
                webAnotacion.setCopiaComentario(0);
            }
            webAnotacion.setIdWebSegeng(webSegregacion.getIdWebSegeng());
            webAnotacion.setIdSolicitud(webSegregacion.getIdSolicitud());

            //Se coloca el documento a la solicitud
            if (turno != null && turno.getSolicitud() != null) {
                SolicitudCorreccion sol = (SolicitudCorreccion) local_Solicitud;
                anotacion.setDocumento(local_Documento);

                WebDocumento webDocumento = new WebDocumento();
                webDocumento.setComentario(local_Documento.getComentario());
                webDocumento.setFecha(local_Documento.getFecha());
                if (local_Documento.getOficinaOrigen() != null) {

                    webDocumento.setIdOficinaOrigen(local_Documento.getOficinaOrigen().getIdOficinaOrigen());
                    /*
                                         *  @author Carlos Torres
                                         *  @chage   se agrega validacion de version diferente
                                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                    webDocumento.setVersion(local_Documento.getOficinaOrigen().getVersion());
                }
                //webDocumento.setIdSolicitud(local_Solicitud.getIdSolicitud());
                //webDocumento.setIdWebSegeng(local_Solicitud.getIdSolicitud());
                if (local_Documento.getTipoDocumento() != null) {
                    webDocumento.setIdTipoDocumento(local_Documento.getTipoDocumento().getIdTipoDocumento());
                }
                webDocumento.setNumero(local_Documento.getNumero());
                webDocumento.setOficinaInternacional(local_Documento.getOficinaInternacional());

                webDocumento.setIdSolicitud(webSegregacion.getIdSolicitud());
                webDocumento.setIdWebSegeng(webSegregacion.getIdWebSegeng());
                webAnotacion.setDocumento(webDocumento);
                if (local_Documento.getIdDocumento() != null) {
                    webAnotacion.setIdDocumento(local_Documento.getIdDocumento());
                }
            }

            if (turno != null) {
                //TFS 3778: El número de radicación y la fecha de la anotación
                //para las correcciones, serán digitadas por el usuario
                webAnotacion.setNumeroRadicacion(sNumeroRadicacion);
                webAnotacion.setFechaRadicacion(fechaAnotacion);
                webAnotacion.setIdAnotacionDefinitiva(anota.getIdAnotacion());
                anotacion.setIdWorkflow(turno.getIdWorkflow());
            }

            Iterator it = ciudadanos.iterator();
            while (it.hasNext()) {
                AnotacionCiudadano anot = (AnotacionCiudadano) it.next();
                anotacion.addAnotacionesCiudadano(anot);

                WebCiudadano webCiudadano = new WebCiudadano();
                webCiudadano.setTipoPersona(anot.getCiudadano().getTipoPersona());
                webCiudadano.setSexo(anot.getCiudadano().getSexo());
                webCiudadano.setApellido1(anot.getCiudadano().getApellido1());
                webCiudadano.setApellido2(anot.getCiudadano().getApellido2());
                webCiudadano.setNombre(anot.getCiudadano().getNombre());
                webCiudadano.setNumDocumento(anot.getCiudadano().getDocumento());
                webCiudadano.setTipoDocumento(anot.getCiudadano().getTipoDoc());
                webCiudadano.setTipoIntervencion(anot.getRolPersona());
                webCiudadano.setPorcentaje(anot.getParticipacion());
                webCiudadano.setPropietario("" + anot.getMarcaPropietario());
                webCiudadano.setIdSolicitud(webSegregacion.getIdSolicitud());
                webCiudadano.setIdWebSegeng(webSegregacion.getIdWebSegeng());
                webAnotacion.addCiudadano(webCiudadano);

            }

            webSegregacion.setAnotacion(webAnotacion);
            session.removeAttribute(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);
        } else {
            throw exception;
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        EvnSegregacion ev = new EvnSegregacion(usuarioAuriga, folio, turno, webSegregacion, EvnSegregacion.CONSULTA_FOLIO, usuarioNeg);

        return ev;
    }

    private Documento crearDocumento(HttpServletRequest request, ValidacionParametrosException exception, boolean validarRequerido) {

        Documento documento = null;
        Calendar fechaDocumento = null;
        HttpSession session = request.getSession();

        //SE CAPTURAN LOS DATOS DEL DOCUMENTO
        List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        String id_tipo_encabezado = request.getParameter(CDocumento.ID_TIPO_DOCUMENTO);
        String tipo_encabezado = request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO);
        String id_encabezado = request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO);
        String fecha = request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO);

        if (id_tipo_encabezado == null || id_tipo_encabezado.trim().equals("")) {
            id_tipo_encabezado = (String) session.getAttribute(CDocumento.ID_TIPO_DOCUMENTO);
        }
        if (tipo_encabezado == null || tipo_encabezado.trim().equals("")) {
            tipo_encabezado = (String) session.getAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
        }
        if (id_encabezado == null || id_encabezado.trim().equals("")) {
            id_encabezado = (String) session.getAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
        }
        if (fecha == null || fecha.trim().equals("")) {
            fecha = (String) session.getAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
        }

        Anotacion anotaCopia = (Anotacion) session.getAttribute(ANOTACION_COPIA);
        Documento docAux = null;
        OficinaOrigen ofAux = null;
        Vereda verAux = null;
        Municipio munAux = null;
        Departamento depAux = null;
        if (anotaCopia.getDocumento() != null) {
            docAux = anotaCopia.getDocumento();
            if (docAux != null && docAux.getOficinaOrigen() != null) {
                ofAux = docAux.getOficinaOrigen();
                if (ofAux != null && ofAux.getVereda() != null) {
                    verAux = ofAux.getVereda();
                    if (verAux.getMunicipio() != null) {
                        munAux = verAux.getMunicipio();
                        if (munAux.getDepartamento() != null) {
                            depAux = munAux.getDepartamento();
                        }
                    }
                }
            }
        }

        //SE CAPTURAN LOS DATOS DE LA OFICINA DÓNDE SE CREO EL DOCUMENTO.
        String nomDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String idDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        if (nomDepto == null) {
            nomDepto = depAux != null ? depAux.getNombre() : "";
        }
        if (idDepto == null) {
            idDepto = depAux != null ? depAux.getIdDepartamento() : "";
        }

        String nomMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String idMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        if (nomMunic == null) {
            nomMunic = munAux != null ? munAux.getNombre() : "";
        }
        if (idMunic == null) {
            idMunic = munAux != null ? munAux.getIdMunicipio() : "";
        }

        String nomVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        String idVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        if (nomVereda == null) {
            nomVereda = verAux != null ? munAux.getNombre() : "";
        }
        if (idVereda == null) {
            idVereda = verAux != null ? verAux.getIdVereda() : "";
        }

        if ((idVereda == null) || nomDepto.equals("") || nomMunic.equals("")) {
            List listaVeredas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS);
            idVereda = ValidacionDatosOficinaOrigen.obtenerVereda(listaVeredas, idDepto, idMunic);
        }

        String tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        if (tipo_oficina == null) {
            tipo_oficina = ofAux != null ? ofAux.getTipoOficina().getNombre() : "";
        }
        String numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        if (numero_oficina == null) {
            numero_oficina = ofAux != null ? ofAux.getNumero() : "";
        }
        String id_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        if (id_oficina == null) {
            id_oficina = ofAux != null ? ofAux.getIdOficinaOrigen() : "";
        }
        /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficinaVersion = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        if (oficinaVersion == null) {
            oficinaVersion = ofAux != null ? String.valueOf(ofAux.getVersion()) : "";
        }
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
                    fechaDocumento = darFechaAsCalendar(fecha);
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

        //SE CREA EL DOCUMENTO A PARTIR DE LA INFORMACIÓN INGRESADA
        if (tipo_encabezado != null && id_oficina != null && id_oficina.length() > 0) {
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
            oficinaOrigen.setVersion(Long.parseLong(oficinaVersion));

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

        }

        //COLOCAR LAS VARIABLES EN LA SESIÓN NUEVAMENTE
        preservarInfoBasicaDocumento(request);
        return documento;
    }

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

    /**
     * @param fechaInterfaz
     * @return
     */
    private static Calendar darFechaAsCalendar(String fechaInterfaz) {
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

            if ((calendar.get(Calendar.YEAR) == año) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                return calendar;
            }
        }

        return null;
    }

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

    private EvnCorreccion cambiarAnotacionesCorreccion(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        this.preservarInfo(request);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        if (null == turno) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }
        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        if (null == t0_folio) {
            t0_folio = new Folio();
        }
        java.util.List t0_folio_hijos = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_HIJO);
        if (null == t0_folio_hijos) {
            t0_folio_hijos = new java.util.ArrayList();
        }
        String param_Item_Folio_IdMatricula = null;
        String[] param_Selected_Folio_IdMatriculas;
        param_Selected_Folio_IdMatriculas = request.getParameterValues("FOLIOEDIT_PADRESHIJOS_HIJO__ITEMS");
        if (null == param_Selected_Folio_IdMatriculas || param_Selected_Folio_IdMatriculas.length <= 0) {
            exception.addError("id-matricula: debe seleccionar los items a eliminar");
        }
        preservarInfoAnotacionesMatriculas(request, param_Selected_Folio_IdMatriculas);
        BasicAbstractComparator comparator = new BasicStringComparator(true, true);
        List selectedFolios = new ArrayList();
        String anotacion = "";
        List foliosDerivadoHijo = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO);
        if (param_Selected_Folio_IdMatriculas != null) {
            for (java.util.Iterator iterator = t0_folio_hijos.iterator(); iterator.hasNext();) {
                Folio element = (Folio) iterator.next();
                if (null == element) {
                    continue;
                }
                String element_idMatricula = element.getIdMatricula();
                String[] matAnot;
                for (int i = 0; i < param_Selected_Folio_IdMatriculas.length; i++) {
                    param_Item_Folio_IdMatricula = param_Selected_Folio_IdMatriculas[i];
                    matAnot = param_Item_Folio_IdMatricula.split(":");
                    if (matAnot != null) {
                        if (matAnot[0] != null && !matAnot[0].equals("")) {
                            param_Item_Folio_IdMatricula = matAnot[0];
                        }
                    }
                    if (comparator.compare(param_Item_Folio_IdMatricula, element_idMatricula) == 0) {
                        anotacion = request.getParameter("A" + element_idMatricula).trim();
                        int anota = 1;
                        if (anotacion != null) {
                            try {
                                anota = Integer.valueOf(anotacion).intValue();
                                if (anota < 1) {
                                    exception.addError("La anotación debe ser mayor a 0, matrícula: " + element_idMatricula);
                                }
                            } catch (Exception e) {
                                exception.addError("Anotación inválida, matrícula: " + element_idMatricula);
                            }
                        }
                        if (foliosDerivadoHijo != null && foliosDerivadoHijo.size() > 0) {
                            FolioDerivado folioDerivado;
                            FolioDerivado folioDerToDelete;
                            FolioDerivado folioDer;
                            for (int j = 0; j < foliosDerivadoHijo.size(); j++) {
                                folioDerivado = (FolioDerivado) foliosDerivadoHijo.get(j);
                                if (folioDerivado.getIdMatricula1() != null
                                        && folioDerivado.getIdMatricula1().equals(element_idMatricula)) {

                                    folioDerToDelete = new FolioDerivado();
                                    folioDerToDelete.setIdAnotacion(folioDerivado.getIdAnotacion());
                                    folioDerToDelete.setIdAnotacion1(folioDerivado.getIdAnotacion1());
                                    folioDerToDelete.setIdMatricula(folioDerivado.getIdMatricula());
                                    folioDerToDelete.setIdMatricula1(folioDerivado.getIdMatricula1());
                                    folioDerToDelete.setHectareas(folioDerivado.getHectareas());
                                    folioDerToDelete.setMetros(folioDerivado.getMetros());
                                    folioDerToDelete.setCentimetros(folioDerivado.getCentimetros());
                                    folioDerToDelete.setArea(folioDerivado.getArea());
                                    folioDerToDelete.setLote(folioDerivado.getLote());
                                    folioDerToDelete.setPorcentaje(folioDerivado.getPorcentaje());

                                    folioDer = new FolioDerivado();
                                    folioDer.setIdAnotacion(folioDerivado.getIdAnotacion());
                                    folioDer.setIdAnotacion1(folioDerivado.getIdAnotacion1());
                                    folioDer.setIdMatricula(folioDerivado.getIdMatricula());
                                    folioDer.setIdMatricula1(folioDerivado.getIdMatricula1());
                                    folioDer.setHectareas(folioDerivado.getHectareas());
                                    folioDer.setMetros(folioDerivado.getMetros());
                                    folioDer.setCentimetros(folioDerivado.getCentimetros());
                                    folioDer.setArea(folioDerivado.getArea());
                                    folioDer.setLote(folioDerivado.getLote());
                                    folioDer.setPorcentaje(folioDerivado.getPorcentaje());
                                    folioDer.setIdAnotacion(anotacion);

                                    selectedFolios.add(new Object[]{folioDer, folioDerToDelete});
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }

        //salvedad
        String param_SalvedadFolio_Descripcion = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIODESCRIPCION);
        String param_SalvedadFolio_Id = request.getParameter(AWModificarFolio.ITEMID_FOLIOEDICION_SALVEDADFOLIOID);

        BasicConditionalValidator stage1_validator;
        stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();
        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; debe escribir un valor en la salvedad");
        }
        stage1_validator = new BasicStringMaxLengthValidatorWrapper(255);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();
        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener maximo de 1024 caracteres");
        }
        stage1_validator = new BasicStringMinLengthValidatorWrapper(30);
        stage1_validator.setObjectToValidate(param_SalvedadFolio_Descripcion);
        stage1_validator.validate();
        if (stage1_validator.getResult() != true) {
            exception.addError("folio:salvedad; la salvedad debe tener mas de 30 caracteres");
        }

        SalvedadFolio salvedadFolio = new SalvedadFolio();
        salvedadFolio.setIdSalvedad(param_SalvedadFolio_Id);
        salvedadFolio.setDescripcion(param_SalvedadFolio_Descripcion);
        salvedadFolio.setIdMatricula(t0_folio.getIdMatricula());
        salvedadFolio.setNumRadicacion(turno.getIdWorkflow());

        List salvsFolio = new ArrayList();
        salvsFolio.add(salvedadFolio);
        t0_folio.setSalvedades(salvsFolio);

        if (selectedFolios.isEmpty()) {
            exception.addError("id-matricula: no se encontro los items seleccionado");
        }
        if (exception.getErrores().size() > 0) {
            ValidacionParametrosModificarFolioException ex1 = new ValidacionParametrosModificarFolioException(
                    exception.getErrores());
            throw ex1;
        }
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        EvnCorreccion result = null;
        EvnCorreccionFolioPadresHijos resultWrap;
        resultWrap = new EvnCorreccionFolioPadresHijos(
                usuarioAuriga,
                usuarioSIR,
                t0_folio,
                turno,
                CAMBIAR_ANOTACIONES_CORRECCION
        );
        resultWrap.setActionName(EvnCorreccionFolioPadresHijos.ACTION_ADDITEM);
        resultWrap.setSources(selectedFolios);
        result = resultWrap;
        return result;
    }

    private Evento preservarInfoAnotacionesMatriculas(HttpServletRequest request, String[] matriculas) {
        if (matriculas != null) {
            for (int i = 0; i < matriculas.length; i++) {
                if (request.getParameter("A" + matriculas[i]) != null) {
                    request.getSession().setAttribute("A" + matriculas[i], request.getParameter("A" + matriculas[i]));
                }
            }
        }
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * usuarios por círculo
     *
     * @param request
     * @return evento <code>EvnAdministracionFenrir</code> con la información
     * del circulo.
     * @throws AccionWebException Caso Mantis: 7275 Autor: Ellery Robles Gómez
     */
    private EvnCorreccion anotacionesAsociadas(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        ValidacionParametrosException exception = new ValidacionParametrosException();

        String idAnotacion = request.getParameter(AWModificarFolio.ANOTACION_ID_ANOTACION);
        String idFolio = request.getParameter(AWModificarFolio.FOLIO_NUM_FOLIO);

        //Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnCorreccion evento = new EvnCorreccion(idFolio, idAnotacion, EvnCorreccion.ANOTACIONES_ASOCIADAS);
        session.setAttribute(AWModificarFolio.CONSULTA_ANOTACIONES_RESP, "1");
        session.setAttribute(AWModificarFolio.EXISTEN_ANOTACIONES_ASOCIADAS, "EXISTEN_ANOTACIONES_ASOCIADAS");
        return evento;
    }
} // end-class

