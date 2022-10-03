package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a SubAtencionSubSolicitud
 */
public class SubAtencionSubSolicitudPk implements java.io.Serializable {

    public String idSubtipoAtencion;
    public String idSubtipoSol;

    public SubAtencionSubSolicitudPk() {
    }
    

    public SubAtencionSubSolicitudPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSubtipoAtencion = s.substring(p, i);
        p = i + 1;
        idSubtipoSol = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubAtencionSubSolicitudPk)) return false;

        final SubAtencionSubSolicitudPk id = (SubAtencionSubSolicitudPk) o;

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