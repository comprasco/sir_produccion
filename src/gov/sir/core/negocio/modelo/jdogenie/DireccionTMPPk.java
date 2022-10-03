package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class DireccionTMPPk implements java.io.Serializable {
    public String idDireccionTmp;
    public String idMatricula;

    public DireccionTMPPk() {
    }

    public DireccionTMPPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idDireccionTmp = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idMatricula = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DireccionTMPPk)) {
            return false;
        }

        final DireccionTMPPk id = (DireccionTMPPk) o;

        if ((this.idDireccionTmp != null)
                ? (!idDireccionTmp.equals(id.idDireccionTmp))
                    : (id.idDireccionTmp != null)) {
            return false;
        }

        if ((this.idMatricula != null)
                ? (!idMatricula.equals(id.idMatricula))
                    : (id.idMatricula != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idDireccionTmp != null) ? idDireccionTmp.hashCode() : 0);
        result = (29 * result) +
            ((idMatricula != null) ? idMatricula.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDireccionTmp);
        buffer.append('-');
        buffer.append(idMatricula);

        return buffer.toString();
    }
}