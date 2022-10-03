package gov.sir.core.negocio.modelo.constantes;

/**
 * @author ppabon
 *
 */
public class CRespuesta {

    public static final String NEGAR = "NEGAR";
    public static final String RECHAZADO = "RECHAZADO";
    public static final String OK = "OK";
    public static final String EXITO = "EXITO";
    public static final String CONFIRMAR = "CONFIRMAR";
    public static final String PAGO_MAYOR_VALOR = "MAYOR_VALOR";
    public static final String AJUSTAR_CALIFICACION = "AJUSTAR_CALIFICACION";
    public static final String AJUSTAR_MESA_CONTROL = "AJUSTAR_MESA_CONTROL";
    public static final String AJUSTAR = "AJUSTAR";
    public static final String REVISAR_CONFRONTACION_CORRECTIVA = "REVISAR_CONFRONTACION_CORRECTIVA";
        
    public static final String NEGAR_REVISION = "NEGAR_REVISION";
    public static final String NEGAR_MESA = "NEGAR_MESA";
    public static final String NEGAR_FIRMA = "NEGAR_FIRMA";
    /**
     * Constante que indica la respuesta de devolucion de Registro
     */
    public static final Object DEVOLUCION = "DEVOLUCION";
    public static final Object FRACASO = "FRACASO";
    public static final String CONFIRMADO = "CONFIRMADO";
    public static final String ELIMINACION = "ELIMINACION";
    public static final String INSCRITO = "INSCRITO";
    public static final String MAYORVALOR = "MAYOR VALOR";
    public static final String DEVUELTO = "DEVUELTO";
    public static final String DEVOLVER_CALIFICACION = "CALIFICACION";
    public static final String CREADO = "CREADO";
    public static final String MAYOR_VALOR = "MAYOR_VALOR";
    public static final String CORRECCION = "CORRECCION";
    public static final String DIGITACION = "DIGITACION";
    public static final String PERSONAL = "PERSONAL";
    public static final String CORRESPONDENCIA = "CORRESPONDENCIA";
    public static final String EXISTE = "EXISTE";
    public static final String CREACION_DEL_TURNO = "CREACION DEL TURNO";
    public static final String RECURSOS = "RECURSOS";
    public static final String REC_INTERPUESTO = "REC_INTERPUESTO";
    
    // Se agregan constantes para requerimiento de tramite suspension
    public static final String TRAMITE_SUSP_TEMP = "TRAMITE_SUSP_TEMP";
    public static final String TRAMITE_SUSP_PREV = "TRAMITE_SUSP_PREV";
    /**
     * Constante que indica que un turno debe ser regresado a la fase de
     * corrección de la calificación.
     */
    public static final String CORRECCION_CALIFICACION = "AJUSTAR";
    public static final String INSCRIPCION_PARCIAL = "INSCRIPCION_PARCIAL";
    public static final String INSCRIPCIONPARCIAL = "INSCRIPCION PARCIAL";
    public static final String CORRECCION_SIMPLE = "CORRECCION_SIMPLE";
    /**
     * Constante que identifica que se debe enviar a Notificador Notas Devolutivas
     */
    public static final String NOTA_DEVOLUTIVA = "NOTA_DEVOLUTIVA";
    /**
     * Modifica Pablo Quintana Junio 16 2008 constante que identifica que un
     * testamento es enviado a fase de corrección de encabezado
     */
    public static final String ERROR_ENCABEZADO = "ERROR_ENCABEZADO";
    /**
     * Modifica Pablo Quintana Junio 19 2008 Constante que identifica que un
     * testamento es devuelto de mesa control a testamento
     */
    public static final String DEVOLVER_MESA_TESTAMENTO = "DEVOLVER_MESA_TESTAMENTO";
    public static final String DEVOLVER_FIRMA_TESTAMENTO = "DEVOLVER_FIRMA_TESTAMENTO";
    public static final String AMPLIACION_TRADICION = "AMPLIACION_TRADICION";
    public static final Object DEVOLVER_A_CONFRONTACION = "DEVOLVER_A_CONFRONTACION";
    public static final String REANOTACION = "REANOTACION";
    public static final String MAYOR_VALOR_ID = "999";
    public static final String DEVUELTO_FINALIZAR = "DEVUELTO_FINALIZAR";
    
    public static String getRespuestaREL(String phase){
        String faseREL = "";
        if(phase.equals(OK)){
            return INSCRITO;
        } else if(phase.equals(DEVOLUCION)){
            return DEVUELTO;
        } else if(phase.equals(INSCRIPCION_PARCIAL)){
            return INSCRIPCION_PARCIAL;
        }
        return null;
    }
    
}
