package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocPagoCheque;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado por medio de Cheque.
 *
 * @author eacosta
 */
public class DocPagoChequeEnhanced extends DocumentoPagoEnhanced {

    /**
     * Número de cuenta de donde fué girado el cheque
     */
    private String noCuenta = "";

    /**
     * Número del cheque
     */
    private String noCheque = "";
    private BancoEnhanced banco;
    private String codSucursalBanco = "";
    private String fecha;

    public DocPagoChequeEnhanced() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado por medio
     * de un cheque.
     *
     * @param codBanco
     * @param noCuenta
     * @param noCheque
     * @param valor
     */
    public DocPagoChequeEnhanced(BancoEnhanced banco, String codSucursalBanco, String noCuenta,
            String noCheque, double valor) {
        this.tipoPago = DocumentoPagoEnhanced.PAGO_CHEQUE;
        this.banco = banco;
        this.codSucursalBanco = codSucursalBanco;
        this.noCuenta = noCuenta;
        this.noCheque = noCheque;
        this.valorDocumento = valor;
    }

    /**
     * Este método sobreescribe el método en Object y retorna true solo si el
     * numero del cheque y el banco son iguales.
     */
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof DocPagoChequeEnhanced)) {
            return false;
        }

        DocPagoChequeEnhanced p = (DocPagoChequeEnhanced) other;

        return banco.equals(p.banco) && noCheque.equals(p.noCheque)
                && noCuenta.equals(p.noCuenta);
    }

    /**
     * @return
     */
    public String getNoCheque() {
        return noCheque;
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
    public void setNoCheque(String string) {
        noCheque = string;
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

    public static DocPagoChequeEnhanced enhance(DocPagoCheque docPagoCheque) {
        return (DocPagoChequeEnhanced) Enhanced.enhance(docPagoCheque);
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
