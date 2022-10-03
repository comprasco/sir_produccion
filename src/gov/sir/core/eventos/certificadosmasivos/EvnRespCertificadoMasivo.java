package gov.sir.core.eventos.certificadosmasivos;


import java.util.Hashtable;
import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Liquidacion;


/**
 * Este evento de respuesta trae información sobre certificados, de la capa de negocio
 * @author ppabon
 */
public class EvnRespCertificadoMasivo extends EvnSIRRespuesta{

	
	/**Esta constante define el tipo respuesta que se envia a la accion de negocio cuando se siguio la ruta de confirmar
	 * luego de la entrega de los certificados*/	
	public static final String ENTREGAR_CONFIRMAR = "ENTREGAR_CONFIRMAR";
	
	/**Esta constante define el tipo respuesta que se envia a la accion de negocio cuando se siguio la ruta de negar
	 * luego de la entrega de los certificados*/
	public static final String ENTREGAR_NEGAR = "ENTREGAR_NEGAR";
	
	/**Esta constante define la acción para llamar la liquidación de certificados masivos*/
	public static final String LIQUIDAR = "LIQUIDAR";	
	
	/**
	 * Constante que identifica que se desea agregar una
	 * nueva matrícula a la solicitud
	 */
	public final static String AGREGAR_VERIFICADO = "AGREGAR_VERIFICADO";

	public static final String AGREGAR_DE_ARCHIVO = "AGREGAR_DE_ARCHIVO";
	
	
	/**
	 * Liquidación efectuada para la solicitud atendida
	 */
	private Liquidacion liquidacion;
	
	
	/** Variable para guardar las validaciones que se realizaron a las matriculas cuando se realiza
	 * una solicitud masiva de certificados*/
	private Hashtable validacionesMasivos;		
	
	/**
	 * El número de anotaciones del folio asociado a la matrícula.
	 */
	private String numeroAnotaciones;

	/**
	 * Indica si el folio es de mayor extensión.
	 */
	private String mayorExtension;

	private List matriculasArchivo = null;

	private List copiasMatriculas = null;


	/**
	 * Constructor del evento de respuesta con el tipo de respuesta unicamente.
	 * @param folio Folio
	 */
	public EvnRespCertificadoMasivo(String tipoEvento) {
		super(tipoEvento,tipoEvento);
	}
	
	/**
	 * Constructor del evento de respuesta con el tipo de respuesta unicamente.
	 * @param folio Folio
	 */
	public EvnRespCertificadoMasivo(String tipoEvento, Liquidacion liquidacion) {
		super(tipoEvento,tipoEvento);
		this.liquidacion = liquidacion;
	}	
	
	/**
	 * Constructor del evento de respuesta con el tipo de respuesta unicamente.
	 * @param folio Folio
	 */
	public EvnRespCertificadoMasivo(String matricula, String tipoEvento) {
		super(matricula,tipoEvento);
	}
	

	public EvnRespCertificadoMasivo(List matriculasArchivo, List copiasMatriculas, String tipoEvento) {
		super(tipoEvento,tipoEvento);
		this.setMatriculasArchivo(matriculasArchivo);
		this.setCopiasMatriculas(copiasMatriculas);
	}

	/**
	 * @return
	 */
	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

	/**
	 * @return
	 */
	public Hashtable getValidacionesMasivos() {
		return validacionesMasivos;
	}

	/**
	 * @param hashtable
	 */
	public void setValidacionesMasivos(Hashtable hashtable) {
		validacionesMasivos = hashtable;
	}
	
	/**
	 * @return
	 */
	public String getMayorExtension() {
		return mayorExtension;
	}

	/**
	 * @return
	 */
	public String getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	/**
	 * @param string
	 */
	public void setMayorExtension(String string) {
		mayorExtension = string;
	}

	/**
	 * @param string
	 */
	public void setNumeroAnotaciones(String string) {
		numeroAnotaciones = string;
	}
	
	//daniel
	public void setMatriculasArchivo(List matriculasArchivo) {
		this.matriculasArchivo = matriculasArchivo;
	}
	
	public List getMatriculasArchivo() {
		return this.matriculasArchivo;
	}
	
	public void setCopiasMatriculas(List copiasMatriculas) {
		this.copiasMatriculas = copiasMatriculas;
	}
	
	public List getCopiasMatriculas() {
		return this.copiasMatriculas;
	}
}
