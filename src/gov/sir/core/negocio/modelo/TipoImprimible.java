package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class TipoImprimible implements TransferObject {

    private String idTipoImprimible; // pk
    private Date fechaCreacion;
    private String nombre;
private static final long serialVersionUID = 1L;
    public TipoImprimible() {
    }

    public String getIdTipoImprimible() {
        return idTipoImprimible;
    }

    public void setIdTipoImprimible(String idTipoImprimible) {
        this.idTipoImprimible = idTipoImprimible;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoImprimible != null ? idTipoImprimible.hashCode() : 0);
        return result;
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoImprimible)) return false;

        final TipoImprimible id = (TipoImprimible) o;

        if (this.idTipoImprimible != null ? !idTipoImprimible.equals(id.idTipoImprimible) : id.idTipoImprimible != null) return false;
        return true;
    }
}