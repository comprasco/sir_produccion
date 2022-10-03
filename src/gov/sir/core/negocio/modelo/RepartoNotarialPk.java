package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a RepartoNotarial	  */

public class RepartoNotarialPk implements java.io.Serializable {

    public String idRepartoNotarial;

    public RepartoNotarialPk() {
    }

    public RepartoNotarialPk(String s) {
        int i, p = 0;
        idRepartoNotarial = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepartoNotarialPk)) return false;

        final RepartoNotarialPk id = (RepartoNotarialPk) o;

        if (this.idRepartoNotarial != null ? !idRepartoNotarial.equals(id.idRepartoNotarial) : id.idRepartoNotarial != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idRepartoNotarial != null ? idRepartoNotarial.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idRepartoNotarial);
        return buffer.toString();
    }
}