package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a ZonaRegistral */

public class ZonaRegistralPk implements java.io.Serializable {
    public String idZonaRegistral;

    public ZonaRegistralPk() {
    }

    public ZonaRegistralPk(String s) {
        int i;
        int p = 0;
        idZonaRegistral = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ZonaRegistralPk)) {
            return false;
        }

        final ZonaRegistralPk id = (ZonaRegistralPk) o;

        if ((this.idZonaRegistral != null)
                ? (!idZonaRegistral.equals(id.idZonaRegistral))
                    : (id.idZonaRegistral != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idZonaRegistral != null) ? idZonaRegistral.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idZonaRegistral);

        return buffer.toString();
    }
}