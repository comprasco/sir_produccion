package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SubtipoAtencionPk;

/**
 * Application identity objectid-class.
 */
public class SubtipoAtencionEnhancedPk implements java.io.Serializable {

    public String idSubtipoAtencion;

    public SubtipoAtencionEnhancedPk() {
    }
    
    public SubtipoAtencionEnhancedPk(SubtipoAtencionPk id){
		idSubtipoAtencion = id.idSubtipoAtencion;        	
    }
    
    public SubtipoAtencionPk getSubtipoAtencionID(){
		SubtipoAtencionPk id = new SubtipoAtencionPk();
		id.idSubtipoAtencion = idSubtipoAtencion;
		return id;
    }

    public SubtipoAtencionEnhancedPk(String s) {
        int i, p = 0;
        idSubtipoAtencion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtipoAtencionEnhancedPk)) return false;

        final SubtipoAtencionEnhancedPk id = (SubtipoAtencionEnhancedPk) o;

        if (this.idSubtipoAtencion != null ? !idSubtipoAtencion.equals(id.idSubtipoAtencion) : id.idSubtipoAtencion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSubtipoAtencion != null ? idSubtipoAtencion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSubtipoAtencion);
        return buffer.toString();
    }
}