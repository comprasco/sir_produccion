package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoConsignacionCorreccion;


/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * por medio de Consignación bancaria.
 * @author eacosta
 */
public class DocPagoConsignacionCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced{
	
	
	/**
	 * Fecha de consignación
	 */
	private String fecha = "";
	
	/**
	 * Número de consignación
	 */
	private String noConsignacion = "";
	
	private BancoEnhanced banco;
	
	private String codSucursal;
	
	private String noCuenta;

	public DocPagoConsignacionCorreccionEnhanced() {
	}	
	/**
	 * Constructor que especifica los atributos de un pago realizado por medio de consignación bancaria.
	 * @param codBanco
	 * @param fecha
	 * @param noConsignacion
	 * @param valor
	 */
	public DocPagoConsignacionCorreccionEnhanced(BancoEnhanced banco, String codSucursal, String fecha, String noConsignacion, double valor) {
		this.tipoPago = DocumentoPagoCorreccionEnhanced.PAGO_CONSIGNACION;
		this.banco = banco;
		this.codSucursal = codSucursal;
		this.fecha = fecha;
		this.noConsignacion = noConsignacion;
		this.valorDocumento = valor;
	}

	/**
	 * Este método sobreescribe el método en Object y retorna true
	 * solo si el numero de consignación y el banco son iguales.
	 */
	public boolean equals(Object other) {
		if (other==null || !(other instanceof DocPagoConsignacionCorreccionEnhanced)) return false;
		DocPagoConsignacionCorreccionEnhanced p = (DocPagoConsignacionCorreccionEnhanced) other;
		return banco.equals(p.banco) && noConsignacion.equals(p.noConsignacion);
	}

	/**
	 * @return
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @return
	 */
	public String getNoConsignacion() {
		return noConsignacion;
	}
	/**
	 * @return
	 */
	public BancoEnhanced getBanco() {
		return banco;
	}

	

	/**
	 * @param banco
	 */
	public void setBanco(BancoEnhanced banco) {
		this.banco = banco;
	}


	/**
	 * @param string
	 */
	public void setFecha(String string) {
		fecha = string;
	}


	/**
	 * @param string
	 */
	public void setNoConsignacion(String string) {
		noConsignacion = string;
	}

	/**
	 * @return
	 */
	public String getCodSucursal() {
		return codSucursal;
	}

	/**
	 * @return
	 */
	public String getNoCuenta() {
		return noCuenta;
	}

	/**
	 * @param string
	 */
	public void setNoCuenta(String string) {
		noCuenta = string;
	}
	
	public static DocPagoConsignacionCorreccionEnhanced enhance(DocPagoConsignacionCorreccion docPagoConsignacion){
	   return (DocPagoConsignacionCorreccionEnhanced)Enhanced.enhance(docPagoConsignacion);
	}

	/**
	 * @param string
	 */
	public void setCodSucursal(String string) {
		codSucursal = string;
	}

}