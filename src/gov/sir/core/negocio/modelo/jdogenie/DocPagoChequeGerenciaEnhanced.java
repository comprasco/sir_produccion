package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoChequeGerencia;

/**
 * Esta clase encapsula el conocimiento y comportamiento relativo
 * a un documento de pago Cheque de Gerencia.
 * @author eacosta
 */
public class DocPagoChequeGerenciaEnhanced extends DocPagoChequeEnhanced {

	/**
	 * @param banco
	 * @param codSucursalBanco
	 * @param noCuenta
	 * @param noCheque
	 * @param valor
	 */
	public DocPagoChequeGerenciaEnhanced(
		BancoEnhanced banco,
		String codSucursalBanco,
		String noCuenta,
		String noCheque,
		double valor) {
		super(banco, codSucursalBanco, noCuenta, noCheque, valor);
	}
	
	public DocPagoChequeGerenciaEnhanced() {
	}

	public static DocPagoChequeGerenciaEnhanced enhance(DocPagoChequeGerencia docPagoChequeGerencia){
	   return (DocPagoChequeGerenciaEnhanced)Enhanced.enhance(docPagoChequeGerencia);
	}
}
