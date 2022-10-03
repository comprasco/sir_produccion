package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class FolioDatosTMPPk implements java.io.Serializable {
    public String idMatricula;

    public FolioDatosTMPPk() {
    }

    public FolioDatosTMPPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idMatricula = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof FolioDatosTMPPk)) {
            return false;
        }

        final FolioDatosTMPPk id = (FolioDatosTMPPk) o;

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
            ((idMatricula != null) ? idMatricula.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idMatricula);

        return buffer.toString();
    }
}