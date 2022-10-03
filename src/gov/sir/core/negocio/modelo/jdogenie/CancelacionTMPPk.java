package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class CancelacionTMPPk implements java.io.Serializable {

    public String idAnotacionTmp;
    public String idCancelacionTmp;
    public String idMatricula;

    public CancelacionTMPPk() {
    }

    public CancelacionTMPPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idAnotacionTmp = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idCancelacionTmp = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idMatricula = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CancelacionTMPPk)) return false;

        final CancelacionTMPPk id = (CancelacionTMPPk) o;

        if (this.idAnotacionTmp != null ? !idAnotacionTmp.equals(id.idAnotacionTmp) : id.idAnotacionTmp != null) return false;
        if (this.idCancelacionTmp != null ? !idCancelacionTmp.equals(id.idCancelacionTmp) : id.idCancelacionTmp != null) return false;
        if (this.idMatricula != null ? !idMatricula.equals(id.idMatricula) : id.idMatricula != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAnotacionTmp != null ? idAnotacionTmp.hashCode() : 0);
        result = 29 * result + (idCancelacionTmp != null ? idCancelacionTmp.hashCode() : 0);
        result = 29 * result + (idMatricula != null ? idMatricula.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAnotacionTmp);
        buffer.append('-');
        buffer.append(idCancelacionTmp);
        buffer.append('-');
        buffer.append(idMatricula);
        return buffer.toString();
    }
}