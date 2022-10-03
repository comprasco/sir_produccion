package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class SalvedadFolioTMPPk implements java.io.Serializable {
    public String idMatricula;
    public String idSalvedadFoTmp;
    public SalvedadFolioTMPPk() {
    }

    public SalvedadFolioTMPPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idMatricula = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idSalvedadFoTmp = s.substring(p, i);

    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SalvedadFolioTMPPk)) {
            return false;
        }

        final SalvedadFolioTMPPk id = (SalvedadFolioTMPPk) o;

        if ((this.idMatricula != null)
                ? (!idMatricula.equals(id.idMatricula))
                    : (id.idMatricula != null)) {
            return false;
        }

        if ((this.idSalvedadFoTmp != null)
                ? (!idSalvedadFoTmp.equals(id.idSalvedadFoTmp))
                    : (id.idSalvedadFoTmp != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idMatricula != null) ? idMatricula.hashCode() : 0);
        result = (29 * result) +
            ((idSalvedadFoTmp != null) ? idSalvedadFoTmp.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idMatricula);
        buffer.append('-');
        buffer.append(idSalvedadFoTmp);

        return buffer.toString();
    }
}