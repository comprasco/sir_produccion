package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * por medio de Cheque.
 * @author eacosta
 */
public class DocPagoChequeCorreccion extends DocumentoPagoCorreccion implements TransferObject {
    /**
     * Número de cuenta de donde fué girado el cheque
     */
    private String noCuenta = "";
    private static final long serialVersionUID = 1L;
    /**
     * Número del cheque
     */
    private String noCheque = "";
    private Banco banco;
    private String codSucursalBanco = "";
    private String fecha;
	
    /** Constructor por defecto */
	public DocPagoChequeCorreccion(){
	}
	
    /**
     * Constructor que especifica los atributos de un pago realizado por medio de un cheque.
     * @param codBanco
     * @param noCuenta
     * @param noCheque
     * @param valor
     */
    public DocPagoChequeCorreccion(Banco banco, String codSucursalBanco, String noCuenta,
        String noCheque, double valor) {
        this.tipoPago = DocumentoPagoCorreccion.PAGO_CHEQUE;
        this.banco = banco;
        this.codSucursalBanco = codSucursalBanco;
        this.noCuenta = noCuenta;
        this.noCheque = noCheque;
        this.valorDocumento = valor;
    }

    /**
     * Este método sobreescribe el método en Object y retorna true
     * solo si el numero del cheque y el banco son iguales.
     */
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof DocPagoChequeCorreccion)) {
            return false;
        }

        DocPagoChequeCorreccion p = (DocPagoChequeCorreccion) other;

        return banco.equals(p.banco) && noCheque.equals(p.noCheque) &&
        noCuenta.equals(p.noCuenta);
    }

    /** Retorna el número de cheque
     * @return
     */
    public String getNoCheque() {
        return noCheque;
    }

    /** Retorna el número de cuenta
     * @return
     */
    public String getNoCuenta() {
        return noCuenta;
    }

    /**Cambia el número de cheque
     * @param string
     */
    public void setNoCuenta(String string) {
        noCuenta = string;
    }

    /** Cambia el número de cuenta
     * @param string
     */
    public void setNoCheque(String string) {
        noCheque = string;
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

    /** Retorna el código de la sucursal del banco del documento
     * @return
     */
    public String getSucursal() {
        return codSucursalBanco;
    }
	/** Retorna el código de la sucursal del banco del documento
	 * @return
	 */
	public String getCodSucursalBanco() {
		return codSucursalBanco;
	}

	/** Cambia el código de la sucursal del banco del documento
	 * @param string
	 */
	public void setCodSucursalBanco(String string) {
		codSucursalBanco = string;
	}

	/** Retorna la fecha del documento
	 * @return
	 */
	public String getFecha() {
		return fecha;
	}

	/** Modifica la fecha del documento
	 * @param string
	 */
	public void setFecha(String string) {
		fecha = string;
	}

}
