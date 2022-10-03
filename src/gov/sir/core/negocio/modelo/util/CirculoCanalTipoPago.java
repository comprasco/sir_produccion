/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.util;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class CirculoCanalTipoPago {

    private String idCtp;
    private String idCirculo;
    private String nombreCirculo;
    private String nombreCanal;
    private String nombreFormaPago;
    private String nroCuenta;
    private String nombreBanco;
    private boolean ctpActivo;
    private boolean canalSir;

    public String getIdCtp() {
        return idCtp;
    }

    public void setIdCtp(String idCtp) {
        this.idCtp = idCtp;
    }

    public String getNombreCirculo() {
        return nombreCirculo;
    }

    public void setNombreCirculo(String nombreCirculo) {
        this.nombreCirculo = nombreCirculo;
    }

    public String getNombreCanal() {
        return nombreCanal;
    }

    public void setNombreCanal(String nombreCanal) {
        this.nombreCanal = nombreCanal;
    }

    public String getNombreFormaPago() {
        return nombreFormaPago;
    }

    public void setNombreFormaPago(String nombreFormaPago) {
        this.nombreFormaPago = nombreFormaPago;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
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

}
