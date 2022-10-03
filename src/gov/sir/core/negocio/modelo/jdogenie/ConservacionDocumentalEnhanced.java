/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ConservacionDocumental;
import java.util.Date;

/**
 *
 * @author desarrollo3
 */
public class ConservacionDocumentalEnhanced extends Enhanced{

    private int idConservacion;
    private Date conservacionFechaInicio;
    private double valorPorcentajeConservacion;
    private String conservacionTipoActo;
    private int conservacionEstado;
    private Date conservacionFechaFin;

    /**
     * @return the idConservacion
     */
    public int getIdConservacion() {
        return idConservacion;
    }

    /**
     * @param idConservacion the idConservacion to set
     */
    public void setIdConservacion(int idConservacion) {
        this.idConservacion = idConservacion;
    }

    /**
     * @return the conservacionFechaInicio
     */
    public Date getConservacionFechaInicio() {
        return conservacionFechaInicio;
    }

    /**
     * @param conservacionFechaInicio the conservacionFechaInicio to set
     */
    public void setConservacionFechaInicio(Date conservacionFechaInicio) {
        this.conservacionFechaInicio = conservacionFechaInicio;
    }

    /**
     * @return the valorPorcentajeConservacion
     */
    public double getValorPorcentajeConservacion() {
        return valorPorcentajeConservacion;
    }

    /**
     * @param valorPorcentajeConservacion the valorPorcentajeConservacion to set
     */
    public void setValorPorcentajeConservacion(double valorPorcentajeConservacion) {
        this.valorPorcentajeConservacion = valorPorcentajeConservacion;
    }

    /**
     * @return the conservacionTipoActo
     */
    public String getConservacionTipoActo() {
        return conservacionTipoActo;
    }

    /**
     * @param conservacionTipoActo the conservacionTipoActo to set
     */
    public void setConservacionTipoActo(String conservacionTipoActo) {
        this.conservacionTipoActo = conservacionTipoActo;
    }

    /**
     * @return the conservacionEstado
     */
    public int getConservacionEstado() {
        return conservacionEstado;
    }

    /**
     * @param conservacionEstado the conservacionEstado to set
     */
    public void setConservacionEstado(int conservacionEstado) {
        this.conservacionEstado = conservacionEstado;
    }

    /**
     * @return the conservacionFechaFin
     */
    public Date getConservacionFechaFin() {
        return conservacionFechaFin;
    }

    /**
     * @param conservacionFechaFin the conservacionFechaFin to set
     */
    public void setConservacionFechaFin(Date conservacionFechaFin) {
        this.conservacionFechaFin = conservacionFechaFin;
    }
    
    public static ConservacionDocumentalEnhanced enhance(
            ConservacionDocumental conservacionDocumental) {
        return (ConservacionDocumentalEnhanced) Enhanced.enhance(conservacionDocumental);
    }
    
}
