package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoChequePosfechadoCorreccion;


/**
 * Esta clase encapsula el conocimiento y comportamiento relativo
 * a un documento de pago Cheque Posfechado.
 * @author eacosta
 */
public class DocPagoChequePosfechadoCorreccionEnhanced extends DocPagoChequeCorreccionEnhanced  {

	/**
	 * @param banco
	 * @param codSucursalBanco
	 * @param noCuenta
	 * @param noCheque
	 * @param valor
	 */
	public DocPagoChequePosfechadoCorreccionEnhanced(
		BancoEnhanced banco,
		String codSucursalBanco,
		String noCuenta,
		String noCheque,
		double valor) {
		super(banco, codSucursalBanco, noCuenta, noCheque, valor);
	}
	
	public DocPagoChequePosfechadoCorreccionEnhanced() {
	}

	public static DocPagoChequePosfechadoCorreccionEnhanced enhance(DocPagoChequePosfechadoCorreccion docPagoChequePosfechado){
	   return (DocPagoChequePosfechadoCorreccionEnhanced)Enhanced.enhance(docPagoChequePosfechado);
	}
}
