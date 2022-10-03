package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a SalvedadFolio	  */

public class SalvedadFolioPk implements java.io.Serializable {
    public String idMatricula;
    public String idSalvedad;

    public SalvedadFolioPk() {
    }

    public SalvedadFolioPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idMatricula = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idSalvedad = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SalvedadFolioPk)) {
            return false;
        }

        final SalvedadFolioPk id = (SalvedadFolioPk) o;

        if ((this.idMatricula != null)
                ? (!idMatricula.equals(id.idMatricula))
                    : (id.idMatricula != null)) {
            return false;
        }

        if ((this.idSalvedad != null) ? (!idSalvedad.equals(id.idSalvedad))
                                          : (id.idSalvedad != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idMatricula != null) ? idMatricula.hashCode() : 0);
        result = (29 * result) +
            ((idSalvedad != null) ? idSalvedad.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idMatricula);
        buffer.append('-');
        buffer.append(idSalvedad);

        return buffer.toString();
    }
}