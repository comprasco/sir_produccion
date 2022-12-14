package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by Versant Open Access (version:3.2.9 (17 Jan 2005))
 */
public class ValidacionNotaDetalle implements TransferObject{

    private String idFase; // pk 
    private long idProceso; // pk 
    private String idRespuesta; // pk 
    private String idTipoNota; // pk 
    private String descripcion;
    private Date fechaCreacion;
    private TipoNota tipoNota;
    private ValidacionNota validacionNotaInf;
    private static final long serialVersionUID = 1L;
    public ValidacionNotaDetalle() {
    }

    public String getIdFase() {
        return idFase;
    }

    public void setIdFase(String idFase) {
        this.idFase = idFase;
    }

    public long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getIdTipoNota() {
        return idTipoNota;
    }

    public void setIdTipoNota(String idTipoNota) {
        this.idTipoNota = idTipoNota;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public TipoNota getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(TipoNota tipoNota) {
        this.tipoNota = tipoNota;
    }

    public ValidacionNota getValidacionNotaInf() {
        return validacionNotaInf;
    }

    public void setValidacionNotaInf(ValidacionNota validacionNotaInf) {
        this.validacionNotaInf = validacionNotaInf;
    }
}
