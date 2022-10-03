package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Eje  */
public class EjePk implements java.io.Serializable {
    public String idEje;

    public EjePk() {
    }

    public EjePk(String s) {
        int i;
        int p = 0;
        idEje = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof EjePk)) {
            return false;
        }

        final EjePk id = (EjePk) o;

        if ((this.idEje != null) ? (!idEje.equals(id.idEje))
                                     : (id.idEje != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + ((idEje != null) ? idEje.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEje);

        return buffer.toString();
    }
}