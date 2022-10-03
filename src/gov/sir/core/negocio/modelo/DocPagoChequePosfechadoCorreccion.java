package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * Esta clase encapsula el conocimiento y comportamiento relativo
 * a un documento de pago Cheque Posfechado.
 * @author eacosta
 */
public class DocPagoChequePosfechadoCorreccion extends DocPagoChequeCorreccion implements TransferObject {
    private static final long serialVersionUID = 1L;
	/** Metodo constructor
	 * @param banco
	 * @param codSucursalBanco
	 * @param noCuenta
	 * @param noCheque
	 * @param valor
	 */
	public DocPagoChequePosfechadoCorreccion(
		Banco banco,
		String codSucursalBanco,
		String noCuenta,
		String noCheque,
		double valor) {
		super(banco, codSucursalBanco, noCuenta, noCheque, valor);
	}

	public DocPagoChequePosfechadoCorreccion() {
	}
}
