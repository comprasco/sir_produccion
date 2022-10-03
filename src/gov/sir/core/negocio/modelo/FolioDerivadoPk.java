package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a FolioDerivado  */
public class FolioDerivadoPk implements java.io.Serializable {
    public String idAnotacion;
    public String idAnotacion1;
    public String idMatricula;
    public String idMatricula1;

    public FolioDerivadoPk() {
    }

    public FolioDerivadoPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idAnotacion = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idAnotacion1 = s.substring(p, i);
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

        if (!(o instanceof FolioDerivadoPk)) {
            return false;
        }

        final FolioDerivadoPk id = (FolioDerivadoPk) o;

        if ((this.idAnotacion != null)
                ? (!idAnotacion.equals(id.idAnotacion))
                    : (id.idAnotacion != null)) {
            return false;
        }

        if ((this.idAnotacion1 != null)
                ? (!idAnotacion1.equals(id.idAnotacion1))
                    : (id.idAnotacion1 != null)) {
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
            ((idAnotacion != null) ? idAnotacion.hashCode() : 0);
        result = (29 * result) +
            ((idAnotacion1 != null) ? idAnotacion1.hashCode() : 0);
        result = (29 * result) +
            ((idMatricula != null) ? idMatricula.hashCode() : 0);
        result = (29 * result) +
            ((idMatricula1 != null) ? idMatricula1.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAnotacion);
        buffer.append('-');
        buffer.append(idAnotacion1);
        buffer.append('-');
        buffer.append(idMatricula);
        buffer.append('-');
        buffer.append(idMatricula1);

        return buffer.toString();
    }
}