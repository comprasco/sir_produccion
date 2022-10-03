package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class ComplementacionTMPPk implements java.io.Serializable {

    public String idComplementacionTmp;

    public ComplementacionTMPPk() {
    }

    public ComplementacionTMPPk(String s) {
        int i, p = 0;
        idComplementacionTmp = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplementacionTMPPk)) return false;

        final ComplementacionTMPPk id = (ComplementacionTMPPk) o;

        if (this.idComplementacionTmp != null ? !idComplementacionTmp.equals(id.idComplementacionTmp) : id.idComplementacionTmp != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idComplementacionTmp != null ? idComplementacionTmp.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idComplementacionTmp);
        return buffer.toString();
    }
}