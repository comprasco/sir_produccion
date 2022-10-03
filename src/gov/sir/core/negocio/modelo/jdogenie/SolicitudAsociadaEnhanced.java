package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudAsociada;


/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class SolicitudAsociadaEnhanced extends Enhanced {

    private String idSolicitud; // pk 
    private String idSolicitud1; // pk 
    private SolicitudEnhanced solicitudHija; // inverse SolicitudEnhanced.solicitudesPadre
    private SolicitudEnhanced solicitudPadre; // inverse SolicitudEnhanced.solicitudesHijas

    public SolicitudAsociadaEnhanced() {
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdSolicitud1() {
        return idSolicitud1;
    }

    public void setIdSolicitud1(String idSolicitud1) {
        this.idSolicitud1 = idSolicitud1;
    }

    public SolicitudEnhanced getSolicitudHija() {
        return solicitudHija;
    }

    public void setSolicitudHija(SolicitudEnhanced solicitudHija) {
        this.solicitudHija = solicitudHija;
        setIdSolicitud1(solicitudHija.getIdSolicitud());
    }

    public SolicitudEnhanced getSolicitudPadre() {
        return solicitudPadre;
    }

    public void setSolicitudPadre(SolicitudEnhanced solicitudPadre) {
        this.solicitudPadre = solicitudPadre;
        setIdSolicitud(solicitudPadre.getIdSolicitud());
    }
    
	public static SolicitudAsociadaEnhanced enhance(SolicitudAsociada solicitud) {
		return (SolicitudAsociadaEnhanced) Enhanced.enhance(solicitud);
	}
}