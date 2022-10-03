package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Ciudadano  */
public class CiudadanoPk implements java.io.Serializable {
    public String idCiudadano;

    public CiudadanoPk() {
    }

    public CiudadanoPk(String s) {
        int i;
        int p = 0;
        idCiudadano = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CiudadanoPk)) {
            return false;
        }

        final CiudadanoPk id = (CiudadanoPk) o;

        if ((this.idCiudadano != null)
                ? (!idCiudadano.equals(id.idCiudadano))
                    : (id.idCiudadano != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idCiudadano != null) ? idCiudadano.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCiudadano);

        return buffer.toString();
    }
}