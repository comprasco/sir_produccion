package gov.sir.core.web.acciones.registro;

/**
 * @Autor: Edgar Lora
 * @Mantis: 0013038
 * @Requerimiento: 060_453
 */
import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
/**
 * @author : Carlos Torres
 * @Caso Mantis : 11604: Acta - Requerimiento No 004_589_Funcionario_Fase_
 * Entregado
 */
import java.util.Arrays;
import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.comun.EvnRespSeguridad;
import gov.sir.core.eventos.registro.EvnCalificacion;
import gov.sir.core.eventos.registro.EvnCalificacionReproduccionSellos;
import gov.sir.core.eventos.registro.EvnConfrontacion;
import gov.sir.core.eventos.registro.EvnFolio;
import gov.sir.core.eventos.registro.EvnNextOrdenAnotacion;
import gov.sir.core.eventos.registro.EvnRespCalificacion;
import gov.sir.core.eventos.registro.EvnRespCalificacionReproduccionSellos;
import gov.sir.core.eventos.registro.EvnRespConfrontacion;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.eventos.registro.EvnRespNextOrdenAnotacion;
import gov.sir.core.negocio.acciones.excepciones.ConfrontacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.registro.ANCalificacion;
import gov.sir.core.negocio.modelo.Acto;
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
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoAnotacion;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolio;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.WebDireccion;
import gov.sir.core.negocio.modelo.WebEnglobe;
import gov.sir.core.negocio.modelo.WebFolioHeredado;
import gov.sir.core.negocio.modelo.WebFolioNuevo;
import gov.sir.core.negocio.modelo.WebSegEng;
import gov.sir.core.negocio.modelo.WebSegregacion;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CConfrontacion;
import gov.sir.core.negocio.modelo.constantes.CError;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CFolioDerivado;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CQueries;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CSalvedadAnotacion;
import gov.sir.core.negocio.modelo.constantes.CSalvedadFolio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CSubtipoAtencion;
import gov.sir.core.negocio.modelo.constantes.CSubtipoSolicitud;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWSeguridad;
import gov.sir.core.web.acciones.correccion.AWCorreccion;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.EstacionInvalidaException;
import gov.sir.core.web.acciones.excepciones.MatriculasEnglobeException;
import gov.sir.core.web.acciones.excepciones.NaturalezaInvalidaException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ProcesoInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionAnotacionesCancelacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionCorreccionEncabezadoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAnotacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAnotacionEnglobeException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCancelacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAvanzarException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoEdicionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoEnglobeException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoSegregacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEdicionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEdicionCancelacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEnglobarException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosMesaControlException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosSegregacion;
import gov.sir.core.web.helpers.comun.DatosRespuestaPaginador;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.MostrarFolioHelper;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicConditionalValidator;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringNotNullOrEmptyValidatorWrapper;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.ForsetiService;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO;
import gov.sir.hermod.workflow.Processor;

import java.math.BigDecimal;
import java.text.ParseException;
/**
 * @author Cesar Ramirez
 * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
 *
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
//TODO se comento la siguiente importacion porque no es utilizada.
//import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

//import gov.sir.core.negocio.modelo.AnotacionesFolio;
/**
 * @author jfrias
 * @author mmunoz
 * @author gvillal
 * @author ddiaz
 * @author dsalas
 * @author ppabon
 */
public class AWCalificacion extends SoporteAccionWeb {

    //Popup
    /**
     * Constante que ayuda a poder pasar los atributos de la oficina del pop up
     * a la ventana principal*
     */
    public static final String ANOTACION_OFICINA_DOCUMENTO = "ANOTACION_OFICINA_DOCUMENTO";
    /**
     * Esta constante identifica el prefijo utilizado para que el popup pueda
     * regrezar los valores a la ventana que lo abrio
     */
    public static final String FOLIO_LOCACION = "FOLIO_LOCACION";
    //End: Popup

    /**
     * Esta constante identifica el numero de la anotacion que se va modificar
     */
    public static final String NUM_ANOTACION_TEMPORAL = "NUM_ANOTACION_TEMPORAL";

    /**
     * Esta constante identifica el numero de la anotacion que se va modificar
     */
    public static final String NUM_ANOTACION_TEMPORAL_CANCELACION = "NUM_ANOTACION_TEMPORAL_CANCELACION";

    /**
     * Esta constante identifica el numero de algun objeto que se quiere
     * eliminar
     */
    public static final String POSICION = "POSICION";

    public static final String LISTA_RESPUESTAS_USUARIO = "LISTA_RESPUESTAS_USUARIO";
    public static final String VISUALIZAR_PDF = "VISUALIZAR_PDF";
    /**
     * Esta constante identifica el numero de la posicion de la anotacion que se
     * esta editando
     */
    public static final String POSICIONANOTACION = "POSICIONANOTACION";

    /**
     * Esta constante identifica el numero de la posicion de la anotacion que se
     * esta editando
     */
    public static final String PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION = "PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION";

    //ACCIONES
    private String accion;
    public static final String CALIFICAR_FOLIO = "CALIFICAR_FOLIO";
    public static final String CONFIRMAR = "CONFIRMAR";
    public static final String PRESERVAR_INFO = "PRESERVAR_INFO";
    public static final String AGREGAR_CIUDADANO = "AGREGAR_CIUDADANO";
    public static final String ELIMINAR_CIUDADANO_EDICION = "ELIMINAR_CIUDADANO_EDICION";
    public static final String AGREGAR_ANOTACION = "AGREGAR_ANOTACION";
    public static final String CANCELAR_EDICION = "CANCELAR_EDICION";
    public static final String CANCELAR_EDICION_CANCELACION = "CANCELAR_EDICION_CANCELACION";
    public static final String CANCELAR_CANCELACION = "CANCELAR_CANCELACION";
    public static final String EDITAR_ANOTACION = "EDITAR_ANOTACION";
    public static final String EDITAR_CANCELACION = "EDITAR_CANCELACION";
    public static final String ELIMINAR_ANOTACION_TEMPORAL = "ELIMINAR_ANOTACION_TEMPORAL";
    public static final String GRABAR_EDICION = "GRABAR_EDICION";
    public static final String GRABAR_EDICION_CANCELACION = "GRABAR_EDICION_CANCELACION";
    public final static String REFRESCAR_EDICION = "REFRESCAR_EDICION";
    public final static String VALIDAR_CIUDADANO_EDICION = "VALIDAR_CIUDADANO_EDICION";
    public static final String AGREGAR_ANOTACION_CREACION_FOLIO = "AGREGAR_ANOTACION_CREACION_FOLIO";
    public static final String ELIMINAR_ANOTACION = "ELIMINAR_ANOTACION";
    public static final String ELIMINAR_ANOTACION_CREACION_FOLIO = "ELIMINAR_ANOTACION_CREACION_FOLIO";
    public static final String PRESERVAR_INFO_ANOTACION = "PRESERVAR_INFO_ANOTACION";
    public static final String ACEPTAR_ANOTACIONES_FOLIO = "ACEPTAR_ANOTACIONES_FOLIO";
    public static final String ISFIRMA = "ISFIRMA";
    public final static String ENGLOBAR_MATRICULAS = "ENGLOBAR_MATRICULAS";
    public final static String CARGAR_ANOTACIONES = "CARGAR_ANOTACIONES";
    public final static String ENGLOBAR = "ENGLOBAR";
    public final static String AGREGAR_COMPLEMENTACION = "AGREGAR_COMPLEMENTACION";
    public final static String GRABACION_TEMPORAL_ANOTACION = "GRABACION_TEMPORAL_ANOTACION";
    public final static String ELIMINAR_COMPLEMENTACION = "ELIMINAR_COMPLEMENTACION";
    public final static String TERMINAR_ANOTACIONES_ENGLOBE = "TERMINAR_ANOTACIONES_ENGLOBE";
    public final static String SEGREGACION_ANOTACION = "SEGREGACION_ANOTACION";
    public final static String SEGREGACION_HERENCIA = "SEGREGACION_HERENCIA";
    public final static String CANCELAR_ANOTACION = "CANCELAR_ANOTACION";
    public final static String CANCELAR = "CANCELAR";
    public final static String CREAR_ANOTACION = "CREAR_ANOTACION";
    public final static String REFRESCAR_ANOTACION = "REFRESCAR_ANOTACION";
    public final static String REFRESCAR_CANCELACION = "REFRESCAR_CANCELACION";
    public final static String REFRESCAR_EDICION_CANCELACION = "REFRESCAR_EDICION_CANCELACION";
    public final static String REFRESCAR_ANOTACION_SEGREGACION = "REFRESCAR_ANOTACION_SEGREGACION";
    public final static String PRESERVAR_INFO_CANCELACION = "PRESERVAR_INFO_CANCELACION";
    public final static String ENVIAR_CORRECCION_ENCABEZADO = "ERROR_ENCABEZADO";
    public final static String ENVIAR_MAYOR_VALOR = "ENVIAR_MAYOR_VALOR";
    public static final String CREAR_FOLIO_ENGLOBE = "CREAR_FOLIO_ENGLOBE";
    public final static String GRABAR_ANOTACIONES_TEMPORAL_FOLIO = "GRABAR_ANOTACIONES_TEMPORAL_FOLIO";
    public static final String TOMAR_TURNO = "TOMAR_TURNO";
    public static final String VALIDAR_TURNO_PARA_CALIFICACION = "VALIDAR_TURNO_PARA_CALIFICACION";
    public static final String DESASOCIAR_FOLIOS = "DESASOCIAR_FOLIOS";
    public static final String ACEPTAR_CORRECCION = "ACEPTAR_CORRECCION";
    public static final String NEGAR_CORRECCION = "NEGAR_CORRECCION";
    public static final String AGREGAR_FOLIO_DERIVADO = "AGREGAR_FOLIO_DERIVADO";
    public static final String SEGREGAR_MASIVO = "SEGREGAR_MASIVO";
    public static final String AVANZAR = "AVANZAR";
    public static final String CORRECCION = "CORRECCION";
    public static final String SIN_SELECCIONAR = "SIN_SELECCIONAR";
    public static final String SUSPENSION_DEL_TRAMITE_REGISTRO_PREVENCION = "SUSPENSION_DEL_TRAMITE_REGISTRO_PREVENCION";
    public static final String SUSPENSION_TEMPORAL_DEL_TRAMITE_REGISTRO = "SUSPENSION_TEMPORAL_DEL_TRAMITE_REGISTRO";
    public static final String MICRO = "MICRO";
    public static final String DIGITACION = "DIGITACION";
    public static final String ESPECIALIZADO = "ESPECIALIZADO";
    public static final String ANT_SISTEMA = "ANT_SISTEMA";
    public static final String DEVOLUCION = "DEVOLUCION";
    public static final String RESPUESTAWF = "RESPUESTAWF";
    public static final String FIRMA_REGISTRO_CONFIRMAR = "FIRMA_REGISTRO_CONFIRMAR";
    public static final String REVISOR_CONFRONTACION_CONFIRMAR = "REVISOR_CONFRONTACION_CONFIRMAR";
    public static final String REVISOR_CONFRONTACION_DEVOLVER = "REVISOR_CONFRONTACION_DEVOLVER";
    public static final String FIRMA_REGISTRO_DEVOLVER = "FIRMA_REGISTRO_DEVOLVER";
    public static final String MESA_CONTROL_CONFIRMAR = "MESA_CONTROL_CONFIRMAR";
    public static final String ENTREGA_CONFIRMAR = "ENTREGA_CONFIRMAR";
    public static final String ELIMINAR_FOLIO_DERIVADO = "ELIMINAR_FOLIO_DERIVADO";
    public static final String VER_SALVEDAD = "VER_SALVEDAD";
    public static final String REGRESAR_CALIFICACION = "REGRESAR_CALIFICACION";
    public static final String CARGAR_DESCRIPCION_NATURALEZA = "CARGAR_DESCRIPCION_NATURALEZA";
    public static final String APROBAR_MAYOR_VALOR = "APROBAR_MAYOR_VALOR";
    public static final String REGISTRAR_PAGO_EXCESO = "REGISTRAR_PAGO_EXCESO";
    public static final String IMPRIMIR_FORMULARIO_CALIFICACION = "IMPRIMIR_FORMULARIO_CALIFICACION";
    public static final String IMPRIMIR_FOLIO_TEMPORAL = "IMPRIMIR_FOLIO_TEMPORAL";
    public static final String CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO = "CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO";
    public static final String ANOTACION_A_EDITAR = "ANOTACION_A_EDITAR";
    public static final String DEVOLVER = "DEVOLVER";
    public static final String INSCRIPCION_PARCIAL = "INSCRIPCION_PARCIAL";
    //daniel SIR-61
    public static final String DEVOLVER_FIRMA_TESTAMENTO = "DEVOLVER_FIRMA_TESTAMENTO";
    public static final String LISTA_DIRECCIONES_TEMPORALES = "LISTA_DIRECCIONES_TEMPORALES";

    public static final String CARGAR_DESCRIPCION_NATURALEZA_CANCELACION = "CARGAR_DESCRIPCION_NATURALEZA_CANCELACION";
    public static final String CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION = "CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION";

    public static final String CARGAR_DESCRIPCION_NATURALEZA_SEGREGACION = "CARGAR_DESCRIPCION_NATURALEZA_SEGREGACION";
    public static final String CARGAR_DESCRIPCION_NATURALEZA_ENGLOBE = "CARGAR_DESCRIPCION_NATURALEZA_ENGLOBE";
    public static final String CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION = "CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION";

    // -----------------------------------------------------------------------------------------
    // reimpresion-sellos :: action-StringKey
    public static final String CALIFICACION_REIMPRESIONSELLOS_SEARCH_ACTION = "CALIFICACION_REIMPRESIONSELLOS_SEARCH_ACTION";
    public static final String CALIFICACION_REIMPRESIONSELLOS_RESET_ACTION = "CALIFICACION_REIMPRESIONSELLOS_RESET_ACTION";
    public static final String CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_ACTION = "CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_ACTION";
    public static final String CALIFICACION_REIMPRESIONSELLOS_MATRICULA = "CALIFICACION_REIMPRESIONSELLOS_MATRICULA";
    // -----------------------------------------------------------------------------------------

    /**
     * Constante donde se guarda si hay o no actos vencidos
     */
    public static final String HAY_ACTOS_VENCIDOS = "HAY_ACTOS_VENCIDOS";

    /**
     * Constante donde se guarda si se refresco la edicion de una anotacion.
     */
    public static final String HAY_REFRESCO = "HAY_REFRESCO";

    /**
     * Constante para el manejo de notas informativas cuando se delega hacia la
     * fase de confrontacion.
     */
    public static final String NOTA_INFORMATIVA_CONFRONTACION = "NOTA_INFORMATIVA_CONFRONTACION";

    /**
     * Constante para el manejo de notas informativas cuando se delega hacia la
     * fase de correccion de encabezado.
     */
    public static final String NOTA_INFORMATIVA_ENCABEZADO = "NOTA_INFORMATIVA_ENCABEZADO";

    /**
     * Constante para el manejo de notas informativas cuando solicita
     * informacion de folios a otras dependencias
     */
    public static final String NOTA_INFORMATIVA_OTRAS_DEPENDENCIAS = "NOTA_INFORMATIVA_OTRAS_DEPENDENCIAS";

    public static final String GUARDAR_ANOTACIONES_TEMPORALES = "GUARDAR_ANOTACIONES_TEMPORALES";

    //Varios ciudadanos
    /**
     * HELPER DE VARIOS CIUDADANOS
     */
    public static final int DEFAULT_NUM_CIUDADANOS_TABLA = 2;
    /**
     * Constante que indica que se van a agregar varios ciudadanos a una
     * anotacion*
     */
    public static final String AGREGAR_VARIOS_CIUDADANOS = "AGREGAR_VARIOS_CIUDADANOS";
    /**
     * Constante que indica que se va a agregar un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS";
    /**
     * Constante que indica que se va a remover un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS";
    /**
     * Constante que indica que se usa en el request para obtener el numero de
     * registros de la tabla de adicion de ciudadanos a la anotacion.*
     */
    public static final String NUM_REGISTROS_TABLA_CIUDADANOS = "NUM_REGISTROS_TABLA_CIUDADANOS";
    /**
     * Constante que indica que se va a remover un ciudadano de la anotacion
     */
    public static final String ELIMINAR_CIUDADANO_ANOTACION = "ELIMINAR_CIUDADANO_ANOTACION";
    //End: Varios ciudadanos

    //	Varios ciudadanos
    public static final String AGREGAR_VARIOS_CIUDADANOS_EDICION = "AGREGAR_VARIOS_CIUDADANOS_EDICION";
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
    /**
     * Constante que indica que se va a validar la existencia de un ciudadano en
     * anotaciones*
     */
    public static final String VALIDAR_CIUDADANO = "VALIDAR_CIUDADANO";

    //End: Varios ciudadanos
    //Varios ciudadanos segregacion
    /**
     * Constante que indica que se van a agregar varios ciudadanos a una
     * anotacion*
     */
    public static final String AGREGAR_VARIOS_CIUDADANOS_SEGREGACION = "AGREGAR_VARIOS_CIUDADANOS_SEGREGACION";
    /**
     * Constante que indica que se va a agregar un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION";
    /**
     * Constante que indica que se va a remover un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION";
    /**
     * Constante que indica que se va a remover un ciudadano de la anotacion
     */
    public static final String ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION = "ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION";
    //End: Varios ciudadanos segregacion

    //	Varios ciudadanos cancelacion
    /**
     * Constante que indica que se van a agregar varios ciudadanos a una
     * anotacion*
     */
    public static final String AGREGAR_VARIOS_CIUDADANOS_CANCELACION = "AGREGAR_VARIOS_CIUDADANOS_CANCELACION";
    /**
     * Constante que indica que se va a agregar un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION";
    /**
     * Constante que indica que se va a remover un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION";
    /**
     * Constante que indica que se va a remover un ciudadano de la anotacion
     */
    public static final String ELIMINAR_CIUDADANO_ANOTACION_CANCELACION = "ELIMINAR_CIUDADANO_ANOTACION_CANCELACION";
    /**
     * Constante que indica que se va a validar la existencia de un ciudadano en
     * anotaciones*
     */
    public static final String VALIDAR_CIUDADANO_CANCELACION = "VALIDAR_CIUDADANO_CANCELACION";
    //End: Varios ciudadanos CANCELACION

//	Varios ciudadanos cancelacion
    /**
     * Constante que indica que se van a agregar varios ciudadanos a una
     * anotacion*
     */
    public static final String AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION = "AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION";
    /**
     * Constante que indica que se va a agregar un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION";
    /**
     * Constante que indica que se va a remover un registro en la tabla de
     * agragar ciudadanos*
     */
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION";
    /**
     * Constante que indica que se va a remover un ciudadano de la anotacion
     */
    public static final String ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION = "ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION";
    /**
     * Constante que indica que se va a validar la existencia de un ciudadano en
     * anotaciones*
     */
    public static final String VALIDAR_CIUDADANO_EDICION_CANCELACION = "VALIDAR_CIUDADANO_EDICION_CANCELACION ";
    //End: Varios ciudadanos CANCELACION

    /**
     * Constante que identifica la accion de obtener bloqueo foliosr.
     */
    public static final String OBTENER_BLOQUEO_FOLIOS_FIRMA = "OBTENER_BLOQUEO_FOLIOS_FIRMA";

    /**
     * Constante que identifica la accion de cerrar un folio.
     */
    public static final String CERRAR_FOLIO = "CERRAR_FOLIO";

    /**
     * Constante que identifica la razon al cerrar un folio.
     */
    public static final String RAZON_CERRAR_FOLIO = "RAZON_CERRAR_FOLIO";

    public static final String MOSTRAR = "MOSTRAR";

    /**
     * Constante para delegar un turno a la fase de confrontacion.
     */
    public static final String CONFRONTACION = "CONFRONTACION";

    /**
     * Constante donde se guarda la cantidad a pagar en mayor valor.
     */
    public static final String PAGO_MAYOR_VALOR = "PAGO_MAYOR_VALOR";
    public static final String PAGO_MAYOR_VALOR_DERECHOS = "PAGO_MAYOR_VALOR_DERECHOS";
    public static final String PAGO_MAYOR_VALOR_CERTIFICADOS = "PAGO_MAYOR_VALOR_CERTIFICADOS";
    public static final String PAGO_MAYOR_VALOR_IMPUESTOS = "PAGO_MAYOR_VALOR_IMPUESTOS";

    /**
     * Constante donde se guarda la razon a pagar en mayor valor.
     */
    public static final String RAZON_MAYOR_VALOR = "RAZON_MAYOR_VALOR";

    /**
     * Constante donde se guarda la cantidad a devolver.
     */
    public static final String PAGO_EXCESO = "PAGO_EXCEO";
    public static final String PAGO_EXCESO_DERECHOS = "PAGO_EXCESO_DERECHOS";
    public static final String PAGO_EXCESO_IMPUESTOS = "PAGO_EXCESO_DERECHOS";

    /**
     * Constante donde se guarda la razon de la devolucion.
     */
    public static final String RAZON_DEVOLUCION = "RAZON_DEVOLUCION";

    public static final String PLAZO_VENCIMIENTO = "PLAZO_VENCIMIENTO";

    //Constantes inscripcion parcial
    /**
     * Constante donde se guardan los folios selecccionados para ser inscritos
     * parcialmente.
     */
    public static final String FOLIOS_INSCRIPCION_PARCIAL = "FOLIOS_INSCRIPCION_PARCIAL";

    /////////////
    public static final String ACTUALIZAR_FOLIO_COD_CATASTRAL = "ACTUALIZAR_FOLIO_COD_CATASTRAL";
    public static final String ACTUALIZAR_FOLIO_CABIDA_LINDEROS = "ACTUALIZAR_FOLIO_CABIDA_LINDEROS";
    /**
     * @author: Cesar Ramirez
     * @change: 1245.HABILITAR.TIPO.PREDIO Se agrega una nueva constante para
     * actualizar el tipo de predio.
     *
     */
    public static final String ACTUALIZAR_FOLIO_TIPO_PREDIO = "ACTUALIZAR_FOLIO_TIPO_PREDIO";
    public static final String ACTUALIZAR_FOLIO_DIRECCIONES = "ACTUALIZAR_FOLIO_DIRECCIONES";
    public static final String ACTUALIZAR_COMPLEMENTACION = "ACTUALIZAR_COMPLEMENTACION";

    public static final String AGREGAR_DIRECCION_CALIFICACION = "AGREGAR_DIRECCION_CALIFICACION";
    public static final String ELIMINAR_DIRECCION_CALIFICACION = "ELIMINAR_DIRECCION_CALIFICACION";
    public static final String ELIMINAR_DIRECCION_DEFINITIVA_CALIFICACION = "ELIMINAR_DIRECCION_DEFINITIVA_CALIFICACION";

    //public static final String HABILITAR_EDICION_NOMENCLATURA = "HABILITAR_EDICION_NOMENCLATURA";
    public static final String HABILITAR_EDICION_COD_CATASTRAL = "HABILITAR_EDICION_COD_CATASTRAL";
    public static final String HABILITAR_EDICION_DIRECCION = "HABILITAR_EDICION_DIRECCION";
    public static final String HABILITAR_EDICION_LINDEROS = "HABILITAR_EDICION_LINDEROS";
    public static final String HABILITAR_EDICION_LINDEROS_DEFINIDOS = "HABILITAR_EDICION_LINDEROS_DEFINIDOS";
    public static final String HABILITAR_EDICION_COMPLEMENTACION = "HABILITAR_EDICION_COMPLEMENTACION";

    /**
     * ************************************************
     */
    /*             ELIMINAR SAS                        */
    /**
     * ************************************************
     */
    public static final String DELEGAR_DIGITACION_MASIVA = "DELEGAR_DIGITACION_MASIVA";

    public static final String AVANZAR_FIRMA_REGISTRADOR = "AVANZAR_FIRMA_REGISTRADOR";

    public static final String ENTREGAR_REGISTRO = "ENTREGAR_REGISTRO";

    /**
     * JAlcazar 13/07/2009 Constante que indica que se realizara el proceso de
     * entrega a mas de un turno
     */
    public static final String ENTREGAR_REGISTRO_MULT = "ENTREGAR_REGISTRO_MULT";

    /**
     * Constante que indica que se van a agregar consultar los ultimos
     * propietarios de una folio
     */
    public static final String GET_ULTIMOS_PROPIETARIOS = "GET_ULTIMOS_PROPIETARIOS";

    /**
     * Constante que indica que se van a agregar consultar los ultimos
     * propietarios de una folio
     */
    public static final String LISTA_PROPIETARIOS_FOLIO = "LISTA_PROPIETARIOS_FOLIO";

    /**
     * Constante que indica que se van a agregar al la lista de ciudadanos los
     * propietarios de una folio
     */
    public static final String GUARDAR_PROPIETARIOS = "GUARDAR_PROPIETARIOS";

    /**
     * Constante que indica que se cancela el proceso de consultar los ultimos
     * propietarios
     */
    public static final String CANCELAR_PROPIETARIOS = "CANCELAR_PROPIETARIOS";
    
    /**
     * Constante que indica que se va realizar el rol revisor confrontaci?n correctiva
     */
    public static final String REVISOR_CONFRONTACION = "REVISOR_CONFRONTACION";
    
    /**
     * Constante que identifica la cantidad de notas informativas de un turno
     */
    public static final String NOTAS_INFORMATIVAS_INICIALES = "NOTAS_INFORMATIVAS_INICIALES";

    /**
     * Crea una instancia de la accion web
     */
    public AWCalificacion() {
        super();
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
     */
    public Evento perform(HttpServletRequest request)
            throws AccionWebException {
        borrarDatosPreviewFolio(request);
        accion = request.getParameter(WebKeys.ACCION);
        // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informatica Ltda.
        // Cambio para asolucionar requerimiento 117 - Incidencia Mantis 02399.
        // Se cambio la variable estatica por atributos en sesion y en el evento EvnPaginadorAnotaciones.

        // El siguiente bloque de codigo es para verificar el numero de anotaciones desde la cual el usuario quiere visualizar.
        // Si se digita un numero negativo, mayor que el numero de anotaciones del folio o letras se mostraran todas las anotaciones.
        String numAnotacionesDesde = request.getParameter(WebKeys.CAMPO_NUM_ANOTACIONES_DESDE);
        //String numAnotacionesTotal = request.getParameter(WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL);
        if (numAnotacionesDesde == null) {
            request.getSession().setAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_DESDE, 0);
            request.getSession().setAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL, 0);
            //AnotacionesFolio.setNumAnotacionesFolio((new Integer(0).intValue()));
        } else {
            try {
                int tNumAnotaciones = Integer.parseInt(request.getParameter(WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL));
                if (tNumAnotaciones > 0) {
                    request.getSession().setAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL, tNumAnotaciones);
                    //AnotacionesFolio.setNumAnotacionesTotalV(tNumAnotaciones);
                } else {
                    request.getSession().setAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL, 0);
                    //AnotacionesFolio.setNumAnotacionesTotalV(0);
                }
                //AnotacionesFolio.setNumAnotacionesFolio(Integer.parseInt(numAnotacionesDesde));
                //if(AnotacionesFolio.getNumAnotacionesFolio()<1 || AnotacionesFolio.getNumAnotacionesFolio()>tNumAnotaciones){
                int numAnotacionesDesdeI = Integer.parseInt(numAnotacionesDesde);
                if (numAnotacionesDesdeI < 1 || numAnotacionesDesdeI > tNumAnotaciones) {
                    request.getSession().setAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_DESDE, 0);
                    //AnotacionesFolio.setNumAnotacionesFolio(0);
                } else {
                    request.getSession().setAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_DESDE, numAnotacionesDesdeI - 1);
                    //AnotacionesFolio.setNumAnotacionesFolio(AnotacionesFolio.getNumAnotacionesFolio()-1);
                }
            } catch (NumberFormatException eNum) {
                request.getSession().setAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_DESDE, 0);
                request.getSession().setAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL, 0);
                //AnotacionesFolio.setNumAnotacionesFolio(0);
                //AnotacionesFolio.setNumAnotacionesTotalV(0);
            }

        }

        if (request.getSession().getAttribute(WebKeys.VISTA_PARA_REGRESAR) != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, request.getSession().getAttribute(WebKeys.VISTA_PARA_REGRESAR));
            request.getSession().removeAttribute(WebKeys.VISTA_PARA_REGRESAR);
        }

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        if (accion.equals(MOSTRAR)) {
            return mostrar(request);
        }
        if (accion.equals(VISUALIZAR_PDF)) {
            return prepararPDF(request);
        }
        if (accion.equals(CALIFICAR_FOLIO)) {
            return calificarFolio(request);
        } else if (accion.equals(PRESERVAR_INFO)) {
            return preservarInfo(request);
        } else if (accion.equals(CONFIRMAR)) {
            return confirmar(request);
        } // -------------------------------------------------------------------------
        //  reimpresion-sellos :: proxy --------------------------------------------
        else if (CALIFICACION_REIMPRESIONSELLOS_SEARCH_ACTION.equals(accion)) {
            return doCalificacion_ReimpresionSellos_Search_Action(request);
        } else if (CALIFICACION_REIMPRESIONSELLOS_RESET_ACTION.equals(accion)) {
            return doCalificacion_ReimpresionSellos_Reset_Action(request);
        } else if (CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_ACTION.equals(accion)) {
            return doCalificacion_ReimpresionSellos_PrintSelected_Action(request);
        } else if (CALIFICACION_REIMPRESIONSELLOS_MATRICULA.equals(accion)) {
            return imprimirSellosMatricula(request);
        } // -------------------------------------------------------------------------
        /**
         * ******************* Creacion anotaciones
         * ***********************************
         */
        //        else if (accion.equals(AGREGAR_RESOLUCION_RECURSO)) {
        //            return agregarResolucion(request);
        //        }
        else if (accion.equals(CREAR_ANOTACION)) {
            return crearAnotacion(request);

        } else if (accion.equals(REFRESCAR_ANOTACION)) {
            return refrescarAnotacion(request);
        } else if (accion.equals(VALIDAR_CIUDADANO)) {
            return validarCiudadano(request);
        } else if (accion.equals(AGREGAR_CIUDADANO)) {
            return agregarCiudadano(request);
        } else if (accion.equals(AGREGAR_ANOTACION)) {
            return agregarAnotacion(request);
        } else if (accion.equals(ELIMINAR_ANOTACION)) {
            return eliminarAnotacion(request);
        } else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO)) {
            return cargarDescripcionNaturaleza(request);
        } else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA_EDITAR_ANOTACION)) {
            return cargarDescripcionNaturaleza(request);
        } else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA_CANCELACION)) {
            return cargarDescripcionNaturaleza(request);
        } else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION)) {
            return cargarDescripcionNaturaleza(request);
        } else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA)) {
            return cargarDescripcionNaturaleza(request);
        } else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA_SEGREGACION)) {
            return cargarDescripcionNaturalezaCalificacion(request);
        } else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA_ENGLOBE)) {
            return cargarDescripcionNaturalezaCalificacion(request);

            //Varios ciudadanos
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS)) {
            return agregarVariosCiudadanos(request, accion);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION)) {
            return eliminarCiudadano(request);
        } else if (accion.equals(GET_ULTIMOS_PROPIETARIOS)) {
            return consultarUltimosPropietarios(request);
        } else if (accion.equals(CANCELAR_PROPIETARIOS)) {
            return cancelarPropietario(request);
        } else if (accion.equals(GUARDAR_PROPIETARIOS)) {
            return guardarPropietario(request);
            //End: Varios ciudadanos

            /**
             * ******************** end Creacion anotaciones
             * *******************************
             */
            /**
             * ******************* Creacion de cancelacion anotaciones
             * *********************
             */
        } else if (accion.equals(CANCELAR_CANCELACION)) {
            return cancelarCancelacion(request);
        } else if (accion.equals(CANCELAR_ANOTACION)) {
            return cancelarAnotacion(request);
        } else if (accion.equals(CANCELAR)) {
            return cancelar(request);
        } else if (accion.equals(REFRESCAR_CANCELACION)) {
            return refrescarCancelacion(request);
            //Varios ciudadanos cancelacion
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_CANCELACION)) {
            return agregarVariosCiudadanos(request, accion);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION_CANCELACION)) {
            return eliminarCiudadano(request);
        } else if (accion.equals(GUARDAR_ANOTACIONES_TEMPORALES)) {
            return guardarAnotacionesCancelacion(request);
        } else if (accion.equals(VALIDAR_CIUDADANO_CANCELACION)) {
            return validarCiudadano(request);
            //End: Varios ciudadanos cancelacion

            /**
             * ******************* end Creacion de cancelacion anotaciones
             * *********************
             */
            /**
             * ******************* Edicion anotaciones
             * ***********************************
             */
        } else if (accion.equals(EDITAR_ANOTACION)) {
            return editarAnotacion(request);
        } else if (accion.equals(GRABAR_EDICION)) {
            return grabarEdicion(request);
        } else if (accion.equals(REFRESCAR_EDICION)) {
            return refrescarEdicion(request);
        } else if (accion.equals(VALIDAR_CIUDADANO_EDICION)) {
            return validarCiudadano(request);
        } else if (accion.equals(CANCELAR_EDICION)) {
            return cancelarEdicion(request);
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_EDICION)) {
            return agregarVariosCiudadanosEdicion(request);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_EDICION)) {
            return eliminarCiudadanoEdicion(request);

            /**
             * ******************** end edicion anotaciones
             * *******************************
             */
            /**
             * ******************* Edicion cancelaciones
             * ***********************************
             */
        } else if (accion.equals(EDITAR_CANCELACION)) {
            return editarCancelacion(request);
        } else if (accion.equals(GRABAR_EDICION_CANCELACION)) {
            return grabarEdicionCancelacion(request);
        } else if (accion.equals(CANCELAR_EDICION_CANCELACION)) {
            return cancelarEdicionCancelacion(request);
        } else if (accion.equals(REFRESCAR_EDICION_CANCELACION)) {
            return refrescarEdicionCancelacion(request);
            //Varios ciudadanos cancelacion
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION)) {
            return agregarVariosCiudadanosEdicion(request);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION)) {
            return eliminarCiudadanoEdicion(request);
        } else if (accion.equals(VALIDAR_CIUDADANO_EDICION_CANCELACION)) {
            return validarCiudadano(request);
            //End: Varios ciudadanos cancelacion

            /**
             * ******************** end edicion cancelaciones
             * *******************************
             */
            /**
             * ******************* anotaciones segregacion
             * ***********************************
             */
        } else if (accion.equals(REFRESCAR_ANOTACION_SEGREGACION)) {
            return refrescarAnotacion(request);
        } else if (accion.equals(SEGREGACION_ANOTACION)) {
            return segregarAnotacion(request);
            //Varios ciudadanos segregacion
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)) {
            return agregarVariosCiudadanos(request, accion);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_SEGREGACION)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION_SEGREGACION)) {
            return eliminarCiudadano(request);
            //End: Varios ciudadanos segregacion

            /**
             * ******************* end anotaciones segregacion
             * *********************************
             */
        } else if (accion.equals(PRESERVAR_INFO_ANOTACION)) {
            return preservarInfoAnotacion(request);
        } else if (accion.equals(PRESERVAR_INFO_CANCELACION)) {
            return preservarInfoCancelacion(request);
        } else if (accion.equals(ACEPTAR_ANOTACIONES_FOLIO)) {
            return aceptarAnotacionesFolio(request);
        } else if (accion.equals(ELIMINAR_ANOTACION_TEMPORAL)) {
            return eliminarAnotacionTemporal(request);
        } else if (accion.equals(AGREGAR_ANOTACION_CREACION_FOLIO)) {
            return agregarAnotacion(request);
        } else if (accion.equals(ELIMINAR_ANOTACION_CREACION_FOLIO)) {
            return eliminarAnotacion(request);
        } else if (accion.equals(ENGLOBAR_MATRICULAS)) {
            return matriculasEnglobe(request);
        } else if (accion.equals(CARGAR_ANOTACIONES)) {
            return cargarAnotaciones(request);
        } else if (accion.equals(ENGLOBAR)) {
            return englobar(request);
        } else if (accion.equals(AGREGAR_COMPLEMENTACION)) {
            return agregarComplementacionEnglobe(request);
        } else if (accion.equals(GRABACION_TEMPORAL_ANOTACION)) {
            return grabacionTemporalEnglobe(request);
        } else if (accion.equals(ELIMINAR_COMPLEMENTACION)) {
            return eliminarComplementacionEnglobe(request);
        } else if (accion.equals(TERMINAR_ANOTACIONES_ENGLOBE)) {
            return terminarAnotacionesEnglobe(request);
        } else if (accion.equals(SEGREGACION_HERENCIA)) {
            return segregarHerencia(request);
        } else if (accion.equals(AVANZAR)) {
            return avanzar(request);
        } else if (accion.equals(AVANZAR_FIRMA_REGISTRADOR)) {
            return avanzarFirmaRegistrador(request);
        } else if (accion.equals(ENVIAR_CORRECCION_ENCABEZADO)) {
            return enviarCorreccionEncabezado(request);
        } else if (accion.equals(AGREGAR_FOLIO_DERIVADO)) {
            return agregarFolioDerivado(request);
        } else if (accion.equals(SEGREGAR_MASIVO)) {
            return segregarMasivo(request);
        } else if (accion.equals(TOMAR_TURNO)) {
            return tomarTurno(request);
        } else if (accion.equals(VALIDAR_TURNO_PARA_CALIFICACION) || accion.equals(ENVIAR_MAYOR_VALOR)) {
            return validarTurnoParaCalificacion(request);
        } else if (accion.equals(DESASOCIAR_FOLIOS)) {
            return desasociarFolio(request);
        } else if (accion.equals(GRABAR_ANOTACIONES_TEMPORAL_FOLIO)) {
            return grabarAnotacionesFolio(request);
        } else if (accion.equals(CREAR_FOLIO_ENGLOBE)) {
            return crearFolioEnglobe(request);
        } else if (accion.equals(ACEPTAR_CORRECCION)) {
            return aceptarCorreccion(request);
        } else if (accion.equals(NEGAR_CORRECCION)) {
            return negarCorreccion(request);
        } else if (accion.equals(ELIMINAR_FOLIO_DERIVADO)) {
            return eliminarFolioDerivado(request);
        } else if (accion.equals(VER_SALVEDAD)) {
            return verSalvedad(request);
        } else if (accion.equals(REGRESAR_CALIFICACION)) {
            return regresarCalificacion(request);
        } else if (accion.equals(APROBAR_MAYOR_VALOR)) {
            return aprobarMayorValor(request);
        } else if (accion.equals(REGISTRAR_PAGO_EXCESO)) {
            return registrarPagoExceso(request);
        } else if (accion.equals(AWCalificacion.IMPRIMIR_FORMULARIO_CALIFICACION)) {
            return imprimirFormularioCalificacion(request);
        } else if (accion.equals(AWCalificacion.IMPRIMIR_FOLIO_TEMPORAL)) {
            return imprimirFolioTemporal(request);
        } else if (accion.equals(AWCalificacion.OBTENER_BLOQUEO_FOLIOS_FIRMA)) {
            return obtenerBloqueoFolios(request);
        } else if (accion.equals(AWCalificacion.REVISOR_CONFRONTACION)) {
            return revisarConfrontacionCorrectiva(request);
        } else if (accion.equals(AWCalificacion.CERRAR_FOLIO)) {
            return cerrarFolios(request);
        } else if (accion.equals(AWCalificacion.DEVOLVER)) {
            return devolver(request);
        } else if (accion.equals(AWCalificacion.INSCRIPCION_PARCIAL)) {
            return inscripcionParcial(request);
        } else if (accion.equals(AWCalificacion.ACTUALIZAR_FOLIO_CABIDA_LINDEROS)) {
            return actualizarFolioCabidaLinderos(request);
            /**
             * @author: Cesar Ramirez
             * @change: 1245.HABILITAR.TIPO.PREDIO Se agrega la accion cuando se
             * desea actualizar el tipo del predio.
             *
             */
        } else if (accion.equals(AWCalificacion.ACTUALIZAR_FOLIO_TIPO_PREDIO)) {
            return actualizarFolioTipoPredio(request);
        } else if (accion.equals(AWCalificacion.ACTUALIZAR_FOLIO_COD_CATASTRAL)) {
            return actualizarFolioCodCatastral(request);
        } else if (accion.equals(AWCalificacion.ACTUALIZAR_FOLIO_DIRECCIONES)) {
            return actualizarFolioDirecciones(request);

        } else if (accion.equals(AWCalificacion.AGREGAR_DIRECCION_CALIFICACION)) {
            return agregarFolioDireccion(request);

        } else if (accion.equals(AWCalificacion.ELIMINAR_DIRECCION_CALIFICACION)) {
            return eliminarDireccion(request);

        } else if (accion.equals(AWCalificacion.ELIMINAR_DIRECCION_DEFINITIVA_CALIFICACION)) {
            return doFolioEdit_DireccionDefinitiva_DelItem(request);

        } //Delegar turno a fase de confrontacion
        else if (accion.equals(CONFRONTACION)) {
            return delegarConfrontacion(request);
        } //Delegar turno a DIGITACION MASIVA
        else if (accion.equals(AWCalificacion.DELEGAR_DIGITACION_MASIVA)) {
            return delegarDigitacionMasivaNuevo(request);
        } //Avanzar turno desde firma del registrador.
        else if (accion.equals(AWCalificacion.FIRMA_REGISTRO_CONFIRMAR)) {
            return avanzarFirmaRegistrador(request);
        }  //Avanzar turno desde revisor confrontacion correctiva.
        else if (accion.equals(AWCalificacion.REVISOR_CONFRONTACION_CONFIRMAR)) {
            return avanzarRevisorConfrontacion(request);
        }   //Devolver turno desde revisor confrontacion correctiva.
        else if (accion.equals(AWCalificacion.REVISOR_CONFRONTACION_DEVOLVER)) {
            return devolverRevisorConfrontacion(request);
        }//Entregar turno de Registro
        else if (accion.equals(AWCalificacion.ENTREGAR_REGISTRO)) {
            return entregarRegistro(request);
        } //JALCAZAR CASO MANTIS 0002225
        else if (accion.equals(AWCalificacion.ENTREGAR_REGISTRO_MULT)) {
            return entregarRegistroMult(request);
        } else if (accion.equals(AWCalificacion.PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION)) {
            return doProcess_BtnDeshacerCambios(request);
        } //devolver testamento
        else if (accion.equals(AWCalificacion.DEVOLVER_FIRMA_TESTAMENTO)) {
            return devolverTestamento(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion
                    + " no es valida.");
        }
    }

    //daniel SIR-61
    private Evento devolverTestamento(HttpServletRequest request) throws AccionWebException {
        ValidacionParametrosMesaControlException exception
                = new ValidacionParametrosMesaControlException();
        /*obtener datos de la session para efectuar la consulta*/
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        EvnCalificacion evento = new EvnCalificacion(usuario, EvnCalificacion.DEVOLVER_FIRMA_TESTAMENTO);
        evento.setTurno(turno);
        evento.setFase(fase);
        evento.setUsuarioNeg(usuarioNeg);
        return evento;
    }

    /**
     * doCalificacion_ReimpresionSellos_PrintSelected_Action
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento
            doCalificacion_ReimpresionSellos_PrintSelected_Action(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        // extraer parametros de request
        String param_ItemToPrint;
        param_ItemToPrint = request.getParameter(gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAM_ITEM_PRINT);

        String nimpresion = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);

        // Collector (exceptions)
        ValidacionParametrosException exception = null;
        exception = new ValidacionParametrosException();

        // Validadores
        BasicConditionalValidator validator;

        // Comparar si es la cadena vacia o nulo;
        validator = new BasicStringNotNullOrEmptyValidatorWrapper();

        validator.setObjectToValidate(param_ItemToPrint);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-turno: no se ha seleccionado un turno para imprimir");
        }

        if (nimpresion == null || nimpresion.equals("")) {
            exception.addError("el numero de copias es invalido");
        }
        int numeroImpresiones = 1;
        try {
            numeroImpresiones = Integer.parseInt(nimpresion);
        } catch (Exception e) {
            exception.addError("el numero de copias es invalido");
        }

        // ------------------------------------------------------
        // raise application error
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // ------------------------------------------------------

        // ------------------------------------------------------
        EvnCalificacion result = null;

        // -----------------------------------------------------------------------------------------------
        gov.sir.core.negocio.modelo.Turno turno
                = (gov.sir.core.negocio.modelo.Turno) request.getSession().getAttribute(WebKeys.TURNO);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario usuarioSIR
                = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        //TODO
        // Variable nunca es utilizada.
        //String sessionId = session.getId();
        String idCirculo = ((Circulo) session.getAttribute(WebKeys.CIRCULO)).getIdCirculo();

        // -----------------------------------------------------------------------------------------------
        EvnCalificacionReproduccionSellos resultWrap;
        resultWrap = new EvnCalificacionReproduccionSellos(
                usuarioAuriga,
                EvnCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENT
        );

        resultWrap.setUsuario(usuarioAuriga);
        resultWrap.setUsuarioNeg(usuarioSIR);
        resultWrap.setTurno(turno);
        resultWrap.setIdCirculo(idCirculo);
        resultWrap.setNumeroImpresiones(numeroImpresiones);
        resultWrap.setItemSelected(param_ItemToPrint);

        result = resultWrap;
        return result;

    }

    /**
     * Metodo que permite la impresion de sellos por matricula
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento imprimirSellosMatricula(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        ValidacionParametrosException exception = new ValidacionParametrosException();

        //OBTENER INFORMACIÓN DEL FORMULARIO
        String matricula = request.getParameter(CFolio.ID_MATRICULA);
        String desdeAnotacion = request.getParameter(WebKeys.DESDE);
        String hastaAnotacion = request.getParameter(WebKeys.HASTA);
        String nimpresion = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
        int inicio = 0;
        int fin = 0;

        if (matricula == null || matricula.length() == 0) {
            exception.addError("Ingrese la matricula.");
        }

        if (desdeAnotacion == null || desdeAnotacion.length() == 0) {
            exception.addError("Ingrese la anotacion inicial.");
        } else {
            try {
                inicio = new Integer(desdeAnotacion.trim()).intValue();
            } catch (Exception e) {
                exception.addError("Ingrese una anotacion de inicio valida.");
            }
        }

        if (hastaAnotacion == null || hastaAnotacion.length() == 0) {
            exception.addError("Ingrese la anotacion final.");
        } else {
            try {
                fin = new Integer(hastaAnotacion.trim()).intValue();
            } catch (Exception e) {
                exception.addError("Ingrese una anotacion final valida.");
            }
        }

        if (nimpresion == null || nimpresion.equals("")) {
            exception.addError("el numero de copias es invalido");
        }
        int numeroImpresiones = 1;
        try {
            numeroImpresiones = Integer.parseInt(nimpresion);
        } catch (Exception e) {
            exception.addError("el numero de copias es invalido");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
        String sessionId = session.getId();
        String idCirculo = ((Circulo) session.getAttribute(WebKeys.CIRCULO)).getIdCirculo();
        EvnCalificacionReproduccionSellos resultWrap = new EvnCalificacionReproduccionSellos(
                usuarioAuriga, EvnCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_MATRICULA);

        resultWrap.setUsuario(usuarioAuriga);
        resultWrap.setUsuarioNeg(usuarioSIR);
        resultWrap.setTurno(turno);
        resultWrap.setUID(sessionId);
        resultWrap.setIdCirculo(idCirculo);
        resultWrap.setMatricula(matricula);
        resultWrap.setInicio(inicio);
        resultWrap.setFin(fin);
        resultWrap.setNumeroImpresiones(numeroImpresiones);

        EvnCalificacion result = resultWrap;
        return result;

    }

    /**
     * doCalificacion_ReimpresionSellos_Reset_Action
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento
            doCalificacion_ReimpresionSellos_Reset_Action(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        // remove result list
        final String PARAM_QUERYRESULTS = gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAMID_SEARCHRESULTS;
        session.removeAttribute(PARAM_QUERYRESULTS);

        // remove other params
        session.removeAttribute(gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAM_ITEM_PRINT);
        session.removeAttribute(gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAM_ITEM_SEARCH);

        // not call buzz-layer
        return null;
    }

    /**
     * doCalificacion_ReimpresionSellos_Search_Action
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento
            doCalificacion_ReimpresionSellos_Search_Action(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();

        // extraer parametros de request
        String param_ItemToFind;
        param_ItemToFind = request.getParameter(gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAM_ITEM_SEARCH);

        // Collector (exceptions)
        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosException();

        // validadores
        BasicConditionalValidator validator;

        // comparar si es la cadena vacia o nulo;
        validator = new BasicStringNotNullOrEmptyValidatorWrapper();

        validator.setObjectToValidate(param_ItemToFind);
        validator.validate();
        if (validator.getResult() != true) {
            // throw exception
            // no se ha seleccionado una cadena valida
            exception.addError("id-turno: no se ha seleccionado una cadena valida");
        }

        // ------------------------------------------------------
        // raise application error
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        // ------------------------------------------------------

        EvnCalificacion result = null;

        // -----------------------------------------------------------------------------------------------
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //TODO
        // Variable Usuario nunca es utilizada.
        //gov.sir.core.negocio.modelo.Usuario usuarioSIR
        //= (gov.sir.core.negocio.modelo.Usuario) session.getAttribute( WebKeys.USUARIO );
        gov.sir.core.negocio.modelo.Turno thisTurno
                = (gov.sir.core.negocio.modelo.Turno) request.getSession().getAttribute(WebKeys.TURNO);

        //TODO
        // Variable nunca es utilizada.
        //String sessionId = session.getId();
        String idCirculo = ((Circulo) session.getAttribute(WebKeys.CIRCULO)).getIdCirculo();

        // -----------------------------------------------------------------------------------------------
        EvnCalificacionReproduccionSellos resultWrap;
        resultWrap = new EvnCalificacionReproduccionSellos(
                usuarioAuriga,
                EvnCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_SEARCH_EVENT
        );

        resultWrap.setItemToFind(param_ItemToFind);
        resultWrap.setTurno(thisTurno);
        resultWrap.setIdCirculo(idCirculo);

        result = resultWrap;
        return result;

    }

    /**
     * @author: Cesar Ramirez
     * @change: 1245.HABILITAR.TIPO.PREDIO Metodo que envia el tipo del predio
     * del folio si es modificado.
     * @param request
     * @return
     * @throws ValidacionParametrosException
     *
     */
    private Evento actualizarFolioTipoPredio(HttpServletRequest request) throws ValidacionParametrosException {
        // Fijar los valores en sesion y despachar el evento
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        // Crear e inicializar el evento
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.ACTUALIZAR_FOLIO_TIPO_PREDIO);
        evento.setTurno(turno);
        evento.setUID(UID);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setFolio(folio);

        /**
         * @author: Cesar Ramirez
         * @change: 1245.HABILITAR.TIPO.PREDIO Metodo Envia los datos del
         * terminal y lo envia a traves del usuario.
         *
         */
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuario.getUsuarioId(), String.valueOf(usuarioSir.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);

        /**
         * @author: Cesar Ramirez
         * @change: 1245.HABILITAR.TIPO.PREDIO Metodo Valida el valor tipoPredio
         * recibido por formulario. Lanza la excepcion sino selecciona un valor
         * valido. Guarda el nuevo TipoPredio y luego se envia en el Evento.
         *
         */
        ValidacionParametrosException exception = new ValidacionParametrosException();

        String valorTipoPredio = request.getParameter(CFolio.FOLIO_TIPO_PREDIO);

        // Valida el Tipo de Predio solo si esta en el combobox.
        if (valorTipoPredio != null) {
            if (valorTipoPredio.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Se debe seleccionar la informacion para el campo tipo predio.");
            } else {
                // Valida que el Tipo Predio existente en el Folio sea diferente al ingresado en el combobox.
                if (!valorTipoPredio.equals(folio.getTipoPredio().getIdPredio())) {
                    TipoPredio tipoPredio = new TipoPredio();
                    tipoPredio.setIdPredio(valorTipoPredio);
                    evento.setNuevoTipoPredio(tipoPredio);
                }
            }

            if (exception.getErrores().size() > 0) {
                throw exception;
            }
        }

        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento actualizarFolioDirecciones(HttpServletRequest request) {
        List t0_folio_direcciones = null; // direcciones existentes en db
        List t1_folio_direcciones = null; // direccione existentes en sesion
        List t2_folio_direcciones = null; // direcciones a enviar como delta

        HttpSession session = request.getSession();

        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);

        // modificar las direcciones
        delta_folio_direcciones:
        {

            // Conditional javaSrcBlock:
            // debe tener permiso para modificar direcciones
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
            //TODO
            // Este codigo fue cambiado por el inmediatamente posterior.
            // Solo se cambio la forma de recorrer la lista.
            /*for (java.util.Iterator iteratorT1 = t1_folio_direcciones.iterator();
													 iteratorT1.hasNext(); ) {
					Direccion t1_element = (Direccion) iteratorT1.next();
					//TODO
                    //String t1_element_id = t1_element.getIdDireccion();
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

						// se anade a la lista de cambios
						t2_folio_direcciones.add(t1_element);
						// se remueve de la otra lista ?
						// por el momento, no, para no afectar la iteracion; luego se podrian dejar en un arreglo

					} // end if

				} // end for
             */
            //Inicia nuevo codigo
            int numDirFolios = t1_folio_direcciones.size();
            Direccion t1_element;
            for (int itDF = 0; itDF < numDirFolios; itDF++) {
                t1_element = new Direccion();
                t1_element = (Direccion) t1_folio_direcciones.get(itDF);
                // ver si tiene flag set to delete en true; si lo tiene, no es necesario agregarla
                // otra condicion es que no puede tener el id, porque hasta ahora, no se ha creado
                if (t1_element.isToDelete()) {
                    continue;
                }
                if (null != t1_element.getIdDireccion()) {
                    continue;
                }
                // se anade a la lista de cambios
                t2_folio_direcciones.add(t1_element);
            }
            //Finaliza nuevo codigo

            // B: recorrido por definitivos
            // B.a: configurar para esta comparacion
            // configurar para esta comparacion
            // la lista modificada es la misma que se usa en t0
            t1_folio_direcciones = t0_folio_direcciones;

            if (null == t1_folio_direcciones) {
                t1_folio_direcciones = new java.util.ArrayList();
            }

            //TODO
            // Este codigo fue cambiado por el inmediatamente posterior.
            /*for (java.util.Iterator iteratorT1 = t1_folio_direcciones.iterator();
													 iteratorT1.hasNext(); ) {
					Direccion t1_element = (Direccion) iteratorT1.next();

					// si tiene flag setToDelete = true, se agrega en la lista de modificaciones
					if( null == t1_element ) {
						continue;
					}

					// debe tener id
					if( null == t1_element.getIdDireccion() ) {
						continue;
					}

					if( t1_element.isToDelete() ) {
						 t2_folio_direcciones.add( t1_element );
					}

				}*/
            //Inicia nuevo codigo
            numDirFolios = t1_folio_direcciones.size();
            for (int itDF = 0; itDF < numDirFolios; itDF++) {
                t1_element = new Direccion();
                t1_element = (Direccion) t1_folio_direcciones.get(itDF);

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
            //Finaliza nuevo codigo

            // 3. si no hay cambios, se deja nula la lista de cambios
            if ((null == t2_folio_direcciones)
                    || (t2_folio_direcciones.size() == 0)) {
                t2_folio_direcciones = null;
            }

        } // :delta_folio_direcciones

        session.removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        // -----------------------------------------------------------------------------------------------
        // fijar los valores en sesion y despachar el evento
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        String determinaInm = request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE);

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        if (determinaInm != null && !determinaInm.equals(WebKeys.SIN_SELECCIONAR)) {
            folio.setDeterminaInm(determinaInm);
        }
        request.getSession().setAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE, folio.getDeterminaInm());
        //crear e inicializar el evento
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.ACTUALIZAR_FOLIO_DIRECCION);
        evento.setTurno(turno);
        evento.setUID(UID);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setNuevasDirecciones(t2_folio_direcciones);
        evento.setFolio(folio);

        return evento;

    }

    /**
     * @param request
     * @return
     */
    private Evento guardarAnotacionesCancelacion(HttpServletRequest request) throws ValidacionAnotacionesCancelacionException {
        /*String[] idsAnotaciones = request.getParameterValues("ESCOGER_ANOTACION_CANCELACION");
		ValidacionAnotacionesCancelacionException exception = new ValidacionAnotacionesCancelacionException();
		if(idsAnotaciones == null){
			exception.addError("Debe seleccionar por lo menos una anotacion para cancelar");
			throw exception;
		}*/

        //		 bug 10371:
        // must be collected in the paginator
        final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID = "TARGET_SELECTIONCOLLECTOR";

        String idsAnotacionesCsv = request.getParameter(LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID);

        // only the last is added
        String[] idsAnotaciones = gov.sir.core.web.helpers.comun.JvLocalUtils.csvStringToStringArray(idsAnotacionesCsv, gov.sir.core.web.helpers.comun.JvLocalUtils.DEFAULT_SEPARATOR, true);
        ValidacionAnotacionesCancelacionException exception = new ValidacionAnotacionesCancelacionException();
        if (idsAnotaciones == null) {
            exception.addError("Debe seleccionar por lo menos una anotacion para cancelar");
            throw exception;
        }

        request.getSession().setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, idsAnotaciones);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento agregarFolioDireccion(HttpServletRequest request) throws ValidacionParametrosException {

        preservarInfoBasicaFolio(request);

        List dirTemporales = (List) request.getSession().getAttribute(LISTA_DIRECCIONES_TEMPORALES);
        //request.getSession().removeAttribute(LISTA_DIRECCIONES_TEMPORALES);
        if (dirTemporales == null) {
            dirTemporales = new ArrayList();
        }

        HttpSession session = request.getSession();
        Folio folioAnt = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        Folio folio = new Folio();
        folio.setIdMatricula(folioAnt.getIdMatricula());
        List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        if (direcciones == null) {
            direcciones = new Vector();
        }

        ValidacionParametrosException exception = new ValidacionParametrosException();
        String valorEje1 = request.getParameter(AWModificarFolio.FOLIO_EJE1);
        if ((valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
        }

        String valorValor1 = request.getParameter(AWModificarFolio.FOLIO_VALOR1);
        if (valorValor1.length() <= 0) {
            exception.addError("Debe ingresar valor valido para el primer eje de la direccion");
        }

        String valorEje2 = request.getParameter(AWModificarFolio.FOLIO_EJE2);
        String valorValor2 = request.getParameter(AWModificarFolio.FOLIO_VALOR2);
        if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
            valorEje2 = new String();
        } else {
            if (valorValor2.length() <= 0) {
                exception.addError("Debe ingresar valor valido para el segundo eje  de la direccion");
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
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

        direcciones.add(direccion);
        List direccionesTmp = new ArrayList();
        direccionesTmp.add(direccion);
        if (exception.getErrores().size() > 0) {
            throw exception;
        } else {
            folio.setDirecciones(direccionesTmp);
        }
        eliminarInfoBasicaDireccion(request);

        session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AGREGAR_FOLIO_DIRECCION);
        evento.setUsuarioNeg(usuarioSIR);
        evento.setFolio(folio);
        evento.setDirTemporales(dirTemporales);
        return evento;

    }

    /**
     * Elimina de la sesion la informacion de una direccion.
     */
    private void eliminarInfoBasicaDireccion(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.removeAttribute(AWModificarFolio.FOLIO_EJE1);
        session.removeAttribute(AWModificarFolio.FOLIO_VALOR1);
        session.removeAttribute(AWModificarFolio.FOLIO_EJE2);
        session.removeAttribute(AWModificarFolio.FOLIO_VALOR2);
        session.removeAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
    }

    /**
     * Permite eliminar una direccion de un folio determinado.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento eliminarDireccion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);

        HttpSession session = request.getSession();

        List dirTemporales = (List) session.getAttribute(LISTA_DIRECCIONES_TEMPORALES);
        //session.removeAttribute(LISTA_DIRECCIONES_TEMPORALES);

        Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Folio folioAnt = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        Folio folio = new Folio();
        folio.setIdMatricula(folioAnt.getIdMatricula());

        List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        Direccion direccion = null;
        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
            session.setAttribute(WebKeys.POSICION, new Integer(aplicacionNumero));
            direccion = (Direccion) direcciones.get(aplicacionNumero);
            direccion.setToDelete(true);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El numero del documento a eliminar es invalido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (direcciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacia");
            } else {
                throw new ParametroInvalidoException("El indice del documento a eliminar esta fuera del rango");
            }
        }
        List direccionesTmp = new ArrayList();
        direccionesTmp.add(direccion);
        folio.setDirecciones(direccionesTmp);
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.ELIMINAR_FOLIO_DIRECCION);
        evento.setUsuarioNeg(usuarioSIR);
        evento.setFolio(folio);
        evento.setDirTemporales(dirTemporales);
        session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);

        return evento;
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

        HttpSession session = request.getSession();
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
            Direccion element = (Direccion) direcciones.get(aplicacionNumero);

            if (null != element) {
                element.setToDelete(true);
            }

        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El numero del documento a eliminar es invalido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (direcciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacia");
            } else {
                throw new ParametroInvalidoException("El indice del documento a eliminar esta fuera del rango");
            }
        }

        return null;

    }

    /**
     * @param request
     * @return
     */
    private Evento actualizarFolioCodCatastral(HttpServletRequest request) {

        preservarInfoBasicaFolio(request);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        String nuevoCat = request.getParameter(CFolio.FOLIO_COD_CATASTRAL);

        //crear e inicializar el evento
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.ACTUALIZAR_FOLIO_COD_CATASTRAL);
        evento.setTurno(turno);
        evento.setUID(UID);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setNuevoCodCatastral(nuevoCat);
        evento.setFolio(folio);

        return evento;
    }

    /**
     * @param request
     * @return
     */
    /**
     * @Autor: Edgar Lora
     * @Mantis: 0013038
     * @Requerimiento: 060_453
     */
    private Evento actualizarFolioCabidaLinderos(HttpServletRequest request) throws AccionWebException {

        preservarInfoBasicaFolio(request);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        String linderoNuevo = request.getParameter(CFolio.LINDERO_ADICION);

        ValidacionParametrosException exception = new ValidacionParametrosException();
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();

        try {
            String matricula = folio.getIdMatricula();
            String lindero = linderoNuevo;
            if (validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)) {
                if (validacionesSIR.validarLinderos(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS)) {
                    String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                    if (lindero.indexOf(articulo) != -1) {
                        int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                        if (lindero.length() - tamArticulo < 100) {
                            //   exception.addError("Debe añadir minimo 100 caracteres al campo de linderos de la matricula: " + matricula);
                        }
                    } else {
                        //  exception.addError("El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto \"Articulo 8 Parágrafo 1º.. de la Ley 1579 de 2012\", Porfavor no lo borre.");
                    }
                }
            }
        } catch (GeneralSIRException ex) {
            exception.addError(ex.getMessage());
        }

        /**
         * @Author Cristian David Garcia Sección de Edición de Areas
         * correspondientes a los Linderos.
         */
        String coeficiente = request.getParameter(CFolio.FOLIO_COEFICIENTE);
        if (coeficiente != null || coeficiente.length() > 0) {
            folio.setCoeficiente(coeficiente);
        }

        String privMetros = request.getParameter(CFolio.FOLIO_PRIVMETROS);
        if (privMetros != null && privMetros.length() > 0) {
            folio.setPrivMetros(privMetros);
        } else{
            folio.setPrivMetros("0");
            privMetros = "0";
        }

        String privCentimetros = request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS);
        if (privCentimetros != null && privCentimetros.length() > 0) {
            folio.setPrivCentimetros(privCentimetros);
        } else{
           folio.setPrivCentimetros("0");  
           privCentimetros = "0";
        }

        String consMetros = request.getParameter(CFolio.FOLIO_CONSMETROS);
        if (consMetros != null && consMetros.length() > 0) {
            folio.setConsMetros(consMetros);
        } else{
           folio.setConsMetros("0");
           consMetros = "0";
        }

        String consCentimetros = request.getParameter(CFolio.FOLIO_CONSCENTIMETROS);
        if (consCentimetros != null && consCentimetros.length() > 0) {
            folio.setConsCentimetros(consCentimetros);
        } else{
            folio.setConsCentimetros("0");
            consCentimetros = "0";
        }

        String hectareas = request.getParameter(CFolio.FOLIO_HECTAREAS);
        if (hectareas != null && hectareas.length() > 0) {
            folio.setHectareas(hectareas);
        } else{
            folio.setHectareas("0");
            hectareas = "0";
        }

        String metros = request.getParameter(CFolio.FOLIO_METROS);
        if (metros != null && metros.length() > 0) {
            folio.setMetros(metros);
        } else{
            folio.setMetros("0");
            metros = "0";
        }

        String centimetros = request.getParameter(CFolio.FOLIO_CENTIMETROS);
        if (centimetros != null && centimetros.length() > 0) {
            folio.setCentimetros(centimetros);
        } else{
            folio.setCentimetros("0");
            centimetros = "0";
        }
        
//        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
        boolean sinValidar = false;
//        
//        if(anotaciones != null){
//        Iterator itAnotacion = anotaciones.iterator();
//        while(itAnotacion.hasNext()){
//            Anotacion anotacion = (Anotacion) itAnotacion.next();
//            if (anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals("0911")){
//                sinValidar = true;
//            }
//        }}
        
        if(!sinValidar){
        String hectareasT = "0";
        String metrosT = "0";
        String centimetrosT = "0";
        int unidadmedidaexception = 3;
        

        if (hectareas == null || hectareas.length() <= 0) {
            unidadmedidaexception--;
            hectareas = hectareasT;
            folio.setHectareas(hectareas);
        } else {
            hectareasT = hectareas;
        }

        if (metros == null || metros.length() <= 0) {
            unidadmedidaexception--;
            metros = metrosT;
            folio.setMetros(metros);
        } else {
            metrosT = metros;
        }

        if (centimetros == null || centimetros.length() <= 0) {
            unidadmedidaexception--;
            centimetros = centimetrosT;
            folio.setCentimetros(centimetros);
        } else {
            centimetrosT = centimetros;
        }

        if (unidadmedidaexception != 0) {
            if (!hectareas.equals("0")) {
                folio.setHectareas(hectareas);
            }
            if (!metros.equals("0")) {
                folio.setMetros(metros);
            }
            if (!centimetros.equals("0")) {
                folio.setCentimetros(centimetros);
            }

            boolean datosPrivBien = true;
            boolean privArea = false;

            String metrosP = "0";
            String centimetrosP = "0";

            if (privMetros != null && !privMetros.isEmpty()) {
                privArea = true;
                metrosP = privMetros;
            } else {
                privMetros = "0";
            }

            if (privCentimetros != null && !privCentimetros.isEmpty()) {
                privArea = true;
                centimetrosP = privCentimetros;
            } else {
                privCentimetros = "0";
            }

            if (privArea) {
                unidadmedidaexception = 2;
                String metrosC = "0";
                String centimetrosC = "0";

                if (consMetros == null || consMetros.length() <= 0) {
                    unidadmedidaexception--;
                    consMetros = "0";
                } else {
                    metrosC = consMetros;
                }

                if (consCentimetros == null || consCentimetros.length() <= 0) {
                    unidadmedidaexception--;
                    consCentimetros = "0";
                } else {
                    centimetrosC = consCentimetros;
                }

                if (unidadmedidaexception == 0) {
                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                    throw exception;
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
                    exception.addError("El area privada no puede ser mayor al area construida");
                    throw exception;
                } else {
                    folio.setPrivMetros(privMetros);
                    folio.setPrivCentimetros(privCentimetros);
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
                    //exception.addError("El area construida no puede ser mayor al area del terreno");
                    //throw exception;
                    folio.setConsMetros(consMetros);
                    folio.setConsCentimetros(consCentimetros);
                } else {
                    folio.setConsMetros(consMetros);
                    folio.setConsCentimetros(consCentimetros);
                }

            } else {

                boolean consArea = false;

                String metrosC = "0";
                String centimetrosC = "0";

                if (consMetros != null && !consMetros.isEmpty()) {
                    consArea = true;
                    metrosC = consMetros;
                } else {
                    consMetros = "0";
                }

                if (consCentimetros != null && !consCentimetros.isEmpty()) {
                    consArea = true;
                    centimetrosC = consCentimetros;
                } else {
                    consCentimetros = "0";
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
                        //exception.addError("El area construida no puede ser mayor al area del terreno");
                        //throw exception;
                        folio.setConsMetros(consMetros);
                        folio.setConsCentimetros(consCentimetros);
                    } else {
                        folio.setConsMetros(consMetros);
                        folio.setConsCentimetros(consCentimetros);
                    }
                }
            }

        } else {
            boolean privArea = false;

            if (privMetros != null && !privMetros.isEmpty()) {
                privArea = true;
            }

            if (privCentimetros != null && !privCentimetros.isEmpty()) {
                privArea = true;
            }

            if (privArea) {
                boolean consArea = false;

                if (consMetros != null && !consMetros.isEmpty()) {
                    consArea = true;
                }

                if (consCentimetros != null && !consCentimetros.isEmpty()) {
                    consArea = true;
                }

                if (consArea) {
                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area del Terreno");
                } else {
                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                }
            } else {
                boolean consArea = false;

                if (consMetros != null && !consMetros.isEmpty()) {
                    consArea = true;
                }

                if (consCentimetros != null && !consCentimetros.isEmpty()) {
                    consArea = true;
                }

                if (consArea) {
                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                }
            }
        }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // SE LE PASA LA PARTE ADICIONAL QUE EL CALIFICADOR LE AGREGO AL LINDERO. LA INFORMACION ES SIN LO 
        //EL LINDERO TENIA ORIGINALMENTE, UNICAMENTE LO NUEVO. LUEGO ESTE LINDERO SE CONCATENA CON EL QUE TENIA EL FOLIO
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.ACTUALIZAR_FOLIO_CABIDA_LINDEROS);
        evento.setTurno(turno);
        evento.setUID(UID);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setNuevoLindero(linderoNuevo);
        evento.setFolio(folio);

        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento verSalvedad(HttpServletRequest request) {
        String ver = request.getParameter(VER_SALVEDAD);
        request.getSession().setAttribute(VER_SALVEDAD, ver);
        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");
        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }
        preservarInfoBasicaAnotacion(request);
        return null;

    }

    /**
     * @param request
     * @return
     * @throws ValidacionParametrosSegregacion
     */
    private Evento eliminarFolioDerivado(HttpServletRequest request) throws ValidacionParametrosSegregacion {
        HttpSession session = request.getSession();
        ValidacionParametrosSegregacion exception = new ValidacionParametrosSegregacion();
        List foliosD = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS);
        if (foliosD == null || foliosD.isEmpty()) {
            exception.addError("Debe incluir por lo menos la informacion de un folio derivado");
            throw exception;
        }
        String i = request.getParameter(WebKeys.POSICION);
        int pos = Integer.valueOf(i).intValue();
        foliosD.remove(pos);
        session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS, foliosD);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento negarCorreccion(HttpServletRequest request) {
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        return new EvnCalificacion(usuarioArq, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnCalificacion.WF_NEGAR_CORRECCION, CAvanzarTurno.AVANZAR_POP, EvnCalificacion.NEGAR_CORRECCION_ENCABEZADO);

    }

    /**
     * @param request
     * @return
     * @throws ValidacionParametrosException
     */
    private Evento aceptarCorreccion(HttpServletRequest request) throws ValidacionParametrosException {
        ValidacionCorreccionEncabezadoException exception = new ValidacionCorreccionEncabezadoException();

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();

        boolean isInternacional = false;

        String TOficina = request.getParameter(WebKeys.TIPO_OFICINA_I_N);
        if (TOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_INTERNACIONAL)) {
            isInternacional = true;
        }

        String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
        String id_encabezado = request.getParameter(CSolicitudRegistro.ID_ENCABEZADO);
        String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);

        String nomOficinaInternacional = "";
        String nomDepto = "";
        String idDepto = "";
        String nomMunic = "";
        String idMunic = "";
        String nomVereda = "";
        String idVereda = "";
        //TODO
        // Variables nunca son utilizadas.
        //String tipo_oficina="";
        //String numero_oficina="";
        String id_oficina = "";

        if (isInternacional) {
            nomOficinaInternacional = request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL);
        } else {

            nomDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
            idDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);

            nomMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
            idMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);

            nomVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
            idVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);

            if (idVereda == null || idVereda.trim().equals("") || nomDepto.trim().equals("") || nomMunic.trim().equals("")) {
                idVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), idDepto, idMunic);
            }

            //TODO
            // Variables nunca son utilizadas.
            //tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            //numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
            id_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);

        }

        if ((tipo_encabezado == null) || tipo_encabezado.equals("SIN_SELECCIONAR")) {
            exception.addError("El tipo de encabezado es invalido");
        }

        Calendar fechaDocumento = darFecha(fecha);

        if (fechaDocumento == null) {
            exception.addError("La fecha del documento es invalida");
        }

        if ((fecha == null) || fecha.trim().equals("")) {
            exception.addError("La fecha del encabezado es invalida");
        }

        if (isInternacional) {
            if ((nomOficinaInternacional == null) || nomOficinaInternacional.trim().equals("")) {
                exception.addError("El nombre de la oficina internacional es invalido");
            }
        } else {
            //daniel
            if ((idVereda == null) || idVereda.trim().equals("")) {
                exception.addError("Error de municipio en el encabezado. "
                        + "Verifique en pantallas administrativas que exista el municipio y su "
                        + " vereda cabecera municipal en el sistema");
            }

            if ((idDepto == null) || idDepto.trim().equals("")) {
                exception.addError("El departamento es invalido");
            }

            if ((idMunic == null) || idMunic.trim().equals("")) {
                exception.addError("El municipio es invalido");
            }

            if ((id_oficina == null) || id_oficina.trim().equals("")) {
                exception.addError("El numero de oficina es invalido");
            }
        }

        preservarInfo(request);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        String valorDepartamento
                = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String valorMunicipio
                = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String valorVereda
                = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String idOficina
                = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                         *  @author Carlos Torres
                         *  @chage   se agrega validacion de version diferente
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Codigo_Notaria_NC
         */
        String oficinaVersion
                = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        String nomOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        String numOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);

        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);

        solicitud.setCirculo(circulo);
        solicitud.setComentario("");
        solicitud.setFecha(new Date(System.currentTimeMillis()));
        solicitud.setProceso(proceso);
        solicitud.setTurnoAnterior(turno);

        OficinaOrigen oficinaOrigen = new OficinaOrigen();

        if (!isInternacional) {

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
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Codigo_Notaria_NC
             */
            oficinaOrigen.setVersion(Long.parseLong(oficinaVersion));

            Vereda vereda = new Vereda();

            if (nomVereda != null) {
                vereda.setNombre(nomVereda);
            }

            vereda.setIdVereda(valorVereda);
            vereda.setIdDepartamento(valorDepartamento);
            vereda.setIdMunicipio(valorMunicipio);

            oficinaOrigen.setVereda(vereda);
        }

        Documento documento = new Documento();
        /**
         * @author Cesar Ramirez
         * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO La fecha del turno
         * actual esta sin hora, la que se envia por el formulario guarda hora.
         * Se verifica que si ambas fechas son iguales (tiene el mismo dia, mes
         * y ano) independiente de su hora.
         *
         */
        Date fecha_turno = solicitud.getDocumento().getFecha();
        Date fecha_enviada = fechaDocumento.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format_fecha_turno = formatter.format(fecha_turno);
        String format_fecha_enviada = formatter.format(fecha_enviada);

        if (format_fecha_turno.equals(format_fecha_enviada)) {
            documento.setFecha(fecha_turno);
        } else {
            documento.setFecha(fecha_enviada);
        }

        if (!isInternacional) {
            documento.setOficinaInternacional(null);
            documento.setOficinaOrigen(oficinaOrigen);
        } else {
            documento.setOficinaOrigen(null);
            documento.setOficinaInternacional(nomOficinaInternacional);
        }

        TipoDocumento tipoDoc = new TipoDocumento();
        tipoDoc.setIdTipoDocumento(tipo_encabezado);

        List tiposDocs = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
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
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnCalificacion evento = new EvnCalificacion(usuarioArq, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, solicitud, fase, EvnCalificacion.WF_CONFIRMAR_CORRECCION, CAvanzarTurno.AVANZAR_POP, EvnCalificacion.REALIZAR_CORRECCION_ENCABEZADO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuario.getUsuarioId(), String.valueOf(usuarioSir.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        evento.setInfoUsuario(infoUsuario);

        return evento;
    }

    /**
     * @param request
     * @return
     * @throws ValidacionParametrosSegregacion
     */
    private Evento segregarMasivo(HttpServletRequest request) throws ValidacionParametrosSegregacion {
        HttpSession session = request.getSession();
        ValidacionParametrosSegregacion exception = new ValidacionParametrosSegregacion();

        List anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_SEGREGACION);
        List foliosD = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS);
        if (foliosD == null || foliosD.isEmpty()) {
            exception.addError("Debe incluir por lo menos la informacion de un folio derivado");
            throw exception;
        }

        Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        return new EvnCalificacion(folio, turno, anotaciones, foliosD, usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO));
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento crearFolioEnglobe(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosEnglobarException exception = new ValidacionParametrosEnglobarException();
        preservarInfoBasicaFolio(request);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        if (turno == null) {
            throw new AccionWebException("No se encontro el turno en la sesion");
        }

        String valorRadicacion = request.getParameter(CFolio.FOLIO_NUM_RADICACION);
        double numRadicacion = 0.0d;

        if ((valorRadicacion != null) && (valorRadicacion.length() > 0)) {
            try {
                numRadicacion = Double.parseDouble(valorRadicacion);

                if (numRadicacion <= 0) {
                    exception.addError(
                            "El numero de la matricula debe ser positivo");
                }
            } catch (NumberFormatException e) {
                exception.addError("El numero de la matricula es invalido");
            }
        }

        String valorDepartamento = request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO);
        if (valorDepartamento == null) {
            valorDepartamento = new String();
        }
        String nomDepartamento = request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO);
        if (nomDepartamento == null) {
            nomDepartamento = new String();
        }
        if ((valorDepartamento.length() <= 0)
                || (nomDepartamento.length() <= 0)) {
            exception.addError("Debe seleccionar un departamento.");
        }

        String valorMunicipio = request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC);
        if (valorMunicipio == null) {
            valorMunicipio = new String();
        }
        String nomMunicipio = request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        if (nomMunicipio == null) {
            nomMunicipio = new String();
        }

        if ((valorMunicipio.length() <= 0) || (nomMunicipio.length() <= 0)) {
            exception.addError("Debe seleccionar un municipio valido.");
        }

        String valorVereda = request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA);
        if (valorVereda == null) {
            valorVereda = new String();
        }
        String nomVereda = request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        if (nomVereda == null) {
            nomVereda = new String();
        }

        if ((valorVereda.length() <= 0) || (nomVereda.length() <= 0)) {
            exception.addError("Debe seleccionar una vereda valida.");
        }

        String valorTipoPredio = request.getParameter(CFolio.FOLIO_TIPO_PREDIO);

        if (valorTipoPredio == null) {
            exception.addError("Debe seleccionar un tipo de predio valido");
        }

        String valorComplementacion = request.getParameter(CFolio.FOLIO_COMPLEMENTACION);

        if (valorComplementacion == null) {
            exception.addError("Debe ingresar una  complementacion valida");
        }

        String valorLindero = request.getParameter(CFolio.FOLIO_LINDERO);

        if (valorLindero == null) {
            exception.addError("Debe ingresar informacion del lindero valida");
        }

        String valorCodCatastral = request.getParameter(CFolio.FOLIO_COD_CATASTRAL);

        if (valorCodCatastral == null) {
            exception.addError("Debe ingresar un codigo catastral valido");
        }

        String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);
        if (valorEje1 == null) {
            valorEje1 = new String();
        }
        if ((valorEje1.length() <= 0)
                || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError(
                    "Debe seleccionar el primer eje valido para la direccion");
        }

        String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);
        if (valorValor1 == null) {
            valorValor1 = new String();
        }
        if (valorValor1.length() <= 0) {
            exception.addError(
                    "Debe ingresar valor valido para el primer eje de la direccion");
        }

        String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);
        if (valorEje2 == null) {
            valorEje2 = new String();
        }
        String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);
        if (valorValor2 == null) {
            valorValor2 = new String();
        }

        if ((valorEje2.length() <= 0)
                || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
            valorEje2 = new String();
        } else {
            if (valorValor2.length() <= 0) {
                exception.addError(
                        "Debe ingresar valor valido para el segundo eje  de la direccion");
            }
        }

        String complemento = request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR);
        if (complemento == null) {
            complemento = new String();
        }
        /*
		   if (complemento.length() <= 0) {
			   exception.addError("Debe ingresar un complemento valido");
		   }*/

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Folio folio = new Folio();
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

        if (circulo == null) {
            circulo = new Circulo();
        }

        if (valorRadicacion != null) {
            if (valorRadicacion.length() > 0) {
                valorRadicacion = circulo.getIdCirculo() + "-"
                        + valorRadicacion;
            } else {
                valorRadicacion = null;
            }
        }

        folio.setIdMatricula(valorRadicacion);
        folio.setCodCatastral(valorCodCatastral);

        Complementacion comp = new Complementacion();
        comp.setComplementacion(valorComplementacion);
        folio.setComplementacion(comp);

        folio.setFechaApertura(new Date());

        if (valorRadicacion != null) {
            folio.setIdMatricula(valorRadicacion);
        }

        folio.setLindero(valorLindero);

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

        List ejes = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();
        direccion.setEspecificacion(complemento);

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
                    direccion.setValorEje1(valorValor2);
                }
            }
        } else {
            exception.addError("La lista de los ejes no se encontro");
        }

        folio.addDireccione(direccion);

        ZonaRegistral zona = new ZonaRegistral();
        zona.setCirculo(circulo);

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
        vereda.setMunicipio(municipio);
        vereda.setNombre(nomVereda);
        zona.setVereda(vereda);
        folio.setZonaRegistral(zona);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();

            while (itAnotaciones.hasNext()) {
                folio.addAnotacione((Anotacion) itAnotaciones.next());
            }
        }

        Solicitud solicitud = turno.getSolicitud();

        if (solicitud instanceof SolicitudRegistro) {
            SolicitudRegistro registro = (SolicitudRegistro) solicitud;
            folio.setDocumento(registro.getDocumento());
        }

        TurnoFolio tFolio = new TurnoFolio();
        tFolio.setTurno(turno);
        folio.addTurnosFolio(tFolio);
        request.getSession().setAttribute(WebKeys.FOLIO, folio);
        eliminarInfoBasicaFolio(request);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        List foliosFuente = (List) request.getSession().getAttribute(WebKeys.LISTA_FOLIOS_ENGLOBE);
        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Hashtable tabla = (Hashtable) request.getSession().getAttribute("TABLA_ANOTACIONES_ENGLOBE");
        if (tabla == null) {
            tabla = new Hashtable();
        }
        //TODO
        // Lista creada nunca es utilizada.
        /*Enumeration e=tabla.elements();
			List escogidas=new ArrayList();
			while(e.hasMoreElements()){
				List anotacionesEscogidas=(List)e.nextElement();
				escogidas.addAll(anotaciones);
			}*/

        return new EvnCalificacion(usuarioArq, foliosFuente, folio, tabla, usuario);
    }

    /**
     * @param request
     * @return
     */
    private Evento tomarTurno(HttpServletRequest request) throws AccionWebException{
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        
        try{
            HermodService hs = HermodService.getInstance();
            Turno turn = hs.getTurnobyWF(turno.getIdWorkflow());
            SolicitudRegistro solReg = (SolicitudRegistro) turn.getSolicitud();
            List solFolio = solReg.getSolicitudFolios();
            List matriculas = new ArrayList();
            if(solFolio != null && !solFolio.isEmpty()){
                Iterator itFol = solFolio.iterator();
                while(itFol.hasNext()){
                    SolicitudFolio solF = (SolicitudFolio) itFol.next();
                    matriculas.add(solF.getIdMatricula());
                }
            }
            
            boolean existError = false;
            if(!matriculas.isEmpty()){
                Iterator itMat = matriculas.iterator();
                while(itMat.hasNext()){
                    String idMatricula = (String) itMat.next();
                    String bloqueo = hs.getStringByQuery(CQueries.getBloqueoNotificadorNota(idMatricula));
                    if(bloqueo != null){
                        existError = true; 
                        break;
                    }
                }
            }
            
            if(existError){
                exception.addError(CError.errorAvanzarNotificador(turn));
            }
            
            if(!exception.getErrores().isEmpty()){
                throw exception;
            }
        } catch(HermodException he){
            System.out.println("ERROR: " + he);
        }

        return new EvnCalificacion(usuario, turno, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento validarTurnoParaCalificacion(HttpServletRequest request) {
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        return new EvnCalificacion(EvnCalificacion.VALIDAR_TURNO_PARA_CALIFICACION, usuario, turno, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento desasociarFolio(HttpServletRequest request) throws ValidacionParametrosException {

        ValidacionParametrosException exception = new ValidacionParametrosException();
        List foliosFuente = null;
        int notasIniciales = 0;
        String[] matriculasImp = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
        
        String notasI = String.valueOf(request.getSession().getAttribute(NOTAS_INFORMATIVAS_INICIALES));
        if(notasI != null && !notasI.isEmpty()){
            notasIniciales = Integer.parseInt(notasI);
        }
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        
        
        int notasNecesarias = 0;
         int hasNote = 0;
         
                List notasInformativas = turno.getNotas();
                if(notasInformativas != null && !notasInformativas.isEmpty()){
                    Iterator itNota = notasInformativas.iterator();
                    while(itNota.hasNext()){
                        Nota note = (Nota) itNota.next();
                        if(note != null && note.getTiponota().getIdTipoNota().equals(CNota.ELIMINAR_MATRICULAS_CALIFICACION)){
                            hasNote++;
                        }
                    }
                }
                
                 notasNecesarias = notasIniciales++;
                 String notasN = String.valueOf(request.getSession().getAttribute("AGREGA_NOTA"));
                 int nN = Integer.parseInt((!notasN.equals("null")?notasN:"0"));
                 if(nN != 0){
                     notasNecesarias = nN;
                 } else{
                 notasNecesarias++;
                 request.getSession().setAttribute("AGREGA_NOTA",notasNecesarias);
                 }
               
                 
                
                
                if(hasNote < notasNecesarias || hasNote == 0){
                    exception.addError("Debe agregar una nota informativa de tipo eliminar matricula para continuar.");
                    request.getSession().setAttribute("AGREGA_NOTA",notasNecesarias);
                }
                
                
                if(exception.getErrores().size() > 0){
                    throw exception;
                }
                
        Solicitud solicitud = turno.getSolicitud();
        List solFolios = solicitud.getSolicitudFolios();
        
        Iterator it = null;
        if (matriculasImp != null) {
            foliosFuente = new ArrayList();

            for (int i = 0; i < matriculasImp.length; i++) {
                it = solFolios.iterator();
                while (it.hasNext()) {
                    SolicitudFolio solFolio = (SolicitudFolio) it.next();
                    if (solFolio.getIdMatricula().equals(matriculasImp[i])) {
                        Folio folio = new Folio();
                        folio.setIdMatricula(solFolio.getIdMatricula());
                        foliosFuente.add(folio);
                        break;
                    }
                }
            }
        }

        if (foliosFuente == null || foliosFuente.size() == 0) {
            exception.addError("Debe seleccionar por lo menos un folio a desasociar");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        return new EvnCalificacion(foliosFuente, usuario, turno, usuarioNeg, EvnCalificacion.DESASOCIAR_FOLIOS);
    }

    /**
     * @param request
     * @return
     */
    private Evento confirmar(HttpServletRequest request) throws ValidacionParametrosException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usu = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        String ImprimirQuest = request.getParameter(WebKeys.ImprimirCC);
        //aqui hacer validacion anotaciones temporales
        List sols = turno.getSolicitud().getSolicitudFolios();
        /**
         * @Autor: Edgar Lora
         * @Mantis: 0013038
         * @Requerimiento: 060_453
         */
        ValidacionParametrosException exception = new ValidacionParametrosException();
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();

        boolean error = true;
        Folio folio = null;
        List anots = null;
        Iterator ia = null;
        Anotacion a = null;
        SolicitudFolio sf = null;
        Iterator i = sols.iterator();
        while (i.hasNext()) {
            sf = (SolicitudFolio) i.next();
            folio = sf.getFolio();
            anots = folio.getAnotaciones();
            ia = anots.iterator();
            while (ia.hasNext()) {
                a = (Anotacion) ia.next();
                if (a.isTemporal()) {
                    Log.getInstance().debug(AWCalificacion.class, "---" + a.getIdMatricula() + "----- " + a.getIdAnotacion() + " (Temporal)");
                    error = false;
                } else {
                    Log.getInstance().debug(AWCalificacion.class, "---" + a.getIdMatricula() + "----- " + a.getIdAnotacion());
                }
            }
            if (error) {
                Log.getInstance().debug(AWCalificacion.class, "\n\n\n 	ERROR!!!! matricula " + folio.getIdMatricula());
            }
            /**
             * @Autor: Edgar Lora
             * @Mantis: 0013038
             * @Requerimiento: 060_453
             */
            try {
                String matricula = folio.getIdMatricula();
                String lindero = validacionesSIR.getLinderoDeMatricula(matricula);
                if (lindero == null) {
                    lindero = folio.getLindero();
                }
                if (validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)) {
                    /**
                     * @author: Cesar Ramirez
                     * @change:
                     * 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL Se
                     * cambia el metodo de de validarLinderos por
                     * validarLinderosPrimeraAnotacionDerivada.
                     *
                     */
                    if (validacionesSIR.validarLinderosPrimeraAnotacionDerivada(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS, CTipoAnotacion.DERIVADO)) {
                        String articulo = ("Articulo 8 Par?grafo 1?. de la Ley 1579 de 2012").toUpperCase();
                        if (lindero.indexOf(articulo) != -1) {
                            int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                            if (lindero.length() - tamArticulo < 100) {
                               // exception.addError("Debe a?adir minimo 100 caracteres al campo de linderos de la matricula: " + matricula);
                            }
                        } else {
                           // exception.addError("El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto \"Articulo 8 Par?grafo 1?... de la Ley 1579 de 2012\", Porfavor no lo borre.");
                        }
                    }
                }
            } catch (GeneralSIRException ex) {
                exception.addError(ex.getMessage());
            }
        }
        HttpSession session = request.getSession();
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String idCirculo = circulo.getIdCirculo();

        String valCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
        int copias = 1;

        if (valCopias != null) {
            try {
                copias = Integer.valueOf(valCopias).intValue();
                if (copias < 1) {
                    exception.addError("El numero de copias es invalido");
                }
            } catch (Exception e) {
                exception.addError("El numero de copias es invalido");
            }
        }

        String impresora = request.getParameter(WebKeys.IMPRESORA);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        /////////////

        EvnCalificacion resp = new EvnCalificacion(usuario, turno, fase, usu, EvnCalificacion.WF_CONFIRMAR_CALIFICACION, CAvanzarTurno.AVANZAR_PUSH, idCirculo);
        resp.setUID(request.getSession().getId());
        if (ImprimirQuest != null) {
            resp.setImprimirYN(1);
        }
        resp.setNumeroCopias(copias);
        if (impresora != null && !impresora.equals(WebKeys.SIN_SELECCIONAR)) {
            resp.setImpresoraSeleccionada(impresora);
        }

        return resp;
    }

    /**
     * @param request
     * @return
     */
    private Evento avanzar(HttpServletRequest request) throws AccionWebException {
        ValidacionParametrosAvanzarException exception = new ValidacionParametrosAvanzarException();
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
        String camino = request.getParameter(RESPUESTAWF);

        if (!camino.equals(AWCalificacion.SIN_SELECCIONAR)) {

            String respuesta;
            Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

            if (camino.equals(AWCalificacion.DEVOLUCION)) {
                respuesta = EvnCalificacion.WF_DEVOLUCION;
                request.getSession().setAttribute(WebKeys.EXCEPCION, true);
                return new EvnCalificacion(usuarioAuriga, userNeg, turno, fase, respuesta, CAvanzarTurno.AVANZAR_PUSH);
            } else if (camino.equals(AWCalificacion.CORRECCION)) {
                //Tiene validacion de que no tenga informacion temporal el turno
                EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_CORRECCIONES);
                evento.setTurno(turno);
                evento.setUsuarioNeg(userNeg);
                return evento;
            } else if (camino.equals(AWCalificacion.MICRO)) {
                respuesta = EvnCalificacion.WF_MICROFILMACION;
                session.setAttribute(WebKeys.EXCEPCION, true);
                return new EvnCalificacion(usuarioAuriga, userNeg, turno, fase, respuesta, CAvanzarTurno.AVANZAR_PUSH);
            } else if (camino.equals(AWCalificacion.DIGITACION)) {
                session.setAttribute(WebKeys.EXCEPCION, true);
                respuesta = EvnCalificacion.WF_DIGITACION;
                return new EvnCalificacion(usuarioAuriga, userNeg, turno, fase, respuesta, CAvanzarTurno.AVANZAR_PUSH);
            } else if (camino.equals(AWCalificacion.ANT_SISTEMA)) {
                //Tiene validacion de que no tenga informacion temporal el turno
                session.setAttribute(WebKeys.EXCEPCION, true);

                EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_ANTIGUO_SISTEMA);
                evento.setTurno(turno);
                evento.setUsuarioNeg(userNeg);
                evento.setFase(fase);
                evento.setRespuestaWF(EvnCalificacion.WF_ANTIGUO_SISTEMA);
                evento.setTipoAvance(CAvanzarTurno.AVANZAR_PUSH);
                return evento;
            } else if (camino.equals(AWCalificacion.ESPECIALIZADO)) {
                //Tiene validacion de que no tenga informacion temporal el turno
                session.setAttribute(WebKeys.EXCEPCION, true);

                EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_ESPECIALIZADO);
                evento.setTurno(turno);
                evento.setUsuarioNeg(userNeg);
                evento.setFase(fase);
                evento.setRespuestaWF(EvnCalificacion.WF_ESPECIALIZADO);
                evento.setTipoAvance(CAvanzarTurno.AVANZAR_PUSH);
                return evento;
            } else if (camino.equals(AWCalificacion.FIRMA_REGISTRO_CONFIRMAR)) {
                // respuesta = EvnCalificacion.WF_FIRMA_REGISTRO_CONFIRMAR;
                EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_FIRMA);
                ev.setTurno(turno);
                ev.setFase(fase);
                ev.setUsuarioNeg(userNeg);
                ev.setRespuestaWF(CRespuesta.CONFIRMAR);
                return ev;
            } else if (camino.equals(AWCalificacion.FIRMA_REGISTRO_DEVOLVER)) {
                //respuesta = EvnCalificacion.WF_MESA_CONTROL_CALIFICACION;
                EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.DEVOLVER_FIRMA);
                evento.setRespuestaWF(CRespuesta.CORRECCION_CALIFICACION);
                evento.setUsuarioNeg(usuario);
                evento.setTurno(turno);
                evento.setFase(fase);
                evento.setUsuarioNeg(userNeg);
                evento.setTipoAvance(CAvanzarTurno.AVANZAR_NORMAL);
                return evento;
            } else if (camino.equals(AWCalificacion.MESA_CONTROL_CONFIRMAR)) {
                respuesta = EvnCalificacion.WF_MESA_CONTROL_CONFIRMAR;
                EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, turno, fase, userNeg, respuesta);
                evento.setTipoEvento(EvnCalificacion.AVANZAR);
                evento.setUsuarioNeg(usuario);
                return evento;
            } else if (camino.equals(AWCalificacion.ENTREGA_CONFIRMAR)) {
                EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_ENTREGA);
                ev.setTurno(turno);
                ev.setFase(fase);
                ev.setUsuarioNeg(userNeg);
                ev.setRespuestaWF(CRespuesta.CONFIRMAR);
                return ev;
            } else if (camino.equals(AWCalificacion.SUSPENSION_TEMPORAL_DEL_TRAMITE_REGISTRO)) {

                Turno turnoActual = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
                Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
                List notas = (List) turnoActual.getNotas();
                Iterator itTiposNotas = notas.iterator();
                boolean band = false;
                while (itTiposNotas.hasNext()) {
                    Nota elemento = (Nota) itTiposNotas.next();
                    String idTipoNota = elemento.getTiponota().getIdTipoNota();
                    if (proceso.getIdProceso() == 14 && validarIdTipoNota(request, idTipoNota) && idTipoNota != null) {
                        band = true;
                    }
                }

                if (!band) {
                    throw new AccionWebException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
                } else {
                    EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_TRAMITE_SUSP);
                    ev.setTurno(turno);
                    ev.setFase(fase);
                    ev.setUsuarioNeg(userNeg);
                    ev.setRespuestaWF(CRespuesta.TRAMITE_SUSP_TEMP);
                    return ev;
                }

            } else if (camino.equals(AWCalificacion.SUSPENSION_DEL_TRAMITE_REGISTRO_PREVENCION)) {

                Turno turnoActual = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
                Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
                List notas = (List) turnoActual.getNotas();
                Iterator itTiposNotas = notas.iterator();
                boolean band = false;
                while (itTiposNotas.hasNext()) {
                    Nota elemento = (Nota) itTiposNotas.next();
                    String idTipoNota = elemento.getTiponota().getIdTipoNota();
                    if (proceso.getIdProceso() == 14 && validarIdTipoNota(request, idTipoNota) && idTipoNota != null) {
                        band = true;
                    }
                }

                if (!band) {
                    throw new AccionWebException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
                } else {
                    EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_TRAMITE_SUSP);
                    ev.setTurno(turno);
                    ev.setFase(fase);
                    ev.setUsuarioNeg(userNeg);
                    ev.setRespuestaWF(CRespuesta.TRAMITE_SUSP_PREV);
                    return ev;
                }
            } else {
                return null;
            }

        } else {
            throw new AccionWebException("No Selecciono Ninguna Opción de Caso");
        }
    }

    public boolean validarIdTipoNota(HttpServletRequest request, String idNota) {
        List tiposNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS);
        Iterator itTiposNotas = tiposNotas.iterator();
        boolean idTipoNota = false;
        while (itTiposNotas.hasNext()) {
            TipoNota elemento = (TipoNota) itTiposNotas.next();
            if (elemento.getNombre().equals("ENVIO A SUSPENSION DEL TRAMITE DE REGISTRO") && elemento.getIdTipoNota().equals(idNota)) {
                idTipoNota = true;
            }
        }
        return idTipoNota;
    }

    /**
     * @param request
     * @return
     */
    private Evento devolver(HttpServletRequest request) {
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //limpiar session el flag del refresh
        session.removeAttribute(WebKeys.VALIDACION_NOTA_DEVOLUTIVA);

        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        //TODO
        // Variables nunca utilizadas.
        //Estacion estacion = (Estacion)session.getAttribute(WebKeys.ESTACION);
        //Usuario usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        //TODO
        // Variable nunca utilizada.
        //String camino = request.getParameter(RESPUESTAWF);
        request.getSession().setAttribute(WebKeys.EXCEPCION, new Boolean(true));
        return new EvnCalificacion(usuarioAuriga, userNeg, turno, fase, EvnCalificacion.WF_DEVOLUCION, EvnCalificacion.DEVOLVER);

    }

    /**
     * @param request
     * @return
     */
    private Evento inscripcionParcial(HttpServletRequest request) {
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //limpiar session el flag del refresh
        session.removeAttribute(WebKeys.VALIDACION_NOTA_DEVOLUTIVA);

        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        //TODO
        // Variables nunca utilizadas.
        //Estacion estacion = (Estacion)session.getAttribute(WebKeys.ESTACION);
        //Usuario usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        //TODO
        // Variable nunca utilizada.
        //String camino = request.getParameter(RESPUESTAWF);
        String UID = request.getSession().getId();
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String idCirculo = circulo.getIdCirculo();

        //Obtener turnos listados.
        List foliosInscripcionParcial = (List) session.getAttribute(AWCalificacion.FOLIOS_INSCRIPCION_PARCIAL);
        String ImprimirQuest = request.getParameter(WebKeys.ImprimirCC);
        Integer numCopias = Integer.valueOf((String) session.getAttribute(WebKeys.NUMERO_COPIAS_IMPRESION));

        request.getSession().setAttribute(WebKeys.EXCEPCION, new Boolean(true));
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, userNeg, turno, fase, EvnCalificacion.WF_INSCRIPCION_PARCIAL, EvnCalificacion.INSCRIPCION_PARCIAL);
        if (fase.getID().equals(CFase.CAL_CALIFICACION)) {
            if (ImprimirQuest != null) {
                evento.setImprimirYN(1);
            } else {
                evento.setImprimirYN(1);
            }
        } else {
            evento.setImprimirYN(numCopias);
        }
        evento.setSolicitudFoliosInscripcionParcial(foliosInscripcionParcial);
        evento.setIdCirculo(idCirculo);
        evento.setUID(UID);
        if (numCopias != null) {
            evento.setNumeroCopias(numCopias.intValue());
            session.removeAttribute(WebKeys.NUMERO_COPIAS_IMPRESION);
        }
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelar(HttpServletRequest request) {

        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiacion_de_Naturaleza_Juridica
         * @Descripcion: Se quita la variable de session
         */
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
        request.getSession().removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        eliminarInfoBasicaVariosCiudadanos(request);
        /*obtencion de DatosRespuestaPaginador para saber si hay un paginador activo y sacar los datos de este*/
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
        if (nombreResultado != null && !nombreResultado.equals("")) {
            DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);

            ArrayList list = new ArrayList();
            if (RPag != null && RPag.getAnotacionesActual() != null) {
                //TODO
                // No es necesario recorrer la lista porque se van a anadir todos los elementos.
                // se cambio el codigo por un addAll.
                /*Iterator it = RPag.getAnotacionesActual().iterator();
					
					int i= 0;
					while(it.hasNext()){
						Object obj = (Object)it.next();
						list.add(obj);
					}*/
                list.addAll(RPag.getAnotacionesActual());
            }
            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, list);

        }
        return null;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento cancelarAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfoCancelacion(request);
        preservarInfoBasicaAnotacionRegistro(request);
        ValidacionParametrosCancelacionException exception = new ValidacionParametrosCancelacionException();
        //List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA);
        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        String[] idsCanc = (String[]) request.getSession().getAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);

        if (idsCanc == null) {
            exception.addError("Debe seleccionar por lo menos una anotacion para cancelar");
            throw exception;
        }

        if (anotaciones == null) {
            anotaciones = new Vector();
        }
        List canceladas = new ArrayList();
        //TODO
        // Iterator nunca utilizado.
        //Iterator itC=anotaciones.iterator();

        // Ordenar Las anotaciones
        for (int i = 0; i < idsCanc.length; i++) {
            Anotacion an = new Anotacion();
            an.setIdMatricula(folio.getIdMatricula());
            an.setIdAnotacion(idsCanc[i]);
            canceladas.add(an);
        }

        /*if(cancelada==null){
				exception.addError("Debe escoger una anotacion a cancelar.");
			}*/
        String valFechaRadicacion = request.getParameter(CFolio.ANOTACION_FECHA_RADICACION);

        Calendar fechaRadicacion = Calendar.getInstance();
        if (valFechaRadicacion != null) {
            fechaRadicacion = darFecha(valFechaRadicacion);
            if (fechaRadicacion == null) {
                exception.addError("La fecha de la radicacion en la anotacion es invalida");
            }
        }

        //Se hacen las validaciones si se requiere validar los campos del Documento, para el caso de correcciones por ejemplo.
        Documento documento = null;
        if (String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CORRECCIONES)) {
            String tipoDocumento = request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
            if ((tipoDocumento == null) || tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe escoger un tipo para el documento de la anotacion");
            }

            String numDocumento = request.getParameter(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);

            if ((numDocumento == null) || numDocumento.equals("")) {
                exception.addError("El valor del numero del documento en la anotacion es invalido");
            }

            String valFechaDocumento = request.getParameter(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
            if ((valFechaDocumento == null) || valFechaDocumento.equals("")) {
                exception.addError("La fecha del documento en la anotacion no puede ser vacia");
            }

            Calendar fechaDocumento = darFecha(valFechaDocumento);
            if (fechaDocumento == null) {
                exception.addError("La fecha del documento en la anotacion es invalida");
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
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Codigo_Notaria_NC
             */
            String oficinaVersion = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
            if ((idOficina == null) || (idOficina.length() <= 0)) {
                exception.addError("Debe seleccionar una entidad publica valida asociada al reparto notarial");
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
                                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Codigo_Notaria_NC
                 */
                oficina.setVersion(Long.parseLong(oficinaVersion));
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

        //Fin Validacion Campos del Documento
        String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        if (numEspecificacion != null) {
            numEspecificacion = numEspecificacion.replaceAll(",", "");
        }
        double valorEspecificacion = 0.0d;
        if (numEspecificacion == null || numEspecificacion.equals("")) {
            numEspecificacion = "0";
        }

        Log.getInstance().debug(AWCalificacion.class, "\n\n Comenzar comparacion de naturalezas juridicas");
        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiacion_de_Naturaleza_Juridica
         * @change : Se define la variable natJuridicia
         */
        NaturalezaJuridica natJuridica = null;
        if (idNaturaleza == null || idNaturaleza.length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza juridica para la especificacion en la anotacion");
        } else {

            //validacion de la idNaturalezaJuridica
            boolean esta = false;
            List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");
            //logger.debug("lista ="+grupoNaturalezas);
            Iterator ig = grupoNaturalezas.iterator();
            while (ig.hasNext() && !esta) {
                GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
                if (group.getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.ID_CANCELACION)) {
                    List natus = group.getNaturalezaJuridicas();
                    Iterator id = natus.iterator();
                    while (id.hasNext()) {
                        NaturalezaJuridica nat = (NaturalezaJuridica) id.next();
                        //logger.debug(nat.getIdNaturalezaJuridica()+"- comparando con -" +idNaturaleza);
                        if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                            /**
                             * @author : Carlos Mario Torres Urina
                             * @casoMantis : 0012705.
                             * @actaReq : Acta - Requerimiento No
                             * 056_453_Modificiacion_de_Naturaleza_Juridica
                             * @change : se asigna valor
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

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Anotacion anotacion = new Anotacion();
        int pos = anotaciones.size() + 1;
        anotacion.setOrden(String.valueOf(pos));

        //anotacion.setFechaRadicacion(fechaRadicacion.getTime());
        if (turno != null) {
            anotacion.setNumRadicacion(turno.getIdWorkflow());
            anotacion.setIdWorkflow(turno.getIdWorkflow());
            anotacion.setFechaRadicacion(turno.getFechaInicio());
        }

        //Se valida si se ingresa un nuevo documento para el caso de correcciones o si se ingresa el de la solicitud(registro)
        if (String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CORRECCIONES)) {
            anotacion.setDocumento(documento);
        } else {
            anotacion.setDocumento(((SolicitudRegistro) turno.getSolicitud()).getDocumento());
        }

        NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
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
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiacion_de_Naturaleza_Juridica
         * @change : Retorna Anotacion temporar.
         * @throws : DAOException.
         */
        naturalezaJuridica.setVersion(natJuridica.getVersion());
        if (nomNatJuridica != null) {
            naturalezaJuridica.setNombre(nomNatJuridica);
        }

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setComentario(comentario);

        //segmento de ingresar ciudadanos
        /**
         * @author: Guillermo Cabrera.
         * @change: Se realiza la validacion para que sea obligatorio el ingreso
         * de ciudadanos cuando se va a crear una anotacion canceladora. Mantis:
         * 3397 Acta - Requerimiento No 187
         */
        if (ciudadanos != null && ciudadanos.size() > 0) {
            Iterator itPersonas = ciudadanos.iterator();
            while (itPersonas.hasNext()) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                ciudadano.setAnotacion(anotacion);
                //ciudadano.setIdMatricula("idMatricula");
                anotacion.addAnotacionesCiudadano(ciudadano);
            }
        } else {
            exception.addError("Debe ingresar por lo menos un ciudadano");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        /* if (exception != null && exception instanceof ValidacionParametrosException && exception.getErrores().size() > 0)
                        {
                            throw exception;
                        }*/
        //fin : segmento de ingresar ciudadanos
        Iterator itCanceladas = canceladas.iterator();
        while (itCanceladas.hasNext()) {
            Cancelacion cancelacion = new Cancelacion();
            cancelacion.setCancelada((Anotacion) itCanceladas.next());
            cancelacion.setCanceladora(anotacion);
            anotacion.addAnotacionesCancelacion(cancelacion);
        }
        //anotacion.setOrden(anotacion.getIdAnotacion());
        TipoAnotacion tipo = new TipoAnotacion();
        tipo.setIdTipoAnotacion(CTipoAnotacion.CANCELACION);
        anotacion.setTipoAnotacion(tipo);

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, folio, anotacion, usuarioNeg);
        evento.setTurno(turno);

        return evento;

    }

    /**
     * @param request
     * @return
     */
    private Evento grabarAnotacionesFolio(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
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
        Log.getInstance().debug(AWCalificacion.class, "enviando a ANCalificacion!!!");
        EvnCalificacion evn = new EvnCalificacion(usuario, folio, anotaciones, usuarioNeg);
        evn.setTurno(turno);
        return evn;

    }

    /**
     * @param request
     * @return
     * @throws ValidacionParametrosException
     */
    private Evento grabarEdicion(HttpServletRequest request) throws ValidacionParametrosException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //Codigo  de grabar Edicion
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);

        //obtencion del folio
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        ValidacionParametrosException exception = null;

        exception = new ValidacionParametrosEdicionCalificacionException();

        //obtencion lista de ciudadanosT en el sesion (Aqui estan tanto los ciudadanos sin editar como los editados)
        List ciudadanosT = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        if (ciudadanosT == null) {
            ciudadanosT = new Vector();
        }

        //obtencion lista de ciudadanosB del folio (aqui estan solo los ciudadanos q existen antes de la edicion)
        List ciudadanosB = null; // se inicializara despues.

        NaturalezaJuridica nat = null;
        boolean cambioNat = false; //flag para saber si se cambio la naturaleza juridica
        String idNaturalezaB = "";
        String nombreNaturalezaB = "";
        String comentarioB = "";
        double valorB = 0;
        String modalidadB = "";

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
            anotacionF = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
        }

        Iterator iaf = anotacionF.iterator();
        while (iaf.hasNext()) {
            Anotacion temp = (Anotacion) iaf.next();
            if (pos.equals(temp.getOrden())) {
                a.setIdAnotacion(temp.getIdAnotacion());

                //se obtiene los datos de la anotacion para compararlos con los datos q esten en la interfaz
                idNaturalezaB = temp.getNaturalezaJuridica().getIdNaturalezaJuridica();
                nombreNaturalezaB = temp.getNaturalezaJuridica().getNombre();
                valorB = temp.getValor();
                comentarioB = temp.getComentario();
                modalidadB = temp.getModalidad();
                nat = temp.getNaturalezaJuridica();

                //obtener la lista de ciudadanos de esa anotacion y guardarla en ciudadanosB
                ciudadanosB = temp.getAnotacionesCiudadanos();
                break;
            }
        }

        if ((a.getIdAnotacion() == null) || (a.getIdAnotacion().equals(""))) {
            exception.addError("La anotacion que va editar no existe.");
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

        if (modalidadB == null) {
            modalidadB = "";
        }

        //fin de obtencion de los datosB (Before)
        Log.getInstance().debug(AWCalificacion.class, "\n\n"
                + "La anotacion a editar tenia los siguientes datos \n"
                + "ID =" + a.getIdAnotacion() + "\n"
                + "idNaturaleza =" + idNaturalezaB + "\n"
                + "nombreNaturaleza =" + nombreNaturalezaB + "\n"
                + "valor =" + Double.toString(valorB) + "\n"
                + "comentario =" + comentarioB + "\n"
                + "modalidad =" + modalidadB + "\n"
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
                exception.addError("El valor de la especificacion en la anotacion es invalido");
            }
        }
        String idNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nombreNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        String comentarioA = (String) request.getSession().getAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        String modalidadA = (String) request.getSession().getAttribute(CFolio.ANOTACION_MODALIDAD);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiacion_de_Naturaleza_Juridica
         */
        NaturalezaJuridica natJuridica = null;
        if (idNaturalezaA == null || idNaturalezaA.length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza juridica para la especificacion en la anotacion");
        }

        if (idNaturalezaA.equals("0125") || idNaturalezaA.equals("0126")) {
            if (modalidadA == null || modalidadA.isEmpty()) {
                exception.addError("Debe seleccionar ingresar una modalidad para la naturaleza juridica en la anotacion");
            }
        }

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
             * 056_453_Modificiacion_de_Naturaleza_Juridica
             * @change : Se agrega condicion al while
             *
             */
            while (id.hasNext() && !esta) {
                NaturalezaJuridica natA = (NaturalezaJuridica) id.next();

                if (natA.getIdNaturalezaJuridica().equals(idNaturalezaA)) {
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiacion_de_Naturaleza_Juridica
                     * @change : se asigan la varible natJuridica
                     */
                    natJuridica = natA;
                    esta = true;
                }
            }
        }
        if (!esta) {
            exception.addError("Debe colocar un codigo de naturaleza juridica valido");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Log.getInstance().debug(AWCalificacion.class, "\n\n"
                + "La anotacion en la interfaz tiene los siguientes datos \n"
                + "idNaturaleza =" + idNaturalezaA + "\n"
                + "nombreNaturaleza =" + nombreNaturalezaA + "\n"
                + "valor =" + Double.toString(valorA) + "\n"
                + "comentario =" + comentarioA + "\n"
                + "modalidad =" + modalidadA + "\n"
                + "fin");

        //comienzo de comparacion y construccion de la anotacion edicion
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiacion_de_Naturaleza_Juridica
         * @change : se asigna valor a la propiedad version
         */
        nat.setVersion(natJuridica.getVersion());
        if (!idNaturalezaB.equals(idNaturalezaA)) {
            nat.setIdNaturalezaJuridica(idNaturalezaA);
            cambioNat = true;
        }
        if (!nombreNaturalezaB.equals(nombreNaturalezaA)) {
            nat.setNombre(nombreNaturalezaA);
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

        if (!modalidadB.equals(modalidadA)) {
            a.setModalidad(modalidadA);
        }

        Log.getInstance().debug(AWCalificacion.class, "\n\n"
                + "La anotacion edicion tiene los siguientes datos");
        if (a.getNaturalezaJuridica() != null) {
            Log.getInstance().debug(AWCalificacion.class, "idNaturaleza =" + a.getNaturalezaJuridica().getIdNaturalezaJuridica() + "\n"
                    + "nombreNaturaleza =" + a.getNaturalezaJuridica().getNombre());
        } else {
            Log.getInstance().debug(AWCalificacion.class, "idNaturaleza =" + "" + "\n"
                    + "nombreNaturaleza =" + "" + "\n");
        }
        Log.getInstance().debug(AWCalificacion.class, "valor =" + Double.toString(a.getValor()) + "\n"
                + "comentario =" + a.getComentario() + "\n"
                + "fin");

        //parte de ciudadanos (aqui se recorrera la lista de ciudadanosT y se comparara con ciudadanosB para crear ciudadanosA)
        List ciudadanosA = new Vector();
        //se obtienen los ciudadanos mandados a eliminar
        Log.getInstance().debug(AWCalificacion.class, "el # de ciudadanosB =" + ciudadanosB.size());
        Log.getInstance().debug(AWCalificacion.class, "el # de ciudadanosT =" + ciudadanosT.size());

        int finAB = ciudadanosB.size();
        int cont = 0;
        while (cont != finAB) {
            AnotacionCiudadano cb = (AnotacionCiudadano) ciudadanosB.get(cont);
            AnotacionCiudadano ct = (AnotacionCiudadano) ciudadanosT.get(cont);
            Log.getInstance().debug(AWCalificacion.class, "ciudadano cedula =" + ct.getCiudadano().getDocumento() + "toDelete = " + Boolean.toString(ct.isToDelete()));
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
        Log.getInstance().debug(AWCalificacion.class, "el tamano de la Sublista =" + SL);

        ciudadanosA.addAll(subList);
        Log.getInstance().debug(AWCalificacion.class, "el # de ciudadanos a editar y/o agregar es = " + ciudadanosA.size());

        /*		Iterator ica= ciudadanosA.iterator();
		while(ica.hasNext()){
			a.addAnotacionesCiudadano((AnotacionCiudadano) ica.next());
		}
         */
        Iterator ica = ciudadanosT.iterator();
        Anotacion anotacionTemp = new Anotacion();
        while (ica.hasNext()) {
            AnotacionCiudadano ciudadano = (AnotacionCiudadano) ica.next();
            if (!ciudadano.isToDelete()) {
                anotacionTemp.addAnotacionesCiudadano(ciudadano);
            }
        }

        //List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        if (anotacionTemp.getAnotacionesCiudadanos() == null || anotacionTemp.getAnotacionesCiudadanos().isEmpty()) {
            exception.addError("Debe ingresar por lo menos un ciudadano");
        }

        ica = ciudadanosT.iterator();
        while (ica.hasNext()) {
            a.addAnotacionesCiudadano((AnotacionCiudadano) ica.next());
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
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
        List lstAnotacionesTempAux = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);
        Iterator iterAnota = lstAnotacionesTempAux.iterator();
        List lstAnotacionesTemp = null;
        while (iterAnota.hasNext()) {
            Anotacion anota = (Anotacion) iterAnota.next();
            if (anota.isTemporal()) {
                if (lstAnotacionesTemp == null) {
                    lstAnotacionesTemp = new ArrayList();
                }
                lstAnotacionesTemp.add(anota);
            }
        }

        EvnCalificacion evn = new EvnCalificacion(usuario, folio, anotaciones, usuarioNeg);
        evn.setAnotacionesTem(lstAnotacionesTemp);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        evn.setTurno(turno);
        return evn;

    }

    /**
     * @param request
     * @return
     * @throws ValidacionParametrosException
     */
    //Modifico: (DAHU)Diego Alejandro Hernandez U. 06/06/2008
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

        /*logger.debug("\n\n" +
			"La Cancelacion a editar tenia los siguientes datos \n" +
			"ID =" + a.getIdAnotacion()+"\n"+
			"idNaturaleza =" + idNaturalezaB+"\n"+
			"nombreNaturaleza =" + nombreNaturalezaB + "\n"+
			"valor =" + valorB + "\n"+
			"idAnotacion a canelar =" +numCanceladaB + "\n" +
			"comentario ="+ comentarioB + "\n"+
			"fin");
         */
        //Comienzo de los datosA (After)
        String idNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String nombreNaturalezaA = (String) request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        String comentarioA = (String) request.getSession().getAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        //String numCanceladaA= request.getParameter("ESCOGER_ANOTACION_CANCELACION");
        String sValorA = (String) request.getSession().getAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiacion_de_Naturaleza_Juridica
         * @change : se define la variable natjuridica
         */
        NaturalezaJuridica natJuridica = null;
        //String[] idsCanc = (String[])request.getSession().getAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);

        // must be collected in the paginator
        final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID = "TARGET_SELECTIONCOLLECTOR";

        String idsAnotacionesCsv = request.getParameter(LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID);

        // only the last is added
        String[] idsCanc = gov.sir.core.web.helpers.comun.JvLocalUtils.csvStringToStringArray(idsAnotacionesCsv, gov.sir.core.web.helpers.comun.JvLocalUtils.DEFAULT_SEPARATOR, true);

        double valorA = 0;

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
             * 056_453_Modificiacion_de_Naturaleza_Juridica
             * @change : Se agrega condicion al while
             */
            while (id.hasNext() && !esta) {
                NaturalezaJuridica natA = (NaturalezaJuridica) id.next();

                if (natA.getIdNaturalezaJuridica().equals(idNaturalezaA)) {
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiacion_de_Naturaleza_Juridica
                     * @change : se asigan la varible natJuridica
                     */
                    natJuridica = natA;
                    esta = true;
                }
            }
        }
        if (!esta) {
            exception.addError("Debe colocar un codigo de naturaleza juridica valido");
        }

        if (idsCanc == null) {
            exception.addError("Debe seleccionar por lo menos una anotacion para cancelar");
            throw exception;
        }

        if (sValorA == null) {
            exception.addError("Debe colocar un valor");
        }

        if (sValorA != null) {
            sValorA = sValorA.replaceAll(",", "");
        }

        if ((sValorA != null) && !sValorA.equals("")) {
            try {
                valorA = Double.parseDouble(sValorA);
            } catch (NumberFormatException e) {
                exception.addError("El valor de la especificacion en la anotacion es invalido");
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        /*logger.debug("\n\n" +
			"La anotacion en la interfaz tiene los siguientes datos \n" +
			"valor = " + sValorA+"\n"+
			"idNaturaleza =" + idNaturalezaA+"\n"+
			"nombreNaturaleza =" + nombreNaturalezaA + "\n"+
			"idAnotacion a canelar =" +numCanceladaA + "\n" +
			"comentario ="+ comentarioA + "\n"+
			"fin");*/
        //comienzo de comparacion y construccion de la anotacion edicion
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiacion_de_Naturaleza_Juridica
         * @change :
         */
        nat.setVersion(natJuridica.getVersion());
        if (!idNaturalezaB.equals(idNaturalezaA)) {
            nat.setIdNaturalezaJuridica(idNaturalezaA);
            cambioNat = true;
        }
        if (!nombreNaturalezaB.equals(nombreNaturalezaA)) {
            nat.setNombre(nombreNaturalezaA);
            cambioNat = true;
        }
        if (cambioNat) {
            a.setNaturalezaJuridica(nat);
        }
        //se quitan todas las que esta cancelando y se crean las nuevas

        List anotaCancel = anotAnt.getAnotacionesCancelacions();

        Iterator anotaCancelIterator = anotaCancel.iterator();

        /* // iterate oover all anotaciones-to-be cancelled
	      
	      Anotacion local_AnotacionToBeCanceledItem;
	      String local_AnotacionToBeCanceledItemId;*/
        Cancelacion local_CancelacionItem;
        String casoCancelacion = "";

        for (; anotaCancelIterator.hasNext();) {
            local_CancelacionItem = (Cancelacion) anotaCancelIterator.next();
            boolean entro = false;
            for (int i = 0; i < idsCanc.length; i++) {
                if (local_CancelacionItem.getIdAnotacion1().equals(idsCanc[i])) {
                    casoCancelacion = "EXISTE";
                    entro = true;
                    break;
                }
            }
            if (!entro) {
                casoCancelacion = "ELIMINAR";
                local_CancelacionItem.setToDelete(true);
                a.addAnotacionesCancelacion(local_CancelacionItem);
            }
        }

        for (int i = 0; i < idsCanc.length; i++) {
            Cancelacion local_CancelacionItem2;
            boolean entro = false;
            Iterator anotaCancelIterator2 = anotaCancel.iterator();
            for (; anotaCancelIterator2.hasNext();) {
                local_CancelacionItem2 = (Cancelacion) anotaCancelIterator2.next();
                if (local_CancelacionItem2.getIdAnotacion1().equals(idsCanc[i])) {
                    entro = true;
                    break;
                }
            }
            if (!entro) {
                //SE DEBE AGREGAR PORQUE ES NUEVA
                Anotacion an = new Anotacion();
                an.setIdMatricula(folio.getIdMatricula());
                an.setIdAnotacion(idsCanc[i]);
                Cancelacion cancelacion = new Cancelacion();
                cancelacion.setCancelada(an);
                cancelacion.setCanceladora(a);
                a.addAnotacionesCancelacion(cancelacion);
            }
        }

        if (valorA != valorB) {
            a.setValor(valorA);
            a.setToUpdateValor(true);
        }
        if (!comentarioB.equals(comentarioA)) {
            a.setComentario(comentarioA);
        }

        /*logger.debug("\n\n" +
			"La anotacion edicion tiene los siguientes datos");
		logger.debug("valor = " + Double.toString(a.getValor()));*/
 /*if(a.getNaturalezaJuridica()!=null){
			logger.debug(	"idNaturaleza =" + a.getNaturalezaJuridica().getIdNaturalezaJuridica()+ "\n"+
				"nombreNaturaleza =" + a.getNaturalezaJuridica().getNombre());
		}else{
			logger.debug(	"idNaturaleza =" + "" +"\n"+
								"nombreNaturaleza =" + "" + "\n");
		}*/
 /*if(a.getAnotacionesCancelacions().size()>0){
			logger.debug(	"idAnotacion a cancelar = se adiciona -->" + c.getCancelada().getOrden() +"se elimina --> "+ ca.getCancelada().getOrden()+ "\n");
		}else{
			logger.debug(	"idAnotacion a cancelar =" + "" + "\n");
		}*/
 /*	logger.debug("comentario ="+ a.getComentario() + "\n"+
			"fin");*/
        //parte de ciudadanos (aqui se recorrera la lista de ciudadanosT y se comparara con ciudadanosB para crear ciudadanosA)
        List ciudadanosA = new Vector();

        //Si la lista de ciudadanos temporales (ciudadanosT) es inmodificable, indica que
        //no fueron agregados ni eliminados ciudadanos, por lo tanto no se realizan los
        //recorridos de los ciudadanos temporales ni definitivos para ver que se guarda (ya
        //que no hay nada que guardar).
        //(DAHU)
        if (!"java.util.Collections$UnmodifiableRandomAccessList".equals(ciudadanosT.getClass().getName())) {
            int finAB = ciudadanosB.size();

            //Se crea la variable booleana hayCiudadanos, si al menos uno de los 
            //ciudadanos temporales no esta marcado como borrado esto indica que 
            //hay ciudadanos en la lista.
            //(DAHU)
            boolean hayCiudadanos = false;

            //Se crea una lista de ciudadanos temporal clone de la
            //lista ciudadanosT, sobre el clon se realiza la substraccion de elementos
            //para no trabajar directamente en la lista de ciudadanosT.
            List ciudadanosTClone = new ArrayList();
            ciudadanosTClone.addAll(ciudadanosT);

            //Se comparan las dos listas (ciudadanos temporales clone y definitivos) y de la 
            //lista de temporales clone se remueven aquellos ciudadanos que estan en definitivas y
            //si el ciudadano definitivo ademas esta marcado para borrar se agrega a 
            //lista ciudadanosA.  
            //(DAHU)
            for (int contCiudadanosB = 0; contCiudadanosB < ciudadanosB.size(); contCiudadanosB++) {
                AnotacionCiudadano cb = (AnotacionCiudadano) ciudadanosB.get(contCiudadanosB);
                Iterator iteradorCiudadanosT = ciudadanosTClone.iterator();
                while (iteradorCiudadanosT.hasNext()) {
                    AnotacionCiudadano ct = (AnotacionCiudadano) iteradorCiudadanosT.next();
                    if (cb.getIdCiudadano().equals(ct.getIdCiudadano())) {
                        if (ct.isToDelete()) {
                            ciudadanosA.add(ct);
                        } else {
                            hayCiudadanos = true;
                        }
                        iteradorCiudadanosT.remove();
                        break;
                    }
                }
            }

            //Se recorre la lista de ciudadanos temporales clone (la que quedo al quitarle
            //los ciudadanos definitivos) y se remueven de la lista aquellos ciudadanos
            //que estan marcados para borrar.
            //Si al menos uno de los ciudadanos temporales clone no esta marcado como borrado 
            //esto indica que hay ciudadanos en la lista y asigna true a la variable
            //hayCiudadanos.
            //(DAHU)
            Iterator iteradorCiudadanosT = ciudadanosTClone.iterator();
            while (iteradorCiudadanosT.hasNext()) {
                AnotacionCiudadano ci = (AnotacionCiudadano) iteradorCiudadanosT.next();
                if (ci.isToDelete()) {
                    iteradorCiudadanosT.remove();
                } else {
                    hayCiudadanos = true;
                }
            }

            //A la lista ciudadanosA se le agregan los ciudadanos temporales clone que 
            //quedaron (despues de realizar los dos filtros anteriores).
            //(DAHU)
            ciudadanosA.addAll(ciudadanosTClone);
            //logger.debug("el # de ciudadanos a editar y/o agregar es = "+ ciudadanosA.size());

            Iterator ica = ciudadanosA.iterator();
            while (ica.hasNext()) {
                a.addAnotacionesCiudadano((AnotacionCiudadano) ica.next());
            }

            //Valida que haya al menos un ciudadano.
            //(DAHU)
            if (a.getAnotacionesCiudadanos() == null || a.getAnotacionesCiudadanos().isEmpty()) {
                exception.addError("Debe ingresar por lo menos un ciudadano");
            } else {
                //Si la variable hayCiudadanos es false indica que no hay
                //ciudadanos.
                //(DAHU)
                if (!hayCiudadanos) {
                    exception.addError("Debe ingresar por lo menos un ciudadano");
                }
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        //codigo de grabar en temporal
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        request.getSession().setAttribute(WebKeys.FOLIO, folio);

        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        //logger.debug("enviando a ANCalificacion!!!");

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, folio, a, usuarioNeg);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        evento.setTurno(turno);
        return new EvnCalificacion(usuarioAuriga, folio, a, usuarioNeg);

    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento agregarAnotacion(HttpServletRequest request) throws AccionWebException {
        //ola1
        preservarInfo(request);
        preservarInfoBasicaAnotacionRegistro(request);
        this.preservarInfoBasicaVariosCiudadanos(request);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        request.getSession().setAttribute(WebKeys.TIENE_ANOTACIONES_TMP, true);
        if (anotaciones == null) {
            anotaciones = new Vector();
        }

        ValidacionParametrosException exception = null;
        if (folio == null) {
            exception = new ValidacionParametrosAnotacionEnglobeException();
        } else {
            exception = new ValidacionParametrosAnotacionCalificacionException();
        }
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        if (numEspecificacion != null) {
            numEspecificacion = numEspecificacion.replaceAll(",", "");
        }
        double valorEspecificacion = 0.0d;

        if ((numEspecificacion != null) && !numEspecificacion.equals("")) {
            try {
                valorEspecificacion = Double.parseDouble(numEspecificacion);
                if (valorEspecificacion < 0) {
                    exception.addError("El valor de la especificacion no puede ser negativo o cero");
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor de la especificacion en la anotacion es invalido");
            }
        }

        if (ciudadanos == null || ciudadanos.isEmpty()) {
            exception.addError("Debe ingresar por lo menos un ciudadano");
        }

        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String modalidad = request.getParameter(CFolio.ANOTACION_MODALIDAD);
        String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiacion_de_Naturaleza_Juridica
         * @change :
         */
        NaturalezaJuridica natJuridica = null;
        if (idNaturaleza == null || idNaturaleza.length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza juridica para la especificacion en la anotacion");
        }

        if (idNaturaleza.equals("0125") || idNaturaleza.equals("0126")) {
            if (modalidad == null || modalidad.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar una modalidad para esta naturaleza juridica");
            }
        }
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
             * 056_453_Modificiacion_de_Naturaleza_Juridica
             * @change : Se agrega condicion al while
             */

            while (id.hasNext() && !esta) {
                NaturalezaJuridica nat = (NaturalezaJuridica) id.next();

                if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiacion_de_Naturaleza_Juridica
                     * @change :se asigan la varible natJuridica
                     */

                    natJuridica = nat;
                    esta = true;
                }
            }
        }
        if (!esta) {
            exception.addError("Debe colocar un codigo de naturaleza juridica valido");
        }

        String comentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        String salvedad = request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);

        String idMatricula = (String) request.getSession().getAttribute(CFolio.ID_MATRICULA);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Anotacion anotacion = new Anotacion();
        int pos = 1;
        if (folio != null) {
            pos += folio.getAnotaciones().size();
        }

        if (anotaciones != null) {
            pos += anotaciones.size();
        }

        anotacion.setOrden(String.valueOf(pos));
        anotacion.setNumRadicacion(turno.getIdWorkflow());
        anotacion.setIdWorkflow(turno.getIdWorkflow());
        anotacion.setFechaRadicacion(turno.getFechaInicio());
        anotacion.setDocumento(((SolicitudRegistro) turno.getSolicitud()).getDocumento());
        anotacion.setModalidad(modalidad);
        NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
        if ((numEspecificacion != null) && !numEspecificacion.equals("")) {
            try {
                valorEspecificacion = Double.parseDouble(numEspecificacion);
                if (valorEspecificacion <= 0) {
                    exception.addError("El valor de la especificacion no puede ser negativo o cero");
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor de la especificacion en la anotacion es invalido");
            }
            valorEspecificacion = Double.parseDouble(numEspecificacion);
        }
        anotacion.setValor(valorEspecificacion);

        naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiacion_de_Naturaleza_Juridica
         * @change :
         *
         */
        naturalezaJuridica.setVersion(natJuridica.getVersion());
        if (nomNatJuridica != null) {
            naturalezaJuridica.setNombre(nomNatJuridica);
        }

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setComentario(comentario);
        if (salvedad != null) {
            SalvedadAnotacion salvedadAnotacion = new SalvedadAnotacion();
            salvedadAnotacion.setDescripcion(salvedad);
            //salvedadAnotacion.setIdAnotacion(idRadicacion);
            if (idMatricula != null) {
                salvedadAnotacion.setIdMatricula(idMatricula);
            }
            anotacion.addSalvedade(salvedadAnotacion);
        }

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
        TipoAnotacion tipoAn = new TipoAnotacion();
        tipoAn.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
        anotacion.setTipoAnotacion(tipoAn);
        if (turno.getIdProceso() == 6 && turno.getIdFase().equals("CAL_CALIFICACION")) {
            anotacion.setTemporal(true);
        }
        String norder = (String) request.getSession().getAttribute(CFolio.NEXT_ORDEN_ANOTACION);
        anotacion.setOrden(norder);

        //LISTA CON LA ANOTACION QUE DESEA GUARDARSE
        List anotacionGrabar = new ArrayList();
        anotacionGrabar.add(anotacion);

        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");
        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        /*if (anotaciones != null) {
				Iterator itAnotaciones = anotaciones.iterator();
				while (itAnotaciones.hasNext()) {
					folio.addAnotacione((Anotacion) itAnotaciones.next());
				}
			}else{
				return null;
			}
			request.getSession().setAttribute(WebKeys.FOLIO, folio);
         */
        //request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        EvnCalificacion evn = new EvnCalificacion(usuario, folio, anotacionGrabar, usuarioNeg);
        evn.setGenerarNextOrden(true);
        evn.setAnotacionesAgregadas(anotaciones);
        evn.setTurno(turno);

        return evn;
    }

    /**
     * @param request
     * @return
     */
    private Evento mostrar(HttpServletRequest request) {

        Boolean okValidarCalificacion = (Boolean) request.getSession().getAttribute(WebKeys.APROBAR_CALIFICACION);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario u = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.VALIDAR_APROBAR_CALIFICACION);
        evento.setTurno(turno);
        evento.setUsuarioNeg(u);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento calificarFolio(HttpServletRequest request) {
        String idMatricula = request.getParameter("ITEM");
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario u = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        request.getSession().removeAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS);
        request.getSession().removeAttribute(WebKeys.LISTA_ID_FOLIOS_SEGREGADOS_EDITADOS);
        request.getSession().removeAttribute(WebKeys.WEB_SEGREGACION);
        eliminarInfoBasicaFolio(request);
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaCiudadano(request);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        LLavesMostrarFolioHelper llaves = (LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
        if (llaves != null) {
            llaves.removeLLaves(request);
        }
        request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
        request.getSession().removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);
        EvnCalificacion evn = new EvnCalificacion(usuarioAuriga, EvnFolio.CONSULTA, folio, u);
        evn.setTurno(turno);
        return evn;
    }

    /**
     * @param request
     * @return
     */
    private Evento cerrarFolio(HttpServletRequest request) throws AccionWebException {
        String idMatricula = request.getParameter("ITEM");
        String razonCierre = request.getParameter(AWCalificacion.RAZON_CERRAR_FOLIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario u = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);

        if (razonCierre == null || razonCierre.equals("")) {
            throw new ParametroInvalidoException("Es necesario ingresar la razon de cierre de un folio");
        }

        EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.CERRAR_FOLIO);
        ev.setUsuarioNeg(u);
        ev.setIdMatricula(idMatricula);
        ev.setRazonCierre(razonCierre);
        ev.setTurno(turno);
        return ev;
    }

    /*metodo para cerrar una lista de folios de una
	 * */
    private Evento cerrarFolios(HttpServletRequest request) throws AccionWebException {
        String idMatricula = request.getParameter("ITEM");
        //String idMatricula = request.getParameterValues(arg0)("ITEM");
        //request.GET["hola"];
        List lstFolios = new ArrayList();
        Map param_map = request.getParameterMap();
        if (param_map != null) {
            Iterator iterator = param_map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry me = (Map.Entry) iterator.next();
                String idCheck = me.getKey().toString();
                if (idCheck != null && idCheck.equals("ckBoxCerrar")) {
                    String[] param = (String[]) me.getValue();
                    for (int i = 0; i < param.length; i++) {
                        lstFolios.add(param[i]);
                    }
                }
            }
        }

        String razonCierre = request.getParameter(AWCalificacion.RAZON_CERRAR_FOLIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario u = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        //request.get
        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);

        if (razonCierre == null || razonCierre.equals("")) {
            throw new ParametroInvalidoException("Es necesario ingresar la razon de cierre de un folio");
        }

        EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.CERRAR_FOLIOS);
        ev.setUsuarioNeg(u);
        //ev.setIdMatricula(idMatricula);
        ev.setRazonCierre(razonCierre);
        ev.setLstFolios(lstFolios);

        ev.setTurno(turno);
        return ev;
    }

    /**
     * @param request
     * @return
     */
    private Evento enviarCorreccionEncabezado(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //Es necesario validar que se haya ingresado la nota informativa con el motivo
        //de la delegacion hacia confrontacion.
        String motivoDevolucion = request.getParameter(AWCalificacion.NOTA_INFORMATIVA_ENCABEZADO);
        if (motivoDevolucion == null || motivoDevolucion.length() == 0) {
            throw new ParametroInvalidoException("Es necesario ingresar el motivo de la delegacion del turno a correccion de encabezado");
        }

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        return new EvnCalificacion(usuario, userNeg, turno, fase, EvnCalificacion.WF_ENVIAR_CORRECCION_ENCABEZADO, CAvanzarTurno.AVANZAR_PUSH, AWCalificacion.ENVIAR_CORRECCION_ENCABEZADO, motivoDevolucion);
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
                                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Codigo_Notaria_NC
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
                                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Codigo_Notaria_NC
             */
            request.getSession().setAttribute("version", request.getParameter("version"));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiacion_de_Naturaleza_Juridica
         * @change :
         */
        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER));
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
     * @return
     * @throws ValidacionParametrosException
     */
    private Evento segregarHerencia(HttpServletRequest request) throws ValidacionParametrosException {

        String sIdDocumento = (String) request.getSession().getAttribute(
                CFolio.ANOTACION_ID_DOCUMENTO);

        String sIdNatJur = (String) request.getSession().getAttribute(
                CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);

        String sComentario = (String) request.getSession().getAttribute(
                CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        String sOrden = (String) request.getSession().getAttribute(
                CFolio.NEXT_ORDEN_ANOTACION);

        String sValor = (String) request.getSession().getAttribute(
                CFolio.ANOTACION_VALOR_ESPECIFICACION);
        if (sValor != null) {
            sValor = sValor.replaceAll(",", "");
        }
        //VALIDACIONES
        ValidacionParametrosException exception = new ValidacionParametrosException();

        //NULOS
        if (sIdDocumento == null || sIdDocumento.equals("")) {
            exception.addError("Id de documento invalido");
        }
        if (sIdNatJur == null || sIdNatJur.equals("")) {
            exception.addError("Id de naturaleza juridica invalida");
        }
//		if(sComentario == null || sComentario.equals(""))
//			exception.addError("Comentario invalido");
        if (sOrden == null || sOrden.equals("")) {
            exception.addError("Orden invalido");
        }
        if (sValor == null || sValor.equals("")) {
            exception.addError("Valor invalido");
        }

        double valor = 0;
        try {
            valor = Double.parseDouble(sValor);
        } catch (NumberFormatException e) {
            exception.addError("Valor no es un numero");
        }

        if (exception.getErrores().size() < 1) {
            //OBJETOS AUXILIARES
            Documento documento = new Documento();
            documento.setIdDocumento(sIdDocumento);
            NaturalezaJuridica natJur = new NaturalezaJuridica();
            natJur.setIdNaturalezaJuridica(sIdNatJur);

            //OBJETO ANOTACION
            Anotacion anotacion = new Anotacion();
            anotacion.setDocumento(documento);
            anotacion.setNaturalezaJuridica(natJur);
            anotacion.setComentario(sComentario);
            anotacion.setOrden(sOrden);
            anotacion.setValor(valor);

            request.getSession().setAttribute(CFolio.ANOTACION, anotacion);
        } else {
            throw exception;
        }

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento segregarAnotacion(HttpServletRequest request) {

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        WebSegregacion webSegregacion = (WebSegregacion) request.getSession().getAttribute(WebKeys.WEB_SEGREGACION);
        if (webSegregacion == null) {
            webSegregacion = new WebSegregacion();
            String idProc = "" + turno.getIdProceso();
            webSegregacion.setIdProceso(new Integer(idProc).intValue());
        }

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.CONSULTAR_SEG_ENG_EXISTENTES);
        evento.setFolio(folio);
        evento.setTurno(turno);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento delegarConfrontacion(HttpServletRequest request) throws AccionWebException {

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //Es necesario validar que se haya ingresado la nota informativa con el motivo
        //de la delegacion hacia confrontacion.
        String motivoDevolucion = request.getParameter(AWCalificacion.NOTA_INFORMATIVA_CONFRONTACION);
        if (motivoDevolucion == null || motivoDevolucion.length() == 0) {
            throw new ParametroInvalidoException("Es necesario ingresar el motivo de la delegacion del turno a confrontacion");
        }
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        String accion1 = EvnCalificacion.CONFRONTACION;
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

        return new EvnCalificacion(usuarioAuriga, turno, fase, userNeg, EvnCalificacion.WF_CONFRONTACION, accion1, circulo.getIdCirculo(), motivoDevolucion);
    }

    /**
     * @param request
     * @return
     */
    private Evento segregarAnotaciones(HttpServletRequest request) {
        List escogidas = new ArrayList();
        String[] anota = request.getParameterValues("Anotaciones");
        //List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        List anotaciones = ((Folio) request.getSession().getAttribute("FOLIO_ENGLOBE")).getAnotaciones();
        Iterator it = anotaciones.iterator();

        if (anota != null) {
            while (it.hasNext()) {
                Anotacion anotacion = (Anotacion) it.next();
                for (int i = 0; i < anota.length; i++) {
                    String an = anota[i];
                    if (anotacion.getOrden().equals(an)) {
                        escogidas.add(anotacion);
                    }
                }
            }
        }
        //Aca se setean los atributos para que se vea la misma info del folio escogido
        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_SEGREGACION, escogidas);
        return null;

    }

    /**
     * @param request
     * @return
     */
    private Evento terminarAnotacionesEnglobe(HttpServletRequest request) {
        Hashtable tabla = (Hashtable) request.getSession().getAttribute("TABLA_ANOTACIONES_ENGLOBE");
        if (tabla == null) {
            tabla = new Hashtable();
        }
        Enumeration e = tabla.elements();
        List escogidas = new ArrayList();
        while (e.hasMoreElements()) {
            List anotaciones = (List) e.nextElement();
            escogidas.addAll(anotaciones);
        }

        Complementacion comp = (Complementacion) request.getSession().getAttribute(WebKeys.COMPLEMENTACION_ENGLOBE);
        if (comp != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, comp.getComplementacion());
        }

        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, escogidas);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarComplementacionEnglobe(HttpServletRequest request) {
        request.getSession().removeAttribute(WebKeys.COMPLEMENTACION_ENGLOBE);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento grabacionTemporalEnglobe(HttpServletRequest request) {
        List escogidas = new ArrayList();
        String[] anota = request.getParameterValues("Anotaciones");
        String matricula = request.getParameter("MATRICULA");
        Folio folio = (Folio) request.getSession().getAttribute("FOLIO_ENGLOBE");
        List anotaciones = folio.getAnotaciones();
        //List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        Hashtable tabla = (Hashtable) request.getSession().getAttribute("TABLA_ANOTACIONES_ENGLOBE");
        if (tabla == null) {
            tabla = new Hashtable();
        }
        Iterator it = anotaciones.iterator();

        if (anota != null && anota.length > 0) {
            while (it.hasNext()) {
                Anotacion anotacion = (Anotacion) it.next();
                for (int i = 0; i < anota.length; i++) {
                    String an = anota[i];
                    if (anotacion.getOrden().equals(an)) {
                        escogidas.add(anotacion);
                    }
                }
            }

            tabla.put(matricula, escogidas);
            request.getSession().setAttribute("Anotaciones", anota);
            request.getSession().setAttribute("TABLA_ANOTACIONES_ENGLOBE", tabla);
            request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
            request.getSession().removeAttribute("COMPLEMENTACION_FOLIO_ENGLOBE");
        } else {
            tabla.remove(matricula);
            request.getSession().setAttribute("TABLA_ANOTACIONES_ENGLOBE", tabla);
            request.getSession().removeAttribute("Anotaciones");
        }
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento agregarComplementacionEnglobe(HttpServletRequest request) {
        Complementacion comp = (Complementacion) request.getSession().getAttribute("COMPLEMENTACION_FOLIO_ENGLOBE");
        request.getSession().setAttribute(WebKeys.COMPLEMENTACION_ENGLOBE, comp);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento englobar(HttpServletRequest request) {
        request.getSession().removeAttribute(WebKeys.FOLIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        WebEnglobe webEnglobe = (WebEnglobe) request.getSession().getAttribute(WebKeys.WEB_ENGLOBE);
        if (webEnglobe == null) {
            webEnglobe = new WebEnglobe();
            String idProc = "" + turno.getIdProceso();
            webEnglobe.setIdProceso(new Integer(idProc).intValue());
        }

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        return new EvnCalificacion(usuarioAuriga, EvnCalificacion.CONSULTAR_SEG_ENG_EXISTENTES, turno, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento cargarAnotaciones(HttpServletRequest request) {
        String matricula = request.getParameter("MATRICULA");
        List fol = (List) request.getSession().getAttribute("LISTA_FOLIOS_ENGLOBE");
        Iterator it = fol.iterator();
        while (it.hasNext()) {
            Folio f = (Folio) it.next();
            if (f.getIdMatricula().equals(matricula)) {
                request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, f.getAnotaciones());
                request.getSession().setAttribute("COMPLEMENTACION_FOLIO_ENGLOBE", f.getComplementacion());
                request.getSession().setAttribute("FOLIO_ENGLOBE", f);
                Hashtable tabla = (Hashtable) request.getSession().getAttribute("TABLA_ANOTACIONES_ENGLOBE");
                if (tabla != null) {
                    if (tabla.containsKey(matricula)) {
                        List anotaciones = (List) tabla.get(matricula);
                        String[] escog = new String[anotaciones.size()];
                        for (int i = 0; i < anotaciones.size(); i++) {
                            Anotacion anota = (Anotacion) anotaciones.get(i);
                            escog[i] = anota.getOrden();
                        }
                        request.getSession().setAttribute("Anotaciones", escog);
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param request
     * @return
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
        Log.getInstance().debug(AWCalificacion.class, "Editando anotacion # --> " + n);
        Iterator ianot = anotacionesTemporales.iterator();
        while (ianot.hasNext()) {
            Anotacion temp = (Anotacion) ianot.next();
            if (temp.getOrden().equals(num)) {
                a = temp;
            }
        }

        a.setToDelete(true);

        //codigo de grabar en temporal
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        request.getSession().setAttribute(WebKeys.FOLIO, folio);

        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        List lstAnotacionesTemp = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, folio, a, usuarioNeg, EvnCalificacion.ELIMINAR_ANOTACION);
        evento.setProceso(new Proceso(new Long(CProceso.PROCESO_REGISTRO).longValue(), "", ""));
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        evento.setTurno(turno);
        evento.setAnotaciones(lstAnotacionesTemp);

        return evento;
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
            ver = request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);
        }
        request.getSession().setAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL, ver);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento editarCancelacion(HttpServletRequest request) {
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
            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA, list);
        } else {
            ver = request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);
        }
        request.getSession().setAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL, ver);

        Anotacion local_Anotacion = new Anotacion();

        List anotacionT = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO);

        Iterator iaf = anotacionT.iterator();

        while (iaf.hasNext()) {
            Anotacion temp = (Anotacion) iaf.next();
            if (ver.equals(temp.getOrden())) {
                local_Anotacion = temp;
                break;
            }
        }

        /*List anotaciones = f.getAnotaciones();
		
		Iterator itt=anotaciones.iterator();
		
		while(itt.hasNext()){
			Anotacion anotacion = (Anotacion)itt.next();
			if (anotacion.getOrden().equals(ver)){
				local_Anotacion = anotacion;
			}
		}*/
//		 List< String > to collect data of id's
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
        request.getSession().setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, local_ArrayAnotacionIdsToBeCanceled);
        // String[] idsAnotaciones = request.getParameterValues("ESCOGER_ANOTACION_CANCELACION");
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarEdicion(HttpServletRequest request) {
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarEdicionCancelacion(HttpServletRequest request) {
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        cambiaEstadoListaCiudadanos(request);
        request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);
        return null;
    }

    /**
     * @author Diego Alejandro Hernandez Uribe
     * @param request
     */
    //Carga la lista temporal de ciudadanos, la recorre y si hay por borrar les
    //cambia el estado de borrar a false.
    private void cambiaEstadoListaCiudadanos(final HttpServletRequest request) {
        final List ciudadanosT = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        Iterator iteradorCiudadanosT = ciudadanosT.iterator();
        while (iteradorCiudadanosT.hasNext()) {
            AnotacionCiudadano ct = (AnotacionCiudadano) iteradorCiudadanosT.next();
            if (ct.isToDelete()) {
                ct.setToDelete(false);
            }
        }
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarCancelacion(HttpServletRequest request) {
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        request.getSession().removeAttribute("listanat");
        request.getSession().removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
        return null;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento matriculasEnglobe(HttpServletRequest request) throws AccionWebException {
        String[] matriculas = request.getParameterValues("MATRICULAS_ENGLOBE");
        if (matriculas == null || matriculas.length == 0) {
            throw new MatriculasEnglobeException("Debe escoger al menos una matricula");
        }
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        List folios = new ArrayList();
        for (int i = 0; i < matriculas.length; i++) {
            String mat = matriculas[i];
            Folio fo = new Folio();
            fo.setIdMatricula(mat);
            folios.add(fo);
        }
        return new EvnFolio(usuario, folios);
    }

    /**
     * @param request
     * @return
     */
    private Evento aceptarAnotacionesFolio(HttpServletRequest request) {
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();

            while (itAnotaciones.hasNext()) {
                folio.addAnotacione((Anotacion) itAnotaciones.next());
            }
        }
        request.getSession().setAttribute(WebKeys.FOLIO, folio);
        //TODO IR AL NEGOCIO PARA GRABAR TEMPORALMENTE LOS DATOS
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
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
     * @throws AccionWebException
     */
    private Evento eliminarAnotacion(HttpServletRequest request) throws AccionWebException {
        preservarInfo(request);

        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        if (anotaciones == null) {
            anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_SEGREGACION);
            if (anotaciones == null) {
                anotaciones = new Vector();
            }
        }
        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(AWCalificacion.POSICION));
            anotaciones.remove(aplicacionNumero);
            List temp = anotaciones.subList(aplicacionNumero, anotaciones.size());
            Iterator it = temp.iterator();
            while (it.hasNext()) {
                Anotacion at = (Anotacion) it.next();
                int norden = Integer.parseInt(at.getOrden());
                norden--;
                at.setOrden(Integer.toString(norden));
            }
            request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El numero del documento a eliminar es invalido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (anotaciones.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacia");
            }
            throw new ParametroInvalidoException("El indice del documento a eliminar esta fuera del rango");
        }
        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");
        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }
        String norder = (String) request.getSession().getAttribute(CFolio.NEXT_ORDEN_ANOTACION);
        int torder = Integer.parseInt(norder);
        torder--;
        norder = Integer.toString(torder);
        request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, norder);

        return null;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento cargarDescripcionNaturaleza(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        String ac = request.getParameter(WebKeys.ACCION);

        ValidacionParametrosAnotacionCalificacionException exception = new ValidacionParametrosAnotacionCalificacionException();

        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, idNaturaleza);
        if ((idNaturaleza == null) || (idNaturaleza.trim().equals(""))) {
            exception.addError("Debe insertar un codigo de naturaleza juridica");
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        //Preservar informacion:
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
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiacion_de_Naturaleza_Juridica
         * @Descripcion: se define variable version
         */

        long version = 0;
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
                     * 056_453_Modificiacion_de_Naturaleza_Juridica
                     * @Descripcion: se define variable version
                     */
                    version = nat.getVersion();

                    if (!nat.isHabilitadoCalificacion()) {
                        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                        /**
                         * @Autor: Carlos Torres
                         * @Mantis: 0012705
                         * @Requerimiento:
                         * 056_453_Modificiacion_de_Naturaleza_Juridica
                         * @Descripcion: se define variable version
                         */
                        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
                        throw new NaturalezaInvalidaException("La naturaleza juridica (" + idNaturaleza + " - " + descripcion + ") no es valida para la calificacion.");
                    } else {
                        found = true;
                    }
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

        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento cargarDescripcionNaturalezaCalificacion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        String ac = request.getParameter(WebKeys.ACCION);

        ValidacionParametrosAnotacionCalificacionException exception = new ValidacionParametrosAnotacionCalificacionException();

        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, idNaturaleza);
        if ((idNaturaleza == null) || (idNaturaleza.trim().equals(""))) {
            exception.addError("Debe insertar un codigo de naturaleza juridica");
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        //Preservar informacion:
        for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String key = (String) enumeration.nextElement();
            request.getSession().setAttribute(key, request.getParameter(key));
        }

        List grupoNaturalezas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);

        GrupoNaturalezaJuridica grupo = new GrupoNaturalezaJuridica();
        NaturalezaJuridica nat;
        boolean found = false;
        String descripcion = "";
        long version = 0;
        for (Iterator it = grupoNaturalezas.iterator(); it.hasNext() && !found;) {
            grupo = (GrupoNaturalezaJuridica) it.next();
            for (Iterator it2 = grupo.getNaturalezaJuridicas().iterator(); it2.hasNext() && !found;) {
                nat = (NaturalezaJuridica) it2.next();
                if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {

                    if (!nat.isHabilitadoCalificacion()) {
                        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
                        throw new NaturalezaInvalidaException("La naturaleza juridica (" + idNaturaleza + " - " + descripcion + ") no es valida para la calificacion.");
                    } else {
                        found = true;
                        descripcion = nat.getNombre();
                        version = nat.getVersion();
                    }

                    /*if (nat.isHabilitadoCalificacion()) {
					descripcion = nat.getNombre();
					found = true;
				}*/
                }
            }
        }

        if ((ac.equals(CARGAR_DESCRIPCION_NATURALEZA_CANCELACION)) || (ac.equals(CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION))) {
            if (!CGrupoNaturalezaJuridica.ID_CANCELACION.equals(grupo.getIdGrupoNatJuridica())) {
                request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
                if (found) {
                    throw new NaturalezaInvalidaException("La naturaleza juridica (" + idNaturaleza + " - " + descripcion + ") no es de cancelacion.");
                }
            }
        }

        if (!found) {
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
            throw new NaturalezaInvalidaException("La naturaleza juridica (" + idNaturaleza + ") no existe.");
        } else {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, descripcion);
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, version);
        }

        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento crearAnotacion(HttpServletRequest request) {
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        return new EvnNextOrdenAnotacion(usuarioAuriga, folio);
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
     * Valida un ciudadano en anotacion, recibe el request, crea el objeto
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
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoAnotacion(request);
        preservarInfoCancelacion(request);

//        String tipoPersona = request.getParameter(CFolio.ANOTACION_TIPO_PERSONA + ver);
//
//        if ((tipoPersona == null) || tipoPersona.equals("-SELECCIONE UNA OPCION-")) {
//            exception.addError("Debe escoger un tipo de identificacion para la persona en la anotacion");
//        }
        String tipoDoc = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + ver);

        if ((tipoDoc == null) || tipoDoc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo de identificacion para la persona en la anotacion");
        }

        String numDoc = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + ver);
        if ((numDoc == null) || numDoc.equals("")) {
            exception.addError("Debe digitar un numero de identificacion");
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
        evnt.setRegistro(true);
        evnt.setTurno(turno);
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
     * @param fechaInterfaz
     * @return
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
            int ano = Integer.parseInt(partido[2]);
            calendar.set(ano, mes, dia);

            if ((calendar.get(Calendar.YEAR) == ano) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                return calendar;
            }
        }
        return null;
    }

    private Evento prepararPDF(HttpServletRequest request) {
        return null;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento eliminarCiudadano(HttpServletRequest request) throws AccionWebException {
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
            throw new ParametroInvalidoException("El numero del documento a eliminar es invalido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacia");
            }
            throw new ParametroInvalidoException("El indice del documento a eliminar esta fuera del rango");
        }

        /**
         * @author: Guillermo Cabrera.
         * @change: se remueve de la sesion los ciudadanos ya que al ingresar
         * los ciudadanos y posteriormente eliminarlos, estos quedan en sesion y
         * permite crear anotaciones sin ciudadanos asociados. Mantis: 3397 Acta
         * - Requerimiento No 187
         *
         * Fecha: 16-08-2011 Autor: Guillermo Cabrera Se valida que no existan
         * ciudadanos en el formulario para poder removerlos de la sesion ya que
         * se estaban removiendo todos los ciudadanos cuando se eliminaba solo
         * uno desde el formulario. Mantis 8411 0008411: Acta - Requerimiento No
         * 021_151 - Error en anotaciones - ciudadanos
         *
         */
        //request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        if (ciudadanos == null || ciudadanos.isEmpty()) {
            request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        } else {
            request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        }

        return null;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento eliminarCiudadanoEdicion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        preservarInfoCancelacion(request);

        List ciudadanosTemp = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanosTemp == null) {
            ciudadanosTemp = new Vector();
        }
        /**
         * Organizar los ciudadanos. Se debe tener en cuenta el orden ya que el
         * helper (VariosCiuddanosAnotacionHelper)los muestra de esta manera y
         * se necesita manejar el mismo contador para eliminar el que realmente
         * se esta indicando
         */

        Iterator itCiudadanos = ciudadanosTemp.iterator();
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
        //ciudadanos.clear();
        List ciudadanos = losDe;

        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(AWCalificacion.POSICION));
            AnotacionCiudadano c = (AnotacionCiudadano) ciudadanos.get(aplicacionNumero);
            c.setToDelete(true);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El numero del documento a eliminar es invalido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacia");
            }
            throw new ParametroInvalidoException("El indice del documento a eliminar esta fuera del rango");
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
     * @param request
     * @return
     */
    private Evento consultarUltimosPropietarios(HttpServletRequest request) {

        preservarInfoBasicaFolio(request);
        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoAnotacion(request);
        preservarInfoCancelacion(request);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        String numAnotacionTmp = (String) request.getParameter(AWCalificacion.NUM_ANOTACION_TEMPORAL);

        if (numAnotacionTmp != null) {
            request.getSession().setAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL, numAnotacionTmp);
        }

        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        // crear e inicializar el evento
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.CONSULTAR_ULTIMOS_PROPIETARIOS);
        evento.setTurno(turno);
        evento.setUID(UID);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setFolio(folio);

        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarPropietario(HttpServletRequest request) {
        request.getSession().removeAttribute(AWCalificacion.LISTA_PROPIETARIOS_FOLIO);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento guardarPropietario(HttpServletRequest request) throws AccionWebException {
        // request.getSession().removeAttribute(AWCalificacion.LISTA_PROPIETARIOS_FOLIO);

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
            if (request.getSession().getAttribute(key) != null && !(((String) request.getSession().getAttribute(key)).equals(""))) {
                continuarGuardado = true;
            }

            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null && !(((String) request.getSession().getAttribute(key)).equals(""))) {
                continuarGuardado = true;
            }

            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null && !(((String) request.getSession().getAttribute(key)).equals(""))) {
                continuarGuardado = true;
            }

            key = CFolio.ANOTACION_NOMBRES_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null && !(((String) request.getSession().getAttribute(key)).equals(""))) {
                continuarGuardado = true;
            }

            key = CFolio.ANOTACION_SEXO_PERSONA + (auxnumCiud - 1);
            if (request.getSession().getAttribute(key) != null && !(((String) request.getSession().getAttribute(key)).equals(""))) {
                continuarGuardado = true;
            }

            if (!continuarGuardado) {
                ciudadanosEliminados++;
            }
            auxnumCiud--;
        }

        String[] numPropietario = request.getParameterValues("ESCOGER_PROPIETARIO");

        if (numPropietario == null) {
            throw new ParametroInvalidoException("Debe Seleccionar un Ciudadano");
        }

        int numSelecionados = numPropietario.length;
        String[] selPropietarios = new String[numPropietario.length];

        for (int i = 0; i < numPropietario.length; i++) {
            selPropietarios[i] = numPropietario[i];
        }

        List listaUltimosPropietarios = (List) request.getSession().getAttribute(AWCalificacion.LISTA_PROPIETARIOS_FOLIO);

        int contadorSelecion = 0;
        for (int i = (numCiud - ciudadanosEliminados); i < ((numCiud - ciudadanosEliminados) + numSelecionados); i++) {
            AnotacionCiudadano anotacionciu = (AnotacionCiudadano) listaUltimosPropietarios.get(Integer.valueOf(selPropietarios[contadorSelecion]).intValue());
            Ciudadano ciu = anotacionciu.getCiudadano();
            contadorSelecion++;

            key = CFolio.ANOTACION_TIPO_PERSONA + i;
            parametro = (String) ciu.getTipoPersona();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

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

            key = CFolio.ANOTACION_SEXO_PERSONA + i;
            parametro = (String) ciu.getSexo();
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

        }
        request.getSession().removeAttribute(AWCalificacion.LISTA_PROPIETARIOS_FOLIO);
        numCiud = (numCiud - ciudadanosEliminados) + numSelecionados;
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        return null;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento agregarCiudadano(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        preservarInfoBasicaAnotacion(request);
        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        ValidacionParametrosException exception = null;
        if (folio == null) {
            exception = new ValidacionParametrosCiudadanoEnglobeException();
        } else {
            exception = new ValidacionParametrosCiudadanoCalificacionException();
        }

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        String tipoPersona = request.getParameter(CFolio.ANOTACION_TIPO_PERSONA);

        if ((tipoPersona == null) || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escojer un tipo de persona para el ciudadano en la anotacion");
        }

        String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA);

        if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escojer un tipo de identificacion para la persona en la anotacion");
        }

        String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA);

        if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escojer un sexo para la persona en la anotacion");
        }

        String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA);

        /*if (tipoId!=null && !tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
			if ((numId == null) || numId.trim().equals("")) {
				exception.addError("Documento Invalido");
			}else{
				double valorId = 0.0d;
				if (numId == null) {
					exception.addError("El numero de identificacion de la persona en la anotacion es invalido");
				} else {
					try {
						valorId = Double.parseDouble(numId);
						if (valorId <= 0) {
							exception.addError("El valor del documento no puede ser negativo o cero");
						}
					} catch (NumberFormatException e) {
						exception.addError("El numero de identificacion de la persona en la anotacion es invalido");
					}
				}
			}

		}		*/
        String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA);
        String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA);
        String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA);
        if (tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {

            if (apellido1 == null || apellido1.trim().equals("")) {
                exception.addError("Primer apellido invalido");
            }
        } else {
            if ((numId == null) || numId.trim().equals("")) {
                exception.addError("Documento Invalido");
            }
            double valorId = 0.0d;
            if (numId == null) {
                exception.addError("El numero de identificacion de la persona en la anotacion es invalido");
            } else {
                try {
                    valorId = Double.parseDouble(numId);
                    if (valorId <= 0) {
                        exception.addError("El valor del documento no puede ser negativo o cero");
                    }
                } catch (NumberFormatException e) {
                    exception.addError("El numero de identificacion de la persona en la anotacion es invalido");
                }
            }
            if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                if (nombres == null || nombres.trim().equals("")) {
                    exception.addError("Razon social invalida");
                }
            } else {
                if ((nombres == null) || nombres.equals("")) {
                    exception.addError("Debe ingresar el nombre de la persona en la anotacion");
                }

                if ((apellido1 == null) || apellido1.equals("")) {
                    exception.addError("Debe ingresar el primer apellido de la persona en la anotacion");
                }
            }
        }

        String tipoIntervecion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION);

        if ((tipoIntervecion == null) || tipoIntervecion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe ingresar un tipo de intervencion para la persona en la anotacion");
        }

        AnotacionCiudadano c;
        Iterator i = ciudadanos.iterator();
        while (i.hasNext()) {
            c = (AnotacionCiudadano) i.next();
            if ((tipoIntervecion.equals(c.getRolPersona())) && (numId.equals(c.getCiudadano().getDocumento()))
                    && (tipoId.equals(c.getCiudadano().getTipoDoc()) && (!tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)))) {
                exception.addError("La persona no puede tener dos veces el mismo rol");
            }
        }

        String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION);

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        eliminarInfoBasicaCiudadano(request);
        AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
        Ciudadano ciudadano = new Ciudadano();

        if (apellido1 != null) {
            ciudadano.setApellido1(apellido1);
        }

        if (apellido2 != null) {
            ciudadano.setApellido2(apellido2);
        }
        ciudadano.setSexo(sexo);
        ciudadano.setNombre(nombres);
        if (numId != null) {
            ciudadano.setDocumento(numId);
        }

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        ciudadano.setTipoDoc(tipoId);
        ciudadano.setTipoPersona(tipoPersona);
        anotacionCiudadano.setCiudadano(ciudadano);
        anotacionCiudadano.setRolPersona(tipoIntervecion);
        anotacionCiudadano.setParticipacion(participacion);

        if (request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION) == null) {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
        } else {
            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
        }

        //if (!existeEnListaCiudadanos(anotacionCiudadano, ciudadanos)) {
        ciudadanos.add(anotacionCiudadano);
        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        /*} else {
			exception.addError("El ciudadano que esta intentado ingresar ya se encuentra registrado en la anotacion");
			throw exception;
		}*/
        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");
        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }

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
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new ArrayList();
        }

        List ciudadanosFinales = new Vector(ciudadanos);

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
                    exception.addError("Debe escojer un tipo de identificacion para la persona " + b + " en la anotacion");
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
                        exception.addError("Documento Invalido para la persona " + b);
                        datosBien = false;
                    }
                    double valorId = 0.0d;
                    if (numId == null) {
                        exception.addError("El numero de identificacion de la persona " + b + " en la anotacion es invalido");
                        datosBien = false;
                    } else {
                        /*
             * @author : CTORRES
             * @change : Se implemento la validacion para solo permitir caracteres alfanumericos en 
             *           numId cuando el tipo de identificacion es PASAPORTE.
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
                                exception.addError("El numero de identificacion de la persona es invalido. Debe ser alfanumerico");
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
                                    exception.addError("El numero de identificacion de la persona " + b + " en la anotacion es invalido");
                                    datosBien = false;
                                }
                            }
                        }
                    }
                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razon social invalida");
                        }
                    } else {
                        if ((nombres == null) || nombres.equals("")) {
                            exception.addError("Debe ingresar el nombre de la persona " + b + " en la anotacion");
                            datosBien = false;
                        }

                        if ((apellido1 == null) || apellido1.equals("")) {
                            exception.addError("Debe ingresar el primer apellido de la persona " + b + " en la anotacion");
                            datosBien = false;
                        }
                    }
                }

                String tipoIntervencion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);

                if ((tipoIntervencion == null) || tipoIntervencion.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe ingresar un tipo de intervencion para la persona " + b + " en la anotacion");
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
                        exception.addError("El campo participacion de una persona no puede tener mas de 50 caracteres");
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
                    ciudadano.setDocumento(numId);

                    //Se setea el circulo del ciudadano
                    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                    ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

                    ciudadano.setTipoPersona(tipoPersona);
                    ciudadano.setTipoDoc(tipoId);
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
                }

            }
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        eliminarInfoBasicaVariosCiudadanos(request);

        //if (!existeEnListaCiudadanos(anotacionCiudadano, ciudadanos)) {
        List listaCompletaCiudadanos = new Vector();

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
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosFinales);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        evento.setTurno(turno);
        return evento;

        //return null;
    }

    /**
     * @param request
     * @param accion1
     * @return
     * @throws AccionWebException
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request, String accion1) throws AccionWebException {

        ValidacionParametrosException exception;

        if (accion1.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION)) {
            exception = new ValidacionParametrosCiudadanoSegregacionCalificacionException();
        } else if (accion1.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS)) {
            exception = new ValidacionParametrosCiudadanoCalificacionException();
        } else {
            exception = new ValidacionParametrosException();
        }

        return agregarVariosCiudadanos(request, exception);

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
                    exception.addError("Debe escojer un tipo de persona para la persona " + b + " en la anotacion");
                    datosBien = false;
                }

                String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + i);

                if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de identificacion para la persona " + b + " en la anotacion");
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
                        exception.addError("Documento Invalido para la persona " + b);
                        datosBien = false;
                    }
                    double valorId = 0.0d;
                    if (numId == null) {
                        exception.addError("El numero de identificacion de la persona " + b + " en la anotacion es invalido");
                        datosBien = false;
                    } else {
                        /*
                                            * @author : CTORRES
                                            * @change : Se implemento la validacion para solo permitir caracteres alfanumericos en 
                                            *           numId cuando el tipo de identificacion es PASAPORTE.
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
                                exception.addError("El numero de identificacion de la persona es invalido. Debe ser alfanumerico");
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
                                    exception.addError("El numero de identificacion de la persona " + b + " en la anotacion es invalido");
                                    datosBien = false;
                                }
                            }
                        }
                    }
                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razon social invalida del campo " + b);
                            datosBien = false;
                        }
                    } else {
                        if ((nombres == null) || nombres.equals("")) {
                            exception.addError("Debe ingresar el nombre de la persona " + b + " en la anotacion");
                            datosBien = false;
                        }

                        if ((apellido1 == null) || apellido1.equals("")) {
                            exception.addError("Debe ingresar el primer apellido de la persona " + b + " en la anotacion");
                            datosBien = false;
                        }
                    }
                }

                String tipoIntervecion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);

                if ((tipoIntervecion == null) || tipoIntervecion.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe ingresar un tipo de intervencion para la persona " + b + " en la anotacion");
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

                        if (c.isToDelete()) {
                            if ((marcaPropietario.equals(c.getStringMarcaPropietario()))
                                    && (participacion.equals(c.getParticipacion()))) {
                                c.getCiudadano().setApellido1(apellido1);
                                c.getCiudadano().setApellido2(apellido2);
                                c.getCiudadano().setNombre(nombres);
                                c.getCiudadano().setSexo(sexo);
                                c.setToDelete(false);
                                repetido = true;
                            }
                        } else {
                            exception.addError("La persona no puede tener dos veces el mismo rol");
                        }
                    }
                }

                if ((participacion != null) && !participacion.equals("")) {
                    if (participacion.length() > 50) {
                        exception.addError("El campo participacion de una persona no puede tener mas de 50 caracteres");
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

                    ciudadano.setTipoPersona(tipoPersona);
                    ciudadano.setTipoDoc(tipoId);
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

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosAgregados);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        return evento;
    }

    /**
     * Valida que los ciudadanos de varias anotyaciones no sean repetidos (Rol,
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
                        && !(ciudadanoTemp.getCiudadano().getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_SECUENCIA))) {
                    exception.addError("El ciudadano no puede tener dos veces el mismo rol");
                    hayExcepcion = true;
                    break;
                }
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

    /**
     * @param request
     */
    private void eliminarInfoBasicaCiudadano(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_COD_VERIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA);
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION);
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

    /**
     * @param request
     * @return
     */
    private Evento preservarInfo(HttpServletRequest request) {
        preservarInfoBasicaFolio(request);
        return null;
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
        if (request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION, request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION));
        }
    }

    /**
     * @param request
     */
    private void preservarInfoBasicaVariosCiudadanos(HttpServletRequest request) {

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        HttpSession session = request.getSession();
        String key = null;
        String tipoID = null;
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
            tipoID = (String) parametro;
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_PERSONA + i;
            parametro = request.getParameter(key);
            if (parametro != null) {
                session.setAttribute(key, parametro);
            }

//            key = CFolio.ANOTACION_TIPO_PERSONA + i;
//            parametro = request.getParameter(key);
//            String auxParametro = null;
//            auxParametro = (String) parametro;
//            if(auxParametro != null && tipoID.equals(CCiudadano.TIPO_DOC_ID_NIT) ){
//                parametro = CCiudadano.TIPO_PERSONA_JURIDICA;
//                session.setAttribute(key, parametro);
//                tipoID = null;
//            } else{
//                if (parametro != null) {
//                session.setAttribute(key, parametro);
//                }
//            }
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
     */
    private void preservarInfoBasicaAnotacion(HttpServletRequest request) {
        if (request.getParameter(AWCalificacion.POSICIONANOTACION) != null) {
            request.getSession().setAttribute(AWCalificacion.POSICIONANOTACION, request.getParameter(AWCalificacion.POSICIONANOTACION));
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
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiacion_de_Naturaleza_Juridica
         * @Descripcion: Se agrega validacion para conservar informacion de
         * version
         */
        if (request.getParameter(CFolio.ANOTACION_MODALIDAD) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_MODALIDAD, request.getParameter(CFolio.ANOTACION_MODALIDAD));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER));
        }
        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }

        if (request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION) != null) {
            request.getSession().setAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION, request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION));
        }
    }

    /**
     * @param request
     */
    private void preservarInfoBasicaAnotacionRegistro(HttpServletRequest request) {

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiacion_de_Naturaleza_Juridica
         * @Descripcion: Se agrega validacion para conservar informacion de
         * version
         */
        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER));
        }
        if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
        }

        if (request.getParameter("natjuridica_id") != null) {
            request.getSession().setAttribute("natjuridica_id", request.getParameter("natjuridica_nom"));
        }

        if (request.getParameter("natjuridica_nom") != null) {
            request.getSession().setAttribute("natjuridica_nom", request.getParameter("natjuridica_nom"));
        }
        if (request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION) != null) {
            request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION));
        }

    }

    /**
     * @param request
     */
    private void preservarInfoBasicaFolio(HttpServletRequest request) {
        if (request.getParameter(CFolio.ID_MATRICULA) != null) {
            request.getSession().setAttribute(CFolio.ID_MATRICULA, request.getParameter(CFolio.ID_MATRICULA));
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

        if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO, request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
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

        if (request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO) != null) {
            request.getSession().setAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO, request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO));
        }
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaAnotacion(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Codigo_Notaria_NC
         */
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        request.getSession().removeAttribute(CFolio.ANOTACION_ID_ANOTACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_MODALIDAD);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        /**
         * @Autor: Carlos Torres
         * @Mantis: 0012705
         * @Requerimiento: 056_453_Modificiacion_de_Naturaleza_Juridica
         * @Descripcion: Se agrega validacion para conservar informacion de
         * version
         */
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
        request.getSession().removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
        request.getSession().removeAttribute("tipo_oficina");
        request.getSession().removeAttribute("tipo_nom_oficina");
        request.getSession().removeAttribute("numero_oficina");
        request.getSession().removeAttribute("id_oficina");
        /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Codigo_Notaria_NC
         */
        request.getSession().removeAttribute("version");
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaFolio(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.ID_MATRICULA);
        request.getSession().removeAttribute(CFolio.FOLIO_COD_CATASTRAL);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA);
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
        request.getSession().removeAttribute(CFolio.FOLIO_LINDERO);
        request.getSession().removeAttribute(CFolio.FOLIO_COD_CATASTRAL);
        request.getSession().removeAttribute(CFolio.FOLIO_EJE1);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR1);
        request.getSession().removeAttribute(CFolio.FOLIO_EJE2);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR2);
        request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
        request.getSession().removeAttribute(CFolio.FOLIO_COD_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.FOLIO_FECHA_APERTURA);
        request.getSession().removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        request.getSession().removeAttribute(CFolio.FOLIO_ESTADO_FOLIO);
        request.getSession().removeAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
    }

    /**
     *
     * @param ciudadano
     * @param ciudadanos
     * @return
     */
    private boolean existeEnListaCiudadanos(AnotacionCiudadano ciudadano, List ciudadanos) {
        Iterator itCiudadanos = ciudadanos.iterator();

        while (itCiudadanos.hasNext()) {
            AnotacionCiudadano temp = (AnotacionCiudadano) itCiudadanos.next();

            if (temp.getCiudadano().getDocumento().equals(ciudadano.getCiudadano().getDocumento()) && temp.getCiudadano().getTipoDoc().equals(ciudadano.getCiudadano().getTipoDoc())) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param anotacion
     * @param anotaciones
     * @return
     */
    private boolean existeEnListaAnotaciones(Anotacion anotacion, List anotaciones) {
        Iterator itAnotaciones = anotaciones.iterator();

        while (itAnotaciones.hasNext()) {
            Anotacion temp = (Anotacion) itAnotaciones.next();

            if (temp.getIdAnotacion().equals(anotacion.getIdAnotacion())
                    && temp.getIdMatricula().equals(anotacion.getIdMatricula())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Este metodo se encarga de limpiar los datos de los paginadores y
     * resultados del <code>HttpServletRequest</code> para realizar de nuevo las
     * consultas.
     *
     * @param request
     * @return evento <code>EvnConsulta</code> con la informacion de la
     * SolicitudConsulta a crear.
     */
    private Evento regresarCalificacion(HttpServletRequest request) {

        //Este codigo dependera de la cantidad de paginadores q hayan, este es el caso baso 1 solo mostrarFolioHelper.
        String nombrePaginador = (String) request.getSession().getAttribute(WebKeys.NOMBRE_PAGINADOR);
        String nombreResultado = (String) request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);

        if (nombrePaginador != null) {
            request.getSession().removeAttribute(nombrePaginador);
        }
        if (nombreResultado != null) {
            request.getSession().removeAttribute(nombreResultado);
        }

        eliminarInfoBasicaVariosCiudadanos(request);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento aprobarMayorValor(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosException exception = new ValidacionParametrosException();
        //Obtener los datos para la liquidacion
        String valorCertificados = request.getParameter(AWCalificacion.PAGO_MAYOR_VALOR_CERTIFICADOS);
        String valorDerechos = request.getParameter(AWCalificacion.PAGO_MAYOR_VALOR_DERECHOS);
        String valorImpuestos = request.getParameter(AWCalificacion.PAGO_MAYOR_VALOR_IMPUESTOS);

        //String valorPagar= request.getParameter(AWCalificacion.PAGO_MAYOR_VALOR);
        String razonPagar = request.getParameter(AWCalificacion.RAZON_MAYOR_VALOR);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        SolicitudRegistro solR = (SolicitudRegistro) turno.getSolicitud();
        double dValorDerechos = 0, dValorImpuestos = 0, nvalor = 0;
        double dValorCertificados = 0;
        //double nvalor=0;

        request.getSession().setAttribute(AWCalificacion.PAGO_MAYOR_VALOR_CERTIFICADOS, valorCertificados);
        request.getSession().setAttribute(AWCalificacion.PAGO_MAYOR_VALOR_DERECHOS, valorDerechos);
        request.getSession().setAttribute(AWCalificacion.PAGO_MAYOR_VALOR_IMPUESTOS, valorImpuestos);
        request.getSession().setAttribute(AWCalificacion.RAZON_MAYOR_VALOR, razonPagar);

        if (valorDerechos == null || valorDerechos.equals("")) {
            dValorDerechos = 0;
        } else {
            valorDerechos = valorDerechos.replaceAll(",", "");
            try {
                if (!valorDerechos.equals("")) {
                    dValorDerechos = Double.parseDouble(valorDerechos);
                } else {
                    dValorDerechos = 0;
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor por derechos es invalido");
            }
        }

        Acto actoMayorValor = new Acto();
        TipoActo tipoActoMayorValor = new TipoActo();
        tipoActoMayorValor.setIdTipoActo(CRespuesta.MAYOR_VALOR_ID);

        actoMayorValor.setTipoActo(tipoActoMayorValor);

        JDOLiquidacionesDAO jdoLiquidacionesDAO = new JDOLiquidacionesDAO();

        double dporcentajeConservacionDoc = 0;
        try {
            dporcentajeConservacionDoc = jdoLiquidacionesDAO.TraerConservacionActo(actoMayorValor);
        } catch (DAOException ex) {
            Logger.getLogger(AWCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        }

        double dValorConservacionDoc = jdoLiquidacionesDAO.roundValue(dValorDerechos * dporcentajeConservacionDoc);

        if (valorImpuestos == null || valorImpuestos.equals("")) {
            dValorImpuestos = 0;
        } else {
            valorImpuestos = valorImpuestos.replaceAll(",", "");
            try {
                if (!valorImpuestos.equals("")) {
                    dValorImpuestos = Double.parseDouble(valorImpuestos);
                } else {
                    dValorImpuestos = 0;
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor por impuestos es invalido");
            }
        }

        if (valorCertificados == null || valorCertificados.equals("")) {
            dValorCertificados = 0;
        } else {
            valorCertificados = valorCertificados.replaceAll(",", "");
            try {
                if (!valorCertificados.equals("")) {
                    dValorCertificados = Double.parseDouble(valorCertificados);
                } else {
                    dValorCertificados = 0;
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor por certificados es invalido");
            }
        }

        nvalor = dValorImpuestos + dValorDerechos + dValorCertificados + dValorConservacionDoc;

        if (razonPagar == null || razonPagar.equals("")) {
            exception.addError("La razon del pago esta vacia");
        }

        if (nvalor == 0.0) {
            exception.addError("Al menos un valor debe ser diferente a cero");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        //crear liquidacion a partir de los datos
        LiquidacionTurnoRegistro liq = new LiquidacionTurnoRegistro();
        liq.setFecha(new Date());
        liq.setUsuario(usuarioNeg);
        liq.setValorDerechos(dValorDerechos);
        liq.setValorImpuestos(dValorImpuestos);
        liq.setValor(nvalor);
        liq.setJustificacionMayorValor(razonPagar);
        //Conservacion Documental
        liq.setValorConservacionDoc(dValorConservacionDoc);

        //crear e inicializar el evento
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.APROBAR_MAYOR_VALOR);
        evento.setTurno(turno);
        evento.setLiquidacion(liq);
        evento.setFase(fase);
        if (usuarioNeg == null) {

        }
        evento.setUsuarioNeg(usuarioNeg);

        return evento;

    }

    /**
     * @param request
     * @return
     */
    private Evento registrarPagoExceso(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosException exception = new ValidacionParametrosException();
        //Obtener los datos para la liquidacion
        String valorDerechos = request.getParameter(AWCalificacion.PAGO_EXCESO_DERECHOS);
        String valorImpuestos = request.getParameter(AWCalificacion.PAGO_EXCESO_IMPUESTOS);
        //String valorDevolucion= request.getParameter(AWCalificacion.PAGO_EXCESO);
        String razonDevolucion = request.getParameter(AWCalificacion.RAZON_DEVOLUCION);

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        SolicitudRegistro solR = (SolicitudRegistro) turno.getSolicitud();
        double dValorDerechos = 0, dValorImpuestos = 0, nvalor = 0;
        //double nvalor=0;

        if (valorDerechos == null || valorDerechos.equals("")) {
            exception.addError("El valor por derechos es invalido.");
        } else {
            try {
                valorDerechos = valorDerechos.replaceAll(",", "");
                dValorDerechos = Double.parseDouble(valorDerechos);
                if (dValorDerechos <= 0) {
                    exception.addError("El valor por derechos debe ser mayor a cero");
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor por derechos es invalido");
            }
        }

        if (valorImpuestos == null || valorImpuestos.equals("")) {
            exception.addError("El valor de impuestos es invalido.");
        } else {
            try {
                valorImpuestos = valorImpuestos.replaceAll(",", "");
                dValorImpuestos = Double.parseDouble(valorImpuestos);
                if (dValorImpuestos <= 0) {
                    exception.addError("El valor de impuestos debe ser mayor a cero");
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor de impuestos es invalido");
            }
        }

        if (razonDevolucion == null || razonDevolucion.equals("")) {
            exception.addError("La razon de la devolucion esta vacia");
        }

        //validar los datos obtenidos por la vista
        /*if(valorDevolucion==null || valorDevolucion.equals("")){			
			exception.addError("El valor a devolver es invalido.");
		}else{
			try{
				valorDevolucion=valorDevolucion.replaceAll(",","");								
				nvalor= Double.parseDouble(valorDevolucion);			
				if(nvalor <= 0){
					exception.addError("El valor a devolver debe ser mayor a cero");		
				}
			}catch(NumberFormatException e){
				exception.addError("El valor a devolver es invalido");
			}
			if(razonDevolucion==null || razonDevolucion.equals("")){
				exception.addError("La razon de la devolucion esta vacia");
			}			
		}*/
        //request.getSession().setAttribute(AWCalificacion.PAGO_EXCESO,valorDevolucion);
        request.getSession().setAttribute(AWCalificacion.RAZON_DEVOLUCION, razonDevolucion);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        if (dValorImpuestos > 0) {
            dValorImpuestos *= -1;
        }
        if (dValorDerechos > 0) {
            dValorDerechos *= -1;
        }

        nvalor = dValorDerechos + dValorImpuestos;

        //crear liquidacion a partir de los datos
        LiquidacionTurnoRegistro liq = new LiquidacionTurnoRegistro();
        liq.setFecha(new Date());
        liq.setUsuario(usuarioNeg);
        liq.setValor(nvalor);
        liq.setValorDerechos(dValorDerechos);
        liq.setValorImpuestos(dValorImpuestos);
        liq.setJustificacionMayorValor(razonDevolucion);

        //crear e inicializar el evento
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.REGISTRAR_PAGO_EXCESO);
        evento.setTurno(turno);
        evento.setLiquidacion(liq);
        evento.setFase(fase);
        evento.setUsuarioNeg(usuarioNeg);

        return evento;

    }

    /**
     * @param request
     * @return
     */
    private Evento imprimirFormularioCalificacion(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosException exception = new ValidacionParametrosException();
        //Obtener los datos para la liquidacion
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        //crear e inicializar el evento
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.IMPRIMIR_FORMULARIO_CALIFICACION);
        evento.setTurno(turno);
        evento.setUID(UID);
        evento.setUsuarioNeg(usuarioNeg);

        return evento;

    }

    /**
     * @param request
     * @return
     */
    private Evento imprimirFolioTemporal(HttpServletRequest request) throws AccionWebException {

        //Obtener los datos para la liquidacion
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        String matricula = request.getParameter(CFolio.ID_MATRICULA);
        String zonaRegistral = request.getParameter(CFolio.ID_ZONA_REGISTRAL);
        Folio folio = new Folio();
        folio.setIdMatricula(matricula);

        //crear e inicializar el evento
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.IMPRIMIR_FOLIO_TEMPORAL);
        evento.setFolio(folio);
        evento.setTurno(turno);
        evento.setUID(UID);
        evento.setUsuarioNeg(usuarioNeg);

        return evento;
    }

    /**
     * @see
     * org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest,
     * org.auriga.smart.eventos.EventoRespuesta)
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

        if (this.accion.equals(MOSTRAR)) {
            if (evento instanceof EvnRespCalificacion) {
                EvnRespCalificacion respuesta = (EvnRespCalificacion) evento;
                boolean okValidarCalificacion = respuesta.isValidoAprobarCalificacion();
                Boolean ok = new Boolean(okValidarCalificacion);
                HttpSession session = request.getSession();
                session.setAttribute(WebKeys.APROBAR_CALIFICACION, ok);
                session.setAttribute(WebKeys.VALIDACION_APROBAR_CALIFICACION, respuesta.getValidacionAnotacionesTemporales());
                session.setAttribute(WebKeys.LISTA_FOLIOS_SIN_ACTUALIZACION_NOMENCLATURA, respuesta.getFoliosSinActualizacionNomenclatura());

                session.removeAttribute(WebKeys.WEB_ENGLOBE);
                session.removeAttribute(WebKeys.WEB_SEGREGACION);
                
               Hashtable anotacionesTMP = respuesta.getValidacionAnotacionesTemporales();
               Enumeration en = anotacionesTMP.elements();
               boolean haveTemporal = false;
               while(en.hasMoreElements()){
                   Object obj = new Object();
                   obj = en.nextElement();
                   if(obj instanceof Boolean){
                       Boolean resp = (Boolean) obj;
                       if(resp){
                           haveTemporal = true;
                       }
                   }
               }
               
               session.setAttribute(WebKeys.TIENE_ANOTACIONES_TMP,haveTemporal);
                

                WebSegEng segEng = respuesta.getWebSegEng();
                if (segEng != null) {

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
                        Folio folio = respuesta.getFolio();
                        if (folio != null) {
                            WebSegregacion webSegregacion = (WebSegregacion) segEng;
                            if (webSegregacion.getFoliosHeredados() != null && webSegregacion.getFoliosHeredados().size() > 0) {
                                WebFolioHeredado webFolioHeredado = (WebFolioHeredado) webSegregacion.getFoliosHeredados().get(0);
                                if (folio.getIdMatricula().equals(webFolioHeredado.getIdMatricula())) {
                                    session.setAttribute(WebKeys.WEB_SEGREGACION, segEng);
                                    session.setAttribute(CFolio.NEXT_ORDEN_ANOTACION, respuesta.getNextOrden());
                                }
                            }
                            session.setAttribute(WebKeys.FOLIO, folio);
                        }
                    }

                }

                return;
            }

        }
        if (evento instanceof EvnRespFolio) {
            EvnRespFolio respuesta = (EvnRespFolio) evento;
            if (respuesta != null) {
                if (respuesta.getTipoEvento().equals(EvnRespFolio.CREAR)) {
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                }
                if (respuesta.getTipoEvento().equals(EvnRespFolio.CONSULTAR)) {
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                }
                if (respuesta.getTipoEvento().equals(EvnRespFolio.CONSULTAR_LISTA)) {
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_ENGLOBE, respuesta.getFolios());
                }
                if (respuesta.getTipoEvento().equals(EvnRespFolio.AGREGAR_DIRECCION)) {
                    if (respuesta.getFolio() != null) {
                        request.getSession().removeAttribute(LISTA_DIRECCIONES_TEMPORALES);
                        List direcciones = new ArrayList();
                        List direccions = respuesta.getFolio().getDirecciones();
                        List dirTemporales = respuesta.getDirTemporales();
                        int tam = respuesta.getFolio().getDirecciones().size();
                        for (int i = 0; i < tam; i++) {
                            if ((tam - (i + 1)) < dirTemporales.size()) {
                                direcciones.add(direccions.get(i));
                            }
                        }
                        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
                        request.getSession().setAttribute(LISTA_DIRECCIONES_TEMPORALES, dirTemporales);
                    }
                }
                if (respuesta.getTipoEvento().equals(EvnRespFolio.ELIMINAR_DIRECCION)) {
                    request.getSession().removeAttribute(LISTA_DIRECCIONES_TEMPORALES);
                    Integer numAplica = (Integer) request.getSession().getAttribute(WebKeys.POSICION);
                    List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
                    direcciones.remove(numAplica.intValue());
                    request.getSession().removeAttribute(WebKeys.POSICION);
                    request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
                    request.getSession().setAttribute(LISTA_DIRECCIONES_TEMPORALES, respuesta.getDirTemporales());
                }
            } else {
                if (request.getParameter(WebKeys.ACCION).equals(ENVIAR_CORRECCION_ENCABEZADO)) {
                    request.getSession().removeAttribute(CTurno.TURNO_ANTERIOR);
                    request.getSession().removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);
                    request.getSession().removeAttribute("LIBRO");
                    request.getSession().removeAttribute("PAGINA");
                    request.getSession().removeAttribute("NUMERO_REGISTRO");
                    request.getSession().removeAttribute("TOMO");
                    request.getSession().removeAttribute(CSolicitudRegistro.COMENTARIO);
                    request.getSession().removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
                    request.getSession().removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
                    request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR);
                    request.getSession().removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
                    request.getSession().removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
                    request.getSession().removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
                    request.getSession().removeAttribute(CCiudadano.TIPOPERSONA);
                    request.getSession().removeAttribute(CCiudadano.TIPODOC);
                    request.getSession().removeAttribute(CCiudadano.DOCUMENTO);
                    request.getSession().removeAttribute(CCiudadano.APELLIDO1);
                    request.getSession().removeAttribute(CCiudadano.APELLIDO2);
                    request.getSession().removeAttribute(CCiudadano.NOMBRE);
                    request.getSession().removeAttribute(CCiudadano.SEXO);
                    request.getSession().removeAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
                    Integer i = (Integer) request.getSession().getAttribute(CFolio.NUM_MATRICULAS);
                    int j = i.intValue();

                    for (int k = 0; k < j; k++) {
                        request.getSession().removeAttribute(CFolio.ID_MATRICULA + k);
                    }
                    request.getSession().removeAttribute(CFolio.NUM_MATRICULAS);
                }
            }
        } else if (evento instanceof EvnRespCalificacionReproduccionSellos) {
            EvnRespCalificacionReproduccionSellos event = (EvnRespCalificacionReproduccionSellos) evento;

            // -------------------- busqueda de turno
            if (null == event) {

            } else if (EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_OK.equals(event.getTipoEvento())) {
                // load list to session
                HttpSession session = request.getSession();

                // demo;
                java.util.List searchResults;
                searchResults = event.getSearchResults();

                final String PARAM_QUERYRESULTS = gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAMID_SEARCHRESULTS;
                session.setAttribute(PARAM_QUERYRESULTS, searchResults);

                /*
                        // colocar el resultado en session // doend
                        //

                        // para probar:

                        // result list; List< Turno >;
                        java.util.List results = null;

                        // test the list

                        tst: {
                          results = new java.util.ArrayList();

                          gov.sir.core.negocio.modelo.Turno element = new gov.sir.core.negocio.modelo.Turno();
                          element.setIdWorkflow( "2005-XXS-Y-ZZZZ" );
                          results.add( element );
                          session.setAttribute( PARAM_QUERYRESULTS, results );

                        }

                 */
            } else if (EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_ERROR.equals(event.getTipoEvento())) {
                // null
            } else if (EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENTRESP_OK.equals(event.getTipoEvento())) {
                // null
            } else if (EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_RESET__EVENTRESP_OK.equals(event.getTipoEvento())) {
                // null
            }

        } else if (evento instanceof EvnRespCalificacion) {

            HttpSession session = request.getSession();

            EvnRespCalificacion respuesta = (EvnRespCalificacion) evento;
            if (respuesta != null && respuesta.getTipoEvento() != null) {
                if (respuesta.getTipoEvento().equals(EvnRespCalificacion.SEGREGACION_MASIVA)) {
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS, respuesta.getFoliosDerivados());
                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.AVANZAR_CORRECCIONES)) {
                    request.getSession().setAttribute(WebKeys.TURNO_HIJO, respuesta.getPayload());
                    request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());
                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.FOLIO_TEMPORAL)) {
                    eliminarInfoBasicaAnotacion(request);
                    eliminarInfoBasicaVariosCiudadanos(request);
                    request.getSession().removeAttribute("listanat");
                    request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);
                    request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                    request.getSession().removeAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES);
                    if (respuesta.getAnotacionesAgregadas() != null) {
                        List anotaciones = respuesta.getAnotacionesAgregadas();
                        if (anotaciones == null) {
                            anotaciones = new Vector();
                        }
                        for (Iterator iter = respuesta.getAnotaciones().iterator(); iter.hasNext();) {
                            Anotacion anota = (Anotacion) iter.next();
                            anotaciones.add(anota);
                        }
                        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);
                    }
                    if ((respuesta.getNextOrden() != null) && (!respuesta.getNextOrden().equals(""))) {
                        request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, respuesta.getNextOrden());
                    }

                    if (respuesta.isEliminarDireccionesTMP()) {
                        request.getSession().removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
                    }
                    if (respuesta.getHistorialAreas() != null) {
                        request.getSession().setAttribute(WebKeys.HISTORIAL_AREAS, respuesta.getHistorialAreas());
                    }
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                    request.getSession().setAttribute(CFolio.LINDEROS_DEFINIDOS, respuesta.getFolio().getLinderosDef());
                    request.getSession().setAttribute(WebKeys.MAYOR_EXTENSION_FOLIO, new Boolean(respuesta.getMayorExtensionFolio()));
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE, respuesta.getFoliosPadre());
                    request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO, respuesta.getFoliosHijo());
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN, respuesta.getGravamenes());
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES, respuesta.getMedidasCautelares());
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION, respuesta.getFalsaTradicion());
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS, respuesta.getAnotacionesInvalidas());
                    request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES, respuesta.getSalvedadesAnotaciones());
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION, respuesta.getCancelaciones());
                    request.getSession().setAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES, respuesta.getDirTemporales());
                    request.getSession().setAttribute(WebKeys.TOTAL_ANOTACIONES, new BigDecimal(respuesta.getNumeroAnotaciones()));

                    session.setAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR, respuesta.getAnotacionesPatrimonioFamiliar());
                    session.setAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA, respuesta.getAnotacionesAfectacionVivienda());

                    //request.getSession().setAttribute(AWCalificacion.HABILITAR_EDICION_NOMENCLATURA, new Boolean(respuesta.isHabilitarEdicionNomenclatura()));
                    request.getSession().setAttribute(AWCalificacion.HABILITAR_EDICION_COD_CATASTRAL, new Boolean(respuesta.isHabilitarEdicionCodCatastral()));
                    request.getSession().setAttribute(AWCalificacion.HABILITAR_EDICION_DIRECCION, new Boolean(respuesta.isHabilitarEdicionDireccion()));
                    request.getSession().setAttribute(AWCalificacion.HABILITAR_EDICION_LINDEROS, new Boolean(respuesta.isHabilitarEdicionLinderos()));

                    if (respuesta.getFoliosDerivadoHijo() != null) {
                        request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO, respuesta.getFoliosDerivadoHijo());
                    }

                    if (respuesta.getFoliosDerivadoPadre() != null) {
                        request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_PADRE, respuesta.getFoliosDerivadoPadre());
                    }

                    if (respuesta.getListATemp() != null) {
                        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_TEMPORALES_FOLIO, respuesta.getListATemp());
                        //pedir al msotrarFolio que se recargue
                        request.getSession().setAttribute(WebKeys.RECARGA, new Boolean(true));
                    }

                    if (respuesta.getTurno() != null) {
                        request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());
                    }

                    if (respuesta.getLinderoTemporal() != null) {
                        request.getSession().setAttribute(CFolio.LINDERO_ADICION, respuesta.getLinderoTemporal());
                    }

                    if (request.getParameter(WebKeys.ACCION).equals(GRABACION_TEMPORAL_ANOTACION)) {
                        //Como ya se grabo la anotacion, se borran los datos relacionados
                        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
                        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
                        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
                        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
                        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
                        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
                        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                        eliminarInfoBasicaVariosCiudadanos(request);
                    } else if (request.getParameter(WebKeys.ACCION).equals(CANCELAR_ANOTACION)) {

                    }
                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.DESASOCIAR_FOLIOS)) {
                    request.getSession().setAttribute(WebKeys.TURNO, respuesta.getPayload());
                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.VALIDAR_TURNO_PARA_CALIFICACION)) {
                    request.getSession().setAttribute(WebKeys.HABILITADO_CALIFICACION, new Boolean(true));
                    request.getSession().setAttribute(WebKeys.MOSTRAR_OPCION_INSCRIPCION, new Boolean(true));
                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.VERIFICACION_ACTOS_REGISTRO)) {
                    request.getSession().setAttribute(AWCalificacion.HAY_ACTOS_VENCIDOS, new Boolean(respuesta.isValidacionVerificacionActos()));
                    request.getSession().setAttribute(AWCalificacion.PLAZO_VENCIMIENTO, respuesta.getPlazoVencimiento());
                    boolean optionReproduccionSellos_Enabled = respuesta.isOptionReproduccionSellos_Enabled();
                    session.setAttribute(CAccionCalificacionReproduccionSellos.PARAMID_OPTION_ENABLED, new java.lang.Boolean(optionReproduccionSellos_Enabled));
                    session.setAttribute(AWTramiteSuspension.LISTA_RESPUESTAS_USUARIO, respuesta.getListaRespuestaTrams());
                    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
                    if (turno != null && turno.getNotas() != null && turno.getNotas().size() > 0) {
                        Nota nota = null;
                        List notasDevolutivas = new Vector();
                        for (int i = 0; i < turno.getNotas().size(); i++) {
                            nota = (Nota) turno.getNotas().get(i);
                            if (nota != null && nota.getTiponota() != null && nota.getTiponota().isDevolutiva()) {
                                notasDevolutivas.add(nota);
                            }
                        }
                        session.setAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION, notasDevolutivas);
                    }
                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.REGISTRAR_PAGO_EXCESO)) {
                    request.getSession().setAttribute(WebKeys.CONFIRMACION_PAGO_EXCESO, respuesta.getPayload());
                    request.getSession().removeAttribute(AWCalificacion.PAGO_EXCESO);
                    request.getSession().removeAttribute(AWCalificacion.RAZON_DEVOLUCION);
                }

                if (respuesta.getTipoEvento().equals(EvnRespCalificacion.CONFRONTACION)) {

                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.CONSULTAR_SEG_ENG_EXISTENTES)) {
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
                                    }
                                }
                            }
                        }
                    }

                    request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, respuesta.getNextOrden());

                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.CONSULTAR_PROPIETARIOS_FOLIO)) {
                    request.getSession().setAttribute(AWCalificacion.LISTA_PROPIETARIOS_FOLIO, respuesta.getPropietariosFolios());
                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.VOLVER_AGREGAR_CIUDADANOS)) {
                    request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, respuesta.getListaCompletaCiudadanos());
                } else if (respuesta.getTipoEvento().equals(EvnRespCalificacion.CERRAR_FOLIO)) {
                    Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
                    List listaFoliosCerrados = respuesta.getLstFoliosCerrados();
                    if (turno != null && turno.getSolicitud() != null) {
                        SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
                        if (solicitud.getSolicitudFolios() != null && solicitud.getSolicitudFolios().size() > 0
                                && listaFoliosCerrados != null && listaFoliosCerrados.size() > 0) {
                            List listaFolios = solicitud.getSolicitudFolios();
                            for (int i = 0; i < listaFoliosCerrados.size(); i++) {
                                String vect1 = (String) listaFoliosCerrados.get(i);
                                Iterator iterListaFolios = listaFolios.iterator();
                                while (iterListaFolios.hasNext()) {
                                    SolicitudFolio solicitudfolio = (SolicitudFolio) iterListaFolios.next();
                                    if (solicitudfolio != null && solicitudfolio.getFolio() != null) {
                                        Folio folio = solicitudfolio.getFolio();
                                        if (folio.getIdMatricula().equals(vect1)) {
                                            if (folio.getEstado() != null) {
                                                folio.getEstado().setIdEstado(CEstadoFolio.CERRADO);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    session.setAttribute(WebKeys.TURNO, turno);
                    /*String[] vect1 =((String[])lstFolios.get(i));				
					f.setIdMatricula(vect1[0]);				
					EstadoFolio estado = new EstadoFolio();
					estado.setIdEstado(CEstadoFolio.CERRADO);
					estado.setComentario(razonCierre);
					f.setEstado(estado);*/

                }
            } else if (request.getParameter(WebKeys.ACCION).equals(CREAR_FOLIO_ENGLOBE)) {
                request.getSession().removeAttribute("LISTA_FOLIOS_ENGLOBE");
                request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
                request.getSession().removeAttribute("COMPLEMENTACION_FOLIO_ENGLOBE");
                request.getSession().removeAttribute("FOLIO_ENGLOBE");
                request.getSession().removeAttribute("TABLA_ANOTACIONES_ENGLOBE");
            }
        } else if (evento instanceof EvnRespNextOrdenAnotacion) {
            EvnRespNextOrdenAnotacion evn = (EvnRespNextOrdenAnotacion) evento;
            request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, evn.getPayload());
        } else if (evento instanceof EvnRespSeguridad) {
            request.getSession().removeAttribute(WebKeys.PROCESO);
            request.getSession().removeAttribute(WebKeys.FASE);
            EvnRespSeguridad respuesta = (EvnRespSeguridad) evento;
            if (respuesta.getProceso() != null) {
                request.getSession().setAttribute(WebKeys.PROCESO, respuesta.getProceso());
            }
            if (respuesta.getLista() != null) {
                request.getSession().setAttribute(WebKeys.LISTA_FASES, respuesta.getLista());
            }
            if (respuesta.getTabla() != null) {
                Integer i
                        = (Integer) ((Hashtable) respuesta.getTabla()).get(
                                EvnRespSeguridad.NUMERO_MAXIMO_IMPRESIONES);
                if (i == null) {
                    i = new Integer(0);
                }
                List tipoNota = (List) respuesta.getTabla().get(WebKeys.LISTA_TIPOS_NOTAS);
                request.getSession().setAttribute(WebKeys.LISTA_TIPOS_NOTAS, tipoNota);
                request.getSession().setAttribute(WebKeys.NUMERO_MAX_IMPRESIONES, i);
            }
        } else if (evento instanceof EvnRespCiudadano) {
            EvnRespCiudadano evn = (EvnRespCiudadano) evento;
            String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);

            if (evn.isCiudadanoEncontrado()) {
                request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + ver, evn.getCiudadano().getApellido1());
                request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + ver, evn.getCiudadano().getApellido2());
                request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + ver, evn.getCiudadano().getNombre());
                request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA + ver, evn.getCiudadano().getSexo());
                if (evn.getCiudadano().getNombre() == null || evn.getCiudadano().getNombre().isEmpty()) {
                    request.getSession().setAttribute(CFolio.CIUDADANO_EDITABLE + ver, new Boolean(true));
                }
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

        }
    }

    /**
     * @param request
     * @return
     * @throws ValidacionParametrosSegregacion
     */
    private Evento agregarFolioDerivado(HttpServletRequest request) throws ValidacionParametrosSegregacion {

        ValidacionParametrosSegregacion exception = new ValidacionParametrosSegregacion();
        HttpSession session = request.getSession();
        List foliosDerivados = (List) session.getAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS);

        if (foliosDerivados == null) {
            foliosDerivados = new Vector();
        }

        Iterator itFoliosD = foliosDerivados.iterator();

        /**
         * @author: Henry Gomez Rocha
         * @change: Modificacion del tipo de dato a las variables
         * porcentajeTotal y porcentaje de tipo double a tipo BigDecimal, para
         * controlar inexactitud ocacionada por usar tipo double al almacenar la
         * division de los lotes al momento de segregar. * MANTIS: 0003877
         *
         */
        BigDecimal porcentajeTotal = new BigDecimal("0.0");
        while (itFoliosD.hasNext()) {
            try {
                porcentajeTotal = porcentajeTotal.add(new BigDecimal((((FolioDerivado) itFoliosD.next()).getPorcentaje()).toString()));
            } catch (NumberFormatException e) {
                porcentajeTotal = porcentajeTotal.add(new BigDecimal("0.0"));
            }
        }

        String descripcion = request.getParameter(CFolioDerivado.DESCRIPCION);
        String hectareas = request.getParameter(CFolioDerivado.HECTAREAS);
        String metros = request.getParameter(CFolioDerivado.METROS);
        String centimetros = request.getParameter(CFolioDerivado.CENTIMETROS);
        String porcen = request.getParameter(CFolioDerivado.PORCENTAJE);
        String unidad = request.getParameter(CFolioDerivado.UNIDAD_MEDIDA);

        int unidadmedidaexception = 3;

        if (hectareas == null || hectareas.length() <= 0) {
            unidadmedidaexception--;
        }

        if (metros == null || metros.length() <= 0) {
            unidadmedidaexception--;
        }

        if (centimetros == null || centimetros.length() <= 0) {
            unidadmedidaexception--;
        }

        if (unidadmedidaexception == 0) {
            exception.addError("Debe digitar por lo menos una unidad de medida v?lida para el Area del Terreno");
        }

        if (descripcion == null || descripcion.length() <= 0) {
            exception.addError("Debe ingresar una descripcion para el folio derivado");
        }

        if (unidad.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar una unidad de medida valida para el folio derivado");
        }

        Iterator itUnidades = ((List) session.getServletContext().getAttribute(WebKeys.LISTA_UNIDADES_MEDIDA)).iterator();

        while (itUnidades.hasNext()) {
            OPLookupCodes code = (OPLookupCodes) itUnidades.next();
            if (code.getCodigo().equals(unidad)) {
                unidad = code.getValor();
            }
        }

        porcen = (porcen.replaceAll("%", "")).trim();
        BigDecimal porcentaje = new BigDecimal("0.0");

        if (porcen != null) {
            if (porcen.length() > 0 || !porcen.equals("")) {
                try {
                    porcentaje = new BigDecimal(porcen);
                    if (porcentaje.doubleValue() <= 0) {
                        exception.addError("El porcentaje no puede ser negativo o cero");
                    }
                } catch (NumberFormatException e) {
                    exception.addError("El porcentaje es invalido");
                }
            }
        }

        /**
         * @author: Henry Gomez Rocha
         * @change: Almacenando en la variable porcentajeTotal la totalidad de
         * los lotes y verificando que dicha totalidad no sea superio a 100. *
         * MANTIS: 0003877
         *
         */
        porcentajeTotal = porcentajeTotal.add(porcentaje);
        if (porcentajeTotal.doubleValue() > 100) {
            exception.addError("El porcentaje no puede superar 100%");
        }

        double intHectareas = 0;
        double intMetros = 0;
        double intCentimetros = 0;
        int areaException = 3;
        try {
            intHectareas = Double.parseDouble(hectareas);
            if (intHectareas <= 0) {
                areaException--;
            }
        } catch (NumberFormatException e) {
            exception.addError("El area es invalida");
        }

        try {
            intMetros = Double.parseDouble(metros);
            if (intHectareas <= 0) {
                areaException--;
            }
        } catch (NumberFormatException e) {
            exception.addError("El area es invalida");
        }

        try {
            intCentimetros = Double.parseDouble(centimetros);
            if (intHectareas <= 0) {
                areaException--;
            }
        } catch (NumberFormatException e) {
            exception.addError("El area es invalida");
        }

        if (areaException == 0) {
            exception.addError("Todas las unidades de medida son negativas o cero");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        FolioDerivado derivado = new FolioDerivado();
        derivado.setHectareas(hectareas);
        derivado.setMetros(metros);
        derivado.setCentimetros(centimetros);
        derivado.setDescripcion(descripcion);
        derivado.setPorcentaje(porcen);

        foliosDerivados.add(derivado);
        session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS, foliosDerivados);

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
     * @param accion1
     * @return
     * @throws AccionWebException
     */
    private Evento obtenerBloqueoFolios(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
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
    
    /**
     * @param request
     * @param accion1
     * @return
     * @throws AccionWebException
     */
    private Evento revisarConfrontacionCorrectiva(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
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

    /**
     * @param request
     * @return
     */
    private Evento delegarDigitacionMasivaNuevo(HttpServletRequest request) {

        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        String respuesta = null;

        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        //Atributos a sesion propios de la fase.
        session.setAttribute(WebKeys.EXCEPCION, new Boolean(true));
        respuesta = EvnCalificacion.WF_DIGITACION;
        return new EvnCalificacion(usuarioAuriga, userNeg, turno, fase, respuesta, CAvanzarTurno.AVANZAR_PUSH, AWCalificacion.DELEGAR_DIGITACION_MASIVA);

    }

    private Evento doProcess_BtnDeshacerCambios(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();

        ValidacionParametrosException exception;
        exception = new ValidacionParametrosException();

        org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
        param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        Circulo param_Circulo;
        param_Circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

        Turno param_Turno;
        param_Turno = (Turno) session.getAttribute(WebKeys.TURNO);

        Fase param_Fase;
        param_Fase = (Fase) session.getAttribute(WebKeys.FASE);

        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);

        List procesos = (List) request.getSession().getAttribute(WebKeys.LISTA_PROCESOS);

        String idProceso = request.getParameter(AWSeguridad.ID_PROCESO);
        if ((rol == null) || rol.getRolId().equals("")) {
            throw new EstacionInvalidaException("La estacion no es valida");
        }

        if (idProceso == null) {
            throw new ProcesoInvalidoException("El proceso no es valido");
        }
        if (idProceso.trim().equals("")) {
            throw new ProcesoInvalidoException("El proceso no puede ser vacio");
        }
        Iterator itProcesos = procesos.iterator();
        Proceso proceso = null;
        Proceso temp = null;

        while (itProcesos.hasNext()) {
            temp = (Proceso) itProcesos.next();
            long valIdProceso = Long.parseLong(idProceso);
            if (temp.getIdProceso() == valIdProceso) {
                proceso = temp;
            }
        }

        EvnCalificacion local_Result;
        local_Result = new EvnCalificacion(param_UsuarioAuriga, param_UsuarioSir, param_Turno, param_Fase, "", EvnCalificacion.PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION);

        local_Result.setRol(rol);
        local_Result.setProceso(proceso);
        /**
         * OSBERT LINERO - IRIDIUM Telecomunicaciones e informatica Ltda. Cambio
         * realizado para resolver requerimiento 092 - Incidencia Mantis 02940 -
         * Agrega un atributo a sesion para verificar si se deshicieron los
         * cambios en calificacion (deshacer segregacion).
         */
        request.getSession().setAttribute(WebKeys.DESHICIERON_CAMBIOS_CALIFICACION, true);
        return local_Result;
        // ---------------------------------------------------------------------------------
    } // end-method: doProcess_BtnDeshacerCambios

    /**
     * @param request
     * @return
     */
    private Evento avanzarFirmaRegistrador(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        String impresora = request.getParameter(WebKeys.IMPRESORA);
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_FIRMA);
        ev.setTurno(turno);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);
        ev.setIsFirma(1);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        ev.setInfoUsuario(infoUsuario);
        String respuesta = request.getParameter(AWCalificacion.RESPUESTAWF);
        if (impresora != null && !impresora.equals(WebKeys.SIN_SELECCIONAR)) {
            ev.setImpresoraSeleccionada(impresora);
        } else {
            ev.setImpresoraSeleccionada("Microsoft Print to PDF");
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        /**
         * @Author Carlos Torres
         * @Mantis 13176
         * @Chaged
         */
        if (respuesta.equals(AWCalificacion.FIRMA_REGISTRO_DEVOLVER)) {
            ev.setRespuestaWF(CRespuesta.AJUSTAR);
            ev.setTipoEvento(EvnCalificacion.DEVOLVER_FIRMA);
        } else {
            ev.setRespuestaWF(CRespuesta.CONFIRMAR);
        }
        
        try{
            HermodService hs = HermodService.getInstance();
            if(hs.isTurnoDevuelto(turno.getIdWorkflow()) && !respuesta.equals(AWCalificacion.FIRMA_REGISTRO_DEVOLVER)){
                ev.setRespuestaWF(CRespuesta.NOTA_DEVOLUTIVA);
                    }
        } catch(HermodException he){
            System.out.println("No ha sido posible consultar si el turno es devuelto");
        }
        
        
        return ev;
    }
    
    
    
    /**
     * @param request
     * @return
     */
    private Evento avanzarRevisorConfrontacion(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_REVISOR);
        ev.setTurno(turno);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);
        ev.setIsFirma(1);
        ev.setUsuarioSir(usuarioSIR);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        ev.setInfoUsuario(infoUsuario);
        ev.setRespuestaWF(CRespuesta.EXITO);
        ev.setTipoEvento(EvnCalificacion.AVANZAR_REVISOR);
        
        return ev;
    }
    
    /**
     * @param request
     * @return
     */
    private Evento devolverRevisorConfrontacion(HttpServletRequest request) throws AccionWebException {
        
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException exception = new ValidacionParametrosException();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        
        if(turno != null){
        List notaInfo = turno.getNotas();
        
        boolean hasNote = false;
        if(notaInfo == null || notaInfo.isEmpty()){
            exception.addError("Debe ingresar una nota informativa de tipo Envio Confrontacion Correctiva");
        } else{
            Iterator itNote = notaInfo.iterator();
            while (itNote.hasNext()){
                Nota nota = (Nota) itNote.next();
                if(nota.getTiponota().getIdTipoNota().equals(CNota.REVISION_CONFRONTACION_BAD)){
                    hasNote = true;
                }
            }
            
            if(!hasNote){
                exception.addError("Debe ingresar una nota informativa de tipo Envio Confrontacion Correctiva");
            }
        }
        }
        
        if(exception.getErrores().size() > 0){
            throw exception;
        }
        
        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_REVISOR);
        ev.setTurno(turno);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);
        ev.setIsFirma(1);
        ev.setUsuarioSir(usuarioSIR);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        ev.setInfoUsuario(infoUsuario);
        ev.setRespuestaWF(CRespuesta.NEGAR);
        ev.setTipoEvento(EvnCalificacion.AVANZAR_REVISOR);
        
        return ev;
    }

    /**
     * @param request
     * @return
     */
    private Evento entregarRegistro(HttpServletRequest request) {
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, AWCalificacion.ENTREGAR_REGISTRO);
        ev.setTurno(turno);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);

        return ev;

    }

    /*JALCAZAR CASO MANTIS 0002225 13/07/2009 Funcion Entrega Multiples Registros */
    private Evento entregarRegistroMult(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String TurnosEntrega = request.getParameter("turnosentrega");
        /**
         * @author : Carlos Torres
         * @Caso Mantis : 11604: Acta - Requerimiento No
         * 030_453_Funcionario_Fase_ Entregado
         */
        List turnosL = (List) request.getSession().getAttribute(WebKeys.LISTA_TURNOS);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        List turnos = new ArrayList();
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

        String[] partes = TurnosEntrega.split(",");
        /**
         * @author : Carlos Torres
         * @Caso Mantis : 11604: Acta - Requerimiento No
         * 030_453_Funcionario_Fase_ Entregado
         */
        if (fase.getID().equals("REG_ENTREGA") || fase.getID().equals("COS_ENTREGAR_ASOCIADOS") || fase.getID().equals("REG_ENTREGA_EXTERNO")) {
            List list = Arrays.asList(partes);
            for (Object turno : turnosL) {
                Turno t = (Turno) turno;
                if (list.contains(t.getIdRelacionActual())) {
                    turnos.add(t.getIdWorkflow());
                }
            }
        } else {
            turnos.addAll(Arrays.asList(partes));
        }
        session.setAttribute(WebKeys.LISTAS_ENTREGA_MULTIPLE, turnos);

        Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, AWCalificacion.ENTREGAR_REGISTRO_MULT);
        ev.setTurnos(turnos);
        ev.setFase(fase);
        ev.setUsuarioNeg(userNeg);

        return ev;

    }

    private void borrarDatosPreviewFolio(HttpServletRequest request) {
        request.getSession().removeAttribute(WebKeys.FOLIO_AUXILIAR);
        request.getSession().removeAttribute(WebKeys.TOTAL_ANOTACIONES_PREVIEW);

        request.getSession().removeAttribute(WebKeys.MAYOR_EXTENSION_FOLIO_PREVIEW);

        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN_PREVIEW);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES_PREVIEW);

        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION_PREVIEW);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS_PREVIEW);

        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR_PREVIEW);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA_PREVIEW);

        request.getSession().removeAttribute(WebKeys.LISTA_FOLIOS_PADRE_PREVIEW);
        request.getSession().removeAttribute(WebKeys.LISTA_FOLIOS_HIJO_PREVIEW);
        request.getSession().removeAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES_PREVIEW);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION_PREVIEW);

        request.getSession().removeAttribute(WebKeys.TURNO_TRAMITE_PREVIEW);
        request.getSession().removeAttribute(WebKeys.TURNO_DEUDA_PREVIEW);
        request.getSession().removeAttribute(WebKeys.USUARIO_BLOQUEO_PREVIEW);

        request.getSession().removeAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_PADRE_PREVIEW);
        request.getSession().removeAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO_PREVIEW);
        request.getSession().removeAttribute(WebKeys.LISTA_TURNOS_FOLIO_TRAMITE_PREVIEW);

        request.getSession().removeAttribute(WebKeys.RECARGA_PREVIEW);
    }
}
