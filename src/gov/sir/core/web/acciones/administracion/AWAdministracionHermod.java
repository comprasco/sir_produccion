package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnAdministracionForseti;
import gov.sir.core.eventos.administracion.EvnAdministracionHermod;
import gov.sir.core.eventos.administracion.EvnRespAdministracionForseti;
import gov.sir.core.eventos.administracion.EvnRespAdministracionHermod;
import gov.sir.core.eventos.comun.EvnRespSistema;
import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.AlcanceGeografico;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancosXCirculo;
import gov.sir.core.negocio.modelo.CanalesRecaudo;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FaseProceso;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.RolPantalla;
import gov.sir.core.negocio.modelo.RolPantallaPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SubAtencionSubSolicitud;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.SubtipoAtencionTipoActo;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoActoDerechoRegistral;
import gov.sir.core.negocio.modelo.TipoCalculo;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoCertificadoValidacion;
import gov.sir.core.negocio.modelo.TipoDerechoReg;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.TipoTarifa;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion;
import gov.sir.core.negocio.modelo.Validacion;
import gov.sir.core.negocio.modelo.ValidacionNota;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.ZonaNotarial;
import gov.sir.core.negocio.modelo.constantes.CAccionNotarial;
import gov.sir.core.negocio.modelo.constantes.CAlcanceGeografico;
import gov.sir.core.negocio.modelo.constantes.CBanco;
import gov.sir.core.negocio.modelo.constantes.CCanalesRecaudo;
import gov.sir.core.negocio.modelo.constantes.CCategoria;
import gov.sir.core.negocio.modelo.constantes.CCausalRestitucion;
import gov.sir.core.negocio.modelo.constantes.CCertificado;
import gov.sir.core.negocio.modelo.constantes.CCheckItem;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CCirculoFestivo;
import gov.sir.core.negocio.modelo.constantes.CCirculoNotarial;
import gov.sir.core.negocio.modelo.constantes.CCuentasBancarias;
import gov.sir.core.negocio.modelo.constantes.CDepartamento;
import gov.sir.core.negocio.modelo.constantes.CEntidadPublica;
import gov.sir.core.negocio.modelo.constantes.CEstacion;
import gov.sir.core.negocio.modelo.constantes.CEstacionRecibo;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFaseProceso;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.constantes.CMunicipio;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CPaginacion;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSubtipoAtencion;
import gov.sir.core.negocio.modelo.constantes.CSubtipoSolicitud;
import gov.sir.core.negocio.modelo.constantes.CSucursalBanco;
import gov.sir.core.negocio.modelo.constantes.CTarifa;
import gov.sir.core.negocio.modelo.constantes.CTipoActo;
import gov.sir.core.negocio.modelo.constantes.CTipoCalculo;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoDerechoRegistral;
import gov.sir.core.negocio.modelo.constantes.CTipoFotocopia;
import gov.sir.core.negocio.modelo.constantes.CTipoImpuesto;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTipoPago;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.constantes.CUsuario;
import gov.sir.core.negocio.modelo.constantes.CUsuarioSubtipoAtencion;
import gov.sir.core.negocio.modelo.constantes.CValidacion;
import gov.sir.core.negocio.modelo.constantes.CValidacionNota;
import gov.sir.core.negocio.modelo.constantes.CVereda;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPago;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.AgregarCirculoNotarialException;
import gov.sir.core.web.acciones.excepciones.ConsultarProbRevisionCalificacionException;
import gov.sir.core.web.acciones.excepciones.EntregaMasivaCertificadosException;
import gov.sir.core.web.acciones.excepciones.ImprimirFolioException;
import gov.sir.core.web.acciones.excepciones.ValidacionAbogadosSubtiposAtencionException;
import gov.sir.core.web.acciones.excepciones.ValidacionAdicionEstacionReciboException;
import gov.sir.core.web.acciones.excepciones.ValidacionCanalesRecaudoException;
import gov.sir.core.web.acciones.excepciones.ValidacionCausalRestitucionException;
import gov.sir.core.web.acciones.excepciones.ValidacionCheckItemException;
import gov.sir.core.web.acciones.excepciones.ValidacionCirculoTipoPagoException;
import gov.sir.core.web.acciones.excepciones.ValidacionCuentasBancariasException;
import gov.sir.core.web.acciones.excepciones.ValidacionFirmaRegistradorException;
import gov.sir.core.web.acciones.excepciones.ValidacionNatJuridicaEntidadException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAgregarNotaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCategoriaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEntidadPublicaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosEstacionCirculoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosValidacion;
import gov.sir.core.web.acciones.excepciones.ValidacionReimpresionCalificacion;
import gov.sir.core.web.acciones.excepciones.ValidacionResetEstacionReciboException;
import gov.sir.core.web.acciones.excepciones.ValidacionTipoCertificadoValidacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionTipoNotaException;
import gov.sir.core.web.acciones.excepciones.ValidacionTipoTarifaPorCirculoException;
import gov.sir.core.web.acciones.excepciones.ValidacionValidaNotaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAdminSubtipoAtencion;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosOrdenSubtipoAtencion;
import gov.sir.core.web.acciones.excepciones.ValidacionZonaNotarialException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.hermod.HermodService;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos provenientes
 * de solicitudes realizadas a través del protocolo HTTP para administración de
 * objetos de Hermod
 *
 * @author jmendez
 * @author dsalas
 */
public class AWAdministracionHermod extends SoporteAccionWeb {

    /**
     * Constante que identifica las acción de adicionar un banco al sistema
     */
    public static final String ADICIONA_BANCO = "ADICIONA_BANCO";

    /**
     * Constante que identifica las acción de eliminar un banco al sistema
     */
    public static final String ELIMINA_BANCO = "ELIMINA_BANCO";

    /**
     * Constante que identifica las acción de seleccionar un banco en la sesión
     */
    public static final String SELECCIONA_BANCO = "SELECCIONA_BANCO";

    /**
     * Constante que identifica las acción de adicionar una sucursal al sistema
     */
    public static final String ADICIONA_SUCURSAL = "ADICIONA_SUCURSAL";

    /**
     * Constante que identifica las acción de eliminar una sucursal del sistema
     */
    public static final String ELIMINA_SUCURSAL = "ELIMINA_SUCURSAL";

    /**
     * Constante que identifica las acción de terminar la edición de sucursales
     * de un banco
     */
    public static final String TERMINA_SUCURSAL = "TERMINA_SUCURSAL";

    /**
     * Constante que identifica las acción de adicionar un tipo de alcance
     * geográfico al sistema
     */
    public static final String ADICIONA_ALCANCE_GEOGRAFICO = "ADICIONA_ALCANCE_GEOGRAFICO";

    /**
     * Constante que identifica las acción de eliminar un tipo de alcance
     * geográfico del sistema
     */
    public static final String ELIMINA_ALCANCE_GEOGRAFICO = "ELIMINA_ALCANCE_GEOGRAFICO";

    /**
     * Constante que identifica las acción de adicionar una Tarifa al sistema
     */
    public static final String ADICIONA_TARIFA = "ADICIONA_TARIFA";

    /**
     * Constante que identifica las acción de eliminar una Tarifa del sistema
     */
    public static final String ELIMINA_TARIFA = "ELIMINA_TARIFA";

    /**
     * Constante que identifica las acción de terminar la edición de tarifas
     * asociadas a un tipo
     */
    public static final String TERMINA_TARIFA = "TERMINA_TARIFA";

    /**
     * Constante que identifica las acción de adicionar un tipo de Tarifa al
     * sistema
     */
    public static final String ADICIONA_TIPO_TARIFA = "ADICIONA_TIPO_TARIFA";

    /**
     * Constante que identifica las acción de eliminación de un tipo de Tarifa
     * del sistema
     */
    public static final String ELIMINA_TIPO_TARIFA = "ELIMINA_TIPO_TARIFA";

    /**
     * Constante que identifica las acción de selección de un tipo de Tarifa en
     * la sesión
     */
    public static final String SELECCIONA_TIPO_TARIFA = "SELECCIONA_TIPO_TARIFA";

    /**
     * Constante que identifica las acción de adicionar un tipo de fotocopia al
     * sistema
     */
    public static final String ADICIONA_TIPO_FOTOCOPIA = "ADICIONA_TIPO_FOTOCOPIA";

    /**
     * Constante que identifica las acción de eliminar un tipo de fotocopia del
     * sistema
     */
    public static final String ELIMINA_TIPO_FOTOCOPIA = "ELIMINA_TIPO_FOTOCOPIA";

    /**
     * Constante que identifica las acción de adicionar un subtipo de solicitud
     * al sistema
     */
    public static final String ADICIONA_SUBTIPO_SOLICITUD = "ADICIONA_SUBTIPO_SOLICITUD";

    /**
     * Constante que identifica las acción de eliminar un subtipo de solicitud
     * del sistema
     */
    public static final String ELIMINA_SUBTIPO_SOLICITUD = "ELIMINA_SUBTIPO_SOLICITUD";

    /**
     * Constante que identifica las acción de editar un subtipo de solicitud del
     * sistema (visualizacion)
     */
    public static final String EDITA_SUBTIPO_SOLICITUD = "EDITA_SUBTIPO_SOLICITUD";

    /**
     * Constante que identifica las acción de editar un subtipo de solicitud al
     * sistema (negocio)
     */
    public static final String EDITAR_SUBTIPO_SOLICITUD = "EDITAR_SUBTIPO_SOLICITUD";

    /**
     * Constante que identifica las acción de adicionar un oplookup code al
     * sistema
     */
    public static final String ADICIONA_OPLOOKUP_CODE = "ADICIONA_OPLOOKUP_CODE";

    /**
     * Constante que identifica las acción de editar un oplookup code al sistema
     */
    public static final String UPDATE_OPLOOKUP_CODE = "UPDATE_OPLOOKUP_CODE";

    /**
     * Constante que identifica las acción de eliminar un oplookup code del
     * sistema
     */
    public static final String ELIMINA_OPLOOKUP_CODE = "ELIMINA_OPLOOKUP_CODE";

    /**
     * Constante que identifica las acción de terminar la edición de oplookup
     * codes de un tipo determinado
     */
    public static final String TERMINA_OPLOOKUP_CODE = "TERMINA_OPLOOKUP_CODE";

    /**
     * Constante que identifica las acción de consultar los oplookup type al
     * sistema
     */
    public static final String CONSULTA_OPLOOKUP_TYPE = "CONSULTA_OPLOOKUP_TYPE";

    /**
     * Constante que identifica las acción de adicionar un oplookup type al
     * sistema
     */
    public static final String ADICIONA_OPLOOKUP_TYPE = "ADICIONA_OPLOOKUP_TYPE";

    /**
     * Constante que identifica las acción de editar un oplookup type al sistema
     */
    public static final String UPDATE_OPLOOKUP_TYPE = "UPDATE_OPLOOKUP_TYPE";

    /**
     * Constante que identifica las acción de eliminar un oplookup type al
     * sistema
     */
    public static final String ELIMINA_OPLOOKUP_TYPE = "ELIMINA_OPLOOKUP_TYPE";

    /**
     * Constante que identifica las acción de seleccionar un oplookup type en la
     * sesión
     */
    public static final String SELECCIONA_OPLOOKUP_TYPE = "SELECCIONA_OPLOOKUP_TYPE";

    /**
     * Constante que identifica las acción de adicionar un tipo de cálculo al
     * sistema
     */
    public static final String ADICIONA_TIPO_CALCULO = "ADICIONA_TIPO_CALCULO";

    /**
     * Constante que identifica las acción de eliminar un tipo de cálculo al
     * sistema
     */
    public static final String ELIMINA_TIPO_CALCULO = "ELIMINA_TIPO_CALCULO";

    /**
     * Constante que identifica las acción de adicionar un tipo de impujesto al
     * sistema
     */
    public static final String ADICIONA_TIPO_IMPUESTO = "ADICIONA_TIPO_IMPUESTO";

    /**
     * Constante que identifica las acción de eliminar un tipo de impujesto al
     * sistema
     */
    public static final String ELIMINA_TIPO_IMPUESTO = "ELIMINA_TIPO_IMPUESTO";

    /**
     * Constante que identifica las acción de adicionar un tipo de acto al
     * sistema
     */
    public static final String ADICIONA_TIPO_ACTO = "ADICIONA_TIPO_ACTO";

    /**
     * Constante que identifica las acción de adicionar un tipo de acto al
     * sistema
     */
    public static final String EDITA_TIPO_ACTO = "EDITA_TIPO_ACTO";

    /**
     * Constante que identifica las acción de editar un tipo de acto del sistema
     */
    public static final String EDITAR_TIPO_ACTO = "EDITAR_TIPO_ACTO";

    /**
     * Constante que identifica las acción de eliminar un tipo de acto del
     * sistema
     */
    public static final String ELIMINA_TIPO_ACTO = "ELIMINA_TIPO_ACTO";

    /**
     * Constante que identifica las acción de cargar los subtipo de atención al
     * sistema
     */
    public static final String CARGAR_SUBTIPO_ATENCION = "CARGAR_SUBTIPO_ATENCION";

    /**
     * Constante que identifica las acción de adicionar un subtipo de atención
     * al sistema
     */
    public static final String ADICIONA_SUBTIPO_ATENCION = "ADICIONA_SUBTIPO_ATENCION";

    /**
     * Constante que identifica las acción de eliminar un subtipo de atención al
     * sistema
     */
    public static final String ELIMINA_SUBTIPO_ATENCION = "ELIMINA_SUBTIPO_ATENCION";

    /**
     * Constante que identifica las acción de adicionar un subtipo de atención
     * al sistema
     */
    public static final String EDITAR_SUBTIPO_ATENCION = "EDITAR_SUBTIPO_ATENCION";

    /**
     * Constante que identifica las acción de adicionar un subtipo de atención
     * al sistema
     */
    public static final String EDITA_SUBTIPO_ATENCION = "EDITA_SUBTIPO_ATENCION";

    /**
     * Constante que identifica las acción de adicionar un tipo de derecho
     * registral al sistema
     */
    public static final String ADICIONA_TIPO_DERECHO_REGISTRAL = "ADICIONA_TIPO_DERECHO_REGISTRAL";

    /**
     * Constante que identifica las acción de eliminar un tipo de derecho
     * registral al sistema
     */
    public static final String ELIMINA_TIPO_DERECHO_REGISTRAL = "ELIMINA_TIPO_DERECHO_REGISTRAL";

    /**
     * Constante que identifica las acción de adicionar un tipo de acción
     * notarial al sistema
     */
    public static final String ADICIONA_ACCION_NOTARIAL = "ADICIONA_ACCION_NOTARIAL";

    /**
     * Constante que identifica las acción de editar un tipo de acción notarial
     * al sistema
     */
    public static final String EDITA_ACCION_NOTARIAL = "EDITA_ACCION_NOTARIAL";

    /**
     * Constante que identifica la accion de habilitar opciones de edición de
     * negocios jurídicos de reparto
     */
    public static final String HABILITA_EDICION_ACCION_NOTARIAL = "HABILITA_EDICION_ACCION_NOTARIAL";

    /**
     * Constante que identifica la accion de deshabilitar opciones de edición de
     * negocios jurídicos de reparto
     */
    public static final String DESHABILITA_EDICION_ACCION_NOTARIAL = "DESHABILITA_EDICION_ACCION_NOTARIAL";

    /**
     * Constante que identifica las acción de eliminar un tipo de acción
     * notarial al sistema
     */
    public static final String ELIMINA_ACCION_NOTARIAL = "ELIMINA_ACCION_NOTARIAL";

    /**
     * Constante que identifica las acción de adicionar una categoría al sistema
     */
    public static final String ADICIONA_CATEGORIA = "ADICIONA_CATEGORIA";

    /**
     * Constante que identifica las acción de terminar la edición de categoria
     * asociadas a un tipo
     */
    public static final String TERMINA_CATEGORIA = "TERMINA_CATEGORIA";

    /**
     * Constante que identifica las acción de eliminar una categoría del sistema
     */
    public static final String EDITA_CATEGORIA = "EDITA_CATEGORIA";

    /**
     * Constante que identifica las acción de eliminar una categoría del sistema
     */
    public static final String ELIMINA_CATEGORIA = "ELIMINA_CATEGORIA";

    /**
     * Constante que identifica las acción de adicionar una estación recibo al
     * sistema
     */
    public static final String SET_ESTACION_RECIBO = "SET_ESTACION_RECIBO";

    /**
     * Constante que identifica que el usuario es administrador nacional
     */
    public static final String CIRCULOS_ADMINISTRADOR_NACIONAL = "CIRCULOS_ADMINISTRADOR_NACIONAL";

    /**
     * Constante que identifica los procesos validos para los recibos
     */
    public static final String MOSTRAR_CIRCULO_PASO_ESTACION_RECIBO = "MOSTRAR_CIRCULO_PASO_ESTACION_RECIBO";

    /**
     * Constante que identifica los procesos validos para los recibos
     */
    public static final String PROCESOS_VALIDOS_RECIBOS = "PROCESOS_VALIDOS_RECIBOS";

    /**
     * Constante que identifica los procesos validos para los recibos
     */
    public static final String PROCESOS_VALIDOS_RECIBOS_PROCESADO = "PROCESOS_VALIDOS_RECIBOS_PROCESADO";

    /**
     * Constante que identifica las acción de mostrar el circulo correspondiente
     * al administrador del sistema
     */
    public static final String MOSTRAR_CIRCULO_ESTACION_RECIBO = "MOSTRAR_CIRCULO_ESTACION_RECIBO";

    /**
     * Constante que indica que debe modificarse el valor de la liquidación de
     * un turno previamente seleccionado (proceso de devoluciones).
     */
    public static final String MODIFICAR_VALOR_LIQUIDACION_DEVOLUCION_SELECCIONADA = "MODIFICAR_VALOR_LIQUIDACION_DEVOLUCION_SELECCIONADA";

    /**
     * Constante para regresar a la pantalla en la que se selecciona el valor de
     * una liquidación asociada con una devolución de dineros.
     */
    public static final String VOLVER_SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION = "VOLVER_SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION";

    /**
     * Constante que identifica las acción de adicionar una estación recibo al
     * sistema
     */
    public static final String SET_AGREGAR_ESTACION_RECIBO = "SET_AGREGAR_ESTACION_RECIBO";

    /**
     * Constante que identifica las acción de traspasar los seriales de recbio
     * de una estacion a otra
     */
    public static final String TRASPASO_ESTACION_RECIBO = "TRASPASO_ESTACION_RECIBO";

    /**
     * Constante que identifica las acción de eliminar una estación recibo del
     * sistema
     */
    public static final String ELIMINA_ESTACION_RECIBO = "ELIMINA_ESTACION_RECIBO";

    /**
     * Constante que identifica las acción de hacer reset del número de recibo
     * en una estación recibo
     */
    public static final String RESET_ESTACION_RECIBO_TRASPASO = "RESET_ESTACION_RECIBO_TRASPASO";

    /**
     * Constante que identifica las acción para consultar las estaciones recibo
     * por círculo
     */
    public static final String CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO = "CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO";

    /**
     * Constante que identifica las acción para consultar las estaciones recibo
     * por círculo
     */
    public static final String CONSULTA_ESTACIONES_POR_ID = "CONSULTA_ESTACIONES_POR_ID";

    /**
     * Constante que identifica las acción para consultar las estaciones recibo
     * por círculo
     */
    public static final String CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO_TRASPASO
            = "CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO_TRASPASO";

    /**
     * Constante que identifica las acción de hacer reset del último valor del
     * número de recibo en una estación recibo
     */
    public static final String RESET_ULTIMO_VALOR_ESTACION_RECIBO = "RESET_ULTIMO_VALOR_ESTACION_RECIBO";

    /**
     * Constante que identifica las acción de consultar las estaciones recibo
     * existentes en el sistema
     */
    public static final String CONSULTA_ESTACION_RECIBO = "CONSULTA_ESTACION_RECIBO";

    /**
     * Constante que identifica las acción de consultar una minuta existente en
     * el sistema
     */
    public static final String CONSULTA_MINUTA = "CONSULTA_MINUTA";

    /**
     * Constante que identifica las acción de editar una minuta existente en el
     * sistema
     */
    public static final String EDITA_MINUTA = "EDITA_MINUTA";

    /**
     * Constante que identifica las acción de adicionar un tipo de pago a un
     * círculo
     */
    public static final String ADICIONA_CIRCULO_TIPO_PAGO = "ADICIONA_CIRCULO_TIPO_PAGO";

    /**
     * Constante que identifica las acción de eliminar un tipo de pago de un
     * círculo
     */
    public static final String ELIMINA_CIRCULO_TIPO_PAGO = "ELIMINA_CIRCULO_TIPO_PAGO";

    /**
     * Constante que identifica las acción de SELECCIONAR un Circulo con sus
     * tipos de pago en el sistema
     */
    public static final String SELECCIONA_CIRCULO_TIPO_PAGO = "SELECCIONA_CIRCULO_TIPO_PAGO";

    /**
     * Constante que identifica las acción de SELECCIONAR las cuentas bancarias
     * para el círculo
     */
    public static final String SELECCIONA_CUENTA_BANCARIA = "SELECCIONA_CUENTA_BANCARIA";

    /**
     * Constante que identifica las acción de adicionar una firma de registrador
     * en el sistema
     */
    public static final String ADICIONA_FIRMA_REGISTRADOR = "ADICIONA_FIRMA_REGISTRADOR";

    /**
     * Constante que identifica las acción de activar una firma de registrador
     * en el sistema
     */
    public static final String ACTIVA_FIRMA_REGISTRADOR = "ACTIVA_FIRMA_REGISTRADOR";

    /**
     * Constante que identifica las acción de inactivar una firma de registrador
     * en el sistema
     */
    public static final String INACTIVA_FIRMA_REGISTRADOR = "INACTIVA_FIRMA_REGISTRADOR";

    /**
     * Constante que identifica las acción de inactivar definitivamente una
     * firma de registrador en el sistema
     */
    public static final String INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR = "INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR";

    /**
     * Constante que identifica las acción de seleccionar las firmas de
     * registrador de un círculo
     */
    public static final String SELECCIONA_CIRCULO_FIRMA_REGISTRADOR = "SELECCIONA_CIRCULO_FIRMA_REGISTRADOR";

    /**
     * Constante que identifica las acción de buscar las firma de registrador
     * para un circulo seleccionado
     */
    public static final String BUSCAR_FIRMA_REGISTRADOR_CIRCULO = "BUSCAR_FIRMA_REGISTRADOR_CIRCULO";

    /**
     * Constante que identifica las acción de guardar la firma de registrador al
     * registrador a añadir
     */
    public static final String GUARDAR_FIRMA_REGISTRADOR = "GUARDAR_FIRMA_REGISTRADOR";

    /**
     * Constante que identifica las acción de hacer preview una firma de
     * registrador
     */
    public static final String PREVIEW_FIRMA_REGISTRADOR = "PREVIEW_FIRMA_REGISTRADOR";

    /**
     * Constante que identifica las acción de consultar los abogados existentes
     * en un círculo determinado.
     */
    public static final String CONSULTA_ABOGADOS_POR_CIRCULO = "CONSULTA_ABOGADOS_POR_CIRCULO";

    /**
     * Constante que identifica las acción de actualizar los subtipos de
     * atención de un grupo de abogados.
     */
    public static final String ACTUALIZA_SUBTIPOS_ATENCION_ABOGADOS
            = "ACTUALIZA_SUBTIPOS_ATENCION_ABOGADOS";

    /**
     * Constante que identifica las acción de adicionar una validación de nota
     * al sistema
     */
    public static final String ADICIONA_VALIDACION_NOTA = "ADICIONA_VALIDACION_NOTA";

    /**
     * Constante que identifica las acción de eliminar una validación de nota
     * del sistema
     */
    public static final String ELIMINA_VALIDACION_NOTA = "ELIMINA_VALIDACION_NOTA";

    /**
     * Constante que identifica las acción de consultar las fases de un proceso
     */
    public static final String CONSULTA_FASE_PROCESO = "CONSULTA_FASE_PROCESO";

    //ADMINISTRACION DE CUASALES DE RESTITUCION.
    /**
     * Constante que identifica la acción de eliminar un Causal de restitución
     * del sistema
     *
     */
    public static final String ELIMINA_CAUSAL_RESTITUCION = "ELIMINA_CAUSAL_RESTITUCION";

    /**
     * Constante que identifica la acción de editar un Causal de restitución del
     * sistema
     *
     */
    public static final String EDITAR_CAUSAL_RESTITUCION = "EDITAR_CAUSAL_RESTITUCION";

    /**
     * Constante que identifica la acción de adicionar un Causal de restitución
     * del sistema
     *
     */
    public static final String ADICIONA_CAUSAL_RESTITUCION = "ADICIONA_CAUSAL_RESTITUCION";

    /**
     * Constante que indica que se van a editar los detalles de un causal de
     * restitución.
     */
    public static final String EDITAR_DETALLES_CAUSAL_RESTITUCION = "EDITAR_DETALLES_CAUSAL_RESTITUCION";

    /**
     * Constante que indica que se quiere regresar a la pantalla en la que se
     * escoge el causal de restitución que quiere editarse.
     */
    public static final String VOLVER_SELECCIONAR_CAUSAL = "VOLVER_SELECCIONAR_CAUSAL";

    /**
     * Constante que identifica las acción de adicionar un tipo de certificado
     * de validación al sistema
     */
    public static final String ADICIONA_TIPO_CERTIFICADO_VALIDACION
            = "ADICIONA_TIPO_CERTIFICADO_VALIDACION";

    /**
     * Constante que identifica las acción de eliminar un tipo de certificado de
     * validación al sistema
     */
    public static final String ELIMINA_TIPO_CERTIFICADO_VALIDACION
            = "ELIMINA_TIPO_CERTIFICADO_VALIDACION";

    /**
     * Constante que identifica las acción de consultar los tipo de certificado
     * de validación del sistema
     */
    public static final String CONSULTA_VALIDACIONES_DE_TIPO_CERTIFICADO
            = "CONSULTA_VALIDACIONES_DE_TIPO_CERTIFICADO";

    /**
     * Constante que identifica las acción de adicionar un tipo de nota al
     * sistema
     */
    public static final String ADICIONA_TIPO_NOTA = "ADICIONA_TIPO_NOTA";

    /**
     * Constante que identifica las acción de adicionar un tipo de nota al
     * sistema
     */
    public static final String ADICIONA_TIPO_NOTA_INF = "ADICIONA_TIPO_NOTA_INF";

    /**
     * Constante que identifica las acción de adicionar un tipo de nota al
     * sistema
     */
    public static final String ADICIONA_TIPO_NOTA_DEV = "ADICIONA_TIPO_NOTA_DEV";

    /**
     * Constante que identifica las acción de MODIFICAR un tipo de nota EN EL
     * sistema
     */
    public static final String MODIFICA_TIPO_NOTA = "MODIFICA_TIPO_NOTA";

    /**
     * Constante que identifica las acción de MODIFICAR un tipo de nota EN EL
     * sistema
     */
    public static final String MODIFICA_TIPO_NOTA_INF = "MODIFICA_TIPO_NOTA_INF";

    /**
     * Constante que identifica las acción de MODIFICAR un tipo de nota EN EL
     * sistema
     */
    public static final String MODIFICA_TIPO_NOTA_DEV = "MODIFICA_TIPO_NOTA_DEV";

    /**
     * Constante que identifica las acción de eliminar un tipo de nota del
     * sistema
     */
    public static final String ELIMINA_TIPO_NOTA = "ELIMINA_TIPO_NOTA";

    /**
     * Constante que identifica las acción de eliminar un tipo de nota del
     * sistema
     */
    public static final String ELIMINA_TIPO_NOTA_INF = "ELIMINA_TIPO_NOTA_INF";

    /**
     * Constante que identifica las acción de eliminar un tipo de nota del
     * sistema
     */
    public static final String ELIMINA_TIPO_NOTA_DEV = "ELIMINA_TIPO_NOTA_DEV";

    /**
     * Constante que identifica las acción de consultar los tipos de nota por
     * proceso
     */
    public static final String CONSULTA_TIPO_NOTA_POR_PROCESO = "CONSULTA_TIPO_NOTA_POR_PROCESO";

    /**
     * Constante que identifica las acción de consultar los tipos de nota
     * informativas por proceso
     */
    public static final String CONSULTA_TIPO_NOTA_INF_POR_PROCESO = "CONSULTA_TIPO_NOTA_INF_POR_PROCESO";

    /**
     * Constante que identifica las acción de consultar los tipos de nota
     * devolutivas por proceso
     */
    public static final String CONSULTA_TIPO_NOTA_DEV_POR_PROCESO = "CONSULTA_TIPO_NOTA_DEV_POR_PROCESO";

    /**
     * Constante que identifica las acción de adicionar un check item al sistema
     */
    public static final String ADICIONA_CHECK_ITEM = "ADICIONA_CHECK_ITEM";

    /**
     * Constante que identifica las acción de eliminar un check item al sistema
     */
    public static final String ELIMINA_CHECK_ITEM = "ELIMINA_CHECK_ITEM";

    /**
     * Constante que identifica las acción de editar un check item al sistema
     * (visualizacion)
     */
    public static final String EDITAR_CHECK_ITEM = "EDITAR_CHECK_ITEM";

    /**
     * Constante que identifica las acción de editar un check item al sistema
     * (negocio)
     */
    public static final String EDITA_CHECK_ITEM = "EDITA_CHECK_ITEM";

    /**
     * Constante que identifica las acción de consultar check item por subtipos
     * de solicitud
     */
    public static final String CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD
            = "CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD";

    /**
     * Constante que identifica las acción de entregar masivamente una serie de
     * certificados
     */
    public static final String ENTREGA_MASIVA_CERTIFICADOS = "ENTREGA_MASIVA_CERTIFICADOS";

    /**
     * Constante que identifica las acción de entregar masivamente una serie de
     * consultas
     */
    public static final String ENTREGA_MASIVA_CONSULTAS = "ENTREGA_MASIVA_CONSULTAS";

    /**
     * Constante que identifica las acción de consultar por fecha una serie de
     * turnos que se encuentran en la face CER_ENTREGAR
     */
    public static final String CONSULTA_TURNOS_CER_ENTREGA_POR_FECHA
            = "CONSULTA_TURNOS_CER_ENTREGA_POR_FECHA";

    /**
     * Constante que identifica las acción de consultar por fecha una serie de
     * turnos que se encuentran en la face CON_ENTREGAR_COMPLEJA y
     * CON_ENTREGAR_SIMPLE
     */
    public static final String CONSULTA_TURNOS_CON_ENTREGA_POR_FECHA
            = "CONSULTA_TURNOS_CON_ENTREGA_POR_FECHA";

    /**
     * Constante que identifica la acción de consultar la probabilidad de
     * revision de calificacion
     */
    public static final String CONSULTAR_PROB_REV_CALIFICACION
            = "CONSULTAR_PROB_REV_CALIFICACION";

    /**
     * Constante que identifica la acción de modificar la probabilidad de
     * revision de calificacion
     */
    public static final String MODIFICAR_PROB_REV_CALIFICACION
            = "MODIFICAR_PROB_REV_CALIFICACION";

    /**
     * Constante que identifica la acción de consultar liquidaciones no pagadas
     */
    public static final String CONSULTA_SOLICITUDES_NO_PAGADAS
            = "CONSULTA_SOLICITUDES_NO_PAGADAS";

    /**
     * Constante que identifica la acción de eliminar liquidaciones no pagadas
     */
    public static final String ELIMINAR_SOLICITUDES_NO_PAGADAS
            = "ELIMINAR_SOLICITUDES_NO_PAGADAS";

    /**
     * Constante que identifica la acción de eliminar liquidaciones no pagadas
     */
    public static final String ELIMINAR_TODAS_LAS_SOLICITUDES_NO_PAGADAS
            = "ELIMINAR_TODAS_LAS_SOLICITUDES_NO_PAGADAS";

    /**
     * Constante que identifica la acción de solicitar la consulta de turnos de
     * calificacion
     */
    public static final String CONSULTA_TURNOS_CALIFICACION
            = "CONSULTA_TURNOS_CALIFICACION";

    /**
     * Constante que identifica la acción de solicitar la correcion de una
     * calificacion
     */
    public static final String SOLICITAR_CORRECCION_CALIFICACION
            = "SOLICITAR_CORRECCION_CALIFICACION";

    /**
     * Constante que identifica la acción de solicitar los tipos de tarifa por
     * círculo
     */
    public static final String CONSULTA_TIPOS_TARIFA_POR_CIRCULO
            = "CONSULTA_TIPOS_TARIFA_POR_CIRCULO";

    /**
     * Constante que identifica la acción de solicitar la descripcion de una
     * nota informativa
     */
    public static final String COLOCAR_DESCRIPCION_NOTA_INF
            = "COLOCAR_DESCRIPCION_NOTA_INF";

    /**
     * Constante que identifica las acción de terminar la utilización de los
     * servicios de la acción WEB (Para limpiar la sesión y redirigir a la
     * página principal de páginas administrativas
     */
    public static final String TERMINA = "TERMINA";

    /**
     * Constante que identifica la acción de solicitar la tarifa dentro de la
     * lista
     */
    public static final String CONSULTA_TARIFA_CIRCULO
            = "CONSULTA_TARIFA_CIRCULO";

    /**
     * Constante que identifica la acción de solicitar la adición de una tarifa
     * en el círculo
     */
    public static final String ADICIONA_TARIFA_POR_CIRCULO
            = "ADICIONA_TARIFA_POR_CIRCULO";

    /**
     * Constante que identifica la acción de solicitar la adición de roles a una
     * estación
     */
    public static final String ADD_ROLES_ESTACION = "ADD_ROLES_ESTACION";

    /**
     * Constante que identifica la acción de soliticar remover roles de la
     * estación
     */
    public static final String REMOVE_ROLES_ESTACION = "REMOVE_ROLES_ESTACION";

    /**
     * Constante que identifica la acción de solicitar la adición de usuarios a
     * una estación
     */
    public static final String ADD_USUARIOS_ESTACION = "ADD_USUARIOS_ESTACION";

    /**
     * Constante que identifica la acción de soliticar remover usuarios de la
     * estación
     */
    public static final String REMOVE_USUARIOS_ESTACION = "REMOVE_USUARIOS_ESTACION";

    /**
     * Constante que identifica la acción de solicitar la adición de un orden de
     * un subtipo atencion
     */
    public static final String CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION = "CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION";

    /**
     * Constante que identifica la acción de solicitar la adición de un orden de
     * un subtipo atencion
     */
    public static final String ADICIONA_ORDEN_SUBTIPOATENCION = "ADICIONA_ORDEN_SUBTIPOATENCION";

    /**
     * Constante que identifica la acción de soliticar remover el orden de un
     * subtipo atencion
     */
    public static final String ELIMINA_ORDEN_SUBTIPOATENCION = "ELIMINA_ORDEN_SUBTIPOATENCION";

    /**
     * Constante que identifica la acción de soliticar cargar el listado de
     * calificadores de un subtipo de atencion
     */
    public static final String CARGAR_LISTADO_CALIFICADORES_SUBTIPOATENCION = "CARGAR_LISTADO_CALIFICADORES_SUBTIPOATENCION";

    /////////////////////////////////////////////////////////////////////////////////////////////
    //valores de variables almacenadas en sesion
    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un Banco seleccionado
     */
    public static final String BANCO_SELECCIONADO = "BANCO_SELECCIONADO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de sucursales de Banco seleccionadas
     */
    public static final String SUCURSALES_SELECCIONADAS = "SUCURSALES_SELECCIONADAS";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un oplookup type
     */
    public static final String LISTA_OPLOOKUP_TYPES_CONSULTA = "LISTA_OPLOOKUP_TYPES_CONSULTA";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un oplookup type seleccionado
     */
    public static final String OPLOOKUP_TYPE_SELECCIONADO = "OPLOOKUP_TYPE_SELECCIONADO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de oplookup codes seleccionadoa
     */
    public static final String OPLOOKUP_CODES_SELECCIONADOS = "OPLOOKUP_CODES_SELECCIONADOS";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un tipo de tarifa seleccionado
     */
    public static final String TIPO_TARIFA_SELECCIONADO = "TIPO_TARIFA_SELECCIONADO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de tarifas seleccionadas
     */
    public static final String TARIFAS_SELECCIONADAS = "TARIFAS_SELECCIONADAS";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una minuta seleccionada
     */
    public static final String MINUTA_CONSULTADA = "MINUTA_CONSULTADA";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * un circulo seleccionado
     */
    public static final String CIRCULO_SELECCIONADO = "CIRCULO_SELECCIONADO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de estaciones recibo por círculo seleccionadas
     */
    public static final String LISTA_CIRCULO_ESTACIONES_RECIBO = "LISTA_CIRCULO_ESTACIONES_RECIBO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de tipos de pago por círculo seleccionadas
     */
    public static final String LISTA_CIRCULO_TIPOS_PAGO = "LISTA_CIRCULO_TIPOS_PAGO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de firmas notariales por círculo seleccionadas
     */
    public static final String LISTA_CIRCULO_FIRMAS_NOTARIALES = "LISTA_CIRCULO_FIRMAS_NOTARIALES";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de estaciones de un círculo seleccionadas
     */
    public static final String LISTA_ESTACIONES_DE_CIRCULO = "LISTA_ESTACIONES_DE_CIRCULO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una estación recibo seleccionada
     */
    public static final String ESTACION_RECIBO_SELECCIONADA = "ESTACION_RECIBO_SELECCIONADA";

    /**
     * Constante que identifica las variable del HttpSession que indica si se
     * está editando una estación
     */
    public static final String ESTACION_RECIBO_EDIT_ULTIMO_VALOR_OK
            = "ESTACION_RECIBO_EDIT_ULTIMO_VALOR_OK";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de abogados por círculo seleccionadas
     */
    public static final String LISTA_ABOGADOS_DE_CIRCULO = "LISTA_ABOGADOS_DE_CIRCULO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * la lista de fases de un proceso determinado
     */
    public static final String LISTA_FASES_DE_PROCESO = "LISTA_FASES_DE_PROCESO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de firmas notariales por círculo seleccionadas
     */
    public static final String LISTA_VALIDACIONES_DE_TIPO_CERTIFICADO
            = "LISTA_VALIDACIONES_DE_TIPO_CERTIFICADO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de tipos de nota por proceso
     */
    public static final String LISTA_TIPOS_NOTA_POR_PROCESO = "LISTA_TIPOS_NOTA_POR_PROCESO";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * una lista de check items por subtipo solicitud
     */
    public static final String LISTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD
            = "LISTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * el valor de la probabilidad de revision de calificacion
     */
    public static final String PROBABILIDAD_REV_CALIFICACION
            = "PROBABILIDAD_REV_CALIFICACION";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * la lista de solicitudes no pagadas
     */
    public static final String LISTA_SOLICITUDES_NO_PAGADAS
            = "LISTA_SOLICITUDES_NO_PAGADAS";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * la lista de turnos de califiaccion de objetos strings
     */
    public static final String LISTA_TURNOS_STRING
            = "LISTA_TURNOS_STRING";

    /**
     * Constante que identifica las variable del HttpSession donde se almacena
     * la lista de turnos de califiaccion de objetos turno
     */
    public static final String LISTA_TURNOS
            = "LISTA_TURNOS";
    /**
     * Accion de pedir las estaciones de un círculo
     */
    public static final String CONSULTA_ESTACIONES_CIRCULO = "CONSULTA_ESTACIONES_CIRCULO";

    /**
     * Accion de iniciar configuración de una estación
     */
    public static final String ADMIN_UNA_ESTACION = "ADMIN_UNA_ESTACION";

    /**
     * Identifica la vista de administración de estaciones
     */
    public static final String ADMIN_ESTACION_CONSULTA_ESTACIONES_CIRCULO = "ADMIN_ESTACION_CONSULTA_ESTACIONES_CIRCULO";

    /**
     * Constante que identifica la accion de agregar una naturaleza juridica
     * para reparto
     */
    public static final String ADICIONA_NAT_JURIDICA_ENTIDAD = "ADICIONA_NAT_JURIDICA_ENTIDAD";

    /**
     * Constante que identifica la accion de editar una naturaleza juridica para
     * reparto
     */
    public static final String EDITAR_NAT_JURIDICA_ENTIDAD = "EDITAR_NAT_JURIDICA_ENTIDAD";

    /**
     * Constante que identifica la accion de habilitar opciones de edición de
     * naturalezas jurídicas de entidad
     */
    public static final String HABILITA_EDICION_NAT_JURIDICA_ENTIDAD = "HABILITA_EDICION_NAT_JURIDICA_ENTIDAD";

    /**
     * Constante que identifica la accion de deshabilitar opciones de edición de
     * naturalezas jurídicas de entidad
     */
    public static final String DESHABILITA_EDICION_NAT_JURIDICA_ENTIDAD = "DESHABILITA_EDICION_NAT_JURIDICA_ENTIDAD";

    /**
     * Constante que identifica la accion de eliminar una naturaleza juridica
     * para reparto
     */
    public static final String ELIMINA_NAT_JURIDICA_ENTIDAD = "ELIMINA_NAT_JURIDICA_ENTIDAD";

    /**
     * Constante que identifica la accion de agregar una entidad publica para
     * reparto
     */
    public static final String ADICIONA_ENTIDAD_PUBLICA = "ADICIONA_ENTIDAD_PUBLICA";

    /**
     * Constante que identifica la accion de eliminar una entidad publica para
     * reparto
     */
    public static final String ELIMINA_ENTIDAD_PUBLICA = "ELIMINA_ENTIDAD_PUBLICA";

    /**
     * Constante que identifica la accion de editar una entidad publica para
     * reparto
     */
    public static final String EDITA_ENTIDAD_PUBLICA = "EDITA_ENTIDAD_PUBLICA";

    /**
     * Constante que identifica la accion de habilitar opciones de edición de
     * entidades públicas
     */
    public static final String HABILITA_EDICION_ENTIDAD_PUBLICA = "HABILITA_EDICION_ENTIDAD_PUBLICA";

    /**
     * Constante que identifica la accion de deshabilitar opciones de edición de
     * entidades públicas
     */
    public static final String DESHABILITA_EDICION_ENTIDAD_PUBLICA = "DESHABILITA_EDICION_ENTIDAD_PUBLICA";

    /**
     * Constante que identifica la accion de consultar una entidad publica para
     * reparto, por naturaleza jurídica
     */
    public static final String CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA = "CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA";

    /**
     * Constante que identifica que se quiere consultar las naturalezas
     * juridicas para reparto
     */
    public static final String CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO = "CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO";

    /**
     * Constante que identifica que se quiere consultar un formulario de
     * calificacion para impresion
     */
    public static final String IMPRESION_FORMULARIOS_CALIFICACION = "IMPRESION_FORMULARIOS_CALIFICACION";

    /**
     * Constante que identifica que se quiere consultar un formulario de
     * correcciones para impresion
     */
    public static final String IMPRESION_FORMULARIOS_CORRECCIONES = "IMPRESION_FORMULARIOS_CORRECCIONES";

    /**
     * Constante que identifica que se quiere consultar las naturalezas
     * juridicas para reparto
     */
    public static final String CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO_ENTIDAD = "CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO_ENTIDAD";

    /**
     * Constante que identifica que se quiere obtener las impresoras del circulo
     * del usuario
     */
    public static final String OBTENER_IMPRESORAS_CIRCULO_CALIFICACION = "OBTENER_IMPRESORAS_CIRCULO_CALIFICACION";

    /**
     * Constante que identifica que se quiere obtener las impresoras del circulo
     * del usuario
     */
    public static final String OBTENER_IMPRESORAS_CIRCULO_CORRECCIONES = "OBTENER_IMPRESORAS_CIRCULO_CORRECCIONES";
    
    public static final String OBTENER_SOLICITUDES_PAGINA = "OBTENER_SOLICITUDES_PAGINA";

    /**
     * Constante que identifica que se quieren avanzar los turnos vencidos de
     * pago mayor valor
     */
    public static final String DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR = "DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR";

    /**
     * Constante que identifica que se quieren obtener la lista de los turnos
     * vencidos de pago mayor valor
     */
    public static final String OBTENER_VENCIDOS_PAGO_MAYOR_VALOR = "OBTENER_VENCIDOS_PAGO_MAYOR_VALOR";
    
    public static final String ADICIONA_TIPO_VALIDACION = "ADICIONA_TIPO_VALIDACION";
    
    public static final String OBTENER_PENDIENTES_PAGO_MAYOR_VALOR = "OBTENER_PENDIENTES_PAGO_MAYOR_VALOR";
    
    public static final String ELIMINAR_PENDIENTES_PAGO_MAYOR_VALOR = "ELIMINAR_PENDIENTES_PAGO_MAYOR_VALOR";
    
    public static final String COLOCAR_DESCRIPCION_NOTA_INF_PMY = "COLOCAR_DESCRIPCION_NOTA_INF_PMY";

    /**
     * Constante que identifica donde se guardan en session el listado de
     * calificadores de un circulo
     */
    public static final String LISTA_CALIFICADORES = "LISTA_CALIFICADORES";

    /**
     * Constante que identifica donde se guardan en session el listado de
     * subtipos de atencion
     */
    public static final String LISTA_SUBTIPOSATENCION = "LISTA_SUBTIPOSATENCION";

    /**
     * Constante que identifica al valor a devolver por concepto de impuestos
     */
    public static final String VALOR_IMPUESTOS = "VALOR_IMPUESTOS";

    /**
     * Constante que identifica al valor a devolver por concepto de derechos de
     * registro
     */
    public static final String VALOR_DERECHOS = "VALOR_DERECHOS";

    /**
     * Constante que identifica al valor a devolver por concepto de certificados
     */
    public static final String VALOR_CERTIFICADOS = "VALOR_CERTIFICADOS";

    /**
     * Constante que indica que se va a modificar el valor a devolver de un
     * turno del proceso de devolución.
     */
    public static final String MODIFICAR_LIQUIDACION_TURNO_DEVOLUCION = "MODIFICAR_LIQUIDACION_TURNO_DEVOLUCION";

    /**
     * Constante que indica que se seleccionó un turno para realizar una
     * devolución.
     */
    public static final String SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION = "SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION";

    /**
     * Constante que identifica donde se guardan en session el listado de
     * calificadores de un subtipo de atencion
     */
    public static final String LISTA_CALIFICADORES_SUBTIPOSATENCION = "LISTA_CALIFICADORES_SUBTIPOSATENCION";

    /**
     * Constante que identifica donde se guardan en session el listado de los
     * nombres de archivos de firmas
     */
    public static final String LISTA_FIRMAS_REGISTRADOR = "LISTA_FIRMAS_REGISTRADOR";

    /**
     * Constante que identifica donde se guardan en session el listado de los
     * nombres de archivos de firmas
     */
    public static final String LISTA_ELEMENTOS_FIRMAS_REGISTRADOR = "LISTA_ELEMENTOS_FIRMAS_REGISTRADOR";

    /**
     * Constante que identifica donde se guardan el nombre del archivo de firma
     * seleccionado
     */
    public static final String NOMBRE_FIRMA_REGISTRADOR = "NOMBRE_FIRMA_REGISTRADOR";

    /**
     * Constante que identifica donde se guardan la firma que se le va hacer
     * preview
     */
    public static final String FIRMA_PREVIEW = "FIRMA_PREVIEW";

    /**
     * Constante que identifica donde se guardan la firma que se va a adicionar
     */
    public static final String FIRMA_REGISTRADOR = "FIRMA_REGISTRADOR";

    /**
     * Constante que identifica donde se guardan la firma que se va a adicionar
     */
    public static final String RELOAD = "RELOAD";
    
    public static final String LISTA_CIRCULOS_ELEMENTO = "LISTA_CIRCULOS_ELEMENTO";
    
    public static final String LISTA_DEPARTAMENTOS = "LISTA_DEPARTAMENTOS";
    
    public static final String LISTA_MUNICIPIOS = "LISTA_MUNICIPIOS";
    
    public static final String LISTA_VEREDAS = "LISTA_VEREDAS";
    
    public static final String LISTA_ZONAS_NOTARIALES = "LISTA_ZONAS_NOTARIALES";
    
    public static final String LISTA_CIRCULOS_NOTARIALES = "LISTA_CIRCULOS_NOTARIALES";
    
    public static final String CIRCULO_NOTARIAL_EDITADO = "CIRCULO_NOTARIAL_EDITADO";
    
    public static final String ADICIONA_ZONA_NOTARIAL = "ADICIONA_ZONA_NOTARIAL";
    
    public static final String ADICIONA_CIRCULO_NOTARIAL = "ADICIONA_CIRCULO_NOTARIAL";
    
    public static final String SELECCIONA_ZONA_NOTARIAL = "SELECCIONA_ZONA_NOTARIAL";
    
    public static final String SELECCIONA_ZONA_NOTARIAL_DEPTO = "SELECCIONA_ZONA_NOTARIAL_DEPTO";
    
    public static final String SELECCIONA_ZONA_NOTARIAL_MUNICIPIO = "SELECCIONA_ZONA_NOTARIAL_MUNICIPIO";
    
    public static final String ELIMINA_ZONA_NOTARIAL = "ELIMINA_ZONA_NOTARIAL";
    
    public static final String EDITAR_ZONA_NOTARIAL = "EDITAR_ZONA_NOTARIAL";
    
    public static final String ELIMINA_CIRCULO_NOTARIAL = "ELIMINA_CIRCULO_NOTARIAL";
    
    public static final String EDITAR_CIRCULO_NOTARIAL = "EDITAR_CIRCULO_NOTARIAL";

    /**
     * Constante que identifica si el turno ya tiene una liquidación negativa
     * para la devolución.
     */
    public static final String LIQUIDACION_NEGATIVA = "LIQUIDACION_NEGATIVA";
    
    public static final String CARGAR_CIRCULOS_NOTARIALES = "CARGAR_CIRCULOS_NOTARIALES";
    
    public static final String EJECUTAR_EDICION_CIRCULO_NOTARIAL = "EJECUTAR_EDICION_CIRCULO_NOTARIAL";
    
    public static final String TERMINA_EDICION = "TERMINA_EDICION";
    
    public static final String CARGAR_CIRCULOS_NOTARIALES_ZONA_NOTARIAL = "CARGAR_CIRCULOS_NOTARIALES_ZONA_NOTARIAL";
    
    public static final String CARGAR_ZONAS_NOTARIALES = "CARGAR_ZONAS_NOTARIALES";
    
    public static final String CARGAR_ROLES_PANTALLAS = "CARGAR_ROLES_PANTALLAS";
    
    public static final String AGREGAR_ROL_PANTALLA = "AGREGAR_ROL_PANTALLA";
    
    public static final String ELIMINAR_ROL_PANTALLA = "ELIMINAR_ROL_PANTALLA";

    /**
     * Constante que identifica la lista de pantallas en session*
     */
    public static final String LISTA_PANTALLAS_ADMINISTRATIVAS = "LISTA_PANTALLAS_ADMINISTRATIVAS";

    /**
     * Constante que identifica la lista de rolpantallas a eliminar en la
     * session*
     */
    public static final String LISTA_ROLES_PANTALLAS_ELIMINAR = "LISTA_ROLES_PANTALLAS_ELIMINAR";

    /**
     * Constante que identifica la lista de rolpantallas a agregar en la
     * session*
     */
    public static final String LISTA_ROLES_PANTALLAS_AGREGAR = "LISTA_ROLES_PANTALLAS_AGREGAR";

    /**
     * Constante que identifica la lista total de rolpantallas en la session*
     */
    public static final String LISTA_ROLES_PANTALLAS = "LISTA_ROLES_PANTALLAS";

    /**
     * Constante que identifica el parametro de peticion con el rolpantalla a
     * eliminar*
     */
    public static final String ROL_PANTALLA_A_ELIMINAR = "ROL_PANTALLA_A_ELIMINAR";

    /**
     * Constante que identifica el parametro de peticion con el rol a asignar a
     * rolpantalla*
     */
    public static final String ROL_ASIGNAR = "ROL_ASIGNAR";

    /**
     * Constante que identifica el parametro de peticion con la pantalla a
     * asignar a rolpantalla*
     */
    public static final String PANTALLA_ASIGNAR = "PANTALLA_ASIGNAR";

    /* Variables para reimpresión de constancias de testamentos*/
    public static final String IMPRESION_FORMULARIOS_TESTAMENTOS = "IMPRESION_FORMULARIOS_TESTAMENTOS";
    public static final String OBTENER_IMPRESORAS_CIRCULO_TESTAMENTOS = "OBTENER_IMPRESORAS_CIRCULO_TESTAMENTOS";
    
    public static final String ADICIONA_BANCO_CIRCULO = "ADICIONA_BANCO_CIRCULO";
    public static final String SELECCIONA_BANCOS_X_CIRCULO = "SELECCIONA_BANCOS_X_CIRCULO";
    public static final String BANCOS_X_CIRCULO = "BANCOS_X_CIRCULO";
    public static final String ELIMINA_BANCO_CIRCULO = "ELIMINA_BANCO_CIRCULO";
    public static final String ACTIVAR_BANCO_PRINCIPAL = "ACTIVAR_BANCO_PRINCIPAL";
    
    public static final String ADICIONA_CUENTA_BANCO_CIRCULO = "ADICIONA_CUENTA_BANCO_CIRCULO";
    public static final String LISTADO_BANCOS_X_CIRCULO = "LISTADO_BANCOS_X_CIRCULO";
    public static final String CUENTAS_BANCARIAS = "CUENTAS_BANCARIAS";
    public static final String CUENTAS_X_CIRCULO_BANCO = "CUENTAS_X_CIRCULO_BANCO";
    public static final String ACTUALIZAR_ESTADO_CTA_BANCARIA = "ACTUALIZAR_ESTADO_CTA_BANCARIA";
    public static final String ACT_INACT_CTA_BANCARIA = "ACT_INACT_CTA_BANCARIA";
    public static final String ADICIONA_CANAL_RECAUDO = "ADICIONA_CANAL_RECAUDO";
    public static final String CANALES_RECAUDO = "CANALES_RECAUDO";
    public static final String ACTUALIZAR_ESTADO_CANAL_RECAUDO = "ACTUALIZAR_ESTADO_CANAL_RECAUDO";
    public static final String ACT_INACT_CANAL_RECAUDO = "ACT_INACT_CANAL_RECAUDO";
    public static final String ACTUALIZAR_ESTADO_CTP = "ACTUALIZAR_ESTADO_CTP";
    public static final String ACT_INACT_CTP = "ACT_INACT_CTP";

    /**
     * Este método se encarga de procesar la solicitud del
     * <code>HttpServletRequest</code> de acuerdo al tipo de accion que tenga
     * como parámetro
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        boolean isMultipart = FileUpload.isMultipartContent(request);
        if (isMultipart) {
            return uploadFileFirma(request);
        }
        
        HttpSession session = request.getSession();
        String accion = request.getParameter(WebKeys.ACCION).trim();
        
        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_BANCO)) {
            return adicionaBanco(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_BANCO)) {
            return eliminaBanco(request);
        } else if (accion.equals(AWAdministracionHermod.SELECCIONA_BANCO)) {
            return seleccionaBanco(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_SUCURSAL)) {
            return adicionaSucursal(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_SUCURSAL)) {
            return eliminaSucursal(request);
        } else if (accion.equals(AWAdministracionHermod.TERMINA_SUCURSAL)) {
            session.removeAttribute(AWAdministracionHermod.SUCURSALES_SELECCIONADAS);
            return null;
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_ALCANCE_GEOGRAFICO)) {
            return adicionaAlcanceGeografico(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_ALCANCE_GEOGRAFICO)) {
            return eliminaAlcanceGeografico(request);
        } else if (accion.equals(AWAdministracionHermod.TERMINA)) {
            return limpiaSesion(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TARIFA)) {
            return adicionaTarifa(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_TARIFA)) {
            return eliminaTarifa(request);
        } else if (accion.equals(AWAdministracionHermod.TERMINA_TARIFA)) {
            session.removeAttribute(AWAdministracionHermod.TARIFAS_SELECCIONADAS);
            return null;
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_TARIFA)) {
            return adicionaTipoTarifa(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_TIPO_TARIFA)) {
            return eliminaTipoTarifa(request);
        } else if (accion.equals(AWAdministracionHermod.SELECCIONA_TIPO_TARIFA)) {
            return seleccionaTipoTarifa(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_FOTOCOPIA)) {
            return adicionaTipoFotocopia(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_TIPO_FOTOCOPIA)) {
            return eliminaTipoFotocopia(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_SUBTIPO_SOLICITUD)) {
            return adicionaSubtipoSolicitud(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_SUBTIPO_SOLICITUD)) {
            return eliminaSubtipoSolicitud(request);
        } else if (accion.equals(AWAdministracionHermod.EDITA_SUBTIPO_SOLICITUD)) {
            return editaSubtipoSolicitud(request);
        } else if (accion.equals(AWAdministracionHermod.EDITAR_SUBTIPO_SOLICITUD)) {
            return editarSubtipoSolicitud(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_OPLOOKUP_TYPE)) {
            return consultaOPLookupType(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_OPLOOKUP_TYPE)) {
            return adicionaOPLookupType(request);
        } else if (accion.equals(AWAdministracionHermod.UPDATE_OPLOOKUP_TYPE)) {
            return updateOPLookupType(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_OPLOOKUP_TYPE)) {
            return eliminaOPLookupType(request);
        } else if (accion.equals(AWAdministracionHermod.SELECCIONA_OPLOOKUP_TYPE)) {
            return seleccionaOPLookupType(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_OPLOOKUP_CODE)) {
            return adicionaOPLookupCode(request);
        } else if (accion.equals(AWAdministracionHermod.UPDATE_OPLOOKUP_CODE)) {
            return updateOPLookupCode(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_OPLOOKUP_CODE)) {
            return eliminaOPLookupCode(request);
        } else if (accion.equals(AWAdministracionHermod.TERMINA_OPLOOKUP_CODE)) {
            session.removeAttribute(AWAdministracionHermod.OPLOOKUP_CODES_SELECCIONADOS);
            return null;
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_CALCULO)) {
            return adicionaTipoCalculo(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_TIPO_CALCULO)) {
            return eliminaTipoCalculo(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_IMPUESTO)) {
            return adicionaTipoImpuesto(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_TIPO_IMPUESTO)) {
            return eliminaTipoImpuesto(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_ACTO)) {
            return adicionaTipoActo(request);
        } else if (accion.equals(AWAdministracionHermod.EDITA_TIPO_ACTO)) {
            return editaTipoActo(request);
        } else if (accion.equals(AWAdministracionHermod.EDITAR_TIPO_ACTO)) {
            return editarTipoActo(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_TIPO_ACTO)) {
            return eliminaTipoActo(request);
        } else if (accion.equals(AWAdministracionHermod.EDITA_TIPO_ACTO)) {
            return eliminaTipoActo(request);
        } else if (accion.equals(AWAdministracionHermod.CARGAR_SUBTIPO_ATENCION)) {
            return cargarSubTipoAtencion(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_SUBTIPO_ATENCION)) {
            return adicionaSubTipoAtencion(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_SUBTIPO_ATENCION)) {
            return eliminaSubTipoAtencion(request);
        } else if (accion.equals(AWAdministracionHermod.EDITAR_SUBTIPO_ATENCION)) {
            return editarSubTipoAtencion(request);
        } else if (accion.equals(AWAdministracionHermod.EDITA_SUBTIPO_ATENCION)) {
            return editaSubTipoAtencion(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_DERECHO_REGISTRAL)) {
            return adicionaTipoDerechoRegistral(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_TIPO_DERECHO_REGISTRAL)) {
            return eliminaTipoDerechoRegistral(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_ACCION_NOTARIAL)) {
            return adicionaAccionNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.EDITA_ACCION_NOTARIAL)) {
            return editarAccionNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.HABILITA_EDICION_ACCION_NOTARIAL)) {
            return habilitarEdicionAccionNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.DESHABILITA_EDICION_ACCION_NOTARIAL)) {
            return desHabilitarEdicionAccionNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_ACCION_NOTARIAL)) {
            return eliminaAccionNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_CATEGORIA)) {
            return adicionaCategoria(request);
        } else if (accion.equals(AWAdministracionHermod.EDITA_CATEGORIA)) {
            return editaCategoria(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_CATEGORIA)) {
            return eliminaCategoria(request);
            
        } //MODIFICACIONES DEVOLUCION DE DINERO
        else if (accion.equals(AWAdministracionHermod.SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION)) {
            session.removeAttribute(AWAdministracionHermod.VALOR_CERTIFICADOS);
            session.removeAttribute(AWAdministracionHermod.VALOR_IMPUESTOS);
            session.removeAttribute(AWAdministracionHermod.VALOR_DERECHOS);
            return modificarValorDevolucion(request);
        } else if (accion.equals(AWAdministracionHermod.MODIFICAR_VALOR_LIQUIDACION_DEVOLUCION_SELECCIONADA)) {
            return modificarValorDevolucionSeleccionada(request);
        } else if (accion.equals(AWAdministracionHermod.VOLVER_SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION)) {
            session.removeAttribute(WebKeys.TURNO);
            session.removeAttribute(AWAdministracionHermod.VALOR_CERTIFICADOS);
            session.removeAttribute(AWAdministracionHermod.VALOR_IMPUESTOS);
            session.removeAttribute(AWAdministracionHermod.VALOR_DERECHOS);
            return null;
        } else if (accion.equals(AWAdministracionHermod.MOSTRAR_CIRCULO_ESTACION_RECIBO)
                || accion.equals(AWAdministracionHermod.MOSTRAR_CIRCULO_PASO_ESTACION_RECIBO)) {
            return mostrarCirculoEstacionRecibo(request);
        } else if (accion.equals(AWAdministracionHermod.SET_ESTACION_RECIBO)) {
            return setEstacionRecibo(request);
        } else if (accion.equals(AWAdministracionHermod.SET_AGREGAR_ESTACION_RECIBO)) {
            EvnAdministracionHermod evn = setEstacionRecibo(request);
            evn.setTipoEvento(EvnAdministracionHermod.SET_AGREGAR_ESTACION_RECIBO);
            return evn;
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_ESTACION_RECIBO)) {
            return eliminaEstacionRecibo(request);
        } else if (accion.equals(AWAdministracionHermod.TRASPASO_ESTACION_RECIBO)) {
            return traspasoEstacionRecibo(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_ESTACIONES_CIRCULO)) {
            return consultaEstacionesCirculo(request);
        } else if (accion.equals(AWAdministracionHermod.ADMIN_UNA_ESTACION)) {
            return adminUnaEstacion(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO)
                || accion.equals(AWAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO_TRASPASO)) {
            return consultaEstacionesReciboPorCirculo(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_ESTACIONES_POR_ID)) {
            return consultaEstacion(request);
        } else if (accion.equals(AWAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO)) {
            return resetUltimoValorEstacionRecibo(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_ESTACION_RECIBO)) {
            return consultaEstacionRecibo(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_MINUTA)) {
            return consultarMinuta(request);
        } else if (accion.equals(AWAdministracionHermod.EDITA_MINUTA)) {
            return actualizarMinuta(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO)) {
            return adicionaCirculoTipoPago(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_CIRCULO_TIPO_PAGO)) {
            return eliminaCirculoTipoPago(request);
        } else if (accion.equals(AWAdministracionHermod.SELECCIONA_CIRCULO_TIPO_PAGO)) {
            return seleccionaCirculoTipoPago(request);
        } else if (accion.equals(AWAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR)) {
            return activaFirmaRegistrador(request);
        } else if (accion.equals(AWAdministracionHermod.INACTIVA_FIRMA_REGISTRADOR)) {
            return inactivaFirmaRegistrador(request);
        } else if (accion.equals(AWAdministracionHermod.INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR)) {
            return inactivaDefinitivamenteFirmaRegistrador(request);
        } else if (accion.equals(AWAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR)) {
            return seleccionaCirculoFirmaRegistrador(request);
        } else if (accion.equals(AWAdministracionHermod.BUSCAR_FIRMA_REGISTRADOR_CIRCULO)) {
            return buscarFirmasRegistradorPorCirculo(request);
        } else if (accion.equals(AWAdministracionHermod.PREVIEW_FIRMA_REGISTRADOR)) {
            return previewFirmaRegistrador(request);
        } else if (accion.equals(AWAdministracionHermod.GUARDAR_FIRMA_REGISTRADOR)) {
            return guardarFirmaRegistrador(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO)) {
            return consultaAbogadosCirculo(request);
        } else if (accion.equals(AWAdministracionHermod.ACTUALIZA_SUBTIPOS_ATENCION_ABOGADOS)) {
            return actualizaAbogadosSubtipoAtencion(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_VALIDACION_NOTA)) {
            return adicionaValidacionNota(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_VALIDACION_NOTA)) {
            return eliminaValidacionNota(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_FASE_PROCESO)) {
            return consultaFaseProceso(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_CAUSAL_RESTITUCION)) {
            return adicionaCausalRestitucion(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_CAUSAL_RESTITUCION)) {
            return eliminaCausalRestitucion(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_CERTIFICADO_VALIDACION)) {
            return adicionaTipoCertificadoValidacion(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_TIPO_CERTIFICADO_VALIDACION)) {
            return eliminaTipoCertificadoValidacion(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_VALIDACIONES_DE_TIPO_CERTIFICADO)) {
            return consultaValidacionesDeTipoCertificado(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_NOTA)
                || (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_NOTA_INF))
                || (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_NOTA_DEV))) {
            return adicionaTipoNota(request);
        } else if (accion.equals(AWAdministracionHermod.MODIFICA_TIPO_NOTA)
                || accion.equals(AWAdministracionHermod.MODIFICA_TIPO_NOTA_INF)
                || accion.equals(AWAdministracionHermod.MODIFICA_TIPO_NOTA_DEV)) {
            return modificaTipoNota(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_TIPO_NOTA)
                || accion.equals(AWAdministracionHermod.ELIMINA_TIPO_NOTA_INF)
                || accion.equals(AWAdministracionHermod.ELIMINA_TIPO_NOTA_DEV)) {
            return eliminaTipoNota(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_TIPO_NOTA_POR_PROCESO)) {
            return consultaTipoNotaPorProceso(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_TIPO_NOTA_INF_POR_PROCESO)) {
            return consultaTipoNotaInfPorProceso(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_TIPO_NOTA_DEV_POR_PROCESO)) {
            return consultaTipoNotaDevPorProceso(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_CHECK_ITEM)) {
            return adicionaCheckItem(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_CHECK_ITEM)) {
            return eliminaCheckItem(request);
        } else if (accion.equals(AWAdministracionHermod.EDITAR_CHECK_ITEM)) {
            return editarCheckItem(request);
        } else if (accion.equals(AWAdministracionHermod.EDITA_CHECK_ITEM)) {
            return editaCheckItem(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD)) {
            return consultaCheckItem(request);
        } else if (accion.equals(AWAdministracionHermod.ENTREGA_MASIVA_CERTIFICADOS)) {
            return entregaMasivaCertificados(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_TURNOS_CER_ENTREGA_POR_FECHA)) {
            return consultaTurnosCerEntregaPorFecha(request);
        } else if (accion.equals(AWAdministracionHermod.ENTREGA_MASIVA_CONSULTAS)) {
            return entregaMasivaConsultas(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_TURNOS_CON_ENTREGA_POR_FECHA)) {
            return consultaTurnosConEntregaPorFecha(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTAR_PROB_REV_CALIFICACION)) {
            return consultaProbRevisionCalificacion(request);
        } else if (accion.equals(AWAdministracionHermod.MODIFICAR_PROB_REV_CALIFICACION)) {
            return modificarProbRevisionCalificacion(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS)) {
            return consultarSolicitudesNoPagadas(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS)) {
            return eliminarSolicitudesNoPagadas(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINAR_TODAS_LAS_SOLICITUDES_NO_PAGADAS)) {
            return eliminarTodasLasSolicitudesNoPagadas(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_TURNOS_CALIFICACION)) {
            return consultarTurnosCalificacion(request);
        } else if (accion.equals(AWAdministracionHermod.SOLICITAR_CORRECCION_CALIFICACION)) {
            return solicitarCorreccionCalificacion(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_TIPOS_TARIFA_POR_CIRCULO)) {
            return consultaTiposTarifaPorCirculo(request);
        } else if (accion.equals(AWAdministracionHermod.COLOCAR_DESCRIPCION_NOTA_INF)) {
            return buscarDescripcion(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TARIFA_POR_CIRCULO)) {
            return adicionaTarifaPorCirculo(request);
        } else if (accion.equals(AWAdministracionHermod.ADD_ROLES_ESTACION)) {
            return addRolesEstacion(request);
        } else if (accion.equals(AWAdministracionHermod.REMOVE_ROLES_ESTACION)) {
            return removeRolesEstacion(request);
        } else if (accion.equals(AWAdministracionHermod.ADD_USUARIOS_ESTACION)) {
            return addUsuariosEstacion(request);
        } else if (accion.equals(AWAdministracionHermod.REMOVE_USUARIOS_ESTACION)) {
            return removeUsuariosEstacion(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_NAT_JURIDICA_ENTIDAD)) {
            return adicionarNatJuridicaEntidad(request);
        } else if (accion.equals(AWAdministracionHermod.EDITAR_NAT_JURIDICA_ENTIDAD)) {
            return editarNatJuridicaEntidad(request);
        } else if (accion.equals(AWAdministracionHermod.HABILITA_EDICION_NAT_JURIDICA_ENTIDAD)) {
            return habilitarEdicionNatEntidadPublica(request);
        } else if (accion.equals(AWAdministracionHermod.DESHABILITA_EDICION_NAT_JURIDICA_ENTIDAD)) {
            return desHabilitarEdicionNatEntidadPublica(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_NAT_JURIDICA_ENTIDAD)) {
            return eliminarNatJuridicaEntidad(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA)) {
            return adicionarEntidadPublica(request);
        } else if (accion.equals(AWAdministracionHermod.HABILITA_EDICION_ENTIDAD_PUBLICA)) {
            return habilitarEdicionEntidadPublica(request);
        } else if (accion.equals(AWAdministracionHermod.DESHABILITA_EDICION_ENTIDAD_PUBLICA)) {
            return desHabilitarEdicionEntidadPublica(request);
        } else if (accion.equals(AWAdministracionHermod.EDITA_ENTIDAD_PUBLICA)) {
            return editarEntidadPublica(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA)) {
            return consultarEntidadPublicaByNaturaleza(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_ENTIDAD_PUBLICA)) {
            return eliminarEntidadPublica(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO)) {
            return consultarNatJuridicas(request);
        } else if (accion.equals(AWAdministracionHermod.IMPRESION_FORMULARIOS_CALIFICACION)) {
            return impresionFormularioCalificacion(request);
        } else if (accion.equals(AWAdministracionHermod.IMPRESION_FORMULARIOS_CORRECCIONES)) {
            return impresionFormularioCorrecciones(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO)) {
            return consultarNatJuridicas(request);
        } else if (accion.equals(AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO_ENTIDAD)) {
            return consultarNatJuridicas(request);
        } else if (accion.equals(AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_CALIFICACION)) {
            return obtnerImpresoras(request);
        } else if (accion.equals(AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_CORRECCIONES)) {
            return obtnerImpresoras(request);
        } else if (accion.equals(AWAdministracionHermod.OBTENER_SOLICITUDES_PAGINA)) {
            return obtenerSolicitudesPagina(request);
        } else if (accion.equals(AWAdministracionHermod.OBTENER_VENCIDOS_PAGO_MAYOR_VALOR)) {
            return obtenerVencidosMayorValor(request);
        } else if (accion.equals(AWAdministracionHermod.DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR)) {
            return devolverVencidosMayorValor(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_VALIDACION)) {
            return adicionarTipoValidacion(request);
        } else if (accion.equals(AWAdministracionHermod.OBTENER_PENDIENTES_PAGO_MAYOR_VALOR)) {
            return obtenerPendientesPagoMayorValor(request);
        } else if (accion.equals(AWAdministracionHermod.EDITAR_CAUSAL_RESTITUCION)) {
            return editarCausalRestitucion(request);
            
        } else if (accion.equals(AWAdministracionHermod.EDITAR_DETALLES_CAUSAL_RESTITUCION)) {
            
            return editarDetallesCausalRestitucion(request);
        } else if (accion.equals(AWAdministracionHermod.VOLVER_SELECCIONAR_CAUSAL)) {
            return null;
        } else if (accion.equals(AWAdministracionHermod.ELIMINAR_PENDIENTES_PAGO_MAYOR_VALOR)) {
            return eliminarPendientesPagoMayorValor(request);
        } else if (accion.equals(AWAdministracionHermod.COLOCAR_DESCRIPCION_NOTA_INF_PMY)) {
            return buscarDescripcionMayorValorPendiente(request);
        } else if (accion.equals(AWAdministracionHermod.CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION)) {
            return cargarCalificadoresCirculo(request);
        } else if (accion.equals(AWAdministracionHermod.CARGAR_LISTADO_CALIFICADORES_SUBTIPOATENCION)) {
            return cargarCalificadoresSubtipoAtencion(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_ORDEN_SUBTIPOATENCION)) {
            return adicionaOrdenSubtipoAtencion(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_ORDEN_SUBTIPOATENCION)) {
            return removeOrdenSubtipoAtencion(request);
        } else if (accion.equals(AWAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES)) {
            return cargarCirculosNotariales(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_CIRCULO_NOTARIAL)) {
            return agregarCirculoNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_CIRCULO_NOTARIAL)) {
            return eliminarCirculoNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.EDITAR_CIRCULO_NOTARIAL)) {
            return editarCirculoNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.EJECUTAR_EDICION_CIRCULO_NOTARIAL)) {
            return ejecutarEdicionCirculoNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.TERMINA_EDICION)) {
            request.getSession().removeAttribute(AWAdministracionHermod.CIRCULO_NOTARIAL_EDITADO);
            request.getSession().removeAttribute(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
            request.getSession().removeAttribute(CCirculoNotarial.NOMBRE_CIRCULO_NOTARIAL);
            return null;
        } else if (accion.equals(AWAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES_ZONA_NOTARIAL)) {
            return cargarCirculosNotariales(request);
        } else if (accion.equals(AWAdministracionHermod.SELECCIONA_ZONA_NOTARIAL_DEPTO)) {
            return seleccionaZonaNotarialDepto(request);
        } else if (accion.equals(AWAdministracionHermod.SELECCIONA_ZONA_NOTARIAL_MUNICIPIO)) {
            return seleccionaZonaNotarialMunicipio(request);
        } else if (accion.equals(AWAdministracionHermod.CARGAR_ZONAS_NOTARIALES)) {
            return cargarZonasNotariales(request);
        } else if (accion.equals(AWAdministracionHermod.ADICIONA_ZONA_NOTARIAL)) {
            return adicionaZonaNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINA_ZONA_NOTARIAL)) {
            return eliminaZonaNotarial(request);
        } else if (accion.equals(AWAdministracionHermod.CARGAR_ROLES_PANTALLAS)) {
            return cargarRolesPantallas(request);
        } else if (accion.equals(AWAdministracionHermod.AGREGAR_ROL_PANTALLA)) {
            return agregarRolPantalla(request);
        } else if (accion.equals(AWAdministracionHermod.ELIMINAR_ROL_PANTALLA)) {
            return eliminarRolPantalla(request);
        } else if (accion.equals(AWAdministracionHermod.IMPRESION_FORMULARIOS_TESTAMENTOS)) {
            return impresionFormularioTestamentos(request);
        } else if (accion.equals(AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_TESTAMENTOS)) {
            return obtnerImpresoras(request);
        } else if (accion.equals(ADICIONA_BANCO_CIRCULO)) {
            return adicionaBancoCirculo(request);
        } else if (accion.equals(SELECCIONA_BANCOS_X_CIRCULO) || accion.equals(LISTADO_BANCOS_X_CIRCULO)) {
            return selecionaBancosXCirculo(request);
        } else if (accion.equals(ELIMINA_BANCO_CIRCULO)) {
            return eliminaBancoCirculo(request);
        } else if (accion.equals(ACTIVAR_BANCO_PRINCIPAL)) {
            return activarBancoPrincipal(request);
        } else if (accion.equals(ADICIONA_CUENTA_BANCO_CIRCULO)) {
            return adicionaCuentaBancoCirculo(request);
        } else if (accion.equals(CUENTAS_X_CIRCULO_BANCO)) {
            return cuentasXCirculoBanco(request);
        } else if (accion.equals(ACT_INACT_CTA_BANCARIA)) {
            return activaInactivaCtaBancaria(request);
        } else if (accion.equals(ADICIONA_CANAL_RECAUDO)) {
            return adicionaCanalRecaudo(request);
        } else if (accion.equals(ACT_INACT_CANAL_RECAUDO)) {
            return activaInactivaCanalRecaudo(request);
        } else if (accion.equals(SELECCIONA_CUENTA_BANCARIA)) {
            return seleccionaCuentasBancarias(request);
        } else if (accion.equals(ACT_INACT_CTP)) {
            return activaInactivaCtp(request);
        }
        
        return null;
    }
    
    private Evento eliminarRolPantalla(HttpServletRequest request) throws AccionWebException {
        
        String rolAEliminar = request.getParameter(AWAdministracionHermod.ROL_PANTALLA_A_ELIMINAR);
        
        RolPantallaPk rolPantallaID = new RolPantallaPk(rolAEliminar);
        
        RolPantalla rolPantalla = new RolPantalla();
        rolPantalla.setIdPantallaAdministrativa(rolPantallaID.idPantallaAdministrativa);
        rolPantalla.setIdRol(rolPantallaID.idRol);
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.ELIMINAR_ROL_PANTALLA_ADMINISTRATIVA);
        evento.setRolPantallaAEliminar(rolPantalla);
        
        return evento;
    }
    
    private Evento agregarRolPantalla(HttpServletRequest request) throws AccionWebException {
        
        String rolAAgregar = request.getParameter(AWAdministracionHermod.ROL_ASIGNAR);
        String pantallaAAgregar = request.getParameter(AWAdministracionHermod.PANTALLA_ASIGNAR);
        
        String rolPantallaAAgregar = pantallaAAgregar + "-" + rolAAgregar;
        
        RolPantalla rolPantalla = null;
        
        RolPantallaPk rolPantallaID = new RolPantallaPk(rolPantallaAAgregar);
        
        rolPantalla = new RolPantalla();
        rolPantalla.setIdPantallaAdministrativa(rolPantallaID.idPantallaAdministrativa);
        rolPantalla.setIdRol(rolPantallaID.idRol);
        
        request.getSession().setAttribute(AWAdministracionHermod.ROL_ASIGNAR, rolAAgregar);
        request.getSession().setAttribute(AWAdministracionHermod.PANTALLA_ASIGNAR, pantallaAAgregar);
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.AGREGAR_ROL_PANTALLA_ADMINISTRATIVA);
        evento.setRolPantallaAAgregar(rolPantalla);
        
        return evento;
    }
    
    private Evento cargarRolesPantallas(HttpServletRequest request) {
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.CARGAR_ROLES_PANTALLAS_ADMINISTRATIVAS);
        
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminaZonaNotarial(HttpServletRequest request) throws AccionWebException {
        String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        ValidacionZonaNotarialException exception = new ValidacionZonaNotarialException();
        if ((idDepto == null)
                || (idDepto.trim().length() == 0)
                || idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un departamento.");
        }
        String idMunic = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunic == null)
                || (idMunic.trim().length() == 0)
                || idMunic.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un municipio.");
        }
        String idVereda = request.getParameter(CVereda.ID_VEREDA);
        if ((idVereda == null)
                || (idVereda.trim().length() == 0)
                || idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar una veredas.");
        }
        
        String idNotarial = request.getParameter(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
        if ((idNotarial == null)
                || (idNotarial.trim().length() == 0)
                || idNotarial.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un círculo notarial.");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        ZonaNotarial zona = new ZonaNotarial();
        
        zona.setIdDepartamento(idDepto);
        zona.setIdMunicipio(idMunic);
        zona.setIdVereda(idVereda);
        zona.setIdCirculoNotarial(idNotarial);
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.ELIMINAR_ZONA_NOTARIAL);
        evento.setZonaNotarial(zona);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento cargarZonasNotariales(HttpServletRequest request) throws AccionWebException {
        ValidacionZonaNotarialException exception = new ValidacionZonaNotarialException();
        String idNotaria = request.getParameter(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
        
        if ((idNotaria == null)
                || (idNotaria.trim().length() == 0)
                || idNotaria.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un círculo notarial.");
        } else {
            request.getSession().setAttribute(CCirculoNotarial.ID_CIRCULO_NOTARIAL, idNotaria);
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        if ((request.getParameter(CVereda.ID_VEREDA) != null) && (!request.getParameter(CVereda.ID_VEREDA).equals(WebKeys.SIN_SELECCIONAR))) {
            request.getSession().setAttribute(CVereda.ID_VEREDA, request.getParameter(CVereda.ID_VEREDA));
        }
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CARGAR_ZONAS_NOTARIALES);
        CirculoNotarial circulo = new CirculoNotarial();
        circulo.setIdCirculo(idNotaria);
        evento.setCirculoNotarial(circulo);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento seleccionaZonaNotarialMunicipio(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        //ServletContext context = session.getServletContext();

        ValidacionZonaNotarialException exception = new ValidacionZonaNotarialException();
        
        String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunicipio == null)
                || (idMunicipio.trim().length() == 0)
                || idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Municipio.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        seleccionaMunicipio(request);
        
        Municipio municipio
                = (Municipio) session.getAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);
        List veredas = municipio.getVeredas();
        List listaVeredas = new ArrayList();
        for (Iterator itMun = veredas.iterator(); itMun.hasNext();) {
            Vereda vereda = (Vereda) itMun.next();
            listaVeredas.add(new ElementoLista(vereda.getIdVereda(), vereda.getNombre()));
        }
        
        session.setAttribute(AWAdministracionForseti.LISTA_VEREDAS, listaVeredas);
        session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
        session.removeAttribute(CVereda.ID_VEREDA);
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para la selección
     * de un Municipio
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code>
     * @throws AccionWebException
     */
    private Evento seleccionaMunicipio(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        String key = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((key == null) || (key.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe seleccionar un municipio.");
        }
        
        Map municipios = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
        Municipio municipio = (Municipio) municipios.get(key);
        
        if (municipio == null) {
            throw new AccionInvalidaException("Debe seleccionar un municipio.");
        }
        
        seleccionarMunicipioEnSesion(municipio, session);
        return null;
    }

    /**
     * Este método se encarga de seleccionar un municipio en la
     * <code>HttpSession</code>
     *
     * @param municipio
     * @param session
     * @throws AccionWebException
     */
    private void seleccionarMunicipioEnSesion(Municipio municipio, HttpSession session) {
        List veredas = municipio.getVeredas();
        //		  Organizar alfabeticamente
        Map mapVeredas = Collections.synchronizedMap(new TreeMap());
        for (Iterator iter = veredas.iterator(); iter.hasNext();) {
            Vereda vereda = (Vereda) iter.next();
            String llave = vereda.getNombre() + "-" + vereda.getIdVereda();
            mapVeredas.put(llave, vereda);
        }
        session.setAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO, municipio);
        session.setAttribute(AWAdministracionForseti.MAP_VEREDAS, mapVeredas);
    }

    /**
     * @param request
     * @return
     */
    private Evento seleccionaZonaNotarialDepto(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        //ServletContext context = session.getServletContext();

        ValidacionZonaNotarialException exception = new ValidacionZonaNotarialException();
        
        String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        if ((idDepto == null)
                || (idDepto.trim().length() == 0)
                || idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Departamento.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Map deptos = (Map) request.getSession().getServletContext().getAttribute(WebKeys.MAPA_DEPARTAMENTOS);
        
        Departamento depto = (Departamento) deptos.get(idDepto);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionForseti evento = new EvnAdministracionForseti(usuario, EvnAdministracionForseti.CONSULTA_DEPARTAMENTO);
        evento.setDepartamento(depto);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento ejecutarEdicionCirculoNotarial(HttpServletRequest request) throws AccionWebException {
        AgregarCirculoNotarialException exception = new AgregarCirculoNotarialException();
        String nombre = request.getParameter(CCirculoNotarial.NOMBRE_CIRCULO_NOTARIAL);
        if (nombre == null || nombre.trim().equals("")) {
            exception.addError("Debe ingresar el nombre del círculo notarial");
        }
        
        String id = request.getParameter(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
        if (id == null || id.trim().equals("")) {
            exception.addError("Debe ingresar el identificador del círculo notarial");
        }
        
        String idcirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idcirculo == null)
                || (idcirculo.trim().length() == 0)
                || idcirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un círculo.");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.EDITAR_CIRCULO_NOTARIAL);
        
        CirculoNotarial circuloNot = new CirculoNotarial();
        circuloNot.setNombre(nombre);
        circuloNot.setIdCirculo(id);
        Circulo circuloReg = new Circulo();
        circuloReg.setIdCirculo(idcirculo);
        circuloNot.setCirculoRegistral(circuloReg);
        evento.setCirculoNotarial(circuloNot);
        
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento editarCirculoNotarial(HttpServletRequest request) throws AccionWebException {
        String idCirculoNot = request.getParameter(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
        Iterator itLista = ((List) request.getSession().getAttribute(AWAdministracionHermod.LISTA_CIRCULOS_NOTARIALES)).iterator();
        CirculoNotarial circulo = null;
        while (itLista.hasNext()) {
            CirculoNotarial temp = (CirculoNotarial) itLista.next();
            if (temp.getIdCirculo().equals(idCirculoNot)) {
                circulo = temp;
                break;
            }
        }
        
        request.getSession().setAttribute(AWAdministracionHermod.CIRCULO_NOTARIAL_EDITADO, circulo);
        
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarCirculoNotarial(HttpServletRequest request) throws AccionWebException {
        String idCirculoNot = request.getParameter(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
        CirculoNotarial circulo = new CirculoNotarial();
        circulo.setIdCirculo(idCirculoNot);
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.ELIMINAR_CIRCULO_NOTARIAL);
        evento.setCirculoNotarial(circulo);
        String idCirculo = (String) request.getSession().getAttribute(CCirculo.ID_CIRCULO);
        Circulo circReg = new Circulo();
        circReg.setIdCirculo(idCirculo);
        evento.setCirculo(circReg);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento agregarCirculoNotarial(HttpServletRequest request) throws AccionWebException {
        AgregarCirculoNotarialException exception = new AgregarCirculoNotarialException();
        String nombre = request.getParameter(CCirculoNotarial.NOMBRE_CIRCULO_NOTARIAL);
        if (nombre == null || nombre.trim().equals("")) {
            exception.addError("Debe ingresar el nombre del círculo notarial");
        }
        
        String id = request.getParameter(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
        if (id == null || id.trim().equals("")) {
            exception.addError("Debe ingresar el identificador del círculo notarial");
        }
        
        String idcirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idcirculo == null)
                || (idcirculo.trim().length() == 0)
                || idcirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un círculo.");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.AGREGAR_CIRCULO_NOTARIAL);
        
        CirculoNotarial circuloNot = new CirculoNotarial();
        circuloNot.setNombre(nombre);
        circuloNot.setIdCirculo(id);
        Circulo circuloReg = new Circulo();
        circuloReg.setIdCirculo(idcirculo);
        circuloNot.setCirculoRegistral(circuloReg);
        evento.setCirculoNotarial(circuloNot);
        
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento cargarCirculosNotariales(HttpServletRequest request) throws AccionWebException {
        AgregarCirculoNotarialException exception = new AgregarCirculoNotarialException();
        String idcirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idcirculo == null)
                || (idcirculo.trim().length() == 0)
                || idcirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un círculo.");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        request.getSession().setAttribute(CCirculo.ID_CIRCULO, idcirculo);
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idcirculo);
        evento.setCirculo(circulo);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento adicionaZonaNotarial(HttpServletRequest request) throws AccionWebException {
        String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
        ValidacionZonaNotarialException exception = new ValidacionZonaNotarialException();
        if ((idDepto == null)
                || (idDepto.trim().length() == 0)
                || idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un departamento.");
        }
        String idMunic = request.getParameter(CMunicipio.ID_MUNICIPIO);
        if ((idMunic == null)
                || (idMunic.trim().length() == 0)
                || idMunic.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un municipio.");
        }
        String idVereda = request.getParameter(CVereda.ID_VEREDA);
        if ((idVereda == null)
                || (idVereda.trim().length() == 0)
                || idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar una veredas.");
        }
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un círculo.");
        }
        
        String idNotarial = request.getParameter(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
        if ((idNotarial == null)
                || (idNotarial.trim().length() == 0)
                || idNotarial.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un círculo notarial.");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        ZonaNotarial zona = new ZonaNotarial();
        
        Departamento depto = (Departamento) request.getSession().getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
        Municipio munic = (Municipio) request.getSession().getAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);
        if (depto != null) {
            zona.setIdDepartamento(depto.getIdDepartamento());
        }
        if (munic != null) {
            zona.setIdMunicipio(munic.getIdMunicipio());
        }
        zona.setIdVereda(idVereda);
        zona.setIdCirculoNotarial(idNotarial);
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.AGREGAR_ZONA_NOTARIAL);
        evento.setZonaNotarial(zona);
        return evento;
    }
    
    private Evento eliminarPendientesPagoMayorValor(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        int numProceso = Integer.parseInt(CProceso.PROCESO_PAGO_MAYOR_VALOR);

        /*obtener valores del session*/
        //turno
        String turno = "";
        turno = request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR);
        session.setAttribute(WebKeys.TITULO_CHECKBOX_ELIMINAR, turno);

        //nota informativa
        ValidacionParametrosException exception = new ValidacionParametrosException();
        String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        //setear el proceso manualmente.
        Proceso proceso = new Proceso();
        proceso.setIdProceso(numProceso);
        
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        String descripcion = request.getParameter(CNota.DESCRIPCION);
        session.setAttribute(CNota.DESCRIPCION, descripcion);
        
        if (turno == null || turno.equals("")) {
            exception.addError("Debe escoger un turno");
        }
        
        if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo de nota válido");
        }
        
        if (descripcion == null || descripcion.length() < 15) {
            exception.addError("La longitud de la descripción no puede ser inferior a 15 caracteres");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Nota notaInformativa = new Nota();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        notaInformativa.setIdNota(idTipoNota);
        notaInformativa.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
        notaInformativa.setIdCirculo(circulo.getIdCirculo());
        notaInformativa.setIdProceso(proceso.getIdProceso());
        notaInformativa.setIdTurno(null);
        notaInformativa.setTime(calendar.getTime());
        notaInformativa.setUsuario(usuario);
        
        notaInformativa.setDescripcion(descripcion);
        String descripcionTipo = request.getParameter((CTipoNota.DESCRIPCION));
        String nombreTipo = (String) request.getSession().getAttribute(CNota.NOMBRE);
        
        TipoNota tipoNota = new TipoNota();
        tipoNota.setDescripcion(descripcionTipo);
        tipoNota.setIdTipoNota(idTipoNota);
        tipoNota.setNombre(nombreTipo);
        notaInformativa.setTiponota(tipoNota);
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg
                = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Circulo cir = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion,
                EvnAdministracionHermod.ELIMINAR_TURNOS_PAGO_MAYOR_VALOR_PENDIENTES);
        evento.setNotaInformativaCorreccion(notaInformativa);
        evento.setCirculo(cir);
        evento.setUsuarioSIR(usuarioNeg);
        evento.setIdTurno(turno);
        return evento;
    }
    
    private Evento obtenerPendientesPagoMayorValor(HttpServletRequest request) {
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo cir = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.OBTENER_PENDIENTES_PAGO_MAYOR_VALOR);
        evento.setCirculo(cir);
        return evento;
    }
    
    private Evento adicionarTipoValidacion(HttpServletRequest request) throws AccionWebException {
        String nombre = request.getParameter(CValidacion.VALIDACION_NOMBRE);
        String id = request.getParameter(CValidacion.VALIDACION_ID);
        
        ValidacionParametrosValidacion exception = new ValidacionParametrosValidacion();
        if (nombre == null || nombre.trim().equals("")) {
            exception.addError("Debe ingresar el nombre de la validación");
        }
        
        if (id == null || id.trim().equals("")) {
            exception.addError("Debe ingresar el identificador de la validación");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        Validacion validacion = new Validacion();
        validacion.setIdValidacion(id);
        validacion.setNombre(nombre);
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_TIPO_VALIDACION);
        evento.setValidacion(validacion);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento obtenerSolicitudesPagina(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento impresionFormularioCalificacion(HttpServletRequest request) throws ValidacionReimpresionCalificacion {
        HttpSession session = request.getSession();
        String idTurno = request.getParameter(CTurno.ID_TURNO);
        String numCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
        
        ValidacionReimpresionCalificacion exception = new ValidacionReimpresionCalificacion();
        
        if (idTurno.length() <= 0) {
            exception.addError("El identificador del turno de calificación no es valido");
        }
        
        if (numCopias.length() <= 0) {
            exception.addError("Debe ingresar el número de copias que debe imprimir");
        } else {
            try {
                int i = new Integer(numCopias).intValue();
                if (i < 1) {
                    exception.addError("El número de copias a imprimir debe ser mayor a cero");
                }
            } catch (Exception e) {
                exception.addError("El número de copias a imprimir debe ser un valor numérico");
            }
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        String impresora = request.getParameter(WebKeys.IMPRESORA);
        
        idTurno = idTurno.toUpperCase();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.REIMPRIMIR_FORMULARIO_CALIFICACION);
        evento.setIdTurnoCalificacion(idTurno);
        String UID = request.getSession().getId();
        evento.setUID(UID);
        evento.setNumeroImpresiones(new Integer(numCopias).intValue());
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        evento.setUsuarioNeg(usuarioNeg);
        if (impresora != null && !impresora.equals(WebKeys.SIN_SELECCIONAR)) {
            evento.setImpresoraSeleccionada(impresora);
        }
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento impresionFormularioCorrecciones(HttpServletRequest request) throws ValidacionReimpresionCalificacion {
        HttpSession session = request.getSession();
        String idTurno = request.getParameter(CTurno.ID_TURNO);
        String numCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
        
        ValidacionReimpresionCalificacion exception = new ValidacionReimpresionCalificacion();
        
        if (idTurno.length() <= 0) {
            exception.addError("El identificador del turno de correcciones no es valido");
        }
        
        if (numCopias.length() <= 0) {
            exception.addError("Debe ingresar el número de copias que debe imprimir");
        } else {
            try {
                int i = new Integer(numCopias).intValue();
                if (i < 1) {
                    exception.addError("El número de copias a imprimir debe ser mayor a cero");
                }
            } catch (Exception e) {
                exception.addError("El número de copias a imprimir debe ser un valor numérico");
            }
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        String impresora = request.getParameter(WebKeys.IMPRESORA);
        
        idTurno = idTurno.toUpperCase();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.REIMPRIMIR_FORMULARIO_CORRECCIONES);
        evento.setIdTurnoCalificacion(idTurno);
        String UID = request.getSession().getId();
        evento.setUID(UID);
        evento.setNumeroImpresiones(new Integer(numCopias).intValue());
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        evento.setUsuarioNeg(usuarioNeg);
        if (impresora != null && !impresora.equals(WebKeys.SIN_SELECCIONAR)) {
            evento.setImpresoraSeleccionada(impresora);
        }
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento consultarNatJuridicas(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CARGAR_NAT_JURIDICAS);
        return evento;
    }
    
    private Evento removeRolesEstacion(HttpServletRequest request) {

        //OBTENER SESSION
        HttpSession session = request.getSession();

        //LEER PARAMETROS
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String idEstacion = (String) session.getAttribute(CEstacion.ESTACION_ID);
        String[] newRoles = request.getParameterValues(WebKeys.EX_ROLES_ESTACION);

        //ARMAR OBJETO ESTACION
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);

        //ARMAR LISTA DE NUEVOS ROLES
        List roles = new Vector();
        for (int i = 0; i < newRoles.length; i++) {
            roles.add(newRoles[i]);
        }

        //ARMAR EVENTO
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario,
                EvnAdministracionHermod.REMOVE_ROLES_ESTACION);
        evento.setEstacion(estacion);
        evento.setRoles(roles);
        return evento;
        
    }
    
    private Evento addRolesEstacion(HttpServletRequest request) {

        //OBTENER SESSION
        HttpSession session = request.getSession();

        //LEER PARAMETROS
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String idEstacion = (String) session.getAttribute(CEstacion.ESTACION_ID);
        String[] newRoles = request.getParameterValues(WebKeys.NEW_ROLES_ESTACION);

        //ARMAR OBJETO ESTACION
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);

        //ARMAR LISTA DE NUEVOS ROLES
        List roles = new Vector();
        for (int i = 0; i < newRoles.length; i++) {
            roles.add(newRoles[i]);
        }

        //ARMAR EVENTO
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario,
                EvnAdministracionHermod.ADD_ROLES_ESTACION);
        evento.setEstacion(estacion);
        evento.setRoles(roles);
        return evento;
        
    }
    
    private Evento removeUsuariosEstacion(HttpServletRequest request) {

        //OBTENER SESSION
        HttpSession session = request.getSession();

        //LEER PARAMETROS
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String idEstacion = (String) session.getAttribute(CEstacion.ESTACION_ID);
        String[] newUsuarios = request.getParameterValues(WebKeys.EX_USUARIOS_ESTACION);

        //ARMAR OBJETO ESTACION
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);

        //ARMAR LISTA DE NUEVOS ROLES
        List usuarios = new Vector();
        for (int i = 0; i < newUsuarios.length; i++) {
            usuarios.add(newUsuarios[i]);
        }

        //ARMAR EVENTO
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario,
                EvnAdministracionHermod.REMOVE_USUARIOS_ESTACION);
        evento.setEstacion(estacion);
        evento.setUsuarios(usuarios);
        return evento;
        
    }
    
    private Evento addUsuariosEstacion(HttpServletRequest request) {

        //OBTENER SESSION
        HttpSession session = request.getSession();

        //LEER PARAMETROS
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String idEstacion = (String) session.getAttribute(CEstacion.ESTACION_ID);
        String[] newUsuarios = request.getParameterValues(WebKeys.NEW_USUARIOS_ESTACION);

        //ARMAR OBJETO ESTACION
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);

        //ARMAR LISTA DE NUEVOS ROLES
        List usuarios = new Vector();
        for (int i = 0; i < newUsuarios.length; i++) {
            usuarios.add(newUsuarios[i]);
        }

        //ARMAR EVENTO
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario,
                EvnAdministracionHermod.ADD_USUARIOS_ESTACION);
        evento.setEstacion(estacion);
        evento.setUsuarios(usuarios);
        return evento;
        
    }
    
    private Evento adminUnaEstacion(HttpServletRequest request) {

        //OBTENER SESSION
        HttpSession session = request.getSession();

        //LEER PARAMETROS
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String idEstacion = request.getParameter(CEstacion.ESTACION_ID);

        //PONER EN SESSION
        session.setAttribute(CEstacion.ESTACION_ID, idEstacion);

        //ARMAR OBJETO ESTACION
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);

        //ARMAR EVENTO
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario,
                EvnAdministracionHermod.ADMIN_UNA_ESTACION);
        evento.setEstacion(estacion);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento consultaEstacionesCirculo(HttpServletRequest request)
            throws ValidacionParametrosEstacionCirculoException {

        //PREPARAR POSIBLE EXCEPCION
        ValidacionParametrosEstacionCirculoException exception
                = new ValidacionParametrosEstacionCirculoException();

        //LEER DATOS
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);

        //SI ERRORES DE VALIDACION DE PARAMETROS
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }

        //CREAR EVENTO
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        if (idCirculo == null || idCirculo.equals("")) {
            return null;
        }

        //SETEAR CIRCULO CONSULTADO
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTA_ESTACIONES_CIRCULO);
        evento.setCirculo(circulo);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de creación de un
     * banco
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * banco a crear
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaBanco(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        String id = request.getParameter(CBanco.ID_BANCO);
        if ((id == null) || (id.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para el Banco.");
        }
        
        String nombre = request.getParameter(CBanco.NOMBRE_BANCO);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un nombre de Banco.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Banco dato = new Banco();
        dato.setNombre(nombre);
        dato.setIdBanco(id);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_BANCO);
        evento.setBanco(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un banco
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * banco a eliminar
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaBanco(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String id = request.getParameter(CBanco.ID_BANCO);
        if ((id == null) || (id.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Banco.");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Banco dato = new Banco();
        dato.setIdBanco(id);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_BANCO);
        evento.setBanco(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de selección de
     * los datos de un banco en la sesión
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod seleccionaBanco(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String id = request.getParameter(CBanco.ID_BANCO);
        if ((id == null) || (id.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Banco.");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        List bancos = (List) context.getAttribute(WebKeys.LISTA_BANCOS);
        for (Iterator iter = bancos.iterator(); iter.hasNext();) {
            Banco banco = (Banco) iter.next();
            if (banco.getIdBanco().equals(id)) {
                session.setAttribute(AWAdministracionHermod.BANCO_SELECCIONADO, banco);
                EvnAdministracionHermod evento
                        = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.LISTAR_SUCURSALES);
                evento.setBanco(banco);
                return evento;
            }
        }
        throw new AccionInvalidaException("El Banco proporcionado no existe en el sistema.");
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de creación de una
     * sucursal
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * sucursal a crear
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaSucursal(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        String id = request.getParameter(CSucursalBanco.ID_SUCURSAL_BANCO);
        if ((id == null) || (id.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para la Sucursal.");
        }
        
        String nombre = request.getParameter(CSucursalBanco.NOMBRE_SUCURSAL_BANCO);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un nombre de Sucursal.");
        }
        
        Banco banco = (Banco) session.getAttribute(AWAdministracionHermod.BANCO_SELECCIONADO);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        SucursalBanco dato = new SucursalBanco();
        dato.setNombre(nombre);
        dato.setIdSucursal(id);
        dato.setIdBanco(banco.getIdBanco());
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_SUCURSAL_BANCO);
        evento.setBanco(banco);
        evento.setSucursalBanco(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * una sucursal
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * sucursal a eliminar
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaSucursal(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String id = request.getParameter(CSucursalBanco.ID_SUCURSAL_BANCO);
        if ((id == null) || (id.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para la Sucursal.");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Banco banco = (Banco) session.getAttribute(AWAdministracionHermod.BANCO_SELECCIONADO);
        
        SucursalBanco dato = new SucursalBanco();
        dato.setIdSucursal(id);
        dato.setIdBanco(banco.getIdBanco());
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_SUCURSAL_BANCO);
        evento.setBanco(banco);
        evento.setSucursalBanco(dato);
        return evento;
    }
    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * alcance geográfico
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * alcance geográfico
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaAlcanceGeografico(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        String id = request.getParameter(CAlcanceGeografico.ID_ALCANCE_GEOGRAFICO);
        if ((id == null) || (id.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para el alcance geográfico.");
        }
        
        String nombre = request.getParameter(CAlcanceGeografico.NOMBRE_ALCANCE_GEOGRAFICO);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un nombre de alcance geográfico.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        AlcanceGeografico dato = new AlcanceGeografico();
        dato.setIdAlcanceGeografico(id);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_ALCANCE_GEOGRAFICO);
        evento.setAlcanceGeografico(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un alcance geográfico
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * alcance geográfico
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaAlcanceGeografico(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String id = request.getParameter(CAlcanceGeografico.ID_ALCANCE_GEOGRAFICO);
        if ((id == null) || (id.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de alcance geográfico.");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        AlcanceGeografico dato = new AlcanceGeografico();
        dato.setIdAlcanceGeografico(id);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_ALCANCE_GEOGRAFICO);
        evento.setAlcanceGeografico(dato);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de una
     * tarifa
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * tarifa
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTarifa(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTarifa.NOMBRE_TARIFA);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre  para la tarifa.");
        }
        
        String valorStr = request.getParameter(CTarifa.VALOR_TARIFA);
        if ((valorStr == null) || (valorStr.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un valor para la tarifa.");
        }
        
        double valor = 0.0d;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            throw new AccionInvalidaException("No es un valor válido para la tarifa.");
        }
        
        TipoTarifa tipoTarifa
                = (TipoTarifa) session.getAttribute(AWAdministracionHermod.SELECCIONA_TIPO_TARIFA);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Tarifa dato = new Tarifa();
        dato.setIdCodigo(codigo);
        dato.setValor(valor);
        dato.setTipoTarifa(tipoTarifa);
        dato.setIdTipo(tipoTarifa.getIdTipo());
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_TARIFA);
        evento.setTarifa(dato);
        evento.setTipoTarifa(tipoTarifa);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * una tarifa
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * tarifa
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaTarifa(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTarifa.NOMBRE_TARIFA);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre  para la tarifa.");
        }
        
        String version = request.getParameter(CTarifa.VERSION_TARIFA);
        if ((version == null) || (version.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar una versión de la tarifa.");
        }
        
        long idVersion = 0;
        try {
            idVersion = Long.parseLong(version);
        } catch (NumberFormatException e) {
            throw new AccionInvalidaException("No es un valor válido para la versión de la tarifa.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoTarifa tipoTarifa
                = (TipoTarifa) session.getAttribute(AWAdministracionHermod.SELECCIONA_TIPO_TARIFA);
        
        Tarifa dato = new Tarifa();
        dato.setIdCodigo(codigo);
        dato.setIdTipo(tipoTarifa.getIdTipo());
        dato.setIdVersion(idVersion);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_TARIFA);
        evento.setTarifa(dato);
        evento.setTipoTarifa(tipoTarifa);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de selección de un
     * tipo de tarifa
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de tarifa
     * @throws AccionWebException
     */
    private EvnAdministracionHermod seleccionaTipoTarifa(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoTarifa.ID_TIPO_TARIFA);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre  para la tarifa.");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoTarifa dato = new TipoTarifa();
        dato.setIdTipo(codigo);
        session.setAttribute(AWAdministracionHermod.SELECCIONA_TIPO_TARIFA, dato);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.SELECCIONA_TIPO_TARIFA);
        evento.setTipoTarifa(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * tipo de tarifa
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de tarifa
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTipoTarifa(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoTarifa.ID_TIPO_TARIFA);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre  para la tarifa.");
        }
        
        String valorStr = request.getParameter(CTarifa.VALOR_TARIFA);
        if ((valorStr == null) || (valorStr.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un valor inicial para la tarifa.");
        }
        
        double valor = 0.0d;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            throw new AccionInvalidaException("No es un valor válido para la tarifa.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoTarifa tipoTarifa = new TipoTarifa();
        tipoTarifa.setIdTipo(codigo);
        
        Tarifa dato = new Tarifa();
        dato.setIdCodigo(codigo);
        dato.setValor(valor);
        dato.setTipoTarifa(tipoTarifa);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_TIPO_TARIFA);
        evento.setTarifa(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un tipo de tarifa
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de tarifa
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaTipoTarifa(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoTarifa.ID_TIPO_TARIFA);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre  para la tarifa.");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoTarifa dato = new TipoTarifa();
        dato.setIdTipo(codigo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_TIPO_TARIFA);
        evento.setTipoTarifa(dato);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * tipo de fotocopia
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de fotocopia
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTipoFotocopia(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoFotocopia.ID_TIPO_FOTOCOPIA);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un código para el tipo de fotocopia.");
        }
        
        String nombre = request.getParameter(CTipoFotocopia.NOMBRE_TIPO_FOTOCOPIA);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar Nombre para el tipo de fotocopia.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoFotocopia dato = new TipoFotocopia();
        dato.setIdTipoFotocopia(codigo);
        dato.setNombre(nombre);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_TIPO_FOTOCOPIA);
        evento.setTipoFotocopia(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un tipo de fotocopia
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de fotocopia
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaTipoFotocopia(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoFotocopia.ID_TIPO_FOTOCOPIA);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un código para el tipo de fotocopia.");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoFotocopia dato = new TipoFotocopia();
        dato.setIdTipoFotocopia(codigo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_TIPO_FOTOCOPIA);
        evento.setTipoFotocopia(dato);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * oplookup types
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * oplookup type
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaOPLookupType(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //borrar flag de carga
        session.removeAttribute("CARGAR_OPLOOKUP_TYPES");
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_OPLOOKUP_TYPE);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * oplookup type
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * oplookup type
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaOPLookupType(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(COPLookupTypes.NOMBRE_OPLOOKUP_TYPE);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Lookup Type.");
        }
        
        String descripcion = request.getParameter(COPLookupTypes.DESCRIPCION_OPLOOKUP_TYPE);
        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar una descripcion del Lookup Type.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        OPLookupTypes dato = new OPLookupTypes();
        dato.setTipo(codigo);
        dato.setDescripcion(descripcion);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_OPLOOKUP_TYPE);
        evento.setOpLookupType(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de edicion de un
     * oplookup type
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * oplookup type
     * @throws AccionWebException
     */
    private EvnAdministracionHermod updateOPLookupType(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = (String) session.getAttribute(COPLookupTypes.NOMBRE_OPLOOKUP_TYPE);
        
        String descripcion = request.getParameter(COPLookupTypes.DESCRIPCION_OPLOOKUP_TYPE);
        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar una descripcion del Lookup Type.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        String codigoAEditar = (String) session.getAttribute(COPLookupTypes.NOMBRE_OPLOOKUP_TYPE_EDITADO);
        
        OPLookupTypes dato = new OPLookupTypes();
        dato.setTipo(codigo);
        dato.setDescripcion(descripcion);
        
        OPLookupTypes datoAEditar = new OPLookupTypes();
        datoAEditar.setTipo(codigoAEditar);

        //cerrar ventana
        session.setAttribute("CERRAR_VENTANA", new Boolean(true));
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.UPDATE_OPLOOKUP_TYPE);
        evento.setOpLookupType(dato);
        evento.setOpLookupTypeAEditar(datoAEditar);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un oplookup type
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * oplookup type
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaOPLookupType(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(COPLookupTypes.NOMBRE_OPLOOKUP_TYPE);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Lookup Type.");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        OPLookupTypes dato = new OPLookupTypes();
        dato.setTipo(codigo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_OPLOOKUP_TYPE);
        evento.setOpLookupType(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de selección de un
     * oplookup type en la sesión
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * oplookup type
     * @throws AccionWebException
     */
    private EvnAdministracionHermod seleccionaOPLookupType(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(COPLookupTypes.NOMBRE_OPLOOKUP_TYPE);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Lookup Type.");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        List elementos = (List) context.getAttribute(WebKeys.LISTA_OPLOOKUP_TYPES);
        for (Iterator iter = elementos.iterator(); iter.hasNext();) {
            ElementoLista dato = (ElementoLista) iter.next();
            if (dato.getId().equals(codigo)) {
                EvnAdministracionHermod evento
                        = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.LISTAR_OPLOOKUP_CODES);
                
                OPLookupTypes opLookUpType = new OPLookupTypes();
                opLookUpType.setTipo(codigo);
                evento.setOpLookupType(opLookUpType);
                session.setAttribute(AWAdministracionHermod.OPLOOKUP_TYPE_SELECCIONADO, opLookUpType);
                return evento;
            }
        }
        throw new AccionInvalidaException("El Lookup Type proporcionado no existe en el sistema.");
    }
    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * oplookup code
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * oplookup code
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaOPLookupCode(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(COPLookupCodes.NOMBRE_OPLOOKUP_CODE);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Lookup Code.");
        }
        
        String descripcion = request.getParameter(COPLookupCodes.DESCRIPCION_OPLOOKUP_CODE);
        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar una descripcion del Lookup Code.");
        }
        
        String valor = request.getParameter(COPLookupCodes.VALOR_OPLOOKUP_CODE);
        if ((valor == null) || (valor.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un valor de Lookup Code.");
        }
        
        OPLookupTypes tipo
                = (OPLookupTypes) session.getAttribute(AWAdministracionHermod.OPLOOKUP_TYPE_SELECCIONADO);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        OPLookupCodes dato = new OPLookupCodes();
        dato.setCodigo(codigo);
        dato.setDescripcion(descripcion);
        dato.setTipo(tipo.getTipo());
        dato.setValor(valor);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_OPLOOKUP_CODE);
        evento.setOpLookupCodes(dato);
        evento.setOpLookupType(tipo);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de edicion de un
     * oplookup code
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * oplookup code
     * @throws AccionWebException
     */
    private EvnAdministracionHermod updateOPLookupCode(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = (String) session.getAttribute(COPLookupCodes.NOMBRE_OPLOOKUP_CODE);
        
        String codigoAEditar = (String) session.getAttribute(COPLookupCodes.NOMBRE_OPLOOKUP_CODE_EDITADO);
        
        String descripcion = request.getParameter(COPLookupCodes.DESCRIPCION_OPLOOKUP_CODE);
        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar una descripcion del Lookup Code.");
        }
        
        String valor = request.getParameter(COPLookupCodes.VALOR_OPLOOKUP_CODE);
        if ((valor == null) || (valor.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un valor de Lookup Code.");
        }
        
        OPLookupTypes tipo
                = (OPLookupTypes) session.getAttribute(AWAdministracionHermod.OPLOOKUP_TYPE_SELECCIONADO);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        OPLookupCodes dato = new OPLookupCodes();
        dato.setCodigo(codigo);
        dato.setDescripcion(descripcion);
        dato.setTipo(tipo.getTipo());
        dato.setValor(valor);
        
        OPLookupCodes datoAEditar = new OPLookupCodes();
        datoAEditar.setCodigo(codigoAEditar);
        datoAEditar.setTipo(tipo.getTipo());

        //cerrar ventana
        session.setAttribute("CERRAR_VENTANA", new Boolean(true));
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.UPDATE_OPLOOKUP_CODE);
        evento.setOpLookupCodes(dato);
        evento.setOpLookupCodesAEditar(datoAEditar);
        evento.setOpLookupType(tipo);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un oplookup code
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * oplookup code
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaOPLookupCode(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(COPLookupCodes.NOMBRE_OPLOOKUP_CODE);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Lookup Code.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        OPLookupTypes tipo
                = (OPLookupTypes) session.getAttribute(AWAdministracionHermod.OPLOOKUP_TYPE_SELECCIONADO);
        
        OPLookupCodes dato = new OPLookupCodes();
        dato.setCodigo(codigo);
        dato.setOPLookupType(tipo);
        dato.setTipo(tipo.getTipo());
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_OPLOOKUP_CODE);
        evento.setOpLookupCodes(dato);
        evento.setOpLookupType(tipo);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * subtipo de solicitud
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * subtipo de solicitud
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaSubtipoSolicitud(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = (String) session.getAttribute(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un código para el subtipo de solicitud");
        }
        
        String nombre = request.getParameter(CSubtipoSolicitud.NOMBRE_SUBTIPO_SOLICITUD);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar Nombre para el subtipo de solicitud.");
        }

        //Validacion del nombre de la solicitud
        List subtiposSolicitud = (List) session.getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
        Iterator it = subtiposSolicitud.iterator();
        while (it.hasNext()) {
            SubtipoSolicitud dato = (SubtipoSolicitud) it.next();
            if (dato.getNombre().equals(nombre)) {
                throw new AccionInvalidaException("El nombre debe ser diferente al de los subtipos existentes.");
            }
            
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        SubtipoSolicitud dato = new SubtipoSolicitud();
        dato.setIdSubtipoSol(codigo);
        dato.setNombre(nombre);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_SUBTIPO_SOLICITUD);
        evento.setSubtipoSolicitud(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un subtipo de solicitud
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * subtipo de solicitud
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaSubtipoSolicitud(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un código para el subtipo de solicitud");
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        SubtipoSolicitud dato = new SubtipoSolicitud();
        dato.setIdSubtipoSol(codigo);
        
        limpiarSesionEdicionSubtipoSolicitud(request);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_SUBTIPO_SOLICITUD);
        evento.setSubtipoSolicitud(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para visualizar los datos a editar de un
     * subtipo de solicitud
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * subtipo de solicitud
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editaSubtipoSolicitud(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un código para el subtipo de solicitud");
        }
        
        List subtiposSolicitud = (List) session.getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
        Iterator it = subtiposSolicitud.iterator();
        while (it.hasNext()) {
            SubtipoSolicitud dato = (SubtipoSolicitud) it.next();
            if (dato.getIdSubtipoSol().equals(codigo)) {
                session.setAttribute(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD_EDICION, dato.getIdSubtipoSol());
                session.setAttribute(CSubtipoSolicitud.NOMBRE_SUBTIPO_SOLICITUD, dato.getNombre());
                break;
            }
        }
        
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de edición de un
     * subtipo de solicitud
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * subtipo de solicitud
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editarSubtipoSolicitud(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = (String) session.getAttribute(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un código para el subtipo de solicitud");
        }
        
        String nombre = request.getParameter(CSubtipoSolicitud.NOMBRE_SUBTIPO_SOLICITUD);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar Nombre para el subtipo de solicitud.");
        }

        //Validacion del nombre de la solicitud
        List subtiposSolicitud = (List) session.getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
        Iterator it = subtiposSolicitud.iterator();
        while (it.hasNext()) {
            SubtipoSolicitud dato = (SubtipoSolicitud) it.next();
            if (dato.getIdSubtipoSol().equals(codigo)) {
                if (dato.getNombre().equals(nombre)) {
                    throw new AccionInvalidaException("El nombre debe ser diferente para que se pueda editar el subtipo.");
                }
            }
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        SubtipoSolicitud dato = new SubtipoSolicitud();
        dato.setIdSubtipoSol(codigo);
        dato.setNombre(nombre);
        
        limpiarSesionEdicionSubtipoSolicitud(request);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.EDITA_SUBTIPO_SOLICITUD);
        evento.setSubtipoSolicitud(dato);
        return evento;
    }

    /**
     * Este método se encarga de limpiar los valores de la
     * <code>HttpSession</code> en la pantalla administracion subtipo solicitud
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private void limpiarSesionEdicionSubtipoSolicitud(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD_EDICION);
        session.removeAttribute(CSubtipoSolicitud.NOMBRE_SUBTIPO_SOLICITUD);
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * tipo de cálculo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos deltipo
     * de cálculo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTipoCalculo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoCalculo.ID_TIPO_CALCULO);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de cálculo.");
        }
        
        String nombre = request.getParameter(CTipoCalculo.NOMBRE_TIPO_CALCULO);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre de Tipo de cálculo.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoCalculo dato = new TipoCalculo();
        dato.setIdTipoCalculo(codigo);
        dato.setNombre(nombre);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_TIPO_CALCULO);
        evento.setTipoCalculo(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un tipo de cálculo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos deltipo
     * de cálculo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaTipoCalculo(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoCalculo.ID_TIPO_CALCULO);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de cálculo.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoCalculo dato = new TipoCalculo();
        dato.setIdTipoCalculo(codigo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_TIPO_CALCULO);
        evento.setTipoCalculo(dato);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * tipo de impuesto
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de impuesto
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTipoImpuesto(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoImpuesto.ID_TIPO_IMPUESTO);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de impuesto.");
        }
        
        String nombre = request.getParameter(CTipoImpuesto.NOMBRE_TIPO_IMPUESTO);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre de Tipo de impuesto.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoImpuesto dato = new TipoImpuesto();
        dato.setIdTipoImpuesto(codigo);
        dato.setNombre(nombre);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_TIPO_IMPUESTO);
        evento.setTipoImpuesto(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un tipo de impuesto
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de impuesto
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaTipoImpuesto(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoImpuesto.ID_TIPO_IMPUESTO);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de impuesto.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoImpuesto dato = new TipoImpuesto();
        dato.setIdTipoImpuesto(codigo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_TIPO_IMPUESTO);
        evento.setTipoImpuesto(dato);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de cargar los
     * subtipo de atención
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * subtipo de atención
     * @throws AccionWebException
     */
    private EvnAdministracionHermod cargarSubTipoAtencion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CARGAR_SUBTIPO_ATENCION);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * subtipo de atención
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * subtipo de atención
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaSubTipoAtencion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        ValidacionParametrosAdminSubtipoAtencion excepcion = new ValidacionParametrosAdminSubtipoAtencion();
        
        String id = (String) session.getAttribute(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
        if ((id == null) || (id.trim().length() == 0)) {
            excepcion.addError("Debe Proporcionar un Código para el subtipo de atención.");
        }
        
        String nombre = request.getParameter(CSubtipoAtencion.NOMBRE_SUBTIPO_ATENCION);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            excepcion.addError("Debe Proporcionar un Nombre para el subtipo de atención.");
        }

        //guardar variable en session
        session.setAttribute(CSubtipoAtencion.NOMBRE_SUBTIPO_ATENCION, nombre);
        
        String minimoStr = request.getParameter(CSubtipoAtencion.MINIMO_ANOTACIONES_SUBTIPO_ATENCION);
        if ((minimoStr == null) || (minimoStr.trim().length() == 0)) {
            minimoStr = "0";
        }
        
        long minimo = 0;
        try {
            minimo = Long.parseLong(minimoStr);
        } catch (NumberFormatException e) {
            excepcion.addError("El número Mínimo de anotaciones no es válido.");
        }
        //guardar variable en session
        session.setAttribute(CSubtipoAtencion.MINIMO_ANOTACIONES_SUBTIPO_ATENCION, minimoStr);

        /*
		String unidadesStr = request.getParameter(CSubtipoAtencion.UNIDADES_TRABAJO_SUBTIPO_ATENCION);
		if ((minimoStr == null) || (minimoStr.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un número de unidades de Trabajo.");
		}

		long unidades = 0;
		try {
			unidades = Long.parseLong(unidadesStr);
		} catch (NumberFormatException e) {
			throw new AccionInvalidaException("El número de unidades de trabajo no es válido.");
		}*/
        String[] subtipos = request.getParameterValues(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        if (subtipos == null) {
            excepcion.addError("Debe Seleccionar al menos un tipo de solicitud.");
        }
        //guardar en session
        if (subtipos != null) {
            List lSubtiposSolicitud = new Vector();
            for (int i = 0; i != subtipos.length; i++) {
                String stemp = subtipos[i];
                lSubtiposSolicitud.add(stemp);
            }
            session.setAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD_EDICION, lSubtiposSolicitud);
        }
        
        String[] actos = request.getParameterValues(CSubtipoAtencion.TIPOS_ACTO);

        //guardar en session
        List lTiposActo = new Vector();
        if (actos != null) {
            for (int i = 0; i != actos.length; i++) {
                String stemp = actos[i];
                lTiposActo.add(stemp);
            }
            session.setAttribute(CSubtipoAtencion.TIPOS_ACTO_EDICION, lTiposActo);
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        if (excepcion.getErrores().size() > 0) {
            throw excepcion;
        }
        
        SubtipoAtencion dato = new SubtipoAtencion();
        dato.setIdSubtipoAtencion(id);
        dato.setNombre(nombre);
        /*
		dato.setUnidadesTrabajo(0);
         */
        dato.setMinAnotacione(minimo);
        
        for (int i = 0; i < subtipos.length; i++) {
            SubtipoSolicitud subtipoSolicitud = new SubtipoSolicitud();
            subtipoSolicitud.setIdSubtipoSol(subtipos[i]);
            
            SubAtencionSubSolicitud subAtenSubSolicitud = new SubAtencionSubSolicitud();
            subAtenSubSolicitud.setSubtipoSolicitud(subtipoSolicitud);
            subAtenSubSolicitud.setIdSubtipoSol(subtipoSolicitud.getIdSubtipoSol());
            
            dato.addSubtipoSolicitude(subAtenSubSolicitud);
        }
        
        if (actos != null) {
            for (int i = 0; i < actos.length; i++) {
                TipoActo tipoActo = new TipoActo();
                tipoActo.setIdTipoActo(actos[i]);
                
                SubtipoAtencionTipoActo subAtenTipoActo = new SubtipoAtencionTipoActo();
                subAtenTipoActo.setTipoActo(tipoActo);
                subAtenTipoActo.setIdTipoActo(tipoActo.getIdTipoActo());
                
                dato.addTiposActo(subAtenTipoActo);
            }
        }
        
        limpiarSesionSubtipoAtencion(request);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_SUBTIPO_ATENCION);
        evento.setSubtipoAtencion(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un subtipo de atención
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * subtipo de atención
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaSubTipoAtencion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String id = request.getParameter(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
        if ((id == null) || (id.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para el subtipo de atención.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        limpiarSesionSubtipoAtencion(request);
        SubtipoAtencion dato = new SubtipoAtencion();
        dato.setIdSubtipoAtencion(id);
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_SUBTIPO_ATENCION);
        evento.setSubtipoAtencion(dato);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para mostrar los datoa a editar de un
     * subtipo de atención
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * subtipo de atención
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editarSubTipoAtencion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String idSubtipoAtencion = request.getParameter(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
        if ((idSubtipoAtencion == null) || (idSubtipoAtencion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para el subtipo de atención.");
        }

        //Se busca el subtipo de atencion
        List subtiposAtencion = (List) session.getAttribute(WebKeys.LISTA_SUBTIPOS_ATENCION);
        for (Iterator it = subtiposAtencion.iterator(); it.hasNext();) {
            SubtipoAtencion dato = (SubtipoAtencion) it.next();
            if (idSubtipoAtencion.equals(dato.getIdSubtipoAtencion())) {
                //Se guardan en la sesion los datos del tipo de atencion
                session.setAttribute(CSubtipoAtencion.ID_SUBTIPO_ATENCION, dato.getIdSubtipoAtencion());
                session.setAttribute(CSubtipoAtencion.NOMBRE_SUBTIPO_ATENCION, dato.getNombre());
                session.setAttribute(CSubtipoAtencion.MINIMO_ANOTACIONES_SUBTIPO_ATENCION, Long.toString(dato.getMinAnotaciones()));
                List lSubtipoSolicitud = dato.getSubtipoSolicitudes();
                List lIdSubtipoSolicitud = new Vector();
                for (Iterator itSubtipoSolicitud = lSubtipoSolicitud.iterator(); itSubtipoSolicitud.hasNext();) {
                    SubAtencionSubSolicitud subSol = (SubAtencionSubSolicitud) itSubtipoSolicitud.next();
                    lIdSubtipoSolicitud.add(subSol.getSubtipoSolicitud().getIdSubtipoSol());
                }
                session.setAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD_EDICION, lIdSubtipoSolicitud);
                
                List lTiposActo = dato.getTiposActos();
                List lIdTiposActo = new Vector();
                for (Iterator itTipoActo = lTiposActo.iterator(); itTipoActo.hasNext();) {
                    SubtipoAtencionTipoActo tipoActo = (SubtipoAtencionTipoActo) itTipoActo.next();
                    lIdTiposActo.add(tipoActo.getTipoActo().getIdTipoActo());
                }
                session.setAttribute(CSubtipoAtencion.TIPOS_ACTO_EDICION, lIdTiposActo);
                break;
            }
        }
        session.setAttribute(CSubtipoAtencion.SUBTIPO_ATENCION_EDICION, new Boolean(true));
        
        return null;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de edicion de un
     * subtipo de atención
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * subtipo de atención
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editaSubTipoAtencion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        ValidacionParametrosAdminSubtipoAtencion excepcion = new ValidacionParametrosAdminSubtipoAtencion();
        
        String id = (String) session.getAttribute(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
        if ((id == null) || (id.trim().length() == 0)) {
            excepcion.addError("Debe Proporcionar un Código para el subtipo de atención.");
        }
        
        String nombre = request.getParameter(CSubtipoAtencion.NOMBRE_SUBTIPO_ATENCION);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            excepcion.addError("Debe Proporcionar un Nombre para el subtipo de atención.");
        }
        //guardar variable en session
        session.setAttribute(CSubtipoAtencion.NOMBRE_SUBTIPO_ATENCION, nombre);
        
        String minimoStr = request.getParameter(CSubtipoAtencion.MINIMO_ANOTACIONES_SUBTIPO_ATENCION);
        if ((minimoStr == null) || (minimoStr.trim().length() == 0)) {
            minimoStr = "0";
        }
        
        long minimo = 0;
        try {
            minimo = Long.parseLong(minimoStr);
        } catch (NumberFormatException e) {
            excepcion.addError("El número Mínimo de anotaciones no es válido.");
        }
        //guardar variable en session
        session.setAttribute(CSubtipoAtencion.MINIMO_ANOTACIONES_SUBTIPO_ATENCION, minimoStr);

        /*
		String unidadesStr = request.getParameter(CSubtipoAtencion.UNIDADES_TRABAJO_SUBTIPO_ATENCION);
		if ((minimoStr == null) || (minimoStr.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un número de unidades de Trabajo.");
		}

		long unidades = 0;
		try {
			unidades = Long.parseLong(unidadesStr);
		} catch (NumberFormatException e) {
			throw new AccionInvalidaException("El número de unidades de trabajo no es válido.");
		}*/
        String[] subtipos = request.getParameterValues(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        if (subtipos == null) {
            excepcion.addError("Debe Seleccionar al menos un tipo de solicitud.");
        }
        //guardar en session
        if (subtipos != null) {
            List lSubtiposSolicitud = new Vector();
            for (int i = 0; i != subtipos.length; i++) {
                String stemp = subtipos[i];
                lSubtiposSolicitud.add(stemp);
            }
            session.setAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD_EDICION, lSubtiposSolicitud);
        }
        
        String[] actos = request.getParameterValues(CSubtipoAtencion.TIPOS_ACTO);

        //guardar en session
        List lTiposActo = new Vector();
        if (actos != null) {
            for (int i = 0; i != actos.length; i++) {
                String stemp = actos[i];
                lTiposActo.add(stemp);
            }
            session.setAttribute(CSubtipoAtencion.TIPOS_ACTO_EDICION, lTiposActo);
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        if (excepcion.getErrores().size() > 0) {
            throw excepcion;
        }
        
        SubtipoAtencion dato = new SubtipoAtencion();
        dato.setIdSubtipoAtencion(id);
        dato.setNombre(nombre);
        /*
		dato.setUnidadesTrabajo(0);
         */
        dato.setMinAnotacione(minimo);
        
        for (int i = 0; i < subtipos.length; i++) {
            SubtipoSolicitud subtipoSolicitud = new SubtipoSolicitud();
            subtipoSolicitud.setIdSubtipoSol(subtipos[i]);
            
            SubAtencionSubSolicitud subAtenSubSolicitud = new SubAtencionSubSolicitud();
            subAtenSubSolicitud.setSubtipoSolicitud(subtipoSolicitud);
            subAtenSubSolicitud.setIdSubtipoSol(subtipoSolicitud.getIdSubtipoSol());
            
            dato.addSubtipoSolicitude(subAtenSubSolicitud);
        }
        
        if (actos != null) {
            for (int i = 0; i < actos.length; i++) {
                TipoActo tipoActo = new TipoActo();
                tipoActo.setIdTipoActo(actos[i]);
                
                SubtipoAtencionTipoActo subAtenTipoActo = new SubtipoAtencionTipoActo();
                subAtenTipoActo.setTipoActo(tipoActo);
                subAtenTipoActo.setIdTipoActo(tipoActo.getIdTipoActo());
                
                dato.addTiposActo(subAtenTipoActo);
            }
        }
        
        limpiarSesionSubtipoAtencion(request);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.EDITAR_SUBTIPO_ATENCION);
        evento.setSubtipoAtencion(dato);
        return evento;
    }

    /**
     * Este método se encarga de limpiar los valores de la
     * <code>HttpSession</code> en la pantalla administracion subtipo atencion
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private void limpiarSesionSubtipoAtencion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CSubtipoAtencion.SUBTIPO_ATENCION_EDICION);
        session.removeAttribute(CSubtipoAtencion.TIPOS_ACTO_EDICION);
        session.removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD_EDICION);
        session.removeAttribute(CSubtipoAtencion.MINIMO_ANOTACIONES_SUBTIPO_ATENCION);
        session.removeAttribute(CSubtipoAtencion.NOMBRE_SUBTIPO_ATENCION);
        session.removeAttribute(WebKeys.LISTA_SUBTIPOS_ATENCION);
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * tipo de derecho registral
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de derecho registral
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTipoDerechoRegistral(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoDerechoRegistral.ID_TIPO_DERECHO_REGISTRAL);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de derecho registral.");
        }
        
        String nombre = request.getParameter(CTipoDerechoRegistral.NOMBRE_TIPO_DERECHO_REGISTRAL);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre de Tipo de derecho registral.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoDerechoReg dato = new TipoDerechoReg();
        dato.setIdTipoDerechoReg(codigo);
        dato.setNombre(nombre);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.ADICIONA_TIPO_DERECHO_REGISTRAL);
        evento.setTipoDerechoRegistral(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un tipo de derecho registral
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de derecho registral
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaTipoDerechoRegistral(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoDerechoRegistral.ID_TIPO_DERECHO_REGISTRAL);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de derecho registral.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoDerechoReg dato = new TipoDerechoReg();
        dato.setIdTipoDerechoReg(codigo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_TIPO_DERECHO_REGISTRAL);
        evento.setTipoDerechoRegistral(dato);
        return evento;
    }
    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de una
     * acción notarial
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * acción notarial
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaAccionNotarial(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();

        //String codigo = request.getParameter(CAccionNotarial.ID_ACCION_NOTARIAL);
        //Esto se modifico para no pedir el codigo de la acción notarial
        //if ((codigo == null) || (codigo.trim().length() == 0)) {
        //throw new AccionInvalidaException("Debe Proporcionar un Código para la Acción Notarial.");
        //}
        String nombre = request.getParameter(CAccionNotarial.NOMBRE_ACCION_NOTARIAL);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre de Acción Notarial.");
        }
        
        String cuantiaStr = request.getParameter(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
        if ((cuantiaStr == null) || (cuantiaStr.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar una Cuantía de Acción Notarial.");
        }
        
        String tipo = request.getParameter(CAccionNotarial.TIPO_ACCION_NOTARIAL);
        preservarInfoAccionNotarial(request);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        AccionNotarial dato = new AccionNotarial();
        //dato.setIdAccionNotarial(codigo);
        dato.setNombre(nombre);
        if (cuantiaStr.equals("TRUE")) {
            dato.setCuantia(true);
        } else {
            dato.setCuantia(false);
        }
        dato.setTipo(tipo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_ACCION_NOTARIAL);
        evento.setAccionNotarial(dato);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento habilitarEdicionAccionNotarial(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        String idAccion = request.getParameter(CAccionNotarial.ID_ACCION_NOTARIAL);
        
        List tipos = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES);
        if (tipos == null) {
            tipos = new ArrayList();
        }
        Iterator it = tipos.iterator();
        while (it.hasNext()) {
            AccionNotarial accion = (AccionNotarial) it.next();
            if (accion.getIdAccionNotarial().equals(idAccion)) {
                request.getSession().setAttribute(CAccionNotarial.ACCION_NOTARIAL, accion);
            }
        }
        
        request.getSession().removeAttribute(CAccionNotarial.ID_ACCION_NOTARIAL);
        request.getSession().removeAttribute(CAccionNotarial.NOMBRE_ACCION_NOTARIAL);
        request.getSession().removeAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
        
        request.getSession().setAttribute(WebKeys.OCULTAR, new Boolean(true));
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento desHabilitarEdicionAccionNotarial(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        request.getSession().removeAttribute(CAccionNotarial.ACCION_NOTARIAL);
        request.getSession().removeAttribute(WebKeys.OCULTAR);
        request.getSession().removeAttribute(CAccionNotarial.ID_ACCION_NOTARIAL);
        request.getSession().removeAttribute(CAccionNotarial.NOMBRE_ACCION_NOTARIAL);
        request.getSession().removeAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de edición de una
     * acción notarial
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * acción notarial
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editarAccionNotarial(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CAccionNotarial.ID_ACCION_NOTARIAL);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para la Acción Notarial.");
        }
        
        String nombre = request.getParameter(CAccionNotarial.NOMBRE_ACCION_NOTARIAL);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre de Acción Notarial.");
        }
        
        String cuantiaStr = request.getParameter(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
        if ((cuantiaStr == null) || (cuantiaStr.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar una Cuantía de Acción Notarial.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        AccionNotarial dato = new AccionNotarial();
        dato.setIdAccionNotarial(codigo);
        dato.setNombre(nombre);
        if (cuantiaStr.equals("TRUE")) {
            dato.setCuantia(true);
        } else {
            dato.setCuantia(false);
        }
        dato.setTipo(null);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.EDITA_ACCION_NOTARIAL);
        evento.setAccionNotarial(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * una acción notarial
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * acción notarial
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaAccionNotarial(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CAccionNotarial.ID_ACCION_NOTARIAL);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para la Acción Notarial.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        AccionNotarial dato = new AccionNotarial();
        dato.setIdAccionNotarial(codigo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_ACCION_NOTARIAL);
        evento.setAccionNotarial(dato);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * tipo de acto
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTipoActo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoActo.ID_TIPO_ACTO);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de Acto.");
        }
        
        String nombre = request.getParameter(CTipoActo.NOMBRE_TIPO_ACTO);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre de Tipo de Acto.");
        }
        
        String impuestoCuantia = request.getParameter(CTipoActo.IMPUESTO_CUANTIA);
        if ((impuestoCuantia == null) || (impuestoCuantia.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Seleccionar una opción para Impuesto de cuantía.");
        }

        /*
                * @autor          : JATENCIA 
                * @mantis        : 0015082 
                * @Requerimiento : 027_589_Acto_liquidación_copias 
                * @descripcion   : Se declara una variable para identificar el atributo que se
                * va a insertar.
                * 
         */
        String estadoTipoActo = request.getParameter(CTipoActo.ESTADO_ACTO);
        if ((estadoTipoActo == null) || (estadoTipoActo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Seleccionar un estado para el Tipo de Acto.");
        }
        /* - Fin del bloque - */
        
        String tipoCalculo = request.getParameter(CTipoCalculo.ID_TIPO_CALCULO);
        if (tipoCalculo == null
                || tipoCalculo.trim().length() == 0
                || tipoCalculo.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new AccionInvalidaException("Debe seleccionar un tipo de cálculo");
        }
        TipoCalculo tipoCalculoObj = new TipoCalculo();
        tipoCalculoObj.setIdTipoCalculo(tipoCalculo);
        
        String[] derechos = request.getParameterValues(CTipoActo.TIPO_DERECHO_REGISTRAL_ASOCIADO);
        if (derechos == null) {
            throw new AccionInvalidaException("Debe Seleccionar al menos un tipo de derecho registral.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoActo dato = new TipoActo();
        dato.setIdTipoActo(codigo);
        dato.setNombre(nombre);
        dato.setTipoCalculo(tipoCalculoObj);
        dato.setImpPorCuantia((impuestoCuantia.equals(CTipoActo.IMPUESTO_CUANTIA_SI) ? true : false));

        /*
                * @autor          : JATENCIA 
                * @mantis        : 0015082 
                * @Requerimiento : 027_589_Acto_liquidación_copias 
                * @descripcion   : Se asigna un valor boolean a la variable antes de insertar. 
         */
        dato.setActivo((estadoTipoActo.equals(CTipoActo.ACTO_ACTIVO) ? true : false));
        /* - Fin del bloque - */
        
        for (int i = 0; i < derechos.length; i++) {
            TipoDerechoReg tipo = new TipoDerechoReg();
            tipo.setIdTipoDerechoReg(derechos[i]);
            
            TipoActoDerechoRegistral tipoActoDer = new TipoActoDerechoRegistral();
            tipoActoDer.setTipoDerechoRegistral(tipo);
            tipoActoDer.setIdTipoDerechoReg(tipo.getIdTipoDerechoReg());
            
            dato.addTiposDerechosRegistrale(tipoActoDer);
        }
        
        limpiarSesionEdicionTipoActo(request);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_TIPO_ACTO);
        evento.setTipoActo(dato);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de editar de un
     * tipo de acto
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editaTipoActo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = (String) session.getAttribute(CTipoActo.ID_TIPO_ACTO);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de Acto.");
        }
        
        String nombre = request.getParameter(CTipoActo.NOMBRE_TIPO_ACTO);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Nombre de Tipo de Acto.");
        }
        
        String impuestoCuantia = request.getParameter(CTipoActo.IMPUESTO_CUANTIA);
        if ((impuestoCuantia == null) || (impuestoCuantia.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Seleccionar una opción para Impuesto de cuantía.");
        }

        /*
                * @autor          : JATENCIA 
                * @mantis        : 0015082 
                * @Requerimiento : 027_589_Acto_liquidación_copias 
                * @descripcion   : Se declara una variable para identificar el atributo que se
                * va a modificar.
                * 
         */
        String estadoTipoActo = request.getParameter(CTipoActo.ESTADO_ACTO);
        if ((estadoTipoActo == null) || (estadoTipoActo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Seleccionar un estado para el Tipo de Acto.");
        }
        /* - Fin del bloque - */
        
        String tipoCalculo = request.getParameter(CTipoCalculo.ID_TIPO_CALCULO);
        if (tipoCalculo == null
                || tipoCalculo.trim().length() == 0
                || tipoCalculo.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new AccionInvalidaException("Debe seleccionar un tipo de cálculo");
        }
        TipoCalculo tipoCalculoObj = new TipoCalculo();
        tipoCalculoObj.setIdTipoCalculo(tipoCalculo);
        
        String[] derechos = request.getParameterValues(CTipoActo.TIPO_DERECHO_REGISTRAL_ASOCIADO);
        if (derechos == null) {
            throw new AccionInvalidaException("Debe Seleccionar al menos un tipo de derecho registral.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoActo dato = new TipoActo();
        dato.setIdTipoActo(codigo);
        dato.setNombre(nombre);
        dato.setTipoCalculo(tipoCalculoObj);
        dato.setImpPorCuantia((impuestoCuantia.equals(CTipoActo.IMPUESTO_CUANTIA_SI) ? true : false));

        /*
                * @autor          : JATENCIA 
                * @mantis        : 0015082 
                * @Requerimiento : 027_589_Acto_liquidación_copias 
                * @descripcion   : Se asigna un valor boolean a la variable antes de actualizar. 
         */
        dato.setActivo((estadoTipoActo.equals(CTipoActo.ACTO_ACTIVO) ? true : false));
        /* - Fin del bloque - */
        
        for (int i = 0; i < derechos.length; i++) {
            TipoDerechoReg tipo = new TipoDerechoReg();
            tipo.setIdTipoDerechoReg(derechos[i]);
            
            TipoActoDerechoRegistral tipoActoDer = new TipoActoDerechoRegistral();
            tipoActoDer.setTipoDerechoRegistral(tipo);
            tipoActoDer.setIdTipoDerechoReg(tipo.getIdTipoDerechoReg());
            
            dato.addTiposDerechosRegistrale(tipoActoDer);
        }
        
        limpiarSesionEdicionTipoActo(request);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.EDITA_TIPO_ACTO);
        evento.setTipoActo(dato);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un tipo de acto
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editarTipoActo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        /*
                * @autor          : JATENCIA
                * @mantis        : 0015082 
                * @Requerimiento : 027_589_Acto_liquidación_copias 
                * @descripcion   : Se modifica la lista para regarcar.
         */
        List tipos = (List) session.getAttribute(WebKeys.LISTA_TIPOS_ACTO_DOS);
        /* - Fin del bloque - */
        String codigo = request.getParameter(CTipoActo.ID_TIPO_ACTO);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de Acto.");
        }
        
        Iterator i = tipos.iterator();
        TipoActo tacto = null;
        boolean esta = false;
        while (i.hasNext()) {
            tacto = (TipoActo) i.next();
            if (tacto.getIdTipoActo().equals(codigo)) {
                esta = true;
                break;
            }
        }
        
        if (!esta) {
            throw new AccionInvalidaException("El codigo del acto es invalido");
        }

        //colocar datos del tipo acto en la session.
        if (tacto != null) {
            session.setAttribute(CTipoActo.ID_TIPO_ACTO, tacto.getIdTipoActo());
            session.setAttribute(CTipoActo.NOMBRE_TIPO_ACTO, tacto.getNombre());
            if (tacto.isImpPorCuantia()) {
                session.setAttribute(CTipoActo.IMPUESTO_CUANTIA_EDICION, CTipoActo.IMPUESTO_CUANTIA_SI);
            } else {
                session.setAttribute(CTipoActo.IMPUESTO_CUANTIA_EDICION, CTipoActo.IMPUESTO_CUANTIA_NO);
            }
            /*
                         *@autor          : JATENCIA
                         * @mantis        : 0015082 
                         * @Requerimiento : 027_589_Acto_liquidación_copias 
                         * @descripcion   : Se establece la variable declarada en sección.
             */
            if (tacto.isActivo()) {
                session.setAttribute(CTipoActo.ACTO_ACTIVO_EDICION, CTipoActo.ACTO_ACTIVO);
            } else {
                session.setAttribute(CTipoActo.ACTO_ACTIVO_EDICION, CTipoActo.ACTO_NO_ACTIVO);
            }
            /* - Fin del bloque - */
            
        }
        session.setAttribute(CTipoCalculo.ID_TIPO_CALCULO, tacto.getTipoCalculo().getIdTipoCalculo());
        session.setAttribute(CTipoActo.TIPO_DERECHO_REGISTRAL_ASOCIADO_EDICION, tacto.getTiposDerechosRegistrales());
        return null;
    }

    /**
     * Este método se encarga de limpiar los valores de la
     * <code>HttpSession</code> en la pantalla administracion tipo acto
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private void limpiarSesionEdicionTipoActo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CTipoActo.ID_TIPO_ACTO);
        session.removeAttribute(CTipoActo.NOMBRE_TIPO_ACTO);
        session.removeAttribute(CTipoActo.IMPUESTO_CUANTIA_EDICION);
        session.removeAttribute(CTipoCalculo.ID_TIPO_CALCULO);
        session.removeAttribute(CTipoActo.TIPO_DERECHO_REGISTRAL_ASOCIADO_EDICION);
        /*
                 *@autor          : JATENCIA
                 * @mantis        : 0015082 
                 * @Requerimiento : 027_589_Acto_liquidación_copias 
                 * @descripcion   : Se limpia la variable de sección.
         */
        session.removeAttribute(CTipoActo.ACTO_ACTIVO_EDICION);
        /* - Fin del bloque - */
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un tipo de acto
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaTipoActo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CTipoActo.ID_TIPO_ACTO);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Tipo de Acto.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        TipoActo dato = new TipoActo();
        dato.setIdTipoActo(codigo);
        
        limpiarSesionEdicionTipoActo(request);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_TIPO_ACTO);
        evento.setTipoActo(dato);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de una
     * Categoria
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * categoría
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaCategoria(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        this.guardarSesionAdministacionCategorias(request);
        
        String codigo = request.getParameter(CCategoria.ID_CATEGORIA);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new ValidacionParametrosCategoriaException("Debe Proporcionar un Código para la Categoría.");
        }
        
        String nombre = request.getParameter(CCategoria.NOMBRE_CATEGORIA);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new ValidacionParametrosCategoriaException("Debe Proporcionar un Nombre de Categoría.");
        }
        
        String activo = request.getParameter(CCategoria.CATEGORIA_ESTADO);
        boolean act = false;
        if (activo != null) {
            act = true;
        }
        
        String valorMaxCategoria = request.getParameter(CCategoria.VALOR_MAX_CATEGORIA);
        if ((valorMaxCategoria == null) || (valorMaxCategoria.trim().length() == 0)) {
            throw new ValidacionParametrosCategoriaException("Debe Seleccionar un valor máximo para la categoría.");
        }
        
        double valorMax = 0.0d;
        try {
            valorMax = Double.parseDouble(valorMaxCategoria);
            
        } catch (NumberFormatException e) {
            throw new ValidacionParametrosCategoriaException(valorMaxCategoria + " no es un valor numérico válido.");
        }
        
        String valorMinCategoria = request.getParameter(CCategoria.VALOR_MIN_CATEGORIA);
        if ((valorMinCategoria == null) || (valorMinCategoria.trim().length() == 0)) {
            throw new ValidacionParametrosCategoriaException("Debe Seleccionar un valor máximo para la categoría.");
        }
        
        double valorMin = 0.0d;
        try {
            valorMin = Double.parseDouble(valorMinCategoria);
            
        } catch (NumberFormatException e) {
            throw new ValidacionParametrosCategoriaException(valorMinCategoria + " no es un valor numérico válido.");
        }
        
        String unidadMaxCategoria = request.getParameter(CCategoria.UNIDAD_MAX_CATEGORIA);
        if (unidadMaxCategoria == null || unidadMaxCategoria.trim().length() == 0) {
            throw new ValidacionParametrosCategoriaException("Debe seleccionar una unidad máxima de categoría. ");
        }
        
        double unidadMax = 0.0d;
        try {
            unidadMax = Double.parseDouble(unidadMaxCategoria);
            
        } catch (NumberFormatException e) {
            throw new ValidacionParametrosCategoriaException(unidadMaxCategoria + " no es un valor numérico válido.");
        }
        
        String unidadMinCategoria = request.getParameter(CCategoria.UNIDAD_MIN_CATEGORIA);
        if (unidadMinCategoria == null || unidadMinCategoria.trim().length() == 0) {
            throw new ValidacionParametrosCategoriaException("Debe seleccionar una unidad máxima de categoría. ");
        }
        
        double unidadMin = 0.0d;
        try {
            unidadMin = Double.parseDouble(unidadMinCategoria);
            
        } catch (NumberFormatException e) {
            throw new ValidacionParametrosCategoriaException(unidadMinCategoria + " no es un valor numérico válido.");
        }

        //Validaciones sobre el grupo de tarifas.
        //No puede tener el mismo nombre que otra categoria ya existente.
        boolean yaExiste = false;
        List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_CATEGORIAS);
        for (Iterator it = tipos.iterator(); it.hasNext() && !yaExiste;) {
            Categoria cat = (Categoria) it.next();
            if (cat.getNombre().equals(nombre)) {
                yaExiste = true;
            }
        }
        
        if (yaExiste) {
            throw new ValidacionParametrosCategoriaException("Ya existe una categoria con nombre " + nombre + ".");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Categoria dato = new Categoria();
        dato.setIdCategoria(codigo);
        dato.setNombre(nombre.toUpperCase());
        dato.setUnidadMax(unidadMax);
        dato.setUnidadMin(unidadMin);
        dato.setValorMax(valorMax);
        dato.setValorMin(valorMin);
        dato.setActivo(act);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_CATEGORIA);
        evento.setCategoria(dato);
        return evento;
        
    }

    /**
     * @param request
     */
    private void guardarSesionAdministacionCategorias(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        
        session.setAttribute(CCategoria.ID_CATEGORIA, request.getParameter(CCategoria.ID_CATEGORIA));
        session.setAttribute(CCategoria.NOMBRE_CATEGORIA, request.getParameter(CCategoria.NOMBRE_CATEGORIA));
        session.setAttribute(CCategoria.VALOR_MAX_CATEGORIA, request.getParameter(CCategoria.VALOR_MAX_CATEGORIA));
        session.setAttribute(CCategoria.VALOR_MIN_CATEGORIA, request.getParameter(CCategoria.VALOR_MIN_CATEGORIA));
        session.setAttribute(CCategoria.UNIDAD_MAX_CATEGORIA, request.getParameter(CCategoria.UNIDAD_MAX_CATEGORIA));
        session.setAttribute(CCategoria.UNIDAD_MIN_CATEGORIA, request.getParameter(CCategoria.UNIDAD_MIN_CATEGORIA));
        session.setAttribute(CCategoria.CATEGORIA_ESTADO, request.getParameter(CCategoria.CATEGORIA_ESTADO));
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de edicion de una
     * Categoria
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * categoría
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editaCategoria(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        this.guardarSesionAdministacionCategorias(request);
        
        String codigo = (String) session.getAttribute(CCategoria.ID_CATEGORIA);
        
        String nombre = request.getParameter(CCategoria.NOMBRE_CATEGORIA);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            throw new ValidacionParametrosCategoriaException("Debe Proporcionar un Nombre de Categoría.");
        }
        
        String valorMaxCategoria = request.getParameter(CCategoria.VALOR_MAX_CATEGORIA);
        if ((valorMaxCategoria == null) || (valorMaxCategoria.trim().length() == 0)) {
            throw new ValidacionParametrosCategoriaException("Debe Seleccionar un valor máximo para la categoría.");
        }
        
        double valorMax = 0.0d;
        try {
            valorMax = Double.parseDouble(valorMaxCategoria);
            
        } catch (NumberFormatException e) {
            throw new ValidacionParametrosCategoriaException(valorMaxCategoria + " no es un valor numérico válido.");
        }
        
        String valorMinCategoria = request.getParameter(CCategoria.VALOR_MIN_CATEGORIA);
        if ((valorMinCategoria == null) || (valorMinCategoria.trim().length() == 0)) {
            throw new ValidacionParametrosCategoriaException("Debe Seleccionar un valor máximo para la categoría.");
        }
        
        double valorMin = 0.0d;
        try {
            valorMin = Double.parseDouble(valorMinCategoria);
            
        } catch (NumberFormatException e) {
            throw new ValidacionParametrosCategoriaException(valorMinCategoria + " no es un valor numérico válido.");
        }
        
        String unidadMaxCategoria = request.getParameter(CCategoria.UNIDAD_MAX_CATEGORIA);
        if (unidadMaxCategoria == null || unidadMaxCategoria.trim().length() == 0) {
            throw new ValidacionParametrosCategoriaException("Debe seleccionar una unidad máxima de categoría. ");
        }
        
        double unidadMax = 0.0d;
        try {
            unidadMax = Double.parseDouble(unidadMaxCategoria);
            
        } catch (NumberFormatException e) {
            throw new ValidacionParametrosCategoriaException(unidadMaxCategoria + " no es un valor numérico válido.");
        }
        
        String unidadMinCategoria = request.getParameter(CCategoria.UNIDAD_MIN_CATEGORIA);
        if (unidadMinCategoria == null || unidadMinCategoria.trim().length() == 0) {
            throw new ValidacionParametrosCategoriaException("Debe seleccionar una unidad máxima de categoría. ");
        }
        
        double unidadMin = 0.0d;
        try {
            unidadMin = Double.parseDouble(unidadMinCategoria);
            
        } catch (NumberFormatException e) {
            throw new ValidacionParametrosCategoriaException(unidadMinCategoria + " no es un valor numérico válido.");
        }
        
        String activo = request.getParameter(CCategoria.CATEGORIA_ESTADO);
        boolean act = false;
        if (activo != null) {
            act = true;
        }

        //Validaciones sobre el grupo de tarifas.
        //No puede tener el mismo nombre que otra categoria ya existente.
        boolean yaExiste = false;
        List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_CATEGORIAS);
        for (Iterator it = tipos.iterator(); it.hasNext() && !yaExiste;) {
            Categoria cat = (Categoria) it.next();
            if (!cat.getIdCategoria().equals(codigo)) {
                if (cat.getNombre().equals(nombre)) {
                    yaExiste = true;
                }
            }
        }
        
        if (yaExiste) {
            throw new ValidacionParametrosCategoriaException("Ya existe una categoria con nombre " + nombre + ".");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Categoria dato = new Categoria();
        dato.setIdCategoria(codigo);
        dato.setNombre(nombre.toUpperCase());
        dato.setUnidadMax(unidadMax);
        dato.setUnidadMin(unidadMin);
        dato.setValorMax(valorMax);
        dato.setValorMin(valorMin);
        dato.setActivo(act);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.EDITA_CATEGORIA);
        evento.setCategoria(dato);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * una Categoria
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * categoría
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaCategoria(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String codigo = request.getParameter(CCategoria.ID_CATEGORIA);
        if ((codigo == null) || (codigo.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para la Categoría.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Categoria dato = new Categoria();
        dato.setIdCategoria(codigo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_CATEGORIA);
        evento.setCategoria(dato);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de mostrar
     * circulos segun el administrador
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * administrador
     * @throws AccionWebException
     */
    private EvnAdministracionHermod mostrarCirculoEstacionRecibo(HttpServletRequest request)
            throws AccionWebException {

        //OBTENER VARIABLES DE LA SESSION
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //CREAR EVENTO
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.MOSTRAR_CIRCULO_ESTACION_RECIBO);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de set de una
     * Estación Recibo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * Estación Recibo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod setEstacionRecibo(HttpServletRequest request)
            throws AccionWebException {
        //CREAR LISTA DE EXCEPCIONES
        ValidacionAdicionEstacionReciboException exception
                = new ValidacionAdicionEstacionReciboException();

        //OBTENER VARIABLES DE LA SESSION
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String idEstacion = (String) request.getParameter(CEstacionRecibo.ID_ESTACION_RECIBO);
        String idProceso = (String) request.getParameter(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
        session.setAttribute("ESTACION", idEstacion);
        session.setAttribute("PROCESO", idProceso);
        String idCirculo = (String) session.getAttribute(CCirculo.ID_CIRCULO);

        //CODIGO DE LA ESTACION
        if ((idEstacion == null) || (idEstacion.trim().length() == 0)) {
            exception.addError("El Circulo debe tener una Estación Asociada");
        }

        //NUMERO INICIAL
        String numeroInicial = request.getParameter(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
        if ((numeroInicial == null) || (numeroInicial.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un valor inicial para la numeración de recibos.");
        }
        long valorInicial = 0;
        try {
            valorInicial = Long.parseLong(numeroInicial);
            
        } catch (NumberFormatException e) {
            exception.addError(numeroInicial + " no es un valor numérico válido.");
        }

        //NUMERO FINAL
        String numeroFinal = request.getParameter(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
        if (numeroFinal == null || numeroFinal.trim().length() == 0) {
            exception.addError("Debe Proporcionar un valor final para la numeración de recibos.");
        }
        long valorFinal = 0;
        try {
            valorFinal = Long.parseLong(numeroFinal);
        } catch (NumberFormatException e) {
            exception.addError(numeroFinal + " no es un valor numérico válido.");
        }

        //NUMERO ACTUAL
        String numeroActual = request.getParameter(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
        long valorActual = 0;
        
        if (numeroActual == null || numeroActual.trim().length() == 0) {
            valorActual = valorInicial - 1;
        } else {
            try {
                valorActual = Long.parseLong(numeroActual);
            } catch (NumberFormatException e) {
                exception.addError(numeroActual + " no es un valor numérico válido.");
            }
        }

        //NUMERO PROCESO
        String numeroProceso = request.getParameter(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
        long valorProceso = 0;
        
        if (numeroProceso == null || numeroProceso.trim().length() == 0) {
            valorProceso = 0;
        } else {
            try {
                valorProceso = Long.parseLong(numeroProceso);
            } catch (NumberFormatException e) {
                exception.addError(numeroProceso + " no es un valor numérico válido.");
            }
        }

        //VALIDAR SI LOS NUMEROS CUMPLEN CON LOS RANGOS
        if (valorActual < valorInicial - 1 && valorActual >= valorFinal && valorInicial >= valorFinal) {
            exception.addError("Debe Proporcionar un rango de Recibos Valido.");
        }

        //SI OCURRIO ALGUN PROBLEMA DE VALIDACION
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        //CREAR ESTACION_RECIBO
        EstacionRecibo eRecibo = new EstacionRecibo();
        eRecibo.setIdEstacion(idEstacion);
        eRecibo.setNumeroInicial(valorInicial);
        eRecibo.setNumeroFinal(valorFinal);
        eRecibo.setUltimoNumero(valorActual);
        eRecibo.setNumeroProceso(valorProceso);

        //CREAR EVENTO
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        Estacion estacion = new Estacion();
        estacion.setEstacionId(idEstacion);
        
        gov.sir.core.negocio.modelo.Usuario user = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.SET_ESTACION_RECIBO);
        evento.setEstacionRecibo(eRecibo);
        evento.setCirculo(circulo);
        evento.setEstacion(estacion);
        evento.setUsuarioNeg(user);
        
        session.removeAttribute("ESTACION");
        session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
        
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de reset de una
     * Estación Recibo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * Estación Recibo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod traspasoEstacionRecibo(HttpServletRequest request)
            throws AccionWebException {
        this.preservarInfoPasoEstacionRecibo(request);
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        ValidacionAdicionEstacionReciboException exception
                = new ValidacionAdicionEstacionReciboException();
        
        String id = request.getParameter(CEstacionRecibo.ID_ESTACION_RECIBO);
        if ((id == null) || id.equals("SIN_SELECCIONAR")) {
            exception.addError("Debe seleccionar una Estación.");
        }
        
        String idProceso = request.getParameter(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
        if (idProceso == null || idProceso.equals("SIN_SELECCIONAR")) {
            exception.addError("Debe seleccionar un Proceso.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        EstacionRecibo estacionReciboDe = new EstacionRecibo();
        estacionReciboDe.setIdEstacion(id);
        estacionReciboDe.setNumeroProceso(Long.parseLong(idProceso));
        
        String idA = request.getParameter(CEstacionRecibo.ID_ESTACION_RECIBO + "A");
        if ((idA == null) || idA.equals("SIN_SELECCIONAR")) {
            exception.addError("Debe seleccionar una Estación Destino.");
        }
        
        String idProcesoA = request.getParameter(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO + "A");
        if ((idProcesoA == null) || idProcesoA.equals("SIN_SELECCIONAR")) {
            exception.addError("Debe seleccionar un Proceso Destino.");
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        EstacionRecibo estacionReciboA = new EstacionRecibo();
        estacionReciboA.setIdEstacion(idA);
        estacionReciboA.setNumeroProceso(Long.parseLong(idProcesoA));
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        //validacion de que la estacion que recibe el serial ya haya terminado sus recibos
        boolean correcto = true;
        boolean existeEstacionReciboOrigen = false;
        boolean existeEstacionReciboDestino = false;
        
        List tipos = (List) session.getAttribute(AWAdministracionHermod.LISTA_CIRCULO_ESTACIONES_RECIBO);
        for (Iterator iter = tipos.iterator(); iter.hasNext();) {
            EstacionRecibo dato = (EstacionRecibo) iter.next();
            
            if (dato.getIdEstacion().equals(id) && dato.getNumeroProceso() == Long.parseLong(idProceso)) {
                existeEstacionReciboOrigen = true;
            }
            
            if (dato.getIdEstacion().equals(idA) && dato.getNumeroProceso() == Long.parseLong(idProcesoA)) {
                existeEstacionReciboDestino = true;
            }
            
            if (dato.getIdEstacion().equals(idA) && dato.getNumeroProceso() == Long.parseLong(idProcesoA)) {
                
                if (dato.getNumeroFinal() != dato.getUltimoNumero()) {
                    correcto = false;
                }
                
                if (dato.getUltimoNumero() == CEstacionRecibo.NUMERO_INVALIDO_RECIBO) {
                    correcto = true;
                }
            }
        }
        
        if (!correcto) {
            exception.addError("La Estación destino, no ha terminado sus recibos");
        }
        
        if (!existeEstacionReciboOrigen) {
            exception.addError("La Estación origen, no tiene configurado el número de proceso dado.");
        }
        
        if (!existeEstacionReciboDestino) {
            exception.addError("La Estación destino, no tiene configurado el número de proceso dado, se debe realizar primero el proceso de Agregar está configuración.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        gov.sir.core.negocio.modelo.Usuario user = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.TRASPASO_ESTACION_RECIBO);
        evento.setEstacionRecibo(estacionReciboDe);
        evento.setEstacionReciboA(estacionReciboA);
        evento.setCirculo(circulo);
        evento.setUsuarioNeg(user);
        return evento;
        
    }

    /**
     * Genera un evento para la consulta de las estaciones de un círculo además
     * de las estaciones / Recibo por círculo
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaEstacionesReciboPorCirculo(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        ValidacionAdicionEstacionReciboException exception
                = new ValidacionAdicionEstacionReciboException();
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un Circulo.");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO);
        
        evento.setCirculo(circulo);
        return evento;
        
    }

    /**
     * Genera un evento para la consulta de las estaciones de un círculo además
     * de las estaciones / Recibo por círculo
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaEstacion(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        String idEstacion = request.getParameter(CEstacionRecibo.ID_ESTACION_RECIBO);
        //request.getSession().setAttribute("ESTACION",idEstacion);
        //session.setAttribute("ESTACION",idEstacion);
        session.setAttribute(CEstacionRecibo.ID_ESTACION_RECIBO, idEstacion);
        session.setAttribute("ESTACION", idEstacion);
        session.setAttribute("PROCESO", "0"); //se pone recibo por default

        //session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
        
        return null;
        
    }

    /**
     * Genera un evento para la edición del último valor de una EstacionRecibo
     * Toma en cuenta la estación disponible en la sesión. Este método se creo
     * con el fin que los cajeros puedan modificar en algún momento el valor del
     * número actual del recibo (En casos como por ejemplo reimpresiones)
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnAdministracionHermod resetUltimoValorEstacionRecibo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        session.removeAttribute(AWAdministracionHermod.ESTACION_RECIBO_EDIT_ULTIMO_VALOR_OK);
        
        ValidacionResetEstacionReciboException exception = new ValidacionResetEstacionReciboException();
        
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        if (estacion == null) {
            exception.addError(
                    "En el momento no tiene disponible una Estación para la edición del su último valor de recibo");
        }
        
        String numeroActual = request.getParameter(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
        if (numeroActual == null || numeroActual.trim().length() == 0) {
            exception.addError("Debe Proporcionar un valor para el nuevo número de recibo actual.");
        }
        
        long valorActual = 0;
        try {
            valorActual = Long.parseLong(numeroActual);
            
        } catch (NumberFormatException e) {
            exception.addError(numeroActual + " no es un valor numérico válido.");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        EstacionRecibo dato = new EstacionRecibo();
        dato.setIdEstacion(estacion.getEstacionId());
        dato.setUltimoNumero(valorActual);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO);
        
        evento.setEstacionRecibo(dato);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para procesar un evento de edición de un
     * causal de restitución.
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * causal de restitución.
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editarCausalRestitucion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCausalRestitucionException exception = new ValidacionCausalRestitucionException();

        //Obtener el identificador del causal y subirlo a la sesión.
        String id = request.getParameter(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe proporcionar un identificador para el Causal de Restitución.");
        } else {
            session.setAttribute(CCausalRestitucion.ID_CAUSAL_RESTITUCION, id);
        }
        
        String nombre = request.getParameter(CCausalRestitucion.CAUSAL_RESTITUCION_NOMBRE);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            exception.addError("Debe proporcionar un nombre para el Causal de Restitución.");
        } else {
            session.setAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_NOMBRE, nombre);
        }
        
        String descripcion = request.getParameter(CCausalRestitucion.CAUSAL_RESTITUCION_DESCRIPCION);
        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            exception.addError("Debe proporcionar una descripción para el Causal de Restitución.");
        } else {
            session.setAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_DESCRIPCION, descripcion);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        CausalRestitucion dato = new CausalRestitucion();
        dato.setIdCausalRestitucion(id);
        dato.setNombre(nombre);
        dato.setDescripcion(descripcion);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.EDITAR_CAUSAL_RESTITUCION);
        evento.setCausalRestitucion(dato);
        
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para procesar un evento de edición de un
     * causal de restitución.
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * causal de restitución.
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editarDetallesCausalRestitucion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCausalRestitucionException exception = new ValidacionCausalRestitucionException();

        //Obtener el identificador del causal y subirlo a la sesión.
        String id = request.getParameter(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe proporcionar un identificador para el Causal de Restitución.");
        } else {
            session.setAttribute(CCausalRestitucion.ID_CAUSAL_RESTITUCION, id);
        }
        
        String nombre = request.getParameter(CCausalRestitucion.CAUSAL_RESTITUCION_NOMBRE);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            exception.addError("Debe proporcionar un nombre para el Causal de Restitución.");
        } else {
            session.setAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_NOMBRE, nombre);
        }
        
        String descripcion = request.getParameter(CCausalRestitucion.CAUSAL_RESTITUCION_DESCRIPCION);
        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            exception.addError("Debe proporcionar una descripción para el Causal de Restitución.");
        } else {
            session.setAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_DESCRIPCION, descripcion);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        CausalRestitucion dato = new CausalRestitucion();
        dato.setIdCausalRestitucion(id);
        dato.setNombre(nombre);
        dato.setDescripcion(descripcion);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.EDITAR_DETALLES_CAUSAL_RESTITUCION);
        evento.setCausalRestitucion(dato);
        
        return evento;
    }

    /**
     * Genera un evento para la consulta del último valor de recibo de una
     * EstacionRecibo Toma en cuenta la estación disponible en la sesión. Este
     * método se creo con el fin que los cajeros puedan modificar en algún
     * momento el valor del número actual del recibo (En casos como por ejemplo
     * reimpresiones)
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaEstacionRecibo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        session.removeAttribute(AWAdministracionHermod.ESTACION_RECIBO_EDIT_ULTIMO_VALOR_OK);
        
        ValidacionResetEstacionReciboException exception = new ValidacionResetEstacionReciboException();
        
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        if (estacion == null) {
            exception.addError(
                    "En el momento no tiene disponible una Estación para la edición del su último valor de recibo");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        EstacionRecibo dato = new EstacionRecibo();
        dato.setIdEstacion(estacion.getEstacionId());
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_ESTACION_RECIBO);
        
        evento.setEstacionRecibo(dato);
        return evento;
    }

    /**
     * Genera un evento para la consulta de los tipos de tarifa configurados por
     * círculo
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaTiposTarifaPorCirculo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        ValidacionTipoTarifaPorCirculoException exception
                = new ValidacionTipoTarifaPorCirculoException();
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un Circulo.");
            session.removeAttribute(CCirculo.ID_CIRCULO);
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTA_TIPOS_TARIFA_POR_CIRCULO);
        
        evento.setCirculo(circulo);
        return evento;
    }

    /**
     * Genera un evento para la consulta de los tipos de tarifa configurados por
     * círculo
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTarifaPorCirculo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        ValidacionTipoTarifaPorCirculoException exception
                = new ValidacionTipoTarifaPorCirculoException();
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un Circulo.");
            session.removeAttribute(CCirculo.ID_CIRCULO);
        }
        
        String tipoTarifa = request.getParameter(CTipoTarifa.ID_TIPO_TARIFA);
        if ((tipoTarifa == null)
                || (tipoTarifa.trim().length() == 0)
                || tipoTarifa.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un tipo de tarifa.");
            session.removeAttribute(CTipoTarifa.ID_TIPO_TARIFA);
        }
        tipoTarifa = tipoTarifa.toUpperCase();
        
        String valorStr = request.getParameter(CTarifa.VALOR_TARIFA);
        if ((valorStr == null) || (valorStr.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un valor para la tarifa.");
        }
        
        double valor = 0.0d;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            exception.addError("No es un valor válido para la tarifa.");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        Tarifa dato = new Tarifa();
        dato.setValor(valor);
        dato.setIdTipo(tipoTarifa);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_TARIFA_POR_CIRCULO);
        evento.setTarifa(dato);
        evento.setCirculo(circulo);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * una Estación Recibo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * Estación Recibo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaEstacionRecibo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionAdicionEstacionReciboException exception
                = new ValidacionAdicionEstacionReciboException();
        
        String id = request.getParameter(CEstacionRecibo.ID_ESTACION_RECIBO);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe Proporcionar una Estación.");
        }
        
        String idNumeroProceso = request.getParameter(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
        if ((idNumeroProceso == null) || (idNumeroProceso.trim().length() == 0)) {
            exception.addError("Debe Proporcionar una Estación y el Proceso.");
        }
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EstacionRecibo dato = new EstacionRecibo();
        dato.setIdEstacion(id);
        dato.setNumeroProceso(Long.parseLong(idNumeroProceso));
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_ESTACION_RECIBO);
        evento.setEstacionRecibo(dato);
        evento.setCirculo(circulo);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Método para consultar una minuta a partir del id del turno.
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultarMinuta(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        String dato = request.getParameter(CMinuta.SOLICITUD_ID);
        if (dato != null) {
            dato = dato.toUpperCase();
        }
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_MINUTA);
        evento.setTurnoMinutaId(dato);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Método para actualizar la minuta en el proceso de reparto notarial de
     * minutas
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnAdministracionHermod actualizarMinuta(HttpServletRequest request)
            throws AccionWebException {

        /*List validacionCiudadano = new ArrayList();
		List validacionEntidad = new ArrayList();
		boolean validacionCiudadanoA = true;
		boolean validacionEntidadA = true;
		boolean bunidades = false;
		boolean bcuantia = false;

		//Se recibe la información que viene del formulario.
		String idSolicitud = request.getParameter(CMinuta.SOLICITUD_ID);
		String tipoAccionNotarial = request.getParameter(CMinuta.TIPO_ACCION_NOTARIAL);
		String cuantia = request.getParameter(CMinuta.CUANTIA);
		String unidades = request.getParameter(CMinuta.UNIDADES);

		String veredaValorDepartamento = request.getParameter(CMinuta.VEREDA_ID_ID_DEPTO);
		String veredaNomDepartamento = request.getParameter(CMinuta.VEREDA_ID_NOM_DEPTO);
		String veredaValorMunicipio = request.getParameter(CMinuta.VEREDA_ID_ID_MUNIC);
		String veredaNomMunicipio = request.getParameter(CMinuta.VEREDA_ID_NOM_MUNIC);
		String veredaValorVereda = request.getParameter(CMinuta.VEREDA_ID_ID_VEREDA);
		String veredaNomVereda = request.getParameter(CMinuta.VEREDA_ID_NOM_VEREDA);

		String valorDepartamento =
			request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
		String nomDepartamento =
			request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
		String valorMunicipio =
			request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
		String nomMunicipio =
			request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
		String valorVereda =
			request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
		String nomVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
		String idOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);

		if (valorVereda == null || nomDepartamento.equals("") || nomMunicipio.equals("")) {
			valorVereda =
				ValidacionDatosOficinaOrigen.obtenerVereda(
					(List) request.getSession().getServletContext().getAttribute(
						WebKeys.LISTA_DEPARTAMENTOS),
					valorDepartamento,
					valorMunicipio);
		}

		String valorDepartamentoA =
			request.getParameter(
				AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO + CSolicitudRepartoNotarial.A);
		String nomDepartamentoA =
			request.getParameter(
				AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO + CSolicitudRepartoNotarial.A);
		String valorMunicipioA =
			request.getParameter(
				AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC + CSolicitudRepartoNotarial.A);
		String nomMunicipioA =
			request.getParameter(
				AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC + CSolicitudRepartoNotarial.A);
		String valorVeredaA =
			request.getParameter(
				AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA + CSolicitudRepartoNotarial.A);
		String nomVeredaA =
			request.getParameter(
				AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA + CSolicitudRepartoNotarial.A);
		String idOficinaA =
			request.getParameter(
				AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA + CSolicitudRepartoNotarial.A);

		if (valorVeredaA == null || nomDepartamentoA.equals("") || nomMunicipioA.equals("")) {
			valorVeredaA =
				ValidacionDatosOficinaOrigen.obtenerVereda(
					(List) request.getSession().getServletContext().getAttribute(
						WebKeys.LISTA_DEPARTAMENTOS),
					valorDepartamentoA,
					valorMunicipioA);
		}

		String tipoId = request.getParameter(CCiudadano.TIPODOC);
		String numId = request.getParameter(CCiudadano.DOCUMENTO);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String nombres = request.getParameter(CCiudadano.NOMBRE);

		//Se detectan las excepciones de validación
		ValidacionEditarMinutaException exception = new ValidacionEditarMinutaException();

		if ((cuantia.length() <= 0) && (unidades.length() <= 0)) {
			exception.addError("Debe ingresar el valor de la cuantía o de las unidades");
		} else {
			int numeroErrores = 0;
			try {
				Double.parseDouble(cuantia);
				bcuantia = true;
			} catch (Exception e) {
				numeroErrores++;
			}
			try {
				Double.parseDouble(unidades);
				bunidades = true;
			} catch (Exception e) {
				numeroErrores++;
			}
			if (numeroErrores == 2) {
				exception.addError(
					"Debe ingresar valores correctos para el valor de la cuantía o para el valor de las unidades");
			}
		}
		if ((tipoAccionNotarial == null) || tipoAccionNotarial.equals("SIN_SELECCIONAR")) {
			exception.addError("El tipo de acción notarial es inválido");
		}

		if ((veredaValorDepartamento.length() <= 0)) {
			exception.addError("Debe seleccionar un departamento válido para la vereda");
		}
		if ((veredaValorMunicipio.length() <= 0)) {
			exception.addError("Debe seleccionar un municipio válido para la vereda");
		}
		if ((veredaValorVereda.length() <= 0)) {
			exception.addError("Debe seleccionar una vereda válida para la vereda");
		}

		if ((valorDepartamento.length() <= 0)) {
			exception.addError("Debe seleccionar un departamento válido para la oficina de procedencia");
		}
		if ((valorMunicipio.length() <= 0)) {
			exception.addError("Debe seleccionar un municipio válido para la oficina de procedencia");
		}
		if ((valorVereda.length() <= 0)) {
			exception.addError("Debe seleccionar una vereda válida para la oficina de procedencia");
		}
		String numOficina = new String();
		String nomOficina = new String();
		if ((idOficina == null) || (idOficina.length() <= 0)) {
			exception.addError(
				"Debe seleccionar una entidad pública válida asociada al reparto notarial");
		} else {
			nomOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
			numOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
		}

		//Validación ciudadano
		if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
			validacionCiudadano.add("Debe seleccionar un tipo de identificación para el ciudadano");
			validacionCiudadanoA = false;
		} else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				validacionCiudadano.add("Debe ingresar el primer apellido del Ciudadano");
				validacionCiudadanoA = false;
			}
		} else if (tipoId.equals(COPLookupCodes.NIT)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				validacionCiudadano.add("Debe ingresar el primer apellido del Ciudadano");
				validacionCiudadanoA = false;
			}
		} else {
			double valorId = 0.0d;
			if (numId == null || numId.trim().equals("")) {
				validacionCiudadano.add("Debe ingresar el número de identificacion del Ciudadano");
				validacionCiudadanoA = false;
			} else {
				if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
					try {
						valorId = Double.parseDouble(numId);
						if (valorId <= 0) {
							validacionCiudadano.add(
								"El valor del documento no puede ser negativo o cero");
							validacionCiudadanoA = false;
						}
					} catch (NumberFormatException e) {
						validacionCiudadano.add(
							"El número de identificación de la persona es inválido. No puede ser alfanumérico");
						validacionCiudadanoA = false;
					}
				}
			}
			if (nombres == null || nombres.trim().equals("")) {
				validacionCiudadano.add("Debe ingresar el nombre del Ciudadano");
				validacionCiudadanoA = false;
			}
			if (apellido1 == null || apellido1.trim().equals("")) {
				validacionCiudadano.add("Debe ingresar el primer apellido del Ciudadano");
				validacionCiudadanoA = false;
			}
		}

		//Validación Segunda Entidad.
		if ((valorDepartamentoA.length() <= 0)) {
			validacionEntidad.add(
				"Debe seleccionar un departamento válido para la entidad pública asociada");
			validacionEntidadA = false;
		}
		if ((valorMunicipioA.length() <= 0)) {
			validacionEntidad.add(
				"Debe seleccionar un municipio válido para la entidad pública asociada");
			validacionEntidadA = false;
		}
		if ((valorVeredaA.length() <= 0)) {
			validacionEntidad.add("Debe seleccionar una vereda válida para la entidad pública asociada");
			validacionEntidadA = false;
		}
		String numOficinaA = new String();
		String nomOficinaA = new String();
		if ((idOficinaA == null) || (idOficinaA.length() <= 0)) {
			validacionEntidad.add("Debe seleccionar una entidad pública válida");
			validacionEntidadA = false;
		} else {
			nomOficinaA =
				request.getParameter(
					AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO + CSolicitudRepartoNotarial.A);
			numOficinaA =
				request.getParameter(
					AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM + CSolicitudRepartoNotarial.A);
		}

		if (!validacionCiudadanoA && !validacionEntidadA) {
			exception.addError(
				"INGRESE INFORMACIÓN CORRECTA PARA EL CIUDADANO O ENTIDAD PÚBLICA ASOCIADO AL REPARTO NOTARIAL");
			exception.addError("PARA LA ENTIDAD PÚBLICA:");
			Iterator it = validacionEntidad.iterator();
			while (it.hasNext()) {
				exception.addError((String) it.next());
			}

			exception.addError("PARA EL CIUDADANO:");
			it = validacionCiudadano.iterator();
			while (it.hasNext()) {
				exception.addError((String) it.next());
			}
		}

		//Los datos se suben a la sesión para que se guarde la información si ocurre algún error, se borran al momento de cargar nuevamente el menú.
		request.getSession().setAttribute(CMinuta.SOLICITUD_ID, idSolicitud);
		request.getSession().setAttribute(CMinuta.TIPO_ACCION_NOTARIAL, tipoAccionNotarial);
		request.getSession().setAttribute(CMinuta.CUANTIA, cuantia);
		request.getSession().setAttribute(CMinuta.UNIDADES, unidades);

		request.getSession().setAttribute(CMinuta.VEREDA_ID_ID_DEPTO, veredaValorDepartamento);
		request.getSession().setAttribute(CMinuta.VEREDA_ID_NOM_DEPTO, veredaNomDepartamento);
		request.getSession().setAttribute(CMinuta.VEREDA_ID_ID_MUNIC, veredaValorMunicipio);
		request.getSession().setAttribute(CMinuta.VEREDA_ID_NOM_MUNIC, veredaNomMunicipio);
		request.getSession().setAttribute(CMinuta.VEREDA_ID_ID_VEREDA, veredaValorVereda);
		request.getSession().setAttribute(CMinuta.VEREDA_ID_NOM_VEREDA, veredaNomVereda);

		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,
			valorDepartamento);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,
			nomDepartamento);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,
			valorMunicipio);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,
			nomMunicipio);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,
			valorVereda);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,
			nomVereda);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
			idOficina);
		request.getSession().setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, nomOficina);
		request.getSession().setAttribute(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, numOficina);

		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO + CSolicitudRepartoNotarial.A,
			valorDepartamentoA);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO + CSolicitudRepartoNotarial.A,
			nomDepartamentoA);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC + CSolicitudRepartoNotarial.A,
			valorMunicipioA);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC + CSolicitudRepartoNotarial.A,
			nomMunicipioA);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA + CSolicitudRepartoNotarial.A,
			valorVeredaA);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA + CSolicitudRepartoNotarial.A,
			nomVeredaA);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA + CSolicitudRepartoNotarial.A,
			idOficinaA);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO + CSolicitudRepartoNotarial.A,
			nomOficinaA);
		request.getSession().setAttribute(
			AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM + CSolicitudRepartoNotarial.A,
			numOficinaA);

		request.getSession().setAttribute(CCiudadano.TIPODOC, tipoId);
		request.getSession().setAttribute(CCiudadano.DOCUMENTO, numId);
		request.getSession().setAttribute(CCiudadano.APELLIDO1, apellido1);
		request.getSession().setAttribute(CCiudadano.APELLIDO2, apellido2);
		request.getSession().setAttribute(CCiudadano.NOMBRE, nombres);

		//Se lanzan los errores si existieron en la validación.
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		//Datos de la sesión.
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		//Se llenan los datos de la oficina de origen.
		OficinaOrigen oficinaOrigen = new OficinaOrigen();
		oficinaOrigen.setIdOficinaOrigen(idOficina);
		oficinaOrigen.setNumero(numOficina);
		oficinaOrigen.setNombre(nomOficina);

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
		oficinaOrigen.setVereda(vereda);

		//Se llenan los datos del segundo otorgante.
		OficinaOrigen oficinaOrigenA = new OficinaOrigen();
		if (validacionEntidadA) {
			oficinaOrigenA.setIdOficinaOrigen(idOficinaA);
			oficinaOrigenA.setNumero(numOficinaA);
			oficinaOrigenA.setNombre(nomOficinaA);

			Departamento departamentoA = new Departamento();
			departamentoA.setIdDepartamento(valorDepartamentoA);
			departamentoA.setNombre(nomDepartamentoA);

			Municipio municipioA = new Municipio();
			municipioA.setIdMunicipio(valorMunicipioA);
			municipioA.setNombre(nomMunicipioA);
			municipioA.setDepartamento(departamentoA);

			Vereda veredaA = new Vereda();
			veredaA.setIdDepartamento(valorDepartamentoA);
			veredaA.setIdMunicipio(valorMunicipioA);
			veredaA.setIdVereda(valorVeredaA);
			veredaA.setNombre(nomVeredaA);
			veredaA.setMunicipio(municipioA);
			oficinaOrigenA.setVereda(veredaA);
		}

		//Se llena el ciudadano
		Ciudadano ciudadano = new Ciudadano();
		if (validacionCiudadanoA) {
			ciudadano.setTipoDoc(tipoId);
			ciudadano.setDocumento(numId);
			ciudadano.setIdCiudadano(null);
			ciudadano.setApellido1(apellido1);
			ciudadano.setApellido2(apellido2);
			ciudadano.setNombre(nombres);
		}

		//Se llenan los datos de la vereda.
		Departamento vdepartamento = new Departamento();
		vdepartamento.setIdDepartamento(veredaValorDepartamento);
		vdepartamento.setNombre(veredaNomDepartamento);

		Municipio vmunicipio = new Municipio();
		vmunicipio.setIdMunicipio(veredaValorMunicipio);
		vmunicipio.setNombre(veredaNomMunicipio);
		vmunicipio.setDepartamento(vdepartamento);

		Vereda vvereda = new Vereda();
		vvereda.setIdDepartamento(veredaValorDepartamento);
		vvereda.setIdMunicipio(veredaValorMunicipio);
		vvereda.setIdVereda(veredaValorVereda);
		vvereda.setNombre(veredaNomVereda);
		vvereda.setMunicipio(vmunicipio);

		//Se llena la accion notarial
		AccionNotarial accionNotarial = new AccionNotarial();
		accionNotarial.setIdAccionNotarial(tipoAccionNotarial);

		//Se llena la minuta
		Minuta minuta = new Minuta();
		//minuta.setIdSolicitud(idSolicitud);
		//minuta.setAnulado(false);
		if (bcuantia) {
			minuta.setValor(new BigDecimal(cuantia).doubleValue());
		}
		if (bunidades) {
			//minuta.setUnidades(new BigDecimal(unidades).doubleValue());
		}
		minuta.setAccionNotarial(accionNotarial);
		//minuta.setOtorgante1Oficina(oficinaOrigen);
		if (validacionEntidadA) {
			//minuta.setOtorgante2Oficina(oficinaOrigenA);
		}
		if (validacionCiudadanoA) {
			//minuta.setOtorgante2Ciudadano(ciudadano);
		}

		EvnAdministracionHermod evento =
			new EvnAdministracionHermod(usuario, EvnAdministracionHermod.EDITAR_MINUTA);
		evento.setMinuta(minuta);
		return evento;*/
        return null;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores de un CirculoTipoPago del
     * <code>HttpServletRequest</code>
     *
     * @param request
     * @return circulotipopago <code>CirculoTipoPago</code>
     * @throws AccionWebException
     */
    private CirculoTipoPago getCirculoTipoPago(
            HttpServletRequest request,
            ValidacionCirculoTipoPagoException exception, boolean accion) {
        
        System.out.println("nuevos cambios 2");
        
        String idCirculoTipoPago = request.getParameter(WebKeys.CIRCULO_CANAL_TIPO_PAGO);
        System.out.println("ID PARA GUARDAR CANAL FORMA PAGO " + idCirculoTipoPago);
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un Circulo.");
        }
        
        TipoPago tipoPago = new TipoPago();
        CanalesRecaudo canalesRecaudo = new CanalesRecaudo();
        CuentasBancarias cuentasBancarias = new CuentasBancarias();

        //if (accion) {
        System.out.println("voy a guardar");
        String idTipoDocPago = request.getParameter(CTipoPago.ID_TIPO_PAGO);
        if ((idTipoDocPago == null) || (idTipoDocPago.trim().length() == 0) || idTipoDocPago.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar una Forma de Pago.");
        }
        
        String idCanalRecaudo = request.getParameter(CCanalesRecaudo.ID_CANAL_RECAUDO);
        System.out.println("ID CANAL RECAUDO " + idCanalRecaudo);
        if ((idCanalRecaudo == null) || (idCanalRecaudo.trim().length() == 0) || idCanalRecaudo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un Canal de Recaudo.");
        }

        /*canalesRecaudo.setIdCanal(Integer.parseInt(idCanalRecaudo));*/
        String cuentasBancariasSize = request.getParameter(WebKeys.LISTA_CUENTAS_BANCARIAS_CIRCULO);
        
        if (Integer.parseInt(cuentasBancariasSize) > 0) {
            String idCuentaBancaria = request.getParameter(CCuentasBancarias.ID_CUENTA);
            if ((idCuentaBancaria == null) || (idCuentaBancaria.trim().length() == 0) || idCuentaBancaria.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe Proporcionar una Cuenta Bancaria.");
            }
        }
        
        tipoPago.setIdTipoDocPago(Long.parseLong(idTipoDocPago));

        /* } else {

            long idCTDP = 0;
            try {
                idCTDP = Long.parseLong(idCirculoTipoPago);
            } catch (NumberFormatException e) {
                exception.addError(idCirculoTipoPago + " no es un identificador de Canal y Forma de Pago por círculo válido.");
            }*/
        //}
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);

        /*
        

       
        if (idCuentaBancaria == null) {
            cuentasBancarias.setId(0);
        } else {
            cuentasBancarias.setId(Integer.parseInt(idCuentaBancaria));
        }*/
        CirculoTipoPago dato = new CirculoTipoPago();
        dato.setCirculo(circulo);
        dato.setTipoPago(tipoPago);
        dato.setIdCirculoTipoPago(idCirculoTipoPago);
        /*dato.setCanalesRecaudo(canalesRecaudo);
        dato.setCuentasBancarias(cuentasBancarias);*/
        
        return dato;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * CirculoTipoPago
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * CirculoTipoPago
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaCirculoTipoPago(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionCirculoTipoPagoException exception = new ValidacionCirculoTipoPagoException();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        //CirculoTipoPago dato = getCirculoTipoPago(request, exception, true);
        CirculoTipoPago dato = new CirculoTipoPago();

        /*Desde aca geremias*/
        //String idCirculoTipoPago = request.getParameter(WebKeys.CIRCULO_CANAL_TIPO_PAGO);
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un Circulo.");
        }
        
        String idTipoDocPago = request.getParameter(CTipoPago.ID_TIPO_PAGO);
        if ((idTipoDocPago == null) || (idTipoDocPago.trim().length() == 0) || idTipoDocPago.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar una Forma de Pago.");
        }
        
        String idCanalRecaudo = request.getParameter(CCanalesRecaudo.ID_CANAL_RECAUDO);
        System.out.println("ID CANAL RECAUDO " + idCanalRecaudo);
        if ((idCanalRecaudo == null) || (idCanalRecaudo.trim().length() == 0) || idCanalRecaudo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un Canal de Recaudo.");
        }
        
        String cuentasBancariasSize = request.getParameter(WebKeys.LISTA_CUENTAS_BANCARIAS_CIRCULO);
        
        String idCuentaBancaria = "";
        
        if (Integer.parseInt(cuentasBancariasSize) > 0) {
            idCuentaBancaria = request.getParameter(CCuentasBancarias.ID_CUENTA);
            if ((idCuentaBancaria == null) || (idCuentaBancaria.trim().length() == 0) || idCuentaBancaria.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe Proporcionar una Cuenta Bancaria.");
            }
        }
        
        boolean canalSirActivo = false;
        
        String isCanalSir = request.getParameter(CTipoPago.CANAL_SIR);
        if (isCanalSir != null) {
            canalSirActivo = true;
        }


        /*hasta aca geremias*/
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        TipoPago tipoPago = new TipoPago();
        tipoPago.setIdTipoDocPago(Long.parseLong(idTipoDocPago));
        
        CanalesRecaudo canalesRecaudo = new CanalesRecaudo();
        canalesRecaudo.setIdCanal(Integer.parseInt(idCanalRecaudo));
        
        CuentasBancarias cuentasBancarias = new CuentasBancarias();
        if (idCuentaBancaria.trim().length() == 0) {
            
            System.out.println("TAMANO ID CUENTA " + idCuentaBancaria.trim().length());
            dato.setIdCuentaBancaria(null);
            cuentasBancarias = null;
        } else {
            
            cuentasBancarias.setId(Integer.parseInt(idCuentaBancaria));
            dato.setIdCuentaBancaria(idCuentaBancaria);
        }
        
        dato.setCirculo(circulo);
        dato.setTipoPago(tipoPago);
        //dato.setIdCirculoTipoPago(idCirculoTipoPago);
        dato.setCanalesRecaudo(canalesRecaudo);
        dato.setCuentasBancarias(cuentasBancarias);
        dato.setCtpActivo(true);
        dato.setCanalSir(canalSirActivo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO);
        evento.setCirculoTipoPago(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un CirculoTipoPago
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * CirculoTipoPago
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaCirculoTipoPago(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionCirculoTipoPagoException exception = new ValidacionCirculoTipoPagoException();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        //CirculoTipoPago dato = getCirculoTipoPago(request, exception);

        String idCirculoTipoPago = request.getParameter(WebKeys.CIRCULO_CANAL_TIPO_PAGO);
        System.out.println("ID PARA GUARDAR CANAL FORMA PAGO " + idCirculoTipoPago);
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un Circulo.");
        }
        
        long idCTP = 0;
        try {
            idCTP = Long.parseLong(idCirculoTipoPago);
        } catch (NumberFormatException e) {
            exception.addError(idCirculoTipoPago + " no es un identificador de Tipo de Pago v?lido.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        CirculoTipoPago dato = new CirculoTipoPago();
        dato.setCirculo(circulo);
        dato.setIdCirculoTipoPago(idCirculoTipoPago);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_CIRCULO_TIPO_PAGO);
        evento.setCirculoTipoPago(dato);
        
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de selección de un
     * CirculoTipoPago
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * CirculoTipoPago a seleccionar
     * @throws AccionWebException
     */
    private EvnAdministracionHermod seleccionaCirculoTipoPago(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionCirculoTipoPagoException exception = new ValidacionCirculoTipoPagoException();
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        session.removeAttribute(CTipoPago.ID_TIPO_PAGO);
        session.removeAttribute(CCanalesRecaudo.ID_CANAL_RECAUDO);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.SELECCIONA_CIRCULO_TIPO_PAGO);
        evento.setCirculo(circulo);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * abogados por círculo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * Circulo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaAbogadosCirculo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionAbogadosSubtiposAtencionException exception
                = new ValidacionAbogadosSubtiposAtencionException();
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO);
        evento.setCirculo(circulo);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de actualizacón de
     * los datos de subtipos de atención de los abogados de un círculo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de
     * UsuarioSubtipoAtencion para los abogados
     * @throws AccionWebException
     */
    private EvnAdministracionHermod actualizaAbogadosSubtipoAtencion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionAbogadosSubtiposAtencionException exception
                = new ValidacionAbogadosSubtiposAtencionException();
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }
        
        String[] idsUsuariosArr = request.getParameterValues(CUsuarioSubtipoAtencion.USUARIO_SUBTIPO_ID);
        if (idsUsuariosArr == null) {
            exception.addError("Debe existir al menos un usuario para realizar la operación.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        List usuarios = new ArrayList();
        
        int i, j;
        for (i = 0; i < idsUsuariosArr.length; i++) {
            long idu = Long.parseLong(idsUsuariosArr[i]);
            gov.sir.core.negocio.modelo.Usuario usuarioNeg = new gov.sir.core.negocio.modelo.Usuario();
            usuarioNeg.setIdUsuario(idu);
            usuarios.add(usuarioNeg);
            
            String[] subtiposUsuarioArr
                    = request.getParameterValues(CUsuarioSubtipoAtencion.USUARIO_SUBTIPO_CHECK_ + idu);
            if (subtiposUsuarioArr == null) {
                continue;
            }
            for (j = 0; j < subtiposUsuarioArr.length; j++) {
                String idSubtipo = subtiposUsuarioArr[j];
                UsuarioSubtipoAtencion uSubtipo = new UsuarioSubtipoAtencion();
                uSubtipo.setIdSubtipoAtencion(idSubtipo);
                uSubtipo.setIdUsuario(idu);
                usuarioNeg.addSubtiposAtencion(uSubtipo);
            }
        }
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.ACTUALIZA_SUBTIPOS_ATENCION_USUARIO);
        evento.setCirculo(circulo);
        evento.setUsuariosNegocio(usuarios);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar un objeto <code>FirmaRegistrador</code>
     * de los parámetros provenientes en el  <code>HttpServletRequest</code>
     *
     * @param request
     * @return dato <code>FirmaRegistrador</code>
     * @throws AccionWebException
     */
    private FirmaRegistrador getCirculoFirmaRegistrador(
            HttpServletRequest request,
            ValidacionFirmaRegistradorException exception) {
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Proporcionar un Circulo.");
        }
        
        String archivo = request.getParameter(CFirmaRegistrador.ARCHIVO);
        if ((archivo == null) || (archivo.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un archivo de Firma de Registrador.");
        }
        String idFirmaRegistrador = (String) request.getParameter(CFirmaRegistrador.ID_FIRMA_REGISTRADOR);
        if ((idFirmaRegistrador == null) || (idFirmaRegistrador.trim().length() == 0)) {
            exception.addError("No fue posible obtener el ID de la firma de Registrador");
        }
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        FirmaRegistrador dato = new FirmaRegistrador();
        dato.setCirculo(circulo);
        dato.setIdArchivo(archivo);
        dato.setIdFirmaRegistrador(new Long(Long.parseLong(idFirmaRegistrador)));
        return dato;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de creación de una
     * firma de registrador
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * objeto FirmaRegistrador
     * @throws AccionWebException
     */
    private EvnAdministracionHermod activaFirmaRegistrador(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionFirmaRegistradorException exception = new ValidacionFirmaRegistradorException();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        FirmaRegistrador dato = getCirculoFirmaRegistrador(request, exception);
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        dato.setActivo(CFirmaRegistrador.ACTIVA);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR);
        evento.setFirmaRegistrador(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de inactivación de
     * una firma de registrador
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * objeto FirmaRegistrador
     * @throws AccionWebException
     */
    private EvnAdministracionHermod inactivaFirmaRegistrador(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionFirmaRegistradorException exception = new ValidacionFirmaRegistradorException();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        FirmaRegistrador dato = getCirculoFirmaRegistrador(request, exception);
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        dato.setActivo(CFirmaRegistrador.INACTIVA);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.INACTIVA_FIRMA_REGISTRADOR);
        evento.setFirmaRegistrador(dato);
        
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de inactivación de
     * una firma de registrador
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * objeto FirmaRegistrador
     * @throws AccionWebException
     */
    private EvnAdministracionHermod inactivaDefinitivamenteFirmaRegistrador(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionFirmaRegistradorException exception = new ValidacionFirmaRegistradorException();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        FirmaRegistrador dato = getCirculoFirmaRegistrador(request, exception);
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        dato.setActivo(CFirmaRegistrador.INACTIVA_DEFINITIVA);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR);
        evento.setFirmaRegistrador(dato);
        
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de selección del
     * conjunto de objetos FirmaRegistrador asociados a un círculo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * circulo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod seleccionaCirculoFirmaRegistrador(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionFirmaRegistradorException exception = new ValidacionFirmaRegistradorException();
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);

        /*
		Circulo circuloEnSesion = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		if (circuloEnSesion != null) {
			//si el círculo en la sesión es el mismo que está consultando toma los datos en memoria
			//sin consultar la capa de negocio.
			if (circuloEnSesion.getIdCirculo().equals(idCirculo)) {
				List firmas = circuloEnSesion.getFirmas();
				session.setAttribute(AWAdministracionHermod.LISTA_CIRCULO_FIRMAS_NOTARIALES, firmas);
				return null;
			}
		}
         */
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR);
        evento.setCirculo(circulo);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de selección del
     * conjunto de objetos FirmaRegistrador asociados a un círculo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * circulo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod buscarFirmasRegistradorPorCirculo(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionFirmaRegistradorException exception = new ValidacionFirmaRegistradorException();
        String idCirculo = (String) session.getAttribute(CCirculo.ID_CIRCULO);
        if ((idCirculo == null)
                || (idCirculo.trim().length() == 0)
                || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Circulo.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario,
                        EvnAdministracionHermod.BUSCAR_FIRMA_REGISTRADOR_CIRCULO);
        evento.setCirculo(circulo);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de selección del
     * conjunto de objetos FirmaRegistrador asociados a un círculo
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * circulo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod previewFirmaRegistrador(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionFirmaRegistradorException exception = new ValidacionFirmaRegistradorException();
        String nombreFirma = request.getParameter(AWAdministracionHermod.NOMBRE_FIRMA_REGISTRADOR);
        
        if (nombreFirma == null || nombreFirma.equals("")) {
            exception.addError("nombre de firma invalido.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        session.setAttribute(AWAdministracionHermod.NOMBRE_FIRMA_REGISTRADOR, nombreFirma);

        //Se buca el archivo que tiene ese nombre de firma
        List firmas = (List) session.getAttribute(AWAdministracionHermod.LISTA_FIRMAS_REGISTRADOR);
        Iterator it = firmas.iterator();
        File dest = null;
        File firma = null;
        boolean termino = false;
        while (it.hasNext() && !termino) {
            firma = (File) it.next();
            if (nombreFirma.equals(firma.getName())) {
                termino = true;
            }
        }
        String path;
        path = firma.getAbsolutePath();
        path = path.replace('\\', '/');
        Log.getInstance().debug(AWAdministracionHermod.class, "path : " + path);
        session.setAttribute("FILE_FIRMA", firma);
        
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para crear y
     * guardar la firma de registrador.
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * circulo
     * @throws AccionWebException
     */
    private EvnAdministracionHermod guardarFirmaRegistrador(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        ValidacionFirmaRegistradorException exception = new ValidacionFirmaRegistradorException();
        String nombreFirma = request.getParameter(AWAdministracionHermod.NOMBRE_FIRMA_REGISTRADOR);
        
        if (nombreFirma == null || nombreFirma.equals("")) {
            exception.addError("nombre de firma invalido.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        FirmaRegistrador firmaR = new FirmaRegistrador();
        firmaR.setIdArchivo(nombreFirma);

        //Se buca el archivo que tiene ese nombre de firma
        List firmas = (List) session.getAttribute(AWAdministracionHermod.LISTA_FIRMAS_REGISTRADOR);
        Iterator it = firmas.iterator();
        File firma = null;
        boolean termino = false;
        while (it.hasNext() && !termino) {
            firma = (File) it.next();
            if (nombreFirma.equals(firma.getName())) {
                termino = true;
            }
        }
        
        session.setAttribute(AWAdministracionHermod.FIRMA_REGISTRADOR, firmaR);
        session.setAttribute("CERRAR_VENTANA", new Boolean(true));

        /*
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionHermod evento =
			new EvnAdministracionHermod(usuario,
				EvnAdministracionHermod.GUARDAR_FIRMA_REGISTRADOR);
		evento.setFileFirmaRegistrador(firma);
		return evento;  */
        return null;
        
    }

    /**
     * Este método se encarga de limpiar los valores de la
     * <code>HttpSession</code> en la pantalla administracion de categorias de
     * reparto
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private void limpiarSesionCategoria(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        session.removeAttribute(CCategoria.ID_CATEGORIA);
        session.removeAttribute(CCategoria.NOMBRE_CATEGORIA);
        session.removeAttribute(CCategoria.VALOR_MAX_CATEGORIA);
        session.removeAttribute(CCategoria.VALOR_MIN_CATEGORIA);
        session.removeAttribute(CCategoria.UNIDAD_MAX_CATEGORIA);
        session.removeAttribute(CCategoria.UNIDAD_MIN_CATEGORIA);
        session.removeAttribute(CCategoria.CATEGORIA_ESTADO);
        session.removeAttribute(CCategoria.MODO_EDICION);
        
    }

    /**
     * Este método se encarga de limpiar los valores de la
     * <code>HttpSession</code> en la pantalla administracion firmas de
     * registrador
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private void limpiarSesionFirmasRegistrador(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        session.removeAttribute(AWAdministracionHermod.FIRMA_PREVIEW);
        session.removeAttribute(AWAdministracionHermod.FIRMA_REGISTRADOR);
        session.removeAttribute(AWAdministracionHermod.LISTA_FIRMAS_REGISTRADOR);
        session.removeAttribute(AWAdministracionHermod.LISTA_ELEMENTOS_FIRMAS_REGISTRADOR);
    }

    /**
     * Carga de los archivos de imagenes de las firmas en el Servidor
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private EvnAdministracionHermod uploadFileFirma(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        
        String contentType = (String) context.getAttribute(WebKeys.FIRMAS_CONTENT_TYPE);
        File directorioFirmas = (File) context.getAttribute(WebKeys.FIRMAS_DIRECTORY);
        
        String accion = null;
        String idCirculo = null;
        boolean estadoSeleccionado = false;
        String nombreArchivo = null;
        String nombreRegistrador = null;
        String idCargoRegistrador = null;
        
        ValidacionFirmaRegistradorException exception = new ValidacionFirmaRegistradorException();
        try {
            //	Create a new file upload handler
            DiskFileUpload upload = new DiskFileUpload();
            List list = upload.parseRequest(request);
            Iterator it = list.iterator();
            
            while (it.hasNext()) {
                FileItem fi = (FileItem) it.next();
                String campo = fi.getFieldName();
                
                if (!fi.isFormField()) {
                    //campo de archivo
                    String fileName = fi.getName();
                    
                    if (fileName != null && !fileName.trim().equals("")) {
                        Date fechaActual = new Date();
                        String extension = fileName.substring(fileName.lastIndexOf("."));
                        //para evitar nombres duplicados de archivos se toma la fecha del upload
                        fileName = "firma_" + fechaActual.getTime() + extension;
                        fileName = fileName.toUpperCase();
                        nombreArchivo = fileName;
                        
                        if (fi.getContentType().equals(contentType)) {
                            File imagen = new File(directorioFirmas, fileName);
                            if (imagen.createNewFile()) {
                                fi.write(imagen);
                            } else {
                                exception.addError("No se pudo crear el archivo " + fileName);
                            }
                        } else {
                            exception.addError(
                                    "El tipo de contenido "
                                    + fi.getContentType()
                                    + " no está autorizado en el sistema");
                        }
                    }
                } else {
                    //campo normal
                    if (campo.equals(WebKeys.ACCION)) {
                        accion = fi.getString();
                        continue;
                    } else if (campo.equals(CCirculo.ID_CIRCULO)) {
                        idCirculo = fi.getString();
                        continue;
                    } else if (campo.equals(CFirmaRegistrador.FIRMA_REGISTRADOR_ESTADO)) {
                        String estado = fi.getString();
                        estadoSeleccionado = (estado == null) ? false : true;
                        continue;
                    } else if (campo.equals(CFirmaRegistrador.FIRMA_REGISTRADOR_NOMBRE)) {
                        nombreRegistrador = fi.getString();
                        if ((nombreRegistrador == null) || (nombreRegistrador.trim().length() == 0)) {
                            exception.addError("Debe proporcionar el nombre del Registrador ");
                        }
                        continue;
                    } else if (campo.equals(CFirmaRegistrador.FIRMA_REGISTRADOR_CARGO)) {
                        idCargoRegistrador = fi.getString();
                        if ((idCargoRegistrador == null)
                                || (idCargoRegistrador.trim().length() == 0)
                                || idCargoRegistrador.equals(WebKeys.SIN_SELECCIONAR)) {
                            exception.addError("Debe proporcionar el cargo del Registrador ");
                        }
                        continue;
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace(System.out);
            throw new AccionWebException(
                    "Ocurrió un error al cargar el archivo en el servidor: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new AccionWebException(
                    "Ocurrió un error al cargar el archivo en el servidor: " + e.getMessage());
        }
        ///////////////
        if (accion.equals(AWAdministracionHermod.TERMINA)) {
            session.setAttribute(WebKeys.ACCION, accion);
            limpiaSesion(request);
            return null;
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        if ((idCirculo == null)
                || (idCirculo.trim().equals("") || idCirculo.equals(WebKeys.SIN_SELECCIONAR))) {
            exception.addError("Debe Seleccionar un Circulo.");
            
            if (accion.equals(AWAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR)) {
                throw exception;
            }
        }
        
        if (accion == null || accion.trim().equals("")) {
            exception.addError("Debe proporcionar una acción a realizar.");
        }

        /*
		if (!accion.equals(AWAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR)) {
			if (nombreArchivo == null || nombreArchivo.trim().equals("")) {
				exception.addError("Debe proporcionar un archivo gráfico de firma.");
			}
		}*/
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        session.setAttribute(WebKeys.ACCION, accion);
        
        if (exception.getErrores().size() > 0
                && !accion.equals(AWAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR)
                && !accion.equals(AWAdministracionHermod.RELOAD)) {
            throw exception;
        }
        
        FirmaRegistrador dato = new FirmaRegistrador();
        dato.setCirculo(circulo);
        dato.setIdArchivo(nombreArchivo);
        if (estadoSeleccionado) {
            dato.setActivo(CFirmaRegistrador.ACTIVA);
        } else {
            dato.setActivo(CFirmaRegistrador.INACTIVA);
        }
        dato.setNombreRegistrador(nombreRegistrador != null ? nombreRegistrador.toUpperCase() : nombreRegistrador);
        dato.setCargoRegistrador(idCargoRegistrador);
        
        if (accion.equals(AWAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR)) {
            FirmaRegistrador fRegistrador = (FirmaRegistrador) session.getAttribute(AWAdministracionHermod.FIRMA_REGISTRADOR);
            EvnAdministracionHermod evento
                    = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR);
            if (fRegistrador == null) {
                evento.setFirmaRegistrador(dato);
            } else {
                fRegistrador.setCirculo(circulo);
                if (estadoSeleccionado) {
                    fRegistrador.setActivo(CFirmaRegistrador.ACTIVA);
                } else {
                    fRegistrador.setActivo(CFirmaRegistrador.INACTIVA);
                }
                fRegistrador.setNombreRegistrador(nombreRegistrador != null ? nombreRegistrador.toUpperCase() : nombreRegistrador);
                fRegistrador.setCargoRegistrador(idCargoRegistrador);
                evento.setFirmaRegistrador(fRegistrador);
            }
            
            return evento;
        } else if (accion.equals(AWAdministracionHermod.RELOAD)) {
            FirmaRegistrador fRegistrador = (FirmaRegistrador) session.getAttribute(AWAdministracionHermod.FIRMA_REGISTRADOR);
            EvnAdministracionHermod evento
                    = new EvnAdministracionHermod(usuario);
            if (fRegistrador == null) {
                evento.setFirmaRegistrador(dato);
            } else {
                fRegistrador.setCirculo(circulo);
                if (estadoSeleccionado) {
                    fRegistrador.setActivo(CFirmaRegistrador.ACTIVA);
                } else {
                    fRegistrador.setActivo(CFirmaRegistrador.INACTIVA);
                }
                fRegistrador.setNombreRegistrador(nombreRegistrador != null ? nombreRegistrador.toUpperCase() : nombreRegistrador);
                fRegistrador.setCargoRegistrador(idCargoRegistrador);
                evento.setFirmaRegistrador(fRegistrador);
            }
            session.setAttribute(WebKeys.ACCION, AWAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR);
            return evento;
        } else if (accion.equals(AWAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR)) {
            EvnAdministracionHermod evento
                    = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR);
            evento.setFirmaRegistrador(dato);
            return evento;
        } else if (accion.equals(AWAdministracionHermod.INACTIVA_FIRMA_REGISTRADOR)) {
            EvnAdministracionHermod evento
                    = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.INACTIVA_FIRMA_REGISTRADOR);
            evento.setFirmaRegistrador(dato);
            return evento;
        } else if (accion.equals(AWAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR)) {
            EvnAdministracionHermod evento
                    = new EvnAdministracionHermod(
                            usuario,
                            EvnAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR);
            evento.setCirculo(circulo);
            return evento;
        } else {
            exception.addError(accion + " no es una acción válida.");
            throw exception;
        }
        
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de creación de una
     * validación nota
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * validación nota
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaValidacionNota(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionNota dato = getValidacionNota(request);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_VALIDACION_NOTA);
        evento.setValidacionNota(dato);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * una validación nota
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos de la
     * validación nota
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaValidacionNota(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionNota dato = getValidacionNota(request);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_VALIDACION_NOTA);
        evento.setValidacionNota(dato);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de las
     * fases relacionadas con un proceso
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * proceso
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaFaseProceso(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionValidaNotaException exception = new ValidacionValidaNotaException();
        
        String idProceso = request.getParameter(CProceso.PROCESO_ID);
        if ((idProceso == null)
                || (idProceso.trim().length() == 0)
                || idProceso.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idProceso);
        }
        
        long idPrc = 0;
        try {
            idPrc = Long.parseLong(idProceso);
        } catch (NumberFormatException e) {
            exception.addError(idProceso + " No es un valor numérico válido.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Proceso dato = new Proceso();
        dato.setIdProceso(idPrc);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_FASE_PROCESO);
        evento.setProceso(dato);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar una <code>ValidacionNota</code> del
     * <code>HttpServletRequest</code>
     *
     * @param request
     * @return dato <code>ValidacionNota</code>
     * @throws AccionWebException
     */
    private ValidacionNota getValidacionNota(HttpServletRequest request)
            throws ValidacionValidaNotaException {
        HttpSession session = request.getSession();
        
        ValidacionValidaNotaException exception = new ValidacionValidaNotaException();
        
        String idProceso = request.getParameter(CProceso.PROCESO_ID);
        if ((idProceso == null)
                || (idProceso.trim().length() == 0)
                || idProceso.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idProceso);
        }
        
        long idPrc = 0;
        try {
            idPrc = Long.parseLong(idProceso);
        } catch (NumberFormatException e) {
            exception.addError(idProceso + " No es un valor numérico válido.");
        }
        
        String idFaseProceso = request.getParameter(CFaseProceso.FASE_PROCESO_ID);
        if ((idFaseProceso == null)
                || (idFaseProceso.trim().length() == 0)
                || idFaseProceso.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar una Fase.");
        } else {
            session.setAttribute(CFaseProceso.FASE_PROCESO_ID, idFaseProceso);
        }
        
        String idRespuesta = request.getParameter(CValidacionNota.VALIDACION_NOTA_RESPUESTA);
        if ((idFaseProceso == null) || (idFaseProceso.trim().length() == 0)) {
            exception.addError("Debe Digitar una Respuesta.");
        } else {
            session.setAttribute(CValidacionNota.VALIDACION_NOTA_RESPUESTA, idRespuesta);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        ValidacionNota dato = new ValidacionNota();
        dato.setIdFase(idFaseProceso);
        dato.setIdProceso(idPrc);
        dato.setIdRespuesta(idRespuesta);
        
        return dato;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * causal de restitución
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * causal de restitución
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaCausalRestitucion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCausalRestitucionException exception = new ValidacionCausalRestitucionException();

        //String id = request.getParameter(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
        //if ((id == null) || (id.trim().length() == 0)) {
        //exception.addError("Debe proporcionar un identificador para el Causal de Restitución.");
        //} else {
        //session.setAttribute(CCausalRestitucion.ID_CAUSAL_RESTITUCION, id);
        //}
        String nombre = request.getParameter(CCausalRestitucion.CAUSAL_RESTITUCION_NOMBRE);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            exception.addError("Debe proporcionar un nombre para el Causal de Restitución.");
        } else {
            session.setAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_NOMBRE, nombre);
        }
        
        String descripcion = request.getParameter(CCausalRestitucion.CAUSAL_RESTITUCION_DESCRIPCION);
        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            exception.addError("Debe proporcionar una descripción para el Causal de Restitución.");
        } else {
            session.setAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_DESCRIPCION, descripcion);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        CausalRestitucion dato = new CausalRestitucion();
        //dato.setIdCausalRestitucion(id);
        dato.setNombre(nombre);
        dato.setDescripcion(descripcion);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_CAUSAL_RESTITUCION);
        evento.setCausalRestitucion(dato);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación de
     * un causal de restitución
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * causal de restitución
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaCausalRestitucion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCausalRestitucionException exception = new ValidacionCausalRestitucionException();
        
        String id = request.getParameter(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe proporcionar un identificador para el Causal de Restitución.");
        } else {
            session.setAttribute(CCausalRestitucion.ID_CAUSAL_RESTITUCION, id);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        CausalRestitucion dato = new CausalRestitucion();
        dato.setIdCausalRestitucion(id);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_CAUSAL_RESTITUCION);
        evento.setCausalRestitucion(dato);
        return evento;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adición de un
     * tipo de certificado de validación
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de certificado de validación
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTipoCertificadoValidacion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        TipoCertificadoValidacion dato = getTipoCertificadoValidacion(request);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.ADICIONA_TIPO_CERTIFICADO_VALIDACION);
        evento.setTipoCertificadoValidacion(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de elminación de
     * un tipo de certificado de validación
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de certificado de validación
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaTipoCertificadoValidacion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        TipoCertificadoValidacion dato = getTipoCertificadoValidacion(request);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.ELIMINA_TIPO_CERTIFICADO_VALIDACION);
        evento.setTipoCertificadoValidacion(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * validaciones asociadas a un tipo de certificado
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de certificado
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaValidacionesDeTipoCertificado(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        ValidacionTipoCertificadoValidacionException exception
                = new ValidacionTipoCertificadoValidacionException();
        
        String idTipoCert = request.getParameter(CTipoCertificado.TIPO_CERTIFICADO_ID);
        if ((idTipoCert == null)
                || (idTipoCert.trim().length() == 0)
                || idTipoCert.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un tipo de certificado.");
        } else {
            session.setAttribute(CTipoCertificado.TIPO_CERTIFICADO_ID, idTipoCert);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        TipoCertificado dato = new TipoCertificado();
        dato.setIdTipoCertificado(idTipoCert);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTA_VALIDACIONES_DE_TIPO_CERTIFICADO);
        evento.setTipoCertificado(dato);
        return evento;
    }

    /**
     * Extrae un objeto de tipo <code>TipoCertificadoValidacion</code> del
     * <code>HttpServletRequest</code>
     *
     * @param request
     * @return
     */
    private TipoCertificadoValidacion getTipoCertificadoValidacion(HttpServletRequest request)
            throws ValidacionTipoCertificadoValidacionException {
        
        HttpSession session = request.getSession();
        ValidacionTipoCertificadoValidacionException exception
                = new ValidacionTipoCertificadoValidacionException();
        
        String idTipoCert = request.getParameter(CTipoCertificado.TIPO_CERTIFICADO_ID);
        if ((idTipoCert == null)
                || (idTipoCert.trim().length() == 0)
                || idTipoCert.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un tipo de certificado.");
        } else {
            session.setAttribute(CTipoCertificado.TIPO_CERTIFICADO_ID, idTipoCert);
        }
        
        String idValidacion = request.getParameter(CValidacion.VALIDACION_ID);
        if ((idValidacion == null)
                || (idValidacion.trim().length() == 0)
                || idValidacion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Seleccionar un tipo de Validación.");
        } else {
            session.setAttribute(CValidacion.VALIDACION_ID, idValidacion);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        TipoCertificado tipocert = new TipoCertificado();
        tipocert.setIdTipoCertificado(idTipoCert);
        
        Validacion validacion = new Validacion();
        validacion.setIdValidacion(idValidacion);
        
        TipoCertificadoValidacion dato = new TipoCertificadoValidacion();
        dato.setIdTipoCertificado(idTipoCert);
        dato.setIdValidacion(idValidacion);
        dato.setTipoCertificado(tipocert);
        dato.setValidacion(validacion);
        
        return dato;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adicionar un
     * tipo de nota
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de nota
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaTipoNota(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionTipoNotaException exception = new ValidacionTipoNotaException();

        //La generación del Identificador debe ser automática
        /*
		String id = request.getParameter(CTipoNota.ID_TIPO_NOTA);
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un Identificador.");
		} else {
			session.setAttribute(CTipoNota.ID_TIPO_NOTA, id);
		}
         */
        String id = request.getParameter(CTipoNota.IDENTIFICADOR);
        
        try {
            long identificadorTipoNotaLong = Long.parseLong(id);
            session.setAttribute(CTipoNota.IDENTIFICADOR, id);
            session.setAttribute(CTipoNota.ID_TIPO_NOTA, id);
        } catch (NumberFormatException e) {
            exception.addError(id + " no es un identificador válido de Nota.");
        }
        
        String idProc = request.getParameter(CProceso.PROCESO_ID);
        if ((idProc == null)
                || (idProc.trim().length() == 0)
                || idProc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idProc);
        }
        
        long idproceso = 0;
        try {
            idproceso = Long.parseLong(idProc);
        } catch (NumberFormatException e) {
            exception.addError(idProc + " no es un identificador válido de Proceso.");
        }
        
        String idFase = request.getParameter(CFase.FASE_ID);
        if ((idFase == null)
                || (idFase.trim().length() == 0)
                || idFase.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar una Fase.");
        } else {
            session.setAttribute(CFase.FASE_ID, idFase);
        }
        
        String nombre = request.getParameter(CTipoNota.NOMBRE_TIPO_NOTA);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un Nombre.");
        } else {
            session.setAttribute(CTipoNota.NOMBRE_TIPO_NOTA, nombre);
        }
        
        String visibilidad = request.getParameter(CTipoNota.VISIBILIDAD_TIPO_NOTA);
        if ((visibilidad == null) || (visibilidad.trim().length() == 0)) {
            exception.addError("Debe Proporcionar una Visibilidad.");
        } else {
            session.setAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA, visibilidad);
        }
        
        boolean devolutiva = false;
        //String devolStr = request.getParameter(CTipoNota.DEVOLUTIVA);
        String devolStr = "";
        devolStr = devolStr + session.getAttribute(CTipoNota.DEVOLUTIVA);
        
        if ((devolStr != null)) {
            if (devolStr.equals("true")) {
                devolutiva = true;
            }
        }
        
        String descripcion = request.getParameter(CTipoNota.DESCRIPCION);
        
        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            exception.addError("Debe Proporcionar una Descripción.");
        } else {
            session.setAttribute(CTipoNota.DESCRIPCION, descripcion);
        }
        /*
                    @author Carlos Torres
                    @change Se agrega la variable version
                    @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
         */
        String version = request.getParameter(CTipoNota.VERSION);
        if (version != null && !version.equals("")) {
            session.setAttribute(CTipoNota.VERSION, version);
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Proceso proceso = new Proceso();
        proceso.setIdProceso(idproceso);
        
        TipoNota dato = new TipoNota();
        dato.setIdTipoNota(id);
        dato.setNombre(nombre);
        dato.setDescripcion(descripcion);
        dato.setVisibilidad(visibilidad);
        dato.setDevolutiva(devolutiva);
        dato.setProceso(proceso);
        dato.setFase(idFase);
        /*
                    @author Carlos Torres
                    @change Se establece el valor para el atributo version de la instacia dato
                    @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
         */
        
        if (version != null) {
            dato.setVersion(Long.parseLong(version));
        } else {
            dato.setVersion(Long.parseLong("1"));
        }
        dato.setActivo(1);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_TIPO_NOTA);
        evento.setTipoNota(dato);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adicionar un
     * tipo de nota
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de nota
     * @throws AccionWebException
     */
    private EvnAdministracionHermod modificaTipoNota(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionTipoNotaException exception = new ValidacionTipoNotaException();

        /*String idProc = request.getParameter(CProceso.PROCESO_ID);
		if ((idProc == null)
			|| (idProc.trim().length() == 0)
			|| idProc.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Selecionar un Proceso.");
		} else {
			session.setAttribute(CProceso.PROCESO_ID, idProc);
		}

		long idproceso = 0;
		try {
			idproceso = Long.parseLong(idProc);
		} catch (NumberFormatException e) {
			exception.addError(idProc + " no es un identificador válido de Proceso.");
		}

		String idFase = request.getParameter(CFase.FASE_ID);
		if ((idFase == null)
			|| (idFase.trim().length() == 0)
			|| idFase.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Selecionar una Fase.");
		} else {
			session.setAttribute(CFase.FASE_ID, idFase);
		}*/
        String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
        if ((idTipoNota == null)
                || (idTipoNota.trim().length() == 0)) {
            exception.addError("Debe Selecionar un Identificador de Tipo de Nota.");
        } else {
            session.setAttribute(CTipoNota.ID_TIPO_NOTA, idTipoNota);
        }
        
        String nombre = request.getParameter(CTipoNota.NOMBRE_TIPO_NOTA);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un Nombre.");
        } else {
            session.setAttribute(CTipoNota.NOMBRE_TIPO_NOTA, nombre);
        }
        
        String visibilidad = request.getParameter(CTipoNota.VISIBILIDAD_TIPO_NOTA);
        if ((visibilidad == null) || (visibilidad.trim().length() == 0)) {
            exception.addError("Debe Proporcionar una Visibilidad.");
        } else {
            session.setAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA, visibilidad);
        }
        
        boolean devolutiva = false;
        //String devolStr = request.getParameter(CTipoNota.DEVOLUTIVA);
        String devolStr = "";
        devolStr = devolStr + session.getAttribute(CTipoNota.DEVOLUTIVA);
        
        if ((devolStr != null)) {
            if (devolStr.equals("true")) {
                devolutiva = true;
            }
        }
        
        String descripcion = request.getParameter(CTipoNota.DESCRIPCION);
        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            exception.addError("Debe Proporcionar una Descripción.");
        } else {
            session.setAttribute(CTipoNota.DESCRIPCION, descripcion);
        }
        
        long idproceso = 0;
        try {
            idproceso = Long.parseLong("" + session.getAttribute(CProceso.PROCESO_ID));
        } catch (NumberFormatException e) {
            exception.addError(session.getAttribute(CProceso.PROCESO_ID) + " no es un identificador válido de Proceso.");
        }
        /*
                    @author Carlos Torres
                    @change Se define la variable version
                    @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
         */
        long version = 0;
        try {
            version = Long.parseLong("" + session.getAttribute(CTipoNota.VERSION));
        } catch (NumberFormatException e) {
            exception.addError(session.getAttribute(CProceso.PROCESO_ID) + " no es un identificador válido de Proceso.");
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Proceso proceso = new Proceso();
        proceso.setIdProceso(idproceso);
        TipoNota dato = new TipoNota();
        dato.setIdTipoNota(idTipoNota);
        dato.setNombre(nombre);
        dato.setDescripcion(descripcion);
        dato.setVisibilidad(visibilidad);
        dato.setDevolutiva(devolutiva);
        dato.setProceso(proceso);
        dato.setFase("" + session.getAttribute(CFase.FASE_ID));
        /**
         * @author : CTORRES
         *** @change : Ajustes respectivos para setear versión del Tipo de
         * Nota. ** Caso Mantis : 14370: Acta - Requerimiento No
         * 022_589_Nota_Informativa_Mayor_Valor
         */
        dato.setActivo(1);
        /*
                    @author Carlos Torres
                    @change Se establece el atributo version de la instacia dato
                    @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
         */
        dato.setVersion(version);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.MODIFICA_TIPO_NOTA);
        evento.setTipoNota(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminar un
     * tipo de nota
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de nota
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaTipoNota(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionTipoNotaException exception = new ValidacionTipoNotaException();
        
        boolean devolutiva = false;
        String devolStr = "";
        devolStr = devolStr + session.getAttribute(CTipoNota.DEVOLUTIVA);
        
        if ((devolStr != null)) {
            if (devolStr.equals("true")) {
                devolutiva = true;
            }
        }
        
        String id = request.getParameter(CTipoNota.ID_TIPO_NOTA);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un Identificador.");
        } else {
            session.setAttribute(CTipoNota.ID_TIPO_NOTA, id);
        }
        
        String idProc = request.getParameter(CProceso.PROCESO_ID);
        if ((idProc == null)
                || (idProc.trim().length() == 0)
                || idProc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idProc);
        }
        
        long idproceso = 0;
        try {
            idproceso = Long.parseLong(idProc);
        } catch (NumberFormatException e) {
            exception.addError(idProc + " no es un identificador válido de Proceso.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Proceso proceso = new Proceso();
        proceso.setIdProceso(idproceso);

        /*
                 * @autor         : HGOMEZ 
                 * @mantis        : 11631 
                 * @Requerimiento : 036_453_Correccion_Actuaciones_Administrativas  
                 * @descripcion   : Se captura los datos "version" y "activa" de la
                 * nota informativa a eliminar dado que junto con el id nota hacen 
                 * parte de una llave combinada.  Asimismo, se asignan al objeto 
                 * "dato" que será el que viajará al negocio.
         */
        String version = request.getParameter(CTipoNota.VERSION);
        String activa = request.getParameter(CTipoNota.ACTIVA);
        
        TipoNota dato = new TipoNota();
        dato.setIdTipoNota(id);
        dato.setProceso(proceso);
        dato.setDevolutiva(devolutiva);
        dato.setVersion(Long.valueOf(version).longValue());
        dato.setActivo(Integer.parseInt(activa));
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_TIPO_NOTA);
        evento.setTipoNota(dato);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de coinsultar los
     * tipos de nota asociados a un proceso
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * proceso
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaTipoNotaPorProceso(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        session.removeAttribute(AWAdministracionHermod.LISTA_FASES_DE_PROCESO);
        session.removeAttribute(CTipoNota.NOMBRE_TIPO_NOTA);
        session.removeAttribute(CTipoNota.DESCRIPCION);
        session.removeAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA);
        session.removeAttribute(CTipoNota.DEVOLUTIVA);
        session.removeAttribute(CFase.FASE_ID);
        
        ValidacionTipoNotaException exception = new ValidacionTipoNotaException();
        
        String idProc = request.getParameter(CProceso.PROCESO_ID);
        if ((idProc == null)
                || (idProc.trim().length() == 0)
                || idProc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idProc);
        }
        
        long idproceso = 0;
        try {
            idproceso = Long.parseLong(idProc);
        } catch (NumberFormatException e) {
            exception.addError(idProc + " no es un identificador válido de Proceso.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Proceso proceso = new Proceso();
        proceso.setIdProceso(idproceso);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_TIPO_NOTA_POR_PROCESO);
        evento.setProceso(proceso);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de coinsultar los
     * tipos de nota asociados a un proceso
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * proceso
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaTipoNotaInfPorProceso(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        session.removeAttribute(AWAdministracionHermod.LISTA_FASES_DE_PROCESO);
        session.removeAttribute(CTipoNota.IDENTIFICADOR);
        session.removeAttribute(CTipoNota.NOMBRE_TIPO_NOTA);
        session.removeAttribute(CTipoNota.DESCRIPCION);
        session.removeAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA);
        //session.removeAttribute(CTipoNota.DEVOLUTIVA);
        session.removeAttribute(CFase.FASE_ID);
        
        ValidacionTipoNotaException exception = new ValidacionTipoNotaException();
        
        String idProc = request.getParameter(CProceso.PROCESO_ID);
        if ((idProc == null)
                || (idProc.trim().length() == 0)
                || idProc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idProc);
        }
        
        long idproceso = 0;
        try {
            idproceso = Long.parseLong(idProc);
        } catch (NumberFormatException e) {
            exception.addError(idProc + " no es un identificador válido de Proceso.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Proceso proceso = new Proceso();
        proceso.setIdProceso(idproceso);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_TIPO_NOTA_INF_POR_PROCESO);
        evento.setProceso(proceso);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de coinsultar los
     * tipos de nota asociados a un proceso
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * proceso
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaTipoNotaDevPorProceso(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        session.removeAttribute(AWAdministracionHermod.LISTA_FASES_DE_PROCESO);
        session.removeAttribute(CTipoNota.IDENTIFICADOR);
        session.removeAttribute(CTipoNota.NOMBRE_TIPO_NOTA);
        session.removeAttribute(CTipoNota.DESCRIPCION);
        session.removeAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA);
        //session.removeAttribute(CTipoNota.DEVOLUTIVA);
        session.removeAttribute(CFase.FASE_ID);
        
        ValidacionTipoNotaException exception = new ValidacionTipoNotaException();
        
        String idProc = request.getParameter(CProceso.PROCESO_ID);
        if ((idProc == null)
                || (idProc.trim().length() == 0)
                || idProc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Proceso.");
        } else {
            session.setAttribute(CProceso.PROCESO_ID, idProc);
        }
        
        long idproceso = 0;
        try {
            idproceso = Long.parseLong(idProc);
        } catch (NumberFormatException e) {
            exception.addError(idProc + " no es un identificador válido de Proceso.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Proceso proceso = new Proceso();
        proceso.setIdProceso(idproceso);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.CONSULTA_TIPO_NOTA_DEV_POR_PROCESO);
        evento.setProceso(proceso);
        return evento;
    }
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de adicionar un
     * Check Item
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * Check Item
     * @throws AccionWebException
     */
    private EvnAdministracionHermod adicionaCheckItem(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCheckItemException exception = new ValidacionCheckItemException();
        
        String id = request.getParameter(CCheckItem.CHECK_ITEM_ID);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un Identificador.");
        } else {
            session.setAttribute(CCheckItem.CHECK_ITEM_ID, id);
        }
        
        String idSub = request.getParameter(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
        if ((idSub == null) || (idSub.trim().length() == 0) || idSub.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Subtipo de Solicitud.");
        } else {
            session.setAttribute(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD, idSub);
        }
        
        String nombre = request.getParameter(CCheckItem.CHECK_ITEM_NOMBRE);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un Nombre.");
        } else {
            session.setAttribute(CCheckItem.CHECK_ITEM_NOMBRE, nombre);
        }

        //ver si el nombre esta repetido
        boolean repetido = false;
        List tipos = (List) session.getAttribute(AWAdministracionHermod.LISTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD);
        Iterator it = tipos.iterator();
        while (it.hasNext()) {
            CheckItem check = (CheckItem) it.next();
            if (nombre.equals(check.getNombre())) {
                repetido = true;
                break;
            }
        }
        
        if (repetido) {
            exception.addError("Ese nombre ya existe en el listado.");
        }
        
        boolean obligatorio = false;
        String devolStr = request.getParameter(CCheckItem.CHECK_ITEM_OBLIGATORIEDAD);
        if ((devolStr != null)) {
            obligatorio = true;
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        SubtipoSolicitud subtipo = new SubtipoSolicitud();
        subtipo.setIdSubtipoSol(idSub);
        
        CheckItem dato = new CheckItem();
        dato.setIdCheckItem(id);
        dato.setNombre(nombre);
        dato.setObligatorio(obligatorio);
        dato.setSubtipoSolicitud(subtipo);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ADICIONA_CHECK_ITEM);
        evento.setCheckItem(dato);
        evento.setSubtipoSolicitud(subtipo);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminación un
     * Check Item
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * Check Item
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminaCheckItem(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCheckItemException exception = new ValidacionCheckItemException();
        
        String id = request.getParameter(CCheckItem.CHECK_ITEM_ID);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un Identificador.");
        } else {
            session.setAttribute(CCheckItem.CHECK_ITEM_ID, id);
        }
        
        String idSub = request.getParameter(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
        if ((idSub == null) || (idSub.trim().length() == 0) || idSub.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Subtipo de Solicitud.");
        } else {
            session.setAttribute(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD, idSub);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        SubtipoSolicitud subtipo = new SubtipoSolicitud();
        subtipo.setIdSubtipoSol(idSub);
        
        CheckItem dato = new CheckItem();
        dato.setIdCheckItem(id);
        dato.setSubtipoSolicitud(subtipo);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        limpiarSesionEdicionCheckItem(request);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ELIMINA_CHECK_ITEM);
        evento.setCheckItem(dato);
        evento.setSubtipoSolicitud(subtipo);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para mostrar en la interfaz la edicion de
     * un check item
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * Check Item
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editarCheckItem(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCheckItemException exception = new ValidacionCheckItemException();
        
        String id = request.getParameter(CCheckItem.CHECK_ITEM_ID);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un Identificador.");
        } else {
            session.setAttribute(CCheckItem.CHECK_ITEM_ID, id);
        }
        
        String idSub = request.getParameter(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
        if ((idSub == null) || (idSub.trim().length() == 0) || idSub.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Subtipo de Solicitud.");
        } else {
            session.setAttribute(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD, idSub);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // subir los datos para la edicion a edicion.
        List checkItems = (List) session.getAttribute(AWAdministracionHermod.LISTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD);
        Iterator it = checkItems.iterator();
        while (it.hasNext()) {
            CheckItem dato = (CheckItem) it.next();
            if (dato.getIdCheckItem().equals(id)) {
                Boolean obligatorio = new Boolean(dato.isObligatorio());
                session.setAttribute(CCheckItem.CHECK_ITEM_NOMBRE, dato.getNombre());
                session.setAttribute(CCheckItem.CHECK_ITEM_OBLIGATORIEDAD_EDICION, obligatorio);
            }
        }
        
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de editar un Check
     * Item
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * Check Item
     * @throws AccionWebException
     */
    private EvnAdministracionHermod editaCheckItem(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCheckItemException exception = new ValidacionCheckItemException();
        
        String id = (String) session.getAttribute(CCheckItem.CHECK_ITEM_ID);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un Identificador.");
        } else {
            session.setAttribute(CCheckItem.CHECK_ITEM_ID, id);
        }
        
        String idSub = request.getParameter(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
        if ((idSub == null) || (idSub.trim().length() == 0) || idSub.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Subtipo de Solicitud.");
        } else {
            session.setAttribute(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD, idSub);
        }
        
        String nombre = request.getParameter(CCheckItem.CHECK_ITEM_NOMBRE);
        if ((nombre == null) || (nombre.trim().length() == 0)) {
            exception.addError("Debe Proporcionar un Nombre.");
        } else {
            session.setAttribute(CCheckItem.CHECK_ITEM_NOMBRE, nombre);
        }
        
        boolean obligatorio = false;
        String devolStr = request.getParameter(CCheckItem.CHECK_ITEM_OBLIGATORIEDAD);
        if ((devolStr != null)) {
            obligatorio = true;
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        SubtipoSolicitud subtipo = new SubtipoSolicitud();
        subtipo.setIdSubtipoSol(idSub);
        
        CheckItem dato = new CheckItem();
        dato.setIdCheckItem(id);
        dato.setNombre(nombre);
        dato.setObligatorio(obligatorio);
        dato.setSubtipoSolicitud(subtipo);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        limpiarSesionEdicionCheckItem(request);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.EDITA_CHECK_ITEM);
        evento.setCheckItem(dato);
        evento.setSubtipoSolicitud(subtipo);
        return evento;
    }

    /**
     * Este método se encarga de limpiar los valores de la
     * <code>HttpSession</code> en la pantalla administracion check items
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private void limpiarSesionEdicionCheckItem(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CCheckItem.CHECK_ITEM_NOMBRE);
        session.removeAttribute(CCheckItem.CHECK_ITEM_OBLIGATORIEDAD_EDICION);
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consultar un
     * Check Item
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * Check Item
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaCheckItem(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCheckItemException exception = new ValidacionCheckItemException();
        
        String idSub = request.getParameter(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD);
        if ((idSub == null) || (idSub.trim().length() == 0) || idSub.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe Selecionar un Subtipo de Solicitud.");
        } else {
            session.setAttribute(CSubtipoSolicitud.ID_SUBTIPO_SOLICITUD, idSub);
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        SubtipoSolicitud subtipo = new SubtipoSolicitud();
        subtipo.setIdSubtipoSol(idSub);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD);
        evento.setSubtipoSolicitud(subtipo);
        
        limpiarSesionEdicionCheckItem(request);
        
        session.removeAttribute(AWAdministracionHermod.LISTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD);
        return evento;
    }

    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de entrega masiva
     * de certificados
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod entregaMasivaCertificados(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        EntregaMasivaCertificadosException exception = new EntregaMasivaCertificadosException();
        
        String[] turnos = request.getParameterValues(CTurno.ID_TURNO);
        
        if ((turnos == null) || (turnos.length == 0)) {
            exception.addError("Debe Selecionar al menos un certificado.");
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        List listaTurnos = new ArrayList();
        
        for (int i = 0; i < turnos.length; i++) {
            Turno turno = new Turno();
            turno.setIdWorkflow(turnos[i]);
            listaTurnos.add(turno);
        }
        
        Fase fase = new Fase();
        fase.setID(CFase.CER_ENTREGAR);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usu = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ENTREGA_MASIVA_CERTIFICADOS);
        evento.setTurnos(listaTurnos);
        evento.setFase(fase);
        evento.setUsuarioSIR(usu);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * turnos en estado CER_ENTREGA en una fecha determinada
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaTurnosCerEntregaPorFecha(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        
        EntregaMasivaCertificadosException exception = new EntregaMasivaCertificadosException();
        
        String fecha = request.getParameter("CALENDAR");
        if ((fecha == null) || (fecha.trim().length() == 0)) {
            exception.addError("Debe Selecionar proporcionar una fecha ");
        } else {
            session.setAttribute("CALENDAR", fecha);
        }
        
        Date date = null;
        date = darFecha(fecha);
        if (date == null) {
            exception.addError("El formato de fecha no es válido.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Proceso proceso = new Proceso();
        proceso.setIdProceso(Long.parseLong(CProceso.PROCESO_CERTIFICADOS));
        
        Fase fase = new Fase();
        fase.setID(CFase.CER_ENTREGAR);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTA_TURNOS_CER_ENTREGA_POR_FECHA);
        evento.setFecha(date);
        evento.setProceso(proceso);
        evento.setCirculo(circulo);
        evento.setFase(fase);
        return evento;
        
    }

//////////////////////////////////////////////////
    //////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de entrega masiva
     * de consultas
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod entregaMasivaConsultas(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        EntregaMasivaCertificadosException exception = new EntregaMasivaCertificadosException();
        
        String[] turnos = request.getParameterValues(CTurno.ID_TURNO);
        
        if ((turnos == null) || (turnos.length == 0)) {
            exception.addError("Debe Selecionar al menos un certificado.");
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        List listaTurnos = new ArrayList();
        
        for (int i = 0; i < turnos.length; i++) {
            Turno turno = new Turno();
            turno.setIdWorkflow(turnos[i]);
            listaTurnos.add(turno);
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usu = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ENTREGA_MASIVA_CONSULTAS);
        evento.setTurnos(listaTurnos);
        evento.setUsuarioSIR(usu);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * turnos en estado CON_ENTREGAR_COMPLEJA y CON_ENTREGAR_SIMPLE en una fecha
     * determinada
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaTurnosConEntregaPorFecha(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        
        EntregaMasivaCertificadosException exception = new EntregaMasivaCertificadosException();
        
        String fecha = request.getParameter("CALENDAR");
        if ((fecha == null) || (fecha.trim().length() == 0)) {
            exception.addError("Debe Selecionar proporcionar una fecha ");
        } else {
            session.setAttribute("CALENDAR", fecha);
        }
        
        Date date = null;
        date = darFecha(fecha);
        if (date == null) {
            exception.addError("El formato de fecha no es válido.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Proceso proceso = new Proceso();
        proceso.setIdProceso(Long.parseLong(CProceso.PROCESO_CONSULTAS));
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTA_TURNOS_CON_ENTREGA_POR_FECHA);
        evento.setFecha(date);
        evento.setProceso(proceso);
        evento.setCirculo(circulo);
        
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consulta de
     * probabilidad de revision calificacion
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultaProbRevisionCalificacion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTAR_PROB_REV_CALIFICACION);
        
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de modificacion de
     * probabilidad de revision calificacion
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod modificarProbRevisionCalificacion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ConsultarProbRevisionCalificacionException exception = new ConsultarProbRevisionCalificacionException();
        
        String valorProb = request.getParameter(AWAdministracionHermod.PROBABILIDAD_REV_CALIFICACION);
        int val = Integer.parseInt(valorProb);
        if (val < 0 || val > 100) {
            exception.addError("Numero de probabilidad invalido. El valor tiene que ser entre 0 y 100.");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.MODIFICAR_PROB_REV_CALIFICACION);
        
        evento.setValorProbRevCalificion(valorProb);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de sonsultar las
     * liquidaciones no pagadas
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultarSolicitudesNoPagadas(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ConsultarProbRevisionCalificacionException exception = new ConsultarProbRevisionCalificacionException();
        Calendar cal = Calendar.getInstance();
        
        Date fechaDesdeConsulta;
        Date fechaHastaConsulta;
        String fechaDesde = request.getParameter("CALENDARDESDE");
        if (fechaDesde == null || fechaDesde.equals("")) {
            fechaDesde = (String) session.getAttribute("CALENDARDESDE");
            if (fechaDesde == null || fechaDesde.equals("")) {
                fechaDesdeConsulta = new Date();
            } else {
                fechaDesdeConsulta = darFecha(fechaDesde);
            }
        } else {
            fechaDesdeConsulta = darFecha(fechaDesde);
        }
        /*transformacion date para q sea desde las 00:00 */
        
        cal.setTime(fechaDesdeConsulta);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        fechaDesdeConsulta = cal.getTime();
        
        session.setAttribute("CALENDARDESDE", fechaDesde);
        
        String fechaHasta = request.getParameter("CALENDARHASTA");
        if (fechaHasta == null || fechaHasta.equals("")) {
            fechaHasta = (String) session.getAttribute("CALENDARHASTA");
            if (fechaHasta == null || fechaHasta.equals("")) {
                fechaHastaConsulta = new Date();
            } else {
                fechaHastaConsulta = darFecha(fechaHasta);
            }
        } else {
            fechaHastaConsulta = darFecha(fechaHasta);
        }
        /*transformacion date para q sea desde las 23:59 */
        cal.setTime(fechaHastaConsulta);
        cal.set(Calendar.AM_PM, Calendar.PM);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        fechaHastaConsulta = cal.getTime();
        
        session.setAttribute("CALENDARHASTA", fechaHasta);
        
        if (fechaDesdeConsulta == null) {
            exception.addError("La fecha desde de la consulta es invalida");
        }
        
        if (fechaHastaConsulta == null) {
            exception.addError("La fecha hasta de la consulta es invalida");
        }
        
        String paginaActual = request.getParameter(CPaginacion.PAGINA);
        String numeroResultadosPagina = request.getParameter(CPaginacion.RESULTADOS_POR_PAGINA);
        String filtro = request.getParameter(CPaginacion.CRITERIO_BUSQUEDA);
        
        long numeroPagina;
        long resultadosPorPagina;
        
        if (paginaActual == null || paginaActual.equals("")) {
            numeroPagina = 1;
        } else {
            numeroPagina = Long.parseLong(paginaActual);
            if (numeroPagina == 0) {
                numeroPagina = 1;
            }
            
        }
        paginaActual = "" + numeroPagina;
        
        if (numeroResultadosPagina == null || numeroResultadosPagina.equals("")) {
            resultadosPorPagina = 10;
        } else {
            resultadosPorPagina = Long.parseLong(numeroResultadosPagina);
        }
        numeroResultadosPagina = "" + resultadosPorPagina;
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        request.getSession().setAttribute(CPaginacion.PAGINA, new Long(paginaActual));
        request.getSession().setAttribute(CPaginacion.RESULTADOS_POR_PAGINA, new Long(numeroResultadosPagina));
        request.getSession().setAttribute(CPaginacion.CRITERIO_BUSQUEDA, filtro);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS);
        
        evento.setValorFechaDesde(fechaDesdeConsulta);
        evento.setValorFechaHasta(fechaHastaConsulta);
        evento.setNumeroPagina(numeroPagina);
        evento.setNumeroResultadosPorPagina(resultadosPorPagina);
        evento.setFiltro(filtro);
        evento.setCirculo(circulo);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminar las
     * solicitudes no pagadas
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminarSolicitudesNoPagadas(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        List solicitudes = (List) session.getAttribute(AWAdministracionHermod.LISTA_SOLICITUDES_NO_PAGADAS);
        String fechaDesde = (String) session.getAttribute("CALENDARDESDE");
        Date fechaDesdeConsulta = darFecha(fechaDesde);
        String fechaHasta = (String) session.getAttribute("CALENDARHASTA");
        Date fechaHastaConsulta = darFecha(fechaHasta);
        
        if (fechaDesde == null || fechaDesde.equals("")) {
            fechaDesdeConsulta = new Date();
        } else {
            fechaDesdeConsulta = darFecha(fechaDesde);
        }
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(fechaDesdeConsulta);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        fechaDesdeConsulta = cal.getTime();
        
        if (fechaHasta == null || fechaHasta.equals("")) {
            fechaHastaConsulta = new Date();
        } else {
            fechaHastaConsulta = darFecha(fechaHasta);
        }
        
        cal.setTime(fechaHastaConsulta);
        cal.set(Calendar.AM_PM, Calendar.PM);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        fechaHastaConsulta = cal.getTime();
        
        if (fechaDesdeConsulta == null || fechaHastaConsulta == null) {
            throw new AccionWebException("Las fechas ingresdas son inválidas");
        }
        
        List solicitudesAEliminar = new ArrayList();
        String[] llavesSol = request.getParameterValues("ELIMINAR_SOL");
        if (llavesSol != null) {
            for (int i = 0; i < llavesSol.length; i++) {
                SolicitudRegistro sol = new SolicitudRegistro();
                int indice = Integer.parseInt(llavesSol[i]);
                sol = (SolicitudRegistro) solicitudes.get(indice);
                solicitudesAEliminar.add(sol);
            }
        }
        
        Long numeroResultadosPagina = (Long) session.getAttribute(CPaginacion.RESULTADOS_POR_PAGINA);
        String filtro = (String) session.getAttribute(CPaginacion.CRITERIO_BUSQUEDA);
        
        long resultadosPorPagina = 0;
        
        if (numeroResultadosPagina == null || numeroResultadosPagina.equals("")) {
            resultadosPorPagina = 10;
        } else {
            resultadosPorPagina = numeroResultadosPagina.longValue();
        }
        
        String paginaActual = request.getParameter(CPaginacion.PAGINA);
        
        long numeroPagina;
        
        if (paginaActual == null || paginaActual.equals("")) {
            numeroPagina = 1;
        } else {
            numeroPagina = Long.parseLong(paginaActual);
            if (numeroPagina == 0) {
                numeroPagina = 1;
            }
            
        }
        paginaActual = "" + numeroPagina;
        
        if (solicitudesAEliminar.size() > 0) {
            Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
            Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
            EvnAdministracionHermod evento
                    = new EvnAdministracionHermod(
                            usuario,
                            EvnAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS);
            
            evento.setCirculo(circulo);
            evento.setSolicitudes(solicitudesAEliminar);
            evento.setValorFechaDesde(fechaDesdeConsulta);
            evento.setValorFechaHasta(fechaHastaConsulta);
            evento.setNumeroResultadosPorPagina(resultadosPorPagina);
            evento.setFiltro(filtro);
            evento.setNumeroPagina(numeroPagina);
            return evento;
            
        }
        
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de eliminar las
     * solicitudes no pagadas
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod eliminarTodasLasSolicitudesNoPagadas(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        List solicitudes = (List) session.getAttribute(AWAdministracionHermod.LISTA_SOLICITUDES_NO_PAGADAS);
        String fechaDesde = (String) session.getAttribute("CALENDARDESDE");
        Date fechaDesdeConsulta = darFecha(fechaDesde);
        String fechaHasta = (String) session.getAttribute("CALENDARHASTA");
        Date fechaHastaConsulta = darFecha(fechaHasta);
        
        if (fechaDesde == null || fechaDesde.equals("")) {
            fechaDesdeConsulta = new Date();
        } else {
            fechaDesdeConsulta = darFecha(fechaDesde);
        }
        
        if (fechaHasta == null || fechaHasta.equals("")) {
            fechaHastaConsulta = new Date();
        } else {
            fechaHastaConsulta = darFecha(fechaHasta);
        }
        
        if (fechaDesdeConsulta == null || fechaHastaConsulta == null) {
            throw new AccionWebException("Las fechas ingresdas son inválidas");
        }
        
        Long numeroResultadosPagina = (Long) session.getAttribute(CPaginacion.RESULTADOS_POR_PAGINA);
        String filtro = (String) session.getAttribute(CPaginacion.CRITERIO_BUSQUEDA);
        
        long resultadosPorPagina = 0;
        
        if (numeroResultadosPagina == null || numeroResultadosPagina.equals("")) {
            resultadosPorPagina = 10;
        } else {
            resultadosPorPagina = numeroResultadosPagina.longValue();
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.ELIMINAR_TODAS_LAS_SOLICITUDES_NO_PAGADAS);
        
        evento.setCirculo(circulo);
        evento.setValorFechaDesde(fechaDesdeConsulta);
        evento.setValorFechaHasta(fechaHastaConsulta);
        evento.setNumeroResultadosPorPagina(resultadosPorPagina);
        evento.setFiltro(filtro);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de consultar los
     * turnos de calificacion segun el usuario.
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod consultarTurnosCalificacion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        session.setAttribute(WebKeys.TITULO_CHECKBOX_ELIMINAR, request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR));
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        
        Log.getInstance().debug(AWAdministracionHermod.class, "CIRCULO EN  SESION ES " + circulo.getIdCirculo());
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(
                        usuario,
                        EvnAdministracionHermod.CONSULTA_TURNOS_CALIFICACION);
        
        evento.setCirculo(circulo);
        
        evento.setUsuarioNeg(usuarioNeg);
        return evento;
        
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de solicitar
     * correccion de calificacion de uno de sus turnos calificacdos.
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code>
     * @throws AccionWebException
     */
    private EvnAdministracionHermod solicitarCorreccionCalificacion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        int numProceso = Integer.parseInt(CProceso.PROCESO_REGISTRO);

        /*obtener valores del session*/
        //turno
        String turno = "";
        turno = request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR);
        session.setAttribute(WebKeys.TITULO_CHECKBOX_ELIMINAR, turno);

        //nota informativa
        ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
        String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        //setear el proceso manualmente.
        Proceso proceso = new Proceso();
        proceso.setIdProceso(numProceso);
        
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        String descripcion = request.getParameter(CNota.DESCRIPCION);
        session.setAttribute(CNota.DESCRIPCION, descripcion);
        
        if (turno == null || turno.equals("")) {
            exception.addError("Debe escoger un turno");
        }
        
        if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo de nota válido");
        }
        
        if (descripcion == null || descripcion.length() < 15) {
            exception.addError("La longitud de la descripción no puede ser inferior a 15 caracteres");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Nota notaInformativa = new Nota();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        notaInformativa.setIdNota(idTipoNota);
        notaInformativa.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
        notaInformativa.setIdCirculo(circulo.getIdCirculo());
        notaInformativa.setIdProceso(proceso.getIdProceso());
        notaInformativa.setIdTurno(null);
        notaInformativa.setTime(calendar.getTime());
        notaInformativa.setUsuario(usuario);
        
        notaInformativa.setDescripcion(descripcion);
        String descripcionTipo = request.getParameter((CTipoNota.DESCRIPCION));
        String nombreTipo = (String) request.getSession().getAttribute(CNota.NOMBRE);
        
        TipoNota tipoNota = new TipoNota();
        tipoNota.setDescripcion(descripcionTipo);
        tipoNota.setIdTipoNota(idTipoNota);
        tipoNota.setNombre(nombreTipo);
        notaInformativa.setTiponota(tipoNota);
        
        Usuario usuarioEnSesion = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.SOLICITAR_CORRECCION_CALIFICACION);
        evento.setTurnoCorreccionId(turno);
        evento.setNotaInformativaCorreccion(notaInformativa);
        evento.setUsuarioSIR(usuario);
        evento.setUsuarioNeg(usuario);
        evento.setCirculo(circulo);
        
        return evento;
        
    }

    /**
     * @param request
     */
    private Evento buscarDescripcion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
        String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
        if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo de nota valido");
        }

        //preservar info del tabla de matriculas
        if (request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR) != null) {
            request.getSession().setAttribute(WebKeys.TITULO_CHECKBOX_ELIMINAR, request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR));
        }
        
        Iterator itTiposNotas = ((List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS)).iterator();
        while (itTiposNotas.hasNext()) {
            TipoNota elemento = (TipoNota) itTiposNotas.next();
            if (elemento.getIdTipoNota().equals(idTipoNota)) {
                session.setAttribute(CTipoNota.DESCRIPCION, elemento.getDescripcion());
                session.setAttribute(CTipoNota.ID_TIPO_NOTA, idTipoNota);
                session.setAttribute(CNota.DESCRIPCION, request.getParameter(CNota.DESCRIPCION));
                session.setAttribute(CNota.NOMBRE, elemento.getNombre());
            }
        }
        
        return null;
    }

    /**
     *
     * @param request
     * @return
     */
    private Evento buscarDescripcionMayorValorPendiente(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
        String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
        if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo de nota valido");
        }

        //preservar info del tabla de matriculas
        if (request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR) != null) {
            request.getSession().setAttribute(WebKeys.TITULO_CHECKBOX_ELIMINAR, request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR));
        }
        
        Iterator itTiposNotas = ((List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS_PMY)).iterator();
        while (itTiposNotas.hasNext()) {
            TipoNota elemento = (TipoNota) itTiposNotas.next();
            if (elemento.getIdTipoNota().equals(idTipoNota)) {
                session.setAttribute(CTipoNota.DESCRIPCION, elemento.getDescripcion());
                session.setAttribute(CTipoNota.ID_TIPO_NOTA, idTipoNota);
                session.setAttribute(CNota.DESCRIPCION, request.getParameter(CNota.DESCRIPCION));
                session.setAttribute(CNota.NOMBRE, elemento.getNombre());
            }
        }
        
        return null;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para adicionar una
     * naturaleza juridica para reparto
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * de ejecución del proceso de catastro
     * @throws AccionWebException
     */
    private Evento adicionarNatJuridicaEntidad(HttpServletRequest request) throws ValidacionNatJuridicaEntidadException {
        ValidacionNatJuridicaEntidadException exception = new ValidacionNatJuridicaEntidadException();
        
        HttpSession session = request.getSession();
        String idNatJuridicaEntidad = request.getParameter(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        String nombre = request.getParameter(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
        String exenta = request.getParameter(CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD);
        
        session.setAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD, idNatJuridicaEntidad);
        session.setAttribute(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD, nombre);
        
        if (idNatJuridicaEntidad == null || idNatJuridicaEntidad.trim().length() <= 0) {
            exception.addError("El código de naturaleza juridica para reparto no puede ser vacio");
        } else {
            try {
                double code = Double.parseDouble(idNatJuridicaEntidad);
                if (code < 0) {
                    exception.addError("El código de naturaleza juridica para reparto no puede ser negativo");
                }
            } catch (NumberFormatException e) {
                exception.addError("El código de naturaleza juridica para reparto es invalido");
            }
        }
        
        if (nombre == null || nombre.trim().length() <= 0) {
            exception.addError("El nombre de naturaleza juridica para reparto no puede ser vacio");
        }
        
        boolean esExenta = false;
        
        if (exenta != null) {
            esExenta = true;
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        NaturalezaJuridicaEntidad natJuridica = new NaturalezaJuridicaEntidad();
        natJuridica.setExento(esExenta);
        natJuridica.setIdNatJuridicaEntidad(idNatJuridicaEntidad);
        natJuridica.setNombre(nombre);
        natJuridica.setActivo(true);
        
        Usuario usuarioEnSesion = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.ADICIONA_NAT_JURIDICA_ENTIDAD);
        evento.setNatJuridicaEntidad(natJuridica);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarNatJuridicaEntidad(HttpServletRequest request) throws ValidacionNatJuridicaEntidadException {
        String idNatJuridica = request.getParameter(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        ValidacionNatJuridicaEntidadException exception = new ValidacionNatJuridicaEntidadException();
        if (idNatJuridica == null || idNatJuridica.trim().length() <= 0) {
            exception.addError("Número de naturaleza jurídica inválido");
            throw exception;
        }
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.ELIMINA_NAT_JURIDICA_ENTIDAD);
        evento.setIdNatJuridicaEntidad(idNatJuridica);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento habilitarEdicionNatEntidadPublica(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        String idNaturaleza = request.getParameter(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        
        List tipos = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD);
        if (tipos == null) {
            tipos = new ArrayList();
        }
        Iterator it = tipos.iterator();
        while (it.hasNext()) {
            NaturalezaJuridicaEntidad naturaleza = (NaturalezaJuridicaEntidad) it.next();
            if (naturaleza.getIdNatJuridicaEntidad().equals(idNaturaleza)) {
                request.getSession().setAttribute(CNaturalezaJuridicaEntidad.NATURALEZA_JURIDICA_ENTIDAD, naturaleza);
            }
        }
        
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.ACTIVA_NAT_JURIDICA_ENTIDAD);
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD);
        
        request.getSession().setAttribute(WebKeys.OCULTAR, new Boolean(true));
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento desHabilitarEdicionNatEntidadPublica(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.NATURALEZA_JURIDICA_ENTIDAD);
        request.getSession().removeAttribute(WebKeys.OCULTAR);
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.ACTIVA_NAT_JURIDICA_ENTIDAD);
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento editarNatJuridicaEntidad(HttpServletRequest request) throws ValidacionNatJuridicaEntidadException {
        ValidacionNatJuridicaEntidadException exception = new ValidacionNatJuridicaEntidadException();
        
        HttpSession session = request.getSession();
        
        String idNatJuridicaEntidad = request.getParameter(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        String nombre = request.getParameter(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
        String exenta = request.getParameter(CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD);
        String activa = request.getParameter(CNaturalezaJuridicaEntidad.ACTIVA_NAT_JURIDICA_ENTIDAD);
        
        if (idNatJuridicaEntidad == null || idNatJuridicaEntidad.trim().length() <= 0) {
            exception.addError("El código de naturaleza jurídica que quiere editar es inválida");
        } else {
            try {
                double code = Double.parseDouble(idNatJuridicaEntidad);
                if (code < 0) {
                    exception.addError("El código de naturaleza jurídica para reparto no puede ser negativo");
                }
            } catch (NumberFormatException e) {
                exception.addError("El código de naturaleza jurídica para reparto es inválido");
            }
        }
        
        if (nombre == null || nombre.trim().length() <= 0) {
            exception.addError("El nombre de naturaleza jurídica para reparto no puede ser vacio");
        }
        
        boolean esExenta = false;
        
        if (exenta != null && !exenta.equals("")) {
            esExenta = true;
        }
        
        boolean esActiva = false;
        
        if (activa != null && !activa.equals("")) {
            esActiva = true;
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        NaturalezaJuridicaEntidad natJuridica = new NaturalezaJuridicaEntidad();
        natJuridica.setExento(esExenta);
        natJuridica.setIdNatJuridicaEntidad(idNatJuridicaEntidad);
        natJuridica.setNombre(nombre);
        natJuridica.setActivo(esActiva);
        
        Usuario usuarioEnSesion = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.EDITAR_NAT_JURIDICA_ENTIDAD);
        evento.setNatJuridicaEntidad(natJuridica);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarEntidadPublica(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        String idEntidadPublica = request.getParameter(CEntidadPublica.ID_ENTIDAD_PUBLICA);
        ValidacionParametrosEntidadPublicaException exception = new ValidacionParametrosEntidadPublicaException();
        if (idEntidadPublica == null || idEntidadPublica.trim().length() <= 0) {
            exception.addError("Número de naturaleza jurídica inválido");
            throw exception;
        }
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.ELIMINA_ENTIDAD_PUBLICA);
        EntidadPublica entidad = new EntidadPublica();
        List tipos = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
        Iterator itTipos = tipos.iterator();
        while (itTipos.hasNext()) {
            EntidadPublica temp = (EntidadPublica) itTipos.next();
            if (temp.getIdEntidadPublica().equals(idEntidadPublica)) {
                entidad = temp;
            }
        }
        
        evento.setEntidadPublica(entidad);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento adicionarEntidadPublica(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        String nomEntidadPublica = request.getParameter(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
        String idNatJuridica = request.getParameter(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        ValidacionParametrosEntidadPublicaException exception = new ValidacionParametrosEntidadPublicaException();
        
        request.getSession().setAttribute(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA, nomEntidadPublica);
        request.getSession().setAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD, idNatJuridica);
        if (nomEntidadPublica == null || nomEntidadPublica.trim().length() <= 0) {
            exception.addError("El nombre de la entidad publica no puede ser vacio");
        }
        if (idNatJuridica == null || idNatJuridica.trim().length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza júridica para la entidad pública");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        EntidadPublica entidad = new EntidadPublica();
        entidad.setIdEntidadPublica(null);
        entidad.setNombre(nomEntidadPublica);
        NaturalezaJuridicaEntidad natJuridica = new NaturalezaJuridicaEntidad();
        natJuridica.setIdNatJuridicaEntidad(idNatJuridica);
        entidad.setNaturalezaJuridica(natJuridica);
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA);
        evento.setEntidadPublica(entidad);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento habilitarEdicionEntidadPublica(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        String idEntidadPublica = request.getParameter(CEntidadPublica.ID_ENTIDAD_PUBLICA);
        
        List tipos = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
        if (tipos == null) {
            tipos = new ArrayList();
        }
        Iterator it = tipos.iterator();
        while (it.hasNext()) {
            EntidadPublica entidad = (EntidadPublica) it.next();
            if (entidad.getIdEntidadPublica().equals(idEntidadPublica)) {
                request.getSession().setAttribute(CEntidadPublica.ENTIDAD_PUBLICA, entidad);
            }
        }
        
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        request.getSession().removeAttribute(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
        request.getSession().removeAttribute(CEntidadPublica.ACTIVA_ENTIDAD_PUBLICA);
        
        request.getSession().setAttribute(WebKeys.OCULTAR, new Boolean(true));
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento desHabilitarEdicionEntidadPublica(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        request.getSession().removeAttribute(CEntidadPublica.ENTIDAD_PUBLICA);
        request.getSession().removeAttribute(WebKeys.OCULTAR);
        request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        request.getSession().removeAttribute(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
        request.getSession().removeAttribute(CEntidadPublica.ACTIVA_ENTIDAD_PUBLICA);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento editarEntidadPublica(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        String idEntidadPublica = request.getParameter(CEntidadPublica.ID_ENTIDAD_PUBLICA);
        String nomEntidadPublica = request.getParameter(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
        String idNatJuridica = request.getParameter(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        String activa = request.getParameter(CEntidadPublica.ACTIVA_ENTIDAD_PUBLICA);
        
        ValidacionParametrosEntidadPublicaException exception = new ValidacionParametrosEntidadPublicaException();
        
        request.getSession().setAttribute(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA, nomEntidadPublica);
        request.getSession().setAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD, idNatJuridica);
        
        if (idEntidadPublica == null || idEntidadPublica.trim().length() <= 0) {
            exception.addError("El identificar de la entidad publica no puede ser vacio");
        }
        if (nomEntidadPublica == null || nomEntidadPublica.trim().length() <= 0) {
            exception.addError("El nombre de la entidad publica no puede ser vacio");
        }
        if (idNatJuridica == null || idNatJuridica.trim().length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza júridica para la entidad pública");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        EntidadPublica entidad = new EntidadPublica();
        entidad.setIdEntidadPublica(idEntidadPublica);
        entidad.setNombre(nomEntidadPublica);
        NaturalezaJuridicaEntidad natJuridica = new NaturalezaJuridicaEntidad();
        natJuridica.setIdNatJuridicaEntidad(idNatJuridica);
        entidad.setNaturalezaJuridica(natJuridica);
        boolean esActiva = false;
        if (activa != null && !activa.equals("")) {
            esActiva = true;
        }
        entidad.setActivo(esActiva);
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.EDITA_ENTIDAD_PUBLICA);
        evento.setEntidadPublica(entidad);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento que permita la
     * modificación de
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * turno de devolución.
     * @throws AccionWebException
     */
    private EvnAdministracionHermod modificarValorDevolucion(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        String idTurno = request.getParameter(WebKeys.TURNO);
        if ((idTurno == null) || (idTurno.trim().length() == 0)) {
            throw new AccionInvalidaException("Ingresó un número de turno vacío");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION);
        evento.setIdentificadorTurnoDevolucion(idTurno);
        evento.setCirculo((Circulo) session.getAttribute(WebKeys.CIRCULO));
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento que permita la
     * modificación de
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * turno de devolución.
     * @throws AccionWebException
     */
    private EvnAdministracionHermod modificarValorDevolucionSeleccionada(HttpServletRequest request)
            throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        String idTurno = request.getParameter(WebKeys.TURNO);
        
        String valorDerechos = request.getParameter(AWAdministracionHermod.VALOR_DERECHOS);
        String valorImpuestos = request.getParameter(AWAdministracionHermod.VALOR_IMPUESTOS);
        String valorCertificados = request.getParameter(AWAdministracionHermod.VALOR_CERTIFICADOS);
        double derechos = 0d;
        double impuestos = 0d;
        double certificados = 0d;

        //Se realizan las respectivas validaciones.
        //Validar que sobre el turno pueda realizarse la modificación
        //esto es posible si ya tiene una liquidación negativa asociada.
        String tieneLiqNegativa = request.getParameter(AWAdministracionHermod.LIQUIDACION_NEGATIVA);
        if (tieneLiqNegativa == null || !(tieneLiqNegativa.equals(AWAdministracionHermod.LIQUIDACION_NEGATIVA))) {
            throw new AccionInvalidaException("No es posible modificar el valor de la devolución porque "
                    + "sobre el turno aun no se ha asociado el valor a devolver");
        }

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
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, AWAdministracionHermod.MODIFICAR_VALOR_LIQUIDACION_DEVOLUCION_SELECCIONADA);
        evento.setIdentificadorTurnoDevolucion(idTurno);
        evento.setDevolucionCertificados(certificados);
        evento.setDevolucionDerechos(derechos);
        evento.setDevolucionImpuestos(impuestos);
        evento.setCirculo((Circulo) session.getAttribute(WebKeys.CIRCULO));
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento consultarEntidadPublicaByNaturaleza(HttpServletRequest request) throws ValidacionParametrosEntidadPublicaException {
        
        String idNatJuridica = request.getParameter(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        request.getSession().setAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD, idNatJuridica);
        
        ValidacionParametrosEntidadPublicaException exception = new ValidacionParametrosEntidadPublicaException();
        
        if (idNatJuridica == null || idNatJuridica.trim().length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza júridica para la entidad pública");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        EntidadPublica entidad = new EntidadPublica();
        NaturalezaJuridicaEntidad natJuridica = new NaturalezaJuridicaEntidad();
        if (idNatJuridica != null && !idNatJuridica.equals(WebKeys.SIN_SELECCIONAR)) {
            natJuridica.setIdNatJuridicaEntidad(idNatJuridica);
        } else {
            natJuridica.setIdNatJuridicaEntidad(null);
        }
        entidad.setNaturalezaJuridica(natJuridica);
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA);
        evento.setEntidadPublica(entidad);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento obtnerImpresoras(HttpServletRequest request) {
        Circulo cir = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion);
        evento.setTipoEvento(EvnAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO);
        evento.setCirculo(cir);
        return evento;
    }

    ////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de limpiar la sesión luego que el usuario ha
     * terminado de usar las pantallas administrativas relacionadas con Hermod
     */
    private EvnAdministracionHermod limpiaSesion(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        session.removeAttribute(AWAdministracionHermod.BANCO_SELECCIONADO);
        session.removeAttribute(AWAdministracionHermod.ESTACION_RECIBO_EDIT_ULTIMO_VALOR_OK);
        session.removeAttribute(AWAdministracionHermod.ESTACION_RECIBO_SELECCIONADA);
        session.removeAttribute(AWAdministracionHermod.LISTA_ABOGADOS_DE_CIRCULO);
        session.removeAttribute(AWAdministracionHermod.LISTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD);
        session.removeAttribute(AWAdministracionHermod.LISTA_CIRCULO_ESTACIONES_RECIBO);
        session.removeAttribute(AWAdministracionHermod.LISTA_CIRCULO_FIRMAS_NOTARIALES);
        session.removeAttribute(AWAdministracionHermod.LISTA_CIRCULO_TIPOS_PAGO);
        session.removeAttribute(AWAdministracionHermod.LISTA_ESTACIONES_DE_CIRCULO);
        session.removeAttribute(AWAdministracionHermod.LISTA_FASES_DE_PROCESO);
        session.removeAttribute(AWAdministracionHermod.LISTA_VALIDACIONES_DE_TIPO_CERTIFICADO);
        session.removeAttribute(AWAdministracionHermod.LISTA_TIPOS_NOTA_POR_PROCESO);
        session.removeAttribute(AWAdministracionHermod.MINUTA_CONSULTADA);
        session.removeAttribute(AWAdministracionHermod.OPLOOKUP_TYPE_SELECCIONADO);
        session.removeAttribute(AWAdministracionHermod.OPLOOKUP_CODES_SELECCIONADOS);
        session.removeAttribute(AWAdministracionHermod.SELECCIONA_TIPO_TARIFA);
        session.removeAttribute(AWAdministracionHermod.SUCURSALES_SELECCIONADAS);
        session.removeAttribute(AWAdministracionHermod.TARIFAS_SELECCIONADAS);
        session.removeAttribute(CCertificado.CERTIFICADO_LISTA_ENTREGAR_MASIVO);
        session.removeAttribute(CCirculo.ID_CIRCULO);
        session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
        session.removeAttribute(CFase.FASE_ID);
        session.removeAttribute(CFaseProceso.FASE_PROCESO_ID);
        session.removeAttribute(CProceso.PROCESO_ID);
        session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
        session.removeAttribute(CTipoNota.IDENTIFICADOR);
        session.removeAttribute(CTipoNota.DESCRIPCION);
        //session.removeAttribute(CTipoNota.DEVOLUTIVA);
        session.removeAttribute(CTipoNota.NOMBRE_TIPO_NOTA);
        session.removeAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA);
        session.removeAttribute(CValidacionNota.VALIDACION_NOTA_RESPUESTA);
        session.removeAttribute(CTipoTarifa.ID_TIPO_TARIFA);
        session.removeAttribute(CTarifa.VALOR_TARIFA);
        session.removeAttribute(WebKeys.LISTA_TIPOS_TARIFA_POR_CIRCULO);
        session.removeAttribute("CALENDAR");
        session.removeAttribute(AWAdministracionHermod.LISTA_TURNOS);
        session.removeAttribute(AWAdministracionHermod.LISTA_TURNOS_STRING);
        session.removeAttribute(CNota.DESCRIPCION);
        session.removeAttribute(WebKeys.RECARGA);
        session.removeAttribute("ESTACION");
        session.removeAttribute(WebKeys.OCULTAR);
        session.removeAttribute(CEntidadPublica.ENTIDAD_PUBLICA);
        session.removeAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
        session.removeAttribute(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
        session.removeAttribute(CNaturalezaJuridicaEntidad.ACTIVA_NAT_JURIDICA_ENTIDAD);
        session.removeAttribute(CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD);
        session.removeAttribute(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
        session.removeAttribute(CEntidadPublica.ACTIVA_ENTIDAD_PUBLICA);
        session.removeAttribute(AWAdministracionHermod.CIRCULO_NOTARIAL_EDITADO);
        session.removeAttribute(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
        session.removeAttribute(CCirculoNotarial.NOMBRE_CIRCULO_NOTARIAL);
        session.removeAttribute(AWAdministracionHermod.LISTA_CIRCULOS_NOTARIALES);
        session.removeAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
        session.removeAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
        session.removeAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS);
        session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
        session.removeAttribute(AWAdministracionForseti.LISTA_VEREDAS);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        session.removeAttribute(AWAdministracionHermod.LISTA_ZONAS_NOTARIALES);
        session.removeAttribute(CAccionNotarial.ID_ACCION_NOTARIAL);
        session.removeAttribute(CAccionNotarial.NOMBRE_ACCION_NOTARIAL);
        session.removeAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
        session.removeAttribute(CAccionNotarial.TIPO_ACCION_NOTARIAL);
        this.limpriarSesionInfoPasoEstacionRecibo(request);
        this.preservarInfoPasoEstacionRecibo(request);
        limpiarSesionEdicionTipoActo(request);
        limpiarSesionEdicionSubtipoSolicitud(request);
        limpiarSesionEdicionCheckItem(request);
        limpiarSesionSubtipoAtencion(request);
        limpiarSesionOrdenSubtipoAtencion(request);
        limpiarSesionFirmasRegistrador(request);
        limpiarSesionCategoria(request);
        session.removeAttribute(AWAdministracionHermod.LISTA_ROLES_PANTALLAS);
        session.removeAttribute(AWAdministracionHermod.ROL_ASIGNAR);
        session.removeAttribute(AWAdministracionHermod.PANTALLA_ASIGNAR);
        session.removeAttribute(AWAdministracionHermod.ROL_PANTALLA_A_ELIMINAR);
        session.removeAttribute(WebKeys.LISTA_BANCOS_X_CIRCULO);
        session.removeAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS);
        session.removeAttribute(WebKeys.LISTADO_BANCOS_CIRCULO);
        session.removeAttribute(CTipoPago.ID_TIPO_PAGO);
        session.removeAttribute(CCanalesRecaudo.ID_CANAL_RECAUDO);
        session.removeAttribute(CCuentasBancarias.ID_CUENTA);
        session.setAttribute(WebKeys.LISTA_CANALES_RECAUDO_ACTIVOS, null);
        return null;
    }

    //	//////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     *
     * @param fechaInterfaz
     * @return
     */
    private Date darFecha(String fechaInterfaz) {
        //java.util.Date date = null;

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
        
        Date fecha = null;
        
        if (partido.length == 3) {
            int dia = Integer.parseInt(partido[0]);
            int mes = Integer.parseInt(partido[1]) - 1;
            int año = Integer.parseInt(partido[2]);
            calendar.set(año, mes, dia, 0, 0, 0);
            fecha = calendar.getTime();
        }
        return fecha;
    }

    /**
     * @param request
     * @return
     */
    private Evento obtenerVencidosMayorValor(HttpServletRequest request) throws AccionWebException {
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo cir = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.OBTENER_VENCIDOS_PAGO_MAYOR_VALOR);
        evento.setCirculo(cir);
        return evento;
    }

    /**
     * @param request
     * @return
     */
    private Evento devolverVencidosMayorValor(HttpServletRequest request) throws AccionWebException {
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Circulo cir = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        
        String numCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
        int numCopy = 1;
        
        ImprimirFolioException exception = new ImprimirFolioException();
        
        if (numCopias != null && numCopias.length() <= 0) {
            exception.addError("Debe ingresar el número de copias que debe imprimir");
        } else {
            try {
                numCopy = new Integer(numCopias).intValue();
                if (numCopy < 1) {
                    exception.addError("El número de copias a imprimir debe ser mayor a cero");
                }
            } catch (Exception e) {
                exception.addError("El número de copias a imprimir debe ser un valor numérico");
            }
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR);
        evento.setCirculo(cir);
        evento.setUsuarioSIR(usuarioNeg);
        evento.setNumeroImpresiones(numCopy);
        return evento;
    }

    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para cargar los
     * calificadores de el circulo actual.
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * de ejecución del proceso de catastro
     * @throws AccionWebException
     */
    private Evento cargarCalificadoresCirculo(HttpServletRequest request) throws AccionWebException {
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo cir = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.CARGAR_CALIFICADORES_CIRCULO);
        evento.setCirculo(cir);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento para cargar los
     * calificadores de el circulo actual.
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * de ejecución del proceso de catastro
     * @throws AccionWebException
     */
    private Evento cargarCalificadoresSubtipoAtencion(HttpServletRequest request) throws AccionWebException {
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo cir = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

        //Obtener el subtipo de atencion
        String idSubtipoAtencion = request.getParameter(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
        request.getSession().setAttribute(CSubtipoAtencion.ID_SUBTIPO_ATENCION, idSubtipoAtencion);
        
        String idUsuario = request.getParameter(CUsuario.ID_USUARIO);
        request.getSession().setAttribute(CUsuario.ID_USUARIO, idUsuario);
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.CARGAR_CALIFICADORES_SUBTIPOATENCION);
        evento.setCirculo(cir);
        evento.setIdSubtipoAtencion(idSubtipoAtencion);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para adcionar el orden de un subtipo
     * atencion
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * de ejecución del proceso de catastro
     * @throws AccionWebException
     */
    private Evento adicionaOrdenSubtipoAtencion(HttpServletRequest request) throws AccionWebException {
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo cir = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        
        ValidacionParametrosOrdenSubtipoAtencion exception = new ValidacionParametrosOrdenSubtipoAtencion();

        //Obtener el subtipo de atencion
        String idSubtipoAtencion = request.getParameter(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
        request.getSession().setAttribute(CSubtipoAtencion.ID_SUBTIPO_ATENCION, idSubtipoAtencion);
        if ((idSubtipoAtencion == null) || (idSubtipoAtencion.trim().length() == 0) || idSubtipoAtencion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Tiene que ingresar el subtipo de atencion");
        }
        
        String idUsuario = request.getParameter(CUsuario.ID_USUARIO);
        request.getSession().setAttribute(CUsuario.ID_USUARIO, idUsuario);
        if ((idUsuario == null) || (idUsuario.trim().length() == 0) || idUsuario.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Tiene que ingresar el usuario");
        }

        //Validar que el usuario no este ya en la lista de subtipos
        if (idUsuario != null) {
            List usuariosSubtiposAtencion = (List) request.getSession().getAttribute(AWAdministracionHermod.LISTA_CALIFICADORES_SUBTIPOSATENCION);
            if (usuariosSubtiposAtencion != null) {
                for (Iterator it = usuariosSubtiposAtencion.iterator(); it.hasNext();) {
                    UsuarioSubtipoAtencion dato = (UsuarioSubtipoAtencion) it.next();
                    if (dato.getIdUsuario() == Long.parseLong(idUsuario)) {
                        exception.addError("El usuario ya existe en el listado. Porfavor eliminelo y vuelva a intentar");
                    }
                }
            }
        }
        
        String orden = request.getParameter(CUsuarioSubtipoAtencion.USUARIO_SUBTIPO_ORDEN);
        request.getSession().setAttribute(CUsuarioSubtipoAtencion.USUARIO_SUBTIPO_ORDEN, orden);
        if ((orden == null) || (orden.trim().length() == 0) || orden.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Tiene que ingresar el orden");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.ADICIONA_ORDEN_SUBTIPOATENCION);
        evento.setCirculo(cir);
        evento.setIdSubtipoAtencion(idSubtipoAtencion);
        evento.setIdUsuario(idUsuario);
        evento.setOrden(orden);
        return evento;
    }

    /**
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para eliminar el orden de un subtipo
     * atencion
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code> con la información
     * de ejecución del proceso de catastro
     * @throws AccionWebException
     */
    private Evento removeOrdenSubtipoAtencion(HttpServletRequest request) throws AccionWebException {
        
        Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo cir = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        
        ValidacionParametrosOrdenSubtipoAtencion exception = new ValidacionParametrosOrdenSubtipoAtencion();

        //Obtener el subtipo de atencion
        String idSubtipoAtencion = request.getParameter(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
        request.getSession().setAttribute(CSubtipoAtencion.ID_SUBTIPO_ATENCION, idSubtipoAtencion);
        if ((idSubtipoAtencion == null) || (idSubtipoAtencion.trim().length() == 0) || idSubtipoAtencion.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Excepcion no esperada. Error ID_SUBTIPO_ATENCION");
        }
        
        String idUsuario = request.getParameter(CUsuario.ID_USUARIO);
        request.getSession().setAttribute(CUsuario.ID_USUARIO, idUsuario);
        if ((idUsuario == null) || (idUsuario.trim().length() == 0) || idUsuario.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Excepcion no esperada. Error ID_USUARIO");
        }

        //Validar que el usuario no este ya en la lista de subtipos
        UsuarioSubtipoAtencion usuSubtipoAtencion = null;
        if (idUsuario != null) {
            List usuariosSubtiposAtencion = (List) request.getSession().getAttribute(AWAdministracionHermod.LISTA_CALIFICADORES_SUBTIPOSATENCION);
            if (usuariosSubtiposAtencion != null) {
                for (Iterator it = usuariosSubtiposAtencion.iterator(); it.hasNext();) {
                    UsuarioSubtipoAtencion dato = (UsuarioSubtipoAtencion) it.next();
                    if (dato.getIdUsuario() == Long.parseLong(idUsuario)) {
                        usuSubtipoAtencion = dato;
                        break;
                    }
                }
            }
        }
        
        if (usuSubtipoAtencion == null) {
            exception.addError("Excepcion no esperada. Error ID_USUARIO");
        }
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuarioEnSesion, EvnAdministracionHermod.REMOVE_ORDEN_SUBTIPOATENCION);
        evento.setCirculo(cir);
        evento.setUsuSubtipoAtencion(usuSubtipoAtencion);
        evento.setIdSubtipoAtencion(idSubtipoAtencion);
        return evento;
    }

    /**
     * @param request
     */
    private void preservarInfoAccionNotarial(HttpServletRequest request) {
        
        if (request.getParameter(CAccionNotarial.ID_ACCION_NOTARIAL) != null) {
            request.getSession().setAttribute(CAccionNotarial.ID_ACCION_NOTARIAL, request.getParameter(CAccionNotarial.ID_ACCION_NOTARIAL));
        }
        if (request.getParameter(CAccionNotarial.NOMBRE_ACCION_NOTARIAL) != null) {
            request.getSession().setAttribute(CAccionNotarial.NOMBRE_ACCION_NOTARIAL, request.getParameter(CAccionNotarial.NOMBRE_ACCION_NOTARIAL));
        }
        if (request.getParameter(CAccionNotarial.CUANTIA_ACCION_NOTARIAL) != null) {
            request.getSession().setAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL, request.getParameter(CAccionNotarial.CUANTIA_ACCION_NOTARIAL));
        }
        if (request.getParameter(CAccionNotarial.TIPO_ACCION_NOTARIAL) != null) {
            request.getSession().setAttribute(CAccionNotarial.TIPO_ACCION_NOTARIAL, request.getParameter(CAccionNotarial.TIPO_ACCION_NOTARIAL));
        }
    }

    /**
     * @param request
     */
    private void preservarInfoPasoEstacionRecibo(HttpServletRequest request) {
        
        if (request.getParameter(CCirculo.ID_CIRCULO) != null) {
            request.getSession().setAttribute(CCirculo.ID_CIRCULO, request.getParameter(CCirculo.ID_CIRCULO));
        }
        if (request.getParameter(CEstacionRecibo.ID_ESTACION_RECIBO) != null) {
            request.getSession().setAttribute(CEstacionRecibo.ID_ESTACION_RECIBO, request.getParameter(CEstacionRecibo.ID_ESTACION_RECIBO));
        }
        if (request.getParameter(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO) != null) {
            request.getSession().setAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO, request.getParameter(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO));
        }
        if (request.getParameter(CEstacionRecibo.ID_ESTACION_RECIBO + "A") != null) {
            request.getSession().setAttribute(CEstacionRecibo.ID_ESTACION_RECIBO + "A", request.getParameter(CEstacionRecibo.ID_ESTACION_RECIBO + "A"));
        }
        if (request.getParameter(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO + "A") != null) {
            request.getSession().setAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO + "A", request.getParameter(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO + "A"));
        }
    }

    /**
     * @param request
     */
    private void limpriarSesionInfoPasoEstacionRecibo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CCirculo.ID_CIRCULO);
        session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
        session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO + "A");
        session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO + "A");
        session.removeAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO + "A");
        
    }

    /**
     * Este método se encarga de limpiar los valores de la
     * <code>HttpSession</code> en la pantalla administracion subtipo atencion
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private void limpiarSesionOrdenSubtipoAtencion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CSubtipoAtencion.ID_SUBTIPO_ATENCION);
        session.removeAttribute(CUsuario.ID_USUARIO);
        session.removeAttribute(AWAdministracionHermod.LISTA_CALIFICADORES);
        session.removeAttribute(AWAdministracionHermod.LISTA_SUBTIPOSATENCION);
        session.removeAttribute(AWAdministracionHermod.LISTA_CALIFICADORES_SUBTIPOSATENCION);
    }

    /**
     * Este método se encarga de cargar al contexto los valores de la Hashtable
     *
     * @param request
     * @return evento <code>EvnAdministracionHermod</code> con los datos del
     * tipo de acto
     * @throws AccionWebException
     */
    private void recargarContexto(HttpServletRequest request, Hashtable mapa) {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        List tiposID = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_ID);
        
        if (tiposID == null) {
            context.setAttribute(WebKeys.LISTA_TIPOS_ID, Collections.unmodifiableList(new ArrayList()));
        } else {
            Iterator itTiposId = tiposID.iterator();
            List elementoTiposId = new ArrayList();
            
            while (itTiposId.hasNext()) {
                OPLookupCodes tipoId = (OPLookupCodes) itTiposId.next();
                elementoTiposId.add(new ElementoLista(tipoId.getCodigo(), tipoId.getValor()));
            }

            //ordenar la lista con quicksort
            ListaElementoHelper qs = new ListaElementoHelper();
            qs.quickSort(elementoTiposId, 0, elementoTiposId.size() - 1);
            
            context.setAttribute(WebKeys.LISTA_TIPOS_ID, Collections.unmodifiableList(elementoTiposId));
        }

        //TIPOS DE TARIFAS DE CERTIFICADOS
        List tarifasCertificados = (List) mapa.get(EvnRespSistema.LISTA_TARIFAS_CERTIFICADOS);
        
        if (tarifasCertificados == null) {
            context.setAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS, Collections.unmodifiableList(new ArrayList()));
        } else {
            Iterator itTarifasCert = tarifasCertificados.iterator();
            List elementosTarifasCert = new ArrayList();
            
            while (itTarifasCert.hasNext()) {
                OPLookupCodes tarifaCert = (OPLookupCodes) itTarifasCert.next();
                elementosTarifasCert.add(new ElementoLista(tarifaCert.getCodigo(), tarifaCert.getCodigo()));
            }
            
            context.setAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS, Collections.unmodifiableList(elementosTarifasCert));
        }
        
        List interesesJuridicos = (List) mapa.get(EvnRespSistema.LISTA_INTERES_JURIDICO);
        
        if (interesesJuridicos == null) {
            context.setAttribute(WebKeys.LISTA_INTERES_JURIDICO, Collections.unmodifiableList(new ArrayList()));
        } else {
            Iterator it = interesesJuridicos.iterator();
            List elementoTiposId = new ArrayList();
            
            while (it.hasNext()) {
                OPLookupCodes tipoId = (OPLookupCodes) it.next();
                elementoTiposId.add(new ElementoLista(tipoId.getCodigo(), tipoId.getValor()));
            }
            
            context.setAttribute(WebKeys.LISTA_INTERES_JURIDICO, Collections.unmodifiableList(elementoTiposId));
        }
        
        List causalesReImp = (List) mapa.get(EvnRespSistema.LISTA_CAUSALES_REIMPRESION);
        
        if (causalesReImp == null) {
            context.setAttribute(WebKeys.LISTA_CAUSALES_REIMPRESION, Collections.unmodifiableList(new ArrayList()));
        } else {
            Iterator itCausales = causalesReImp.iterator();
            List elementosCausales = new ArrayList();
            
            while (itCausales.hasNext()) {
                OPLookupCodes causal = (OPLookupCodes) itCausales.next();
                elementosCausales.add(new ElementoLista(causal.getCodigo(), causal.getValor()));
            }
            
            context.setAttribute(WebKeys.LISTA_CAUSALES_REIMPRESION, Collections.unmodifiableList(elementosCausales));
        }
        
        List opLookupTypes = (List) mapa.get(EvnRespSistema.LISTA_OPLOOKUP_TYPE);
        
        if (opLookupTypes == null) {
            context.setAttribute(WebKeys.LISTA_OPLOOKUP_TYPES, Collections.unmodifiableList(new ArrayList()));
            context.setAttribute(WebKeys.LISTA_OPLOOKUP_TYPES_OBJETOS, Collections.unmodifiableList(new ArrayList()));
        } else {
            List elementos = new ArrayList();
            
            for (Iterator iter = opLookupTypes.iterator(); iter.hasNext();) {
                OPLookupTypes dato = (OPLookupTypes) iter.next();
                elementos.add(new ElementoLista(dato.getTipo(), dato.getTipo()));
            }
            
            context.setAttribute(WebKeys.LISTA_OPLOOKUP_TYPES, Collections.unmodifiableList(elementos));
            
            context.setAttribute(WebKeys.LISTA_OPLOOKUP_TYPES_OBJETOS, Collections.unmodifiableList(opLookupTypes));
            
        }
        
        List unidadesMedida = (List) mapa.get(EvnRespSistema.LISTA_UNIDADES_MEDIDA);
        
        if (unidadesMedida == null) {
            context.setAttribute(WebKeys.LISTA_UNIDADES_MEDIDA, Collections.unmodifiableList(new ArrayList()));
        } else {
            context.setAttribute(WebKeys.LISTA_UNIDADES_MEDIDA, Collections.unmodifiableList(unidadesMedida));
        }
        
        List visibilidades = (List) mapa.get(EvnRespSistema.LISTA_VISIBILIDADES);
        
        if (visibilidades == null) {
            context.setAttribute(WebKeys.LISTA_VISIBILIDAD_NOTAS, Collections.unmodifiableList(new ArrayList()));
        } else {
            Iterator itVisibilidades = visibilidades.iterator();
            List elementosVisibilidad = new ArrayList();
            
            while (itVisibilidades.hasNext()) {
                OPLookupCodes visibilidad = (OPLookupCodes) itVisibilidades.next();
                elementosVisibilidad.add(new ElementoLista(visibilidad.getCodigo(), visibilidad.getValor()));
            }
            
            context.setAttribute(WebKeys.LISTA_VISIBILIDADES, Collections.unmodifiableList(elementosVisibilidad));
        }
        
        String ipBalanceador = (String) mapa.get(EvnRespSistema.IP_BALANCEADOR);
        if (ipBalanceador == null) {
            context.setAttribute(CImpresion.IP_BALANCEADOR, "");
        } else {
            context.setAttribute(CImpresion.IP_BALANCEADOR, ipBalanceador);
        }
        
        String usarBalanceador = (String) mapa.get(EvnRespSistema.USAR_BALANCEADOR);
        if (usarBalanceador == null) {
            context.setAttribute(CImpresion.USAR_BALANCEADOR, "");
        } else {
            context.setAttribute(CImpresion.USAR_BALANCEADOR, usarBalanceador);
        }
        
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de manejar el evento de respuesta proveniente de
     * la acción de negocio. Sube datos a sesión de acuerdo al tipo de respuesta
     * proveniente en el evento
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        if (evento instanceof EvnRespAdministracionHermod) {
            EvnRespAdministracionHermod respuesta = (EvnRespAdministracionHermod) evento;
            
            HttpSession session = request.getSession();
            context = session.getServletContext();
            
            if (respuesta != null) {
                String tipoEvento = respuesta.getTipoEvento();
                if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_BANCO_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_BANCO_OK)) {
                    List bancos = (List) respuesta.getPayload();
                    context.setAttribute(WebKeys.LISTA_BANCOS, bancos);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.NAT_JURIDICAS_ENTIDAD)) {
                    context.setAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD, respuesta.getNatsJuridicasEntidad());
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CAMBIO_NAT_JURIDICA_ENTIDAD)) {
                    context.setAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD, respuesta.getNatsJuridicasEntidad());
                    
                    request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.NATURALEZA_JURIDICA_ENTIDAD);
                    request.getSession().removeAttribute(WebKeys.OCULTAR);
                    request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
                    request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.NOMBRE_NAT_JURIDICA_ENTIDAD);
                    request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.ACTIVA_NAT_JURIDICA_ENTIDAD);
                    request.getSession().removeAttribute(CNaturalezaJuridicaEntidad.EXENTO_NAT_JURIDICA_ENTIDAD);
                    
                    List natJuridicasEntidad = respuesta.getNatsJuridicasEntidad();
                    List natJuridicasEntidadActivas = new ArrayList();
                    if (natJuridicasEntidad == null) {
                        natJuridicasEntidad = new ArrayList();
                    }
                    Iterator it = natJuridicasEntidad.iterator();
                    while (it.hasNext()) {
                        NaturalezaJuridicaEntidad nje = (NaturalezaJuridicaEntidad) it.next();
                        if (nje.isActivo()) {
                            natJuridicasEntidadActivas.add(nje);
                        }
                    }
                    context.setAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS, natJuridicasEntidadActivas);
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CAMBIO_ENTIDAD_PUBLICA)) {
                    context.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS, respuesta.getEntidadesPublicas());
                    
                    List entidadesPublicas = respuesta.getEntidadesPublicas();
                    List entidades = new ArrayList();
                    Iterator itEntidades = entidadesPublicas.iterator();
                    while (itEntidades.hasNext()) {
                        EntidadPublica entidad = (EntidadPublica) itEntidades.next();
                        if (entidad.isActivo()) {
                            entidades.add(entidad);
                        }
                    }
                    context.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS_ACTIVAS, entidades);
                    
                    String accion = request.getParameter(WebKeys.ACCION);
                    if (accion != null && !accion.equals(AWAdministracionHermod.CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA)) {
                        session.removeAttribute(CEntidadPublica.ID_ENTIDAD_PUBLICA);
                        session.removeAttribute(CEntidadPublica.NOMBRE_ENTIDAD_PUBLICA);
                        session.removeAttribute(CNaturalezaJuridicaEntidad.ID_NAT_JURIDICA_ENTIDAD);
                        session.removeAttribute(CEntidadPublica.ENTIDAD_PUBLICA);
                        session.removeAttribute(WebKeys.OCULTAR);
                    }
                    
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.LISTADO_SUCURSALES_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_SUCURSAL_BANCO_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_SUCURSAL_BANCO_OK)) {
                    List sucursales = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.SUCURSALES_SELECCIONADAS, sucursales);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_ALCANCE_GEOGRAFICO_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_ALCANCE_GEOGRAFICO_OK)) {
                    List alcances = (List) respuesta.getPayload();
                    List elementosAlcance = new Vector();
                    //Para mantener uniforme con el tipo de las listas del sistema
                    for (Iterator iter = alcances.iterator(); iter.hasNext();) {
                        AlcanceGeografico alcance = (AlcanceGeografico) iter.next();
                        elementosAlcance.add(
                                new ElementoLista(alcance.getIdAlcanceGeografico(), alcance.getNombre()));
                    }
                    context.setAttribute(WebKeys.LISTA_TIPOS_ALCANCE_GEOGRAFICO, elementosAlcance);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TARIFA_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_TARIFA_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.SELECCIONA_TIPO_TARIFA_OK)) {
                    List tarifas = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.TARIFAS_SELECCIONADAS, tarifas);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TIPO_TARIFA_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_TIPO_TARIFA_OK)) {
                    List tarifas = (List) respuesta.getPayload();
                    context.setAttribute(WebKeys.LISTA_TIPOS_TARIFA, tarifas);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TIPO_FOTOCOPIA_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_TIPO_FOTOCOPIA_OK)) {
                    List alcances = (List) respuesta.getPayload();
                    List elementos = new Vector();
                    //Para mantener uniforme con el tipo de las listas del sistema
                    for (Iterator iter = alcances.iterator(); iter.hasNext();) {
                        TipoFotocopia dato = (TipoFotocopia) iter.next();
                        elementos.add(new ElementoLista(dato.getIdTipoFotocopia(), dato.getNombre()));
                    }
                    context.setAttribute(WebKeys.LISTA_TIPOS_FOTOCOPIA, elementos);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_SUBTIPO_SOLICITUD_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_SUBTIPO_SOLICITUD_OK)) {
                    List subtipos = (List) respuesta.getPayload();
                    context.setAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD, subtipos);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_OPLOOKUP_TYPE_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_OPLOOKUP_TYPE_OK)) {
                    List tipos = (List) respuesta.getPayload();
                    List elementos = new Vector();
                    //Para mantener uniforme con el tipo de las listas del sistema
                    for (Iterator iter = tipos.iterator(); iter.hasNext();) {
                        OPLookupTypes dato = (OPLookupTypes) iter.next();
                        elementos.add(new ElementoLista(dato.getTipo(), dato.getTipo()));
                    }

                    //limpiar variables
                    session.removeAttribute(COPLookupTypes.NOMBRE_OPLOOKUP_TYPE);
                    session.removeAttribute(COPLookupTypes.DESCRIPCION_OPLOOKUP_TYPE);
                    
                    context.setAttribute(WebKeys.LISTA_OPLOOKUP_TYPES, elementos);
                    session.setAttribute(AWAdministracionHermod.LISTA_OPLOOKUP_TYPES_CONSULTA, tipos);
                    
                    Hashtable ht = null;
                    
                    ht = respuesta.getHt();
                    if (ht != null) {
                        recargarContexto(request, ht);
                    }
                    
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.LISTADO_LOOKUP_CODES_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_OPLOOKUP_CODE_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_OPLOOKUP_CODE_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.OPLOOKUP_CODES_SELECCIONADOS, elementos);
                    
                    Hashtable ht = null;
                    ht = respuesta.getHt();
                    if (ht != null) {
                        recargarContexto(request, ht);
                    }
                    
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TIPO_CALCULO_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_TIPO_CALCULO_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    context.setAttribute(WebKeys.LISTA_TIPOS_CALCULO, elementos);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TIPO_IMPUESTO_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_TIPO_IMPUESTO_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    context.setAttribute(WebKeys.LISTA_TIPOS_IMPUESTO, elementos);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_SUBTIPO_ATENCION_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_SUBTIPO_ATENCION_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    session.setAttribute(WebKeys.LISTA_SUBTIPOS_ATENCION, elementos);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TIPO_ACTO_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_TIPO_ACTO_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    /*
                                        * @autor          : JATENCIA
                                        * @mantis        : 0015082 
                                        * @Requerimiento : 027_589_Acto_liquidación_copias 
                                        * @descripcion   : Se modifica la lista para regarcar.
                     */
                    context.setAttribute(WebKeys.LISTA_TIPOS_ACTO_DOS, elementos);
                    /* - Fin del bloque - */
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TIPO_DERECHO_REGISTRAL_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_TIPO_DERECHO_REGISTRAL_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    context.setAttribute(WebKeys.LISTA_TIPOS_DERECHO_REGISTRAL, elementos);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_ACCION_NOTARIAL_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_ACCION_NOTARIAL_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    if (elementos != null && !elementos.isEmpty()) {
                        context.setAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES, elementos);
                    }
                    
                    request.getSession().removeAttribute(CAccionNotarial.ACCION_NOTARIAL);
                    request.getSession().removeAttribute(WebKeys.OCULTAR);
                    request.getSession().removeAttribute(CAccionNotarial.ID_ACCION_NOTARIAL);
                    request.getSession().removeAttribute(CAccionNotarial.NOMBRE_ACCION_NOTARIAL);
                    request.getSession().removeAttribute(CAccionNotarial.CUANTIA_ACCION_NOTARIAL);
                    
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_CATEGORIA_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_CATEGORIA_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    context.setAttribute(WebKeys.LISTA_CATEGORIAS, elementos);
                    limpiarSesionCategoria(request);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_CIRCULO_TIPO_PAGO_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.SELECCIONA_CIRCULO_TIPO_PAGO_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ACTUALIZA_CIRCULO_TIPO_PAGO_OK)) {
                    session.setAttribute(WebKeys.LISTA_CANALES_RECAUDO_ACTIVOS, null);
                    List elementos = (List) respuesta.getPayload();
                    try {
                        HermodService hs = HermodService.getInstance();
                        List canalesRecaudoActivos = hs.getCanalesRecaudo(true);
                        session.setAttribute(WebKeys.LISTA_CANALES_RECAUDO_ACTIVOS, canalesRecaudoActivos);
                    } catch (Exception e) {
                    }
                    
                    session.setAttribute(AWAdministracionHermod.LISTA_CIRCULO_TIPOS_PAGO, elementos);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_MINUTA_OK)) {
                    Minuta dato = (Minuta) respuesta.getPayload();
                    context.setAttribute(AWAdministracionHermod.MINUTA_CONSULTADA, dato);

                    //session.setAttribute(CMinuta.SOLICITUD_ID, dato.getIdSolicitud());
                    session.setAttribute(
                            WebKeys.LISTA_ACCIONES_NOTARIALES,
                            dato.getAccionesNotariales());
                    session.setAttribute(CMinuta.CUANTIA, "" + new BigDecimal(dato.getValor()).toString());
                    session.setAttribute(
                            CMinuta.UNIDADES,
                            "" + new BigDecimal(dato.getUnidades()).toString());

                    //session.setAttribute(
                    //AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,
                    //dato.getOtorgante1Oficina().getVereda().getIdDepartamento());
                    //session.setAttribute(
                    //AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,
                    //(dato.getOtorgante1Oficina().getVereda().getMunicipio() != null
                    //&& dato.getOtorgante1Oficina().getVereda().getMunicipio().getDepartamento() != null
                    //? dato
                    //	.getOtorgante1Oficina()
                    //	.getVereda()
                    //	.getMunicipio()
                    //	.getDepartamento()
                    //	.getNombre()
                    //	: ""));
                    /*session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,
						dato.getOtorgante1Oficina().getVereda().getIdMunicipio());*/
                    //session.setAttribute(
                    //	AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,
                    //	(dato.getOtorgante1Oficina().getVereda().getMunicipio() != null
                    //		? dato.getOtorgante1Oficina().getVereda().getMunicipio().getNombre()
                    //	: ""));
                    /*
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,
						dato.getOtorgante1Oficina().getVereda().getIdVereda());
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,
						dato.getOtorgante1Oficina().getVereda().getNombre());
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
						dato.getOtorgante1Oficina().getIdOficinaOrigen());
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,
						dato.getOtorgante1Oficina().getNombre());
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
						dato.getOtorgante1Oficina().getNumero());
                     */
 /*
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO + CSolicitudRepartoNotarial.A,
						(dato.getOtorgante2Oficina() != null
							? dato.getOtorgante2Oficina().getVereda().getIdDepartamento()
							: ""));
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO + CSolicitudRepartoNotarial.A,
						(dato.getOtorgante2Oficina() != null
							&& dato.getOtorgante2Oficina().getVereda().getMunicipio() != null
							&& dato.getOtorgante2Oficina().getVereda().getMunicipio().getDepartamento() != null
								? dato
									.getOtorgante2Oficina()
									.getVereda()
									.getMunicipio()
									.getDepartamento()
									.getNombre()
								: ""));
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC + CSolicitudRepartoNotarial.A,
						(dato.getOtorgante2Oficina() != null
							? dato.getOtorgante2Oficina().getVereda().getIdMunicipio()
							: ""));
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC + CSolicitudRepartoNotarial.A,
						(dato.getOtorgante2Oficina() != null
							&& dato.getOtorgante2Oficina().getVereda().getMunicipio() != null
								? dato.getOtorgante2Oficina().getVereda().getMunicipio().getNombre()
								: ""));
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA + CSolicitudRepartoNotarial.A,
						(dato.getOtorgante2Oficina() != null
							? dato.getOtorgante2Oficina().getVereda().getIdVereda()
							: ""));
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA
							+ CSolicitudRepartoNotarial.A,
						(dato.getOtorgante2Oficina() != null
							? dato.getOtorgante2Oficina().getVereda().getNombre()
							: ""));
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA
							+ CSolicitudRepartoNotarial.A,
						(dato.getOtorgante2Oficina() != null
							? dato.getOtorgante2Oficina().getIdOficinaOrigen()
							: ""));
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO + CSolicitudRepartoNotarial.A,
						(dato.getOtorgante2Oficina() != null
							? dato.getOtorgante2Oficina().getNombre()
							: ""));
					session.setAttribute(
						AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM + CSolicitudRepartoNotarial.A,
						(dato.getOtorgante2Oficina() != null
							? dato.getOtorgante2Oficina().getNumero()
							: ""));

					session.setAttribute(
						CCiudadano.TIPODOC,
						(dato.getOtorgante2Ciudadano() != null
							? dato.getOtorgante2Ciudadano().getTipoDoc()
							: ""));
					session.setAttribute(
						CCiudadano.DOCUMENTO,
						(dato.getOtorgante2Ciudadano() != null
							? dato.getOtorgante2Ciudadano().getDocumento()
							: ""));
					session.setAttribute(
						CCiudadano.APELLIDO1,
						(dato.getOtorgante2Ciudadano() != null
							? dato.getOtorgante2Ciudadano().getApellido1()
							: ""));
					session.setAttribute(
						CCiudadano.APELLIDO2,
						(dato.getOtorgante2Ciudadano() != null
							? dato.getOtorgante2Ciudadano().getApellido2()
							: ""));
					session.setAttribute(
						CCiudadano.NOMBRE,
						(dato.getOtorgante2Ciudadano() != null
							? dato.getOtorgante2Ciudadano().getNombre()
							: ""));*/
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.EDITA_MINUTA_OK)) {
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.INACTIVA_FIRMA_REGISTRADOR_OK)
                        || tipoEvento.equals(
                                EvnRespAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR_OK)) {
                    Circulo circulo = (Circulo) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.CIRCULO_SELECCIONADO, circulo);
                    
                    List elementos = circulo.getFirmas();
                    session.setAttribute(AWAdministracionHermod.LISTA_CIRCULO_FIRMAS_NOTARIALES, elementos);
                    
                    session.removeAttribute(AWAdministracionHermod.FIRMA_REGISTRADOR);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.BUSCAR_FIRMA_REGISTRADOR_CIRCULO)) {
                    List firmas = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.LISTA_FIRMAS_REGISTRADOR, firmas);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_ESTACION_RECIBO_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.LISTA_CIRCULO_ESTACIONES_RECIBO, elementos);
                    
                    session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO);
                    session.removeAttribute(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
                    session.removeAttribute(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
                    session.removeAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
                    session.removeAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.TRASPASO_ESTACION_RECIBO_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.LISTA_CIRCULO_ESTACIONES_RECIBO, elementos);
                    
                    session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO);
                    session.removeAttribute(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
                    session.removeAttribute(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
                    session.removeAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
                    session.removeAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.LISTA_CIRCULO_ESTACIONES_RECIBO, elementos);
                    
                    List estaciones = respuesta.getEstaciones();
                    List elementosLista = new ArrayList();
                    for (Iterator iter = estaciones.iterator(); iter.hasNext();) {
                        Estacion estacion = (Estacion) iter.next();
                        elementosLista.add(
                                new ElementoLista(estacion.getEstacionId(), estacion.getEstacionId()));
                    }
                    session.setAttribute(AWAdministracionHermod.LISTA_ESTACIONES_DE_CIRCULO, elementosLista);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_VALOR_ESTACION_RECIBO_OK)) {
                    EstacionRecibo estacion = (EstacionRecibo) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.ESTACION_RECIBO_SELECCIONADA, estacion);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO_OK)) {
                    session.removeAttribute(AWAdministracionHermod.ESTACION_RECIBO_SELECCIONADA);
                    session.setAttribute(
                            AWAdministracionHermod.ESTACION_RECIBO_EDIT_ULTIMO_VALOR_OK,
                            new Boolean(true));
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ACTUALIZA_SUBTIPOS_ATENCION_USUARIO_OK)
                        || tipoEvento.equals(
                                EvnRespAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO_ROL_OK)) {
                    session.removeAttribute(AWAdministracionHermod.LISTA_ABOGADOS_DE_CIRCULO);
                    session.setAttribute(
                            AWAdministracionHermod.LISTA_ABOGADOS_DE_CIRCULO,
                            respuesta.getPayload());
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_VALIDACION_NOTA_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_VALIDACION_NOTA_OK)) {
                    context.setAttribute(WebKeys.LISTA_VALIDACIONES_NOTA, respuesta.getPayload());
                    session.removeAttribute(CValidacionNota.VALIDACION_NOTA_RESPUESTA);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_FASE_PROCESO_OK)) {
                    List listaObjetos = (List) respuesta.getPayload();
                    List elementosLista = new ArrayList();
                    for (Iterator iter = listaObjetos.iterator(); iter.hasNext();) {
                        FaseProceso dato = (FaseProceso) iter.next();
                        elementosLista.add(new ElementoLista(dato.getIdFase(), dato.getNombre()));
                    }
                    session.setAttribute(AWAdministracionHermod.LISTA_FASES_DE_PROCESO, elementosLista);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_CAUSAL_RESTITUCION_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_CAUSAL_RESTITUCION_OK)
                        || tipoEvento.equals(EvnAdministracionHermod.EDITAR_DETALLES_CAUSAL_RESTITUCION)) {
                    context.setAttribute(WebKeys.LISTA_CAUSALES_RESTITUCION, respuesta.getPayload());
                    session.removeAttribute(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
                    session.removeAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_DESCRIPCION);
                    session.removeAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_NOMBRE);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_VALIDACION_TIPO_CERTIFICADO_OK)
                        || tipoEvento.equals(
                                EvnRespAdministracionHermod.ELIMINA_VALIDACION_TIPO_CERTIFICADO_OK)
                        || tipoEvento.equals(
                                EvnRespAdministracionHermod.CONSULTA_VALIDACION_TIPO_CERTIFICADO_OK)) {
                    session.setAttribute(
                            AWAdministracionHermod.LISTA_VALIDACIONES_DE_TIPO_CERTIFICADO,
                            respuesta.getPayload());
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_TIPO_NOTA_INF_POR_PROCESO_OK)) {
                    //	Fases relacionadas con un proceso
                    List listaObjetos = respuesta.getFases();
                    List elementosLista = new ArrayList();
                    for (Iterator iter = listaObjetos.iterator(); iter.hasNext();) {
                        FaseProceso dato = (FaseProceso) iter.next();
                        elementosLista.add(new ElementoLista(dato.getIdFase(), dato.getNombre()));
                    }
                    session.setAttribute(AWAdministracionHermod.LISTA_FASES_DE_PROCESO, elementosLista);
                    
                    session.setAttribute(
                            AWAdministracionHermod.LISTA_TIPOS_NOTA_POR_PROCESO,
                            respuesta.getPayload());
                    
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_TIPO_NOTA_DEV_POR_PROCESO_OK)) {
                    //	Fases relacionadas con un proceso
                    List listaObjetos = respuesta.getFases();
                    List elementosLista = new ArrayList();
                    for (Iterator iter = listaObjetos.iterator(); iter.hasNext();) {
                        FaseProceso dato = (FaseProceso) iter.next();
                        elementosLista.add(new ElementoLista(dato.getIdFase(), dato.getNombre()));
                    }
                    session.setAttribute(AWAdministracionHermod.LISTA_FASES_DE_PROCESO, elementosLista);
                    
                    session.setAttribute(
                            AWAdministracionHermod.LISTA_TIPOS_NOTA_POR_PROCESO,
                            respuesta.getPayload());
                    
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TIPO_NOTA_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_TIPO_NOTA_OK)) {
                    session.setAttribute(
                            AWAdministracionHermod.LISTA_TIPOS_NOTA_POR_PROCESO,
                            respuesta.getPayload());

                    //Eliminación de los Atributos del tipo de nota de la sesión
                    session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
                    session.removeAttribute(CTipoNota.IDENTIFICADOR);
                    session.removeAttribute(CTipoNota.DESCRIPCION);
                    //session.removeAttribute(CTipoNota.DEVOLUTIVA);
                    session.removeAttribute(CTipoNota.NOMBRE_TIPO_NOTA);
                    session.removeAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_CHECK_ITEM_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINA_CHECK_ITEM_OK)
                        || tipoEvento.equals(
                                EvnRespAdministracionHermod.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD_OK)) {
                    session.setAttribute(
                            AWAdministracionHermod.LISTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD,
                            respuesta.getPayload());
                    session.removeAttribute(CCheckItem.CHECK_ITEM_ID);
                    session.removeAttribute(CCheckItem.CHECK_ITEM_NOMBRE);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_TURNOS_CER_ENTREGAR_OK)) {
                    session.setAttribute(
                            CCertificado.CERTIFICADO_LISTA_ENTREGAR_MASIVO,
                            respuesta.getPayload());
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_TURNOS_CON_ENTREGAR_OK)) {
                    session.setAttribute(
                            CCertificado.CERTIFICADO_LISTA_ENTREGAR_MASIVO,
                            respuesta.getPayload());
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ENTREGA_MASIVA_CERTIFICADOS_OK)) {
                    session.removeAttribute(CCertificado.CERTIFICADO_LISTA_ENTREGAR_MASIVO);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ENTREGA_MASIVA_CONSULTAS_OK)) {
                    session.removeAttribute(CCertificado.CERTIFICADO_LISTA_ENTREGAR_MASIVO);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.MODIFICAR_PROB_REV_CALIFICACION_OK)) {
                    session.removeAttribute(AWAdministracionHermod.PROBABILIDAD_REV_CALIFICACION);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTAR_PROB_REV_CALIFICACION_OK)) {
                    session.setAttribute(AWAdministracionHermod.PROBABILIDAD_REV_CALIFICACION, respuesta.getPayload());
                    return;
                    
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_TIPOS_TARIFA_POR_CIRCULO_OK)) {
                    session.setAttribute(WebKeys.LISTA_TIPOS_TARIFA_POR_CIRCULO, respuesta.getPayload());
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS_OK) || tipoEvento.equals(EvnRespAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS_OK)) {
                    if (tipoEvento.equals(EvnRespAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS_OK)) {
                        session.setAttribute(CPaginacion.PAGINA, new Long(1));
                    }
                    session.setAttribute(CPaginacion.TOTAL_RESULTADOS, new Long(respuesta.getTotalResultados()));
                    session.setAttribute(AWAdministracionHermod.LISTA_SOLICITUDES_NO_PAGADAS, respuesta.getPayload());
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_TURNOS_CALIFICACION_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.SOLICITAR_CORRECCION_CALIFICACION_OK)) {
                    session.setAttribute(AWAdministracionHermod.LISTA_TURNOS, respuesta.getPayload());
                    //creacion de lista turnos string para tablaHelper
                    List listaTurnos = (List) respuesta.getPayload();
                    List listaString = new Vector();

                    //se crea la lista de strings
                    Iterator it = listaTurnos.iterator();
                    while (it.hasNext()) {
                        Turno t = (Turno) it.next();
                        listaString.add(t.getIdWorkflow());
                    }
                    
                    session.setAttribute(AWAdministracionHermod.LISTA_TURNOS_STRING, listaString);
                    //session.setAttribute("CARGA_SOLICITAR_AJUSTE", new Boolean(false));
                    request.getSession().setAttribute(WebKeys.LISTA_TIPOS_NOTAS, respuesta.getNotas());
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTA_ESTACIONES_CIRCULO_OK)) {
                    session.removeAttribute(AWAdministracionHermod.LISTA_ESTACIONES_DE_CIRCULO);
                    session.setAttribute(AWAdministracionHermod.LISTA_ESTACIONES_DE_CIRCULO, respuesta.getPayload());
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TARIFA_POR_CIRCULO_OK)) {
                    session.setAttribute(WebKeys.LISTA_TIPOS_TARIFA_POR_CIRCULO, respuesta.getPayload());
                    session.removeAttribute(CTipoTarifa.ID_TIPO_TARIFA);
                    session.removeAttribute(CTarifa.VALOR_TARIFA);
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADMIN_UNA_ESTACION_OK)) {
                    session.setAttribute(WebKeys.ESTACION, respuesta.getEstacion());
                    session.setAttribute(WebKeys.ESTACION_RECIBO, respuesta.getEstacionRecibo());
                    session.setAttribute(WebKeys.ESTACION_ROLES_POTENCIALES, respuesta.getRolesPotenciales());
                    session.setAttribute(WebKeys.ESTACION_USUARIOS, respuesta.getUsuariosEstacion());
                    session.setAttribute(WebKeys.ESTACION_USUARIOS_POTENCIALES, respuesta.getUsuariosPotenciales());
                    if (respuesta.getVpe() != null) {
                        session.setAttribute(SMARTKeys.EXCEPCION, respuesta.getVpe());
                    }
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO)) {
                    Circulo cir = respuesta.getCirculo();
                    String idCirculo = cir.getIdCirculo();
                    String key = WebKeys.LISTA_IMPRESORAS + "_" + idCirculo;
                    session.setAttribute(key, respuesta.getPayload());
                    session.setAttribute(WebKeys.RECARGA, new Boolean(false));
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTAR_TURNOS_PMY_VENCIDOS_OK)) {

                    //ASIGNAR UN VALOR AL ATRIBUTO DE RECARGA
                    String keyRecarga = WebKeys.RECARGA_MAYOR_VALOR;
                    String keyLista = WebKeys.LISTADO_VENCIDOS_MAYOR_VALOR;
                    session.setAttribute(keyRecarga, new Boolean(false));
                    session.setAttribute(keyLista, respuesta.getTurnos());
                    
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CONSULTAR_TURNOS_PMY_PENDIENTES_OK)) {

                    //ASIGNAR UN VALOR AL ATRIBUTO DE RECARGA
                    String keyRecarga = WebKeys.RECARGA_MAYOR_VALOR;
                    String keyLista = WebKeys.LISTADO_PENDIENTES_MAYOR_VALOR;
                    
                    List turnos = respuesta.getTurnos();
                    List filas = new ArrayList();
                    List fila = null;
                    
                    for (Iterator iterTurnos = turnos.iterator(); iterTurnos.hasNext();) {
                        Turno turno = (Turno) iterTurnos.next();
                        fila = new ArrayList();
                        fila.add(turno.getIdWorkflow());
                        fila.add(turno.getIdFase());
                        filas.add(fila);
                    }
                    
                    session.setAttribute(keyRecarga, new Boolean(false));
                    session.setAttribute(keyLista, filas);
                    
                    session.setAttribute(WebKeys.LISTA_TIPOS_NOTAS_PMY, respuesta.getNotas());
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.AVANZAR_TURNOS_PMY_VENCIDOS_OK)) {
                    //ASIGNAR UN VALOR AL ATRIBUTO DE RECARGA
                    String keyRecarga = WebKeys.RECARGA_MAYOR_VALOR;
                    session.setAttribute(keyRecarga, new Boolean(true));
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.AVANZAR_TURNOS_PMY_PENDIENTES_OK)) {
                    String keyRecarga = WebKeys.RECARGA_MAYOR_VALOR;
                    session.setAttribute(keyRecarga, new Boolean(true));
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_TIPO_VALIDACION_OK)) {
                    Validacion validacion = (Validacion) respuesta.getPayload();
                    List validaciones = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_VALIDACIONES);
                    ElementoLista elem = new ElementoLista();
                    elem.setId(validacion.getIdValidacion());
                    elem.setValor(validacion.getNombre());
                    validaciones.add(elem);
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.MOSTRAR_CIRCULO_ESTACION_RECIBO_OK)) {
                    session.setAttribute(AWAdministracionHermod.CIRCULOS_ADMINISTRADOR_NACIONAL, respuesta.getPayload());
                    session.setAttribute(AWAdministracionHermod.PROCESOS_VALIDOS_RECIBOS, respuesta.getProcesosValidosRecibo());
                    session.setAttribute(WebKeys.RECARGA, new Boolean(false));
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.SET_ESTACION_RECIBO_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.LISTA_CIRCULO_ESTACIONES_RECIBO, elementos);
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.LISTA_CALIFICADORES, elementos);
                    session.setAttribute(AWAdministracionHermod.LISTA_SUBTIPOSATENCION, respuesta.getSubtiposAtencion());
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CARGAR_CALIFICADORES_SUBTIPOATENCION_OK)) {
                    List elementos = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.LISTA_CALIFICADORES_SUBTIPOSATENCION, elementos);
                } //Obtener turno de devolución a modificar
                else if (tipoEvento.equals(EvnAdministracionHermod.SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION)) {

                    //Obtener el turno y subirlo a la sesión.
                    Turno turnoDevolucion = null;
                    Turno turnoAsociado = null;
                    List listaTurnos = respuesta.getTurnos();
                    if (listaTurnos != null) {
                        if (listaTurnos.size() > 0) {
                            turnoAsociado = (Turno) listaTurnos.get(0);
                            session.setAttribute(WebKeys.TURNO_HIJO, turnoAsociado);
                            if (listaTurnos.size() > 1) {
                                turnoDevolucion = (Turno) listaTurnos.get(1);
                                session.setAttribute(WebKeys.TURNO, turnoDevolucion);
                            }
                        }
                    }
                    
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES_OK)) {
                    List lista = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.LISTA_CIRCULOS_NOTARIALES, lista);
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CARGAR_ZONAS_NOTARIALES_OK)) {
                    List lista = (List) respuesta.getPayload();
                    session.setAttribute(AWAdministracionHermod.LISTA_ZONAS_NOTARIALES, lista);
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CARGAR_ROLES_PANTALLAS_ADMINISTRATIVAS_OK)) {
                    List pantallas = (List) respuesta.getPayload();
                    List rolesPantallas = respuesta.getRolesPantallas();
                    
                    session.setAttribute(AWAdministracionHermod.LISTA_PANTALLAS_ADMINISTRATIVAS, pantallas);
                    session.setAttribute(AWAdministracionHermod.LISTA_ROLES_PANTALLAS, rolesPantallas);
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ELIMINAR_ROL_PANTALLA_ADMINISTRATIVA_OK)
                        || tipoEvento.equals(EvnRespAdministracionHermod.AGREGAR_ROL_PANTALLA_ADMINISTRATIVA_OK)) {
                    List rolesPantallas = respuesta.getRolesPantallas();
                    
                    session.setAttribute(AWAdministracionHermod.LISTA_ROLES_PANTALLAS, rolesPantallas);
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_BANCO_CIRCULO_OK)) {
                    List bancos = (List) respuesta.getPayload();
                    session.setAttribute(WebKeys.LISTA_BANCOS_X_CIRCULO, bancos);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.ADICIONA_CUENTA_BANCARIA_OK)) {
                    /*List bancos = (List) respuesta.getPayload();
                    session.setAttribute(WebKeys.LISTA_BANCOS_X_CIRCULO, bancos);
                    return;*/
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CUENTAS_X_CIRCULO_BANCO_OK)) {
                    List cuentasBancarias = (List) respuesta.getPayload();
                    session.setAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS, cuentasBancarias);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CUENTAS_X_CIRCULO_OK)) {
                    List cuentasBancariasXCirculo = (List) respuesta.getPayload();
                    session.setAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS_CIRCULO, cuentasBancariasXCirculo);
                    return;
                } else if (tipoEvento.equals(EvnRespAdministracionHermod.CANAL_RECAUDO_OK)) {
                    List canalesRecaudo = (List) respuesta.getPayload();
                    //session.setAttribute(WebKeys.LISTA_CANALES_RECAUDO, canalesRecaudo);
                    context.setAttribute(WebKeys.LISTA_CANALES_RECAUDO, canalesRecaudo);
                    return;
                }
            }
        } else if (evento instanceof EvnRespAdministracionForseti) {
            EvnRespAdministracionForseti respuesta = (EvnRespAdministracionForseti) evento;
            String tipoEvento = respuesta.getTipoEvento();
            if (tipoEvento.equals(EvnRespAdministracionForseti.CONSULTA_DEPARTAMENTO)) {
                Departamento depto = (Departamento) respuesta.getPayload();
                List municipios = depto.getMunicipios();
                List municElemLista = new ArrayList();
                //		  Organizar alfabeticamente
                Map mapMunicipios = Collections.synchronizedMap(new TreeMap());
                for (Iterator itMun = municipios.iterator(); itMun.hasNext();) {
                    Municipio municipio = (Municipio) itMun.next();
                    String llave = municipio.getNombre() + "-" + municipio.getIdMunicipio();
                    mapMunicipios.put(llave, municipio);
                    ElementoLista elem = new ElementoLista();
                    elem.setId(municipio.getNombre() + "-" + municipio.getIdMunicipio());
                    elem.setValor(municipio.getNombre());
                    municElemLista.add(elem);
                }
                
                request.getSession().setAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO, depto);
                request.getSession().setAttribute(AWAdministracionForseti.MAP_MUNICIPIOS, mapMunicipios);
                request.getSession().setAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS, municElemLista);
                request.getSession().setAttribute(CDepartamento.ID_DEPARTAMENTO, depto.getNombre() + "-" + depto.getIdDepartamento());
                request.getSession().removeAttribute(AWAdministracionForseti.LISTA_VEREDAS);
                request.getSession().removeAttribute(CMunicipio.ID_MUNICIPIO);
            }
        }
        
    }

    /**
     * @param request
     * @return Modifica Pablo Quintana Junio 18 2008 Imprime una constancia de
     * testamentos desde pantllas administrativas
     */
    private Evento impresionFormularioTestamentos(HttpServletRequest request) throws ValidacionReimpresionCalificacion {
        HttpSession session = request.getSession();
        String idTurno = request.getParameter(CTurno.ID_TURNO);
        String numCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
        ValidacionReimpresionCalificacion exception = new ValidacionReimpresionCalificacion();
        if (idTurno.length() <= 0) {
            exception.addError("El identificador del turno de correcciones no es valido");
        }
        if (numCopias.length() <= 0) {
            exception.addError("Debe ingresar el número de copias que debe imprimir");
        } else {
            try {
                int i = new Integer(numCopias).intValue();
                if (i < 1) {
                    exception.addError("El número de copias a imprimir debe ser mayor a cero");
                }
            } catch (Exception e) {
                exception.addError("El número de copias a imprimir debe ser un valor numérico");
            }
        }
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        String impresora = request.getParameter(WebKeys.IMPRESORA);
        idTurno = idTurno.toUpperCase();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.REIMPRIMIR_FORMULARIO_TESTAMENTOS);
        evento.setIdTurnoCalificacion(idTurno);
        String UID = request.getSession().getId();
        evento.setUID(UID);
        evento.setNumeroImpresiones(new Integer(numCopias).intValue());
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        evento.setUsuarioNeg(usuarioNeg);
        if (impresora != null && !impresora.equals(WebKeys.SIN_SELECCIONAR)) {
            evento.setImpresoraSeleccionada(impresora);
        }
        return evento;
    }

    /**
     * Adiciona una relación banco-círculo (SIR_OP_BANCOS_X_CIRCULO)
     *
     * @param request círculo-banco
     * @return
     * @throws AccionWebException Pablo Quintana Nov07-2008
     */
    private EvnAdministracionHermod adicionaBancoCirculo(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        String idCirculo = request.getParameter(CCirculoFestivo.ID_CIRCULO);
        request.getSession().setAttribute(CCirculoFestivo.ID_CIRCULO, idCirculo);
        if (idCirculo == null || idCirculo.trim().length() == 0 || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new AccionInvalidaException("Debe seleccionar círculo");
        }
        String idBanco = request.getParameter(AWPago.COD_BANCO);
        if ((idBanco == null) || (idBanco.trim().length() == 0) || idBanco.equals(WebKeys.SIN_SELECCIONAR)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código para el Banco.");
        }
        String principal = request.getParameter(CBanco.PRINCIPAL_BANCO);
        if (principal == null || principal.equals("")) {
            principal = "0";
        } else {
            principal = "1";
        }
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        BancosXCirculo bancoXCirculo = new BancosXCirculo();
        bancoXCirculo.setIdCirculo(idCirculo);
        bancoXCirculo.setIdBanco(idBanco);
        bancoXCirculo.setPrincipal(principal);
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, AWAdministracionHermod.ADICIONA_BANCO_CIRCULO);
        evento.setBancoXCirculo(bancoXCirculo);
        return evento;
    }

    /**
     * Elimina un banco relacionado con un círculo (SIR_OP_BANCOS_X_CIRCULO)
     *
     * @param request
     * @return
     * @throws AccionWebException Pablo Quintana Nov07-2008
     */
    private EvnAdministracionHermod eliminaBancoCirculo(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        BancosXCirculo dato = null;
        List bancosXCirculo = (List) session.getAttribute(WebKeys.LISTA_BANCOS_X_CIRCULO);
        String idBanco = (String) request.getParameter(CBanco.ID_BANCO);
        String idCirculo = (String) request.getParameter(CCirculo.ID_CIRCULO);
        if (bancosXCirculo != null && bancosXCirculo.size() > 0) {
            dato = new BancosXCirculo();
            dato.setIdBanco(idBanco);
            dato.setIdCirculo(idCirculo);
        } else {
            throw new AccionInvalidaException("Debe Proporcionar un Banco.");
        }
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, ELIMINA_BANCO_CIRCULO);
        evento.setBancoXCirculo(dato);
        return evento;
    }

    /**
     * Consulta los bancos configurados para un determinado círculo
     *
     * @param request
     * @return
     * @throws AccionWebException Pablo Quintana Nov07-2007
     */
    private EvnAdministracionHermod selecionaBancosXCirculo(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        session.setAttribute(WebKeys.LISTADO_BANCOS_CIRCULO, null);
        session.setAttribute(WebKeys.LISTA_CUENTAS_BANCARIAS, null);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String idCirculo = (String) request.getParameter(CCirculoFestivo.ID_CIRCULO);
        request.getSession().setAttribute(CCirculoFestivo.ID_CIRCULO, idCirculo);
        request.getSession().setAttribute(CCuentasBancarias.ID_BANCO, null);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, SELECCIONA_BANCOS_X_CIRCULO);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        evento.setCirculo(circulo);
        return evento;
    }

    /**
     * Método para activar un banco como principal (defecto) para una oficina
     *
     * @param request
     * @return
     * @throws AccionWebException Pablo Quintana Nov07-2008
     */
    private EvnAdministracionHermod activarBancoPrincipal(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        context = session.getServletContext();
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String idBanco = (String) request.getParameter(CBanco.ID_BANCO);
        String idCirculo = (String) request.getParameter(CCirculo.ID_CIRCULO);
        request.getSession().setAttribute(CCirculoFestivo.ID_CIRCULO, idCirculo);
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, ACTIVAR_BANCO_PRINCIPAL);
        BancosXCirculo bancoXCirculo = new BancosXCirculo();
        bancoXCirculo.setIdCirculo(idCirculo);
        bancoXCirculo.setIdBanco(idBanco);
        evento.setBancoXCirculo(bancoXCirculo);
        return evento;
    }

    /**
     * Adiciona una relación circulo-banco-cuenta (SIR_OP_CUENTAS_BANCARIAS)
     *
     * @param request círculo-banco-cuenta
     * @return
     * @throws AccionWebException Geremias Ortiz Lozano
     */
    private EvnAdministracionHermod adicionaCuentaBancoCirculo(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        
        ValidacionCuentasBancariasException exception = new ValidacionCuentasBancariasException();
        
        String idCirculo = request.getParameter(CCuentasBancarias.ID_CIRCULO);
        //request.getSession().setAttribute(CCirculoFestivo.ID_CIRCULO, idCirculo);
        if (idCirculo == null || idCirculo.trim().length() == 0 || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un círculo.");
        }
        
        String idBanco = request.getParameter(CCuentasBancarias.ID_BANCO);
        if ((idBanco == null) || (idBanco.trim().length() == 0) || idBanco.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar un banco.");
        }
        
        String idCuenta = request.getParameter(CCuentasBancarias.ID_CUENTA);
        if ((idCuenta == null) || (idCuenta.trim().length() == 0)) {
            exception.addError("Debe proporcionar un número de cuenta para el banco seleccionado.");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        CuentasBancarias cuentasBancarias = new CuentasBancarias();
        cuentasBancarias.setIdCirculo(idCirculo);
        cuentasBancarias.setIdBanco(idBanco);
        cuentasBancarias.setNroCuenta(idCuenta);
        cuentasBancarias.setActiva(true);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        Banco banco = new Banco();
        banco.setIdBanco(idBanco);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, AWAdministracionHermod.ADICIONA_CUENTA_BANCO_CIRCULO);
        evento.setCuentasBancarias(cuentasBancarias);
        evento.setCirculo(circulo);
        evento.setBanco(banco);
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        return evento;
    }
    
    private EvnAdministracionHermod cuentasXCirculoBanco(HttpServletRequest request) throws AccionWebException {
        
        HttpSession session = request.getSession();
        context = session.getServletContext();
        
        String idCirculo = request.getParameter(CCuentasBancarias.ID_CIRCULO);
        String idBanco = request.getParameter(CCuentasBancarias.ID_BANCO);
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        request.getSession().setAttribute(CCuentasBancarias.ID_CIRCULO, idCirculo);
        request.getSession().setAttribute(CCuentasBancarias.ID_BANCO, idBanco);
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, CUENTAS_X_CIRCULO_BANCO);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        Banco banco = new Banco();
        banco.setIdBanco(idBanco);
        
        evento.setCirculo(circulo);
        evento.setBanco(banco);
        return evento;
    }
    
    private Evento activaInactivaCtaBancaria(HttpServletRequest request) throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCuentasBancariasException exception = new ValidacionCuentasBancariasException();
        
        String idCirculo = request.getParameter(CCuentasBancarias.ID_CIRCULO);
        String idBanco = request.getParameter(CCuentasBancarias.ID_BANCO);
        String estadosCtasBancarias = request.getParameter(WebKeys.CUENTA_BANCARIA_ESTADOS);
        
        if (estadosCtasBancarias.length() <= 0) {
            exception.addError("Debe seleccionar por lo menos una cuenta bancaria para Activar/Inactivar");
        }

        //List estados = listaEstados(rolesEstacionesEstados);
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        Banco banco = new Banco();
        banco.setIdBanco(idBanco);
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ACT_INACT_CTA_BANCARIA);
        evento.setCirculo(circulo);
        evento.setBanco(banco);
        evento.setEstadosCtasBancarias(estadosCtasBancarias);
        
        return evento;
    }

    /**
     * Adiciona una relación circulo-banco-cuenta (SIR_OP_CUENTAS_BANCARIAS)
     *
     * @param request círculo-banco-cuenta
     * @return
     * @throws AccionWebException Geremias Ortiz Lozano
     */
    private EvnAdministracionHermod adicionaCanalRecaudo(HttpServletRequest request)
            throws AccionWebException {
        HttpSession session = request.getSession();
        
        ValidacionCanalesRecaudoException exception = new ValidacionCanalesRecaudoException();
        
        String nombreCanal = request.getParameter(CCanalesRecaudo.NOMBRE_CANAL_RECAUDO);
        
        if (nombreCanal == null || nombreCanal.trim().length() == 0) {
            exception.addError("Debe diligenciar el nombre del nuevo Canal de Recaudo");
        }
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        CanalesRecaudo canalesRecaudo = new CanalesRecaudo();
        canalesRecaudo.setNombreCanal(nombreCanal);
        canalesRecaudo.setActivo(true);
        
        EvnAdministracionHermod evento
                = new EvnAdministracionHermod(usuario, AWAdministracionHermod.ADICIONA_CANAL_RECAUDO);
        evento.setCanalesRecaudo(canalesRecaudo);
        
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        return evento;
    }
    
    private Evento activaInactivaCanalRecaudo(HttpServletRequest request) throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCanalesRecaudoException exception = new ValidacionCanalesRecaudoException();
        
        String estadosCanalesRecaudo = request.getParameter(WebKeys.CANALES_RECAUDO_ESTADOS);
        
        if (estadosCanalesRecaudo.length() <= 0) {
            exception.addError("Debe seleccionar por lo menos un canal de recaudo para Activar/Inactivar");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ACT_INACT_CANAL_RECAUDO);
        evento.setEstadosCanalesRecaudo(estadosCanalesRecaudo);
        
        return evento;
    }
    
    private Evento seleccionaCuentasBancarias(HttpServletRequest request) throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        ValidacionCuentasBancariasException exception = new ValidacionCuentasBancariasException();
        
        String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
        String idFormaPago = request.getParameter(CTipoPago.ID_TIPO_PAGO);
        
        System.out.println("CIRCULO REGISTRAL ENERO " + idCirculo);
        System.out.println("FORMA PAGO ENERO " + idFormaPago);
        
        request.getSession().setAttribute(CCirculo.ID_CIRCULO, idCirculo);
        request.getSession().setAttribute(CTipoPago.ID_TIPO_PAGO, idFormaPago);
        request.getSession().setAttribute(CCanalesRecaudo.ID_CANAL_RECAUDO, request.getParameter(CCanalesRecaudo.ID_CANAL_RECAUDO));
        
        if (idCirculo.length() <= 0) {
            exception.addError("Debe seleccionar por lo menos un círculo registral");
        }
        
        if (idFormaPago.length() <= 0) {
            exception.addError("Debe seleccionar por lo menos una forma de pago");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idCirculo);
        
        TipoPago tipoPago = new TipoPago();
        tipoPago.setIdTipoDocPago(Long.parseLong(idFormaPago));
        
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.SELECCIONA_CUENTA_BANCARIA);
        evento.setCirculo(circulo);
        evento.setTipoPago(tipoPago);
        
        return evento;
    }
    
    private Evento activaInactivaCtp(HttpServletRequest request) throws AccionWebException {
        
        HttpSession session = request.getSession();
        
        String idcirculo = request.getParameter(CCirculo.ID_CIRCULO);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(idcirculo);
        
        ValidacionCirculoTipoPagoException exception = new ValidacionCirculoTipoPagoException();
        
        String estadosCirculoTipoPago = request.getParameter(WebKeys.CIRCULO_TIPO_PAGO_ESTADOS);
        
        if (estadosCirculoTipoPago.length() <= 0) {
            exception.addError("Debe seleccionar por lo menos un elemento para Activar/Inactivar");
        }
        
        if (!exception.getErrores().isEmpty()) {
            throw exception;
        }
        
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAdministracionHermod evento = new EvnAdministracionHermod(usuario, EvnAdministracionHermod.ACT_INACT_CTP);
        evento.setEstadosCtp(estadosCirculoTipoPago);
        evento.setCirculo(circulo);
        
        return evento;
    }
    
}
