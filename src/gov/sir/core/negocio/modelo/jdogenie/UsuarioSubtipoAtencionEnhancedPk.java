package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencionPk;

/**
 * Application identity objectid-class.
 */
public class UsuarioSubtipoAtencionEnhancedPk implements java.io.Serializable {

    public String idSubtipoAtencion;
    public long idUsuario;

    public UsuarioSubtipoAtencionEnhancedPk() {
    }

    public UsuarioSubtipoAtencionEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSubtipoAtencion = s.substring(p, i);
        p = i + 1;
        idUsuario = Long.parseLong(s.substring(p));
    }
    

	public UsuarioSubtipoAtencionEnhancedPk(UsuarioSubtipoAtencionPk oid){
		idSubtipoAtencion = oid.idSubtipoAtencion;
		idUsuario = oid.idUsuario;
	}
    
	public UsuarioSubtipoAtencionPk getSubAtencionSubSolicitudID() {
		UsuarioSubtipoAtencionPk rta = new UsuarioSubtipoAtencionPk();
		rta.idSubtipoAtencion = idSubtipoAtencion;
		rta.idUsuario = idUsuario;
		return rta;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioSubtipoAtencionEnhancedPk)) return false;

        final UsuarioSubtipoAtencionEnhancedPk id = (UsuarioSubtipoAtencionEnhancedPk) o;

        if (this.idSubtipoAtencion != null ? !idSubtipoAtencion.equals(id.idSubtipoAtencion) : id.idSubtipoAtencion != null) return false;
        if (this.idUsuario != id.idUsuario) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSubtipoAtencion != null ? idSubtipoAtencion.hashCode() : 0);
        result = 29 * result + (int) (idUsuario ^ (idUsuario >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSubtipoAtencion);
        buffer.append('-');
        buffer.append(idUsuario);
        return buffer.toString();
    }
}