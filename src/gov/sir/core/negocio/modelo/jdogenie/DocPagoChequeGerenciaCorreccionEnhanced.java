package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoChequeGerenciaCorreccion;

/**
 * Esta clase encapsula el conocimiento y comportamiento relativo
 * a un documento de pago Cheque de Gerencia.
 * @author eacosta
 */
public class DocPagoChequeGerenciaCorreccionEnhanced extends DocPagoChequeCorreccionEnhanced {

	/**
	 * @param banco
	 * @param codSucursalBanco
	 * @param noCuenta
	 * @param noCheque
	 * @param valor
	 */
	public DocPagoChequeGerenciaCorreccionEnhanced(
		BancoEnhanced banco,
		String codSucursalBanco,
		String noCuenta,
		String noCheque,
		double valor) {
		super(banco, codSucursalBanco, noCuenta, noCheque, valor);
	}
	
	public DocPagoChequeGerenciaCorreccionEnhanced() {
	}

	public static DocPagoChequeGerenciaCorreccionEnhanced enhance(DocPagoChequeGerenciaCorreccion docPagoChequeGerencia){
	   return (DocPagoChequeGerenciaCorreccionEnhanced)Enhanced.enhance(docPagoChequeGerencia);
	}
}
