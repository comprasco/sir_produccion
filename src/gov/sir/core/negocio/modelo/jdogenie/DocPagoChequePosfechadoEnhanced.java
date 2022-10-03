package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoChequePosfechado;


/**
 * Esta clase encapsula el conocimiento y comportamiento relativo
 * a un documento de pago Cheque Posfechado.
 * @author eacosta
 */
public class DocPagoChequePosfechadoEnhanced extends DocPagoChequeEnhanced  {

	/**
	 * @param banco
	 * @param codSucursalBanco
	 * @param noCuenta
	 * @param noCheque
	 * @param valor
	 */
	public DocPagoChequePosfechadoEnhanced(
		BancoEnhanced banco,
		String codSucursalBanco,
		String noCuenta,
		String noCheque,
		double valor) {
		super(banco, codSucursalBanco, noCuenta, noCheque, valor);
	}
	
	public DocPagoChequePosfechadoEnhanced() {
	}

	public static DocPagoChequePosfechadoEnhanced enhance(DocPagoChequePosfechado docPagoChequePosfechado){
	   return (DocPagoChequePosfechadoEnhanced)Enhanced.enhance(docPagoChequePosfechado);
	}
}
