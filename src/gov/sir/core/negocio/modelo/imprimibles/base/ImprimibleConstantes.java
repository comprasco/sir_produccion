package gov.sir.core.negocio.modelo.imprimibles.base;

import java.awt.Font;

/**
 * @author gvillal
 * Interface que contiene constantes usadas por los imprimibles.
 */
public interface ImprimibleConstantes {
	/**	 */
	public static final String SEPARADOR1 = "================================================================================================================";
	/**	 */
	public static final String SEPARADOR2 =
	"---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"+
	"--------------";
	
	/**	 */
	public static final String SEPARADOR3_PEQ =
"SUPERINTENDENCIA DE NOTARIADO Y REGISTRO LA GUARDA DE LA FE PUBLICA OFICINA DE INSTRUMENTOS PUBLICOS "+
"SUPERINTENDENCIA DE NOTARIADO Y REGISTRO LA GUARDA DE LA FE PUBLICA OFICINA DE INSTRUMENTOS PUBLICOS "+
"SUPERINTENDENCIA DE NOTARIADO Y REGISTRO LA GUARDA DE LA FE PUBLICA OFICINA DE INSTRUMENTOS PUBLICOS "+
"SUPERINTENDENCIA DE NOTARIADO Y REGISTRO LA GUARDA DE LA FE PUBLICA OFICINA DE INSTRUMENTOS PUBLICOS "+
"SUPERINTENDENCIA DE NOTARIADO Y REGISTRO LA GUARDA DE LA FE PUBLICA OFICINA DE INSTRUMENTOS PUBLICOS ";

	/**	 */
	public static final String SEPARADOR4 = "..................................................................................................................................";
	/**	 */
	public static final String SEPARADOR5 = "*********************************************************************************************************************************";
	/**	 */
	public static final String ESPACIOS = "        ";
	//public static final String SEPARADOR_CONTADOR = "123456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789112345678911234567892";
	/**	 */
	public static final String PRIMER_APELLIDO_NULL = "(primer apellido no registrado)";
	/**	 */
	public static final String SEGUNDO_APELLIDO_NULL = "";
	/**	 */
	public static final String NOMBRE_NULL = "(nombre no registrado)";
	/**	 */
	public static final String RAZON_SOCIAL_NULL = "(razón social no registrada)";

	/**	 */
	public static final String SIGUIENTE_RECIBO = "Continua en el siguiente recibo";
        /**	 */
        public static final int TAM_LETRAMENUDA      = 7; // id usado para establecer la fuente
        public static final int TAM_LETRAMENUDA_FONT = 7; // tamaño de la fuente
        public static final int TAM_LETRAMENUDA_SEP  = TAM_LETRAMENUDA_FONT + 1;
        public static final int TAM_LETRAMENUDA_NUMLINECHARS = 250;
        public static final int TAM_LETRAMENUDA_FOTOCOPIAS = 170;

    /**Tamaños de Fuentes**/
	/**	 */
	public static final int TAM_TITULO_1 = 10;
	/**	 */
	public static final int TAM_TITULO_2 = 8;
	/**	 */
	public static final int TAM_PLANO = 8;
	/**	 */
	public static final int TAM_PLANO2 = 9;
	/**	 */
	public static final int TAM_PEQUE = 7;	
	/**	 */
	public static final int TAM_GRANDE = 15;
	/**	 */
	public static final int TAM_GRANDE2 = 12;
	/**	 */
	public static final int TAM_MICRO = 1;

	/**Coordenadas**/
	/**	 */
	public static final int MARGEN_IZQ = 36;
	
	/**	 */
	public static final int MARGEN_SUP = 50;

    /**Tipo de texto**/
	/**	 */
	public static final int TIPO_TEXTO_TITULO1 = Font.BOLD;
	/**	 */
	public static final int TIPO_TEXTO_TITULO2 = Font.BOLD;
	/**	 */
	public static final int TIPO_TEXTO_PLANO = Font.PLAIN;
	/**	 */
	public static final int TIPO_TEXTO_PLANO2 = Font.PLAIN;

	/**Estilos**/
	/**	 */
	public static final int TITULO1 = 1;
	/**	 */
	public static final int TITULO2 = 2;
	/**	 */
	public static final int PLANO = 3;	
	/**	 */
	public static final int PLANO2 = 16;	
	/**	 */
	public static final int TITULO_GRANDE = 4;
	/**	 */
	public static final int TITULO_GRANDE2 = 5;
	/**	 */
	public static final int TITULO_MICRO = 6;
	/**	 */
	public static final int PEQUE = 8;	

	/**Separación entre lineas.**/
	public static final int SEPARACION_LINE = 12;
	
	public static final int SEPARACION_LINE_PEQUE = 8;	

	/**Constantes de dimensionamiento del documento**/
	/**	 */
	public static final int MAXIMO_VERTICAL = 105*72/10+SEPARACION_LINE;
	
	public static final int TAMANO_FIRMA_ESTATICA = 2*72;
	public static final int TAMANO_ADICIONAL_FIRMA = 1*72;
	
	
	
	/**	 */
	public static final int MAXIMO_ANTES_FIRMA = MAXIMO_VERTICAL-TAMANO_FIRMA_ESTATICA;
	/**	 */
	public static final int MAX_NUM_MICRO = 500;
	/**	 */
	public static final int MAX_NUM_CHAR = 110;
	/**	 */
	public static final int MAX_NUM_CHAR_PEQUE = 132;	
	/**	 */
	public static final int MAX_NUM_CHAR_TITULO1 = 80;
	/**	 */
	public static final int LONG_LOGO = 15;
	/**	 */
	public static final int MAX_NUM_CHAR_MICRO = 1140;

	/**Texto**/
	public static final String CVALIDEZ_TEMP= "No tiene validez. Solo sirve para verificar información";

	public static final String CVALIDEZ= "No tiene validez sin la firma del registrador en la última página";

	/**Arreglo de meses del año.**/
	public static final String MESES[] = {
											"Enero","Febrero","Marzo",
											"Abril","Mayo","Junio",
											"Julio","Agosto","Septiembre",
											"Octubre","Noviembre","Diciembre"
											};
	
	public static final int ANCHO_TEXTO_MICRO1 = 525;
	public static final int MARGEN_BOTH_SIDES = 0;
        /**
        * @author: David Panesso
        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
        * Constante de dimensionamiento del ancho en carta del documento
        **/
        public static final int ANCHO_CARTA = 612;
}
