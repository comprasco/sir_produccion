package gov.sir.core.negocio.modelo.constantes;

/**
 * @author mmunoz
 */
public final class CTipoNota {
	
	public static final String ID_TIPO_NOTA = "ID_TIPO_NOTA";
	
	public static final String IDENTIFICADOR = "IDENTIFICADOR";
	
	public static final String ID_SUBTIPO_NOTA = "ID_SUBTIPO_NOTA";
	
	public static final String NOMBRE_TIPO_NOTA = "NOMBRE_TIPO_NOTA";
	
	public static final String VISIBILIDAD_TIPO_NOTA = "VISIBILIDAD_TIPO_NOTA"; 

	public static final String DEVOLUTIVA = "DEVOLUTIVA";
          /*
            @author Carlos Torres
            @change Se agrega la constante version
            @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
        */
        public static final String VERSION ="VERSION";
	
	/*
                * @autor : HGOMEZ 
                * @mantis : 11631 
                * @Requerimiento : 036_453_Correccion_Actuaciones_Administrativas 
                * @descripcion : Se agrega constante ACTIVA.
                */
        
        public static final String ACTIVA = "ACTIVA";
        
	public static final String INFORMATIVA = "INFORMATIVA";
        
    public static final String DESCRIPCION = "TIPO_NOTA_DESCRIPCION";
    
	public static final String TABLE_NAME = "SIR_OP_TIPO_NOTA";
	
	
	/**
	* Constante para nota devolutiva El folio no existe. 
    */
	public static final String FOLIO_NO_EXISTE = "1";
	
	/**
	* Constante para nota devolutiva El folio es de mayor extensión.
	*/
	public static final String FOLIO_MAYOR_EXTENSION = "3";
	
	/**
	* Constante para nota devolutiva El folio está en trámite
	*/
	public static final String FOLIO_EN_TRAMITE = "4";
	
	/**
	* Constante para nota devolutiva El folio está bloqueado o en custodia
	*/
	public static final String FOLIO_EN_CUSTODIA = "5";
	
	
	/**
	* Constante para nota devolutiva El folio debe dinero
	*/
	public static final String FOLIO_DEBE_DINERO = "7";
	
	
	/**
	* Constante para nota devolutiva El folio está cerrado o anulado
	*/
	public static final String FOLIO_CERRADO = "9";
	
	
	/**
	* Constante para nota devolutiva El folio está en estado inconsistente
	*/
	public static final String FOLIO_INCONSISTENTE = "10";
	

	/**
	* Constante para nota devolutiva del vencimiento del mayor valor
	*/
	public static final String NOTA_DEVOLUTIVA_VENCIMIENTO_MAYOR_VALOR = "22063";
    }
