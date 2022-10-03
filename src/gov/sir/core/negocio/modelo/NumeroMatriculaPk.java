package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a NumeroMatricula  */

public class NumeroMatriculaPk implements java.io.Serializable {
    public String idNumero;

    public NumeroMatriculaPk() {
    }

    public NumeroMatriculaPk(String s) {
        int i;
        int p = 0;
        idNumero = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof NumeroMatriculaPk)) {
            return false;
        }

        final NumeroMatriculaPk id = (NumeroMatriculaPk) o;

        if ((this.idNumero != null) ? (!idNumero.equals(id.idNumero))
                                        : (id.idNumero != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idNumero != null) ? idNumero.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idNumero);

        return buffer.toString();
    }
}