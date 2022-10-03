package gov.sir.core.negocio.modelo.constantes;

/**
 * Clase que define las constantes relacionadas con la minuta. 
 * @author ppabon
 */
public final class CMinuta  {
	
	/**  Constante que indica el identificador de la minuta.**/	
	public final static String SOLICITUD_ID = "SOLICITUD_ID";
	
	/**  Constante que indica el idworkflow del turno al cual esta asociada.**/	
	public final static String TURNO_ID_WORKFLOW = "TURNO_ID_WORKFLOW";
	
	/**  Constante que indica el número de folios.**/
	public final static String NRO_FOLIOS_MINUTA = "NRO_FOLIOS_MINUTA";		
	
	/**  Constante que indica el tipo de reparto.**/
	public final static String TIPO_REPARTO = "TIPO_REPARTO";

	/**  Constante que indica el tipo de reparto Ordinario.**/
	public final static String ORDINARIO = "ORDINARIO";

	/**  Constante que indica el tipo de reparto Extraordinario.**/
	public final static String EXTRAORDINARIO = "EXTRAORDINARIO";
	
	/**  Constante que indica el tipo de acción notarial.**/
	public final static String ID_TIPO_ACCION_NOTARIAL = "ID_TIPO_ACCION_NOTARIAL";

	/**  Constante que indica el tipo de acción notarial.**/
	public final static String TIPO_ACCION_NOTARIAL = "TIPO_ACCION_NOTARIAL";
	
	/**  Constante que indica el tipo de acción notarial.**/
	public final static String ACCION_NOTARIAL_CON_CUANTIA = "ACCION_NOTARIAL_CON_CUANTIA";
	
	/**  Constante que indica la cuantia de la minuta.**/
	public final static String CUANTIA = "CUANTIA";
	
	/**  Constante que indica las unidades de la minuta.**/
	public final static String UNIDADES = "UNIDADES";
	
	
	/**  Constante que indica la cuantia del acto minuta.**/
	public final static String CUANTIA_ACTO = "CUANTIA_ACTO";
	
	/**  Constante que indica las unidades del acto minuta.**/
	public final static String UNIDADES_ACTO = "UNIDADES_ACTO";	
	
	/**  Constante que indica las observaciones al reparto notarial de minutas.**/
	public final static String OBSERVACIONES_REPARTO = "OBSERVACIONES_REPARTO";
	
	/**  Constante que indica la entidad pública dentro del reparto notarial de minutas.**/
	public final static String ENTIDAD_PUBLICA = "ENTIDAD_PUBLICA";	
	
	/**  Constante que indica la entidad pública dentro del reparto notarial de minutas.**/
	public final static String ENTIDAD_PUBLICA_EXENTO = "ENTIDAD_PUBLICA_EXENTO";	
	
	/**  Constante que indica el otorgante natural dentro del reparto notarial de minutas.**/
	public final static String OTORGANTE_NATURAL = "OTORGANTE_NATURAL";		
	
	/**  Constante que indica el otorgante natural es exento.**/
	public final static String OTORGANTE_NATURAL_EXENTO = "OTORGANTE_NATURAL_EXENTO";	
	
	/**  Constante que indica el otorgante natural es exento.**/
	public final static String EXENTO = "EXENTO";	
	
	/**  Constante que indica el circulo notarial para el reparto de minutas.**/
	public final static String CIRCULO_NOTARIAL = "CIRCULO_NOTARIAL";	
	
	/** Constante que indica el nombre del circulo notarial de la minuta*/
	public static final String CIRCULO_NOTARIAL_NOM = "CIRCULO_NOTARIAL_NOM"; 
	
	/** Constante que indica el identificador circulo notarial de la minuta*/
	public static final String CIRCULO_NOTARIAL_ID = "CIRCULO_NOTARIAL_ID";		
	
	/** Constante que indica el número de impresiones de actas en el reparto notarial de minutas*/
	public static final String NUMERO_ACTAS = "NUMERO_ACTAS";
	
	/** Constante que indica el número de impresiones de el imprimible de resúmen del reparto notarial*/
	public static final String NUMERO_RESUMENES = "NUMERO_RESUMENES";		
	
    public static final String CATEGORIA_NOM = "CATEGORIA_NOM"; 

	public static final String NOTARIA = "NOTARIA"; 
	public static final String ESTADO = "ESTADO"; 
	
	public static final String ID_MINUTA = "ID_MINUTA";
	
	public static final String MINUTA = "MINUTA";
	
	public static final String FECHA_CREACION= "FECHA_CREACION";
	
	public static final String FECHA_REPARTO="FECHA_REPARTO";
	
	public static final String NUMERO_REPARTO="NUMERO_REPARTO";
	
	/**  Constante que indica las observaciones al restitución notarial de minutas.**/
	public final static String OBSERVACIONES_RESTITUCION = "OBSERVACIONES_RESTITUCION";
	
	/**  Constante que indica si se desea generar imprimible.**/
	public final static String IMPRIMIBLE = "IMPRIMIBLE";
	
	/** Constante que indica la lista de actos asociados al reparto */
	//public final static String LISTA_ACTOS_REPARTO = "LISTA_ACTOS_REPARTO";
	
	public final static String ACCION_NOTARIAL = "ACCION_NOTARIAL";
	
	public final static String IMPRIMIR_CONSTANCIA = "IMPRIMIR_CONSTANCIA";
        /**
         * @author: Diana Lora
         * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         * Categorización de las minutas radicadas: Si la solicitud contiene varios
         * actos o negocios jurídicos, el sistema debe permitir marcar o seleccionar el
         * acto o la cuantía que determinará la categorización de la minuta.
         */
        public final static String CUANTIA_SELECCIONADA = "CUANTIA_SELECCIONADA";

        public final static String UNIDAD_SELECCIONADA = "UNIDAD_SELECCIONADA";

        public final static String ID_ACCION_NOTARIAL_SELECCIONADA = "ID_ACCION_NOTARIAL_SELECCIONADA";

}
