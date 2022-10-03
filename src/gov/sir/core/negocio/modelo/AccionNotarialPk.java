package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a AccionNotarial
 */
public class AccionNotarialPk implements java.io.Serializable {

    public String idAccionNotarial;

    public AccionNotarialPk() {
    }

    public AccionNotarialPk(String s) {
        int i, p = 0;
        idAccionNotarial = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccionNotarialPk)) return false;

        final AccionNotarialPk id = (AccionNotarialPk) o;

        if (this.idAccionNotarial != null ? !idAccionNotarial.equals(id.idAccionNotarial) : id.idAccionNotarial != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAccionNotarial != null ? idAccionNotarial.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAccionNotarial);
        return buffer.toString();
    }
}