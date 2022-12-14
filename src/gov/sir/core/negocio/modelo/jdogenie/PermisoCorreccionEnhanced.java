package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.PermisoCorreccion;

import java.util.Date;

/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004))
 */
public class PermisoCorreccionEnhanced extends Enhanced{

    private String idPermiso; // pk 
    private String descripcion;
    private Date fechaCreacion;

    public PermisoCorreccionEnhanced() {
    }

    public String getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(String idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
  
	public static PermisoCorreccionEnhanced enhance(PermisoCorreccion x){
		return (PermisoCorreccionEnhanced)Enhanced.enhance(x);
	}
}
