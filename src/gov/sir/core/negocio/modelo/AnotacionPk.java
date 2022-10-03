package gov.sir.core.negocio.modelo;

/**
* Clase que define los atributos que identifican a Anotación 
*/
public class AnotacionPk implements java.io.Serializable {
    public String idAnotacion;
    public String idMatricula;

    public AnotacionPk() {
    }

    public AnotacionPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idAnotacion = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idMatricula = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AnotacionPk)) {
            return false;
        }

        final AnotacionPk id = (AnotacionPk) o;

        if ((this.idAnotacion != null)
                ? (!idAnotacion.equals(id.idAnotacion))
                    : (id.idAnotacion != null)) {
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
            ((idAnotacion != null) ? idAnotacion.hashCode() : 0);
        result = (29 * result) +
            ((idMatricula != null) ? idMatricula.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAnotacion);
        buffer.append('-');
        buffer.append(idMatricula);

        return buffer.toString();
    }
}