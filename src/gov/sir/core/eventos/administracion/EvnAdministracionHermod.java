/*
 * Created on 8/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.eventos.administracion;

import java.io.File;
import java.util.Date;
import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
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
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.RolPantalla;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoCalculo;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoCertificadoValidacion;
import gov.sir.core.negocio.modelo.TipoDerechoReg;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.TipoTarifa;
import gov.sir.core.negocio.modelo.Validacion;
import gov.sir.core.negocio.modelo.ValidacionNota;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion;
import gov.sir.core.negocio.modelo.ZonaNotarial;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con la administración de objetos de Hermod.
 * @author jmendez
 * @author dsalas
 */
public class EvnAdministracionHermod extends EvnSIR {

	/**Esta constante se utiliza  para identificar el evento de creación de un banco*/
    public static final String ADICIONA_BANCO = "ADICIONA_BANCO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un banco*/
    public static final String ELIMINA_BANCO = "ELIMINA_BANCO";

	/**Esta constante se utiliza  para identificar el evento de consulta de las sucursales de un banco */
    public static final String LISTAR_SUCURSALES = "LISTAR_SUCURSALES";

	/**Esta constante se utiliza  para identificar el evento de creación de una Sucursal*/
    public static final String ADICIONA_SUCURSAL_BANCO = "ADICIONA_SUCURSAL_BANCO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de una Sucursal*/
    public static final String ELIMINA_SUCURSAL_BANCO = "ELIMINA_SUCURSAL_BANCO";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de alcance geográfico*/
    public static final String ADICIONA_ALCANCE_GEOGRAFICO = "ADICIONA_ALCANCE_GEOGRAFICO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de alcance geográfico*/
    public static final String ELIMINA_ALCANCE_GEOGRAFICO = "ELIMINA_ALCANCE_GEOGRAFICO";

	/**Esta constante se utiliza  para identificar el evento de creación de una tarifa */
    public static final String ADICIONA_TARIFA = "ADICIONA_TARIFA";

	/**Esta constante se utiliza  para identificar el evento de eliminación de una tarifa */
    public static final String ELIMINA_TARIFA = "ELIMINA_TARIFA";

	/**Esta constante se utiliza  para identificar el evento de creación de un tipo de tarifa  */
    public static final String ADICIONA_TIPO_TARIFA = "ADICIONA_TIPO_TARIFA";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de tarifa  */
    public static final String ELIMINA_TIPO_TARIFA = "ELIMINA_TIPO_TARIFA";

	/**Esta constante se utiliza  para identificar el evento de selección de un tipo de tarifa  */
    public static final String SELECCIONA_TIPO_TARIFA = "SELECCIONA_TIPO_TARIFA";

	/**Esta constante se utiliza  para identificar el evento de creación de un tipo de fotocopia  */
    public static final String ADICIONA_TIPO_FOTOCOPIA = "ADICIONA_TIPO_FOTOCOPIA";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de fotocopia */
    public static final String ELIMINA_TIPO_FOTOCOPIA = "ELIMINA_TIPO_FOTOCOPIA";

	/**Esta constante se utiliza  para identificar el evento de creación de un subtipo de solicitud  */
    public static final String ADICIONA_SUBTIPO_SOLICITUD = "ADICIONA_SUBTIPO_SOLICITUD";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un subtipo de solicitud  */
    public static final String ELIMINA_SUBTIPO_SOLICITUD = "ELIMINA_SUBTIPO_SOLICITUD";

	/**Esta constante se utiliza  para identificar el evento de creación de un subtipo de solicitud  */
    public static final String EDITA_SUBTIPO_SOLICITUD = "EDITA_SUBTIPO_SOLICITUD";

	/**Esta constante se utiliza  para identificar el evento de consulta los oplookup type */
    public static final String CONSULTA_OPLOOKUP_TYPE = "CONSULTA_OPLOOKUP_TYPE";

	/**Esta constante se utiliza  para identificar el evento de creación de un objeto oplookup type */
    public static final String ADICIONA_OPLOOKUP_TYPE = "ADICIONA_OPLOOKUP_TYPE";

	/**Esta constante se utiliza  para identificar el evento de creación de un objeto oplookup type */
    public static final String UPDATE_OPLOOKUP_TYPE = "UPDATE_OPLOOKUP_TYPE";

	/**Esta constante se utiliza  para identificar el evento de elminación de un objeto oplookup type */
    public static final String ELIMINA_OPLOOKUP_TYPE = "ELIMINA_OPLOOKUP_TYPE";

	/**Esta constante se utiliza  para identificar el evento de Consulta de objetos oplookup code  */
    public static final String LISTAR_OPLOOKUP_CODES = "LISTAR_OPLOOKUP_CODES";

	/**Esta constante se utiliza  para identificar el evento de creación de un objeto oplookup code  */
    public static final String ADICIONA_OPLOOKUP_CODE = "ADICIONA_OPLOOKUP_CODE";

	/**Esta constante se utiliza  para identificar el evento de edicion de un objeto oplookup code  */
    public static final String UPDATE_OPLOOKUP_CODE = "UPDATE_OPLOOKUP_CODE";

	/**Esta constante se utiliza  para identificar el evento de elminación de un objeto oplookup code  */
    public static final String ELIMINA_OPLOOKUP_CODE = "ELIMINA_OPLOOKUP_CODE";

	/**Esta constante se utiliza  para identificar el evento de creación de un tipo de cálculo */
    public static final String ADICIONA_TIPO_CALCULO = "ADICIONA_TIPO_CALCULO";

	/**Esta constante se utiliza  para identificar el evento de elminación de un tipo de cálculo */
    public static final String ELIMINA_TIPO_CALCULO = "ELIMINA_TIPO_CALCULO";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de impuesto */
    public static final String ADICIONA_TIPO_IMPUESTO = "ADICIONA_TIPO_IMPUESTO";

	/**Esta constante se utiliza  para identificar el evento de elminación de un tipo de impuesto */
    public static final String ELIMINA_TIPO_IMPUESTO = "ELIMINA_TIPO_IMPUESTO";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de atención */
    public static final String CARGAR_SUBTIPO_ATENCION = "CARGAR_SUBTIPO_ATENCION";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de atención */
    public static final String ADICIONA_SUBTIPO_ATENCION = "ADICIONA_SUBTIPO_ATENCION";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de atención */
    public static final String ELIMINA_SUBTIPO_ATENCION = "ELIMINA_SUBTIPO_ATENCION";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de atención */
    public static final String EDITAR_SUBTIPO_ATENCION = "EDITAR_SUBTIPO_ATENCION";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de acto */
    public static final String ADICIONA_TIPO_ACTO = "ADICIONA_TIPO_ACTO";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de acto */
    public static final String EDITA_TIPO_ACTO = "EDITA_TIPO_ACTO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de acto */
    public static final String ELIMINA_TIPO_ACTO = "ELIMINA_TIPO_ACTO";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de derecho registral */
    public static final String ADICIONA_TIPO_DERECHO_REGISTRAL = "ADICIONA_TIPO_DERECHO_REGISTRAL";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de derecho registral */
    public static final String ELIMINA_TIPO_DERECHO_REGISTRAL = "ELIMINA_TIPO_DERECHO_REGISTRAL";

	/**Esta constante se utiliza  para identificar el evento de adición de una accion notarial  */
    public static final String ADICIONA_ACCION_NOTARIAL = "ADICIONA_ACCION_NOTARIAL";

	/**Esta constante se utiliza  para identificar el evento de edición de una accion notarial  */
    public static final String EDITA_ACCION_NOTARIAL = "EDITA_ACCION_NOTARIAL";

	/**Esta constante se utiliza  para identificar el evento de eliminación de una accion notarial  */
    public static final String ELIMINA_ACCION_NOTARIAL = "ELIMINA_ACCION_NOTARIAL";

	/**Esta constante se utiliza  para identificar el evento de adición de una categoria  */
    public static final String ADICIONA_CATEGORIA = "ADICIONA_CATEGORIA";

	/**Esta constante se utiliza  para identificar el evento de edicion de una categoria  */
    public static final String EDITA_CATEGORIA = "EDITA_CATEGORIA";

	/**Esta constante se utiliza  para identificar el evento de elminación de una categoria  */
    public static final String ELIMINA_CATEGORIA = "ELIMINA_CATEGORIA";

	/**Esta constante se utiliza  para identificar el evento de mostrar los circulos de un administrador */
    public static final String MOSTRAR_CIRCULO_ESTACION_RECIBO = "MOSTRAR_CIRCULO_ESTACION_RECIBO";

	/**Esta constante se utiliza  para identificar el evento de seteo de una estación / recibo  */
    public static final String SET_ESTACION_RECIBO = "SET_ESTACION_RECIBO";

	/** Constante que identifica las acción de adicionar una estación recibo  al sistema */
    public static final String SET_AGREGAR_ESTACION_RECIBO = "SET_AGREGAR_ESTACION_RECIBO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de una estación / recibo  */
    public static final String ELIMINA_ESTACION_RECIBO = "ELIMINA_ESTACION_RECIBO";

	/**Esta constante se utiliza  para identificar el evento para hacer reset de los números de secuencia de una estación recibo   */
    public static final String TRASPASO_ESTACION_RECIBO = "TRASPASO_ESTACION_RECIBO";

	/**Esta constante se utiliza  para identificar el evento de consulta de estaciones-recibo por círculo  */
	public static final String CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO =
		"CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO";

	/**Esta constante se utiliza  para identificar el evento de consulta de estaciones-recibo  */
    public static final String CONSULTA_ESTACION_RECIBO = "CONSULTA_ESTACION_RECIBO";

	/**Esta constante se utiliza  para identificar el evento para hacer reset del último valor de recibo de una estación recibo   */
    public static final String RESET_ULTIMO_VALOR_ESTACION_RECIBO = "RESET_ULTIMO_VALOR_ESTACION_RECIBO";

	/**Esta constante se utiliza  para identificar el evento para hacer la consulta de una minuta */
    public static final String CONSULTA_MINUTA = "CONSULTA_MINUTA";

	/**Esta constante se utiliza  para identificar el evento para editar una minuta */
    public static final String EDITAR_MINUTA = "EDITAR_MINUTA";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de pago por círculo   */
    public static final String ADICIONA_CIRCULO_TIPO_PAGO = "ADICIONA_CIRCULO_TIPO_PAGO";

	/**Esta constante se utiliza  para identificar el evento de elminación de un tipo de pago por círculo   */
    public static final String ELIMINA_CIRCULO_TIPO_PAGO = "ELIMINA_CIRCULO_TIPO_PAGO";

	/**Esta constante se utiliza  para identificar el evento de selección de un tipo de pago por círculo   */
    public static final String SELECCIONA_CIRCULO_TIPO_PAGO = "SELECCIONA_CIRCULO_TIPO_PAGO";

	/**Esta constante se utiliza  para identificar el evento de adición de una firma de registrador  */
    public static final String ADICIONA_FIRMA_REGISTRADOR = "ADICIONA_FIRMA_REGISTRADOR";

	/**Esta constante se utiliza  para identificar el evento de activación de una firma de registrador  */
    public static final String ACTIVA_FIRMA_REGISTRADOR = "ACTIVA_FIRMA_REGISTRADOR";

	/**Esta constante se utiliza  para identificar el evento de inactivación de una firma de registrador  */
    public static final String INACTIVA_FIRMA_REGISTRADOR = "INACTIVA_FIRMA_REGISTRADOR";

	/**Esta constante se utiliza  para identificar el evento de inactivación definitiva de una firma de registrador  */
    public static final String INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR = "INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR";

	/**Esta constante se utiliza  para identificar el evento de selección de una firma de registrador  */
    public static final String SELECCIONA_CIRCULO_FIRMA_REGISTRADOR = "SELECCIONA_CIRCULO_FIRMA_REGISTRADOR";

	/**Esta constante se utiliza  para identificar el evento de buscar firmas de registrador  */
    public static final String BUSCAR_FIRMA_REGISTRADOR_CIRCULO = "BUSCAR_FIRMA_REGISTRADOR_CIRCULO";

	/**Esta constante se utiliza  para identificar el evento de buscar firmas de registrador  */
    public static final String GUARDAR_FIRMA_REGISTRADOR = "GUARDAR_FIRMA_REGISTRADOR";

	/**Esta constante se utiliza  para identificar el evento de activación de una firma de registrador  */
    public static final String CONSULTA_ABOGADOS_POR_CIRCULO = "CONSULTA_ABOGADOS_POR_CIRCULO";

	/**Esta constante se utiliza  para identificar el evento de actualización de subtipos de atención de un usuario  */
	public static final String ACTUALIZA_SUBTIPOS_ATENCION_USUARIO =
		"ACTUALIZA_SUBTIPOS_ATENCION_USUARIO";

	/**Esta constante se utiliza  para identificar el evento de adición  de una validación nota */
    public static final String ADICIONA_VALIDACION_NOTA = "ADICIONA_VALIDACION_NOTA";

	/**Esta constante se utiliza  para identificar el evento de elminación  de una validación nota */
    public static final String ELIMINA_VALIDACION_NOTA = "ELIMINA_VALIDACION_NOTA";

	/**Esta constante se utiliza  para identificar el evento de consulta de las  fases de un proceso */
    public static final String CONSULTA_FASE_PROCESO = "CONSULTA_FASE_PROCESO";

	/**Esta constante se utiliza  para identificar el evento de adición  de un causal de restitución */
    public static final String ADICIONA_CAUSAL_RESTITUCION = "ADICIONA_CAUSAL_RESTITUCION";

	/**Esta constante se utiliza  para identificar el evento de eliminación  de un causal de restitución */
    public static final String ELIMINA_CAUSAL_RESTITUCION = "ELIMINA_CAUSAL_RESTITUCION";

	/**Esta constante se utiliza  para identificar el evento de adición  de un tipo de certificado de validación */
	public static final String ADICIONA_TIPO_CERTIFICADO_VALIDACION =
		"ADICIONA_TIPO_CERTIFICADO_VALIDACION";

	/**Esta constante se utiliza  para identificar el evento de eliminación  de un tipo de certificado de validación */
	public static final String ELIMINA_TIPO_CERTIFICADO_VALIDACION =
		"ELIMINA_TIPO_CERTIFICADO_VALIDACION";

	/**Esta constante se utiliza  para identificar el evento de consulta  de validaciones de tipos de certificado */
	public static final String CONSULTA_VALIDACIONES_DE_TIPO_CERTIFICADO =
		"CONSULTA_VALIDACIONES_DE_TIPO_CERTIFICADO";

	/**Esta constante se utiliza  para identificar el evento de adición  de un tipo de nota */
    public static final String ADICIONA_TIPO_NOTA = "ADICIONA_TIPO_NOTA";

	/**Esta constante se utiliza  para identificar el evento de elminación  de un tipo de nota */
    public static final String ELIMINA_TIPO_NOTA = "ELIMINA_TIPO_NOTA";

	/**Esta constante se utiliza  para identificar el evento de elminación  de un tipo de nota */
    public static final String ELIMINA_TIPO_NOTA_INF = "ELIMINA_TIPO_NOTA_INF";

	/**Esta constante se utiliza  para identificar el evento de elminación  de un tipo de nota */
    public static final String ELIMINA_TIPO_NOTA_DEV = "ELIMINA_TIPO_NOTA_DEV";

	/**Esta constante se utiliza  para identificar el evento de MODIFICACIÓN  de un tipo de nota */
    public static final String MODIFICA_TIPO_NOTA = "MODIFICA_TIPO_NOTA";

	/**Esta constante se utiliza  para identificar el evento de consulta  de tipos de nota  por proceso */
    public static final String CONSULTA_TIPO_NOTA_POR_PROCESO = "CONSULTA_TIPO_NOTA_POR_PROCESO";

	/**Esta constante se utiliza  para identificar el evento de consulta  de tipos de nota informativas por proceso */
    public static final String CONSULTA_TIPO_NOTA_INF_POR_PROCESO = "CONSULTA_TIPO_NOTA_INF_POR_PROCESO";

	/**Esta constante se utiliza  para identificar el evento de consulta  de tipos de nota informativas por proceso */
    public static final String CONSULTA_TIPO_NOTA_DEV_POR_PROCESO = "CONSULTA_TIPO_NOTA_DEV_POR_PROCESO";

	/**Esta constante se utiliza  para identificar el evento de adición de check items */
    public static final String ADICIONA_CHECK_ITEM = "ADICIONA_CHECK_ITEM";

	/**Esta constante se utiliza  para identificar el evento de elminación de check items */
    public static final String ELIMINA_CHECK_ITEM = "ELIMINA_CHECK_ITEM";

	/**Esta constante se utiliza  para identificar el evento de edición de check items */
    public static final String EDITA_CHECK_ITEM = "EDITA_CHECK_ITEM";

	/**Esta constante se utiliza  para identificar el evento de consulta de check items por subtipo de solicitud */
	public static final String CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD =
		"CONSULTA_ELIMINA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD";

	/**Esta constante se utiliza  para identificar el evento de consultar turnos a entregar en certificados */
	public static final String CONSULTA_TURNOS_CER_ENTREGA_POR_FECHA =
		"CONSULTA_TURNOS_CER_ENTREGA_POR_FECHA";

	/**Esta constante se utiliza  para identificar el evento de consultar turnos a entregar en consultas */
	public static final String CONSULTA_TURNOS_CON_ENTREGA_POR_FECHA =
		"CONSULTA_TURNOS_CON_ENTREGA_POR_FECHA";

	/**Esta constante se utiliza  para identificar el evento de consulta de probabilidad de revision calificacion */
	public static final String CONSULTAR_PROB_REV_CALIFICACION =
		"CONSULTAR_PROB_REV_CALIFICACION";

	/**Esta constante se utiliza  para identificar el evento de modificar de probabilidad de revision calificacion */
	public static final String MODIFICAR_PROB_REV_CALIFICACION =
		"MODIFICAR_PROB_REV_CALIFICACION";

	/** Constante que identifica la acción de consultar solicitudes no pagadas */
	public static final String CONSULTA_SOLICITUDES_NO_PAGADAS =
		"CONSULTA_SOLICITUDES_NO_PAGADAS";

	/** Constante que identifica la acción de eliminar solicitudes no pagadas */
	public static final String ELIMINAR_SOLICITUDES_NO_PAGADAS =
		"ELIMINAR_SOLICITUDES_NO_PAGADAS";

	/** Constante que identifica la acción de eliminar solicitudes no pagadas */
	public static final String ELIMINAR_TODAS_LAS_SOLICITUDES_NO_PAGADAS =
		"ELIMINAR_TODAS_LAS_SOLICITUDES_NO_PAGADAS";

	/** Constante que identifica la acción de consultar turnos de calificacion */
	public static final String CONSULTA_TURNOS_CALIFICACION =
		"CONSULTA_TURNOS_CALIFICACION";

	/** Constante que identifica la acción de solicitar la correcion de una calificacion  */
	public static final String SOLICITAR_CORRECCION_CALIFICACION =
		"SOLICITAR_CORRECCION_CALIFICACION";

	/**Esta constante se utiliza  para identificar el evento de entrega masiva de certificados */
    public static final String ENTREGA_MASIVA_CERTIFICADOS = "ENTREGA_MASIVA_CERTIFICADOS";

	/**Esta constante se utiliza  para identificar el evento de entrega masiva de consultas */
    public static final String ENTREGA_MASIVA_CONSULTAS = "ENTREGA_MASIVA_CONSULTAS";

	/** Identifica que se desean consultar las estaciones de un circulo */
    public static final String CONSULTA_ESTACIONES_CIRCULO = "CONSULTA_ESTACIONES_CIRCULO";

	/** Identifica que se desean consultar los tipos de tarifa por circulo */
    public static final String CONSULTA_TIPOS_TARIFA_POR_CIRCULO = "CONSULTA_TIPOS_TARIFA_POR_CIRCULO";


	/** Identifica que se desea adicionar una tarifa por circulo */
    public static final String ADICIONA_TARIFA_POR_CIRCULO = "ADICIONA_TARIFA_POR_CIRCULO";

	/**	Identifica que se desea configurar la información de una estacion */
    public static final String ADMIN_UNA_ESTACION = "ADMIN_UNA_ESTACION";

	/** Constante que identifica la acción de solicitar la adición de roles a una estación */
    public static final String ADD_ROLES_ESTACION = "ADD_ROLES_ESTACION";

	/** Constante que identifica la acción de solicitar remover roles a una estación */
    public static final String REMOVE_ROLES_ESTACION = "REMOVE_ROLES_ESTACION";

	/** Constante que identifica la acción de solicitar la adición de usuarios a una estación */
    public static final String ADD_USUARIOS_ESTACION = "ADD_USUARIOS_ESTACION";

	/** Constante que identifica la acción de soliticar remover usuarios de la estación */
    public static final String REMOVE_USUARIOS_ESTACION = "REMOVE_USUARIOS_ESTACION";

	/** Constante que identifica la accion de agregar una naturaleza juridica para reparto*/
    public static final String ADICIONA_NAT_JURIDICA_ENTIDAD = "ADICIONA_NAT_JURIDICA_ENTIDAD";

	/** Constante que identifica la accion de editar una naturaleza juridica para reparto*/
    public static final String EDITAR_NAT_JURIDICA_ENTIDAD = "EDITAR_NAT_JURIDICA_ENTIDAD";

	/** Constante que identifica la accion de agregar una naturaleza juridica para reparto*/
    public static final String ELIMINA_NAT_JURIDICA_ENTIDAD = "ELIMINA_NAT_JURIDICA_ENTIDAD";

	/** Constante que identifica la accion de agregar una entida publica para reparto*/
    public static final String ADICIONA_ENTIDAD_PUBLICA = "ADICIONA_ENTIDAD_PUBLICA";

	/** Constante que identifica la accion de editar una entidad publica para reparto*/
    public static final String EDITA_ENTIDAD_PUBLICA = "EDITA_ENTIDAD_PUBLICA";

	/** Constante que identifica la accion de consultar una entidad publica para reparto, por naturaleza jurídica*/
    public static final String CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA = "CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA";

	/** Constante que identifica la accion de agregar una entidad publica para reparto*/
    public static final String ELIMINA_ENTIDAD_PUBLICA = "ELIMINA_ENTIDAD_PUBLICA";

	/** Constante que identifica que se quieren cargar las naturalezas juridicas para reparto*/
    public static final String CARGAR_NAT_JURIDICAS = "CARGAR_NAT_JURIDICAS";

	/** Constante que identifica que se quiere reimprimir un formulario de calificacion*/
    public static final String REIMPRIMIR_FORMULARIO_CALIFICACION = "REIMPRIMIR_FORMULARIO_CALIFICACION";

	/** Constante que identifica que se quiere reimprimir un formulario de correcciones*/
    public static final String REIMPRIMIR_FORMULARIO_CORRECCIONES = "REIMPRIMIR_FORMULARIO_CORRECCIONES";

	/** Constante que identifica que se quiere obtener las impresoras de un circulo*/
    public static final String OBTENER_IMPRESORAS_CIRCULO = "OBTENER_IMPRESORAS_CIRCULO";

	
	/** Constante que identifica que se quiere obtener la lista de turnos de mayor valor vencidos*/
    public static final String OBTENER_VENCIDOS_PAGO_MAYOR_VALOR = "OBTENER_VENCIDOS_PAGO_MAYOR_VALOR";

	
	/** Constante que identifica que se quiere obtener la lista de turnos de mayor valor vencidos*/
    public static final String DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR = "DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR";
	/** Constante que identifica que se quiere obtener la lista de turnos de mayor valor vencidos*/
    public static final String ADICIONA_TIPO_VALIDACION = "ADICIONA_TIPO_VALIDACION";

    public static final String ELIMINAR_TURNOS_PAGO_MAYOR_VALOR_PENDIENTES = "ELIMINAR_TURNOS_PAGO_MAYOR_VALOR_PENDIENTES";

    public static final String OBTENER_PENDIENTES_PAGO_MAYOR_VALOR = "OBTENER_PENDIENTES_PAGO_MAYOR_VALOR";

	/** Constante que identifica que se quiere obtener la lista de calificadores del circulo actual*/
    public static final String CARGAR_CALIFICADORES_CIRCULO = "CARGAR_CALIFICADORES_CIRCULO";

	/** Constante que identifica que se quiere obtener la lista de calificadores del circulo actual*/
    public static final String CARGAR_CALIFICADORES_SUBTIPOATENCION = "CARGAR_CALIFICADORES_SUBTIPOATENCION";

	/** Constante que identifica que se quiere agregar un orden a un subtipo de atencion con un usuario dado*/
    public static final String ADICIONA_ORDEN_SUBTIPOATENCION = "ADICIONA_ORDEN_SUBTIPOATENCION";

	/** Constante que identifica que se quiere eliminar un orden a un subtipo de atencion con un usuario dado*/
    public static final String REMOVE_ORDEN_SUBTIPOATENCION = "REMOVE_ORDEN_SUBTIPOATENCION";

    /**
	* Constante que indica que se seleccionó un turno para realizar una devolución.
     */
    public static final String SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION = "SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION";

    /**
	* Constante que indica que se seleccionó la edición de un causal de restitución.
     */
    public static final String EDITAR_CAUSAL_RESTITUCION = "EDITAR_CAUSAL_RESTITUCION";


    /**
	* Constante que indica que se seleccionó la edición de los atributos de un causal de restitución.
     */
    public static final String EDITAR_DETALLES_CAUSAL_RESTITUCION = "EDITAR_DETALLES_CAUSAL_RESTITUCION";

    public static final String CARGAR_CIRCULOS_NOTARIALES = "CARGAR_CIRCULOS_NOTARIALES";

    public static final String AGREGAR_CIRCULO_NOTARIAL = "AGREGAR_CIRCULO_NOTARIAL";

    public static final String ELIMINAR_CIRCULO_NOTARIAL = "ELIMINAR_CIRCULO_NOTARIAL";

	public static final String EDITAR_CIRCULO_NOTARIAL="EDITAR_CIRCULO_NOTARIAL";

	public static final String CARGAR_ZONAS_NOTARIALES="CARGAR_ZONAS_NOTARIALES";

    public static final String AGREGAR_ZONA_NOTARIAL = "AGREGAR_ZONA_NOTARIAL";

    public static final String ELIMINAR_ZONA_NOTARIAL = "ELIMINAR_ZONA_NOTARIAL";

    public static final String CARGAR_ROLES_PANTALLAS_ADMINISTRATIVAS = "CARGAR_ROLES_PANTALLAS_ADMINISTRATIVAS";

    public static final String AGREGAR_ROL_PANTALLA_ADMINISTRATIVA = "AGREGAR_ROL_PANTALLA_ADMINISTRATIVA";

    public static final String ELIMINAR_ROL_PANTALLA_ADMINISTRATIVA = "ELIMINAR_ROL_PANTALLA_ADMINISTRATIVA";

	/** Constante que identifica que se quiere reimprimir un formulario de testamentos
	 * Pablo Quintana*/
    public static final String REIMPRIMIR_FORMULARIO_TESTAMENTOS = "REIMPRIMIR_FORMULARIO_TESTAMENTOS";
    
    public static final String ACT_INACT_CTA_BANCARIA = "ACT_INACT_CTA_BANCARIA";
    
    public static final String ACT_INACT_CANAL_RECAUDO = "ACT_INACT_CANAL_RECAUDO";
    
    public static final String SELECCIONA_CUENTA_BANCARIA = "SELECCIONA_CUENTA_BANCARIA";
    
    public static final String ACT_INACT_CTP = "ACT_INACT_CTP";

    private Banco banco;

    private SucursalBanco sucursalBanco;

    private AlcanceGeografico alcanceGeografico;

    private Tarifa tarifa;

    private TipoTarifa tipoTarifa;

    private TipoFotocopia tipoFotocopia;

    private SubtipoSolicitud subtipoSolicitud;

    private OPLookupTypes opLookupType;

    private OPLookupTypes opLookupTypeAEditar;

    private OPLookupCodes opLookupCodes;

    private OPLookupCodes opLookupCodesAEditar;

    private TipoCalculo tipoCalculo;

    private TipoImpuesto tipoImpuesto;

    private SubtipoAtencion subtipoAtencion;

    private TipoActo tipoActo;

    private TipoDerechoReg tipoDerechoRegistral;

    private AccionNotarial accionNotarial;

    private Categoria categoria;

    private EstacionRecibo estacionRecibo;

    private EstacionRecibo estacionReciboA;

    private String turnoMinutaId;

    private Minuta minuta;

    private Circulo circulo;

    private Circulo circuloA;

    private CirculoTipoPago circuloTipoPago;

    private FirmaRegistrador firmaRegistrador;

    private List subtiposAtencion;

    private List usuariosNegocio;

    private ValidacionNota validacionNota;

    private Proceso proceso;

    private CausalRestitucion causalRestitucion;

    private TipoCertificado tipoCertificado;

    private TipoCertificadoValidacion tipoCertificadoValidacion;

    private TipoNota tipoNota;

    private CheckItem checkItem;

    private Date fecha;

    private Fase fase;

    private List turnos;

    private String valorProbRevCalificacion;

    private Date fechaDesdeConsulta;

    private Date fechaHastaConsulta;

    private List solicitudes;

    private String turnoCorreccionId;

    private Nota notaInformativaCorreccion;

    private Estacion estacion;

    private List roles;

    private List usuarios;

	private ValidacionParametrosException vpe=null;

    private gov.sir.core.negocio.modelo.Usuario usuarioNeg;

    private gov.sir.core.negocio.modelo.Usuario usuarioSIR;

    private NaturalezaJuridicaEntidad natJuridicaEntidad;

    private String idNatJuridicaEntidad;

    private EntidadPublica entidadPublica;

    private String idTurnoCalificacion;

    private String UID;

    private int numeroImpresiones = 1;

    private String impresoraSeleccionada;

    private Validacion validacion;

    private String idSubtipoAtencion;

    private String idUsuario;

    private String orden;

    private UsuarioSubtipoAtencion usuSubtipoAtencion;

    private File fileFirmaRegistrador;

    private CirculoNotarial circuloNotarial;

    private ZonaNotarial zonaNotarial;

    private double devolucionDerechos;

    private double devolucionCertificados;

    private double devolucionImpuestos;

    private String identificadorTurnoDevolucion;

    private RolPantalla rolPantallaAAgregar;

    private RolPantalla rolPantallaAEliminar;

    private CuentasBancarias cuentasBancarias;
    
    private String estadosCtasBancarias;
    
    private CanalesRecaudo canalesRecaudo;
    
    private String estadosCanalesRecaudo;
    
    private String estadosCtp;
    
    private TipoPago tipoPago;

    /**
     * @param usuario
     */
    public EvnAdministracionHermod(Usuario usuario) {
        super(usuario);
    }

    /**
     * @param usuario
     * @param tipoEvento
     */
    public EvnAdministracionHermod(Usuario usuario, String tipoEvento) {
        super(usuario, tipoEvento);
    }

    /**
     * @return
     */
    public Banco getBanco() {
        return banco;
    }

    /**
     * @param banco
     */
    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    /**
     * @return
     */
    public SucursalBanco getSucursalBanco() {
        return sucursalBanco;
    }

    /**
     * @param banco
     */
    public void setSucursalBanco(SucursalBanco banco) {
        sucursalBanco = banco;
    }

    /**
     * @return
     */
    public AlcanceGeografico getAlcanceGeografico() {
        return alcanceGeografico;
    }

    /**
     * @param geografico
     */
    public void setAlcanceGeografico(AlcanceGeografico geografico) {
        alcanceGeografico = geografico;
    }

    /**
     * @return
     */
    public Tarifa getTarifa() {
        return tarifa;
    }

    /**
     * @param tarifa
     */
    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * @return
     */
    public TipoFotocopia getTipoFotocopia() {
        return tipoFotocopia;
    }

    /**
     * @param fotocopia
     */
    public void setTipoFotocopia(TipoFotocopia fotocopia) {
        tipoFotocopia = fotocopia;
    }

    /**
     * @return
     */
    public SubtipoSolicitud getSubtipoSolicitud() {
        return subtipoSolicitud;
    }

    /**
     * @param solicitud
     */
    public void setSubtipoSolicitud(SubtipoSolicitud solicitud) {
        subtipoSolicitud = solicitud;
    }

    /**
     * @return
     */
    public OPLookupTypes getOpLookupType() {
        return opLookupType;
    }

    /**
     * @param types
     */
    public void setOpLookupType(OPLookupTypes types) {
        opLookupType = types;
    }

    /**
     * @return
     */
    public OPLookupCodes getOpLookupCodes() {
        return opLookupCodes;
    }

    /**
     * @param codes
     */
    public void setOpLookupCodes(OPLookupCodes codes) {
        opLookupCodes = codes;
    }

    /**
     * @return
     */
    public TipoCalculo getTipoCalculo() {
        return tipoCalculo;
    }

    /**
     * @param calculo
     */
    public void setTipoCalculo(TipoCalculo calculo) {
        tipoCalculo = calculo;
    }

    /**
     * @return
     */
    public TipoImpuesto getTipoImpuesto() {
        return tipoImpuesto;
    }

    /**
     * @param impuesto
     */
    public void setTipoImpuesto(TipoImpuesto impuesto) {
        tipoImpuesto = impuesto;
    }

    /**
     * @return
     */
    public TipoTarifa getTipoTarifa() {
        return tipoTarifa;
    }

    /**
     * @param tarifa
     */
    public void setTipoTarifa(TipoTarifa tarifa) {
        tipoTarifa = tarifa;
    }

    /**
     * @return
     */
    public SubtipoAtencion getSubtipoAtencion() {
        return subtipoAtencion;
    }

    /**
     * @param atencion
     */
    public void setSubtipoAtencion(SubtipoAtencion atencion) {
        subtipoAtencion = atencion;
    }

    /**
     * @return
     */
    public TipoActo getTipoActo() {
        return tipoActo;
    }

    /**
     * @param acto
     */
    public void setTipoActo(TipoActo acto) {
        tipoActo = acto;
    }

    /**
     * @return
     */
    public TipoDerechoReg getTipoDerechoRegistral() {
        return tipoDerechoRegistral;
    }

    /**
     * @param reg
     */
    public void setTipoDerechoRegistral(TipoDerechoReg reg) {
        tipoDerechoRegistral = reg;
    }

    /**
     * @return
     */
    public AccionNotarial getAccionNotarial() {
        return accionNotarial;
    }

    /**
     * @param notarial
     */
    public void setAccionNotarial(AccionNotarial notarial) {
        accionNotarial = notarial;
    }

    /**
     * @return
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return
     */
    public EstacionRecibo getEstacionRecibo() {
        return estacionRecibo;
    }

    /**
     * @param recibo
     */
    public void setEstacionRecibo(EstacionRecibo recibo) {
        estacionRecibo = recibo;
    }

    /**
     * @return
     */
    public EstacionRecibo getEstacionReciboA() {
        return estacionReciboA;
    }

    /**
     * @param recibo
     */
    public void setEstacionReciboA(EstacionRecibo recibo) {
        estacionReciboA = recibo;
    }

    /**
     * @return
     */
    public String getTurnoMinutaId() {
        return turnoMinutaId;
    }

    /**
     * @param string
     */
    public void setTurnoMinutaId(String string) {
        turnoMinutaId = string;
    }

    /**
     * @return
     */
    public Minuta getMinuta() {
        return minuta;
    }

    /**
     * @param minuta
     */
    public void setMinuta(Minuta minuta) {
        this.minuta = minuta;
    }

    /**
     * @return
     */
    public Circulo getCirculo() {
        return circulo;
    }

    /**
     * @param circulo
     */
    public void setCirculo(Circulo circulo) {
        this.circulo = circulo;
    }

    /**
     * @return
     */
    public Circulo getCirculoA() {
        return circuloA;
    }

    /**
     * @param circulo
     */
    public void setCirculoA(Circulo circulo) {
        this.circuloA = circulo;
    }

    /**
     * @return
     */
    public CirculoTipoPago getCirculoTipoPago() {
        return circuloTipoPago;
    }

    /**
     * @param pago
     */
    public void setCirculoTipoPago(CirculoTipoPago pago) {
        circuloTipoPago = pago;
    }

    /**
     * @return
     */
    public FirmaRegistrador getFirmaRegistrador() {
        return firmaRegistrador;
    }

    /**
     * @param registrador
     */
    public void setFirmaRegistrador(FirmaRegistrador registrador) {
        firmaRegistrador = registrador;
    }

    /**
     * @return
     */
    public List getSubtiposAtencion() {
        return subtiposAtencion;
    }

    /**
     * @param list
     */
    public void setSubtiposAtencion(List list) {
        subtiposAtencion = list;
    }

    /**
     * @return
     */
    public List getUsuariosNegocio() {
        return usuariosNegocio;
    }

    /**
     * @param list
     */
    public void setUsuariosNegocio(List list) {
        usuariosNegocio = list;
    }

    /**
     * @return
     */
    public ValidacionNota getValidacionNota() {
        return validacionNota;
    }

    /**
     * @param nota
     */
    public void setValidacionNota(ValidacionNota nota) {
        validacionNota = nota;
    }

    /**
     * @return
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * @param proceso
     */
    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    /**
     * @return
     */
    public CausalRestitucion getCausalRestitucion() {
        return causalRestitucion;
    }

    /**
     * @param restitucion
     */
    public void setCausalRestitucion(CausalRestitucion restitucion) {
        causalRestitucion = restitucion;
    }

    /**
     * @return
     */
    public TipoCertificadoValidacion getTipoCertificadoValidacion() {
        return tipoCertificadoValidacion;
    }

    /**
     * @param validacion
     */
    public void setTipoCertificadoValidacion(TipoCertificadoValidacion validacion) {
        tipoCertificadoValidacion = validacion;
    }

    /**
     * @return
     */
    public TipoCertificado getTipoCertificado() {
        return tipoCertificado;
    }

    /**
     * @param certificado
     */
    public void setTipoCertificado(TipoCertificado certificado) {
        tipoCertificado = certificado;
    }

    /**
     * @return
     */
    public TipoNota getTipoNota() {
        return tipoNota;
    }

    /**
     * @param nota
     */
    public void setTipoNota(TipoNota nota) {
        tipoNota = nota;
    }

    /**
     * @return
     */
    public CheckItem getCheckItem() {
        return checkItem;
    }

    /**
     * @param item
     */
    public void setCheckItem(CheckItem item) {
        checkItem = item;
    }

    /**
     * @return
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param date
     */
    public void setFecha(Date date) {
        fecha = date;
    }

    /**
     * @return
     */
    public Fase getFase() {
        return fase;
    }

    /**
     * @param fase
     */
    public void setFase(Fase fase) {
        this.fase = fase;
    }

    /**
     * @return
     */
    public List getTurnos() {
        return turnos;
    }

    /**
     * @param list
     */
    public void setTurnos(List list) {
        turnos = list;
    }

    /**
     * @param valorProb
     */
    public void setValorProbRevCalificion(String valorProb) {
        valorProbRevCalificacion = valorProb;
    }

    /**
     * @return
     */
    public String getValorProbRevCalificion() {
        return valorProbRevCalificacion;
    }

    /**
     * @param nfechaDesde
     */
    public void setValorFechaDesde(Date nfechaDesde) {
        fechaDesdeConsulta = nfechaDesde;
    }

    /**
     * @return
     */
    public Date getValorFechaDesde() {
        return fechaDesdeConsulta;
    }

    /**
     * @param nfechaHasta
     */
    public void setValorFechaHasta(Date nfechaHasta) {
        fechaHastaConsulta = nfechaHasta;
    }

    /**
     * @return
     */
    public Date getValorFechaHasta() {
        return fechaHastaConsulta;
    }

    /**
     * @param nsolicitudes
     */
    public void setSolicitudes(List nsolicitudes) {
        solicitudes = nsolicitudes;
    }

    /**
     * @return
     */
    public List getSolicitudes() {
        return solicitudes;
    }

    /**
     * @param nsolicitudes
     */
    public void setTurnoCorreccionId(String nTurnoCorreccionId) {
        turnoCorreccionId = nTurnoCorreccionId;
    }

    /**
     * @return
     */
    public String getTurnoCorreccionId() {
        return turnoCorreccionId;
    }

    /**
     * @param nsolicitudes
     */
    public void setNotaInformativaCorreccion(Nota nNotaInformativaCorreccion) {
        notaInformativaCorreccion = nNotaInformativaCorreccion;
    }

    /**
     * @return
     */
    public Nota getNotaInformativaCorreccion() {
        return notaInformativaCorreccion;
    }
    /**
     * @return
     */
    public Estacion getEstacion() {
        return estacion;
    }

    /**
     * @param estacion
     */
    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    /**
     * @return
     */
    public List getRoles() {
        return roles;
    }

    /**
     * @param list
     */
    public void setRoles(List list) {
        roles = list;
    }

    /**
     * @return
     */
    public ValidacionParametrosException getVpe() {
        return vpe;
    }

    /**
     * @param exception
     */
    public void setVpe(ValidacionParametrosException exception) {
        vpe = exception;
    }

    /**
     * @param usuarioNeg
     */
    public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		this.usuarioNeg= usuarioNeg;

    }

    /**
     * @return Returns the usuarioNeg.
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
        return usuarioNeg;
    }

    /**
     * @param usuario
     */
    public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuario) {
		this.usuarioSIR=usuario;

    }
    /**
     * @return Returns the usuarioSIR.
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
        return usuarioSIR;
    }

    /**
     * @return
     */
    public NaturalezaJuridicaEntidad getNatJuridicaEntidad() {
        return natJuridicaEntidad;
    }

    /**
     * @param entidad
     */
    public void setNatJuridicaEntidad(NaturalezaJuridicaEntidad entidad) {
        natJuridicaEntidad = entidad;
    }

    /**
     * @return
     */
    public String getIdNatJuridicaEntidad() {
        return idNatJuridicaEntidad;
    }

    /**
     * @param string
     */
    public void setIdNatJuridicaEntidad(String string) {
        idNatJuridicaEntidad = string;
    }

    /**
     * @return
     */
    public EntidadPublica getEntidadPublica() {
        return entidadPublica;
    }

    /**
     * @param publica
     */
    public void setEntidadPublica(EntidadPublica publica) {
        entidadPublica = publica;
    }

    /**
     * @return
     */
    public String getIdTurnoCalificacion() {
        return idTurnoCalificacion;
    }

    /**
     * @param string
     */
    public void setIdTurnoCalificacion(String string) {
        idTurnoCalificacion = string;
    }

    /**
     * @return
     */
    public String getUID() {
        return UID;
    }

    /**
     * @param string
     */
    public void setUID(String string) {
        UID = string;
    }

    /**
     * @return
     */
    public int getNumeroImpresiones() {
        return numeroImpresiones;
    }

    /**
     * @param i
     */
    public void setNumeroImpresiones(int i) {
        numeroImpresiones = i;
    }

    /**
     * @return
     */
    public List getUsuarios() {
        return usuarios;
    }

    /**
     * @param list
     */
    public void setUsuarios(List list) {
        usuarios = list;
    }

    public void setImpresoraSeleccionada(String impresora) {
        impresoraSeleccionada = impresora;
    }

    public String getImpresoraSeleccionada() {
        return impresoraSeleccionada;
    }

    private long numeroResultadosPorPagina = 0;
    private long numeroPagina = 0;
    private String filtro = "";

    /**
     * @param resultadosPorPagina
     */
    public void setNumeroResultadosPorPagina(long resultadosPorPagina) {

        numeroResultadosPorPagina = resultadosPorPagina;
    }

    /**
     * @param numeroPagina
     */
    public void setNumeroPagina(long numeroPagina) {

        this.numeroPagina = numeroPagina;
    }

    /**
     * @param filtro
     */
    public void setFiltro(String filtro) {

        this.filtro = filtro;
    }

    /**
     * @return
     */
    public long getNumeroPagina() {

        return numeroPagina;
    }

    /**
     * @return
     */
    public long getNumeroResultadosPorPagina() {

        return numeroResultadosPorPagina;
    }

    /**
     * @return
     */
    public String getFiltro() {

        return filtro;
    }

    public Validacion getValidacion() {
        return validacion;
    }

    public void setValidacion(Validacion validacion) {
        this.validacion = validacion;
    }

    private String idTurno;

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public String getIdSubtipoAtencion() {
        return idSubtipoAtencion;
    }

    public void setIdSubtipoAtencion(String idSubtipoAtencion) {
        this.idSubtipoAtencion = idSubtipoAtencion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public UsuarioSubtipoAtencion getUsuSubtipoAtencion() {
        return usuSubtipoAtencion;
    }

    public void setUsuSubtipoAtencion(UsuarioSubtipoAtencion usuSubtipoAtencion) {
        this.usuSubtipoAtencion = usuSubtipoAtencion;
    }

    public File getFileFirmaRegistrador() {
        return fileFirmaRegistrador;
    }

    public void setFileFirmaRegistrador(File fileFirmaRegistrador) {
        this.fileFirmaRegistrador = fileFirmaRegistrador;
    }

    public OPLookupTypes getOpLookupTypeAEditar() {
        return opLookupTypeAEditar;
    }

    public void setOpLookupTypeAEditar(OPLookupTypes opLookupTypeAEditar) {
        this.opLookupTypeAEditar = opLookupTypeAEditar;
    }

    public OPLookupCodes getOpLookupCodesAEditar() {
        return opLookupCodesAEditar;
    }

    public void setOpLookupCodesAEditar(OPLookupCodes opLookupCodesAEditar) {
        this.opLookupCodesAEditar = opLookupCodesAEditar;
    }
    /**
     * @return
     */
    public double getDevolucionCertificados() {
        return devolucionCertificados;
    }


    /**
     * @return Returns the circuloNotarial.
     */
    public CirculoNotarial getCirculoNotarial() {
        return circuloNotarial;
    }
    /**
     * @param circuloNotarial The circuloNotarial to set.
     */
    public void setCirculoNotarial(CirculoNotarial circuloNotarial) {
        this.circuloNotarial = circuloNotarial;
    }
    /**
     * @return Returns the zonaNotarial.
     */
    public ZonaNotarial getZonaNotarial() {
        return zonaNotarial;
    }
    /**
     * @param zonaNotarial The zonaNotarial to set.
     */
    public void setZonaNotarial(ZonaNotarial zonaNotarial) {
        this.zonaNotarial = zonaNotarial;
    }

    /**
     * @return
     */
    public double getDevolucionDerechos() {
        return devolucionDerechos;
    }

    /**
     * @return
     */
    public double getDevolucionImpuestos() {
        return devolucionImpuestos;
    }

    /**
     * @return
     */
    public String getIdentificadorTurnoDevolucion() {
        return identificadorTurnoDevolucion;
    }

    /**
     * @param d
     */
    public void setDevolucionCertificados(double d) {
        devolucionCertificados = d;
    }

    /**
     * @param d
     */
    public void setDevolucionDerechos(double d) {
        devolucionDerechos = d;
    }

    /**
     * @param d
     */
    public void setDevolucionImpuestos(double d) {
        devolucionImpuestos = d;
    }

    /**
     * @param string
     */
    public void setIdentificadorTurnoDevolucion(String string) {
        identificadorTurnoDevolucion = string;
    }

    public RolPantalla getRolPantallaAAgregar() {
        return rolPantallaAAgregar;
    }

    public void setRolPantallaAAgregar(RolPantalla rolPantallaAAgregar) {
        this.rolPantallaAAgregar = rolPantallaAAgregar;
    }

    public RolPantalla getRolPantallaAEliminar() {
        return rolPantallaAEliminar;
    }

    public void setRolPantallaAEliminar(RolPantalla rolPantallaAEliminar) {
        this.rolPantallaAEliminar = rolPantallaAEliminar;
    }

    private BancosXCirculo bancoXCirculo;

    public BancosXCirculo getBancoXCirculo() {
        return bancoXCirculo;
    }

    public void setBancoXCirculo(BancosXCirculo bancoXCirculo) {
        this.bancoXCirculo = bancoXCirculo;
    }

    public CuentasBancarias getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(CuentasBancarias cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }

    public String getEstadosCtasBancarias() {
        return estadosCtasBancarias;
    }

    public void setEstadosCtasBancarias(String estadosCtasBancarias) {
        this.estadosCtasBancarias = estadosCtasBancarias;
    }

    public CanalesRecaudo getCanalesRecaudo() {
        return canalesRecaudo;
    }

    public void setCanalesRecaudo(CanalesRecaudo canalesRecaudo) {
        this.canalesRecaudo = canalesRecaudo;
    }

    public String getEstadosCanalesRecaudo() {
        return estadosCanalesRecaudo;
    }

    public void setEstadosCanalesRecaudo(String estadosCanalesRecaudo) {
        this.estadosCanalesRecaudo = estadosCanalesRecaudo;
    }

    public String getEstadosCtp() {
        return estadosCtp;
    }

    public void setEstadosCtp(String estadosCtp) {
        this.estadosCtp = estadosCtp;
    }  

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }    

}
