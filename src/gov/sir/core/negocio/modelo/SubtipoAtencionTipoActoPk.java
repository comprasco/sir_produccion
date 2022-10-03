package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a SubtipoAtencionTipoActo
 */
public class SubtipoAtencionTipoActoPk implements java.io.Serializable {

    public String idSubtipoAtencion;
    public String idTipoActo;

    public SubtipoAtencionTipoActoPk() {
    }

    public SubtipoAtencionTipoActoPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSubtipoAtencion = s.substring(p, i);
        p = i + 1;
        idTipoActo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtipoAtencionTipoActoPk)) return false;

        final SubtipoAtencionTipoActoPk id = (SubtipoAtencionTipoActoPk) o;

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