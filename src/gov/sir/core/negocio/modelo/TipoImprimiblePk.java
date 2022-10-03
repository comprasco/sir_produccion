package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class TipoImprimiblePk implements java.io.Serializable {

    public String idTipoImprimible;

    public TipoImprimiblePk() {
    }

    public TipoImprimiblePk(String s) {
        int i, p = 0;
        idTipoImprimible = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoImprimiblePk)) return false;

        final TipoImprimiblePk id = (TipoImprimiblePk) o;

        if (this.idTipoImprimible != null ? !idTipoImprimible.equals(id.idTipoImprimible) : id.idTipoImprimible != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoImprimible != null ? idTipoImprimible.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoImprimible);
        return buffer.toString();
    }
}