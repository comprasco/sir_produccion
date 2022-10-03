package gov.sir.core.negocio.modelo.jdogenie;

//import gov.sir.core.negocio.modelo.DocPagoCheque;

import gov.sir.core.negocio.modelo.DocPagoNotaDebitoCorreccion;


/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago realizado
 * por medio de nota débito.
 * @author OSBERT LINERO - Iridium telecomunicaciones e Informática Ltda.
 */
public class DocPagoNotaDebitoCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced {
    
    private String noCuenta = "";

    /**
     * Número de la nota debito
     */
    private String noNotaDebito = "";
    private BancoEnhanced banco;
    private String codSucursalBanco = "";
	private String fecha;

	public DocPagoNotaDebitoCorreccionEnhanced() {
	}
    /**
     * Constructor que especifica los atributos de un pago realizado por medio de nota débito.
     * @param codBanco
     * @param noCuenta
     * @param noNotaDebito
     * @param valor
     */
    public DocPagoNotaDebitoCorreccionEnhanced(BancoEnhanced banco, String codSucursalBanco, String noCuenta,
        String noNotaDebito, double valor) {
        this.tipoPago = DocumentoPagoCorreccionEnhanced.PAGO_NOTA_DEBITO;
        this.banco = banco;
        this.codSucursalBanco = codSucursalBanco;
        this.noCuenta = noCuenta;
        this.noNotaDebito = noNotaDebito;
        this.valorDocumento = valor;
    }

    /**
     * Método que retorna true si el número de la nota debito y el banco son iguales.
     */
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof DocPagoNotaDebitoCorreccionEnhanced)) {
            return false;
        }

        DocPagoNotaDebitoCorreccionEnhanced p = (DocPagoNotaDebitoCorreccionEnhanced) other;

        return banco.equals(p.banco) && noNotaDebito.equals(p.noNotaDebito) &&
        noCuenta.equals(p.noCuenta);
    }

    /**
     * @return
     */
    public String getNoNotaDebito() {
        return noNotaDebito;
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

    /**
     * @param string
     */
    public void setNoNotaDebito(String string) {
        noNotaDebito = string;
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
     * @return
     */
    public String getSucursal() {
        return codSucursalBanco;
    }

	public static DocPagoNotaDebitoCorreccionEnhanced enhance(DocPagoNotaDebitoCorreccion docPagoNotaDebito){
	   return (DocPagoNotaDebitoCorreccionEnhanced)Enhanced.enhance(docPagoNotaDebito);
	}
	/**
	 * @return
	 */
	public String getCodSucursalBanco() {
		return codSucursalBanco;
	}

	/**
	 * @param string
	 */
	public void setCodSucursalBanco(String string) {
		codSucursalBanco = string;
	}

	/**
	 * @return
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param string
	 */
	public void setFecha(String string) {
		fecha = string;
	}
}


