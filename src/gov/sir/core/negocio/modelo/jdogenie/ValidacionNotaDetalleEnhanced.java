package gov.sir.core.negocio.modelo.jdogenie;

import java.util.Date;

public class ValidacionNotaDetalleEnhanced extends Enhanced{

    private String idFase; // pk 
    private long idProceso; // pk 
    private String idRespuesta; // pk 
    private String idTipoNota; // pk 
    private String descripcion;
    private Date fechaCreacion;
    private TipoNotaEnhanced tipoNota;
    private ValidacionNotaEnhanced validacionNotaInf;

    public ValidacionNotaDetalleEnhanced() {
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

    public TipoNotaEnhanced getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(TipoNotaEnhanced tipoNota) {
        this.tipoNota = tipoNota;
    }

    public ValidacionNotaEnhanced getValidacionNotaInf() {
        return validacionNotaInf;
    }

    public void setValidacionNotaInf(ValidacionNotaEnhanced validacionNotaInf) {
        this.validacionNotaInf = validacionNotaInf;
    }
}
