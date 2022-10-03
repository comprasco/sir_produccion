package gov.sir.core.negocio.modelo.constantes;

/**
 * Clase que define las constantes relacionadas con el ciudadano.
 * @author ppabon
 */
public final class CCiudadano {

	/** Constante que identifica el campo donde se solicita el nombre del ciudadano*/
	public final static String NOMBRE = "NOMBRE";

	/** Constante que identifica el campo donde se solicita el segundo apellido del ciudadano*/
	public final static String APELLIDO2 = "APELLIDO2";

	/** Constante que identifica el campo donde se solicita el primer apellido del ciudadano*/
	public final static String APELLIDO1 = "APELLIDO1";

	/** Constante que identifica el campo donde se solicita el id del ciudadano. Es generado automáticamente generado*/
	public final static String IDCIUDADANO = "IDCIUDADANO";

	/** Constante que identifica el campo donde se solicita el tipo del documento del ciudadano. Ejemplo : Cédula, NIT, Cédula de extranjería, Tarjeta de Identidad.*/
	public final static String TIPODOC = "TIPODOC";
        
        /** Constante que identifica el campo donde se solicita el tipo de persona del ciudadano. Ejemplo : Natural, Juridica.*/
	public final static String TIPOPERSONA = "TIPOPERSONA";
        
        /** Constante que identifica el campo donde se solicita el sexo del ciudadano. Ejemplo : Masculino, Femenino*/
	public final static String SEXO = "SEXO";

	/** Constante que identifica el campo donde se solicita el teléfono*/
	public final static String TELEFONO = "TELEFONO";

	/** Constante que identifica el campo donde se solicita el número del documento de identificación del ciudadano*/
	public final static String DOCUMENTO = "DOCUMENTO";

	/** Constante que identifica el tipo de documento como secuencia*/
	public final static String TIPO_DOC_ID_SECUENCIA = "SE";

	/** Constante que identifica el tipo de documento como cédula*/
	public final static String TIPO_DOC_ID_CEDULA = "CC";

	/** Constante que identifica el tipo de documento como cédula de extranjería*/
	public final static String TIPO_DOC_ID_CEDULA_EXTRANJERIA = "CE";

	/** Constante que identifica el tipo de documento nit*/
	public final static String TIPO_DOC_ID_NIT = "NIT";

	/** Constante que identifica el tipo de documento tarjeta de identidad*/
	public final static String TIPO_DOC_ID_TARJETA = "TI";
        
        /** Constante que identifica el tipo de persona juridica*/
	public final static String TIPO_PERSONA_JURIDICA = "JURIDICA";
        
        /** Constante que identifica el tipo de persona natural*/
	public final static String TIPO_PERSONA_NATURAL = "NATURAL";

}
