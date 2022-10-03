package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a SubtipoSolicitud
 */
public class SubtipoSolicitudPk implements java.io.Serializable {

    public String idSubtipoSol;

    public SubtipoSolicitudPk() {
    }

    public SubtipoSolicitudPk(String s) {
        int i, p = 0;
        idSubtipoSol = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtipoSolicitudPk)) return false;

        final SubtipoSolicitudPk id = (SubtipoSolicitudPk) o;

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