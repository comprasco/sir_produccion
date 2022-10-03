/*
 * Created on 10-nov-2004
 */
package gov.sir.core.negocio.modelo.constantes;

/**
 * @author dlopez
 * Clase que define las constantes para las naturalezas
 * juridicas de las anotaciones. 
 */
public final class CNaturalezaJuridica {
	
	public static final String ID_NATURALEZA_JURIDICA = "ID_NATURALEZA_JURIDICA";
	public static final String NOMBRE_NATURALEZA_JURIDICA = "NOMBRE_NATURALEZA_JURIDICA";
	public static final String DOMINIO_NATURALEZA_JURIDICA = "DOMINIO_NATURALEZA_JURIDICA";
	public static final String GRUPO_NATURALEZA_JURIDICA = "GRUPO_NATURALEZA_JURIDICA";
	public static final String HABILIDATO_CALIFICACION = "HABILIDATO_CALIFICACION";
         /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : Se agrega constate version
	 */
        public static final String VERSION = "VERSION";
	
	public static final String ENGLOBE = "0919";
	public static final String ANTIGUO_ENGLOBE = "913";
	public static final String DESENGLOBE = "0915";
	
	public static final String COMPRAVENTA = "0125";
	public static final String COMPRAVENTA_PARCIAL = "0126";
	
	public static final String ACLARACION = "0901";
	public static final String ACTUALIZACION_AREA = "0902";
	public static final String ACTUALIZACION_DE_LINDEROS = "0903";
	public static final String ACTUALIZACION_DE_NOMENCLATURA = "0904";
	public static final String DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA = "0910";
	public static final String DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO = "0911";
	public static final String DECLARACION_PARTE_RESTANTE = "0913";
	public static final String LOTEO = "0920";
	public static final String RELOTEO = "0924";
	
	public static final String NATURALEZA_NULA = "999";
	
	/*Constantes de naturaleza Juridica que debe mostrar alerta al consultar folio*/
	public static final String PATRIMONIO_DE_FAMILIA = "370";
	public static final String AFECTACION_A_VIVIENDA_FAMILIAR = "0304";
	public static final String AFECTACION_A_VIVIENDA_FAMILIAR_2 = "381";
	
	public static final String FECHA_NUEVAS_NATURALEZAS = "03/07/2001";
	
        /**
        * @Autor: Edgar Lora
        * @Mantis: 0013038
        * @Requerimiento: 060_453
        */
        public static final String NATURALEZA_PARA_VALIDAR_LINDEROS = "S";
        public static final String NATURALEZA_OPCIONAL_PARA_VALIDAR_LINDEROS = "N";
}
