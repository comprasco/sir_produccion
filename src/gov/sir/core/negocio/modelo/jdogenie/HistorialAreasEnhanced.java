package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.HistorialAreas;
import java.util.Date;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
*/
public class HistorialAreasEnhanced extends Enhanced{
    private String idHistorial; //pk
    private String idMatricula;
    private String hectareas;
    private String metros;
    private String centimetros;
    private Date fechaActualizacion;
    
    public HistorialAreasEnhanced() {
    }

    public String getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(String idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getHectareas() {
        return hectareas;
    }

    public void setHectareas(String hectareas) {
        this.hectareas = hectareas;
    }

    public String getMetros() {
        return metros;
    }

    public void setMetros(String metros) {
        this.metros = metros;
    }

    public String getCentimetros() {
        return centimetros;
    }

    public void setCentimetros(String centimetros) {
        this.centimetros = centimetros;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    public static HistorialAreasEnhanced enhance(HistorialAreas historialAreas) {
		return (HistorialAreasEnhanced) Enhanced.enhance(historialAreas);
	}
    
}
