package gov.sir.hermod;

import java.util.Hashtable;

/**
 * @author dlopez, mrios, mortiz Clase para el manejo de los diferentes tipos de
 * excepciones lanzadas durante la utilizaci�n de los servicios de hermod.
 */
public class HermodException extends java.lang.Exception {

    private Hashtable hashErrores = new Hashtable();

    /**
     * *****************************************************************
     */

    /*          Excepciones lanzadas en m�todos analizadores.           */
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
     * Excepci�n generada cuando no es posible obtener la lista de procesos.
     *
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public static String LISTA_PROCESOS_NO_OBTENIDA = "No fue posible obtener la lista de Procesos";
    
    /**
     * Excepci�n generada cuando no es posible adicionar la lista de notas informativas.
     *
     * @see gov.sir.core.negocio.modelo.Nota
     */
    public static String LISTA_NOTAS_INFORMATIVAS_NO_AGREGADAS = "No fue posible agregar la lista de notas informativas";
    
     /**
     * Excepci�n generada cuando no es posible adicionar un registro al control de matriculas.
     *
     * @see gov.sir.core.negocio.modelo.ControlMatricula
     */
    public static String CONTROL_MATRICULA_NO_AGREGADO = "No fue posible agregar al control de matriculas";
    
     /**
     * Excepci�n generada cuando no es posible adicionar un registro al control de reasignaci�n
     *
     * @see gov.sir.core.negocio.modelo.ControlReasignacion
     */
    public static String CONTROL_REASIGNACION_NO_AGREGADO = "No fue posible agregar al control de reasignacion";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de procesos que
     * inician.
     *
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public static String LISTA_PROCESOS_QUE_INICIA_NO_OBTENIDA = "No fue posible obtener la lista de Procesos que inicia";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de fases.
     *
     * @see gov.sir.core.negocio.modelo.Fase
     */
    public static String LISTA_FASES_NO_OBTENIDA = "No fue posible obtener la lista de Fases";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de fases
     * siguientes.
     *
     * @see gov.sir.core.negocio.modelo.Fase
     */
    public static String LISTA_FASES_SIGUIENTES_NO_OBTENIDA = "No fue posible obtener la lista de Fases siguientes";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de respuestas
     * siguientes.
     */
    public static String LISTA_RTAS_SIGUIENTES_NO_OBTENIDA = "No fue posible obtener la lista de Respuestas siguientes";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de turnos.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String LISTA_TURNOS_NO_OBTENIDA = "No fue posible obtener la lista de Turnos";
    /**
     * Excepci�n generada cuando no es posible crear un regsitro en reproduccion sellos
     *
     * @see gov.sir.core.negocio.modelo.ReproduccionSellos
     */
    public static String REPRODUCCIONNOCREADA = "No fue posible crear el registro de reproduccion de sellos";
    /**
     * Excepci�n generada cuando no es posible cambiar la estaci�n de un turno.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String TURNOS_NO_REASIGNADOS = "No fue posible reasignar los turnos seleccionados";

    /**
     * Excepci�n generada cuando no es posible la informacion en un turno
     * ejecucion dado.
     *
     * @see gov.sir.core.negocio.modelo.TurnoEjecucion
     */
    public static String TURNO_EJECUCION_NO_ACTUALIZADO = "No fue posible restituir el turno";

    /**
     * Excepci�n generada cuando no es posible ejecutar la reanotaci�n de un
     * turno en las tablas del modelo operativo.
     *
     * @see gov.sir.core.negocio.modelo.TurnoEjecucion
     */
    public static String REANOTACION_NO_EJECUTADA_MODELO_OPERATIVO = "No fue posible restituir el turno en el modelo operativo de SIR";

    /**
     * Excepci�n generada cuando no es posible actualizar el turno historia.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String TURNOS_HISTORIA_NO_ACTUALIZADA = "No fue posible actualizar el turno usuario para la fase";

    /**
     * Excepci�n generada cuando no es posible obtener un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String TURNO_NO_OBTENIDO = "No fue posible obtener el Turno";

    /**
     * Excepci�n generada cuando no es posible obtener un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String DATOS_PAGO_NO_OBTENIDOS = "No fue posible obtener los datos asociados al pago para el turno";

    /**
     * Excepci�n generada cuando no es posible obtener un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String CREACION_DOCUMENTO_PAGO_NO_EFECTUADA = "No fue posible crear el documento de pago";

    /**
     * Excepci�n generada cuando no es posible obtener un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String DATOS_CONSIGNACION_NO_OBTENIDOS = "No fue posible obtener los datos de la consignacion o cheque";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de Bancos.
     *
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public static String LISTA_BANCOS_NO_OBTENIDA = "No fue posible obtener la lista de Bancos";

    /**
     * Excepci�n generada cuando no es posible obtener el rango de aceptaci�n.
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
     * Excepci�n generada cuando no es posible obtener la lista de Lookup Codes.
     *
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String LISTA_LOOKUP_CODES_NO_OBTENIDA = "No fue posible obtener la lista de OPLookupCodes";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de Lookup Types.
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     */
    public static String LISTA_LOOKUP_TYPES_NO_OBTENIDA = "No fue posible obtener la lista de LookUpTypes";
    
    /**
     * Excepci�n generada cuando no es posible eliminar los actos.
     */
    public static String ACTOS_NO_ELIMINADOS = "No fue posible eliminar los actos";
    
    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * consulta.
     *
     * @see gov.sir.core.negocio.modelo.TipoConsulta
     */
    public static String LISTA_TIPOS_CONSULTA_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Consultas";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * alcance geogr�fico.
     *
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     */
    public static String LISTA_ALCANCES_GEOGRAFICOS_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Alcances Geograficos";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * fotocopias.
     *
     * @see gov.sir.core.negocio.modelo.TipoFotocopia
     */
    public static String LISTA_TIPOS_FOTOCOPIA_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Fotocopia";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * recepci�n petici�n
     *
     * @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
     */
    public static String LISTA_TIPOS_RECEPCION_PETICION_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Recepci�n Petici�n";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de subtipos de
     * atenci�n.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public static String LISTA_SUB_TIPOS_ATENCION_NO_OBTENIDA = "No fue posible obtener la lista de Subtipos de atenci�n";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de subtipos de
     * solicitud.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     */
    public static String LISTA_SUB_TIPOS_SOLICITUD_NO_OBTENIDA = "No fue posible obtener la lista de Subtipos de Solicitud";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * actos.
     *
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public static String LISTA_TIPOS_ACTO_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Actos";

    /**
     * Excepci�n generada cuando no es posible obtener un tipo de acto.
     *
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public static String TIPO_ACTO_NO_OBTENIDO = "No fue posible obtener el Tipo de Acto";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * impuestos.
     *
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     */
    public static String LISTA_TIPOS_IMPUESTOS_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Impuestos";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * Accion Notarial
     *
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     */
    public static String LISTA_TIPOS_ACCION_NOTARIAL_NO_OBTENIDA = "No fue posible obtener la lista de tipos de Accion Notarial";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de causales de
     * restituci�n.
     *
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    public static String LISTA_CAUSALES_RESTITUCION_NO_OBTENIDA = "No fue posible obtener la lista de Causales de Restituci�n";

    /**
     * Excepci�n generada cuando no es posible eliminar un causal de
     * restituci�n.
     *
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    public static String ELIMINACION_CAUSALES_RESTITUCION_NO_EFECTUADA = "No fue posible eliminar el Causale de Restituci�n";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * c�lculo.
     *
     * @see gov.sir.core.negocio.modelo.TipoCalculo
     */
    public static String LISTA_TIPOS_CALCULO_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de C�lculo";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * derechos registrales.
     *
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     */
    public static String LISTA_TARIFA_DERECHO_REGISTRAL_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Derechos Registrales";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * tarifas.
     *
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public static String LISTA_TIPOS_TARIFA_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Tarifas";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tarifas.
     *
     * @see gov.sir.core.negocio.modelo.Tarifa
     */
    public static String LISTA_TARIFAS_NO_OBTENIDA = "No fue posible obtener la lista de Tarifas";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * certificados.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     */
    public static String LISTA_TIPOS_CERTIFICADO_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Certificados";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * documentos.
     *
     * @see gov.sir.core.negocio.modelo.TipoDocumento
     */
    public static String LISTA_TIPOS_DOCUMENTO_NO_OBTENIDA = "No fue posible obtener la lista de tipos de documentos";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de subtipos de
     * atenci�n.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public static String LISTA_SUBTIPOS_ATENCION_USUARIO_NO_OBTENIDA = "No fue posible obtener la lista de los subtipos de atenci�n de los usuarios";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de Categor�as de
     * Reparto.
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String LISTA_CATEGORIAS_REPARTO_NO_OBTENIDA = "No fue posible obtener la lista de categorias de reparto";

    /**
     * Excepci�n generada cuando no es posible obtener el reparto de un
     * <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Reparto
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String REPARTO_NO_OBTENIDO = "No fue posible obtener el reparto del turno";

    /**
     * Excepci�n generada cuando no es posible obtener el reparto de un
     * <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Reparto
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String IMPRESION_REPARTO_NO_OBTENIDO = "No fue posible realizar la reimpresi�n del Acta";

    /**
     * Excepci�n generada cuando no es posible obtener el reparto de un
     * <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Reparto
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String IMPRESION_REPARTO_C_NO_OBTENIDO = "No fue posible realizar la reimpresi�n de la Car�tula";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de sucursales
     * de un <code>Banco</code>.
     *
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public static String SUCURSALES_NO_OBTENIDAS = "No fue posible obtener el listado de sucursales";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de minutas no
     * asignadas.
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public static String LISTA_MINUTAS_NO_ASIGNADAS_NO_OBTENIDA = "No fue posible obtener la lista de minutas disponibles para realizar reparto";

    /**
     * Excepci�n generada cuando no es posible obtener el n�mero m�ximo de
     * impresiones permitido para un usuario.
     */
    public static String NUMERO_MAXIMO_IMPRESIONES_NO_OBTENDIO = "No fue posible obtener el n�mero m�ximo de impresiones permitido";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de tipos de
     * notas.
     *
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public static String LISTA_TIPOS_NOTA = "No fue posible obtener la lista de Tipos de Nota";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de minutas no
     * asignadas en una <code>Vereda</code>.
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Vereda
     */
    public static String LISTA_MINUTAS_BY_VEREDA_NO_OBTENIDA = "No fue posible obtener la lista de minutas seg�n vereda";

    /**
     * Excepci�n generada cuando no es posible obtener la Zona Notarial asociada
     * con una <code>Vereda</code>.
     *
     * @see gov.sir.core.negocio.modelo.ZonaNotarial
     * @see gov.sir.core.negocio.modelo.Vereda
     */
    public static String ZONA_NOTARIAL_VEREDA_NO_OBTENIDA = "No fue posible obtener la Zona Notarial de la Vereda";

    /**
     * Excepci�n generada cuando no es posible obtener la <code>Minuta</code>
     * asociada con un <code>Turno</code>.
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String MINUTA_ASOCIADA_TURNO_NO_OBTENIDA = "No fue posible obtener la minuta asociada al turno dado";

    /**
     * Excepci�n generada cuando no es posible obtener la
     * <code>Liquidaci�n</code> asociada con un <code>Acto</code>.
     *
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.Acto
     */
    public static String LIQUIDACION_ACTO_NO_OBTENIDA = "No fue posible obtener la liquidaci�n del acto dado";

    /**
     * Excepci�n generada cuando no es posible obtener la asociacion entre una
     * estaci�n y un recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_OBTENIDO = "No fue posible obtener la estaci�n recibo";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de validacion
     * de notas.
     *
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     */
    public static String LISTA_VALIDACION_NOTA = "No fue posible obtener la lista de Validaciones de Nota";

    /**
     * Excepci�n generada cuando no es posible ACTUALIZAR los subtipos de
     * atenci�n de un usuario.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public static String ERROR_ACTUALIZACION_SUBTIPOS_ATENCION_USUARIO = "No fue posible actualizar los subtipos de atenci�n del Usuario.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado los procesos
     * existentes en el sistema
     *
     * @see gov.sir.core.negocio.modelo.Proceso
     */
    public static String CONSULTA_LISTA_PROCESOS = "No fue posible consultar la lista de procesos existentes en el sitema.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado las fases
     * relacionadas con un proceso espec�fico
     *
     * @see gov.sir.core.negocio.modelo.FaseProceso
     */
    public static String CONSULTA_FASES_PROCESO = "No fue posible consultar la lista de las fases del proceso.";

    /**
     * Excepci�n generada cuando no es posible obtener una <code>Fase</code>.
     *
     * @see gov.sir.core.negocio.modelo.Fase
     */
    public static String FASE_NO_OBTENIDA = "No fue posible obtener la Fase";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de turnos
     * anteriores asociados con un turno.
     */
    public static String TURNOS_ANTERIORES_NO_OBTENIDOS = "No fue posible obtener listado de turnos anteriores";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de turnos
     * siguientes asociados con un turno.
     */
    public static String TURNOS_SIGUIENTES_NO_OBTENIDOS = "No fue posible obtener listado de turnos siguientes";

    /**
     * Excepci�n generada cuando no es posible obtener el tipo de una fase.
     */
    public static String TIPO_FASE_NO_OBTENIDO = "No fue posible obtener el tipo de una fase";

    /**
     * Excepci�n generada cuando no es posible obtener la solicitud y sus
     * liquidaciones asociadas con el identificador dado.
     */
    public static String SOLICITUD_BY_ID_NO_OBTENIDA = "No fue posible obtener la solicitud con el id dado";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de turnos en un
     * rango dado, que han pasado por una fase dada.
     */
    public static String RANGO_TURNOS_BY_FASE_NO_OBTENIDO = "No fue posible obtener la lista de turnos en el rango y fase dadas";

    /**
     * Excepci�n generada cuando no es posible obtener el n�mero de intentos
     * permitidos en un proceso de impresi�n.
     */
    public static String NUMERO_INTENTOS_IMPRESION_NO_OBTENIDO = "No fue posible obtener el n�mero permitido de intentos de impresi�n";

    /**
     * Excepci�n generada cuando no es posible obtener el tiempo de espera en un
     * proceso de impresi�n.
     */
    public static String TIEMPO_ESPERA_IMPRESION_NO_OBTENIDO = "No fue posible obtener el tiempo de espera en el proceso de impresi�n";

    /**
     * Excepci�n generada cuando no es posible obtener el estado de pago de una
     * solicitud.
     */
    public static String ESTADO_PAGO_SOLICITUD_NO_OBTENIDO = "No fue posible obtener el estado de pago de una solcitud.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de turnos
     * calificados por un usuario.
     */
    public static String TURNOS_CALIFICADOS_USUARIO_NO_OBTENIDOS = "No fue posible obtener el listado de turnos calificados por un usuario";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de turnos
     * validos para agregar certificados asociados.
     */
    public static String TURNOS_VALIDOS_AGREGAR_CERTIFICADOS_ASOCIADOS_NO_OBTENIDOS = "No fue posible obtener el listado de turnos validos para agregar certificados asociados";

    /**
     * Excepci�n generada cuando no es posible obtener el proceso dado el id de
     * una fase.
     */
    public static String PROCESO_FASE_NO_ENCONTRADO = "No fue posible obtener el proceso dada una fase";

    /**
     * Excepci�n generada cuando no es posible obtener el c�rculo de proceso con
     * el id suministrado
     */
    public static String CIRCULO_PROCESO_NO_OBTENIDO = "No fue posible obtener el c�rculo de proceso con el identificador dado";

    /**
     * *****************************************************************
     */

    /*          Excepciones lanzadas en m�todos modificadores           */
    /**
     * *****************************************************************
     */
    /**
     * Excepci�n generada cuando no es posible crear un causal de restituci�n.
     *
     * @see gov.sir.core.negocio.modelo.CausalRestitucion
     */
    public static String CAUSAL_RESTITUCION_NO_CREADO = "No fue posible crear el Causal de Restituci�n";

    /**
     * Excepci�n generada cuando no es posible modificar los datos de una
     * liquidaci�n de registro de documentos.
     */
    public static String LIQUIDACION_REGISTRO_NO_MODIFICADA = "No fue posible modificar la liquidaci�n de registro.";

    /**
     * Excepci�n generada cuando no es posible crear una acci�n notarial.
     *
     * @see gov.sir.core.negocio.modelo.AccionNotarial
     */
    public static String ACCION_NOTARIAL_NO_CREADA = "No fue posible crear la acci�n notarial";

    /**
     * Excepci�n generada cuando no es posible crear un tipo de Alcance
     * Geogr�fico.
     *
     * @see gov.sir.core.negocio.modelo.AlcanceGeografico
     */
    public static String ALCANCE_GEOGRAFICO_NO_CREADO = "No fue posible crear el tipo de Alcance Geografico";

    /**
     * Excepci�n generada cuando no es posible crear un tipo de Acto.
     *
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public static String TIPO_ACTO_NO_CREADO = "No fue posible crear el tipo de acto";

    /**
     * Excepci�n generada cuando no es posible crear un tipo de C�lculo.
     *
     * @see gov.sir.core.negocio.modelo.TipoCalculo
     */
    public static String TIPO_CALCULO_NO_CREADO = "No fue posible crear el tipo de c�lculo";

    /**
     * Excepci�n generada cuando no es posible crear un tipo de Impuesto
     *
     * @see gov.sir.core.negocio.modelo.TipoImpuesto
     */
    public static String TIPO_IMPUESTO_NO_CREADO = "No fue posible crear el tipo de impuesto";

    /**
     * Excepci�n generada cuando no es posible crear un tipo de tarifa de
     * derecho registral
     *
     * @see gov.sir.core.negocio.modelo.TipoDerechoReg
     */
    public static String TARIFA_DERECHO_REGISTRAL_NO_CREADA = "No fue posible crear el tipo de derecho registral";

    /**
     * Excepci�n generada cuando no es posible crear un <code>Banco</code>.
     *
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public static String BANCO_NO_CREADO = "No fue posible crear el Banco";

    /**
     * Excepci�n generada cuando no es posible crear un tipo de fotocopia
     *
     * @see gov.sir.core.negocio.modelo.TipoFotocopia
     */
    public static String TIPO_FOTOCOPIA_NO_CREADO = "No fue posible crear el tipo de fotocopia";

    /**
     * Excepci�n generada cuando no es posible crear un LookupCode
     *
     * @see gov.sir.core.negocio.modelo.OPLookupCodes
     */
    public static String LOOK_UP_CODE_NO_CREADO = "No fue posible crear el Lookup Code";

    /**
     * Excepci�n generada cuando no es posible crear un LookupType
     *
     * @see gov.sir.core.negocio.modelo.OPLookupTypes
     */
    public static String LOOK_UP_TYPE_NO_CREADO = "No fue posible crear el Lookup Type";

    /**
     * Excepci�n generada cuando no es posible crear un Subtipo de Atenci�n.
     *
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     */
    public static String SUBTIPO_ATENCION_NO_CREADO = "No fue posible crear el subtipo de atenci�n";

    /**
     * Excepci�n generada cuando no es posible crear un Subtipo de Solicitud
     *
     * @see gov.sir.core.negocio.modelo.SubtipoSolicitud
     */
    public static String SUBTIPO_SOLICITUD_NO_CREADO = "No fue posible crear el subtipo de solicitud";

    /**
     * Excepci�n generada cuando no es posible crear una <code>Tarifa</code>
     *
     * @see gov.sir.core.negocio.modelo.Tarifa
     */
    public static String TARIFA_NO_CREADA = "No fue posible crear la tarifa";

    /**
     * Excepci�n generada cuando no es posible crear un tipo de Certificado.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificado
     */
    public static String TIPO_CERTIFICADO_NO_CREADO = "No fue posible crear el tipo de certificado";

    /**
     * Excepci�n generada cuando no es posible crear un Tipo de Consulta
     *
     * @see gov.sir.core.negocio.modelo.TipoConsulta
     */
    public static String TIPO_CONSULTA_NO_CREADO = "No fue posible crear el tipo de consulta";

    /**
     * Excepci�n generada cuando no es posible crear un Tipo de Tarifa.
     *
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public static String TIPO_TARIFA_NO_CREADO = "No fue posible crear el tipo de tarifa";

    /**
     * Excepci�n generada cuando no es posible crear un Tipo de Recepci�n
     * Petici�n.
     *
     * @see gov.sir.core.negocio.modelo.TipoRecepcionPeticion
     */
    public static String TIPO_RECEPCION_PETICION_NO_CREADO = "No fue posible crear el tipo de Recepci�n de Petici�n";

    /**
     * Excepci�n generada cuando no es posible crear un Tipo de Documento.
     *
     * @see gov.sir.core.negocio.modelo.TipoActo
     */
    public static String TIPO_DOCUMENTO_NO_CREADO = "No fue posible crear el tipo de documento";

    /**
     * Excepci�n generada cuando no es posible crear un Tipo de Nota.
     *
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public static String NOTA_NO_CREADO = "No fue posible crear el Tipo de Nota";

    /**
     * Excepci�n generada cuando no es posible agregar un testamento a una
     * solicitud de registro.
     *
     * @see gov.sir.core.negocio.modelo.Testamento
     */
    public static String TESTAMENTO_NO_CREADO = "No fue posible registrar el testamento";

    /**
     * Excepci�n generada cuando no es posible crear un proceso de reparto.
     *
     * @see gov.sir.core.negocio.modelo.ProcesoReparto
     */
    public static String PROCESO_REPARTO_NO_CREADO = "No fue posible crear proceso de reparto";

    /**
     * Excepci�n generada cuando no es posible crear una
     * <code>SucursalBanco</code>
     *
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     */
    public static String SUCURSAL_NO_CREADA = "No fue posible crear la sucursal";

    /**
     * Excepci�n generada cuando no es posible crear una Categor�a de Reparto
     * Notarial.
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String CATEGORIA_REPARTO_NO_CREADA = "No fue posible crear la categoria de reparto";

    /**
     * Excepci�n generada cuando no es posible crear un <code>Folio</code> a un
     * <code>Turno</code>
     *
     * @see gov.sir.core.negocio.modelo.Folio
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String FOLIO_NO_AGREGADO = "No fue posible agregar el folio al turno";

    /**
     * Excepci�n generada cuando no es posible eliminar un<code>Folio</code> de
     * un <code>Turno</code>
     *
     * @see gov.sir.core.negocio.modelo.Folio
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String FOLIO_NO_ELIMINADO = "No fue posible eliminar el folio del turno";

    /**
     * Excepci�n generada cuando no es posible eliminar un<code>Folio</code> de
     * un <code>Turno</code>
     *
     * @see gov.sir.core.negocio.modelo.Folio
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String FOLIO_NO_CAMBIADO = "No fue posible cambiar el Folio";

    /**
     * Excepci�n generada cuando no es posible agregar una <code>Minuta</code> a
     * un <code>Turno
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     */
    public static String MINUTA_NO_AGREGADA = "No fue posible agregar la minuta a la solicitud de reparto notarial";

    /**
     * Excepci�n generada cuando no es posible asignar un
     * <code>RepartoNotarial</code> a una <code>Minuta</code>
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.RepartoNotarial
     */
    public static String REPARTO_NO_ASOCIADO = "No fue posible asignar el reparto notarial a la minuta";

    /**
     * Excepci�n generada cuando no es posible anular una <code>Minuta </code>
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public static String MINUTA_NO_ANULADA = "No fue posible anular la minuta";

    /**
     * Excepci�n generada cuando no es posible modificar una <code>Minuta
     * </code>
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     */
    public static String MINUTA_NO_MODIFICADA = "No fue posible modificar la minuta";

    /**
     * Excepci�n generada cuando no es posible crear una <code>Categoria </code>
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String CATEGORIA_NO_CREADA = "No fue posible crear la Categor�a de Reparto Notarial";

    /**
     * Excepci�n generada cuando no es posible modificar una <code>Categoria
     * </code>
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String CATEGORIA_NO_MODIFICADA = "No fue posible modificar la categor�a";

    /**
     * Excepci�n generada cuando no es posible asociar una
     * <code>OficinaOrigen</code> con una <code>Categoria</code>
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.OficinaOrigen
     */
    public static String OFICINA_NO_ASOCIADA_CATEGORIA = "No fue posible asociar la Oficina Origen con la Categor�a";

    /**
     * Excepci�n generada cuando no es posible eliminar una <code>OficinaOrigen
     * </code>de una <code>Categoria</code>
     *
     * @see gov.sir.core.negocio.modelo.Categoria
     * @see gov.sir.core.negocio.modelo.OficinaOrigen
     */
    public static String OFICINA_NO_ELIMINADA_CATEGORIA = "No fue posible eliminar la Oficina Origen de la Categor�a";

    /**
     * Excepci�n generada cuando no es posible crear la asociaci�n Estaci�n
     * Recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_CREADO = "No fue posible crear la estaci�n recibo";

    /**
     * Excepci�n generada cuando no es posible resetear la asociaci�n Estaci�n
     * Recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_RESET = "No fue posible resetear la estaci�n recibo";

    /**
     * Excepci�n generada cuando no es posible resetear la asociaci�n Estaci�n
     * Recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_RESET_ULT_NUM = "No fue posible resetear el �ltimo n�mero de la estaci�n recibo";

    /**
     * Excepci�n generada cuando no es posible obtener el numero consecutivo de
     * un recibo.
     *
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_NEXT = "No fue posible obtener el numero de recibo";

    /**
     * Excepci�n generada cuando no es posible clasificar una minuta dentro de
     * una categor�a.
     *
     * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Categoria
     */
    public static String CLASIFICACION_MINUTA_NO_REALIZADA = "No fue posible clasificar la minuta dentro de una categor�a";

    /**
     * Excepci�n generada cuando no es posible asociar un <code>Ciudadano
     * </code> a una solicitud de consulta.
     *
     * @see gov.sir.core.negocio.modelo.Ciudadano
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     */
    public static String CIUDADANO_NO_ASOCIADO_A_SOLICITUD_CONSULTA = "No fue posible asociar el ciudadano a la solicitud de consulta";

    /**
     * Excepci�n generada cuando no es posible realizar shutdown de la conexion
     * en un servicio
     */
    public static String SHUTDOWN_NO_REALIZADO = "Fallo en el shutdown del servicio ";

    /**
     * Excepci�n generada cuando no es posible asociar un
     *  <code>numero de anotaciones</code> con una <code>Solitud</code>
     *
     * @see gov.sir.core.negocio.modelo.Solicitud
     */
    public static String NUMERO_ANOTACIONES_NO_ASOCIADO = "No fue posible asociar el numero de anotaciones a la solicitud";

    /**
     * Excepci�n generada cuando no es posible realizar el reparto de las
     * minutas de un C�rculo en particular.
     */
    public static String REPARTO_CIRCULO_NO_REALIZADO = "No fue posible realizar el Reparto Notarial del C�rculo";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de validaciones.
     */
    public static String LISTA_DE_VALIDACIONES_NO_OBTENIDA = "No fue posible obtener la lista de validaciones";

    /**
     * Excepci�n generada cuando no es posible crear un nuevo turno manual
     */
    public static String TURNO_MANUAL_NO_CREADO = "No fue posible crear el turno con la informaci�n suministrada";

    /**
     * Excepci�n generada cuando no es posible avanzar un turno normal
     */
    public static String TURNO_NORMAL_NO_AVANZADO = "No fue posible avanzar un turno normal";

    /**
     * Excepci�n generada cuando no es posible avanzar un turno pop
     */
    public static String TURNO_POP_NO_AVANZADO = "No fue posible avanzar un turno pop";

    /**
     * Excepci�n generada cuando no es posible avanzar un turno push
     */
    public static String TURNO_PUSH_NO_AVANZADO = "No fue posible avanzar un turno Push";

    /**
     * Excepci�n generada cuando no es posible avanzar un turno entrega.
     */
    public static String TURNO_ENTREGA_NO_AVANZADO = "No fue posible avanzar un turno Entrega";

    /**
     * Excepci�n generada cuando no es posible avanzar un turno en modo
     * AvanzarCualquiera
     */
    public static String TURNO_CUALQUIERA_NO_AVANZADO = "No fue posible avanzar un turno en modo cualquiera";

    /**
     * Excepci�n generada cuando no es posible avanzar un turno en modo
     * AvanzarEliminandoPush
     */
    public static String TURNO_ELIMINAR_PUSH_NO_AVANZADO = "No fue posible avanzar un turno en modo eliminarPush";

    /**
     * Excepci�n generada cuando no es posible avanzar un turno en modo
     * AvanzarUsuario
     */
    public static String TURNO_USUARIO_NO_AVANZADO = "No fue posible avanzar un turno en modo avanzarUsuario";

    /**
     * Excepci�n generada cuando no se puede consultar la lista de tipos de pago
     * del sistema
     */
    public static String LISTA_TIPOS_PAGO_NO_OBTENIDA = "No se pudo obtener la lista de tipos de pago del sistema";

    /**
     * Excepci�n generada cuando no se puede consultar la lista de tipos de pago
     * por c�rculo
     */
    public static String LISTA_CIRCULO_TIPOS_PAGO_NO_OBTENIDA = "No se pudo obtener la lista de tipos de pago por c�rculo";
    
    /**
     * Excepci�n generada cuando no se puede consultar la lista de tipos de pago
     * por c�rculo
     */
    public static String LISTA_CAMPOS_CAPTURA_X_FORMA_PAGO = "No se pudo obtener la lista de campos de captura para la forma de pago recibida";

    /**
     * Excepci�n generada cuando no se puede adicionar un Tipo de Pago a un
     * C�rculo
     */
    public static String CIRCULO_TIPO_PAGO_NO_ADICIONADO = "No se pudo adicionar el Tipo de Pago al C�rculo";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de Pago de un
     * C�rculo
     */
    public static String CIRCULO_TIPO_PAGO_NO_ELIMINADO = "No se pudo eliminar el Tipo de Pago del C�rculo";

    /**
     * Excepci�n generada cuando no se puede realizar el proceso de restituci�n
     * de reparto notarial.
     */
    public static String RESTITUCION_REPARTO_NO_REALIZADA = "No se pudo realizar el proceso de Restituci�n de Reparto Notarial ";

    /**
     * Excepci�n generada cuando no se puede asociar el n�mero m�ximo de
     * b�squedas a la solicitud de consulta.
     */
    public static String NUM_MAXIMO_BUSQUEDAS_NO_ASIGNADO = "No se pudo asingnar el n�mero m�ximo de b�squedas a la Solicitud Consulta ";

    /**
     * Excepci�n generada cuando no se puede asociar una resoluci�n a la
     * solicitud de reparto notarial.
     */
    public static String RESOLUCION_NO_ASOCIADA_SOLICITUD_RESTITUCION = "No se pudo asociar la Resoluci�n a la Solicitud de Restitucion";

    /**
     * Excepci�n generada cuando no se puede marcar como rechazada una solicitud
     * de reparto notarial.
     */
    public static String SOLICITUD_RESTITUCION_NO_RECHAZADA = "No se pudo marcar como rechazada la Solicitud de Restitucion";

    /**
     * Excepci�n generada cuando no se puede eliminar una Categor�a
     */
    public static String CATEGORIA_NO_ELIMINADA = "No se pudo eliminar la Categoria dada";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de Alcance
     * Geogr�fico.
     */
    public static String ALCANCE_GEOGRAFICO_NO_ELIMINADO = "No se pudo eliminar el Alcance Geogr�fico";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de Fotocopia.
     */
    public static String TIPO_FOTOCOPIA_NO_ELIMINADO = "No se pudo eliminar el Tipo de Fotocopia";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de C�lculo
     */
    public static String TIPO_CALCULO_NO_ELIMINADO = "No se pudo eliminar el Tipo de C�lculo";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de Derecho
     * Registral
     */
    public static String TIPO_DERECHO_REGISTRAL_NO_ELIMINADO = "No se pudo eliminar el Tipo de Derecho Registral";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de Acci�n Notarial
     */
    public static String TIPO_ACCION_NOTARIAL_NO_ELIMINADO = "No se pudo eliminar el Tipo de Acci�n Notarial";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de Acci�n Notarial
     */
    public static String RANGO_FOLIO_NO_ASOCIADO_SOLICITUD = "No se pudo asociar el rango de folios a la solicitud";

    /**
     * Excepci�n generada cuando no es posible modificar el encabezado de un
     * diocumento asociado a una <code>SolicitudRegistro </code>
     *
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public static String ENCABEZADO_NO_MODIFICADO = "No fue posible modificar el encabezado de la solicitud enviada";

    /**
     * Excepci�n generada cuando no es posible modificar el encabezado de un
     * diocumento asociado a una <code>SolicitudRegistro </code>
     *
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public static String CONSULTA_ESTACIONES_RECIBO_CIRCULO_NO_EFECTUADA = "No fue posible consultar las estaciones recibo del c�rculo";

    /**
     * Excepci�n generada cuando un <code>Turno</code> no tiene nota devolutiva
     * y deberia tenerla
     *
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public static String TURNO_SIN_NOTA = "El turno no tiene una nota asociada y es necesaria para la fase ingresada";

    /**
     * Excepci�n generada cuando un <code>Turno</code> no tiene el tipo de nota
     * devolutiva que es requerido
     *
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public static String TURNO_SIN_TIPO_NOTA = "El turno no tiene una nota asociada del tipo requerido y es necesaria para la fase ingresada";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de los tipos de
     * certificados individuales.
     *
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.Acto
     */
    public static String LISTA_CERTIFICADOS_INDIVIDUALES_NO_OBTENIDA = "No fue posible obtener la lista de tipos de certificados individuales";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de las entidades
     * p�blicas que intervienen en el proceso de reparto notarial.
     */
    public static String LISTA_ENTIDADES_PUBLICAS_REPARTO_NO_OBTENIDA = "No fue posible obtener la lista de entidades p�blicas que intervienen en el proceso de reparto";

    /**
     * Excepci�n generada cuando no se puede eliminar un Banco
     */
    public static String BANCO_NO_ELIMINADO = "No se pudo eliminar el Banco dado";

    /**
     * Excepci�n generada cuando no se puede eliminar una Sucursal Banco
     */
    public static String SUCURSAL_BANCO_NO_ELIMINADA = "No se pudo eliminar la Sucursal Banco dada";

    /**
     * Excepci�n generada cuando no se puede eliminar una Tarifa
     */
    public static String TARIFA_NO_ELIMINADA = "No se pudo eliminar la Tarifa dada";

    /**
     * Excepci�n generada cuando no se puede eliminar un Subtipo de Solicitud
     */
    public static String SUBTIPO_SOLICITUD_NO_ELIMINADO = "No se pudo eliminar el Subtipo de Solicitud dado";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de Tarifa
     */
    public static String TIPO_TARIFA_NO_ELIMINADO = "No se pudo eliminar el Tipo de Tarifa dado";

    /**
     * Excepci�n generada cuando no se puede eliminar un Lookup Type
     */
    public static String LOOKUP_TYPE_NO_ELIMINADO = "No se pudo eliminar el Lookup Type dado";

    /**
     * Excepci�n generada cuando no se puede eliminar un Lookup Code
     */
    public static String LOOKUP_CODE_NO_ELIMINADO = "No se pudo eliminar el Lookup Code dado";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de Impuesto
     */
    public static String TIPO_IMPUESTO_NO_ELIMINADO = "No se pudo eliminar el Tipo de Impuesto dado";

    /**
     * Excepci�n generada cuando no se puede eliminar un Subtipo de Atenci�n
     */
    public static String SUBTIPO_ATENCION_NO_ELIMINADO = "No se pudo eliminar el Subtipo de Atencion dado";

    /**
     * Excepci�n generada cuando no se puede eliminar un Tipo de Acto
     */
    public static String TIPO_ACTO_NO_ELIMINADO = "No se pudo eliminar el Tipo de Acto dado";

    /**
     * Excepci�n generada cuando no se puede eliminar una EstacionRecibo
     */
    public static String ESTACION_RECIBO_NO_ELIMINADA = "No se pudo eliminar la Estaci�n / Recibo";

    /**
     * Excepci�n generada cuando no es posible crear una
     * <code>ValidacionNota</code>.
     *
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     */
    public static String VALIDACION_NOTA_NO_CREADA = "No fue posible crear la Validacion de Nota";

    /**
     * Excepci�n generada cuando no es posible eliminar
     * una<code>ValidacionNota</code>.
     *
     * @see gov.sir.core.negocio.modelo.ValidacionNota
     */
    public static String VALIDACION_NOTA_NO_ELIMINADA = "No fue posible eliminar la Validacion de Nota";

    /**
     * Excepci�n generada cuando no es posible adicionar un
     * <code>TipoCertificadoValidacion</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public static String ADICION_TIPO_CERTIFICADO_VALIDACION_NO_EFECTUADA = "No fue posible adicionar la validaci�n al Tipo de Certificado";

    /**
     * Excepci�n generada cuando no es posible eliminar un
     * <code>TipoCertificadoValidacion</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public static String ELIMINACION_TIPO_CERTIFICADO_VALIDACION_NO_EFECTUADA = "No fue posible eliminar la validaci�n del Tipo de Certificado";

    /**
     * Excepci�n generada cuando no es posible eliminar un
     * <code>TipoCertificadoValidacion</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoCertificadoValidacion
     */
    public static String CONSULTA_TIPO_CERTIFICADO_VALIDACION_NO_EFECTUADA = "No fue posible consultar las validaciones del Tipo de Certificado";

    /**
     * Excepci�n generada cuando no es posible consultar la lista de objetos
     * <code>Validacion</code> existente.
     *
     * @see gov.sir.core.negocio.modelo.Validacion
     */
    public static String CONSULTA_VALIDACIONES = "No fue posible consultar las lista de validaciones existentes ";

    /**
     * Excepci�n generada cuando no es posible adicionar un
     * <code>TipoNota</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public static String ADICION_TIPO_NOTA = "No fue posible adicionar el Tipo de Nota ";

    /**
     * Excepci�n generada cuando no es posible eliminar un
     * <code>TipoNota</code>.
     *
     * @see gov.sir.core.negocio.modelo.TipoNota
     */
    public static String ELIMINACION_TIPO_NOTA = "No fue posible eliminar el Tipo de Nota ";

    /**
     * Excepci�n generada cuando no es posible adicionar un
     * <code>CheckItem</code>.
     *
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public static String ADICION_CHECK_ITEM = "No fue posible adicionar el Item de Chequeo";

    /**
     * Excepci�n generada cuando no es posible eliminar un
     * <code>CheckItem</code>.
     *
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public static String ELIMINACION_CHECK_ITEM = "No fue posible eliminar el Item de Chequeo ";

    /**
     * Excepci�n generada cuando no es posible eliminar un
     * <code>CheckItem</code>.
     *
     * @see gov.sir.core.negocio.modelo.CheckItem
     */
    public static String CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD
            = "No fue posible consultar los Items de Chequeo por subtipo de solicitud";

    /**
     * Excepci�n generada cuando no es posible asociar un <code>Usuario</code>a
     * un turno.
     *
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public static String USUARIO_NO_ASOCIADO_TURNO
            = "No fue posible asociar un Usuario a un Turno";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * visibilidad, asociados con las notas informativas.
     */
    public static String LISTA_TIPOS_VISIBILIDAD_NO_OBTENIDA
            = "No fue posible obtener el listado de tipos de visibilidad";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * tarifas de certificados
     */
    public static String LISTA_TIPOS_TARIFAS_CERTIFICADOS_NO_OBTENIDA
            = "No fue posible obtener la lista de tipos de tarifas de certificados";

    /**
     * Excepci�n generada cuando no es posible obtener el circulo del portal
     */
    public static String CIRCULO_PORTAL_NO_OBTENIDO
            = "No fue posible obtener el circulo del portal";

    /**
     * Excepci�n generada cuando no es posible realizar la actualizaci�n de
     * �rdenes asociados con los usuarios y sub tipos de atenci�n.
     */
    public static String ROTACION_REPARTO_NO_REALIZADA
            = "No fue posible realizar la rotaci�n en el proceso de reparto-";

    /**
     * Excepci�n generada cuando no es posible actualizar los datos de un tipo
     * de nota.
     */
    public static String TIPO_NOTA_NO_ACTUALIZADO
            = "No fue posible actualizar el tipo de nota.";

    /**
     * Excepci�n generada cuando no es posible anular un turno.
     */
    public static String TURNO_NO_ANULADO
            = "No fue posible anular el turno.";

    /**
     * Excepci�n generada cuando no es posible habilitar un turno.
     */
    public static String TURNO_NO_HABILITADO
            = "No fue posible habilitar el turno.";

    /**
     * Excepci�n generada cuando no es posible guardar el motivo por el cual se
     * increment� el secuencial de recibos.
     */
    public static String MOTIVO_SECUENCIAL_RECIBOS_NO_ALMACENADO
            = "No fue posible almacenar el motivo por el cual se incrementa el secuencial de recibos";

    /**
     * Excepci�n generada cuando no es posible Consultar el porcentaje de
     * probabilidad de ir a la fase de revisi�n de calificaci�n.
     */
    public static String PROBABILIDAD_REVISION_CALIFICACION_NO_OBTENIDA
            = "No fue posible obtener la probabilidad de ir a la fase Revisi�n de Calificaci�n";

    /**
     * Excepci�n generada cuando no es posible Consultar el numero de
     * impresiones de certificados en correcciones
     */
    public static String NUMERO_DE_IMPRESIONES_CERTIFICADOS_EN_CORRECCIONES_NO_OBTENIDA
            = "No fue posible obtener el numero de impresiones de certificados en correccion";

    /**
     * Excepci�n generada cuando no es posible Modificar el porcentaje de
     * probabilidad de ir a la fase de revisi�n de calificaci�n.
     */
    public static String PROBABILIDAD_REVISION_CALIFICACION_NO_MODIFICADA
            = "No fue posible modificar la probabilidad de ir a la fase Revisi�n de Calificaci�n";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de solicitudes
     * cuyas liquidaciones no han sido pagadas dentro de una fecha dada.
     */
    public static String LISTA_SOLICITUDES_SIN_TURNOS_ASOCIADOS_NO_OBTENIDA
            = "No fue posible obtener la lista de solicitudes sin turnos asociados.";

    /**
     * Excepci�n generada cuando no es posible eliminar una solicitud sin turnos
     * asociados.
     */
    public static String SOLICITUD_SIN_TURNOS_ASOCIADOS_NO_ELIMINADA
            = "No fue posible eliminar la Solicitud recibida.";
    
    
    public static String LIQUIDACION_SIN_ID_NO_ELIMINADA
        = "No fue posible eliminar la liquidacion anterior.";

    /**
     * Excepci�n generada cuando se valida una matr�cula en mesa de control
     */
    public static String VALIDAR_MATRICULA_MESA_CONTROL
            = "Fallo en la validaci�n de la matr�cula";

    /**
     * Excepcion generada cuando no es posible modificar la categor�a de
     * clasificaci�n de una solicitud de registro.
     */
    public static String CATEGORIA_CLASIFICACION_NO_MODIFICADA = "No fue posible modificar la categor�a de clasificaci�n de la solicitud de registro";

    /**
     * Excepcion generada cuando no es posible registrar un pago.
     */
    public static String PAGO_NO_REGISTRADO = "No fue posible registrar el Pago.";

    /**
     * Excepci�n generada cuando no es posible modificar el n�mero de intentos
     * permitidos en un proceso de impresi�n.
     */
    public static String NUMERO_INTENTOS_IMPRESION_NO_MODIFICADO = "No fue posible modificar el n�mero permitido de intentos de impresi�n";

    /**
     * Excepci�n generada cuando no es posible modificar el tiempo de espera en
     * un proceso de impresi�n.
     */
    public static String TIEMPO_ESPERA_IMPRESION_NO_MODIFICADO = "No fue posible modificar el tiempo de espera en el proceso de impresi�n";

    /**
     * Excepci�n generada cuando no es posible crear un turno de registro.
     */
    public static String TURNO_REGISTRO_NO_CREADO = "No fue posible crear un turno de registro.";

    /**
     * Excepci�n generada cuando no es posible crear un turno de fotocopias
     */
    public static String TURNO_FOTOCOPIAS_NO_CREADO = "No fue posible crear un turno de fotocopias.";

    /**
     * Excepci�n generada cuando no es posible agregar una solicitudFolio a una
     * solicitud Hija
     */
    public static String SOLICITUD_FOLIO_ESTADO_NO_MODIFICADO = "No fue posible modificar el estado de la solicitud folio";

    /**
     * Excepci�n generada cuando no es posible agregar una solicitudFolio a una
     * solicitud Hija
     */
    public static String SOLICITUD_FOLIO_NO_AGREGADA_SOLICITUD_HIJA = "No fue posible agregar una solicitud folio a una solicitud hija";

    /**
     * Excepci�n generada cuando no es posible eliminar una solicitudFolio de
     * una solicitud Hija
     */
    public static String SOLICITUD_FOLIO_NO_ELIMINADA_SOLICITUD_HIJA = "No fue posible eliminar una solicitud folio de una solicitud hija";

    /**
     * Excepci�n generada cuando no es posible modificar el atributo ajuste de
     * una solicitud de registro.
     */
    public static String AJUSTE_REGISTRO_NO_MODIFICADO = "No fue posible modificar el atributo ajuste de la solicitud de registro.";

    /**
     * Excepci�n generada cuando no es posible modificar el atributo ajuste de
     * una solicitud de registro.
     */
    public static String ROTACION_NO_OBTENIDA = "No fue posible obtener los usuarios rotados del c�rculo.";

    /**
     * Excepci�n generada cuando no es posible agregar una liquidaci�n a una
     * solicitud.
     */
    public static String LIQUIDACION_NO_AGREGADA_TURNO = "No fue posible agregar una nueva liquidaci�n";

    /**
     * Excepci�n generada cuando no es posible actualizar un objeto
     */
    public static String NO_ACTUALIZACION = "No fue posible actualizar el objeto";

    /**
     * Excepci�n generada cuando no es posible crear el oficio
     */
    public static String NO_CREACION_OFICIO = "No fue posible crear el oficio";

    /**
     * Excepci�n generada cuando no es posible cobtener el c�digo de lookup
     */
    public static String NO_OBTENCION_DE_LOOKUPCODE = "No fue posible obtener el c�digo especificado";

    /**
     * Excepci�n generada cuando no es posible cobtener el c�digo de lookup
     */
    public static String NO_OBTENCION_DE_OFICIOS = "No fue posible obtener los oficios del turno";

    /**
     * Excepci�n generada cuando no es posible obtener el usuario asociado al
     * turno con el folio indicado
     */
    public static String NO_OBTENCION_USUARIO = "No fue posible obtener el usuario asociado al turno con el folio indicado";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de naturalezas
     * jur�dicas de entidades.
     */
    public static String NATURALEZA_JURIDICA_ENTIDADES_NO_OBTENIDA = "No fue posible obtener el listado de naturalezas jur�dicas de entidades.";

    /**
     * Excepci�n generada cuando no es posible eliminar una naturaleza jur�dica
     * de entidad p�blica.
     */
    public static String NATURALEZA_JURIDICA_ENTIDADES_NO_ELIMINADA = "No fue posible obtener el listado de naturalezas jur�dicas de entidades.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de permisos de
     * correcci�n
     */
    public static String PERMISOS_CORRECCION_NO_OBTENIDOS = "No fue posible obtener el listado de permisos de correcci�n.";

    /**
     * Excepci�n generada cuando no es posible agregar una naturaleza jur�dica
     * de entidad.
     */
    public static String NATURALEZA_JURIDICA_ENTIDAD_NO_AGREGADA = "No fue posible agregar una naturaleza jur�dica de entidad.";

    /**
     * Excepci�n generada cuando no es posible editar una naturaleza jur�dica de
     * entidad.
     */
    public static String NATURALEZA_JURIDICA_ENTIDAD_NO_EDITADA = "No fue posible editar la naturaleza jur�dica de entidad p�blica.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de permisos de
     * correcci�n
     */
    public static String PERMISOS_CORRECCION_NO_ASIGNADOS = "No fue posible asignar los permisos de correcci�n";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de permisos de
     * correcci�n
     */
    public static String NO_REFRESCA_SUBTIPO_ATENCION = "No fue posible refrescar el subtipo de atenci�n del turno";

    /**
     * Excepci�n generada cuando no es posible obtener el identificador de un
     * turno, dado el identificador de una minuta asociada.
     */
    public static String ID_WORKFLOW_BY_ID_MINUTA_NO_OBTENIDO = "No fue posible obtener el identificador de workflow, de un turno asociado con una minuta.";

    /**
     * Excepci�n generada cuando no es posible agregar una entidad p�blica.
     */
    public static String ENTIDAD_PUBLICA_NO_AGREGADA = "No fue posible agregar la entidad p�blica.";

    /**
     * Excepci�n generada cuando no es posible actualizar una entidad p�blica.
     */
    public static String ENTIDAD_PUBLICA_NO_ACTUALIZADA = "No fue posible actualizar la entidad p�blica.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de minutas por
     * repartir dentro de un <code>CirculoNotarial</code>
     */
    public static String MINUTAS_NO_REPARTIDAS_NO_OBTENIDAS = "No fue posible obtener el listado de minutas por repartir";

    /**
     * Excepci�n generada cuando no es posible desactivar una
     * <code>EntidadPublica</code> para reparto notarial.
     */
    public static String ENTIDAD_PUBLICA_NO_ELIMINADA = "No fue posible desactivar la Entidad Publica";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de minutas en
     * las que figura un otorgante.
     */
    public static String MINUTAS_POR_OTORGANTE_NO_OBTENIDAS = "No fue posible obtener el listado de minutas en las que aparece un otorgante.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de minutas
     * repartidas dentro de un rango de fechas.
     */
    public static String MINUTAS_RANGO_FECHAS_NO_OBTENIDAS = "No fue posible obtener el listado de minutas repartidas dentro de un rango de fechas.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de c�rculos
     * notariales, asociados con un c�rculo registral.
     */
    public static String CIRCULOS_NOTARIALES_CIRCULO_REGISTRAL_NO_OBTENIDOS = "No fue posible obtener el listado de c�rculos notariales de un c�rculo registral.";

    /**
     * Excepci�n generada cuando no es posible setear los datos de antiguo
     * sistema a la solicitud
     */
    public static String DATOS_ANTIGUO_SISTEMA_NO_SETEADOS = "No fue posible setear los datos de antiguo sistema a la solicitud";

    /**
     * Excepci�n generada cuando no es posible setear los datos de antiguo
     * sistema a la solicitud
     */
    public static String COLAS_REPARTO_NOTARIAL_NO_OBTENIDAS = "No fue posible obtener las colas por categor�a para reparto notarial";

    /**
     * Excepci�n generada cuando no es posible setear las marcas a los folios
     * asociados al turno
     */
    public static String NO_MARCA_FOLIO = "No fue posible setear las marcas a los folios asociados al turno";

    /**
     * Excepci�n generada cuando no es obtener la lista de alertas
     */
    public static String NO_ALERTAS = "No fue posible obtener la lista de alertas";

    /**
     * Excepci�n generada cuando no es posible obtener el documento de pago
     */
    public static String NO_DOCUMENTO_PAGO = "No fue posible obtener el documento de pago";

    /**
     * Excepci�n generada cuando no es posible decidir si el turno tiene el tipo
     * de acto indicado
     */
    public static String NO_HAS_ACTO_TURNO = "No fue posible decidir si el turno tiene el tipo de acto indicado";

    /**
     * Excepci�n generada cuando se genera un error en la validaci�n de la
     * solicitud vinculada
     */
    public static String NO_VALIDA_SOLICITUD_VINCULADA = "Error en la validaci�n de la solicitud vinculada";

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
     * Excepci�n generada cuando no es posible obtener la lista de intereses
     * jur�dicos.
     */
    public static String LISTA_INTERESES_JURIDICOS_NO_OBTENIDA = "No fue posible obtener la lista de intereses jur�dicos";

    /**
     * Excepci�n generada cuando no es posible obtener la ruta f�sica donde se
     * encuentran las firmas de los registradores.
     */
    public static String RUTA_FIRMAS_NO_ENCONTRADA = "No fue posible encontrar la ruta f�sica de la firma de los registradores";

    /**
     * Excepci�n generada cuando es posible deshacer los cambios de los avanzar
     * push.
     */
    public static String DESHACER_AVANZAR_PUSH_NO_REALIZADO = "No fue posible deshacer el avanzar push.";

    /**
     * Excepci�n generada cuando no es posible obtener el tipo de contenido para
     * las imagenes.
     */
    public static String CONTENT_TYPE_NO_ENCONTRADO = "No fue posible encontrar el tipo de contenido para las im�genes";

    /**
     * Excepci�n generada cuando no es posible obtener la url del servlet de
     * reportes.
     */
    public static String URL_SERVLET_REPORTES_NO_ENCONTRADO = "No fue posible encontrar la url del servlet de reportes";

    /**
     * Excepci�n generada cuando no es posible obtener el n�mero m�ximo de
     * impresiones para los certificados.
     */
    public static String NUMERO_IMPRESIONES_CERTIFICADOS_NO_ENCONTRADO = "No fue posible encontrar el n�mero m�ximo de impresiones para los certificados";

    /**
     * Excepci�n generada cuando no es posible obtener el texto para
     * certificados exentos.
     */
    public static String TEXTO_EXENTO_NO_ENCONTRADO = "No fue posible encontrar el texto para un certificado exento";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de pantallas
     * administrativas asociadas a un rol
     */
    public static String PANTALLAS_ADMINISTRATIVAS_ROL_NO_OBTENIDAS = "No fue posible obtener el listado de pantallas administrativas asociadas con el rol del usuario";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de tipos de
     * pantallas administrativas
     */
    public static String TIPOS_PANTALLAS_ADMINISTRATIVAS_NO_OBTENIDOS = "No fue posible obtener el listado de tipos de pantallas administrativas";

    public static final String NUMERO_RECIBO_PAGO_NO_ESTABLECIDO = "No fue posible establecer el n�mero de recibo para el pago";

    public static final String PAGO_CON_NUMERO_RECIBO_NO_OBTENIDO = "No se pudo obtener el pago con el n�mero de recibo dado";

    public static final String TURNO_HISTORIA_NO_AGREGADO = "No fue posible agregar los datos a la historia del turno";

    public static final String ACTUALIZAR_TURNO_ASOCIADO_NO_EXITOSO = "No fue posible actualizar el turno asociado.";

    public static final String PROCESO_NO_ENCONTRADO = "No se encontr� el proceso";

    /**
     * Excepci�n generada cuando no es posible obtener el n�mero de
     * modificaciones permitidas para la edici�n de minutas de reparto notarial.
     */
    public static final String NUMERO_MODIFICACIONES_MINUTA_NO_OBTENIDO = "No fue posible obtener el n�mero de modificaciones permitido para  la edici�n de minutas";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de turnos
     */
    public static String NO_LISTA_TURNOS = "No fue posible obtener el listado de turnos";

    /**
     * Excepci�n generada cuando no es posible modificar el atributo que
     * almacena el n�mero de modificaciones permitidas para la edici�n de
     * minutas de reparto notarial.
     */
    public static final String NUMERO_MODIFICACIONES_MINUTA_NO_MODIFICADO = "No fue posible actualizar el n�mero de modificaciones permitido para  la edici�n de minutas";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de notar�as
     * pendientes de reparto.
     */
    public static final String NOTARIAS_PENDIENTES_NO_OBTENIDAS = "No fue posible obtener el listado de Notar�as pendientes de Reparto Notarial";

    /**
     * Excepci�n generada cuando no es posible obtener eliminar las notas
     * devolutivas.
     */
    public static final String DEVOLUTIVAS_TURNO_NO_ELIMINADAS = "No fue posible eliminar las notas devolutivas del turno";

    /**
     * Excepci�n generada cuando no es posible obtener el valor de Otro impuesto
     * en las liquidaciones de un turno de registro y sus valores en sus turnos
     * anteriores.
     */
    public static final String OTRO_IMPUESTO_TURNOS_ANTERIORES = "No fue posible encontrar el valor Otro impuesto para el turno.";

    /**
     * Excepci�n generada cuando no es posible obtener el valor del secuencial
     * de la constancia de devoluci�n para un c�rculo dado.
     */
    public static final String SECUENCIAL_DEVOLUCION_NO_OBTENIDO = "No fue posible obtener el secuencial de la constancia de devoluci�n";

    /**
     * Excepci�n generada cuando no es posible obtener el valor del secuencial
     * de la constancia de devoluci�n para un c�rculo dado.
     */
    public static final String SECUENCIAL_MASIVOS_NO_OBTENIDO = "No fue posible obtener el secuencial del recibo de certificados masivos";

    public static final String REPORTE_JASPER_NO_GENERADO = "No fue posible generar el reporte jasper";

    /**
     * Excepci�n generada cuando no es posible modificar el valor de la �ltima
     * liquidaci�n de un turno
     */
    public static String ULTIMA_LIQUIDACION_NO_ACTUALIZADA = "No fue posible modificar el valor de la �ltima liquidaci�n del turno";

    /**
     * Excepci�n generada cuando no es posible agregar una nota de actuaci�n
     * administrativa a la solitidu de un turno.
     */
    public static String ADICION_NOTAS_ACTUACION = "No fue posible agregar notas de actuaci�n al turno.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de items de
     * chequeo para devoluciones.
     */
    public static String ITEMS_CHEQUEO_DEVOLUCIONES_NO_OBTENIDOS = "No fue posible obtener el listado de items de chequeo para devoluciones.";

    /**
     * Excepci�n generada cuando no es posible modificar los atributos de un
     * causal de restituci�n
     */
    public static String CAUSAL_RESTITUCION_NO_MODIFICADO = "No fue posible modificar el causal de restituci�n";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de items de
     * chequeo para devoluciones.
     */
    public static String ULTIMO_TURNO_PROCESO_NO_OBTENIDO = "No fue posible obtener el �ltimo del usuario para el proceso seleccionado.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de items de
     * chequeo para devoluciones.
     */
    public static String ACTUALIZACION_INTENTO_REIMPRESION_NO_EFECTUADO = "No fue posible incrementar el intento de reimpresion.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de turnos de
     * restituci�n asociados con una Minuta.
     */
    public static String LISTADO_TURNOS_RESTITUCION_MINUTA_NO_OBTENIDO = "No fue posible obtener el listado de turnos de restituci�n asociados con una Minuta";

    /**
     * Excepci�n generada cuando no es posible agregar un circulo notarial.
     */
    public static String AGREGAR_CIRCULO_NOTARIAL_NO_EFECTUADO = "No fue posible agregar el c�rculo notarial";

    /**
     * Excepci�n generada cuando no es posible eliminar un circulo notarial.
     */
    public static String ELIMINAR_CIRCULO_NOTARIAL_NO_EFECTUADO = "No fue posible eliminar el c�rculo notarial";

    /**
     * Excepci�n generada cuando no es posible editar un circulo notarial.
     */
    public static String EDITAR_CIRCULO_NOTARIAL_NO_EFECTUADO = "No fue posible editar el c�rculo notarial";

    /**
     * Excepci�n generada cuando no es posible consultar un circulo notarial.
     */
    public static String CIRCULO_NOTARIAL_NO_OBTENIDO = "No fue posible consultar el c�rculo notarial";

    /**
     * Excepci�n generada cuando no es posible agregar una zona notarial.
     */
    public static String AGREGAR_ZONA_NOTARIAL_NO_EFECTUADO = "No fue posible agregar la zona notarial";

    /**
     * Excepci�n generada cuando no es posible eliminar una zona notarial.
     */
    public static String ELIMINAR_ZONA_NOTARIAL_NO_EFECTUADO = "No fue posible eliminar la zona notarial";

    /**
     * Excepci�n generada cuando no es posible crear la operaci�n de englobe o
     * segregaci�n.
     */
    public static String WEB_OPERACION_CREAR_NO_EFECTUADA = "No fue posible crear la operaci�n de englobe o segregaci�n";

    /**
     * Excepci�n generada cuando no es posible eliminar la operaci�n de englobe
     * o segregaci�n.
     */
    public static String WEB_OPERACION_ELIMINAR_NO_EFECTUADA = "No fue posible eliminar la operaci�n de englobe o segregaci�n";

    /**
     * Excepci�n generada cuando no es posible actualizar la operaci�n de
     * englobe o segregaci�n.
     */
    public static String WEB_OPERACION_ACTUALIZAR_NO_EFECTUADA = "No fue posible actualizar la operaci�n de englobe o segregaci�n";

    /**
     * Excepci�n generada cuando no es posible consultar la operaci�n de englobe
     * o segregaci�n.
     */
    public static String WEB_OPERACION_CONSULTAR_NO_EFECTUADA = "No fue posible consultar la operaci�n de englobe o segregaci�n";

    /**
     * Excepci�n generada cuando no es posible agregar una notar�a al reparto.
     */
    public static String AGREGAR_NOTARIA_REPARTO_NO_EFECTUADO = "No fue posible agregar la notar�a al reparto";

    /**
     * Excepci�n generada cuando no es posible obtener el texto del imprimible.
     */
    public static String TEXTO_IMPRIMIBLE_NO_OBTENIDO = "No fue posible obtener el texto del imprimible";

    /**
     * Excepci�n generada cuando no es posible actualizar la marca de
     * revocatorias.
     */
    public static String ACTUALIZAR_MARCA_REVOCATORIA_NO_EFECTUADA = "No fue posible actualizar la marca de revocatorias para el turno";

    /**
     * Excepci�n generada cuando no es reanotar un turno de registro.
     */
    public static String REANOTAR_TURNO_NO_EFECTUADO = "No fue posible reanotar el turno";

    /**
     * Excepci�n generada cuando es posible consultar los tunrnos a reanotar.
     */
    public static String CONSULTAR_TURNOS_A_REANOTAR_NO_EFECTUADO = "No fue posible encontrar los turnos que han sido blqueados para ser reanotados";

    /**
     * Excepci�n generada cuando es posible actualizar el �ltimo turno historia.
     */
    public static String ACTUALIZAR_LAST_TURNO_HISTORIA = "No fue posible actualizar el �ltimo turno historia";

    /**
     * Excepci�n generada cuando es posible actualizar la informaci�n de
     * Reimpresi�n del Tuerno
     */
    public static String ACTUALIZAR_TURNO_INFO_IMPRESION = "No fue posible actualizar la informaci�n de Reimpresi�n del Turno";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * tarifas.
     *
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public static String LISTA_TIPOS_HORARIO_NOTARIAL_NO_OBTENIDA = "No fue posible obtener la lista de Tipos de Horario de Reparto";

    /**
     * Excepci�n generada cuando no es posible obtener la lista de tipos de
     * tarifas.
     *
     * @see gov.sir.core.negocio.modelo.TipoTarifa
     */
    public static String HORARIOS_NOTARIALES_CIRCULO_POR_TIPO_NO_OBTENIDOS = "No fue posible obtener los horarios notariales para el c�rculo";

    public static String HORARIO_NOTARIAL_NO_AGREGADO = "No fue posible agregar el horario notarial";

    public static String HORARIO_NOTARIAL_NO_ELIMINADO = "No fue posible eliminar el horario notarial";

    public static String HORARIO_NOTARIAL_NO_EDITADO = "No fue posible editar el horario notarial";

    /**
     * Excepci�n generada cuando no es posible consultar una relaci�n por el id
     * de la relaci�n.
     */
    public static String CONSULTA_RELACION_EXCEPTION = "No fue posible consultar la relaci�n por el n�mero de relaci�n asociado";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String CONSULTA_TURNO_FOLIO_NO_EFECTUADA = "No fue posible consultar los folios asociados al turno del sistema FOLIO";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String CONSULTA_TURNO_FOLIO_NO_EFECTUADA_BY_FOLIO = "No fue posible consultar si el folio tiene turnos asociados en el sistema FOLIO";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String CONSULTA_TURNO_FOLIO_NO_EFECTUADA_BY_FOLIO_MIG = "No fue posible consultar si el folio es valido para asociar en el proceso de Migraci�n";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_SOLICITUD_FOLIO
     */
    public static String CONSULTA_SOLICITUD_FOLIO_MIG_NO_EFECTUADA = "No fue posible consultar los folios asociados a la solicitud del sistema FOLIO";

    /**
     * Excepci�n generada cuando no es posible adicionar registros a la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String ADICION_TURNO_FOLIO_NO_EFECTUADA = "No fue posible agregar los folios asociados al turno del sistema FOLIO";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_MIG_REL_TURNO_FOLIO
     */
    public static String ELIMINACION_SOLICITUD_FOLIO_MIG_NO_EFECTUADA = "No fue posible eliminar los folios asociados a la solicitud del sistema FOLIO";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_MIG_TRAMITE_TURNO_FOLIO
     */
    public static String CONSULTA_TRAMITE_TURNO_FOLIO_NO_EFECTUADA = "No fue posible consultar los folios en tramite asociados al turno del sistema FOLIO";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_MIG_TRAMITE_TURNO_FOLIO
     */
    public static String CONSULTA_TRAMITE_TURNO_FOLIO_NO_EFECTUADA_BY_FOLIO = "No fue posible consultar si el folio tiene turnos en tramite en el sistema FOLIO";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_MIG_TRAMITE_TURNO_FOLIO
     */
    public static String CONSULTA_TURNO_FOLIO_TRAMITE_NO_EFECTUADA = "No fue posible verificar los folios en tramite para el turno en el sistema FOLIO";

    /**
     * Excepci�n generada cuando no es posible insertar los registros en la
     * tabla SIR_MIG_REL_TURNO_FOLIO
     */
    public static String CREACION_TURNO_FOLIO_MIG_NO_EFECTUADA = "No fue posible agregar los folios asociados del turno anterior.";

    /**
     * Excepci�n generada cuando no es posible insertar los registros en la
     * tabla SIR_MIG_TRAMITE_TURNO_FOLIO
     */
    public static String CREACION_TRAMITE_TURNO_FOLIO_MIG_NO_EFECTUADA = "No fue posible asociar los folios al turno en tr�mite.";

    /**
     * Excepci�n generada cuando no es posible obtener el listado de c�rculos
     * notariales, asociados con un c�rculo registral.
     */
    public static String CONSULTA_SOLICITUDES_TURNO_ANTERIOR_NO_EFECTUADA = "No fue posible obtener el listado de solicitudes por turno anterior.";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_OP_PANTALLA_ADMINISTRATIVA
     */
    public static String CONSULTA_PANTALLA_ADMINISTRATIVA_NO_EFECTUADA_BY_PAGINA = "No fue posible consultar las pantallas administrativas por p�gina";

    /**
     * Excepci�n generada cuando no es posible consultar la tabla
     * SIR_NE_CATEGORIA_NOTARIA
     */
    public static String CONSULTA_CATEGORIAS_NOTARIAS_NO_EFECTUADA = "No fue posible consultar la lista de Categor�as a las que pertenecen las Notar�as";

    /**
     * Excepci�n generada cuando no es posible insertar en SIR_OP_ROL_PANTALLA
     */
    public static String ADD_ROL_PANTALLA_ADMINISTRATIVA_NO_EFECTUADO = "No fue posible agregar la pantalla al rol";

    /**
     * Excepci�n generada cuando no es posible eliminar de SIR_OP_ROL_PANTALLA
     */
    public static String DELETE_ROL_PANTALLA_ADMINISTRATIVA_NO_EFECTUADO = "No fue posible eliminar la pantalla al rol";

    /**
     * Excepci�n generada cuando no es posible cambiar la respuesta del �ltimo
     * TurnoHistoria con fase fase
     */
    public static String UPDATE_RESPUESTA_LAST_TURNO_HISTORIA_NO_EFECTUADO = "No fue posible eliminar la pantalla al rol";

    /**
     * Excepci�n generada cuando no fue posible eliminar la relacion del turno
     */
    public static String RELACION_NO_ELIMINADA = "No fue posible eliminar la relacion del turno";

    /**
     * Excepci�n generada cuando no fue posible actualizar el valor de la
     * liquidacion
     */
    public static String VALOR_LIQUIDACION_DEVOLUCION_NO_ACTUALIZADA = "No fue posible actualizar el valor de la liquidacion de devoluciones";

    /**
     * Excepci�n generada cuando no se pudieron eliminar las notas informativas
     * asociadas al turno en su fase actual
     */
    public static String NOTAS_INFORMATIVAS_NO_ELIMINADAS = "No fue posible eliminar las notas informativas asociadas a la fase del turno";

    /**
     * Excepci�n generado cuando no se pudo cambiar la marca de turno nacional
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

    public static String TESTADOR_NO_ELIMINADO = "No se elimin�el testador";

    public static String SOLICITUD_NO_ACTUALIZADA = "Error actualizando solicitud";

    /**
     * Excepci�n generada cuando no es posible obtener el documento de pago
     */
    public static String DOCUMENTO_PAGO_NO_VALIDO = "El documento pago no es v�lido";

    public static String BANCO_CIRCULO_NO_CREADO = "No fue posible crear relaci�n banco c�rculo";

    public static String BANCOS_X_CIRCULO_NO_OBTENIDA = "Lista de bancos por c�rculo no obtenida";

    public static String BANCO_CIRCULO_NO_ELIMINADO = "No fue posible eliminar relaci�n banco c�rculo";

    /**
     * Excepci�n generada cuando no es posible obtener eliminar las notas
     * devolutivas.
     */
    public static final String NOTA_DEVOLUTIVAS_TURNO_NO_ELIMINADS = "No fue posible eliminar la nota devolutiva del turno";


    /*
         * @author      : Julio Alc�zar Rivas
         * @change      : Cadena de exception para metodo getCambioMatriculaAuditoria(TurnoPk tID)
         * Caso Mantis  :   02359
     */
    public static final String TURNO_NO_ENCONTRADO = "No fue posible obtener informaci�n del turno";

    /*
         * @author      : Julio Alc�zar Rivas
         * @change      : Cadena de exception para metodo getTurnosDevolucion(Turno turno)
         * Caso Mantis  : 07393
     */
    public static String TURNOS_DEVOLUCION_NO_OBTENIDOS = "No fue posible obtener listado de turnos de devoluci�n";
    /**
     * @author: Diana Lora
     * @Change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes
     * Reparto Notarial
     */
    public static final String SECUENCIAL_REPARTO_NOTARIAL_NO_OBTENIDO = "No fue posible obtener el secuencial de recibo correspondiente al proceso de reparto notarial.";

    /*
         * @author      : Geremias Ortiz Lozanop
         * @change      : Excepcion creaci�n cuenta bancaria
     */
    public static String CUENTA_BANCARIA_NO_CREADA = "No fue posible crear relaci�n c�rculo - banco - cuenta";

    public static String CUENTAS_BANCARIAS_NO_OBTENIDAS = "Lista de cuentas bancarias no obtenidas";

    public static String FALLO_ESTADO_CUENTA_BANCARIA = "Fallo servicio de actualizaci�n de estados";
    
    public static String FALLO_ESTADO_CANAL_RECAUDO = "Fallo servicio de actualizaci�n de estados";
    
    public static String FALLO_ESTADO_CTP = "Fallo servicio de actualizaci�n de estados";
    
    public static String CANAL_RECAUDO_NO_ENCONTRADO = "Fall� servicio de busqueda de canal de recaudo";
    
    public static String TIPO_PAGO_NO_ENCONTRADO = "Fall� servicio de busqueda de tipo de pago";
    
    public static String CIRCULO_TIPO_PAGO_NO_ENCONTRADO = "Fall� servicio de busqueda de circulo tipo de pago";
    
    public static String CUENTA_BANCARIA_NO_ENCONTRADA = "Fall� servicio de busqueda de cuenta bancaria";
    /**
     * Excepci�n generada cuando no es posible obtener la lista de canales.
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
