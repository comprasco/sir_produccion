package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancoFranquicia;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.DocPagoGeneralCorreccion;

/**
 * @author Geremias Ortiz Lozano
 */
public class DocPagoGeneralCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced {

    private int idTipoDocPago;
    private String fechaDocu;
    private String noConsignacion;
    private BancoEnhanced banco;
    private String codSucursal;
    private String noCuenta;
//    private double valorDocumento;
    private String noAprobacion;
    private BancoFranquiciaEnhanced bancoFranquicia;
    private String noDocumento;
    private int idCtp;
//    private String nombreCanal;

    public DocPagoGeneralCorreccionEnhanced() {
    }

    public DocPagoGeneralCorreccionEnhanced(int idTipoDocPago, String fechaDocu, String noConsignacion,
            String codSucursal, String noCuenta, double valorDocumento, String noAprobacion,
            String noDocumento, double valorLiquidado, int idCtp, String nombreFormaPago, 
            BancoFranquiciaEnhanced bancoFranquicia, String nombreCanal, double saldoDocumento) {

        this.idTipoDocPago = idTipoDocPago;
        this.fechaDocu = fechaDocu;
        this.noConsignacion = noConsignacion;
        this.codSucursal = codSucursal;
        this.noCuenta = noCuenta;
        this.valorDocumento = valorDocumento;
        this.noAprobacion = noAprobacion;
        this.noDocumento = noDocumento;
        this.tipoPago = nombreFormaPago;
        this.idCtp = idCtp;
        this.bancoFranquicia = bancoFranquicia;
        this.nombreCanal = nombreCanal;
        this.saldoDocumento = saldoDocumento;
    }

    public static DocPagoGeneralCorreccionEnhanced enhance(DocPagoGeneralCorreccion docPagoGeneral) {
        return (DocPagoGeneralCorreccionEnhanced) Enhanced.enhance(docPagoGeneral);
    }

    public int getIdTipoDocPago() {
        return idTipoDocPago;
    }

    public void setIdTipoDocPago(int idTipoDocPago) {
        this.idTipoDocPago = idTipoDocPago;
    }

    public String getFechaDocu() {
        return fechaDocu;
    }

    public void setFechaDocu(String fechaDocu) {
        this.fechaDocu = fechaDocu;
    }

    public String getNoConsignacion() {
        return noConsignacion;
    }

    public void setNoConsignacion(String noConsignacion) {
        this.noConsignacion = noConsignacion;
    }

    public BancoEnhanced getBanco() {
        return banco;
    }

    public void setBanco(BancoEnhanced banco) {
        this.banco = banco;
    }

    public String getCodSucursal() {
        return codSucursal;
    }

    public void setCodSucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    /*public double getValorDocumento() {
        return valorDocumento;
    }

    public void setValorDocumento(double valorDocumento) {
        this.valorDocumento = valorDocumento;
    }*/

    public String getNoAprobacion() {
        return noAprobacion;
    }

    public void setNoAprobacion(String noAprobacion) {
        this.noAprobacion = noAprobacion;
    }

    public BancoFranquiciaEnhanced getBancoFranquicia() {
        return bancoFranquicia;
    }

    public void setBancoFranquicia(BancoFranquiciaEnhanced bancoFranquicia) {
        this.bancoFranquicia = bancoFranquicia;
    }

    public String getNoDocumento() {
        return noDocumento;
    }

    public void setNoDocumento(String noDocumento) {
        this.noDocumento = noDocumento;
    }

    public int getIdCtp() {
        return idCtp;
    }

    public void setIdCtp(int idCtp) {
        this.idCtp = idCtp;
    }
    
}
