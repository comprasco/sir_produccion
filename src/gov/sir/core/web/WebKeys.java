package gov.sir.core.web;

/**
 * Esta clase contiene variables publicas estaticas y finales por medio de las
 * cuales se almacena informacion en la sesion web del usuario.
 *
 * @author mmunoz, dsalas, dcantor
 */
public final class WebKeys {

    //SIR 57 R
    public static final String TURNO_REL = "1";
    
    public static final String AGREGAR_MATRICULA_NOTA = "AGREGAR_MATRICULA_NOTA";
    
    public static final String ELIMINAR_MATRICULA_NOTA = "ELIMINAR_MATRICULA_NOTA";
    /**
     * Constante que identifica la lista del control de matriculas de un turno.
     */
    public static final String LISTA_CONTROL_MATRICULA = "LISTA_CONTROL_MATRICULA";
    
    /**
     * Constante que identifica si el usuario tiene los permisos para reasignar turnos.
     */
    public static final String PERMITIR_REASIGNACION = "PERMITIR_REASIGNACION";
    
    /**
     * Constante que identifica el numero de matriculas a agregar en liquidador;
     */
    public static final String NUM_MATRICULAS_LIQUIDADOR = "NUM_MATRICULAS_LIQUIDADOR";

    /**
     * Constante que identifica el numero de notas anterior.
     */
    public static final String NUM_NOTAS_ANT = "NUM_NOTAS_ANT";
            
    /**
     * Esta constatnte almacena si un folio tiene datos temporales.
     */
    public static final String TIENE_ANOTACIONES_TMP = "TIENE_ANOTACIONES_TMP";
    /**
     * Esta constante almacena el historial de areas
     */
    public static final String HISTORIAL_AREAS = "HISTORIAL_AREAS";
    
    /**
     * Esta constante almacena si la radicación es de REL o de SIR
     */
    public static final String ORIGEN_REL = "ORIGEN_REL";
    /**
     * Esta constante identifica el tipo de consulta de turno
     */
    public static final String REGRESAR_TURNO = "REGRESAR_TURNO";
    
    /**
     * Esta constante identifica si el certificado es especial.
     */
    public static final String IS_CERTIFICADO_ESPECIAL = "IS_CERTIFICADO_ESPECIAL";
    
    /**
     * Esta constante si el certificado esta en tramite especial.
     */
    public static final String IS_CERTIFICADO_TRAMITE = "IS_CERTIFICADO_TRAMITE";
    
    /**
     * Esta constante identifica un certificado especial por actuación administrativa.
     */
    public static final String IS_CERTIFICADO_ACTUACION = "IS_CERTIFICADO_ACTUACION";
    
    /**
     * Esta constante identifica el turno el cual un folio está en tramite.
     */
    public static final String TURNO_TRAMITE_FOLIO = "TURNO_TRAMITE_FOLIO";
    /**
     * Esta constante identifica el nombre del formulario
     * para el campo de captura de BANCO/FRANQUICIA
     */
    public static final String CAMPO_BANCO_FRANQUICIA = "BANCO_FRANQUICIA";
    
    /**
     * Esta constante identifica el nombre del formulario
     * para el campo de captura de NO_CONSIGNACION
     */
    public static final String CAMPO_NO_CONSIGNACION= "NO_CONSIGNACION";
    
    /**
     * Esta constante identifica la lista de las formas de pago para un 
     * canal y circulo en especifico
     */
    public static final String LISTA_FORMAS_PAGO_CANAL = "LISTA_FORMAS_PAGO_CANAL";

    /**
     * Esta constante identifica el tipo de certificado 3934
     */
    public static final String CERTIFICADO_TIPO = "CERTIFICADO_TIPO";

    /**
     * Esta constante identifica las pantallas simplificadas
     */
    public static final String SIMPLIFICADO = "SIMPLIFICADO";

    /**
     * Esta constante identifica a accion que el usuario a seleccionado y esta
     * guardada en el request
     */
    public static final String ACCION = "ACCION";

    /**
     * Esta constante identifica que se debe actualizar informacion
     */
    public static final String ACTUALIZAR = "ACTUALIZAR";

    /**
     * Esta variable identifica el objeto Usuario que esta guardado en la sesion
     */
    public static final String USUARIO = "USUARIO";

    /**
     * Constante para identificar el valor de redirecciï¿½n.do
     */
    public static final String REDIRECCION_DO = "REDIRECCION_DO";

    /**
     * Esta variable identifica el objeto Usuario de auriga que esta guardado en
     * la sesion
     */
    public static final String USUARIO_AURIGA = "USUARIO_AURIGA";

    /**
     * Esta constante identifica el objeto con la lista de las estaciones que
     * tiene el usuario que esta guardado en la sesion
     */
    public static final String LISTA_ESTACIONES = "LISTA_ESTACIONES";

    /**
     * Esta constante identifica el objeto Estacion que esta guardado en la sesion
     */
    public static final String ESTACION = "ESTACION";

    /**
     * Esta constante identifica el objeto EstacionRecibo que esta guardado en
     * la sesion
     */
    public static final String ESTACION_RECIBO = "ESTACION_RECIBO";

    /**
     * Esta constante identifica si hay excepcion en el jsp
     */
    public static final String HAY_EXCEPCION = "HAY_EXCEPCION";

    /**
     * Esta constante identifica la razon de la excepcion ocurrida en el jsp
     */
    public static final String RAZON_EXCEPCION = "RAZON_EXCEPCION";

    /**
     * Esta constante identifica la lista de usuarios de una estacion
     */
    public static final String ESTACION_USUARIOS = "ESTACION_USUARIOS";

    /**
     * Esta constante identifica la lista de usuarios potenciales de una
     * estacion
     */
    public static final String ESTACION_USUARIOS_POTENCIALES = "ESTACION_USUARIOS_POTENCIALES";

    /**
     * Esta constante identifica la lista de roles de una estacion
     */
    public static final String ESTACION_ROLES = "ESTACION_ROLES";

    /**
     * Esta constante identifica la lista de roles potenciales de una estacion
     */
    public static final String ESTACION_ROLES_POTENCIALES = "ESTACION_ROLES_POTENCIALES";

    /**
     * Esta constante identifica la lista de roles que se va a aï¿½adir a la
     * estacion
     */
    public static final String NEW_ROLES_ESTACION = "NEW_ROLES_ESTACION";

    /**
     * Esta constante identifica la lista de roles que se va a retirar de la
     * estacion
     */
    public static final String EX_ROLES_ESTACION = "EX_ROLES_ESTACION";

    /**
     * Esta constante identifica la lista de usuarios que se va a aï¿½adir a la
     * estacion
     */
    public static final String NEW_USUARIOS_ESTACION = "NEW_USUARIOS_ESTACION";

    /**
     * Esta constante identifica la lista de usuarios que se a retirar de la
     * estacion
     */
    public static final String EX_USUARIOS_ESTACION = "EX_USUARIOS_ESTACION";

    /**
     * Esta variable identifica el objeto Rol que esta guardado en la sesion
     */
    public static final String ROL = "ROL";

    /**
     * Esta variable se utiliza para guardar el rol que se utiliza en las
     * transacciones de administracion de perfiles
     */
    public static final String ROL_PERFIL = "ROL_PERFIL";

    /**
     * Esta variable identifica el objeto Rol-Estacion que esta guardado en la sesion
     */
    public static final String ROL_ESTACION = "ROL_ESTACION";
    /**
     * Esta variable identifica el objeto Rol-Estacion que esta guardado en la sesion
     */
    public static final String SESSION_ROL_ESTACION = "SESSION_ROL_ESTACION";

    /**
     * Esta variable identifica el objeto Rol-Estacion-Estado que esta guardado
     * en la sesion
     */
    public static final String ROL_ESTACION_ESTADO = "ROL_ESTACION_ESTADO";
    /**
     * Esta variable identifica el objeto Rol-Estacion-Estado que esta guardado
     * en la sesion
     */
    public static final String SESSION_ROL_ESTACION_ESTADO = "SESSION_ROL_ESTACION_ESTADO";

    /**
     * Esta constante identifica el objeto circulo que se encuentra guardado en
     * la sesion
     */
    public static final String CIRCULO = "CIRCULO";

    /**
     * Esta constante identifica el objeto circulo que se encuentra guardado en
     * la sesion
     */
    public static final String CIRCULO_PERFILES = "CIRCULO_PERFILES";

    /**
     * Esta variable identifica el objeto con la lista de las fases que tiene el
     * usuario que esta guardado en la sesion
     */
    public static final String LISTA_ROLES = "LISTA_ROLES";
    public static final String ImprimirCC ="ImprimirCC";
    /**
     * Esta variable identifica el objeto con la lista de los procesos que tiene
     * el usuario que esta guardado en la sesion
     */
    public static final String LISTA_PROCESOS = "LISTA_PROCESOS";

    /**
     * Esta variable identifica el objeto con la lista de los procesos que
     * puesde iniciar el usuario que esta guardado en la sesion
     */
    public static final String LISTA_PROCESOS_INICIABLES = "LISTA_PROCESOS_INICIABLES";

    /**
     * Esta variable identifica el objeto con la lista de las fases que tiene el
     * usuario que esta guardado en la sesion
     */
    public static final String LISTA_FASES = "LISTA_FASES";

    /**
     * Esta variable identifica el objeto con la lista de las estaciones que
     * corresponden al cï¿½rculo registral actual
     */
    public static final String LISTA_ESTACIONES_CIRCULO = "LISTA_ESTACIONES_CIRCULO";

    /**
     * Esta variable identifica el objeto con la lista de los turnos que que
     * pertenecen a una relaciï¿½n.
     */
    public static final String LISTA_TURNOS_RELACION = "LISTA_TURNOS_RELACION";

    /**
     * Esta variable identifica el objeto con la lista de los turnos que que
     * pertenecen a una relaciï¿½n. Usada para pantallas administrativas
     */
    public static final String LISTA_TURNOS_RELACION_DETALLE = "LISTA_TURNOS_RELACION_DETALLE";

    /**
     * Esta variable identifica el objeto con la lista de los turnos de
     * certificados asociados que son vï¿½lidos para impresiï¿½n.
     */
    public static final String LISTA_TURNOS_VALIDOS = "LISTA_TURNOS_VALIDOS";

    /**
     * Esta variable identifica la posiciï¿½n en una lista
     */
    public static final String POSICION = "POSICION";

    /**
     * Esta variable identifica la posiciï¿½n a editar en una lista llevada a
     * session en una lista
     */
    public static final String POSICION_EDIT_TEMP = "POSICION_EDIT_TEMP";

    public static final String POSICION_EDIT_DEF = "POSICION_EDIT_DEF";

    /**
     * Esta variable identifica un parametro
     */
    public static final String PARAMETRO = "PARAMETRO";

    /**
     * Esta variable identifica un mensaje
     */
    public static final String MENSAJE = "MENSAJE";

    /**
     * Esta variable identifica el objeto Proceso que esta guardado en la sesion
     */
    public static final String PROCESO = "PROCESO";

    /**
     * Esta variable identifica el objeto que esta escuchando cuando la sesion
     * expira para mandar el evento de seguridad con el log out
     */
    public static final String LISTENER = "LISTENER";

    /**
     * Esta variable identifica el objeto con la lista de los turnos que tiene
     * el usuario que esta guardado en la sesion
     */
    public static final String LISTA_TURNOS = "LISTA_TURNOS";

    /**
     * Esta variable identifica el objeto con la lista de los turnos que tiene
     * el usuario y que tienen folios o turnos en tramite en el sistema FOLIO
     */
    public static final String LISTA_TURNOS_SIR_MIG = "LISTA_TURNOS_SIR_MIG";

    public static final String LISTA_TURNOS_PARCIAL = "LISTA_TURNOS_PARCIAL";

    /**
     * Esta variable identifica el objeto Fase que esta guardado en la sesion
     */
    public static final String FASE = "FASE";

    /**
     * Esta constante identifica la liquidaciï¿½n de un certificado
     */
    public static final String LIQUIDACION = "LIQUIDACION";
    
    /**
     * Esta constante identifica el listado de cananales por circulo
     */
    public static final String LISTA_CANALES_RECAUDO_CIRCULO = "LISTA_CANALES_RECAUDO_CIRCULO";
    

    /**
     * Esta constante identifica el atributo del objeto HttpServletRequest que
     * almacena un objeto ciudadano
     */
    public static final String CIUDADANO = "CIUDADANO";

    /**
     * Esta constante identifica el parï¿½metro del objeto HttpServletRequest que
     * almacena las diferentes formas de pago para un certificado
     */
    public static final String LISTA_TIPOS_CERTIFICADOS = "LISTA_TIPOS_CERTIFICADOS";

    /**
     * Esta constante identifica la forma de pago en Efectivo y Cheques, que
     * estarï¿½ en el dominio de valores posibles para el parï¿½metro FORMA_PAGO del
     * objeto HttpServletRequest
     */
    public static final String FORMA_PAGO_CHEQUE = "CHEQUE";

    /**
     * Esta constante identifica la forma de pago en Consignaciï¿½n, que estarï¿½ en
     * el dominio de valores posibles para el parï¿½metro FORMA_PAGO del objeto
     * HttpServletRequest
     */
    public static final String FORMA_PAGO_CONSIGNACION = "CONSIGNACION";

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Se crean las constantes necesarias para dar soluciï¿½n al
     * requerimiento.
     */
    public static final String FORMA_PAGO_TARJETA_CREDITO = "TARJETA_CREDITO";
    public static final String FORMA_PAGO_TARJETA_DEBITO = "TARJETA_DEBITO";
    public static final String FORMA_PAGO_PSE = "PSE";
    public static final String FORMA_PAGO_CONVENIO = "CONVENIO";

    /**
     * Constante de forma de pago de recibo con timbre del banco
     */
    public static final String FORMA_PAGO_TIMBRE_BANCO = "FORMA_PAGO_TIMBRE_BANCO";

    /**
     * Constante de pago de registro para indicar el boolean si se debe mostar
     * la forma de pago de timbre
     */
    public static final String PAGO_REGISTRO_LIQUIDACION = "PAGO_REGISTRO_LIQUIDACION";
    
    public static final String SWITCH_ON = "1";
    
    public static final String SWITCH_OFF = "0";

    /**
     * Utilizada para procesar condicionalmente el pago de una fotocopia sin
     * avance de turno
     *
     */
    public static final String PAGO_FOTOCOPIAS_ACTIVE = "PAGO_FOTOCOPIAS_ACTIVE";

    /**
     * Esta constante identifica el parï¿½metro del objeto HttpServletRequest que
     * almacena la forma de pago invalida
     */
    public static final String SIN_SELECCIONAR = "SIN_SELECCIONAR";

    /**
     * Esta constante identifica el atributo del objeto HttpServletRequest que
     * almacena el recibo de pago en formato PDF para una solicitud
     */
    public static final String MAPA_PARAMETROS = "RECIBO_PDF";

    /**
     * Esta constante identifica la forma de pago en EFECTIVO, que estarï¿½ en el
     * dominio de valores posibles para el parï¿½metro FORMA_PAGO del objeto
     * HttpServletRequest
     */
    public static final String FORMA_PAGO_EFECTIVO = "EFECTIVO";

    /**
     * Esta constante identifica el atributo del objeto HttpServletRequest que
     * almacena los datos con respecto a los datos de pago del certificado
     */
    public static final String LISTA_CHEQUES = "LISTA_CHEQUES";

    /**
     * Esta constante identifica el atributo del objeto HttpServletRequest que
     * almacena los datos con respecto a los datos de pago del certificado
     */
    public static final String LISTA_CONSIGNACIONES = "LISTA_CONSIGNACIONES";
    
    
    public static final String LISTA_GENERAL = "LISTA_GENERAL";
    
    
    public static final String LISTA_PAGOS_REGISTRADOS = "LISTA_PAGOS_REGISTRADOS";
    
    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Se crean las constantes necesarias para dar soluciï¿½n al
     * requerimiento.
     */
    public static final String LISTA_TARJETA_CREDITO = "LISTA_TARJETA_CREDITO";
    public static final String LISTA_TARJETA_DEBITO = "LISTA_TARJETA_DEBITO";
    //public static final String LISTA_PSE = "LISTA_PSE";
    /**
     * Esta constante guarda la aplicacion en convenio para el registro de pago
     */
    public static final String APLICACION_CONVENIO = "APLICACION_CONVENIO";
    public static final String APLICACION_PAGO_ELECTRONICO_PSE = "APLICACION_PAGO_ELECTRONICO_PSE";
    //public static final String LISTA_CONVENIO = "LISTA_CONVENIO";
    public static final String LISTA_BANCOS_FRANQUICIAS = "LISTA_BANCOS_FRANQUICIAS";

    /**
     * Esta constante identifica el parï¿½metro del objeto HttpServletRequest que
     * almacena la posicion de una aplicacion a eliminar
     */
    public static final String PAGO = "PAGO";

    /**
     * Esta constante identifica el parï¿½metro del objeto HttpServletRequest que
     * almacena la posicion de una aplicacion a eliminar
     */
    public static final String PRECISION_PAGO = "PRECISION_PAGO";

    /**
     * Esta constante identifica el parï¿½metro del objeto HttpSession que
     * almacena la lista de objetos Banco
     */
    public static final String LISTA_BANCOS = "LISTA_BANCOS";
        
    /**
     * Esta constante identifica el parï¿½metro del objeto HttpSession que
     * almacena la lista de objetos CanalesRecaudo
     */
    public static final String LISTA_CANALES_RECAUDO = "LISTA_CANALES_RECAUDO";
    
    public static final String LISTA_CANALES_RECAUDO_ACTIVOS = "LISTA_CANALES_RECAUDO_ACTIVOS";
    
   

    public static final String LISTA_RELACION_BANCOS_CIRCULO = "LISTA_RELACION_BANCOS_CIRCULO";

    /**
     * Esta constante guarda la aplicacion en efectivo para el registro de pago
     */
    public static final String APLICACION_EFECTIVO = "APLICACION_EFECTIVO";
    
    /**
     * Esta constante guarda la aplicacion para el registro de pago por cualquier forma
     */
    public static final String APLICACION_PAGO = "APLICACION_PAGO";

    /**
     * Esta constante guarda las anotaciones que tiene un folio de manera
     * temporal antes de ser creado
     */
    public static final String LISTA_ANOTACIONES_FOLIO = "LISTA_ANOTACIONES_FOLIO";

    /**
     * Esta constante guarda las anotaciones que tiene un folio de manera
     * definitivia el tamaï¿½o varia segun el tamaï¿½o del cache
     */
    public static final String LISTA_ANOTACIONES_DEFINITIVAS_FOLIO = "LISTA_ANOTACIONES_DEFINITIVAS_FOLIO";

    /**
     * Esta constante guarda las anotaciones que tiene un folio de manera
     * definitiva que se va mostrar en la pagain respectiva
     */
    public static final String LISTA_ANOTACIONES_DEFINITIVAS_PAGINA = "LISTA_ANOTACIONES_DEFINITIVAS_PAGINA";

    /**
     * Esta constante guarda las anotaciones que tiene un folio de manera
     * temporal antes de ser creado
     */
    public static final String LISTA_ANOTACIONES_TEMPORALES_FOLIO = "LISTA_ANOTACIONES_TEMPORALES_FOLIO";

    /**
     * Esta constante guarda una lista con los id_maricula de los folios padre.
     */
    public static final String LISTA_FOLIOS_PADRE = "LISTA_FOLIOS_PADRE";

    /**
     * Esta constante guarda una lista con los id_maricula de los folios hijo
     */
    public static final String LISTA_FOLIOS_HIJO = "LISTA_FOLIOS_HIJO";

    /**
     * Esta constante guarda una lista de los objetos FolioDerivado de los
     * folios padre.
     */
    public static final String LISTA_FOLIOS_DERIVADO_PADRE = "LISTA_FOLIOS_DERIVADO_PADRE";

    /**
     * Esta constante guarda una lista de los objetos FolioDerivado d los folios hijo
     */
    public static final String LISTA_FOLIOS_DERIVADO_HIJO = "LISTA_FOLIOS_DERIVADO_HIJO";

    /**
     * Esta constante guarda una lista de objetos TurnoFolioTramiteMig con los
     * turnos en tramite para un folio
     */
    public static final String LISTA_TURNOS_FOLIO_TRAMITE = "LISTA_TURNOS_FOLIO_TRAMITE";

    /**
     * Esta constante guarda las anotaciones que tienen gravamenes
     */
    public static final String LISTA_ANOTACIONES_GRAVAMEN = "LISTA_ANOTACIONES_GRAVAMEN";

    /**
     * Esta constante guarda las anotaciones que tienen medidas cautelares
     */
    public static final String LISTA_ANOTACIONES_MEDIDAS_CAUTELARES = "LISTA_ANOTACIONES_MEDIDAS_CAUTELARES";

    /**
     * Esta constante guarda las anotaciones que tienen falsa tradiciï¿½n
     */
    public static final String LISTA_ANOTACIONES_FALSA_TRADICION = "LISTA_ANOTACIONES_FALSA_TRADICION";

    /**
     * Esta constante guarda las anotaciones que tienen anotaciones invï¿½lidas
     */
    public static final String LISTA_ANOTACIONES_INVALIDAS = "LISTA_ANOTACIONES_INVALIDAS";

    /**
     * Esta constante guarda las anotaciones que tienen anotaciones invï¿½lidas
     */
    public static final String LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR = "LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR";

    /**
     * Esta constante guarda las anotaciones que tienen anotaciones invï¿½lidas
     */
    public static final String LISTA_ANOTACIONES_AFECTACION_VIVIENDA = "LISTA_ANOTACIONES_AFECTACION_VIVIENDA";

    /**
     * Esta constante guarda las anotaciones que tienen salvedades
     */
    public static final String LISTA_SALVEDADES_ANOTACIONES = "LISTA_SALVEDADES_ANOTACIONES";

    /**
     * Esta constante guarda las anotaciones que son tienen canceladoras de otras
     */
    public static final String LISTA_ANOTACIONES_CANCELACION = "LISTA_ANOTACIONES_CANCELACION";

    /**
     * Esta constante guarda un conjunto de anotaciones durante la acciï¿½n de segregaciï¿½n
     */
    public static final String LISTA_ANOTACIONES_SEGREGACION = "LISTA_ANOTACIONES_SEGREGACION";

    /**
     * Esta constante guarda los ciudadanos que se estan ingresando en una anotacion
     */
    public static final String LISTA_CIUDADANOS_ANOTACION = "LISTA_CIUDADANOS_ANOTACION";

    /**
     * Esta constante guarda los ejes que se pueden tener para las direcciones
     */
    public static final String LISTA_EJES_DIRECCION = "LISTA_EJES_DIRECCION";

    /**
     * Esta constante guarda la lista de los tipos de identificacion del ciudadano
     */
    public static final String LISTA_TIPOS_ID = "LISTA_TIPOS_ID";
    
    /**
     * Esta constante guarda la lista de los tipos de identificacion de las personas naturales
     */
    public static final String LISTA_TIPOS_ID_NATURAL = "LISTA_TIPOS_ID_NATURAL";
    
    /**
     * Esta constante guarda la lista de los tipos de identificacion de las personas juridicas
     */
    public static final String LISTA_TIPOS_ID_JURIDICA = "LISTA_TIPOS_ID_JURIDICA";
    
    /**
     * Esta constante guarda la lista de los tipos de modalidad
     */
    public static final String LISTA_MODALIDAD = "LISTA_MODALIDAD";
    
    /**
     * Esta constante guarda la lista de los tipos de determinaciones del inmueble
     */
    public static final String LISTA_DETERMINACION_INM = "LISTA_DETERMINACION_INM";
    
    /**
     * Esta constante guarda la lista de los tipos de persona del ciudadano
     */
    public static final String LISTA_TIPOS_PERSONA = "LISTA_TIPOS_PERSONA";
    
    /**
     * Esta constante guarda la lista de los tipos de sexo del ciudadano
     */
    public static final String LISTA_TIPOS_SEXO = "LISTA_TIPOS_SEXO";

    /**
     * Esta constante guarda la lista de los tipos de documento que se pueden
     * seleccionar para una anotacion
     */
    public static final String LISTA_TIPOS_DOCUMENTO = "LISTA_TIPOS_DOCUMENTO";

    /**
     * Esta constante guarda la lista de los tipos de oficina de origen
     */
    public static final String LISTA_OFICINAS_ORIGEN = "LISTA_OFICINAS_ORIGEN";

    /**
     * Esta constante guarda la lista de las veredas
     */
    public static final String LISTA_VEREDAS = "LISTA_VEREDAS";

    /**
     * Esta constante guarda la lista de los municipios
     */
    public static final String LISTA_MUNICIPIOS = "LISTA_MUNICIPIOS";

    /**
     * Esta constante guarda la lista de las departamentos
     */
    public static final String LISTA_DEPARTAMENTOS = "LISTA_DEPARTAMENTOS";

    /**
     * Esta constante guarda el valor de la liquidacion para poder ser mostrada
     * al momento de registrar el pago*
     */
    public static final String VALOR_LIQUIDACION = "VALOR_LIQUIDACION";

    /**
     * Esta constante guarda el valor de los certificados asociados para poder
     * sumarlos a la liquidacion y mostrarlo en el momento de registrar el pago*
     */
    public static final String VALOR_CERTIFICADOS_ASOCIADOS = "VALOR_CERTIFICADOS_ASOCIADOS";

    /**
     * Esta constante identifica la lista de los tipos de estado para los folios
     * que esta guardado en el ServletContext
     */
    public static final String LISTA_TIPOS_ESTADO_FOLIO = "LISTA_TIPOS_ESTADO_FOLIO";

    /**
     * Esta constante identifica la lista de los tipos de predio que estan
     * guardados en el ServletContext
     */
    public static final String LISTA_TIPOS_PREDIO = "LISTA_TIPOS_PREDIO";

    /**
     * Esta constante guarda la informacion sobre el turno que se esta manejando
     * en el momento
     */
    public static final String TURNO = "TURNO";

    /**
     * Esta constante guarda la informacion sobre los turnos que se estan
     * manejando en el momento
     */
    public static final String TURNOS = "TURNOS";

    /**
     * Esta constante identifica la hashtable que guarda las oficinas de acuerdo
     * a su tipo de oficina en el servlet context
     */
    public static final String TABLA_TIPOS_OFICINAS = "LISTA_TIPOS_OFICINAS";

    /**
     * Esta constante identifica el folio que se va a guardar en la sesion
     */
    public static final String FOLIO = "FOLIO";

    /**
     * Esta constante identifica el folio que se ha editado, que tiene cambios y
     * esta en la sesion
     */
    public static final String FOLIO_EDITADO = "FOLIO_EDITADO";

    /**
     * Esta constante identifica la anotacion que se va a guardar en la sesion
     */
    public static final String ANOTACION = "ANOTACION";

    /**
     * Esta constante identifica la lista de los tipos de nota informativa que
     * estan guardados en el ServletContext
     */
    public static final String LISTA_TIPOS_NOTAS = "LISTA_TIPOS_NOTAS";

    /**
     * Esta constante identifica la lista de los tipos de nota informativa que
     * estan guardados en el ServletContext
     */
    public static final String LISTA_TIPOS_NOTAS_DEVOLUTIVAS = "LISTA_TIPOS_NOTAS_DEVOLUTIVAS";

    /**
     * Esta constante identifica la lista de los grupos de naturalezas juridicas
     * que se va a guardar en el ServletContext
     */
    public static final String LISTA_GRUPOS_NATURALEZAS_JURIDICAS = "LISTA_GRUPOS_NATURALEZAS_JURIDICAS";
    /*
        *  @author Carlos Torres
        *  @chage   Nueva constante
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
     */
    /**
     * Esta constante identifica la lista de los grupos de naturalezas juridicas
     * que se va a guardar en el ServletContext
     */
    public static final String LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V = "LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V";

    /**
     * Esta constante identifica la lista de intereses juridicos que se va a
     * guardar en el ServletContext
     */
    public static final String LISTA_INTERES_JURIDICO = "LISTA_INTERES_JURIDICO";

    /**
     * Esta constante identifica la lista de los causales de reimpresion que se
     * va a subir en el ServletContext
     */
    public static final String LISTA_CAUSALES_REIMPRESION = "LISTA_CAUSALES_REIMPRESION";

    /**
     * Esta constante identifica la lista de los folios que han sifo bloqueados
     * para hacer una correcciï¿½n
     */
    public static final String FOLIOS_BLOQUEADOS = "FOLIOS_BLOQUEADOS";

    /**
     * Esta constante identifica la lista de los alcances geogrï¿½ficos que se va
     * a subir en el ServletContext
     */
    public static final String LISTA_TIPOS_ALCANCE_GEOGRAFICO = "LISTA_TIPOS_ALCANCE_GEOGRAFICO";

    /**
     * Esta constante identifica las variables que se guardan es sesion para
     * poder maximizar o minimizar los diferentes bloques
     */
    public static final String OCULTAR_FOLIO = "OCULTAR_FOLIO";

    /**
     * Esta constante identifica las variables que se guardan es sesion para
     * poder maximizar o minimizar los diferentes bloques
     */
    public static final String OCULTAR_TURNO = "OCULTAR_TURNO";

    /**
     * Esta constante identifica las variables que se guardan es sesion para
     * poder maximizar o minimizar los diferentes bloques
     */
    public static final String OCULTAR_SALVEDADES = "OCULTAR_SALVEDADES";

    /**
     * Esta constante identifica las variables que se guardan es sesion para
     * poder maximizar o minimizar los diferentes bloques
     */
    public static final String OCULTAR_ANTIGUO_SISTEMA = "OCULTAR_ANTIGUO_SISTEMA";

    /**
     * Esta constante identifica las variables que se guardan es sesion para
     * poder maximizar o minimizar los diferentes bloques
     */
    public static final String OCULTAR = "OCULTAR";

    /**
     * Esta constante identifica las variables que se guardan es sesion para
     * poder maximizar o minimizar los diferentes bloques
     */
    public static final String OCULTAR_ANOTACIONES = "OCULTAR_ANOTACIONES";

    /**
     * Esta constante identifica la lista de los tipos de visibilidad para las
     * notas que se guarda en el servletContext()
     */
    public static final String LISTA_VISIBILIDAD_NOTAS = "LISTA_VISIBILIDAD_NOTAS";

    /**
     * Esta constante identifica la lista de los causales de restituciï¿½n que se
     * va a subir en el ServletContext
     */
    public static final String LISTA_CAUSALES_RESTITUCION = "LISTA_CAUSALES_RESTITUCION";

    /**
     * Esta constante identifica la lista de los tipos de fotocopia
     */
    public static final String LISTA_TIPOS_FOTOCOPIA = "LISTA_TIPOS_FOTOCOPIA";

    /**
     * Constante nï¿½mero de notas de la fase
     */
    public static final String NUM_NOTAS_FASE = "NUM_NOTAS_FASE";

    /**
     * Constante Lista direcciones de anotaciones
     */
    public static final String LISTA_DIRECCION_ANOTACION = "LISTA_DIRECCION_ANOTACION";

    /**
     * Contante lista de diferenicias
     */
    public static final String LISTA_DIFERENCIAS = "LISTA_DIFERENCIAS";

    /**
     * Esta constante identifica los subtipos de atencion disponibles
     */
    public static final String LISTA_SUBTIPOS_ATENCION = "LISTA_SUBTIPOS_ATENCION";

    /**
     * Esta constante identifica los subtipos de solicitud disponibles
     */
    public static final String LISTA_SUBTIPOS_SOLICITUD = "LISTA_SUBTIPOS_SOLICITUD";

    /**
     * Esta constante identifica los tipos de acto disponibles
     */
    public static final String LISTA_TIPOS_ACTO = "LISTA_TIPOS_ACTO";

    /*
     *@autor          : JATENCIA
     * @mantis        : 0015082 
     * @Requerimiento : 027_589_Acto_liquidaciï¿½n_copias 
     * @descripcion   : Se establece la variable declarada en secciï¿½n.
     */
    /**
     * Esta constante identifica los tipos de acto disponibles
     */
    public static final String LISTA_TIPOS_ACTO_DOS = "LISTA_TIPOS_ACTO_DOS";
    /* - Fin del bloque - */

    /**
     * Esta contante identifica los tipos de impuestos disponibles
     */
    public static final String LISTA_TIPOS_IMPUESTO = "LISTA_TIPOS_IMPUESTO";

    /**
     * @author: Fernando Padilla Velez
     * @change: Esta contante identifica los tipos de impuestos por circulo
     * disponibles.
     */
    public static final String LISTA_TIPOS_IMPUESTO_CIRCULO = "LISTA_TIPOS_IMPUESTO_CIRCULO";

    /**
     * Constante de la lista de folios de Englobe
     */
    public static final String LISTA_FOLIOS_ENGLOBE = "LISTA_FOLIOS_ENGLOBE";

    /**
     * Constante de la lista de una lista de folios
     */
    public static final String LISTA_FOLIOS = "LISTA_FOLIOS";

    /**
     * Constante de complementaciï¿½n de englobe
     */
    public static final String COMPLEMENTACION_ENGLOBE = "COMPLEMENTACION_ENGLOBE";

    /**
     * Esta constante identifica la lista de los folios derivados que se van
     * creando al momento de segregar
     */
    public static final String LISTA_FOLIOS_DERIVADOS = "LISTA_FOLIOS_DERIVADOS";

    /**
     * Constante lista tipos de tarifa
     */
    public static final String LISTA_TIPOS_TARIFA = "LISTA_TIPOS_TARIFA";

    /**
     * Constante listado de tarifas
     */
    public static final String LISTA_TARIFAS = "LISTA_TARIFAS";

    /**
     * Se utiliza para subir al contexto una lista de tipo ElementoLista con los
     * OPLOOKUP TYPES
     */
    public static final String LISTA_OPLOOKUP_TYPES = "LISTA_OPLOOKUP_TYPES";

    /**
     * Se utiliza para subir al contexto una lista objetos de tipo OplookupType
     */
    public static final String LISTA_OPLOOKUP_TYPES_OBJETOS = "LISTA_OPLOOKUP_TYPES_OBJETOS";

    /**
     * Constante de la lista de tipos de calculo
     */
    public static final String LISTA_TIPOS_CALCULO = "LISTA_TIPOS_CALCULO";

    /**
     * Constante lista tipos de derechos registrales
     */
    public static final String LISTA_TIPOS_DERECHO_REGISTRAL = "LISTA_TIPOS_DERECHO_REGISTRAL";

    /**
     * Constante lista con los estados de los folios
     */
    public static final String LISTA_ESTADOS_FOLIO = "LISTA_ESTADOS_FOLIO";

    /**
     * Se utiliza para subir al contexto una lista de tipo ElementoLista con los
     * estados del folio
     */
    public static final String LISTA_ESTADOS_FOLIO_ELEMENTO_LISTA = "LISTA_ESTADOS_FOLIO_ELEMENTO_LISTA";

    /**
     * Constante con la lista de oficinas
     */
    public static final String LISTA_TIPOS_OFICINA = "LISTA_TIPOS_OFICINA";

    /**
     * Constante lista con los dominios de naturaleza jurï¿½dica
     */
    public static final String LISTA_DOMINIOS_NATURALEZA_JURIDICA = "LISTA_DOMINIOS_NATURALEZA_JURIDICA";

    /**
     * Constante listado de circulos registrales
     */
    public static final String LISTA_CIRCULOS_REGISTRALES = "LISTA_CIRCULOS_REGISTRALES";

    /**
     * Constante listado de circulos registrales asociados al SIR
     */
    public static final String LISTA_CIRCULOS_REGISTRALES_FECHA = "LISTA_CIRCULOS_REGISTRALES_FECHA";

    /**
     * Se utiliza para subir al contexto una lista de tipo ElementoLista con los
     * estados de la anotaciï¿½n
     */
    public static final String LISTA_ESTADOS_ANOTACION = "LISTA_ESTADOS_ANOTACION";

    /**
     * Constante para identificar el listado de estados de la anotaciï¿½n
     */
    public static final String LISTA_ESTADOS_ANOTACION_ELEMENTO_LISTA = "LISTA_ESTADOS_ANOTACION_ELEMENTO_LISTA";

    /**
     * Constante para identificar la lista de certificados masivos
     */
    public static final String LISTA_CERTIFICADOS_MASIVOS = "LISTA_CERTIFICADOS_MASIVOS";

    /**
     * Constante para identificar el mapa de Abogado turno
     */
    public static final String MAPA_ABOGADO_TURNO = "MAPA_ABOGADO_TURNO";

    /**
     * Constante para identificar la estaciï¿½n del abogado
     */
    public static final String MAPA_ABOGADO_ESTACION = "MAPA_ABOGADO_ESTACION";

    /**
     * Constante para identificar la lista de acciones notariales
     */
    public static final String LISTA_ACCIONES_NOTARIALES = "LISTA_ACCIONES_NOTARIALES";

    /**
     * Constante para identificar el mapa de departamentos
     */
    public static final String MAPA_DEPARTAMENTOS = "MAPA_DEPARTAMENTOS";

    /**
     * Constante para identificar lista de observaciones de reparto de abogados
     */
    public static final String LISTA_OBSERVACIONES_REPARTO_ABOGADOS = "LISTA_OBSERVACIONES_REPARTO_ABOGADOS";

    /**
     * Constante para identificar la lista de observaciones de reparto notarial
     */
    public static final String LISTA_OBSERVACIONES_REPARTO_NOTARIAL = "LISTA_OBSERVACIONES_REPARTO_NOTARIAL";

    /**
     * Constante para identificar la lista de observaciones de reparto notarial
     */
    public static final String REPARTO_NOTARIAL_FAILED = "REPARTO_NOTARIAL_FAILED";

    /**
     * Constante para identificar las lista de categorï¿½as
     */
    public static final String LISTA_CATEGORIAS = "LISTA_CATEGORIAS";

    /**
     * Constante para identificar las lista de categorï¿½as a las que puede
     * pertenecer una Notaria
     */
    public static final String LISTA_CATEGORIAS_NOTARIA = "LISTA_CATEGORIAS_NOTARIA";

    /**
     * Constante para identificar el nï¿½mero maximo de impresiones
     */
    public static final String NUMERO_MAX_IMPRESIONES = "NUMERO_MAX_IMPRESIONES";

    /**
     * Constante para identificar el titulo del checkbox eliminar
     */
    public static final String TITULO_CHECKBOX_ELIMINAR = "ELIMINAR_CHECKBOX";

    /**
     * Constante para identificar el titulo del checkbox eliminar
     */
    public static final String TITULO_CHECKBOX_ELIMINAR_SIR_MIG = "ELIMINAR_CHECKBOX_SIR_MIG";

    /**
     * Constante para identificar las lista de matriculas de solicitudes de
     * registro
     */
    public static final String LISTA_MATRICULAS_SOLICITUD_REGISTRO = "LISTA_MATRICULAS_SOLICITUD_REGISTRO";

    /**
     * Constante para identificar las lista de matriculas sin migrar que desean
     * asociarse al turno de registro
     */
    public static final String LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO = "LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO";

    /**
     * Constante para identificar la lista de turno folio mig que tiene un turno
     * que fue migrados por mig. incremental
     */
    public static final String LISTA_MATRICULAS_TURNO_FOLIO_MIG = "LISTA_MATRICULAS_TURNO_FOLIO_MIG";

    /**
     * Constante para identificar la lista de turno folio mig que tiene un turno
     * que fue migrados por mig. incremental
     */
    public static final String LISTA_MATRICULAS_TURNO_FOLIO_MIG_STRING = "LISTA_MATRICULAS_TURNO_FOLIO_MIG_STRING";

    /**
     * Constante para identificar las lista de matriculas de solicitudes de
     * correciï¿½n
     */
    public static final String LISTA_MATRICULAS_SOLICITUD_CORRECCION = "LISTA_MATRICULAS_SOLICITUD_CORRECCION";

    /**
     * Constante para identificar las lista de matriculas sin migrar de la
     * solicitud de correciï¿½n
     */
    public static final String LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION = "LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION";

    /**
     * Constante para identificar las lista de matriculas vï¿½lidas para el cambio
     * de estas en cert. asociados
     */
    public static final String LISTA_MATRICULAS_VALIDAS_CAMBIO = "LISTA_MATRICULAS_VALIDAS_CAMBIO";

    /**
     * Esta constante identifica los tipos de unidades de medida (area)
     */
    public static final String LISTA_UNIDADES_MEDIDA = "LISTA_UNIDADES_MEDIDA";

    /**
     * Esta constante identifica los tipos de RESPONSABILIDAD
     */
    public static final String LISTA_RESPONSABILIDADES = "LISTA_RESPONSABILIDADES";

    /**
     * Esta constante identifica los tipos de los diferentes roles de usuario
     * disponibles en el sistema
     */
    public static final String LISTA_ROLES_SISTEMA = "LISTA_ROLES_SISTEMA";

    /**
     * Esta constante identifica los tipos de los diferentes roles de usuario
     * disponibles en el sistema
     */
    public static final String LISTA_TIPOS_PAGO = "LISTA_DOCUMENTOS_PAGO";

    /**
     * Esta constante identifica los tipos de los diferentes roles de usuario
     * disponibles en el sistema
     */
    public static final String TIPOS_PAGO_LISTA = "TIPOS_PAGO_LISTA";

    /**
     * Esta constante identifica las diferentes estaciones disponibles en el
     * sistema
     */
    public static final String LISTA_ESTACIONES_SISTEMA = "LISTA_ESTACIONES";

    /**
     * Esta constante identifica los diferentes niveles disponibles en el
     * sistema
     */
    public static final String LISTA_NIVELES_SISTEMA = "LISTA_NIVELES_SISTEMA";

    /**
     * Esta constante identifica las diferentes validaciones nota disponibles en
     * el sistema
     */
    public static final String LISTA_VALIDACIONES_NOTA = "LISTA_VALIDACIONES_NOTA";

    /**
     * Esta constante identifica lOs diferentes PROCESOS nota disponibles en el
     * sistema
     */
    public static final String LISTA_PROCESOS_SISTEMA = "LISTA_PROCESOS_SISTEMA";

    /**
     * Esta constante identifica las diferentes validaciones existentes en el
     * sistema
     */
    public static final String LISTA_VALIDACIONES = "LISTA_VALIDACIONES";

    /**
     * Esta constante indica cuando un usuario desea iniciar la sesiï¿½n para
     * tener acceso a las pantallas administrativas del sistema
     */
    public static final String INICIA_SESION_ADMINISTRACION = "INICIA_SESION_ADMINISTRACION";

    /**
     * Ruta de la carpeta en el sistema de archivos donde se almacenan los
     * archivos graficos de firmas (Nombre de la variable de init parameter del
     * servlet)
     */
    public static final String FIRMAS_PATH = "FIRMAS_PATH";

    /**
     * Directorio donde se almacenan las imagenes de firmas
     */
    public static final String FIRMAS_DIRECTORY = "FIRMAS_DIRECTORY";

    /**
     * Tipo de Contenido Autorizado para las firmas
     */
    public static final String FIRMAS_CONTENT_TYPE = "FIRMAS_CONTENT_TYPE";

    /**
     * Constante para determinar la url del servlet de reportes.
     */
    public static final String REPORTES_SERVLET_URL = "REPORTES_SERVLET_URL";

    /**
     * Constante de mayor extensiï¿½n del folio
     */
    public static final String MAYOR_EXTENSION_FOLIO = "MAYOR_EXTENSION_FOLIO";

    /**
     * Variable para guardar el sitio desde donde se desean consultar las anotaciones
     */
    public static final String INICIO = "INICIO";

    /**
     * Variable para guardar la cantidad de anotaciones que se desean consultar
     */
    public static final String CANTIDAD = "CANTIDAD";

    /**
     * Variable para guardar la cantidad de anotaciones que hay para un folio
     */
    public static final String CANTIDAD_REGISTROS = "CANTIDAD_REGISTROS";

    /**
     * Variable para guardar la lista de anotaciones de un folio
     */
    public static final String LISTA_ANOTACIONES = "LISTA_ANOTACIONES";

    /**
     * Variable para guardar el nï¿½mero total de anotaciones que tiene un folio
     */
    public static final String TOTAL_ANOTACIONES = "TOTAL_ANOTACIONES";

    public static final String TOTAL_ANOTACIONES_PREVIEW = "TOTAL_ANOTACIONES_PREVIEW";

    /**
     * Variable para guardar la vista que desea guardarse para luego regresar a ella.
     */
    public static final String VISTA_ORIGINADORA = "VISTA_ORIGINADORA";

    /**
     * Variable para guardar la primera vista que desea guardarse para luego
     * regresar a ella.
     */
    public static final String VISTA_INICIAL = "VISTA_INICIAL";

    /**
     * Variable para guardar la vista vista a la que se debe regresar luego de
     * ejecutar un reporte.
     */
    public static final String VISTA_PARA_REGRESAR = "VISTA_PARA_REGRESAR";

    /**
     * Esta constante es la llave que indica que el certificado asociado a la
     * respuesta de este evento en de mayor valor.
     */
    public static final String CERTIFICADO_MAYOR_EXTENSION = "CERTIFICADO_MAYOR_EXTENSION";

    /**
     * Esta constante indica un objeto ciudadano para validar si ï¿½ste se
     * encuentra en la base de datos.
     */
    public static final String VALIDAR_CIUDADANO = "VALIDAR_CIUDADANO";

    /**
     * Esta constante indica que el ciudadano ha sido verificado en la Base de
     * Datos
     */
    public static final String CIUDADANO_VERIFICADO = "CIUDADANO_VERIFICADO";

    /**
     * Esta constante indica que el se puede aprobar la calificacion (se hace
     * visible el boton "Aprobar")
     */
    public static final String APROBAR_CALIFICACION = "APROBAR_CALIFICACION";

    /**
     * Esta constante indica que hay una excepcion en la pagina tonces no se
     * debe refrescar la pagina.
     */
    public static final String EXCEPCION = "EXCEPCION";

    /**
     * Almacena la vista a la que se debe volver
     */
    public static final String ULTIMA_VISTA_TEMPORAL = "ULTIMA_VISTA_TEMPORAL";

    /**
     * Almacena la lista de prohibiciones existentes en el sistema
     */
    public static final String LISTA_PROHIBICIONES = "LISTA_PROHIBICIONES";

    /**
     * Nï¿½mero mï¿½ximo de copias que se pueden imprimir de un certificado
     */
    public static final String MAXIMO_COPIAS_CERTIFICADO = "MAXIMO_COPIAS_CERTIFICADOS";

    /**
     * Constante para acceder a la lista de tipos de visibilidad de las notas
     * informativas existentes en el sistema.
     */
    public static final String LISTA_VISIBILIDADES = "LISTA_VISIBILIDADES";

    /**
     * Constante para identificar el delta de los cambios que se han aplicado a
     * un folio cuando se ha modificado, el tipo de objeto que se carga en esta
     * variable es de tipo DeltaFolio
     */
    public static final String CAMBIOS_PROPUESTOS = "CAMBIOS_PROPUESTOS";

    /**
     * Almacena la variable con el tipo de dato WebEnglobe
     */
    public static final String WEB_ENGLOBE = "WEB_ENGLOBE";

    /**
     * Almacena la variable con el tipo de dato WebSegregacion
     */
    public static final String WEB_SEGREGACION = "WEB_SEGREGACION";

    /**
     * Almacena la variable con el tipo de dato WebSegregacion
     */
    public static final String LISTA_WEB_SEG_ENG = "LISTA_WEB_SEG_ENG";

    /**
     * Almacena la lista de tipos de tarifa de certificados*
     */
    public static final String LISTA_TARIFAS_CERTIFICADOS = "LISTA_TARIFAS_CERTIFICADOS";

    /**
     * Almacena el valor de un ITEM seleccionado*
     */
    public static final String ITEM = "ITEM";

    /**
     * Almacena un listado de las notas informativas utilizadas antes de la
     * creaciï¿½n de un turno.
     */
    public static final String LISTA_NOTAS_INFORMATIVAS_SIN_TURNO = "LISTA_NOTAS_INFORMATIVAS_SIN_TURNO";

    /**
     * Almacena un listado con los errores que se presentaron al validar las
     * matrï¿½culas de un turno de certificados masivos.
     */
    public static final String LISTA_VALIDACIONES_MASIVOS = "LISTA_VALIDACIONES_MASIVOS";

    /**
     * Almacena el valor del secuencial de recibo para la estaciï¿½n.
     */
    public static final String SECUENCIAL_RECIBO_ESTACION = "SECUENCIAL_RECIBO_ESTACION";

    /**
     * Indica si el secuencial de recibos ha sido incrementado
     */
    public static final String SECUENCIAL_RECIBO_CONSULTADO = "SECUENCIAL_RECIBO_CONSULTADO";

    /**
     * Variable para guardar la vista de las solicitudes para saber a dï¿½nde
     * regresar cuando se realiza un pago
     */
    public static final String VISTA_LIQUIDACION = "VISTA_LIQUIDACION";

    /**
     * Variable para guardar la vista de las solicitudes para saber a dï¿½nde
     * regresar cuando se realiza un pago en el proceso de liquidaciï¿½n
     */
    public static final String VISTA_SOLICITUD = "VISTA_SOLICITUD";

    /**
     * Variable para guardar la lista de impresoras
     */
    public static final String LISTA_IMPRESORAS = "LISTA_IMPRESORAS";

    /**
     * Variable para guardar la impresora seleccionada
     */
    public static final String IMPRESORA = "IMPRESORA";

    /**
     * Variable para guardar una plantilla predeterminada para el oficio de pertenencia
     */
    public static final String PLANTILLA = "PLANTILLA";

    /**
     * Variable para guardar el nombre paginadorAvanzado asociado
     */
    public static final String NOMBRE_PAGINADOR = "NOMBRE_PAGINADOR";

    /**
     * Variable para guardar el nombre ResultadoPaginador asociado
     */
    public static final String NOMBRE_RESULTADOS_PAGINADOR = "NOMBRE_RESULTADOS_PAGINADOR";

    /**
     * Variable para guardar el nombre del numero de la anotacion temporal
     * asociado
     */
    public static final String NOMBRE_NUM_ANOTACION_TEMPORAL = "NOMBRE_NUM_ANOTACION_TEMPORAL";

    /**
     * Variable para guardar el motivo por el cual se incrementa el secuencial
     * de recibos.
     */
    public static final String MOTIVO_INCREMENTO_SECUENCIAL = "MOTIVO_INCREMENTO_SECUENCIAL";

    /**
     * Variable para guardar el motivo por el cual se incrementa el secuencial
     * de recibos.
     */
    public static final String NATURALEZA_JURIDICA = "NATURALEZA_JURIDICA";

    /**
     * Constante que identifica el valor de la variable de sesiï¿½n para almacenar
     * un turno tramite
     */
    public static final String TURNO_TRAMITE = "TURNO_TRAMITE";

    /**
     * Constante que identifica el valor de la variable de sesiï¿½n para almacenar
     * un turno deuda
     */
    public static final String TURNO_DEUDA = "TURNO_DEUDA";

    /**
     * Constante que identifica el valor de la variable de sesiï¿½n para almacenar
     * un usuario bloqueo
     */
    public static final String USUARIO_BLOQUEO = "USUARIO_BLOQUEO";

    /**
     * Constante que identifica el valor de la variable de sesiï¿½n para almacenar
     * un usuario bloqueo
     */
    public static final String RECARGA = "RECARGA";

    /**
     * Constante que identifica la lista de matriculas que se han consultado en
     * las pantallas administrativas
     */
    public static final String LISTA_CONSULTAS = "LISTAS_CONSULTAS";

    /**
     * Constante que identifica la lista de tipos de tarifa por cï¿½rculo que se
     * han consultado en las pantallas administrativas
     */
    public static final String LISTA_TIPOS_TARIFA_POR_CIRCULO = "LISTA_TIPOS_TARIFA_POR_CIRCULO";

    /**
     * Constante que identifica la lista de tipos de tarifa configurables por
     * cï¿½rculo que se cargan en el contexto
     */
    public static final String LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO = "LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO";

    /**
     * Constante que identifica la solicitud en el contexto
     */
    public static final String SOLICITUD = "SOLICITUD";

    /**
     * Constante que identifica el atributo en sesiï¿½n
     */
    public static final String REABIERTO_FOLIO = "REABIERTO_FOLIO";

    /**
     * Constante que indica el campo Fecha de pago del timbre del banco
     */
    public final static String CAMPO_FECHA_PAGO_TIMBRE = "CAMPO_FECHA_PAGO_TIMBRE";

    /**
     * Constante que indica el campo nï¿½mero de solicitud de pago del timbre del
     * banco
     */
    public final static String CAMPO_NUMERO_SOLICITUD_LIQUIDACION = "NUMERO_SOLICITUD_LIQUIDACION";

    /**
     * Constante que indica el campo numero timbre de banco de pago del timbre
     * del banco
     */
    public final static String CAMPO_NUMERO_TIMBRE_BANCO = "CAMPO_NUMERO_TIMBRE_BANCO";

    /**
     * Constante que indica la lista de grupos de naturalezas jurï¿½dicas
     * habilitadas para calificaciï¿½n
     */
    public final static String LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION = "LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION";

    /**
     * Constante que define el formato de fecha utilizado por la
     * superintendencia
     */
    public final static String FORMATO_FECHA = "dd/MM/yyyy";

    /**
     * Constante que define el texto de la oficina internacional
     */
    public final static String TEXT_OFICINA_INTERNACIONAL = "TEXT_OFICINA_INTERNACIONAL";

    /**
     * Constante que define el tipo de oficina Nacional o Internacional
     */
    public final static String TIPO_OFICINA_I_N = "TIPO_OFICINA_I_N";

    /**
     * Constante que define el tipo de oficina Nacional
     */
    public final static String TIPO_OFICINA_NACIONAL = "TIPO_OFICINA_NACIONAL";

    /**
     * Constante que define el tipo de oficina Internacional
     */
    public final static String TIPO_OFICINA_INTERNACIONAL = "TIPO_OFICINA_INTERNACIONAL";

    /**
     * Constante que define el Identificador de la oficina
     */
    public final static String ID_OFICINA = "ID_OFICINA";

    /**
     * Constante que define el Identificador de la oficina
     */
    public final static String NUMERO_OFICINA = "NUMERO_OFICINA";

    /**
     * Constante que define el identificador para la clase copia anotaciï¿½n que
     * facilita la copia de una anotaciï¿½n a otros folios
     */
    public final static String COPIA_ANOTACION = "COPIA_ANOTACION";

    /**
     * Constante que define el identificador del proceso de reparto de abogados
     */
    public final static String ID_PROCESO_REPARTO = "ID_PROCESO_REPARTO";

    /**
     * Constante que define el identificador del las llaves del Mostrar Folio Helper
     */
    public static final String NOMBRE_LLAVES_MOSTRAR_FOLIO = "NOMBRE_LLAVES_MOSTRAR_FOLIO";

    /**
     * Constante que define el identificador del valor de la lista de Subtipo de
     * solicitud
     */
    public static final String SUBTIPO_SOLICITUD = "SUBTIPO_SOLICITUD";

    /**
     * ******* CONSTANTES PARA EL OFICINA HELPER ********************
     */
    /**
     * Constante que define el identificador del flag que define si el oficina
     * helper es o no stteado por el jsp
     */
    public static final String OFICINA_HELPER_MANUAL = "OFICINA_HELPER_MANUAL";

    /**
     * Constante que define el identificador del tipo oficina
     */
    public static final String TIPO_OFICINA = "TIPO_OFICINA";

    /**
     * Constante que define el identificador del tipo nom oficina
     */
    public static final String TIPO_NOM_OFICINA = "TIPO_NOM_OFICINA";

    /**
     * Constante que define el identificador para el folio origen, Sirve para
     * determinar cuï¿½l es el folio de dï¿½nde se copiara una anotaciï¿½n o una cancelaciï¿½n
     */
    public static final String FOLIO_ORIGEN = "FOLIO_ORIGEN";

    /**
     * Constante que identifica el campo para redireccionar un turno de
     * correccion en la fase REVISION ANALISIS
     */
    public static final String REDIRECCION_CORRECCION = "REDIRECCION_CORRECCION";

    /**
     * Constante que define el de la excepciï¿½n de copia de anotaciones
     */
    public static final String EXCEPCION_COPIA_ANOTACION = "EXCEPCION_COPIA_ANOTACION";

    /**
     * Constante Boolean que define si la forma de pago efectivo se genera por
     * defecto en un pago
     */
    public static final String GENERAR_PAGO_EFECTIVO = "GENERAR_PAGO_EFECTIVO";

    /**
     * Esta constante indica que las anotaciones temporales que hay por
     * matrï¿½cula.
     */
    public static final String VALIDACION_APROBAR_CALIFICACION = "VALIDACION_APROBAR_CALIFICACION";

    /**
     * Esta constante indica que ya se agregï¿½ una nota devolutiva.
     */
    public static final String VALIDACION_NOTA_DEVOLUTIVA = "VALIDACION_NOTA_DEVOLUTIVA";

    /**
     * Esta constante guarda una lista con los otorgantes en el reparto notarial
     */
    public static final String LISTA_OTORGANTES = "LISTA_OTORGANTES";

    /**
     * Lista de las naturalezas juridicas de entidad
     */
    public static final String LISTA_NATURALEZAS_JURIDICAS_ENTIDAD = "LISTA_NATURALEZAS_JURIDICAS_ENTIDAD";

    /**
     * Lista de las naturalezas juridicas de entidad que estan activas
     */
    public static final String LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS = "LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS";

    /**
     * Esta constante guarda una lista con los otorgantes en el reparto notarial
     */
    public static final String LISTA_ENTIDADES_PUBLICAS = "LISTA_ENTIDADES_PUBLICAS";

    /**
     * Esta constante guarda una lista con las entidades publicas activas
     */
    public static final String LISTA_ENTIDADES_PUBLICAS_ACTIVAS = "LISTA_ENTIDADES_PUBLICAS_ACTIVAS";

    /**
     * Esta constante guarda una lista con los circulos notariales de los
     * circulos registrales
     */
    public static final String LISTA_CIRCULOS_NOTARIALES = "LISTA_CIRCULOS_NOTARIALES";

    /**
     * Constante que identifica la lista de los resultados de la busqueda por
     * fechas de reparto
     */
    public static final String TABLA_MINUTAS = "TABLA_MINUTAS";

    /**
     * Constante que identifica la lista de los permisos en la fase de revision
     */
    public static final String LISTA_PERMISOS_REVISION_CORRECCION = "LISTA_PERMISOS_REVISION_CORRECCION";

    /**
     * Constante que identifica la lista de los permisos escogidos en la fase de revision
     */
    public static final String LISTA_PARAMETROS_PERMISOS_REVISION = "LISTA_PARAMETROS_PERMISOS_REVISION";

    /**
     * Constante que identifica la lista de los permisos en la fase de revision
     */
    public static final String LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION = "LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION";

    /**
     * Constante que identifica la minuta de la busqueda por radicacion
     */
    public static final String MINUTA = "MINUTA";

    /**
     * Constante que identifica si un usuario es o no cajero para permitir que
     * despuï¿½s de la liquidaciï¿½n de registro liquide el pago o no.
     */
    public static final String ES_CAJERO = "ES_CAJERO";

    /**
     * Constante que identifica si un usuario es o no cajero DE REGISTROS para
     * permitir que despuï¿½s de la liquidaciï¿½n de registro pueda crear el turno o no.
     */
    public static final String ES_CAJERO_REGISTRO = "ES_CAJERO_REGISTRO";

    public static final String LISTA_ID_FOLIOS_SEGREGADOS_EDITADOS = "LISTA_ID_FOLIOS_SEGREGADOS_EDITADOS";

    /**
     * Constante que identifica el numero de copias a imprimir.
     */
    public static final String NUMERO_COPIAS_IMPRESION = "NUMERO_COPIAS_IMPRESION";

    /**
     * Constante que identifica la lista donde se estan guardando las alertas
     */
    public static final String ALERTAS = "ALERTAS";

    public static final String ACCION_SECUNDARIA = "ACCION_SECUNDARIA";

    public static final String LISTA_DIFERENCIAS_DIRECCION = "LISTA_DIFERENCIAS_DIRECCION";

    public static final String LISTA_DIFERENCIAS_ANOTACIONES = "LISTA_DIFERENCIAS_ANOTACIONES";

    public static final String ID_DIRECCION = "ID_DIRECCION";

    public static final String ID_ANOTACION = "ID_ANOTACION";

    public static final String DELTA_FOLIO_EDICION = "DELTA_FOLIO_EDICION";

    public static final String ANOTACION_EDITAR = "ANOTACION_EDITAR";

    /**
     * Permite saber si hay un pop up activo de certificados asociados para
     * saber si se carga los errores en la vista de la liquidaciï¿½n o de los
     * certificados asociados
     */
    public static final String POP_CERTIFICADOS_ASOCIADOS = "POP_CERTIFICADOS_ASOCIADOS";

    /**
     * Esta constante identifica el atributo del objeto HttpServletRequest que
     * almacena los datos con respecto a la informacion de los chques
     */
    public static final String LISTA_CHEQUES_MARCAS = "LISTA_CHEQUES_MARCAS";

    /**
     * Esta constante identifica el atributo del objeto HttpServletRequest que
     * almacena los datos con respecto a la informacion de las consignaciones
     */
    public static final String LISTA_CONSIGNACIONES_MARCAS = "LISTA_CONSIGNACIONES_MARCAS";
    
    public static final String LISTA_GENERAL_MARCAS = "LISTA_GENERAL_MARCAS";

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Se crean las constantes necesarias para dar soluciï¿½n al
     * requerimiento.
     */
    public static final String LISTA_TARJETA_CREDITO_MARCAS = "LISTA_TARJETA_CREDITO_MARCAS";
    public static final String LISTA_TARJETA_DEBITO_MARCAS = "LISTA_TARJETA_DEBITO_MARCAS";
    public static final String LISTA_PSE_MARCAS = "LISTA_PSE_MARCAS";
    //public static final String LISTA_CONVENIO_MARCAS = "LISTA_CONVENIO_MARCAS";

    /**
     * Mï¿½todo constructor por defecto de la clase WebKeys
     */
    public static final String VISTA_VOLVER = "VISTA_VOLVER";

    public static final String ES_TURNO_MANUAL = "ES_TURNO_MANUAL";

    /**
     * Variable para determinar la confirmaciï¿½n del pago en exceso
     */
    public static final String CONFIRMACION_PAGO_EXCESO = "CONFIRMACION_PAGO_EXCESO";

    /**
     * Variable para el manejo del listado de pantallas administrativas visibles
     * para un usuario
     */
    public static final String LISTADO_PANTALLAS_VISIBLES = "LISTADO_PANTALLAS VISIBLES";

    /**
     * Variable para el manejo del listado de tipos de pantallas administrativas
     * definidas en la aplicaciï¿½n
     */
    public static final String LISTADO_TIPOS_PANTALLAS = "LISTADO_TIPOS_PANTALLAS";

    /**
     * Variable para el manejo del listado de turnos vencidos de pago de mayor
     * valor.
     */
    public static final String LISTADO_VENCIDOS_MAYOR_VALOR = "LISTADO_VENCIDOS_MAYOR_VALOR";

    /**
     * Variable para el manejo de la recarga del listado de turnos vencidos de
     * pago de mayor valor.
     */
    public static final String RECARGA_MAYOR_VALOR = "RECARGA_MAYOR_VALOR";

    /**
     * Constante que se usa para validar que cuando es un turno anterior no se
     * puedan modificar los datos del turno anterior.
     */
    public static final String TURNO_ANTERIOR_OK = "TURNO_ANTERIOR_OK";

    public static final String PANTALLA_ESPERA = "PANTALLA_ESPERA";

    /**
     * Variable para gardar el valor inferior de una rango.
     */
    public static final String DESDE = "DESDE";

    /**
     * Variable para gardar el valor superior de una rango.
     */
    public static final String HASTA = "HASTA";

    public static final String LISTADO_PENDIENTES_MAYOR_VALOR = "LISTADO_PENDIENTES_MAYOR_VALOR";

    public static final String LISTA_TIPOS_NOTAS_PMY = "LISTA_TIPOS_NOTAS_PMY";
    public static final String ROL_ADMINISTRADOR_NACIONAL = "ROL_ADMINISTRADOR_NACIONAL";

    public static final String HAY_REFRESCO = "HAY_REFRESCO";

    public static final String MAPA_ABOGADO_RELACION = "MAPA_ABOGADO_RELACION";

    public static final String NUMERO_RELACION = "NUMERO_RELACION";

    public static final String ESTADO_REL_ROL_ESTACION = "ESTADO_REL_ROL_ESTACION";
    public static final String IMPRIMIBLE = "IMPRIMIBLE";

    public static final String LISTA_IMPRESIONES_FALLIDAS = "LISTA_IMPRESIONES_FALLIDAS";

    public static final String ID_IMPRIMIBLE = "ID_IMPRIMIBLE";

    public static final String IMPRESO = "IMPRESO";

    public static final String NOTA_DEVOLUTIVA_IMPRESA = "NOTA_DEVOLUTIVA_IMPRESA";

    public static final String DEPENDENCIA_TURNO = "DEPENDENCIA_TURNO";

    public static final String TURNO_HIJO = "TURNO_HIJO";

    public static final String TURNO_PADRE = "TURNO_PADRE";

    public static final String MENSAJE_VENCIMIENTO_TERMINOS_HIPOTECA = "MENSAJE_VENCIMIENTO_TERMINOS_HIPOTECA";

    public static final String MENSAJE_VENCIMIENTO_TERMINOS_PATRIMONIO = "MENSAJE_VENCIMIENTO_TERMINOS_PATRIMONIO";

    public static final String LISTA_FOLIOS_SIN_ACTUALIZACION_NOMENCLATURA = "LISTA_FOLIOS_SIN_ACTUALIZACION_NOMENCLATURA";

    /**
     * Esta constante identifica donde se guarda se hay alguna alerta para
     * digitacion masiva
     */
    public static final String ALERTA_FOLIOS_FORANEOS = "ALERTA_FOLIOS_FORANEOS";

    public static final String LISTA_ACTOS_MINUTA = "LISTA_ACTOS_MINUTA";

    /**
     * Constante para identificar la lista en la que se almacenan los items de
     * chequeo de devoluciones.
     */
    public static final String ITEMS_CHEQUEO_DEVOLUCIONES = "ITEMS_CHEQUEO_DEVOLUCIONES";

    public static final String DATOS_ANTIGUO_SISTEMA = "DATOS_ANTIGUO_SISTEMA";

    public static final String LISTA_PROCESOS_RECIBO_SISTEMA = "LISTA_PROCESOS_RECIBO_SISTEMA";

    public static final String OFICINA_ORIGEN = "OFICINA_ORIGEN";

    public static final String LISTA_TURNOS_REANOTAR = "LISTA_TURNOS_REANOTAR";

    public static final String LISTA_CALIFICADORES = "LISTA_CALIFICADORES";

    public static final String LISTA_CAUSALES_DEVOLUCION = "LISTA_CAUSALES_DEVOLUCION";

    public static final String LISTA_SALVEDADES_FOLIO = "LISTA_SALVEDADES_FOLIO";

    public static final String LISTA_SALVEDADES_ANOTACION = "LISTA_SALVEDADES_ANOTACION";

    public static final String CHECK_ANOTACION_TEMPORAL_MOSTRAR_FOLIO_HELPER = "CHECK_ANOTACION_TEMPORAL_MOSTRAR_FOLIO_HELPER";

    public static final String MATRICULAS_INSCRITAS = "MATRICULAS_INSCRITAS";

    public static final String MATRICULAS_NO_INSCRITAS = "MATRICULAS_INSCRITAS";

    public static final String MIGRACION = "MIGRACION";

    /**
     * Constante para identificar la lista en la que se almacenan los items de
     * restitucion reparto notarial.
     */
    public static final String LISTA_TURNOS_RESTITUCION_NOTARIAL = "LISTA_TURNOS_RESTITUCION_NOTARIAL";

    public static final String LISTA_TURNOS_RESTITUCION_NOTARIAL_INFO = "LISTA_TURNOS_RESTITUCION_NOTARIAL_INFO";

    public static final String NOTA_DEVOLUTIVA = "NOTA_DEVOLUTIVA";

    public static final String NOTA_INFORMATIVA = "NOTA_INFORMATIVA";

    /**
     * Constante listado de tipo de reparto
     */
    public static final String LISTA_TIPOS_HORARIO_NOTARIAL = "LISTA_TIPOS_HORARIO_NOTARIAL";

    public static final String HABILITADO_CALIFICACION = "HABILITADO_CALIFICACION";

    public static final String MOSTRAR_OPCION_INSCRIPCION = "MOSTRAR_OPCION_INSCRIPCION";

    public static final String LISTA_SESIONES = "LISTA_SESIONES";

    public static final String IMPRESION_EXISTE_SESION = "IMPRESION_EXISTE_SESION";

    /**
     * Constante que identifica el parametro para reimprimir el recibo de mayor
     * valor
     */
    public static final String REIMPRIME_RECIBO_MAYOR_VALOR = "REIMPRIME_RECIBO_MAYOR_VALOR";

    public static final String IMPRIMIBLE_PDF = "IMPRIMIBLE_PDF";

    public static final String REPORTES_JASPER_RUTA = "REPORTES_JASPER_RUTA";

    public static final String TURNO_DEVOLUCION = "TURNO_DEVOLUCION";

    /**
     * Modifica Pablo Quintana Junio 19 2008 Constante que identifica un
     * testamento en session
     */
    public static final String TESTAMENTO_SESION = "TESTAMENTO_SESION";

    /**
     * Constante que identifica la constante de turno anterior (devoluciones)*
     */
    public static final String TURNO_ANT_DEV = "TURNO_ENT_DEV";
    public static final String TURNO_DEVOLUCION_MODIFICADO = "TURNO_DEVOLUCION_MODIFICADO";

    /**
     * Constante que determina si se hizo el proceso inicial de registro desde
     * el rol liquidador registro
     */
    public static final String LIQUIDADOR = "LIQUIDADOR";

    /**
     * Constante para identificar el titulo del checkbox eliminar
     */
    public static final String TITULO_CHECKBOX_ELIMINAR_MATSOL_REGISTRO = "TITULO_CHECKBOX_ELIMINAR_MATSOL_REGISTRO";

    /**
     * Constante para indicar las matriculas de registro que no se han agregado
     * como certificados asociados*
     */
    public static final String LISTA_CERT_NO_MAT = "LISTA_CERT_NO_MAT";

    public static final String VISTA_ANTERIOR_AUXILIAR = "VISTA_ANTERIOR_AUXILIAR";

    public static final String LISTA_BANCOS_X_CIRCULO = "LISTA_BANCOS_X_CIRCULO";
    
    public static final String LISTADO_BANCOS_CIRCULO = "LISTADO_BANCOS_CIRCULO";

    public static final String TURNOS_POSTERIORES_CONF_TESTAMENTO = "TURNOS_POSTERIORES_CONF_TESTAMENTO";

    public static final String FOLIO_AUXILIAR = "FOLIO_AUXILIAR";

    public static final String LISTA_FOLIOS_DERIVADO_HIJO_TMP = "LISTA_FOLIOS_DERIVADO_HIJO_TMP";

    public static final String MAYOR_EXTENSION_FOLIO_PREVIEW = "MAYOR_EXTENSION_FOLIO_PREVIEW";

    public static final String LISTA_ANOTACIONES_GRAVAMEN_PREVIEW = "LISTA_ANOTACIONES_GRAVAMEN_PREVIEW";

    public static final String LISTA_ANOTACIONES_MEDIDAS_CAUTELARES_PREVIEW = "LISTA_ANOTACIONES_MEDIDAS_CAUTELARES_PREVIEW";

    public static final String LISTA_ANOTACIONES_FALSA_TRADICION_PREVIEW = "LISTA_ANOTACIONES_FALSA_TRADICION_PREVIEW";

    public static final String LISTA_ANOTACIONES_INVALIDAS_PREVIEW = "LISTA_ANOTACIONES_INVALIDAS_PREVIEW";

    public static final String LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR_PREVIEW = "LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR_PREVIEW";

    public static final String LISTA_ANOTACIONES_AFECTACION_VIVIENDA_PREVIEW = "LISTA_ANOTACIONES_AFECTACION_VIVIENDA_PREVIEW";

    public static final String LISTA_FOLIOS_PADRE_PREVIEW = "LISTA_FOLIOS_PADRE_PREVIEW";

    public static final String LISTA_FOLIOS_HIJO_PREVIEW = "LISTA_FOLIOS_HIJO_PREVIEW";

    public static final String LISTA_SALVEDADES_ANOTACIONES_PREVIEW = "LISTA_SALVEDADES_ANOTACIONES_PREVIEW";

    public static final String LISTA_ANOTACIONES_CANCELACION_PREVIEW = "LISTA_ANOTACIONES_CANCELACION_PREVIEW";

    public static final String TURNO_TRAMITE_PREVIEW = "TURNO_TRAMITE_PREVIEW";

    public static final String TURNO_DEUDA_PREVIEW = "TURNO_DEUDA_PREVIEW";

    public static final String USUARIO_BLOQUEO_PREVIEW = "USUARIO_BLOQUEO_PREVIEW";

    public static final String LISTA_FOLIOS_DERIVADO_PADRE_PREVIEW = "LISTA_FOLIOS_DERIVADO_PADRE_PREVIEW";

    public static final String LISTA_FOLIOS_DERIVADO_HIJO_PREVIEW = "LISTA_FOLIOS_DERIVADO_HIJO_PREVIEW";

    public static final String LISTA_TURNOS_FOLIO_TRAMITE_PREVIEW = "LISTA_TURNOS_FOLIO_TRAMITE_PREVIEW";

    public static final String RECARGA_PREVIEW = "RECARGA_PREVIEW";

    public static final String PREVIEW_TOTAL = "PREVIEW_TOTAL";

    /**
     * Constante que identifica el atributo que se cargarï¿½ en el request para
     * verificar en el jsp sï¿½ existe sï¿½lo una estaciï¿½n.
     */
    public final static String NUM_ESTACIONES = "NUM_ESTACIONES";
    /**
     * Constante que identifica el atributo que se cargarï¿½ en el request para
     * verificar en el jsp sï¿½ existe sï¿½lo un proceso.
     */
    public final static String NUM_PROCESOS = "NUM_PROCESOS";
    /**
     * Constante que identifica el atributo que se cargarï¿½ en el request para
     * verificar en el jsp sï¿½ existe sï¿½lo una fase.
     */
    public final static String NUM_FASES = "NUM_FASES";

    /**
     * JALCAZAR 02/09/2009 Constante que identifica el listado de turnos en el
     * poroceso de entrega de documentos.
     */
    public final static String LISTAS_ENTREGA_MULTIPLE = "LISTAS_ENTREGA_MULTIPLE";

    /**
     * Constante que representa el nombre del campo del formulario que contiene
     * el nï¿½mero de anotaciones desde el cual el usuario quiere visualizar.
     */
    public static String CAMPO_NUM_ANOTACIONES_DESDE = "CAMPO_NUM_ANOTACIONES_DESDE";

    /**
     * Constante que representa el nombre del campo del formulario que contiene
     * el nï¿½mero de anotaciones total que posee el folio.
     */
    public static String CAMPO_NUM_ANOTACIONES_TOTAL = "CAMPO_NUM_ANOTACIONES_TOTAL";

    /**
     * Constante que representa el nombre del campo del formulario para
     * verificar si estï¿½ en segregaciï¿½n.
     */
    public static String CAMPO_ES_SEGREGACION = "CAMPO_ES_SEGREGACION";

    /**
     * Constante con el nombre del atributo en sesion que contiene las
     * anotaciones a heredar.
     */
    public static String ANOTACIONES_A_HEREDAR = "ANOTACIONES_A_HEREDAR";

    /**
     * OSBERT LINERO - IRIDIUM Telecomunicaciones e informï¿½tica Ltda. Cambio
     * realizado para resolver requerimiento 092 - Incidencia Mantis 02940
     * Constante que representa el atributo en session, para verificar si se
     * deshicieron los cambios en calificaciï¿½n.
     */
    public static String DESHICIERON_CAMBIOS_CALIFICACION = "DESHICIERON_CAMBIOS_CALIFICACION";

    /**
     * @author : Julio Alcazar
     * @change : Se declaran las constantes LISTADO_CIRCULOS_SIR y
     * LISTA_MATRICULAS_TRASLADO para la navegacion en el proceso traslado folio
     * Caso Mantis : 0007123
     */
    public static final String LISTADO_CIRCULOS_SIR = "LISTADO_CIRCULOS_SIR";

    public static final String LISTA_MATRICULAS_TRASLADO = "LISTA_MATRICULAS_TRASLADO";

    /**
     * @author : Julio Alcazar
     * @change : Se declaran las constantes para la navegacion en el proceso
     * traslado folio Caso Mantis : 0007676: Acta - Requerimiento No 247 -
     * Traslado de Folios V2
     */
    public static final String TRASLADO_ORIGEN = "TRASLADO_ORIGEN";

    public static final String TRASLADO_DESTINO = "TRASLADO_DESTINO";

    public static final String CIRCULO_ORIGEN = "CIRCULO_ORIGEN";

    public static final String CIRCULO_DESTINO = "CIRCULO_DESTINO";

    public static final String LISTADO_CIRCULOS_NOSIR = "LISTADO_CIRCULOS_NOSIR";

    /*
     * @author      : Julio Alcï¿½zar Rivas
     * @change      : nueva constante para manejar los usuarios con  el rol testamentos
     * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
     */
    public static final String LISTA_CAL_TESTAMENTOS = "LISTA_CAL_TESTAMENTOS";

    /**
     * @author : Diana Lora
     * @Change: Nueva constante para controlar el horario del reparto notarial
     * de minutas. Caso Mantis: 0010397: Acta - Requerimiento No 063_151 -
     * Ajustes Reparto Notarial
     */
    public static final String REPARTO_MINUTAS_EN_HORARIO = "REPARTO_MINUTAS_EN_HORARIO";

    /**
     * @author : Edgar Lora
     * @change : Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes
     * Reparto Notarial
     */
    public static final String REPARTO_NOTARIAL_EN_HORARIO = "REPARTO_NOTARIAL_EN_HORARIO";

    /**
     * @author : Carlos Mario Torre Urina
     * @casoMantis :
     * @actaRequerimiento :
     * @change : Nueva constante para manejar listado municipio origen.
     */
    public static final String MUNICIPIO_ORIGEN = "MUNICIPIO_ORIGEN";
    /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
     */
    public static final String VERSION = "VERSION";

    protected WebKeys() {
    }

    /**
     * @author : Ellery David Robles Gï¿½mez
     * @change : Se declaran las constantes LISTADO_FUNDAMENTOS_SIR,
     * LISTADO_FUNDAMENTOS_ASOCIADOS, ELIMINAR_FUNDAMENTOS_CHECKBOX Caso Mantis
     * : 14985
     */
    public static final String LISTADO_FUNDAMENTOS_SIR = "LISTADO_FUNDAMENTOS_SIR";
    public static final String LISTADO_FUNDAMENTOS_ASOCIADOS = "LISTADO_FUNDAMENTOS_ASOCIADOS";
    public static final String ELIMINAR_FUNDAMENTOS_CHECKBOX = "ELIMINAR_FUNDAMENTOS_CHECKBOX";

    /**
     * @Autor: Edgar Lora
     * @Mantis: 003_589
     */
    public static final String IDENTIFICADOR_DE_SESSION = "IDENTIFICADOR_DE_SESSION";

    public static final String RANGO_FECHA_INI = "RANGO_FECHA_INI";

    public static final String RANGO_FECHA_FIN = "RANGO_FECHA_FIN";

    /**
     * Esta constante identifica el parï¿½metro del objeto HttpSession que
     * almacena la lista de objetos CuentasBancarias
     */
    public static final String LISTA_CUENTAS_BANCARIAS = "LISTA_CUENTAS_BANCARIAS";
    
    public static final String LISTA_CUENTAS_BANCARIAS_CIRCULO = "LISTA_CUENTAS_BANCARIAS_CIRCULO";
    
    public static final String CUENTA_BANCARIA_ESTADOS = "CUENTA_BANCARIA_ESTADOS";
    
    public static final String CANALES_RECAUDO_ESTADOS = "CANALES_RECAUDO_ESTADOS";
    
    public static final String CIRCULO_CANAL_TIPO_PAGO = "CIRCULO_CANAL_TIPO_PAGO";
    
    public static final String CAMPOS_CAPTURA = "CAMPOS_CAPTURA";
    
    public static final String LISTA_CAMPOS_CAPTURA_X_FORMA = "LISTA_CAMPOS_CAPTURA_X_FORMA";
    
    public static final String LISTA_CUENTAS_BANCARIAS_X_CIRCULO = "LISTA_CUENTAS_BANCARIAS_X_CIRCULO";
    
    public static final String CIRCULO_TIPO_PAGO_ESTADOS = "CIRCULO_TIPO_PAGO_ESTADOS";
}
