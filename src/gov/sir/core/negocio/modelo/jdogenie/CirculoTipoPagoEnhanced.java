package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CanalesRecaudo;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.CuentasBancarias;

import java.util.Date;

/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004))
 */
public class CirculoTipoPagoEnhanced extends Enhanced {

    private String idCirculoTipoPago; // pk
    private String idCirculo;
    private long idTipoDocPago;
    private String idCuentaBancaria;
    private String idCanalRecaudo;
    private Date fechaCreacion;
    private CirculoEnhanced circulo; // inverse CirculoEnhanced.tiposPago
    private TipoPagoEnhanced tipoPago;
    private CuentasBancariasEnhanced cuentasBancarias;
    private CanalesRecaudoEnhanced canalesRecaudo;
    private boolean ctpActivo;
    private boolean canalSir;

    public CirculoTipoPagoEnhanced() {
    }

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    public long getIdTipoDocPago() {
        return idTipoDocPago;
    }

    public void setIdTipoDocPago(long idTipoDocPago) {
        this.idTipoDocPago = idTipoDocPago;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CirculoEnhanced getCirculo() {
        return circulo;
    }

    public void setCirculo(CirculoEnhanced circulo) {
        this.circulo = circulo;
        setIdCirculo(circulo.getIdCirculo());
    }

    public TipoPagoEnhanced getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPagoEnhanced tipoPago) {
        this.tipoPago = tipoPago;
        setIdTipoDocPago(tipoPago.getIdTipoDocPago());
    }

    public String getIdCirculoTipoPago() {
        return idCirculoTipoPago;
    }

    public void setIdCirculoTipoPago(String idCirculoTipoPago) {
        this.idCirculoTipoPago = idCirculoTipoPago;
    }

    public String getIdCuentaBancaria() {
        return idCuentaBancaria;
    }

    public void setIdCuentaBancaria(String idCuentaBancaria) {
        this.idCuentaBancaria = idCuentaBancaria;
    }

    public String getIdCanalRecaudo() {
        return idCanalRecaudo;
    }

    public void setIdCanalRecaudo(String idCanalRecaudo) {
        this.idCanalRecaudo = idCanalRecaudo;
    }

    public CuentasBancariasEnhanced getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(CuentasBancariasEnhanced cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
        //setIdCuentaBancaria(cuentasBancarias.getId()+"");
    }

    public CanalesRecaudoEnhanced getCanalesRecaudo() {
        return canalesRecaudo;
    }

    public void setCanalesRecaudo(CanalesRecaudoEnhanced canalesRecaudo) {
        this.canalesRecaudo = canalesRecaudo;
        setIdCanalRecaudo(canalesRecaudo.getIdCanal()+"");
    }

    public boolean isCtpActivo() {
        return ctpActivo;
    }

    public void setCtpActivo(boolean ctpActivo) {
        this.ctpActivo = ctpActivo;
    }

    public boolean isCanalSir() {
        return canalSir;
    }

    public void setCanalSir(boolean canalSir) {
        this.canalSir = canalSir;
    }  

    public static CirculoTipoPagoEnhanced enhance(CirculoTipoPago circuloTipoPago) {
        return (CirculoTipoPagoEnhanced) Enhanced.enhance(circuloTipoPago);
    }
}