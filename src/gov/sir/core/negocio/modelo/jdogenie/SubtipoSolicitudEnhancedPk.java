package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SubtipoSolicitudPk;

/**
 * Application identity objectid-class.
 */
public class SubtipoSolicitudEnhancedPk implements java.io.Serializable {

    public String idSubtipoSol;

    public SubtipoSolicitudEnhancedPk() {
    }
    

	public SubtipoSolicitudEnhancedPk(SubtipoSolicitudPk id){
		idSubtipoSol = id.idSubtipoSol;        	
	}
    
	public SubtipoSolicitudPk getSubtipoSolicitudID(){
		SubtipoSolicitudPk id = new SubtipoSolicitudPk();
		id.idSubtipoSol= idSubtipoSol;
		return id;
	}
    

    public SubtipoSolicitudEnhancedPk(String s) {
        int i, p = 0;
        idSubtipoSol = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtipoSolicitudEnhancedPk)) return false;

        final SubtipoSolicitudEnhancedPk id = (SubtipoSolicitudEnhancedPk) o;

        if (this.idSubtipoSol != null ? !idSubtipoSol.equals(id.idSubtipoSol) : id.idSubtipoSol != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSubtipoSol != null ? idSubtipoSol.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSubtipoSol);
        return buffer.toString();
    }
}