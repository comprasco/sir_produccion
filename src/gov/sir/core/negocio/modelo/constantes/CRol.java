package gov.sir.core.negocio.modelo.constantes;


/**
 * @author jmendez
 *
 */
public final class CRol {
    public static final String ID_ROL = "ID_ROL";
    public static final String ROL_NOMBRE = "ROL_NOMBRE";
    public static final String ROL_DESCRIPCION = "ROL_DESCRIPCION";
    
	public static final String ROL_ABOGADO = "SIR_ROL_CALIFICADOR";
	
	public static final String ROL_DIGITADOR = "SIR_ROL_DIGITADOR";
	
	public static final String SIR_ROL_CAJERO = "SIR_ROL_CAJERO";
	
	public static final String SIR_ROL_RESPONSABLE_CORRECCIONES = "SIR_ROL_RESPONSABLE_CORRECCIONES"; 
	
	public static final String SIR_ROL_ENCARGADO_ANTIGUO_SISTEMA = "SIR_ROL_ENCARGADO_ANTIGUO_SISTEMA";
	
	public static final String SIR_ROL_MESA_CONTROL_CORRECCIONES = "SIR_ROL_MESA_CONTROL_CORRECCIONES";
	
	public static final String SIR_ROL_CAJERO_CORRECCIONES = "SIR_ROL_CAJERO_CORRECCIONES";
	
	public static final String SIR_ROL_CAJERO_CONSULTAS = "SIR_ROL_CAJERO_CONSULTAS";
	
	public static final String SIR_ROL_ADMINISTRADOR_NACIONAL = "SIR_ROL_ADMINISTRADOR_NACIONAL";
	
	public static final String SIR_ROL_REGISTRADOR = "SIR_ROL_REGISTRADOR";
	
	public static final String SIR_ROL_USUARIO_ADMINISTRATIVO = "SIR_ROL_USUARIO_ADMINISTRATIVO";
	
	public static final String SIR_ROL_CAJERO_CERTIFICADOS="SIR_ROL_CAJERO_CERTIFICADOS";
	
	public static final String SIR_ROL_CAJERO_CERTIFICADOS_SIR="SIR_ROL_CAJERO_CERTIFICADOS_SIR";
	
	public static final String SIR_ROL_CAJERO_CERT_MASIVOS="SIR_ROL_CAJERO_CERT_MASIVOS";
	
	public static final String SIR_ROL_CAJERO_DEVOLUCIONES="SIR_ROL_CAJERO_DEVOLUCIONES";
	
	public static final String SIR_ROL_CAJERO_REGISTRO="SIR_ROL_CAJERO_REGISTRO";
	
	public static final String SIR_ROL_CAJERO_RN="SIR_ROL_CAJERO_RN";
	
	public static final String SIR_ROL_CAJERO_FOTOCOPIAS="SIR_ROL_CAJERO_FOTOCOPIAS";
	
	public static final String SIR_ROL_MESA_CONTROL = "SIR_ROL_MESA_CONTROL";
	
	public static final String SIR_ROL_ENTREGA_DOCUMENTOS = "SIR_ROL_ENTREGA_DOCUMENTOS";
	
	public static final String SIR_ROL_ENTREGA_EXTERNA = "SIR_ROL_ENTREGA_EXTERNA";
	
	public static final String SIR_ROL_BUSCADOR_AS="BUSCADOR ANTIGUO SISTEMA";
        
        public static final String SIR_ROL_NOTIFICADOR_NOTAS_DEV = "SIR_ROL_NOTIFICADOR_NOTAS_DEV";

        /**
         * @author: Henry Gómez Rocha.
         * @change: Se crea la variable "SIR_ROL_USUARIO_OPERATIVO_CONSULTAS" para poder remover
         *          del rol "USUARIO OPERATIVO CONSULTAS" la fase "ENTREGAR CONSULTA COMPLEJA O EXENTA".
         * Mantis:  5166 Acta - Requerimiento No 151.
         */
        public static final String SIR_ROL_USUARIO_OPERATIVO_CONSULTAS = "SIR_ROL_USUARIO_OPERATIVO_CONSULTAS";

        //AHERRENO 28/10/2010
        //Se crean variables para llamado de roles
	public static final String SIR_ROL_CONFRONTADOR = "SIR_ROL_CONFRONTADOR";

        public static final String SIR_ROL_TESTAMENTOS = "SIR_ROL_TESTAMENTOS";
        
        public static final String SIR_ROL_CERTIFICADOS_ASOCIADOS_REGISTRO = "SIR_ROL_CERTIFICADOS_ASOCIADOS_REGISTRO";

        /**
         * @author              : Ellery David Robles Gómez.
         * @casoMantis          : 08539.
         * @actaRequerimiento   : 151. Proceso Certificados Error al enviar Ampliacion Tradicion.
         * @change              : Se crea la variable "SIR_ROL_AMPLIACION_TRADICION" para identificar los
         *                        tipos de certificados enviados a la fase "AMPLIACION_TRADICION".
         */
        public static final String SIR_ROL_AMPLIACION_TRADICION = "SIR_ROL_AMPLIACION_TRADICION";
        
        /**
         * @author: Edgar Lora
         * @change: Se crea la variable "SIR_ROL_AUXILIAR_CORRECCIONES" para poder consultar el rol de auxiliar
         *          de correciones.
         */
        public static final String SIR_ROL_AUXILIAR_CORRECCIONES = "SIR_ROL_AUXILIAR_CORRECCIONES";

        /**
                 * @author: Edgar Lora
                 * @change: Se crea la variable "SIR_ROL_ACTUACIONES_ADMINISTRATIVAS" para poder consultar el rol de actuaciones
                 *          administrativas.
                 */
        public static final String SIR_ROL_ACTUACIONES_ADMINISTRATIVAS = "SIR_ROL_ACTUACIONES_ADMINISTRATIVAS";

        /**
                 * @author: Edgar Lora
                 * @change: Se crea la variable "SIR_ROL_AUXILIAR_ACTUACIONES_ADMINISTRATIVAS" para poder consultar el rol de auxiliar 
                 *          actuacionesadministrativas.
                 */
        public static final String SIR_ROL_AUXILIAR_ACTUACIONES_ADMINISTRATIVAS = "SIR_ROL_AUXILIAR_ACT_ADMIN";

        /**
         * @author: Edgar Lora
         * @change: Se crea la variable "SIR_ROL_ENTREGA" para poder consultar el rol de entregas.
         */
        public static final String SIR_ROL_ENTREGA = "SIR_ROL_ENTREGA";

	
}
