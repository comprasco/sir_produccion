package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SubtipoAtencionTipoActoPk;

/**
 * Application identity objectid-class.
 */
public class SubtipoAtencionTipoActoEnhancedPk implements java.io.Serializable {

    public String idSubtipoAtencion;
    public String idTipoActo;

    public SubtipoAtencionTipoActoEnhancedPk() {
    }
    
	public SubtipoAtencionTipoActoEnhancedPk(SubtipoAtencionTipoActoPk id){
		idSubtipoAtencion = id.idSubtipoAtencion;
		idTipoActo        = id.idTipoActo;        	
	}
    
	public SubtipoAtencionTipoActoPk getSubtipoAtencionTipoActoID(){
		SubtipoAtencionTipoActoPk id = new SubtipoAtencionTipoActoPk();
		id.idSubtipoAtencion = idSubtipoAtencion;
		id.idTipoActo        = idTipoActo;
		return id;
	}        

    public SubtipoAtencionTipoActoEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSubtipoAtencion = s.substring(p, i);
        p = i + 1;
        idTipoActo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtipoAtencionTipoActoEnhancedPk)) return false;

        final SubtipoAtencionTipoActoEnhancedPk id = (SubtipoAtencionTipoActoEnhancedPk) o;

        if (this.idSubtipoAtencion != null ? !idSubtipoAtencion.equals(id.idSubtipoAtencion) : id.idSubtipoAtencion != null) return false;
        if (this.idTipoActo != null ? !idTipoActo.equals(id.idTipoActo) : id.idTipoActo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSubtipoAtencion != null ? idSubtipoAtencion.hashCode() : 0);
        result = 29 * result + (idTipoActo != null ? idTipoActo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSubtipoAtencion);
        buffer.append('-');
        buffer.append(idTipoActo);
        return buffer.toString();
    }
}