package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class ImprimiblePk implements java.io.Serializable {

    public int idImprimible;

    public ImprimiblePk() {
    }

    public ImprimiblePk(String s) {
        int i, p = 0;
        idImprimible = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImprimiblePk)) return false;

        final ImprimiblePk id = (ImprimiblePk) o;

        if (this.idImprimible != id.idImprimible) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idImprimible ^ (idImprimible >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idImprimible);
        return buffer.toString();
    }
}