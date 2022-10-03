/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LiquidacionConservacion;
import java.util.Date;

/**
 *
 * @author desarrollo3
 */
public class LiquidacionConservacionEnhanced extends Enhanced {

    private int idLiquidacionConserva;
    private String numTurnoLiquidacionConserva;
    private double valorPorcentajeLiquidacionConserva;
    private Date fechaLiquidacionConserva;
    private double tarifaPlenaLiquidacionConserva;
    private double tarifaBaseLiquidacionConserva;
    private double valorTarifaLiquidacionConserva;
    private String tipoActoLiquidacionConserva;
    private String idSolicituLiquidacionConserva;
    private int mayorvalor;
    private int anulacion;
    private int tipoActoConsec;

    /**
     * @return the idLiquidacionConserva
     */
    public int getIdLiquidacionConserva() {
        return idLiquidacionConserva;
    }

    /**
     * @param idLiquidacionConserva the idLiquidacionConserva to set
     */
    public void setIdLiquidacionConserva(int idLiquidacionConserva) {
        this.idLiquidacionConserva = idLiquidacionConserva;
    }

    /**
     * @return the numTurnoLiquidacionConserva
     */
    public String getNumTurnoLiquidacionConserva() {
        return numTurnoLiquidacionConserva;
    }

    /**
     * @param numTurnoLiquidacionConserva the numTurnoLiquidacionConserva to set
     */
    public void setNumTurnoLiquidacionConserva(String numTurnoLiquidacionConserva) {
        this.numTurnoLiquidacionConserva = numTurnoLiquidacionConserva;
    }

    /**
     * @return the valorPorcentajeLiquidacionConserva
     */
    public double getValorPorcentajeLiquidacionConserva() {
        return valorPorcentajeLiquidacionConserva;
    }

    /**
     * @param valorPorcentajeLiquidacionConserva the
     * valorPorcentajeLiquidacionConserva to set
     */
    public void setValorPorcentajeLiquidacionConserva(double valorPorcentajeLiquidacionConserva) {
        this.valorPorcentajeLiquidacionConserva = valorPorcentajeLiquidacionConserva;
    }

    /**
     * @return the fechaLiquidacionConserva
     */
    public Date getFechaLiquidacionConserva() {
        return fechaLiquidacionConserva;
    }

    /**
     * @param fechaLiquidacionConserva the fechaLiquidacionConserva to set
     */
    public void setFechaLiquidacionConserva(Date fechaLiquidacionConserva) {
        this.fechaLiquidacionConserva = fechaLiquidacionConserva;
    }

    /**
     * @return the tarifaPlenaLiquidacionConserva
     */
    public double getTarifaPlenaLiquidacionConserva() {
        return tarifaPlenaLiquidacionConserva;
    }

    /**
     * @param tarifaPlenaLiquidacionConserva the tarifaPlenaLiquidacionConserva
     * to set
     */
    public void setTarifaPlenaLiquidacionConserva(double tarifaPlenaLiquidacionConserva) {
        this.tarifaPlenaLiquidacionConserva = tarifaPlenaLiquidacionConserva;
    }

    /**
     * @return the tarifaBaseLiquidacionConserva
     */
    public double getTarifaBaseLiquidacionConserva() {
        return tarifaBaseLiquidacionConserva;
    }

    /**
     * @param tarifaBaseLiquidacionConserva the tarifaBaseLiquidacionConserva to
     * set
     */
    public void setTarifaBaseLiquidacionConserva(double tarifaBaseLiquidacionConserva) {
        this.tarifaBaseLiquidacionConserva = tarifaBaseLiquidacionConserva;
    }

    /**
     * @return the valorTarifaLiquidacionConserva
     */
    public double getValorTarifaLiquidacionConserva() {
        return valorTarifaLiquidacionConserva;
    }

    /**
     * @param valorTarifaLiquidacionConserva the valorTarifaLiquidacionConserva
     * to set
     */
    public void setValorTarifaLiquidacionConserva(double valorTarifaLiquidacionConserva) {
        this.valorTarifaLiquidacionConserva = valorTarifaLiquidacionConserva;
    }

    /**
     * @return the tipoActoLiquidacionConserva
     */
    public String getTipoActoLiquidacionConserva() {
        return tipoActoLiquidacionConserva;
    }

    /**
     * @param tipoActoLiquidacionConserva the tipoActoLiquidacionConserva to set
     */
    public void setTipoActoLiquidacionConserva(String tipoActoLiquidacionConserva) {
        this.tipoActoLiquidacionConserva = tipoActoLiquidacionConserva;
    }

    /**
     * @return the idSolicituLiquidacionConserva
     */
    public String getIdSolicituLiquidacionConserva() {
        return idSolicituLiquidacionConserva;
    }

    /**
     * @param idSolicituLiquidacionConserva the idSolicituLiquidacionConserva to
     * set
     */
    public void setIdSolicituLiquidacionConserva(String idSolicituLiquidacionConserva) {
        this.idSolicituLiquidacionConserva = idSolicituLiquidacionConserva;
    }

    public static LiquidacionConservacionEnhanced enhance(
            LiquidacionConservacion liquidacionConservacion) {
        return (LiquidacionConservacionEnhanced) Enhanced.enhance(liquidacionConservacion);
    }

    /**
     * @return the mayorvalor
     */
    public int getMayorvalor() {
        return mayorvalor;
    }

    /**
     * @param mayorvalor the mayorvalor to set
     */
    public void setMayorvalor(int mayorvalor) {
        this.mayorvalor = mayorvalor;
    }

    /**
     * @return the anulacion
     */
    public int getAnulacion() {
        return anulacion;
    }

    /**
     * @param anulacion the anulacion to set
     */
    public void setAnulacion(int anulacion) {
        this.anulacion = anulacion;
    }

    public int getTipoActoConsec() {
        return tipoActoConsec;
    }

    public void setTipoActoConsec(int tipoActoConsec) {
        this.tipoActoConsec = tipoActoConsec;
    }
    
    
}
