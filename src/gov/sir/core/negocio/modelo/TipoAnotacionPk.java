package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoAnotacion */

public class TipoAnotacionPk implements java.io.Serializable {
    public String idTipoAnotacion;

    public TipoAnotacionPk() {
    }

    public TipoAnotacionPk(String s) {
        int i;
        int p = 0;
        idTipoAnotacion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TipoAnotacionPk)) {
            return false;
        }

        final TipoAnotacionPk id = (TipoAnotacionPk) o;

        if ((this.idTipoAnotacion != null)
                ? (!idTipoAnotacion.equals(id.idTipoAnotacion))
                    : (id.idTipoAnotacion != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idTipoAnotacion != null) ? idTipoAnotacion.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoAnotacion);

        return buffer.toString();
    }
}