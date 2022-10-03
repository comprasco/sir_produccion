/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import java.util.Date;

/**
 *
 * @author desarrollo3
 */
public class DevolucionConservacionDocEnhanced extends Enhanced{
 
    private int idDevolucionConservaDoc;
    private String turnoDevolucionConservadoc;
    private Date fechaDevolucionConservaDoc;
    private double tarifaBaseDevolucionConservaDoc;
    private double valorDevolucionLiquidaConserva;
    private String idSolicituDevolucionConservacion;
    private int mayorvalor;
    private String turnoAntLiquidacionConserva;
    private int anulacion;
    /**
     * @return the idDevolucionConservaDoc
     */
    public int getIdDevolucionConservaDoc() {
        return idDevolucionConservaDoc;
    }

    /**
     * @param idDevolucionConservaDoc the idDevolucionConservaDoc to set
     */
    public void setIdDevolucionConservaDoc(int idDevolucionConservaDoc) {
        this.idDevolucionConservaDoc = idDevolucionConservaDoc;
    }

    /**
     * @return the turnoDevolucionConservadoc
     */
    public String getTurnoDevolucionConservadoc() {
        return turnoDevolucionConservadoc;
    }

    /**
     * @param turnoDevolucionConservadoc the turnoDevolucionConservadoc to set
     */
    public void setTurnoDevolucionConservadoc(String turnoDevolucionConservadoc) {
        this.turnoDevolucionConservadoc = turnoDevolucionConservadoc;
    }

    /**
     * @return the fechaDevolucionConservaDoc
     */
    public Date getFechaDevolucionConservaDoc() {
        return fechaDevolucionConservaDoc;
    }

    /**
     * @param fechaDevolucionConservaDoc the fechaDevolucionConservaDoc to set
     */
    public void setFechaDevolucionConservaDoc(Date fechaDevolucionConservaDoc) {
        this.fechaDevolucionConservaDoc = fechaDevolucionConservaDoc;
    }

    /**
     * @return the tarifaBaseDevolucionConservaDoc
     */
    public double getTarifaBaseDevolucionConservaDoc() {
        return tarifaBaseDevolucionConservaDoc;
    }

    /**
     * @param tarifaBaseDevolucionConservaDoc the tarifaBaseDevolucionConservaDoc to set
     */
    public void setTarifaBaseDevolucionConservaDoc(double tarifaBaseDevolucionConservaDoc) {
        this.tarifaBaseDevolucionConservaDoc = tarifaBaseDevolucionConservaDoc;
    }

    /**
     * @return the valorDevolucionLiquidaConserva
     */
    public double getValorDevolucionLiquidaConserva() {
        return valorDevolucionLiquidaConserva;
    }

    /**
     * @param valorDevolucionLiquidaConserva the valorDevolucionLiquidaConserva to set
     */
    public void setValorDevolucionLiquidaConserva(double valorDevolucionLiquidaConserva) {
        this.valorDevolucionLiquidaConserva = valorDevolucionLiquidaConserva;
    }

    /**
     * @return the idSolicituDevolucionConservacion
     */
    public String getIdSolicituDevolucionConservacion() {
        return idSolicituDevolucionConservacion;
    }

    /**
     * @param idSolicituDevolucionConservacion the idSolicituDevolucionConservacion to set
     */
    public void setIdSolicituDevolucionConservacion(String idSolicituDevolucionConservacion) {
        this.idSolicituDevolucionConservacion = idSolicituDevolucionConservacion;
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
     * @return the turnoAntLiquidacionConserva
     */
    public String getTurnoAntLiquidacionConserva() {
        return turnoAntLiquidacionConserva;
    }

    /**
     * @param turnoAntLiquidacionConserva the turnoAntLiquidacionConserva to set
     */
    public void setTurnoAntLiquidacionConserva(String turnoAntLiquidacionConserva) {
        this.turnoAntLiquidacionConserva = turnoAntLiquidacionConserva;
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
    
    
}
