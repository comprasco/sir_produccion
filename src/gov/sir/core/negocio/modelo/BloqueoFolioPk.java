package gov.sir.core.negocio.modelo;

/**
 *  Clase que define los atributos que identifican a BloqueoFolio
 */
public class BloqueoFolioPk implements java.io.Serializable {
    public String idLlave;
    public String idMatricula;

    public BloqueoFolioPk() {
    }

    public BloqueoFolioPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idLlave = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idMatricula = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BloqueoFolioPk)) {
            return false;
        }

        final BloqueoFolioPk id = (BloqueoFolioPk) o;

        if ((this.idLlave != null) ? (!idLlave.equals(id.idLlave))
                                       : (id.idLlave != null)) {
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
            ((idLlave != null) ? idLlave.hashCode() : 0);
        result = (29 * result) +
            ((idMatricula != null) ? idMatricula.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idLlave);
        buffer.append('-');
        buffer.append(idMatricula);

        return buffer.toString();
    }
}