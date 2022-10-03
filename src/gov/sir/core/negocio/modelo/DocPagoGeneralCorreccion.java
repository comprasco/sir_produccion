package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * Esta clase identifica y encapsula el conocimiento relativo a un pago
 * realizado por cualquier forma de pago
 *
 * @author Geremias Ortiz Lozano
 */
public class DocPagoGeneralCorreccion extends DocumentoPagoCorreccion implements TransferObject {
    private static final long serialVersionUID = 1L;
    private int idTipoDocPago;
    private String fechaDocu;
    private String noConsignacion;
    private Banco banco;
    private String codSucursal;
    private String noCuenta;
//    private double valorDocumento;
    private String noAprobacion;
    private BancoFranquicia bancoFranquicia;
    private String noDocumento;
    private String noPin;
    private String noNir;
    private String noCus;
//    private String nombreCanal;
    private String nombreFormaPago;
    private String nombreBancoFranquicia;
    private double valorLiquidado;
    private CuentasBancarias cuentasBancarias;
    private int idCtp;

    public DocPagoGeneralCorreccion() {
    }

    public DocPagoGeneralCorreccion(int idTipoDocPago, String fechaDocu, String noConsignacion,
            String codSucursal, String noCuenta, double valorDocumento, String noAprobacion,
            String noDocumento, double valorLiquidado, int idCtp, String nombreFormaPago, 
            String nombreCanal, String nombreBancoFranquicia,
            BancoFranquicia bancoFranquicia, Banco banco, double saldoDocumento) {

        this.idTipoDocPago = idTipoDocPago;
        this.fechaDocu = fechaDocu;
        this.noConsignacion = noConsignacion;
        this.codSucursal = codSucursal;
        this.noCuenta = noCuenta;
        this.valorDocumento = valorDocumento;
        this.noAprobacion = noAprobacion;
        this.noDocumento = noDocumento;
        this.nombreCanal = nombreCanal;
        this.nombreFormaPago = nombreFormaPago;
        this.nombreBancoFranquicia = nombreBancoFranquicia;
        this.valorLiquidado = valorLiquidado;
        this.tipoPago = nombreFormaPago;
        this.idCtp = idCtp;
        this.bancoFranquicia = bancoFranquicia;
        this.banco = banco;
        this.saldoDocumento = saldoDocumento;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
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

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
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

   /* public double getValorDocumento() {
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

    public BancoFranquicia getBancoFranquicia() {
        return bancoFranquicia;
    }

    public void setBancoFranquicia(BancoFranquicia bancoFranquicia) {
        this.bancoFranquicia = bancoFranquicia;
    }

    public String getNoDocumento() {
        return noDocumento;
    }

    public void setNoDocumento(String noDocumento) {
        this.noDocumento = noDocumento;
    }

    public String getNoPin() {
        return noPin;
    }

    public void setNoPin(String noPin) {
        this.noPin = noPin;
    }

    public String getNoNir() {
        return noNir;
    }

    public void setNoNir(String noNir) {
        this.noNir = noNir;
    }

    public String getNoCus() {
        return noCus;
    }

    public void setNoCus(String noCus) {
        this.noCus = noCus;
    }

    public String getNombreFormaPago() {
        return nombreFormaPago;
    }

    public void setNombreFormaPago(String nombreFormaPago) {
        this.nombreFormaPago = nombreFormaPago;
    }

    public String getNombreBancoFranquicia() {
        return nombreBancoFranquicia;
    }

    public void setNombreBancoFranquicia(String nombreBancoFranquicia) {
        this.nombreBancoFranquicia = nombreBancoFranquicia;
    }

    public double getValorLiquidado() {
        return valorLiquidado;
    }

    public void setValorLiquidado(double valorLiquidado) {
        this.valorLiquidado = valorLiquidado;
    }

    public CuentasBancarias getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(CuentasBancarias cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }

    public int getIdCtp() {
        return idCtp;
    }

    public void setIdCtp(int idCtp) {
        this.idCtp = idCtp;
    }
}
