package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/* Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004)) */

/** Tabla que guada las relaciones entre las solicitudes  */

public class SolicitudAsociada implements TransferObject {

    private String idSolicitud; // pk 
    private String idSolicitud1; // pk 
    private Solicitud solicitudHija; // inverse SolicitudEnhanced.solicitudesPadre
    private Solicitud solicitudPadre; // inverse SolicitudEnhanced.solicitudesHijas
private static final long serialVersionUID = 1L;
    /** Constructor por defecto */
    public SolicitudAsociada()  {
    }

    /** Retorna el identificador de la solicitud padre  */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /** Cambia el identificador de la solicitud padre  */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /** Retorna el identificador de solicitud hija  */
    public String getIdSolicitud1() {
        return idSolicitud1;
    }

    /** Cambia el identificador de solicitud hija  */
    public void setIdSolicitud1(String idSolicitud1) {
        this.idSolicitud1 = idSolicitud1;
    }

    public Solicitud getSolicitudHija() {
        return solicitudHija;
    }

    public void setSolicitudHija(Solicitud solicitudHija) {
        this.solicitudHija = solicitudHija;
		setIdSolicitud1(solicitudHija.getIdSolicitud());
    }

    public Solicitud getSolicitudPadre() {
        return solicitudPadre;
    }

    public void setSolicitudPadre(Solicitud solicitudPadre) {
        this.solicitudPadre = solicitudPadre;
        setIdSolicitud(solicitudPadre.getIdSolicitud());
    }
}