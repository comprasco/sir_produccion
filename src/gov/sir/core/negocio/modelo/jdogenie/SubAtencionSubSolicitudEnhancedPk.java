package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SubAtencionSubSolicitudPk;

/**
 * Application identity objectid-class.
 */
public class SubAtencionSubSolicitudEnhancedPk implements java.io.Serializable {

    public String idSubtipoAtencion;
    public String idSubtipoSol;

    public SubAtencionSubSolicitudEnhancedPk() {
    }
    

	public SubAtencionSubSolicitudEnhancedPk(SubAtencionSubSolicitudPk oid){
		idSubtipoAtencion = oid.idSubtipoAtencion;
	    idSubtipoSol = oid.idSubtipoSol;
	}
    
	public SubAtencionSubSolicitudPk getSubAtencionSubSolicitudID() {
		SubAtencionSubSolicitudPk rta = new SubAtencionSubSolicitudPk();
		rta.idSubtipoAtencion = idSubtipoAtencion;
		rta.idSubtipoSol = idSubtipoSol;
		return rta;
	}
    

    public SubAtencionSubSolicitudEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSubtipoAtencion = s.substring(p, i);
        p = i + 1;
        idSubtipoSol = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubAtencionSubSolicitudEnhancedPk)) return false;

        final SubAtencionSubSolicitudEnhancedPk id = (SubAtencionSubSolicitudEnhancedPk) o;

        if (this.idSubtipoAtencion != null ? !idSubtipoAtencion.equals(id.idSubtipoAtencion) : id.idSubtipoAtencion != null) return false;
        if (this.idSubtipoSol != null ? !idSubtipoSol.equals(id.idSubtipoSol) : id.idSubtipoSol != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSubtipoAtencion != null ? idSubtipoAtencion.hashCode() : 0);
        result = 29 * result + (idSubtipoSol != null ? idSubtipoSol.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSubtipoAtencion);
        buffer.append('-');
        buffer.append(idSubtipoSol);
        return buffer.toString();
    }
}