/*
 * Created on 01-oct-2004
*/
package gov.sir.core.negocio.modelo.constantes;

/**
 * @author jfrias
*/
public final class CActo {

	public static final String TIPO_ACTO_CARGADO = "TIPO_ACTO_CARGADO";
	public static final String NUM_ACTOS = "NUM_ACTOS";
	public static final String ACTO = "ACTO";
	public static final String TIPO_ACTO = "TIPO_ACTO";
	public static final String TIPO_DERECHO = "TIPO_DERECHO";
	public static final String VALOR_ACTO = "VALOR_ACTO";
	public static final String IMPUESTO= "IMPUESTO";
	public static final String TIPO_TARIFA = "TIPO_TARIFA";
	public static final String COBRA_IMPUESTO= "COBRA_IMPUESTO";
	public static final String VALOR_OTRO_IMPUESTO= "VALOR_OTRO_IMPUESTO";
	public static final String DESCRIPCION= "DESCRIPCION";
	public static final String CUANTIA= "CUANTIA";
	public static final String ID_ACTO= "ID_ACTO";
	public static final String ID_ACTO_EDICION= "ID_ACTO_EDICION";
	public static final String VALOR_DERECHOS = "VALOR_DERECHOS";
	
	public static final String CUANTIA_EDICION = "CUANTIA_EDICION";
	
	/**
	* Constante para identificar que se desea consultar un tipo de tarifa de derechos registrales. 
	*/
	public static final String DERECHO_REGISTRAL = "DERECHO_REGISTRAL";
	

	/**
	* Constante para identificar que se desea consultar un tipo de tarifa basado en la tasa diaria para multas. 
	*/
	public static final String TASA_DIARIA_MULTA = "TASA_DIARIA_MULTA";
	
	/**
	* Constante para identificar que se desea consultar un tipo de tarifa basado en certificados. 
	*/
	public static final String CERTIFICADOS = "CERTIFICADOS";
	

	
	/**
	* Constante para identificar que un <code>Acto </code> es código 80 (CESION DE BIENES FISCALES).
	*/
	public static final String  CESION_TITULO_GRATUITO = "30";

	/**
	* Constante para identificar que un <code>Acto </code> es código 9 (PATRIMONIO DE FAMILIA).
	*/
	public static final String ID_PATRIMONIO_DE_FAMILIA = "9";
	
	/**
	* Constante para identificar que un <code>Acto </code> es código 2 (HIPOTECA).
	*/	
	public static final String ID_HIPOTECA = "2";		
	
	
	
	/**
	* Constante para identificar que un <code>Acto </code> es código 80 (CESION DE BIENES FISCALES).
	*/
	public static final String CESION_BIENES_FISCALES = "80";

	
	/**
	* Constante para identificar que un <code>Acto </code> es código 81 (VENTA PLANOS CATASTRALES).
	*/
	public static final String VENTA_PLANOS_CATASTRALES = "81";

	/**
        * @author Fernando Padilla
	* @change Requeimiento externo 118, Caso Constante para identificar que
        *         un <code>Acto </code> es código 38 (VIVIENDA DE INTERES SOCIAL).
	*/
	public static final String VIVIENDA_INTERES_SOCIAL = "38";
	
	/**
	* Constante para identificar que un <code>Acto </code> es tiene tipo calculo manual.
	*/
	public static final String MANUAL = "M";
	
	/**
	* Constante para identificar que un <code>Acto </code> es tiene tipo calculo absoluto.
	*/
	public static final String ABSOLUTO = "A";
	
	/**
	* Constante para identificar que un <code>Acto </code> es tiene tipo calculo porcentual.
	*/
	public static final Object PORCENTUAL = "P";
	
	/**
	* Constante para identificar que un <code>Acto </code> el valor impuesto manual de un acto.
	*/
	public static final String VALOR_IMPUESTO = "VALOR_IMPUESTO";
	
	/**
	* Constante para identificar que un <code>Acto </code> el tipo calculo de una aplicacion.
	*/
	public static final String TIPO_CALCULO = "TIPO_CALCULO";
	
	/**
	* Constante para identificar que un <code>Acto </code> el tipo de tarifa de impuesto es NORMAL.
	*/
	public static final String NORMAL = "N";
	
	/**
	* Constante para identificar la posicion de un<code>Acto </code>en una lista
	*/
	public static final String POSICION = "POSICION";
	
	/**
	* Constante para identificar el id de la solicitud del acto
	*/
	public static final String ID_SOLICITUD = "ID_SOLICITUD";
    
	/**
	* Constante para identificar el id de la liquidacion del acto
	*/
	
	public static final String ID_LIQUIDACION = "ID_LIQUIDACION";	
	
	/**
	* Constante para identificar que un <code>Acto </code> el nombre tipo calculon.
	*/
	public static final String NOMBRE_TIPO_CALCULO = "NOMBRE_TIPO_CALCULO";
	
	
	/**
	* Constante para identificar que un <code>Acto </code> es de tipo EMBARGO. 
	*/
	public static final String EMBARGO ="10";
	
	
	/**
	* Constante para identificar que un <code>Acto </code> es código 64 (TESTAMENTOS).
	*/	
	public static final String ID_TESTAMENTOS = "64";
	
	/**
	* Constante para identificar que un <code>Acto </code> es código 87 (ACTO REVOCATORIA DE TESTAMENTO).
	*/	
	public static final String ID_REVOCATORIA_TESTAMENTO = "87";
	public static final Object TIPO_ACTO_REPRODUCCION_SELLOS = "12";	
	
}
