package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class TipoRelacionPk implements java.io.Serializable {

    public String idTipoRelacion;

    public TipoRelacionPk() {
    }

    public TipoRelacionPk(String s) {
        int p = 0;
        idTipoRelacion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoRelacionPk)) return false;

        final TipoRelacionPk id = (TipoRelacionPk) o;

        if (this.idTipoRelacion != null ? !idTipoRelacion.equals(id.idTipoRelacion) : id.idTipoRelacion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoRelacion != null ? idTipoRelacion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoRelacion);
        return buffer.toString();
    }
}