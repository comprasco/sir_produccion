package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SubAtencionSubSolicitud;


/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class SubAtencionSubSolicitudEnhanced extends Enhanced {

    private String idSubtipoAtencion; // pk 
    private String idSubtipoSol; // pk 
    private SubtipoAtencionEnhanced subtipoAtencion; // inverse SubtipoAtencionEnhanced.subtipoSolicitudes
    private SubtipoSolicitudEnhanced subtipoSolicitud;

    public SubAtencionSubSolicitudEnhanced() {
    }

    public String getIdSubtipoAtencion() {
        return idSubtipoAtencion;
    }

    public void setIdSubtipoAtencion(String idSubtipoAtencion) {
        this.idSubtipoAtencion = idSubtipoAtencion;
    }

    public String getIdSubtipoSol() {
        return idSubtipoSol;
    }

    public void setIdSubtipoSol(String idSubtipoSol) {
        this.idSubtipoSol = idSubtipoSol;
    }

    public SubtipoAtencionEnhanced getSubtipoAtencion() {
        return subtipoAtencion;
    }

    public void setSubtipoAtencion(SubtipoAtencionEnhanced subtipoAtencion) {
        this.subtipoAtencion = subtipoAtencion;
        setIdSubtipoAtencion(subtipoAtencion.getIdSubtipoAtencion());
    }

    public SubtipoSolicitudEnhanced getSubtipoSolicitud() {
        return subtipoSolicitud;
    }

    public void setSubtipoSolicitud(SubtipoSolicitudEnhanced subtipoSolicitud) {
        this.subtipoSolicitud = subtipoSolicitud;
        setIdSubtipoSol(subtipoSolicitud.getIdSubtipoSol());
    }
    
	
	public static SubAtencionSubSolicitudEnhanced enhance(SubAtencionSubSolicitud sucursalBanco) {
		return (SubAtencionSubSolicitudEnhanced) Enhanced.enhance(sucursalBanco);
	}
}