package gov.sir.core.eventos.administracion;

import java.util.Hashtable;
import java.util.List;

import org.auriga.core.modelo.transferObjects.Estacion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.modelo.BancosXCirculo;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.EstacionRecibo;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas con la
 * administración de objetos de Hermod
 *
 * @author jmendez
 */
public class EvnRespAdministracionHermod extends EvnSIRRespuesta {

    /**
     * Esta constante se utiliza para identificar el evento de obtención
     * satisfactoria del listado de cuentas para un círculo - banco
     */
    public static final String CUENTAS_X_CIRCULO_BANCO_OK = "CUENTAS_X_CIRCULO_BANCO_OK";
    
    /**
     * Esta constante se utiliza para identificar el evento de obtención
     * satisfactoria del listado de cuentas para un círculo
     */
    public static final String CUENTAS_X_CIRCULO_OK = "CUENTAS_X_CIRCULO_OK";
    
    /**
     * Esta constante se utiliza para identificar el evento de obtención
     * satisfactoria del listado de canales de recaudo
     */
    public static final String CANAL_RECAUDO_OK = "CANAL_RECAUDO_OK";
    

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un banco
     */
    public static final String ADICIONA_BANCO_OK = "ADICIONA_BANCO_OK";

    /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Esta constante se utiliza  para identificar el evento de 
         * creación satisfactoria de un  banco/franquicia.
     */
    public static final String ADICIONA_BANCO_FRANQUICIA_OK = "ADICIONA_BANCO_FRANQUICIA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un banco
     */
    public static final String ELIMINA_BANCO_OK = "ELIMINA_BANCO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de las sucursales de un banco
     */
    public static final String LISTADO_SUCURSALES_OK = "LISTADO_SUCURSALES_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de una sucursal de banco
     */
    public static final String ADICIONA_SUCURSAL_BANCO_OK = "ADICIONA_SUCURSAL_BANCO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de una sucursal de banco
     */
    public static final String ELIMINA_SUCURSAL_BANCO_OK = "ELIMINA_SUCURSAL_BANCO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de alcance geográfico para las consultas
     */
    public static final String ADICIONA_ALCANCE_GEOGRAFICO_OK = "ADICIONA_ALCANCE_GEOGRAFICO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de alcance geográfico para las consultas
     */
    public static final String ELIMINA_ALCANCE_GEOGRAFICO_OK = "ELIMINA_ALCANCE_GEOGRAFICO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de una tarifa
     */
    public static final String ADICIONA_TARIFA_OK = "ADICIONA_TARIFA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de una tarifa
     */
    public static final String ELIMINA_TARIFA_OK = "ELIMINA_TARIFA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de tarifa
     */
    public static final String ADICIONA_TIPO_TARIFA_OK = "ADICIONA_TIPO_TARIFA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de tarifa
     */
    public static final String ELIMINA_TIPO_TARIFA_OK = "ELIMINA_TIPO_TARIFA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de selección
     * satisfactoria de un tipo de tarifa
     */
    public static final String SELECCIONA_TIPO_TARIFA_OK = "SELECCIONA_TIPO_TARIFA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de fotocopia
     */
    public static final String ADICIONA_TIPO_FOTOCOPIA_OK = "ADICIONA_TIPO_FOTOCOPIA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de fotocopia
     */
    public static final String ELIMINA_TIPO_FOTOCOPIA_OK = "ELIMINA_TIPO_FOTOCOPIA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un subtipo de solicitud
     */
    public static final String ADICIONA_SUBTIPO_SOLICITUD_OK = "ADICIONA_SUBTIPO_SOLICITUD_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un subtipo de solicitud
     */
    public static final String ELIMINA_SUBTIPO_SOLICITUD_OK = "ELIMINA_SUBTIPO_SOLICITUD_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un objeto oplookup type
     */
    public static final String ADICIONA_OPLOOKUP_TYPE_OK = "ADICIONA_OPLOOKUP_TYPE_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un objeto oplookup type
     */
    public static final String ELIMINA_OPLOOKUP_TYPE_OK = "ELIMINA_OPLOOKUP_TYPE_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un objeto oplookup code
     */
    public static final String ADICIONA_OPLOOKUP_CODE_OK = "ADICIONA_OPLOOKUP_CODE_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un objeto oplookup code
     */
    public static final String ELIMINA_OPLOOKUP_CODE_OK = "ELIMINA_OPLOOKUP_CODE_OK";

    /**
     * Esta constante se utiliza para identificar el evento de listado de los
     * objeto oplookup code asociados a un oplookup type
     */
    public static final String LISTADO_LOOKUP_CODES_OK = "LISTADO_LOOKUP_CODES_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de cálculo
     */
    public static final String ADICIONA_TIPO_CALCULO_OK = "ADICIONA_TIPO_CALCULO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de cálculo
     */
    public static final String ELIMINA_TIPO_CALCULO_OK = "ELIMINA_TIPO_CALCULO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de impuesto
     */
    public static final String ADICIONA_TIPO_IMPUESTO_OK = "ADICIONA_TIPO_IMPUESTO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de impuesto
     */
    public static final String ELIMINA_TIPO_IMPUESTO_OK = "ELIMINA_TIPO_IMPUESTO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un subtipo de atención
     */
    public static final String ADICIONA_SUBTIPO_ATENCION_OK = "ADICIONA_SUBTIPO_ATENCION_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un subtipo de atención
     */
    public static final String ELIMINA_SUBTIPO_ATENCION_OK = "ELIMINA_SUBTIPO_ATENCION_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de acto
     */
    public static final String ADICIONA_TIPO_ACTO_OK = "ADICIONA_TIPO_ACTO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de acto
     */
    public static final String ELIMINA_TIPO_ACTO_OK = "ELIMINA_TIPO_ACTO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de derecho registral
     */
    public static final String ADICIONA_TIPO_DERECHO_REGISTRAL_OK = "ADICIONA_TIPO_DERECHO_REGISTRAL_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de derecho registral
     */
    public static final String ELIMINA_TIPO_DERECHO_REGISTRAL_OK = "ELIMINA_TIPO_DERECHO_REGISTRAL_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de una acción notarial
     */
    public static final String ADICIONA_ACCION_NOTARIAL_OK = "ADICIONA_ACCION_NOTARIAL_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de una acción notarial
     */
    public static final String ELIMINA_ACCION_NOTARIAL_OK = "ELIMINA_ACCION_NOTARIAL_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de una categoría
     */
    public static final String ADICIONA_CATEGORIA_OK = "ADICIONA_CATEGORIA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de una categoría
     */
    public static final String ELIMINA_CATEGORIA_OK = "ELIMINA_CATEGORIA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de una estación recibo
     */
    public static final String ADICIONA_ESTACION_RECIBO_OK = "ADICIONA_ESTACION_RECIBO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de una estación recibo
     */
    public static final String SET_ESTACION_RECIBO_OK = "SET_ESTACION_RECIBO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de traspaso
     * satisfactoria de una estación recibo
     */
    public static final String TRASPASO_ESTACION_RECIBO_OK = "TRASPASO_ESTACION_RECIBO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de una estación recibo
     */
    public static final String ELIMINA_ESTACION_RECIBO_OK = "ELIMINA_ESTACION_RECIBO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de las estaciones recibo existentes en el sistema
     */
    public static final String CONSULTA_ESTACIONES_RECIBO_OK = "CONSULTA_ESTACIONES_RECIBO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de los turnos de pago de mayor valor vencido
     */
    public static final String CONSULTAR_TURNOS_PMY_VENCIDOS_OK = "CONSULTAR_TURNOS_PMY_VENCIDOS_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta del
     * valor actual del recibo en una estación recibo
     */
    public static final String CONSULTA_VALOR_ESTACION_RECIBO_OK = "CONSULTA_VALOR_ESTACION_RECIBO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de reset del valor
     * actual del recibo en una estación recibo
     */
    public static final String RESET_ULTIMO_VALOR_ESTACION_RECIBO_OK
            = "RESET_ULTIMO_VALOR_ESTACION_RECIBO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de una minuta
     */
    public static final String CONSULTA_MINUTA_OK = "CONSULTA_MINUTA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de edición
     * satisfactoria de una minuta
     */
    public static final String EDITA_MINUTA_OK = "EDITA_MINUTA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de pago para un círculo
     */
    public static final String ADICIONA_CIRCULO_TIPO_PAGO_OK = "ADICIONA_CIRCULO_TIPO_PAGO_OK";
    
    /**
     * Esta constante se utiliza para identificar el evento de actualizacion
     * satisfactoria de un tipo de pago para un círculo
     */
    public static final String ACTUALIZA_CIRCULO_TIPO_PAGO_OK = "ACTUALIZA_CIRCULO_TIPO_PAGO_OK";    

    /**
     * Esta constante se utiliza para identificar el evento de eliminacion
     * satisfactoria de un tipo de pago para un círculo
     */
    public static final String ELIMINA_CIRCULO_TIPO_PAGO_OK = "ELIMINA_CIRCULO_TIPO_PAGO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de selección
     * satisfactoria de los tipos de pago para un círculo
     */
    public static final String SELECCIONA_CIRCULO_TIPO_PAGO_OK = "SELECCIONA_CIRCULO_TIPO_PAGO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de una firma de registrador
     */
    public static final String ADICIONA_FIRMA_REGISTRADOR_OK = "ADICIONA_FIRMA_REGISTRADOR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de activación
     * satisfactoria de una firma de registrador
     */
    public static final String ACTIVA_FIRMA_REGISTRADOR_OK = "ACTIVA_FIRMA_REGISTRADOR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de inactivación
     * satisfactoria de una firma de registrador
     */
    public static final String INACTIVA_FIRMA_REGISTRADOR_OK = "INACTIVA_FIRMA_REGISTRADOR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de selección
     * satisfactoria de una firma de registrador por círculo
     */
    public static final String SELECCIONA_CIRCULO_FIRMA_REGISTRADOR_OK = "SELECCIONA_CIRCULO_FIRMA_REGISTRADOR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de buscar firmas de
     * registrador
     */
    public static final String BUSCAR_FIRMA_REGISTRADOR_CIRCULO = "BUSCAR_FIRMA_REGISTRADOR_CIRCULO";

    /**
     * Esta constante se utiliza para identificar el evento de buscar firmas de
     * registrador
     */
    public static final String GUARDAR_FIRMA_REGISTRADOR = "GUARDAR_FIRMA_REGISTRADOR";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de los abogados existentes en un circulo rol
     */
    public static final String CONSULTA_ABOGADOS_POR_CIRCULO_ROL_OK
            = "CONSULTA_ABOGADOS_POR_CIRCULO_ROL_OK";

    /**
     * Esta constante se utiliza para identificar el evento de actualización
     * satisfactoria de los subtipos de atención de un usuario
     */
    public static final String ACTUALIZA_SUBTIPOS_ATENCION_USUARIO_OK
            = "ACTUALIZA_SUBTIPOS_ATENCION_USUARIO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de una validación de nota
     */
    public static final String ADICIONA_VALIDACION_NOTA_OK = "ADICIONA_VALIDACION_NOTA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de una validación de nota
     */
    public static final String ELIMINA_VALIDACION_NOTA_OK = "ELIMINA_VALIDACION_NOTA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de las fases de un proceso
     */
    public static final String CONSULTA_FASE_PROCESO_OK = "CONSULTA_FASE_PROCESO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un causal de restitución
     */
    public static final String ADICIONA_CAUSAL_RESTITUCION_OK = "ADICIONA_CAUSAL_RESTITUCION_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un causal de restitución
     */
    public static final String ELIMINA_CAUSAL_RESTITUCION_OK = "ELIMINA_CAUSAL_RESTITUCION_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de una validación de tipo de certificado
     */
    public static final String ADICIONA_VALIDACION_TIPO_CERTIFICADO_OK = "ADICIONA_VALIDACION_TIPO_CERTIFICADO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de una validación de un tipo de certificado
     */
    public static final String ELIMINA_VALIDACION_TIPO_CERTIFICADO_OK = "ELIMINA_VALIDACION_TIPO_CERTIFICADO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de una validación de tipo de certificado
     */
    public static final String CONSULTA_VALIDACION_TIPO_CERTIFICADO_OK = "CONSULTA_VALIDACION_TIPO_CERTIFICADO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un tipo de nota
     */
    public static final String ADICIONA_TIPO_NOTA_OK = "ADICIONA_TIPO_NOTA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un tipo de nota
     */
    public static final String ELIMINA_TIPO_NOTA_OK = "ELIMINA_TIPO_NOTA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de tipos de nota por proceso
     */
    public static final String CONSULTA_TIPO_NOTA_POR_PROCESO_OK = "CONSULTA_TIPO_NOTA_POR_PROCESO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de tipos de nota por proceso
     */
    public static final String CONSULTA_TIPO_NOTA_INF_POR_PROCESO_OK = "CONSULTA_TIPO_NOTA_INF_POR_PROCESO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de tipos de nota por proceso
     */
    public static final String CONSULTA_TIPO_NOTA_DEV_POR_PROCESO_OK = "CONSULTA_TIPO_NOTA_DEV_POR_PROCESO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de creación
     * satisfactoria de un check items
     */
    public static final String ADICIONA_CHECK_ITEM_OK = "ADICIONA_CHECK_ITEM_OK";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación
     * satisfactoria de un check items
     */
    public static final String ELIMINA_CHECK_ITEM_OK = "ELIMINA_CHECK_ITEM_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de check items
     */
    public static final String CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD_OK = "CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria TURNOS EN FASE CER_ENTREGAR
     */
    public static final String CONSULTA_TURNOS_CER_ENTREGAR_OK = "CONSULTA_TURNOS_CER_ENTREGAR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria TURNOS EN FASE CON_ENTREGAR_SIMPLE y CON_ENTREGAR_COMPLEJA
     */
    public static final String CONSULTA_TURNOS_CON_ENTREGAR_OK = "CONSULTA_TURNOS_CON_ENTREGAR_OK";

    /**
     * Esta constante se utiliza para identificar el evento satisfactorio de
     * entrega masiva de certificados
     */
    public static final String ENTREGA_MASIVA_CERTIFICADOS_OK = "ENTREGA_MASIVA_CERTIFICADOS_OK";

    /**
     * Esta constante se utiliza para identificar el evento satisfactorio de
     * entrega masiva de certificados
     */
    public static final String ENTREGA_MASIVA_CONSULTAS_OK = "ENTREGA_MASIVA_CONSULTAS_OK";

    /**
     * Esta constante se utiliza para identificar el evento satisfactorio de
     * consulta de probabilidad de revision calificacion
     */
    public static final String CONSULTAR_PROB_REV_CALIFICACION_OK
            = "CONSULTAR_PROB_REV_CALIFICACION_OK";

    /**
     * Esta constante se utiliza para identificar el evento satisfactorio de
     * modificacion de probabilidad de revision calificacion
     */
    public static final String MODIFICAR_PROB_REV_CALIFICACION_OK
            = "MODIFICAR_PROB_REV_CALIFICACION_OK";

    /**
     * Constante que identifica que la acción de consultar liquidaciones no
     * pagadas retorna exitosa
     */
    public static final String CONSULTA_SOLICITUDES_NO_PAGADAS_OK
            = "CONSULTA_SOLICITUDES_NO_PAGADAS_OK";

    /**
     * Constante que identifica que la acción de consultar liquidaciones no
     * pagadas retorna exitosa
     */
    public static final String ELIMINAR_SOLICITUDES_NO_PAGADAS_OK
            = "ELIMINAR_SOLICITUDES_NO_PAGADAS_OK";

    /**
     * Constante que identifica que la acción de consultar liquidaciones no
     * pagadas retorna exitosa
     */
    public static final String CONSULTA_TURNOS_CALIFICACION_OK
            = "CONSULTA_TURNOS_CALIFICACION_OK";

    /**
     * Constante que identifica que la acción de solcitar correccion
     * calificacion retorna exitosa
     */
    public static final String SOLICITAR_CORRECCION_CALIFICACION_OK
            = "SOLICITAR_CORRECCION_OK";

    /**
     * Constante que identifica que se consultaron satisfactoriamente las
     * estaciones por círculo
     */
    public static final String CONSULTA_ESTACIONES_CIRCULO_OK
            = "CONSULTA_ESTACIONES_CIRCULO_OK";

    /**
     * Constante que identifica que se consultaron satisfactoriamente los tipos
     * de tarifa por círculo
     */
    public static final String CONSULTA_TIPOS_TARIFA_POR_CIRCULO_OK
            = "CONSULTA_TIPOS_TARIFA_POR_CIRCULO_OK";

    /**
     * Constante que identifica que se adicionó satisfactoriamente una tarifa
     * por circulo
     */
    public static final String ADICIONA_TARIFA_POR_CIRCULO_OK
            = "ADICIONA_TARIFA_POR_CIRCULO_OK";

    /**
     * Constante que identifica que se consultó satisfactoriamente la
     * información de una estación
     */
    public static final String ADMIN_UNA_ESTACION_OK = "ADMIN_UNA_ESTACION_OK";

    /**
     * Constante que identifica que cambiaron las naturalezas juridicas de
     * entidad
     */
    public static final String CAMBIO_NAT_JURIDICA_ENTIDAD = "CAMBIO_NAT_JURIDICA_ENTIDAD";

    /**
     * Constante que identifica que cambiaron las entidades publicas
     */
    public static final String CAMBIO_ENTIDAD_PUBLICA = "CAMBIO_ENTIDAD_PUBLICA";

    public static final String NAT_JURIDICAS_ENTIDAD = "NAT_JURIDICAS_ENTIDAD";

    /**
     * Constante que identifica que e obtuvo las impresoras del circulo
     */
    public static final String OBTENER_IMPRESORAS_CIRCULO = "OBTENER_IMPRESORAS_CIRCULO";

    /**
     * Constante que identifica que e obtuvo las impresoras del circulo
     */
    public static final String MOSTRAR_CIRCULO_ESTACION_RECIBO_OK = "MOSTRAR_CIRCULO_ESTACION_RECIBO_OK";

    /**
     * Constante que identifica que e obtuvo las impresoras del circulo
     */
    public static final String AVANZAR_TURNOS_PMY_VENCIDOS_OK = "AVANZAR_TURNOS_PMY_VENCIDOS_OK";

    public static final String ADICIONA_TIPO_VALIDACION_OK = "ADICIONA_TIPO_VALIDACION_OK";

    public static final String CONSULTAR_TURNOS_PMY_PENDIENTES_OK = "CONSULTAR_TURNOS_PMY_PENDIENTES_OK";

    public static final String AVANZAR_TURNOS_PMY_PENDIENTES_OK = "AVANZAR_TURNOS_PMY_PENDIENTES_OK";

    /**
     * Constante que identifica que se quiere obtener la lista de calificadores
     * del circulo actual
     */
    public static final String CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION_OK = "CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION_OK";

    /**
     * Constante que identifica que se quiere obtener la lista de calificadores
     * del circulo actual y del subtipo seleccionado
     */
    public static final String CARGAR_CALIFICADORES_SUBTIPOATENCION_OK = "CARGAR_CALIFICADORES_SUBTIPOATENCION_OK";
    public static final String CARGAR_CIRCULOS_NOTARIALES_OK = "CARGAR_CIRCULOS_NOTARIALES_OK";

    public static final String CARGAR_ZONAS_NOTARIALES_OK = "CARGAR_ZONAS_NOTARIALES_OK";

    public static final String CARGAR_ROLES_PANTALLAS_ADMINISTRATIVAS_OK = "CARGAR_ROLES_PANTALLAS_ADMINISTRATIVAS_OK";

    public static final String AGREGAR_ROL_PANTALLA_ADMINISTRATIVA_OK = "AGREGAR_ROL_PANTALLA_ADMINISTRATIVA_OK";

    public static final String ELIMINAR_ROL_PANTALLA_ADMINISTRATIVA_OK = "ELIMINAR_ROL_PANTALLA_ADMINISTRATIVA_OK";

    public static final String ADICIONA_BANCO_CIRCULO_OK = "ADICIONA_BANCO_CIRCULO_OK";

    public static final String ADICIONA_CUENTA_BANCARIA_OK = "ADICIONA_CUENTA_BANCARIA_OK";

    private List estaciones;

    private List fases;

    private List notas;

    private Estacion estacion;

    private EstacionRecibo estacionRecibo;

    private List potRoles;

    private List usuariosEstacion;

    private List usuariosPotenciales;

    private List natsJuridicasEntidad;

    private List entidadesPublicas;

    private ValidacionParametrosException vpe = null;

    private Circulo circulo;

    private List turnos;

    private List subtiposAtencion;

    private boolean hayExcepcion;

    private List procesosValidosRecibo;

    private Hashtable ht;

    private List rolesPantallas;

    private CuentasBancarias cuentasBancarias;

    /**
     * @param payload
     */
    public EvnRespAdministracionHermod(Object payload) {
        super(payload);
    }

    /**
     * @param payload
     * @param tipoEvento
     */
    public EvnRespAdministracionHermod(Object payload, String tipoEvento) {
        super(payload, tipoEvento);
    }

    /**
     * @return
     */
    public List getEstaciones() {
        return estaciones;
    }

    /**
     * @param list
     */
    public void setEstaciones(List list) {
        estaciones = list;
    }

    /**
     * @return
     */
    public List getFases() {
        return fases;
    }

    /**
     * @param list
     */
    public void setFases(List list) {
        fases = list;
    }

    /**
     * @return
     */
    public List getNotas() {
        return notas;
    }

    /**
     * @param list
     */
    public void setNotas(List list) {
        notas = list;
    }

    /**
     * @return
     */
    public Estacion getEstacion() {
        return estacion;
    }

    /**
     * @return
     */
    public EstacionRecibo getEstacionRecibo() {
        return estacionRecibo;
    }

    /**
     * @param estacion
     */
    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
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
    public List getRolesPotenciales() {
        return potRoles;
    }

    /**
     * @param list
     */
    public void setRolesPotenciales(List list) {
        potRoles = list;
    }

    /**
     * @return
     */
    public List getUsuariosPotenciales() {
        return usuariosPotenciales;
    }

    /**
     * @return
     */
    public List getUsuariosEstacion() {
        return usuariosEstacion;
    }

    /**
     * @param list
     */
    public void setUsuariosPotenciales(List list) {
        usuariosPotenciales = list;
    }

    /**
     * @param list
     */
    public void setUsuariosEstacion(List list) {
        usuariosEstacion = list;
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
     * @return
     */
    public List getNatsJuridicasEntidad() {
        return natsJuridicasEntidad;
    }

    /**
     * @param list
     */
    public void setNatsJuridicasEntidad(List list) {
        natsJuridicasEntidad = list;
    }

    /**
     * @return
     */
    public List getEntidadesPublicas() {
        return entidadesPublicas;
    }

    /**
     * @param list
     */
    public void setEntidadesPublicas(List list) {
        entidadesPublicas = list;
    }

    /**
     * @param cir
     */
    public void setCirculo(Circulo cir) {
        circulo = cir;

    }

    /**
     * @return
     */
    public Circulo getCirculo() {
        return circulo;
    }

    private long totalResultados;

    public long getTotalResultados() {
        return totalResultados;
    }

    public void setTotalResultados(long total) {
        totalResultados = total;
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

    public List getSubtiposAtencion() {
        return subtiposAtencion;
    }

    public void setSubtiposAtencion(List subtiposAtencion) {
        this.subtiposAtencion = subtiposAtencion;
    }

    public boolean isHayExcepcion() {
        return hayExcepcion;
    }

    public void setHayExcepcion(boolean hayExcepcion) {
        this.hayExcepcion = hayExcepcion;
    }

    public Hashtable getHt() {
        return ht;
    }

    public void setHt(Hashtable ht) {
        this.ht = ht;
    }

    public List getProcesosValidosRecibo() {
        return procesosValidosRecibo;
    }

    public void setProcesosValidosRecibo(List procesosValidosRecibo) {
        this.procesosValidosRecibo = procesosValidosRecibo;
    }

    public List getRolesPantallas() {
        return rolesPantallas;
    }

    public void setRolesPantallas(List rolesPantallas) {
        this.rolesPantallas = rolesPantallas;
    }

    private BancosXCirculo bancosXCirculo;

    public BancosXCirculo getBancosXCirculo() {
        return bancosXCirculo;
    }

    public void setBancosXCirculo(BancosXCirculo bancosXCirculo) {
        this.bancosXCirculo = bancosXCirculo;
    }

    public CuentasBancarias getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(CuentasBancarias cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }

}
