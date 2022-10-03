package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class MatriculaNoGrabadaEnhancedPk implements java.io.Serializable {

    public String idCirculo;
    public long idMatNoGrabada;

    public MatriculaNoGrabadaEnhancedPk() {
    }

    public MatriculaNoGrabadaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        p = i + 1;
        idMatNoGrabada = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatriculaNoGrabadaEnhancedPk)) return false;

        final MatriculaNoGrabadaEnhancedPk id = (MatriculaNoGrabadaEnhancedPk) o;

        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idMatNoGrabada != id.idMatNoGrabada) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (int) (idMatNoGrabada ^ (idMatNoGrabada >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idMatNoGrabada);
        return buffer.toString();
    }
}