package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CirculoRelacion;

import java.util.Date;

public class CirculoRelacionEnhanced extends Enhanced {

    private String anio; // pk
    private String idCirculo; // pk
    private Date fechaCreacion;
    private long lastIdRelacion;

    public CirculoRelacionEnhanced() {
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getLastIdRelacion() {
        return lastIdRelacion;
    }

    public void setLastIdRelacion(long lastIdRelacion) {
        this.lastIdRelacion = lastIdRelacion;
    }
    
    public static CirculoRelacionEnhanced enhance(CirculoRelacion circuloRelacion){
 	   return (CirculoRelacionEnhanced)Enhanced.enhance(circuloRelacion);
 	}
}
