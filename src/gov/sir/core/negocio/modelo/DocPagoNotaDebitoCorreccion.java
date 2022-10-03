package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * por medio de Nota debito.
 * @author OSBERT LINERO - Iridium telecomunicaciones e Informática Ltda.
 */
public class DocPagoNotaDebitoCorreccion extends DocumentoPagoCorreccion implements TransferObject {
    private static final long serialVersionUID = 1L;
    private String noCuenta = "";

    /**
     * Número de la nota debito.
     */
    private String noNotaDebito = "";
    private Banco banco;
    private String codSucursalBanco = "";
    private String fecha;

    /** Constructor por defecto */
	public DocPagoNotaDebitoCorreccion(){
	}

    /**
     * Constructor que especifica los atributos de un pago realizado por medio de nota débito.
     * @param codBanco
     * @param noCuenta
     * @param noNotaDebito
     * @param valor
     */
    public DocPagoNotaDebitoCorreccion(Banco banco, String codSucursalBanco, String noCuenta,
        String noNotaDebito, double valor) {
        this.tipoPago = DocumentoPagoCorreccion.PAGO_NOTA_DEBITO;
        this.banco = banco;
        this.codSucursalBanco = codSucursalBanco;
        this.noCuenta = noCuenta;
        this.noNotaDebito = noNotaDebito;
        this.valorDocumento = valor;
    }

    /**
     * Este método sobreescribe el método equals en Object y retorna true
     * sólo si el número de la nota débito y el banco son iguales.
     */
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof DocPagoNotaDebitoCorreccion)) {
            return false;
        }

        DocPagoNotaDebitoCorreccion p = (DocPagoNotaDebitoCorreccion) other;

        return banco.equals(p.banco) && noNotaDebito.equals(p.noNotaDebito) &&
        noCuenta.equals(p.noCuenta);
    }

    /** Retorna el número de nota débito
     * @return
     */
    public String getNoNotaDebito() {
        return noNotaDebito;
    }

    /** Retorna el número de cuenta
     * @return
     */
    public String getNoCuenta() {
        return noCuenta;
    }

    /** Asigna el número de nota débito
     * @param string
     */
    public void setNoCuenta(String string) {
        noCuenta = string;
    }

    /** Cambia el número de cuenta
     * @param string
     */
    public void setNoNotaDebito(String string) {
        noNotaDebito = string;
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

