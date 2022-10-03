package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class TipoPantallaEnhancedPk implements java.io.Serializable {

    public long idTipoPantalla;

    public TipoPantallaEnhancedPk() {
    }

    public TipoPantallaEnhancedPk(String s) {
        int i, p = 0;
        idTipoPantalla = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoPantallaEnhancedPk)) return false;

        final TipoPantallaEnhancedPk id = (TipoPantallaEnhancedPk) o;

        if (this.idTipoPantalla != id.idTipoPantalla) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idTipoPantalla ^ (idTipoPantalla >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoPantalla);
        return buffer.toString();
    }
}