package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


public class Solicitante implements TransferObject{

    private String idSolicitud; // pk
    private String idCiudadano; // pk
    private SolicitudDevolucion solicitud; // inverse SolicitudDevolucionEnhanced.checkedItems
    private Ciudadano ciudadano; 
    private static final long serialVersionUID = 1L;
    
    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    
    public String getIdCiudadano() {
        return idCiudadano;
    }

    public void setIdCiudadano(String idCiudadano) {
        this.idCiudadano = idCiudadano;
    }
    
    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudDevolucion solicitud) {
        this.solicitud = solicitud;
        setIdSolicitud(solicitud.getIdSolicitud());
    }
    
    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }
}
