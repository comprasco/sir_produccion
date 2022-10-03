package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a Circulo
 */
public class CirculoPk implements java.io.Serializable {
    public String idCirculo;

    public CirculoPk() {
    }

    public CirculoPk(String s) {
        //int i;
        int p = 0;
        idCirculo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CirculoPk)) {
            return false;
        }

        final CirculoPk idCirculo = (CirculoPk) o;

        if ((this.idCirculo != null)
                ? (!idCirculo.equals(idCirculo.idCirculo))
                    : (idCirculo.idCirculo != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idCirculo != null) ? idCirculo.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculo);

        return buffer.toString();
    }
}