/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CuentasBancarias;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class CuentasBancariasEnhanced extends Enhanced {

    private String idCirculo;
    private String idBanco;
    private String nroCuenta;
    private CirculoEnhanced circulo;//inverse CirculoEnhanced.circulos
    private BancoEnhanced banco; //inverse BancoEnhanced.bancos
    private boolean activa;
    private int id;

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    public String getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public CirculoEnhanced getCirculo() {
        return circulo;
    }

    public void setCirculo(CirculoEnhanced circulo) {
        this.circulo = circulo;
    }

    public BancoEnhanced getBanco() {
        return banco;
    }

    public void setBanco(BancoEnhanced banco) {
        this.banco = banco;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public static CuentasBancariasEnhanced enhance(CuentasBancarias cuentasBancariasEnhanced) {
        return (CuentasBancariasEnhanced) Enhanced.enhance(cuentasBancariasEnhanced);
    }

}
