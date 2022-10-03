package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class RolPantallaEnhancedPk implements java.io.Serializable {

    public long idPantallaAdministrativa;
    public String idRol;

    public RolPantallaEnhancedPk() {
    }

    public RolPantallaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idPantallaAdministrativa = Long.parseLong(s.substring(p, i));
        p = i + 1;
        idRol = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolPantallaEnhancedPk)) return false;

        final RolPantallaEnhancedPk id = (RolPantallaEnhancedPk) o;

        if (this.idPantallaAdministrativa != id.idPantallaAdministrativa) return false;
        if (this.idRol != null ? !idRol.equals(id.idRol) : id.idRol != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idPantallaAdministrativa ^ (idPantallaAdministrativa >>> 32));
        result = 29 * result + (idRol != null ? idRol.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idPantallaAdministrativa);
        buffer.append('-');
        buffer.append(idRol);
        return buffer.toString();
    }
}