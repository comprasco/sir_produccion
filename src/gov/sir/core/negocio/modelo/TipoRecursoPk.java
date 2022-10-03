package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoRecurso */

public class TipoRecursoPk implements java.io.Serializable {

    public String idTipoRecurso;

    public TipoRecursoPk() {
    }

    public TipoRecursoPk(String s) {
        int i, p = 0;
        idTipoRecurso = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoRecursoPk)) return false;

        final TipoRecursoPk id = (TipoRecursoPk) o;

        if (this.idTipoRecurso != null ? !idTipoRecurso.equals(id.idTipoRecurso) : id.idTipoRecurso != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoRecurso != null ? idTipoRecurso.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoRecurso);
        return buffer.toString();
    }
}