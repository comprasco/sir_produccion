package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class FolioDerivadoTMPPk implements java.io.Serializable {
    public String idAnotacion1Tmp;
    public String idAnotacionTmp;
    public String idMatricula;
    public String idMatricula1;

    public FolioDerivadoTMPPk() {
    }

    public FolioDerivadoTMPPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idAnotacion1Tmp = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idAnotacionTmp = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idMatricula = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idMatricula1 = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof FolioDerivadoTMPPk)) {
            return false;
        }

        final FolioDerivadoTMPPk id = (FolioDerivadoTMPPk) o;

        if ((this.idAnotacion1Tmp != null)
                ? (!idAnotacion1Tmp.equals(id.idAnotacion1Tmp))
                    : (id.idAnotacion1Tmp != null)) {
            return false;
        }

        if ((this.idAnotacionTmp != null)
                ? (!idAnotacionTmp.equals(id.idAnotacionTmp))
                    : (id.idAnotacionTmp != null)) {
            return false;
        }

        if ((this.idMatricula != null)
                ? (!idMatricula.equals(id.idMatricula))
                    : (id.idMatricula != null)) {
            return false;
        }

        if ((this.idMatricula1 != null)
                ? (!idMatricula1.equals(id.idMatricula1))
                    : (id.idMatricula1 != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idAnotacion1Tmp != null) ? idAnotacion1Tmp.hashCode() : 0);
        result = (29 * result) +
            ((idAnotacionTmp != null) ? idAnotacionTmp.hashCode() : 0);
        result = (29 * result) +
            ((idMatricula != null) ? idMatricula.hashCode() : 0);
        result = (29 * result) +
            ((idMatricula1 != null) ? idMatricula1.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAnotacion1Tmp);
        buffer.append('-');
        buffer.append(idAnotacionTmp);
        buffer.append('-');
        buffer.append(idMatricula);
        buffer.append('-');
        buffer.append(idMatricula1);

        return buffer.toString();
    }
}