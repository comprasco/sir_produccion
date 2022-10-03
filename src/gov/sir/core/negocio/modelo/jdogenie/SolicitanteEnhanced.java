package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Solicitante;




public class SolicitanteEnhanced extends Enhanced{

    private String idSolicitud; // pk
    private String idCiudadano; // pk
    private SolicitudDevolucionEnhanced solicitud; // inverse SolicitudDevolucionEnhanced.checkedItems
    private CiudadanoEnhanced ciudadano; 

    
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

    public SolicitudDevolucionEnhanced getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudDevolucionEnhanced solicitud) {
        this.solicitud = solicitud;
        setIdSolicitud(solicitud.getIdSolicitud());
    }
    
    public CiudadanoEnhanced getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(CiudadanoEnhanced ciudadano) {
        this.ciudadano = ciudadano;
    }
    
	public static SolicitanteEnhanced enhance(Solicitante x) {
		return (SolicitanteEnhanced) Enhanced.enhance(x);
	}
	
	
}
