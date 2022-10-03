package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnRespTurnoManualRegistro;
import gov.sir.core.eventos.administracion.EvnTurnoManualRegistro;
import gov.sir.core.eventos.certificado.EvnCertificado;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.comun.EvnRespPago;
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
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCheckedItem;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoActoDerechoRegistral;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CLiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.constantes.CSolicitudAsociada;
import gov.sir.core.negocio.modelo.constantes.CSolicitudCheckedItem;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CSubtipoAtencion;
import gov.sir.core.negocio.modelo.constantes.CTipoCalculo;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.constantes.CRespuestaCalificacion;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.CrearTurnoException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionRegistroException;
import gov.sir.core.web.acciones.excepciones.ValidacionMatriculaRegistroException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosActoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosRegistroException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicConditionalValidator;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringNotNullOrEmptyValidatorWrapper;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


/**
 * @author jfrias
 */
public class AWTurnoManualRegistro extends SoporteAccionWeb {

    // edicion-turno-vinculado -------------------------------------------
    public static final String REGISTRO_VINCULARTURNO_ADDITEM_ACTION =
            "REGISTRO_VINCULARTURNO_ADDITEM_ACTION";
    public static final String REGISTRO_VINCULARTURNO_DELITEM_ACTION =
            "REGISTRO_VINCULARTURNO_DELITEM_ACTION";

    // -------------------------------------------------------------------

    /**
     * Indicador de la accion web agregar.
     * <p>
     * Agregar descripcion aca.
     */
    public final static String AGREGAR = "AGREGAR";

    /**
     * Indicador de la accion web eliminar.
     * <p>
     * Agregar descripcion aca.
     */
    public final static String ELIMINAR = "ELIMINAR";

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
	 * siguiente etapa esta sea cancelada (pagada). A diferencia de liquidar, esta acción permite
	 * procesar el pago de la solicitud de registro.
	 */
	public final static String LIQUIDAR_CONTINUAR = "LIQUIDAR_CONTINUAR";

	/**
	 * Indicador de la accion web que edita una liquidación.
	 * <p>
	 * Accion en la cual se obtienen los datos de la pagina, para crear una
	 * liquidacion, añadirla a la solicitud de registro, y editar una liquidación existente
	 * con la nueva información.
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
     * Indicador de la accion web eliminar un acto
     * <p>
     * Accion en la cual se elimina uno de los actos anteriormente agregados.
     */
    public final static String ELIMINAR_ACTO = "ELIMINAR_ACTO";

    /**
     * Indicador de la accion web cargar derechos
     * <p>
     * Colocar descripcion aca.
     */
    public final static String CARGAR_DERECHOS = "CARGAR_DERECHOS";

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
    //public final static String ADICIONAR_CERTIFICADO_ASOCIADO = "ADICIONAR_CERTIFICADO_ASOCIADO";

    /**
     * Indicador de la accion web de eliminar un certificados asociado.
     * <p>
     * Accion en la cual se elimina un certificado asociado a la solicitud de
     * registro.
     */
    //public final static String ELIMINA_CERTIFICADO_ASOCIADO = "ELIMINA_CERTIFICADO_ASOCIADO";

    /**
     * Indicador de la accion web de crear un nuevo certificado asociado.
     * <p>
     * Accion en la cual se lleva a la pantalla para crear un nuevo certificado
     * asociado.
     */
    //public final static String NUEVO_CERTIFICADO_ASOCIADO = "NUEVO_CERTIFICADO_ASOCIADO";

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

    /***/
    public final static String ELIMINAR_TURNO_ANTERIOR = "ELIMINAR_TURNO_ANTERIOR";

    /***/
    //public final static String AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO = "AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO";

    /***/
    //public final static String ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO = "ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO";

    /***/
    //public final static String AGREGAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA =
    //    "AGREGAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA";

    /***/
    //public final static String AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION = "AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION";

    /***/
    //public final static String ELIMINAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA =
    //    "ELIMINAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA";

    /***/
    //public final static String ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION = "ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION";

    /***/

    // public final static String CANCELAR_ADICION_CERTIFICADO_ASOCIADO =
    // "CANCELAR_ADICION_CERTIFICADO_ASOCIADO";

    /***/
    public final static String VER_ANTIGUO_SISTEMA = "VER_ANTIGUO_SISTEMA";

    /***/
    public final static String ID_SOLICITUD = "ID_SOLICITUD";

    /***/
    public final static String BUSCAR_SOLICITUD = "BUSCAR_SOLICITUD";

	/***/
	public static final String BUSCAR_SOLICITUD_EDICION = "BUSCAR_SOLICITUD_EDICION";

    /**
     * Constante que define que debe buscar la solicitud para realizar el pago
     * de registro
     */
    public final static String BUSCAR_SOLICITUD_PAGO = "BUSCAR_SOLICITUD_PAGO";

    /***/
    public final static String CREAR_TURNO = "CREAR_TURNO";
    
    public final static String TERMINA = "TERMINA";

    /***/
    private static final String SOLICITUD = "SOLICITUD";

    // VARIABLES DE SESION PARA EL CERTIFICADO ASOCIADO

    /***/
    //public final static String LISTA_CERTIFICADOS_ASOCIADOS = "LISTA_CERTIFICADOS_ASOCIADOS";

    /***/
    //public final static String LISTA_MATRICULAS_CERTIFICADO_ASOCIADO = "LISTA_MATRICULAS_CERTIFICADO_ASOCIADO";

    /***/
    //public final static String CERTIFICADO_ASOCIADO_NUM_MATRICULAS = "CERTIFICADO_ASOCIADO_NUM_MATRICULAS";

    /***/
    //public final static String CERTIFICADO_ASOCIADO_ID_MATRICULA = "CERTIFICADO_ASOCIADO_ID_MATRICULA";

    /***/
    //public final static String CAPTURANDO_CERTIFICADO_ASOCIADO = "CAPTURANDO_CERTIFICADO_ASOCIADO";

    /***/
    //public final static String CERTIFICADO_ASOCIADO_ITEM = "CERTIFICADO_ASOCIADO_ITEM";

    /***/
    public final static String AGREGAR_MATRICULA_REGISTRO = "AGREGAR_MATRICULA_REGISTRO";

    /**Variable para diferenciar si se esta editando una liquidación o si se esta creando*/
	public final static String ES_EDICION_LIQUIDACION = "ES_EDICION_LIQUIDACION";
	
	public final static String NUMERO_RECIBO = "NUMERO_RECIBO";
	public final static String FECHA_INICIO = "FECHA_INICIO";
	public final static String TURNO_CONSECUTIVO = "TURNO_CONSECUTIVO";
	public final static String TURNO_CIRCULO = "TURNO_CIRCULO";
	public final static String TURNO_ANIO = "TURNO_ANIO";

    /**  */
    public AWTurnoManualRegistro() {
        super();
    }

    /**
     * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        String accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion válida");
        }

        this.preservarParametros(request);

        // turno-vinculado  ----------------------------------------------
        if( REGISTRO_VINCULARTURNO_ADDITEM_ACTION.equals( accion ) ) {
            return doRegistro_VincularTurno_AddItem( request );
        }
        else if( REGISTRO_VINCULARTURNO_DELITEM_ACTION.equals( accion ) ) {
            return doRegistro_VincularTurno_DelItem( request );
        }
        // -----------------------------------------------
        else if (accion.equals(LIQUIDAR)) {
            return liquidar(request);
        } else if (accion.equals(LIQUIDAR_CONTINUAR)) {
			return liquidar(request);
		} else if (accion.equals(EDITAR_LIQUIDACION)) {
			return editarLiquidacion(request);
		} else if (accion.equals(LIQUIDAR_REGISTRO)) {
            return liquidarRegistro(request);
        } else if (accion.equals(AGREGAR)) {
            return agregarMatricula(request);
        } /*else if (accion.equals(AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO)) {
            return agregarMatriculaAsociada(request);
        } */else if (accion.equals(ELIMINAR)) {
            return eliminarMatricula(request);
        } else if (accion.equals(PRESERVAR_INFO)) {
            return preservarInfo(request);
        } else if (accion.equals(PRESERVAR_INFO_LIQUIDAR)) {
            preservarInfoLiquidar(request);

            return null;
        } else if (accion.equals(AGREGAR_ACTO)) {
            return agregarActo(request);
        } else if (accion.equals(ELIMINAR_ACTO)) {
            return eliminarActo(request);
        } else if (accion.equals(CARGAR_DERECHOS)) {
            return cargarDerechos(request);
        } else if (accion.equals(CARGAR_DOCUMENTOS)) {
            return cargarDocumentos(request);
        } else if (accion.equals(VALIDAR_TURNO_ANTERIOR)) {
            return validarTurnoAnterior(request);
        } /*else if (accion.equals(ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO)) {
            return eliminarMatriculaCertificadoAsociado(request);
        } *//*else if (accion.equals(
                    AGREGAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA)) {
            return agregarCertificadosAntiguoSistema(request);
        } *//*else if (accion.equals(AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION)) {
            return agregarCertificadosSegregacion(request);
        } *//*else if (accion.equals(
                    ELIMINAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA)) {
            return eliminarCertificadosAntiguoSistema(request);
        } *//*else if (accion.equals(ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION)) {
            return eliminarCertificadosSegregacion(request);
        } */else if (accion.equals(ELIMINAR_TURNO_ANTERIOR)) {
            return eliminarTurnoAnterior(request);
        } else if (accion.equals(VER_ANTIGUO_SISTEMA)) {
            return verAntiguoSistema(request);
        } else if (accion.equals(AWTurnoManualRegistro.BUSCAR_SOLICITUD) ||
                accion.equals(AWTurnoManualRegistro.BUSCAR_SOLICITUD_PAGO)) {
            return buscarSolicitud(request);
        } else if (accion.equals(AWTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION)) {
            return buscarSolicitudEdicion(request);
        } else if (accion.equals(AWTurnoManualRegistro.VALIDAR_SOLICITUD)) {
			return validarSolicitud(request);
		} else if (accion.equals(
                    AWTurnoManualRegistro.INCREMENTAR_SECUENCIAL_RECIBO)) {
            return incrementarSecuencialRecibo(request);
        } else if (accion.equals(AWTurnoManualRegistro.CREAR_TURNO)) {
            return crearTurnoRegistro(request);
        } else if (accion.equals(AWTurnoManualRegistro.TERMINA)) {
        	return termina(request);
        } else {
            throw new AccionInvalidaException("La accion " + accion +
                " no es valida.");
        }
    }
    private Evento termina(HttpServletRequest request) {

    	HttpSession session = request.getSession();

        session.removeAttribute(CTurno.TURNO_ANTERIOR);
        session.removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        session.removeAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

        session.removeAttribute(CSolicitudRegistro.COMENTARIO);
        session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
        session.removeAttribute(CSolicitudRegistro.CALENDAR);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
        */
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
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
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
        */
        session.removeAttribute("version");
        
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
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
        session.removeAttribute(CDatosAntiguoSistema.COMENTARIO_AS);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
         /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
        */
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION);
        session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
        
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
        
        session.removeAttribute(AWTurnoManualRegistro.TURNO_CONSECUTIVO);
        session.removeAttribute(AWTurnoManualRegistro.TURNO_ANIO);
        session.removeAttribute(AWTurnoManualRegistro.TURNO_CIRCULO);
        session.removeAttribute(AWTurnoManualRegistro.NUMERO_RECIBO);
        session.removeAttribute(AWTurnoManualRegistro.FECHA_INICIO);
		session.removeAttribute(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION);
        
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

        session.removeAttribute( CTurno.SOLICITUD_VINCULADA );

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
    doRegistro_VincularTurno_AddItem( HttpServletRequest request )
    throws AccionWebException {

      HttpSession session = request.getSession();

      // parametro con id de solicitud vincualda
      final String PARAM_SOLICITUD_VINCULADA = "SOLICITUD_VINCULADA";

      org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

      ValidacionLiquidacionRegistroException exception = new ValidacionLiquidacionRegistroException();

      session.removeAttribute( CTurno.SOLICITUD_VINCULADA );
      String param_SolicitudVinculadaId = request.getParameter( PARAM_SOLICITUD_VINCULADA ); //  CTurno.TURNO_VINCULADO

      if( null == param_SolicitudVinculadaId )
          param_SolicitudVinculadaId = "";

      BasicConditionalValidator validator;
      validator = new BasicStringNotNullOrEmptyValidatorWrapper();
      validator.setObjectToValidate( param_SolicitudVinculadaId );
      validator.validate();

      if( validator.getResult() != true ) {
          exception.addError( "solicitud-vinculada; debe indicar una cadena valida" );
      }


      gov.sir.core.negocio.modelo.SolicitudVinculada localItem
       = new gov.sir.core.negocio.modelo.SolicitudVinculada();

      // decompose test; delegated validation

      String anno    = null;
      String circ    = null;
      String proceso = null;
      String turnoId = null;

      decompose_SolicitudId: {


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
      EvnTurnoManualRegistro evento = null;

      validate_SolicitudId: {

          if( ( param_SolicitudVinculadaId != null )
           && ( anno != null )
           && ( circ != null )
           && ( proceso != null )
           && ( turnoId != null ) ) {

            param_SolicitudVinculadaId = param_SolicitudVinculadaId.trim();
            param_SolicitudVinculadaId = param_SolicitudVinculadaId.toUpperCase();
            // fix pk: solicitud hija
            localItem.setIdSolicitud( param_SolicitudVinculadaId );

            gov.sir.core.negocio.modelo.Proceso thisProceso
             = (gov.sir.core.negocio.modelo.Proceso) session.getAttribute( WebKeys.PROCESO );

            if( ( null != thisProceso )
              &&( thisProceso.getIdProceso() != Long.parseLong( proceso ) ) ) {
              exception.addError( "solicitud-vinculada; Solicitud no es del mismo proceso" + " (" + param_SolicitudVinculadaId + ")" );
            }
            else {
                                    // session.setAttribute(CTurno.TURNO_ANTERIOR, idWfTurno);
                                    evento = new EvnTurnoManualRegistro( usuario );
                                    evento.setTipoEvento( EvnTurnoManualRegistro.REGISTRO_VINCULARTURNO_ADDITEM_EVENT );
                                    evento.setSolicitudVinculada( localItem );
            }
          }
          else {
            exception.addError( "solicitud-vinculada; El codigo de solicitud no esta completo" );
          }

      } //:validate_SolicitudId


      // ------------------------------------------------------------------------------
      // raise app error

      if( exception.getErrores().size() > 0 ) {
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
     * @param request
     *            Tiene la información del formulario
     * @return Un <CODE>EvnTurnoManualRegistro</CODE> nulo, ya que no se
     *         necesita generar un evento
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
         /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
        */
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);

        session.removeAttribute(CTurno.TURNO_ANTERIOR);

        return null;
    }

    /**
     * Se utiliza para maximizar/minimizar los datos de antiguo sistema de la
     * solicitud En el parámetro VER_ANTIGUO_SISTEMA de la solicitud viene la
     * información para maximizar o minimizar la información de antiguo sistema.
     * Este parámetro se pone en la sesión, para que cuando se recargue la
     * página se lea este atributo de la sesión.
     *
     * @param request
     *            Tiene la información del formulario
     * @return Un <CODE>EvnTurnoManualRegistro</CODE> nulo, ya que no se
     *         necesita generar un evento
     */
    private EvnTurnoManualRegistro verAntiguoSistema(HttpServletRequest request) {
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
     * @param request
     *            Tiene la información del formulario
     * @return Un evento de tipo <CODE>EvnTurnoManualRegistro</CODE> con el
     *         usuario y el turno que se quiere validar
     * @throws AccionWebException
     */
    private EvnTurnoManualRegistro validarTurnoAnterior(
        HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        // Turno turnoAnterior = new Turno();
        ValidacionLiquidacionRegistroException exception = new ValidacionLiquidacionRegistroException();
        session.removeAttribute(CTurno.TURNO_ANTERIOR);

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

        Turno turno = new Turno();
        EvnTurnoManualRegistro evento = null;

        if ((idWfTurno != null) && (anno != null) && (circ != null) &&
                (proceso != null) && (turnoId != null)) {
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
                evento = new EvnTurnoManualRegistro(usuario, turno);
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
     * @param request
     *            Tiene la información del formulario
     * @return Un <CODE>EvnTurnoManualRegistro</CODE> nulo, ya que no se
     *         necesita generar un evento
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    private EvnTurnoManualRegistro cargarDocumentos(HttpServletRequest request) {
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
     * @param request
     *            La información del formulario
     * @return Un <CODE>EvnTurnoManualRegistro</CODE> nulo, ya que no se
     *         necesita generar un evento
     * @throws AccionWebException
     * @see gov.sir.core.negocio.modelo.TipoActoDerechoRegistral
     */
    private EvnTurnoManualRegistro cargarDerechos(HttpServletRequest request)
        throws AccionWebException {
        HttpSession session = request.getSession();
        String tipo = request.getParameter(CActo.TIPO_ACTO);
        ValidacionParametrosActoException excepcion = new ValidacionParametrosActoException();

        if ((tipo == null) || tipo.equals("SIN_SELECCIONAR")) {
            excepcion.addError("Tipo de acto inválido");
        }

        if (excepcion.getErrores().size() > 0) {
            preservarInfoLiquidar(request);
            throw excepcion;
        }

        // La lista de tipos de acto está en el ServletContext bajo la llave
        // LISTA_TIPOS_ACTO
        List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ACTO);

        Iterator it = tipos.iterator();
        List derechos = new ArrayList();

        while (it.hasNext()) {
            TipoActo sub = (TipoActo) it.next();

            if (tipo.equals(sub.getIdTipoActo())) {
                List checked = sub.getTiposDerechosRegistrales();
                Iterator it2 = checked.iterator();

                while (it2.hasNext()) {
                    TipoActoDerechoRegistral ch = (TipoActoDerechoRegistral) it2.next();
                    derechos.add(new ElementoLista(ch.getTipoDerechoRegistral()
                                                     .getIdTipoDerechoReg(),
                            ch.getTipoDerechoRegistral().getNombre()));
                }

                // La lista de tipos de derechos registrales, se guarda en la
                // sesión bajo la llave LISTA_TIPOS_DERECHOS_REGISTRALES
                session.setAttribute("LISTA_TIPOS_DERECHOS_REGISTRALES",
                    derechos);
                preservarInfoLiquidar(request);

                // guardar el tipo calculo en la session.
                session.setAttribute(CActo.TIPO_CALCULO, CTipoCalculo.MANUAL);
                session.setAttribute(CActo.NOMBRE_TIPO_CALCULO,
                    CTipoCalculo.NOMBRE_MANUAL);

                return null;
            }
        }

        preservarInfoLiquidar(request);

        return null;
    }

    /**
     * Para eliminar una acto de la solicitud, que fue ingresado anteriormente
     *
     * @param request
     *            La información del formulario
     * @return Un <CODE>EvnTurnoManualRegistro</CODE> nulo, ya que no se
     *         necesita generar un evento
     */
    private EvnTurnoManualRegistro eliminarActo(HttpServletRequest request) {
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
                Acto itemActual = (Acto) session.getAttribute(CActo.ACTO +
                        actual.toString());
                session.setAttribute(CActo.ACTO + mover.toString(), itemActual);
                session.removeAttribute(CActo.ACTO + actual.toString());
            }
        }

        val--;
        session.setAttribute(CActo.NUM_ACTOS, new Integer(val));

        preservarInfoLiquidar(request);

        return null;
    }

    /**
     * Para agregar un acto a la solicitud. Con los datos ingresados por el
     * usuario, el servicio Hermod debe calcular el valor del acto
     *
     * @param request
     *            Los datos del formulario
     * @return Un evento <CODE>EvnTurnoManualRegistro</CODE> con el usuario y
     *         el <CODE>Acto</CODE>
     * @see gov.sir.core.negocio.modelo.Acto
     * @throws AccionWebException
     */
    private EvnTurnoManualRegistro agregarActo(HttpServletRequest request)
        throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionParametrosActoException excepcion = new ValidacionParametrosActoException();
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        //org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        // String nombreCampo = CActo.ACTO+ num.toString();
        // String acto= request.getParameter(nombreCampo);
        String tipoActo = request.getParameter(CActo.TIPO_ACTO);
        String tipoDerecho = request.getParameter(CActo.TIPO_DERECHO);
        String valorActo = request.getParameter(CActo.VALOR_ACTO);

        String seCobraImp = request.getParameter(CActo.COBRA_IMPUESTO);
        //String cuantia = request.getParameter(CActo.CUANTIA);
        String tipoTarifa = request.getParameter(CActo.TIPO_TARIFA);
        String valorImp = request.getParameter(CActo.VALOR_IMPUESTO);
        String valorDerechos = request.getParameter(CActo.VALOR_DERECHOS);

        if ((tipoActo == null) || tipoActo.equals("SIN_SELECCIONAR")) {
            excepcion.addError("Tipo de acto inválido");
        }

        if ((tipoDerecho == null) || tipoDerecho.equals("SIN_SELECCIONAR")) {
            excepcion.addError("Tipo de derecho inválido");
        }

        if ((valorActo == null) || valorActo.trim().equals("")) {
            excepcion.addError("Valor del acto inválido");
        } else {
	        try {
	            Double.parseDouble(valorActo);
	        } catch (NumberFormatException e) {
	            excepcion.addError("Valor del acto inválido");
	        }
        }
        
        if((valorDerechos == null) || valorDerechos.trim().equals("")) {
        	excepcion.addError("Valor de derechos del acto inválido");
        } else {
	        try {
	        	double v = Double.parseDouble(valorDerechos);
	        	if(v < 0) {
	        		excepcion.addError("Valor de derechos del acto inválido");
	        	}
	        } catch (NumberFormatException e) {
	        	excepcion.addError("Valor de derechos del acto inválido");
	        }
        }
        
        if(seCobraImp != null && seCobraImp.trim().equals("on")) {
        	if(tipoTarifa != null || !tipoTarifa.equals("SIN_SELECCIONAR")) {
        		if(valorImp == null || valorImp.trim().equals("")) {
        			excepcion.addError("Valor de impuestos del acto inválido");
        		} else {
        			try {
        	        	double v = Double.parseDouble(valorImp);
        	        	if(v < 0) {
        	        		excepcion.addError("Valor de impuestos del acto inválido");
        	        	}
        	        } catch (NumberFormatException e) {
        	        	excepcion.addError("Valor de impuestos del acto inválido");
        	        }
        		}
        	} else {
        		excepcion.addError("Tipo de tarifa inválido");
        	}
        }

        if (excepcion.getErrores().size() > 0) {
            preservarInfoLiquidar(request);
            throw excepcion;
        }

        // Se busca el tipo de acto escogido por el usuario
        List tiposActo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ACTO);
        Iterator itA = tiposActo.iterator();
        Acto acto = new Acto();
        TipoActo actType = null;

        while (itA.hasNext()) {
            TipoActo temp = (TipoActo) itA.next();

            if (tipoActo.equals(temp.getIdTipoActo())) {
                actType = temp;

                break;
            }
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

        double actValue = 0, actCuantia = 0;

        actValue = Double.parseDouble(valorDerechos);
        actCuantia = Double.parseDouble(valorActo);
        
		actType.getTipoCalculo().setIdTipoCalculo(CActo.MANUAL);

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
                    tipoTarifa = CActo.NORMAL;

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

                if ((tipoTarifa == null) ||
                        tipoTarifa.equals("SIN_SELECCIONAR")) {
                    excepcion.addError("Tipo de tarifa inválida");
                }
            }
        }

        if (excepcion.getErrores().size() > 0) {
            preservarInfoLiquidar(request);
            throw excepcion;
        }

        // Cuantía o cantidad de actos a registrar ingresada por el usuario
        acto.setCuantia(actCuantia);

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
        liq.setSolicitud(sol);
        acto.setLiquidacion(liq);
        eliminarInfoActoAsociada(request);
        
        // Cuando no se envía un evento hasta la acción de negocio, es necesario
        // realizar toda la plomería desde aquí
        session.removeAttribute(CActo.TIPO_ACTO);
        session.removeAttribute(CActo.TIPO_DERECHO);
        session.removeAttribute(CActo.VALOR_ACTO);
        session.removeAttribute(CActo.VALOR_DERECHOS);
        session.removeAttribute(CActo.COBRA_IMPUESTO);
        session.removeAttribute(CActo.IMPUESTO);
        session.removeAttribute(CActo.TIPO_TARIFA);
        session.removeAttribute(CActo.TIPO_CALCULO);
        session.removeAttribute(CActo.VALOR_IMPUESTO);
        session.removeAttribute("LISTA_TIPOS_DERECHOS_REGISTRALES");

        Integer num = (Integer) session.getAttribute(CActo.NUM_ACTOS);
        session.setAttribute(CActo.ACTO + num.toString(), acto);

        int v = num.intValue();
        v++;

        Integer nuevo = new Integer(v);

        session.setAttribute(CActo.NUM_ACTOS, nuevo);

        //return new EvnTurnoManualRegistro(usuario, acto);
        return null;
    }

    /**
     * Eliminar de session los datos del acto.
     *
     * @param request
     *            petición del usuario
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
     * @param request
     *            La información del formulario
     * @return Un evento <CODE>EvnTurnoManualRegistro</CODE> con el usuario y
     *         la solicitud de registro creada
     * @throws AccionWebException
     */
    private EvnTurnoManualRegistro liquidarRegistro(HttpServletRequest request)
        throws AccionWebException {
        HttpSession session = request.getSession();
        ValidacionLiquidacionRegistroException exception = new ValidacionLiquidacionRegistroException();
        SolicitudRegistro solicitud = new SolicitudRegistro();
        String turno = request.getParameter(CTurno.TURNO_ANTERIOR);

        String comentario = request.getParameter(CSolicitudRegistro.COMENTARIO);
        
        
        
        
		// Año del turno
        String sAnio = "";
		/*String sAnio = request.getParameter(AWTurnoManualCertificado.TURNO_ANIO);
		int iAnio = 0;
		if(sAnio == null || sAnio.equals("")) {
			exception.addError("Tiene que ingresar el año del turno");
		}
		else {
			try {
				iAnio = Integer.parseInt(sAnio);
				if(iAnio < 0) {
					exception.addError("El valor del año del turno es inválido");
				}
			} catch(NumberFormatException nfException) {
				exception.addError("El valor del año del turno es inválido");
				iAnio = 0;
			}
		}*/
        
        // Círculo del turno
        String sCirculoTurno = request.getParameter(TURNO_CIRCULO);
        if(sCirculoTurno == null || sCirculoTurno.equals("")) {
			exception.addError("Tiene que ingresar un círculo para el turno");
		}
        
        Circulo circulo = null;
        //circulo.setIdCirculo(sCirculoTurno);
        
        //List circulos = (List)request.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        List circulos = (List)session.getServletContext().getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        if(circulos != null) {
	        for(Iterator itCirculos = circulos.iterator(); itCirculos.hasNext();) {
	        	Circulo circuloTemp = (Circulo)itCirculos.next();
	        	if(circuloTemp.getIdCirculo().equalsIgnoreCase(sCirculoTurno)) {
	        		circulo = circuloTemp;
	        		break;
	        	}
	        }
        }
        
        if(circulo == null) {
        	exception.addError("El círculo digitado es inválido");
        }
			
		// ID del turno
		String sConsecutivoTurno = request.getParameter(AWTurnoManualCertificado.TURNO_CONSECUTIVO);
		if(sConsecutivoTurno == null || sConsecutivoTurno.equals("")) {
			exception.addError("Tiene que ingresar un consecutivo para el turno");
		}
		if(sConsecutivoTurno != null){
			try{
				Long cons = new Long(sConsecutivoTurno);
			}catch(java.lang.NumberFormatException e){
				exception.addError("El valor del consecutivo es inválido");
			}
		}
		
		// Número de recibo
		String sNumeroRecibo = request.getParameter(AWTurnoManualCertificado.NUMERO_RECIBO);
		if(sNumeroRecibo == null || sNumeroRecibo.equals("")) {
			exception.addError("Tiene que ingresar un número de recibo");
		}

        /**
         * @author: Fernando Padilla
         * @change: Se valida que la respuesta de la resolución del resultado
         *          de calificación haya sido seleccionada.
        **/
        //Se campura el historial del turno
        String respuestaCalificacion = request.getParameter(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION);
		if(respuestaCalificacion == null) {
			exception.addError("Debe seleccionar la respuesta de la resolución del resultado de la calificación");
		}


		// Obtención de la fecha de inicio
		String fechaInicio = request.getParameter(AWTurnoManualCertificado.FECHA_INICIO);
		Calendar cFecha = Calendar.getInstance();
		if((fechaInicio == null) || fechaInicio.equals("")) {
			exception.addError("Debe ingresar una fecha de inicio para el turno");
		} else {
			// Se valida que el formato de la fehca sea válido
			Date fFecha = null;
			try {
				fFecha = DateFormatUtil.parse(fechaInicio);
				Calendar cCalendario = Calendar.getInstance();
				cCalendario.setTime(fFecha);
				sAnio = String.valueOf(cCalendario.get(Calendar.YEAR));
				session.setAttribute(AWTurnoManualCertificado.TURNO_ANIO, sAnio);
			} catch (ParseException e) {
				exception.addError("Debe ingresar un formato válido para la fecha de radicación");
				fFecha = null;
			}
			
			// Se valida que la fecha de inicio del turno no sea inconsistente con el año para el
			// que se está creando el turno.
			/*if(fFecha != null) {
				cFecha.setTime(fFecha);
				if(cFecha.get(Calendar.YEAR) > iAnio) {
					exception.addError("La fecha de inicio del turno no puede ser mayor al año en que se radicó el turno");
				}
			}*/
		}

        // En este arreglo de strings están los documentos entregados que el
        // usuario chequeó
        //String[] entregados = request.getParameterValues(CSolicitudCheckedItem.DOCUMENTOS_ENTREGADOS);

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

        if ((idVereda == null) || nomDepto.equals("") || nomMunic.equals("")) {
            List listaVeredas = (List) request.getSession().getServletContext()
                                              .getAttribute(WebKeys.LISTA_DEPARTAMENTOS);
            idVereda = ValidacionDatosOficinaOrigen.obtenerVereda(listaVeredas,
                    idDepto, idMunic);
        }

       
        //String tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);

        //String numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
        String id_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);

        String subtipo = request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD);
        List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
        String id_tipo_encabezado = request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO);

        // Antiguo Sistema
        String libro_tipo_as = request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS);
        libro_tipo_as = (libro_tipo_as == null) ? "" : libro_tipo_as;

        String libro_numero_as = request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
        libro_numero_as = (libro_numero_as == null) ? "" : libro_numero_as;

        String libro_pagina_as = request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
        libro_pagina_as = (libro_pagina_as == null) ? "" : libro_pagina_as;

        String libro_ano_as = request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS);
        libro_ano_as = (libro_ano_as == null) ? "" : libro_ano_as;

        String tomo_numero_as = request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS);
        tomo_numero_as = (tomo_numero_as == null) ? "" : tomo_numero_as;

        String tomo_pagina_as = request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS);
        tomo_pagina_as = (tomo_pagina_as == null) ? "" : tomo_pagina_as;

        String tomo_municipio_as = request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
        tomo_municipio_as = (tomo_municipio_as == null) ? "" : tomo_municipio_as;

        String tomo_ano_as = request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS);
        tomo_ano_as = (tomo_ano_as == null) ? "" : tomo_ano_as;

        String documento_tipo_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
        documento_tipo_as = (documento_tipo_as == null) ? "" : documento_tipo_as;

        String documento_numero_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
        documento_numero_as = (documento_numero_as == null) ? ""
                                                            : documento_numero_as;

        String documento_comentario_as = request.getParameter(CSolicitudRegistro.COMENTARIO);
        documento_comentario_as = (documento_comentario_as == null) ? ""
                                                                    : documento_comentario_as;

        String documento_fecha_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
        documento_fecha_as = (documento_fecha_as == null) ? ""
                                                          : documento_fecha_as;

        String comentario_as = request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS);
        comentario_as = (comentario_as == null) ? "" : comentario_as;

        String oficina_depto_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
        oficina_depto_id_as = (oficina_depto_id_as == null) ? ""
                                                            : oficina_depto_id_as;

        String oficina_depto_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
        oficina_depto_nom_as = (oficina_depto_nom_as == null) ? ""
                                                              : oficina_depto_nom_as;

        String oficina_muni_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
        oficina_muni_id_as = (oficina_muni_id_as == null) ? ""
                                                          : oficina_muni_id_as;

        String oficina_muni_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
        oficina_muni_nom_as = (oficina_muni_nom_as == null) ? ""
                                                            : oficina_muni_nom_as;

        String oficina_vereda_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
        oficina_vereda_id_as = (oficina_vereda_id_as == null) ? ""
                                                              : oficina_vereda_id_as;

        String oficina_vereda_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
        oficina_vereda_nom_as = (oficina_vereda_nom_as == null) ? ""
                                                                : oficina_vereda_nom_as;

        String oficina_oficia_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
        oficina_oficia_id_as = (oficina_oficia_id_as == null) ? ""
                                                              : oficina_oficia_id_as;
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        String oficina_oficia_version_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION);
        oficina_oficia_version_as = (oficina_oficia_version_as == null) ? "0"
                                                              : oficina_oficia_version_as;
        
        String oficina_oficia_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
        oficina_oficia_nom_as = (oficina_oficia_nom_as == null) ? ""
                                                                : oficina_oficia_nom_as;

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

        if (tipo_encabezado.equals("SIN_SELECCIONAR") &&
                id_tipo_encabezado.equals("")) {
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
            // tipo de oficina internacional
            if ((oficinaInternacional == null) ||
                    (oficinaInternacional.length() == 0)) {
                exception.addError(
                    "El campo Oficina Ubicación Internacional no puede estar en blanco");
            }
        }

		Calendar fechaDocumento;
		if(fecha!=null)
			fechaDocumento = darFecha(fecha);
		else
			fechaDocumento=null;

        if (fechaDocumento == null) {
            exception.addError("La fecha del documento es invalida");
        }

        if (fechaDocumento!=null && !fechaDocumento.before(cFecha)){
        	exception.addError("La fecha del documento debe ser anterior a la creación del turno");
        }
        
        if (((matriculas == null) || matriculas.isEmpty()) &&
                !subtipo.equals(
                    CDatosAntiguoSistema.CODIGO_SUBTIPO_LIQUIDACION_ANTIGUO_SISTEMA)) {
            exception.addError("Debe existir al menos una matricula asociada");
        }

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
        if (subtipo.equals(
                    CDatosAntiguoSistema.CODIGO_SUBTIPO_LIQUIDACION_ANTIGUO_SISTEMA)) {
            das = new DatosAntiguoSistema();

            //StringBuffer sb = new StringBuffer("");
            StringBuffer sbDocAs = new StringBuffer("");
            StringBuffer sbOfiAs = new StringBuffer("");
            StringBuffer sbTipoDocAS = new StringBuffer("");
            Date docFecha = null;

            /*sb.append(libro_tipo_as);
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
            sb.append(oficina_oficia_nom_as);*/

            if (documento_fecha_as.length() > 0) {
                try {
                    docFecha = DateFormatUtil.parse(documento_fecha_as);
                } catch (ParseException e) {
                    exception.addError("El campo Fecha del documento de " +
                        "antiguo sistema no es válido");
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
            oficinaOrigenAS.setNombre(oficina_oficia_nom_as);
            oficinaOrigenAS.setVereda(veredaAS);
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            oficinaOrigenAS.setVersion(Long.parseLong(oficina_oficia_version_as));

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
                        Log.getInstance().debug(AWTurnoManualRegistro.class, 
                            "[Colocando en Antiguo sistema Oficina de origen]");
                    }

                    Log.getInstance().debug(AWTurnoManualRegistro.class, 
                        "[Colocando en Antiguo sistema tipo documento]");
                }

                Log.getInstance().debug(AWTurnoManualRegistro.class, 
                    "[Colocando en datos de actiguo sistema el documento]");
            }

            /**
            * @author: Henry Gomez Rocha
            * @change: Se valida que la variable que almacena la información de la caja de texto "Comentario (Datos Antiguo Sistema)" contenga informacion.
            * MANTIS: 0003999
            **/
            if(comentario_as == null || comentario_as.equals(""))
            	exception.addError("Debe digitar un comentario para Antiguo Sistema");

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

        //LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();

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

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        //Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

        solicitud.setCirculo(circulo);

        /**
        * @author: Henry Gomez Rocha
        * MANTIS: 0003999
        **/
        solicitud.setComentario(documento_comentario_as);
        //solicitud.setFecha(new Date(System.currentTimeMillis()));
       	Date fechaI = null;
		try {
			fechaI = DateFormatUtil.parse(fechaInicio);
			solicitud.setFecha(fechaI);
		} catch (java.text.ParseException e) {
			solicitud.setFecha(new Date(System.currentTimeMillis()));
		}
				
        
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
            oficinaOrigen.setVersion(Long.parseLong(oficinaVersion));

            Vereda vereda = new Vereda();

            if (nomVereda != null) {
                vereda.setNombre(nomVereda);
            }

            vereda.setIdVereda(valorVereda);
            vereda.setIdDepartamento(valorDepartamento);
            vereda.setIdMunicipio(valorMunicipio);

            oficinaOrigen.setVereda(vereda);

            documento.setOficinaOrigen(oficinaOrigen);
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
		EvnTurnoManualRegistro evnLiq = new EvnTurnoManualRegistro(usuario, solicitud);
		evnLiq.setUsuarioNec(usuarioSIR);
		evnLiq.setUsuarioSIR(usuarioSIR);
		evnLiq.setAnio(sAnio);
		evnLiq.setIdTurno(sConsecutivoTurno);
		evnLiq.setCirculo(circulo);
		
        return evnLiq;
    }

    /**
     * Utilizado para preservar la información ingresada anteriormente cuando se
     * recarga la página en la segunda pantalla de registro
     * (turno.registro.liquidacion.view)
     *
     * @param request
     *            La información del formulario que se quiere preservar
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
     * @param request
     *            La información del formulario que se quiere preservar
     * @return Un <CODE>EvnTurnoManualRegistro</CODE> nulo, ya que no se
     *         necesita generar un evento
     */
    private EvnTurnoManualRegistro preservarInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if ((request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD) != null) &&
                !request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD)
                            .equals("")) {
            session.setAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD,
                request.getParameter(CSubtipoAtencion.SUBTIPO_SOLICITUD));
        }

        if ((request.getParameter("SUBTIPO_SOLICITUD") != null) &&
                !request.getParameter("SUBTIPO_SOLICITUD").equals("")) {
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
         /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
        */
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION,
            request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
        
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
            request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));

        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
            request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
            request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));

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
            request.getParameter(
                CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS,
            request.getParameter(
                CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS,
            request.getParameter(
                CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS,
            request.getParameter(
                CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS,
            request.getParameter(
                CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS,
            request.getParameter(
                CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS,
            request.getParameter(
                CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));

        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS,
            request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS));
        /**
        * @author: Henry Gomez Rocha
        * MANTIS: 0003999
        **/
        session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS,
            request.getParameter(CSolicitudRegistro.COMENTARIO));

        //
        if (request.getParameterValues("DOCUMENTOS_ENTREGADOS") != null) {
            session.setAttribute("DOCUMENTOS_ENTREGADOS",
                request.getParameterValues("DOCUMENTOS_ENTREGADOS"));
        }

        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO);

        if ((numCopiasStr != null) && !numCopiasStr.trim().equals("")) {
            session.setAttribute(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO,
                numCopiasStr);
        }
        
        session.setAttribute(AWTurnoManualCertificado.FECHA_INICIO,
                request.getParameter(AWTurnoManualCertificado.FECHA_INICIO));
        
        session.setAttribute(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION,
        		request.getParameter(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION));

        return null;
    }

    /**
     * Desasociar una matrícula de la solicitud
     *
     * @param request
     *            La información del formulario
     * @return Un <CODE>EvnTurnoManualRegistro</CODE> nulo, ya que no se
     *         necesita generar un evento
     */
    private EvnTurnoManualRegistro eliminarMatricula(HttpServletRequest request) {
        List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

        if (matriculas == null) {
            matriculas = new ArrayList();
        }

        String[] matriculasElim = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);

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
     * @param request
     *            La información del formulario
     * @return Un <CODE>EvnTurnoManualRegistro</CODE> con el usuario y el
     *         String de la matrícula que se quiere asociar para que sea
     *         validada
     * @throws AccionWebException
     */
    private EvnTurnoManualRegistro agregarMatricula(HttpServletRequest request)
        throws AccionWebException {
        List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

        if (matriculas == null) {
            matriculas = new ArrayList();
        }

        Circulo circ= (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		String matricula = circ.getIdCirculo()+ "-";
		matricula += request.getParameter(AGREGAR_MATRICULA_REGISTRO);
        preservarInfo(request);

        if ((matricula == null) || matricula.trim().equals("")) {
            throw new ValidacionMatriculaRegistroException("Matrícula inválida");
        }

        if (matriculas.contains(matricula)) {
            throw new ValidacionMatriculaRegistroException(
                "La matrícula que quiere ingresar, ya fue ingresada");
        }

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                                                                                                                         .getAttribute(SMARTKeys.USUARIO_EN_SESION);

        return new EvnTurnoManualRegistro(usuario, matricula);
    }

    /**
     * Captura todos los datos de la solicitud y crea la liquidación para que
     * sea evaluada por hermod
     *
     * @param request
     *            La información del formulario
     * @return Un <CODE>EvnTurnoManualRegistro</CODE> con el usuario, <CODE>LiquidacionTurnoRegistro</CODE>
     * @throws AccionWebException
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     */
    private EvnTurnoManualRegistro liquidar(HttpServletRequest request)
        throws AccionWebException {
    	
        HttpSession session = request.getSession();
        ValidacionParametrosRegistroException excepcion = new ValidacionParametrosRegistroException();
        LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();
        SolicitudRegistro solicitud = (SolicitudRegistro) session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
        Integer cantidadActos = (Integer) session.getAttribute(CActo.NUM_ACTOS);
        int val = cantidadActos.intValue();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        //Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String sCirculo = (String) session.getAttribute(TURNO_CIRCULO);
        double valorDerechos = 0, valorImpuestos = 0, valorConservacion=0;
        
        Circulo circulo = null;
        //circulo.setIdCirculo(sCirculo);
        
        //List circulos = (List)session.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        List circulos = (List)session.getServletContext().getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        if(circulos != null) {
	        for(Iterator itCirculos = circulos.iterator(); itCirculos.hasNext();) {
	        	Circulo circuloTemp = (Circulo)itCirculos.next();
	        	if(circuloTemp.getIdCirculo().equals(sCirculo)) {
	        		circulo = circuloTemp;
	        		break;
	        	}
	        }
        }
        
        if(circulo == null) {
        	excepcion.addError("El círculo digitado es inválido");
        }

        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
        
        JDOLiquidacionesDAO jdoLiquidacionesDAO = new JDOLiquidacionesDAO();
        if (val == 0) {
            excepcion.addError("Debe agregar al menos un acto");
        } else {
            for (int i = 0; i < val; i++) {
                Integer actual = new Integer(i);
                String id = CActo.ACTO + actual.toString();
                Acto acto = (Acto) session.getAttribute(id);
                liquidacion.addActo(acto);
                valorDerechos += acto.getValor();
                try {
                    valorConservacion += (acto.getValor()*jdoLiquidacionesDAO.TraerConservacionActo(acto));
                } catch (DAOException ex) {
                    Logger.getLogger(AWTurnoManualRegistro.class.getName()).log(Level.SEVERE, null, ex);
                }
                valorImpuestos += acto.getValorImpuestos();
            }
        }

        String valorOtro = request.getParameter(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
        String valorMora = request.getParameter(CLiquidacionTurnoRegistro.VALOR_MORA);
        String descripcion = request.getParameter(CLiquidacionTurnoRegistro.DESCRIPCION);
        //String tipoTarifa = request.getParameter(CLiquidacionTurnoRegistro.TIPO_TARIFA);
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

        if ((valorOtro == null) || valorOtro.trim().equals("")) {
            if ((descripcion != null) && !descripcion.equals("")) {
                excepcion.addError("Debe ingresar un valor para otro impuesto");
            }
        }

        if ((descripcion == null) || descripcion.trim().equals("")) {
            if ((valorOtro != null) && !valorOtro.equals("")) {
                excepcion.addError("Debe ingresar una descripción para otro impuesto");
            }
        }

        if ((valorOtro != null) && !valorOtro.trim().equals("")) {
            if ((descripcion != null) || !descripcion.equals("")) {
                try {
                    float v = Float.parseFloat(valorOtro);
                    if(v < 0) {
                    	excepcion.addError("Valor de otro impuesto inválido");
                    }
                } catch (NumberFormatException e) {
                    excepcion.addError("Valor de otro impuesto inválido");
                }
            }
        }
        
        if ((valorMora != null) && !valorMora.trim().equals("")) {
        	try {
        		double v = Double.parseDouble(valorMora);
        		if(v < 0) {
        			excepcion.addError("Valor de mora inválido");
        		}
        	} catch (NumberFormatException e) {
        		excepcion.addError("Valor de mora inválido");
        	}
        }

        //List certificadosAsociados = (List) request.getSession().getAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

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
		ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

        /*
         * if (nombre != null) { ciudadano.setNombre(nombre); } if (apellido2 !=
         * null) { ciudadano.setApellido2(apellido2); }
         */
        solicitud.setCiudadano(ciudadano);
        System.out.println("Desarollo1:: Valor derechos AW:"+String.valueOf(valorDerechos));
        System.out.println("Desarollo1:: Valor impuestos AW:"+String.valueOf(valorImpuestos));
        System.out.println("Desarollo1:: Valor Conservacion AW:"+String.valueOf(valorConservacion));
        
        double valorTotal = valorDerechos + valorImpuestos + valorConservacion;
        
        if ((valorOtro != null) && !valorOtro.equals("")) {
            liquidacion.setOtroImpuesto(descripcion);
            liquidacion.setValorOtroImp(Float.parseFloat(valorOtro));
            valorTotal += liquidacion.getValorOtroImp();
        }
        
        if ((valorMora != null) && !valorMora.equals("")) {
        	liquidacion.setValorMora(Double.parseDouble(valorMora));
        	valorTotal += liquidacion.getValorMora();
        }

        liquidacion.setValor(valorTotal);
        liquidacion.setValorDerechos(valorDerechos);
        liquidacion.setValorImpuestos(valorImpuestos);
        liquidacion.setFecha(solicitud.getFecha());
        if(circulo != null){
        	liquidacion.setCirculo(circulo.getIdCirculo());
        }
        
        solicitud.setCirculo(circulo);
        solicitud.setUsuario(usuarioNeg);
        solicitud.setProceso(proceso);
        solicitud.addLiquidacion(liquidacion);

        // SE AGREGAN LOS CERTIFICADOS ASOCIADOS A LA SOLICITUD DE REGISTRO.
        /*if (certificadosAsociados != null) {
            Iterator it = certificadosAsociados.iterator();

            while (it.hasNext()) {
                SolicitudCertificado solCert = (SolicitudCertificado) it.next();
                solCert.setCiudadano(ciudadano);

                SolicitudAsociada solAsoc = new SolicitudAsociada();
                solAsoc.setSolicitudHija(solCert);

                solicitud.addSolicitudesHija(solAsoc);
            }
        }*/

        Turno turnoAnterior = (Turno) session.getAttribute(CTurno.TURNO_ANTERIOR_OBJETO);

        if (turnoAnterior != null) {
            solicitud.setTurnoAnterior(turnoAnterior);
        }

        // -----------------------------------------------------------------------------------------
        // adicionar el turno vinculado

        gov.sir.core.negocio.modelo.SolicitudVinculada solicitudVinculada
          = (gov.sir.core.negocio.modelo.SolicitudVinculada)session.getAttribute( CTurno.SOLICITUD_VINCULADA_OBJETO );
        if( null != solicitudVinculada ) {
            // TODO: db-insert
            solicitud.addSolicitudesVinculada( solicitudVinculada );
        }
        // -----------------------------------------------------------------------------------------


        liquidacion.setSolicitud(solicitud);
        session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, solicitud);
        
		String accion = request.getParameter(WebKeys.ACCION);        

        EvnTurnoManualRegistro evento = new EvnTurnoManualRegistro(usuario,
                liquidacion, (Proceso) session.getAttribute(WebKeys.PROCESO),
                (Estacion) session.getAttribute(WebKeys.ESTACION), true,
                usuarioNeg);

        evento.setUID(request.getSession().getId());
        
        String respuestaCalificacion = (String) session.getAttribute(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION);        
        if(accion!=null){
        	//NO SE DEBE IMPRIMIR CONSTANCIA SI SE VA A REGISTRAR EL PAGO
			if(accion.equals(AWTurnoManualRegistro.LIQUIDAR_CONTINUAR)){
				evento.setImprimirConstancia(false);        	
			}else{
				evento.setImprimirConstancia(true);				
			}
        }
        

        return evento;
    }


	/**
	 * Método que permite realizar la edición de una liquidación.
	 *
	 * @param request
	 *            La información del formulario
	 * @return Un <CODE>EvnTurnoManualRegistro</CODE> con el usuario, <CODE>LiquidacionTurnoRegistro</CODE>
	 * @throws AccionWebException
	 * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
	 */
	private EvnTurnoManualRegistro editarLiquidacion(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		ValidacionParametrosRegistroException excepcion = new ValidacionParametrosRegistroException();

		LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();
		SolicitudRegistro solicitud = (SolicitudRegistro) session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);

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
		//String tipoTarifa = request.getParameter(CLiquidacionTurnoRegistro.TIPO_TARIFA);
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
					/*float v = */Float.parseFloat(valorOtro);
				} catch (NumberFormatException e) {
					excepcion.addError("Impuesto inválido");
				}
			}
		}

		//List certificadosAsociados = (List) request.getSession().getAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

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
		ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);
		
		/*
		 * if (nombre != null) { ciudadano.setNombre(nombre); } if (apellido2 !=
		 * null) { ciudadano.setApellido2(apellido2); }
		 */
		solicitud.setCiudadano(ciudadano);

		if ((valorOtro != null) && !valorOtro.equals("")) {
			liquidacion.setOtroImpuesto(descripcion);
			liquidacion.setValorOtroImp(Float.parseFloat(valorOtro));
		}

		solicitud.setCirculo(circulo);
		solicitud.setUsuario(usuarioNeg);
		solicitud.setProceso(proceso);
		solicitud.addLiquidacion(liquidacion);

		// SE AGREGAN LOS CERTIFICADOS ASOCIADOS A LA SOLICITUD DE REGISTRO.
		/*if (certificadosAsociados != null) {
			Iterator it = certificadosAsociados.iterator();

			while (it.hasNext()) {
				SolicitudCertificado solCert = (SolicitudCertificado) it.next();
				solCert.setCiudadano(ciudadano);

				SolicitudAsociada solAsoc = new SolicitudAsociada();
				solAsoc.setSolicitudHija(solCert);

				solicitud.addSolicitudesHija(solAsoc);
			}
		}*/

		Turno turnoAnterior = (Turno) session.getAttribute(CTurno.TURNO_ANTERIOR_OBJETO);

		if (turnoAnterior != null) {
			solicitud.setTurnoAnterior(turnoAnterior);
		}

		liquidacion.setSolicitud(solicitud);
		session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, solicitud);

		EvnTurnoManualRegistro evento = new EvnTurnoManualRegistro(usuario,
				liquidacion, (Proceso) session.getAttribute(WebKeys.PROCESO),
				(Estacion) session.getAttribute(WebKeys.ESTACION), true,
				usuarioNeg, EvnTurnoManualRegistro.EDITAR_LIQUIDACION);

		evento.setUID(request.getSession().getId());

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
    /*private EvnTurnoManualRegistro agregarMatriculaAsociada(
        HttpServletRequest request) throws AccionWebException {
        List matriculas = (List) request.getSession().getAttribute(AWTurnoManualRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);

        if (matriculas == null) {
            matriculas = new ArrayList();
        }

        String matricula = request.getParameter(CFolio.ID_MATRICULA);

        if ((matricula == null) || matricula.trim().equals("")) {
            throw new ValidacionMatriculaAsociadaRegistroException(
                "Matrícula inválida");
        }

        if (matriculas.contains(matricula)) {
            request.getSession().setAttribute(CFolio.ID_MATRICULA, matricula);
            throw new ValidacionMatriculaAsociadaRegistroException(
                "La matrícula que quiere ingresar, ya fue ingresada");
        }

        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_MATRICULA);

        if ((numCopiasStr == null) || numCopiasStr.trim().equals("")) {
            request.getSession().setAttribute(CFolio.ID_MATRICULA, matricula);
            throw new ValidacionMatriculaAsociadaRegistroException(
                "Debe Proporcionar un número de copias para el certificado asociado");
        }

        //int numCopias = 0;

        try {
            numCopias = Integer.parseInt(numCopiasStr);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute(CFolio.ID_MATRICULA, matricula);
            throw new ValidacionMatriculaAsociadaRegistroException(
                " no es un valor numérico válido");
        }

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                                                                                                                         .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        EvnTurnoManualRegistro evento = new EvnTurnoManualRegistro(usuario,
                matricula);
        evento.setTipoEvento(EvnTurnoManualRegistro.VALIDAR_MATRICULA_ASOCIADA);

        return evento;
    }*/

    /**
     * Elimina una matricula de la lista de certificados asociados que se han
     * añadido
     *
     * @param request
     * @return evento respuesta
     */
    /*private EvnTurnoManualRegistro eliminarMatriculaCertificadoAsociado(
        HttpServletRequest request) {
        List matriculas = (List) request.getSession().getAttribute(AWTurnoManualRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);
        List certificadosAsociados = (List) request.getSession().getAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

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

            // SE ELIMINAN LAS SOLICITUDES DE CERTIFICADOS QUE ESTAN ASOCIADOS
            // CON LAS MATRICULAS A ELIMINAR
            for (int i = 0; i < matriculasElim.length; i++) {
                String actual = matriculasElim[i];
                matriculas.remove(actual);
            }
        }

        return null;
    }*/

    /**
     * Agrega certificados asociados a partir de los datos de antiguo sistema
     * del turno de registro.
     *
     * @param request
     * @return evento respuesta
     * @throws AccionWebException
     */
    /*private EvnTurnoManualRegistro agregarCertificadosAntiguoSistema(
        HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        List certificadosAsociados = (List) session.getAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (certificadosAsociados == null) {
            certificadosAsociados = new ArrayList();
        }

        ValidacionCertificadoAsociadoException excepcion = new ValidacionCertificadoAsociadoException();

        // SE VALIDA QUE HAYAN DATOS DE ANTIGUO SISTEMA
        Solicitud solicitud = (Solicitud) session.getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);

        if ((solicitud == null) ||
                (solicitud.getDatosAntiguoSistema() == null)) {
            excepcion.addError(
                "No se puede crear un certificado asociado porque el turno de registro no tiene información de antiguo sistema.");
            throw excepcion;
        }

        // SE RECUPERA LA INFORMACIÓN DE NÚMERO DE COPIAS Y DE UNIDADES DE
        // CERTIFICADOS.
        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_ANT_SIS);
        String numUnidadesStr = request.getParameter(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_ANT_SIS);
        String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS +
                CSolicitudAsociada.OPCION_ANT_SIS);

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

        session.setAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
            certificadosAsociados);

        return null;
    }*/

    /**
     * Elimina certificados asociados a partir de los datos de antiguo sistema
     * del turno de registro.
     *
     * @param request
     * @return evento respuesta
     */
    /*private EvnTurnoManualRegistro eliminarCertificadosAntiguoSistema(
        HttpServletRequest request) {
        HttpSession session = request.getSession();
        List certificadosAsociados = (List) session.getAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

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

        session.setAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
            certificadosAsociados);

        return null;
    }*/

    /**
     * Agrega certificados asociados a partir de los datos de antiguo sistema
     * del turno de registro.
     *
     * @param request
     * @return evento respuesta
     * @throws AccionWebException
     */
    /*private EvnTurnoManualRegistro agregarCertificadosSegregacion(
        HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        List certificadosAsociados = (List) session.getAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (certificadosAsociados == null) {
            certificadosAsociados = new ArrayList();
        }

        ValidacionCertificadoAsociadoException excepcion = new ValidacionCertificadoAsociadoException();

        // SE RECUPERA LA INFORMACIÓN DE NÚMERO DE COPIAS Y DE UNIDADES DE
        // CERTIFICADOS.
        String numCopiasStr = request.getParameter(CSolicitudAsociada.NUMERO_COPIAS_CERTIFICADO_ASOCIADO_SEGREGACION);
        String numUnidadesStr = request.getParameter(CSolicitudAsociada.NUMERO_UNIDADES_CERTIFICADO_ASOCIADO_SEGREGACION);
        String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS +
                CSolicitudAsociada.OPCION_SEGREGACION);

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

        session.setAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
            certificadosAsociados);

        return null;
    }*/

    /**
     * Elimina certificados asociados a partir de los datos de antiguo sistema
     * del turno de registro.
     *
     * @param request
     * @return evento respuesta
     */
    /*private EvnTurnoManualRegistro eliminarCertificadosSegregacion(
        HttpServletRequest request) {
        HttpSession session = request.getSession();
        List certificadosAsociados = (List) session.getAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

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

        session.setAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
            certificadosAsociados);

        return null;
    }*/

    /**
     * @param matricula
     * @param request
     * @return evento respuesta
     */
    /*private EvnTurnoManualRegistro adicionarCertificadoAsociado(
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
        String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS +
                CSolicitudAsociada.OPCION_MATRICULA);

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

        List certificadosAsociados = (List) session.getAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS);

        if (certificadosAsociados == null) {
            certificadosAsociados = new ArrayList();
        }

        certificadosAsociados.add(solCert);
        session.setAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS,
            certificadosAsociados);


        return null;
    }*/

	/**
	 * Método que se encarga de subir a la sesión los certificados asociados que tiene
	 * una solicitud. Es usado cuando se desea editar una liquidación existente y que aún no
	 * ha sido pagada.
	 * @param List certificados
	 * @param request
	 * @return evento respuesta
	 */
	/*private void cargarCertificadoAsociados(HttpSession session, List certificados) {

		List matriculasAsociadas = new ArrayList();
		List certificadosAsociados = new ArrayList();

		List solicitudFolios = null;
		Iterator itSolFolios = null;

		Iterator itCertificados = certificados.iterator();
		while(itCertificados.hasNext()){
			SolicitudAsociada solAsociada = (SolicitudAsociada)itCertificados.next();

			SolicitudCertificado solCertificado = (SolicitudCertificado)solAsociada.getSolicitudHija();
			certificadosAsociados.add(solCertificado);

			if(solCertificado.getSolicitudFolios().size()>0){
				solicitudFolios = solCertificado.getSolicitudFolios();
				itSolFolios = solicitudFolios.iterator();

				while (itSolFolios.hasNext()){
					SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();
					matriculasAsociadas.add(solFolio.getIdMatricula());
				}
			}
		}

		session.setAttribute(AWTurnoManualRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO, matriculasAsociadas);
		session.setAttribute(AWTurnoManualRegistro.LISTA_CERTIFICADOS_ASOCIADOS, certificadosAsociados);
	}*/

    // FIN DE CERTIFICADOS ASOCIADOS.

    /**
     * @param request
     * @return evento respuesta
     * @throws ValidacionParametrosException
     */
    private EvnTurnoManualRegistro buscarSolicitud(HttpServletRequest request)
        throws ValidacionParametrosException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                                                                                                                         .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        request.getSession().removeAttribute(AWTurnoManualRegistro.SOLICITUD);

        // obtener parametros
        String idSolicitud = request.getParameter(AWTurnoManualRegistro.ID_SOLICITUD);
        request.getSession().setAttribute(AWTurnoManualRegistro.ID_SOLICITUD,
            idSolicitud);

        // validar parametros
        if ((idSolicitud == null) || idSolicitud.equals("")) {
            exception.addError("Numero de solicitud vacio.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // creacion del evento
        EvnTurnoManualRegistro evento = new EvnTurnoManualRegistro(usuario);
        Usuario usuarioSIR = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
        
        evento.setTipoEvento(EvnTurnoManualRegistro.BUSCAR_SOLICITUD);
        evento.setIdSolicitud(idSolicitud);
		evento.setUsuarioNec(usuarioSIR);
		evento.setUsuarioSIR(usuarioSIR);

        return evento;
    }

	/**
	 * Método que permite consultar una solicitud. junto con sus liquidaciones, con el objeto de
	 * poder editarlas.
	 * @param request
	 * @return evento respuesta
	 * @throws ValidacionParametrosException
	 */
	private EvnTurnoManualRegistro buscarSolicitudEdicion(HttpServletRequest request)
		throws ValidacionParametrosException {
		ValidacionParametrosException exception = new ValidacionParametrosException();
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
																														 .getAttribute(SMARTKeys.USUARIO_EN_SESION);
		request.getSession().removeAttribute(AWTurnoManualRegistro.SOLICITUD);

		// obtener parametros
		String idSolicitud = request.getParameter(AWTurnoManualRegistro.ID_SOLICITUD);
		request.getSession().setAttribute(AWTurnoManualRegistro.ID_SOLICITUD,
			idSolicitud);

		// validar parametros
		if ((idSolicitud == null) || idSolicitud.equals("")) {
			exception.addError("Número de solicitud vacio.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		// creacion del evento
		EvnTurnoManualRegistro evento = new EvnTurnoManualRegistro(usuario);
		evento.setTipoEvento(EvnTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION);
		evento.setIdSolicitud(idSolicitud);

		return evento;
	}

    /**
     * @param request
     * @return evento respuesta
     * @throws ValidacionParametrosException
     */
    private EvnTurnoManualRegistro validarSolicitud(HttpServletRequest request)
        throws ValidacionParametrosException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
                                                                                                                         .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);

        // obtener parametros
        String idSolicitud = request.getParameter(AWTurnoManualRegistro.ID_SOLICITUD);
        request.getSession().setAttribute(AWTurnoManualRegistro.ID_SOLICITUD,
            idSolicitud);

        // validar parametros
        if ((idSolicitud == null) || idSolicitud.equals("")) {
            exception.addError("Numero de solicitud vacio.");
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // creacion del evento
        EvnTurnoManualRegistro evento = new EvnTurnoManualRegistro(usuario);
        evento.setTipoEvento(EvnTurnoManualRegistro.VALIDAR_SOLICITUD);
        evento.setIdSolicitud(idSolicitud);
        evento.setEstacion(estacion);

        return evento;
    }

    /**
     * Permite incrementar el secuencial de recibos.
     *
     * @param request
     * @return EvnTurnoManualRegistro
     * @throws AccionWebException
     */
    private EvnTurnoManualRegistro incrementarSecuencialRecibo(
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
        //Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        //String idCirculo = circulo.getIdCirculo();
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        //Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
        //Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

        EvnTurnoManualRegistro eventoReg = new EvnTurnoManualRegistro(usuarioAuriga,
                EvnTurnoManualRegistro.INCREMENTAR_SECUENCIAL_RECIBO, null,
                fase, estacion, EvnCertificado.INCREMENTAR_SECUENCIAL_RECIBO,
                usuario);
        eventoReg.setMotivo(motivo);

        return eventoReg;
    }

    /**
     * Permite incrementar el secuencial de recibos.
     *
     * @param request
     * @return EvnTurnoManualRegistro
     * @throws AccionWebException
     * @throws CrearTurnoException
     */
    private EvnTurnoManualRegistro crearTurnoRegistro(
        HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        SolicitudRegistro sol = (SolicitudRegistro) session.getAttribute(AWTurnoManualRegistro.SOLICITUD);
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

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
                    throw new CrearTurnoException(check.getNombre() +
                        " es obligatorio para la solicitud.");
                }
            }
        }

        // Obtener fecha impresion
        String fechaImpresion = "";
        Date fecha = new Date();
        fechaImpresion = this.darFormato(fecha);

        if (fechaImpresion == null) {
            throw new AccionWebException("Fecha invalida");
        }

        String UID = request.getSession().getId();

        EvnTurnoManualRegistro eventoReg = new EvnTurnoManualRegistro(usuarioAuriga);
        eventoReg.setTipoEvento(EvnTurnoManualRegistro.CREAR_TURNO);
        eventoReg.setSolicitudRegistro(sol);
        eventoReg.setUsuarioSIR(usuario);
        eventoReg.setFechaImpresion(fechaImpresion);
        eventoReg.setCirculo(circulo);
        eventoReg.setUID(UID);

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

                    if (solicitud != null) {
                        session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO,
                            solicitud);
                    }
                    
					if(evento.getEsCajero()!=null){
						session.setAttribute(WebKeys.ES_CAJERO, evento.getEsCajero());
					} 
					
                } else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.LIQUIDACION)) {
                    Liquidacion liquidacion = evento.getLiquidacion();
                    session.setAttribute(WebKeys.LIQUIDACION, liquidacion);

                    if(liquidacion!=null && liquidacion.getSolicitud()!=null){
						session.setAttribute(WebKeys.SOLICITUD, liquidacion.getSolicitud());
                    }

					if(evento.getEsCajero()!=null){
						session.setAttribute(WebKeys.ES_CAJERO, evento.getEsCajero());
					}
					
					if(evento.getEsCajeroRegistro()!=null){
						session.setAttribute(WebKeys.ES_CAJERO_REGISTRO, evento.getEsCajeroRegistro());
					}

                    //NumberFormat nf = NumberFormat.getInstance();
                    session.setAttribute(WebKeys.VALOR_LIQUIDACION,
                        String.valueOf(liquidacion.getValor()));

                    DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
                    AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo,
                            liquidacion.getValor());
                    session.setAttribute(WebKeys.APLICACION_EFECTIVO,
                        aplicacionEfectivo);
                    session.setAttribute(CActo.NUM_ACTOS, new Integer(0));

                    session.removeAttribute(CTurno.TURNO_ANTERIOR);
                    session.removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);
                    session.removeAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);

                    session.removeAttribute(CSolicitudRegistro.COMENTARIO);
                    session.removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
                    session.removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
                    session.removeAttribute(CSolicitudRegistro.CALENDAR);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
                    /*
                    *  @author Carlos Torres
                    *  @chage   se agrega validacion de version diferente
                    *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                    */
                    session.removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
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
                    /*
                    *  @author Carlos Torres
                    *  @chage   se agrega validacion de version diferente
                    *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                    */
                    session.removeAttribute("version");
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
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
                    session.removeAttribute(CDatosAntiguoSistema.COMENTARIO_AS);
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
                    /*
                    *  @author Carlos Torres
                    *  @chage   se agrega validacion de version diferente
                    *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                    */
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION);
                    session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
                    
                    session.removeAttribute("VER_ANTIGUO_SISTEMA");

                    session.removeAttribute(CActo.TIPO_ACTO);
                    session.removeAttribute(CActo.VALOR_ACTO);
                    session.removeAttribute(CActo.COBRA_IMPUESTO);
                    session.removeAttribute(CActo.IMPUESTO);
                    session.removeAttribute(CActo.VALOR_OTRO_IMPUESTO);
                    session.removeAttribute(CActo.DESCRIPCION);
                    session.removeAttribute(CLiquidacionTurnoRegistro.VALOR_MORA);
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
                } /*else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.VALIDACION_MATRICULA_ASOCIADA)) {
                    String matricula = (String) evento.getPayload();

                    if (matricula != null) {
                        List matriculasAsociadas = (List) request.getSession()
                                                                 .getAttribute(AWTurnoManualRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO);

                        if (matriculasAsociadas == null) {
                            matriculasAsociadas = new ArrayList();
                        }

                        matriculasAsociadas.add(matricula);
                        request.getSession().setAttribute(AWTurnoManualRegistro.LISTA_MATRICULAS_CERTIFICADO_ASOCIADO,
                            matriculasAsociadas);

                        this.adicionarCertificadoAsociado(matricula, request);
                    }
                }*/ else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.TURNO_ANTERIOR_VALIDADO)) {
                    doEndValidarTurnoAnterior(session, evento);
                }

            } else if (respuesta instanceof EvnRespTurnoManualRegistro) {
                // casted event
                EvnRespTurnoManualRegistro evento = (EvnRespTurnoManualRegistro) respuesta;

                if( EvnRespTurnoManualRegistro.REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK.equalsIgnoreCase( evento.getTipoEvento() ) ){
                  // escribir valores a la sesion de turno vinculado
                  doEndRegistro_VincularTurno_AddItem( session, evento );
                }
                else {
                    doEndProcesarEventoLiquidacionRegistro( request, respuesta, session );
                }
            }
            else if (respuesta instanceof EvnRespPago) {
                EvnRespPago evento = (EvnRespPago) respuesta;

                if (evento != null) {
                    if (evento.getTipoEvento().equals(EvnRespPago.PROCESAMIENTO_PAGO)) {
                        Turno turno = evento.getTurno();
                        session.setAttribute(WebKeys.TURNO, turno);
                    }
                }
            }

        }
    }

    /**
     * doEndRegistro_VincularTurno_AddItem
     *
     * @param session HttpSession
     * @param evento EvnRespLiquidacion
     * @see doEndValidarTurnoAnterior
     */
    private void
    doEndRegistro_VincularTurno_AddItem( HttpSession session, EvnRespTurnoManualRegistro evento ) {
      gov.sir.core.negocio.modelo.SolicitudVinculada param_SolicitudVinculada
        = evento.getSolicitudVinculada();

      if( null != param_SolicitudVinculada ) {
        session.setAttribute( CTurno.SOLICITUD_VINCULADA       , param_SolicitudVinculada.getIdSolicitud() );
        session.setAttribute( CTurno.SOLICITUD_VINCULADA_OBJETO, param_SolicitudVinculada );
      }


    }

    /**
     * Realizar el doEnd para cargar los datos del turno anterior
     *
     * @param session
     * @param evento
     */
    private void doEndValidarTurnoAnterior(HttpSession session,
        EvnRespLiquidacion evento) {
        Turno turno = (Turno) evento.getPayload();

        if (turno != null) {
            if (turno.getSolicitud() instanceof SolicitudRegistro) {
                SolicitudRegistro solicitudReg = (SolicitudRegistro) turno.getSolicitud();

                SubtipoAtencion subAtencion = solicitudReg.getSubtipoAtencion();
                SubtipoSolicitud subSol = solicitudReg.getSubtipoSolicitud();
                //List subtiposObj = (List) session.getServletContext()
                //                                 .getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD);
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

                long idLiq = solicitudReg.getLastIdLiquidacion() - 1;
                Liquidacion liq = (Liquidacion) solicitudReg.getLiquidaciones()
                                                            .get((int) idLiq);

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
                }

                Ciudadano ciu = solicitudReg.getCiudadano();

                if (ciu != null) {
                    session.setAttribute(CCiudadano.TIPODOC, ciu.getTipoDoc());
                    session.setAttribute(CCiudadano.DOCUMENTO,
                        ciu.getDocumento());
                    session.setAttribute(CCiudadano.APELLIDO1,
                        ciu.getApellido1());
                    session.setAttribute(CCiudadano.APELLIDO2,
                        ciu.getApellido2());
                    session.setAttribute(CCiudadano.NOMBRE, ciu.getNombre());
                    session.setAttribute(CCiudadano.TELEFONO, ciu.getTelefono());
                }

                session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO,
                    folios);
                session.setAttribute(CSolicitudCheckedItem.DOCUMENTOS_ENTREGADOS,
                    docs);

                Documento documento = solicitudReg.getDocumento();
                String tipoDoc = documento.getTipoDocumento()
                                          .getIdTipoDocumento();
                String numDoc = documento.getNumero();
                Date fechaDoc = documento.getFecha();

                String tipoOficinaNI = "";
                String oficinaInternacional = "";

                oficinaInternacional = documento.getOficinaInternacional();
                tipoOficinaNI = (oficinaInternacional == null) ? ""
                                                               : oficinaInternacional;

                if (tipoOficinaNI.length() == 0) {
                    session.setAttribute(WebKeys.TIPO_OFICINA_I_N,
                        WebKeys.TIPO_OFICINA_NACIONAL);
                } else {
                    session.setAttribute(WebKeys.TIPO_OFICINA_I_N,
                        WebKeys.TIPO_OFICINA_INTERNACIONAL);
                    session.setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL,
                        oficinaInternacional);
                    session.setAttribute(WebKeys.TEXT_OFICINA_INTERNACIONAL,
                        oficinaInternacional);
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
                    idTipo = tipo.getIdTipoOficina();
                    /*
                    *  @author Carlos Torres
                    *  @chage   se agrega validacion de version diferente
                    *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                    */
                    version = oficinaOrigen.getVersion().toString();
                }

                DatosAntiguoSistema das = solicitudReg.getDatosAntiguoSistema();

                //Cargar Datos de antiguo sistema
                Log.getInstance().debug(AWTurnoManualRegistro.class, "\n[DAS] " + das + "\n");
                if (das != null) {
                    cargarDatosAntioguoSistemaASesion(session, das);
                } //End if das!=null

                session.setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO, tipoDoc);
                session.setAttribute(CSolicitudRegistro.ID_ENCABEZADO, numDoc);
                session.setAttribute(CSolicitudRegistro.CALENDAR,
                    darFormato(fechaDoc));
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM,
                    numOficina);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO,
                    nomOficina);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO,
                    idTipo);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
                    idOficina);
                 /*
                    *  @author Carlos Torres
                    *  @chage   se agrega validacion de version diferente
                    *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                    */
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION,
                    version); 
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,
                    valorDepartamento);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,
                    nomDepto);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,
                    valorMunicipio);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,
                    nomMunicpio);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,
                    valorVereda);
                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,
                    nomVereda);
                session.setAttribute(CSolicitudRegistro.SUBTIPO_ATENCION,
                    subAtencion);
                session.setAttribute(CSolicitudRegistro.SUBTIPO_SOLICITUD,
                    subSol.getIdSubtipoSol());

                session.setAttribute(CTurno.TURNO_ANTERIOR,
                    turno.getIdWorkflow());

                session.setAttribute(CTurno.TURNO_ANTERIOR_OBJETO, turno);
            }
        }
    }

    /**
     * Cargar los datos del objeto Antiguo sistema en memoria
     * @param session  Session para cargar los datos
     * @param das  Objeto @see <DatosAntiguoSistema> para obtener los datos
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
                    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS,
                        oficina.getNumero());
                } //End if vereda!=null
            } //End if oficina!=null

            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS,
                documento.getNumero());

            session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS,
                this.darFormato(das.getFechaCreacion()));
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
        EvnRespTurnoManualRegistro evento = (EvnRespTurnoManualRegistro) respuesta;

        if (evento.getTipoEvento().equals(EvnRespTurnoManualRegistro.AGREGAR_ACTO)) {
            Acto acto = (Acto) evento.getPayload();

            if (acto != null) {
                session.removeAttribute(CActo.TIPO_ACTO);
                session.removeAttribute(CActo.TIPO_DERECHO);
                session.removeAttribute(CActo.VALOR_ACTO);
                session.removeAttribute(CActo.COBRA_IMPUESTO);
                session.removeAttribute(CActo.IMPUESTO);
                session.removeAttribute(CActo.TIPO_TARIFA);
                session.removeAttribute(CActo.TIPO_CALCULO);
                session.removeAttribute(CActo.VALOR_IMPUESTO);
                session.removeAttribute("LISTA_TIPOS_DERECHOS_REGISTRALES");

                Integer num = (Integer) session.getAttribute(CActo.NUM_ACTOS);
                session.setAttribute(CActo.ACTO + num.toString(), acto);

                int v = num.intValue();
                v++;

                Integer nuevo = new Integer(v);

                session.setAttribute(CActo.NUM_ACTOS, nuevo);
            }
        } else if (evento.getTipoEvento().equals(EvnRespTurnoManualRegistro.VALIDAR_SOLICITUD)) {
            request.getSession().setAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION,
                "" + evento.getValorSecuencial());
            request.getSession().setAttribute(WebKeys.SECUENCIAL_RECIBO_CONSULTADO,
                "" + evento.getValorSecuencial());
            session.setAttribute(WebKeys.SOLICITUD, evento.getPayload());
        } else if (evento.getTipoEvento().equals(EvnRespTurnoManualRegistro.BUSCAR_SOLICITUD)) {
            this.doEndBuscarSolicitud(session, evento);
        }else if (evento.getTipoEvento().equals(EvnRespTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION)) {
			this.doEndBuscarSolicitudEdicion(session, evento);
		} else if (evento.getTipoEvento().equals(EvnRespTurnoManualRegistro.INCREMENTAR_SECUENCIAL_RECIBO)) {
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
        } else if (evento.getTipoEvento().equals(EvnRespTurnoManualRegistro.CREAR_TURNO)) {
            // subir el turno a la sesion.
            request.getSession().setAttribute(WebKeys.TURNO, evento.getPayload());
        }
    }

    /**
     * @param session
     * @param evento
     */
    private void doEndBuscarSolicitud(HttpSession session, EvnRespTurnoManualRegistro evento) {
        Solicitud sol = (Solicitud) evento.getPayload();
        double valLiquidacion = 0;
        if (sol != null) {
            List liquidaciones = sol.getLiquidaciones();

            if ((liquidaciones != null) && (liquidaciones.size() > 0)) {
                Liquidacion liq = (Liquidacion) liquidaciones.get(0);
                valLiquidacion = liq.getValor();
                session.setAttribute(WebKeys.LIQUIDACION, liq);
            }

            session.setAttribute(WebKeys.SOLICITUD, evento.getPayload());
            session.setAttribute(WebKeys.VALOR_LIQUIDACION, valLiquidacion + "");
                
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
	private void doEndBuscarSolicitudEdicion(HttpSession session, EvnRespTurnoManualRegistro evento) {

		SolicitudRegistro solRegistro = (SolicitudRegistro)evento.getPayload();
		session.setAttribute(WebKeys.SOLICITUD, solRegistro);
		session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, solRegistro);

		if (solRegistro != null) {
			List liquidaciones = solRegistro.getLiquidaciones();

			LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro)liquidaciones.get(liquidaciones.size()-1);

			List actos = liquidacion.getActos();
			Iterator itActos = actos.iterator();
			while(itActos.hasNext()){
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

			session.setAttribute(CActo.VALOR_OTRO_IMPUESTO,"" + liquidacion.getValorOtroImp());
			session.setAttribute(CActo.DESCRIPCION, liquidacion.getOtroImpuesto());

			//SE CARGAN LOS CERTIFICADOS ASOCIADOS QUE SE HABIAN INGREZADO INICIALMENTE.
			//cargarCertificadoAsociados(session, solRegistro.getSolicitudesHijas());
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

    /**
     * Obtener una cadena de fecha con formato apartir de un Date
     *
     * @param fechaDoc
     *            fecha a la que se aplica el formato
     * @return Fecha con formato d/M/yyyy
     */
    private String darFormato(Date fechaDoc) {
        Calendar c = Calendar.getInstance();

        if (fechaDoc != null) {
            c.setTime(fechaDoc);

            return DateFormatUtil.format(fechaDoc);
        }

        return null;
    }

    /**
     * Convertir una cadena fecha en un Calendar de la fecha
     *
     * @param fechaInterfaz
     *            fecha que se va a convertir a Date en formato dd/MM/yyyy;
     * @return Calendar con la fecha
     */
    private Calendar darFecha(String fechaInterfaz) {

		try {
			DateFormatUtil.parse(fechaInterfaz);
			if(fechaInterfaz.indexOf("-")!=-1){
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

            if ((calendar.get(Calendar.YEAR) == año) &&
                    (calendar.get(Calendar.MONTH) == mes) &&
                    (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
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
     * @param tiposDoc
     *            Lista con los datos
     * @param id_tipo_encabezado
     *            cadena que se busca que exista en la lista
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
}
