package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * por medio de Consignaci�n bancaria.
 * @author eacosta
 */
public class DocPagoConsignacionCorreccion extends DocumentoPagoCorreccion implements TransferObject{
            private static final long serialVersionUID = 1L;
	
	/**
	 * Fecha de consignaci�n
	 */
	private String fecha = "";
	
	/**
	 * N�mero de consignaci�n
	 */
	private String noConsignacion = "";
	
	private Banco banco;
	
	private String codSucursal;
	
	private String noCuenta;

	/** Constructor por defecto */
	public DocPagoConsignacionCorreccion() {
	}
		
	/**
	 * Constructor que especifica los atributos de un pago realizado por medio de consignaci�n bancaria.
	 * @param codBanco
	 * @param fecha
	 * @param noConsignacion
	 * @param valor
	 */
	public DocPagoConsignacionCorreccion(Banco banco, String codSucursal, String fecha, String noConsignacion, double valor) {
		this.tipoPago = DocumentoPagoCorreccion.PAGO_CONSIGNACION;
		this.banco = banco;
		this.codSucursal = codSucursal;
		this.fecha = fecha;
		this.noConsignacion = noConsignacion;
		this.valorDocumento = valor;
	}

	/**
	 * Este m�todo sobreescribe el m�todo en Object y retorna true
	 * solo si el numero de consignaci�n y el banco son iguales.
	 */
	public boolean equals(Object other) {
		if (other==null || !(other instanceof DocPagoConsignacionCorreccion)) return false;
		DocPagoConsignacionCorreccion p = (DocPagoConsignacionCorreccion) other;
		return banco.equals(p.banco) && noConsignacion.equals(p.noConsignacion);
	}

	/** Retorna la fecha del documento
	 * @return
	 */
	public String getFecha() {
		return fecha;
	}

	/** Retorna el n�umero de consignaci�n
	 * @return
	 */
	public String getNoConsignacion() {
		return noConsignacion;
	}
	
	/** Retorna el identificador del banco asociado
	 * @return
	 */
	public Banco getBanco() {
		return banco;
	}

	/** Cambia el identificador del banco asociado
	 * @param banco
	 */
	public void setBanco(Banco banco) {
		this.banco = banco;
	}


	/** Modifica la fecha del documento
	 * @param string
	 */
	public void setFecha(String string) {
		fecha = string;
	}


	/** Cambia el n�umero de consignaci�n
	 * @param string
	 */
	public void setNoConsignacion(String string) {
		noConsignacion = string;
	}

	/** Retorna el c�digo de la sucursal del banco del documento
	 * @return
	 */
	public String getCodSucursal() {
		return codSucursal;
	}

	/** Retorna el n�mero de cuenta
	 * @return
	 */
	public String getNoCuenta() {
		return noCuenta;
	}

	/** Cambia el n�mero de cuenta
	 * @param string
	 */
	public void setNoCuenta(String string) {
		noCuenta = string;
	}

	/** Cambia el c�digo de la sucursal del banco del documento
	 * @param string
	 */
	public void setCodSucursal(String string) {
		codSucursal = string;
	}

}