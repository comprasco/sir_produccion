package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/* Generated by Versant Open Access (version:3.2.6 (17 Nov 2004)) */

/** Clase que modela la configuracion de permisos de correcciones para cada solicitud  */

public class SolicitudPermisoCorreccion implements TransferObject{

    private String idPermiso; // pk 
    private String idSolicitud; // pk 
    private Date fechaCreacion;
    private PermisoCorreccion permiso;
    private Solicitud solicitud; // inverse SolicitudCorreccionEnhanced.solicitudPermisoCorreccionEnhanced

    /** M?todo constructor por defecto  */
    public SolicitudPermisoCorreccion() {
    }

    /** Retorna el identificador del permiso configurado para la solicitud  */
    public String getIdPermiso() {
        return idPermiso;
    }
    private static final long serialVersionUID = 1L;
    /** Cambia el identificador del permiso configurado para la solicitud  */
    public void setIdPermiso(String idPermiso) {
        this.idPermiso = idPermiso;
    }

    /** Retorna el identificador de la solicitud  */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /** Cambia el identificador de la solicitud  */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /** Retorna la fecha de creaci?n del registro en la base de datos  */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /** Cambia la fecha de creaci?n del registro en la base de datos  */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna el identificador del permiso configurado para la solicitud  */
    public PermisoCorreccion getPermiso() {
        return permiso;
    }

    /** Cambia el identificador del permiso configurado para la solicitud  */
    public void setPermiso(PermisoCorreccion permiso) {
        this.permiso = permiso;
        setIdPermiso(permiso.getIdPermiso());
    }

    /** Retorna el identificador de la solicitud  */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /** Cambia el identificador de la solicitud  */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        setIdSolicitud(solicitud.getIdSolicitud());
    }
}
