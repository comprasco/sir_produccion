package gov.sir.hermod;

import java.util.Hashtable;

/**
 * @author dlopez, mrios, mortiz Clase para el manejo de los diferentes tipos de
 * excepciones lanzadas durante la utilizaciï¿½n de los servicios de hermod.
 */
public class HermodException extends java.lang.Exception {

    private Hashtable hashErrores = new Hashtable();

    /**
     * *****************************************************************
     */

    /*          Excepciones lanzadas en mï¿½todos analizadores.           */
    /**
     * *****************************************************************
     */
    
    /**
     * Generada cuando no es posible asignar el estatus de un turno REL
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String STATUS_NO_ALMACENADO = "No fue posible alamacenar el status del turno REL actual";
    
    /**
     * Generada cuando no es posible obtener la lista de imprimibles pendientes.
     *
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public static String LISTA_IMPRIMIBLESPENDIENTES_NO_OBTENIDA = "No fue posible obtener la lista de Turnos Pendientes";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de procesos.
     *
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public static String LISTA_PROCESOS_NO_OBTENIDA = "No fue posible obtener la lista de Procesos";
    
    /**
     * Excepción generada cuando no es posible adicionar la lista de notas informativas.
     *
     * @see gov.sir.core.negocio.modelo.Nota
     */
    public static String LISTA_NOTAS_INFORMATIVAS_NO_AGREGADAS = "No fue posible agregar la lista de notas informativas";
    
     /**
     * Excepción generada cuando no es posible adicionar un registro al control de matriculas.
     *
     * @see gov.sir.core.negocio.modelo.ControlMatricula
     */
    public static String CONTROL_MATRICULA_NO_AGREGADO = "No fue posible agregar al control de matriculas";
    
     /**
     * Excepción generada cuando no es posible adicionar un registro al control de reasignación
     *
     * @see gov.sir.core.negocio.modelo.ControlReasignacion
     */
    public static String CONTROL_REASIGNACION_NO_AGREGADO = "No fue posible agregar al control de reasignacion";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de procesos que
     * inician.
     *
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public static String LISTA_PROCESOS_QUE_INICIA_NO_OBTENIDA = "No fue posible obtener la lista de Procesos que inicia";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de fases.
     *
     * @see gov.sir.core.negocio.modelo.Fase
     */
    public static String LISTA_FASES_NO_OBTENIDA = "No fue posible obtener la lista de Fases";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de fases
     * siguientes.
     *
     * @see gov.sir.core.negocio.modelo.Fase
     */
    public static String LISTA_FASES_SIGUIENTES_NO_OBTENIDA = "No fue posible obtener la lista de Fases siguientes";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de respuestas
     * siguientes.
     */
    public static String LISTA_RTAS_SIGUIENTES_NO_OBTENIDA = "No fue posible obtener la lista de Respuestas siguientes";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de turnos.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String LISTA_TURNOS_NO_OBTENIDA = "No fue posible obtener la lista de Turnos";
    /**
     * Excepciï¿½n generada cuando no es posible crear un regsitro en reproduccion sellos
     *
     * @see gov.sir.core.negocio.modelo.ReproduccionSellos
     */
    public static String REPRODUCCIONNOCREADA = "No fue posible crear el registro de reproduccion de sellos";
    /**
     * Excepciï¿½n generada cuando no es posible cambiar la estaciï¿½n de un turno.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String TURNOS_NO_REASIGNADOS = "No fue posible reasignar los turnos seleccionados";

    /**
     * Excepciï¿½n generada cuando no es posible la informacion en un turno
     * ejecucion dado.
     *
     * @see gov.sir.core.negocio.modelo.TurnoEjecucion
     */
    public static String TURNO_EJECUCION_NO_ACTUALIZADO = "No fue posible restituir el turno";

    /**
     * Excepciï¿½n generada cuando no es posible ejecutar la reanotaciï¿½n de un
     * turno en las tablas del modelo operativo.
     *
     * @see gov.sir.core.negocio.modelo.TurnoEjecucion
     */
    public static String REANOTACION_NO_EJECUTADA_MODELO_OPERATIVO = "No fue posible restituir el turno en el modelo operativo de SIR";

    /**
     * Excepciï¿½n generada cuando no es posible actualizar el turno historia.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String TURNOS_HISTORIA_NO_ACTUALIZADA = "No fue posible actualizar el turno usuario para la fase";

    /**
     * Excepciï¿½n generada cuando no es posible obtener un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String TURNO_NO_OBTENIDO = "No fue posible obtener el Turno";

    /**
     * Excepciï¿½n generada cuando no es posible obtener un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String DATOS_PAGO_NO_OBTENIDOS = "No fue posible obtener los datos asociados al pago para el turno";

    /**
     * Excepciï¿½n generada cuando no es posible obtener un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String CREACION_DOCUMENTO_PAGO_NO_EFECTUADA = "No fue posible crear el documento de pago";

    /**
     * Excepciï¿½n generada cuando no es posible obtener un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String DATOS_CONSIGNACION_NO_OBTENIDOS = "No fue posible obtener los datos de la consignacion o cheque";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de Bancos.
     *
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public static String LISTA_BANCOS_NO_OBTENIDA = "No fue posible obtener la lista de Bancos";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el rango de aceptaciï¿½n.
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String RANGO_ACEPTACION_NO_OBTENIDO = "No fue posible obtener el rango de aceptacion";
    
    /**
     * Excepcion generada cuando no es posible obtener la lista de tipos de documento para personas juridicas.
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String TIPOIDJURDICA_NO_OBTENIDA = "No fue posible obtener la lista con los tipos de identificacion para personas juridicas";
    
    /**
     * Excepcion generada cuando no es posible obtener la lista de tipos de documento para personas naturales.
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String TIPOSIDNATURAL_NO_OBTENIDA = "No fue posible obtener la lista con los tipos de identificacion para personas naturales";
    
    /**
     * Excepcion generada cuando no es posible obtener la lista de modalidades
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String MODALIDAD_NO_OBTENIDA = "No fue posible obtener la lista con las modalidades";
    
    /**
     * Excepcion generada cuando no es posible obtener la lista de modalidades
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String DETERMINACION_INMUEBLE_NO_OBTENIDA = "No fue posible obtener la lista con las determinaciones del inmueble";
    
   /**
     * Excepcion generada cuando no es posible obtener la lista de modalidades
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String TIPO_PERSONA_NO_OBTENIDO = "No fue posible obtener la lista con tipos de persona";
    
    /**
     * Excepcion generada cuando no es posible obtener la lista de modalidades
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String TIPO_SEXO_NO_OBTENIDO = "No fue posible obtener la lista con los tipos de sexo";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de Lookup Codes.
     *
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String LISTA_LOOKUP_CODES_NO_OBTENIDA = "No fue posible obtener la lista de OPLookupCodes";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de Lookup Types.
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     */
    public static String LISTA_LOOKUP_TYPES_NO_OBTENIDA = "No fue posible obtener la lista de LookUpTypes";
    
    /**
     * Excepción generada cuando no es posible eliminar los actos.
     */
    public static String ACTOS_NO_ELIMINADOS = "No fue posible eliminar los actos";
    
    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * consulta.
     *
     * @see gov.sir.core.negocio.modelo.TipoConsulta
     */
    public static String LISTA_TIPOS_CONSULTA_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Consultas";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * alcance geogrï¿½fico.
     *
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     */
    public static String LISTA_ALCANCES_GEOGRAFICOS_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Alcances Geograficos";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * fotocopias.
     *
     * @see gov.sir.core.negocio.modelo.TipoFotocopia
     */
    public static String LISTA_TIPOS_FOTOCOPIA_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Fotocopia";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * recepciï¿½n peticiï¿½n
     *
     * @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
     */
    public static String LISTA_TIPOS_RECEPCION_PETICION_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Recepciï¿½n Peticiï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de subtipos de
     * atenciï¿½n.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public static String LISTA_SUB_TIPOS_ATENCION_NO_OBTENIDA = "No fue posible obtener la lista de Subtipos de atenciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de subtipos de
     * solicitud.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     */
    public static String LISTA_SUB_TIPOS_SOLICITUD_NO_OBTENIDA = "No fue posible obtener la lista de Subtipos de Solicitud";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * actos.
     *
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public static String LISTA_TIPOS_ACTO_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Actos";

    /**
     * Excepciï¿½n generada cuando no es posible obtener un tipo de acto.
     *
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public static String TIPO_ACTO_NO_OBTENIDO = "No fue posible obtener el Tipo de Acto";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * impuestos.
     *
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     */
    public static String LISTA_TIPOS_IMPUESTOS_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Impuestos";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * Accion Notarial
     *
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     */
    public static String LISTA_TIPOS_ACCION_NOTARIAL_NO_OBTENIDA = "No fue posible obtener la lista de tipos de Accion Notarial";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de causales de
     * restituciï¿½n.
     *
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    public static String LISTA_CAUSALES_RESTITUCION_NO_OBTENIDA = "No fue posible obtener la lista de Causales de Restituciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar un causal de
     * restituciï¿½n.
     *
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    public static String ELIMINACION_CAUSALES_RESTITUCION_NO_EFECTUADA = "No fue posible eliminar el Causale de Restituciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * cï¿½lculo.
     *
     * @see gov.sir.core.negocio.modelo.TipoCalculo
     */
    public static String LISTA_TIPOS_CALCULO_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Cï¿½lculo";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * derechos registrales.
     *
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     */
    public static String LISTA_TARIFA_DERECHO_REGISTRAL_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Derechos Registrales";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * tarifas.
     *
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public static String LISTA_TIPOS_TARIFA_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Tarifas";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tarifas.
     *
     * @see gov.sir.core.negocio.modelo.Tarifa
     */
    public static String LISTA_TARIFAS_NO_OBTENIDA = "No fue posible obtener la lista de Tarifas";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * certificados.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     */
    public static String LISTA_TIPOS_CERTIFICADO_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Certificados";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * documentos.
     *
     * @see gov.sir.core.negocio.modelo.TipoDocumento
     */
    public static String LISTA_TIPOS_DOCUMENTO_NO_OBTENIDA = "No fue posible obtener la lista de tipos de documentos";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de subtipos de
     * atenciï¿½n.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public static String LISTA_SUBTIPOS_ATENCION_USUARIO_NO_OBTENIDA = "No fue posible obtener la lista de los subtipos de atenciï¿½n de los usuarios";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de Categorï¿½as de
     * Reparto.
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String LISTA_CATEGORIAS_REPARTO_NO_OBTENIDA = "No fue posible obtener la lista de categorias de reparto";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el reparto de un
     * <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Reparto
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String REPARTO_NO_OBTENIDO = "No fue posible obtener el reparto del turno";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el reparto de un
     * <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Reparto
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String IMPRESION_REPARTO_NO_OBTENIDO = "No fue posible realizar la reimpresiï¿½n del Acta";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el reparto de un
     * <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Reparto
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String IMPRESION_REPARTO_C_NO_OBTENIDO = "No fue posible realizar la reimpresiï¿½n de la Carï¿½tula";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de sucursales
     * de un <code>Banco</code>.
     *
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public static String SUCURSALES_NO_OBTENIDAS = "No fue posible obtener el listado de sucursales";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de minutas no
     * asignadas.
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public static String LISTA_MINUTAS_NO_ASIGNADAS_NO_OBTENIDA = "No fue posible obtener la lista de minutas disponibles para realizar reparto";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el nï¿½mero mï¿½ximo de
     * impresiones permitido para un usuario.
     */
    public static String NUMERO_MAXIMO_IMPRESIONES_NO_OBTENDIO = "No fue posible obtener el nï¿½mero mï¿½ximo de impresiones permitido";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de tipos de
     * notas.
     *
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public static String LISTA_TIPOS_NOTA = "No fue posible obtener la lista de Tipos de Nota";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de minutas no
     * asignadas en una <code>Vereda</code>.
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Vereda
     */
    public static String LISTA_MINUTAS_BY_VEREDA_NO_OBTENIDA = "No fue posible obtener la lista de minutas segï¿½n vereda";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la Zona Notarial asociada
     * con una <code>Vereda</code>.
     *
     * @see gov.sir.core.negocio.modelo.ZonaNotarial
     * @see gov.sir.core.negocio.modelo.Vereda
     */
    public static String ZONA_NOTARIAL_VEREDA_NO_OBTENIDA = "No fue posible obtener la Zona Notarial de la Vereda";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la <code>Minuta</code>
     * asociada con un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String MINUTA_ASOCIADA_TURNO_NO_OBTENIDA = "No fue posible obtener la minuta asociada al turno dado";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la
     * <code>Liquidaciï¿½n</code> asociada con un <code>Acto</code>.
     *
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.Acto
     */
    public static String LIQUIDACION_ACTO_NO_OBTENIDA = "No fue posible obtener la liquidaciï¿½n del acto dado";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la asociacion entre una
     * estaciï¿½n y un recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_OBTENIDO = "No fue posible obtener la estaciï¿½n recibo";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de validacion
     * de notas.
     *
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     */
    public static String LISTA_VALIDACION_NOTA = "No fue posible obtener la lista de Validaciones de Nota";

    /**
     * Excepciï¿½n generada cuando no es posible ACTUALIZAR los subtipos de
     * atenciï¿½n de un usuario.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public static String ERROR_ACTUALIZACION_SUBTIPOS_ATENCION_USUARIO = "No fue posible actualizar los subtipos de atenciï¿½n del Usuario.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado los procesos
     * existentes en el sistema
     *
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public static String CONSULTA_LISTA_PROCESOS = "No fue posible consultar la lista de procesos existentes en el sitema.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado las fases
     * relacionadas con un proceso especï¿½fico
     *
     * @see gov.sir.core.negocio.modelo.FaseProceso
     */
    public static String CONSULTA_FASES_PROCESO = "No fue posible consultar la lista de las fases del proceso.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener una <code>Fase</code>.
     *
     * @see gov.sir.core.negocio.modelo.Fase
     */
    public static String FASE_NO_OBTENIDA = "No fue posible obtener la Fase";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de turnos
     * anteriores asociados con un turno.
     */
    public static String TURNOS_ANTERIORES_NO_OBTENIDOS = "No fue posible obtener listado de turnos anteriores";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de turnos
     * siguientes asociados con un turno.
     */
    public static String TURNOS_SIGUIENTES_NO_OBTENIDOS = "No fue posible obtener listado de turnos siguientes";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el tipo de una fase.
     */
    public static String TIPO_FASE_NO_OBTENIDO = "No fue posible obtener el tipo de una fase";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la solicitud y sus
     * liquidaciones asociadas con el identificador dado.
     */
    public static String SOLICITUD_BY_ID_NO_OBTENIDA = "No fue posible obtener la solicitud con el id dado";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de turnos en un
     * rango dado, que han pasado por una fase dada.
     */
    public static String RANGO_TURNOS_BY_FASE_NO_OBTENIDO = "No fue posible obtener la lista de turnos en el rango y fase dadas";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el nï¿½mero de intentos
     * permitidos en un proceso de impresiï¿½n.
     */
    public static String NUMERO_INTENTOS_IMPRESION_NO_OBTENIDO = "No fue posible obtener el nï¿½mero permitido de intentos de impresiï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el tiempo de espera en un
     * proceso de impresiï¿½n.
     */
    public static String TIEMPO_ESPERA_IMPRESION_NO_OBTENIDO = "No fue posible obtener el tiempo de espera en el proceso de impresiï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el estado de pago de una
     * solicitud.
     */
    public static String ESTADO_PAGO_SOLICITUD_NO_OBTENIDO = "No fue posible obtener el estado de pago de una solcitud.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de turnos
     * calificados por un usuario.
     */
    public static String TURNOS_CALIFICADOS_USUARIO_NO_OBTENIDOS = "No fue posible obtener el listado de turnos calificados por un usuario";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de turnos
     * validos para agregar certificados asociados.
     */
    public static String TURNOS_VALIDOS_AGREGAR_CERTIFICADOS_ASOCIADOS_NO_OBTENIDOS = "No fue posible obtener el listado de turnos validos para agregar certificados asociados";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el proceso dado el id de
     * una fase.
     */
    public static String PROCESO_FASE_NO_ENCONTRADO = "No fue posible obtener el proceso dada una fase";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el cï¿½rculo de proceso con
     * el id suministrado
     */
    public static String CIRCULO_PROCESO_NO_OBTENIDO = "No fue posible obtener el cï¿½rculo de proceso con el identificador dado";

    /**
     * *****************************************************************
     */

    /*          Excepciones lanzadas en mï¿½todos modificadores           */
    /**
     * *****************************************************************
     */
    /**
     * Excepciï¿½n generada cuando no es posible crear un causal de restituciï¿½n.
     *
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    public static String CAUSAL_RESTITUCION_NO_CREADO = "No fue posible crear el Causal de Restituciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible modificar los datos de una
     * liquidaciï¿½n de registro de documentos.
     */
    public static String LIQUIDACION_REGISTRO_NO_MODIFICADA = "No fue posible modificar la liquidaciï¿½n de registro.";

    /**
     * Excepciï¿½n generada cuando no es posible crear una acciï¿½n notarial.
     *
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     */
    public static String ACCION_NOTARIAL_NO_CREADA = "No fue posible crear la acciï¿½n notarial";

    /**
     * Excepciï¿½n generada cuando no es posible crear un tipo de Alcance
     * Geogrï¿½fico.
     *
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     */
    public static String ALCANCE_GEOGRAFICO_NO_CREADO = "No fue posible crear el tipo de Alcance Geografico";

    /**
     * Excepciï¿½n generada cuando no es posible crear un tipo de Acto.
     *
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public static String TIPO_ACTO_NO_CREADO = "No fue posible crear el tipo de acto";

    /**
     * Excepciï¿½n generada cuando no es posible crear un tipo de Cï¿½lculo.
     *
     * @see gov.sir.core.negocio.modelo.TipoCalculo
     */
    public static String TIPO_CALCULO_NO_CREADO = "No fue posible crear el tipo de cï¿½lculo";

    /**
     * Excepciï¿½n generada cuando no es posible crear un tipo de Impuesto
     *
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     */
    public static String TIPO_IMPUESTO_NO_CREADO = "No fue posible crear el tipo de impuesto";

    /**
     * Excepciï¿½n generada cuando no es posible crear un tipo de tarifa de
     * derecho registral
     *
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     */
    public static String TARIFA_DERECHO_REGISTRAL_NO_CREADA = "No fue posible crear el tipo de derecho registral";

    /**
     * Excepciï¿½n generada cuando no es posible crear un <code>Banco</code>.
     *
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public static String BANCO_NO_CREADO = "No fue posible crear el Banco";

    /**
     * Excepciï¿½n generada cuando no es posible crear un tipo de fotocopia
     *
     * @see gov.sir.core.negocio.modelo.TipoFotocopia
     */
    public static String TIPO_FOTOCOPIA_NO_CREADO = "No fue posible crear el tipo de fotocopia";

    /**
     * Excepciï¿½n generada cuando no es posible crear un LookupCode
     *
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String LOOK_UP_CODE_NO_CREADO = "No fue posible crear el Lookup Code";

    /**
     * Excepciï¿½n generada cuando no es posible crear un LookupType
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     */
    public static String LOOK_UP_TYPE_NO_CREADO = "No fue posible crear el Lookup Type";

    /**
     * Excepciï¿½n generada cuando no es posible crear un Subtipo de Atenciï¿½n.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public static String SUBTIPO_ATENCION_NO_CREADO = "No fue posible crear el subtipo de atenciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible crear un Subtipo de Solicitud
     *
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     */
    public static String SUBTIPO_SOLICITUD_NO_CREADO = "No fue posible crear el subtipo de solicitud";

    /**
     * Excepciï¿½n generada cuando no es posible crear una <code>Tarifa</code>
     *
     * @see gov.sir.core.negocio.modelo.Tarifa
     */
    public static String TARIFA_NO_CREADA = "No fue posible crear la tarifa";

    /**
     * Excepciï¿½n generada cuando no es posible crear un tipo de Certificado.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     */
    public static String TIPO_CERTIFICADO_NO_CREADO = "No fue posible crear el tipo de certificado";

    /**
     * Excepciï¿½n generada cuando no es posible crear un Tipo de Consulta
     *
     * @see gov.sir.core.negocio.modelo.TipoConsulta
     */
    public static String TIPO_CONSULTA_NO_CREADO = "No fue posible crear el tipo de consulta";

    /**
     * Excepciï¿½n generada cuando no es posible crear un Tipo de Tarifa.
     *
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public static String TIPO_TARIFA_NO_CREADO = "No fue posible crear el tipo de tarifa";

    /**
     * Excepciï¿½n generada cuando no es posible crear un Tipo de Recepciï¿½n
     * Peticiï¿½n.
     *
     * @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
     */
    public static String TIPO_RECEPCION_PETICION_NO_CREADO = "No fue posible crear el tipo de Recepciï¿½n de Peticiï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible crear un Tipo de Documento.
     *
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public static String TIPO_DOCUMENTO_NO_CREADO = "No fue posible crear el tipo de documento";

    /**
     * Excepciï¿½n generada cuando no es posible crear un Tipo de Nota.
     *
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public static String NOTA_NO_CREADO = "No fue posible crear el Tipo de Nota";

    /**
     * Excepciï¿½n generada cuando no es posible agregar un testamento a una
     * solicitud de registro.
     *
     * @see gov.sir.core.negocio.modelo.Testamento
     */
    public static String TESTAMENTO_NO_CREADO = "No fue posible registrar el testamento";

    /**
     * Excepciï¿½n generada cuando no es posible crear un proceso de reparto.
     *
     * @see gov.sir.core.negocio.modelo.ProcesoReparto
     */
    public static String PROCESO_REPARTO_NO_CREADO = "No fue posible crear proceso de reparto";

    /**
     * Excepciï¿½n generada cuando no es posible crear una
     * <code>SucursalBanco</code>
     *
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     */
    public static String SUCURSAL_NO_CREADA = "No fue posible crear la sucursal";

    /**
     * Excepciï¿½n generada cuando no es posible crear una Categorï¿½a de Reparto
     * Notarial.
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String CATEGORIA_REPARTO_NO_CREADA = "No fue posible crear la categoria de reparto";

    /**
     * Excepciï¿½n generada cuando no es posible crear un <code>Folio</code> a un
     * <code>Turno</code>
     *
     * @see gov.sir.core.negocio.modelo.Folio
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String FOLIO_NO_AGREGADO = "No fue posible agregar el folio al turno";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar un<code>Folio</code> de
     * un <code>Turno</code>
     *
     * @see gov.sir.core.negocio.modelo.Folio
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String FOLIO_NO_ELIMINADO = "No fue posible eliminar el folio del turno";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar un<code>Folio</code> de
     * un <code>Turno</code>
     *
     * @see gov.sir.core.negocio.modelo.Folio
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String FOLIO_NO_CAMBIADO = "No fue posible cambiar el Folio";

    /**
     * Excepciï¿½n generada cuando no es posible agregar una <code>Minuta</code> a
     * un <code>Turno
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     */
    public static String MINUTA_NO_AGREGADA = "No fue posible agregar la minuta a la solicitud de reparto notarial";

    /**
     * Excepciï¿½n generada cuando no es posible asignar un
     * <code>RepartoNotarial</code> a una <code>Minuta</code>
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     */
    public static String REPARTO_NO_ASOCIADO = "No fue posible asignar el reparto notarial a la minuta";

    /**
     * Excepciï¿½n generada cuando no es posible anular una <code>Minuta </code>
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public static String MINUTA_NO_ANULADA = "No fue posible anular la minuta";

    /**
     * Excepciï¿½n generada cuando no es posible modificar una <code>Minuta
     * </code>
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public static String MINUTA_NO_MODIFICADA = "No fue posible modificar la minuta";

    /**
     * Excepciï¿½n generada cuando no es posible crear una <code>Categoria </code>
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String CATEGORIA_NO_CREADA = "No fue posible crear la Categorï¿½a de Reparto Notarial";

    /**
     * Excepciï¿½n generada cuando no es posible modificar una <code>Categoria
     * </code>
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String CATEGORIA_NO_MODIFICADA = "No fue posible modificar la categorï¿½a";

    /**
     * Excepciï¿½n generada cuando no es posible asociar una
     * <code>OficinaOrigen</code> con una <code>Categoria</code>
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.OficinaOrigen
     */
    public static String OFICINA_NO_ASOCIADA_CATEGORIA = "No fue posible asociar la Oficina Origen con la Categorï¿½a";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar una <code>OficinaOrigen
     * </code>de una <code>Categoria</code>
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.OficinaOrigen
     */
    public static String OFICINA_NO_ELIMINADA_CATEGORIA = "No fue posible eliminar la Oficina Origen de la Categorï¿½a";

    /**
     * Excepciï¿½n generada cuando no es posible crear la asociaciï¿½n Estaciï¿½n
     * Recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_CREADO = "No fue posible crear la estaciï¿½n recibo";

    /**
     * Excepciï¿½n generada cuando no es posible resetear la asociaciï¿½n Estaciï¿½n
     * Recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_RESET = "No fue posible resetear la estaciï¿½n recibo";

    /**
     * Excepciï¿½n generada cuando no es posible resetear la asociaciï¿½n Estaciï¿½n
     * Recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_RESET_ULT_NUM = "No fue posible resetear el ï¿½ltimo nï¿½mero de la estaciï¿½n recibo";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el numero consecutivo de
     * un recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_NEXT = "No fue posible obtener el numero de recibo";

    /**
     * Excepciï¿½n generada cuando no es posible clasificar una minuta dentro de
     * una categorï¿½a.
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String CLASIFICACION_MINUTA_NO_REALIZADA = "No fue posible clasificar la minuta dentro de una categorï¿½a";

    /**
     * Excepciï¿½n generada cuando no es posible asociar un <code>Ciudadano
     * </code> a una solicitud de consulta.
     *
     * @see gov.sir.core.negocio.modelo.Ciudadano
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     */
    public static String CIUDADANO_NO_ASOCIADO_A_SOLICITUD_CONSULTA = "No fue posible asociar el ciudadano a la solicitud de consulta";

    /**
     * Excepciï¿½n generada cuando no es posible realizar shutdown de la conexion
     * en un servicio
     */
    public static String SHUTDOWN_NO_REALIZADO = "Fallo en el shutdown del servicio ";

    /**
     * Excepciï¿½n generada cuando no es posible asociar un
     *  <code>numero de anotaciones</code> con una <code>Solitud</code>
     *
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public static String NUMERO_ANOTACIONES_NO_ASOCIADO = "No fue posible asociar el numero de anotaciones a la solicitud";

    /**
     * Excepciï¿½n generada cuando no es posible realizar el reparto de las
     * minutas de un Cï¿½rculo en particular.
     */
    public static String REPARTO_CIRCULO_NO_REALIZADO = "No fue posible realizar el Reparto Notarial del Cï¿½rculo";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de validaciones.
     */
    public static String LISTA_DE_VALIDACIONES_NO_OBTENIDA = "No fue posible obtener la lista de validaciones";

    /**
     * Excepciï¿½n generada cuando no es posible crear un nuevo turno manual
     */
    public static String TURNO_MANUAL_NO_CREADO = "No fue posible crear el turno con la informaciï¿½n suministrada";

    /**
     * Excepciï¿½n generada cuando no es posible avanzar un turno normal
     */
    public static String TURNO_NORMAL_NO_AVANZADO = "No fue posible avanzar un turno normal";

    /**
     * Excepciï¿½n generada cuando no es posible avanzar un turno pop
     */
    public static String TURNO_POP_NO_AVANZADO = "No fue posible avanzar un turno pop";

    /**
     * Excepciï¿½n generada cuando no es posible avanzar un turno push
     */
    public static String TURNO_PUSH_NO_AVANZADO = "No fue posible avanzar un turno Push";

    /**
     * Excepciï¿½n generada cuando no es posible avanzar un turno entrega.
     */
    public static String TURNO_ENTREGA_NO_AVANZADO = "No fue posible avanzar un turno Entrega";

    /**
     * Excepciï¿½n generada cuando no es posible avanzar un turno en modo
     * AvanzarCualquiera
     */
    public static String TURNO_CUALQUIERA_NO_AVANZADO = "No fue posible avanzar un turno en modo cualquiera";

    /**
     * Excepciï¿½n generada cuando no es posible avanzar un turno en modo
     * AvanzarEliminandoPush
     */
    public static String TURNO_ELIMINAR_PUSH_NO_AVANZADO = "No fue posible avanzar un turno en modo eliminarPush";

    /**
     * Excepciï¿½n generada cuando no es posible avanzar un turno en modo
     * AvanzarUsuario
     */
    public static String TURNO_USUARIO_NO_AVANZADO = "No fue posible avanzar un turno en modo avanzarUsuario";

    /**
     * Excepciï¿½n generada cuando no se puede consultar la lista de tipos de pago
     * del sistema
     */
    public static String LISTA_TIPOS_PAGO_NO_OBTENIDA = "No se pudo obtener la lista de tipos de pago del sistema";

    /**
     * Excepciï¿½n generada cuando no se puede consultar la lista de tipos de pago
     * por cï¿½rculo
     */
    public static String LISTA_CIRCULO_TIPOS_PAGO_NO_OBTENIDA = "No se pudo obtener la lista de tipos de pago por cï¿½rculo";
    
    /**
     * Excepciï¿½n generada cuando no se puede consultar la lista de tipos de pago
     * por cï¿½rculo
     */
    public static String LISTA_CAMPOS_CAPTURA_X_FORMA_PAGO = "No se pudo obtener la lista de campos de captura para la forma de pago recibida";

    /**
     * Excepciï¿½n generada cuando no se puede adicionar un Tipo de Pago a un
     * Cï¿½rculo
     */
    public static String CIRCULO_TIPO_PAGO_NO_ADICIONADO = "No se pudo adicionar el Tipo de Pago al Cï¿½rculo";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Pago de un
     * Cï¿½rculo
     */
    public static String CIRCULO_TIPO_PAGO_NO_ELIMINADO = "No se pudo eliminar el Tipo de Pago del Cï¿½rculo";

    /**
     * Excepciï¿½n generada cuando no se puede realizar el proceso de restituciï¿½n
     * de reparto notarial.
     */
    public static String RESTITUCION_REPARTO_NO_REALIZADA = "No se pudo realizar el proceso de Restituciï¿½n de Reparto Notarial ";

    /**
     * Excepciï¿½n generada cuando no se puede asociar el nï¿½mero mï¿½ximo de
     * bï¿½squedas a la solicitud de consulta.
     */
    public static String NUM_MAXIMO_BUSQUEDAS_NO_ASIGNADO = "No se pudo asingnar el nï¿½mero mï¿½ximo de bï¿½squedas a la Solicitud Consulta ";

    /**
     * Excepciï¿½n generada cuando no se puede asociar una resoluciï¿½n a la
     * solicitud de reparto notarial.
     */
    public static String RESOLUCION_NO_ASOCIADA_SOLICITUD_RESTITUCION = "No se pudo asociar la Resoluciï¿½n a la Solicitud de Restitucion";

    /**
     * Excepciï¿½n generada cuando no se puede marcar como rechazada una solicitud
     * de reparto notarial.
     */
    public static String SOLICITUD_RESTITUCION_NO_RECHAZADA = "No se pudo marcar como rechazada la Solicitud de Restitucion";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar una Categorï¿½a
     */
    public static String CATEGORIA_NO_ELIMINADA = "No se pudo eliminar la Categoria dada";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Alcance
     * Geogrï¿½fico.
     */
    public static String ALCANCE_GEOGRAFICO_NO_ELIMINADO = "No se pudo eliminar el Alcance Geogrï¿½fico";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Fotocopia.
     */
    public static String TIPO_FOTOCOPIA_NO_ELIMINADO = "No se pudo eliminar el Tipo de Fotocopia";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Cï¿½lculo
     */
    public static String TIPO_CALCULO_NO_ELIMINADO = "No se pudo eliminar el Tipo de Cï¿½lculo";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Derecho
     * Registral
     */
    public static String TIPO_DERECHO_REGISTRAL_NO_ELIMINADO = "No se pudo eliminar el Tipo de Derecho Registral";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Acciï¿½n Notarial
     */
    public static String TIPO_ACCION_NOTARIAL_NO_ELIMINADO = "No se pudo eliminar el Tipo de Acciï¿½n Notarial";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Acciï¿½n Notarial
     */
    public static String RANGO_FOLIO_NO_ASOCIADO_SOLICITUD = "No se pudo asociar el rango de folios a la solicitud";

    /**
     * Excepciï¿½n generada cuando no es posible modificar el encabezado de un
     * diocumento asociado a una <code>SolicitudRegistro </code>
     *
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public static String ENCABEZADO_NO_MODIFICADO = "No fue posible modificar el encabezado de la solicitud enviada";

    /**
     * Excepciï¿½n generada cuando no es posible modificar el encabezado de un
     * diocumento asociado a una <code>SolicitudRegistro </code>
     *
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public static String CONSULTA_ESTACIONES_RECIBO_CIRCULO_NO_EFECTUADA = "No fue posible consultar las estaciones recibo del cï¿½rculo";

    /**
     * Excepciï¿½n generada cuando un <code>Turno</code> no tiene nota devolutiva
     * y deberia tenerla
     *
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public static String TURNO_SIN_NOTA = "El turno no tiene una nota asociada y es necesaria para la fase ingresada";

    /**
     * Excepciï¿½n generada cuando un <code>Turno</code> no tiene el tipo de nota
     * devolutiva que es requerido
     *
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public static String TURNO_SIN_TIPO_NOTA = "El turno no tiene una nota asociada del tipo requerido y es necesaria para la fase ingresada";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de los tipos de
     * certificados individuales.
     *
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.Acto
     */
    public static String LISTA_CERTIFICADOS_INDIVIDUALES_NO_OBTENIDA = "No fue posible obtener la lista de tipos de certificados individuales";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de las entidades
     * pï¿½blicas que intervienen en el proceso de reparto notarial.
     */
    public static String LISTA_ENTIDADES_PUBLICAS_REPARTO_NO_OBTENIDA = "No fue posible obtener la lista de entidades pï¿½blicas que intervienen en el proceso de reparto";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Banco
     */
    public static String BANCO_NO_ELIMINADO = "No se pudo eliminar el Banco dado";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar una Sucursal Banco
     */
    public static String SUCURSAL_BANCO_NO_ELIMINADA = "No se pudo eliminar la Sucursal Banco dada";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar una Tarifa
     */
    public static String TARIFA_NO_ELIMINADA = "No se pudo eliminar la Tarifa dada";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Subtipo de Solicitud
     */
    public static String SUBTIPO_SOLICITUD_NO_ELIMINADO = "No se pudo eliminar el Subtipo de Solicitud dado";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Tarifa
     */
    public static String TIPO_TARIFA_NO_ELIMINADO = "No se pudo eliminar el Tipo de Tarifa dado";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Lookup Type
     */
    public static String LOOKUP_TYPE_NO_ELIMINADO = "No se pudo eliminar el Lookup Type dado";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Lookup Code
     */
    public static String LOOKUP_CODE_NO_ELIMINADO = "No se pudo eliminar el Lookup Code dado";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Impuesto
     */
    public static String TIPO_IMPUESTO_NO_ELIMINADO = "No se pudo eliminar el Tipo de Impuesto dado";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Subtipo de Atenciï¿½n
     */
    public static String SUBTIPO_ATENCION_NO_ELIMINADO = "No se pudo eliminar el Subtipo de Atencion dado";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar un Tipo de Acto
     */
    public static String TIPO_ACTO_NO_ELIMINADO = "No se pudo eliminar el Tipo de Acto dado";

    /**
     * Excepciï¿½n generada cuando no se puede eliminar una EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_ELIMINADA = "No se pudo eliminar la Estaciï¿½n / Recibo";

    /**
     * Excepciï¿½n generada cuando no es posible crear una
     * <code>ValidacionNota</code>.
     *
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     */
    public static String VALIDACION_NOTA_NO_CREADA = "No fue posible crear la Validacion de Nota";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar
     * una<code>ValidacionNota</code>.
     *
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     */
    public static String VALIDACION_NOTA_NO_ELIMINADA = "No fue posible eliminar la Validacion de Nota";

    /**
     * Excepciï¿½n generada cuando no es posible adicionar un
     * <code>TipoCertificadoValidacion</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public static String ADICION_TIPO_CERTIFICADO_VALIDACION_NO_EFECTUADA = "No fue posible adicionar la validaciï¿½n al Tipo de Certificado";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar un
     * <code>TipoCertificadoValidacion</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public static String ELIMINACION_TIPO_CERTIFICADO_VALIDACION_NO_EFECTUADA = "No fue posible eliminar la validaciï¿½n del Tipo de Certificado";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar un
     * <code>TipoCertificadoValidacion</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public static String CONSULTA_TIPO_CERTIFICADO_VALIDACION_NO_EFECTUADA = "No fue posible consultar las validaciones del Tipo de Certificado";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la lista de objetos
     * <code>Validacion</code> existente.
     *
     * @see gov.sir.core.negocio.modelo.Validacion
     */
    public static String CONSULTA_VALIDACIONES = "No fue posible consultar las lista de validaciones existentes ";

    /**
     * Excepciï¿½n generada cuando no es posible adicionar un
     * <code>TipoNota</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public static String ADICION_TIPO_NOTA = "No fue posible adicionar el Tipo de Nota ";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar un
     * <code>TipoNota</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public static String ELIMINACION_TIPO_NOTA = "No fue posible eliminar el Tipo de Nota ";

    /**
     * Excepciï¿½n generada cuando no es posible adicionar un
     * <code>CheckItem</code>.
     *
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public static String ADICION_CHECK_ITEM = "No fue posible adicionar el Item de Chequeo";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar un
     * <code>CheckItem</code>.
     *
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public static String ELIMINACION_CHECK_ITEM = "No fue posible eliminar el Item de Chequeo ";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar un
     * <code>CheckItem</code>.
     *
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public static String CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD
            = "No fue posible consultar los Items de Chequeo por subtipo de solicitud";

    /**
     * Excepciï¿½n generada cuando no es posible asociar un <code>Usuario</code>a
     * un turno.
     *
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String USUARIO_NO_ASOCIADO_TURNO
            = "No fue posible asociar un Usuario a un Turno";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * visibilidad, asociados con las notas informativas.
     */
    public static String LISTA_TIPOS_VISIBILIDAD_NO_OBTENIDA
            = "No fue posible obtener el listado de tipos de visibilidad";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * tarifas de certificados
     */
    public static String LISTA_TIPOS_TARIFAS_CERTIFICADOS_NO_OBTENIDA
            = "No fue posible obtener la lista de tipos de tarifas de certificados";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el circulo del portal
     */
    public static String CIRCULO_PORTAL_NO_OBTENIDO
            = "No fue posible obtener el circulo del portal";

    /**
     * Excepciï¿½n generada cuando no es posible realizar la actualizaciï¿½n de
     * ï¿½rdenes asociados con los usuarios y sub tipos de atenciï¿½n.
     */
    public static String ROTACION_REPARTO_NO_REALIZADA
            = "No fue posible realizar la rotaciï¿½n en el proceso de reparto-";

    /**
     * Excepciï¿½n generada cuando no es posible actualizar los datos de un tipo
     * de nota.
     */
    public static String TIPO_NOTA_NO_ACTUALIZADO
            = "No fue posible actualizar el tipo de nota.";

    /**
     * Excepciï¿½n generada cuando no es posible anular un turno.
     */
    public static String TURNO_NO_ANULADO
            = "No fue posible anular el turno.";

    /**
     * Excepciï¿½n generada cuando no es posible habilitar un turno.
     */
    public static String TURNO_NO_HABILITADO
            = "No fue posible habilitar el turno.";

    /**
     * Excepciï¿½n generada cuando no es posible guardar el motivo por el cual se
     * incrementï¿½ el secuencial de recibos.
     */
    public static String MOTIVO_SECUENCIAL_RECIBOS_NO_ALMACENADO
            = "No fue posible almacenar el motivo por el cual se incrementa el secuencial de recibos";

    /**
     * Excepciï¿½n generada cuando no es posible Consultar el porcentaje de
     * probabilidad de ir a la fase de revisiï¿½n de calificaciï¿½n.
     */
    public static String PROBABILIDAD_REVISION_CALIFICACION_NO_OBTENIDA
            = "No fue posible obtener la probabilidad de ir a la fase Revisiï¿½n de Calificaciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible Consultar el numero de
     * impresiones de certificados en correcciones
     */
    public static String NUMERO_DE_IMPRESIONES_CERTIFICADOS_EN_CORRECCIONES_NO_OBTENIDA
            = "No fue posible obtener el numero de impresiones de certificados en correccion";

    /**
     * Excepciï¿½n generada cuando no es posible Modificar el porcentaje de
     * probabilidad de ir a la fase de revisiï¿½n de calificaciï¿½n.
     */
    public static String PROBABILIDAD_REVISION_CALIFICACION_NO_MODIFICADA
            = "No fue posible modificar la probabilidad de ir a la fase Revisiï¿½n de Calificaciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de solicitudes
     * cuyas liquidaciones no han sido pagadas dentro de una fecha dada.
     */
    public static String LISTA_SOLICITUDES_SIN_TURNOS_ASOCIADOS_NO_OBTENIDA
            = "No fue posible obtener la lista de solicitudes sin turnos asociados.";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar una solicitud sin turnos
     * asociados.
     */
    public static String SOLICITUD_SIN_TURNOS_ASOCIADOS_NO_ELIMINADA
            = "No fue posible eliminar la Solicitud recibida.";
    
    
    public static String LIQUIDACION_SIN_ID_NO_ELIMINADA
        = "No fue posible eliminar la liquidacion anterior.";

    /**
     * Excepciï¿½n generada cuando se valida una matrï¿½cula en mesa de control
     */
    public static String VALIDAR_MATRICULA_MESA_CONTROL
            = "Fallo en la validaciï¿½n de la matrï¿½cula";

    /**
     * Excepcion generada cuando no es posible modificar la categorï¿½a de
     * clasificaciï¿½n de una solicitud de registro.
     */
    public static String CATEGORIA_CLASIFICACION_NO_MODIFICADA = "No fue posible modificar la categorï¿½a de clasificaciï¿½n de la solicitud de registro";

    /**
     * Excepcion generada cuando no es posible registrar un pago.
     */
    public static String PAGO_NO_REGISTRADO = "No fue posible registrar el Pago.";

    /**
     * Excepciï¿½n generada cuando no es posible modificar el nï¿½mero de intentos
     * permitidos en un proceso de impresiï¿½n.
     */
    public static String NUMERO_INTENTOS_IMPRESION_NO_MODIFICADO = "No fue posible modificar el nï¿½mero permitido de intentos de impresiï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible modificar el tiempo de espera en
     * un proceso de impresiï¿½n.
     */
    public static String TIEMPO_ESPERA_IMPRESION_NO_MODIFICADO = "No fue posible modificar el tiempo de espera en el proceso de impresiï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible crear un turno de registro.
     */
    public static String TURNO_REGISTRO_NO_CREADO = "No fue posible crear un turno de registro.";

    /**
     * Excepciï¿½n generada cuando no es posible crear un turno de fotocopias
     */
    public static String TURNO_FOTOCOPIAS_NO_CREADO = "No fue posible crear un turno de fotocopias.";

    /**
     * Excepciï¿½n generada cuando no es posible agregar una solicitudFolio a una
     * solicitud Hija
     */
    public static String SOLICITUD_FOLIO_ESTADO_NO_MODIFICADO = "No fue posible modificar el estado de la solicitud folio";

    /**
     * Excepciï¿½n generada cuando no es posible agregar una solicitudFolio a una
     * solicitud Hija
     */
    public static String SOLICITUD_FOLIO_NO_AGREGADA_SOLICITUD_HIJA = "No fue posible agregar una solicitud folio a una solicitud hija";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar una solicitudFolio de
     * una solicitud Hija
     */
    public static String SOLICITUD_FOLIO_NO_ELIMINADA_SOLICITUD_HIJA = "No fue posible eliminar una solicitud folio de una solicitud hija";

    /**
     * Excepciï¿½n generada cuando no es posible modificar el atributo ajuste de
     * una solicitud de registro.
     */
    public static String AJUSTE_REGISTRO_NO_MODIFICADO = "No fue posible modificar el atributo ajuste de la solicitud de registro.";

    /**
     * Excepciï¿½n generada cuando no es posible modificar el atributo ajuste de
     * una solicitud de registro.
     */
    public static String ROTACION_NO_OBTENIDA = "No fue posible obtener los usuarios rotados del cï¿½rculo.";

    /**
     * Excepciï¿½n generada cuando no es posible agregar una liquidaciï¿½n a una
     * solicitud.
     */
    public static String LIQUIDACION_NO_AGREGADA_TURNO = "No fue posible agregar una nueva liquidaciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible actualizar un objeto
     */
    public static String NO_ACTUALIZACION = "No fue posible actualizar el objeto";

    /**
     * Excepciï¿½n generada cuando no es posible crear el oficio
     */
    public static String NO_CREACION_OFICIO = "No fue posible crear el oficio";

    /**
     * Excepciï¿½n generada cuando no es posible cobtener el cï¿½digo de lookup
     */
    public static String NO_OBTENCION_DE_LOOKUPCODE = "No fue posible obtener el cï¿½digo especificado";

    /**
     * Excepciï¿½n generada cuando no es posible cobtener el cï¿½digo de lookup
     */
    public static String NO_OBTENCION_DE_OFICIOS = "No fue posible obtener los oficios del turno";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el usuario asociado al
     * turno con el folio indicado
     */
    public static String NO_OBTENCION_USUARIO = "No fue posible obtener el usuario asociado al turno con el folio indicado";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de naturalezas
     * jurï¿½dicas de entidades.
     */
    public static String NATURALEZA_JURIDICA_ENTIDADES_NO_OBTENIDA = "No fue posible obtener el listado de naturalezas jurï¿½dicas de entidades.";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar una naturaleza jurï¿½dica
     * de entidad pï¿½blica.
     */
    public static String NATURALEZA_JURIDICA_ENTIDADES_NO_ELIMINADA = "No fue posible obtener el listado de naturalezas jurï¿½dicas de entidades.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de permisos de
     * correcciï¿½n
     */
    public static String PERMISOS_CORRECCION_NO_OBTENIDOS = "No fue posible obtener el listado de permisos de correcciï¿½n.";

    /**
     * Excepciï¿½n generada cuando no es posible agregar una naturaleza jurï¿½dica
     * de entidad.
     */
    public static String NATURALEZA_JURIDICA_ENTIDAD_NO_AGREGADA = "No fue posible agregar una naturaleza jurï¿½dica de entidad.";

    /**
     * Excepciï¿½n generada cuando no es posible editar una naturaleza jurï¿½dica de
     * entidad.
     */
    public static String NATURALEZA_JURIDICA_ENTIDAD_NO_EDITADA = "No fue posible editar la naturaleza jurï¿½dica de entidad pï¿½blica.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de permisos de
     * correcciï¿½n
     */
    public static String PERMISOS_CORRECCION_NO_ASIGNADOS = "No fue posible asignar los permisos de correcciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de permisos de
     * correcciï¿½n
     */
    public static String NO_REFRESCA_SUBTIPO_ATENCION = "No fue posible refrescar el subtipo de atenciï¿½n del turno";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el identificador de un
     * turno, dado el identificador de una minuta asociada.
     */
    public static String ID_WORKFLOW_BY_ID_MINUTA_NO_OBTENIDO = "No fue posible obtener el identificador de workflow, de un turno asociado con una minuta.";

    /**
     * Excepciï¿½n generada cuando no es posible agregar una entidad pï¿½blica.
     */
    public static String ENTIDAD_PUBLICA_NO_AGREGADA = "No fue posible agregar la entidad pï¿½blica.";

    /**
     * Excepciï¿½n generada cuando no es posible actualizar una entidad pï¿½blica.
     */
    public static String ENTIDAD_PUBLICA_NO_ACTUALIZADA = "No fue posible actualizar la entidad pï¿½blica.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de minutas por
     * repartir dentro de un <code>CirculoNotarial</code>
     */
    public static String MINUTAS_NO_REPARTIDAS_NO_OBTENIDAS = "No fue posible obtener el listado de minutas por repartir";

    /**
     * Excepciï¿½n generada cuando no es posible desactivar una
     * <code>EntidadPublica</code> para reparto notarial.
     */
    public static String ENTIDAD_PUBLICA_NO_ELIMINADA = "No fue posible desactivar la Entidad Publica";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de minutas en
     * las que figura un otorgante.
     */
    public static String MINUTAS_POR_OTORGANTE_NO_OBTENIDAS = "No fue posible obtener el listado de minutas en las que aparece un otorgante.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de minutas
     * repartidas dentro de un rango de fechas.
     */
    public static String MINUTAS_RANGO_FECHAS_NO_OBTENIDAS = "No fue posible obtener el listado de minutas repartidas dentro de un rango de fechas.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de cï¿½rculos
     * notariales, asociados con un cï¿½rculo registral.
     */
    public static String CIRCULOS_NOTARIALES_CIRCULO_REGISTRAL_NO_OBTENIDOS = "No fue posible obtener el listado de cï¿½rculos notariales de un cï¿½rculo registral.";

    /**
     * Excepciï¿½n generada cuando no es posible setear los datos de antiguo
     * sistema a la solicitud
     */
    public static String DATOS_ANTIGUO_SISTEMA_NO_SETEADOS = "No fue posible setear los datos de antiguo sistema a la solicitud";

    /**
     * Excepciï¿½n generada cuando no es posible setear los datos de antiguo
     * sistema a la solicitud
     */
    public static String COLAS_REPARTO_NOTARIAL_NO_OBTENIDAS = "No fue posible obtener las colas por categorï¿½a para reparto notarial";

    /**
     * Excepciï¿½n generada cuando no es posible setear las marcas a los folios
     * asociados al turno
     */
    public static String NO_MARCA_FOLIO = "No fue posible setear las marcas a los folios asociados al turno";

    /**
     * Excepciï¿½n generada cuando no es obtener la lista de alertas
     */
    public static String NO_ALERTAS = "No fue posible obtener la lista de alertas";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el documento de pago
     */
    public static String NO_DOCUMENTO_PAGO = "No fue posible obtener el documento de pago";

    /**
     * Excepciï¿½n generada cuando no es posible decidir si el turno tiene el tipo
     * de acto indicado
     */
    public static String NO_HAS_ACTO_TURNO = "No fue posible decidir si el turno tiene el tipo de acto indicado";

    /**
     * Excepciï¿½n generada cuando se genera un error en la validaciï¿½n de la
     * solicitud vinculada
     */
    public static String NO_VALIDA_SOLICITUD_VINCULADA = "Error en la validaciï¿½n de la solicitud vinculada";

    /**
     * Crea una nueva instancia de <code>HermodException</code> sin ningun
     * mensaje detallado
     */
    public HermodException() {
        super();
    }

    /**
     * Crea una nueva instancia de <code>HermodException</code> con el mensaje
     * detallado
     *
     * @param msg el mensaje detallado
     */
    public HermodException(String msg) {
        super(msg);
    }

    /**
     * Crea una nueva instancia de <code>HermodException</code> con el mensaje
     * detallado, y throwable correspondiente a la excepcion
     *
     * @param msg el mensaje detallado
     * @param tr el throwable de la excepcion
     */
    public HermodException(String msg, Throwable tr) {
        super(msg, tr);
    }

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de intereses
     * jurï¿½dicos.
     */
    public static String LISTA_INTERESES_JURIDICOS_NO_OBTENIDA = "No fue posible obtener la lista de intereses jurï¿½dicos";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la ruta fï¿½sica donde se
     * encuentran las firmas de los registradores.
     */
    public static String RUTA_FIRMAS_NO_ENCONTRADA = "No fue posible encontrar la ruta fï¿½sica de la firma de los registradores";

    /**
     * Excepciï¿½n generada cuando es posible deshacer los cambios de los avanzar
     * push.
     */
    public static String DESHACER_AVANZAR_PUSH_NO_REALIZADO = "No fue posible deshacer el avanzar push.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el tipo de contenido para
     * las imagenes.
     */
    public static String CONTENT_TYPE_NO_ENCONTRADO = "No fue posible encontrar el tipo de contenido para las imï¿½genes";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la url del servlet de
     * reportes.
     */
    public static String URL_SERVLET_REPORTES_NO_ENCONTRADO = "No fue posible encontrar la url del servlet de reportes";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el nï¿½mero mï¿½ximo de
     * impresiones para los certificados.
     */
    public static String NUMERO_IMPRESIONES_CERTIFICADOS_NO_ENCONTRADO = "No fue posible encontrar el nï¿½mero mï¿½ximo de impresiones para los certificados";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el texto para
     * certificados exentos.
     */
    public static String TEXTO_EXENTO_NO_ENCONTRADO = "No fue posible encontrar el texto para un certificado exento";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de pantallas
     * administrativas asociadas a un rol
     */
    public static String PANTALLAS_ADMINISTRATIVAS_ROL_NO_OBTENIDAS = "No fue posible obtener el listado de pantallas administrativas asociadas con el rol del usuario";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de tipos de
     * pantallas administrativas
     */
    public static String TIPOS_PANTALLAS_ADMINISTRATIVAS_NO_OBTENIDOS = "No fue posible obtener el listado de tipos de pantallas administrativas";

    public static final String NUMERO_RECIBO_PAGO_NO_ESTABLECIDO = "No fue posible establecer el nï¿½mero de recibo para el pago";

    public static final String PAGO_CON_NUMERO_RECIBO_NO_OBTENIDO = "No se pudo obtener el pago con el nï¿½mero de recibo dado";

    public static final String TURNO_HISTORIA_NO_AGREGADO = "No fue posible agregar los datos a la historia del turno";

    public static final String ACTUALIZAR_TURNO_ASOCIADO_NO_EXITOSO = "No fue posible actualizar el turno asociado.";

    public static final String PROCESO_NO_ENCONTRADO = "No se encontrï¿½ el proceso";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el nï¿½mero de
     * modificaciones permitidas para la ediciï¿½n de minutas de reparto notarial.
     */
    public static final String NUMERO_MODIFICACIONES_MINUTA_NO_OBTENIDO = "No fue posible obtener el nï¿½mero de modificaciones permitido para  la ediciï¿½n de minutas";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de turnos
     */
    public static String NO_LISTA_TURNOS = "No fue posible obtener el listado de turnos";

    /**
     * Excepciï¿½n generada cuando no es posible modificar el atributo que
     * almacena el nï¿½mero de modificaciones permitidas para la ediciï¿½n de
     * minutas de reparto notarial.
     */
    public static final String NUMERO_MODIFICACIONES_MINUTA_NO_MODIFICADO = "No fue posible actualizar el nï¿½mero de modificaciones permitido para  la ediciï¿½n de minutas";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de notarï¿½as
     * pendientes de reparto.
     */
    public static final String NOTARIAS_PENDIENTES_NO_OBTENIDAS = "No fue posible obtener el listado de Notarï¿½as pendientes de Reparto Notarial";

    /**
     * Excepciï¿½n generada cuando no es posible obtener eliminar las notas
     * devolutivas.
     */
    public static final String DEVOLUTIVAS_TURNO_NO_ELIMINADAS = "No fue posible eliminar las notas devolutivas del turno";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el valor de Otro impuesto
     * en las liquidaciones de un turno de registro y sus valores en sus turnos
     * anteriores.
     */
    public static final String OTRO_IMPUESTO_TURNOS_ANTERIORES = "No fue posible encontrar el valor Otro impuesto para el turno.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el valor del secuencial
     * de la constancia de devoluciï¿½n para un cï¿½rculo dado.
     */
    public static final String SECUENCIAL_DEVOLUCION_NO_OBTENIDO = "No fue posible obtener el secuencial de la constancia de devoluciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el valor del secuencial
     * de la constancia de devoluciï¿½n para un cï¿½rculo dado.
     */
    public static final String SECUENCIAL_MASIVOS_NO_OBTENIDO = "No fue posible obtener el secuencial del recibo de certificados masivos";

    public static final String REPORTE_JASPER_NO_GENERADO = "No fue posible generar el reporte jasper";

    /**
     * Excepciï¿½n generada cuando no es posible modificar el valor de la ï¿½ltima
     * liquidaciï¿½n de un turno
     */
    public static String ULTIMA_LIQUIDACION_NO_ACTUALIZADA = "No fue posible modificar el valor de la ï¿½ltima liquidaciï¿½n del turno";

    /**
     * Excepciï¿½n generada cuando no es posible agregar una nota de actuaciï¿½n
     * administrativa a la solitidu de un turno.
     */
    public static String ADICION_NOTAS_ACTUACION = "No fue posible agregar notas de actuaciï¿½n al turno.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de items de
     * chequeo para devoluciones.
     */
    public static String ITEMS_CHEQUEO_DEVOLUCIONES_NO_OBTENIDOS = "No fue posible obtener el listado de items de chequeo para devoluciones.";

    /**
     * Excepciï¿½n generada cuando no es posible modificar los atributos de un
     * causal de restituciï¿½n
     */
    public static String CAUSAL_RESTITUCION_NO_MODIFICADO = "No fue posible modificar el causal de restituciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de items de
     * chequeo para devoluciones.
     */
    public static String ULTIMO_TURNO_PROCESO_NO_OBTENIDO = "No fue posible obtener el ï¿½ltimo del usuario para el proceso seleccionado.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de items de
     * chequeo para devoluciones.
     */
    public static String ACTUALIZACION_INTENTO_REIMPRESION_NO_EFECTUADO = "No fue posible incrementar el intento de reimpresion.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de turnos de
     * restituciï¿½n asociados con una Minuta.
     */
    public static String LISTADO_TURNOS_RESTITUCION_MINUTA_NO_OBTENIDO = "No fue posible obtener el listado de turnos de restituciï¿½n asociados con una Minuta";

    /**
     * Excepciï¿½n generada cuando no es posible agregar un circulo notarial.
     */
    public static String AGREGAR_CIRCULO_NOTARIAL_NO_EFECTUADO = "No fue posible agregar el cï¿½rculo notarial";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar un circulo notarial.
     */
    public static String ELIMINAR_CIRCULO_NOTARIAL_NO_EFECTUADO = "No fue posible eliminar el cï¿½rculo notarial";

    /**
     * Excepciï¿½n generada cuando no es posible editar un circulo notarial.
     */
    public static String EDITAR_CIRCULO_NOTARIAL_NO_EFECTUADO = "No fue posible editar el cï¿½rculo notarial";

    /**
     * Excepciï¿½n generada cuando no es posible consultar un circulo notarial.
     */
    public static String CIRCULO_NOTARIAL_NO_OBTENIDO = "No fue posible consultar el cï¿½rculo notarial";

    /**
     * Excepciï¿½n generada cuando no es posible agregar una zona notarial.
     */
    public static String AGREGAR_ZONA_NOTARIAL_NO_EFECTUADO = "No fue posible agregar la zona notarial";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar una zona notarial.
     */
    public static String ELIMINAR_ZONA_NOTARIAL_NO_EFECTUADO = "No fue posible eliminar la zona notarial";

    /**
     * Excepciï¿½n generada cuando no es posible crear la operaciï¿½n de englobe o
     * segregaciï¿½n.
     */
    public static String WEB_OPERACION_CREAR_NO_EFECTUADA = "No fue posible crear la operaciï¿½n de englobe o segregaciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar la operaciï¿½n de englobe
     * o segregaciï¿½n.
     */
    public static String WEB_OPERACION_ELIMINAR_NO_EFECTUADA = "No fue posible eliminar la operaciï¿½n de englobe o segregaciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible actualizar la operaciï¿½n de
     * englobe o segregaciï¿½n.
     */
    public static String WEB_OPERACION_ACTUALIZAR_NO_EFECTUADA = "No fue posible actualizar la operaciï¿½n de englobe o segregaciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la operaciï¿½n de englobe
     * o segregaciï¿½n.
     */
    public static String WEB_OPERACION_CONSULTAR_NO_EFECTUADA = "No fue posible consultar la operaciï¿½n de englobe o segregaciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible agregar una notarï¿½a al reparto.
     */
    public static String AGREGAR_NOTARIA_REPARTO_NO_EFECTUADO = "No fue posible agregar la notarï¿½a al reparto";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el texto del imprimible.
     */
    public static String TEXTO_IMPRIMIBLE_NO_OBTENIDO = "No fue posible obtener el texto del imprimible";

    /**
     * Excepciï¿½n generada cuando no es posible actualizar la marca de
     * revocatorias.
     */
    public static String ACTUALIZAR_MARCA_REVOCATORIA_NO_EFECTUADA = "No fue posible actualizar la marca de revocatorias para el turno";

    /**
     * Excepciï¿½n generada cuando no es reanotar un turno de registro.
     */
    public static String REANOTAR_TURNO_NO_EFECTUADO = "No fue posible reanotar el turno";

    /**
     * Excepciï¿½n generada cuando es posible consultar los tunrnos a reanotar.
     */
    public static String CONSULTAR_TURNOS_A_REANOTAR_NO_EFECTUADO = "No fue posible encontrar los turnos que han sido blqueados para ser reanotados";

    /**
     * Excepciï¿½n generada cuando es posible actualizar el ï¿½ltimo turno historia.
     */
    public static String ACTUALIZAR_LAST_TURNO_HISTORIA = "No fue posible actualizar el ï¿½ltimo turno historia";

    /**
     * Excepciï¿½n generada cuando es posible actualizar la informaciï¿½n de
     * Reimpresiï¿½n del Tuerno
     */
    public static String ACTUALIZAR_TURNO_INFO_IMPRESION = "No fue posible actualizar la informaciï¿½n de Reimpresiï¿½n del Turno";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * tarifas.
     *
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public static String LISTA_TIPOS_HORARIO_NOTARIAL_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Horario de Reparto";

    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de tipos de
     * tarifas.
     *
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public static String HORARIOS_NOTARIALES_CIRCULO_POR_TIPO_NO_OBTENIDOS = "No fue posible obtener los horarios notariales para el cï¿½rculo";

    public static String HORARIO_NOTARIAL_NO_AGREGADO = "No fue posible agregar el horario notarial";

    public static String HORARIO_NOTARIAL_NO_ELIMINADO = "No fue posible eliminar el horario notarial";

    public static String HORARIO_NOTARIAL_NO_EDITADO = "No fue posible editar el horario notarial";

    /**
     * Excepciï¿½n generada cuando no es posible consultar una relaciï¿½n por el id
     * de la relaciï¿½n.
     */
    public static String CONSULTA_RELACION_EXCEPTION = "No fue posible consultar la relaciï¿½n por el nï¿½mero de relaciï¿½n asociado";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String CONSULTA_TURNO_FOLIO_NO_EFECTUADA = "No fue posible consultar los folios asociados al turno del sistema FOLIO";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String CONSULTA_TURNO_FOLIO_NO_EFECTUADA_BY_FOLIO = "No fue posible consultar si el folio tiene turnos asociados en el sistema FOLIO";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String CONSULTA_TURNO_FOLIO_NO_EFECTUADA_BY_FOLIO_MIG = "No fue posible consultar si el folio es valido para asociar en el proceso de Migraciï¿½n";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_SOLICITUD_FOLIO
     */
    public static String CONSULTA_SOLICITUD_FOLIO_MIG_NO_EFECTUADA = "No fue posible consultar los folios asociados a la solicitud del sistema FOLIO";

    /**
     * Excepciï¿½n generada cuando no es posible adicionar registros a la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String ADICION_TURNO_FOLIO_NO_EFECTUADA = "No fue posible agregar los folios asociados al turno del sistema FOLIO";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String ELIMINACION_SOLICITUD_FOLIO_MIG_NO_EFECTUADA = "No fue posible eliminar los folios asociados a la solicitud del sistema FOLIO";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_MIG_TRAMITE_TURNO_FOLIO
     */
    public static String CONSULTA_TRAMITE_TURNO_FOLIO_NO_EFECTUADA = "No fue posible consultar los folios en tramite asociados al turno del sistema FOLIO";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_MIG_TRAMITE_TURNO_FOLIO
     */
    public static String CONSULTA_TRAMITE_TURNO_FOLIO_NO_EFECTUADA_BY_FOLIO = "No fue posible consultar si el folio tiene turnos en tramite en el sistema FOLIO";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_MIG_TRAMITE_TURNO_FOLIO
     */
    public static String CONSULTA_TURNO_FOLIO_TRAMITE_NO_EFECTUADA = "No fue posible verificar los folios en tramite para el turno en el sistema FOLIO";

    /**
     * Excepciï¿½n generada cuando no es posible insertar los registros en la
     * tabla SIR_MIG_REL_TURNO_FOLIO
     */
    public static String CREACION_TURNO_FOLIO_MIG_NO_EFECTUADA = "No fue posible agregar los folios asociados del turno anterior.";

    /**
     * Excepciï¿½n generada cuando no es posible insertar los registros en la
     * tabla SIR_MIG_TRAMITE_TURNO_FOLIO
     */
    public static String CREACION_TRAMITE_TURNO_FOLIO_MIG_NO_EFECTUADA = "No fue posible asociar los folios al turno en trï¿½mite.";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el listado de cï¿½rculos
     * notariales, asociados con un cï¿½rculo registral.
     */
    public static String CONSULTA_SOLICITUDES_TURNO_ANTERIOR_NO_EFECTUADA = "No fue posible obtener el listado de solicitudes por turno anterior.";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_OP_PANTALLA_ADMINISTRATIVA
     */
    public static String CONSULTA_PANTALLA_ADMINISTRATIVA_NO_EFECTUADA_BY_PAGINA = "No fue posible consultar las pantallas administrativas por pï¿½gina";

    /**
     * Excepciï¿½n generada cuando no es posible consultar la tabla
     * SIR_NE_CATEGORIA_NOTARIA
     */
    public static String CONSULTA_CATEGORIAS_NOTARIAS_NO_EFECTUADA = "No fue posible consultar la lista de Categorï¿½as a las que pertenecen las Notarï¿½as";

    /**
     * Excepciï¿½n generada cuando no es posible insertar en SIR_OP_ROL_PANTALLA
     */
    public static String ADD_ROL_PANTALLA_ADMINISTRATIVA_NO_EFECTUADO = "No fue posible agregar la pantalla al rol";

    /**
     * Excepciï¿½n generada cuando no es posible eliminar de SIR_OP_ROL_PANTALLA
     */
    public static String DELETE_ROL_PANTALLA_ADMINISTRATIVA_NO_EFECTUADO = "No fue posible eliminar la pantalla al rol";

    /**
     * Excepciï¿½n generada cuando no es posible cambiar la respuesta del ï¿½ltimo
     * TurnoHistoria con fase fase
     */
    public static String UPDATE_RESPUESTA_LAST_TURNO_HISTORIA_NO_EFECTUADO = "No fue posible eliminar la pantalla al rol";

    /**
     * Excepciï¿½n generada cuando no fue posible eliminar la relacion del turno
     */
    public static String RELACION_NO_ELIMINADA = "No fue posible eliminar la relacion del turno";

    /**
     * Excepciï¿½n generada cuando no fue posible actualizar el valor de la
     * liquidacion
     */
    public static String VALOR_LIQUIDACION_DEVOLUCION_NO_ACTUALIZADA = "No fue posible actualizar el valor de la liquidacion de devoluciones";

    /**
     * Excepciï¿½n generada cuando no se pudieron eliminar las notas informativas
     * asociadas al turno en su fase actual
     */
    public static String NOTAS_INFORMATIVAS_NO_ELIMINADAS = "No fue posible eliminar las notas informativas asociadas a la fase del turno";

    /**
     * Excepciï¿½n generado cuando no se pudo cambiar la marca de turno nacional
     */
    public static String TURNO_NACIONAL_NO_ACTUALIZADO = "No fue posible actualizar la marca de turno nacional";

    /**
     * Excepcion generada cuando no se puede obtener el inicio del proceso
     */
    public static String INICIO_PROCESO_NO_OBTENIDO = "No fue posible obtener el inicio del proceso";
    /**
     * Modifica Pablo Quintana Junio 19 2008 Variable que identifica que un
     * turno de testamento no fue encontrado
     */
    public static String TESTAMENTO_NO_ENCONTRADO = "No se encontro testamento";

    public static String TESTADOR_NO_ELIMINADO = "No se eliminï¿½el testador";

    public static String SOLICITUD_NO_ACTUALIZADA = "Error actualizando solicitud";

    /**
     * Excepciï¿½n generada cuando no es posible obtener el documento de pago
     */
    public static String DOCUMENTO_PAGO_NO_VALIDO = "El documento pago no es vï¿½lido";

    public static String BANCO_CIRCULO_NO_CREADO = "No fue posible crear relaciï¿½n banco cï¿½rculo";

    public static String BANCOS_X_CIRCULO_NO_OBTENIDA = "Lista de bancos por cï¿½rculo no obtenida";

    public static String BANCO_CIRCULO_NO_ELIMINADO = "No fue posible eliminar relaciï¿½n banco cï¿½rculo";

    /**
     * Excepciï¿½n generada cuando no es posible obtener eliminar las notas
     * devolutivas.
     */
    public static final String NOTA_DEVOLUTIVAS_TURNO_NO_ELIMINADS = "No fue posible eliminar la nota devolutiva del turno";


    /*
         * @author      : Julio Alcï¿½zar Rivas
         * @change      : Cadena de exception para metodo getCambioMatriculaAuditoria(TurnoPk tID)
         * Caso Mantis  :   02359
     */
    public static final String TURNO_NO_ENCONTRADO = "No fue posible obtener informaciï¿½n del turno";

    /*
         * @author      : Julio Alcï¿½zar Rivas
         * @change      : Cadena de exception para metodo getTurnosDevolucion(Turno turno)
         * Caso Mantis  : 07393
     */
    public static String TURNOS_DEVOLUCION_NO_OBTENIDOS = "No fue posible obtener listado de turnos de devoluciï¿½n";
    /**
     * @author: Diana Lora
     * @Change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes
     * Reparto Notarial
     */
    public static final String SECUENCIAL_REPARTO_NOTARIAL_NO_OBTENIDO = "No fue posible obtener el secuencial de recibo correspondiente al proceso de reparto notarial.";

    /*
         * @author      : Geremias Ortiz Lozanop
         * @change      : Excepcion creaciï¿½n cuenta bancaria
     */
    public static String CUENTA_BANCARIA_NO_CREADA = "No fue posible crear relaciï¿½n cï¿½rculo - banco - cuenta";

    public static String CUENTAS_BANCARIAS_NO_OBTENIDAS = "Lista de cuentas bancarias no obtenidas";

    public static String FALLO_ESTADO_CUENTA_BANCARIA = "Fallo servicio de actualizaciï¿½n de estados";
    
    public static String FALLO_ESTADO_CANAL_RECAUDO = "Fallo servicio de actualizaciï¿½n de estados";
    
    public static String FALLO_ESTADO_CTP = "Fallo servicio de actualizaciï¿½n de estados";
    
    public static String CANAL_RECAUDO_NO_ENCONTRADO = "Fallï¿½ servicio de busqueda de canal de recaudo";
    
    public static String TIPO_PAGO_NO_ENCONTRADO = "Fallï¿½ servicio de busqueda de tipo de pago";
    
    public static String CIRCULO_TIPO_PAGO_NO_ENCONTRADO = "Fallï¿½ servicio de busqueda de circulo tipo de pago";
    
    public static String CUENTA_BANCARIA_NO_ENCONTRADA = "Fallï¿½ servicio de busqueda de cuenta bancaria";
    /**
     * Excepciï¿½n generada cuando no es posible obtener la lista de canales.
     *
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public static String LISTA_CANALES_NO_OBTENIDA = "No fue posible obtener la lista de Canales de Recaudo";
    
    public static String TURNO_STATUS_REL
            = "No fue posible guardar stado y url rel";
    /**
     * @return
     */
    public Hashtable getHashErrores() {
        return hashErrores;
    }

    /**
     * @param hashErrores
     */
    public void setHashErrores(Hashtable hashErrores) {
        this.hashErrores = hashErrores;
    }

}
