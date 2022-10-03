/*
 * Created on 06-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/** Clase que modela la información de las solicitudes de certificados
 * @author fceballos
 */
public class SolicitudCertificado extends Solicitud implements TransferObject {
	private Ciudadano ciudadano;
	private String condicionEntrega;
	private int numeroCertificados;
	private int numImpresiones;
	private DatosAntiguoSistema datosAntiguoSistema;
	private long numeroAnotaciones;
	private Documento documento;
	private String matriculaNoExistente;
	private boolean preliquidacion = false;
	private static final long serialVersionUID = 1L;
	/**
	 * Flag que indica que la solicitudCertificado esta siendo editada (antes de pagarse)
	 */
	private boolean editado = false;
		
	/**
	 * Flag que indica que la solicitudCertificado debe borrarse (antes de pagarse)
	 */
	private boolean eliminar = false;
	
	public boolean isEditado() {
		return editado;
	}
	public void setEditado(boolean editado) {
		this.editado = editado;
	}
	/** Método constructor por defecto */
	public SolicitudCertificado(){
		super();
	}
	/** Retorna el identificador del ciudadano solicitante  */
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	/** Cambia el identificador del ciudadano solicitante  */
	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}
	
	/** Retorna la condición de entrega
	 * @return condicionEntrega
	 */
	public String getCondicionEntrega() {
		return condicionEntrega;
	}

	/** Retorna el número de copias
	 * @return numeroCertificados
	 */
	public int getNumeroCertificados() {
		return numeroCertificados;
	}

	/** Cambia la condición de entrega
	 * @param string
	 */
	public void setCondicionEntrega(String string) {
		condicionEntrega = string;
	}

	/** Cambia el número de copias
	 * @param i
	 */
	public void setNumeroCertificados(int i) {
		numeroCertificados = i;
	}

	/** Retorna el número de impresiones
	 * @return
	 */
	public int getNumImpresiones() {
		return numImpresiones;
	}

	/** Cambia el número de impresiones
	 * @param i
	 */
	public void setNumImpresiones(int i) {
		numImpresiones = i;
	}

	/** Retorna el identificador de datos de antiguo sistema
	 * @return datosAntiguoSistema
	 */
	public DatosAntiguoSistema getDatosAntiguoSistema() {
		return datosAntiguoSistema;
	}

	/** Cambia el identificador de datos de antiguo sistema
	 * @param sistema
	 */
	public void setDatosAntiguoSistema(DatosAntiguoSistema sistema) {
		datosAntiguoSistema = sistema;
	}

	/** Retorna el número de anotaciones expedida en certificados
	 * @return numeroAnotaciones
	 */
	public long getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	/** Cambia el número de anotaciones expedida en certificados
	 * @param l
	 */
	public void setNumeroAnotaciones(long l) {
		numeroAnotaciones = l;
	}

	/** Retorna el identificador del documento asociado
	 * @return documento
	 */
	public Documento getDocumento() {
		return documento;
	}

	/** Cambia el identificador del documento asociado
	 * @param documento
	 */
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	/** Retorna el identificador de matrícula no existente en la base de datos, usado para certificados masivos
	 * @return matriculaNoExistente
	 */
	public String getMatriculaNoExistente() {
		return matriculaNoExistente;
	}

	/** Cambia el identificador de matrícula no existente en la base de datos, usado para certificados masivos
	 * @param string
	 */
	public void setMatriculaNoExistente(String string) {
		matriculaNoExistente = string;
	}
	public boolean isPreliquidacion() {
		return preliquidacion;
	}
	public void setPreliquidacion(boolean preliquidacion) {
		this.preliquidacion = preliquidacion;
	}
	public boolean isEliminar() {
		return eliminar;
	}
	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

}
