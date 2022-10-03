package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class PantallaAdministrativaEnhancedPk implements java.io.Serializable {

    public long idPantallaAdministrativa;

    public PantallaAdministrativaEnhancedPk() {
    }

    public PantallaAdministrativaEnhancedPk(String s) {
        int i, p = 0;
        idPantallaAdministrativa = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PantallaAdministrativaEnhancedPk)) return false;

        final PantallaAdministrativaEnhancedPk id = (PantallaAdministrativaEnhancedPk) o;

        if (this.idPantallaAdministrativa != id.idPantallaAdministrativa) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idPantallaAdministrativa ^ (idPantallaAdministrativa >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idPantallaAdministrativa);
        return buffer.toString();
    }
}